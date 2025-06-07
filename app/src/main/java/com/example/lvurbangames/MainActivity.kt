package com.example.lvurbangames

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import com.example.lvurbangames.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"
    private val bluetoothPermissionRequestCode = 1

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "MainActivity started")

        // Check for Bluetooth and location permissions
        if (!hasBluetoothPermissions()) {
            requestBluetoothPermissions()
        }

        // Initialize ViewPagerAdapter (no fragment list required)
        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter

        // Sync BottomNavigationView with ViewPager2
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> binding.viewPager.currentItem = 0
                R.id.navigation_scan -> binding.viewPager.currentItem = 1
                R.id.navigation_info -> binding.viewPager.currentItem = 2
            }
            true
        }

        // Sync ViewPager2 with BottomNavigationView
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.d(TAG, "Page selected: $position")
                when (position) {
                    0 -> binding.bottomNavigationView.selectedItemId = R.id.navigation_home
                    1 -> binding.bottomNavigationView.selectedItemId = R.id.navigation_scan
                    2 -> binding.bottomNavigationView.selectedItemId = R.id.navigation_info
                }
            }
        })
    }

    // Check for necessary Bluetooth permissions
    @RequiresApi(Build.VERSION_CODES.S)
    private fun hasBluetoothPermissions(): Boolean {
        val bluetoothScanPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.BLUETOOTH_SCAN
        )
        val locationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        return bluetoothScanPermission == PackageManager.PERMISSION_GRANTED &&
                locationPermission == PackageManager.PERMISSION_GRANTED
    }

    // Request Bluetooth and location permissions
    private fun requestBluetoothPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                bluetoothPermissionRequestCode
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                bluetoothPermissionRequestCode
            )
        }
    }

    // Handle permission results
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == bluetoothPermissionRequestCode) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Log.d(TAG, "Permissions granted")
            } else {
                Log.e(TAG, "Bluetooth permissions required for scanning.")
            }
        }
    }
}
