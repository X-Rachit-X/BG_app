    package com.example.backgroundprocess_runner

    import android.content.Intent
    import android.os.Bundle
    import android.widget.Button
    import androidx.appcompat.app.AppCompatActivity

    class P1activity:AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.p1activity)
            val butt:Button=findViewById(R.id.button7)
            val serviceintent1:Intent=Intent(this,BackgroundService1::class.java)
            if(DataHolder.ggg==0)
                butt.setText("Start")
            else
                butt.setText("Stop")
            butt.setOnClickListener{
                if(DataHolder.ggg==0)
                {
                startService(serviceintent1)
                butt.setText("Stop")
                DataHolder.ggg=1}
                else
                {stopService(serviceintent1)
                    DataHolder.ggg=0
                    butt.setText("Start")
                }

            }
        }
    }