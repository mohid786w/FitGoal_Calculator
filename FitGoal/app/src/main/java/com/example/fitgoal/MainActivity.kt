package com.example.fitgoal

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fitgoal.R
import com.google.android.material.textfield.TextInputLayout
import kotlin.math.absoluteValue

class MainActivity : AppCompatActivity() {

    private lateinit var currentWeightInput: EditText
    private lateinit var targetWeightInput: EditText
    private lateinit var calculateButton: Button
    private lateinit var genderRadioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        currentWeightInput = findViewById(R.id.currentWeightInput)
        targetWeightInput = findViewById(R.id.targetWeightInput)
        calculateButton = findViewById(R.id.calculateButton)
        genderRadioGroup = findViewById(R.id.genderRadioGroup)

        // Set up click listener
        calculateButton.setOnClickListener {
            if (validateInputs()) {
                calculatePlan()
            }
        }
    }

    private fun validateInputs(): Boolean {
        val currentWeightStr = currentWeightInput.text.toString()
        val targetWeightStr = targetWeightInput.text.toString()

        if (currentWeightStr.isEmpty()) {
            currentWeightInput.error = "Please enter your current weight"
            return false
        }

        if (targetWeightStr.isEmpty()) {
            targetWeightInput.error = "Please enter your target weight"
            return false
        }

        try {
            val currentWeight = currentWeightStr.toFloat()
            val targetWeight = targetWeightStr.toFloat()

            if (currentWeight <= 0 || currentWeight > 500) {
                currentWeightInput.error = "Please enter a valid weight"
                return false
            }

            if (targetWeight <= 0 || targetWeight > 500) {
                targetWeightInput.error = "Please enter a valid weight"
                return false
            }
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun calculatePlan() {
        val currentWeight = currentWeightInput.text.toString().toFloat()
        val targetWeight = targetWeightInput.text.toString().toFloat()
        val isMale = genderRadioGroup.checkedRadioButtonId == R.id.maleRadioButton

        // Calculate weight difference
        val weightDifference = targetWeight - currentWeight
        val isWeightLoss = weightDifference < 0

        // Show result dialog
        ResultDialogFragment.newInstance(
            currentWeight,
            targetWeight,
            weightDifference.absoluteValue,
            isWeightLoss,
            isMale
        ).show(supportFragmentManager, "result_dialog")
    }
}