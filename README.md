# SignalR-.net-core-android-client
Kotlin and java versions

## Getting Started

In this description we use connection to simple server with Bearer authorization.

Also, in this repository you can find app example written in kotlin language.

### Prerequisites

Working .net core SignalR server.

### Installing

```
implementation 'com.smartarmenia:dotnetcoresignalrclientjava:1.12'
```
or (for Android Studio 2.x)
```
compile 'com.smartarmenia:dotnetcoresignalrclientjava:1.12'
```

#### Kotlin example
For alpha version
```
private val connection: HubConnection = WebSocketHubConnection("http(https)://hubaddress/", "Bearer your_token")
```
For preview2-final version
```
private val connection: HubConnection = WebSocketHubConnectionP2("http(https)://hubaddress/", "Bearer your_token")
```

```
connection.addListener(listener)
connection.subscribeToEvent(event, listener)
connection.connect()
```

Invoke method

```
connection.invoke("Method", params...)
```

#### Java example

For alpha version
```
final HubConnection connection = new WebSocketHubConnection("http(https)://hubaddress/", "Bearer your_token");
```
For preview2-final version
```
final HubConnection connection = new WebSocketHubConnectionP2("http(https)://hubaddress/", "Bearer your_token");
```

```
connection.addListener(listener);
connection.subscribeToEvent(event, listener);
connection.connect();
```

Invoke method

```
connection.invoke("Method", params...);
```

### Help

If you get Multidex... error on compile, clean and rebuild project

### Contribution and Issues

I you want to participate in this project or have proposals, ideas or wishes, please create issue.