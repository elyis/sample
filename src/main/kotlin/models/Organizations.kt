package models

import org.jetbrains.exposed.sql.Table


object Organizations : Table(){
    val id = integer("organization_id").autoIncrement()
    val name = text("org_name").uniqueIndex("organization_name")
    override val primaryKey = PrimaryKey(id, name = "PK_ORG_Id")
}