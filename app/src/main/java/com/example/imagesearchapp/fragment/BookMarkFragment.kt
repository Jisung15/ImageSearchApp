package com.example.imagesearchapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearchapp.viewModel.BookMarkViewModel
import com.example.imagesearchapp.databinding.FragmentBookmarkBinding
import com.example.imagesearchapp.dataClass.SubmitDataItem
import com.example.imagesearchapp.recyclerView.SearchRecyclerViewAdapter

class BookMarkFragment : Fragment() {
    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SearchRecyclerViewAdapter
    private val viewModel: BookMarkViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchRecyclerViewAdapter(mutableListOf())
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter

        viewModel.bookMarkList.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }

        adapter.clicked = object : SearchRecyclerViewAdapter.OnBookMarkClicked {
            override fun onAddBookMark(item: SubmitDataItem?) {
                item ?: return
                viewModel.addBookMark(item)
            }

            override fun onRemoveBookMark(item: SubmitDataItem?) {
                item ?: return
                viewModel.removeBookMark(item)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
