package com.example.imagesearchapp.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearchapp.viewModel.BookMarkViewModel
import com.example.imagesearchapp.R
import com.example.imagesearchapp.retrofit.RetrofitClient
import com.example.imagesearchapp.recyclerView.SearchRecyclerViewAdapter
import com.example.imagesearchapp.databinding.FragmentSearchBinding
import com.example.imagesearchapp.dataClass.SubmitDataItem
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var searchText: String = ""
    private lateinit var adapter: SearchRecyclerViewAdapter
    private val viewModel: BookMarkViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataLoad()
        binding.recyclerView.setBackgroundColor(Color.WHITE)

        binding.searchButton.setOnClickListener {
            hideKeyBoard()

            if (binding.searchTextInput.text.toString().isNotEmpty()) {
                searchText = binding.searchTextInput.text.toString()
                dataSaved(searchText)
                searchResult(searchText)

                binding.recyclerView.setBackgroundColor(Color.parseColor("#00000000"))

                Toast.makeText(requireContext(), "${searchText}(을/를) 검색하셨습니다.", Toast.LENGTH_SHORT)
                    .show()

            } else {
                Toast.makeText(requireContext(), R.string.search_bar_hint, Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
        }

        binding.floatingButton.setOnClickListener {
            floatingButton()
        }
    }

    private fun dataLoad() {
        val loadData = requireContext().getSharedPreferences("shared", 0)
        binding.searchTextInput.setText(loadData.getString("name", ""))
    }

    @SuppressLint("SuspiciousIndentation")
    private fun hideKeyBoard() {
        val hideKeyBoard =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        hideKeyBoard.hideSoftInputFromWindow(binding.searchTextInput.windowToken, 0)
    }

    private fun dataSaved(searchText: String) {
        val saveData = requireContext().getSharedPreferences("shared", 0)
        val edit = saveData.edit()
        edit.putString("name", searchText)
        edit.apply()
    }

    private fun searchResult(query: String) {
        lifecycleScope.launch {
            val imageResponseData = RetrofitClient.makeRetrofit.getImage(
                apiKey = "KakaoAK d9e31c60db2fd236337a605b8b0128bf",
                query = query,
                sort = "recency",
                page = 1,
                size = 80
            )

            val videoResponseData = RetrofitClient.makeRetrofit.getVideo(
                apiKey = "KakaoAK d9e31c60db2fd236337a605b8b0128bf",
                query = query,
                sort = "recency",
                page = 1,
                size = 30
            )

            val imageDocumentData = imageResponseData.documents ?: emptyList()
            val videoDocumentData = videoResponseData.documents ?: emptyList()
            val submitDataList = mutableListOf<SubmitDataItem>()

            for (imageDocument in imageDocumentData) {
                val imageItem = SubmitDataItem.Image(
                    datetime = imageDocument?.datetime,
                    displaySitename = imageDocument?.displaySitename,
                    thumbnail = imageDocument?.thumbnailUrl
                )

                submitDataList.add(imageItem)
            }

            for (videoDocument in videoDocumentData) {
                val videoItem = SubmitDataItem.Video(
                    datetime = videoDocument?.datetime,
                    title = videoDocument?.title,
                    thumbnail = videoDocument?.thumbnail
                )

                submitDataList.add(videoItem)
            }

            submitDataList.sortBy {
                when (it) {
                    is SubmitDataItem.Image -> it.datetime
                    is SubmitDataItem.Video -> it.datetime
                }
            }

            adapter = SearchRecyclerViewAdapter(submitDataList)
            binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.recyclerView.adapter = adapter

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
    }

    private fun floatingButton() {
        var top = true

        with(binding) {
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (!binding.recyclerView.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        top = true
                    } else {
                        if (top) {
                            floatingButton.setOnClickListener {
                                recyclerView.smoothScrollToPosition(0)
                            }
                            top = true
                        }
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
