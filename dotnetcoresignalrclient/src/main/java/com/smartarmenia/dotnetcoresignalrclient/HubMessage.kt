package com.smartarmenia.dotnetcoresignalrclient

import com.google.gson.JsonElement

class HubMessage(val invocationId: String = "", val target: String = "", val arguments: Array<JsonElement> = emptyArray())