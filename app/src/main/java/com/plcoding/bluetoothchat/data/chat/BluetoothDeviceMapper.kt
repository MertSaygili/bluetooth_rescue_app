package com.plcoding.bluetoothchat.data.chat

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.plcoding.bluetoothchat.domain.chat.models.BluetoothDeviceDomain

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain(): BluetoothDeviceDomain {
    return BluetoothDeviceDomain(
        name = name,
        address = address
    )
}