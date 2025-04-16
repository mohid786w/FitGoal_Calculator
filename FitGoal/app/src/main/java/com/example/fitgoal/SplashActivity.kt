package com.example.fitgoal

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fitgoal.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Find views
        val logoImage = findViewById<ImageView>(R.id.logoImageView)
        val appNameText = findViewById<TextView>(R.id.appNameTextView)
        val progressBar = findViewById<ProgressBar>(R.id.loadingProgressBar)

        // Load animations
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val bounce = AnimationUtils.loadAnimation(this, R.anim.bounce)

        // Apply animations
        logoImage.startAnimation(fadeIn)
        appNameText.startAnimation(fadeIn)

        // Start bounce animation after fade in completes
        Handler(Looper.getMainLooper()).postDelayed({
            logoImage.startAnimation(bounce)
        }, 500)

        // Navigate to MainActivity after delay
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2500) // 2.5 seconds delay
    }
}