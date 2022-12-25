package models

import org.jetbrains.exposed.sql.Table

object Permissions : Table(){
    val id = integer("permission_id").autoIncrement()
    val mod = char(name = "mod", length = 5)
    val description = text("description")

    override val primaryKey = PrimaryKey(id, name = "PK_Permissions_Id")
}

enum class PermissionsMod{
    Powerless,
    ReadOnly,
    WriteOnly,
    ExecuteOnly,
    NoRead,
    NoWrite,
    NoExecute,
    FullRights
}