package com.example.imagesearchapp.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearchapp.dataclass.VideoDocument
import com.example.imagesearchapp.databinding.GridItemRecyclerViewBinding
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class SearchRecyclerViewAdapter(
//    private val imageItems: List<ImageDocument?>,
    private val videoItems: List<VideoDocument?>
//    private val submitDataItem: List<SubmitDataItem>,

) : RecyclerView.Adapter<SearchRecyclerViewAdapter.Holder>() {

    inner class Holder(private val binding: GridItemRecyclerViewBinding) :
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

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = videoItems[position]
        Glide.with(holder.itemView)
            .load(item?.thumbnail)
            .into(holder.image)

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateTime = OffsetDateTime.parse(item?.datetime)
        val dataTimeFormat = dateTime?.format(formatter)

        holder.date.text = dataTimeFormat

        holder.text.text = "[Image] ${item?.title}"
    }

    override fun getItemCount(): Int {
        return videoItems.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}