package data

import kotlinx.serialization.Serializable

@Serializable
data class UserAuthBody(
    val mail: String,
    val password: String
)