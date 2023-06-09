package com.plcoding.bluetoothchat.util.constants

class Strings {
    companion object{
        // router constants
        const val splash_route_name = "splash_screen"
        const val home_route_name = "home_screen"
        const val bluetooth_devices_route_name = "bluetooth_devices_route_name"
        const val bluetooth_settings_screen = "bluetooth_settings_screen"
        const val bluetooth_chat_screen = "bluetooth_chat_screen"
        const val loading_screen = "loading_screen"

        // db constants
        const val db_name = "message"
        const val messageStringTitle = "Acil Konuşma Paneli"


        // toast messages
        const val notFindSOSDevice = "Yakınlarda yardım cihazı bulunamamıştır, lütfen sonra deneyiniz!!!"
        const val connectionMade = "Bağlantı kuruldu."
        const val locationSend = "Konumunuz yetkililere bildirildi."
        const val hintArduinoHelpText = "Konum göndere basmadan önce cihazınızın mesaj göndermek istediğiniz cihaza bağlı olduğundan emin olunuz."
    }
}

class MACAddresses{
    companion object{
        var macAddressOfArduinos = mutableListOf(
            "00:22:09:01:3D:AF",
            "00:19:10:08:39:47"
        )
    }
}