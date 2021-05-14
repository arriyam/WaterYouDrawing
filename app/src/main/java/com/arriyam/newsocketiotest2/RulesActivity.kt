package com.arriyam.newsocketiotest2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arriyam.newsocketiotest2.socket.SocketHandler

class RulesActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rules)
        //      Socket connection for this activity
        val nSocket= SocketHandler.getSocket();


    }
}