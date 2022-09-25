package com.example.mynotes.ui.detail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mynotes.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    companion object {
        var MODE : String = ""
    }

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    private val detailViewModel: DetailViewModel by viewModels()

    private var nameNote : String = ""
    private var content : String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        arguments?.let {
            nameNote = it.getString("nameNote").toString()
            content = it.getString("content").toString()
            MODE = it.getString("mode").toString()
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // move to viewModel
//        if (binding.editNote.text.isNotEmpty() or binding.editNoteName.text.isNotEmpty()) {
//            binding.fabSave.isEnabled = true
//        }

        if (MODE == "seen") {
            binding.editNoteName.setText(nameNote)
            binding.editNote.setText(content)

            binding.editNote.isEnabled = false
            binding.editNoteName.isEnabled = false

            binding.fabSave.visibility = View.INVISIBLE
            binding.fabDeleteCurrentNote.visibility = View.VISIBLE
            binding.fabEditNote.visibility = View.VISIBLE
        }

        binding.fabBack.setOnClickListener {
            findNavController().popBackStack()
        }

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

            // delete item
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