
var taskid = '#';
var chart;
function popPoint(arrayobj1,arrayobj2) {
    setInterval(function () {
        if(arrayobj1.length>0){
            var temp = arrayobj1.pop();
            var temp2 = arrayobj2.pop();
            console.info(temp);
            console.info(temp2);
            chart.series[0].addPoint(temp, true, false);
            chart.series[1].addPoint(temp2, true, false);
            //activeLastPointToolip(chart);
        }

    },100);
}
function getCharts() {
    chart = Highcharts.StockChart('main', {
        chart: {//图表全局属性
            type: 'spline',
            animation: Highcharts.svg, // don't animate in old IE
            marginRight: 10
        },
        credits: { //版本信息
            enabled: true,
            href: 'http://www.hexun.com',
            text: 'www.hexun.com'

        },
        xAxis: {// x 轴
            type: "datetime",
            dateTimeLabelFormats: { // don't display the
                // dummy year
                day: '%Y-%m-%d',
                week: '%Y-%m-%d',
                month: '%Y-%m-%d',
                year: '%Y-%m-%d'
            }
        },
        yAxis: {   //y轴
            title: {
                text: '值'
            },
            plotLines: [
                {
                    value: 0,
                    width: 1,
                    color: '#808080'
                }
            ]
        },
        legend: {//图例
            enabled: false
        },
        exporting: {   //导出模块
            enabled: false
        },
        rangeSelector: {  //范围选择按钮组
            enabled: false
        },
        tooltip: {  //提示框
            split: false
        },
        series: [  //数据列
            {
                name: '基准收益',
                "data": [ ]
            },
            {
                name: '回测收益',
                "data": [ ]
            }
        ]
    });
}
client = null;
connected = false;


// subscribe_options
var subscribe_options = {
    qos: 1,
    onSuccess: OnSubSuccess,
    onFailure: OnSubFailed
    //timeout: 3
}

// connect the client
function connect(){
    client = new Paho.MQTT.Client("10.0.200.59", Number(9001), "121212212");

    //client options
    var options = {
        timeout: 3,
        keepAliveInterval: 60,
        cleanSession: true,
        useSSL: false,
        onSuccess: onConnect,
        onFailure: OnConnectFailed,
        userName: "S_user",
        password: "aaa",
        mqttVersion: 4
    };
    client.connect(options);
    // set callback handlers
    client.onConnectionLost = onConnectionLost;
    client.onMessageArrived = onMessageArrived;
}

//disconnect function
function disconnect(){
    console.info('Disconnecting from Server');
    client.disconnect();
    connected = false;
}
// called when the client loses its connection
function onConnectionLost(responseObject) {
    if (responseObject.errorCode !== 0) {
        console.log("onConnectionLost:onSubFailed:MQTT——链接丢失:" + responseObject.errorMessage);
    }
    connected = false;
}

// called when a message arrives
function onMessageArrived(message) {
    tailDingYue(message.payloadString);
   // console.log("onMessageArrived:" + message.payloadString);
}

// called when the client connects
function onConnect() {
    // Once a connection has been made, make a subscription and send a message.
    console.log("onConnect"+"hxquant/123456/"+taskid);
    client.subscribe("hxquant/123456/"+taskid, subscribe_options);
    connected = true;
}

// called when the client failed connect
function OnConnectFailed(responseObject) {
    if (responseObject.errorCode !== 0) {
        console.log("onConnectFailed:" + responseObject.errorMessage);
        connected = false;
    }
}


function OnSubSuccess(responseObject) {
    console.log("onSubSccess");
    console.log(responseObject);
}


function OnSubFailed(responseObject) {
    if (responseObject.errorCode !== 0) {
        console.log("onSubFailed:" + responseObject.errorMessage);
        console.log(responseObject);
    }
}

