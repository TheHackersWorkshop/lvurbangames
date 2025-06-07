package com.example.lvurbangames

import SharedViewModel
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class InfoFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var clueTextView: TextView
    private lateinit var clearButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the TextView and Button
        clueTextView = view.findViewById(R.id.clueTextView)
        clueTextView.movementMethod = LinkMovementMethod.getInstance()

        clearButton = view.findViewById(R.id.clearButton)

        // Set up the click listener for the clearButton
        clearButton.setOnClickListener {
            viewModel.clearClues()
        }

        // Observe the clues LiveData from the ViewModel
        viewModel.clues.observe(viewLifecycleOwner) { clues ->
            updateClueDisplay(clues)
        }
    }

    private fun updateClueDisplay(clues: List<String>) {
        clueTextView.text = clues.joinToString("\n\n")
        Log.d("InfoFragment", "Clues updated: ${clues.joinToString(", ")}")
    }
}
