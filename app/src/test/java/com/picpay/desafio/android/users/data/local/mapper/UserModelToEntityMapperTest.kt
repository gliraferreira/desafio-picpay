package com.picpay.desafio.android.users.data.local.mapper

import com.picpay.desafio.android.testcore.factory.UserFactory.makeUser
import org.junit.Test
import kotlin.test.assertEquals

class UserModelToEntityMapperTest {

    private val mapper = UserModelToEntityMapper()

    @Test
    fun mapFrom_withUserModel_returnEntity() {
        val user = makeUser()

        val entity = mapper.mapFrom(user)

        assertEquals(user.id, entity.id)
        assertEquals(user.name, entity.name)
        assertEquals(user.img, entity.img)
        assertEquals(user.username, entity.username)
    }
}