package com.arriyam.newsocketiotest2.game


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.arriyam.newsocketiotest2.R
import com.arriyam.newsocketiotest2.socket.SocketHandler


class AnswerActivity : AppCompatActivity() {

    lateinit var answerServer:String
    lateinit var winner:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //      What team is pulled from the username activity which got it from the server.
        val answerServer3 = intent.getStringExtra("Answer_Data")
        val team = intent.getStringExtra("Team_Data")
        val pointsViewDisplay=intent.getStringExtra("Points_Data")
        val roundWinner=intent.getStringExtra("Winner_Data")

//        Toast.makeText(this,answerServer.toString(),Toast.LENGTH_SHORT).show()

        when (answerServer3) {
            "river" -> setContentView(R.layout.answer_river)
            "well" -> setContentView(R.layout.answer_well)
            "rain" -> setContentView(R.layout.answer_rain)
            "plant" -> setContentView(R.layout.answer_plant)
            "underground" -> setContentView(R.layout.answer_underground)
//            "avoid" ->setContentView(R.layout.answer_)
//            "melt" ->setContentView(R.layout.answer_)
//            "desert"->setContentView(R.layout.answer_)
            else -> setContentView(R.layout.activity_rules)
        }
//
//
        //      Socket connection for this activity
        val nSocket= SocketHandler.getSocket();
//

        val textViewRedBanner = findViewById<TextView>(R.id.textViewRedBanner)
        val textViewBlueBanner = findViewById<TextView>(R.id.textViewBlueBanner)
        val textViewPoints=findViewById<TextView>(R.id.textViewPoints)
        val textViewWinner = findViewById<TextView>(R.id.textViewWinner)
        val textViewCountDown = findViewById<TextView>(R.id.textViewCountDown)

        textViewWinner.text=roundWinner

//      Red or blue banner is displayed according to what team is viewing it
        if (team == "R") {
            textViewBlueBanner.visibility = View.GONE;
        } else {
            textViewRedBanner.visibility = View.GONE;
        }

        nSocket.on("winner") { args ->
            if (args[0] != null) {
                val data = args[0] as String
                runOnUiThread {
                    winner=data
                }
            }
        }

        nSocket.on("answerWord") { args ->
            if (args[0] != null) {
                val answer = args[0] as String
                runOnUiThread {
                    answerServer=answer
                }
            }
        }

        textViewPoints.text=pointsViewDisplay



//     Next round starter
        nSocket.on("gameState") { args ->
            if (args[0] != null) {
                val gameState = args[0] as Int
                runOnUiThread {
                    if (gameState==3){
                        val intent= Intent(this, GuessingActivity::class.java)
                        intent.putExtra("Team_Data", team)
                        intent.putExtra("Answer_Data", answerServer)
                        intent.putExtra("Points_Data",pointsViewDisplay )

                        startActivity(intent)
                    }
                    else if(gameState==4){
                        val intent= Intent(this, DrawingActivity::class.java)
                        intent.putExtra("Team_Data", team)
                        intent.putExtra("Answer_Data", answerServer)
                        intent.putExtra("Points_Data", pointsViewDisplay)
                        startActivity(intent)
                    }
                    else if(gameState==6){
                        val intent= Intent(this, EndActivity::class.java)
                        intent.putExtra("Team_Data", team)
                        intent.putExtra("Points_Data", pointsViewDisplay)
                        intent.putExtra("Total_Winner_Data", winner)
                        startActivity(intent)
                    }
                }
            }
        }


//      Displays countdown.
        nSocket.on("answerCounter") { args ->
            if (args[0] != null) {
                val data = args[0] as String

                runOnUiThread {
                    textViewCountDown.text="  Next Round in: "+data
                }
            }
        }

//        //      Displays winner.
//        nSocket.on("roundWinner") { args ->
//            if (args[0] != null) {
//                val data = args[0] as String
//
//                runOnUiThread {
//                    textViewWinner.text=data
//                }
//            }
//        }
//     Displays points
//        nSocket.on("points") { args ->
//            if (args[0] != null) {
//                val data = args[0] as String
//
//                runOnUiThread {
//                    textViewPoints.text=data
//                }
//            }
//        }
//
//
////        Displaying the team that won
//
    }

}