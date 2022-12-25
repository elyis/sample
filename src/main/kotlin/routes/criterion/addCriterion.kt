package routes.criterion

import dao.facade.daoFacadeCriterion
import data.RequestedCriterionBody
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.addCriterion(){
    post("criterion"){
        try {
            val criterion = call.receiveNullable<RequestedCriterionBody>() ?: return@post

            if(daoFacadeCriterion.criterion(criterion.description, criterion.organizationName) == null){
                if(daoFacadeCriterion.addCriterion(criterion = criterion) != null)
                    call.response.status(HttpStatusCode.Created)

                else call.response.status(HttpStatusCode.BadRequest)
            }else
                call.response.status(HttpStatusCode.Conflict)

        }catch (e: BadRequestException){
            call.response.status(HttpStatusCode.BadRequest)
        }
    }
}