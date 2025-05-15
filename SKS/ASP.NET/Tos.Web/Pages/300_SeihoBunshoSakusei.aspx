<%@ Page Title="300_製法文書作成" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="300_SeihoBunshoSakusei.aspx.cs" Inherits="Tos.Web.Pages._300_SeihoBunshoSakusei" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchList(Ver2.1)】 Template--%>

<asp:Content ID="IncludeContent" ContentPlaceHolderID="IncludeContent" runat="server">

    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/part.css") %>" type="text/css" />
    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/headerdetail.css") %>" type="text/css" />
    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/selectize.css") %>" type="text/css" />
    <%--<link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/selectize.default.css") %>" type="text/css" />--%>
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

    <style type="text/css">
        div .detail-command {
            text-align: center;
        }

        .btn-next-search {
            width: 200px;
        }

        table.datatable th.selected {
            font-weight: bold;
        }

        .header .float-left {
            float: left;
        }

        .header span.float-left {
            padding-top: 2px;
        }

        .header button {
            min-width: 40px;
        }

        .nav-tabs .tab-a {
            padding: 2px 10px;
            background-color: #f6f6f6;
        }

        .loading .loading-image {
            /*height: 300px;*/
        }

        .fit-25 {
            max-width: 342px!important;
        }

        .part.full-height {
            height: calc(100% - 2px);
        }

        .fit-25::-webkit-scrollbar,
        .fit-30::-webkit-scrollbar {
            width: 8px;
        }

        .fit-25::-webkit-scrollbar-track,
        .fit-30::-webkit-scrollbar-track {
            background: #f1f1f1;
        }

        .fit-25::-webkit-scrollbar-thumb,
        .fit-30::-webkit-scrollbar-thumb {
            background: #888;
            /*border-radius: 4px;*/
            cursor: default!important;
        }

            .fit-25::-webkit-scrollbar-thumb:hover
            .fit-30::-webkit-scrollbar-thumb:hover {
                background: #555;
                cursor: default;
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

        .selectize-control .selectize-input.disabled {
            opacity: 0.9;
        }

        .selectize-control.single .selectize-input:after {
            right: 6px;
        }

        .smaller .textarea-height:not(.bar-less) {
            margin-left: 3px;
            max-width: calc(100% - 3px);
            width: calc(100% - 3px)!important;
        }

        .selectize-control div.item {
            width: calc(100% - 8px);
            overflow: hidden;
        }
        
        /*input[list]::-webkit-calendar-picker-indicator {
          display: none;
        }
        .datalist-content {
            position: relative;
            float: left;
        }
        td .datalist-content {
            width: 100%;
        }
        .datalist-content::after {
            content: ' ';
            display: block;
            position: absolute;
            top: 50%;
            right:6px;
            margin-top: -3px;
            width: 0;
            height: 0;
            border-style: solid;
            border-width:6px 3px 0 3px;
            border-color:#333 transparent transparent transparent;
        }*/

    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("_300_SeihoBunshoSakusei", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.dataTakeCount,   // TODO:取得するデータ数を指定します。
                filter: "",
                mode: {
                    "new": "new",
                    edit: "edit",
                    copy: "copy",
                    apply: "apply",
                    view: "view",
                    new_copy: "new_copy",
                    edit_copy: "edit_copy"
                },
                tabID: App.settings.app.SeihobunshoTabName
            },
            values: {
                isFirstLoading: true,
                closeTabs: []                       // List of windows need to close in copy mode
            },
            urls: {
                confirmDialog: "../Pages/Dialogs/ConfirmDialog.aspx",
                _602_IroShitei_Dialog: "../Pages/Dialogs/602_IroShitei_Dialog.aspx",
                _801_SeihobunshoSakusei_Dialog: "../Pages/Dialogs/801_SeihobunshoSakusei_Dialog.aspx",
                _802_CopyHaniSelection_Dialog: "../Pages/Dialogs/802_CopyHaniSelection_Dialog.aspx",
                _803_ExcelHaniSelection_Dialog: "../Pages/Dialogs/803_ExcelHaniSelection_Dialog.aspx",
                _804_SeihinSansho_Dialog: "../Pages/Dialogs/804_SeihinSansho_Dialog.aspx",
                _805_ShikakarihinSentaku_Dialog: "../Pages/Dialogs/805_ShikakarihinSentaku_Dialog.aspx",
                _806_ShisakuretsuSentaku_Dialog: "../Pages/Dialogs/806_ShisakuretsuSentaku_Dialog.aspx",
                _800_HinmeiSentaku_Dialog: "../Pages/Dialogs/800_HinmeiSentaku_Dialog.aspx",
                mainMenu: "MainMenu.aspx",
                save: "../api/_300_SeihoBunshoSakusei",
                search: "../api/_300_SeihoBunshoSakusei",
                excelDownload: "../api/_300_SeihoBunshoSakusei_Excel/Post",
                transfer200: "200_SeihoIchiran.aspx?M_SeihoIchiran={0}&no_seiho={1}",
                staticExcelExport: "../api/_300_SeihoBunshoSakusei_Excel/GetStaticExcel"
            },
            header: {
                options: {},
                values: {},
                urls: {}
            },
            detail: {
                options: {},
                values: {}
            },
            tabs: {
                urls: {
                    // TAB 1
                    Hyoshi: "../Pages/300_SeihoBunshoSakusei_Hyoshi_Tab.aspx",
                    // TAB 2
                    YoukiHousou: "../Pages/300_SeihoBunshoSakusei_YoukiHousou_Tab.aspx",
                    // TAB 3
                    GenryoSetsubi: "../Pages/300_SeihoBunshoSakusei_GenryoSetsubi_Tab.aspx",
                    // TAB 4
                    HaigoSeizoChuiJiko: "../Pages/300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.aspx",
                    // TAB 5
                    SeizoKoteizu: "../Pages/300_SeihoBunshoSakusei_SeizoKoteizu_Tab.aspx",
                    // TAB 6
                    SeihinKikakuan: "../Pages/300_SeihoBunshoSakusei_SeihinKikakuan_Tab.aspx",
                    // TAB 7
                    SeihojyoKakuninJiko: "../Pages/300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.aspx",
                    // TAB 8
                    ShomikigenSetteiHyo: "../Pages/300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.aspx",
                    // TAB 9
                    RirekiHyo: "../Pages/300_SeihoBunshoSakusei_RirekiHyo_Tab.aspx",
                    // TAB 10
                    HaigoHyo: "../Pages/300_SeihoBunshoSakusei_HaigoHyo_Tab.aspx",
                }
            },
            dialogs: {},
            commands: {},
            param: {
                // Source seiho for copy mode
                no_seiho_copy: "",
                // Main no_seiho
                no_seiho: "",
                // Main no_seiho
                no_seiho_sakusei: "",
                // List of mapping cd_haigo on copy mode: cd_haigo_moto, cd_haigo_saki
                haigo_copy_mapping: [],
                // Page mode: new, edit, view, copy, apply
                // 1. New, 2. New Copy, 3. edit, 4. edit copy, 5. view, 6.view copy, 7.apply
                // Base mode: 1. NEW, 2. EDIT, 3. VIEW
                // Additons: 1. COPY, 2.APPLY
                mode: "",
                // Page status
                status: "",
                // List of TAB for copy mode
                no_Tab: [],
                // Page ID to open this page
                openerID: ""
            },
            mode: {
                config: {
                    NEW: {
                        base: "new",            // NEW
                        addt: "copy",           // COPY
                        getTabParam: function (tabID) {
                            var BASE = page.mode,
                                THIS = page.mode.config.NEW,
                                result = BASE.getBaseParam(tabID);
                            if (BASE.isTabCopy(tabID)) {
                                var addtModeParam = BASE.getCopyParam(tabID);
                                $.extend(result, addtModeParam);
                                result.mode = BASE.convertModeValue(THIS.base, THIS.addt);
                            } else {
                                result.mode = BASE.convertModeValue(THIS.base, "");
                            }
                            return result;
                        },
                        setDisplay: function () {
                            $("#copy-from, #excel-download, #save, #open-shisaku-sentaku").show();
                        },
                        validate: function () {
                            return App.async.success();
                        },
                        changeModeAfterSave: function () {
                            page.param.mode = App.settings.app.m_seiho_bunsho.henshu;
                        }
                    },
                    EDIT: {
                        base: "edit",                // EDIT
                        addt: "copy",                // COPY
                        getTabParam: function (tabID) {
                            var BASE = page.mode,
                                THIS = page.mode.config.EDIT,
                                result = BASE.getBaseParam(tabID);
                            if (BASE.isTabCopy(tabID)) {
                                var addtModeParam = BASE.getCopyParam(tabID);
                                $.extend(result, addtModeParam);
                                result.mode = BASE.convertModeValue(THIS.base, THIS.addt);
                            } else {
                                result.mode = BASE.convertModeValue(THIS.base, "");
                            }
                            return result;
                        },
                        setDisplay: function () {
                            $("#copy-from, #excel-download, #save, #open-shisaku-sentaku").show();
                        },
                        validate: function () {
                            return App.async.success();
                        }
                    },
                    VIEW: {
                        base: "view",                // EDIT
                        addt: "",                // COPY
                        getTabParam: function (tabID) {
                            var BASE = page.mode,
                                THIS = page.mode.config.VIEW,
                                result = BASE.getBaseParam(tabID);

                            result.mode = BASE.convertModeValue(THIS.base, "");
                            return result;
                        },
                        setDisplay: function () {
                            $("#excel-download, #save").show();
                        },
                        validate: function () {
                            return App.async.success();
                        }
                    },
                    COPY: {
                        base: "view",                // VIEW
                        addt: "",
                        getTabParam: function (tabID) {
                            var BASE = page.mode,
                                THIS = page.mode.config.COPY,
                                result = BASE.getBaseParam(tabID);

                            if (BASE.isTabCopy(tabID)) {
                                var addtModeParam = BASE.getCopyParam(tabID);
                                $.extend(result, addtModeParam);
                                result.mode = BASE.convertModeValue(THIS.base, THIS.addt);
                            } else {
                                result.mode = BASE.convertModeValue(THIS.base, "");
                            }
                            if (tabID == App.settings.app.SeihobunshoTabName.Hyoshi
                                || tabID == App.settings.app.SeihobunshoTabName.RirekiHyo) {
                                result.sub_mode = "view";
                            }
                            return result;
                        },
                        setDisplay: function () {
                            $("#copy-to").show();
                        },
                        validate: function () {
                            return App.async.success();
                        }
                    },
                    APPLY_NEW: {
                        base: "new",                  // new
                        addt: "copy",                 // copy
                        getTabParam: function (tabID) {
                            var BASE = page.mode,
                                THIS = page.mode.config.APPLY_NEW,
                                result = BASE.getBaseParam(tabID);
                            if (BASE.isTabCopy(tabID)) {
                                var addtModeParam = BASE.getCopyParam(tabID);
                                $.extend(result, addtModeParam);
                                result.mode = BASE.convertModeValue(THIS.base, THIS.addt);
                            } else {
                                result.mode = BASE.convertModeValue(THIS.base, "");
                            }
                            result.sub_mode = "apply";
                            return result;
                        },
                        setDisplay: function () {
                            $("#copy-from, #excel-download, #save, #open-shisaku-sentaku").show();
                        },
                        validate: function () {
                            if (_300_SeihoBunshoSakusei_Hyoshi_Tab.getShoninLevel() <= 0) {
                                App.ui.page.notifyAlert.message(_300_SeihoBunshoSakusei_Hyoshi_Tab.name + App.messages.app.AP0091, _300_SeihoBunshoSakusei_Hyoshi_Tab.element.find(".apply-shonin[data-shonin-level='1']")).show();
                                return App.async.fail();
                            }
                            return App.async.success();
                        }
                    },
                    APPLY_EDIT: {
                        base: "edit",                 // edit
                        addt: "copy",                 // copy
                        getTabParam: function (tabID) {
                            var BASE = page.mode,
                                THIS = page.mode.config.APPLY_EDIT,
                                result = BASE.getBaseParam(tabID);
                            if (BASE.isTabCopy(tabID)) {
                                var addtModeParam = BASE.getCopyParam(tabID);
                                $.extend(result, addtModeParam);
                                result.mode = BASE.convertModeValue(THIS.base, THIS.addt);
                            } else {
                                result.mode = BASE.convertModeValue(THIS.base, "");
                            }
                            result.sub_mode = "apply";
                            return result;
                        },
                        setDisplay: function () {
                            $("#copy-from, #excel-download, #save, #open-shisaku-sentaku").show();
                        },
                        validate: function () {
                            if (_300_SeihoBunshoSakusei_Hyoshi_Tab.getShoninLevel() <= 0) {
                                App.ui.page.notifyAlert.message(_300_SeihoBunshoSakusei_Hyoshi_Tab.name + App.messages.app.AP0091, _300_SeihoBunshoSakusei_Hyoshi_Tab.element.find(".apply-shonin[data-shonin-level='1']")).show();
                                return App.async.fail();
                            }
                            return App.async.success();
                        }
                    },
                    APPLY_VIEW: {
                        base: "view",                 // view
                        addt: "",                    // APPLY
                        getTabParam: function (tabID) {
                            var BASE = page.mode,
                                THIS = page.mode.config.APPLY_VIEW,
                                result = BASE.getBaseParam(tabID);
                            result.mode = BASE.convertModeValue(THIS.base, "");
                            //if (tabID == App.settings.app.SeihobunshoTabName.Hyoshi) {
                            result.sub_mode = "apply";
                            //}
                            return result;
                        },
                        setDisplay: function () {
                            $("#excel-download, #save").show();
                        }
                    },
                    BROWSE: {
                        base: "view",                 // VIEW
                        addt: "",                       // APPLY
                        getTabParam: function (tabID) {
                            var BASE = page.mode,
                                THIS = page.mode.config.BROWSE,
                                result = BASE.getBaseParam(tabID);
                            result.mode = BASE.convertModeValue(THIS.base, "");
                            if (tabID == App.settings.app.SeihobunshoTabName.Hyoshi) {
                                result.sub_mode = "view";
                            }
                            if (tabID == App.settings.app.SeihobunshoTabName.RirekiHyo && page.param.no_gamen !== App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
                                result.sub_mode = "view";
                            }
                            return result;
                        },
                        setDisplay: function () {
                            $("#excel-download").show();
                            if (page.param.no_gamen === App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
                                $("#save").show();
                            }
                        }
                    }
                },
                getCopyParam: function (tabID) {
                    if (tabID == page.options.tabID.SeizoKoteizu) {
                        return {
                            haigoMapping: page.param.haigo_copy_mapping
                        }
                    }
                    return {};
                },
                getBaseParam: function (tabID) {
                    var param = page.param;
                    var result = {};
                    if (tabID == page.options.tabID.HaigoSeizoChuiJiko) {
                        result = {
                            pt_kotei: page.values.pt_kotei
                        }
                    }
                    if (tabID == page.options.tabID.SeihinKikakuan) {
                        result = {
                            pt_kotei: page.values.pt_kotei,
                            cd_naiyoryo_tani: page.values.cd_naiyoryo_tani,
                            yoryo: page.values.yoryo
                        }
                    }
                    if (tabID == page.options.tabID.SeizoKoteizu || tabID == page.options.tabID.HaigoHyo) {
                        result = {
                            canSearch: (page.values.haigoHeaderData && page.values.haigoHeaderData.length > 0) ? true : false
                        }
                    }
                    var seihoData = page.values.seihoData;
                    return $.extend(result, {
                        no_seiho: param.no_seiho,
                        no_seiho_copy: param.no_seiho_copy,
                        cd_shain: seihoData.cd_shain,
                        nen: seihoData.nen,
                        no_oi: seihoData.no_oi,
                        seq_shisaku: seihoData.seq_shisaku,
                        isApplied: (page.getShinseiStatus() > App.settings.app.kbn_status.henshuchu) ? true : false
                    });
                },
                isTabCopy: function (TabName) {
                    var param = page.param;
                    if (!param.no_seiho_copy) {
                        // No source seiho to copy
                        return false;
                    }
                    return $.grep(param.no_Tab, function (tabID) {
                        return (tabID == TabName);
                    }).length > 0;
                },
                getMode: function (dataTab1) {
                    var param = page.param,
                        BASE = page.mode;

                    // Data went wrong: status > 0 but bunsho not found
                    if (page.getShinseiStatus() > App.settings.app.kbn_status.henshuchu) {
                        if (!dataTab1) {
                            return BASE.deactivePage();
                        }
                    }
                    // Param copy mode
                    if (param.mode == App.settings.app.m_seiho_bunsho.copy) {
                        if (!dataTab1) {
                            return BASE.deactivePage();
                        } else {
                            return BASE.config.COPY;
                        }
                    }
                    // Param browse mode
                    if (param.mode == App.settings.app.m_seiho_bunsho.etsuran) {
                        if (!dataTab1) {
                            return BASE.deactivePage();
                        } else {
                            return BASE.config.BROWSE;
                        }
                    }
                    // Param undefined mode
                    if (param.mode == App.settings.app.m_seiho_bunsho.undef) {
                        if (dataTab1) {
                            // If exists bunsho data
                            param.mode = App.settings.app.m_seiho_bunsho.henshu;
                        } else {
                            // If not exists bunsho data
                            param.mode = App.settings.app.m_seiho_bunsho.shinki;
                        }
                    }
                    // Param new mode
                    if (param.mode == App.settings.app.m_seiho_bunsho.shinki) {
                        if (dataTab1) {
                            return BASE.deactivePage();
                        }
                        return BASE.config.NEW;
                    }
                    // Param edit mode
                    if (param.mode == App.settings.app.m_seiho_bunsho.henshu) {
                        if (!dataTab1) {
                            return BASE.deactivePage();
                        } else {
                            if (dataTab1.cd_create) {
                                return BASE.config.VIEW;
                            }
                            return BASE.config.EDIT;
                        }
                    }
                    // Param apply mode
                    if (param.mode == App.settings.app.m_seiho_bunsho.shinsei) {
                        if (!dataTab1) {
                            return BASE.config.APPLY_NEW;
                        } else {
                            if (dataTab1.cd_create) {
                                return BASE.config.APPLY_VIEW;
                            }
                            return BASE.config.APPLY_EDIT;
                        }
                    }
                    // No mode found
                    return BASE.deactivePage();
                },
                setTabMode: function (pageMode) {
                    var param = page.param,
                        BASE = page.mode,
                        pageMode = pageMode || BASE.getMode(page.values.dataTab1);

                    if (!pageMode) {
                        return;
                    }
                    $.each(page.tabs, function (name, context) {
                        var modeParam = pageMode.getTabParam(name)
                        $.extend(context.param, modeParam);
                    });
                },
                convertModeValue: function (baseMode, addtMode) {
                    if (baseMode) {
                        return baseMode + (addtMode ? ("_" + addtMode) : "");
                    }
                    return "";
                },
                active: function () {
                    var BASE = page.mode,
                        pageMode = BASE.getMode(page.values.dataTab1);

                    if (!pageMode) {
                        return page.risingKengenError();
                    }
                    BASE.setTabMode(pageMode);
                    pageMode.setDisplay();
                    page.values.mode = pageMode;
                },
                deactivePage: function () {
                    page.iskengenError = true;
                    return false;
                },
                resetActiveParam: function () {
                    var newParam = $.extend({}, page.param);
                    newParam.no_Tab = [];
                    newParam.no_seiho_copy = "";
                    newParam.haigo_copy_mapping = []
                    sessionStorage.setItem(window.name, JSON.stringify(newParam));
                }
            },
            display: {}
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
                            row.element.find("tr").removeClass("has-error");
                        }
                    });
                } else {
                    page.setColValidStyle(item.element);
                }

                App.ui.page.notifyAlert.remove(item.element);
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
                            row.element.find("tr").addClass("has-error");
                        }
                    });
                } else {
                    page.setColInvalidStyle(item.element);
                }

                if (state && state.suppressMessage) {
                    continue;
                }
                App.ui.page.notifyAlert.message(item.message, item.element).show();
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

        // Check if back color is ligth or dark to increase the contract display
        page.lightOrDark = function (color) {

            // Variables for red, green, blue values
            var r, g, b, hsp;

            // Check the format of the color, HEX or RGB?
            if (color.match(/^rgb/)) {

                // If HEX --> store the red, green, blue values in separate variables
                color = color.match(/^rgba?\((\d+),\s*(\d+),\s*(\d+)(?:,\s*(\d+(?:\.\d+)?))?\)$/);

                r = color[1];
                g = color[2];
                b = color[3];
            }
            else {

                // If RGB --> Convert it to HEX
                color = +("0x" + color.slice(1).replace(
                color.length < 5 && /./g, '$&$&'));

                r = color >> 16;
                g = color >> 8 & 255;
                b = color & 255;
            }

            // HSP (Highly Sensitive Poo) 
            hsp = Math.sqrt(
            0.299 * (r * r) +
            0.587 * (g * g) +
            0.114 * (b * b)
            );

            // Using the HSP value, determine whether the color is light or dark
            if (hsp > 127.5) {

                return 'light';
            }
            else {

                return 'dark';
            }
        }

        /**
         * Add space to duplicate value name
         */
        page.processDuplicateData = function (value, textProp) {
            if (value) {
                var DuplicateList = [];
                // Defined duplicate
                $.each(value, function (ind, item) {
                    var Duplicate = $.grep(value, function (subItem) {
                        return (subItem[textProp] === item[textProp]);
                    })
                    if (Duplicate.length > 1) {
                        DuplicateList.push(Duplicate);
                    }
                });
                // Defined duplicate
                $.each(value, function (ind, item) {
                    if (!App.isUndefOrNull(item[textProp]))
                    {
                        item[textProp] += " ";
                    }
                });
                // Rename the duplicate
                $.each(DuplicateList, function (ind, item) {
                    $.each(item, function (i, subItem) {
                        subItem[textProp] += "          ".substr(0, i + 1);
                    });
                });
            }
            return value;
        }

        // Append options to special comboboxs
        page.appendOptions = function (target, data, value, display, isDefaultOption, isEmptyPrevOptions) {
            var $control = target.find("datalist"),
                $input = target.find("input"),
                convertedData = [];

            if (isEmptyPrevOptions) {
                $control.children().remove();
            }

            if (isDefaultOption) {
                $control.append("<option value=''></option>");
            }
            
            data = page.processDuplicateData(data, display);

            $.each(data, function (ind, item) {
                $control.append(App.str.format("<option value='{0}' data-value='{1}'></option>", item[display], item[value]));
            });
            //$input.data("datalist", convertedData);
        }


        // Append options to special comboboxs
        page.appendOptions2 = function (target, data, value, display, isDefaultOption, isEmptyPrevOptions) {
            var $control = target.find("datalist"),
                $input = target.find("input"),
                convertedData = [];

            if (isEmptyPrevOptions) {
                $control.children().remove();
            }

            if (isDefaultOption) {
                $control.append("<option value=''></option>");
            }

            $.each(data, function (ind, item) {
                $control.append(App.str.format("<option value='{0}' data-value='{1}'></option>", item[display], item[value]));
            });
            //$input.data("datalist", convertedData);
        }

        page.selectList = function (target, options) {
            if (!target) {
                target = $.find("select.select-list");
            }
            target.each(function (ind, item) {
                item = $(item);
                var dataProp = item.attr("data-prop");
                var dataId = item.attr("id");
                var dataList = $(App.str.format("<div class='datalist-content'><input type='text' list='{0}' data-prop='{1}' style='{2}' class='{3}' /><datalist id='{0}'autocomplete='off'></datalist></div>",
                                                dataId, dataProp, item.attr("style"), item.attr("class")));
                dataList.insertAfter(item);
                item.remove();
                if (options && options.data) {
                    page.appendOptions(dataList, options.data, options.value, options.text, options.isDefaultOption);
                }
            })
        }

        /// Get value code from datalist
        page.getDataFromDatalist = function (target) {
            var result = null;
            if (target && target.val()) {
                var option = target.closest(".datalist-content").find("datalist option");
                option.each(function (ind, item) {
                    if ($(item).attr("value") === target.val()) {
                        result = $(item).attr("data-value");
                        return false;
                    }
                })
            }
            // data return
            return result;
        }

        /// Get value code when change combobox
        page.getValueFromList = function (target) {
            if (target) {
                if (target.is(":focus")) {
                    var result = page.getDataFromDatalist(target);
                    target.val(target.val().replace(/\s+$/, ''));
                    return result;
                }
                return null;
            }
            return null;
        }

        /// Prevent enter key in datalist input
        $(document).on("keydown", "input[list]", function (e) {
            if (e.which == 13) {
                return false;
            }
        });

        /// Show loading with many layers and status (in percent)
        /// This use for heavy loading that required some time to complete
        page.copyLoading = function (lstOption) {
            page.lstOptionLoading = lstOption;
            var findFirstOption = function () {
                // Get the first option
                for (var i = 0; i < lstOption.length; i++) {
                    if (Object.keys(lstOption[i].finishLoading).length < lstOption[i].count)
                        return lstOption[i];
                }
                return null;
            },
                findOption = function (key) {
                    for (var i = 0; i < lstOption.length; i++) {
                        if (lstOption[i].key === key)
                            return lstOption[i];
                    }
                },
                resumeLoading = function () {
                    var options = findFirstOption(), title;
                    if (options) {
                        title = options.title;
                        App.ui.loading.show();
                    } else {
                        App.ui.loading.close();
                    }
                }, percent = 0;

            page.finishLoading = function (key, name, fpercent) {
                var option = findOption(key);
                if (option) {
                    option.finishLoading[name] = name;
                    percent += fpercent;
                    if (percent > 100) {
                        percent = 100;
                    }
                }
                resumeLoading();
            }
            resumeLoading();
        }

        /// Show loading with many layers and status (in percent)
        /// This use for heavy loading that required some time to complete
        page.loading = function (lstOption) {
            page.lstOptionLoading = lstOption;
            var findFirstOption = function () {
                // Get the first option
                for (var i = 0; i < lstOption.length; i++) {
                    if (Object.keys(lstOption[i].finishLoading).length < lstOption[i].count)
                        return lstOption[i];
                }
                return null;
            },
                findOption = function (key) {
                    for (var i = 0; i < lstOption.length; i++) {
                        if (lstOption[i].key === key)
                            return lstOption[i];
                    }
                },
                resumeLoading = function () {
                    var options = findFirstOption(), title;
                    if (options) {
                        title = options.title;
                        //App.ui.loading.show(title + percent + "%");
                        App.ui.loading.show();
                    } else {
                        //App.ui.loading.show("Standing by ..." + "100%");
                        setTimeout(function () {
                            App.ui.loading.close();
                            page.values.isFirstLoading = false;
                            if (page.param.no_Tab && page.param.no_Tab.length) {
                                page.values.isChanged = true;
                            }
                            if (!page.values.haigoHeaderData || !page.values.haigoHeaderData.length) {
                                App.ui.page.notifyInfo.message(App.messages.app.AP0097).show();
                                //page.dialogs.confirmDialog.confirm({
                                //    text: App.messages.app.AP0097
                                //});
                            }
                            page.finishLoading = function () { };
                        }, 1000);
                    }
                }, percent = 0;

            page.finishLoading = function (key, name, fpercent) {
                var option = findOption(key);
                if (option) {
                    option.finishLoading[name] = name;
                    percent += fpercent;
                    if (percent > 100) {
                        percent = 100;
                    }
                }
                resumeLoading();
            }
            resumeLoading();
        }

        page.display.initialize = function () {
            var pageMode = page.mode.getPageMode(),
                modes = page.mode.getAllMode(),
                setActive = function (target, isActive) {
                    target.prop("disabled", !isActive);
                },
                setDisplay = function (target, isDisplay) {
                    if (isDisplay) {
                        target.show();
                    } else {
                        target.hide();
                    }
                };

            switch (pageMode) {
                case modes.new:

                    break;
            }
        };

        /**
        * Set change flg when any elem on change - for process of EXCEL btn
        */
        page.setIsChangeValue = function () {
            if (!page.values.isFirstLoading) {
                page.values.isChanged = true;
            }
        }

        /**
        * Active copy mode, close all relate tabs
        */
        page.closeTabs = function () {
            App.common.closeTab({
                closeTab: page.values.closeTabs
            });
            // Reset close tab
            page.values.closeTabs = [];
        }

        /**
        * Reload copy data when the window is in open
        */
        page.activeCopy = function (no_Tab, haigo_copy_mapping, no_seiho_copy) {
            if (page.values.mode.addt !== page.options.mode.copy) {
                return;
            }
            if (!page.values.isWaitingForCopy300) {
                return;
            }

            if (no_Tab && no_Tab.length) {
                page.copyLoading([{
                    key: "TAB_Bind",
                    count: no_Tab.length,
                    title: "Loading ...",
                    finishLoading: {}
                }]);
            }

            page.param.no_Tab = no_Tab;
            page.param.haigo_copy_mapping = haigo_copy_mapping;
            page.param.no_seiho_copy = no_seiho_copy;

            page.mode.setTabMode(page.values.mode);
            page.values.isWaitingForCopy300 = false;
            page.values.isChanged = true;
            $.each(no_Tab, function (ind, tabName) {
                var tab = page.tabs[tabName];
                if (tab && tab.searchCopy) {
                    if (tabName === App.settings.app.SeihobunshoTabName.SeizoKoteizu) {
                        tab.param.haigoMapping = haigo_copy_mapping;
                    }
                    tab.searchCopy();
                }
            });
        }

        /**
        * rise error and return menu when kengen get error of main data is invalid
        */
        page.risingKengenError = function () {
            // When data get error
            window.location.href = page.urls.mainMenu;
            //var options = {
            //    text: "Some thing is going wrong!",
            //    hideCancel: true
            //};

            //page.dialogs.confirmDialog.confirm(options)
            //.always(function () {
            //    window.location.href = page.urls.mainMenu;
            //});
        }

        /**
        * 画面の初期化処理を行います。
        */
        page.initialize = function () {

            //App.ui.loading.show();
            page.loading([{
                key: "TAB_Initilize",
                count: 10,
                title: "Loading ...",
                finishLoading: {}
            }, {
                key: "TAB_Bind",
                count: 10,
                title: "Loading ...",
                finishLoading: {}
            }]);

            page.initializeControl();
            page.initializeControlEvent();

            //get kengen screen
            // Pass the kengen check
            getKengenGamen(500).then(function (results) {
                //page.kengenGamen = results;
                //if (!results || !results.length) {
                //    page.iskengenError = true;
                //}
                return page.header.initialize();
            }).then(function () {
                page.detail.initialize();

                //TODO: ヘッダー/明細以外の初期化の処理を記述します。

                page.loadMasterData().then(function (result) {

                    return page.loadDialogs();
                }).then(function (result) {
                    if (page.iskengenError) {
                        App.ui.loading.close();
                        page.risingKengenError();
                        return;
                    }
                    //TODO: 画面の初期化処理成功時の処理を記述します。
                    return page.loadTabs().then(function () {
                        //TOsVN - 19075 - 2023/06/20: st ShohinKaihatuSupport Modify 2023
                        App.common.Log.write({
                            cd_game: App.settings.app.log_mode.gamen.SeihoBunsho
                            , cd_taisho_data: page.header.element.findP("no_seiho").text()
                            , mode: {
                                gamen: "seiho"
                                , nm_mode: page.param.mode
                            }
                        });
                        //ed
                    })
                }).fail(function (error) {
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

                }).always(function (result) {

                    page.header.element.find(":input:first").focus();
                });
            });
        };


        /*
        *
        */
        page.loadTabs = function () {
            //TODO: 共有ダイアログデータを取得し、画面にバインドする処理を記述します。
            return App.async.all({
                Hyoshi: $.get(page.tabs.urls.Hyoshi),
                YoukiHousou: $.get(page.tabs.urls.YoukiHousou),
                GenryoSetsubi: $.get(page.tabs.urls.GenryoSetsubi),
                HaigoSeizoChuiJiko: $.get(page.tabs.urls.HaigoSeizoChuiJiko),
                SeizoKoteizu: $.get(page.tabs.urls.SeizoKoteizu),
                SeihinKikakuan: $.get(page.tabs.urls.SeihinKikakuan),
                SeihojyoKakuninJiko: $.get(page.tabs.urls.SeihojyoKakuninJiko),
                ShomikigenSetteiHyo: $.get(page.tabs.urls.ShomikigenSetteiHyo),
                RirekiHyo: $.get(page.tabs.urls.RirekiHyo),
                HaigoHyo: $.get(page.tabs.urls.HaigoHyo)

            }).then(function (result) {

                var tab_container = page.detail.element.find("#tab-container");

                tab_container.append(result.successes.Hyoshi);
                page.tabs.Hyoshi = _300_SeihoBunshoSakusei_Hyoshi_Tab;

                tab_container.append(result.successes.YoukiHousou);
                page.tabs.YoukiHousou = _300_SeihoBunshoSakusei_YoukiHousou_Tab;

                tab_container.append(result.successes.GenryoSetsubi);
                page.tabs.GenryoSetsubi = _300_SeihoBunshoSakusei_GenryoSetsubi_Tab;

                tab_container.append(result.successes.HaigoSeizoChuiJiko);
                page.tabs.HaigoSeizoChuiJiko = _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab;

                tab_container.append(result.successes.SeizoKoteizu);
                page.tabs.SeizoKoteizu = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab;

                tab_container.append(result.successes.SeihinKikakuan);
                page.tabs.SeihinKikakuan = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab;

                tab_container.append(result.successes.SeihojyoKakuninJiko);
                page.tabs.SeihojyoKakuninJiko = _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab;

                tab_container.append(result.successes.ShomikigenSetteiHyo);
                page.tabs.ShomikigenSetteiHyo = _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab;

                tab_container.append(result.successes.RirekiHyo);
                page.tabs.RirekiHyo = _300_SeihoBunshoSakusei_RirekiHyo_Tab;

                tab_container.append(result.successes.HaigoHyo);
                page.tabs.HaigoHyo = _300_SeihoBunshoSakusei_HaigoHyo_Tab;

                page.mode.active();
                page.tabs.Hyoshi.initialize();
                page.tabs.YoukiHousou.initialize();
                page.tabs.GenryoSetsubi.initialize();
                page.tabs.HaigoSeizoChuiJiko.initialize();
                page.tabs.SeizoKoteizu.initialize();
                page.tabs.SeihinKikakuan.initialize();
                page.tabs.SeihojyoKakuninJiko.initialize();
                page.tabs.ShomikigenSetteiHyo.initialize();
                page.tabs.RirekiHyo.initialize();
                page.tabs.HaigoHyo.initialize();

                // ajustment detail height
                $(window).on('resize', function () {
                    var windowHeight = $(window).height();
                    $(".sub-tab-content").css("max-height", windowHeight - 177).css("height", windowHeight - 200);
                    //$("#_300_SeihoBunshoSakusei_GenryoSetsubi_Tab .sub-tab-content, #_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab .sub-tab-content").css("height", windowHeight - 177);
                    //$("#_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab .sub-tab-content").css("height", windowHeight - 177);
                    $(".flow-auto-height").css("height", windowHeight - 320);
                    $(".fix-auto-height").css("height", windowHeight - 338);
                    _300_SeihoBunshoSakusei_Hyoshi_Tab.autoAjustMemoWidth();
                });
                $(window).resize();
                // Set default table height
                setTimeout(function () {
                    _300_SeihoBunshoSakusei_RirekiHyo_Tab.element.find(".dt-body").css("height", 100);
                    _300_SeihoBunshoSakusei_HaigoHyo_Tab.element.find(".dt-body").css("height", 100);
                }, 500);
                // Show tab event
                $('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
                    var target = $(e.target).attr("href");
                    //App.ui.page.notifyAlert.clear();
                    switch (target) {
                        case '#_300_SeihoBunshoSakusei_RirekiHyo_Tab':
                            _300_SeihoBunshoSakusei_RirekiHyo_Tab.dataTable.dataTable("setAditionalOffset", -20);
                            _300_SeihoBunshoSakusei_RirekiHyo_Tab.autoHeightTextArea();
                            break;
                        case '#_300_SeihoBunshoSakusei_HaigoHyo_Tab':
                            _300_SeihoBunshoSakusei_HaigoHyo_Tab.dataTable.dataTable("setAditionalOffset", 10);
                            break;
                            //case '#YoukiHousou2':
                            //    page.tabs.YoukiHousou.show();
                            //    break;
                            //case '#_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab':
                            //    _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.validateAll();
                            //    break;
                            //case "#_300_SeihoBunshoSakusei_SeihinKikakuan_Tab":
                            //    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.validateAll();
                            //    break;
                            //case "#_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab":
                            //    _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.validateAll();
                            //    break;
                    }
                    if (target == '#ShomikigenSetteiHyo') {
                        $(".group-1").hide();
                        $(".group-2").show();
                    }
                    else {
                        $(".group-1").show();
                        $(".group-2").hide();
                    }
                });

                $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
                    var target = $(e.target).attr("href");
                    //App.ui.page.notifyAlert.clear();
                    switch (target) {
                        case '#_300_SeihoBunshoSakusei_RirekiHyo_Tab':
                            _300_SeihoBunshoSakusei_RirekiHyo_Tab.autoHeightTextArea();
                            break;
                        case '#_300_SeihoBunshoSakusei_SeizoKoteizu_Tab':
                            _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.autoAjustContentHeight();
                            break;
                    }
                });

            });

            //TODO: 共有ダイアログのロード処理を実装後、下の1行を削除してください。
            return App.async.success();
        };

        /**
         * 画面コントロールの初期化処理を行います。
         */
        page.initializeControl = function () {

            //TODO: 画面全体で利用するコントロールの初期化処理をここに記述します。
            $(".part").part();
        };


        /**
         * Click button [製法文書ｺﾋﾟｰ(他製法)]
         */
        page.commands.findCopyData = function () {
            //page.dialogs._801_SeihobunshoSakusei_Dialog.param.no_seiho = page.param.no_seiho;
            //page.dialogs._801_SeihobunshoSakusei_Dialog.element.modal("show");
            if (page.values.isChanged) {
                return App.ui.page.notifyInfo.message(App.messages.app.AP0018, "300_AP0018").show();
            }
            page.values.isWaitingForCopy300 = true;
            page.shortcutParam = {
                seihoBunshoMode: page.param.mode
            }
            var url = App.str.format(_801_SeihobunshoSakusei_Dialog.urls.transfer200, App.settings.app.m_seiho_ichiran.copy, page.param.no_seiho, page.param.no_seiho);
            var tab = window.open(url);
            //App.common.addCloseTab({
            //    openWindow: tab
            //});
        }

        /**
         * Click button [製法文書コピー]
         */
        page.commands.openDL802 = function () {
            var paramm = page.param;
            page.dialogs._802_CopyHaniSelection_Dialog.param.no_seiho = paramm.no_seiho;
            page.dialogs._802_CopyHaniSelection_Dialog.param.no_seiho_sakusei = paramm.no_seiho_sakusei;
            page.dialogs._802_CopyHaniSelection_Dialog.param.no_seiho_copy = paramm.no_seiho_copy;
            page.dialogs._802_CopyHaniSelection_Dialog.param.id_gamen = 300;
            page.dialogs._802_CopyHaniSelection_Dialog.element.modal("show");
        }

        /**
         * コントロールへのイベントの紐づけを行います。
         */
        page.initializeControlEvent = function () {
            $("#copy-from").on("click", page.commands.findCopyData);
            $("#copy-to").on("click", page.commands.openDL802);
            //TODO: 画面全体で利用するコントロールのイベントの紐づけ処理をここに記述します。
            $("#excel-download").on("click", page.commands.excelDownload);
        };

        /**
         * Get excel filter
         */
        page.commands.getExcelFilter = function () {
            $.each(_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.propertyMapping, function (ind, item) {
                item.classed = item.class;
            });
            return {
                no_seiho: page.param.no_seiho,
                lst_haigo: page.getHaigoList().join(","),

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
                kbn_hinmei: App.settings.app.cd_category.kbn_hinmei,

                kbn_haigo: App.settings.app.kbnHin.haigo,
                kbn_genryo: App.settings.app.kbnHin.genryo,
                kbn_maeshori: App.settings.app.kbnHin.maeshoriGenryo,
                kbn_shikakari: App.settings.app.kbnHin.shikakari,
                kbn_sagyo: App.settings.app.kbnHin.sagyo,

                kbn_tani_kg: App.settings.app.taniShiyo.kg,
                kbn_tani_l: App.settings.app.taniShiyo.l,
                kbn_gam: App.settings.app.taniShiyo.g,

                kbn_pt_kotei: App.settings.app.pt_kotei.chomieki_2,         //"002"
                kbn_chomi: App.settings.app.zoku_kotei.sakkinchomi_eki,     //"001",
                kbn_suisho: App.settings.app.zoku_kotei.eki_suiso2,         ///"006",
                kbn_yusho: App.settings.app.zoku_kotei.eki_yu_sho2,         //"003"
                cd_tani_g: App.settings.app.cd_tani.gram,                   //"002"

                kbn_shikakari_kaihatsu: App.settings.app.kbn_shikakari.kaihatsu,
                kbn_shikakari_FP: App.settings.app.kbn_shikakari.foodprocs,

                loop_count: App.settings.app.tenkaiLoopCount,

                pt_kotei_chomieki_2: App.settings.app.pt_kotei.chomieki_2,
                pt_kotei_sonohokaeki: App.settings.app.pt_kotei.sonohokaeki,
                flg_mishiyo: "true",

                su_code_standard: page.values.su_code_standard,
                defaultColors: _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.defaultColor,
                shisakuPropMapping: _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.propertyMapping,
                kakuninJikoDefaultValue: _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.defaultValue,

                loginCD: App.ui.page.user.EmployeeCD,
                nm_login: App.ui.page.user.Name,
                nm_busho_login: App.ui.page.user.nm_busho,

                // Delete the exist Excel file when user Un-Approve or check into the skip apply validate
                isDeleteExcel: ((page.values.oldShoninLevel == 3 && _300_SeihoBunshoSakusei_Hyoshi_Tab.getShoninLevel() != 3) || page.isSkipApplyValidation())
            }
        }

        /**
         * Excel出力を行います。(ファイルStream形式でダウンロード)
         */
        page.commands.excelDownload = function (btnClick) {
            var downloadEXC = function (lstTab) {
                var filter = page.commands.getExcelFilter(),
                    url = btnClick ? page.urls.excelDownload : page.urls.staticExcelExport;

                filter.lst_tab = lstTab;
                // ローディング表示
                // TEST
                App.ui.loading.show();
                App.ui.page.notifyAlert.clear();

                if (btnClick) {
                    // CSV出力（Ajax通信でファイルstreamを返却）
                    return $.ajax(App.ajax.file.download(url, filter, "POST")
                    ).then(function (response, status, xhr) {
                        if (status !== "success") {
                            App.ui.page.notifyAlert.message(App.messages.base.MS0005).show();
                        } else {
                            App.file.save(response, App.ajax.file.extractFileNameDownload(xhr) || "ExcelFile.csv");

                            //TOsVN - 19075 - 2023/06/20: st ShohinKaihatuSupport Modify 2023
                            App.common.Log.write({
                                cd_game: App.settings.app.log_mode.gamen.SeihoBunshoExcel
                                , cd_taisho_data: page.header.element.findP("no_seiho").text()
                            });
                            //ed
                        }
                    }).fail(function (error) {
                        App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

                    }).always(function () {
                        App.ui.loading.close();
                    });
                } else {
                    return $.ajax(App.ajax.webapi.post(url, filter)).fail(function (error) {
                        //App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                    })
                }
            };
            if (btnClick) {
                if (page.values.isChanged) {
                    return App.ui.page.notifyInfo.message(App.messages.app.AP0018, "300_AP0018").show();
                }
                page.dialogs._803_ExcelHaniSelection_Dialog.dataSelected = downloadEXC;
                page.dialogs._803_ExcelHaniSelection_Dialog.element.modal("show");
            } else {
                var lstTab = [];
                $.each(App.settings.app.SeihobunshoTabName, function (tabName) {
                    lstTab.push(tabName);
                })
                return downloadEXC(lstTab);
            }
        };


        /**
         * 共有ダイアログのロード処理を実行します。
         */
        page.loadDialogs = function () {

            //TODO: 共有ダイアログデータを取得し、画面にバインドする処理を記述します。
            return App.async.all({

                confirmDialog: $.get(page.urls.confirmDialog),
                _602_IroShitei_Dialog: $.get(page.urls._602_IroShitei_Dialog),
                _801_SeihobunshoSakusei_Dialog: $.get(page.urls._801_SeihobunshoSakusei_Dialog),
                _802_CopyHaniSelection_Dialog: $.get(page.urls._802_CopyHaniSelection_Dialog),
                _803_ExcelHaniSelection_Dialog: $.get(page.urls._803_ExcelHaniSelection_Dialog),
                _804_SeihinSansho_Dialog: $.get(page.urls._804_SeihinSansho_Dialog),
                _805_ShikakarihinSentaku_Dialog: $.get(page.urls._805_ShikakarihinSentaku_Dialog),
                _806_ShisakuretsuSentaku_Dialog: $.get(page.urls._806_ShisakuretsuSentaku_Dialog),
                _800_HinmeiSentaku_Dialog: $.get(page.urls._800_HinmeiSentaku_Dialog)
            }).then(function (result) {

                $("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;
                //page.dialogs.confirmDialog.initialize();


                $("#dialog-container").append(result.successes._602_IroShitei_Dialog);
                page.dialogs._602_IroShitei_Dialog = _602_IroShitei_Dialog;
                page.dialogs._602_IroShitei_Dialog.initialize();
                //page.dialogs._602_IroShitei_Dialog.setReturnColor = 1

                $("#dialog-container").append(result.successes._801_SeihobunshoSakusei_Dialog);
                page.dialogs._801_SeihobunshoSakusei_Dialog = _801_SeihobunshoSakusei_Dialog;
                page.dialogs._801_SeihobunshoSakusei_Dialog.initialize();

                $("#dialog-container").append(result.successes._802_CopyHaniSelection_Dialog);
                page.dialogs._802_CopyHaniSelection_Dialog = _802_CopyHaniSelection_Dialog;
                page.dialogs._802_CopyHaniSelection_Dialog.initialize();

                $("#dialog-container").append(result.successes._803_ExcelHaniSelection_Dialog);
                page.dialogs._803_ExcelHaniSelection_Dialog = _803_ExcelHaniSelection_Dialog;
                page.dialogs._803_ExcelHaniSelection_Dialog.initialize();

                $("#dialog-container").append(result.successes._804_SeihinSansho_Dialog);
                page.dialogs._804_SeihinSansho_Dialog = _804_SeihinSansho_Dialog;
                page.dialogs._804_SeihinSansho_Dialog.initialize();

                $("#dialog-container").append(result.successes._805_ShikakarihinSentaku_Dialog);
                page.dialogs._805_ShikakarihinSentaku_Dialog = _805_ShikakarihinSentaku_Dialog;
                page.dialogs._805_ShikakarihinSentaku_Dialog.initialize();

                $("#dialog-container").append(result.successes._806_ShisakuretsuSentaku_Dialog);
                page.dialogs._806_ShisakuretsuSentaku_Dialog = _806_ShisakuretsuSentaku_Dialog;
                page.dialogs._806_ShisakuretsuSentaku_Dialog.initialize();
                page.dialogs._806_ShisakuretsuSentaku_Dialog.dataSelected = page.header.setShisakuInfo;

                $("#dialog-container").append(result.successes._800_HinmeiSentaku_Dialog);
                page.dialogs._800_HinmeiSentaku_Dialog = _800_HinmeiSentaku_Dialog;
                page.dialogs._800_HinmeiSentaku_Dialog.initialize();

                //検索ダイアログで選択が実行された時に呼び出される関数を設定しています。
                //page.dialogs.searchDialog.dataSelected = page.header.setTorihiki;
            });
        }

        /**
         * Change shisaku info 
         */
        page.header.setShisakuInfo = function (data) {
            if (data && page.values.mode.base != page.options.mode.view) {
                var entity = page.header.data.entry(page.header.element.attr("data-key"));
                if (entity) {
                    // Update entity
                    entity.cd_shain = data.cd_shain;
                    entity.nen = data.nen;
                    entity.no_oi = data.no_oi;
                    entity.seq_shisaku = data.seq_shisaku;
                    entity.nm_sample = data.nm_sample || "";
                    page.header.data.update(entity);
                }
                page.header.element.form(page.header.options.bindOption).bind(entity);
                page.values.isChanged = true;
                // Update TAB 4 [配合・製造上の注意事項] parameters
                page.tabs.updateShisakuParam(page.tabs.HaigoSeizoChuiJiko, data);
                // Update TAB 6 [製品規格案及び取扱基準] parameters
                page.tabs.updateShisakuParam(page.tabs.SeihinKikakuan, data);
                // Update TAB 8 [賞味期間設定表] parameters
                page.tabs.updateShisakuParam(page.tabs.ShomikigenSetteiHyo, data);
                // Change TAB 4 [ドレッシング充填量] data
                page.tabs.HaigoSeizoChuiJiko.changeShisaku();
                // Change TAB 6 [取扱基準] data
                page.tabs.SeihinKikakuan.changeShisaku();
                // Change TAB 8 [賞味期間設定表] data
                page.tabs.ShomikigenSetteiHyo.changeShisaku();
            }
        }

        /**
         * Change shisaku param when change shisaku info
         */
        page.tabs.updateShisakuParam = function (tab, shisakuData) {
            if (tab) {
                tab.param.cd_shain = shisakuData.cd_shain;
                tab.param.nen = shisakuData.nen;
                tab.param.no_oi = shisakuData.no_oi;
                tab.param.seq_shisaku = shisakuData.seq_shisaku;
                tab.param.pt_kotei = shisakuData.pt_kotei;
                tab.param.yoryo = shisakuData.yoryo;
                tab.param.nm_naiyoryo_tani = shisakuData.nm_naiyoryo_tani;
                tab.param.shomikikan = shisakuData.shomikikan;
                if (App.isNumeric(shisakuData.shomikikan)) {
                    tab.param.shomikikan = Number(shisakuData.shomikikan);//App.data.getCommaNumberString(shisakuData.shomikikan);
                }
                tab.param.shomikikan_tani = shisakuData.shomikikan_tani;
                tab.param.cd_ondo = shisakuData.cd_ondo;
                tab.param.cd_naiyoryo_tani = shisakuData.cd_naiyoryo_tani;
            }
        }

        /**
         * Set shonin level param for update ma_seiho
         */
        page.setShoninLevel = function (level) {
            // Set shinsei create
            if (level == "1") {
                page.values.isShinseiCreate = true;
            }
            // Set shonin 1
            if (level == "2") {
                page.values.isShonin1 = true;
            }
            // Set shonin 2
            if (level == "3") {
                page.values.isShonin2 = true;
            }
            // Reset shonin level
            if (level == "0") {
                page.values.isShinseiCreate = false;
                page.values.isShonin1 = false;
                page.values.isShonin2 = false;
                if (page.values.oldShoninLevel != 0) {
                    page.values.isRemoveApply = true;
                }
            }
            page.values.isUpdateShonin = true;
        }

        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.validations = {
            //TODO: 画面ヘッダーのバリデーションの定義を記述します。
        };

        /**
         * 画面ヘッダーの初期化処理を行います。
         */
        page.header.initialize = function () {

            var element = $(".header");
            page.header.validator = element.validation(page.createValidator(page.header.options.validations));
            page.header.element = element;

            //TODO: 画面ヘッダーの初期化処理をここに記述します。
            //TODO: 画面ヘッダーで利用するコントロールのイベントの紐づけ処理をここに記述します。
            element.on("click", "#search", page.header.search);
            element.on("change", ":input", page.header.change);
            element.on("click", "#open-shisaku-sentaku", page.dialogs.openShisakuSentaku)

            return page.header.search();
        };

        /**
         * Open dialog [806_試作列選択]
         */
        page.dialogs.openShisakuSentaku = function () {
            page.dialogs._806_ShisakuretsuSentaku_Dialog.element.modal("show");
        }

        page.header.bind = function (data) {

            var data = data || {};
            page.values.su_code_standard = data.su_code_standard;
            page.values.pt_kotei = data.pt_kotei;
            page.values.cd_naiyoryo_tani = data.cd_naiyoryo_tani;
            page.values.yoryo = data.yoryo;
            page.values.dataTab1 = data.dataTab1;
            page.values.seihoData = data.seihoData;
            page.values.haigoHeaderData = data.haigoHeaderData;
            page.values.seihoDensoData = data.seihoDensoData;

            var displayData = data.seihoData;
            page.header.data = App.ui.page.dataSet();
            page.header.data.attach(displayData);
            page.header.data.update(displayData);

            displayData.nm_hin_syurui = data.nm_hin_syurui;

            page.header.element.form(page.header.options.bindOption).bind(displayData);
            var inCount = 0;
            // Waiting for loadtabs is completed
            //var inter = setInterval(function () {
            //    inCount++;
            //    if (inCount > 100) {
            //        clearInterval(inter);
            //    }
            //    if (page.tabs.HaigoSeizoChuiJiko && page.tabs.SeihinKikakuan)
            //    {
            //        // Update TAB 4 [配合・製造上の注意事項] parameters
            //        page.tabs.updateShisakuParam(page.tabs.HaigoSeizoChuiJiko, displayData);
            //        // Update TAB 6 [製品規格案及び取扱基準] parameters
            //        page.tabs.updateShisakuParam(page.tabs.SeihinKikakuan, displayData);
            //        clearInterval(inter);
            //    }
            //}, 100);
        }

        /**
         * 画面ヘッダーの変更時処理を定義します。
         */
        page.header.change = function () {
            if ($("#nextsearch").hasClass("show-search")) {
                $("#nextsearch").removeClass("show-search").hide();
                App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
            }
            else if (page.detail.searchData) {
                // 保持検索データの消去
                page.detail.searchData = undefined;
                App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
            }
        };

        /**
         * 検索処理を定義します。
         */
        page.header.search = function () {
            var query;
            page.options.filter = page.header.createFilter();

            App.ui.loading.show();
            App.ui.page.notifyAlert.clear();

            return $.ajax(App.ajax.webapi.get(page.urls.search, page.options.filter))
            .done(function (result) {
                if (!page.iskengenError) {
                    // データバインド
                    page.header.bind(result);
                }
                /// Get page mode
                page.values.mode = page.mode.getMode(result.dataTab1);
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

            }).always(function () {
                //App.ui.loading.close();

            });
        };

        /**
         * 検索条件のフィルターを定義します。
         */
        page.header.createFilter = function () {
            var param = page.param;
            return {
                no_seiho: param.no_seiho,
                kbn_haigo: App.settings.app.kbnHin.haigo
            }
        };


        /**
         * 画面明細の初期化処理を行います。
         */
        page.detail.initialize = function () {

            var element = $(".detail");

            page.detail.element = element;
        };

        /**
         * 画面明細の各行にデータを設定する際のオプションを定義します。
         */
        page.header.options.bindOption = {
            appliers: {
                dt_seiho_sakusei: function (value, element) {
                    element.text(App.data.getDateString(new Date(value)));
                    return true;
                },
                cd_shain: function (value, element) {
                    element.val(App.common.getFullCdUser(value));
                    return true;
                },
                nen: function (value, element) {
                    element.val(App.common.getFullSeihoNen(value));
                    return true;
                },
                no_oi: function (value, element) {
                    element.val(App.common.getFullShisaOiban(value));
                    return true;
                }
            }
        };

        /**
         * Get shinsei status of haigo in haigo header data
         */
        page.getShinseiStatus = function () {
            var lstHaigo = page.values.haigoHeaderData;
            if (!lstHaigo || lstHaigo.length == 0) {
                return -1;
            }
            var maxStatus = -1;
            $.each(lstHaigo, function (ind, haigo) {
                if (maxStatus < haigo.status) {
                    maxStatus = haigo.status;
                }
            })
            return maxStatus;
        }

        /**
         * Get shinsei status of haigo in haigo header data
         */
        page.getHaigoList = function () {
            var lstHaigo = page.values.haigoHeaderData;
            if (!lstHaigo || lstHaigo.length == 0) {
                return [];
            }
            var lstCDHaigo = [];
            $.each(lstHaigo, function (ind, haigo) {
                lstCDHaigo.push(haigo.cd_haigo);
            })
            return lstCDHaigo;
        }

        /**
         * すべてのバリデーションを実行します。
         */
        page.detail.validateAll = function () {
            var filter = page.getSaveValidationFilter();
            var isRequired = !page.isSkipApplyValidation();
            var defer = $.Deferred();
            var isInvalidationFound = false;
            //return App.async.all({
            //    TAB1: _300_SeihoBunshoSakusei_Hyoshi_Tab.validateAll(filter),
            //    TAB2: _300_SeihoBunshoSakusei_YoukiHousou_Tab.validateAll(filter),
            //    TAB3: _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.validateAll(filter),
            //    TAB4: _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.validateAll(filter),
            //    TAB5: _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.validateAll(filter),
            //    TAB6: _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.validateAll(filter),
            //    TAB7: _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.validateAll(filter),
            //    TAB8: _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.validateAll(filter),
            //    TAB9: _300_SeihoBunshoSakusei_RirekiHyo_Tab.validateAll(filter),
            //    TAB0: (page.values.mode.validate || App.async.success)()
            //}).fail(function (result) { });
            (page.values.mode.validate || App.async.success)().fail(function () {
                isInvalidationFound = true;
            }).always(function () {
                return _300_SeihoBunshoSakusei_Hyoshi_Tab.validateAll(filter).fail(function () {
                    isInvalidationFound = true;
                }).always(function () {
                    return _300_SeihoBunshoSakusei_YoukiHousou_Tab.validateAll(filter).fail(function () {
                        isInvalidationFound = true;
                    }).always(function () {
                        return _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.validateAll(filter).fail(function () {
                            isInvalidationFound = true;
                        }).always(function () {
                            return _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.validateAll(filter).fail(function () {
                                isInvalidationFound = true;
                            }).always(function () {
                                return _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.validateAll(filter).fail(function () {
                                    isInvalidationFound = true;
                                }).always(function () {
                                    return _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.validateAll(filter).fail(function () {
                                        isInvalidationFound = true;
                                    }).always(function () {
                                        return _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.validateAll(filter).fail(function () {
                                            isInvalidationFound = true;
                                        }).always(function () {
                                            return _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.validateAll(filter).fail(function () {
                                                isInvalidationFound = true;
                                            }).always(function () {
                                                return _300_SeihoBunshoSakusei_RirekiHyo_Tab.validateAll(filter).fail(function () {
                                                    isInvalidationFound = true;
                                                }).always(function () {
                                                    if (isInvalidationFound) {
                                                        defer.reject();
                                                    } else {
                                                        defer.resolve();
                                                    }
                                                });
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            return defer.promise();
        }

        page.isSkipApplyValidation = function () {
            return $.findP("apply-validate-mode").is(":checked");
        }

        /// get validation filter when validate all before save
        page.getSaveValidationFilter = function () {
            /// Not apply mode: view mode or edit mode
            if (page.values.mode.base == page.options.mode.view) {
                return page.options.viewValidationFilter;
            }
            if (!page.values.applyMode || page.isSkipApplyValidation()) {
                return page.options.beforeApplyValidationFilter;
            }
            /// Apply mode: validation base on seiho shubetsu [製法分類]
            var seiho_shubetsu = page.getSeihoShubetsu();
            switch (seiho_shubetsu) {
                case App.settings.app.seiho_shubetsu.hon_seiho:
                    return page.options.honSeihoValidationFilter;
                case App.settings.app.seiho_shubetsu.hyoji:
                    return page.options.hyojiSeihoValidationFilter;
                case App.settings.app.seiho_shubetsu.shiken:
                    return page.options.shikenSeihoValidationFilter;
                case App.settings.app.seiho_shubetsu.tanki:
                    return page.options.tankiSeihoValidationFilter;
                default:
                    return page.options.beforeApplyValidationFilter;
            }
        }

        /// Get no_seiho - shubetsu
        page.getSeihoShubetsu = function () {
            var no_seiho = page.param.no_seiho;
            if (no_seiho && no_seiho.length) {
                return no_seiho.substr(6, 2);
            }
            return "";
        };

        /// When click 作成担当 - 承認
        page.activeApplyMode = function () {

            if (page.values.mode.base == page.options.mode.view) {
                return;
            }
            page.values.applyMode = true;
        }

        page.deactiveApplyMode = function () {
            page.values.applyMode = false;
        }

        page.loadMasterData = function () {
            return App.async.success();
        }

        /**
         * Saving data for all 10 tabs
         */
        page.detail.save = function () {
            // Active loading status

            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            App.ui.loading.show();
            // Check all validations
            page.detail.validateAll().then(function () {
                App.ui.loading.close();
                return page.dialogs.confirmDialog.confirm({
                    text: App.messages.app.AP0004
                });
            }).then(function () {
                App.ui.loading.show();
                // Gathering data before save
                data = {
                    Hyoshi_1: _300_SeihoBunshoSakusei_Hyoshi_Tab.getSaveData(),
                    YoukiHousou_2: _300_SeihoBunshoSakusei_YoukiHousou_Tab.getSaveData(),
                    Zairyo_3: _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.getSaveData(),
                    HaigoSeizoChuiJiko_4: _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab.data.getChangeSet(),
                    SeizoKoteizu_5: _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getIndex(),
                    SeihiKikakuan_6: _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.getSaveData(),
                    SeihojyoKakuninjiko_7: _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.getSaveData(),
                    ShomikigenSetteiHyo_8: _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.getSaveData(),
                    RirekiHyo_9: _300_SeihoBunshoSakusei_RirekiHyo_Tab.getSaveData(),
                    haigoHeaderData: page.values.haigoHeaderData,
                    seihoDensoData: page.values.seihoDensoData,
                    SeihoData: page.header.data.getChangeSet(),
                    isUpdateShonin: page.values.isUpdateShonin,
                    isRemoveApply: page.values.isRemoveApply,
                    isShinseiCreate: page.values.isShinseiCreate,
                    isShonin1: page.values.isShonin1,
                    isShonin2: page.values.isShonin2,
                    oldShoninLevel: page.values.oldShoninLevel,
                    no_seiho: page.param.no_seiho,
                }
                // Send data
                $.ajax(App.ajax.webapi.post(page.urls.save, data)).then(function () {
                    // Disabled all command buttons
                    $(".footer-content button").prop("disabled", true);
                    // Reset the changed param
                    page.values.isChanged = false;
                    // Check if remove shonin
                    if (page.values.isUpdateShonin && (page.values.isShonin2 || page.values.oldShoninLevel == 3)) {
                        return page.commands.excelDownload();
                    }
                }).then(function () {
                    // Show success message
                    App.ui.page.notifyInfo.message(App.messages.base.MS0002, "300_MS0002").show();
                    // Change current mode and reload the page
                    if (page.values.mode) {
                        // Reset page param base on current mode
                        if (page.values.mode.changeModeAfterSave) {
                            page.values.mode.changeModeAfterSave();
                        }
                        // Reset all copy param
                        page.mode.resetActiveParam()
                    }
                    // reload page after 2s
                    setTimeout(function () {
                        App.ui.page.onclose = false;
                        location.reload();
                    }, 2000);
                }).fail(function (error) {
                    // Show error message
                    if (error.status === App.settings.base.conflictStatus) {
                        App.ui.page.notifyAlert.clear();
                        App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
                        return;
                    }
                    // Unexpected error
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                }).always(function () {
                    App.ui.loading.close();
                });
            }).fail(function (resultSet) {
                App.ui.loading.close();
            });
        }
        // Release all validations in view mode
        page.options.viewValidationFilter = function (item, method, state, options) {
            return item === "no_seiho_rireki_naiyo" || item === "shonin_memo";
        };
        // All free input
        page.options.changeValidationFilter = function (item, method, state, options) {
            return (method !== "required" && method !== "required_custom");
        };
        // All free input
        page.options.beforeApplyValidationFilter = function (item, method, state, options) {
            return (method !== "required" && method !== "required_custom");
        };
        // 01 本製法
        page.options.honSeihoValidationFilter = function (item, method, state, options) {
            return (method !== "required" && method !== "required_custom") ||
                (
                    item != "cd_hin"                            // TAB 1 製品コード
                    && item != "nm_seihin"                      // TAB 1 製品名
                );
        };
        // 19 表示用製法
        page.options.hyojiSeihoValidationFilter = function (item, method, state, options) {
            return (method !== "required" && method !== "required_custom") ||
                (
                       item != "cd_yoki_hoso"                   // TAB 2 容器包装
                    && item != "nm_yoki_hoso"                   // TAB 2 容器包装
                    && item != "nm_genryo_free_komoku"          // TAB 3 原料
                    && item != "nm_kikai_setsubi_free_komoku"    // TAB 3 機械設備
                    && item != "nm_seizo_hoho_free_komoku"      // TAB 3 製造方法
                    && item != "nm_hyoji_jiko_free_komoku"      // TAB 3 表示事項
                    && item != "nm_haigo_free_tokki_jiko"       // TAB 4 配合　（フリー入力）
                    && item != "nm_chuiten_free_komoku"         // TAB 4 製造上の注意事項　（フリー入力）
                    && item != "nm_genryo_validate"             // TAB 5 製造工程図
                    && item != "nm_free_tokuseichi"             // TAB 6 特性値
                    && item != "nm_free_biseibutsu"             // TAB 6 微生物
                    && item != "nm_free_kanren_tokkyo"          // TAB 7 関連特許
                    && item != "nm_free_tasha_kanren_tokkyo"    // TAB 7 他社関連特許
                    && item != "nm_eigyo_kyoka_gyoshu01"        // TAB 7 営業許可業種１
                    && item != "nm_shokuhin_tenkabutsu"         // TAB 7 使用基準のある食品添加物の使用
                    && item != "nm_free_genryo_shitei"          // TAB 7 原料の指定
                    && item != "nm_free_genryo_seigen"          // TAB 7 原料の制限
                    && item != "nm_kaihatsubusho"               // TAB 8 得意先名
                    && item != "nm_kaihatsutanto"               // TAB 8 ブランド
                    && item != "no_anzen_keisu"                 // TAB 8 安全係数
                    && item != "cd_nengetsu_hyoji"                 // TAB 8 年月表示対応
                    //&& item != "nm_shomikigen_biko"             // TAB 8 賞味期間
                );
        };
        // 20 試験製法
        page.options.shikenSeihoValidationFilter = function (item, method, state, options) {
            return (method !== "required" && method !== "required_custom") ||
                (
                       item != "cd_hin"                         // TAB 1 製品コード
                    && item != "nm_seihin"                      // TAB 1 製品名
                    && item != "cd_yoki_hoso"                   // TAB 2 容器包装
                    && item != "nm_yoki_hoso"                   // TAB 2 容器包装
                    && item.indexOf("nm_yoki_hoso_shizai") < 0  // TAB 2 資材
                    && item != "nm_genryo_free_komoku"          // TAB 3 原料
                    && item != "nm_kikai_setsubi_free_komoku"   // TAB 3 機械設備
                    && item != "nm_seizo_hoho_free_komoku"      // TAB 3 製造方法
                    && item != "nm_hyoji_jiko_free_komoku"      // TAB 3 表示事項
                    && item != "nm_free_kanren_tokkyo"          // TAB 7 関連特許
                    && item != "nm_free_tasha_kanren_tokkyo"    // TAB 7 他社関連特許
                    && item != "nm_eigyo_kyoka_gyoshu01"        // TAB 7 営業許可業種１
                    && item != "nm_shokuhin_tenkabutsu"         // TAB 7 使用基準のある食品添加物の使用
                    && item != "nm_free_genryo_shitei"          // TAB 7 原料の指定
                    && item != "nm_free_genryo_seigen"          // TAB 7 原料の制限
                    && item != "nm_kaihatsubusho"               // TAB 8 得意先名
                    && item != "nm_kaihatsutanto"               // TAB 8 ブランド
                    && item != "no_anzen_keisu"                 // TAB 8 安全係数
                    && item != "cd_nengetsu_hyoji"                 // TAB 8 年月表示対応
                    //&& item != "nm_shomikigen_biko"             // TAB 8 賞味期間
                );
        };
        // 21 短期製法
        page.options.tankiSeihoValidationFilter = function (item, method, state, options) {
            return (method !== "required" && method !== "required_custom") ||
                (
                       item != "cd_hin"                         // TAB 1 製品コード
                    && item != "nm_seihin"                      // TAB 1 製品名
                    && item != "cd_yoki_hoso"                   // TAB 2 容器包装
                    && item != "nm_yoki_hoso"                   // TAB 2 容器包装
                    && item.indexOf("nm_yoki_hoso_shizai") < 0  // TAB 2 資材
                    && item != "nm_kikai_setsubi_free_komoku"    // TAB 3 機械設備
                );
        };

        /**
         * Windows閉じる際のイベントを定義します。
         * @return 文字列を返却した場合に確認メッセージが表示されます。
         */
        App.ui.page.onclose = function () {

            return page.values.isChanged;
        };

        /**
         * jQuery イベントで、ページの読み込み処理を定義します。
         */
        $(function () {
            var param = sessionStorage[window.name];
            if (!param) {
                page.mode.deactivePage();
            }
            param = JSON.parse(param)
            if (!param.no_seiho) {
                page.mode.deactivePage();
            }
            $.extend(page.param, param);
            // ページの初期化処理を呼び出します。
            page.initialize();
        });

        $(document).on("keydown", "[data-tab-direct]", function (e) {
            var code = e.keyCode || e.which;

            if (code === 9) {
                var target = $(e.target),
                    nextIndex = target.attr("data-tab-direct");
                if (nextIndex) {
                    var nextTarget = target.closest(".tab-pane").findP(nextIndex);
                    while (nextTarget.length && (nextTarget.is(":disabled") || !nextTarget.is(":visible"))) {
                        var passNextIndex = nextTarget.attr("data-tab-direct");
                        if (!passNextIndex) {
                            return;
                        }
                        nextTarget = target.closest(".tab-pane").findP(passNextIndex);
                    }
                    if (nextTarget.length) {
                        nextTarget.focus();
                        e.preventDefault();
                    }
                }
            }
        });

    </script>

</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="content-wrap">
        <!--TODO: ヘッダーを定義するHTMLをここに記述します。-->
        <div class="header smaller">
            <%--<div title="TODO: ヘッダー部のタイトルを設定します。" class="part">--%>
            <div class="row" style="padding-right: 7px">
                <div class="control-label col-xs-1">
                    <label>製法番号</label>
                </div>
                <div class="control col-xs-2">
                    <label data-prop="no_seiho"></label>
                </div>
                <div class="control-label col-xs-2 fix-label-md">
                    <label class="lb float-left">製品種類</label>
                    <label class="prop overflow-ellipsis" data-prop="nm_hin_syurui"></label>
                </div>
                <div class="control-label col-xs-5 fix-label-md">
                    <div class="lb col-zr float-left">
                        <label class="lb">製法名</label>
                    </div>
                    <div class="prop col-zr float-left">
                        <label data-prop="nm_seiho" class="overflow-ellipsis"></label>
                    </div>
                </div>
                <div class="control-label col-xs-2 fix-label-md">
                    <label class="lb">製法作成日</label>
                    <label class="prop" data-prop="dt_seiho_sakusei"></label>
                </div>
            </div>
            <div class="row" style="padding-right: 7px">
                <div class="col-xs-1 control-label">
                    <label>試作No</label>
                </div>
                <div class="col-xs-11 control">
                    <input type="tel" style="width: 100px;" disabled="disabled" class="seiho right" data-prop="cd_shain" />
                    <span style="width: 30px;">－</span>
                    <input type="tel" style="width: 30px;" disabled="disabled" class="seiho right" data-prop="nen" />
                    <span style="width: 30px;">－</span>
                    <input type="tel" style="width: 40px;" disabled="disabled" class="seiho right" data-prop="no_oi" />
                    <span style="width: 30px;">（</span>
                    <input type="tel" style="width: 120px;" disabled="disabled" class="seiho" data-prop="nm_sample" />
                    <span style="width: 30px;">）</span>
                    <button type="button" class="btn btn-xs btn-primary" id="open-shisaku-sentaku" style="display: none">連携選択</button>
                </div>
            </div>
        </div>
        <div class="detail" style="padding-top: 2px">

            <div class="row">
                <div class="col-xs-12" style="padding-left: 0px; height: 23px;">
                    <div class="tabbable">
                        <ul class="nav nav-tabs" style="height: 15px; border-bottom: #ffffff">
                            <li class="styleLi active">
                                <a data-toggle="tab" tabindex="-1" id="TAB1" class="tab-a" href="#_300_SeihoBunshoSakusei_Hyoshi_Tab">表紙</a></li>
                            <li class="styleLi"><a data-toggle="tab" tabindex="-1" id="TAB2" class="tab-a" href="#_300_SeihoBunshoSakusei_YoukiHousou_Tab">容器包装</a></li>
                            <%--1. 容器包装--%>
                            <li class="styleLi"><a data-toggle="tab" tabindex="-1" id="TAB3" class="tab-a" href="#_300_SeihoBunshoSakusei_GenryoSetsubi_Tab">原料・機械設備・製造方法・表示事項</a></li>
                            <li class="styleLi"><a data-toggle="tab" tabindex="-1" id="TAB4" class="tab-a" href="#_300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_Tab">配合・製造上の注意事項</a></li>
                            <li class="styleLi"><a data-toggle="tab" tabindex="-1" id="TAB5" class="tab-a" href="#_300_SeihoBunshoSakusei_SeizoKoteizu_Tab">製造工程図</a></li>
                            <li class="styleLi"><a data-toggle="tab" tabindex="-1" id="TAB6" class="tab-a" href="#_300_SeihoBunshoSakusei_SeihinKikakuan_Tab">製品規格案及び取扱基準</a></li>
                            <li class="styleLi"><a data-toggle="tab" tabindex="-1" id="TAB7" class="tab-a" href="#_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab">製法上の確認事項</a></li>
                            <li class="styleLi"><a data-toggle="tab" tabindex="-1" id="TAB8" class="tab-a" href="#_300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab">賞味期間設定表</a></li>
                            <li class="styleLi"><a data-toggle="tab" tabindex="-1" id="TAB9" class="tab-a" href="#_300_SeihoBunshoSakusei_RirekiHyo_Tab">商品開発履歴表</a></li>
                            <li class="styleLi"><a data-toggle="tab" tabindex="-1" id="TAB10" class="tab-a" href="#_300_SeihoBunshoSakusei_HaigoHyo_Tab">配合表</a></li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="tab-content seiho-bunsyo" id="tab-container">
                <%--TAB1--%>

                <%--TAB2--%>

                <%--TAB3--%>

                <%--TAB4--%>
                <%--<div class="tab-pane" id="KihonSekkeiTab4" style="margin-top:3px;">
                <h1>Tab4</h1>
            </div>--%>

                <%--TAB5--%>
                <%--<div class="tab-pane" id="tab5" style="margin-top:3px;">
                <h1>Tab5</h1>
            </div>--%>

                <%--TAB6--%>
            </div>

        </div>
        <!--TODO: 明細をpartにする場合は以下を利用します。
        </div>
        -->
    </div>
</asp:Content>

<asp:Content ID="FooterContent" ContentPlaceHolderID="FooterContent" runat="server">
    <div class="command-left">
        <button type="button" id="excel-download" class="btn btn-sm btn-primary" style="display: none">EXCEL</button>
    </div>
    <div class="command">
        <!--TODO: コマンドボタン（右寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" class="btn btn-sm btn-primary" id="copy-from" style="display: none">製法文書ｺﾋﾟｰ(他製法)</button>
        <button type="button" class="btn btn-sm btn-primary" id="copy-to" style="display: none">製法文書コピー</button>
        <button type="button" id="save" class="btn btn-sm btn-primary" onclick="page.detail.save();" style="display: none">保存</button>
    </div>

</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>
<%--Halo--%>
