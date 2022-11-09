package com.oucrc.routing.scores.show

import com.oucrc.ext.getParameter
import com.oucrc.serializable.ExceptionSerializable
import com.oucrc.serializable.ScoreSerializable
import com.wsr.result.consume
import com.wsr.result.flatMap
import com.wsr.result.map
import com.wsr.result.mapBoth
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import score.GetScoreByUserIdUseCase
import user.UserId

fun Route.scoreShowGetRoute(path: String, param: String) {
    val getScoreByIdUseCase by inject<GetScoreByUserIdUseCase>()

    get(path) {
        call.getParameter<String>(param, "Invalid parameter")
            .map { UserId(it) }
            .flatMap { userId -> getScoreByIdUseCase(userId) }
            .mapBoth(
                success = { score -> score.map { ScoreSerializable.from(it) } },
                failure = { ExceptionSerializable.from(it) },
            )
            .consume(
                success = { score -> call.respond(score) },
                failure = { (message, status) -> call.respond(status, message) },
            )
    }
}