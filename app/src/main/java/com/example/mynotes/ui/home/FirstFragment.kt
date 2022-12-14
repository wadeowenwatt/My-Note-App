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

        private var HOME_STATE = "Default"
    }

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: MyNoteAdapter

    private val dbViewModel: DbViewModel by activityViewModels {
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
        // Search listen
        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                searchDatabase(query)
                return true
            }
        })

        // Observe List data and show
        dbViewModel.allNotes.observe(viewLifecycleOwner) { it ->

            if (HOME_STATE == "Default") {
                if (it.isNullOrEmpty()) {
                    binding.contentForEmpty.visibility = View.VISIBLE
                    binding.fabBin.visibility = View.INVISIBLE

                    binding.fabAccept.visibility = View.INVISIBLE
                    binding.fabPlus.visibility = View.VISIBLE
                } else {
                    binding.contentForEmpty.visibility = View.INVISIBLE
                    binding.fabBin.visibility = View.VISIBLE

                    binding.fabAccept.visibility = View.INVISIBLE
                    binding.fabPlus.visibility = View.VISIBLE
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

            } else if (HOME_STATE == "Delete") {
                binding.fabAccept.visibility = View.VISIBLE
                binding.fabBin.visibility = View.INVISIBLE
                binding.fabPlus.visibility = View.INVISIBLE
            }

            it.let {
                adapter.submitList(it)
                binding.listNote.layoutManager =
                    LinearLayoutManager(requireContext())
                binding.listNote.adapter = adapter
            }
        }

        // this code make show hint always in searchview and not focus it (disable block onActionViewExpanded())
        // binding.searchView.onActionViewExpanded()
        // Handler().postDelayed(Runnable { binding.searchView.clearFocus() }, 300)

        // OnClick Button del in Home Fragment
        binding.fabBin.setOnClickListener {
            // show button bin in item
            HOME_STATE = "Delete"
            dbViewModel.changeDeleteMode()
        }

        binding.fabAccept.setOnClickListener {
            HOME_STATE = "Default"

            dbViewModel.backDefaultMode()
            dbViewModel.allNotes.observe(viewLifecycleOwner) {
                if (HOME_STATE == "Default") {
                    if (it.isNullOrEmpty()) {
                        binding.contentForEmpty.visibility = View.VISIBLE
                        binding.fabBin.visibility = View.INVISIBLE

                        binding.fabAccept.visibility = View.INVISIBLE
                        binding.fabPlus.visibility = View.VISIBLE
                    } else {
                        binding.contentForEmpty.visibility = View.INVISIBLE
                        binding.fabBin.visibility = View.VISIBLE
                    }
                }
            }
        }

        adapter = MyNoteAdapter(
            {
                dbViewModel.confirmDeleteMode(it)
            },
            {
                dbViewModel.acceptDeleteMode(it)
            },
            {
                dbViewModel.denyDeleteMode(it)
            }
        )



    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        dbViewModel.searchNote(searchQuery).observe(viewLifecycleOwner) {
            it.let {
                adapter.submitList(it)
                binding.listNote.layoutManager =
                    LinearLayoutManager(requireContext())
                binding.listNote.adapter = adapter
            }
        }
    }

    override fun onStop() {
        super.onStop()
        Log.e("test lifecycle", "stop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("test lifecycle", "destroyView")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("test lifecycle", "destroy")
    }
}
