/*
 Highstock JS v6.0.7 (2018-02-16)

 (c) 2009-2016 Torstein Honsi

 License: www.highcharts.com/license
*/
(function (T, L) {
    "object" === typeof module && module.exports ? module.exports = T.document ? L(T) : L : T.Highcharts = L(T)
})("undefined" !== typeof window ? window : this, function (T) {
    var L = function () {
        var a = "undefined" === typeof T ? window : T, C = a.document, E = a.navigator && a.navigator.userAgent || "",
            F = C && C.createElementNS && !!C.createElementNS("http://www.w3.org/2000/svg", "svg").createSVGRect,
            r = /(edge|msie|trident)/i.test(E) && !a.opera, m = -1 !== E.indexOf("Firefox"),
            l = -1 !== E.indexOf("Chrome"), w = m && 4 > parseInt(E.split("Firefox/")[1],
            10);
        return a.Highcharts ? a.Highcharts.error(16, !0) : {
            product: "Highstock",
            version: "6.0.7",
            deg2rad: 2 * Math.PI / 360,
            doc: C,
            hasBidiBug: w,
            hasTouch: C && void 0 !== C.documentElement.ontouchstart,
            isMS: r,
            isWebKit: -1 !== E.indexOf("AppleWebKit"),
            isFirefox: m,
            isChrome: l,
            isSafari: !l && -1 !== E.indexOf("Safari"),
            isTouchDevice: /(Mobile|Android|Windows Phone)/.test(E),
            SVG_NS: "http://www.w3.org/2000/svg",
            chartCount: 0,
            seriesTypes: {},
            symbolSizes: {},
            svg: F,
            win: a,
            marginNames: ["plotTop", "marginRight", "marginBottom", "plotLeft"],
            noop: function () {
            },
            charts: []
        }
    }();
    (function (a) {
        a.timers = [];
        var C = a.charts, E = a.doc, F = a.win;
        a.error = function (r, m) {
            r = a.isNumber(r) ? "Highcharts error #" + r + ": www.highcharts.com/errors/" + r : r;
            if (m) throw Error(r);
            F.console && console.log(r)
        };
        a.Fx = function (a, m, l) {
            this.options = m;
            this.elem = a;
            this.prop = l
        };
        a.Fx.prototype = {
            dSetter: function () {
                var a = this.paths[0], m = this.paths[1], l = [], w = this.now, p = a.length, v;
                if (1 === w) l = this.toD; else if (p === m.length && 1 > w) for (; p--;) v = parseFloat(a[p]), l[p] = isNaN(v) ? m[p] : w * parseFloat(m[p] - v) + v; else l = m;
                this.elem.attr("d",
                    l, null, !0)
            }, update: function () {
                var a = this.elem, m = this.prop, l = this.now, w = this.options.step;
                if (this[m + "Setter"]) this[m + "Setter"](); else a.attr ? a.element && a.attr(m, l, null, !0) : a.style[m] = l + this.unit;
                w && w.call(a, l, this)
            }, run: function (r, m, l) {
                var w = this, p = w.options, v = function (a) {
                    return v.stopped ? !1 : w.step(a)
                }, B = F.requestAnimationFrame || function (a) {
                    setTimeout(a, 13)
                }, e = function () {
                    for (var c = 0; c < a.timers.length; c++) a.timers[c]() || a.timers.splice(c--, 1);
                    a.timers.length && B(e)
                };
                r === m ? (delete p.curAnim[this.prop],
                p.complete && 0 === a.keys(p.curAnim).length && p.complete.call(this.elem)) : (this.startTime = +new Date, this.start = r, this.end = m, this.unit = l, this.now = this.start, this.pos = 0, v.elem = this.elem, v.prop = this.prop, v() && 1 === a.timers.push(v) && B(e))
            }, step: function (r) {
                var m = +new Date, l, w = this.options, p = this.elem, v = w.complete, B = w.duration, e = w.curAnim;
                p.attr && !p.element ? r = !1 : r || m >= B + this.startTime ? (this.now = this.end, this.pos = 1, this.update(), l = e[this.prop] = !0, a.objectEach(e, function (a) {
                    !0 !== a && (l = !1)
                }), l && v && v.call(p), r =
                    !1) : (this.pos = w.easing((m - this.startTime) / B), this.now = this.start + (this.end - this.start) * this.pos, this.update(), r = !0);
                return r
            }, initPath: function (r, m, l) {
                function w(a) {
                    var g, b;
                    for (t = a.length; t--;) g = "M" === a[t] || "L" === a[t], b = /[a-zA-Z]/.test(a[t + 3]), g && b && a.splice(t + 1, 0, a[t + 1], a[t + 2], a[t + 1], a[t + 2])
                }

                function p(a, c) {
                    for (; a.length < g;) {
                        a[0] = c[g - a.length];
                        var e = a.slice(0, b);
                        [].splice.apply(a, [0, 0].concat(e));
                        f && (e = a.slice(a.length - b), [].splice.apply(a, [a.length, 0].concat(e)), t--)
                    }
                    a[0] = "M"
                }

                function v(a, c) {
                    for (var e =
                        (g - a.length) / b; 0 < e && e--;) n = a.slice().splice(a.length / u - b, b * u), n[0] = c[g - b - e * b], k && (n[b - 6] = n[b - 2], n[b - 5] = n[b - 1]), [].splice.apply(a, [a.length / u, 0].concat(n)), f && e--
                }

                m = m || "";
                var B, e = r.startX, c = r.endX, k = -1 < m.indexOf("C"), b = k ? 7 : 3, g, n, t;
                m = m.split(" ");
                l = l.slice();
                var f = r.isArea, u = f ? 2 : 1, D;
                k && (w(m), w(l));
                if (e && c) {
                    for (t = 0; t < e.length; t++) if (e[t] === c[0]) {
                        B = t;
                        break
                    } else if (e[0] === c[c.length - e.length + t]) {
                        B = t;
                        D = !0;
                        break
                    }
                    void 0 === B && (m = [])
                }
                m.length && a.isNumber(B) && (g = l.length + B * u * b, D ? (p(m, l), v(l, m)) : (p(l, m), v(m,
                    l)));
                return [m, l]
            }
        };
        a.Fx.prototype.fillSetter = a.Fx.prototype.strokeSetter = function () {
            this.elem.attr(this.prop, a.color(this.start).tweenTo(a.color(this.end), this.pos), null, !0)
        };
        a.merge = function () {
            var r, m = arguments, l, w = {}, p = function (l, r) {
                "object" !== typeof l && (l = {});
                a.objectEach(r, function (e, c) {
                    !a.isObject(e, !0) || a.isClass(e) || a.isDOMElement(e) ? l[c] = r[c] : l[c] = p(l[c] || {}, e)
                });
                return l
            };
            !0 === m[0] && (w = m[1], m = Array.prototype.slice.call(m, 2));
            l = m.length;
            for (r = 0; r < l; r++) w = p(w, m[r]);
            return w
        };
        a.pInt = function (a,
                           m) {
            return parseInt(a, m || 10)
        };
        a.isString = function (a) {
            return "string" === typeof a
        };
        a.isArray = function (a) {
            a = Object.prototype.toString.call(a);
            return "[object Array]" === a || "[object Array Iterator]" === a
        };
        a.isObject = function (r, m) {
            return !!r && "object" === typeof r && (!m || !a.isArray(r))
        };
        a.isDOMElement = function (r) {
            return a.isObject(r) && "number" === typeof r.nodeType
        };
        a.isClass = function (r) {
            var m = r && r.constructor;
            return !(!a.isObject(r, !0) || a.isDOMElement(r) || !m || !m.name || "Object" === m.name)
        };
        a.isNumber = function (a) {
            return "number" ===
                typeof a && !isNaN(a) && Infinity > a && -Infinity < a
        };
        a.erase = function (a, m) {
            for (var l = a.length; l--;) if (a[l] === m) {
                a.splice(l, 1);
                break
            }
        };
        a.defined = function (a) {
            return void 0 !== a && null !== a
        };
        a.attr = function (r, m, l) {
            var w;
            a.isString(m) ? a.defined(l) ? r.setAttribute(m, l) : r && r.getAttribute && (w = r.getAttribute(m)) : a.defined(m) && a.isObject(m) && a.objectEach(m, function (a, l) {
                r.setAttribute(l, a)
            });
            return w
        };
        a.splat = function (r) {
            return a.isArray(r) ? r : [r]
        };
        a.syncTimeout = function (a, m, l) {
            if (m) return setTimeout(a, m, l);
            a.call(0,
                l)
        };
        a.extend = function (a, m) {
            var l;
            a || (a = {});
            for (l in m) a[l] = m[l];
            return a
        };
        a.pick = function () {
            var a = arguments, m, l, w = a.length;
            for (m = 0; m < w; m++) if (l = a[m], void 0 !== l && null !== l) return l
        };
        a.css = function (r, m) {
            a.isMS && !a.svg && m && void 0 !== m.opacity && (m.filter = "alpha(opacity\x3d" + 100 * m.opacity + ")");
            a.extend(r.style, m)
        };
        a.createElement = function (r, m, l, w, p) {
            r = E.createElement(r);
            var v = a.css;
            m && a.extend(r, m);
            p && v(r, {padding: 0, border: "none", margin: 0});
            l && v(r, l);
            w && w.appendChild(r);
            return r
        };
        a.extendClass = function (r,
                                  m) {
            var l = function () {
            };
            l.prototype = new r;
            a.extend(l.prototype, m);
            return l
        };
        a.pad = function (a, m, l) {
            return Array((m || 2) + 1 - String(a).length).join(l || 0) + a
        };
        a.relativeLength = function (a, m, l) {
            return /%$/.test(a) ? m * parseFloat(a) / 100 + (l || 0) : parseFloat(a)
        };
        a.wrap = function (a, m, l) {
            var w = a[m];
            a[m] = function () {
                var a = Array.prototype.slice.call(arguments), v = arguments, B = this;
                B.proceed = function () {
                    w.apply(B, arguments.length ? arguments : v)
                };
                a.unshift(w);
                a = l.apply(this, a);
                B.proceed = null;
                return a
            }
        };
        a.formatSingle = function (r,
                                   m, l) {
            var w = /\.([0-9])/, p = a.defaultOptions.lang;
            /f$/.test(r) ? (l = (l = r.match(w)) ? l[1] : -1, null !== m && (m = a.numberFormat(m, l, p.decimalPoint, -1 < r.indexOf(",") ? p.thousandsSep : ""))) : m = (l || a.time).dateFormat(r, m);
            return m
        };
        a.format = function (r, m, l) {
            for (var w = "{", p = !1, v, B, e, c, k = [], b; r;) {
                w = r.indexOf(w);
                if (-1 === w) break;
                v = r.slice(0, w);
                if (p) {
                    v = v.split(":");
                    B = v.shift().split(".");
                    c = B.length;
                    b = m;
                    for (e = 0; e < c; e++) b && (b = b[B[e]]);
                    v.length && (b = a.formatSingle(v.join(":"), b, l));
                    k.push(b)
                } else k.push(v);
                r = r.slice(w + 1);
                w = (p =
                    !p) ? "}" : "{"
            }
            k.push(r);
            return k.join("")
        };
        a.getMagnitude = function (a) {
            return Math.pow(10, Math.floor(Math.log(a) / Math.LN10))
        };
        a.normalizeTickInterval = function (r, m, l, w, p) {
            var v, B = r;
            l = a.pick(l, 1);
            v = r / l;
            m || (m = p ? [1, 1.2, 1.5, 2, 2.5, 3, 4, 5, 6, 8, 10] : [1, 2, 2.5, 5, 10], !1 === w && (1 === l ? m = a.grep(m, function (a) {
                return 0 === a % 1
            }) : .1 >= l && (m = [1 / l])));
            for (w = 0; w < m.length && !(B = m[w], p && B * l >= r || !p && v <= (m[w] + (m[w + 1] || m[w])) / 2); w++) ;
            return B = a.correctFloat(B * l, -Math.round(Math.log(.001) / Math.LN10))
        };
        a.stableSort = function (a, m) {
            var l =
                a.length, w, p;
            for (p = 0; p < l; p++) a[p].safeI = p;
            a.sort(function (a, p) {
                w = m(a, p);
                return 0 === w ? a.safeI - p.safeI : w
            });
            for (p = 0; p < l; p++) delete a[p].safeI
        };
        a.arrayMin = function (a) {
            for (var m = a.length, l = a[0]; m--;) a[m] < l && (l = a[m]);
            return l
        };
        a.arrayMax = function (a) {
            for (var m = a.length, l = a[0]; m--;) a[m] > l && (l = a[m]);
            return l
        };
        a.destroyObjectProperties = function (r, m) {
            a.objectEach(r, function (a, w) {
                a && a !== m && a.destroy && a.destroy();
                delete r[w]
            })
        };
        a.discardElement = function (r) {
            var m = a.garbageBin;
            m || (m = a.createElement("div"));
            r && m.appendChild(r);
            m.innerHTML = ""
        };
        a.correctFloat = function (a, m) {
            return parseFloat(a.toPrecision(m || 14))
        };
        a.setAnimation = function (r, m) {
            m.renderer.globalAnimation = a.pick(r, m.options.chart.animation, !0)
        };
        a.animObject = function (r) {
            return a.isObject(r) ? a.merge(r) : {duration: r ? 500 : 0}
        };
        a.timeUnits = {
            millisecond: 1,
            second: 1E3,
            minute: 6E4,
            hour: 36E5,
            day: 864E5,
            week: 6048E5,
            month: 24192E5,
            year: 314496E5
        };
        a.numberFormat = function (r, m, l, w) {
            r = +r || 0;
            m = +m;
            var p = a.defaultOptions.lang, v = (r.toString().split(".")[1] || "").split("e")[0].length, B,
                e, c = r.toString().split("e");
            -1 === m ? m = Math.min(v, 20) : a.isNumber(m) ? m && c[1] && 0 > c[1] && (B = m + +c[1], 0 <= B ? (c[0] = (+c[0]).toExponential(B).split("e")[0], m = B) : (c[0] = c[0].split(".")[0] || 0, r = 20 > m ? (c[0] * Math.pow(10, c[1])).toFixed(m) : 0, c[1] = 0)) : m = 2;
            e = (Math.abs(c[1] ? c[0] : r) + Math.pow(10, -Math.max(m, v) - 1)).toFixed(m);
            v = String(a.pInt(e));
            B = 3 < v.length ? v.length % 3 : 0;
            l = a.pick(l, p.decimalPoint);
            w = a.pick(w, p.thousandsSep);
            r = (0 > r ? "-" : "") + (B ? v.substr(0, B) + w : "");
            r += v.substr(B).replace(/(\d{3})(?=\d)/g, "$1" + w);
            m && (r += l + e.slice(-m));
            c[1] && 0 !== +r && (r += "e" + c[1]);
            return r
        };
        Math.easeInOutSine = function (a) {
            return -.5 * (Math.cos(Math.PI * a) - 1)
        };
        a.getStyle = function (r, m, l) {
            if ("width" === m) return Math.min(r.offsetWidth, r.scrollWidth) - a.getStyle(r, "padding-left") - a.getStyle(r, "padding-right");
            if ("height" === m) return Math.min(r.offsetHeight, r.scrollHeight) - a.getStyle(r, "padding-top") - a.getStyle(r, "padding-bottom");
            F.getComputedStyle || a.error(27, !0);
            if (r = F.getComputedStyle(r, void 0)) r = r.getPropertyValue(m), a.pick(l, "opacity" !== m) && (r = a.pInt(r));
            return r
        };
        a.inArray = function (r, m) {
            return (a.indexOfPolyfill || Array.prototype.indexOf).call(m, r)
        };
        a.grep = function (r, m) {
            return (a.filterPolyfill || Array.prototype.filter).call(r, m)
        };
        a.find = Array.prototype.find ? function (a, m) {
            return a.find(m)
        } : function (a, m) {
            var l, w = a.length;
            for (l = 0; l < w; l++) if (m(a[l], l)) return a[l]
        };
        a.map = function (a, m) {
            for (var l = [], w = 0, p = a.length; w < p; w++) l[w] = m.call(a[w], a[w], w, a);
            return l
        };
        a.keys = function (r) {
            return (a.keysPolyfill || Object.keys).call(void 0, r)
        };
        a.reduce = function (r, m, l) {
            return (a.reducePolyfill ||
                Array.prototype.reduce).call(r, m, l)
        };
        a.offset = function (a) {
            var m = E.documentElement;
            a = a.parentElement ? a.getBoundingClientRect() : {top: 0, left: 0};
            return {
                top: a.top + (F.pageYOffset || m.scrollTop) - (m.clientTop || 0),
                left: a.left + (F.pageXOffset || m.scrollLeft) - (m.clientLeft || 0)
            }
        };
        a.stop = function (r, m) {
            for (var l = a.timers.length; l--;) a.timers[l].elem !== r || m && m !== a.timers[l].prop || (a.timers[l].stopped = !0)
        };
        a.each = function (r, m, l) {
            return (a.forEachPolyfill || Array.prototype.forEach).call(r, m, l)
        };
        a.objectEach = function (a,
                                 m, l) {
            for (var w in a) a.hasOwnProperty(w) && m.call(l, a[w], w, a)
        };
        a.isPrototype = function (r) {
            return r === a.Axis.prototype || r === a.Chart.prototype || r === a.Point.prototype || r === a.Series.prototype || r === a.Tick.prototype
        };
        a.addEvent = function (r, m, l) {
            var w, p = r.addEventListener || a.addEventListenerPolyfill;
            w = a.isPrototype(r) ? "protoEvents" : "hcEvents";
            w = r[w] = r[w] || {};
            p && p.call(r, m, l, !1);
            w[m] || (w[m] = []);
            w[m].push(l);
            return function () {
                a.removeEvent(r, m, l)
            }
        };
        a.removeEvent = function (r, m, l) {
            function w(e, c) {
                var k = r.removeEventListener ||
                    a.removeEventListenerPolyfill;
                k && k.call(r, e, c, !1)
            }

            function p(e) {
                var c, k;
                r.nodeName && (m ? (c = {}, c[m] = !0) : c = e, a.objectEach(c, function (a, g) {
                    if (e[g]) for (k = e[g].length; k--;) w(g, e[g][k])
                }))
            }

            var v, B;
            a.each(["protoEvents", "hcEvents"], function (e) {
                var c = r[e];
                c && (m ? (v = c[m] || [], l ? (B = a.inArray(l, v), -1 < B && (v.splice(B, 1), c[m] = v), w(m, l)) : (p(c), c[m] = [])) : (p(c), r[e] = {}))
            })
        };
        a.fireEvent = function (r, m, l, w) {
            var p, v, B, e, c;
            l = l || {};
            E.createEvent && (r.dispatchEvent || r.fireEvent) ? (p = E.createEvent("Events"), p.initEvent(m, !0, !0),
                a.extend(p, l), r.dispatchEvent ? r.dispatchEvent(p) : r.fireEvent(m, p)) : a.each(["protoEvents", "hcEvents"], function (k) {
                if (r[k]) for (v = r[k][m] || [], B = v.length, l.target || a.extend(l, {
                    preventDefault: function () {
                        l.defaultPrevented = !0
                    }, target: r, type: m
                }), e = 0; e < B; e++) (c = v[e]) && !1 === c.call(r, l) && l.preventDefault()
            });
            w && !l.defaultPrevented && w(l)
        };
        a.animate = function (r, m, l) {
            var w, p = "", v, B, e;
            a.isObject(l) || (e = arguments, l = {duration: e[2], easing: e[3], complete: e[4]});
            a.isNumber(l.duration) || (l.duration = 400);
            l.easing = "function" ===
            typeof l.easing ? l.easing : Math[l.easing] || Math.easeInOutSine;
            l.curAnim = a.merge(m);
            a.objectEach(m, function (c, e) {
                a.stop(r, e);
                B = new a.Fx(r, l, e);
                v = null;
                "d" === e ? (B.paths = B.initPath(r, r.d, m.d), B.toD = m.d, w = 0, v = 1) : r.attr ? w = r.attr(e) : (w = parseFloat(a.getStyle(r, e)) || 0, "opacity" !== e && (p = "px"));
                v || (v = c);
                v && v.match && v.match("px") && (v = v.replace(/px/g, ""));
                B.run(w, v, p)
            })
        };
        a.seriesType = function (r, m, l, w, p) {
            var v = a.getOptions(), B = a.seriesTypes;
            v.plotOptions[r] = a.merge(v.plotOptions[m], l);
            B[r] = a.extendClass(B[m] || function () {
            },
                w);
            B[r].prototype.type = r;
            p && (B[r].prototype.pointClass = a.extendClass(a.Point, p));
            return B[r]
        };
        a.uniqueKey = function () {
            var a = Math.random().toString(36).substring(2, 9), m = 0;
            return function () {
                return "highcharts-" + a + "-" + m++
            }
        }();
        F.jQuery && (F.jQuery.fn.highcharts = function () {
            var r = [].slice.call(arguments);
            if (this[0]) return r[0] ? (new (a[a.isString(r[0]) ? r.shift() : "Chart"])(this[0], r[0], r[1]), this) : C[a.attr(this[0], "data-highcharts-chart")]
        })
    })(L);
    (function (a) {
        var C = a.each, E = a.isNumber, F = a.map, r = a.merge, m = a.pInt;
        a.Color = function (l) {
            if (!(this instanceof a.Color)) return new a.Color(l);
            this.init(l)
        };
        a.Color.prototype = {
            parsers: [{
                regex: /rgba\(\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]?(?:\.[0-9]+)?)\s*\)/,
                parse: function (a) {
                    return [m(a[1]), m(a[2]), m(a[3]), parseFloat(a[4], 10)]
                }
            }, {
                regex: /rgb\(\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*\)/, parse: function (a) {
                    return [m(a[1]), m(a[2]), m(a[3]), 1]
                }
            }], names: {none: "rgba(255,255,255,0)", white: "#ffffff", black: "#000000"}, init: function (l) {
                var m,
                    p, v, B;
                if ((this.input = l = this.names[l && l.toLowerCase ? l.toLowerCase() : ""] || l) && l.stops) this.stops = F(l.stops, function (e) {
                    return new a.Color(e[1])
                }); else if (l && l.charAt && "#" === l.charAt() && (m = l.length, l = parseInt(l.substr(1), 16), 7 === m ? p = [(l & 16711680) >> 16, (l & 65280) >> 8, l & 255, 1] : 4 === m && (p = [(l & 3840) >> 4 | (l & 3840) >> 8, (l & 240) >> 4 | l & 240, (l & 15) << 4 | l & 15, 1])), !p) for (v = this.parsers.length; v-- && !p;) B = this.parsers[v], (m = B.regex.exec(l)) && (p = B.parse(m));
                this.rgba = p || []
            }, get: function (a) {
                var l = this.input, p = this.rgba, v;
                this.stops ?
                    (v = r(l), v.stops = [].concat(v.stops), C(this.stops, function (p, e) {
                        v.stops[e] = [v.stops[e][0], p.get(a)]
                    })) : v = p && E(p[0]) ? "rgb" === a || !a && 1 === p[3] ? "rgb(" + p[0] + "," + p[1] + "," + p[2] + ")" : "a" === a ? p[3] : "rgba(" + p.join(",") + ")" : l;
                return v
            }, brighten: function (a) {
                var l, p = this.rgba;
                if (this.stops) C(this.stops, function (p) {
                    p.brighten(a)
                }); else if (E(a) && 0 !== a) for (l = 0; 3 > l; l++) p[l] += m(255 * a), 0 > p[l] && (p[l] = 0), 255 < p[l] && (p[l] = 255);
                return this
            }, setOpacity: function (a) {
                this.rgba[3] = a;
                return this
            }, tweenTo: function (a, m) {
                var p = this.rgba,
                    l = a.rgba;
                l.length && p && p.length ? (a = 1 !== l[3] || 1 !== p[3], m = (a ? "rgba(" : "rgb(") + Math.round(l[0] + (p[0] - l[0]) * (1 - m)) + "," + Math.round(l[1] + (p[1] - l[1]) * (1 - m)) + "," + Math.round(l[2] + (p[2] - l[2]) * (1 - m)) + (a ? "," + (l[3] + (p[3] - l[3]) * (1 - m)) : "") + ")") : m = a.input || "none";
                return m
            }
        };
        a.color = function (l) {
            return new a.Color(l)
        }
    })(L);
    (function (a) {
        var C, E, F = a.addEvent, r = a.animate, m = a.attr, l = a.charts, w = a.color, p = a.css, v = a.createElement,
            B = a.defined, e = a.deg2rad, c = a.destroyObjectProperties, k = a.doc, b = a.each, g = a.extend,
            n = a.erase, t = a.grep,
            f = a.hasTouch, u = a.inArray, D = a.isArray, q = a.isFirefox, A = a.isMS, y = a.isObject, H = a.isString,
            I = a.isWebKit, J = a.merge, d = a.noop, z = a.objectEach, G = a.pick, h = a.pInt, x = a.removeEvent,
            N = a.stop, O = a.svg, P = a.SVG_NS, M = a.symbolSizes, Q = a.win;
        C = a.SVGElement = function () {
            return this
        };
        g(C.prototype, {
            opacity: 1,
            SVG_NS: P,
            textProps: "direction fontSize fontWeight fontFamily fontStyle color lineHeight width textAlign textDecoration textOverflow textOutline".split(" "),
            init: function (a, h) {
                this.element = "span" === h ? v(h) : k.createElementNS(this.SVG_NS,
                    h);
                this.renderer = a
            },
            animate: function (h, d, x) {
                d = a.animObject(G(d, this.renderer.globalAnimation, !0));
                0 !== d.duration ? (x && (d.complete = x), r(this, h, d)) : (this.attr(h, null, x), d.step && d.step.call(this));
                return this
            },
            colorGradient: function (h, d, x) {
                var K = this.renderer, g, f, c, e, n, q, t, u, A, y, G = [], k;
                h.radialGradient ? f = "radialGradient" : h.linearGradient && (f = "linearGradient");
                f && (c = h[f], n = K.gradients, t = h.stops, y = x.radialReference, D(c) && (h[f] = c = {
                    x1: c[0],
                    y1: c[1],
                    x2: c[2],
                    y2: c[3],
                    gradientUnits: "userSpaceOnUse"
                }), "radialGradient" ===
                f && y && !B(c.gradientUnits) && (e = c, c = J(c, K.getRadialAttr(y, e), {gradientUnits: "userSpaceOnUse"})), z(c, function (a, h) {
                    "id" !== h && G.push(h, a)
                }), z(t, function (a) {
                    G.push(a)
                }), G = G.join(","), n[G] ? y = n[G].attr("id") : (c.id = y = a.uniqueKey(), n[G] = q = K.createElement(f).attr(c).add(K.defs), q.radAttr = e, q.stops = [], b(t, function (h) {
                    0 === h[1].indexOf("rgba") ? (g = a.color(h[1]), u = g.get("rgb"), A = g.get("a")) : (u = h[1], A = 1);
                    h = K.createElement("stop").attr({offset: h[0], "stop-color": u, "stop-opacity": A}).add(q);
                    q.stops.push(h)
                })), k = "url(" +
                    K.url + "#" + y + ")", x.setAttribute(d, k), x.gradient = G, h.toString = function () {
                    return k
                })
            },
            applyTextOutline: function (h) {
                var K = this.element, d, x, g, f, c;
                -1 !== h.indexOf("contrast") && (h = h.replace(/contrast/g, this.renderer.getContrast(K.style.fill)));
                h = h.split(" ");
                x = h[h.length - 1];
                if ((g = h[0]) && "none" !== g && a.svg) {
                    this.fakeTS = !0;
                    h = [].slice.call(K.getElementsByTagName("tspan"));
                    this.ySetter = this.xSetter;
                    g = g.replace(/(^[\d\.]+)(.*?)$/g, function (a, h, K) {
                        return 2 * h + K
                    });
                    for (c = h.length; c--;) d = h[c], "highcharts-text-outline" ===
                    d.getAttribute("class") && n(h, K.removeChild(d));
                    f = K.firstChild;
                    b(h, function (a, h) {
                        0 === h && (a.setAttribute("x", K.getAttribute("x")), h = K.getAttribute("y"), a.setAttribute("y", h || 0), null === h && K.setAttribute("y", 0));
                        a = a.cloneNode(1);
                        m(a, {
                            "class": "highcharts-text-outline",
                            fill: x,
                            stroke: x,
                            "stroke-width": g,
                            "stroke-linejoin": "round"
                        });
                        K.insertBefore(a, f)
                    })
                }
            },
            attr: function (a, h, d, x) {
                var K, g = this.element, b, f = this, c, e;
                "string" === typeof a && void 0 !== h && (K = a, a = {}, a[K] = h);
                "string" === typeof a ? f = (this[a + "Getter"] || this._defaultGetter).call(this,
                    a, g) : (z(a, function (h, d) {
                    c = !1;
                    x || N(this, d);
                    this.symbolName && /^(x|y|width|height|r|start|end|innerR|anchorX|anchorY)$/.test(d) && (b || (this.symbolAttr(a), b = !0), c = !0);
                    !this.rotation || "x" !== d && "y" !== d || (this.doTransform = !0);
                    c || (e = this[d + "Setter"] || this._defaultSetter, e.call(this, h, d, g), this.shadows && /^(width|height|visibility|x|y|d|transform|cx|cy|r)$/.test(d) && this.updateShadows(d, h, e))
                }, this), this.afterSetters());
                d && d.call(this);
                return f
            },
            afterSetters: function () {
                this.doTransform && (this.updateTransform(),
                    this.doTransform = !1)
            },
            updateShadows: function (a, h, d) {
                for (var K = this.shadows, x = K.length; x--;) d.call(K[x], "height" === a ? Math.max(h - (K[x].cutHeight || 0), 0) : "d" === a ? this.d : h, a, K[x])
            },
            addClass: function (a, h) {
                var d = this.attr("class") || "";
                -1 === d.indexOf(a) && (h || (a = (d + (d ? " " : "") + a).replace("  ", " ")), this.attr("class", a));
                return this
            },
            hasClass: function (a) {
                return -1 !== u(a, (this.attr("class") || "").split(" "))
            },
            removeClass: function (a) {
                return this.attr("class", (this.attr("class") || "").replace(a, ""))
            },
            symbolAttr: function (a) {
                var h =
                    this;
                b("x y r start end width height innerR anchorX anchorY".split(" "), function (d) {
                    h[d] = G(a[d], h[d])
                });
                h.attr({d: h.renderer.symbols[h.symbolName](h.x, h.y, h.width, h.height, h)})
            },
            clip: function (a) {
                return this.attr("clip-path", a ? "url(" + this.renderer.url + "#" + a.id + ")" : "none")
            },
            crisp: function (a, h) {
                var d;
                h = h || a.strokeWidth || 0;
                d = Math.round(h) % 2 / 2;
                a.x = Math.floor(a.x || this.x || 0) + d;
                a.y = Math.floor(a.y || this.y || 0) + d;
                a.width = Math.floor((a.width || this.width || 0) - 2 * d);
                a.height = Math.floor((a.height || this.height || 0) -
                    2 * d);
                B(a.strokeWidth) && (a.strokeWidth = h);
                return a
            },
            css: function (a) {
                var d = this.styles, x = {}, K = this.element, b, f = "", c, e = !d,
                    n = ["textOutline", "textOverflow", "width"];
                a && a.color && (a.fill = a.color);
                d && z(a, function (a, h) {
                    a !== d[h] && (x[h] = a, e = !0)
                });
                e && (d && (a = g(d, x)), b = this.textWidth = a && a.width && "auto" !== a.width && "text" === K.nodeName.toLowerCase() && h(a.width), this.styles = a, b && !O && this.renderer.forExport && delete a.width, K.namespaceURI === this.SVG_NS ? (c = function (a, h) {
                    return "-" + h.toLowerCase()
                }, z(a, function (a, h) {
                    -1 ===
                    u(h, n) && (f += h.replace(/([A-Z])/g, c) + ":" + a + ";")
                }), f && m(K, "style", f)) : p(K, a), this.added && ("text" === this.element.nodeName && this.renderer.buildText(this), a && a.textOutline && this.applyTextOutline(a.textOutline)));
                return this
            },
            strokeWidth: function () {
                return this["stroke-width"] || 0
            },
            on: function (a, h) {
                var d = this, x = d.element;
                f && "click" === a ? (x.ontouchstart = function (a) {
                    d.touchEventFired = Date.now();
                    a.preventDefault();
                    h.call(x, a)
                }, x.onclick = function (a) {
                    (-1 === Q.navigator.userAgent.indexOf("Android") || 1100 < Date.now() -
                        (d.touchEventFired || 0)) && h.call(x, a)
                }) : x["on" + a] = h;
                return this
            },
            setRadialReference: function (a) {
                var h = this.renderer.gradients[this.element.gradient];
                this.element.radialReference = a;
                h && h.radAttr && h.animate(this.renderer.getRadialAttr(a, h.radAttr));
                return this
            },
            translate: function (a, h) {
                return this.attr({translateX: a, translateY: h})
            },
            invert: function (a) {
                this.inverted = a;
                this.updateTransform();
                return this
            },
            updateTransform: function () {
                var a = this.translateX || 0, h = this.translateY || 0, d = this.scaleX, x = this.scaleY,
                    g = this.inverted, b = this.rotation, f = this.matrix, c = this.element;
                g && (a += this.width, h += this.height);
                a = ["translate(" + a + "," + h + ")"];
                B(f) && a.push("matrix(" + f.join(",") + ")");
                g ? a.push("rotate(90) scale(-1,1)") : b && a.push("rotate(" + b + " " + G(this.rotationOriginX, c.getAttribute("x"), 0) + " " + G(this.rotationOriginY, c.getAttribute("y") || 0) + ")");
                (B(d) || B(x)) && a.push("scale(" + G(d, 1) + " " + G(x, 1) + ")");
                a.length && c.setAttribute("transform", a.join(" "))
            },
            toFront: function () {
                var a = this.element;
                a.parentNode.appendChild(a);
                return this
            },
            align: function (a, h, d) {
                var x, g, b, f, K = {};
                g = this.renderer;
                b = g.alignedObjects;
                var c, e;
                if (a) {
                    if (this.alignOptions = a, this.alignByTranslate = h, !d || H(d)) this.alignTo = x = d || "renderer", n(b, this), b.push(this), d = null
                } else a = this.alignOptions, h = this.alignByTranslate, x = this.alignTo;
                d = G(d, g[x], g);
                x = a.align;
                g = a.verticalAlign;
                b = (d.x || 0) + (a.x || 0);
                f = (d.y || 0) + (a.y || 0);
                "right" === x ? c = 1 : "center" === x && (c = 2);
                c && (b += (d.width - (a.width || 0)) / c);
                K[h ? "translateX" : "x"] = Math.round(b);
                "bottom" === g ? e = 1 : "middle" === g && (e = 2);
                e && (f += (d.height -
                    (a.height || 0)) / e);
                K[h ? "translateY" : "y"] = Math.round(f);
                this[this.placed ? "animate" : "attr"](K);
                this.placed = !0;
                this.alignAttr = K;
                return this
            },
            getBBox: function (a, h) {
                var d, x = this.renderer, f, c = this.element, K = this.styles, n, z = this.textStr, q, t = x.cache,
                    u = x.cacheKeys, A;
                h = G(h, this.rotation);
                f = h * e;
                n = K && K.fontSize;
                B(z) && (A = z.toString(), -1 === A.indexOf("\x3c") && (A = A.replace(/[0-9]/g, "0")), A += ["", h || 0, n, K && K.width, K && K.textOverflow].join());
                A && !a && (d = t[A]);
                if (!d) {
                    if (c.namespaceURI === this.SVG_NS || x.forExport) {
                        try {
                            (q =
                                this.fakeTS && function (a) {
                                    b(c.querySelectorAll(".highcharts-text-outline"), function (h) {
                                        h.style.display = a
                                    })
                                }) && q("none"), d = c.getBBox ? g({}, c.getBBox()) : {
                                width: c.offsetWidth,
                                height: c.offsetHeight
                            }, q && q("")
                        } catch (fa) {
                        }
                        if (!d || 0 > d.width) d = {width: 0, height: 0}
                    } else d = this.htmlGetBBox();
                    x.isSVG && (a = d.width, x = d.height, K && "11px" === K.fontSize && 17 === Math.round(x) && (d.height = x = 14), h && (d.width = Math.abs(x * Math.sin(f)) + Math.abs(a * Math.cos(f)), d.height = Math.abs(x * Math.cos(f)) + Math.abs(a * Math.sin(f))));
                    if (A && 0 < d.height) {
                        for (; 250 <
                               u.length;) delete t[u.shift()];
                        t[A] || u.push(A);
                        t[A] = d
                    }
                }
                return d
            },
            show: function (a) {
                return this.attr({visibility: a ? "inherit" : "visible"})
            },
            hide: function () {
                return this.attr({visibility: "hidden"})
            },
            fadeOut: function (a) {
                var h = this;
                h.animate({opacity: 0}, {
                    duration: a || 150, complete: function () {
                        h.attr({y: -9999})
                    }
                })
            },
            add: function (a) {
                var h = this.renderer, d = this.element, x;
                a && (this.parentGroup = a);
                this.parentInverted = a && a.inverted;
                void 0 !== this.textStr && h.buildText(this);
                this.added = !0;
                if (!a || a.handleZ || this.zIndex) x =
                    this.zIndexSetter();
                x || (a ? a.element : h.box).appendChild(d);
                if (this.onAdd) this.onAdd();
                return this
            },
            safeRemoveChild: function (a) {
                var h = a.parentNode;
                h && h.removeChild(a)
            },
            destroy: function () {
                var a = this, h = a.element || {}, d = a.renderer.isSVG && "SPAN" === h.nodeName && a.parentGroup,
                    x = h.ownerSVGElement, g = a.clipPath;
                h.onclick = h.onmouseout = h.onmouseover = h.onmousemove = h.point = null;
                N(a);
                g && x && (b(x.querySelectorAll("[clip-path],[CLIP-PATH]"), function (a) {
                    var h = a.getAttribute("clip-path"), d = g.element.id;
                    (-1 < h.indexOf("(#" +
                        d + ")") || -1 < h.indexOf('("#' + d + '")')) && a.removeAttribute("clip-path")
                }), a.clipPath = g.destroy());
                if (a.stops) {
                    for (x = 0; x < a.stops.length; x++) a.stops[x] = a.stops[x].destroy();
                    a.stops = null
                }
                a.safeRemoveChild(h);
                for (a.destroyShadows(); d && d.div && 0 === d.div.childNodes.length;) h = d.parentGroup, a.safeRemoveChild(d.div), delete d.div, d = h;
                a.alignTo && n(a.renderer.alignedObjects, a);
                z(a, function (h, d) {
                    delete a[d]
                });
                return null
            },
            shadow: function (a, h, d) {
                var x = [], g, b, f = this.element, c, e, K, n;
                if (!a) this.destroyShadows(); else if (!this.shadows) {
                    e =
                        G(a.width, 3);
                    K = (a.opacity || .15) / e;
                    n = this.parentInverted ? "(-1,-1)" : "(" + G(a.offsetX, 1) + ", " + G(a.offsetY, 1) + ")";
                    for (g = 1; g <= e; g++) b = f.cloneNode(0), c = 2 * e + 1 - 2 * g, m(b, {
                        isShadow: "true",
                        stroke: a.color || "#000000",
                        "stroke-opacity": K * g,
                        "stroke-width": c,
                        transform: "translate" + n,
                        fill: "none"
                    }), d && (m(b, "height", Math.max(m(b, "height") - c, 0)), b.cutHeight = c), h ? h.element.appendChild(b) : f.parentNode && f.parentNode.insertBefore(b, f), x.push(b);
                    this.shadows = x
                }
                return this
            },
            destroyShadows: function () {
                b(this.shadows || [], function (a) {
                        this.safeRemoveChild(a)
                    },
                    this);
                this.shadows = void 0
            },
            xGetter: function (a) {
                "circle" === this.element.nodeName && ("x" === a ? a = "cx" : "y" === a && (a = "cy"));
                return this._defaultGetter(a)
            },
            _defaultGetter: function (a) {
                a = G(this[a + "Value"], this[a], this.element ? this.element.getAttribute(a) : null, 0);
                /^[\-0-9\.]+$/.test(a) && (a = parseFloat(a));
                return a
            },
            dSetter: function (a, h, d) {
                a && a.join && (a = a.join(" "));
                /(NaN| {2}|^$)/.test(a) && (a = "M 0 0");
                this[h] !== a && (d.setAttribute(h, a), this[h] = a)
            },
            dashstyleSetter: function (a) {
                var d, x = this["stroke-width"];
                "inherit" ===
                x && (x = 1);
                if (a = a && a.toLowerCase()) {
                    a = a.replace("shortdashdotdot", "3,1,1,1,1,1,").replace("shortdashdot", "3,1,1,1").replace("shortdot", "1,1,").replace("shortdash", "3,1,").replace("longdash", "8,3,").replace(/dot/g, "1,3,").replace("dash", "4,3,").replace(/,$/, "").split(",");
                    for (d = a.length; d--;) a[d] = h(a[d]) * x;
                    a = a.join(",").replace(/NaN/g, "none");
                    this.element.setAttribute("stroke-dasharray", a)
                }
            },
            alignSetter: function (a) {
                this.alignValue = a;
                this.element.setAttribute("text-anchor", {
                    left: "start", center: "middle",
                    right: "end"
                }[a])
            },
            opacitySetter: function (a, h, d) {
                this[h] = a;
                d.setAttribute(h, a)
            },
            titleSetter: function (a) {
                var h = this.element.getElementsByTagName("title")[0];
                h || (h = k.createElementNS(this.SVG_NS, "title"), this.element.appendChild(h));
                h.firstChild && h.removeChild(h.firstChild);
                h.appendChild(k.createTextNode(String(G(a), "").replace(/<[^>]*>/g, "").replace(/&lt;/g, "\x3c").replace(/&gt;/g, "\x3e")))
            },
            textSetter: function (a) {
                a !== this.textStr && (delete this.bBox, this.textStr = a, this.added && this.renderer.buildText(this))
            },
            fillSetter: function (a, h, d) {
                "string" === typeof a ? d.setAttribute(h, a) : a && this.colorGradient(a, h, d)
            },
            visibilitySetter: function (a, h, d) {
                "inherit" === a ? d.removeAttribute(h) : this[h] !== a && d.setAttribute(h, a);
                this[h] = a
            },
            zIndexSetter: function (a, d) {
                var x = this.renderer, g = this.parentGroup, b = (g || x).element || x.box, f, c = this.element, e, n,
                    x = b === x.box;
                f = this.added;
                var z;
                B(a) && (c.zIndex = a, a = +a, this[d] === a && (f = !1), this[d] = a);
                if (f) {
                    (a = this.zIndex) && g && (g.handleZ = !0);
                    d = b.childNodes;
                    for (z = d.length - 1; 0 <= z && !e; z--) if (g = d[z],
                            f = g.zIndex, n = !B(f), g !== c) if (0 > a && n && !x && !z) b.insertBefore(c, d[z]), e = !0; else if (h(f) <= a || n && (!B(a) || 0 <= a)) b.insertBefore(c, d[z + 1] || null), e = !0;
                    e || (b.insertBefore(c, d[x ? 3 : 0] || null), e = !0)
                }
                return e
            },
            _defaultSetter: function (a, h, d) {
                d.setAttribute(h, a)
            }
        });
        C.prototype.yGetter = C.prototype.xGetter;
        C.prototype.translateXSetter = C.prototype.translateYSetter = C.prototype.rotationSetter = C.prototype.verticalAlignSetter = C.prototype.rotationOriginXSetter = C.prototype.rotationOriginYSetter = C.prototype.scaleXSetter = C.prototype.scaleYSetter =
            C.prototype.matrixSetter = function (a, h) {
                this[h] = a;
                this.doTransform = !0
            };
        C.prototype["stroke-widthSetter"] = C.prototype.strokeSetter = function (a, h, d) {
            this[h] = a;
            this.stroke && this["stroke-width"] ? (C.prototype.fillSetter.call(this, this.stroke, "stroke", d), d.setAttribute("stroke-width", this["stroke-width"]), this.hasStroke = !0) : "stroke-width" === h && 0 === a && this.hasStroke && (d.removeAttribute("stroke"), this.hasStroke = !1)
        };
        E = a.SVGRenderer = function () {
            this.init.apply(this, arguments)
        };
        g(E.prototype, {
            Element: C, SVG_NS: P,
            init: function (a, h, d, x, g, b) {
                var f;
                x = this.createElement("svg").attr({version: "1.1", "class": "highcharts-root"}).css(this.getStyle(x));
                f = x.element;
                a.appendChild(f);
                m(a, "dir", "ltr");
                -1 === a.innerHTML.indexOf("xmlns") && m(f, "xmlns", this.SVG_NS);
                this.isSVG = !0;
                this.box = f;
                this.boxWrapper = x;
                this.alignedObjects = [];
                this.url = (q || I) && k.getElementsByTagName("base").length ? Q.location.href.replace(/#.*?$/, "").replace(/<[^>]*>/g, "").replace(/([\('\)])/g, "\\$1").replace(/ /g, "%20") : "";
                this.createElement("desc").add().element.appendChild(k.createTextNode("Created with Highstock 6.0.7"));
                this.defs = this.createElement("defs").add();
                this.allowHTML = b;
                this.forExport = g;
                this.gradients = {};
                this.cache = {};
                this.cacheKeys = [];
                this.imgCount = 0;
                this.setSize(h, d, !1);
                var c;
                q && a.getBoundingClientRect && (h = function () {
                    p(a, {left: 0, top: 0});
                    c = a.getBoundingClientRect();
                    p(a, {left: Math.ceil(c.left) - c.left + "px", top: Math.ceil(c.top) - c.top + "px"})
                }, h(), this.unSubPixelFix = F(Q, "resize", h))
            }, getStyle: function (a) {
                return this.style = g({
                        fontFamily: '"Lucida Grande", "Lucida Sans Unicode", Arial, Helvetica, sans-serif',
                        fontSize: "12px"
                    },
                    a)
            }, setStyle: function (a) {
                this.boxWrapper.css(this.getStyle(a))
            }, isHidden: function () {
                return !this.boxWrapper.getBBox().width
            }, destroy: function () {
                var a = this.defs;
                this.box = null;
                this.boxWrapper = this.boxWrapper.destroy();
                c(this.gradients || {});
                this.gradients = null;
                a && (this.defs = a.destroy());
                this.unSubPixelFix && this.unSubPixelFix();
                return this.alignedObjects = null
            }, createElement: function (a) {
                var h = new this.Element;
                h.init(this, a);
                return h
            }, draw: d, getRadialAttr: function (a, h) {
                return {
                    cx: a[0] - a[2] / 2 + h.cx * a[2], cy: a[1] -
                    a[2] / 2 + h.cy * a[2], r: h.r * a[2]
                }
            }, getSpanWidth: function (a) {
                return a.getBBox(!0).width
            }, applyEllipsis: function (a, h, d, x) {
                var g = a.rotation, b = d, f, c = 0, e = d.length, n = function (a) {
                    h.removeChild(h.firstChild);
                    a && h.appendChild(k.createTextNode(a))
                }, z;
                a.rotation = 0;
                b = this.getSpanWidth(a, h);
                if (z = b > x) {
                    for (; c <= e;) f = Math.ceil((c + e) / 2), b = d.substring(0, f) + "\u2026", n(b), b = this.getSpanWidth(a, h), c === e ? c = e + 1 : b > x ? e = f - 1 : c = f;
                    0 === e && n("")
                }
                a.rotation = g;
                return z
            }, escapes: {
                "\x26": "\x26amp;", "\x3c": "\x26lt;", "\x3e": "\x26gt;", "'": "\x26#39;",
                '"': "\x26quot;"
            }, buildText: function (a) {
                var d = a.element, x = this, g = x.forExport, f = G(a.textStr, "").toString(),
                    c = -1 !== f.indexOf("\x3c"), e = d.childNodes, n, q, A, y, I = m(d, "x"), D = a.styles,
                    K = a.textWidth, J = D && D.lineHeight, N = D && D.textOutline,
                    H = D && "ellipsis" === D.textOverflow, M = D && "nowrap" === D.whiteSpace, l = D && D.fontSize, v,
                    B, Q = e.length, D = K && !a.added && this.box, w = function (a) {
                        var g;
                        g = /(px|em)$/.test(a && a.style.fontSize) ? a.style.fontSize : l || x.style.fontSize || 12;
                        return J ? h(J) : x.fontMetrics(g, a.getAttribute("style") ? a : d).h
                    },
                    r = function (a, h) {
                        z(x.escapes, function (d, x) {
                            h && -1 !== u(d, h) || (a = a.toString().replace(new RegExp(d, "g"), x))
                        });
                        return a
                    };
                v = [f, H, M, J, N, l, K].join();
                if (v !== a.textCache) {
                    for (a.textCache = v; Q--;) d.removeChild(e[Q]);
                    c || N || H || K || -1 !== f.indexOf(" ") ? (n = /<.*class="([^"]+)".*>/, q = /<.*style="([^"]+)".*>/, A = /<.*href="([^"]+)".*>/, D && D.appendChild(d), f = c ? f.replace(/<(b|strong)>/g, '\x3cspan style\x3d"font-weight:bold"\x3e').replace(/<(i|em)>/g, '\x3cspan style\x3d"font-style:italic"\x3e').replace(/<a/g, "\x3cspan").replace(/<\/(b|strong|i|em|a)>/g,
                        "\x3c/span\x3e").split(/<br.*?>/g) : [f], f = t(f, function (a) {
                        return "" !== a
                    }), b(f, function (h, f) {
                        var c, e = 0;
                        h = h.replace(/^\s+|\s+$/g, "").replace(/<span/g, "|||\x3cspan").replace(/<\/span>/g, "\x3c/span\x3e|||");
                        c = h.split("|||");
                        b(c, function (h) {
                            if ("" !== h || 1 === c.length) {
                                var b = {}, z = k.createElementNS(x.SVG_NS, "tspan"), t, u;
                                n.test(h) && (t = h.match(n)[1], m(z, "class", t));
                                q.test(h) && (u = h.match(q)[1].replace(/(;| |^)color([ :])/, "$1fill$2"), m(z, "style", u));
                                A.test(h) && !g && (m(z, "onclick", 'location.href\x3d"' + h.match(A)[1] +
                                    '"'), m(z, "class", "highcharts-anchor"), p(z, {cursor: "pointer"}));
                                h = r(h.replace(/<[a-zA-Z\/](.|\n)*?>/g, "") || " ");
                                if (" " !== h) {
                                    z.appendChild(k.createTextNode(h));
                                    e ? b.dx = 0 : f && null !== I && (b.x = I);
                                    m(z, b);
                                    d.appendChild(z);
                                    !e && B && (!O && g && p(z, {display: "block"}), m(z, "dy", w(z)));
                                    if (K) {
                                        b = h.replace(/([^\^])-/g, "$1- ").split(" ");
                                        t = 1 < c.length || f || 1 < b.length && !M;
                                        var G = [], D, J = w(z), N = a.rotation;
                                        for (H && (y = x.applyEllipsis(a, z, h, K)); !H && t && (b.length || G.length);) a.rotation = 0, D = x.getSpanWidth(a, z), h = D > K, void 0 === y && (y = h),
                                            h && 1 !== b.length ? (z.removeChild(z.firstChild), G.unshift(b.pop())) : (b = G, G = [], b.length && !M && (z = k.createElementNS(P, "tspan"), m(z, {
                                                dy: J,
                                                x: I
                                            }), u && m(z, "style", u), d.appendChild(z)), D > K && (K = D)), b.length && z.appendChild(k.createTextNode(b.join(" ").replace(/- /g, "-")));
                                        a.rotation = N
                                    }
                                    e++
                                }
                            }
                        });
                        B = B || d.childNodes.length
                    }), y && a.attr("title", r(a.textStr, ["\x26lt;", "\x26gt;"])), D && D.removeChild(d), N && a.applyTextOutline && a.applyTextOutline(N)) : d.appendChild(k.createTextNode(r(f)))
                }
            }, getContrast: function (a) {
                a = w(a).rgba;
                return 510 < a[0] + a[1] + a[2] ? "#000000" : "#FFFFFF"
            }, button: function (a, h, d, x, b, f, c, e, z) {
                var n = this.label(a, h, d, z, null, null, null, null, "button"), q = 0;
                n.attr(J({padding: 8, r: 2}, b));
                var t, u, G, y;
                b = J({
                    fill: "#f7f7f7",
                    stroke: "#cccccc",
                    "stroke-width": 1,
                    style: {color: "#333333", cursor: "pointer", fontWeight: "normal"}
                }, b);
                t = b.style;
                delete b.style;
                f = J(b, {fill: "#e6e6e6"}, f);
                u = f.style;
                delete f.style;
                c = J(b, {fill: "#e6ebf5", style: {color: "#000000", fontWeight: "bold"}}, c);
                G = c.style;
                delete c.style;
                e = J(b, {style: {color: "#cccccc"}},
                    e);
                y = e.style;
                delete e.style;
                F(n.element, A ? "mouseover" : "mouseenter", function () {
                    3 !== q && n.setState(1)
                });
                F(n.element, A ? "mouseout" : "mouseleave", function () {
                    3 !== q && n.setState(q)
                });
                n.setState = function (a) {
                    1 !== a && (n.state = q = a);
                    n.removeClass(/highcharts-button-(normal|hover|pressed|disabled)/).addClass("highcharts-button-" + ["normal", "hover", "pressed", "disabled"][a || 0]);
                    n.attr([b, f, c, e][a || 0]).css([t, u, G, y][a || 0])
                };
                n.attr(b).css(g({cursor: "default"}, t));
                return n.on("click", function (a) {
                    3 !== q && x.call(n, a)
                })
            }, crispLine: function (a,
                                    h) {
                a[1] === a[4] && (a[1] = a[4] = Math.round(a[1]) - h % 2 / 2);
                a[2] === a[5] && (a[2] = a[5] = Math.round(a[2]) + h % 2 / 2);
                return a
            }, path: function (a) {
                var h = {fill: "none"};
                D(a) ? h.d = a : y(a) && g(h, a);
                return this.createElement("path").attr(h)
            }, circle: function (a, h, d) {
                a = y(a) ? a : {x: a, y: h, r: d};
                h = this.createElement("circle");
                h.xSetter = h.ySetter = function (a, h, d) {
                    d.setAttribute("c" + h, a)
                };
                return h.attr(a)
            }, arc: function (a, h, d, x, b, g) {
                y(a) ? (x = a, h = x.y, d = x.r, a = x.x) : x = {innerR: x, start: b, end: g};
                a = this.symbol("arc", a, h, d, d, x);
                a.r = d;
                return a
            }, rect: function (a,
                               h, d, x, b, g) {
                b = y(a) ? a.r : b;
                var f = this.createElement("rect");
                a = y(a) ? a : void 0 === a ? {} : {x: a, y: h, width: Math.max(d, 0), height: Math.max(x, 0)};
                void 0 !== g && (a.strokeWidth = g, a = f.crisp(a));
                a.fill = "none";
                b && (a.r = b);
                f.rSetter = function (a, h, d) {
                    m(d, {rx: a, ry: a})
                };
                return f.attr(a)
            }, setSize: function (a, h, d) {
                var x = this.alignedObjects, b = x.length;
                this.width = a;
                this.height = h;
                for (this.boxWrapper.animate({width: a, height: h}, {
                    step: function () {
                        this.attr({viewBox: "0 0 " + this.attr("width") + " " + this.attr("height")})
                    }, duration: G(d, !0) ?
                        void 0 : 0
                }); b--;) x[b].align()
            }, g: function (a) {
                var h = this.createElement("g");
                return a ? h.attr({"class": "highcharts-" + a}) : h
            }, image: function (a, h, d, x, b) {
                var f = {preserveAspectRatio: "none"};
                1 < arguments.length && g(f, {x: h, y: d, width: x, height: b});
                f = this.createElement("image").attr(f);
                f.element.setAttributeNS ? f.element.setAttributeNS("http://www.w3.org/1999/xlink", "href", a) : f.element.setAttribute("hc-svg-href", a);
                return f
            }, symbol: function (a, h, d, x, f, c) {
                var e = this, z, n = /^url\((.*?)\)$/, q = n.test(a), t = !q && (this.symbols[a] ?
                    a : "circle"), A = t && this.symbols[t],
                    u = B(h) && A && A.call(this.symbols, Math.round(h), Math.round(d), x, f, c), y, I;
                A ? (z = this.path(u), z.attr("fill", "none"), g(z, {
                    symbolName: t,
                    x: h,
                    y: d,
                    width: x,
                    height: f
                }), c && g(z, c)) : q && (y = a.match(n)[1], z = this.image(y), z.imgwidth = G(M[y] && M[y].width, c && c.width), z.imgheight = G(M[y] && M[y].height, c && c.height), I = function () {
                    z.attr({width: z.width, height: z.height})
                }, b(["width", "height"], function (a) {
                    z[a + "Setter"] = function (a, h) {
                        var d = {}, x = this["img" + h], b = "width" === h ? "translateX" : "translateY";
                        this[h] = a;
                        B(x) && (this.element && this.element.setAttribute(h, x), this.alignByTranslate || (d[b] = ((this[h] || 0) - x) / 2, this.attr(d)))
                    }
                }), B(h) && z.attr({
                    x: h,
                    y: d
                }), z.isImg = !0, B(z.imgwidth) && B(z.imgheight) ? I() : (z.attr({width: 0, height: 0}), v("img", {
                    onload: function () {
                        var a = l[e.chartIndex];
                        0 === this.width && (p(this, {position: "absolute", top: "-999em"}), k.body.appendChild(this));
                        M[y] = {width: this.width, height: this.height};
                        z.imgwidth = this.width;
                        z.imgheight = this.height;
                        z.element && I();
                        this.parentNode && this.parentNode.removeChild(this);
                        e.imgCount--;
                        if (!e.imgCount && a && a.onload) a.onload()
                    }, src: y
                }), this.imgCount++));
                return z
            }, symbols: {
                circle: function (a, h, d, x) {
                    return this.arc(a + d / 2, h + x / 2, d / 2, x / 2, {start: 0, end: 2 * Math.PI, open: !1})
                }, square: function (a, h, d, x) {
                    return ["M", a, h, "L", a + d, h, a + d, h + x, a, h + x, "Z"]
                }, triangle: function (a, h, d, x) {
                    return ["M", a + d / 2, h, "L", a + d, h + x, a, h + x, "Z"]
                }, "triangle-down": function (a, h, d, x) {
                    return ["M", a, h, "L", a + d, h, a + d / 2, h + x, "Z"]
                }, diamond: function (a, h, d, x) {
                    return ["M", a + d / 2, h, "L", a + d, h + x / 2, a + d / 2, h + x, a, h + x / 2, "Z"]
                }, arc: function (a,
                                  h, d, x, b) {
                    var f = b.start, g = b.r || d, c = b.r || x || d, e = b.end - .001;
                    d = b.innerR;
                    x = G(b.open, .001 > Math.abs(b.end - b.start - 2 * Math.PI));
                    var z = Math.cos(f), n = Math.sin(f), q = Math.cos(e), e = Math.sin(e);
                    b = .001 > b.end - f - Math.PI ? 0 : 1;
                    g = ["M", a + g * z, h + c * n, "A", g, c, 0, b, 1, a + g * q, h + c * e];
                    B(d) && g.push(x ? "M" : "L", a + d * q, h + d * e, "A", d, d, 0, b, 0, a + d * z, h + d * n);
                    g.push(x ? "" : "Z");
                    return g
                }, callout: function (a, h, d, x, b) {
                    var f = Math.min(b && b.r || 0, d, x), g = f + 6, c = b && b.anchorX;
                    b = b && b.anchorY;
                    var e;
                    e = ["M", a + f, h, "L", a + d - f, h, "C", a + d, h, a + d, h, a + d, h + f, "L", a + d, h + x -
                    f, "C", a + d, h + x, a + d, h + x, a + d - f, h + x, "L", a + f, h + x, "C", a, h + x, a, h + x, a, h + x - f, "L", a, h + f, "C", a, h, a, h, a + f, h];
                    c && c > d ? b > h + g && b < h + x - g ? e.splice(13, 3, "L", a + d, b - 6, a + d + 6, b, a + d, b + 6, a + d, h + x - f) : e.splice(13, 3, "L", a + d, x / 2, c, b, a + d, x / 2, a + d, h + x - f) : c && 0 > c ? b > h + g && b < h + x - g ? e.splice(33, 3, "L", a, b + 6, a - 6, b, a, b - 6, a, h + f) : e.splice(33, 3, "L", a, x / 2, c, b, a, x / 2, a, h + f) : b && b > x && c > a + g && c < a + d - g ? e.splice(23, 3, "L", c + 6, h + x, c, h + x + 6, c - 6, h + x, a + f, h + x) : b && 0 > b && c > a + g && c < a + d - g && e.splice(3, 3, "L", c - 6, h, c, h - 6, c + 6, h, d - f, h);
                    return e
                }
            }, clipRect: function (h, d, x,
                                   b) {
                var f = a.uniqueKey(), g = this.createElement("clipPath").attr({id: f}).add(this.defs);
                h = this.rect(h, d, x, b, 0).add(g);
                h.id = f;
                h.clipPath = g;
                h.count = 0;
                return h
            }, text: function (a, h, d, x) {
                var b = {};
                if (x && (this.allowHTML || !this.forExport)) return this.html(a, h, d);
                b.x = Math.round(h || 0);
                d && (b.y = Math.round(d));
                if (a || 0 === a) b.text = a;
                a = this.createElement("text").attr(b);
                x || (a.xSetter = function (a, h, d) {
                    var x = d.getElementsByTagName("tspan"), b, f = d.getAttribute(h), g;
                    for (g = 0; g < x.length; g++) b = x[g], b.getAttribute(h) === f && b.setAttribute(h,
                        a);
                    d.setAttribute(h, a)
                });
                return a
            }, fontMetrics: function (a, d) {
                a = a || d && d.style && d.style.fontSize || this.style && this.style.fontSize;
                a = /px/.test(a) ? h(a) : /em/.test(a) ? parseFloat(a) * (d ? this.fontMetrics(null, d.parentNode).f : 16) : 12;
                d = 24 > a ? a + 3 : Math.round(1.2 * a);
                return {h: d, b: Math.round(.8 * d), f: a}
            }, rotCorr: function (a, h, d) {
                var x = a;
                h && d && (x = Math.max(x * Math.cos(h * e), 4));
                return {x: -a / 3 * Math.sin(h * e), y: x}
            }, label: function (h, d, f, c, e, z, n, q, t) {
                var A = this, u = A.g("button" !== t && "label"), y = u.text = A.text("", 0, 0, n).attr({zIndex: 1}),
                    G, I, k = 0, P = 3, O = 0, D, N, H, M, p, l = {}, v, m, K = /^url\((.*?)\)$/.test(c), Q = K, w, r,
                    Y, V;
                t && u.addClass("highcharts-" + t);
                Q = K;
                w = function () {
                    return (v || 0) % 2 / 2
                };
                r = function () {
                    var a = y.element.style, h = {};
                    I = (void 0 === D || void 0 === N || p) && B(y.textStr) && y.getBBox();
                    u.width = (D || I.width || 0) + 2 * P + O;
                    u.height = (N || I.height || 0) + 2 * P;
                    m = P + A.fontMetrics(a && a.fontSize, y).b;
                    Q && (G || (u.box = G = A.symbols[c] || K ? A.symbol(c) : A.rect(), G.addClass(("button" === t ? "" : "highcharts-label-box") + (t ? " highcharts-" + t + "-box" : "")), G.add(u), a = w(), h.x = a, h.y = (q ? -m :
                        0) + a), h.width = Math.round(u.width), h.height = Math.round(u.height), G.attr(g(h, l)), l = {})
                };
                Y = function () {
                    var a = O + P, h;
                    h = q ? 0 : m;
                    B(D) && I && ("center" === p || "right" === p) && (a += {center: .5, right: 1}[p] * (D - I.width));
                    if (a !== y.x || h !== y.y) y.attr("x", a), void 0 !== h && y.attr("y", h);
                    y.x = a;
                    y.y = h
                };
                V = function (a, h) {
                    G ? G.attr(a, h) : l[a] = h
                };
                u.onAdd = function () {
                    y.add(u);
                    u.attr({text: h || 0 === h ? h : "", x: d, y: f});
                    G && B(e) && u.attr({anchorX: e, anchorY: z})
                };
                u.widthSetter = function (h) {
                    D = a.isNumber(h) ? h : null
                };
                u.heightSetter = function (a) {
                    N = a
                };
                u["text-alignSetter"] =
                    function (a) {
                        p = a
                    };
                u.paddingSetter = function (a) {
                    B(a) && a !== P && (P = u.padding = a, Y())
                };
                u.paddingLeftSetter = function (a) {
                    B(a) && a !== O && (O = a, Y())
                };
                u.alignSetter = function (a) {
                    a = {left: 0, center: .5, right: 1}[a];
                    a !== k && (k = a, I && u.attr({x: H}))
                };
                u.textSetter = function (a) {
                    void 0 !== a && y.textSetter(a);
                    r();
                    Y()
                };
                u["stroke-widthSetter"] = function (a, h) {
                    a && (Q = !0);
                    v = this["stroke-width"] = a;
                    V(h, a)
                };
                u.strokeSetter = u.fillSetter = u.rSetter = function (a, h) {
                    "r" !== h && ("fill" === h && a && (Q = !0), u[h] = a);
                    V(h, a)
                };
                u.anchorXSetter = function (a, h) {
                    e = u.anchorX =
                        a;
                    V(h, Math.round(a) - w() - H)
                };
                u.anchorYSetter = function (a, h) {
                    z = u.anchorY = a;
                    V(h, a - M)
                };
                u.xSetter = function (a) {
                    u.x = a;
                    k && (a -= k * ((D || I.width) + 2 * P));
                    H = Math.round(a);
                    u.attr("translateX", H)
                };
                u.ySetter = function (a) {
                    M = u.y = Math.round(a);
                    u.attr("translateY", M)
                };
                var ea = u.css;
                return g(u, {
                    css: function (a) {
                        if (a) {
                            var h = {};
                            a = J(a);
                            b(u.textProps, function (d) {
                                void 0 !== a[d] && (h[d] = a[d], delete a[d])
                            });
                            y.css(h)
                        }
                        return ea.call(u, a)
                    }, getBBox: function () {
                        return {width: I.width + 2 * P, height: I.height + 2 * P, x: I.x - P, y: I.y - P}
                    }, shadow: function (a) {
                        a &&
                        (r(), G && G.shadow(a));
                        return u
                    }, destroy: function () {
                        x(u.element, "mouseenter");
                        x(u.element, "mouseleave");
                        y && (y = y.destroy());
                        G && (G = G.destroy());
                        C.prototype.destroy.call(u);
                        u = A = r = Y = V = null
                    }
                })
            }
        });
        a.Renderer = E
    })(L);
    (function (a) {
        var C = a.attr, E = a.createElement, F = a.css, r = a.defined, m = a.each, l = a.extend, w = a.isFirefox,
            p = a.isMS, v = a.isWebKit, B = a.pick, e = a.pInt, c = a.SVGRenderer, k = a.win, b = a.wrap;
        l(a.SVGElement.prototype, {
            htmlCss: function (a) {
                var b = this.element;
                if (b = a && "SPAN" === b.tagName && a.width) delete a.width, this.textWidth =
                    b, this.updateTransform();
                a && "ellipsis" === a.textOverflow && (a.whiteSpace = "nowrap", a.overflow = "hidden");
                this.styles = l(this.styles, a);
                F(this.element, a);
                return this
            }, htmlGetBBox: function () {
                var a = this.element;
                return {x: a.offsetLeft, y: a.offsetTop, width: a.offsetWidth, height: a.offsetHeight}
            }, htmlUpdateTransform: function () {
                if (this.added) {
                    var a = this.renderer, b = this.element, c = this.translateX || 0, f = this.translateY || 0,
                        u = this.x || 0, k = this.y || 0, q = this.textAlign || "left",
                        A = {left: 0, center: .5, right: 1}[q], y = this.styles,
                        H = y && y.whiteSpace;
                    F(b, {marginLeft: c, marginTop: f});
                    this.shadows && m(this.shadows, function (a) {
                        F(a, {marginLeft: c + 1, marginTop: f + 1})
                    });
                    this.inverted && m(b.childNodes, function (d) {
                        a.invertChild(d, b)
                    });
                    if ("SPAN" === b.tagName) {
                        var y = this.rotation, I = this.textWidth && e(this.textWidth),
                            J = [y, q, b.innerHTML, this.textWidth, this.textAlign].join(), d;
                        (d = I !== this.oldTextWidth) && !(d = I > this.oldTextWidth) && ((d = this.textPxLength) || (F(b, {
                            width: "",
                            whiteSpace: H || "nowrap"
                        }), d = b.offsetWidth), d = d > I);
                        d && /[ \-]/.test(b.textContent ||
                            b.innerText) && (F(b, {
                            width: I + "px",
                            display: "block",
                            whiteSpace: H || "normal"
                        }), this.oldTextWidth = I);
                        J !== this.cTT && (H = a.fontMetrics(b.style.fontSize).b, r(y) && y !== (this.oldRotation || 0) && this.setSpanRotation(y, A, H), this.getSpanCorrection(this.textPxLength || b.offsetWidth, H, A, y, q));
                        F(b, {left: u + (this.xCorr || 0) + "px", top: k + (this.yCorr || 0) + "px"});
                        this.cTT = J;
                        this.oldRotation = y
                    }
                } else this.alignOnAdd = !0
            }, setSpanRotation: function (a, b, c) {
                var f = {}, g = this.renderer.getTransformKey();
                f[g] = f.transform = "rotate(" + a + "deg)";
                f[g + (w ? "Origin" : "-origin")] = f.transformOrigin = 100 * b + "% " + c + "px";
                F(this.element, f)
            }, getSpanCorrection: function (a, b, c) {
                this.xCorr = -a * c;
                this.yCorr = -b
            }
        });
        l(c.prototype, {
            getTransformKey: function () {
                return p && !/Edge/.test(k.navigator.userAgent) ? "-ms-transform" : v ? "-webkit-transform" : w ? "MozTransform" : k.opera ? "-o-transform" : ""
            }, html: function (a, c, e) {
                var f = this.createElement("span"), g = f.element, n = f.renderer, q = n.isSVG, t = function (a, f) {
                    m(["opacity", "visibility"], function (c) {
                        b(a, c + "Setter", function (a, d, b, c) {
                            a.call(this,
                                d, b, c);
                            f[b] = d
                        })
                    })
                };
                f.textSetter = function (a) {
                    a !== g.innerHTML && delete this.bBox;
                    this.textStr = a;
                    g.innerHTML = B(a, "");
                    f.doTransform = !0
                };
                q && t(f, f.element.style);
                f.xSetter = f.ySetter = f.alignSetter = f.rotationSetter = function (a, b) {
                    "align" === b && (b = "textAlign");
                    f[b] = a;
                    f.doTransform = !0
                };
                f.afterSetters = function () {
                    this.doTransform && (this.htmlUpdateTransform(), this.doTransform = !1)
                };
                f.attr({text: a, x: Math.round(c), y: Math.round(e)}).css({
                    fontFamily: this.style.fontFamily,
                    fontSize: this.style.fontSize,
                    position: "absolute"
                });
                g.style.whiteSpace = "nowrap";
                f.css = f.htmlCss;
                q && (f.add = function (a) {
                    var b, c = n.box.parentNode, e = [];
                    if (this.parentGroup = a) {
                        if (b = a.div, !b) {
                            for (; a;) e.push(a), a = a.parentGroup;
                            m(e.reverse(), function (a) {
                                function d(h, d) {
                                    a[d] = h;
                                    "translateX" === d ? g.left = h + "px" : g.top = h + "px";
                                    a.doTransform = !0
                                }

                                var g, h = C(a.element, "class");
                                h && (h = {className: h});
                                b = a.div = a.div || E("div", h, {
                                        position: "absolute",
                                        left: (a.translateX || 0) + "px",
                                        top: (a.translateY || 0) + "px",
                                        display: a.display,
                                        opacity: a.opacity,
                                        pointerEvents: a.styles && a.styles.pointerEvents
                                    },
                                    b || c);
                                g = b.style;
                                l(a, {
                                    classSetter: function (a) {
                                        return function (h) {
                                            this.element.setAttribute("class", h);
                                            a.className = h
                                        }
                                    }(b), on: function () {
                                        e[0].div && f.on.apply({element: e[0].div}, arguments);
                                        return a
                                    }, translateXSetter: d, translateYSetter: d
                                });
                                t(a, g)
                            })
                        }
                    } else b = c;
                    b.appendChild(g);
                    f.added = !0;
                    f.alignOnAdd && f.htmlUpdateTransform();
                    return f
                });
                return f
            }
        })
    })(L);
    (function (a) {
        var C = a.defined, E = a.each, F = a.extend, r = a.merge, m = a.pick, l = a.timeUnits, w = a.win;
        a.Time = function (a) {
            this.update(a, !1)
        };
        a.Time.prototype = {
            defaultOptions: {},
            update: function (p) {
                var l = m(p && p.useUTC, !0), B = this;
                this.options = p = r(!0, this.options || {}, p);
                this.Date = p.Date || w.Date;
                this.timezoneOffset = (this.useUTC = l) && p.timezoneOffset;
                this.getTimezoneOffset = this.timezoneOffsetFunction();
                (this.variableTimezone = !(l && !p.getTimezoneOffset && !p.timezone)) || this.timezoneOffset ? (this.get = function (a, c) {
                    var e = c.getTime(), b = e - B.getTimezoneOffset(c);
                    c.setTime(b);
                    a = c["getUTC" + a]();
                    c.setTime(e);
                    return a
                }, this.set = function (e, c, k) {
                    var b;
                    if (-1 !== a.inArray(e, ["Milliseconds", "Seconds",
                            "Minutes"])) c["set" + e](k); else b = B.getTimezoneOffset(c), b = c.getTime() - b, c.setTime(b), c["setUTC" + e](k), e = B.getTimezoneOffset(c), b = c.getTime() + e, c.setTime(b)
                }) : l ? (this.get = function (a, c) {
                    return c["getUTC" + a]()
                }, this.set = function (a, c, k) {
                    return c["setUTC" + a](k)
                }) : (this.get = function (a, c) {
                    return c["get" + a]()
                }, this.set = function (a, c, k) {
                    return c["set" + a](k)
                })
            }, makeTime: function (p, l, B, e, c, k) {
                var b, g, n;
                this.useUTC ? (b = this.Date.UTC.apply(0, arguments), g = this.getTimezoneOffset(b), b += g, n = this.getTimezoneOffset(b),
                    g !== n ? b += n - g : g - 36E5 !== this.getTimezoneOffset(b - 36E5) || a.isSafari || (b -= 36E5)) : b = (new this.Date(p, l, m(B, 1), m(e, 0), m(c, 0), m(k, 0))).getTime();
                return b
            }, timezoneOffsetFunction: function () {
                var p = this, l = this.options, m = w.moment;
                if (!this.useUTC) return function (a) {
                    return 6E4 * (new Date(a)).getTimezoneOffset()
                };
                if (l.timezone) {
                    if (m) return function (a) {
                        return 6E4 * -m.tz(a, l.timezone).utcOffset()
                    };
                    a.error(25)
                }
                return this.useUTC && l.getTimezoneOffset ? function (a) {
                    return 6E4 * l.getTimezoneOffset(a)
                } : function () {
                    return 6E4 *
                        (p.timezoneOffset || 0)
                }
            }, dateFormat: function (p, l, m) {
                if (!a.defined(l) || isNaN(l)) return a.defaultOptions.lang.invalidDate || "";
                p = a.pick(p, "%Y-%m-%d %H:%M:%S");
                var e = this, c = new this.Date(l), k = this.get("Hours", c), b = this.get("Day", c),
                    g = this.get("Date", c), n = this.get("Month", c), t = this.get("FullYear", c),
                    f = a.defaultOptions.lang, u = f.weekdays, D = f.shortWeekdays, q = a.pad, c = a.extend({
                        a: D ? D[b] : u[b].substr(0, 3),
                        A: u[b],
                        d: q(g),
                        e: q(g, 2, " "),
                        w: b,
                        b: f.shortMonths[n],
                        B: f.months[n],
                        m: q(n + 1),
                        y: t.toString().substr(2, 2),
                        Y: t,
                        H: q(k),
                        k: k,
                        I: q(k % 12 || 12),
                        l: k % 12 || 12,
                        M: q(e.get("Minutes", c)),
                        p: 12 > k ? "AM" : "PM",
                        P: 12 > k ? "am" : "pm",
                        S: q(c.getSeconds()),
                        L: q(Math.round(l % 1E3), 3)
                    }, a.dateFormats);
                a.objectEach(c, function (a, b) {
                    for (; -1 !== p.indexOf("%" + b);) p = p.replace("%" + b, "function" === typeof a ? a.call(e, l) : a)
                });
                return m ? p.substr(0, 1).toUpperCase() + p.substr(1) : p
            }, getTimeTicks: function (a, v, B, e) {
                var c = this, k = [], b = {}, g, n = new c.Date(v), t = a.unitRange, f = a.count || 1, u;
                if (C(v)) {
                    c.set("Milliseconds", n, t >= l.second ? 0 : f * Math.floor(c.get("Milliseconds", n) / f));
                    t >=
                    l.second && c.set("Seconds", n, t >= l.minute ? 0 : f * Math.floor(c.get("Seconds", n) / f));
                    t >= l.minute && c.set("Minutes", n, t >= l.hour ? 0 : f * Math.floor(c.get("Minutes", n) / f));
                    t >= l.hour && c.set("Hours", n, t >= l.day ? 0 : f * Math.floor(c.get("Hours", n) / f));
                    t >= l.day && c.set("Date", n, t >= l.month ? 1 : f * Math.floor(c.get("Date", n) / f));
                    t >= l.month && (c.set("Month", n, t >= l.year ? 0 : f * Math.floor(c.get("Month", n) / f)), g = c.get("FullYear", n));
                    t >= l.year && c.set("FullYear", n, g - g % f);
                    t === l.week && c.set("Date", n, c.get("Date", n) - c.get("Day", n) + m(e, 1));
                    g = c.get("FullYear", n);
                    e = c.get("Month", n);
                    var D = c.get("Date", n), q = c.get("Hours", n);
                    v = n.getTime();
                    c.variableTimezone && (u = B - v > 4 * l.month || c.getTimezoneOffset(v) !== c.getTimezoneOffset(B));
                    n = n.getTime();
                    for (v = 1; n < B;) k.push(n), n = t === l.year ? c.makeTime(g + v * f, 0) : t === l.month ? c.makeTime(g, e + v * f) : !u || t !== l.day && t !== l.week ? u && t === l.hour && 1 < f ? c.makeTime(g, e, D, q + v * f) : n + t * f : c.makeTime(g, e, D + v * f * (t === l.day ? 1 : 7)), v++;
                    k.push(n);
                    t <= l.hour && 1E4 > k.length && E(k, function (a) {
                        0 === a % 18E5 && "000000000" === c.dateFormat("%H%M%S%L",
                            a) && (b[a] = "day")
                    })
                }
                k.info = F(a, {higherRanks: b, totalRange: t * f});
                return k
            }
        }
    })(L);
    (function (a) {
        var C = a.color, E = a.merge;
        a.defaultOptions = {
            colors: "#7cb5ec #434348 #90ed7d #f7a35c #8085e9 #f15c80 #e4d354 #2b908f #f45b5b #91e8e1".split(" "),
            symbols: ["circle", "diamond", "square", "triangle", "triangle-down"],
            lang: {
                loading: "Loading...",
                months: "January February March April May June July August September October November December".split(" "),
                shortMonths: "Jan Feb Mar Apr May Jun Jul Aug Sep Oct Nov Dec".split(" "),
                weekdays: "Sunday Monday Tuesday Wednesday Thursday Friday Saturday".split(" "),
                decimalPoint: ".",
                numericSymbols: "kMGTPE".split(""),
                resetZoom: "Reset zoom",
                resetZoomTitle: "Reset zoom level 1:1",
                thousandsSep: " "
            },
            global: {},
            time: a.Time.prototype.defaultOptions,
            chart: {
                borderRadius: 0,
                defaultSeriesType: "line",
                ignoreHiddenSeries: !0,
                spacing: [10, 10, 15, 10],
                resetZoomButton: {theme: {zIndex: 6}, position: {align: "right", x: -10, y: 10}},
                width: null,
                height: null,
                borderColor: "#335cad",
                backgroundColor: "#ffffff",
                plotBorderColor: "#cccccc"
            },
            title: {text: "Chart title", align: "center", margin: 15, widthAdjust: -44},
            subtitle: {text: "", align: "center", widthAdjust: -44},
            plotOptions: {},
            labels: {style: {position: "absolute", color: "#333333"}},
            legend: {
                enabled: !0,
                align: "center",
                layout: "horizontal",
                labelFormatter: function () {
                    return this.name
                },
                borderColor: "#999999",
                borderRadius: 0,
                navigation: {activeColor: "#003399", inactiveColor: "#cccccc"},
                itemStyle: {color: "#333333", fontSize: "12px", fontWeight: "bold", textOverflow: "ellipsis"},
                itemHoverStyle: {color: "#000000"},
                itemHiddenStyle: {color: "#cccccc"},
                shadow: !1,
                itemCheckboxStyle: {position: "absolute", width: "13px", height: "13px"},
                squareSymbol: !0,
                symbolPadding: 5,
                verticalAlign: "bottom",
                x: 0,
                y: 0,
                title: {style: {fontWeight: "bold"}}
            },
            loading: {
                labelStyle: {fontWeight: "bold", position: "relative", top: "45%"},
                style: {position: "absolute", backgroundColor: "#ffffff", opacity: .5, textAlign: "center"}
            },
            tooltip: {
                enabled: !0,
                animation: a.svg,
                borderRadius: 3,
                dateTimeLabelFormats: {
                    millisecond: "%A, %b %e, %H:%M:%S.%L",
                    second: "%A, %b %e, %H:%M:%S",
                    minute: "%A, %b %e, %H:%M",
                    hour: "%A, %b %e, %H:%M",
                    day: "%A, %b %e, %Y",
                    week: "Week from %A, %b %e, %Y",
                    month: "%B %Y",
                    year: "%Y"
                },
                footerFormat: "",
                padding: 8,
                snap: a.isTouchDevice ? 25 : 10,
                backgroundColor: C("#f7f7f7").setOpacity(.85).get(),
                borderWidth: 1,
                headerFormat: '\x3cspan style\x3d"font-size: 10px"\x3e{point.key}\x3c/span\x3e\x3cbr/\x3e',
                pointFormat: '\x3cspan style\x3d"color:{point.color}"\x3e\u25cf\x3c/span\x3e {series.name}: \x3cb\x3e{point.y}\x3c/b\x3e\x3cbr/\x3e',
                shadow: !0,
                style: {
                    color: "#333333", cursor: "default", fontSize: "12px", pointerEvents: "none",
                    whiteSpace: "nowrap"
                }
            },
            credits: {
                enabled: !0,
                href: "http://www.highcharts.com",
                position: {align: "right", x: -10, verticalAlign: "bottom", y: -5},
                style: {cursor: "pointer", color: "#999999", fontSize: "9px"},
                text: "Highcharts.com"
            }
        };
        a.setOptions = function (C) {
            a.defaultOptions = E(!0, a.defaultOptions, C);
            a.time.update(E(a.defaultOptions.global, a.defaultOptions.time), !1);
            return a.defaultOptions
        };
        a.getOptions = function () {
            return a.defaultOptions
        };
        a.defaultPlotOptions = a.defaultOptions.plotOptions;
        a.time = new a.Time(E(a.defaultOptions.global,
            a.defaultOptions.time));
        a.dateFormat = function (C, r, m) {
            return a.time.dateFormat(C, r, m)
        }
    })(L);
    (function (a) {
        var C = a.correctFloat, E = a.defined, F = a.destroyObjectProperties, r = a.isNumber, m = a.merge, l = a.pick,
            w = a.deg2rad;
        a.Tick = function (a, l, m, e) {
            this.axis = a;
            this.pos = l;
            this.type = m || "";
            this.isNewLabel = this.isNew = !0;
            m || e || this.addLabel()
        };
        a.Tick.prototype = {
            addLabel: function () {
                var a = this.axis, v = a.options, B = a.chart, e = a.categories, c = a.names, k = this.pos,
                    b = v.labels, g = a.tickPositions, n = k === g[0], t = k === g[g.length - 1], c = e ?
                    l(e[k], c[k], k) : k, e = this.label, g = g.info, f;
                a.isDatetimeAxis && g && (f = v.dateTimeLabelFormats[g.higherRanks[k] || g.unitName]);
                this.isFirst = n;
                this.isLast = t;
                v = a.labelFormatter.call({
                    axis: a,
                    chart: B,
                    isFirst: n,
                    isLast: t,
                    dateTimeLabelFormat: f,
                    value: a.isLog ? C(a.lin2log(c)) : c,
                    pos: k
                });
                if (E(e)) e && e.attr({text: v}); else {
                    if (this.label = e = E(v) && b.enabled ? B.renderer.text(v, 0, 0, b.useHTML).css(m(b.style)).add(a.labelGroup) : null) e.textPxLength = e.getBBox().width;
                    this.rotation = 0
                }
            }, getLabelSize: function () {
                return this.label ? this.label.getBBox()[this.axis.horiz ?
                    "height" : "width"] : 0
            }, handleOverflow: function (a) {
                var p = this.axis, m = p.options.labels, e = a.x, c = p.chart.chartWidth, k = p.chart.spacing,
                    b = l(p.labelLeft, Math.min(p.pos, k[3])),
                    k = l(p.labelRight, Math.max(p.isRadial ? 0 : p.pos + p.len, c - k[1])), g = this.label,
                    n = this.rotation, t = {left: 0, center: .5, right: 1}[p.labelAlign || g.attr("align")],
                    f = g.getBBox().width, u = p.getSlotWidth(), D = u, q = 1, A, y = {};
                if (n || !1 === m.overflow) 0 > n && e - t * f < b ? A = Math.round(e / Math.cos(n * w) - b) : 0 < n && e + t * f > k && (A = Math.round((c - e) / Math.cos(n * w))); else if (c = e + (1 -
                        t) * f, e - t * f < b ? D = a.x + D * (1 - t) - b : c > k && (D = k - a.x + D * t, q = -1), D = Math.min(u, D), D < u && "center" === p.labelAlign && (a.x += q * (u - D - t * (u - Math.min(f, D)))), f > D || p.autoRotation && (g.styles || {}).width) A = D;
                A && (y.width = A, (m.style || {}).textOverflow || (y.textOverflow = "ellipsis"), g.css(y))
            }, getPosition: function (l, m, B, e) {
                var c = this.axis, k = c.chart, b = e && k.oldChartHeight || k.chartHeight;
                return {
                    x: l ? a.correctFloat(c.translate(m + B, null, null, e) + c.transB) : c.left + c.offset + (c.opposite ? (e && k.oldChartWidth || k.chartWidth) - c.right - c.left : 0),
                    y: l ?
                        b - c.bottom + c.offset - (c.opposite ? c.height : 0) : a.correctFloat(b - c.translate(m + B, null, null, e) - c.transB)
                }
            }, getLabelPosition: function (a, l, m, e, c, k, b, g) {
                var n = this.axis, t = n.transA, f = n.reversed, u = n.staggerLines, D = n.tickRotCorr || {x: 0, y: 0},
                    q = c.y, A = e || n.reserveSpaceDefault ? 0 : -n.labelOffset * ("center" === n.labelAlign ? .5 : 1);
                E(q) || (q = 0 === n.side ? m.rotation ? -8 : -m.getBBox().height : 2 === n.side ? D.y + 8 : Math.cos(m.rotation * w) * (D.y - m.getBBox(!1, 0).height / 2));
                a = a + c.x + A + D.x - (k && e ? k * t * (f ? -1 : 1) : 0);
                l = l + q - (k && !e ? k * t * (f ? 1 : -1) : 0);
                u && (m = b / (g || 1) % u, n.opposite && (m = u - m - 1), l += n.labelOffset / u * m);
                return {x: a, y: Math.round(l)}
            }, getMarkPath: function (a, l, m, e, c, k) {
                return k.crispLine(["M", a, l, "L", a + (c ? 0 : -m), l + (c ? m : 0)], e)
            }, renderGridLine: function (a, l, m) {
                var e = this.axis, c = e.options, k = this.gridLine, b = {}, g = this.pos, n = this.type,
                    t = e.tickmarkOffset, f = e.chart.renderer, u = n ? n + "Grid" : "grid", D = c[u + "LineWidth"],
                    q = c[u + "LineColor"], c = c[u + "LineDashStyle"];
                k || (b.stroke = q, b["stroke-width"] = D, c && (b.dashstyle = c), n || (b.zIndex = 1), a && (b.opacity = 0), this.gridLine =
                    k = f.path().attr(b).addClass("highcharts-" + (n ? n + "-" : "") + "grid-line").add(e.gridGroup));
                if (!a && k && (a = e.getPlotLinePath(g + t, k.strokeWidth() * m, a, !0))) k[this.isNew ? "attr" : "animate"]({
                    d: a,
                    opacity: l
                })
            }, renderMark: function (a, m, B) {
                var e = this.axis, c = e.options, k = e.chart.renderer, b = this.type, g = b ? b + "Tick" : "tick",
                    n = e.tickSize(g), t = this.mark, f = !t, u = a.x;
                a = a.y;
                var D = l(c[g + "Width"], !b && e.isXAxis ? 1 : 0), c = c[g + "Color"];
                n && (e.opposite && (n[0] = -n[0]), f && (this.mark = t = k.path().addClass("highcharts-" + (b ? b + "-" : "") + "tick").add(e.axisGroup),
                    t.attr({
                        stroke: c,
                        "stroke-width": D
                    })), t[f ? "attr" : "animate"]({
                    d: this.getMarkPath(u, a, n[0], t.strokeWidth() * B, e.horiz, k),
                    opacity: m
                }))
            }, renderLabel: function (a, m, B, e) {
                var c = this.axis, k = c.horiz, b = c.options, g = this.label, n = b.labels, t = n.step,
                    c = c.tickmarkOffset, f = !0, u = a.x;
                a = a.y;
                g && r(u) && (g.xy = a = this.getLabelPosition(u, a, g, k, n, c, e, t), this.isFirst && !this.isLast && !l(b.showFirstLabel, 1) || this.isLast && !this.isFirst && !l(b.showLastLabel, 1) ? f = !1 : !k || n.step || n.rotation || m || 0 === B || this.handleOverflow(a), t && e % t && (f = !1),
                    f && r(a.y) ? (a.opacity = B, g[this.isNewLabel ? "attr" : "animate"](a), this.isNewLabel = !1) : (g.attr("y", -9999), this.isNewLabel = !0))
            }, render: function (m, v, B) {
                var e = this.axis, c = e.horiz, k = this.getPosition(c, this.pos, e.tickmarkOffset, v), b = k.x,
                    g = k.y, e = c && b === e.pos + e.len || !c && g === e.pos ? -1 : 1;
                B = l(B, 1);
                this.isActive = !0;
                this.renderGridLine(v, B, e);
                this.renderMark(k, B, e);
                this.renderLabel(k, v, B, m);
                this.isNew = !1;
                a.fireEvent(this, "afterRender")
            }, destroy: function () {
                F(this, this.axis)
            }
        }
    })(L);
    var da = function (a) {
        var C = a.addEvent,
            E = a.animObject, F = a.arrayMax, r = a.arrayMin, m = a.color, l = a.correctFloat, w = a.defaultOptions,
            p = a.defined, v = a.deg2rad, B = a.destroyObjectProperties, e = a.each, c = a.extend, k = a.fireEvent,
            b = a.format, g = a.getMagnitude, n = a.grep, t = a.inArray, f = a.isArray, u = a.isNumber, D = a.isString,
            q = a.merge, A = a.normalizeTickInterval, y = a.objectEach, H = a.pick, I = a.removeEvent, J = a.splat,
            d = a.syncTimeout, z = a.Tick, G = function () {
                this.init.apply(this, arguments)
            };
        a.extend(G.prototype, {
            defaultOptions: {
                dateTimeLabelFormats: {
                    millisecond: "%H:%M:%S.%L",
                    second: "%H:%M:%S",
                    minute: "%H:%M",
                    hour: "%H:%M",
                    day: "%e. %b",
                    week: "%e. %b",
                    month: "%b '%y",
                    year: "%Y"
                },
                endOnTick: !1,
                labels: {enabled: !0, style: {color: "#666666", cursor: "default", fontSize: "11px"}, x: 0},
                maxPadding: .01,
                minorTickLength: 2,
                minorTickPosition: "outside",
                minPadding: .01,
                startOfWeek: 1,
                startOnTick: !1,
                tickLength: 10,
                tickmarkPlacement: "between",
                tickPixelInterval: 100,
                tickPosition: "outside",
                title: {align: "middle", style: {color: "#666666"}},
                type: "linear",
                minorGridLineColor: "#f2f2f2",
                minorGridLineWidth: 1,
                minorTickColor: "#999999",
                lineColor: "#ccd6eb",
                lineWidth: 1,
                gridLineColor: "#e6e6e6",
                tickColor: "#ccd6eb"
            },
            defaultYAxisOptions: {
                endOnTick: !0,
                tickPixelInterval: 72,
                showLastLabel: !0,
                labels: {x: -8},
                maxPadding: .05,
                minPadding: .05,
                startOnTick: !0,
                title: {rotation: 270, text: "Values"},
                stackLabels: {
                    allowOverlap: !1, enabled: !1, formatter: function () {
                        return a.numberFormat(this.total, -1)
                    }, style: {fontSize: "11px", fontWeight: "bold", color: "#000000", textOutline: "1px contrast"}
                },
                gridLineWidth: 1,
                lineWidth: 0
            },
            defaultLeftAxisOptions: {labels: {x: -15}, title: {rotation: 270}},
            defaultRightAxisOptions: {labels: {x: 15}, title: {rotation: 90}},
            defaultBottomAxisOptions: {labels: {autoRotation: [-45], x: 0}, title: {rotation: 0}},
            defaultTopAxisOptions: {labels: {autoRotation: [-45], x: 0}, title: {rotation: 0}},
            init: function (a, d) {
                var h = d.isX, x = this;
                x.chart = a;
                x.horiz = a.inverted && !x.isZAxis ? !h : h;
                x.isXAxis = h;
                x.coll = x.coll || (h ? "xAxis" : "yAxis");
                x.opposite = d.opposite;
                x.side = d.side || (x.horiz ? x.opposite ? 0 : 2 : x.opposite ? 1 : 3);
                x.setOptions(d);
                var b = this.options, f = b.type;
                x.labelFormatter = b.labels.formatter ||
                    x.defaultLabelFormatter;
                x.userOptions = d;
                x.minPixelPadding = 0;
                x.reversed = b.reversed;
                x.visible = !1 !== b.visible;
                x.zoomEnabled = !1 !== b.zoomEnabled;
                x.hasNames = "category" === f || !0 === b.categories;
                x.categories = b.categories || x.hasNames;
                x.names || (x.names = [], x.names.keys = {});
                x.plotLinesAndBandsGroups = {};
                x.isLog = "logarithmic" === f;
                x.isDatetimeAxis = "datetime" === f;
                x.positiveValuesOnly = x.isLog && !x.allowNegativeLog;
                x.isLinked = p(b.linkedTo);
                x.ticks = {};
                x.labelEdge = [];
                x.minorTicks = {};
                x.plotLinesAndBands = [];
                x.alternateBands =
                    {};
                x.len = 0;
                x.minRange = x.userMinRange = b.minRange || b.maxZoom;
                x.range = b.range;
                x.offset = b.offset || 0;
                x.stacks = {};
                x.oldStacks = {};
                x.stacksTouched = 0;
                x.max = null;
                x.min = null;
                x.crosshair = H(b.crosshair, J(a.options.tooltip.crosshairs)[h ? 0 : 1], !1);
                d = x.options.events;
                -1 === t(x, a.axes) && (h ? a.axes.splice(a.xAxis.length, 0, x) : a.axes.push(x), a[x.coll].push(x));
                x.series = x.series || [];
                a.inverted && !x.isZAxis && h && void 0 === x.reversed && (x.reversed = !0);
                y(d, function (a, h) {
                    C(x, h, a)
                });
                x.lin2log = b.linearToLogConverter || x.lin2log;
                x.isLog &&
                (x.val2lin = x.log2lin, x.lin2val = x.lin2log)
            },
            setOptions: function (a) {
                this.options = q(this.defaultOptions, "yAxis" === this.coll && this.defaultYAxisOptions, [this.defaultTopAxisOptions, this.defaultRightAxisOptions, this.defaultBottomAxisOptions, this.defaultLeftAxisOptions][this.side], q(w[this.coll], a))
            },
            defaultLabelFormatter: function () {
                var h = this.axis, d = this.value, f = h.chart.time, c = h.categories, g = this.dateTimeLabelFormat,
                    e = w.lang, z = e.numericSymbols, e = e.numericSymbolMagnitude || 1E3, n = z && z.length, q,
                    u = h.options.labels.format,
                    h = h.isLog ? Math.abs(d) : h.tickInterval;
                if (u) q = b(u, this, f); else if (c) q = d; else if (g) q = f.dateFormat(g, d); else if (n && 1E3 <= h) for (; n-- && void 0 === q;) f = Math.pow(e, n + 1), h >= f && 0 === 10 * d % f && null !== z[n] && 0 !== d && (q = a.numberFormat(d / f, -1) + z[n]);
                void 0 === q && (q = 1E4 <= Math.abs(d) ? a.numberFormat(d, -1) : a.numberFormat(d, -1, void 0, ""));
                return q
            },
            getSeriesExtremes: function () {
                var a = this, d = a.chart;
                a.hasVisibleSeries = !1;
                a.dataMin = a.dataMax = a.threshold = null;
                a.softThreshold = !a.isXAxis;
                a.buildStacks && a.buildStacks();
                e(a.series,
                    function (h) {
                        if (h.visible || !d.options.chart.ignoreHiddenSeries) {
                            var x = h.options, b = x.threshold, f;
                            a.hasVisibleSeries = !0;
                            a.positiveValuesOnly && 0 >= b && (b = null);
                            if (a.isXAxis) x = h.xData, x.length && (h = r(x), f = F(x), u(h) || h instanceof Date || (x = n(x, u), h = r(x), f = F(x)), x.length && (a.dataMin = Math.min(H(a.dataMin, x[0], h), h), a.dataMax = Math.max(H(a.dataMax, x[0], f), f))); else if (h.getExtremes(), f = h.dataMax, h = h.dataMin, p(h) && p(f) && (a.dataMin = Math.min(H(a.dataMin, h), h), a.dataMax = Math.max(H(a.dataMax, f), f)), p(b) && (a.threshold =
                                    b), !x.softThreshold || a.positiveValuesOnly) a.softThreshold = !1
                        }
                    })
            },
            translate: function (a, d, b, f, c, g) {
                var h = this.linkedParent || this, x = 1, e = 0, z = f ? h.oldTransA : h.transA;
                f = f ? h.oldMin : h.min;
                var q = h.minPixelPadding;
                c = (h.isOrdinal || h.isBroken || h.isLog && c) && h.lin2val;
                z || (z = h.transA);
                b && (x *= -1, e = h.len);
                h.reversed && (x *= -1, e -= x * (h.sector || h.len));
                d ? (a = (a * x + e - q) / z + f, c && (a = h.lin2val(a))) : (c && (a = h.val2lin(a)), a = u(f) ? x * (a - f) * z + e + x * q + (u(g) ? z * g : 0) : void 0);
                return a
            },
            toPixels: function (a, d) {
                return this.translate(a, !1, !this.horiz,
                    null, !0) + (d ? 0 : this.pos)
            },
            toValue: function (a, d) {
                return this.translate(a - (d ? 0 : this.pos), !0, !this.horiz, null, !0)
            },
            getPlotLinePath: function (a, d, b, f, c) {
                var h = this.chart, x = this.left, g = this.top, e, z, q = b && h.oldChartHeight || h.chartHeight,
                    n = b && h.oldChartWidth || h.chartWidth, t;
                e = this.transB;
                var A = function (a, h, d) {
                    if (a < h || a > d) f ? a = Math.min(Math.max(h, a), d) : t = !0;
                    return a
                };
                c = H(c, this.translate(a, null, null, b));
                c = Math.min(Math.max(-1E5, c), 1E5);
                a = b = Math.round(c + e);
                e = z = Math.round(q - c - e);
                u(c) ? this.horiz ? (e = g, z = q - this.bottom,
                    a = b = A(a, x, x + this.width)) : (a = x, b = n - this.right, e = z = A(e, g, g + this.height)) : (t = !0, f = !1);
                return t && !f ? null : h.renderer.crispLine(["M", a, e, "L", b, z], d || 1)
            },
            getLinearTickPositions: function (a, d, b) {
                var h, x = l(Math.floor(d / a) * a);
                b = l(Math.ceil(b / a) * a);
                var f = [], c;
                l(x + a) === x && (c = 20);
                if (this.single) return [d];
                for (d = x; d <= b;) {
                    f.push(d);
                    d = l(d + a, c);
                    if (d === h) break;
                    h = d
                }
                return f
            },
            getMinorTickInterval: function () {
                var a = this.options;
                return !0 === a.minorTicks ? H(a.minorTickInterval, "auto") : !1 === a.minorTicks ? null : a.minorTickInterval
            },
            getMinorTickPositions: function () {
                var a = this, d = a.options, b = a.tickPositions, f = a.minorTickInterval, c = [],
                    g = a.pointRangePadding || 0, z = a.min - g, g = a.max + g, q = g - z;
                if (q && q / f < a.len / 3) if (a.isLog) e(this.paddedTicks, function (h, d, x) {
                    d && c.push.apply(c, a.getLogTickPositions(f, x[d - 1], x[d], !0))
                }); else if (a.isDatetimeAxis && "auto" === this.getMinorTickInterval()) c = c.concat(a.getTimeTicks(a.normalizeTimeTickInterval(f), z, g, d.startOfWeek)); else for (d = z + (b[0] - z) % f; d <= g && d !== c[0]; d += f) c.push(d);
                0 !== c.length && a.trimTicks(c);
                return c
            },
            adjustForMinRange: function () {
                var a = this.options, d = this.min, b = this.max, f, c, g, z, q, n, u, t;
                this.isXAxis && void 0 === this.minRange && !this.isLog && (p(a.min) || p(a.max) ? this.minRange = null : (e(this.series, function (a) {
                    n = a.xData;
                    for (z = u = a.xIncrement ? 1 : n.length - 1; 0 < z; z--) if (q = n[z] - n[z - 1], void 0 === g || q < g) g = q
                }), this.minRange = Math.min(5 * g, this.dataMax - this.dataMin)));
                b - d < this.minRange && (c = this.dataMax - this.dataMin >= this.minRange, t = this.minRange, f = (t - b + d) / 2, f = [d - f, H(a.min, d - f)], c && (f[2] = this.isLog ? this.log2lin(this.dataMin) :
                    this.dataMin), d = F(f), b = [d + t, H(a.max, d + t)], c && (b[2] = this.isLog ? this.log2lin(this.dataMax) : this.dataMax), b = r(b), b - d < t && (f[0] = b - t, f[1] = H(a.min, b - t), d = F(f)));
                this.min = d;
                this.max = b
            },
            getClosest: function () {
                var a;
                this.categories ? a = 1 : e(this.series, function (h) {
                    var d = h.closestPointRange, x = h.visible || !h.chart.options.chart.ignoreHiddenSeries;
                    !h.noSharedTooltip && p(d) && x && (a = p(a) ? Math.min(a, d) : d)
                });
                return a
            },
            nameToX: function (a) {
                var h = f(this.categories), d = h ? this.categories : this.names, b = a.options.x, c;
                a.series.requireSorting =
                    !1;
                p(b) || (b = !1 === this.options.uniqueNames ? a.series.autoIncrement() : h ? t(a.name, d) : H(d.keys[a.name], -1));
                -1 === b ? h || (c = d.length) : c = b;
                void 0 !== c && (this.names[c] = a.name, this.names.keys[a.name] = c);
                return c
            },
            updateNames: function () {
                var h = this, d = this.names;
                0 < d.length && (e(a.keys(d.keys), function (a) {
                    delete d.keys[a]
                }), d.length = 0, this.minRange = this.userMinRange, e(this.series || [], function (a) {
                    a.xIncrement = null;
                    if (!a.points || a.isDirtyData) a.processData(), a.generatePoints();
                    e(a.points, function (d, b) {
                        var x;
                        d.options &&
                        (x = h.nameToX(d), void 0 !== x && x !== d.x && (d.x = x, a.xData[b] = x))
                    })
                }))
            },
            setAxisTranslation: function (a) {
                var h = this, d = h.max - h.min, b = h.axisPointRange || 0, f, c = 0, g = 0, z = h.linkedParent,
                    q = !!h.categories, n = h.transA, t = h.isXAxis;
                if (t || q || b) f = h.getClosest(), z ? (c = z.minPointOffset, g = z.pointRangePadding) : e(h.series, function (a) {
                    var d = q ? 1 : t ? H(a.options.pointRange, f, 0) : h.axisPointRange || 0;
                    a = a.options.pointPlacement;
                    b = Math.max(b, d);
                    h.single || (c = Math.max(c, D(a) ? 0 : d / 2), g = Math.max(g, "on" === a ? 0 : d))
                }), z = h.ordinalSlope && f ? h.ordinalSlope /
                    f : 1, h.minPointOffset = c *= z, h.pointRangePadding = g *= z, h.pointRange = Math.min(b, d), t && (h.closestPointRange = f);
                a && (h.oldTransA = n);
                h.translationSlope = h.transA = n = h.options.staticScale || h.len / (d + g || 1);
                h.transB = h.horiz ? h.left : h.bottom;
                h.minPixelPadding = n * c
            },
            minFromRange: function () {
                return this.max - this.range
            },
            setTickInterval: function (h) {
                var d = this, b = d.chart, f = d.options, c = d.isLog, z = d.log2lin, q = d.isDatetimeAxis,
                    n = d.isXAxis, t = d.isLinked, G = f.maxPadding, y = f.minPadding, I = f.tickInterval,
                    D = f.tickPixelInterval, J = d.categories,
                    m = d.threshold, v = d.softThreshold, B, w, r, C;
                q || J || t || this.getTickAmount();
                r = H(d.userMin, f.min);
                C = H(d.userMax, f.max);
                t ? (d.linkedParent = b[d.coll][f.linkedTo], b = d.linkedParent.getExtremes(), d.min = H(b.min, b.dataMin), d.max = H(b.max, b.dataMax), f.type !== d.linkedParent.options.type && a.error(11, 1)) : (!v && p(m) && (d.dataMin >= m ? (B = m, y = 0) : d.dataMax <= m && (w = m, G = 0)), d.min = H(r, B, d.dataMin), d.max = H(C, w, d.dataMax));
                c && (d.positiveValuesOnly && !h && 0 >= Math.min(d.min, H(d.dataMin, d.min)) && a.error(10, 1), d.min = l(z(d.min), 15), d.max =
                    l(z(d.max), 15));
                d.range && p(d.max) && (d.userMin = d.min = r = Math.max(d.dataMin, d.minFromRange()), d.userMax = C = d.max, d.range = null);
                k(d, "foundExtremes");
                d.beforePadding && d.beforePadding();
                d.adjustForMinRange();
                !(J || d.axisPointRange || d.usePercentage || t) && p(d.min) && p(d.max) && (z = d.max - d.min) && (!p(r) && y && (d.min -= z * y), !p(C) && G && (d.max += z * G));
                u(f.softMin) && !u(d.userMin) && (d.min = Math.min(d.min, f.softMin));
                u(f.softMax) && !u(d.userMax) && (d.max = Math.max(d.max, f.softMax));
                u(f.floor) && (d.min = Math.max(d.min, f.floor));
                u(f.ceiling) &&
                (d.max = Math.min(d.max, f.ceiling));
                v && p(d.dataMin) && (m = m || 0, !p(r) && d.min < m && d.dataMin >= m ? d.min = m : !p(C) && d.max > m && d.dataMax <= m && (d.max = m));
                d.tickInterval = d.min === d.max || void 0 === d.min || void 0 === d.max ? 1 : t && !I && D === d.linkedParent.options.tickPixelInterval ? I = d.linkedParent.tickInterval : H(I, this.tickAmount ? (d.max - d.min) / Math.max(this.tickAmount - 1, 1) : void 0, J ? 1 : (d.max - d.min) * D / Math.max(d.len, D));
                n && !h && e(d.series, function (a) {
                    a.processData(d.min !== d.oldMin || d.max !== d.oldMax)
                });
                d.setAxisTranslation(!0);
                d.beforeSetTickPositions &&
                d.beforeSetTickPositions();
                d.postProcessTickInterval && (d.tickInterval = d.postProcessTickInterval(d.tickInterval));
                d.pointRange && !I && (d.tickInterval = Math.max(d.pointRange, d.tickInterval));
                h = H(f.minTickInterval, d.isDatetimeAxis && d.closestPointRange);
                !I && d.tickInterval < h && (d.tickInterval = h);
                q || c || I || (d.tickInterval = A(d.tickInterval, null, g(d.tickInterval), H(f.allowDecimals, !(.5 < d.tickInterval && 5 > d.tickInterval && 1E3 < d.max && 9999 > d.max)), !!this.tickAmount));
                this.tickAmount || (d.tickInterval = d.unsquish());
                this.setTickPositions()
            },
            setTickPositions: function () {
                var a = this.options, d, b = a.tickPositions;
                d = this.getMinorTickInterval();
                var f = a.tickPositioner, c = a.startOnTick, g = a.endOnTick;
                this.tickmarkOffset = this.categories && "between" === a.tickmarkPlacement && 1 === this.tickInterval ? .5 : 0;
                this.minorTickInterval = "auto" === d && this.tickInterval ? this.tickInterval / 5 : d;
                this.single = this.min === this.max && p(this.min) && !this.tickAmount && (parseInt(this.min, 10) === this.min || !1 !== a.allowDecimals);
                this.tickPositions = d = b && b.slice();
                !d && (d = this.isDatetimeAxis ? this.getTimeTicks(this.normalizeTimeTickInterval(this.tickInterval, a.units), this.min, this.max, a.startOfWeek, this.ordinalPositions, this.closestPointRange, !0) : this.isLog ? this.getLogTickPositions(this.tickInterval, this.min, this.max) : this.getLinearTickPositions(this.tickInterval, this.min, this.max), d.length > this.len && (d = [d[0], d.pop()], d[0] === d[1] && (d.length = 1)), this.tickPositions = d, f && (f = f.apply(this, [this.min, this.max]))) && (this.tickPositions = d = f);
                this.paddedTicks = d.slice(0);
                this.trimTicks(d, c, g);
                this.isLinked || (this.single && 2 > d.length && (this.min -= .5, this.max += .5), b || f || this.adjustTickAmount())
            },
            trimTicks: function (a, d, b) {
                var h = a[0], f = a[a.length - 1], c = this.minPointOffset || 0;
                if (!this.isLinked) {
                    if (d && -Infinity !== h) this.min = h; else for (; this.min - c > a[0];) a.shift();
                    if (b) this.max = f; else for (; this.max + c < a[a.length - 1];) a.pop();
                    0 === a.length && p(h) && !this.options.tickPositions && a.push((f + h) / 2)
                }
            },
            alignToOthers: function () {
                var a = {}, d, b = this.options;
                !1 === this.chart.options.chart.alignTicks ||
                !1 === b.alignTicks || this.isLog || e(this.chart[this.coll], function (h) {
                    var b = h.options, b = [h.horiz ? b.left : b.top, b.width, b.height, b.pane].join();
                    h.series.length && (a[b] ? d = !0 : a[b] = 1)
                });
                return d
            },
            getTickAmount: function () {
                var a = this.options, d = a.tickAmount, b = a.tickPixelInterval;
                !p(a.tickInterval) && this.len < b && !this.isRadial && !this.isLog && a.startOnTick && a.endOnTick && (d = 2);
                !d && this.alignToOthers() && (d = Math.ceil(this.len / b) + 1);
                4 > d && (this.finalTickAmt = d, d = 5);
                this.tickAmount = d
            },
            adjustTickAmount: function () {
                var a =
                        this.tickInterval, d = this.tickPositions, b = this.tickAmount, f = this.finalTickAmt,
                    c = d && d.length, g = H(this.threshold, this.softThreshold ? 0 : null);
                if (this.hasData()) {
                    if (c < b) {
                        for (; d.length < b;) d.length % 2 || this.min === g ? d.push(l(d[d.length - 1] + a)) : d.unshift(l(d[0] - a));
                        this.transA *= (c - 1) / (b - 1);
                        this.min = d[0];
                        this.max = d[d.length - 1]
                    } else c > b && (this.tickInterval *= 2, this.setTickPositions());
                    if (p(f)) {
                        for (a = b = d.length; a--;) (3 === f && 1 === a % 2 || 2 >= f && 0 < a && a < b - 1) && d.splice(a, 1);
                        this.finalTickAmt = void 0
                    }
                }
            },
            setScale: function () {
                var a,
                    d;
                this.oldMin = this.min;
                this.oldMax = this.max;
                this.oldAxisLength = this.len;
                this.setAxisSize();
                d = this.len !== this.oldAxisLength;
                e(this.series, function (d) {
                    if (d.isDirtyData || d.isDirty || d.xAxis.isDirty) a = !0
                });
                d || a || this.isLinked || this.forceRedraw || this.userMin !== this.oldUserMin || this.userMax !== this.oldUserMax || this.alignToOthers() ? (this.resetStacks && this.resetStacks(), this.forceRedraw = !1, this.getSeriesExtremes(), this.setTickInterval(), this.oldUserMin = this.userMin, this.oldUserMax = this.userMax, this.isDirty ||
                (this.isDirty = d || this.min !== this.oldMin || this.max !== this.oldMax)) : this.cleanStacks && this.cleanStacks();
                k(this, "afterSetScale")
            },
            setExtremes: function (a, d, b, f, g) {
                var h = this, z = h.chart;
                b = H(b, !0);
                e(h.series, function (a) {
                    delete a.kdTree
                });
                g = c(g, {min: a, max: d});
                k(h, "setExtremes", g, function () {
                    h.userMin = a;
                    h.userMax = d;
                    h.eventArgs = g;
                    b && z.redraw(f)
                })
            },
            zoom: function (a, d) {
                var h = this.dataMin, b = this.dataMax, f = this.options, c = Math.min(h, H(f.min, h)),
                    f = Math.max(b, H(f.max, b));
                if (a !== this.min || d !== this.max) this.allowZoomOutside ||
                (p(h) && (a < c && (a = c), a > f && (a = f)), p(b) && (d < c && (d = c), d > f && (d = f))), this.displayBtn = void 0 !== a || void 0 !== d, this.setExtremes(a, d, !1, void 0, {trigger: "zoom"});
                return !0
            },
            setAxisSize: function () {
                var d = this.chart, b = this.options, f = b.offsets || [0, 0, 0, 0], c = this.horiz,
                    g = this.width = Math.round(a.relativeLength(H(b.width, d.plotWidth - f[3] + f[1]), d.plotWidth)),
                    z = this.height = Math.round(a.relativeLength(H(b.height, d.plotHeight - f[0] + f[2]), d.plotHeight)),
                    e = this.top = Math.round(a.relativeLength(H(b.top, d.plotTop + f[0]), d.plotHeight,
                        d.plotTop)),
                    b = this.left = Math.round(a.relativeLength(H(b.left, d.plotLeft + f[3]), d.plotWidth, d.plotLeft));
                this.bottom = d.chartHeight - z - e;
                this.right = d.chartWidth - g - b;
                this.len = Math.max(c ? g : z, 0);
                this.pos = c ? b : e
            },
            getExtremes: function () {
                var a = this.isLog, d = this.lin2log;
                return {
                    min: a ? l(d(this.min)) : this.min,
                    max: a ? l(d(this.max)) : this.max,
                    dataMin: this.dataMin,
                    dataMax: this.dataMax,
                    userMin: this.userMin,
                    userMax: this.userMax
                }
            },
            getThreshold: function (a) {
                var d = this.isLog, h = this.lin2log, b = d ? h(this.min) : this.min, d = d ? h(this.max) :
                    this.max;
                null === a ? a = b : b > a ? a = b : d < a && (a = d);
                return this.translate(a, 0, 1, 0, 1)
            },
            autoLabelAlign: function (a) {
                a = (H(a, 0) - 90 * this.side + 720) % 360;
                return 15 < a && 165 > a ? "right" : 195 < a && 345 > a ? "left" : "center"
            },
            tickSize: function (a) {
                var d = this.options, h = d[a + "Length"], b = H(d[a + "Width"], "tick" === a && this.isXAxis ? 1 : 0);
                if (b && h) return "inside" === d[a + "Position"] && (h = -h), [h, b]
            },
            labelMetrics: function () {
                var a = this.tickPositions && this.tickPositions[0] || 0;
                return this.chart.renderer.fontMetrics(this.options.labels.style && this.options.labels.style.fontSize,
                    this.ticks[a] && this.ticks[a].label)
            },
            unsquish: function () {
                var a = this.options.labels, d = this.horiz, b = this.tickInterval, f = b,
                    c = this.len / (((this.categories ? 1 : 0) + this.max - this.min) / b), g, z = a.rotation,
                    q = this.labelMetrics(), n, t = Number.MAX_VALUE, u, A = function (a) {
                        a /= c || 1;
                        a = 1 < a ? Math.ceil(a) : 1;
                        return a * b
                    };
                d ? (u = !a.staggerLines && !a.step && (p(z) ? [z] : c < H(a.autoRotationLimit, 80) && a.autoRotation)) && e(u, function (a) {
                    var d;
                    if (a === z || a && -90 <= a && 90 >= a) n = A(Math.abs(q.h / Math.sin(v * a))), d = n + Math.abs(a / 360), d < t && (t = d, g = a, f = n)
                }) :
                    a.step || (f = A(q.h));
                this.autoRotation = u;
                this.labelRotation = H(g, z);
                return f
            },
            getSlotWidth: function () {
                var a = this.chart, d = this.horiz, b = this.options.labels,
                    f = Math.max(this.tickPositions.length - (this.categories ? 0 : 1), 1), c = a.margin[3];
                return d && 2 > (b.step || 0) && !b.rotation && (this.staggerLines || 1) * this.len / f || !d && (b.style && parseInt(b.style.width, 10) || c && c - a.spacing[3] || .33 * a.chartWidth)
            },
            renderUnsquish: function () {
                var a = this.chart, d = a.renderer, b = this.tickPositions, f = this.ticks, c = this.options.labels,
                    g = this.horiz,
                    z = this.getSlotWidth(), q = Math.max(1, Math.round(z - 2 * (c.padding || 5))), n = {},
                    t = this.labelMetrics(), u = c.style && c.style.textOverflow, A, G, y = 0, I;
                D(c.rotation) || (n.rotation = c.rotation || 0);
                e(b, function (a) {
                    (a = f[a]) && a.label && a.label.textPxLength > y && (y = a.label.textPxLength)
                });
                this.maxLabelLength = y;
                if (this.autoRotation) y > q && y > t.h ? n.rotation = this.labelRotation : this.labelRotation = 0; else if (z && (A = q, !u)) for (G = "clip", q = b.length; !g && q--;) if (I = b[q], I = f[I].label) I.styles && "ellipsis" === I.styles.textOverflow ? I.css({textOverflow: "clip"}) :
                    I.textPxLength > z && I.css({width: z + "px"}), I.getBBox().height > this.len / b.length - (t.h - t.f) && (I.specificTextOverflow = "ellipsis");
                n.rotation && (A = y > .5 * a.chartHeight ? .33 * a.chartHeight : a.chartHeight, u || (G = "ellipsis"));
                if (this.labelAlign = c.align || this.autoLabelAlign(this.labelRotation)) n.align = this.labelAlign;
                e(b, function (a) {
                    var d = (a = f[a]) && a.label;
                    d && (d.attr(n), !A || c.style && c.style.width || !(A < d.textPxLength || "SPAN" === d.element.tagName) || d.css({
                        width: A,
                        textOverflow: d.specificTextOverflow || G
                    }), delete d.specificTextOverflow,
                        a.rotation = n.rotation)
                });
                this.tickRotCorr = d.rotCorr(t.b, this.labelRotation || 0, 0 !== this.side)
            },
            hasData: function () {
                return this.hasVisibleSeries || p(this.min) && p(this.max) && this.tickPositions && 0 < this.tickPositions.length
            },
            addTitle: function (a) {
                var d = this.chart.renderer, h = this.horiz, b = this.opposite, f = this.options.title, c;
                this.axisTitle || ((c = f.textAlign) || (c = (h ? {
                    low: "left",
                    middle: "center",
                    high: "right"
                } : {
                    low: b ? "right" : "left",
                    middle: "center",
                    high: b ? "left" : "right"
                })[f.align]), this.axisTitle = d.text(f.text, 0,
                    0, f.useHTML).attr({
                    zIndex: 7,
                    rotation: f.rotation || 0,
                    align: c
                }).addClass("highcharts-axis-title").css(q(f.style)).add(this.axisGroup), this.axisTitle.isNew = !0);
                f.style.width || this.isRadial || this.axisTitle.css({width: this.len});
                this.axisTitle[a ? "show" : "hide"](!0)
            },
            generateTick: function (a) {
                var d = this.ticks;
                d[a] ? d[a].addLabel() : d[a] = new z(this, a)
            },
            getOffset: function () {
                var a = this, d = a.chart, b = d.renderer, f = a.options, c = a.tickPositions, g = a.ticks, z = a.horiz,
                    q = a.side, n = d.inverted && !a.isZAxis ? [1, 0, 3, 2][q] : q, t, u,
                    A = 0, G, I = 0, k = f.title, D = f.labels, J = 0, l = d.axisOffset, d = d.clipOffset,
                    m = [-1, 1, 1, -1][q], v = f.className, B = a.axisParent, w = this.tickSize("tick");
                t = a.hasData();
                a.showAxis = u = t || H(f.showEmpty, !0);
                a.staggerLines = a.horiz && D.staggerLines;
                a.axisGroup || (a.gridGroup = b.g("grid").attr({zIndex: f.gridZIndex || 1}).addClass("highcharts-" + this.coll.toLowerCase() + "-grid " + (v || "")).add(B), a.axisGroup = b.g("axis").attr({zIndex: f.zIndex || 2}).addClass("highcharts-" + this.coll.toLowerCase() + " " + (v || "")).add(B), a.labelGroup = b.g("axis-labels").attr({
                    zIndex: D.zIndex ||
                    7
                }).addClass("highcharts-" + a.coll.toLowerCase() + "-labels " + (v || "")).add(B));
                t || a.isLinked ? (e(c, function (d, b) {
                    a.generateTick(d, b)
                }), a.renderUnsquish(), a.reserveSpaceDefault = 0 === q || 2 === q || {
                    1: "left",
                    3: "right"
                }[q] === a.labelAlign, H(D.reserveSpace, "center" === a.labelAlign ? !0 : null, a.reserveSpaceDefault) && e(c, function (a) {
                    J = Math.max(g[a].getLabelSize(), J)
                }), a.staggerLines && (J *= a.staggerLines), a.labelOffset = J * (a.opposite ? -1 : 1)) : y(g, function (a, d) {
                    a.destroy();
                    delete g[d]
                });
                k && k.text && !1 !== k.enabled && (a.addTitle(u),
                u && !1 !== k.reserveSpace && (a.titleOffset = A = a.axisTitle.getBBox()[z ? "height" : "width"], G = k.offset, I = p(G) ? 0 : H(k.margin, z ? 5 : 10)));
                a.renderLine();
                a.offset = m * H(f.offset, l[q]);
                a.tickRotCorr = a.tickRotCorr || {x: 0, y: 0};
                b = 0 === q ? -a.labelMetrics().h : 2 === q ? a.tickRotCorr.y : 0;
                I = Math.abs(J) + I;
                J && (I = I - b + m * (z ? H(D.y, a.tickRotCorr.y + 8 * m) : D.x));
                a.axisTitleMargin = H(G, I);
                l[q] = Math.max(l[q], a.axisTitleMargin + A + m * a.offset, I, t && c.length && w ? w[0] + m * a.offset : 0);
                f = f.offset ? 0 : 2 * Math.floor(a.axisLine.strokeWidth() / 2);
                d[n] = Math.max(d[n],
                    f)
            },
            getLinePath: function (a) {
                var d = this.chart, b = this.opposite, h = this.offset, f = this.horiz,
                    c = this.left + (b ? this.width : 0) + h,
                    h = d.chartHeight - this.bottom - (b ? this.height : 0) + h;
                b && (a *= -1);
                return d.renderer.crispLine(["M", f ? this.left : c, f ? h : this.top, "L", f ? d.chartWidth - this.right : c, f ? h : d.chartHeight - this.bottom], a)
            },
            renderLine: function () {
                this.axisLine || (this.axisLine = this.chart.renderer.path().addClass("highcharts-axis-line").add(this.axisGroup), this.axisLine.attr({
                    stroke: this.options.lineColor, "stroke-width": this.options.lineWidth,
                    zIndex: 7
                }))
            },
            getTitlePosition: function () {
                var a = this.horiz, d = this.left, b = this.top, f = this.len, c = this.options.title, g = a ? d : b,
                    z = this.opposite, e = this.offset, q = c.x || 0, n = c.y || 0, t = this.axisTitle,
                    u = this.chart.renderer.fontMetrics(c.style && c.style.fontSize, t),
                    t = Math.max(t.getBBox(null, 0).height - u.h - 1, 0),
                    f = {low: g + (a ? 0 : f), middle: g + f / 2, high: g + (a ? f : 0)}[c.align],
                    d = (a ? b + this.height : d) + (a ? 1 : -1) * (z ? -1 : 1) * this.axisTitleMargin + [-t, t, u.f, -t][this.side];
                return {
                    x: a ? f + q : d + (z ? this.width : 0) + e + q, y: a ? d + n - (z ? this.height : 0) + e :
                        f + n
                }
            },
            renderMinorTick: function (a) {
                var d = this.chart.hasRendered && u(this.oldMin), b = this.minorTicks;
                b[a] || (b[a] = new z(this, a, "minor"));
                d && b[a].isNew && b[a].render(null, !0);
                b[a].render(null, !1, 1)
            },
            renderTick: function (a, d) {
                var b = this.isLinked, h = this.ticks, f = this.chart.hasRendered && u(this.oldMin);
                if (!b || a >= this.min && a <= this.max) h[a] || (h[a] = new z(this, a)), f && h[a].isNew && h[a].render(d, !0, .1), h[a].render(d)
            },
            render: function () {
                var b = this, f = b.chart, c = b.options, g = b.isLog, q = b.lin2log, n = b.isLinked,
                    t = b.tickPositions,
                    A = b.axisTitle, I = b.ticks, G = b.minorTicks, k = b.alternateBands, D = c.stackLabels,
                    J = c.alternateGridColor, H = b.tickmarkOffset, l = b.axisLine, m = b.showAxis,
                    p = E(f.renderer.globalAnimation), v, B;
                b.labelEdge.length = 0;
                b.overlap = !1;
                e([I, G, k], function (a) {
                    y(a, function (a) {
                        a.isActive = !1
                    })
                });
                if (b.hasData() || n) b.minorTickInterval && !b.categories && e(b.getMinorTickPositions(), function (a) {
                    b.renderMinorTick(a)
                }), t.length && (e(t, function (a, d) {
                    b.renderTick(a, d)
                }), H && (0 === b.min || b.single) && (I[-1] || (I[-1] = new z(b, -1, null, !0)), I[-1].render(-1))),
                J && e(t, function (d, h) {
                    B = void 0 !== t[h + 1] ? t[h + 1] + H : b.max - H;
                    0 === h % 2 && d < b.max && B <= b.max + (f.polar ? -H : H) && (k[d] || (k[d] = new a.PlotLineOrBand(b)), v = d + H, k[d].options = {
                        from: g ? q(v) : v,
                        to: g ? q(B) : B,
                        color: J
                    }, k[d].render(), k[d].isActive = !0)
                }), b._addedPlotLB || (e((c.plotLines || []).concat(c.plotBands || []), function (a) {
                    b.addPlotBandOrLine(a)
                }), b._addedPlotLB = !0);
                e([I, G, k], function (a) {
                    var b, h = [], c = p.duration;
                    y(a, function (a, d) {
                        a.isActive || (a.render(d, !1, 0), a.isActive = !1, h.push(d))
                    });
                    d(function () {
                        for (b = h.length; b--;) a[h[b]] &&
                        !a[h[b]].isActive && (a[h[b]].destroy(), delete a[h[b]])
                    }, a !== k && f.hasRendered && c ? c : 0)
                });
                l && (l[l.isPlaced ? "animate" : "attr"]({d: this.getLinePath(l.strokeWidth())}), l.isPlaced = !0, l[m ? "show" : "hide"](!0));
                A && m && (c = b.getTitlePosition(), u(c.y) ? (A[A.isNew ? "attr" : "animate"](c), A.isNew = !1) : (A.attr("y", -9999), A.isNew = !0));
                D && D.enabled && b.renderStackTotals();
                b.isDirty = !1
            },
            redraw: function () {
                this.visible && (this.render(), e(this.plotLinesAndBands, function (a) {
                    a.render()
                }));
                e(this.series, function (a) {
                    a.isDirty = !0
                })
            },
            keepProps: "extKey hcEvents names series userMax userMin".split(" "),
            destroy: function (a) {
                var d = this, b = d.stacks, h = d.plotLinesAndBands, f;
                a || I(d);
                y(b, function (a, d) {
                    B(a);
                    b[d] = null
                });
                e([d.ticks, d.minorTicks, d.alternateBands], function (a) {
                    B(a)
                });
                if (h) for (a = h.length; a--;) h[a].destroy();
                e("stackTotalGroup axisLine axisTitle axisGroup gridGroup labelGroup cross".split(" "), function (a) {
                    d[a] && (d[a] = d[a].destroy())
                });
                for (f in d.plotLinesAndBandsGroups) d.plotLinesAndBandsGroups[f] = d.plotLinesAndBandsGroups[f].destroy();
                y(d, function (a, b) {
                    -1 === t(b, d.keepProps) && delete d[b]
                })
            },
            drawCrosshair: function (a, d) {
                var b, f = this.crosshair, h = H(f.snap, !0), c, g = this.cross;
                a || (a = this.cross && this.cross.e);
                this.crosshair && !1 !== (p(d) || !h) ? (h ? p(d) && (c = this.isXAxis ? d.plotX : this.len - d.plotY) : c = a && (this.horiz ? a.chartX - this.pos : this.len - a.chartY + this.pos), p(c) && (b = this.getPlotLinePath(d && (this.isXAxis ? d.x : H(d.stackY, d.y)), null, null, null, c) || null), p(b) ? (d = this.categories && !this.isRadial, g || (this.cross = g = this.chart.renderer.path().addClass("highcharts-crosshair highcharts-crosshair-" +
                    (d ? "category " : "thin ") + f.className).attr({zIndex: H(f.zIndex, 2)}).add(), g.attr({
                    stroke: f.color || (d ? m("#ccd6eb").setOpacity(.25).get() : "#cccccc"),
                    "stroke-width": H(f.width, 1)
                }).css({"pointer-events": "none"}), f.dashStyle && g.attr({dashstyle: f.dashStyle})), g.show().attr({d: b}), d && !f.width && g.attr({"stroke-width": this.transA}), this.cross.e = a) : this.hideCrosshair()) : this.hideCrosshair()
            },
            hideCrosshair: function () {
                this.cross && this.cross.hide()
            }
        });
        return a.Axis = G
    }(L);
    (function (a) {
        var C = a.Axis, E = a.getMagnitude,
            F = a.normalizeTickInterval, r = a.timeUnits;
        C.prototype.getTimeTicks = function () {
            return this.chart.time.getTimeTicks.apply(this.chart.time, arguments)
        };
        C.prototype.normalizeTimeTickInterval = function (a, l) {
            var m = l || [["millisecond", [1, 2, 5, 10, 20, 25, 50, 100, 200, 500]], ["second", [1, 2, 5, 10, 15, 30]], ["minute", [1, 2, 5, 10, 15, 30]], ["hour", [1, 2, 3, 4, 6, 8, 12]], ["day", [1, 2]], ["week", [1, 2]], ["month", [1, 2, 3, 4, 6]], ["year", null]];
            l = m[m.length - 1];
            var p = r[l[0]], v = l[1], B;
            for (B = 0; B < m.length && !(l = m[B], p = r[l[0]], v = l[1], m[B + 1] && a <= (p *
                v[v.length - 1] + r[m[B + 1][0]]) / 2); B++) ;
            p === r.year && a < 5 * p && (v = [1, 2, 5]);
            a = F(a / p, v, "year" === l[0] ? Math.max(E(a / p), 1) : 1);
            return {unitRange: p, count: a, unitName: l[0]}
        }
    })(L);
    (function (a) {
        var C = a.Axis, E = a.getMagnitude, F = a.map, r = a.normalizeTickInterval, m = a.pick;
        C.prototype.getLogTickPositions = function (a, w, p, v) {
            var l = this.options, e = this.len, c = this.lin2log, k = this.log2lin, b = [];
            v || (this._minorAutoInterval = null);
            if (.5 <= a) a = Math.round(a), b = this.getLinearTickPositions(a, w, p); else if (.08 <= a) for (var e = Math.floor(w), g, n,
                                                                                                                  t, f, u, l = .3 < a ? [1, 2, 4] : .15 < a ? [1, 2, 4, 6, 8] : [1, 2, 3, 4, 5, 6, 7, 8, 9]; e < p + 1 && !u; e++) for (n = l.length, g = 0; g < n && !u; g++) t = k(c(e) * l[g]), t > w && (!v || f <= p) && void 0 !== f && b.push(f), f > p && (u = !0), f = t; else w = c(w), p = c(p), a = v ? this.getMinorTickInterval() : l.tickInterval, a = m("auto" === a ? null : a, this._minorAutoInterval, l.tickPixelInterval / (v ? 5 : 1) * (p - w) / ((v ? e / this.tickPositions.length : e) || 1)), a = r(a, null, E(a)), b = F(this.getLinearTickPositions(a, w, p), k), v || (this._minorAutoInterval = a / 5);
            v || (this.tickInterval = a);
            return b
        };
        C.prototype.log2lin =
            function (a) {
                return Math.log(a) / Math.LN10
            };
        C.prototype.lin2log = function (a) {
            return Math.pow(10, a)
        }
    })(L);
    (function (a, C) {
        var E = a.arrayMax, F = a.arrayMin, r = a.defined, m = a.destroyObjectProperties, l = a.each, w = a.erase,
            p = a.merge, v = a.pick;
        a.PlotLineOrBand = function (a, e) {
            this.axis = a;
            e && (this.options = e, this.id = e.id)
        };
        a.PlotLineOrBand.prototype = {
            render: function () {
                var l = this, e = l.axis, c = e.horiz, k = l.options, b = k.label, g = l.label, n = k.to, t = k.from,
                    f = k.value, u = r(t) && r(n), D = r(f), q = l.svgElem, A = !q, y = [], H = k.color, I = v(k.zIndex,
                    0), J = k.events, y = {"class": "highcharts-plot-" + (u ? "band " : "line ") + (k.className || "")},
                    d = {}, z = e.chart.renderer, G = u ? "bands" : "lines", h = e.log2lin;
                e.isLog && (t = h(t), n = h(n), f = h(f));
                D ? (y = {
                    stroke: H,
                    "stroke-width": k.width
                }, k.dashStyle && (y.dashstyle = k.dashStyle)) : u && (H && (y.fill = H), k.borderWidth && (y.stroke = k.borderColor, y["stroke-width"] = k.borderWidth));
                d.zIndex = I;
                G += "-" + I;
                (H = e.plotLinesAndBandsGroups[G]) || (e.plotLinesAndBandsGroups[G] = H = z.g("plot-" + G).attr(d).add());
                A && (l.svgElem = q = z.path().attr(y).add(H));
                if (D) y =
                    e.getPlotLinePath(f, q.strokeWidth()); else if (u) y = e.getPlotBandPath(t, n, k); else return;
                A && y && y.length ? (q.attr({d: y}), J && a.objectEach(J, function (a, d) {
                    q.on(d, function (a) {
                        J[d].apply(l, [a])
                    })
                })) : q && (y ? (q.show(), q.animate({d: y})) : (q.hide(), g && (l.label = g = g.destroy())));
                b && r(b.text) && y && y.length && 0 < e.width && 0 < e.height && !y.flat ? (b = p({
                    align: c && u && "center",
                    x: c ? !u && 4 : 10,
                    verticalAlign: !c && u && "middle",
                    y: c ? u ? 16 : 10 : u ? 6 : -4,
                    rotation: c && !u && 90
                }, b), this.renderLabel(b, y, u, I)) : g && g.hide();
                return l
            }, renderLabel: function (a,
                                      e, c, k) {
                var b = this.label, g = this.axis.chart.renderer;
                b || (b = {
                    align: a.textAlign || a.align,
                    rotation: a.rotation,
                    "class": "highcharts-plot-" + (c ? "band" : "line") + "-label " + (a.className || "")
                }, b.zIndex = k, this.label = b = g.text(a.text, 0, 0, a.useHTML).attr(b).add(), b.css(a.style));
                k = e.xBounds || [e[1], e[4], c ? e[6] : e[1]];
                e = e.yBounds || [e[2], e[5], c ? e[7] : e[2]];
                c = F(k);
                g = F(e);
                b.align(a, !1, {x: c, y: g, width: E(k) - c, height: E(e) - g});
                b.show()
            }, destroy: function () {
                w(this.axis.plotLinesAndBands, this);
                delete this.axis;
                m(this)
            }
        };
        a.extend(C.prototype,
            {
                getPlotBandPath: function (a, e) {
                    var c = this.getPlotLinePath(e, null, null, !0), k = this.getPlotLinePath(a, null, null, !0),
                        b = [], g = this.horiz, n = 1, t;
                    a = a < this.min && e < this.min || a > this.max && e > this.max;
                    if (k && c) for (a && (t = k.toString() === c.toString(), n = 0), a = 0; a < k.length; a += 6) g && c[a + 1] === k[a + 1] ? (c[a + 1] += n, c[a + 4] += n) : g || c[a + 2] !== k[a + 2] || (c[a + 2] += n, c[a + 5] += n), b.push("M", k[a + 1], k[a + 2], "L", k[a + 4], k[a + 5], c[a + 4], c[a + 5], c[a + 1], c[a + 2], "z"), b.flat = t;
                    return b
                }, addPlotBand: function (a) {
                return this.addPlotBandOrLine(a, "plotBands")
            },
                addPlotLine: function (a) {
                    return this.addPlotBandOrLine(a, "plotLines")
                }, addPlotBandOrLine: function (l, e) {
                var c = (new a.PlotLineOrBand(this, l)).render(), k = this.userOptions;
                c && (e && (k[e] = k[e] || [], k[e].push(l)), this.plotLinesAndBands.push(c));
                return c
            }, removePlotBandOrLine: function (a) {
                for (var e = this.plotLinesAndBands, c = this.options, k = this.userOptions, b = e.length; b--;) e[b].id === a && e[b].destroy();
                l([c.plotLines || [], k.plotLines || [], c.plotBands || [], k.plotBands || []], function (c) {
                    for (b = c.length; b--;) c[b].id === a &&
                    w(c, c[b])
                })
            }, removePlotBand: function (a) {
                this.removePlotBandOrLine(a)
            }, removePlotLine: function (a) {
                this.removePlotBandOrLine(a)
            }
            })
    })(L, da);
    (function (a) {
        var C = a.each, E = a.extend, F = a.format, r = a.isNumber, m = a.map, l = a.merge, w = a.pick, p = a.splat,
            v = a.syncTimeout, B = a.timeUnits;
        a.Tooltip = function () {
            this.init.apply(this, arguments)
        };
        a.Tooltip.prototype = {
            init: function (a, c) {
                this.chart = a;
                this.options = c;
                this.crosshairs = [];
                this.now = {x: 0, y: 0};
                this.isHidden = !0;
                this.split = c.split && !a.inverted;
                this.shared = c.shared || this.split
            },
            cleanSplit: function (a) {
                C(this.chart.series, function (c) {
                    var e = c && c.tt;
                    e && (!e.isActive || a ? c.tt = e.destroy() : e.isActive = !1)
                })
            }, getLabel: function () {
                var a = this.chart.renderer, c = this.options;
                this.label || (this.split ? this.label = a.g("tooltip") : (this.label = a.label("", 0, 0, c.shape || "callout", null, null, c.useHTML, null, "tooltip").attr({
                    padding: c.padding,
                    r: c.borderRadius
                }), this.label.attr({
                    fill: c.backgroundColor,
                    "stroke-width": c.borderWidth
                }).css(c.style).shadow(c.shadow)), this.label.attr({zIndex: 8}).add());
                return this.label
            },
            update: function (a) {
                this.destroy();
                l(!0, this.chart.options.tooltip.userOptions, a);
                this.init(this.chart, l(!0, this.options, a))
            }, destroy: function () {
                this.label && (this.label = this.label.destroy());
                this.split && this.tt && (this.cleanSplit(this.chart, !0), this.tt = this.tt.destroy());
                clearTimeout(this.hideTimer);
                clearTimeout(this.tooltipTimeout)
            }, move: function (a, c, k, b) {
                var g = this, e = g.now,
                    t = !1 !== g.options.animation && !g.isHidden && (1 < Math.abs(a - e.x) || 1 < Math.abs(c - e.y)),
                    f = g.followPointer || 1 < g.len;
                E(e, {
                    x: t ? (2 * e.x + a) /
                        3 : a,
                    y: t ? (e.y + c) / 2 : c,
                    anchorX: f ? void 0 : t ? (2 * e.anchorX + k) / 3 : k,
                    anchorY: f ? void 0 : t ? (e.anchorY + b) / 2 : b
                });
                g.getLabel().attr(e);
                t && (clearTimeout(this.tooltipTimeout), this.tooltipTimeout = setTimeout(function () {
                    g && g.move(a, c, k, b)
                }, 32))
            }, hide: function (a) {
                var c = this;
                clearTimeout(this.hideTimer);
                a = w(a, this.options.hideDelay, 500);
                this.isHidden || (this.hideTimer = v(function () {
                    c.getLabel()[a ? "fadeOut" : "hide"]();
                    c.isHidden = !0
                }, a))
            }, getAnchor: function (a, c) {
                var e, b = this.chart, g = b.inverted, n = b.plotTop, t = b.plotLeft, f = 0, u =
                    0, D, q;
                a = p(a);
                e = a[0].tooltipPos;
                this.followPointer && c && (void 0 === c.chartX && (c = b.pointer.normalize(c)), e = [c.chartX - b.plotLeft, c.chartY - n]);
                e || (C(a, function (a) {
                    D = a.series.yAxis;
                    q = a.series.xAxis;
                    f += a.plotX + (!g && q ? q.left - t : 0);
                    u += (a.plotLow ? (a.plotLow + a.plotHigh) / 2 : a.plotY) + (!g && D ? D.top - n : 0)
                }), f /= a.length, u /= a.length, e = [g ? b.plotWidth - u : f, this.shared && !g && 1 < a.length && c ? c.chartY - n : g ? b.plotHeight - f : u]);
                return m(e, Math.round)
            }, getPosition: function (a, c, k) {
                var b = this.chart, g = this.distance, e = {}, t = b.inverted &&
                    k.h || 0, f, u = ["y", b.chartHeight, c, k.plotY + b.plotTop, b.plotTop, b.plotTop + b.plotHeight],
                    D = ["x", b.chartWidth, a, k.plotX + b.plotLeft, b.plotLeft, b.plotLeft + b.plotWidth],
                    q = !this.followPointer && w(k.ttBelow, !b.inverted === !!k.negative),
                    A = function (a, d, b, f, h, c) {
                        var z = b < f - g, n = f + g + b < d, u = f - g - b;
                        f += g;
                        if (q && n) e[a] = f; else if (!q && z) e[a] = u; else if (z) e[a] = Math.min(c - b, 0 > u - t ? u : u - t); else if (n) e[a] = Math.max(h, f + t + b > d ? f : f + t); else return !1
                    }, y = function (a, d, b, f) {
                        var h;
                        f < g || f > d - g ? h = !1 : e[a] = f < b / 2 ? 1 : f > d - b / 2 ? d - b - 2 : f - b / 2;
                        return h
                    }, H =
                        function (a) {
                            var d = u;
                            u = D;
                            D = d;
                            f = a
                        }, I = function () {
                        !1 !== A.apply(0, u) ? !1 !== y.apply(0, D) || f || (H(!0), I()) : f ? e.x = e.y = 0 : (H(!0), I())
                    };
                (b.inverted || 1 < this.len) && H();
                I();
                return e
            }, defaultFormatter: function (a) {
                var c = this.points || p(this), e;
                e = [a.tooltipFooterHeaderFormatter(c[0])];
                e = e.concat(a.bodyFormatter(c));
                e.push(a.tooltipFooterHeaderFormatter(c[0], !0));
                return e
            }, refresh: function (a, c) {
                var e, b = this.options, g, n = a, t, f = {}, u = [];
                e = b.formatter || this.defaultFormatter;
                var f = this.shared, D;
                b.enabled && (clearTimeout(this.hideTimer),
                    this.followPointer = p(n)[0].series.tooltipOptions.followPointer, t = this.getAnchor(n, c), c = t[0], g = t[1], !f || n.series && n.series.noSharedTooltip ? f = n.getLabelConfig() : (C(n, function (a) {
                    a.setState("hover");
                    u.push(a.getLabelConfig())
                }), f = {
                    x: n[0].category,
                    y: n[0].y
                }, f.points = u, n = n[0]), this.len = u.length, f = e.call(f, this), D = n.series, this.distance = w(D.tooltipOptions.distance, 16), !1 === f ? this.hide() : (e = this.getLabel(), this.isHidden && e.attr({opacity: 1}).show(), this.split ? this.renderSplit(f, p(a)) : (b.style.width || e.css({width: this.chart.spacingBox.width}),
                    e.attr({text: f && f.join ? f.join("") : f}), e.removeClass(/highcharts-color-[\d]+/g).addClass("highcharts-color-" + w(n.colorIndex, D.colorIndex)), e.attr({stroke: b.borderColor || n.color || D.color || "#666666"}), this.updatePosition({
                    plotX: c,
                    plotY: g,
                    negative: n.negative,
                    ttBelow: n.ttBelow,
                    h: t[2] || 0
                })), this.isHidden = !1))
            }, renderSplit: function (e, c) {
                var k = this, b = [], g = this.chart, n = g.renderer, t = !0, f = this.options, u = 0,
                    D = this.getLabel();
                a.isString(e) && (e = [!1, e]);
                C(e.slice(0, c.length + 1), function (a, e) {
                    if (!1 !== a) {
                        e = c[e - 1] ||
                            {isHeader: !0, plotX: c[0].plotX};
                        var q = e.series || k, A = q.tt, I = e.series || {},
                            J = "highcharts-color-" + w(e.colorIndex, I.colorIndex, "none");
                        A || (q.tt = A = n.label(null, null, null, "callout", null, null, f.useHTML).addClass("highcharts-tooltip-box " + J).attr({
                            padding: f.padding,
                            r: f.borderRadius,
                            fill: f.backgroundColor,
                            stroke: f.borderColor || e.color || I.color || "#333333",
                            "stroke-width": f.borderWidth
                        }).add(D));
                        A.isActive = !0;
                        A.attr({text: a});
                        A.css(f.style).shadow(f.shadow);
                        a = A.getBBox();
                        I = a.width + A.strokeWidth();
                        e.isHeader ? (u =
                            a.height, I = Math.max(0, Math.min(e.plotX + g.plotLeft - I / 2, g.chartWidth - I))) : I = e.plotX + g.plotLeft - w(f.distance, 16) - I;
                        0 > I && (t = !1);
                        a = (e.series && e.series.yAxis && e.series.yAxis.pos) + (e.plotY || 0);
                        a -= g.plotTop;
                        b.push({
                            target: e.isHeader ? g.plotHeight + u : a,
                            rank: e.isHeader ? 1 : 0,
                            size: q.tt.getBBox().height + 1,
                            point: e,
                            x: I,
                            tt: A
                        })
                    }
                });
                this.cleanSplit();
                a.distribute(b, g.plotHeight + u);
                C(b, function (a) {
                    var b = a.point, c = b.series;
                    a.tt.attr({
                        visibility: void 0 === a.pos ? "hidden" : "inherit",
                        x: t || b.isHeader ? a.x : b.plotX + g.plotLeft + w(f.distance,
                            16),
                        y: a.pos + g.plotTop,
                        anchorX: b.isHeader ? b.plotX + g.plotLeft : b.plotX + c.xAxis.pos,
                        anchorY: b.isHeader ? a.pos + g.plotTop - 15 : b.plotY + c.yAxis.pos
                    })
                })
            }, updatePosition: function (a) {
                var c = this.chart, e = this.getLabel(),
                    e = (this.options.positioner || this.getPosition).call(this, e.width, e.height, a);
                this.move(Math.round(e.x), Math.round(e.y || 0), a.plotX + c.plotLeft, a.plotY + c.plotTop)
            }, getDateFormat: function (a, c, k, b) {
                var g = this.chart.time, e = g.dateFormat("%m-%d %H:%M:%S.%L", c), t, f, u = {
                    millisecond: 15, second: 12, minute: 9, hour: 6,
                    day: 3
                }, D = "millisecond";
                for (f in B) {
                    if (a === B.week && +g.dateFormat("%w", c) === k && "00:00:00.000" === e.substr(6)) {
                        f = "week";
                        break
                    }
                    if (B[f] > a) {
                        f = D;
                        break
                    }
                    if (u[f] && e.substr(u[f]) !== "01-01 00:00:00.000".substr(u[f])) break;
                    "week" !== f && (D = f)
                }
                f && (t = b[f]);
                return t
            }, getXDateFormat: function (a, c, k) {
                c = c.dateTimeLabelFormats;
                var b = k && k.closestPointRange;
                return (b ? this.getDateFormat(b, a.x, k.options.startOfWeek, c) : c.day) || c.year
            }, tooltipFooterHeaderFormatter: function (a, c) {
                c = c ? "footer" : "header";
                var e = a.series, b = e.tooltipOptions,
                    g = b.xDateFormat, n = e.xAxis, t = n && "datetime" === n.options.type && r(a.key),
                    f = b[c + "Format"];
                t && !g && (g = this.getXDateFormat(a, b, n));
                t && g && C(a.point && a.point.tooltipDateKeys || ["key"], function (a) {
                    f = f.replace("{point." + a + "}", "{point." + a + ":" + g + "}")
                });
                return F(f, {point: a, series: e}, this.chart.time)
            }, bodyFormatter: function (a) {
                return m(a, function (a) {
                    var c = a.series.tooltipOptions;
                    return (c[(a.point.formatPrefix || "point") + "Formatter"] || a.point.tooltipFormatter).call(a.point, c[(a.point.formatPrefix || "point") + "Format"])
                })
            }
        }
    })(L);
    (function (a) {
        var C = a.addEvent, E = a.attr, F = a.charts, r = a.color, m = a.css, l = a.defined, w = a.each, p = a.extend,
            v = a.find, B = a.fireEvent, e = a.isNumber, c = a.isObject, k = a.offset, b = a.pick, g = a.splat,
            n = a.Tooltip;
        a.Pointer = function (a, b) {
            this.init(a, b)
        };
        a.Pointer.prototype = {
            init: function (a, f) {
                this.options = f;
                this.chart = a;
                this.runChartClick = f.chart.events && !!f.chart.events.click;
                this.pinchDown = [];
                this.lastValidTouch = {};
                n && (a.tooltip = new n(a, f.tooltip), this.followTouchMove = b(f.tooltip.followTouchMove, !0));
                this.setDOMEvents()
            },
            zoomOption: function (a) {
                var f = this.chart, c = f.options.chart, g = c.zoomType || "", f = f.inverted;
                /touch/.test(a.type) && (g = b(c.pinchType, g));
                this.zoomX = a = /x/.test(g);
                this.zoomY = g = /y/.test(g);
                this.zoomHor = a && !f || g && f;
                this.zoomVert = g && !f || a && f;
                this.hasZoom = a || g
            }, normalize: function (a, b) {
                var f;
                f = a.touches ? a.touches.length ? a.touches.item(0) : a.changedTouches[0] : a;
                b || (this.chartPosition = b = k(this.chart.container));
                return p(a, {chartX: Math.round(f.pageX - b.left), chartY: Math.round(f.pageY - b.top)})
            }, getCoordinates: function (a) {
                var b =
                    {xAxis: [], yAxis: []};
                w(this.chart.axes, function (f) {
                    b[f.isXAxis ? "xAxis" : "yAxis"].push({axis: f, value: f.toValue(a[f.horiz ? "chartX" : "chartY"])})
                });
                return b
            }, findNearestKDPoint: function (a, b, g) {
                var f;
                w(a, function (a) {
                    var e = !(a.noSharedTooltip && b) && 0 > a.options.findNearestPointBy.indexOf("y");
                    a = a.searchPoint(g, e);
                    if ((e = c(a, !0)) && !(e = !c(f, !0))) var e = f.distX - a.distX, q = f.dist - a.dist,
                        n = (a.series.group && a.series.group.zIndex) - (f.series.group && f.series.group.zIndex),
                        e = 0 < (0 !== e && b ? e : 0 !== q ? q : 0 !== n ? n : f.series.index >
                        a.series.index ? -1 : 1);
                    e && (f = a)
                });
                return f
            }, getPointFromEvent: function (a) {
                a = a.target;
                for (var b; a && !b;) b = a.point, a = a.parentNode;
                return b
            }, getChartCoordinatesFromPoint: function (a, f) {
                var c = a.series, g = c.xAxis, c = c.yAxis, e = b(a.clientX, a.plotX);
                if (g && c) return f ? {
                    chartX: g.len + g.pos - e,
                    chartY: c.len + c.pos - a.plotY
                } : {chartX: e + g.pos, chartY: a.plotY + c.pos}
            }, getHoverData: function (g, f, e, n, q, A, y) {
                var t, u = [], k = y && y.isBoosting;
                n = !(!n || !g);
                y = f && !f.stickyTracking ? [f] : a.grep(e, function (a) {
                    return a.visible && !(!q && a.directTouch) &&
                        b(a.options.enableMouseTracking, !0) && a.stickyTracking
                });
                f = (t = n ? g : this.findNearestKDPoint(y, q, A)) && t.series;
                t && (q && !f.noSharedTooltip ? (y = a.grep(e, function (a) {
                    return a.visible && !(!q && a.directTouch) && b(a.options.enableMouseTracking, !0) && !a.noSharedTooltip
                }), w(y, function (a) {
                    var d = v(a.points, function (a) {
                        return a.x === t.x && !a.isNull
                    });
                    c(d) && (k && (d = a.getPoint(d)), u.push(d))
                })) : u.push(t));
                return {hoverPoint: t, hoverSeries: f, hoverPoints: u}
            }, runPointActions: function (c, f) {
                var g = this.chart, e = g.tooltip && g.tooltip.options.enabled ?
                    g.tooltip : void 0, n = e ? e.shared : !1, t = f || g.hoverPoint,
                    y = t && t.series || g.hoverSeries,
                    y = this.getHoverData(t, y, g.series, !!f || y && y.directTouch && this.isDirectTouch, n, c, {isBoosting: g.isBoosting}),
                    k, t = y.hoverPoint;
                k = y.hoverPoints;
                f = (y = y.hoverSeries) && y.tooltipOptions.followPointer;
                n = n && y && !y.noSharedTooltip;
                if (t && (t !== g.hoverPoint || e && e.isHidden)) {
                    w(g.hoverPoints || [], function (b) {
                        -1 === a.inArray(b, k) && b.setState()
                    });
                    w(k || [], function (a) {
                        a.setState("hover")
                    });
                    if (g.hoverSeries !== y) y.onMouseOver();
                    g.hoverPoint && g.hoverPoint.firePointEvent("mouseOut");
                    if (!t.series) return;
                    t.firePointEvent("mouseOver");
                    g.hoverPoints = k;
                    g.hoverPoint = t;
                    e && e.refresh(n ? k : t, c)
                } else f && e && !e.isHidden && (t = e.getAnchor([{}], c), e.updatePosition({
                    plotX: t[0],
                    plotY: t[1]
                }));
                this.unDocMouseMove || (this.unDocMouseMove = C(g.container.ownerDocument, "mousemove", function (b) {
                    var f = F[a.hoverChartIndex];
                    if (f) f.pointer.onDocumentMouseMove(b)
                }));
                w(g.axes, function (f) {
                    var g = b(f.crosshair.snap, !0), d = g ? a.find(k, function (a) {
                        return a.series[f.coll] === f
                    }) : void 0;
                    d || !g ? f.drawCrosshair(c, d) : f.hideCrosshair()
                })
            },
            reset: function (a, b) {
                var f = this.chart, c = f.hoverSeries, e = f.hoverPoint, n = f.hoverPoints, t = f.tooltip,
                    k = t && t.shared ? n : e;
                a && k && w(g(k), function (b) {
                    b.series.isCartesian && void 0 === b.plotX && (a = !1)
                });
                if (a) t && k && (t.refresh(k), e && (e.setState(e.state, !0), w(f.axes, function (a) {
                    a.crosshair && a.drawCrosshair(null, e)
                }))); else {
                    if (e) e.onMouseOut();
                    n && w(n, function (a) {
                        a.setState()
                    });
                    if (c) c.onMouseOut();
                    t && t.hide(b);
                    this.unDocMouseMove && (this.unDocMouseMove = this.unDocMouseMove());
                    w(f.axes, function (a) {
                        a.hideCrosshair()
                    });
                    this.hoverX =
                        f.hoverPoints = f.hoverPoint = null
                }
            }, scaleGroups: function (a, b) {
                var f = this.chart, c;
                w(f.series, function (g) {
                    c = a || g.getPlotBox();
                    g.xAxis && g.xAxis.zoomEnabled && g.group && (g.group.attr(c), g.markerGroup && (g.markerGroup.attr(c), g.markerGroup.clip(b ? f.clipRect : null)), g.dataLabelsGroup && g.dataLabelsGroup.attr(c))
                });
                f.clipRect.attr(b || f.clipBox)
            }, dragStart: function (a) {
                var b = this.chart;
                b.mouseIsDown = a.type;
                b.cancelClick = !1;
                b.mouseDownX = this.mouseDownX = a.chartX;
                b.mouseDownY = this.mouseDownY = a.chartY
            }, drag: function (a) {
                var b =
                        this.chart, c = b.options.chart, g = a.chartX, e = a.chartY, n = this.zoomHor, t = this.zoomVert,
                    k = b.plotLeft, I = b.plotTop, J = b.plotWidth, d = b.plotHeight, z, G = this.selectionMarker,
                    h = this.mouseDownX, x = this.mouseDownY, l = c.panKey && a[c.panKey + "Key"];
                G && G.touch || (g < k ? g = k : g > k + J && (g = k + J), e < I ? e = I : e > I + d && (e = I + d), this.hasDragged = Math.sqrt(Math.pow(h - g, 2) + Math.pow(x - e, 2)), 10 < this.hasDragged && (z = b.isInsidePlot(h - k, x - I), b.hasCartesianSeries && (this.zoomX || this.zoomY) && z && !l && !G && (this.selectionMarker = G = b.renderer.rect(k, I, n ? 1 : J,
                    t ? 1 : d, 0).attr({
                    fill: c.selectionMarkerFill || r("#335cad").setOpacity(.25).get(),
                    "class": "highcharts-selection-marker",
                    zIndex: 7
                }).add()), G && n && (g -= h, G.attr({
                    width: Math.abs(g),
                    x: (0 < g ? 0 : g) + h
                })), G && t && (g = e - x, G.attr({
                    height: Math.abs(g),
                    y: (0 < g ? 0 : g) + x
                })), z && !G && c.panning && b.pan(a, c.panning)))
            }, drop: function (a) {
                var b = this, c = this.chart, g = this.hasPinched;
                if (this.selectionMarker) {
                    var n = {originalEvent: a, xAxis: [], yAxis: []}, t = this.selectionMarker,
                        y = t.attr ? t.attr("x") : t.x, k = t.attr ? t.attr("y") : t.y, I = t.attr ? t.attr("width") :
                        t.width, J = t.attr ? t.attr("height") : t.height, d;
                    if (this.hasDragged || g) w(c.axes, function (f) {
                        if (f.zoomEnabled && l(f.min) && (g || b[{xAxis: "zoomX", yAxis: "zoomY"}[f.coll]])) {
                            var c = f.horiz, h = "touchend" === a.type ? f.minPixelPadding : 0,
                                e = f.toValue((c ? y : k) + h), c = f.toValue((c ? y + I : k + J) - h);
                            n[f.coll].push({axis: f, min: Math.min(e, c), max: Math.max(e, c)});
                            d = !0
                        }
                    }), d && B(c, "selection", n, function (a) {
                        c.zoom(p(a, g ? {animation: !1} : null))
                    });
                    e(c.index) && (this.selectionMarker = this.selectionMarker.destroy());
                    g && this.scaleGroups()
                }
                c && e(c.index) &&
                (m(c.container, {cursor: c._cursor}), c.cancelClick = 10 < this.hasDragged, c.mouseIsDown = this.hasDragged = this.hasPinched = !1, this.pinchDown = [])
            }, onContainerMouseDown: function (a) {
                a = this.normalize(a);
                2 !== a.button && (this.zoomOption(a), a.preventDefault && a.preventDefault(), this.dragStart(a))
            }, onDocumentMouseUp: function (b) {
                F[a.hoverChartIndex] && F[a.hoverChartIndex].pointer.drop(b)
            }, onDocumentMouseMove: function (a) {
                var b = this.chart, c = this.chartPosition;
                a = this.normalize(a, c);
                !c || this.inClass(a.target, "highcharts-tracker") ||
                b.isInsidePlot(a.chartX - b.plotLeft, a.chartY - b.plotTop) || this.reset()
            }, onContainerMouseLeave: function (b) {
                var f = F[a.hoverChartIndex];
                f && (b.relatedTarget || b.toElement) && (f.pointer.reset(), f.pointer.chartPosition = null)
            }, onContainerMouseMove: function (b) {
                var f = this.chart;
                l(a.hoverChartIndex) && F[a.hoverChartIndex] && F[a.hoverChartIndex].mouseIsDown || (a.hoverChartIndex = f.index);
                b = this.normalize(b);
                b.returnValue = !1;
                "mousedown" === f.mouseIsDown && this.drag(b);
                !this.inClass(b.target, "highcharts-tracker") && !f.isInsidePlot(b.chartX -
                    f.plotLeft, b.chartY - f.plotTop) || f.openMenu || this.runPointActions(b)
            }, inClass: function (a, b) {
                for (var f; a;) {
                    if (f = E(a, "class")) {
                        if (-1 !== f.indexOf(b)) return !0;
                        if (-1 !== f.indexOf("highcharts-container")) return !1
                    }
                    a = a.parentNode
                }
            }, onTrackerMouseOut: function (a) {
                var b = this.chart.hoverSeries;
                a = a.relatedTarget || a.toElement;
                this.isDirectTouch = !1;
                if (!(!b || !a || b.stickyTracking || this.inClass(a, "highcharts-tooltip") || this.inClass(a, "highcharts-series-" + b.index) && this.inClass(a, "highcharts-tracker"))) b.onMouseOut()
            },
            onContainerClick: function (a) {
                var b = this.chart, c = b.hoverPoint, g = b.plotLeft, e = b.plotTop;
                a = this.normalize(a);
                b.cancelClick || (c && this.inClass(a.target, "highcharts-tracker") ? (B(c.series, "click", p(a, {point: c})), b.hoverPoint && c.firePointEvent("click", a)) : (p(a, this.getCoordinates(a)), b.isInsidePlot(a.chartX - g, a.chartY - e) && B(b, "click", a)))
            }, setDOMEvents: function () {
                var b = this, f = b.chart.container, c = f.ownerDocument;
                f.onmousedown = function (a) {
                    b.onContainerMouseDown(a)
                };
                f.onmousemove = function (a) {
                    b.onContainerMouseMove(a)
                };
                f.onclick = function (a) {
                    b.onContainerClick(a)
                };
                this.unbindContainerMouseLeave = C(f, "mouseleave", b.onContainerMouseLeave);
                a.unbindDocumentMouseUp || (a.unbindDocumentMouseUp = C(c, "mouseup", b.onDocumentMouseUp));
                a.hasTouch && (f.ontouchstart = function (a) {
                    b.onContainerTouchStart(a)
                }, f.ontouchmove = function (a) {
                    b.onContainerTouchMove(a)
                }, a.unbindDocumentTouchEnd || (a.unbindDocumentTouchEnd = C(c, "touchend", b.onDocumentTouchEnd)))
            }, destroy: function () {
                var b = this;
                b.unDocMouseMove && b.unDocMouseMove();
                this.unbindContainerMouseLeave();
                a.chartCount || (a.unbindDocumentMouseUp && (a.unbindDocumentMouseUp = a.unbindDocumentMouseUp()), a.unbindDocumentTouchEnd && (a.unbindDocumentTouchEnd = a.unbindDocumentTouchEnd()));
                clearInterval(b.tooltipTimeout);
                a.objectEach(b, function (a, c) {
                    b[c] = null
                })
            }
        }
    })(L);
    (function (a) {
        var C = a.charts, E = a.each, F = a.extend, r = a.map, m = a.noop, l = a.pick;
        F(a.Pointer.prototype, {
            pinchTranslate: function (a, l, m, r, e, c) {
                this.zoomHor && this.pinchTranslateDirection(!0, a, l, m, r, e, c);
                this.zoomVert && this.pinchTranslateDirection(!1, a, l, m, r,
                    e, c)
            }, pinchTranslateDirection: function (a, l, m, r, e, c, k, b) {
                var g = this.chart, n = a ? "x" : "y", t = a ? "X" : "Y", f = "chart" + t, u = a ? "width" : "height",
                    D = g["plot" + (a ? "Left" : "Top")], q, A, y = b || 1, H = g.inverted, I = g.bounds[a ? "h" : "v"],
                    J = 1 === l.length, d = l[0][f], z = m[0][f], G = !J && l[1][f], h = !J && m[1][f], x;
                m = function () {
                    !J && 20 < Math.abs(d - G) && (y = b || Math.abs(z - h) / Math.abs(d - G));
                    A = (D - z) / y + d;
                    q = g["plot" + (a ? "Width" : "Height")] / y
                };
                m();
                l = A;
                l < I.min ? (l = I.min, x = !0) : l + q > I.max && (l = I.max - q, x = !0);
                x ? (z -= .8 * (z - k[n][0]), J || (h -= .8 * (h - k[n][1])), m()) : k[n] =
                    [z, h];
                H || (c[n] = A - D, c[u] = q);
                c = H ? 1 / y : y;
                e[u] = q;
                e[n] = l;
                r[H ? a ? "scaleY" : "scaleX" : "scale" + t] = y;
                r["translate" + t] = c * D + (z - c * d)
            }, pinch: function (a) {
                var p = this, v = p.chart, w = p.pinchDown, e = a.touches, c = e.length, k = p.lastValidTouch,
                    b = p.hasZoom, g = p.selectionMarker, n = {},
                    t = 1 === c && (p.inClass(a.target, "highcharts-tracker") && v.runTrackerClick || p.runChartClick),
                    f = {};
                1 < c && (p.initiated = !0);
                b && p.initiated && !t && a.preventDefault();
                r(e, function (a) {
                    return p.normalize(a)
                });
                "touchstart" === a.type ? (E(e, function (a, b) {
                    w[b] = {
                        chartX: a.chartX,
                        chartY: a.chartY
                    }
                }), k.x = [w[0].chartX, w[1] && w[1].chartX], k.y = [w[0].chartY, w[1] && w[1].chartY], E(v.axes, function (a) {
                    if (a.zoomEnabled) {
                        var b = v.bounds[a.horiz ? "h" : "v"], f = a.minPixelPadding,
                            c = a.toPixels(l(a.options.min, a.dataMin)), g = a.toPixels(l(a.options.max, a.dataMax)),
                            e = Math.max(c, g);
                        b.min = Math.min(a.pos, Math.min(c, g) - f);
                        b.max = Math.max(a.pos + a.len, e + f)
                    }
                }), p.res = !0) : p.followTouchMove && 1 === c ? this.runPointActions(p.normalize(a)) : w.length && (g || (p.selectionMarker = g = F({
                    destroy: m,
                    touch: !0
                }, v.plotBox)), p.pinchTranslate(w,
                    e, n, g, f, k), p.hasPinched = b, p.scaleGroups(n, f), p.res && (p.res = !1, this.reset(!1, 0)))
            }, touch: function (m, p) {
                var v = this.chart, r, e;
                if (v.index !== a.hoverChartIndex) this.onContainerMouseLeave({relatedTarget: !0});
                a.hoverChartIndex = v.index;
                1 === m.touches.length ? (m = this.normalize(m), (e = v.isInsidePlot(m.chartX - v.plotLeft, m.chartY - v.plotTop)) && !v.openMenu ? (p && this.runPointActions(m), "touchmove" === m.type && (p = this.pinchDown, r = p[0] ? 4 <= Math.sqrt(Math.pow(p[0].chartX - m.chartX, 2) + Math.pow(p[0].chartY - m.chartY, 2)) : !1), l(r,
                    !0) && this.pinch(m)) : p && this.reset()) : 2 === m.touches.length && this.pinch(m)
            }, onContainerTouchStart: function (a) {
                this.zoomOption(a);
                this.touch(a, !0)
            }, onContainerTouchMove: function (a) {
                this.touch(a)
            }, onDocumentTouchEnd: function (l) {
                C[a.hoverChartIndex] && C[a.hoverChartIndex].pointer.drop(l)
            }
        })
    })(L);
    (function (a) {
        var C = a.addEvent, E = a.charts, F = a.css, r = a.doc, m = a.extend, l = a.noop, w = a.Pointer,
            p = a.removeEvent, v = a.win, B = a.wrap;
        if (!a.hasTouch && (v.PointerEvent || v.MSPointerEvent)) {
            var e = {}, c = !!v.PointerEvent, k = function () {
                var b =
                    [];
                b.item = function (a) {
                    return this[a]
                };
                a.objectEach(e, function (a) {
                    b.push({pageX: a.pageX, pageY: a.pageY, target: a.target})
                });
                return b
            }, b = function (b, c, e, f) {
                "touch" !== b.pointerType && b.pointerType !== b.MSPOINTER_TYPE_TOUCH || !E[a.hoverChartIndex] || (f(b), f = E[a.hoverChartIndex].pointer, f[c]({
                    type: e,
                    target: b.currentTarget,
                    preventDefault: l,
                    touches: k()
                }))
            };
            m(w.prototype, {
                onContainerPointerDown: function (a) {
                    b(a, "onContainerTouchStart", "touchstart", function (a) {
                        e[a.pointerId] = {pageX: a.pageX, pageY: a.pageY, target: a.currentTarget}
                    })
                },
                onContainerPointerMove: function (a) {
                    b(a, "onContainerTouchMove", "touchmove", function (a) {
                        e[a.pointerId] = {pageX: a.pageX, pageY: a.pageY};
                        e[a.pointerId].target || (e[a.pointerId].target = a.currentTarget)
                    })
                }, onDocumentPointerUp: function (a) {
                    b(a, "onDocumentTouchEnd", "touchend", function (a) {
                        delete e[a.pointerId]
                    })
                }, batchMSEvents: function (a) {
                    a(this.chart.container, c ? "pointerdown" : "MSPointerDown", this.onContainerPointerDown);
                    a(this.chart.container, c ? "pointermove" : "MSPointerMove", this.onContainerPointerMove);
                    a(r, c ?
                        "pointerup" : "MSPointerUp", this.onDocumentPointerUp)
                }
            });
            B(w.prototype, "init", function (a, b, c) {
                a.call(this, b, c);
                this.hasZoom && F(b.container, {"-ms-touch-action": "none", "touch-action": "none"})
            });
            B(w.prototype, "setDOMEvents", function (a) {
                a.apply(this);
                (this.hasZoom || this.followTouchMove) && this.batchMSEvents(C)
            });
            B(w.prototype, "destroy", function (a) {
                this.batchMSEvents(p);
                a.call(this)
            })
        }
    })(L);
    (function (a) {
        var C = a.addEvent, E = a.css, F = a.discardElement, r = a.defined, m = a.each, l = a.isFirefox,
            w = a.marginNames, p = a.merge,
            v = a.pick, B = a.setAnimation, e = a.stableSort, c = a.win, k = a.wrap;
        a.Legend = function (a, c) {
            this.init(a, c)
        };
        a.Legend.prototype = {
            init: function (a, c) {
                this.chart = a;
                this.setOptions(c);
                c.enabled && (this.render(), C(this.chart, "endResize", function () {
                    this.legend.positionCheckboxes()
                }))
            }, setOptions: function (a) {
                var b = v(a.padding, 8);
                this.options = a;
                this.itemStyle = a.itemStyle;
                this.itemHiddenStyle = p(this.itemStyle, a.itemHiddenStyle);
                this.itemMarginTop = a.itemMarginTop || 0;
                this.padding = b;
                this.initialItemY = b - 5;
                this.itemHeight =
                    this.maxItemWidth = 0;
                this.symbolWidth = v(a.symbolWidth, 16);
                this.pages = []
            }, update: function (a, c) {
                var b = this.chart;
                this.setOptions(p(!0, this.options, a));
                this.destroy();
                b.isDirtyLegend = b.isDirtyBox = !0;
                v(c, !0) && b.redraw()
            }, colorizeItem: function (a, c) {
                a.legendGroup[c ? "removeClass" : "addClass"]("highcharts-legend-item-hidden");
                var b = this.options, g = a.legendItem, f = a.legendLine, e = a.legendSymbol,
                    k = this.itemHiddenStyle.color, b = c ? b.itemStyle.color : k, q = c ? a.color || k : k,
                    A = a.options && a.options.marker, y = {fill: q};
                g && g.css({
                    fill: b,
                    color: b
                });
                f && f.attr({stroke: q});
                e && (A && e.isMarker && (y = a.pointAttribs(), c || (y.stroke = y.fill = k)), e.attr(y))
            }, positionItem: function (a) {
                var b = this.options, c = b.symbolPadding, b = !b.rtl, e = a._legendItemPos, f = e[0], e = e[1],
                    k = a.checkbox;
                (a = a.legendGroup) && a.element && a.translate(b ? f : this.legendWidth - f - 2 * c - 4, e);
                k && (k.x = f, k.y = e)
            }, destroyItem: function (a) {
                var b = a.checkbox;
                m(["legendItem", "legendLine", "legendSymbol", "legendGroup"], function (b) {
                    a[b] && (a[b] = a[b].destroy())
                });
                b && F(a.checkbox)
            }, destroy: function () {
                function a(a) {
                    this[a] &&
                    (this[a] = this[a].destroy())
                }

                m(this.getAllItems(), function (b) {
                    m(["legendItem", "legendGroup"], a, b)
                });
                m("clipRect up down pager nav box title group".split(" "), a, this);
                this.display = null
            }, positionCheckboxes: function () {
                var a = this.group && this.group.alignAttr, c, e = this.clipHeight || this.legendHeight,
                    k = this.titleHeight;
                a && (c = a.translateY, m(this.allItems, function (b) {
                    var f = b.checkbox, g;
                    f && (g = c + k + f.y + (this.scrollOffset || 0) + 3, E(f, {
                        left: a.translateX + b.checkboxOffset + f.x - 20 + "px",
                        top: g + "px",
                        display: g > c - 6 && g < c + e - 6 ?
                            "" : "none"
                    }))
                }, this))
            }, renderTitle: function () {
                var a = this.options, c = this.padding, e = a.title, k = 0;
                e.text && (this.title || (this.title = this.chart.renderer.label(e.text, c - 3, c - 4, null, null, null, a.useHTML, null, "legend-title").attr({zIndex: 1}).css(e.style).add(this.group)), a = this.title.getBBox(), k = a.height, this.offsetWidth = a.width, this.contentGroup.attr({translateY: k}));
                this.titleHeight = k
            }, setText: function (b) {
                var c = this.options;
                b.legendItem.attr({text: c.labelFormat ? a.format(c.labelFormat, b, this.chart.time) : c.labelFormatter.call(b)})
            },
            renderItem: function (a) {
                var b = this.chart, c = b.renderer, e = this.options, f = "horizontal" === e.layout,
                    k = this.symbolWidth, l = e.symbolPadding, q = this.itemStyle, A = this.itemHiddenStyle,
                    y = this.padding, m = f ? v(e.itemDistance, 20) : 0, I = !e.rtl, J = e.width,
                    d = e.itemMarginBottom || 0, z = this.itemMarginTop, G = a.legendItem, h = !a.series,
                    x = !h && a.series.drawLegendSymbol ? a.series : a, r = x.options,
                    O = this.createCheckboxForItem && r && r.showCheckbox, r = k + l + m + (O ? 20 : 0), P = e.useHTML,
                    M = a.options.className;
                G || (a.legendGroup = c.g("legend-item").addClass("highcharts-" +
                    x.type + "-series highcharts-color-" + a.colorIndex + (M ? " " + M : "") + (h ? " highcharts-series-" + a.index : "")).attr({zIndex: 1}).add(this.scrollGroup), a.legendItem = G = c.text("", I ? k + l : -l, this.baseline || 0, P).css(p(a.visible ? q : A)).attr({
                    align: I ? "left" : "right",
                    zIndex: 2
                }).add(a.legendGroup), this.baseline || (k = q.fontSize, this.fontMetrics = c.fontMetrics(k, G), this.baseline = this.fontMetrics.f + 3 + z, G.attr("y", this.baseline)), this.symbolHeight = e.symbolHeight || this.fontMetrics.f, x.drawLegendSymbol(this, a), this.setItemEvents &&
                this.setItemEvents(a, G, P), O && this.createCheckboxForItem(a));
                this.colorizeItem(a, a.visible);
                q.width || G.css({width: (e.itemWidth || e.width || b.spacingBox.width) - r});
                this.setText(a);
                c = G.getBBox();
                q = a.checkboxOffset = e.itemWidth || a.legendItemWidth || c.width + r;
                this.itemHeight = c = Math.round(a.legendItemHeight || c.height || this.symbolHeight);
                f && this.itemX - y + q > (J || b.spacingBox.width - 2 * y - e.x) && (this.itemX = y, this.itemY += z + this.lastLineHeight + d, this.lastLineHeight = 0);
                this.maxItemWidth = Math.max(this.maxItemWidth, q);
                this.lastItemY = z + this.itemY + d;
                this.lastLineHeight = Math.max(c, this.lastLineHeight);
                a._legendItemPos = [this.itemX, this.itemY];
                f ? this.itemX += q : (this.itemY += z + c + d, this.lastLineHeight = c);
                this.offsetWidth = J || Math.max((f ? this.itemX - y - (a.checkbox ? 0 : m) : q) + y, this.offsetWidth)
            }, getAllItems: function () {
                var a = [];
                m(this.chart.series, function (b) {
                    var c = b && b.options;
                    b && v(c.showInLegend, r(c.linkedTo) ? !1 : void 0, !0) && (a = a.concat(b.legendItems || ("point" === c.legendType ? b.data : b)))
                });
                return a
            }, getAlignment: function () {
                var a =
                    this.options;
                return a.floating ? "" : a.align.charAt(0) + a.verticalAlign.charAt(0) + a.layout.charAt(0)
            }, adjustMargins: function (a, c) {
                var b = this.chart, g = this.options, f = this.getAlignment();
                f && m([/(lth|ct|rth)/, /(rtv|rm|rbv)/, /(rbh|cb|lbh)/, /(lbv|lm|ltv)/], function (e, n) {
                    e.test(f) && !r(a[n]) && (b[w[n]] = Math.max(b[w[n]], b.legend[(n + 1) % 2 ? "legendHeight" : "legendWidth"] + [1, -1, -1, 1][n] * g[n % 2 ? "x" : "y"] + v(g.margin, 12) + c[n] + (0 === n ? b.titleOffset + b.options.title.margin : 0)))
                })
            }, render: function () {
                var a = this, c = a.chart, n = c.renderer,
                    k = a.group, f, u, l, q, A = a.box, y = a.options, H = a.padding;
                a.itemX = H;
                a.itemY = a.initialItemY;
                a.offsetWidth = 0;
                a.lastItemY = 0;
                k || (a.group = k = n.g("legend").attr({zIndex: 7}).add(), a.contentGroup = n.g().attr({zIndex: 1}).add(k), a.scrollGroup = n.g().add(a.contentGroup));
                a.renderTitle();
                f = a.getAllItems();
                e(f, function (a, b) {
                    return (a.options && a.options.legendIndex || 0) - (b.options && b.options.legendIndex || 0)
                });
                y.reversed && f.reverse();
                a.allItems = f;
                a.display = u = !!f.length;
                a.lastLineHeight = 0;
                m(f, function (b) {
                    a.renderItem(b)
                });
                l =
                    (y.width || a.offsetWidth) + H;
                q = a.lastItemY + a.lastLineHeight + a.titleHeight;
                q = a.handleOverflow(q);
                q += H;
                A || (a.box = A = n.rect().addClass("highcharts-legend-box").attr({r: y.borderRadius}).add(k), A.isNew = !0);
                A.attr({
                    stroke: y.borderColor,
                    "stroke-width": y.borderWidth || 0,
                    fill: y.backgroundColor || "none"
                }).shadow(y.shadow);
                0 < l && 0 < q && (A[A.isNew ? "attr" : "animate"](A.crisp.call({}, {
                    x: 0,
                    y: 0,
                    width: l,
                    height: q
                }, A.strokeWidth())), A.isNew = !1);
                A[u ? "show" : "hide"]();
                a.legendWidth = l;
                a.legendHeight = q;
                m(f, function (b) {
                    a.positionItem(b)
                });
                u && (n = c.spacingBox, /(lth|ct|rth)/.test(a.getAlignment()) && (n = p(n, {y: n.y + c.titleOffset + c.options.title.margin})), k.align(p(y, {
                    width: l,
                    height: q
                }), !0, n));
                c.isResizing || this.positionCheckboxes()
            }, handleOverflow: function (a) {
                var b = this, c = this.chart, e = c.renderer, f = this.options, k = f.y, l = this.padding,
                    c = c.spacingBox.height + ("top" === f.verticalAlign ? -k : k) - l, k = f.maxHeight, q,
                    A = this.clipRect, y = f.navigation, H = v(y.animation, !0), I = y.arrowSize || 12, J = this.nav,
                    d = this.pages, z, G = this.allItems, h = function (a) {
                        "number" === typeof a ?
                            A.attr({height: a}) : A && (b.clipRect = A.destroy(), b.contentGroup.clip());
                        b.contentGroup.div && (b.contentGroup.div.style.clip = a ? "rect(" + l + "px,9999px," + (l + a) + "px,0)" : "auto")
                    };
                "horizontal" !== f.layout || "middle" === f.verticalAlign || f.floating || (c /= 2);
                k && (c = Math.min(c, k));
                d.length = 0;
                a > c && !1 !== y.enabled ? (this.clipHeight = q = Math.max(c - 20 - this.titleHeight - l, 0), this.currentPage = v(this.currentPage, 1), this.fullHeight = a, m(G, function (a, b) {
                    var c = a._legendItemPos[1], f = Math.round(a.legendItem.getBBox().height), h = d.length;
                    if (!h || c - d[h - 1] > q && (z || c) !== d[h - 1]) d.push(z || c), h++;
                    a.pageIx = h - 1;
                    z && (G[b - 1].pageIx = h - 1);
                    b === G.length - 1 && c + f - d[h - 1] > q && (d.push(c), a.pageIx = h);
                    c !== z && (z = c)
                }), A || (A = b.clipRect = e.clipRect(0, l, 9999, 0), b.contentGroup.clip(A)), h(q), J || (this.nav = J = e.g().attr({zIndex: 1}).add(this.group), this.up = e.symbol("triangle", 0, 0, I, I).on("click", function () {
                    b.scroll(-1, H)
                }).add(J), this.pager = e.text("", 15, 10).addClass("highcharts-legend-navigation").css(y.style).add(J), this.down = e.symbol("triangle-down", 0, 0, I, I).on("click",
                    function () {
                        b.scroll(1, H)
                    }).add(J)), b.scroll(0), a = c) : J && (h(), this.nav = J.destroy(), this.scrollGroup.attr({translateY: 1}), this.clipHeight = 0);
                return a
            }, scroll: function (a, c) {
                var b = this.pages, e = b.length;
                a = this.currentPage + a;
                var f = this.clipHeight, g = this.options.navigation, k = this.pager, q = this.padding;
                a > e && (a = e);
                0 < a && (void 0 !== c && B(c, this.chart), this.nav.attr({
                    translateX: q,
                    translateY: f + this.padding + 7 + this.titleHeight,
                    visibility: "visible"
                }), this.up.attr({"class": 1 === a ? "highcharts-legend-nav-inactive" : "highcharts-legend-nav-active"}),
                    k.attr({text: a + "/" + e}), this.down.attr({
                    x: 18 + this.pager.getBBox().width,
                    "class": a === e ? "highcharts-legend-nav-inactive" : "highcharts-legend-nav-active"
                }), this.up.attr({fill: 1 === a ? g.inactiveColor : g.activeColor}).css({cursor: 1 === a ? "default" : "pointer"}), this.down.attr({fill: a === e ? g.inactiveColor : g.activeColor}).css({cursor: a === e ? "default" : "pointer"}), this.scrollOffset = -b[a - 1] + this.initialItemY, this.scrollGroup.animate({translateY: this.scrollOffset}), this.currentPage = a, this.positionCheckboxes())
            }
        };
        a.LegendSymbolMixin =
            {
                drawRectangle: function (a, c) {
                    var b = a.symbolHeight, e = a.options.squareSymbol;
                    c.legendSymbol = this.chart.renderer.rect(e ? (a.symbolWidth - b) / 2 : 0, a.baseline - b + 1, e ? b : a.symbolWidth, b, v(a.options.symbolRadius, b / 2)).addClass("highcharts-point").attr({zIndex: 3}).add(c.legendGroup)
                }, drawLineMarker: function (a) {
                var b = this.options, c = b.marker, e = a.symbolWidth, f = a.symbolHeight, k = f / 2,
                    l = this.chart.renderer, q = this.legendGroup;
                a = a.baseline - Math.round(.3 * a.fontMetrics.b);
                var A;
                A = {"stroke-width": b.lineWidth || 0};
                b.dashStyle &&
                (A.dashstyle = b.dashStyle);
                this.legendLine = l.path(["M", 0, a, "L", e, a]).addClass("highcharts-graph").attr(A).add(q);
                c && !1 !== c.enabled && (b = Math.min(v(c.radius, k), k), 0 === this.symbol.indexOf("url") && (c = p(c, {
                    width: f,
                    height: f
                }), b = 0), this.legendSymbol = c = l.symbol(this.symbol, e / 2 - b, a - b, 2 * b, 2 * b, c).addClass("highcharts-point").add(q), c.isMarker = !0)
            }
            };
        (/Trident\/7\.0/.test(c.navigator.userAgent) || l) && k(a.Legend.prototype, "positionItem", function (a, c) {
            var b = this, e = function () {
                c._legendItemPos && a.call(b, c)
            };
            e();
            setTimeout(e)
        })
    })(L);
    (function (a) {
        var C = a.addEvent, E = a.animate, F = a.animObject, r = a.attr, m = a.doc, l = a.Axis, w = a.createElement,
            p = a.defaultOptions, v = a.discardElement, B = a.charts, e = a.css, c = a.defined, k = a.each,
            b = a.extend, g = a.find, n = a.fireEvent, t = a.grep, f = a.isNumber, u = a.isObject, D = a.isString,
            q = a.Legend, A = a.marginNames, y = a.merge, H = a.objectEach, I = a.Pointer, J = a.pick, d = a.pInt,
            z = a.removeEvent, G = a.seriesTypes, h = a.splat, x = a.syncTimeout, N = a.win, O = a.Chart = function () {
                this.getArgs.apply(this, arguments)
            };
        a.chart = function (a, d, b) {
            return new O(a,
                d, b)
        };
        b(O.prototype, {
            callbacks: [], getArgs: function () {
                var a = [].slice.call(arguments);
                if (D(a[0]) || a[0].nodeName) this.renderTo = a.shift();
                this.init(a[0], a[1])
            }, init: function (d, b) {
                var c, f, h = d.series, e = d.plotOptions || {};
                d.series = null;
                c = y(p, d);
                for (f in c.plotOptions) c.plotOptions[f].tooltip = e[f] && y(e[f].tooltip) || void 0;
                c.tooltip.userOptions = d.chart && d.chart.forExport && d.tooltip.userOptions || d.tooltip;
                c.series = d.series = h;
                this.userOptions = d;
                f = c.chart;
                h = f.events;
                this.margin = [];
                this.spacing = [];
                this.bounds =
                    {h: {}, v: {}};
                this.labelCollectors = [];
                this.callback = b;
                this.isResizing = 0;
                this.options = c;
                this.axes = [];
                this.series = [];
                this.time = d.time && a.keys(d.time).length ? new a.Time(d.time) : a.time;
                this.hasCartesianSeries = f.showAxes;
                var g = this;
                g.index = B.length;
                B.push(g);
                a.chartCount++;
                h && H(h, function (a, d) {
                    C(g, d, a)
                });
                g.xAxis = [];
                g.yAxis = [];
                g.pointCount = g.colorCounter = g.symbolCounter = 0;
                g.firstRender()
            }, initSeries: function (d) {
                var b = this.options.chart;
                (b = G[d.type || b.type || b.defaultSeriesType]) || a.error(17, !0);
                b = new b;
                b.init(this,
                    d);
                return b
            }, orderSeries: function (a) {
                var d = this.series;
                for (a = a || 0; a < d.length; a++) d[a] && (d[a].index = a, d[a].name = d[a].getName())
            }, isInsidePlot: function (a, d, b) {
                var c = b ? d : a;
                a = b ? a : d;
                return 0 <= c && c <= this.plotWidth && 0 <= a && a <= this.plotHeight
            }, redraw: function (d) {
                var c = this.axes, f = this.series, h = this.pointer, e = this.legend, g = this.isDirtyLegend, z, q,
                    x = this.hasCartesianSeries, G = this.isDirtyBox, y, I = this.renderer, A = I.isHidden(), t = [];
                this.setResponsive && this.setResponsive(!1);
                a.setAnimation(d, this);
                A && this.temporaryDisplay();
                this.layOutTitles();
                for (d = f.length; d--;) if (y = f[d], y.options.stacking && (z = !0, y.isDirty)) {
                    q = !0;
                    break
                }
                if (q) for (d = f.length; d--;) y = f[d], y.options.stacking && (y.isDirty = !0);
                k(f, function (a) {
                    a.isDirty && "point" === a.options.legendType && (a.updateTotals && a.updateTotals(), g = !0);
                    a.isDirtyData && n(a, "updatedData")
                });
                g && e.options.enabled && (e.render(), this.isDirtyLegend = !1);
                z && this.getStacks();
                x && k(c, function (a) {
                    a.updateNames();
                    a.setScale()
                });
                this.getMargins();
                x && (k(c, function (a) {
                    a.isDirty && (G = !0)
                }), k(c, function (a) {
                    var d =
                        a.min + "," + a.max;
                    a.extKey !== d && (a.extKey = d, t.push(function () {
                        n(a, "afterSetExtremes", b(a.eventArgs, a.getExtremes()));
                        delete a.eventArgs
                    }));
                    (G || z) && a.redraw()
                }));
                G && this.drawChartBox();
                n(this, "predraw");
                k(f, function (a) {
                    (G || a.isDirty) && a.visible && a.redraw();
                    a.isDirtyData = !1
                });
                h && h.reset(!0);
                I.draw();
                n(this, "redraw");
                n(this, "render");
                A && this.temporaryDisplay(!0);
                k(t, function (a) {
                    a.call()
                })
            }, get: function (a) {
                function d(d) {
                    return d.id === a || d.options && d.options.id === a
                }

                var b, c = this.series, f;
                b = g(this.axes, d) ||
                    g(this.series, d);
                for (f = 0; !b && f < c.length; f++) b = g(c[f].points || [], d);
                return b
            }, getAxes: function () {
                var a = this, d = this.options, b = d.xAxis = h(d.xAxis || {}), d = d.yAxis = h(d.yAxis || {});
                n(this, "beforeGetAxes");
                k(b, function (a, d) {
                    a.index = d;
                    a.isX = !0
                });
                k(d, function (a, d) {
                    a.index = d
                });
                b = b.concat(d);
                k(b, function (d) {
                    new l(a, d)
                })
            }, getSelectedPoints: function () {
                var a = [];
                k(this.series, function (d) {
                    a = a.concat(t(d.data || [], function (a) {
                        return a.selected
                    }))
                });
                return a
            }, getSelectedSeries: function () {
                return t(this.series, function (a) {
                    return a.selected
                })
            },
            setTitle: function (a, d, b) {
                var c = this, f = c.options, h;
                h = f.title = y({style: {color: "#333333", fontSize: f.isStock ? "16px" : "18px"}}, f.title, a);
                f = f.subtitle = y({style: {color: "#666666"}}, f.subtitle, d);
                k([["title", a, h], ["subtitle", d, f]], function (a, d) {
                    var b = a[0], f = c[b], h = a[1];
                    a = a[2];
                    f && h && (c[b] = f = f.destroy());
                    a && !f && (c[b] = c.renderer.text(a.text, 0, 0, a.useHTML).attr({
                        align: a.align,
                        "class": "highcharts-" + b,
                        zIndex: a.zIndex || 4
                    }).add(), c[b].update = function (a) {
                        c.setTitle(!d && a, d && a)
                    }, c[b].css(a.style))
                });
                c.layOutTitles(b)
            },
            layOutTitles: function (a) {
                var d = 0, c, f = this.renderer, h = this.spacingBox;
                k(["title", "subtitle"], function (a) {
                    var c = this[a], e = this.options[a];
                    a = "title" === a ? -3 : e.verticalAlign ? 0 : d + 2;
                    var g;
                    c && (g = e.style.fontSize, g = f.fontMetrics(g, c).b, c.css({width: (e.width || h.width + e.widthAdjust) + "px"}).align(b({y: a + g}, e), !1, "spacingBox"), e.floating || e.verticalAlign || (d = Math.ceil(d + c.getBBox(e.useHTML).height)))
                }, this);
                c = this.titleOffset !== d;
                this.titleOffset = d;
                !this.isDirtyBox && c && (this.isDirtyBox = c, this.hasRendered && J(a,
                    !0) && this.isDirtyBox && this.redraw())
            }, getChartSize: function () {
                var d = this.options.chart, b = d.width, d = d.height, f = this.renderTo;
                c(b) || (this.containerWidth = a.getStyle(f, "width"));
                c(d) || (this.containerHeight = a.getStyle(f, "height"));
                this.chartWidth = Math.max(0, b || this.containerWidth || 600);
                this.chartHeight = Math.max(0, a.relativeLength(d, this.chartWidth) || (1 < this.containerHeight ? this.containerHeight : 400))
            }, temporaryDisplay: function (d) {
                var b = this.renderTo;
                if (d) for (; b && b.style;) b.hcOrigStyle && (a.css(b, b.hcOrigStyle),
                    delete b.hcOrigStyle), b.hcOrigDetached && (m.body.removeChild(b), b.hcOrigDetached = !1), b = b.parentNode; else for (; b && b.style;) {
                    m.body.contains(b) || b.parentNode || (b.hcOrigDetached = !0, m.body.appendChild(b));
                    if ("none" === a.getStyle(b, "display", !1) || b.hcOricDetached) b.hcOrigStyle = {
                        display: b.style.display,
                        height: b.style.height,
                        overflow: b.style.overflow
                    }, d = {
                        display: "block",
                        overflow: "hidden"
                    }, b !== this.renderTo && (d.height = 0), a.css(b, d), b.offsetWidth || b.style.setProperty("display", "block", "important");
                    b = b.parentNode;
                    if (b === m.body) break
                }
            }, setClassName: function (a) {
                this.container.className = "highcharts-container " + (a || "")
            }, getContainer: function () {
                var c, h = this.options, e = h.chart, g, z;
                c = this.renderTo;
                var q = a.uniqueKey(), k;
                c || (this.renderTo = c = e.renderTo);
                D(c) && (this.renderTo = c = m.getElementById(c));
                c || a.error(13, !0);
                g = d(r(c, "data-highcharts-chart"));
                f(g) && B[g] && B[g].hasRendered && B[g].destroy();
                r(c, "data-highcharts-chart", this.index);
                c.innerHTML = "";
                e.skipClone || c.offsetWidth || this.temporaryDisplay();
                this.getChartSize();
                g = this.chartWidth;
                z = this.chartHeight;
                k = b({
                    position: "relative",
                    overflow: "hidden",
                    width: g + "px",
                    height: z + "px",
                    textAlign: "left",
                    lineHeight: "normal",
                    zIndex: 0,
                    "-webkit-tap-highlight-color": "rgba(0,0,0,0)"
                }, e.style);
                this.container = c = w("div", {id: q}, k, c);
                this._cursor = c.style.cursor;
                this.renderer = new (a[e.renderer] || a.Renderer)(c, g, z, null, e.forExport, h.exporting && h.exporting.allowHTML);
                this.setClassName(e.className);
                this.renderer.setStyle(e.style);
                this.renderer.chartIndex = this.index
            }, getMargins: function (a) {
                var d =
                    this.spacing, b = this.margin, f = this.titleOffset;
                this.resetMargins();
                f && !c(b[0]) && (this.plotTop = Math.max(this.plotTop, f + this.options.title.margin + d[0]));
                this.legend && this.legend.display && this.legend.adjustMargins(b, d);
                this.extraMargin && (this[this.extraMargin.type] = (this[this.extraMargin.type] || 0) + this.extraMargin.value);
                this.adjustPlotArea && this.adjustPlotArea();
                a || this.getAxisMargins()
            }, getAxisMargins: function () {
                var a = this, d = a.axisOffset = [0, 0, 0, 0], b = a.margin;
                a.hasCartesianSeries && k(a.axes, function (a) {
                    a.visible &&
                    a.getOffset()
                });
                k(A, function (f, h) {
                    c(b[h]) || (a[f] += d[h])
                });
                a.setChartSize()
            }, reflow: function (d) {
                var b = this, f = b.options.chart, h = b.renderTo, e = c(f.width) && c(f.height),
                    g = f.width || a.getStyle(h, "width"), f = f.height || a.getStyle(h, "height"),
                    h = d ? d.target : N;
                if (!e && !b.isPrinting && g && f && (h === N || h === m)) {
                    if (g !== b.containerWidth || f !== b.containerHeight) clearTimeout(b.reflowTimeout), b.reflowTimeout = x(function () {
                        b.container && b.setSize(void 0, void 0, !1)
                    }, d ? 100 : 0);
                    b.containerWidth = g;
                    b.containerHeight = f
                }
            }, initReflow: function () {
                var a =
                    this, d;
                d = C(N, "resize", function (d) {
                    a.reflow(d)
                });
                C(a, "destroy", d)
            }, setSize: function (d, b, c) {
                var f = this, h = f.renderer;
                f.isResizing += 1;
                a.setAnimation(c, f);
                f.oldChartHeight = f.chartHeight;
                f.oldChartWidth = f.chartWidth;
                void 0 !== d && (f.options.chart.width = d);
                void 0 !== b && (f.options.chart.height = b);
                f.getChartSize();
                d = h.globalAnimation;
                (d ? E : e)(f.container, {width: f.chartWidth + "px", height: f.chartHeight + "px"}, d);
                f.setChartSize(!0);
                h.setSize(f.chartWidth, f.chartHeight, c);
                k(f.axes, function (a) {
                    a.isDirty = !0;
                    a.setScale()
                });
                f.isDirtyLegend = !0;
                f.isDirtyBox = !0;
                f.layOutTitles();
                f.getMargins();
                f.redraw(c);
                f.oldChartHeight = null;
                n(f, "resize");
                x(function () {
                    f && n(f, "endResize", null, function () {
                        --f.isResizing
                    })
                }, F(d).duration)
            }, setChartSize: function (a) {
                var d = this.inverted, b = this.renderer, c = this.chartWidth, f = this.chartHeight,
                    h = this.options.chart, e = this.spacing, g = this.clipOffset, z, q, x, n;
                this.plotLeft = z = Math.round(this.plotLeft);
                this.plotTop = q = Math.round(this.plotTop);
                this.plotWidth = x = Math.max(0, Math.round(c - z - this.marginRight));
                this.plotHeight = n = Math.max(0, Math.round(f - q - this.marginBottom));
                this.plotSizeX = d ? n : x;
                this.plotSizeY = d ? x : n;
                this.plotBorderWidth = h.plotBorderWidth || 0;
                this.spacingBox = b.spacingBox = {x: e[3], y: e[0], width: c - e[3] - e[1], height: f - e[0] - e[2]};
                this.plotBox = b.plotBox = {x: z, y: q, width: x, height: n};
                c = 2 * Math.floor(this.plotBorderWidth / 2);
                d = Math.ceil(Math.max(c, g[3]) / 2);
                b = Math.ceil(Math.max(c, g[0]) / 2);
                this.clipBox = {
                    x: d,
                    y: b,
                    width: Math.floor(this.plotSizeX - Math.max(c, g[1]) / 2 - d),
                    height: Math.max(0, Math.floor(this.plotSizeY -
                        Math.max(c, g[2]) / 2 - b))
                };
                a || k(this.axes, function (a) {
                    a.setAxisSize();
                    a.setAxisTranslation()
                })
            }, resetMargins: function () {
                var a = this, d = a.options.chart;
                k(["margin", "spacing"], function (b) {
                    var c = d[b], f = u(c) ? c : [c, c, c, c];
                    k(["Top", "Right", "Bottom", "Left"], function (c, h) {
                        a[b][h] = J(d[b + c], f[h])
                    })
                });
                k(A, function (d, b) {
                    a[d] = J(a.margin[b], a.spacing[b])
                });
                a.axisOffset = [0, 0, 0, 0];
                a.clipOffset = [0, 0, 0, 0]
            }, drawChartBox: function () {
                var a = this.options.chart, d = this.renderer, b = this.chartWidth, c = this.chartHeight,
                    f = this.chartBackground,
                    h = this.plotBackground, e = this.plotBorder, g, z = this.plotBGImage, q = a.backgroundColor,
                    k = a.plotBackgroundColor, x = a.plotBackgroundImage, G, y = this.plotLeft, I = this.plotTop,
                    A = this.plotWidth, t = this.plotHeight, u = this.plotBox, J = this.clipRect, l = this.clipBox,
                    m = "animate";
                f || (this.chartBackground = f = d.rect().addClass("highcharts-background").add(), m = "attr");
                g = a.borderWidth || 0;
                G = g + (a.shadow ? 8 : 0);
                q = {fill: q || "none"};
                if (g || f["stroke-width"]) q.stroke = a.borderColor, q["stroke-width"] = g;
                f.attr(q).shadow(a.shadow);
                f[m]({
                    x: G /
                    2, y: G / 2, width: b - G - g % 2, height: c - G - g % 2, r: a.borderRadius
                });
                m = "animate";
                h || (m = "attr", this.plotBackground = h = d.rect().addClass("highcharts-plot-background").add());
                h[m](u);
                h.attr({fill: k || "none"}).shadow(a.plotShadow);
                x && (z ? z.animate(u) : this.plotBGImage = d.image(x, y, I, A, t).add());
                J ? J.animate({width: l.width, height: l.height}) : this.clipRect = d.clipRect(l);
                m = "animate";
                e || (m = "attr", this.plotBorder = e = d.rect().addClass("highcharts-plot-border").attr({zIndex: 1}).add());
                e.attr({
                    stroke: a.plotBorderColor, "stroke-width": a.plotBorderWidth ||
                    0, fill: "none"
                });
                e[m](e.crisp({x: y, y: I, width: A, height: t}, -e.strokeWidth()));
                this.isDirtyBox = !1;
                n(this, "afterDrawChartBox")
            }, propFromSeries: function () {
                var a = this, d = a.options.chart, b, c = a.options.series, f, h;
                k(["inverted", "angular", "polar"], function (e) {
                    b = G[d.type || d.defaultSeriesType];
                    h = d[e] || b && b.prototype[e];
                    for (f = c && c.length; !h && f--;) (b = G[c[f].type]) && b.prototype[e] && (h = !0);
                    a[e] = h
                })
            }, linkSeries: function () {
                var a = this, d = a.series;
                k(d, function (a) {
                    a.linkedSeries.length = 0
                });
                k(d, function (d) {
                    var b = d.options.linkedTo;
                    D(b) && (b = ":previous" === b ? a.series[d.index - 1] : a.get(b)) && b.linkedParent !== d && (b.linkedSeries.push(d), d.linkedParent = b, d.visible = J(d.options.visible, b.options.visible, d.visible))
                })
            }, renderSeries: function () {
                k(this.series, function (a) {
                    a.translate();
                    a.render()
                })
            }, renderLabels: function () {
                var a = this, c = a.options.labels;
                c.items && k(c.items, function (f) {
                    var h = b(c.style, f.style), e = d(h.left) + a.plotLeft, g = d(h.top) + a.plotTop + 12;
                    delete h.left;
                    delete h.top;
                    a.renderer.text(f.html, e, g).attr({zIndex: 2}).css(h).add()
                })
            },
            render: function () {
                var a = this.axes, d = this.renderer, b = this.options, c, f, h;
                this.setTitle();
                this.legend = new q(this, b.legend);
                this.getStacks && this.getStacks();
                this.getMargins(!0);
                this.setChartSize();
                b = this.plotWidth;
                c = this.plotHeight = Math.max(this.plotHeight - 21, 0);
                k(a, function (a) {
                    a.setScale()
                });
                this.getAxisMargins();
                f = 1.1 < b / this.plotWidth;
                h = 1.05 < c / this.plotHeight;
                if (f || h) k(a, function (a) {
                    (a.horiz && f || !a.horiz && h) && a.setTickInterval(!0)
                }), this.getMargins();
                this.drawChartBox();
                this.hasCartesianSeries && k(a,
                    function (a) {
                        a.visible && a.render()
                    });
                this.seriesGroup || (this.seriesGroup = d.g("series-group").attr({zIndex: 3}).add());
                this.renderSeries();
                this.renderLabels();
                this.addCredits();
                this.setResponsive && this.setResponsive();
                this.hasRendered = !0
            }, addCredits: function (a) {
                var d = this;
                a = y(!0, this.options.credits, a);
                a.enabled && !this.credits && (this.credits = this.renderer.text(a.text + (this.mapCredits || ""), 0, 0).addClass("highcharts-credits").on("click", function () {
                    a.href && (N.location.href = a.href)
                }).attr({
                    align: a.position.align,
                    zIndex: 8
                }).css(a.style).add().align(a.position), this.credits.update = function (a) {
                    d.credits = d.credits.destroy();
                    d.addCredits(a)
                })
            }, destroy: function () {
                var d = this, b = d.axes, c = d.series, f = d.container, h, e = f && f.parentNode;
                n(d, "destroy");
                d.renderer.forExport ? a.erase(B, d) : B[d.index] = void 0;
                a.chartCount--;
                d.renderTo.removeAttribute("data-highcharts-chart");
                z(d);
                for (h = b.length; h--;) b[h] = b[h].destroy();
                this.scroller && this.scroller.destroy && this.scroller.destroy();
                for (h = c.length; h--;) c[h] = c[h].destroy();
                k("title subtitle chartBackground plotBackground plotBGImage plotBorder seriesGroup clipRect credits pointer rangeSelector legend resetZoomButton tooltip renderer".split(" "),
                    function (a) {
                        var b = d[a];
                        b && b.destroy && (d[a] = b.destroy())
                    });
                f && (f.innerHTML = "", z(f), e && v(f));
                H(d, function (a, b) {
                    delete d[b]
                })
            }, firstRender: function () {
                var a = this, d = a.options;
                if (!a.isReadyToRender || a.isReadyToRender()) {
                    a.getContainer();
                    n(a, "init");
                    a.resetMargins();
                    a.setChartSize();
                    a.propFromSeries();
                    a.getAxes();
                    k(d.series || [], function (d) {
                        a.initSeries(d)
                    });
                    a.linkSeries();
                    n(a, "beforeRender");
                    I && (a.pointer = new I(a, d));
                    a.render();
                    if (!a.renderer.imgCount && a.onload) a.onload();
                    a.temporaryDisplay(!0)
                }
            }, onload: function () {
                k([this.callback].concat(this.callbacks),
                    function (a) {
                        a && void 0 !== this.index && a.apply(this, [this])
                    }, this);
                n(this, "load");
                n(this, "render");
                c(this.index) && !1 !== this.options.chart.reflow && this.initReflow();
                this.onload = null
            }
        })
    })(L);
    (function (a) {
        var C, E = a.each, F = a.extend, r = a.erase, m = a.fireEvent, l = a.format, w = a.isArray, p = a.isNumber,
            v = a.pick, B = a.removeEvent;
        a.Point = C = function () {
        };
        a.Point.prototype = {
            init: function (a, c, k) {
                this.series = a;
                this.color = a.color;
                this.applyOptions(c, k);
                a.options.colorByPoint ? (c = a.options.colors || a.chart.options.colors, this.color =
                    this.color || c[a.colorCounter], c = c.length, k = a.colorCounter, a.colorCounter++, a.colorCounter === c && (a.colorCounter = 0)) : k = a.colorIndex;
                this.colorIndex = v(this.colorIndex, k);
                a.chart.pointCount++;
                m(this, "afterInit");
                return this
            }, applyOptions: function (a, c) {
                var e = this.series, b = e.options.pointValKey || e.pointValKey;
                a = C.prototype.optionsToObject.call(this, a);
                F(this, a);
                this.options = this.options ? F(this.options, a) : a;
                a.group && delete this.group;
                b && (this.y = this[b]);
                this.isNull = v(this.isValid && !this.isValid(), null ===
                    this.x || !p(this.y, !0));
                this.selected && (this.state = "select");
                "name" in this && void 0 === c && e.xAxis && e.xAxis.hasNames && (this.x = e.xAxis.nameToX(this));
                void 0 === this.x && e && (this.x = void 0 === c ? e.autoIncrement(this) : c);
                return this
            }, optionsToObject: function (a) {
                var c = {}, e = this.series, b = e.options.keys, g = b || e.pointArrayMap || ["y"], n = g.length, t = 0,
                    f = 0;
                if (p(a) || null === a) c[g[0]] = a; else if (w(a)) for (!b && a.length > n && (e = typeof a[0], "string" === e ? c.name = a[0] : "number" === e && (c.x = a[0]), t++); f < n;) b && void 0 === a[t] || (c[g[f]] = a[t]),
                    t++, f++; else "object" === typeof a && (c = a, a.dataLabels && (e._hasPointLabels = !0), a.marker && (e._hasPointMarkers = !0));
                return c
            }, getClassName: function () {
                return "highcharts-point" + (this.selected ? " highcharts-point-select" : "") + (this.negative ? " highcharts-negative" : "") + (this.isNull ? " highcharts-null-point" : "") + (void 0 !== this.colorIndex ? " highcharts-color-" + this.colorIndex : "") + (this.options.className ? " " + this.options.className : "") + (this.zone && this.zone.className ? " " + this.zone.className.replace("highcharts-negative",
                    "") : "")
            }, getZone: function () {
                var a = this.series, c = a.zones, a = a.zoneAxis || "y", k = 0, b;
                for (b = c[k]; this[a] >= b.value;) b = c[++k];
                b && b.color && !this.options.color && (this.color = b.color);
                return b
            }, destroy: function () {
                var a = this.series.chart, c = a.hoverPoints, k;
                a.pointCount--;
                c && (this.setState(), r(c, this), c.length || (a.hoverPoints = null));
                if (this === a.hoverPoint) this.onMouseOut();
                if (this.graphic || this.dataLabel) B(this), this.destroyElements();
                this.legendItem && a.legend.destroyItem(this);
                for (k in this) this[k] = null
            }, destroyElements: function () {
                for (var a =
                    ["graphic", "dataLabel", "dataLabelUpper", "connector", "shadowGroup"], c, k = 6; k--;) c = a[k], this[c] && (this[c] = this[c].destroy())
            }, getLabelConfig: function () {
                return {
                    x: this.category,
                    y: this.y,
                    color: this.color,
                    colorIndex: this.colorIndex,
                    key: this.name || this.category,
                    series: this.series,
                    point: this,
                    percentage: this.percentage,
                    total: this.total || this.stackTotal
                }
            }, tooltipFormatter: function (a) {
                var c = this.series, e = c.tooltipOptions, b = v(e.valueDecimals, ""), g = e.valuePrefix || "",
                    n = e.valueSuffix || "";
                E(c.pointArrayMap || ["y"],
                    function (c) {
                        c = "{point." + c;
                        if (g || n) a = a.replace(c + "}", g + c + "}" + n);
                        a = a.replace(c + "}", c + ":,." + b + "f}")
                    });
                return l(a, {point: this, series: this.series}, c.chart.time)
            }, firePointEvent: function (a, c, k) {
                var b = this, e = this.series.options;
                (e.point.events[a] || b.options && b.options.events && b.options.events[a]) && this.importEvents();
                "click" === a && e.allowPointSelect && (k = function (a) {
                    b.select && b.select(null, a.ctrlKey || a.metaKey || a.shiftKey)
                });
                m(this, a, c, k)
            }, visible: !0
        }
    })(L);
    (function (a) {
        var C = a.addEvent, E = a.animObject, F = a.arrayMax,
            r = a.arrayMin, m = a.correctFloat, l = a.defaultOptions, w = a.defaultPlotOptions, p = a.defined,
            v = a.each, B = a.erase, e = a.extend, c = a.fireEvent, k = a.grep, b = a.isArray, g = a.isNumber,
            n = a.isString, t = a.merge, f = a.objectEach, u = a.pick, D = a.removeEvent, q = a.splat, A = a.SVGElement,
            y = a.syncTimeout, H = a.win;
        a.Series = a.seriesType("line", null, {
            lineWidth: 2, allowPointSelect: !1, showCheckbox: !1, animation: {duration: 1E3}, events: {}, marker: {
                lineWidth: 0, lineColor: "#ffffff", enabledThreshold: 2, radius: 4, states: {
                    normal: {animation: !0}, hover: {
                        animation: {duration: 50},
                        enabled: !0, radiusPlus: 2, lineWidthPlus: 1
                    }, select: {fillColor: "#cccccc", lineColor: "#000000", lineWidth: 2}
                }
            }, point: {events: {}}, dataLabels: {
                align: "center",
                formatter: function () {
                    return null === this.y ? "" : a.numberFormat(this.y, -1)
                },
                style: {fontSize: "11px", fontWeight: "bold", color: "contrast", textOutline: "1px contrast"},
                verticalAlign: "bottom",
                x: 0,
                y: 0,
                padding: 5
            }, cropThreshold: 300, pointRange: 0, softThreshold: !0, states: {
                normal: {animation: !0},
                hover: {animation: {duration: 50}, lineWidthPlus: 1, marker: {}, halo: {size: 10, opacity: .25}},
                select: {marker: {}}
            }, stickyTracking: !0, turboThreshold: 1E3, findNearestPointBy: "x"
        }, {
            isCartesian: !0,
            pointClass: a.Point,
            sorted: !0,
            requireSorting: !0,
            directTouch: !1,
            axisTypes: ["xAxis", "yAxis"],
            colorCounter: 0,
            parallelArrays: ["x", "y"],
            coll: "series",
            init: function (a, b) {
                var d = this, c, g = a.series, h;
                d.chart = a;
                d.options = b = d.setOptions(b);
                d.linkedSeries = [];
                d.bindAxes();
                e(d, {name: b.name, state: "", visible: !1 !== b.visible, selected: !0 === b.selected});
                c = b.events;
                f(c, function (a, b) {
                    C(d, b, a)
                });
                if (c && c.click || b.point && b.point.events &&
                    b.point.events.click || b.allowPointSelect) a.runTrackerClick = !0;
                d.getColor();
                d.getSymbol();
                v(d.parallelArrays, function (a) {
                    d[a + "Data"] = []
                });
                d.setData(b.data, !1);
                d.isCartesian && (a.hasCartesianSeries = !0);
                g.length && (h = g[g.length - 1]);
                d._i = u(h && h._i, -1) + 1;
                a.orderSeries(this.insert(g))
            },
            insert: function (a) {
                var b = this.options.index, d;
                if (g(b)) {
                    for (d = a.length; d--;) if (b >= u(a[d].options.index, a[d]._i)) {
                        a.splice(d + 1, 0, this);
                        break
                    }
                    -1 === d && a.unshift(this);
                    d += 1
                } else a.push(this);
                return u(d, a.length - 1)
            },
            bindAxes: function () {
                var b =
                    this, c = b.options, d = b.chart, f;
                v(b.axisTypes || [], function (e) {
                    v(d[e], function (a) {
                        f = a.options;
                        if (c[e] === f.index || void 0 !== c[e] && c[e] === f.id || void 0 === c[e] && 0 === f.index) b.insert(a.series), b[e] = a, a.isDirty = !0
                    });
                    b[e] || b.optionalAxis === e || a.error(18, !0)
                })
            },
            updateParallelArrays: function (a, b) {
                var d = a.series, c = arguments, f = g(b) ? function (c) {
                    var f = "y" === c && d.toYData ? d.toYData(a) : a[c];
                    d[c + "Data"][b] = f
                } : function (a) {
                    Array.prototype[b].apply(d[a + "Data"], Array.prototype.slice.call(c, 2))
                };
                v(d.parallelArrays, f)
            },
            autoIncrement: function () {
                var a =
                        this.options, b = this.xIncrement, d, c = a.pointIntervalUnit, f = this.chart.time,
                    b = u(b, a.pointStart, 0);
                this.pointInterval = d = u(this.pointInterval, a.pointInterval, 1);
                c && (a = new f.Date(b), "day" === c ? f.set("Date", a, f.get("Date", a) + d) : "month" === c ? f.set("Month", a, f.get("Month", a) + d) : "year" === c && f.set("FullYear", a, f.get("FullYear", a) + d), d = a.getTime() - b);
                this.xIncrement = b + d;
                return b
            },
            setOptions: function (a) {
                var b = this.chart, d = b.options, c = d.plotOptions, f = (b.userOptions || {}).plotOptions || {},
                    h = c[this.type];
                this.userOptions =
                    a;
                b = t(h, c.series, a);
                this.tooltipOptions = t(l.tooltip, l.plotOptions.series && l.plotOptions.series.tooltip, l.plotOptions[this.type].tooltip, d.tooltip.userOptions, c.series && c.series.tooltip, c[this.type].tooltip, a.tooltip);
                this.stickyTracking = u(a.stickyTracking, f[this.type] && f[this.type].stickyTracking, f.series && f.series.stickyTracking, this.tooltipOptions.shared && !this.noSharedTooltip ? !0 : b.stickyTracking);
                null === h.marker && delete b.marker;
                this.zoneAxis = b.zoneAxis;
                a = this.zones = (b.zones || []).slice();
                !b.negativeColor &&
                !b.negativeFillColor || b.zones || a.push({
                    value: b[this.zoneAxis + "Threshold"] || b.threshold || 0,
                    className: "highcharts-negative",
                    color: b.negativeColor,
                    fillColor: b.negativeFillColor
                });
                a.length && p(a[a.length - 1].value) && a.push({color: this.color, fillColor: this.fillColor});
                return b
            },
            getName: function () {
                return this.name || "Series " + (this.index + 1)
            },
            getCyclic: function (a, b, d) {
                var c, f = this.chart, h = this.userOptions, e = a + "Index", g = a + "Counter",
                    q = d ? d.length : u(f.options.chart[a + "Count"], f[a + "Count"]);
                b || (c = u(h[e], h["_" + e]),
                p(c) || (f.series.length || (f[g] = 0), h["_" + e] = c = f[g] % q, f[g] += 1), d && (b = d[c]));
                void 0 !== c && (this[e] = c);
                this[a] = b
            },
            getColor: function () {
                this.options.colorByPoint ? this.options.color = null : this.getCyclic("color", this.options.color || w[this.type].color, this.chart.options.colors)
            },
            getSymbol: function () {
                this.getCyclic("symbol", this.options.marker.symbol, this.chart.options.symbols)
            },
            drawLegendSymbol: a.LegendSymbolMixin.drawLineMarker,
            setData: function (c, f, d, e) {
                var z = this, h = z.points, q = h && h.length || 0, k, y = z.options, A =
                        z.chart, t = null, I = z.xAxis, l = y.turboThreshold, m = this.xData, J = this.yData,
                    H = (k = z.pointArrayMap) && k.length;
                c = c || [];
                k = c.length;
                f = u(f, !0);
                if (!1 !== e && k && q === k && !z.cropped && !z.hasGroupedData && z.visible) v(c, function (a, d) {
                    h[d].update && a !== y.data[d] && h[d].update(a, !1, null, !1)
                }); else {
                    z.xIncrement = null;
                    z.colorCounter = 0;
                    v(this.parallelArrays, function (a) {
                        z[a + "Data"].length = 0
                    });
                    if (l && k > l) {
                        for (d = 0; null === t && d < k;) t = c[d], d++;
                        if (g(t)) for (d = 0; d < k; d++) m[d] = this.autoIncrement(), J[d] = c[d]; else if (b(t)) if (H) for (d = 0; d < k; d++) t =
                            c[d], m[d] = t[0], J[d] = t.slice(1, H + 1); else for (d = 0; d < k; d++) t = c[d], m[d] = t[0], J[d] = t[1]; else a.error(12)
                    } else for (d = 0; d < k; d++) void 0 !== c[d] && (t = {series: z}, z.pointClass.prototype.applyOptions.apply(t, [c[d]]), z.updateParallelArrays(t, d));
                    J && n(J[0]) && a.error(14, !0);
                    z.data = [];
                    z.options.data = z.userOptions.data = c;
                    for (d = q; d--;) h[d] && h[d].destroy && h[d].destroy();
                    I && (I.minRange = I.userMinRange);
                    z.isDirty = A.isDirtyBox = !0;
                    z.isDirtyData = !!h;
                    d = !1
                }
                "point" === y.legendType && (this.processData(), this.generatePoints());
                f &&
                A.redraw(d)
            },
            processData: function (b) {
                var c = this.xData, d = this.yData, f = c.length, e;
                e = 0;
                var h, g, q = this.xAxis, k, n = this.options;
                k = n.cropThreshold;
                var y = this.getExtremesFromAll || n.getExtremesFromAll, A = this.isCartesian, n = q && q.val2lin,
                    t = q && q.isLog, u = this.requireSorting, I, l;
                if (A && !this.isDirty && !q.isDirty && !this.yAxis.isDirty && !b) return !1;
                q && (b = q.getExtremes(), I = b.min, l = b.max);
                if (A && this.sorted && !y && (!k || f > k || this.forceCrop)) if (c[f - 1] < I || c[0] > l) c = [], d = []; else if (c[0] < I || c[f - 1] > l) e = this.cropData(this.xData,
                    this.yData, I, l), c = e.xData, d = e.yData, e = e.start, h = !0;
                for (k = c.length || 1; --k;)
                    f = t ? n(c[k]) - n(c[k - 1]) : c[k] - c[k - 1],
                        0 < f && (void 0 === g || f < g) ? g = f : 0 > f && u && (a.error(15), u = !1);
                this.cropped = h;
                this.cropStart = e;
                this.processedXData = c;
                this.processedYData = d;
                this.closestPointRange = g
            },
            cropData: function (a, b, d, c) {
                var f = a.length, h = 0, e = f, g = u(this.cropShoulder, 1), z;
                for (z = 0; z < f; z++) if (a[z] >= d) {
                    h = Math.max(0, z - g);
                    break
                }
                for (d = z; d < f; d++) if (a[d] > c) {
                    e = d + g;
                    break
                }
                return {xData: a.slice(h, e), yData: b.slice(h, e), start: h, end: e}
            },
            generatePoints: function () {
                var a =
                        this.options, b = a.data, d = this.data, c, f = this.processedXData, h = this.processedYData,
                    e = this.pointClass, g = f.length, k = this.cropStart || 0, n, y = this.hasGroupedData, a = a.keys,
                    A, t = [], u;
                d || y || (d = [], d.length = b.length, d = this.data = d);
                a && y && (this.options.keys = !1);
                for (u = 0; u < g; u++) n = k + u, y ? (A = (new e).init(this, [f[u]].concat(q(h[u]))), A.dataGroup = this.groupMap[u]) : (A = d[n]) || void 0 === b[n] || (d[n] = A = (new e).init(this, b[n], f[u])), A && (A.index = n, t[u] = A);
                this.options.keys = a;
                if (d && (g !== (c = d.length) || y)) for (u = 0; u < c; u++) u !== k || y ||
                (u += g), d[u] && (d[u].destroyElements(), d[u].plotX = void 0);
                this.data = d;
                this.points = t
            },
            getExtremes: function (a) {
                var c = this.yAxis, d = this.processedXData, f, e = [], h = 0;
                f = this.xAxis.getExtremes();
                var q = f.min, k = f.max, n, y, A, t;
                a = a || this.stackedYData || this.processedYData || [];
                f = a.length;
                for (t = 0; t < f; t++) if (y = d[t], A = a[t], n = (g(A, !0) || b(A)) && (!c.positiveValuesOnly || A.length || 0 < A), y = this.getExtremesFromAll || this.options.getExtremesFromAll || this.cropped || (d[t + 1] || y) >= q && (d[t - 1] || y) <= k, n && y) if (n = A.length) for (; n--;) "number" ===
                typeof A[n] && (e[h++] = A[n]); else e[h++] = A;
                this.dataMin = r(e);
                this.dataMax = F(e)
            },
            translate: function () {
                this.processedXData || this.processData();
                this.generatePoints();
                var a = this.options, b = a.stacking, d = this.xAxis, f = d.categories, e = this.yAxis, h = this.points,
                    q = h.length, k = !!this.modifyValue, n = a.pointPlacement, y = "between" === n || g(n),
                    A = a.threshold, t = a.startFromThreshold ? A : 0, l, H, D, v, r = Number.MAX_VALUE;
                "between" === n && (n = .5);
                g(n) && (n *= u(a.pointRange || d.pointRange));
                for (a = 0; a < q; a++) {
                    var w = h[a], B = w.x, C = w.y;
                    H = w.low;
                    var E =
                        b && e.stacks[(this.negStacks && C < (t ? 0 : A) ? "-" : "") + this.stackKey], F;
                    e.positiveValuesOnly && null !== C && 0 >= C && (w.isNull = !0);
                    w.plotX = l = m(Math.min(Math.max(-1E5, d.translate(B, 0, 0, 0, 1, n, "flags" === this.type)), 1E5));
                    b && this.visible && !w.isNull && E && E[B] && (v = this.getStackIndicator(v, B, this.index), F = E[B], C = F.points[v.key], H = C[0], C = C[1], H === t && v.key === E[B].base && (H = u(A, e.min)), e.positiveValuesOnly && 0 >= H && (H = null), w.total = w.stackTotal = F.total, w.percentage = F.total && w.y / F.total * 100, w.stackY = C, F.setOffset(this.pointXOffset ||
                        0, this.barW || 0));
                    w.yBottom = p(H) ? Math.min(Math.max(-1E5, e.translate(H, 0, 1, 0, 1)), 1E5) : null;
                    k && (C = this.modifyValue(C, w));
                    w.plotY = H = "number" === typeof C && Infinity !== C ? Math.min(Math.max(-1E5, e.translate(C, 0, 1, 0, 1)), 1E5) : void 0;
                    w.isInside = void 0 !== H && 0 <= H && H <= e.len && 0 <= l && l <= d.len;
                    w.clientX = y ? m(d.translate(B, 0, 0, 0, 1, n)) : l;
                    w.negative = w.y < (A || 0);
                    w.category = f && void 0 !== f[w.x] ? f[w.x] : w.x;
                    w.isNull || (void 0 !== D && (r = Math.min(r, Math.abs(l - D))), D = l);
                    w.zone = this.zones.length && w.getZone()
                }
                this.closestPointRangePx =
                    r;
                c(this, "afterTranslate")
            },
            getValidPoints: function (a, b) {
                var d = this.chart;
                return k(a || this.points || [], function (a) {
                    return b && !d.isInsidePlot(a.plotX, a.plotY, d.inverted) ? !1 : !a.isNull
                })
            },
            setClip: function (a) {
                var b = this.chart, d = this.options, c = b.renderer, f = b.inverted, h = this.clipBox,
                    e = h || b.clipBox,
                    g = this.sharedClipKey || ["_sharedClip", a && a.duration, a && a.easing, e.height, d.xAxis, d.yAxis].join(),
                    q = b[g], n = b[g + "m"];
                q || (a && (e.width = 0, f && (e.x = b.plotSizeX), b[g + "m"] = n = c.clipRect(f ? b.plotSizeX + 99 : -99, f ? -b.plotLeft :
                    -b.plotTop, 99, f ? b.chartWidth : b.chartHeight)), b[g] = q = c.clipRect(e), q.count = {length: 0});
                a && !q.count[this.index] && (q.count[this.index] = !0, q.count.length += 1);
                !1 !== d.clip && (this.group.clip(a || h ? q : b.clipRect), this.markerGroup.clip(n), this.sharedClipKey = g);
                a || (q.count[this.index] && (delete q.count[this.index], --q.count.length), 0 === q.count.length && g && b[g] && (h || (b[g] = b[g].destroy()), b[g + "m"] && (b[g + "m"] = b[g + "m"].destroy())))
            },
            animate: function (a) {
                var b = this.chart, d = E(this.options.animation), c;
                a ? this.setClip(d) :
                    (c = this.sharedClipKey, (a = b[c]) && a.animate({
                        width: b.plotSizeX,
                        x: 0
                    }, d), b[c + "m"] && b[c + "m"].animate({width: b.plotSizeX + 99, x: 0}, d), this.animate = null)
            },
            afterAnimate: function () {
                this.setClip();
                c(this, "afterAnimate");
                this.finishedAnimating = !0
            },
            drawPoints: function () {
                var a = this.points, b = this.chart, d, c, f, h, e = this.options.marker, g, q, n,
                    k = this[this.specialGroup] || this.markerGroup, y,
                    A = u(e.enabled, this.xAxis.isRadial ? !0 : null, this.closestPointRangePx >= e.enabledThreshold * e.radius);
                if (!1 !== e.enabled || this._hasPointMarkers) for (d =
                                                                        0; d < a.length; d++) c = a[d], h = c.graphic, g = c.marker || {}, q = !!c.marker, f = A && void 0 === g.enabled || g.enabled, n = c.isInside, f && !c.isNull ? (f = u(g.symbol, this.symbol), y = this.markerAttribs(c, c.selected && "select"), h ? h[n ? "show" : "hide"](!0).animate(y) : n && (0 < y.width || c.hasImage) && (c.graphic = h = b.renderer.symbol(f, y.x, y.y, y.width, y.height, q ? g : e).add(k)), h && h.attr(this.pointAttribs(c, c.selected && "select")), h && h.addClass(c.getClassName(), !0)) : h && (c.graphic = h.destroy())
            },
            markerAttribs: function (a, b) {
                var d = this.options.marker,
                    c = a.marker || {}, f = c.symbol || d.symbol, h = u(c.radius, d.radius);
                b && (d = d.states[b], b = c.states && c.states[b], h = u(b && b.radius, d && d.radius, h + (d && d.radiusPlus || 0)));
                a.hasImage = f && 0 === f.indexOf("url");
                a.hasImage && (h = 0);
                a = {x: Math.floor(a.plotX) - h, y: a.plotY - h};
                h && (a.width = a.height = 2 * h);
                return a
            },
            pointAttribs: function (a, b) {
                var d = this.options.marker, c = a && a.options, f = c && c.marker || {}, h = this.color,
                    e = c && c.color, g = a && a.color, c = u(f.lineWidth, d.lineWidth);
                a = a && a.zone && a.zone.color;
                h = e || a || g || h;
                a = f.fillColor || d.fillColor ||
                    h;
                h = f.lineColor || d.lineColor || h;
                b && (d = d.states[b], b = f.states && f.states[b] || {}, c = u(b.lineWidth, d.lineWidth, c + u(b.lineWidthPlus, d.lineWidthPlus, 0)), a = b.fillColor || d.fillColor || a, h = b.lineColor || d.lineColor || h);
                return {stroke: h, "stroke-width": c, fill: a}
            },
            destroy: function () {
                var a = this, b = a.chart, d = /AppleWebKit\/533/.test(H.navigator.userAgent), e, g, h = a.data || [],
                    q, n;
                c(a, "destroy");
                D(a);
                v(a.axisTypes || [], function (d) {
                    (n = a[d]) && n.series && (B(n.series, a), n.isDirty = n.forceRedraw = !0)
                });
                a.legendItem && a.chart.legend.destroyItem(a);
                for (g = h.length; g--;) (q = h[g]) && q.destroy && q.destroy();
                a.points = null;
                clearTimeout(a.animationTimeout);
                f(a, function (a, b) {
                    a instanceof A && !a.survive && (e = d && "group" === b ? "hide" : "destroy", a[e]())
                });
                b.hoverSeries === a && (b.hoverSeries = null);
                B(b.series, a);
                b.orderSeries();
                f(a, function (d, b) {
                    delete a[b]
                })
            },
            getGraphPath: function (a, b, d) {
                var c = this, f = c.options, h = f.step, e, g = [], q = [], n;
                a = a || c.points;
                (e = a.reversed) && a.reverse();
                (h = {right: 1, center: 2}[h] || h && 3) && e && (h = 4 - h);
                !f.connectNulls || b || d || (a = this.getValidPoints(a));
                v(a, function (e, z) {
                    var k = e.plotX, y = e.plotY, A = a[z - 1];
                    (e.leftCliff || A && A.rightCliff) && !d && (n = !0);
                    e.isNull && !p(b) && 0 < z ? n = !f.connectNulls : e.isNull && !b ? n = !0 : (0 === z || n ? z = ["M", e.plotX, e.plotY] : c.getPointSpline ? z = c.getPointSpline(a, e, z) : h ? (z = 1 === h ? ["L", A.plotX, y] : 2 === h ? ["L", (A.plotX + k) / 2, A.plotY, "L", (A.plotX + k) / 2, y] : ["L", k, A.plotY], z.push("L", k, y)) : z = ["L", k, y], q.push(e.x), h && q.push(e.x), g.push.apply(g, z), n = !1)
                });
                g.xMap = q;
                return c.graphPath = g
            },
            drawGraph: function () {
                var a = this, b = this.options, d = (this.gappedPath ||
                    this.getGraphPath).call(this),
                    c = [["graph", "highcharts-graph", b.lineColor || this.color, b.dashStyle]];
                v(this.zones, function (d, f) {
                    c.push(["zone-graph-" + f, "highcharts-graph highcharts-zone-graph-" + f + " " + (d.className || ""), d.color || a.color, d.dashStyle || b.dashStyle])
                });
                v(c, function (c, f) {
                    var h = c[0], e = a[h];
                    e ? (e.endX = a.preventGraphAnimation ? null : d.xMap, e.animate({d: d})) : d.length && (a[h] = a.chart.renderer.path(d).addClass(c[1]).attr({zIndex: 1}).add(a.group), e = {
                        stroke: c[2], "stroke-width": b.lineWidth, fill: a.fillGraph &&
                        a.color || "none"
                    }, c[3] ? e.dashstyle = c[3] : "square" !== b.linecap && (e["stroke-linecap"] = e["stroke-linejoin"] = "round"), e = a[h].attr(e).shadow(2 > f && b.shadow));
                    e && (e.startX = d.xMap, e.isArea = d.isArea)
                })
            },
            applyZones: function () {
                var a = this, b = this.chart, d = b.renderer, c = this.zones, f, h, e = this.clips || [], g,
                    q = this.graph, n = this.area, k = Math.max(b.chartWidth, b.chartHeight),
                    y = this[(this.zoneAxis || "y") + "Axis"], A, t, l = b.inverted, m, H, D, p, r = !1;
                c.length && (q || n) && y && void 0 !== y.min && (t = y.reversed, m = y.horiz, q && q.hide(), n && n.hide(),
                    A = y.getExtremes(), v(c, function (c, z) {
                    f = t ? m ? b.plotWidth : 0 : m ? 0 : y.toPixels(A.min);
                    f = Math.min(Math.max(u(h, f), 0), k);
                    h = Math.min(Math.max(Math.round(y.toPixels(u(c.value, A.max), !0)), 0), k);
                    r && (f = h = y.toPixels(A.max));
                    H = Math.abs(f - h);
                    D = Math.min(f, h);
                    p = Math.max(f, h);
                    y.isXAxis ? (g = {
                        x: l ? p : D,
                        y: 0,
                        width: H,
                        height: k
                    }, m || (g.x = b.plotHeight - g.x)) : (g = {
                        x: 0,
                        y: l ? p : D,
                        width: k,
                        height: H
                    }, m && (g.y = b.plotWidth - g.y));
                    l && d.isVML && (g = y.isXAxis ? {x: 0, y: t ? D : p, height: g.width, width: b.chartWidth} : {
                        x: g.y - b.plotLeft - b.spacingBox.x, y: 0, width: g.height,
                        height: b.chartHeight
                    });
                    e[z] ? e[z].animate(g) : (e[z] = d.clipRect(g), q && a["zone-graph-" + z].clip(e[z]), n && a["zone-area-" + z].clip(e[z]));
                    r = c.value > A.max
                }), this.clips = e)
            },
            invertGroups: function (a) {
                function b() {
                    v(["group", "markerGroup"], function (b) {
                        d[b] && (c.renderer.isVML && d[b].attr({
                            width: d.yAxis.len,
                            height: d.xAxis.len
                        }), d[b].width = d.yAxis.len, d[b].height = d.xAxis.len, d[b].invert(a))
                    })
                }

                var d = this, c = d.chart, f;
                d.xAxis && (f = C(c, "resize", b), C(d, "destroy", f), b(a), d.invertGroups = b)
            },
            plotGroup: function (a, b, d, c, f) {
                var h =
                    this[a], e = !h;
                e && (this[a] = h = this.chart.renderer.g().attr({zIndex: c || .1}).add(f));
                h.addClass("highcharts-" + b + " highcharts-series-" + this.index + " highcharts-" + this.type + "-series " + (p(this.colorIndex) ? "highcharts-color-" + this.colorIndex + " " : "") + (this.options.className || "") + (h.hasClass("highcharts-tracker") ? " highcharts-tracker" : ""), !0);
                h.attr({visibility: d})[e ? "attr" : "animate"](this.getPlotBox());
                return h
            },
            getPlotBox: function () {
                var a = this.chart, b = this.xAxis, d = this.yAxis;
                a.inverted && (b = d, d = this.xAxis);
                return {translateX: b ? b.left : a.plotLeft, translateY: d ? d.top : a.plotTop, scaleX: 1, scaleY: 1}
            },
            render: function () {
                var a = this, b = a.chart, d, f = a.options,
                    e = !!a.animate && b.renderer.isSVG && E(f.animation).duration,
                    h = a.visible ? "inherit" : "hidden", g = f.zIndex, q = a.hasRendered, n = b.seriesGroup,
                    k = b.inverted;
                d = a.plotGroup("group", "series", h, g, n);
                a.markerGroup = a.plotGroup("markerGroup", "markers", h, g, n);
                e && a.animate(!0);
                d.inverted = a.isCartesian ? k : !1;
                a.drawGraph && (a.drawGraph(), a.applyZones());
                a.drawDataLabels && a.drawDataLabels();
                a.visible && a.drawPoints();
                a.drawTracker && !1 !== a.options.enableMouseTracking && a.drawTracker();
                a.invertGroups(k);
                !1 === f.clip || a.sharedClipKey || q || d.clip(b.clipRect);
                e && a.animate();
                q || (a.animationTimeout = y(function () {
                    a.afterAnimate()
                }, e));
                a.isDirty = !1;
                a.hasRendered = !0;
                c(a, "afterRender")
            },
            redraw: function () {
                var a = this.chart, b = this.isDirty || this.isDirtyData, d = this.group, c = this.xAxis,
                    f = this.yAxis;
                d && (a.inverted && d.attr({width: a.plotWidth, height: a.plotHeight}), d.animate({
                    translateX: u(c && c.left, a.plotLeft),
                    translateY: u(f && f.top, a.plotTop)
                }));
                this.translate();
                this.render();
                b && delete this.kdTree
            },
            kdAxisArray: ["clientX", "plotY"],
            searchPoint: function (a, b) {
                var d = this.xAxis, c = this.yAxis, f = this.chart.inverted;
                return this.searchKDTree({
                    clientX: f ? d.len - a.chartY + d.pos : a.chartX - d.pos,
                    plotY: f ? c.len - a.chartX + c.pos : a.chartY - c.pos
                }, b)
            },
            buildKDTree: function () {
                function a(d, c, f) {
                    var h, e;
                    if (e = d && d.length) return h = b.kdAxisArray[c % f], d.sort(function (a, d) {
                        return a[h] - d[h]
                    }), e = Math.floor(e / 2), {
                        point: d[e], left: a(d.slice(0,
                            e), c + 1, f), right: a(d.slice(e + 1), c + 1, f)
                    }
                }

                this.buildingKdTree = !0;
                var b = this, d = -1 < b.options.findNearestPointBy.indexOf("y") ? 2 : 1;
                delete b.kdTree;
                y(function () {
                    b.kdTree = a(b.getValidPoints(null, !b.directTouch), d, d);
                    b.buildingKdTree = !1
                }, b.options.kdNow ? 0 : 1)
            },
            searchKDTree: function (a, b) {
                function d(a, b, g, q) {
                    var n = b.point, k = c.kdAxisArray[g % q], z, y, A = n;
                    y = p(a[f]) && p(n[f]) ? Math.pow(a[f] - n[f], 2) : null;
                    z = p(a[h]) && p(n[h]) ? Math.pow(a[h] - n[h], 2) : null;
                    z = (y || 0) + (z || 0);
                    n.dist = p(z) ? Math.sqrt(z) : Number.MAX_VALUE;
                    n.distX = p(y) ?
                        Math.sqrt(y) : Number.MAX_VALUE;
                    k = a[k] - n[k];
                    z = 0 > k ? "left" : "right";
                    y = 0 > k ? "right" : "left";
                    b[z] && (z = d(a, b[z], g + 1, q), A = z[e] < A[e] ? z : n);
                    b[y] && Math.sqrt(k * k) < A[e] && (a = d(a, b[y], g + 1, q), A = a[e] < A[e] ? a : A);
                    return A
                }

                var c = this, f = this.kdAxisArray[0], h = this.kdAxisArray[1], e = b ? "distX" : "dist";
                b = -1 < c.options.findNearestPointBy.indexOf("y") ? 2 : 1;
                this.kdTree || this.buildingKdTree || this.buildKDTree();
                if (this.kdTree) return d(a, this.kdTree, b, b)
            }
        })
    })(L);
    (function (a) {
        var C = a.Axis, E = a.Chart, F = a.correctFloat, r = a.defined, m = a.destroyObjectProperties,
            l = a.each, w = a.format, p = a.objectEach, v = a.pick, B = a.Series;
        a.StackItem = function (a, c, k, b, g) {
            var e = a.chart.inverted;
            this.axis = a;
            this.isNegative = k;
            this.options = c;
            this.x = b;
            this.total = null;
            this.points = {};
            this.stack = g;
            this.rightCliff = this.leftCliff = 0;
            this.alignOptions = {
                align: c.align || (e ? k ? "left" : "right" : "center"),
                verticalAlign: c.verticalAlign || (e ? "middle" : k ? "bottom" : "top"),
                y: v(c.y, e ? 4 : k ? 14 : -6),
                x: v(c.x, e ? k ? -6 : 6 : 0)
            };
            this.textAlign = c.textAlign || (e ? k ? "right" : "left" : "center")
        };
        a.StackItem.prototype = {
            destroy: function () {
                m(this,
                    this.axis)
            }, render: function (a) {
                var c = this.axis.chart, e = this.options, b = e.format,
                    b = b ? w(b, this, c.time) : e.formatter.call(this);
                this.label ? this.label.attr({
                    text: b,
                    visibility: "hidden"
                }) : this.label = c.renderer.text(b, null, null, e.useHTML).css(e.style).attr({
                    align: this.textAlign,
                    rotation: e.rotation,
                    visibility: "hidden"
                }).add(a)
            }, setOffset: function (a, c) {
                var e = this.axis, b = e.chart, g = e.translate(e.usePercentage ? 100 : this.total, 0, 0, 0, 1),
                    e = e.translate(0), e = Math.abs(g - e);
                a = b.xAxis[0].translate(this.x) + a;
                g = this.getStackBox(b,
                    this, a, g, c, e);
                if (c = this.label) c.align(this.alignOptions, null, g), g = c.alignAttr, c[!1 === this.options.crop || b.isInsidePlot(g.x, g.y) ? "show" : "hide"](!0)
            }, getStackBox: function (a, c, k, b, g, n) {
                var e = c.axis.reversed, f = a.inverted;
                a = a.plotHeight;
                c = c.isNegative && !e || !c.isNegative && e;
                return {
                    x: f ? c ? b : b - n : k,
                    y: f ? a - k - g : c ? a - b - n : a - b,
                    width: f ? n : g,
                    height: f ? g : n
                }
            }
        };
        E.prototype.getStacks = function () {
            var a = this;
            l(a.yAxis, function (a) {
                a.stacks && a.hasVisibleSeries && (a.oldStacks = a.stacks)
            });
            l(a.series, function (c) {
                !c.options.stacking ||
                !0 !== c.visible && !1 !== a.options.chart.ignoreHiddenSeries || (c.stackKey = c.type + v(c.options.stack, ""))
            })
        };
        C.prototype.buildStacks = function () {
            var a = this.series, c = v(this.options.reversedStacks, !0), k = a.length, b;
            if (!this.isXAxis) {
                this.usePercentage = !1;
                for (b = k; b--;) a[c ? b : k - b - 1].setStackedPoints();
                for (b = 0; b < k; b++) a[b].modifyStacks()
            }
        };
        C.prototype.renderStackTotals = function () {
            var a = this.chart, c = a.renderer, k = this.stacks, b = this.stackTotalGroup;
            b || (this.stackTotalGroup = b = c.g("stack-labels").attr({
                visibility: "visible",
                zIndex: 6
            }).add());
            b.translate(a.plotLeft, a.plotTop);
            p(k, function (a) {
                p(a, function (a) {
                    a.render(b)
                })
            })
        };
        C.prototype.resetStacks = function () {
            var a = this, c = a.stacks;
            a.isXAxis || p(c, function (c) {
                p(c, function (b, e) {
                    b.touched < a.stacksTouched ? (b.destroy(), delete c[e]) : (b.total = null, b.cumulative = null)
                })
            })
        };
        C.prototype.cleanStacks = function () {
            var a;
            this.isXAxis || (this.oldStacks && (a = this.stacks = this.oldStacks), p(a, function (a) {
                p(a, function (a) {
                    a.cumulative = a.total
                })
            }))
        };
        B.prototype.setStackedPoints = function () {
            if (this.options.stacking &&
                (!0 === this.visible || !1 === this.chart.options.chart.ignoreHiddenSeries)) {
                var e = this.processedXData, c = this.processedYData, k = [], b = c.length, g = this.options,
                    n = g.threshold, t = v(g.startFromThreshold && n, 0), f = g.stack, g = g.stacking,
                    u = this.stackKey, l = "-" + u, q = this.negStacks, A = this.yAxis, y = A.stacks, m = A.oldStacks,
                    p, J, d, z, G, h, x;
                A.stacksTouched += 1;
                for (G = 0; G < b; G++) h = e[G], x = c[G], p = this.getStackIndicator(p, h, this.index), z = p.key, d = (J = q && x < (t ? 0 : n)) ? l : u, y[d] || (y[d] = {}), y[d][h] || (m[d] && m[d][h] ? (y[d][h] = m[d][h], y[d][h].total =
                    null) : y[d][h] = new a.StackItem(A, A.options.stackLabels, J, h, f)), d = y[d][h], null !== x ? (d.points[z] = d.points[this.index] = [v(d.cumulative, t)], r(d.cumulative) || (d.base = z), d.touched = A.stacksTouched, 0 < p.index && !1 === this.singleStacks && (d.points[z][0] = d.points[this.index + "," + h + ",0"][0])) : d.points[z] = d.points[this.index] = null, "percent" === g ? (J = J ? u : l, q && y[J] && y[J][h] ? (J = y[J][h], d.total = J.total = Math.max(J.total, d.total) + Math.abs(x) || 0) : d.total = F(d.total + (Math.abs(x) || 0))) : d.total = F(d.total + (x || 0)), d.cumulative = v(d.cumulative,
                    t) + (x || 0), null !== x && (d.points[z].push(d.cumulative), k[G] = d.cumulative);
                "percent" === g && (A.usePercentage = !0);
                this.stackedYData = k;
                A.oldStacks = {}
            }
        };
        B.prototype.modifyStacks = function () {
            var a = this, c = a.stackKey, k = a.yAxis.stacks, b = a.processedXData, g, n = a.options.stacking;
            a[n + "Stacker"] && l([c, "-" + c], function (c) {
                for (var f = b.length, e, t; f--;) if (e = b[f], g = a.getStackIndicator(g, e, a.index, c), t = (e = k[c] && k[c][e]) && e.points[g.key]) a[n + "Stacker"](t, e, f)
            })
        };
        B.prototype.percentStacker = function (a, c, k) {
            c = c.total ? 100 / c.total :
                0;
            a[0] = F(a[0] * c);
            a[1] = F(a[1] * c);
            this.stackedYData[k] = a[1]
        };
        B.prototype.getStackIndicator = function (a, c, k, b) {
            !r(a) || a.x !== c || b && a.key !== b ? a = {x: c, index: 0, key: b} : a.index++;
            a.key = [k, c, a.index].join();
            return a
        }
    })(L);
    (function (a) {
        var C = a.addEvent, E = a.animate, F = a.Axis, r = a.createElement, m = a.css, l = a.defined, w = a.each,
            p = a.erase, v = a.extend, B = a.fireEvent, e = a.inArray, c = a.isNumber, k = a.isObject, b = a.isArray,
            g = a.merge, n = a.objectEach, t = a.pick, f = a.Point, u = a.Series, D = a.seriesTypes, q = a.setAnimation,
            A = a.splat;
        v(a.Chart.prototype,
            {
                addSeries: function (a, b, c) {
                    var f, d = this;
                    a && (b = t(b, !0), B(d, "addSeries", {options: a}, function () {
                        f = d.initSeries(a);
                        d.isDirtyLegend = !0;
                        d.linkSeries();
                        b && d.redraw(c)
                    }));
                    return f
                },
                addAxis: function (a, b, c, f) {
                    var d = b ? "xAxis" : "yAxis", e = this.options;
                    a = g(a, {index: this[d].length, isX: b});
                    b = new F(this, a);
                    e[d] = A(e[d] || {});
                    e[d].push(a);
                    t(c, !0) && this.redraw(f);
                    return b
                },
                showLoading: function (a) {
                    var b = this, c = b.options, f = b.loadingDiv, d = c.loading, e = function () {
                        f && m(f, {
                            left: b.plotLeft + "px", top: b.plotTop + "px", width: b.plotWidth +
                            "px", height: b.plotHeight + "px"
                        })
                    };
                    f || (b.loadingDiv = f = r("div", {className: "highcharts-loading highcharts-loading-hidden"}, null, b.container), b.loadingSpan = r("span", {className: "highcharts-loading-inner"}, null, f), C(b, "redraw", e));
                    f.className = "highcharts-loading";
                    b.loadingSpan.innerHTML = a || c.lang.loading;
                    m(f, v(d.style, {zIndex: 10}));
                    m(b.loadingSpan, d.labelStyle);
                    b.loadingShown || (m(f, {
                        opacity: 0,
                        display: ""
                    }), E(f, {opacity: d.style.opacity || .5}, {duration: d.showDuration || 0}));
                    b.loadingShown = !0;
                    e()
                },
                hideLoading: function () {
                    var a =
                        this.options, b = this.loadingDiv;
                    b && (b.className = "highcharts-loading highcharts-loading-hidden", E(b, {opacity: 0}, {
                        duration: a.loading.hideDuration || 100,
                        complete: function () {
                            m(b, {display: "none"})
                        }
                    }));
                    this.loadingShown = !1
                },
                propsRequireDirtyBox: "backgroundColor borderColor borderWidth margin marginTop marginRight marginBottom marginLeft spacing spacingTop spacingRight spacingBottom spacingLeft borderRadius plotBackgroundColor plotBackgroundImage plotBorderColor plotBorderWidth plotShadow shadow".split(" "),
                propsRequireUpdateSeries: "chart.inverted chart.polar chart.ignoreHiddenSeries chart.type colors plotOptions time tooltip".split(" "),
                update: function (a, b, f) {
                    var q = this, d = {credits: "addCredits", title: "setTitle", subtitle: "setSubtitle"}, z = a.chart,
                        k, h, y = [];
                    if (z) {
                        g(!0, q.options.chart, z);
                        "className" in z && q.setClassName(z.className);
                        if ("inverted" in z || "polar" in z) q.propFromSeries(), k = !0;
                        "alignTicks" in z && (k = !0);
                        n(z, function (a, d) {
                            -1 !== e("chart." + d, q.propsRequireUpdateSeries) && (h = !0);
                            -1 !== e(d, q.propsRequireDirtyBox) &&
                            (q.isDirtyBox = !0)
                        });
                        "style" in z && q.renderer.setStyle(z.style)
                    }
                    a.colors && (this.options.colors = a.colors);
                    a.plotOptions && g(!0, this.options.plotOptions, a.plotOptions);
                    n(a, function (a, b) {
                        if (q[b] && "function" === typeof q[b].update) q[b].update(a, !1); else if ("function" === typeof q[d[b]]) q[d[b]](a);
                        "chart" !== b && -1 !== e(b, q.propsRequireUpdateSeries) && (h = !0)
                    });
                    w("xAxis yAxis zAxis series colorAxis pane".split(" "), function (d) {
                        a[d] && (w(A(a[d]), function (a, b) {
                            (b = l(a.id) && q.get(a.id) || q[d][b]) && b.coll === d && (b.update(a,
                                !1), f && (b.touched = !0));
                            if (!b && f) if ("series" === d) q.addSeries(a, !1).touched = !0; else if ("xAxis" === d || "yAxis" === d) q.addAxis(a, "xAxis" === d, !1).touched = !0
                        }), f && w(q[d], function (a) {
                            a.touched ? delete a.touched : y.push(a)
                        }))
                    });
                    w(y, function (a) {
                        a.remove(!1)
                    });
                    k && w(q.axes, function (a) {
                        a.update({}, !1)
                    });
                    h && w(q.series, function (a) {
                        a.update({}, !1)
                    });
                    a.loading && g(!0, q.options.loading, a.loading);
                    k = z && z.width;
                    z = z && z.height;
                    c(k) && k !== q.chartWidth || c(z) && z !== q.chartHeight ? q.setSize(k, z) : t(b, !0) && q.redraw()
                },
                setSubtitle: function (a) {
                    this.setTitle(void 0,
                        a)
                }
            });
        v(f.prototype, {
            update: function (a, b, c, f) {
                function d() {
                    e.applyOptions(a);
                    null === e.y && h && (e.graphic = h.destroy());
                    k(a, !0) && (h && h.element && a && a.marker && void 0 !== a.marker.symbol && (e.graphic = h.destroy()), a && a.dataLabels && e.dataLabel && (e.dataLabel = e.dataLabel.destroy()), e.connector && (e.connector = e.connector.destroy()));
                    q = e.index;
                    g.updateParallelArrays(e, q);
                    A.data[q] = k(A.data[q], !0) || k(a, !0) ? e.options : a;
                    g.isDirty = g.isDirtyData = !0;
                    !g.fixedBox && g.hasCartesianSeries && (n.isDirtyBox = !0);
                    "point" === A.legendType &&
                    (n.isDirtyLegend = !0);
                    b && n.redraw(c)
                }

                var e = this, g = e.series, h = e.graphic, q, n = g.chart, A = g.options;
                b = t(b, !0);
                !1 === f ? d() : e.firePointEvent("update", {options: a}, d)
            }, remove: function (a, b) {
                this.series.removePoint(e(this, this.series.data), a, b)
            }
        });
        v(u.prototype, {
            addPoint: function (a, b, c, f) {
                var d = this.options, e = this.data, g = this.chart, h = this.xAxis, h = h && h.hasNames && h.names,
                    q = d.data, n, k, A = this.xData, y, u;
                b = t(b, !0);
                n = {series: this};
                this.pointClass.prototype.applyOptions.apply(n, [a]);
                u = n.x;
                y = A.length;
                if (this.requireSorting &&
                    u < A[y - 1]) for (k = !0; y && A[y - 1] > u;) y--;
                this.updateParallelArrays(n, "splice", y, 0, 0);
                this.updateParallelArrays(n, y);
                h && n.name && (h[u] = n.name);
                q.splice(y, 0, a);
                k && (this.data.splice(y, 0, null), this.processData());
                "point" === d.legendType && this.generatePoints();
                c && (e[0] && e[0].remove ? e[0].remove(!1) : (e.shift(), this.updateParallelArrays(n, "shift"), q.shift()));
                this.isDirtyData = this.isDirty = !0;
                b && g.redraw(f)
            }, removePoint: function (a, b, c) {
                var f = this, d = f.data, e = d[a], g = f.points, h = f.chart, n = function () {
                    g && g.length === d.length &&
                    g.splice(a, 1);
                    d.splice(a, 1);
                    f.options.data.splice(a, 1);
                    f.updateParallelArrays(e || {series: f}, "splice", a, 1);
                    e && e.destroy();
                    f.isDirty = !0;
                    f.isDirtyData = !0;
                    b && h.redraw()
                };
                q(c, h);
                b = t(b, !0);
                e ? e.firePointEvent("remove", null, n) : n()
            }, remove: function (a, b, c) {
                function f() {
                    d.destroy();
                    e.isDirtyLegend = e.isDirtyBox = !0;
                    e.linkSeries();
                    t(a, !0) && e.redraw(b)
                }

                var d = this, e = d.chart;
                !1 !== c ? B(d, "remove", null, f) : f()
            }, update: function (a, b) {
                var c = this, f = c.chart, d = c.userOptions, e = c.oldType || c.type,
                    q = a.type || d.type || f.options.chart.type,
                    h = D[e].prototype, n, k = ["group", "markerGroup", "dataLabelsGroup"],
                    A = ["navigatorSeries", "baseSeries"], y = c.finishedAnimating && {animation: !1};
                if (Object.keys && "data" === Object.keys(a).toString()) return this.setData(a.data, b);
                A = k.concat(A);
                w(A, function (a) {
                    A[a] = c[a];
                    delete c[a]
                });
                a = g(d, y, {index: c.index, pointStart: c.xData[0]}, {data: c.options.data}, a);
                c.remove(!1, null, !1);
                for (n in h) c[n] = void 0;
                v(c, D[q || e].prototype);
                w(A, function (a) {
                    c[a] = A[a]
                });
                c.init(f, a);
                a.zIndex !== d.zIndex && w(k, function (d) {
                    c[d] && c[d].attr({zIndex: a.zIndex})
                });
                c.oldType = e;
                f.linkSeries();
                t(b, !0) && f.redraw(!1)
            }
        });
        v(F.prototype, {
            update: function (a, b) {
                var c = this.chart;
                a = c.options[this.coll][this.options.index] = g(this.userOptions, a);
                this.destroy(!0);
                this.init(c, v(a, {events: void 0}));
                c.isDirtyBox = !0;
                t(b, !0) && c.redraw()
            }, remove: function (a) {
                for (var c = this.chart, f = this.coll, e = this.series, d = e.length; d--;) e[d] && e[d].remove(!1);
                p(c.axes, this);
                p(c[f], this);
                b(c.options[f]) ? c.options[f].splice(this.options.index, 1) : delete c.options[f];
                w(c[f], function (a, d) {
                    a.options.index =
                        d
                });
                this.destroy();
                c.isDirtyBox = !0;
                t(a, !0) && c.redraw()
            }, setTitle: function (a, b) {
                this.update({title: a}, b)
            }, setCategories: function (a, b) {
                this.update({categories: a}, b)
            }
        })
    })(L);
    (function (a) {
        var C = a.color, E = a.each, F = a.map, r = a.pick, m = a.Series, l = a.seriesType;
        l("area", "line", {softThreshold: !1, threshold: 0}, {
            singleStacks: !1, getStackPoints: function (l) {
                var m = [], v = [], w = this.xAxis, e = this.yAxis, c = e.stacks[this.stackKey], k = {}, b = this.index,
                    g = e.series, n = g.length, t, f = r(e.options.reversedStacks, !0) ? 1 : -1, u;
                l = l || this.points;
                if (this.options.stacking) {
                    for (u = 0; u < l.length; u++) l[u].leftNull = l[u].rightNull = null, k[l[u].x] = l[u];
                    a.objectEach(c, function (a, b) {
                        null !== a.total && v.push(b)
                    });
                    v.sort(function (a, b) {
                        return a - b
                    });
                    t = F(g, function () {
                        return this.visible
                    });
                    E(v, function (a, g) {
                        var q = 0, y, l;
                        if (k[a] && !k[a].isNull) m.push(k[a]), E([-1, 1], function (e) {
                            var q = 1 === e ? "rightNull" : "leftNull", d = 0, A = c[v[g + e]];
                            if (A) for (u = b; 0 <= u && u < n;) y = A.points[u], y || (u === b ? k[a][q] = !0 : t[u] && (l = c[a].points[u]) && (d -= l[1] - l[0])), u += f;
                            k[a][1 === e ? "rightCliff" : "leftCliff"] =
                                d
                        }); else {
                            for (u = b; 0 <= u && u < n;) {
                                if (y = c[a].points[u]) {
                                    q = y[1];
                                    break
                                }
                                u += f
                            }
                            q = e.translate(q, 0, 1, 0, 1);
                            m.push({isNull: !0, plotX: w.translate(a, 0, 0, 0, 1), x: a, plotY: q, yBottom: q})
                        }
                    })
                }
                return m
            }, getGraphPath: function (a) {
                var l = m.prototype.getGraphPath, v = this.options, w = v.stacking, e = this.yAxis, c, k, b = [],
                    g = [], n = this.index, t, f = e.stacks[this.stackKey], u = v.threshold,
                    D = e.getThreshold(v.threshold), q, v = v.connectNulls || "percent" === w, A = function (c, q, k) {
                        var A = a[c];
                        c = w && f[A.x].points[n];
                        var d = A[k + "Null"] || 0;
                        k = A[k + "Cliff"] || 0;
                        var z,
                            y, A = !0;
                        k || d ? (z = (d ? c[0] : c[1]) + k, y = c[0] + k, A = !!d) : !w && a[q] && a[q].isNull && (z = y = u);
                        void 0 !== z && (g.push({
                            plotX: t,
                            plotY: null === z ? D : e.getThreshold(z),
                            isNull: A,
                            isCliff: !0
                        }), b.push({plotX: t, plotY: null === y ? D : e.getThreshold(y), doCurve: !1}))
                    };
                a = a || this.points;
                w && (a = this.getStackPoints(a));
                for (c = 0; c < a.length; c++) if (k = a[c].isNull, t = r(a[c].rectPlotX, a[c].plotX), q = r(a[c].yBottom, D), !k || v) v || A(c, c - 1, "left"), k && !w && v || (g.push(a[c]), b.push({
                    x: c,
                    plotX: t,
                    plotY: q
                })), v || A(c, c + 1, "right");
                c = l.call(this, g, !0, !0);
                b.reversed =
                    !0;
                k = l.call(this, b, !0, !0);
                k.length && (k[0] = "L");
                k = c.concat(k);
                l = l.call(this, g, !1, v);
                k.xMap = c.xMap;
                this.areaPath = k;
                return l
            }, drawGraph: function () {
                this.areaPath = [];
                m.prototype.drawGraph.apply(this);
                var a = this, l = this.areaPath, v = this.options,
                    B = [["area", "highcharts-area", this.color, v.fillColor]];
                E(this.zones, function (e, c) {
                    B.push(["zone-area-" + c, "highcharts-area highcharts-zone-area-" + c + " " + e.className, e.color || a.color, e.fillColor || v.fillColor])
                });
                E(B, function (e) {
                    var c = e[0], k = a[c];
                    k ? (k.endX = a.preventGraphAnimation ?
                        null : l.xMap, k.animate({d: l})) : (k = a[c] = a.chart.renderer.path(l).addClass(e[1]).attr({
                        fill: r(e[3], C(e[2]).setOpacity(r(v.fillOpacity, .75)).get()),
                        zIndex: 0
                    }).add(a.group), k.isArea = !0);
                    k.startX = l.xMap;
                    k.shiftUnit = v.step ? 2 : 1
                })
            }, drawLegendSymbol: a.LegendSymbolMixin.drawRectangle
        })
    })(L);
    (function (a) {
        var C = a.pick;
        a = a.seriesType;
        a("spline", "line", {}, {
            getPointSpline: function (a, F, r) {
                var m = F.plotX, l = F.plotY, w = a[r - 1];
                r = a[r + 1];
                var p, v, B, e;
                if (w && !w.isNull && !1 !== w.doCurve && !F.isCliff && r && !r.isNull && !1 !== r.doCurve &&
                    !F.isCliff) {
                    a = w.plotY;
                    B = r.plotX;
                    r = r.plotY;
                    var c = 0;
                    p = (1.5 * m + w.plotX) / 2.5;
                    v = (1.5 * l + a) / 2.5;
                    B = (1.5 * m + B) / 2.5;
                    e = (1.5 * l + r) / 2.5;
                    B !== p && (c = (e - v) * (B - m) / (B - p) + l - e);
                    v += c;
                    e += c;
                    v > a && v > l ? (v = Math.max(a, l), e = 2 * l - v) : v < a && v < l && (v = Math.min(a, l), e = 2 * l - v);
                    e > r && e > l ? (e = Math.max(r, l), v = 2 * l - e) : e < r && e < l && (e = Math.min(r, l), v = 2 * l - e);
                    F.rightContX = B;
                    F.rightContY = e
                }
                F = ["C", C(w.rightContX, w.plotX), C(w.rightContY, w.plotY), C(p, m), C(v, l), m, l];
                w.rightContX = w.rightContY = null;
                return F
            }
        })
    })(L);
    (function (a) {
        var C = a.seriesTypes.area.prototype,
            E = a.seriesType;
        E("areaspline", "spline", a.defaultPlotOptions.area, {
            getStackPoints: C.getStackPoints,
            getGraphPath: C.getGraphPath,
            drawGraph: C.drawGraph,
            drawLegendSymbol: a.LegendSymbolMixin.drawRectangle
        })
    })(L);
    (function (a) {
        var C = a.animObject, E = a.color, F = a.each, r = a.extend, m = a.isNumber, l = a.merge, w = a.pick,
            p = a.Series, v = a.seriesType, B = a.svg;
        v("column", "line", {
            borderRadius: 0,
            crisp: !0,
            groupPadding: .2,
            marker: null,
            pointPadding: .1,
            minPointLength: 0,
            cropThreshold: 50,
            pointRange: null,
            states: {
                hover: {halo: !1, brightness: .1},
                select: {color: "#cccccc", borderColor: "#000000"}
            },
            dataLabels: {align: null, verticalAlign: null, y: null},
            softThreshold: !1,
            startFromThreshold: !0,
            stickyTracking: !1,
            tooltip: {distance: 6},
            threshold: 0,
            borderColor: "#ffffff"
        }, {
            cropShoulder: 0,
            directTouch: !0,
            trackerGroups: ["group", "dataLabelsGroup"],
            negStacks: !0,
            init: function () {
                p.prototype.init.apply(this, arguments);
                var a = this, c = a.chart;
                c.hasRendered && F(c.series, function (c) {
                    c.type === a.type && (c.isDirty = !0)
                })
            },
            getColumnMetrics: function () {
                var a = this, c = a.options, k = a.xAxis,
                    b = a.yAxis, g = k.reversed, n, t = {}, f = 0;
                !1 === c.grouping ? f = 1 : F(a.chart.series, function (c) {
                    var e = c.options, g = c.yAxis, q;
                    c.type !== a.type || !c.visible && a.chart.options.chart.ignoreHiddenSeries || b.len !== g.len || b.pos !== g.pos || (e.stacking ? (n = c.stackKey, void 0 === t[n] && (t[n] = f++), q = t[n]) : !1 !== e.grouping && (q = f++), c.columnIndex = q)
                });
                var l = Math.min(Math.abs(k.transA) * (k.ordinalSlope || c.pointRange || k.closestPointRange || k.tickInterval || 1), k.len),
                    m = l * c.groupPadding, q = (l - 2 * m) / (f || 1),
                    c = Math.min(c.maxPointWidth || k.len, w(c.pointWidth,
                        q * (1 - 2 * c.pointPadding)));
                a.columnMetrics = {
                    width: c,
                    offset: (q - c) / 2 + (m + ((a.columnIndex || 0) + (g ? 1 : 0)) * q - l / 2) * (g ? -1 : 1)
                };
                return a.columnMetrics
            },
            crispCol: function (a, c, k, b) {
                var e = this.chart, n = this.borderWidth, t = -(n % 2 ? .5 : 0), n = n % 2 ? .5 : 1;
                e.inverted && e.renderer.isVML && (n += 1);
                this.options.crisp && (k = Math.round(a + k) + t, a = Math.round(a) + t, k -= a);
                b = Math.round(c + b) + n;
                t = .5 >= Math.abs(c) && .5 < b;
                c = Math.round(c) + n;
                b -= c;
                t && b && (--c, b += 1);
                return {x: a, y: c, width: k, height: b}
            },
            translate: function () {
                var a = this, c = a.chart, k = a.options, b =
                        a.dense = 2 > a.closestPointRange * a.xAxis.transA, b = a.borderWidth = w(k.borderWidth, b ? 0 : 1),
                    g = a.yAxis, n = k.threshold, t = a.translatedThreshold = g.getThreshold(n),
                    f = w(k.minPointLength, 5), l = a.getColumnMetrics(), m = l.width,
                    q = a.barW = Math.max(m, 1 + 2 * b), A = a.pointXOffset = l.offset;
                c.inverted && (t -= .5);
                k.pointPadding && (q = Math.ceil(q));
                p.prototype.translate.apply(a);
                F(a.points, function (b) {
                    var e = w(b.yBottom, t), k = 999 + Math.abs(e), k = Math.min(Math.max(-k, b.plotY), g.len + k),
                        y = b.plotX + A, d = q, z = Math.min(k, e), l, h = Math.max(k, e) - z;
                    f &&
                    Math.abs(h) < f && (h = f, l = !g.reversed && !b.negative || g.reversed && b.negative, b.y === n && a.dataMax <= n && g.min < n && (l = !l), z = Math.abs(z - t) > f ? e - f : t - (l ? f : 0));
                    b.barX = y;
                    b.pointWidth = m;
                    b.tooltipPos = c.inverted ? [g.len + g.pos - c.plotLeft - k, a.xAxis.len - y - d / 2, h] : [y + d / 2, k + g.pos - c.plotTop, h];
                    b.shapeType = "rect";
                    b.shapeArgs = a.crispCol.apply(a, b.isNull ? [y, t, d, 0] : [y, z, d, h])
                })
            },
            getSymbol: a.noop,
            drawLegendSymbol: a.LegendSymbolMixin.drawRectangle,
            drawGraph: function () {
                this.group[this.dense ? "addClass" : "removeClass"]("highcharts-dense-data")
            },
            pointAttribs: function (a, c) {
                var e = this.options, b, g = this.pointAttrToOptions || {};
                b = g.stroke || "borderColor";
                var n = g["stroke-width"] || "borderWidth", t = a && a.color || this.color,
                    f = a && a[b] || e[b] || this.color || t, u = a && a[n] || e[n] || this[n] || 0, g = e.dashStyle;
                a && this.zones.length && (t = a.getZone(), t = a.options.color || t && t.color || this.color);
                c && (a = l(e.states[c], a.options.states && a.options.states[c] || {}), c = a.brightness, t = a.color || void 0 !== c && E(t).brighten(a.brightness).get() || t, f = a[b] || f, u = a[n] || u, g = a.dashStyle || g);
                b = {
                    fill: t,
                    stroke: f, "stroke-width": u
                };
                g && (b.dashstyle = g);
                return b
            },
            drawPoints: function () {
                var a = this, c = this.chart, k = a.options, b = c.renderer, g = k.animationLimit || 250, n;
                F(a.points, function (e) {
                    var f = e.graphic;
                    if (m(e.plotY) && null !== e.y) {
                        n = e.shapeArgs;
                        if (f) f[c.pointCount < g ? "animate" : "attr"](l(n)); else e.graphic = f = b[e.shapeType](n).add(e.group || a.group);
                        k.borderRadius && f.attr({r: k.borderRadius});
                        f.attr(a.pointAttribs(e, e.selected && "select")).shadow(k.shadow, null, k.stacking && !k.borderRadius);
                        f.addClass(e.getClassName(),
                            !0)
                    } else f && (e.graphic = f.destroy())
                })
            },
            animate: function (a) {
                var c = this, e = this.yAxis, b = c.options, g = this.chart.inverted, n = {},
                    l = g ? "translateX" : "translateY", f;
                B && (a ? (n.scaleY = .001, a = Math.min(e.pos + e.len, Math.max(e.pos, e.toPixels(b.threshold))), g ? n.translateX = a - e.len : n.translateY = a, c.group.attr(n)) : (f = c.group.attr(l), c.group.animate({scaleY: 1}, r(C(c.options.animation), {
                    step: function (a, b) {
                        n[l] = f + b.pos * (e.pos - f);
                        c.group.attr(n)
                    }
                })), c.animate = null))
            },
            remove: function () {
                var a = this, c = a.chart;
                c.hasRendered && F(c.series,
                    function (c) {
                        c.type === a.type && (c.isDirty = !0)
                    });
                p.prototype.remove.apply(a, arguments)
            }
        })
    })(L);
    (function (a) {
        a = a.seriesType;
        a("bar", "column", null, {inverted: !0})
    })(L);
    (function (a) {
        var C = a.Series;
        a = a.seriesType;
        a("scatter", "line", {
                lineWidth: 0,
                findNearestPointBy: "xy",
                marker: {enabled: !0},
                tooltip: {
                    headerFormat: '\x3cspan style\x3d"color:{point.color}"\x3e\u25cf\x3c/span\x3e \x3cspan style\x3d"font-size: 0.85em"\x3e {series.name}\x3c/span\x3e\x3cbr/\x3e',
                    pointFormat: "x: \x3cb\x3e{point.x}\x3c/b\x3e\x3cbr/\x3ey: \x3cb\x3e{point.y}\x3c/b\x3e\x3cbr/\x3e"
                }
            },
            {
                sorted: !1,
                requireSorting: !1,
                noSharedTooltip: !0,
                trackerGroups: ["group", "markerGroup", "dataLabelsGroup"],
                takeOrdinalPosition: !1,
                drawGraph: function () {
                    this.options.lineWidth && C.prototype.drawGraph.call(this)
                }
            })
    })(L);
    (function (a) {
        var C = a.deg2rad, E = a.isNumber, F = a.pick, r = a.relativeLength;
        a.CenteredSeriesMixin = {
            getCenter: function () {
                var a = this.options, l = this.chart, w = 2 * (a.slicedOffset || 0), p = l.plotWidth - 2 * w,
                    l = l.plotHeight - 2 * w, v = a.center,
                    v = [F(v[0], "50%"), F(v[1], "50%"), a.size || "100%", a.innerSize || 0], B = Math.min(p,
                    l), e, c;
                for (e = 0; 4 > e; ++e) c = v[e], a = 2 > e || 2 === e && /%$/.test(c), v[e] = r(c, [p, l, B, v[2]][e]) + (a ? w : 0);
                v[3] > v[2] && (v[3] = v[2]);
                return v
            }, getStartAndEndRadians: function (a, l) {
                a = E(a) ? a : 0;
                l = E(l) && l > a && 360 > l - a ? l : a + 360;
                return {start: C * (a + -90), end: C * (l + -90)}
            }
        }
    })(L);
    (function (a) {
        var C = a.addEvent, E = a.CenteredSeriesMixin, F = a.defined, r = a.each, m = a.extend,
            l = E.getStartAndEndRadians, w = a.inArray, p = a.noop, v = a.pick, B = a.Point, e = a.Series,
            c = a.seriesType, k = a.setAnimation;
        c("pie", "line", {
            center: [null, null],
            clip: !1,
            colorByPoint: !0,
            dataLabels: {
                distance: 30,
                enabled: !0, formatter: function () {
                    return this.point.isNull ? void 0 : this.point.name
                }, x: 0
            },
            ignoreHiddenPoint: !0,
            legendType: "point",
            marker: null,
            size: null,
            showInLegend: !1,
            slicedOffset: 10,
            stickyTracking: !1,
            tooltip: {followPointer: !0},
            borderColor: "#ffffff",
            borderWidth: 1,
            states: {hover: {brightness: .1}}
        }, {
            isCartesian: !1,
            requireSorting: !1,
            directTouch: !0,
            noSharedTooltip: !0,
            trackerGroups: ["group", "dataLabelsGroup"],
            axisTypes: [],
            pointAttribs: a.seriesTypes.column.prototype.pointAttribs,
            animate: function (a) {
                var b = this,
                    c = b.points, e = b.startAngleRad;
                a || (r(c, function (a) {
                    var c = a.graphic, f = a.shapeArgs;
                    c && (c.attr({r: a.startR || b.center[3] / 2, start: e, end: e}), c.animate({
                        r: f.r,
                        start: f.start,
                        end: f.end
                    }, b.options.animation))
                }), b.animate = null)
            },
            updateTotals: function () {
                var a, c = 0, e = this.points, k = e.length, f, l = this.options.ignoreHiddenPoint;
                for (a = 0; a < k; a++) f = e[a], c += l && !f.visible ? 0 : f.isNull ? 0 : f.y;
                this.total = c;
                for (a = 0; a < k; a++) f = e[a], f.percentage = 0 < c && (f.visible || !l) ? f.y / c * 100 : 0, f.total = c
            },
            generatePoints: function () {
                e.prototype.generatePoints.call(this);
                this.updateTotals()
            },
            translate: function (a) {
                this.generatePoints();
                var b = 0, c = this.options, e = c.slicedOffset, f = e + (c.borderWidth || 0), k, m, q,
                    A = l(c.startAngle, c.endAngle), y = this.startAngleRad = A.start,
                    A = (this.endAngleRad = A.end) - y, H = this.points, p, r = c.dataLabels.distance,
                    c = c.ignoreHiddenPoint, d, z = H.length, G;
                a || (this.center = a = this.getCenter());
                this.getX = function (d, b, c) {
                    q = Math.asin(Math.min((d - a[1]) / (a[2] / 2 + c.labelDistance), 1));
                    return a[0] + (b ? -1 : 1) * Math.cos(q) * (a[2] / 2 + c.labelDistance)
                };
                for (d = 0; d < z; d++) {
                    G = H[d];
                    G.labelDistance = v(G.options.dataLabels && G.options.dataLabels.distance, r);
                    this.maxLabelDistance = Math.max(this.maxLabelDistance || 0, G.labelDistance);
                    k = y + b * A;
                    if (!c || G.visible) b += G.percentage / 100;
                    m = y + b * A;
                    G.shapeType = "arc";
                    G.shapeArgs = {
                        x: a[0],
                        y: a[1],
                        r: a[2] / 2,
                        innerR: a[3] / 2,
                        start: Math.round(1E3 * k) / 1E3,
                        end: Math.round(1E3 * m) / 1E3
                    };
                    q = (m + k) / 2;
                    q > 1.5 * Math.PI ? q -= 2 * Math.PI : q < -Math.PI / 2 && (q += 2 * Math.PI);
                    G.slicedTranslation = {
                        translateX: Math.round(Math.cos(q) * e),
                        translateY: Math.round(Math.sin(q) * e)
                    };
                    m = Math.cos(q) * a[2] /
                        2;
                    p = Math.sin(q) * a[2] / 2;
                    G.tooltipPos = [a[0] + .7 * m, a[1] + .7 * p];
                    G.half = q < -Math.PI / 2 || q > Math.PI / 2 ? 1 : 0;
                    G.angle = q;
                    k = Math.min(f, G.labelDistance / 5);
                    G.labelPos = [a[0] + m + Math.cos(q) * G.labelDistance, a[1] + p + Math.sin(q) * G.labelDistance, a[0] + m + Math.cos(q) * k, a[1] + p + Math.sin(q) * k, a[0] + m, a[1] + p, 0 > G.labelDistance ? "center" : G.half ? "right" : "left", q]
                }
            },
            drawGraph: null,
            drawPoints: function () {
                var a = this, c = a.chart.renderer, e, k, f, l, D = a.options.shadow;
                D && !a.shadowGroup && (a.shadowGroup = c.g("shadow").add(a.group));
                r(a.points, function (b) {
                    k =
                        b.graphic;
                    if (b.isNull) k && (b.graphic = k.destroy()); else {
                        l = b.shapeArgs;
                        e = b.getTranslate();
                        var g = b.shadowGroup;
                        D && !g && (g = b.shadowGroup = c.g("shadow").add(a.shadowGroup));
                        g && g.attr(e);
                        f = a.pointAttribs(b, b.selected && "select");
                        k ? k.setRadialReference(a.center).attr(f).animate(m(l, e)) : (b.graphic = k = c[b.shapeType](l).setRadialReference(a.center).attr(e).add(a.group), b.visible || k.attr({visibility: "hidden"}), k.attr(f).attr({"stroke-linejoin": "round"}).shadow(D, g));
                        k.addClass(b.getClassName())
                    }
                })
            },
            searchPoint: p,
            sortByAngle: function (a, c) {
                a.sort(function (a, b) {
                    return void 0 !== a.angle && (b.angle - a.angle) * c
                })
            },
            drawLegendSymbol: a.LegendSymbolMixin.drawRectangle,
            getCenter: E.getCenter,
            getSymbol: p
        }, {
            init: function () {
                B.prototype.init.apply(this, arguments);
                var a = this, c;
                a.name = v(a.name, "Slice");
                c = function (b) {
                    a.slice("select" === b.type)
                };
                C(a, "select", c);
                C(a, "unselect", c);
                return a
            }, isValid: function () {
                return a.isNumber(this.y, !0) && 0 <= this.y
            }, setVisible: function (a, c) {
                var b = this, e = b.series, f = e.chart, g = e.options.ignoreHiddenPoint;
                c = v(c, g);
                a !== b.visible && (b.visible = b.options.visible = a = void 0 === a ? !b.visible : a, e.options.data[w(b, e.data)] = b.options, r(["graphic", "dataLabel", "connector", "shadowGroup"], function (c) {
                    if (b[c]) b[c][a ? "show" : "hide"](!0)
                }), b.legendItem && f.legend.colorizeItem(b, a), a || "hover" !== b.state || b.setState(""), g && (e.isDirty = !0), c && f.redraw())
            }, slice: function (a, c, e) {
                var b = this.series;
                k(e, b.chart);
                v(c, !0);
                this.sliced = this.options.sliced = F(a) ? a : !this.sliced;
                b.options.data[w(this, b.data)] = this.options;
                this.graphic.animate(this.getTranslate());
                this.shadowGroup && this.shadowGroup.animate(this.getTranslate())
            }, getTranslate: function () {
                return this.sliced ? this.slicedTranslation : {translateX: 0, translateY: 0}
            }, haloPath: function (a) {
                var b = this.shapeArgs;
                return this.sliced || !this.visible ? [] : this.series.chart.renderer.symbols.arc(b.x, b.y, b.r + a, b.r + a, {
                    innerR: this.shapeArgs.r - 1,
                    start: b.start,
                    end: b.end
                })
            }
        })
    })(L);
    (function (a) {
        var C = a.addEvent, E = a.arrayMax, F = a.defined, r = a.each, m = a.extend, l = a.format, w = a.map,
            p = a.merge, v = a.noop, B = a.pick, e = a.relativeLength, c =
                a.Series, k = a.seriesTypes, b = a.stableSort;
        a.distribute = function (a, c) {
            function e(a, b) {
                return a.target - b.target
            }

            var f, g = !0, k = a, q = [], n;
            n = 0;
            for (f = a.length; f--;) n += a[f].size;
            if (n > c) {
                b(a, function (a, b) {
                    return (b.rank || 0) - (a.rank || 0)
                });
                for (n = f = 0; n <= c;) n += a[f].size, f++;
                q = a.splice(f - 1, a.length)
            }
            b(a, e);
            for (a = w(a, function (a) {
                return {size: a.size, targets: [a.target], align: B(a.align, .5)}
            }); g;) {
                for (f = a.length; f--;) g = a[f], n = (Math.min.apply(0, g.targets) + Math.max.apply(0, g.targets)) / 2, g.pos = Math.min(Math.max(0, n - g.size *
                    g.align), c - g.size);
                f = a.length;
                for (g = !1; f--;) 0 < f && a[f - 1].pos + a[f - 1].size > a[f].pos && (a[f - 1].size += a[f].size, a[f - 1].targets = a[f - 1].targets.concat(a[f].targets), a[f - 1].align = .5, a[f - 1].pos + a[f - 1].size > c && (a[f - 1].pos = c - a[f - 1].size), a.splice(f, 1), g = !0)
            }
            f = 0;
            r(a, function (a) {
                var b = 0;
                r(a.targets, function () {
                    k[f].pos = a.pos + b;
                    b += k[f].size;
                    f++
                })
            });
            k.push.apply(k, q);
            b(k, e)
        };
        c.prototype.drawDataLabels = function () {
            function b(a, d) {
                var b = d.filter;
                return b ? (d = b.operator, a = a[b.property], b = b.value, "\x3e" === d && a > b || "\x3c" ===
                d && a < b || "\x3e\x3d" === d && a >= b || "\x3c\x3d" === d && a <= b || "\x3d\x3d" === d && a == b || "\x3d\x3d\x3d" === d && a === b ? !0 : !1) : !0
            }

            var c = this, e = c.chart, f = c.options, k = f.dataLabels, m = c.points, q, A, y = c.hasRendered || 0, H,
                v, w = B(k.defer, !!f.animation), d = e.renderer;
            if (k.enabled || c._hasPointLabels) c.dlProcessOptions && c.dlProcessOptions(k), v = c.plotGroup("dataLabelsGroup", "data-labels", w && !y ? "hidden" : "visible", k.zIndex || 6), w && (v.attr({opacity: +y}), y || C(c, "afterAnimate", function () {
                c.visible && v.show(!0);
                v[f.animation ? "animate" : "attr"]({opacity: 1},
                    {duration: 200})
            })), A = k, r(m, function (g) {
                var n, h = g.dataLabel, z, y, t = g.connector, m = !h, u;
                q = g.dlOptions || g.options && g.options.dataLabels;
                (n = B(q && q.enabled, A.enabled) && !g.isNull) && (n = !0 === b(g, q || k));
                n && (k = p(A, q), z = g.getLabelConfig(), u = k[g.formatPrefix + "Format"] || k.format, H = F(u) ? l(u, z, e.time) : (k[g.formatPrefix + "Formatter"] || k.formatter).call(z, k), u = k.style, z = k.rotation, u.color = B(k.color, u.color, c.color, "#000000"), "contrast" === u.color && (g.contrastColor = d.getContrast(g.color || c.color), u.color = k.inside || 0 > B(g.labelDistance,
                    k.distance) || f.stacking ? g.contrastColor : "#000000"), f.cursor && (u.cursor = f.cursor), y = {
                    fill: k.backgroundColor,
                    stroke: k.borderColor,
                    "stroke-width": k.borderWidth,
                    r: k.borderRadius || 0,
                    rotation: z,
                    padding: k.padding,
                    zIndex: 1
                }, a.objectEach(y, function (a, d) {
                    void 0 === a && delete y[d]
                }));
                !h || n && F(H) ? n && F(H) && (h ? y.text = H : (h = g.dataLabel = z ? d.text(H, 0, -9999).addClass("highcharts-data-label") : d.label(H, 0, -9999, k.shape, null, null, k.useHTML, null, "data-label"), h.addClass(" highcharts-data-label-color-" + g.colorIndex + " " + (k.className ||
                    "") + (k.useHTML ? "highcharts-tracker" : ""))), h.attr(y), h.css(u).shadow(k.shadow), h.added || h.add(v), c.alignDataLabel(g, h, k, null, m)) : (g.dataLabel = h = h.destroy(), t && (g.connector = t.destroy()))
            });
            a.fireEvent(this, "afterDrawDataLabels")
        };
        c.prototype.alignDataLabel = function (a, b, c, f, e) {
            var g = this.chart, q = g.inverted, k = B(a.dlBox && a.dlBox.centerX, a.plotX, -9999),
                n = B(a.plotY, -9999), l = b.getBBox(), t, u = c.rotation, d = c.align,
                z = this.visible && (a.series.forceDL || g.isInsidePlot(k, Math.round(n), q) || f && g.isInsidePlot(k, q ? f.x +
                    1 : f.y + f.height - 1, q)), G = "justify" === B(c.overflow, "justify");
            if (z && (t = c.style.fontSize, t = g.renderer.fontMetrics(t, b).b, f = m({
                    x: q ? this.yAxis.len - n : k,
                    y: Math.round(q ? this.xAxis.len - k : n),
                    width: 0,
                    height: 0
                }, f), m(c, {
                    width: l.width,
                    height: l.height
                }), u ? (G = !1, k = g.renderer.rotCorr(t, u), k = {
                    x: f.x + c.x + f.width / 2 + k.x,
                    y: f.y + c.y + {top: 0, middle: .5, bottom: 1}[c.verticalAlign] * f.height
                }, b[e ? "attr" : "animate"](k).attr({align: d}), n = (u + 720) % 360, n = 180 < n && 360 > n, "left" === d ? k.y -= n ? l.height : 0 : "center" === d ? (k.x -= l.width / 2, k.y -= l.height /
                    2) : "right" === d && (k.x -= l.width, k.y -= n ? 0 : l.height)) : (b.align(c, null, f), k = b.alignAttr), G ? a.isLabelJustified = this.justifyDataLabel(b, c, k, l, f, e) : B(c.crop, !0) && (z = g.isInsidePlot(k.x, k.y) && g.isInsidePlot(k.x + l.width, k.y + l.height)), c.shape && !u)) b[e ? "attr" : "animate"]({
                anchorX: q ? g.plotWidth - a.plotY : a.plotX,
                anchorY: q ? g.plotHeight - a.plotX : a.plotY
            });
            z || (b.attr({y: -9999}), b.placed = !1)
        };
        c.prototype.justifyDataLabel = function (a, b, c, f, e, k) {
            var g = this.chart, n = b.align, l = b.verticalAlign, m, t, u = a.box ? 0 : a.padding || 0;
            m = c.x +
                u;
            0 > m && ("right" === n ? b.align = "left" : b.x = -m, t = !0);
            m = c.x + f.width - u;
            m > g.plotWidth && ("left" === n ? b.align = "right" : b.x = g.plotWidth - m, t = !0);
            m = c.y + u;
            0 > m && ("bottom" === l ? b.verticalAlign = "top" : b.y = -m, t = !0);
            m = c.y + f.height - u;
            m > g.plotHeight && ("top" === l ? b.verticalAlign = "bottom" : b.y = g.plotHeight - m, t = !0);
            t && (a.placed = !k, a.align(b, null, e));
            return t
        };
        k.pie && (k.pie.prototype.drawDataLabels = function () {
            var b = this, e = b.data, k, f = b.chart, l = b.options.dataLabels, m = B(l.connectorPadding, 10),
                q = B(l.connectorWidth, 1), A = f.plotWidth,
                y = f.plotHeight, p, v = b.center, w = v[2] / 2, d = v[1], z, G, h, x, N = [[], []], O, C, M, Q,
                K = [0, 0, 0, 0];
            b.visible && (l.enabled || b._hasPointLabels) && (r(e, function (a) {
                a.dataLabel && a.visible && a.dataLabel.shortened && (a.dataLabel.attr({width: "auto"}).css({
                    width: "auto",
                    textOverflow: "clip"
                }), a.dataLabel.shortened = !1)
            }), c.prototype.drawDataLabels.apply(b), r(e, function (a) {
                a.dataLabel && a.visible && (N[a.half].push(a), a.dataLabel._pos = null)
            }), r(N, function (c, e) {
                var g, q, n = c.length, t = [], u;
                if (n) for (b.sortByAngle(c, e - .5), 0 < b.maxLabelDistance &&
                (g = Math.max(0, d - w - b.maxLabelDistance), q = Math.min(d + w + b.maxLabelDistance, f.plotHeight), r(c, function (a) {
                    0 < a.labelDistance && a.dataLabel && (a.top = Math.max(0, d - w - a.labelDistance), a.bottom = Math.min(d + w + a.labelDistance, f.plotHeight), u = a.dataLabel.getBBox().height || 21, a.positionsIndex = t.push({
                        target: a.labelPos[1] - a.top + u / 2,
                        size: u,
                        rank: a.y
                    }) - 1)
                }), a.distribute(t, q + u - g)), Q = 0; Q < n; Q++) k = c[Q], q = k.positionsIndex, h = k.labelPos, z = k.dataLabel, M = !1 === k.visible ? "hidden" : "inherit", C = g = h[1], t && F(t[q]) && (void 0 === t[q].pos ?
                    M = "hidden" : (x = t[q].size, C = k.top + t[q].pos)), delete k.positionIndex, O = l.justify ? v[0] + (e ? -1 : 1) * (w + k.labelDistance) : b.getX(C < k.top + 2 || C > k.bottom - 2 ? g : C, e, k), z._attr = {
                    visibility: M,
                    align: h[6]
                }, z._pos = {
                    x: O + l.x + ({left: m, right: -m}[h[6]] || 0),
                    y: C + l.y - 10
                }, h.x = O, h.y = C, B(l.crop, !0) && (G = z.getBBox().width, g = null, O - G < m ? (g = Math.round(G - O + m), K[3] = Math.max(g, K[3])) : O + G > A - m && (g = Math.round(O + G - A + m), K[1] = Math.max(g, K[1])), 0 > C - x / 2 ? K[0] = Math.max(Math.round(-C + x / 2), K[0]) : C + x / 2 > y && (K[2] = Math.max(Math.round(C + x / 2 - y), K[2])), z.sideOverflow =
                    g)
            }), 0 === E(K) || this.verifyDataLabelOverflow(K)) && (this.placeDataLabels(), q && r(this.points, function (a) {
                var d;
                p = a.connector;
                if ((z = a.dataLabel) && z._pos && a.visible && 0 < a.labelDistance) {
                    M = z._attr.visibility;
                    if (d = !p) a.connector = p = f.renderer.path().addClass("highcharts-data-label-connector  highcharts-color-" + a.colorIndex).add(b.dataLabelsGroup), p.attr({
                        "stroke-width": q,
                        stroke: l.connectorColor || a.color || "#666666"
                    });
                    p[d ? "attr" : "animate"]({d: b.connectorPath(a.labelPos)});
                    p.attr("visibility", M)
                } else p && (a.connector =
                    p.destroy())
            }))
        }, k.pie.prototype.connectorPath = function (a) {
            var b = a.x, c = a.y;
            return B(this.options.dataLabels.softConnector, !0) ? ["M", b + ("left" === a[6] ? 5 : -5), c, "C", b, c, 2 * a[2] - a[4], 2 * a[3] - a[5], a[2], a[3], "L", a[4], a[5]] : ["M", b + ("left" === a[6] ? 5 : -5), c, "L", a[2], a[3], "L", a[4], a[5]]
        }, k.pie.prototype.placeDataLabels = function () {
            r(this.points, function (a) {
                var b = a.dataLabel;
                b && a.visible && ((a = b._pos) ? (b.sideOverflow && (b._attr.width = b.getBBox().width - b.sideOverflow, b.css({
                    width: b._attr.width + "px",
                    textOverflow: "ellipsis"
                }),
                    b.shortened = !0), b.attr(b._attr), b[b.moved ? "animate" : "attr"](a), b.moved = !0) : b && b.attr({y: -9999}))
            }, this)
        }, k.pie.prototype.alignDataLabel = v, k.pie.prototype.verifyDataLabelOverflow = function (a) {
            var b = this.center, c = this.options, f = c.center, g = c.minSize || 80, k, q = null !== c.size;
            q || (null !== f[0] ? k = Math.max(b[2] - Math.max(a[1], a[3]), g) : (k = Math.max(b[2] - a[1] - a[3], g), b[0] += (a[3] - a[1]) / 2), null !== f[1] ? k = Math.max(Math.min(k, b[2] - Math.max(a[0], a[2])), g) : (k = Math.max(Math.min(k, b[2] - a[0] - a[2]), g), b[1] += (a[0] - a[2]) / 2), k <
            b[2] ? (b[2] = k, b[3] = Math.min(e(c.innerSize || 0, k), k), this.translate(b), this.drawDataLabels && this.drawDataLabels()) : q = !0);
            return q
        });
        k.column && (k.column.prototype.alignDataLabel = function (a, b, e, f, k) {
            var g = this.chart.inverted, q = a.series, l = a.dlBox || a.shapeArgs,
                n = B(a.below, a.plotY > B(this.translatedThreshold, q.yAxis.len)),
                m = B(e.inside, !!this.options.stacking);
            l && (f = p(l), 0 > f.y && (f.height += f.y, f.y = 0), l = f.y + f.height - q.yAxis.len, 0 < l && (f.height -= l), g && (f = {
                x: q.yAxis.len - f.y - f.height, y: q.xAxis.len - f.x - f.width, width: f.height,
                height: f.width
            }), m || (g ? (f.x += n ? 0 : f.width, f.width = 0) : (f.y += n ? f.height : 0, f.height = 0)));
            e.align = B(e.align, !g || m ? "center" : n ? "right" : "left");
            e.verticalAlign = B(e.verticalAlign, g || m ? "middle" : n ? "top" : "bottom");
            c.prototype.alignDataLabel.call(this, a, b, e, f, k);
            a.isLabelJustified && a.contrastColor && a.dataLabel.css({color: a.contrastColor})
        })
    })(L);
    (function (a) {
        var C = a.Chart, E = a.each, F = a.objectEach, r = a.pick;
        a = a.addEvent;
        a(C.prototype, "render", function () {
            var a = [];
            E(this.labelCollectors || [], function (l) {
                a = a.concat(l())
            });
            E(this.yAxis || [], function (l) {
                l.options.stackLabels && !l.options.stackLabels.allowOverlap && F(l.stacks, function (l) {
                    F(l, function (l) {
                        a.push(l.label)
                    })
                })
            });
            E(this.series || [], function (l) {
                var m = l.options.dataLabels, p = l.dataLabelCollections || ["dataLabel"];
                (m.enabled || l._hasPointLabels) && !m.allowOverlap && l.visible && E(p, function (m) {
                    E(l.points, function (l) {
                        l[m] && (l[m].labelrank = r(l.labelrank, l.shapeArgs && l.shapeArgs.height), a.push(l[m]))
                    })
                })
            });
            this.hideOverlappingLabels(a)
        });
        C.prototype.hideOverlappingLabels =
            function (a) {
                var l = a.length, m, p, v, r, e, c, k, b, g, n = function (a, b, c, e, g, k, l, n) {
                    return !(g > a + c || g + l < a || k > b + e || k + n < b)
                };
                for (p = 0; p < l; p++) if (m = a[p]) m.oldOpacity = m.opacity, m.newOpacity = 1, m.width || (v = m.getBBox(), m.width = v.width, m.height = v.height);
                a.sort(function (a, b) {
                    return (b.labelrank || 0) - (a.labelrank || 0)
                });
                for (p = 0; p < l; p++) for (v = a[p], m = p + 1; m < l; ++m) if (r = a[m], v && r && v !== r && v.placed && r.placed && 0 !== v.newOpacity && 0 !== r.newOpacity && (e = v.alignAttr, c = r.alignAttr, k = v.parentGroup, b = r.parentGroup, g = 2 * (v.box ? 0 : v.padding ||
                        0), e = n(e.x + k.translateX, e.y + k.translateY, v.width - g, v.height - g, c.x + b.translateX, c.y + b.translateY, r.width - g, r.height - g))) (v.labelrank < r.labelrank ? v : r).newOpacity = 0;
                E(a, function (a) {
                    var b, c;
                    a && (c = a.newOpacity, a.oldOpacity !== c && a.placed && (c ? a.show(!0) : b = function () {
                        a.hide()
                    }, a.alignAttr.opacity = c, a[a.isOld ? "animate" : "attr"](a.alignAttr, null, b)), a.isOld = !0)
                })
            }
    })(L);
    (function (a) {
        var C = a.addEvent, E = a.Chart, F = a.createElement, r = a.css, m = a.defaultOptions, l = a.defaultPlotOptions,
            w = a.each, p = a.extend, v = a.fireEvent,
            B = a.hasTouch, e = a.inArray, c = a.isObject, k = a.Legend, b = a.merge, g = a.pick, n = a.Point,
            t = a.Series, f = a.seriesTypes, u = a.svg, D;
        D = a.TrackerMixin = {
            drawTrackerPoint: function () {
                var a = this, b = a.chart.pointer, c = function (a) {
                    var c = b.getPointFromEvent(a);
                    void 0 !== c && (b.isDirectTouch = !0, c.onMouseOver(a))
                };
                w(a.points, function (a) {
                    a.graphic && (a.graphic.element.point = a);
                    a.dataLabel && (a.dataLabel.div ? a.dataLabel.div.point = a : a.dataLabel.element.point = a)
                });
                a._hasTracking || (w(a.trackerGroups, function (f) {
                    if (a[f]) {
                        a[f].addClass("highcharts-tracker").on("mouseover",
                            c).on("mouseout", function (a) {
                            b.onTrackerMouseOut(a)
                        });
                        if (B) a[f].on("touchstart", c);
                        a.options.cursor && a[f].css(r).css({cursor: a.options.cursor})
                    }
                }), a._hasTracking = !0);
                v(this, "afterDrawTracker")
            }, drawTrackerGraph: function () {
                var a = this, b = a.options, c = b.trackByArea, f = [].concat(c ? a.areaPath : a.graphPath),
                    e = f.length, g = a.chart, d = g.pointer, k = g.renderer, l = g.options.tooltip.snap, h = a.tracker,
                    n, m = function () {
                        if (g.hoverSeries !== a) a.onMouseOver()
                    }, t = "rgba(192,192,192," + (u ? .0001 : .002) + ")";
                if (e && !c) for (n = e + 1; n--;) "M" ===
                f[n] && f.splice(n + 1, 0, f[n + 1] - l, f[n + 2], "L"), (n && "M" === f[n] || n === e) && f.splice(n, 0, "L", f[n - 2] + l, f[n - 1]);
                h ? h.attr({d: f}) : a.graph && (a.tracker = k.path(f).attr({
                    "stroke-linejoin": "round",
                    visibility: a.visible ? "visible" : "hidden",
                    stroke: t,
                    fill: c ? t : "none",
                    "stroke-width": a.graph.strokeWidth() + (c ? 0 : 2 * l),
                    zIndex: 2
                }).add(a.group), w([a.tracker, a.markerGroup], function (a) {
                    a.addClass("highcharts-tracker").on("mouseover", m).on("mouseout", function (a) {
                        d.onTrackerMouseOut(a)
                    });
                    b.cursor && a.css({cursor: b.cursor});
                    if (B) a.on("touchstart",
                        m)
                }));
                v(this, "afterDrawTracker")
            }
        };
        f.column && (f.column.prototype.drawTracker = D.drawTrackerPoint);
        f.pie && (f.pie.prototype.drawTracker = D.drawTrackerPoint);
        f.scatter && (f.scatter.prototype.drawTracker = D.drawTrackerPoint);
        p(k.prototype, {
            setItemEvents: function (a, c, f) {
                var e = this, g = e.chart.renderer.boxWrapper,
                    k = "highcharts-legend-" + (a instanceof n ? "point" : "series") + "-active";
                (f ? c : a.legendGroup).on("mouseover", function () {
                    a.setState("hover");
                    g.addClass(k);
                    c.css(e.options.itemHoverStyle)
                }).on("mouseout", function () {
                    c.css(b(a.visible ?
                        e.itemStyle : e.itemHiddenStyle));
                    g.removeClass(k);
                    a.setState()
                }).on("click", function (b) {
                    var d = function () {
                        a.setVisible && a.setVisible()
                    };
                    g.removeClass(k);
                    b = {browserEvent: b};
                    a.firePointEvent ? a.firePointEvent("legendItemClick", b, d) : v(a, "legendItemClick", b, d)
                })
            }, createCheckboxForItem: function (a) {
                a.checkbox = F("input", {
                    type: "checkbox",
                    checked: a.selected,
                    defaultChecked: a.selected
                }, this.options.itemCheckboxStyle, this.chart.container);
                C(a.checkbox, "click", function (b) {
                    v(a.series || a, "checkboxClick", {
                        checked: b.target.checked,
                        item: a
                    }, function () {
                        a.select()
                    })
                })
            }
        });
        m.legend.itemStyle.cursor = "pointer";
        p(E.prototype, {
            showResetZoom: function () {
                function a() {
                    b.zoomOut()
                }

                var b = this, c = m.lang, f = b.options.chart.resetZoomButton, e = f.theme, g = e.states,
                    d = "chart" === f.relativeTo ? null : "plotBox";
                v(this, "beforeShowResetZoom", null, function () {
                    b.resetZoomButton = b.renderer.button(c.resetZoom, null, null, a, e, g && g.hover).attr({
                        align: f.position.align,
                        title: c.resetZoomTitle
                    }).addClass("highcharts-reset-zoom").add().align(f.position, !1, d)
                })
            }, zoomOut: function () {
                var a =
                    this;
                v(a, "selection", {resetSelection: !0}, function () {
                    a.zoom()
                })
            }, zoom: function (a) {
                var b, f = this.pointer, e = !1, k;
                !a || a.resetSelection ? (w(this.axes, function (a) {
                    b = a.zoom()
                }), f.initiated = !1) : w(a.xAxis.concat(a.yAxis), function (a) {
                    var d = a.axis;
                    f[d.isXAxis ? "zoomX" : "zoomY"] && (b = d.zoom(a.min, a.max), d.displayBtn && (e = !0))
                });
                k = this.resetZoomButton;
                e && !k ? this.showResetZoom() : !e && c(k) && (this.resetZoomButton = k.destroy());
                b && this.redraw(g(this.options.chart.animation, a && a.animation, 100 > this.pointCount))
            }, pan: function (a,
                              b) {
                var c = this, f = c.hoverPoints, e;
                f && w(f, function (a) {
                    a.setState()
                });
                w("xy" === b ? [1, 0] : [1], function (b) {
                    b = c[b ? "xAxis" : "yAxis"][0];
                    var d = b.horiz, f = a[d ? "chartX" : "chartY"], d = d ? "mouseDownX" : "mouseDownY", g = c[d],
                        h = (b.pointRange || 0) / 2, k = b.getExtremes(), q = b.toValue(g - f, !0) + h,
                        l = b.toValue(g + b.len - f, !0) - h, n = l < q, g = n ? l : q, q = n ? q : l,
                        l = Math.min(k.dataMin, h ? k.min : b.toValue(b.toPixels(k.min) - b.minPixelPadding)),
                        h = Math.max(k.dataMax, h ? k.max : b.toValue(b.toPixels(k.max) + b.minPixelPadding)),
                        n = l - g;
                    0 < n && (q += n, g = l);
                    n = q - h;
                    0 < n && (q =
                        h, g -= n);
                    b.series.length && g !== k.min && q !== k.max && (b.setExtremes(g, q, !1, !1, {trigger: "pan"}), e = !0);
                    c[d] = f
                });
                e && c.redraw(!1);
                r(c.container, {cursor: "move"})
            }
        });
        p(n.prototype, {
            select: function (a, b) {
                var c = this, f = c.series, k = f.chart;
                a = g(a, !c.selected);
                c.firePointEvent(a ? "select" : "unselect", {accumulate: b}, function () {
                    c.selected = c.options.selected = a;
                    f.options.data[e(c, f.data)] = c.options;
                    c.setState(a && "select");
                    b || w(k.getSelectedPoints(), function (a) {
                        a.selected && a !== c && (a.selected = a.options.selected = !1, f.options.data[e(a,
                            f.data)] = a.options, a.setState(""), a.firePointEvent("unselect"))
                    })
                })
            }, onMouseOver: function (a) {
                var b = this.series.chart, c = b.pointer;
                a = a ? c.normalize(a) : c.getChartCoordinatesFromPoint(this, b.inverted);
                c.runPointActions(a, this)
            }, onMouseOut: function () {
                var a = this.series.chart;
                this.firePointEvent("mouseOut");
                w(a.hoverPoints || [], function (a) {
                    a.setState()
                });
                a.hoverPoints = a.hoverPoint = null
            }, importEvents: function () {
                if (!this.hasImportedEvents) {
                    var c = this, f = b(c.series.options.point, c.options).events;
                    c.events = f;
                    a.objectEach(f,
                        function (a, b) {
                            C(c, b, a)
                        });
                    this.hasImportedEvents = !0
                }
            }, setState: function (a, b) {
                var c = Math.floor(this.plotX), f = this.plotY, e = this.series,
                    k = e.options.states[a || "normal"] || {}, d = l[e.type].marker && e.options.marker,
                    q = d && !1 === d.enabled, n = d && d.states && d.states[a || "normal"] || {}, h = !1 === n.enabled,
                    m = e.stateMarkerGraphic, A = this.marker || {}, t = e.chart, u = e.halo, D,
                    r = d && e.markerAttribs;
                a = a || "";
                if (!(a === this.state && !b || this.selected && "select" !== a || !1 === k.enabled || a && (h || q && !1 === n.enabled) || a && A.states && A.states[a] && !1 ===
                        A.states[a].enabled)) {
                    r && (D = e.markerAttribs(this, a));
                    if (this.graphic) this.state && this.graphic.removeClass("highcharts-point-" + this.state), a && this.graphic.addClass("highcharts-point-" + a), this.graphic.animate(e.pointAttribs(this, a), g(t.options.chart.animation, k.animation)), D && this.graphic.animate(D, g(t.options.chart.animation, n.animation, d.animation)), m && m.hide(); else {
                        if (a && n) {
                            d = A.symbol || e.symbol;
                            m && m.currentSymbol !== d && (m = m.destroy());
                            if (m) m[b ? "animate" : "attr"]({x: D.x, y: D.y}); else d && (e.stateMarkerGraphic =
                                m = t.renderer.symbol(d, D.x, D.y, D.width, D.height).add(e.markerGroup), m.currentSymbol = d);
                            m && m.attr(e.pointAttribs(this, a))
                        }
                        m && (m[a && t.isInsidePlot(c, f, t.inverted) ? "show" : "hide"](), m.element.point = this)
                    }
                    (c = k.halo) && c.size ? (u || (e.halo = u = t.renderer.path().add((this.graphic || m).parentGroup)), u.show()[b ? "animate" : "attr"]({d: this.haloPath(c.size)}), u.attr({"class": "highcharts-halo highcharts-color-" + g(this.colorIndex, e.colorIndex)}), u.point = this, u.attr(p({
                        fill: this.color || e.color, "fill-opacity": c.opacity,
                        zIndex: -1
                    }, c.attributes))) : u && u.point && u.point.haloPath && u.animate({d: u.point.haloPath(0)}, null, u.hide);
                    this.state = a;
                    v(this, "afterSetState")
                }
            }, haloPath: function (a) {
                return this.series.chart.renderer.symbols.circle(Math.floor(this.plotX) - a, this.plotY - a, 2 * a, 2 * a)
            }
        });
        p(t.prototype, {
            onMouseOver: function () {
                var a = this.chart, b = a.hoverSeries;
                if (b && b !== this) b.onMouseOut();
                this.options.events.mouseOver && v(this, "mouseOver");
                this.setState("hover");
                a.hoverSeries = this
            }, onMouseOut: function () {
                var a = this.options, b =
                    this.chart, c = b.tooltip, f = b.hoverPoint;
                b.hoverSeries = null;
                if (f) f.onMouseOut();
                this && a.events.mouseOut && v(this, "mouseOut");
                !c || this.stickyTracking || c.shared && !this.noSharedTooltip || c.hide();
                this.setState()
            }, setState: function (a) {
                var b = this, c = b.options, f = b.graph, e = c.states, k = c.lineWidth, c = 0;
                a = a || "";
                if (b.state !== a && (w([b.group, b.markerGroup, b.dataLabelsGroup], function (d) {
                        d && (b.state && d.removeClass("highcharts-series-" + b.state), a && d.addClass("highcharts-series-" + a))
                    }), b.state = a, !e[a] || !1 !== e[a].enabled) &&
                    (a && (k = e[a].lineWidth || k + (e[a].lineWidthPlus || 0)), f && !f.dashstyle)) for (k = {"stroke-width": k}, f.animate(k, g(e[a || "normal"] && e[a || "normal"].animation, b.chart.options.chart.animation)); b["zone-graph-" + c];) b["zone-graph-" + c].attr(k), c += 1
            }, setVisible: function (a, b) {
                var c = this, f = c.chart, e = c.legendItem, g, d = f.options.chart.ignoreHiddenSeries, k = c.visible;
                g = (c.visible = a = c.options.visible = c.userOptions.visible = void 0 === a ? !k : a) ? "show" : "hide";
                w(["group", "dataLabelsGroup", "markerGroup", "tracker", "tt"], function (a) {
                    if (c[a]) c[a][g]()
                });
                if (f.hoverSeries === c || (f.hoverPoint && f.hoverPoint.series) === c) c.onMouseOut();
                e && f.legend.colorizeItem(c, a);
                c.isDirty = !0;
                c.options.stacking && w(f.series, function (a) {
                    a.options.stacking && a.visible && (a.isDirty = !0)
                });
                w(c.linkedSeries, function (b) {
                    b.setVisible(a, !1)
                });
                d && (f.isDirtyBox = !0);
                !1 !== b && f.redraw();
                v(c, g)
            }, show: function () {
                this.setVisible(!0)
            }, hide: function () {
                this.setVisible(!1)
            }, select: function (a) {
                this.selected = a = void 0 === a ? !this.selected : a;
                this.checkbox && (this.checkbox.checked = a);
                v(this, a ? "select" :
                    "unselect")
            }, drawTracker: D.drawTrackerGraph
        })
    })(L);
    (function (a) {
        var C = a.Chart, E = a.each, F = a.inArray, r = a.isArray, m = a.isObject, l = a.pick, w = a.splat;
        C.prototype.setResponsive = function (l) {
            var m = this.options.responsive, p = [], e = this.currentResponsive;
            m && m.rules && E(m.rules, function (c) {
                void 0 === c._id && (c._id = a.uniqueKey());
                this.matchResponsiveRule(c, p, l)
            }, this);
            var c = a.merge.apply(0, a.map(p, function (c) {
                return a.find(m.rules, function (a) {
                    return a._id === c
                }).chartOptions
            })), p = p.toString() || void 0;
            p !== (e && e.ruleIds) &&
            (e && this.update(e.undoOptions, l), p ? (this.currentResponsive = {
                ruleIds: p,
                mergedOptions: c,
                undoOptions: this.currentOptions(c)
            }, this.update(c, l)) : this.currentResponsive = void 0)
        };
        C.prototype.matchResponsiveRule = function (a, m) {
            var p = a.condition;
            (p.callback || function () {
                return this.chartWidth <= l(p.maxWidth, Number.MAX_VALUE) && this.chartHeight <= l(p.maxHeight, Number.MAX_VALUE) && this.chartWidth >= l(p.minWidth, 0) && this.chartHeight >= l(p.minHeight, 0)
            }).call(this) && m.push(a._id)
        };
        C.prototype.currentOptions = function (l) {
            function p(e,
                       c, k, b) {
                var g;
                a.objectEach(e, function (a, e) {
                    if (!b && -1 < F(e, ["series", "xAxis", "yAxis"])) for (a = w(a), k[e] = [], g = 0; g < a.length; g++) c[e][g] && (k[e][g] = {}, p(a[g], c[e][g], k[e][g], b + 1)); else m(a) ? (k[e] = r(a) ? [] : {}, p(a, c[e] || {}, k[e], b + 1)) : k[e] = c[e] || null
                })
            }

            var B = {};
            p(l, this.options, B, 0);
            return B
        }
    })(L);
    (function (a) {
        var C = a.addEvent, E = a.Axis, F = a.Chart, r = a.css, m = a.defined, l = a.each, w = a.extend, p = a.noop,
            v = a.pick, B = a.timeUnits, e = a.wrap;
        e(a.Series.prototype, "init", function (a) {
            var c;
            a.apply(this, Array.prototype.slice.call(arguments,
                1));
            (c = this.xAxis) && c.options.ordinal && C(this, "updatedData", function () {
                delete c.ordinalIndex
            })
        });
        e(E.prototype, "getTimeTicks", function (a, e, b, g, l, t, f, u) {
            var c = 0, k, n, y = {}, p, r, v, d = [], z = -Number.MAX_VALUE, G = this.options.tickPixelInterval,
                h = this.chart.time;
            if (!this.options.ordinal && !this.options.breaks || !t || 3 > t.length || void 0 === b) return a.call(this, e, b, g, l);
            r = t.length;
            for (k = 0; k < r; k++) {
                v = k && t[k - 1] > g;
                t[k] < b && (c = k);
                if (k === r - 1 || t[k + 1] - t[k] > 5 * f || v) {
                    if (t[k] > z) {
                        for (n = a.call(this, e, t[c], t[k], l); n.length && n[0] <=
                        z;) n.shift();
                        n.length && (z = n[n.length - 1]);
                        d = d.concat(n)
                    }
                    c = k + 1
                }
                if (v) break
            }
            a = n.info;
            if (u && a.unitRange <= B.hour) {
                k = d.length - 1;
                for (c = 1; c < k; c++) h.dateFormat("%d", d[c]) !== h.dateFormat("%d", d[c - 1]) && (y[d[c]] = "day", p = !0);
                p && (y[d[0]] = "day");
                a.higherRanks = y
            }
            d.info = a;
            if (u && m(G)) {
                u = h = d.length;
                k = [];
                var x;
                for (p = []; u--;) c = this.translate(d[u]), x && (p[u] = x - c), k[u] = x = c;
                p.sort();
                p = p[Math.floor(p.length / 2)];
                p < .6 * G && (p = null);
                u = d[h - 1] > g ? h - 1 : h;
                for (x = void 0; u--;) c = k[u], g = Math.abs(x - c), x && g < .8 * G && (null === p || g < .8 * p) ? (y[d[u]] &&
                !y[d[u + 1]] ? (g = u + 1, x = c) : g = u, d.splice(g, 1)) : x = c
            }
            return d
        });
        w(E.prototype, {
            beforeSetTickPositions: function () {
                var a, e = [], b = !1, g, n = this.getExtremes(), t = n.min, f = n.max, u,
                    p = this.isXAxis && !!this.options.breaks, n = this.options.ordinal, q = Number.MAX_VALUE,
                    A = this.chart.options.chart.ignoreHiddenSeries;
                g = "highcharts-navigator-xaxis" === this.options.className;
                !this.options.overscroll || this.max !== this.dataMax || this.chart.mouseIsDown && !g || this.eventArgs && (!this.eventArgs || "navigator" === this.eventArgs.trigger) || (this.max +=
                    this.options.overscroll, !g && m(this.userMin) && (this.min += this.options.overscroll));
                if (n || p) {
                    l(this.series, function (b, c) {
                        if (!(A && !1 === b.visible || !1 === b.takeOrdinalPosition && !p) && (e = e.concat(b.processedXData), a = e.length, e.sort(function (a, b) {
                                return a - b
                            }), q = Math.min(q, v(b.closestPointRange, q)), a)) for (c = a - 1; c--;) e[c] === e[c + 1] && e.splice(c, 1)
                    });
                    a = e.length;
                    if (2 < a) {
                        g = e[1] - e[0];
                        for (u = a - 1; u-- && !b;) e[u + 1] - e[u] !== g && (b = !0);
                        !this.options.keepOrdinalPadding && (e[0] - t > g || f - e[e.length - 1] > g) && (b = !0)
                    } else this.options.overscroll &&
                    (2 === a ? q = e[1] - e[0] : 1 === a ? (q = this.options.overscroll, e = [e[0], e[0] + q]) : q = this.overscrollPointsRange);
                    b ? (this.options.overscroll && (this.overscrollPointsRange = q, e = e.concat(this.getOverscrollPositions())), this.ordinalPositions = e, g = this.ordinal2lin(Math.max(t, e[0]), !0), u = Math.max(this.ordinal2lin(Math.min(f, e[e.length - 1]), !0), 1), this.ordinalSlope = f = (f - t) / (u - g), this.ordinalOffset = t - g * f) : (this.overscrollPointsRange = v(this.closestPointRange, this.overscrollPointsRange), this.ordinalPositions = this.ordinalSlope =
                        this.ordinalOffset = void 0)
                }
                this.isOrdinal = n && b;
                this.groupIntervalFactor = null
            }, val2lin: function (a, e) {
                var b = this.ordinalPositions;
                if (b) {
                    var c = b.length, k, l;
                    for (k = c; k--;) if (b[k] === a) {
                        l = k;
                        break
                    }
                    for (k = c - 1; k--;) if (a > b[k] || 0 === k) {
                        a = (a - b[k]) / (b[k + 1] - b[k]);
                        l = k + a;
                        break
                    }
                    e = e ? l : this.ordinalSlope * (l || 0) + this.ordinalOffset
                } else e = a;
                return e
            }, lin2val: function (a, e) {
                var b = this.ordinalPositions;
                if (b) {
                    var c = this.ordinalSlope, k = this.ordinalOffset, l = b.length - 1, f;
                    if (e) 0 > a ? a = b[0] : a > l ? a = b[l] : (l = Math.floor(a), f = a - l); else for (; l--;) if (e =
                            c * l + k, a >= e) {
                        c = c * (l + 1) + k;
                        f = (a - e) / (c - e);
                        break
                    }
                    return void 0 !== f && void 0 !== b[l] ? b[l] + (f ? f * (b[l + 1] - b[l]) : 0) : a
                }
                return a
            }, getExtendedPositions: function () {
                var a = this, e = a.chart, b = a.series[0].currentDataGrouping, g = a.ordinalIndex,
                    n = b ? b.count + b.unitName : "raw", m = a.options.overscroll, f = a.getExtremes(), u, r;
                g || (g = a.ordinalIndex = {});
                g[n] || (u = {
                    series: [], chart: e, getExtremes: function () {
                        return {min: f.dataMin, max: f.dataMax + m}
                    }, options: {ordinal: !0}, val2lin: E.prototype.val2lin, ordinal2lin: E.prototype.ordinal2lin
                }, l(a.series,
                    function (c) {
                        r = {xAxis: u, xData: c.xData.slice(), chart: e, destroyGroupedData: p};
                        r.xData = r.xData.concat(a.getOverscrollPositions());
                        r.options = {
                            dataGrouping: b ? {
                                enabled: !0,
                                forced: !0,
                                approximation: "open",
                                units: [[b.unitName, [b.count]]]
                            } : {enabled: !1}
                        };
                        c.processData.apply(r);
                        u.series.push(r)
                    }), a.beforeSetTickPositions.apply(u), g[n] = u.ordinalPositions);
                return g[n]
            }, getOverscrollPositions: function () {
                var c = this.options.overscroll, e = this.overscrollPointsRange, b = [], g = this.dataMax;
                if (a.defined(e)) for (b.push(g); g <=
                this.dataMax + c;) g += e, b.push(g);
                return b
            }, getGroupIntervalFactor: function (a, e, b) {
                var c;
                b = b.processedXData;
                var k = b.length, l = [];
                c = this.groupIntervalFactor;
                if (!c) {
                    for (c = 0; c < k - 1; c++) l[c] = b[c + 1] - b[c];
                    l.sort(function (a, b) {
                        return a - b
                    });
                    l = l[Math.floor(k / 2)];
                    a = Math.max(a, b[0]);
                    e = Math.min(e, b[k - 1]);
                    this.groupIntervalFactor = c = k * l / (e - a)
                }
                return c
            }, postProcessTickInterval: function (a) {
                var c = this.ordinalSlope;
                return c ? this.options.breaks ? this.closestPointRange || a : a / (c / this.closestPointRange) : a
            }
        });
        E.prototype.ordinal2lin =
            E.prototype.val2lin;
        e(F.prototype, "pan", function (a, e) {
            var b = this.xAxis[0], c = b.options.overscroll, k = e.chartX, m = !1;
            if (b.options.ordinal && b.series.length) {
                var f = this.mouseDownX, u = b.getExtremes(), p = u.dataMax, q = u.min, A = u.max, y = this.hoverPoints,
                    v = b.closestPointRange || b.overscrollPointsRange,
                    f = (f - k) / (b.translationSlope * (b.ordinalSlope || v)),
                    w = {ordinalPositions: b.getExtendedPositions()}, v = b.lin2val, B = b.val2lin, d;
                w.ordinalPositions ? 1 < Math.abs(f) && (y && l(y, function (a) {
                    a.setState()
                }), 0 > f ? (y = w, d = b.ordinalPositions ?
                    b : w) : (y = b.ordinalPositions ? b : w, d = w), w = d.ordinalPositions, p > w[w.length - 1] && w.push(p), this.fixedRange = A - q, f = b.toFixedRange(null, null, v.apply(y, [B.apply(y, [q, !0]) + f, !0]), v.apply(d, [B.apply(d, [A, !0]) + f, !0])), f.min >= Math.min(u.dataMin, q) && f.max <= Math.max(p, A) + c && b.setExtremes(f.min, f.max, !0, !1, {trigger: "pan"}), this.mouseDownX = k, r(this.container, {cursor: "move"})) : m = !0
            } else m = !0;
            m && (c && (b.max = b.dataMax + c), a.apply(this, Array.prototype.slice.call(arguments, 1)))
        })
    })(L);
    (function (a) {
        function C() {
            return Array.prototype.slice.call(arguments,
                1)
        }

        function E(a) {
            a.apply(this);
            this.drawBreaks(this.xAxis, ["x"]);
            this.drawBreaks(this.yAxis, F(this.pointArrayMap, ["y"]))
        }

        var F = a.pick, r = a.wrap, m = a.each, l = a.extend, w = a.isArray, p = a.fireEvent, v = a.Axis, B = a.Series;
        l(v.prototype, {
            isInBreak: function (a, c) {
                var e = a.repeat || Infinity, b = a.from, g = a.to - a.from;
                c = c >= b ? (c - b) % e : e - (b - c) % e;
                return a.inclusive ? c <= g : c < g && 0 !== c
            }, isInAnyBreak: function (a, c) {
                var e = this.options.breaks, b = e && e.length, g, l, m;
                if (b) {
                    for (; b--;) this.isInBreak(e[b], a) && (g = !0, l || (l = F(e[b].showPoints, this.isXAxis ?
                        !1 : !0)));
                    m = g && c ? g && !l : g
                }
                return m
            }
        });
        r(v.prototype, "setTickPositions", function (a) {
            a.apply(this, Array.prototype.slice.call(arguments, 1));
            if (this.options.breaks) {
                var c = this.tickPositions, e = this.tickPositions.info, b = [], g;
                for (g = 0; g < c.length; g++) this.isInAnyBreak(c[g]) || b.push(c[g]);
                this.tickPositions = b;
                this.tickPositions.info = e
            }
        });
        r(v.prototype, "init", function (a, c, k) {
            var b = this;
            k.breaks && k.breaks.length && (k.ordinal = !1);
            a.call(this, c, k);
            a = this.options.breaks;
            b.isBroken = w(a) && !!a.length;
            b.isBroken && (b.val2lin =
                function (a) {
                    var c = a, e, f;
                    for (f = 0; f < b.breakArray.length; f++) if (e = b.breakArray[f], e.to <= a) c -= e.len; else if (e.from >= a) break; else if (b.isInBreak(e, a)) {
                        c -= a - e.from;
                        break
                    }
                    return c
                }, b.lin2val = function (a) {
                var c, e;
                for (e = 0; e < b.breakArray.length && !(c = b.breakArray[e], c.from >= a); e++) c.to < a ? a += c.len : b.isInBreak(c, a) && (a += c.len);
                return a
            }, b.setExtremes = function (a, b, c, f, e) {
                for (; this.isInAnyBreak(a);) a -= this.closestPointRange;
                for (; this.isInAnyBreak(b);) b -= this.closestPointRange;
                v.prototype.setExtremes.call(this, a,
                    b, c, f, e)
            }, b.setAxisTranslation = function (a) {
                v.prototype.setAxisTranslation.call(this, a);
                a = b.options.breaks;
                var c = [], e = [], f = 0, g, k, l = b.userMin || b.min, A = b.userMax || b.max,
                    y = F(b.pointRangePadding, 0), r, w;
                m(a, function (a) {
                    k = a.repeat || Infinity;
                    b.isInBreak(a, l) && (l += a.to % k - l % k);
                    b.isInBreak(a, A) && (A -= A % k - a.from % k)
                });
                m(a, function (a) {
                    r = a.from;
                    for (k = a.repeat || Infinity; r - k > l;) r -= k;
                    for (; r < l;) r += k;
                    for (w = r; w < A; w += k) c.push({value: w, move: "in"}), c.push({
                        value: w + (a.to - a.from),
                        move: "out",
                        size: a.breakSize
                    })
                });
                c.sort(function (a,
                                 b) {
                    return a.value === b.value ? ("in" === a.move ? 0 : 1) - ("in" === b.move ? 0 : 1) : a.value - b.value
                });
                g = 0;
                r = l;
                m(c, function (a) {
                    g += "in" === a.move ? 1 : -1;
                    1 === g && "in" === a.move && (r = a.value);
                    0 === g && (e.push({
                        from: r,
                        to: a.value,
                        len: a.value - r - (a.size || 0)
                    }), f += a.value - r - (a.size || 0))
                });
                b.breakArray = e;
                b.unitLength = A - l - f + y;
                p(b, "afterBreaks");
                b.options.staticScale ? b.transA = b.options.staticScale : b.unitLength && (b.transA *= (A - b.min + y) / b.unitLength);
                y && (b.minPixelPadding = b.transA * b.minPointOffset);
                b.min = l;
                b.max = A
            })
        });
        r(B.prototype, "generatePoints",
            function (a) {
                a.apply(this, C(arguments));
                var c = this.xAxis, e = this.yAxis, b = this.points, g, l = b.length, m = this.options.connectNulls, f;
                if (c && e && (c.options.breaks || e.options.breaks)) for (; l--;) g = b[l], f = null === g.y && !1 === m, f || !c.isInAnyBreak(g.x, !0) && !e.isInAnyBreak(g.y, !0) || (b.splice(l, 1), this.data[l] && this.data[l].destroyElements())
            });
        a.Series.prototype.drawBreaks = function (a, c) {
            var e = this, b = e.points, g, l, t, f;
            a && m(c, function (c) {
                g = a.breakArray || [];
                l = a.isXAxis ? a.min : F(e.options.threshold, a.min);
                m(b, function (b) {
                    f =
                        F(b["stack" + c.toUpperCase()], b[c]);
                    m(g, function (c) {
                        t = !1;
                        if (l < c.from && f > c.to || l > c.from && f < c.from) t = "pointBreak"; else if (l < c.from && f > c.from && f < c.to || l > c.from && f > c.to && f < c.from) t = "pointInBreak";
                        t && p(a, t, {point: b, brk: c})
                    })
                })
            })
        };
        a.Series.prototype.gappedPath = function () {
            var e = this.currentDataGrouping, c = e && e.totalRange, e = this.options.gapSize, k = this.points.slice(),
                b = k.length - 1, g = this.yAxis;
            if (e && 0 < b) for ("value" !== this.options.gapUnit && (e *= this.closestPointRange), c && c > e && (e = c); b--;) k[b + 1].x - k[b].x > e && (c = (k[b].x +
                k[b + 1].x) / 2, k.splice(b + 1, 0, {
                isNull: !0,
                x: c
            }), this.options.stacking && (c = g.stacks[this.stackKey][c] = new a.StackItem(g, g.options.stackLabels, !1, c, this.stack), c.total = 0));
            return this.getGraphPath(k)
        };
        r(a.seriesTypes.column.prototype, "drawPoints", E);
        r(a.Series.prototype, "drawPoints", E)
    })(L);
    (function (a) {
        var C = a.arrayMax, E = a.arrayMin, F = a.Axis, r = a.defaultPlotOptions, m = a.defined, l = a.each,
            w = a.extend, p = a.format, v = a.isNumber, B = a.merge, e = a.pick, c = a.Point, k = a.Tooltip, b = a.wrap,
            g = a.Series.prototype, n = g.processData,
            t = g.generatePoints, f = {
                approximation: "average",
                groupPixelWidth: 2,
                dateTimeLabelFormats: {
                    millisecond: ["%A, %b %e, %H:%M:%S.%L", "%A, %b %e, %H:%M:%S.%L", "-%H:%M:%S.%L"],
                    second: ["%A, %b %e, %H:%M:%S", "%A, %b %e, %H:%M:%S", "-%H:%M:%S"],
                    minute: ["%A, %b %e, %H:%M", "%A, %b %e, %H:%M", "-%H:%M"],
                    hour: ["%A, %b %e, %H:%M", "%A, %b %e, %H:%M", "-%H:%M"],
                    day: ["%A, %b %e, %Y", "%A, %b %e", "-%A, %b %e, %Y"],
                    week: ["Week from %A, %b %e, %Y", "%A, %b %e", "-%A, %b %e, %Y"],
                    month: ["%B %Y", "%B", "-%B %Y"],
                    year: ["%Y", "%Y", "-%Y"]
                }
            },
            u = {
                line: {},
                spline: {},
                area: {},
                areaspline: {},
                column: {approximation: "sum", groupPixelWidth: 10},
                arearange: {approximation: "range"},
                areasplinerange: {approximation: "range"},
                columnrange: {approximation: "range", groupPixelWidth: 10},
                candlestick: {approximation: "ohlc", groupPixelWidth: 10},
                ohlc: {approximation: "ohlc", groupPixelWidth: 5}
            },
            D = a.defaultDataGroupingUnits = [["millisecond", [1, 2, 5, 10, 20, 25, 50, 100, 200, 500]], ["second", [1, 2, 5, 10, 15, 30]], ["minute", [1, 2, 5, 10, 15, 30]], ["hour", [1, 2, 3, 4, 6, 8, 12]], ["day", [1]], ["week",
                [1]], ["month", [1, 3, 6]], ["year", null]], q = a.approximations = {
                sum: function (a) {
                    var b = a.length, c;
                    if (!b && a.hasNulls) c = null; else if (b) for (c = 0; b--;) c += a[b];
                    return c
                }, average: function (a) {
                    var b = a.length;
                    a = q.sum(a);
                    v(a) && b && (a /= b);
                    return a
                }, averages: function () {
                    var a = [];
                    l(arguments, function (b) {
                        a.push(q.average(b))
                    });
                    return void 0 === a[0] ? void 0 : a
                }, open: function (a) {
                    return a.length ? a[0] : a.hasNulls ? null : void 0
                }, high: function (a) {
                    return a.length ? C(a) : a.hasNulls ? null : void 0
                }, low: function (a) {
                    return a.length ? E(a) : a.hasNulls ?
                        null : void 0
                }, close: function (a) {
                    return a.length ? a[a.length - 1] : a.hasNulls ? null : void 0
                }, ohlc: function (a, b, c, f) {
                    a = q.open(a);
                    b = q.high(b);
                    c = q.low(c);
                    f = q.close(f);
                    if (v(a) || v(b) || v(c) || v(f)) return [a, b, c, f]
                }, range: function (a, b) {
                    a = q.low(a);
                    b = q.high(b);
                    if (v(a) || v(b)) return [a, b];
                    if (null === a && null === b) return null
                }
            };
        g.groupData = function (a, b, c, e) {
            var g = this.data, d = this.options.data, k = [], m = [], h = [], n = a.length, A, y, p = !!b, t = [];
            e = "function" === typeof e ? e : q[e] || u[this.type] && q[u[this.type].approximation] || q[f.approximation];
            var r = this.pointArrayMap, D = r && r.length, w = 0;
            y = 0;
            var B, H;
            D ? l(r, function () {
                t.push([])
            }) : t.push([]);
            B = D || 1;
            for (H = 0; H <= n && !(a[H] >= c[0]); H++) ;
            for (H; H <= n; H++) {
                for (; void 0 !== c[w + 1] && a[H] >= c[w + 1] || H === n;) {
                    A = c[w];
                    this.dataGroupInfo = {start: y, length: t[0].length};
                    y = e.apply(this, t);
                    void 0 !== y && (k.push(A), m.push(y), h.push(this.dataGroupInfo));
                    y = H;
                    for (A = 0; A < B; A++) t[A].length = 0, t[A].hasNulls = !1;
                    w += 1;
                    if (H === n) break
                }
                if (H === n) break;
                if (r) {
                    A = this.cropStart + H;
                    var I = g && g[A] || this.pointClass.prototype.applyOptions.apply({series: this},
                        [d[A]]), C;
                    for (A = 0; A < D; A++) C = I[r[A]], v(C) ? t[A].push(C) : null === C && (t[A].hasNulls = !0)
                } else A = p ? b[H] : null, v(A) ? t[0].push(A) : null === A && (t[0].hasNulls = !0)
            }
            return [k, m, h]
        };
        g.processData = function () {
            var a = this.chart, b = this.options.dataGrouping,
                c = !1 !== this.allowDG && b && e(b.enabled, a.options.isStock),
                f = this.visible || !a.options.chart.ignoreHiddenSeries, k, d = this.currentDataGrouping, l;
            this.forceCrop = c;
            this.groupPixelWidth = null;
            this.hasProcessed = !0;
            if (!1 !== n.apply(this, arguments) && c) {
                this.destroyGroupedData();
                var q =
                        this.processedXData, h = this.processedYData, u = a.plotSizeX, a = this.xAxis,
                    p = a.options.ordinal, t = this.groupPixelWidth = a.getGroupPixelWidth && a.getGroupPixelWidth();
                if (t) {
                    this.isDirty = k = !0;
                    this.points = null;
                    c = a.getExtremes();
                    l = c.min;
                    c = c.max;
                    p = p && a.getGroupIntervalFactor(l, c, this) || 1;
                    t = t * (c - l) / u * p;
                    u = a.getTimeTicks(a.normalizeTimeTickInterval(t, b.units || D), Math.min(l, q[0]), Math.max(c, q[q.length - 1]), a.options.startOfWeek, q, this.closestPointRange);
                    q = g.groupData.apply(this, [q, h, u, b.approximation]);
                    h = q[0];
                    p = q[1];
                    if (b.smoothed && h.length) {
                        b = h.length - 1;
                        for (h[b] = Math.min(h[b], c); b-- && 0 < b;) h[b] += t / 2;
                        h[0] = Math.max(h[0], l)
                    }
                    l = u.info;
                    this.closestPointRange = u.info.totalRange;
                    this.groupMap = q[2];
                    m(h[0]) && h[0] < a.dataMin && f && (a.min === a.dataMin && (a.min = h[0]), a.dataMin = h[0]);
                    this.processedXData = h;
                    this.processedYData = p
                } else this.groupMap = null;
                this.hasGroupedData = k;
                this.currentDataGrouping = l;
                this.preventGraphAnimation = (d && d.totalRange) !== (l && l.totalRange)
            }
        };
        g.destroyGroupedData = function () {
            var a = this.groupedData;
            l(a || [], function (b,
                                 c) {
                b && (a[c] = b.destroy ? b.destroy() : null)
            });
            this.groupedData = null
        };
        g.generatePoints = function () {
            t.apply(this);
            this.destroyGroupedData();
            this.groupedData = this.hasGroupedData ? this.points : null
        };
        a.addEvent(c.prototype, "update", function () {
            if (this.dataGroup) return a.error(24), !1
        });
        b(k.prototype, "tooltipFooterHeaderFormatter", function (a, b, c) {
            var f = this.chart.time, e = b.series, d = e.tooltipOptions, g = e.options.dataGrouping, k = d.xDateFormat,
                h, l = e.xAxis;
            return l && "datetime" === l.options.type && g && v(b.key) ? (a = e.currentDataGrouping,
                g = g.dateTimeLabelFormats, a ? (l = g[a.unitName], 1 === a.count ? k = l[0] : (k = l[1], h = l[2])) : !k && g && (k = this.getXDateFormat(b, d, l)), k = f.dateFormat(k, b.key), h && (k += f.dateFormat(h, b.key + a.totalRange - 1)), p(d[(c ? "footer" : "header") + "Format"], {
                point: w(b.point, {key: k}),
                series: e
            }, f)) : a.call(this, b, c)
        });
        a.addEvent(g, "destroy", g.destroyGroupedData);
        b(g, "setOptions", function (a, b) {
            a = a.call(this, b);
            var c = this.type, e = this.chart.options.plotOptions, g = r[c].dataGrouping;
            u[c] && (g || (g = B(f, u[c])), a.dataGrouping = B(g, e.series && e.series.dataGrouping,
                e[c].dataGrouping, b.dataGrouping));
            this.chart.options.isStock && (this.requireSorting = !0);
            return a
        });
        a.addEvent(F.prototype, "afterSetScale", function () {
            l(this.series, function (a) {
                a.hasProcessed = !1
            })
        });
        F.prototype.getGroupPixelWidth = function () {
            var a = this.series, b = a.length, c, f = 0, e = !1, d;
            for (c = b; c--;) (d = a[c].options.dataGrouping) && (f = Math.max(f, d.groupPixelWidth));
            for (c = b; c--;) (d = a[c].options.dataGrouping) && a[c].hasProcessed && (b = (a[c].processedXData || a[c].data).length, a[c].groupPixelWidth || b > this.chart.plotSizeX /
            f || b && d.forced) && (e = !0);
            return e ? f : 0
        };
        F.prototype.setDataGrouping = function (a, b) {
            var c;
            b = e(b, !0);
            a || (a = {forced: !1, units: null});
            if (this instanceof F) for (c = this.series.length; c--;) this.series[c].update({dataGrouping: a}, !1); else l(this.chart.options.series, function (b) {
                b.dataGrouping = a
            }, !1);
            b && this.chart.redraw()
        }
    })(L);
    (function (a) {
        var C = a.each, E = a.Point, F = a.seriesType, r = a.seriesTypes;
        F("ohlc", "column", {
            lineWidth: 1,
            tooltip: {pointFormat: '\x3cspan style\x3d"color:{point.color}"\x3e\u25cf\x3c/span\x3e \x3cb\x3e {series.name}\x3c/b\x3e\x3cbr/\x3eOpen: {point.open}\x3cbr/\x3eHigh: {point.high}\x3cbr/\x3eLow: {point.low}\x3cbr/\x3eClose: {point.close}\x3cbr/\x3e'},
            threshold: null,
            states: {hover: {lineWidth: 3}},
            stickyTracking: !0
        }, {
            directTouch: !1,
            pointArrayMap: ["open", "high", "low", "close"],
            toYData: function (a) {
                return [a.open, a.high, a.low, a.close]
            },
            pointValKey: "close",
            pointAttrToOptions: {stroke: "color", "stroke-width": "lineWidth"},
            pointAttribs: function (a, l) {
                l = r.column.prototype.pointAttribs.call(this, a, l);
                var m = this.options;
                delete l.fill;
                !a.options.color && m.upColor && a.open < a.close && (l.stroke = m.upColor);
                return l
            },
            translate: function () {
                var a = this, l = a.yAxis, w = !!a.modifyValue,
                    p = ["plotOpen", "plotHigh", "plotLow", "plotClose", "yBottom"];
                r.column.prototype.translate.apply(a);
                C(a.points, function (m) {
                    C([m.open, m.high, m.low, m.close, m.low], function (r, e) {
                        null !== r && (w && (r = a.modifyValue(r)), m[p[e]] = l.toPixels(r, !0))
                    });
                    m.tooltipPos[1] = m.plotHigh + l.pos - a.chart.plotTop
                })
            },
            drawPoints: function () {
                var a = this, l = a.chart;
                C(a.points, function (m) {
                    var p, r, w, e, c = m.graphic, k, b = !c;
                    void 0 !== m.plotY && (c || (m.graphic = c = l.renderer.path().add(a.group)), c.attr(a.pointAttribs(m, m.selected && "select")), r = c.strokeWidth() %
                        2 / 2, k = Math.round(m.plotX) - r, w = Math.round(m.shapeArgs.width / 2), e = ["M", k, Math.round(m.yBottom), "L", k, Math.round(m.plotHigh)], null !== m.open && (p = Math.round(m.plotOpen) + r, e.push("M", k, p, "L", k - w, p)), null !== m.close && (p = Math.round(m.plotClose) + r, e.push("M", k, p, "L", k + w, p)), c[b ? "attr" : "animate"]({d: e}).addClass(m.getClassName(), !0))
                })
            },
            animate: null
        }, {
            getClassName: function () {
                return E.prototype.getClassName.call(this) + (this.open < this.close ? " highcharts-point-up" : " highcharts-point-down")
            }
        })
    })(L);
    (function (a) {
        var C =
            a.defaultPlotOptions, E = a.each, F = a.merge, r = a.seriesType, m = a.seriesTypes;
        r("candlestick", "ohlc", F(C.column, {
            states: {hover: {lineWidth: 2}},
            tooltip: C.ohlc.tooltip,
            threshold: null,
            lineColor: "#000000",
            lineWidth: 1,
            upColor: "#ffffff",
            stickyTracking: !0
        }), {
            pointAttribs: function (a, r) {
                var l = m.column.prototype.pointAttribs.call(this, a, r), v = this.options, w = a.open < a.close,
                    e = v.lineColor || this.color;
                l["stroke-width"] = v.lineWidth;
                l.fill = a.options.color || (w ? v.upColor || this.color : this.color);
                l.stroke = a.lineColor || (w ? v.upLineColor ||
                    e : e);
                r && (a = v.states[r], l.fill = a.color || l.fill, l.stroke = a.lineColor || l.stroke, l["stroke-width"] = a.lineWidth || l["stroke-width"]);
                return l
            }, drawPoints: function () {
                var a = this, m = a.chart;
                E(a.points, function (l) {
                    var p = l.graphic, r, e, c, k, b, g, n, t = !p;
                    void 0 !== l.plotY && (p || (l.graphic = p = m.renderer.path().add(a.group)), p.attr(a.pointAttribs(l, l.selected && "select")).shadow(a.options.shadow), b = p.strokeWidth() % 2 / 2, g = Math.round(l.plotX) - b, r = l.plotOpen, e = l.plotClose, c = Math.min(r, e), r = Math.max(r, e), n = Math.round(l.shapeArgs.width /
                        2), e = Math.round(c) !== Math.round(l.plotHigh), k = r !== l.yBottom, c = Math.round(c) + b, r = Math.round(r) + b, b = [], b.push("M", g - n, r, "L", g - n, c, "L", g + n, c, "L", g + n, r, "Z", "M", g, c, "L", g, e ? Math.round(l.plotHigh) : c, "M", g, r, "L", g, k ? Math.round(l.yBottom) : r), p[t ? "attr" : "animate"]({d: b}).addClass(l.getClassName(), !0))
                })
            }
        })
    })(L);
    da = function (a) {
        var C = a.each, E = a.seriesTypes, F = a.stableSort;
        return {
            getPlotBox: function () {
                return a.Series.prototype.getPlotBox.call(this.options.onSeries && this.chart.get(this.options.onSeries) || this)
            },
            translate: function () {
                E.column.prototype.translate.apply(this);
                var a = this.options, m = this.chart, l = this.points, w = l.length - 1, p, v, B = a.onSeries;
                p = B && m.get(B);
                var a = a.onKey || "y", B = p && p.options.step, e = p && p.points, c = e && e.length, k = this.xAxis,
                    b = this.yAxis, g = 0, n, t, f, u;
                if (p && p.visible && c) for (g = (p.pointXOffset || 0) + (p.barW || 0) / 2, p = p.currentDataGrouping, t = e[c - 1].x + (p ? p.totalRange : 0), F(l, function (a, b) {
                    return a.x - b.x
                }), a = "plot" + a[0].toUpperCase() + a.substr(1); c-- && l[w] && !(n = e[c], p = l[w], p.y = n.y, n.x <= p.x && void 0 !== n[a] &&
                (p.x <= t && (p.plotY = n[a], n.x < p.x && !B && (f = e[c + 1]) && void 0 !== f[a] && (u = (p.x - n.x) / (f.x - n.x), p.plotY += u * (f[a] - n[a]), p.y += u * (f.y - n.y))), w--, c++, 0 > w));) ;
                C(l, function (a, c) {
                    var f;
                    a.plotX += g;
                    void 0 === a.plotY && (0 <= a.plotX && a.plotX <= k.len ? a.plotY = m.chartHeight - k.bottom - (k.opposite ? k.height : 0) + k.offset - b.top : a.shapeArgs = {});
                    (v = l[c - 1]) && v.plotX === a.plotX && (void 0 === v.stackIndex && (v.stackIndex = 0), f = v.stackIndex + 1);
                    a.stackIndex = f
                })
            }
        }
    }(L);
    (function (a, C) {
        function E(a) {
            e[a + "pin"] = function (c, b, g, l, m) {
                var f = m && m.anchorX;
                m = m && m.anchorY;
                "circle" === a && l > g && (c -= Math.round((l - g) / 2), g = l);
                c = e[a](c, b, g, l);
                f && m && (c.push("M", "circle" === a ? c[1] - c[4] : c[1] + c[4] / 2, b > m ? b : b + l, "L", f, m), c = c.concat(e.circle(f - 1, m - 1, 2, 2)));
                return c
            }
        }

        var F = a.addEvent, r = a.each, m = a.merge, l = a.noop, w = a.Renderer, p = a.seriesType, v = a.TrackerMixin,
            B = a.VMLRenderer, e = a.SVGRenderer.prototype.symbols;
        p("flags", "column", {
            pointRange: 0,
            allowOverlapX: !1,
            shape: "flag",
            stackDistance: 12,
            textAlign: "center",
            tooltip: {pointFormat: "{point.text}\x3cbr/\x3e"},
            threshold: null,
            y: -30,
            fillColor: "#ffffff",
            lineWidth: 1,
            states: {hover: {lineColor: "#000000", fillColor: "#ccd6eb"}},
            style: {fontSize: "11px", fontWeight: "bold"}
        }, {
            sorted: !1,
            noSharedTooltip: !0,
            allowDG: !1,
            takeOrdinalPosition: !1,
            trackerGroups: ["markerGroup"],
            forceCrop: !0,
            init: a.Series.prototype.init,
            pointAttribs: function (a, e) {
                var b = this.options, c = a && a.color || this.color, k = b.lineColor, l = a && a.lineWidth;
                a = a && a.fillColor || b.fillColor;
                e && (a = b.states[e].fillColor, k = b.states[e].lineColor, l = b.states[e].lineWidth);
                return {
                    fill: a || c, stroke: k ||
                    c, "stroke-width": l || b.lineWidth || 0
                }
            },
            translate: C.translate,
            getPlotBox: C.getPlotBox,
            drawPoints: function () {
                var c = this.points, e = this.chart, b = e.renderer, g, l, t = this.options, f = t.y, u, p, q, A, y, v,
                    w = this.yAxis, B = {}, d = [];
                for (p = c.length; p--;) q = c[p], v = q.plotX > this.xAxis.len, g = q.plotX, A = q.stackIndex, u = q.options.shape || t.shape, l = q.plotY, void 0 !== l && (l = q.plotY + f - (void 0 !== A && A * t.stackDistance)), q.anchorX = A ? void 0 : q.plotX, y = A ? void 0 : q.plotY, A = q.graphic, void 0 !== l && 0 <= g && !v ? (A || (A = q.graphic = b.label("", null, null, u, null,
                    null, t.useHTML).attr(this.pointAttribs(q)).css(m(t.style, q.style)).attr({
                    align: "flag" === u ? "left" : "center",
                    width: t.width,
                    height: t.height,
                    "text-align": t.textAlign
                }).addClass("highcharts-point").add(this.markerGroup), q.graphic.div && (q.graphic.div.point = q), A.shadow(t.shadow), A.isNew = !0), 0 < g && (g -= A.strokeWidth() % 2), u = {
                    y: l,
                    anchorY: y
                }, t.allowOverlapX && (u.x = g, u.anchorX = q.anchorX), A.attr({text: q.options.title || t.title || "A"})[A.isNew ? "attr" : "animate"](u), t.allowOverlapX || (B[q.plotX] ? B[q.plotX].size = Math.max(B[q.plotX].size,
                    A.width) : B[q.plotX] = {
                    align: 0,
                    size: A.width,
                    target: g,
                    anchorX: g
                }), q.tooltipPos = e.inverted ? [w.len + w.pos - e.plotLeft - l, this.xAxis.len - g] : [g, l + w.pos - e.plotTop]) : A && (q.graphic = A.destroy());
                t.allowOverlapX || (a.objectEach(B, function (a) {
                    a.plotX = a.anchorX;
                    d.push(a)
                }), a.distribute(d, this.xAxis.len), r(c, function (a) {
                    var b = a.graphic && B[a.plotX];
                    b && (a.graphic[a.graphic.isNew ? "attr" : "animate"]({
                        x: b.pos,
                        anchorX: a.anchorX
                    }), a.graphic.isNew = !1)
                }));
                t.useHTML && a.wrap(this.markerGroup, "on", function (b) {
                    return a.SVGElement.prototype.on.apply(b.apply(this,
                        [].slice.call(arguments, 1)), [].slice.call(arguments, 1))
                })
            },
            drawTracker: function () {
                var a = this.points;
                v.drawTrackerPoint.apply(this);
                r(a, function (c) {
                    var b = c.graphic;
                    b && F(b.element, "mouseover", function () {
                        0 < c.stackIndex && !c.raised && (c._y = b.y, b.attr({y: c._y - 8}), c.raised = !0);
                        r(a, function (a) {
                            a !== c && a.raised && a.graphic && (a.graphic.attr({y: a._y}), a.raised = !1)
                        })
                    })
                })
            },
            animate: l,
            buildKDTree: l,
            setClip: l
        });
        e.flag = function (a, k, b, g, l) {
            var c = l && l.anchorX || a;
            l = l && l.anchorY || k;
            return e.circle(c - 1, l - 1, 2, 2).concat(["M",
                c, l, "L", a, k + g, a, k, a + b, k, a + b, k + g, a, k + g, "Z"])
        };
        E("circle");
        E("square");
        w === B && r(["flag", "circlepin", "squarepin"], function (a) {
            B.prototype.symbols[a] = e[a]
        })
    })(L, da);
    (function (a) {
        function C(a, b, c) {
            this.init(a, b, c)
        }

        var E = a.addEvent, F = a.Axis, r = a.correctFloat, m = a.defaultOptions, l = a.defined,
            w = a.destroyObjectProperties, p = a.each, v = a.fireEvent, B = a.hasTouch, e = a.isTouchDevice,
            c = a.merge, k = a.pick, b = a.removeEvent, g = a.wrap, n, t = {
                height: e ? 20 : 14,
                barBorderRadius: 0,
                buttonBorderRadius: 0,
                liveRedraw: a.svg && !e,
                margin: 10,
                minWidth: 6,
                step: .2,
                zIndex: 3,
                barBackgroundColor: "#cccccc",
                barBorderWidth: 1,
                barBorderColor: "#cccccc",
                buttonArrowColor: "#333333",
                buttonBackgroundColor: "#e6e6e6",
                buttonBorderColor: "#cccccc",
                buttonBorderWidth: 1,
                rifleColor: "#333333",
                trackBackgroundColor: "#f2f2f2",
                trackBorderColor: "#f2f2f2",
                trackBorderWidth: 1
            };
        m.scrollbar = c(!0, t, m.scrollbar);
        a.swapXY = n = function (a, b) {
            var c = a.length, f;
            if (b) for (b = 0; b < c; b += 3) f = a[b + 1], a[b + 1] = a[b + 2], a[b + 2] = f;
            return a
        };
        C.prototype = {
            init: function (a, b, e) {
                this.scrollbarButtons = [];
                this.renderer =
                    a;
                this.userOptions = b;
                this.options = c(t, b);
                this.chart = e;
                this.size = k(this.options.size, this.options.height);
                b.enabled && (this.render(), this.initEvents(), this.addEvents())
            }, render: function () {
                var a = this.renderer, b = this.options, c = this.size, e;
                this.group = e = a.g("scrollbar").attr({zIndex: b.zIndex, translateY: -99999}).add();
                this.track = a.rect().addClass("highcharts-scrollbar-track").attr({
                    x: 0,
                    r: b.trackBorderRadius || 0,
                    height: c,
                    width: c
                }).add(e);
                this.track.attr({
                    fill: b.trackBackgroundColor, stroke: b.trackBorderColor,
                    "stroke-width": b.trackBorderWidth
                });
                this.trackBorderWidth = this.track.strokeWidth();
                this.track.attr({y: -this.trackBorderWidth % 2 / 2});
                this.scrollbarGroup = a.g().add(e);
                this.scrollbar = a.rect().addClass("highcharts-scrollbar-thumb").attr({
                    height: c,
                    width: c,
                    r: b.barBorderRadius || 0
                }).add(this.scrollbarGroup);
                this.scrollbarRifles = a.path(n(["M", -3, c / 4, "L", -3, 2 * c / 3, "M", 0, c / 4, "L", 0, 2 * c / 3, "M", 3, c / 4, "L", 3, 2 * c / 3], b.vertical)).addClass("highcharts-scrollbar-rifles").add(this.scrollbarGroup);
                this.scrollbar.attr({
                    fill: b.barBackgroundColor,
                    stroke: b.barBorderColor, "stroke-width": b.barBorderWidth
                });
                this.scrollbarRifles.attr({stroke: b.rifleColor, "stroke-width": 1});
                this.scrollbarStrokeWidth = this.scrollbar.strokeWidth();
                this.scrollbarGroup.translate(-this.scrollbarStrokeWidth % 2 / 2, -this.scrollbarStrokeWidth % 2 / 2);
                this.drawScrollbarButton(0);
                this.drawScrollbarButton(1)
            }, position: function (a, b, c, e) {
                var f = this.options.vertical, g = 0, k = this.rendered ? "animate" : "attr";
                this.x = a;
                this.y = b + this.trackBorderWidth;
                this.width = c;
                this.xOffset = this.height = e;
                this.yOffset = g;
                f ? (this.width = this.yOffset = c = g = this.size, this.xOffset = b = 0, this.barWidth = e - 2 * c, this.x = a += this.options.margin) : (this.height = this.xOffset = e = b = this.size, this.barWidth = c - 2 * e, this.y += this.options.margin);
                this.group[k]({translateX: a, translateY: this.y});
                this.track[k]({width: c, height: e});
                this.scrollbarButtons[1][k]({translateX: f ? 0 : c - b, translateY: f ? e - g : 0})
            }, drawScrollbarButton: function (a) {
                var b = this.renderer, c = this.scrollbarButtons, f = this.options, e = this.size, g;
                g = b.g().add(this.group);
                c.push(g);
                g = b.rect().addClass("highcharts-scrollbar-button").add(g);
                g.attr({
                    stroke: f.buttonBorderColor,
                    "stroke-width": f.buttonBorderWidth,
                    fill: f.buttonBackgroundColor
                });
                g.attr(g.crisp({
                    x: -.5,
                    y: -.5,
                    width: e + 1,
                    height: e + 1,
                    r: f.buttonBorderRadius
                }, g.strokeWidth()));
                g = b.path(n(["M", e / 2 + (a ? -1 : 1), e / 2 - 3, "L", e / 2 + (a ? -1 : 1), e / 2 + 3, "L", e / 2 + (a ? 2 : -2), e / 2], f.vertical)).addClass("highcharts-scrollbar-arrow").add(c[a]);
                g.attr({fill: f.buttonArrowColor})
            }, setRange: function (a, b) {
                var c = this.options, f = c.vertical, e = c.minWidth, g = this.barWidth,
                    k, m, n = this.rendered && !this.hasDragged ? "animate" : "attr";
                l(g) && (a = Math.max(a, 0), k = Math.ceil(g * a), this.calculatedWidth = m = r(g * Math.min(b, 1) - k), m < e && (k = (g - e + m) * a, m = e), e = Math.floor(k + this.xOffset + this.yOffset), g = m / 2 - .5, this.from = a, this.to = b, f ? (this.scrollbarGroup[n]({translateY: e}), this.scrollbar[n]({height: m}), this.scrollbarRifles[n]({translateY: g}), this.scrollbarTop = e, this.scrollbarLeft = 0) : (this.scrollbarGroup[n]({translateX: e}), this.scrollbar[n]({width: m}), this.scrollbarRifles[n]({translateX: g}), this.scrollbarLeft =
                    e, this.scrollbarTop = 0), 12 >= m ? this.scrollbarRifles.hide() : this.scrollbarRifles.show(!0), !1 === c.showFull && (0 >= a && 1 <= b ? this.group.hide() : this.group.show()), this.rendered = !0)
            }, initEvents: function () {
                var a = this;
                a.mouseMoveHandler = function (b) {
                    var c = a.chart.pointer.normalize(b), f = a.options.vertical ? "chartY" : "chartX",
                        e = a.initPositions;
                    !a.grabbedCenter || b.touches && 0 === b.touches[0][f] || (c = a.cursorToScrollbarPosition(c)[f], f = a[f], f = c - f, a.hasDragged = !0, a.updatePosition(e[0] + f, e[1] + f), a.hasDragged && v(a, "changed",
                        {from: a.from, to: a.to, trigger: "scrollbar", DOMType: b.type, DOMEvent: b}))
                };
                a.mouseUpHandler = function (b) {
                    a.hasDragged && v(a, "changed", {
                        from: a.from,
                        to: a.to,
                        trigger: "scrollbar",
                        DOMType: b.type,
                        DOMEvent: b
                    });
                    a.grabbedCenter = a.hasDragged = a.chartX = a.chartY = null
                };
                a.mouseDownHandler = function (b) {
                    b = a.chart.pointer.normalize(b);
                    b = a.cursorToScrollbarPosition(b);
                    a.chartX = b.chartX;
                    a.chartY = b.chartY;
                    a.initPositions = [a.from, a.to];
                    a.grabbedCenter = !0
                };
                a.buttonToMinClick = function (b) {
                    var c = r(a.to - a.from) * a.options.step;
                    a.updatePosition(r(a.from -
                        c), r(a.to - c));
                    v(a, "changed", {from: a.from, to: a.to, trigger: "scrollbar", DOMEvent: b})
                };
                a.buttonToMaxClick = function (b) {
                    var c = (a.to - a.from) * a.options.step;
                    a.updatePosition(a.from + c, a.to + c);
                    v(a, "changed", {from: a.from, to: a.to, trigger: "scrollbar", DOMEvent: b})
                };
                a.trackClick = function (b) {
                    var c = a.chart.pointer.normalize(b), f = a.to - a.from, e = a.y + a.scrollbarTop,
                        g = a.x + a.scrollbarLeft;
                    a.options.vertical && c.chartY > e || !a.options.vertical && c.chartX > g ? a.updatePosition(a.from + f, a.to + f) : a.updatePosition(a.from - f, a.to - f);
                    v(a, "changed", {from: a.from, to: a.to, trigger: "scrollbar", DOMEvent: b})
                }
            }, cursorToScrollbarPosition: function (a) {
                var b = this.options, b = b.minWidth > this.calculatedWidth ? b.minWidth : 0;
                return {
                    chartX: (a.chartX - this.x - this.xOffset) / (this.barWidth - b),
                    chartY: (a.chartY - this.y - this.yOffset) / (this.barWidth - b)
                }
            }, updatePosition: function (a, b) {
                1 < b && (a = r(1 - r(b - a)), b = 1);
                0 > a && (b = r(b - a), a = 0);
                this.from = a;
                this.to = b
            }, update: function (a) {
                this.destroy();
                this.init(this.chart.renderer, c(!0, this.options, a), this.chart)
            }, addEvents: function () {
                var a =
                        this.options.inverted ? [1, 0] : [0, 1], b = this.scrollbarButtons, c = this.scrollbarGroup.element,
                    e = this.mouseDownHandler, g = this.mouseMoveHandler, k = this.mouseUpHandler,
                    a = [[b[a[0]].element, "click", this.buttonToMinClick], [b[a[1]].element, "click", this.buttonToMaxClick], [this.track.element, "click", this.trackClick], [c, "mousedown", e], [c.ownerDocument, "mousemove", g], [c.ownerDocument, "mouseup", k]];
                B && a.push([c, "touchstart", e], [c.ownerDocument, "touchmove", g], [c.ownerDocument, "touchend", k]);
                p(a, function (a) {
                    E.apply(null,
                        a)
                });
                this._events = a
            }, removeEvents: function () {
                p(this._events, function (a) {
                    b.apply(null, a)
                });
                this._events.length = 0
            }, destroy: function () {
                var a = this.chart.scroller;
                this.removeEvents();
                p(["track", "scrollbarRifles", "scrollbar", "scrollbarGroup", "group"], function (a) {
                    this[a] && this[a].destroy && (this[a] = this[a].destroy())
                }, this);
                a && this === a.scrollbar && (a.scrollbar = null, w(a.scrollbarButtons))
            }
        };
        g(F.prototype, "init", function (a) {
            var b = this;
            a.apply(b, Array.prototype.slice.call(arguments, 1));
            b.options.scrollbar && b.options.scrollbar.enabled &&
            (b.options.scrollbar.vertical = !b.horiz, b.options.startOnTick = b.options.endOnTick = !1, b.scrollbar = new C(b.chart.renderer, b.options.scrollbar, b.chart), E(b.scrollbar, "changed", function (a) {
                var c = Math.min(k(b.options.min, b.min), b.min, b.dataMin),
                    f = Math.max(k(b.options.max, b.max), b.max, b.dataMax) - c, e;
                b.horiz && !b.reversed || !b.horiz && b.reversed ? (e = c + f * this.to, c += f * this.from) : (e = c + f * (1 - this.from), c += f * (1 - this.to));
                b.setExtremes(c, e, !0, !1, a)
            }))
        });
        g(F.prototype, "render", function (a) {
            var b = Math.min(k(this.options.min,
                this.min), this.min, k(this.dataMin, this.min)),
                c = Math.max(k(this.options.max, this.max), this.max, k(this.dataMax, this.max)), f = this.scrollbar,
                e = this.titleOffset || 0;
            a.apply(this, Array.prototype.slice.call(arguments, 1));
            if (f) {
                this.horiz ? (f.position(this.left, this.top + this.height + 2 + this.chart.scrollbarsOffsets[1] + (this.opposite ? 0 : e + this.axisTitleMargin + this.offset), this.width, this.height), e = 1) : (f.position(this.left + this.width + 2 + this.chart.scrollbarsOffsets[0] + (this.opposite ? e + this.axisTitleMargin + this.offset :
                    0), this.top, this.width, this.height), e = 0);
                if (!this.opposite && !this.horiz || this.opposite && this.horiz) this.chart.scrollbarsOffsets[e] += this.scrollbar.size + this.scrollbar.options.margin;
                isNaN(b) || isNaN(c) || !l(this.min) || !l(this.max) ? f.setRange(0, 0) : (e = (this.min - b) / (c - b), b = (this.max - b) / (c - b), this.horiz && !this.reversed || !this.horiz && this.reversed ? f.setRange(e, b) : f.setRange(1 - b, 1 - e))
            }
        });
        g(F.prototype, "getOffset", function (a) {
            var b = this.horiz ? 2 : 1, c = this.scrollbar;
            a.apply(this, Array.prototype.slice.call(arguments,
                1));
            c && (this.chart.scrollbarsOffsets = [0, 0], this.chart.axisOffset[b] += c.size + c.options.margin)
        });
        g(F.prototype, "destroy", function (a) {
            this.scrollbar && (this.scrollbar = this.scrollbar.destroy());
            a.apply(this, Array.prototype.slice.call(arguments, 1))
        });
        a.Scrollbar = C
    })(L);
    (function (a) {
        function C(a) {
            this.init(a)
        }

        var E = a.addEvent, F = a.Axis, r = a.Chart, m = a.color, l = a.defaultOptions, w = a.defined,
            p = a.destroyObjectProperties, v = a.each, B = a.erase, e = a.error, c = a.extend, k = a.grep,
            b = a.hasTouch, g = a.isArray, n = a.isNumber, t = a.isObject,
            f = a.merge, u = a.pick, D = a.removeEvent, q = a.Scrollbar, A = a.Series, y = a.seriesTypes, H = a.wrap,
            I = [].concat(a.defaultDataGroupingUnits), J = function (a) {
                var b = k(arguments, n);
                if (b.length) return Math[a].apply(0, b)
            };
        I[4] = ["day", [1, 2, 3, 4]];
        I[5] = ["week", [1, 2, 3]];
        y = void 0 === y.areaspline ? "line" : "areaspline";
        c(l, {
            navigator: {
                height: 40,
                margin: 25,
                maskInside: !0,
                handles: {
                    width: 7,
                    height: 15,
                    symbols: ["navigator-handle", "navigator-handle"],
                    enabled: !0,
                    lineWidth: 1,
                    backgroundColor: "#f2f2f2",
                    borderColor: "#999999"
                },
                maskFill: m("#6685c2").setOpacity(.3).get(),
                outlineColor: "#cccccc",
                outlineWidth: 1,
                series: {
                    type: y,
                    fillOpacity: .05,
                    lineWidth: 1,
                    compare: null,
                    dataGrouping: {approximation: "average", enabled: !0, groupPixelWidth: 2, smoothed: !0, units: I},
                    dataLabels: {enabled: !1, zIndex: 2},
                    id: "highcharts-navigator-series",
                    className: "highcharts-navigator-series",
                    lineColor: null,
                    marker: {enabled: !1},
                    pointRange: 0,
                    threshold: null
                },
                xAxis: {
                    overscroll: 0,
                    className: "highcharts-navigator-xaxis",
                    tickLength: 0,
                    lineWidth: 0,
                    gridLineColor: "#e6e6e6",
                    gridLineWidth: 1,
                    tickPixelInterval: 200,
                    labels: {
                        align: "left",
                        style: {color: "#999999"}, x: 3, y: -4
                    },
                    crosshair: !1
                },
                yAxis: {
                    className: "highcharts-navigator-yaxis",
                    gridLineWidth: 0,
                    startOnTick: !1,
                    endOnTick: !1,
                    minPadding: .1,
                    maxPadding: .1,
                    labels: {enabled: !1},
                    crosshair: !1,
                    title: {text: null},
                    tickLength: 0,
                    tickWidth: 0
                }
            }
        });
        a.Renderer.prototype.symbols["navigator-handle"] = function (a, b, c, f, e) {
            a = e.width / 2;
            b = Math.round(a / 3) + .5;
            e = e.height;
            return ["M", -a - 1, .5, "L", a, .5, "L", a, e + .5, "L", -a - 1, e + .5, "L", -a - 1, .5, "M", -b, 4, "L", -b, e - 3, "M", b - 1, 4, "L", b - 1, e - 3]
        };
        C.prototype = {
            drawHandle: function (a,
                                  b, c, f) {
                var d = this.navigatorOptions.handles.height;
                this.handles[b][f](c ? {
                    translateX: Math.round(this.left + this.height / 2),
                    translateY: Math.round(this.top + parseInt(a, 10) + .5 - d)
                } : {
                    translateX: Math.round(this.left + parseInt(a, 10)),
                    translateY: Math.round(this.top + this.height / 2 - d / 2 - 1)
                })
            }, drawOutline: function (a, b, c, f) {
                var d = this.navigatorOptions.maskInside, e = this.outline.strokeWidth(), h = e / 2, e = e % 2 / 2,
                    g = this.outlineHeight, k = this.scrollbarHeight, l = this.size, m = this.left - k, n = this.top;
                c ? (m -= h, c = n + b + e, b = n + a + e, a = ["M", m +
                g, n - k - e, "L", m + g, c, "L", m, c, "L", m, b, "L", m + g, b, "L", m + g, n + l + k].concat(d ? ["M", m + g, c - h, "L", m + g, b + h] : [])) : (a += m + k - e, b += m + k - e, n += h, a = ["M", m, n, "L", a, n, "L", a, n + g, "L", b, n + g, "L", b, n, "L", m + l + 2 * k, n].concat(d ? ["M", a - h, n, "L", b + h, n] : []));
                this.outline[f]({d: a})
            }, drawMasks: function (a, b, c, f) {
                var d = this.left, e = this.top, h = this.height, g, k, l, m;
                c ? (l = [d, d, d], m = [e, e + a, e + b], k = [h, h, h], g = [a, b - a, this.size - b]) : (l = [d, d + a, d + b], m = [e, e, e], k = [a, b - a, this.size - b], g = [h, h, h]);
                v(this.shades, function (a, b) {
                    a[f]({x: l[b], y: m[b], width: k[b], height: g[b]})
                })
            },
            renderElements: function () {
                var a = this, b = a.navigatorOptions, c = b.maskInside, e = a.chart, f = e.inverted, g = e.renderer, k;
                a.navigatorGroup = k = g.g("navigator").attr({zIndex: 8, visibility: "hidden"}).add();
                var l = {cursor: f ? "ns-resize" : "ew-resize"};
                v([!c, c, !c], function (c, d) {
                    a.shades[d] = g.rect().addClass("highcharts-navigator-mask" + (1 === d ? "-inside" : "-outside")).attr({fill: c ? b.maskFill : "rgba(0,0,0,0)"}).css(1 === d && l).add(k)
                });
                a.outline = g.path().addClass("highcharts-navigator-outline").attr({
                    "stroke-width": b.outlineWidth,
                    stroke: b.outlineColor
                }).add(k);
                b.handles.enabled && v([0, 1], function (c) {
                    b.handles.inverted = e.inverted;
                    a.handles[c] = g.symbol(b.handles.symbols[c], -b.handles.width / 2 - 1, 0, b.handles.width, b.handles.height, b.handles);
                    a.handles[c].attr({zIndex: 7 - c}).addClass("highcharts-navigator-handle highcharts-navigator-handle-" + ["left", "right"][c]).add(k);
                    var d = b.handles;
                    a.handles[c].attr({
                        fill: d.backgroundColor,
                        stroke: d.borderColor,
                        "stroke-width": d.lineWidth
                    }).css(l)
                })
            }, update: function (a) {
                v(this.series || [], function (a) {
                    a.baseSeries &&
                    delete a.baseSeries.navigatorSeries
                });
                this.destroy();
                f(!0, this.chart.options.navigator, this.options, a);
                this.init(this.chart)
            }, render: function (b, c, e, f) {
                var d = this.chart, h, g, k = this.scrollbarHeight, l, m = this.xAxis;
                h = m.fake ? d.xAxis[0] : m;
                var q = this.navigatorEnabled, z, p = this.rendered;
                g = d.inverted;
                var t, r = d.xAxis[0].minRange, y = d.xAxis[0].options.maxRange;
                if (!this.hasDragged || w(e)) {
                    if (!n(b) || !n(c)) if (p) e = 0, f = u(m.width, h.width); else return;
                    this.left = u(m.left, d.plotLeft + k + (g ? d.plotWidth : 0));
                    this.size = z = l = u(m.len,
                        (g ? d.plotHeight : d.plotWidth) - 2 * k);
                    d = g ? k : l + 2 * k;
                    e = u(e, m.toPixels(b, !0));
                    f = u(f, m.toPixels(c, !0));
                    n(e) && Infinity !== Math.abs(e) || (e = 0, f = d);
                    b = m.toValue(e, !0);
                    c = m.toValue(f, !0);
                    t = Math.abs(a.correctFloat(c - b));
                    t < r ? this.grabbedLeft ? e = m.toPixels(c - r, !0) : this.grabbedRight && (f = m.toPixels(b + r, !0)) : w(y) && t > y && (this.grabbedLeft ? e = m.toPixels(c - y, !0) : this.grabbedRight && (f = m.toPixels(b + y, !0)));
                    this.zoomedMax = Math.min(Math.max(e, f, 0), z);
                    this.zoomedMin = Math.min(Math.max(this.fixedWidth ? this.zoomedMax - this.fixedWidth :
                        Math.min(e, f), 0), z);
                    this.range = this.zoomedMax - this.zoomedMin;
                    z = Math.round(this.zoomedMax);
                    e = Math.round(this.zoomedMin);
                    q && (this.navigatorGroup.attr({visibility: "visible"}), p = p && !this.hasDragged ? "animate" : "attr", this.drawMasks(e, z, g, p), this.drawOutline(e, z, g, p), this.navigatorOptions.handles.enabled && (this.drawHandle(e, 0, g, p), this.drawHandle(z, 1, g, p)));
                    this.scrollbar && (g ? (g = this.top - k, h = this.left - k + (q || !h.opposite ? 0 : (h.titleOffset || 0) + h.axisTitleMargin), k = l + 2 * k) : (g = this.top + (q ? this.height : -k), h = this.left -
                        k), this.scrollbar.position(h, g, d, k), this.scrollbar.setRange(this.zoomedMin / l, this.zoomedMax / l));
                    this.rendered = !0
                }
            }, addMouseEvents: function () {
                var a = this, c = a.chart, e = c.container, f = [], g, k;
                a.mouseMoveHandler = g = function (b) {
                    a.onMouseMove(b)
                };
                a.mouseUpHandler = k = function (b) {
                    a.onMouseUp(b)
                };
                f = a.getPartsEvents("mousedown");
                f.push(E(e, "mousemove", g), E(e.ownerDocument, "mouseup", k));
                b && (f.push(E(e, "touchmove", g), E(e.ownerDocument, "touchend", k)), f.concat(a.getPartsEvents("touchstart")));
                a.eventsToUnbind = f;
                a.series &&
                a.series[0] && f.push(E(a.series[0].xAxis, "foundExtremes", function () {
                    c.navigator.modifyNavigatorAxisExtremes()
                }))
            }, getPartsEvents: function (a) {
                var b = this, c = [];
                v(["shades", "handles"], function (d) {
                    v(b[d], function (e, f) {
                        c.push(E(e.element, a, function (a) {
                            b[d + "Mousedown"](a, f)
                        }))
                    })
                });
                return c
            }, shadesMousedown: function (a, b) {
                a = this.chart.pointer.normalize(a);
                var c = this.chart, d = this.xAxis, e = this.zoomedMin, f = this.left, g = this.size, k = this.range,
                    l = a.chartX, m, n;
                c.inverted && (l = a.chartY, f = this.top);
                1 === b ? (this.grabbedCenter =
                    l, this.fixedWidth = k, this.dragOffset = l - e) : (a = l - f - k / 2, 0 === b ? a = Math.max(0, a) : 2 === b && a + k >= g && (a = g - k, d.reversed ? (a -= k, n = this.getUnionExtremes().dataMin) : m = this.getUnionExtremes().dataMax), a !== e && (this.fixedWidth = k, b = d.toFixedRange(a, a + k, n, m), w(b.min) && c.xAxis[0].setExtremes(Math.min(b.min, b.max), Math.max(b.min, b.max), !0, null, {trigger: "navigator"})))
            }, handlesMousedown: function (a, b) {
                this.chart.pointer.normalize(a);
                a = this.chart;
                var c = a.xAxis[0], d = a.inverted && !c.reversed || !a.inverted && c.reversed;
                0 === b ? (this.grabbedLeft =
                    !0, this.otherHandlePos = this.zoomedMax, this.fixedExtreme = d ? c.min : c.max) : (this.grabbedRight = !0, this.otherHandlePos = this.zoomedMin, this.fixedExtreme = d ? c.max : c.min);
                a.fixedRange = null
            }, onMouseMove: function (a) {
                var b = this, c = b.chart, d = b.left, e = b.navigatorSize, f = b.range, g = b.dragOffset,
                    k = c.inverted;
                a.touches && 0 === a.touches[0].pageX || (a = c.pointer.normalize(a), c = a.chartX, k && (d = b.top, c = a.chartY), b.grabbedLeft ? (b.hasDragged = !0, b.render(0, 0, c - d, b.otherHandlePos)) : b.grabbedRight ? (b.hasDragged = !0, b.render(0, 0, b.otherHandlePos,
                    c - d)) : b.grabbedCenter && (b.hasDragged = !0, c < g ? c = g : c > e + g - f && (c = e + g - f), b.render(0, 0, c - g, c - g + f)), b.hasDragged && b.scrollbar && b.scrollbar.options.liveRedraw && (a.DOMType = a.type, setTimeout(function () {
                    b.onMouseUp(a)
                }, 0)))
            }, onMouseUp: function (a) {
                var b = this.chart, c = this.xAxis, d = c && c.reversed, e = this.scrollbar, f, g, k = a.DOMEvent || a;
                (!this.hasDragged || e && e.hasDragged) && "scrollbar" !== a.trigger || (e = this.getUnionExtremes(), this.zoomedMin === this.otherHandlePos ? f = this.fixedExtreme : this.zoomedMax === this.otherHandlePos &&
                    (g = this.fixedExtreme), this.zoomedMax === this.size && (g = d ? e.dataMin : e.dataMax), 0 === this.zoomedMin && (f = d ? e.dataMax : e.dataMin), c = c.toFixedRange(this.zoomedMin, this.zoomedMax, f, g), w(c.min) && b.xAxis[0].setExtremes(Math.min(c.min, c.max), Math.max(c.min, c.max), !0, this.hasDragged ? !1 : null, {
                    trigger: "navigator",
                    triggerOp: "navigator-drag",
                    DOMEvent: k
                }));
                "mousemove" !== a.DOMType && (this.grabbedLeft = this.grabbedRight = this.grabbedCenter = this.fixedWidth = this.fixedExtreme = this.otherHandlePos = this.hasDragged = this.dragOffset =
                    null)
            }, removeEvents: function () {
                this.eventsToUnbind && (v(this.eventsToUnbind, function (a) {
                    a()
                }), this.eventsToUnbind = void 0);
                this.removeBaseSeriesEvents()
            }, removeBaseSeriesEvents: function () {
                var a = this.baseSeries || [];
                this.navigatorEnabled && a[0] && (!1 !== this.navigatorOptions.adaptToUpdatedData && v(a, function (a) {
                    D(a, "updatedData", this.updatedDataHandler)
                }, this), a[0].xAxis && D(a[0].xAxis, "foundExtremes", this.modifyBaseAxisExtremes))
            }, init: function (a) {
                var b = a.options, c = b.navigator, d = c.enabled, e = b.scrollbar, g =
                    e.enabled, b = d ? c.height : 0, k = g ? e.height : 0;
                this.handles = [];
                this.shades = [];
                this.chart = a;
                this.setBaseSeries();
                this.height = b;
                this.scrollbarHeight = k;
                this.scrollbarEnabled = g;
                this.navigatorEnabled = d;
                this.navigatorOptions = c;
                this.scrollbarOptions = e;
                this.outlineHeight = b + k;
                this.opposite = u(c.opposite, !d && a.inverted);
                var l = this, e = l.baseSeries, g = a.xAxis.length, m = a.yAxis.length,
                    n = e && e[0] && e[0].xAxis || a.xAxis[0];
                a.extraMargin = {
                    type: l.opposite ? "plotTop" : "marginBottom",
                    value: (d || !a.inverted ? l.outlineHeight : 0) + c.margin
                };
                a.inverted && (a.extraMargin.type = l.opposite ? "marginRight" : "plotLeft");
                a.isDirtyBox = !0;
                l.navigatorEnabled ? (l.xAxis = new F(a, f({
                    breaks: n.options.breaks,
                    ordinal: n.options.ordinal
                }, c.xAxis, {
                    id: "navigator-x-axis",
                    yAxis: "navigator-y-axis",
                    isX: !0,
                    type: "datetime",
                    index: g,
                    offset: 0,
                    keepOrdinalPadding: !0,
                    startOnTick: !1,
                    endOnTick: !1,
                    minPadding: 0,
                    maxPadding: 0,
                    zoomEnabled: !1
                }, a.inverted ? {offsets: [k, 0, -k, 0], width: b} : {
                    offsets: [0, -k, 0, k],
                    height: b
                })), l.yAxis = new F(a, f(c.yAxis, {
                    id: "navigator-y-axis", alignTicks: !1, offset: 0,
                    index: m, zoomEnabled: !1
                }, a.inverted ? {width: b} : {height: b})), e || c.series.data ? l.updateNavigatorSeries() : 0 === a.series.length && H(a, "redraw", function (b, c) {
                    0 < a.series.length && !l.series && (l.setBaseSeries(), a.redraw = b);
                    b.call(a, c)
                }), l.renderElements(), l.addMouseEvents()) : l.xAxis = {
                    translate: function (b, c) {
                        var d = a.xAxis[0], e = d.getExtremes(), f = d.len - 2 * k,
                            h = J("min", d.options.min, e.dataMin), d = J("max", d.options.max, e.dataMax) - h;
                        return c ? b * d / f + h : f * (b - h) / d
                    }, toPixels: function (a) {
                        return this.translate(a)
                    }, toValue: function (a) {
                        return this.translate(a,
                            !0)
                    }, toFixedRange: F.prototype.toFixedRange, fake: !0
                };
                a.options.scrollbar.enabled && (a.scrollbar = l.scrollbar = new q(a.renderer, f(a.options.scrollbar, {
                    margin: l.navigatorEnabled ? 0 : 10,
                    vertical: a.inverted
                }), a), E(l.scrollbar, "changed", function (b) {
                    var c = l.size, d = c * this.to, c = c * this.from;
                    l.hasDragged = l.scrollbar.hasDragged;
                    l.render(0, 0, c, d);
                    (a.options.scrollbar.liveRedraw || "mousemove" !== b.DOMType && "touchmove" !== b.DOMType) && setTimeout(function () {
                        l.onMouseUp(b)
                    })
                }));
                l.addBaseSeriesEvents();
                l.addChartEvents()
            },
            getUnionExtremes: function (a) {
                var b = this.chart.xAxis[0], c = this.xAxis, d = c.options, e = b.options, f;
                a && null === b.dataMin || (f = {
                    dataMin: u(d && d.min, J("min", e.min, b.dataMin, c.dataMin, c.min)),
                    dataMax: u(d && d.max, J("max", e.max, b.dataMax, c.dataMax, c.max))
                });
                return f
            }, setBaseSeries: function (a, b) {
                var c = this.chart, d = this.baseSeries = [];
                a = a || c.options && c.options.navigator.baseSeries || 0;
                v(c.series || [], function (b, c) {
                    b.options.isInternal || !b.options.showInNavigator && (c !== a && b.options.id !== a || !1 === b.options.showInNavigator) ||
                    d.push(b)
                });
                this.xAxis && !this.xAxis.fake && this.updateNavigatorSeries(b)
            }, updateNavigatorSeries: function (b) {
                var d = this, e = d.chart, h = d.baseSeries, k, m, n = d.navigatorOptions.series, q, p = {
                    enableMouseTracking: !1,
                    index: null,
                    linkedTo: null,
                    group: "nav",
                    padXAxis: !1,
                    xAxis: "navigator-x-axis",
                    yAxis: "navigator-y-axis",
                    showInLegend: !1,
                    stacking: !1,
                    isInternal: !0,
                    visible: !0
                }, t = d.series = a.grep(d.series || [], function (b) {
                    var c = b.baseSeries;
                    return 0 > a.inArray(c, h) ? (c && (D(c, "updatedData", d.updatedDataHandler), delete c.navigatorSeries),
                        b.destroy(), !1) : !0
                });
                h && h.length && v(h, function (a) {
                    var z = a.navigatorSeries, r = c({color: a.color}, g(n) ? l.navigator.series : n);
                    z && !1 === d.navigatorOptions.adaptToUpdatedData || (p.name = "Navigator " + h.length, k = a.options || {}, q = k.navigatorOptions || {}, m = f(k, p, r, q), r = q.data || r.data, d.hasNavigatorData = d.hasNavigatorData || !!r, m.data = r || k.data && k.data.slice(0), z && z.options ? z.update(m, b) : (a.navigatorSeries = e.initSeries(m), a.navigatorSeries.baseSeries = a, t.push(a.navigatorSeries)))
                });
                if (n.data && (!h || !h.length) || g(n)) d.hasNavigatorData =
                    !1, n = a.splat(n), v(n, function (a, b) {
                    p.name = "Navigator " + (t.length + 1);
                    m = f(l.navigator.series, {color: e.series[b] && !e.series[b].options.isInternal && e.series[b].color || e.options.colors[b] || e.options.colors[0]}, p, a);
                    m.data = a.data;
                    m.data && (d.hasNavigatorData = !0, t.push(e.initSeries(m)))
                });
                this.addBaseSeriesEvents()
            }, addBaseSeriesEvents: function () {
                var a = this, b = a.baseSeries || [];
                b[0] && b[0].xAxis && E(b[0].xAxis, "foundExtremes", this.modifyBaseAxisExtremes);
                v(b, function (b) {
                    E(b, "show", function () {
                        this.navigatorSeries &&
                        this.navigatorSeries.setVisible(!0, !1)
                    });
                    E(b, "hide", function () {
                        this.navigatorSeries && this.navigatorSeries.setVisible(!1, !1)
                    });
                    !1 !== this.navigatorOptions.adaptToUpdatedData && b.xAxis && E(b, "updatedData", this.updatedDataHandler);
                    E(b, "remove", function () {
                        this.navigatorSeries && (B(a.series, this.navigatorSeries), this.navigatorSeries.remove(!1), delete this.navigatorSeries)
                    })
                }, this)
            }, modifyNavigatorAxisExtremes: function () {
                var a = this.xAxis, b;
                a.getExtremes && (!(b = this.getUnionExtremes(!0)) || b.dataMin === a.min &&
                    b.dataMax === a.max || (a.min = b.dataMin, a.max = b.dataMax))
            }, modifyBaseAxisExtremes: function () {
                var a = this.chart.navigator, b = this.getExtremes(), c = b.dataMin, e = b.dataMax, b = b.max - b.min,
                    f = a.stickToMin, g = a.stickToMax, k = this.options.overscroll, l, m, q = a.series && a.series[0],
                    p = !!this.setExtremes;
                this.eventArgs && "rangeSelectorButton" === this.eventArgs.trigger || (f && (m = c, l = m + b), g && (l = e + k, f || (m = Math.max(l - b, q && q.xData ? q.xData[0] : -Number.MAX_VALUE))), p && (f || g) && n(m) && (this.min = this.userMin = m, this.max = this.userMax = l));
                a.stickToMin =
                    a.stickToMax = null
            }, updatedDataHandler: function () {
                var a = this.chart.navigator, b = this.navigatorSeries;
                a.stickToMax = a.xAxis.reversed ? 0 === Math.round(a.zoomedMin) : Math.round(a.zoomedMax) >= Math.round(a.size);
                a.stickToMin = n(this.xAxis.min) && this.xAxis.min <= this.xData[0] && (!this.chart.fixedRange || !a.stickToMax);
                b && !a.hasNavigatorData && (b.options.pointStart = this.xData[0], b.setData(this.options.data, !1, null, !1))
            }, addChartEvents: function () {
                E(this.chart, "redraw", function () {
                    var a = this.navigator, b = a && (a.baseSeries &&
                        a.baseSeries[0] && a.baseSeries[0].xAxis || a.scrollbar && this.xAxis[0]);
                    b && a.render(b.min, b.max)
                })
            }, destroy: function () {
                this.removeEvents();
                this.xAxis && (B(this.chart.xAxis, this.xAxis), B(this.chart.axes, this.xAxis));
                this.yAxis && (B(this.chart.yAxis, this.yAxis), B(this.chart.axes, this.yAxis));
                v(this.series || [], function (a) {
                    a.destroy && a.destroy()
                });
                v("series xAxis yAxis shades outline scrollbarTrack scrollbarRifles scrollbarGroup scrollbar navigatorGroup rendered".split(" "), function (a) {
                    this[a] && this[a].destroy &&
                    this[a].destroy();
                    this[a] = null
                }, this);
                v([this.handles], function (a) {
                    p(a)
                }, this)
            }
        };
        a.Navigator = C;
        H(F.prototype, "zoom", function (a, b, c) {
            var d = this.chart, e = d.options, f = e.chart.zoomType, g = e.navigator, e = e.rangeSelector, k;
            this.isXAxis && (g && g.enabled || e && e.enabled) && ("x" === f ? d.resetZoomButton = "blocked" : "y" === f ? k = !1 : "xy" === f && this.options.range && (d = this.previousZoom, w(b) ? this.previousZoom = [this.min, this.max] : d && (b = d[0], c = d[1], delete this.previousZoom)));
            return void 0 !== k ? k : a.call(this, b, c)
        });
        H(r.prototype,
            "init", function (a, b, c) {
                E(this, "beforeRender", function () {
                    var a = this.options;
                    if (a.navigator.enabled || a.scrollbar.enabled) this.scroller = this.navigator = new C(this)
                });
                a.call(this, b, c)
            });
        H(r.prototype, "setChartSize", function (a) {
            var b = this.legend, c = this.navigator, d, e, f, g;
            a.apply(this, [].slice.call(arguments, 1));
            c && (e = b && b.options, f = c.xAxis, g = c.yAxis, d = c.scrollbarHeight, this.inverted ? (c.left = c.opposite ? this.chartWidth - d - c.height : this.spacing[3] + d, c.top = this.plotTop + d) : (c.left = this.plotLeft + d, c.top = c.navigatorOptions.top ||
                this.chartHeight - c.height - d - this.spacing[2] - (this.rangeSelector && this.extraBottomMargin ? this.rangeSelector.getHeight() : 0) - (e && "bottom" === e.verticalAlign && e.enabled && !e.floating ? b.legendHeight + u(e.margin, 10) : 0)), f && g && (this.inverted ? f.options.left = g.options.left = c.left : f.options.top = g.options.top = c.top, f.setAxisSize(), g.setAxisSize()))
        });
        H(A.prototype, "addPoint", function (a, b, c, f, g) {
            var d = this.options.turboThreshold;
            d && this.xData.length > d && t(b, !0) && this.chart.navigator && e(20, !0);
            a.call(this, b, c, f, g)
        });
        H(r.prototype, "addSeries", function (a, b, c, e) {
            a = a.call(this, b, !1, e);
            this.navigator && this.navigator.setBaseSeries(null, !1);
            u(c, !0) && this.redraw();
            return a
        });
        H(A.prototype, "update", function (a, b, c) {
            a.call(this, b, !1);
            this.chart.navigator && !this.options.isInternal && this.chart.navigator.setBaseSeries(null, !1);
            u(c, !0) && this.chart.redraw()
        });
        r.prototype.callbacks.push(function (a) {
            var b = a.navigator;
            b && (a = a.xAxis[0].getExtremes(), b.render(a.min, a.max))
        })
    })(L);
    (function (a) {
        function C(a) {
            this.init(a)
        }

        var E = a.addEvent,
            F = a.Axis, r = a.Chart, m = a.css, l = a.createElement, w = a.defaultOptions, p = a.defined,
            v = a.destroyObjectProperties, B = a.discardElement, e = a.each, c = a.extend, k = a.fireEvent,
            b = a.isNumber, g = a.merge, n = a.pick, t = a.pInt, f = a.splat, u = a.wrap;
        c(w, {
            rangeSelector: {
                verticalAlign: "top",
                buttonTheme: {"stroke-width": 0, width: 28, height: 18, padding: 2, zIndex: 7},
                floating: !1,
                x: 0,
                y: 0,
                height: void 0,
                inputPosition: {align: "right", x: 0, y: 0},
                buttonPosition: {align: "left", x: 0, y: 0},
                labelStyle: {color: "#666666"}
            }
        });
        w.lang = g(w.lang, {
            rangeSelectorZoom: "Zoom",
            rangeSelectorFrom: "From", rangeSelectorTo: "To"
        });
        C.prototype = {
            clickButton: function (a, c) {
                var g = this, k = g.chart, l = g.buttonOptions[a], m = k.xAxis[0],
                    q = k.scroller && k.scroller.getUnionExtremes() || m || {}, d = q.dataMin, p = q.dataMax, t,
                    h = m && Math.round(Math.min(m.max, n(p, m.max))), r = l.type, u, q = l._range, v, w, D,
                    B = l.dataGrouping;
                if (null !== d && null !== p) {
                    k.fixedRange = q;
                    B && (this.forcedDataGrouping = !0, F.prototype.setDataGrouping.call(m || {chart: this.chart}, B, !1));
                    if ("month" === r || "year" === r) m ? (r = {
                        range: l, max: h, chart: k, dataMin: d,
                        dataMax: p
                    }, t = m.minFromRange.call(r), b(r.newMax) && (h = r.newMax)) : q = l; else if (q) t = Math.max(h - q, d), h = Math.min(t + q, p); else if ("ytd" === r) if (m) void 0 === p && (d = Number.MAX_VALUE, p = Number.MIN_VALUE, e(k.series, function (a) {
                        a = a.xData;
                        d = Math.min(a[0], d);
                        p = Math.max(a[a.length - 1], p)
                    }), c = !1), h = g.getYTDExtremes(p, d, k.time.useUTC), t = v = h.min, h = h.max; else {
                        E(k, "beforeRender", function () {
                            g.clickButton(a)
                        });
                        return
                    } else "all" === r && m && (t = d, h = p);
                    t += l._offsetMin;
                    h += l._offsetMax;
                    g.setSelected(a);
                    m ? m.setExtremes(t, h, n(c, 1), null,
                        {
                            trigger: "rangeSelectorButton",
                            rangeSelectorButton: l
                        }) : (u = f(k.options.xAxis)[0], D = u.range, u.range = q, w = u.min, u.min = v, E(k, "load", function () {
                        u.range = D;
                        u.min = w
                    }))
                }
            },
            setSelected: function (a) {
                this.selected = this.options.selected = a
            },
            defaultButtons: [{type: "month", count: 1, text: "1m"}, {
                type: "month",
                count: 3,
                text: "3m"
            }, {type: "month", count: 6, text: "6m"}, {type: "ytd", text: "YTD"}, {
                type: "year",
                count: 1,
                text: "1y"
            }, {type: "all", text: "All"}],
            init: function (a) {
                var b = this, c = a.options.rangeSelector, f = c.buttons || [].concat(b.defaultButtons),
                    g = c.selected, l = function () {
                        var a = b.minInput, c = b.maxInput;
                        a && a.blur && k(a, "blur");
                        c && c.blur && k(c, "blur")
                    };
                b.chart = a;
                b.options = c;
                b.buttons = [];
                a.extraTopMargin = c.height;
                b.buttonOptions = f;
                this.unMouseDown = E(a.container, "mousedown", l);
                this.unResize = E(a, "resize", l);
                e(f, b.computeButtonRange);
                void 0 !== g && f[g] && this.clickButton(g, !1);
                E(a, "load", function () {
                    a.xAxis && a.xAxis[0] && E(a.xAxis[0], "setExtremes", function (c) {
                        this.max - this.min !== a.fixedRange && "rangeSelectorButton" !== c.trigger && "updatedData" !== c.trigger &&
                        b.forcedDataGrouping && this.setDataGrouping(!1, !1)
                    })
                })
            },
            updateButtonStates: function () {
                var a = this.chart, c = a.xAxis[0], f = Math.round(c.max - c.min), g = !c.hasVisibleSeries,
                    k = a.scroller && a.scroller.getUnionExtremes() || c, l = k.dataMin, m = k.dataMax,
                    a = this.getYTDExtremes(m, l, a.time.useUTC), d = a.min, n = a.max, p = this.selected, h = b(p),
                    t = this.options.allButtonsEnabled, r = this.buttons;
                e(this.buttonOptions, function (a, b) {
                    var e = a._range, k = a.type, q = a.count || 1, y = r[b], A = 0;
                    a = a._offsetMax - a._offsetMin;
                    b = b === p;
                    var u = e > m - l, z = e < c.minRange,
                        v = !1, x = !1, e = e === f;
                    ("month" === k || "year" === k) && f + 36E5 >= 864E5 * {
                        month: 28,
                        year: 365
                    }[k] * q - a && f - 36E5 <= 864E5 * {
                        month: 31,
                        year: 366
                    }[k] * q + a ? e = !0 : "ytd" === k ? (e = n - d + a === f, v = !b) : "all" === k && (e = c.max - c.min >= m - l, x = !b && h && e);
                    k = !t && (u || z || x || g);
                    q = b && e || e && !h && !v;
                    k ? A = 3 : q && (h = !0, A = 2);
                    y.state !== A && y.setState(A)
                })
            },
            computeButtonRange: function (a) {
                var b = a.type, c = a.count || 1,
                    e = {millisecond: 1, second: 1E3, minute: 6E4, hour: 36E5, day: 864E5, week: 6048E5};
                if (e[b]) a._range = e[b] * c; else if ("month" === b || "year" === b) a._range = 864E5 * {
                    month: 30,
                    year: 365
                }[b] * c;
                a._offsetMin = n(a.offsetMin, 0);
                a._offsetMax = n(a.offsetMax, 0);
                a._range += a._offsetMax - a._offsetMin
            },
            setInputValue: function (a, b) {
                var c = this.chart.options.rangeSelector, e = this.chart.time, f = this[a + "Input"];
                p(b) && (f.previousValue = f.HCTime, f.HCTime = b);
                f.value = e.dateFormat(c.inputEditDateFormat || "%Y-%m-%d", f.HCTime);
                this[a + "DateBox"].attr({text: e.dateFormat(c.inputDateFormat || "%b %e, %Y", f.HCTime)})
            },
            showInput: function (a) {
                var b = this.inputGroup, c = this[a + "DateBox"];
                m(this[a + "Input"], {
                    left: b.translateX +
                    c.x + "px",
                    top: b.translateY + "px",
                    width: c.width - 2 + "px",
                    height: c.height - 2 + "px",
                    border: "2px solid silver"
                })
            },
            hideInput: function (a) {
                m(this[a + "Input"], {border: 0, width: "1px", height: "1px"});
                this.setInputValue(a)
            },
            drawInput: function (a) {
                function e() {
                    var a = v.value, c = (r.inputDateParser || Date.parse)(a), d = k.xAxis[0],
                        e = k.scroller && k.scroller.xAxis ? k.scroller.xAxis : d, g = e.dataMin, e = e.dataMax;
                    c !== v.previousValue && (v.previousValue = c, b(c) || (c = a.split("-"), c = Date.UTC(t(c[0]), t(c[1]) - 1, t(c[2]))), b(c) && (k.time.useUTC || (c +=
                        6E4 * (new Date).getTimezoneOffset()), u ? c > f.maxInput.HCTime ? c = void 0 : c < g && (c = g) : c < f.minInput.HCTime ? c = void 0 : c > e && (c = e), void 0 !== c && d.setExtremes(u ? c : d.min, u ? d.max : c, void 0, void 0, {trigger: "rangeSelectorInput"})))
                }

                var f = this, k = f.chart, n = k.renderer.style || {}, p = k.renderer, r = k.options.rangeSelector,
                    d = f.div, u = "min" === a, v, h, x = this.inputGroup;
                this[a + "Label"] = h = p.label(w.lang[u ? "rangeSelectorFrom" : "rangeSelectorTo"], this.inputGroup.offset).addClass("highcharts-range-label").attr({padding: 2}).add(x);
                x.offset +=
                    h.width + 5;
                this[a + "DateBox"] = p = p.label("", x.offset).addClass("highcharts-range-input").attr({
                    padding: 2,
                    width: r.inputBoxWidth || 90,
                    height: r.inputBoxHeight || 17,
                    stroke: r.inputBoxBorderColor || "#cccccc",
                    "stroke-width": 1,
                    "text-align": "center"
                }).on("click", function () {
                    f.showInput(a);
                    f[a + "Input"].focus()
                }).add(x);
                x.offset += p.width + (u ? 10 : 0);
                this[a + "Input"] = v = l("input", {
                    name: a,
                    className: "highcharts-range-selector",
                    type: "text"
                }, {top: k.plotTop + "px"}, d);
                h.css(g(n, r.labelStyle));
                p.css(g({color: "#333333"}, n, r.inputStyle));
                m(v, c({
                    position: "absolute",
                    border: 0,
                    width: "1px",
                    height: "1px",
                    padding: 0,
                    textAlign: "center",
                    fontSize: n.fontSize,
                    fontFamily: n.fontFamily,
                    top: "-9999em"
                }, r.inputStyle));
                v.onfocus = function () {
                    f.showInput(a)
                };
                v.onblur = function () {
                    f.hideInput(a)
                };
                v.onchange = e;
                v.onkeypress = function (a) {
                    13 === a.keyCode && e()
                }
            },
            getPosition: function () {
                var a = this.chart, b = a.options.rangeSelector,
                    a = "top" === b.verticalAlign ? a.plotTop - a.axisOffset[0] : 0;
                return {buttonTop: a + b.buttonPosition.y, inputTop: a + b.inputPosition.y - 10}
            },
            getYTDExtremes: function (a,
                                      b, c) {
                var e = this.chart.time, f = new e.Date(a), g = e.get("FullYear", f);
                c = c ? e.Date.UTC(g, 0, 1) : +new e.Date(g, 0, 1);
                b = Math.max(b || 0, c);
                f = f.getTime();
                return {max: Math.min(a || f, f), min: b}
            },
            render: function (a, b) {
                var c = this, f = c.chart, g = f.renderer, k = f.container, m = f.options,
                    d = m.exporting && !1 !== m.exporting.enabled && m.navigation && m.navigation.buttonOptions,
                    q = w.lang, p = c.div, h = m.rangeSelector, m = h.floating, t = c.buttons, p = c.inputGroup,
                    r = h.buttonTheme, u = h.buttonPosition, v = h.inputPosition, B = h.inputEnabled, D = r && r.states,
                    C = f.plotLeft,
                    F, E = c.buttonGroup, L;
                L = c.rendered;
                var W = c.options.verticalAlign, Z = f.legend, aa = Z && Z.options, ba = u.y, X = v.y, ca = L || !1,
                    U = 0, R = 0, S;
                if (!1 !== h.enabled) {
                    L || (c.group = L = g.g("range-selector-group").attr({zIndex: 7}).add(), c.buttonGroup = E = g.g("range-selector-buttons").add(L), c.zoomText = g.text(q.rangeSelectorZoom, n(C + u.x, C), 15).css(h.labelStyle).add(E), F = n(C + u.x, C) + c.zoomText.getBBox().width + 5, e(c.buttonOptions, function (a, b) {
                        t[b] = g.button(a.text, F, 0, function () {
                            var d = a.events && a.events.click, e;
                            d && (e = d.call(a));
                            !1 !==
                            e && c.clickButton(b);
                            c.isActive = !0
                        }, r, D && D.hover, D && D.select, D && D.disabled).attr({"text-align": "center"}).add(E);
                        F += t[b].width + n(h.buttonSpacing, 5)
                    }), !1 !== B && (c.div = p = l("div", null, {
                        position: "relative",
                        height: 0,
                        zIndex: 1
                    }), k.parentNode.insertBefore(p, k), c.inputGroup = p = g.g("input-group").add(L), p.offset = 0, c.drawInput("min"), c.drawInput("max")));
                    C = f.plotLeft - f.spacing[3];
                    c.updateButtonStates();
                    d && this.titleCollision(f) && "top" === W && "right" === u.align && u.y + E.getBBox().height - 12 < (d.y || 0) + d.height && (U = -40);
                    "left" === u.align ? S = u.x - f.spacing[3] : "right" === u.align && (S = u.x + U - f.spacing[1]);
                    E.align({y: u.y, width: E.getBBox().width, align: u.align, x: S}, !0, f.spacingBox);
                    c.group.placed = ca;
                    c.buttonGroup.placed = ca;
                    !1 !== B && (U = d && this.titleCollision(f) && "top" === W && "right" === v.align && v.y - p.getBBox().height - 12 < (d.y || 0) + d.height + f.spacing[0] ? -40 : 0, "left" === v.align ? S = C : "right" === v.align && (S = -Math.max(f.axisOffset[1], -U)), p.align({
                        y: v.y,
                        width: p.getBBox().width,
                        align: v.align,
                        x: v.x + S - 2
                    }, !0, f.spacingBox), k = p.alignAttr.translateX +
                        p.alignOptions.x - U + p.getBBox().x + 2, d = p.alignOptions.width, q = E.alignAttr.translateX + E.getBBox().x, S = E.getBBox().width + 20, (v.align === u.align || q + S > k && k + d > q && ba < X + p.getBBox().height) && p.attr({
                        translateX: p.alignAttr.translateX + (f.axisOffset[1] >= -U ? 0 : -U),
                        translateY: p.alignAttr.translateY + E.getBBox().height + 10
                    }), c.setInputValue("min", a), c.setInputValue("max", b), c.inputGroup.placed = ca);
                    c.group.align({verticalAlign: W}, !0, f.spacingBox);
                    a = c.group.getBBox().height + 20;
                    b = c.group.alignAttr.translateY;
                    "bottom" ===
                    W && (Z = aa && "bottom" === aa.verticalAlign && aa.enabled && !aa.floating ? Z.legendHeight + n(aa.margin, 10) : 0, a = a + Z - 20, R = b - a - (m ? 0 : h.y) - 10);
                    if ("top" === W) m && (R = 0), f.titleOffset && (R = f.titleOffset + f.options.title.margin), R += f.margin[0] - f.spacing[0] || 0; else if ("middle" === W) if (X === ba) R = 0 > X ? b + void 0 : b; else if (X || ba) R = 0 > X || 0 > ba ? R - Math.min(X, ba) : b - a + NaN;
                    c.group.translate(h.x, h.y + Math.floor(R));
                    !1 !== B && (c.minInput.style.marginTop = c.group.translateY + "px", c.maxInput.style.marginTop = c.group.translateY + "px");
                    c.rendered = !0
                }
            },
            getHeight: function () {
                var a = this.options, b = this.group, c = a.y, e = a.buttonPosition.y, a = a.inputPosition.y,
                    b = b ? b.getBBox(!0).height + 13 + c : 0, c = Math.min(a, e);
                if (0 > a && 0 > e || 0 < a && 0 < e) b += Math.abs(c);
                return b
            },
            titleCollision: function (a) {
                return !(a.options.title.text || a.options.subtitle.text)
            },
            update: function (a) {
                var b = this.chart;
                g(!0, b.options.rangeSelector, a);
                this.destroy();
                this.init(b);
                b.rangeSelector.render()
            },
            destroy: function () {
                var b = this, c = b.minInput, e = b.maxInput;
                b.unMouseDown();
                b.unResize();
                v(b.buttons);
                c &&
                (c.onfocus = c.onblur = c.onchange = null);
                e && (e.onfocus = e.onblur = e.onchange = null);
                a.objectEach(b, function (a, c) {
                    a && "chart" !== c && (a.destroy ? a.destroy() : a.nodeType && B(this[c]));
                    a !== C.prototype[c] && (b[c] = null)
                }, this)
            }
        };
        F.prototype.toFixedRange = function (a, c, e, f) {
            var g = this.chart && this.chart.fixedRange;
            a = n(e, this.translate(a, !0, !this.horiz));
            c = n(f, this.translate(c, !0, !this.horiz));
            e = g && (c - a) / g;
            .7 < e && 1.3 > e && (f ? a = c - g : c = a + g);
            b(a) && b(c) || (a = c = void 0);
            return {min: a, max: c}
        };
        F.prototype.minFromRange = function () {
            var a =
                this.range, c = {month: "Month", year: "FullYear"}[a.type], e, f = this.max, g, k, l = function (a, b) {
                var d = new Date(a), e = d["get" + c]();
                d["set" + c](e + b);
                e === d["get" + c]() && d.setDate(0);
                return d.getTime() - a
            };
            b(a) ? (e = f - a, k = a) : (e = f + l(f, -a.count), this.chart && (this.chart.fixedRange = f - e));
            g = n(this.dataMin, Number.MIN_VALUE);
            b(e) || (e = g);
            e <= g && (e = g, void 0 === k && (k = l(e, a.count)), this.newMax = Math.min(e + k, this.dataMax));
            b(f) || (e = void 0);
            return e
        };
        u(r.prototype, "init", function (a, b, c) {
            E(this, "init", function () {
                this.options.rangeSelector.enabled &&
                (this.rangeSelector = new C(this))
            });
            a.call(this, b, c)
        });
        u(r.prototype, "render", function (a, b, c) {
            var f = this.axes, g = this.rangeSelector;
            g && (e(f, function (a) {
                a.updateNames();
                a.setScale()
            }), this.getAxisMargins(), g.render(), f = g.options.verticalAlign, g.options.floating || ("bottom" === f ? this.extraBottomMargin = !0 : "middle" !== f && (this.extraTopMargin = !0)));
            a.call(this, b, c)
        });
        u(r.prototype, "update", function (b, c, e, f) {
            var g = this.rangeSelector, k;
            this.extraTopMargin = this.extraBottomMargin = !1;
            g && (g.render(), k = c.rangeSelector &&
                c.rangeSelector.verticalAlign || g.options && g.options.verticalAlign, g.options.floating || ("bottom" === k ? this.extraBottomMargin = !0 : "middle" !== k && (this.extraTopMargin = !0)));
            b.call(this, a.merge(!0, c, {
                chart: {
                    marginBottom: n(c.chart && c.chart.marginBottom, this.margin.bottom),
                    spacingBottom: n(c.chart && c.chart.spacingBottom, this.spacing.bottom)
                }
            }), e, f)
        });
        u(r.prototype, "redraw", function (a, b, c) {
            var e = this.rangeSelector;
            e && !e.options.floating && (e.render(), e = e.options.verticalAlign, "bottom" === e ? this.extraBottomMargin =
                !0 : "middle" !== e && (this.extraTopMargin = !0));
            a.call(this, b, c)
        });
        r.prototype.adjustPlotArea = function () {
            var a = this.rangeSelector;
            this.rangeSelector && (a = a.getHeight(), this.extraTopMargin && (this.plotTop += a), this.extraBottomMargin && (this.marginBottom += a))
        };
        r.prototype.callbacks.push(function (a) {
            function c() {
                e = a.xAxis[0].getExtremes();
                b(e.min) && f.render(e.min, e.max)
            }

            var e, f = a.rangeSelector, g, k;
            f && (k = E(a.xAxis[0], "afterSetExtremes", function (a) {
                f.render(a.min, a.max)
            }), g = E(a, "redraw", c), c());
            E(a, "destroy", function () {
                f &&
                (g(), k())
            })
        });
        a.RangeSelector = C
    })(L);
    (function (a) {
        var C = a.arrayMax, E = a.arrayMin, F = a.Axis, r = a.Chart, m = a.defined, l = a.each, w = a.extend,
            p = a.format, v = a.grep, B = a.inArray, e = a.isNumber, c = a.isString, k = a.map, b = a.merge, g = a.pick,
            n = a.Point, t = a.Renderer, f = a.Series, u = a.splat, D = a.SVGRenderer, q = a.VMLRenderer, A = a.wrap,
            y = f.prototype, H = y.init, I = y.processData, J = n.prototype.tooltipFormatter;
        a.StockChart = a.stockChart = function (d, e, f) {
            var h = c(d) || d.nodeName, l = arguments[h ? 1 : 0], m = l.series, n = a.getOptions(), p,
                q = g(l.navigator &&
                    l.navigator.enabled, n.navigator.enabled, !0), t = q ? {startOnTick: !1, endOnTick: !1} : null,
                v = {marker: {enabled: !1, radius: 2}}, z = {shadow: !1, borderWidth: 0};
            l.xAxis = k(u(l.xAxis || {}), function (a, c) {
                return b({
                    minPadding: 0,
                    maxPadding: 0,
                    overscroll: 0,
                    ordinal: !0,
                    title: {text: null},
                    labels: {overflow: "justify"},
                    showLastLabel: !0
                }, n.xAxis, n.xAxis && n.xAxis[c], a, {type: "datetime", categories: null}, t)
            });
            l.yAxis = k(u(l.yAxis || {}), function (a, c) {
                p = g(a.opposite, !0);
                return b({
                    labels: {y: -2}, opposite: p, showLastLabel: !(!a.categories && "category" !==
                        a.type), title: {text: null}
                }, n.yAxis, n.yAxis && n.yAxis[c], a)
            });
            l.series = null;
            l = b({
                chart: {panning: !0, pinchType: "x"},
                navigator: {enabled: q},
                scrollbar: {enabled: g(n.scrollbar.enabled, !0)},
                rangeSelector: {enabled: g(n.rangeSelector.enabled, !0)},
                title: {text: null},
                tooltip: {split: g(n.tooltip.split, !0), crosshairs: !0},
                legend: {enabled: !1},
                plotOptions: {
                    line: v,
                    spline: v,
                    area: v,
                    areaspline: v,
                    arearange: v,
                    areasplinerange: v,
                    column: z,
                    columnrange: z,
                    candlestick: z,
                    ohlc: z
                }
            }, l, {isStock: !0});
            l.series = m;
            return h ? new r(d, l, f) : new r(l,
                e)
        };
        A(F.prototype, "autoLabelAlign", function (a) {
            var b = this.chart, c = this.options, b = b._labelPanes = b._labelPanes || {}, d = this.options.labels;
            return this.chart.options.isStock && "yAxis" === this.coll && (c = c.top + "," + c.height, !b[c] && d.enabled) ? (15 === d.x && (d.x = 0), void 0 === d.align && (d.align = "right"), b[c] = this, "right") : a.apply(this, [].slice.call(arguments, 1))
        });
        A(F.prototype, "destroy", function (a) {
            var b = this.chart, c = this.options && this.options.top + "," + this.options.height;
            c && b._labelPanes && b._labelPanes[c] === this &&
            delete b._labelPanes[c];
            return a.apply(this, Array.prototype.slice.call(arguments, 1))
        });
        A(F.prototype, "getPlotLinePath", function (b, f, n, h, p, q) {
            var d = this, t = this.isLinked && !this.series ? this.linkedParent.series : this.series, r = d.chart,
                u = r.renderer, v = d.left, z = d.top, x, w, y, A, C = [], G = [], D, E;
            if ("xAxis" !== d.coll && "yAxis" !== d.coll) return b.apply(this, [].slice.call(arguments, 1));
            G = function (a) {
                var b = "xAxis" === a ? "yAxis" : "xAxis";
                a = d.options[b];
                return e(a) ? [r[b][a]] : c(a) ? [r.get(a)] : k(t, function (a) {
                    return a[b]
                })
            }(d.coll);
            l(d.isXAxis ? r.yAxis : r.xAxis, function (a) {
                if (m(a.options.id) ? -1 === a.options.id.indexOf("navigator") : 1) {
                    var b = a.isXAxis ? "yAxis" : "xAxis", b = m(a.options[b]) ? r[b][a.options[b]] : r[b][0];
                    d === b && G.push(a)
                }
            });
            D = G.length ? [] : [d.isXAxis ? r.yAxis[0] : r.xAxis[0]];
            l(G, function (b) {
                -1 !== B(b, D) || a.find(D, function (a) {
                    return a.pos === b.pos && a.len && b.len
                }) || D.push(b)
            });
            E = g(q, d.translate(f, null, null, h));
            e(E) && (d.horiz ? l(D, function (a) {
                var b;
                w = a.pos;
                A = w + a.len;
                x = y = Math.round(E + d.transB);
                if (x < v || x > v + d.width) p ? x = y = Math.min(Math.max(v,
                    x), v + d.width) : b = !0;
                b || C.push("M", x, w, "L", y, A)
            }) : l(D, function (a) {
                var b;
                x = a.pos;
                y = x + a.len;
                w = A = Math.round(z + d.height - E);
                if (w < z || w > z + d.height) p ? w = A = Math.min(Math.max(z, w), d.top + d.height) : b = !0;
                b || C.push("M", x, w, "L", y, A)
            }));
            return 0 < C.length ? u.crispPolyLine(C, n || 1) : null
        });
        D.prototype.crispPolyLine = function (a, b) {
            var c;
            for (c = 0; c < a.length; c += 6) a[c + 1] === a[c + 4] && (a[c + 1] = a[c + 4] = Math.round(a[c + 1]) - b % 2 / 2), a[c + 2] === a[c + 5] && (a[c + 2] = a[c + 5] = Math.round(a[c + 2]) + b % 2 / 2);
            return a
        };
        t === q && (q.prototype.crispPolyLine = D.prototype.crispPolyLine);
        A(F.prototype, "hideCrosshair", function (a, b) {
            a.call(this, b);
            this.crossLabel && (this.crossLabel = this.crossLabel.hide())
        });
        A(F.prototype, "drawCrosshair", function (a, b, c) {
            var d, e;
            a.call(this, b, c);
            if (m(this.crosshair.label) && this.crosshair.label.enabled && this.cross) {
                a = this.chart;
                var f = this.options.crosshair.label, k = this.horiz;
                d = this.opposite;
                e = this.left;
                var l = this.top, n = this.crossLabel, q, r = f.format, t = "",
                    u = "inside" === this.options.tickPosition, v = !1 !== this.crosshair.snap, z = 0;
                b || (b = this.cross && this.cross.e);
                q = k ? "center" : d ? "right" === this.labelAlign ? "right" : "left" : "left" === this.labelAlign ? "left" : "center";
                n || (n = this.crossLabel = a.renderer.label(null, null, null, f.shape || "callout").addClass("highcharts-crosshair-label" + (this.series[0] && " highcharts-color-" + this.series[0].colorIndex)).attr({
                    align: f.align || q,
                    padding: g(f.padding, 8),
                    r: g(f.borderRadius, 3),
                    zIndex: 2
                }).add(this.labelGroup), n.attr({
                    fill: f.backgroundColor || this.series[0] && this.series[0].color || "#666666",
                    stroke: f.borderColor || "",
                    "stroke-width": f.borderWidth ||
                    0
                }).css(w({color: "#ffffff", fontWeight: "normal", fontSize: "11px", textAlign: "center"}, f.style)));
                k ? (q = v ? c.plotX + e : b.chartX, l += d ? 0 : this.height) : (q = d ? this.width + e : 0, l = v ? c.plotY + l : b.chartY);
                r || f.formatter || (this.isDatetimeAxis && (t = "%b %d, %Y"), r = "{value" + (t ? ":" + t : "") + "}");
                b = v ? c[this.isXAxis ? "x" : "y"] : this.toValue(k ? b.chartX : b.chartY);
                n.attr({
                    text: r ? p(r, {value: b}, a.time) : f.formatter.call(this, b),
                    x: q,
                    y: l,
                    visibility: b < this.min || b > this.max ? "hidden" : "visible"
                });
                b = n.getBBox();
                if (k) {
                    if (u && !d || !u && d) l = n.y - b.height
                } else l =
                    n.y - b.height / 2;
                k ? (d = e - b.x, e = e + this.width - b.x) : (d = "left" === this.labelAlign ? e : 0, e = "right" === this.labelAlign ? e + this.width : a.chartWidth);
                n.translateX < d && (z = d - n.translateX);
                n.translateX + b.width >= e && (z = -(n.translateX + b.width - e));
                n.attr({
                    x: q + z,
                    y: l,
                    anchorX: k ? q : this.opposite ? 0 : a.chartWidth,
                    anchorY: k ? this.opposite ? a.chartHeight : 0 : l + b.height / 2
                })
            }
        });
        y.init = function () {
            H.apply(this, arguments);
            this.setCompare(this.options.compare)
        };
        y.setCompare = function (a) {
            this.modifyValue = "value" === a || "percent" === a ? function (b,
                                                                            c) {
                var d = this.compareValue;
                if (void 0 !== b && void 0 !== d) return b = "value" === a ? b - d : b / d * 100 - (100 === this.options.compareBase ? 0 : 100), c && (c.change = b), b
            } : null;
            this.userOptions.compare = a;
            this.chart.hasRendered && (this.isDirty = !0)
        };
        y.processData = function () {
            var a, b = -1, c, f, g = !0 === this.options.compareStart ? 0 : 1, k, l;
            I.apply(this, arguments);
            if (this.xAxis && this.processedYData) for (c = this.processedXData, f = this.processedYData, k = f.length, this.pointArrayMap && (b = B("close", this.pointArrayMap), -1 === b && (b = B(this.pointValKey ||
                "y", this.pointArrayMap))), a = 0; a < k - g; a++) if (l = f[a] && -1 < b ? f[a][b] : f[a], e(l) && c[a + g] >= this.xAxis.min && 0 !== l) {
                this.compareValue = l;
                break
            }
        };
        A(y, "getExtremes", function (a) {
            var b;
            a.apply(this, [].slice.call(arguments, 1));
            this.modifyValue && (b = [this.modifyValue(this.dataMin), this.modifyValue(this.dataMax)], this.dataMin = E(b), this.dataMax = C(b))
        });
        F.prototype.setCompare = function (a, b) {
            this.isXAxis || (l(this.series, function (b) {
                b.setCompare(a)
            }), g(b, !0) && this.chart.redraw())
        };
        n.prototype.tooltipFormatter = function (b) {
            b =
                b.replace("{point.change}", (0 < this.change ? "+" : "") + a.numberFormat(this.change, g(this.series.tooltipOptions.changeDecimals, 2)));
            return J.apply(this, [b])
        };
        A(f.prototype, "render", function (a) {
            this.chart.is3d && this.chart.is3d() || this.chart.polar || !this.xAxis || this.xAxis.isRadial || (!this.clipBox && this.animate ? (this.clipBox = b(this.chart.clipBox), this.clipBox.width = this.xAxis.len, this.clipBox.height = this.yAxis.len) : this.chart[this.sharedClipKey] ? this.chart[this.sharedClipKey].attr({
                    width: this.xAxis.len,
                    height: this.yAxis.len
                }) :
                this.clipBox && (this.clipBox.width = this.xAxis.len, this.clipBox.height = this.yAxis.len));
            a.call(this)
        });
        A(r.prototype, "getSelectedPoints", function (a) {
            var b = a.call(this);
            l(this.series, function (a) {
                a.hasGroupedData && (b = b.concat(v(a.points || [], function (a) {
                    return a.selected
                })))
            });
            return b
        });
        A(r.prototype, "update", function (a, c) {
            "scrollbar" in c && this.navigator && (b(!0, this.options.scrollbar, c.scrollbar), this.navigator.update({}, !1), delete c.scrollbar);
            return a.apply(this, Array.prototype.slice.call(arguments,
                1))
        })
    })(L);
    return L
});
