<%@ Page Title="203_配合登録_開発部門" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="203_HaigoTorokuKaihatsuBumon.aspx.cs" Inherits="Tos.Web.Pages.HaigoTorokuKaihatsuBumon" %>

<%@ MasterType VirtualPath="~/Site.Master" %>


<asp:Content ID="IncludeContent" ContentPlaceHolderID="IncludeContent" runat="server">

    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/part.css") %>" type="text/css" />
    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/headerdetail.css") %>" type="text/css" />
    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/selectize.css") %>" type="text/css" />
    <script src="<%=ResolveUrl("~/Scripts/selectize.js") %>" type="text/javascript"></script>

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
        .hide {
            display:none;
        }

        .input-active.dropdown-active div.item {
            display: none!important;
        }
        .input-active.dropdown-active input {
            width: calc(100% - 10px)!important;
        }
        .selectize-dropdown-content div.option {
            height: 25px;
        }

        .selectize-input { 
            height: 22px; 
            padding: 2px 8px;
            background-image: none!important;
            border-radius: 0!important;
            float: left;
            margin: 0px;
            -webkit-box-shadow: none;
            box-shadow: none;
        }

        .row-error-past-css {
            background-color:#bbbbbb !important;
        }

        .dt-body table.datatable {
            margin-top: -1px!important;
        }


    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("HaigoTorokuKaihatsuBumon", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {},
            values: {
                isChangeRunning: {},
                resultComboboxNoSeihoShurui: {},
                ma_mark: {},
                ma_tani: {},
                nameHinKotei:{},
                no_seiho: null,
                lstKojyo: [],
                no_haigo: null,
                cd_kaisha_daihyo: null,
                cd_kojyo_daihyo: null,
                flg_edit_shikakarihin: null,
                hasLoadKbnHin: false,
                shijiMin: 999990,
                shijiMax: 999999,
                genryoRangeMin: 51980,
                genryoRangeMax: 51989,
                maxNumberRowDetail: 99,
                haveHaigo: false,
                countNumberHaigo: 0,
                qty_kihon: null,
                isShowSeizoKojoShiteiDialog: false,
                modeReadOnly: false,
                shikakarihin_edit:1,
                //Kengen
                id_kino_CSV: 10,
                id_kino_ta_haigo: 20,
                id_kino_haigo_toroku: 30,
                id_kino_haigo_haishi: 40,
                id_kino_seihin_tsuika: 50,
                id_kino_shinsei: 60,
                id_data: 9,
                kengen_kino_ta_haigo: false,
                kengen_kino_shinsei: false,
                kengen_kino_haigo_haishi: false,

            },
            urls: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
                seizoKojoShiteiDialog: "Dialogs/704_SeizoKojoShitei_Dialog.aspx",
                tsuikaJohoNyuryokuDialog: "Dialogs/702_TsuikaJohoNyuryoku_Dialog.aspx",
                seihinJohoNyuryokuDialog: "Dialogs/701_SeihinJohoNyuryoku_Dialog.aspx",
                hinmeiKaihatsuDialog: "Dialogs/710_HinmeiKaihatsu_Dialog.aspx",
                markDialog: "Dialogs/706_MarkKensaku_Dialog.aspx",
                haigoTorikomiDialog: "Dialogs/700_HaigoTorikomi_Dialog.aspx",
                seihoBunshoSakuseiDialog:"Dialogs/801_SeihobunshoSakusei_Dialog.aspx",
                haigoTorokuKaihatsuBumon: "../api/HaigoTorokuKaihatsuBumon",
                vw_ma_mark: "../Services/ShisaQuickService.svc/vw_ma_mark",
                ma_tani: "../Services/ShisaQuickService.svc/ma_tani",
                ma_seiho_bunsho_hyoshi: "../Services/ShisaQuickService.svc/ma_seiho_bunsho_hyoshi?$filter=no_seiho eq '{0}'",
                mainMenu: "MainMenu.aspx",
                excelHiagoList: "../api/_300_SeihoBunshoSakusei_Excel/Post",
                RenewalMotoSentakuDialog: "Dialogs/807_RenewalMotoSentaku.aspx",
                ShisakuCopyDialog: "Dialogs/711_ShisakuCopy_Dialog.aspx"
                
            },
            header: {
                options: {},
                values: {},
                urls: {                 
                    vw_no_seiho_shurui: "../Services/ShisaQuickService.svc/vw_no_seiho_shurui?$orderby=cd_hin_syurui,no_sort",
                    ma_literal: "../Services/ShisaQuickService.svc/ma_literal?$filter=cd_category eq '{0}' and cd_literal eq '{1}'",
                    ma_seiho_bunrui: "../Services/ShisaQuickService.svc/ma_seiho_bunrui?$filter=cd_kaisha eq {0}M &$orderby=cd_seiho_bunrui",
                    ma_kbn_hin: "../Services/ShisaQuickService.svc/ma_kbn_hin?$orderby=kbn_hin&$filter=kbn_hin eq {0}",
                    vw_ma_kojyo: "../Services/ShisaQuickService.svc/vw_ma_kojyo?$filter=cd_kaisha eq {0} and cd_kojyo eq {1}",
                    ma_kaisha: "../Services/ShisaQuickService.svc/ma_kaisha?$filter=cd_kaisha eq {0}",
                }
            },
            detail: {
                urls:{
                    vw_hin: "../Services/ShisaQuickService.svc/vw_hin",
                    ma_kotei: "../Services/ShisaQuickService.svc/ma_kotei?$filter=cd_kaisha eq {0} &$orderby=cd_kotei"
                },
                options: {
                    downloadCSV:"../api/_203_HaigoTorokuKaihatsuBumonCSVDownload",  
                    downloadCSVDetail: "../api/_203_HaigoTorokuKaihatsuBumonCSVDownload/GetCSV_2"
                },
                values: {}
            },
            dialogs: {
                tsuikaJohoNyuryokuDialog: {},
                markDialog: {},
                seihinJohoNyuryokuDialog: {},
                seizoKojoShiteiDialog: {},
                hinmeiKaihatsuDialog: {},
                haigoTorikomiDialog: {},
                seihoBunshoSakuseiDialog: {},
                RenewalMotoSentakuDialog: {}
            },
            commands: {},
            ma_seiho: {},
            ma_seiho_denso: {},
            ma_seizo_line: {},
            tr_event_log: {},
            ma_seihin_seiho: {},
            ma_haigo_header: {},
            ma_haigo_meisai: {}
        });

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
         * Save data
         */
        page.saveData = function (notMessageConfirm) {
            if (notMessageConfirm) {
                page.setSaveData();
            }
            else {
                var options = {
                    text: App.messages.app.AP0004
                };
                page.dialogs.confirmDialog.confirm(options)
                .then(function () {
                    page.setSaveData();
                })
            }
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
         * Set save data.
         */
        page.setSaveData = function () {
            App.ui.loading.show();

            var parameter = {};
            //Mode toroku
            if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.seiho_shinki_toroku) {

                var data_header = page.header.element.form().data();
                //ma_seiho
                var entity_seiho = page.ma_seiho.data.entry(page.ma_seiho.dataId);
                entity_seiho["no_seiho"] = data_header["no_seiho_kaisha"] + "-" + page.header.element.findP("no_seiho_shurui").val() + '-' + data_header["no_seiho_nen"] + "-"
                entity_seiho["nm_seiho"] = data_header["nm_seiho"];
                entity_seiho["flg_renewal"] = page.values.flg_renewal == null ? 0 : page.values.flg_renewal;

                //ma_haigo_header
                var entity_header = page.ma_haigo_header.data.entry(page.ma_haigo_header.dataId);
                var state = page.ma_haigo_header.data.entries[page.ma_haigo_header.dataId].state;
                entity_header["nm_haigo"] = data_header["nm_haigo"]
                entity_header["nm_haigo_r"] = data_header["nm_haigo_r"]
                entity_header["cd_kaisha_daihyo"] = page.values.cd_kaisha_daihyo
                entity_header["cd_kojyo_daihyo"] = page.values.cd_kojyo_daihyo
                entity_header["kbn_hin"] = data_header["kbn_hin"]
                entity_header["cd_bunrui"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").val()
                entity_header["budomari"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("budomari").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("budomari").val()
                entity_header["qty_kihon"] = page.values.qty_kihon
                entity_header["ritsu_kihon"] = data_header["ritsu_kihon"]
                //entity_header["cd_setsubi"]  == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi").val()
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
                entity_header["qty_haigo_kei"] = $(page.detail.element.findP("rdo_shiage")[1]).prop("checked") ? $(page.detail.element.findP("qty_haigo_kei")[1]).text().replace(/,/g, "") : $(page.detail.element.findP("qty_haigo_kei_user")[1]).val().replace(/,/g, "")
                entity_header["biko"] = $(page.detail.element.findP("biko")[1]).val() == "" ? null : $(page.detail.element.findP("biko")[1]).val()
                entity_header["no_seiho"] = data_header["no_seiho_kaisha"] + "-" + page.header.element.findP("no_seiho_shurui").val() + '-' + data_header["no_seiho_nen"] + "-"
                entity_header["cd_kaisha"] = data_header["no_seiho_kaisha"]
                entity_header["kbn_vw"] = page.header.element.findP("rdoVWKbnKg").prop("checked") ? App.settings.app.cd_tani.kg : App.settings.app.cd_tani.l
                entity_header["hijyu"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("hijyu").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("hijyu").val()
                entity_header["flg_mishiyo"] = page.header.element.findP("flg_mishiyo").prop("checked") ? true : false
                entity_header["kbn_haishi"] = page.header.element.findP("kbn_haishi").prop("checked") ? App.settings.app.kbn_haishi.haishi : App.settings.app.kbn_haishi.shiyo
                entity_header["kbn_shiagari"] = $(page.detail.element.findP("rdo_shiage")[1]).prop("checked") ? App.settings.app.kbn_shiagari.gokeishiagarinashi : App.settings.app.kbn_shiagari.gokeishiagariari
                entity_header["status"] = App.settings.app.kbn_status.henshuchu
                entity_header["cd_seiho_bunrui"] = data_header["cd_seiho_bunrui"]
                entity_header["no_seiho_sanko"] = null
                entity_header["cd_toroku_kaisha"] = App.ui.page.user.cd_kaisha
                entity_header["cd_koshin_kaisha"] = App.ui.page.user.cd_kaisha
                entity_header["cd_haigo_sanko"] = null
                entity_header["cd_dcp_aoh"] = null
                entity_header["cd_mxt_aoh"] = null
                entity_header["kbn_cnv_aoh"] = null

                //ma_haigo_meisai
                var dataMeisai = App.ui.page.dataSet();
                page.detail.element.find(".new").each(function (i, row) {
                    var tbody = $(row),
                        id = tbody.attr("data-key"),
                        entity = page.ma_haigo_meisai.data.entry(id);

                    var qty = page.detail.getSaveQtyHaigo(entity.cd_mark, entity.qty_haigo, 0);
                    var isExistMark = page.getObjMark(entity.cd_mark, null);

                    var item = {
                        cd_haigo: entity.cd_haigo,
                        no_kotei: entity.no_kotei,
                        no_tonyu: entity.no_tonyu,
                        cd_hin: entity.cd_hin,
                        flg_shitei: entity.flg_shitei,
                        kbn_hin: entity.kbn_hin,
                        kbn_shikakari: entity.kbn_shikakari,
                        nm_hin: entity.nm_hin,
                        cd_mark: entity.cd_mark,
                        qty_haigo: qty,
                        qty_nisugata: !isExistMark ? null : entity.qty_nisugata,
                        gosa: !isExistMark ? 0 : entity.gosa,
                        budomari: !isExistMark ? 100 : entity.budomari,
                        kbn_bunkatsu: entity.kbn_bunkatsu
                    }
                    dataMeisai.add(item);
                })


                //ma_seiho_denso
                page.ma_seiho_denso.data = App.ui.page.dataSet();
                for (var i = 0; i < page.values.lstKojyo.length; i++) {
                    var ma_seiho_denso = {
                        no_seiho: 0,
                        cd_kaisha: page.values.lstKojyo[i].cd_kaisha,
                        cd_kojyo: page.values.lstKojyo[i].cd_kojyo,
                        flg_denso_taisho: App.settings.app.flg_denso_taisho.taishogai,
                        flg_denso_jyotai: 0,
                        dt_denso_toroku: null,
                        dt_densho_kanryo: null,
                        flg_daihyo_kojyo: page.values.lstKojyo[i].flg_daihyo_kojyo,
                        cd_denso_tanto_kaisha: null,
                        cd_denso_tanto: null,
                        biko: null
                    }
                    page.ma_seiho_denso.data.add(ma_seiho_denso);
                }

                //ma_seizo_line
                page.ma_seizo_line.data = App.ui.page.dataSet();
                for (var i = 0; i < page.dialogs.tsuikaJohoNyuryokuDialog.element.find(".new").length; i++) {
                    var row = $(page.dialogs.tsuikaJohoNyuryokuDialog.element.find(".new")[i]);
                    var ma_seizo_line = {
                        no_yusen: row.findP("no_yusen").text(),
                        cd_line: parseInt(row.findP("cd_line").val(), 10),
                        cd_koshin_kaisha: App.ui.page.user.cd_kaisha
                    };
                    page.ma_seizo_line.data.add(ma_seizo_line);
                }

                //ma_seihin_seiho
                page.ma_seihin_seiho.data = App.ui.page.dataSet();
                for (var i = 0; i < page.dialogs.seihinJohoNyuryokuDialog.element.find(".new").length; i++) {
                    var row = $(page.dialogs.seihinJohoNyuryokuDialog.element.find(".new")[i]);
                    var ma_seihin_seiho = {
                        no_yusen: (i+1).toString(),
                        cd_hin: parseInt(row.findP("cd_hin").val(), 10),
                        cd_koshin_kaisha: App.ui.page.user.cd_kaisha,
                        cd_koshin: App.ui.page.user.EmployeeCD
                    };
                    page.ma_seihin_seiho.data.attach(ma_seihin_seiho);
                    page.ma_seihin_seiho.data.update(ma_seihin_seiho);
                }

                //tr_event_log
                page.tr_event_log.data = App.ui.page.dataSet();
                var tr_event_log = {
                    no_seiho: data_header["no_seiho_kaisha"] + "-" + page.header.element.findP("no_seiho_shurui").val() + '-' + data_header["no_seiho_nen"] + "-",
                    cd_tanto_kaisha: App.ui.page.user.cd_kaisha,
                    cd_tanto: App.ui.page.user.EmployeeCD,
                    cd_koshin: App.ui.page.user.EmployeeCD,
                    nm_shori: App.settings.app.shori_mei.haigo_toroku,
                    nm_ope: App.settings.app.jikko_mei.seiho_shinki,
                    ip_address: "0",
                    kbn_system: 0
                }
                page.tr_event_log.data.add(tr_event_log);

                parameter = {
                    cs_ma_seiho: page.ma_seiho.data.getChangeSet(),
                    cs_ma_haigo_header: page.ma_haigo_header.data.getChangeSet(),
                    cs_ma_haigo_meisai: dataMeisai.getChangeSet(),
                    cs_ma_seiho_denso: page.ma_seiho_denso.data.getChangeSet(),
                    cs_ma_seizo_line: page.ma_seizo_line.data.getChangeSet(),
                    cs_tr_event_log: page.tr_event_log.data.getChangeSet(),
                    cs_ma_seihin_seiho: page.ma_seihin_seiho.data.getChangeSet(),
                    M_HaigoTorokuKaihatsu: App.settings.app.m_haigo_toroku_kaihatsu.seiho_shinki_toroku
                }
            }

                //Mode shosai
            else if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.shosai) {
                var data_header = page.header.element.form().data();

                //ma_seiho
                var entity_seiho = page.ma_seiho.data.entry(page.ma_seiho.dataId);
                entity_seiho["nm_seiho"] = data_header["nm_seiho"];

                //ma_haigo_header
                var entity_header = page.ma_haigo_header.data.entry(page.ma_haigo_header.dataId);
                var state = page.ma_haigo_header.data.entries[page.ma_haigo_header.dataId].state;
                entity_header["nm_haigo"] = data_header["nm_haigo"];
                entity_header["nm_haigo_r"] = data_header["nm_haigo_r"];
                entity_header["cd_kaisha_daihyo"] = page.values.cd_kaisha_daihyo;
                entity_header["cd_kojyo_daihyo"] = page.values.cd_kojyo_daihyo;
                entity_header["kbn_hin"] = data_header["kbn_hin"];
                entity_header["cd_bunrui"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").val();
                entity_header["budomari"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("budomari").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("budomari").val();
                entity_header["qty_kihon"] = page.values.qty_kihon;
                entity_header["ritsu_kihon"] = data_header["ritsu_kihon"];
                //entity_header["cd_setsubi"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi").val();
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
                entity_header["flg_gasan"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("flg_gasan").prop("checked") ? true : false;
                entity_header["qty_max"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_max").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("qty_max").val();
                entity_header["qty_haigo_kei"] = $(page.detail.element.findP("rdo_shiage")[1]).prop("checked") ? $(page.detail.element.findP("qty_haigo_kei")[1]).text().replace(/,/g, "") : $(page.detail.element.findP("qty_haigo_kei_user")[1]).val().replace(/,/g, "");
                entity_header["biko"] = $(page.detail.element.findP("biko")[1]).val() == "" ? null : $(page.detail.element.findP("biko")[1]).val();
                entity_header["kbn_vw"] = page.header.element.findP("rdoVWKbnKg").prop("checked") ? App.settings.app.cd_tani.kg : App.settings.app.cd_tani.l;
                entity_header["hijyu"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("hijyu").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("hijyu").val();
                entity_header["flg_mishiyo"] = page.header.element.findP("flg_mishiyo").prop("checked") ? true : false;
                entity_header["kbn_haishi"] = page.header.element.findP("kbn_haishi").prop("checked") ? App.settings.app.kbn_haishi.haishi : App.settings.app.kbn_haishi.shiyo
                entity_header["kbn_shiagari"] = $(page.detail.element.findP("rdo_shiage")[1]).prop("checked") ? App.settings.app.kbn_shiagari.gokeishiagarinashi : App.settings.app.kbn_shiagari.gokeishiagariari;
                entity_header["cd_seiho_bunrui"] = data_header["cd_seiho_bunrui"];
                entity_header["cd_koshin_kaisha"] = App.ui.page.user.cd_kaisha;

                // SKS 2020 st
                entity_header["no_seiho_sanko"] = data_header["no_seiho_sanko"];
                entity_header["cd_haigo_sanko"] = data_header["cd_haigo_sanko"];
                entity_header["flg_renewal"] = page.values.flg_renewal == null ? 0 : page.values.flg_renewal;
                // SKS 2020 ed

                //ma_haigo_meisai
                var dataMeisai = App.ui.page.dataSet();
                page.detail.element.find(".new").each(function (i, row) {
                    var tbody = $(row),
                        id = tbody.attr("data-key"),
                        entity = page.ma_haigo_meisai.data.entry(id);

                    var qty = page.detail.getSaveQtyHaigo(entity.cd_mark, entity.qty_haigo, 0);
                    var isExistMark = page.getObjMark(entity.cd_mark, null);

                    var item = {
                        cd_haigo: entity.cd_haigo,
                        no_kotei: entity.no_kotei,
                        no_tonyu: entity.no_tonyu,
                        cd_hin: entity.cd_hin,
                        flg_shitei: entity.flg_shitei,
                        kbn_hin: entity.kbn_hin,
                        kbn_shikakari: entity.kbn_shikakari,
                        nm_hin: entity.nm_hin,
                        cd_mark: entity.cd_mark,
                        qty_haigo: qty,
                        qty_nisugata: !isExistMark ? null : entity.qty_nisugata,
                        gosa: !isExistMark ? 0 : entity.gosa,
                        budomari: !isExistMark ? 100 : entity.budomari,
                        kbn_bunkatsu: entity.kbn_bunkatsu
                    }
                    dataMeisai.add(item);
                })

                //ma_seiho_denso
                if (page.values.isShowSeizoKojoShiteiDialog) {
                    var jsonKojyo = {};
                    for (var i = 0 ; i < page.values.lstKojyo.length; i++) {
                        var item = page.values.lstKojyo[i];
                        jsonKojyo[parseInt(item.cd_kaisha, 10) + "_" + parseInt(item.cd_kojyo, 10)] = item;
                    }

                    page.ma_seiho_denso.data.findAll(function (item, entity) {
                        if (App.isUndefOrNull(jsonKojyo[item.cd_kaisha + "_" + item.cd_kojyo])) {
                            page.ma_seiho_denso.data.remove(item);
                        }
                        else {
                            if ((item.flg_daihyo_kojyo && !jsonKojyo[item.cd_kaisha + "_" + item.cd_kojyo].flg_daihyo_kojyo) || (!item.flg_daihyo_kojyo && jsonKojyo[item.cd_kaisha + "_" + item.cd_kojyo].flg_daihyo_kojyo)) {
                                item["flg_daihyo_kojyo"] = jsonKojyo[item.cd_kaisha + "_" + item.cd_kojyo].flg_daihyo_kojyo;
                                page.ma_seiho_denso.data.update(item);
                            }
                            delete jsonKojyo[item.cd_kaisha + "_" + item.cd_kojyo];
                        }

                    });

                    $.each(jsonKojyo, function (key, value) {
                        var ma_seiho_denso = {
                            no_seiho: entity_seiho["no_seiho"],
                            cd_kaisha: parseInt(value.cd_kaisha, 10),
                            cd_kojyo: parseInt(value.cd_kojyo, 10),
                            flg_denso_taisho: App.settings.app.flg_denso_taisho.taishogai,
                            flg_denso_jyotai: 0,
                            dt_denso_toroku: null,
                            dt_densho_kanryo: null,
                            flg_daihyo_kojyo: value.flg_daihyo_kojyo,
                            cd_denso_tanto_kaisha: null,
                            cd_denso_tanto: null,
                            biko: null
                        }
                        page.ma_seiho_denso.data.add(ma_seiho_denso);
                    });
                }

                //ma_seizo_line
                var jsonSeizoLine = {};
                for (var i = 0; i < page.dialogs.tsuikaJohoNyuryokuDialog.element.find(".new").length; i++) {
                    var row = $(page.dialogs.tsuikaJohoNyuryokuDialog.element.find(".new")[i]);
                    jsonSeizoLine[row.findP("no_yusen").text()] = parseInt(row.findP("cd_line").val(), 10);
                }
                page.ma_seizo_line.data.findAll(function (item, entity) {
                    if (App.isUndefOrNull(jsonSeizoLine[item.no_yusen])) {
                        page.ma_seizo_line.data.remove(item);
                    }
                    else {
                        if (item.cd_line != jsonSeizoLine[item.no_yusen]) {
                            item["cd_line"] = jsonSeizoLine[item.no_yusen];
                            item["cd_koshin_kaisha"] = App.ui.page.user.cd_kaisha;
                            page.ma_seizo_line.data.update(item);
                        }
                        delete jsonSeizoLine[item.no_yusen];
                    }
                });
                $.each(jsonSeizoLine, function (key, value) {
                    var ma_seizo_line = {
                        cd_haigo: entity_header["cd_haigo"],
                        no_yusen: key,
                        cd_line: value,
                        cd_koshin_kaisha: App.ui.page.user.cd_kaisha
                    };
                    page.ma_seizo_line.data.add(ma_seizo_line);
                });

                //ma_seihin_seiho
                var jsonSeihinSeiho = {};
                for (var i = 0; i < page.dialogs.seihinJohoNyuryokuDialog.element.find(".new").length; i++) {
                    var row = $(page.dialogs.seihinJohoNyuryokuDialog.element.find(".new")[i]),
                        cd_hin = parseInt(row.findP("cd_hin").val(), 10),
                        no_yusen = (i + 1).toString();
                    jsonSeihinSeiho[cd_hin] = no_yusen;
                }
                page.ma_seihin_seiho.data.findAll(function (item, entity) {
                    var cd_hin = item.cd_hin,
                        no_yusen = item.no_yusen;
                    if (App.isUndefOrNull(jsonSeihinSeiho[cd_hin])) {
                        item["no_yusen"] = null;
                        item["cd_haigo"] = null;
                        item["cd_koshin_kaisha"] = App.ui.page.user.cd_kaisha;
                        item["cd_koshin"] = App.ui.page.user.EmployeeCD;
                        page.ma_seihin_seiho.data.update(item);
                    }
                    else {
                        if (item.no_yusen != jsonSeihinSeiho[cd_hin]) {
                            item["no_yusen"] = jsonSeihinSeiho[cd_hin];
                            item["cd_koshin_kaisha"] = App.ui.page.user.cd_kaisha;
                            item["cd_koshin"] = App.ui.page.user.EmployeeCD;
                            page.ma_seihin_seiho.data.update(item);
                        }
                        delete jsonSeihinSeiho[cd_hin];
                    }

                });
                $.each(jsonSeihinSeiho, function (key, value) {
                    var ma_seihin_seiho = {
                        no_yusen: value,
                        cd_hin: key,
                        cd_haigo: entity_header["cd_haigo"],
                        cd_koshin_kaisha: App.ui.page.user.cd_kaisha,
                        cd_koshin: App.ui.page.user.EmployeeCD
                    };
                    page.ma_seihin_seiho.data.attach(ma_seihin_seiho);
                    page.ma_seihin_seiho.data.update(ma_seihin_seiho);
                });

                page.tr_event_log.data = App.ui.page.dataSet();
                var tr_event_log = {
                    no_seiho: entity_header["no_seiho"],
                    cd_tanto_kaisha: App.ui.page.user.cd_kaisha,
                    cd_tanto: App.ui.page.user.EmployeeCD,
                    cd_koshin: App.ui.page.user.EmployeeCD,
                    nm_shori: App.settings.app.shori_mei.haigo_toroku,
                    nm_ope: App.settings.app.jikko_mei.koshin,
                    ip_address: "0",
                    kbn_system: 0
                }
                page.tr_event_log.data.add(tr_event_log);

                parameter = {
                    cs_ma_seiho: page.ma_seiho.data.getChangeSet(),
                    cs_ma_haigo_header: page.ma_haigo_header.data.getChangeSet(),
                    cs_ma_haigo_meisai: dataMeisai.getChangeSet(),
                    cs_ma_seiho_denso: page.ma_seiho_denso.data.getChangeSet(),
                    cs_ma_seizo_line: page.ma_seizo_line.data.getChangeSet(),
                    cs_tr_event_log: page.tr_event_log.data.getChangeSet(),
                    cs_ma_seihin_seiho: page.ma_seihin_seiho.data.getChangeSet(),
                    M_HaigoTorokuKaihatsu: App.settings.app.m_haigo_toroku_kaihatsu.shosai
                }

            }

                //Mode haigo_shinki
            else if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.haigo_shinki) {
                var data_header = page.header.element.form().data();
                //ma_seiho
                var entity_seiho = page.ma_seiho.data.entry(page.ma_seiho.dataId);
                entity_seiho["nm_seiho"] = data_header["nm_seiho"];

                //ma_haigo_header
                var entity_header = page.ma_haigo_header.data.entry(page.ma_haigo_header.dataId);
                var state = page.ma_haigo_header.data.entries[page.ma_haigo_header.dataId].state;
                entity_header["nm_haigo"] = data_header["nm_haigo"]
                entity_header["nm_haigo_r"] = data_header["nm_haigo_r"]
                entity_header["cd_kaisha_daihyo"] = page.values.cd_kaisha_daihyo
                entity_header["cd_kojyo_daihyo"] = page.values.cd_kojyo_daihyo
                entity_header["kbn_hin"] = data_header["kbn_hin"]
                entity_header["cd_bunrui"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").val()
                entity_header["budomari"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("budomari").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("budomari").val()
                entity_header["qty_kihon"] = page.values.qty_kihon
                entity_header["ritsu_kihon"] = data_header["ritsu_kihon"]
                //entity_header["cd_setsubi"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi").val()
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
                entity_header["qty_haigo_kei"] = $(page.detail.element.findP("rdo_shiage")[1]).prop("checked") ? $(page.detail.element.findP("qty_haigo_kei")[1]).text().replace(/,/g, "") : $(page.detail.element.findP("qty_haigo_kei_user")[1]).val().replace(/,/g, "")
                entity_header["biko"] = $(page.detail.element.findP("biko")[1]).val() == "" ? null : $(page.detail.element.findP("biko")[1]).val()
                entity_header["no_seiho"] = entity_seiho["no_seiho"]
                entity_header["cd_kaisha"] = data_header["no_seiho_kaisha"]
                entity_header["kbn_vw"] = page.header.element.findP("rdoVWKbnKg").prop("checked") ? App.settings.app.cd_tani.kg : App.settings.app.cd_tani.l
                entity_header["hijyu"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("hijyu").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("hijyu").val()
                entity_header["flg_mishiyo"] = page.header.element.findP("flg_mishiyo").prop("checked") ? true : false
                entity_header["kbn_haishi"] = page.header.element.findP("kbn_haishi").prop("checked") ? App.settings.app.kbn_haishi.haishi : App.settings.app.kbn_haishi.shiyo
                entity_header["kbn_shiagari"] = $(page.detail.element.findP("rdo_shiage")[1]).prop("checked") ? App.settings.app.kbn_shiagari.gokeishiagarinashi : App.settings.app.kbn_shiagari.gokeishiagariari
                entity_header["status"] = App.settings.app.kbn_status.henshuchu
                entity_header["cd_seiho_bunrui"] = data_header["cd_seiho_bunrui"]
                entity_header["no_seiho_sanko"] = data_header["no_seiho_sanko"]
                entity_header["cd_toroku_kaisha"] = App.ui.page.user.cd_kaisha
                entity_header["cd_koshin_kaisha"] = App.ui.page.user.cd_kaisha
                entity_header["cd_haigo_sanko"] = data_header["cd_haigo_sanko"]
                entity_header["cd_dcp_aoh"] = null
                entity_header["cd_mxt_aoh"] = null
                entity_header["kbn_cnv_aoh"] = null

                //ma_haigo_meisai
                var dataMeisai = App.ui.page.dataSet();
                page.detail.element.find(".new").each(function (i, row) {
                    var tbody = $(row),
                        id = tbody.attr("data-key"),
                        entity = page.ma_haigo_meisai.data.entry(id);

                    var qty = page.detail.getSaveQtyHaigo(entity.cd_mark, entity.qty_haigo, 0);
                    var isExistMark = page.getObjMark(entity.cd_mark, null);

                    var item = {
                        cd_haigo: entity.cd_haigo,
                        no_kotei: entity.no_kotei,
                        no_tonyu: entity.no_tonyu,
                        cd_hin: entity.cd_hin,
                        flg_shitei: entity.flg_shitei,
                        kbn_hin: entity.kbn_hin,
                        kbn_shikakari: entity.kbn_shikakari,
                        nm_hin: entity.nm_hin,
                        cd_mark: entity.cd_mark,
                        qty_haigo: qty,
                        qty_nisugata: !isExistMark ? null : entity.qty_nisugata,
                        gosa: !isExistMark ? 0 : entity.gosa,
                        budomari: !isExistMark ? 100 : entity.budomari,
                        kbn_bunkatsu: entity.kbn_bunkatsu
                    }
                    dataMeisai.add(item);
                })

                //ma_seiho_denso
                if (page.values.isShowSeizoKojoShiteiDialog) {
                    var jsonKojyo = {};
                    for (var i = 0 ; i < page.values.lstKojyo.length; i++) {
                        var item = page.values.lstKojyo[i];
                        jsonKojyo[parseInt(item.cd_kaisha, 10) + "_" + parseInt(item.cd_kojyo, 10)] = item;
                    }

                    page.ma_seiho_denso.data.findAll(function (item, entity) {
                        if (App.isUndefOrNull(jsonKojyo[item.cd_kaisha + "_" + item.cd_kojyo])) {
                            page.ma_seiho_denso.data.remove(item);
                        }
                        else {
                            if ((item.flg_daihyo_kojyo && !jsonKojyo[item.cd_kaisha + "_" + item.cd_kojyo].flg_daihyo_kojyo) || (!item.flg_daihyo_kojyo && jsonKojyo[item.cd_kaisha + "_" + item.cd_kojyo].flg_daihyo_kojyo)) {
                                item["flg_daihyo_kojyo"] = jsonKojyo[item.cd_kaisha + "_" + item.cd_kojyo].flg_daihyo_kojyo;
                                page.ma_seiho_denso.data.update(item);
                            }
                            delete jsonKojyo[item.cd_kaisha + "_" + item.cd_kojyo];
                        }

                    });

                    $.each(jsonKojyo, function (key, value) {
                        var ma_seiho_denso = {
                            no_seiho: entity_seiho["no_seiho"],
                            cd_kaisha: parseInt(value.cd_kaisha, 10),
                            cd_kojyo: parseInt(value.cd_kojyo, 10),
                            flg_denso_taisho: App.settings.app.flg_denso_taisho.taishogai,
                            flg_denso_jyotai: 0,
                            dt_denso_toroku: null,
                            dt_densho_kanryo: null,
                            flg_daihyo_kojyo: value.flg_daihyo_kojyo,
                            cd_denso_tanto_kaisha: null,
                            cd_denso_tanto: null,
                            biko: null
                        }
                        page.ma_seiho_denso.data.add(ma_seiho_denso);
                    });
                }

                //ma_seizo_line
                page.ma_seizo_line.data = App.ui.page.dataSet();
                for (var i = 0; i < page.dialogs.tsuikaJohoNyuryokuDialog.element.find(".new").length; i++) {
                    var row = $(page.dialogs.tsuikaJohoNyuryokuDialog.element.find(".new")[i]);
                    var ma_seizo_line = {
                        no_yusen: row.findP("no_yusen").text(),
                        cd_line: parseInt(row.findP("cd_line").val(), 10),
                        cd_koshin_kaisha: App.ui.page.user.cd_kaisha
                    };
                    page.ma_seizo_line.data.add(ma_seizo_line);
                }

                //ma_seihin_seiho
                page.ma_seihin_seiho.data = App.ui.page.dataSet();
                for (var i = 0; i < page.dialogs.seihinJohoNyuryokuDialog.element.find(".new").length; i++) {
                    var row = $(page.dialogs.seihinJohoNyuryokuDialog.element.find(".new")[i]);
                    var ma_seihin_seiho = {
                        no_yusen: (i + 1).toString(),
                        cd_hin: parseInt(row.findP("cd_hin").val(), 10),
                        cd_koshin_kaisha: App.ui.page.user.cd_kaisha,
                        cd_koshin: App.ui.page.user.EmployeeCD
                    };
                    page.ma_seihin_seiho.data.attach(ma_seihin_seiho);
                    page.ma_seihin_seiho.data.update(ma_seihin_seiho);
                }

                //tr_event_log
                page.tr_event_log.data = App.ui.page.dataSet();
                var tr_event_log = {
                    no_seiho: entity_seiho["no_seiho"],
                    cd_tanto_kaisha: App.ui.page.user.cd_kaisha,
                    cd_tanto: App.ui.page.user.EmployeeCD,
                    cd_koshin: App.ui.page.user.EmployeeCD,
                    nm_shori: App.settings.app.shori_mei.haigo_toroku,
                    nm_ope: App.settings.app.jikko_mei.haigo_shinki,
                    ip_address: "0",
                    kbn_system: 0
                };
                page.tr_event_log.data.add(tr_event_log);

                parameter = {
                    cs_ma_seiho: page.ma_seiho.data.getChangeSet(),
                    cs_ma_haigo_header: page.ma_haigo_header.data.getChangeSet(),
                    cs_ma_haigo_meisai: dataMeisai.getChangeSet(),
                    cs_ma_seiho_denso: page.ma_seiho_denso.data.getChangeSet(),
                    cs_ma_seizo_line: page.ma_seizo_line.data.getChangeSet(),
                    cs_tr_event_log: page.tr_event_log.data.getChangeSet(),
                    cs_ma_seihin_seiho: page.ma_seihin_seiho.data.getChangeSet(),
                    M_HaigoTorokuKaihatsu: App.settings.app.m_haigo_toroku_kaihatsu.haigo_shinki
                }
            }
                //Mode seiho copy
            else if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.seiho_copy) {
                var data_header = page.header.element.form().data();
                //ma_seiho
                var data_seiho = App.ui.page.dataSet(),
                    entity_seiho = {};
                data_seiho.add(entity_seiho);
                entity_seiho["no_seiho"] = data_header["no_seiho_kaisha"] + "-" + page.header.element.findP("no_seiho_shurui").val() + '-' + data_header["no_seiho_nen"] + "-"
                entity_seiho["nm_seiho"] = data_header["nm_seiho"];
                entity_seiho["flg_renewal"] = page.values.flg_renewal == null ? 0 : page.values.flg_renewal;

                //ma_haigo_header
                var data_haigo_header = App.ui.page.dataSet(),
                    entity_header = {};
                data_haigo_header.add(entity_header);
                entity_header["nm_haigo"] = data_header["nm_haigo"]
                entity_header["nm_haigo_r"] = data_header["nm_haigo_r"]
                entity_header["cd_kaisha_daihyo"] = page.values.cd_kaisha_daihyo
                entity_header["cd_kojyo_daihyo"] = page.values.cd_kojyo_daihyo
                entity_header["kbn_hin"] = data_header["kbn_hin"]
                entity_header["cd_bunrui"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").val()
                entity_header["budomari"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("budomari").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("budomari").val()
                entity_header["qty_kihon"] = page.values.qty_kihon
                entity_header["ritsu_kihon"] = data_header["ritsu_kihon"]
                //entity_header["cd_setsubi"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi").val()
                var cd_setsubi = page.dialogs.tsuikaJohoNyuryokuDialog.values.defaultSetsubi;
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
                entity_header["qty_haigo_kei"] = $(page.detail.element.findP("rdo_shiage")[1]).prop("checked") ? $(page.detail.element.findP("qty_haigo_kei")[1]).text().replace(/,/g, "") : $(page.detail.element.findP("qty_haigo_kei_user")[1]).val().replace(/,/g, "")
                entity_header["biko"] = $(page.detail.element.findP("biko")[1]).val() == "" ? null : $(page.detail.element.findP("biko")[1]).val()
                entity_header["no_seiho"] = data_header["no_seiho_kaisha"] + "-" + page.header.element.findP("no_seiho_shurui").val() + '-' + data_header["no_seiho_nen"] + "-"
                entity_header["cd_kaisha"] = data_header["no_seiho_kaisha"]
                entity_header["kbn_vw"] = page.header.element.findP("rdoVWKbnKg").prop("checked") ? App.settings.app.cd_tani.kg : App.settings.app.cd_tani.l
                entity_header["hijyu"] = page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("hijyu").val() == "" ? null : page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("hijyu").val()
                entity_header["flg_mishiyo"] = page.header.element.findP("flg_mishiyo").prop("checked") ? true : false
                entity_header["kbn_haishi"] = page.header.element.findP("kbn_haishi").prop("checked") ? App.settings.app.kbn_haishi.haishi : App.settings.app.kbn_haishi.shiyo
                entity_header["kbn_shiagari"] = $(page.detail.element.findP("rdo_shiage")[1]).prop("checked") ? App.settings.app.kbn_shiagari.gokeishiagarinashi : App.settings.app.kbn_shiagari.gokeishiagariari
                entity_header["status"] = App.settings.app.kbn_status.henshuchu
                entity_header["cd_seiho_bunrui"] = data_header["cd_seiho_bunrui"]
                entity_header["no_seiho_sanko"] = data_header["no_seiho_sanko"]
                entity_header["cd_toroku_kaisha"] = App.ui.page.user.cd_kaisha
                entity_header["cd_koshin_kaisha"] = App.ui.page.user.cd_kaisha
                entity_header["cd_haigo_sanko"] = data_header["cd_haigo_sanko"]
                entity_header["cd_dcp_aoh"] = null
                entity_header["cd_mxt_aoh"] = null
                entity_header["kbn_cnv_aoh"] = null

                //ma_haigo_meisai
                var dataMeisai = App.ui.page.dataSet();
                page.detail.element.find(".new").each(function (i, row) {
                    var tbody = $(row),
                        id = tbody.attr("data-key"),
                        entity = page.ma_haigo_meisai.data.entry(id);

                    var qty = page.detail.getSaveQtyHaigo(entity.cd_mark, entity.qty_haigo, 0);
                    var isExistMark = page.getObjMark(entity.cd_mark, null);

                    var item = {
                        cd_haigo: entity.cd_haigo,
                        no_kotei: entity.no_kotei,
                        no_tonyu: entity.no_tonyu,
                        cd_hin: entity.cd_hin,
                        flg_shitei: entity.flg_shitei,
                        kbn_hin: entity.kbn_hin,
                        kbn_shikakari: entity.kbn_shikakari,
                        nm_hin: entity.nm_hin,
                        cd_mark: entity.cd_mark,
                        qty_haigo: qty,
                        qty_nisugata: !isExistMark ? null : entity.qty_nisugata,
                        gosa: !isExistMark ? 0 : entity.gosa,
                        budomari: !isExistMark ? 100 : entity.budomari,
                        kbn_bunkatsu: entity.kbn_bunkatsu
                    }
                    dataMeisai.add(item);
                })

                //ma_seiho_denso
                page.ma_seiho_denso.data = App.ui.page.dataSet();
                for (var i = 0; i < page.values.lstKojyo.length; i++) {
                    var ma_seiho_denso = {
                        no_seiho: 0,
                        cd_kaisha: page.values.lstKojyo[i].cd_kaisha,
                        cd_kojyo: page.values.lstKojyo[i].cd_kojyo,
                        flg_denso_taisho: App.settings.app.flg_denso_taisho.taishogai,
                        flg_denso_jyotai: 0,
                        dt_denso_toroku: null,
                        dt_densho_kanryo: null,
                        flg_daihyo_kojyo: page.values.lstKojyo[i].flg_daihyo_kojyo,
                        cd_denso_tanto_kaisha: null,
                        cd_denso_tanto: null,
                        biko: null
                    }
                    page.ma_seiho_denso.data.add(ma_seiho_denso);
                }

                //ma_seizo_line
                page.ma_seizo_line.data = App.ui.page.dataSet();
                for (var i = 0; i < page.dialogs.tsuikaJohoNyuryokuDialog.element.find(".new").length; i++) {
                    var row = $(page.dialogs.tsuikaJohoNyuryokuDialog.element.find(".new")[i]);
                    var ma_seizo_line = {
                        no_yusen: row.findP("no_yusen").text(),
                        cd_line: parseInt(row.findP("cd_line").val(), 10),
                        cd_koshin_kaisha: App.ui.page.user.cd_kaisha
                    };
                    page.ma_seizo_line.data.add(ma_seizo_line);
                }

                //ma_seihin_seiho
                page.ma_seihin_seiho.data = App.ui.page.dataSet();
                for (var i = 0; i < page.dialogs.seihinJohoNyuryokuDialog.element.find(".new").length; i++) {
                    var row = $(page.dialogs.seihinJohoNyuryokuDialog.element.find(".new")[i]);
                    var ma_seihin_seiho = {
                        no_yusen: (i+1).toString(),
                        cd_hin: parseInt(row.findP("cd_hin").val(), 10),
                        cd_koshin_kaisha: App.ui.page.user.cd_kaisha,
                        cd_koshin: App.ui.page.user.EmployeeCD
                    };
                    page.ma_seihin_seiho.data.attach(ma_seihin_seiho);
                    page.ma_seihin_seiho.data.update(ma_seihin_seiho);
                }

                //tr_event_log
                page.tr_event_log.data = App.ui.page.dataSet();
                var tr_event_log = {
                    no_seiho: data_header["no_seiho_kaisha"] + "-" + page.header.element.findP("no_seiho_shurui").val() + '-' + data_header["no_seiho_nen"] + "-",
                    cd_tanto_kaisha: App.ui.page.user.cd_kaisha,
                    cd_tanto: App.ui.page.user.EmployeeCD,
                    cd_koshin: App.ui.page.user.EmployeeCD,
                    nm_shori: App.settings.app.shori_mei.haigo_toroku,
                    nm_ope: App.settings.app.jikko_mei.seiho_shinki,
                    ip_address: "0",
                    kbn_system: 0
                }
                page.tr_event_log.data.add(tr_event_log);

                parameter = {
                    cs_ma_seiho: data_seiho.getChangeSet(),
                    cs_ma_haigo_header: data_haigo_header.getChangeSet(),
                    cs_ma_haigo_meisai: dataMeisai.getChangeSet(),
                    cs_ma_seiho_denso: page.ma_seiho_denso.data.getChangeSet(),
                    cs_ma_seizo_line: page.ma_seizo_line.data.getChangeSet(),
                    cs_tr_event_log: page.tr_event_log.data.getChangeSet(),
                    cs_ma_seihin_seiho: page.ma_seihin_seiho.data.getChangeSet(),
                    M_HaigoTorokuKaihatsu: App.settings.app.m_haigo_toroku_kaihatsu.seiho_copy
                }
            }

            //TODO: データの保存処理をここに記述します。
            return $.ajax(App.ajax.webapi.post(page.urls.haigoTorokuKaihatsuBumon, parameter))
            .then(function (result) {

                App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                $("#save").prop("disabled", true);
                setTimeout(function () {
                    var link = "203_HaigoTorokuKaihatsuBumon.aspx?M_HaigoTorokuKaihatsu=" + App.settings.app.m_haigo_toroku_kaihatsu.shosai + "&cd_haigo=" + result.cd_haigo + "&no_seiho=" + result.no_seiho;
                    if (!App.isUndefOrNull(page.values.flg_edit_shikakarihin)) {
                        link = link + "&flg_edit_shikakarihin=" + page.values.flg_edit_shikakarihin;
                    }
                    window.open(link, "_self");
                }, 2000)
            }).then(function () {
                
            }).fail(function (error) {

                if (error.status === App.settings.base.conflictStatus) {
                    // TODO: 同時実行エラー時の処理を行っています。
                    // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                    App.ui.page.notifyAlert.clear();
                    App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
                    return;
                }

                if (!App.isUndefOrNull(error.responseJSON) && error.responseJSON.Message == "haigo_out_of_range") {
                    App.ui.page.notifyAlert.message(App.messages.app.AP0162.replace("{0}", "保存")).show();
                    return;
                }

                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

            }).always(function () {
                App.ui.loading.close();
            })
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

                if (App.isUndefOrNull(page.values.cd_kaisha_daihyo)) {
                    App.ui.page.notifyAlert.message(App.messages.app.AP0006).show();
                    return;
                }

                page.validateAll().then(function () {

                    if ((Number($(page.detail.element.findP("qty_haigo_kei_user")[1]).val().replace(/,/g, "")) == 0 && $(page.detail.element.findP("rdo_shiage_user")[1]).prop("checked"))){
                        App.ui.page.notifyAlert.message(App.messages.app.AP0020).show();
                        return;
                    }

                    if (page.detail.checkHinMark()) {
                        return;
                    }

                    if (page.detail.element.find(".new .errorNoKikaku").length) {
                        var options = {
                            text: App.messages.app.AP0068
                        };

                        page.dialogs.confirmDialog.confirm(options)
                        .then(function () {
                            setTimeout(function () {
                                if (page.header.element.findP("kbn_hin").val() == App.settings.app.kbnHin.haigo) {
                                    if (page.dialogs.seihinJohoNyuryokuDialog.element.find(".new").length == 0) {

                                        var options = {
                                            text: App.messages.app.AP0016
                                        };

                                        page.dialogs.confirmDialog.confirm(options)
                                        .then(function () {
                                            page.saveData(true);
                                        })
                                    }
                                    else {
                                        page.saveData(true);
                                    }
                                }
                                else {
                                    if (page.dialogs.seihinJohoNyuryokuDialog.element.find(".new").length > 0) {
                                        App.ui.page.notifyAlert.message(App.messages.app.AP0017).show();
                                        return;
                                    }
                                    page.saveData(true);
                                }
                            }, 500)

                        })
                    }
                    else {
                        if (page.header.element.findP("kbn_hin").val() == App.settings.app.kbnHin.haigo) {
                            if (page.dialogs.seihinJohoNyuryokuDialog.element.find(".new").length == 0) {

                                var options = {
                                    text: App.messages.app.AP0016
                                };

                                page.dialogs.confirmDialog.confirm(options)
                                .then(function () {
                                    page.saveData(true);
                                })
                            }
                            else {
                                page.saveData();
                            }
                        }
                        else {
                            if (page.dialogs.seihinJohoNyuryokuDialog.element.find(".new").length > 0) {
                                App.ui.page.notifyAlert.message(App.messages.app.AP0017).show();
                                return;
                            }
                            page.saveData();
                        }
                    }
                });
            }).fail(function () {
                App.ui.page.notifyAlert.message(App.messages.base.MS0006).show();
            }).always(function () {
            });
        };

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
            $(".wrap, .footer").addClass("theme-yellow");

            if (!App.isUndefOrNull(App.uri.queryStrings.M_HaigoTorokuKaihatsu)) {
                page.values.M_HaigoTorokuKaihatsu = App.uri.queryStrings.M_HaigoTorokuKaihatsu;
            } else {
                page.values.M_HaigoTorokuKaihatsu = null;
            }

            if (!App.isUndefOrNull(App.uri.queryStrings.cd_haigo)) {
                page.values.cd_haigo = App.uri.queryStrings.cd_haigo;
            } else {
                page.values.cd_haigo = null;
            }

            if (!App.isUndefOrNull(App.uri.queryStrings.no_seiho)) {
                page.values.no_seiho = App.uri.queryStrings.no_seiho;
            } else {
                page.values.no_seiho = null;
            }

            if (!App.isUndefOrNull(App.uri.queryStrings.cd_kaisha_daihyo)) {
                page.values.cd_kaisha_daihyo = App.uri.queryStrings.cd_kaisha_daihyo;
            } else {
                page.values.cd_kaisha_daihyo = null;
            }

            if (!App.isUndefOrNull(App.uri.queryStrings.cd_kojyo_daihyo)) {
                page.values.cd_kojyo_daihyo = parseFloat(App.uri.queryStrings.cd_kojyo_daihyo);
            } else {
                page.values.cd_kojyo_daihyo = null;
            }

            if (!App.isUndefOrNull(App.uri.queryStrings.flg_renewal)) {
                page.values.flg_renewal = parseInt(App.uri.queryStrings.flg_renewal,10);
            } else {
                page.values.flg_renewal = null;
            }

            if (!App.isUndefOrNull(App.uri.queryStrings.flg_edit_shikakarihin)) {
                page.values.flg_edit_shikakarihin = App.uri.queryStrings.flg_edit_shikakarihin;
            } else {
                page.values.flg_edit_shikakarihin = null;
            }

            if (!App.isUndefOrNull(App.uri.queryStrings.no_seiho_sakusei)) {
                page.values.no_seiho_sakusei = App.uri.queryStrings.no_seiho_sakusei;
            } else {
                page.values.no_seiho_sakusei = null;
            }

            if (!App.isUndefOrNull(App.uri.queryStrings.no_seiho_copy)) {
                page.values.no_seiho_copy = App.uri.queryStrings.no_seiho_copy;
            } else {
                page.values.no_seiho_copy = null;
            }

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
                return page.dialogs.tsuikaJohoNyuryokuDialog.loadData(true, true)
            }).then(function () {
                return page.dialogs.seihinJohoNyuryokuDialog.loadData()
            }).then(function () {
                return page.loadData();
            }).then(function () {
                page.applyKengen();
                //TOsVN - 19075 - 2023/06/20: st ShohinKaihatuSupport Modify 2023
                if (page.values.M_HaigoTorokuKaihatsu != App.settings.app.log_mode.haigoMode.create) {
                    App.common.Log.write({
                        cd_game: App.settings.app.log_mode.gamen.HaigoToroku
                        , cd_taisho_data: page.taishoData(page.values.M_HaigoTorokuKaihatsu)
                        , mode: {
                            gamen: "haigo"
                            , nm_mode: page.values.M_HaigoTorokuKaihatsu
                        }
                    });
                }
                //ed
            }).fail(function (error) {
                if (error.status === 404) {
                    App.ui.page.notifyWarn.message(App.ajax.handleError(error).message).show();
                }
                else {
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                }
            }).always(function (result) {

                page.header.element.find(":input:first").focus();
                $("#save").prop("disabled", true);
                App.ui.loading.close();
            });
        };

        /**
         * Set title mode
         */
        page.setTitleMode = function () {
            
            if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.seiho_shinki_toroku
                || page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.haigo_shinki
                || page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.seiho_copy) {
                $("#title-mode").text("新規登録")
                $("#title-mode").css("color", "white").css("background-color", "red");
            }
            else if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.shosai) {
                if (page.values.flg_edit_shikakarihin) {
                    $("#title-mode").text("仕掛品　編集")
                    $("#title-mode").css("color", "white").css("background-color", "green");
                }
                else {
                    $("#title-mode").css("color", "white").css("background-color", "blue");
                }
            }
            else if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.seiho_bunsho_copy) {
                $("#title-mode").text("参　照")
                $("#title-mode").css("color", "white").css("background-color", "blue");
            }
            else if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.shisaku_copy) {
                $("#title-mode").text("参　照")
                $("#title-mode").css("color", "white").css("background-color", "blue");
            }
        }

        /**
         * Disabled control when not daihyo Kojyo
         */
        page.disabledControl = function(){
            page.header.element.findP("cd_haigo").prop("disabled", true);
            page.header.element.findP("flg_mishiyo").prop("disabled", true);
            page.header.element.findP("kbn_haishi").prop("disabled", true);
            page.header.element.findP("nm_haigo").prop("disabled", true);
            page.header.element.findP("nm_haigo_r").prop("disabled", true);
            page.header.element.findP("kbn_hin").prop("disabled", true);
            page.header.element.findP("ritsu_kihon").prop("disabled", true);
            page.header.element.find("input[type=radio]").prop("disabled", true);
            page.header.element.find("#haigoTorikomi-dialog").prop("disabled", true);
            page.header.element.findP("nm_kojyo").text("代表工場を指定ください");
            page.header.element.findP("nm_kojyo").css("color", "red");
            page.detail.element.find("#add-kotei").prop("disabled", true);
            page.detail.element.find("#move-kotei-up").prop("disabled", true);
            page.detail.element.find("#move-kotei-down").prop("disabled", true);
            page.detail.element.find("#del-kotei").prop("disabled", true);
            page.detail.element.find("#add-tonyu").prop("disabled", true);
            page.detail.element.find("#move-tonyu-up").prop("disabled", true);
            page.detail.element.find("#move-tonyu-down").prop("disabled", true);
            page.detail.element.find("#del-tonyu").prop("disabled", true)
            page.detail.element.find("input[type=radio]").prop("disabled", true);
            $(page.detail.element.findP("qty_haigo_kei_user")[1]).prop("disabled", true);
            $(page.detail.element.findP("biko")[1]).prop("disabled", true);

            page.detail.element.find(".new").find("input").prop("disabled", true);
            page.detail.element.find(".new").find("button").prop("disabled", true);
        }

        /**
         * Disabled control when have daihyo Kojyo
         */
        page.enabledControl = function(data){
            page.header.element.findP("cd_haigo").prop("disabled", false);
            page.header.element.findP("flg_mishiyo").prop("disabled", false);
            page.header.element.findP("kbn_haishi").not(".disabledKengen").prop("disabled", false);
            page.header.element.findP("nm_haigo").prop("disabled", false);
            page.header.element.findP("nm_haigo_r").prop("disabled", false);
            page.header.element.findP("kbn_hin").prop("disabled", false);
            page.header.element.findP("ritsu_kihon").prop("disabled", false);
            page.header.element.find("input[type=radio]").prop("disabled", false);
            page.header.element.find("#haigoTorikomi-dialog").not(".disabledKengen").prop("disabled", false);
            page.header.element.findP("nm_kojyo").text(data.nm_kojyo);
            page.header.element.findP("nm_kojyo").css("color", "");
            page.detail.element.find("#add-kotei").not(".disabledKengen").prop("disabled", false);
            page.detail.element.find("#move-kotei-up").not(".disabledKengen").prop("disabled", false);
            page.detail.element.find("#move-kotei-down").not(".disabledKengen").prop("disabled", false);
            page.detail.element.find("#del-kotei").not(".disabledKengen").prop("disabled", false);
            page.detail.element.find("#add-tonyu").not(".disabledKengen").prop("disabled", false);
            page.detail.element.find("#move-tonyu-up").not(".disabledKengen").prop("disabled", false);
            page.detail.element.find("#move-tonyu-down").not(".disabledKengen").prop("disabled", false);
            page.detail.element.find("#del-tonyu").not(".disabledKengen").prop("disabled", false)
            page.detail.element.find("input[type=radio]").prop("disabled", false);
            page.detail.element.find(".new").find("input").prop("disabled", false);
            page.detail.element.find(".new").find("button").prop("disabled", false);

            if ($(page.detail.element.findP("rdo_shiage_user")[1]).prop("checked")) {
                $(page.detail.element.findP("qty_haigo_kei_user")[1]).prop("disabled", false);
            }
            
            $(page.detail.element.findP("biko")[1]).prop("disabled", false);
        }

        /**
         * Apply kengen to dialogs
         */
        page.applyKengen = function () {
            var lstKengen = page.values.lstKengen,
                haigoHeaderData = page.getHaigoHeaderData();
            // For リニューアル元選択
            if (haigoHeaderData && haigoHeaderData.status == "0" 
                && (page.values.allowRenewalLevel == page.values.id_kino_ta_haigo 
                || (page.values.allowRenewalLevel == page.values.id_kino_haigo_toroku && haigoHeaderData.cd_toroku == App.ui.page.user.EmployeeCD))) {
                page.header.element.find("#moto-sentaku").prop("disabled", false);
            }
            // Restore readonly mode
            page.values.modeReadOnly701 = page.values.modeReadOnly;
            // In new mode or data get error
            if (App.isUndefOrNull(haigoHeaderData) || App.isUndefOrNull(lstKengen)) {
                return;
            }
            // *** Case haigo (header) is applied (status <> 0)
            //     Check if ([user login has edit kengen] or [the haigo created by user login]) and [user login has kengen add seihin]  
            //     => Active edit mode of DL 701
            // Check header status
            if (haigoHeaderData.status != "0") {
                // Defaut view mode
                page.dialogs.seihinJohoNyuryokuDialog.options.parameter.flg_edit = 0;
                page.values.modeReadOnly701 = true;
                // Check user edit kengen
                var kengenAddSeihin = $.grep(lstKengen, function (item) {
                    return item.id_kino == page.values.id_kino_seihin_tsuika;
                });
                if (kengenAddSeihin.length > 0) {
                    // Check the login user can edit the haigo header data
                    var kengenEditHaigo = $.grep(lstKengen, function (item) {
                        return item.id_kino == page.values.id_kino_ta_haigo;
                    });
                    if (haigoHeaderData.cd_toroku == App.ui.page.user.EmployeeCD || kengenEditHaigo.length > 0) {
                        // Update dialog mode
                        page.dialogs.seihinJohoNyuryokuDialog.options.parameter.flg_edit = 1;
                        page.values.modeReadOnly701 = false;
                    }
                }
            }
        }

        /**
         * Get ma_haigo_header data from dataset
         */
        page.getHaigoHeaderData = function () {
            if (page.ma_haigo_header.data) {
                var lstData = page.ma_haigo_header.data.findAll(function (item, entity) {
                    return entity.state != App.ui.page.dataSet.status.Deleted;
                });
                if (lstData && lstData.length > 0) {
                    return lstData[0];
                }
            }
            return null;
        }

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
            $("#csv").on("click", page.commands.downloadCSV);
            $("#shisaku-copy").on("click", page.commands.openShisakuCopyDialog);
        };

        page.commands.openShisakuCopyDialog = function () {
            debugger
            page.dialogs.ShisakuCopyDialog.param.cd_haigo = page.values.cd_haigo;
            page.dialogs.ShisakuCopyDialog.element.modal("show");
        }
        //Download CSV-203
        page.commands.downloadCSV = function () {
            if ($("#save").prop("disabled")) {

                var id = page.ma_haigo_header.dataId,
                    data = page.ma_haigo_header.data.entry(id),
                    cd_kaisha_daihyo = data.cd_kaisha_daihyo,
                    cd_kojyo_daihyo = data.cd_kojyo_daihyo,
                    no_seiho_kaisha = page.header.element.findP("no_seiho_kaisha").val();
                var params = {
                    cd_haigo: App.uri.queryStrings.cd_haigo,
                    cd_kaisha_daihyo: cd_kaisha_daihyo,
                    cd_kojyo_daihyo: cd_kojyo_daihyo,
                    no_seiho_kaisha: no_seiho_kaisha
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

                var koteiCSV = $.ajax(App.ajax.file.download(page.detail.options.downloadCSVDetail, params))
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

                    //TOsVN - 19075 - 2023/06/20: st ShohinKaihatuSupport Modify 2023
                    App.common.Log.write({
                        cd_game: App.settings.app.log_mode.gamen.HaigoTorokuCSV
                        , cd_taisho_data: page.taishoData()
                    });
                    //ed

                }).fail(function (error) {
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                }).always(function () {
                    setTimeout(function () {

                    })
                    App.ui.loading.close();
                });
               // return $.ajax(App.ajax.file.download(page.detail.options.downloadCSV, params))
               //.then(function (response, status, xhr) {
               //    if (status !== "success") {
               //        App.ui.page.notifyAlert.message(App.messages.base.MS0005).show();
               //    } else {
               //        App.file.save(response, App.ajax.file.extractFileNameDownload(xhr) || "CSVFile.csv");
               //    }
               //}).then(function () {
               //    return $.ajax(App.ajax.file.download(page.detail.options.downloadCSVDetail, params))
               //    .then(function (response, status, xhr) {
               //        if (status !== "success") {
               //            App.ui.page.notifyAlert.message(App.messages.base.MS0005).show();
               //        } else {
               //            App.file.save(response, App.ajax.file.extractFileNameDownload(xhr) || "CSVFile.csv");
               //        }
               //    })
               //}).fail(function (error) {
               //    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
               //}).always(function () {
               //    setTimeout(function () {

               //    })
               //    App.ui.loading.close();
               //});
            } else {
                var options = {
                    text: App.messages.app.AP0018,
                    hideCancel: true
                };
                page.dialogs.confirmDialog.confirm(options)
                .then(function () {})
            }
        };
        /**
         * Get kengen
         */
        page.getKengen = function () {
            var deferred = $.Deferred();
            return getKengenGamen(App.settings.app.id_gamen.haigoTorokuKaihatsuBumon).then(function (results) {
                var id_kino_defaul = false,
                    id_kino_ta_haigo = false,
                    id_kino_haigo_toroku = false,
                    id_kino_haigo_haishi = false,
                    id_kino_CSV = false;

                // Store kengen
                page.values.lstKengen = results;
                deferred.resolve();
                // Apply kengen for page mode
                $.each(results, function (index, item) {
                    if (item.id_data == page.values.id_data) {
                        switch (item.id_kino) {
                            case page.values.id_kino_CSV: //10
                                id_kino_defaul = true;
                                id_kino_CSV = true;
                                break;
                            case page.values.id_kino_ta_haigo: // 20
                                page.values.kengen_kino_ta_haigo = true;
                                id_kino_ta_haigo = true;
                                break;
                            case page.values.id_kino_haigo_toroku: // 30
                                id_kino_haigo_toroku = true;
                                break;
                            case page.values.id_kino_haigo_haishi: // 40
                                page.values.kengen_kino_haigo_haishi = true;
                                id_kino_haigo_haishi = true;
                                break;
                            case page.values.id_kino_seihin_tsuika: // 50
                                id_kino_defaul = true;
                                break;
                            case page.values.id_kino_shinsei: // 60
                                page.values.kengen_kino_shinsei = true;
                                id_kino_defaul = true;
                                break;
                        }
                        
                    }
                });

                // Enabled #csv
                if (id_kino_CSV) {
                    $("#csv").prop("disabled", false);
                }

                if (id_kino_haigo_haishi && id_kino_haigo_toroku) {
                }
                else if (id_kino_haigo_haishi && !id_kino_haigo_toroku) {
                    $("#seizo-kojo-shitei").prop("disabled", true).addClass("disabledKengen");
                    $("#haigoTorikomi-dialog").prop("disabled", true).addClass("disabledKengen");
                    $("#add-kotei").prop("disabled", true).addClass("disabledKengen");
                    $("#move-kotei-up").prop("disabled", true).addClass("disabledKengen");
                    $("#move-kotei-down").prop("disabled", true).addClass("disabledKengen");
                    $("#del-kotei").prop("disabled", true).addClass("disabledKengen");
                    $("#add-tonyu").prop("disabled", true).addClass("disabledKengen");
                    $("#move-tonyu-up").prop("disabled", true).addClass("disabledKengen");
                    $("#move-tonyu-down").prop("disabled", true).addClass("disabledKengen");
                    $("#del-tonyu").prop("disabled", true).addClass("disabledKengen");
                }
                else if (!id_kino_haigo_haishi && id_kino_haigo_toroku) {
                    page.header.element.findP("kbn_haishi").prop("disabled", true).addClass("disabledKengen");
                }
                else if (!id_kino_haigo_haishi && !id_kino_haigo_toroku && id_kino_defaul) {
                    $("#seizo-kojo-shitei").prop("disabled", true).addClass("disabledKengen");
                    $("#haigoTorikomi-dialog").prop("disabled", true).addClass("disabledKengen");
                    $("#add-kotei").prop("disabled", true).addClass("disabledKengen");
                    $("#move-kotei-up").prop("disabled", true).addClass("disabledKengen");
                    $("#move-kotei-down").prop("disabled", true).addClass("disabledKengen");
                    $("#del-kotei").prop("disabled", true).addClass("disabledKengen");
                    $("#add-tonyu").prop("disabled", true).addClass("disabledKengen");
                    $("#move-tonyu-up").prop("disabled", true).addClass("disabledKengen");
                    $("#move-tonyu-down").prop("disabled", true).addClass("disabledKengen");
                    $("#del-tonyu").prop("disabled", true).addClass("disabledKengen");
                    page.header.element.findP("kbn_haishi").prop("disabled", true).addClass("disabledKengen");
                }
                else if (!id_kino_ta_haigo && !id_kino_haigo_haishi && !id_kino_haigo_toroku && !id_kino_defaul) {
                    window.location.href = page.urls.mainMenu;
                }

                if (!page.values.kengen_kino_shinsei) {
                    page.header.element.findP("no_seiho_kaisha").prop("disabled", true).addClass("disabledKengen");
                }

                if (id_kino_ta_haigo || id_kino_haigo_toroku) {
                    page.values.allowRenewalLevel = id_kino_ta_haigo ? page.values.id_kino_ta_haigo : page.values.id_kino_haigo_toroku;
                    //page.header.element.find("#moto-sentaku").prop("disabled", false);
                }

                if (App.ui.page.user.cd_kengen != App.settings.app.kbn_kengen_bunrui.system_admin) {
                    page.header.element.findP("no_seiho_kaisha").prop("disabled", true).addClass("disabledKengen");
                }

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
                        result.value

                    );
                    page.values.comboSeihoShuruiData = result.value;
                    for (var i = 0; i < result.value.length; i++) {
                        page.values.resultComboboxNoSeihoShurui[result.value[i].no_seiho_shurui] = result.value[i].nm_hin_syurui;
                    }
                    return $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.ma_seiho_bunrui, App.ui.page.user.cd_kaisha)))
                }).then(function (result_seiho_bunrui) {
                    var cd_seiho_bunrui = page.header.element.findP("cd_seiho_bunrui");
                    cd_seiho_bunrui.children().remove();
                    App.ui.appendOptions(
                        cd_seiho_bunrui,
                        "cd_seiho_bunrui",
                        "nm_seiho_bunrui",
                        result_seiho_bunrui.value,
                        true
                    );

                    return $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.ma_literal, App.settings.app.cd_category.kbn_status, App.settings.app.kbn_status.henshuchu)))
                }).then(function (result_status) {
                    if (result_status.value[0]) {
                        page.header.element.findP("nm_status").text(result_status.value[0].nm_literal)
                    }

                    return $.ajax(App.ajax.odata.get(App.str.format(page.detail.urls.ma_kotei, App.ui.page.user.cd_kaisha)))

                }).then(function (result_kotei) {

                    var nm_kotei_list = page.detail.element.findP("nm_kotei_list");
                    nm_kotei_list.children().remove();
                    App.ui.appendOptions(
                        nm_kotei_list,
                        "nm_kotei",
                        "nm_kotei",
                        result_kotei.value,
                        true
                    );

                    for (var i = 0; i < result_kotei.value.length; i++) {
                        var item = result_kotei.value[i];
                        page.values.nameHinKotei[item.nm_kotei] = true;
                    }

                    deferred.resolve();
                }).fail(function (error) {
                    deferred.reject(error);
                })
            return deferred.promise();
        };

        /**
         * load data from ma_mark table
         */
        page.loadMarkData = function () {
            var deferred = $.Deferred();
            return $.ajax(App.ajax.odata.get(App.str.format(page.urls.vw_ma_mark + "?$filter=cd_kaisha eq {0} and cd_kojyo eq {1}", page.values.cd_kaisha_daihyo, page.values.cd_kojyo_daihyo))
                ).then(function (result) {
                    page.values.ma_mark = {};
                    for (var i = 0; i < result.value.length; i++) {
                        var mark = result.value[i];
                        page.values.ma_mark[mark.cd_mark] = mark;
                    }
                    deferred.resolve();
                }).fail(function (error) {
                    deferred.reject(error);
                })
            return deferred.promise();
            
        }

        /**
         * Load data from ma_tani_table
         */
        page.loadTaniData = function () {
            var deferred = $.Deferred();
            
            return $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_tani + "?$filter=cd_kaisha eq {0} and cd_kojyo eq {1}", page.values.cd_kaisha_daihyo, page.values.cd_kojyo_daihyo))
                ).then(function (result) {
                    page.values.ma_tani = {};
                    for (var i = 0; i < result.value.length; i++) {
                        var tani = result.value[i];

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
        * Get Fixed value point 3
        */
        page.getFixed3 = function (value) {
            if (App.isUndefOrNull(value) || isNaN(Number(value))) {
                value = 0;
            }
            return new BigNumber(value.toString()).times(1000).round().div(1000).toNumber();
        }
        /**
         * 共有ダイアログのロード処理を実行します。
         */
        page.loadDialogs = function () {

            return App.async.all({

                confirmDialog: $.get(page.urls.confirmDialog),
                seizoKojoShiteiDialog: $.get(page.urls.seizoKojoShiteiDialog),
                tsuikaJohoNyuryokuDialog: $.get(page.urls.tsuikaJohoNyuryokuDialog),
                seihinJohoNyuryokuDialog: $.get(page.urls.seihinJohoNyuryokuDialog),
                markDialog: $.get(page.urls.markDialog),
                hinmeiKaihatsuDialog: $.get(page.urls.hinmeiKaihatsuDialog),
                haigoTorikomiDialog: $.get(page.urls.haigoTorikomiDialog),
                seihoBunshoSakuseiDialog: $.get(page.urls.seihoBunshoSakuseiDialog),
                RenewalMotoSentakuDialog: $.get(page.urls.RenewalMotoSentakuDialog),
                ShisakuCopyDialog: $.get(page.urls.ShisakuCopyDialog)
            }).then(function (result) {

                $("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;

                $("#dialog-container").append(result.successes.seizoKojoShiteiDialog);
                page.dialogs.seizoKojoShiteiDialog = seizoKojoShiteiDialog;
                page.dialogs.seizoKojoShiteiDialog.initialize();

                page.dialogs.seizoKojoShiteiDialog.dataSelected = function (data) {
                    $("#save").prop("disabled", false);

                    if (page.values.cd_kaisha_daihyo != data.cd_kaisha || page.values.cd_kojyo_daihyo != data.cd_kojyo) {

                        page.values.cd_kaisha_daihyo = data.cd_kaisha;
                        page.values.cd_kojyo_daihyo = data.cd_kojyo;
                        page.values.qty_kihon = data.qty_kihon;
                        page.values.su_code_standard = data.su_code_standard;
                        page.detail.options.validations.cd_hin.rules.maxlength = Number(page.values.su_code_standard) + 1;
                        page.detail.element.find(".hinmei-input").prop("maxlength", Number(page.values.su_code_standard) + 1);

                        var id = page.ma_haigo_header.dataId,
                            entity = page.ma_haigo_header.data.entry(id);
                        entity["cd_kaisha_daihyo"] = data.cd_kaisha;
                        entity["cd_kojyo_daihyo"] = data.cd_kojyo;
                        page.ma_haigo_header.data.update(entity);

                        //Load tsuikaJohoNyuryokuDialog dialog. 
                        page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.cd_kaisha = data.cd_kaisha;
                        page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.cd_kojyo = data.cd_kojyo;
                        page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.no_haigo = null;
                        page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.kbn_hin = App.isUndefOrNull(entity["kbn_hin"]) ? null : entity["kbn_hin"];
                        if (data.cd_kaisha && (App.isUndefOrNull(entity["status"]) || entity["status"] == App.settings.app.kbn_status.henshuchu)) {
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.flg_edit = 1
                        }
                        else {
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.flg_edit = 0
                        }
                        
                        if (data.cd_kaisha) {
                            page.dialogs.tsuikaJohoNyuryokuDialog.loadData(true).then(function () {
                                page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.no_haigo = App.isUndefOrNull(entity["cd_haigo"]) ? null : entity["cd_haigo"];
                            });
                        }
                        else {
                            page.dialogs.tsuikaJohoNyuryokuDialog.loadData().then(function () {
                                page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.no_haigo = App.isUndefOrNull(entity["cd_haigo"]) ? null : entity["cd_haigo"];
                            });
                        }

                        //Load seihinJohoNyuryokuDialog dialog. 
                        page.dialogs.seihinJohoNyuryokuDialog.options.parameter.cd_kaisha = data.cd_kaisha;
                        page.dialogs.seihinJohoNyuryokuDialog.options.parameter.cd_kojyo = data.cd_kojyo;
                        page.dialogs.seihinJohoNyuryokuDialog.options.parameter.no_haigo = App.isUndefOrNull(entity["cd_haigo"]) ? null : entity["cd_haigo"];
                        //if (data.cd_kaisha && (App.isUndefOrNull(entity["status"]) || entity["status"] == App.settings.app.kbn_status.henshuchu)) {
                        if (data.cd_kaisha) {
                            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.flg_edit = 1
                        }
                        else {
                            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.flg_edit = 0
                        }
                        page.dialogs.seihinJohoNyuryokuDialog.loadData();

                        //Set enabled/disabled control
                        if (data.cd_kaisha) {
                            page.enabledControl(data);
                        }
                        else {
                            page.disabledControl();
                        };

                        //Load data when change cd_kaisha_daihyo, cd_kojyo_daihyo.
                        if (data.cd_kaisha) {
                            App.ui.loading.show();
                            page.loadMasterWhenChangeKaishaKojyo().then(function () {
                                if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.seiho_copy) {
                                    for (var i = 0; i < page.detail.element.find(".new").length; i++) {
                                        var tbody = $(page.detail.element.find(".new")[i]),
                                            no_tonyu = tbody.attr("tonyu");
                                        if (parseInt(no_tonyu, 10) > 1) {
                                            var id = tbody.attr("data-key"),
                                                entity = page.ma_haigo_meisai.data.entry(id);

                                            if (tbody.findP("kbn_shikakari").text() != "") {
                                                page.detail.setFormatHinmei(tbody, entity);
                                                page.detail.setControlByMark(tbody, page.values.ma_mark[tbody.findP("cd_mark").text()]);
                                                page.detail.setTaniShiyo(tbody);
                                            }
                                        }
                                    }
                                    page.detail.calculateQtyHaigoKei();
                                }
                                else {
                                    page.detail.setControlByMark($(page.detail.element.find(".new")[1]), page.values.ma_mark[$(page.detail.element.find(".new")[1]).findP("cd_mark").text()]);
                                    page.detail.setTaniShiyo($(page.detail.element.find(".new")[1]),true);
                                }
                            }).fail(function (error) {
                                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                            }).always(function () {
                                App.ui.loading.close();
                            })
                        }
                        
                    }

                    page.values.lstKojyo = data.lstKojyo;
                    
                }

                $("#dialog-container").append(result.successes.tsuikaJohoNyuryokuDialog);
                page.dialogs.tsuikaJohoNyuryokuDialog = tsuikaJohoNyuryokuDialog;
                page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.no_gamen = App.settings.app.no_gamen.haigo_toroku_kaihatsu;
                page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.cd_kaisha = page.values.cd_kaisha_daihyo;
                page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.cd_kojyo = page.values.cd_kojyo_daihyo;
                page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.no_haigo = page.values.cd_haigo;
                page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.flg_edit = 0;

                page.dialogs.tsuikaJohoNyuryokuDialog.initialize();

                //dialog markKensaku
                $("#dialog-container").append(result.successes.markDialog);
                page.dialogs.markDialog = markDialog;
                page.dialogs.markDialog.options.parameter.no_gamen = App.settings.app.no_gamen.haigo_toroku_kaihatsu;
                page.dialogs.markDialog.initialize();

                // dialog seihinJohoNyuryoku
                $("#dialog-container").append(result.successes.seihinJohoNyuryokuDialog);
                page.dialogs.seihinJohoNyuryokuDialog = seihinJohoNyuryokuDialog;
                page.dialogs.seihinJohoNyuryokuDialog.options.parameter.no_gamen = App.settings.app.no_gamen.haigo_toroku_kaihatsu;
                page.dialogs.seihinJohoNyuryokuDialog.options.parameter.cd_kaisha = page.values.cd_kaisha_daihyo;
                page.dialogs.seihinJohoNyuryokuDialog.options.parameter.cd_kojyo = page.values.cd_kojyo_daihyo;

                if (!App.isUndefOrNull(page.values.cd_kaisha_daihyo)) {
                    page.dialogs.seihinJohoNyuryokuDialog.options.parameter.flg_edit = 1
                }
                else {
                    page.dialogs.seihinJohoNyuryokuDialog.options.parameter.flg_edit = 0
                }
                page.dialogs.seihinJohoNyuryokuDialog.initialize();
                page.dialogs.seihinJohoNyuryokuDialog.dataSelected = function (data) {
                    page.header.element.findP("nm_seihin").text(data);
                }

                // Dialog hinmeiKaihatsu
                $("#dialog-container").append(result.successes.hinmeiKaihatsuDialog);
                page.dialogs.hinmeiKaihatsuDialog = _710_HinmeiKaihatsu_Dialog;
                page.dialogs.hinmeiKaihatsuDialog.param.no_gamen = App.settings.app.no_gamen.haigo_toroku_kaihatsu;
                page.dialogs.hinmeiKaihatsuDialog.initialize();

                // Dialog haigoTorikomi
                $("#dialog-container").append(result.successes.haigoTorikomiDialog);
                page.dialogs.haigoTorikomiDialog = haigoTorikomiDialog;
                page.dialogs.haigoTorikomiDialog.options.parameter.no_gamen = App.settings.app.no_gamen.haigo_toroku_kaihatsu;
                page.dialogs.haigoTorikomiDialog.initialize();

                page.dialogs.haigoTorikomiDialog.dataSelected = function (data) {
                    $("#save").prop("disabled", false);

                    

                    var countRow = page.detail.element.find(".new").length,
                        idLast, maxKotei;

                    if (countRow == 2) {
                        if ($(page.detail.element.find(".new")[1]).findP("cd_hin").val() == "") {
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
                                        var entity = page.ma_haigo_meisai.data.entry(id);
                                        page.ma_haigo_meisai.data.remove(entity);
                                    }
                                });
                            });
                            countRow = 0;
                        }
                    }

                    if (countRow > 0) {
                        idLast = $(page.detail.element.find(".new")[countRow - 1]).attr("data-key");
                        maxKotei = parseInt(page.ma_haigo_meisai.data.entry(idLast)["no_kotei"], 10) + 1;
                    }
                    else {
                        maxKotei = 1;
                    }

                    //Bind detail part.
                    var data_detail = [];
                    //page.ma_haigo_meisai.data = App.ui.page.dataSet();
                    for (var i = 0; i < data.length; i++) {
                        var item = data[i],
                            haigo_meisai = {},
                            no_kotei_old = item.no_kotei;

                        item.no_kotei = maxKotei;
                        item.qty_haigo = page.getFixed3(item.qty_haigo);
                        item.qty_nisugata = page.getFixed3(item.qty_nisugata);
                        page.ma_haigo_meisai.data.add(item);

                        if (item.no_tonyu == App.settings.app.no_tonyu.isKotei) {
                            haigo_meisai.no_kotei = item.no_kotei;
                            haigo_meisai.no_tonyu = item.no_tonyu;
                            haigo_meisai.kotei_tonyu = item.no_kotei + "-" + item.no_tonyu;
                            haigo_meisai.nm_kotei = item.nm_hin;
                        }
                        else {
                            //if (page.values.ma_mark[item.cd_mark].cd_tani_shiyo == App.settings.app.taniShiyo.g) {
                            //    item.qty_haigo = App.isUndefOrNull(item.qty_haigo) ? null : new BigNumber(item.qty_haigo).times(1000).toNumber();
                            //}

                            haigo_meisai.no_kotei = item.no_kotei;
                            haigo_meisai.no_tonyu = item.no_tonyu;
                            haigo_meisai.kbn_bunkatsu = item.kbn_bunkatsu;
                            haigo_meisai.kotei_tonyu = item.no_kotei + "-" + item.no_tonyu;
                            haigo_meisai.cd_hin = item.cd_hin;
                            haigo_meisai.flg_shitei = item.flg_shitei;
                            haigo_meisai.nm_hin = item.nm_hin;
                            haigo_meisai.mark = page.values.ma_mark[item.cd_mark].mark;
                            haigo_meisai.qty_haigo = item.qty_haigo;
                            haigo_meisai.qty_nisugata = item.qty_nisugata;
                            haigo_meisai.hijyu = item.hijyu;
                            haigo_meisai.budomari = item.budomari;
                            haigo_meisai.no_kikaku = item.no_kikaku;
                            haigo_meisai.gosa = item.gosa;
                            haigo_meisai.cd_mark = item.cd_mark;
                            haigo_meisai.kbn_shikakari = item.kbn_shikakari;
                            haigo_meisai.cd_tani_hin = item.cd_tani_hin;
                            haigo_meisai.kbn_hin = item.kbn_hin;
                        }
                        haigo_meisai.__id = item.__id;
                        // flg check exist in vw_hin or vw_hin_shika
                        haigo_meisai.hasHinmei = item.hasHinmei;
                        data_detail.push(haigo_meisai);

                        if (i == 0) {
                            var entity = page.ma_haigo_header.data.entry(page.ma_haigo_header.dataId);
                            entity["no_seiho_sanko"] = item.no_seiho;
                            entity["cd_haigo_sanko"] = item.cd_haigo;
                            page.ma_haigo_header.data.update(entity);

                            page.header.element.findP("no_seiho_sanko").text(item.no_seiho);
                            page.header.element.findP("cd_haigo_sanko").text(item.cd_haigo);
                            
                        }

                        if (!App.isUndefOrNull(data[i + 1])) {
                            if (data[i + 1].no_kotei > no_kotei_old) {
                                maxKotei = maxKotei + 1;
                            }
                        }
                    }
                    page.detail.bind(data_detail, true, true);
                    page.detail.calculateQtyHaigoKei();
                    
                }

                // Dialog seihoBunshoSakusei
                $("#dialog-container").append(result.successes.seihoBunshoSakuseiDialog);
                page.dialogs.seihoBunshoSakuseiDialog = _801_SeihobunshoSakusei_Dialog;
                //page.dialogs.seihoBunshoSakuseiDialog.options.parameter.no_gamen = App.settings.app.no_gamen.haigo_toroku_kaihatsu;
                page.dialogs.seihoBunshoSakuseiDialog.initialize();

                // Dialog 製法番号選択（リニューアル元選択）
                $("#dialog-container").append(result.successes.RenewalMotoSentakuDialog);
                page.dialogs.RenewalMotoSentakuDialog = _807_RenewalMotoSentaku;
                page.dialogs.RenewalMotoSentakuDialog.dataSelected = page.header.setRenewal;
                page.dialogs.RenewalMotoSentakuDialog.initialize(page.values.comboSeihoShuruiData);

                // Dialog 製法番号選択（リニューアル元選択）
                $("#dialog-container").append(result.successes.ShisakuCopyDialog);
                page.dialogs.ShisakuCopyDialog = _711_ShisakuCopy_Dialog;
                page.dialogs.ShisakuCopyDialog.initialize();
            })
            
        };

        /**
         * リニューアル元選択 set return value
         */
        page.header.setRenewal = function (data) {
            if (data) {
                var element = page.header.element;
                element.findP("no_seiho_sanko").text(data.no_seiho);
                element.findP("cd_haigo_sanko").text(data.cd_haigo);

                var haigoHeader = page.ma_haigo_header.data.entry(page.ma_haigo_header.dataId);
                if (haigoHeader && haigoHeader.kbn_hin == App.settings.app.kbn_hin.kbn_hin_3) {
                    page.values.flg_renewal = data.flg_renewal;
                    var seihoData = page.ma_seiho.data.entry(page.ma_seiho.dataId);
                    if (seihoData) {
                        seihoData.flg_renewal = data.flg_renewal;
                        page.ma_seiho.data.update(seihoData);
                    }
                }
                $("#save").prop("disabled", false);
            }
        }

        /**
         * 画面で処理の対象とするデータのロード処理を実行します。
         */
        page.loadData = function () {

            var deferred = $.Deferred();

            //MODE SEIHO TOROKU
            if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.seiho_shinki_toroku) {

                page.ma_haigo_header.data = App.ui.page.dataSet();
                page.ma_haigo_meisai.data = App.ui.page.dataSet();
                page.ma_seiho.data = App.ui.page.dataSet();

                var data_header = {
                    kbn_vw: App.settings.app.cd_tani.kg,
                    flg_mishiyo: false,
                    kbn_haishi: 0,
                    kbn_shiagari: 0,
                    status: App.settings.app.kbn_status.henshuchu,
                    qty_haigo_kei:0
                }
                page.ma_haigo_header.data.add(data_header);
                page.ma_haigo_header.dataId = data_header.__id;

                var data_seiho = {};
                page.ma_seiho.data.add(data_seiho);
                page.ma_seiho.dataId = data_seiho.__id;

                page.header.element.findP("no_seiho_kaisha").val(("000" + App.ui.page.user.cd_kaisha).slice(-4));
                page.header.element.findP("no_seiho_nen").val(new Date().getFullYear().toString().substr(2, 2));
                page.header.element.findP("no_seiho_renban").prop("disabled", true);
                page.header.element.findP("nm_hin_syurui").text(page.values.resultComboboxNoSeihoShurui[page.header.element.findP("no_seiho_shurui").val()]);
                page.header.element.find("#shiryo-tenpu").prop("disabled", true);
                page.header.element.find("#seizo-bunsho").prop("disabled", true);
                page.header.element.find("#seizo-bunsho-sansho").hide();

                //haigo part
                page.header.element.findP("flg_mishiyo").prop("disabled", true);
                page.header.element.findP("kbn_haishi").prop("disabled", true);
                page.header.element.findP("nm_haigo").prop("disabled", true);
                page.header.element.findP("nm_haigo_r").prop("disabled", true);
                page.header.element.findP("kbn_hin").prop("disabled", true);
                page.header.element.findP("ritsu_kihon").prop("disabled", true);
                page.header.element.find("input[type=radio]").prop("disabled", true);
                page.header.element.find("#haigoTorikomi-dialog").prop("disabled", true);
                
                //detail-part
                page.detail.element.find("#add-kotei").prop("disabled", true);
                page.detail.element.find("#move-kotei-up").prop("disabled", true);
                page.detail.element.find("#move-kotei-down").prop("disabled", true);
                page.detail.element.find("#del-kotei").prop("disabled", true);
                page.detail.element.find("#add-tonyu").prop("disabled", true);
                page.detail.element.find("#move-tonyu-up").prop("disabled", true);
                page.detail.element.find("#move-tonyu-down").prop("disabled", true);
                page.detail.element.find("#del-tonyu").prop("disabled", true)
                page.detail.element.find("input[type=radio]").prop("disabled", true);
                $(page.detail.element.findP("qty_haigo_kei_user")[1]).prop("disabled", true);
                $(page.detail.element.findP("biko")[1]).prop("disabled", true);

                //comand-part
                $("#csv").prop("disabled", true);

                //page.header.bind({}, true);

                deferred.resolve();
                page.detail.addNewKotei("loadBegin");
                page.detail.element.find(".new").find("input").prop("disabled",true);
                page.detail.element.find(".new").find("button").prop("disabled", true);

                return deferred.promise();
                
            }
            //MODE SHOSAI, MODE SEIHO BUNSO COPY , MODE SEIHO COPY
            else if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.shosai || page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.seiho_bunsho_copy
                || page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.seiho_copy || page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.shisaku_copy) {
                var deferred = $.Deferred(),
                    result = {},
                    kbn_hin,
                    ritsu_kihon,
                    isDeleted = false,
                    parameter = {
                        cd_haigo: page.values.cd_haigo
                    };

                return $.ajax(App.ajax.webapi.get(page.urls.haigoTorokuKaihatsuBumon + "/getDataModeShosai", parameter)
                ).then(function (result_seiho) {
                    result = result_seiho;
                    if (result.ma_haigo_header == null) {
                        $("input").prop("disabled", true);
                        $("button").prop("disabled", true);
                        isDeleted = true;
                        App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
                        setTimeout(function () {
                            window.close();
                        }, 3000)
                    }
                    else {
                        kbn_hin = result.ma_haigo_header.kbn_hin;
                        ritsu_kihon = result.ma_haigo_header.ritsu_kihon;
                        page.values.cd_kaisha_daihyo = result.ma_haigo_header.cd_kaisha_daihyo;
                        page.values.cd_kojyo_daihyo = result.ma_haigo_header.cd_kojyo_daihyo;
                        page.values.qty_kihon = result.ma_haigo_header.qty_kihon;
                        page.values.su_code_standard = result.addColumnHeader.su_code_standard;
                        page.detail.options.validations.cd_hin.rules.maxlength = Number(page.values.su_code_standard) + 1;
                        page.detail.element.find(".hinmei-input").prop("maxlength", Number(page.values.su_code_standard) + 1);

                        if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.seiho_copy) {
                            page.ma_haigo_header.data = App.ui.page.dataSet();
                            page.ma_haigo_header.data.add(result.ma_haigo_header);
                            page.ma_haigo_header.dataId = result.ma_haigo_header.__id;

                            page.ma_seiho.data = App.ui.page.dataSet();
                            page.ma_seiho.data.add(result.ma_seiho);
                            page.ma_seiho.dataId = result.ma_seiho.__id;

                            result.ma_haigo_header.status = App.settings.app.kbn_status.henshuchu;
                        }
                        else {

                            if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.shosai) {
                                page.values.haveHaigo = result.addColumnHeader.haveHaigo;
                                page.values.countNumberHaigo = result.addColumnHeader.countNumberHaigo;
                            }

                            page.ma_haigo_header.data = App.ui.page.dataSet();
                            page.ma_haigo_header.data.attach(result.ma_haigo_header);
                            page.ma_haigo_header.data.update(result.ma_haigo_header);
                            page.ma_haigo_header.dataId = result.ma_haigo_header.__id;

                            page.ma_seiho.data = App.ui.page.dataSet();
                            page.ma_seiho.data.attach(result.ma_seiho);
                            page.ma_seiho.data.update(result.ma_seiho);
                            page.ma_seiho.dataId = result.ma_seiho.__id;
                        }

                        page.ma_seiho_denso.data = App.ui.page.dataSet();
                        for (var i = 0; i < result.listSeihoDenso.length; i++) {
                            var item = result.listSeihoDenso[i];
                            page.ma_seiho_denso.data.attach(item);

                            if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.seiho_copy) {
                                if (item.flg_daihyo_kojyo) {
                                    var kojyo = {
                                        no_seiho: null,
                                        cd_kaisha: item.cd_kaisha,
                                        cd_kojyo: item.cd_kojyo,
                                        flg_daihyo_kojyo: item.flg_daihyo_kojyo,
                                        flg_denso_jyotai: 0,
                                        flg_denso_taisho: false,
                                        flg_database: false
                                    };
                                    page.values.lstKojyo.push(kojyo);
                                    break;
                                }
                            }
                        }

                        
                        page.ma_seizo_line.data = App.ui.page.dataSet();
                        for (var i = 0; i < result.listSeizoLine.length; i++) {
                            var item = result.listSeizoLine[i];
                            page.ma_seizo_line.data.attach(item);
                        }

                        page.ma_seihin_seiho.data = App.ui.page.dataSet();
                        for (var i = 0; i < result.listSeihinSeiho.length; i++) {
                            var item = result.listSeihinSeiho[i];
                            page.ma_seihin_seiho.data.attach(item);
                        }

                        return page.loadMasterWhenChangeKaishaKojyo()
                    }
                }).then(function () {
                    if (isDeleted) {
                    }
                    else if (result.ma_haigo_header != null) {
                        //Bind header part.
                        var data_header = {};
                        result.ma_haigo_header.kbn_hin = kbn_hin;
                        result.ma_haigo_header.ritsu_kihon = ritsu_kihon;
                        if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.seiho_copy) {
                            data_header = {
                                no_seiho_kaisha: App.num.format(Number(App.ui.page.user.cd_kaisha), "0000"),
                                no_seiho_shurui: page.header.element.findP("no_seiho_shurui").val(),
                                no_seiho_nen: new Date().getFullYear().toString().substr(2, 2),
                                no_seiho_renban: null,
                                nm_hin_syurui: page.values.resultComboboxNoSeihoShurui[result.ma_haigo_header.no_seiho.substr(5, 3)],
                                cd_seiho_bunrui: result.ma_haigo_header.cd_seiho_bunrui,
                                dt_toroku: null,
                                nm_user_toroku: null,
                                dt_henko: null,
                                nm_user_henko: null,
                                nm_seiho: result.ma_seiho.nm_seiho,
                                cd_haigo: null,
                                nm_status: page.header.element.findP("nm_status").text(),
                                flg_mishiyo: false,
                                kbn_haishi: 0,
                                nm_haigo: result.ma_haigo_header.nm_haigo,
                                nm_haigo_r: result.ma_haigo_header.nm_haigo_r,
                                kbn_hin: result.ma_haigo_header.kbn_hin,
                                ritsu_kihon: result.ma_haigo_header.ritsu_kihon,
                                kbn_vw: result.ma_haigo_header.kbn_vw,
                                nm_seihin: null,
                                no_seiho_sanko: result.ma_haigo_header.no_seiho,
                                cd_haigo_sanko: result.ma_haigo_header.cd_haigo,
                                nm_kojyo: result.addColumnHeader.nm_kojyo
                            }
                        }
                        else {
                            data_header = {
                                no_seiho_kaisha: result.ma_haigo_header.no_seiho.substr(0, 4),
                                no_seiho_shurui: result.ma_haigo_header.no_seiho.substr(5, 3),
                                no_seiho_nen: result.ma_haigo_header.no_seiho.substr(9, 2),
                                no_seiho_renban: result.ma_haigo_header.no_seiho.substr(12, 4),
                                nm_hin_syurui: page.values.resultComboboxNoSeihoShurui[result.ma_haigo_header.no_seiho.substr(5, 3)],
                                cd_seiho_bunrui: result.ma_haigo_header.cd_seiho_bunrui,
                                dt_toroku: result.ma_haigo_header.dt_toroku.substr(0, 10).replace(/-/g, "/") + " " + result.ma_haigo_header.dt_toroku.substr(11, 8),
                                nm_user_toroku: result.addColumnHeader.nm_user_toroku,
                                dt_henko: result.ma_haigo_header.dt_henko.substr(0, 10).replace(/-/g, "/") + " " + result.ma_haigo_header.dt_henko.substr(11, 8),
                                nm_user_henko: result.addColumnHeader.nm_user_henko,
                                nm_seiho: result.ma_seiho.nm_seiho,
                                cd_haigo: result.ma_haigo_header.cd_haigo,
                                nm_status: result.addColumnHeader.nm_status,
                                flg_mishiyo: result.ma_haigo_header.flg_mishiyo,
                                kbn_haishi: result.ma_haigo_header.kbn_haishi,
                                nm_haigo: result.ma_haigo_header.nm_haigo,
                                nm_haigo_r: result.ma_haigo_header.nm_haigo_r,
                                kbn_hin: result.ma_haigo_header.kbn_hin,
                                ritsu_kihon: result.ma_haigo_header.ritsu_kihon,
                                kbn_vw: result.ma_haigo_header.kbn_vw,
                                nm_seihin: result.addColumnHeader.nm_seihin,
                                no_seiho_sanko: result.ma_haigo_header.no_seiho_sanko,
                                cd_haigo_sanko: result.ma_haigo_header.cd_haigo_sanko,
                                nm_kojyo: result.addColumnHeader.nm_kojyo
                            }
                        }
                        page.header.bind(data_header);
                        

                        //Bind detail part.
                        var data_detail = [];
                        page.ma_haigo_meisai.data = App.ui.page.dataSet();
                        for (var i = 0; i < result.listHaigoMeisai.length; i++) {
                            var item = result.listHaigoMeisai[i],
                                addColumnMesai = result.listAddColumnMeisai[i],
                                haigo_meisai = {};

                            if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.seiho_copy) {
                                page.ma_haigo_meisai.data.add(item);
                            }
                            else {
                                page.ma_haigo_meisai.data.attach(item);
                            }

                            if (item.no_tonyu == App.settings.app.no_tonyu.isKotei) {
                                haigo_meisai.no_kotei = item.no_kotei;
                                haigo_meisai.no_tonyu = item.no_tonyu;
                                haigo_meisai.kotei_tonyu = item.no_kotei + "-" + item.no_tonyu;
                                haigo_meisai.nm_kotei = item.nm_hin;
                            }
                            else {
                                var mark = page.getObjMark(item.cd_mark, null);
                                if (mark && mark.cd_tani_shiyo == App.settings.app.taniShiyo.g) {
                                    item.qty_haigo = App.isUndefOrNull(item.qty_haigo)? null : new BigNumber((item.qty_haigo || 0).toString()).times(1000).toNumber();
                                }

                                haigo_meisai.no_kotei = item.no_kotei;
                                haigo_meisai.no_tonyu = item.no_tonyu;
                                haigo_meisai.kbn_bunkatsu = item.kbn_bunkatsu;
                                haigo_meisai.kotei_tonyu = item.no_kotei + "-" + item.no_tonyu;
                                haigo_meisai.cd_hin = item.cd_hin;
                                haigo_meisai.flg_shitei = item.flg_shitei;
                                haigo_meisai.nm_hin = item.nm_hin;
                                haigo_meisai.mark = mark ? mark.mark : "";
                                haigo_meisai.qty_haigo = page.getFixed3(item.qty_haigo);
                                // Detected change data when round with point 3
                                if (haigo_meisai.qty_haigo != item.qty_haigo) {
                                    item.qty_haigo = haigo_meisai.qty_haigo;
                                    page.ma_haigo_meisai.data.update(item);
                                }
                                haigo_meisai.qty_nisugata = page.getFixed3(item.qty_nisugata);
                                haigo_meisai.hijyu = addColumnMesai.hijyu;
                                haigo_meisai.budomari = item.budomari;
                                haigo_meisai.no_kikaku = addColumnMesai.no_kikaku;
                                haigo_meisai.gosa = item.gosa;
                                haigo_meisai.cd_mark = item.cd_mark;
                                haigo_meisai.kbn_shikakari = addColumnMesai.kbn_shikakari;
                                haigo_meisai.cd_tani_hin = addColumnMesai.cd_tani_hin;
                                haigo_meisai.kbn_hin = addColumnMesai.kbn_hin;
                            }
                            
                            haigo_meisai.__id = item.__id;
                            item.hasHinmei = addColumnMesai.hasHinmei;
                            data_detail.push(haigo_meisai);
                        }
                        page.detail.bind(data_detail);

                        //Bind footer
                        if(!result.ma_haigo_header.kbn_shiagari){
                            $(page.detail.element.findP("rdo_shiage")[1]).prop("checked",true);
                            //$(page.detail.element.findP("qty_haigo_kei")[1]).text(App.num.format(Number(result.ma_haigo_header.qty_haigo_kei),"#,0.000000"));
                            //$(page.detail.element.findP("qty_haigo_kei_user")[1]).val(App.num.format(Number(result.ma_haigo_header.qty_haigo_kei), "#,0.000000"));
                            page.detail.calculateQtyHaigoKei(true);
                            $(page.detail.element.findP("qty_haigo_kei_user")[1]).val($(page.detail.element.findP("qty_haigo_kei")[1]).text());
                            page.detail.element.findP("qty_haigo_kei_user")
                            $(page.detail.element.findP("qty_haigo_kei_user")[1]).prop("disabled", true);
                        }
                        else{
                            $(page.detail.element.findP("rdo_shiage_user")[1]).prop("checked",true);
                            page.detail.calculateQtyHaigoKei(true);
                            $(page.detail.element.findP("qty_haigo_kei_user")[1]).val(App.num.format(Number(result.ma_haigo_header.qty_haigo_kei),"#,0.000000"));
                        }
                        if(result.ma_haigo_header.kbn_vw == App.settings.app.cd_tani.kg){
                            $(page.detail.element.findP("tani_kei")[1]).text(App.settings.app.nm_tani.kg);
                            $(page.detail.element.findP("tani_kei_user")[1]).text(App.settings.app.nm_tani.kg);
                        }
                        else{
                            $(page.detail.element.findP("tani_kei")[1]).text(App.settings.app.nm_tani.l);
                            $(page.detail.element.findP("tani_kei_user")[1]).text(App.settings.app.nm_tani.l);
                        }
                        $(page.detail.element.findP("biko")[1]).val(App.isUndefOrNull(result.ma_haigo_header.biko) ? "" : result.ma_haigo_header.biko);

                        //Load tsuikaJohoNyuryokuDialog dialog. 
                        page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.cd_kaisha = result.ma_haigo_header.cd_kaisha_daihyo;
                        page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.cd_kojyo = result.ma_haigo_header.cd_kojyo_daihyo;
                        page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.no_haigo = result.ma_haigo_header.cd_haigo;
                        page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.kbn_hin = result.ma_haigo_header.kbn_hin;
                        if (result.ma_haigo_header.cd_kaisha_daihyo && (result.ma_haigo_header.status == App.settings.app.kbn_status.henshuchu || page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.seiho_copy)) {
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.flg_edit = 1
                        }
                        else {
                            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.flg_edit = 0
                        }
                        
                        page.dialogs.tsuikaJohoNyuryokuDialog.loadData(true);


                        if (page.values.M_HaigoTorokuKaihatsu != App.settings.app.m_haigo_toroku_kaihatsu.seiho_copy) {
                            //Load seihinJohoNyuryokuDialog dialog. 
                            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.cd_kaisha = result.ma_haigo_header.cd_kaisha_daihyo;
                            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.cd_kojyo = result.ma_haigo_header.cd_kojyo_daihyo;
                            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.no_haigo = result.ma_haigo_header.cd_haigo;
                            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.flg_shoki = 1

                            //if (result.ma_haigo_header.cd_kaisha_daihyo && result.ma_haigo_header.status == App.settings.app.kbn_status.henshuchu) {
                            if (result.ma_haigo_header.cd_kaisha_daihyo) {
                                page.dialogs.seihinJohoNyuryokuDialog.options.parameter.flg_edit = 1
                            }
                            else {
                                page.dialogs.seihinJohoNyuryokuDialog.options.parameter.flg_edit = 0
                            }
                            page.dialogs.seihinJohoNyuryokuDialog.loadData();
                        }
                        
                    }
                }).then(function () {
                    debugger
                    if (isDeleted) {
                    }

                    else if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.seiho_bunsho_copy) {
                        page.header.element.find("input").prop("disabled", true);
                        page.header.element.find("button").prop("disabled", true);
                        page.header.element.find("select").prop("disabled", true);
                        page.detail.element.find("input").prop("disabled", true);
                        page.detail.element.find("button").prop("disabled", true);

                        $("#csv").prop("disabled", true);
                        $("#save").prop("disabled", true);
                        page.header.element.find("#seizo-bunsho").hide();
                        $("#seizo-bunsho-sansho").prop("disabled", false);
                    }
                    else if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.shisaku_copy) {
                        page.header.element.find("input").prop("disabled", true);
                        page.header.element.find("button").prop("disabled", true);
                        page.header.element.find("select").prop("disabled", true);
                        page.detail.element.find("input").prop("disabled", true);
                        page.detail.element.find("button").prop("disabled", true);

                        $("#csv").prop("disabled", true);
                        $("#save").prop("disabled", true);
                        page.header.element.find("#seizo-bunsho").hide();
                        $("#shisaku-copy").show();
                    }
                    else if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.seiho_copy) {
                        page.header.element.findP("no_seiho_kaisha").prop("disabled", true);
                        page.header.element.findP("no_seiho_renban").prop("disabled", true);
                        page.header.element.find("#shiryo-tenpu").prop("disabled", true);
                        page.header.element.find("#seizo-bunsho").prop("disabled", true);
                        $("#csv").prop("disabled", true);
                        page.header.element.find("#seizo-bunsho-sansho").hide();
                    }
                    else
                    {
                        if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.shosai)
                        {
                            page.header.element.find("#excel-haigo-list").show();
                            // For リニューアル元選択
                            page.header.element.find("#moto-sentaku").show();
                        }
                        if (result.ma_haigo_header.cd_toroku == App.ui.page.user.EmployeeCD || page.values.kengen_kino_ta_haigo)
                        {
                            if (page.values.flg_edit_shikakarihin) {
                                if (result.ma_haigo_header.status == App.settings.app.kbn_status.henshuchu) {
                                    //seiho part
                                    page.header.element.findP("no_seiho_kaisha").prop("disabled", true);
                                    page.header.element.findP("no_seiho_shurui").prop("disabled", true);
                                    page.header.element.findP("no_seiho_nen").prop("disabled", true);
                                    page.header.element.findP("no_seiho_renban").prop("disabled", true);
                                    page.header.element.findP("cd_seiho_bunrui").prop("disabled", true);
                                    page.header.element.findP("nm_seiho").prop("disabled", true);
                                    page.header.element.find("#seizo-kojo-shitei").prop("disabled", true);
                                    page.header.element.find("#shiryo-tenpu").prop("disabled", true);
                                    page.header.element.find("#seizo-bunsho").prop("disabled", true);
                                    page.header.element.find("#seizo-bunsho-sansho").hide();

                                    //haigo part
                                    page.header.element.findP("flg_mishiyo").prop("disabled", true);
                                    page.header.element.findP("kbn_haishi").prop("disabled", true);
                                    page.header.element.findP("nm_haigo").prop("disabled", true);
                                    page.header.element.findP("nm_haigo_r").prop("disabled", true);
                                    page.header.element.findP("kbn_hin").prop("disabled", true);
                                    page.header.element.findP("ritsu_kihon").prop("disabled", true);
                                    page.header.element.find("input[type=radio]").prop("disabled", true);
                                }
                                else {
                                    //seiho part
                                    page.header.element.findP("no_seiho_kaisha").prop("disabled", true);
                                    page.header.element.findP("no_seiho_shurui").prop("disabled", true);
                                    page.header.element.findP("no_seiho_nen").prop("disabled", true);
                                    page.header.element.findP("no_seiho_renban").prop("disabled", true);
                                    page.header.element.findP("cd_seiho_bunrui").prop("disabled", true);
                                    page.header.element.findP("nm_seiho").prop("disabled", true);
                                    page.header.element.find("#seizo-kojo-shitei").prop("disabled", true);
                                    page.header.element.find("#shiryo-tenpu").prop("disabled", true);
                                    page.header.element.find("#haigoTorikomi-dialog").prop("disabled", true);
                                    page.header.element.find("#seizo-bunsho-sansho").hide();

                                    //haigo part
                                    page.header.element.findP("flg_mishiyo").prop("disabled", true);
                                    page.header.element.findP("kbn_haishi").prop("disabled", true);
                                    page.header.element.findP("nm_haigo").prop("disabled", true);
                                    page.header.element.findP("nm_haigo_r").prop("disabled", true);
                                    page.header.element.findP("kbn_hin").prop("disabled", true);
                                    page.header.element.findP("ritsu_kihon").prop("disabled", true);
                                    page.header.element.find("input[type=radio]").prop("disabled", true);

                                    //detail part
                                    page.detail.element.find("input").prop("disabled", true);
                                    page.detail.element.find("button").prop("disabled", true);

                                    $("#save").prop("disabled", true);
                                }
                            }
                            else {
                                if (result.ma_haigo_header.status != App.settings.app.kbn_status.henshuchu) {

                                    $("#title-mode").text("参　照")
                                    //seiho part
                                    page.header.element.findP("no_seiho_kaisha").prop("disabled", true);
                                    page.header.element.findP("no_seiho_shurui").prop("disabled", true);
                                    page.header.element.findP("no_seiho_nen").prop("disabled", true);
                                    page.header.element.findP("no_seiho_renban").prop("disabled", true);
                                    page.header.element.findP("cd_seiho_bunrui").prop("disabled", true);
                                    page.header.element.findP("nm_seiho").prop("disabled", true);
                                    page.header.element.find("#haigoTorikomi-dialog").prop("disabled", true);
                                    page.header.element.find("#seizo-bunsho-sansho").hide();

                                    //haigo part
                                    page.header.element.findP("flg_mishiyo").prop("disabled", true);
                                    page.header.element.findP("nm_haigo").prop("disabled", true);
                                    page.header.element.findP("nm_haigo_r").prop("disabled", true);
                                    page.header.element.findP("kbn_hin").prop("disabled", true);
                                    page.header.element.findP("ritsu_kihon").prop("disabled", true);
                                    page.header.element.find("input[type=radio]").prop("disabled", true);

                                    //detail part
                                    page.detail.element.find("#add-kotei").prop("disabled", true);
                                    page.detail.element.find("#move-kotei-up").prop("disabled", true);

                                    page.detail.element.find("#move-kotei-down").prop("disabled", true);
                                    page.detail.element.find("#del-kotei").prop("disabled", true);
                                    page.detail.element.find("#add-tonyu").prop("disabled", true);
                                    page.detail.element.find("#move-tonyu-up").prop("disabled", true);
                                    page.detail.element.find("#move-tonyu-down").prop("disabled", true);
                                    page.detail.element.find("#del-tonyu").prop("disabled", true);

                                    page.detail.element.find("input").prop("disabled", true);
                                    page.detail.element.find(".cd_hin").prop("disabled", true);
                                    page.detail.element.find(".mark").prop("disabled", true);
                                }
                                else {
                                    $("#title-mode").text("編　集")
                                    page.header.element.findP("no_seiho_kaisha").prop("disabled", true);
                                    page.header.element.findP("no_seiho_shurui").prop("disabled", true);
                                    page.header.element.findP("no_seiho_nen").prop("disabled", true);
                                    page.header.element.findP("no_seiho_renban").prop("disabled", true);
                                    page.header.element.find("#seizo-bunsho-sansho").hide();
                                }
                            }
                        }
                        else {
                            if (!page.values.flg_edit_shikakarihin) {
                                if (result.ma_haigo_header.status != App.settings.app.kbn_status.henshuchu) {
                                    $("#title-mode").text("参　照")
                                }
                                else {
                                    $("#title-mode").text("編　集")
                                }
                            }

                            page.values.modeReadOnly = true;
                            page.header.element.find("input").prop("disabled", true);
                            page.header.element.find("button").prop("disabled", true);
                            page.header.element.find("select").prop("disabled", true);
                            page.detail.element.find("input").prop("disabled", true);
                            page.detail.element.find("button").prop("disabled", true);
                            
                            $("#save").prop("disabled", true);
                            page.header.element.find("#seizo-bunsho-sansho").hide();

                            page.header.element.find("#seizo-kojo-shitei").prop("disabled", false);
                            page.header.element.find("#excel-haigo-list").prop("disabled", false);
                            page.header.element.find("#tsuikaJohoNyuryoku-dialog").prop("disabled",false);
                            page.header.element.find("#seihinJohoNyuryoku-dialog").prop("disabled", false);
                            page.header.element.find("#shiryo-tenpu").prop("disabled", false);
                            page.header.element.find("#seizo-bunsho").prop("disabled", false);
                        }
                    }

                    deferred.resolve();
                }).fail(function (error) {
                    deferred.reject(error);
                })
                return deferred.promise();
            }

            //MODE HAIGO TOROKU
            else if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.haigo_shinki) {

                var deferred = $.Deferred(),
                    result = {},
                    parameter = {
                        no_seiho: page.values.no_seiho,
                        cd_haigo: page.values.cd_haigo
                    };

                return $.ajax(App.ajax.webapi.get(page.urls.haigoTorokuKaihatsuBumon + "/getDataModeHaigoShinki", parameter)
                ).then(function (result_seiho) {
                    result = result_seiho;
                    page.values.cd_kaisha_daihyo = result.addColumnHeader.cd_kaisha_daihyo;
                    page.values.cd_kojyo_daihyo = result.addColumnHeader.cd_kojyo_daihyo;
                    page.values.qty_kihon = result.addColumnHeader.qty_kihon;
                    page.values.su_code_standard = result.addColumnHeader.su_code_standard;
                    page.detail.options.validations.cd_hin.rules.maxlength = Number(page.values.su_code_standard) + 1;
                    page.detail.element.find(".hinmei-input").prop("maxlength", Number(page.values.su_code_standard) + 1);
                    page.values.haveHaigo = result.addColumnHeader.haveHaigo;

                    page.ma_seiho.data = App.ui.page.dataSet();
                    page.ma_seiho.data.attach(result.ma_seiho);
                    page.ma_seiho.data.update(result.ma_seiho);
                    page.ma_seiho.dataId = result.ma_seiho.__id;

                    var data_header = {
                        no_seiho: page.values.no_seiho,
                        kbn_vw: App.settings.app.cd_tani.kg,
                        flg_mishiyo: false,
                        kbn_haishi: 0,
                        kbn_shiagari: 0,
                        status: App.settings.app.kbn_status.henshuchu,
                        qty_haigo_kei: 0,
                        cd_seiho_bunrui: result.cd_seiho_bunrui
                    }
                    page.ma_haigo_header.data = App.ui.page.dataSet();
                    page.ma_haigo_header.data.add(data_header);
                    page.ma_haigo_header.dataId = data_header.__id;
                    page.header.element.findP("cd_seiho_bunrui").val(result.cd_seiho_bunrui);

                    page.ma_haigo_meisai.data = App.ui.page.dataSet();

                    page.ma_seiho_denso.data = App.ui.page.dataSet();
                    for (var i = 0; i < result.listSeihoDenso.length; i++) {
                        var item = result.listSeihoDenso[i];
                        page.ma_seiho_denso.data.attach(item);
                    }
                    return page.loadMasterWhenChangeKaishaKojyo()
                    
                }).then(function () {
                    var no_seiho_kaisha = result.ma_seiho.no_seiho.substr(0, 4),
                        no_seiho_shurui = result.ma_seiho.no_seiho.substr(5, 3),
                        no_seiho_nen = result.ma_seiho.no_seiho.substr(9, 2),
                        no_seiho_renban = result.ma_seiho.no_seiho.substr(12, 4),
                        nm_hin_syurui = page.values.resultComboboxNoSeihoShurui[result.ma_seiho.no_seiho.substr(5, 3)],
                        nm_seiho = result.ma_seiho.nm_seiho,
                        nm_kojyo = result.addColumnHeader.nm_kojyo;

                    //bind data
                    page.header.element.findP("no_seiho_kaisha").val(no_seiho_kaisha);
                    page.header.element.findP("no_seiho_shurui").val(no_seiho_shurui);
                    page.header.element.findP("no_seiho_nen").val(no_seiho_nen);
                    page.header.element.findP("no_seiho_renban").val(no_seiho_renban);
                    page.header.element.findP("nm_hin_syurui").text(App.isUndefOrNull(nm_hin_syurui) ? "" : nm_hin_syurui);
                    page.header.element.findP("nm_seiho").val(nm_seiho);
                    page.header.element.findP("nm_kojyo").text(nm_kojyo).css("color", "");

                    //disable/enable
                    page.header.element.findP("no_seiho_kaisha").prop("disabled", true);
                    page.header.element.findP("no_seiho_shurui").prop("disabled", true);
                    page.header.element.findP("no_seiho_nen").prop("disabled", true);
                    page.header.element.findP("no_seiho_renban").prop("disabled", true);
                    page.header.element.find("#shiryo-tenpu").prop("disabled", true);
                    page.header.element.find("#seizo-bunsho").prop("disabled", true);
                    page.header.element.find("#seizo-bunsho-sansho").hide();

                    $("#csv").prop("disabled", true);

                    $(page.detail.element.findP("qty_haigo_kei_user")[1]).prop("disabled", true);

                    page.detail.addNewKotei(true);

                    //Load tsuikaJohoNyuryokuDialog dialog. 
                    page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.cd_kaisha = result.addColumnHeader.cd_kaisha_daihyo;
                    page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.cd_kojyo = result.addColumnHeader.cd_kojyo_daihyo;
                    page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.no_haigo = null;
                    page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.kbn_hin = page.header.element.findP("kbn_hin").val();
                    page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.flg_edit = 1
                    page.dialogs.tsuikaJohoNyuryokuDialog.loadData(true);

                    deferred.resolve();
                }).fail(function (error) {
                    deferred.reject(error);
                })

                return deferred.promise();
            }
            else {
                page.detail.element.find("#move-tonyu-up").prop("disabled", true);
                page.detail.element.find("#move-tonyu-down").prop("disabled", true);
                page.detail.element.find("#del-tonyu").prop("disabled", true)
                page.detail.element.find("input[type=radio]").prop("disabled", true);
                $(page.detail.element.findP("qty_haigo_kei_user")[1]).prop("disabled", true);
                $(page.detail.element.findP("biko")[1]).prop("disabled", true);
            }
        };

        /**
         * Load data master when change cd_kaisha_daihyo, cd_kojyo_daihyo
         */
        page.loadMasterWhenChangeKaishaKojyo = function () {
            var deferred = $.Deferred();
            return page.loadMarkData().then(function () {
                return page.header.loadKbnHin();
            }).then(function () {
                return page.header.loadRitsuKihon();
            }).then(function () {
                return page.loadTaniData();
            }).then(function () {
                return page.detail.checkExistsHinmei();
            }).then(function () {
                deferred.resolve();
            }).fail(function (error) {
                deferred.reject(error);
            })
            return deferred.promise();

        }

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
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.validations = {
            //TODO: 画面ヘッダーのバリデーションの定義を記述します。
            no_seiho_kaisha: {
                rules: {
                    required: true,
                    digits: true,
                    maxlength: 4,
                    checkExistsKaisha: function (value, opts, state, done) {
                        var element = page.header.element,
                            target = element.findP("no_seiho_kaisha");
                        return done(!target.val() || target.data("existKaisha"));
                    }
                },
                options: {
                    name: "製法番号（会社コード）"
                },
                messages: {
                    required: App.messages.base.required,
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength,
                    checkExistsKaisha: App.messages.app.AP0010
                }
            },

            no_seiho_shurui: {
                rules: {
                    required: true,
                },
                options: {
                    name: "製法番号（管理番号）"
                },
                messages: {
                    required: App.messages.base.required,
                }
            },

            no_seiho_nen: {
                rules: {
                    required: true,
                    digits: true,
                    maxlength: 2
                },
                options: {
                    name: "製法番号（年）"
                },
                messages: {
                    required: App.messages.base.required,
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength
                }
            },

            nm_seiho: {
                rules: {
                    required: true,
                    maxbytelength: 120
                },
                options: {
                    name: "製法名",
                    byte: 60
                },
                messages: {
                    required: App.messages.base.required,
                    maxbytelength: App.messages.base.maxbytelength
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
                    maxbytelength: 38
                },
                options: {
                    name: "配合名略称",
                    byte: 19
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
        };

        /**
         * 画面ヘッダーの初期化処理を行います。
         */
        page.header.initialize = function () {

            var element = $(".header");
            page.header.validator = element.validation(page.createValidator(page.header.options.validations));
            element.on("change", ":input", page.header.change);
            page.header.element = element;
            
            
            element.on("click", "#excel-haigo-list", page.header.downloadHaigoListExcel);
            element.on("click", "#seizo-kojo-shitei", page.header.showSeizoKojoShiteiDialog);
            element.on("click", "#tsuikaJohoNyuryoku-dialog", page.header.showTsuikaJohoNyuryokuDialog);
            element.on("click", "#seihinJohoNyuryoku-dialog", page.header.showSeihinJohoNyuryokuDialog);
            element.on("click", "#haigoTorikomi-dialog", page.header.showHaigoTorikomiDialog);
            element.on("click", "#seizo-bunsho", page.header.showSeihoBunshoSakuseiDialog);
            element.on("click", "#seizo-bunsho-sansho", page.header.moveSeihoBunshoSakuseiGamen);
            element.on("click", "#shiryo-tenpu", page.header.moveTenpuBunshoGamen);
            element.on("click", "#moto-sentaku", page.header.showRenewalMotoSentakuDialog);

            element.findP("no_seiho_kaisha").data("existKaisha", true);
        };

        /**
         * 画面ヘッダーへのデータバインド処理を行います。
         */
        page.header.bind = function (data, isNewData) {

            var element = page.header.element;

            page.header.element.form(page.header.options.bindOption).bind(data);

            if (data.kbn_vw == App.settings.app.cd_tani.kg) {
                page.header.element.findP("rdoVWKbnKg").prop("checked", true);
            }
            else {
                page.header.element.findP("rdoVWKbnL").prop("checked", true);
            }
            
        };

        /**
        * 画面ヘッダーにデータを設定する際のオプションを定義します。
        */
        page.header.options.bindOption = {
            appliers: {
                nm_kojyo: function (value, element) {
                    if (App.isUndefOrNull(value)) {
                        value = "代表工場を指定ください";
                        page.header.element.findP("nm_kojyo").css("color", "red");
                    }
                    else {
                        page.header.element.findP("nm_kojyo").css("color", "");
                    }
                    element.text(value);
                    return true;
                },
                ritsu_kihon: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.val("");
                    }
                    else {
                        element.val(App.num.format(Number(value),"#,#.00"))
                    }
                    return true;
                }
            }
        };

        /**
         * Check if cd_kaisha is existed
         */
        page.header.getKaisha = function () {
            var element = page.header.element,
                target = element.findP("no_seiho_kaisha");
            target.data("existKaisha", false);
            
            var cd_kaisha = target.val();
            if (!cd_kaisha || isNaN(Number(cd_kaisha))) {
                return App.async.success();
            }
            cd_kaisha = parseInt(cd_kaisha);
            return $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.ma_kaisha, cd_kaisha), {}, { async: false })).then(function (result) {
                if (result && result.value.length) {
                    target.data("existKaisha", true);
                }
            });
        }

        /**
         * 画面ヘッダーにある入力項目の変更イベントの処理を行います。
         */
        page.header.change = function (e) {
            var element = page.header.element,
                target = $(e.target),
                id_haigo_header = page.ma_haigo_header.dataId,
                id_seiho = page.ma_seiho.dataId,
                property = target.attr("data-prop"),
                entity_haigo_header = page.ma_haigo_header.data.entry(id_haigo_header),
                entity_seiho = page.ma_seiho.data.entry(id_seiho),
                data = element.form().data();

            page.values.isChangeRunning[property] = true;

            $("#save").prop("disabled", false);

            if (property == "no_seiho_kaisha") {
                if (target.val() != "" && Number(target.val()) == 0) {
                    target.val("");
                }
            }

            // SKS 2020 Modified - Add check exist cd_kaisha
            (property == "no_seiho_kaisha" ? page.header.getKaisha() : App.async.success()).always(function () {
                element.validation().validate({
                    targets: target
                }).then(function () {
                    if (property == "no_seiho_kaisha" || property == "no_seiho_nen") {
                        if (property == "no_seiho_kaisha") {
                            var valOld = target.val(),
                            valNew = App.num.format(Number(valOld), "0000");
                            target.val(valNew);
                        }
                        if (property == "no_seiho_nen") {
                            var valOld = target.val(),
                            valNew = App.num.format(Number(valOld), "00");
                            target.val(valNew);
                        }

                        element.validation().validate({
                            targets: element.find(".no-seiho-group")
                        })
                    }
                    else if (property == "no_seiho_shurui") {
                        page.header.element.findP("nm_hin_syurui").text(page.values.resultComboboxNoSeihoShurui[page.header.element.findP("no_seiho_shurui").val()]);
                        element.validation().validate({
                            targets: element.find(".no-seiho-group")
                        })
                    }
                    else if (property == "nm_seiho") {
                        entity_seiho[property] = data[property];
                        page.ma_seiho.data.update(entity_seiho);
                    }
                    else if (property == "flg_mishiyo") {
                        if (target.prop("checked")) {
                            entity_haigo_header[property] = true;
                        }
                        else {
                            entity_haigo_header[property] = false;
                        }
                    }
                    else if (property == "kbn_haishi") {
                        if (target.prop("checked")) {
                            entity_haigo_header[property] = 1;
                        }
                        else {
                            entity_haigo_header[property] = 0;
                        }
                    }
                    else if (property == "kbn_hin") {
                        if (target.val() == App.settings.app.kbnHin.shikakari) {
                            if (page.values.cd_kaisha_daihyo && (App.isUndefOrNull(entity_haigo_header["status"]) || entity_haigo_header["status"] == App.settings.app.kbn_status.henshuchu)) {
                                page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").prop("disabled", false);
                                page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("flg_gasan").prop("disabled", false);
                            }
                        }
                        else {
                            page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").prop("disabled", true).val("")
                            page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("flg_gasan").prop("disabled", true).prop("checked", false);
                        }
                    }

                    else if (property == "rdoVWKbnKg") {
                        entity_haigo_header["kbn_vw"] = App.settings.app.cd_tani.kg;
                        $(page.detail.element.findP("tani_kei")[1]).text(App.settings.app.nm_tani.kg);
                        $(page.detail.element.findP("tani_kei_user")[1]).text(App.settings.app.nm_tani.kg);
                        page.detail.calculateQtyHaigoKei();
                    }
                    else if (property == "rdoVWKbnL") {
                        entity_haigo_header["kbn_vw"] = App.settings.app.cd_tani.l;
                        $(page.detail.element.findP("tani_kei")[1]).text(App.settings.app.nm_tani.l);
                        $(page.detail.element.findP("tani_kei_user")[1]).text(App.settings.app.nm_tani.l);
                        page.detail.calculateQtyHaigoKei();
                    }
                    else if (property == "ritsu_kihon") {
                        var valOld = target.val(),
                            valNew = App.num.format(Number(valOld), "#,#.00");
                        target.val(valNew);
                        entity_haigo_header[property] = valNew;
                    }
                    else {
                        entity_haigo_header[property] = data[property];
                    }
                    page.ma_haigo_header.data.update(entity_haigo_header);
                }).always(function () {
                    delete page.values.isChangeRunning[property];
                });
            });
            
        };

        /**
        * Load combobox kbn_hin in header.
        */
        page.header.loadKbnHin = function () {
            var deferred = $.Deferred();
            if (!page.values.hasLoadKbnHin) {
                var strFilter = "";
                if (!page.values.haveHaigo) {
                    strFilter = " or kbn_hin eq " + App.settings.app.kbnHin.haigo;
                }

                return $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.ma_kbn_hin , App.settings.app.kbnHin.shikakari) + strFilter)
                    ).then(function (result_kbn_hin) {
                        var kbn_hin = page.header.element.findP("kbn_hin");
                        kbn_hin.children().remove();
                        App.ui.appendOptions(
                            kbn_hin,
                            "kbn_hin",
                            "nm_kbn_hin",
                            result_kbn_hin.value
                        );
                        page.values.hasLoadKbnHin = true;
                        deferred.resolve();
                    }).fail(function (error) {
                        deferred.reject(error);
                    })
            } else {
                deferred.resolve();
            }

            return deferred.promise();
        }

        /**
        * Load ritsu_kihon
        */
        page.header.loadRitsuKihon = function () {
            var deferred = $.Deferred();
            return $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.vw_ma_kojyo, page.values.cd_kaisha_daihyo, page.values.cd_kojyo_daihyo))).then(function (result_kojyo) {
                if (result_kojyo.value[0]) {

                    page.header.element.findP("ritsu_kihon").val(App.num.format(Number(result_kojyo.value[0].ritsu_kihon),"#,#.00"))
                    if ((result_kojyo.value[0].kbn_hin == App.settings.app.kbnHin.haigo || result_kojyo.value[0].kbn_hin == App.settings.app.kbnHin.shikakari) && !page.values.haveHaigo) {
                        page.header.element.findP("kbn_hin").val(result_kojyo.value[0].kbn_hin);
                    }
                    var id = page.ma_haigo_header.dataId,
                        entity = page.ma_haigo_header.data.entry(id);

                    entity["ritsu_kihon"] = page.header.element.findP("ritsu_kihon").val();
                    entity["kbn_hin"] = page.header.element.findP("kbn_hin").val();
                    page.ma_haigo_header.data.update(entity);

                    if (page.header.element.findP("kbn_hin").val() == App.settings.app.kbnHin.shikakari) {
                        if (page.values.cd_kaisha_daihyo && (App.isUndefOrNull(entity["status"]) || entity["status"] == App.settings.app.kbn_status.henshuchu)) {
                            page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").prop("disabled", false);
                            page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("flg_gasan").prop("disabled", false);
                        }
                    }
                    else {
                        page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").prop("disabled", true).val("")
                        page.dialogs.tsuikaJohoNyuryokuDialog.element.findP("flg_gasan").prop("disabled", true).prop("checked", false);
                    }
                    
                }
                deferred.resolve();
            }).fail(function (error) {
                deferred.reject(error);
            })
            return deferred.promise();
        }

        //TODO: 以下の page.header の各宣言は画面仕様ごとにことなるため、不要の場合は削除してください。
        /**
        * Download excel file content the haigo list like TAB 10 in page [300]
        */
        page.header.downloadHaigoListExcel = function () {
            var element = page.header.element,
                no_seiho_exc = element.findP("no_seiho_kaisha").val() + "-"
                               + element.findP("no_seiho_shurui").val() + "-"
                               + element.findP("no_seiho_nen").val() + "-"
                               + element.findP("no_seiho_renban").val(),
                excelFilter = {
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
            // CSV出力（Ajax通信でファイルstreamを返却）
            return $.ajax(App.ajax.file.download(page.urls.excelHiagoList, excelFilter, "POST")
            ).then(function (response, status, xhr) {
                if (status !== "success") {
                    App.ui.page.notifyAlert.message(App.messages.base.MS0005).show();
                } else {
                    App.file.save(response, App.ajax.file.extractFileNameDownload(xhr) || "ExcelFile.csv");

                    //TOsVN - 19075 - 2023/06/20: st ShohinKaihatuSupport Modify 2023
                    App.common.Log.write({
                        cd_game: App.settings.app.log_mode.gamen.HaigoTorokuExcel
                        , cd_taisho_data: no_seiho_exc
                    });
                    //ed
                }
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

            }).always(function () {
                App.ui.loading.close();
            });
        }

        /**
        * Show SeizoKojoShitei dialog
        */
        page.header.showSeizoKojoShiteiDialog = function () {
            var element = page.header.element,
                id = page.ma_haigo_header.dataId,
                entity = page.ma_haigo_header.data.entry(id);
            
            if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.seiho_copy) {
                page.dialogs.seizoKojoShiteiDialog.options.parameter.no_seiho = null
                page.dialogs.seizoKojoShiteiDialog.options.parameter.cd_kaisha = page.values.cd_kaisha_daihyo;
                page.dialogs.seizoKojoShiteiDialog.options.parameter.cd_kojyo = page.values.cd_kojyo_daihyo;
                page.dialogs.seizoKojoShiteiDialog.options.parameter.flg_daihyo_kojyo = 1
                if (page.detail.checkNotHaveDataDetail()) {
                    page.dialogs.seizoKojoShiteiDialog.options.parameter.flg_edit = 1;
                }
                else {
                    page.dialogs.seizoKojoShiteiDialog.options.parameter.flg_edit = 0;
                }

                if (page.dialogs.seizoKojoShiteiDialog.options.parameter.flg_shoki) {
                    page.dialogs.seizoKojoShiteiDialog.options.parameter.flg_edit = 1
                }

                page.dialogs.seizoKojoShiteiDialog.options.parameter.lstKojyo = page.values.lstKojyo;
            }

            else
            {
                page.dialogs.seizoKojoShiteiDialog.options.parameter.no_seiho = page.values.no_seiho;
                if (page.dialogs.seizoKojoShiteiDialog.options.parameter.flg_shoki) {
                    page.dialogs.seizoKojoShiteiDialog.options.parameter.cd_kaisha = null;
                    page.dialogs.seizoKojoShiteiDialog.options.parameter.cd_kojyo = null;
                    page.dialogs.seizoKojoShiteiDialog.options.parameter.flg_daihyo_kojyo = null;
                }
                else {
                    page.dialogs.seizoKojoShiteiDialog.options.parameter.cd_kaisha = null;
                    page.dialogs.seizoKojoShiteiDialog.options.parameter.cd_kojyo = null;
                    page.dialogs.seizoKojoShiteiDialog.options.parameter.flg_daihyo_kojyo = null
                    page.dialogs.seizoKojoShiteiDialog.options.parameter.flg_denso_jyotai = null
                    for (var i = 0; i < page.values.lstKojyo.length; i++) {
                        var item = page.values.lstKojyo[i];
                        if (item.flg_daihyo_kojyo) {
                            page.dialogs.seizoKojoShiteiDialog.options.parameter.cd_kaisha = item.cd_kaisha;
                            page.dialogs.seizoKojoShiteiDialog.options.parameter.cd_kojyo = item.cd_kojyo;
                            page.dialogs.seizoKojoShiteiDialog.options.parameter.flg_daihyo_kojyo = 1
                            if (item.flg_denso_jyotai) {
                                page.dialogs.seizoKojoShiteiDialog.options.parameter.flg_denso_jyotai = 1;
                            }
                        }
                    }
                }

                if (page.detail.checkNotHaveDataDetail()) {
                    page.dialogs.seizoKojoShiteiDialog.options.parameter.flg_edit = 1;
                }
                else {
                    page.dialogs.seizoKojoShiteiDialog.options.parameter.flg_edit = 0;
                }
                if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.haigo_shinki) {
                    page.dialogs.seizoKojoShiteiDialog.options.parameter.flg_edit = 0;
                }

                if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.shosai && page.values.countNumberHaigo > 0) {
                    page.dialogs.seizoKojoShiteiDialog.options.parameter.flg_edit = 0;
                }


                page.dialogs.seizoKojoShiteiDialog.options.parameter.lstKojyo = page.values.lstKojyo;
            }
            
            page.dialogs.seizoKojoShiteiDialog.options.parameter.modeReadOnly = page.values.modeReadOnly;

            page.dialogs.seizoKojoShiteiDialog.element.modal("show");

            page.values.isShowSeizoKojoShiteiDialog = true;
        };

        /**
        * Show TsuikaJohoNyuryoku dialog
        */
        page.header.showTsuikaJohoNyuryokuDialog = function () {
            //検索ダイアログで選択が実行された時に呼び出される関数を設定しています。
            page.dialogs.tsuikaJohoNyuryokuDialog.options.parameter.modeReadOnly = page.values.modeReadOnly;
            page.dialogs.tsuikaJohoNyuryokuDialog.element.modal("show");
           
        }

        /**
        * Show SehinJohoNyuryoku dialog
        */
        page.header.showSeihinJohoNyuryokuDialog = function () {
            //検索ダイアログで選択が実行された時に呼び出される関数を設定しています。

            page.dialogs.seihinJohoNyuryokuDialog.options.parameter.modeReadOnly = page.values.modeReadOnly701;

            if (!page.values.modeReadOnly701) {
                var kbn_hin = page.header.element.findP("kbn_hin").val();
                if (kbn_hin == App.settings.app.kbnHin.shikakari) {
                    if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.shosai
                        || page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.seiho_copy) {
                        var id = page.ma_haigo_header.dataId,
                            entity = page.ma_haigo_header.data.entry(id);
                    }
                }
            }
            page.dialogs.seihinJohoNyuryokuDialog.element.modal("show");
           
        }

        /**
        * Show リニューアル元選択 dialog
        */
        page.header.showRenewalMotoSentakuDialog = function () {
            var haigoHeader = page.ma_haigo_header.data.entry(page.ma_haigo_header.dataId)
            page.dialogs.RenewalMotoSentakuDialog.options.kbn_hin = haigoHeader ? haigoHeader.kbn_hin : 0;
            page.dialogs.RenewalMotoSentakuDialog.element.modal("show");

        }
        /**
        * Show HaigoTorikomi dialog
        */
        page.header.showHaigoTorikomiDialog = function () {
            page.dialogs.haigoTorikomiDialog.options.parameter.no_gamen = App.settings.app.no_gamen.haigo_toroku_kaihatsu;
            page.dialogs.haigoTorikomiDialog.options.parameter.cd_kaisha = page.values.cd_kaisha_daihyo;
            page.dialogs.haigoTorikomiDialog.options.parameter.cd_kojyo = page.values.cd_kojyo_daihyo;
            page.dialogs.haigoTorikomiDialog.options.parameter.M_kirikae = page.values.M_HaigoTorokuKaihatsu;
            page.dialogs.haigoTorikomiDialog.element.modal("show");
        }

        /**
        * Show SeihoBunshoSakuseiDialog
        */
        page.header.showSeihoBunshoSakuseiDialog = function () {
            var id = page.ma_haigo_header.dataId,
                entity = page.ma_haigo_header.data.entry(id),
                no_seiho = entity["no_seiho"];
            if (App.isUndefOrNull(entity["status"]) || entity["status"] == App.settings.app.kbn_status.henshuchu) {
                page.dialogs.seihoBunshoSakuseiDialog.param.no_seiho = no_seiho;
                page.dialogs.seihoBunshoSakuseiDialog.param.no_seiho_sakusei = no_seiho;
                page.dialogs.seihoBunshoSakuseiDialog.param.no_seiho_copy = App.isUndefOrNull(entity["no_seiho_sanko"]) ? null : entity["no_seiho_sanko"]
                page.dialogs.seihoBunshoSakuseiDialog.element.modal("show");
            }   
            else {
                App.ui.loading.show();
                $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_seiho_bunsho_hyoshi, no_seiho))).then(function (result) {

                    if (result.value.length) {
                        App.common.openSeihoBunsho
                        ({
                            mode: App.settings.app.m_seiho_bunsho.etsuran,
                            no_seiho: no_seiho,
                            no_seiho_sakusei: no_seiho,
                            no_seiho_copy: App.isUndefOrNull(entity["no_seiho_sanko"]) ? null : entity["no_seiho_sanko"],
                            nm_seiho_bunsho: App.settings.app.m_seiho_bunsho.etsuran,
                            no_gamen: App.settings.app.no_gamen.haigo_toroku_kaihatsu
                        })
                    }
                    else {
                        App.ui.page.notifyAlert.remove("AP0125");
                        App.ui.page.notifyAlert.message(App.messages.app.AP0125,"AP0125").show();
                    }
                }).fail(function (error) {
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                }).always(function () {
                    App.ui.loading.close();
                })
            }
        }
        
        /**
        * Move to SeihoBunshoSakusei
        */
        page.header.moveSeihoBunshoSakuseiGamen = function () {
            var id = page.ma_haigo_header.dataId,
                entity = page.ma_haigo_header.data.entry(id),
                no_seiho = entity["no_seiho"];
            App.common.openSeihoBunsho
            ({
                mode: App.settings.app.m_seiho_bunsho.copy,
                no_seiho: no_seiho,
                no_seiho_sakusei: page.values.no_seiho_sakusei,
                no_seiho_copy: page.values.no_seiho_copy
            })
        }

        /**
        * Move to TenpuBunsho
        */
        page.header.moveTenpuBunshoGamen = function () {
            var id = page.ma_haigo_header.dataId,
                entity = page.ma_haigo_header.data.entry(id),
                no_seiho = entity["no_seiho"];

            var link = "211_TenpuBunsho.aspx?no_seiho=" + no_seiho;
            window.open(link);
            
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
                            var tbody = state.tbody;
                            if (tbody.hasClass("row-error-past")) {
                                return done(false);
                            }
                        }
                        return done(true);
                    },
                    required: true,
                    digits: true,
                    maxlength:7,
                    checkExistsHimmei: function (value, opts, state, done) {
                        if (state && state.check) {
                            if (value >= page.values.shijiMin && value <= page.values.shijiMax) {
                                return done(true);
                            }
                            var tbody = state.tbody.element ? state.tbody.element : state.tbody,
                                cd_hin = tbody.findP("cd_hin");

                            if (tbody.hasClass("row-error-past-css") || tbody.hasClass("row-error-past")) {
                                return done(true);
                            }

                            return done(tbody.findP("kbn_shikakari").text() != "");
                        }
                        return done(true);
                    },
                },
                options: {
                    name: "コード"
                },
                messages: {
                    checkErrorRow: App.messages.app.AP0013,
                    required:App.messages.base.required,
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength,
                    checkExistsHimmei: App.messages.app.AP0010
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
                    name: "工程名",
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
                            mark = page.getObjMark(cd_mark, null),
                            ss_kbn_qty_haigo = mark ? mark.ss_kbn_qty_haigo : null;

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
                            mark = page.getObjMark(cd_mark, null),
                            ss_kbn_nisugata = mark ? mark.ss_kbn_nisugata : null;

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
                            mark = page.getObjMark(cd_mark, null),
                            ss_kbn_gosa = mark ? mark.ss_kbn_gosa : null;

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
                            mark = page.getObjMark(cd_mark, null),
                            ss_kbn_budomari = mark ? mark.ss_kbn_budomari : null;

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
                    maxbytelength:300
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
            element.on("click", ".hyperlink", page.detail.moveToHaigoTorokuKaihatsuBumon);

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

                    var id = page.ma_haigo_header.dataId,
                        entity = page.ma_haigo_header.data.entry(id);
                    entity["qty_haigo_kei"] = valOld;
                    page.ma_haigo_header.data.update(entity);
                })
            })

            page.detail.element.find("input[type='radio']").change(function (e) {
                var target = $(e.target),
                    property = target.attr("data-prop"),
                    id = page.ma_haigo_header.dataId,
                    entity = page.ma_haigo_header.data.entry(id);
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
                page.ma_haigo_header.data.update(entity);
            })

            page.detail.element.findP("biko").change(function (e) {
                var target = $(e.target);

                $("#save").prop("disabled", false);

                page.detail.validator.validate({
                    targets: target
                }).then(function () {
                    var id = page.ma_haigo_header.dataId,
                        entity = page.ma_haigo_header.data.entry(id);
                    entity["biko"] = target.val() == "" ? null: target.val();
                    page.ma_haigo_header.data.update(entity);
                })
            })

            //TODO: 画面明細の初期化処理をここに記述します。

            //TODO: 画面明細で利用するコントロールのイベントの紐づけ処理をここに記述します。         
        };

        /**
         * Check Exists Hinmei 
         */
        page.detail.checkExistsHinmei = function () {

            var deferred = $.Deferred();
            
                
            if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.seiho_copy && !App.isUndefOrNull(page.ma_haigo_meisai.data)) {
                
                var parameter = {
                    data: page.ma_haigo_meisai.data.getChangeSet().created,
                    cd_kaisha_daihyo: page.values.cd_kaisha_daihyo,
                    cd_kojyo_daihyo: page.values.cd_kojyo_daihyo,
                    cd_kaisha: parseInt(page.header.element.findP("no_seiho_kaisha").val(), 10)
                }

                return $.ajax(App.ajax.webapi.post(page.urls.haigoTorokuKaihatsuBumon + "/post_checkExistsHinmei", parameter)).then(function (result) {
                    $.each(result, function (index, item) {
                        var kotei = item.no_kotei,
                            tonyu = item.no_tonyu,
                            kotei_tonyu = kotei + "_" + tonyu,
                            row = page.detail.element.find(App.str.format(".new[kotei-tonyu='{0}-{1}']", kotei, tonyu));

                        if (row.findP("kbn_shikakari").text() != "") {
                            if (!App.isUndefOrNull(item.qty_haigo) && item.qty_haigo < 0) {
                                row.findP("cd_hin").addClass("errorNoKikaku");
                                row.findP("no_kikaku").text("");
                            }
                            else {
                                row.addClass("row-error-past")
                                row.addClass("row-error-past-css");
                            }
                        }
                        
                    });
                    deferred.resolve();
                }).fail(function (error) {
                    deferred.reject(error);
                })
            } else {
                deferred.resolve();
            }

        }

        /**
         * 画面明細の各行にデータを設定する際のオプションを定義します。
         */
        page.detail.options.bindOption = {
            appliers: {

                kbn_bunkatsu: function (value, element) {
                    if (App.isUndefOrNull(value) || value == 0 || value.toString() == "") {
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
                        element.val(App.num.format(Number(value), "000000"));
                    }
                    return true;
                },

                qty_haigo: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.val("");
                    }
                    else {
                        element.val(App.num.format(Number(value), "#,#.000"));
                    }
                    return true;
                },

                qty_nisugata: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.val("");
                    }
                    else {
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
                    if (App.isUndefOrNull(value) || value.toString() == "") {
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
        page.detail.bind = function (data, isNotclearTable, isFrom700) {
            var i, l, item, dataSet, dataCount;

            if (App.isUndefOrNull(isNotclearTable) || !isNotclearTable) {
                page.detail.dataTable.dataTable("clear");
            }

            page.detail.dataTable.dataTable("addRows", data, function (row, item) {
                if (!App.isUndefOrNull(isNotclearTable) && isNotclearTable) {
                    row.addClass("rowCopy");
                }

                if (item.no_tonyu == App.settings.app.no_tonyu.isKotei) {
                    row.find(".tonyu").remove();

                    row.form(page.detail.options.bindOption).bind(item);
                    row.addClass("" + item.no_kotei)
                    row.addClass(item.no_kotei + "-" + item.no_tonyu)
                    row.attr("kotei", item.no_kotei);
                    row.attr("tonyu", item.no_tonyu)
                    row.attr("kotei-tonyu", item.no_kotei + "-" + item.no_tonyu);

                }
                else {
                    row.find(".kotei").remove();

                    row.form(page.detail.options.bindOption).bind(item);
                    page.detail.setFormatHinmei(row, item);

                    page.detail.setNokikaku(row, item.kbn_hin, item.kbn_shikakari);
                    page.detail.setControlByMark(row, page.values.ma_mark[item.cd_mark], true);
                    page.detail.setTaniShiyo(row);

                    row.addClass("" + item.no_kotei)
                    row.addClass(item.no_kotei + "-" + item.no_tonyu)

                    row.attr("kotei", item.no_kotei);
                    row.attr("tonyu", item.no_tonyu)
                    row.attr("kotei-tonyu", item.no_kotei + "-" + item.no_tonyu);

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
                    entity = page.ma_haigo_meisai.data.entry(id),
                    cd_hin = entity["cd_hin"],
                    kbn_hin = row.element.findP("kbn_hin").text(),
                    no_tonyu = entity["no_tonyu"],
                    kbn_shikakari = row.element.findP("kbn_shikakari").text(),
                    no_kikaku = row.element.findP("no_kikaku").text();

                if (no_tonyu != App.settings.app.no_tonyu.isKotei && !(cd_hin >= page.values.shijiMin && cd_hin <= page.values.shijiMax)) {
                    //if (kbn_shikakari == "" || App.isUndefOrNull(kbn_shikakari)) {
                    if (!entity.hasHinmei) {
                        row.element.addClass("row-error-past-css")
                        // Validate check mishiyo genryo when page load in mode copy seiho or new haigo
                        if (page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.seiho_copy
                            || (isFrom700 && page.values.M_HaigoTorokuKaihatsu == App.settings.app.m_haigo_toroku_kaihatsu.haigo_shinki)
                            ) {
                            row.element.addClass("row-error-past");
                        }
                    }
                    else {
                        if (kbn_hin == App.settings.app.kbnHin.genryo && (cd_hin < page.values.genryoRangeMin || cd_hin > page.values.genryoRangeMax) && (App.isUndefOrNull(no_kikaku) || no_kikaku == "")) {
                            row.element.findP("cd_hin").addClass("errorNoKikaku");
                        }
                    }
                }
            });
            
            
        };

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
            else if (kbn_hin == App.settings.app.kbnHin.shikakari && data.kbn_shikakari == App.settings.app.kbn_shikakari.kaihatsu) {
                formatLength = Number(page.values.su_code_standard);
            }
            else if (kbn_hin == App.settings.app.kbnHin.shikakari && data.kbn_shikakari != App.settings.app.kbn_shikakari.kaihatsu) {
                formatLength = Number(page.values.su_code_standard) + 1;
            }
            else if (kbn_hin == App.settings.app.kbnHin.maeshoriGenryo) {
                formatLength = Number(page.values.su_code_standard) + 1;
            }
            else {
                formatLength = (data.cd_hin.toString()).length;
            }

            hinCodeFormat = App.num.format(Number(data.cd_hin), pad.substr(0, formatLength));
            row.findP("cd_hin").val(hinCodeFormat);
        }

        /**
         * Set no_kikaku
         */
        page.detail.setNokikaku = function (row, kbn_hin, kbn_shikakari) {

            if (!App.isUndefOrNull(kbn_hin) && kbn_hin < 0) {
                row.findP("hyperlink").hide();
                row.findP("text_shikakari").hide();
                row.findP("no_kikaku").show();
            }

            if (kbn_hin == App.settings.app.kbnHin.shikakari) {
                if (App.isUndefOrNull(kbn_shikakari)) {
                    row.findP("hyperlink").hide();
                    row.findP("text_shikakari").hide();
                    row.findP("no_kikaku").show();
                }
                else if (kbn_shikakari == 0) {
                    row.findP("hyperlink").show();
                    row.findP("text_shikakari").hide();
                    row.findP("no_kikaku").hide();
                }
                else {
                    row.findP("hyperlink").hide();
                    row.findP("text_shikakari").show();
                    row.findP("no_kikaku").hide();
                }
            }
            else {
                row.findP("hyperlink").hide();
                row.findP("text_shikakari").hide();
                row.findP("no_kikaku").show();
            }
        }

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
         * add ValidateHinmei
         */
        page.detail.checkHinMark = function () {

            var flg_error = false;

            page.detail.dataTable.dataTable("each", function (row) {
                var tbody = row.element;

                if (tbody.hasClass("item-tmpl")) {
                    return;
                }

                var kbn_hin = tbody.findP("kbn_hin").text(),
                    tonyu = tbody.attr("tonyu"),
                    cd_mark = tbody.findP("cd_mark").text(),
                    cd_hin = Number(tbody.findP("cd_hin").val()),
                    mark = page.getObjMark(cd_mark, null);

                if (!mark) {
                    return;
                }
                if (tonyu == App.settings.app.no_tonyu.isKotei) {
                    return;
                }

                else if (cd_hin >= page.values.shijiMin && cd_hin <= page.values.shijiMax && !page.values.ma_mark[cd_mark].ss_flg_sagyo) {
                    flg_error = true;
                    App.ui.page.notifyAlert.message(App.messages.app.AP0019,tbody.find(".cd_hin")).show();
                }

                else if (kbn_hin == App.settings.app.kbnHin.genryo && !page.values.ma_mark[cd_mark].ss_flg_genryo) {
                    flg_error = true;
                    App.ui.page.notifyAlert.message(App.messages.app.AP0019, tbody.find(".cd_hin")).show();
                }

                else if (kbn_hin == App.settings.app.kbnHin.shikakari && !page.values.ma_mark[cd_mark].ss_flg_shikakari) {
                    flg_error = true;
                    App.ui.page.notifyAlert.message(App.messages.app.AP0019, tbody.find(".cd_hin")).show();
                }

                else if (kbn_hin == App.settings.app.kbnHin.maeshoriGenryo && !page.values.ma_mark[cd_mark].ss_flg_maeshori) {
                    flg_error = true;
                    App.ui.page.notifyAlert.message(App.messages.app.AP0019, tbody.find(".cd_hin")).show();
                }
            });

            return flg_error;
        }

        /**
         * 画面明細の一覧の入力項目の変更イベントの処理を行います。
         */
        page.detail.change = function (e, row) {
            var target = $(e.target),
                id = row.element.attr("data-key"),
                property = target.attr("data-prop"),
                entity = page.ma_haigo_meisai.data.entry(id),
                data = row.element.form().data(),
                options = {
                    filter: page.detail.validationFilter
                };

            if (property == "cd_hin") {
                target.removeClass("errorNoKikaku");
                row.element.removeClass("row-error-past");
                row.element.removeClass("row-error-past-css");
            }

            $("#save").prop("disabled", false);
            

            page.values.isChangeRunning[property] = true;

            page.detail.executeValidation(target, row.element)
            .then(function () {
                if (property == "kbn_bunkatsu") {
                    if (data[property] == 0 || App.isUndefOrNull(data[property])) {
                        target.val("");
                        entity[property] = 0;
                    }
                    else {
                        entity[property] = data[property];
                    }
                    page.ma_haigo_meisai.data.update(entity);
                    page.detail.validator.validate({
                        targets: row.element.find(".validate-error"),
                        state: {
                            tbody: row.element,
                            isGridValidation: true,
                            check: true
                        }
                    });
                }


                else if (property == "nm_kotei") {
                    entity["nm_hin"] = data[property];
                    page.ma_haigo_meisai.data.update(entity);
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
                    page.ma_haigo_meisai.data.update(entity);
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
                    var valOld = parseInt(data[property], 10),
                        valNew = App.num.format(Number(valOld), "000000");

                    if (valOld >= page.values.shijiMin && valOld <= page.values.shijiMax) {

                        if (entity["kbn_hin"] != App.settings.app.kbnHin.sagyo) {
                            row.element.findP("cd_mark").text(App.settings.app.cd_mark.mark_18);
                            var mark_18 = page.getObjMark(App.settings.app.cd_mark.mark_18, null);
                            row.element.findP("mark").text(mark_18 ? mark_18.mark : "");
                            entity["cd_mark"] = App.settings.app.cd_mark.mark_18;
                            page.detail.setNokikaku(row.element,-1);
                            page.detail.setControlByMark(row.element, page.values.ma_mark[App.settings.app.cd_mark.mark_18]);
                            page.detail.calculateQtyHaigoKei();
                            page.detail.setTaniShiyo(row.element);
                            
                        }
                        entity["cd_hin"] = valOld;
                        entity["kbn_hin"] = App.settings.app.kbnHin.sagyo;
                        entity["kbn_shikakari"] = null;
                        row.element.findP("kbn_hin").text(App.settings.app.kbnHin.sagyo);
                        row.element.findP("kbn_shikakari").text("");
                        row.element.findP("no_kikaku").text("");
                        row.element.findP("hijyu").text("");

                        page.ma_haigo_meisai.data.update(entity);

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
                        var no_seiho_kaisha = page.header.element.findP("no_seiho_kaisha").val();
                        var parameter = {
                            cd_kaisha_daihyo: page.values.cd_kaisha_daihyo,
                            cd_kojyo_daihyo: page.values.cd_kojyo_daihyo,
                            cd_kaisha: no_seiho_kaisha == "" ? -1 : parseInt(no_seiho_kaisha, 10),
                            cd_hin: valOld,
                            kbn_hin: null,
                            kbn_shikakari:null
                        }
                        return $.ajax(App.ajax.webapi.get(page.urls.haigoTorokuKaihatsuBumon + "/getInfoHinmei", parameter)
                        ).then(function (result) {
                            if (result.length > 0) {
                                var hinmei = result[0];
                                for (var i = 0 ; i < result.length; i++) {
                                    if (result[i].kbn_hin == App.settings.app.kbnHin.shikakari) {
                                        hinmei = result[i];
                                        break;
                                    }
                                }

                                if (hinmei.kbn_hin == App.settings.app.kbnHin.genryo && (hinmei.cd_hin < page.values.genryoRangeMin || hinmei.cd_hin > page.values.genryoRangeMax) && (App.isUndefOrNull(hinmei.no_kikaku) || hinmei.no_kikaku == "")) {
                                    row.element.findP("cd_hin").addClass("errorNoKikaku");
                                }

                                if (App.isUndefOrNull(entity) || entity["kbn_hin"] == App.settings.app.kbnHin.sagyo) {
                                    row.element.findP("cd_mark").text(App.settings.app.cd_mark.mark_0);
                                    var mark_0 = page.getObjMark(mark_0, null);
                                    row.element.findP("mark").text(mark_0 ? mark_0.mark : "");
                                    entity["cd_mark"] = App.settings.app.cd_mark.mark_0;
                                }
                                    
                                row.element.findP("nm_hin").val(hinmei.nm_hin);
                                row.element.findP("hijyu").text(App.isUndefOrNull(hinmei.hijyu) ? "" : App.num.format(Number(hinmei.hijyu), "#,#.000"));
                                row.element.findP("no_kikaku").text(App.isUndefOrNull(hinmei.no_kikaku) ? "" : hinmei.no_kikaku);
                                row.element.findP("budomari").val(App.isUndefOrNull(hinmei.budomari) ? "" : App.num.format(Number(hinmei.budomari), "#,#.00"));
                                row.element.findP("qty_nisugata").val(App.isUndefOrNull(hinmei.nisugata_gty) ? "" : App.num.format(Number(hinmei.nisugata_gty), "#,#.000"));
                                row.element.findP("kbn_shikakari").text(hinmei.kbn_shikakari);
                                row.element.findP("kbn_hin").text(hinmei.kbn_hin);
                                row.element.findP("cd_tani_hin").text(App.isUndefOrNull(hinmei.cd_tani_hin) ? "" : hinmei.cd_tani_hin);

                                entity["cd_hin"] = valOld;
                                entity["nm_hin"] = hinmei.nm_hin;
                                entity["kbn_hin"] = hinmei.kbn_hin_toroku;
                                entity["kbn_shikakari"] = hinmei.kbn_shikakari;
                                entity["qty_nisugata"] = hinmei.nisugata_gty;
                                entity["budomari"] = hinmei.budomari;
                                page.ma_haigo_meisai.data.update(entity);

                                page.detail.setFormatHinmei(row.element, hinmei);
                                page.detail.setNokikaku(row.element, hinmei.kbn_hin, hinmei.kbn_shikakari);
                                page.detail.setControlByMark(row.element, page.values.ma_mark[row.element.findP("cd_mark").text()]);
                                page.detail.calculateQtyHaigoKei();
                                page.detail.setTaniShiyo(row.element);

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
                                row.element.findP("kbn_shikakari").text("");
                                row.element.findP("kbn_hin").text("");
                                row.element.findP("cd_tani_hin").text("");

                                page.detail.setNokikaku(row.element,-1);

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
                            row.element.findP("kbn_shikakari").text("");
                            row.element.findP("kbn_hin").text("");
                            row.element.findP("cd_tani_hin").text("");

                            page.detail.setNokikaku(row.element,-1);

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

                else if (property == "flg_shitei") {
                    if (target.prop("checked") == true) {
                        entity[property] = true;
                    }
                    else {
                        entity[property] = false;
                    }
                    page.ma_haigo_meisai.data.update(entity);
                    
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
                    page.ma_haigo_meisai.data.update(entity);
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
                    page.ma_haigo_meisai.data.update(entity);
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

                    if (data[property] == 0 || App.isUndefOrNull(data[property])) {
                        target.val("");
                        entity[property] = null;
                    }
                    else {
                        entity[property] = data[property];
                        target.val(App.num.format(Number(data[property]), "#,#.00"))
                    }
                    
                    page.ma_haigo_meisai.data.update(entity);
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
                    page.ma_haigo_meisai.data.update(entity);
                    page.detail.validator.validate({
                        targets: row.element.find(".validate-error"),
                        state: {
                            tbody: row.element,
                            isGridValidation: true,
                            check: true
                        }
                    });
                }                
            }).fail(function(){
                if (property == "cd_hin") {
                    row.element.findP("nm_hin").val("");
                    row.element.findP("hijyu").text("");
                    row.element.findP("no_kikaku").text("");
                    row.element.findP("budomari").val("");
                    row.element.findP("qty_nisugata").val("");
                    row.element.findP("kbn_shikakari").text("");
                    row.element.findP("kbn_hin").text("");
                    row.element.findP("cd_tani_hin").text("");

                    page.detail.setNokikaku(row.element,-1);

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
         * Set tani shiyo
         */
        page.detail.setTaniShiyo = function (row,notShowNameTani) {
            var cd_hin = row.findP("cd_hin").val(),
                cd_mark = row.findP("cd_mark").text(),
                cd_tani_hin = row.findP("cd_tani_hin").text(),
                mark = page.getObjMark(cd_mark, null),
                cd_taniShiyo_mark = mark ? mark.cd_tani_shiyo : null;
           
            if (cd_hin >= page.values.shijiMin && cd_hin <= page.values.shijiMax) {
                row.findP("nm_tani").text(page.values.ma_tani[cd_taniShiyo_mark] ? page.values.ma_tani[cd_taniShiyo_mark].nm_tani : "");
                row.findP("cd_tani").text(page.values.ma_tani[cd_taniShiyo_mark] ? (page.values.ma_tani[cd_taniShiyo_mark].nm_tani == "" ? "" :  cd_taniShiyo_mark) : "");
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
         * get new bignumber
         */
        page.getBigNumber = function (value) {
            var val = Number(value);
            if (isNaN(val)) {
                val = 0;
            }
            return new BigNumber(val);
        }

        /**
         * Calculate qty_haigo_kei
         */
        page.detail.calculateQtyHaigoKei = function (isNotUpdateHeader) {
            var qty_haigo_kei = new BigNumber(0);
            if (page.header.element.findP("rdoVWKbnKg").prop("checked")) {
                var rows = page.detail.element.find(".new");
                for (var i = 0 ; i < rows.length; i++) {
                    var row = $(rows[i]),
                        id = row.attr("data-key"),
                        entity = page.ma_haigo_meisai.data.entry(id),
                        cd_tani = row.findP("cd_tani").text();
                    if (entity.no_tonyu > App.settings.app.no_tonyu.isKotei) {
                        if (cd_tani == App.settings.app.taniShiyo.kg || cd_tani == App.settings.app.taniShiyo.g) {

                            //var qty_haigo = Number(row.findP("qty_haigo").val().replace(/,/g, ""));
                            var qty_haigo = page.getBigNumber(row.findP("qty_haigo").val().replace(/,/g, ""));
                            if (cd_tani == App.settings.app.taniShiyo.g) {
                                qty_haigo = qty_haigo.div(1000)//.toNumber();
                            }

                            qty_haigo_kei = qty_haigo_kei.plus(qty_haigo)//.toNumber();
                        }
                        else {
                            //var qty_haigo = Number(row.findP("qty_haigo").val().replace(/,/g, "")),
                            var qty_haigo = row.findP("qty_haigo").val().replace(/,/g, "");
                                hijyu = Number(row.findP("hijyu").text());
                            if (hijyu == 0) {
                                hijyu = 1;
                            }
                            qty_haigo = page.getBigNumber(qty_haigo).times(hijyu)//.toNumber();
                            if (cd_tani == App.settings.app.taniShiyo.ml) {
                                qty_haigo = qty_haigo.div(1000)//.toNumber();
                            }
                            qty_haigo_kei = qty_haigo_kei.plus(qty_haigo)//.toNumber();
                        }
                    }
                }
                if (!qty_haigo_kei) {
                    qty_haigo_kei = new BigNumber(0);
                }
                $(page.detail.element.findP("qty_haigo_kei")[1]).text(App.num.format(qty_haigo_kei.toNumber(), "#,0.000000"))
            }
            else {
                var rows = page.detail.element.find(".new");
                for (var i = 0 ; i < rows.length; i++) {
                    var row = $(rows[i]),
                        id = row.attr("data-key"),
                        entity = page.ma_haigo_meisai.data.entry(id),
                        cd_tani = row.findP("cd_tani").text();

                    if (entity.no_tonyu > App.settings.app.no_tonyu.isKotei) {
                        if (cd_tani == App.settings.app.taniShiyo.l || cd_tani == App.settings.app.taniShiyo.ml) {

                            //var qty_haigo = Number(row.findP("qty_haigo").val().replace(/,/g, ""));
                            var qty_haigo = page.getBigNumber(row.findP("qty_haigo").val().replace(/,/g, ""));

                            if (cd_tani == App.settings.app.taniShiyo.ml) {
                                qty_haigo = qty_haigo.div(1000)//.toNumber();
                            }

                            qty_haigo_kei = qty_haigo_kei.plus(qty_haigo)//.toNumber();
                        }
                        else {
                            //var qty_haigo = Number(row.findP("qty_haigo").val().replace(/,/g, "")),
                            var qty_haigo = row.findP("qty_haigo").val().replace(/,/g, "");
                                hijyu = Number(row.findP("hijyu").text());
                            if (hijyu == 0) {
                                hijyu = 1;
                            }

                            qty_haigo = page.getBigNumber(qty_haigo).div(hijyu)//.toNumber();

                            if (cd_tani == App.settings.app.taniShiyo.g) {
                                qty_haigo = qty_haigo.div(1000)//.toNumber();
                            }
                            qty_haigo_kei = qty_haigo_kei.plus(qty_haigo)//.toNumber();
                        }
                    }
                }
                if (!qty_haigo_kei) {
                    qty_haigo_kei = new BigNumber(0);
                }
                $(page.detail.element.findP("qty_haigo_kei")[1]).text(App.num.format(Number(qty_haigo_kei.toNumber()), "#,0.000000"))
            }

            if ($(page.detail.element.findP("rdo_shiage")[1]).prop("checked")) {
                $(page.detail.element.findP("qty_haigo_kei_user")[1]).val(App.num.format(Number(qty_haigo_kei.toNumber()), "#,0.000000"));
            }

            if (!isNotUpdateHeader) {
                var id = page.ma_haigo_header.dataId,
                    entity = page.ma_haigo_header.data.entry(id);
                entity["qty_haigo_kei"] = qty_haigo_kei.toNumber();
                page.ma_haigo_header.data.update(entity);
            }

        }

        /**
         * 画面明細の一覧に新規データを追加します。（工程追加押下時）
         */
        page.detail.addNewKotei = function (e) {
            ////TODO:新規データおよび初期値を設定する処理を記述します。
            var row = page.detail.element.find(".datatable .select-tab.selected").closest("tbody"),
                countRow = page.detail.element.find(".new").length,
                idLast,maxKotei;

            if (countRow > 0) {
                idLast = $(page.detail.element.find(".new")[countRow - 1]).attr("data-key");
                maxKotei = parseInt(page.ma_haigo_meisai.data.entry(idLast)["no_kotei"], 10) + 1;
            }
            else {
                maxKotei = 1;
            }

            $("#save").prop("disabled", false);
            
            var newKoteiData = {
                cd_haigo: page.header.element.findP("cd_haigo").text() == ""? -1 : page.header.element.findP("cd_haigo").text(),
                no_kotei: maxKotei,
                no_tonyu: 1,
                cd_hin: App.settings.app.commentCode,
                flg_shitei: 0,
                kbn_hin: App.settings.app.kbnHin.sagyo,
                kbn_shikakari: null,
                nm_hin: null,
                cd_mark: App.settings.app.cd_mark.mark_18,
                qty_haigo: null,
                qty_nisugata: null,
                gosa: null,
                budomari: null,
                kbn_bunkatsu:0
            }

            page.ma_haigo_meisai.data.add(newKoteiData);

            page.detail.dataTable.dataTable("addRow", function (tbody) {
                tbody.find(".tonyu").remove();

                var item = {
                    __id: newKoteiData.__id,
                    kotei_tonyu: newKoteiData.no_kotei + "-" + newKoteiData.no_tonyu,
                    nm_kotei: newKoteiData.nm_hin 
                }

                tbody.form(page.detail.options.bindOption).bind(item);

                tbody.addClass("" + newKoteiData.no_kotei)
                tbody.addClass(newKoteiData.no_kotei + "-" + newKoteiData.no_tonyu)

                tbody.attr("kotei", newKoteiData.no_kotei);
                tbody.attr("tonyu", newKoteiData.no_tonyu)
                tbody.attr("kotei-tonyu", newKoteiData.no_kotei + "-" + newKoteiData.no_tonyu);

                tbody.addClass("kbn_kotei");
                tbody.attr("no_kotei", newKoteiData.no_kotei);
                tbody.attr("no_tonyu", newKoteiData.no_tonyu);

                return tbody;
            }, true);


            var newTonyuData = {
                cd_haigo: page.header.element.findP("cd_haigo").text() == "" ? -1 : page.header.element.findP("cd_haigo").text(),
                no_kotei: maxKotei,
                no_tonyu: 2,
                cd_hin: null,
                flg_shitei: 0,
                kbn_hin: null,
                kbn_shikakari: null,
                nm_hin: null,
                cd_mark: App.settings.app.cd_mark.mark_0,
                qty_haigo: null,
                qty_nisugata: null,
                gosa: null,
                budomari: null,
                kbn_bunkatsu: 0
            }

            page.ma_haigo_meisai.data.add(newTonyuData);

            page.detail.dataTable.dataTable("addRow", function (tbody) {

                tbody.find(".kotei").remove();

                var item = {
                    __id: newTonyuData.__id,
                    kotei_tonyu: newTonyuData.no_kotei + "-" + newTonyuData.no_tonyu,
                    cd_mark: App.settings.app.cd_mark.mark_0
                }

                tbody.form(page.detail.options.bindOption).bind(item);

                if (App.isUndefOrNull(e) || e != "loadBegin") {
                    page.detail.setControlByMark(tbody, page.values.ma_mark[App.settings.app.cd_mark.mark_0], true);
                    if (App.isUndefOrNull(e)) {
                        page.detail.setTaniShiyo(tbody);
                    }
                    else {
                        page.detail.setTaniShiyo(tbody,true);
                    }
                }

                tbody.addClass("" + newTonyuData.no_kotei)
                tbody.addClass(newTonyuData.no_kotei + "-" + newTonyuData.no_tonyu)

                tbody.attr("kotei", newTonyuData.no_kotei);
                tbody.attr("tonyu", newTonyuData.no_tonyu)
                tbody.attr("kotei-tonyu", newTonyuData.no_kotei + "-" + newTonyuData.no_tonyu);

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
                    entity = page.ma_haigo_meisai.data.entry(id);
                entity["no_kotei"] = kotei - 1;
                page.ma_haigo_meisai.data.update(entity);
            }

            for (var i = 0; i < rows_all_kotei_prev.length; i++) {
                var row = $(rows_all_kotei_prev[i]),
                    tonyu = row.attr("tonyu");

                //edit kotei class
                row.removeClass("" + kotei_prev)
                row.addClass("" + (kotei_prev + 1))

                //edit kotei-tonyu class
                row.removeClass(kotei_prev  + "-" + tonyu);
                row.addClass((kotei_prev + 1) + "-" + tonyu);

                //edit kotei attribute
                row.attr("kotei", (kotei_prev + 1));

                //edit kotei-tonyu attribute
                row.attr("kotei-tonyu", (kotei_prev + 1) + "-" + tonyu);

                row.findP("kotei_tonyu").text((kotei_prev + 1) + "-" + tonyu);

                var id = row.attr("data-key"),
                    entity = page.ma_haigo_meisai.data.entry(id);
                entity["no_kotei"] = kotei_prev + 1;
                page.ma_haigo_meisai.data.update(entity);
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
                    entity = page.ma_haigo_meisai.data.entry(id);
                entity["no_kotei"] = kotei - 1;
                page.ma_haigo_meisai.data.update(entity);
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
                    entity = page.ma_haigo_meisai.data.entry(id);
                entity["no_kotei"] = kotei_prev + 1;
                page.ma_haigo_meisai.data.update(entity)
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

            if (!targetRow.length) {
                App.ui.page.notifyAlert.message(App.messages.app.AP0008, "AP0008").show();
                return;
            }

            var kotei = parseInt(targetRow.attr("kotei")),
                tonyu = parseInt(targetRow.attr("tonyu")),
                kotei_tonyu = kotei + "-" + tonyu;
            
            if (tonyu == 1) {
                App.ui.page.notifyAlert.message(App.messages.app.AP0008, "AP0008").show();
                return;
            }

            if (tonyu == 2) {
                var kotei_prev = kotei - 1;
                if (kotei_prev == 0) {
                    return;
                }

                var rowBefore = targetRow.prev().prev(),
                    koteiBefore = parseInt(rowBefore.attr("kotei")),
                    tonyuBefore = parseInt(rowBefore.attr("tonyu"));

                if (page.detail.element.find(".new." + koteiBefore).length == page.values.maxNumberRowDetail) {
                    App.ui.page.notifyAlert.message(App.messages.app.AP0015, "AP0015").show();
                    return;
                }
                $("#save").prop("disabled", false);

                if (page.detail.element.find("." + kotei).length == 2) {

                    var rowKotei = page.detail.element.find("." + kotei + "-1"),
                        newTonyuData = {
                            cd_haigo: page.header.element.findP("cd_haigo").text() == "" ? -1 : page.header.element.findP("cd_haigo").text(),
                            no_kotei: kotei,
                            no_tonyu: 2,
                            cd_hin: null,
                            flg_shitei: 0,
                            kbn_hin: null,
                            kbn_shikakari: null,
                            nm_hin: null,
                            cd_mark: App.settings.app.cd_mark.mark_0,
                            qty_haigo: null,
                            qty_nisugata: null,
                            gosa: null,
                            budomari: null,
                            kbn_bunkatsu: 0
                        }
                    page.ma_haigo_meisai.data.add(newTonyuData);
                    page.detail.dataTable.dataTable("insertRow", rowKotei, true, function (tbody) {
                        tbody.find(".kotei").remove();

                        var item = {
                            __id: newTonyuData.__id,
                            kotei_tonyu: newTonyuData.no_kotei + "-" + newTonyuData.no_tonyu,
                            cd_mark: App.settings.app.cd_mark.mark_0
                        }

                        tbody.form(page.detail.options.bindOption).bind(item);

                        page.detail.setControlByMark(tbody, page.values.ma_mark[App.settings.app.cd_mark.mark_0], true);
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
                            entity = page.ma_haigo_meisai.data.entry(id);
                        entity["no_tonyu"] = tonyu_rowNext - 1;
                        page.ma_haigo_meisai.data.update(entity);

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
                    entity = page.ma_haigo_meisai.data.entry(id);
                entity["no_kotei"] = koteiBefore;
                entity["no_tonyu"] = tonyuBefore + 1;
                page.ma_haigo_meisai.data.update(entity);

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
                    entity = page.ma_haigo_meisai.data.entry(id);
                entity["no_tonyu"] = tonyuBefore;
                page.ma_haigo_meisai.data.update(entity);

                rowBefore.removeClass(koteiBefore + "-" + tonyuBefore);
                rowBefore.addClass("" + kotei_tonyu);
                rowBefore.attr("tonyu", tonyu);
                rowBefore.attr("kotei-tonyu", kotei_tonyu);
                rowBefore.findP("kotei_tonyu").text(kotei_tonyu);
                var idBefore = rowBefore.attr("data-key"),
                    entityBefore = page.ma_haigo_meisai.data.entry(idBefore);
                entityBefore["no_tonyu"] = tonyu;
                page.ma_haigo_meisai.data.update(entityBefore);

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
            if (!targetRow.length) {
                App.ui.page.notifyAlert.message(App.messages.app.AP0008, "AP0008").show();
                return;
            }

            var kotei = parseInt(targetRow.attr("kotei")),
                tonyu = parseInt(targetRow.attr("tonyu")),
                kotei_tonyu = kotei + "-" + tonyu;

            if (tonyu == 1) {
                App.ui.page.notifyAlert.message(App.messages.app.AP0008, "AP0008").show();
                return;
            }

            var rowNext = targetRow.next();
            if (rowNext.length) {
                var koteiNext = parseInt(rowNext.attr("kotei"));
                if (koteiNext != kotei) {

                    if (page.detail.element.find(".new." + koteiNext).length == page.values.maxNumberRowDetail) {
                        App.ui.page.notifyAlert.message(App.messages.app.AP0015, "AP0015").show();
                        return;
                    }
                    $("#save").prop("disabled", false);
                    var row = rowNext.next();
                    for (; ;) {
                        var tonyuNext = parseInt(row.attr("tonyu"));
                        row.removeClass(koteiNext + "-" + tonyuNext);
                        row.addClass(koteiNext + "-" + (tonyuNext + 1));
                        row.attr("kotei-tonyu", koteiNext + "-" + (tonyuNext + 1));
                        row.attr("tonyu", (tonyuNext + 1))
                        row.findP("kotei_tonyu").text(koteiNext + "-" + (tonyuNext + 1));

                        var id = row.attr("data-key"),
                        entity = page.ma_haigo_meisai.data.entry(id);
                        entity["no_tonyu"] = tonyuNext + 1;
                        page.ma_haigo_meisai.data.update(entity);

                        row = row.next();
                        if (row.length == 0 || parseInt(row.attr("kotei")) != koteiNext) {
                            break;
                        }
                    }
                    targetRow.removeClass("" + kotei);
                    targetRow.removeClass(kotei + "-" + tonyu);
                    targetRow.addClass("" + koteiNext);
                    targetRow.addClass(koteiNext + "-" + "2");
                    targetRow.attr("kotei", koteiNext);
                    targetRow.attr("tonyu", "2");
                    targetRow.attr("kotei-tonyu", koteiNext + "-" + "2");
                    targetRow.findP("kotei_tonyu").text(koteiNext + "-" + "2");

                    var id = targetRow.attr("data-key"),
                        entity = page.ma_haigo_meisai.data.entry(id);
                    entity["no_kotei"] = koteiNext;
                    entity["no_tonyu"] = 2;
                    page.ma_haigo_meisai.data.update(entity);

                    rowNext.after(targetRow);

                    if (page.detail.element.find("." + kotei).length == 1) {
                        var rowKotei = page.detail.element.find("." + kotei + "-1"),
                            newTonyuData = {
                                cd_haigo: page.header.element.findP("cd_haigo").text() == "" ? -1 : page.header.element.findP("cd_haigo").text(),
                                no_kotei: kotei,
                                no_tonyu: 2,
                                cd_hin: null,
                                flg_shitei: 0,
                                kbn_hin: null,
                                kbn_shikakari: null,
                                nm_hin: null,
                                cd_mark: App.settings.app.cd_mark.mark_0,
                                qty_haigo: null,
                                qty_nisugata: null,
                                gosa: null,
                                budomari: null,
                                kbn_bunkatsu: 0
                            }
                        page.ma_haigo_meisai.data.add(newTonyuData);
                        page.detail.dataTable.dataTable("insertRow", rowKotei, true, function (tbody) {
                            tbody.find(".kotei").remove();

                            var item = {
                                __id: newTonyuData.__id,
                                kotei_tonyu: newTonyuData.no_kotei + "-" + newTonyuData.no_tonyu,
                                cd_mark: App.settings.app.cd_mark.mark_0
                            }

                            tbody.form(page.detail.options.bindOption).bind(item);

                            page.detail.setControlByMark(tbody, page.values.ma_mark[App.settings.app.cd_mark.mark_0], true);
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
                        entityRowNext = page.ma_haigo_meisai.data.entry(idRowNext);
                    entityRowNext["no_tonyu"] = tonyu;

                    targetRow.removeClass(kotei + "-" + tonyu);
                    targetRow.addClass(kotei + "-" + tonyuNext);
                    targetRow.attr("tonyu", tonyuNext);
                    targetRow.attr("kotei-tonyu", kotei + "-" + tonyuNext);
                    targetRow.findP("kotei_tonyu").text(kotei + "-" + tonyuNext);

                    var id = targetRow.attr("data-key"),
                        entity = page.ma_haigo_meisai.data.entry(id);
                    entity["no_tonyu"] = tonyuNext;
                    page.ma_haigo_meisai.data.update(entity);
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
                cd_haigo: page.header.element.findP("cd_haigo").text() == "" ? -1 :  page.header.element.findP("cd_haigo").text(),
                no_kotei: kotei,
                no_tonyu: tonyu,
                cd_hin: null,
                flg_shitei: 0,
                kbn_hin: null,
                kbn_shikakari: null,
                nm_hin: null,
                cd_mark: App.settings.app.cd_mark.mark_0,
                qty_haigo: null,
                qty_nisugata: null,
                gosa: null,
                budomari: null,
                kbn_bunkatsu: 0
            }

            page.ma_haigo_meisai.data.add(newTonyuData);

            page.detail.dataTable.dataTable("insertRow", selected, true, function (tbody) {

                tbody.find(".kotei").remove();
                var item = {
                    __id: newTonyuData.__id,
                    kotei_tonyu: newTonyuData.no_kotei + "-" + newTonyuData.no_tonyu,
                    cd_mark: App.settings.app.cd_mark.mark_0
                }
                tbody.form(page.detail.options.bindOption).bind(item);
                page.detail.setControlByMark(tbody, page.values.ma_mark[App.settings.app.cd_mark.mark_0], true);
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
                    entity = page.ma_haigo_meisai.data.entry(id);
                entity["no_tonyu"] = tonyu_next + 1;
                page.ma_haigo_meisai.data.update(entity);

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

            if (!selected.length) {
                App.ui.page.notifyAlert.message(App.messages.app.AP0012, "AP0012").show();
                return;
            }

            $("#save").prop("disabled", false);

            page.dialogs.confirmDialog.confirm({ text: App.messages.app.AP0011 }).then(function () {
                var kotei = parseInt(selected.attr("kotei")),
                selectorsKotei = page.detail.element.find("." + kotei)

                selectorsKotei.each(function (i, selectorKotei) {
                    page.detail.dataTable.dataTable("deleteRow", selectorKotei, function (row) {
                        var id = row.attr("data-key"),
                            newSelected;

                        row.find(":input").each(function (i, elem) {
                            App.ui.page.notifyAlert.remove(elem);
                        });

                        if (!App.isUndefOrNull(id)) {
                            var entity = page.ma_haigo_meisai.data.entry(id);
                            page.ma_haigo_meisai.data.remove(entity);
                        }
                    });
                });

                if (page.detail.element.find(".new").length == 0) {
                    page.detail.addNewKotei(true);
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
                        entity = page.ma_haigo_meisai.data.entry(id);
                        entity["no_kotei"] = i - 1;
                        page.ma_haigo_meisai.data.update(entity);
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

            if (!selected.length) {
                App.ui.page.notifyAlert.message(App.messages.app.AP0008, "AP0008").show();
                return;
            }

            var kotei = parseInt(selected.attr("kotei")),
                kotei_tonyu = selected.attr("kotei-tonyu");

            App.ui.page.notifyAlert.remove("AP0014");
            if (kotei_tonyu == kotei + "-" + 1) {
                App.ui.page.notifyAlert.message(App.messages.app.AP0014, "AP0014").show();
                return;
            }

            $("#save").prop("disabled", false);

            if (page.detail.element.find("." + kotei).length == 2) {

                page.detail.dataTable.dataTable("deleteRow", selected, function (row) {
                    var id = row.attr("data-key"),
                        newSelected;

                    row.find(":input").each(function (i, elem) {
                        App.ui.page.notifyAlert.remove(elem);
                    });

                    if (!App.isUndefOrNull(id)) {
                        var entity = page.ma_haigo_meisai.data.entry(id);
                        page.ma_haigo_meisai.data.remove(entity);
                    }
                });

                var newTonyuData = {
                    cd_haigo: page.header.element.findP("cd_haigo").text() == "" ? -1 : page.header.element.findP("cd_haigo").text(),
                    no_kotei: kotei,
                    no_tonyu: 2,
                    cd_hin: null,
                    flg_shitei: 0,
                    kbn_hin: null,
                    kbn_shikakari: null,
                    nm_hin: null,
                    cd_mark: App.settings.app.cd_mark.mark_0,
                    qty_haigo: null,
                    qty_nisugata: null,
                    gosa: null,
                    budomari: null,
                    kbn_bunkatsu: 0
                }

                page.ma_haigo_meisai.data.add(newTonyuData);

                page.detail.dataTable.dataTable("insertRow", page.detail.element.find("." + kotei + "-" + 1), true, function (tbody) {

                    tbody.find(".kotei").remove();

                    var item = {
                        __id: newTonyuData.__id,
                        kotei_tonyu: newTonyuData.no_kotei + "-" + newTonyuData.no_tonyu,
                        cd_mark: App.settings.app.cd_mark.mark_0
                    }

                    tbody.form(page.detail.options.bindOption).bind(item);

                    page.detail.setControlByMark(tbody, page.values.ma_mark[App.settings.app.cd_mark.mark_0], true);
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
                    var entity = page.ma_haigo_meisai.data.entry(id);
                    page.ma_haigo_meisai.data.remove(entity);
                }

                rowFocus = row.next().not(".item-tmpl");
                if (!rowFocus.length) {
                    rowFocus = row.prev().not(".item-tmpl");
                }
                else {
                    var newSelected = rowFocus;
                    for (; ;) {
                        var kotei_newSelected = parseInt(newSelected.attr("kotei"))
                        tonyu_newSelected = parseInt(newSelected.attr("tonyu"));

                        if (kotei_newSelected != kotei) {
                            break;
                        }

                        newSelected.removeClass(kotei_newSelected + "-" + tonyu_newSelected);
                        newSelected.addClass(kotei_newSelected + "-" + (tonyu_newSelected - 1));
                        newSelected.attr("tonyu", tonyu_newSelected - 1);
                        newSelected.attr("kotei-tonyu", kotei_newSelected + "-" + (tonyu_newSelected - 1));
                        newSelected.findP("kotei_tonyu").text(kotei_newSelected + "-" + (tonyu_newSelected - 1));

                        var id = newSelected.attr("data-key"),
                            entity = page.ma_haigo_meisai.data.entry(id);
                        entity["no_tonyu"] = tonyu_newSelected - 1;

                        page.ma_haigo_meisai.data.update(entity);

                        if (newSelected.next().not(".item-tmpl").length == 0) {
                            break;
                        }
                        newSelected = newSelected.next().not(".item-tmpl");
                            
                    }
                }
                
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

            var id, entity = {};

            if (!isNotUpdate) {
                id = tbody.attr("data-key"),
                entity = page.ma_haigo_meisai.data.entry(id);
            }

            if (!data) {
                tbody.findP("qty_haigo").val("").prop("disabled", true);
                tbody.findP("qty_nisugata").val("").prop("disabled", true);
                tbody.findP("budomari").val("").prop("disabled", true);
                tbody.findP("gosa").val("").prop("disabled", true);
                return;
            }

            //check qty_haigo
            var ss_kbn_qty_haigo = data.ss_kbn_qty_haigo,
                qty_haigo = tbody.findP("qty_haigo"),
                qty_haigo_val = qty_haigo.val() == "" ? null : qty_haigo.val().replace(/,/g, "");

            if (ss_kbn_qty_haigo == App.settings.app.kbn_nyuryoku.fuka) {
                qty_haigo.prop("disabled", true);
                qty_haigo.val("");
                entity["qty_haigo"] = null;
            }
            else if (ss_kbn_qty_haigo == App.settings.app.kbn_nyuryoku.kano) {
                qty_haigo.prop("disabled", false);
                entity["qty_haigo"] = qty_haigo_val;
            }
            else {
                qty_haigo.prop("disabled", false);
            }

            //check qty_nisugata
            var ss_kbn_nisugata = data.ss_kbn_nisugata,
                qty_nisugata = tbody.findP("qty_nisugata"),
                qty_nisugata_val = qty_nisugata.val() == "" ? null : qty_nisugata.val().replace(/,/g, "")

            if (ss_kbn_nisugata == App.settings.app.kbn_nyuryoku.fuka) {
                qty_nisugata.prop("disabled", true);
                qty_nisugata.val("");
                entity["qty_nisugata"] = null;
            }
            else if (ss_kbn_nisugata == App.settings.app.kbn_nyuryoku.kano) {
                qty_nisugata.prop("disabled", false);
                entity["qty_nisugata"] = qty_nisugata_val;
            }
            else {
                qty_nisugata.prop("disabled", false);
            }

            //check gosa
            var ss_kbn_gosa = data.ss_kbn_gosa,
                gosa = tbody.findP("gosa");
                gosa_val = gosa.val() == "" ? null : gosa.val().replace(/,/g, "")

            if (ss_kbn_gosa == App.settings.app.kbn_nyuryoku.fuka) {
                gosa.prop("disabled", true);
                gosa.val("");
                entity["gosa"] = null;
            }
            else if (ss_kbn_gosa == App.settings.app.kbn_nyuryoku.kano) {
                gosa.prop("disabled", false);
                entity["gosa"] = gosa_val;
            }
            else {
                gosa.prop("disabled", false);
            }

            //check budomari
            var ss_kbn_budomari = data.ss_kbn_budomari,
                budomari = tbody.findP("budomari"),
                budomari_val = budomari.val() == "" ? null : budomari.val().replace(/,/g, "")

            if (ss_kbn_budomari == App.settings.app.kbn_nyuryoku.fuka) {
                budomari.prop("disabled", true);
                budomari.val("");
                entity["budomari"] = null;
            }
            else if (ss_kbn_budomari == App.settings.app.kbn_nyuryoku.kano) {
                budomari.prop("disabled", false);
                entity["budomari"] = budomari_val;
            }
            else {
                budomari.prop("disabled", false);
            }

            if (!isNotUpdate) {
                page.ma_haigo_meisai.data.update(entity);
            }
        }

        /**
         * Check Not data detail
         */
        page.detail.checkNotHaveDataDetail = function () {
            var rows = page.detail.element.find("tbody.new");
            if (rows.length != 2) {
                return false;
            }

            var fistRow = $(rows[0]);
            if (fistRow.findP("nm_kotei").val() != "") {
                return false;
            }

            var secondRow = $(rows[1]);
            if (secondRow.findP("kbn_bunkatsu").val() != "") {
                return false;
            }

            if (secondRow.findP("cd_hin").val() != "") {
                return false;
            }

            if (secondRow.findP("nm_hin").val() != "") {
                return false;
            }

            if (secondRow.findP("qty_haigo").val() != "") {
                return false;
            }

            if (secondRow.findP("qty_nisugata").val() != "") {
                return false;
            }

            if (secondRow.findP("budomari").val() != "") {
                return false;
            }

            if (secondRow.findP("gosa").val() != "") {
                return false;
            }
            return true;
        }

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
                        check:true
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
                row = $(e.target).closest("tbody"),
                no_seiho_kaisha = page.header.element.findP("no_seiho_kaisha").val(),
                row;

            page.dialogs.hinmeiKaihatsuDialog.param.no_gamen = App.settings.app.no_gamen.haigo_toroku_kaihatsu;
            page.dialogs.hinmeiKaihatsuDialog.param.cd_kaisha = page.values.cd_kaisha_daihyo;
            page.dialogs.hinmeiKaihatsuDialog.param.cd_kojyo = page.values.cd_kojyo_daihyo;
            if (no_seiho_kaisha == "") {
                page.dialogs.hinmeiKaihatsuDialog.param.no_seiho_kaisha = -1;
            }
            else {
                page.dialogs.hinmeiKaihatsuDialog.param.no_seiho_kaisha = parseInt(no_seiho_kaisha, 10);
            }

            page.dialogs.hinmeiKaihatsuDialog.element.modal("show");

            //検索ダイアログでデータ選択が実行された時に呼び出される関数を設定しています。
            page.dialogs.hinmeiKaihatsuDialog.dataSelected = function (data) {

                var id = row.attr("data-key"),
                    entity = page.ma_haigo_meisai.data.entry(id);

                setTimeout(function () {
                    if (data.kbn_hin == App.settings.app.kbnHin.sagyo) {
                        if (data.nm_hin != row.findP("nm_hin").val() || row.findP("cd_hin").val() != App.settings.app.commentCode) {
                            $("#save").prop("disabled", false);
                            row.removeClass("row-error-past");
                            row.removeClass("row-error-past-css");
                            row.findP("cd_hin").removeClass("errorNoKikaku")
                            row.findP("cd_hin").val(App.settings.app.commentCode);
                            row.findP("nm_hin").val(data.nm_hin);
                            if (entity["kbn_hin"] != App.settings.app.kbnHin.sagyo) {
                                row.findP("cd_mark").text(App.settings.app.cd_mark.mark_18);
                                var mark_18 = page.getObjMark(App.settings.app.cd_mark.mark_18, null);
                                row.findP("mark").text(mark_18 ? mark_18.mark : "");
                                entity["cd_mark"] = App.settings.app.cd_mark.mark_18;
                                page.detail.setControlByMark(row, page.values.ma_mark[App.settings.app.cd_mark.mark_18]);
                                page.detail.calculateQtyHaigoKei();
                                page.detail.setTaniShiyo(row);
                            }

                            page.detail.setNokikaku(row,-1);

                            entity["cd_hin"] = App.settings.app.commentCode;
                            entity["kbn_hin"] = App.settings.app.kbnHin.sagyo;
                            entity["nm_hin"] = data.nm_hin;
                            entity["kbn_shikakari"] = null;
                            row.findP("kbn_shikakari").text("");
                            row.findP("kbn_hin").text(App.settings.app.kbnHin.sagyo);
                            row.findP("no_kikaku").text("");
                            row.findP("hijyu").text("");

                            page.ma_haigo_meisai.data.update(entity);

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
                        if (row.findP("cd_hin").val() == "" || parseInt(data.cd_hin, 10) != parseInt(row.findP("cd_hin").val(), 10)) {
                            $("#save").prop("disabled", false);
                            row.removeClass("row-error-past");
                            row.removeClass("row-error-past-css");
                            row.findP("cd_hin").removeClass("errorNoKikaku");

                            var no_seiho_kaisha = page.header.element.findP("no_seiho_kaisha").val();
                            var parameter = {
                                cd_kaisha_daihyo: page.values.cd_kaisha_daihyo,
                                cd_kojyo_daihyo: page.values.cd_kojyo_daihyo,
                                cd_kaisha: no_seiho_kaisha == "" ? -1 : parseInt(no_seiho_kaisha, 10),
                                cd_hin: data.cd_hin,
                                kbn_hin: App.isUndefOrNull(data.kbn_hin)? 0 : data.kbn_hin,
                                kbn_shikakari:App.isUndefOrNull(data.kbn_shikakari)? 0:data.kbn_shikakari
                            }
                            return $.ajax(App.ajax.webapi.get(page.urls.haigoTorokuKaihatsuBumon + "/getInfoHinmei", parameter)
                            ).then(function (result) {
                                if (result.length > 0) {
                                    var hinmei = result[0];
                                    for (var i = 0 ; i < result.length; i++) {
                                        if (result[i].kbn_hin == App.settings.app.kbnHin.shikakari) {
                                            hinmei = result[i];
                                            break;
                                        }
                                    }

                                    if (hinmei.kbn_hin == App.settings.app.kbnHin.genryo && (hinmei.cd_hin < page.values.genryoRangeMin || hinmei.cd_hin > page.values.genryoRangeMax) && (App.isUndefOrNull(hinmei.no_kikaku) || hinmei.no_kikaku == "")) {
                                        row.findP("cd_hin").addClass("errorNoKikaku");
                                    }

                                    if (App.isUndefOrNull(entity) || entity["kbn_hin"] == App.settings.app.kbnHin.sagyo) {
                                        row.findP("cd_mark").text(App.settings.app.cd_mark.mark_0);
                                        var mark_0 = page.getObjMark(App.settings.app.cd_mark.mark_0, null);
                                        row.findP("mark").text(mark_0 ? mark_0.mark : "");
                                        entity["cd_mark"] = App.settings.app.cd_mark.mark_0;
                                    }
                                        
                                    row.findP("nm_hin").val(hinmei.nm_hin);
                                    row.findP("hijyu").text(App.isUndefOrNull(hinmei.hijyu) ? "" : App.num.format(Number(hinmei.hijyu), "#,#.000"));
                                    row.findP("no_kikaku").text(App.isUndefOrNull(hinmei.no_kikaku) ? "" : hinmei.no_kikaku);
                                    row.findP("budomari").val(App.isUndefOrNull(hinmei.budomari) ? "" : App.num.format(Number(hinmei.budomari), "#,#.00"));
                                    row.findP("qty_nisugata").val(App.isUndefOrNull(hinmei.nisugata_gty) ? "" : App.num.format(Number(hinmei.nisugata_gty), "#,#.000"));
                                    row.findP("kbn_shikakari").text(hinmei.kbn_shikakari);
                                    row.findP("kbn_hin").text(hinmei.kbn_hin);
                                    row.findP("cd_tani_hin").text(App.isUndefOrNull(hinmei.cd_tani_hin) ? "" : hinmei.cd_tani_hin);

                                    entity["cd_hin"] = data.cd_hin;
                                    entity["nm_hin"] = hinmei.nm_hin;
                                    entity["kbn_hin"] = hinmei.kbn_hin_toroku;
                                    entity["kbn_shikakari"] = hinmei.kbn_shikakari;
                                    entity["qty_nisugata"] = hinmei.nisugata_gty;
                                    entity["budomari"] = hinmei.budomari;
                                    page.ma_haigo_meisai.data.update(entity);
                                    page.detail.setFormatHinmei(row, hinmei);
                                    page.detail.setNokikaku(row, hinmei.kbn_hin, hinmei.kbn_shikakari);
                                    page.detail.setControlByMark(row, page.values.ma_mark[row.findP("cd_mark").text()]);
                                    page.detail.calculateQtyHaigoKei();
                                    page.detail.setTaniShiyo(row);

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
                                    page.detail.setNokikaku(row,-1);
                                    row.findP("nm_hin").val("");
                                    row.findP("hijyu").text("");
                                    row.findP("no_kikaku").text("");
                                    row.findP("budomari").val("");
                                    row.findP("qty_nisugata").val("");
                                    row.findP("kbn_shikakari").text("");
                                    row.findP("kbn_hin").text("");
                                    row.findP("cd_tani_hin").text("");
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
                },100)

                delete page.dialogs.hinmeiKaihatsuDialog.dataSelected;
            }
        };

        /**
         * 画面明細の一覧から検索ダイアログを表示します。
         */
        page.detail.showMarkDialog = function (e) { 
            var element = page.detail.element,
                tbody = $(e.target).closest("tbody"),
                id = tbody.attr("data-key"),
                entity = page.ma_haigo_meisai.data.entry(id);
                

            page.dialogs.markDialog.options.parameter.no_gamen = App.settings.app.no_gamen.haigo_toroku_kaihatsu;
            page.dialogs.markDialog.options.parameter.cd_kaisha = page.values.cd_kaisha_daihyo;
            page.dialogs.markDialog.options.parameter.cd_kojyo = page.values.cd_kojyo_daihyo;

            page.dialogs.markDialog.element.modal("show");

            //検索ダイアログでデータ選択が実行された時に呼び出される関数を設定しています。
            page.dialogs.markDialog.dataSelected = function (data) {
                
                $("#save").prop("disabled", false);
                if (data.cd_mark != tbody.findP("cd_mark")) {

                    tbody.findP("cd_mark").text(data.cd_mark);
                    tbody.findP("mark").text(data.mark);
                    
                    page.detail.setControlByMark(tbody, data);
                    page.detail.setTaniShiyo(tbody);
                    page.detail.calculateQtyHaigoKei();

                    entity["cd_mark"] = data.cd_mark;
                    page.ma_haigo_meisai.data.update(entity);

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
        page.detail.moveToHaigoTorokuKaihatsuBumon = function(e){
            var target = $(e.target),
                row = target.closest("tbody"),
                id = row.attr("data-key"),
                entity = page.ma_haigo_meisai.data.entry(id),
                cd_hin = entity["cd_hin"];

            window.open(App.str.format("203_HaigoTorokuKaihatsuBumon.aspx?cd_haigo={0}&M_HaigoTorokuKaihatsu={1}&flg_edit_shikakarihin={2}",cd_hin,App.settings.app.m_haigo_toroku_kaihatsu.shosai,page.values.shikakarihin_edit));
        }
        
        //TODO-END: 以下の page.detail の各宣言は画面仕様ごとにことなるため、不要の場合は削除してください。

        //TOsVN - 19075 - 2023/06/20: st ShohinKaihatuSupport Modify 2023
        page.taishoData = function (mode) {
            var element = page.header.element,
                dataFrame = "{no_seiho_kaisha}-{no_seiho_shurui}-{no_seiho_nen}-{no_seiho_renban}",
                props =
                [
                    "no_seiho_kaisha",
                    "no_seiho_shurui",
                    "no_seiho_nen",
                    "no_seiho_renban",
                ], data = {};

            if (mode && mode == App.settings.app.log_mode.haigoMode.copy) {
                return element.findP("no_seiho_sanko").text();
            }

            props.forEach(function (val, index) {
                data[val] = element.findP(val).val();
            });

            return App.str.format(dataFrame, data);
        };
        //ed

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

        /**
         * jQuery イベントで、ページの読み込み処理を定義します。
         */
        $(document).ready(function () {
            // ページの初期化処理を呼び出します。
            page.initialize();
        });

    </script>
</asp:Content>
<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="content-wrap smaller">
        <!--TODO: ヘッダーを定義するHTMLをここに記述します。-->
        <label id="title-mode" style="margin-left:10px;font-size:18px;text-align:center;font-weight:bold;"></label>
        <div class="header" style="margin-top:3px;">
            <div title="製法" class="part">
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>製法番号<span style="color: red">*</span></label>
                    </div>
                    <div class="control col-xs-2">
                        <%--<label>製法番号<span style="color: red">*</span></label>--%>
                        <input type="tel" data-prop="no_seiho_kaisha" maxlength="4" class="limit-input-int no-seiho-group" style="width:41px;ime-mode:disabled"/>
                        <span style="width:5px;">-</span>
                        <select class="number no-seiho-group" data-prop="no_seiho_shurui" style="width:63px;"></select>
                        <span>-</span>
                        <input type="tel" maxlength="2" data-prop="no_seiho_nen" class="limit-input-int no-seiho-group" style="width:24px;ime-mode:disabled;"/>
                        <span>-</span>
                        <input type="tel" data-prop="no_seiho_renban" disabled="disabled" style="width:41px;"/>
                    </div>
                    
                    <div class="control-label col-xs-2">
                        <label style="width:35%;">製品種類</label>
                        <label data-prop="nm_hin_syurui"></label>
                    </div>
                    <div class="control-label col-xs-2">
                        <label style="width:35%">製法分類</label>
                        <select data-prop="cd_seiho_bunrui" style="width:63%;" class="number"></select>
                    </div>
                    <div class="control-label col-xs-5">
                        <label class="overflow-ellipsis" style="width:50px;min-width:50px;">登録日</label>
                        <label class="overflow-ellipsis" style="width:150px;" data-prop="dt_toroku"></label>
                        <label class="overflow-ellipsis" style="width:123px;" data-prop="nm_user_toroku"></label>
                        <label class="overflow-ellipsis" style="width:55px;min-width:55px;border-left:5px solid #dfdfdf;height:21px;">更新日</label>
                        <label class="overflow-ellipsis" style="width:150px;" data-prop="dt_henko"></label>
                        <label class="overflow-ellipsis" style="width:123px;" data-prop="nm_user_henko"></label>
                    </div>
                </div>

                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>製法名<span style="color: red">*</span></label>
                    </div>
                    <div class="control col-xs-7">
                        <input type="text" data-prop="nm_seiho" class="ime-active"/>
                    </div>
                    <div class="control col-xs-4 right">
                        <button type="button" id="excel-haigo-list" class="btn btn-xs btn-primary" style="display: none;">配合表EXCEL</button>
                        <button type="button" id="seizo-kojo-shitei" class="btn btn-xs btn-primary">製造工場指定</button>
                        <button type="button" id="shiryo-tenpu" class="btn btn-xs btn-primary">資料添付/参照</button>
                        <button type="button" id="seizo-bunsho" class="btn btn-xs btn-primary">製法文書</button>
                        <button type="button" id="seizo-bunsho-sansho" class="btn btn-xs btn-primary">製法文書参照</button>
                    </div>
                </div>

            </div>
            <div title="配合情報" class="part not-color">
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>配合コード</label>
                    </div>
                    <div class="control col-xs-2">
                        <label data-prop="cd_haigo"></label>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>ステータス</label>
                    </div>
                    <div class="control col-xs-1">
                        <label data-prop="nm_status" style="font-weight:bold;"></label>
                    </div>
                    <div class="control col-xs-2">
                        <label class="min-zero">（</label>
                        <label class="check-group min-zero">
                            <input type="checkbox" value ="true" data-prop="flg_mishiyo" />
                            未使用
                        </label>
                        <label class="check-group min-zero">
                            <input type="checkbox" value="1" data-prop="kbn_haishi" />
                            廃止
                        </label>
                        <label class="min-zero">）</label>
                    </div>

                    <div class="control col-xs-5">
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>配合名<span style="color: red">*</span></label>
                    </div>
                    <div class="control col-xs-6">
                        <input type="text" class="ime-active" data-prop="nm_haigo"/>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>配合名略称</label>
                    </div>
                    <div class="control col-xs-4">
                        <input type="text" class="ime-active" data-prop="nm_haigo_r" style="width: 400px;" />
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>品区分<span style="color: red">*</span></label>
                    </div>
                    <div class="control col-xs-2">
                        <select class="" data-prop="kbn_hin"></select>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>基本倍率（係数）<span style="color: red">*</span></label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="tel" class="number-right limit-input-float" maxlength="6" data-prop="ritsu_kihon" style="width: 65px;ime-mode:disabled;"/>
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
                    <div class="control-label col-xs-1">
                        <label>製品情報</label>
                    </div>
                    <div class="control col-xs-2">
                        <label class="overflow-ellipsis" data-prop="nm_seihin"></label>
                    </div>
                </div>

                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>参考製法番号</label>
                    </div>
                    <div class="control col-xs-2">
                        <label data-prop="no_seiho_sanko"></label>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>参考配合コード</label>
                    </div>
                    <div class="control col-xs-1">
                        <label data-prop="cd_haigo_sanko" ></label>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>代表工場</label>
                    </div>
                    <div class="control col-xs-2">
                        <label class="overflow-ellipsis" data-prop="nm_kojyo" style="color:red;font-weight:bold;">代表工場を指定ください</label>
                    </div>
                    <div class="control col-xs-4 right">
                        <button type="button" id="moto-sentaku" class="btn btn-xs btn-primary" style="display: none;" disabled="disabled" >リニューアル元選択</button>
                        <button type="button" id="haigoTorikomi-dialog" class="btn btn-xs btn-primary btn_haigou_torikomi">配合取込</button>
                        <button type="button" id="tsuikaJohoNyuryoku-dialog" class="btn btn-xs btn-primary">追加情報入力</button>
                        <button type="button" id="seihinJohoNyuryoku-dialog" class="btn btn-xs btn-primary">製品コード入力</button>

                    </div>
                </div>
            </div>
        </div>
        <!--TODO: 明細を定義するHTMLをここに記述します。-->
        <div class="detail">
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
                            <th style="width: 442px;">品名/作業指示</th>
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
                            <td colspan="8">
                                <div class="control col-xs-1" style="text-align:center;">
                                    <label>メモ</label>
                                </div>
                                <div class="control col-xs-11">
                                    <input type="text" class="ime-active" data-prop="biko" />
                                </div>
                            </td>
                        </tr>
                    </tfoot>
                    <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->
                    <!--工程名追加　工程追加ボタン押下時に行追加-->
                    <tbody class="item-tmpl" style="cursor: default; display: none;">
                        <tr>
                            <!--TODO: 単一行を作成する場合は、下記を利用します。-->
                            <td>
                                <span class="select-tab unselected"></span>
                            </td>
                            <td class="kotei">
                                <span></span>
                            </td>
                            <td class="kotei" style="text-align:right;">
                                <span data-prop="kotei_tonyu"></span>
                            </td>
                            <td class="kotei">
                                <span>工程名</span>
                            </td>
                            <td class="kotei" colspan="5">
                                <%--<select data-prop="nm_kotei"></select>--%>
                                <input type="text" data-prop="nm_kotei" class="input-kotei"  list="listKotei">
                                <datalist style="width:300px;" id="listKotei" data-prop="nm_kotei_list">
                                </datalist>
                            </td>
                            <td class="kotei" colspan ="5">
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
                                <input type="checkbox" value="true" data-prop="flg_shitei" />
                            </td>
                            <td class="tonyu">
                                <input type="text" class="ime-active" data-prop="nm_hin" />
                            </td>
                            <td class="tonyu">
                                <label data-prop="cd_mark" style="display:none;">0</label>
                                <label data-prop="mark" style="width: 25px;text-align:center;"></label>
                                <button type="button" class="btn btn-xs btn-info mark" style="min-width:25px;margin-bottom:2px;">&nbsp;</button>
                            </td>
                            <td class="tonyu">
                                <input type="tel" maxlength="12" class="limit-input-float format-when-focus" style="ime-mode:disabled;text-align:right;" data-prop="qty_haigo" />
                            </td>
                            <td class="tonyu" style="text-align:center;">
                                <label data-prop="kbn_shikakari" style="display:none;"></label>
                                <label data-prop="kbn_hin" style="display:none;"></label>
                                <label data-prop="cd_tani_hin" style="display:none;"></label>
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
                    <!-- 工程内行追加　行追加ボタン　押下時に行追加-->
                </table>
        </div>

    </div>
</asp:Content>

<asp:Content ID="FooterContent" ContentPlaceHolderID="FooterContent" runat="server">
    <div class="command-left">
        <!--TODO: コマンドボタン（左寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" id="csv" class="btn btn-sm btn-primary" disabled="disabled">CSV</button>
    </div>
    <div class="command">
        <!--TODO: コマンドボタン（右寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" id="shisaku-copy" class="btn btn-sm btn-primary" style="display: none">試作コピー</button>
        <button type="button" id="save" class="btn btn-sm btn-primary">保存</button>
    </div>
</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>
