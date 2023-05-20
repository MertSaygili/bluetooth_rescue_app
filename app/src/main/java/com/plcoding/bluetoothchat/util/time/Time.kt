package com.plcoding.bluetoothchat.util.time

import java.util.*

class Time {

    fun getCurrentTimeForMessage() : String{
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        return "$year:$month:$day:$hour:$minute"
    }
}