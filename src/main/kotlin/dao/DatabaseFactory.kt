package dao

import kotlinx.coroutines.Dispatchers
import models.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction


object DatabaseFactory{
    fun init(){
        val driver = "org.postgresql.Driver"
        val jdbcURL = "jdbc:postgresql://localhost:5432/complex_it"
        val db = Database.connect(
            url = jdbcURL,
            driver = driver,
            user = "postgres",
            password = "complex_IT"
        )

        transaction(db) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(
                Organizations,
                Users,
                Roles,
                Criterions,
                UsersRoles,
                RolesCriteria,
                Permissions,
                RolesPermissions
                )
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}