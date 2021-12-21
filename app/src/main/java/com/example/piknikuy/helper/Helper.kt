package com.example.piknikuy.helper

import com.example.piknikuy.model.ModelHotel
import com.example.piknikuy.model.ModelResto
import com.example.piknikuy.model.ModelWisata
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
                dataResto.location = item.getString("location")
                dataResto.picture = item.getString("picture")
                listResto.add(dataResto)
            }
            return listResto
        }

        fun detailRestoResponse(item: JSONObject): ModelResto {
            val dataResto = ModelResto()
            dataResto.id = item.getString("id")
            dataResto.picture = item.getString("picture")
            dataResto.name = item.getString("name")
            dataResto.location = item.getString("location")
            dataResto.overview = item.getString("overview")
            dataResto.rating = item.getString("rating")
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

        //for wisata
        fun listWisataResponse(items: JSONArray): ArrayList<ModelWisata> {
            val listWisata = ArrayList<ModelWisata>()
            for (i in 0 until items.length()) {
                val item = items.getJSONObject(i)
                val dataWisata = ModelWisata()
                dataWisata.id = item.getString("id")
                dataWisata.name = item.getString("name")
                dataWisata.city = item.getString("city")
                dataWisata.picture = item.getString("picture")
                listWisata.add(dataWisata)
            }
            return listWisata
        }

        fun detailWisataResponse(item: JSONObject): ModelWisata {
            val dataWisata = ModelWisata()
            dataWisata.id = item.getString("id")
            dataWisata.picture = item.getString("picture")
            dataWisata.name = item.getString("name")
            dataWisata.city = item.getString("city")
            dataWisata.description = item.getString("description")
            dataWisata.rating = item.getString("rating")
            dataWisata.address = item.getString("address")
            return dataWisata
        }
    }
}