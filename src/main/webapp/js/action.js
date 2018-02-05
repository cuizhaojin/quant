function submitSave(){
    var python_code = editor.getValue();
    var rootPath = "http://localhost:8089/quant"
    $.ajax({
        url: rootPath + '/manager/modifyAlgorithm',
        async: true,
        type: 'POST',
        data: {
            "userId":"123456",
            "algorithmName": "万宁",
            "algorithmId":"200354",
            "code": python_code
        },
        dataType: 'json',
        success: function (data) {
            if (data.result == 1) {
                console.info(data);
                layer.msg('修改策略成功');
            } else if (data.result == 0) {
                console.info(data);
                layer.msg('修改策略失败');
            }
        },
        error: function () {
            layer.msg('修改策略失败');
        }

    });
}

function submitAdd(){
    var python_code = editor.getValue();
    var rootPath = "http://localhost:8089/quant";
    $.ajax({
        url: rootPath + '/manager/addAlgorithm',
        async: true,
        type: 'POST',
        data: {
            "userId":"123456",
            "algorithmName": "test_cui",
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
            } else if (data.result == 0) {
                console.info(data);
                layer.msg('添加策略失败');
            }
        },
        error: function () {
            layer.msg('添加策略失败');
        }
    });
}


function submitRun(){
    var python_code = editor.getValue();
    var rootPath = "http://localhost:8089/quant";
    $.ajax({
        url: rootPath + '/manager/executeAlgorithm',
        async: true,
        type: 'POST',
        data: {
            "userId":"123456",
            "algorithmId": "200354",
            "code": python_code
        },
        dataType: 'json',
        success: function (data) {
            if (data.result == 1) {
                console.info(data);
                layer.msg('执行策略成功');
            } else if (data.result == 0) {
                console.info(data);
                layer.msg('执行策略失败');
            }
        },
        error: function () {
            layer.msg('执行策略失败');
        }
    });
}



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
$(document).ready(function(){
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
        BootstrapDialog.confirm("确定要删除?", function (a) {
            if (a) {
                var e = [];
                var c = [];
                $("#algo_table").find("tbody").find("input[type=checkbox]:checked").each(function (f, g) {
                    if ($(g.parentNode).attr("_algorithmId")) {
                        e.push($(g.parentNode).attr("_algorithmId"))
                    } else {
                        c.push($(g.parentNode).attr("_fId"))
                    }
                });
                var b = e.join(",");
                var d = c.join(",");
                if (b) {
                    Cy.ajax("/algorithm/index/del", {data: {algorithmId: b}, success: function (f) {
                        if (f.status == 0) {
                            BootstrapDialog.alert("删除成功", function () {
                                window.location.reload()
                            })
                        } else {
                            BootstrapDialog.alert("删除失败:" + f.msg)
                        }
                    }})
                }
                if (d) {
                    Cy.ajax("/algorithm/index/DelFile?fId=" + d, {success: function (f) {
                        if (f.code == "00000") {
                            BootstrapDialog.alert("删除成功", function () {
                                window.location.reload()
                            })
                        }
                    }})
                }
            } else {
                return
            }
        })
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






