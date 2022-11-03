package com.oucrc.routing.rooms.index

import com.oucrc.serializable.ExceptionSerializable
import com.wsr.result.consume
import com.wsr.result.mapFailure
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import org.koin.ktor.ext.inject
import room.CreateRoomUseCase

fun Route.roomsIndexPost(path: String) {
    val createRoomUseCase by inject<CreateRoomUseCase>()

    post(path) {
        createRoomUseCase()
            .mapFailure { ExceptionSerializable.from(it) }
            .consume(
                success = { call.respond(it) },
                failure = { (message, status) -> call.respond(status, message) },
            )
    }
}
