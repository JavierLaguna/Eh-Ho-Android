package io.keepcoding.eh_ho.posts

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.PostsRepo
import io.keepcoding.eh_ho.inflate
import kotlinx.android.synthetic.main.fragment_posts.*
import kotlinx.android.synthetic.main.fragment_posts.swipeRefresh
import kotlinx.android.synthetic.main.fragment_posts.viewError
import kotlinx.android.synthetic.main.fragment_posts.viewLoading
import kotlinx.android.synthetic.main.view_error.*
import java.lang.IllegalArgumentException

class PostsFragment(val topicId: String) : Fragment() {

    var postsInteractionListener: PostsInteractionListener? = null

    private val postsAdapter: PostsAdapter by lazy {
        val adapter = PostsAdapter()
        adapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is PostsInteractionListener) {
            postsInteractionListener = context
        } else {
            throw IllegalArgumentException("Context doesn´t implement ${PostsInteractionListener::class.java.canonicalName}")
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
        return container?.inflate(R.layout.fragment_posts)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listPosts.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listPosts.adapter = postsAdapter

        buttonRetry.setOnClickListener {
            retryLoadPosts()
        }

        swipeRefresh.setOnRefreshListener {
            loadPosts()
        }
    }

    override fun onResume() {
        super.onResume()

        loadPosts()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_create_post, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_create_post -> postsInteractionListener?.onCreatePost()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadPosts() {
        context?.let {
            enableLoading()

            PostsRepo.getPosts(it.applicationContext, topicId, {
                postsAdapter.setPosts(it)
                enableLoading(false)
                swipeRefresh.isRefreshing = false
            }, {
                swipeRefresh.isRefreshing = false
                showError()
            })
        }
    }

    private fun enableLoading(enabled: Boolean = true) {
        if (enabled) {
            listPosts.visibility = View.INVISIBLE
            viewLoading.visibility = View.VISIBLE
        } else {
            listPosts.visibility = View.VISIBLE
            viewLoading.visibility = View.INVISIBLE
        }
    }

    private fun showError(show: Boolean = true) {
        if (show) {
            viewError.visibility = View.VISIBLE
            listPosts.visibility = View.INVISIBLE
            viewLoading.visibility = View.INVISIBLE
        } else {
            viewError.visibility = View.INVISIBLE
        }
    }

    private fun retryLoadPosts() {
        showError(false)
        loadPosts()
    }

    interface PostsInteractionListener {
        fun onCreatePost()
    }
}