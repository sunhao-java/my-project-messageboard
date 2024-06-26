/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 2.8.2r1
 */
(function() {
	var G = YAHOO.util.Dom, M = YAHOO.util.Event, I = YAHOO.lang, L = YAHOO.env.ua, B = YAHOO.widget.Overlay, J = YAHOO.widget.Menu, D = {}, K = null, E = null, C = null;
	function F(O, N, R, P) {
		var S, Q;
		if (I.isString(O) && I.isString(N)) {
			if (L.ie) {
				Q = '<input type="' + O + '" name="' + N + '"';
				if (P) {
					Q += " checked";
				}
				Q += ">";
				S = document.createElement(Q);
			} else {
				S = document.createElement("input");
				S.name = N;
				S.type = O;
				if (P) {
					S.checked = true;
				}
			}
			S.value = R;
		}
		return S;
	}
	function H(O, V) {
		var N = O.nodeName.toUpperCase(), S = (this.CLASS_NAME_PREFIX + this.CSS_CLASS_NAME), T = this, U, P, Q;
		function W(X) {
			if (!(X in V)) {
				U = O.getAttributeNode(X);
				if (U && ("value" in U)) {
					V[X] = U.value;
				}
			}
		}
		function R() {
			W("type");
			if (V.type == "button") {
				V.type = "push";
			}
			if (!("disabled" in V)) {
				V.disabled = O.disabled;
			}
			W("name");
			W("value");
			W("title");
		}
		switch (N) {
		case "A":
			V.type = "link";
			W("href");
			W("target");
			break;
		case "INPUT":
			R();
			if (!("checked" in V)) {
				V.checked = O.checked;
			}
			break;
		case "BUTTON":
			R();
			P = O.parentNode.parentNode;
			if (G.hasClass(P, S + "-checked")) {
				V.checked = true;
			}
			if (G.hasClass(P, S + "-disabled")) {
				V.disabled = true;
			}
			O.removeAttribute("value");
			O.setAttribute("type", "button");
			break;
		}
		O.removeAttribute("id");
		O.removeAttribute("name");
		if (!("tabindex" in V)) {
			V.tabindex = O.tabIndex;
		}
		if (!("label" in V)) {
			Q = N == "INPUT" ? O.value : O.innerHTML;
			if (Q && Q.length > 0) {
				V.label = Q;
			}
		}
	}
	function A(P) {
		var O = P.attributes, N = O.srcelement, R = N.nodeName.toUpperCase(), Q = this;
		if (R == this.NODE_NAME) {
			P.element = N;
			P.id = N.id;
			G.getElementsBy(function(S) {
				switch (S.nodeName.toUpperCase()) {
				case "BUTTON":
				case "A":
				case "INPUT":
					H.call(Q, S, O);
					break;
				}
			}, "*", N);
		} else {
			switch (R) {
			case "BUTTON":
			case "A":
			case "INPUT":
				H.call(this, N, O);
				break;
			}
		}
	}
	YAHOO.widget.Button = function(R, O) {
		if (!B && YAHOO.widget.Overlay) {
			B = YAHOO.widget.Overlay;
		}
		if (!J && YAHOO.widget.Menu) {
			J = YAHOO.widget.Menu;
		}
		var Q = YAHOO.widget.Button.superclass.constructor, P, N;
		if (arguments.length == 1 && !I.isString(R) && !R.nodeName) {
			if (!R.id) {
				R.id = G.generateId();
			}
			Q.call(this, (this.createButtonElement(R.type)), R);
		} else {
			P = {
				element : null,
				attributes : (O || {})
			};
			if (I.isString(R)) {
				N = G.get(R);
				if (N) {
					if (!P.attributes.id) {
						P.attributes.id = R;
					}
					P.attributes.srcelement = N;
					A.call(this, P);
					if (!P.element) {
						P.element = this.createButtonElement(P.attributes.type);
					}
					Q.call(this, P.element, P.attributes);
				}
			} else {
				if (R.nodeName) {
					if (!P.attributes.id) {
						if (R.id) {
							P.attributes.id = R.id;
						} else {
							P.attributes.id = G.generateId();
						}
					}
					P.attributes.srcelement = R;
					A.call(this, P);
					if (!P.element) {
						P.element = this.createButtonElement(P.attributes.type);
					}
					Q.call(this, P.element, P.attributes);
				}
			}
		}
	};
	YAHOO
			.extend(
					YAHOO.widget.Button,
					YAHOO.util.Element,
					{
						_button : null,
						_menu : null,
						_hiddenFields : null,
						_onclickAttributeValue : null,
						_activationKeyPressed : false,
						_activationButtonPressed : false,
						_hasKeyEventHandlers : false,
						_hasMouseEventHandlers : false,
						_nOptionRegionX : 0,
						CLASS_NAME_PREFIX : "yui-",
						NODE_NAME : "SPAN",
						CHECK_ACTIVATION_KEYS : [ 32 ],
						ACTIVATION_KEYS : [ 13, 32 ],
						OPTION_AREA_WIDTH : 20,
						CSS_CLASS_NAME : "button",
						_setType : function(N) {
							if (N == "split") {
								this.on("option", this._onOption);
							}
						},
						_setLabel : function(O) {
							this._button.innerHTML = O;
							var P, N = L.gecko;
							if (N && N < 1.9
									&& G.inDocument(this.get("element"))) {
								P = (this.CLASS_NAME_PREFIX + this.CSS_CLASS_NAME);
								this.removeClass(P);
								I.later(0, this, this.addClass, P);
							}
						},
						_setTabIndex : function(N) {
							this._button.tabIndex = N;
						},
						_setTitle : function(N) {
							if (this.get("type") != "link") {
								this._button.title = N;
							}
						},
						_setDisabled : function(N) {
							if (this.get("type") != "link") {
								if (N) {
									if (this._menu) {
										this._menu.hide();
									}
									if (this.hasFocus()) {
										this.blur();
									}
									this._button.setAttribute("disabled",
											"disabled");
									this.addStateCSSClasses("disabled");
									this.removeStateCSSClasses("hover");
									this.removeStateCSSClasses("active");
									this.removeStateCSSClasses("focus");
								} else {
									this._button.removeAttribute("disabled");
									this.removeStateCSSClasses("disabled");
								}
							}
						},
						_setHref : function(N) {
							if (this.get("type") == "link") {
								this._button.href = N;
							}
						},
						_setTarget : function(N) {
							if (this.get("type") == "link") {
								this._button.setAttribute("target", N);
							}
						},
						_setChecked : function(N) {
							var O = this.get("type");
							if (O == "checkbox" || O == "radio") {
								if (N) {
									this.addStateCSSClasses("checked");
								} else {
									this.removeStateCSSClasses("checked");
								}
							}
						},
						_setMenu : function(U) {
							var P = this.get("lazyloadmenu"), R = this
									.get("element"), N, W = false, X, O, Q;
							function V() {
								X.render(R.parentNode);
								this.removeListener("appendTo", V);
							}
							function T() {
								X.cfg.queueProperty("container", R.parentNode);
								this.removeListener("appendTo", T);
							}
							function S() {
								var Y;
								if (X) {
									G.addClass(X.element, this
											.get("menuclassname"));
									G.addClass(X.element,
											this.CLASS_NAME_PREFIX
													+ this.get("type")
													+ "-button-menu");
									X.showEvent.subscribe(this._onMenuShow,
											null, this);
									X.hideEvent.subscribe(this._onMenuHide,
											null, this);
									X.renderEvent.subscribe(this._onMenuRender,
											null, this);
									if (J && X instanceof J) {
										if (P) {
											Y = this.get("container");
											if (Y) {
												X.cfg.queueProperty(
														"container", Y);
											} else {
												this.on("appendTo", T);
											}
										}
										X.cfg.queueProperty("clicktohide",
												false);
										X.keyDownEvent
												.subscribe(this._onMenuKeyDown,
														this, true);
										X.subscribe("click", this._onMenuClick,
												this, true);
										this.on("selectedMenuItemChange",
												this._onSelectedMenuItemChange);
										Q = X.srcElement;
										if (Q
												&& Q.nodeName.toUpperCase() == "SELECT") {
											Q.style.display = "none";
											Q.parentNode.removeChild(Q);
										}
									} else {
										if (B && X instanceof B) {
											if (!K) {
												K = new YAHOO.widget.OverlayManager();
											}
											K.register(X);
										}
									}
									this._menu = X;
									if (!W && !P) {
										if (G.inDocument(R)) {
											X.render(R.parentNode);
										} else {
											this.on("appendTo", V);
										}
									}
								}
							}
							if (B) {
								if (J) {
									N = J.prototype.CSS_CLASS_NAME;
								}
								if (U && J && (U instanceof J)) {
									X = U;
									W = true;
									S.call(this);
								} else {
									if (B && U && (U instanceof B)) {
										X = U;
										W = true;
										X.cfg.queueProperty("visible", false);
										S.call(this);
									} else {
										if (J && I.isArray(U)) {
											X = new J(G.generateId(), {
												lazyload : P,
												itemdata : U
											});
											this._menu = X;
											this.on("appendTo", S);
										} else {
											if (I.isString(U)) {
												O = G.get(U);
												if (O) {
													if (J
															&& G.hasClass(O, N)
															|| O.nodeName
																	.toUpperCase() == "SELECT") {
														X = new J(U, {
															lazyload : P
														});
														S.call(this);
													} else {
														if (B) {
															X = new B(U, {
																visible : false
															});
															S.call(this);
														}
													}
												}
											} else {
												if (U && U.nodeName) {
													if (J
															&& G.hasClass(U, N)
															|| U.nodeName
																	.toUpperCase() == "SELECT") {
														X = new J(U, {
															lazyload : P
														});
														S.call(this);
													} else {
														if (B) {
															if (!U.id) {
																G.generateId(U);
															}
															X = new B(U, {
																visible : false
															});
															S.call(this);
														}
													}
												}
											}
										}
									}
								}
							}
						},
						_setOnClick : function(N) {
							if (this._onclickAttributeValue
									&& (this._onclickAttributeValue != N)) {
								this.removeListener("click",
										this._onclickAttributeValue.fn);
								this._onclickAttributeValue = null;
							}
							if (!this._onclickAttributeValue && I.isObject(N)
									&& I.isFunction(N.fn)) {
								this.on("click", N.fn, N.obj, N.scope);
								this._onclickAttributeValue = N;
							}
						},
						_isActivationKey : function(N) {
							var S = this.get("type"), O = (S == "checkbox" || S == "radio") ? this.CHECK_ACTIVATION_KEYS
									: this.ACTIVATION_KEYS, Q = O.length, R = false, P;
							if (Q > 0) {
								P = Q - 1;
								do {
									if (N == O[P]) {
										R = true;
										break;
									}
								} while (P--);
							}
							return R;
						},
						_isSplitButtonOptionKey : function(P) {
							var O = (M.getCharCode(P) == 40);
							var N = function(Q) {
								M.preventDefault(Q);
								this.removeListener("keypress", N);
							};
							if (O) {
								if (L.opera) {
									this.on("keypress", N);
								}
								M.preventDefault(P);
							}
							return O;
						},
						_addListenersToForm : function() {
							var T = this.getForm(), S = YAHOO.widget.Button.onFormKeyPress, R, N, Q, P, O;
							if (T) {
								M.on(T, "reset", this._onFormReset, null, this);
								M.on(T, "submit", this._onFormSubmit, null,
										this);
								N = this.get("srcelement");
								if (this.get("type") == "submit"
										|| (N && N.type == "submit")) {
									Q = M.getListeners(T, "keypress");
									R = false;
									if (Q) {
										P = Q.length;
										if (P > 0) {
											O = P - 1;
											do {
												if (Q[O].fn == S) {
													R = true;
													break;
												}
											} while (O--);
										}
									}
									if (!R) {
										M.on(T, "keypress", S);
									}
								}
							}
						},
						_showMenu : function(R) {
							if (YAHOO.widget.MenuManager) {
								YAHOO.widget.MenuManager.hideVisible();
							}
							if (K) {
								K.hideAll();
							}
							var N = this._menu, Q = this.get("menualignment"), P = this
									.get("focusmenu"), O;
							if (this._renderedMenu) {
								N.cfg.setProperty("context", [
										this.get("element"), Q[0], Q[1] ]);
								N.cfg
										.setProperty("preventcontextoverlap",
												true);
								N.cfg.setProperty("constraintoviewport", true);
							} else {
								N.cfg.queueProperty("context", [
										this.get("element"), Q[0], Q[1] ]);
								N.cfg.queueProperty("preventcontextoverlap",
										true);
								N.cfg
										.queueProperty("constraintoviewport",
												true);
							}
							this.focus();
							if (J && N && (N instanceof J)) {
								O = N.focus;
								N.focus = function() {
								};
								if (this._renderedMenu) {
									N.cfg.setProperty("minscrollheight", this
											.get("menuminscrollheight"));
									N.cfg.setProperty("maxheight", this
											.get("menumaxheight"));
								} else {
									N.cfg.queueProperty("minscrollheight", this
											.get("menuminscrollheight"));
									N.cfg.queueProperty("maxheight", this
											.get("menumaxheight"));
								}
								N.show();
								N.focus = O;
								N.align();
								if (R.type == "mousedown") {
									M.stopPropagation(R);
								}
								if (P) {
									N.focus();
								}
							} else {
								if (B && N && (N instanceof B)) {
									if (!this._renderedMenu) {
										N
												.render(this.get("element").parentNode);
									}
									N.show();
									N.align();
								}
							}
						},
						_hideMenu : function() {
							var N = this._menu;
							if (N) {
								N.hide();
							}
						},
						_onMouseOver : function(O) {
							var Q = this.get("type"), N, P;
							if (Q === "split") {
								N = this.get("element");
								P = (G.getX(N) + (N.offsetWidth - this.OPTION_AREA_WIDTH));
								this._nOptionRegionX = P;
							}
							if (!this._hasMouseEventHandlers) {
								if (Q === "split") {
									this.on("mousemove", this._onMouseMove);
								}
								this.on("mouseout", this._onMouseOut);
								this._hasMouseEventHandlers = true;
							}
							this.addStateCSSClasses("hover");
							if (Q === "split" && (M.getPageX(O) > P)) {
								this.addStateCSSClasses("hoveroption");
							}
							if (this._activationButtonPressed) {
								this.addStateCSSClasses("active");
							}
							if (this._bOptionPressed) {
								this.addStateCSSClasses("activeoption");
							}
							if (this._activationButtonPressed
									|| this._bOptionPressed) {
								M.removeListener(document, "mouseup",
										this._onDocumentMouseUp);
							}
						},
						_onMouseMove : function(N) {
							var O = this._nOptionRegionX;
							if (O) {
								if (M.getPageX(N) > O) {
									this.addStateCSSClasses("hoveroption");
								} else {
									this.removeStateCSSClasses("hoveroption");
								}
							}
						},
						_onMouseOut : function(N) {
							var O = this.get("type");
							this.removeStateCSSClasses("hover");
							if (O != "menu") {
								this.removeStateCSSClasses("active");
							}
							if (this._activationButtonPressed
									|| this._bOptionPressed) {
								M.on(document, "mouseup",
										this._onDocumentMouseUp, null, this);
							}
							if (O === "split"
									&& (M.getPageX(N) > this._nOptionRegionX)) {
								this.removeStateCSSClasses("hoveroption");
							}
						},
						_onDocumentMouseUp : function(P) {
							this._activationButtonPressed = false;
							this._bOptionPressed = false;
							var Q = this.get("type"), N, O;
							if (Q == "menu" || Q == "split") {
								N = M.getTarget(P);
								O = this._menu.element;
								if (N != O && !G.isAncestor(O, N)) {
									this
											.removeStateCSSClasses((Q == "menu" ? "active"
													: "activeoption"));
									this._hideMenu();
								}
							}
							M.removeListener(document, "mouseup",
									this._onDocumentMouseUp);
						},
						_onMouseDown : function(P) {
							var Q, O = true;
							function N() {
								this._hideMenu();
								this.removeListener("mouseup", N);
							}
							if ((P.which || P.button) == 1) {
								if (!this.hasFocus()) {
									this.focus();
								}
								Q = this.get("type");
								if (Q == "split") {
									if (M.getPageX(P) > this._nOptionRegionX) {
										this.fireEvent("option", P);
										O = false;
									} else {
										this.addStateCSSClasses("active");
										this._activationButtonPressed = true;
									}
								} else {
									if (Q == "menu") {
										if (this.isActive()) {
											this._hideMenu();
											this._activationButtonPressed = false;
										} else {
											this._showMenu(P);
											this._activationButtonPressed = true;
										}
									} else {
										this.addStateCSSClasses("active");
										this._activationButtonPressed = true;
									}
								}
								if (Q == "split" || Q == "menu") {
									this._hideMenuTimer = I.later(250, this,
											this.on, [ "mouseup", N ]);
								}
							}
							return O;
						},
						_onMouseUp : function(P) {
							var Q = this.get("type"), N = this._hideMenuTimer, O = true;
							if (N) {
								N.cancel();
							}
							if (Q == "checkbox" || Q == "radio") {
								this.set("checked", !(this.get("checked")));
							}
							this._activationButtonPressed = false;
							if (Q != "menu") {
								this.removeStateCSSClasses("active");
							}
							if (Q == "split"
									&& M.getPageX(P) > this._nOptionRegionX) {
								O = false;
							}
							return O;
						},
						_onFocus : function(O) {
							var N;
							this.addStateCSSClasses("focus");
							if (this._activationKeyPressed) {
								this.addStateCSSClasses("active");
							}
							C = this;
							if (!this._hasKeyEventHandlers) {
								N = this._button;
								M.on(N, "blur", this._onBlur, null, this);
								M.on(N, "keydown", this._onKeyDown, null, this);
								M.on(N, "keyup", this._onKeyUp, null, this);
								this._hasKeyEventHandlers = true;
							}
							this.fireEvent("focus", O);
						},
						_onBlur : function(N) {
							this.removeStateCSSClasses("focus");
							if (this.get("type") != "menu") {
								this.removeStateCSSClasses("active");
							}
							if (this._activationKeyPressed) {
								M.on(document, "keyup", this._onDocumentKeyUp,
										null, this);
							}
							C = null;
							this.fireEvent("blur", N);
						},
						_onDocumentKeyUp : function(N) {
							if (this._isActivationKey(M.getCharCode(N))) {
								this._activationKeyPressed = false;
								M.removeListener(document, "keyup",
										this._onDocumentKeyUp);
							}
						},
						_onKeyDown : function(O) {
							var N = this._menu;
							if (this.get("type") == "split"
									&& this._isSplitButtonOptionKey(O)) {
								this.fireEvent("option", O);
							} else {
								if (this._isActivationKey(M.getCharCode(O))) {
									if (this.get("type") == "menu") {
										this._showMenu(O);
									} else {
										this._activationKeyPressed = true;
										this.addStateCSSClasses("active");
									}
								}
							}
							if (N && N.cfg.getProperty("visible")
									&& M.getCharCode(O) == 27) {
								N.hide();
								this.focus();
							}
						},
						_onKeyUp : function(N) {
							var O;
							if (this._isActivationKey(M.getCharCode(N))) {
								O = this.get("type");
								if (O == "checkbox" || O == "radio") {
									this.set("checked", !(this.get("checked")));
								}
								this._activationKeyPressed = false;
								if (this.get("type") != "menu") {
									this.removeStateCSSClasses("active");
								}
							}
						},
						_onClick : function(P) {
							var R = this.get("type"), Q, N, O;
							switch (R) {
							case "submit":
								if (P.returnValue !== false) {
									this.submitForm();
								}
								break;
							case "reset":
								Q = this.getForm();
								if (Q) {
									Q.reset();
								}
								break;
							case "split":
								if (this._nOptionRegionX > 0
										&& (M.getPageX(P) > this._nOptionRegionX)) {
									O = false;
								} else {
									this._hideMenu();
									N = this.get("srcelement");
									if (N && N.type == "submit"
											&& P.returnValue !== false) {
										this.submitForm();
									}
								}
								break;
							}
							return O;
						},
						_onDblClick : function(O) {
							var N = true;
							if (this.get("type") == "split"
									&& M.getPageX(O) > this._nOptionRegionX) {
								N = false;
							}
							return N;
						},
						_onAppendTo : function(N) {
							I.later(0, this, this._addListenersToForm);
						},
						_onFormReset : function(O) {
							var P = this.get("type"), N = this._menu;
							if (P == "checkbox" || P == "radio") {
								this.resetValue("checked");
							}
							if (J && N && (N instanceof J)) {
								this.resetValue("selectedMenuItem");
							}
						},
						_onFormSubmit : function(N) {
							this.createHiddenFields();
						},
						_onDocumentMouseDown : function(Q) {
							var N = M.getTarget(Q), P = this.get("element"), O = this._menu.element;
							if (N != P && !G.isAncestor(P, N) && N != O
									&& !G.isAncestor(O, N)) {
								this._hideMenu();
								if (L.ie && N.focus) {
									N.setActive();
								}
								M.removeListener(document, "mousedown",
										this._onDocumentMouseDown);
							}
						},
						_onOption : function(N) {
							if (this.hasClass(this.CLASS_NAME_PREFIX
									+ "split-button-activeoption")) {
								this._hideMenu();
								this._bOptionPressed = false;
							} else {
								this._showMenu(N);
								this._bOptionPressed = true;
							}
						},
						_onMenuShow : function(N) {
							M.on(document, "mousedown",
									this._onDocumentMouseDown, null, this);
							var O = (this.get("type") == "split") ? "activeoption"
									: "active";
							this.addStateCSSClasses(O);
						},
						_onMenuHide : function(N) {
							var O = (this.get("type") == "split") ? "activeoption"
									: "active";
							this.removeStateCSSClasses(O);
							if (this.get("type") == "split") {
								this._bOptionPressed = false;
							}
						},
						_onMenuKeyDown : function(P, O) {
							var N = O[0];
							if (M.getCharCode(N) == 27) {
								this.focus();
								if (this.get("type") == "split") {
									this._bOptionPressed = false;
								}
							}
						},
						_onMenuRender : function(P) {
							var S = this.get("element"), O = S.parentNode, N = this._menu, R = N.element, Q = N.srcElement, T;
							if (O != R.parentNode) {
								O.appendChild(R);
							}
							this._renderedMenu = true;
							if (Q && Q.nodeName.toLowerCase() === "select"
									&& Q.value) {
								T = N.getItem(Q.selectedIndex);
								this.set("selectedMenuItem", T, true);
								this._onSelectedMenuItemChange( {
									newValue : T
								});
							}
						},
						_onMenuClick : function(O, N) {
							var Q = N[1], P;
							if (Q) {
								this.set("selectedMenuItem", Q);
								P = this.get("srcelement");
								if (P && P.type == "submit") {
									this.submitForm();
								}
								this._hideMenu();
							}
						},
						_onSelectedMenuItemChange : function(O) {
							var P = O.prevValue, Q = O.newValue, N = this.CLASS_NAME_PREFIX;
							if (P) {
								G.removeClass(P.element,
										(N + "button-selectedmenuitem"));
							}
							if (Q) {
								G.addClass(Q.element,
										(N + "button-selectedmenuitem"));
							}
						},
						_onLabelClick : function(N) {
							this.focus();
							var O = this.get("type");
							if (O == "radio" || O == "checkbox") {
								this.set("checked", (!this.get("checked")));
							}
						},
						createButtonElement : function(N) {
							var P = this.NODE_NAME, O = document
									.createElement(P);
							O.innerHTML = "<"
									+ P
									+ ' class="first-child">'
									+ (N == "link" ? "<a></a>"
											: '<button type="button"></button>')
									+ "</" + P + ">";
							return O;
						},
						addStateCSSClasses : function(O) {
							var P = this.get("type"), N = this.CLASS_NAME_PREFIX;
							if (I.isString(O)) {
								if (O != "activeoption" && O != "hoveroption") {
									this.addClass(N + this.CSS_CLASS_NAME
											+ ("-" + O));
								}
								this.addClass(N + P + ("-button-" + O));
							}
						},
						removeStateCSSClasses : function(O) {
							var P = this.get("type"), N = this.CLASS_NAME_PREFIX;
							if (I.isString(O)) {
								this.removeClass(N + this.CSS_CLASS_NAME
										+ ("-" + O));
								this.removeClass(N + P + ("-button-" + O));
							}
						},
						createHiddenFields : function() {
							this.removeHiddenFields();
							var V = this.getForm(), Z, O, S, X, Y, T, U, N, R, W, P, Q = false;
							if (V && !this.get("disabled")) {
								O = this.get("type");
								S = (O == "checkbox" || O == "radio");
								if ((S && this.get("checked")) || (E == this)) {
									Z = F((S ? O : "hidden"), this.get("name"),
											this.get("value"), this
													.get("checked"));
									if (Z) {
										if (S) {
											Z.style.display = "none";
										}
										V.appendChild(Z);
									}
								}
								X = this._menu;
								if (J && X && (X instanceof J)) {
									Y = this.get("selectedMenuItem");
									P = X.srcElement;
									Q = (P && P.nodeName.toUpperCase() == "SELECT");
									if (Y) {
										U = (Y.value === null || Y.value === "") ? Y.cfg
												.getProperty("text")
												: Y.value;
										T = this.get("name");
										if (Q) {
											W = P.name;
										} else {
											if (T) {
												W = (T + "_options");
											}
										}
										if (U && W) {
											N = F("hidden", W, U);
											V.appendChild(N);
										}
									} else {
										if (Q) {
											N = V.appendChild(P);
										}
									}
								}
								if (Z && N) {
									this._hiddenFields = [ Z, N ];
								} else {
									if (!Z && N) {
										this._hiddenFields = N;
									} else {
										if (Z && !N) {
											this._hiddenFields = Z;
										}
									}
								}
								R = this._hiddenFields;
							}
							return R;
						},
						removeHiddenFields : function() {
							var Q = this._hiddenFields, O, P;
							function N(R) {
								if (G.inDocument(R)) {
									R.parentNode.removeChild(R);
								}
							}
							if (Q) {
								if (I.isArray(Q)) {
									O = Q.length;
									if (O > 0) {
										P = O - 1;
										do {
											N(Q[P]);
										} while (P--);
									}
								} else {
									N(Q);
								}
								this._hiddenFields = null;
							}
						},
						submitForm : function() {
							var Q = this.getForm(), P = this.get("srcelement"), O = false, N;
							if (Q) {
								if (this.get("type") == "submit"
										|| (P && P.type == "submit")) {
									E = this;
								}
								if (L.ie) {
									O = Q.fireEvent("onsubmit");
								} else {
									N = document.createEvent("HTMLEvents");
									N.initEvent("submit", true, true);
									O = Q.dispatchEvent(N);
								}
								if ((L.ie || L.webkit) && O) {
									Q.submit();
								}
							}
							return O;
						},
						init : function(P, d) {
							var V = d.type == "link" ? "a" : "button", a = d.srcelement, S = P
									.getElementsByTagName(V)[0], U;
							if (!S) {
								U = P.getElementsByTagName("input")[0];
								if (U) {
									S = document.createElement("button");
									S.setAttribute("type", "button");
									U.parentNode.replaceChild(S, U);
								}
							}
							this._button = S;
							YAHOO.widget.Button.superclass.init
									.call(this, P, d);
							var T = this.get("id"), Z = T + "-button";
							S.id = Z;
							var X, Q;
							var e = function(f) {
								return (f.htmlFor === T);
							};
							var c = function() {
								Q.setAttribute((L.ie ? "htmlFor" : "for"), Z);
							};
							if (a && this.get("type") != "link") {
								X = G.getElementsBy(e, "label");
								if (I.isArray(X) && X.length > 0) {
									Q = X[0];
								}
							}
							D[T] = this;
							var b = this.CLASS_NAME_PREFIX;
							this.addClass(b + this.CSS_CLASS_NAME);
							this.addClass(b + this.get("type") + "-button");
							M.on(this._button, "focus", this._onFocus, null,
									this);
							this.on("mouseover", this._onMouseOver);
							this.on("mousedown", this._onMouseDown);
							this.on("mouseup", this._onMouseUp);
							this.on("click", this._onClick);
							var R = this.get("onclick");
							this.set("onclick", null);
							this.set("onclick", R);
							this.on("dblclick", this._onDblClick);
							var O;
							if (Q) {
								if (this.get("replaceLabel")) {
									this.set("label", Q.innerHTML);
									O = Q.parentNode;
									O.removeChild(Q);
								} else {
									this.on("appendTo", c);
									M.on(Q, "click", this._onLabelClick, null,
											this);
									this._label = Q;
								}
							}
							this.on("appendTo", this._onAppendTo);
							var N = this.get("container"), Y = this
									.get("element"), W = G.inDocument(Y);
							if (N) {
								if (a && a != Y) {
									O = a.parentNode;
									if (O) {
										O.removeChild(a);
									}
								}
								if (I.isString(N)) {
									M.onContentReady(N, this.appendTo, N, this);
								} else {
									this.on("init", function() {
										I.later(0, this, this.appendTo, N);
									});
								}
							} else {
								if (!W && a && a != Y) {
									O = a.parentNode;
									if (O) {
										this.fireEvent("beforeAppendTo", {
											type : "beforeAppendTo",
											target : O
										});
										O.replaceChild(Y, a);
										this.fireEvent("appendTo", {
											type : "appendTo",
											target : O
										});
									}
								} else {
									if (this.get("type") != "link" && W && a
											&& a == Y) {
										this._addListenersToForm();
									}
								}
							}
							this.fireEvent("init", {
								type : "init",
								target : this
							});
						},
						initAttributes : function(O) {
							var N = O || {};
							YAHOO.widget.Button.superclass.initAttributes.call(
									this, N);
							this.setAttributeConfig("type", {
								value : (N.type || "push"),
								validator : I.isString,
								writeOnce : true,
								method : this._setType
							});
							this.setAttributeConfig("label", {
								value : N.label,
								validator : I.isString,
								method : this._setLabel
							});
							this.setAttributeConfig("value", {
								value : N.value
							});
							this.setAttributeConfig("name", {
								value : N.name,
								validator : I.isString
							});
							this.setAttributeConfig("tabindex", {
								value : N.tabindex,
								validator : I.isNumber,
								method : this._setTabIndex
							});
							this.configureAttribute("title", {
								value : N.title,
								validator : I.isString,
								method : this._setTitle
							});
							this.setAttributeConfig("disabled", {
								value : (N.disabled || false),
								validator : I.isBoolean,
								method : this._setDisabled
							});
							this.setAttributeConfig("href", {
								value : N.href,
								validator : I.isString,
								method : this._setHref
							});
							this.setAttributeConfig("target", {
								value : N.target,
								validator : I.isString,
								method : this._setTarget
							});
							this.setAttributeConfig("checked", {
								value : (N.checked || false),
								validator : I.isBoolean,
								method : this._setChecked
							});
							this.setAttributeConfig("container", {
								value : N.container,
								writeOnce : true
							});
							this.setAttributeConfig("srcelement", {
								value : N.srcelement,
								writeOnce : true
							});
							this.setAttributeConfig("menu", {
								value : null,
								method : this._setMenu,
								writeOnce : true
							});
							this.setAttributeConfig("lazyloadmenu", {
								value : (N.lazyloadmenu === false ? false
										: true),
								validator : I.isBoolean,
								writeOnce : true
							});
							this
									.setAttributeConfig(
											"menuclassname",
											{
												value : (N.menuclassname || (this.CLASS_NAME_PREFIX + "button-menu")),
												validator : I.isString,
												method : this._setMenuClassName,
												writeOnce : true
											});
							this.setAttributeConfig("menuminscrollheight", {
								value : (N.menuminscrollheight || 90),
								validator : I.isNumber
							});
							this.setAttributeConfig("menumaxheight", {
								value : (N.menumaxheight || 0),
								validator : I.isNumber
							});
							this.setAttributeConfig("menualignment", {
								value : (N.menualignment || [ "tl", "bl" ]),
								validator : I.isArray
							});
							this.setAttributeConfig("selectedMenuItem", {
								value : null
							});
							this.setAttributeConfig("onclick", {
								value : N.onclick,
								method : this._setOnClick
							});
							this.setAttributeConfig("focusmenu", {
								value : (N.focusmenu === false ? false : true),
								validator : I.isBoolean
							});
							this.setAttributeConfig("replaceLabel", {
								value : false,
								validator : I.isBoolean,
								writeOnce : true
							});
						},
						focus : function() {
							if (!this.get("disabled")) {
								this._button.focus();
							}
						},
						blur : function() {
							if (!this.get("disabled")) {
								this._button.blur();
							}
						},
						hasFocus : function() {
							return (C == this);
						},
						isActive : function() {
							return this.hasClass(this.CLASS_NAME_PREFIX
									+ this.CSS_CLASS_NAME + "-active");
						},
						getMenu : function() {
							return this._menu;
						},
						getForm : function() {
							var N = this._button, O;
							if (N) {
								O = N.form;
							}
							return O;
						},
						getHiddenFields : function() {
							return this._hiddenFields;
						},
						destroy : function() {
							var P = this.get("element"), N = this._menu, T = this._label, O, S;
							if (N) {
								if (K && K.find(N)) {
									K.remove(N);
								}
								N.destroy();
							}
							M.purgeElement(P);
							M.purgeElement(this._button);
							M.removeListener(document, "mouseup",
									this._onDocumentMouseUp);
							M.removeListener(document, "keyup",
									this._onDocumentKeyUp);
							M.removeListener(document, "mousedown",
									this._onDocumentMouseDown);
							if (T) {
								M
										.removeListener(T, "click",
												this._onLabelClick);
								O = T.parentNode;
								O.removeChild(T);
							}
							var Q = this.getForm();
							if (Q) {
								M.removeListener(Q, "reset", this._onFormReset);
								M.removeListener(Q, "submit",
										this._onFormSubmit);
							}
							this.unsubscribeAll();
							O = P.parentNode;
							if (O) {
								O.removeChild(P);
							}
							delete D[this.get("id")];
							var R = (this.CLASS_NAME_PREFIX + this.CSS_CLASS_NAME);
							S = G.getElementsByClassName(R, this.NODE_NAME, Q);
							if (I.isArray(S) && S.length === 0) {
								M.removeListener(Q, "keypress",
										YAHOO.widget.Button.onFormKeyPress);
							}
						},
						fireEvent : function(O, N) {
							var P = arguments[0];
							if (this.DOM_EVENTS[P] && this.get("disabled")) {
								return false;
							}
							return YAHOO.widget.Button.superclass.fireEvent
									.apply(this, arguments);
						},
						toString : function() {
							return ("Button " + this.get("id"));
						}
					});
	YAHOO.widget.Button.onFormKeyPress = function(R) {
		var P = M.getTarget(R), S = M.getCharCode(R), Q = P.nodeName
				&& P.nodeName.toUpperCase(), N = P.type, T = false, V, X, O, W;
		function U(a) {
			var Z, Y;
			switch (a.nodeName.toUpperCase()) {
			case "INPUT":
			case "BUTTON":
				if (a.type == "submit" && !a.disabled) {
					if (!T && !O) {
						O = a;
					}
				}
				break;
			default:
				Z = a.id;
				if (Z) {
					V = D[Z];
					if (V) {
						T = true;
						if (!V.get("disabled")) {
							Y = V.get("srcelement");
							if (!X
									&& (V.get("type") == "submit" || (Y && Y.type == "submit"))) {
								X = V;
							}
						}
					}
				}
				break;
			}
		}
		if (S == 13
				&& ((Q == "INPUT" && (N == "text" || N == "password"
						|| N == "checkbox" || N == "radio" || N == "file")) || Q == "SELECT")) {
			G.getElementsBy(U, "*", this);
			if (O) {
				O.focus();
			} else {
				if (!O && X) {
					M.preventDefault(R);
					if (L.ie) {
						X.get("element").fireEvent("onclick");
					} else {
						W = document.createEvent("HTMLEvents");
						W.initEvent("click", true, true);
						if (L.gecko < 1.9) {
							X.fireEvent("click", W);
						} else {
							X.get("element").dispatchEvent(W);
						}
					}
				}
			}
		}
	};
	YAHOO.widget.Button.addHiddenFieldsToForm = function(N) {
		var R = YAHOO.widget.Button.prototype, T = G.getElementsByClassName(
				(R.CLASS_NAME_PREFIX + R.CSS_CLASS_NAME), "*", N), Q = T.length, S, O, P;
		if (Q > 0) {
			for (P = 0; P < Q; P++) {
				O = T[P].id;
				if (O) {
					S = D[O];
					if (S) {
						S.createHiddenFields();
					}
				}
			}
		}
	};
	YAHOO.widget.Button.getButton = function(N) {
		return D[N];
	};
})();
(function() {
	var C = YAHOO.util.Dom, B = YAHOO.util.Event, D = YAHOO.lang, A = YAHOO.widget.Button, E = {};
	YAHOO.widget.ButtonGroup = function(J, H) {
		var I = YAHOO.widget.ButtonGroup.superclass.constructor, K, G, F;
		if (arguments.length == 1 && !D.isString(J) && !J.nodeName) {
			if (!J.id) {
				F = C.generateId();
				J.id = F;
			}
			I.call(this, (this._createGroupElement()), J);
		} else {
			if (D.isString(J)) {
				G = C.get(J);
				if (G) {
					if (G.nodeName.toUpperCase() == this.NODE_NAME) {
						I.call(this, G, H);
					}
				}
			} else {
				K = J.nodeName.toUpperCase();
				if (K && K == this.NODE_NAME) {
					if (!J.id) {
						J.id = C.generateId();
					}
					I.call(this, J, H);
				}
			}
		}
	};
	YAHOO
			.extend(
					YAHOO.widget.ButtonGroup,
					YAHOO.util.Element,
					{
						_buttons : null,
						NODE_NAME : "DIV",
						CLASS_NAME_PREFIX : "yui-",
						CSS_CLASS_NAME : "buttongroup",
						_createGroupElement : function() {
							var F = document.createElement(this.NODE_NAME);
							return F;
						},
						_setDisabled : function(G) {
							var H = this.getCount(), F;
							if (H > 0) {
								F = H - 1;
								do {
									this._buttons[F].set("disabled", G);
								} while (F--);
							}
						},
						_onKeyDown : function(K) {
							var G = B.getTarget(K), I = B.getCharCode(K), H = G.parentNode.parentNode.id, J = E[H], F = -1;
							if (I == 37 || I == 38) {
								F = (J.index === 0) ? (this._buttons.length - 1)
										: (J.index - 1);
							} else {
								if (I == 39 || I == 40) {
									F = (J.index === (this._buttons.length - 1)) ? 0
											: (J.index + 1);
								}
							}
							if (F > -1) {
								this.check(F);
								this.getButton(F).focus();
							}
						},
						_onAppendTo : function(H) {
							var I = this._buttons, G = I.length, F;
							for (F = 0; F < G; F++) {
								I[F].appendTo(this.get("element"));
							}
						},
						_onButtonCheckedChange : function(G, F) {
							var I = G.newValue, H = this.get("checkedButton");
							if (I && H != F) {
								if (H) {
									H.set("checked", false, true);
								}
								this.set("checkedButton", F);
								this.set("value", F.get("value"));
							} else {
								if (H && !H.set("checked")) {
									H.set("checked", true, true);
								}
							}
						},
						init : function(I, H) {
							this._buttons = [];
							YAHOO.widget.ButtonGroup.superclass.init.call(this,
									I, H);
							this.addClass(this.CLASS_NAME_PREFIX
									+ this.CSS_CLASS_NAME);
							var K = (YAHOO.widget.Button.prototype.CLASS_NAME_PREFIX + "radio-button"), J = this
									.getElementsByClassName(K);
							if (J.length > 0) {
								this.addButtons(J);
							}
							function F(L) {
								return (L.type == "radio");
							}
							J = C
									.getElementsBy(F, "input", this
											.get("element"));
							if (J.length > 0) {
								this.addButtons(J);
							}
							this.on("keydown", this._onKeyDown);
							this.on("appendTo", this._onAppendTo);
							var G = this.get("container");
							if (G) {
								if (D.isString(G)) {
									B.onContentReady(G, function() {
										this.appendTo(G);
									}, null, this);
								} else {
									this.appendTo(G);
								}
							}
						},
						initAttributes : function(G) {
							var F = G || {};
							YAHOO.widget.ButtonGroup.superclass.initAttributes
									.call(this, F);
							this.setAttributeConfig("name", {
								value : F.name,
								validator : D.isString
							});
							this.setAttributeConfig("disabled", {
								value : (F.disabled || false),
								validator : D.isBoolean,
								method : this._setDisabled
							});
							this.setAttributeConfig("value", {
								value : F.value
							});
							this.setAttributeConfig("container", {
								value : F.container,
								writeOnce : true
							});
							this.setAttributeConfig("checkedButton", {
								value : null
							});
						},
						addButton : function(J) {
							var L, K, G, F, H, I;
							if (J instanceof A && J.get("type") == "radio") {
								L = J;
							} else {
								if (!D.isString(J) && !J.nodeName) {
									J.type = "radio";
									L = new A(J);
								} else {
									L = new A(J, {
										type : "radio"
									});
								}
							}
							if (L) {
								F = this._buttons.length;
								H = L.get("name");
								I = this.get("name");
								L.index = F;
								this._buttons[F] = L;
								E[L.get("id")] = L;
								if (H != I) {
									L.set("name", I);
								}
								if (this.get("disabled")) {
									L.set("disabled", true);
								}
								if (L.get("checked")) {
									this.set("checkedButton", L);
								}
								K = L.get("element");
								G = this.get("element");
								if (K.parentNode != G) {
									G.appendChild(K);
								}
								L.on("checkedChange",
										this._onButtonCheckedChange, L, this);
							}
							return L;
						},
						addButtons : function(G) {
							var H, I, J, F;
							if (D.isArray(G)) {
								H = G.length;
								J = [];
								if (H > 0) {
									for (F = 0; F < H; F++) {
										I = this.addButton(G[F]);
										if (I) {
											J[J.length] = I;
										}
									}
								}
							}
							return J;
						},
						removeButton : function(H) {
							var I = this.getButton(H), G, F;
							if (I) {
								this._buttons.splice(H, 1);
								delete E[I.get("id")];
								I.removeListener("checkedChange",
										this._onButtonCheckedChange);
								I.destroy();
								G = this._buttons.length;
								if (G > 0) {
									F = this._buttons.length - 1;
									do {
										this._buttons[F].index = F;
									} while (F--);
								}
							}
						},
						getButton : function(F) {
							return this._buttons[F];
						},
						getButtons : function() {
							return this._buttons;
						},
						getCount : function() {
							return this._buttons.length;
						},
						focus : function(H) {
							var I, G, F;
							if (D.isNumber(H)) {
								I = this._buttons[H];
								if (I) {
									I.focus();
								}
							} else {
								G = this.getCount();
								for (F = 0; F < G; F++) {
									I = this._buttons[F];
									if (!I.get("disabled")) {
										I.focus();
										break;
									}
								}
							}
						},
						check : function(F) {
							var G = this.getButton(F);
							if (G) {
								G.set("checked", true);
							}
						},
						destroy : function() {
							var I = this._buttons.length, H = this
									.get("element"), F = H.parentNode, G;
							if (I > 0) {
								G = this._buttons.length - 1;
								do {
									this._buttons[G].destroy();
								} while (G--);
							}
							B.purgeElement(H);
							F.removeChild(H);
						},
						toString : function() {
							return ("ButtonGroup " + this.get("id"));
						}
					});
})();
YAHOO.register("button", YAHOO.widget.Button, {
	version : "2.8.2r1",
	build : "7"
});