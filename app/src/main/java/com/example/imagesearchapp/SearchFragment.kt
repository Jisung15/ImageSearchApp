package com.example.imagesearchapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearchapp.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var searchValue: String? = null
    private var itemList = mutableListOf<ImageSearchValue>()
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        data()
        recyclerViewAdapter()
        keyboardSetting()

        binding.searchView.setOnClickListener {
            showKeyboard(binding.searchView)
        }


        binding.searchButton.setOnClickListener {
            Toast.makeText(requireContext(), "${searchValue}를 검색하셨습니다.", Toast.LENGTH_SHORT).show()
            hideKeyboard()
        }
    }

    private fun data() {
        itemList = listOf(
            ImageSearchValue(R.mipmap.ic_launcher, "테스트1", "2024.01.01"),
            ImageSearchValue(R.mipmap.ic_launcher, "테스트1", "2024.01.01"),
            ImageSearchValue(R.mipmap.ic_launcher, "테스트1", "2024.01.01"),
            ImageSearchValue(R.mipmap.ic_launcher, "테스트1", "2024.01.01"),
            ImageSearchValue(R.mipmap.ic_launcher, "테스트1", "2024.01.01"),
            ImageSearchValue(R.mipmap.ic_launcher, "테스트1", "2024.01.01"),
            ImageSearchValue(R.mipmap.ic_launcher, "테스트1", "2024.01.01"),
            ImageSearchValue(R.mipmap.ic_launcher, "테스트1", "2024.01.01"),
            ImageSearchValue(R.mipmap.ic_launcher, "테스트1", "2024.01.01"),
            ImageSearchValue(R.mipmap.ic_launcher, "테스트1", "2024.01.01"),
            ImageSearchValue(R.mipmap.ic_launcher, "테스트1", "2024.01.01"),
            ImageSearchValue(R.mipmap.ic_launcher, "테스트1", "2024.01.01"),
            ImageSearchValue(R.mipmap.ic_launcher, "테스트1", "2024.01.01"),
            ImageSearchValue(R.mipmap.ic_launcher, "테스트1", "2024.01.01"),
            ImageSearchValue(R.mipmap.ic_launcher, "테스트1", "2024.01.01"),
            ImageSearchValue(R.mipmap.ic_launcher, "테스트1", "2024.01.01"),
            ImageSearchValue(R.mipmap.ic_launcher, "테스트1", "2024.01.01"),
            ImageSearchValue(R.mipmap.ic_launcher, "테스트1", "2024.01.01"),
            ImageSearchValue(R.mipmap.ic_launcher, "테스트1", "2024.01.01"),
            ImageSearchValue(R.mipmap.ic_launcher, "테스트1", "2024.01.01"),
            ImageSearchValue(R.mipmap.ic_launcher, "테스트1", "2024.01.01")
        ) as MutableList<ImageSearchValue>
    }

    private fun recyclerViewAdapter() {
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = RecyclerViewAdapter(itemList)
        binding.recyclerView.adapter = adapter
    }

    private fun keyboardSetting() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchValue = query
                hideKeyboard()

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchValue = newText

                return true
            }
        })
    }

    private fun showKeyboard(view: View) {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}