package routes.roles

import dao.facade.daoFacadeRole
import data.RequestedRoleBody
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.addRole(){
    post("role") {
        try {
            val role = call.receiveNullable<RequestedRoleBody>()

            if (daoFacadeRole.role(name = role!!.name, organizationName = role.organizationName) == null) {
                if(daoFacadeRole.addRole(role) != null)
                    call.response.status(HttpStatusCode.Created)
                else
                    call.response.status(HttpStatusCode.BadRequest)
            }else
                call.response.status(HttpStatusCode.Conflict)


        }catch (e: BadRequestException){
            call.response.status(HttpStatusCode.BadRequest)
        }
    }
}