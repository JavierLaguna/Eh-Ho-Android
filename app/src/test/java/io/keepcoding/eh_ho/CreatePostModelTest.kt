package io.keepcoding.eh_ho

import io.keepcoding.eh_ho.data.CreatePostModel
import org.junit.Assert
import org.junit.Test

class CreatePostModelTest {

    @Test
    fun toJson_isCorrect() {
        val model = CreatePostModel("1", "Content")
        val json = model.toJson()

        Assert.assertEquals("1", json.get("topic_id"))
        Assert.assertEquals("Content", json.get("raw"))
    }
}