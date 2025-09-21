package com.engineeringbay.pumpcontrol

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log

class SmsReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "SmsReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent.action) {
            val smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            
            for (smsMessage in smsMessages) {
                val messageBody = smsMessage.messageBody
                val originatingAddress = smsMessage.originatingAddress
                
                Log.d(TAG, "SMS received from: $originatingAddress, Body: $messageBody")
                
                // Check if the SMS is from our GSM module device
                val gsmModuleNumber = RegistrationActivity.getDeviceNumber(context)
                
                if (originatingAddress == gsmModuleNumber || 
                    originatingAddress == gsmModuleNumber.removePrefix("+")) {
                    
                    // Handle confirmation SMS
                    SmsHandler.handleConfirmationSMS(context, messageBody)
                }
            }
        }
    }
}