package com.chenjim.jitpack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chenjim.jplib.JpL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        JpL().doSth()
    }
}