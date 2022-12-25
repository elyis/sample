package plugins

import io.ktor.server.metrics.dropwizard.*
import com.codahale.metrics.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

//fun Application.configureMonitoring() {
//    install(DropwizardMetrics) {
//      Slf4jReporter.forRegistry(registry)
//        .outputTo(thi@module.log)
//        .convertRatesTo(TimeUnit.SECONDS)
//        .convertDurationsTo(TimeUnit.MILLISECONDS)
//        .build()
//        .start(10, TimeUnit.SECONDS)
//    }
//}
