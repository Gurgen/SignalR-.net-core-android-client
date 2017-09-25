package com.smartarmenia.dotnetcoresignalrclient

import com.google.gson.JsonElement

class SignalRMessage {
    var invocationId: String? = null
    var type: Int? = null
    var target:String? = null
    var nonBlocking: Boolean? = null
    var arguments:Array<JsonElement>? = null
}