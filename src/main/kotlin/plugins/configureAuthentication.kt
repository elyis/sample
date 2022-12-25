package plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import dao.facade.daoFacadeUser
import data.TokenPair
import data.UserAuthBody
import data.UserClaims
import data.UserRegistrationBody
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*
import javax.crypto.Mac

fun Application.configureAuthentication(){
    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    install(Authentication){
        jwt {
            realm = myRealm
            verifier(
                JWT
                .require(Algorithm.HMAC256(secret))
                .withAudience(audience)
                .withIssuer(issuer)
                .build())

            validate { credential ->
                if (!credential.payload.getClaim("mail").asString().isNullOrEmpty()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }

            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }

        }
    }

    fun generateTokenPair(userClaims: UserClaims): TokenPair{
        val accessToken = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("mail", userClaims.mail)
            .sign(Algorithm.HMAC256(secret))

        val refreshToken = UUID.randomUUID().toString()

        return TokenPair("Bearer $accessToken", refreshToken)
    }

    routing {
        post("sign_up") {
            try {
                val user = call.receiveNullable<UserRegistrationBody>()
                if (daoFacadeUser.user(user!!.mail) == null){
                    if (daoFacadeUser.addUser(user) != null) {
                        val userClaims = UserClaims(
                            mail = user.mail
                        )
                        val tokenPair: TokenPair = generateTokenPair(userClaims)
                        call.respond(message = tokenPair, status = HttpStatusCode.Created)
                    }
                    else call.response.status(HttpStatusCode.BadRequest)
                }else
                    call.response.status(HttpStatusCode.Conflict)


            }catch (e: BadRequestException){
                call.response.status(HttpStatusCode.BadRequest)
            }
        }

        post("sign_in"){
            try {
                val user = call.receiveNullable<UserAuthBody>()
                if (daoFacadeUser.checkAuth(user!!)){
                    val userClaims = UserClaims(
                        mail = user.mail
                    )
                    val tokenPair: TokenPair = generateTokenPair(userClaims)
                    call.respond(message = tokenPair, status = HttpStatusCode.OK)
                }else
                    call.response.status(HttpStatusCode.Conflict)
            }catch (e: BadRequestException){
                call.response.status(HttpStatusCode.BadRequest)
            }
        }
    }




}