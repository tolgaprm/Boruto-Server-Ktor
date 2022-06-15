package com.prmto.plugins

import com.prmto.routes.root
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting() {
    routing {
        root()
    }
}
