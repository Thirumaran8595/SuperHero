package com.thirumaran.superhero.service.repo.volley

import android.app.Application
import android.content.Context
import android.text.TextUtils
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.thirumaran.superhero.service.model.ErrorDetailPojo
import com.thirumaran.superhero.service.model.HeroDetailsPojo
import java.lang.reflect.Type

class VolleyApplication : Application() {

    private var requestQueue: RequestQueue? = null
    private val TAG: String = VolleyApplication::class.java.simpleName
    var context: Context? = null

    companion object {
        @JvmStatic
        var mInstance: VolleyApplication? = null

        @JvmStatic
        fun newInstance(): VolleyApplication {
            return mInstance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        context = applicationContext
    }

    @JvmName("getRequestQueue1")
    fun getRequestQueue(): RequestQueue {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context)

        return requestQueue!!
    }

    fun <T> addToRequestQueue(req: Request<T>, tag: String?) {
        req.tag = if (TextUtils.isEmpty(tag)) "addToRequestQueue" else tag
        getRequestQueue().add(req)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        req.tag = TAG
        getRequestQueue().add(req)
    }


    fun cancelPendingRequest(tag: Any) {
        requestQueue?.cancelAll(tag)
    }

    fun getJsontoGson(response: String, responseType: Type) : HeroDetailsPojo{
        return Gson().fromJson(response, responseType)
    }

    fun getErrorJsontoGson(response: String, responseType: Type) : ErrorDetailPojo{
        return Gson().fromJson(response, responseType)
    }
}