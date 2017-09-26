package com.smartarmenia.dotnetcoresignalrclient

import android.net.Uri
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonElement
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.handshake.ServerHandshake
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import java.util.*

class WebSocketHubConnection(private val hubUrl: String) : HubConnection {
    companion object {
        const val SPECIAL_SYMBOL = "\u001E"
        const val TAG = "WebSockets"
    }

    private lateinit var client: WebSocketClient
    private val listeners = mutableListOf<HubConnectionListener>()
    private val eventListeners = mutableMapOf<String, MutableList<HubEventListener>>()
    private val parsedUri = Uri.parse(hubUrl)
    private val gson = Gson()

    override fun connect(authHeader: String?) {
        Log.i(TAG, "Requesting connection id...")
        if (!(parsedUri.scheme == "http" || parsedUri.scheme == "https"))
            throw RuntimeException("URL must start with http or https")

        val connection: HttpURLConnection = URL(hubUrl).openConnection() as HttpURLConnection
        if (authHeader != null) {
            connection.addRequestProperty("Authorization", authHeader)
        }
        connection.connectTimeout = 15000
        connection.readTimeout = 15000
        connection.requestMethod = "OPTIONS"
        val responseCode = connection.responseCode
        when (responseCode) {
            200 -> {
                val result = InputStreamConverter.convert(connection.inputStream)
                val jsonElement = gson.fromJson<JsonElement>(result, JsonElement::class.java)
                val connectionId = jsonElement.asJsonObject.get("connectionId").asString
                if (!jsonElement.asJsonObject.get("availableTransports").asJsonArray.toList().map { it.asString }.contains("WebSockets")) {
                    throw RuntimeException("The server does not support WebSockets transport")
                }
                connectClient(connectionId, authHeader)
            }
            401 -> throw RuntimeException("Unauthorized request")
            else -> throw RuntimeException("Server error")
        }
    }

    private fun connectClient(connectionId: String?, authHeader: String?) {
        val uriBuilder = parsedUri.buildUpon()
        uriBuilder.appendQueryParameter("id", connectionId)
        uriBuilder.scheme(parsedUri.scheme.replace("http", "ws"))
        val uri = uriBuilder.build()
        val headers = if (authHeader == null) null else mapOf("Authorization" to authHeader)
        client = object : WebSocketClient(URI(uri.toString()), Draft_6455(), headers, 15000) {
            override fun onOpen(serverHandshake: ServerHandshake) {
                Log.i(TAG, "Opened")
                listeners.forEach { it.onConnected() }
                send("{\"protocol\":\"json\"}$SPECIAL_SYMBOL")
            }

            override fun onMessage(s: String) {
                Log.i(TAG, s)
                val element = gson.fromJson<SignalRMessage>(s.replace(SPECIAL_SYMBOL, ""), SignalRMessage::class.java)
                if (element.type == 1) {
                    val message = HubMessage(element.invocationId!!, element.target!!, element.arguments!!)
                    listeners.forEach { it.onMessage(message) }
                    eventListeners.get(message.target)?.forEach { it.onEventMessage(message) }
                }
            }

            override fun onClose(i: Int, s: String, b: Boolean) {
                Log.i(TAG, "Closed. Code: $i, Reason: $s, Remote: $b")
                listeners.forEach { it.onDisconnected() }
            }

            override fun onError(e: Exception) {
                Log.i(TAG, "Error " + e.message)
                listeners.forEach { it.onError(e) }
            }
        }
        Log.i(TAG, "Connecting...")
        client.connect()
    }

    override fun disconnect() {
        client.close()
    }

    override fun subscribeToEvent(eventName: String, eventListener: HubEventListener) {
        val eventMap: MutableList<HubEventListener>
        if (eventListeners.contains(eventName)) {
            eventMap = eventListeners.get(eventName)!!
        } else {
            eventMap = mutableListOf()
            eventListeners.put(eventName, eventMap)
        }
        eventMap.add(eventListener)
    }

    override fun unSubscribeFromEvent(eventName: String, eventListener: HubEventListener) {
        if (eventListeners.contains(eventName)) {
            val eventMap = eventListeners.get(eventName)!!
            eventMap.remove(eventListener)
            if (eventMap.isEmpty()) {
                eventListeners.remove(eventName)
            }
        }
    }

    override fun invoke(event: String, vararg parameters: Any) {
        val map = mapOf("type" to 1, "invocationId" to UUID.randomUUID().toString(), "target" to event, "arguments" to parameters, "nonblocking" to false)
        client.send(gson.toJson(map) + SPECIAL_SYMBOL)
    }

    override fun addListener(listener: HubConnectionListener) {
        listeners.add(listener)
    }

    override fun removeListener(listener: HubConnectionListener) {
        listeners.remove(listener)
    }

    private object InputStreamConverter {
        @Throws(IOException::class)
        internal fun convert(stream: InputStream): String {
            val r = BufferedReader(InputStreamReader(stream))
            val total = StringBuilder()
            r.lineSequence().forEach { line ->
                total.append(line)
                total.append('\n')
            }

            return total.toString()
        }
    }
}