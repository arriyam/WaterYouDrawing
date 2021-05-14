package com.arriyam.newsocketiotest2.game

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.arriyam.newsocketiotest2.MainActivity
import com.arriyam.newsocketiotest2.R
import com.arriyam.newsocketiotest2.login.UsernameActivity
import com.arriyam.newsocketiotest2.socket.SocketHandler


class EndActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)

        //      Socket connection for this activity
        val nSocket= SocketHandler.getSocket()

        val textViewTitle=findViewById<TextView>(R.id.textViewTitle)
        val btnReturnMainMenu=findViewById<Button>(R.id.btnReturnMainMenu)
        val textViewScore=findViewById<TextView>(R.id.textViewScore)

        val pointsViewDisplay=intent.getStringExtra("Points_Data")
        val roundTotalWinner=intent.getStringExtra("Total_Winner_Data")


//         Removed because there was a big on the server. Didn't display proper point values.
//        textViewScore.text="Final "+pointsViewDisplay

        when (roundTotalWinner){
            "R"->textViewTitle.text="Team Red wins!"
            "B"->textViewTitle.text="Team Blue wins!"
            else ->textViewTitle.text="It is a Tie"
        }

        btnReturnMainMenu.setOnClickListener{
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}