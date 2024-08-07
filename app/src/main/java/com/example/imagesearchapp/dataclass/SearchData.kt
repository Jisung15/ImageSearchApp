package com.example.imagesearchapp.dataclass

import com.google.gson.annotations.SerializedName

sealed class SubmitDataItem {
//    val thumbnail: String?
//    val datetime: String?
//    val title: String?
//    val displaySitename: String?

    data class ImageDocument(
        @SerializedName("datetime")
        val datetime: String?,
        @SerializedName("display_sitename")
        val displaySitename: String?,
        @SerializedName("thumbnail_url")
        val thumbnail: String?,
    ) : SubmitDataItem()

    data class VideoDocument(
        @SerializedName("datetime")
        val datetime: String?,
        @SerializedName("thumbnail")
        val thumbnail: String?,
        @SerializedName("title")
        val title: String?,
    ) : SubmitDataItem()
}