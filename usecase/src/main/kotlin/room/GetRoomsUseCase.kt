package room

import DomainException
import com.wsr.result.ApiResult
import com.wsr.result.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetRoomsUseCase(
    private val roomRepository: RoomRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend operator fun invoke(): ApiResult<List<RoomUseCaseModel>, DomainException> =
        withContext(dispatcher) {
            roomRepository.getAll().map { rooms ->
                rooms.map { RoomUseCaseModel.from(it) }
            }
        }
}
