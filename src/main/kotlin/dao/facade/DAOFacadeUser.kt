package dao.facade

import dao.DatabaseFactory.dbQuery
import dao.facadeImpl.DAOFacadeUserImpl
import data.User
import data.UserAuthBody
import data.UserRegistrationBody
import models.Users
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class DAOFacadeUser : DAOFacadeUserImpl{
    private fun resultRowToUser(row: ResultRow) = User(
        id = row[Users.id],
        mail = row[Users.mail],
        login = row[Users.login],
        password = row[Users.password],
        organizationId = row[Users.organization_id],
        salary = row[Users.salary],
        premium = row[Users.premium]
    )

    override suspend fun user(id: Long): User? = dbQuery{
        Users
            .select { Users.id eq id }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    override suspend fun user(mail: String): User? = dbQuery{
        Users
            .select { Users.mail eq mail }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    override suspend fun addUser(userBody: UserRegistrationBody): User? = dbQuery{
        val organizationId = daoFacadeOrganization.organization(userBody.organizationName)?.id

        if (organizationId != null){
            val insertStatement =
                Users.insert {
                    it[mail] = userBody.mail
                    it[login] = userBody.login
                    it[password] = userBody.password
                    it[organization_id] = organizationId
                }
            insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUser)
        }else return@dbQuery null
    }

    override suspend fun rmUser(id: Int): Boolean = dbQuery{
        return@dbQuery false
    }

    override suspend fun checkAuth(user: UserAuthBody): Boolean  = dbQuery{
        !Users.select{(Users.mail eq user.mail) and (Users.password eq user.password)}.empty()
    }
}

val daoFacadeUser = DAOFacadeUser()