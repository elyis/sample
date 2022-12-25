import dao.DatabaseFactory
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import plugins.configureAuthentication
import plugins.configureRouting
import plugins.configureSecurity
import plugins.configureSerialization

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    DatabaseFactory.init()
    configureSerialization()
    configureAuthentication()
//    configureMonitoring()
    configureSecurity()
    configureRouting()

}