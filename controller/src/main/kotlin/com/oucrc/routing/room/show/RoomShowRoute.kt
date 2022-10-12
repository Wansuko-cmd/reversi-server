package com.oucrc.routing.room.show

import com.oucrc.serializable.RoomSerializable
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
            .let { RoomSerializable.from(it) }
            .also { call.respond(it) }
    }
}


