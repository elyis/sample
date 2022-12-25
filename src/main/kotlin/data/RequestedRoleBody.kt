package data

import kotlinx.serialization.Serializable
import models.RoleTypes

@Serializable
data class RequestedRoleBody(
    val name: String,
    val description: String? = null,
    val type: RoleTypes,
    val organizationName: String,
    val criteria: List<String>
)