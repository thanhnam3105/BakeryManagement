<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="708_GenryoMasterSansho_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs._708_GenryoMasterSansho_Dialog" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>

<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var _708_GenryoMasterSansho_Dialog = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.dialogDataTakeCount   // TODO:取得するデータ数を指定します。
        },
        urls: {
            search_lab: "../api/_708_GenryoMasterSansho_Dialog/Get_lab",
            search_factory: "../api/_708_GenryoMasterSansho_Dialog/Get_factory",
            ma_kaisha: "../Services/ShisaQuickService.svc/ma_kaisha?$filter=cd_kaisha eq {0}",
        },
        param: {
            kbn_bumon: null,
            cd_kaisha: null,
            cd_kojyo: null,
            cd_hin: ""
        }
    };

    /**
     * 検索ダイアログの初期化処理を行います。
     */
    _708_GenryoMasterSansho_Dialog.initialize = function () {
        var element = $("#_708_GenryoMasterSansho_Dialog");
        if (_708_GenryoMasterSansho_Dialog.param.kbn_bumon == App.settings.app.kbn_bumon.lab) {
            //$("#kojyo").show();
            $("#show_busho").show();
            $("#hide_busho").hide();

            $("#show_doto_kaisha").hide();
            $("#show_doto_SET_DATE").hide();
            $("#show_kaisha").hide();
            $("#hide_kaisha").show();
            $("#hide_doto_kaisha").show();
            $("#show_SET_DATE").hide();
            $("#hide_doto_SET_DATE").show();
            $("#hide_hin_doto").show();
        }
        if (_708_GenryoMasterSansho_Dialog.param.kbn_bumon == App.settings.app.kbn_bumon.factory) {
            //$("#kojyo").hide();
            $("#show_busho").hide();
            $("#hide_busho").show();

            $("#show_doto_kaisha").show();
            $("#show_doto_SET_DATE").show();
            $("#show_kaisha").show();
            $("#hide_kaisha").hide();
            $("#hide_doto_kaisha").hide();
            $("#show_SET_DATE").show();
            $("#hide_doto_SET_DATE").hide();
            $("#hide_hin_doto").hide();
        }

        element.on("hidden.bs.modal", _708_GenryoMasterSansho_Dialog.hidden);
        element.on("shown.bs.modal", _708_GenryoMasterSansho_Dialog.shown);
        //element.on("click", ".search", _708_GenryoMasterSansho_Dialog.search);
        //TODO: 単一セレクトの場合は、下の１行を使用します
        element.on("click", ".search-list tbody ", _708_GenryoMasterSansho_Dialog.select);
        //TODO: 複数セレクトの場合は、上の１行を削除し、下の３行をコメント解除します
        //element.on("click", ".select", _708_GenryoMasterSansho_Dialog.select);
        //element.on("click", ".search-list tbody", _708_GenryoMasterSansho_Dialog.selectOne);
        //element.find("[name='select_cd_all']").on("click", _708_GenryoMasterSansho_Dialog.selectAll);
        _708_GenryoMasterSansho_Dialog.element = element;

        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        _708_GenryoMasterSansho_Dialog.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#_708_GenryoMasterSansho_Dialog .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        _708_GenryoMasterSansho_Dialog.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#_708_GenryoMasterSansho_Dialog .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body",
                show: function () {
                    element.find(".alert-message").show();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                }
            });

        _708_GenryoMasterSansho_Dialog.validator = element.validation(App.validation(_708_GenryoMasterSansho_Dialog.options.validations, {
            success: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _708_GenryoMasterSansho_Dialog.setColValidStyle(item.element);

                    _708_GenryoMasterSansho_Dialog.notifyAlert.remove(item.element);
                }
            },
            fail: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _708_GenryoMasterSansho_Dialog.setColInvalidStyle(item.element);
                    if (state && state.suppressMessage) {
                        continue;
                    }

                    _708_GenryoMasterSansho_Dialog.notifyAlert.message(item.message, item.element).show();
                }
            },
            always: function (results) {
                //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
            }
        }));

        element.find(".modal-dialog").draggable({
            drag: true,
        });
    };

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    _708_GenryoMasterSansho_Dialog.setColInvalidStyle = function (target) {
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
    _708_GenryoMasterSansho_Dialog.setColValidStyle = function (target) {
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
     * 検索ダイアログ非表示時処理を実行します。
     */
    _708_GenryoMasterSansho_Dialog.hidden = function (e) {
        var element = _708_GenryoMasterSansho_Dialog.element,
            table = element.find(".search-list");

        //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
        element.find(":input").val("");
        element.find("[data-prop]").val("").text("");
        //TODO: 複数セレクトの場合は、下の１行をコメント解除します。
        //element.find(":checked").prop("checked", false);
        table.find("tbody").not(".item-tmpl").remove();

        element.findP("data_count").text("");
        element.findP("data_count_total").text("");

        $("#mishiyo").hide();

        var items = element.find(".search-criteria :input:not(button)");
        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            _708_GenryoMasterSansho_Dialog.setColValidStyle(item);
        }

        _708_GenryoMasterSansho_Dialog.notifyInfo.clear();
        _708_GenryoMasterSansho_Dialog.notifyAlert.clear();
    };

    /**
     * 検索ダイアログ表示時処理を実行します。
     */
    _708_GenryoMasterSansho_Dialog.shown = function (e) {

        _708_GenryoMasterSansho_Dialog.element.find(":input:not(button):first").focus();
        _708_GenryoMasterSansho_Dialog.search();
    };

    /**
     * 検索ダイアログ　バリデーションルールを定義します。
     */
    _708_GenryoMasterSansho_Dialog.options.validations = {
    };

    /**
     * 検索ダイアログの検索処理を実行します。
     */
    _708_GenryoMasterSansho_Dialog.search = function () {
        var element = _708_GenryoMasterSansho_Dialog.element,
            loadingTaget = element.find(".modal-content"),
            table = element.find(".search-list"),
            param = _708_GenryoMasterSansho_Dialog.param,
            query, filters, urls_search;

        filters = _708_GenryoMasterSansho_Dialog.createFilter();

        if (param.kbn_bumon == App.settings.app.kbn_bumon.lab) {
            urls_search = _708_GenryoMasterSansho_Dialog.urls.search_lab;
        }
        if (param.kbn_bumon == App.settings.app.kbn_bumon.factory) {
            urls_search = _708_GenryoMasterSansho_Dialog.urls.search_factory;
        }

        _708_GenryoMasterSansho_Dialog.validator.validate()
        .then(function () {
            table.find("tbody:visible").remove();
            _708_GenryoMasterSansho_Dialog.notifyAlert.clear();
            App.ui.loading.show("", loadingTaget);

            $.ajax(App.ajax.webapi.get(urls_search, filters))
            .done(function (result) {
                var data = (param.kbn_bumon == App.settings.app.kbn_bumon.lab) ? result.Items[0] : result;
                if (!App.isUndefOrNullOrStrEmpty(data)) {
                    _708_GenryoMasterSansho_Dialog.bind(data);
                }
            }).always(function () {

                App.ui.loading.close(loadingTaget);
            });
        });
    };

    /**
     * 検索ダイアログの検索条件を組み立てます
     */
    _708_GenryoMasterSansho_Dialog.createFilter = function () {
        var criteria = _708_GenryoMasterSansho_Dialog.element.find(".search-criteria").form().data(),
            param = _708_GenryoMasterSansho_Dialog.param,
            filters;
        
        //TODO: 画面で設定された検索条件を取得し、データ取得サービスのフィルターオプションを組み立てます。
        var filter = {
            cd_hin: param.cd_hin,
            cd_kaisha: param.cd_kaisha,
            cd_kojyo: param.cd_kojyo
        };
        return filter;
    };

    /**
     * 検索ダイアログの一覧にデータをバインドします。
     */
    _708_GenryoMasterSansho_Dialog.bind = function (data) {
        var element = _708_GenryoMasterSansho_Dialog.element,
            param = _708_GenryoMasterSansho_Dialog.param,
            //item = (param.kbn_bumon == App.settings.app.kbn_bumon.lab) ? data.Items[0] : data,
            setData = {},
            dataSet = App.ui.page.dataSet();

        setData = App.isUndefOrNull(data) ? data : data.constructor();

        for (var attr in data) {
            if (data.hasOwnProperty(attr)) {
                setData[attr] = data[attr];
            }
        }

        _708_GenryoMasterSansho_Dialog.data = App.ui.page.dataSet();
        //_708_GenryoMasterSansho_Dialog.data.attach(items);
        _708_GenryoMasterSansho_Dialog.data.attach.bind(dataSet)(setData);
        _708_GenryoMasterSansho_Dialog.element.form(_708_GenryoMasterSansho_Dialog.options.bindOption).bind(setData);

        if (data.flg_mishiyo == true) {
            $("#mishiyo").show();
        }
        else {
            $("#mishiyo").hide();
        }

        if (data.dt_henko == data.dt_toroku) {
            element.findP("dt_henko").hide();
        }
        else {
            element.findP("dt_henko").show();
        }

        if (data.kbn_ksys_denso == 1) {
            element.findP("ksys_denso").text("KSYS加工残伝送しない");
        }
        else {
            element.findP("ksys_denso").text("KSYS加工残伝送する");
        }

        if (!App.isUndefOrNullOrStrEmpty(data.budomari)) {
            element.findP("budomari").text(App.num.format(data.budomari, "#,#.##") + " %");
        }
        if (!App.isUndefOrNullOrStrEmpty(data.kin_tanka)) {
            element.findP("kin_tanka").text(App.num.format(data.kin_tanka, "#,#.##") + " 円/納入単位");
        }
        if (!App.isUndefOrNullOrStrEmpty(data.su_leadtime)) {
            element.findP("su_leadtime").text(App.num.format(data.su_leadtime, "#,#") + " 日");
        }

        if (param.kbn_bumon == App.settings.app.kbn_bumon.lab) {
            var su_code_standard = 0, numberZeroAdd = "";
            $.ajax(App.ajax.odata.get(App.str.format(_708_GenryoMasterSansho_Dialog.urls.ma_kaisha, param.cd_kaisha))).then(function (result) {
                su_code_standard = result.value[0].su_code_standard;
                if (!App.isUndefOrNullOrStrEmpty(su_code_standard)) {
                    for (var i = 0; i < su_code_standard; i++) {
                        numberZeroAdd += "0";
                    }
                }
                element.findP("cd_hin").val(data.cd_hin);
                element.findP("cd_hin").text(App.common.getFullString(data.cd_hin, numberZeroAdd));
            });
            
            var nm_busho = data.nm_busho ? data.nm_busho : " ";
            element.findP("nm_busho").text(App.common.getFullCdKaisha(param.cd_kojyo) + " " + nm_busho);

            if (!App.isUndefOrNullOrStrEmpty(data.qty)) {
                if (data.cd_tani_shiyo == 4) {
                    element.findP("qty").text(App.num.format(data.qty, "#,#.######") + "Kg");
                }
                if (data.cd_tani_shiyo == 11) {
                    element.findP("qty").text(App.num.format(data.qty, "#,#.######") + "L");
                }
            }

            if (!App.isUndefOrNullOrStrEmpty(data.kikan_shomi)) {
                element.findP("kikan_shomi").text(App.num.format(data.kikan_shomi, "#,#") + " " + (!App.isUndefOrNullOrStrEmpty(data.nm_literal23) ? data.nm_literal23 : ""));
            }
            if (!App.isUndefOrNullOrStrEmpty(data.kikan_shomi_kaifu)) {
                element.findP("kikan_shomi_kaifu").text(App.num.format(data.kikan_shomi_kaifu, "#,#") + " " + (!App.isUndefOrNullOrStrEmpty(data.nm_literal23_kaifu) ? data.nm_literal23_kaifu : ""));
            }

            element.findP("nm_literal_kino21").val(data.nm_literal21);
            element.findP("nm_literal_kino21").text(data.nm_literal21);
            element.findP("nm_literal_kino22").val(data.nm_literal22);
            element.findP("nm_literal_kino22").text(data.nm_literal22);
            element.findP("nm_literal_kino23").val(data.nm_literal23);
            element.findP("nm_literal_kino23").text(data.nm_literal23);
            element.findP("nm_literal_kino23_kaifu").val(data.nm_literal23_kaifu);
            element.findP("nm_literal_kino23_kaifu").text(data.nm_literal23_kaifu);
        }

        if (param.kbn_bumon == App.settings.app.kbn_bumon.factory) {
            if (!App.isUndefOrNullOrStrEmpty(data.qty)) {
                if (data.cd_tani_shiyo == '04') {
                    element.findP("qty").text(App.num.format(data.qty, "#,#.######") + "Kg");
                }
                if (data.cd_tani_shiyo == '11') {
                    element.findP("qty").text(App.num.format(data.qty, "#,#.######") + "L");
                }
            }

            if (!App.isUndefOrNullOrStrEmpty(data.kikan_shomi)) {
                element.findP("kikan_shomi").text(App.num.format(data.kikan_shomi, "#,#") + " " + (!App.isUndefOrNullOrStrEmpty(data.nm_kino23) ? data.nm_kino23 : ""));
            }
            if (!App.isUndefOrNullOrStrEmpty(data.kikan_shomi_kaifu)) {
                element.findP("kikan_shomi_kaifu").text(App.num.format(data.kikan_shomi_kaifu, "#,#") + " " + (!App.isUndefOrNullOrStrEmpty(data.nm_kino23_kaifu) ? data.nm_kino23_kaifu : ""));
            }

            element.findP("nm_literal_kino21").val(data.nm_kino21);
            element.findP("nm_literal_kino21").text(data.nm_kino21);
            element.findP("nm_literal_kino22").val(data.nm_kino22);
            element.findP("nm_literal_kino22").text(data.nm_kino22);
            element.findP("nm_literal_kino23").val(data.nm_kino23);
            element.findP("nm_literal_kino23").text(data.nm_kino23);
            element.findP("nm_literal_kino23_kaifu").val(data.nm_kino23_kaifu);
            element.findP("nm_literal_kino23_kaifu").text(data.nm_kino23_kaifu);
        }
    };

    /**
      * 画面明細の各行にデータを設定する際のオプションを定義します。
    */
    _708_GenryoMasterSansho_Dialog.options.bindOption = {
        appliers: {
            dt_toroku: function (value, element) {
                element.text(App.data.getDateTimeString(new Date(value)));
                return true;
            },
            dt_henko: function (value, element) {
                element.text(App.data.getDateTimeString(new Date(value)));
                return true;
            },
            dt_toroku_kikaku: function (value, element) {
                element.text(App.data.getDateString(new Date(value)));
                return true;
            },
            dt_doto_SET_DATE: function (value, element) {
                element.text(App.data.getDateString(new Date(value)));
                return true;
            },
            qty: function (value, element) {
                element.text(value.toLocaleString());
                return true;
            },
            su_iri: function (value, element) {
                element.text(App.num.format(Number(value), "#,#"));
                return true;
            },
            kin_tanka: function (value, element) {
                element.text(App.num.format(Number(value), "#,#.##"));
                return true;
            },
            nisugata_qty: function (value, element) {
                element.text(App.num.format(Number(value), "#,#.######"));
                return true;
            },
            size_hachu: function (value, element) {
                element.text(App.num.format(Number(value), "#,#.########"));
                return true;
            },
            hijyu: function (value, element) {
                element.text(App.num.format(Number(value), "#,#.###"));
                return true;
            },
            su_saitei: function (value, element) {
                element.text(App.num.format(Number(value), "#,#.######"));
                return true;
            },
            budomari: function (value, element) {
                element.text(App.num.format(Number(value), "#,#.##"));
                return true;
            }
        }
    };
    /**
     * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
     */
    _708_GenryoMasterSansho_Dialog.select = function (e) {
        var element = _708_GenryoMasterSansho_Dialog.element,
            target = $(e.target),
            id = target.closest("tbody").attr("data-key"),
            data;

        if (App.isUndef(id)) {
            return;
        }

        data = _708_GenryoMasterSansho_Dialog.data.entry(id);

        if (App.isFunc(_708_GenryoMasterSansho_Dialog.dataSelected)) {
            if (!_708_GenryoMasterSansho_Dialog.dataSelected(data)) {
                element.modal("hide");
            }
        } else {
            element.modal("hide");
        }
    };

    //TODO: 複数セレクトを使用する場合は、上の単一セレクト用select関数を削除し、下の複数セレクト用の関数をコメント解除します
    //TODO: 単一セレクトの場合は、不要なコメント部分は削除してください
    <%--
        /**
         * 一覧から行を選択された際の処理を実行します。（複数セレクト用）
         */
        _708_GenryoMasterSansho_Dialog.select = function (e) {
            var element = _708_GenryoMasterSansho_Dialog.element,
                items;

            //TODO: 選択された行から起動元画面に返却したい値を抽出します
            items = element.find(".search-list").find("input:checked[name='select_cd']").map(function (index, item) {
                var tbody = $(item).closest("tbody"),
                    id = tbody.attr("data-key"),
                    data = _708_GenryoMasterSansho_Dialog.data.entry(id);
                return data.cd_torihiki;
            }).toArray();

            if (items.length == 0) {
                _708_GenryoMasterSansho_Dialog.notifyAlert.message(App.messages.base.MS0020).show();
                return;
            }

            if (App.isFunc(_708_GenryoMasterSansho_Dialog.dataSelected)) {
                if (!_708_GenryoMasterSansho_Dialog.dataSelected(items)) {
                    element.modal("hide");
                }
            } else {
                element.modal("hide");
            }
        };

        /**
         * 一覧の行をクリックした際の処理を実行します。（複数セレクト用）
         */
        _708_GenryoMasterSansho_Dialog.selectOne = function (e) {
            var target = $(e.target),
                tr = target.closest("tr");

            if (target.is("[name='select_cd']")) {
                return;
            }

            var check = tr.find("[name='select_cd']");
            check.prop("checked", !check.is(":checked"));
        };

        /**
         * 一覧のヘッダ部のckeckbox(ALLチェック用）をクリックした際の処理を実行します。（複数セレクト用）
         */
        _708_GenryoMasterSansho_Dialog.selectAll = function (e) {
            var target = $(e.target);
            _708_GenryoMasterSansho_Dialog.element.find("[name='select_cd']:visible").prop("checked", target.is(":checked"));
        };
--%>
</script>
<style type="text/css">
    div.FOODPROC {
        background: #9370DB;
        word-wrap: break-word;
        font-size: 15px;
        width: 2%;
        height: 100%;
        padding-top: 82px;
        padding-bottom: 82px;
        padding-right: 20px;
        padding-left: 10px;
        margin-left: 10px;
        margin-right: -5px;
    }

    div.G-Mat {
        background: #9370DB;
        word-wrap: break-word;
        font-size: 15px;
        width: 2%;
        height: 100%;
        padding-top: 50px;
        padding-bottom: 50px;
        padding-right: 20px;
        padding-left: 10px;
        margin-left: 10px;
        margin-right: -5px;
    }

    div.detail_foodproc {
        width: 96.2%;
        padding-top: 15px;
        padding-bottom: 13px;
    }

    div.detail_gmat {
        width: 95%;
        padding-top: 15px;
        padding-bottom: 13px;
    }

    span.center_text {
        padding: 0px 2px;
    }
</style>
<div class="modal fade wide" tabindex="-1" id="_708_GenryoMasterSansho_Dialog">
    <div class="modal-dialog" style="height: 500px; width: 70%">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">原料マスタ参照</h4>
            </div>

            <div class="modal-body smaller" style="padding: 10px">
                <div class="search-criteria">
                    <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
                    <div class="row">
                        <%--<div class="control-label col-xs-6" id="kojyo">
                            <label>工場コード</label>
                            <label data-prop="cd_kojyo"></label>
                        </div>--%>
                        <%--<div class="control col-xs-6" id="Div1" style="display: none"></div>--%>
                        <div class="control col-xs-6"></div>
                        <div class="control-label col-xs-6">
                            <label>登録日</label>
                            <label data-prop="dt_toroku"></label>
                        </div>
                    </div>
                    <div class="row">
                        <div id="show_busho">
                            <div class="control-label col-xs-6">
                                <div class="control col-xs-2">
                                    <label>工場名</label>
                                </div>
                                <div class="control col-xs-10">
                                    <label class="overflow-ellipsis" data-prop="nm_busho"></label>
                                </div>
                            </div>
                            <div class="control-label col-xs-6">
                                <label>更新日</label>
                                <label data-prop="dt_henko"></label>
                            </div>
                        </div>

                        <div id="hide_busho">
                            <div class="control col-xs-6">
                            </div>
                            <div class="control-label col-xs-3">
                                <label>更新日</label>
                                <label data-prop="dt_henko"></label>
                            </div>
                            <div class="control col-xs-3">
                                <label data-prop="nm_tanto"></label>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="detail">
                    <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
                    <div class="part">
                        <div class="row">
                            <div class="col-xs-1 center FOODPROC">
                                FOODPROCS
                            </div>
                            <div class="col-xs-11 detail_foodproc">
                                <div class="row">
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-5">
                                            <label>原料コード</label>
                                        </div>
                                        <div class="control col-xs-7">
                                            <label class="overflow-ellipsis" data-prop="cd_hin"></label>
                                        </div>
                                    </div>
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-6">
                                            <label>集計コード</label>
                                        </div>
                                        <div class="control col-xs-6">
                                            <label class="overflow-ellipsis" data-prop="cd_shukei"></label>
                                        </div>
                                    </div>
                                    <div class="control col-xs-4">
                                        <div class="control col-xs-6">
                                            <label class="center" id="mishiyo" style="background-color: red; color: white; display: none;">未使用</label>
                                        </div>
                                        <div class="control col-xs-6">
                                            <label class="overflow-ellipsis" data-prop="flg_mishiyo" style="display: none;"></label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="control-label col-xs-2" style="width: 14%">
                                        <label>原料名</label>
                                    </div>
                                    <div class="control col-xs-10" style="width: 86%">
                                        <label class="overflow-ellipsis" data-prop="nm_hin"></label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="control-label col-xs-2" style="width: 14%">
                                        <label>原料名略称</label>
                                    </div>
                                    <div class="control col-xs-10" style="width: 86%">
                                        <label class="overflow-ellipsis" data-prop="nm_hin_r"></label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="control-label col-xs-2" style="width: 14%">
                                        <label>分類</label>
                                    </div>
                                    <div class="control col-xs-10" style="width: 86%">
                                        <label class="overflow-ellipsis" data-prop="nm_bunrui"></label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-5">
                                            <label>荷姿表示用</label>
                                        </div>
                                        <div class="control col-xs-7">
                                            <label class="overflow-ellipsis" data-prop="nisugata_hyoji"></label>
                                        </div>
                                    </div>
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-6">
                                            <label>荷姿内容量</label>
                                        </div>
                                        <div class="control col-xs-6">
                                            <label class="overflow-ellipsis" data-prop="nisugata_qty"></label>
                                        </div>
                                    </div>
                                    <div class="control col-xs-4"></div>
                                </div>
                                <div class="row">
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-5">
                                            <label>入数</label>
                                        </div>
                                        <div class="control col-xs-7">
                                            <label class="overflow-ellipsis" data-prop="su_iri"></label>
                                        </div>
                                    </div>
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-6">
                                            <label>発注ロットサイズ</label>
                                        </div>
                                        <div class="control col-xs-6">
                                            <label class="overflow-ellipsis" data-prop="size_hachu"></label>
                                        </div>
                                    </div>
                                    <div class="control col-xs-4"></div>
                                </div>
                                <div class="row">
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-5">
                                            <label>一個の量</label>
                                        </div>
                                        <div class="control col-xs-7">
                                            <label class="overflow-ellipsis" data-prop="qty"></label>
                                        </div>
                                    </div>
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-6">
                                            <label>比重</label>
                                        </div>
                                        <div class="control col-xs-6">
                                            <label class="overflow-ellipsis" data-prop="hijyu"></label>
                                        </div>
                                    </div>
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-6">
                                            <label>歩留</label>
                                        </div>
                                        <div class="control col-xs-6">
                                            <label class="overflow-ellipsis" data-prop="budomari"></label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-5">
                                            <label>単価</label>
                                        </div>
                                        <div class="control col-xs-7">
                                            <label class="overflow-ellipsis" data-prop="kin_tanka"></label>
                                        </div>
                                    </div>
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-6">
                                            <label>納入単位</label>
                                        </div>
                                        <div class="control col-xs-6">
                                            <label class="overflow-ellipsis" data-prop="nm_tani"></label>
                                        </div>
                                    </div>
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-6">
                                            <label>納品リードタイム</label>
                                        </div>
                                        <div class="control col-xs-6">
                                            <label class="overflow-ellipsis" data-prop="su_leadtime"></label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-5">
                                            <label>メーカー品コード</label>
                                        </div>
                                        <div class="control col-xs-7">
                                            <label class="overflow-ellipsis" data-prop="cd_maker_hin"></label>
                                        </div>
                                    </div>
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-6">
                                            <label>最低在庫</label>
                                        </div>
                                        <div class="control col-xs-6">
                                            <label class="overflow-ellipsis" data-prop="su_saitei"></label>
                                        </div>
                                    </div>
                                    <div class="control col-xs-4"></div>
                                </div>
                                <div class="row">
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-5">
                                            <label>倉場所</label>
                                        </div>
                                        <div class="control col-xs-7">
                                            <label class="overflow-ellipsis" data-prop="nm_kura"></label>
                                        </div>
                                    </div>
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-6">
                                            <label>品位状態</label>
                                        </div>
                                        <div class="control col-xs-6">
                                            <label class="overflow-ellipsis" data-prop="nm_hini"></label>
                                        </div>
                                    </div>
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-6">
                                            <label>税区分</label>
                                        </div>
                                        <div class="control col-xs-6">
                                            <label class="overflow-ellipsis" data-prop="nm_zei"></label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-5">
                                            <label>状態区分</label>
                                        </div>
                                        <div class="control col-xs-7">
                                            <label class="overflow-ellipsis" data-prop="nm_literal_kino21"></label>
                                        </div>
                                    </div>
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-6">
                                            <label>荷受場所</label>
                                        </div>
                                        <div class="control col-xs-6">
                                            <label class="overflow-ellipsis" data-prop="nm_niuke"></label>
                                        </div>
                                    </div>
                                    <div class="control col-xs-3" style="width: 20%">
                                        <label data-prop="ksys_denso" style="background-color: blue; color: white;"></label>
                                    </div>
                                    <div class="control col-xs-2" style="width: 13.3%">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-5">
                                            <label>使用期限</label>
                                        </div>
                                        <div class="control col-xs-7">
                                            <label class="overflow-ellipsis" data-prop="kikan_shomi"></label>
                                        </div>
                                    </div>
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-6">
                                            <label>開封後使用期限</label>
                                        </div>
                                        <div class="control col-xs-6">
                                            <label class="overflow-ellipsis" data-prop="kikan_shomi_kaifu"></label>
                                        </div>
                                    </div>
                                    <div class="control col-xs-4"></div>
                                </div>
                                <div class="row">
                                    <div class="control-label col-xs-2" style="width: 14%">
                                        <label>備考</label>
                                    </div>
                                    <div class="control col-xs-10" style="width: 86%">
                                        <label class="overflow-ellipsis" data-prop="biko"></label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="part">
                        <div class="row">
                            <div class="col-xs-1 center G-Mat">
                                G<span class="center_text">|</span>M<span class="center_text">a</span><span style="padding: 0px 3px;">t</span>
                            </div>
                            <div class="col-xs-11 detail_gmat" style="width: 96.2%">
                                <div class="row">
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-5">
                                            <label>規格書No</label>
                                        </div>
                                        <div class="control col-xs-7">
                                            <label class="overflow-ellipsis" data-prop="no_kikaku"></label>
                                        </div>
                                    </div>
                                    <div class="control-label col-xs-8">
                                        <div class="control col-xs-3">
                                            <label>同等品コード</label>
                                        </div>
                                        <div class="control col-xs-9">
                                            <label class="overflow-ellipsis" data-prop="cd_doto_hin"></label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-5">
                                            <label>規格書登録日</label>
                                        </div>
                                        <div class="control col-xs-7">
                                            <label class="overflow-ellipsis" data-prop="dt_toroku_kikaku"></label>
                                        </div>
                                    </div>
                                    <div class="control-label col-xs-8" id="hide_hin_doto">
                                        <div class="control col-xs-3">
                                            <label>同等品名</label>
                                        </div>
                                        <div class="control col-xs-9">
                                            <label class="overflow-ellipsis" data-prop="nm_hin_doto"></label>
                                        </div>
                                    </div>
                                    <div class="control-label col-xs-8" id="show_doto_kaisha" style="display: none">
                                        <div class="control col-xs-3">
                                            <label>同等品会社コード</label>
                                        </div>
                                        <div class="control col-xs-9">
                                            <label class="overflow-ellipsis" data-prop="cd_doto_kaisha"></label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="control-label col-xs-4">
                                        <div class="control col-xs-5">
                                            <label>規格書商品名</label>
                                        </div>
                                        <div class="control col-xs-7">
                                            <label class="overflow-ellipsis" data-prop="nm_kikaku"></label>
                                        </div>
                                    </div>
                                    <div class="control-label col-xs-8" id="hide_doto_kaisha">
                                        <div class="control col-xs-3">
                                            <label>同等品会社コード</label>
                                        </div>
                                        <div class="control col-xs-9">
                                            <label class="overflow-ellipsis" data-prop="cd_doto_kaisha"></label>
                                        </div>
                                    </div>
                                    <div class="control-label col-xs-8" id="show_doto_SET_DATE" style="display: none">
                                        <div class="control col-xs-3">
                                            <label>同等品コード設定日</label>
                                        </div>
                                        <div class="control col-xs-9">
                                            <label class="overflow-ellipsis" data-prop="dt_doto_SET_DATE"></label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div id="show_kaisha">
                                        <div class="control-label col-xs-2" style="width: 14%">
                                            <label>物質名</label>
                                        </div>
                                        <div class="control col-xs-10" style="width: 86%">
                                            <label class="overflow-ellipsis" data-prop="nm_bussitsu"></label>
                                        </div>
                                    </div>
                                    <div id="hide_kaisha">
                                        <div class="control-label col-xs-4">
                                            <div class="control col-xs-5">
                                                <label>物質名</label>
                                            </div>
                                            <div class="control col-xs-7">
                                                <label class="overflow-ellipsis" data-prop="nm_bussitsu"></label>
                                            </div>
                                        </div>
                                        <div class="control-label col-xs-8">
                                            <div class="control col-xs-3">
                                                <label>同等品会社名</label>
                                            </div>
                                            <div class="control col-xs-9">
                                                <label class="overflow-ellipsis" data-prop="nm_kaisha"></label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div id="show_SET_DATE">
                                        <div class="control-label col-xs-2" style="width: 14%">
                                            <label>品位</label>
                                        </div>
                                        <div class="control col-xs-10" style="width: 86%">
                                            <label class="overflow-ellipsis" data-prop="nm_literal_kino22"></label>
                                        </div>
                                    </div>
                                    <div id="hide_doto_SET_DATE">
                                        <div class="control-label col-xs-4">
                                            <div class="control col-xs-5">
                                                <label>品位</label>
                                            </div>
                                            <div class="control col-xs-7">
                                                <label class="overflow-ellipsis" data-prop="nm_literal_kino22"></label>
                                            </div>
                                        </div>
                                        <div class="control-label col-xs-8">
                                            <div class="control col-xs-3">
                                                <label>同等品コード設定日</label>
                                            </div>
                                            <div class="control col-xs-9">
                                                <label class="overflow-ellipsis" data-prop="dt_doto_SET_DATE"></label>
                                            </div>
                                        </div>
                                    </div>
                                    <%--<div class="control-label col-xs-4">
                                        <div class="control col-xs-5">
                                            <label>品位</label>
                                        </div>
                                        <div class="control col-xs-7">
                                            <label class="overflow-ellipsis" data-prop="nm_literal_kino22"></label>
                                        </div>
                                    </div>
                                    <div class="control-label col-xs-8" id="hide_doto_SET_DATE">
                                        <div class="control col-xs-3">
                                            <label>同等品コード設定日</label>
                                        </div>
                                        <div class="control col-xs-9">
                                            <label class="overflow-ellipsis" data-prop="dt_doto_SET_DATE"></label>
                                        </div>
                                    </div>
                                    <div class="control col-xs-8" id="Div4"></div>--%>
                                </div>
                                <div class="row">
                                    <div class="control-label col-xs-2" style="width: 14%">
                                        <label>販売会社</label>
                                    </div>
                                    <div class="control col-xs-10" style="width: 86%">
                                        <label class="overflow-ellipsis" data-prop="nm_hanbai"></label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="control-label col-xs-2" style="width: 14%">
                                        <label>製造会社</label>
                                    </div>
                                    <div class="control col-xs-10" style="width: 86%">
                                        <label class="overflow-ellipsis" data-prop="nm_seizo"></label>
                                    </div>
                                </div>
                            </div>
                        </div>
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

            <%--<div class="modal-footer">--%>
            <%--複数セレクトの場合は、下の１行をコメント解除してください--%>
            <%--<button type="button" class="btn btn-success select" name="select" >選択</button>--%>
            <%--<button type="button" class="btn btn-success search" name="select">選択</button>--%>
            <%--<button type="button" class="cancel-button btn btn-sm btn-default" data-dismiss="modal" name="close">閉じる</button>--%>
            <%--</div>--%>
        </div>
    </div>
</div>
