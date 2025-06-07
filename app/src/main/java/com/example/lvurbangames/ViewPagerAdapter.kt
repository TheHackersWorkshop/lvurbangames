package com.example.lvurbangames

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        // Return the number of fragments
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        // Return the correct fragment based on position
        return when (position) {
            0 -> HomeFragment()   // Home fragment
            1 -> ScanFragment()   // Scan fragment
            2 -> InfoFragment()   // Info fragment
            else -> HomeFragment()
        }
    }
}
