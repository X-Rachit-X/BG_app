package com.example.backgroundprocess_runner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class P6activity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.p6activity)
         val butt: Button =findViewById(R.id.button7)
        butt.setOnClickListener{
           butt.setText("Made By Rachit_Agarwal " + "Samarth_Tripathi"+"Sohail_Ansari")

        }
    }
}