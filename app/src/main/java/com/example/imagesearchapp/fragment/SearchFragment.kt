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
    // ViewModel 연결
    private val viewModel: BookMarkViewModel by activityViewModels()
    // 현재 페이지와 로딩 여부
    private var currentPage = 1
    private var loading = false

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

        // 어댑터 초기화
        adapter = SearchRecyclerViewAdapter(mutableListOf())
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter

        // 검색 버튼 눌렀을 때
        binding.searchButton.setOnClickListener {
            // 키보드 숨기는 메소드 호출
            hideKeyBoard()

            // 검색창이 비었으면 입력해 달라고 토스트 메세지 출력
            // 비어있지 않으면 검색한 키워드를 SharedPreferences 이용해 저장
            // 처음 검색 버튼 누르면 맨 위 검색 결과부터 뜨기 때문에 페이지 넘버는 1
            if (binding.searchTextInput.text.toString().isNotEmpty()) {
                searchText = binding.searchTextInput.text.toString()
                dataSaved(searchText)
                currentPage = 1
                searchResult(searchText, currentPage)

                binding.recyclerView.setBackgroundColor(Color.parseColor("#00000000"))

                Toast.makeText(requireContext(), "검색한 키워드 = $searchText \n 검색이 성공적으로 완료되었습니다!", Toast.LENGTH_SHORT)
                    .show()

            } else {
                Toast.makeText(requireContext(), R.string.search_bar_hint, Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
        }

        // 플로팅 버튼 누르면 맨 위로 가는 메소드 호출
        binding.floatingButton.setOnClickListener {
            floatingButton()
        }

        // 무한 스크롤 메소드 호출
        setUpScrollListener()
    }

    // 앱 재시작시 검색창에 데이터 출력
    private fun dataLoad() {
        val loadData = requireContext().getSharedPreferences("shared", 0)
        binding.searchTextInput.setText(loadData.getString("name", ""))
    }

    // 키보드 숨기는 메소드
    @SuppressLint("SuspiciousIndentation")
    private fun hideKeyBoard() {
        val hideKeyBoard =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        hideKeyBoard.hideSoftInputFromWindow(binding.searchTextInput.windowToken, 0)
    }

    // SharedPreferences로 키워드 저장하는 메소드
    private fun dataSaved(searchText: String) {
        val saveData = requireContext().getSharedPreferences("shared", 0)
        val edit = saveData.edit()
        edit.putString("name", searchText)
        edit.apply()
    }

    // 검색 결과 데이터 세팅(?) 하는 메소드
    private fun searchResult(query: String, page: Int) {
        if (loading) return // RecyclerView가 로딩 중이면 다음 코드를 실행하면 안 된다. 맞나...?

        // 다음 페이지를 로드하고 로딩을 한 다음, 데이터를 세팅(?) 한다.
        loading = true
        lifecycleScope.launch {
            val imageResponseData = RetrofitClient.makeRetrofit.getImage(
                apiKey = "KakaoAK d9e31c60db2fd236337a605b8b0128bf",
                query = query,
                sort = "recency",
                page = page,
                size = 50
            )

            val videoResponseData = RetrofitClient.makeRetrofit.getVideo(
                apiKey = "KakaoAK d9e31c60db2fd236337a605b8b0128bf",
                query = query,
                sort = "recency",
                page = page,
                size = 30
            )

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

            // 그리고 그렇게 나온 리스트를 어댑터에 세팅
            adapter.addItems(submitDataList)
            // 데이터를 모두 로드했으니 이 값은 당연히 false
            loading = false

            // 사진 클릭했을 때 설정. 이건 똑같다.
            // 하트 이모티콘은 Adapter에서 설정
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

    // 플로팅 버튼 관련 메소드
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

    // 무한 스크롤 기능 구현 메소드
    private fun setUpScrollListener() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val nowItemCount = recyclerView.childCount   // 현재 아이템 개수
                val totalItemCount = (recyclerView.layoutManager as GridLayoutManager).itemCount   // 총 아이템 개수
                val firstVisibleItemPosition = (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition() // 마지막 아이템의 위치..? 라고 한다.

                if (!loading && (totalItemCount-nowItemCount) <= (firstVisibleItemPosition) + 1) {   // 마지막 아이템에 스크롤이 도달하면
                    // 새 페이지를 만들고 다시 데이터 세팅(?)
                    currentPage++
                    searchResult(searchText, currentPage)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
