package com.example.imagesearchapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearchapp.databinding.GridItemRecyclerViewBinding

class RecyclerViewAdapter(private val item: MutableList<ImageSearchValue>) : RecyclerView.Adapter<RecyclerViewAdapter.Holder>() {

    inner class Holder(private val binding: GridItemRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.searchImageValue
        val imagText = binding.searchTextValue
        val imageDate = binding.searchDateValue
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = GridItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.image.setImageResource(item[position].image)
        holder.imagText.text = item[position].imageSearchText
        holder.imageDate.text = item[position].imageSearchDate
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}