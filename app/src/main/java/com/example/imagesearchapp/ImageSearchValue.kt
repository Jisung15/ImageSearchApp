package com.example.imagesearchapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageSearchValue(
    val image: Int,
    val imageSearchText: String,
    val imageSearchDate: String
) : Parcelable
