package io.keepcoding.eh_ho.topics

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.Topic
import io.keepcoding.eh_ho.data.TopicsRepo
import io.keepcoding.eh_ho.inflate
import kotlinx.android.synthetic.main.fragment_topics.*
import kotlinx.android.synthetic.main.fragment_topics.viewLoading
import java.lang.IllegalArgumentException

class TopicsFragment : Fragment() {

    var topicsInteractionListener: TopicsInteractionListener? = null

    private val topicsAdapter: TopicsAdapter by lazy {
        val adapter = TopicsAdapter {
            topicsInteractionListener?.onShowPosts(it)
        }
        adapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is TopicsInteractionListener) {
            topicsInteractionListener = context
        } else {
            throw IllegalArgumentException("Context doesn´t implement ${TopicsInteractionListener::class.java.canonicalName}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_topics)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonCreate.setOnClickListener {
            topicsInteractionListener?.onCreateTopic()
        }

        buttonRetry.setOnClickListener {
            retryLoadTopics()
        }

        listTopics.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listTopics.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        listTopics.adapter = topicsAdapter
    }

    override fun onResume() {
        super.onResume()

        loadTopics()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_topics, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> topicsInteractionListener?.onLogout()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDetach() {
        super.onDetach()

        topicsInteractionListener = null
    }

    private fun loadTopics() {
        context?.let {
            enableLoading()
            TopicsRepo.getTopics(it.applicationContext, {
                topicsAdapter.setTopics(it)
                enableLoading(false)
            }, { error ->
                showError()
            })
        }
    }

    private fun enableLoading(enabled: Boolean = true) {
        if (enabled) {
            listTopics.visibility = View.INVISIBLE
            buttonCreate.visibility = View.INVISIBLE
            viewLoading.visibility = View.VISIBLE
        } else {
            listTopics.visibility = View.VISIBLE
            buttonCreate.visibility = View.VISIBLE
            viewLoading.visibility = View.INVISIBLE
        }
    }

    private fun showError(show: Boolean = true) {
        if (show) {
            viewError.visibility = View.VISIBLE
            buttonCreate.visibility = View.VISIBLE
            listTopics.visibility = View.INVISIBLE
            viewLoading.visibility = View.INVISIBLE
        } else {
            viewError.visibility = View.GONE
        }
    }

    private fun retryLoadTopics() {
        showError(false)
        loadTopics()
    }

    interface TopicsInteractionListener {
        fun onCreateTopic()
        fun onShowPosts(topic: Topic)
        fun onLogout()
    }
}