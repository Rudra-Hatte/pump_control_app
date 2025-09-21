package com.engineeringbay.pumpcontrol

import android.content.Context
import android.telephony.SmsManager
import android.widget.Toast

object SMSManager {

    // Replace with your actual GSM module phone number
    private const val GSM_MODULE_NUMBER = "+1234567890"

    // SMS Commands (4-bit codes as required)
    const val COMMAND_PUMP_ON = "0001"      // Pump ON
    const val COMMAND_PUMP_OFF = "0000"     // Pump OFF
    const val COMMAND_AUTO_ON = "1101"      // Auto Mode ON + Pump ON
    const val COMMAND_AUTO_OFF = "0100"     // Auto Mode + Pump OFF

    fun sendSMS(context: Context, message: String) {
        try {
            // Get device number from SharedPreferences
            val deviceNumber = RegistrationActivity.getDeviceNumber(context)
            
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(deviceNumber, null, message, null, null)

            Toast.makeText(context, "SMS sent: $message to $deviceNumber", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(context, "Failed to send SMS: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    fun getGsmModuleNumber(context: Context): String {
        return RegistrationActivity.getDeviceNumber(context)
    }
}