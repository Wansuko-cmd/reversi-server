package user

import DomainException
import com.wsr.result.ApiResult
import com.wsr.result.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetUsersUseCase(
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend operator fun invoke(): ApiResult<List<UserUseCaseModel>, DomainException> =
        withContext(dispatcher) {
            userRepository
                .getAll()
                .map { users -> users.map { UserUseCaseModel.from(it) } }
        }
}
