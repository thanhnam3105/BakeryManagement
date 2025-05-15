(function (global, $, undef) {

    "use strict";

    /**
    * 画面に関する共通関数を定義します。
    */
    App.define("App.extend", {
        /**
        * validation duplicate key
        */
        duplicate207: function (value, opts, state, done) {
            var $tbody = state.tbody.element ? state.tbody.element : state.tbody;

            if (!$tbody || value == "")
                return done(true);

            var isKeyCount = 0;

            var dataCheckElement = page.detail.element.find(".checkedProcess");

            for (var i = 0; i < dataCheckElement.length; i++) {
                var element = $(dataCheckElement[i]),
                    keyCussor = "";

                var rowMerge = element.find("tr");
                var j = 0;
                while (j < rowMerge.length) {
                    var keyMerge = $(rowMerge[j]);
                    keyCussor = keyMerge.findP("cd_haigo_new").val();

                    if (value === keyCussor) {
                        isKeyCount++;
                    }
                    if (isKeyCount == 2) {
                        i = dataCheckElement.length;
                    }
                    j++;
                }
            }

            if (isKeyCount > 1) {
                page.detail.options.validations[opts[0]].messages.duplicate = App.str.format("同一キーのデータが既に存在します。{name}", { name: value });
                return done(false);
            }

            return done(true);
        },

    });

})(window, jQuery);

