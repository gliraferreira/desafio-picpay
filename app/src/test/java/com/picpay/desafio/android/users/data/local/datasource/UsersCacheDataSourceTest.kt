package com.picpay.desafio.android.users.data.local.datasource

import com.picpay.desafio.android.testcore.MainDispatcherRule
import com.picpay.desafio.android.testcore.factory.UserEntityFactory.makeUserEntity
import com.picpay.desafio.android.testcore.factory.UserFactory.makeUser
import com.picpay.desafio.android.users.data.local.database.UsersDao
import com.picpay.desafio.android.users.data.local.database.entity.UserEntity
import com.picpay.desafio.android.users.data.local.mapper.UserEntityToModelMapper
import com.picpay.desafio.android.users.data.local.mapper.UserModelToEntityMapper
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class UsersCacheDataSourceTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val usersDao: UsersDao = mockk()
    private val userModelToEntityMapper: UserModelToEntityMapper = mockk()
    private val userEntityToModelMapper: UserEntityToModelMapper = mockk()
    private val dataSource = UsersCacheDataSource(
        usersDao = usersDao,
        userModelToEntityMapper = userModelToEntityMapper,
        userEntityToModelMapper = userEntityToModelMapper
    )

    @Test
    fun saveUsers_withUsersList_callMapper() = runTest {
        prepareScenario()
        val user1 = makeUser()
        val user2 = makeUser()
        val user3 = makeUser()
        val users = listOf(
            user1,
            user2,
            user3
        )

        dataSource.saveUsers(users)

        verifyOrder {
            userModelToEntityMapper.mapFrom(user1)
            userModelToEntityMapper.mapFrom(user2)
            userModelToEntityMapper.mapFrom(user3)
        }
    }

    @Test
    fun saveUsers_withUsersList_callDao() = runTest {
        val users = listOf(
            makeUser(),
            makeUser(),
            makeUser()
        )
        prepareScenario()

        dataSource.saveUsers(users)

        coVerify {
            usersDao.insertAll(
                users = withArg {
                    assertEquals(users.size, it.size)
                }
            )
        }
    }

    @Test
    fun getUsers_withSuccessResult_callMapper() = runTest {
        val entity1 = makeUserEntity()
        val entity2 = makeUserEntity()
        val entity3 = makeUserEntity()
        val entities = listOf(
            entity1,
            entity2,
            entity3
        )
        prepareScenario(
            userEntityList = entities
        )

        val users = dataSource.getUsers()

        verifyOrder {
            userEntityToModelMapper.mapFrom(entity1)
            userEntityToModelMapper.mapFrom(entity2)
            userEntityToModelMapper.mapFrom(entity3)
        }
    }

    @Test
    fun getUsers_withSuccessResult_returnListFromDao() = runTest {
        val entities = listOf(
            makeUserEntity(),
            makeUserEntity(),
            makeUserEntity()
        )
        prepareScenario(
            userEntityList = entities
        )

        val users = dataSource.getUsers()

        assertEquals(entities.size, users.size)
    }

    private fun prepareScenario(
        userEntityList: List<UserEntity> = listOf(makeUserEntity())
    ) {
        every { userModelToEntityMapper.mapFrom(any()) } returns makeUserEntity()
        every { userEntityToModelMapper.mapFrom(any()) } returns makeUser()

        coEvery { usersDao.insertAll(any()) } just runs
        coEvery { usersDao.getAll() } returns userEntityList
    }
}