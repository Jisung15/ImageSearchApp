package com.example.imagesearchapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagesearchapp.dataclass.ImageDocument

class SearchViewModel : ViewModel() {

    private val _imageDocuments = MutableLiveData<List<ImageDocument>>()
    val imageDocuments : LiveData<List<ImageDocument>> get() = _imageDocuments

    fun update (query: String) {
    }
}