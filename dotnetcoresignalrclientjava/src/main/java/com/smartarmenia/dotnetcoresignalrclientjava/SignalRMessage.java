package com.smartarmenia.dotnetcoresignalrclientjava;

import com.google.gson.JsonElement;

public class SignalRMessage {
    private String invocationId;
    private Integer type;
    private String target;
    private Boolean nonBlocking;
    private JsonElement[] arguments;

    public String getInvocationId() {
        return invocationId;
    }

    public void setInvocationId(String invocationId) {
        this.invocationId = invocationId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Boolean getNonBlocking() {
        return nonBlocking;
    }

    public void setNonBlocking(Boolean nonBlocking) {
        this.nonBlocking = nonBlocking;
    }

    public JsonElement[] getArguments() {
        return arguments;
    }

    public void setArguments(JsonElement[] arguments) {
        this.arguments = arguments;
    }
}
