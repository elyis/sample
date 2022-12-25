package dao.facade

import dao.DatabaseFactory.dbQuery
import dao.facadeImpl.DAOFacadeCriterionImpl
import data.Criterion
import data.RequestedCriterionBody
import kotlinx.serialization.descriptors.StructureKind
import models.CriteriaTypes
import models.Criterions
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class DAOFacadeCriterion : DAOFacadeCriterionImpl{
    private fun resultRowToCriterion(row: ResultRow): Criterion = Criterion(
            id = row[Criterions.id],
            description = row[Criterions.description],
            maxPoints = row[Criterions.max_points],
            type = CriteriaTypes.valueOf(row[Criterions.criterionType]),
            incrementAmount = row[Criterions.incrementAmount],
            organizationId = row[Criterions.organization_id]
    )

    override suspend fun criterion(id: Long): Criterion? = dbQuery{
        Criterions
            .select { Criterions.id eq id }
            .map(::resultRowToCriterion)
            .singleOrNull()
    }

    override suspend fun criterion(description: String, organizationName: String): Criterion? = dbQuery{
        val organizationId = daoFacadeOrganization.organization(organizationName)?.id ?: return@dbQuery null
        Criterions
            .select { (Criterions.description eq description) and
                    (Criterions.organization_id eq organizationId)}
            .map(::resultRowToCriterion)
            .singleOrNull()
    }

    override suspend fun allCriteria(organizationName: String): List<Criterion> = dbQuery{
        val organizationId = daoFacadeOrganization.organization(organizationName)?.id ?: return@dbQuery emptyList<Criterion>()

        Criterions
            .select { Criterions.organization_id eq organizationId }
            .map(::resultRowToCriterion)
    }

    override suspend fun addCriterion(criterion: RequestedCriterionBody): Criterion? = dbQuery{
        val organizationId = daoFacadeOrganization.organization(criterion.organizationName)?.id ?: return@dbQuery null

        val insertStatement =
            Criterions.insert {
                it[criterionType] = criterion.type.name
                it[incrementAmount] = criterion.incrementAmount
                it[organization_id] = organizationId
                it[description] = criterion.description
                it[max_points] = criterion.maxPoints
            }
        insertStatement.resultedValues?.singleOrNull()?.let (::resultRowToCriterion)
    }

    override suspend fun rmCriterion(id: Long): Boolean {
        TODO("Not yet implemented")
    }

}

val daoFacadeCriterion = DAOFacadeCriterion()