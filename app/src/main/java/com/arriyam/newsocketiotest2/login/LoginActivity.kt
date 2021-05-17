package com.arriyam.newsocketiotest2.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.arriyam.newsocketiotest2.R
import com.arriyam.newsocketiotest2.dataclass.User
import com.arriyam.newsocketiotest2.socket.SocketHandler

class LoginActivity: AppCompatActivity() {

//    lateinit var listData:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //        Connecting to the socket using SocketHandler Object
        SocketHandler.setSocket()
        //      Socket connection for this activity
        val nSocket= SocketHandler.getSocket();

        nSocket.connect()

        val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
        val btnJoin = findViewById<Button>(R.id.buttonJoin)


//        nSocket.on("connectedPlayersA") { args ->
//            if (args[0] != null) {
//                val answer = args[0] as String
//                runOnUiThread {
//                    listData=answer
//                }
//            }
//        }


        btnJoin.setOnClickListener {

            if (nSocket.connected()) {
                val username=editTextUsername.text.toString()
                if (username!="") {
                    var bob = User(username, 30, 1)
                    nSocket.emit("join", bob)
                    val intent= Intent(this, UsernameActivity::class.java)
                    intent.putExtra("Username_Data", username)
//                    intent.putExtra("List_Data", listData)
                    startActivity(intent)
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