; (function (global, App, $, undef) {

    "use strict";

    /**
    * アプリケーションで利用する共通バリデーションを定義します。
    */
    //App.validation.addMethod("newMethodName", function (value, param, opts, done) {

    //    if (isEmpty(value)) {
    //        return done(true);
    //    }

    //    value = App.isNum(value) ? value + "" : value;
    //    var length = App.isArray(value) ? value.length : $.trim(value).length;

    //    done((((value || "") + "") === "") || (length >= param));

    //}, "input {param} or more characters");

    /**
    * アプリケーションで利用する共通Applierを定義します。
    */
    //App.ui.addFormApplier("point3digits", function (value, element) {
    //    var formated;

    //    if (value == null || value == "" || !value || value == -1) {
    //        formated = 0;
    //    } else {
    //        var str = App.isNum(value) ? value + "" : value,
    //            remain = (str.split(".")[1] ? "." + (str.split(".")[1] + "000").substr(0, 3) : ".000");
    //        formated = App.num.format(Number(value), "#,0") + remain;
    //    }

    //    if (element.is(":input")) {
    //        element.val(formated);
    //    } else {
    //        if (!App.isUndefOrNull(formated)) {
    //            element.text(formated);
    //        } else {
    //            element.text("");
    //        }
    //    }
    //    return true;
    //});

    /**
    * 指定された値が数値かどうかを検証します。
    * 値が空の場合は、検証を実行しない為成功となります。
    * FOR Bug #15481
    */
    App.validation.addMethod("numberBug15481", function (value, param, opts, done) {
        if (!param) {
            return done(true);
        }
        if (isEmpty(value)) {
            return done(true);
        }
        value = value.replace(/\,/g, "");
        value = value.indexOf(".") == 0 ? ("0" + value) : value;
        value = value.indexOf(".") == (value.length - 1) ? (value + "0") : value;

        done(App.isNumeric(value));
    }, "a invalid number");
    /**
    * 入力引数： value[対象数値],beforePoint[整数部の桁数],
    * afterPoint[小数点以下桁数],minus[マイナス可不可]
    * FOR Bug #15481
    */
    App.validation.addMethod("pointlengthBug15481", function (value, param, opts, done) {

        if (isEmpty(value)) {
            return done(true);
        }

        var beforePoint = param[0];
        var afterPoint = param[1];
        var minus = param[2];
        //文字列がnullの時はtrueを返す
        value = App.isNum(value) ? value + "" : value;
        //カンマがあったら削除
        value = value.toString();
        value = value.replace(/,/g, "");
        value = value.indexOf(".") == 0 ? ("0" + value) : value;
        value = value.indexOf(".") == (value.length - 1) ? (value + "0") : value;
        value = value == "." ? "" : value;

        var isPoint = false;
        if (afterPoint > 0) {
            isPoint = true;
        }
        if (!App.isNumeric(value)) {
            return done(false);
        }
        afterPoint = parseFloat(afterPoint);
        beforePoint = parseFloat(beforePoint);

        //小数点以下の数をチェック
        var point = value.indexOf("."),
            after, before;
        if (point >= 0) {
            after = value.substring((point + 1));
            if (after.length > afterPoint) {
                return done(false);
            }
            before = value.substring(0, point);
        }
        else {
            before = value;
        }

        //整数部分から"-"を取り除く
        if (minus && before.match(/^-/)) {
            before = before.substring(1);
        }
        //整数部分のチェック
        if (before.length > beforePoint) {
            return done(false);
        }

        done(true);
    }, "integer {param[0]}-digit, entered in {param[1]} decimal places");

    var isEmpty = App.validation.isEmpty;
    /**
    * シングルクォーテーションは入力できません.
    */
    App.validation.addMethod("check_single_kotations", function (value, param, opts, done) {

        if (isEmpty(value)) {
            return done(true);
        }

        value = App.isNum(value) ? value + "" : value;
        var regex = /'/;

        done(((value || "") + "") === "" || !regex.test(value));

    }, "not allowed input character has been input「'」");

    /**
    * 「; / ? : @ & = + $ ,」は使用できません。全角で入力して下さい。
    */
    App.validation.addMethod("check_charactor_range", function (value, param, opts, done) {
        if (isEmpty(value)) {
            return done(true);
        }

        value = App.isNum(value) ? value + "" : value;
        done(((value || "") + "") === "" || value.search(/[;|/|?|:|@|&|=|+|$|,]/i) == -1);

    }, "not allowed input character has been input 「; / ? : @ & = + $ ,」");

    /**
    * Common function of app.
    */
    App.define("App.common", {});

    /**
    * refill string to full of specified length with specified character
    * value string, numer of character (length), wrap char
    */
    App.common.fillString = function (value, num, wrap) {
        if (!wrap)
            wrap = "0";
        if (App.isUndefOrNullOrStrEmpty(value))
            return "";
        value = value.toString();
        if (!num)
            num = 0;
        for (var i = value.length; i < num; i++) {
            value = wrap + value;
        };
        return value;
    }

    /**
    * refill a list of string or list of object content string property
    */
    App.common.fillArrayString = function (arr, num, property, wrap) {
        if (!arr)
            return null;
        var result = [];
        $.each(arr, function (ind, item) {
            if (property) {
                var node = $.extend({}, item);
                node[property] = App.common.fillString(node[property], num, wrap);
                result.push(node);
            }
            else {
                result.push(App.common.fillString(item, num, wrap));
            }
        })
        return result;
    }

    App.common.setHeaderUserInfo = function (userInfo) {
        if (App.isUndefOrNull(userInfo))
            return;
        var infoGroup = $(".user-info").find("span");
        $(infoGroup[1]).text(userInfo.nm_kaisha);
        $(infoGroup[2]).text(userInfo.nm_busho);
        $(infoGroup[3]).text(userInfo.Name);
    }

    // If user click change Sort option
    // This method will apply in all page of this system (synchronize reason)
    // -> Do not overwrite or create new this function in specify page
    // -> Be careful with any change
    App.common.swicthSort = function (e) {
        var target = $(e.target),
            container = target.closest(".sort-container");

        // prevent duplicate trigger when multi click
        if (target.hasClass("selected-sort"))
            return target;
        // remove 'selected style'
        container.find(".selected-sort").removeClass("selected-sort");
        // add 'selected style' to new chosen
        target.addClass("selected-sort");

        /// Additional inner code of the page here
        //if (page && page.header.processSort)
        //    page.header.processSort();
        page.header.search();
        return target;
    }

    // Apply selected style to default option when click search button
    App.common.activeDefaultSort = function (container) {
        if (!container)
            container = $(".sort-container");
        var target = container.find(".sort-elem.default");
        container.find(".selected-sort").removeClass("selected-sort");
        target.addClass("selected-sort");
    }

    // Auto fill to fixed data length
    App.common.getFullString = function (value, format) {
        if (App.isUndefOrNullOrStrEmpty(value) || isNaN(Number(value))) {
            return value;
        }
        return App.num.format(Number(value), format);
    }
    App.common.getFullCdKaisha = function (value) {
        return App.common.getFullString(value, "0000");
    }
    App.common.getFullCdKojyo = function (value) {
        return App.common.getFullString(value, "0000");
    }
    App.common.getFullCdUser = function (value) {
        return App.common.getFullString(value, "0000000000");
    }
    App.common.getFullCdHin = function (value) {
        return App.common.getFullString(value, "0000000");
    }
    App.common.getFullSeihoNen = function (value) {
        return App.common.getFullString(value, "00");
    }
    App.common.getFullShisakuNen = function (value) {
        return App.common.getFullString(value, "00");
    }
    App.common.getFullSeihoRenban = function (value) {
        return App.common.getFullString(value, "0000");
    }
    App.common.getFullShisaOiban = function (value) {
        return App.common.getFullString(value, "000");
    }

    // Get mode config base on ID_Gamens
    App.common.getModeByPageID = function (config, pageID, isCloneConfig) {
        if (!config || !pageID) {
            return null;
        }
        if (App.isUndefOrNull(isCloneConfig)) {
            isCloneConfig = true;
        }

        var defaultConfig;
        for (var i = 0; i < config.length; i++) {
            if (typeof config[i] == 'object') {
                if (config[i].ID_Gamens == "DEFAULT") {
                    defaultConfig = config[i];
                } else {
                    if (config[i].ID_Gamens.indexOf(pageID) > -1) {
                        if (isCloneConfig) {
                            return $.extend({}, config[i]);
                        } else {
                            return config[i];
                        }
                    }
                }
            }
        }
        if (defaultConfig) {
            return defaultConfig;
        }
        return null;
    }

    App.common.getSettingsByName = function (lstNmSettings) {
        if (!lstNmSettings) {
            return null;
        }
        var result = {};
        for (var i = 0; i < lstNmSettings.length ; i++) {
            var NmSetting = lstNmSettings[i];
            if (App.settings.app[NmSetting]) {
                $.extend(result, App.settings.app[NmSetting]);
            }
        }
        return result;
    }

    App.common.fillTrailingZero = function (value, num) {
        if (App.isUndefOrNull(value)) {
            return "";
        }
        value = value.toString();
        var dotLoc = value.indexOf(".");
        if (dotLoc < 0) {
            dotLoc = 0;
            if (num > 0) {
                value += "."
            }
        } else {
            dotLoc = value.length - (dotLoc + 1);
        }
        for (var i = dotLoc; i < num; i++) {
            value += "0";
        }
        return value;
    }

    /**
    *   Number character allow only
    */
    App.common.checkNumberOnly = function (e) {
        var keyCode = e.keyCode || e.which;
        return keyCode >= 48 && keyCode <= 57;
    }

    /**
    *   Number and '-' character allow only
    */
    App.common.checkCurrencyOnly = function (e) {
        var keyCode = e.keyCode || e.which;
        return (keyCode >= 48 && keyCode <= 57) || keyCode === 45;
    }

    /**
    *   Disabled drag and drop event
    */
    App.common.disabledDragDrop = function (e) {
        return false;
    }

    /**
        * 指定された select コントロールに対して、 option タグを作成して設定します。
        * @param {Object} target select コントロールの jQuery Object もしくは、select コントロールの名前(name属性で検索）
        * @param {String} val option タグに設定するデータのキー項目
        * @param {String} label option タグに設定するデータの表示項目
        * @param {Array} data option タグに設定するデータ
        * @param {Boolean} isDefaultOption 先頭に空白項目を挿入するか
        * @param {Array} filter option タグに設定するデータのフィルタ
        * @param {Boolean} isNumColonText 表示項目に「val：label」で表示するかどうか
        * @param {Number} numberZeroAdd 表示項目に「0000：000X」で表示するかどうか
        */
    App.common.appendOptions = function (target, val, label, data, isDefaultOption, filter, isNumColonText, numberZeroAdd, dataProp) {
        var $control = target;
        // 文字列の場合には name 属性でセレクタを作成します。
        if (App.isStr(target)) {
            $control = $("[name=" + target + "]");
        }

        if (isDefaultOption) {
            $control.append("<option value=''></option>");
        }
        var quantityZero = 0;
        if (numberZeroAdd) {
            quantityZero = numberZeroAdd.length;
        } else {
            numberZeroAdd = "";
        }


        $.each(data, function (index, option) {
            if (!App.isUndefOrNull(filter) && filter.length > 0) {
                for (var i = 0; filter.length > i; i++) {
                    if (filter[i] == option[val]) {
                        if (isNumColonText) {
                            $control.append("<option " + dataProp + "val=" + option[dataProp] + " value='" + option[val] + "'>" + (numberZeroAdd + option[val]).slice(-quantityZero) + "：" + option[label] + "</option>");
                        } else {
                            $control.append("<option" + dataProp + "val=" + option[dataProp] + " value='" + option[val] + "'>" + option[label] + "</option>");
                        }
                    }
                }
            } else {
                if (isNumColonText) {
                    $control.append("<option " + dataProp + "val=" + option[dataProp] + " value='" + option[val] + "'>" + (numberZeroAdd + option[val]).slice(-quantityZero) + "：" + option[label] + "</option>");
                } else {
                    $control.append("<option " + dataProp + "val=" + option[dataProp] + " value='" + option[val] + "'>" + option[label] + "</option>");
                }
            }
        });
    }

    /**
    * Compare date from and date to。
    */
    App.validation.addMethod("lessthan_ymd_to", function (value, param, opts, done) {
        var dt_from = value,
            dt_to = param[1].element.findP(param[2]).val();

        if (App.isUndefOrNullOrStrEmpty(dt_to) || App.isUndefOrNullOrStrEmpty(dt_from)) {
            return done(true);
        }

        if (dt_to < dt_from) {
            return done(false);
        }

        return done(true);
    });

    /**
    * 指定された値がパラメーターで指定された配列のインデックス 0 に指定されている値以上、
    * インデックス 1 に指定されている値以下かどうかを検証します。
    * 値が空または数値に変換できない場合、およびパラメーターが配列でないもしくはインデックス 0 と 1 が数値に変換できない場合は
    * 検証を実行しない為成功となります。
    */
    App.validation.addMethod("rangeYearMonthDay", function (value, param, opts, done) {

        if (App.isUndefOrNullOrStrEmpty(value)) {
            return done(true);
        }

        if (!App.isArray(param)) {
            return done(true);
        }

        if (App.isUndefOrNullOrStrEmpty(value) || App.isUndefOrNullOrStrEmpty(param[0]) || App.isUndefOrNullOrStrEmpty(param[1])) {
            return done(true);
        }


        return done(value >= param[0] && value <= param[1]);
    }, "a value between {param[0]} and {param[1]}");

    /**
    * Open seiho bunsho in safe
    * The only one entrance to [300_製法文書作成] 
    */
    App.common.openSeihoBunsho = function (options) {
        var param = $.extend({
            // SRC seiho in copy mode
            no_seiho_copy: "",
            // Main seiho
            // DEST seiho in copy mode
            no_seiho: "",
            no_seiho_sakusei: "",
            // Haigo mapping: cd_haigo_moto, cd_haigo_saki
            haigo_copy_mapping: [],
            // Open page mode: NEW, EDIT, APPLY, ...
            mode: "",
            // Seiho status (not nesscessary)
            status: "",
            // TAB list for copy mode
            no_Tab: [],
            // Opener id_gamen
            openerID: ""
        }, options);

        var token = "_" + (new Date()).getTime();
        sessionStorage.setItem(token, JSON.stringify(param));
        var target = window.open("300_SeihoBunshoSakusei.aspx", token);
        return target;
    }

    /**
    * Control tabs in copy mode
    * All window related with [300_製法文書作成] in copy will be close
    */
    App.common.addCloseTab = function (options) {
        if (options) {
            var opener = window;
            var mainWindow = null;
            while (opener) {
                if (opener.page && opener.page.values.isWaitingForCopy300) {
                    mainWindow = opener;
                    break;
                }
                opener = opener.opener;
            }
            if (mainWindow && mainWindow.page) {
                mainWindow.page.values.closeTabs.push(options.openWindow);
            }
        }
    }

    /**
    * Control tabs in copy mode
    * All window related with [300_製法文書作成] in copy will be close
    */
    App.common.closeTab = function (options) {
        if (options) {
            $.each(options.closeTab, function (ind, tab) {
                if (tab) {
                    tab.close();
                }
            })
        }
    }

    /**
    * Selectize remove option
    */
    App.common.removeSelectOption = function (selectize, optionValue, ispendingChange) {
        if (!selectize || !selectize.length) {
            return;
        }
        $.each(selectize, function (ind, item) {
            if (item.selectize) {
                var pendingStatus = item.selectize.settings.pendingChange;
                item.selectize.settings.pendingChange = ispendingChange;
                item.selectize.removeOption(optionValue);
                item.selectize.settings.pendingChange = pendingStatus;
            }
        });
    }

    /**
    * Selectize add option
    */
    App.common.addSelectOption = function (selectize, option) {
        if (!selectize || !selectize.length) {
            return;
        }
        $.each(selectize, function (ind, item) {
            if (item.selectize) {
                item.selectize.addOption(option);
            }
        });
    }

    /**
    * TOsVN - Shohin Support Modify 2023: -st Log
    */
    App.common.Log = {
        getModeValue: function (param) {

            if (App.isUndefOrNull(param) || App.isUndefOrNull(param.gamen) || App.isUndefOrNull(param.nm_mode)) {
                return null;
            }

            var nm_mode = null,
                gamen = param.gamen,
                value = param.nm_mode,
                dataModes = App.settings.app.log_mode[gamen];

            for (var i = 0; i < dataModes.length; i++) {
                if (dataModes[i].value == value) {
                    nm_mode = dataModes[i].text;
                    break;
                }
            }

            return nm_mode;
        },
        write: function (paramsLog) {

            var url = "../api/Common/PostWriteLog",
                params = {
                    cd_game: paramsLog.cd_game,
                    cd_taisho_data: paramsLog.cd_taisho_data,
                    nm_mode: null,
                };

            params.nm_mode = this.getModeValue(paramsLog.mode);

            return $.ajax(App.ajax.webapi.post(url, params));
        }
    };

    /**
    * Apply selectize value
    */
    $.fn.setValue = function (value) {
        var targets = this;
        $.each(targets, function (ind, target) {
            if (target.selectize) {
                target.selectize.setValue(value, null, true);
            }
        })
    }

    /**
    * Remove seletize
    */
    $.fn.destroySelectize = function () {
        var targets = this;
        $.each(targets, function (ind, target) {
            if (target.selectize) {
                target.selectize.destroy();
            }
        })
    }

    /**
    * Disabled seletize
    */
    $.fn.disabledSelectize = function () {
        var targets = this;
        targets.each(function (ind, elem) {
            if (elem.selectize) {
                elem.selectize.disable();
            } else {
                $(elem).prop("disabled", true);
            }
        });
    }

    /**
    * Enabled seletize
    */
    $.fn.enabledSelectize = function () {
        var targets = this;
        targets.each(function (ind, elem) {
            if (elem.selectize) {
                elem.selectize.enabled();
            } else {
                $(elem).prop("disabled", false);
            }
        });
    }

})(this, App, jQuery);

