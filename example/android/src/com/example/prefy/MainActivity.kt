package com.example.prefy

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onClick(v: View) = startActivity(
        Intent(
            this, when (v) {
                button1 -> Example1Activity::class.java
                else -> Example2Activity::class.java
            }
        )
    )
}