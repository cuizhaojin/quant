function allscreen() {
    if ($("#editpanel").attr("class") == 'col-md-9') {
        $("#leftside").css("display", "none");
        $("#editpanel").removeClass("col-md-9").addClass("col-md-12");
        $("#screen-set").removeClass("icon-fullscreen").addClass("icon-minus");
    }else{
        $("#leftside").css("display", "block");
        $("#editpanel").removeClass("col-md-12").addClass("col-md-9");
        $("#screen-set").removeClass("icon-minus").addClass("icon-fullscreen");
    }
}
