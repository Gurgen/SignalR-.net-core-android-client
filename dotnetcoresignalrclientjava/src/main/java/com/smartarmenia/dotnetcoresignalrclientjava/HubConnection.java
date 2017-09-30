package com.smartarmenia.dotnetcoresignalrclientjava;

public interface HubConnection {
    void connect();

    void disconnect();

    void addListener(HubConnectionListener listener);

    void removeListener(HubConnectionListener listener);

    void subscribeToEvent(String eventName, HubEventListener eventListener);

    void unSubscribeFromEvent(String eventName, HubEventListener eventListener);

    void invoke(String event, Object... parameters);
}
