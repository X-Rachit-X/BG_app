package com.example.backgroundprocess_runner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class P2activity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.p2activity)
        val butt: Button =findViewById(R.id.button7)
        val serviceintent2 = Intent(this, NotificationService2::class.java)
        if(DataHolder.gggg==0)
            butt.setText("Start")
        else
            butt.setText("Stop")
        butt.setOnClickListener{
            if(DataHolder.gggg==0)
            {
                startService(serviceintent2)
                butt.setText("Stop")
                DataHolder.gggg=1}
            else
            {stopService(serviceintent2)
                DataHolder.gggg=0
                butt.setText("Start")
            }
        }
    }
}