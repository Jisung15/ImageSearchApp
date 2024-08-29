package com.example.imagesearchapp.dataClass

// RecyclerViewAdapter에 들어갈 친구들
sealed class SubmitDataItem {
    data class Image(
        val datetime: String?,
        val displaySitename: String?,
        val thumbnail: String?,
        var selected: Boolean = false
    ) : SubmitDataItem()

    data class Video(
        val datetime: String?,
        val thumbnail: String?,
        val title: String?,
        var selected: Boolean = false
    ) : SubmitDataItem()
}
