package com.oucrc.ext

import DomainException
import com.wsr.result.ApiResult
import io.ktor.server.application.ApplicationCall

inline fun <reified T> ApplicationCall.getParameter(
    param: String,
    errorMessage: String = "validation error",
): ApiResult<T, DomainException> =
    (this.parameters[param] as? T)
        ?.let { ApiResult.Success(it) }
        ?: ApiResult.Failure(DomainException.ValidationException(errorMessage))
