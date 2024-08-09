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
    // ViewModel 연결
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

        // 어댑터 초기화
        adapter = SearchRecyclerViewAdapter(mutableListOf())
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter

        // 검색 결과 화면에서 바뀐 데이터를 감지하여(?) BookMarkFragment의 Adapter에 업데이트 하는 것으로 이해하고 있다.
        viewModel.bookMarkList.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }

        // 이거 클릭하면 보관함 화면에서 삭제하는 건데.. 검색 화면에도 반영하는 걸 아직 구현 못함
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
