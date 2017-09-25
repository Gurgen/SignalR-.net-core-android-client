package com.smartarmenia.dotnetcoresignalrclient

interface HubConnectionListener {
    fun onConnected()
    fun onDisconnected()
    fun onMessage(message: HubMessage)
    fun onError(exception: Exception)
}