<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="300_SeihoBunshoSakusei_HaigoHyo_Tab.aspx.cs" Inherits="Tos.Web.Pages._300_SeihoBunshoSakusei_HaigoHyo_Tab" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>

<style>
    #_300_SeihoBunshoSakusei_HaigoHyo_Tab .part.roof {
        margin-right: 0px;
        height: 100%;
        margin-bottom: 0PX;
    }
    #_300_SeihoBunshoSakusei_HaigoHyo_Tab .gray {
        background-color: #bbbbbb;
    }
    /*#_300_SeihoBunshoSakusei_HaigoHyo_Tab select {
        text-align-last: right;
    }
    #_300_SeihoBunshoSakusei_HaigoHyo_Tab select option {
        direction: rtl;
    }*/
</style>

<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var _300_SeihoBunshoSakusei_HaigoHyo_Tab = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.dialogDataTakeCount   // TODO:取得するデータ数を指定します。
        },
        urls: {
            search: "../api/_300_SeihoBunshoSakusei_HaigoHyo_Tab"
        },
        param: {
            no_seiho: ""
        },
        name: "【配合表】"
    };

    /**
     * 検索ダイアログの初期化処理を行います。
     */
    _300_SeihoBunshoSakusei_HaigoHyo_Tab.initialize = function () {
        var element = $("#_300_SeihoBunshoSakusei_HaigoHyo_Tab");

        _300_SeihoBunshoSakusei_HaigoHyo_Tab.element = element;
        element.on("change", "[data-prop='cd_haigo']", function (e) {
            _300_SeihoBunshoSakusei_HaigoHyo_Tab.bindHeader();
            _300_SeihoBunshoSakusei_HaigoHyo_Tab.bindDetail();
        })

        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        _300_SeihoBunshoSakusei_HaigoHyo_Tab.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#_300_SeihoBunshoSakusei_HaigoHyo_Tab .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        _300_SeihoBunshoSakusei_HaigoHyo_Tab.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#_300_SeihoBunshoSakusei_HaigoHyo_Tab .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body",
                show: function () {
                    element.find(".alert-message").show();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                }
            });

        _300_SeihoBunshoSakusei_HaigoHyo_Tab.validator = element.validation(App.validation(_300_SeihoBunshoSakusei_HaigoHyo_Tab.options.validations, {
            success: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _300_SeihoBunshoSakusei_HaigoHyo_Tab.setColValidStyle(item.element);

                    _300_SeihoBunshoSakusei_HaigoHyo_Tab.notifyAlert.remove(item.element);
                }
            },
            fail: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _300_SeihoBunshoSakusei_HaigoHyo_Tab.setColInvalidStyle(item.element);
                    if (state && state.suppressMessage) {
                        continue;
                    }

                    _300_SeihoBunshoSakusei_HaigoHyo_Tab.notifyAlert.message(item.message, item.element).show();
                }
            },
            always: function (results) {
                //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
            }
        }));
        var table = element.find(".datatable"),
            offsetHeight = 80,
            datatable = table.dataTable({
                height: 300,
                resize: true,
                resizeOffset: offsetHeight,
                onselect: _300_SeihoBunshoSakusei_HaigoHyo_Tab.select
            });
        table = element.find(".datatable");        //列固定にした場合DOM要素が再作成されるため、変数を再取得

        _300_SeihoBunshoSakusei_HaigoHyo_Tab.dataTable = datatable;
        // 行選択時に利用するテーブルインデックスを指定します
        _300_SeihoBunshoSakusei_HaigoHyo_Tab.fixedColumnIndex = element.find(".fix-columns").length;

        _300_SeihoBunshoSakusei_HaigoHyo_Tab.search();

        page.finishLoading("TAB_Initilize", "TAB10", 5);

    };

    /**
     * Datatable selected row event
     * 
     */
    _300_SeihoBunshoSakusei_HaigoHyo_Tab.select = function (e, row) {
        if (row && row.element && !row.element.hasClass("selected-row")) {
            _300_SeihoBunshoSakusei_HaigoHyo_Tab.element.find(".selected-row").removeClass("selected-row");
            row.element.find("tr").addClass("selected-row");
        }
    }

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    _300_SeihoBunshoSakusei_HaigoHyo_Tab.setColInvalidStyle = function (target) {
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
    _300_SeihoBunshoSakusei_HaigoHyo_Tab.setColValidStyle = function (target) {
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
     * 検索ダイアログ　バリデーションルールを定義します。
     */
    _300_SeihoBunshoSakusei_HaigoHyo_Tab.options.validations = {
    };

    _300_SeihoBunshoSakusei_HaigoHyo_Tab.options.bindOptions = {
        appliers: {
            kbn_bunkatsu: function (value, element) {
                if (value == 0) {
                    return true;
                }
            },
            flg_shitei: function (value, element) {
                if (value) {
                    element.text("●");
                }
                return true;
            },
            dt_toroku: function (value, element) {
                element.text(App.data.getDateString(new Date(value)));
                return true;
            },
            qty_haigo: function (value, element) {
                element.text(App.num.format(value, "#,0.000"));
                return true;
            },
            qty_nisugata: function (value, element) {
                element.text(App.num.format(value, "#,0.000"));
                return true;
            },
            hijyu: function (value, element) {
                element.text(App.num.format(value, "0.000"));
                return true;
            },
            budomari: function (value, element) {
                element.text(App.num.format(value, "0.00"));
                return true;
            },
            gosa: function (value, element) {
                element.text(App.num.format(value, "0.###"));
                return true;
            },
            //cd_hin: function (value, element) {
            //    element.text(App.common.fillString(value, page.values.su_code_standard));
            //    return true;
            //}
        }
    }

    /**
     * 検索ダイアログの検索処理を実行します。
     */
    _300_SeihoBunshoSakusei_HaigoHyo_Tab.search = function () {
        var element = _300_SeihoBunshoSakusei_HaigoHyo_Tab.element,
            loadingTaget = element.find(".modal-content"),
            table = element.find(".search-list"),
            url = _300_SeihoBunshoSakusei_HaigoHyo_Tab.urls.search;

        _300_SeihoBunshoSakusei_HaigoHyo_Tab.options.filter = _300_SeihoBunshoSakusei_HaigoHyo_Tab.createFilter();

        (!_300_SeihoBunshoSakusei_HaigoHyo_Tab.param.canSearch ? App.async.success({ lstHaigo: [], lstHaigoHeader: [], lstHaigoDetail: [] }) :
        $.ajax(App.ajax.webapi.get(url, _300_SeihoBunshoSakusei_HaigoHyo_Tab.options.filter)))
        .done(function (result) {

            _300_SeihoBunshoSakusei_HaigoHyo_Tab.bind(result);
        }).always(function () {

            App.ui.loading.close(loadingTaget);
        });
    };

    _300_SeihoBunshoSakusei_HaigoHyo_Tab.bindHaigoOptions = function (data) {
        /// Bind cbb cd_haigo
        var cd_haigo = _300_SeihoBunshoSakusei_HaigoHyo_Tab.element.findP("cd_haigo");
        cd_haigo.children().remove();
        for (var i = 0; i < data.length ; i++) {
            var cData = data[i];
            var option = $("<option value='" + cData.cd_haigo + "' data-mishiyo='" + cData.flg_mishiyo + "'>" + cData.cd_haigo + "</option>");
            cd_haigo.append(option);
        }
    }

    _300_SeihoBunshoSakusei_HaigoHyo_Tab.bind = function (data) {
        if (data) {
            _300_SeihoBunshoSakusei_HaigoHyo_Tab.bindHaigoOptions(data.lstHaigo)
            /// Bind header haigo infor
            _300_SeihoBunshoSakusei_HaigoHyo_Tab.beforeBindHeader(data.lstHaigoHeader);
            /// Bind detail haigo infor
            _300_SeihoBunshoSakusei_HaigoHyo_Tab.beforeBindDetail(data.lstHaigoDetail);
            page.finishLoading("TAB_Bind", "TAB10", 5);
        }
    }

    /**
     * 検索ダイアログの検索条件を組み立てます
     */
    _300_SeihoBunshoSakusei_HaigoHyo_Tab.createFilter = function () {
        var param = _300_SeihoBunshoSakusei_HaigoHyo_Tab.param,
            settings = App.settings.app;
        return {
            no_seiho: param.no_seiho,
            loop_count: settings.tenkaiLoopCount,
            kbn_genryo: settings.kbnHin.genryo,
            kbn_haigo: settings.kbnHin.haigo,
            kbn_shikakari: settings.kbnHin.shikakari,
            kbn_maeshori: settings.kbnHin.maeshoriGenryo,
            kbn_sagyo: settings.kbnHin.sagyo,
            su_code_standard: page.values.su_code_standard,
            kbn_tani_kg: settings.taniShiyo.kg,
            kbn_tani_l: settings.taniShiyo.l,
            cd_literal_status: settings.cd_category.kbn_status,
            kbn_gam: settings.taniShiyo.g,
            kbn_shikakari_kaihatsu: settings.kbn_shikakari.kaihatsu,
            kbn_shikakari_FP: settings.kbn_shikakari.foodprocs,
            flg_mishiyo: settings.flg_mishiyo.shiyo,
        }
    };

    /**
     * Group detail data by cd_haigo
     * When change combobox cd_haigo, the detail data will be change
     */
    _300_SeihoBunshoSakusei_HaigoHyo_Tab.beforeBindDetail = function (data) {
        data = (data && data.Items) ? data.Items : data ? data : [];
        var element = _300_SeihoBunshoSakusei_HaigoHyo_Tab.element,
            searchData = {};

        $.each(data, function (ind, item) {
            if (!searchData["_" + item.cd_haigo]) {
                searchData["_" + item.cd_haigo] = [];
            }
            searchData["_" + item.cd_haigo].push(item);
        });
        _300_SeihoBunshoSakusei_HaigoHyo_Tab.searchDetailData = searchData;
        _300_SeihoBunshoSakusei_HaigoHyo_Tab.bindDetail();
    }

    /**
     * Bind Haigo header informations
     */
    _300_SeihoBunshoSakusei_HaigoHyo_Tab.beforeBindHeader = function (data) {
        data = (data && data.Items) ? data.Items : data ? data : [];
        var searchData = {};

        $.each(data, function (ind, item) {
            searchData["_" + item.cd_haigo] = item;
        });
        _300_SeihoBunshoSakusei_HaigoHyo_Tab.searchHeaderData = searchData;
        _300_SeihoBunshoSakusei_HaigoHyo_Tab.bindHeader();

    }

    /**
     * Bind Haigo header informations
     */
    _300_SeihoBunshoSakusei_HaigoHyo_Tab.bindHeader = function () {
        var element = _300_SeihoBunshoSakusei_HaigoHyo_Tab.element.find(".header-part"),
            cd_haigo = element.findP("cd_haigo").val(),
            flg_mishiyo = element.findP("cd_haigo").find("option:selected").attr("data-mishiyo"),
            data = _300_SeihoBunshoSakusei_HaigoHyo_Tab.searchHeaderData["_" + cd_haigo] || {},
            kbn_status = App.settings.app.kbn_status;

        if (flg_mishiyo === App.settings.app.flg_mishiyo.mishiyo.toString()) {
            data.flg_mishiyo = "未使用・廃止";
        } else {
            data.flg_mishiyo = "";
        }
        // Clear old data
        element.find("label[data-prop]").text("");
        // Bind new data
        element.form(_300_SeihoBunshoSakusei_HaigoHyo_Tab.options.bindOptions).bind(data);
        switch (data.status) {
            /// 編集中
            case kbn_status.henshuchu:
            /// 受信中
            case kbn_status.jushinchu:
                element.findP("nm_status").css("color", "");
                break;
            /// 申請済
            case kbn_status.shinseisumi:
            /// 承認１
            case kbn_status.shonin1:
            /// 承認２
            case kbn_status.shonin2:
                element.findP("nm_status").css("color", "blue");
                break;
            /// 伝送済
            case kbn_status.densosumi:
                element.findP("nm_status").css("color", "green");
                break;
            /// 工場確認済
            case kbn_status.kojokakunin:
                element.findP("nm_status").css("color", "red");
                break;
            default:
                element.findP("nm_status").css("color", "");
                break;
        }
    }

    /**
     * Bind Haigo meisai informations
     */
    _300_SeihoBunshoSakusei_HaigoHyo_Tab.bindDetail = function () {
        var element = _300_SeihoBunshoSakusei_HaigoHyo_Tab.element,
            cd_haigo = element.findP("cd_haigo").val(),
            data = _300_SeihoBunshoSakusei_HaigoHyo_Tab.searchDetailData["_" + cd_haigo];

        if (!data) {
            data = [];
        }
        _300_SeihoBunshoSakusei_HaigoHyo_Tab.dataTable.dataTable("clear");
        _300_SeihoBunshoSakusei_HaigoHyo_Tab.dataTable.dataTable("addRows", data, function (row, item) {
            row.form(_300_SeihoBunshoSakusei_HaigoHyo_Tab.options.bindOptions).bind(item);
            if (item.is_mishiyo) {
                row.addClass("gray");
            }
            return row;
        }, true);
    };
</script>

<div class="tab-pane" tabindex="-1" id="_300_SeihoBunshoSakusei_HaigoHyo_Tab">
    <div class="sub-tab-content smaller">
        <div class="part roof">
            <div class="part-content full-height">
                <div class="header-part">
                    <div class="row">
                        <div class="col-xs-1 control-label">
                            <label>配合コード</label>
                        </div>
                        <div class="col-xs-2 control">
                            <select data-prop="cd_haigo" style="width: 150px;"></select>
                        </div>
                        <div class="col-xs-1 control-label">
                            <label>配合名</label>
                        </div>
                        <div class="col-xs-4 control">
                            <label data-prop="nm_haigo"></label>
                        </div>
                        <div class="col-xs-4 control"></div>
                    </div>
                    <div class="row">
                        <div class="col-xs-1 control-label">
                            <label>配合登録者</label>
                        </div>
                        <div class="col-xs-2 control">
                            <label data-prop="nm_user"></label>
                        </div>
                        <div class="col-xs-1 control-label">
                            <label>配合作成日</label>
                        </div>
                        <div class="col-xs-2 control">
                            <label data-prop="dt_toroku"></label>
                        </div>
                        <div class="col-xs-1 control-label">
                            <label>ステータス</label>
                        </div>
                        <div class="col-xs-1 control">
                            <label data-prop="nm_status" class="status" style="font-weight: bold"></label>
                        </div>
                        <div class="col-xs-1 control">
                        </div>
                        <div class="col-xs-3 control">
                            <label data-prop="flg_mishiyo"></label>
                        </div>
                    </div>
                </div>
                <div class="detail" style="margin-top: 4px">
                    <table class="datatable">
                        <thead>
                            <tr>
                                <th style="width: 40px">分割</th>
                                <th style="width: 40px">工程</th>
                                <th style="width: 80px">投入順</th>
                                <th style="width: 130px">コード</th>
                                <th style="width: 30px">指</th>
                                <th style="width: 494px">品名／作業指示</th>
                                <th style="width: 60px">マーク</th>
                                <th style="width: 130px">配合重量</th>
                                <th style="width: 70px">単位</th>
                                <th style="width: 130px">荷姿重量</th>
                                <th style="width: 70px">比重</th>
                                <th style="width: 70px">歩留</th>
                                <th style="width: 150px">規格書番号</th>
                                <th style="width: 70px">誤差</th>
                            </tr>
                        </thead>
                        <tbody class="item-tmpl" style="display: none">
                            <tr>
                                <td class="right">
                                    <label data-prop="kbn_bunkatsu"></label>
                                </td>
                                <td class="right">
                                    <label data-prop="no_kotei"></label>
                                </td>
                                <td class="right">
                                    <label data-prop="no_tonyu"></label>
                                </td>
                                <td class="right">
                                    <label data-prop="cd_hin"></label>
                                </td>
                                <td class="center">
                                    <label data-prop="flg_shitei" style="font-size: 18px; margin-top: -4px;"></label>
                                </td>
                                <td>
                                    <label data-prop="nm_hin"></label>
                                </td>
                                <td class="center">
                                    <label data-prop="nm_mark"></label>
                                </td>
                                <td class="right">
                                    <label data-prop="qty_haigo"></label>
                                </td>
                                <td class="center">
                                    <label data-prop="nm_tani"></label>
                                </td>
                                <td class="right">
                                    <label data-prop="qty_nisugata"></label>
                                </td>
                                <td class="right">
                                    <label data-prop="hijyu"></label>
                                </td>
                                <td class="right">
                                    <label data-prop="budomari"></label>
                                </td>
                                <td>
                                    <label data-prop="no_kikaku"></label>
                                </td>
                                <td class="right">
                                    <label data-prop="gosa"></label>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
