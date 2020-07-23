package io.keepcoding.eh_ho

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_topic.view.*

class TopicsAdapter(val topicClickListener: ((Topic) -> Unit)?) :
    RecyclerView.Adapter<TopicsAdapter.TopicHolder>() {

    private val topics = mutableListOf<Topic>()

    // Unit == Void
    private val listener: ((View) -> Unit) = {
        if (it.tag is Topic) {
            val topic: Topic = it.tag as Topic
            topicClickListener?.invoke(topic)
        }
    }

    override fun getItemCount(): Int {
        return topics.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicHolder {
        val context = parent.context
        // AttachToRoot -> reemplaza el parent con la vista que se va a inflar
        val view = LayoutInflater.from(context).inflate(R.layout.item_topic, parent, false)
        return TopicHolder(view)
    }

    override fun onBindViewHolder(holder: TopicHolder, position: Int) {
//        val topic = topics.get(position)
        val topic = topics[position]

//        holder.itemView.findViewById<TextView>(R.id.label_topic).setText(topic.title)
        holder.topic = topic

        holder.itemView.setOnClickListener(listener)
    }

    fun setTopics(topics: List<Topic>) {
        this.topics.clear()
        this.topics.addAll(topics)
        notifyDataSetChanged()
    }

    // VIewHolder
    inner class TopicHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var topic: Topic? = null
            set(value) {
                field = value
//                itemView.findViewById<TextView>(R.id.label_topic).text = field?.title
                itemView.labelTopic.text = field?.title

                itemView.tag = field
            }
    }
}