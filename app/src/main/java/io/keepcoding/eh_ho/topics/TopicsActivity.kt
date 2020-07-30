package io.keepcoding.eh_ho.topics

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.keepcoding.eh_ho.*
import io.keepcoding.eh_ho.data.Topic
import io.keepcoding.eh_ho.data.TopicsRepo
import io.keepcoding.eh_ho.data.UserRepo
import io.keepcoding.eh_ho.login.LoginActivity

const val TRANSACTION_CREATE_TOPIC = "create_topic"

class TopicsActivity : AppCompatActivity(), TopicsFragment.TopicsInteractionListener,
    CreateTopicFragment.CreateTopicInteractionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topics)

        Log.d(TopicsActivity::class.simpleName, TopicsRepo.topics.toString())

        // val list: RecyclerView = findViewById(R.id.list_topics)
        // list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

//        val adapter = TopicsAdapter {
//            goToPosts(it)
//        }
//        adapter.setTopics(TopicsRepo.topics)
//
//        listTopics.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        listTopics.adapter = adapter

        if (isFirstTimeCreated(savedInstanceState)) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, TopicsFragment())
                .commit()
        }
    }

    fun goToPosts(topic: Topic) {
        val intent = Intent(this, PostsActivity::class.java)
        intent.putExtra(EXTRA_TOPIC_ID, topic.id)
        startActivity(intent)
    }

    // TopicsInteractionListener
    override fun onCreateTopic() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, CreateTopicFragment())
            .addToBackStack(TRANSACTION_CREATE_TOPIC)
            .commit()
    }

    override fun onShowPosts(topic: Topic) {
        goToPosts(topic)
    }

    override fun onLogout() {
        UserRepo.logout(applicationContext)

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Esta actividad se destruye y no se queda en el backstack de actividades
    }

    // CreateTopicInteractionListener
    override fun onTopicCreated() {
        supportFragmentManager.popBackStack()
    }
}