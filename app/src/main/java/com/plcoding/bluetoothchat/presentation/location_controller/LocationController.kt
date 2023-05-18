package com.plcoding.bluetoothchat.presentation.location_controller

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient

class LocationController{

    @SuppressLint("MissingPermission")
    fun getCurrentCoordinates(fusedLocationClient: FusedLocationProviderClient) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                Log.d("Success", location?.latitude.toString() + " - " + location?.longitude.toString() )
                // Got last known location. In some rare situations this can be null.
            }
    }

}