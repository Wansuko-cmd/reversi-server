package com.oucrc.routing.room.show

import com.oucrc.serializable.ExceptionSerializable
import com.oucrc.serializable.RoomSerializable
import com.wsr.result.consume
import com.wsr.result.mapBoth
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import room.GetRoomByIdUseCase
import room.RoomId

fun Route.roomShow(path: String, params: String) {
    val getRoomByIdUseCase by inject<GetRoomByIdUseCase>()

    get(path) {
        val roomId = call.parameters[params]
            ?: return@get run { call.respond(HttpStatusCode.BadRequest) }
        getRoomByIdUseCase(RoomId(roomId))
            .mapBoth(
                success = { room -> RoomSerializable.from(room) },
                failure = { ExceptionSerializable.from(it) }
            )
            .consume(
                success = { room -> call.respond(room) },
                failure = { (message, status) -> call.respond(status, message) }
            )
    }
}


