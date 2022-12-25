package data

import kotlinx.serialization.Serializable
import models.CriteriaTypes

@Serializable
data class Criterion(
    val id: Long,
    val description: String,
    val maxPoints: Int,
    val type: CriteriaTypes,
    val incrementAmount: Int?,
    val organizationId: Int
)