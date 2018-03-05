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
    <title>编辑策略</title>
    <link rel="shortcut icon" type="image/x-icon" href="${contextPath}/image/favicon.ico" media="screen">
    <link rel="stylesheet" href="${contextPath}/css/bootstrap.min.css">
    <link href="${contextPath}/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <!--js  mqtt client-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/paho-mqtt/1.0.1/mqttws31.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${contextPath}/plugin/layer/layer.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.6/ace.js" type="text/javascript" charset="utf-8"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.6/ext-language_tools.js" type="text/javascript" charset="utf-8"></script>
    <script src="${contextPath}/plugin/page/jquery.paging.js"  type="text/javascript" charset="utf-8"></script>
    <script src="${contextPath}/js/highstock.js"  type="text/javascript" charset="utf-8"></script>
    <script src="${contextPath}/js/action.js"  type="text/javascript" charset="utf-8"></script>
    <script src="${contextPath}/js/mqtt-action.js"  type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" href="${contextPath}/css/style.css">
    <script>
        var flag = '${pageflag}';
        var rootPath = "${contextPath}";
        if(flag =='new'){
            $(document).attr("title", '新建策略');
        }else{
            $(document).attr("title", '编辑策略');
        }
        //获取的的策略列表
        function getMyAlgorithmList(){
            $.ajax({
                url: rootPath + '/manager/getAlgorithmList?pageSize=7&pageId=1',
                async: true,
                type: 'GET',
                data: {
                },
                dataType: 'json',
                success: function (data) {
                    if (data.result == 1) {
                      var html ="";
                      var obj = data.algorithmMainList;
                      for(var i = 0; i < obj.length ; i++){
                        var htmltemp = "<tr>"+
                                "<td style='vertical-align:middle'>"+obj[i].algorithm_id+"</td>"+
                                "<td><img src='/quant/image/code_wizard.png' alt=''/>" +
                                "<a href='/quant/manager/edit/"+obj[i].algorithm_id+"' style='color: #333;'>"+obj[i].algorithm_name+"</a></td>"+
                                "</tr>";
                        html = html + htmltemp;
                      }
                      $("#myalgorithm tbody").append(html);
                    }
                },
                error: function () {
                    layer.msg('获取策略列表初始化失败');
                }

            });
        }
        $(function(){
         getMyAlgorithmList();
         pageinit();
         $("a[value='首页']").css("display","none");
         $("a[value='尾页']").css("display","none");
        });

        function oprates(data) {
            if (data.result == 1) {
                var html = "";
                var obj = data.algorithmMainList;
                for (var i = 0; i < obj.length; i++) {
                    var htmltemp = "<tr>" +
                            "<td style='vertical-align:middle'>" + obj[i].algorithm_id + "</td>" +
                            "<td><img src='/quant/image/code_wizard.png' alt=''/>" +
                            "<a href='/quant/manager/edit/" + obj[i].algorithm_id + "' style='color: #333;'>" + obj[i].algorithm_name + "</a></td>" +
                            "</tr>";
                    html = html + htmltemp;
                }
                $("#myalgorithm tbody").html(html);
            } else {
                layer.msg('获取失败');
            }
        }


    </script>

    <script>
        function pageinit(){
            //分页
            var ispage = '${ispage}';
            var totalnum = '${total}';
            var totalpage = '${totalpage}';
            var currentPage = ${currentPage};
            if(ispage=='true'){
                ispage = true;
            }else{
                ispage = false;
            }
            //自定义皮肤，无跳转框，总长度100，总长度等参数实际使用由后台传入ajaxData
            if(ispage){
                $(".page").paging({
                    height: 30,
                    width: 11,
                    total: totalpage,
                    currentPage:currentPage,
                    showPage:3,
                    centerBgColor: "#fff",
                    centerFontColor: "#000",
                    centerBorder: "1px solid #ddd",
                    transition: "all .2s",
                    centerHoverBgColor: "#00a1d6",
                    centerHoverBorder: "1px solid #00a1d6",
                    centerFontHoverColor: "#fff",
                    otherFontHoverColor: "#fff",
                    otherBorder: "1px solid #ddd",
                    otherHoverBorder: "1px solid #00a1d6",
                    otherBgColor: "#fff",
                    otherHoverBgColor: "#00a1d6",
                    currentFontColor: "#fff",
                    currentBgColor: "#00a1d6",
                    currentBorder: "1px solid #00a1d6",
                    fontSize: 13,
                    currentFontSize: 13,
                    animation:true,
                    cormer: 2,
                    gapWidth: 3,
                    jumpBgColor: "#fff",
                    jumpFontHoverColor: "#fff",
                    jumpHoverBgColor: "#25dd3d",
                    jumpBorder: "1px solid #ddd",
                    jumpHoverBorder: "1px solid #25dd3d",
                    beforeBtnString: "<<",
                    nextBtnString: ">>",
                    submitStyle: "ajax",                 //点击按钮后的提交方式，有a标签的href提交和ajax异步提交两种选择
                    submitType: "get",                  //注明是通过get方式访问还是post方式访问
                    idParameter: "pageId",               //传到后台的当前页的id的参数名，这个传值会自动添加在href或ajax的url末尾
                    url: "/quant/manager/getAlgorithmList?pageSize=7",               //需要提交的目标控制器，如"/Home/List/"或"/Home/List?name='张三'&password='123456'"
                    limit: 5000,                         //设置滚动显示方式的极限值，大于则自动切换无滚动模式
                    animation: true,                     //是否是滚动动画方式呈现  false为精简方式呈现   页数大于limit时无论怎么设置自动默认为false
                    dataOperate: function oprate(data) {
                        oprates(data);
                    }         //用于ajax返回的数据的操作,回调函数,data为服务器返回数据
                });
            };
        }
    </script>
