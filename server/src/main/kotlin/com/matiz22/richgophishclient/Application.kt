package com.matiz22.richgophishclient

import SERVER_PORT
import com.matiz22.richgophishclient.dao.UserConfigDao
import com.matiz22.richgophishclient.dao.UserConfigDaoImpl
import com.matiz22.richgophishclient.dao.UserDao
import com.matiz22.richgophishclient.dao.UserDaoImpl
import com.matiz22.richgophishclient.model.DatabaseSingleton
import com.matiz22.richgophishclient.plugins.configureUserConfigs
import com.matiz22.richgophishclient.plugins.configureUserRoutes


import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.*


fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val userDao: UserDao = UserDaoImpl()
    val userConfigDao: UserConfigDao = UserConfigDaoImpl()
    DatabaseSingleton.init()
    install(ContentNegotiation) {
        json()
    }
    routing {
        configureUserRoutes(userDao)
        configureUserConfigs(userConfigDao)
    }

}
