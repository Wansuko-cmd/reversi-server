package room

import DomainException
import com.wsr.result.ApiResult
import com.wsr.result.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetRoomByIdUseCase(
    private val roomRepository: RoomRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend operator fun invoke(id: RoomId): ApiResult<RoomUseCaseModel, DomainException> =
        withContext(dispatcher) {
            roomRepository.getById(id)
                .map { room -> RoomUseCaseModel.from(room) }
        }
}
