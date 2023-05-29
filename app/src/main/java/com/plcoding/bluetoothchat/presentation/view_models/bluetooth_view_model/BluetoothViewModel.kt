package com.plcoding.bluetoothchat.presentation.view_models.bluetooth_view_model

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.plcoding.bluetoothchat.database.AppDatabase
import com.plcoding.bluetoothchat.domain.chat.BluetoothController
import com.plcoding.bluetoothchat.domain.chat.models.BluetoothDeviceDomain
import com.plcoding.bluetoothchat.domain.chat.ConnectionResult
import com.plcoding.bluetoothchat.domain.chat.models.BluetoothMessage
import com.plcoding.bluetoothchat.entities.Message
import com.plcoding.bluetoothchat.util.constants.Strings
import com.plcoding.bluetoothchat.util.time.Time
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class BluetoothViewModel @Inject constructor(private val bluetoothController: BluetoothController,@ApplicationContext application: Context): ViewModel() {
    // this file for bluetooth state control and bluetooth functions
    private val _state = MutableStateFlow(BluetoothUiState())
    val state = combine(bluetoothController.scannedDevices, bluetoothController.pairedDevices, _state) { scannedDevices, pairedDevices, state ->
        state.copy(
            scannedDevices = scannedDevices,
            pairedDevices = pairedDevices,
            messages = if(state.isConnected) state.messages else emptyList()
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)


    var db = Room.databaseBuilder(
        application,
        AppDatabase::class.java, Strings.db_name
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

    private var deviceConnectionJob: Job? = null
    private var device:BluetoothDeviceDomain? = null;

    init{
        bluetoothController.isConnected.onEach { isConnected->
            _state.update { it.copy(isConnected = isConnected) }
            _state.update {
                val messageDao = this.db.messageDao()

                val messages: List<Message> = messageDao.getMessagesBySender(device?.address.toString())
                val bluetoothMessages = arrayListOf<BluetoothMessage>();
                for(item:Message in messages){
                    if(item.isMe != null && item.content != null && item.sender != null){
                        val bluetoothMessage = BluetoothMessage(message=item.content, senderName = item.sender, isFromLocalUser = item.isMe, sendDate = item.time!!)
                        bluetoothMessages.add(bluetoothMessage);
                    }

                }
                it.copy(
                    messages = bluetoothMessages
                )
            }
        }.launchIn(viewModelScope)

        bluetoothController.errors.onEach {  error->
            _state.update { it.copy(errorMessage = error)}
        }.launchIn(viewModelScope)
    }

    // connects devices
    fun connectToDevice(device: BluetoothDeviceDomain) {
        _state.update { it.copy(isConnecting = true) }
        deviceConnectionJob = bluetoothController.connectToDevice(device).listen()
        this.device = device
    }

    // disconnect from device -- not works - unpairing
    fun disconnectFromDevice(device: BluetoothDeviceDomain){
        bluetoothController.disconnectFromBluetoothDevice(device)
        _state.update { it.copy(isConnected = false, isConnecting = false) }
    }

    // disconnect from devices -- works -- not unpairing
    fun disconnectFromDevice(){
        deviceConnectionJob?.cancel()
        bluetoothController.closeConnection()
        _state.update { it.copy(isConnected = false, isConnecting = false) }
    }

    // waits for incoming connection request
    fun waitForIncomingConnections() {
        _state.update { it.copy(isConnecting = true) }
        deviceConnectionJob = bluetoothController.startBluetoothServer().listen()
    }

    // sends message to other device
    fun sendMessage(message: String) {

        val currentDate = Time().getCurrentTimeForMessage()
        val messageDao = this.db.messageDao()
        val newMessage = Message(content=message,sender=this.device?.address.toString(),time=currentDate,isMe=true)
        messageDao.insertAll(newMessage)

        val messages: List<Message> = messageDao.getMessagesBySender(device?.address.toString())
        Log.d("Success",messages.toString())

        viewModelScope.launch {
            val bluetoothMessage = bluetoothController.trySendMessage(message)
            if(bluetoothMessage != null) {
                _state.update { it.copy(
                    messages = it.messages + bluetoothMessage
                ) }
            }
        }
    }
    // for discovery -- finding nearby devices
    fun startScan() {
        bluetoothController.startDiscovery()
    }

    // for discovery -- stops discovery
    fun stopScan() {
        bluetoothController.stopDiscovery()
    }

    private fun Flow<ConnectionResult>.listen(): Job {
        return onEach { result ->
            when(result) {
                ConnectionResult.ConnectionEstablished -> {
                    _state.update { it.copy(
                        isConnected = true,
                        isConnecting = false,
                        errorMessage = null
                    ) }
                }
                is ConnectionResult.TransferSucceeded -> {
                    _state.update { it.copy(
                        messages = it.messages + result.message
                    ) }
                    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                    val currentDate = sdf.format(Date())
                    val messageDao = db.messageDao()
                    val newMessage = Message(content=result.message.message,sender=device?.address.toString(),time=currentDate,isMe=false)
                    messageDao.insertAll(newMessage)

                    val messages: List<Message> = messageDao.getMessagesBySender(device?.address.toString())
                    Log.d("Come Messages",messages.toString())
                }
                is ConnectionResult.Error -> {
                    _state.update { it.copy(
                        isConnected = false,
                        isConnecting = false,
                        errorMessage = result.message
                    ) }
                }
            }
        }
            .catch { throwable ->
                bluetoothController.closeConnection()
                _state.update { it.copy(
                    isConnected = false,
                    isConnecting = false,
                ) }
            }
            .launchIn(viewModelScope)
    }

    // clear all bluetooth devices -- not used in app
    override fun onCleared() {
        super.onCleared()
        bluetoothController.release()
    }
}