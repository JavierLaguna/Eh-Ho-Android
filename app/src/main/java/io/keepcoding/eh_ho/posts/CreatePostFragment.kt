package io.keepcoding.eh_ho.posts

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.CreatePostModel
import io.keepcoding.eh_ho.data.PostsRepo
import io.keepcoding.eh_ho.data.RequestError
import io.keepcoding.eh_ho.inflate
import io.keepcoding.eh_ho.topics.LoadingDialogFragment
import kotlinx.android.synthetic.main.fragment_create_post.*

const val TAG_LOADING_DIALOG_CREATING_POST = "loading_dialog_creating_post"

class CreatePostFragment(val topicId: String, val topicTitle: String) : Fragment() {

    var createPostInteractionListener: CreatePostInteractionListener? = null
    val loadingDialogFragment: LoadingDialogFragment by lazy {
        val message = getString(R.string.label_creating_post)
        LoadingDialogFragment.newInstance(message)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is CreatePostInteractionListener) {
            createPostInteractionListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_create_post)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        labelTopicTitle.text = topicTitle
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_create_topic, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_send -> createPost()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun createPost() {
        if (isFormValid()) {
            sendPost()
        } else {
            showErrors()
        }
    }

    private fun sendPost() {
        enableLoadingDialog()

        val model = CreatePostModel(topicId, inputContent.text.toString())

        context?.let {
            PostsRepo.addPost(it, model, {
                enableLoadingDialog(false)
                createPostInteractionListener?.onPostCreated()
            }, {
                enableLoadingDialog(false)
                handleError(it)
            })
        }
    }

    private fun enableLoadingDialog(enabled: Boolean = true) {
        if (enabled) {
            loadingDialogFragment.show(childFragmentManager, TAG_LOADING_DIALOG_CREATING_POST)
        } else {
            loadingDialogFragment.dismiss()
        }
    }

    private fun handleError(error: RequestError) {
        val message: String = if (error.messageResId != null) {
            getString(error.messageResId)
        } else error.message ?: getString(R.string.error_default)

        Snackbar.make(container, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showErrors() {
        if (inputContent.text.isEmpty()) {
            inputContent.error = getString(R.string.error_empty)
        }
    }

    private fun isFormValid() = inputContent.text.isNotEmpty()

    interface CreatePostInteractionListener {
        fun onPostCreated()
    }
}