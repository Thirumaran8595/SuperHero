package com.thirumaran.superhero.service.repo

import androidx.lifecycle.MutableLiveData
import com.android.volley.Request.Method.GET
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.reflect.TypeToken
import com.thirumaran.superhero.BuildConfig
import com.thirumaran.superhero.service.model.ErrorDetailPojo
import com.thirumaran.superhero.service.model.HeroDetailsPojo
import com.thirumaran.superhero.service.model.ResultsItem
import com.thirumaran.superhero.service.repo.volley.VolleyApplication
import org.json.JSONObject

object SearchApi {

    fun getHeroAPI(value: String,
        herodetail: MutableLiveData<List<ResultsItem>>,
        errorDetail: MutableLiveData<ErrorDetailPojo>) {

        val responseListener = Response.Listener { response: JSONObject ->

            if (response.has("error")) {
                val responseType = object : TypeToken<ErrorDetailPojo>() {}.type
                val errorResponse = VolleyApplication.newInstance().getErrorJsontoGson(
                    response = response.toString(),
                    responseType = responseType
                )
                errorDetail.value = errorResponse

            } else if (response.has("response") && response.get("response") == "success") {
                val responseType = object : TypeToken<HeroDetailsPojo>() {}.type
                val finalResponse = VolleyApplication.newInstance().getJsontoGson(
                    response = response.toString(),
                    responseType = responseType
                )
                herodetail.value = finalResponse.results
            }

        }


        val errorListener = Response.ErrorListener { error ->
        }


        //Network Call
        val jsonObject = JsonObjectRequest(
            GET,
            BuildConfig.BaseURL + "/search/" + value,
            null,
            responseListener,
            errorListener
        )
        VolleyApplication.newInstance().addToRequestQueue(jsonObject)


    }

}