</head>
<body>
<input type="hidden" value="${algorithmId}" id="algorithmId"/>
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
                <li class="active"><a href="${contextPath}/manager/new"><i class="glyphicon glyphicon-edit"></i><span>主编辑区</span> </a> </li>
                <li><a href="javascript:void(0)"><i class="icon-bar-chart"></i><span>收益概述</span> </a> </li>
                <li class="subnavbar-open-right"><a href="javascript:void(0)"><i class="glyphicon glyphicon-dashboard"></i><span>回测历史</span> </a></li>
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
<!--主编辑区-->
<div class="container">
    <div class="row">
        <div class="col-md-3" id="leftside">
            <div class="panel panel-default">
                <div class="panel-heading">
                    我的策略列表
                </div>
                <div class="panel-body" style="height: 406px; padding: 0px;" id="myalgorithm">
                    <table class="table table-hover">
                        <tbody>
                        <tr style="border: hidden">
                            <th>策略id</th>
                            <th>策略名称</th>
                        </tr>
                        </tbody>
                    </table>
                    <div style="position: absolute;left:10%;bottom: 30px;">
                        <div style="margin: 0 auto;width:260px">
                            <div class="page"></div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <div class="col-md-9" id="editpanel">
            <div class="panel panel-default">
                <div class="panel-heading" style="padding: 3px 15px;height: 42px;">
                        <div class="row">
                            <div class="col-xs-6">
                                <a href="#" style="text-decoration: none;color:#383838;">
                                    <i class="icon-fullscreen" onclick="allscreen();" id="screen-set"></i>
                                </a>
                                <c:if test="${pageflag=='new'}">
                                    <h5 class="algo-title" title="点击修改策略名称" style="text-transform:none">请编辑策略名称</h5>
                                </c:if>
                                <c:if test="${pageflag=='edit'}">
                                    <h5 class="algo-title" title="点击修改策略名称" style="text-transform:none">${data.result[0].algorithm_name}</h5>
                                </c:if>
                                <input class="algo-title-box medium hidden" id="title-box" size="30" type="text">
                            </div>
                            <div class="col-xs-6 text-right" id="editbtn">
                                <c:if test="${pageflag=='new'}">
                                    <a class="btn btn-default" onclick="submitAdd()" id="submitSave">
                                        保存
                                    </a>
                                </c:if>
                                <c:if test="${pageflag=='edit'}">
                                    <a class="btn btn-default" onclick="submitSave()" id="submitSave">
                                        修改
                                    </a>
                                </c:if>

                                <a class="btn btn-default" id="submitRun">
                                    <span class="icon-play"></span> RUN
                                </a>
                            </div>
                        </div>
                </div>
                <div class="panel-body" style="height: 405px;padding:0px">
                    <!--代码输入框（注意请务必设置高度，否则无法显示）-->
                    <pre id="code" class="ace_editor"><textarea class="ace_text-input">#请输入您的代码</textarea>
                    </pre>
                </div>
            </div>
        </div>
    </div>
