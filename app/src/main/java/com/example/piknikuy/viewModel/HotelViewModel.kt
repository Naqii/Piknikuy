package com.example.piknikuy.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piknikuy.api.ApiConfig
import com.example.piknikuy.database.PiknikuyDatabase
import com.example.piknikuy.helper.Helper
import com.example.piknikuy.model.ModelHotel
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception

class HotelViewModel : ViewModel() {

    private var database = PiknikuyDatabase.getDatabase().hotelDao()

    private var _listHotel = MutableLiveData<ArrayList<ModelHotel>>()
    val listHotel : LiveData<ArrayList<ModelHotel>> = _listHotel

    private var _hotel = MutableLiveData<ModelHotel>()
    val hotel : LiveData<ModelHotel> = _hotel

    private var _isFavorite = MutableLiveData<Boolean>()
    val isFavorite : LiveData<Boolean> = _isFavorite

    private var _favorite = database.select()
    val favorite : LiveData<List<ModelHotel>> = _favorite

    fun setSearchHotel(query: String? = null) {
        if(query == null){
            ApiConfig.getListHotel( object: AsyncHttpResponseHandler(){
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray
                ) {
                    val result = String(responseBody)
                    try {
                        val responseObject = JSONObject(result)
                        val restoArray = responseObject.getJSONArray("hotels")
                        _listHotel.postValue(Helper.listHotelResponse(restoArray))
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
        } else {
           /* ApiConfig.getSearchHotel(query, object: AsyncHttpResponseHandler(){
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray
                ) {
                    val result = String(responseBody)
                    try {
                        val responseObject = JSONObject(result)
                        val hotelArray = responseObject.getJSONArray("hotels")
                        _listHotel.postValue(Helper.listHotelResponse(restoArray))
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
            })*/
        }
    }

    fun setDetailHotel(id: String) {
        ApiConfig.getDetailHotel(id, object: AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val restoObject = responseObject.getJSONObject("hotels")
                    _hotel.postValue(Helper.detailHotelResponse(restoObject))
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

    fun insertFavorite(favHotel: ModelHotel) = viewModelScope.launch(Dispatchers.IO) {
        database.insert(favHotel)
        updateFavorite(favHotel)
    }

    fun updateFavorite(favHotel: ModelHotel) = viewModelScope.launch(Dispatchers.IO) {
        val isFavorite = database.num(favHotel.id) > 0
        _isFavorite.postValue(isFavorite)
    }

    fun deleteFavorite(favHotel: ModelHotel) = viewModelScope.launch(Dispatchers.IO) {
        database.drop(favHotel.id)
        updateFavorite(favHotel)
    }
}