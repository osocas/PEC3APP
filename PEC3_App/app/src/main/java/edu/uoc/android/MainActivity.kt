package edu.uoc.android

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        rellay_maps.setOnClickListener(){
            val intent = Intent(this, Activity_Mapa::class.java)
            startActivity(intent)
        }

        rellay_museums.setOnClickListener(){
            val intent = Intent(this, Activity_Museums::class.java)
            startActivity(intent)
        }

        rellay_quizzes.setOnClickListener(){
            val intent = Intent(this, Activity_Quizzes::class.java)
            startActivity(intent)
        }

    }

}
