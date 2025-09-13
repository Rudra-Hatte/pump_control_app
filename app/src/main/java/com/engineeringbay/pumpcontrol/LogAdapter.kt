package com.engineeringbay.pumpcontrol

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LogAdapter(private var logList: List<PumpLog>) : RecyclerView.Adapter<LogAdapter.LogViewHolder>() {

    class LogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAction: TextView = itemView.findViewById(R.id.tvAction)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val tvTimestamp: TextView = itemView.findViewById(R.id.tvTimestamp)
        val tvSensorData: TextView = itemView.findViewById(R.id.tvSensorData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_log, parent, false)
        return LogViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        val log = logList[position]

        holder.tvAction.text = log.action
        holder.tvStatus.text = log.status
        holder.tvTimestamp.text = log.timestamp
        holder.tvSensorData.text = log.sensorData

        // Set status background based on status
        val backgroundResource = when (log.status.uppercase()) {
            "ON" -> R.drawable.status_badge_green
            "OFF" -> R.drawable.status_badge_red
            else -> R.drawable.status_badge_blue
        }
        holder.tvStatus.setBackgroundResource(backgroundResource)
    }

    override fun getItemCount(): Int = logList.size

    fun updateLogs(newLogs: List<PumpLog>) {
        logList = newLogs
        notifyDataSetChanged()
    }
}