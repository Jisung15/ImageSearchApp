package com.example.imagesearchapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearchapp.recyclerView.ChoiceRecyclerViewAdapter
import com.example.imagesearchapp.databinding.FragmentChoiceBinding

class ChoiceFragment : Fragment() {
    private var _binding: FragmentChoiceBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ChoiceRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    fun setList(data: List<ImageDocument?>) {
//        adapter = ChoiceRecyclerViewAdapter(data)
//        binding.recyclerView.adapter = adapter
//    }

//    fun newInstance(param1: List<Document?>) {
//        ChoiceFragment().apply {
//            arguments = Bundle().apply {
//                set
//                putSerializable("Data", param1)
//            }
//        }
//    }
}
