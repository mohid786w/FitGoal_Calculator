package com.example.fitgoal

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.fitgoal.R
import kotlin.math.absoluteValue

class ResultDialogFragment : DialogFragment() {

    private var currentWeight: Float = 0f
    private var targetWeight: Float = 0f
    private var weightDifference: Float = 0f
    private var isWeightLoss: Boolean = false
    private var isMale: Boolean = true

    companion object {
        private const val ARG_CURRENT_WEIGHT = "current_weight"
        private const val ARG_TARGET_WEIGHT = "target_weight"
        private const val ARG_WEIGHT_DIFFERENCE = "weight_difference"
        private const val ARG_IS_WEIGHT_LOSS = "is_weight_loss"
        private const val ARG_IS_MALE = "is_male"

        fun newInstance(
            currentWeight: Float,
            targetWeight: Float,
            weightDifference: Float,
            isWeightLoss: Boolean,
            isMale: Boolean
        ): ResultDialogFragment {
            val fragment = ResultDialogFragment()
            val args = Bundle()
            args.putFloat(ARG_CURRENT_WEIGHT, currentWeight)
            args.putFloat(ARG_TARGET_WEIGHT, targetWeight)
            args.putFloat(ARG_WEIGHT_DIFFERENCE, weightDifference)
            args.putBoolean(ARG_IS_WEIGHT_LOSS, isWeightLoss)
            args.putBoolean(ARG_IS_MALE, isMale)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentWeight = it.getFloat(ARG_CURRENT_WEIGHT)
            targetWeight = it.getFloat(ARG_TARGET_WEIGHT)
            weightDifference = it.getFloat(ARG_WEIGHT_DIFFERENCE)
            isWeightLoss = it.getBoolean(ARG_IS_WEIGHT_LOSS)
            isMale = it.getBoolean(ARG_IS_MALE)
        }

        // Set dialog style
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Make dialog background transparent
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Find views
        val titleTextView = view.findViewById<TextView>(R.id.dialogTitleTextView)
        val exerciseCardView = view.findViewById<CardView>(R.id.exerciseCardView)
        val exerciseTextView = view.findViewById<TextView>(R.id.exerciseTextView)
        val foodCardView = view.findViewById<CardView>(R.id.foodCardView)
        val foodTextView = view.findViewById<TextView>(R.id.foodTextView)
        val tipCardView = view.findViewById<CardView>(R.id.tipCardView)
        val tipTextView = view.findViewById<TextView>(R.id.tipTextView)
        val closeButton = view.findViewById<Button>(R.id.closeButton)
        val recalculateButton = view.findViewById<Button>(R.id.recalculateButton)

        // Apply animations
        val fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        val slideIn = AnimationUtils.loadAnimation(context, R.anim.slide_in)

        exerciseCardView.startAnimation(slideIn)
        foodCardView.startAnimation(slideIn)
        tipCardView.startAnimation(slideIn)

        // Set color scheme based on goal
        val themeColor = if (isWeightLoss) {
            ContextCompat.getColor(requireContext(), R.color.lose_weight)
        } else {
            ContextCompat.getColor(requireContext(), R.color.gain_weight)
        }

        titleTextView.setTextColor(themeColor)

        // Set content
        val goalType = if (isWeightLoss) "Weight Loss" else "Weight Gain"
        titleTextView.text = "Your $goalType Plan (${weightDifference.absoluteValue} kg)"

        // Set exercise recommendations
        exerciseTextView.text = generateExerciseRecommendations()

        // Set food recommendations
        foodTextView.text = generateFoodRecommendations()

        // Set tips
        tipTextView.text = generateTips()

        // Set button listeners
        closeButton.setOnClickListener {
            dismiss()
        }

        recalculateButton.setOnClickListener {
            dismiss()
        }
    }

    private fun generateExerciseRecommendations(): String {
        return if (isWeightLoss) {
            if (weightDifference < 5) {
                "• 30 mins cardio (walking, jogging) 3x/week\n" +
                        "• 15 mins HIIT workout 2x/week\n" +
                        "• Light strength training with high reps"
            } else if (weightDifference < 10) {
                "• 45 mins cardio 4x/week\n" +
                        "• 20 mins HIIT workout 3x/week\n" +
                        "• Moderate strength training\n" +
                        "• Consider swimming or cycling for joint-friendly cardio"
            } else {
                "• 60 mins cardio 5x/week\n" +
                        "• 30 mins HIIT workout 3x/week\n" +
                        "• Full-body strength training\n" +
                        "• Consider working with a personal trainer"
            }
        } else {
            if (weightDifference < 5) {
                "• Strength training 3x/week\n" +
                        "• Focus on compound movements\n" +
                        "• Moderate cardio 2x/week for heart health"
            } else if (weightDifference < 10) {
                "• Heavy strength training 4x/week\n" +
                        "• Focus on progressive overload\n" +
                        "• Split training (different muscle groups)\n" +
                        "• Light cardio for recovery"
            } else {
                "• Heavy strength training 5x/week\n" +
                        "• Focus on hypertrophy training\n" +
                        "• Split training with rest days\n" +
                        "• Consider working with a strength coach"
            }
        }
    }

    private fun generateFoodRecommendations(): String {
        return if (isWeightLoss) {
            if (isMale) {
                "• Calorie deficit of 300-500 kcal/day\n" +
                        "• Increase protein intake (lean meats, fish)\n" +
                        "• Replace refined carbs with vegetables\n" +
                        "• Track food with an app like MyFitnessPal"
            } else {
                "• Calorie deficit of 250-400 kcal/day\n" +
                        "• Increase protein intake (lean meats, fish)\n" +
                        "• Focus on fiber-rich foods\n" +
                        "• Stay hydrated with water or herbal tea"
            }
        } else {
            if (isMale) {
                "• Calorie surplus of 300-500 kcal/day\n" +
                        "• High protein intake (1.6-2g per kg)\n" +
                        "• Healthy carbs for energy (oats, rice, potatoes)\n" +
                        "• Healthy fats from nuts, avocados, olive oil"
            } else {
                "• Calorie surplus of 250-400 kcal/day\n" +
                        "• Moderate protein intake (1.4-1.8g per kg)\n" +
                        "• Complex carbs for sustained energy\n" +
                        "• Add nutrient-dense smoothies between meals"
            }
        }
    }

    private fun generateTips(): String {
        val generalTips = "• Stay hydrated! Aim for 2-3 liters daily\n" +
                "• Get 7-8 hours of quality sleep\n" +
                "• Take progress photos weekly\n" +
                "• Be patient - sustainable change takes time"

        val specificTip = if (isWeightLoss) {
            "• Consider intermittent fasting (16:8 method)"
        } else {
            "• Eat frequent, smaller meals throughout the day"
        }

        return "$generalTips\n$specificTip"
    }
}