package models

import org.jetbrains.exposed.sql.Table

object RolesPermissions : Table(){
    val id = long("id").autoIncrement()
    val role_id = long("role_id")
        .uniqueIndex("FK_Role_RolesPermissions")
        .references(Roles.id)
    val permission_id = integer("permission_id")
        .uniqueIndex("FK_Permission_RolesPermissions")
        .references(Permissions.id)


    override val primaryKey = PrimaryKey(id, name = "PK_RolesPermission_Id")
}