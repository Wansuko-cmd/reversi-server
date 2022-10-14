package user

import com.wsr.result.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetUserByIdUseCase(
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend operator fun invoke(id: UserId) =
        withContext(dispatcher) {
            userRepository.getById(id)
                .map { user -> UserUseCaseModel.from(user) }
        }
}