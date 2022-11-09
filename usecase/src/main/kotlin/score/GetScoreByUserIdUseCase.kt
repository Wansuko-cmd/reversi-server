package score

import DomainException
import com.wsr.result.ApiResult
import com.wsr.result.flatMap
import com.wsr.result.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import room.Room
import room.RoomRepository
import user.UserId
import user.UserRepository
import user.UserUseCaseModel

class GetScoreByUserIdUseCase(
    private val userRepository: UserRepository,
    private val roomRepository: RoomRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend operator fun invoke(userId: UserId): ApiResult<List<ScoreUseCaseModel>, DomainException> =
        withContext(dispatcher) {
            isExistUser(userId)
                .flatMap { roomRepository.getAll() }
                .filterFinishedRoom()
                .filterOnlyRoomsJoinedSpecifiedUser(userId)
                .calculateScore(userId)
        }

    private suspend fun isExistUser(userId: UserId): ApiResult<Boolean, DomainException> =
        userRepository.getById(userId).map { true }

    private fun ApiResult<List<Room>, DomainException>.filterFinishedRoom(): ApiResult<List<Room>, DomainException> =
        this.map { rooms -> rooms.filter { it.next == null } }

    private fun ApiResult<List<Room>, DomainException>.filterOnlyRoomsJoinedSpecifiedUser(
        userId: UserId,
    ): ApiResult<List<Room>, DomainException> =
        this.map { rooms ->
            rooms.filter { room ->
                room.black.id == userId || room.white.id == userId
            }
        }

    private fun ApiResult<List<Room>, DomainException>.calculateScore(
        userId: UserId,
    ): ApiResult<List<ScoreUseCaseModel>, DomainException> =
        this.map { rooms ->
            rooms
                .groupBy { it.opponent(userId) }
                .map { (opponent, groupedRooms) ->
                    groupedRooms
                        .map { it.winner() }
                        .let { winners ->
                            ScoreUseCaseModel(
                                opponent = UserUseCaseModel.from(opponent),
                                win = winners.filterNotNull().count { it.id == userId },
                                lose = winners.filterNotNull().count { it.id != userId },
                                draw = winners.count { it == null },
                            )
                        }
                }
        }

    private fun Room.opponent(userId: UserId) = if (this.black.id == userId) white else black
}

data class ScoreUseCaseModel(
    val opponent: UserUseCaseModel,
    val win: Int,
    val lose: Int,
    val draw: Int,
)
