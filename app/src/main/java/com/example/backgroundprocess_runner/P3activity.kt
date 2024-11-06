package com.example.backgroundprocess_runner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class P3activity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.p3activity)
        val butt: Button =findViewById(R.id.button7)
        val serviceintent3 = Intent(this, BackgroundService3::class.java)
        if(DataHolder.gg==0)
            butt.setText("Start")
        else
            butt.setText("Stop")
        butt.setOnClickListener{
            if(DataHolder.gg==0)
            {
                startService(serviceintent3)
                butt.setText("Stop")
                DataHolder.gg=1}
            else
            {stopService(serviceintent3)
                butt.setText("Start")
                DataHolder.gg=0
            }

        }
    }
}