package com.ariflutfhansah.masakpedia

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class TutorialActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        // Inisialisasi sharedPreferences
        sharedPreferences = getSharedPreferences("your_preferences_name", MODE_PRIVATE)

        // Inisialisasi tombol
        val continueButton: Button = findViewById(R.id.continue_button)

        // Set listener untuk tombol
        continueButton.setOnClickListener {
            // Simpan status tutorial telah dilihat
            val editor = sharedPreferences.edit()
            editor.putBoolean("isFirstRun", false)
            editor.apply()

            // Pindah ke MainActivity saat tombol ditekan
            goToMainActivity()
        }
    }

    private fun goToMainActivity() {
        // Pindah ke MainActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Menutup TutorialActivity
    }
}