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
    <title>我的策略</title>
    <link rel="shortcut icon" type="image/x-icon" href="${contextPath}/image/favicon.ico" media="screen">
    <link rel="stylesheet" href="${contextPath}/css/bootstrap.min.css">
    <link href="${contextPath}/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="${contextPath}/js/action.js"  type="text/javascript" charset="utf-8"></script>
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
                <li><a href="javascript:void(0)"><i class="icon-bar-chart"></i><span>收益概述</span> </a> </li>
                <li class="subnavbar-open-right"><a href="javascript:void(0)"><i class="glyphicon glyphicon-dashboard"></i><span>回测历史</span> </a></li>
                <li class="active"><a href="${contextPath}/manager/list"><i class="glyphicon glyphicon-list-alt"></i><span>我的策略</span> </a> </li>
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
<!--策略列表-->
<div class="container">
<div class="row">
    <div class="col-md-12">
        <button type="button" class="btn btn-primary" onclick="location='/quant/manager/new';"><i class="icon-plus"></i> 添加策略</button>
        <button type="button" class="btn btn-default disabled" id="rename-algorithm" style="margin-left: 20px;">重命名</button>
        <button type="button" class="btn btn-default disabled" id="del-algorithm"><i class="glyphicon glyphicon-trash"></i> 删除</button>
    </div>
    <div class="col-md-12">
        <table id="algo_table" delete-button-id="algo-delete-button" class="table checkbox-table checkbox-done table-hover">
            <thead>
            <tr>
                <td class="un-selectbox">
                    <input type="checkbox" class="select-checkbox" id="selectAll">
                </td>
                <td class="align-left">名称</td>
                <td>分类</td>
                <td>最后修改时间</td>
                <td>历史编译运行</td>
                <td>历史回测</td>
                <td></td>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="algorithmMain" items="${algorithmList.result}">
                <tr class="algorithm_list">
                    <td class="select-box un-selectbox" _algorithmid="${algorithmMain.algorithm_id}">
                        <input class="select-checkbox" type="checkbox">
                    </td>

                    <td class="align-left name">
                        <img class="name_img" src="${contextPath}/image/code_wizard.png" alt="">
                        <a href="/quant/manager/edit/${algorithmMain.algorithm_id}" class="black file_name" target="_blank">${algorithmMain.algorithm_name}</a>
                        <div class="rename_box inline-block hidden">
                            <input type="text" value="这是一个简单的策略" class="rename_input" autofocus="autofocus">
                            <button class="rename_ok"></button>
                            <button class="rename_cancel"></button>
                        </div>
                    </td>
                    <td class="iscode f12">Code</td>
                    <td>
                        <fmt:parseDate value="${fn:substring(algorithmMain.update_time,0,14)}" var="update_time" pattern="yyyyMMddHHmmss"/>
                        <fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${update_time}"/></td>
                    <td>
                        <a class="bold"  target="_blank">1</a>
                    </td>
                    <td>
                        <a class="bold"  target="_blank">3</a>
                    </td>
                    <td>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</div>
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js" ></script>
<script type="text/javascript" src="${contextPath}/plugin/layer/layer.js"></script>
<script>
    $(function () {
        //layer.msg('提交失败,请重试');

        /*layer.msg('确定取消关注?', {
         time: 0 //不自动关闭
         ,btn: ['嗯呐', '返回']
         ,yes: function(index){
         layer.close(index);
         }
         });*/
    })
</script>
</body>
</html>