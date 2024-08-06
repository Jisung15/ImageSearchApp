package com.example.imagesearchapp.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearchapp.ImageDocument
import com.example.imagesearchapp.RetrofitClient
import com.example.imagesearchapp.SubmitDataItem
import com.example.imagesearchapp.VideoDocument
import com.example.imagesearchapp.recyclerView.RecyclerViewAdapter
import com.example.imagesearchapp.databinding.FragmentSearchBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var searchText: String = ""
    private lateinit var adapter: RecyclerViewAdapter

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

        binding.recyclerView.setBackgroundColor(Color.WHITE)

        binding.searchButton.setOnClickListener {
            if (binding.searchTextInput.text.toString().isNotEmpty()) {
                searchText = binding.searchTextInput.text.toString()
                dataSaved(searchText)
                searchResult(searchText)
                binding.recyclerView.setBackgroundColor(Color.parseColor("#00000000"))
                Toast.makeText(requireContext(), "${searchText}를 검색하셨습니다.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "검색어를 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            dataLoad()
        }
    }

    private fun dataSaved(searchText: String) {
        val shared = requireContext().getSharedPreferences("shared", 0)
        val edit = shared.edit()
        edit.putString("name", searchText)
        edit.apply()
    }

    private fun dataLoad() {
        val shared = requireContext().getSharedPreferences("pref", 0)

        binding.searchTextInput.setText(shared.getString("name", ""))
    }

    private fun searchResult(query: String) {
        lifecycleScope.launch {
            try {
                val imageResponseData = RetrofitClient.dustNetWork.getImage(
                    apiKey = "KakaoAK d9e31c60db2fd236337a605b8b0128bf",
                    query = query,
                    sort = "recency",
                    page = 1,
                    size = 80
                )

                val videoResponseData = RetrofitClient.dustNetWork.getVideo(
                    apiKey = "KakaoAK d9e31c60db2fd236337a605b8b0128bf",
                    query = query,
                    sort = "recency",
                    page = 1,
                    size = 30
                )

                val imageDocumentData = imageResponseData.documents!!
                val videoDocumentData = videoResponseData.documents!!

                adapter = RecyclerViewAdapter(videoDocumentData)
                recyclerViewAdapter()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "에러!", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    private fun recyclerViewAdapter() {
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}