package com.plcoding.bluetoothchat.presentation.location_controller

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.location.FusedLocationProviderClient

class LocationController{
    fun checkGPSOn(context: Context) : Boolean{
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    fun getCurrentCoordinates(fusedLocationClient: FusedLocationProviderClient) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                Log.d("Success", location?.latitude.toString() + " - " + location?.longitude.toString() )
                // Got last known location. In some rare situations this can be null.
            }
    }

}