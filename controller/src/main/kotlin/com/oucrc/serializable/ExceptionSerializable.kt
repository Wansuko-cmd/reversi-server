package com.oucrc.serializable

import DomainException
import io.ktor.http.HttpStatusCode

@kotlinx.serialization.Serializable
class ExceptionSerializable(
    val message: String,
) {
    companion object {
        fun from(exception: DomainException): Pair<ExceptionSerializable, HttpStatusCode> =
            when (exception) {
                is DomainException.NoSuchElementException ->
                    ExceptionSerializable(exception.message) to HttpStatusCode.BadRequest
                is DomainException.FinishedGameException ->
                    ExceptionSerializable(exception.message) to HttpStatusCode.Conflict
                is DomainException.NotPlaceableCoordinateException ->
                    ExceptionSerializable(exception.message) to HttpStatusCode.UnprocessableEntity
                is DomainException.NoMoreWaitMattingUserException ->
                    ExceptionSerializable(exception.message) to HttpStatusCode.Conflict
                is DomainException.RequestValidationException ->
                    ExceptionSerializable(exception.message) to HttpStatusCode.BadRequest
                is DomainException.SystemException ->
                    ExceptionSerializable(exception.message) to HttpStatusCode.InternalServerError
            }
    }
}
