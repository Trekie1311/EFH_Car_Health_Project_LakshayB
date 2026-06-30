# EFH_Car_Health_Project_LakshayB
# Car Health Monitoring System

## Project Overview

The Car Health Monitoring System is a Java and Arduino-based application that monitors a vehicle's operating conditions in real time. An Arduino collects sensor readings and sends them to a Java desktop application through serial communication.

The Java application displays the sensor values in a graphical user interface (GUI), changes the system status colour based on the readings, and stores all readings in a CSV log file for future analysis.

---

## Features

- Real-time communication between Arduino and Java
- Live display of:
  -  Temperature
  -  Current
  - System Status
- Colour-coded status indicators
  -  Green – Normal
  -  Yellow – Warning
  -  Red – Critical
- Automatic logging of every sensor reading
- CSV log file containing:
  - Timestamp
  - Temperature
  - Current
  - System Status

---

## Hardware Used

- Arduino Uno
- DHT11 Temperature Sensor
- ACS712 Current Sensor
- Breadboard
- Jumper Wires
- USB Cable

---

## Software Used

- Java
- Arduino IDE
- VS Code
- jSerialComm Library

---


## Program Flow

```
Arduino Sensors
       │
       ▼
Arduino Serial Output
       │
       ▼
SerialReader
       │
       ▼
GUI
       │
       ├────────► Update Labels
       │
       ├────────► Change Status Colour
       │
       └────────► Save Reading
                     │
                     ▼
                 LogWriter
                     │
                     ▼
               logs1.csv
```

---

## Java Classes

### MainApp

- Starts the application
- Displays available COM ports
- Creates the GUI
- Starts the serial communication thread

---

### SerialReader

Responsible for:

- Connecting to the Arduino
- Reading serial data
- Sending each reading to the GUI

Example Arduino message:

```
TEMP:27.8,CURRENT:0.42,STATUS:NORMAL
```

---

### GUI

Responsible for:

- Displaying live sensor values
- Updating labels
- Changing status colours
- Sending readings to the LogWriter

---

### AlertEngine

Compares sensor values against predefined limits.

Current limits:

| Sensor | Warning Limit |
|---------|---------------|
| Temperature | > 35°C |
| Current | > 1.0 A |

Returns:

- NORMAL
- WARNING_TEMP
- WARNING_CURRENT

---

### LogWriter

Stores every reading in

```
logs/logs1.csv
```

Each row contains:

```
Timestamp,Temperature,Current,Status
```

Example:

```
2026-06-30T18:52:17,27.5,0.42,NORMAL
```

---

## Running the Project

1. Connect the Arduino.
2. Upload the Arduino sketch.
3. Check the Arduino COM port.
4. Update

```java
String arduinoPort = "COM3";
```

if necessary.

5. Run

```
MainApp.java
```

---

## Dependencies

This project requires:

```
jSerialComm-2.11.4.jar
```


