package com.arriyam.newsocketiotest2.game

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.arriyam.newsocketiotest2.MainActivity
import com.arriyam.newsocketiotest2.R
import com.arriyam.newsocketiotest2.socket.SocketHandler


class EndActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_end_blue)


        val pointsViewDisplay=intent.getStringExtra("Points_Data")
        val roundTotalWinner=intent.getStringExtra("Total_Winner_Data")



        when (roundTotalWinner){
            "R"->{
                setContentView(R.layout.activity_end_red)

            }
            "B"->{
                setContentView(R.layout.activity_end_blue)

            }
            else ->{
                setContentView(R.layout.activity_end)

            }
        }

        val textViewTitle=findViewById<TextView>(R.id.textViewTitle)
        val btnReturnMainMenu=findViewById<Button>(R.id.btnReturnMainMenu)
        val textViewScore=findViewById<TextView>(R.id.textViewScore)


        when (roundTotalWinner){
            "R"->{

                textViewTitle.text="Team Red wins!"
            }
            "B"->{

                textViewTitle.text="Team Blue wins!"
            }
            else ->{

                textViewTitle.text="It is a Tie"
            }
        }

        //         Removed because there was a big on the server. Didn't display proper point values.
        textViewScore.text="Final "+pointsViewDisplay



        //      Socket connection for this activity
        val nSocket= SocketHandler.getSocket()

        btnReturnMainMenu.setOnClickListener{
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}