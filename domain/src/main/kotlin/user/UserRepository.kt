package user

interface UserRepository {
    suspend fun getAll(): List<User>
    suspend fun getById(id: UserId): User
    suspend fun insert(user: User)
}