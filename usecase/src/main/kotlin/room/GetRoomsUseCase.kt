package room

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetRoomsUseCase(
    private val roomRepository: RoomRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend operator fun invoke(): List<RoomUseCaseModel> =
        withContext(dispatcher) {
            roomRepository.getAll().map { RoomUseCaseModel.from(it) }
        }
}