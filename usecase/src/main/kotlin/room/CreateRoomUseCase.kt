package room

import DomainException
import com.wsr.result.ApiResult
import com.wsr.result.flatMap
import com.wsr.result.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import user.User
import user.UserRepository

class CreateRoomUseCase(
    private val roomRepository: RoomRepository,
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend operator fun invoke(): ApiResult<Unit, DomainException> =
        withContext(dispatcher) {
            userRepository
                .getAll()
                .flatMap { users -> users.takeTwoUsers() }
                .map { users -> Room.create(users.first, users.second) }
                .flatMap { roomRepository.insert(it) }
        }

    private fun List<User>.takeTwoUsers(): ApiResult<Pair<User, User>, DomainException> =
        try {
            this.shuffled()
                .take(2)
                .let { it[0] to it[1] }
                .let { ApiResult.Success(it) }
        } catch (e: IndexOutOfBoundsException) {
            ApiResult.Failure(DomainException.SystemException("", e))
        } catch (e: Exception) {
            ApiResult.Failure(DomainException.SystemException("", e))
        }
}
