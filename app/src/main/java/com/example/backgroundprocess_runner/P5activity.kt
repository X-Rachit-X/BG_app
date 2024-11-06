package com.example.backgroundprocess_runner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class P5activity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.p5activity)
        val butt: Button =findViewById(R.id.button7)
        val serviceintent5= Intent(this,BackgroundService5::class.java)
        if(DataHolder.g==0)
            butt.setText("Start")
        else
            butt.setText("Stop")
        butt.setOnClickListener{
            if(DataHolder.g==0)
            {
                startService(serviceintent5)
                butt.setText("Stop")
                DataHolder.g=1}
            else
            {stopService(serviceintent5)
                butt.setText("Start")
                DataHolder.g=0}
        }
    }
}