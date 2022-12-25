package routes.organization

import dao.facade.daoFacadeOrganization
import data.RequestedOrganizationBody
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.addOrganization(){
    post("organization") {
        try {
            val requestedBody = call.receiveNullable<RequestedOrganizationBody>()

            if (daoFacadeOrganization.organization(requestedBody!!.name) == null) {
                daoFacadeOrganization.addOrganization(requestedBody.name)
                call.response.status(HttpStatusCode.Created)
            } else
                call.response.status(HttpStatusCode.Conflict)

        }catch (e: BadRequestException){
            call.response.status(HttpStatusCode.BadRequest)
        }
    }
}