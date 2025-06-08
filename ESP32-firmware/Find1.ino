#include <BLEDevice.h>
#include <BLEUtils.h>
#include <BLEServer.h>

// Define UUIDs for the service and characteristics
#define SERVICE_UUID           "12345678-1234-1234-1234-123456789abq"
#define CHARACTERISTIC_UUID    "87651321-4321-4321-4321-cba967654333"

// Define the device name and clue text
#define DEVICE_NAME            "LVWT_Clue1"
#define CLUE_TEXT              "The character you're seeking is a man with a unique style. Head south to narrow your search ~ he’s waiting in that direction!"

// Declare the advertising object globally
BLEAdvertising *pAdvertising;
unsigned long previousMillis = 0;  // Stores the last time advertising was restarted
const long interval = 30000;  // Interval to restart advertising (30 seconds)

void setup() {
  // Initialize the BLE device
  BLEDevice::init(DEVICE_NAME);

  // Create a BLE server
  BLEServer *pServer = BLEDevice::createServer();

  // Create a BLE service
  BLEService *pService = pServer->createService(SERVICE_UUID);

  // Create a BLE characteristic
  BLECharacteristic *pCharacteristic = pService->createCharacteristic(
                                         CHARACTERISTIC_UUID,
                                         BLECharacteristic::PROPERTY_READ |
                                         BLECharacteristic::PROPERTY_NOTIFY
                                       );

  // Set the value of the characteristic
  pCharacteristic->setValue(CLUE_TEXT);

  // Start the service
  pService->start();

  // Initialize BLE advertising
  pAdvertising = BLEDevice::getAdvertising();
  pAdvertising->addServiceUUID(SERVICE_UUID);
  pAdvertising->setScanResponse(true);
  pAdvertising->start();

  Serial.begin(115200);
  Serial.println("Beacon with clue started...");
}

void loop() {
  unsigned long currentMillis = millis();

  // Restart advertising every 30 seconds (or the interval you've set)
  if (currentMillis - previousMillis >= interval) {
    previousMillis = currentMillis;  // Save the last time advertising was restarted

    // Stop and restart advertising
    pAdvertising->stop();
    Serial.println("Restarting BLE advertising...");
    pAdvertising->start();
  }

  // Optional: Small delay to reduce CPU load
  delay(1000);
}
