package com.wsr.result

sealed class ApiResult<out T, out E> {
    data class Success<T>(val value: T) : ApiResult<T, Nothing>()
    data class Failure<E>(val value: E) : ApiResult<Nothing, E>()
}

inline fun <T, E, NT, NE> ApiResult<T, E>.mapBoth(
    success: (T) -> NT,
    failure: (E) -> NE,
): ApiResult<NT, NE> =
    when (this) {
        is ApiResult.Success -> ApiResult.Success(success(value))
        is ApiResult.Failure -> ApiResult.Failure(failure(value))
    }

inline fun <T, E, NT> ApiResult<T, E>.map(block: (T) -> NT): ApiResult<NT, E> =
    when (this) {
        is ApiResult.Success -> ApiResult.Success(block(value))
        is ApiResult.Failure -> this
    }

inline fun <T, E, NE> ApiResult<T, E>.mapFailure(block: (E) -> NE): ApiResult<T, NE> =
    when (this) {
        is ApiResult.Success -> this
        is ApiResult.Failure -> ApiResult.Failure(block(value))
    }

inline fun <T, E> ApiResult<T, E>.consume(
    success: (T) -> Unit = {},
    failure: (E) -> Unit = {},
) {
    when (this) {
        is ApiResult.Success -> success(value)
        is ApiResult.Failure -> failure(value)
    }
}

fun <T, E> List<ApiResult<T, E>>.sequence(): ApiResult<List<T>, E> {
    val result = mutableListOf<T>()
    for (element in this) {
        when (element) {
            is ApiResult.Success -> result.add(element.value)
            is ApiResult.Failure -> return ApiResult.Failure(element.value)
        }
    }
    return ApiResult.Success(result)
}
