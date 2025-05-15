<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.aspx.cs" Inherits="Tos.Web.Pages._300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>

<style type="text/css">
    #_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab .part-content .col-xs-6 {
        padding-left: 0px;
        padding-right: 0px;
    }

    #_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab .row-area {
        height: 248px!important;
    }

    #_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab .row-area2 {
        height: 223px!important;
    }

    #_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab textarea {
        width: 100%;
        resize: none;
    }

    #_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab .btn-xs {
        min-width: inherit;
    }

    #_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab .l-balance {
        width: 42.5%;
    }

    #_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab .r-balance {
        width: 57.5%;
    }

    #_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab .tree-line-height {
        height: 63px;
    }

    #_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab .two-line-height {
        height: 47px;
    }

    #_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab .control-label.col-xs-1.first {
        width: 12%;
    }

    #_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab .control.col-xs-11 {
        width: 88%;
    }

    #_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab .table-height {
        height: 390px;
    }

    #_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab table textarea {
        border: 0;
        margin: 0px;
        float: left;
    }
    #_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab .datatable tr td:first-child {
        vertical-align: top;
        background-color: #efefef;
    }
    #_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab .small-height {
        height: 23px;
    }

    #_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab .mixed-group * {
        float: left;
        margin-left: 5px;
    }
    #_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab .part.roof {
        height: 100%;
        margin-right: 0px;
        margin-bottom: 0px;
        overflow: auto;
    }
    #_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab textarea.has-err {
        background-color: #ffdab9;
    }
    #_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab .auto-width {
        min-width: 0px;
    }
</style>

