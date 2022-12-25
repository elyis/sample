package models

import org.jetbrains.exposed.sql.Table

object RolesCriteria : Table(){
    val id = long("id").autoIncrement()
    val role_id = long("role_id").index().references(Roles.id)
    val criterion_id = long("criterion_id").index().references(Criterions.id)

    override val primaryKey = PrimaryKey(id, name = "PK_RolesCriteria_Id")
}