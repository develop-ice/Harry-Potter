package com.gmail.orlandroyd.myapplication.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat
import com.gmail.orlandroyd.myapplication.R
import com.google.android.material.snackbar.Snackbar
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Utility classes for doing stuffs such as checking if network is available etc
 */

object Utility {

    /**
     * Method to format date
     */
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @SuppressLint("SimpleDateFormat")
    fun getFromDateTime(
        dateTime: String,
        dateFormat: String = "yyyy-MM-dd",
        field: String = "dd/MM/yyyy"
    ): String {
        val input = SimpleDateFormat(dateFormat)
        val output = SimpleDateFormat(field)
        try {
            val getAbbreviate = input.parse(dateTime)
            return output.format(getAbbreviate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return "-"
    }

    /**
     * check if network is connected
     */
    @Suppress("DEPRECATION")
    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    /**
     *  SNACK-BAR UI
     */
    @SuppressLint("ShowToast")
    fun showSnack(
        view: View,
        msg: String,
        background: Int,
        duration: Int = Snackbar.LENGTH_SHORT,
        textColor: Int = R.color.grey_1000
    ) {
        Snackbar.make(
            view,
            msg,
            duration
        )
            .withColor(ContextCompat.getColor(view.context, background))
            .setTextColor(ContextCompat.getColor(view.context, textColor))
            .show()
    }

}