package plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import routes.criterion.addCriterion
import routes.organization.addOrganization
import routes.roles.addRole

fun Application.configureRouting() {

    routing {
        authenticate {

        }
        addOrganization()
        addCriterion()
        addRole()
    }
}
