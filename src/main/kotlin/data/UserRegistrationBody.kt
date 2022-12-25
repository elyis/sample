package data

import kotlinx.serialization.Serializable

@Serializable
data class UserRegistrationBody(
    val mail: String,
    val login: String,
    val password: String,
    val organizationName: String
)