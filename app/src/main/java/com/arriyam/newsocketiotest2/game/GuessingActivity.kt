package com.arriyam.newsocketiotest2.game

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.arriyam.newsocketiotest2.R
import com.arriyam.newsocketiotest2.RulesActivity
import com.arriyam.newsocketiotest2.sketch.GuessingView
import com.arriyam.newsocketiotest2.socket.SocketHandler

class GuessingActivity : AppCompatActivity() {



    lateinit var guessingViews: GuessingView
    lateinit var answerServer:String
    lateinit var points:String
    lateinit var roundWinner:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guesser)

        guessingViews=findViewById<GuessingView>(R.id.canvas)
        val editTextGuess=findViewById<EditText>(R.id.editTextGuess1)
        val textViewCountDown = findViewById<TextView>(R.id.textViewCountDown)
        val textViewPoints = findViewById<TextView>(R.id.textViewPoints)
        val textViewRedBanner = findViewById<TextView>(R.id.textViewRedBanner)
        val textViewBlueBanner = findViewById<TextView>(R.id.textViewBlueBanner)

//      Socket connection for this activity
        val nSocket= SocketHandler.getSocket();

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

        //      Tell the player how many points they have
        textViewPoints.text=pointsViewDisplay



        //      Displaying answer activity
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


//        First paint coordinate received from server
        nSocket.on("mouseAndroidStart") { args ->
            if (args[0] != null) {
                val mouse = args[0] as String
//                Log.v("Hen",mouse)
                runOnUiThread {
                    mouseAndroidStart(mouse)
                }
            }
        }

//      Paint coordinates between the start and end received from server
        nSocket.on("mouseAndroidMiddle") { args ->
            if (args[0] != null) {
                val mouse = args[0] as String
//                Log.v("Hen",mouse)
                runOnUiThread {
                    mouseAndroidMiddle(mouse)
                }
            }
        }
//        Server asked the server to clear Canvas
        nSocket.on("clear") {
            clearCanvas()
        }



        editTextGuess.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                Toast.makeText(this, editTextGuess.text.toString(), Toast.LENGTH_SHORT).show()
                nSocket.emit("guess",editTextGuess.text.toString())
                handled = true
            }
            handled
        })


    }

    fun clearCanvas(){
        guessingViews.clearCanvas()
    }

    private fun mouseAndroidStart(mouse:String){
        guessingViews.mouseAndroidStart(mouse)
    }

    private fun mouseAndroidMiddle(mouse:String){
        guessingViews.mouseAndroidMiddle(mouse)
    }

//    private fun mouseAndroidEnd(){
//        canvasViews.mouseAndroidEnd()
//    }



}