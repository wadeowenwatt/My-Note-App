package com.example.mynotes.ui.detail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mynotes.MyNotesApplication
import com.example.mynotes.data.MyNote
import com.example.mynotes.databinding.FragmentSecondBinding
import com.example.mynotes.ui.DbViewModel
import com.example.mynotes.ui.DbViewModelFactory
import com.example.mynotes.ui.home.FirstFragment

class SecondFragment : Fragment() {

    companion object {
        var MODE : String = "create"
    }

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    private val dbViewModel : DbViewModel by activityViewModels {
        DbViewModelFactory(
            (activity?.application as MyNotesApplication).database.noteDao()
        )
    }

    lateinit var note: MyNote

    private var nameNote : String = ""
    private var content : String = ""
    private var timeEdit : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        arguments?.let {
            nameNote = it.getString("nameNote").toString()
            content = it.getString("content").toString()
            MODE = it.getString("mode").toString()
            timeEdit = it.getString("timeEdit").toString()
        }
        return binding.root

    }

    private fun isEntryValid(): Boolean {
        return dbViewModel.isEntryValid(
            binding.editNoteName.text.toString(),
            binding.editNote.text.toString(),
            binding.timeWriteNote.text.toString()
        )
    }

    private fun addNewNote() {
        if (isEntryValid()) {
            dbViewModel.addNewNote(
                binding.editNoteName.text.toString(),
                binding.editNote.text.toString(),
                binding.timeWriteNote.text.toString()
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (MODE == "seen") {
            binding.editNoteName.setText(nameNote)
            binding.editNote.setText(content)

            binding.editNote.isEnabled = false
            binding.editNoteName.isEnabled = false

            binding.fabSave.visibility = View.INVISIBLE
            binding.fabSave.isEnabled = true
            binding.fabDeleteCurrentNote.visibility = View.VISIBLE
            binding.fabEditNote.visibility = View.VISIBLE
        }

        binding.timeWriteNote.text = timeEdit

        binding.fabBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.editNoteName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {

            }

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.fabSave.isEnabled = p0!!.isNotEmpty()
            }

        })

        binding.editNote.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
            }

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.fabSave.isEnabled = p0!!.isNotEmpty()
            }
        })

        binding.fabSave.setOnClickListener {
            binding.editNote.isEnabled = false
            binding.editNoteName.isEnabled = false

            binding.fabSave.visibility = View.INVISIBLE
            binding.fabDeleteCurrentNote.visibility = View.VISIBLE
            binding.fabEditNote.visibility = View.VISIBLE

            addNewNote()

            // Update edit time
            binding.timeWriteNote.text = FirstFragment.CURRENT_TIME
        }

        binding.fabEditNote.setOnClickListener {
            binding.editNote.isEnabled = true
            binding.editNoteName.isEnabled = true

            binding.fabSave.visibility = View.VISIBLE
            binding.fabDeleteCurrentNote.visibility = View.INVISIBLE
            binding.fabEditNote.visibility = View.INVISIBLE
        }

        binding.fabDeleteCurrentNote.setOnClickListener {
            binding.fabDeleteCurrentNote.visibility = View.INVISIBLE
            binding.fabEditNote.visibility = View.INVISIBLE
            binding.fabAcceptEdit.visibility = View.VISIBLE
            binding.fabDenyEdit.visibility = View.VISIBLE
        }

        binding.fabAcceptEdit.setOnClickListener {
            findNavController().popBackStack()

            // delete item call
        }

        binding.fabDenyEdit.setOnClickListener {
            binding.fabDeleteCurrentNote.visibility = View.VISIBLE
            binding.fabEditNote.visibility = View.VISIBLE
            binding.fabAcceptEdit.visibility = View.INVISIBLE
            binding.fabDenyEdit.visibility = View.INVISIBLE
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}