package com.kroger.classapp


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class splashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        // Delay for 2 seconds and then start the main activity
        val splashScreenDuration = 3000L // 2 seconds
        Thread {
            Thread.sleep(splashScreenDuration)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }.start()
    }
}
