package com.matiz22.richgophishclient.plugins

import auth.model.config_gophish.CreateGophishConfig
import auth.model.config_gophish.EditGophishConfig
import com.matiz22.richgophishclient.dao.UserConfigDao
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.configureUserConfigs(userConfigDao: UserConfigDao) {
    route("/configs") {
        post("/post") {
            val body = call.receive<CreateGophishConfig>()
            val createdConfig = userConfigDao.createConfig(createGophishConfig = body)
            if (createdConfig != null) {
                call.respond(HttpStatusCode.Created, message = createdConfig)
            } else {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
        put("/edit") {
            val body = call.receive<EditGophishConfig>()
            val edited = userConfigDao.editConfig(body)
            if (edited) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotModified)
            }
        }
        get("/user") {
            val body = call.receive<Long>()
            val configs = userConfigDao.getConfigsForUser(body)
            call.respond(HttpStatusCode.OK, message = configs)
        }
        delete {
            val body = call.receive<Long>()
            val deleted = userConfigDao.deleteConfig(body)
            if (deleted) {
                call.respond(HttpStatusCode.OK, message = "Deleted")
            } else {
                call.respond(HttpStatusCode.NotModified)
            }
        }
    }
}