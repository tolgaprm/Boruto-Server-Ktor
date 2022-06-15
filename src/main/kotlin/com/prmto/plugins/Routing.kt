package com.prmto.plugins

import com.prmto.routes.getAllHeroes
import com.prmto.routes.root
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.routing.*

fun Application.configureRouting() {
    routing {
        root()
        getAllHeroes()

        static("/images") {
            resources("images")
        }
    }
}
