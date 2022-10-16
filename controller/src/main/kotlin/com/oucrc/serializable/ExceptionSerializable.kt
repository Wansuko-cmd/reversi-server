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
                is DomainException.ValidationException ->
                    ExceptionSerializable(exception.message) to HttpStatusCode.BadRequest
                is DomainException.SystemException ->
                    ExceptionSerializable(exception.message) to HttpStatusCode.InternalServerError
            }
    }
}
