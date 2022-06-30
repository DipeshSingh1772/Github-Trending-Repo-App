package com.example.zoho_project.Utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

//this class will check that we are connected to internet or not
@Suppress("DEPRECATION")
class MyUtils {
    companion object{
        @SuppressLint("ObsoleteSdkInt")
        fun isInternetAvailable(context:Context): Boolean? {
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    return this.getNetworkCapabilities(this.activeNetwork)?.hasCapability(
                        NetworkCapabilities.NET_CAPABILITY_INTERNET)
                else{
                    return this.activeNetworkInfo?.isConnected?:false
                }
            }
        }
    }
}