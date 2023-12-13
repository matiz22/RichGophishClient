package com.matiz22.richgophishclient

import Greeting
import SERVER_PORT
import auth.model.User


import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation){
        json()
    }
    routing {
        post("/") {
            val user = call.receive<User>()
            println(user)

        }
        get("/") {
            val user = User("Test")
            //val file = File("uploads/${user.id}.txt")
            call.respond(user)
        }
    }
}
