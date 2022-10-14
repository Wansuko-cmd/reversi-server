package com.oucrc.routing.room.show

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
import room.UpdateRoomUseCase
import user.UserId

fun Route.roomShowPost(path: String, params: String) {
    val updateRoomUseCase by inject<UpdateRoomUseCase>()
    post(path) {
        call.getParameter<String>(params, errorMessage = "Invalid room id.")
            .flatMap { id ->
                call.getRequest<RoomShowPostRequest>(errorMessage = "Invalid request.")
                    .map { (userId, row, column) ->
                        updateRoomUseCase(RoomId(id), row, column, UserId(userId))
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

