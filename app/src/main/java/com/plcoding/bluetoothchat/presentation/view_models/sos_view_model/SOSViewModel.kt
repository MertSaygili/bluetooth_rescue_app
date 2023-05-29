package com.plcoding.bluetoothchat.presentation.view_models.sos_view_model

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.plcoding.bluetoothchat.domain.chat.SOSMessageController
import com.plcoding.bluetoothchat.domain.chat.models.BluetoothDevice
import com.plcoding.bluetoothchat.presentation.location_controller.LocationController
import com.plcoding.bluetoothchat.util.constants.MACAddresses
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SOSViewModel @Inject constructor(private val sosMessageController: SOSMessageController, @ApplicationContext application: Context): ViewModel() {
    // view model for SOS message
    // private val bluetoothController: BluetoothController
    private val _state = MutableStateFlow(SOSUiState())
    val state = combine(sosMessageController.foundDevices, _state) { devices, state ->
        state.copy(
            devices = devices
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(4000), _state.value)

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)
    private var locationCoordinates : String = ""
    lateinit var location : Unit

    private fun startDiscovery() {
        // scans nearby devices
        sosMessageController.startDiscovery()
    }

    private fun stopDiscovery() {
        // stops to scanning nearby devices
        sosMessageController.stopDiscovery()
    }

    fun searchDevice() {
        location = LocationController().getCurrentCoordinates(fusedLocationClient = fusedLocationClient) {location ->
            locationCoordinates = "latitude = ${location?.latitude} - longitude = ${location?.longitude}!"
        }


        // for show to loading screen, makes isLoading attribute true
        _state.update { it.copy(isLoading = true) }
        // scanning nearby devices to find arduino device
        startDiscovery()
        Handler().postDelayed(
             {
                 // will search nearby arduino devices for four seconds
                 val devices: List<BluetoothDevice> = filterBluetoothDevices(sosMessageController.foundDevices.value)

                 _state.update { it.copy(
                     devices = devices
                 ) }

                 check()
             }, 4000
        )
    }

    fun connectToDevice(device : BluetoothDevice) {
//        Log.d("Success", "before function call...")
        sosMessageController.connectToDevice(device = device)

        Log.d("Success", "last = $locationCoordinates")
        // message send
        if(sosMessageController.sendLocation(location = locationCoordinates)) {
            // make location send attribute true
            _state.update { it.copy(
                locationSend = true
            ) }
        }
        // error occur while sending location
        else{
            _state.update { it.copy(
                errorMessage = "Error occur"
            ) }
        }
        // wait 2 second then close socket
        Handler().postDelayed({
            sosMessageController.closeSocketConnection()
        }, 2000)

    }

    private fun check() {
        if ((_state.value.devices).isEmpty()) {
            // arduino device not found
            _state.update { it.copy(notFindAnyDevice = true, isLoading = false, devices = _state.value.devices) }
        } else {
            // arduino device found
            _state.update { it.copy(isFindDevice = true, isLoading = false, devices = _state.value.devices) }
        }
        stopDiscovery()
    }

     fun filterBluetoothDevices(bluetoothDevices : List<BluetoothDevice>) : List<BluetoothDevice> {
        // gets all paired devices and nearby devices
        // then filtering them
        // takes only arduino devices
        val macAddresses = MACAddresses.macAddressOfArduinos
        var filteredBluetoothDevices : List<BluetoothDevice> = emptyList()
        for( device in bluetoothDevices) {
            if(macAddresses.contains(device.address)){
                filteredBluetoothDevices = filteredBluetoothDevices + device
            }
        }
        print(filteredBluetoothDevices)
        return filteredBluetoothDevices
    }
}