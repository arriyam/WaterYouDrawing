package com.arriyam.newsocketiotest2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.arriyam.newsocketiotest2.login.LoginActivity
import com.arriyam.newsocketiotest2.sketch.CanvasSize

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

        val btnWebsite=findViewById<Button>(R.id.btnWebsite)



        btnSketch.setOnClickListener{
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }


        btnRules.setOnClickListener{

            val intent= Intent(this, RulesActivity::class.java)
            startActivity(intent)

        }

        btnWebsite.setOnClickListener{
            val uri: Uri = Uri.parse("https://www.worldvision.ca/") // missing 'http://' will cause crashed

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

}