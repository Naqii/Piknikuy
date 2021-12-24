package com.example.piknikuy.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piknikuy.api.ApiConfig
import com.example.piknikuy.database.PiknikuyDatabase
import com.example.piknikuy.helper.Helper
import com.example.piknikuy.model.ModelResto
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception

class RestoViewModel : ViewModel() {

    private var database = PiknikuyDatabase.getDatabase().restoDao()

    private var _listResto = MutableLiveData<ArrayList<ModelResto>>()
    val listResto: LiveData<ArrayList<ModelResto>> = _listResto

    private var _resto = MutableLiveData<ModelResto>()
    val resto: LiveData<ModelResto> = _resto

    private var _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private var _favorite = database.select()
    val favorite: LiveData<List<ModelResto>> = _favorite

    fun setListResto() {
        ApiConfig.getListResto(object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val restoArray = responseObject.getJSONArray("resto")
                    _listResto.postValue(Helper.listRestoResponse(restoArray))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int, headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun setDetailResto(id: String) {
        ApiConfig.getDetailResto(id, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val restoObject = responseObject.getJSONObject("resto")
                    _resto.postValue(Helper.detailRestoResponse(restoObject))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun insertFavorite(favResto: ModelResto) = viewModelScope.launch(Dispatchers.IO) {
        database.insert(favResto)
        updateFavorite(favResto)
    }

    fun updateFavorite(favResto: ModelResto) = viewModelScope.launch(Dispatchers.IO) {
        val isFavorite = database.num(favResto.id) > 0
        _isFavorite.postValue(isFavorite)
    }

    fun deleteFavorite(favResto: ModelResto) = viewModelScope.launch(Dispatchers.IO) {
        database.drop(favResto.id)
        updateFavorite(favResto)
    }
}