<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.dialogDataTakeCount   // TODO:取得するデータ数を指定します。
        },
        urls: {
            search: "../api/_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab",
            searchCopy: "../api/_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab/getCopyData"
        },
        param: {
            no_seiho: "",
            mode: ""
        },
        name: "【賞味期間設定表】"
    };

    /**
     * 検索ダイアログの初期化処理を行います。
     */
    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.initialize = function () {
        var element = $("#_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab");

        _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.element = element;

        element.on("change", ":input", _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.change);

        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body",
                show: function () {
                    element.find(".alert-message").show();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                }
            });

        _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.validator = element.validation(App.validation(_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.options.validations, {
            success: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    if ($(item.element).closest("table").length) {
                        $(item.element).removeClass("has-err");
                    } else {
                        _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.setColValidStyle(item.element);
                    }

                    App.ui.page.notifyAlert.remove(item.element);
                }
            },
            fail: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    if ($(item.element).closest("table").length) {
                        $(item.element).addClass("has-err");
                    } else {
                        _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.setColInvalidStyle(item.element);
                    }
                    if (state && state.suppressMessage) {
                        continue;
                    }

                    App.ui.page.notifyAlert.message(_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.name + item.message, item.element).show();
                }
            },
            always: function (results) {
                //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
            }
        }));

        _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.search();
        page.finishLoading("TAB_Initilize", "TAB8", 5);
    };

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.setColInvalidStyle = function (target) {
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
    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.setColValidStyle = function (target) {
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
    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.shown = function (e) {

        _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.element.find(":input:not(button):first").focus();
    };

    /**
     * 検索ダイアログ　バリデーションルールを定義します。
     */
    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.options.validations = {
        nm_kaihatsubusho: {
            rules: {
                //required: true
            },
            options: {
                name: "開発担当"
            },
            messages: {
                required: App.messages.base.required
            }
        },
        nm_kaihatsutanto: {
            rules: {
                required: true
            },
            options: {
                name: "開発担当"
            },
            messages: {
                required: App.messages.base.required
            }
        },
        nm_hozon_shiken_kankaku: {
            rules: {
                maxbytelength: 120
            },
            options: {
                name: "保存試験の間隔",
                byte: 60
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_hozon_shiken_kikan: {
            rules: {
                maxbytelength: 120
            },
            options: {
                name: "保存試験の期間",
                byte: 60
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        biko: {
            rules: {
                maxbytelength: 500
            },
            options: {
                name: "備考",
                byte: 250
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_rikagaku_shiken_komoku: {
            rules: {
                maxbytelength: 160
            },
            options: {
                name: "理化学試験の項目",
                byte: 80
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_rikagaku_shiken_hinshitsu: {
            rules: {
                maxbytelength: 160
            },
            options: {
                name: "理化学試験の品質上の許容範囲",
                byte: 80
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_rikagaku_shiken_kikan: {
            rules: {
                maxbytelength: 160
            },
            options: {
                name: "理化学試験の保存可能期間",
                byte: 80
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_rikagaku_shiken_hokoku: {
            rules: {
                maxbytelength: 160
            },
            options: {
                name: "理化学試験の報告書番号",
                byte: 80
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_biseibutsu_shiken_komoku: {
            rules: {
                maxbytelength: 160
            },
            options: {
                name: "微生物試験の項目",
                byte: 80
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_biseibutsu_shiken_hinshitsu: {
            rules: {
                maxbytelength: 160
            },
            options: {
                name: "微生物試験の品質上の許容範囲",
                byte: 80
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_biseibutsu_shiken_kikan: {
            rules: {
                maxbytelength: 160
            },
            options: {
                name: "微生物試験の保存可能期間",
                byte: 80
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_biseibutsu_shiken_hokoku: {
            rules: {
                maxbytelength: 160
            },
            options: {
                name: "微生物試験の報告書番号",
                byte: 80
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_kanno_hyoka_komoku: {
            rules: {
                maxbytelength: 160
            },
            options: {
                name: "官能評価の項目",
                byte: 80
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_kanno_hyoka_hinshitsu: {
            rules: {
                maxbytelength: 160
            },
            options: {
                name: "官能評価の品質上の許容範囲",
                byte: 80
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_kanno_hyoka_kikan: {
            rules: {
                maxbytelength: 160
            },
            options: {
                name: "官能評価の保存可能期間",
                byte: 80
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_kanno_hyoka_hokoku: {
            rules: {
                maxbytelength: 160
            },
            options: {
                name: "官能評価の報告書番号",
                byte: 80
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        no_anzen_keisu: {
            rules: {
                required: true,
                number: true,
                pointlength: [1,1]
            },
            options: {
                name: "安全係数"
            },
            messages: {
                required: App.messages.base.required,
                number: App.messages.base.number,
                pointlength: App.messages.base.pointlength
            }
        },
        nm_shomikigen_biko: {
            rules: {
                //required: true,
                maxbytelength: 140
            },
            options: {
                name: "賞味期間の備考",
                byte: 70
            },
            messages: {
                required: App.messages.base.required,
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        cd_nengetsu_hyoji: {
            rules: {
                required: true,
                //maxbytelength: 140
            },
            options: {
                name: "年月表示対応"
            },
            messages: {
                required: App.messages.base.required,
                //maxbytelength: App.messages.base.maxbytelength
            }
        },
    };

    /**
     * 検索ダイアログの検索処理を実行します。
     */
    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.search = function () {
        var element = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.element,
            url = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.urls.search;

        _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.options.filter = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.createFilter();

        $.ajax(App.ajax.webapi.get(url, _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.options.filter))
        .done(function (result) {
            var data = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.prepairBindingData(result);
            var int = setInterval(function () {
                if (_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.isMasterLoaded) {
                    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.bind(data);
                    clearInterval(int);
                }
            }, 100);
        }).always(function () {
        });

    };

    /**
     * Reload data in copy mode
     */
    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.searchCopy = function () {
        var element = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.element,
            url = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.urls.searchCopy;

        _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.options.filter = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.createFilter();

        $.ajax(App.ajax.webapi.get(url, _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.options.filter))
        .done(function (result) {
            var data = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.mergeSearchData(result);
            _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.bind(data);
        }).always(function () {

        });
    }

    /**
     * Merge current data with copy data
     */
    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.mergeSearchData = function (copyData) {
        var currentData = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.currentData;
        copyData = copyData || {}
        if (currentData) {
            currentData.nm_hozon_shiken_kankaku = copyData.nm_hozon_shiken_kankaku || "";
            currentData.nm_hozon_shiken_kikan = copyData.nm_hozon_shiken_kikan || "";
            currentData.biko = copyData.biko || "";
            currentData.nm_shomikigen_biko = copyData.nm_shomikigen_biko || "";
            currentData.cd_nengetsu_hyoji = copyData.cd_nengetsu_hyoji || "";                       // SKS - Modified 2022 
            currentData.nm_rikagaku_shiken_komoku = copyData.nm_rikagaku_shiken_komoku || "";
            currentData.nm_rikagaku_shiken_hinshitsu = copyData.nm_rikagaku_shiken_hinshitsu || "";
            currentData.nm_rikagaku_shiken_kikan = copyData.nm_rikagaku_shiken_kikan || "";
            currentData.nm_rikagaku_shiken_hokoku = copyData.nm_rikagaku_shiken_hokoku || "";
            currentData.nm_biseibutsu_shiken_komoku = copyData.nm_biseibutsu_shiken_komoku || "";
            currentData.nm_biseibutsu_shiken_hinshitsu = copyData.nm_biseibutsu_shiken_hinshitsu || "";
            currentData.nm_biseibutsu_shiken_kikan = copyData.nm_biseibutsu_shiken_kikan || "";
            currentData.nm_biseibutsu_shiken_hokoku = copyData.nm_biseibutsu_shiken_hokoku || "";
            currentData.nm_kanno_hyoka_komoku = copyData.nm_kanno_hyoka_komoku || "";
            currentData.nm_kanno_hyoka_hinshitsu = copyData.nm_kanno_hyoka_hinshitsu || "";
            currentData.nm_kanno_hyoka_kikan = copyData.nm_kanno_hyoka_kikan || "";
            currentData.nm_kanno_hyoka_hokoku = copyData.nm_kanno_hyoka_hokoku || "";
            currentData.no_anzen_keisu = copyData.no_anzen_keisu || "";
            //currentData.nm_kaihatsubusho = copyData.nm_kaihatsubusho || "";
            //currentData.nm_kaihatsutanto = copyData.nm_kaihatsutanto || "";
        }
        return currentData;
    }

    /**
     * Prepair search data before binding: create changeset, create variables, ...
     */
    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.prepairBindingData = function (searchData) {
        searchData = searchData || {};
        var param = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.param,
            currentData = searchData.currentData,
            modes = page.options.mode;
        _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.data = App.ui.page.dataSet();
        _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.currentData = currentData;
        switch (param.mode) {
            case modes.new:
            case modes.new_copy:
                // Add new data
                _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.mergeSearchData(searchData.copyData);
                _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.data.add(currentData);
                break;
            case modes.edit_copy:
                // Merge copy data in copy mode
                _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.mergeSearchData(searchData.copyData);
            case modes.edit:
                // Edit data
                _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.data.attach(currentData);
                _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.data.update(currentData);
                break;
            case modes.view:
                _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.data.attach(currentData);
                break;
        }
        return currentData;
    }

    /**
     * Change header shisaku
     */
    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.changeShisaku = function () {
        var element = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.element,
            param = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.param;
        element.findP("su_shomikikan_free_input").val(App.data.getCommaNumberString(param.shomikikan));
        element.findP("kbn_shomikikan_tani").val(param.shomikikan_tani);
    }

    /**
     * 検索ダイアログの検索条件を組み立てます
     */
    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.createFilter = function () {
        var param = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.param;
        return {
            no_seiho: param.no_seiho,
            no_seiho_copy: param.no_seiho_copy,
            kbn_haigo: App.settings.app.kbnHin.haigo,
            mode: param.mode,
            isApplied: (page.getShinseiStatus() > App.settings.app.kbn_status.henshuchu) ? true : false
        };
    };

    /**
     * Sync shomikigen data when load master data in tab 6 製品規格案及び取扱基準
     */
    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.bindMasterShomikigen = function (data) {
        var kbn_shomikikan = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.element.findP("kbn_shomikikan"),
            kbn_shomikikan_seizo_fukumu = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.element.findP("kbn_shomikikan_seizo_fukumu"),
            kbn_shomikikan_tani = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.element.findP("kbn_shomikikan_tani"),
            cd_nengetsu_hyoji = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.element.findP("cd_nengetsu_hyoji");
       
        // Combobox [賞味期間区分]
        kbn_shomikikan.children().remove();
        App.ui.appendOptions(
            kbn_shomikikan,
            "cd_literal",
            "nm_literal",
            data.kbn_shomikikan.value,
            true
        );

        // Combobox [製造日を含め区分]
        kbn_shomikikan_seizo_fukumu.children().remove();
        App.ui.appendOptions(
            kbn_shomikikan_seizo_fukumu,
            "cd_literal",
            "nm_literal",
            data.kbn_shomikikan_seizo_fukumu.value,
            true
        );

        // Combobox [単位区分]
        kbn_shomikikan_tani.children().remove();
        App.ui.appendOptions(
            kbn_shomikikan_tani,
            "cd_literal",
            "nm_literal",
            data.kbn_shomikikan_tani.value,
            true
        );

        // Combobox [年月表示対応]
        cd_nengetsu_hyoji.children().remove();
        App.ui.appendOptions(
            cd_nengetsu_hyoji,
            "cd_literal",
            "nm_literal",
            data.cd_nengetsu_hyoji.value,
            true
        );

        _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.isMasterLoaded = true;
    }

    /**
     * Sync shomikigen data when change in tab 6 製品規格案及び取扱基準
     */
    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.setShomikigen = function (property, value) {
        var target = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.element.findP(property);
        target.val(value);
    }
    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.bindShomikigen = function (data) {
        var target = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.element.find(".shomikigen");
        target.form().bind(data);
    }

    /**
     * Bind options
     */
    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.options.bindOptions = {
        appliers: {
            no_anzen_keisu: function (value, element) {
                if (!App.isUndefOrNullOrStrEmpty(value)) {
                    element.val(Number(value));
                }
                return true;
            }
        }
    }

    // Append reference data: ブランド,
    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.setBurando = function (value) {
        _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.element.findP("nm_burando").text(value).val(value)//.change();
    }

    // Append reference data: 得意先名,
    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.setTokisaki = function (value) {
        _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.element.findP("nm_tokisaki").text(value).val(value)//.change();
    }

    /**
     * 検索ダイアログの一覧にデータをバインドします。
     */
    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.bind = function (data) {
        var element = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.element,
            data = data || {},
            param = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.param;

        //if (param.mode == page.options.mode.view) {
        //    // Do not thing
        //} else {
        //    // 安全係数(null -> 0)
        //    data.nm_kaihatsubusho = App.ui.page.user.nm_busho;
        //    data.nm_kaihatsutanto = App.ui.page.user.Name;
        //}
        //if (App.isUndefOrNullOrStrEmpty(data.no_anzen_keisu)) {
        //    data.no_anzen_keisu = 0;
        //}
        // Bind data
        element.form(_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.options.bindOptions).bind(data);
        // Mode control
        _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.activeModeControl();
        page.finishLoading("TAB_Bind", "TAB8", 5);
    };

    /**
    * Active mode control
    */
    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.activeModeControl = function () {
        var mode = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.param.mode;
        if (mode == page.options.mode.view) {
            _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.element.find(":input").prop("disabled", true);
        }
    }

    /**
    * Element on change event
    */
    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.change = function (e) {
        var target = $(e.target),
            property = target.attr("data-prop"),
            element = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.element,
            entity = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.data.entry(element.attr("data-key"));


        _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.validator.validate({
            targets: target,
            filter: page.options.changeValidationFilter,
            state: {
                isGridValidation: target.closest("tr").length > 0 ? true : false
            }
        }).then(function () {
            page.setIsChangeValue();
            entity[property] = target.val() ? target.val() : null;
            _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.data.update(entity);
            if (property === "no_anzen_keisu") {
                if (!App.isUndefOrNullOrStrEmpty(target.val())) {
                    target.val(Number(target.val()));
                }
            }
        });
    }

    /**
    * 開発担当 = 所属部署＋'　'+ユーザー名
    */
    //_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.getUserInfo = function () {
    //    var userInfo = App.ui.page.user;
    //    return userInfo.nm_busho + '　' + userInfo.Name;
    //}

    /**
    * Get data set when save
    */
    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.getSaveData = function () {
        return _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.data.getChangeSet();
    }

    // Check all validate in the tab
    _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.validateAll = function (filter) {
        var targets = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.element.find(":input");

        return _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.validator.validate({
            targets: targets,
            filter: filter,
            state: {
                suppressMessage: false
            }
        })
    }

</script>

<div class="tab-pane smaller" id="_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab">
    <div class="smaller sub-tab-content">
        <div class="part roof">
            <div class="part-content tab-content">
                <div class="col-xs-12 col-zr">
                    <div class="row">
                        <div class="control-label col-xs-1 first">
                            <label>得意先名</label>
                        </div>
                        <div class="control col-xs-4">
                            <label data-prop="nm_tokisaki"></label>
                            <input type="hidden" data-prop="nm_tokisaki" />
                        </div>
                        <div class="control-label col-xs-1 fix-label-md single-target" style="width: 54.666666666%">
                            <label class="lb">開発担当</label>
                            <label class="auto-width" data-prop="nm_kaihatsubusho"></label>
                            <label class="auto-width" data-prop="nm_kaihatsutanto"></label>
                            <input type="hidden" data-prop="nm_kaihatsubusho" />
                            <input type="hidden" data-prop="nm_kaihatsutanto" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-label col-xs-1 first">
                            <label>ブランド</label>
                        </div>
                        <div class="control col-xs-2" style="width: 88%">
                            <label data-prop="nm_burando"></label>
                            <input type="hidden" data-prop="nm_burando" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-label col-xs-1 first">
                            <label>保存試験の間隔</label>
                        </div>
                        <div class="control col-xs-11">
                            <input type="text" data-prop="nm_hozon_shiken_kankaku" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-label col-xs-1 first">
                            <label>保存試験の期間</label>
                        </div>
                        <div class="control col-xs-11">
                            <input type="text" data-prop="nm_hozon_shiken_kikan" />
                        </div>
                    </div>
                    <div class="row ">
                        <div class="control-label col-xs-1 middle-content tree-line-height first">
                            <label class="middle-elem">備考</label>
                        </div>
                        <div class="control col-xs-11 tree-line-height">
                            <textarea rows="3" style="width: 98%; height: auto;" data-prop="biko"></textarea>
                        </div>
                    </div>
                </div>
                <div class="control-label col-xs-12 table-height">
                    <table class="datatable full-height auto-height" style="width: 98.2%">
                        <tr>
                            <th style="width: 11.9%" class="small-height">試験分類
                            </th>
                            <th style="width: 22%" class="small-height">項目
                            </th>
                            <th style="width: 22%" class="small-height">品質上の許容範囲
                            </th>
                            <th style="width: 22%" class="small-height">保存可能期間
                            </th>
                            <th>報告書番号
                            </th>
                        </tr>
                        <tr>
                            <td class="tb-label">
                                <label>理化学試験</label>
                            </td>
                            <td>
                                <textarea class="textarea-height bar-less" data-prop="nm_rikagaku_shiken_komoku"></textarea>
                            </td>
                            <td>
                                <textarea class="textarea-height bar-less" data-prop="nm_rikagaku_shiken_hinshitsu"></textarea>
                            </td>
                            <td>
                                <textarea class="textarea-height bar-less" data-prop="nm_rikagaku_shiken_kikan"></textarea>
                            </td>
                            <td>
                                <textarea class="textarea-height bar-less" data-prop="nm_rikagaku_shiken_hokoku"></textarea>
                            </td>
                        </tr>
                        <tr>
                            <td class="tb-label">
                                <label>微生物試験</label>
                            </td>
                            <td>
                                <textarea class="textarea-height bar-less" data-prop="nm_biseibutsu_shiken_komoku"></textarea>
                            </td>
                            <td>
                                <textarea class="textarea-height bar-less" data-prop="nm_biseibutsu_shiken_hinshitsu"></textarea>
                            </td>
                            <td>
                                <textarea class="textarea-height bar-less" data-prop="nm_biseibutsu_shiken_kikan"></textarea>
                            </td>
                            <td>
                                <textarea class="textarea-height bar-less" data-prop="nm_biseibutsu_shiken_hokoku"></textarea>
                            </td>
                        </tr>
                        <tr>
                            <td class="tb-label">
                                <label>官能評価</label>
                            </td>
                            <td>
                                <textarea class="textarea-height bar-less" data-prop="nm_kanno_hyoka_komoku"></textarea>
                            </td>
                            <td>
                                <textarea class="textarea-height bar-less" data-prop="nm_kanno_hyoka_hinshitsu"></textarea>
                            </td>
                            <td>
                                <textarea class="textarea-height bar-less" data-prop="nm_kanno_hyoka_kikan"></textarea>
                            </td>
                            <td>
                                <textarea class="textarea-height bar-less" data-prop="nm_kanno_hyoka_hokoku"></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="col-xs-12 col-zr" style="margin-top: 2px;">
                    <div class="row">
                        <div class="control-label col-xs-1  two-line-height first">
                            <label>安全係数</label>
                        </div>
                        <div class="control col-xs-1  two-line-height">
                            <input type="tel" style="width: 70px" class="number-right limit-input-float" data-prop="no_anzen_keisu" maxlength="3"/>
                        </div>
                        <div class="control-label col-xs-1 mixed-group shomikigen single-target two-line-height" style="width: 79.66666666666666666666%">
                            <label class="lb float-left">賞味期間</label>
                            <select data-prop="kbn_shomikikan" class="float-left" style="width: 150px;" disabled="disabled"></select>
                            <select data-prop="kbn_shomikikan_seizo_fukumu" class="float-left" style="width: 150px;" disabled="disabled"></select>
                            <input type="tel" data-prop="su_shomikikan_free_input" class="float-left number-right comma-number" style="width: 50px;" disabled="disabled" />
                            <select data-prop="kbn_shomikikan_tani" class="float-left" style="width: 60px;" disabled="disabled"></select>
                            <textarea class="prop" data-prop="nm_shomikigen_biko" style="width: calc(97.4895% - 518px); height: 45px;" ></textarea>
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-label col-xs-2" style="width: 20.33333333333%"></div>
                        <div class="control-label col-xs-10 single-target" style="width: 79.66666666666666666666%">
                            <%--SKS - Modified 2022 - ST--%>
                            <label class="lb" style="width: 516px; text-align: right">年月表示対応</label>
                            <select data-prop="cd_nengetsu_hyoji" style="width: calc(97.4895% - 518px);"></select>
                            <%--SKS - Modified 2022 - ED--%></div>
                    </div>
                    <%--<div class="row">
                    <div class="control-label col-xs-12"></div>
                </div>--%>
                    <%--<div class="row">
                        <div class="control-label col-xs-6 right l-balance">
                            <label>
                                ★　作成担当
                            </label>
                            <button type="button" class="btn btn-xs btn-primary">署名</button>
                            <button type="button" class="btn btn-xs btn-primary">削除</button>
                        </div>
                        <div class="control col-xs-6 r-balance">
                            <div class="lb-padding">
                                <label>2018/05/30　　○○○開発部　 □□　花子</label>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-label col-xs-6 right l-balance">
                            <label>
                                ★　承認１
                            </label>
                            <button type="button" class="btn btn-xs btn-primary">署名</button>
                            <button type="button" class="btn btn-xs btn-primary">削除</button>
                        </div>
                        <div class="control col-xs-6 r-balance">
                            <div class="lb-padding">
                                <label>2018/06/04　　○○○開発部　 △△　太郎</label>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-label col-xs-6 right l-balance">
                            <label>
                                ★　承認２
                            </label>
                            <button type="button" class="btn btn-xs btn-primary">署名</button>
                            <button type="button" class="btn btn-xs btn-primary">削除</button>
                        </div>
                        <div class="control col-xs-6 r-balance">
                            <div class="lb-padding">
                                <label>2018/06/04　　○○○開発部　 △△　太郎</label>
                            </div>
                        </div>
                    </div>--%>
                </div>
            </div>
        </div>
    </div>
</div>
