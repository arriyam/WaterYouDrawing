package com.arriyam.newsocketiotest2.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
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

//    private lateinit var listView:ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_username)


//      Socket connection for this activity
        val nSocket= SocketHandler.getSocket();

        val textViewUserCount = findViewById<TextView>(R.id.textViewUserCount)
        val textViewTimer = findViewById<TextView>(R.id.textViewTimer)
        val textViewName= findViewById<TextView>(R.id.textViewName)
        val listView = findViewById<ListView>(R.id.list_displayed)



//        Data from LoginActivity
        val username = intent.getStringExtra("Username_Data")
//        val listData = intent.getStringExtra("List_Data")
//        if (listData!=null){
//            val listItems = listData.split(",").toTypedArray()
//            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
//            listView.adapter = adapter
//        }



        nSocket.on("connectedPlayersA") { args ->
            if (args[0] != null) {
                val answer = args[0] as String
                runOnUiThread {
                    val listItems = answer.split(",").toTypedArray()
                    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
                    listView.adapter = adapter
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
                        intent.putExtra("Points_Data", "Points: R: 0 B: 0  ")

                        startActivity(intent)
                    }
                    else if(gameState==4){
                        val intent= Intent(this, DrawingActivity::class.java)
                        intent.putExtra("Team_Data", team)
                        intent.putExtra("Answer_Data", answerServer)
                        intent.putExtra("Points_Data", "Points: R: 0 B: 0  ")
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

        textViewName.text = "Hello, " + username


    }




}