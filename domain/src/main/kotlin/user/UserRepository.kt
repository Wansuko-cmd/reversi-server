package user

import DomainException
import com.wsr.result.ApiResult

interface UserRepository {
    suspend fun getAll(): ApiResult<List<User>, DomainException>
    suspend fun getById(id: UserId): ApiResult<User, DomainException>
    suspend fun insert(user: User): ApiResult<Unit, DomainException>
}
