package com.engineeringbay.pumpcontrol

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.*

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "PumpControl.db"
        private const val DATABASE_VERSION = 1

        // Table and column names
        private const val TABLE_PUMP_LOGS = "pump_logs"
        private const val COLUMN_ID = "id"
        private const val COLUMN_ACTION = "action"
        private const val COLUMN_STATUS = "status"
        private const val COLUMN_TIMESTAMP = "timestamp"
        private const val COLUMN_SENSOR_DATA = "sensor_data"

        fun getCurrentTimestamp(): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            return dateFormat.format(Date())
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_PUMP_LOGS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_ACTION TEXT NOT NULL,
                $COLUMN_STATUS TEXT NOT NULL,
                $COLUMN_TIMESTAMP TEXT NOT NULL,
                $COLUMN_SENSOR_DATA TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PUMP_LOGS")
        onCreate(db)
    }

    // Insert a new log entry
    fun insertLog(log: PumpLog): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ACTION, log.action)
            put(COLUMN_STATUS, log.status)
            put(COLUMN_TIMESTAMP, log.timestamp)
            put(COLUMN_SENSOR_DATA, log.sensorData)
        }

        val id = db.insert(TABLE_PUMP_LOGS, null, values)
        db.close()
        return id
    }

    // Get all log entries
    fun getAllLogs(): List<PumpLog> {
        val logList = mutableListOf<PumpLog>()
        val selectQuery = "SELECT * FROM $TABLE_PUMP_LOGS ORDER BY $COLUMN_ID DESC"

        val db = readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val log = PumpLog(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        action = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ACTION)),
                        status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS)),
                        timestamp = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP)),
                        sensorData = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SENSOR_DATA))
                    )
                    logList.add(log)
                } while (cursor.moveToNext())
            }
        }

        db.close()
        return logList
    }

    // Get log count
    fun getLogCount(): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_PUMP_LOGS", null)
        val count = cursor.count
        cursor.close()
        db.close()
        return count
    }

    // Delete all logs
    fun deleteAllLogs() {
        val db = writableDatabase
        db.delete(TABLE_PUMP_LOGS, null, null)
        db.close()
    }
}