package com.example.imagesearchapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagesearchapp.dataClass.SubmitDataItem

class BookMarkViewModel : ViewModel() {

    // 리스트를 받아와서..? 그걸 LiveData로 설정하고 지켜보다가 리스트가 바뀌면 bookMarkList를 BookMarkFragment에 반영하는 걸로 이해하고 있다.
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
