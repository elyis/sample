package dao.facade

import dao.DatabaseFactory.dbQuery
import dao.facadeImpl.DAOFacadeRoleImpl
import data.RequestedRoleBody
import data.Role
import models.RoleTypes
import models.Roles
import models.RolesCriteria
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOFacadeRole : DAOFacadeRoleImpl{
    private fun resultRowToRole(row: ResultRow) = Role(
        id = row[Roles.id],
        name = row[Roles.name],
        description = row[Roles.description],
        type = RoleTypes.valueOf(row[Roles.roleType]),
        organizationId = row[Roles.organization_id]
    )

    override suspend fun role(id: Long): Role? = dbQuery{
        Roles
            .select { Roles.id eq id }
            .map(::resultRowToRole)
            .singleOrNull()
    }

    override suspend fun role(name: String, organizationName: String): Role?  = dbQuery{
        val organizationId = daoFacadeOrganization.organization(organizationName)?.id ?: return@dbQuery null
        Roles
            .select { (Roles.name eq name) and
                    (Roles.organization_id eq organizationId) }
            .map(::resultRowToRole)
            .singleOrNull()
    }

    override suspend fun addRole(role: RequestedRoleBody): Role?  = dbQuery{
        val organizationId = daoFacadeOrganization.organization(role.organizationName)?.id ?: return@dbQuery null

        val insertStatement =
            Roles.insert {
                it[roleType] = role.type.name
                it[name] = role.name
                it[description] = role.description
                it[organization_id] = organizationId
        }

        val roleId = insertStatement.resultedValues?.singleOrNull()!![Roles.id]
        role.criteria.forEach{
            val criterion = daoFacadeCriterion.criterion(description = it, organizationName = role.organizationName)
            if (criterion != null){
                val relationId =
                    RolesCriteria.select {
                        (RolesCriteria.role_id eq roleId) and
                                (RolesCriteria.criterion_id eq criterion.id)
                    }.singleOrNull()
                        ?.get(RolesCriteria.id)

                if (relationId == null){
                    RolesCriteria.insert {it1 ->
                        it1[role_id] = roleId
                        it1[criterion_id] = criterion.id
                    }
                }
            }
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToRole)
    }

    override suspend fun rmRole(name: String, organizationName: String): Boolean = dbQuery{
        val organizationId = daoFacadeOrganization.organization(organizationName)?.id ?: return@dbQuery false
        Roles.deleteWhere { (Roles.name eq name) and (organization_id eq organizationId) } > 0
    }

    override suspend fun allRoles(organizationName: String): List<Role>  = dbQuery{
        val organizationId = daoFacadeOrganization.organization(organizationName)?.id ?: return@dbQuery emptyList<Role>()
        Roles.select { Roles.organization_id eq organizationId }.map(::resultRowToRole)
    }
}

val daoFacadeRole = DAOFacadeRole()