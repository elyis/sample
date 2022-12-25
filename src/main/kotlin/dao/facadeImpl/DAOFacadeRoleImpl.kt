package dao.facadeImpl

import data.RequestedRoleBody
import data.Role

interface DAOFacadeRoleImpl {
    suspend fun role(id: Long): Role?
    suspend fun role(name: String, organizationName: String): Role?
    suspend fun addRole(role: RequestedRoleBody): Role?
    suspend fun rmRole(name: String, organizationName: String): Boolean
}