<%@ Page Title="209_配合登録_工場部門"  Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="209_HaigoTorokuKojyoBumon.aspx.cs" Inherits="Tos.Web.Pages.HaigoTorokuKojyoBumon" %>
<%@ MasterType VirtualPath="~/Site.Master" %>

<asp:Content ID="IncludeContent" ContentPlaceHolderID="IncludeContent" runat="server">

    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/part.css") %>" type="text/css" />
    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/headerdetail.css") %>" type="text/css" />

    <% #if DEBUG %>
    <script src="<%=ResolveUrl("~/Scripts/datatable.js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Scripts/part.js") %>" type="text/javascript"></script>
    <% #else %>
    <script src="<%=ResolveUrl("~/Scripts/datatable.min.js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Scripts/part.min.js") %>" type="text/javascript"></script>
    <% #endif %>

</asp:Content>


<asp:Content ID="HeadContent" ContentPlaceHolderID="HeadContent" runat="server">
    <title></title>
    <style type="text/css">
        .row-error-past {
            background-color:#bbbbbb !important;
        }

        /*.dt-body table.datatable {
            margin-top: -1px!important;
        }*/
    </style>

    <script type="text/javascript">
        //表示用配合登録(工場部門)
        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("HyoujiyouHaigouTouroku", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {},
            values: {
                isChangeRunning: {},
                ma_tani: {},
                ma_mark:{},
                M_HaigoToroku: null,
                flg_edit_shikakarihin: null,
                cd_kaisha: null,
                cd_kojyo: null,
                M_kirikae: null,
                cd_haigo: null,
                no_han: null,
                no_seiho:null,
                su_code_standard: null,
                no_haigo: null,
                modeReadOnly: false,
                shijiMin: 999990,
                shijiMax: 999999,
                maxNumberRowDetail: 99,
                noHanFirst: 1,
                shikakarihin_edit: 1,
                maxHan: 0,
                resultSearch: {},
                modeReadOnly: false,
                //Kengen
                id_kino_etsuran: 10,
                id_kino_sagyo_shiji: 20,
                id_kino_jisshi_kano: 30,
                id_data: 9,
                kengen_kino_jisshi_kano: false,
                allDisabledInput:false,
                kbn_nmacs_relate: 1,
                kbn_nmacs_general: 2
            },
            urls: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx",                                
                haigoTorokuKojyoBumon: "../api/HaigoTorokuKojyoBumon",
                vw_kaisha_kojyo: "../Services/ShisaQuickService.svc/vw_kaisha_kojyo?$filter=cd_kaisha eq {0} and cd_kojyo eq {1}",
                ma_seiho_denso: "../Services/ShisaQuickService.svc/ma_seiho_denso?$filter=no_seiho eq '{0}' and cd_kaisha eq {1} and cd_kojyo eq {2} and flg_denso_jyotai eq true",
                ma_seiho_bunsho_hyoshi: "../Services/ShisaQuickService.svc/ma_seiho_bunsho_hyoshi?$filter=no_seiho eq '{0}'",
                hinmeiKaihatsuDialog: "Dialogs/710_HinmeiKaihatsu_Dialog.aspx",
                markDialog: "Dialogs/706_MarkKensaku_Dialog.aspx",
                tsuikaJohoNyuryokuDialog: "Dialogs/702_TsuikaJohoNyuryoku_Dialog.aspx",
                seihinJohoNyuryokuDialog: "Dialogs/701_SeihinJohoNyuryoku_Dialog.aspx",
                haigoTorikomiDialog: "Dialogs/700_HaigoTorikomi_Dialog.aspx",
                haigoCopyDialog: "Dialogs/705_HaigoCopy_Dialog.aspx",
                seihoBunshoSakuseiDialog: "Dialogs/801_SeihobunshoSakusei_Dialog.aspx",
                mainMenu: "MainMenu.aspx",
                excelHaigoList: "../api/_300_SeihoBunshoSakusei_Excel/Post",
                excelHaigoSakusei: "../api/_302_HaigoSakuseiHyo_Excel/Post"
            },
            header: {
                options: {},
                values: {},
                urls: {
                    vw_no_seiho_shurui: "../Services/ShisaQuickService.svc/vw_no_seiho_shurui?$orderby=cd_hin_syurui,no_sort",
                    getComboboxKbnHin: "../api/Common/GetShinaKubun",
                    vw_ma_kojyo: "../Services/ShisaQuickService.svc/vw_ma_kojyo?$filter=cd_kaisha eq {0} and cd_kojyo eq {1}"
                }
            },
            detail: {
                options: {
                    downloadCSV: "../api/_209_HaigoTorokuKojyoBumonDownloadCSV",
                    downloadCSV_2: "../api/_209_HaigoTorokuKojyoBumonDownloadCSV/GetCSV_2"
                },
                values: {}
            },
            dialogs: {},
            commands: {},
            ma_haigo_mei_hyoji: {},
            ma_haigo_recipe_hyoji: {},
            ma_seizo_line_hyoji: {},
            ma_haigo_mei: {},
            ma_haigo_recipe: {},
            ma_seizo_line: {},
            ma_seihin: {},
            ma_konpo: {},
            ma_seizo_line_hyoji_temp: {},
            ma_seizo_line_temp:{},
            ma_seihin_temp: {},
            ma_konpo_temp: {}
        });

        var EntityState = {
            Unchanged: 2,
            Added: 4,
            Deleted: 8,
            Modified: 16
        };

        /**
         * 単項目要素をエラーのスタイルに設定します。
         * @param target 設定する要素
         */
        page.setColInvalidStyle = function (target) {
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
        page.setColValidStyle = function (target) {
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
         * バリデーション成功時の処理を実行します。
         */
        page.validationSuccess = function (results, state) {
            var i = 0, l = results.length,
                item, $target;

            for (; i < l; i++) {
                item = results[i];
                if (state && state.isGridValidation) {
                    page.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                        if (row && row.element) {
                            if (App.isUndefOrNull(state.notSetStyle) || !state.notSetStyle) {
                                row.element.find("tr").removeClass("has-error");
                                $(item.element).removeClass("validate-error")
                            }
                        }
                    });
                } else {
                    page.setColValidStyle(item.element);
                }

                if (!(state && state.isGridValidation && !App.isUndefOrNull(state.notSetStyle) && state.notSetStyle)) {
                    App.ui.page.notifyAlert.remove(item.element);
                }
            }
        };

        /**
         * バリデーション失敗時の処理を実行します。
         */
        page.validationFail = function (results, state) {

            var i = 0, l = results.length,
                item, $target;

            for (; i < l; i++) {
                item = results[i];
                if (state && state.isGridValidation) {
                    page.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                        if (row && row.element) {
                            if (App.isUndefOrNull(state.notSetStyle) || !state.notSetStyle) {
                                row.element.find("tr").addClass("has-error");
                                $(item.element).addClass("validate-error")
                            }
                        }
                    });
                } else {
                    page.setColInvalidStyle(item.element);
                }

                if (state && state.suppressMessage) {
                    continue;
                }
                if (!(state && state.isGridValidation && !App.isUndefOrNull(state.notSetStyle) && state.notSetStyle)) {
                    App.ui.page.notifyAlert.message(item.message, item.element).show();
                }
            }
        };

        /**
         * バリデーション後の処理を実行します。
         */
        page.validationAlways = function (results) {
            //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
        };

        /**
          * 指定された定義をもとにバリデータを作成します。
          * @param target バリデーション定義
          * @param options オプションに設定する値。指定されていない場合は、
          *                画面の success/fail/always のハンドル処理が指定されたオプションが設定されます。
          */
        page.createValidator = function (target, options) {
            return App.validation(target, options || {
                success: page.validationSuccess,
                fail: page.validationFail,
                always: page.validationAlways
            });
        };

        /**
         * Check empty value
         */
        page.isEmpty = function (value) {
            return App.isUndef(value) || App.isNull(value) || (App.isStr(value) && value.length === 0);
        }

        /**
         * convert number
         */
        page.toNum = function (val) {
            if (page.isEmpty(val)) {
                return undef;
            }

            if (!App.isNum(val)) {
                if (App.isNumeric(val)) {
                    return parseFloat(val);
                } else {
                    return undef;
                }
            }
            return val;
        }

        /**
         * Get save qty haigo base on mark
         */
        page.detail.getSaveQtyHaigo = function (cd_mark, qtyOri, defValue) {
            var mark = page.getObjMark(cd_mark, null),
                qty = defValue;
            if (mark && !App.isUndefOrNull(qtyOri)) {
                if (mark.cd_tani_shiyo == App.settings.app.taniShiyo.g) {
                    qty = new BigNumber(qtyOri).div(1000).toNumber();
                } else {
                    qty = qtyOri;
                }
            }
            return qty;
        }

        /**
         * Save data
         */
        page.saveData = function () {
            var options = {
                text: App.messages.app.AP0004
            };

            page.dialogs.confirmDialog.confirm(options)
            .then(function () {

                App.ui.loading.show();
                page.ma_haigo_mei_hyoji.data = App.ui.page.dataSet();
                page.ma_haigo_mei.data = App.ui.page.dataSet();
                page.ma_haigo_recipe_hyoji.data = App.ui.page.dataSet();
                page.ma_haigo_recipe.data = App.ui.page.dataSet();
                page.ma_seizo_line_hyoji.data = App.ui.page.dataSet();
                page.ma_seizo_line.data = App.ui.page.dataSet();
                page.ma_seihin.data = App.ui.page.dataSet();
                var parameter = {};

                // Start mode toroku
                if (page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.shinki || page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.copy
                    || page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.newVersion) {
                    if (page.values.M_kirikae == App.settings.app.m_kirikae.hyoji) {

                        //ma_haigo_mei_hyoji
                        var entity_header = {},
                            data_header = page.header.element.form().data();
                        page.ma_haigo_mei_hyoji.data.add(entity_header);
                        entity_header["cd_haigo"] = page.header.element.findP("cd_haigo").val()
                        entity_header["nm_haigo"] = data_header["nm_haigo"]
                        entity_header["nm_haigo_r"] = App.isUndefOrNull(data_header["nm_haigo_r"]) ? "" : data_header["nm_haigo_r"]
                        entity_header["kbn_hin"] = data_header["kbn_hin"]
                        entity_header["cd_bunrui"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").val() == "" ? "" : App.num.format(Number(page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").val()), "00")
                        entity_header["budomari"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("budomari").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("budomari").val()
                        entity_header["qty_kihon"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val()
                        if (entity_header["qty_kihon"]) {
                            entity_header["qty_kihon"] = entity_header["qty_kihon"].replace(/,/g, '');
                        }
                        entity_header["ritsu_kihon"] = data_header["ritsu_kihon"]
                        //entity_header["cd_setsubi"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi").val() == "" ? null : App.num.format(Number(page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi").val()), "00")
                        var cd_setsubi = page.dialogs.tsuikaJohoNyuryokuDialog.values.defaultSetsubi;
                        if (cd_setsubi === undefined) {
                            cd_setsubi = "00";
                        }
                        if (page.dialogs.tsuikaJohoNyuryokuDialog.values.isChangeSetsubi) {
                            cd_setsubi = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi").val();
                        }
                        // Case Insert set cd_setsubi = null when empty
                        if (cd_setsubi === "") {
                            cd_setsubi = null;
                        }
                        entity_header["cd_setsubi"] = cd_setsubi;
                        entity_header["flg_gasan"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("flg_gasan").prop("checked") ? true : false
                        entity_header["qty_max"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_max").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_max").val()
                        entity_header["no_han"] = data_header["no_han"];
                        entity_header["qty_haigo_h"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val()
                        if (entity_header["qty_haigo_h"]) {
                            entity_header["qty_haigo_h"] = entity_header["qty_haigo_h"].replace(/,/g, '');
                        }
                        entity_header["qty_haigo_kei"] = $(page.detail.element.findP("rdo_shiage")[1]).prop("checked") ? $(page.detail.element.findP("qty_haigo_kei")[1]).text().replace(/,/g, "") : $(page.detail.element.findP("qty_haigo_kei_user")[1]).val().replace(/,/g, "")
                        entity_header["biko"] = $(page.detail.element.findP("biko")[1]).val()
                        entity_header["no_seiho"] = page.header.element.findP("no_seiho_kaisha").val() + "-" + page.header.element.findP("no_seiho_shurui").val() + '-' + page.header.element.findP("no_seiho_nen").val() + "-" + page.header.element.findP("no_seiho_renban").val()
                        entity_header["cd_tanto_seizo"] = $(page.detail.element.findP("flg_seizo")[1]).prop("checked") ?App.ui.page.user.EmployeeCD : null
                        entity_header["cd_tanto_hinkan"] = $(page.detail.element.findP("flg_hinkan")[1]).prop("checked") ? App.ui.page.user.EmployeeCD : null
                        entity_header["dt_from"] = page.header.element.findP("dt_from").val().replace(/\//g, "-")
                        entity_header["dt_to"] = page.header.element.findP("dt_to").val().replace(/\//g, "-")
                        entity_header["kbn_vw"] = page.header.element.findP("rdoVWKbnKg").prop("checked") ? App.num.format(Number(App.settings.app.cd_tani.kg), "00") : App.num.format(Number(App.settings.app.cd_tani.l), "00")
                        entity_header["hijyu"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("hijyu").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("hijyu").val()
                        entity_header["flg_shorihin"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("flg_shorihin").prop("checked") ? true : false
                        entity_header["flg_hinkan"] = $(page.detail.element.findP("flg_hinkan")[1]).prop("checked") ? true : false
                        entity_header["flg_seizo"] = $(page.detail.element.findP("flg_seizo")[1]).prop("checked") ? true : false
                        entity_header["flg_sakujyo"] = false
                        entity_header["flg_mishiyo"] = page.header.element.findP("flg_mishiyo").prop("checked") ? true : false
                        entity_header["cd_koshin"] = App.ui.page.user.EmployeeCD
                        entity_header["kbn_shiagari"] = $(page.detail.element.findP("rdo_shiage")[1]).prop("checked") ? App.settings.app.kbn_shiagari.gokeishiagarinashi : App.settings.app.kbn_shiagari.gokeishiagariari
                        entity_header["nm_haigo_rm"] = null
                        entity_header["cd_haigo_seiho"] = null
                        entity_header["flg_seiho_base"] = false
                        entity_header["nm_seiho"] = data_header["nm_seiho"]

                        //ma_haigo_recipe_hyoji
                        page.detail.element.find(".new").each(function (i, row) {
                            var tbody = $(row),
                                id = tbody.attr("data-key"),
                                entity_detail = page.detail.data.entry(id);
                            var qty = page.detail.getSaveQtyHaigo(entity_detail.cd_mark, entity_detail.qty_haigo, 0);
                            var isExistMark = page.checkExistsMark(entity_detail.cd_mark);
                            var item = {
                                cd_haigo: page.header.element.findP("cd_haigo").val(),
                                no_han: page.header.element.findP("no_han").val(),
                                qty_haigo_h: page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val(),
                                no_kotei: entity_detail["no_kotei"],
                                no_tonyu: entity_detail["no_tonyu"],
                                cd_hin: tbody.findP("cd_hin").val(),
                                kbn_hin: entity_detail["kbn_hin"],
                                nm_hin: entity_detail["nm_hin"],
                                cd_mark: App.num.format(Number(entity_detail["cd_mark"]), "00"),
                                qty: qty,
                                qty_haigo: qty,
                                qty_nisugata: isExistMark ? entity_detail["qty_nisugata"] : null,
                                su_nisugata: 0,
                                qty_kowake: 0,
                                su_kowake: 0,
                                cd_futai: "",
                                qty_futai: null,
                                hijyu: !isExistMark ? 0 : (App.isUndefOrNull(entity_detail["gosa"]) ? 0 : entity_detail["gosa"]),
                                budomari: !isExistMark ? 100 : (App.isUndefOrNull(entity_detail["budomari"]) ? 100 : entity_detail["budomari"]),
                                flg_sakujyo: false,
                                flg_mishiyo: false,
                                cd_koshin: App.ui.page.user.EmployeeCD,
                                //kbn_jyotai: App.isUndefOrNull(entity_detail["kbn_jyotai"]) ? 0 : entity_detail["kbn_jyotai"],
                                kbn_jyotai: entity_detail["kbn_jyotai"],
                                kbn_shitei: entity_detail["kbn_shitei"],
                                kbn_bunkatsu: App.isUndefOrNull(entity_detail["kbn_bunkatsu"]) ? 0 : entity_detail["kbn_bunkatsu"]
                            }
                            if (item.qty_haigo_h) {
                                item.qty_haigo_h = item.qty_haigo_h.replace(/,/g, '');
                            }
                            page.ma_haigo_recipe_hyoji.data.add(item);
                        })

                        
                        //ma_seizo_line_hyoji
                        if (page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.shinki || page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.copy) {
                            page.ma_seizo_line_hyoji.data = App.ui.page.dataSet();
                            for (var i = 0; i < page.dialogs.tsuikaJohoNyuryokuDialog.element.find(".new").length; i++) {
                                var row = $(page.dialogs.tsuikaJohoNyuryokuDialog.element.find(".new")[i]),
                                ma_seizo_line = {
                                    cd_haigo: page.header.element.findP("cd_haigo").val(),
                                    no_yusen: row.findP("no_yusen").text(),
                                    cd_line: row.findP("cd_line").val(),
                                    flg_sakujyo: false,
                                    flg_mishiyo: false,
                                    cd_koshin: App.ui.page.user.EmployeeCD
                                };
                                page.ma_seizo_line_hyoji.data.add(ma_seizo_line);
                            }

                        }
                        else {
                            var jsonSeizoLine = {};
                            page.ma_seizo_line_hyoji.data = $.extend({}, page.ma_seizo_line_hyoji_temp.data);
                            for (var i = 0; i < page.dialogs.tsuikaJohoNyuryokuDialog.element.find(".new").length; i++) {
                                var row = $(page.dialogs.tsuikaJohoNyuryokuDialog.element.find(".new")[i]);
                                jsonSeizoLine[row.findP("cd_line").val()] = row.findP("no_yusen").text();
                            }
                            page.ma_seizo_line_hyoji.data.findAll(function (item, entity) {
                                if (App.isUndefOrNull(jsonSeizoLine[item.cd_line])) {
                                    item["flg_sakujyo"] = true;
                                    item["flg_mishiyo"] = true;
                                    item["cd_koshin"] = App.ui.page.user.EmployeeCD;
                                    page.ma_seizo_line_hyoji.data.update(item);
                                }
                                else {
                                    if (item.no_yusen != jsonSeizoLine[item.cd_line]) {
                                        item["no_yusen"] = jsonSeizoLine[item.cd_line];
                                        item["cd_koshin"] = App.ui.page.user.EmployeeCD;
                                        page.ma_seizo_line_hyoji.data.update(item);
                                    }
                                    delete jsonSeizoLine[item.cd_line];
                                }
                            });
                            $.each(jsonSeizoLine, function (key, value) {
                                var ma_seizo_line = {
                                    cd_haigo: page.header.element.findP("cd_haigo").val(),
                                    no_yusen: value,
                                    cd_line: key,
                                    flg_sakujyo: false,
                                    flg_mishiyo: false,
                                    cd_koshin: App.ui.page.user.EmployeeCD
                                };
                                page.ma_seizo_line_hyoji.data.add(ma_seizo_line);
                            });
                        }

                        //ma_seihin
                        page.ma_seihin.data = $.extend({}, page.ma_seihin_temp.data);
                        var jsonSeihin = {};
                        for (var i = 0; i < page.dialogs.seihinJohoNyuryokuDialog.element.find(".new").length; i++) {
                            var row = $(page.dialogs.seihinJohoNyuryokuDialog.element.find(".new")[i]);
                            jsonSeihin[row.findP("cd_hin").val()] = (i + 1).toString();
                        }
                        page.ma_seihin.data.findAll(function (item, entity) {
                            if (App.isUndefOrNull(jsonSeihin[item.cd_hin])) {
                                item["no_yusen"] = null;
                                item["cd_haigo"] = null;
                                item["cd_koshin"] = App.ui.page.user.EmployeeCD;
                                page.ma_seihin.data.update(item);
                            }
                            else {
                                if (item.no_yusen_hyoji != jsonSeihin[item.cd_hin]) {
                                    item["no_yusen"] = jsonSeihin[item.cd_hin];
                                    item["cd_haigo"] = page.header.element.findP("cd_haigo").val();
                                    item["cd_koshin"] = App.ui.page.user.EmployeeCD;
                                    page.ma_seihin.data.update(item);
                                }
                                delete jsonSeihin[item.cd_hin];
                            }

                        });
                        $.each(jsonSeihin, function (key, value) {
                            var ma_seihin_seiho = {
                                cd_hin: key,
                                no_yusen: value,
                                cd_haigo: page.header.element.findP("cd_haigo").val(),
                                cd_koshin: App.ui.page.user.EmployeeCD
                            };
                            page.ma_seihin.data.attach(ma_seihin_seiho);
                            page.ma_seihin.data.update(ma_seihin_seiho);
                        });
                        
                    }
                    //M_kirikae = 2
                    else {
                        
                        //ma_haigo_mei
                        page.ma_haigo_mei.data = App.ui.page.dataSet();
                        var entity_header = {},
                            data_header = page.header.element.form().data();
                        page.ma_haigo_mei.data.add(entity_header);
                        entity_header["cd_haigo"] = page.header.element.findP("cd_haigo").val()
                        entity_header["nm_haigo"] = data_header["nm_haigo"]
                        entity_header["nm_haigo_r"] = App.isUndefOrNull(data_header["nm_haigo_r"]) ? "" : data_header["nm_haigo_r"]
                        entity_header["kbn_hin"] = data_header["kbn_hin"]
                        entity_header["cd_bunrui"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").val() == "" ? "" : App.num.format(Number(page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").val()), "00")
                        entity_header["budomari"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("budomari").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("budomari").val()
                        entity_header["qty_kihon"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val()
                        if (entity_header["qty_kihon"]) {
                            entity_header["qty_kihon"] = entity_header["qty_kihon"].replace(/,/g, '');
                        }
                        entity_header["ritsu_kihon"] = data_header["ritsu_kihon"]
                        //entity_header["cd_setsubi"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi").val() == "" ? null : App.num.format(Number(page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi").val()), "00")
                        var cd_setsubi = page.dialogs.tsuikaJohoNyuryokuDialog.values.defaultSetsubi;
                        if (cd_setsubi === undefined) {
                            cd_setsubi = "00";
                        }
                        if (page.dialogs.tsuikaJohoNyuryokuDialog.values.isChangeSetsubi) {
                            cd_setsubi = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi").val();
                        }
                        // Case Insert set cd_setsubi = null when empty
                        if (cd_setsubi === "") {
                            cd_setsubi = null;
                        }
                        entity_header["cd_setsubi"] = cd_setsubi;
                        entity_header["flg_gasan"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("flg_gasan").prop("checked") ? true : false
                        entity_header["qty_max"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_max").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_max").val()
                        entity_header["no_han"] = data_header["no_han"];
                        entity_header["qty_haigo_h"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val()
                        if (entity_header["qty_haigo_h"]) {
                            entity_header["qty_haigo_h"] = entity_header["qty_haigo_h"].replace(/,/g, '');
                        }
                        entity_header["qty_haigo_kei"] = $(page.detail.element.findP("rdo_shiage")[1]).prop("checked") ? $(page.detail.element.findP("qty_haigo_kei")[1]).text().replace(/,/g, "") : $(page.detail.element.findP("qty_haigo_kei_user")[1]).val().replace(/,/g, "")
                        entity_header["biko"] = $(page.detail.element.findP("biko")[1]).val()
                        entity_header["no_seiho"] = page.header.element.findP("no_seiho_kaisha").val() + "-" + page.header.element.findP("no_seiho_shurui").val() + '-' + page.header.element.findP("no_seiho_nen").val() + "-" + page.header.element.findP("no_seiho_renban").val()
                        entity_header["cd_tanto_seizo"] = $(page.detail.element.findP("flg_seizo")[1]).prop("checked") ? App.ui.page.user.EmployeeCD : null
                        entity_header["cd_tanto_hinkan"] = $(page.detail.element.findP("flg_hinkan")[1]).prop("checked") ? App.ui.page.user.EmployeeCD : null
                        entity_header["dt_from"] = page.header.element.findP("dt_from").val().replace(/\//g, "-")
                        entity_header["dt_to"] = page.header.element.findP("dt_to").val().replace(/\//g, "-")
                        entity_header["kbn_vw"] = page.header.element.findP("rdoVWKbnKg").prop("checked") ? App.num.format(Number(App.settings.app.cd_tani.kg), "00") : App.num.format(Number(App.settings.app.cd_tani.l), "00")
                        entity_header["hijyu"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("hijyu").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("hijyu").val()
                        entity_header["flg_shorihin"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("flg_shorihin").prop("checked") ? true : false
                        entity_header["flg_hinkan"] = $(page.detail.element.findP("flg_hinkan")[1]).prop("checked") ? true : false
                        entity_header["flg_seizo"] = $(page.detail.element.findP("flg_seizo")[1]).prop("checked") ? true : false
                        entity_header["flg_sakujyo"] = false
                        entity_header["flg_mishiyo"] = page.header.element.findP("flg_mishiyo").prop("checked") ? true : false
                        entity_header["cd_koshin"] = App.ui.page.user.EmployeeCD
                        entity_header["kbn_shiagari"] = $(page.detail.element.findP("rdo_shiage")[1]).prop("checked") ? App.settings.app.kbn_shiagari.gokeishiagarinashi : App.settings.app.kbn_shiagari.gokeishiagariari
                        entity_header["nm_haigo_rm"] = null
                        entity_header["cd_haigo_seiho"] = null
                        entity_header["flg_seiho_base"] = false
                        entity_header["nm_seiho"] = data_header["nm_seiho"]
                        entity_header["kbn_koshin"] = App.settings.app.kbn_koshin.haigo_shinki
                        entity_header["kin_keihi"] = null
                        //ma_haigo_recipe
                        page.ma_haigo_recipe.data = App.ui.page.dataSet();
                        page.detail.element.find(".new").each(function (i, row) {
                            var tbody = $(row),
                                id = tbody.attr("data-key"),
                                entity_detail = page.detail.data.entry(id),
                                mark = page.getObjMark(entity_detail.cd_mark, null);

                            var qty = page.detail.getSaveQtyHaigo(entity_detail.cd_mark, entity_detail.qty_haigo, 0);
                            var isExistMark = page.checkExistsMark(entity_detail.cd_mark);

                            var item = {
                                cd_haigo: page.header.element.findP("cd_haigo").val(),
                                no_han: page.header.element.findP("no_han").val(),
                                qty_haigo_h: page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val(),
                                no_kotei: entity_detail["no_kotei"],
                                no_tonyu: entity_detail["no_tonyu"],
                                cd_hin: tbody.findP("cd_hin").val(),
                                kbn_hin: entity_detail["kbn_hin"],
                                nm_hin: entity_detail["nm_hin"],
                                cd_mark: App.num.format(Number(entity_detail["cd_mark"]), "00"),
                                qty: qty,
                                qty_haigo: qty,
                                // Bug #16903　flg_mishiyo更新不具合対応  (2020-05-05) ST
                                qty_nisugata: (entity_detail.isChanged && !isExistMark) ? null : entity_detail["qty_nisugata"],
                                // Bug #16903　flg_mishiyo更新不具合対応  (2020-05-05) ED
                                su_nisugata: 0,
                                qty_kowake: 0,
                                su_kowake: 0,
                                cd_futai: "",
                                qty_futai: null,
                                hijyu: !isExistMark ? 0 : (App.isUndefOrNull(entity_detail["gosa"]) ? 0 : entity_detail["gosa"]),
                                budomari: !isExistMark ? 100 : (App.isUndefOrNull(entity_detail["budomari"]) ? 100 : entity_detail["budomari"]),
                                flg_sakujyo: false,
                                flg_mishiyo: false,
                                cd_koshin: App.ui.page.user.EmployeeCD,
                                kbn_jyotai: entity_detail["kbn_jyotai"],
                                kbn_shitei: entity_detail["kbn_shitei"],
                                kbn_bunkatsu: App.isUndefOrNull(entity_detail["kbn_bunkatsu"]) ? 0 : entity_detail["kbn_bunkatsu"]
                            }
                            if (item.qty_haigo_h) {
                                item.qty_haigo_h = item.qty_haigo_h.replace(/,/g, '');
                            }
                            page.ma_haigo_recipe.data.add(item);
                        })

                        //ma_seizo_line
                        if (page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.shinki || page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.copy) {
                            page.ma_seizo_line.data = App.ui.page.dataSet();
                            for (var i = 0; i < page.dialogs.tsuikaJohoNyuryokuDialog.element.find(".new").length; i++) {
                                var row = $(page.dialogs.tsuikaJohoNyuryokuDialog.element.find(".new")[i]),
                                ma_seizo_line = {
                                    cd_haigo: page.header.element.findP("cd_haigo").val(),
                                    no_yusen: row.findP("no_yusen").text(),
                                    cd_line: row.findP("cd_line").val(),
                                    flg_sakujyo: false,
                                    flg_mishiyo: false,
                                    cd_koshin: App.ui.page.user.EmployeeCD,
                                    qty_noryoku: null
                                };
                                page.ma_seizo_line.data.add(ma_seizo_line);
                            }
                        }
                        else {
                            var jsonSeizoLine = {};
                            page.ma_seizo_line.data = $.extend({}, page.ma_seizo_line_temp.data);
                            for (var i = 0; i < page.dialogs.tsuikaJohoNyuryokuDialog.element.find(".new").length; i++) {
                                var row = $(page.dialogs.tsuikaJohoNyuryokuDialog.element.find(".new")[i]);
                                jsonSeizoLine[row.findP("cd_line").val()] = row.findP("no_yusen").text();
                            }
                            page.ma_seizo_line.data.findAll(function (item, entity) {
                                if (App.isUndefOrNull(jsonSeizoLine[item.cd_line])) {
                                    item["flg_sakujyo"] = true;
                                    item["flg_mishiyo"] = true;
                                    item["cd_koshin"] = App.ui.page.user.EmployeeCD;
                                    page.ma_seizo_line.data.update(item);
                                }
                                else {
                                    if (item.no_yusen != jsonSeizoLine[item.cd_line]) {
                                        item["no_yusen"] = jsonSeizoLine[item.cd_line];
                                        item["cd_koshin"] = App.ui.page.user.EmployeeCD;
                                        page.ma_seizo_line.data.update(item);
                                    }
                                    delete jsonSeizoLine[item.cd_line];
                                }
                            });
                            $.each(jsonSeizoLine, function (key, value) {
                                var ma_seizo_line = {
                                    cd_haigo: page.header.element.findP("cd_haigo").val(),
                                    no_yusen: value,
                                    cd_line: key,
                                    flg_sakujyo: false,
                                    flg_mishiyo: false,
                                    cd_koshin: App.ui.page.user.EmployeeCD,
                                    qty_noryoku: null
                                };
                                page.ma_seizo_line.data.add(ma_seizo_line);
                            });
                        }

                        //ma_seihin
                        page.ma_seihin.data = $.extend({}, page.ma_seihin_temp.data);
                        var jsonSeihin = {};
                        for (var i = 0; i < page.dialogs.seihinJohoNyuryokuDialog.element.find(".new").length; i++) {
                            var row = $(page.dialogs.seihinJohoNyuryokuDialog.element.find(".new")[i]);
                            jsonSeihin[row.findP("cd_hin").val()] = (i + 1).toString();
                        }
                        page.ma_seihin.data.findAll(function (item, entity) {
                            if (App.isUndefOrNull(jsonSeihin[item.cd_hin])) {
                                item["no_yusen"] = null;
                                item["cd_haigo"] = null;
                                item["cd_koshin"] = App.ui.page.user.EmployeeCD;
                                page.ma_seihin.data.update(item);
                            }
                            else {
                                if (item.no_yusen != jsonSeihin[item.cd_hin]) {
                                    item["no_yusen"] = jsonSeihin[item.cd_hin];
                                    item["cd_haigo"] = page.header.element.findP("cd_haigo").val();
                                    item["cd_koshin"] = App.ui.page.user.EmployeeCD;
                                    page.ma_seihin.data.update(item);
                                }
                                delete jsonSeihin[item.cd_hin];
                            }

                        });
                        $.each(jsonSeihin, function (key, value) {
                            var ma_seihin_seiho = {
                                cd_hin: key,
                                no_yusen: value,
                                cd_haigo: page.header.element.findP("cd_haigo").val(),
                                cd_koshin: App.ui.page.user.EmployeeCD
                            };
                            page.ma_seihin.data.attach(ma_seihin_seiho);
                            page.ma_seihin.data.update(ma_seihin_seiho);
                        });

                    }
                }//end mode toroku
                //start mode shosai
                else if (page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.shosai) {
                    //start M_kirikae = 1
                    if (page.values.M_kirikae == App.settings.app.m_kirikae.hyoji) {
                        //ma_haigo_mei_hyoji
                        page.ma_haigo_mei_hyoji.data = $.extend({}, page.header.data);
                        var id = page.header.element.attr("data-key"),
                            entity_header = page.ma_haigo_mei_hyoji.data.entry(id),
                            data_header = page.header.element.form().data();
                        page.ma_haigo_mei_hyoji.data.update(entity_header);
                        var state = page.ma_haigo_mei_hyoji.data.entries[id].state;
                        entity_header["nm_haigo"] = data_header["nm_haigo"]
                        entity_header["nm_haigo_r"] = App.isUndefOrNull(data_header["nm_haigo_r"]) ? "" : data_header["nm_haigo_r"]
                        entity_header["kbn_hin"] = data_header["kbn_hin"]
                        entity_header["cd_bunrui"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").val() == "" ? "" : App.num.format(Number(page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").val()), "00")
                        entity_header["budomari"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("budomari").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("budomari").val()
                        entity_header["qty_kihon"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val()
                        if (entity_header["qty_kihon"]) {
                            entity_header["qty_kihon"] = entity_header["qty_kihon"].replace(/,/g, '');
                        }
                        entity_header["ritsu_kihon"] = data_header["ritsu_kihon"]
                        //entity_header["cd_setsubi"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi").val() == "" ? null : App.num.format(Number(page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi").val()), "00")
                        var cd_setsubi = entity_header["cd_setsubi"];
                        if (page.dialogs.tsuikaJohoNyuryokuDialog.values.isChangeSetsubi || state == App.ui.page.dataSet.status.Added) {
                            cd_setsubi = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi").val();
                        }
                        if (cd_setsubi === "") {
                            // Case Insert default set cd_setsubi = 0
                            if (state == App.ui.page.dataSet.status.Added && !page.dialogs.tsuikaJohoNyuryokuDialog.values.isChangeSetsubi) {
                                cd_setsubi = 0;
                            } else {
                                // Case Edit set cd_setsubi = null
                                cd_setsubi = null;
                            }
                        }
                        entity_header["cd_setsubi"] = cd_setsubi;
                        entity_header["flg_gasan"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("flg_gasan").prop("checked") ? true : false
                        entity_header["qty_max"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_max").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_max").val()
                        entity_header["no_han"] = data_header["no_han"];
                        entity_header["qty_haigo_h"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val()
                        if (entity_header["qty_haigo_h"]) {
                            entity_header["qty_haigo_h"] = entity_header["qty_haigo_h"].replace(/,/g, '');
                        }
                        entity_header["qty_haigo_kei"] = $(page.detail.element.findP("rdo_shiage")[1]).prop("checked") ? $(page.detail.element.findP("qty_haigo_kei")[1]).text().replace(/,/g, "") : $(page.detail.element.findP("qty_haigo_kei_user")[1]).val().replace(/,/g, "")
                        entity_header["biko"] = $(page.detail.element.findP("biko")[1]).val()
                        entity_header["no_seiho"] = page.header.element.findP("no_seiho_kaisha").val() + "-" + page.header.element.findP("no_seiho_shurui").val() + '-' + page.header.element.findP("no_seiho_nen").val() + "-" + page.header.element.findP("no_seiho_renban").val()
                        entity_header["cd_tanto_seizo"] = $(page.detail.element.findP("flg_seizo")[1]).prop("checked") ? App.ui.page.user.EmployeeCD : entity_header["cd_tanto_seizo"]
                        entity_header["cd_tanto_hinkan"] = $(page.detail.element.findP("flg_hinkan")[1]).prop("checked") ? App.ui.page.user.EmployeeCD : entity_header["cd_tanto_hinkan"]
                        entity_header["dt_from"] = page.header.element.findP("dt_from").val().replace(/\//g, "-")
                        entity_header["dt_to"] = page.header.element.findP("dt_to").val().replace(/\//g, "-")
                        entity_header["kbn_vw"] = page.header.element.findP("rdoVWKbnKg").prop("checked") ? App.num.format(Number(App.settings.app.cd_tani.kg), "00") : App.num.format(Number(App.settings.app.cd_tani.l), "00")
                        entity_header["hijyu"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("hijyu").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("hijyu").val()
                        entity_header["flg_shorihin"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("flg_shorihin").prop("checked") ? true : false
                        entity_header["flg_hinkan"] = $(page.detail.element.findP("flg_hinkan")[1]).prop("checked") ? true : false
                        entity_header["flg_seizo"] = $(page.detail.element.findP("flg_seizo")[1]).prop("checked") ? true : false
                        entity_header["flg_sakujyo"] = false
                        entity_header["flg_mishiyo"] = page.header.element.findP("flg_mishiyo").prop("checked") ? true : false
                        entity_header["cd_koshin"] = App.ui.page.user.EmployeeCD
                        entity_header["kbn_shiagari"] = $(page.detail.element.findP("rdo_shiage")[1]).prop("checked") ? App.settings.app.kbn_shiagari.gokeishiagarinashi : App.settings.app.kbn_shiagari.gokeishiagariari
                        entity_header["nm_seiho"] = data_header["nm_seiho"]

                        //ma_haigo_recipe_hyoji
                        page.detail.element.find(".new").each(function (i, row) {
                            page.ma_haigo_recipe_hyoji.data = $.extend({}, page.detail.data);
                            var tbody = $(row),
                                id = tbody.attr("data-key"),
                                entity_detail = page.ma_haigo_recipe_hyoji.data.entry(id);

                            var qty = page.detail.getSaveQtyHaigo(entity_detail.cd_mark, entity_detail.qty_haigo, 0);
                            var isExistMark = page.checkExistsMark(entity_detail.cd_mark);

                            if (page.ma_haigo_recipe_hyoji.data.entries[id].state == EntityState.Modified) {
                                entity_detail.cd_haigo = page.header.element.findP("cd_haigo").val();
                                entity_detail.no_han = page.header.element.findP("no_han").val();
                                entity_detail.qty_haigo_h = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val();
                                if (entity_detail.qty_haigo_h) {
                                    entity_detail.qty_haigo_h = entity_detail.qty_haigo_h.replace(/,/g, '');
                                }
                                entity_detail.no_kotei = entity_detail["no_kotei"];
                                entity_detail.no_tonyu = entity_detail["no_tonyu"];
                                entity_detail.cd_hin = tbody.findP("cd_hin").val();
                                entity_detail.kbn_hin = entity_detail["kbn_hin"];
                                entity_detail.nm_hin = entity_detail["nm_hin"];
                                entity_detail.cd_mark = App.num.format(Number(entity_detail["cd_mark"]), "00");
                                entity_detail.qty = qty;
                                entity_detail.qty_haigo = qty;
                                entity_detail.qty_nisugata = !isExistMark ? null : entity_detail["qty_nisugata"];
                                entity_detail.su_nisugata = 0;
                                entity_detail.qty_kowake = 0;
                                entity_detail.su_kowake = 0;
                                entity_detail.cd_futai = 0;
                                entity_detail.qty_futai = null;
                                entity_detail.hijyu = !isExistMark ? 0 : (App.isUndefOrNull(entity_detail["gosa"]) ? 0 : entity_detail["gosa"]);
                                entity_detail.budomari = !isExistMark ? 100 : (App.isUndefOrNull(entity_detail["budomari"]) ? 100 : entity_detail["budomari"]);
                                entity_detail.cd_koshin = App.ui.page.user.EmployeeCD;
                                //entity_detail.kbn_jyotai = App.isUndefOrNull(entity_detail["kbn_jyotai"]) ? 0 : entity_detail["kbn_jyotai"];
                                entity_detail.kbn_shitei = entity_detail["kbn_shitei"];
                                entity_detail.kbn_bunkatsu = App.isUndefOrNull(entity_detail["kbn_bunkatsu"]) ? 0 : entity_detail["kbn_bunkatsu"];
                            }
                            if (page.ma_haigo_recipe_hyoji.data.entries[id].state == EntityState.Added)  {
                                entity_detail.cd_haigo = page.header.element.findP("cd_haigo").val();
                                entity_detail.no_han = page.header.element.findP("no_han").val();
                                entity_detail.qty_haigo_h = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val();
                                if (entity_detail.qty_haigo_h) {
                                    entity_detail.qty_haigo_h = entity_detail.qty_haigo_h.replace(/,/g, '');
                                }
                                entity_detail.no_kotei = entity_detail["no_kotei"];
                                entity_detail.no_tonyu = entity_detail["no_tonyu"];
                                entity_detail.cd_hin = tbody.findP("cd_hin").val();
                                entity_detail.kbn_hin = entity_detail["kbn_hin"];
                                entity_detail.nm_hin = entity_detail["nm_hin"];
                                entity_detail.cd_mark = App.num.format(Number(entity_detail["cd_mark"]), "00");
                                entity_detail.qty = qty;
                                entity_detail.qty_haigo = qty;
                                entity_detail.qty_nisugata = !isExistMark ? null : entity_detail["qty_nisugata"];
                                entity_detail.su_nisugata = 0;
                                entity_detail.qty_kowake = 0;
                                entity_detail.su_kowake = 0;
                                entity_detail.cd_futai = "";
                                entity_detail.qty_futai = null,
                                entity_detail.hijyu = !isExistMark ? 0 : (App.isUndefOrNull(entity_detail["gosa"]) ? 0 : entity_detail["gosa"]);
                                entity_detail.budomari = !isExistMark ? 100 : (App.isUndefOrNull(entity_detail["budomari"]) ? 100 : entity_detail["budomari"]);
                                entity_detail.flg_sakujyo = false;
                                entity_detail.flg_mishiyo = false;
                                entity_detail.cd_koshin = App.ui.page.user.EmployeeCD;
                                //entity_detail.kbn_jyotai = App.isUndefOrNull(entity_detail["kbn_jyotai"]) ? 0 : entity_detail["kbn_jyotai"];
                                var kbn_jyotai = 1;
                                entity_detail.kbn_jyotai = kbn_jyotai;
                                entity_detail.kbn_shitei = entity_detail["kbn_shitei"];
                                entity_detail.kbn_bunkatsu = App.isUndefOrNull(entity_detail["kbn_bunkatsu"]) ? 0 : entity_detail["kbn_bunkatsu"];
                            }
                           
                        })

                        //ma_seizo_line_hyoji
                        var jsonSeizoLine = {};
                        page.ma_seizo_line_hyoji.data = $.extend({}, page.ma_seizo_line_hyoji_temp.data);
                        for (var i = 0; i < page.dialogs.tsuikaJohoNyuryokuDialog.element.find(".new").length; i++) {
                            var row = $(page.dialogs.tsuikaJohoNyuryokuDialog.element.find(".new")[i]);
                            jsonSeizoLine[row.findP("cd_line").val()] = row.findP("no_yusen").text();
                        }
                        page.ma_seizo_line_hyoji.data.findAll(function (item, entity) {
                            if (App.isUndefOrNull(jsonSeizoLine[item.cd_line])) {
                                item["flg_sakujyo"] = true;
                                item["flg_mishiyo"] = true;
                                item["cd_koshin"] = App.ui.page.user.EmployeeCD;
                                page.ma_seizo_line_hyoji.data.update(item);
                            }
                            else {
                                if (item.no_yusen != jsonSeizoLine[item.cd_line]) {
                                    item["no_yusen"] = jsonSeizoLine[item.cd_line];
                                    item["cd_koshin"] = App.ui.page.user.EmployeeCD;
                                    page.ma_seizo_line_hyoji.data.update(item);
                                }
                                delete jsonSeizoLine[item.cd_line];
                            }
                        });
                        $.each(jsonSeizoLine, function (key, value) {
                            var ma_seizo_line = {
                                cd_haigo: page.header.element.findP("cd_haigo").val(),
                                no_yusen: value,
                                cd_line: key,
                                flg_sakujyo: false,
                                flg_mishiyo: false,
                                cd_koshin: App.ui.page.user.EmployeeCD
                            };
                            page.ma_seizo_line_hyoji.data.add(ma_seizo_line);
                        });

                        //ma_seihin
                        page.ma_seihin.data = $.extend({}, page.ma_seihin_temp.data);
                        var jsonSeihin = {};
                        for (var i = 0; i < page.dialogs.seihinJohoNyuryokuDialog.element.find(".new").length; i++) {
                            var row = $(page.dialogs.seihinJohoNyuryokuDialog.element.find(".new")[i]);
                            jsonSeihin[row.findP("cd_hin").val()] = (i + 1).toString();
                        }
                        page.ma_seihin.data.findAll(function (item, entity) {
                            if (App.isUndefOrNull(jsonSeihin[item.cd_hin])) {
                                item["no_yusen"] = null;
                                item["cd_haigo"] = null;
                                item["cd_koshin"] = App.ui.page.user.EmployeeCD;
                                page.ma_seihin.data.update(item);
                            }
                            else {
                                if (item.no_yusen_hyoji != jsonSeihin[item.cd_hin]) {
                                    item["no_yusen"] = jsonSeihin[item.cd_hin];
                                    item["cd_haigo"] = page.header.element.findP("cd_haigo").val();
                                    item["cd_koshin"] = App.ui.page.user.EmployeeCD;
                                    page.ma_seihin.data.update(item);
                                }
                                delete jsonSeihin[item.cd_hin];
                            }

                        });
                        $.each(jsonSeihin, function (key, value) {
                            var ma_seihin_seiho = {
                                cd_hin: key,
                                no_yusen: value,
                                cd_haigo: page.header.element.findP("cd_haigo").val(),
                                cd_koshin: App.ui.page.user.EmployeeCD
                            };
                            page.ma_seihin.data.attach(ma_seihin_seiho);
                            page.ma_seihin.data.update(ma_seihin_seiho);
                        });

                    }//end M_kirikae = 1
                    //start M_kirikae = 2
                    else {
                        //ma_haigo_mei
                        page.ma_haigo_mei.data = $.extend({}, page.header.data);
                        var id = page.header.element.attr("data-key"),
                            entity_header = page.ma_haigo_mei.data.entry(id),
                            data_header = page.header.element.form().data();
                        page.ma_haigo_mei.data.update(entity_header);
                        var state = page.ma_haigo_mei.data.entries[id].state;
                        entity_header["nm_haigo"] = data_header["nm_haigo"]
                        entity_header["nm_haigo_r"] = App.isUndefOrNull(data_header["nm_haigo_r"]) ? "" : data_header["nm_haigo_r"]
                        entity_header["kbn_hin"] = data_header["kbn_hin"]
                        entity_header["cd_bunrui"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").val() == "" ? "" : App.num.format(Number(page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").val()), "00")
                        entity_header["budomari"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("budomari").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("budomari").val()
                        entity_header["qty_kihon"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val()
                        if (entity_header["qty_kihon"]) {
                            entity_header["qty_kihon"] = entity_header["qty_kihon"].replace(/,/g, '');
                        }
                        entity_header["ritsu_kihon"] = data_header["ritsu_kihon"]
                        //entity_header["cd_setsubi"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi").val() == "" ? null : App.num.format(Number(page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi").val()), "00")
                        var cd_setsubi = entity_header["cd_setsubi"];
                        if (page.dialogs.tsuikaJohoNyuryokuDialog.values.isChangeSetsubi || state == App.ui.page.dataSet.status.Added) {
                            cd_setsubi = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi").val();
                        }
                        if (cd_setsubi === "") {
                            // Case Insert default set cd_setsubi = 0
                            if (state == App.ui.page.dataSet.status.Added && !page.dialogs.tsuikaJohoNyuryokuDialog.values.isChangeSetsubi) {
                                cd_setsubi = 0;
                            } else {
                                // Case Edit set cd_setsubi = null
                                cd_setsubi = null;
                            }
                        }
                        entity_header["cd_setsubi"] = cd_setsubi;
                        entity_header["flg_gasan"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("flg_gasan").prop("checked") ? true : false
                        entity_header["qty_max"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_max").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_max").val()
                        entity_header["no_han"] = data_header["no_han"];
                        entity_header["qty_haigo_h"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val()
                        if (entity_header["qty_haigo_h"]) {
                            entity_header["qty_haigo_h"] = entity_header["qty_haigo_h"].replace(/,/g, '');
                        }
                        entity_header["qty_haigo_kei"] = $(page.detail.element.findP("rdo_shiage")[1]).prop("checked") ? $(page.detail.element.findP("qty_haigo_kei")[1]).text().replace(/,/g, "") : $(page.detail.element.findP("qty_haigo_kei_user")[1]).val().replace(/,/g, "")
                        entity_header["biko"] = $(page.detail.element.findP("biko")[1]).val()
                        entity_header["no_seiho"] = page.header.element.findP("no_seiho_kaisha").val() + "-" + page.header.element.findP("no_seiho_shurui").val() + '-' + page.header.element.findP("no_seiho_nen").val() + "-" + page.header.element.findP("no_seiho_renban").val()
                        entity_header["cd_tanto_seizo"] = $(page.detail.element.findP("flg_seizo")[1]).prop("checked") ? App.ui.page.user.EmployeeCD : entity_header["cd_tanto_seizo"]
                        entity_header["cd_tanto_hinkan"] = $(page.detail.element.findP("flg_hinkan")[1]).prop("checked") ? App.ui.page.user.EmployeeCD : entity_header["cd_tanto_hinkan"]
                        entity_header["dt_from"] = page.header.element.findP("dt_from").val().replace(/\//g, "-")
                        entity_header["dt_to"] = page.header.element.findP("dt_to").val().replace(/\//g, "-")
                        entity_header["kbn_vw"] = page.header.element.findP("rdoVWKbnKg").prop("checked") ? App.num.format(Number(App.settings.app.cd_tani.kg), "00") : App.num.format(Number(App.settings.app.cd_tani.l), "00")
                        entity_header["hijyu"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("hijyu").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("hijyu").val()
                        entity_header["flg_shorihin"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("flg_shorihin").prop("checked") ? true : false
                        entity_header["flg_hinkan"] = $(page.detail.element.findP("flg_hinkan")[1]).prop("checked") ? true : false
                        entity_header["flg_seizo"] = $(page.detail.element.findP("flg_seizo")[1]).prop("checked") ? true : false
                        entity_header["flg_sakujyo"] = false
                        entity_header["flg_mishiyo"] = page.header.element.findP("flg_mishiyo").prop("checked") ? true : false
                        entity_header["cd_koshin"] = App.ui.page.user.EmployeeCD
                        entity_header["kbn_shiagari"] = $(page.detail.element.findP("rdo_shiage")[1]).prop("checked") ? App.settings.app.kbn_shiagari.gokeishiagarinashi : App.settings.app.kbn_shiagari.gokeishiagariari
                        entity_header["nm_seiho"] = data_header["nm_seiho"]

                        //ma_haigo_recipe
                        page.detail.element.find(".new").each(function (i, row) {
                            page.ma_haigo_recipe.data = $.extend({}, page.detail.data);
                            var tbody = $(row),
                                id = tbody.attr("data-key"),
                                entity_detail = page.ma_haigo_recipe.data.entry(id);

                            var qty = page.detail.getSaveQtyHaigo(entity_detail.cd_mark, entity_detail.qty_haigo, 0);
                            var isExistMark = page.checkExistsMark(entity_detail.cd_mark);

                            if (page.ma_haigo_recipe.data.entries[id].state == EntityState.Modified) {
                                entity_detail.cd_haigo = page.header.element.findP("cd_haigo").val();
                                entity_detail.no_han = page.header.element.findP("no_han").val();
                                entity_detail.qty_haigo_h = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val();
                                if (entity_detail.qty_haigo_h) {
                                    entity_detail.qty_haigo_h = entity_detail.qty_haigo_h.replace(/,/g, '');
                                }
                                entity_detail.no_kotei = entity_detail["no_kotei"];
                                entity_detail.no_tonyu = entity_detail["no_tonyu"];
                                entity_detail.cd_hin = tbody.findP("cd_hin").val();
                                entity_detail.kbn_hin = entity_detail["kbn_hin"];
                                entity_detail.nm_hin = entity_detail["nm_hin"];
                                entity_detail.cd_mark = App.num.format(Number(entity_detail["cd_mark"]), "00");
                                entity_detail.qty = qty;
                                entity_detail.qty_haigo = qty;
                                // Bug #16903　flg_mishiyo更新不具合対応  (2020-05-05) ST
                                entity_detail.qty_nisugata = (entity_detail.isChanged && !isExistMark) ? null : entity_detail["qty_nisugata"];
                                // Bug #16903　flg_mishiyo更新不具合対応  (2020-05-05) ED
                                entity_detail.su_nisugata = 0;
                                entity_detail.qty_kowake = 0;
                                entity_detail.su_kowake = 0;
                                entity_detail.cd_futai = 0;
                                entity_detail.qty_futai = null;
                                entity_detail.hijyu = !isExistMark ? null : (App.isUndefOrNull(entity_detail["gosa"]) ? 0 : entity_detail["gosa"]);
                                entity_detail.budomari = !isExistMark ? null : (App.isUndefOrNull(entity_detail["budomari"]) ? 100 : entity_detail["budomari"]);
                                entity_detail.cd_koshin = App.ui.page.user.EmployeeCD;
                                //entity_detail.kbn_jyotai = App.isUndefOrNull(entity_detail["kbn_jyotai"]) ? 0 : entity_detail["kbn_jyotai"];
                                entity_detail.kbn_shitei = entity_detail["kbn_shitei"];
                                entity_detail.kbn_bunkatsu = App.isUndefOrNull(entity_detail["kbn_bunkatsu"]) ? 0 : entity_detail["kbn_bunkatsu"];
                            }
                            if (page.ma_haigo_recipe.data.entries[id].state == EntityState.Added) {
                                entity_detail.cd_haigo = page.header.element.findP("cd_haigo").val();
                                entity_detail.no_han = page.header.element.findP("no_han").val();
                                entity_detail.qty_haigo_h = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val();
                                if (entity_detail.qty_haigo_h) {
                                    entity_detail.qty_haigo_h = entity_detail.qty_haigo_h.replace(/,/g, '');
                                }
                                entity_detail.no_kotei = entity_detail["no_kotei"];
                                entity_detail.no_tonyu = entity_detail["no_tonyu"];
                                entity_detail.cd_hin = tbody.findP("cd_hin").val();
                                entity_detail.kbn_hin = entity_detail["kbn_hin"];
                                entity_detail.nm_hin = entity_detail["nm_hin"];
                                entity_detail.cd_mark = App.num.format(Number(entity_detail["cd_mark"]), "00");
                                entity_detail.qty = (!App.isUndefOrNull[entity_detail.cd_mark] && !App.isUndefOrNull(page.values.ma_mark[entity_detail.cd_mark]) && page.values.ma_mark[entity_detail.cd_mark].cd_tani_shiyo == App.settings.app.taniShiyo.g && !App.isUndefOrNull(entity_detail.qty_haigo)) ? new BigNumber(entity_detail.qty_haigo).div(1000).toNumber() : (App.isUndefOrNull(entity_detail.qty_haigo) ? 0 : entity_detail.qty_haigo);
                                entity_detail.qty_haigo = (!App.isUndefOrNull[entity_detail.cd_mark] && !App.isUndefOrNull(page.values.ma_mark[entity_detail.cd_mark]) && page.values.ma_mark[entity_detail.cd_mark].cd_tani_shiyo == App.settings.app.taniShiyo.g && !App.isUndefOrNull(entity_detail.qty_haigo)) ? new BigNumber(entity_detail.qty_haigo).div(1000).toNumber() : (App.isUndefOrNull(entity_detail.qty_haigo) ? 0 : entity_detail.qty_haigo);
                                entity_detail.qty_nisugata = !isExistMark ? null : entity_detail["qty_nisugata"];
                                entity_detail.su_nisugata = 0;
                                entity_detail.qty_kowake = 0;
                                entity_detail.su_kowake = 0;
                                entity_detail.cd_futai = "";
                                entity_detail.qty_futai = null,
                                entity_detail.hijyu = !isExistMark ? null : (App.isUndefOrNull(entity_detail["gosa"]) ? 0 : entity_detail["gosa"]);
                                entity_detail.budomari = !isExistMark ? null : (App.isUndefOrNull(entity_detail["budomari"]) ? 100 : entity_detail["budomari"]);
                                entity_detail.flg_sakujyo = false;
                                entity_detail.flg_mishiyo = false;
                                entity_detail.cd_koshin = App.ui.page.user.EmployeeCD;
                                //entity_detail.kbn_jyotai = App.isUndefOrNull(entity_detail["kbn_jyotai"]) ? 0 : entity_detail["kbn_jyotai"];
                                var kbn_jyotai = 1
                                entity_detail.kbn_jyotai = kbn_jyotai;
                                entity_detail.kbn_shitei = entity_detail["kbn_shitei"];
                                entity_detail.kbn_bunkatsu = App.isUndefOrNull(entity_detail["kbn_bunkatsu"]) ? 0 : entity_detail["kbn_bunkatsu"];
                            }

                        })

                        //ma_seizo_line
                        var jsonSeizoLine = {};
                        page.ma_seizo_line.data = $.extend({}, page.ma_seizo_line_temp.data);
                        for (var i = 0; i < page.dialogs.tsuikaJohoNyuryokuDialog.element.find(".new").length; i++) {
                            var row = $(page.dialogs.tsuikaJohoNyuryokuDialog.element.find(".new")[i]);
                            jsonSeizoLine[row.findP("cd_line").val()] = row.findP("no_yusen").text();
                        }
                        page.ma_seizo_line.data.findAll(function (item, entity) {
                            if (App.isUndefOrNull(jsonSeizoLine[item.cd_line])) {
                                item["flg_sakujyo"] = true;
                                item["flg_mishiyo"] = true;
                                item["cd_koshin"] = App.ui.page.user.EmployeeCD;
                                page.ma_seizo_line.data.update(item);
                            }
                            else {
                                if (item.no_yusen != jsonSeizoLine[item.cd_line]) {
                                    item["no_yusen"] = jsonSeizoLine[item.cd_line];
                                    item["cd_koshin"] = App.ui.page.user.EmployeeCD;
                                    page.ma_seizo_line.data.update(item);
                                }
                                delete jsonSeizoLine[item.cd_line];
                            }
                        });
                        $.each(jsonSeizoLine, function (key, value) {
                            var ma_seizo_line = {
                                cd_haigo: page.header.element.findP("cd_haigo").val(),
                                no_yusen: value,
                                cd_line: key,
                                flg_sakujyo: false,
                                flg_mishiyo: false,
                                cd_koshin: App.ui.page.user.EmployeeCD,
                                qty_noryoku: null
                            };
                            page.ma_seizo_line.data.add(ma_seizo_line);
                        });

                        //ma_seihin
                        page.ma_seihin.data = $.extend({}, page.ma_seihin_temp.data);
                        var jsonSeihin = {};
                        for (var i = 0; i < page.dialogs.seihinJohoNyuryokuDialog.element.find(".new").length; i++) {
                            var row = $(page.dialogs.seihinJohoNyuryokuDialog.element.find(".new")[i]);
                            jsonSeihin[row.findP("cd_hin").val()] = (i + 1).toString();
                        }
                        page.ma_seihin.data.findAll(function (item, entity) {
                            if (App.isUndefOrNull(jsonSeihin[item.cd_hin])) {
                                item["no_yusen"] = null;
                                item["cd_haigo"] = null;
                                item["cd_koshin"] = App.ui.page.user.EmployeeCD;
                                page.ma_seihin.data.update(item);
                            }
                            else {
                                if (item.no_yusen != jsonSeihin[item.cd_hin]) {
                                    item["no_yusen"] = jsonSeihin[item.cd_hin];
                                    item["cd_haigo"] = page.header.element.findP("cd_haigo").val();
                                    item["cd_koshin"] = App.ui.page.user.EmployeeCD;
                                    page.ma_seihin.data.update(item);
                                }
                                delete jsonSeihin[item.cd_hin];
                            }

                        });
                        $.each(jsonSeihin, function (key, value) {
                            var ma_seihin_seiho = {
                                cd_hin: key,
                                no_yusen: value,
                                cd_haigo: page.header.element.findP("cd_haigo").val(),
                                cd_koshin: App.ui.page.user.EmployeeCD
                            };
                            page.ma_seihin.data.attach(ma_seihin_seiho);
                            page.ma_seihin.data.update(ma_seihin_seiho);
                        });
                    }
                    //end M_kirikae = 2
                }//end mode shosai

                //Set parameter
                parameter.cs_ma_haigo_mei_hyoji = page.ma_haigo_mei_hyoji.data.getChangeSet();
                parameter.cs_ma_haigo_mei = page.ma_haigo_mei.data.getChangeSet();
                parameter.cs_ma_haigo_recipe_hyoji = page.ma_haigo_recipe_hyoji.data.getChangeSet();
                parameter.cs_ma_haigo_recipe = page.ma_haigo_recipe.data.getChangeSet();
                parameter.cs_ma_seizo_line_hyoji = page.ma_seizo_line_hyoji.data.getChangeSet();
                parameter.cs_ma_seizo_line = page.ma_seizo_line.data.getChangeSet();
                parameter.cs_ma_seihin = page.ma_seihin.data.getChangeSet();
                parameter.cd_kaisha = page.values.cd_kaisha;
                parameter.cd_kojyo = page.values.cd_kojyo;
                parameter.M_HaigoToroku = page.values.M_HaigoToroku;
                parameter.M_kirikae = page.values.M_kirikae;
                parameter.updateAllVersion = page.header.element.findP("kbn_koshin_han").prop("checked") ? true : false;

                // SKS_MOD_2020
                parameter.qty_kihon_old = page.values.qty_kihon_old;
                parameter.flg_mishiyo_old = page.values.flg_mishiyo_old;
                // SKS_MOD_2020

                //TODO: データの保存処理をここに記述します。
                return $.ajax(App.ajax.webapi.post(page.urls.haigoTorokuKojyoBumon, parameter))
                    .then(function (result) {

                        //TODO: データの保存成功時の処理をここに記述します。

                        //最後に再度データを取得しなおします。
                        //return App.async.all([page.header.search(false)]);
                    }).then(function () {
                        App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                        $("#save").prop("disabled", true);

                        setTimeout(function () {
                            var link = "209_HaigoTorokuKojyoBumon.aspx?cd_kaisha=" + page.values.cd_kaisha + "&cd_kojyo=" + page.values.cd_kojyo
                                + "&M_HaigoToroku="+ App.settings.app.m_haigo_toroku.shosai + "&M_kirikae=" + page.values.M_kirikae
                                + "&cd_haigo=" + page.header.element.findP("cd_haigo").val() + "&no_han=" + page.header.element.findP("no_han").val();

                            if (!App.isUndefOrNull(page.values.flg_edit_shikakarihin)) {
                                link = link + "&flg_edit_shikakarihin=" + page.values.flg_edit_shikakarihin;
                            }
                            window.open(link, "_self");
                        }, 2000)

                    }).fail(function (error) {

                        if (error.status === App.settings.base.conflictStatus) {
                            // TODO: 同時実行エラー時の処理を行っています。
                            // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                            App.ui.page.notifyAlert.clear();
                            App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
                            return;
                        }
                        
                        if (!App.isUndefOrNull(error.responseJSON) && error.responseJSON.Message == "NotExistsNoSeiho") {
                            App.ui.page.notifyAlert.message(App.messages.app.AP0090, page.header.element.findP("no_seiho_kaisha")).show();
                            page.setColInvalidStyle(page.header.element.findP("no_seiho_kaisha"));
                            return;
                        }

                        if (!App.isUndefOrNull(error.responseJSON) && error.responseJSON.Message == "NohanError") {
                            App.ui.page.notifyAlert.message(App.messages.app.AP0115).show();
                            return;
                        }

                        if (!App.isUndefOrNull(error.responseJSON) && error.responseJSON.Message == "ExistsHaigo") {
                            App.ui.page.notifyAlert.message(App.messages.app.AP0123.replace("{name}", "配合"), page.header.element.findP("cd_haigo")).show();
                            page.setColInvalidStyle(page.header.element.findP("cd_haigo"));
                            return;
                        }

                        App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

                    }).always(function () {
                        App.ui.loading.close();
                    })
            })
        }

        /**
         * Check if the mark is exist
         */
        page.checkExistsMark = function (cd_mark) {
            if (!page.values.ma_mark || App.isUndefOrNullOrStrEmpty(cd_mark)) {
                return false;
            }
            cd_mark = parseInt(cd_mark, 10)
            var mark = page.values.ma_mark[cd_mark];
            if (!mark) {
                return false;
            }
            return true;
        }

        /**
         * Get mark base on cd_mark
         */
        page.getMark = function (cd_mark, defValue) {
            var result = defValue || null;
            if (!page.values.ma_mark || App.isUndefOrNullOrStrEmpty(cd_mark)) {
                return result;
            }
            cd_mark = parseInt(cd_mark, 10);
            var mark = page.values.ma_mark[cd_mark];
            if (!mark) {
                return result;
            }
            return mark.mark;
        }

        /**
        * Get mark base on cd_mark
        */
        page.getObjMark = function (cd_mark, defValue) {
            var result = (defValue === undefined) ? {} : defValue;
            if (!page.values.ma_mark || App.isUndefOrNullOrStrEmpty(cd_mark)) {
                return result;
            }
            cd_mark = parseInt(cd_mark, 10);
            var mark = page.values.ma_mark[cd_mark];
            if (!mark) {
                return result;
            }
            return mark;
        }

        /**
         * データの保存処理を実行します。
         */
        page.commands.save = function () {

            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            var sleep = 0;
            var condition = "Object.keys(page.values.isChangeRunning).length == 0";
            App.ui.wait(sleep, condition, 100)
            .then(function () {

                page.validateAll().then(function () {

                    if ((Number($(page.detail.element.findP("qty_haigo_kei_user")[1]).val().replace(/,/g, "")) == 0 && $(page.detail.element.findP("rdo_shiage_user")[1]).prop("checked"))) {
                        App.ui.page.notifyAlert.message(App.messages.app.AP0020).show();
                        return;
                    }

                    if (page.detail.checkHinMark()) {
                        return;
                    }

                    var flg_error = false;
                    if ($(page.detail.element.findP("flg_hinkan")[1]).prop("checked")) {
                        for (var i = 0 ; i < page.detail.element.find(".new").length; i++) {
                            var row = $(page.detail.element.find(".new")[i]),
                                cd_hin = row.findP("cd_hin");
                            if (Number(cd_hin.val()) == 0) {
                                App.ui.page.notifyAlert.message(App.messages.app.AP0124, cd_hin).show();
                                row.find("tr").addClass("has-error");
                                cd_hin.addClass("validate-error");
                                flg_error = true;
                            }
                        }
                    }

                    if(flg_error){
                        return;
                    }

                    if (page.header.element.findP("kbn_hin").val() == App.settings.app.kbnHin.haigo) {
                        page.saveData();
                    }
                    else {
                        if (page.dialogs.seihinJohoNyuryokuDialog.element.find(".new").length > 0) {
                            App.ui.page.notifyAlert.message(App.messages.app.AP0017).show();
                            return;
                        }
                        page.saveData();
                    }

                });
            }).fail(function () {
                App.ui.page.notifyAlert.message(App.messages.base.MS0006).show();
            }).always(function () {
            });
        };

        /**
         * Add new version.
         */
        page.commands.addVersion = function () {
            if (!$("#save").prop("disabled")) {
                var options = {
                    text: App.messages.app.AP0018,
                    hideCancel: true
                };

                page.dialogs.confirmDialog.confirm(options)
                .always(function () {
                });
            }
            else {
                var options = {
                    text: (App.messages.app.AP0118).replace("{name}", "新規版追加に")
                };

                page.dialogs.confirmDialog.confirm(options)
                .then(function () {
                    var M_HaigoToroku = App.settings.app.m_haigo_toroku.newVersion,
                        no_han = page.values.no_han,
                        cd_kaisha = page.values.cd_kaisha,
                        cd_kojyo = page.values.cd_kojyo,
                        cd_haigo = page.values.cd_haigo,
                        M_kirikae = page.values.M_kirikae;
                   
                    var pathLink = "209_HaigoTorokuKojyoBumon.aspx" + "?cd_kaisha=" + cd_kaisha + "&cd_kojyo=" + cd_kojyo + "&M_HaigoToroku=" + M_HaigoToroku
                        + "&M_kirikae=" + M_kirikae + "&cd_haigo=" + cd_haigo + "&no_han=" + no_han;
                    App.ui.page.onclose = false;
                    window.open(pathLink,"_self");
                });
            }
        }

        /**
         * Add new version.
         */
        page.commands.deleteVersion = function() {
            var no_han = page.header.element.findP("no_han").val(),
                message;
            if (no_han == page.values.noHanFirst) {
                message = App.messages.app.AP0119;
            }
            else{
                message = App.messages.app.AP0120;
            }
            var options = {
                text: message
            };

            page.dialogs.confirmDialog.confirm(options)
            .then(function () {
                App.ui.loading.show();
                //TODO: データの保存処理をここに記述します。
                var parameter = {
                    cd_kaisha: page.values.cd_kaisha,
                    cd_kojyo: page.values.cd_kojyo,
                    cd_haigo: App.common.fillString(page.values.cd_haigo, 7),
                    no_han: page.header.element.findP("no_han").val(),
                    M_kirikae: page.values.M_kirikae
                }
                return $.ajax(App.ajax.webapi.get(page.urls.haigoTorokuKojyoBumon + "/get_deleteVersion", parameter))
                .then(function (result) {
                    App.ui.page.notifyInfo.message(App.messages.base.MS0008).show();
                    $("#save").prop("disabled", true);
                    setTimeout(function () {
                        window.close();
                    }, 2000)
                }).fail(function (error) {
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                }).always(function () {
                    App.ui.loading.close();
                })
            })
        }

        /**
         * Copy haigo
         */
        page.commands.copyHaigo = function (e) {
            if (!$("#save").prop("disabled")) {
                var options = {
                    text: App.messages.app.AP0018,
                    hideCancel: true
                };

                page.dialogs.confirmDialog.confirm(options)
                .always(function () {
                });
            }
            else {
                page.dialogs.haigoCopyDialog.values.M_kirikae = page.values.M_kirikae;
                page.dialogs.haigoCopyDialog.values.cd_kaisha = page.values.cd_kaisha;
                page.dialogs.haigoCopyDialog.values.cd_kojyo = page.values.cd_kojyo;
                page.dialogs.haigoCopyDialog.values.cd_haigo = page.values.cd_haigo;
                page.dialogs.haigoCopyDialog.values.no_han = page.values.no_han;
                page.dialogs.haigoCopyDialog.values.su_code_standard = page.values.su_code_standard + 1;
                page.dialogs.haigoCopyDialog.values.kbn_nmacs_kojyo = page.values.kbn_nmacs_kojyo;
                page.dialogs.haigoCopyDialog.element.modal({ backdrop: 'static', keyboard: false });
            }
        }

        /**
         * すべてのバリデーションを実行します。
         */
        page.validateAll = function () {

            var validations = [];

            validations.push(page.header.validator.validate());
            validations.push(page.detail.validateList());

            validations.push(page.detail.validator.validate({ targets: $(page.detail.element.findP("qty_haigo_kei_user")[1]) }));
            validations.push(page.detail.validator.validate({ targets: $(page.detail.element.findP("biko")[1]) }));

            //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。

            return App.async.all(validations);
        };

        /**
         * Windows閉じる際のイベントを定義します。
         * @return 文字列を返却した場合に確認メッセージが表示されます。
         */
        App.ui.page.onclose = function () {

            var closeMessage = App.messages.base.exit;

            if (!($("#save").prop("disabled"))) {
                return closeMessage;
            }
        };

        /**
        * 画面の初期化処理を行います。
        */
        page.initialize = function () {

            ////TODO: URLパラメーターで渡された値を取得し、保持する処理を記述します。

            if (!App.isUndefOrNull(App.uri.queryStrings.M_HaigoToroku)) {
                page.values.M_HaigoToroku = App.uri.queryStrings.M_HaigoToroku;
            } else {
                page.values.M_HaigoToroku = null;
            }

            if (!App.isUndefOrNull(App.uri.queryStrings.cd_haigo)) {
                page.values.cd_haigo = App.common.fillString(App.uri.queryStrings.cd_haigo, 7);
                page.values.flg_haigo_alpha = !App.isNumeric(page.values.cd_haigo);
            } else {
                page.values.cd_haigo = null;
            }

            if (!App.isUndefOrNull(App.uri.queryStrings.no_seiho)) {
                page.values.no_seiho = App.uri.queryStrings.no_seiho;
            } else {
                page.values.no_seiho = null;
            }

            if (!App.isUndefOrNull(App.uri.queryStrings.cd_kaisha)) {
                page.values.cd_kaisha = App.uri.queryStrings.cd_kaisha;
            } else {
                page.values.cd_kaisha = null;
            }

            if (!App.isUndefOrNull(App.uri.queryStrings.cd_kojyo)) {
                page.values.cd_kojyo = parseFloat(App.uri.queryStrings.cd_kojyo);
            } else {
                page.values.cd_kojyo = null;
            }

            if (!App.isUndefOrNull(App.uri.queryStrings.flg_edit_shikakarihin)) {
                page.values.flg_edit_shikakarihin = App.uri.queryStrings.flg_edit_shikakarihin;
            } else {
                page.values.flg_edit_shikakarihin = null;
            }

            if (!App.isUndefOrNull(App.uri.queryStrings.no_han)) {
                page.values.no_han = App.uri.queryStrings.no_han;
            } else {
                page.values.no_han = null;
                if (page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.copy) {
                    page.values.no_han = page.values.noHanFirst;
                }
                else {
                    page.values.no_han = null;
                }
            }

            if (!App.isUndefOrNull(App.uri.queryStrings.M_kirikae)) {
                page.values.M_kirikae = App.uri.queryStrings.M_kirikae;
            } else {
                page.values.M_kirikae = null;
            }

            if (page.values.M_kirikae == App.settings.app.m_kirikae.hyoji) {
                $(".page-title").text("表示用配合登録");
                $(".wrap, .footer").addClass("theme-pink");
            }
            else {
                $(".page-title").text("配合登録");
            }


            //page.values.M_kirikae = 1
            //page.values.M_HaigoToroku = 2
            //page.values.cd_kaisha = 1
            //page.values.cd_kojyo = 6
            //page.values.cd_haigo = "1000002"
            //page.values.no_han = 1
            //page.values.flg_edit_shikakarihin = 1

            //page.values.M_kirikae = 1
            //page.values.M_HaigoToroku = 1
            //page.values.cd_kaisha = 1
            //page.values.cd_kojyo = 6

            //page.setTitleMode();

            //page.values.cd_haigo = "258692"
            ////page.values.cd_haigo = "258685"
            ////page.values.flg_edit_shikakarihin = 0
            ////page.values.M_HaigoTorokuKaihatsu = 3;
            ////spage.values.no_seiho = "0001-A01-19-0071"

            ////page.values.flg_shoki = 1;
            ////page.values.no_seiho = null;
            ////page.values.cd_kaisha = null;
            ////page.values.cd_kojyo = null;
            ////page.values.flg_edit = 1; 

            page.setTitleMode();

            page.initializeControl();
            page.initializeControlEvent();

            page.header.initialize();
            page.detail.initialize();

            //TODO: ヘッダー/明細以外の初期化の処理を記述します。

            App.ui.loading.show();

            return page.loadMasterData().then(function () {
                return page.loadDialogs();
            }).then(function () {
                return page.getKengen()
            }).then(function () {
                //TODO: 画面の初期化処理成功時の処理を記述します。
               //return page.dialogs.tsuikaJohoNyuryokuDialog.loadData(true, true)
            }).then(function () {
                return page.loadData();
            }).then(function () {
                // SKS_MOD_2020
                page.setPageModeControl();
                page.setBackupValues();
                // SKS_MOD_2020
            }).fail(function (error) {
                if (error.status === 404) {
                    App.ui.page.notifyWarn.message(App.ajax.handleError(error).message).show();
                }
                else {
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                }
            }).always(function (result) {

                page.header.element.find(":input:first").focus();
                if (page.values.M_HaigoToroku != App.settings.app.m_haigo_toroku.newVersion) {
                    $("#save").prop("disabled", true);
                }
                
                App.ui.loading.close();
            });
        };

        /**
         * 画面コントロールの初期化処理を行います。
         */
        page.initializeControl = function () {

            //TODO: 画面全体で利用するコントロールの初期化処理をここに記述します。
            $(".part").part();

        };

        /**
         * コントロールへのイベントの紐づけを行います。
         */
        page.initializeControlEvent = function () {

            //TODO: 画面全体で利用するコントロールのイベントの紐づけ処理をここに記述します。
            $("#save").on("click", page.commands.save);
            $("#csv").on("click", page.commands.DownloadCSV);
            $("#add-version").on("click", page.commands.addVersion);
            $("#delete-version").on("click", page.commands.deleteVersion);
            $("#copy-haigo").on("click", page.commands.copyHaigo);
        };
        
        // SKS_MOD_2020
        // Display [dt_to]
        page.setPageModeControl = function () {
            if (page.values.M_kirikae == App.settings.app.m_kirikae.foodprocs) {
                if (page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.newVersion) {
                    if (page.values.kbn_nmacs_kojyo == page.values.kbn_nmacs_general) {
                        page.header.element.findP("dt_to").val("3000/12/31");
                    }
                }
                if (page.values.kbn_nmacs_kojyo == page.values.kbn_nmacs_general) {
                    if (page.header.element.findP("no_han").val() > '1') {
                        page.header.element.findP("dt_to").prop("disabled", true);
                    }
                }
            }
        }

        page.setBackupValues = function () {
            var hID = page.header.element.attr("data-key"),
                hData = page.header.data.entry(hID);
            page.values.qty_kihon_old = hData.qty_kihon;
            page.values.flg_mishiyo_old = hData.flg_mishiyo;
        }
        // SKS_MOD_2020

        /**
         * Set title mode
         */
        page.setTitleMode = function () {

            if (page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.shinki || page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.copy
                || page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.newVersion) {
                $("#title-mode").text("新規登録")
                $("#title-mode").css("color", "white").css("background-color", "red");
            }

            else if (page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.shosai) {
                if (page.values.flg_edit_shikakarihin) {
                    $("#title-mode").text("仕掛品　編集")
                    $("#title-mode").css("color", "white").css("background-color", "green");
                }
                else {
                    $("#title-mode").text("編　集")
                    $("#title-mode").css("color", "white").css("background-color", "blue");
                }
            }
        }

        //Download CSV
        page.commands.DownloadCSV = function () {
            if ($("#save").prop("disabled")) {

                var params = {
                    M_kirikae: page.values.M_kirikae,
                    cd_haigo: page.values.cd_haigo,
                    no_han: page.values.no_han,
                    cd_kaisha: page.values.cd_kaisha,
                    cd_kojyo: page.values.cd_kojyo,
                }

                App.ui.loading.show();
                App.ui.page.notifyAlert.clear();

                var listCsv = [];
                var haigoCSV = $.ajax(App.ajax.file.download(page.detail.options.downloadCSV, params))
                .then(function (response, status, xhr) {
                    listCsv.push({
                        response: response,
                        status: status,
                        xhr: xhr
                    });
                });

                var koteiCSV = $.ajax(App.ajax.file.download(page.detail.options.downloadCSV_2, params))
                .then(function (response, status, xhr) {
                    listCsv.push({
                        response: response,
                        status: status,
                        xhr: xhr
                    });
                });

                return App.async.all([haigoCSV, koteiCSV]).then(function () {
                    var failCSV = $.grep(listCsv, function (CSV) {
                        return CSV.status !== "success";
                    });
                    if (failCSV.length) {
                        return App.ui.page.notifyAlert.message(App.messages.base.MS0005).show();
                    }
                    $.each(listCsv, function (ind, CSV) {
                        App.file.save(CSV.response, App.ajax.file.extractFileNameDownload(CSV.xhr) || "CSVFile.csv");
                    })
                }).fail(function (error) {
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                }).always(function () {
                    setTimeout(function () {

                    })
                    App.ui.loading.close();
                });
            }
            else {
                var options = {
                    text: App.messages.app.AP0018,
                    hideCancel: true
                };
                page.dialogs.confirmDialog.confirm(options)
                .then(function () { })
            }
        };

        /**
         * Get kengen
         */
        page.getKengen = function () {
            var deferred = $.Deferred(),
                id_gamen = App.settings.app.id_gamen.haigo_ichiran_hyoji;
            if (page.values.M_kirikae == App.settings.app.m_kirikae.foodprocs) {
                id_gamen = App.settings.app.id_gamen.haigo_ichiran_foodprocs;
            }

            return getKengenGamen(id_gamen).then(function (results) {
                var id_kino_etsuran = false,
                    id_kino_sagyo_shiji = false,
                    id_kino_jisshi_kano = false;
                
                $.each(results, function (index, item) {
                    if (item.id_data == page.values.id_data) {
                        switch (item.id_kino) {
                            case page.values.id_kino_etsuran: //10
                                id_kino_etsuran = true;
                                break;
                            case page.values.id_kino_sagyo_shiji: // 20
                                id_kino_sagyo_shiji = true;
                                break;
                            case page.values.id_kino_jisshi_kano: // 30
                                id_kino_jisshi_kano = true;
                                break;
                        }

                    }
                });

                if (id_kino_etsuran && !id_kino_sagyo_shiji && !id_kino_jisshi_kano) {
                    page.header.element.find("input").prop("disabled", true);
                    page.header.element.find("button").prop("disabled", true);
                    page.header.element.find("select:not(.no_han)").prop("disabled", true);
                    page.detail.element.find("input").prop("disabled", true);
                    page.detail.element.find("button").prop("disabled", true);
                    page.detail.element.find("select").prop("disabled", true);
                    $(".button-command").find("button").prop("disabled", true);
                    $("#seizo-bunsho-sansho").prop("disabled", false);
                    $("#shiryo-tenpu").prop("disabled", false);
                    $("#tsuikaJohoNyuryoku-dialog").prop("disabled", false);
                    $("#seihinJohoNyuryoku-dialog").prop("disabled", false);
                    $("#csv").prop("disabled", false);
                    page.values.modeReadOnly = true;
                    page.values.allDisabledInput = true;
                }
                else if (id_kino_sagyo_shiji && !id_kino_jisshi_kano) {
                    $("#haigoTorikomi-dialog").prop("disabled", true);
                    $("#add-kotei").prop("disabled", true);
                    $("#move-kotei-up").prop("disabled", true);
                    $("#move-kotei-down").prop("disabled", true);
                    $("#del-kotei").prop("disabled", true);
                    $("#add-version").prop("disabled", true);
                    $("#delete-version").prop("disabled", true);
                    $("#copy-haigo").prop("disabled", true);
                    page.values.modeReadOnly = true;
                    page.values.kengen_kino_jisshi_kano = true;
                }
                else if (!id_kino_etsuran && !id_kino_sagyo_shiji && !id_kino_jisshi_kano) {
                    window.location.href = page.urls.mainMenu;
                }
                deferred.resolve();
            }).fail(function (error) {
                deferred.reject(error);
            })
            return deferred.promise();
        }

        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function () {
            var deferred = $.Deferred();

            return $.ajax(App.ajax.odata.get(page.header.urls.vw_no_seiho_shurui)
                ).then(function (result) {
                    var no_seiho_shurui = page.header.element.findP("no_seiho_shurui");
                    no_seiho_shurui.children().remove();
                    App.ui.appendOptions(
                        no_seiho_shurui,
                        "no_seiho_shurui",
                        "no_seiho_shurui",
                        result.value,
                        true
                    );
                    var parameter = {
                        cd_kaisha: page.values.cd_kaisha,
                        cd_kojyo: page.values.cd_kojyo
                    }
                    return $.ajax(App.ajax.webapi.get(page.header.urls.getComboboxKbnHin, parameter))
                }).then(function (result_kbnHin) {
                    var kbn_hin = page.header.element.findP("kbn_hin");
                    kbn_hin.children().remove();
                    App.ui.appendOptions(
                        kbn_hin,
                        "kbn_hin",
                        "nm_kbn_hin",
                        result_kbnHin
                    );
                    return page.header.loadComboboxHan();
                }).then(function () {
                    return page.header.loadRitsuKihon();
                }).then(function () {
                    return page.loadTaniData();
                }).then(function () {
                    return page.loadMarkData();
                }).then(function () {
                    return page.loadInfoKaishaKojyo();
                }).then(function () {
                    deferred.resolve();
                }).fail(function (error) {
                    deferred.reject(error);
                })
            return deferred.promise();
            
        };

        /**
         * Load data from ma_tani_table
         */
        page.loadTaniData = function () {
            var deferred = $.Deferred(),
                parameter = {
                    cd_kaisha: page.values.cd_kaisha,
                    cd_kojyo: page.values.cd_kojyo
                };

            return $.ajax(App.ajax.webapi.get(page.urls.haigoTorokuKojyoBumon + "/getTaniData", parameter)
                ).then(function (result) {
                    page.values.ma_tani = {};
                    for (var i = 0; i < result.length; i++) {
                        var tani = result[i];

                        tani.cd_tani = parseInt(tani.cd_tani, 10);

                        if (tani.flg_mishiyo) {
                            tani.nm_tani = "";
                        }

                        page.values.ma_tani[tani.cd_tani] = tani;
                    }
                    deferred.resolve();
                }).fail(function (error) {
                    deferred.reject(error);
                })
            return deferred.promise();
        }

        /**
         * load data from ma_mark table
         */
        page.loadMarkData = function () {
            var deferred = $.Deferred(),
                parameter = {
                    cd_kaisha: page.values.cd_kaisha,
                    cd_kojyo: page.values.cd_kojyo
                };
            return $.ajax(App.ajax.webapi.get(page.urls.haigoTorokuKojyoBumon + "/getMarkData", parameter)
                ).then(function (result) {
                    page.values.ma_mark = {};
                    for (var i = 0; i < result.length; i++) {
                        var mark = result[i];
                        mark.cd_mark = parseInt(mark.cd_mark, 10);
                        mark.cd_tani_shiyo = App.isUndefOrNull(mark.cd_tani_shiyo) ? null : parseInt(mark.cd_tani_shiyo, 10);
                        page.values.ma_mark[mark.cd_mark] = mark;
                    }
                    deferred.resolve();
                }).fail(function (error) {
                    deferred.reject(error);
                })
            return deferred.promise();

        }

        /**
         * Load info kaisha, kojyo
         */
        page.loadInfoKaishaKojyo = function () {
            var deferred = $.Deferred();
            return $.ajax(App.ajax.odata.get(App.str.format(page.urls.vw_kaisha_kojyo, page.values.cd_kaisha, page.values.cd_kojyo))).then(function (result) {
                if (result.value[0]) {
                    page.values.su_code_standard = result.value[0].su_code_standard;
                    page.detail.options.validations.cd_hin.rules.maxlength = Number(page.values.su_code_standard) + 1;
                    page.detail.element.find(".hinmei-input").prop("maxlength", Number(page.values.su_code_standard) + 1);
                }
                deferred.resolve();
            }).fail(function (error) {
                deferred.reject(error);
            })
            
            return deferred.promise();
        }

        /**
         * 共有ダイアログのロード処理を実行します。
         */
        page.loadDialogs = function () {

            //TODO: 共有ダイアログデータを取得し、画面にバインドする処理を記述します。
            return App.async.all({
                confirmDialog: $.get(page.urls.confirmDialog),
                seizoKojoShiteiDialog: $.get(page.urls.seizoKojoShiteiDialog),
                tsuikaJohoNyuryokuDialog: $.get(page.urls.tsuikaJohoNyuryokuDialog),
                seihinJohoNyuryokuDialog: $.get(page.urls.seihinJohoNyuryokuDialog),
                markDialog: $.get(page.urls.markDialog),
                hinmeiKaihatsuDialog: $.get(page.urls.hinmeiKaihatsuDialog),
                haigoTorikomiDialog: $.get(page.urls.haigoTorikomiDialog),
                haigoCopyDialog: $.get(page.urls.haigoCopyDialog)
            }).then(function (result) {

                $("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;

                $("#dialog-container").append(result.successes.tsuikaJohoNyuryokuDialog);
                page.dialogs.tsuikaJohoNyuryokuDialog = tsuikaJohoNyuryokuDialog;
                page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.no_gamen = App.settings.app.no_gamen.haigo_toroku_kojyo;
                page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.cd_kaisha = page.values.cd_kaisha;
                page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.cd_kojyo = page.values.cd_kojyo;
                page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.no_haigo = page.values.cd_haigo;
                page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.flg_edit = 0;

                page.dialogs.tsuikaJohoNyuryokuDialog.initialize();

                //dialog markKensaku
                $("#dialog-container").append(result.successes.markDialog);
                page.dialogs.markDialog = markDialog;
                page.dialogs.markDialog.options.parameter.no_gamen = App.settings.app.no_gamen.haigo_toroku_kojyo;
                page.dialogs.markDialog.initialize();

                //dialog seihinJohoNyuryoku
                $("#dialog-container").append(result.successes.seihinJohoNyuryokuDialog);
                page.dialogs.seihinJohoNyuryokuDialog = seihinJohoNyuryokuDialog;
                page.dialogs.seihinJohoNyuryokuDialog.options.parameter.no_gamen = App.settings.app.no_gamen.haigo_toroku_kojyo;
                page.dialogs.seihinJohoNyuryokuDialog.initialize();
                page.dialogs.seihinJohoNyuryokuDialog.dataSelected = function (data) {
                    page.header.element.findP("nm_seihin").text(data);
                }

                // Dialog hinmeiKaihatsu
                $("#dialog-container").append(result.successes.hinmeiKaihatsuDialog);
                page.dialogs.hinmeiKaihatsuDialog = _710_HinmeiKaihatsu_Dialog;
                page.dialogs.hinmeiKaihatsuDialog.param.no_gamen = App.settings.app.no_gamen.haigo_toroku_kojyo;
                page.dialogs.hinmeiKaihatsuDialog.initialize();

                //Dialog haigoTorikomi
                $("#dialog-container").append(result.successes.haigoTorikomiDialog);
                page.dialogs.haigoTorikomiDialog = haigoTorikomiDialog;
                page.dialogs.haigoTorikomiDialog.options.parameter.no_gamen = App.settings.app.no_gamen.haigo_toroku_kojyo;
                page.dialogs.haigoTorikomiDialog.initialize();

                //Dialog haigoTorikomi
                $("#dialog-container").append(result.successes.haigoCopyDialog);
                page.dialogs.haigoCopyDialog = _705_HaigoCopy_Dialog;
                page.dialogs.haigoCopyDialog.initialize();
                
            })
        };

        /**
         * 画面で処理の対象とするデータのロード処理を実行します。
         */
        page.loadData = function () {
            if (page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.shinki) {
                var deferred = $.Deferred();
                page.header.data = App.ui.page.dataSet();
                page.detail.data = App.ui.page.dataSet();
                page.ma_seizo_line_hyoji_temp.data = App.ui.page.dataSet();
                page.ma_seizo_line_temp.data = App.ui.page.dataSet();
                page.ma_seihin_temp.data = App.ui.page.dataSet();
                var item = {
                    flg_mishiyo: 0,
                    no_han: page.values.noHanFirst,
                    dt_from: "1950-01-01",
                    dt_to: "3000-12-31",
                    kbn_hin: page.header.element.findP("kbn_hin").val(),
                    kbn_vw: App.settings.app.cd_tani.kg,
                    ritsu_kihon: page.header.element.findP("ritsu_kihon").val() == "" ? null : page.header.element.findP("ritsu_kihon").val(),
                    kbn_koshin_han: 0

                };
                page.header.data.add(item);
                page.header.element.form(page.header.options.bindOption).bind(item);

                
                page.detail.addNewKotei();

                //header
                page.header.element.findP("no_han").prop("disabled", true);
                page.header.element.findP("dt_from").prop("disabled", true);
                page.header.element.findP("dt_to").prop("disabled", true);
                //detail
                $(page.detail.element.findP("qty_haigo_kei_user")[1]).prop("disabled", true);
                //command
                $("#add-version").prop("disabled", true);
                $("#delete-version").prop("disabled", true);
                $("#copy-haigo").prop("disabled", true);
                $("#csv").prop("disabled", true);

                page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.no_gamen = App.settings.app.no_gamen.haigo_toroku_kojyo;
                page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.cd_kaisha = page.values.cd_kaisha;
                page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.cd_kojyo = page.values.cd_kojyo;
                page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.no_haigo = page.values.cd_haigo;
                page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.M_HaigoToroku = App.settings.app.m_haigo_toroku.shinki;
                page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.M_kirikae = page.values.M_kirikae;
                page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.flg_seiho_base = 0;
                page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.kbn_hin = page.header.element.findP("kbn_hin").val();
                page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.no_han = 1
                page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.modeReadOnly = page.values.modeReadOnly;
                return page.dialogs.tsuikaJohoNyuryokuDialog.loadData(true).then(function () {

                    page.dialogs.seihinJohoNyuryokuDialog.options.parameter.no_gamen = App.settings.app.no_gamen.haigo_toroku_kojyo;
                    page.dialogs.seihinJohoNyuryokuDialog.options.parameter.M_kirikae = page.values.M_kirikae;
                    page.dialogs.seihinJohoNyuryokuDialog.options.parameter.flg_seiho_base = 0;
                    page.dialogs.seihinJohoNyuryokuDialog.options.parameter.cd_kaisha = page.values.cd_kaisha;
                    page.dialogs.seihinJohoNyuryokuDialog.options.parameter.cd_kojyo = page.values.cd_kojyo;
                    page.dialogs.seihinJohoNyuryokuDialog.options.parameter.no_haigo = page.values.cd_haigo;
                    page.dialogs.seihinJohoNyuryokuDialog.options.parameter.su_code_standard = page.values.su_code_standard;
                    page.dialogs.seihinJohoNyuryokuDialog.options.parameter.modeReadOnly = page.values.modeReadOnly;
                    return page.dialogs.seihinJohoNyuryokuDialog.loadData()
                    deferred.resolve();
                }).then(function(){
                }).fail(function (error) {
                    deferred.reject();
                })
                return deferred.promise();
            }//end m_haigo_toroku = 1

                //MODE SHOSAI, MODE COPY
            else if (page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.shosai || page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.copy
                || page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.newVersion || page.values.flg_edit_shikakarihin) {
                var deferred = $.Deferred(),
                    isDeleted = false,
                    flg_seiho_base = 0,
                    data_copy = {},
                    parameter = {
                        //cd_haigo: App.num.format(Number(page.values.cd_haigo),"0000000"),
                        cd_haigo: App.common.fillString(page.values.cd_haigo, 7),
                        no_han: page.values.no_han,
                        cd_kaisha: page.values.cd_kaisha,
                        cd_kojyo: page.values.cd_kojyo,
                        M_kirikae: page.values.M_kirikae
                    };
                return $.ajax(App.ajax.webapi.get(page.urls.haigoTorokuKojyoBumon + "/getData", parameter)
                ).then(function (result) {
                    page.values.resultSearch = $.extend({},result);
                    //start bind data
                    //start M_kirikae = 1
                    if (page.values.M_kirikae == App.settings.app.m_kirikae.hyoji) {
                        if (result.ma_haigo_mei_hyoji == null) {
                            $("input").prop("disabled", true);
                            $("select").prop("disabled", true);
                            $("button").prop("disabled", true);
                            isDeleted = true;
                            App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
                            setTimeout(function () {
                                window.close();
                            }, 3000)
                        }
                        else {
                            page.header.data = App.ui.page.dataSet();
                            page.detail.data = App.ui.page.dataSet();
                            page.ma_seizo_line_hyoji_temp.data = App.ui.page.dataSet();
                            page.ma_seihin_temp.data = App.ui.page.dataSet();

                            //bind header
                            if (page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.copy) {
                                data_copy = {
                                    flg_mishiyo: 0,
                                    no_han: page.values.noHanFirst,
                                    dt_from: "1950-01-01",
                                    dt_to: "3000-12-31",
                                    nm_haigo: result.ma_haigo_mei_hyoji.nm_haigo,
                                    nm_haigo_r: result.ma_haigo_mei_hyoji.nm_haigo_r,
                                    kbn_hin: result.ma_haigo_mei_hyoji.kbn_hin,
                                    kbn_vw: result.ma_haigo_mei_hyoji.kbn_vw,
                                    ritsu_kihon: result.ma_haigo_mei_hyoji.ritsu_kihon,
                                    nm_seiho: result.ma_haigo_mei_hyoji.nm_seiho,
                                    kbn_koshin_han: 0,
                                    no_seiho: result.ma_haigo_mei_hyoji.no_seiho,
                                    kbn_shiagari: result.ma_haigo_mei_hyoji.kbn_shiagari,
                                    qty_haigo_kei: result.ma_haigo_mei_hyoji.qty_haigo_kei,
                                    biko: result.ma_haigo_mei_hyoji.biko
                                };
                                if (!App.isUndefOrNull(result.ma_haigo_mei_hyoji.no_seiho)) {
                                    data_copy.no_seiho_kaisha = result.ma_haigo_mei_hyoji.no_seiho.substr(0, 4);
                                    data_copy.no_seiho_shurui = result.ma_haigo_mei_hyoji.no_seiho.substr(5, 3);
                                    data_copy.no_seiho_nen = result.ma_haigo_mei_hyoji.no_seiho.substr(9, 2);
                                    data_copy.no_seiho_renban = result.ma_haigo_mei_hyoji.no_seiho.substr(12, 4);
                                }
                                page.header.data.add(data_copy);
                                page.header.element.form(page.header.options.bindOption).bind(data_copy);

                            }
                            else if (page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.newVersion) {
                                result.ma_haigo_mei_hyoji.no_han = page.values.maxHan + 1;
                                result.ma_haigo_mei_hyoji.cd_haigo_seiho = null;
                                result.ma_haigo_mei_hyoji.flg_seiho_base = false;
                                result.ma_haigo_mei_hyoji.flg_seizo = false;
                                result.ma_haigo_mei_hyoji.cd_tanto_seizo = null;
                                result.ma_haigo_mei_hyoji.dt_seizo = null;
                                result.ma_haigo_mei_hyoji.cd_tanto_hinkan = null;
                                result.ma_haigo_mei_hyoji.dt_hinkan = null;
                                result.ma_haigo_mei_hyoji.dt_toroku = null;
                                result.ma_haigo_mei_hyoji.dt_henko = null;
                                result.ma_haigo_mei_hyoji.nm_tanto_koshin = null;

                                page.header.data.add(result.ma_haigo_mei_hyoji);
                                if (!App.isUndefOrNull(result.list_ma_seihin[0])) {
                                    result.ma_haigo_mei_hyoji.nm_seihin = result.list_ma_seihin[0].nm_hin;
                                }
                                if (!App.isUndefOrNull(result.ma_haigo_mei_hyoji.no_seiho)) {
                                    result.ma_haigo_mei_hyoji.no_seiho_kaisha = result.ma_haigo_mei_hyoji.no_seiho.substr(0, 4);
                                    result.ma_haigo_mei_hyoji.no_seiho_shurui = result.ma_haigo_mei_hyoji.no_seiho.substr(5, 3);
                                    result.ma_haigo_mei_hyoji.no_seiho_nen = result.ma_haigo_mei_hyoji.no_seiho.substr(9, 2);
                                    result.ma_haigo_mei_hyoji.no_seiho_renban = result.ma_haigo_mei_hyoji.no_seiho.substr(12, 4);
                                }
                                page.header.element.form(page.header.options.bindOption).bind(result.ma_haigo_mei_hyoji);
                            }
                            else {
                                page.header.data.attach(result.ma_haigo_mei_hyoji);
                                result.ma_haigo_mei_hyoji.nm_tanto_koshin = result.ma_tanto_koshin.nm_tanto;
                                if (!App.isUndefOrNull(result.list_ma_seihin[0])) {
                                    result.ma_haigo_mei_hyoji.nm_seihin = result.list_ma_seihin[0].nm_hin;
                                }
                                if (!App.isUndefOrNull(result.ma_haigo_mei_hyoji.no_seiho)) {
                                    result.ma_haigo_mei_hyoji.no_seiho_kaisha = result.ma_haigo_mei_hyoji.no_seiho.substr(0, 4);
                                    result.ma_haigo_mei_hyoji.no_seiho_shurui = result.ma_haigo_mei_hyoji.no_seiho.substr(5, 3);
                                    result.ma_haigo_mei_hyoji.no_seiho_nen = result.ma_haigo_mei_hyoji.no_seiho.substr(9, 2);
                                    result.ma_haigo_mei_hyoji.no_seiho_renban = result.ma_haigo_mei_hyoji.no_seiho.substr(12, 4);
                                }
                                flg_seiho_base = App.isUndefOrNull(result.ma_haigo_mei_hyoji.flg_seiho_base) ? 0 : result.ma_haigo_mei_hyoji.flg_seiho_base;

                                if (flg_seiho_base) {
                                    result.ma_haigo_mei_hyoji.flg_seiho_base_show = "原本";
                                    page.header.element.findP("flg_seiho_base_show").css("color", "white").css("background-color", "blue");
                                    // Disable edit
                                    page.values.flg_edit_shikakarihin = null;
                                }
                                else {
                                    result.ma_haigo_mei_hyoji.flg_seiho_base_show = "";
                                }

                                //if (!App.isUndefOrNullOrStrEmpty(result.ma_haigo_mei_hyoji.no_seiho)) {
                                page.header.element.find("#excel-haigo-list").show();
                                //}
                                page.header.element.form(page.header.options.bindOption).bind(result.ma_haigo_mei_hyoji);

                                if (page.header.element.findP("dt_toroku").text() == page.header.element.findP("dt_henko").text()) {
                                    page.header.element.findP("dt_henko").text("");
                                    page.header.element.findP("nm_tanto_koshin").text("");
                                }
                            }
                            if (result.ma_haigo_mei_hyoji.kbn_vw == App.settings.app.cd_tani.kg) {
                                page.header.element.findP("rdoVWKbnKg").prop("checked", true);
                            }
                            else {
                                page.header.element.findP("rdoVWKbnL").prop("checked", true);
                            }

                            //bind detail
                            var data_detail_bind = [];
                            for (var i = 0; i < result.list_ma_haigo_recipe_hyoji.length; i++) {
                                var item = result.list_ma_haigo_recipe_hyoji[i],
                                    ss_vw_hin_varchar_hyoji = result.list_SS_vw_hin_varchar_hyoji[i];

                                if (page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.copy) {
                                    page.detail.data.add(item);
                                }
                                else {
                                    page.detail.data.attach(item);
                                }
                                var mark = page.getObjMark(parseInt(item.cd_mark, 10), null);
                                if (mark) {
                                    if (mark.cd_tani_shiyo == App.settings.app.taniShiyo.g) {
                                        item.qty_haigo = App.isUndefOrNull(item.qty_haigo) ? null : new BigNumber(item.qty_haigo.toString()).times(1000).toNumber();
                                    }
                                } else {
                                    //item.qty_haigo = null;
                                }
                                item.gosa = item.hijyu;
                                // Detected change data when round with point 3
                                if (item.qty_haigo != page.getFixed3(item.qty_haigo)) {
                                    item.qty_haigo = page.getFixed3(item.qty_haigo)
                                    page.detail.data.update(item);
                                }

                                data_detail_bind.push
                                (
                                    {
                                        __id: item.__id,
                                        kbn_bunkatsu: item.kbn_bunkatsu,
                                        no_kotei: item.no_kotei,
                                        no_tonyu: item.no_tonyu,
                                        kotei_tonyu: item.no_kotei + "-" + item.no_tonyu,
                                        cd_hin: item.cd_hin,
                                        kbn_shitei: item.kbn_shitei,
                                        nm_hin: item.nm_hin,
                                        cd_mark: parseInt(item.cd_mark, 10),
                                        mark: page.getMark(item.cd_mark),//page.values.ma_mark[parseInt(item.cd_mark, 10)].mark,
                                        qty_haigo: item.qty_haigo,
                                        kbn_hin: item.kbn_hin,
                                        cd_tani_hin:App.isUndefOrNull(ss_vw_hin_varchar_hyoji) ? null : ss_vw_hin_varchar_hyoji.cd_tani_hin,
                                        no_sort:App.isUndefOrNull(ss_vw_hin_varchar_hyoji) ? null : ss_vw_hin_varchar_hyoji.no_sort,
                                        qty_nisugata: item.qty_nisugata,
                                        hijyu: App.isUndefOrNull(ss_vw_hin_varchar_hyoji) ? null : ss_vw_hin_varchar_hyoji.hijyu,
                                        budomari: item.budomari,
                                        no_kikaku: App.isUndefOrNull(ss_vw_hin_varchar_hyoji) ? null : ss_vw_hin_varchar_hyoji.no_kikaku,
                                        gosa: item.hijyu
                                    }
                                )
                            }
                            if (data_detail_bind.length) {
                                page.detail.bind(data_detail_bind);
                            }
                            else {
                                var options = {
                                    text: App.messages.app.AP0116,
                                    hideCancel: true
                                };

                                page.dialogs.confirmDialog.confirm(options)
                                .always(function () {
                                    page.detail.data = App.ui.page.dataSet();
                                    page.detail.addNewKotei();
                                });
                            }
                            //Bind footer
                            if (!result.ma_haigo_mei_hyoji.kbn_shiagari || App.isUndefOrNull(result.ma_haigo_mei_hyoji.kbn_shiagari)) {
                                $(page.detail.element.findP("rdo_shiage")[1]).prop("checked", true);
                                //$(page.detail.element.findP("qty_haigo_kei")[1]).text(App.num.format(Number(result.ma_haigo_mei_hyoji.qty_haigo_kei), "#,0.000000"));
                                //$(page.detail.element.findP("qty_haigo_kei_user")[1]).val(App.num.format(Number(result.ma_haigo_mei_hyoji.qty_haigo_kei), "#,0.000000"));
                                page.detail.calculateQtyHaigoKei(true);
                                $(page.detail.element.findP("qty_haigo_kei_user")[1]).val($(page.detail.element.findP("qty_haigo_kei")[1]).text());
                                $(page.detail.element.findP("qty_haigo_kei_user")[1]).prop("disabled", true);
                            }
                            else {
                                $(page.detail.element.findP("rdo_shiage_user")[1]).prop("checked", true);
                                page.detail.calculateQtyHaigoKei(true);
                                $(page.detail.element.findP("qty_haigo_kei_user")[1]).val(App.num.format(Number(result.ma_haigo_mei_hyoji.qty_haigo_kei), "#,0.000000"));
                            }
                            if (result.ma_haigo_mei_hyoji.kbn_vw == App.settings.app.cd_tani.kg) {
                                $(page.detail.element.findP("tani_kei")[1]).text(App.settings.app.nm_tani.kg);
                                $(page.detail.element.findP("tani_kei_user")[1]).text(App.settings.app.nm_tani.kg);
                            }
                            else {
                                $(page.detail.element.findP("tani_kei")[1]).text(App.settings.app.nm_tani.l);
                                $(page.detail.element.findP("tani_kei_user")[1]).text(App.settings.app.nm_tani.l);
                            }
                            $(page.detail.element.findP("biko")[1]).val(App.isUndefOrNull(result.ma_haigo_mei_hyoji.biko) ? "" : result.ma_haigo_mei_hyoji.biko);

                            if (page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.shosai) {
                                if (result.ma_haigo_mei_hyoji.flg_seizo) {
                                    $(page.detail.element.findP("flg_seizo")[1]).prop("checked", true);
                                }
                                if (!App.isUndefOrNull(result.ma_haigo_mei_hyoji.dt_seizo)) {
                                    $(page.detail.element.findP("dt_seizo")[1]).text(result.ma_haigo_mei_hyoji.dt_seizo.substr(0, 10).replace(/-/g, "/"));
                                }

                                if (!App.isUndefOrNull(result.ma_tanto_seizo) && !App.isUndefOrNull(result.ma_tanto_seizo.nm_tanto)) {
                                    $(page.detail.element.findP("nm_tanto_seizo")[1]).text(result.ma_tanto_seizo.nm_tanto);
                                }

                                if (result.ma_haigo_mei_hyoji.flg_hinkan) {
                                    $(page.detail.element.findP("flg_hinkan")[1]).prop("checked", true);
                                }

                                if (!App.isUndefOrNull(result.ma_haigo_mei_hyoji.dt_hinkan)) {
                                    $(page.detail.element.findP("dt_hinkan")[1]).text(result.ma_haigo_mei_hyoji.dt_hinkan.substr(0, 10).replace(/-/g, "/"));
                                }
                                if (!App.isUndefOrNull(result.ma_tanto_hinkan) && !App.isUndefOrNull(result.ma_tanto_hinkan.nm_tanto)) {
                                    $(page.detail.element.findP("nm_tanto_hinkan")[1]).text(result.ma_tanto_hinkan.nm_tanto);
                                }

                            }

                            for (var i = 0; i < result.list_ma_seizo_line_hyoji.length; i++) {
                                var item = result.list_ma_seizo_line_hyoji[i];
                                page.ma_seizo_line_hyoji_temp.data.attach(item);
                            }

                            if (page.values.M_HaigoToroku != App.settings.app.m_haigo_toroku.copy) {
                                for (var i = 0; i < result.list_ma_seihin.length; i++) {
                                    var item = result.list_ma_seihin[i];
                                    page.ma_seihin_temp.data.attach(item);
                                }
                            }
                        }
                    }//end M_kirikae = 1
                    else { // start M_kirikae = 2
                        if (result.ma_haigo_mei == null) {
                            $("input").prop("disabled", true);
                            $("select").prop("disabled", true)
                            $("button").prop("disabled", true);
                            isDeleted = true;
                            App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
                            setTimeout(function () {
                                window.close();
                            }, 2000)
                        }
                        else {
                            page.header.data = App.ui.page.dataSet();
                            page.detail.data = App.ui.page.dataSet();
                            page.ma_seizo_line_temp.data = App.ui.page.dataSet();
                            page.ma_seihin_temp.data = App.ui.page.dataSet();

                            //bind header
                            if (page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.copy) {
                                data_copy = {
                                    flg_mishiyo: 0,
                                    no_han: page.values.noHanFirst,
                                    dt_from: "1950-01-01",
                                    dt_to: "3000-12-31",
                                    nm_haigo: result.ma_haigo_mei.nm_haigo,
                                    nm_haigo_r: result.ma_haigo_mei.nm_haigo_r,
                                    kbn_hin: result.ma_haigo_mei.kbn_hin,
                                    kbn_vw: result.ma_haigo_mei.kbn_vw,
                                    ritsu_kihon: result.ma_haigo_mei.ritsu_kihon,
                                    nm_seiho: result.ma_haigo_mei.nm_seiho,
                                    kbn_koshin_han: 0,
                                    no_seiho: result.ma_haigo_mei.no_seiho,
                                    kbn_shiagari: result.ma_haigo_mei.kbn_shiagari,
                                    qty_haigo_kei: result.ma_haigo_mei.qty_haigo_kei,
                                    biko: result.ma_haigo_mei.biko
                                };
                                if (!App.isUndefOrNull(result.ma_haigo_mei.no_seiho)) {
                                    data_copy.no_seiho_kaisha = result.ma_haigo_mei.no_seiho.substr(0, 4);
                                    data_copy.no_seiho_shurui = result.ma_haigo_mei.no_seiho.substr(5, 3);
                                    data_copy.no_seiho_nen = result.ma_haigo_mei.no_seiho.substr(9, 2);
                                    data_copy.no_seiho_renban = result.ma_haigo_mei.no_seiho.substr(12, 4);
                                }
                                page.header.data.add(data_copy);
                                page.header.element.form(page.header.options.bindOption).bind(data_copy);
                            }
                            else if (page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.newVersion) {
                                result.ma_haigo_mei.no_han = page.values.maxHan + 1;
                                result.ma_haigo_mei.cd_haigo_seiho = null;
                                result.ma_haigo_mei.flg_seiho_base = false;
                                result.ma_haigo_mei.flg_seizo = false;
                                result.ma_haigo_mei.cd_tanto_seizo = null;
                                result.ma_haigo_mei.dt_seizo = null;
                                result.ma_haigo_mei.cd_tanto_hinkan = null;
                                result.ma_haigo_mei.dt_hinkan = null;
                                result.ma_haigo_mei.dt_toroku = null;
                                result.ma_haigo_mei.dt_henko = null;
                                result.ma_haigo_mei.nm_tanto_koshin = null;
                                page.header.data.add(result.ma_haigo_mei);
                                if (!App.isUndefOrNull(result.list_ma_seihin[0])) {
                                    result.ma_haigo_mei.nm_seihin = result.list_ma_seihin[0].nm_hin;
                                }
                                if (!App.isUndefOrNull(result.ma_haigo_mei.no_seiho)) {
                                    result.ma_haigo_mei.no_seiho_kaisha = result.ma_haigo_mei.no_seiho.substr(0, 4);
                                    result.ma_haigo_mei.no_seiho_shurui = result.ma_haigo_mei.no_seiho.substr(5, 3);
                                    result.ma_haigo_mei.no_seiho_nen = result.ma_haigo_mei.no_seiho.substr(9, 2);
                                    result.ma_haigo_mei.no_seiho_renban = result.ma_haigo_mei.no_seiho.substr(12, 4);
                                }
                                page.header.element.form(page.header.options.bindOption).bind(result.ma_haigo_mei);
                            }
                            else {
                                page.header.data.attach(result.ma_haigo_mei);
                                result.ma_haigo_mei.nm_tanto_koshin = result.ma_tanto_koshin.nm_tanto;
                                if (!App.isUndefOrNull(result.list_ma_seihin[0])) {
                                    result.ma_haigo_mei.nm_seihin = result.list_ma_seihin[0].nm_hin;
                                }
                                if (!App.isUndefOrNull(result.ma_haigo_mei.no_seiho)) {
                                    result.ma_haigo_mei.no_seiho_kaisha = result.ma_haigo_mei.no_seiho.substr(0, 4);
                                    result.ma_haigo_mei.no_seiho_shurui = result.ma_haigo_mei.no_seiho.substr(5, 3);
                                    result.ma_haigo_mei.no_seiho_nen = result.ma_haigo_mei.no_seiho.substr(9, 2);
                                    result.ma_haigo_mei.no_seiho_renban = result.ma_haigo_mei.no_seiho.substr(12, 4);
                                }
                                flg_seiho_base = App.isUndefOrNull(result.ma_haigo_mei.flg_seiho_base) ? 0 : result.ma_haigo_mei.flg_seiho_base;
                                if (flg_seiho_base) {
                                    result.ma_haigo_mei.flg_seiho_base_show = "原本";
                                    page.header.element.findP("flg_seiho_base_show").css("color", "white").css("background-color", "blue");
                                    // Disable edit
                                    page.values.flg_edit_shikakarihin = null;
                                }
                                else {
                                    result.ma_haigo_mei.flg_seiho_base_show = "";
                                }

                                //if (!App.isUndefOrNullOrStrEmpty(result.ma_haigo_mei.no_seiho)) {
                                page.header.element.find("#excel-haigo-list").show();
                                //}
                                page.header.element.form(page.header.options.bindOption).bind(result.ma_haigo_mei);

                                if (page.header.element.findP("dt_toroku").text() == page.header.element.findP("dt_henko").text()) {
                                    page.header.element.findP("dt_henko").text("");
                                    page.header.element.findP("nm_tanto_koshin").text("");
                                }
                            }
                            if (result.ma_haigo_mei.kbn_vw == App.settings.app.cd_tani.kg) {
                                page.header.element.findP("rdoVWKbnKg").prop("checked", true);
                            }
                            else {
                                page.header.element.findP("rdoVWKbnL").prop("checked", true);
                            }

                            //bind detail
                            data_detail_bind = [];
                            for (var i = 0; i < result.list_ma_haigo_recipe.length; i++) {
                                var item = result.list_ma_haigo_recipe[i],
                                    ss_vw_hin_varchar = result.list_SS_vw_hin_varchar[i];
                                if (page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.copy) {
                                    page.detail.data.add(item);
                                }
                                else {
                                    page.detail.data.attach(item);
                                }
                                var mark = page.getObjMark(parseInt(item.cd_mark, 10), null);
                                if (mark) {
                                    if (mark.cd_tani_shiyo == App.settings.app.taniShiyo.g) {
                                        item.qty_haigo = App.isUndefOrNull(item.qty_haigo) ? null : new BigNumber(item.qty_haigo.toString()).times(1000).toNumber();
                                    }
                                } else {
                                    //item.qty_haigo = null;
                                }

                                item.gosa = item.hijyu;
                                // Detected change data when round with point 3
                                if (item.qty_haigo != page.getFixed3(item.qty_haigo)) {
                                    item.qty_haigo = page.getFixed3(item.qty_haigo)
                                    page.detail.data.update(item);
                                }

                                data_detail_bind.push
                                (
                                    {
                                        __id: item.__id,
                                        kbn_bunkatsu: item.kbn_bunkatsu,
                                        no_kotei: item.no_kotei,
                                        no_tonyu: item.no_tonyu,
                                        kotei_tonyu: item.no_kotei + "-" + item.no_tonyu,
                                        cd_hin: item.cd_hin,
                                        kbn_shitei: item.kbn_shitei,
                                        nm_hin: item.nm_hin,
                                        cd_mark: parseInt(item.cd_mark, 10),
                                        mark: page.getMark(item.cd_mark),//page.values.ma_mark[parseInt(item.cd_mark, 10)].mark,
                                        qty_haigo: item.qty_haigo,
                                        kbn_hin: item.kbn_hin,
                                        cd_tani_hin: App.isUndefOrNull(ss_vw_hin_varchar) ? null : ss_vw_hin_varchar.cd_tani_hin,
                                        no_sort: App.isUndefOrNull(ss_vw_hin_varchar) ? null : ss_vw_hin_varchar.no_sort,
                                        qty_nisugata: item.qty_nisugata,
                                        hijyu: App.isUndefOrNull(ss_vw_hin_varchar) ? null : ss_vw_hin_varchar.hijyu,
                                        budomari: item.budomari,
                                        no_kikaku: App.isUndefOrNull(ss_vw_hin_varchar) ? null : ss_vw_hin_varchar.no_kikaku,
                                        gosa: item.hijyu
                                    }
                                )
                            }
                            if (data_detail_bind.length) {
                                page.detail.bind(data_detail_bind);
                            }
                            else {
                                var options = {
                                    text: App.messages.app.AP0116,
                                    hideCancel: true
                                };

                                page.dialogs.confirmDialog.confirm(options)
                                .always(function () {
                                    page.detail.data = App.ui.page.dataSet();
                                    page.detail.addNewKotei();
                                });
                            }
                            //Bind footer
                            if (!result.ma_haigo_mei.kbn_shiagari || App.isUndefOrNull(result.ma_haigo_mei.kbn_shiagari)) {
                                $(page.detail.element.findP("rdo_shiage")[1]).prop("checked", true);
                                //$(page.detail.element.findP("qty_haigo_kei")[1]).text(App.num.format(Number(result.ma_haigo_mei.qty_haigo_kei), "#,0.000000"));
                                //$(page.detail.element.findP("qty_haigo_kei_user")[1]).val(App.num.format(Number(result.ma_haigo_mei.qty_haigo_kei), "#,0.000000"));
                                page.detail.calculateQtyHaigoKei(true);
                                $(page.detail.element.findP("qty_haigo_kei_user")[1]).val($(page.detail.element.findP("qty_haigo_kei")[1]).text());
                                $(page.detail.element.findP("qty_haigo_kei_user")[1]).prop("disabled", true);
                            }
                            else {
                                $(page.detail.element.findP("rdo_shiage_user")[1]).prop("checked", true);
                                page.detail.calculateQtyHaigoKei(true);
                                $(page.detail.element.findP("qty_haigo_kei_user")[1]).val(App.num.format(Number(result.ma_haigo_mei.qty_haigo_kei), "#,0.000000"));
                            }
                            if (result.ma_haigo_mei.kbn_vw == App.settings.app.cd_tani.kg) {
                                $(page.detail.element.findP("tani_kei")[1]).text(App.settings.app.nm_tani.kg);
                                $(page.detail.element.findP("tani_kei_user")[1]).text(App.settings.app.nm_tani.kg);
                            }
                            else {
                                $(page.detail.element.findP("tani_kei")[1]).text(App.settings.app.nm_tani.l);
                                $(page.detail.element.findP("tani_kei_user")[1]).text(App.settings.app.nm_tani.l);
                            }
                            $(page.detail.element.findP("biko")[1]).val(App.isUndefOrNull(result.ma_haigo_mei.biko) ? "" : result.ma_haigo_mei.biko);

                            if (page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.shosai) {
                                if (result.ma_haigo_mei.flg_seizo) {
                                    $(page.detail.element.findP("flg_seizo")[1]).prop("checked", true);
                                }
                                if (!App.isUndefOrNull(result.ma_haigo_mei.dt_seizo)) {
                                    $(page.detail.element.findP("dt_seizo")[1]).text(result.ma_haigo_mei.dt_seizo.substr(0, 10).replace(/-/g, "/"));
                                }

                                if (!App.isUndefOrNull(result.ma_tanto_seizo) && !App.isUndefOrNull(result.ma_tanto_seizo.nm_tanto)) {
                                    $(page.detail.element.findP("nm_tanto_seizo")[1]).text(result.ma_tanto_seizo.nm_tanto);
                                }

                                if (result.ma_haigo_mei.flg_hinkan) {
                                    $(page.detail.element.findP("flg_hinkan")[1]).prop("checked", true);
                                }

                                if (!App.isUndefOrNull(result.ma_haigo_mei.dt_hinkan)) {
                                    $(page.detail.element.findP("dt_hinkan")[1]).text(result.ma_haigo_mei.dt_hinkan.substr(0, 10).replace(/-/g, "/"));
                                }
                                if (!App.isUndefOrNull(result.ma_tanto_hinkan) && !App.isUndefOrNull(result.ma_tanto_hinkan.nm_tanto)) {
                                    $(page.detail.element.findP("nm_tanto_hinkan")[1]).text(result.ma_tanto_hinkan.nm_tanto);
                                }
                            }

                            
                            for (var i = 0; i < result.list_ma_seizo_line.length; i++) {
                                var item = result.list_ma_seizo_line[i];
                                page.ma_seizo_line_temp.data.attach(item);
                            }
                            
                            if (page.values.M_HaigoToroku != App.settings.app.m_haigo_toroku.copy) {
                                for (var i = 0; i < result.list_ma_seihin.length; i++) {
                                    var item = result.list_ma_seihin[i];
                                    page.ma_seihin_temp.data.attach(item);
                                }
                            }
                        }
                    }//End M_kirikae = 2

                    if (!isDeleted) {
                        

                        
                        if (page.values.M_HaigoToroku != App.settings.app.m_haigo_toroku.copy) {
                            //load dialog tsuikaJohoNyuryokuDialog
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.no_gamen = App.settings.app.no_gamen.haigo_toroku_kojyo;
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.cd_kaisha = page.values.cd_kaisha;
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.cd_kojyo = page.values.cd_kojyo;
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.no_haigo = page.values.cd_haigo;
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.M_HaigoToroku = page.values.M_HaigoToroku;
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.M_kirikae = page.values.M_kirikae;
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.flg_seiho_base = page.values.M_kirikae == App.settings.app.m_kirikae.hyoji ? result.ma_haigo_mei_hyoji.flg_seiho_base : result.ma_haigo_mei.flg_seiho_base;
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.no_han = page.header.element.findP("no_han").val();
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.kbn_hin = page.header.element.findP("kbn_hin").val();
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.modeReadOnly = page.values.modeReadOnly;
                            page.dialogs.tsuikaJohoNyuryokuDialog.loadData(true)

                            //load dialog seihinJohoNyuryokuDialog
                            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.no_gamen = App.settings.app.no_gamen.haigo_toroku_kojyo;
                            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.M_kirikae = page.values.M_kirikae;
                            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.flg_seiho_base = page.values.M_kirikae == App.settings.app.m_kirikae.hyoji ? result.ma_haigo_mei_hyoji.flg_seiho_base : result.ma_haigo_mei.flg_seiho_base;
                            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.cd_kaisha = page.values.cd_kaisha;
                            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.cd_kojyo = page.values.cd_kojyo;
                            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.no_haigo = page.values.cd_haigo;
                            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.su_code_standard = page.values.su_code_standard;
                            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.modeReadOnly = page.values.modeReadOnly;
                            page.dialogs.seihinJohoNyuryokuDialog.loadData()
                        }
                        else {
                            //load dialog tsuikaJohoNyuryokuDialog
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.no_gamen = App.settings.app.no_gamen.haigo_toroku_kojyo;
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.cd_kaisha = page.values.cd_kaisha;
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.cd_kojyo = page.values.cd_kojyo;
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.no_haigo = page.values.cd_haigo;
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.M_HaigoToroku = page.values.M_HaigoToroku;
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.M_kirikae = page.values.M_kirikae;
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.flg_seiho_base = 0;
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.no_han = page.header.element.findP("no_han").val();
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.kbn_hin = page.header.element.findP("kbn_hin").val();
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.modeReadOnly = page.values.modeReadOnly;
                            page.dialogs.tsuikaJohoNyuryokuDialog.loadData(true)

                            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.no_gamen = App.settings.app.no_gamen.haigo_toroku_kojyo;
                            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.M_kirikae = page.values.M_kirikae;
                            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.flg_seiho_base = 0;
                            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.cd_kaisha = page.values.cd_kaisha;
                            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.cd_kojyo = page.values.cd_kojyo;
                            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.no_haigo = null;
                            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.su_code_standard = page.values.su_code_standard;
                            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.modeReadOnly = page.values.modeReadOnly;
                            page.dialogs.seihinJohoNyuryokuDialog.loadData()
                        }
                    }
                    //end bind data mode m_kirikae = 2

                    // SKS_MOD_2020
                    if (result.kaisha_kojyo) {
                        page.values.kbn_nmacs_kojyo = result.kaisha_kojyo.kbn_nmacs_kojyo;
                    }
                    // SKS_MOD_2020

                }).then(function () {
                    //start set status control
                    if (page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.shosai || page.values.flg_edit_shikakarihin) {
                        page.header.element.findP("cd_haigo").prop("disabled", true);

                        if (page.values.flg_edit_shikakarihin) {
                            page.header.element.findP("nm_haigo").prop("disabled", true);
                            page.header.element.findP("nm_haigo_r").prop("disabled", true);
                            page.header.element.findP("rdoVWKbnKg").prop("disabled", true);
                            page.header.element.findP("rdoVWKbnL").prop("disabled", true);
                            page.header.element.findP("ritsu_kihon").prop("disabled", true);
                            if (page.values.no_han == page.values.noHanFirst) {
                                page.header.element.findP("dt_from").prop("disabled", true);
                                page.header.element.findP("dt_to").prop("disabled", true);
                            }
                            else {
                                page.header.element.findP("kbn_hin").prop("disabled", true);
                            }
                        }
                        else {
                            if (flg_seiho_base) {
                                //Header
                                page.header.element.findP("dt_from").prop("disabled", true);
                                page.header.element.findP("dt_to").prop("disabled", true);
                                page.header.element.findP("nm_haigo").prop("disabled", true);
                                page.header.element.findP("nm_haigo_r").prop("disabled", true);
                                page.header.element.findP("kbn_hin").prop("disabled", true);
                                page.header.element.findP("rdoVWKbnKg").prop("disabled", true);
                                page.header.element.findP("rdoVWKbnL").prop("disabled", true);
                                page.header.element.findP("ritsu_kihon").prop("disabled", true);
                                page.header.element.findP("no_seiho_kaisha").prop("disabled", true);
                                page.header.element.findP("no_seiho_shurui").prop("disabled", true);
                                page.header.element.findP("no_seiho_nen").prop("disabled", true);
                                page.header.element.findP("no_seiho_renban").prop("disabled", true);
                                page.header.element.findP("kbn_koshin_han").prop("disabled", true);
                                page.header.element.findP("nm_seiho").prop("disabled", true);
                                $("#haigoTorikomi-dialog").prop("disabled", true);

                                //Detail
                                page.detail.element.find("input").prop("disabled", true);
                                page.detail.element.find("button").prop("disabled", true);
                                //Command
                                $("#delete-version").prop("disabled", true);
                            }
                            else {
                                if (page.values.no_han == page.values.noHanFirst) {
                                    page.header.element.findP("dt_from").prop("disabled", true);
                                    page.header.element.findP("dt_to").prop("disabled", true);
                                }
                                else {
                                    page.header.element.findP("nm_haigo").prop("disabled", true);
                                    page.header.element.findP("nm_haigo_r").prop("disabled", true);
                                    page.header.element.findP("kbn_hin").prop("disabled", true);
                                    page.header.element.findP("ritsu_kihon").prop("disabled", true);
                                    page.header.element.findP("rdoVWKbnKg").prop("disabled", true);
                                    page.header.element.findP("rdoVWKbnL").prop("disabled", true);
                                }
                            }
                        }

                        // Request #16630
                        if (page.values.flg_haigo_alpha) {
                            // Enabled button
                            // 製法文書参照
                            $("#seizo-bunsho-sansho").prop("disabled", false);
                            // 資料添付/参照
                            $("#shiryo-tenpu").prop("disabled", false);
                            // 配合表Excel
                            $("#excel-haigo-list").prop("disabled", false);
                            // 追加情報入力
                            $("#tsuikaJohoNyuryoku-dialog").prop("disabled", false);
                            // 製品情報入力
                            $("#seihinJohoNyuryoku-dialog").prop("disabled", false);
                            // 版削除
                            $("#delete-version").prop("disabled", false);
                            // 配合データコピー
                            $("#copy-haigo").prop("disabled", false);
                            // Disabled button
                            // 配合取込
                            $("#haigoTorikomi-dialog").prop("disabled", true);
                            // 版追加
                            $("#add-version").prop("disabled", true);
                            // CSV出力
                            $("#csv").prop("disabled", true);
                            // 保存
                            $("#save").prop("disabled", true);

                            // Display error message
                            page.dialogs.confirmDialog.confirm({
                                text: App.messages.app.AP0166,
                                hideCancel: true
                            });

                            // Disabled all input controls
                            //Header
                            page.header.element.findP("dt_from").prop("disabled", true);
                            page.header.element.findP("dt_to").prop("disabled", true);
                            page.header.element.findP("nm_haigo").prop("disabled", true);
                            page.header.element.findP("nm_haigo_r").prop("disabled", true);
                            page.header.element.findP("kbn_hin").prop("disabled", true);
                            page.header.element.findP("rdoVWKbnKg").prop("disabled", true);
                            page.header.element.findP("rdoVWKbnL").prop("disabled", true);
                            page.header.element.findP("ritsu_kihon").prop("disabled", true);
                            page.header.element.findP("no_seiho_kaisha").prop("disabled", true);
                            page.header.element.findP("no_seiho_shurui").prop("disabled", true);
                            page.header.element.findP("no_seiho_nen").prop("disabled", true);
                            page.header.element.findP("no_seiho_renban").prop("disabled", true);
                            page.header.element.findP("kbn_koshin_han").prop("disabled", true);
                            page.header.element.findP("nm_seiho").prop("disabled", true);
                            $("#haigoTorikomi-dialog").prop("disabled", true);

                            //Detail
                            page.detail.element.find("input").prop("disabled", true);
                            page.detail.element.find("button").prop("disabled", true);

                        }

                    }
                    else if (page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.copy) {
                        //header
                        page.header.element.findP("no_han").prop("disabled", true);
                        page.header.element.findP("dt_from").prop("disabled", true);
                        page.header.element.findP("dt_to").prop("disabled", true);
                        //command
                        $("#add-version").prop("disabled", true);
                        $("#delete-version").prop("disabled", true);
                        $("#copy-haigo").prop("disabled", true);
                        $("#csv").prop("disabled", true);
                    }
                    else{
                        page.header.element.findP("cd_haigo").prop("disabled", true);
                        page.header.element.findP("no_han").prop("disabled", true);
                        page.header.element.findP("nm_haigo").prop("disabled", true);
                        page.header.element.findP("nm_haigo_r").prop("disabled", true);
                        page.header.element.findP("kbn_hin").prop("disabled", true);
                        page.header.element.findP("rdoVWKbnKg").prop("disabled", true);
                        page.header.element.findP("rdoVWKbnL").prop("disabled", true);
                        page.header.element.findP("ritsu_kihon").prop("disabled", true);
                        $("#csv").prop("disabled", true);
                        $("#add-version").prop("disabled", true);
                        $("#delete-version").prop("disabled", true);
                        $("#copy-haigo").prop("disabled", true);
                    }
                    deferred.resolve();
                }).fail(function (error) {
                    deferred.reject(error);
                })
                return deferred.promise();
            }//end m_haigo_toroku = 2
            
        };

        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.validations = {
            //TODO: 画面ヘッダーのバリデーションの定義を記述します。
            cd_haigo: {
                rules: {
                    required: true,
                    digits: true,
                    maxlength: 7
                },
                options: {
                    name: "配合コード"
                },
                messages: {
                    required: App.messages.base.required,
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength
                }
            },

            no_han: {
                rules: {
                    required: true
                },
                options: {
                    name: "版"
                },
                messages: {
                    required: App.messages.base.required
                }
            },

            dt_from: {
                rules: {
                    required:true,
                    datestring: true,
                    lessThanDate: "dt_to"
                },
                options: {
                    name: "有効期間（開始)"
                },
                messages: {
                    required: App.messages.base.required,
                    datestring: App.messages.base.datestring,
                    lessThanDate: App.messages.base.MS0014

                }
            },

            dt_to: {
                rules: {
                    required: true,
                    datestring: true
                },
                options: {
                    name: "有効期間（終了)"
                },
                messages: {
                    required: App.messages.base.required,
                    datestring: App.messages.base.datestring
                }
            },

            nm_haigo: {
                rules: {
                    required: true,
                    maxbytelength: 60
                },
                options: {
                    name: "配合名",
                    byte:30
                },
                messages: {
                    required: App.messages.base.required,
                    maxbytelength: App.messages.base.maxbytelength
                }
            },

            nm_haigo_r: {
                rules: {
                    maxbytelength: 20
                },
                options: {
                    name: "配合名略称",
                    byte:10
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },

            kbn_hin: {
                rules: {
                    required: true
                },
                options: {
                    name: "品区分"
                },
                messages: {
                    required: App.messages.base.required
                }
            },

            ritsu_kihon: {
                rules: {
                    required: true,
                    number: true,
                    range: [0, 999.99],
                    pointlength: [3, 2]
                },
                options: {
                    name: "基本倍率（係数）"
                },
                messages: {
                    required: App.messages.base.required,
                    number: App.messages.base.number,
                    range: App.messages.base.range,
                    pointlength: App.messages.base.pointlength
                }
            },

            no_seiho_kaisha: {
                rules: {
                    digits: true,
                    maxlength: 4
                },
                options: {
                    name: "製法番号（会社コード）"
                },
                messages: {
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength
                }
            },

            no_seiho_nen: {
                rules: {
                    digits: true,
                    maxlength: 2
                },
                options: {
                    name: "製法番号（年）"
                },
                messages: {
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength
                }
            },

            no_seiho_renban: {
                rules: {
                    digits: true,
                    maxlength: 4
                },
                options: {
                    name: "製法番号（シーケンス番号）"
                },
                messages: {
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength
                }
            },

            nm_seiho: {
                rules: {
                    maxbytelength: 120
                },
                options: {
                    name: "製法名",
                    byte:60
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            }
        };

        /**
         * 画面ヘッダーの初期化処理を行います。
         */
        page.header.initialize = function () {

            var element = $(".header");
            page.header.validator = element.validation(page.createValidator(page.header.options.validations));
            element.on("change", ":input", page.header.change);
            page.header.element = element;

            element.find("input[data-role='date']").datepicker({ dateFormat: "yy/mm/dd" });
            element.find("input[data-role='date']").on("keyup", App.data.addSlashForDateString);
            element.find("input[data-role='date']").on("blur", function (e) {
                $(e.target).trigger("change");
            });
            
            element.on("click", "#tsuikaJohoNyuryoku-dialog", page.header.showTsuikaJohoNyuryokuDialog);
            element.on("click", "#seihinJohoNyuryoku-dialog", page.header.showSeihinJohoNyuryokuDialog);
            element.on("click", "#haigoTorikomi-dialog", page.header.showHaigoTorikomiDialog);
            element.on("click", "#seizo-bunsho-sansho", page.header.moveSeihoBunshoSakuseiGamen);
            element.on("click", "#shiryo-tenpu", page.header.moveTenpuBunsho);
            element.on("click", "#excel-haigo-list", function () {
                page.header.downloadHaigoListExcel(2);
            });
            
        };

        /**
         * 画面ヘッダーへのデータバインド処理を行います。
         */
        page.header.bind = function (data, isNewData) {

            var element = page.header.element,
                dataSet = App.ui.page.dataSet();

            page.header.data = dataSet;

            (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(data);
            page.header.element.form(page.header.options.bindOption).bind(data);

            if (isNewData) {

                //TODO: 新規データの場合の処理を記述します。
                //ドロップダウンなど初期値がある場合は、
                //DataSetに値を反映させるために change 関数を呼び出します。
                //element.findP("cd_shiharai").change();
            }

            //バリデーションを実行します。
            page.header.validator.validate({
                state: {
                    suppressMessage: true
                }
            });
        };

        /**
        * 画面ヘッダーにデータを設定する際のオプションを定義します。
        */
        page.header.options.bindOption = {
            appliers: {
                dt_toroku: function (value, element) {
                    if (App.isUndefOrNull(value) || value == "") {
                        element.text("");
                    }
                    else {
                        element.text(value.substr(0, 10).replace(/-/g, "/") + " " + value.substr(11, 8));
                    }
                    return true;
                },

                dt_henko: function (value, element) {
                    if (App.isUndefOrNull(value) || value == "") {
                        element.text("");
                    }
                    else {
                        element.text(value.substr(0, 10).replace(/-/g, "/") + " " + value.substr(11, 8));
                    }
                    return true;
                },

                ritsu_kihon: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.val("");
                    }
                    else {
                        element.val(App.num.format(Number(value), "#,#.00"))
                    }
                    return true;
                },

                flg_mishiyo: function (value, element) {
                    if (App.isUndefOrNull(value) || !value) {
                        element.prop("checked", false);
                    }
                    else {
                        element.prop("checked", true);
                    }
                    return true;
                },

                cd_haigo_seiho: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.text("");
                    }
                    else {
                        var pad = "0000000000000",
                            formatLength = Number(page.values.su_code_standard) + 1,
                            length = value.toString().length,
                            addZero = (formatLength - length) > 0 ? pad.substr(0, (formatLength - length)) : "";
                        hinCodeFormat = value + addZero;
                        element.text(hinCodeFormat);
                    }
                    return true;
                }
            }
        };

        /**
         * 画面ヘッダーにある入力項目の変更イベントの処理を行います。
         */
        page.header.change = function (e) {
            var element = page.header.element,
                target = $(e.target),
                id = element.attr("data-key"),
                property = target.attr("data-prop"),
                entity = page.header.data.entry(id),
                data = element.form().data();

            page.values.isChangeRunning[property] = true;
            if (property != "no_han") {
                $("#save").prop("disabled", false);
            }

            if (property == "dt_from" || property == "dt_to") {
                element.validation().validate({
                    targets: element.find(".yukokigen")
                }).then(function () {
                    entity["dt_from"] = page.header.element.findP("dt_from").val().replace(/\//g, "-");
                    entity["dt_to"] = page.header.element.findP("dt_to").val().replace(/\//g, "-");
                    page.header.data.update(entity);
                }).always(function () {
                    delete page.values.isChangeRunning[property];
                })
                return;
            }

            element.validation().validate({
                targets: target
            }).then(function () {
                if (property == "cd_haigo") {
                    var valOld = target.val(),
                        valNew = App.num.format(Number(valOld), "0000000");
                    target.val(valNew);
                    entity[property] = valNew;
                }
                else if(property == "no_han"){
                    var options = {
                        text: (App.messages.app.AP0118).replace("{name}", " 版を")
                    };

                    page.dialogs.confirmDialog.confirm(options)
                    .then(function () {
                        var M_HaigoToroku = App.settings.app.m_haigo_toroku.shosai,
                            no_han = target.val(),
                            cd_kaisha = page.values.cd_kaisha,
                            cd_kojyo = page.values.cd_kojyo,
                            cd_haigo = page.values.cd_haigo,
                            M_kirikae = page.values.M_kirikae,
                            pathLink;
                        if(App.isUndefOrNull(page.values.flg_edit_shikakarihin)){
                            pathLink = "209_HaigoTorokuKojyoBumon.aspx" + "?cd_kaisha=" + cd_kaisha + "&cd_kojyo=" + cd_kojyo + "&M_HaigoToroku=" + M_HaigoToroku
                                + "&M_kirikae=" + M_kirikae + "&cd_haigo=" + cd_haigo + "&no_han=" + no_han;
                        }
                        else{
                            pathLink = "209_HaigoTorokuKojyoBumon.aspx" + "?cd_kaisha=" + cd_kaisha + "&cd_kojyo=" + cd_kojyo + "&M_HaigoToroku=" + M_HaigoToroku
                                + "&M_kirikae=" + M_kirikae + "&cd_haigo=" + cd_haigo + "&no_han=" + no_han + "&flg_edit_shikakarihin=1";
                        }
                        App.ui.page.onclose = false;
                        window.open(pathLink,"_self");
                    }).fail(function(){
                        var no_han_old = entity["no_han"];
                        target.val(no_han_old);
                    })
                }
                else if (property == "flg_mishiyo") {
                    if (target.prop("checked")) {
                        entity[property] = 1
                    }
                    else {
                        entity[property] = 0
                    }
                }
                else if (property == "kbn_hin") {
                    if (target.val() == App.settings.app.kbnHin.shikakari) {
                        page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").prop("disabled", false);
                        page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("flg_gasan").prop("disabled", false);
                    }
                    else {
                        page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").prop("disabled", true).val("")
                        page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("flg_gasan").prop("disabled", true).prop("checked", false);
                    }
                }
                else if (property == "rdoVWKbnKg") {
                    entity["kbn_vw"] = App.settings.app.cd_tani.kg;
                    $(page.detail.element.findP("tani_kei")[1]).text(App.settings.app.nm_tani.kg);
                    $(page.detail.element.findP("tani_kei_user")[1]).text(App.settings.app.nm_tani.kg);
                    page.detail.calculateQtyHaigoKei();
                }
                else if (property == "rdoVWKbnL") {
                    entity["kbn_vw"] = App.settings.app.cd_tani.l;
                    $(page.detail.element.findP("tani_kei")[1]).text(App.settings.app.nm_tani.l);
                    $(page.detail.element.findP("tani_kei_user")[1]).text(App.settings.app.nm_tani.l);
                    page.detail.calculateQtyHaigoKei();
                }
                else if (property == "no_seiho_kaisha") {
                    var valOld = target.val();
                    if (valOld == "") {
                        entity["no_seiho_kaisha"] = null;
                    }
                    else {
                        var valNew = App.num.format(Number(valOld), "0000");
                        target.val(valNew);
                        entity["no_seiho_kaisha"] = valNew;
                    }

                    element.validation().validate({
                        targets: element.find(".no-seiho-group")
                    })

                }
                else if (property == "no_seiho_shurui") {
                    var val = target.val();
                    if (App.isUndefOrNull(val) || val == "") {
                        entity["no_seiho_shurui"] = null;
                    }
                    else {
                        entity["no_seiho_shurui"] = val;
                    }

                    element.validation().validate({
                        targets: element.find(".no-seiho-group")
                    })
                }
                else if (property == "no_seiho_nen") {
                    var valOld = target.val();
                    if (valOld == "") {
                        entity["no_seiho_nen"] = null;
                    }
                    else {
                        var valNew = App.num.format(Number(valOld), "00");
                        target.val(valNew);
                        entity["no_seiho_nen"] = valNew;
                    }

                    element.validation().validate({
                        targets: element.find(".no-seiho-group")
                    })
                }
                else if (property == "no_seiho_renban") {
                    var valOld = target.val();
                    if (valOld == "") {
                        entity["no_seiho_renban"] = null;
                    }
                    else {
                        var valNew = App.num.format(Number(valOld), "0000");
                        target.val(valNew);
                        entity["no_seiho_renban"] = valNew;
                    }

                    element.validation().validate({
                        targets: element.find(".no-seiho-group")
                    })
                }
                else if (property == "kbn_koshin_han") {
                    if (target.prop("checked")) {
                        entity[property] = 1
                    }
                    else {
                        entity[property] = 0
                    }
                }

                else if (property == "ritsu_kihon") {
                    var valOld = target.val(),
                        valNew = App.num.format(Number(valOld), "#,#.00");
                    target.val(valNew);
                    entity[property] = valNew;
                }
                else {
                    entity[property] = data[property];
                }
                page.header.data.update(entity);
            }).always(function () {
                delete page.values.isChangeRunning[property];
            });
        };

        //TODO: 以下の page.header の各宣言は画面仕様ごとにことなるため、不要の場合は削除してください。

        /**
        * 画面ヘッダーにある検索ダイアログ起動時の処理を定義します。
        */
        page.header.showSearchDialog = function () {
            page.dialogs.searchDialog.element.modal("show");
        };

        /**
         * 画面ヘッダーにある取引先コードに値を設定します。
         */
        page.header.setTorihiki = function (data) {
            page.header.element.findP("cd_toroku").val(data.cd_toroku).change();
            page.header.element.findP("hinmei").text(data.ninmei);
        };
     
        /**
        *  Load combobox Han
        */
        page.header.loadComboboxHan = function () {
            var deferred = $.Deferred();
            if (App.isUndefOrNull(page.values.cd_haigo) || page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.copy) {
                var no_han = page.header.element.findP("no_han");
                App.ui.appendOptions(
                    no_han,
                    "no_han",
                    "no_han",
                    [{ no_han: page.values.noHanFirst }]
                )

                page.values.maxHan = page.values.noHanFirst;

                deferred.resolve();
            }
            else {
                
                var parameter = {
                    cd_kaisha: page.values.cd_kaisha,
                    cd_kojyo: page.values.cd_kojyo,
                    cd_haigo: App.common.fillString(page.values.cd_haigo, 7),
                    M_kirikae: page.values.M_kirikae
                }

                return $.ajax(App.ajax.webapi.get(page.urls.haigoTorokuKojyoBumon + "/getDataComboboxHan", parameter)).then(function (result) {

                    var no_han = page.header.element.findP("no_han");
                    no_han.children().remove();
                    App.ui.appendOptions(
                        no_han,
                        "no_han",
                        "no_han",
                        result
                    );

                    if (result.length) {
                        page.values.maxHan = result[result.length - 1].no_han;

                        if (page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.newVersion) {
                            App.ui.appendOptions(
                                no_han,
                                "no_han",
                                "no_han",
                                [{ no_han: result[result.length - 1].no_han + 1 }]
                            );
                        }
                    }

                    deferred.resolve();
                }).fail(function (error) {
                    deferred.reject(error);
                })
            }
            return deferred.promise();
        }

        /**
        *  Load ritsu_kihon
        */
        page.header.loadRitsuKihon = function () {
            var deferred = $.Deferred();
            if (page.values.M_HaigoToroku == App.settings.app.m_haigo_toroku.shinki) {
                return $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.vw_ma_kojyo, page.values.cd_kaisha, page.values.cd_kojyo))).then(function (result_kojyo) {
                    if (result_kojyo.value[0]) {
                        page.header.element.findP("ritsu_kihon").val(App.num.format(Number(result_kojyo.value[0].ritsu_kihon), "#,#.00"));
                    }
                    deferred.resolve();
                }).fail(function (error) {
                    deferred.reject(error);
                })
            }
            else {
                deferred.resolve();
            }
            return deferred.promise();

        }

        /**
        * Show TsuikaJohoNyuryoku dialog
        */
        page.header.showTsuikaJohoNyuryokuDialog = function () {
            //検索ダイアログで選択が実行された時に呼び出される関数を設定しています。
            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.modeReadOnly = page.values.flg_haigo_alpha ? true : page.values.modeReadOnly;
            page.dialogs.tsuikaJohoNyuryokuDialog.element.modal("show");
        }

        /**
        * Show SehinJohoNyuryoku dialog
        */
        page.header.showSeihinJohoNyuryokuDialog = function () {
            //検索ダイアログで選択が実行された時に呼び出される関数を設定しています。
           
            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.modeReadOnly = page.values.flg_haigo_alpha ? true : page.values.modeReadOnly;

            page.dialogs.seihinJohoNyuryokuDialog.element.modal("show");
            
        }

        /**
        * Get Fixed value point 3
        */
        page.getFixed3 = function (value) {
            if (App.isUndefOrNull(value) || isNaN(Number(value))) {
                value = 0;
            }
            return new BigNumber(value.toString()).times(1000).round().div(1000).toNumber();
        }

        /**
        * Show HaigoTorikomi dialog
        */
        page.header.showHaigoTorikomiDialog = function () {
            page.dialogs.haigoTorikomiDialog.options.parameter.no_gamen = App.settings.app.no_gamen.haigo_toroku_kojyo;
            page.dialogs.haigoTorikomiDialog.options.parameter.cd_kaisha = page.values.cd_kaisha;
            page.dialogs.haigoTorikomiDialog.options.parameter.cd_kojyo = page.values.cd_kojyo;
            page.dialogs.haigoTorikomiDialog.options.parameter.M_kirikae = page.values.M_kirikae;
            page.dialogs.haigoTorikomiDialog.element.modal("show");

            page.dialogs.haigoTorikomiDialog.dataSelected = function (data) {
                $("#save").prop("disabled", false);

                var countRow = page.detail.element.find(".new").length,
                    idLast, maxKotei;

                if (countRow == 1) {
                    if (page.detail.element.find(".new").findP("cd_hin").val() == "") {
                        var selected = page.detail.element.find(".datatable .select-tab.selected").closest("tbody"),
                            kotei = parseInt(selected.attr("kotei")),
                            selectorsKotei = page.detail.element.find("." + kotei)

                        selectorsKotei.each(function (i, selectorKotei) {
                            page.detail.dataTable.dataTable("deleteRow", selectorKotei, function (row) {
                                var id = row.attr("data-key"),
                                    newSelected;

                                row.find(":input").each(function (i, elem) {
                                    App.ui.page.notifyAlert.remove(elem);
                                });

                                if (!App.isUndefOrNull(id)) {
                                    var entity = page.detail.data.entry(id);
                                    page.detail.data.remove(entity);
                                }
                            });
                        });
                        countRow = 0;
                    }
                }

                if (countRow > 0) {
                    idLast = $(page.detail.element.find(".new")[countRow - 1]).attr("data-key");
                    maxKotei = parseInt(page.detail.data.entry(idLast)["no_kotei"], 10) + 1;
                }
                else {
                    maxKotei = 1;
                }

                //Bind detail part.
                var data_detail = [];
                for (var i = 0; i < data.length; i++) {
                    var item = data[i],
                        haigo_recipe = {},
                        no_kotei_old = item.no_kotei;

                    item.no_kotei = maxKotei;
                    page.detail.data.add(item);
                   
                    var mark = page.getObjMark(parseInt(item.cd_mark, 10), null);
                    if (mark) {
                        if (mark.cd_tani_shiyo == App.settings.app.taniShiyo.g) {
                            item.qty_haigo = App.isUndefOrNull(item.qty_haigo) ? null : new BigNumber(item.qty_haigo).times(1000).toNumber();
                        }
                    } else {
                        //item.qty_haigo = null;
                    }
                    // Detected change data when round with point 3
                    if (item.qty_haigo != page.getFixed3(item.qty_haigo)) {
                        page.detail.data.update(item);
                    }
                    item.qty_haigo = page.getFixed3(item.qty_haigo);
                    item.qty_nisugata = page.getFixed3(item.qty_nisugata);

                    haigo_recipe.no_kotei = item.no_kotei;
                    haigo_recipe.no_tonyu = item.no_tonyu;
                    haigo_recipe.kbn_bunkatsu = item.kbn_bunkatsu;
                    haigo_recipe.kotei_tonyu = item.no_kotei + "-" + item.no_tonyu;
                    haigo_recipe.cd_hin = item.cd_hin;
                    haigo_recipe.kbn_shitei = item.kbn_shitei;
                    haigo_recipe.nm_hin = item.nm_hin;
                    haigo_recipe.mark = page.getMark(item.cd_mark),//page.values.ma_mark[parseInt(item.cd_mark, 10)].mark;
                    haigo_recipe.qty_haigo = item.qty_haigo;
                    haigo_recipe.qty_nisugata = item.qty_nisugata;
                    haigo_recipe.hijyu = item.hijyu_label;
                    haigo_recipe.budomari = item.budomari;
                    haigo_recipe.no_kikaku = item.no_kikaku;
                    haigo_recipe.gosa = item.hijyu;
                    haigo_recipe.cd_mark = parseInt(item.cd_mark,10);
                    haigo_recipe.cd_tani_hin = item.cd_tani_hin;
                    haigo_recipe.kbn_hin = item.kbn_hin;
                  
                    haigo_recipe.__id = item.__id;
                    data_detail.push(haigo_recipe);

                    if (!App.isUndefOrNull(data[i + 1])) {
                        if (data[i + 1].no_kotei > no_kotei_old) {
                            maxKotei = maxKotei + 1;
                        }
                    }
                }
                page.detail.bind(data_detail, true);
                page.detail.calculateQtyHaigoKei();
                delete page.dialogs.haigoTorikomiDialog.dataSelected
            }


        }

        /**
        * Move to SeihoBunshoSakusei
        */
        page.header.moveSeihoBunshoSakuseiGamen = function () {
            var no_seiho_kaisha = page.header.element.findP("no_seiho_kaisha").val(),
                no_seiho_shurui = page.header.element.findP("no_seiho_shurui").val(),
                no_seiho_nen = page.header.element.findP("no_seiho_nen").val(),
                no_seiho_renban = page.header.element.findP("no_seiho_renban").val(),
                no_seiho = no_seiho_kaisha + "-" + no_seiho_shurui + "-" + no_seiho_nen + "-" + no_seiho_renban;
                
        
            App.ui.loading.show();
            return $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_seiho_denso, no_seiho,page.values.cd_kaisha,page.values.cd_kojyo))).then(function (result) {

                if (result.value.length) {
                    return $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_seiho_bunsho_hyoshi, no_seiho)))
                }
                else {
                    App.ui.page.notifyAlert.remove("AP0117");
                    App.ui.page.notifyAlert.remove("AP0125");
                    App.ui.page.notifyAlert.message((App.messages.app.AP0117).replace("{name}", "製法文書"), "AP0117").show();
                    
                }
            }).then(function (seiho_hyoshi) {
                if (!App.isUndefOrNull(seiho_hyoshi)) {
                    if (seiho_hyoshi.value.length == 0) {
                        App.ui.page.notifyAlert.remove("AP0125");
                        App.ui.page.notifyAlert.remove("AP0117");
                        App.ui.page.notifyAlert.message(App.messages.app.AP0125, "AP0125").show();
                    }
                    else {
                        App.ui.page.notifyAlert.remove("AP0125");
                        App.ui.page.notifyAlert.remove("AP0117");
                        App.common.openSeihoBunsho
                        ({
                            mode: App.settings.app.m_seiho_bunsho.etsuran,
                            no_seiho: no_seiho
                        })
                    }
                }
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            }).always(function () {
                App.ui.loading.close();
            })
            
        }

        /**
        * Move to TenpuBunsho
        */
        page.header.moveTenpuBunsho = function () {

            var no_seiho_kaisha = page.header.element.findP("no_seiho_kaisha").val(),
                no_seiho_shurui = page.header.element.findP("no_seiho_shurui").val(),
                no_seiho_nen = page.header.element.findP("no_seiho_nen").val(),
                no_seiho_renban = page.header.element.findP("no_seiho_renban").val(),
                no_seiho = no_seiho_kaisha + "-" + no_seiho_shurui + "-" + no_seiho_nen + "-" + no_seiho_renban;

            App.ui.loading.show();
            $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_seiho_denso, no_seiho, page.values.cd_kaisha, page.values.cd_kojyo))).then(function (result) {

                if (result.value.length) {
                    var link = "211_TenpuBunsho.aspx?no_seiho=" + no_seiho;
                    window.open(link);
                }
                else {
                    App.ui.page.notifyAlert.remove("AP0117");
                    App.ui.page.notifyAlert.message((App.messages.app.AP0117).replace("{name}", "製法文書"), "AP0117").show();
                }
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            }).always(function () {
                App.ui.loading.close();
            })
        }

        /**
        * get bunsho excel filter
        */
        page.header.getBunshoExcelFilter = function (no_seiho_exc) {
            var element = page.header.element;
            return {
                no_seiho: no_seiho_exc,
                // Tab 10 only
                lst_tab: [App.settings.app.SeihobunshoTabName.HaigoHyo],
                // Category literal
                kbn_tokisaki: App.settings.app.cd_category.kbn_tokisaki,
                kbn_brand: App.settings.app.cd_category.kbn_brand,
                cd_category_kotei: App.settings.app.cd_category.kotei_patan,
                kbn_shomikikan: App.settings.app.cd_category.kbn_shomikikan,
                kbn_shomikikan_seizo_fukumu: App.settings.app.cd_category.kbn_shomikikan_seizo_fukumu,
                kbn_shomikikan_tani: App.settings.app.cd_category.kbn_shomikikan_tani,
                kbn_meisho: App.settings.app.cd_category.kbn_meisho,
                toriatsukai_ondo: App.settings.app.cd_category.toriatsukai_ondo,
                cd_literal_status: App.settings.app.cd_category.kbn_status,
                kbn_yoryo_tani: App.settings.app.cd_category.yoryo_tani,
                kbn_haigo_kyodo: App.settings.app.cd_category.kbn_haigo_kyodo,
                // kbn_hin
                kbn_haigo: App.settings.app.kbnHin.haigo,
                kbn_genryo: App.settings.app.kbnHin.genryo,
                kbn_maeshori: App.settings.app.kbnHin.maeshoriGenryo,
                kbn_shikakari: App.settings.app.kbnHin.shikakari,
                kbn_sagyo: App.settings.app.kbnHin.sagyo,
                // kbn_tani
                kbn_tani_kg: App.settings.app.taniShiyo.kg,
                kbn_tani_l: App.settings.app.taniShiyo.l,
                kbn_gam: App.settings.app.taniShiyo.g,
                // kbn_kotei
                kbn_pt_kotei: App.settings.app.pt_kotei.chomieki_2,         //"002"
                kbn_chomi: App.settings.app.zoku_kotei.sakkinchomi_eki,     //"001",
                kbn_suisho: App.settings.app.zoku_kotei.eki_suiso2,         ///"006",
                kbn_yusho: App.settings.app.zoku_kotei.eki_yu_sho2,         //"003"
                cd_tani_g: App.settings.app.cd_tani.gram,                   //"002"
                // kbn_shikakari
                kbn_shikakari_kaihatsu: App.settings.app.kbn_shikakari.kaihatsu,
                kbn_shikakari_FP: App.settings.app.kbn_shikakari.foodprocs,
                // Tenkai loop count
                loop_count: App.settings.app.tenkaiLoopCount,
                // pt_kotei
                pt_kotei_chomieki_2: App.settings.app.pt_kotei.chomieki_2,
                pt_kotei_sonohokaeki: App.settings.app.pt_kotei.sonohokaeki,
                // flgl_mishiyo
                flg_mishiyo: "true",
                // su_code_standard
                su_code_standard: page.values.su_code_standard,
                // Login info
                loginCD: App.ui.page.user.EmployeeCD,
                nm_login: App.ui.page.user.Name,
                nm_busho_login: App.ui.page.user.nm_busho,
                isDeleteExcel: false
            }
        }

        /**
        * get bunsho excel filter
        */
        page.header.getHaigoSakuseiFilter = function (no_seiho_exc) {
            var element = page.header.element;
            return {
                no_seiho: no_seiho_exc,
                cd_kaisha: page.values.cd_kaisha,
                cd_kojyo: page.values.cd_kojyo,
                cd_haigo: element.findP("cd_haigo").val(),
                no_han: element.findP("no_han").val(),
                M_kirikae: page.values.M_kirikae
            }
        }

        /**
        * Download excel file content the haigo list like TAB 10 in page [300]
        */
        page.header.downloadHaigoListExcel = function (mode) {
            var element = page.header.element,
                no_seiho_exc = element.findP("no_seiho_kaisha").val() + "-"
                               + element.findP("no_seiho_shurui").val() + "-"
                               + element.findP("no_seiho_nen").val() + "-"
                               + element.findP("no_seiho_renban").val();
            var excelFilter = page.header.getBunshoExcelFilter(no_seiho_exc);
            var url = page.urls.excelHaigoList;
            if (mode == 2) {
                excelFilter = page.header.getHaigoSakuseiFilter(no_seiho_exc);
                url = page.urls.excelHaigoSakusei;
            }
            // CSV出力（Ajax通信でファイルstreamを返却）
            return $.ajax(App.ajax.file.download(url, excelFilter, "POST")
            ).then(function (response, status, xhr) {
                if (status !== "success") {
                    App.ui.page.notifyAlert.message(App.messages.base.MS0005).show();
                } else {
                    App.file.save(response, App.ajax.file.extractFileNameDownload(xhr) || "ExcelFile.csv");
                }
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

            }).always(function () {
                App.ui.loading.close();
            });
        }

        //TODO-END: 以下の page.header の各宣言は画面仕様ごとにことなるため、不要の場合は削除してください。

        /**
         * 画面明細のバリデーションを定義します。
         */
        page.detail.options.validations = {
            //TODO: 画面明細のバリデーションの定義を記述します。

            kbn_bunkatsu: {
                rules: {
                    digits: true,
                    maxlength: 2
                },
                options: {
                    name: "分割"
                },
                messages: {
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength
                }
            },

            cd_hin: {
                rules: {
                    checkErrorRow: function (value, opts, state, done) {
                        if (state && state.tbody) {
                            var tbody = state.tbody,
                                cd_hin = tbody.findP("cd_hin").val();
                            if (!tbody.hasClass("exist-check")) {
                                return done(true);
                            }
                            if (tbody.hasClass("row-error-past") && (Number(cd_hin) != 0 || !($(page.detail.element.findP("flg_hinkan")[1]).prop("checked")))) {
                                return done(false);
                            }
                        }
                        return done(true);
                    },
                    required: true,
                    digits: true,
                    maxlength: 7,
                    checkExistsHimmei: function (value, opts, state, done) {
                        if (state && state.check) {
                            if (value >= page.values.shijiMin && value <= page.values.shijiMax) {
                                return done(true);
                            }
                            var tbody = state.tbody.element ? state.tbody.element : state.tbody

                            if (tbody.hasClass("row-error-past")) {
                                return done(true);
                            }

                            return done(tbody.findP("cd_tani_hin").text() != "");
                        }
                        return done(true);
                    },
                    kengenError: function (value, opts, state, done) {
                        if (state && state.tbody) {
                            var tbody = state.tbody;
                            if (tbody.findP("cd_hin").hasClass("kengen-error")) {
                                return done(false);
                            }
                        }
                        return done(true);
                    },
                },
                options: {
                    name: "コード"
                },
                messages: {
                    checkErrorRow: App.messages.app.AP0013,
                    required: App.messages.base.required,
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength,
                    checkExistsHimmei: App.messages.app.AP0010,
                    kengenError: App.messages.app.AP0071
                }
            },

            nm_hin: {
                rules: {
                    required: true,
                    maxbytelength: 60
                },
                options: {
                    name: "品名/作業指示",
                    byte: 30
                },
                messages: {
                    required: App.messages.base.required,
                    maxbytelength: App.messages.base.maxbytelength
                }
            },

            nm_kotei: {
                rules: {
                    required: true,
                    maxbytelength: 60
                },
                options: {
                    name: "品名/作業指示",
                    byte: 30
                },
                messages: {
                    required: App.messages.base.required,
                    maxbytelength: App.messages.base.maxbytelength
                }
            },

            qty_haigo: {
                rules: {
                    requiredCustom: function (value, opts, state, done) {
                        var tbody = state.tbody.element ? state.tbody.element : state.tbody,
                            cd_mark = tbody.findP("cd_mark").text(),
                            ss_kbn_qty_haigo = page.checkExistsMark(cd_mark) ? page.values.ma_mark[cd_mark].ss_kbn_qty_haigo : null;

                        if (ss_kbn_qty_haigo == App.settings.app.kbn_nyuryoku.kano_hissu_chekku) {
                            if (tbody.findP("qty_haigo").val() == "") {
                                return done(false);
                            }
                        }
                        return done(true);
                    },
                    number: true,
                    range: [0, 99999999.999],
                    pointlength: [8, 3]
                },
                options: {
                    name: "配合重量"
                },
                messages: {
                    requiredCustom: App.messages.base.required,
                    number: App.messages.base.number,
                    range: App.messages.base.range,
                    pointlength: App.messages.base.pointlength
                }
            },

            qty_nisugata: {
                rules: {
                    requiredCustom: function (value, opts, state, done) {
                        var tbody = state.tbody.element ? state.tbody.element : state.tbody,
                            cd_mark = tbody.findP("cd_mark").text(),
                            ss_kbn_nisugata = page.checkExistsMark(cd_mark) ? page.values.ma_mark[cd_mark].ss_kbn_nisugata : null;

                        if (ss_kbn_nisugata == App.settings.app.kbn_nyuryoku.kano_hissu_chekku) {
                            if (tbody.findP("qty_nisugata").val() == "") {
                                return done(false);
                            }
                        }
                        return done(true);
                    },
                    number: true,
                    range: [0, 99999999.999],
                    pointlength: [8, 3]
                },
                options: {
                    name: "荷姿重量"
                },
                messages: {
                    requiredCustom: App.messages.base.required,
                    number: App.messages.base.number,
                    range: App.messages.base.range,
                    pointlength: App.messages.base.pointlength
                }
            },

            gosa: {
                rules: {
                    requiredCustom: function (value, opts, state, done) {
                        var tbody = state.tbody.element ? state.tbody.element : state.tbody,
                            cd_mark = tbody.findP("cd_mark").text(),
                            ss_kbn_gosa = page.checkExistsMark(cd_mark) ? page.values.ma_mark[cd_mark].ss_kbn_gosa : null;

                        if (ss_kbn_gosa == App.settings.app.kbn_nyuryoku.kano_hissu_chekku) {
                            if (tbody.findP("gosa").val() == "") {
                                return done(false);
                            }
                        }
                        return done(true);
                    },
                    number: true,
                    range: [0, 99.999],
                    pointlength: [2, 3]
                },
                options: {
                    name: "誤差"
                },
                messages: {
                    requiredCustom: App.messages.base.required,
                    number: App.messages.base.number,
                    range: App.messages.base.range,
                    pointlength: App.messages.base.pointlength
                }
            },

            budomari: {
                rules: {
                    requiredCustom: function (value, opts, state, done) {
                        var tbody = state.tbody.element ? state.tbody.element : state.tbody,
                            cd_mark = tbody.findP("cd_mark").text(),
                            ss_kbn_budomari = page.checkExistsMark(cd_mark) ? page.values.ma_mark[cd_mark].ss_kbn_budomari : null;

                        if (ss_kbn_budomari == App.settings.app.kbn_nyuryoku.kano_hissu_chekku) {
                            if (tbody.findP("budomari").val() == "") {
                                return done(false);
                            }
                        }
                        return done(true);
                    },
                    number: true,
                    range: [0, 999.99],
                    pointlength: [3, 2]
                },
                options: {
                    name: "歩留"
                },
                messages: {
                    requiredCustom: App.messages.base.required,
                    number: App.messages.base.number,
                    range: App.messages.base.range,
                    pointlength: App.messages.base.pointlength
                }
            },

            qty_haigo_kei_user: {
                rules: {
                    requiredCustom: function (value, opts, state, done) {
                        if (!$(page.detail.element.findP("rdo_shiage_user")[1]).prop("checked")) {
                            return done(true);
                        }
                        if ($(page.detail.element.findP("qty_haigo_kei_user")[1]).val() == "") {
                            return done(false);
                        }
                        return done(true);
                    },
                    numberCustom: true,
                    rangeCustom: [0, 99999999.999999],
                    pointlengthCustom: [8, 6]
                },
                options: {
                    name: "仕上量/指定/重量"
                },
                messages: {
                    requiredCustom: App.messages.base.required,
                    numberCustom: App.messages.base.number,
                    rangeCustom: App.messages.base.range,
                    pointlengthCustom: App.messages.base.pointlength
                }
            },

            biko: {
                rules: {
                    maxbytelength: 300
                },
                options: {
                    name: "メモ ",
                    byte: 150
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            }
        };

        /**
         * 画面明細の初期化処理を行います。
         */
        page.detail.initialize = function () {

            var element = $(".detail"),
                table = element.find(".datatable"),
                datatable = table.dataTable({
                    height: 100,
                    //列固定横スクロールにする場合は、下記3行をコメント解除
                    //fixedColumn: true,                //列固定の指定
                    //fixedColumns: 3,                  //固定位置を指定（左端を0としてカウント）
                    //innerWidth: 1200,                 //可動列の合計幅を指定
                    //TODO: データテーブルでソート機能を利用しない場合はfalseを指定します。
                    sortable: true,
                    resize: true,
                    onselect: page.detail.select,
                    onchange: page.detail.change
                });

            table = element.find(".datatable"); //列固定にした場合DOM要素が再作成されるため、変数を再取得

            page.detail.validator = element.validation(page.createValidator(page.detail.options.validations));
            page.detail.element = element;
            page.detail.dataTable = datatable;

            element.on("click", "#add-kotei", page.detail.addNewKotei);
            element.on("click", "#add-tonyu", page.detail.addNewTonyu);
            element.on("click", "#del-kotei", page.detail.deleteKotei);
            element.on("click", "#del-tonyu", page.detail.deleteTonyu);
            element.on("click", "#move-kotei-up", page.detail.moveKoteiUp);
            element.on("click", "#move-kotei-down", page.detail.moveKoteiDown);
            element.on("click", "#move-tonyu-up", page.detail.moveTonyuUp);
            element.on("click", "#move-tonyu-down", page.detail.moveTonyuDown);

            element.on("focus", ".format-when-focus", page.detail.focusIn);
            element.on("focusout", ".format-when-focus", page.detail.focusOut);

            element.on("click", ".cd_hin", page.detail.showHinmeiKaihatsuDialog);
            element.on("click", ".mark", page.detail.showMarkDialog);
            element.on("click", ".hyperlink", page.detail.moveToHaigoTorokuKojyoBumon);

            // 行選択時に利用するテーブルインデックスを指定します
            page.detail.fixedColumnIndex = element.find(".fix-columns").length;

            page.detail.element.findP("qty_haigo_kei_user").change(function (e) {
                var target = $(e.target);
                $("#save").prop("disabled", false);
                page.detail.validator.validate({
                    targets: target
                }).then(function () {
                    var valOld = target.val().replace(/,/g, ""),
                        valNew = App.num.format(Number(valOld), "#,0.000000");
                    target.val(valNew);

                    var id = page.header.element.attr("data-key"),
                        entity = page.header.data.entry(id);
                    entity["qty_haigo_kei"] = valOld;
                    page.header.data.update(entity);
                })
            })

            page.detail.element.find("input[type='radio']").change(function (e) {
                var target = $(e.target),
                    property = target.attr("data-prop"),
                    id = page.header.element.attr("data-key"),
                    entity = page.header.data.entry(id);
                $("#save").prop("disabled", false);

                if (property == "rdo_shiage_user") {
                    $(page.detail.element.findP("qty_haigo_kei_user")[1]).prop("disabled", false);
                    entity["kbn_shiagari"] = true;
                }
                else {
                    var qty_haigo_kei = $(page.detail.element.findP("qty_haigo_kei")[1]).text();
                    $(page.detail.element.findP("qty_haigo_kei_user")[1]).prop("disabled", true)
                    $(page.detail.element.findP("qty_haigo_kei_user")[1]).val(qty_haigo_kei);
                    entity["kbn_shiagari"] = false;
                    entity["qty_haigo_kei"] = qty_haigo_kei.replace(/,/g, "");
                    page.detail.validator.validate({
                        targets: $(page.detail.element.findP("qty_haigo_kei_user")[1])
                    })
                }
                page.header.data.update(entity);
            })

            page.detail.element.findP("biko").change(function (e) {
                var target = $(e.target);

                $("#save").prop("disabled", false);

                page.detail.validator.validate({
                    targets: target
                }).then(function () {
                    var id = page.header.element.attr("data-key"),
                        entity = page.header.data.entry(id);
                    entity["biko"] = target.val() == "" ? null : target.val();
                    page.header.data.update(entity);
                })
            })

            page.detail.element.findP("flg_hinkan").change(function (e) {
                var target = $(e.target);

                $("#save").prop("disabled", false);

                page.detail.validator.validate({
                    targets: target
                }).then(function () {
                    var id = page.header.element.attr("data-key"),
                        entity = page.header.data.entry(id);

                    if (target.prop("checked")) {
                        entity["flg_hinkan"] = true;
                    }
                    else {
                        entity["flg_hinkan"] = false;
                    }
                    page.header.data.update(entity);
                })
            })

            page.detail.element.findP("flg_seizo").change(function (e) {
                var target = $(e.target);

                $("#save").prop("disabled", false);

                page.detail.validator.validate({
                    targets: target
                }).then(function () {
                    var id = page.header.element.attr("data-key"),
                        entity = page.header.data.entry(id);

                    if (target.prop("checked")) {
                        entity["flg_seizo"] = true;
                    }
                    else {
                        entity["flg_seizo"] = false;
                    }
                    page.header.data.update(entity);
                })
            })

            //TODO: 画面明細の初期化処理をここに記述します。

            //TODO: 画面明細で利用するコントロールのイベントの紐づけ処理をここに記述します。
        };

        /**
         * Calculate qty_haigo_kei
         */
        page.detail.calculateQtyHaigoKei = function (isNotUpdateHeader) {
            var qty_haigo_kei = 0;
            if (page.header.element.findP("rdoVWKbnKg").prop("checked")) {
                var rows = page.detail.element.find(".new");
                for (var i = 0 ; i < rows.length; i++) {
                    var row = $(rows[i]),
                        id = row.attr("data-key"),
                        entity = page.detail.data.entry(id),
                        cd_tani = row.findP("cd_tani").text();
                    
                    if (cd_tani == App.settings.app.taniShiyo.kg || cd_tani == App.settings.app.taniShiyo.g) {

                        var qty_haigo = Number(row.findP("qty_haigo").val().replace(/,/g, ""));
                        if (cd_tani == App.settings.app.taniShiyo.g) {
                            qty_haigo = new BigNumber(qty_haigo).div(1000).toNumber();
                        }

                        qty_haigo_kei = new BigNumber(qty_haigo_kei).plus(qty_haigo).toNumber();
                    }
                    else {
                        var qty_haigo = Number(row.findP("qty_haigo").val().replace(/,/g, "")),
                            hijyu = Number(row.findP("hijyu").text());
                        if (hijyu == 0) {
                            hijyu = 1;
                        }
                        qty_haigo = Math.round((new BigNumber(qty_haigo).times(hijyu).toNumber())*1000000)/1000000;
                        if (cd_tani == App.settings.app.taniShiyo.ml) {
                            qty_haigo = new BigNumber(qty_haigo).div(1000).toNumber();
                        }
                        qty_haigo_kei = new BigNumber(qty_haigo_kei).plus(qty_haigo).toNumber();
                    }
                    
                }
                $(page.detail.element.findP("qty_haigo_kei")[1]).text(App.num.format(qty_haigo_kei, "#,0.000000"))
            }
            else {
                var rows = page.detail.element.find(".new");
                for (var i = 0 ; i < rows.length; i++) {
                    var row = $(rows[i]),
                        id = row.attr("data-key"),
                        entity = page.detail.data.entry(id),
                        cd_tani = row.findP("cd_tani").text();
                    
                    if (cd_tani == App.settings.app.taniShiyo.l || cd_tani == App.settings.app.taniShiyo.ml) {

                        var qty_haigo = Number(row.findP("qty_haigo").val().replace(/,/g, ""));

                        if (cd_tani == App.settings.app.taniShiyo.ml) {
                            qty_haigo = new BigNumber(qty_haigo).div(1000).toNumber();
                        }

                        qty_haigo_kei = new BigNumber(qty_haigo_kei).plus(qty_haigo).toNumber();
                    }
                    else {
                        var qty_haigo = Number(row.findP("qty_haigo").val().replace(/,/g, "")),
                            hijyu = Number(row.findP("hijyu").text());
                        if (hijyu == 0) {
                            hijyu = 1;
                        }

                        qty_haigo = Math.round((new BigNumber(qty_haigo).div(hijyu).toNumber())*1000000)/1000000;

                        if (cd_tani == App.settings.app.taniShiyo.g) {
                            qty_haigo = new BigNumber(qty_haigo).div(1000).toNumber();
                        }
                        qty_haigo_kei = new BigNumber(qty_haigo_kei).plus(qty_haigo).toNumber();
                    }
                    
                }
                $(page.detail.element.findP("qty_haigo_kei")[1]).text(App.num.format(Number(qty_haigo_kei), "#,0.000000"))
            }

            if ($(page.detail.element.findP("rdo_shiage")[1]).prop("checked")) {
                $(page.detail.element.findP("qty_haigo_kei_user")[1]).val(App.num.format(Number(qty_haigo_kei), "#,0.000000"));
            }

            if (!isNotUpdateHeader) {
                var id = page.header.element.attr("data-key"),
                    entity = page.header.data.entry(id);
                entity["qty_haigo_kei"] = qty_haigo_kei;
                page.header.data.update(entity);
            }

        }

        /**
         * Focus in 
         */
        page.detail.focusIn = function (e) {
            var target = $(e.target),
                currentVal = target.val();
            target.val(currentVal.replace(/,/g, ""));
        }

        /**
         * Focus out 
         */
        page.detail.focusOut = function (e) {
            var target = $(e.target),
                row = target.closest("tbody"),
                property = target.attr("data-prop");
            page.detail.validator.validate({
                targets: target,
                state: {
                    tbody: row,
                    isGridValidation: true,
                    notSetStyle: true
                }

            }).then(function () {
                if (property == "qty_haigo" || property == "qty_nisugata") {
                    var valOld = target.val().replace(/,/g, "")
                    if (valOld != "") {;
                        var valNew = App.num.format(Number(valOld), "#,#.000");
                        target.val(valNew);
                    }
                }
                else if (property == "qty_haigo_kei_user") {
                    var valOld = target.val().replace(/,/g, "")
                    if (valOld != "") {;
                        var valNew = App.num.format(Number(valOld), "#,0.000000");
                        target.val(valNew);
                    }
                }
            })
        }

        /**
         * Set format hinmei code.
         */
        page.detail.setFormatHinmei = function (row, data) {

            var pad = "0000000000000",
                formatLength = 0,
                hinCodeFormat,
                kbn_hin = row.findP("kbn_hin").text();

            if (kbn_hin == App.settings.app.kbnHin.genryo || kbn_hin == App.settings.app.kbnHin.jikaGenryo) {
                formatLength = Number(page.values.su_code_standard);
            }
            else if (kbn_hin == App.settings.app.kbnHin.shikakari || kbn_hin == App.settings.app.kbnHin.maeshoriGenryo) {
                formatLength = Number(page.values.su_code_standard) + 1;
            }
            else {
                formatLength = (data.cd_hin.toString()).length;
            }

            //hinCodeFormat = App.num.format(Number(data.cd_hin), pad.substr(0, formatLength));
            hinCodeFormat = App.common.fillString(data.cd_hin, formatLength);
            row.findP("cd_hin").val(hinCodeFormat);
        }

        /**
         * 画面明細の一覧に新規データを追加します。（工程追加押下時）
         */
        page.detail.addNewKotei = function (e) {
            ////TODO:新規データおよび初期値を設定する処理を記述します。
            var row = page.detail.element.find(".datatable .select-tab.selected").closest("tbody"),
                countRow = page.detail.element.find(".new").length,
                idLast, maxKotei;

            if (countRow > 0) {
                idLast = $(page.detail.element.find(".new")[countRow - 1]).attr("data-key");
                maxKotei = parseInt(page.detail.data.entry(idLast)["no_kotei"], 10) + 1;
            }
            else {
                maxKotei = 1;
            }

            $("#save").prop("disabled", false);

            var newData = {
                //cd_haigo: page.header.element.findP("cd_haigo").text() == "" ? -1 : page.header.element.findP("cd_haigo").text(),
                no_kotei: maxKotei,
                no_tonyu: 1,
                cd_hin: null,
                kbn_shitei: 0,
                kbn_hin: null,
                nm_hin: null,
                cd_mark: App.settings.app.cd_mark.mark_0,
                qty_haigo: null,
                qty_nisugata: null,
                gosa: null,
                budomari: null,
                kbn_bunkatsu: 0
            }

            page.detail.data.add(newData);

            page.detail.dataTable.dataTable("addRow", function (tbody) {

                tbody.find(".kotei").remove();

                var item = {
                    __id: newData.__id,
                    kotei_tonyu: newData.no_kotei + "-" + newData.no_tonyu,
                    cd_mark: App.settings.app.cd_mark.mark_0
                }

                tbody.form(page.detail.options.bindOption).bind(item);

                if (App.isUndefOrNull(e) || e != "loadBegin") {
                    var mark = page.values.ma_mark[App.settings.app.cd_mark.mark_0];
                    page.detail.setControlByMark(tbody, mark, true);
                    page.detail.setTaniShiyo(tbody,true);
                }

                tbody.addClass("" + newData.no_kotei)
                tbody.addClass(newData.no_kotei + "-" + newData.no_tonyu)

                tbody.attr("kotei", newData.no_kotei);
                tbody.attr("tonyu", newData.no_tonyu)
                tbody.attr("kotei-tonyu", newData.no_kotei + "-" + newData.no_tonyu);

                return tbody;
            }, true);

        };

        /**
         * Move kotei up
         */
        page.detail.moveKoteiUp = function (e) {
            // 行が選択されていない場合は終了
            var targetRow = page.detail.element.find(".datatable .select-tab.selected").closest("tbody");

            App.ui.page.notifyAlert.remove("AP0012");

            if (!targetRow.length) {
                App.ui.page.notifyAlert.message(App.messages.app.AP0012, "AP0012").show();
                return;
            }

            var kotei = parseInt(targetRow.attr("kotei")),
                rows_all_kotei = page.detail.element.find("." + kotei),
                kotei_prev = kotei - 1,
                row_kotei_prev = page.detail.element.find("." + kotei_prev + "-" + 1),
                rows_all_kotei_prev = page.detail.element.find("." + kotei_prev);

            if (row_kotei_prev.length == 0) {
                return;
            }

            $("#save").prop("disabled", false);

            for (var i = 0 ; i < rows_all_kotei.length; i++) {
                var row = $(rows_all_kotei[i]),
                    tonyu = row.attr("tonyu");

                row_kotei_prev.before(row);

                //edit kotei class
                row.removeClass("" + kotei)
                row.addClass("" + (kotei - 1))

                //edit kotei-tonyu class
                row.removeClass(kotei + "-" + tonyu);
                row.addClass((kotei - 1) + "-" + tonyu);

                //edit kotei attribute
                row.attr("kotei", (kotei - 1));

                //edit kotei-tonyu attribute
                row.attr("kotei-tonyu", (kotei - 1) + "-" + tonyu);

                row.findP("kotei_tonyu").text((kotei - 1) + "-" + tonyu);

                var id = row.attr("data-key"),
                    entity = page.detail.data.entry(id);
                entity["no_kotei"] = kotei - 1;
                page.detail.data.update(entity);
            }

            for (var i = 0; i < rows_all_kotei_prev.length; i++) {
                var row = $(rows_all_kotei_prev[i]),
                    tonyu = row.attr("tonyu");

                //edit kotei class
                row.removeClass("" + kotei_prev)
                row.addClass("" + (kotei_prev + 1))

                //edit kotei-tonyu class
                row.removeClass(kotei_prev + "-" + tonyu);
                row.addClass((kotei_prev + 1) + "-" + tonyu);

                //edit kotei attribute
                row.attr("kotei", (kotei_prev + 1));

                //edit kotei-tonyu attribute
                row.attr("kotei-tonyu", (kotei_prev + 1) + "-" + tonyu);

                row.findP("kotei_tonyu").text((kotei_prev + 1) + "-" + tonyu);

                var id = row.attr("data-key"),
                    entity = page.detail.data.entry(id);
                entity["no_kotei"] = kotei_prev + 1;
                page.detail.data.update(entity);
            }
        }

        /**
         * Move kotei down
         */
        page.detail.moveKoteiDown = function (e) {
            // 行が選択されていない場合は終了
            var targetRow = page.detail.element.find(".datatable .select-tab.selected").closest("tbody");

            App.ui.page.notifyAlert.remove("AP0012");
            if (!targetRow.length) {
                App.ui.page.notifyAlert.message(App.messages.app.AP0012, "AP0012").show();
                return;
            }
            var row_kotei_next = page.detail.element.find("." + (parseInt(targetRow.attr("kotei")) + 1) + "-" + 1);

            if (row_kotei_next.length == 0) {
                return;
            }

            targetRow = row_kotei_next;

            var kotei = parseInt(targetRow.attr("kotei")),
                rows_all_kotei = page.detail.element.find("." + kotei),
                kotei_prev = kotei - 1,
                row_kotei_prev = page.detail.element.find("." + kotei_prev + "-" + 1),
                rows_all_kotei_prev = page.detail.element.find("." + kotei_prev);

            if (row_kotei_prev.length == 0) {
                return;
            }

            $("#save").prop("disabled", false);

            for (var i = 0 ; i < rows_all_kotei.length; i++) {
                var row = $(rows_all_kotei[i]),
                    tonyu = row.attr("tonyu");

                row_kotei_prev.before(row);

                //edit kotei class
                row.removeClass("" + kotei)
                row.addClass("" + (kotei - 1))

                //edit kotei-tonyu class
                row.removeClass(kotei + "-" + tonyu);
                row.addClass((kotei - 1) + "-" + tonyu);

                //edit kotei attribute
                row.attr("kotei", (kotei - 1));

                //edit kotei-tonyu attribute
                row.attr("kotei-tonyu", (kotei - 1) + "-" + tonyu);

                row.findP("kotei_tonyu").text((kotei - 1) + "-" + tonyu);

                var id = row.attr("data-key"),
                    entity = page.detail.data.entry(id);
                entity["no_kotei"] = kotei - 1;
                page.detail.data.update(entity);
            }

            for (var i = 0; i < rows_all_kotei_prev.length; i++) {
                var row = $(rows_all_kotei_prev[i]),
                    tonyu = row.attr("tonyu");

                //edit kotei class
                row.removeClass("" + kotei_prev)
                row.addClass("" + (kotei_prev + 1))

                //edit kotei-tonyu class
                row.removeClass(kotei_prev + "-" + tonyu);
                row.addClass((kotei_prev + 1) + "-" + tonyu);

                //edit kotei attribute
                row.attr("kotei", (kotei_prev + 1));

                //edit kotei-tonyu attribute
                row.attr("kotei-tonyu", (kotei_prev + 1) + "-" + tonyu);

                row.findP("kotei_tonyu").text((kotei_prev + 1) + "-" + tonyu);

                var id = row.attr("data-key"),
                    entity = page.detail.data.entry(id);
                entity["no_kotei"] = kotei_prev + 1;
                page.detail.data.update(entity)
            }
        }

        /**
         * Move tonyu up
         */
        page.detail.moveTonyuUp = function (e) {
            // 行が選択されていない場合は終了
            var targetRow = page.detail.element.find(".datatable .select-tab.selected").closest("tbody");

            App.ui.page.notifyAlert.remove("AP0008");
            App.ui.page.notifyAlert.remove("AP0015");
            App.ui.page.notifyAlert.remove("AP0072");

            if (!targetRow.length) {
                App.ui.page.notifyAlert.message(App.messages.app.AP0008, "AP0008").show();
                return;
            }

            if (page.values.kengen_kino_jisshi_kano) {
                var kbn_hin = targetRow.findP("kbn_hin").text(),
                    cd_tani_hin = targetRow.findP("cd_tani_hin").text();

                if (!(cd_tani_hin == "" || kbn_hin == App.settings.app.kbnHin.sagyo)) {
                    App.ui.page.notifyAlert.message(App.messages.app.AP0072, "AP0072").show();
                    return;
                }
                var pre = targetRow.prev();
                if (pre.length && !pre.hasClass("item-tmpl")) {
                    var kbn_hin_pre = pre.findP("kbn_hin").text(),
                        cd_tani_hin_pre = pre.findP("cd_tani_hin").text();
                    if (!(cd_tani_hin_pre == "" || kbn_hin_pre == App.settings.app.kbnHin.sagyo)) {
                        App.ui.page.notifyAlert.message(App.messages.app.AP0072, "AP0072").show();
                        return;
                    }
                }
            }

            var kotei = parseInt(targetRow.attr("kotei")),
                tonyu = parseInt(targetRow.attr("tonyu")),
                kotei_tonyu = kotei + "-" + tonyu;

            if (tonyu == 1) {
                var kotei_prev = kotei - 1;
                if (kotei_prev == 0) {
                    return;
                }

                var rowBefore = targetRow.prev(),
                    koteiBefore = parseInt(rowBefore.attr("kotei")),
                    tonyuBefore = parseInt(rowBefore.attr("tonyu"));

                if (page.detail.element.find(".new." + koteiBefore).length == page.values.maxNumberRowDetail) {
                    App.ui.page.notifyAlert.message(App.messages.app.AP0015, "AP0015").show();
                    return;
                }
                $("#save").prop("disabled", false);

                if (page.detail.element.find("." + kotei).length == 1) {
                    var newTonyuData = {
                            cd_haigo: page.header.element.findP("cd_haigo").text() == "" ? -1 : page.header.element.findP("cd_haigo").text(),
                            no_kotei: kotei,
                            no_tonyu: 1,
                            cd_hin: null,
                            kbn_shitei: 0,
                            kbn_hin: null,
                            nm_hin: null,
                            cd_mark: App.settings.app.cd_mark.mark_0,
                            qty_haigo: null,
                            qty_nisugata: null,
                            gosa: null,
                            budomari: null,
                            kbn_bunkatsu: 0
                        }
                    page.detail.data.add(newTonyuData);
                    page.detail.dataTable.dataTable("insertRow", targetRow, true, function (tbody) {
                        tbody.find(".kotei").remove();

                        var item = {
                            __id: newTonyuData.__id,
                            kotei_tonyu: newTonyuData.no_kotei + "-" + newTonyuData.no_tonyu,
                            cd_mark: App.settings.app.cd_mark.mark_0
                        }

                        tbody.form(page.detail.options.bindOption).bind(item);
                        var mark = page.values.ma_mark[App.settings.app.cd_mark.mark_0];
                        page.detail.setControlByMark(tbody, mark, true);
                        page.detail.setTaniShiyo(tbody,true);

                        tbody.addClass("" + newTonyuData.no_kotei)
                        tbody.addClass(newTonyuData.no_kotei + "-" + newTonyuData.no_tonyu)

                        tbody.attr("kotei", newTonyuData.no_kotei);
                        tbody.attr("tonyu", newTonyuData.no_tonyu)
                        tbody.attr("kotei-tonyu", newTonyuData.no_kotei + "-" + newTonyuData.no_tonyu);
                        return tbody;
                    }, false);
                }
                else {

                    var rowNext = targetRow.next();

                    for (; ;) {
                        var kotei_rowNext = parseInt(rowNext.attr("kotei"))
                        tonyu_rowNext = parseInt(rowNext.attr("tonyu"));

                        if (kotei_rowNext != kotei) {
                            break;
                        }

                        rowNext.removeClass(kotei_rowNext + "-" + tonyu_rowNext);
                        rowNext.addClass(kotei_rowNext + "-" + (tonyu_rowNext - 1));
                        rowNext.attr("tonyu", tonyu_rowNext - 1);
                        rowNext.attr("kotei-tonyu", kotei_rowNext + "-" + (tonyu_rowNext - 1));
                        rowNext.findP("kotei_tonyu").text(kotei_rowNext + "-" + (tonyu_rowNext - 1));

                        var id = rowNext.attr("data-key"),
                            entity = page.detail.data.entry(id);
                        entity["no_tonyu"] = tonyu_rowNext - 1;
                        page.detail.data.update(entity);

                        if (rowNext.next().not(".item-tmpl").length == 0) {
                            break;
                        }
                        rowNext = rowNext.next().not(".item-tmpl");
                    }

                }
                targetRow.removeClass("" + kotei);
                targetRow.removeClass(kotei_tonyu);
                targetRow.addClass("" + koteiBefore)
                targetRow.addClass("" + koteiBefore + "-" + (tonyuBefore + 1));
                targetRow.attr("kotei", koteiBefore);
                targetRow.attr("tonyu", (tonyuBefore + 1));
                targetRow.attr("kotei-tonyu", koteiBefore + "-" + (tonyuBefore + 1));
                targetRow.findP("kotei_tonyu").text(koteiBefore + "-" + (tonyuBefore + 1));

                rowBefore.after(targetRow);
                var id = targetRow.attr("data-key"),
                    entity = page.detail.data.entry(id);
                entity["no_kotei"] = koteiBefore;
                entity["no_tonyu"] = tonyuBefore + 1;
                page.detail.data.update(entity);

            }
            else {

                $("#save").prop("disabled", false);

                var rowBefore = targetRow.prev(),
                    koteiBefore = parseInt(rowBefore.attr("kotei")),
                    tonyuBefore = parseInt(rowBefore.attr("tonyu"));

                targetRow.removeClass(kotei_tonyu);
                targetRow.addClass("" + koteiBefore + "-" + tonyuBefore);
                targetRow.attr("tonyu", tonyuBefore);
                targetRow.attr("kotei-tonyu", koteiBefore + "-" + tonyuBefore);
                targetRow.findP("kotei_tonyu").text(koteiBefore + "-" + tonyuBefore);
                var id = targetRow.attr("data-key"),
                    entity = page.detail.data.entry(id);
                entity["no_tonyu"] = tonyuBefore;
                page.detail.data.update(entity);

                rowBefore.removeClass(koteiBefore + "-" + tonyuBefore);
                rowBefore.addClass("" + kotei_tonyu);
                rowBefore.attr("tonyu", tonyu);
                rowBefore.attr("kotei-tonyu", kotei_tonyu);
                rowBefore.findP("kotei_tonyu").text(kotei_tonyu);
                var idBefore = rowBefore.attr("data-key"),
                    entityBefore = page.detail.data.entry(idBefore);
                entityBefore["no_tonyu"] = tonyu;
                page.detail.data.update(entityBefore);

                rowBefore.before(targetRow);
            }
        }

        /**
         * Move tonyu down
         */
        page.detail.moveTonyuDown = function (e) {
            // 行が選択されていない場合は終了
            var targetRow = page.detail.element.find(".datatable .select-tab.selected").closest("tbody");

            App.ui.page.notifyAlert.remove("AP0008");
            App.ui.page.notifyAlert.remove("AP0015");
            App.ui.page.notifyAlert.remove("AP0072");
            if (!targetRow.length) {
                App.ui.page.notifyAlert.message(App.messages.app.AP0008, "AP0008").show();
                return;
            }

            if (page.values.kengen_kino_jisshi_kano) {
                var kbn_hin = targetRow.findP("kbn_hin").text(),
                    cd_tani_hin = targetRow.findP("cd_tani_hin").text();
                if (!(cd_tani_hin == "" || kbn_hin == App.settings.app.kbnHin.sagyo)) {
                    App.ui.page.notifyAlert.message(App.messages.app.AP0072, "AP0072").show();
                    return;
                }
                var next = targetRow.next();
                if (next.length && !next.hasClass("item-tmpl")) {
                    var kbn_hin_next = next.findP("kbn_hin").text(),
                        cd_tani_hin_next = next.findP("cd_tani_hin").text();
                    if (!(cd_tani_hin_next == "" || kbn_hin_next == App.settings.app.kbnHin.sagyo)) {
                        App.ui.page.notifyAlert.message(App.messages.app.AP0072, "AP0072").show();
                        return;
                    }
                }
            }

            var kotei = parseInt(targetRow.attr("kotei")),
                tonyu = parseInt(targetRow.attr("tonyu")),
                kotei_tonyu = kotei + "-" + tonyu;

            var rowNext = targetRow.next();
            if (rowNext.length) {
                var koteiNext = parseInt(rowNext.attr("kotei"));
                if (koteiNext != kotei) {
                    if (page.detail.element.find(".new." + koteiNext).length == page.values.maxNumberRowDetail) {
                        App.ui.page.notifyAlert.message(App.messages.app.AP0015, "AP0015").show();
                        return;
                    }

                    $("#save").prop("disabled", false);

                    var row = rowNext;
                    for (; ;) {
                        if (row.length == 0 || parseInt(row.attr("kotei")) != koteiNext) {
                            break;
                        }
                        var tonyuNext = parseInt(row.attr("tonyu"));
                        row.removeClass(koteiNext + "-" + tonyuNext);
                        row.addClass(koteiNext + "-" + (tonyuNext + 1));
                        row.attr("kotei-tonyu", koteiNext + "-" + (tonyuNext + 1));
                        row.attr("tonyu", (tonyuNext + 1))
                        row.findP("kotei_tonyu").text(koteiNext + "-" + (tonyuNext + 1));

                        var id = row.attr("data-key"),
                        entity = page.detail.data.entry(id);
                        entity["no_tonyu"] = tonyuNext + 1;
                        page.detail.data.update(entity);

                        row = row.next();
                    }
                    targetRow.removeClass("" + kotei);
                    targetRow.removeClass(kotei + "-" + tonyu);
                    targetRow.addClass("" + koteiNext);
                    targetRow.addClass(koteiNext + "-" + "1");
                    targetRow.attr("kotei", koteiNext);
                    targetRow.attr("tonyu", "1");
                    targetRow.attr("kotei-tonyu", koteiNext + "-" + "1");
                    targetRow.findP("kotei_tonyu").text(koteiNext + "-" + "1");

                    var id = targetRow.attr("data-key"),
                        entity = page.detail.data.entry(id);
                    entity["no_kotei"] = koteiNext;
                    entity["no_tonyu"] = 1;
                    page.detail.data.update(entity);
                    

                    if (page.detail.element.find("." + kotei).length == 0) {
                        var newTonyuData = {
                                cd_haigo: page.header.element.findP("cd_haigo").text() == "" ? -1 : page.header.element.findP("cd_haigo").text(),
                                no_kotei: kotei,
                                no_tonyu: 1,
                                cd_hin: null,
                                kbn_shitei: 0,
                                kbn_hin: null,
                                nm_hin: null,
                                cd_mark: App.settings.app.cd_mark.mark_0,
                                qty_haigo: null,
                                qty_nisugata: null,
                                gosa: null,
                                budomari: null,
                                kbn_bunkatsu: 0
                            }
                        page.detail.data.add(newTonyuData);
                        page.detail.dataTable.dataTable("insertRow", targetRow, false, function (tbody) {
                            tbody.find(".kotei").remove();

                            var item = {
                                __id: newTonyuData.__id,
                                kotei_tonyu: newTonyuData.no_kotei + "-" + newTonyuData.no_tonyu,
                                cd_mark: App.settings.app.cd_mark.mark_0
                            }

                            tbody.form(page.detail.options.bindOption).bind(item);
                            var mark = page.values.ma_mark[App.settings.app.cd_mark.mark_0];
                            page.detail.setControlByMark(tbody, mark, true);
                            page.detail.setTaniShiyo(tbody,true);

                            tbody.addClass("" + newTonyuData.no_kotei)
                            tbody.addClass(newTonyuData.no_kotei + "-" + newTonyuData.no_tonyu)

                            tbody.attr("kotei", newTonyuData.no_kotei);
                            tbody.attr("tonyu", newTonyuData.no_tonyu)
                            tbody.attr("kotei-tonyu", newTonyuData.no_kotei + "-" + newTonyuData.no_tonyu);

                            return tbody;
                        }, false);
                    }
                } else {

                    $("#save").prop("disabled", false);

                    rowNext.after(targetRow);
                    var tonyuNext = parseInt(rowNext.attr("tonyu"));
                    rowNext.removeClass(kotei + "-" + tonyuNext);
                    rowNext.addClass(kotei + "-" + tonyu);
                    rowNext.attr("tonyu", tonyu);
                    rowNext.attr("kotei-tonyu", kotei + "-" + tonyu);
                    rowNext.findP("kotei_tonyu").text(kotei + "-" + tonyu);

                    var idRowNext = rowNext.attr("data-key"),
                        entityRowNext = page.detail.data.entry(idRowNext);
                    entityRowNext["no_tonyu"] = tonyu;

                    targetRow.removeClass(kotei + "-" + tonyu);
                    targetRow.addClass(kotei + "-" + tonyuNext);
                    targetRow.attr("tonyu", tonyuNext);
                    targetRow.attr("kotei-tonyu", kotei + "-" + tonyuNext);
                    targetRow.findP("kotei_tonyu").text(kotei + "-" + tonyuNext);

                    var id = targetRow.attr("data-key"),
                        entity = page.detail.data.entry(id);
                    entity["no_tonyu"] = tonyuNext;
                    page.detail.data.update(entity);
                    if (entityRowNext) {
                        page.detail.data.update(entityRowNext);
                    }
                }
            }
        }


        /**
         * 画面明細の一覧に新規データを追加します。（行追加押下時）
         */
        page.detail.addNewTonyu = function () {
            var element = page.detail.element,
                selected = element.find(".datatable .select-tab.selected").closest("tbody"),
                rowInsert;

            App.ui.page.notifyAlert.remove("AP0008");
            App.ui.page.notifyAlert.remove("AP0015");

            if (!selected.length) {
                // 選択行が無ければこれまでどおり追加
                App.ui.page.notifyAlert.message(App.messages.app.AP0008, "AP0008").show();
                return;
            }

            var kotei = parseInt(selected.attr("kotei")),
                tonyu = parseInt(selected.attr("tonyu")) + 1;

            if (page.detail.element.find(".new." + kotei).length == page.values.maxNumberRowDetail) {
                App.ui.page.notifyAlert.message(App.messages.app.AP0015, "AP0015").show();
                return;
            }

            $("#save").prop("disabled", false);

            var newTonyuData = {
                cd_haigo: page.header.element.findP("cd_haigo").text() == "" ? -1 : page.header.element.findP("cd_haigo").text(),
                no_kotei: kotei,
                no_tonyu: tonyu,
                cd_hin: null,
                kbn_shitei: 0,
                kbn_hin: null,
                nm_hin: null,
                cd_mark: App.settings.app.cd_mark.mark_0,
                qty_haigo: null,
                qty_nisugata: null,
                gosa: null,
                budomari: null,
                kbn_bunkatsu: 0
            }

            page.detail.data.add(newTonyuData);

            page.detail.dataTable.dataTable("insertRow", selected, true, function (tbody) {

                tbody.find(".kotei").remove();
                var item = {
                    __id: newTonyuData.__id,
                    kotei_tonyu: newTonyuData.no_kotei + "-" + newTonyuData.no_tonyu,
                    cd_mark: App.settings.app.cd_mark.mark_0
                }
                tbody.form(page.detail.options.bindOption).bind(item);
                var mark = page.values.ma_mark[App.settings.app.cd_mark.mark_0];
                page.detail.setControlByMark(tbody, mark, true);
                page.detail.setTaniShiyo(tbody,true);

                tbody.addClass("" + kotei);
                tbody.addClass(kotei + "-" + tonyu);
                tbody.attr("kotei", kotei);
                tbody.attr("tonyu", tonyu);
                tbody.attr("kotei-tonyu", kotei + '-' + tonyu);

                rowInsert = tbody

                return tbody;
            }, true);

            for (; ;) {
                var tbody_next = rowInsert.next();
                if (tbody_next.length == 0) {
                    break;
                }

                if (tbody_next.attr("kotei") != kotei) {
                    break;
                }

                var tonyu_next = parseInt(tbody_next.attr("tonyu"));
                tbody_next.removeClass(kotei + "-" + tonyu_next);
                tbody_next.addClass(kotei + "-" + (tonyu_next + 1));

                tbody_next.attr("tonyu", tonyu_next + 1);
                tbody_next.attr("kotei-tonyu", kotei + "-" + (tonyu_next + 1));

                tbody_next.findP("kotei_tonyu").text(kotei + "-" + (tonyu_next + 1));

                var id = tbody_next.attr("data-key"),
                    entity = page.detail.data.entry(id);
                entity["no_tonyu"] = tonyu_next + 1;
                page.detail.data.update(entity);

                rowInsert = tbody_next;
            }
        };

        /**
         * Delete kotei
         */
        page.detail.deleteKotei = function (e) {
            var element = page.detail.element,
                selected = element.find(".datatable .select-tab.selected").closest("tbody");

            App.ui.page.notifyAlert.remove("AP0012");
            App.ui.page.notifyAlert.remove("AP0146");

            if (!selected.length) {
                App.ui.page.notifyAlert.message(App.messages.app.AP0012, "AP0012").show();
                return;
            }

            var kotei = parseInt(selected.attr("kotei")),
                selectorsKotei = page.detail.element.find("." + kotei);
            if (selectorsKotei.find(":checked").length) {
                App.ui.page.notifyAlert.message(App.messages.app.AP0146, "AP0146").show();
                return;
            }

            $("#save").prop("disabled", false);

            page.dialogs.confirmDialog.confirm({ text: App.messages.app.AP0011 }).then(function () {

                selectorsKotei.each(function (i, selectorKotei) {
                    page.detail.dataTable.dataTable("deleteRow", selectorKotei, function (row) {
                        var id = row.attr("data-key"),
                            newSelected;

                        row.find(":input").each(function (i, elem) {
                            App.ui.page.notifyAlert.remove(elem);
                        });

                        if (!App.isUndefOrNull(id)) {
                            var entity = page.detail.data.entry(id);
                            page.detail.data.remove(entity);
                        }
                    });
                });

                if (page.detail.element.find(".new").length == 0) {
                    page.detail.addNewKotei();
                    page.detail.calculateQtyHaigoKei();
                    return;
                }
                else {
                    var nextKotei = page.detail.element.find("." + (kotei + 1) + "-1");
                    if (nextKotei.length) {

                        if (nextKotei.find(":focusable:first").length) {
                            nextKotei.find(":focusable:first").focus();
                        }
                        else {
                            var prevKotei = page.detail.element.find("." + (kotei - 1) + "-1");
                            if (prevKotei.find(":focusable:first").length) {
                                prevKotei.find(":focusable:first").focus();

                            }
                        }
                    }
                    else {
                        var prevKotei = page.detail.element.find("." + (kotei - 1) + "-1");
                        if (prevKotei.find(":focusable:first").length) {
                            prevKotei.find(":focusable:first").focus();

                        }
                    }
                }

                for (var i = kotei + 1 ; ; i++) {
                    var rows = page.detail.element.find("." + i)
                    if (rows.length == 0) {
                        break;
                    }

                    rows.each(function (index, row) {
                        var tonyu = $(row).attr("tonyu");

                        //edit kotei class
                        $(row).removeClass("" + i)
                        $(row).addClass("" + (i - 1))

                        //edit kotei-tonyu class
                        $(row).removeClass(i + "-" + tonyu);
                        $(row).addClass((i - 1) + "-" + tonyu);

                        //edit kotei attribute
                        $(row).attr("kotei", (i - 1));

                        //edit kotei-tonyu attribute
                        $(row).attr("kotei-tonyu", (i - 1) + "-" + tonyu);

                        $(row).findP("kotei_tonyu").text((i - 1) + "-" + tonyu);

                        var id = $(row).attr("data-key"),
                        entity = page.detail.data.entry(id);
                        entity["no_kotei"] = i - 1;
                        page.detail.data.update(entity);
                    });
                }
                page.detail.calculateQtyHaigoKei();
            })
        };


        /**
         * delete Tonyu
         */
        page.detail.deleteTonyu = function (e) {
            var element = page.detail.element,
                selected = element.find(".datatable .select-tab.selected").closest("tbody");

            App.ui.page.notifyAlert.remove("AP0008");
            App.ui.page.notifyAlert.remove("AP0146");
            App.ui.page.notifyAlert.remove("AP0073");

            if (!selected.length) {
                App.ui.page.notifyAlert.message(App.messages.app.AP0008, "AP0008").show();
                return;
            }

            if (selected.findP("kbn_shitei").prop("checked")) {
                App.ui.page.notifyAlert.message(App.messages.app.AP0146, "AP0146").show();
                return;
            }

            if (page.values.kengen_kino_jisshi_kano) {
                kbn_hin = selected.findP("kbn_hin").text(),
                cd_tani_hin = selected.findP("cd_tani_hin").text();
                if (!(cd_tani_hin == "" || kbn_hin == App.settings.app.kbnHin.sagyo)) {
                    App.ui.page.notifyAlert.message(App.messages.app.AP0073, "AP0073").show();
                    return;
                }
            }

            var kotei = parseInt(selected.attr("kotei")),
                kotei_tonyu = selected.attr("kotei-tonyu");

            $("#save").prop("disabled", false);

            if (page.detail.element.find("." + kotei).length == 1) {
                var row_temp = selected.prev(),
                    isInsert = true;

                if (row_temp.length == 0 || row_temp.hasClass("item-tmpl")) {
                    isInsert = false;
                    row_temp = selected.next();
                }
                page.detail.dataTable.dataTable("deleteRow", selected, function (row) {
                    var id = row.attr("data-key"),
                        newSelected;

                    row.find(":input").each(function (i, elem) {
                        App.ui.page.notifyAlert.remove(elem);
                    });

                    if (!App.isUndefOrNull(id)) {
                        var entity = page.detail.data.entry(id);
                        page.detail.data.remove(entity);
                    }
                });

                if (page.detail.element.find(".new").length == 0) {
                    page.detail.addNewKotei();
                    return;
                }

                var newTonyuData = {
                    cd_haigo: page.header.element.findP("cd_haigo").text() == "" ? -1 : page.header.element.findP("cd_haigo").text(),
                    no_kotei: kotei,
                    no_tonyu: 1,
                    cd_hin: null,
                    kbn_shitei: 0,
                    kbn_hin: null,
                    nm_hin: null,
                    cd_mark: App.settings.app.cd_mark.mark_0,
                    qty_haigo: null,
                    qty_nisugata: null,
                    gosa: null,
                    budomari: null,
                    kbn_bunkatsu: 0
                }

                page.detail.data.add(newTonyuData);

                page.detail.dataTable.dataTable("insertRow", row_temp, isInsert, function (tbody) {

                    tbody.find(".kotei").remove();

                    var item = {
                        __id: newTonyuData.__id,
                        kotei_tonyu: newTonyuData.no_kotei + "-" + newTonyuData.no_tonyu,
                        cd_mark: App.settings.app.cd_mark.mark_0
                    }

                    tbody.form(page.detail.options.bindOption).bind(item);
                    var mark = page.values.ma_mark[App.settings.app.cd_mark.mark_0];
                    page.detail.setControlByMark(tbody, mark, true);
                    page.detail.setTaniShiyo(tbody,true);

                    tbody.addClass("" + newTonyuData.no_kotei)
                    tbody.addClass(newTonyuData.no_kotei + "-" + newTonyuData.no_tonyu)

                    tbody.attr("kotei", newTonyuData.no_kotei);
                    tbody.attr("tonyu", newTonyuData.no_tonyu)
                    tbody.attr("kotei-tonyu", newTonyuData.no_kotei + "-" + newTonyuData.no_tonyu);
                    return tbody;
                }, true);
                page.detail.calculateQtyHaigoKei();
                return;
            }

            page.detail.dataTable.dataTable("deleteRow", selected, function (row) {
                var id = row.attr("data-key"),
                    newSelected;

                row.find(":input").each(function (i, elem) {
                    App.ui.page.notifyAlert.remove(elem);
                });

                if (!App.isUndefOrNull(id)) {
                    var entity = page.detail.data.entry(id);
                    page.detail.data.remove(entity);
                }

                rowFocus = row.next().not(".item-tmpl");
                if (!rowFocus.length) {
                    rowFocus = row.prev().not(".item-tmpl");
                }
                
                var newSelected = null;
                var oldSelected = selected.prev().not(".item-tmpl");
                var tonyu_Selected = 1;
                var tonyu_newSelected = tonyu_Selected;
                for (; ;) {
                    if (!newSelected) {
                        newSelected = page.detail.element.find(".dt-body tbody:not(.item-tmpl):first");
                        if (newSelected.length == 0) {
                            break;
                        }
                    } else {
                        if (newSelected.next().not(".item-tmpl").length == 0) {
                            break;
                        }
                        newSelected = newSelected.next().not(".item-tmpl");
                    }
                    var kotei_newSelected = parseInt(newSelected.attr("kotei"));
                    var tonyu_newSelected = parseInt(newSelected.attr("tonyu"));
                    if (kotei_newSelected != kotei) {
                        continue;
                    }
                    if (newSelected.attr("data-key") === selected.attr("data-key")) {
                        continue;
                    }
                    if (tonyu_Selected === tonyu_newSelected) {
                        tonyu_Selected += 1;
                        continue;
                    }
                    newSelected.removeClass(kotei_newSelected + "-" + tonyu_newSelected);
                    newSelected.addClass(kotei_newSelected + "-" + tonyu_Selected);
                    newSelected.attr("tonyu", tonyu_Selected);
                    newSelected.attr("kotei-tonyu", kotei_newSelected + "-" + tonyu_Selected);
                    newSelected.findP("kotei_tonyu").text(kotei_newSelected + "-" + tonyu_Selected);

                    var id = newSelected.attr("data-key"),
                        entity = page.detail.data.entry(id);
                    entity["no_tonyu"] = tonyu_Selected;

                    page.detail.data.update(entity);

                    tonyu_Selected += 1;
                }


                //for (; ;) {
                //    var kotei_newSelected = parseInt(newSelected.attr("kotei"));
                //    var tonyu_newSelected = parseInt(newSelected.attr("tonyu"));
                        
                //    if (kotei_newSelected != kotei) {
                //        break;
                //    }

                //    newSelected.removeClass(kotei_newSelected + "-" + tonyu_newSelected);
                //    newSelected.addClass(kotei_newSelected + "-" + tonyu_Selected);
                //    newSelected.attr("tonyu", tonyu_Selected);
                //    newSelected.attr("kotei-tonyu", kotei_newSelected + "-" + tonyu_Selected);
                //    newSelected.findP("kotei_tonyu").text(kotei_newSelected + "-" + tonyu_Selected);

                //    var id = newSelected.attr("data-key"),
                //        entity = page.detail.data.entry(id);
                //    entity["no_tonyu"] = tonyu_Selected;

                //    page.detail.data.update(entity);

                //    if (newSelected.next().not(".item-tmpl").length == 0) {
                //        break;
                //    }
                //    newSelected = newSelected.next().not(".item-tmpl");

                //    tonyu_Selected += 1;
                //}



                if (rowFocus.length) {
                    for (var i = page.detail.fixedColumnIndex; i > -1; i--) {
                        if ($(rowFocus[i]).find(":focusable:first").length) {
                            $(rowFocus[i]).find(":focusable:first").focus();
                            break;
                        }
                    }
                }
            });

            page.detail.calculateQtyHaigoKei();
        }

        /**
         * Set status control by mark
         */
        page.detail.setControlByMark = function (tbody, data, isNotUpdate) {

            var id, entity = {},
                flg_change = false;
            try{
                id = tbody.attr("data-key"),
                entity = page.detail.data.entry(id);
            }
            catch(e){
            }

            if (!data) {
                tbody.findP("qty_haigo").prop("disabled", true);
                tbody.findP("qty_haigo").val("");
                tbody.findP("qty_nisugata").prop("disabled", true);
                tbody.findP("qty_nisugata").val("");
                tbody.findP("budomari").prop("disabled", true);
                tbody.findP("budomari").val("");
                tbody.findP("gosa").prop("disabled", true);
                tbody.findP("gosa").val("");
                return;
            }


            //check qty_haigo
            var ss_kbn_qty_haigo = data.ss_kbn_qty_haigo,
                qty_haigo = tbody.findP("qty_haigo");

            if (ss_kbn_qty_haigo == App.settings.app.kbn_nyuryoku.fuka) {
                qty_haigo.prop("disabled", true);
                qty_haigo.val("");
                if (!App.isUndefOrNull(entity["qty_haigo"]) && entity["qty_haigo"] != 0) {
                    flg_change = true;
                }
                entity["qty_haigo"] = null;
            }
            else if (ss_kbn_qty_haigo == 1) {
                if (!page.values.allDisabledInput) {
                    qty_haigo.prop("disabled", false);
                }
            }
            else {
                if (!page.values.allDisabledInput) {
                    qty_haigo.prop("disabled", false);
                }
            }

            //check qty_nisugata
            var ss_kbn_nisugata = data.ss_kbn_nisugata,
                qty_nisugata = tbody.findP("qty_nisugata");

            if (ss_kbn_nisugata == App.settings.app.kbn_nyuryoku.fuka) {
                qty_nisugata.prop("disabled", true);
                qty_nisugata.val("");
                if (!App.isUndefOrNull(entity["qty_nisugata"])) {
                    flg_change = true;
                }
                // Bug #16903　flg_mishiyo更新不具合対応  (2020-05-05) ST
                if (!isNotUpdate) {
                    entity["qty_nisugata"] = null;
                }
                // Bug #16903　flg_mishiyo更新不具合対応  (2020-05-05) ED
            }
            else if (ss_kbn_nisugata == App.settings.app.kbn_nyuryoku.kano) {
                if (!page.values.allDisabledInput) {
                    qty_nisugata.prop("disabled", false);
                }
            }
            else {
                if (!page.values.allDisabledInput) {
                    qty_nisugata.prop("disabled", false);
                }
            }

            //check gosa
            var ss_kbn_gosa = data.ss_kbn_gosa,
                gosa = tbody.findP("gosa");

            if (ss_kbn_gosa == App.settings.app.kbn_nyuryoku.fuka) {
                gosa.prop("disabled", true);
                gosa.val("");
                if (!App.isUndefOrNull(entity["gosa"]) && entity["gosa"] != 0) {
                    flg_change = true;
                }
                entity["gosa"] = null;
            }
            else if (ss_kbn_gosa == App.settings.app.kbn_nyuryoku.kano) {
                if (!page.values.allDisabledInput) {
                    gosa.prop("disabled", false);
                }
            }
            else {
                if (!page.values.allDisabledInput) {
                    gosa.prop("disabled", false);
                }
            }

            //check budomari
            var ss_kbn_budomari = data.ss_kbn_budomari,
                budomari = tbody.findP("budomari");

            if (ss_kbn_budomari == App.settings.app.kbn_nyuryoku.fuka) {
                budomari.prop("disabled", true);
                budomari.val("");
                if (!App.isUndefOrNull(entity["budomari"]) && entity["budomari"] != 100) {
                    flg_change = true;
                }
                entity["budomari"] = null;
            }
            else if (ss_kbn_budomari == App.settings.app.kbn_nyuryoku.kano) {
                if (!page.values.allDisabledInput) {
                    budomari.prop("disabled", false);
                }
            }
            else {
                if (!page.values.allDisabledInput) {
                    budomari.prop("disabled", false);
                    }
            }

            if (!isNotUpdate || flg_change) {
                page.detail.data.update(entity);
            }
        }

        /**
         * 画面明細の各行にデータを設定する際のオプションを定義します。
         */
        page.detail.options.bindOption = {
            appliers: {

                kbn_bunkatsu: function (value, element) {
                    if (value == "" || App.isUndefOrNull(value) || value == 0) {
                        element.val("");
                    }
                    else {
                        element.val(value);
                    }
                    return true;
                },

                cd_hin: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.val("");
                    }
                    else {
                        //element.val(App.num.format(Number(value), "000000"));
                        element.val(App.common.fillString(value), 6);
                    }
                    return true;
                },

                qty_haigo: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.val("");
                    }
                    else {
                        value = page.getFixed3(value);
                        element.val(App.num.format(Number(value), "#,#.000"));
                    }
                    return true;
                },

                qty_nisugata: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.val("");
                    }
                    else {
                        value = page.getFixed3(value);
                        element.val(App.num.format(Number(value), "#,#.000"));
                    }
                    return true;
                },

                hijyu: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.text("");
                    }
                    else {
                        element.text(App.num.format(Number(value), "#,#.000"));
                    }
                    return true;
                },

                budomari: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.val("");
                    }
                    else {
                        element.val(App.num.format(Number(value), "#,#.00"));
                    }
                    return true;
                },

                gosa: function (value, element) {
                    if (App.isUndefOrNull(value) ||value.toString() == "") {
                        element.val("");
                    }
                    else {
                        element.val(App.num.format(Number(value), "#,#.000"));
                    }
                    return true;
                }
            }
        };

        /**
         * 画面明細へのデータバインド処理を行います。
         */
        page.detail.bind = function (data, isNotclearTable) {
            var i, l, item, dataSet, dataCount;

            if (App.isUndefOrNull(isNotclearTable) || !isNotclearTable) {
                page.detail.dataTable.dataTable("clear");
            }

            page.detail.dataTable.dataTable("addRows", data, function (row, item) {
                if (!App.isUndefOrNull(isNotclearTable) && isNotclearTable) {
                    row.addClass("rowCopy");
                }

                row.form(page.detail.options.bindOption).bind(item);
                page.detail.setFormatHinmei(row, item);
                page.detail.setNokikaku(row, item.kbn_hin);
                var mark = page.values.ma_mark[item.cd_mark];
                page.detail.setControlByMark(row, mark, true);
                page.detail.setTaniShiyo(row);

                row.addClass("" + item.no_kotei)
                row.addClass(item.no_kotei + "-" + item.no_tonyu)

                row.attr("kotei", item.no_kotei);
                row.attr("tonyu", item.no_tonyu)
                row.attr("kotei-tonyu", item.no_kotei + "-" + item.no_tonyu);

                if (!App.isUndefOrNull(item.kbn_shitei) && Number(item.kbn_shitei)) {
                    row.findP("cd_hin").prop("disabled", true);
                    row.find(".cd_hin").prop("disabled", true);
                }

                return row;
            }, true);

            page.detail.dataTable.dataTable("each", function (row) {
                if (row.element.hasClass("item-tmpl")) {
                    return;
                }

                if (!App.isUndefOrNull(isNotclearTable) && isNotclearTable) {
                    if (!row.element.hasClass("rowCopy")) {
                        return;
                    }
                }
                var id = row.element.attr("data-key"),
                    entity = page.detail.data.entry(id),
                    cd_hin = entity["cd_hin"],
                    kbn_hin = row.element.findP("kbn_hin").text(),
                    cd_tani_hin = row.element.findP("cd_tani_hin").text();

                if (!(Number(cd_hin) >= page.values.shijiMin && Number(cd_hin) <= page.values.shijiMax)) {
                    if (cd_tani_hin == "" || App.isUndefOrNull(cd_tani_hin) || Number(cd_hin) == 0) {
                        row.element.addClass("row-error-past")
                        row.element.findP("hyperlink").hide();
                    }
                    if (page.values.kengen_kino_jisshi_kano) {
                        row.element.find("input").not("[data-prop='kbn_bunkatsu']").prop("disabled", true);
                        row.element.find("button").prop("disabled", true);
                    }
                }
                
            });
        };

        /**
         * 画面明細の一覧の行が選択された時の処理を行います。
         */
        page.detail.select = function (e, row) {
            //TODO: 単一行を作成する場合は、下記２行を利用します。
            $($(row.element[page.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab.selected")).removeClass("selected").addClass("unselected");
            $(row.element[page.detail.fixedColumnIndex].querySelectorAll(".select-tab")).removeClass("unselected").addClass("selected");
            //TODO: 多段行を作成する場合は、下記２行を有効にし、上記の２行は削除します。
            //$($(row.element[page.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab-2lines.selected")).removeClass("selected").addClass("unselected");
            //$(row.element[page.detail.fixedColumnIndex].querySelectorAll(".select-tab-2lines")).removeClass("unselected").addClass("selected");

            //TODO: 選択行全体に背景色を付ける場合は以下のコメントアウトを解除します。
            //if (!App.isUndefOrNull(page.detail.selectedRow)) {
            //    page.detail.selectedRow.element.find(".selected-row").removeClass("selected-row");
            //}
            //row.element.find("tr").addClass("selected-row");
            //page.detail.selectedRow = row;
        };

        /**
         * 画面明細の一覧の入力項目の変更イベントの処理を行います。
         */
        page.detail.change = function (e, row) {
            var target = $(e.target),
                id = row.element.attr("data-key"),
                property = target.attr("data-prop"),
                entity = page.detail.data.entry(id),
                data = row.element.form().data(),
                options = {
                    filter: page.detail.validationFilter
                };

            if (property == "cd_hin") {
                row.element.removeClass("row-error-past");
                target.removeClass("kengen-error");
                row.element.addClass("exist-check");
            }

            $("#save").prop("disabled", false);

            page.values.isChangeRunning[property] = true;

            page.detail.executeValidation(target, row.element)
            .then(function () {
                // Bug #16903　flg_mishiyo更新不具合対応  (2020-05-05) ST
                data.isChanged = true;
                // Bug #16903　flg_mishiyo更新不具合対応  (2020-05-05) ED
                if (property == "kbn_bunkatsu") {
                    if (data[property] == 0 || App.isUndefOrNull(data[property])) {
                        target.val("");
                        entity[property] = 0;
                    }
                    else {
                        entity[property] = data[property];
                    }
                    page.detail.data.update(entity);
                    page.detail.validator.validate({
                        targets: row.element.find(".validate-error"),
                        state: {
                            tbody: row.element,
                            isGridValidation: true,
                            check: true
                        }
                    });
                }

                else if (property == "nm_hin") {
                    entity["nm_hin"] = data[property];
                    page.detail.data.update(entity);
                    page.detail.validator.validate({
                        targets: row.element.find(".validate-error"),
                        state: {
                            tbody: row.element,
                            isGridValidation: true,
                            check: true
                        }
                    });
                }

                else if (property == "cd_hin") {
                    var valOld = App.isNumeric(data[property]) ? parseInt(data[property], 10) : data[property],
                        //valNew = App.num.format(Number(valOld), "000000");
                        valNew = App.common.fillString(valOld);

                    if (valOld >= page.values.shijiMin && valOld <= page.values.shijiMax) {

                        if (entity["kbn_hin"] != App.settings.app.kbnHin.sagyo) {
                            row.element.findP("cd_mark").text(App.settings.app.cd_mark.mark_18);
                            row.element.findP("mark").text(page.getMark(App.settings.app.cd_mark.mark_18));
                            entity["cd_mark"] = App.settings.app.cd_mark.mark_18;
                            page.detail.setNokikaku(row.element, -1);
                            var mark = page.values.ma_mark[App.settings.app.cd_mark.mark_18];
                            page.detail.setControlByMark(row.element, mark);
                            page.detail.calculateQtyHaigoKei();
                            page.detail.setTaniShiyo(row.element);

                        }
                        entity["cd_hin"] = valOld;
                        entity["kbn_hin"] = App.settings.app.kbnHin.sagyo;
                        entity["kbn_jyotai"] = 1;
                        row.element.findP("kbn_hin").text(App.settings.app.kbnHin.sagyo);
                        row.element.findP("no_kikaku").text("");
                        row.element.findP("hijyu").text("");
                        row.element.findP("cd_tani_hin").text("");
                        row.element.findP("no_sort").text("");

                        page.detail.data.update(entity);

                        page.detail.validator.validate({
                            targets: row.element.find(".validate-error"),
                            state: {
                                tbody: row.element,
                                isGridValidation: true,
                                check: true
                            }
                        });

                    }
                    else {
                        
                        var parameter = {
                            cd_kaisha: page.values.cd_kaisha,
                            cd_kojyo: page.values.cd_kojyo,
                            cd_hin: valOld,
                            kbn_hin:null,
                            M_kirikae: page.values.M_kirikae
                        }
                        return $.ajax(App.ajax.webapi.get(page.urls.haigoTorokuKojyoBumon + "/getInfoHinmei", parameter)
                        ).then(function (result) {
                            if (result.length > 0) {
                                var hinmei = result[0];
                                for (var i = 0 ; i < result.length; i++) {
                                    if (result[i].kbn_hin == App.settings.app.kbnHin.shikakari) {
                                        hinmei = result[i];
                                        break;
                                    }
                                }
                                if (App.isUndefOrNull(entity) || entity["kbn_hin"] == App.settings.app.kbnHin.sagyo) {
                                    row.element.findP("cd_mark").text(App.settings.app.cd_mark.mark_0);
                                    row.element.findP("mark").text(page.getMark(App.settings.app.cd_mark.mark_0));
                                    entity["cd_mark"] = App.settings.app.cd_mark.mark_0;
                                }

                                row.element.findP("nm_hin").val(hinmei.nm_hin);
                                row.element.findP("hijyu").text(App.isUndefOrNull(hinmei.hijyu) ? "" : App.num.format(Number(hinmei.hijyu), "#,#.000"));
                                row.element.findP("no_kikaku").text(App.isUndefOrNull(hinmei.no_kikaku) ? "" : hinmei.no_kikaku);
                                row.element.findP("budomari").val(App.isUndefOrNull(hinmei.budomari) ? "" : App.num.format(Number(hinmei.budomari), "#,#.00"));
                                row.element.findP("qty_nisugata").val(App.isUndefOrNull(hinmei.nisugata_gty) ? "" : App.num.format(Number(hinmei.nisugata_gty), "#,#.000"));
                                row.element.findP("kbn_hin").text(hinmei.kbn_hin);
                                row.element.findP("cd_tani_hin").text(App.isUndefOrNull(hinmei.cd_tani_hin) ? "" : hinmei.cd_tani_hin);
                                row.element.findP("no_sort").text(App.isUndefOrNull(hinmei.no_sort) ? "" : hinmei.no_sort);

                                entity["cd_hin"] = valOld;
                                entity["nm_hin"] = hinmei.nm_hin;
                                entity["kbn_hin"] = hinmei.kbn_hin_toroku;
                                
                                entity["qty_nisugata"] = hinmei.nisugata_gty;
                                entity["budomari"] = hinmei.budomari;
                                //entity["kbn_jyotai"] = hinmei.kbn_jyotai;
                                page.detail.data.update(entity);

                                page.detail.setFormatHinmei(row.element, hinmei);
                                page.detail.setNokikaku(row.element, hinmei.kbn_hin);
                                var cd_mark = row.element.findP("cd_mark").text();
                                var mark = page.values.ma_mark[cd_mark];
                                page.detail.setControlByMark(row.element, mark);
                                page.detail.calculateQtyHaigoKei();
                                page.detail.setTaniShiyo(row.element);


                                if (page.values.kengen_kino_jisshi_kano) {
                                    target.addClass("validate-error");
                                    target.addClass("kengen-error");
                                }
                                page.detail.validator.validate({
                                    targets: row.element.find(".validate-error"),
                                    state: {
                                        tbody: row.element,
                                        isGridValidation: true,
                                        check: true
                                    }
                                });
                                
                            }
                            else {
                                row.element.findP("nm_hin").val("");
                                row.element.findP("hijyu").text("");
                                row.element.findP("no_kikaku").text("");
                                row.element.findP("budomari").val("");
                                row.element.findP("qty_nisugata").val("");
                                row.element.findP("kbn_hin").text("");
                                row.element.findP("cd_tani_hin").text("");
                                row.element.findP("no_sort").text("");

                                page.detail.setNokikaku(row.element, -1);

                                page.detail.validator.validate({
                                    targets: row.element.findP("cd_hin"),
                                    state: {
                                        tbody: row.element,
                                        isGridValidation: true,
                                        check: true
                                    }
                                });

                                page.detail.validator.validate({
                                    targets: row.element.find(".validate-error"),
                                    state: {
                                        tbody: row.element,
                                        isGridValidation: true,
                                        check: true
                                    }
                                });
                            }

                        }).fail(function () {
                            row.element.findP("nm_hin").val("");
                            row.element.findP("hijyu").text("");
                            row.element.findP("no_kikaku").text("");
                            row.element.findP("budomari").val("");
                            row.element.findP("qty_nisugata").val("");
                            row.element.findP("kbn_hin").text("");
                            row.element.findP("cd_tani_hin").text("");
                            row.element.findP("no_sort").text("");

                            page.detail.setNokikaku(row.element, -1);

                            page.detail.validator.validate({
                                targets: row.element.findP("cd_hin"),
                                state: {
                                    tbody: row.element,
                                    isGridValidation: true,
                                    check: true
                                }
                            });

                            page.detail.validator.validate({
                                targets: row.element.find(".validate-error"),
                                state: {
                                    tbody: row.element,
                                    isGridValidation: true,
                                    check: true
                                }
                            });
                        })

                    }
                }

                else if (property == "kbn_shitei") {
                    if (target.prop("checked") == true) {
                        entity[property] = 1
                        row.element.findP("cd_hin").prop("disabled", true);
                        row.element.find(".cd_hin").prop("disabled", true);
                        page.detail.data.update(entity);
                    }
                    else {
                        var options = {
                            text: App.messages.app.AP0137
                        };
                        page.dialogs.confirmDialog.confirm(options)
                        .then(function () {
                            entity[property] = 0
                            row.element.findP("cd_hin").prop("disabled", false);
                            row.element.find(".cd_hin").prop("disabled", false);
                            page.detail.data.update(entity);
                        }).fail(function () {
                            target.prop("checked", true);
                        })
                    }
                }
                else if (property == "qty_haigo") {
                    var valOld = target.val().replace(/,/g, "")
                    if (valOld != "") {;
                        var valNew = App.num.format(Number(valOld), "#,#.000");
                        target.val(valNew);
                        entity[property] = valOld;
                    }
                    else {
                        entity[property] = null;
                    }

                    page.detail.calculateQtyHaigoKei();
                    page.detail.data.update(entity);
                    page.detail.validator.validate({
                        targets: row.element.find(".validate-error"),
                        state: {
                            tbody: row.element,
                            isGridValidation: true,
                            check: true
                        }
                    });

                }
                else if (property == "qty_nisugata") {
                    var valOld = target.val().replace(/,/g, "")
                    if (valOld != "") {
                        var valOld = target.val().replace(/,/g, ""),
                            valNew = App.num.format(Number(valOld), "#,#.000");
                        target.val(valNew);
                        entity[property] = valOld;
                    }
                    else {
                        entity[property] = null;
                    }
                    page.detail.data.update(entity);
                    page.detail.validator.validate({
                        targets: row.element.find(".validate-error"),
                        state: {
                            tbody: row.element,
                            isGridValidation: true,
                            check: true
                        }
                    });
                }
                else if (property == "budomari") {

                    if (App.isUndefOrNull(data[property])) {
                        entity[property] = null;
                    }
                    else {
                        entity[property] = data[property];
                        target.val(App.num.format(Number(data[property]), "#,#.00"))
                    }

                    page.detail.data.update(entity);
                    page.detail.validator.validate({
                        targets: row.element.find(".validate-error"),
                        state: {
                            tbody: row.element,
                            isGridValidation: true,
                            check: true
                        }
                    });
                }

                else if (property == "gosa") {
                    var valOld = target.val().replace(/,/g, "")
                    if (valOld != "") {
                        var valOld = target.val(),
                            valNew = App.num.format(Number(valOld), "#,#.000");
                        target.val(valNew);
                        entity[property] = valOld;
                    }
                    else {
                        entity[property] = null;
                    }
                    page.detail.data.update(entity);
                    page.detail.validator.validate({
                        targets: row.element.find(".validate-error"),
                        state: {
                            tbody: row.element,
                            isGridValidation: true,
                            check: true
                        }
                    });
                }
            }).fail(function () {
                if (property == "cd_hin") {
                    row.element.findP("nm_hin").val("");
                    row.element.findP("hijyu").text("");
                    row.element.findP("no_kikaku").text("");
                    row.element.findP("budomari").val("");
                    row.element.findP("qty_nisugata").val("");
                    row.element.findP("kbn_hin").text("");
                    row.element.findP("cd_tani_hin").text("");
                    row.element.findP("no_sort").text("");

                    page.detail.setNokikaku(row.element, -1);

                    page.detail.validator.validate({
                        targets: row.element.find(".validate-error"),
                        state: {
                            tbody: row.element,
                            isGridValidation: true,
                            check: true
                        }
                    });

                }
            }).always(function () {
                delete page.values.isChangeRunning[property];
            });
        };

        

        

        /**
         * 画面明細のバリデーションを実行します。
         */
        page.detail.executeValidation = function (targets, row, options) {
            var defaultOptions = {
                targets: targets,
                state: {
                    tbody: row,
                    isGridValidation: true
                }
            };
            options = $.extend(true, {}, defaultOptions, options);

            return page.detail.validator.validate(options);
        };

        /**
         * 画面明細のバリデーションフィルターを設定します。（必須チェックを行わない）
         */
        page.detail.validationFilter = function (item, method, state, options) {
            return method !== "required";
        };

        /**
         * 画面明細の一覧のバリデーションを実行します。
         */
        page.detail.validateList = function (suppressMessage) {
            var validations = [],
                options = {
                    state: {
                        suppressMessage: suppressMessage,
                        check: true
                    }
                };

            page.detail.dataTable.dataTable("each", function (row) {
                if (row.element.hasClass("item-tmpl")) {
                    return;
                }

                validations.push(page.detail.executeValidation(row.element.find(":input"), row.element, options));
            });

            return App.async.all(validations);
        };

        //TODO: 以下の page.detail の各宣言は画面仕様ごとにことなるため、不要の場合は削除してください。

        /**
         * 画面明細の一覧から検索ダイアログを表示します。
         */
        page.detail.showHinmeiKaihatsuDialog = function (e) {
            var element = page.detail.element,
                row = $(e.target).closest("tbody");
            page.dialogs.hinmeiKaihatsuDialog.param.no_gamen = App.settings.app.no_gamen.haigo_toroku_kojyo;
            page.dialogs.hinmeiKaihatsuDialog.param.cd_kaisha = page.values.cd_kaisha;
            page.dialogs.hinmeiKaihatsuDialog.param.cd_kojyo = page.values.cd_kojyo;
            page.dialogs.hinmeiKaihatsuDialog.param.M_kirikae = page.values.M_kirikae;
            

            page.dialogs.hinmeiKaihatsuDialog.element.modal("show");

            //検索ダイアログでデータ選択が実行された時に呼び出される関数を設定しています。
            page.dialogs.hinmeiKaihatsuDialog.dataSelected = function (data) {

                var id = row.attr("data-key"),
                    entity = page.detail.data.entry(id);

                

                setTimeout(function () {
                    if (data.kbn_hin == App.settings.app.kbnHin.sagyo) {
                        if (data.nm_hin != row.findP("nm_hin").val() || row.findP("cd_hin").val() != App.settings.app.commentCode) {
                            $("#save").prop("disabled", false);
                            row.removeClass("row-error-past");
                            row.findP("cd_hin").removeClass("kengen-error");
                            row.findP("cd_hin").val(App.settings.app.commentCode);
                            row.findP("nm_hin").val(data.nm_hin);
                            if (entity["kbn_hin"] != App.settings.app.kbnHin.sagyo) {
                                row.findP("cd_mark").text(App.settings.app.cd_mark.mark_18);
                                row.findP("mark").text(page.getMark(App.settings.app.cd_mark.mark_18));
                                entity["cd_mark"] = App.settings.app.cd_mark.mark_18;
                                var mark = page.values.ma_mark[App.settings.app.cd_mark.mark_18];
                                page.detail.setControlByMark(row, mark);
                                page.detail.calculateQtyHaigoKei();
                                page.detail.setTaniShiyo(row);
                            }
                            page.detail.setNokikaku(row, -1);

                            entity["cd_hin"] = App.settings.app.commentCode;
                            entity["kbn_hin"] = App.settings.app.kbnHin.sagyo;
                            entity["nm_hin"] = data.nm_hin;
                            entity["kbn_jyotai"] = 1;
                            row.findP("kbn_hin").text(App.settings.app.kbnHin.sagyo);
                            row.findP("no_kikaku").text("");
                            row.findP("hijyu").text("");
                            row.findP("cd_tani_hin").text("");
                            row.findP("no_sort").text("");

                            page.detail.data.update(entity);

                            page.detail.validator.validate({
                                targets: row.find(".validate-error"),
                                state: {
                                    tbody: row,
                                    isGridValidation: true
                                }
                            });
                        }
                    }
                    else {
                        if (row.findP("cd_hin").val() == "" || parseInt(data.cd_hin, 10) != parseInt(row.findP("cd_hin").val(), 10) || row.findP("kbn_hin").text() == "" || parseInt(data.kbn_hin, 10) != parseInt(row.findP("kbn_hin").text(), 10)) {
                            $("#save").prop("disabled", false);
                            row.removeClass("row-error-past");
                            row.findP("cd_hin").removeClass("kengen-error");
                            var parameter = {
                                cd_kaisha: page.values.cd_kaisha,
                                cd_kojyo: page.values.cd_kojyo,
                                cd_hin: App.isNumeric(data.cd_hin) ? parseInt(data.cd_hin, 10) : data.cd_hin,
                                kbn_hin: App.isUndefOrNull(data.kbn_hin) ? 0 : data.kbn_hin,
                                M_kirikae: page.values.M_kirikae
                            }

                            return $.ajax(App.ajax.webapi.get(page.urls.haigoTorokuKojyoBumon + "/getInfoHinmei", parameter)
                            ).then(function (result) {
                                if (result.length > 0) {
                                    var hinmei = result[0];
                                    for (var i = 0 ; i < result.length; i++) {
                                        if (result[i].kbn_hin == App.settings.app.kbnHin.shikakari) {
                                            hinmei = result[i];
                                            break;
                                        }
                                    }

                                    if (App.isUndefOrNull(entity) || entity["kbn_hin"] == App.settings.app.kbnHin.sagyo) {
                                        row.findP("cd_mark").text(App.settings.app.cd_mark.mark_0);
                                        row.findP("mark").text(page.getMark(App.settings.app.cd_mark.mark_0));
                                        entity["cd_mark"] = App.num.format(Number(App.settings.app.cd_mark.mark_0), "00");
                                    }

                                    row.findP("nm_hin").val(hinmei.nm_hin);
                                    row.findP("hijyu").text(App.isUndefOrNull(hinmei.hijyu) ? "" : App.num.format(Number(hinmei.hijyu), "#,#.000"));
                                    row.findP("no_kikaku").text(App.isUndefOrNull(hinmei.no_kikaku) ? "" : hinmei.no_kikaku);
                                    row.findP("budomari").val(App.isUndefOrNull(hinmei.budomari) ? "" : App.num.format(Number(hinmei.budomari), "#,#.00"));
                                    row.findP("qty_nisugata").val(App.isUndefOrNull(hinmei.nisugata_gty) ? "" : App.num.format(Number(hinmei.nisugata_gty), "#,#.000"));
                                    row.findP("kbn_hin").text(hinmei.kbn_hin);
                                    row.findP("cd_tani_hin").text(App.isUndefOrNull(hinmei.cd_tani_hin) ? "" : hinmei.cd_tani_hin);
                                    row.findP("no_sort").text(App.isUndefOrNull(hinmei.no_sort) ? "" : hinmei.no_sort);

                                    entity["cd_hin"] = data.cd_hin;
                                    entity["nm_hin"] = hinmei.nm_hin;
                                    entity["kbn_hin"] = hinmei.kbn_hin_toroku;
                                    entity["qty_nisugata"] = hinmei.nisugata_gty;
                                    entity["budomari"] = hinmei.budomari;
                                    //entity["kbn_jyotai"] = hinmei.kbn_jyotai;
                                    page.detail.data.update(entity);
                                    page.detail.setFormatHinmei(row, hinmei);
                                    page.detail.setNokikaku(row, hinmei.kbn_hin);
                                    var cd_mark = row.findP("cd_mark").text();
                                    page.detail.setControlByMark(row, page.values.ma_mark[cd_mark]);
                                    page.detail.calculateQtyHaigoKei();
                                    page.detail.setTaniShiyo(row);

                                    if (page.values.kengen_kino_jisshi_kano) {
                                        row.findP("cd_hin").addClass("validate-error");
                                        row.findP("cd_hin").addClass("kengen-error");
                                    }
                                    page.detail.validator.validate({
                                        targets: row.find(".validate-error"),
                                        state: {
                                            tbody: row,
                                            isGridValidation: true,
                                            check: true
                                        }
                                    });
                                }
                                else {
                                    page.detail.setNokikaku(row, -1);
                                    row.findP("nm_hin").val("");
                                    row.findP("hijyu").text("");
                                    row.findP("no_kikaku").text("");
                                    row.findP("budomari").val("");
                                    row.findP("qty_nisugata").val("");
                                    row.findP("kbn_hin").text("");
                                    row.findP("cd_tani_hin").text("");
                                    row.findP("no_sort").text("");
                                    page.detail.validator.validate({
                                        targets: row.findP("cd_hin"),
                                        state: {
                                            tbody: row,
                                            isGridValidation: true,
                                            check: true
                                        }
                                    });

                                    page.detail.validator.validate({
                                        targets: row.find(".validate-error"),
                                        state: {
                                            tbody: row,
                                            isGridValidation: true,
                                            check: true
                                        }
                                    });
                                }
                            })
                        }
                    }
                }, 100)

                delete page.dialogs.hinmeiKaihatsuDialog.dataSelected;
            }
        };

        /**
         * Check if the row content cd_haigo in range [999990, 999999]
         */
        page.detail.isSagyoCode = function (entity) {
            var cd_haigo = App.isUndefOrNull(entity.cd_haigo) ? null : entity.cd_haigo.toString();
            if (cd_haigo && cd_haigo >= page.values.shijiMin.toString() && cd_haigo <= page.values.shijiMax.toString()) {
                return true;
            }
            return false;
        }

        /**
         * 画面明細の一覧から検索ダイアログを表示します。
         */
        page.detail.showMarkDialog = function (e) {
            var element = page.detail.element,
                tbody = $(e.target).closest("tbody"),
                id = tbody.attr("data-key"),
                entity = page.detail.data.entry(id);


            page.dialogs.markDialog.options.parameter.no_gamen = App.settings.app.no_gamen.haigo_toroku_kojyo;
            page.dialogs.markDialog.options.parameter.cd_kaisha = page.values.cd_kaisha;
            page.dialogs.markDialog.options.parameter.cd_kojyo = page.values.cd_kojyo;

            page.dialogs.markDialog.element.modal("show");

            //検索ダイアログでデータ選択が実行された時に呼び出される関数を設定しています。
            page.dialogs.markDialog.dataSelected = function (data) {
                
                if (parseInt(data.cd_mark,10) != tbody.findP("cd_mark")) {
                    $("#save").prop("disabled", false);
                    tbody.findP("cd_mark").text(parseInt(data.cd_mark,10));
                    tbody.findP("mark").text(data.mark);

                    page.detail.setControlByMark(tbody, data);
                    page.detail.setTaniShiyo(tbody);
                    page.detail.calculateQtyHaigoKei();

                    entity["cd_mark"] = App.num.format(Number(data.cd_mark),"00");
                    page.detail.data.update(entity);

                    page.detail.validator.validate({
                        targets: tbody.find(".validate-error"),
                        state: {
                            tbody: tbody,
                            isGridValidation: true,
                            check: true
                        }
                    });

                }

                delete page.dialogs.markDialog.dataSelected;
            }
        };

        /**
         * Event when click hyperlink
         */
        page.detail.moveToHaigoTorokuKojyoBumon = function (e) {
            var target = $(e.target),
                row = target.closest("tbody"),
                id = row.attr("data-key"),
                entity = page.detail.data.entry(id),
                cd_hin = entity["cd_hin"];
            window.open(App.str.format("209_HaigoTorokuKojyoBumon.aspx?cd_haigo={0}&M_HaigoToroku={1}&flg_edit_shikakarihin={2}&no_han={3}&cd_kaisha={4}&cd_kojyo={5}&M_kirikae={6}", cd_hin, App.settings.app.m_haigo_toroku.shosai, page.values.shikakarihin_edit, page.values.noHanFirst,page.values.cd_kaisha,page.values.cd_kojyo,page.values.M_kirikae));
        }

        /**
         * Set tani shiyo
         */
        page.detail.setTaniShiyo = function (row, notShowNameTani) {
            var cd_hin = row.findP("cd_hin").val(),
                cd_mark = row.findP("cd_mark").text(),
                cd_tani_hin = row.findP("cd_tani_hin").text(),
                cd_taniShiyo_mark = page.checkExistsMark(cd_mark) ? page.values.ma_mark[cd_mark].cd_tani_shiyo : null;

            if (cd_hin >= page.values.shijiMin && cd_hin <= page.values.shijiMax) {
                row.findP("nm_tani").text(page.values.ma_tani[cd_taniShiyo_mark] ? page.values.ma_tani[cd_taniShiyo_mark].nm_tani : "");
                row.findP("cd_tani").text(page.values.ma_tani[cd_taniShiyo_mark] ? (page.values.ma_tani[cd_taniShiyo_mark].nm_tani == "" ? "" : cd_taniShiyo_mark) : "");
            }
            else {
                if (cd_tani_hin == "") {
                    if (cd_taniShiyo_mark == App.settings.app.taniShiyo.g) {
                        row.findP("nm_tani").text(page.values.ma_tani[App.settings.app.taniShiyo.g].nm_tani);
                        row.findP("cd_tani").text(page.values.ma_tani[App.settings.app.taniShiyo.g].nm_tani == "" ? "" : App.settings.app.taniShiyo.g);
                    }
                    else {
                        row.findP("nm_tani").text(page.values.ma_tani[App.settings.app.taniShiyo.kg].nm_tani);
                        row.findP("cd_tani").text(page.values.ma_tani[App.settings.app.taniShiyo.kg].nm_tani == "" ? "" : App.settings.app.taniShiyo.kg);
                    }
                }
                else {
                    cd_tani_hin = parseInt(cd_tani_hin, 10);
                    if (cd_tani_hin == App.settings.app.taniShiyo.kg && cd_taniShiyo_mark == App.settings.app.taniShiyo.g) {
                        row.findP("nm_tani").text(page.values.ma_tani[App.settings.app.taniShiyo.g].nm_tani);
                        row.findP("cd_tani").text(page.values.ma_tani[App.settings.app.taniShiyo.g].nm_tani == "" ? "" : App.settings.app.taniShiyo.g);
                    }
                    else if (cd_tani_hin == App.settings.app.taniShiyo.kg && cd_taniShiyo_mark != App.settings.app.taniShiyo.g) {
                        row.findP("nm_tani").text(page.values.ma_tani[App.settings.app.taniShiyo.kg].nm_tani);
                        row.findP("cd_tani").text(page.values.ma_tani[App.settings.app.taniShiyo.kg].nm_tani == "" ? "" : App.settings.app.taniShiyo.kg);
                    }
                    else if (cd_tani_hin == App.settings.app.taniShiyo.l && cd_taniShiyo_mark == App.settings.app.taniShiyo.g) {
                        row.findP("nm_tani").text(page.values.ma_tani[App.settings.app.taniShiyo.ml].nm_tani)
                        row.findP("cd_tani").text(page.values.ma_tani[App.settings.app.taniShiyo.ml].nm_tani == "" ? "" : App.settings.app.taniShiyo.ml)
                    }
                    else if (cd_tani_hin == App.settings.app.taniShiyo.l && cd_taniShiyo_mark != App.settings.app.taniShiyo.g) {
                        row.findP("nm_tani").text(page.values.ma_tani[App.settings.app.taniShiyo.l].nm_tani)
                        row.findP("cd_tani").text(page.values.ma_tani[App.settings.app.taniShiyo.l].nm_tani == "" ? "" : App.settings.app.taniShiyo.l)
                    }
                    else {
                        if (cd_taniShiyo_mark == App.settings.app.taniShiyo.g) {
                            row.findP("nm_tani").text(page.values.ma_tani[App.settings.app.taniShiyo.g].nm_tani);
                            row.findP("cd_tani").text(page.values.ma_tani[App.settings.app.taniShiyo.g].nm_tani == "" ? "" : App.settings.app.taniShiyo.g);
                        }
                        else {
                            row.findP("nm_tani").text(page.values.ma_tani[App.settings.app.taniShiyo.kg].nm_tani);
                            row.findP("cd_tani").text(page.values.ma_tani[App.settings.app.taniShiyo.kg].nm_tani == "" ? "" : App.settings.app.taniShiyo.kg);
                        }
                    }
                }
                if (notShowNameTani) {
                    row.findP("nm_tani").text("");
                }
            }
        }

        /**
         * Set no_kikaku
         */
        page.detail.setNokikaku = function (row, kbn_hin) {

            if (!App.isUndefOrNull(kbn_hin) && kbn_hin < 0) {
                row.findP("hyperlink").hide();
                row.findP("text_shikakari").hide();
                row.findP("no_kikaku").show();
            }
            if (kbn_hin == App.settings.app.kbnHin.shikakari) {
                row.findP("hyperlink").show();
                row.findP("text_shikakari").hide();
                row.findP("no_kikaku").hide();
            }
            else {
                row.findP("hyperlink").hide();
                row.findP("text_shikakari").hide();
                row.findP("no_kikaku").show();
            }
        }

        /**
        * add ValidateHinmei
        */
        page.detail.checkHinMark = function () {

            var flg_error = false;

            page.detail.dataTable.dataTable("each", function (row) {
                var tbody = row.element,
                    id = tbody.attr("data-key"),
                    entity = page.detail.data.entry(id);

                if (tbody.hasClass("item-tmpl")) {
                    return;
                }

                var kbn_hin = entity["kbn_hin"],
                    tonyu = tbody.attr("tonyu"),
                    cd_mark = tbody.findP("cd_mark").text(),
                    cd_hin = Number(tbody.findP("cd_hin").val()),
                    ss_flg_sagyo = null,
                    ss_flg_genryo = null,
                    ss_flg_shikakari = null,
                    ss_flg_maeshori = null,
                    mark = page.getObjMark(cd_mark, null);
                if (mark) {
                    ss_flg_sagyo = mark.ss_flg_sagyo;
                    ss_flg_genryo = mark.ss_flg_genryo;
                    ss_flg_shikakari = mark.ss_flg_shikakari;
                    ss_flg_maeshori = mark.ss_flg_maeshori;
                } else {
                    flg_error = false;
                    return;
                }

                if (cd_hin >= page.values.shijiMin && cd_hin <= page.values.shijiMax && !ss_flg_sagyo) {
                    flg_error = true;
                    App.ui.page.notifyAlert.message(App.messages.app.AP0019, tbody.find(".cd_hin")).show();
                }

                else if (kbn_hin == App.settings.app.kbnHin.genryo && !ss_flg_genryo) {
                    flg_error = true;
                    App.ui.page.notifyAlert.message(App.messages.app.AP0019, tbody.find(".cd_hin")).show();
                }

                else if (kbn_hin == App.settings.app.kbnHin.shikakari && !ss_flg_shikakari) {
                    flg_error = true;
                    App.ui.page.notifyAlert.message(App.messages.app.AP0019, tbody.find(".cd_hin")).show();
                }

                else if (kbn_hin == App.settings.app.kbnHin.maeshoriGenryo && !ss_flg_maeshori) {
                    flg_error = true;
                    App.ui.page.notifyAlert.message(App.messages.app.AP0019, tbody.find(".cd_hin")).show();
                }
            });

            return flg_error;
        }


        //TODO-END: 以下の page.detail の各宣言は画面仕様ごとにことなるため、不要の場合は削除してください。

        /**
         * jQuery イベントで、ページの読み込み処理を定義します。
         */
        $(document).ready(function () {
            // ページの初期化処理を呼び出します。
            page.initialize();
        });

        /**
        * addMethod lessThanDate
        */
        App.validation.addMethod("lessThanDate", function (value, param, opts, done) {

            var data = page.header.element.form().data();

            if (page.isEmpty(value)) {
                return done(true);
            }

            if (App.isUndefOrNull(data[param])) {
                return done(true);
            }

            var dt_from = value,
                dt_to = App.date.format(data[param], "yyyy/MM/dd");

            if (dt_from > dt_to) {
                return done(false);
            }

            return done(true);
        });

        /**
        * Customize validate range.
        */
        App.validation.addMethod("rangeCustom", function (value, param, opts, done) {

            if (!$(page.detail.element.findP("rdo_shiage_user")[1]).prop("checked")) {
                return done(true);
            }

            if (page.isEmpty(value)) {
                return done(true);
            }

            if (!App.isArray(param)) {
                return done(true);
            }
            value = value.replace(/\,/g, "");

            value = page.toNum(value);
            param[0] = page.toNum(param[0]);
            param[1] = page.toNum(param[1]);
            if (App.isUndef(value) || App.isUndef(param[0]) || App.isUndef(param[1])) {
                return done(true);
            }
            return done(value >= param[0] && value <= param[1]);
        });

        /**
        * Customize validate number
        */
        App.validation.addMethod("numberCustom", function (value, param, opts, done) {

            if (!$(page.detail.element.findP("rdo_shiage_user")[1]).prop("checked")) {
                return done(true);
            }

            if (!param) {
                return done(true);
            }
            if (page.isEmpty(value)) {
                return done(true);
            }
            value = value.replace(/\,/g, "");

            done(App.isNumeric(value));
        });

        /**
        * Customize validate pointlength
        */
        App.validation.addMethod("pointlengthCustom", function (value, param, opts, done) {

            if (!$(page.detail.element.findP("rdo_shiage_user")[1]).prop("checked")) {
                return done(true);
            }

            if (page.isEmpty(value)) {
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
        });

    </script>
</asp:Content>
<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="content-wrap">
        <!--TODO: ヘッダーを定義するHTMLをここに記述します。-->
        <label id="title-mode" style="margin-left:10px;font-size:18px;text-align:center;font-weight:bold;"></label>
        <div class="header" style="margin-top:3px;">
            <div class="row">
                <div class="control-label col-xs-1">
                    <label>登録日</label>
                </div>
                <div class="control col-xs-2">
                    <label data-prop="dt_toroku"></label>
                </div>
                <div class="control-label col-xs-1">
                    <label>更新日</label>
                </div>
                <div class="control col-xs-8">
                    <label data-prop="dt_henko"></label>
                    <label data-prop="nm_tanto_koshin"></label>
                </div>
            </div>
            <div title="配合情報" class="part">
                <div class="row">
                    <div class="control-label col-xs-2">
                        <label>配合コード<span style="color: red">*</span></label>
                    </div>
                    <div class="control col-xs-1">
                        <input type="tel" style="width:100px;ime-mode:disabled;" maxlength="7" class="limit-input-int"  data-prop="cd_haigo"/>
                    </div>
                    <div class="control col-xs-1">
                        <label class="min-zero">（</label>
                        <label class="check-group min-zero">
                            <input type="checkbox" value ="true" data-prop="flg_mishiyo" />
                            未使用
                        </label>
                        <label class="min-zero">）</label>
                    </div>
                    <div class="control col-xs-2">
                        第
                        <select data-prop="no_han" class="no_han number" style="width:48px;"></select>
                        版
                    </div>
                    <div class="control-label col-xs-2">
                        <label>有効期間<span style="color: red">*</span></label>
                    </div>
                    <div class="control col-xs-4">
                        <input type="tel"  data-prop="dt_from" data-role="date" class="yukokigen" maxlength="10" style="width:95px;ime-mode:disabled;">
                        <%--<button type="button" data-role="date" class="btn btn-xs btn-info data-app-format" style="min-width:35px;margin-right:5px;" >暦</button>--%>
                        <span>～</span>                 
                        <input type="tel"  data-prop="dt_to" data-role="date" class="yukokigen" maxlength="10" style="width:95px;ime-mode:disabled;">                   
                        <%--<button type="button"  class="btn btn-xs btn-info data-app-format" style="min-width:35px;margin-right:5px;" data-role="date">暦</button>--%>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-2">
                        <label>配合名<span style="color: red">*</span></label>
                    </div>
                    <div class="control col-xs-4">
                        <input type="text" class="ime-active" data-prop="nm_haigo" />
                    </div>
                    <div class="control-label col-xs-2">
                        <label>配合名略称</label>
                    </div>
                    <div class="control col-xs-4">
                        <input type="text" class="ime-active" data-prop="nm_haigo_r" />
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-2">
                        <label>品区分<span style="color: red">*</span></label>
                    </div>
                    <div class="control col-xs-1">
                        <select  class="number" data-prop="kbn_hin" ></select>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>V/W区分</label>
                    </div>
                    <div class="control col-xs-2">
                        <label class="check-group" style="min-width: 60px">
                            <input type="radio" class="" data-prop="rdoVWKbnKg" checked name="vw_kubun" value="1" />
                            Kg
                        </label>
                        <label class="check-group" style="min-width: 60px">
                            <input type="radio" class="" data-prop="rdoVWKbnL" name="vw_kubun" value="2" />
                            L
                        </label>
                    </div>
                    <div class="control-label col-xs-2">
                        <label>基本倍率（係数）<span style="color: red">*</span></label>
                    </div>
                    <div class="control col-xs-1">
                        <input type="tel" class="number-right limit-input-float" maxlength="6" data-prop="ritsu_kihon" style="width: 65px;ime-mode:disabled;"/>
                    </div>
                    <div class="control col-xs-3">
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-2">
                        <label>開発配合コード</label>
                    </div>
                    <div class="control col-xs-1">
                        <label data-prop="cd_haigo_seiho"></label>
                    </div>
                    <div class="control col-xs-3">
                        <label style="min-width:30px;" data-prop="flg_seiho_base_show"></label>
                    </div>
                    
                    <div class="control-label col-xs-2">
                        <label>製法番号</label>
                    </div>
                    <div class="control col-xs-4">
                        <input type="tel" data-prop="no_seiho_kaisha" maxlength="4" class="limit-input-int no-seiho-group" style="width:41px;ime-mode:disabled"/>
                        <span style="width:5px;">-</span>
                        <select class="number no-seiho-group" data-prop="no_seiho_shurui" style="width:63px;"></select>
                        <span>-</span>
                        <input type="tel" maxlength="2" data-prop="no_seiho_nen" class="limit-input-int no-seiho-group" style="width:24px;ime-mode:disabled;"/>
                        <span>-</span>
                        <input type="tel" maxlength="4" data-prop="no_seiho_renban" class="limit-input-int no-seiho-group" style="width:41px;ime-mode:disabled;"/>
                        <label class="check-group min-zero">
                            <input type="checkbox" data-prop="kbn_koshin_han" value="true" />
                            全版更新
                        </label>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-2">
                        <label>製品情報</label>
                    </div>
                    <div class="control col-xs-4">
                        <label class="overflow-ellipsis" data-prop="nm_seihin"></label>
                    </div>
                                                   
                    <div class="control-label col-xs-2">
                        <label>製法名</label>
                    </div>
                    <div class="control col-xs-4">
                        <input type="text" class="ime-active" data-prop="nm_seiho"/>
                    </div>
                </div>
                <div class="row">
                    <div class="control col-xs-6">
                    </div>
                    
                    <div class="control col-xs-1">
                        <button type="button" id="seizo-bunsho-sansho" class="btn btn-xs btn-primary">&nbsp;&nbsp;製法文書参照&nbsp;&nbsp;</button> 
                    </div>
                    <div class="control col-xs-1">
                        <button type="button" id="shiryo-tenpu" class="btn btn-xs btn-primary">&nbsp;&nbsp;資料添付/参照&nbsp;&nbsp;</button> 
                    </div>
                    <div class="control col-xs-1">
                        <button type="button" id="excel-haigo-list" class="btn btn-xs btn-primary" style="display: none;">&nbsp;&nbsp;配合表EXCEL&nbsp;&nbsp;</button>
                    </div>     
                    <div class="control col-xs-1">
                        <button type="button" id="haigoTorikomi-dialog" class="btn btn-xs btn-primary haigoutorikomi">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;配合取込&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</button> 
                    </div>
                    <div class="control col-xs-1">
                        <button type="button" id="tsuikaJohoNyuryoku-dialog" class="btn btn-xs btn-primary btnTuikaJyoho">&nbsp;&nbsp;追加情報入力&nbsp;&nbsp;</button>                       
                    </div>
                    <div class="control col-xs-1">   
                        <button type="button" id="seihinJohoNyuryoku-dialog" class="btn btn-xs btn-primary btnSeihinNyuroku">&nbsp;&nbsp;製品情報入力&nbsp;&nbsp;</button>                    
                    </div> 
                </div>
            </div>
        </div>
        <!--TODO: 明細を定義するHTMLをここに記述します。-->
        <div class="detail">
            <div title="明細一覧" class="part">
                <div class="control-label toolbar">
                    <div class="row">
                        <div class="control col-xs-12">
                            <button type="button" class="btn btn-default btn-xs" id="add-kotei">工程追加</button>
                            <button type="button" class="btn btn-default btn-xs" id="move-kotei-up">工程移動▲</button>
                            <button type="button" class="btn btn-default btn-xs" id="move-kotei-down">工程移動▼</button>
                            <button type="button" class="btn btn-default btn-xs" id="del-kotei">工程削除</button>
                            <button type="button" class="btn btn-default btn-xs" id="add-tonyu" style="margin-left: 70px;">行追加</button>
                            <button type="button" class="btn btn-default btn-xs" id="move-tonyu-up">行移動▲</button>
                            <button type="button" class="btn btn-default btn-xs" id="move-tonyu-down">行移動▼</button>
                            <button type="button" class="btn btn-default btn-xs" id="del-tonyu">行削除</button>
                        </div>
                    </div>
                </div>
                <table class="datatable">
                    <!--TODO: 明細一覧のヘッダーを定義するHTMLをここに記述します。-->
                    <thead>
                        <tr>
                        <!--TODO: 単一行を作成する場合は、下記を利用します。-->
                            <th style="width: 10px;"></th>
                            <th style="width: 50px;">分割</th>
                            <th style="width: 50px;"></th>
                            <th style="width: 150px;">コード</th>
                            <th style="width: 30px;">指</th>
                            <th style="width: 430px;">品名/作業指示</th>
                            <th style="width: 65px;">マーク</th>
                            <th style="width: 150px;">配合重量</th>
                            <th style="width: 60px;">単位</th>
                            <th style="width: 150px;">荷姿重量</th>
                            <th style="width: 100px;">比重</th>
                            <th style="width: 100px;">歩留</th>
                            <th style="width: 120px;">規格書番号</th>
                            <th style="width: 80px;">誤差</th>
                        <!--TODO: 多段行を作成する場合は、以下を利用し、上記１行は削除します。
							<th rowspan="2" style="width: 10px;"></th>
                       	</tr>
                        <tr>
						-->
                        </tr>
                    </thead>
                    <!--TODO: 明細一覧のフッターを定義するHTMLをここに記述します。-->
                    <tfoot>
                        <tr>
                            <td colspan="8">
                                <div class="control col-xs-6"></div>
                                <div class="control col-xs-6 right" style="padding-right: 20px">
                                    <label class="qty_haigo_kei" style="padding-right: 30px">配合合計</label>
                                    <label data-prop="qty_haigo_kei" style="text-decoration: underline;">0.000000</label>
                                    <span data-prop="tani_kei">Kg</span>
                                
                                    <label style="padding-left: 70px">仕上量</label>
                                
                                    <label class="check-group" style="margin-left: 20px;">
                                        <input type="radio" name="shiageryo" checked class="" data-prop="rdo_shiage" />
                                        配合量と同じ
                                    </label>
                               
                                    <label class="check-group">
                                        <input type="radio" name="shiageryo" class="" data-prop="rdo_shiage_user" />
                                        指定
                                    </label>
                                    <input type="tel" style="width: 150px;text-align: right;ime-mode:disabled" class="limit-input-float format-when-focus" maxlength="15"  value="0.000000" data-prop="qty_haigo_kei_user" />
                                    <span data-prop="tani_kei_user">Kg</span>
                                </div>
                            </td>                    
                        </tr>
                        <tr>
                            <%--<td colspan="8">
                                <div class="control col-xs-1" style="text-align:center;">
                                    <label>メモ</label>
                                </div>
                                <div class="control col-xs-11">
                                    <input type="text" class="ime-active" data-prop="biko" />
                                </div>
                            </td>--%>



                            <td rowspan="2" colspan="5" >
                                <div class="control col-xs-1" style="text-align:center;height:42px!important">
                                    <label style="margin-top:5px;">メモ</label>
                                </div>
                                <div class="control col-xs-11" style="height:42px !important">
                                    <input type="text" class="ime-active" data-prop="biko"/>
                                </div>
                            </td>
                            <%--<td rowspan="2" colspan="4">
                                <input type="text" class="ime-active" data-prop="biko" />
                            </td>--%>
                            <td colspan="3" >
                                <div class="control col-xs-1" style="height:20px!important">
                                </div>
                                <div class="control col-xs-2" style="height:20px!important">
                                    <label>品管部門</label>
                                </div>
                                <div class="control col-xs-2" style="height:20px!important">
                                    <label class="check-group min-zero">
                                        <input type="checkbox" data-prop="flg_hinkan" value = "true"/>
                                        <span>確認</span>
                                    </label>
                                </div>
                                    
                                <div class="control col-xs-7" style="height:20px!important">
                                    <label>更新日</label>
                                    <label data-prop="dt_hinkan"></label>
                                    <label data-prop="nm_tanto_hinkan"></label>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3" >
                                 <div class="control col-xs-1" style="height:20px!important">
                                </div>
                                <div class="control col-xs-2" style="height:20px!important">
                                    <label>製造部門</label>
                                </div>
                                <div class="control col-xs-2" style="height:20px!important">
                                    <label class="check-group min-zero">
                                        <input type="checkbox" data-prop="flg_seizo" value="true"/>
                                        <span>確認</span>
                                    </label>
                                </div>
                                    
                                <div class="control col-xs-7" style="height:20px!important">
                                    <label>更新日</label>
                                    <label data-prop="dt_seizo"></label>
                                    <label data-prop="nm_tanto_seizo"></label>
                                </div>
                            </td>
                        </tr>
                    </tfoot>
                    <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->
                    <tbody class="item-tmpl" style="cursor: default; display: none;">
                        <tr>
                            <!--TODO: 単一行を作成する場合は、下記を利用します。-->
                            <td>
                                <span class="select-tab unselected"></span>
                            </td>
                            <td class="tonyu">
                                <input type="tel" class="limit-input-int" style="ime-mode:disabled;text-align:right;" maxlength="2" data-prop="kbn_bunkatsu" />
                            </td>
                            <td class="tonyu" style="text-align:right;">
                                <span data-prop="kotei_tonyu"></span>
                            </td>
                            <td class="tonyu">
                                <input type="tel" data-prop="cd_hin" maxlength="7" class="limit-input-int hinmei-input" style="width: 75%; white-space: nowrap;ime-mode:disabled;" />
                                <button type="button" class="btn btn-xs btn-info cd_hin" style="min-width: 25px; margin-right: 5px;">&nbsp;</button>
                            </td>
                            <td class="tonyu" style="text-align:center;">
                                <input type="checkbox" value="1" data-prop="kbn_shitei" />
                            </td>
                            <td class="tonyu">
                                <input type="text" class="ime-active" data-prop="nm_hin" />
                            </td>
                            <td class="tonyu">
                                <label data-prop="cd_mark" style="display:none;">00</label>
                                <label data-prop="mark" style="width: 50%;text-align:center;"></label>
                                <button type="button" class="btn btn-xs btn-info mark" style="width: 40%;">&nbsp;</button>
                            </td>
                            <td class="tonyu">
                                <input type="tel" maxlength="12" class="limit-input-float format-when-focus" style="ime-mode:disabled;text-align:right;" data-prop="qty_haigo" />
                            </td>
                            <td class="tonyu" style="text-align:center;">
                                <label data-prop="kbn_hin" style="display:none;"></label>
                                <label data-prop="cd_tani_hin" style="display:none;"></label>
                                <label data-prop="no_sort" style="display:none;"></label>
                                <label data-prop="cd_tani" style="display:none;"></label>
                                <label data-prop="nm_tani"></label>
                            </td>
                            <td class="tonyu">
                                <input type="tel" maxlength="12" class="limit-input-float format-when-focus" style="ime-mode:disabled;text-align:right;" data-prop="qty_nisugata" />
                            </td>
                            <td class="tonyu" style="text-align:right;">
                                <label data-prop="hijyu"></label>
                            </td>
                            <td class="tonyu">
                                <input type="tel" maxlength="6" class="limit-input-float" style="ime-mode:disabled;text-align:right;" data-prop="budomari" />
                            </td>
                            <td class="tonyu">
                                <label class="overflow-ellipsis" data-prop="no_kikaku"></label>
                                <a style="display:none;text-decoration: underline;" class="hyperlink" data-prop="hyperlink">仕掛品</a>
                                <label style="display:none;" data-prop="text_shikakari">仕掛品</label>
                            </td>
                            <td class="tonyu">
                                <input type="tel" maxlength="6" class="limit-input-float" style="ime-mode:disabled;text-align:right;" data-prop="gosa" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </div>
</asp:Content>

<asp:Content ID="FooterContent" ContentPlaceHolderID="FooterContent" runat="server">
    <div class="command-left button-command">
        <!--TODO: コマンドボタン（左寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" id="csv" class="btn btn-sm btn-primary">CSV</button>
        
        
    </div>
    <div class="command button-command">
        <!--TODO: コマンドボタン（右寄せ配置）を定義するHTMLをここに記述します。-->        
        <button type="button" id="copy-haigo"  class="btn btn-sm btn-primary">配合データコピー</button>
        <button type="button" id="add-version"  class="btn btn-sm btn-primary">版追加</button>
        <button type="button" id="delete-version"  class="btn btn-sm btn-primary" style="margin-right:200px;">版削除</button>
        <button type="button" id="save" class="btn btn-sm btn-primary">保存</button>
        
    </div>
</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>
