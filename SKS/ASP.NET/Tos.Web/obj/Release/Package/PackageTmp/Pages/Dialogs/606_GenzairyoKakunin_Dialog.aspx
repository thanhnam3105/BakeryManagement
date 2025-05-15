<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="606_GenzairyoKakunin_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs._606_GenzairyoKakunin_Dialog" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchInputDialog(Ver2.1)】 Template--%>
<style type="text/css">

    #_606_GenzairyoKakunin_Dialog {
        overflow: hidden !important;
    }

    #_606_GenzairyoKakunin_Dialog .datatable tbody tr, #_606_GenzairyoKakunin_Dialog .datatable select {
        padding: 0px !important;
        margin: 0px !important;
        height: 16px !important;
        font-size: 12px !important;
    }

    #_606_GenzairyoKakunin_Dialog input, #_606_GenzairyoKakunin_Dialog select {
        padding: 0px !important;
        margin: 0px !important;
        height: 17px !important;
        font-size: 12px !important;
        border: 0px;
    }

    #_606_GenzairyoKakunin_Dialog .dt-container .dt-body thead tr th {
        height: 0px !important;
    }

    #_606_GenzairyoKakunin_Dialog input[type="tel"] {
        text-align: right !important;
    }

    #_606_GenzairyoKakunin_Dialog .datatable td input:read-only, #_606_GenzairyoKakunin_Dialog .datatable tfoot td {
        background-color: #efefef;
    }

    #_606_GenzairyoKakunin_Dialog .datatable .new .selected-row input:not(.selectedInput) {
        background-color: transparent !important;
    }

    #_606_GenzairyoKakunin_Dialog .datatable .input-kotei {
        display: none;
    }

    #_606_GenzairyoKakunin_Dialog .datatable .selectedInput {
        background-color: #7cfc00 !important;
    }
