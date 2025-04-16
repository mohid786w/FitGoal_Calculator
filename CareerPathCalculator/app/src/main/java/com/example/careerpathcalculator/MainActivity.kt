package com.example.careerpathcalculator

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import android.graphics.drawable.AnimationDrawable
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var interestsInput: TextInputEditText
    private lateinit var subjectInput: TextInputEditText
    private lateinit var hobbyInput: TextInputEditText
    private lateinit var calculateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        interestsInput = findViewById(R.id.interests_input)
        subjectInput = findViewById(R.id.subject_input)
        hobbyInput = findViewById(R.id.hobby_input)
        calculateButton = findViewById(R.id.calculate_button)

        // Add animation to the calculate button
        val buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.pulse_animation)
        calculateButton.startAnimation(buttonAnimation)

        // Set click listener for calculate button
        calculateButton.setOnClickListener {
            if (validateInputs()) {
                val interests = interestsInput.text.toString().trim()
                val subject = subjectInput.text.toString().trim()
                val hobby = hobbyInput.text.toString().trim()

                // Get career suggestion based on inputs
                val careerSuggestion = CareerSuggestionEngine.getSuggestion(interests, subject, hobby)

                // Show result in popup
                showCareerResultPopup(careerSuggestion)
            }
        }
    }

    private fun validateInputs(): Boolean {
        if (interestsInput.text.isNullOrBlank() ||
            subjectInput.text.isNullOrBlank() ||
            hobbyInput.text.isNullOrBlank()) {

            Toast.makeText(this, getString(R.string.error_empty_fields), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun showCareerResultPopup(careerSuggestion: CareerSuggestion) {
        val dialogView = layoutInflater.inflate(R.layout.career_result_dialog, null)

        // Initialize dialog elements
        val careerIcon: ImageView = dialogView.findViewById(R.id.career_icon)
        val careerTitle: TextView = dialogView.findViewById(R.id.career_title)
        val careerDescription: TextView = dialogView.findViewById(R.id.career_description)
        val careerQuote: TextView = dialogView.findViewById(R.id.career_quote)
        val closeButton: Button = dialogView.findViewById(R.id.close_button)

        // Set content
        careerIcon.setImageResource(careerSuggestion.iconResourceId)
        careerTitle.text = careerSuggestion.title
        careerDescription.text = careerSuggestion.description
        careerQuote.text = careerSuggestion.quote

        // Start icon animation
        careerIcon.setBackgroundResource(getAnimationForCareer(careerSuggestion.category))
        val iconAnimation = careerIcon.background as AnimationDrawable
        iconAnimation.start()

        // Create and show dialog
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        // Dialog animation
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation

        // Close button listener
        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun getAnimationForCareer(category: String): Int {
        return when (category) {
            "TECH" -> R.drawable.tech_animation
            "SCIENCE" -> R.drawable.science_animation
            "ARTS" -> R.drawable.arts_animation
            "BUSINESS" -> R.drawable.business_animation
            else -> R.drawable.default_animation
        }
    }
}