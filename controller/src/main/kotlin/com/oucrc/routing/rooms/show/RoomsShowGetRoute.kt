package com.oucrc.routing.rooms.show

import com.oucrc.ext.getParameter
import com.oucrc.serializable.ExceptionSerializable
import com.oucrc.serializable.RoomSerializable
import com.wsr.result.consume
import com.wsr.result.flatMap
import com.wsr.result.mapBoth
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.koin.ktor.ext.inject
import room.GetRoomByIdUseCase
import room.RoomId

fun Route.roomsShowGet(path: String, param: String) {
    val getRoomByIdUseCase by inject<GetRoomByIdUseCase>()

    get(path) {
        call.getParameter<String>(param)
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
