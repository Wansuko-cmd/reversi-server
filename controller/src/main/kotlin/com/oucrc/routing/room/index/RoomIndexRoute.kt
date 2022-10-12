package com.oucrc.routing.room.index

import com.oucrc.serializable.RoomSerializable
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import room.GetRoomsUseCase

fun Route.roomIndex(path: String) {
    val getRoomsUseCase by inject<GetRoomsUseCase>()

    get(path) {
        getRoomsUseCase()
            .map { RoomSerializable.from(it) }
            .also { call.respond(it) }
    }
}
