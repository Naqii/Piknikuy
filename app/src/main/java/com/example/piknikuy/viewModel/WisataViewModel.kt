package com.example.piknikuy.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piknikuy.api.ApiConfig
import com.example.piknikuy.database.PiknikuyDatabase
import com.example.piknikuy.helper.Helper
import com.example.piknikuy.model.ModelWisata
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class WisataViewModel: ViewModel() {

    private var database = PiknikuyDatabase.getDatabase().wisataDao()

    private var _listWisata = MutableLiveData<ArrayList<ModelWisata>>()
    val listWisata : LiveData<ArrayList<ModelWisata>> = _listWisata

    private var _wisata = MutableLiveData<ModelWisata>()
    val wisata : LiveData<ModelWisata> = _wisata

    private var _isFavorite = MutableLiveData<Boolean>()
    val isFavorite : LiveData<Boolean> = _isFavorite

    private var _favorite = database.select()
    val favorite : LiveData<List<ModelWisata>> = _favorite

    fun setSearchWisata(query: String? = null) {
        if (query == null){
            //jika sudah ada API Wisata bisa diganti untuk sementara pakai API Resto
            ApiConfig.getListResto( object: AsyncHttpResponseHandler(){
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray
                ) {
                    val result = String(responseBody)
                    try {
                        val responseObject = JSONObject(result)
                        val wisataArray = responseObject.getJSONArray("wisata")
                        _listWisata.postValue(Helper.listWisataResponse(wisataArray))
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
            ApiConfig.getSearchResto(query, object: AsyncHttpResponseHandler(){
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray
                ) {
                    val result = String(responseBody)
                    try {
                        val responseObject = JSONObject(result)
                        val wisataArray = responseObject.getJSONArray("wisata")
                        _listWisata.postValue(Helper.listWisataResponse(wisataArray))
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
    }

    fun setDetailWisata(id: String) {
        ApiConfig.getDetailResto(id, object: AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val wisataObject = responseObject.getJSONObject("wisata")
                    _wisata.postValue(Helper.detailWisataResponse(wisataObject))
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

    fun insertFavorite(favWisata: ModelWisata) = viewModelScope.launch(Dispatchers.IO) {
        database.insert(favWisata)
        updateFavorite(favWisata)
    }

    fun updateFavorite(favWisata: ModelWisata) = viewModelScope.launch(Dispatchers.IO) {
        val isFavorite = database.num(favWisata.id) > 0
        _isFavorite.postValue(isFavorite)
    }

    fun deleteFavorite(favWisata: ModelWisata) = viewModelScope.launch(Dispatchers.IO) {
        database.drop(favWisata.id)
        updateFavorite(favWisata)
    }
}