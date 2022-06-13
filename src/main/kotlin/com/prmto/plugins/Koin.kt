package com.prmto.plugins

import io.ktor.application.*
import org.koin.ktor.ext.Koin

fun Application.configureKoin(){
    install(Koin){

    }
}