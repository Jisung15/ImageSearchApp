package com.example.imagesearchapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagesearchapp.dataclass.SubmitDataItem

class BookViewModel : ViewModel() {

    private val _bookMarkList = MutableLiveData<List<SubmitDataItem>>()
    val bookMarkList: LiveData<List<SubmitDataItem>> get() = _bookMarkList

    fun addBookMark(item: SubmitDataItem) {
        val currentList = _bookMarkList.value?.toMutableList() ?: mutableListOf()
        currentList.add(item)
        _bookMarkList.value = currentList
    }

    fun removeBookMark(item: SubmitDataItem) {
        val currentList = _bookMarkList.value?.toMutableList() ?: mutableListOf()
        currentList.remove(item)
        _bookMarkList.value = currentList
    }
}