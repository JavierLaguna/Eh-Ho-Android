package io.keepcoding.eh_ho

import io.keepcoding.eh_ho.data.Post
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test

import java.text.SimpleDateFormat

class PostModelTest {

    @Test
    fun fromJson_isCorrect() {
        val stringPost =
            "{\"id\":1253,\"name\":\"Gerard Rico\",\"username\":\"gricob\",\"avatar_template\":\"\\/letter_avatar_proxy\\/v4\\/letter\\/g\\/6a8cbe\\/{size}.png\",\"created_at\":\"2020-09-05T11:06:09.325Z\",\"cooked\":\"<p>Funciona so pongo al menos 20 caracteres?<\\/p>\",\"post_number\":6,\"post_type\":1,\"updated_at\":\"2020-09-05T11:06:09.325Z\",\"reply_count\":0,\"reply_to_post_number\":null,\"quote_count\":0,\"incoming_link_count\":0,\"reads\":1,\"score\":0.2,\"yours\":false,\"topic_id\":851,\"topic_slug\":\"nueva-creacion-de-prueba-martes\",\"display_username\":\"Gerard Rico\",\"primary_group_name\":null,\"primary_group_flair_url\":null,\"primary_group_flair_bg_color\":null,\"primary_group_flair_color\":null,\"version\":1,\"can_edit\":false,\"can_delete\":false,\"can_recover\":false,\"can_wiki\":false,\"read\":true,\"user_title\":null,\"actions_summary\":[],\"moderator\":false,\"admin\":false,\"staff\":false,\"user_id\":85,\"hidden\":false,\"trust_level\":0,\"deleted_at\":null,\"user_deleted\":false,\"edit_reason\":null,\"can_view_edit_history\":true,\"wiki\":false}"

        val json = JSONObject(stringPost)

        val post = Post.parsePost(json)

        val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        val dateFormatted = formatter.format(post.date)

        Assert.assertEquals("1253", post.id)
        Assert.assertEquals("gricob", post.author)
        Assert.assertEquals("<p>Funciona so pongo al menos 20 caracteres?</p>", post.content)
        Assert.assertEquals("05/09/2020 01:06:09", dateFormatted)
    }
}

