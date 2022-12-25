package models

import org.jetbrains.exposed.sql.Table

object Roles : Table(){
    val id = long("role_id").autoIncrement()
    val name = varchar(name = "name", length = 256)
    val description = text("description").nullable()
    val roleType = varchar(name = "type", 100)
    val organization_id = integer("organization_id").index("FK_Org_Role").references(Organizations.id)

    override val primaryKey = PrimaryKey(id, name = "PK_Role_Id")
}

enum class RoleTypes{
    Appraiser,
    Admin,
    OrdinaryWorker
}

