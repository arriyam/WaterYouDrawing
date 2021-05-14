package com.arriyam.newsocketiotest2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.Toast
import com.arriyam.newsocketiotest2.login.LoginActivity
import com.arriyam.newsocketiotest2.login.UsernameActivity
import com.arriyam.newsocketiotest2.sketch.CanvasSize
import com.arriyam.newsocketiotest2.socket.SocketHandler

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        CanvasSize.setCanvasHeight(height.toFloat())
        CanvasSize.setCanvasWidth(width.toFloat())


//        Toast.makeText(this,"8", Toast.LENGTH_SHORT).show()

//      btnMenu and btnSearch were created to interact with the buttons on the MainActivity
        val btnSketch=findViewById<Button>(R.id.btnSketch)
        val btnRules=findViewById<Button>(R.id.btnRules)



        btnSketch.setOnClickListener{
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }


        btnRules.setOnClickListener{

            val intent= Intent(this, RulesActivity::class.java)
            startActivity(intent)

        }
    }

}