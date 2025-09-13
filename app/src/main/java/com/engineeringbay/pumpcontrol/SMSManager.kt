package com.engineeringbay.pumpcontrol

import android.content.Context
import android.telephony.SmsManager
import android.widget.Toast

object SMSManager {

    // Replace with your actual GSM module phone number
    private const val GSM_MODULE_NUMBER = "+1234567890"

    // SMS Commands
    const val COMMAND_PUMP_ON = "PUMP_ON"
    const val COMMAND_PUMP_OFF = "PUMP_OFF"
    const val COMMAND_AUTO_ON = "AUTO_ON"
    const val COMMAND_AUTO_OFF = "AUTO_OFF"

    fun sendSMS(context: Context, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(GSM_MODULE_NUMBER, null, message, null, null)

            Toast.makeText(context, "SMS sent: $message", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(context, "Failed to send SMS: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    fun getGsmModuleNumber(): String = GSM_MODULE_NUMBER
}