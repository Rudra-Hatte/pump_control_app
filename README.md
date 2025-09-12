# 🚰 Smart Pump Control System

An Android application for remote pump control via SMS integration with GSM module, featuring real-time status monitoring and comprehensive data logging.

## 📋 Overview

This system enables remote pump control through SMS commands sent to a GSM module, with automatic status feedback and database logging of all operations. Perfect for agricultural irrigation, water supply management, or any remote pump control application.

## ✨ Features

### 🎮 Control Interface
- **4 Primary Controls**: Pump On, Pump Off, Data Entry Log, Auto Mode
- **Real-time Status Display**: Current pump status (ON/OFF) with visual indicators
- **User-friendly Interface**: Simple, intuitive button-based design

### 📊 Data Management
- **Database Integration**: SQLite database for local data storage
- **Comprehensive Logging**: All pump operations with timestamps
- **Sensor Data Recording**: Environmental/operational sensor data logging
- **Tabular Data View**: Easy-to-read data entry log display

### 📱 Communication System
- **SMS Integration**: Two-way SMS communication with GSM module
- **Command Transmission**: Send control commands via SMS
- **Status Feedback**: Receive pump status updates via SMS
- **Auto-logging**: Automatic database entry upon SMS feedback

## 🏗️ System Architecture

```
[Android App] ←→ SMS ←→ [GSM Module] ←→ [Pump Controller] ←→ [Water Pump]
      ↓
[Local Database]
```

## 🚀 Usage Guide

### Basic Operation
1. **Pump On**: Tap "Pump On" button → SMS sent → Confirmation received → Status updated
2. **Pump Off**: Tap "Pump Off" button → SMS sent → Confirmation received → Status updated
3. **View Logs**: Tap "Data Entry Log" → View historical data in table format
4. **Auto Mode**: Enable/disable automatic pump control based on sensors





## 📄 License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## 📞 Support

For support and questions:
- **Email**: rudra.hatte@example.com
- **Issues**: [GitHub Issues](https://github.com/Rudra-Hatte/smart-pump-control/issues)
- **Documentation**: [Wiki](https://github.com/Rudra-Hatte/smart-pump-control/wiki)


---

**Built with ❤️ by [Rudra-Hatte](https://github.com/Rudra-Hatte)**
