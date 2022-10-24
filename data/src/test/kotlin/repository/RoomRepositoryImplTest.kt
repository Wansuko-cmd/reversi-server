package repository

import TestDB
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RoomRepositoryImplTest {
    private val target = RoomRepositoryImpl(TestDB)

    @Test
    fun test() = runTest {
        println(target.getAll())
    }
}
