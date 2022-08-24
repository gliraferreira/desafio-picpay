package com.picpay.desafio.android.users.data.remote.mapper

import com.picpay.desafio.android.testcore.factory.UserResponseFactory.makeUserResponse
import org.junit.Test
import kotlin.test.assertEquals

class UserResponseToModelMapperTest {

    private val mapper = UserResponseToModelMapper()

    @Test
    fun mapFrom_withResponse_returnModel() {
        val response = makeUserResponse()

        val model = mapper.mapFrom(response)

        assertEquals(response.id, model.id)
        assertEquals(response.name, model.name)
        assertEquals(response.img, model.img)
        assertEquals(response.username, model.username)
    }
}
