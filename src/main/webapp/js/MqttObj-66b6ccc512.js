define(["cookies"],
function(o) {
    function e() {
        u.subscribe(m, M),
        d5.userInfo.login && (g = "zhibo/chat/dm/" + d + "/#", u.subscribe(g, M)),
        ("assistant" == d5.userInfo.identity || "presenter" == d5.userInfo.identity || d5.isAss_teacher) && (h = "zhibo/chat/dm/" + l + "/#", u.subscribe(h, M))
    }
    function t(o) {
        if (_ || 6 == o.errorCode) return void console.log("onConnectFailed:", o.errorMessage);
        0 !== o.errorCode && console.log("onConnectFailed:", o.errorMessage),
        setTimeout(function() {
            u.connect(p),
            console.log("断后重连")
        },
        5e3)
    }
    function n() {
        console.log("MQTT——初始化成功onSubSccess")
    }
    function i(o) {
        0 !== o.errorCode && console.log("onSubFailed:MQTT——初始化失败", o.errorMessage)
    }
    function s(o) {
        if (5 === o.errorCode) return void console.log("onSubFailed:MQTT——链接丢失", o.errorMessage);
        0 !== o.errorCode && setTimeout(function() {
            u.connect(p),
            console.log("断后重连")
        },
        5e3)
    }
    function r(o) {
        var e = null;
        try {
            e = JSON.parse(o.payloadString),
            b[e.type](e)
        } catch(o) {
            console.log("没有此类型！")
        }
    }
    function c(o) {
        var e = $("#openWrap_exit"),
        t = null;
        e.find("p.sys-tips").text(o),
        d5.showBox(e),
        t = $("#dotNav").find("a"),
        t.eq(0).text("登录").attr("data-p", "login"),
        t.eq(1).text("注册").attr({
            href: "https://reg.hexun.com/regname.aspx?gourl=http://zhibo.hexun.com/",
            target: "_blank"
        }),
        e.find("a.re_login").click(function() {
            popupLogin(),
            closeBox("openWrap_exit")
        }),
        e.find("a.et_exit").click(function() {
            location.href = "http://zhibo.hexun.com"
        }),
        setTimeout(function() {
            document.getElementsByTagName("body")[0].removeChild($("#to_exit")[0])
        },
        2e3)
    }
    var u, d = d5.userInfo.userId + "",
    a = d5.userInfo.sessionId,
    l = d5.roomInfo.ownerId,
    f = null;
    d5.isIE ? (f = d5.isTest ? 9090 : 8565, u = new Paranoid945Mqtt("broker.zhibo.hexun.com", f)) : (f = d5.isTest ? 90 : 89, u = new Paho.MQTT.Client("broker.zhibo.hexun.com", f, d));
    var g, h, m = "zhibo/room/" + d5.roomId,
    _ = !1,
    M = {
        qos: 0,
        onSuccess: n,
        onFailure: i
    };
    u.onConnectionLost = s,
    u.onMessageArrived = r;
    var p = {
        useSSL: !1,
        userName: d,
        password: a,
        cleanSession: !0,
        onSuccess: e,
        onFailure: t,
        keepAliveInterval: 60
    };
    u.connect(p);
    var b = {
        t_s: function(o) {
            d5.leftMsg(o),
            d5.newMss()
        },
        t_ssm: function(o) {
            d5.leftMsg(o)
        },
        t_ss: function(o) {
            d5.leftMsg(o)
        },
        t_pumf: function(o) {
            d5.leftMsg(o)
        },
        t_pum: function(o) {
            d5.leftMsg(o)
        },
        u_s: function(o) {
            d5.rightMsg(o)
        },
        u_a: function(o) {
            d5.rightMsg(o)
        },
        t_rp: function(o) {
            d5.rightMsg(o)
        },
        t_ro: function(o) {
            d5.leftMsg(o),
            d5.rightMsg(o)
        },
        t_rol: function(o) {
            d5.leftMsg(o),
            !d5.isAdver && d5.rightMsg(o)
        },
        t_ptrf: function(o) {
            d5.leftMsg(o)
        },
        t_ror: function(o) {
            d5.rightMsg(o)
        },
        u_r: function(o) {
            d5.rightMsg(o)
        },
        cs_s: function(o) {
            d5.rightMsg(o)
        },
        cs_ro: function(o) {
            d5.rightMsg(o)
        },
        ta_ror: function(o) {
            d5.rightMsg(o)
        },
        ta_rol: function(o) {
            d5.leftMsg(o),
            !d5.isAdver && d5.rightMsg(o)
        },
        ta_rp: function(o) {
            d5.rightMsg(o)
        },
        ta_ro: function(o) {
            d5.leftMsg(o),
            d5.rightMsg(o)
        },
        ta_s: function(o) {
            d5.leftMsg(o)
        },
        zc_s: function(o) {
            d5.leftMsg(o)
        },
        zc_rp: function(o) {
            d5.rightMsg(o)
        },
        zc_ro: function(o) {
            d5.leftMsg(o),
            d5.rightMsg(o)
        },
        zc_rol: function(o) {
            d5.leftMsg(o),
            !d5.isAdver && d5.rightMsg(o)
        },
        zc_ror: function(o) {
            d5.rightMsg(o)
        },
        ad_robot_room: function(o) {
            d5.leftMsg(o)
        },
        ad_robot_all: function(o) {
            d5.leftMsg(o)
        },
        room_sys_msg: function(o) {
            d5.rightMsg(o)
        },
        notify_courses: function(o) {
            d5.leftMsg(o)
        },
        notify_sqr: function(o) {
            var e = "teacher" != d5.userInfo.identity && "fv" == $("#topInfo .btn-atten").attr("data-p") ? "名额已满，请关注老师": "",
            t = $("#se_" + o.secretId).find(".voice-classroom-btn");
            t.find(".syme").html(e),
            t.addClass("isfull")
        },
        kick_user: function(o) {
            o.roomId == d5.roomId && ("uid" == o.kick_by ? o.toKickUserId == d && d5.sysWarn(o.body,
            function() {
                setTimeout(function() {
                    location.href = "http://zhibo.hexun.com"
                },
                7e3)
            }) : o.toKickSessionId == a && d5.sysWarn(o.body,
            function() {
                setTimeout(function() {
                    location.href = "http://zhibo.hexun.com"
                },
                7e3)
            }))
        },
        kick_user_multi_account: function(e) {
            "sid" === e.kick_by && e.toKickSessionId == d5.userInfo.sessionId && e.roomId == d5.roomId && ($("#to_exit").attr("src", "http://utility.tool.hexun.com/quit.aspx?gourl=http://imgzq.hexun.com/commonjs/a.html"), o.remove("userToken"), o.remove("hxck_sq_common"), o.remove("LoginStateCookie"), o.remove("SnapCookie"), c(e.body), _ = !0, setTimeout(function() {
                location.reload()
            },
            45e3))
        },
        blacklist_user: function(o) {
            o.toBlackListUserId == d && "system" == o.from && (d5.sysWarn("您已被该直播室拉黑，客服热线：010-8569-7400"), setTimeout(function() {
                window.location.href = "http://zhibo.hexun.com"
            },
            5e3))
        },
        delete_message: function(o) {
            o.toDeleteMessage && "u_a" == o.toDeleteMessage.type &&
            function() {
                var o = Number($("#qaN").text()) - 1;
                o > 0 ? $("#qaN").text(o) : $("#qaN").removeClass("g-news").text("")
            } (),
            d5.delMsg(o.toDeleteMessageId)
        },
        close_room: function(o) {
            d5.sysWarn("该产品已下线，如有疑问请联系客服010-85696800（7400）",
            function() {
                setTimeout(function() {
                    location.href = "http://zhibo.hexun.com"
                },
                2e3)
            })
        },
        silence_user: function(o) {},
        delete_a: function(o) {
            d5.delMsg(o.toDeleteMessageId)
        },
        topic_webinar_start: function(o) {
            return ! 1
        },
        topic_webinar_stop: function(o) {
            return ! 1
        },
        gift: function(o) {
            d5.leftMsg(o)
        },
        sys_wnu: function(o) {
            d5.rightMsg(o)
        },
        hongbao: function(o) {},
        update_room: function(o) {
            switch (o.field) {
            case "announcement":
                o.value = d5.filter(o.value),
                o.value = d5.filterImg(o.value),
                o.type = "announcement",
                d5.leftMsg(o);
                break;
            case "topic":
                o.value = o.value.slice(0, 20),
                o.type = "topic",
                d5.leftMsg(o);
                break;
            case "speak_policy":
                o.type = "speak_policy",
                d5.leftMsg(o);
                break;
            case "online":
                o.value ? $("#online").removeClass("visible") : $("#online").addClass("visible");
                break;
            case "popularity":
            case "webinar_popularity":
                d5.roomThemeControl.find("span.zhibo-newfN").text(o.value)
            }
        }
    }
});