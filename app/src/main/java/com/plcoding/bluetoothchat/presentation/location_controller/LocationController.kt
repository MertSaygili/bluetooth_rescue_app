package com.plcoding.bluetoothchat.presentation.location_controller

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.provider.Settings.Global
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.logging.Handler

class LocationController{
    // is gps on or off, returns status of gps
    fun checkGPSIsOn(context: Context) : Boolean{
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    // gets current location of user
    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("MissingPermission")
    fun getCurrentCoordinates(fusedLocationClient: FusedLocationProviderClient, callback: (Location) -> Unit) {

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location?  ->
                // Got last known location. In some rare situations this can be null.
                Log.d("Success", "Location Text0 = latitude = ${location?.latitude} - longitude = ${location?.longitude}!")

                if (location != null) {
                    callback.invoke(location)
                }
            }
    }

}