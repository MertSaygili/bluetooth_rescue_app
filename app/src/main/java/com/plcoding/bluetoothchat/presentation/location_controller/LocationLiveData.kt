package com.plcoding.bluetoothchat.presentation.location_controller//package com.plcoding.bluetoothchat.presentation
//
//import android.Manifest
//import android.content.Context
//import android.content.pm.PackageManager
//import android.location.Location
//import android.os.Looper
//import androidx.core.app.ActivityCompat
//import androidx.lifecycle.LiveData
//import com.google.android.gms.location.LocationCallback
//import com.google.android.gms.location.LocationRequest
//import com.google.android.gms.location.LocationResult
//import com.google.android.gms.location.LocationServices
//import com.plcoding.bluetoothchat.domain.chat.LocationDetails
//
//class LocationLiveData (var context: Context) : LiveData<LocationDetails>(){
//    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
//
//    override fun onActive() {
//        super.onActive()
//        if (ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        }
//        fusedLocationClient.lastLocation.addOnSuccessListener { location->
//            location.also {
//                setLocationData(it)
//            }
//        }
//
//        startLocationUpdates()
//    }
//
//    internal fun startLocationUpdates() {
//        if (ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }
//        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
//    }
//
//    private fun setLocationData(location: Location?){
//        // occur if only location is not null
//        location?.let {
//            value = LocationDetails(location.longitude.toString(), location.latitude.toString())
//
//        }
//    }
//
//    override fun onInactive() {
//        super.onInactive()
//        fusedLocationClient.removeLocationUpdates(locationCallback)
//    }
//
//    private val locationCallback = object: LocationCallback(){
//        override fun onLocationResult(locationResult: LocationResult){
//            super.onLocationResult(locationResult)
//            locationResult ?: return
//            for(location in locationResult.locations) {
//                setLocationData(location)
//            }
//        }
//    }
//
//    companion object{
//        private const val ONE_MINUTE : Long = 60000
//        val locationRequest: LocationRequest = LocationRequest.create().apply{
//            interval = ONE_MINUTE
//            fastestInterval = ONE_MINUTE/4
//            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//        }
//    }
//}