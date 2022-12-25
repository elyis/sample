package models

import org.jetbrains.exposed.sql.Table

object Users : Table(){
    val id = long("user_id").autoIncrement()
    val mail = varchar(name = "mail", length = 256).uniqueIndex()
    val login = varchar(name = "login", length = 256)
    val password = varchar(name = "psw", length = 100)
    val salary = integer("salary").default(0)
    val premium = integer("premium").default(0)
    val organization_id = integer("organizations_id").index("FK_OrgUser").references(Organizations.id)

    override val primaryKey = PrimaryKey(id, name = "PK_USER_ID")
}