</style>
<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var _606_GenzairyoKakunin_Dialog = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.maxInputDataCount,   // TODO:取得するデータ数を指定します。
            filter: ""
        },
        values: {
            isChangeRunning: {},
            validationsCellDefault: {
                rules: {
                    numberBug15481: true,
                    min: 0,
                    pointlength_custom: function (value, param, otps, done) {
                        value = value == "." ? "" : value;
                        var check = page.numPointlength(value, [5, 60]);
                        return done(check);
                    }
                },
                options: {
                    name: "配合重量"
                },
                messages: {
                    numberBug15481: App.messages.base.number,
                    min: App.messages.base.min,
                    pointlength_custom: App.str.format(App.messages.base.pointlength, { name: "配合重量", param: [5, 4] }),
                }
            },
            dataBackup: {}
        },
        urls: {},
        header: {
            options: {},
            values: {},
        },
        detail: {
            options: {},
            values: {}
        },
        commands: {},
        dialogs: {}
    };

    /**
     * バリデーション成功時の処理を実行します。
     */
    _606_GenzairyoKakunin_Dialog.validationSuccess = function (results, state) {
        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                _606_GenzairyoKakunin_Dialog.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                    if (row && row.element) {
                        //row.element.find("tr").removeClass("has-error");
                        $(item.element).removeClass("has-error");
                    }
                });
            } else {
                _606_GenzairyoKakunin_Dialog.setColValidStyle(item.element);
            }

            _606_GenzairyoKakunin_Dialog.notifyAlert.remove(item.element);
        }
    };

    /**
     * バリデーション失敗時の処理を実行します。
     */
    _606_GenzairyoKakunin_Dialog.validationFail = function (results, state) {

        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                _606_GenzairyoKakunin_Dialog.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                    if (row && row.element) {
                        //row.element.find("tr").addClass("has-error");
                        $(item.element).addClass("has-error");
                    }
                });
            } else {
                _606_GenzairyoKakunin_Dialog.setColInvalidStyle(item.element);
            }

            if (state && state.suppressMessage) {
                continue;
            }
            _606_GenzairyoKakunin_Dialog.notifyAlert.message(item.message, item.element).show();
        }
    };

    /**
     * バリデーション後の処理を実行します。
     */
    _606_GenzairyoKakunin_Dialog.validationAlways = function (results) {
        //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
    };

    /**
      * 指定された定義をもとにバリデータを作成します。
      * @param target バリデーション定義
      * @param options オプションに設定する値。指定されていない場合は、
      *                画面の success/fail/always のハンドル処理が指定されたオプションが設定されます。
      */
    _606_GenzairyoKakunin_Dialog.createValidator = function (target, options) {
        return App.validation(target, options || {
            success: _606_GenzairyoKakunin_Dialog.validationSuccess,
            fail: _606_GenzairyoKakunin_Dialog.validationFail,
            always: _606_GenzairyoKakunin_Dialog.validationAlways
        });
    };

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    _606_GenzairyoKakunin_Dialog.setColInvalidStyle = function (target) {
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
        $target.addClass("control-required");
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
    _606_GenzairyoKakunin_Dialog.setColValidStyle = function (target) {
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
        $target.removeClass("control-required");
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
     * Confirm quantity。
     */
    _606_GenzairyoKakunin_Dialog.commands.confirm = function () {
        var loadingTaget = _606_GenzairyoKakunin_Dialog.element.find(".modal-content");

        _606_GenzairyoKakunin_Dialog.notifyAlert.clear();
        _606_GenzairyoKakunin_Dialog.notifyInfo.clear();

        App.ui.loading.show("", loadingTaget);

        var sleep = 0;
        var condition = "Object.keys(_606_GenzairyoKakunin_Dialog.values.isChangeRunning).length == 0";
        App.ui.wait(sleep, condition, 100)
        .then(function () {
            _606_GenzairyoKakunin_Dialog.validateAll().then(function () {

                var selectData = function (data) {
                    if (App.isFunc(_606_GenzairyoKakunin_Dialog.dataSelected)) {
                        if (!_606_GenzairyoKakunin_Dialog.dataSelected(data)) {
                            _606_GenzairyoKakunin_Dialog.element.modal("hide");
                        }
                    } else {
                        _606_GenzairyoKakunin_Dialog.element.modal("hide");
                    }
                };

                var keySample = _606_GenzairyoKakunin_Dialog.values.data.seq_shisaku,
                    data = _606_GenzairyoKakunin_Dialog.detail.data.getChangeSet().updated;

                var dataModified = {
                    shisakuNumber: keySample,
                    dataModified: data
                };

                selectData(dataModified);

            });
        }).fail(function () {
            _606_GenzairyoKakunin_Dialog.notifyAlert.message(App.messages.base.MS0006).show();
        }).always(function () {
            App.ui.loading.close(loadingTaget);
        });
    };

    /**
     * すべてのバリデーションを実行します。
     */
    _606_GenzairyoKakunin_Dialog.validateAll = function () {

        var validations = [];

        validations.push(_606_GenzairyoKakunin_Dialog.detail.validateList());

        //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。

        return App.async.all(validations);
    };

    /**
     * each kotei: return false if all quantity is blank and otherwise。
     */
    _606_GenzairyoKakunin_Dialog.checkExistsQuantity = function (keySample, sort_kotei) {
        var isExistsQuantityNotBlank = false;
        page.detail.tabs.haigohyo_tab.detail.element.find(".sample-" + keySample + " .quantity_" + sort_kotei).each(function (index, elem) {
            if ($(elem).val() != "") {
                isExistsQuantityNotBlank = true;
                return true;
            }
        });

        return isExistsQuantityNotBlank;
    }

    /**
     * validate quantity, tanka, budomari, juryo_shiagari_g, zoku_kotei before open dialog。
     */
    _606_GenzairyoKakunin_Dialog.haigohyoTabValidateListFileds = function (keySample) {
        var validations = [],
            validateKotei = [];

        page.detail.tabs.haigohyo_tab.detail.dataTable.dataTable("each", function (row, index) {
            row.element.find(".fiel-validation:not([readonly],[disabled])").each(function (index, elem) {
                target = $(elem);

                var prop = target.attr("data-prop"),
                    colKey = target.closest("td").attr("index-col");
                    //ignoreBlankQuantity = false;

                var sort_kotei = row.element.findP("sort_kotei").text();

                if (prop.indexOf("quantity_") < 0 && prop != "tanka" && prop != "budomari" && prop != "juryo_shiagari_g" && prop != "zoku_kotei") {
                    return true;
                }

                if (target.hasClass("quantity") && colKey != keySample) {
                    return true;
                }

                //if (prop == "tanka" || prop == "budomari") {
                //    var quantity = row.element.find(".sample-" + keySample + " .quantity");
                //    if (quantity.length != 0 && App.isUndefOrNullOrStrEmpty(quantity.val())) {
                //        ignoreBlankQuantity = true;
                //    }
                //}

                if (prop == "zoku_kotei") {
                    validateKotei.push({
                        targets: target,
                        state: {
                            suppressMessage: false,
                            tbody: row.element,
                            isGridValidation: true,
                            is606: true,
                            ignoreKotei: _606_GenzairyoKakunin_Dialog.checkExistsQuantity(keySample, sort_kotei)
                        }
                    })

                    return true;
                }

                validations.push(page.detail.tabs.haigohyo_tab.detail.validator.validate({
                    targets: target,
                    state: {
                        suppressMessage: false,
                        tbody: row.element,
                        isGridValidation: true,
                        is606: true,
                        //ignoreBlankQuantity: ignoreBlankQuantity
                    }
                }));

            });
        });

        //after: check required kotei if all quantity isn't blank
        $.each(validateKotei, function (index, elem) {
            validations.push(page.detail.tabs.haigohyo_tab.detail.validator.validate({
                targets: elem.targets,
                state: elem.state
            }));
        });

        return App.async.all(validations);
    };

    /**
     * validate hiju_sui before open dialog。
     */
    _606_GenzairyoKakunin_Dialog.tokuseichiTabValidateListFileds = function (keySample) {
        var validations = [];

        page.detail.tabs.tokuseichi_tab.detail.dataTable.dataTable("each", function (row, index) {
            row.element.find("input:not([readonly],[disabled])").each(function (index, elem) {
                target = $(elem);

                var prop = target.attr("data-prop"),
                    colKey = target.closest("td").attr("data-key-sample");

                if (!App.isUndefOrNullOrStrEmpty(colKey)) {
                    colKey = colKey.replace("sample-", "");
                }

                if (prop != "hiju_sui" && prop != "hiju") {
                    return true;
                }

                if (colKey != keySample) {
                    return true;
                }

                validations.push(page.detail.tabs.tokuseichi_tab.detail.validator.validate({
                    targets: target,
                    state: {
                        suppressMessage: false,
                        tbody: row.element,
                        isGridValidation: true,
                        is606: true
                    }
                }));

            });
        });

        return App.async.all(validations);
    };

    /**
     * validate yoryo, su_iri before open dialog。
     */
    _606_GenzairyoKakunin_Dialog.kihonTabValidateListFileds = function () {
        var validations = [];

        page.detail.tabs.kihonjohyo_tab.detail.dataTable.dataTable("each", function (row, index) {
            row.element.find("input,select:not([readonly],[disabled])").each(function (index, elem) {
                target = $(elem);

                var prop = target.attr("data-prop");

                if (prop != "yoryo" && prop != "su_iri" && prop != "cd_tani") {
                    return true;
                }

                validations.push(page.detail.tabs.kihonjohyo_tab.detail.validator.validate({
                    targets: target,
                    state: {
                        suppressMessage: false,
                        tbody: row.element,
                        isGridValidation: true
                    }
                }));

            });
        });

        return App.async.all(validations);
    };

    /**
     * validate heikinzyu, yukobudomari before open dialog。
     */
    _606_GenzairyoKakunin_Dialog.genkashisanTabValidateListFileds = function (keySample) {
        var validations = [];

        page.detail.tabs.genkashisan_tab.detail.dataTable.dataTable("each", function (row, index) {
            row.element.find("input:not([readonly],[disabled])").each(function (index, elem) {
                target = $(elem);

                var prop = target.attr("data-prop"),
                    colKey = target.closest("td").attr("data-key-sample");

                if (!App.isUndefOrNullOrStrEmpty(colKey)) {
                    colKey = colKey.replace("sample-", "");
                }

                if (prop != "heikinzyu" && prop != "yukobudomari") {
                    return true;
                }

                if (colKey != keySample) {
                    return true;
                }

                validations.push(page.detail.tabs.genkashisan_tab.detail.validator.validate({
                    targets: target,
                    state: {
                        suppressMessage: false,
                        tbody: row.element,
                        isGridValidation: true,
                        is606: true
                    }
                }));

            });
        });

        return App.async.all(validations);
    };

    /**
     * Excute all function validate above。
     */
    _606_GenzairyoKakunin_Dialog.ValidateListFieldsAllTabs = function (keySample) {
        return App.async.all({
            haigo: _606_GenzairyoKakunin_Dialog.haigohyoTabValidateListFileds(keySample),
            tokuseichi: _606_GenzairyoKakunin_Dialog.tokuseichiTabValidateListFileds(keySample),
            kihon: _606_GenzairyoKakunin_Dialog.kihonTabValidateListFileds(),
            genka: _606_GenzairyoKakunin_Dialog.genkashisanTabValidateListFileds(keySample)
        }).fail(function (err) { });
    };

    /**
     * ダイアログの初期化処理を行います。
     */
    _606_GenzairyoKakunin_Dialog.initialize = function () {

        var element = $("#_606_GenzairyoKakunin_Dialog"),
            contentHeight = $(window).height() * 80 / 100;

        _606_GenzairyoKakunin_Dialog.element = element;
        element.find(".modal-body").css("max-height", contentHeight);
        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        _606_GenzairyoKakunin_Dialog.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#_606_GenzairyoKakunin_Dialog .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body .detail",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        _606_GenzairyoKakunin_Dialog.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#_606_GenzairyoKakunin_Dialog .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body .detail",
                show: function () {
                    element.find(".alert-message").show();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                }
            });

        element.find(".modal-dialog").draggable({
            drag: true,
        });

        _606_GenzairyoKakunin_Dialog.initializeControlEvent();
        _606_GenzairyoKakunin_Dialog.header.initialize();
        //_606_GenzairyoKakunin_Dialog.loadMasterData();
        _606_GenzairyoKakunin_Dialog.loadDialogs();
    };

    /**
     * ダイアログコントロールへのイベントの紐づけを行います。
     */
    _606_GenzairyoKakunin_Dialog.initializeControlEvent = function () {
        var element = _606_GenzairyoKakunin_Dialog.element;

        //TODO: 画面全体で利用するコントロールのイベントの紐づけ処理をここに記述します。
        element.on("hidden.bs.modal", _606_GenzairyoKakunin_Dialog.hidden);
        element.on("show.bs.modal", _606_GenzairyoKakunin_Dialog.show);
        element.on("shown.bs.modal", _606_GenzairyoKakunin_Dialog.shown);
        element.on("click", ".save", _606_GenzairyoKakunin_Dialog.commands.confirm);
    };

    /**
     * ダイアログ非表示時処理を実行します。
     */
    _606_GenzairyoKakunin_Dialog.hidden = function (e) {
        var element = _606_GenzairyoKakunin_Dialog.element;

        //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
        element.find(":input:not([type='checkbox']):not([type='radio'])").val("");
        element.find("input[type='checkbox']").prop("checked", false);
        //TODO:radioボタンの初期表示は、画面要件に合わせて記述します。

        _606_GenzairyoKakunin_Dialog.detail.dataTable.dataTable("clear");
        element.find(".dt-foot input").removeClass("selectedInput");

        element.findP("data_count").text("");
        element.findP("data_count_total").text("");

        var items = element.find(".search-criteria :input:not(button)");
        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            _606_GenzairyoKakunin_Dialog.setColValidStyle(item);
        }

        _606_GenzairyoKakunin_Dialog.element.find(".save").prop("disabled", true);
        _606_GenzairyoKakunin_Dialog.notifyInfo.clear();
        _606_GenzairyoKakunin_Dialog.notifyAlert.clear();

        if (!App.isUndefOrNull(_606_GenzairyoKakunin_Dialog.values.data)) {
            delete _606_GenzairyoKakunin_Dialog.values.data;
        }

        delete _606_GenzairyoKakunin_Dialog.values.abura_sho_juryu;
        delete _606_GenzairyoKakunin_Dialog.values.suiso_no_juryo;
        delete _606_GenzairyoKakunin_Dialog.values.dblSakinGokeiryo;
        delete _606_GenzairyoKakunin_Dialog.values.dblSuisoGokeiryo;
        delete _606_GenzairyoKakunin_Dialog.values.dblYusoGokeiryo;
        _606_GenzairyoKakunin_Dialog.values.dataBackup = {};
        _606_GenzairyoKakunin_Dialog.detail.options.validationsShisakuList = {};
    };

    /**
     * ダイアログ表示時処理を実行します。
     */
    _606_GenzairyoKakunin_Dialog.show = function () {
        _606_GenzairyoKakunin_Dialog.loadMasterData();
    }

    /**
     * ダイアログ表示時処理を実行します。
     */
    _606_GenzairyoKakunin_Dialog.shown = function (e) {
        //初回起動時にdatatable作成
        if (App.isUndefOrNull(_606_GenzairyoKakunin_Dialog.detail.fixedColumnIndex)) {
            _606_GenzairyoKakunin_Dialog.detail.initialize();
        }

        _606_GenzairyoKakunin_Dialog.repareBinding();
        _606_GenzairyoKakunin_Dialog.detail.element.find(":input:not([readonly]):visible:first").click();
    };

    /**
     * ダイアログ表示時処理を実行します。
     */
    _606_GenzairyoKakunin_Dialog.repareBinding = function () {
        var obj = {
            Items: [],
            seq_shisaku: _606_GenzairyoKakunin_Dialog.values.data.seq_shisaku
        };

        $.each(_606_GenzairyoKakunin_Dialog.values.data.haigo, function (index, item) {
            $.each(_606_GenzairyoKakunin_Dialog.values.data.shisaku[obj.seq_shisaku], function (i, it) {
                if (i.indexOf("quantity_") < 0) {
                    if (i === "juryo_shiagari_g") {
                        item[i] = it;
                    }

                    if (i === "flg_shisanIrai") {
                        item[i] = it;
                    }

                    return true;
                }

                var spli = i.split("_"),
                    name = spli[0] + "_" + spli[1] + "_" + spli[2];

                if (index == name) {
                    item[i] = it;
                }

            })

            obj.Items.push(item);
        });

        _606_GenzairyoKakunin_Dialog.detail.bind(obj);
    }

    /**
     * マスターデータのロード処理を実行します。
     */
    _606_GenzairyoKakunin_Dialog.loadMasterData = function () {
        var zoku_kotei = _606_GenzairyoKakunin_Dialog.element.findP("zoku_kotei"),
            pt_kotei = $("#pt_kotei").val();

        zoku_kotei.children().remove();

        switch (pt_kotei) {
            case App.settings.app.pt_kotei.chomieki_1://調味料1液タイプ

                App.ui.appendOptions(
                    zoku_kotei,
                    "cd_literal",
                    "nm_literal",
                    $.grep(page.values.masterData.KoteiZokusei, function (n, i) {
                        //殺菌調味液
                        //１液充填
                        return (n.cd_literal == App.settings.app.zoku_kotei.sakkinchomi_eki || n.cd_literal == App.settings.app.zoku_kotei.eki_juten1);
                    }),
                    true
                );
                break;

            case App.settings.app.pt_kotei.chomieki_2://調味料2液タイプ

                App.ui.appendOptions(
                    zoku_kotei,
                    "cd_literal",
                    "nm_literal",
                    $.grep(page.values.masterData.KoteiZokusei, function (n, i) {
                        //殺菌調味液
                        //２液油相
                        //２液水相
                        return (n.cd_literal == App.settings.app.zoku_kotei.sakkinchomi_eki || n.cd_literal == App.settings.app.zoku_kotei.eki_yu_sho2 || n.cd_literal == App.settings.app.zoku_kotei.eki_suiso2);
                    }),
                    true
                );
                break;

            case App.settings.app.pt_kotei.sonohokaeki://その他・加食タイプ

                App.ui.appendOptions(
                    zoku_kotei,
                    "cd_literal",
                    "nm_literal",
                    $.grep(page.values.masterData.KoteiZokusei, function (n, i) {
                        //ソース
                        //別充填具材
                        return (n.cd_literal == App.settings.app.zoku_kotei.Sosu || n.cd_literal == App.settings.app.zoku_kotei.betsu_juten_guzai);
                    }),
                    true
                );
                break;

            default:
                break;
        }
    };

    /**
     * 共有のダイアログのロード処理を実行します。
     */
    _606_GenzairyoKakunin_Dialog.loadDialogs = function () {

        if ($.find("#ConfirmDialog").length == 0) {
            return App.async.all({
                confirmDialog: $.get(_606_GenzairyoKakunin_Dialog.urls.confirmDialog)
            }).then(function (result) {
                $("#dialog-container").append(result.successes.confirmDialog);
                _606_GenzairyoKakunin_Dialog.dialogs.confirmDialog = ConfirmDialog;
            });
        } else {
            _606_GenzairyoKakunin_Dialog.dialogs.confirmDialog = ConfirmDialog;
        }
    };

    /**
     * ダイアログヘッダー　バリデーションルールを定義します。
     */
    _606_GenzairyoKakunin_Dialog.header.options.validations = {
    };

    /**
     * ダイアログヘッダーの初期化処理を行います。
     */
    _606_GenzairyoKakunin_Dialog.header.initialize = function () {

        var element = _606_GenzairyoKakunin_Dialog.element.find(".header");
        _606_GenzairyoKakunin_Dialog.header.validator = element.validation(_606_GenzairyoKakunin_Dialog.createValidator(_606_GenzairyoKakunin_Dialog.header.options.validations));
        _606_GenzairyoKakunin_Dialog.header.element = element;
    };

    /**
     * ダイアログヘッダー　バリデーションルールを定義します。
     */
    _606_GenzairyoKakunin_Dialog.detail.options.validations = {

    };

    /**
     * 画面明細の初期化処理を行います。
     */
    _606_GenzairyoKakunin_Dialog.detail.initialize = function () {

        var element = _606_GenzairyoKakunin_Dialog.element.find(".detail"),
            table = element.find(".datatable"),
            offsetHeight = $(window).height() * 15 / 100,
            datatable = table.dataTable({
                height: 300,
                //resize: true,
                //resizeOffset: offsetHeight,
                //列固定横スクロールにする場合は、下記3行をコメント解除
                //fixedColumn: true,               //列固定の指定
                //fixedColumns: 1,                 //固定位置を指定（左端を0としてカウント）
                //innerWidth: 700,                 //可動列の合計幅を指定
                onselect: _606_GenzairyoKakunin_Dialog.detail.select,
                onchange: _606_GenzairyoKakunin_Dialog.detail.change
            });

        table = element.find(".datatable");        //列固定にした場合DOM要素が再作成されるため、変数を再取得

        table.on("focusin", "td", function (e) {
            table.find(".selectedInput").removeClass("selectedInput");
            $(e.target).addClass("selectedInput");
        });

        table.on("click", "td", function (e) {
            table.find(".selectedInput").removeClass("selectedInput");
            $(e.target).addClass("selectedInput");
        });

        _606_GenzairyoKakunin_Dialog.detail.options.validationsShisakuList = {};
        _606_GenzairyoKakunin_Dialog.detail.validator = element.validation(_606_GenzairyoKakunin_Dialog.createValidator(_606_GenzairyoKakunin_Dialog.detail.options.validations));
        _606_GenzairyoKakunin_Dialog.detail.element = element;
        _606_GenzairyoKakunin_Dialog.detail.dataTable = datatable;
        // 行選択時に利用するテーブルインデックスを指定します
        _606_GenzairyoKakunin_Dialog.detail.fixedColumnIndex = element.find(".fix-columns").length;
    };

    /**
     * ダイアログ明細へのデータバインド処理を行います。
     */
    _606_GenzairyoKakunin_Dialog.detail.bind = function (data, isNewData) {
        var i, l, item, dataSet, colIndex;

        colIndex = data.seq_shisaku ? data.seq_shisaku : 0;
        seq_shisaku = data.seq_shisaku ? data.seq_shisaku : 0;
        data = (data.Items) ? data.Items : data;

        dataSet = App.ui.page.dataSet();
        _606_GenzairyoKakunin_Dialog.detail.data = dataSet;
        _606_GenzairyoKakunin_Dialog.detail.dataTable.dataTable("clear");

        // fillter quantity is null or blank
        var fillter = $.grep(data, function (item) {

            if (App.isUndefOrNullOrStrEmpty(item["quantity_" + item.cd_kotei + "_" + item.seq_kotei + "_" + seq_shisaku])) {
                return false;
            }
            (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
            return true;
        });

        var cd_kotei = "",
            i = 0;

        while (i < fillter.length) {

            var data = fillter[i],
                propQuantity,
                cloneData = {},
                item = {};

            if (data.cd_kotei != cd_kotei) {
                //clone data for bind kotei row
                cloneData = $.extend(true, {}, fillter[i]);
                item = cloneData;
                item.nm_genryo = "";
                item.tanka = null;
                item["quantity_" + data.cd_kotei + "_" + data.seq_kotei + "_" + seq_shisaku] = null;
            } else {
                //data for bind genryo row
                item = fillter[i];
                i = i + 1;
            }

            propQuantity = "quantity_" + item.cd_kotei + "_" + item.seq_kotei + "_" + seq_shisaku;

            item["genryohi"] = null;
            item["genryohi1"] = null;
            item["wariai"] = null;

            _606_GenzairyoKakunin_Dialog.detail.dataTable.dataTable("addRow", function (row) {

                row.find("tr td").addClass("sample-" + colIndex);

                if (data.cd_kotei == cd_kotei) {
                    row.find(".quantity").attr("data-prop", propQuantity).attr("data-number-format-toFixed", Number(page.values.keta_shosu));
                }

                row.form(_606_GenzairyoKakunin_Dialog.detail.options.bindOption).bind(item);

                _606_GenzairyoKakunin_Dialog.detail.controlDisplayItem(row, item.cd_kotei != cd_kotei)

                if (item.flg_shisanIrai == true || page.values.modeDisp == App.settings.app.m_shisaku_data.etsuran || page.values.modeDisp == App.settings.app.m_shisaku_data.copy) {
                    row.findP(propQuantity).prop("disabled", true);
                }

                _606_GenzairyoKakunin_Dialog.detail.options.validationsShisakuList[propQuantity] = _606_GenzairyoKakunin_Dialog.values.validationsCellDefault;

                return row;
            });

            cd_kotei = item.cd_kotei;
        }

        _606_GenzairyoKakunin_Dialog.detail.options.validations = $.extend(true, {}, _606_GenzairyoKakunin_Dialog.detail.options.validations, _606_GenzairyoKakunin_Dialog.detail.options.validationsShisakuList);
        _606_GenzairyoKakunin_Dialog.detail.validator = _606_GenzairyoKakunin_Dialog.detail.element.validation(_606_GenzairyoKakunin_Dialog.createValidator(_606_GenzairyoKakunin_Dialog.detail.options.validations));

        var dataCalculate = _606_GenzairyoKakunin_Dialog.values.data;
        dataCalculate.shisaku = {};
        dataCalculate.shisaku[colIndex] = item;

        _606_GenzairyoKakunin_Dialog.values.dataBackup = dataCalculate;

        _606_GenzairyoKakunin_Dialog.validateAll().then(function () {

            _606_GenzairyoKakunin_Dialog.detail.calculateTotalRow(true);

            // sum total follow each kotei
            _606_GenzairyoKakunin_Dialog.detail.sumKoteiQuantity();

            //after binding basic data, calculate 原料費(KG), 原料費(1個あたり), 割合
            _606_GenzairyoKakunin_Dialog.detail.recalculation(dataCalculate);
        }).fail(function () {
            _606_GenzairyoKakunin_Dialog.detail.dataDefaultError();
        });

        //バリデーションを実行します。
        //_606_GenzairyoKakunin_Dialog.detail.validateList(true);
    };

    /**
     * sum total follow each kotei (工程 = 油相】or  工程 = 殺菌調味液 & 水相】)
     */
    _606_GenzairyoKakunin_Dialog.detail.sumKoteiQuantity = function () {
        var element = _606_GenzairyoKakunin_Dialog.detail.element,
            data = _606_GenzairyoKakunin_Dialog.values.data,
            abura_sho_juryu = 0,
            suiso_no_juryo = 0,
            dblSakinGokeiryo = 0,
            dblSuisoGokeiryo = 0,
            dblYusoGokeiryo = 0;

        var items = _606_GenzairyoKakunin_Dialog.detail.data.findAll(function (item, entity) {
            return entity.state !== App.ui.page.dataSet.status.Deleted;
        });

        $.each(items, function (index, item) {
            $.each(item, function (pro, val) {

                if (pro.indexOf("quantity_") < 0) {
                    return true;
                }

                var haigo, zoku_kotei;

                val = page.detail.checkUndefi(val);

                var spli = pro.split("_"),
                    haigo = data.haigo[spli[0] + "_" + spli[1] + "_" + spli[2]];

                if (!haigo) {
                    return true;
                }
                        
                zoku_kotei = page.confirmKoteiZokusei(haigo.zoku_kotei);

                if (zoku_kotei.value1 == App.settings.app.kote_value1.chomiryoType) {
                    //殺菌調味液（リテラルマスタのvalue1 =1  AND value2 = 1）の「量」の合計　
                    dblSakinGokeiryo = new BigNumber(stringNumbers(dblSakinGokeiryo)).plus(stringNumbers(page.detail.tabs.genkashisan_tab.detail.findKoteiZokusei(haigo, val, 1))).toNumber();
                    //水相（リテラルマスタのvalue1 =1  AND value2 = 2）の「量」の合計　
                    dblSuisoGokeiryo = new BigNumber(stringNumbers(dblSuisoGokeiryo)).plus(stringNumbers(page.detail.tabs.genkashisan_tab.detail.findKoteiZokusei(haigo, val, 2))).toNumber();
                    //油相（リテラルマスタのvalue1 =1  AND value2 = 3）の「量」の合計　
                    dblYusoGokeiryo = new BigNumber(stringNumbers(dblYusoGokeiryo)).plus(stringNumbers(page.detail.tabs.genkashisan_tab.detail.findKoteiZokusei(haigo, val, 3))).toNumber();

                    if (zoku_kotei.value2 == App.settings.app.kote_value2.aburasho) {
                        abura_sho_juryu = new BigNumber(stringNumbers(abura_sho_juryu)).plus(stringNumbers(val)).toNumber();
                    }

                    if (zoku_kotei.value2 == App.settings.app.kote_value2.chomiryo || zoku_kotei.value2 == App.settings.app.kote_value2.suisho) {
                        suiso_no_juryo = new BigNumber(stringNumbers(suiso_no_juryo)).plus(stringNumbers(val)).toNumber();;
                    }
                }
            });
        });

        _606_GenzairyoKakunin_Dialog.values.dblSakinGokeiryo = dblSakinGokeiryo;
        _606_GenzairyoKakunin_Dialog.values.dblSuisoGokeiryo = dblSuisoGokeiryo;
        _606_GenzairyoKakunin_Dialog.values.dblYusoGokeiryo = dblYusoGokeiryo;
        _606_GenzairyoKakunin_Dialog.values.abura_sho_juryu = abura_sho_juryu;
        _606_GenzairyoKakunin_Dialog.values.suiso_no_juryo = suiso_no_juryo;

    }

    /**
     * calculate 原料費(KG), 原料費(1個あたり), 割合 in each row。
     */
    _606_GenzairyoKakunin_Dialog.detail.recalculation = function (data) {
        var element = _606_GenzairyoKakunin_Dialog.detail.element.find(".datatable"),
            total_quantity = page.detail.checkUndefi(element.findP("total_quantity").val()),
            total_genryohi = 0,
            total_genryohi1 = 0;

        //その他・加食タイプ
        if (data.shisaku_hin.pt_kotei == App.settings.app.pt_kotei.sonohokaeki) {

            var items = _606_GenzairyoKakunin_Dialog.detail.data.findAll(function (item, entity) {
                return entity.state !== App.ui.page.dataSet.status.Deleted;
            });

            $.each(items, function (index, item) {
                var tokuseichi = data["dataToku"]["sample-" + data.seq_shisaku],
                genka_shisan_col = data["genka_shisan"],
                cs_genryo = 0,  //「金額計」
                row, entity, id;

                $.each(item, function (pro, val) {

                    var quantity = 0;
                    var haigo, zoku_kotei;

                    if (pro.indexOf("quantity_") < 0) {
                        return true;
                    }

                    row = element.findP(pro).closest('tbody');
                    id = row.attr("data-key"),
                    entity = _606_GenzairyoKakunin_Dialog.detail.data.entry(id);

                    val = page.detail.checkUndefi(val);

                    var spli = pro.split("_"),
                        haigo = data.haigo[spli[0] + "_" + spli[1] + "_" + spli[2]];

                    if (!haigo) {
                        return true;
                    }

                    zoku_kotei = page.confirmKoteiZokusei(haigo.zoku_kotei);

                    //ソース, 別充填具材
                    if (zoku_kotei.value1 == App.settings.app.kote_value1.otherType) {
                        //「量」 × 「単価」 ÷ ( 「歩留」 ÷ 100 )
                        quantity = new BigNumber(stringNumbers(val)).times(stringNumbers(haigo.tanka)).div(stringNumbers(new BigNumber(stringNumbers(haigo.budomari)).div(stringNumbers(App.settings.app.keisansikiyo.value_100)).toNumber())).toNumber();
                        cs_genryo = new BigNumber(stringNumbers(cs_genryo)).plus(stringNumbers(page.detail.checkUndefi(quantity))).toNumber();
                    }
                });

                var reberu = ""; //比重加算量（ｇ平均充填量ｘ比重）:「平均充填量」 × 「比重」

                if (checkEvalBeforCalcu([data.shisaku_hin.su_iri, data.shisaku_hin.yoryo])) {
                    reberu = new BigNumber(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.yoryo))).times(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.su_iri))).toNumber();//レベル量(g内容量ｘ入数) : 「容量」 × 「入数」
                }

                var genryohiGenryo = undefined;
                //「金額計」 ÷ 「仕上がり合計重量」 × 「平均充填量」 ÷ 1000 ÷ ( 「有効歩留（％）」 ÷ 100 )
                if (checkEvalBeforCalcu([genka_shisan_col.yukobudomari, genka_shisan_col.heikinzyu])) {
                    try {
                        if (item.juryo_shiagari_g != "" && item.juryo_shiagari_g != null && item.juryo_shiagari_g != undefined) {
                            cs_genryo = new BigNumber(stringNumbers(cs_genryo, true)).div(stringNumbers(item.juryo_shiagari_g, true)).times(stringNumbers(genka_shisan_col.heikinzyu, true)).div(stringNumbers(App.settings.app.keisansikiyo.value_1000, true)).div(new BigNumber(stringNumbers(genka_shisan_col.yukobudomari, true)).div(stringNumbers(App.settings.app.keisansikiyo.value_100, true)).toNumber() + "").toNumber();

                            if (cs_genryo == 0) {
                                genryohiGenryo = "0.00";
                            }
                        } else {
                            cs_genryo = "0.00";
                            genryohiGenryo = "0.00";
                        }
                    } catch (ex) {
                        cs_genryo = "";
                        genryohiGenryo = "0.00";
                    }
                } else {
                    cs_genryo = "";
                    genryohiGenryo = "0.00";
                }

                cs_genryo = page.detail.checkUndefi(cs_genryo);

                //レベル量(g内容量ｘ入数) 
                reberu = page.detail.formatNumber(page.detail.checkUndefi(reberu), 2, "0.00", 100);

                var genryohi1 = "",// 原料費（１本当）:「原料費/ケース」 ÷ 「入数」
                    genryohi = "";//原料費（ｋg) :「原料費/ケース」 ÷（ 「レベル量(g内容量ｘ入数)」×「比重」） × 1000

                if (genryohiGenryo != undefined) {
                    genryohi1 = genryohiGenryo;
                } else if (checkEvalBeforCalcu([data.shisaku_hin.su_iri, genka_shisan_col.yukobudomari])) {
                    genryohi1 = new BigNumber(stringNumbers(cs_genryo)).div(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.su_iri))).toNumber();
                }
                genryohi1 = page.detail.checkUndefi(genryohi1);

                if (genryohiGenryo != undefined) {
                    genryohi = genryohiGenryo;
                } else if (checkEvalBeforCalcu([tokuseichi.hiju, genka_shisan_col.yukobudomari])) {
                    genryohi = new BigNumber(stringNumbers(cs_genryo)).div(stringNumbers(new BigNumber(stringNumbers(reberu.forData)).times(stringNumbers(page.detail.checkUndefi(tokuseichi.hiju))).toNumber())).times(stringNumbers(App.settings.app.keisansikiyo.value_1000)).toNumber();
                }

                //原料費（ｋg)
                genryohi = page.detail.formatNumber(page.detail.checkUndefi(genryohi), 2, "0.00", 100);
                entity["genryohi"] = page.detail.tabs.genkashisan_tab.detail.stringForData(genryohi.forDisp);
                row.findP("genryohi").val(genryohi.forDisp);

                //原料費(1個あたり)
                genryohi1 = page.detail.formatNumber(page.detail.checkUndefi(genryohi1), 2, "0.00", 100);
                entity["genryohi1"] = page.detail.tabs.genkashisan_tab.detail.stringForData(genryohi1.forDisp);
                row.findP("genryohi1").val(genryohi1.forDisp);

                _606_GenzairyoKakunin_Dialog.detail.data.update(entity);

                total_genryohi = new BigNumber(stringNumbers(total_genryohi)).plus(stringNumbers(entity.genryohi)).toNumber();
                total_genryohi1 = new BigNumber(stringNumbers(total_genryohi1)).plus(stringNumbers(entity.genryohi1)).toNumber();
            })
        }

        //調味料1液タイプ, 調味料2液タイプ
        if (data.shisaku_hin.pt_kotei == App.settings.app.pt_kotei.chomieki_1 || data.shisaku_hin.pt_kotei == App.settings.app.pt_kotei.chomieki_2) {

            var items = _606_GenzairyoKakunin_Dialog.detail.data.findAll(function (item, entity) {
                return entity.state !== App.ui.page.dataSet.status.Deleted;
            });

            $.each(items, function (index, item) {
                var tokuseichi = data["dataToku"]["sample-" + data.seq_shisaku],
                genka_shisan_col = data["genka_shisan"],
                abura_sho_juryu = _606_GenzairyoKakunin_Dialog.values.abura_sho_juryu,  //「量」の水相工程
                suiso_no_juryo = _606_GenzairyoKakunin_Dialog.values.suiso_no_juryo, //「量」の油相工程  
                dblSakinGokeiryo = _606_GenzairyoKakunin_Dialog.values.dblSakinGokeiryo, //殺菌調味液重量(g)
                dblSuisoGokeiryo = _606_GenzairyoKakunin_Dialog.values.dblSuisoGokeiryo, //水相合計重量(g) 
                dblYusoGokeiryo = _606_GenzairyoKakunin_Dialog.values.dblYusoGokeiryo, //油相合計重量(g) 
                row, entity, id;

                var dblYusoZyuten = "",//0,//充填量油相
                    dblSuisoZyuten = "",//0,//充填量水相
                    dblSuisoHiju = 0,//水相比重
                    dblYusoHiju = 0.92,//油相比重  
                    dblSeihinYoryo = data.shisaku_hin.yoryo;//製品容量

                $.each(item, function (pro, val) {

                    if (pro.indexOf("quantity_") < 0) {
                        return true;
                    }

                    row = element.findP(pro).closest('tbody');
                    id = row.attr("data-key"),
                    entity = _606_GenzairyoKakunin_Dialog.detail.data.entry(id);

                    var quantity = 0;
                    var haigo, zoku_kotei;

                    abura_aihaka = 0;
                    suiso_tanka = 0;
                    cs_genryo = "";

                    val = page.detail.checkUndefi(val);

                    var spli = pro.split("_"),
                        haigo = data.haigo[spli[0] + "_" + spli[1] + "_" + spli[2]];

                    if (!haigo) {
                        return true;
                    }

                    zoku_kotei = page.confirmKoteiZokusei(haigo.zoku_kotei);

                    if (zoku_kotei.value1 == App.settings.app.kote_value1.chomiryoType) {
                        //油相合計重量
                        //油相合計単価
                        //【工程 = 油相】 
                        if (zoku_kotei.value2 == App.settings.app.kote_value2.aburasho) {
                            //油相合計単価
                            //「量」 × 「単価」 ÷ ( 「歩留」 ÷ 100 ) の 油相の合計
                            quantity = new BigNumber(stringNumbers(val)).times(stringNumbers(page.detail.checkUndefi(haigo.tanka))).div(stringNumbers(new BigNumber(stringNumbers(page.detail.checkUndefi(haigo.budomari))).div(stringNumbers(App.settings.app.keisansikiyo.value_100)).toNumber())).toNumber();
                            abura_aihaka = new BigNumber(stringNumbers(abura_aihaka)).plus(stringNumbers(page.detail.checkUndefi(quantity))).toNumber();
                        }

                        //水相合計重量
                        //水相合計単価
                        //【工程 = 殺菌調味液 & 水相】
                        if (zoku_kotei.value2 == App.settings.app.kote_value2.chomiryo || zoku_kotei.value2 == App.settings.app.kote_value2.suisho) {
                            //「量」 × 「単価」 ÷ ( 「歩留」 ÷ 100 ) の 水相の合計
                            quantity = new BigNumber(stringNumbers(val)).times(stringNumbers(page.detail.checkUndefi(haigo.tanka))).div(stringNumbers(new BigNumber(stringNumbers(page.detail.checkUndefi(haigo.budomari))).div(stringNumbers(App.settings.app.keisansikiyo.value_100)).toNumber())).toNumber();
                            suiso_tanka = new BigNumber(stringNumbers(suiso_tanka)).plus(stringNumbers(page.detail.checkUndefi(quantity))).toNumber();
                        }
                    }
                });

                var flg_dblSuisoZyuten = false,
                    flg_dblYusoZyuten = false;

                dblSuisoHiju = page.detail.checkUndefi(genka_shisan_col.hiju_sui);//特性値．水相比重

                //調味料2液タイプ、【gram】
                if (data.shisaku_hin.pt_kotei == App.settings.app.pt_kotei.chomieki_2 && data.shisaku_hin.cd_tani == App.settings.app.cd_tani.gram) {
                    //[殺菌調味液重量(g) +  水相合計重量(g) ] X  																									
                    //{ 製品容量(g)  /  [ 殺菌調味液重量(g) + ドレ水相合計重量(g) ) + ドレ油相合計重量(g) ] }			
                    if (checkEvalBeforCalcu([data.shisaku_hin.yoryo])) {
                        dblSuisoZyuten = new BigNumber(stringNumbers(new BigNumber(stringNumbers(dblSakinGokeiryo)).plus(stringNumbers(dblSuisoGokeiryo)).toNumber())).times(stringNumbers(new BigNumber(stringNumbers(dblSeihinYoryo)).div(stringNumbers(new BigNumber(stringNumbers(dblSakinGokeiryo)).plus(stringNumbers(dblSuisoGokeiryo)).plus(stringNumbers(dblYusoGokeiryo)).toNumber())).toNumber())).toNumber();
                    }
                    flg_dblSuisoZyuten = true;

                    //油相合計重量(g)  X  																								
                    //{ 製品容量(g)  /  [ 殺菌調味液重量(g) + ドレ水相合計重量(g) ) + ドレ油相合計重量(g) ] }			
                    if (checkEvalBeforCalcu([data.shisaku_hin.yoryo])) {
                        dblYusoZyuten = new BigNumber(stringNumbers(dblYusoGokeiryo)).times(stringNumbers(new BigNumber(stringNumbers(dblSeihinYoryo)).div(stringNumbers(new BigNumber(stringNumbers(dblSakinGokeiryo)).plus(stringNumbers(dblSuisoGokeiryo)).plus(stringNumbers(dblYusoGokeiryo)).toNumber())).toNumber())).toNumber();
                    }
                    flg_dblYusoZyuten = true;
                }

                //調味料2液タイプ、【ml】
                if (data.shisaku_hin.pt_kotei == App.settings.app.pt_kotei.chomieki_2 && data.shisaku_hin.cd_tani == App.settings.app.cd_tani.ml) {
                    //[殺菌調味液重量(g) +  水相合計重量(g) ] X  																														
                    //{ 製品容量(ml)  / { [(殺菌調味液重量(g) + ドレ水相合計重量(g) )/水相比重] + [ドレ油相合計重量(g) / 油相比重] } }		
                    if (checkEvalBeforCalcu([data.shisaku_hin.yoryo])) {
                        var numCal = new BigNumber(stringNumbers(dblSakinGokeiryo)).plus(stringNumbers(dblSuisoGokeiryo)).toNumber(),
                            numCal1 = new BigNumber(stringNumbers(new BigNumber(stringNumbers(dblSakinGokeiryo)).plus(stringNumbers(dblSuisoGokeiryo)).toNumber())).div(stringNumbers(dblSuisoHiju)).toNumber();

                        numCal1 = new BigNumber(stringNumbers(numCal1)).plus(stringNumbers(new BigNumber(stringNumbers(dblYusoGokeiryo)).div(stringNumbers(dblYusoHiju)).toNumber())).toNumber();
                        dblSuisoZyuten = new BigNumber(stringNumbers(numCal)).times(stringNumbers(new BigNumber(stringNumbers(dblSeihinYoryo)).div(stringNumbers(numCal1)).toNumber())).toNumber();
                    }
                    flg_dblSuisoZyuten = true;

                    //油相合計重量(g)  X  																														
                    //{ 製品容量(ml)  / { [(殺菌調味液重量(g) + ドレ水相合計重量(g) )/水相比重] + [ドレ油相合計重量(g) /油相比重] } }		
                    if (checkEvalBeforCalcu([data.shisaku_hin.yoryo])) {
                        dblYusoZyuten = new BigNumber(stringNumbers(dblYusoGokeiryo)).times(stringNumbers(new BigNumber(stringNumbers(dblSeihinYoryo)).div(stringNumbers(new BigNumber(stringNumbers(new BigNumber(stringNumbers(new BigNumber(stringNumbers(dblSakinGokeiryo)).plus(stringNumbers(dblSuisoGokeiryo)).toNumber())).div(stringNumbers(dblSuisoHiju)).toNumber())).plus(stringNumbers(new BigNumber(stringNumbers(dblYusoGokeiryo)).div(stringNumbers(dblYusoHiju)).toNumber())).toNumber())).toNumber())).toNumber();
                    }
                    flg_dblYusoZyuten = true;
                }

                //調味料1液タイプ、【ml,g】
                if (data.shisaku_hin.pt_kotei == App.settings.app.pt_kotei.chomieki_1 && (data.shisaku_hin.cd_tani == App.settings.app.cd_tani.ml || data.shisaku_hin.cd_tani == App.settings.app.cd_tani.gram)) {
                    // 製品容量 X 製品比重					
                    if (checkEvalBeforCalcu([tokuseichi.hiju, data.shisaku_hin.yoryo])) {
                        dblSuisoZyuten = new BigNumber(stringNumbers(dblSeihinYoryo)).times(stringNumbers(page.detail.checkUndefi(tokuseichi.hiju))).toNumber();
                    }
                    flg_dblSuisoZyuten = true;
                }

                //充填量水相
                dblSuisoZyuten = page.detail.checkUndefi(dblSuisoZyuten);
                //充填量油相（ｇ）
                dblYusoZyuten = page.detail.checkUndefi(dblYusoZyuten);

                //充填量水相（ｇ）
                var zyusui;
                if (flg_dblSuisoZyuten) {
                    dblSuisoZyuten = page.detail.formatNumber(dblSuisoZyuten, 2, "0.00");
                    zyusui = page.detail.tabs.genkashisan_tab.detail.stringForData(dblSuisoZyuten.forDisp);
                }

                //充填量油相（ｇ）
                var zyuabura;
                if (flg_dblYusoZyuten) {
                    dblYusoZyuten = page.detail.formatNumber(dblYusoZyuten, 2, "0.00");
                    zyuabura = page.detail.tabs.genkashisan_tab.detail.stringForData(dblYusoZyuten.forDisp);
                }

                var reberu = "";   //レベル量(g内容量ｘ入数) : 「容量」 × 「入数」

                if (checkEvalBeforCalcu([data.shisaku_hin.yoryo, data.shisaku_hin.su_iri])) {
                    reberu = new BigNumber(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.yoryo))).times(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.su_iri))).toNumber();//レベル量(g内容量ｘ入数) : 「容量」 × 「入数」
                }

                //（「水相合計単価」 ÷ 「水相合計重量」 × 「充填量水相（ｇ）」 ÷ 1000）
                // ＋ （「油相合計単価」 ÷ 「油相合計重量」 × 「充填量油相（ｇ）」 ÷ 1000) )
                var cs_genryo1 = new BigNumber(stringNumbers(page.detail.checkUndefi(new BigNumber(stringNumbers(suiso_tanka)).div(stringNumbers(suiso_no_juryo)).times(stringNumbers(page.detail.checkUndefi(zyusui))).div(stringNumbers(App.settings.app.keisansikiyo.value_1000)).toNumber()))).plus(stringNumbers(
                    page.detail.checkUndefi(new BigNumber(stringNumbers(abura_aihaka)).div(stringNumbers(abura_sho_juryu)).times(stringNumbers(page.detail.checkUndefi(zyuabura))).div(stringNumbers(App.settings.app.keisansikiyo.value_1000)).toNumber()))).toNumber();
                //(「有効歩留」 ÷ 100)

                var genryohiGenryo = undefined;
                if (checkEvalBeforCalcu([data.shisaku_hin.su_iri, genka_shisan_col.yukobudomari, genka_shisan_col.heikinzyu])) {
                    try {
                        var cs_genryo2 = new BigNumber(stringNumbers(genka_shisan_col.yukobudomari, true)).div(stringNumbers(App.settings.app.keisansikiyo.value_100, true)).toNumber();
                        cs_genryo = new BigNumber(stringNumbers(cs_genryo1, true)).times(stringNumbers(genka_shisan_col.heikinzyu, true)).div(stringNumbers(reberu, true)).div(stringNumbers(cs_genryo2, true)).times(stringNumbers(data.shisaku_hin.su_iri, true)).toNumber();

                        if (cs_genryo == 0) {
                            genryohiGenryo = "0.00";
                        }
                    } catch (ex) {
                        cs_genryo = "";
                        genryohiGenryo = "0.00";
                    }
                } else {
                    cs_genryo = "";
                    genryohiGenryo = "0.00";
                }

                cs_genryo = page.detail.checkUndefi(cs_genryo);

                //レベル量(g内容量ｘ入数) 
                reberu = page.detail.formatNumber(reberu, 2, "0.00", 100);

                var genryohi1 = "",// 原料費（１本当）:「原料費/ケース」 ÷ 「入数」
                    genryohi = "",//原料費（ｋg) :「原料費/ケース」 ÷（ 「レベル量(g内容量ｘ入数)」×「比重」） × 1000
                    wariai = "";//割合

                if (genryohiGenryo != undefined) {
                    genryohi1 = genryohiGenryo;
                } else if (checkEvalBeforCalcu([data.shisaku_hin.su_iri, genka_shisan_col.yukobudomari])) {
                    genryohi1 = page.detail.checkUndefi(new BigNumber(stringNumbers(cs_genryo)).div(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.su_iri))).toNumber());
                }

                if (genryohiGenryo != undefined) {
                    genryohi = genryohiGenryo;
                } else if (checkEvalBeforCalcu([tokuseichi.hiju, genka_shisan_col.yukobudomari])) {
                    genryohi = page.detail.checkUndefi(new BigNumber(stringNumbers(cs_genryo)).div(stringNumbers(new BigNumber(stringNumbers(reberu.forData)).times(stringNumbers(page.detail.checkUndefi(tokuseichi.hiju))).toNumber())).times(stringNumbers(App.settings.app.keisansikiyo.value_1000)).toNumber());
                }

                //原料費（ｋg)
                genryohi = page.detail.formatNumber(genryohi, 2, "0.00", 100);
                entity["genryohi"] = page.detail.tabs.genkashisan_tab.detail.stringForData(genryohi.forDisp);
                row.findP("genryohi").val(genryohi.forDisp);

                //原料費(1個あたり)
                genryohi1 = page.detail.formatNumber(genryohi1, 2, "0.00", 100);
                entity["genryohi1"] = page.detail.tabs.genkashisan_tab.detail.stringForData(genryohi1.forDisp);
                row.findP("genryohi1").val(genryohi1.forDisp);

                _606_GenzairyoKakunin_Dialog.detail.data.update(entity);

                total_genryohi = new BigNumber(stringNumbers(total_genryohi)).plus(stringNumbers(entity.genryohi)).toNumber();
                total_genryohi1 = new BigNumber(stringNumbers(total_genryohi1)).plus(stringNumbers(entity.genryohi1)).toNumber();
            });
        }

        total_genryohi = page.detail.formatNumber(total_genryohi, 2, "0.00")
        total_genryohi1 = page.detail.formatNumber(total_genryohi1, 2, "0.00")
        element.findP("total_genryohi").val(total_genryohi.forDisp);
        element.findP("total_genryohi1").val(total_genryohi1.forDisp);

        _606_GenzairyoKakunin_Dialog.detail.dataTable.dataTable("each", function (row, index) {
            var entity, id, wariai;

            row = row.element;

            if (row.hasClass("row-kotei")) {
                return;
            }

            id = row.attr("data-key");
            entity = _606_GenzairyoKakunin_Dialog.detail.data.entry(id);

            wariai = page.detail.checkUndefi(new BigNumber(stringNumbers(entity.genryohi)).div(new BigNumber(stringNumbers(total_genryohi.forDisp))).times(App.settings.app.keisansikiyo.value_100).toNumber());

            if (wariai === 0) {
                wariai = "0.00";
            }

            if (App.isUndefOrNullOrStrEmpty(entity.genryohi) || App.isUndefOrNullOrStrEmpty(entity.genryohi1)) {
                wariai = "";
            }

            wariai = page.detail.formatNumber(wariai, 2, "0.00");
            entity["wariai"] = page.detail.tabs.genkashisan_tab.detail.stringForData(wariai.forDisp);
            row.findP("wariai").val(wariai.forDisp + (!App.isUndefOrNullOrStrEmpty(wariai.forDisp) ? "%" : ""));

            _606_GenzairyoKakunin_Dialog.detail.data.update(entity);
        });
    }

    /**
     * 明細の各行にデータを設定する際のオプションを定義します。
     */
    _606_GenzairyoKakunin_Dialog.detail.options.bindOption = {
        // TODO: 主キーが直接入力の場合には、修正の場合変更を不可とします。
        appliers: {
            tanka: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.beforFormatNumber(value, 2).forDisp);
                return true;
            }
        }
    };

    /**
     * 明細の一覧の行が選択された時の処理を行います。
     */
    _606_GenzairyoKakunin_Dialog.detail.select = function (e, row) {
        $($(row.element[_606_GenzairyoKakunin_Dialog.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab.selected")).removeClass("selected").addClass("unselected");
        $(row.element[_606_GenzairyoKakunin_Dialog.detail.fixedColumnIndex].querySelectorAll(".select-tab")).removeClass("unselected").addClass("selected");

        //選択行全体に背景色を付ける場合は以下のコメントアウトを解除します。
        if (!App.isUndefOrNull(_606_GenzairyoKakunin_Dialog.detail.selectedRow)) {
            _606_GenzairyoKakunin_Dialog.detail.selectedRow.element.find(".selected-row").removeClass("selected-row");
        }
        row.element.find("tr").addClass("selected-row");
        _606_GenzairyoKakunin_Dialog.detail.selectedRow = row;

    };

    /**
     * 明細の一覧の入力項目の変更イベントの処理を行います。
     */
    _606_GenzairyoKakunin_Dialog.detail.change = function (e, row) {
        var target = $(e.target),
            id = row.element.attr("data-key"),
            property = target.attr("data-prop"),
            entity = _606_GenzairyoKakunin_Dialog.detail.data.entry(id),
            options = {
                filter: _606_GenzairyoKakunin_Dialog.detail.validationFilter
            };

        _606_GenzairyoKakunin_Dialog.values.isChangeRunning[property] = true;
        page.wait(target).then(function () {
            _606_GenzairyoKakunin_Dialog.detail.executeValidation(target, row)
            .then(function () {
                entity[property] = row.element.form(_606_GenzairyoKakunin_Dialog.detail.options.bindOption).data()[property];
                _606_GenzairyoKakunin_Dialog.detail.data.update(entity);

                if (_606_GenzairyoKakunin_Dialog.element.find(".save").is(":disabled")) {
                    _606_GenzairyoKakunin_Dialog.element.find(".save").prop("disabled", false);
                }

                // Calculations are related to the total amount => check validate All
                _606_GenzairyoKakunin_Dialog.validateAll().then(function () {
                    _606_GenzairyoKakunin_Dialog.detail.calculateTotalRow(true);
                    _606_GenzairyoKakunin_Dialog.detail.sumKoteiQuantity();
                    _606_GenzairyoKakunin_Dialog.detail.recalculation(_606_GenzairyoKakunin_Dialog.values.dataBackup);
                });

            }).always(function () {
                delete _606_GenzairyoKakunin_Dialog.values.isChangeRunning[property];
            });
        });
    };

    /**
     * show or hide kotei, genryo row。
     */
    _606_GenzairyoKakunin_Dialog.detail.controlDisplayItem = function (row, isKotei, isTotalKesan) {
        switch (isKotei) {
            case true:
                row.find(":input:not(.input-kotei)").prop("readonly", true);
                row.find(".remove-genryo").remove();
                row.addClass("row-kotei");
                var labelKotei = row.find(".input-kotei option:selected").text();
                row.find(".label-kotei").val(labelKotei);
                break;
            case false:
                row.find(".remove-kotei").remove();
                row.find(".input-genryo").show();
                row.addClass("row-genryo");
                break;
            default:
                break;
        }
    }

    /**
     * set data default (0) for 原料費(KG), 原料費(1個あたり) and 割合 when has error from main creen。
     */
    _606_GenzairyoKakunin_Dialog.detail.dataDefaultError = function () {
        var arrProp = ["genryohi", "genryohi1", "wariai"],
            valDefault = page.detail.formatNumber(0, 2, "0.00"),
            id, entity;

        _606_GenzairyoKakunin_Dialog.detail.dataTable.dataTable("each", function (row, index) {

            if (row.element.hasClass("row-kotei")) {
                return;
            }

            id = row.element.attr("data-key");

            entity = _606_GenzairyoKakunin_Dialog.detail.data.entry(id);

            $.each(arrProp, function (index, item) {
                row.element.findP(item).val(item == "wariai" ? valDefault.forDisp + "%" : valDefault.forDisp);
                entity[item] = 0;
            });

            _606_GenzairyoKakunin_Dialog.detail.data.update(entity);

        });

        _606_GenzairyoKakunin_Dialog.detail.calculateTotalRow(null, true, true, true);
    }

    /**
     * sum value total row 。
     */
    _606_GenzairyoKakunin_Dialog.detail.calculateTotalRow = function (isQuantity, isGenryohi, isGenryohi1, isWariai) {
        var items,
            element,
            totalQuantity,
            totalGenryohi,
            totalGenryohi1;
        //totalWariai;

        element = _606_GenzairyoKakunin_Dialog.detail.element;

        items = _606_GenzairyoKakunin_Dialog.detail.data.findAll(function (item, entity) {
            return entity.state !== App.ui.page.dataSet.status.Deleted;
        });

        $.each(items, function (index, item) {
            $.each(item, function (pro, val) {
                if (pro.indexOf("quantity_") >= 0 && isQuantity) {
                    totalQuantity = new BigNumber(stringNumbers(totalQuantity)).plus(stringNumbers(page.detail.checkUndefi(val))).toNumber();
                }

                if (pro == "genryohi" && isGenryohi) {
                    totalGenryohi = new BigNumber(stringNumbers(totalGenryohi)).plus(stringNumbers(page.detail.checkUndefi(val))).toNumber();
                }

                if (pro == "genryohi1" && isGenryohi1) {
                    totalGenryohi1 = new BigNumber(stringNumbers(totalGenryohi1)).plus(stringNumbers(page.detail.checkUndefi(val))).toNumber();
                }

                //if (pro == "wariai" && isWariai) {
                //    totalWariai = new BigNumber(stringNumbers(totalWariai)).plus(stringNumbers(page.detail.checkUndefi(val))).toNumber();
                //}
            })
        });

        if (isQuantity || App.isNull(isQuantity)) {
            totalQuantity = page.detail.formatNumber(totalQuantity, Number(page.values.keta_shosu), App.common.fillTrailingZero(0, Number(page.values.keta_shosu)));
            element.findP("total_quantity").val(totalQuantity.forDisp);
        }

        if (isGenryohi) {
            totalGenryohi = page.detail.formatNumber(totalGenryohi, 2, "0.00", 1000);
            element.findP("total_genryohi").val(totalGenryohi.forDisp);
        }

        if (isGenryohi1) {
            totalGenryohi1 = page.detail.formatNumber(totalGenryohi1, 2, "0.00", 1000);
            element.findP("total_genryohi1").val(totalGenryohi1.forDisp);
        }

        //if (isWariai) {
        //    totalWariai = page.detail.formatNumber(totalWariai, 2, "0.00");
        //    element.findP("total_wariai").val(totalWariai.forDisp + (!App.isUndefOrNullOrStrEmpty(totalWariai.forDisp) ? "%" : ""));
        //}
    }

    /**
    * 明細のバリデーションを実行します。
    */
    _606_GenzairyoKakunin_Dialog.detail.executeValidation = function (targets, row, options) {
        var defaultOptions = {
            targets: targets,
            state: {
                tbody: row,
                isGridValidation: true
            }
        },
            execOptions = $.extend(true, {}, defaultOptions, options);

        return _606_GenzairyoKakunin_Dialog.detail.validator.validate(execOptions);
    };

    /**
     * 明細のバリデーションフィルターを設定します。（必須チェックを行わない）
     */
    _606_GenzairyoKakunin_Dialog.detail.validationFilter = function (item, method, state, options) {
        return method !== "required";
    };

    /**
     * 明細の一覧全体のバリデーションを実行します。
     */
    _606_GenzairyoKakunin_Dialog.detail.validateList = function (suppressMessage) {
        var validations = [],
            options = {
                state: {
                    suppressMessage: suppressMessage,
                }
            };

        _606_GenzairyoKakunin_Dialog.detail.dataTable.dataTable("each", function (row, index) {
            validations.push(_606_GenzairyoKakunin_Dialog.detail.executeValidation(row.element.find(":input"), row.element, options));
        });

        return App.async.all(validations);
    };

</script>

<div class="modal fade wide" tabindex="-1" id="_606_GenzairyoKakunin_Dialog">
    <div class="modal-dialog" style="height: 80%; width: 1080px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">原料費確認</h4>
            </div>

            <div class="modal-body">
                <div class="modal-wrap">
                    <div class="detail">
                        <table class="datatable">
                            <thead>
                                <tr>
                                    <th style="width: 10px; display: none;"></th>
                                    <th style="width: 95px;">原料CD</th>
                                    <th style="width: 400px;">原料名</th>
                                    <th style="width: 80px;">単価</th>
                                    <th style="width: 135px;">配合重量</th>
                                    <th style="width: 120px;">原料費<br />
                                        (KG)</th>
                                    <th style="width: 120px;">原料費<br />
                                        (1個あたり)</th>
                                    <th style="width: 70px;">割合</th>
                                </tr>
                            </thead>
                            <tbody class="item-tmpl" style="cursor: default; display: none;">
                                <tr>
                                    <td style="display: none;">
                                        <span class="select-tab unselected"></span>
                                    </td>
                                    <td>
                                        <input type="text" data-prop="cd_genryo" class="fiel-validation input-genryo remove-genryo shisanIraied genryo-input seihokopiInput genryo-selected text-selectAll" readonly="readonly" />
                                        <select class="fiel-validation remove-kotei input-kotei shisanIraied" data-prop="zoku_kotei" disabled></select>
                                        <input type="text" class="remove-kotei label-kotei" readonly="readonly" />
                                    </td>
                                    <td>
                                        <input type="text" data-prop="nm_genryo" class="remove-genryo" readonly="readonly" />
                                        <input type="text" data-prop="nm_kotei" class="remove-kotei" readonly="readonly" />
                                    </td>
                                    <td>
                                        <input type="tel" data-prop="tanka" class="" data-number-format-tofixed="2" readonly="readonly" />
                                    </td>
                                    <td>
                                        <input type="tel" data-prop="quantity" class="quantity input-total limit-input-float-new number-kirisu" style="ime-mode: disabled" maxlength="10" />
                                    </td>
                                    <td>
                                        <input type="tel" data-prop="genryohi" class="" readonly="readonly" />
                                    </td>
                                    <td>
                                        <input type="tel" data-prop="genryohi1" class="" readonly="readonly" />
                                    </td>
                                    <td>
                                        <input type="tel" data-prop="wariai" class="" readonly="readonly" />
                                    </td>
                                </tr>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <td style="display: none;">
                                        <span class="select-tab unselected"></span>
                                    </td>
                                    <td style="width: 95px; background-color: white !important; border: 0px !important;"></td>
                                    <td>
                                        <label>合計</label>
                                    </td>
                                    <td style="width: 80px;"></td>
                                    <td style="width: 135px;">
                                        <input type="tel" data-prop="total_quantity" class="quantity input-total limit-input-float-new" readonly="readonly" style="ime-mode: disabled" />
                                    </td>
                                    <td style="width: 120px;">
                                        <input type="tel" data-prop="total_genryohi" class="" readonly="readonly" />
                                    </td>
                                    <td style="width: 120px;">
                                        <input type="tel" data-prop="total_genryohi1" class="" readonly="readonly" />
                                    </td>
                                    <td style="width: 70px;">
                                        <input type="tel" data-prop="total_wariai" class="" readonly="readonly" />
                                    </td>
                                </tr>
                            </tfoot>

                        </table>
                    </div>
                </div>
            </div>

            <div class="modal-message message-area dialog-slideup-area">
                <div class="alert-message" style="display: none">
                    <ul>
                    </ul>
                </div>
                <div class="info-message" style="display: none">
                    <ul>
                    </ul>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-primary save" name="save" disabled="disabled">反映</button>
                <button type="button" class="cancel-button btn btn-sm btn-default" data-dismiss="modal" name="close">閉じる</button>
            </div>

        </div>
    </div>
</div>
