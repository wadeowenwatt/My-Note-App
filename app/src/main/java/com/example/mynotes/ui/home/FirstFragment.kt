package com.example.mynotes.ui.home

import android.app.DirectAction
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotes.R
import com.example.mynotes.data.MyNote
import com.example.mynotes.databinding.FragmentFirstBinding
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

    private lateinit var listItem: ArrayList<MyNote>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        listItem = arrayListOf()
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabPlus.setOnClickListener {
            val action =
                FirstFragmentDirections.actionFirstFragmentToSecondFragment(
                    "",
                    "",
                    "create",
                    CURRENT_TIME
                )
            findNavController().navigate(action)
        }


        val note1 = MyNote(
            "Note1",
            "muwhewjhejwhthrhtrtruuthfhgdhgdkhgdjhgfjdhfgjdhfjghdjghsssssssssssssda d sadasdasmmsadassadasdasdasdasdjkhnbwejwhejhwehwehwjehwhejwhejhwjejhwdsdm,m,zmc,mzxxc,mzx,xmc,zmxxxc,mzxcsdawewewe",
            "13 Mar 2022 3:00"
        )
        val note2 = MyNote(
            "Note2",
            "muwhewjhejwhthrhtrtruuthfhgdhgdkhgdjhgfjdhfgjdhfjghdjgh",
            "13 June 2022 12:00"
        )
        listItem.add(note1)
        listItem.add(note2)

        val adapter = MyNoteAdapter(listItem) {

            binding.fabBin.setOnClickListener {
                binding.fabAccept.visibility = View.VISIBLE
                binding.fabBin.visibility = View.INVISIBLE
                binding.fabPlus.visibility = View.INVISIBLE

                // show button bin in item

            }

        }

        binding.listNote.layoutManager = LinearLayoutManager(requireContext())
        binding.listNote.adapter = adapter

        if (listItem.isEmpty()) {
            binding.contentForEmpty.visibility = View.VISIBLE
            binding.fabBin.visibility = View.INVISIBLE
        } else {
            binding.contentForEmpty.visibility = View.INVISIBLE
            binding.fabBin.visibility = View.VISIBLE
        }

        binding.fabAccept.setOnClickListener {
            binding.fabAccept.visibility = View.INVISIBLE
            binding.fabBin.visibility = View.VISIBLE
            binding.fabPlus.visibility = View.VISIBLE
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}