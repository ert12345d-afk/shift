package com.bra.autosamurai

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val intent = Intent(this, AutoClickService::class.java)
        intent.putExtra("keyword", "458")
        intent.putExtra("time", "13:05")
        intent.putExtra("speed", 500L)
        startService(intent)
        
        finish()
    }
}
