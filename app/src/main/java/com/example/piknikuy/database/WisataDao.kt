package com.example.piknikuy.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.piknikuy.model.ModelWisata

@Dao
interface WisataDao {

    @Query("select * from favorite_wisata" )
    fun select() : LiveData<List<ModelWisata>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wisata: ModelWisata)

    @Query("SELECT * from favorite_wisata ORDER BY id ASC")
    fun selectCursor(): Cursor

    @Query("SELECT * from favorite_wisata WHERE id =:id ORDER BY id ASC")
    fun selectCursor(id: String? = null): Cursor

    @Query("DELETE from favorite_wisata WHERE id = :id")
    fun drop(id: String?)

    @Query("SELECT COUNT(*) from favorite_wisata WHERE id = :id")
    fun num(id: String?) : Int
}