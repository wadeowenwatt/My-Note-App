package com.example.mynotes.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.data.MyNote

class MyNoteAdapter(private val listItem: ArrayList<MyNote>) :
    RecyclerView.Adapter<MyNoteAdapter.MyNoteViewHolder>() {

    class MyNoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameNote: TextView = itemView.findViewById(R.id.name_of_note)
        val previewNote: TextView = itemView.findViewById(R.id.preview_note)
        val layout: LinearLayout = itemView.findViewById(R.id.big_layout)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyNoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note_preview, parent, false)
        return MyNoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyNoteViewHolder, position: Int) {
        val element = listItem[position]
        holder.nameNote.text = element.nameNote
        holder.previewNote.text = element.content
        holder.layout.setOnClickListener {

        }
    }

    override fun getItemCount(): Int = listItem.size
}