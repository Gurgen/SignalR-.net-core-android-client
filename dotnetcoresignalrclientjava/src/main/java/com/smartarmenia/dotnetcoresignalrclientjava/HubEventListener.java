package com.smartarmenia.dotnetcoresignalrclientjava;

public interface HubEventListener {
    void onEventMessage(HubMessage message);
}
