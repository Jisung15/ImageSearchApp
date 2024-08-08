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

    interface OnBookMarkClicked {
        fun onAddBookMark(item: SubmitDataItem?)
        fun onRemoveBookMark(item: SubmitDataItem?)
    }

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
        val itemList = submitDataItems?.get(position)

        if (itemList is SubmitDataItem.Image) {

            Glide.with(holder.itemView)
                .load(itemList.thumbnail)
                .into(holder.image)

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val dateTime = OffsetDateTime.parse(itemList.datetime)
            val dataTimeFormat = dateTime?.format(formatter)

            holder.date.text = dataTimeFormat
            holder.text.text = "[Image] " + itemList.displaySitename

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

    fun updateList(newDataItems: List<SubmitDataItem>) {
        submitDataItems?.clear()
        submitDataItems?.addAll(newDataItems)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return submitDataItems?.size ?: 0
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}
