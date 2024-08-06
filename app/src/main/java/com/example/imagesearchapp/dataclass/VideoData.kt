package com.example.imagesearchapp.dataclass

import com.google.gson.annotations.SerializedName

data class VideoData(
    @SerializedName("documents")
    val documents: List<VideoDocument?>?,
    @SerializedName("meta")
    val meta: VideoMeta?
)

data class VideoDocument(
    @SerializedName("author")
    val author: String?,
    @SerializedName("datetime")
    val datetime: String?,
    @SerializedName("play_time")
    val playTime: Int?,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?
)

data class VideoMeta(
    @SerializedName("is_end")
    val isEnd: Boolean?,
    @SerializedName("pageable_count")
    val pageableCount: Int?,
    @SerializedName("total_count")
    val totalCount: Int?
)