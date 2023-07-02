package com.daniel.workmanagerapp.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkChecker constructor(private val context: Context) {


    fun hasConnectivity() : Boolean {
        val connectivityManager =
            context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork
        val network = connectivityManager.getNetworkCapabilities(activeNetwork)!!
        val result = when {
            network.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    network.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    network.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                true
            }

            else -> {
                false
            }
        }
        return result
    }
}