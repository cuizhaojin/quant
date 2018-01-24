"use strict";
var hcodeOptions = {},
    hcode = null,
    cdns = {
        idMap: null,
        singleMap: null
    },
    app = new Vue(
        {
        el: "#app",
        data: {
            base: {
                version: "2.1.5",
                staticBase: staticBase,
                type: 0,
                menuOpen: !0
            },
            doctypes: doctypes,
            code: {
                code: null,
                name: null,
                version: null,
                doctype: 0,
                meta: null,
                description: null,
                group: null,
                newGroup: null,
                author: null,
                saveLayout: !1,
                saveFold: !1,
                autoFormat: !0,
                publicTemplates: [],
                myTemplates: [],
                selectedCdns: [],
                info: null
            },
            loginObj: {
                user: null,
                username: null,
                password: null,
                code: null,
                showCode: null,
                info: null
            },
            isMine: !1,
            myGroups: null,
            newGroup: {
                name: null,
                url: null,
                info: null
            },
            layouts: {},
            newLibMessage: null,
            newLib: null,
            libs: [],
            libSort: 0,
            cdnDomain: "https://img.hcharts.cn/",
            cdns: null,
            singleCheckCdns: {}
        },
        methods: {
            run: function() {
                hcode.run()
            },
            updateWithName: function() {
                return null === this.code.name || "" === this.code.name ? (this.code.info = "请正确填写代码的名字！", !1) : void this.update(!1, !0)
            },
            update: function(e, n) {
                if (e || (e = !1), !this.loginObj.user) return toastr.warning("请先登录再进行操作！"),
                    this.$refs.login.login(),
                    !1;
                if (!this.code.name) return $("#fork-modal").modal(),
                    !1;
                if (!this.myGroups) return toastr.warning("请创建一个默认代码分组！"),
                    this.createGroup(),
                    !1;
                var t = {
                        code: this.code.code,
                        name: this.code.name,
                        version: this.code.version,
                        description: this.code.description,
                        html: null,
                        css: null,
                        javascript: null,
                        libs: [],
                        options: {
                            editor: {
                                saveLayout: this.code.saveLayout,
                                saveFold: this.code.saveFold,
                                autoFormat: this.code.autoFormat
                            },
                            hcode: {
                                layoutId: hcode.layoutIndex
                            }
                        },
                        resource: [],
                        doctype: this.code.doctype,
                        meta: this.code.meta,
                        group: null
                    },
                    o = this;
                if (t.group = this.code.newGroup, this.code.saveLayout && (t.options.hcode.layoutOptions = hcode.getLayoutConfig()), this.code.saveFold) {
                    var s = hcode.getFolds();
                    s && (t.options.hcode.folds = s)
                }
                t.options = JSON.stringify(t.options);
                var i = _.sortBy(this.libs,
                    function(e) {
                        return e.sort
                    });
                _.each(i,
                    function(e) {
                        t.resource.push(e.isInner && e.id ? e.id: e.url)
                    }),
                    t.resource = t.resource.join();
                for (var r in hcode.planes) {
                    var u = hcode.planes[r];
                    u.mode && !u.readonly && (t[r] = _.escape(u.obj.getValue()))
                }
                return "" === t.html && "" === t.javascript ? (toastr.warning("HTML 和 JS 代码不能同时为空！"), !1) : (t.isFork = e, void post("/my/save", t,
                    function(e) {
                        return 1 === e.code ? (o.base.type = 1, o.code.code = e.result.code, o.code.version = e.result.version, o.code.group = {
                            id: e.result.groupId,
                            code: e.result.groupCode,
                            name: e.result.groupName
                        },
                            o.code.author = {
                                id: e.result.authId,
                                name: e.result.authName
                            },
                            window.history.pushState({},
                                0, document.location.protocol + "//" + window.location.host + "/" + o.code.group.code + "/" + o.code.code + (0 === o.code.version ? "": "/" + o.code.version) + (window.location.search || "")), document.title = o.code.name + " | HCODE", $(".share-qrcode").html("<span>手机扫码预览</span>"), n && $("#fork-modal").modal("hide"), toastr.success("代码保存成功！"), hcode.markClean()) : toastr.warning(e.msg),
                            !1
                    },
                    !0))
            },
            login: function() {
                this.$refs.login.loginRequest()
            },
            needLogin: function() {
                return null === this.loginObj.user && (toastr.warning("请先登录再进行操作！"), this.$refs.login.login(), !0)
            },
            createGroup: function() {
                return ! this.needLogin() && void $("#group-modal").modal()
            },
            getMyGroup: function() {
                var e = this;
                post("/my/groups", {},
                    function(n) {
                        e.dealGroup(n.groups, !0)
                    })
            },
            dealGroup: function(e, n) {
                if (0 === e.length) return ! 1;
                var t = null,
                    o = [],
                    s = this;
                if (n ? (this.myGroups = _.groupBy(e,
                    function(e) {
                        return 1 === e["default"] && (t = e.id, e.name += " (默认)"),
                            3 === e.role && o.push(e.id),
                            e.roleStr
                    }), o.length > 0 && null === t && (t = o[0])) : _.each(e,
                    function(e) {
                        1 === e["default"] && (e.name += " (默认)", t = e.id),
                            s.myGroups && s.myGroups[e.roleStr] || (s.myGroups || (s.myGroups = {}), s.myGroups[e.roleStr] = []),
                            s.myGroups[e.roleStr].push(e)
                    }), n || null !== t || (t = this.code.newGroup), this.code.group) for (var i in this.myGroups) {
                    var r = _.find(this.myGroups[i],
                        function(e) {
                            return e.id === s.code.group.id
                        });
                    if (r) return this.code.newGroup = this.code.group.id,
                        !1
                }
                this.code.newGroup = t
            },
            saveGroup: function() {
                var e = /^[A-Za-z0-9]+[a-zA-Z\d\.\_\-]+[A-Za-z0-9]$/,
                    n = this;
                if (this.newGroup.name && this.newGroup.url) {
                    if (this.newGroup.name.length > 20) return this.newGroup.info = "分组名称长度不能超过20个字符",
                        !1;
                    if (this.newGroup.url.length < 5 || this.newGroup.url.length > 20 || !e.test(this.newGroup.url)) return this.newGroup.info = "个性域名不符合规范",
                        !1;
                    n.newGroup.info = "操作中，请稍后...",
                        post("/my/newGroup", {
                                name: n.newGroup.name,
                                code: n.newGroup.url
                            },
                            function(e) {
                                if (1 === e.code) {
                                    n.newGroup.info = "新增分组成功";
                                    var t = [];
                                    t.push(e.groups),
                                        n.dealGroup(t, !1),
                                        setTimeout(function() {
                                                $("#group-modal").modal("hide"),
                                                    n.newGroup = {
                                                        name: null,
                                                        url: null,
                                                        info: null
                                                    }
                                            },
                                            1e3)
                                } else n.newGroup.info = e.msg
                            })
                } else this.newGroup.info = "请填写完整信息"
            },
            fork: function() {
                return $("#fork-modal").modal(),
                    !1
            },
            commonMethod: function() {
                function e(e) {
                    return ! 1
                }
                function n(e) {
                    e = e || window.event;
                    var n = hcode.isSaved(),
                        t = (e.clientX > document.body.clientWidth && e.clientY < 0 || e.altKey, "关闭");
                    return n ? (e.preventDefault(), null) : (e && (e.returnValue = t), t)
                }
                $("header").delegate(".tip-content", "click",
                    function(e) {
                        return "A" === e.target.nodeName && 2 === e.target.attributes.length || ("INPUT" === e.target.nodeName || "LABEL" === e.target.nodeName) && (e.stopPropagation(), !0)
                    }),
                    $("header").delegate(".share-tip input, .share-tip textarea", "click",
                        function() {
                            $(this).select()
                        }),
                    $(".menu-title").click(function() {
                        var e = $(this).parent();
                        return ! e.hasClass("active") && ($("#menu .active .menu-content").slideUp(), $("#menu .active").removeClass("active"), e.addClass("active"), void e.find(".menu-content").slideDown())
                    }),
                    this.shortkey(),
                    this.$refs.login.checkLogin();
                var t = $('textarea.code[data-type="options"]').val(),
                    o = [],
                    s = 0;
                if (t) {
                    t = JSON.parse(t);
                    var i = t.options ? JSON.parse(t.options) : null,
                        r = t.code;
                    i && (r = _.assign(r, i.editor), hcodeOptions = i.hcode, s = hcodeOptions.layoutId),
                        this.dealResources(t.resource),
                        r && (this.code = $.extend(this.code, r), this.base.type = void 0 !== t.type ? t.type: 1),
                        t.templates && (this.code.publicTemplates = t.templates)
                }
                _.each(layoutOptions,
                    function(e, n) {
                        o.push({
                            id: n,
                            icon: e.icon,
                            deg: e.deg,
                            sort: e.sort
                        })
                    }),
                    this.layouts = {
                        current: s,
                        options: _.sortBy(o,
                            function(e) {
                                return e.sort
                            })
                    };
                var u = this;
                $(".menu-switch").click(function() {
                    u.menu()
                }),
                    window.onbeforeunload = n,
                    window.onunload = e
            },
            shortkey: function() {
                var e = this;
                $(document).keydown(function(n) {
                    var t = !1;
                    if (n && (!n.ctrlKey || 13 !== n.keyCode && 10 !== n.keyCode ? !n.ctrlKey || 37 !== n.keyCode && 39 !== n.keyCode ? n.ctrlKey && 90 === n.keyCode ? hcode.isFocused() || (hcode.undo(), t = !0) : n.ctrlKey && 85 === n.keyCode ? hcode.isFocused() || (hcode.redo(), t = !0) : 122 === n.keyCode ? hcode.isFocused() || (hcode.fullPage("result"), t = !0) : n.ctrlKey && 79 === n.keyCode && (t = !0, e.showCdns()) : hcode.isFocused() || (e.menu(39 === n.keyCode), t = !0) : hcode.isFocused() || (hcode.run(), t = !0), 27 === n.keyCode && $("#cdn-modal").modal("hide"), t)) return n.preventDefault(),
                        !1
                })
            },
            menu: function(e) {
                return void 0 === e && (e = $("#menu").hasClass("toggle")),
                    this.menuOpen !== e && (e ? ($(".menu-switch").html('<i class="fa fa-caret-square-o-left" aria-hidden="true" title="折叠菜单"></i>'), $("#menu").removeClass("toggle"), $("#editor").css("left", 220)) : ($(".menu-switch").html('<i class="fa fa-caret-square-o-right" aria-hidden="true"></i> <span>展开菜单</span>'), $("#menu").addClass("toggle"), $("#editor").css("left", 28)), hcode.refresh(), void(this.menuOpen = e))
            },
            dealResources: function(e) {
                if (!e) return ! 1;
                for (var n = e.split(","), t = null, o = null, s = [], i = [], r = n.length, u = -1, d = 0, l = 0; l < r; l++) if (t = n[l], o = t.indexOf(";"), o !== -1) {
                    var a = t.substring(0, o),
                        c = parseInt(t.substring(o + 1, t.length));
                    c > u && (u = c),
                        isNumber(a) && (i.push(a), a = parseInt(a)),
                        s.push([a, c])
                } else isNumber(t) && (t = parseInt(t), i.push(t + "")),
                    s.push([t, d++]);
                i.length > 0 && (this.code.selectedCdns = i),
                    this.addInitLibs(s)
            },
            addInitLibs: function(e) {
                var n = this;
                this.loadCdns(function() {
                    _.each(e,
                        function(e) {
                            var t = !1,
                                o = null;
                            isNumber(e[0]) && (o = e[0], e[0] = cdns.idMap[e[0]].url, t = !0),
                                n.addLib(e[0], e[1], t, o)
                        }),
                        n.code.selectedCdns.length > 0 && _.each(cdns.singleMap,
                        function(e, t) {
                            var o = _.find(e,
                                function(e) {
                                    return _.indexOf(n.code.selectedCdns, e) !== -1
                                });
                            o && (n.singleCheckCdns[t] = o)
                        })
                })
            },
            addLib: function(e, n, t, o) {
                var s = this;
                if ("string" == typeof e && (this.newLib = e), this.newLib) {
                    var i = new RegExp;
                    if (i.compile("^([A-Za-z]+:)?//[A-Za-z0-9-_]+\\.[A-Za-z0-9-_%&?/.=]+$"), i.test(this.newLib)) {
                        var r = _.find(this.libs,
                            function(e) {
                                return e.url === s.newLib
                            });
                        if (r) return this.newLibMessage = "资源 URL 已经存在",
                            !1;
                        var u = s.newLib.split("."),
                            d = u[u.length - 1];
                        if ("css" !== d && "js" !== d) return this.newLibMessage = "目前只支持 .js 和 .css 文件",
                            !1;
                        var l = s.newLib.split("/"),
                            a = l[l.length - 1],
                            c = a;
                        a.length > 24 && (c = a.substring(0, 20) + "..."),
                            this.libs.push({
                                id: o,
                                showName: c,
                                name: a,
                                url: s.newLib,
                                isInner: t || !1,
                                sort: void 0 === n ? s.libSort: n
                            }),
                            s.libSort++,
                            this.newLib = null,
                            this.newLibMessage = null
                    } else this.newLibMessage = "请输入正确的资源地址"
                } else this.newLibMessage = "请输入正确的资源地址"
            },
            sortLib: function e(n, t) {
                var o = n.sort,
                    s = 0,
                    i = null,
                    e = [];
                return ! (t && 0 === o || !t && o === this.libSort - 1) && (e = _.sortBy(this.libs,
                    function(e) {
                        return e.sort
                    }), s = _.findIndex(e,
                    function(e) {
                        return e.url === n.url
                    }), i = e[t ? s - 1 : s + 1], s = i.sort, i.sort = o, void(n.sort = s))
            },
            delLib: function(e) {
                if (confirm("确定要删除吗？")) {
                    e.isInner && (this.code.selectedCdns = _.filter(this.code.selectedCdns,
                        function(n) {
                            return n != e.id
                        }));
                    var n = _.sortBy(this.libs,
                            function(e) {
                                return e.sort
                            }),
                        t = -1;
                    _.each(n,
                        function(n, o) {
                            n.id === e.id ? t = o: t >= 0 && o > t && n.sort--
                        }),
                        this.libSort--,
                        this.libs = _.filter(this.libs,
                            function(n) {
                                return n.name !== e.name
                            })
                }
            },
            showCdns: function(e) {
                return e === !0 ? ($("#cdn-modal").modal("hide"), !1) : (null === this.cdns && this.loadCdns(), void $("#cdn-modal").modal({
                    keyboard: !0
                }))
            },
            cdnCheck: function(e, n, t) {
                if (e.radio) {
                    if (t += "", this.singleCheckCdns && this.singleCheckCdns[n]) {
                        if (this.singleCheckCdns[n] === t) return delete this.singleCheckCdns[n],
                            !1;
                        var o = this;
                        this.code.selectedCdns = _.filter(this.code.selectedCdns,
                            function(e) {
                                return e !== o.singleCheckCdns[n]
                            }),
                            this.code.selectedCdns.push(t)
                    }
                    this.singleCheckCdns[n] = t
                }
            },
            loadCdns: function(e) {
                var n = this;
                $.getJSON(this.base.staticBase + "scripts/cdns.json",
                    function(t) {
                        n.cdns = t;
                        var o = {},
                            s = {},
                            i = [];
                        _.each(t,
                            function(e, n) {
                                i = [],
                                    _.each(e.files,
                                        function(e) {
                                            o[e.id] = {
                                                url: e.url,
                                                name: e.name,
                                                description: e.description
                                            },
                                                i.push(e.id + "")
                                        }),
                                    1 === e.radio && (s[n] = i)
                            }),
                            cdns.idMap = o,
                            cdns.singleMap = s,
                            e && e()
                    })
            },
            finishedSelectCdns: function() {
                var e = this.code.selectedCdns.length,
                    n = 0,
                    t = [],
                    o = _.filter(this.libs,
                        function(n) {
                            return n.isInner || (n.sort = e++),
                                !n.isInner
                        }),
                    s = _.sortBy(this.code.selectedCdns,
                        function(e) {
                            return parseInt(e)
                        });
                _.each(s,
                    function(e) {
                        var o = cdns.idMap[e];
                        t.push({
                            id: e,
                            url: o.url,
                            name: o.name,
                            showName: o.name,
                            isInner: !0,
                            sort: n++
                        })
                    }),
                    o.length > 0 && (t = t.concat(o)),
                    this.libs = t,
                    this.libSort = n + o.length,
                    this.showCdns(!0)
            }
        }
    });
app.commonMethod(),
    hcodeOptions.loading = "#loading",
    hcode = new HCode("#editor", hcodeOptions,
        function() {
            this.run()
        });