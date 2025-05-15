<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.aspx.cs" Inherits="Tos.Web.Pages._300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>


<style type="text/css">
    #_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab .part-content .col-xs-12 {
    }

    #_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab .row-table {
        height: 92px;
        /*background-color: #f6f6f6;*/
    }

    #_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab .row-area-parent {
        height: 746px !important;
    }

    #_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab .row-area-parent-seizo {
        height: 500px !important;
    }

    #_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab .row-area {
        height: 256px!important;
    }

    #_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab textarea {
        width: 100%;
        resize: none;
        border: 1px solid #cccccc !important;
    }

    #_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab .text-content {
        height: 239px;
    }

    #_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab td.calculate {
        text-align: right;
        padding-right: 5px;
    }
    #_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab .col-content-low {
        height: calc(100% - 106px);
    }
    #_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab .col-content-high {
        height: 100%;
    }
    #_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab .part.bottom {
        margin-bottom: 0px;
    }
</style>
<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.dialogDataTakeCount   // TODO:取得するデータ数を指定します。
        },
        urls: {
            search: "../api/_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab",
            searchCopy: "../api/_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab/getCopyData",
            save: "../api/_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab",
            changeShisaku: "../api/_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab/ChangeShisaku"
        },
        param: {
            cd_shain: "",
            nen: "",
            no_oi: "",
            seq_shisaku: "",
            no_seiho: "",
            mode: ""
        },
        name: "【配合・製造上の注意事項】"
    };

    /**
     * 検索ダイアログの初期化処理を行います。
     */
    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.initialize = function () {
        var element = $("#_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab");

        //element.on("shown.bs.modal", _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.shown);
        element.on("click", ".search", _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.search);
        //TODO: 単一セレクトの場合は、下の１行を使用します
        element.on("click", ".search-list tbody ", _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.select);
        element.on("change", ":input", _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.change);
        _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.element = element;

        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body",
                show: function () {
                    element.find(".alert-message").show();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                }
            });

        _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.validator = element.validation(App.validation(_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.options.validations, {
            success: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.setColValidStyle(item.element);

                    App.ui.page.notifyAlert.remove(item.element);
                }
            },
            fail: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.setColInvalidStyle(item.element);
                    if (state && state.suppressMessage) {
                        continue;
                    }

                    App.ui.page.notifyAlert.message(_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.name + item.message, item.element).show();
                }
            },
            always: function (results) {
                //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
            }
        }));

        _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.search();
        page.finishLoading("TAB_Initilize", "TAB4", 5);
    };

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.setColInvalidStyle = function (target) {
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
        $target.addClass("control-required-label");
        $target.removeClass("control-success");

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
    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.setColValidStyle = function (target) {
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
        $target.removeClass("control-required-label");
        $target.addClass("control-success");

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
     * 検索ダイアログ表示時処理を実行します。
     */
    //_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.shown = function (e) {

    //    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.element.find(":input:not(button):first").focus();
    //};

    /**
     * 検索ダイアログ　バリデーションルールを定義します。
     */
    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.options.validations = {
        nm_haigo_free_tokki_jiko: {
            rules: {
                required: true,
                maxbytelength: 500
            },
            options: {
                name: "配合（フリー入力）",
                byte: 250
            },
            messages: {
                required: App.messages.base.required,
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_chuiten_free_komoku: {
            rules: {
                required: true,
                maxbytelength: 6000
            },
            options: {
                name: "製造上の注意事項（フリー入力）",
                byte: 3000
            },
            messages: {
                required: App.messages.base.required,
                maxbytelength: App.messages.base.maxbytelength
            }
        }
    };

    /**
     * 検索ダイアログの検索処理を実行します。
     */
    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.search = function () {
        var element = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.element,
             url = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.urls.search;

        _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.options.filter = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.createFilter();

        $.ajax(App.ajax.webapi.get(url, _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.options.filter))
        .done(function (result) {
            var data = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.prepairBindingData(result);
            data.cd_tani = result.cd_tani;
            _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.bind(data);
        }).always(function () {

        });
    };
    /**
    * Reload data in copy mode
    */
    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.searchCopy = function () {
        var element = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.element,
            url = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.urls.searchCopy;

        _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.options.filter = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.createFilter();

        $.ajax(App.ajax.webapi.get(url, _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.options.filter))
        .done(function (result) {
            var data = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.mergeSearchData(result);
            _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.bind(data);
        }).always(function () {

        });
    }

    /**
     * Merge current data with copy data
     */
    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.mergeSearchData = function (copyData) {
        var currentData = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.currentData;
        copyData = copyData || {}
        if (currentData) {
            //currentData.su_haigo_suiso_g = copyData.su_haigo_suiso_g || "";
            //currentData.su_haigo_suiso_ml = copyData.su_haigo_suiso_ml || "";
            //currentData.su_haigo_suiso_hiju = copyData.su_haigo_suiso_hiju || "";
            //currentData.su_haigo_yuso_g = copyData.su_haigo_yuso_g || "";
            //currentData.su_haigo_yuso_ml = copyData.su_haigo_yuso_ml || "";
            //currentData.su_haigo_yuso_hiju = copyData.su_haigo_yuso_hiju || "";
            //currentData.su_haigo_gokei_g = copyData.su_haigo_gokei_g || "";
            //currentData.su_haigo_gokei_ml = copyData.su_haigo_gokei_ml || "";
            //currentData.su_haigo_gokei_hiju = copyData.su_haigo_gokei_hiju || "";
            currentData.nm_chuiten_free_komoku = copyData.nm_chuiten_free_komoku || "";
            currentData.nm_haigo_free_tokki_jiko = copyData.nm_haigo_free_tokki_jiko || "";
            //currentData.pt_kotei = copyData.pt_kotei || "";
        }
        return currentData;
    }

    /**
     * Prepair search data before binding: create changeset, create variables, ...
     */
    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.prepairBindingData = function (searchData) {
        searchData = searchData || {};
        var param = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.param,
            currentData = searchData.currentData,
            mode = page.options.mode;
        _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.data = App.ui.page.dataSet();
        _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.currentData = currentData;
        switch (param.mode) {
            case mode.new_copy:
                // Merge copy data
                _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.mergeSearchData(searchData.copyData);
            case mode.new:
                // Add new data
                _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.data.add(currentData);
                break;
            case mode.edit_copy:
                // Merge copy data in copy mode
                _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.mergeSearchData(searchData.copyData);
            case mode.edit:
                // Edit data
                _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.data.attach(currentData);
                _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.data.update(currentData);
                break;
            case mode.view:
                _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.data.attach(currentData);
                break;
        }
        return currentData;
    }

    /**
     * Validate all element before save
     */
    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.validateAll = function (filter) {
        var entity = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.data.entry(_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.element.attr("data-key"));
        var calcDataCheck = function () {
            if (_300_SeihoBunshoSakusei_Hyoshi_Tab.param.mode === page.options.mode.view) {
                return App.async.success();
            }
            var cd_tani_gram = App.settings.app.cd_tani.gram;
            if (entity.pt_kotei == App.settings.app.pt_kotei.chomieki_2
            && _300_SeihoBunshoSakusei_Hyoshi_Tab.getShoninLevel() > 0) {
                if (App.isUndefOrNull(entity.su_haigo_suiso_g)
                    || (App.isUndefOrNull(entity.su_haigo_suiso_ml) && entity.cd_tani != cd_tani_gram)
                    || (App.isUndefOrNull(entity.su_haigo_suiso_hiju) && entity.cd_tani != cd_tani_gram)
                    || App.isUndefOrNull(entity.su_haigo_yuso_g)
                    || (App.isUndefOrNull(entity.su_haigo_yuso_ml) && entity.cd_tani != cd_tani_gram)
                    || (App.isUndefOrNull(entity.su_haigo_yuso_hiju) && entity.cd_tani != cd_tani_gram)
                    || App.isUndefOrNull(entity.su_haigo_gokei_g)
                    || (App.isUndefOrNull(entity.su_haigo_gokei_ml) && entity.cd_tani != cd_tani_gram)
                    || (App.isUndefOrNull(entity.su_haigo_gokei_hiju) && entity.cd_tani != cd_tani_gram)
                    ) {
                    App.ui.page.notifyAlert.message(_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.name + App.messages.app.AP0060, "+++_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab").show();
                    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.element.find(".control-label.row-table").addClass("control-required-label");
                    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.element.find(".control.row-table").addClass("control-required");
                    return App.async.fail();
                } else {
                    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.element.find(".row-table").removeClass("control-required-label, control-required");
                }
            }
            return App.async.success();
        }
        
        return App.async.all([
            // Normal validation check
            _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.validator.validate({
                targets: _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.element.find(":input"),
                filter: filter,
                state: {
                    suppressMessage: false
                }
            }),
            // Calculated data check
            calcDataCheck()
        ]);
    }

    /**
    * TABヘッダーの変更時処理を定義します。
    */
    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.change = function (e) {
        var target = $(e.target),
            property = target.attr("data-prop"),
            element = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.element,
            entity = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.data.entry(element.attr("data-key"));

        _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.validator.validate({
            targets: target,
            filter: page.options.changeValidationFilter
        }).then(function () {
            page.setIsChangeValue();
            entity[property] = target.val() ? target.val() : null;
        });
    }

    /**
     * 検索ダイアログの検索条件を組み立てます
     */
    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.createFilter = function () {
        var settings = App.settings.app
        return $.extend({
            kbn_pt_kotei: settings.pt_kotei.chomieki_2,//"002"
            kbn_chomi: settings.zoku_kotei.sakkinchomi_eki, //"001",
            kbn_suisho: settings.zoku_kotei.eki_suiso2, ///"006",
            kbn_yusho: settings.zoku_kotei.eki_yu_sho2, //"003"
            cd_tani_g: settings.cd_tani.gram            //"002"
        }, _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.param);
    };

    /**
     * Appliers to format 0.0
     */
    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.apply1point = function (value, cd_tani, isChangeDisplay) {
        if (_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.param.isApplied && isChangeDisplay && App.isUndefOrNull(value)) {
            return "－";
        }
        if (cd_tani == App.settings.app.cd_tani.gram && isChangeDisplay) {
            return "－";
        }
        if (App.isUndefOrNull(value)) {
            return "計算不可";
        } else {
            return App.common.fillTrailingZero(App.data.getCommaNumberString(value), 1);
        }
    }

    /**
     * Appliers to format 0.000
     */
    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.apply3point = function (value, cd_tani, isChangeDisplay) {
        if (_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.param.isApplied && isChangeDisplay && App.isUndefOrNull(value)) {
            return "－";
        }
        if (cd_tani == App.settings.app.cd_tani.gram && isChangeDisplay) {
            return "－";
        }
        if (App.isUndefOrNull(value)) {
            return "計算不可";
        } else {
            return App.common.fillTrailingZero(App.data.getCommaNumberString(value), 3);
        }
    }

    /**
     * Appliers
     */
    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.options.bindOption = {
        appliers: {
        }
    };

    /**
     * 検索ダイアログの一覧にデータをバインドします。
     */
    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.bind = function (data) {
        var element = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.element;
        var displayData = $.extend({}, data);

        _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.processMaterialCalc(displayData);

        element.form(_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.options.bindOption).bind(displayData);
        _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.activeModeControl();
        page.finishLoading("TAB_Bind", "TAB4", 5);
    };

    /**
     * mode control page's reaction for modes: new, edit, view, copy
     */
    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.activeModeControl = function () {
        var mode = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.param.mode,
            element = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.element,
            modes = page.options.mode;
        switch (mode) {
            case modes.new:
            case modes.new_copy:
                break;
            case modes.edit:
            case modes.edit_copy:
                break;
            case modes.view:
                element.find("textarea").prop("disabled", true);
                break;
        }
    }

    /**
     * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
     */
    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.select = function (e) {
        var element = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.element,
            target = $(e.target),
            id = target.closest("tbody").attr("data-key"),
            data;

        if (App.isUndef(id)) {
            return;
        }

        data = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.data.entry(id);

        if (App.isFunc(_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.dataSelected)) {
            if (!_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.dataSelected(data)) {
                element.modal("hide");
            }
        } else {
            element.modal("hide");
        }
    };

    /**
     * Show / Hide [ ドレッシング充填量（１本あたり）] + Display Appliers
     */
    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.processMaterialCalc = function (displayData) {
        var element = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.element;

        // Reset validate
        App.ui.page.notifyAlert.remove("+++_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab");
        _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.element.find(".row-table").removeClass("control-required-label control-required");
        // Dispaly / hide ドレッシング充填量（１本あたり）
        if (displayData.pt_kotei !== App.settings.app.pt_kotei.chomieki_2) {
            element.find(".material-cal").hide();
            element.find(".col-content-low").css("height", "100%");
        } else {
            element.find(".material-cal").show();
            element.find(".col-content-low").css("height", "");
            // Data Appliers change NULL -> [計算不可] and fill trailing 0
            // Because the framework [Appliers] cannot apply value of NULL
            // 配合_水相（ｇ）
            displayData.su_haigo_suiso_g = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.apply1point(displayData.su_haigo_suiso_g, displayData.cd_tani);
            // 配合_水相（mL)
            displayData.su_haigo_suiso_ml = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.apply1point(displayData.su_haigo_suiso_ml, displayData.cd_tani, true);
            // 配合_水相（比重）
            displayData.su_haigo_suiso_hiju = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.apply3point(displayData.su_haigo_suiso_hiju, displayData.cd_tani, true);
            // 配合_油相（ｇ）
            displayData.su_haigo_yuso_g = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.apply1point(displayData.su_haigo_yuso_g, displayData.cd_tani);
            // 配合_油相（mL)
            displayData.su_haigo_yuso_ml = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.apply1point(displayData.su_haigo_yuso_ml, displayData.cd_tani, true);
            // 配合_油相（比重）
            displayData.su_haigo_yuso_hiju = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.apply3point(displayData.su_haigo_yuso_hiju, displayData.cd_tani, true);
            // 配合_合計（ｇ）
            displayData.su_haigo_gokei_g = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.apply1point(displayData.su_haigo_gokei_g, displayData.cd_tani);
            // 配合_合計（mL)
            displayData.su_haigo_gokei_ml = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.apply1point(displayData.su_haigo_gokei_ml, displayData.cd_tani, true);
            // 配合_合計（比重）
            displayData.su_haigo_gokei_hiju = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.apply3point(displayData.su_haigo_gokei_hiju, displayData.cd_tani, true);
        }
    }
    
    /**
     * Change display data when change shisaku
     */
    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.changeShisaku = function (shisakuData) {
        var url = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.urls.changeShisaku,
            filter = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.createFilter();

        return $.ajax(App.ajax.webapi.get(url, filter)).then(function (result) {
            data = result.currentData || {};

            // Update entity
            var entity = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.data.entry(_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.element.attr("data-key"));
            entity.su_haigo_suiso_g = data.su_haigo_suiso_g;
            entity.su_haigo_suiso_ml = data.su_haigo_suiso_ml;
            entity.su_haigo_suiso_hiju = data.su_haigo_suiso_hiju;
            entity.su_haigo_yuso_g = data.su_haigo_yuso_g;
            entity.su_haigo_yuso_ml = data.su_haigo_yuso_ml;
            entity.su_haigo_yuso_hiju = data.su_haigo_yuso_hiju;
            entity.su_haigo_gokei_g = data.su_haigo_gokei_g;
            entity.su_haigo_gokei_ml = data.su_haigo_gokei_ml;
            entity.su_haigo_gokei_hiju = data.su_haigo_gokei_hiju;
            entity.pt_kotei = data.pt_kotei;
            entity.cd_tani = result.cd_tani;

            // Prepair display data
            // Data Appliers change NULL -> [計算不可] and fill trailing 0
            // Because the framework [Appliers] cannot apply value of NULL
            var displayData = $.extend({}, entity);
            _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.processMaterialCalc(displayData);
            // Display changed data
            _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.element.form(_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.options.bindOption).bind(displayData);

        });
    }

</script>

<div class="tab-pane" tabindex="-1" id="_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab">
    <div class="sub-tab-content smaller">
        <div class="col-xs-6 tab-padding roof full-height">
            <div class="part material-cal">
                <div class="part-content">
                    <div class="row">
                        <div class="control-label col-xs-12 row-table">
                            <div class="col-zr col-xs-2">
                                <label>ドレッシング充填量（１本あたり）</label>
                            </div>
                            <div class="control col-xs-10 row-table" style="padding-top: 1px;">
                                <table class="datatable table-striped-even table-condensed">
                                    <!--TODO: 明細一覧のヘッダーを定義するHTMLをここに記述します。-->
                                    <thead>
                                        <tr>
                                            <th style="width: 25%">調味料2液タイプ</th>
                                            <th style="width: 25%">g</th>
                                            <th style="width: 25%">mL</th>
                                            <th style="width: 25%">比重</th>
                                        </tr>
                                    </thead>
                                    <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->
                                    <tbody class="item-tmpl" style="cursor: default;">
                                        <tr>
                                            <td>
                                                <label>水相</label></td>
                                            <td class="calculate"><span data-prop="su_haigo_suiso_g"></span></td>
                                            <td class="calculate"><span data-prop="su_haigo_suiso_ml"></span></td>
                                            <td class="calculate"><span data-prop="su_haigo_suiso_hiju"></span></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label>油相</label></td>
                                            <td class="calculate"><span data-prop="su_haigo_yuso_g"></span></td>
                                            <td class="calculate"><span data-prop="su_haigo_yuso_ml"></span></td>
                                            <td class="calculate"><span data-prop="su_haigo_yuso_hiju"></span></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label>合計</label></td>
                                            <td class="calculate"><span data-prop="su_haigo_gokei_g"></span></td>
                                            <td class="calculate"><span data-prop="su_haigo_gokei_ml"></span></td>
                                            <td class="calculate"><span data-prop="su_haigo_gokei_hiju"></span></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="part bottom col-content-low" style="">
                <div class="part-content full-height">
                    <div class="row full-height">
                        <div class="control-label col-xs-10 col-md-8 col-lg-7" style="height: calc(100% - 2px)">
                            <div class="row">
                                <div class="small-height col-xs-12">
                                    <label>配合　（フリー入力）</label>
                                </div>
                            </div>
                            <textarea class="textarea-height fit-25" data-prop="nm_haigo_free_tokki_jiko"></textarea>
                        </div>
                        <div class="control col-xs-2 col-md-4 col-lg-5" style="height: calc(100% - 2px)"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-6 col-zr roof full-height">
            <div class="part bottom col-content-high" style="margin-right: 0px;">
                <div class="part-content full-height"">
                    <div class="col-xs-12 col-zr full-height">
                        <div class="row full-height">
                            <div class="control-label col-xs-10 col-md-8 col-lg-7" style="height: calc(100% - 2px)">
                                <div class="row">
                                    <div class="small-height col-xs-12">
                                        <label>製造上の注意事項　（フリー入力）</label>
                                    </div>
                                </div>
                                <textarea class="textarea-height fit-25" data-prop="nm_chuiten_free_komoku"></textarea>
                            </div>
                            <div class="control col-xs-2 col-md-4 col-lg-5" style="height: calc(100% - 2px)"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
