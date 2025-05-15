<%@ Page Title="400_設計品質ゲート" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="400_SekkeiHinshutsuGeto.aspx.cs" Inherits="Tos.Web.Pages.SekkeiHinshutsuGeto" %>
<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【HeaderDetail(Ver2.1)】 Template--%>

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
        .styleLi {
            padding-top: 0px;
        }
        .styleLi > a {
            background-color: #f6f6f6;
            border: 1px solid #dddddd !important;
        }
        .btn-header{
            height:26px;
            padding-top:2px;
        }
        textarea { resize: none; }
        .height-row-detail {
            height:107px!important;
        }
        .txtarea-detail {
            height:107px;
            padding-top:1px!important;
            padding-bottom:0px!important;
            padding-left:0px!important;
            padding-right:0px!important;
        }
        .layouttxtarea-detail {
            border:none;
            margin-top:-0px!important;
            margin-bottom:-4px!important;
            padding-bottom:0px;
            height:100%;
        }
        .shiryo {
            background-color:#ebebe4;
            border-color:#ebebe4;
        }
        .shiryo:hover {
            background-color: #ebebe4;
            border-color: #ebebe4;
        }
        .shiryo:focus {
            background-color: #ebebe4;
            border-color: #ebebe4;
        }
        .temp {
            background-color:#e4f2da;
            border-color:#e4f2da;
        }
        .temp:hover {
            background-color:#e4f2da;
            border-color:#e4f2da;
        }
        .temp:focus {
            background-color:#e4f2da;
            border-color:#e4f2da;
        }
        .btn-attachment {
            /*background-color:#76933C;
            border-color:#76933C;*/
            background-color:#66d615;
            border-color:#66d615;
        }
        .btn-attachment:hover {
            /*background-color: #76933C;
            border-color: #76933C;*/
            background-color: #66d615;
            border-color: #66d615;
        }
        .btn-attachment:focus {
            /*background-color: #76933C;
            border-color: #76933C;*/
            background-color: #66d615;
            border-color: #66d615;
        }
        textarea[disabled] {
            background-color: #efefef;
        }
        .col-xs-2-cus {
            width:13%!important;
        }
        .col-xs-1-cus {
            width:12%!important;
        }
        .tab-content > .tab-pane {
            display: block;
        }
        .datatable textarea.selected-row {
            background-color: #fffed4;
        }
        .datatable textarea[disabled].selected-row {
            background-color: #efefef;
        }
        input[type='text'],
        input[type='tel'],
        textarea,
        select {
            padding: 0px 3px!important;
            margin: 0px;
        }
        table.datatable .has-error {
            background-color: #ffdab9!important;
        }
        .header-textarea {
            padding-left:0px!important;
            padding-top:0px!important;
        }
        .closebtn {
            background-color:#008000;
            border-color:#008000;
        }
        .closebtn:focus{
            background-color:#008000;
            border-color:#008000;
        }
        .closebtn:hover{
            background-color:#008000;
            border-color:#008000;
        }
        .navbar {
            display:none;
        }
    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("SekkeiHinshutsuGeto", {
            options: {},
            values: {
                isChangeRunning: {},
                dataColor: {
                    lstColor: ["#000000", "#808080", "#c0c0c0", "#ffffff"
                        , "#800000", "#ff0000", "#800080", "#ff00ff"
                        , "#008000", "#00ff00", "#808000", "#ffff00"
                        , "#000080", "#0000ff", "#008080", "#00ffff"
                    ],
                    col: 4,
                    row: 4
                },
                flgUpdateDataNewest: false,
                flg_shonin_reader_confirm: false,
                isClose: false
            },
            urls: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
                iroShiteiDialog: "Dialogs/602_IroShitei_Dialog.aspx",
                getShisaku: "../Services/ShisaQuickService.svc/tr_shisaku?$filter=cd_shain eq {0}M and nen eq {1}M and no_oi eq {2}M &$orderby=seq_shisaku",
                getBunrui: "../api/SekkeiHinshutsuGeto/GetGateBunrui",
                getGateHeader: "../Services/ShisaQuickService.svc/tr_gate_header?$filter=cd_shain eq {0}M and nen eq {1}M and no_oi eq {2}M",
                search: "../api/SekkeiHinshutsuGeto",
                save: "../api/SekkeiHinshutsuGeto",
                deleteAttachment: "../api/SekkeiHinshutsuGeto/deleteAttachment",
                checkHaita: "../api/SekkeiHinshutsuGeto/checkHaita",
                excel: "../api/SekkeiHinshutsuGetoExcel",
                getShisakuhin: "../Services/ShisaQuickService.svc/tr_shisakuhin?$filter=cd_shain eq {0}M and nen eq {1}M and no_oi eq {2}M",
            },
            header: {
                options: {},
                values: {},
                urls: {}
            },
            detail: {
                options: {},
                values: {
                    tab: "all_tab",
                    noGate: 0,
                    isChange: [],
                    maxNoMeisai:[]
                },
                dataTable: [],
                data:[],
                fixedColumnIndex: [],
                selectedRow:[]
            },
            dialogs: {},
            commands: {}
        });

        /**
         * 単項目要素をエラーのスタイルに設定します。
         * @param target 設定する要素
         */
        page.setColInvalidStyle = function (target, special) {
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
            if (special) {
                $(target).addClass("control-required");
            } else {
                $target.addClass("control-required");
                $target.removeClass("control-success");
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
        page.setColValidStyle = function (target, special) {
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
            if (special) {
                $(target).removeClass("control-required");
            } else {
                $target.removeClass("control-required");
                $target.addClass("control-success");
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
         * バリデーション成功時の処理を実行します。
         */
        page.validationSuccess = function (results, state) {
            var i = 0, l = results.length,
                item, $target;

            for (; i < l; i++) {
                item = results[i];
                if (state && state.isGridValidation) {
                    //page.detail.dataTable[state.cls].dataTable("getRow", $(item.element), function (row) {
                    //    if (row && row.element) {
                    //        row.element.find("tr").removeClass("has-error");
                    //    }
                    //});
                    $(item.element).removeClass("has-error");
                } else {
                    if ($(item.element).attr("data-prop") == "nm_comment_tanto" || $(item.element).attr("data-prop") == "nm_comment_reader") {
                        page.setColValidStyle(item.element, true);
                    }
                    else {
                        page.setColValidStyle(item.element);
                    }
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
                    //page.detail.dataTable[state.cls].dataTable("getRow", $(item.element), function (row) {
                    //    if (row && row.element) {
                    //        row.element.find("tr").addClass("has-error");
                    //    }
                    //});
                    $(item.element).addClass("has-error");
                } else {
                    if ($(item.element).attr("data-prop") == "nm_comment_tanto" || $(item.element).attr("data-prop") == "nm_comment_reader") {
                        page.setColInvalidStyle(item.element, true);
                    }
                    else {
                        page.setColInvalidStyle(item.element);
                    }
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

        /**
         * データの保存処理を実行します。
         */
        page.commands.save = function () {

            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            App.ui.loading.show();

            var sleep = 0;
            var condition = "Object.keys(page.values.isChangeRunning).length == 0";
            App.ui.wait(sleep, condition, 100)
            .then(function () {
                page.validateAll()
                .then(function () {
                    var element = page.detail.element
                        , lstGate = [];
                    if (page.values.flgUpdateDataNewest || !page.values.flg_shonin_reader_confirm) {
                        if (!App.isUndefOrNullOrStrEmpty(element.findP("gateAll").val())) {
                            lstGate.push({ cd_shain: page.values.cd_shain, nen: page.values.nen, no_oi: page.values.no_oi, no_gate: 1, no_bunrui: element.findP("gateAll").val() });
                        }
                        if (!App.isUndefOrNullOrStrEmpty(element.findP("gateBacterialcontrol").val())) {
                            lstGate.push({ cd_shain: page.values.cd_shain, nen: page.values.nen, no_oi: page.values.no_oi, no_gate: 2, no_bunrui: element.findP("gateBacterialcontrol").val() });
                        }
                        if (!App.isUndefOrNullOrStrEmpty(element.findP("gatePriorconfirmation").val())) {
                            lstGate.push({ cd_shain: page.values.cd_shain, nen: page.values.nen, no_oi: page.values.no_oi, no_gate: 3, no_bunrui: element.findP("gatePriorconfirmation").val() });
                        }
                        if (!App.isUndefOrNullOrStrEmpty(element.findP("gateValidity").val())) {
                            lstGate.push({ cd_shain: page.values.cd_shain, nen: page.values.nen, no_oi: page.values.no_oi, no_gate: 4, no_bunrui: element.findP("gateValidity").val() });
                        }
                    }
                    var changeSets = {
                        Header: page.header.data.getChangeSet(),
                        DetailAllTab: page.detail.data.all_tab.getChangeSet(),
                        DetailBacterialcontrolTab: page.detail.data.bacterialcontrol_tab.getChangeSet(),
                        DetailPriorconfirmationTab: page.detail.data.priorconfirmation_tab.getChangeSet(),
                        DetailValidityTab: page.detail.data.validity_tab.getChangeSet(),
                        LstGate: lstGate
                    };
                    var options = {
                        text: App.messages.app.AP0004,
                        multiModal: true
                    };
                    page.dialogs.confirmDialog.confirm(options).then(function () {
                        App.ui.loading.show();
                        return $.ajax(App.ajax.webapi.post(page.urls.save, changeSets))
                        .then(function (result) {
                            //最後に再度データを取得しなおします。
                            page.values.flgUpdateDataNewest = false;
                            return page.loadDataHeader();
                        }).then(function () {
                            page.layoutMode();
                        }).then(function () {
                            return page.loadDataDetail();
                        }).then(function () {
                            App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                        }).fail(function (error) {
                            if (error.status === App.settings.base.conflictStatus) {
                                // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                                //page.loadDataHeader();
                                App.ui.page.notifyAlert.clear();
                                App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
                                return;
                            }

                            if (error.status === App.settings.base.validationErrorStatus) {
                                var errors = error.responseJSON;
                                $.each(errors, function (index, err) {
                                    App.ui.page.notifyAlert.message(
                                        err.Message +
                                        (App.isUndefOrNull(err.InvalidationName) ? "" : err.Data[err.InvalidationName])
                                    ).show();
                                });
                                return;
                            }
                            App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

                        });
                    }).fail(function (error) {
                        App.ui.loading.close();
                    });
                });
            }).fail(function () {
                App.ui.page.notifyAlert.message(App.messages.base.MS0006).show();
            }).always(function () {
                setTimeout(function () {
                    page.header.element.find(":input:not(:disabled):not([readonly]):first").focus();
                }, 100);
                App.ui.loading.close();
            });
        };

        /**
         * Excel出力を行います。(Stream形式)
         */
        page.commands.excel = function () {
            var element = page.detail.element;

            if (page.header.data) {
                header = page.header.data.getChangeSet();
                if (header.created.length || header.updated.length || header.deleted.length) {
                    page.dialogs.confirmDialog.confirm({
                        text: App.messages.app.AP0018,
                        hideCancel: true,
                        multiModal: true,
                        backdrop: "static",
                        keyboard: false
                    });
                    return;
                }
            }
            if (page.detail.values.isChange["all_tab"] || page.detail.values.isChange["bacterialcontrol_tab"]
                    || page.detail.values.isChange["priorconfirmation_tab"] || page.detail.values.isChange["validity_tab"]) {
                page.dialogs.confirmDialog.confirm({
                    text: App.messages.app.AP0018,
                    hideCancel: true,
                    multiModal: true,
                    backdrop: "static",
                    keyboard: false
                });
                return;
            }

            page.header.validator.validate().then(function () {
                var lstGate = [];
                lstGate.push({ no_gate: "1", no_bunrui: element.findP("gateAll").val() });
                lstGate.push({ no_gate: "2", no_bunrui: element.findP("gateBacterialcontrol").val() });
                lstGate.push({ no_gate: "3", no_bunrui: element.findP("gatePriorconfirmation").val() });
                lstGate.push({ no_gate: "4", no_bunrui: element.findP("gateValidity").val() });
                var filter = {
                    cd_shain : page.values.cd_shain,
                    nen : page.values.nen,
                    no_oi: page.values.no_oi,
                    lstGate: lstGate
                };

                // ローディング表示
                App.ui.loading.show();
                App.ui.page.notifyAlert.clear();

                // Excel出力（Ajax通信でファイルstreamを返却）
                return $.ajax(App.ajax.file.download(page.urls.excel, filter))
                    .then(function (response, status, xhr) {
                    if (status !== "success") {
                        App.ui.page.notifyAlert.message(App.messages.base.MS0016).show();
                    } else {
                        App.file.save(response, App.ajax.file.extractFileNameDownload(xhr) || "ExcelFile.xlsx");

                        //TOsVN - 19075 - 2023/06/20: st ShohinKaihatuSupport Modify 2023
                        App.common.Log.write({
                            cd_game: App.settings.app.log_mode.gamen.SekkeiExcel
                            , cd_taisho_data: page.header.element.findP("no_seiho").val()
                        });
                        //ed
                    }

                }).fail(function (error) {
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

                }).always(function () {
                    App.ui.loading.close();
                });
            });
        };

        /**
         * すべてのバリデーションを実行します。
         */
        page.validateAll = function () {

            var validations = [];

            validations.push(page.header.validator.validate());
            validations.push(page.detail.validateList());

            return App.async.all(validations);
        };

        /**
         * Windows閉じる際のイベントを定義します。
         * @return 文字列を返却した場合に確認メッセージが表示されます。
         */
        App.ui.page.onclose = function () {

            var header, detailAllTab, detailBacterialcontrolTab, detailPriorconfirmationTab, detailValidityTab,
                closeMessage = App.messages.base.exit;

            if (page.header.data && page.values.isClose == false) {
                header = page.header.data.getChangeSet();
                if (header.created.length || header.updated.length || header.deleted.length) {
                    return closeMessage;
                }
            }
            if (page.detail.data && page.values.isClose == false) {
                if (page.detail.values.isChange["all_tab"] || page.detail.values.isChange["bacterialcontrol_tab"]
                    || page.detail.values.isChange["priorconfirmation_tab"] || page.detail.values.isChange["validity_tab"]) {
                    return closeMessage;
                }
            }
        };

        /**
        * check who is using the data
        */
        page.checkHaita = function () {
            App.ui.loading.show();
            $(".wrap, .footer").addClass("theme-blue");

            if (!App.isUndefOrNull(App.uri.queryStrings.mode)) {
                page.values.mode = App.uri.queryStrings.mode;
            }

            //試作コード（社員）
            if (!App.isUndefOrNull(App.uri.queryStrings.cd_shain)) {
                page.values.cd_shain = App.uri.queryStrings.cd_shain;
            } else {
                page.values.cd_shain = null;
            }

            //試作コード（年）
            if (!App.isUndefOrNull(App.uri.queryStrings.nen)) {
                page.values.nen = App.uri.queryStrings.nen;
            } else {
                page.values.nen = null;
            }
            //試作コード（追番）
            if (!App.isUndefOrNull(App.uri.queryStrings.no_oi)) {
                page.values.no_oi = App.uri.queryStrings.no_oi;
            } else {
                page.values.no_oi = null;
            }

            return page.loadDialogsConfirm().then(function (result) {
                return page.checkHaitaKubun(true);
            }).then(function () {
                page.initialize();
            }).always(function () {
                //App.ui.loading.close();
            });
        };

        /**
        *　画面を開きるとき排他区分がEmployeeCDになる。
        */
        page.checkHaitaKubun = function (isOpen) {
            var deferred = $.Deferred();

            if (isOpen == false || page.values.mode == App.settings.app.mode_SekkeiHinshutsuGeto.edit || page.values.mode == App.settings.app.mode_SekkeiHinshutsuGeto.viewEdit) {
                var cd_shain = page.values.cd_shain,
                    nen = page.values.nen,
                    no_oi = page.values.no_oi,
                    EmployeeCD = App.ui.page.user.EmployeeCD,
                    isOpen = isOpen;
                $.ajax(App.ajax.webapi.get(page.urls.checkHaita, { cd_shain: cd_shain, nen: nen, no_oi: no_oi, EmployeeCD: EmployeeCD, isOpen: isOpen }))
                .then(function (result) {
                    if (result.msg == "NG") {
                        page.values.mode = App.settings.app.mode_SekkeiHinshutsuGeto.view;
                        page.dialogs.confirmDialog.confirm({
                            text: App.str.format(App.messages.app.AP0164, result.userHaitaKaisha, result.userHaitaBusho, result.userHaitaName),
                            hideCancel: true,
                            multiModal: true,
                            backdrop: "static",
                            keyboard: false
                        });
                        deferred.resolve();
                    } else {
                        deferred.resolve();
                    }
                }).fail(function (error) {
                    page.values.mode = App.settings.app.mode_SekkeiHinshutsuGeto.view;
                    deferred.reject(error);
                })
            } else {
                deferred.resolve();
            }
            return deferred.promise();
        }

        /**
        * 画面の初期化処理を行います。
        */
        page.initialize = function () {
            App.ui.loading.show();

            page.initializeControl();
            page.initializeControlEvent();

            page.header.initialize();
            page.detail.initialize();

            page.loadMasterData()
            .then(function (result) {
                return page.loadDialogs();
            }).then(function (result) {
                return page.deleteData();
            }).then(function (result) {
                return page.loadDataHeader();
            }).then(function (result) {
                page.detail.element.find("#all_tab").trigger("click");
            }).then(function (result) {
                page.layoutMode();
            }).then(function () {
                page.loadDataDetail();
                //TOsVN - 19075 - 2023/06/20: st ShohinKaihatuSupport Modify 2023
                App.common.Log.write({
                    cd_game: App.settings.app.log_mode.gamen.Sekkei
                    , cd_taisho_data: page.header.element.findP("no_seiho").val()
                    , mode: {
                        gamen: "sekkei"
                        , nm_mode: page.values.mode
                    }
                });
                //ed
            }).fail(function (error) {
                page.detail.element.find("#all_tab").trigger("click");
                page.values.mode = App.settings.app.mode_SekkeiHinshutsuGeto.view;
                page.layoutMode();
                App.ui.loading.close();
            }).always(function (result) {
                page.header.element.find(":input:not(:disabled):not([readonly]):first").focus();
                //App.ui.loading.close();
            });
        };

        /**
         * 画面コントロールの初期化処理を行います。
         */
        page.initializeControl = function () {

            $(".part").part();
        };

        /**
         * コントロールへのイベントの紐づけを行います。
         */
        page.initializeControlEvent = function () {
            $("#save").on("click", page.commands.save);
            $("#excel").on("click", page.commands.excel);
            //閉じる
            $("#close").on("click", function () {
                var flg_change = false;
                if (page.header.data) {
                    header = page.header.data.getChangeSet();
                    if (header.created.length || header.updated.length || header.deleted.length) {
                        flg_change = true;
                    }
                }
                if (page.detail.data) {
                    if (page.detail.values.isChange["all_tab"] || page.detail.values.isChange["bacterialcontrol_tab"]
                        || page.detail.values.isChange["priorconfirmation_tab"] || page.detail.values.isChange["validity_tab"]) {
                        flg_change = true;
                    }
                }
                if (flg_change) {
                    page.dialogs.confirmDialog.confirm({
                        text: App.messages.base.exit,
                        multiModal: true,
                        backdrop: "static",
                        keyboard: false
                    }).then(function () {
                        page.checkHaitaKubun(false).then(function (result) {
                            page.values.isClose = true;
                            window.close();
                        }).fail(function (error) {
                            page.dialogs.confirmDialog.confirm({
                                text: App.messages.app.AP0186,
                                multiModal: true,
                                backdrop: "static",
                                keyboard: false
                            }).then(function () {
                                page.values.isClose = true;
                                window.close();
                            });
                        });
                    });
                } else {
                    page.checkHaitaKubun(false).then(function (result) {
                        window.close();
                    }).fail(function (error) {
                        page.dialogs.confirmDialog.confirm({
                            text: App.messages.app.AP0186,
                            multiModal: true,
                            backdrop: "static",
                            keyboard: false
                        }).then(function () {
                            window.close();
                        });
                    });
                }

            });
        };

        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function () {

            var cd_shain = parseFloat(page.values.cd_shain),
                nen = parseFloat(page.values.nen),
                no_oi = parseFloat(page.values.no_oi),
                gate = App.settings.app.no_gate;
            return $.ajax(App.ajax.odata.get(App.str.format(page.urls.getShisaku, cd_shain, nen, no_oi)))
            .then(function (result) {
                var nm_sample_plan = page.header.element.findP("nm_sample_plan");
                nm_sample_plan.children().remove();
                $.each(result.value, function (idx, value) {
                    result.value[idx].cd_sample = result.value[idx].nm_sample;
                    if (value.nm_sample == null) {
                        result.value[idx].cd_sample = null;
                        result.value[idx].nm_sample = "";
                    }
                });
                App.ui.appendOptions(
                    nm_sample_plan,
                    "cd_sample",
                    "nm_sample",
                    result.value,
                    false
                );
                var nm_sample_confirm = page.header.element.findP("nm_sample_confirm");
                nm_sample_confirm.children().remove();
                App.ui.appendOptions(
                    nm_sample_confirm,
                    "cd_sample",
                    "nm_sample",
                    result.value,
                    false
                );
                var param = {
                    cd_shain: cd_shain
                    , nen: nen
                    , no_oi: no_oi
                };
                return $.ajax(App.ajax.webapi.get(page.urls.getBunrui, param))
            }).then(function (result) {
                var lstGateAll = result.filter(function (data) {
                    return data.no_gate == "1";
                });
                var gateAll = page.detail.element.findP("gateAll");
                gateAll.children().remove();
                App.ui.appendOptions(
                    gateAll,
                    "no_bunrui",
                    "nm_bunrui",
                    lstGateAll,
                    false
                );
                gateAll.val(lstGateAll[0].no_bunrui_default).data("lastVal", lstGateAll[0].no_bunrui_default);
                var lstGateBacterialControl = result.filter(function (data) {
                    return data.no_gate == "2";
                });
                var gateBacterialcontrol = page.detail.element.findP("gateBacterialcontrol");
                gateBacterialcontrol.children().remove();
                App.ui.appendOptions(
                    gateBacterialcontrol,
                    "no_bunrui",
                    "nm_bunrui",
                    lstGateBacterialControl,
                    false
                );
                gateBacterialcontrol.val(lstGateBacterialControl[0].no_bunrui_default).data("lastVal", lstGateBacterialControl[0].no_bunrui_default);
                var lstGatePriorconfirmation = result.filter(function (data) {
                    return data.no_gate == "3";
                });
                var gatePriorconfirmation = page.detail.element.findP("gatePriorconfirmation");
                gatePriorconfirmation.children().remove();
                App.ui.appendOptions(
                    gatePriorconfirmation,
                    "no_bunrui",
                    "nm_bunrui",
                    lstGatePriorconfirmation,
                    false
                );
                gatePriorconfirmation.val(lstGatePriorconfirmation[0].no_bunrui_default).data("lastVal", lstGatePriorconfirmation[0].no_bunrui_default);
                var lstGateValidity = result.filter(function (data) {
                    return data.no_gate == "4";
                });
                var gateValidity = page.detail.element.findP("gateValidity");
                gateValidity.children().remove();
                App.ui.appendOptions(
                    gateValidity,
                    "no_bunrui",
                    "nm_bunrui",
                    lstGateValidity,
                    false
                );
                gateValidity.val(lstGateValidity[0].no_bunrui_default).data("lastVal", lstGateValidity[0].no_bunrui_default);
                return $.ajax(App.ajax.odata.get(App.str.format(page.urls.getShisakuhin, cd_shain, nen, no_oi)))
            }).then(function (result) {
                page.header.element.findP("nm_hin").val(result.value[0].nm_hin);
            });
        };

        /**
         * 共有ダイアログのロード処理を実行します。
         */
        page.loadDialogs = function () {

            return App.async.all({
                IroShitei_Dialog: $.get(page.urls.iroShiteiDialog),
            }).then(function (result) {

                $("#dialog-container").append(result.successes.IroShitei_Dialog);
                page.dialogs.iroShiteiDialog = _602_IroShitei_Dialog;
                page.dialogs.iroShiteiDialog.options.dataColor = page.values.dataColor
                page.dialogs.iroShiteiDialog.initialize();
            });
        };

        /**
         * 共有ダイアログのロード処理を実行します。
         */
        page.loadDialogsConfirm = function () {
            return App.async.all({
                confirmDialog: $.get(page.urls.confirmDialog)
            }).then(function (result) {
                $("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;
            });
        };

        /**
         * load data header
         */
        page.loadDataHeader = function () {
            var deferred = $.Deferred();
            var cd_shain = parseFloat(page.values.cd_shain),
                nen = parseFloat(page.values.nen),
                no_oi = parseFloat(page.values.no_oi);
            $.ajax(App.ajax.odata.get(App.str.format(page.urls.getGateHeader, cd_shain, nen, no_oi)))
            .then(function (result) {
                page.header.bind(result.value[0]);
                deferred.resolve();
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                deferred.reject();
            });
            return deferred.promise();
        };

        /**
         * delete unused data attachment
         */
        page.deleteData = function () {
            var deferred = $.Deferred();
            if (page.values.mode != App.settings.app.mode_SekkeiHinshutsuGeto.edit) {
                deferred.resolve();
            } else {
                var param = {
                    cd_shain: parseFloat(page.values.cd_shain),
                    nen: parseFloat(page.values.nen),
                    no_oi: parseFloat(page.values.no_oi)
                }
                $.ajax(App.ajax.webapi.post(page.urls.deleteAttachment, param))
                .then(function () {
                    deferred.resolve();
                }).fail(function (error) {
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                    deferred.reject();
                });
            }
            return deferred.promise();
        };

        /**
        * load data tab detail
        */
        page.loadDataDetail = function () {
            var element = page.detail.element,
                loading = true;
            var param = {
                cd_shain: parseFloat(page.values.cd_shain)
                , nen: parseFloat(page.values.nen)
                , no_oi: parseFloat(page.values.no_oi)
                , no_gate: App.settings.app.no_gate_val.no_gate_1
                , no_bunrui: element.findP("gateAll").val()
            };
            if (!App.isUndefOrNullOrStrEmpty(element.findP("gateAll").val())) {
                page.detail.searchTab(param, "all_tab");
                loading = false;
            }
            if (!App.isUndefOrNullOrStrEmpty(element.findP("gateBacterialcontrol").val())) {
                param.no_gate = App.settings.app.no_gate_val.no_gate_2;
                param.no_bunrui = element.findP("gateBacterialcontrol").val();
                page.detail.searchTab(param, "bacterialcontrol_tab");
                loading = false;
            }
            if (!App.isUndefOrNullOrStrEmpty(element.findP("gatePriorconfirmation").val())) {
                param.no_gate = App.settings.app.no_gate_val.no_gate_3;
                param.no_bunrui = element.findP("gatePriorconfirmation").val();
                page.detail.searchTab(param, "priorconfirmation_tab");
                loading = false;
            }
            if (!App.isUndefOrNullOrStrEmpty(element.findP("gateValidity").val())) {
                param.no_gate = App.settings.app.no_gate_val.no_gate_4;
                param.no_bunrui = element.findP("gateValidity").val();
                page.detail.searchTab(param, "validity_tab");
                loading = false;
            }
            if (loading) {
                App.ui.loading.close();
            }
        };

        /**
        * search data tab
        */
        page.detail.searchTab = function (param, cls) {
            App.ui.loading.show();
            var deferred = $.Deferred(),
                element = page.detail.element;
            
            $.ajax(App.ajax.webapi.get(page.urls.search, param))
            .then(function (result) {
                page.detail.bind(result, undefined, cls);
                page.detail.updateNosort(cls, true);
                page.detail.values.isChange[cls] = false;
                element.find("." + cls).find(".add-item").removeClass("hide");
                element.find("." + cls).find(".del-item").removeClass("hide");
                deferred.resolve();
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                deferred.reject();
            }).always(function () {
                App.ui.loading.close();
            });
            return deferred.promise();
        };

        /**
         * display layout mode
         */
        page.layoutMode = function () {
            if (App.settings.app.mode_SekkeiHinshutsuGeto.view == page.values.mode) {
                page.header.element.find(":input").prop("disabled", true);
                page.header.element.find("select").prop("disabled", true);
                page.detail.element.find(".add-item").prop("disabled", true);
                page.detail.element.find(".del-item").prop("disabled", true);
                page.detail.element.find("textarea").prop("disabled", true);
                page.detail.element.find("input").prop("disabled", true);
                $("#save").prop("disabled", true);
            } else if (App.settings.app.mode_SekkeiHinshutsuGeto.viewEdit == page.values.mode) {
                page.header.element.find(":input:not(.reader_confirm_release)").prop("disabled", true);
                page.detail.element.find(".add-item").prop("disabled", true);
                page.detail.element.find(".del-item").prop("disabled", true);
                page.detail.element.find("input,textarea").prop("disabled", true);
            }else if (App.settings.app.mode_SekkeiHinshutsuGeto.edit == page.values.mode) {
                page.header.element.find("textarea").prop("disabled", false);
                page.detail.element.find(".add-item").prop("disabled", false);
                page.detail.element.find(".del-item").prop("disabled", false);
                page.detail.element.find("textarea").prop("disabled", false);
                page.detail.element.find("input").prop("disabled", false);
            }
        };

        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.validations = {
            nm_comment_tanto: {
                rules: {
                    maxlength: 300
                },
                options: {
                    name: "担当者コメント"
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_comment_reader: {
                rules: {
                    maxlength: 300
                },
                options: {
                    name: "リーダーコメント"
                },
                messages: {
                    maxlength: App.messages.base.maxlength
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
            element.on("click", ".btn-approval-status", page.header.approvalStatus);

        };

        /**
         * processed approval flg
         */
        page.header.approvalStatus = function (e) {
            var element = page.header.element,
                target = $(e.target),
                id = element.attr("data-key"),
                entity = page.header.data.entry(id),
                currentDate = new Date(),
                strCurrentDate = App.str.format("{0}/{1}/{2}", currentDate.getFullYear(), (currentDate.getMonth() + 1) > 9 ? currentDate.getMonth() + 1 : "0" + (currentDate.getMonth() + 1), currentDate.getDate() > 9 ? currentDate.getDate() : "0" + currentDate.getDate());
            if (target.hasClass("tanto_plan_signature")) {
                element.find(".tanto_plan_signature").prop("disabled", true);
                element.find(".tanto_plan_release").prop("disabled", false);
                element.findP("dt_shonin_tanto_plan").text(strCurrentDate);
                element.findP("nm_shonin_tanto_plan").text(App.ui.page.user.Name);
                entity["flg_shonin_tanto_plan"] = true;
                entity["dt_shonin_tanto_plan"] = strCurrentDate;
                entity["nm_shonin_tanto_plan"] = App.ui.page.user.Name;
                page.header.data.update(entity);
            }else if (target.hasClass("tanto_plan_release")) {
                element.find(".tanto_plan_signature").prop("disabled", false);
                element.find(".tanto_plan_release").prop("disabled", true);
                element.findP("dt_shonin_tanto_plan").text("");
                element.findP("nm_shonin_tanto_plan").text("");
                entity["flg_shonin_tanto_plan"] = false;
                entity["dt_shonin_tanto_plan"] = "";
                entity["nm_shonin_tanto_plan"] = "";
                page.header.data.update(entity);
            }else if (target.hasClass("reader_plan_signature")) {
                element.find(".reader_plan_signature").prop("disabled", true);
                element.find(".reader_plan_release").prop("disabled", false);
                element.findP("dt_shonin_reader_plan").text(strCurrentDate);
                element.findP("nm_shonin_reader_plan").text(App.ui.page.user.Name);
                element.findP("nm_sample_plan").prop("disabled", true);
                entity["flg_shonin_reader_plan"] = true;
                entity["dt_shonin_reader_plan"] = strCurrentDate;
                entity["nm_shonin_reader_plan"] = App.ui.page.user.Name;
                page.header.data.update(entity);
            } else if (target.hasClass("reader_plan_release")) {
                element.find(".reader_plan_signature").prop("disabled", false);
                element.find(".reader_plan_release").prop("disabled", true);
                element.findP("dt_shonin_reader_plan").text("");
                element.findP("nm_shonin_reader_plan").text("");
                element.findP("nm_sample_plan").prop("disabled", false);
                entity["flg_shonin_reader_plan"] = false;
                entity["dt_shonin_reader_plan"] = "";
                entity["nm_shonin_reader_plan"] = "";
                page.header.data.update(entity);
            } else if (target.hasClass("tanto_confirm_signature")) {
                element.find(".tanto_confirm_signature").prop("disabled", true);
                element.find(".tanto_confirm_release").prop("disabled", false);
                element.findP("dt_shonin_tanto_confirm").text(strCurrentDate);
                element.findP("nm_shonin_tanto_confirm").text(App.ui.page.user.Name);
                entity["flg_shonin_tanto_confirm"] = true;
                entity["dt_shonin_tanto_confirm"] = strCurrentDate;
                entity["nm_shonin_tanto_confirm"] = App.ui.page.user.Name;
                page.header.data.update(entity);
            } else if (target.hasClass("tanto_confirm_release")) {
                element.find(".tanto_confirm_signature").prop("disabled", false);
                element.find(".tanto_confirm_release").prop("disabled", true);
                element.findP("dt_shonin_tanto_confirm").text("");
                element.findP("nm_shonin_tanto_confirm").text("");
                entity["flg_shonin_tanto_confirm"] = false;
                entity["dt_shonin_tanto_confirm"] = "";
                entity["nm_shonin_tanto_confirm"] = "";
                page.header.data.update(entity);
            } else if (target.hasClass("reader_confirm_signature")) {
                element.find(".reader_confirm_signature").prop("disabled", true);
                element.find(".reader_confirm_release").prop("disabled", false);
                element.findP("dt_shonin_reader_confirm").text(strCurrentDate);
                element.findP("nm_shonin_reader_confirm").text(App.ui.page.user.Name);
                element.findP("nm_sample_confirm").prop("disabled", true);
                entity["flg_shonin_reader_confirm"] = true;
                entity["dt_shonin_reader_confirm"] = strCurrentDate;
                entity["nm_shonin_reader_confirm"] = App.ui.page.user.Name;
                page.values.flgUpdateDataNewest = true;
                page.header.data.update(entity);
            } else if (target.hasClass("reader_confirm_release")) {
                element.find(".reader_confirm_signature").prop("disabled", false);
                element.find(".reader_confirm_release").prop("disabled", true);
                element.findP("dt_shonin_reader_confirm").text("");
                element.findP("nm_shonin_reader_confirm").text("");
                element.findP("nm_sample_confirm").prop("disabled", false);
                entity["flg_shonin_reader_confirm"] = false;
                entity["dt_shonin_reader_confirm"] = "";
                entity["nm_shonin_reader_confirm"] = "";
                page.values.flgUpdateDataNewest = true;
                page.header.data.update(entity);
            }
        };

        /**
         * 画面ヘッダーへのデータバインド処理を行います。
         */
        page.header.bind = function (data, isNewData) {

            if (App.isUndefOrNullOrStrEmpty(data)) {
                App.ui.page.notifyAlert.message(App.messages.app.AP0171).show();
                page.values.mode = App.settings.app.mode_SekkeiHinshutsuGeto.view;
                page.detail.element.find("#tab-container").addClass("hide");
                $("#excel").prop("disabled", true);;
                return;
            }
            var element = page.header.element,
                dataSet = App.ui.page.dataSet();

            page.header.data = dataSet;
            data.no_seiho = page.values.cd_shain + "-" + page.values.nen + "-" + page.values.no_oi;
            page.values.flg_shonin_reader_confirm = data.flg_shonin_reader_confirm;
            if (data.nm_sample_plan == null) {
                page.header.element.findP("nm_sample_plan").val("null");
            }
            if (data.nm_sample_confirm == null) {
                page.header.element.findP("nm_sample_confirm").val("null");
            }
            (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(data);
            page.header.element.form(page.header.options.bindOption).bind(data);
            if (data.flg_shonin_tanto_plan) {
                element.find(".tanto_plan_signature").prop("disabled", true);
                element.find(".tanto_plan_release").prop("disabled", false);
            } else {
                element.find(".tanto_plan_signature").prop("disabled", false);
                element.find(".tanto_plan_release").prop("disabled", true);
            }

            if (data.flg_shonin_reader_plan) {
                element.find(".reader_plan_signature").prop("disabled", true);
                element.find(".reader_plan_release").prop("disabled", false);
                element.findP("nm_sample_plan").prop("disabled", true);
            } else {
                element.find(".reader_plan_signature").prop("disabled", false);
                element.find(".reader_plan_release").prop("disabled", true);
                element.findP("nm_sample_plan").prop("disabled", false);
            }

            if (data.flg_shonin_tanto_confirm) {
                element.find(".tanto_confirm_signature").prop("disabled", true);
                element.find(".tanto_confirm_release").prop("disabled", false);
            } else {
                element.find(".tanto_confirm_signature").prop("disabled", false);
                element.find(".tanto_confirm_release").prop("disabled", true);
            }

            if (data.flg_shonin_reader_confirm) {
                element.find(".reader_confirm_signature").prop("disabled", true);
                element.find(".reader_confirm_release").prop("disabled", false);
                element.findP("nm_sample_confirm").prop("disabled", true);
            } else {
                element.find(".reader_confirm_signature").prop("disabled", false);
                element.find(".reader_confirm_release").prop("disabled", true);
            }
            if (page.values.mode != App.settings.app.mode_SekkeiHinshutsuGeto.view) {
                if (data.flg_shonin_reader_confirm) {
                    page.values.mode = App.settings.app.mode_SekkeiHinshutsuGeto.viewEdit;
                } else {
                    page.values.mode = App.uri.queryStrings.mode == App.settings.app.mode_SekkeiHinshutsuGeto.view ? App.uri.queryStrings.mode : App.settings.app.mode_SekkeiHinshutsuGeto.edit;
                }
            }
        };

        /**
        * 画面ヘッダーにデータを設定する際のオプションを定義します。
        */
        page.header.options.bindOption = {
            appliers: {
                dt_shonin_tanto_plan: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.text("");
                    }
                    else {
                        element.text(page.formatDate(value));
                    }
                    return true;
                },
                dt_shonin_reader_plan: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.text("");
                    }
                    else {
                        element.text(page.formatDate(value));
                    }
                    return true;
                },
                dt_shonin_tanto_confirm: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.text("");
                    }
                    else {
                        element.text(page.formatDate(value));
                    }
                    return true;
                },
                dt_shonin_reader_confirm: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.text("");
                    }
                    else {
                        element.text(page.formatDate(value));
                    }
                    return true;
                }
            },
            converters: {
                nm_sample_plan: function (element, defVal) {
                    return defVal == "null" ? null : defVal;
                },
                nm_sample_confirm: function (element, defVal) {
                    return defVal == "null" ? null : defVal;
                }
            }
        };

        /**
         * formatDate
         */
        page.formatDate = function (date) {
            var year = date.substr(0, 4);
            var month = date.substr(5, 2);
            var day = date.substr(8, 2);
            return App.str.format("{0}/{1}/{2}", year, month, day);
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
                data = element.form(page.header.options.bindOption).data();

            page.values.isChangeRunning[property] = true;

            element.validation().validate({
                targets: target
            }).then(function () {
                entity[property] = data[property];
                page.header.data.update(entity);
            }).always(function () {
                delete page.values.isChangeRunning[property];
            });
        };

        /**
         * 画面明細のバリデーションを定義します。
         */
        page.detail.options.validations = {
            nm_check_bunrui: {
                rules: {
                    maxlength: 30
                },
                options: {
                    name: "チェック分類"
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_check: {
                rules: {
                    maxlengthCus: function (value, opts, state, done) {
                        var $tbody = state.tbody.element ? state.tbody.element : state.tbody,
                            cls = $tbody.closest(".tab").attr("data-value"),
                            param = 2000;
                        if (App.isUndefOrNullOrStrEmpty(value)) {
                            return done(true);
                        }
                        value = App.isNum(value) ? value + "" : value;
                        var length = App.isArray(value) ? value.length : value.length;

                        if (((value || "") + "") === "" || length <= param) {
                            return done(true);
                        } else {
                            var option = {
                                name: cls == "bacterialcontrol_tab" ? "チェック項目１" : "チェック項目",
                                param: param
                            };
                            page.detail.options.validations.nm_check.messages.maxlengthCus = App.str.format(App.messages.base.maxlength, option);
                            return done(false);
                        }
                    }
                },
                options: {
                    name: "チェック項目"
                },
                messages: {
                    maxlengthCus: App.messages.base.maxlength
                }
            },
            nm_check_note1: {
                rules: {
                    maxlengthCus: function (value, opts, state, done) {
                        var $tbody = state.tbody.element ? state.tbody.element : state.tbody,
                            cls = $tbody.closest(".tab").attr("data-value"),
                            param = 2000;
                        if (App.isUndefOrNullOrStrEmpty(value)) {
                            return done(true);
                        }
                        value = App.isNum(value) ? value + "" : value;
                        var length = App.isArray(value) ? value.length : value.length;

                        if (((value || "") + "") === "" || length <= param) {
                            return done(true);
                        } else {
                            var option = {
                                name: cls == "priorconfirmation_tab" ? "備考／リスクの具体的な内容" : cls == "bacterialcontrol_tab" ? "チェック項目２" : "備考",
                                param: param
                            };
                            page.detail.options.validations.nm_check_note1.messages.maxlengthCus = App.str.format(App.messages.base.maxlength, option);
                            return done(false);
                        }
                    }
                },
                options: {
                    name: "備考"
                },
                messages: {
                    maxlengthCus: App.messages.base.maxlength
                }
            },
            nm_check_note2: {
                rules: {
                    maxlengthCus: function (value, opts, state, done) {
                        var $tbody = state.tbody.element ? state.tbody.element : state.tbody,
                            cls = $tbody.closest(".tab").attr("data-value"),
                            param = 2000;
                        if (App.isUndefOrNullOrStrEmpty(value)) {
                            return done(true);
                        }
                        value = App.isNum(value) ? value + "" : value;
                        var length = App.isArray(value) ? value.length : value.length;

                        if (((value || "") + "") === "" || length <= param) {
                            return done(true);
                        } else {
                            var option = {
                                name: cls == "bacterialcontrol_tab" ? "備考" : "リスクがある場合のアクションプラン",
                                param: param
                            };
                            page.detail.options.validations.nm_check_note2.messages.maxlengthCus = App.str.format(App.messages.base.maxlength, option);
                            return done(false);
                        }
                    }
                },
                options: {
                    name: "リスクがある場合のアクションプラン"
                },
                messages: {
                    maxlengthCus: App.messages.base.maxlength
                }
            },
            nm_comment_plan: {
                rules: {
                    maxlength: 2000
                },
                options: {
                    name: "計画時コメント"
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_comment_confirm: {
                rules: {
                    maxlength: 2000
                },
                options: {
                    name: "品位確定時コメント"
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_free: {
                rules: {
                    maxlength: 2000
                },
                options: {
                    name: "フリー項目"
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            }
        };

        /**
         * 画面明細の初期化処理を行います。
         */
        page.detail.initialize = function () {

            var element = $(".detail");

            page.detail.validator = element.validation(page.createValidator(page.detail.options.validations));
            page.detail.element = element;
            page.detail.initializeAllTab();
            page.detail.initializeBacterialcontrolTab();
            page.detail.initializePriorconfirmationTab();
            page.detail.initializeValidityTab();
            //全体
            element.find("#all_tab").click(function () {
                element.find(".all_tab").removeClass("hide").addClass("active");
                element.find(".bacterialcontrol_tab").addClass("hide").removeClass("active");
                element.find(".priorconfirmation_tab").addClass("hide").removeClass("active");
                element.find(".validity_tab").addClass("hide").removeClass("active");
                page.detail.values.tab = "all_tab";
                page.detail.values.noGate = App.settings.app.no_gate_val.no_gate_1;
                $(window).resize();
            });
            //菌制御
            element.find("#bacterialcontrol_tab").click(function () {
                element.find(".all_tab").addClass("hide").removeClass("active");
                element.find(".bacterialcontrol_tab").removeClass("hide").addClass("active");
                element.find(".priorconfirmation_tab").addClass("hide").removeClass("active");
                element.find(".validity_tab").addClass("hide").removeClass("active");
                page.detail.values.tab = "bacterialcontrol_tab";
                page.detail.values.noGate = App.settings.app.no_gate_val.no_gate_2;
                $(window).resize();
            });
            //事前確認
            element.find("#priorconfirmation_tab").click(function () {
                element.find(".all_tab").addClass("hide").removeClass("active");
                element.find(".bacterialcontrol_tab").addClass("hide").removeClass("active");
                element.find(".priorconfirmation_tab").removeClass("hide").addClass("active");
                element.find(".validity_tab").addClass("hide").removeClass("active");
                page.detail.values.tab = "priorconfirmation_tab";
                page.detail.values.noGate = App.settings.app.no_gate_val.no_gate_3;
                $(window).resize();
            });
            //妥当性
            element.find("#validity_tab").click(function () {
                element.find(".all_tab").addClass("hide").removeClass("active");
                element.find(".bacterialcontrol_tab").addClass("hide").removeClass("active");
                element.find(".priorconfirmation_tab").addClass("hide").removeClass("active");
                element.find(".validity_tab").removeClass("hide").addClass("active");
                page.detail.values.tab = "validity_tab";
                page.detail.values.noGate = App.settings.app.no_gate_val.no_gate_4;
                $(window).resize();
            });

        };

        /**
         * 画面明細の初期化処理を行います。
         */
        page.detail.initializeAllTab = function () {

            var element = page.detail.element.find(".all_tab"),
                table = element.find(".datatable"),
                datatable = table.dataTable({
                    height: 427,
                    resize: true,
                    //列固定横スクロールにする場合は、下記3行をコメント解除
                    fixedColumn: true,                //列固定の指定
                    fixedColumns: 2,                  //固定位置を指定（左端を0としてカウント）
                    innerWidth: 1871,                 //可動列の合計幅を指定
                    resizeOffset: 60,
                    onselect: page.detail.select,
                    onchange: page.detail.change
                });

            table = element.find(".datatable"); //列固定にした場合DOM要素が再作成されるため、変数を再取得
            page.detail.dataTable["all_tab"] = datatable;

            element.on("click", ".add-item", page.detail.insertNewItemAfter);
            element.on("click", ".del-item", page.detail.deleteItem);
            element.on("change", "[data-prop='gateAll']", page.detail.reSearch);
            table.on("dblclick", "[data-prop='nm_check_note1']", page.detail.showIroSettingDialog);
            table.on("dblclick", "[data-prop='nm_comment_plan']", page.detail.showIroSettingDialog);
            table.on("dblclick", "[data-prop='nm_comment_confirm']", page.detail.showIroSettingDialog);
            table.on("dblclick", "[data-prop='nm_free']", page.detail.showIroSettingDialog);
            table.on("click", ".documents", page.detail.showKanrenTenpuShiryo);
            table.on("click", ".attachment", page.detail.showKanrenTenpuShiryo);

            // 行選択時に利用するテーブルインデックスを指定します
            page.detail.fixedColumnIndex["all_tab"] = element.find(".fix-columns").length;

            page.detail.bind([], true, "all_tab");
        };

        /**
         * 画面明細の初期化処理を行います。
         */
        page.detail.initializeBacterialcontrolTab = function () {

            var element = page.detail.element.find(".bacterialcontrol_tab"),
                table = element.find(".datatable"),
                datatable = table.dataTable({
                    height: 427,
                    resize: true,
                    //列固定横スクロールにする場合は、下記3行をコメント解除
                    fixedColumn: true,                //列固定の指定
                    fixedColumns: 3,                  //固定位置を指定（左端を0としてカウント）
                    innerWidth: 2271,                 //可動列の合計幅を指定
                    resizeOffset: 60,
                    onselect: page.detail.select,
                    onchange: page.detail.change
                });

            table = element.find(".datatable"); //列固定にした場合DOM要素が再作成されるため、変数を再取得
            page.detail.dataTable["bacterialcontrol_tab"] = datatable;

            element.on("click", ".add-item", page.detail.insertNewItemAfter);
            element.on("click", ".del-item", page.detail.deleteItem);
            element.on("change", "[data-prop='gateBacterialcontrol']", page.detail.reSearch);
            //table.on("dblclick", "[data-prop='nm_check_note1']", page.detail.showIroSettingDialog);
            table.on("dblclick", "[data-prop='nm_check_note2']", page.detail.showIroSettingDialog);
            table.on("dblclick", "[data-prop='nm_comment_plan']", page.detail.showIroSettingDialog);
            table.on("dblclick", "[data-prop='nm_comment_confirm']", page.detail.showIroSettingDialog);
            table.on("dblclick", "[data-prop='nm_free']", page.detail.showIroSettingDialog);
            table.on("click", ".documents", page.detail.showKanrenTenpuShiryo);
            table.on("click", ".attachment", page.detail.showKanrenTenpuShiryo);

            // 行選択時に利用するテーブルインデックスを指定します
            page.detail.fixedColumnIndex["bacterialcontrol_tab"] = element.find(".fix-columns").length;
            page.detail.bind([], true, "bacterialcontrol_tab");
        };

        /**
         * 画面明細の初期化処理を行います。
         */
        page.detail.initializePriorconfirmationTab = function () {

            var element = page.detail.element.find(".priorconfirmation_tab"),
                table = element.find(".datatable"),
                datatable = table.dataTable({
                    height: 427,
                    resize: true,
                    //列固定横スクロールにする場合は、下記3行をコメント解除
                    fixedColumn: true,                //列固定の指定
                    fixedColumns: 2,                  //固定位置を指定（左端を0としてカウント）
                    innerWidth: 2271,                 //可動列の合計幅を指定
                    resizeOffset: 60,
                    onselect: page.detail.select,
                    onchange: page.detail.change
                });

            table = element.find(".datatable"); //列固定にした場合DOM要素が再作成されるため、変数を再取得
            page.detail.dataTable["priorconfirmation_tab"] = datatable;

            element.on("click", ".add-item", page.detail.insertNewItemAfter);
            element.on("click", ".del-item", page.detail.deleteItem);
            element.on("change", "[data-prop='gatePriorconfirmation']", page.detail.reSearch);
            table.on("dblclick", "[data-prop='nm_check']", page.detail.showIroSettingDialog);
            table.on("dblclick", "[data-prop='nm_check_note1']", page.detail.showIroSettingDialog);
            table.on("dblclick", "[data-prop='nm_check_note2']", page.detail.showIroSettingDialog);
            table.on("dblclick", "[data-prop='nm_comment_plan']", page.detail.showIroSettingDialog);
            table.on("dblclick", "[data-prop='nm_comment_confirm']", page.detail.showIroSettingDialog);
            table.on("dblclick", "[data-prop='nm_free']", page.detail.showIroSettingDialog);
            table.on("click", ".documents", page.detail.showKanrenTenpuShiryo);
            table.on("click", ".attachment", page.detail.showKanrenTenpuShiryo);

            // 行選択時に利用するテーブルインデックスを指定します
            page.detail.fixedColumnIndex["priorconfirmation_tab"] = element.find(".fix-columns").length;

            page.detail.bind([], true, "priorconfirmation_tab");
        };

        /**
         * 画面明細の初期化処理を行います。
         */
        page.detail.initializeValidityTab = function () {

            var element = page.detail.element.find(".validity_tab"),
                table = element.find(".datatable"),
                datatable = table.dataTable({
                    height: 427,
                    resize: true,
                    //列固定横スクロールにする場合は、下記3行をコメント解除
                    fixedColumn: true,                //列固定の指定
                    fixedColumns: 2,                  //固定位置を指定（左端を0としてカウント）
                    innerWidth: 1871,                 //可動列の合計幅を指定
                    resizeOffset: 60,
                    onselect: page.detail.select,
                    onchange: page.detail.change
                });

            table = element.find(".datatable"); //列固定にした場合DOM要素が再作成されるため、変数を再取得
            page.detail.dataTable["validity_tab"] = datatable;

            element.on("click", ".add-item", page.detail.insertNewItemAfter);
            element.on("click", ".del-item", page.detail.deleteItem);
            element.on("change", "[data-prop='gateValidity']", page.detail.reSearch);
            table.on("dblclick", "[data-prop='nm_check_note1']", page.detail.showIroSettingDialog);
            table.on("dblclick", "[data-prop='nm_comment_plan']", page.detail.showIroSettingDialog);
            table.on("dblclick", "[data-prop='nm_comment_confirm']", page.detail.showIroSettingDialog);
            table.on("dblclick", "[data-prop='nm_free']", page.detail.showIroSettingDialog);
            table.on("click", ".documents", page.detail.showKanrenTenpuShiryo);
            table.on("click", ".attachment", page.detail.showKanrenTenpuShiryo);

            // 行選択時に利用するテーブルインデックスを指定します
            page.detail.fixedColumnIndex["validity_tab"] = element.find(".fix-columns").length;

            page.detail.bind([], true, "validity_tab");
        };

        /**
         * 画面明細の各行にデータを設定する際のオプションを定義します。
         */
        page.detail.options.bindOption = {
            appliers: {
                flg_check_plan: function (value, element) {
                    element.prop("checked", value ? 1 : 0);
                    return true;
                },
                flg_check_review: function (value, element) {
                    element.prop("checked", value ? 1 : 0);
                    return true;
                }
            },
            converters:{
                flg_check_plan: function (element, defVal) {
                    return element.is(':checked') ? 1 : 0;
               },
                flg_check_review: function(element, defVal) {
                    return element.is(':checked') ? 1 : 0;
               }
            }

        };

        /**
         * 画面明細へのデータバインド処理を行います。
         */
        page.detail.bind = function (data, isNewData, cls) {
            var element = page.detail.element,
                i, l, item,
                dataSet = App.ui.page.dataSet();
            data = (data.Items) ? data.Items : data;
            page.detail.data[cls] = dataSet;

            page.detail.dataTable[cls].dataTable("clear");
            page.detail.values.maxNoMeisai[cls] = 0;
            page.detail.dataTable[cls].dataTable("addRows", data, function (row, item) {
                (isNewData || item.flg_add_new == 1 ? dataSet.add : dataSet.attach).bind(dataSet)(item);
                if (item.flg_ma_attachment == 1) {
                    row.find(".documents").addClass("btn-attachment");
                }
                if (item.flg_tr_attachment == 1) {
                    row.find(".attachment").addClass("btn-attachment");
                }
                row.findP("nm_check").css("background", item.col_item == null ? "" : item.col_item);
                row.findP("nm_check").css("color", page.detail.lightOrDark(item.col_item == null ? "" : item.col_item));
                row.findP("nm_check_note1").css("background", item.col_note1 == null ? "" : item.col_note1);
                row.findP("nm_check_note1").css("color", page.detail.lightOrDark(item.col_note1 == null ? "" : item.col_note1));
                row.findP("nm_check_note2").css("background", item.col_note2 == null ? "" : item.col_note2);
                row.findP("nm_check_note2").css("color", page.detail.lightOrDark(item.col_note2 == null ? "" : item.col_note2));
                row.findP("nm_comment_plan").css("background", item.col_comment_plan == null ? "" : item.col_comment_plan);
                row.findP("nm_comment_plan").css("color", page.detail.lightOrDark(item.col_comment_plan == null ? "" : item.col_comment_plan));
                row.findP("nm_comment_confirm").css("background", item.col_comment_confirm == null ? "" : item.col_comment_confirm);
                row.findP("nm_comment_confirm").css("color", page.detail.lightOrDark(item.col_comment_confirm == null ? "" : item.col_comment_confirm));
                row.findP("nm_free").css("background", item.col_free == null ? "" : item.col_free);
                row.findP("nm_free").css("color", page.detail.lightOrDark(item.col_free == null ? "" : item.col_free));
                if (page.detail.values.maxNoMeisai[cls] < item.no_meisai) {
                    page.detail.values.maxNoMeisai[cls] = item.no_meisai;
                }
                var flg_disabled = false;
                if (App.settings.app.mode_SekkeiHinshutsuGeto.viewEdit == page.values.mode || App.settings.app.mode_SekkeiHinshutsuGeto.view == page.values.mode) {
                    flg_disabled = true;
                }
                row.findP("nm_check_bunrui").prop("disabled", item.flg_master == 1 ? true : flg_disabled);
                row.findP("nm_check").prop("disabled", cls == "priorconfirmation_tab" ? flg_disabled : item.flg_master == 1 ? true : flg_disabled);
                row.findP("nm_check_note1").prop("disabled", cls != "bacterialcontrol_tab" ? flg_disabled : item.flg_master == 1 ? true : flg_disabled);
                row.findP("flg_check_plan").prop("disabled", item.flg_check_disp == 1 || item.flg_check_disp == null ? flg_disabled : true);
                row.findP("flg_check_review").prop("disabled", item.flg_check_disp == 1 || item.flg_check_disp == null ? flg_disabled : true);
                row.form(page.detail.options.bindOption).bind(item);

                return row;
            }, true);

            //バリデーションを実行します。
            page.detail.validateList(true, cls);
        };

        /**
         * 画面明細の一覧の行が選択された時の処理を行います。
         */
        page.detail.select = function (e, row) {
            if (e == undefined)
                return;
            var target = e.target == undefined ? $(e) : $(e.target),
                cls = target.closest(".tab").attr("data-value");
            $($(row.element[page.detail.fixedColumnIndex[cls]]).closest("table")[0].querySelectorAll(".select-tab.selected")).removeClass("selected").addClass("unselected");
            $(row.element[page.detail.fixedColumnIndex[cls]].querySelectorAll(".select-tab")).removeClass("unselected").addClass("selected");
            
            if (!App.isUndefOrNull(page.detail.selectedRow[cls])) {
                page.detail.selectedRow[cls].element.find(".selected-row").removeClass("selected-row");
            }
            row.element.find("tr").addClass("selected-row");
            row.element.find("tr").find("textarea").addClass("selected-row");
            page.detail.selectedRow[cls] = row;
        };

        /**
         * 画面明細の一覧の入力項目の変更イベントの処理を行います。
         */
        page.detail.change = function (e, row) {
            var target = $(e.target),
                cls = target.closest(".tab").attr("data-value");
                id = row.element.attr("data-key"),
                property = target.attr("data-prop"),
                entity = page.detail.data[cls].entry(id),
                options = {
                    filter: page.detail.validationFilter
                };

                page.values.isChangeRunning[property] = true;
                page.detail.values.isChange[cls] = true;

            page.detail.executeValidation(target, row)
            .then(function () {
                if (property == "nm_check") {
                    entity["flg_check"] = App.isUndefOrNullOrStrEmpty(row.element.form(page.detail.options.bindOption).data()[property]) ? 0 : 1;
                } else if (property == "nm_check_note1") {
                    entity["flg_check_note1"] = App.isUndefOrNullOrStrEmpty(row.element.form(page.detail.options.bindOption).data()[property]) ? 0 : 1;
                } else if (property == "nm_check_note2") {
                    entity["flg_check_note2"] = App.isUndefOrNullOrStrEmpty(row.element.form(page.detail.options.bindOption).data()[property]) ? 0 : 1;
                }
                entity[property] = row.element.form(page.detail.options.bindOption).data()[property];
                page.detail.data[cls].update(entity);

                //入力行の他の項目のバリデーション（必須チェック以外）を実施します
                page.detail.executeValidation(row.element.find(":input"), row, options);
            }).always(function () {
                delete page.values.isChangeRunning[property];
            });
        };

        /**
         * 画面明細の一覧に新規データを追加します。
         */
        page.detail.addNewItem = function (e,newData) {
            var target = $(e.target),
                cls = target.closest(".tab").attr("data-value"),
                element = page.detail.element,
                maxNoMeisai = page.detail.values.maxNoMeisai[cls] + 1;
            newData.no_sort = 1;
            newData.no_meisai = maxNoMeisai;
            page.detail.values.maxNoMeisai[cls] = maxNoMeisai;
            if (cls == "all_tab") {
                newData.no_bunrui = element.findP("gateAll").val();
            } else if (cls == "bacterialcontrol_tab") {
                newData.no_bunrui = element.findP("gateBacterialcontrol").val();
            } else if (cls == "priorconfirmation_tab") {
                newData.no_bunrui = element.findP("gatePriorconfirmation").val();
            } else if (cls == "validity_tab") {
                newData.no_bunrui = element.findP("gateValidity").val();
            }
            page.detail.data[cls].add(newData);
            page.detail.dataTable[cls].dataTable("addRow", function (tbody) {
                tbody.form().bind(newData);
                tbody.find(".documents").prop("disabled", true);
                return tbody;
            }, true);
            page.detail.updateNosort(cls);
        };

        /**
         * 画面明細の一覧に対して、選択行の後に新規データを挿入します。
         */
        page.detail.insertNewItemAfter = function (e) {
            var newData = {
                cd_shain: parseFloat(page.values.cd_shain),
                nen: parseFloat(page.values.nen),
                no_oi: parseFloat(page.values.no_oi),
                no_gate: page.detail.values.noGate,
                no_check: 0,
                flg_check_plan: 0,
                flg_check_review: 0,
                flg_check: 0,
                flg_check_note1: 0,
                flg_check_note2: 0
            };

            // 新規データを挿入（後）
            page.detail.insertRow(e,newData, true, true);
        };

        /**
         * update no_sort
         */
        page.detail.updateNosort = function (cls,isSearch) {
            var maxNoMeisai = page.detail.values.maxNoMeisai[cls];
            $.each(page.detail.element.find("." + cls).find(".flow-container").find(".datatable").find("tbody").not(".item-tmpl"), function (idx, tbody) {
                var id = $(tbody).attr("data-key"),
                    entity = page.detail.data[cls].entry(id),
                    state = page.detail.data[cls].entries[id].state;
                if (entity.no_sort == (idx + 1)) {
                    no_sort = (idx + 1) + 1;
                }
                else {
                    entity["no_sort"] = (idx + 1);
                    page.detail.data[cls].update(entity);
                    no_sort = (idx + 1) + 1;
                }
                if (isSearch) {
                    if(state == App.ui.page.dataSet.status.Added){
                        maxNoMeisai = maxNoMeisai + 1;
                        entity["no_meisai"] = maxNoMeisai;
                        page.detail.data[cls].update(entity);
                        page.detail.values.maxNoMeisai[cls] = maxNoMeisai;
                    }
                }
            });
        }

        /**
        * 画面明細の一覧に、選択行の後に新しい行を挿入します。
        */
        page.detail.insertRow = function (e,newData, isFocus, isInsertAfter) {
            var element = page.detail.element,
                target = $(e.target),
                cls = target.closest(".tab").attr("data-value"),
                row,
                selected = element.find("." + cls).find(".datatable .select-tab.selected").closest("tbody");
            page.detail.values.isChange[cls] = true;
            if (!selected.length) {
                // 選択行が無ければこれまでどおり最終行に行追加
                page.detail.addNewItem(e, newData);
                return;
            }

            page.detail.dataTable[cls].dataTable("getRow", selected, function (rowObject) {
                row = rowObject.element;
            });
            var id = row.attr("data-key"),
                entity = page.detail.data[cls].entry(id),
                maxNoMeisai = page.detail.values.maxNoMeisai[cls] + 1;
            newData.no_sort = entity.no_sort + 1;
            newData.no_meisai = maxNoMeisai;
            page.detail.values.maxNoMeisai[cls] = maxNoMeisai;
            if(cls == "all_tab"){
                newData.no_bunrui = element.findP("gateAll").val();
            }else if(cls == "bacterialcontrol_tab"){
                newData.no_bunrui = element.findP("gateBacterialcontrol").val();
            }else if(cls == "priorconfirmation_tab"){
                newData.no_bunrui = element.findP("gatePriorconfirmation").val();
            }else if(cls == "validity_tab"){
                newData.no_bunrui = element.findP("gateValidity").val();
            }
            page.detail.data[cls].add(newData);
            page.detail.dataTable[cls].dataTable("insertRow", selected, isInsertAfter, function (tbody) {
                tbody.form().bind(newData);
                tbody.find(".documents").prop("disabled", true);
                return tbody;
            }, isFocus);
            page.detail.updateNosort(cls);
        };

        /**
         * 画面明細の一覧で選択されている行とデータを削除します。
         */
        page.detail.deleteItem = function (e) {
            var element = page.detail.element,
                target = $(e.target),
                cls = target.closest(".tab").attr("data-value"),
                selected = element.find("." + cls).find(".datatable .select-tab.selected").closest("tbody"),
                row, id, entity;

            if (!selected.length) {
                return;
            }

            page.detail.dataTable[cls].dataTable("getRow", selected, function (rowObject) {
                row = rowObject.element;
            });

            id = row.attr("data-key");
            entity = page.detail.data[cls].entry(id);
            if (entity.flg_master) {
                page.dialogs.confirmDialog.confirm({
                    text: App.messages.app.AP0170,
                    hideCancel: true,
                    multiModal: true,
                    backdrop: "static",
                    keyboard: false
                });
                return;
            }
            page.detail.values.isChange[cls] = true;
            page.detail.dataTable[cls].dataTable("deleteRow", selected, function (row) {
                id = row.attr("data-key");
                 var newSelected;

                row.find(":input").each(function (i, elem) {
                    App.ui.page.notifyAlert.remove(elem);
                });

                if (!App.isUndefOrNull(id)) {
                    entity = page.detail.data[cls].entry(id);
                    page.detail.data[cls].remove(entity);
                }

                newSelected = row.next().not(".item-tmpl");
                if (!newSelected.length) {
                    newSelected = row.prev().not(".item-tmpl");
                }
                if (newSelected.length) {
                    for (var i = page.detail.fixedColumnIndex[cls]; i > -1; i--) {
                        if ($(newSelected[i]).find(":focusable:first").length) {
                            $(newSelected[i]).find(":focusable:first").focus();
                            break;
                        }
                    }
                }
            });
            page.detail.updateNosort(cls);
        };

        /**
         * 画面明細のバリデーションを実行します。
         */
        page.detail.executeValidation = function (targets, row, options) {
            var element = row.element || row,
                cls = element.closest(".tab").attr("data-value")
            var defaultOptions = {
                targets: targets,
                state: {
                    tbody: row,
                    isGridValidation: true,
                    cls: cls
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
        page.detail.validateList = function (suppressMessage, cls) {
            var validations = [],
                options = {
                    state: {
                        suppressMessage: suppressMessage
                    }
                };
            if (App.isUndefOrNullOrStrEmpty(cls)) {
                var lstTab = ["all_tab", "bacterialcontrol_tab", "priorconfirmation_tab", "validity_tab"];
                $.each(lstTab, function (idx, table) {
                    page.detail.dataTable[table].dataTable("each", function (row) {
                        validations.push(page.detail.executeValidation(row.element.find(":input"), row.element, options));
                    });
                });
            } else {
                page.detail.dataTable[cls].dataTable("each", function (row) {
                    validations.push(page.detail.executeValidation(row.element.find(":input"), row.element, options));
                });
            }

            return App.async.all(validations);
        };

        /**
         * 画面明細の一覧から検索ダイアログを表示します。
         */
        page.detail.showIroSettingDialog = function (e) {
            var element = page.detail.element,
                target = $(e.target),
                tbody = target.closest("tbody"),
                cls = target.closest(".tab").attr("data-value"),
                property = target.attr("data-prop"),
                id, entity, row;
            if (target.is(":disabled")) {
                return;
            }
            page.detail.dataTable[cls].dataTable("getRow", tbody, function (rowObject) {
                row = rowObject.element;
            });

            page.dialogs.iroShiteiDialog.element.modal("show");

            //検索ダイアログでデータ選択が実行された時に呼び出される関数を設定しています。
            page.dialogs.iroShiteiDialog.setReturnColor = function (color) {
                id = row.attr("data-key");
                entity = page.detail.data[cls].entry(id);
                color = color == undefined ? "" : color;
                page.detail.values.isChange[cls] = true;
                if (property == "nm_check") {
                    entity["col_item"] = color == "" ? null : color;
                }
                else if (property == "nm_check_note1") {
                    entity["col_note1"] = color == "" ? null : color;
                } else if (property == "nm_check_note2") {
                    entity["col_note2"] = color == "" ? null : color;
                } else if (property == "nm_comment_plan") {
                    entity["col_comment_plan"] = color == "" ? null : color;
                } else if (property == "nm_comment_confirm") {
                    entity["col_comment_confirm"] = color == "" ? null : color;
                } else if (property == "nm_free") {
                    entity["col_free"] = color == "" ? null : color;
                }
                
                page.detail.data[cls].update(entity);
                //paint color
                tbody.findP(property).css("background", color);
                tbody.findP(property).css("color", page.detail.lightOrDark(color));
                delete page.dialogs.iroShiteiDialog.setReturnColor;
            }
        };

        /**
        *  show page 403
        */
        page.detail.showKanrenTenpuShiryo = function (e) {
            var element = page.detail.element,
                target = $(e.target),
                tbody = target.closest("tbody"),
                cls = target.closest(".tab").attr("data-value"),
                id, entity, row,
                url = "403_KanrenShiryo.aspx?cd_shain={0} &nen={1} &no_oi={2} &no_gate={3} &no_bunrui={4} &no_check={5} &no_meisai={6}&mode={7}&kbn_btn={8}",
                winObj;

            page.detail.dataTable[cls].dataTable("getRow", tbody, function (rowObject) {
                row = rowObject.element;
            });

            id = row.attr("data-key");
            entity = page.detail.data[cls].entry(id);
            if (target.hasClass("documents")) {
                url = App.str.format(url, 0, 0, 0, entity.no_gate, entity.no_bunrui, entity.no_check, 0, 2, 0);
                window.open(url);
            } else if (target.hasClass("attachment")) {
                url = App.str.format(url, entity.cd_shain, entity.nen, entity.no_oi, entity.no_gate, entity.no_bunrui, entity.no_check, entity.no_meisai, page.values.mode, 1);
                window.open(url);
            }
        }

        /**
        *  契約の表示を増やすには色をチェックする
        */
        page.detail.lightOrDark = function (color) {

            if (color == null || color == undefined || color == "" || !color) {
                return "";
            }
            if (color.indexOf("#") != 0) {
                return "";
            }
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
            hsp = Math.sqrt(0.299 * (r * r) + 0.587 * (g * g) + 0.114 * (b * b));
            // Using the HSP value, determine whether the color is light or dark
            if (hsp > 127.5) {
                return 'black';
            }
            else {
                return 'white';
            }
        };

        /**
         * processed before search
         */
        page.detail.reSearch = function (e) {
            var element = page.detail.element,
                target = $(e.target),
                cls = target.closest(".tab").attr("data-value"),
                property = target.attr("data-prop");
            if (page.detail.values.isChange[cls]) {
                page.dialogs.confirmDialog.confirm({
                    text: App.messages.app.AP0169,
                    backdrop: "static",
                    keyboard: false
                }).then(function () {
                    var val = element.find("." + cls).findP(property).val();
                    element.find("." + cls).findP(property).data("lastVal", val);
                    page.detail.search(e);
                    element.find("." + cls).find(".add-item").removeClass("hide");
                    element.find("." + cls).find(".del-item").removeClass("hide");
                }).fail(function () {
                    var lastVal = element.find("." + cls).findP(property).data("lastVal");
                    element.find("." + cls).findP(property).val(lastVal);
                });
            } else {
                var val = element.find("." + cls).findP(property).val();
                element.find("." + cls).findP(property).data("lastVal", val);
                page.detail.search(e);
                element.find("." + cls).find(".add-item").removeClass("hide");
                element.find("." + cls).find(".del-item").removeClass("hide");
            }
        };

        /**
         * 検索処理を定義します。
         */
        page.detail.search = function (e) {

            var deferred = $.Deferred(),
                element = page.detail.element,
                target = $(e.target),
                property = target.attr("data-prop"),
                cls = target.closest(".tab").attr("data-value");

            var param = {
                cd_shain: parseFloat(page.values.cd_shain)
                , nen: parseFloat(page.values.nen)
                , no_oi: parseFloat(page.values.no_oi)
                , no_gate: page.detail.values.noGate
                , no_bunrui: element.findP(property).val()
            };

            App.ui.loading.show();
            page.detail.dataTable[cls].dataTable("each", function (row) {
                row.element.find(":input").each(function (index, elem) {
                    if ($(elem).closest("td").hasClass("th-tmpl")) {
                        return true;
                    }
                    App.ui.page.notifyAlert.remove(elem);
                });
                
            });

            $.ajax(App.ajax.webapi.get(page.urls.search, param))
            .done(function (result) {
                page.detail.bind(result, undefined, cls);
                page.detail.updateNosort(cls,true);
                page.detail.values.isChange[cls] = false;
                deferred.resolve();
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                deferred.reject();
            }).always(function () {
                App.ui.loading.close();
            });
            return deferred.promise();
        };

        /**
         * jQuery イベントで、ページの読み込み処理を定義します。
         */
        $(function () {
            // ページの初期化処理を呼び出します。
            //page.initialize();
            page.checkHaita();
        });

    </script>
</asp:Content>
<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="content-wrap">
        <div class="header">
            <div class="part">
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>試作No</label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="text" class="overflow-ellipsis" disabled="disabled" data-prop="no_seiho" />
                    </div>
                    <div class="control-label col-xs-1">
                        <label>試作名</label>
                    </div>
                    <div class="control col-xs-6">
                        <input type="text" disabled="disabled" data-prop="nm_hin" />
                    </div>
                    <div class="control col-xs-2">
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-2 col-xs-2-cus">
                        <label>設計計画時 サンプルNo</label>
                    </div>
                    <div class="control col-xs-1 col-xs-1-cus">
                        <select data-prop="nm_sample_plan"></select>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>担当者</label>
                    </div>
                    <div class="control col-xs-2">
                        <button type="button" class="btn btn-sm btn-primary btn-header btn-approval-status tanto_plan_signature">署名</button>
                        <button type="button" class="btn btn-sm btn-primary btn-header btn-approval-status tanto_plan_release">解除</button>
                    </div>
                    <div class="control col-xs-2">
                        <label data-prop="dt_shonin_tanto_plan"></label>
                        <label data-prop="nm_shonin_tanto_plan"></label>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>担当者コメント</label>
                    </div>
                    <div class="control col-xs-3 header-textarea" style="z-index:1">
                        <textarea rows="4" data-prop="nm_comment_tanto" style="height:89px;width:98%;"></textarea>
                    </div>
                </div>
                <div class="row">
                    <div class="control col-xs-3">
                    </div>
                    <div class="control-label col-xs-1">
                        <label>リーダー</label>
                    </div>
                    <div class="control col-xs-2">
                        <button type="button" class="btn btn-sm btn-primary btn-header btn-approval-status reader_plan_signature">署名</button>
                        <button type="button" class="btn btn-sm btn-primary btn-header btn-approval-status reader_plan_release">解除</button>
                    </div>
                    <div class="control col-xs-2">
                        <label data-prop="dt_shonin_reader_plan"></label>
                        <label data-prop="nm_shonin_reader_plan"></label>
                    </div>
                    <div class="control col-xs-4">
                    </div>
                </div>
                <div class="row">
                    <div class="control col-xs-12">
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-2 col-xs-2-cus">
                        <label>品位確定時　サンプルNo</label>
                    </div>
                    <div class="control col-xs-1 col-xs-1-cus">
                        <select data-prop="nm_sample_confirm"></select>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>担当者</label>
                    </div>
                    <div class="control col-xs-2">
                        <button type="button" class="btn btn-sm btn-primary btn-header btn-approval-status tanto_confirm_signature">署名</button>
                        <button type="button" class="btn btn-sm btn-primary btn-header btn-approval-status tanto_confirm_release">解除</button>
                    </div>
                    <div class="control col-xs-2">
                        <label data-prop="dt_shonin_tanto_confirm"></label>
                        <label data-prop="nm_shonin_tanto_confirm"></label>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>リーダーコメント</label>
                    </div>
                    <div class="control col-xs-3 header-textarea" style="z-index:1">
                        <textarea rows="4" data-prop="nm_comment_reader" style="height:89px;width:98%;"></textarea>
                    </div>
                </div>
                <div class="row">
                    <div class="control col-xs-3">
                    </div>
                    <div class="control-label col-xs-1">
                        <label>リーダー</label>
                    </div>
                    <div class="control col-xs-2">
                        <button type="button" class="btn btn-sm btn-primary btn-header btn-approval-status reader_confirm_signature">署名</button>
                        <button type="button" class="btn btn-sm btn-primary btn-header btn-approval-status reader_confirm_release">解除</button>
                    </div>
                    <div class="control col-xs-2">
                        <label data-prop="dt_shonin_reader_confirm"></label>
                        <label data-prop="nm_shonin_reader_confirm"></label>
                    </div>
                    <div class="control col-xs-4">
                    </div>
                </div>
                <div class="row">
                    <div class="control col-xs-12">
                    </div>
                </div>
            </div>
        </div>
        <div class="detail">
            <div class="row" style="border-bottom: 1px solid #ccc">
                <div class="col-xs-3" style="padding-left: 0px; height: 26px;">
                    <div class="tabbable" style="margin-top: 3px">
                        <ul class="nav nav-tabs" style="height: 15px; border-bottom: #ffffff">
                            <li class="styleLi" data-for="All_Tab"><a href="#All_Tab" data-toggle="tab" id="all_tab" tabindex="-1" style="padding: 2px 10px;">全体</a></li>
                            <li class="styleLi" data-for="BacterialControl_Tab"><a href="#BacterialControl_Tab" data-toggle="tab" id="bacterialcontrol_tab" tabindex="-1" style="padding: 2px 10px;">菌制御</a></li>
                            <li class="styleLi" data-for="PriorConfirmation_Tab"><a href="#PriorConfirmation_Tab" data-toggle="tab" id="priorconfirmation_tab" tabindex="-1" style="padding: 2px 10px;">事前確認</a></li>
                            <li class="styleLi" data-for="Validity_Tab"><a href="#Validity_Tab" data-toggle="tab" id="validity_tab" tabindex="-1" style="padding: 2px 10px;">妥当性</a></li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="tab-content" id="tab-container">
                <div class="tab tab-pane all_tab" id="All_Tab" data-value="all_tab">
                    <div class="row">
                        <label>分類</label>
                        <select style="width:410px;" data-prop="gateAll"></select>
                        <button type="button" class="btn btn-xs btn-primary add-item hide">行追加</button>
                        <button type="button" class="btn btn-xs btn-primary del-item hide">行削除</button>
                    </div>
                    <table class="datatable">
                        <thead>
                            <tr>
                                <th style="width: 10px;" class="hide"></th>
                                <th style="width: 60px;">チェック分類</th>
                                <th style="width: 300px;">チェック項目</th>
                                <th style="width: 300px;">備考</th>
                                <th style="width: 35px;">関連資料</th>
                                <th style="width: 50px;">計画時</th>
                                <th style="width: 50px;">品位</br>確定時</th>
                                <th style="width: 400px;">計画時コメント</th>
                                <th style="width: 400px;">品位確定時コメント</th>
                                <th style="width: 35px;">添付資料</th>
                                <th style="width: 400px;"></th>
                            </tr>
                        </thead>
                        <tbody class="item-tmpl" style="cursor: default; display: none;">
                            <tr class="height-row-detail">
                                <td class="hide">
                                    <span class="select-tab unselected"></span>
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_check_bunrui"></textarea>
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_check"></textarea>
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_check_note1"></textarea>
                                </td>
                                <td class="center">
                                    <button class="btn btn-sm btn-primary documents shiryo">　　</button>
                                </td>
                                <td class="center">
                                    <input type="checkbox" data-prop="flg_check_plan" />
                                </td>
                                <td class="center">
                                    <input type="checkbox" data-prop="flg_check_review" />
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_comment_plan"></textarea>
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_comment_confirm"></textarea>
                                </td>
                                <td class="center">
                                    <button class="btn btn-sm btn-primary attachment temp">　　</button>
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_free"></textarea>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="tab tab-pane bacterialcontrol_tab" id="BacterialControl_Tab" data-value="bacterialcontrol_tab">
                    <div class="row">
                        <label>分類</label>
                        <select style="width:410px;" data-prop="gateBacterialcontrol"></select>
                        <button type="button" class="btn btn-xs btn-primary add-item hide">行追加</button>
                        <button type="button" class="btn btn-xs btn-primary del-item hide">行削除</button>
                    </div>
                    <table class="datatable">
                        <thead>
                            <tr>
                                <th style="width: 10px;" class="hide"></th>
                                <th style="width: 60px;">チェック分類</th>
                                <th style="width: 300px;">チェック項目１</th>
                                <th style="width: 300px;">チェック項目２</th>
                                <th style="width: 300px;">備考</th>
                                <th style="width: 35px;">関連資料</th>
                                <th style="width: 50px;">計画時</th>
                                <th style="width: 50px;">品位</br>確定時</th>
                                <th style="width: 400px;">計画時コメント</th>
                                <th style="width: 400px;">品位確定時コメント</th>
                                <th style="width: 35px;">添付資料</th>
                                <th style="width: 400px;"></th>
                            </tr>
                        </thead>
                        <tbody class="item-tmpl" style="cursor: default; display: none;">
                            <tr class="height-row-detail">
                                <td class="hide">
                                    <span class="select-tab unselected"></span>
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_check_bunrui"></textarea>
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_check"></textarea>
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_check_note1"></textarea>
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_check_note2"></textarea>
                                </td>
                                <td class="center">
                                    <button class="btn btn-sm btn-primary documents shiryo">　　</button>
                                </td>
                                <td class="center">
                                    <input type="checkbox" data-prop="flg_check_plan" />
                                </td>
                                <td class="center">
                                    <input type="checkbox" data-prop="flg_check_review" />
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_comment_plan"></textarea>
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_comment_confirm"></textarea>
                                </td>
                                <td class="center">
                                    <button class="btn btn-sm btn-primary attachment temp">　　</button>
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_free"></textarea>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="tab tab-pane priorconfirmation_tab" id="PriorConfirmation_Tab" data-value="priorconfirmation_tab">
                    <div class="row">
                        <label>分類</label>
                        <select style="width:410px;" data-prop="gatePriorconfirmation"></select>
                        <button type="button" class="btn btn-xs btn-primary add-item hide">行追加</button>
                        <button type="button" class="btn btn-xs btn-primary del-item hide">行削除</button>
                    </div>
                    <table class="datatable">
                        <thead>
                            <tr>
                                <th style="width: 10px;" class="hide"></th>
                                <th style="width: 60px;">チェック分類</th>
                                <th style="width: 300px;">チェック項目</th>
                                <th style="width: 300px;">備考／リスクの具体的な内容</th>
                                <th style="width: 300px;">リスクがある場合のアクションプラン</th>
                                <th style="width: 35px;">関連資料</th>
                                <th style="width: 50px;">計画時</th>
                                <th style="width: 50px;">品位</br>確定時</th>
                                <th style="width: 400px;">計画時コメント</th>
                                <th style="width: 400px;">品位確定時コメント</th>
                                <th style="width: 35px;">添付資料</th>
                                <th style="width: 400px;"></th>
                            </tr>
                        </thead>
                        <tbody class="item-tmpl" style="cursor: default; display: none;">
                            <tr class="height-row-detail">
                                <td class="hide">
                                    <span class="select-tab unselected"></span>
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_check_bunrui"></textarea>
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_check"></textarea>
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_check_note1"></textarea>
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_check_note2"></textarea>
                                </td>
                                <td class="center">
                                    <button class="btn btn-sm btn-primary documents shiryo">　　</button>
                                </td>
                                <td class="center">
                                    <input type="checkbox" data-prop="flg_check_plan" />
                                </td>
                                <td class="center">
                                    <input type="checkbox" data-prop="flg_check_review" />
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_comment_plan"></textarea>
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_comment_confirm"></textarea>
                                </td>
                                <td class="center">
                                    <button class="btn btn-sm btn-primary attachment temp">　　</button>
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_free"></textarea>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="tab tab-pane validity_tab" id="Validity_Tab" data-value="validity_tab">
                    <div class="row">
                        <label>分類</label>
                        <select style="width:410px;" data-prop="gateValidity"></select>
                        <button type="button" class="btn btn-xs btn-primary add-item hide">行追加</button>
                        <button type="button" class="btn btn-xs btn-primary del-item hide">行削除</button>
                    </div>
                    <table class="datatable">
                        <thead>
                            <tr>
                                <th style="width: 10px;" class="hide"></th>
                                <th style="width: 60px;">チェック分類</th>
                                <th style="width: 300px;">チェック項目</th>
                                <th style="width: 300px;">備考</th>
                                <th style="width: 35px;">関連資料</th>
                                <th style="width: 50px;">計画時</th>
                                <th style="width: 50px;">品位</br>確定時</th>
                                <th style="width: 400px;">計画時コメント</th>
                                <th style="width: 400px;">品位確定時コメント</th>
                                <th style="width: 35px;">添付資料</th>
                                <th style="width: 400px;"></th>
                            </tr>
                        </thead>
                        <tbody class="item-tmpl" style="cursor: default; display: none;">
                            <tr class="height-row-detail">
                                <td class="hide">
                                    <span class="select-tab unselected"></span>
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_check_bunrui"></textarea>
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_check"></textarea>
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_check_note1"></textarea>
                                </td>
                                <td class="center">
                                    <button class="btn btn-sm btn-primary documents shiryo">　　</button>
                                </td>
                                <td class="center">
                                    <input type="checkbox" data-prop="flg_check_plan" />
                                </td>
                                <td class="center">
                                    <input type="checkbox" data-prop="flg_check_review" />
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_comment_plan"></textarea>
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_comment_confirm"></textarea>
                                </td>
                                <td class="center">
                                    <button class="btn btn-sm btn-primary attachment temp">　　</button>
                                </td>
                                <td class="txtarea-detail">
                                    <textarea rows="3" class="layouttxtarea-detail" data-prop="nm_free"></textarea>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </div>
</asp:Content>

<asp:Content ID="FooterContent" ContentPlaceHolderID="FooterContent" runat="server">
    <div class="command-left">
        <button type="button" id="excel" class="btn btn-sm btn-primary">EXCEL</button>
    </div>
    <div class="command">
        <button type="button" id="save" class="btn btn-sm btn-primary">保存</button>
        <button type="button" id="close" class="btn btn-sm btn-primary closebtn" >閉じる</button>
    </div>
</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>
