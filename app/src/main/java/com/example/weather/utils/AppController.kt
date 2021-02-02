package com.example.weather.utils

import android.app.Application
import android.text.TextUtils
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

class AppController : Application() {

    val TAG = AppController::class.java
        .simpleName
    var mRequestQueue: RequestQueue? = null
    var mImageLoader: ImageLoader? = null
    var mInstance: AppController? = null

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

    fun getInstance(): AppController? {
        return mInstance
    }

    fun getRequestQueue(): RequestQueue? {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext())
        }
        return mRequestQueue
    }

    fun getImageLoader(): ImageLoader? {
        getRequestQueue()
        if (mImageLoader == null) {
            mImageLoader = ImageLoader(
                this.mRequestQueue,
                LruBitmapCache()
            )
        }
        return this.mImageLoader
    }

    fun <T> addToRequestQueue(req: Request<T>, tag: String?) {
        // set the default tag if tag is empty
        req.tag = if (TextUtils.isEmpty(tag)) TAG else tag
        getRequestQueue()!!.add(req)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        req.tag = TAG
        getRequestQueue()!!.add(req)
    }

    fun cancelPendingRequests(tag: Any?) {
        if (mRequestQueue != null) {
            mRequestQueue!!.cancelAll(tag)
        }
    }
}