//notify modified for multi tabs page and new selectized
(function (global, $, undef) {

    "use strict";

    var notify = App.define("App.ui.notify"),
        alertNotifySetting = {
            containerClass: "alert-notify",
            textContainerClass: "alert-notify-text",
            labelClass: "error",
            clickableClass: "alert-clickable",
            targetElemData: "alert-target",
            targetElemClass: "error",
            defaultTimeout: 0
        },
        infoNotifySetting = {
            containerClass: "info-notify",
            textContainerClass: "info-notify-text",
            labelClass: "info",
            clickableClass: "info-clickable",
            targetElemData: "info-target",
            targetElemClass: "info",
            defaultTimeout: 3000
        },
        warnNotifySetting = {
            containerClass: "warn-notify",
            textContainerClass: "warn-notify-text",
            labelClass: "warn",
            clickableClass: "warn-clickable",
            targetElemData: "warn-target",
            targetElemClass: "warn",
            defaultTimeout: 0
        },
        setupSlideUpMessage = function (root, opts, notifySetting) {
            var root = $(root ? root : document.body),
                container, bodyContainer, footerContent,
                messagesContainer,
                clearTimeoutId = 0,
                notifyObj,
                titleHolder = $("<div class='notify-title-holder'>" +
                    "<div class='notify-title-open' style='display:inline-block;'><i class='icon-chevron-down'></i></div>" +
                    "<div class='notify-title-close' style='display:none;'><i class='icon-chevron-up'></i></div>" +
                    "<p class='notify-title-message-length badge' style='display:inline-block;'></p>" +
                    "<p class='notify-title-message' style='display:inline-block;'></p>" +
                    "</div>"),
                settings = {
                    container: "<div class='notify " + notifySetting.containerClass + "' style='display:none;'><ul></ul></div>",
                    messagesContainerQuery: "ul",
                    textContainer: "<li class='" + notifySetting.textContainerClass + "'></li>",
                    show: function () { },
                    clear: function () { }
                },
                closeButton = titleHolder.find(".notify-title-close"),
                openButton = titleHolder.find(".notify-title-open"),
                titleMessageLength = titleHolder.find(".notify-title-message-length"),
                hasTitle = false;

            settings = $.extend({}, settings, opts);
            container = $(settings.container);
            bodyContainer = $(settings.bodyContainer ? settings.bodyContainer : ".wrap");
            footerContent = settings.footerContent ? $(settings.footerContent) : "";

            titleHolder.insertBefore(container.children(":first"));
            if (container.attr("title")) {
                titleHolder.find(".notify-title-message").text(container.attr("title"));
                hasTitle = true;
                container.attr("title", "");
            }
            messagesContainer = container.find(settings.messagesContainerQuery);
            if (container.parent().length < 1) {
                root.append(container);
            }

            titleHolder.on("click", function () {
                if (closeButton.css("display") === "none") {
                    messagesContainer.hide();
                    closeButton.css("display", "inline-block");
                    openButton.css("display", "none");
                } else {
                    messagesContainer.show();
                    closeButton.css("display", "none");
                    openButton.css("display", "inline-block");
                }
                adjustBodyBottom();
            });

            var adjustBodyBottom = function () {
                var bottom = (footerContent ? footerContent.outerHeight() : 0) + 15;
                if (container.parent().find("." + alertNotifySetting.labelClass).length
                    || container.parent().find("." + warnNotifySetting.labelClass).length) {
                    bottom += container.parent().height();
                }

                bodyContainer.css("padding-bottom", bottom);
            };

            notifyObj = {
                message: function (text, unique) {
                    var textElem, self = this;

                    //jquery を解除
                    if (unique && unique.jquery) {
                        unique = unique[0];
                    }

                    if (unique) {
                        messagesContainer.children().each(function (index, elem) {
                            var current = $(elem),
                                target = current.data(notifySetting.targetElemData);
                            if ((unique.nodeType && unique == target) || unique === target) {
                                current.off("click");
                                current.children().text(text);
                                textElem = current;
                            }
                        });
                    }

                    if (!textElem) {
                        textElem = $(settings.textContainer),
                        textElem.append("<pre class='" + notifySetting.labelClass + "'></pre>");
                        textElem.children().text(text);
                        messagesContainer.append(textElem);
                    }

                    if (unique) {
                        //if(unique.nodeType){ //if HTMLElement
                        textElem.addClass(notifySetting.clickableClass);
                        textElem.css("cursor", "pointer");
                        textElem.on("click", function () {
                            var arg = {
                                unique: unique,
                                handled: false
                            };
                            var a = $(self).trigger("itemselected", [arg]);
                            // Active tab only
                            if (typeof unique == "string" && unique && unique.indexOf("+++") === 0) {
                                $("li a[href='#" + unique.replace(/\+/g, "") + "']").click();
                                return;
                            }
                            // Focus on control when the control is visible
                            if (!$(unique).is(":visible")) {
                                var TabContent = $(unique).closest(".tab-pane:not(.active)");
                                if (TabContent.length) {
                                    $("li a[href='#" + TabContent.attr("id") + "']").click();
                                    var interCount = 0;
                                    var inter = setInterval(function () {
                                        if ((unique.selectize && unique.selectize.$control.is(":visible"))
                                            || $(unique).is(":visible")) {
                                            (unique.selectize ? unique.selectize : unique).focus();
                                            clearInterval(inter);
                                        }
                                        interCount++;
                                        if (interCount > 100) {
                                            clearInterval(inter);
                                        }
                                    }, 100);
                                } else {
                                    if (unique.selectize) {
                                        unique.selectize.focus();
                                    }
                                }

                            } else {
                                if (unique.nodeType && !arg.handled && $(unique).is(':visible') && $(unique).not(':disabled')) {
                                    unique.focus();
                                    //(unique.selectize ? unique.selectize : unique).focus();
                                }
                            }
                        });
                        $(unique).addClass(notifySetting.targetElemClass);
                        //}
                        textElem.data(notifySetting.targetElemData, unique);
                    }
                    if (hasTitle) {
                        titleMessageLength.text(messagesContainer.children().length)
                    }

                    return notifyObj;
                },

                show: function (timeout) {
                    timeout = timeout ? timeout : notifySetting.defaultTimeout;

                    if (messagesContainer.children().length > 0) {
                        container.show("slide", { direction: "down" }, 500);
                        setTimeout(function () {
                            adjustBodyBottom();
                        }, 600);
                    }

                    if (clearTimeoutId !== 0) {
                        clearTimeout(clearTimeoutId);
                    }
                    clearTimeoutId = 0;

                    if (timeout > 0) {
                        clearTimeoutId = setTimeout(function () {
                            notifyObj.clear(true);
                        }, timeout);
                    }

                    if (App.isFunc(settings.show)) {
                        settings.show();
                    }
                    adjustBodyBottom();

                    return notifyObj;
                },

                remove: function (unique) {

                    //jquery を解除
                    if (unique && unique.jquery) {
                        unique = unique[0];
                    }

                    messagesContainer.children().each(function (index, elem) {
                        var current = $(elem),
                            target = current.data(notifySetting.targetElemData);
                        if (unique) {
                            if ((unique.nodeType && unique == target)) {
                                current.css("cursor", "default");
                                current.off("click");
                                $(unique).removeClass(notifySetting.targetElemClass);
                            }
                            if ((unique.nodeType && unique == target) || unique === target) {
                                current.remove();
                            }
                        } else if (App.isUndef(target)) {
                            current.remove();
                        }
                    });

                    if (hasTitle) {
                        titleMessageLength.text(messagesContainer.children().length)
                    }

                    if (messagesContainer.children().length < 1) {
                        messagesContainer.empty();
                        container.hide();
                        messagesContainer.show();
                        if (App.isFunc(settings.clear)) {
                            settings.clear();
                        }
                    }
                    adjustBodyBottom();

                    return notifyObj;
                },

                clear: function (useAnime) {
                    messagesContainer.children().each(function (index, elem) {
                        var target = $($(elem).data(notifySetting.targetElemData));
                        target.removeClass(notifySetting.targetElemClass);
                    });

                    if (useAnime) {
                        container.hide("slide", { direction: "down" }, 200, function () {
                            messagesContainer.empty();
                            messagesContainer.show();
                            adjustBodyBottom();
                        });
                    } else {
                        messagesContainer.empty();
                        container.hide();
                        messagesContainer.show();
                        adjustBodyBottom();
                    }
                    if (App.isFunc(settings.clear)) {
                        settings.clear();
                    }

                    return notifyObj;
                },

                count: function () {
                    return messagesContainer.children().length;
                }
            };
            return notifyObj;
        };

    /** 
    * 情報メッセージを表示する機能を提供します。
    * 戻りで返されるオブジェクトは message / show / clear メソッドをもち、メッセージの追加、表示、削除と非表示を制御します。 
    * 
    * @param {String} title タイトル 
    * @param {String} subtitle サブタイトル 
    * @return {Object} 情報メッセージを表示するためのオブジェクト
    */
    notify.info = function (root, opts) {
        return setupSlideUpMessage(root, opts, infoNotifySetting);
    };

    /**
    * 警告メッセージを表示する機能を提供します。 
    * 戻りで返されるオブジェクトは message / show / clear メソッドをもち、メッセージの追加、表示、削除と非表示を制御します。
    * 
    * @param title タイトル 
    * @param subtitle サブタイトル 
    * @return {Object} 警告メッセージを表示するためのオブジェクト
    */
    notify.warn = function (root, opts) {
        return setupSlideUpMessage(root, opts, warnNotifySetting);
    };

    /**
    * 警告メッセージを表示する機能を提供します。 
    * 戻りで返されるオブジェクトは message / show / clear メソッドをもち、メッセージの追加、表示、削除と非表示を制御します。
    * 
    * @param title タイトル 
    * @param subtitle サブタイトル 
    * @return {Object} 警告メッセージを表示するためのオブジェクト
    */
    notify.alert = function (root, opts) {
        return setupSlideUpMessage(root, opts, alertNotifySetting);
    };

})(this, jQuery);