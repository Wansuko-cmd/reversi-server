import com.wsr.result.ApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import user.User
import user.UserId
import user.UserRepository

class UserRepositoryImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : UserRepository {
    private val users = mutableListOf<User>()

    override suspend fun getAll(): ApiResult<List<User>, DomainException> =
        withContext(dispatcher) {
            ApiResult.Success(users)
        }

    override suspend fun getById(id: UserId): ApiResult<User, DomainException> =
        withContext(dispatcher) {
            users
                .firstOrNull { it.id == id }
                ?.let { ApiResult.Success(it) }
                ?: ApiResult.Failure(DomainException.NoSuchElementException("User"))
        }

    override suspend fun insert(user: User): ApiResult<Unit, DomainException> =
        withContext(dispatcher) {
            users.add(user)
            ApiResult.Success(Unit)
        }
}
