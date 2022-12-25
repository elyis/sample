package data

import kotlinx.serialization.Serializable

@Serializable
data class UserClaims(
    val mail: String,
)