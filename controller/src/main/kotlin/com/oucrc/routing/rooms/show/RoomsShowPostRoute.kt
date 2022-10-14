package com.oucrc.routing.rooms.show

import com.oucrc.ext.getParameter
import com.oucrc.ext.getRequest
import com.oucrc.serializable.ExceptionSerializable
import com.wsr.result.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.SerialName
import org.koin.ktor.ext.inject
import room.RoomId
import room.PlacePieceInRoomUseCase
import user.UserId

fun Route.roomsShowPost(path: String, param: String) {
    val placePieceInRoomUseCase by inject<PlacePieceInRoomUseCase>()
    post(path) {
        call.getParameter<String>(param, errorMessage = "Invalid room id.")
            .flatMap { id ->
                call.getRequest<RoomShowPostRequest>(errorMessage = "Invalid request.")
                    .map { (userId, row, column) ->
                        placePieceInRoomUseCase(RoomId(id), row, column, UserId(userId))
                    }
            }
            .mapFailure { ExceptionSerializable.from(it) }
            .consume(
                success = { call.respond(HttpStatusCode.OK) },
                failure = { (message, status) -> call.respond(status, message) }
            )
    }
}

@kotlinx.serialization.Serializable
data class RoomShowPostRequest(
    @SerialName("user_id") val userId: String,
    val row: Int,
    val column: Int,
)

