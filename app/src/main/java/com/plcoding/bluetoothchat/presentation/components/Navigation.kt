package com.plcoding.bluetoothchat.presentation.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.plcoding.bluetoothchat.constants.Strings
import com.plcoding.bluetoothchat.presentation.components.bluetooth_vm.BluetoothViewModel
import com.plcoding.bluetoothchat.presentation.components.screen.DeviceScreen
import com.plcoding.bluetoothchat.presentation.components.screen.HomeScreen
import com.plcoding.bluetoothchat.presentation.components.screen.SplashScreen

@Composable
fun Navigation(context: Context) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash_screen") {
        composable(route = Strings.splash_route_name){
            SplashScreen(navController = navController)
        }
        composable(route = Strings.home_route_name){
            var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
            HomeScreen(navController, fusedLocationClient)
        }
        composable(route = Strings.main_route_name) {
            val viewModel = hiltViewModel<BluetoothViewModel>()
            val state by viewModel.state.collectAsState()
            DeviceScreen(
                state = state,
                onStartScan = viewModel::startScan,
                onStopScan = viewModel::stopScan
            )
        }
    }
}