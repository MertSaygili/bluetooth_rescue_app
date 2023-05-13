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

    // system provides BluetoothManager
    private val bluetoothManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }

    // contains all functionality of Bluetooth, start server, cancel server etc.
    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    // MutableStateFlow needs initial value so we give emptyList
    private val _scannedDevices = MutableStateFlow<List<BluetoothDeviceDomain>>(emptyList())
    override val scannedDevices: StateFlow<List<BluetoothDeviceDomain>>  get() = _scannedDevices.asStateFlow() // makes _scanned devices only readable

    // MutableStateFlow needs initial value so we give emptyList
    private val _pairedDevices = MutableStateFlow<List<BluetoothDeviceDomain>>(emptyList())
    override val pairedDevices: StateFlow<List<BluetoothDeviceDomain>>  get() = _pairedDevices.asStateFlow() // makes _paired devices only readable

    // if finds new device add it to _scannedDevices list
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
        // check permission
        if(!hasPermissions(Manifest.permission.BLUETOOTH_CONNECT)){
            return
        }
        // if intent is ACTION_FOUND executes foundDeviceReceiver code
        context.registerReceiver(
            foundDeviceReceiver,
            IntentFilter(BluetoothDevice.ACTION_FOUND)
        )

        // updates paired devices
        updatePairedDevices()

        // start discovery
        bluetoothAdapter?.startDiscovery()
    }

    @SuppressLint("MissingPermission")
    override fun stopDiscovery() {
        // checks permission
        if(!hasPermissions(Manifest.permission.BLUETOOTH_SCAN)){
            return
        }

        // cancels bluetoothAdapter, for discovery
        bluetoothAdapter?.cancelDiscovery()
    }

    override fun release() {
        context.unregisterReceiver(foundDeviceReceiver)
    }

    @SuppressLint("MissingPermission")
    private fun updatePairedDevices() {
        // first checks permission
        if(!hasPermissions(Manifest.permission.BLUETOOTH_CONNECT)) {
            return
        }
        // then map bluetooth devices to BluetoothDeviceDomain
        bluetoothAdapter?.bondedDevices?.map {
            it.toBluetoothDeviceDomain()
        }?.also {
            _pairedDevices.update { it }
        }
    }

    // Checks given permission
    private fun hasPermissions(permission: String) : Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

}