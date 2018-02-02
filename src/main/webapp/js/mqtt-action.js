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

// set callback handlers
client.onConnectionLost = onConnectionLost;
client.onMessageArrived = onMessageArrived;

// called when the client loses its connection
function onConnectionLost(responseObject) {
    if (responseObject.errorCode !== 0) {
        console.log("onConnectionLost:"+responseObject.errorMessage);
    }
}

// called when a message arrives
function onMessageArrived(message) {
    tailDingYue(message.payloadString);
    console.log("onMessageArrived:"+message.payloadString);
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
        console.log(responseObject);

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
function tailDingYue(message) {
    var obj = eval('('+message+')');
    var flag = obj[0].type;
    switch (flag) {
        //error
        case -1:
            break;
        //log
        case 0:
            var errortype = obj[0].content.log_level;
            console.info(obj[0].content.log_info);
            switch (errortype){
                //info
                case 1:
                    $("#log .less-container").append("<div>"+obj[0].content.log_info+"</div>");
                    break;
                //warn
                case 2:
                    break;
                //errorlog
                case 3:
                    $("#errorlog .less-container").append("<div>"+obj[0].content.log_info+"</div>");
                    break;
                //critical
                case 4:
                    break;
            };
            break;
        //backtest
        case 1:
            break;
        //tradelist
        case 2:
            break;
        //holdlist
        case 3:
            break;
    }
}