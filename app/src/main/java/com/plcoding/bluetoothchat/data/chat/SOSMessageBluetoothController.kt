package com.plcoding.bluetoothchat.data.chat

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.IntentFilter
import android.util.Log
import com.plcoding.bluetoothchat.domain.chat.SOSMessageController
import com.plcoding.bluetoothchat.domain.chat.models.BluetoothDevice
import com.plcoding.bluetoothchat.domain.chat.models.BluetoothDeviceDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.OutputStream
import java.util.*


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

    // if android phone find new device add them to devices list
    private val foundDeviceReceiver = FoundDeviceReceiver { device ->
        _foundDevices.update { devices ->
            val newDevice = device.toBluetoothDeviceDomain()
            if(newDevice in devices) devices else devices + newDevice
        }
    }

    // for connecting to different device
    private val bluetoothStateReceiver =  BluetoothStateReceiver { isConnected, bluetoothDevice ->
        if(bluetoothAdapter?.bondedDevices?.contains(bluetoothDevice) == true){
            _isConnected.update { isConnected }
        } else{
            CoroutineScope(Dispatchers.IO).launch {
                _errors.tryEmit("Can't cnnect to a non-paired device")
            }
        }
    }

    // bluetooth socket for communication between devices
    private var bluetoothSocket : BluetoothSocket? = null

    init {
        // first updates paired devices then try to find new devices
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

    // searches nearby devices
    override fun startDiscovery() {
        // if device found
        context.registerReceiver(
            foundDeviceReceiver,
            IntentFilter(android.bluetooth.BluetoothDevice.ACTION_FOUND)
        )

        updatePairedDevices()

        bluetoothAdapter?.startDiscovery()
    }

    // for connecting android device to arduino device
    // creates bluetooth socket for sendin message
    override fun connectToDevice(device: BluetoothDevice){
//        Log.d("Success", "before socket")

        // connecting socket
        // takes device macAddress and SERVICE_UUID string
        bluetoothSocket = bluetoothAdapter
            ?.getRemoteDevice(device.address)
            ?.createRfcommSocketToServiceRecord((
                UUID.fromString(SERVICE_UUID)
            ))
        stopDiscovery()

//        Log.d("Success", "after socket")


        // if bluetooth socket is not null, connect with device
        // if bluetooth socket is null, close socket
        bluetoothSocket?.let { socket ->
            try {
                socket.connect()
                Log.d("Success", "Socket connected")

            } catch(e: IOException) {
                closeSocketConnection()
                Log.d("Success", e.message.toString())
            }
        }

    }


    // stops discovering nearby devices
    override fun stopDiscovery() {
        bluetoothAdapter?.cancelDiscovery()
    }

    // closes bluetooth socket
    override fun closeSocketConnection() {
        bluetoothSocket?.close()
    }

    // sends location to arduino device
     override fun sendLocation (location: String) : Boolean{
        // gets bluetoothSocket output stream and assign yo outStream variable
        val outStream : OutputStream?

        try {
            outStream = bluetoothSocket?.outputStream
        } catch (e: IOException) {
             Log.d("Success", "Bug BEFORE Sending stuff", e)
            // error occur, probably connection error
            return false
        }
        // converts message (location) to byte array
        val msgBuffer = location.toByteArray()

         return try {
             // sends location to arduino device
             outStream?.write(msgBuffer)
             Log.d("Success", "Location send")
             true

         } catch (e: IOException) {
              Log.d("Success", "Bug while sending stuff", e)
             // error occur, probably socket error
             false
         }
         // message send successfully

    }

    // update paired devices
    private fun updatePairedDevices() {
        bluetoothAdapter
            ?.bondedDevices
            ?.map { it.toBluetoothDeviceDomain() }
            ?.also { devices ->
                _foundDevices.update { devices }
            }
    }

    // UUID for connect devices
    companion object{
        const val SERVICE_UUID = "00001101-0000-1000-8000-00805f9b34fb"
    }
}