package com.ets.inven.ui.individual.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ets.inven.R

class ChatIndividualRecyclerViewAdapter (
    private val context: Context,
    private val messages: List<String>,
) : RecyclerView.Adapter<ChatIndividualRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView

        init {
            text = view.findViewById(R.id.item_chat_text)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_chat, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val message = messages[position]

        viewHolder.text.text = message
    }

    override fun getItemCount() = messages.size

}