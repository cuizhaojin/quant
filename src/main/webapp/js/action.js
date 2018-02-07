//修改回测
function submitSave(){
    layer.msg('确定修改?', {
        time: 0 //不自动关闭
        ,btn: ['嗯呐', '返回']
        ,yes: function(index){
            submitSaveAction();
            layer.close(index);
        }
    });
}

function submitSaveAction(){
    var python_code = editor.getValue();
    var algorithmName= $(".algo-title").html();
    if(python_code==undefined || python_code=="" || python_code==null){
        layer.msg('策略代码为空');
        return;
    }
    var rootPath = "http://localhost:8089/quant"
    var algorithmId = $("#algorithmId").val();
    $.ajax({
        url: rootPath + '/manager/modifyAlgorithm',
        async: true,
        type: 'POST',
        data: {
            "userId":"123456",
            "algorithmName": algorithmName,
            "algorithmId":algorithmId,
            "code": python_code
        },
        dataType: 'json',
        success: function (data) {
            if (data.result == 1) {
                console.info(data);
                layer.msg('修改策略成功');
            } else if (data.result == 0||data.result == 2) {
                console.info(data);
                layer.msg('修改策略失败');
            }
        },
        error: function () {
            layer.msg('修改策略失败');
        }

    });
}
//添加回测
function submitAdd(){
    layer.msg('确定添加?', {
        time: 0 //不自动关闭
        ,btn: ['嗯呐', '返回']
        ,yes: function(index){
            submitAddAction();
            layer.close(index);
        }
    });
}
function submitAddAction(){
    var python_code = editor.getValue();
    var algorithmName= $(".algo-title").html();
    var rootPath = "http://localhost:8089/quant";
    if(python_code==undefined || python_code=="" || python_code==null){
        layer.msg('策略代码为空');
        return;
    }
    $.ajax({
        url: rootPath + '/manager/addAlgorithm',
        async: true,
        type: 'POST',
        data: {
            "userId":"123456",
            "algorithmName": algorithmName,
            "code": python_code
        },
        dataType: 'json',
        success: function (data) {
            if (data.result == 1) {
                console.info(data);
                layer.msg('添加策略成功');
                //修改 添加按钮 -> 修改按钮
                $("#editbtn a").eq(0).remove();
                var html = "<a class='btn btn-default'onclick='submitSave()' id='submitSave'> 修改</a>";
                $("#editbtn").prepend(html);
                $(window).attr('location',rootPath+"/manager/edit/"+data.algorithm_id);
            } else if (data.result == 0||data.result == 2) {
                console.info(data);
                layer.msg('添加策略失败');
            }
        },
        error: function () {
            layer.msg('添加策略失败');
        }
    });
}
//运行回测
function submitRun(){
    layer.msg('确定运行吗?', {
        time: 0 //不自动关闭
        ,btn: ['嗯呐', '返回']
        ,yes: function(index){
            submitRunAction();
            layer.close(index);
        }
    });
}
function submitRunAction(){

    var python_code = editor.getValue();
    if(python_code==undefined || python_code=="" || python_code==null){
        layer.msg('策略代码为空');
        return;
    }
    var rootPath = "http://localhost:8089/quant";
    var algorithmId = $("#algorithmId").val();
    $.ajax({
        url: rootPath + '/manager/executeAlgorithm',
        async: true,
        type: 'POST',
        data: {
            "userId":"123456",
            "algorithmId": algorithmId,
            "code": python_code
        },
        dataType: 'json',
        success: function (data) {
            if (data.result == 1) {
                console.info(data);
                //清空控制台
                $("#log .less-container").empty();
                $("#errorlog .less-container").empty();
                $("#backtest tbody").empty();
                $("#tradelist tbody").empty();
                $("#holdlist tbody").empty();
                layer.msg('执行策略成功');
            } else if (data.result == 0||data.result == 2) {
                console.info(data);
                layer.msg('执行策略失败');
            }
        },
        error: function () {
            layer.msg('执行策略失败');
        }
    });
}

//全屏事件
function allscreen() {
    if ($("#editpanel").attr("class") == 'col-md-9') {
        $("#leftside").css("display", "none");
        $("#editpanel").removeClass("col-md-9").addClass("col-md-12");
        $("#screen-set").removeClass("icon-fullscreen").addClass("icon-minus");
    } else {
        $("#leftside").css("display", "block");
        $("#editpanel").removeClass("col-md-12").addClass("col-md-9");
        $("#screen-set").removeClass("icon-minus").addClass("icon-fullscreen");
    }
}
function getByteLen(e) {
    for (var t = 0, a = 0; a < e.length; a++)null != e.charAt(a).match(/[^\x00-\xff]/gi) ? t += 2 : t += 1;
    return t
}

