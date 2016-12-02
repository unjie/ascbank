/*!
 * froala_editor v2.3.4 (https://www.froala.com/wysiwyg-editor)
 * License https://froala.com/wysiwyg-editor/terms/
 * Copyright 2014-2016 Froala Labs
 */
!function(a) {
    "function" == typeof define && define.amd ? define(["jquery"], a) : "object" == typeof module && module.exports ? module.exports = function(b, c) {
        return void 0 === c && (c = "undefined" != typeof window ? require("jquery") : require("jquery")(b)),
        a(c),
        c
    }
    : a(jQuery)
}(function(a) {
    "use strict";
    if (a.extend(a.FE.DEFAULTS, {
        fileManagerLoadURL: "./file/reads",
        fileManagerLoadMethod: "get",
        fileManagerLoadContentType:"application/json;charset=UTF-8",
        fileManagerLoadParams: {},
        fileManagerPreloader: "",
        fileManagerDeleteURL: "",
        fileManagerDeleteMethod: "post",
        fileManagerDeleteContentType:"application/json;charset=UTF-8",
        fileManagerDeleteParams: {},
        fileManagerPageSize: 12,
        fileManagerScrollOffset: 20,
        fileManagerToggleTags: !0
    }),
    a.FE.PLUGINS.fileManager = function(b) {
        function c() {
            A || h(),
            A.data("instance", b),
            A.show(),
            G.show(),
            V = b.file.get(),
            B || y(),
            i(),
            b.$doc.find("body").addClass("prevent-scroll"),
            b.helpers.isMobile() && b.$doc.find("body").addClass("fr-mobile")
        }
        function d() {
            var a = A.data("instance") || b;
            a.events.enableBlur(),
            A.hide(),
            G.hide(),
            a.$doc.find("body").removeClass("prevent-scroll fr-mobile")
        }
        function e() {
            var b = a(window).outerWidth();
            return 768 > b ? 2 : 1200 > b ? 3 : 4
        }
        function f() {
            C.empty();
            for (var a = 0; L > a; a++)
                C.append('<div class="fr-list-column"></div>')
        }
        function g() {
            var c = "";
            b.opts.theme && (c = " " + b.opts.theme + "-theme");
            var d = '<div class="fr-modal' + c + '"><div class="fr-modal-wrapper">';
            return d += '<div class="fr-modal-title"><div class="fr-modal-title-line"><i class="fa fa-bars fr-modal-more fr-not-available" id="fr-modal-more-' + b.sid + '" title="' + b.language.translate("Tags") + '"></i><h4 data-text="true">' + b.language.translate("Manage files") + '</h4><i title="' + b.language.translate("Cancel") + '" class="fa fa-times fr-modal-close" id="fr-modal-close"></i></div>',
            d += '<div class="fr-modal-tags" id="fr-modal-tags"></div>',
            d += "</div>",
            d += '<img class="fr-preloader" id="fr-preloader" alt="' + b.language.translate("Loading") + '.." src="' + b.opts.fileManagerPreloader + '" style="display: none;">',
            d += '<div class="fr-scroller" id="fr-scroller"><div class="fr-file-list" id="fr-file-list"></div></div>',
            d += "</div></div>",
            a(d)
        }
        function h() {
            b.shared.$modal ? (A = b.shared.$modal,
            G = b.shared.$overlay) : (b.shared.$modal = g(),
            A = b.shared.$modal,
            b.helpers.isMobile() || A.addClass("fr-desktop"),
            A.appendTo("body"),
            b.shared.$overlay = a('<div class="fr-overlay">').appendTo("body"),
            G = b.shared.$overlay,
            b.opts.theme && G.addClass(b.opts.theme + "-theme"),
            d()),
            b.events.on("shared.destroy", function() {
                A.removeData().remove(),
                G.removeData().remove()
            }, !0)
        }
        function i() {
            B.show(),
            C.find(".fr-list-column").empty(),
            b.opts.fileManagerLoadURL ? a.ajax({
                url: b.opts.fileManagerLoadURL,
                method: b.opts.fileManagerLoadMethod,
                data:JSON.stringify( b.opts.fileManagerLoadParams),
                contentType:b.opts.fileManagerLoadContentType,
             // processData:false,
                traditional:false,
                dataType: "json",
                crossDomain: b.opts.requestWithCORS,
                xhrFields: {
                    withCredentials: b.opts.requestWithCORS
                },
                headers: b.opts.requestHeaders
            }).done(function(a, c, d) {
            	a=a.data,
                b.events.trigger("fileManager.filesLoaded", [a]),
                j(a, d.response),
                B.hide()
            }).fail(function() {
                var a = this.xhr();
                t(N, a.response || a.responseText)
            }) : t(O)
        }
        function j(a, b) {
            try {
                C.find(".fr-list-column").empty(),
                I = 0,
                J = 0,
                K = 0,
                H = a,
                k()
            } catch (c) {
                t(P, b)
            }
        }
        function k() {
            if (J < H.length && (C.outerHeight() <= D.outerHeight() + b.opts.fileManagerScrollOffset || D.scrollTop() + b.opts.fileManagerScrollOffset > C.outerHeight() - D.outerHeight())) {
                I++;
                for (var a = b.opts.fileManagerPageSize * (I - 1); a < Math.min(H.length, b.opts.fileManagerPageSize * I); a++)
                    l(H[a])
            }
        }
        function l(c) {
            var d =a("<a/>")
              , e = a('<div class="fr-file-container fr-empty fr-file-' + K++ + '" data-loading="' + b.language.translate("Loading") + '.." data-deleting="' + b.language.translate("Deleting") + '..">');
            p(!1);
         /*
            d.onload = function() {
                e.height(Math.floor(e.width() / d.width * d.height));
                var f = a("<a/>");
                  if (c.path)
                    f.attr("href", c.path);
                else {
                    if (t(Q, c),
                    !c.path)
                        return t(R, c),
                        !1;
                    f.attr("href", c.path)
                }
                //type
                if (c.path && f.attr("data-url", c.path),  c.type){
                	if(c.type === 'dir'){
                		f.html('<div class="item-icon"><i class="glyphicon glyphicon-folder-open "></i></div>'+c.name)
                	}else{
                    	f.html('<div class="item-icon"><i class="glyphicon glyphicon-file item-extension" data-ext="'+c.name.split('.')[1]+'"></i></div>'+c.name)
                	}
                }
                	
                
                //tag
                
                if (c.path && f.attr("data-url", c.path),  c.tag)
                    if (F.find(".fr-modal-more.fr-not-available").removeClass("fr-not-available"),  F.find(".fr-modal-tags").show(),c.tag.indexOf(",") >= 0) {
                        for (var g = c.tag.split(","), h = 0; h < g.length; h++)
                            g[h] = g[h].trim(),
                            0 === E.find('a[title="' + g[h] + '"]').length && E.append('<a role="button" title="' + g[h] + '">' + g[h] + "</a>");
                        f.attr("data-tag", g.join())
                    } else{
                        0 === E.find('a[title="' + c.tag.trim() + '"]').length && E.append('<a role="button" title="' + c.tag.trim() + '">' + c.tag.trim() + "</a>"),
                        f.attr("data-tag", c.tag.trim());
                    }
                E.find(".fr-selected-tag").each(function(a, b) {         x(f, b.text) || e.hide()         })
			
                for (var i in c) c.hasOwnProperty(i) && "path" != i && f.attr("data-" + i, c[i]);
                e.append(f).append(a(b.icon.create("fileManagerDelete")).addClass("fr-delete-img").attr("title", b.language.translate("Delete"))).append(a(b.icon.create("fileManagerInsert")).addClass("fr-insert-img").attr("title", b.language.translate("Insert")))
             
              f.on("load", function() {
                    e.removeClass("fr-empty"),
                    e.height("auto"),
                    J++;
                    var a = n(parseInt(f.parent().attr("class").match(/fr-file-(\d+)/)[1], 10) + 1);
                    o(a),
                    p(!1),
                    J % b.opts.fileManagerPageSize === 0 && k()
                }),
                b.events.trigger("fileManager.fileLoaded", [f])
                
            } ,
            d.onerror = function() {
                J++,
                e.remove();
                var a = n(parseInt(e.attr("class").match(/fr-file-(\d+)/)[1], 10) + 1);
                o(a),
                t(M, c),
                J % b.opts.fileManagerPageSize === 0 && k()
            } ,*/
            
            if (c.path && d.attr("data-url", c.path),  c.type){
            	if(c.type === 'dir'){
            		d.html('<div class="item-icon"><i class="glyphicon glyphicon-folder-open "></i></div>'+c.name)
            	}else{
                	d.html('<div class="item-icon"><i class="glyphicon glyphicon-file item-extension" data-ext="'+c.name.split('.').pop()+'"></i></div>'+c.name)
            	}
            }
            for (var i in c) c.hasOwnProperty(i) && "path" != i && d.attr("data-" + i, c[i]);
            e.height(Math.floor(e.width() / d.width * d.height));
            e.append(d).append(a(b.icon.create("fileManagerDelete")).addClass("fr-delete-img").attr("title", b.language.translate("Delete"))).append(a(b.icon.create("fileManagerInsert")).addClass("fr-insert-img").attr("title", b.language.translate("Insert")))
           
            d.addClass();
            
            d.href = c.path,
            m().append(e);
            

        }
        function m() {
            var b, c;
            return C.find(".fr-list-column").each(function(d, e) {
                var f = a(e);
                0 === d ? (c = f.outerHeight(),
                b = f) : f.outerHeight() < c && (c = f.outerHeight(),
                b = f)
            }),
            b
        }
        function n(b) {
            void 0 === b && (b = 0);
            for (var c = [], d = K - 1; d >= b; d--) {
                var e = C.find(".fr-file-" + d);
                e.length && (c.push(e),
                a('<div id="fr-file-hidden-container">').append(e),
                C.find(".fr-file-" + d).remove())
            }
            return c
        }
        function o(a) {
            for (var b = a.length - 1; b >= 0; b--)
                m().append(a[b])
        }
        function p(a) {
            if (void 0 === a && (a = !0),
            !A.is(":visible"))
                return !0;
            var c = e();
            if (c != L) {
                L = c;
                var d = n();
                f(),
                o(d)
            }
            var g = b.$win.height()
              , h = A.find(".fr-modal-wrapper")
              , i = parseFloat(h.css("margin-top")) + parseFloat(h.css("margin-bottom"))
              , j = parseFloat(h.css("padding-top")) + parseFloat(h.css("padding-bottom"))
              , l = parseFloat(h.css("border-top-width"))
              , m = h.find("h4").outerHeight();
            D.height(Math.min(C.outerHeight(), g - i - j - m - l)),
            a && k()
        }
        function q(a) {
            var b = {}
              , c = a.data();
            for (var d in c)
                c.hasOwnProperty(d) && "url" != d && "tag" != d && (b[d] = c[d]);
            return b
        }
        function r(c) {
            var e = a(c.currentTarget).siblings("img")
              , f = A.data("instance") || b;
            if (d(),
            f.file.showProgressBar(),
            V)
                V.trigger("click");
            else {
                f.events.focus(!0),
                f.selection.restore();
                var g = f.position.getBoundingRect()
                  , h = g.left + g.width / 2
                  , i = g.top + g.height;
                f.popups.setContainer("file.insert", f.$box || a("body")),
                f.popups.show("file.insert", h, i)
            }
            f.file.insert(e.data("url"), !1, q(e), V)
        }
        function s(c) {
            var d = a(c.currentTarget).siblings("img")
              , e = b.language.translate("Are you sure? file will be deleted.");
            confirm(e) && (b.opts.fileManagerDeleteURL ? b.events.trigger("fileManager.beforeDeletefile", [d]) !== !1 && (d.parent().addClass("fr-file-deleting"),
            a.ajax({
                method: b.opts.fileManagerDeleteMethod,
                url: b.opts.fileManagerDeleteURL,
                contentType:b.opts.fileManagerDeleteContentType,
                data:JSON.stringify({
                	items: [d.attr("src")]
                }),/* a.extend({}, q(d)), */
                crossDomain: b.opts.requestWithCORS,
                xhrFields: {
                    withCredentials: b.opts.requestWithCORS
                },
                headers: b.opts.requestHeaders
            }).done(function(a) {
                b.events.trigger("fileManager.fileDeleted", [a]);
                var c = n(parseInt(d.parent().attr("class").match(/fr-file-(\d+)/)[1], 10) + 1);
                d.parent().remove(),
                o(c),
                p(!0)
            }).fail(function() {
                var a = this.xhr();
                t(S, a.response || a.responseText)
            })) : t(T))
        }
        function t(c, d) {
            c >= 10 && 20 > c ? B.hide() : c >= 20 && 30 > c && a(".fr-file-deleting").removeClass("fr-file-deleting"),
            b.events.trigger("fileManager.error", [{
                code: c,
                message: U[c]
            }, d])
        }
        function u() {
            var a = F.find(".fr-modal-title-line").outerHeight()
              , b = E.outerHeight();
            F.toggleClass(".fr-show-tags"),
            F.hasClass(".fr-show-tags") ? (F.css("height", a + b),
            E.find("a").css("opacity", 1)) : (F.css("height", a),
            E.find("a").css("opacity", 0))
        }
        function v() {
            var b = E.find(".fr-selected-tag");
            b.length > 0 ? (C.find("img").parent().show(),
            b.each(function(b, c) {
                C.find("img").each(function(b, d) {
                    var e = a(d);
                    x(e, c.text) || e.parent().hide()
                })
            })) : C.find("img").parent().show();
            var c = n();
            o(c),
            k()
        }
        function w(c) {
            c.preventDefault();
            var d = a(c.currentTarget);
            d.toggleClass("fr-selected-tag"),
            b.opts.fileManagerToggleTags && d.siblings("a").removeClass("fr-selected-tag"),
            v()
        }
        function x(a, b) {
            for (var c = a.attr("data-tag").split(","), d = 0; d < c.length; d++)
                if (c[d] == b)
                    return !0;
            return !1
        }
        function y() {
            B = A.find("#fr-preloader"),
            C = A.find("#fr-file-list"),
            D = A.find("#fr-scroller"),
            E = A.find("#fr-modal-tags"),
            F = E.parent(),
            L = e(),
            f();
            var c = F.find(".fr-modal-title-line").outerHeight();
            F.css("height", c),
            D.css("margin-top", c),
            b.events.bindClick(A, "i#fr-modal-close", d),
            b.events.$on(a(b.o_win), "resize", function() {
                p(H ? !0 : !1)
            }),
            b.helpers.isMobile() && (b.events.bindClick(C, "div.fr-file-container", function(b) {
                A.find(".fr-mobile-selected").removeClass("fr-mobile-selected"),
                a(b.currentTarget).addClass("fr-mobile-selected")
            }),
            A.on(b._mousedown, function() {
                A.find(".fr-mobile-selected").removeClass("fr-mobile-selected")
            })),
            b.events.bindClick(C, ".fr-insert-img", r),
            b.events.bindClick(C, ".fr-delete-img", s),
            A.on(b._mousedown + " " + b._mouseup, function(a) {
                a.stopPropagation()
            }),
            A.on(b._mousedown, "*", function() {
                b.events.disableBlur()
            }),
            D.on("scroll", k),
            b.events.bindClick(A, "i#fr-modal-more-" + b.sid, u),
            b.events.bindClick(E, "a", w)
        }
        function z() {
            return b.$wp || "IMG" == b.$el.get(0).tagName ? void 0 : !1
        }
        var A, B, C, D, E, F, G, H, I, J, K, L, M = 10, N = 11, O = 12, P = 13, Q = 14, R = 15, S = 21, T = 22, U = {};
        U[M] = "file cannot be loaded from the passed link.",
        U[N] = "Error during load files request.",
        U[O] = "Missing fileManagerLoadURL option.",
        U[P] = "Parsing load response failed.",
        U[Q] = "Missing file thumb.",
        U[R] = "Missing file URL.",
        U[S] = "Error during delete file request.",
        U[T] = "Missing fileManagerDeleteURL option.";
        var V;
        return {
            require: ["file"],
            _init: z,
            show: c,
            hide: d
        }
    }
    ,
    !a.FE.PLUGINS.file)
        throw new Error("file manager plugin requires file plugin.");
    a.FE.DEFAULTS.fileInsertButtons.push("fileManager"),
    a.FE.RegisterCommand("fileManager", {
        title: "Browse",
        undo: !1,
        focus: !1,
        callback: function() {
            this.fileManager.show()
        },
        plugin: "fileManager"
    }),
    a.FE.DefineIcon("fileManager", {
        NAME: "folder"
    }),
    a.FE.DefineIcon("fileManagerInsert", {
        NAME: "plus"
    }),
    a.FE.DefineIcon("fileManagerDelete", {
        NAME: "trash"
    })
});
