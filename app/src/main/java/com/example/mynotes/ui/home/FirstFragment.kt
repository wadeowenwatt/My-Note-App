package com.example.mynotes.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotes.MyNotesApplication
import com.example.mynotes.R
import com.example.mynotes.databinding.FragmentFirstBinding
import com.example.mynotes.ui.DbViewModel
import com.example.mynotes.ui.DbViewModelFactory
import java.text.SimpleDateFormat
import java.util.*


class FirstFragment : Fragment() {

    companion object {
        private val sdf =
            SimpleDateFormat("dd MMM yyyy HH:mm", Locale("en", "VietNam"))
        val CURRENT_TIME: String = sdf.format(Date())
    }

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    private val adapter = MyNoteAdapter()

    private val dbViewModel : DbViewModel by activityViewModels {
        DbViewModelFactory(
            (activity?.application as MyNotesApplication).database.noteDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                searchDatabase(query)
                return true
            }
        })

        dbViewModel.allNotes.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.contentForEmpty.visibility = View.VISIBLE
                binding.fabBin.visibility = View.INVISIBLE
            } else {
                binding.contentForEmpty.visibility = View.INVISIBLE
                binding.fabBin.visibility = View.VISIBLE
            }

            it.let {
                adapter.submitList(it)
                binding.listNote.layoutManager = LinearLayoutManager(requireContext())
                binding.listNote.adapter = adapter
            }
        }

        binding.fabBin.setOnClickListener {
            binding.fabAccept.visibility = View.VISIBLE
            binding.fabBin.visibility = View.INVISIBLE
            binding.fabPlus.visibility = View.INVISIBLE

            // show button bin in item
            dbViewModel.changeViewType()
            Log.e("test", dbViewModel.allNotes.value.toString())
            dbViewModel.allNotes.observe(viewLifecycleOwner) {
                it.let {
                    adapter.submitList(it)
                    binding.listNote.layoutManager = LinearLayoutManager(requireContext())
                    binding.listNote.adapter = adapter
                }
            }
        }

        binding.fabAccept.setOnClickListener {
            binding.fabAccept.visibility = View.INVISIBLE
            binding.fabBin.visibility = View.VISIBLE
            binding.fabPlus.visibility = View.VISIBLE

            // disable del mode
            dbViewModel.changeViewType()
            Log.e("test", dbViewModel.allNotes.value.toString())
            dbViewModel.allNotes.observe(viewLifecycleOwner) {
                it.let {
                    adapter.submitList(it)
                    binding.listNote.layoutManager = LinearLayoutManager(requireContext())
                    binding.listNote.adapter = adapter
                }
            }
        }

        binding.fabPlus.setOnClickListener {
            val action =
                FirstFragmentDirections.actionFirstFragmentToSecondFragment(
                    0,
                    "",
                    "",
                    "create",
                    CURRENT_TIME
                )
            findNavController().navigate(action)
        }

    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        dbViewModel.searchNote(searchQuery).observe(viewLifecycleOwner) {
            it.let {
                adapter.submitList(it)
                binding.listNote.layoutManager = LinearLayoutManager(requireContext())
                binding.listNote.adapter = adapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
