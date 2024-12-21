package com.ariflutfhansah.masakpedia

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGImageView
import java.io.InputStream

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        // Mengatur padding untuk menghindari konten tertutup oleh sistem bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Mengatur SVG ke SVGImageView
        val svgImageView: SVGImageView = findViewById(R.id.app_logo)
        val inputStream: InputStream = resources.openRawResource(R.raw.app_icon)
        val svg: SVG = SVG.getFromInputStream(inputStream)
        svgImageView.setSVG(svg)

        // Memuat animasi
        val animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        svgImageView.startAnimation(animation)

        // Menampilkan splash screen selama 3 detik
        Handler().postDelayed({
            // Pindah ke MainActivity setelah splash screen
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Menutup SplashActivity
        }, 3000) // 3000 ms = 3 detik
    }
}