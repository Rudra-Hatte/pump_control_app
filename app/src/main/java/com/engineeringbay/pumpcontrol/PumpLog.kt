package com.engineeringbay.pumpcontrol

data class PumpLog(
    var id: Int = 0,
    val action: String,
    val status: String,
    val timestamp: String,
    val sensorData: String
) {
    // Secondary constructor without ID (for new entries)
    constructor(action: String, status: String, timestamp: String, sensorData: String)
            : this(0, action, status, timestamp, sensorData)
}