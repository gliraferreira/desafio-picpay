package com.picpay.desafio.android.users.data.local.mapper

import com.picpay.desafio.android.testcore.factory.UserEntityFactory.makeUserEntity
import org.junit.Test
import kotlin.test.assertEquals

class UserEntityToModelMapperTest {

    private val mapper = UserEntityToModelMapper()

    @Test
    fun mapFrom_withEntity_returnModel() {
        val entity = makeUserEntity()

        val model = mapper.mapFrom(entity)

        assertEquals(entity.id, model.id)
        assertEquals(entity.name, model.name)
        assertEquals(entity.img, model.img)
        assertEquals(entity.username, model.username)
    }
}
