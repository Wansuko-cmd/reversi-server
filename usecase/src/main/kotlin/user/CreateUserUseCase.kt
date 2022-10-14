package user

import DomainException
import com.wsr.result.ApiResult
import com.wsr.result.flatMap
import com.wsr.result.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateUserUseCase(
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend operator fun invoke(userName: UserName): ApiResult<User, DomainException> =
        withContext(dispatcher) {
            ApiResult.Success(User.create(userName))
                .flatMap { user -> userRepository.insert(user).map { user } }
        }
}