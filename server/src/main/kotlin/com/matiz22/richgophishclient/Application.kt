package com.matiz22.richgophishclient

import SERVER_PORT
import com.matiz22.richgophishclient.dao.UserConfigDao
import com.matiz22.richgophishclient.dao.UserConfigDaoImpl
import com.matiz22.richgophishclient.dao.UserDao
import com.matiz22.richgophishclient.dao.UserDaoImpl
import com.matiz22.richgophishclient.model.DatabaseSingleton
import com.matiz22.richgophishclient.plugins.configureUserConfigs
import com.matiz22.richgophishclient.plugins.configureUserRoutes
import io.github.cdimascio.dotenv.dotenv
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode


import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.response.respond
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
    intercept(ApplicationCallPipeline.Plugins) {
        val apiKeyHeader = call.request.headers["X-Api-Key"]
        val expectedApiKey = try {
            dotenv {
                directory = "./"
                filename = ".env"
            }.get("API_KEY")
        }catch (e: Exception){
            "test"
        }
        if (apiKeyHeader != expectedApiKey) {
            call.respond(status = HttpStatusCode.Unauthorized, "Invalid api key in header")
            finish()
        }
    }
    routing {
        configureUserRoutes(userDao)
        configureUserConfigs(userConfigDao)
    }

}
