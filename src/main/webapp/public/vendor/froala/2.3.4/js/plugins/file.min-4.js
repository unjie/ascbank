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
    a.extend(a.FE.POPUP_TEMPLATES, {
        "file.insert": "[_BUTTONS_][_UPLOAD_LAYER_][_BY_URL_LAYER_][_PROGRESS_BAR_]",
//        "file.edit": "[_BUTTONS_]",
//        "file.alt": "[_BUTTONS_][_ALT_LAYER_]",
//        "file.size": "[_BUTTONS_][_SIZE_LAYER_]"
    }),
    a.extend(a.FE.DEFAULTS, {
        fileInsertButtons: ["fileBack", "|", "fileUpload", "fileByURL"],
        fileEditButtons: ["fileReplace", "fileAlign", "fileRemove", "|", "fileLink", "linkOpen", "linkEdit", "linkRemove", "-", "fileDisplay", "fileStyle", "fileAlt", "fileSize"],
        fileAltButtons: ["fileBack", "|"],
        fileSizeButtons: ["fileBack", "|"],
        fileUploadURL: "./file/upload",
        fileUploadParam: "files",
        fileUploadParams: {},
        fileUploadToS3: !1,
        fileUploadMethod: "POST",
        fileMaxSize: 10485760,
        fileAllowedTypes: ["jpeg", "jpg", "png", "gif", "svg+xml"],
        fileResize: !0,
        fileResizeWithPercent: !1,
        fileRoundPercent: !1,
        fileDefaultWidth: 300,
        fileDefaultAlign: "center",
        fileDefaultDisplay: "block",
        fileSplitHTML: !1,
        fileStyles: {
            "fr-rounded": "Rounded",
            "fr-bordered": "Bordered"
        },
        fileMove: !0,
        fileMultipleStyles: !0,
        fileTextNear: !0,
        filePaste: !0,
        filePasteProcess: !1,
        fileMinWidth: 16,
        fileOutputSize: !1
    }),
    a.FE.PLUGINS.file = function(b) {
        function c() {
            var a = b.popups.get("file.insert")
              , c = a.find(".fr-file-by-url-layer input");
            c.val(""),
            qa && c.val(qa.attr("src")),
            c.trigger("change")
        }
        function d() {
            var a = b.$tb.find('.fr-command[data-cmd="insertfile"]')
              , c = b.popups.get("file.insert");
            if (c || (c = L()),
            r(),
            !c.hasClass("fr-active"))
                if (b.popups.refresh("file.insert"),
                b.popups.setContainer("file.insert", b.$tb),
                a.is(":visible")) {
                    var d = a.offset().left + a.outerWidth() / 2
                      , e = a.offset().top + (b.opts.toolbarBottom ? 10 : a.outerHeight() - 10);
                    b.popups.show("file.insert", d, e, a.outerHeight())
                } else
                    b.position.forSelection(c),
                    b.popups.show("file.insert")
        }
      /*  function e() {
           var c = b.popups.get("file.edit");
            c || (c = p()),
            b.popups.setContainer("file.edit", a(b.opts.scrollableContainer)),
            b.popups.refresh("file.edit");
            var d = qa.offset().left + qa.outerWidth() / 2
              , e = qa.offset().top + qa.outerHeight();
            b.popups.show("file.edit", d, e, qa.outerHeight())
        }*/
        function f() {
            r()
        }
        function g(a) {
            if (!a.hasClass("fr-dii") && !a.hasClass("fr-dib")) {
                var c = a.css("float");
                a.css("float", "none"),
                "block" == a.css("display") ? (a.css("float", c),
                b.opts.fileEditButtons.indexOf("fileAlign") >= 0 && (0 === parseInt(a.css("margin-left"), 10) && (a.attr("style") || "").indexOf("margin-right: auto") >= 0 ? a.addClass("fr-fil") : 0 === parseInt(a.css("margin-right"), 10) && (a.attr("style") || "").indexOf("margin-left: auto") >= 0 && a.addClass("fr-fir")),
                a.addClass("fr-dib")) : (a.css("float", c),
                b.opts.fileEditButtons.indexOf("fileAlign") >= 0 && ("left" == a.css("float") ? a.addClass("fr-fil") : "right" == a.css("float") && a.addClass("fr-fir")),
                a.addClass("fr-dii")),
                a.css("margin", ""),
                a.css("float", ""),
                a.css("display", ""),
                a.css("z-index", ""),
                a.css("position", ""),
                a.css("overflow", ""),
                a.css("vertical-align", "")
            }
        }
        function h() {
            for (var c = "IMG" == b.$el.get(0).tagName ? [b.$el.get(0)] : b.$el.get(0).querySelectorAll("img"), d = 0; d < c.length; d++) {
                var e = a(c[d]);
                (b.opts.fileEditButtons.indexOf("fileAlign") >= 0 || b.opts.fileEditButtons.indexOf("fileDisplay") >= 0) && g(e),
                e.attr("width") && (e.css("width", e.width()),
                e.removeAttr("width")),
                b.opts.fileTextNear || e.removeClass("fr-dii").addClass("fr-dib"),
                b.opts.iframe && e.on("load", b.size.syncIframe)
            }
        }
        function i() {
            var c, d = Array.prototype.slice.call(b.$el.get(0).querySelectorAll("img")), e = [];
            for (c = 0; c < d.length; c++)
                e.push(d[c].getAttribute("src")),
                a(d[c]).toggleClass("fr-draggable", b.opts.fileMove),
                "" === d[c].className && d[c].removeAttribute("class"),
                "" === d[c].getAttribute("style") && d[c].removeAttribute("style");
            if (Da)
                for (c = 0; c < Da.length; c++)
                    e.indexOf(Da[c].getAttribute("src")) < 0 && b.events.trigger("file.removed", [a(Da[c])]);
            Da = d
        }
        function j() {
            ra || X();
            var c = b.$wp || a(b.opts.scrollableContainer);
            c.append(ra),
            ra.data("instance", b);
            var d = c.scrollTop() - ("static" != c.css("position") ? c.offset().top : 0)
              , e = c.scrollLeft() - ("static" != c.css("position") ? c.offset().left : 0);
            e -= b.helpers.getPX(c.css("border-left-width")),
            d -= b.helpers.getPX(c.css("border-top-width")),
            ra.css("top", (b.opts.iframe ? qa.offset().top : qa.offset().top + d) - 1).css("left", (b.opts.iframe ? qa.offset().left : qa.offset().left + e) - 1).css("width", qa.get(0).getBoundingClientRect().width).css("height", qa.get(0).getBoundingClientRect().height).addClass("fr-active")
        }
        function k(a) {
            return '<div class="fr-handler fr-h' + a + '"></div>'
        }
        function l(c) {
            if (!b.core.sameInstance(ra))
                return !0;
            if (c.preventDefault(),
            c.stopPropagation(),
            b.$el.find("img.fr-error").left)
                return !1;
            b.undo.canDo() || b.undo.saveStep(),
            sa = a(this),
            sa.data("start-x", c.pageX || c.originalEvent.touches[0].pageX),
            sa.data("start-width", qa.width()),
            sa.data("start-height", qa.height());
            var d = qa.width();
            if (b.opts.fileResizeWithPercent) {
                var e = qa.parentsUntil(b.$el, b.html.blockTagsQuery()).get(0) || b.$el.get(0);
                qa.css("width", (d / a(e).outerWidth() * 100).toFixed(2) + "%")
            } else
                qa.css("width", d);
            ta.show(),
            b.popups.hideAll(),
            ea()
        }
        function m(c) {
            if (!b.core.sameInstance(ra))
                return !0;
            if (sa && qa) {
                if (c.preventDefault(),
                b.$el.find("img.fr-error").left)
                    return !1;
                var d = c.pageX || (c.originalEvent.touches ? c.originalEvent.touches[0].pageX : null );
                if (!d)
                    return !1;
                var e = sa.data("start-x")
                  , f = d - e
                  , g = sa.data("start-width");
                if ((sa.hasClass("fr-hnw") || sa.hasClass("fr-hsw")) && (f = 0 - f),
                b.opts.fileResizeWithPercent) {
                    var h = qa.parentsUntil(b.$el, b.html.blockTagsQuery()).get(0) || b.$el.get(0);
                    g = ((g + f) / a(h).outerWidth() * 100).toFixed(2),
                    b.opts.fileRoundPercent && (g = Math.round(g)),
                    qa.css("width", g + "%"),
                    qa.css("height", "").removeAttr("height")
                } else
                    g + f >= b.opts.fileMinWidth && qa.css("width", g + f),
                    qa.css("height", sa.data("start-height") * qa.width() / sa.data("start-width"));
                j(),
                b.events.trigger("file.resize", [oa()])
            }
        }
        function n(a) {
            if (!b.core.sameInstance(ra))
                return !0;
            if (sa && qa) {
                if (a && a.stopPropagation(),
                b.$el.find("img.fr-error").left)
                    return !1;
                sa = null ,
                ta.hide(),
                j(),
//                e(),
                b.undo.saveStep(),
                b.events.trigger("file.resizeEnd", [oa()])
            }
        }
        function o(a, c) {
            b.edit.on(),
            qa && qa.addClass("fr-error"),
            t(b.language.translate("Something went wrong. Please try again.")),
            b.events.trigger("file.error", [{
                code: a,
                message: Ca[a]
            }, c])
        }
        function p(a) {
            if (a)
                return b.$wp && b.events.$on(b.$wp, "scroll", function() {
                    qa /*&& b.popups.isVisible("file.edit") && e()*/
                }),
                !0;
            var c = "";
            b.opts.fileEditButtons.length > 0 && (c += '<div class="fr-buttons">',
            c += b.button.buildList(b.opts.fileEditButtons),
            c += "</div>");
            var d = {
                buttons: c
            }
//              , f = b.popups.create("file.edit", d);
            return f
        }
        function q(c) {
            var d = b.popups.get("file.insert");
            if (d || (d = L()),
            d.find(".fr-layer.fr-active").removeClass("fr-active").addClass("fr-pactive"),
            d.find(".fr-file-progress-bar-layer").addClass("fr-active"),
            d.find(".fr-buttons").hide(),
            qa) {
                b.popups.setContainer("file.insert", a(b.opts.scrollableContainer));
                var e = qa.offset().left + qa.width() / 2
                  , f = qa.offset().top + qa.height();
                b.popups.show("file.insert", e, f, qa.outerHeight())
            }
            "undefined" == typeof c && s("Uploading", 0)
        }
        function r(a) {
            var c = b.popups.get("file.insert");
            c && (c.find(".fr-layer.fr-pactive").addClass("fr-active").removeClass("fr-pactive"),
            c.find(".fr-file-progress-bar-layer").removeClass("fr-active"),
            c.find(".fr-buttons").show(),
            (a || b.$el.find("img.fr-error").length) && (b.events.focus(),
            b.$el.find("img.fr-error").remove(),
            b.undo.saveStep(),
            b.undo.run(),
            b.undo.dropRedo()))
        }
        function s(a, c) {
            var d = b.popups.get("file.insert");
            if (d) {
                var e = d.find(".fr-file-progress-bar-layer");
                e.find("h3").text(a + (c ? " " + c + "%" : "")),
                e.removeClass("fr-error"),
                c ? (e.find("div").removeClass("fr-indeterminate"),
                e.find("div > span").css("width", c + "%")) : e.find("div").addClass("fr-indeterminate")
            }
        }
        function t(a) {
            q();
            var c = b.popups.get("file.insert")
              , d = c.find(".fr-file-progress-bar-layer");
            d.addClass("fr-error"),
            d.find("h3").text(a)
        }
        function u() {
            var a = b.popups.get("file.insert")
              , c = a.find(".fr-file-by-url-layer input");
            c.val().length > 0 && (q(),
            s("Loading file"),
            x(c.val(), !0, [], qa),
            c.val(""),
            c.blur())
        }
        function v(a) {
            ba.call(a.get(0))
        }
        function w() {
            var c = a(this);
            b.popups.hide("file.insert"),
            c.removeClass("fr-uploading"),
            c.next().is("br") && c.next().remove(),
            v(c),
            b.events.trigger("file.loaded", [c])
        }
        function x(a, c, d, e, f) {
            b.edit.off(),
            s("Loading file"),
            c && (a = b.helpers.sanitizeURL(a));
            var g = new file;
            g.onload = function() {
                var c, g;
                if (e) {
                    var h = e.data("fr-old-src");
                    b.$wp ? (c = e.clone().removeData("fr-old-src").removeClass("fr-uploading"),
                    c.off("load"),
                    h && e.attr("src", h),
                    e.replaceWith(c)) : c = e;
                    for (var j = c.get(0).attributes, k = 0; k < j.length; k++) {
                        var l = j[k];
                        0 === l.nodeName.indexOf("data-") && c.removeAttr(l.nodeName)
                    }
                    if ("undefined" != typeof d)
                        for (g in d)
                            d.hasOwnProperty(g) && "path" != g && c.attr("data-" + g, d[g]);
                    c.on("load", w),
                    c.attr("src", a),
                    b.edit.on(),
                    i(),
                    b.undo.saveStep(),
                    b.events.trigger(h ? "file.replaced" : "file.inserted", [c, f])
                } else
                    c = D(a, d, w),
                    i(),
                    b.undo.saveStep(),
                    b.events.trigger("file.inserted", [c, f])
            }
            ,
            g.onerror = function() {
                o(va)
            }
            ,
            g.src = a
        }
        function y(c) {
            try {
                if (b.events.trigger("file.uploaded", [c], !0) === !1)
                    return b.edit.on(),
                    !1;
                var d = a.parseJSON(c).data[0];
                return d.path ? d : (o(wa, c),
                !1)
            } catch (e) {
                return o(ya, c),
                !1
            }
        }
        function z(c) {
            try {
                var d = a(c).find("Location").text()
                  , e = a(c).find("Key").text();
                return b.events.trigger("file.uploadedToS3", [d, e, c], !0) === !1 ? (b.edit.on(),
                !1) : d
            } catch (f) {
                return o(ya, c),
                !1
            }
        }
        function A(a) {
            s("Loading file");
            var c = this.status
              , d = this.response
              , e = this.responseXML
              , f = this.responseText;
            try {
                if (b.opts.fileUploadToS3)
                    if (201 == c) {
                        var g = z(e);
                        g && x(g, !1, [], a, d || e)
                    } else
                        o(ya, d || e);
                else if (c >= 200 && 300 > c) {
                    var h = y(f);
                    h && x(h.path, !1, h, a, d || f)
                } else
                    o(xa, d || f)
            } catch (i) {
                o(ya, d || f)
            }
        }
        function B() {
            o(ya, this.response || this.responseText || this.responseXML)
        }
        function C(a) {
            if (a.lengthComputable) {
                var b = a.loaded / a.total * 100 | 0;
                s("Uploading", b)
            }
        }
        function D(c, d, e) {
            var f, g = "";
            if (d && "undefined" != typeof d)
                for (f in d)
                    d.hasOwnProperty(f) && "link" != f && (g += " data-" + f + '="' + d[f] + '"');
            var h = b.opts.fileDefaultWidth;
            h && "auto" != h && (h += b.opts.fileResizeWithPercent ? "%" : "px");
            var i = a('<img class="' + (b.opts.fileDefaultDisplay ? "fr-di" + b.opts.fileDefaultDisplay[0] : "") + (b.opts.fileDefaultAlign && "center" != b.opts.fileDefaultAlign ? " fr-fi" + b.opts.fileDefaultAlign[0] : "") + '" src="' + c + '"' + g + (h ? ' style="width: ' + h + ';"' : "") + ">");
            i.on("load", e),
            b.edit.on(),
            b.events.focus(!0),
            b.selection.restore(),
            b.undo.saveStep(),
            b.opts.fileSplitHTML ? b.markers.split() : b.markers.insert();
            var j = b.$el.find(".fr-marker");
            return j.replaceWith(i),
            b.html.wrap(),
            b.selection.clear(),
            i
        }
        function E() {
            b.edit.on(),
            r(!0)
        }
        function F(c, d, e) {
            function f() {
                var e = a(this);
                e.off("load"),
                e.addClass("fr-uploading"),
                e.next().is("br") && e.next().remove(),
                b.placeholder.refresh(),
                e.is(qa) || v(e),
                j(),
                q(),
                b.edit.off(),
                c.onload = function() {
                    A.call(c, e)
                }
                ,
                c.onerror = B,
                c.upload.onprogress = C,
                c.onabort = E,
                e.off("abortUpload").on("abortUpload", function() {
                    4 != c.readyState && c.abort()
                }),
                c.send(d)
            }
            var g, h = new FileReader;
            h.addEventListener("load", function() {
                var a = h.result;
                if (h.result.indexOf("svg+xml") < 0) {
                    for (var c = atob(h.result.split(",")[1]), d = [], e = 0; e < c.length; e++)
                        d.push(c.charCodeAt(e));
                    a = window.URL.createObjectURL(new Blob([new Uint8Array(d)],{
                        type: "file/jpeg"
                    }))
                }
                qa ? (qa.on("load", f),
                b.edit.on(),
                b.undo.saveStep(),
                qa.data("fr-old-src", qa.attr("src")),
                qa.attr("src", a)) : g = D(a, null , f)
            }, !1),
            h.readAsDataURL(e)
        }
        function G(a) {
            if (b.events.trigger("file.beforeUpload", [a]) === !1)
                return !1;
            if ("undefined" != typeof a && a.length > 0) {
                var c = a[0];
                if (c.size > b.opts.fileMaxSize)
                    return o(za),
                    !1;
                if (b.opts.fileAllowedTypes.indexOf(c.type.replace(/file\//g, "")) < 0)
                    return o(Aa),
                    !1;
                var d;
                if (b.drag_support.formdata && (d = b.drag_support.formdata ? new FormData : null ),
                d) {
                    var e;
                    if (b.opts.fileUploadToS3 !== !1) {
                        d.append("key", b.opts.fileUploadToS3.keyStart + (new Date).getTime() + "-" + (c.name || "untitled")),
                        d.append("success_action_status", "201"),
                        d.append("X-Requested-With", "xhr"),
                        d.append("Content-Type", c.type);
                        for (e in b.opts.fileUploadToS3.params)
                            b.opts.fileUploadToS3.params.hasOwnProperty(e) && d.append(e, b.opts.fileUploadToS3.params[e])
                    }
                    for (e in b.opts.fileUploadParams)
                        b.opts.fileUploadParams.hasOwnProperty(e) && d.append(e, b.opts.fileUploadParams[e]);
                    d.append(b.opts.fileUploadParam, c);
                    var f = b.opts.fileUploadURL;
                    b.opts.fileUploadToS3 && (f = "https://" + b.opts.fileUploadToS3.region + ".amazonaws.com/" + b.opts.fileUploadToS3.bucket);
                    var g = b.core.getXHR(f, b.opts.fileUploadMethod);
                    F(g, d, c)
                }
            }
        }
        function H(c) {
            b.events.$on(c, "dragover dragenter", ".fr-file-upload-layer", function() {
                return a(this).addClass("fr-drop"),
                !1
            }),
            b.events.$on(c, "dragleave dragend", ".fr-file-upload-layer", function() {
                return a(this).removeClass("fr-drop"),
                !1
            }),
            b.events.$on(c, "drop", ".fr-file-upload-layer", function(d) {
                d.preventDefault(),
                d.stopPropagation(),
                a(this).removeClass("fr-drop");
                var e = d.originalEvent.dataTransfer;
                if (e && e.files) {
                    var f = c.data("instance") || b;
                    f.events.disableBlur(),
                    f.file.upload(e.files),
                    f.events.enableBlur()
                }
            }),
            b.events.$on(c, "change", '.fr-file-upload-layer input[type="file"]', function() {
                if (this.files) {
                    var d = c.data("instance") || b;
                    d.events.disableBlur(),
                    c.find("input:focus").blur(),
                    d.events.enableBlur(),
                    d.file.upload(this.files)
                }
                a(this).val("")
            })
        }
        function I(c) {
            var d = c.originalEvent.dataTransfer;
            if (d && d.files && d.files.length) {
                var e = d.files[0];
                if (e && e.type && b.opts.fileAllowedTypes.indexOf(e.type.replace(/file\//g, "")) >= 0) {
                    b.markers.remove(),
                    b.markers.insertAtPoint(c.originalEvent),
                    b.$el.find(".fr-marker").replaceWith(a.FE.MARKERS),
                    b.popups.hideAll();
                    var f = b.popups.get("file.insert");
                    return f || (f = L()),
                    b.popups.setContainer("file.insert", a(b.opts.scrollableContainer)),
                    b.popups.show("file.insert", c.originalEvent.pageX, c.originalEvent.pageY),
                    q(),
                    G(d.files),
                    c.preventDefault(),
                    c.stopPropagation(),
                    !1
                }
            }
        }
        function J() {
            var c, d, e = b.selection.ranges(0);
            e.collapsed && e.startContainer.nodeType == Node.ELEMENT_NODE && (e.startContainer.childNodes.length == e.startOffset ? (c = e.startContainer.childNodes[e.startOffset - 1],
            c && "IMG" == c.tagName && "block" == a(c).css("display") && (d = b.node.blockParent(c),
            d && b.html.defaultTag() ? d.nextSibling || (["TD", "TH"].indexOf(d.tagName) < 0 ? a(d).after("<" + b.html.defaultTag() + "><br>" + a.FE.MARKERS + "</" + b.html.defaultTag() + ">") : a(img).after("<br>" + a.FE.MARKERS),
            b.selection.restore()) : d || (a(c).after("<br>" + a.FE.MARKERS),
            b.selection.restore()))) : 0 === e.startOffset && e.startContainer.childNodes.length > e.startOffset && (c = e.startContainer.childNodes[e.startOffset],
            c && "IMG" == c.tagName && "block" == a(c).css("display") && (d = b.node.blockParent(c),
            d && b.html.defaultTag() ? d.previousSibling || (["TD", "TH"].indexOf(d.tagName) < 0 ? a(d).before("<" + b.html.defaultTag() + "><br>" + a.FE.MARKERS + "</" + b.html.defaultTag() + ">") : a(img).before("<br>" + a.FE.MARKERS),
            b.selection.restore()) : d || (a(c).before(a.FE.MARKERS + "<br>"),
            b.selection.restore()))))
        }
        function K() {
            b.events.$on(b.$el, b._mousedown, "IMG" == b.$el.get(0).tagName ? null : 'img:not([contenteditable="false"])', function(c) {
                return a(this).parents('[contenteditable="false"]:not(.fr-element):not(body)').length ? !0 : (b.selection.clear(),
                ua = !0,
                b.browser.msie && (b.events.disableBlur(),
                b.$el.attr("contenteditable", !1)),
                b.draggable || c.preventDefault(),
                void c.stopPropagation())
            }),
            b.events.$on(b.$el, b._mouseup, "IMG" == b.$el.get(0).tagName ? null : 'img:not([contenteditable="false"])', function(c) {
                return a(this).parents('[contenteditable="false"]:not(.fr-element):not(body)').length ? !0 : void (ua && (ua = !1,
                c.stopPropagation(),
                b.browser.msie && (b.$el.attr("contenteditable", !0),
                b.events.enableBlur())))
            }),
            b.events.on("keyup", function(c) {
                if (c.shiftKey && "" === b.selection.text().replace(/\n/g, "")) {
                    var d = b.selection.element()
                      , e = b.selection.endElement();
                    d && "IMG" == d.tagName ? v(a(d)) : e && "IMG" == e.tagName && v(a(e))
                }
            }, !0),
            b.events.on("drop", I),
            b.events.on("mousedown window.mousedown", da),
            b.events.on("window.touchmove", ea),
            b.events.on("mouseup window.mouseup", function() {
                return qa ? (ca(),
                !1) : void 0
            }),
            b.events.on("commands.mousedown", function(a) {
                a.parents(".fr-toolbar").length > 0 && ca()
            }),
            b.events.on("mouseup", J),
            b.events.on("blur file.hideResizer commands.undo commands.redo element.dropped", function() {
                ua = !1,
                ca(!0)
            })
        }
        function L(a) {
            if (a)
                return b.popups.onRefresh("file.insert", c),
                b.popups.onHide("file.insert", f),
                !0;
            var d, e = "";
            b.opts.fileInsertButtons.length > 1 && (e = '<div class="fr-buttons">' + b.button.buildList(b.opts.fileInsertButtons) + "</div>");
            var g = b.opts.fileInsertButtons.indexOf("fileUpload")
              , h = b.opts.fileInsertButtons.indexOf("fileByURL")
              , i = "";
            g >= 0 && (d = " fr-active",   h >= 0 && g > h && (d = ""),
            i = '<div class="fr-file-upload-layer' + d + ' fr-layer" id="fr-file-upload-layer-' + b.id + '"><strong>' + b.language.translate("Drop file") + "</strong><br>(" + b.language.translate("or click") + ')<div class="fr-form"><input type="file" accept="file/' + b.opts.fileAllowedTypes.join(", file/").toLowerCase() + '" tabIndex="-1"></div></div>');
            var j = "";
            h >= 0 && (d = " fr-active",   g >= 0 && h > g && (d = ""),
            j = '<div class="fr-file-by-url-layer' + d + ' fr-layer" id="fr-file-by-url-layer-' + b.id + '"><div class="fr-input-line"><input type="text" placeholder="http://" tabIndex="1"></div><div class="fr-action-buttons"><button type="button" class="fr-command fr-submit" data-cmd="fileInsertByURL" tabIndex="2">' + b.language.translate("Insert") + "</button></div></div>");
            var k = '<div class="fr-file-progress-bar-layer fr-layer"><h3 class="fr-message">Uploading</h3><div class="fr-loader"><span class="fr-progress"></span></div><div class="fr-action-buttons"><button type="button" class="fr-command fr-back" data-cmd="fileDismissError" tabIndex="2">OK</button></div></div>'
              , l = {
                buttons: e,
                upload_layer: i,
                by_url_layer: j,
                progress_bar: k
            }
              , m = b.popups.create("file.insert", l);
            return b.$wp && b.events.$on(b.$wp, "scroll", function() {
                qa && b.popups.isVisible("file.insert") && la()
            }),
            H(m),
            m
        }
       /* function M() {
            if (qa) {
                var a = b.popups.get("file.alt");
                a.find("input").val(qa.attr("alt") || "").trigger("change")
            }
        }
        function N() {
            var c = b.popups.get("file.alt");
            c || (c = O()),
            r(),
            b.popups.refresh("file.alt"),
            b.popups.setContainer("file.alt", a(b.opts.scrollableContainer));
            var d = qa.offset().left + qa.width() / 2
              , e = qa.offset().top + qa.height();
            b.popups.show("file.alt", d, e, qa.outerHeight())
        }
        function O(a) {
            if (a)
                return b.popups.onRefresh("file.alt", M),
                !0;
            var c = "";
            c = '<div class="fr-buttons">' + b.button.buildList(b.opts.fileAltButtons) + "</div>";
            var d = "";
            d = '<div class="fr-file-alt-layer fr-layer fr-active" id="fr-file-alt-layer-' + b.id + '"><div class="fr-input-line"><input type="text" placeholder="' + b.language.translate("Alternate Text") + '" tabIndex="1"></div><div class="fr-action-buttons"><button type="button" class="fr-command fr-submit" data-cmd="fileSetAlt" tabIndex="2">' + b.language.translate("Update") + "</button></div></div>";
            var e = {
                buttons: c,
                alt_layer: d
            }
              , f = b.popups.create("file.alt", e);
            return b.$wp && b.events.$on(b.$wp, "scroll.file-alt", function() {
                qa && b.popups.isVisible("file.alt") && N()
            }),
            f
        }
        function P(a) {
            if (qa) {
                var c = b.popups.get("file.alt");
                qa.attr("alt", a || c.find("input").val() || ""),
                c.find("input:focus").blur(),
                v(qa)
            }
        }
        function Q() {
            if (qa) {
                var a = b.popups.get("file.size");
                a.find('input[name="width"]').val(qa.get(0).style.width).trigger("change"),
                a.find('input[name="height"]').val(qa.get(0).style.height).trigger("change")
            }
        }
        function R() {
            var c = b.popups.get("file.size");
            c || (c = S()),
            r(),
            b.popups.refresh("file.size"),
            b.popups.setContainer("file.size", a(b.opts.scrollableContainer));
            var d = qa.offset().left + qa.width() / 2
              , e = qa.offset().top + qa.height();
            b.popups.show("file.size", d, e, qa.outerHeight())
        }
        function S(a) {
            if (a)
                return b.popups.onRefresh("file.size", Q),
                !0;
            var c = "";
            c = '<div class="fr-buttons">' + b.button.buildList(b.opts.fileSizeButtons) + "</div>";
            var d = "";
            d = '<div class="fr-file-size-layer fr-layer fr-active" id="fr-file-size-layer-' + b.id + '"><div class="fr-file-group"><div class="fr-input-line"><input type="text" name="width" placeholder="' + b.language.translate("Width") + '" tabIndex="1"></div><div class="fr-input-line"><input type="text" name="height" placeholder="' + b.language.translate("Height") + '" tabIndex="1"></div></div><div class="fr-action-buttons"><button type="button" class="fr-command fr-submit" data-cmd="fileSetSize" tabIndex="2">' + b.language.translate("Update") + "</button></div></div>";
            var e = {
                buttons: c,
                size_layer: d
            }
              , f = b.popups.create("file.size", e);
            return b.$wp && b.events.$on(b.$wp, "scroll.file-size", function() {
                qa && b.popups.isVisible("file.size") && R()
            }),
            f
        }
        function T(a, c) {
            if (qa) {
                var d = b.popups.get("file.size");
                qa.css("width", a || d.find('input[name="width"]').val()),
                qa.css("height", c || d.find('input[name="height"]').val()),
                d.find("input:focus").blur(),
                v(qa)
            }
        }*/
        function U(a) {
            var c, d, e = b.popups.get("file.insert");
            if (qa || b.opts.toolbarInline)
                qa && (d = qa.offset().top + qa.outerHeight());
            else {
                var f = b.$tb.find('.fr-command[data-cmd="insertfile"]');
                c = f.offset().left + f.outerWidth() / 2,
                d = f.offset().top + (b.opts.toolbarBottom ? 10 : f.outerHeight() - 10)
            }
            !qa && b.opts.toolbarInline && (d = e.offset().top - b.helpers.getPX(e.css("margin-top")),
            e.hasClass("fr-above") && (d += e.outerHeight())),
            e.find(".fr-layer").removeClass("fr-active"),
            e.find(".fr-" + a + "-layer").addClass("fr-active"),
            b.popups.show("file.insert", c, d, qa ? qa.outerHeight() : 0)
        }
        function V(a) {
            var c = b.popups.get("file.insert");
            c.find(".fr-file-upload-layer").hasClass("fr-active") && a.addClass("fr-active")
        }
        function W(a) {
            var c = b.popups.get("file.insert");
            c.find(".fr-file-by-url-layer").hasClass("fr-active") && a.addClass("fr-active")
        }
        function X() {
            var c;
            b.shared.$file_resizer ? (ra = b.shared.$file_resizer,
            ta = b.shared.$img_overlay,
            b.events.on("destroy", function() {
                ra.removeClass("fr-active").appendTo(a("body"))
            }, !0)) : (b.shared.$file_resizer = a('<div class="fr-file-resizer"></div>'),
            ra = b.shared.$file_resizer,
            b.events.$on(ra, "mousedown", function(a) {
                a.stopPropagation()
            }, !0),
            b.opts.fileResize && (ra.append(k("nw") + k("ne") + k("sw") + k("se")),
            b.shared.$img_overlay = a('<div class="fr-file-overlay"></div>'),
            ta = b.shared.$img_overlay,
            c = ra.get(0).ownerDocument,
            a(c).find("body").append(ta))),
            b.events.on("shared.destroy", function() {
                ra.html("").removeData().remove(),
                ra = null ,
                b.opts.fileResize && (ta.remove(),
                ta = null )
            }, !0),
            b.helpers.isMobile() || b.events.$on(a(b.o_win), "resize", function() {
                qa && !qa.hasClass("fr-uploading") ? ca(!0) : qa && (j(),
                la(),
                q(!1))
            }),
            b.opts.fileResize && (c = ra.get(0).ownerDocument,
            b.events.$on(ra, b._mousedown, ".fr-handler", l),
            b.events.$on(a(c), b._mousemove, m),
            b.events.$on(a(c.defaultView || c.parentWindow), b._mouseup, n),
            b.events.$on(ta, "mouseleave", n))
        }
        function Y(c) {
            c = c || qa,
            c && b.events.trigger("file.beforeRemove", [c]) !== !1 && (b.popups.hideAll(),
            ca(!0),
            c.get(0) == b.$el.get(0) ? c.removeAttr("src") : ("A" == c.get(0).parentNode.tagName ? (b.selection.setBefore(c.get(0).parentNode) || b.selection.setAfter(c.get(0).parentNode) || c.parent().after(a.FE.MARKERS),
            a(c.get(0).parentNode).remove()) : (b.selection.setBefore(c.get(0)) || b.selection.setAfter(c.get(0)) || c.after(a.FE.MARKERS),
            c.remove()),
            b.html.fillEmptyBlocks(),
            b.selection.restore()),
            b.undo.saveStep())
        }
        function Z() {
            if (K(),
            "IMG" == b.$el.get(0).tagName && b.$el.addClass("fr-view"),
            b.events.$on(b.$el, b.helpers.isMobile() && !b.helpers.isWindowsPhone() ? "touchend" : "click", "IMG" == b.$el.get(0).tagName ? null : 'img:not([contenteditable="false"])', ba),
            b.helpers.isMobile() && (b.events.$on(b.$el, "touchstart", "IMG" == b.$el.get(0).tagName ? null : 'img:not([contenteditable="false"])', function() {
                Ea = !1
            }),
            b.events.$on(b.$el, "touchmove", function() {
                Ea = !0
            })),
            b.events.on("window.keydown keydown", function(c) {
                var d = c.which;
                if (qa && (d == a.FE.KEYCODE.BACKSPACE || d == a.FE.KEYCODE.DELETE))
                    return c.preventDefault(),
                    c.stopPropagation(),
                    Y(),
                    !1;
                if (qa && d == a.FE.KEYCODE.ESC) {
                    var e = qa;
                    return ca(!0),
                    b.selection.setAfter(e.get(0)),
                    b.selection.restore(),
                    c.preventDefault(),
                    !1
                }
                return qa && !b.keys.ctrlKey(c) ? (c.preventDefault(),
                !1) : void 0
            }, !0),
            b.events.on("window.cut window.copy", function(c) {
                qa && (ma(),
                a.FE.copied_text = "\n",
                a.FE.copied_html = qa.get(0).outerHTML,
                "copy" == c.type ? setTimeout(function() {
                    v(qa)
                }) : (ca(!0),
                b.undo.saveStep(),
                setTimeout(function() {
                    b.undo.saveStep()
                }, 0)))
            }, !0),
            b.events.$on(a(b.o_win), "keydown", function(b) {
                var c = b.which;
                return qa && c == a.FE.KEYCODE.BACKSPACE ? (b.preventDefault(),
                !1) : void 0
            }),
            b.events.$on(b.$win, "keydown", function(b) {
                var c = b.which;
                qa && qa.hasClass("fr-uploading") && c == a.FE.KEYCODE.ESC && qa.trigger("abortUpload")
            }),
            b.events.on("destroy", function() {
                qa && qa.hasClass("fr-uploading") && qa.trigger("abortUpload")
            }),
            b.events.on("paste.before", _),
            b.events.on("paste.beforeCleanup", aa),
            b.events.on("paste.after", $),
            b.events.on("html.set", h),
            b.events.on("html.inserted", h),
            h(),
            b.events.on("destroy", function() {
                Da = []
            }),
            b.events.on("html.get", function(a) {
                return a = a.replace(/<(img)((?:[\w\W]*?))class="([\w\W]*?)(fr-uploading|fr-error)([\w\W]*?)"((?:[\w\W]*?))>/g, "")
            }),
            b.opts.fileOutputSize) {
                var c;
                b.events.on("html.beforeGet", function() {
                    c = b.$el.get(0).querySelectorAll("img");
                    for (var d = 0; d < c.length; d++)
                        c[d].setAttribute("width", a(c[d]).width()),
                        c[d].setAttribute("height", a(c[d]).height())
                }),
                b.events.on("html.afterGet", function() {
                    for (var a = 0; a < c.length; a++)
                        c[a].removeAttribute("width"),
                        c[a].removeAttribute("height")
                })
            }
            b.opts.iframe && b.events.on("file.loaded", b.size.syncIframe),
            b.$wp && (i(),
            b.events.on("contentChanged", i)),
            b.events.$on(a(b.o_win), "orientationchange.file", function() {
                setTimeout(function() {
                    var a = oa();
                    a && v(a)
                }, 0)
            }),
            p(!0),
            L(!0),
//            S(!0),
//            O(!0),
            b.events.on("node.remove", function(a) {
                return "IMG" == a.get(0).tagName ? (Y(a),
                !1) : void 0
            })
        }
        function $() {
            b.opts.filePaste ? b.$el.find("img[data-fr-file-pasted]").each(function(c, d) {
                if (b.opts.filePasteProcess) {
                    var f = b.opts.fileDefaultWidth;
                    f && "auto" != f && (f += b.opts.fileResizeWithPercent ? "%" : "px"),
                    a(d).css("width", f),
                    a(d).removeClass("fr-dii fr-dib fr-fir fr-fil").addClass((b.opts.fileDefaultDisplay ? "fr-di" + b.opts.fileDefaultDisplay[0] : "") + (b.opts.fileDefaultAlign && "center" != b.opts.fileDefaultAlign ? " fr-fi" + b.opts.fileDefaultAlign[0] : ""))
                }
                if (0 === d.src.indexOf("data:")) {
                    if (b.events.trigger("file.beforePasteUpload", [d]) === !1)
                        return !1;
                    qa = a(d),
                    j(),
//                    e(),
                    la(),
                    q(),
                    b.edit.off();
                    for (var g = atob(a(d).attr("src").split(",")[1]), h = [], i = 0; i < g.length; i++)
                        h.push(g.charCodeAt(i));
                    var k = new Blob([new Uint8Array(h)],{
                        type: "file/jpeg"
                    });
                    G([k]),
                    a(d).removeAttr("data-fr-file-pasted")
                } else
                    0 !== d.src.indexOf("http") ? (b.selection.save(),
                    a(d).remove(),
                    b.selection.restore()) : a(d).removeAttr("data-fr-file-pasted")
            }) : b.$el.find("img[data-fr-file-pasted]").remove()
        }
        function _(a) {
            if (a && a.clipboardData && a.clipboardData.items && a.clipboardData.items[0]) {
                var c = a.clipboardData.items[0].getAsFile();
                if (c) {
                    var d = new FileReader;
                    return d.onload = function(a) {
                        var c = a.target.result
                          , d = b.opts.fileDefaultWidth;
                        d && "auto" != d && (d += b.opts.fileResizeWithPercent ? "%" : "px"),
                        b.html.insert('<img data-fr-file-pasted="true" class="' + (b.opts.fileDefaultDisplay ? "fr-di" + b.opts.fileDefaultDisplay[0] : "") + (b.opts.fileDefaultAlign && "center" != b.opts.fileDefaultAlign ? " fr-fi" + b.opts.fileDefaultAlign[0] : "") + '" src="' + c + '"' + (d ? ' style="width: ' + d + ';"' : "") + ">"),
                        b.events.trigger("paste.after")
                    }
                    ,
                    d.readAsDataURL(c),
                    !1
                }
            }
        }
        function aa(a) {
            return a = a.replace(/<img /gi, '<img data-fr-file-pasted="true" ')
        }
        function ba(c) {
            if (a(this).parents('[contenteditable="false"]:not(.fr-element):not(body)').length)
                return !0;
            if (c && "touchend" == c.type && Ea)
                return !0;
            if (c && b.edit.isDisabled())
                return c.stopPropagation(),
                c.preventDefault(),
                !1;
            for (var d = 0; d < a.FE.INSTANCES.length; d++)
                a.FE.INSTANCES[d] != b && a.FE.INSTANCES[d].events.trigger("file.hideResizer");
            b.toolbar.disable(),
            c && (c.stopPropagation(),
            c.preventDefault()),
            b.helpers.isMobile() && (b.events.disableBlur(),
            b.$el.blur(),
            b.events.enableBlur()),
            b.opts.iframe && b.size.syncIframe(),
            qa = a(this),
            ma(),
            j(),
//           e(),
            b.selection.clear(),
            b.button.bulkRefresh(),
            b.events.trigger("video.hideResizer")
        }
        function ca(a) {
            qa && (fa() || a === !0) && (b.toolbar.enable(),
            ra.removeClass("fr-active"),
//            b.popups.hide("file.edit"),
            qa = null ,
            ea())
        }
        function da() {
            Fa = !0
        }
        function ea() {
            Fa = !1
        }
        function fa() {
            return Fa
        }
        function ga(a) {
            qa.removeClass("fr-fir fr-fil"),
            "left" == a ? qa.addClass("fr-fil") : "right" == a && qa.addClass("fr-fir"),
            j()
//            e()
        }
        function ha(a) {
            qa && (qa.hasClass("fr-fil") ? a.find("> *:first").replaceWith(b.icon.create("align-left")) : qa.hasClass("fr-fir") ? a.find("> *:first").replaceWith(b.icon.create("align-right")) : a.find("> *:first").replaceWith(b.icon.create("align-justify")))
        }
        function ia(a, b) {
            if (qa) {
                var c = "justify";
                qa.hasClass("fr-fil") ? c = "left" : qa.hasClass("fr-fir") && (c = "right"),
                b.find('.fr-command[data-param1="' + c + '"]').addClass("fr-active")
            }
        }
        function ja(a) {
            qa.removeClass("fr-dii fr-dib"),
            "inline" == a ? qa.addClass("fr-dii") : "block" == a && qa.addClass("fr-dib"),
            j()
//           e()
        }
        function ka(a, b) {
            var c = "block";
            qa.hasClass("fr-dii") && (c = "inline"),
            b.find('.fr-command[data-param1="' + c + '"]').addClass("fr-active")
        }
        function la() {
            var c = b.popups.get("file.insert");
            c || (c = L()),
            b.popups.isVisible("file.insert") || (r(),
            b.popups.refresh("file.insert"),
            b.popups.setContainer("file.insert", a(b.opts.scrollableContainer)));
            var d = qa.offset().left + qa.width() / 2
              , e = qa.offset().top + qa.height();
            b.popups.show("file.insert", d, e, qa.outerHeight())
        }
        function ma() {
            if (qa) {
                b.selection.clear();
                var a = b.doc.createRange();
                a.selectNode(qa.get(0));
                var c = b.selection.get();
                c.addRange(a)
            }
        }
        function na() {
            qa ? (a(".fr-popup input:focus").blur(),
            v(qa)) : (b.events.disableBlur(),
            b.selection.restore(),
            b.events.enableBlur(),
            b.popups.hide("file.insert"),
            b.toolbar.showInline())
        }
        function oa() {
            return qa
        }
        function pa(a, c, d) {
            if ("undefined" == typeof c && (c = b.opts.fileStyles),
            "undefined" == typeof d && (d = b.opts.fileMultipleStyles),
            !qa)
                return !1;
            if (!d) {
                var e = Object.keys(c);
                e.splice(e.indexOf(a), 1),
                qa.removeClass(e.join(" "))
            }
            qa.toggleClass(a),
            v(qa)
        }
        var qa, ra, sa, ta, ua = !1, va = 1, wa = 2, xa = 3, ya = 4, za = 5, Aa = 6, Ba = 7, Ca = {};
        Ca[va] = "file cannot be loaded from the passed link.",
        Ca[wa] = "No link in upload response.",
        Ca[xa] = "Error during file upload.",
        Ca[ya] = "Parsing response failed.",
        Ca[za] = "File is too large.",
        Ca[Aa] = "file file type is invalid.",
        Ca[Ba] = "Files can be uploaded only to same domain in IE 8 and IE 9.";
        var Da, Ea, Fa = !1;
        return {
            _init: Z,
            showInsertPopup: d,
            showLayer: U,
            refreshUploadButton: V,
            refreshByURLButton: W,
            upload: G,
            insertByURL: u,
            align: ga,
            refreshAlign: ha,
            refreshAlignOnShow: ia,
            display: ja,
            refreshDisplayOnShow: ka,
            replace: la,
            back: na,
            get: oa,
            insert: x,
            showProgressBar: q,
            remove: Y,
            hideProgressBar: r,
            applyStyle: pa
          //  showAltPopup: N,
          //  showSizePopup: R,
          //  setAlt: P,
          //  setSize: T,
        //    exitEdit: ca,
         //   edit: v
        }
    }
    ,
    a.FE.DefineIcon("insertfile", {
        NAME: "file"
    }),
    a.FE.RegisterShortcut(a.FE.KEYCODE.P, "insertfile", null , "P"),
    a.FE.RegisterCommand("insertfile", {
        title: "Insert file",
        undo: !1,
        focus: !0,
        refreshAfterCallback: !1,
        popup: !0,
        callback: function() {
            this.popups.isVisible("file.insert") ? (this.$el.find(".fr-marker") && (this.events.disableBlur(),
            this.selection.restore()),
            this.popups.hide("file.insert")) : this.file.showInsertPopup()
        },
        plugin: "file"
    }),
    a.FE.DefineIcon("fileUpload", {
        NAME: "upload"
    }),
    a.FE.RegisterCommand("fileUpload", {
        title: "Upload file",
        undo: !1,
        focus: !1,
        callback: function() {
            this.file.showLayer("file-upload")
        },
        refresh: function(a) {
            this.file.refreshUploadButton(a)
        }
    }),
    a.FE.DefineIcon("fileByURL", {
        NAME: "link"
    }),
    a.FE.RegisterCommand("fileByURL", {
        title: "By URL",
        undo: !1,
        focus: !1,
        callback: function() {
            this.file.showLayer("file-by-url")
        },
        refresh: function(a) {
            this.file.refreshByURLButton(a)
        }
    }),
    a.FE.RegisterCommand("fileInsertByURL", {
        title: "Insert file",
        undo: !0,
        refreshAfterCallback: !1,
        callback: function() {
            this.file.insertByURL()
        },
        refresh: function(a) {
            var b = this.file.get();
            b ? a.text(this.language.translate("Replace")) : a.text(this.language.translate("Insert"))
        }
    })
/*    a.FE.DefineIcon("fileDisplay", {
        NAME: "star"
    }),
    a.FE.RegisterCommand("fileDisplay", {
        title: "Display",
        type: "dropdown",
        options: {
            inline: "Inline",
            block: "Break Text"
        },
        callback: function(a, b) {
            this.file.display(b)
        },
        refresh: function(a) {
            this.opts.fileTextNear || a.addClass("fr-hidden")
        },
        refreshOnShow: function(a, b) {
            this.file.refreshDisplayOnShow(a, b)
        }
    }),
    a.FE.ICONS.align || (a.FE.DefineIcon("align", {
        NAME: "align-left"
    }),
    a.FE.DefineIcon("align-left", {
        NAME: "align-left"
    }),
    a.FE.DefineIcon("align-right", {
        NAME: "align-right"
    }),
    a.FE.DefineIcon("align-center", {
        NAME: "align-center"
    }),
    a.FE.DefineIcon("align-justify", {
        NAME: "align-justify"
    })),
    a.FE.DefineIcon("fileAlign", {
        NAME: "align-center"
    }),
    a.FE.RegisterCommand("fileAlign", {
        type: "dropdown",
        title: "Align",
        options: {
            left: "Align Left",
            justify: "None",
            right: "Align Right"
        },
        html: function() {
            var b = '<ul class="fr-dropdown-list">'
              , c = a.FE.COMMANDS.fileAlign.options;
            for (var d in c)
                c.hasOwnProperty(d) && (b += '<li><a class="fr-command fr-title" data-cmd="fileAlign" data-param1="' + d + '" title="' + this.language.translate(c[d]) + '">' + this.icon.create("align-" + d) + "</a></li>");
            return b += "</ul>"
        },
        callback: function(a, b) {
            this.file.align(b)
        },
        refresh: function(a) {
            this.file.refreshAlign(a)
        },
        refreshOnShow: function(a, b) {
            this.file.refreshAlignOnShow(a, b)
        }
    }),
    a.FE.DefineIcon("fileReplace", {
        NAME: "exchange"
    }),
    a.FE.RegisterCommand("fileReplace", {
        title: "Replace",
        undo: !1,
        focus: !1,
        refreshAfterCallback: !1,
        callback: function() {
            this.file.replace()
        }
    }),
    a.FE.DefineIcon("fileRemove", {
        NAME: "trash"
    }),
    a.FE.RegisterCommand("fileRemove", {
        title: "Remove",
        callback: function() {
            this.file.remove()
        }
    }),
    a.FE.DefineIcon("fileBack", {
        NAME: "arrow-left"
    }),
    a.FE.RegisterCommand("fileBack", {
        title: "Back",
        undo: !1,
        focus: !1,
        back: !0,
        callback: function() {
            this.file.back()
        },
        refresh: function(a) {
            var b = this.file.get();
            b || this.opts.toolbarInline ? (a.removeClass("fr-hidden"),
            a.next(".fr-separator").removeClass("fr-hidden")) : (a.addClass("fr-hidden"),
            a.next(".fr-separator").addClass("fr-hidden"))
        }
    }),
    a.FE.RegisterCommand("fileDismissError", {
        title: "OK",
        undo: !1,
        callback: function() {
            this.file.hideProgressBar(!0)
        }
    }),
    a.FE.DefineIcon("fileStyle", {
        NAME: "magic"
    }),
    a.FE.RegisterCommand("fileStyle", {
        title: "Style",
        type: "dropdown",
        html: function() {
            var a = '<ul class="fr-dropdown-list">'
              , b = this.opts.fileStyles;
            for (var c in b)
                b.hasOwnProperty(c) && (a += '<li><a class="fr-command" data-cmd="fileStyle" data-param1="' + c + '">' + this.language.translate(b[c]) + "</a></li>");
            return a += "</ul>"
        },
        callback: function(a, b) {
            this.file.applyStyle(b)
        },
        refreshOnShow: function(b, c) {
            var d = this.file.get();
            d && c.find(".fr-command").each(function() {
                var b = a(this).data("param1");
                a(this).toggleClass("fr-active", d.hasClass(b))
            })
        }
    }),
    a.FE.DefineIcon("fileAlt", {
        NAME: "info"
    }),
    a.FE.RegisterCommand("fileAlt", {
        undo: !1,
        focus: !1,
        title: "Alternate Text",
        callback: function() {
            this.file.showAltPopup()
        }
    }),
    a.FE.RegisterCommand("fileSetAlt", {
        undo: !0,
        focus: !1,
        title: "Update",
        refreshAfterCallback: !1,
        callback: function() {
            this.file.setAlt()
        }
    }),
    a.FE.DefineIcon("fileSize", {
        NAME: "arrows-alt"
    }),
    a.FE.RegisterCommand("fileSize", {
        undo: !1,
        focus: !1,
        title: "Change Size",
        callback: function() {
            this.file.showSizePopup()
        }
    }),
    a.FE.RegisterCommand("fileSetSize", {
        undo: !0,
        focus: !1,
        title: "Update",
        refreshAfterCallback: !1,
        callback: function() {
            this.file.setSize()
        }
    })*/
});
