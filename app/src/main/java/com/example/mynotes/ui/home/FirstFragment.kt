package com.example.mynotes.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotes.R
import com.example.mynotes.data.MyNote
import com.example.mynotes.databinding.FragmentFirstBinding
import com.example.mynotes.ui.ActivityViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel : ActivityViewModel by activityViewModels()

    private lateinit var listItem : ArrayList<MyNote>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        listItem = arrayListOf()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabPlus.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        val note1 = MyNote("1", "Note1", "muwhewjhejwhthrhtrtruuthfhgdhgdkhgdjhgfjdhfgjdhfjghdjgh", "sadasd")
        val note2 = MyNote("2", "Note2", "muwhewjhejwhthrhtrtruuthfhgdhgdkhgdjhgfjdhfgjdhfjghdjgh", "sadasd")
        listItem.add(note1)
        listItem.add(note2)

        val adapter = MyNoteAdapter(listItem)

        binding.listNote.layoutManager = LinearLayoutManager(requireContext())
        binding.listNote.adapter = adapter

        if (listItem.isEmpty()) {
            binding.contentForEmpty.visibility = View.VISIBLE
            binding.fabBin.visibility = View.INVISIBLE
        } else {
            binding.contentForEmpty.visibility = View.INVISIBLE
            binding.fabBin.visibility = View.VISIBLE
        }

        binding.fabBin.setOnClickListener {

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}