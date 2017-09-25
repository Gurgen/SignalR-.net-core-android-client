package com.smartarmenia.dotnetcoresignalrclient

interface HubConnection {
    fun connect(authHeader: String? = null)
    fun disconnect()
    fun addListener(listener: HubConnectionListener)
    fun removeListener(listener: HubConnectionListener)
    fun subscribeToEvent(eventName: String, eventListener: HubEventListener)
    fun unSubscribeFromEvent(eventName: String, eventListener: HubEventListener)
    fun invoke(event: String, vararg parameters: Any)
}
