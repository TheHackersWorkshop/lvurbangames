# ESP32 BLE Beacon Firmware – Las Vegas Urban Games

This firmware turns an ESP32 into a BLE (Bluetooth Low Energy) beacon that broadcasts a unique clue to Android players participating in the **Las Vegas Urban Games**.

## What It Does

- Creates a BLE device with a custom name (e.g. `LVSH_Clue1`)
- Hosts a BLE service and characteristic that holds a **clue text**
- Advertises this service so it can be discovered by the mobile app
- Restarts advertising every 10 seconds to ensure visibility and stability
- Allows Android devices to scan, connect, and read the clue over BLE

This firmware supports the *Scavenger Hunt* game mode by serving clue #1. Other clues can be created by changing the `DEVICE_NAME` and `CLUE_TEXT`.

## Clue Example

This specific build includes the following clue:

> Track down the place with vintage flair and a whisker of charm – where fashion has nine lives!

This would guide players toward a themed destination on the Strip.

## How to Use

### Requirements

- ESP32 microcontroller
- Arduino IDE (or PlatformIO)
- `ESP32 BLE Arduino` library installed:
  - https://github.com/nkolban/ESP32_BLE_Arduino

### Uploading the Firmware

1. Open the firmware `.ino` file (or `.cpp`) in the Arduino IDE.
2. Connect your ESP32 board.
3. Select the correct board and port from **Tools > Board** and **Port**.
4. Click **Upload**.

Once uploaded, the ESP32 will begin advertising its BLE service. Use the Las Vegas Urban Games Android app to scan and read the clue.

## Customization

To create other clue beacons:

- Change `DEVICE_NAME` to match the clue (e.g. `LVSH_Clue2`)
- Change `CLUE_TEXT` to the new riddle or message
- Flash each ESP32 with a different clue and label them accordingly

## Licensing

This firmware is part of the **Las Vegas Urban Games** project.  
It is free to use and modify for **personal and educational** purposes.  
Commercial use is **not allowed** without written permission.

