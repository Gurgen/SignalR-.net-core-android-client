# SignalR-.net-core-android-client
Kotlin and java versions

## Getting Started

In this description we use connection to simple server with Bearer authorization.

Also, in this repository you can find app example written in kotlin language.

### Prerequisites

Working .net core SignalR server.

### Installing

```
implementation 'com.smartarmenia:dotnetcoresignalrclientjava:1.0.6'
```
or (for Android Studio 2.x)
```
compile 'com.smartarmenia:dotnetcoresignalrclientjava:1.0.6'
```

#### Kotlin example
```
private val connection: HubConnection = WebSocketHubConnection("http(https)://hubaddress/")
```

```
Thread(Runnable {
            try {
                connection.connect("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6Ijc5NzhjMjI3LWViMGItNGMwOS1iYWEyLTEwYmE0MjI4YWE4OSIsImNlcnRzZXJpYWxudW1iZXIiOiJtYWNfYWRkcmVzc19vZl9waG9uZSIsInNlY3VyaXR5U3RhbXAiOiJlMTAxOWNiYy1jMjM2LTQ0ZTEtYjdjYy0zNjMxYTYxYzMxYmIiLCJuYmYiOjE1MDYyODQ4NzMsImV4cCI6NDY2MTk1ODQ3MywiaWF0IjoxNTA2Mjg0ODczLCJpc3MiOiJCbGVuZCIsImF1ZCI6IkJsZW5kIn0.QUh241IB7g3axLcfmKR2899Kt1xrTInwT6BBszf6aP4")
            } catch (ex: Exception) {
                runOnUiThread { Toast.makeText(this@MainActivity, ex.message, Toast.LENGTH_SHORT).show() }
            }
            connection.addListener(this@MainActivity)
            connection.subscribeToEvent("Send", this)
        }).start()
```