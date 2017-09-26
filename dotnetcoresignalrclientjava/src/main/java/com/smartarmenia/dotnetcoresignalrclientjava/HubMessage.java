package com.smartarmenia.dotnetcoresignalrclientjava;

import com.google.gson.JsonElement;

public class HubMessage {
    private String invocationId = "";
    private String target = "";
    private JsonElement[] arguments;

    public HubMessage(String invocationId, String target, JsonElement[] arguments) {
        this.invocationId = invocationId;
        this.target = target;
        this.arguments = arguments;
    }

    public String getInvocationId() {
        return invocationId;
    }

    public void setInvocationId(String invocationId) {
        this.invocationId = invocationId;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public JsonElement[] getArguments() {
        return arguments;
    }

    public void setArguments(JsonElement[] arguments) {
        this.arguments = arguments;
    }
}
