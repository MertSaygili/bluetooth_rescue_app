package com.plcoding.bluetoothchat.presentation.components

import android.content.Context
import android.widget.Toast
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
import com.plcoding.bluetoothchat.util.constants.Strings
import com.plcoding.bluetoothchat.presentation.bluetooth_view_model.BluetoothViewModel
import com.plcoding.bluetoothchat.presentation.components.screen.*


@Composable
fun Navigation(context: Context) {
    val navController = rememberNavController()

    // navigation for app
    NavHost(navController = navController, startDestination = "splash_screen") {
        // opens splash screen immediately
        composable(route = Strings.splash_route_name){
            SplashScreen(navController = navController)
        }
        // home screen route
        composable(route = Strings.home_route_name){
            val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
            HomeScreen(navController, fusedLocationClient)
        }
        // nearby bluetooth devices route
        composable(route = Strings.bluetooth_devices_route_name) {
            // bluetooth view model state
            val viewModel = hiltViewModel<BluetoothViewModel>()
            val state by viewModel.state.collectAsState()

            // show error message if error occur
            LaunchedEffect(key1 = state.errorMessage) {
                state.errorMessage?.let{
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }
            }

            // when devices are connect shows this message
            LaunchedEffect(key1 = state.isConnected) {
                if(state.isConnected) {
                    Toast.makeText(context, "You are connected", Toast.LENGTH_LONG).show()

                }
            }

            // state control
            when{
                // if devices are connecting then show LoadingScreen()
                state.isConnecting -> {
                    LoadingScreen()
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
