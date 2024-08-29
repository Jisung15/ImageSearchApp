package com.example.imagesearchapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearchapp.BuildConfig
import com.example.imagesearchapp.SearchRepository
import com.example.imagesearchapp.dataClass.SubmitDataItem
import com.example.imagesearchapp.dataClass.UiState
import com.example.imagesearchapp.retrofit.RetrofitClient
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val _searchResult = MutableLiveData<UiState<List<SubmitDataItem>>>()
    val searchResult : LiveData<UiState<List<SubmitDataItem>>> get() = _searchResult
    private val repository = SearchRepository()
    fun getResult(query: String) {
        _searchResult.value = UiState.Loading
        viewModelScope.launch {
            try {
                val imageRequest = async {
                    repository.getImage(query = query)
                }

                val videoRequest = async {
                    repository.getVideo(query = query)
                }

                val imageResponseData = imageRequest.await()
                val videoResponseData = videoRequest.await()

                // 각각 서버에서 받아온 리스트를 변수에다가 연결
                val imageDocumentData = imageResponseData.documents ?: emptyList()
                val videoDocumentData = videoResponseData.documents ?: emptyList()
                // 그걸 하나의 리스트에 저장한다. 이걸 Adapter에서 쓸 예정
                val submitDataList = mutableListOf<SubmitDataItem>()

                // 빈 리스트에 각각의 데이터 대입(?)
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

                // 이미지와 동영상을 같이 출력해야 하니 섞어준다.
                submitDataList.sortBy {
                    when (it) {
                        is SubmitDataItem.Image -> it.datetime
                        is SubmitDataItem.Video -> it.datetime
                    }
                }

                _searchResult.value = UiState.Success(submitDataList)

                // 그리고 그렇게 나온 리스트를 어댑터에 세팅
            } catch (e: Exception) {
//            error()   에러 처리
                _searchResult.value = UiState.Error(e.message ?: "에러 발생")
        }
            // 데이터를 모두 로드했으니 이 값은 당연히 false
//            loading = false
        }
    }
}