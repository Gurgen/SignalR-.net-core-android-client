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
                connection.connect("Bearer your_token")
            } catch (ex: Exception) {
                runOnUiThread { Toast.makeText(this@MainActivity, ex.message, Toast.LENGTH_SHORT).show() }
            }
            connection.addListener(this@MainActivity)
            connection.subscribeToEvent("Send", this)
        }).start()
```

Invoke method

```
btnHello.setOnClickListener {
            connection.invoke("Send", "Hello")
        }
```

#### Java example

```
final HubConnection connection = new WebSocketHubConnection("http://192.168.0.104:5002/signalr/hubs/auth");
```

```
new Thread(new Runnable() {
            @Override
            public void run() {
                connection.connect("Bearer your_token");
            }
        }).start();
```

Invoke method

```
btnHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection.invoke("Send", "Hello");
            }
        });
```