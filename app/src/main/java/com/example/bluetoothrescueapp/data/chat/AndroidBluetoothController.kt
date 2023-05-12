package com.example.bluetoothrescueapp.data.chat

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import com.example.bluetoothrescueapp.domain.BluetoothController
import com.example.bluetoothrescueapp.domain.BluetoothDeviceDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AndroidBluetoothController(
    private val context: Context
) : BluetoothController {

    private val bluetoothManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }

    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    // MutableStateFlow needs initial value so we give emptyList
    private val _scannedDevices = MutableStateFlow<List<BluetoothDeviceDomain>>(emptyList())
    override val scannedDevices: StateFlow<List<BluetoothDeviceDomain>>  get() = _scannedDevices.asStateFlow() // makes _scanned devices only readable

    // MutableStateFlow needs initial value so we give emptyList
    private val _pairedDevices = MutableStateFlow<List<BluetoothDeviceDomain>>(emptyList())
    override val pairedDevices: StateFlow<List<BluetoothDeviceDomain>>  get() = _pairedDevices.asStateFlow() // makes _paired devices only readable

    private val foundDeviceReceiver = FoundDeviceReceiver{ device ->
        _scannedDevices.update { devices ->
            val newDevice = device.toBluetoothDeviceDomain()
            if(newDevice in devices) devices else devices + newDevice
        }
    }
    init {
        updatePairedDevices()
    }

    @SuppressLint("MissingPermission")
    override fun startDiscovery() {
        if(!hasPermissions(Manifest.permission.BLUETOOTH_CONNECT)){
            return
        }
        context.registerReceiver(
            foundDeviceReceiver,
            IntentFilter(BluetoothDevice.ACTION_FOUND)
        )
        updatePairedDevices()

        bluetoothAdapter?.startDiscovery()
    }

    @SuppressLint("MissingPermission")
    override fun stopDiscovery() {
        if(!hasPermissions(Manifest.permission.BLUETOOTH_SCAN)){
            return
        }

        bluetoothAdapter?.cancelDiscovery()
    }

    override fun release() {
        context.unregisterReceiver(foundDeviceReceiver)
    }

    @SuppressLint("MissingPermission")
    private fun updatePairedDevices() {
        if(!hasPermissions(Manifest.permission.BLUETOOTH_CONNECT)) {
            return
        }

        bluetoothAdapter?.bondedDevices?.map {
            it.toBluetoothDeviceDomain()
        }?.also {devices ->
            _pairedDevices.update { devices }
        }
    }

    private fun hasPermissions(permission: String) : Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

}