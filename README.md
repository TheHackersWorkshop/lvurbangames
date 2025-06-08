# LVUrbanGames

Las Vegas Urban Games is a mobile app framework that turns real-world spaces into interactive scavenger hunts. Originally designed for the Las Vegas Strip, the system uses Bluetooth Low Energy (BLE) beacons to deliver clues to players through their smartphones. While the original experience was based in Vegas, the platform is fully adaptable—perfect for customizing to any city, campus, museum, event, or private venue. Whether you're running a community event, creating an educational experience, or just designing something fun, this app helps you bring exploration and discovery to the physical world.

## What the App Does

The app connects to BLE beacons placed at various physical locations. Each beacon holds a clue that guides the player to the next stop in the game. Players can select from multiple game modes, each with its own rules and objectives—but the core gameplay stays the same: scan, receive a clue, solve it, move on.

Because the full source code is included here, developers, students, and creators can modify the app to create their own experiences. You’re welcome to explore, adapt, and build your own local version.

Note: This app is free to use for personal and educational purposes. Commercial use or redistribution of the app, assets, or content is not permitted without written permission.

## Game Modes

### Scavenger Hunt
A race to the finish. Players (solo or in teams) compete to be the first to find and solve all clues. Each beacon reveals the next step. First one to complete the route wins.

### Find the Weirdo
A mystery game. Players are given clues about a strange character known as "The Weirdo." These clues are hidden in BLE beacons across the city. Solve the clues, find the Weirdo, win the prize.

### Casino History Tour
An educational mode. Players visit historic casino sites and scan BLE beacons that reveal stories, historical trivia, and media like photos or videos. Great for tourists or history buffs.

## How It Works
Install the apk
Install the Android app on a BLE-supported smartphone.

Choose a Game Mode
Game-specific instructions are available on the Home screen.

Scan for Beacons
Move through the environment. Beacons appear on the Scan page when in range.

Read and Solve Clues
Clues are displayed on the Info screen and persist locally during your session.

Reset as Needed
When finished or restarting, players can clear their clue history from the Info screen.



## Technical Details

- **Built for Android** using Kotlin.
- **BLE Support** is required for the device.
- **Navigation** is handled through a bottom tab bar: Home, Scan, and Info.
- **Clue Storage** persists locally on the device during gameplay and remains available unless cleared by the user.
- **Bluetooth Permissions** are required for beacon scanning.

## ESP32 Beacons

The beacons used in this game are powered by ESP32 microcontrollers. Each beacon:
Advertises a unique Bluetooth identifier (e.g., LVSH_Clue1)
Stores clue text in a BLE characteristic
Responds to scans from the app with the stored clue
The firmware for these beacons is available in a separate repository so you can create your own, modify the clues, or build new game routes.

## File Structure

This repository contains only the source code for the Android app. All unnecessary files (e.g., cache folders, binaries, and wrappers) have been removed to keep the repo clean and lightweight.

## License and Use

Las Vegas Urban Games is released under a non-commercial license. It is free to use, modify, and study for personal, educational, and community projects.
## Commercial use, resale, or redistribution is not permitted without written permission.
If you're interested in a commercial license, collaboration, or adapting the system for your organization, feel free to contact the maintainer.

## Contact

Project by David Rosales ~ Reimagined Innovations LLC 2024
# Email lvurbangames@gmail.com
