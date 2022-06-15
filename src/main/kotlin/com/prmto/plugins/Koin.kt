package com.prmto.plugins

import com.prmto.di.koinModule
import io.ktor.application.*
import org.koin.ktor.ext.Koin

fun Application.configureKoin() {
    install(Koin) {
       // slf4jLogger()
        modules(koinModule)
    }
}