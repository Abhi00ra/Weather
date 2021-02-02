package com.example.weather.utils

import android.content.Context
import android.widget.Toast
import com.example.weather.R
import java.util.logging.Level.WARNING

object AppUtils {


    fun toast(context: Context, message: String) {
        if (message.isNullOrEmpty()) {
            val msg = context.getString(R.string.something_went_wrong)
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}