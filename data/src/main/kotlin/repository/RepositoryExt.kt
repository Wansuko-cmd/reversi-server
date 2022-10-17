package repository

import DomainException
import com.wsr.result.ApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.transaction

suspend inline fun <T> runCatchWithTransaction(
    database: Database,
    dispatcher: CoroutineDispatcher,
    noinline block: Transaction.() -> T,
): ApiResult<T, DomainException> = try {
    withContext(dispatcher) {
        transaction(database, block)
    }.let { ApiResult.Success(it) }
} catch (exception: Exception) {
    when (exception) {
        is NoSuchElementException ->
            ApiResult.Failure(
                DomainException.NoSuchElementException(exception.message ?: ""),
            )
        else ->
            ApiResult.Failure(DomainException.SystemException("transaction", exception))
    }
}
