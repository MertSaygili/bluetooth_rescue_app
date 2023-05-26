package com.plcoding.bluetoothchat.data.chat

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.IntentFilter
import com.plcoding.bluetoothchat.domain.chat.ConnectionResult
import com.plcoding.bluetoothchat.domain.chat.SOSMessageController
import com.plcoding.bluetoothchat.domain.chat.models.BluetoothDevice
import com.plcoding.bluetoothchat.domain.chat.models.BluetoothDeviceDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


@SuppressLint("MissingPermission")
class SOSMessageBluetoothController(private val context: Context) : SOSMessageController {

    private val bluetoothManager by lazy { context.getSystemService(BluetoothManager::class.java) }
    private val bluetoothAdapter by lazy { bluetoothManager?.adapter }

    private val _foundDevices = MutableStateFlow<List<BluetoothDeviceDomain>> (emptyList())
    override val foundDevices: StateFlow<List<BluetoothDevice>> get() = _foundDevices.asStateFlow()

    private val _isConnected = MutableStateFlow<Boolean>(value = false)
    override val isConnected: StateFlow<Boolean> get() = _isConnected.asStateFlow()

    private val _errors = MutableSharedFlow<String>()
    override val errors: SharedFlow<String> get() = _errors.asSharedFlow()

    private val foundDeviceReceiver = FoundDeviceReceiver { device ->
        _foundDevices.update { devices ->
            val newDevice = device.toBluetoothDeviceDomain()
            if(newDevice in devices) devices else devices + newDevice
        }
    }

    private val bluetoothStateReceiver =  BluetoothStateReceiver { isConnected, bluetoothDevice ->
        if(bluetoothAdapter?.bondedDevices?.contains(bluetoothDevice) == true){
            _isConnected.update { isConnected }
        } else{
            CoroutineScope(Dispatchers.IO).launch {
                _errors.tryEmit("Can't cnnect to a non-paired device")
            }
        }
    }

    init {
        updatePairedDevices()
        context.registerReceiver(
            bluetoothStateReceiver,
            IntentFilter().apply {
                addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)
                addAction(android.bluetooth.BluetoothDevice.ACTION_ACL_CONNECTED)
                addAction(android.bluetooth.BluetoothDevice.ACTION_ACL_DISCONNECTED)
            }
        )
    }

    override fun startDiscovery() {
        context.registerReceiver(
            foundDeviceReceiver,
            IntentFilter(android.bluetooth.BluetoothDevice.ACTION_FOUND)
        )

        updatePairedDevices()

        bluetoothAdapter?.startDiscovery()
    }

    override fun connectToDevice(device: BluetoothDevice): Flow<ConnectionResult> {
        TODO("Not yet implemented")
    }

    override fun stopDiscovery() {
        bluetoothAdapter?.cancelDiscovery()
    }

    private fun updatePairedDevices() {
        bluetoothAdapter
            ?.bondedDevices
            ?.map { it.toBluetoothDeviceDomain() }
            ?.also { devices ->
                _foundDevices.update { devices }
            }
    }

    companion object{
        const val SERVICE_UUID = "57dc2ee4-f48e-11ed-a05b-0242ac120003"
    }
}