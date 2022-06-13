package com.prmto.plugins

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import java.time.Duration

fun Application.configureDefaultHeader() {
    install(DefaultHeaders){
        val oneYearInSeconds =Duration.ofDays(365).seconds
        header(HttpHeaders.CacheControl, value = "public, max-age=$oneYearInSeconds, immutable")
    }
}