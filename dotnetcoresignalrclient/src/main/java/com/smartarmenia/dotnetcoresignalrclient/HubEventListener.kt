package com.smartarmenia.dotnetcoresignalrclient

interface HubEventListener {
    fun onEventMessage(message: HubMessage)
}