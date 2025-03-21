package com.interswitchug.sparkutils

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mcdenny.sparkutils.SparkUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        content()
    }

    private fun content() {
        val date = "2024-09-08T14:54:00.85ZZ"
        val view = findViewById<TextView>(R.id.mtView)
//        Log.d("MainActivity", "Formatted:::: " + SparkUtils.formatDateTime(date))
//        view.text = SparkUtils.formatDateTime(date, "hh:mm ss")
//        val m = SparkUtils.capitalizeWords("interswitch")
//        Log.d("DENIS", "Capitalize: $m")
    }
}