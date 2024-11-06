package com.example.backgroundprocess_runner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


class MainActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       Toast.makeText(this,"BCSE303",Toast.LENGTH_SHORT).show()
        var button: Button =findViewById(R.id.button)
         button.setOnClickListener{
             val intent: Intent=Intent(this,P1activity::class.java)
             startActivity(intent)
         }
        val button2: Button =findViewById(R.id.button2)
        button2.setOnClickListener{
            val intent: Intent=Intent(this,P2activity::class.java)
            startActivity(intent)
        }
        val button3: Button =findViewById(R.id.button3)
        button3.setOnClickListener{
            val intent: Intent=Intent(this,P3activity::class.java)
            startActivity(intent)
        }
        val button4: Button =findViewById(R.id.button4)
        button4.setOnClickListener{
            val intent: Intent=Intent(this,P4activity::class.java)
            startActivity(intent)
        }
        val button5: Button =findViewById(R.id.button6)
        button5.setOnClickListener{
            val intent: Intent=Intent(this,P5activity::class.java)
            startActivity(intent)
        }
        val button6: Button =findViewById(R.id.button5)
        button6.setOnClickListener{
            val intent: Intent=Intent(this,P6activity::class.java)
            startActivity(intent)
        }
        var button0:ImageButton =findViewById(R.id.imageButton)
        button0.setOnClickListener{
            val intent: Intent=Intent(this,Infoactivity::class.java)
            startActivity(intent)
        }

    }

//    override fun onStart() {
//        Toast.makeText(this,"Made by",Toast.LENGTH_SHORT).show()
//        super.onStart()
//    }
//
//    override fun onResume() {
//        Toast.makeText(this,"Rachit Agarwal",Toast.LENGTH_SHORT).show()
//        super.onResume()
//    }

}