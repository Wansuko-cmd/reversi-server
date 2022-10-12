package com.oucrc.routing.room.show

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.roomShow(path: String, params: String) {
    get(path) {
        val roomId = call.parameters[params] ?: return@get run { call.respond(HttpStatusCode.BadRequest) }
        call.respond(roomId)
    }
}
