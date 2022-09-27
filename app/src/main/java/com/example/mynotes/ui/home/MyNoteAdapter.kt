package com.example.mynotes.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.data.MyNote

class MyNoteAdapter :
    ListAdapter<MyNote, MyNoteAdapter.MyNoteViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<MyNote>() {
            override fun areItemsTheSame(oldItem: MyNote, newItem: MyNote): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: MyNote, newItem: MyNote): Boolean {
                return oldItem.nameNote == newItem.nameNote
            }
        }
    }

    class MyNoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameNote: TextView = itemView.findViewById(R.id.name_of_note)
        val previewNote: TextView = itemView.findViewById(R.id.preview_note)
        val layout: LinearLayout = itemView.findViewById(R.id.big_layout)
        val btnDel: ImageView = itemView.findViewById(R.id.delete_button)
        val btnAccept: ImageView = itemView.findViewById(R.id.accept_button)
        val btnDeny: ImageView = itemView.findViewById(R.id.deny_button)
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
        val element = getItem(position)
        holder.nameNote.text = element.nameNote
        holder.previewNote.text = element.content
        holder.layout.setOnClickListener {
            val action =
                FirstFragmentDirections.actionFirstFragmentToSecondFragment(
                    element.id,
                    element.nameNote,
                    element.content.toString(),
                    "seen",
                    element.timeEdit.toString()
                )
            it.findNavController().navigate(action)
        }


        holder.btnDel.visibility = View.VISIBLE
        holder.btnDel.setOnClickListener {
            holder.btnDel.visibility = View.INVISIBLE
            holder.btnAccept.visibility = View.VISIBLE
            holder.btnDeny.visibility = View.VISIBLE
        }

        holder.btnDeny.setOnClickListener {
            holder.btnDel.visibility = View.VISIBLE
            holder.btnAccept.visibility = View.INVISIBLE
            holder.btnDeny.visibility = View.INVISIBLE
        }

    }
}


