package com.example.piknikuy.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.piknikuy.model.ModelResto

@Dao
interface RestoDao {

    @Query("select * from favorite_resto" )
    fun select() : LiveData<List<ModelResto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(resto: ModelResto)

    @Query("SELECT * from favorite_resto ORDER BY id ASC")
    fun selectCursor(): Cursor

    @Query("SELECT * from favorite_resto WHERE id =:id ORDER BY id ASC")
    fun selectCursor(id: String? = null): Cursor

    @Query("DELETE from favorite_resto WHERE id = :id")
    fun drop(id: String?)

    @Query("SELECT COUNT(*) from favorite_resto WHERE id = :id")
    fun num(id: String?) : Int
}