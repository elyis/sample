package dao.facade

import dao.DatabaseFactory.dbQuery
import dao.facadeImpl.DAOFacadeOrganizationImpl
import data.Organization
import models.Organizations
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOFacadeOrganization : DAOFacadeOrganizationImpl{
    private fun resultRowToOrganization(row: ResultRow) = Organization(
        id = row[Organizations.id],
        name = row[Organizations.name]
    )

    override suspend fun organization(id: Int): Organization? = dbQuery{
        Organizations
            .select { Organizations.id eq id }
            .map(::resultRowToOrganization)
            .singleOrNull()
    }

    override suspend fun organization(name: String): Organization? = dbQuery {
        Organizations
            .select { Organizations.name.lowerCase() eq name.lowercase() }
            .map(::resultRowToOrganization)
            .singleOrNull()
    }

    override suspend fun allOrganization(): List<Organization> = dbQuery {
        Organizations
            .selectAll()
            .map(::resultRowToOrganization)
    }

    override suspend fun addOrganization(name: String): Organization? = dbQuery{
        val insertStatement =
            Organizations.insert {
                it[Organizations.name] = name
            }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToOrganization)
    }

    override suspend fun editOrganization(name: String): Boolean  = dbQuery{
        Organizations.update ({Organizations.name eq name}){
            it[Organizations.name] = name
        } > 0
    }

    override suspend fun rmOrganization(id: Int): Boolean  = dbQuery{
        Organizations.deleteWhere { Organizations.id eq id } > 0
    }
}

val daoFacadeOrganization = DAOFacadeOrganization()
