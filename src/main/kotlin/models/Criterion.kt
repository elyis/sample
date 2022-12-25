package models

import org.jetbrains.exposed.sql.Table

object Criterions : Table(){
    val id = long("criterion_id").autoIncrement()
    val description = text("description")
    val max_points = integer("max_points").default(5)
    val criterionType = varchar(name = "type", length = 100)
    val incrementAmount = integer("increment_amount").nullable()
    val organization_id = integer("organization_id").index("FK_OrgCriterion").references(Organizations.id)

    override val primaryKey = PrimaryKey(id, name = "PK_Criterion_Id")
}

enum class CriteriaTypes{
    General,
    Stimulation,
    Fines
}