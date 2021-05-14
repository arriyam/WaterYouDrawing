package com.arriyam.newsocketiotest2.game

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.arriyam.newsocketiotest2.R
import com.arriyam.newsocketiotest2.RulesActivity
import com.arriyam.newsocketiotest2.sketch.DrawingView
import com.arriyam.newsocketiotest2.game.GuessingActivity
import com.arriyam.newsocketiotest2.socket.SocketHandler

class DrawingActivity : AppCompatActivity() {


    lateinit var drawingViews: DrawingView
    lateinit var answerServer:String
    lateinit var points:String
    lateinit var roundWinner:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)

        drawingViews = findViewById<DrawingView>(R.id.canvas)
        val button = findViewById<Button>(R.id.button)
        val textViewCountDown = findViewById<TextView>(R.id.textViewCountDown)
        val textViewPoints = findViewById<TextView>(R.id.textViewPoints)
        val textViewRedBanner = findViewById<TextView>(R.id.textViewRedBanner)
        val textViewBlueBanner = findViewById<TextView>(R.id.textViewBlueBanner)
        val textViewGameAnswer=findViewById<TextView>(R.id.textViewGameAnswer)


//      Socket connection for this activity
        val nSocket= SocketHandler.getSocket();

        //      Switches to Sketching activity once timer is done


//      What team is pulled from the username activity which got it from the server.
        val team = intent.getStringExtra("Team_Data")
        val answerServer=intent.getStringExtra("Answer_Data")
        val pointsViewDisplay=intent.getStringExtra("Points_Data")


//      Red or blue banner is displayed according to what team is viewing it
        if (team=="R"){
            textViewBlueBanner.visibility = View.GONE;
        }
        else{
            textViewRedBanner.visibility = View.GONE;
        }

//        Tells the drawer what to draw
        textViewGameAnswer.text="You are Drawing: "+answerServer
//      Tell the player how many points they have
        textViewPoints.text=pointsViewDisplay


//      Going to answer activity
        nSocket.on("gameState") { args ->
            if (args[0] != null) {
                val gameState = args[0] as Int
                runOnUiThread {
                    if (gameState==5){
                        val intent= Intent(this, AnswerActivity::class.java)
                        intent.putExtra("Answer_Data", answerServer)
                        intent.putExtra("Team_Data", team)
                        intent.putExtra("Points_Data",points)
                        intent.putExtra("Winner_Data",roundWinner)
                        startActivity(intent)
                    }
                }
            }
        }



                //      Displays winner.
        nSocket.on("roundWinner") { args ->
            if (args[0] != null) {
                val data = args[0] as String
                runOnUiThread {
                    roundWinner=data
                }
            }
        }


        //      Displays countdown.
        nSocket.on("gameCounter") { args ->
            if (args[0] != null) {
                val data = args[0] as String

                runOnUiThread {
                    textViewCountDown.text="  Countdown: "+data
                }
            }
        }




//     Displays points
        nSocket.on("points") { args ->
            if (args[0] != null) {
                val data = args[0] as String

                runOnUiThread {
                    points=data
                }
            }
        }

//        Button to clear
        button.setOnClickListener {
            clearCanvas()
            nSocket.emit("clear")
            nSocket.emit("release")
        }

    }

    fun clearCanvas() {
        drawingViews.clearCanvas()
    }
}


