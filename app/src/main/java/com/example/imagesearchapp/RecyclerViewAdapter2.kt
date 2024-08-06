package com.example.imagesearchapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearchapp.databinding.GridItemRecyclerViewBinding

class RecyclerViewAdapter2(private val items: List<ImageDocument?>) :
    RecyclerView.Adapter<RecyclerViewAdapter2.Holder>() {

    inner class Holder(private val binding: GridItemRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val image = binding.searchImageValue
        val imageText = binding.searchTextValue
        val imageDate = binding.searchDateValue
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            GridItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Glide.with(holder.itemView)
            .load(items[position]?.thumbnailUrl)
            .into(holder.image)
        holder.imageText.text = items[position]?.displaySitename
        holder.imageDate.text = items[position]?.datetime

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}