package routes.criterion

import dao.facade.daoFacadeCriterion
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getCriteria(){
    get("criterion/{orgName}"){
        val orgName = call.parameters["orgName"] ?: return@get
        call.respond(daoFacadeCriterion.allCriteria(orgName))
    }
}