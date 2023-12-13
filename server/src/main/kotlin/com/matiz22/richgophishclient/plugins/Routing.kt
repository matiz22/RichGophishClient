package com.matiz22.richgophishclient.plugins

import auth.model.User
import com.matiz22.richgophishclient.services.UserService
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.configureUserService(userService: UserService){
    routing {
        post("/user") {
            val requestUser = call.receive<User>()
            userService.createUser(requestUser).let {  }
        }
    }
}