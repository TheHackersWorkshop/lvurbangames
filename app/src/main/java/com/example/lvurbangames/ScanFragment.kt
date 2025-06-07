package com.example.lvurbangames

import SharedViewModel
import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.*
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import java.nio.charset.Charset
import java.util.*

class ScanFragment : Fragment() {

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
    }

    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var bluetoothLeScanner: BluetoothLeScanner
    private lateinit var deviceList: ArrayList<BluetoothDevice>
    private lateinit var deviceListAdapter: ArrayAdapter<String>
    private lateinit var listView: ListView
    private lateinit var scanButton: Button
    private var isScanning = false
    private val scanDuration: Long = 10000 // Stop scanning after 10 seconds
    private val handler = Handler(Looper.getMainLooper())
    private var bluetoothGatt: BluetoothGatt? = null

    private val characteristicsToRead = mutableListOf<BluetoothGattCharacteristic>()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scan, container, false)
        initializeUI(view)
        return view
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun initializeUI(view: View) {
        listView = view.findViewById(R.id.device_list)
        scanButton = view.findViewById(R.id.scan_button)
        deviceList = ArrayList()
        deviceListAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1)
        listView.adapter = deviceListAdapter

        val bluetoothManager = requireContext().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter

        scanButton.setOnClickListener {
            if (isScanning) {
                stopScanning()
            } else {
                clearDeviceList()
                checkPermissionsAndStartScanning()
            }
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val device = deviceList[position]
            stopScanning()
            connectToDevice(device)
        }
    }

    private fun clearDeviceList() {
        deviceListAdapter.clear()
        deviceList.clear()
        deviceListAdapter.notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun checkPermissionsAndStartScanning() {
        if (hasRequiredPermissions()) {
            startScanning()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.BLUETOOTH_CONNECT
                ), PERMISSION_REQUEST_CODE
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun startScanning() {
        if (!bluetoothAdapter.isEnabled) {
            Toast.makeText(requireContext(), "Please enable Bluetooth", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isScanning) {
            deviceList.clear()
            deviceListAdapter.clear()
            deviceListAdapter.notifyDataSetChanged()

            bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
            handler.postDelayed({ stopScanning() }, scanDuration)
            isScanning = true
            scanButton.text = "Stop Scan"
            bluetoothLeScanner.startScan(scanCallback)
            Toast.makeText(requireContext(), "Scanning for devices...", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingPermission")
    private fun stopScanning() {
        if (isScanning) {
            bluetoothLeScanner.stopScan(scanCallback)
            isScanning = false
            scanButton.text = "Start Scan"
            Toast.makeText(requireContext(), "Scan complete.", Toast.LENGTH_SHORT).show()
        }
    }

    private val scanCallback = object : ScanCallback() {
        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val device: BluetoothDevice = result.device
            val deviceName: String = device.name ?: "Not the Clue"
            if (!deviceList.contains(device)) {
                deviceList.add(device)
                deviceListAdapter.add(deviceName)
                deviceListAdapter.notifyDataSetChanged()
            }
        }

        override fun onScanFailed(errorCode: Int) {
            Toast.makeText(requireContext(), "Scan failed with error: $errorCode", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    private fun connectToDevice(device: BluetoothDevice) {
        bluetoothGatt = device.connectGatt(requireContext(), false, gattCallback)
    }

    private val gattCallback = object : BluetoothGattCallback() {
        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                gatt.discoverServices()
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                gatt.close()
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Device disconnected", Toast.LENGTH_SHORT).show()
                }
            }
        }

        @SuppressLint("MissingPermission")
        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                // Clear previous characteristics
                characteristicsToRead.clear()
                // Collect readable characteristics
                gatt.services.forEach { service ->
                    service.characteristics.forEach { characteristic ->
                        if (characteristic.properties and BluetoothGattCharacteristic.PROPERTY_READ != 0) {
                            characteristicsToRead.add(characteristic)
                        }
                    }
                }
                if (characteristicsToRead.isNotEmpty()) {
                    readNextCharacteristic(gatt)
                } else {
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "No readable characteristics found", Toast.LENGTH_SHORT).show()
                    }
                    gatt.disconnect()
                }
            } else {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Service discovery failed", Toast.LENGTH_SHORT).show()
                }
                gatt.disconnect()
            }
        }

        @Deprecated("Deprecated in Java")
        @SuppressLint("MissingPermission")
        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                val value = characteristic.value

                // Decode using the correct character set
                val clueText = try {
                    // Try decoding as UTF-8
                    String(value, Charsets.UTF_8)
                } catch (e: Exception) {
                    // If decoding fails, log the error and use a placeholder
                    Log.e("ScanFragment", "Error decoding characteristic value", e)
                    "Unknown Clue"
                }

                // Log the characteristic UUID and decoded value for debugging
                Log.d("ScanFragment", "Characteristic Read: UUID=${characteristic.uuid}, ClueText=$clueText")

                requireActivity().runOnUiThread {
                    viewModel.addClue(clueText)
                    Toast.makeText(requireContext(), "Clue added: $clueText", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.e("ScanFragment", "Characteristic read failed for UUID=${characteristic.uuid}, status=$status")
            }
            // Read the next characteristic
            readNextCharacteristic(gatt)
        }
    }

    @SuppressLint("MissingPermission")
    private fun readNextCharacteristic(gatt: BluetoothGatt) {
        if (characteristicsToRead.isNotEmpty()) {
            val characteristic = characteristicsToRead.removeAt(0)
            gatt.readCharacteristic(characteristic)
        } else {
            requireActivity().runOnUiThread {
                Toast.makeText(requireContext(), "All characteristics read", Toast.LENGTH_SHORT).show()
            }
            gatt.disconnect()
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun hasRequiredPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.BLUETOOTH_SCAN
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.BLUETOOTH_CONNECT
                ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    override fun onDestroy() {
        super.onDestroy()
        bluetoothGatt?.close()
    }
}
