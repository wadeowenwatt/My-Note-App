package com.example.mynotes.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.data.MyNote

class MyNoteAdapter(
    val enableDeleteMode: (note: MyNote) -> Unit,
    val acceptDeleteMode: (note: MyNote) -> Unit
) :
    ListAdapter<MyNote, RecyclerView.ViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<MyNote>() {
            override fun areItemsTheSame(
                oldItem: MyNote,
                newItem: MyNote
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: MyNote,
                newItem: MyNote
            ): Boolean {
                return oldItem.nameNote == newItem.nameNote
            }
        }
    }

    class MyNoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameNote: TextView = itemView.findViewById(R.id.name_of_note)
        val previewNote: TextView = itemView.findViewById(R.id.preview_note)
        val layout: LinearLayout = itemView.findViewById(R.id.big_layout)
    }

    class MyNoteViewHolder1(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val nameNote: TextView = itemView.findViewById(R.id.name_of_note)
        val previewNote: TextView = itemView.findViewById(R.id.preview_note)
        val btnDel: ImageButton = itemView.findViewById(R.id.delete_button)
    }

    class MyNoteViewHolder2(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val nameNote: TextView = itemView.findViewById(R.id.name_of_note)
        val previewNote: TextView = itemView.findViewById(R.id.preview_note)
        val btnAccept: ImageButton = itemView.findViewById(R.id.accept_button)
        val btnDeny: ImageButton = itemView.findViewById(R.id.deny_button)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_note_preview, parent, false)
                MyNoteViewHolder(itemView)
            }
            1 -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_note_preview_1, parent, false)
                MyNoteViewHolder1(itemView)
            }
            else -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_note_preview_2, parent, false)
                MyNoteViewHolder2(itemView)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val element = getItem(position)
        return element.viewType
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val element = getItem(position)

        when (holder) {
            is MyNoteViewHolder -> {
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
            }
            is MyNoteViewHolder1 -> {
                holder.nameNote.text = element.nameNote
                holder.previewNote.text = element.content
                holder.btnDel.setOnClickListener {
                    enableDeleteMode(element)
                    Log.e("test click:", "click")
                }
            }
            is MyNoteViewHolder2 -> {
                holder.nameNote.text = element.nameNote
                holder.previewNote.text = element.content
                holder.btnAccept.setOnClickListener {
                    acceptDeleteMode(element)
                    Log.e("test click acc:", "click")
                }
                holder.btnDeny.setOnClickListener {
                    Log.e("test click deny:", "click")
                }
            }
        }
    }
}


