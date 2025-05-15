<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="300_SeihoBunshoSakusei_Hyoshi_Tab.aspx.cs" Inherits="Tos.Web.Pages._300_SeihoBunshoSakusei_Hyoshi_Tab" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>

<style type="text/css">
    #_300_SeihoBunshoSakusei_Hyoshi_Tab .part-content .col-xs-6 {
        padding-left: 0px;
        padding-right: 0px;
    }
    #_300_SeihoBunshoSakusei_Hyoshi_Tab .row-area {
        height: 473px!important
    }    
    #_300_SeihoBunshoSakusei_Hyoshi_Tab textarea {
        width: 100%;
        resize: none;
        border: 1px solid #cccccc !important;
    }
    #_300_SeihoBunshoSakusei_Hyoshi_Tab .btn-xs {
        min-width: inherit;
    }
    #_300_SeihoBunshoSakusei_Hyoshi_Tab .l-balance {
        width: 42.5%;
    }

    #_300_SeihoBunshoSakusei_Hyoshi_Tab .r-balance {
        width: 57.5%;
    }
     #_300_SeihoBunshoSakusei_Hyoshi_Tab .part.roof {
        height: 100%;
        margin-right: 0px;
        margin-bottom: 0px;
        overflow: auto;
    }
    #_300_SeihoBunshoSakusei_Hyoshi_Tab .div-lv2 .col-lb {
        width: 28.57142%;
    }
    #_300_SeihoBunshoSakusei_Hyoshi_Tab .div-lv2 .col-inp {
        width: 57.14286%;
    }
    #_300_SeihoBunshoSakusei_Hyoshi_Tab .div-lv2 .col-blank {
        width: calc(100% - 28.57142% - 57.14286%);
    }
    #_300_SeihoBunshoSakusei_Hyoshi_Tab .juyo-gijitsu .control,
    #_300_SeihoBunshoSakusei_Hyoshi_Tab .juyo-gijitsu .control-label {
        height: 131px;
    }
    #_300_SeihoBunshoSakusei_Hyoshi_Tab .div-lv2 .juyo-gijitsu .col-inp {
        width: 60%;
    }
    #_300_SeihoBunshoSakusei_Hyoshi_Tab .div-lv2 .juyo-gijitsu .col-blank {
        width: calc(100% - 28.57142% - 60%);
    }
    #_300_SeihoBunshoSakusei_Hyoshi_Tab .kyocho-hyoji {
        height: 372px;
    }
    #_300_SeihoBunshoSakusei_Hyoshi_Tab .dt-lb {
        width: 90px;
        float: left;
    }
    #_300_SeihoBunshoSakusei_Hyoshi_Tab .busho-lb,
    #_300_SeihoBunshoSakusei_Hyoshi_Tab .user-lb {
        width: 120px;
        float: left;
        white-space: nowrap;
        -ms-text-overflow: ellipsis;
        -o-text-overflow: ellipsis;
        text-overflow: ellipsis;
        overflow: hidden;
    }
    #_300_SeihoBunshoSakusei_Hyoshi_Tab .busho-cd,
    #_300_SeihoBunshoSakusei_Hyoshi_Tab .user-cd {
        display: none!important;
    }
    #_300_SeihoBunshoSakusei_Hyoshi_Tab .shonin-zone label,
    #_300_SeihoBunshoSakusei_Hyoshi_Tab .shonin-zone button {
        display: none;
    }
    #_300_SeihoBunshoSakusei_Hyoshi_Tab .width-balance {
        max-width: 228px;
    }

    #_300_SeihoBunshoSakusei_Hyoshi_Tab .double-row div {
        height: 73px;
    }
</style>

