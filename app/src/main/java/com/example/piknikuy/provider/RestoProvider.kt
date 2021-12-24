package com.example.piknikuy.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.piknikuy.database.PiknikuyDatabase
import com.example.piknikuy.database.RestoDao
import com.example.piknikuy.model.ModelResto

class RestoProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        favoriteResto = PiknikuyDatabase.getDatabase(context as Context).restoDao()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        return when (uriMatch.match(uri)) {
            FAVORITE -> favoriteResto.selectCursor()
            FAVORITE_ID -> favoriteResto.selectCursor(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(
        uri: Uri,
        values: ContentValues?
    ): Uri? {
        return null
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        favoriteResto.drop(uri.lastPathSegment.toString())
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return 0
    }

    companion object {
        private lateinit var favoriteResto: RestoDao

        private const val SCHEME = "content"
        private const val TABLE_NAME = ModelResto.TABLE_NAME
        private const val AUTH = "com.example.piknikuy"
        private val uriMatch = UriMatcher(UriMatcher.NO_MATCH)
        private const val FAVORITE = 1
        private const val FAVORITE_ID = 2

        init {
            uriMatch.addURI(AUTH, TABLE_NAME, FAVORITE)
            uriMatch.addURI(AUTH, "$TABLE_NAME/#", FAVORITE_ID)
        }

        val CONTENT_URI: Uri = Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTH)
            .appendPath(TABLE_NAME)
            .build()
    }
}