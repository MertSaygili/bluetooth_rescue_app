package com.plcoding.bluetoothchat.presentation.components

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.plcoding.bluetoothchat.R
import com.plcoding.bluetoothchat.util.constants.Strings
import com.plcoding.bluetoothchat.presentation.view_models.bluetooth_view_model.BluetoothViewModel
import com.plcoding.bluetoothchat.presentation.components.screen.*
import com.plcoding.bluetoothchat.presentation.view_models.sos_view_model.SOSViewModel
import com.plcoding.bluetoothchat.util.toasts.CustomToasts
import com.shashank.sony.fancytoastlib.FancyToast


@Composable
fun Navigation(context: Context) {
    val navController = rememberNavController()
    val customToasts : CustomToasts = CustomToasts()

    // bluetooth view model state
    val viewModel = hiltViewModel<BluetoothViewModel>()
    val state by viewModel.state.collectAsState()

    // navigation for app
    NavHost(navController = navController, startDestination = "splash_screen") {
        // opens splash screen immediately
        composable(route = Strings.splash_route_name){
            SplashScreen(navController = navController)
        }
        // home screen route


        composable(route = Strings.home_route_name){
            val sosViewModel = hiltViewModel<SOSViewModel>()
            val stateSOS by sosViewModel.state.collectAsState()

            LaunchedEffect(key1 = stateSOS.errorMessage) {
                stateSOS.errorMessage?.let{
                    customToasts.customToast(context, it, FancyToast.DEFAULT)
                }
            }

            LaunchedEffect(key1 = stateSOS.notFindAnyDevice) {
                if(stateSOS.notFindAnyDevice == true) {
                    customToasts.customToast(context, Strings.notFindSOSDevice, FancyToast.ERROR)
                }
            }

            LaunchedEffect(key1 = stateSOS.locationSend) {
                if(stateSOS.locationSend){
                    customToasts.customToast(context, Strings.locationSend, FancyToast.SUCCESS)
                }
            }

            when{
                stateSOS.isLoading -> {
                    HomeScreen(
                        navController = navController,
                        searchDevice = sosViewModel::searchDevice,
                        isSearchingDevice = true,
                        showArduinoDevices = false,
                        filterFunction = sosViewModel::filterBluetoothDevices,
                        stateSOS = stateSOS,
                        connectToDevice = sosViewModel::connectToDevice,
                    )
                }
                stateSOS.isFindDevice -> {
                    Log.d("Success", "00${stateSOS.devices}")

                    HomeScreen(
                        navController = navController,
                        searchDevice = sosViewModel::searchDevice,
                        isSearchingDevice = false,
                        showArduinoDevices = true,
                        filterFunction = sosViewModel::filterBluetoothDevices,
                        stateSOS = stateSOS,
                        connectToDevice = sosViewModel::connectToDevice,
                    )
                }
                else -> {
                    HomeScreen(
                        navController = navController,
                        searchDevice = sosViewModel::searchDevice,
                        isSearchingDevice = false,
                        showArduinoDevices = false,
                        filterFunction = sosViewModel::filterBluetoothDevices,
                        stateSOS = stateSOS,
                        connectToDevice = sosViewModel::connectToDevice,
                    )
                }
            }
        }
        // nearby bluetooth devices route
        composable(route = Strings.bluetooth_devices_route_name) {

            // show error message if error occur
            LaunchedEffect(key1 = state.errorMessage) {
                state.errorMessage?.let{
                    customToasts.customToast(context, it, FancyToast.DEFAULT)
                }
            }

            // when devices are connect shows this message
            LaunchedEffect(key1 = state.isConnected) {
                if(state.isConnected) {
                    customToasts.customToast(context, Strings.connectionMade, FancyToast.DEFAULT)
                }
            }

            // state control
            when{
                // if devices are connecting then show LoadingScreen()
                state.isConnecting -> {
                    LoadingScreen(titleID = R.string.connecting)
                }
                // if devices are connected shows ChatScreen
                state.isConnected -> {
                    ChatScreen(
                        state = state,
                        onDisconnect = viewModel::disconnectFromDevice,
                        onSendMessage = viewModel::sendMessage,
                        context = context
                    )
                }
                // in else state shows device screen
                else -> {
                    DeviceScreen(
                        state = state,
                        onStartScan = viewModel::startScan,
                        onStopScan = viewModel::stopScan,
                        onStartServer = viewModel::waitForIncomingConnections,
                        connectToDevice = viewModel::connectToDevice,
                        context = context
                    )
                }
            }
        }
    }
}
