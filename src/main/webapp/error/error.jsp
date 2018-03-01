<%--
  Created by IntelliJ IDEA.
  User: hexun
  Date: 2016/11/22
  Time: 13:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../commons/taglibs.jsp" %>
<html>
<head>
    <title>服务器出现错误</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="renderer" content="webkit">
    <link rel="shortcut icon" type="image/x-icon" href="${contextPath}/image/favicon.ico" media="screen"/>
    <link rel="stylesheet" href="${contextPath}/css/bootstrap.min.css">
    <link href="${contextPath}/error/css/error.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
</head>
<body>
<!--导航-->
<div id="content-header" style="background-color: #e5e5e5;">
    <div class="container">
        <div id="breadcrumb"> <a href="${contextPath}/manager/list" class="tip-bottom" data-original-title="Go to Home"><i class="icon-home"></i> Home</a></div>
    </div>
</div>
<div class="cont">
    <div class="c1">
        <img class="img1" src="${contextPath}/error/404/01.png" />
    </div>
    <h2>sorry..网页出现错误！</h2>
    <div class="c2">
        <a class="home" href="${contextPath}/manager/list">我的策略</a>
    </div>
    <div class="c3">
        【后台管理】提醒您 - 网站服务器出现错误，请稍后再试
    </div>
</div>
<!--导入bootstrap js库-->
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js" ></script>
</body>
</html>



