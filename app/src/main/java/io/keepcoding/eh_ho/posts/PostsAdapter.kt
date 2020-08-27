package io.keepcoding.eh_ho.posts

import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.Post
import io.keepcoding.eh_ho.inflate
import kotlinx.android.synthetic.main.item_post.view.*

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.PostHolder>() {

    private val posts = mutableListOf<Post>()

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsAdapter.PostHolder {
        val view = parent.inflate(R.layout.item_post)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostsAdapter.PostHolder, position: Int) {
        val post = posts[position]

        holder.post = post
    }

    fun setPosts(posts: List<Post>) {
        this.posts.clear()
        this.posts.addAll(posts)
        notifyDataSetChanged()
    }

    // ViewHolder
    inner class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var post: Post? = null
            set(value) {
                field = value
                itemView.tag = field

                field?.let { post ->
                    itemView.labelAuthor.text = post.author
                    itemView.labelDate.text = post.formattedDate()
                    itemView.labelContent.text =
                        HtmlCompat.fromHtml(post.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
                }
            }
    }
}