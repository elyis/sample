package data

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val mail: String,
    val login: String,
    val password: String,
    val organizationId: Int,
    val salary: Int,
    val premium: Int
)