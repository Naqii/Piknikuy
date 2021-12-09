package com.example.piknikuy.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ModelResto (
    var name: String = "",
    var description: String = "",
    var pictureId: String = "",
    var city: String = "",
    var rating: String = "",
    var address: String = ""

) : Parcelable