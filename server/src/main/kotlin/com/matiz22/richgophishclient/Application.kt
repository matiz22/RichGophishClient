package com.matiz22.richgophishclient

import Greeting
import SERVER_PORT
import auth.model.User
import com.matiz22.richgophishclient.dao.UserDao
import com.matiz22.richgophishclient.dao.UserDaoImpl
import com.matiz22.richgophishclient.model.DatabaseSingleton
import com.matiz22.richgophishclient.plugins.configureUserRoutes


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
    val userDao: UserDao = UserDaoImpl()
    DatabaseSingleton.init()
    install(ContentNegotiation) {
        json()
    }
    routing {
        configureUserRoutes(userDao)
    }

}
