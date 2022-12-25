package data

import kotlinx.serialization.Serializable
import models.CriteriaTypes

@Serializable
data class RequestedCriterionBody(
    val description: String,
    val maxPoints: Int,
    val type: CriteriaTypes,
    val organizationName: String,
    val incrementAmount: Int? = null
)