<%--
  Created by IntelliJ IDEA.
  User: hexun
  Date: 2018/1/31
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/commons/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,
                                     initial-scale=1.0,
                                     maximum-scale=1.0,
                                     user-scalable=no">
    <title>回测图表</title>
    <link rel="shortcut icon" type="image/x-icon" href="${contextPath}/image/favicon.ico" media="screen">
    <link rel="stylesheet" href="${contextPath}/css/bootstrap.min.css">
    <link href="${contextPath}/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/paho-mqtt/1.0.1/mqttws31.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${contextPath}/plugin/layer/layer.js"></script>
    <script src="${contextPath}/js/highstock.js"  type="text/javascript" charset="utf-8"></script>
    <script src="${contextPath}/js/mqtt-chart-action.js"  type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" href="${contextPath}/css/style.css">
</head>
<body>
<!--导航-->
<div id="content-header" style="background-color: #e5e5e5;">
    <div class="container">
        <div id="breadcrumb"> <a href="${contextPath}/manager/list" class="tip-bottom" data-original-title="Go to Home"><i class="icon-home"></i> Home</a></div>
    </div>
</div>
<!--二级导航-->
<div class="subnavbar">
    <div class="subnavbar-inner">
        <div class="container">
            <ul class="mainnav">
                <li><a href="${contextPath}/manager/new"><i class="glyphicon glyphicon-edit"></i><span>主编辑区</span> </a> </li>
                <li class="active"><a href="${contextPath}/manager/benefit"><i class="icon-bar-chart"></i><span>收益概述</span> </a> </li>
                <li class="subnavbar-open-right"><a href="${contextPath}/manager/testdetail"><i class="glyphicon glyphicon-dashboard"></i><span>回测历史</span> </a></li>
                <li><a href="${contextPath}/manager/list"><i class="glyphicon glyphicon-list-alt"></i><span>我的策略</span> </a> </li>
                <li><a href="index.html"><i class="icon-plus "></i><span>new nav</span> </a> </li>
                <li class="dropdown subnavbar-open-right"><a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"> <i class="icon-plus"></i><span>下拉列表</span> <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="index.html">one</a></li>
                        <li><a href="index.html">two</a></li>
                        <li class="subnavbar-open-right"><a href="index.html">three</a></li>
                        <li><a href="index.html">four</a></li>
                        <li><a href="index.html">five</a></li>
                        <li class="subnavbar-open-right"><a href="index.html">six</a></li>
                    </ul>
                </li>
            </ul>
        </div>
        <!-- /container -->
    </div>
    <!-- /subnavbar-inner -->
</div>
<!--图表charts demo-->
<div class="container">
    <!-- 各种指标列表-->
    <div class="row" style="margin-bottom:5px;">
        <div class="col-md-2">
            <div class="sm-st clearfix">
                <div class="sm-st-info">
                    <span>4.00%</span>
                    策略收益
                </div>
            </div>
        </div>
        <div class="col-md-2">
            <div class="sm-st clearfix">
                <div class="sm-st-info">
                    <span>-0.186%</span>
                    策略年化收益
                </div>
            </div>
        </div>
        <div class="col-md-2">
            <div class="sm-st clearfix">
                <div class="sm-st-info">
                    <span>100,320</span>
                    基准收益
                </div>
            </div>
        </div>
        <div class="col-md-2">
            <div class="sm-st clearfix">
                <div class="sm-st-info">
                    <span>4567</span>
                    Alpha
                </div>
            </div>
        </div>
        <div class="col-md-2">
            <div class="sm-st clearfix">
                <div class="sm-st-info">
                    <span>4567</span>
                    Beta
                </div>
            </div>
        </div>
        <div class="col-md-2">
            <div class="sm-st clearfix">
                <div class="sm-st-info">
                    <span>4567</span>
                    最大回撤
                </div>
            </div>
        </div>
    </div>
    <!--chart body-->
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default" style="height: 600px;" id="main">

            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js" ></script>
<script>
    $(function () {
        Highcharts.setOptions({
            global: {
                useUTC: false
            }
        });
        var chart = Highcharts.chart('main', {
            chart: {
                type: 'spline',
                animation: Highcharts.svg, // don't animate in old IE
                marginRight: 10,
                events: {
                    load: function () {
                        // set callback handlers
                        client.onConnectionLost = onConnectionLost;
                        client.onMessageArrived = onMessageArrived;
                    }
                }
            },
            title: {
                text: '动态模拟实时数据'
            },
            xAxis: {
                type: "datetime",
                dateTimeLabelFormats : { // don't display the
                    // dummy year
                    day : '%Y-%m-%d',
                    week : '%Y-%m-%d',
                    month : '%Y-%m-%d',
                    year : '%Y-%m-%d'
                }
            },
            yAxis: {
                title: {
                    text: '值'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            legend: {
                enabled: false
            },
            exporting: {
                enabled: false
            },
            series: [{
                name: '随机数据',
                "data": [ ]
            }]
        });
        function activeLastPointToolip(chart) {
            var points = chart.series[0].points;
            chart.tooltip.refresh(points[points.length -1]);
        }

        var arramqtt = [];
        var attachflag = false;

        //called when a message arrives
        function onMessageArrived(message) {
            console.log("onMessageArrived:"+message.payloadString);

            var obj = eval('('+message.payloadString+')');
            var flag = obj[0].type;
            if(flag=='1') {
                var x = new Date(obj[0].content.date).getTime();
                var y = obj[0].content.benchmark_returns;
                console.info('x= '+x+' y= '+y);
                arramqtt.unshift([x,y]);
                console.log([x,y] + "  插入");
                console.log(arramqtt);
                var len = arramqtt.length;
                if(len>=10 && !attachflag){
                    attachflag = true;
                    popPoint(arramqtt)
                }
            }
        }
        function popPoint(arrayobj) {
            setInterval(function () {
                if(arrayobj.length>0){
                    console.log(arrayobj.pop());
                    chart.series[0].addPoint(arrayobj.pop(), true, false);
                    activeLastPointToolip(chart);
                }

            },100);
        }
    });

</script>
</body>
</html>
