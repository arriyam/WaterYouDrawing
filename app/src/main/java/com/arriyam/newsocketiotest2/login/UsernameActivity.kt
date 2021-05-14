package com.arriyam.newsocketiotest2.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.arriyam.newsocketiotest2.R
import com.arriyam.newsocketiotest2.socket.SocketHandler
import com.arriyam.newsocketiotest2.dataclass.User
import com.arriyam.newsocketiotest2.game.AnswerActivity
import com.arriyam.newsocketiotest2.game.DrawingActivity
import com.arriyam.newsocketiotest2.game.GuessingActivity


class UsernameActivity : AppCompatActivity() {

    lateinit var team:String
    lateinit var answerServer:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_username)


//      Socket connection for this activity
        val nSocket= SocketHandler.getSocket();

        nSocket.connect()
        val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
        val btnJoin = findViewById<Button>(R.id.buttonJoin)
        val textViewUserCount = findViewById<TextView>(R.id.textViewUserCount)
        val textViewTimer = findViewById<TextView>(R.id.textViewTimer)
        val textViewName= findViewById<TextView>(R.id.textViewName)
        val textViewWelcome= findViewById<TextView>(R.id.textViewWelcome)




        nSocket.on("answerWord") { args ->
            if (args[0] != null) {
                val answer = args[0] as String
                runOnUiThread {
                    answerServer=answer
                }
            }
        }

//        Declares what team you are in
        nSocket.on("team") { args ->
            if (args[0] != null) {
                val data = args[0] as String
                runOnUiThread {
                    team=data
                }
            }
        }

//      Switches to Sketching activity once timer is done

        nSocket.on("gameState") { args ->
            if (args[0] != null) {
                val gameState = args[0] as Int
                runOnUiThread {
                    if (gameState==3){
                        val intent= Intent(this, GuessingActivity::class.java)
                        intent.putExtra("Team_Data", team)
                        intent.putExtra("Answer_Data", answerServer)
                        intent.putExtra("Points_Data", "Points: R: 0 B: 0 ")

                        startActivity(intent)
                    }
                    else if(gameState==4){
                        val intent= Intent(this, DrawingActivity::class.java)
                        intent.putExtra("Team_Data", team)
                        intent.putExtra("Answer_Data", answerServer)
                        intent.putExtra("Points_Data", "Points: R: 0 B: 0 ")
                        startActivity(intent)
                    }
                }
            }
        }


//      Displays countdown.
        nSocket.on("counterVal") { args ->
            if (args[0] != null) {
                val data = args[0] as String

                runOnUiThread {
                    textViewTimer.text="Game starts in "+data+" seconds"
                }
            }
        }

//        Displays number of Players connected.
        nSocket.on("numberConnected") { args ->
            if (args[0] != null) {
                val data = args[0] as Int


                runOnUiThread {
                    textViewUserCount.text="Players in lobby: "+data.toString()
                }
            }
        }

        btnJoin.setOnClickListener {

            if (nSocket.connected()) {
                val username=editTextUsername.text.toString()
                if (username!="") {
                    var bob = User(editTextUsername.text.toString(), 30, 1)

                    nSocket.emit("join", bob)

                    textViewWelcome.text="Please wait for others to join."
                    editTextUsername.visibility = View.GONE
                    btnJoin.visibility = View.GONE;
                    textViewName.text = "Hello, " + bob.name
                }
                else{
                    Toast.makeText(this,"Username can not be blank.", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show()
            }

        }

    }




}