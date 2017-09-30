# SignalR-.net-core-android-client
Kotlin and java versions

## Getting Started

In this description we use connection to simple server with Bearer authorization.

Also, in this repository you can find app example written in kotlin language.

### Prerequisites

Working .net core SignalR server.

### Installing

```
implementation 'com.smartarmenia:dotnetcoresignalrclientjava:1.0.7'
```
or (for Android Studio 2.x)
```
compile 'com.smartarmenia:dotnetcoresignalrclientjava:1.0.7'
```

#### Kotlin example
```
private val connection: HubConnection = WebSocketHubConnection("http(https)://hubaddress/")
```

```
connection.connect("Bearer your_token")
connection.addListener(listener)
connection.subscribeToEvent(event, listener)
```

Invoke method

```
connection.invoke("Method", params...)
```

#### Java example

```
final HubConnection connection = new WebSocketHubConnection("http(https)://hubaddress/");
```

```
connection.connect("Bearer your_token");
connection.addListener(listener);
connection.subscribeToEvent(event, listener);
```

Invoke method

```
connection.invoke("Method", params...);
```