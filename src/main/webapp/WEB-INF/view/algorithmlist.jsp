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
        <div id="breadcrumb"> <a href="index.html" class="tip-bottom" data-original-title="Go to Home"><i class="icon-home"></i> Home</a></div>
    </div>
</div>
<!--二级导航-->
<div class="subnavbar">
    <div class="subnavbar-inner">
        <div class="container">
            <ul class="mainnav">
                <li><a href="${contextPath}/manager/edit/123"><i class="glyphicon glyphicon-edit"></i><span>主编辑区</span> </a> </li>
                <li><a href="${contextPath}/manager/benefit"><i class="icon-bar-chart"></i><span>收益概述</span> </a> </li>
                <li class="subnavbar-open-right"><a href="${contextPath}/manager/testdetail"><i class="glyphicon glyphicon-dashboard"></i><span>性能指标</span> </a></li>
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
        <button type="button" class="btn btn-primary"><i class="icon-plus"></i> 添加策略</button>
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
            <tr class="algorithm_list">
                <td class="select-box un-selectbox" _algorithmid="939195">
                    <input class="select-checkbox" type="checkbox">
                </td>

                <td class="align-left name">
                    <img class="name_img" src="https://cdn.joinquant.com/themes/jq/static/algorithm/img/code_wizard.png" alt="">
                    <a href="/algorithm/index/edit?algorithmId=df7e69e069b14ceec723f6cbf3991eb8" class="black file_name" target="_blank">这是一个简单的策略</a>
                    <div class="rename_box inline-block hidden">
                        <input type="text" value="这是一个简单的策略" class="rename_input" autofocus="autofocus">
                        <button class="rename_ok"></button>
                        <button class="rename_cancel"></button>
                    </div>
                </td>
                <td class="iscode f12">Code</td>
                <td>2018-01-24 13:09:29</td>
                <td>
                    <a class="bold" href="/algorithm/backtest/buildList?algorithmId=75a6e94429af2daea9c98a016f670724" target="_blank">1</a>
                </td>
                <td>
                    <a class="bold" href="/algorithm/backtest/list?algorithmId=8618a21b1a639d148838717482f94a23" target="_blank">3</a>
                </td>
                <td>
                </td>
            </tr>
            <tr class="algorithm_list">
                <td class="select-box un-selectbox" _algorithmid="922883">
                    <input class="select-checkbox" type="checkbox">
                </td>

                <td class="align-left name">
                    <img class="name_img" src="https://cdn.joinquant.com/themes/jq/static/algorithm/img/code_wizard.png" alt="">
                    <a href="/algorithm/index/edit?algorithmId=6494fcb8a9fc254b4f167dc8a90da7eb" class="black file_name" target="_blank">这是一个简单的策略</a>
                    <div class="rename_box hidden inline-block">
                        <input type="text" value="这是一个简单的策略" class="rename_input">
                        <button class="rename_ok"></button>
                        <button class="rename_cancel"></button>
                    </div>
                </td>
                <td class="iscode f12">Code</td>
                <td>2018-01-23 13:46:24</td>
                <td>
                    <a class="bold" href="/algorithm/backtest/buildList?algorithmId=84011d6015045c385ad1a3eb810ece28" target="_blank">2</a>
                </td>
                <td>
                    <a class="bold" href="/algorithm/backtest/list?algorithmId=ee3709332fb6bec13be181fb7fa6d4ae" target="_blank">2</a>
                </td>
                <td>
                </td>
            </tr>
            <tr class="algorithm_list">
                <td class="select-box un-selectbox" _algorithmid="917967">
                    <input class="select-checkbox" type="checkbox">
                </td>

                <td class="align-left name">
                    <img class="name_img" src="https://cdn.joinquant.com/themes/jq/static/algorithm/img/code_wizard.png" alt="">
                    <a href="/algorithm/index/edit?algorithmId=8667530f729297f19d1001ea9a956809" class="black file_name" target="_blank">小市值策略</a>
                    <div class="rename_box hidden inline-block">
                        <input type="text" value="小市值策略" class="rename_input">
                        <button class="rename_ok"></button>
                        <button class="rename_cancel"></button>
                    </div>
                </td>
                <td class="iscode f12">Code</td>
                <td>2018-01-15 21:06:43</td>
                <td>
                    <a class="bold" href="/algorithm/backtest/buildList?algorithmId=5c8c8b37fced5f8808e90e7da4e2a900" target="_blank">6</a>
                </td>
                <td>
                    0
                </td>
                <td>
                </td>
            </tr>
            <tr class="algorithm_list">
                <td class="select-box un-selectbox" _algorithmid="917966">
                    <input class="select-checkbox" type="checkbox">
                </td>

                <td class="align-left name">
                    <img class="name_img" src="https://cdn.joinquant.com/themes/jq/static/algorithm/img/code_wizard.png" alt="">
                    <a href="/algorithm/index/edit?algorithmId=411d18494f47bab3b6e18cd297e87131" class="black file_name" target="_blank">双均线策略</a>
                    <div class="rename_box hidden inline-block">
                        <input type="text" value="双均线策略" class="rename_input">
                        <button class="rename_ok"></button>
                        <button class="rename_cancel"></button>
                    </div>
                </td>
                <td class="iscode f12">Code</td>
                <td>2018-01-15 16:52:52</td>
                <td>
                    <a class="bold" href="/algorithm/backtest/buildList?algorithmId=eccee04074e58f47d9bce88139fea19b" target="_blank">5</a>
                </td>
                <td>
                    <a class="bold" href="/algorithm/backtest/list?algorithmId=dc0171ade15c8d985869f19c52c76cf9" target="_blank">2</a>
                </td>
                <td>
                </td>
            </tr>
            <tr class="algorithm_list">
                <td class="select-box un-selectbox" _algorithmid="917965">
                    <input class="select-checkbox" type="checkbox">
                </td>

                <td class="align-left name">
                    <img class="name_img" src="https://cdn.joinquant.com/themes/jq/static/algorithm/img/code_wizard.png" alt="">
                    <a href="/algorithm/index/edit?algorithmId=df903729e1a0e8cc2a33b738e0ffd767" class="black file_name" target="_blank">银行股轮动策略</a>
                    <div class="rename_box hidden inline-block">
                        <input type="text" value="银行股轮动策略" class="rename_input">
                        <button class="rename_ok"></button>
                        <button class="rename_cancel"></button>
                    </div>
                </td>
                <td class="iscode f12">Code</td>
                <td>2018-01-11 16:19:03</td>
                <td>
                    0
                </td>
                <td>
                    0
                </td>
                <td>
                </td>
            </tr>
            <tr class="algorithm_list">
                <td class="select-box un-selectbox" _algorithmid="917964">
                    <input class="select-checkbox" type="checkbox">
                </td>

                <td class="align-left name">
                    <img class="name_img" src="https://cdn.joinquant.com/themes/jq/static/algorithm/img/code_wizard.png" alt="">
                    <a href="/algorithm/index/edit?algorithmId=9300a1cff15274ee5a34658189d71d62" class="black file_name" target="_blank">低估价值选股策略</a>
                    <div class="rename_box hidden inline-block">
                        <input type="text" value="低估价值选股策略" class="rename_input">
                        <button class="rename_ok"></button>
                        <button class="rename_cancel"></button>
                    </div>
                </td>
                <td class="iscode f12">Code</td>
                <td>2018-01-13 17:00:30</td>
                <td>
                    0
                </td>
                <td>
                    0
                </td>
                <td>
                </td>
            </tr>
            <tr class="algorithm_list">
                <td class="select-box un-selectbox" _algorithmid="917963">
                    <input class="select-checkbox" type="checkbox">
                </td>

                <td class="align-left name">
                    <img class="name_img" src="https://cdn.joinquant.com/themes/jq/static/algorithm/img/code_wizard.png" alt="">
                    <a href="/algorithm/index/edit?algorithmId=7e8ec1c811774952033333caa9285275" class="black file_name" target="_blank">Dual_Thrust策略—股指期货</a>
                    <div class="rename_box hidden inline-block">
                        <input type="text" value="Dual_Thrust策略—股指期货" class="rename_input">
                        <button class="rename_ok"></button>
                        <button class="rename_cancel"></button>
                    </div>
                </td>
                <td class="iscode f12">Code</td>
                <td>2018-01-11 16:19:03</td>
                <td>
                    0
                </td>
                <td>
                    0
                </td>
                <td>
                </td>
            </tr>
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