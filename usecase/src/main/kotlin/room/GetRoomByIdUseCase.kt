package room

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetRoomByIdUseCase(
    private val roomRepository: RoomRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend operator fun invoke(id: RoomId): RoomUseCaseModel =
        withContext(dispatcher) {
            roomRepository.getById(id).let { RoomUseCaseModel.from(it) }
        }
}
