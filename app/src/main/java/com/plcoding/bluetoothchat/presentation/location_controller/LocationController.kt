package com.plcoding.bluetoothchat.presentation.location_controller

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient

class LocationController{
    // is gps on or off, returns status of gps
    fun checkGPSIsOn(context: Context) : Boolean{
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    // gets current location of user
    @SuppressLint("MissingPermission")
    fun getCurrentCoordinates(fusedLocationClient: FusedLocationProviderClient) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                Log.d("Success", location?.latitude.toString() + " - " + location?.longitude.toString() )
                // Got last known location. In some rare situations this can be null.
            }
    }

}