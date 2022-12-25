package plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import routes.criterion.addCriterion
import routes.criterion.getCriteria
import routes.organization.addOrganization
import routes.roles.addRole
import routes.roles.getRoles

fun Application.configureRouting() {

    routing {
        authenticate {
            addOrganization()
            addCriterion()
            addRole()
            getCriteria()
            getRoles()
        }
    }
}
