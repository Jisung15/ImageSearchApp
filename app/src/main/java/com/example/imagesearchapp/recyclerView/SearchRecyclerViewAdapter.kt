package com.example.imagesearchapp.recyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearchapp.databinding.GridItemRecyclerViewBinding
import com.example.imagesearchapp.dataClass.SubmitDataItem
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class SearchRecyclerViewAdapter(
    private val submitDataItems: MutableList<SubmitDataItem>?
) : RecyclerView.Adapter<SearchRecyclerViewAdapter.Holder>() {

    // ViewModel 관련 interface 설정
    interface OnBookMarkClicked {
        fun onAddBookMark(item: SubmitDataItem?)
        fun onRemoveBookMark(item: SubmitDataItem?)
    }

    // 클릭 리스너 설정
    var clicked: OnBookMarkClicked? = null

    class Holder(private val binding: GridItemRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val image = binding.searchImageValue
        val text = binding.searchTextValue
        val date = binding.searchDateValue
        val bookMarkButton = binding.choiceButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            GridItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        // 받아온 리스트를 itemList 변수로 설정
        val itemList = submitDataItems?.get(position)

        // itemList에서 직접 가져오는 게 안 된다..? 이렇게 하면 되긴 된다... 이미지와 비디오를 나누어 관리.. ㅋㅋ
        if (itemList is SubmitDataItem.Image) {

            Glide.with(holder.itemView)
                .load(itemList.thumbnail)
                .into(holder.image)

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val dateTime = OffsetDateTime.parse(itemList.datetime)
            val dataTimeFormat = dateTime?.format(formatter)

            holder.date.text = dataTimeFormat
            holder.text.text = "[Image] " + itemList.displaySitename

            // 사진 클릭 여부에 따라 즐겨찾기 이모티콘 visibility 설정
            holder.bookMarkButton.visibility = if (itemList.selected) View.VISIBLE else View.GONE

            // 이미지 클릭 여부에 따라 설정
            // 나중에 별도 리스트에 add, remove하는 건 ViewModel에서 한다.
            holder.image.setOnClickListener {
                if (!itemList.selected) {
                    clicked?.onAddBookMark(itemList)
                } else {
                    clicked?.onRemoveBookMark(itemList)
                }

                itemList.selected = !itemList.selected
                notifyItemChanged(position)
            }

        } else if (itemList is SubmitDataItem.Video) {

            Glide.with(holder.itemView)
                .load(itemList.thumbnail)
                .into(holder.image)

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val dateTime = OffsetDateTime.parse(itemList.datetime)
            val dataTimeFormat = dateTime?.format(formatter)

            holder.date.text = dataTimeFormat
            holder.text.text = "[Video] " + itemList.title

            holder.bookMarkButton.visibility = if (itemList.selected) View.VISIBLE else View.GONE

            holder.image.setOnClickListener {
                if (!itemList.selected) {
                    clicked?.onAddBookMark(submitDataItems?.get(position))
                } else {
                    clicked?.onRemoveBookMark(submitDataItems?.get(position))
                }

                itemList.selected = !itemList.selected
                notifyItemChanged(position)
            }
        }
    }

    // 내 보관함 관련 업데이트 메소드
    fun updateList(newDataItems: List<SubmitDataItem>) {
        submitDataItems?.clear()
        submitDataItems?.addAll(newDataItems)
        notifyDataSetChanged()
    }

    // 무한 스크롤 시 마지막 아이템에 도달하면 다시 새로 페이지 만들어 주는 메소드
    fun addItems(newDataItems: List<SubmitDataItem>) {
        val itemSize = submitDataItems?.size ?: 0
        submitDataItems?.addAll(newDataItems)
        notifyItemRangeInserted(itemSize, newDataItems.size)
    }

    override fun getItemCount(): Int {
        return submitDataItems?.size ?: 0
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}
