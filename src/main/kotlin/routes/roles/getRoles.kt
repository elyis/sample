package routes.roles

import dao.facade.daoFacadeRole
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getRoles(){
    get("role/{orgName}"){
        val organizationName = call.parameters["orgName"] ?: return@get
        call.respond(daoFacadeRole.allRoles(organizationName))
    }
}