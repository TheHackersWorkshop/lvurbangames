package com.example.lvurbangames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    private lateinit var instructionsTextView: TextView
    private var lastClickedButtonId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize instructionsTextView
        instructionsTextView = rootView.findViewById(R.id.instructionsTextView)

        // Initialize buttons
        val scavengerHuntButton: Button = rootView.findViewById(R.id.buttonScavengerHunt)
        val findWeirdoButton: Button = rootView.findViewById(R.id.buttonFindWeirdo)
        val casinoHistoryButton: Button = rootView.findViewById(R.id.buttonCasinoHistory)

        // Set button listeners to update the TextView with instructions
        scavengerHuntButton.setOnClickListener {
            toggleInstructionsVisibility(R.string.scavenger_hunt_instructions, R.id.buttonScavengerHunt)
        }

        findWeirdoButton.setOnClickListener {
            toggleInstructionsVisibility(R.string.find_the_weirdo_instructions, R.id.buttonFindWeirdo)
        }

        casinoHistoryButton.setOnClickListener {
            toggleInstructionsVisibility(R.string.casino_history_tour_instructions, R.id.buttonCasinoHistory)
        }

        return rootView
    }

    private fun toggleInstructionsVisibility(instructionId: Int, buttonId: Int) {
        if (lastClickedButtonId == buttonId && instructionsTextView.visibility == View.VISIBLE) {
            instructionsTextView.visibility = View.GONE // Hide the instructions
        } else {
            instructionsTextView.text = getString(instructionId) // Update with new instructions
            instructionsTextView.visibility = View.VISIBLE // Show the instructions
        }
        lastClickedButtonId = buttonId // Update the last clicked button id
    }
}
