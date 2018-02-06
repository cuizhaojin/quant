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
        console.log("onConnectionLost:onSubFailed:MQTT——链接丢失:"+responseObject.errorMessage);
    }
    client.connect(options);
    console.log("断后重连");
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
                    $("#log .less-container").scrollTop($('#log .less-container')[0].scrollHeight);
                    break;
                //warn
                case 2:
                    $("#errorlog .less-container").append("<div>"+obj[0].content.log_info+"</div>");
                    break;
                //errorlog
                case 3:
                    $("#errorlog .less-container").append("<div>"+obj[0].content.log_info+"</div>");
                    break;
                //critical
                case 4:
                    $("#errorlog .less-container").append("<div>"+obj[0].content.log_info+"</div>");
                    break;
            };
            break;
        //backtest
        case 1:
            var html = "<tr><td>"+obj[0].content.date+"</td>" +
                "<td>"+parseFloat(obj[0].content.alpha)+"</td>" +
                "<td>"+parseFloat(obj[0].content.info_ratio)+"</td>" +
                "<td>"+parseFloat(obj[0].content.sharpe_ratio)+"</td>" +
                "<td>"+parseFloat(obj[0].content.stock_returns)+"</td>" +
                "<td>"+parseFloat(obj[0].content.beta)+"</td>" +
                "<td>"+parseFloat(obj[0].content.volatility)+"</td>" +
                "<td>"+obj[0].content.max_drawdown+"</td>" +
                "<td>"+parseFloat(obj[0].content.benchmark_returns)+"</td>" +
                "<td>"+parseFloat(obj[0].content.total_profit)+"</td>" +
                "<td>"+parseFloat(obj[0].content.capital_left)+"</td></tr>";
            $("#backtest tbody").append(html);
            break;
        //tradelist
        case 2:
            var content = obj[0].content;
            var tradelist = content.trade_list;
            var datestr = content.date;
            var stockid = content.stockid;
            var htmlstr ="";
            for(var i = 0 ;i < tradelist.length; i ++ ){
                var tr = "<tr><td>"+datestr+"</td>" +
                    "<td>"+stockid+"</td>" +
                    "<td>***</td>" +
                    "<td>"+tradelist[i].bs+"</td>" +
                    "<td>"+tradelist[i].order_type+"</td>" +
                    "<td>"+tradelist[i].mount+"</td>" +
                    "<td>"+tradelist[i].price+"</td>" +
                    "<td>"+tradelist[i].profit+"</td>" +
                    "<td>"+tradelist[i].act_type+"</td>" +
                    "<td>"+tradelist[i].fee+"</td></tr>";
                htmlstr = htmlstr+ tr;
            }
            $("#tradelist tbody").append(htmlstr);
            break;
        //holdlist
        case 3:
            var html = "<tr><td>"+obj[0].content.date+"</td>" +
                "<td>"+obj[0].content.stockid+"</td>" +
                "<td>*****</td>" +
                "<td>"+obj[0].content.avg_price+"</td>" +
                "<td>"+obj[0].content.profit+"</td>" +
                "<td>"+obj[0].content.mount+"</td>" +
                "<td>"+obj[0].content.close+"</td></tr>";
            $("#holdlist tbody").append(html);
            break;
    }
}
