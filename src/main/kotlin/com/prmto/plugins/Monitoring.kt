package com.prmto.plugins

import io.ktor.application.*
import io.ktor.features.*

fun Application.configureMonitoring() {
    install(CallLogging)

}
