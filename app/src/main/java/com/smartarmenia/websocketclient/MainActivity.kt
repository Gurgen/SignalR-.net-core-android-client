package com.smartarmenia.websocketclient

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.gson.Gson
import com.smartarmenia.dotnetcoresignalrclientjava.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), HubConnectionListener, HubEventListener {
    override fun onEventMessage(message: HubMessage) {
        runOnUiThread { Toast.makeText(this@MainActivity, "Event message: ${message.target}\n${Gson().toJson(message.arguments)}", Toast.LENGTH_SHORT).show() }
    }

    override fun onConnected() {
        runOnUiThread { Toast.makeText(this@MainActivity, "Connected", Toast.LENGTH_SHORT).show() }
    }

    override fun onDisconnected() {
        runOnUiThread { Toast.makeText(this@MainActivity, "Disconnected", Toast.LENGTH_SHORT).show() }
    }

    override fun onMessage(message: HubMessage) {
        runOnUiThread { Toast.makeText(this@MainActivity, "${message.target}\n${Gson().toJson(message.arguments)}", Toast.LENGTH_SHORT).show() }
    }

    override fun onError(exception: Exception) {
        runOnUiThread { Toast.makeText(this@MainActivity, exception.message, Toast.LENGTH_SHORT).show() }
    }

    private val authHeader = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6Ijc5NzhjMjI3LWViMGItNGMwOS1iYWEyLTEwYmE0MjI4YWE4OSIsImNlcnRzZXJpYWxudW1iZXIiOiJtYWNfYWRkcmVzc19vZl9waG9uZSIsInNlY3VyaXR5U3RhbXAiOiJlMTAxOWNiYy1jMjM2LTQ0ZTEtYjdjYy0zNjMxYTYxYzMxYmIiLCJuYmYiOjE1MDYyODQ4NzMsImV4cCI6NDY2MTk1ODQ3MywiaWF0IjoxNTA2Mjg0ODczLCJpc3MiOiJCbGVuZCIsImF1ZCI6IkJsZW5kIn0.QUh241IB7g3axLcfmKR2899Kt1xrTInwT6BBszf6aP4"
    private val connection: HubConnection = WebSocketHubConnectionP2("http://192.168.1.8:5002/signalr/hubs/auth", authHeader)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connection.addListener(this@MainActivity)
        connection.subscribeToEvent("Send", this)

        btnSendMessage.setOnClickListener {
            try {
                connection.invoke("Send", "Hello")
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }

        btnConnect.setOnClickListener {
            connect()
        }

        btnDisconnect.setOnClickListener { connection.disconnect() }
    }

    override fun onDestroy() {
        super.onDestroy()
        connection.removeListener(this)
        connection.unSubscribeFromEvent("Send", this)
        connection.disconnect()
    }

    private fun connect() {
        try {
            connection.connect()
        } catch (ex: Exception) {
            runOnUiThread { Toast.makeText(this@MainActivity, ex.message, Toast.LENGTH_SHORT).show() }
        }
    }
}
