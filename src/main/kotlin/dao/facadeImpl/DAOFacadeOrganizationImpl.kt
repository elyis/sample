package dao.facadeImpl

import data.Organization

interface DAOFacadeOrganizationImpl{
    suspend fun organization(name: String) : Organization?
    suspend fun organization(id: Int) : Organization?
    suspend fun allOrganization(): List<Organization>
    suspend fun addOrganization(name: String) : Organization?
    suspend fun editOrganization(name: String) : Boolean
    suspend fun rmOrganization(id: Int) : Boolean
}