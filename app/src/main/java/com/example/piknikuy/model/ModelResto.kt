package com.example.piknikuy.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.piknikuy.model.ModelResto.Companion.TABLE_NAME
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME)
data class ModelResto (

    @ColumnInfo(name = "id")
    var id: String = "",

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "overview")
    var overview: String = "",

    @ColumnInfo(name = "picture")
    var picture: String = "",

    @ColumnInfo(name = "location")
    var location: String = "",

    @ColumnInfo(name = "rating")
    var rating: String = "",

    @ColumnInfo(name = "status")
    var status: String = "",

    @PrimaryKey(autoGenerate = true)
    var uid: Int? = null
) : Parcelable {
    companion object {
        const val TABLE_NAME = "favorite_resto"
    }
}