client =null;
client = new Paho.MQTT.Client("10.0.200.59", Number(9001), "121212212");

//client options
var options = {
    timeout: 3,
    keepAliveInterval:60,
    cleanSession: true,
    useSSL: false,
    onSuccess: onConnect,
    onFailure: OnConnectFailed,
    userName: "S_user",
    password:"aaa",
    mqttVersion:4
};

// subscribe_options
var subscribe_options ={
    qos : 1,
    onSuccess: OnSubSuccess,
    onFailure: OnSubFailed
    //timeout: 3
}

// connect the client
client.connect(options);


// called when the client loses its connection
function onConnectionLost(responseObject) {
    if (responseObject.errorCode !== 0) {
        console.log("onConnectionLost:onSubFailed:MQTT——链接丢失:"+responseObject.errorMessage);
    }
    client.connect(options);
    console.log("断后重连");
}

// called when the client connects
function onConnect() {
    // Once a connection has been made, make a subscription and send a message.
    console.log("onConnect");
//  client.subscribe("World");
//  message = new Paho.MQTT.Message("Hello");
//  message.destinationName = "World";
//  client.send(message);
    client.subscribe("hxquant/123456/#",subscribe_options);
}

// called when the client failed connect
function OnConnectFailed(responseObject) {
    if (responseObject.errorCode !== 0) {
        console.log("onConnectFailed:"+responseObject.errorMessage);
        client.connect(options);
        console.log("断后重连");
    }
}


function OnSubSuccess(responseObject) {
    console.log("onSubSccess");
    console.log(responseObject);
//    message = new Paho.MQTT.Message('{"type":"t_s","body":"Hello"}');//创建消息包
//    message.destinationName = "/hxquant/123456/#";
//    message.qos = 1;
//    client.send(message);
}


function OnSubFailed(responseObject) {
    if (responseObject.errorCode !== 0) {
        console.log("onSubFailed:" + responseObject.errorMessage);
        console.log(responseObject);
    }
}

