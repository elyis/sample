package dao.facadeImpl

import data.Criterion
import data.RequestedCriterionBody

interface DAOFacadeCriterionImpl {
    suspend fun criterion(id: Long): Criterion?
    suspend fun criterion(description: String, organizationName: String): Criterion?
    suspend fun addCriterion(criterion: RequestedCriterionBody): Criterion?
    suspend fun allCriteria(organizationName: String): List<Criterion>
    suspend fun rmCriterion(id: Long): Boolean
}