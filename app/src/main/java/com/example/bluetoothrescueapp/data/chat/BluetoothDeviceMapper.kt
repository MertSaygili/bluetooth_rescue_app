package com.example.bluetoothrescueapp.data.chat


import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.example.bluetoothrescueapp.domain.BluetoothDeviceDomain

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain() : BluetoothDeviceDomain{
    // Converts BluetoothDevice instance to BluetoothDeviceDomain
    return BluetoothDeviceDomain(
        name = name,
        address = address
    )
}