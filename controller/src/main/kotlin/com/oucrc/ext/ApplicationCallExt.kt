package com.oucrc.ext

import DomainException
import com.wsr.result.ApiResult
import io.ktor.server.application.ApplicationCall
import io.ktor.server.plugins.*
import io.ktor.server.request.*

inline fun <reified T> ApplicationCall.getParameter(
    param: String,
    errorMessage: String = "validation error",
): ApiResult<T, DomainException> =
    (this.parameters[param] as? T)
        ?.let { ApiResult.Success(it) }
        ?: ApiResult.Failure(DomainException.ValidationException(errorMessage))

suspend inline fun <reified T: Any> ApplicationCall.getRequest(
    errorMessage: String = "validation error",
): ApiResult<T, DomainException> =
    try {
        ApiResult.Success(this.receive())
    } catch (e: CannotTransformContentToTypeException) {
        ApiResult.Failure(DomainException.ValidationException(errorMessage))
    }