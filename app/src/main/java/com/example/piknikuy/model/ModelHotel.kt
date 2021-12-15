package com.example.piknikuy.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.piknikuy.model.ModelHotel.Companion.TABLE_NAME
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME)
data class ModelHotel (

    @ColumnInfo(name = "id")
    var id: String = "",

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "description")
    var description: String = "",

    @ColumnInfo(name = "picture")
    var picture: String = "",

    @ColumnInfo(name = "city")
    var city: String = "",

    @ColumnInfo(name = "rating")
    var rating: String = "",

    @PrimaryKey(autoGenerate = true)
    var uid: Int? = null
) : Parcelable {
    companion object {
        const val TABLE_NAME = "favorite_hotel"
    }
}