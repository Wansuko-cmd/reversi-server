package com.oucrc.routing.rooms.show

import com.oucrc.ext.getParameter
import com.oucrc.serializable.ExceptionSerializable
import com.oucrc.serializable.RoomSerializable
import com.wsr.result.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import room.GetRoomByIdUseCase
import room.RoomId

fun Route.roomsShowGet(path: String, params: String) {
    val getRoomByIdUseCase by inject<GetRoomByIdUseCase>()

    get(path) {
        call.getParameter<String>(params, errorMessage = "Invalid room id.")
            .flatMap { id -> getRoomByIdUseCase(RoomId(id)) }
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
