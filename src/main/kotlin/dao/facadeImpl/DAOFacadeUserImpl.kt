package dao.facadeImpl

import data.User
import data.UserAuthBody
import data.UserRegistrationBody

interface DAOFacadeUserImpl{
    suspend fun user(mail: String) : User?
    suspend fun user(id: Long) : User?
    suspend fun addUser(userBody: UserRegistrationBody) : User?
    suspend fun rmUser(id: Int) : Boolean
    suspend fun checkAuth(user: UserAuthBody) : Boolean
}