package com.prmto.plugins

import com.prmto.routes.getAllHeroes
import com.prmto.routes.root
import com.prmto.routes.searchHeroes
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.routing.*

fun Application.configureRouting() {
    routing {
        root()
        getAllHeroes()
        searchHeroes()

        static("/images") {
            resources("images")
        }
    }
}
