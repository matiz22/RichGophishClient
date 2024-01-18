package com.matiz22.richgophishclient.plugins

import auth.domain.model.EmailCredentials
import auth.domain.model.IdCredentials
import com.matiz22.richgophishclient.dao.UserDao
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.configureUserRoutes(userDao: UserDao) {
    route("/user") {
        post("/userById") {
            val body = call.receive<IdCredentials>()
            val loggedUser = userDao.logUserBySavedCredential(
                id = body.id, password = body.password
            )
            if (loggedUser == null) {
                call.respond(status = HttpStatusCode.BadRequest, message = "User doesn't exist")
            } else {
                call.respond(status = HttpStatusCode.OK, loggedUser)
            }
        }
        post("/userByEmail") {
            val body = call.receive<EmailCredentials>()

            val loggedUser = userDao.logUserByEmail(
                email = body.email, password = body.password
            )
            if (loggedUser == null) {
                call.respond(status = HttpStatusCode.BadRequest, message = "User doesn't exist")
            } else {
                call.respond(loggedUser)
            }
        }
        post("/createUser") {
            val body = call.receive<EmailCredentials>()
            if (userDao.checkIfExist(email = body.email)) {
                call.respond(status = HttpStatusCode.BadRequest, message = "User exists")
            } else {
                val created = userDao.createUser(email = body.email, password = body.password)
                if (created == null) {
                    call.respond(
                        status = HttpStatusCode.InternalServerError,
                        message = "Something went wrong"
                    )
                } else {
                    call.respond(created)
                }
            }
        }
        delete("/deleteUser") {
            val id = call.receive<Map<String, Long>>()
            val deleted = userDao.deleteUser(id.get("id"))
            if (deleted) {
                call.respond("Deleted")
            } else {
                call.respond(
                    status = HttpStatusCode.InternalServerError, message = "Something went wrong"
                )
            }
        }
    }
}