</div>
<!--运行结果区域-->
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    运行控制台
                </div>
                <div class="panel-body">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#log" data-toggle="tab" aria-expanded="true">日志</a>
                        </li>
                        <li class=""><a href="#errorlog" data-toggle="tab" aria-expanded="false">错误</a>
                        </li>
                        <li class=""><a href="#backtest" data-toggle="tab" aria-expanded="false">回测</a>
                        </li>
                        <li class=""><a href="#tradelist" data-toggle="tab" aria-expanded="false">交易</a>
                        </li>
                        <li class=""><a href="#holdlist" data-toggle="tab" aria-expanded="false">持仓</a>
                        </li>
                        <li class=""><a href="#chartsarea" data-toggle="tab" aria-expanded="false">图表</a>
                        </li>
                    </ul>

                    <div class="tab-content">
                        <div class="tab-pane fade active in" id="log">
                            <div class="less-container">
                                <div>******************************** log is monitoring **********************************
                                </div>
                            </div>
                            <div class="less-status-bar">
                                <div style="height: 18px;">
                                    <span class="ctrl">
                                        <input id="tail-reload-btn" type="button" class="button" value="刷 新">
                                        <input id="tail-clear-btn" type="button" class="button" value="清 空">
                                        <input id="tail-stop-btn" type="button" class="button" value="开 始">
                                        <input id="tail-select-btn" type="button" class="button" value="全 选">
                                        <input id="tail-find-btn" type="button" class="button" value="过 滤">
                                    </span>
                                    <span class="pad4"><input id="tail-reload-interval" type="hidden" class="text w30" value="1"></span>
                                    <span class="pad4"><input id="tail-auto-scroll" type="checkbox" class="checkbox" checked="true">自动滚动</span>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="errorlog">
                            <div class="less-container">
                                <div>******************************** log is monitoring **********************************
                                </div>
                            </div>
                            <div class="less-status-bar">
                                <div style="height: 18px;">
                                    <span class="ctrl">
                                        <input  type="button" class="button" value="刷 新">
                                        <input  type="button" class="button" value="清 空">
                                        <input  type="button" class="button" value="开 始">
                                        <input  type="button" class="button" value="全 选">
                                        <input  type="button" class="button" value="过 滤">
                                    </span>
                                    <span class="pad4"><input  type="hidden" class="text w30" value="1"></span>
                                    <span class="pad4"><input  type="checkbox" class="checkbox" checked="true">自动滚动</span>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="backtest">
                            <div class="row">
                                <div class="col-md-12">
                                    <!--   Kitchen Sink -->
                                    <div class="table-responsive">
                                        <table class="table table-striped table-bordered table-hover">
                                            <thead>
                                            <tr>
                                                <th>日期</th>
                                                <th>alpha</th>
                                                <th>信息比率</th>
                                                <th>夏普比率</th>
                                                <th>回测收益</th>
                                                <th>beta</th>
                                                <th>波动率</th>
                                                <th>最大回撤</th>
                                                <th>基准收益</th>
                                                <th>总盈亏</th>
                                                <th>可用权益</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            </tbody>
                                        </table>
                                    </div>
                                    <!-- End  Kitchen Sink -->
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="tradelist">
                            <div class="row">
                                <div class="col-md-12">
                                    <!--   Kitchen Sink -->
                                    <div class="table-responsive">
                                        <table class="table table-striped table-bordered table-hover">
                                            <thead>
                                            <tr>
                                                <th>日期</th>
                                                <th>股票代码</th>
                                                <th>多空</th>
                                                <th>多卖</th>
                                                <th>报单类型</th>
                                                <th>数量</th>
                                                <th>价格</th>
                                                <th>平仓盈亏</th>
                                                <th>操作类型</th>
                                                <th>手续费佣金</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            </tbody>
                                        </table>
                                    </div>
                                    <!-- End  Kitchen Sink -->
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="holdlist">
                            <div class="row">
                                <div class="col-md-12">
                                    <!--   Kitchen Sink -->
                                    <div class="table-responsive">
                                        <table class="table table-striped table-bordered table-hover">
                                            <thead>
                                            <tr>
                                                <th>日期</th>
                                                <th>股票代码</th>
                                                <th>多空</th>
                                                <th>开仓均价</th>
                                                <th>持仓盈亏</th>
                                                <th>数量</th>
                                                <th>收盘价/结算价</th>

                                            </tr>
                                            </thead>
                                            <tbody>
                                            </tbody>
                                        </table>
                                    </div>
                                    <!-- End  Kitchen Sink -->
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="chartsarea">
                            <div class="row">
                                <div class="col-md-2">
                                    <div class="panel panel-default" style="height: 500px;margin-bottom: 1px;border: none" id="main2">
                                        <div class="sm-st clearfix">
                                            <div class="sm-st-info">
                                                <span>-- --</span>
                                                策略收益
                                            </div>
                                        </div>
                                        <div class="sm-st clearfix">
                                            <div class="sm-st-info">
                                                <span>-- --</span>
                                                策略年化收益
                                            </div>
                                        </div>
                                        <div class="sm-st clearfix">
                                            <div class="sm-st-info">
                                                <span>--  --</span>
                                                基准收益
                                            </div>
                                        </div>
                                        <div class="sm-st clearfix">
                                            <div class="sm-st-info">
                                                <span>-- --</span>
                                                Alpha
                                            </div>
                                        </div>
                                        <div class="sm-st clearfix">
                                            <div class="sm-st-info">
                                                <span>-- --</span>
                                                Beta
                                            </div>
                                        </div>
                                        <div class="sm-st clearfix">
                                            <div class="sm-st-info">
                                                <span>-- --</span>
                                                最大回撤
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-10">
                                    <div class="panel panel-default" style="height: 500px;margin-bottom: 1px;" id="main">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--导入js库-->
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js" ></script>
<script>
    //初始化对象
    editor = ace.edit("code");

    //设置风格和语言（更多风格和语言，请到github上相应目录查看）
    theme = "monokai"  //monokai clouds
    language = "python"  //python 语言可以设置为 python  c_cpp
    editor.setTheme("ace/theme/" + theme);
    editor.session.setMode("ace/mode/" + language);

    //字体大小
    editor.setFontSize(14);

    //设置只读（true时只读，用于展示代码）
    editor.setReadOnly(false);

    //自动换行,设置为off关闭
    editor.setOption("wrap", "off");

    //启用提示菜单
    ace.require("ace/ext/language_tools");
    editor.setOptions({
        enableBasicAutocompletion: true,
        enableSnippets: true,
        enableLiveAutocompletion: true
    });
    //设置值 获取值
    var datas;
    var algorithm;
    if(${data}){
        datas = ${data};
        algorithm = datas.result[0].algorithm;
        editor.setValue(algorithm);
    }
    editor.focus();  //获取焦点
    //把焦点移到内容的最后面
    let session = editor.getSession();
    let count = session.getLength();
    editor.gotoLine(count, session.getLine(count - 1).length,true);
    //editor.getValue(); // or session.getValue

</script>

</body>
</html>
