package com.example.piknikuy.helper

import com.example.piknikuy.model.ModelHotel
import com.example.piknikuy.model.ModelResto
import org.json.JSONArray
import org.json.JSONObject

class Helper {

    companion object {
        //for restaurant
        fun listRestoResponse(items: JSONArray): ArrayList<ModelResto> {
            val listResto = ArrayList<ModelResto>()
            for (i in 0 until items.length()) {
                val item = items.getJSONObject(i)
                val dataResto = ModelResto()
                dataResto.id = item.getString("id")
                dataResto.name = item.getString("name")
                dataResto.city = item.getString("city")
                dataResto.pictureId = item.getString("pictureId")
                listResto.add(dataResto)
            }
            return listResto
        }

        fun detailRestoResponse(item: JSONObject): ModelResto {
            val dataResto = ModelResto()
            dataResto.id = item.getString("id")
            dataResto.pictureId = item.getString("pictureId")
            dataResto.name = item.getString("name")
            dataResto.city = item.getString("city")
            dataResto.description = item.getString("description")
            dataResto.rating = item.getString("rating")
            dataResto.address = item.getString("address")
            return dataResto
        }

        //for hotel
        fun listHotelResponse(items: JSONArray): ArrayList<ModelHotel> {
            val listHotel = ArrayList<ModelHotel>()
            for (i in 0 until items.length()) {
                val item = items.getJSONObject(i)
                val dataHotel = ModelHotel()
                dataHotel.id = item.getString("id")
                dataHotel.name = item.getString("name")
                dataHotel.city = item.getString("city")
                dataHotel.picture = item.getString("picture")
                listHotel.add(dataHotel)
            }
            return listHotel
        }

        fun detailHotelResponse(item: JSONObject): ModelHotel {
            val dataHotel = ModelHotel()
            dataHotel.id = item.getString("id")
            dataHotel.picture = item.getString("picture")
            dataHotel.name = item.getString("name")
            dataHotel.city = item.getString("city")
            dataHotel.description = item.getString("description")
            dataHotel.rating = item.getString("rating")
            return dataHotel
        }
    }
}