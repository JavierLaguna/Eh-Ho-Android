package io.keepcoding.eh_ho.topics

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.CreateTopicModel
import io.keepcoding.eh_ho.data.RequestError
import io.keepcoding.eh_ho.data.TopicsRepo
import io.keepcoding.eh_ho.data.UserRepo
import io.keepcoding.eh_ho.inflate
import kotlinx.android.synthetic.main.fragment_create_topic.*

const val TAG_LOADING_DIALOG = "loading_dialog"

class CreateTopicFragment : Fragment() {

    var createTopicInteractionListener: CreateTopicInteractionListener? = null
    val loadingDialogFragment: LoadingDialogFragment by lazy {
        val message = getString(R.string.label_creating_topic)
        LoadingDialogFragment.newInstance(message)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is CreateTopicInteractionListener) {
            createTopicInteractionListener = context
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
        return container?.inflate(R.layout.fragment_create_topic)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_create_topic, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_send -> createTopic()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        context?.let {
            labelAuthor.text = UserRepo.getUsername(it)
        }
    }

    private fun createTopic() {
        if (isFormValid()) {
            postTopic()
        } else {
            showErrors()
        }
    }

    private fun postTopic() {

        enableLoadingDialog()

        val model = CreateTopicModel(inputTitle.text.toString(), inputContent.text.toString())

        context?.let {
            TopicsRepo.addTopic(it, model, {
                enableLoadingDialog(false)
                createTopicInteractionListener?.onTopicCreated()
            }, {
                enableLoadingDialog(false)
                handleError(it)
            })
        }
    }

    private fun enableLoadingDialog(enabled: Boolean = true) {
        if (enabled) {
            loadingDialogFragment.show(childFragmentManager, TAG_LOADING_DIALOG)
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
        if (inputTitle.text.isEmpty()) {
            inputTitle.error = getString(R.string.error_empty)
        }

        if (inputContent.text.isEmpty()) {
            inputContent.error = getString(R.string.error_empty)
        }
    }

    private fun isFormValid() = inputTitle.text.isNotEmpty() && inputContent.text.isNotEmpty()

    interface CreateTopicInteractionListener {
        fun onTopicCreated()
    }
}