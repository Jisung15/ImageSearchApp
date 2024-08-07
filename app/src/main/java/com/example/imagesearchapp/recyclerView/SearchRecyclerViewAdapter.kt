package com.example.imagesearchapp.recyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearchapp.databinding.GridItemRecyclerViewBinding
import com.example.imagesearchapp.dataclass.SubmitDataItem
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class SearchRecyclerViewAdapter(
//    private val imageItems: List<ImageDocument?>,
//    private val videoItems: List<VideoDocument?>
    private val submitDataItem: List<SubmitDataItem>,
) : RecyclerView.Adapter<SearchRecyclerViewAdapter.Holder>() {

//    companion object {
//        private const val TYPE_IMAGE = 0
//        private const val TYPE_VIDEO = 1
//    }

    class Holder(private val binding: GridItemRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val image = binding.searchImageValue
        val text = binding.searchTextValue
        val date = binding.searchDateValue
        val choiceButton = binding.choiceButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = GridItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    private val newDataList = mutableListOf<SubmitDataItem>()

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
//        val item = submitDataItem[position]
//        Glide.with(holder.itemView)
//            .load(item?.thumbnailUrl)
//            .into(holder.image)
//
//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//        val dateTime = OffsetDateTime.parse(item?.datetime)
//        val dataTimeFormat = dateTime?.format(formatter)
//
//        holder.date.text = dataTimeFormat
//        holder.text.text = "[Image] " + item?.displaySitename

        val itemList = submitDataItem[position]

        if (itemList is SubmitDataItem.ImageDocument) {

            Glide.with(holder.itemView)
                .load(itemList.thumbnail)
                .into(holder.image)

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val dateTime = OffsetDateTime.parse(itemList.datetime)
            val dataTimeFormat = dateTime?.format(formatter)

            holder.date.text = dataTimeFormat
            holder.text.text = "[Image] " + itemList.displaySitename

            holder.choiceButton.visibility = if (itemList.selected) View.VISIBLE else View.GONE

            holder.image.setOnClickListener {
                itemList.selected = !itemList.selected
                notifyItemChanged(position)
            }

        } else if (itemList is SubmitDataItem.VideoDocument) {

            Glide.with(holder.itemView)
                .load(itemList.thumbnail)
                .into(holder.image)

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val dateTime = OffsetDateTime.parse(itemList.datetime)
            val dataTimeFormat = dateTime?.format(formatter)

            holder.date.text = dataTimeFormat
            holder.text.text = "[Video] " + itemList.title

            holder.choiceButton.visibility = if (itemList.selected) View.VISIBLE else View.GONE

            holder.image.setOnClickListener {
                itemList.selected = !itemList.selected
                notifyItemChanged(position)
            }

        }

//        var check = false
//        holder.image.setOnClickListener {
//            if (check) {
//                holder.choiceButton.visibility = View.VISIBLE
//                newDataList.add(itemList)
//                check = false
//            } else {
//                holder.choiceButton.visibility = View.GONE
//                newDataList.remove(itemList)
//                check = true
//            }
//        }
    }

    fun getLikedItems() : List<SubmitDataItem?>{
        return newDataList.toList()
    }

    override fun getItemCount(): Int {
        return submitDataItem.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}

// async를 기억하자