package com.oucrc.routing.rooms.index

import com.oucrc.serializable.ExceptionSerializable
import com.oucrc.serializable.RoomSerializable
import com.oucrc.serializable.UserSerializable
import com.wsr.result.consume
import com.wsr.result.mapBoth
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import room.GetRoomsUseCase
import room.RoomUseCaseModel

fun Route.roomsIndexGet(path: String) {
    val getRoomsUseCase by inject<GetRoomsUseCase>()

    get(path) {
        getRoomsUseCase()
            .mapBoth(
                success = { rooms -> rooms.map { RoomSerializable.from(it) } },
                failure = { ExceptionSerializable.from(it) }
            )
            .consume(
                success = { rooms -> call.respond(rooms) },
                failure = { (message, status) -> call.respond(status, message) }
            )
    }
}
