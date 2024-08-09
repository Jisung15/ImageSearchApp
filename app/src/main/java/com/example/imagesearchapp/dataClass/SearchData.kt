package com.example.imagesearchapp.dataClass

import com.google.gson.annotations.SerializedName

// RecyclerViewAdapter에 들어갈 친구들
sealed class SubmitDataItem {
    data class Image(
        @SerializedName("datetime")
        val datetime: String?,
        @SerializedName("display_sitename")
        val displaySitename: String?,
        @SerializedName("thumbnail_url")
        val thumbnail: String?,
        var selected: Boolean = false
    ) : SubmitDataItem()

    data class Video(
        @SerializedName("datetime")
        val datetime: String?,
        @SerializedName("thumbnail")
        val thumbnail: String?,
        @SerializedName("title")
        val title: String?,
        var selected: Boolean = false
    ) : SubmitDataItem()
}
