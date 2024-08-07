package com.example.imagesearchapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearchapp.recyclerView.ChoiceRecyclerViewAdapter
import com.example.imagesearchapp.databinding.FragmentChoiceBinding
import com.example.imagesearchapp.dataclass.ImageDocument
import com.example.imagesearchapp.dataclass.SubmitDataItem

class ChoiceFragment : Fragment() {
    private var _binding: FragmentChoiceBinding? = null
    private val binding get() = _binding!!
//    private lateinit var adapter: ChoiceRecyclerViewAdapter

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
//        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: List<SubmitDataItem?>) =
            ChoiceFragment().apply {
                arguments = Bundle().apply {
//                    putSerializable(param1)
                }
            }
    }
}
