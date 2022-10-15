package repository

import DomainException
import com.wsr.result.ApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import table.UserModel
import user.User
import user.UserId
import user.UserName
import user.UserRepository

class UserRepositoryImpl(
    private val database: Database,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : UserRepository {
    override suspend fun getAll(): ApiResult<List<User>, DomainException> =
        runCatchWithTransaction(database, dispatcher) {
            UserModel
                .selectAll()
                .map { it.toUser() }
        }

    override suspend fun getById(id: UserId): ApiResult<User, DomainException> =
        runCatchWithTransaction(database, dispatcher) {
            UserModel
                .select { UserModel.id eq id.value }
                .first()
                .toUser()
        }

    override suspend fun insert(user: User): ApiResult<Unit, DomainException> =
        runCatchWithTransaction(database, dispatcher) {
            UserModel
                .insert {
                    it[id] = user.id.value
                    it[name] = user.name.value
                }
        }
}

fun ResultRow.toUser() = User.reconstruct(
    id = UserId(this[UserModel.id]),
    name = UserName(this[UserModel.name]),
)