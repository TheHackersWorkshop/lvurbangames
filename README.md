# LVUrbanGames

Las Vegas Urban Games is a mobile app that turns the Las Vegas Strip into an interactive scavenger hunt. It’s a real-world location-based game that uses Bluetooth Low Energy (BLE) beacons to deliver clues to players through their smartphones. Whether you're a tourist looking for something fun to do or a local looking to explore the Strip in a new way, this app is designed to get you moving, thinking, and discovering the city around you.

## What the App Does

The app connects to BLE beacons placed at different locations along the Las Vegas Strip. Each beacon holds a clue that leads the player to the next stop in the game. Players can choose from a few different game modes, each with its own rules and objective, but the basic mechanic remains the same: scan, receive a clue, solve it, move to the next location.

This app is free to use for personal, non-commercial purposes. You’re welcome to download it, play with friends, or explore the source code for educational use. However, commercial use or redistribution of this app, its assets, or its content is not allowed without permission.

## Game Modes

### Scavenger Hunt
This is a race to the finish. Players (either solo or in teams) compete to be the first to find and solve all the clues. Each beacon reveals the next step in the journey, and the first to complete the route wins.

### Find the Weirdo
Players are given clues about a mysterious character called "The Weirdo." These clues are hidden in BLE beacons throughout the Strip. Follow the clues, find the Weirdo, and win.

### Casino History Tour
This mode is less of a game and more of an educational walking tour. Players scan beacons placed near famous casinos to learn about their history, previous owners, and sometimes watch videos or view photos from the past.

## How It Works

1. **Download the App**  
   Install the Las Vegas Urban Games Android app.

2. **Choose a Game Mode**  
   Each game has its own instructions accessible from the Home screen.

3. **Scan for Beacons**  
   Walk around the city. When you're near a BLE beacon, the app will show it on the Scan screen. Select it to retrieve the clue.

4. **Solve the Clue**  
   Clues appear in the Info screen. Follow the instructions or solve the puzzle to find the next beacon.

5. **Continue or Reset**  
   The Info screen saves all your clues. You can clear it at any time to start over.

## Technical Details

- **Built for Android** using Kotlin.
- **BLE Support** is required for the device.
- **Navigation** is handled through a bottom tab bar: Home, Scan, and Info.
- **Clue Storage** persists locally on the device during gameplay and remains available unless cleared by the user.
- **Bluetooth Permissions** are required for beacon scanning.

## ESP32 Beacons

The beacons used in the game are powered by ESP32 microcontrollers. Each one is programmed to broadcast a unique identifier and respond with a clue stored in its Bluetooth characteristics. These clues are retrieved directly by the app when a user connects.

The firmware for these beacons is planned to be released in a separate repository. It can be used to build your own custom beacons if you're interested in hosting your own version of the game or learning how it works behind the scenes.

## File Structure

This repository includes only the necessary source code for the Android app. Temporary files, caches, and large binaries have been filtered out to keep the repository lightweight and clean.

## License and Use

Las Vegas Urban Games is free to use, modify, and study for personal and educational purposes. Commercial use is not permitted without written permission. If you're interested in licensing or collaborating on a commercial version of the game, please reach out through the GitHub contact options.

## Contact

Project by David Rosales  
Repository: https://github.com/TheHackersWorkshop/LVUrbanGames

---

This project was  apart of a my other company and I am offering it under Creative Commons Attribution-NonCommercial 4.0 International (CC BY-NC 4.0) license.