<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var _300_SeihoBunshoSakusei_Hyoshi_Tab = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.dialogDataTakeCount   // TODO:取得するデータ数を指定します。
        },
        urls: {
            search: "../api/_300_SeihoBunshoSakusei_Hyoshi_Tab",
            searchCopy: "../api/_300_SeihoBunshoSakusei_Hyoshi_Tab/getCopyData",
            ma_literal: "../Services/ShisaQuickService.svc/ma_literal?$filter=cd_category eq '{0}'"
        },
        param: {
            no_seiho: "",
            mode: ""
        },
        name: "【表紙】"
    };

    /**
     * 検索ダイアログの初期化処理を行います。
     */
    _300_SeihoBunshoSakusei_Hyoshi_Tab.initialize = function () {
        var element = $("#_300_SeihoBunshoSakusei_Hyoshi_Tab");

        element.on("change", ":input", _300_SeihoBunshoSakusei_Hyoshi_Tab.change);
        element.on("click", ".seihin-sansho", _300_SeihoBunshoSakusei_Hyoshi_Tab.openSeihinDialog);
        element.on("click", ".apply-shonin", _300_SeihoBunshoSakusei_Hyoshi_Tab.activeApplyMode);
        element.on("click", ".clear-shonin", _300_SeihoBunshoSakusei_Hyoshi_Tab.clearShonin);
        _300_SeihoBunshoSakusei_Hyoshi_Tab.element = element;

        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        _300_SeihoBunshoSakusei_Hyoshi_Tab.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#_300_SeihoBunshoSakusei_Hyoshi_Tab .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        _300_SeihoBunshoSakusei_Hyoshi_Tab.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#_300_SeihoBunshoSakusei_Hyoshi_Tab .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body",
                show: function () {
                    element.find(".alert-message").show();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                }
            });

        _300_SeihoBunshoSakusei_Hyoshi_Tab.validator = element.validation(App.validation(_300_SeihoBunshoSakusei_Hyoshi_Tab.options.validations, {
            success: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _300_SeihoBunshoSakusei_Hyoshi_Tab.setColValidStyle(item.element);

                    App.ui.page.notifyAlert.remove(item.element);
                }
            },
            fail: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _300_SeihoBunshoSakusei_Hyoshi_Tab.setColInvalidStyle(item.element);
                    if (state && state.suppressMessage) {
                        continue;
                    }

                    App.ui.page.notifyAlert.message(_300_SeihoBunshoSakusei_Hyoshi_Tab.name + item.message, item.element).show();
                }
            },
            always: function (results) {
                //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
            }
        }));

        _300_SeihoBunshoSakusei_Hyoshi_Tab.loadMasterData().then(function () {
            _300_SeihoBunshoSakusei_Hyoshi_Tab.search();
        })
        page.finishLoading("TAB_Initilize", "TAB1", 5);
    };

    /// Active main page for create apply mode [★　作成担当]
    _300_SeihoBunshoSakusei_Hyoshi_Tab.activeApplyMode = function (e) {
        var target = $(e.target),
            applyList = target.closest(".row").find("[data-prop]"),
            entity = _300_SeihoBunshoSakusei_Hyoshi_Tab.data.entry(_300_SeihoBunshoSakusei_Hyoshi_Tab.element.attr("data-key")),
            preventChange = false;

        $.each(applyList, function (ind, item) {
            item = $(item);
            var property = item.attr("data-prop"),
                value;
            // Existed shonin -> abort the change
            if (ind === 0 && entity[property]) {
                preventChange = true;
                return false;
            }
            // Continue
            if (item.hasClass("dt-lb")) {
//                value = App.data.getDateString(new Date());
                value = App.data.getDateString(new Date(),true);
            }
            if (item.hasClass("busho-lb")) {
                value = App.ui.page.user.nm_busho;
            }
            if (item.hasClass("user-lb")) {
                value = App.ui.page.user.Name;
            }
            if (item.hasClass("user-cd")) {
                value = App.ui.page.user.EmployeeCD;
            }
            if (item.hasClass("busho-cd")) {
                value = App.ui.page.user.cd_busho;
            }
            entity[property] = value;
            item.text(value);
        });

        _300_SeihoBunshoSakusei_Hyoshi_Tab.processShiseiButton(e);
        _300_SeihoBunshoSakusei_Hyoshi_Tab.data.update(entity);
        if (!preventChange) {
            if (target.attr("data-shonin-level") == "1") {
                page.activeApplyMode();
            }
            page.setShoninLevel(target.attr("data-shonin-level"));
        }
        // Remove validate msg
        App.ui.page.notifyAlert.remove(target);
    }

    /// Clear shonin data (base on shonin level)
    _300_SeihoBunshoSakusei_Hyoshi_Tab.clearShonin = function (e) {
        var target = $(e.target),
            clearList = _300_SeihoBunshoSakusei_Hyoshi_Tab.element.find(".shonin-zone").find("[data-prop]"),
            entity = _300_SeihoBunshoSakusei_Hyoshi_Tab.data.entry(_300_SeihoBunshoSakusei_Hyoshi_Tab.element.attr("data-key"));
        $.each(clearList, function (ind, item) {
            item = $(item);
            var property = item.attr("data-prop");
            item.text("");
            entity[property] = null;
        });

        //_300_SeihoBunshoSakusei_Hyoshi_Tab.processShiseiButton(e);
        _300_SeihoBunshoSakusei_Hyoshi_Tab.element.find(".apply-shonin").prop("disabled", true);
        _300_SeihoBunshoSakusei_Hyoshi_Tab.element.find(".apply-shonin[data-shonin-level='1']").prop("disabled", false);
        _300_SeihoBunshoSakusei_Hyoshi_Tab.data.update(entity);
        page.setIsChangeValue();
        page.deactiveApplyMode();
        page.setShoninLevel("0");
    }

    /**
    * TAB 1 on change
    */
    _300_SeihoBunshoSakusei_Hyoshi_Tab.change = function (e) {
        var target = $(e.target),
            property = target.attr("data-prop"),
            id = _300_SeihoBunshoSakusei_Hyoshi_Tab.element.attr("data-key"),
            entity = _300_SeihoBunshoSakusei_Hyoshi_Tab.data.entry(id);

        _300_SeihoBunshoSakusei_Hyoshi_Tab.validator.validate({
            targets: target,
            filter: page.options.changeValidationFilter
        }).then(function () {
            entity[property] = target.val();
            _300_SeihoBunshoSakusei_Hyoshi_Tab.data.update(entity);
            if (property !== "apply-validate-mode") {
                page.setIsChangeValue();
            }
            // Exceptions process when change successful
            switch (property) {
                case "cd_brand":
                    // Sync nm_brand in TAB 8 [賞味期間設定表] - [ブランド]
                    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.setBurando(target.find("option:selected").text());
                    break;
                case "cd_tokisaki":
                    // Sync nm_brand in TAB 8 [賞味期間設定表] - [得意先名]
                    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.setTokisaki(target.find("option:selected").text());
                    break;
                default:
                    break;
            }
        })
    }

    /**
    * マスターデータのロード処理を実行します。
    */
    _300_SeihoBunshoSakusei_Hyoshi_Tab.loadMasterData = function () {
        var urls = _300_SeihoBunshoSakusei_Hyoshi_Tab.urls ;
        return App.async.all({
            nm_tokisaki: $.ajax(App.ajax.odata.get(App.str.format(urls.ma_literal, App.settings.app.cd_category.kbn_tokisaki))),
            nm_brand: $.ajax(App.ajax.odata.get(App.str.format(urls.ma_literal, App.settings.app.cd_category.kbn_brand)))
        }).then(function (result) {
            var data = result.successes,
                element = _300_SeihoBunshoSakusei_Hyoshi_Tab.element;

            // 得意先名
            var cd_tokisaki = element.findP("cd_tokisaki");
            cd_tokisaki.children().remove();
            App.ui.appendOptions(
                cd_tokisaki,
                "cd_literal",
                "nm_literal",
                data.nm_tokisaki.value,
                true
            );

            // プランド名
            var cd_brand = element.findP("cd_brand");
            cd_brand.children().remove();
            App.ui.appendOptions(
                cd_brand,
                "cd_literal",
                "nm_literal",
                data.nm_brand.value,
                true
            );
        })
    }
    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    _300_SeihoBunshoSakusei_Hyoshi_Tab.setColInvalidStyle = function (target) {
        var $target,
            nextColStyleChange = function (target) {
                var next;
                if (target.hasClass("with-next-col")) {
                    next = target.next();
                    if (next.length) {
                        next.addClass("control-required");
                        next.removeClass("control-success");
                        nextColStyleChange(next);
                    }
                }
            };

        $target = $(target).closest("div");
        if ($target.hasClass("control")) {
            $target.addClass("control-required");
        } else {
            $target.addClass("control-required-label");
        }
        $target.removeClass("control-success");

        if ($target.hasClass("single-target")) {
            return;
        }
        if ($target.hasClass("kyocho-hyoji")) {
            _300_SeihoBunshoSakusei_Hyoshi_Tab.element.find(".kyocho-hyoji-lb").addClass("control-required-label").removeClass("control-success");
            return;
        }

        // control-labelまで対象の前の項目にクラスをセットする
        var element = $target;
        while (element.prev().length > 0) {
            element = element.prev();
            if (element.hasClass("control-label")) {
                element.addClass("control-required-label");
                element.removeClass("control-success-label");
                break;
            }
            else if (element.hasClass("control-required-label")) {
                element.removeClass("control-success-label");
                break;
            }
            else if (element.hasClass("control")) {
                element.addClass("control-required");
                element.removeClass("control-success");
            }
        }
        nextColStyleChange($target);
    };

    /**
     * 単項目要素をエラー無しのスタイルに設定します。
     * @param target 設定する要素
     */
    _300_SeihoBunshoSakusei_Hyoshi_Tab.setColValidStyle = function (target) {
        var $target,
            nextColStyleChange = function (target) {
                var next;
                if (target.hasClass("with-next-col")) {
                    next = target.next();
                    if (next.length) {
                        next.removeClass("control-required");
                        next.addClass("control-success");
                        nextColStyleChange(next);
                    }
                }
            };
        $target = $(target).closest("div");
        $target.removeClass("control-required control-required-label");
        $target.addClass("control-success");

        if ($target.hasClass("single-target")) {
            return;
        }
        if ($target.hasClass("kyocho-hyoji")) {
            _300_SeihoBunshoSakusei_Hyoshi_Tab.element.find(".kyocho-hyoji-lb").removeClass("control-required-label").addClass("control-success");
            return;
        }

        // control-labelまで対象の前の項目にクラスをセットする
        var element = $target;
        while (element.prev().length > 0) {
            element = element.prev();
            if (element.hasClass("control-label")) {
                element.removeClass("control-required-label");
                element.addClass("control-success-label");
                break;
            }
            else if (element.hasClass("control-required-label")) {
                element.removeClass("control-required-label");
                element.addClass("control-success-label");
                element.addClass("control-label");
                break;
            }
            else if (element.hasClass("control")) {
                element.removeClass("control-required");
                element.addClass("control-success");
            }
        }
        nextColStyleChange($target);
    };

    /**
   * Ajust memo width on resize window event
   */
    _300_SeihoBunshoSakusei_Hyoshi_Tab.autoAjustMemoWidth = function () {
        var element = _300_SeihoBunshoSakusei_Hyoshi_Tab.element;
        var childWidth = element.findP("nm_seihin_tokucho").width();
        var parentWidth = element.findP("nm_seihin_tokucho").closest("div").width();
        var padding = parentWidth - childWidth - 10;
        element.findP("shonin_memo").closest("div").css("padding-right", padding);
    }

    /**
    * Click button [製品参照]
    */
    _300_SeihoBunshoSakusei_Hyoshi_Tab.openSeihinDialog = function () {
        page.dialogs._804_SeihinSansho_Dialog.param.su_code_standard = page.values.su_code_standard;
        page.dialogs._804_SeihinSansho_Dialog.param.cd_haigo = page.getHaigoList();
        page.dialogs._804_SeihinSansho_Dialog.element.modal("show");
    }

    /**
     * 検索ダイアログ表示時処理を実行します。
     */
    _300_SeihoBunshoSakusei_Hyoshi_Tab.shown = function (e) {

        _300_SeihoBunshoSakusei_Hyoshi_Tab.element.find(":input:not(button):first").focus();
    };

    /**
     * 検索ダイアログ　バリデーションルールを定義します。
     */
    _300_SeihoBunshoSakusei_Hyoshi_Tab.options.validations = {
        no_hyojiyo_seiho: {
            rules: {
                required: true,
                maxbytelength: 30
            },
            options: {
                name: "表示用製法",
                byte: 15
            },
            messages: {
                required: App.messages.base.required,
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        cd_hin: {
            rules: {
                required: true
            },
            options: {
                name: "製品コード"
            },
            messages: {
                required: App.messages.base.required
            }
        },
        nm_seihin: {
            rules: {
                required: true
            },
            options: {
                name: "製品名"
            },
            messages: {
                required: App.messages.base.required
            }
        },
        nm_kaisha_kojyo: {
            rules: {
                required: true
            },
            options: {
                name: "製造工場"
            },
            messages: {
                required: App.messages.base.required
            }
        },
        dt_seizo_kaishi_yotei: {
            rules: {
                required: true, 
                maxbytelength: 20
            },
            options: {
                name: "製造開始予定日",
                byte: 10
            },
            messages: {
                required: App.messages.base.required,
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_shozoku_group: {
            rules: {
                required: true,
                maxbytelength: 70
            },
            options: {
                name: "所属グループ",
                byte: 35
            },
            messages: {
                required: App.messages.base.required,
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_shozoku_team: {
            rules: {
                required: true,
                maxbytelength: 30
            },
            options: {
                name: "所属チーム",
                byte: 15
            },
            messages: {
                required: App.messages.base.required,
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_team_leader: {
            rules: {
                required: true,
                maxbytelength: 30
            },
            options: {
                name: "チームリーダー",
                byte: 15
            },
            messages: {
                required: App.messages.base.required,
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_juyo_gijitsu_joho: {
            rules: {
                maxbytelength: 60
            },
            options: {
                name: "重要技術情報",
                byte: 30
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_kyocho_hyoji: {
            rules: {
                maxbytelength: 1000
            },
            options: {
                name: "強調表示",
                byte: 500
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_zenbankara_henkoten: {
            rules: {
                maxbytelength: 1000
            },
            options: {
                name: "前版からの変更点（改版の場合）",
                byte: 500
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_seihin_tokucho: {
            rules: {
                required: true,
                maxbytelength: 1500
            },
            options: {
                name: "製品特徴",
                byte: 750
            },
            messages: {
                required: App.messages.base.required,
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        shonin_memo: {
            rules: {
                maxlength: 500
            },
            options: {
                name: "承認／取消メモ"
            },
            messages: {
                maxlength: App.messages.base.maxlength
            }
        }
    };

    /**
     * 検索ダイアログの検索処理を実行します。
     */
    _300_SeihoBunshoSakusei_Hyoshi_Tab.search = function () {
        var element = _300_SeihoBunshoSakusei_Hyoshi_Tab.element,
            url = _300_SeihoBunshoSakusei_Hyoshi_Tab.urls.search;

        _300_SeihoBunshoSakusei_Hyoshi_Tab.options.filter = _300_SeihoBunshoSakusei_Hyoshi_Tab.createFilter();

        $.ajax(App.ajax.webapi.get(url, _300_SeihoBunshoSakusei_Hyoshi_Tab.options.filter))
        .done(function (result) {
            var data = _300_SeihoBunshoSakusei_Hyoshi_Tab.prepairBindingData(result);
            _300_SeihoBunshoSakusei_Hyoshi_Tab.bind(data);
        }).always(function () {

        });
    };

    /**
     * Reload data in copy mode
     */
    _300_SeihoBunshoSakusei_Hyoshi_Tab.searchCopy = function () {
        var element = _300_SeihoBunshoSakusei_Hyoshi_Tab.element,
            url = _300_SeihoBunshoSakusei_Hyoshi_Tab.urls.searchCopy;

        _300_SeihoBunshoSakusei_Hyoshi_Tab.options.filter = _300_SeihoBunshoSakusei_Hyoshi_Tab.createFilter();

        $.ajax(App.ajax.webapi.get(url, _300_SeihoBunshoSakusei_Hyoshi_Tab.options.filter))
        .done(function (result) {
            var data = _300_SeihoBunshoSakusei_Hyoshi_Tab.mergeSearchData(result);
            _300_SeihoBunshoSakusei_Hyoshi_Tab.bind(data);
        }).always(function () {

        });
    }

    /**
     * Merge current data with copy data
     */
    _300_SeihoBunshoSakusei_Hyoshi_Tab.mergeSearchData = function (copyData) {
        var currentData = _300_SeihoBunshoSakusei_Hyoshi_Tab.currentData;
        copyData = copyData || {}
        if (currentData) {
            currentData.no_hyojiyo_seiho        = copyData.no_hyojiyo_seiho     || "";
            currentData.cd_tokisaki             = copyData.cd_tokisaki          || "";
            currentData.cd_brand                = copyData.cd_brand             || "";
            currentData.dt_seizo_kaishi_yotei   = copyData.dt_seizo_kaishi_yotei || "";
            currentData.nm_shozoku_group        = copyData.nm_shozoku_group     || "";
            currentData.nm_shozoku_team         = copyData.nm_shozoku_team      || "";
            currentData.nm_team_leader          = copyData.nm_team_leader       || "";
            currentData.nm_juyo_gijitsu_joho    = copyData.nm_juyo_gijitsu_joho || "";
            currentData.nm_zenbankara_henkoten  = copyData.nm_zenbankara_henkoten || "";
            currentData.nm_seihin_tokucho       = copyData.nm_seihin_tokucho    || "";
            currentData.nm_kyocho_hyoji         = copyData.nm_kyocho_hyoji      || "";
        }
        return currentData;
    }

    /**
     * Prepair search data before binding: create changeset, create variables, ...
     */
    _300_SeihoBunshoSakusei_Hyoshi_Tab.prepairBindingData = function (searchData) {
        searchData = searchData || {};
        var param = _300_SeihoBunshoSakusei_Hyoshi_Tab.param,
            currentData = searchData.currentData,
            modes = page.options.mode;
        _300_SeihoBunshoSakusei_Hyoshi_Tab.data = App.ui.page.dataSet();
        _300_SeihoBunshoSakusei_Hyoshi_Tab.currentData = currentData;
        page.values.oldShoninLevel = _300_SeihoBunshoSakusei_Hyoshi_Tab.getShoninLevel();
        switch (param.mode) {
            case modes.new:
            case modes.new_copy:
                // Add new data
                _300_SeihoBunshoSakusei_Hyoshi_Tab.mergeSearchData(searchData.copyData);
                _300_SeihoBunshoSakusei_Hyoshi_Tab.data.add(currentData);
                break;
            case modes.edit_copy:
                // Merge copy data in copy mode
                _300_SeihoBunshoSakusei_Hyoshi_Tab.mergeSearchData(searchData.copyData);
            case modes.edit:
                // Edit data
                _300_SeihoBunshoSakusei_Hyoshi_Tab.data.attach(currentData);
                _300_SeihoBunshoSakusei_Hyoshi_Tab.data.update(currentData);
                break;
            case modes.view:
                _300_SeihoBunshoSakusei_Hyoshi_Tab.data.attach(currentData);
                break;
        }
        return currentData;
    }

    /**
     * 検索ダイアログの検索条件を組み立てます
     */
    _300_SeihoBunshoSakusei_Hyoshi_Tab.createFilter = function () {
        var param = _300_SeihoBunshoSakusei_Hyoshi_Tab.param,
            settings = App.settings.app;

        return {
            no_seiho: param.no_seiho,
            no_seiho_copy: param.no_seiho_copy,
            kbn_haigo: settings.kbnHin.haigo,
            kbn_tokisaki: settings.cd_category.kbn_tokisaki,
            kbn_brand: settings.cd_category.kbn_brand,
            mode: param.mode
        }
    };

    /**
     * bind options
     */
    _300_SeihoBunshoSakusei_Hyoshi_Tab.bindOptions = {
        appliers: {
            nm_kaisha_kojyo: function (value, element) {
                element.attr("title", value);
                return false;
            },
            cd_hin: function (value, element) {
                element.val(App.common.fillString(value, page.values.su_code_standard)); 
                return true;
            },
            dt_create: function (value, element) {
                element.text(App.data.getDateString(new Date(value)));
                return true;
            },
            dt_shonin1: function (value, element) {
                element.text(App.data.getDateString(new Date(value)));
                return true;
            },
            dt_shonin2: function (value, element) {
                element.text(App.data.getDateString(new Date(value)));
                return true;
            }
        }
    }

    /**
     * 検索ダイアログの一覧にデータをバインドします。
     */
    _300_SeihoBunshoSakusei_Hyoshi_Tab.bind = function (data) {
        var element = _300_SeihoBunshoSakusei_Hyoshi_Tab.element,
            param = _300_SeihoBunshoSakusei_Hyoshi_Tab.param,
            item = data || {};

        if (item) {
            item.no_seiho = _300_SeihoBunshoSakusei_Hyoshi_Tab.param.no_seiho;
        }
        
        _300_SeihoBunshoSakusei_Hyoshi_Tab.options.cd_haigo = item.cd_haigo;
        element.form(_300_SeihoBunshoSakusei_Hyoshi_Tab.bindOptions).bind(item);
        _300_SeihoBunshoSakusei_Hyoshi_Tab.activeModeControl(item);
        //set [ブランド] for tab 8 [賞味期間設定表]
        _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.setBurando(_300_SeihoBunshoSakusei_Hyoshi_Tab.element.findP("cd_brand").find("option:selected").text());
        //set [得意先名] for tab 8 [賞味期間設定表]
        _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.setTokisaki(_300_SeihoBunshoSakusei_Hyoshi_Tab.element.findP("cd_tokisaki").find("option:selected").text());
        page.finishLoading("TAB_Bind", "TAB1", 5);
    };

    /**
     * Show / hide, disabled / enabled controls
     */
    _300_SeihoBunshoSakusei_Hyoshi_Tab.activeModeControl = function (item) {
        var mode = _300_SeihoBunshoSakusei_Hyoshi_Tab.param.mode,
            element = _300_SeihoBunshoSakusei_Hyoshi_Tab.element;

        element.find(".apply-shonin").prop("disabled", true);
        if (mode == page.options.mode.view) {
            _300_SeihoBunshoSakusei_Hyoshi_Tab.element.find("input:not(.inp-lb), textarea, select").prop("disabled", true);
            // display shonin status in view mode
            element.find(".shonin-zone").find("label, button, input").show();
            element.find(".clear-shonin").prop("disabled", true);
        }
        if (_300_SeihoBunshoSakusei_Hyoshi_Tab.param.sub_mode == page.options.mode.apply) {
            element.find(".shonin-zone").find("label, button, input").show();
            if (_300_SeihoBunshoSakusei_Hyoshi_Tab.getShoninLevel() == 0) {
                element.find(".shonin-zone-1").find("label, button, input").show();
            }
            element.find(".clear-shonin").prop("disabled", false);
            if (item && _300_SeihoBunshoSakusei_Hyoshi_Tab.param.sub_mode != page.options.mode.view) {
                if (item.cd_shonin2) {
                    //element.find(".clear-shonin[data-shonin-level='1']").prop("disabled", false);
                    //element.find(".apply-shonin[data-shonin-level='3']").prop("disabled", false);
                } else if (item.cd_shonin1) {
                    //element.find(".clear-shonin[data-shonin-level='1']").prop("disabled", false);
                    element.find(".apply-shonin[data-shonin-level='3']").prop("disabled", false);
                } else if (item.cd_create) {
                    //element.find(".clear-shonin[data-shonin-level='1']").prop("disabled", false);
                    element.find(".apply-shonin[data-shonin-level='2']").prop("disabled", false);
                } else {
                    element.find(".apply-shonin[data-shonin-level='1']").prop("disabled", false);
                }
                // haigo status >= 3 [承認２] -> disabled remove shonin
                if (page.getShinseiStatus() >= App.settings.app.kbn_status.shonin2) {
                    element.find(".clear-shonin").prop("disabled", true);
                    element.find(".apply-shonin").prop("disabled", true);
                }
            }
        }

        // SKS 2020 st
        var pageMode = page.param.mode;
        switch (pageMode) {
            case App.settings.app.m_seiho_bunsho.shinki:
                break;
            case App.settings.app.m_seiho_bunsho.henshu:
                var shiseiStatus = page.getShinseiStatus();
                if (page.getShinseiStatus() < App.settings.app.kbn_status.shonin2) {
                    element.findP("lb_shonin_memo").show();
                    element.findP("shonin_memo").show();
                }
                break;
            case App.settings.app.m_seiho_bunsho.shinsei:
                var shiseiStatus = page.getShinseiStatus();
                if (page.getShinseiStatus() < App.settings.app.kbn_status.shonin2) {
                    element.findP("lb_shonin_memo").show();
                    element.findP("shonin_memo").show();
                    if (shiseiStatus != App.settings.app.kbn_status.henshuchu) {
                        element.findP("shonin_memo").prop("disabled", false);
                    }
                }
                break;
            case App.settings.app.m_seiho_bunsho.copy:
                break;
            case App.settings.app.m_seiho_bunsho.etsuran:
                var shiseiStatus = page.getShinseiStatus();
                if (page.getShinseiStatus() < App.settings.app.kbn_status.shonin2) {
                    element.findP("lb_shonin_memo").show();
                    element.findP("shonin_memo").show();
                }
                break;
        }
        // SKS 2020 ed
    }

    /**
   * Check all validations
   */
    _300_SeihoBunshoSakusei_Hyoshi_Tab.validateAll = function (filter) {
        return _300_SeihoBunshoSakusei_Hyoshi_Tab.validator.validate({
            targets: _300_SeihoBunshoSakusei_Hyoshi_Tab.element.find(":input"),
            filter: filter,
            state: {
                suppressMessage: false
            }
        }).then(function (result) {
            // If page mode not in APPLY
            if (_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.param.sub_mode !== page.options.mode.apply) {
                return App.async.success(result);
            }
            var shoninData = _300_SeihoBunshoSakusei_Hyoshi_Tab.currentData,
                isError = false;
            if (!shoninData) {
                return App.async.success(result);
            }
            //★　作成担当
            if (!App.isUndefOrNullOrStrEmpty(shoninData.cd_create)) {
                if (App.isUndefOrNullOrStrEmpty(shoninData.dt_create)
                    //|| App.isUndefOrNullOrStrEmpty(shoninData.cd_create_shozoku_busho)
                    || App.isUndefOrNullOrStrEmpty(shoninData.nm_create)) {
                    isError = true;
                }
            }
            //★　承認１
            if (!App.isUndefOrNullOrStrEmpty(shoninData.cd_shonin1)) {
                if (App.isUndefOrNullOrStrEmpty(shoninData.dt_shonin1)
                     //|| App.isUndefOrNullOrStrEmpty(shoninData.nm_shonin1_shozoku_busho)
                     || App.isUndefOrNullOrStrEmpty(shoninData.nm_shonin1)) {
                    isError = true;
                }
            }
            //★　承認２
            if (!App.isUndefOrNullOrStrEmpty(shoninData.cd_shonin2)) {
                if (App.isUndefOrNullOrStrEmpty(shoninData.dt_shonin2)
                    //|| App.isUndefOrNullOrStrEmpty(shoninData.nm_shonin2_shozoku_busho)
                    || App.isUndefOrNullOrStrEmpty(shoninData.nm_shonin2)) {
                    isError = true;
                }
            }
            if (isError) {
                App.ui.page.notifyAlert.message(_300_SeihoBunshoSakusei_Hyoshi_Tab.name + App.messages.app.AP0091).show();
                return App.async.fail();
            }
            else {
                return App.async.success(result);
            }
        });

    }

    _300_SeihoBunshoSakusei_Hyoshi_Tab.processShiseiButton = function (e) {
        var target = $(e.target),
            element = _300_SeihoBunshoSakusei_Hyoshi_Tab.element,
            shinseiLevel = target.attr("data-shonin-level"),
            isShonin = target.hasClass("apply-shonin");

        page.setIsChangeValue();
        //element.find(".apply-shonin").prop("disabled", true);
        //element.find(".clear-shonin").prop("disabled", true);
        switch (shinseiLevel) {
            case "1":
                element.find(".apply-shonin[data-shonin-level='1']").prop("disabled", isShonin);
                //element.find(".clear-shonin[data-shonin-level='1']").prop("disabled", !isShonin);
                element.find(".apply-shonin[data-shonin-level='2']").prop("disabled", !isShonin);
                break;
            case "2":
                //element.find(".clear-shonin[data-shonin-level='1']").prop("disabled", isShonin);
                element.find(".apply-shonin[data-shonin-level='2']").prop("disabled", isShonin);
                //element.find(".clear-shonin[data-shonin-level='2']").prop("disabled", !isShonin);
                element.find(".apply-shonin[data-shonin-level='3']").prop("disabled", !isShonin);
                break;
            case "3":
                //element.find(".clear-shonin[data-shonin-level='2']").prop("disabled", isShonin);
                element.find(".apply-shonin[data-shonin-level='3']").prop("disabled", isShonin);
                //element.find(".clear-shonin[data-shonin-level='3']").prop("disabled", !isShonin);
                break;
        }
    }

    /**
   * Get shonin level
   */
    _300_SeihoBunshoSakusei_Hyoshi_Tab.getShoninLevel = function () {
        var shoninData = _300_SeihoBunshoSakusei_Hyoshi_Tab.currentData;
        if (!shoninData) {
            return 0;
        }
        //★　承認２
        if (!App.isUndefOrNullOrStrEmpty(shoninData.cd_shonin2)) {
            return 3;
        }
        //★　承認１
        if (!App.isUndefOrNullOrStrEmpty(shoninData.cd_shonin1)) {
            return 2;
        }
        //★　作成担当
        if (!App.isUndefOrNullOrStrEmpty(shoninData.cd_create)) {
            return 1;
        }
        return 0;
    }

    /**
   * Get data for save
   */
    _300_SeihoBunshoSakusei_Hyoshi_Tab.getSaveData = function () {
        return _300_SeihoBunshoSakusei_Hyoshi_Tab.data.getChangeSet();
    }

</script>

<div class="tab-pane active smaller" id="_300_SeihoBunshoSakusei_Hyoshi_Tab">
    <div class="smaller sub-tab-content">
        <div class="part roof">
            <div class="part-content tab-content">
                <div class="col-xs-6" style="width: 53%;">
                    <div class="row">
                        <div class="control-label col-xs-2">
                            <label>表示用製法</label>
                        </div>
                        <div class="control col-xs-4">
                            <input type="text" data-prop="no_hyojiyo_seiho" class="width-balance"/>
                        </div>
                        <div class="control col-xs-6"></div>
                    </div>
                    <div class="row">
                        <div class="control-label col-xs-2">
                            <label>製品コード</label>
                        </div>
                        <div class="control col-xs-2">
                            <input type="text" data-prop="cd_hin" class="inp-lb" readonly="readonly">
                        </div>
                        <div class="control-label col-xs-8 fix-label-md single-target">
                            <label class="lb" style="float: left">製品名</label>
                            <input type="text" class="prop overflow-ellipsis inp-lb" data-prop="nm_seihin" style="width: calc(100% - 170px)" readonly="readonly" />
                            <button class="btn btn-xs btn-primary seihin-sansho">製品参照</button>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-7 col-zr div-lv2">
                            <div class="row">
                                <div class="control-label col-xs-3 col-lb">
                                    <label>得意先名</label>
                                </div>
                                <div class="control col-xs-8">
                                    <select data-prop="cd_tokisaki"></select>
                                </div>
                            </div>
                            <div class="row">
                                <div class="control-label col-xs-3 col-lb">
                                    <label>ブランド名</label>
                                </div>
                                <div class="control col-xs-8">
                                    <select data-prop="cd_brand" class=""></select>
                                </div>
                            </div>
                            <div class="row">
                                <div class="control-label col-xs-3 col-lb" style="height: 40px">
                                    <label>製造工場</label>
                                </div>
                                <div class="control col-xs-5" style="width: 66.42858%; height: 40px">
                                    <label data-prop="nm_kaisha_kojyo" data-title="nm_kaisha_kojyo" class="inp-lb ellipsis-line-2" style="overflow: hidden; height: 100%"></label>
                                    <input type="hidden" data-prop="nm_kaisha_kojyo" />
                                </div>
                            </div>
                            <div class="row">
                                <div class="control-label col-xs-3 col-lb">
                                    <label style="padding-right: 0px;">製造開始予定日</label>
                                </div>
                                <div class="control col-xs-4 col-inp">
                                    <input type="text" data-prop="dt_seizo_kaishi_yotei" class="width-balance" />
                                </div>
                                <div class="control col-xs-3 col-blank">
                                </div>
                            </div>
                            <div class="row double-row">
                                <div class="control-label col-xs-3 col-lb">
                                    <label>所属グループ</label>
                                </div>
                                <div class="control col-xs-4 col-inp">
                                    <%--<input type="text" data-prop="nm_shozoku_group" class="width-balance" />--%>
                                    <textarea data-prop="nm_shozoku_group" class="width-balance textarea-height bar-less" style="height: 64px;"></textarea>
                                </div>
                                <div class="control col-xs-3 col-blank">
                                </div>
                            </div>
                            <div class="row">
                                <div class="control-label col-xs-3 col-lb">
                                    <label>所属チーム</label>
                                </div>
                                <div class="control col-xs-4 col-inp">
                                    <input type="text" data-prop="nm_shozoku_team" class="width-balance" />
                                </div>
                                <div class="control col-xs-3 col-blank">
                                </div>
                            </div>
                            <div class="row">
                                <div class="control-label col-xs-3 col-lb">
                                    <label>チームリーダー</label>
                                </div>
                                <div class="control col-xs-4 col-inp">
                                    <input type="text" data-prop="nm_team_leader" class="width-balance" />
                                </div>
                                <div class="control col-xs-5 col-blank">
                                </div>
                            </div>
                            <div class="row">
                                <div class="control-label col-xs-12">
                                </div>
                            </div>
                            <div class="row juyo-gijitsu">
                                <div class="col-xs-10 control-label" style="width: 84.6%">
                                    <label class="full-width">重要技術情報</label>
                                    <textarea data-prop="nm_juyo_gijitsu_joho" class="textarea-height fit-25"></textarea>
                                </div>
                                <div class="col-xs-2 control" style="width: 15.4%"></div>
                            </div>                    
                            <div class="row">
                                <div class="control-label col-xs-12">
                                </div>
                            </div>
                            <div class="row">
                                <div class="control-label col-xs-12">
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-5 col-zr" style="width: 44.66666667%; margin-left: -3%; ">
                            <div class="row">
                                <div class="control col-xs-12"></div>
                            </div>
                            <div class="row">
                                <div class="control-label" style="height: 100%;height: 423px">
                                    <label class="full-width">強調表示</label>
                                    <textarea data-prop="nm_kyocho_hyoji" class="textarea-height fit-25"></textarea>
                                </div>
                            </div>
                        </div> 
                    </div>
                </div>
                <div class="col-xs-6" style="width: 47%;">
                    <div class="row">
                        <div class="control-label col-xs-2">
                            <label>元になる製法</label>
                        </div>
                        <div class="control col-xs-3">
                            <label data-prop="no_seiho_sanko"></label>
                        </div>
                        <div class="control-label col-xs-2">
                           <label>元になる製法名</label>
                        </div>
                        
                        <div class="col-xs-5 control">
                            <label data-prop="nm_seiho_sanko" class="overflow-ellipsis"></label>
                        </div>
                    </div>
                    <div class="row row-area">
                        <div class="control-label col-xs-6 row-area">                        
                            <label class="full-width">
                                前版からの変更点（改版の場合）
                            </label>
                            <textarea class="textarea-height fit-25" data-prop="nm_zenbankara_henkoten" style="width: 99%"></textarea>
                        </div>
                        <div class="control-label col-xs-6 row-area single-target">             
                            <label class="full-width">
                                製品特徴
                            </label>
                            <textarea class="textarea-height fit-25" data-prop="nm_seihin_tokucho"></textarea>
                        </div>
                    </div>
                </div>
                <div class="col-xs-12 col-zr">
                    <div class="row shonin-zone-1">
                        <div class="control-label col-xs-3" style="width: 10%;">
                        </div>
                        <div class="control col-xs-9" style="width: 90%;">
                            <label class="check-group" style="display: none;">
                                <input type="checkbox" data-prop="apply-validate-mode" />
                                製法文書はワードで運用するため、必須項目の入力はせずに申請をすすめます
                            </label>
                        </div>
                    </div>
                </div>
                <div class="col-xs-6">
                    <div class="row shonin-zone">
                        <div class="control-label col-xs-5 right">
                            <label>
                                ★　作成担当
                            </label>
                            <button type="button" class="btn btn-xs btn-primary apply-shonin" data-shonin-level="1">承認</button>
                            <button type="button" class="btn btn-xs btn-primary clear-shonin" data-shonin-level="1">取消</button>
                        </div>
                        <div class="control col-xs-7">
                            <div class="lb-padding">
                                <label data-prop="dt_create" class="dt-lb"></label>
                                <label data-prop="cd_create_shozoku_busho" class="busho-cd"></label>
                                <label data-prop="nm_create_shozoku_busho" class="busho-lb"></label>
                                <label data-prop="cd_create" class="user-cd"></label>
                                <label data-prop="nm_create" class="user-lb"></label>
                            </div>
                        </div>
                    </div>
                    <div class="row shonin-zone">
                        <div class="control-label col-xs-5 right">
                            <label>
                                ★　承認１
                            </label>
                            <button type="button" class="btn btn-xs btn-primary apply-shonin" data-shonin-level="2">承認</button>
                            <button type="button" class="btn btn-xs btn-primary clear-shonin" data-shonin-level="1">取消</button>
                        </div>
                        <div class="control col-xs-7">
                            <div class="lb-padding">
                                <label data-prop="dt_shonin1" class="dt-lb"></label>
                                <label data-prop="cd_shonin1_shozoku_busho" class="busho-cd"></label>
                                <label data-prop="nm_shonin1_shozoku_busho" class="busho-lb"></label>
                                <label data-prop="cd_shonin1" class="user-cd"></label>
                                <label data-prop="nm_shonin1" class="user-lb"></label>
                            </div>
                        </div>
                    </div>
                    <div class="row shonin-zone">
                        <div class="control-label col-xs-5 right">
                            <label>
                                ★　承認２
                            </label>
                            <button type="button" class="btn btn-xs btn-primary apply-shonin" data-shonin-level="3">承認</button>
                            <button type="button" class="btn btn-xs btn-primary clear-shonin" data-shonin-level="1">取消</button>
                        </div>
                        <div class="control col-xs-7">
                            <div class="lb-padding">
                                <label data-prop="dt_shonin2" class="dt-lb"></label>
                                <label data-prop="cd_shonin2_shozoku_busho" class="busho-cd"></label>
                                <label data-prop="nm_shonin2_shozoku_busho" class="busho-lb"></label>
                                <label data-prop="cd_shonin2" class="user-cd"></label>
                                <label data-prop="nm_shonin2" class="user-lb"></label>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12 control-label"></div>
                    </div>
                </div>
                <div class="col-xs-1 col-zr" style="width: 137px; margin-left: -2px">
                    <div class="row">
                        <div class="col-xs-12 control">
                            <label data-prop="lb_shonin_memo" style="display: none;">承認／取消メモ</label>
                        </div>
                        <div class="col-xs-12 control"></div>
                        <div class="col-xs-12 control"></div>
                        <div class="col-xs-12 control"></div>
                    </div>
                </div>
                <div class="col-xs-4 col-zr" style="width: calc(50% - 135px);">
                    <div class="row">
                        <div class="control col-xs-12" style="height: 98px; padding-right: 50px;">                           
                            <textarea class="full-height" data-prop="shonin_memo" style="display: none" disabled="disabled"></textarea>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
