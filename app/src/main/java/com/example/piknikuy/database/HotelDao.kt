package com.example.piknikuy.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.piknikuy.model.ModelHotel


@Dao
interface HotelDao {

    @Query("select * from favorite_hotel" )
    fun select() : LiveData<List<ModelHotel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(resto: ModelHotel)

    @Query("SELECT * from favorite_hotel ORDER BY id ASC")
    fun selectCursor(): Cursor

    @Query("SELECT * from favorite_hotel WHERE id =:id ORDER BY id ASC")
    fun selectCursor(id: String? = null): Cursor

    @Query("DELETE from favorite_hotel WHERE id = :id")
    fun drop(id: String?)

    @Query("SELECT COUNT(*) from favorite_hotel WHERE id = :id")
    fun num(id: String?) : Int
}