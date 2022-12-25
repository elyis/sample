package models

import org.jetbrains.exposed.sql.Table

object UsersRoles: Table(){
    val id = long("id").autoIncrement()
    val user_id = long("user_id").uniqueIndex("FK_UsersRoles_User").references(Users.id)
    val role_id = long("role_id").uniqueIndex("FK_UsersRoles_Roles").references(Roles.id)

    override val primaryKey = PrimaryKey(id, name = "PK_UsersRoles_Id")
}