function tailDingYue(message) {
    var obj = eval('(' + message + ')');
    var flag = obj[0].type;
    switch (flag) {
        //error
        case -1:
            break;
        //log
        case 0:
            var errortype = obj[0].content.log_level;
            switch (errortype) {
                //info
                case 1:
                    $("#log .less-container").append("<div>" + obj[0].content.log_info + "</div>");
                    $("#log .less-container").scrollTop($('#log .less-container')[0].scrollHeight);
                    break;
                //warn
                case 2:
                    $("#errorlog .less-container").append("<div>" + obj[0].content.log_info + "</div>");
                    break;
                //errorlog
                case 3:
                    $("#errorlog .less-container").append("<div>" + obj[0].content.log_info + "</div>");
                    break;
                //critical
                case 4:
                    $("#errorlog .less-container").append("<div>" + obj[0].content.log_info + "</div>");
                    break;
            }
            ;
            break;
        //backtest
        case 1:
            var html = "<tr><td>" + obj[0].content.date + "</td>" +
                "<td>" + parseFloat(obj[0].content.alpha) + "</td>" +
                "<td>" + parseFloat(obj[0].content.info_ratio) + "</td>" +
                "<td>" + parseFloat(obj[0].content.sharpe_ratio) + "</td>" +
                "<td>" + parseFloat(obj[0].content.stock_returns) + "</td>" +
                "<td>" + parseFloat(obj[0].content.beta) + "</td>" +
                "<td>" + parseFloat(obj[0].content.volatility) + "</td>" +
                "<td>" + obj[0].content.max_drawdown + "</td>" +
                "<td>" + parseFloat(obj[0].content.benchmark_returns) + "</td>" +
                "<td>" + parseFloat(obj[0].content.total_profit) + "</td>" +
                "<td>" + parseFloat(obj[0].content.capital_left) + "</td></tr>";
            $("#backtest tbody").append(html);
            $("#stock_returns").text(obj[0].content.stock_returns);
            $("#sharpe_ratio").text(obj[0].content.sharpe_ratio);
            $("#benchmark_returns").text(obj[0].content.benchmark_returns);
            $("#alpha").text(obj[0].content.alpha);
            $("#beta").text(obj[0].content.beta);
            $("#max_drawdown").text(obj[0].content.max_drawdown[0]);
            onMessageArrived2(message);
            break;
        //tradelist
        case 2:
            var content = obj[0].content;
            var tradelist = content.trade_list;
            var datestr = content.date;
            var stockid = content.stockid;
            var htmlstr = "";
            for (var i = 0; i < tradelist.length; i++) {
                var tr = "<tr><td>" + datestr + "</td>" +
                    "<td>" + stockid + "</td>" +
                    "<td>***</td>" +
                    "<td>" + tradelist[i].bs + "</td>" +
                    "<td>" + tradelist[i].order_type + "</td>" +
                    "<td>" + tradelist[i].mount + "</td>" +
                    "<td>" + tradelist[i].price + "</td>" +
                    "<td>" + tradelist[i].profit + "</td>" +
                    "<td>" + tradelist[i].act_type + "</td>" +
                    "<td>" + tradelist[i].fee + "</td></tr>";
                htmlstr = htmlstr + tr;
            }
            $("#tradelist tbody").append(htmlstr);
            break;
        //holdlist
        case 3:
            var html = "<tr><td>" + obj[0].content.date + "</td>" +
                "<td>" + obj[0].content.stockid + "</td>" +
                "<td>*****</td>" +
                "<td>" + obj[0].content.avg_price + "</td>" +
                "<td>" + obj[0].content.profit + "</td>" +
                "<td>" + obj[0].content.mount + "</td>" +
                "<td>" + obj[0].content.close + "</td></tr>";
            $("#holdlist tbody").append(html);
            break;
    }
}

Highcharts.setOptions({//highchart 全局属性
    global: {
        useUTC: false
    }
});
function activeLastPointToolip(chart) {
    var points = chart.series[0].points;
    chart.tooltip.refresh(points[points.length -1]);
}

var arramqtt = [];
var arramqtt2 =[];
var attachflag = false;

//called when a message arrives
function onMessageArrived2(message) {
   // console.log("onMessageArrived:"+message);
    var obj = eval('('+message+')');
    var flag = obj[0].type;
    if(flag=='1') {
        var x = new Date(obj[0].content.date).getTime();
        var y = obj[0].content.benchmark_returns;
        var y2 = obj[0].content.stock_returns;
       // console.info('x= '+x+' y= '+y);
        arramqtt.unshift([x,y]);
        arramqtt2.unshift([x,y2])
        console.log("基准收益数组 "+[x,y] + "  插入");
        console.log("回测收益数组 "+[x,y2] + "  插入");
        var len = arramqtt.length;
        if(len>=10 && !attachflag){
            attachflag = true;
            popPoint(arramqtt,arramqtt2)
        }
    }
}

