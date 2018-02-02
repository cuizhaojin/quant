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
    <script src="${contextPath}/js/action.js"  type="text/javascript" charset="utf-8"></script>
    <script src="${contextPath}/js/mqtt-action.js"  type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" href="${contextPath}/css/style.css">
    <script>
        $(function () {
            layer.msg('提交失败,请重试');

            /* layer.msg('确定取消关注?', {
             time: 0 //不自动关闭
             ,btn: ['嗯呐', '返回']
             ,yes: function(index){
             layer.close(index);
             }
             });*/
        })
    </script>
</head>
<body>
<!--导航-->
<div id="content-header" style="background-color: #e5e5e5;">
    <div class="container">
        <div id="breadcrumb"> <a href="index.html" class="tip-bottom" data-original-title="Go to Home"><i class="icon-home"></i> Home</a></div>
    </div>
</div>
<!--二级导航-->
<div class="subnavbar">
    <div class="subnavbar-inner">
        <div class="container">
            <ul class="mainnav">
                <li class="active"><a href="${contextPath}/manager/edit/123"><i class="glyphicon glyphicon-edit"></i><span>主编辑区</span> </a> </li>
                <li><a href="${contextPath}/manager/benefit"><i class="icon-bar-chart"></i><span>收益概述</span> </a> </li>
                <li class="subnavbar-open-right"><a href="${contextPath}/manager/testdetail"><i class="glyphicon glyphicon-dashboard"></i><span>性能指标</span> </a></li>
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
                <div class="panel-body" style="height: 406px; padding: 0px;">
                    <table class="table table-hover">
                        <tbody>
                        <tr style="border: hidden">
                            <th>ID</th>
                            <th>User</th>
                            <th>Date</th>
                        </tr>
                        <tr>
                            <td>183</td>
                            <td>John Doe</td>
                            <td>11-7-2014</td>
                        </tr>
                        <tr>
                            <td>219</td>
                            <td>Jane Doe</td>
                            <td>11-7-2014</td>
                        </tr>
                        <tr>
                            <td>657</td>
                            <td>Bob Doe</td>
                            <td>11-7-2014</td>
                        </tr>
                        <tr>
                            <td>175</td>
                            <td>Mike Doe</td>
                            <td>11-7-2014</td>
                        </tr>
                        </tbody>
                    </table>
                    <div style="position: absolute; left: 15%;bottom: 20px;">
                        <ul class="pagination pagination-sm pull-right">
                            <li><a href="#">«</a></li>
                            <li><a href="#">1</a></li>
                            <li><a href="#">2</a></li>
                            <li><a href="#">3</a></li>
                            <li><a href="#">4</a></li>
                            <li><a href="#">5</a></li>
                            <li><a href="#">»</a></li>
                        </ul>
                    </div>
                </div>

            </div>
        </div>
        <div class="col-md-9" id="editpanel">
            <div class="panel panel-default">
                <div class="panel-heading" style="padding: 4px 15px">
                    <form class="form-inline">
                        <div class="row">
                            <div class="col-xs-6" style="padding-top: 7px">
                                <a href="#" style="text-decoration: none;color:#383838;">
                                    <i class="icon-fullscreen" onclick="allscreen();" id="screen-set"></i>
                                </a> 源代码
                            </div>
                            <div class="col-xs-6 text-right">
                                <a class="btn btn-default" onclick="submitSave()" id="submitSave">
                                    保存
                                </a>
                                <a class="btn btn-default" onclick="submitRun()" id="submitRun">
                                    <span class="icon-play"></span> RUN
                                </a>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="panel-body" style="height: 405px;padding:0px">
                    <!--代码输入框（注意请务必设置高度，否则无法显示）-->
                    <pre id="code" class="ace_editor"><textarea class="ace_text-input">
                        #include <cstdio>
                        int main(){
                        int n,m;
                        scanf("%d %d",&n,&m);
                        printf("%d",n+m);
                        return 0;
                        }
                    </textarea>
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
                        <li class=""><a href="#errorlog" data-toggle="tab" aria-expanded="false">回测</a>
                        </li>
                        <li class=""><a href="#errorlog" data-toggle="tab" aria-expanded="false">交易</a>
                        </li>
                        <li class=""><a href="#errorlog" data-toggle="tab" aria-expanded="false">持仓</a>
                        </li>
                    </ul>

                    <div class="tab-content">
                        <div class="tab-pane fade active in" id="log">
                            <div class="less-container">
                                <div>11:25:06.976 [http-nio-8088-exec-9] DEBUG com.skin.finder.servlet.LessServlet(353) - tail.time: 0.140172
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
                                <div>11:25:06.976 [http-nio-8088-exec-9] DEBUG com.skin.finder.servlet.LessServlet(353) - tail.time: 0.140172
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
    var datas = ${data};
    var algorithm = datas.result[0].algorithm;
    editor.setValue(algorithm);
    editor.focus();  //获取焦点
    //把焦点移到内容的最后面
    let session = editor.getSession();
    let count = session.getLength();
    editor.gotoLine(count, session.getLine(count - 1).length,true);
    //editor.getValue(); // or session.getValue

</script>
</body>
</html>