$(document).ready(function(){

    //修改策略名称
    $(".algo-title").click(function (e) {
        e.stopPropagation(),
        $(this).addClass("hidden"),
        $(".algo-title-box").removeClass("hidden").focus().val($(".algo-title").html());
        var t = getByteLen($("#title-box").val());
        $("#title-box").width(8 * t + "px").css({"min-width": 208, "max-width": 1280})
    });

    $("#title-box").keydown(function () {
        var e = getByteLen($("#title-box").val());
        if(e>31){
            layer.msg('请缩短字数到15字');
           return;
        }
        $("#title-box").width(8 * e + "px").css({"min-width": 208, "max-width": 1280})
    });

    $("#title-box").blur(function () {
        var t = getByteLen($("#title-box").val());
        if(t>31){
            layer.msg('请缩短字数到15字');
            return;
        }
        $(".algo-title-box").addClass("hidden");
        var e = this;
        "" != $(this).val() && $(".algo-title").html($(e).val()),
            $(".algo-title").removeClass("hidden");
    }),

    $("#selectAll").click(function () {
        $("#algo_table").find("tbody").find("input[type=checkbox]").prop("checked", this.checked);
        $("#algo_table").find("input[type=checkbox]").trigger("change");
        if ($(this).parent().hasClass("un-selectbox")) {
            $(":checkbox").parent().addClass("selectbox").removeClass("un-selectbox")
        } else {
            if ($(this).parent().hasClass("selectbox")) {
                $(":checkbox").parent().addClass("un-selectbox").removeClass("selectbox")
            }
        }
    });
    $("#algo_table").delegate(".algorithm_list .select-checkbox", "click", function () {
        if ($(this).prop("checked")) {
            $(this).parent().removeClass("un-selectbox").addClass("selectbox").parent("tr").addClass("checked")
        } else {
            $(this).parent().addClass("un-selectbox").removeClass("selectbox").parent("tr").removeClass("checked")
        }
    });

    $("#algo_table").delegate("input[type=checkbox]", "change", function (b) {
        var a = $("#algo_table").find("tbody").find("input[type=checkbox]:checked").length;
        if (a > 0) {
            $("#del-algorithm").removeClass("disabled");
            if (a == 1) {
                $("#rename-algorithm").removeClass("disabled")
            } else {
                $("#rename-algorithm").addClass("disabled")
            }
        } else {
            $("#rename-algorithm").addClass("disabled");
            $("#del-algorithm").addClass("disabled")
        }
    });
    $("#rename-algorithm").click(function (f) {
        f.stopPropagation();
        var d = $("#algo_table").find("tbody").find("input[type=checkbox]:checked");
        var b = d.parents("tr").find(".name").find(".rename_box");
        var c = d.prop("checked");
        var a = $(".algorithm_list.checked").find(".file_name").html();
        if (c) {
            b.removeClass("hidden").find(".rename_input").val(a).attr("autofocus", true).select();
            b.prev().addClass("hidden");
        } else {
            b.addClass("hidden").find(".rename_input").attr("autofocus", false);
            b.prev().removeClass("hidden");
        }
    });
    $("#algo_table").delegate(".rename_ok", "click", function () {
        var f = $(this).parent(".rename_box");
        var b = $(this).parents("tr").find(".select-box");
        var a = b.attr("_fId");
        var e = b.attr("_algorithmId");
        var d = $("#breadcrumb").attr("_fId");
        var c = f.find("input").val();
        if (!a) {
            if (e) {
                Cy.ajax("/algorithm/index/save", {data: {"algorithm[name]": c, "algorithm[algorithmId]": e}, success: function () {
                    f.prev().html(c);
                    f.addClass("hidden");
                    f.prev().removeClass("hidden")
                }})
            } else {
                var g = this;
                Cy.ajax("/algorithm/index/AddFile?name=" + c + "&pId=" + d, {success: function (h) {
                    window.location.href = "/algorithm/index/list?fId=" + d
                }})
            }
        } else {
            Cy.ajax("/algorithm/index/UpdateFile?name=" + c + "&fId=" + a, {success: function (h) {
                f.prev().find(".file_name").html(c);
                f.addClass("hidden");
                f.prev().removeClass("hidden")
            }})
        }
        $("#new-file-button").removeClass("disabled")
    });
    $("#algo_table").delegate(".rename_cancel", "click", function () {
        cancel();
        var a = $(".algorithm_list.checked").find(".select-box").attr("_fId");
        var b = $(".algorithm_list.checked").find(".select-box").attr("_algorithmId");
        if (!a && !b) {
            $(".algorithm_list.checked").remove()
        }
    });
    $("#del-algorithm").click(function () {
         var rootPath = "http://localhost:8089/quant";
         layer.msg('确定删除策略?', {
         time: 0 //不自动关闭
         ,btn: ['OK', '返回']
         ,yes: function(index){
             var e = [];
             $("#algo_table").find("tbody").find("input[type=checkbox]:checked").each(function (f, g) {
                 if ($(g.parentNode).attr("_algorithmId")) {
                     e.push($(g.parentNode).attr("_algorithmId"))
                 }
             });
             var b = e.join(",");
             if (b && e.length==1){
                 console.info(b);
                 $.ajax({
                     url: rootPath + '/manager/deleteAlgorithm',
                     async: true,
                     type: 'POST',
                     data: {
                         "userId":"123456",
                         "algorithmId": b
                     },
                     dataType: 'json',
                     success: function (data) {
                         if (data.result == 1) {
                             console.info(data);
                             layer.msg('删除策略成功');
                             window.location.reload();
                         } else if (data.result == 0||data.result == 2) {
                             console.info(data);
                             layer.msg('删除策略失败');
                         }
                     },
                     error: function () {
                         layer.msg('删除策略失败');
                     }

                 });

             }else{
                 layer.msg('暂时不支持批量删除');
             }
             layer.close(index);
         }
         });

    });
    $("#algo_table").delegate("tr", "click", function (a) {
        a.stopPropagation()
    });
    function cancel() {
        var c = $(".algorithm_list.checked").find(".rename_box");
        c.addClass("hidden");
        c.prev().removeClass("hidden")
    }
});






