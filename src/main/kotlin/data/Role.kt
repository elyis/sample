package data

import kotlinx.serialization.Serializable
import models.RoleTypes

@Serializable
data class Role(
    val id: Long,
    val name: String,
    val description: String?,
    val type: RoleTypes,
    val organizationId: Int
)