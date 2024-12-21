package com.ariflutfhansah.masakpedia

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PorterDuff
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
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        // Inisialisasi SharedPreferences terlebih dahulu
        sharedPreferences = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

        applyTheme() // Terapkan tema setelah inisialisasi SharedPreferences
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() // Menyembunyikan app bar
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        // Inisialisasi ProgressBar dan TextView
        progressBar = findViewById(R.id.custom_progress_bar)
        val progressText = findViewById<TextView>(R.id.progress_text)

        // Mengatur padding untuk menghindari konten tertutup oleh sistem bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi elemen UI
        svgImageView = findViewById(R.id.app_logo)
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
        loadData(progressText)
    }

    private fun loadData(progressText: TextView) {
        // Simulasi pemanggilan API atau proses loading data
        val totalDuration = 2000L // Total durasi loading

        // Animator untuk mengubah progress
        val progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100)
        progressAnimator.duration = totalDuration
        progressAnimator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Int
            progressText.text = "$progress%" // Update teks dengan persentase
        }
        progressAnimator.start()

        // Animator untuk mengubah warna
        val colorAnimator = ValueAnimator.ofArgb(Color.RED, Color.GREEN)
        colorAnimator.duration = totalDuration
        colorAnimator.addUpdateListener { animator ->
            val animatedColor = animator.animatedValue as Int
            progressBar.progressDrawable.setColorFilter(animatedColor, PorterDuff.Mode.SRC_IN)
        }
        colorAnimator.start()

        Handler().postDelayed({
            // Data sudah siap, sembunyikan ProgressBar
            progressBar.visibility = View.GONE

            // Cek apakah ini pertama kali pengguna membuka aplikasi
            val isFirstRun = sharedPreferences.getBoolean("is_first_run", true)
            if (isFirstRun) {
                // Jika ini pertama kali, simpan status dan tampilkan tutorial
                sharedPreferences.edit().putBoolean("is_first_run", false).apply()
                showTutorial()
            } else {
                // Jika bukan pertama kali, langsung ke MainActivity
                goToMainActivity()
            }
        }, totalDuration) // Pastikan durasi sama dengan durasi animator
    }

    private fun showTutorial() {
        // Tampilkan tutorial atau informasi singkat di sini
        // Misalnya, Anda bisa memulai Activity baru untuk tutorial
        val intent = Intent(this, TutorialActivity::class.java)
        startActivity(intent)
        finish() // Menutup SplashActivity
    }

    private fun goToMainActivity() {
        // Pindah ke MainActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Menutup SplashActivity
    }

    private fun applyTheme() {
        val isDarkMode = sharedPreferences.getBoolean("is_dark_mode", false)
        if (isDarkMode) {
            setTheme(R.style.Theme_App_Dark) // Ganti dengan tema gelap Anda
        } else {
            setTheme(R.style.Theme_App_Light) // Ganti dengan tema terang Anda
        }
    }
}