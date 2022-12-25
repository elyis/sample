package data

import kotlinx.serialization.Serializable

@Serializable
data class RequestedOrganizationBody(
    val name: String,
)
