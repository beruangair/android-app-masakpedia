package com.ariflutfhansah.masakpedia

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGImageView
import java.io.InputStream

class SplashActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var svgImageView: SVGImageView
    private lateinit var versionText: TextView

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

        // Inisialisasi elemen UI
        svgImageView = findViewById(R.id.app_logo)
        progressBar = findViewById(R.id.progress_bar)
        versionText = findViewById(R.id.version_text)

        // Mengatur SVG ke SVGImageView
        val inputStream: InputStream = resources.openRawResource(R.raw.app_icon)
        val svg: SVG = SVG.getFromInputStream(inputStream)
        svgImageView.setSVG(svg)

        // Menampilkan versi aplikasi
        val versionName = packageManager.getPackageInfo(packageName, 0).versionName
        versionText.text = "Version $versionName"

        // Memuat animasi
        val animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        svgImageView.startAnimation(animation)

        // Menampilkan ProgressBar
        progressBar.visibility = View.VISIBLE // Tampilkan ProgressBar

        // Memuat data
        loadData()
    }

    private fun loadData() {
        // Simulasi pemanggilan API atau proses loading data
        Handler().postDelayed({
            // Data sudah siap, sembunyikan ProgressBar
            progressBar.visibility = View.GONE

            // Pindah ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Menutup SplashActivity
        }, 2000) // Misalnya, 2000 ms = 2 detik
    }
}