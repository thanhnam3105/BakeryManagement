<%@ Page Title="200_製法一覧" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="200_SeihoIchiran.aspx.cs" Inherits="Tos.Web.Pages._200_SeihoIchiran" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchInputDetail(Ver2.1)】 Template--%>

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

    <style type="text/css">
        div .detail-command {
            text-align: center;
        }

        .btn-next-search {
            width: 200px;
        }
    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("_200_SeihoIchiran", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.dataTakeCount,   // TODO:取得するデータ数を指定します。
                filter: "",
                //Bug #15260 - START - 【200製法一覧】G-Label連携（権限処理）
                kengenSeiho: null,
                kengenGlabel: null
                //Bug #15260 - END- 【200製法一覧】G-Label連携（権限処理）
            },
            values: {
                isChangeRunning: {},
                isGetCodeStandard: {},
                flg_createHaigo: false,
                M_SeihoIchiran: null,
                M_SeihoBunsho: 4,
                no_seiho: null,
                no_seiho_sakusei: null,
                no_seiho_copy: null,
                kbn_hin: null,
                kbn_shikakari: null,
                su_code_standard: null,
                default_su_code_standard: 6,
                no_gamen: 200,
                //kengen
                id_kino_CSV: 10,
                id_kino_ta_haigo: 20,
                id_kino_haigo_toroku: 30,
                id_kino_haigo_haishi: 40,
                id_kino_seihin_tsuika: 50,
                id_kino_shinsei: 60,
                id_kino_shisaku_copy: 70,
                id_data: 9,
                id_kino_glabel: 10,
                id_data_glabel: 1,
                cd_kengen: 6
            },
            urls: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
                SeihoBunshoSakusei: "../Pages/300_SeihoBunshoSakusei.aspx",
                TenpuBunsho: "../Pages/211_TenpuBunsho.aspx?no_seiho={0}",
                mainMenu: "MainMenu.aspx",
                ShisakuCopyCheck: "../api/_711_ShisakuCopy_Dialog/CheckHaigoStatus"
            },
            header: {
                options: {},
                values: {},
                urls: {
                    searchHaigo: "../api/_200_SeihoIchiran/Get",
                    ma_seiho_bunrui: "../Services/ShisaQuickService.svc/ma_seiho_bunrui?$filter=cd_kaisha eq {0}M &$orderby=cd_seiho_bunrui",
                    ma_literal: "../Services/ShisaQuickService.svc/ma_literal?$filter=cd_category eq '{0}' &$orderby=cd_literal",
                    ma_kaisha: "../Services/ShisaQuickService.svc/ma_kaisha?{0}&$orderby=cd_kaisha",
                    vw_kaisha_kojyo: "../Services/ShisaQuickService.svc/vw_kaisha_kojyo?$filter=cd_kaisha eq {0}{1}&$orderby=cd_kaisha,cd_kojyo",
                    vw_no_seiho_shurui: "../Services/ShisaQuickService.svc/vw_no_seiho_shurui?$orderby=cd_hin_syurui,cd_literal",
                    ma_seihin_seiho: "../Services/ShisaQuickService.svc/ma_seihin_seiho?$filter=cd_hin eq {0}",
                    searchHin: "../api/_200_SeihoIchiran/GetHin",
                    su_code_standard: "../Services/ShisaQuickService.svc/ma_kaisha?$filter=cd_kaisha eq {0}",
                }
            },
            detail: {
                options: {
                    deleteHaigo: "../api/_200_SeihoIchiran/Post",
                    downloadCSV: "../api/_200_SeihoIchiranCSVDownload",
                    CheckExists: "../Services/ShisaQuickService.svc/ma_seiho_bunsho_hyoshi?$filter=no_seiho eq '{0}'",
                },
                values: {}
            },
            detailInput: {
                options: {},
                values: {},
                mode: {
                    input: "input",
                    edit: "edit"
                }
            },
            dialogs: {
                seihinKensakuDialog: "../Pages/Dialogs/703_SeihinKensaku_Dialog.aspx",
                HinmeiKaihatsuDialog: "../Pages/Dialogs/710_HinmeiKaihatsu_Dialog.aspx",
                CopyHaniSelectionDialog: "../Pages/Dialogs/802_CopyHaniSelection_Dialog.aspx",
                ShikakarihinSentakuDialog: "../Pages/Dialogs/805_ShikakarihinSentaku_Dialog.aspx",
            },
            commands: {}
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

        /**
         * 編集明細の一覧に新しい行を追加します。
         */
        page.commands.addItem = function () {

            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            App.ui.loading.show();

            var sleep = 0;
            var condition = "Object.keys(page.values.isChangeRunning).length == 0";
            App.ui.wait(sleep, condition, 100)
            .then(function () {
                page.validateAll().then(function () {

                    var changeSets = page.detailInput.data.getChangeSet();
                    //TODO: データの保存処理をここに記述します。
                    return $.ajax(App.ajax.webapi.post(/* TODO: データ保存サービスの URL を設定してください。, */  changeSets))
                        .then(function (result) {

                            //TODO: データの保存成功時の処理をここに記述します。
                            page.detailInput.previous(false);

                            //TODO: 新規行追加後に一覧再検索をする場合は、下記2行のコメントを外します
                            //    return App.async.all([page.header.search(false)]);
                            //}).then(function () {
                            App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                        }).fail(function (error) {

                            if (error.status === App.settings.base.conflictStatus) {
                                // TODO: 同時実行エラー時の処理を行っています。
                                // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                                page.header.search(false);
                                App.ui.page.notifyAlert.clear();
                                App.ui.page.notifyAlert.message(App.messages.base.MS0009).show();
                                return;
                            }

                            //TODO: データの保存失敗時の処理をここに記述します。
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
                });
            }).fail(function () {
                App.ui.page.notifyAlert.message(App.messages.base.MS0006).show();
            }).always(function () {
                setTimeout(function () {
                    page.header.element.find(":input:first").focus();
                }, 100);
                App.ui.loading.close();
            });
        };

        /**
        * 画面明細の対象行を更新します。
        */
        page.commands.editItem = function () {

            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            App.ui.loading.show();

            var sleep = 0;
            var condition = "Object.keys(page.values.isChangeRunning).length == 0";
            App.ui.wait(sleep, condition, 100)
            .then(function () {
                page.validateAll().then(function () {

                    var changeSets = page.detailInput.data.getChangeSet();
                    //TODO: データの更新処理をここに記述します。
                    return $.ajax(App.ajax.webapi.put(/* TODO: データ更新サービスの URL を設定してください。, */  changeSets))
                        .then(function (result) {

                            //TODO: データの保存成功時の処理をここに記述します。
                            return page.detail.updateData(changeSets);
                        }).then(function (result) {

                            page.detailInput.previous(false);
                            App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                        }).fail(function (error) {

                            if (error.status === App.settings.base.conflictStatus) {
                                // TODO: 同時実行エラー時の処理を行っています。
                                // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                                page.header.search(false);
                                App.ui.page.notifyAlert.clear();
                                App.ui.page.notifyAlert.message(App.messages.base.MS0009).show();
                                return;
                            }

                            //TODO: データの保存失敗時の処理をここに記述します。
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
                });
            }).fail(function () {
                App.ui.page.notifyAlert.message(App.messages.base.MS0006).show();
            }).always(function () {
                setTimeout(function () {
                    page.header.element.find(":input:first").focus();
                }, 100);
                App.ui.loading.close();
            });

        };

        /**
         * 画面明細の対象行を削除します。
         */
        page.commands.deleteItem = function () {

            var options = {
                text: App.messages.base.MS0003
            };

            page.dialogs.confirmDialog.confirm(options)
            .then(function () {

                App.ui.page.notifyWarn.clear();
                App.ui.page.notifyAlert.clear();
                App.ui.page.notifyInfo.clear();

                App.ui.loading.show();

                var element = page.detailInput.element,
                    id = element.attr("data-key"),
                    entity = page.detailInput.data.entry(id),
                    changeSets;

                page.detailInput.data.remove(entity);
                changeSets = page.detailInput.data.getChangeSet();

                //TODO: データの更新処理をここに記述します。
                $.ajax(App.ajax.webapi["delete"](/* TODO: データ更新サービスの URL を設定してください。, */  changeSets))
                .then(function (result) {

                    //TODO: データの保存成功時の処理をここに記述します。
                    page.detail.removeData(changeSets);
                    page.detailInput.previous(false);

                    App.ui.page.notifyInfo.message(App.messages.base.MS0008).show();
                }).fail(function (error) {

                    //TODO: データの保存失敗時の処理をここに記述します。
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

                }).always(function () {
                    setTimeout(function () {
                        page.header.element.find(":input:first").focus();
                    }, 100);
                    App.ui.loading.close();
                });
            });
        };

        /**
         * すべてのバリデーションを実行します。
         */
        page.validateAll = function () {

            var validations = [];

            validations.push(page.detailInput.validator.validate());

            //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。

            return App.async.all(validations);
        };

        /**
         * Windows閉じる際のイベントを定義します。
         * @return 文字列を返却した場合に確認メッセージが表示されます。
         */
        App.ui.page.onclose = function () {

            var detailInput,
                closeMessage = App.messages.base.exit;

            if (page.detailInput.data) {
                detailInput = page.detailInput.data.getChangeSet();
                if (detailInput.created.length || detailInput.updated.length || detailInput.deleted.length) {
                    return closeMessage;
                }
            }

        };

        /**
        * 画面の初期化処理を行います。
        */
        page.initialize = function () {

            App.ui.loading.show();
            $(".wrap, .footer").addClass("theme-yellow");

            //TODO: URLパラメーターで渡された値を取得し、保持する処理を記述します。
            if (!App.isUndefOrNull(App.uri.queryStrings.M_SeihoIchiran)) {
                page.values.M_SeihoIchiran = parseFloat(App.uri.queryStrings.M_SeihoIchiran);
            }
            else {
                page.values.M_SeihoIchiran = App.settings.app.m_seiho_ichiran.search;
            }
            if (!App.isUndefOrNull(App.uri.queryStrings.no_seiho)) {
                page.values.no_seiho = App.uri.queryStrings.no_seiho;
            } else {
                page.values.no_seiho = null;
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
            page.initializeControl();
            page.initializeControlEvent();

            page.header.initialize();
            page.detail.initialize();
            page.detailInput.initialize();
            //TODO: ヘッダー/明細以外の初期化の処理を記述します。

            //Bug #15260 - START - 【200製法一覧】G-Label連携（権限処理）
            getKengenGamen(App.settings.app.id_gamen.seiho_ichiran_glabel).then(function (kengenGlabel) {

                page.options.kengenGlabel = kengenGlabel;
                return getKengenGamen(App.settings.app.id_gamen.seiho_ichiran);

            }).then(function (kengenSeiho) {

                page.options.kengenSeiho = kengenSeiho;

                return page.loadMasterData();

                //Bug #15260 - END - 【200製法一覧】G-Label連携（権限処理）

            }).then(function (result) {

                return page.loadDialogs();

            }).then(function (result) {
                //TODO: 画面の初期化処理成功時の処理を記述します。
                $.each(page.options.kengenSeiho, function (index, item) {
                    if (item.id_data == page.values.id_data) {
                        switch (item.id_kino) {
                            case page.values.id_kino_CSV:
                                $("#CSV").prop('disabled', false);
                                break;
                            case page.values.id_kino_ta_haigo:
                                $(".edit-select").prop('disabled', false);
                                break;
                            case page.values.id_kino_haigo_toroku:
                                page.values.flg_createHaigo = true;
                                $(".edit-select").prop("disabled", false);
                                $("#add-item").prop('disabled', false);
                                //$("#203_HaigoTorokuKaihatsuBumon_Copy").prop('disabled', false);
                                //$("#203_HaigoTorokuKaihatsuBumon_Renew").prop('disabled', false);
                                //$("#203_HaigoTorokuKaihatsuBumon_Create").prop('disabled', false);
                                //$("#deleteHaigo").prop('disabled', false);
                                //$("#211_").prop('disabled', false);
                                break;
                            case page.values.id_kino_shisaku_copy:
                                $(".shisaku-copy").prop('disabled', false);
                                break;
                            case page.values.id_kino_haigo_haishi:
                                $(".edit-select").prop('disabled', false);
                                break;
                            case page.values.id_kino_seihin_tsuika:
                                break;
                            case page.values.id_kino_shinsei:
                                //$("#300_SeihoBunshoSakusei").prop('disabled', false);
                                break;
                            default:
                                var options = {
                                    text: App.messages.app.AP0089
                                };

                                page.dialogs.confirmDialog.confirm(options).always(function () {
                                    window.location.href = page.urls.mainMenu;
                                });
                        }

                    }
                    else {
                        var options = {
                            text: App.messages.app.AP0089
                        };

                        page.dialogs.confirmDialog.confirm(options).always(function () {
                            window.location.href = page.urls.mainMenu;
                        });
                    }

                });

                //});
                return page.loadData();

            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

            }).always(function (result) {
                page.header.def(true);
                page.header.element.find(":input:first").focus();
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
            $("#addItem").on("click", page.commands.addItem);
            $("#editItem").on("click", page.commands.editItem);
            $("#deleteItem").on("click", page.commands.deleteItem);

            $("#reset").on("click", page.detail.resetHeader);

            $("#CSV").on("click", page.detail.DownloadCSV);

            $("#300_SeihoBunshoSakusei").on("click", page.detail.openSeihoBunshoSakusei);

            $("#203_HaigoTorokuKaihatsuBumon_Copy").on("click", page.detail.HaigoTorokuKaihatsuBumon_Copy);

            $("#203_HaigoTorokuKaihatsuBumon_Renew").on("click", page.detail.HaigoTorokuKaihatsuBumon_Renew);

            $("#203_HaigoTorokuKaihatsuBumon_Create").on("click", page.detail.HaigoTorokuKaihatsuBumon_Create);

            $("#deleteHaigo").on("click", page.detail.deleteHaigo);

            $("#202_G_LabelRenkei").on("click", page.detail._202_G_LabelRenkei);

            $("#211_").on("click", page.commands._211_);

            $("#802_CopyHaniSelection_Dialog").on("click", page.detail.openCopyHaniSelectionDialog);

            $("#shisaku-copy").on("click", page.detail.shisakuCopy);

        };

        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function () {
            var element = page.header.element;
            //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。

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

                    return $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.ma_seiho_bunrui, App.ui.page.user.cd_kaisha)))
                }).then(function (result) {
                    var cd_seiho_bunrui = page.header.element.findP("cd_seiho_bunrui");
                    cd_seiho_bunrui.children().remove();
                    App.ui.appendOptions(
                        cd_seiho_bunrui,
                        "cd_seiho_bunrui",
                        "nm_seiho_bunrui",
                        result.value,
                        true
                    );

                    return $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.ma_literal, App.settings.app.cd_category.kbn_status)))
                }).then(function (result) {
                    var status = page.header.element.findP("status");
                    status.children().remove();
                    App.ui.appendOptions(
                        status,
                        "cd_literal",
                        "nm_literal",
                        result.value,
                        true
                    );
                        return page.kaishaKojoJotai(page.header.urls.ma_kaisha, "nm_kaisha", "cd_kaisha", "nm_kaisha", "", false).then(function () {
                            var cd_kaisha = element.findP("nm_kaisha").val();
                            //element.findP("nm_kaisha").val(App.ui.page.user.cd_kaisha); //TOsVN [19075 - 2019/09/17 Request #15254
                            cd_kaisha = App.ui.page.user.cd_kaisha;
                            if (!App.ui.page.user.flg_kaishakan_sansyo) {
                                element.find("[data-prop='cd_kaisha'], [data-prop='nm_kaisha']").prop("disabled", true);
                            }

                            if (!App.isUndefOrNullOrStrEmpty(cd_kaisha)) {
                                //element.findP("cd_kaisha").val(App.common.getFullString(cd_kaisha, "0000")); //TOsVN [19075 - 2019/09/17 Request #15254
                                element.findP("cd_kaisha_validate").text(cd_kaisha);


                                page.kaishaKojoJotai(page.header.urls.vw_kaisha_kojyo, "nm_kojyo", "cd_kojyo", "nm_kojyo", cd_kaisha, true).then(function () {
                                    var cd_kojyo = element.findP("nm_kojyo").val();
                                    //element.findP("nm_kojyo").val(App.ui.page.user.cd_busho); //TOsVN [19075 - 2019/09/17 Request #15254
                                    cd_kojyo = App.ui.page.user.cd_busho;
                                    if (!App.ui.page.user.flg_kojyokan_sansyo) {
                                        element.find("[data-prop='cd_kojyo'], [data-prop='nm_kojyo']").prop("disabled", true);
                                    }

                                    if (!App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                                        //element.findP("cd_kojyo").val(App.common.getFullString(cd_kojyo, "0000")); //TOsVN [19075 - 2019/09/17 Request #15254
                                        element.findP("cd_kojyo_validate").text(cd_kojyo);
                                    }
                                });
                            }
                        });
                });
            //TODO: マスタデータのロード処理を実装後、下の1行を削除してください。
            return App.async.success();
        };


        /**
         * Load combobox 製造会社, 製造工場, 状態。
         */
        page.kaishaKojoJotai = function (url, dataPropElement, codeProps, nameProps, param, blank) {

            //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
            return $.ajax(App.ajax.odata.get(App.str.format(url, param))).then(function (result) {
                var element = page.header.element.findP(dataPropElement);                
                    element.children().remove();
                    App.ui.appendOptions(
                        element,
                        codeProps,
                        nameProps,
                        result.value,
                        blank 
                    );
                    element.val("");//TOsVN [19075 - 2019/09/17 Request #15254

            });
        };


        /**
         * 共有ダイアログのロード処理を実行します。
         */
        page.loadDialogs = function () {

            //TODO: 共有ダイアログデータを取得し、画面にバインドする処理を記述します。
            return App.async.all({

                confirmDialog: $.get(page.urls.confirmDialog),
                seihinKensakuDialog: $.get(page.dialogs.seihinKensakuDialog),
                HinmeiKaihatsuDialog: $.get(page.dialogs.HinmeiKaihatsuDialog),
                CopyHaniSelectionDialog: $.get(page.dialogs.CopyHaniSelectionDialog),
                ShikakarihinSentakuDialog: $.get(page.dialogs.ShikakarihinSentakuDialog),
                //searchDialog: $.get(/* TODO:共有ダイアログの URL */),

            }).then(function (result) {
                //if (page.values.M_SeihoIchiran == App.settings.app.m_seiho_ichiran.search) {
                $("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;
                $("#dialog-container").append(result.successes.seihinKensakuDialog);
                page.dialogs.seihinKensakuDialog = SeihinKensakuDialog;
                page.dialogs.seihinKensakuDialog.param.no_gamen = App.settings.app.no_gamen.seiho_ichiran;
                page.dialogs.seihinKensakuDialog.initialize();

                $("#dialog-container").append(result.successes.HinmeiKaihatsuDialog);
                page.dialogs.HinmeiKaihatsuDialog = _710_HinmeiKaihatsu_Dialog;
                page.dialogs.HinmeiKaihatsuDialog.param.no_gamen = App.settings.app.no_gamen.seiho_ichiran;
                page.dialogs.HinmeiKaihatsuDialog.initialize();
                //}
                //else if (page.values.M_SeihoIchiran == App.settings.app.m_seiho_ichiran.copy) {
                $("#dialog-container").append(result.successes.CopyHaniSelectionDialog);
                page.dialogs.CopyHaniSelectionDialog = _802_CopyHaniSelection_Dialog;
                page.dialogs.CopyHaniSelectionDialog.initialize();

                $("#dialog-container").append(result.successes.ShikakarihinSentakuDialog);
                page.dialogs.ShikakarihinSentakuDialog = _805_ShikakarihinSentaku_Dialog;
                page.dialogs.ShikakarihinSentakuDialog.initialize();
                //}
            });
        };

        page.SetSuCodeStandard = function (cd_kaisha) {
            $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.su_code_standard, parseInt(cd_kaisha))))
                   .then(function (result) {
                       if (result && result.value.length > 0) {
                           page.values.su_code_standard = result.value[0].su_code_standard;
                           page.header.options.validations.cd_hin.rules.maxlength = Number(page.values.su_code_standard + 1);
                           page.header.element.findP("cd_hin").prop("maxlength", Number(page.values.su_code_standard + 1));
                           page.SetupComplete();
                       }
                   });
        }

        page.SetFormatHinmei = function (data) {
            var cd_hin;
            if (data.kbn_hin == App.settings.app.kbnHin.shikakari && data.kbn_shikakari == App.settings.app.kbn_shikakari.kaihatsu) {
                num = page.values.default_su_code_standard;
            }
            else if (data.kbn_hin != App.settings.app.kbnHin.genryo && data.kbn_hin != App.settings.app.kbnHin.jikaGenryo) {
                num = page.values.su_code_standard + 1;
            }
            else {
                num = page.values.su_code_standard;
            }
            page.header.options.validations.cd_hin.rules.maxlength = Number(num);
            page.header.element.findP("cd_hin").prop("maxlength", Number(num));
            cd_hin = App.common.fillString(data.cd_hin, num, "0");

            return cd_hin;
        }
        page.SetupComplete = function () {
            var element = page.header.element,
                defchoose = element.findP("def").is(":checked");
            element.findP("cd_hin").unbind("change");
            element.findP("cd_hin").data("lastVal", "");
            element.findP("cd_hin").complete({
                textLength: (page.values.su_code_standard + 1),
                ajax: function (val) {
                    App.ui.loading.show();
                    var target = page.header.element.findP("cd_kojyo"),
                        kojyo = target.val(),
                        kaisha = page.header.element.findP("cd_kaisha").val();

                    if ((kojyo == null || kojyo == "") && defchoose) {
                        var target = page.header.element.findP("cd_kojyo");
                        page.header.validator.validate({
                            targets: target
                        });
                    }
                    var param = {
                        cd_kaisha: parseInt(kaisha),
                        cd_kojyo: parseInt(kojyo),
                        cd_hin: parseInt(val),
                        no_seiho_kaisha: parseInt(page.header.element.findP("no_seiho_kaisha").val() != ""
                            ? page.header.element.findP("no_seiho_kaisha").val() : App.ui.page.user.cd_kaisha)
                    };
                    return $.ajax(App.ajax.webapi.get(page.header.urls.searchHin, param));
                },
                success: page.header.completeHin,
                error: page.header.clearHin,
                clear: page.header.clearHin
            });
        }

        /**
         * 画面で処理の対象とするデータのロード処理を実行します。
         */
        page.loadData = function () {

            page.header.element.findP("no_seiho_kaisha").val(("000" + App.ui.page.user.cd_kaisha).slice(-4));
            page.header.element.findP("no_seiho_nen").val(new Date().getFullYear().toString().substr(2, 2));

            var cd_kaisha_user = ("000" + App.ui.page.user.cd_kaisha).slice(-4),
                cd_busho_user = ("000" + App.ui.page.user.cd_busho).slice(-4);

            page.SetSuCodeStandard(App.ui.page.user.cd_kaisha);
            //$.ajax(App.ajax.odata.get(App.str.format(page.header.urls.su_code_standard, parseInt(cd_kaisha_user))))
            //        .then(function (result) {
            //            page.values.su_code_standard = result.value[0].su_code_standard;
            //            page.header.options.validations.cd_hin.rules.maxlength = Number(page.values.su_code_standard);
            //            page.header.element.findP("cd_hin").prop("maxlength", Number(page.values.su_code_standard));
            //        })

            if (page.values.M_SeihoIchiran == App.settings.app.m_seiho_ichiran.search) {
                $("#add-item").show();
                $("#reset").show();
                $("#CSV").show();
                $("#shisaku-copy").show();
                $("#300_SeihoBunshoSakusei").show();
                $("#203_HaigoTorokuKaihatsuBumon_Copy").show();
                $("#203_HaigoTorokuKaihatsuBumon_Renew").show();
                $("#203_HaigoTorokuKaihatsuBumon_Create").show();
                $("#deleteHaigo").show();
                $("#202_G_LabelRenkei").show();
                $("#211_").show();
            }
            else if (page.values.M_SeihoIchiran == App.settings.app.m_seiho_ichiran.copy) {
                $("#802_CopyHaniSelection_Dialog").show();
            }

            //Bug #15260 - START - 【200製法一覧】G-Label連携（権限処理）
            var isHideGlabel = true
            $.each(page.options.kengenGlabel, function (index, item) {
                if (item.id_kino == page.values.id_kino_glabel && (item.id_data == page.values.id_data || item.id_data == page.values.id_data_glabel)) {
                    isHideGlabel = false;
                    return false;
                }
            });

            if (isHideGlabel) {
                $("#202_G_LabelRenkei").hide();
            }
            //Bug #15260 - END - 【200製法一覧】G-Label連携（権限処理）
        };

        page.header.openSeihinKensakuDialog = function () {
            page.dialogs.seihinKensakuDialog.element.modal("show");
            page.dialogs.seihinKensakuDialog.dataSelected = function (data) {
                page.header.element.findP("cd_seihin").val(data.cd_seihin).change();
                page.header.element.findP("label_seihin").text(data.nm_seihin);
            }
        }

        page.header.openHinmeiKaihatsuDialog = function (e) {
            var element = page.header.element;
            var kojyo = element.findP("cd_kojyo").val(),
                kaisha = element.findP("cd_kaisha").val(),
                cd_hin,
                num;

            if (kojyo == null || kojyo == "") {
                var target = element.findP("cd_kojyo");
                page.header.validator.validate({
                    targets: target,
                    state: { mode: 1 }
                });
                return;
            }

            page.dialogs.HinmeiKaihatsuDialog.param.cd_kaisha = kaisha;
            page.dialogs.HinmeiKaihatsuDialog.param.cd_kojyo = kojyo;

            page.dialogs.HinmeiKaihatsuDialog.element.modal("show");
            page.dialogs.HinmeiKaihatsuDialog.dataSelected = function (data) {
                cd_hin = page.SetFormatHinmei(data);
                element.findP("cd_hin").val(cd_hin);
                element.findP("label_nmhin").text(data.nm_hin);
                element.findP("label_nmhin").attr("title", data.nm_hin);
                page.values.kbn_hin = data.kbn_hin,
                page.values.kbn_shikakari = data.kbn_shikakari;
                page.header.validator.validate({
                    targets: element.findP("cd_hin")
                });

                element.findP("cd_hin").data()["lastVal"] = undefined;
            }
        }


        page.detail.openCopyHaniSelectionDialog = function (e) {
            var selected = page.detail.getSelected(e);

            if (selected != undefined) {
                page.dialogs.CopyHaniSelectionDialog.param.no_seiho = page.values.no_seiho;
                page.dialogs.CopyHaniSelectionDialog.param.no_seiho_copy = selected.no_seiho;
                page.dialogs.CopyHaniSelectionDialog.param.no_seiho_sakusei = page.values.no_seiho_sakusei;
                page.dialogs.CopyHaniSelectionDialog.element.modal("show");
            }
        }

        page.detail.shisakuCopy = function (e) {
            var selected = page.detail.getSelected(e);
            App.ui.page.notifyAlert.remove('200_AP0189');
            App.ui.page.notifyAlert.remove('200_AP0008');

            if (selected != undefined) {
                var cd_haigo = selected.cd_haigo,
                    no_seiho = selected.no_seiho,
                    cd_kaisha_daihyo = selected.cd_kaisha_daihyo,
                    cd_kojyo_daihyo = selected.cd_kojyo_daihyo;

                var param = {
                    cd_haigo: cd_haigo,
                    status_densosumi: App.settings.app.kbn_status.densosumi,
                    status_kojokakunin: App.settings.app.kbn_status.kojokakunin,
                    kbnhin_haigo: App.settings.app.kbnHin.haigo,
                    kbnhin_shikakari: App.settings.app.kbnHin.shikakari
                }

                return $.ajax(App.ajax.webapi.get(page.urls.ShisakuCopyCheck, param)).then(function (result) {
                    if (result && result > 0) {
                        App.ui.page.notifyAlert.message(App.messages.app.AP0189, '200_AP0189').show();
                    } else {
                        var link = "203_HaigoTorokuKaihatsuBumon.aspx?M_HaigoTorokuKaihatsu=" + App.settings.app.m_haigo_toroku_kaihatsu.shisaku_copy
                            + "&cd_haigo=" + cd_haigo + "&no_seiho=" + no_seiho;
                        window.open(link);
                    }
                });
            } else {
                //App.ui.page.notifyAlert.message(App.messages.app.AP0008, '200_AP0008').show();
            }

        }
        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.validations = {
            //TODO: 画面ヘッダーのバリデーションの定義を記述します。
            no_seiho_kaisha: {
                rules: {
                    digits: true,
                    maxlength: 4,
                    hissu: function (values, opts, state, done) {
                        var nen = page.header.element.findP("no_seiho_nen").val();
                        if (values == "" && nen == "")
                            return done(false);
                        return done(true);
                    }
                },
                options: {
                    name: "製法番号(会社コード)"
                },
                messages: {
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength,
                    hissu: App.messages.base.MS0025
                }
            },

            no_seiho_nen: {
                rules: {
                    digits: true,
                    maxlength: 2
                },
                options: {
                    name: "製法番号 (年)"
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
                    name: "製法番号 (シーケンス番号)"
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
                    byte: 60
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },

            dt_seiho_sakusei_start: {
                rules: {
                    datestring: true,
                    maxlength: 10,
                    startLessEnd: function (values, opts, state, done) {
                        var dt_end = page.header.element.findP("dt_seiho_sakusei_end").val();
                        if (values > dt_end && (values != "" && dt_end != ""))
                            return done(false);
                        return done(true);
                    }
                },
                options: {
                    name: "製法作成日(開始日)",
                },
                messages: {
                    datestring: App.messages.base.datestring,
                    maxlength: App.messages.base.maxlength,
                    startLessEnd: App.messages.base.MS0014
                }
            },
            dt_seiho_sakusei_end: {
                rules: {
                    datestring: true,
                    maxlength: 10,
                },
                options: {
                    name: "製法作成日(終了日)",
                },
                messages: {
                    datestring: App.messages.base.datestring,
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_seiho_sakusei: {
                rules: {
                    maxbytelength: 20
                },
                options: {
                    name: "製法作成者１・２",
                    byte: 10
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            cd_seihin: {
                rules: {
                    maxlength: 6,
                    digits: true,
                    requiredname: function (value, opts, state, done) {
                        var element = page.header.element;

                        if (App.isUndefOrNullOrStrEmpty(value)) {
                            return done(true);
                        }

                        return done(element.findP("label_seihin").text() != "")
                    }
                },
                options: {
                    name: "製品コード",
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits,
                    requiredname: App.messages.app.AP0010
                }
            },
            nm_seihin: {
                rules: {
                    maxbytelength: 60
                },
                options: {
                    name: "製品名",
                    byte: 30
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            nm_haigo: {
                rules: {
                    maxbytelength: 60
                },
                options: {
                    name: "配合名/コード",
                    byte: 30
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            cd_kaisha: {
                rules: {
                    requiredCuston: function (value, opts, state, done) {
                        var element = page.header.element;
                        var chooseOther = element.findP("choose").is(":checked");
                        var cd_kaisha = element.findP("cd_kaisha").val();
                        if (chooseOther && App.isUndefOrNullOrStrEmpty(cd_kaisha)) {
                            return done(false);
                        }
                        return done(true);
                    },
                    maxlength: 4,
                    digits: true,
                    existsCode: function (value, opts, state, done) {
                        var element = page.header.element;
                        var cd_kaisha = element.findP("cd_kaisha").val();
                        var cd_kaisha_validate = element.findP("cd_kaisha_validate").text();
                        if (App.isUndefOrNullOrStrEmpty(cd_kaisha)) {
                            return done(true);
                        }
                        return done(cd_kaisha_validate != "");
                    }
                },
                options: {
                    name: "会社コード"
                },
                messages: {
                    requiredCuston: App.messages.base.required,
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits,
                    existsCode: App.messages.app.AP0010
                }
            },
            cd_kojyo: {
                rules: {
                    maxlengthCustom: function (value, opts, state, done) {
                        var element = page.header.element,
                            chooseOther = element.findP("choose").is(":checked");

                        if (App.isUndefOrNullOrStrEmpty(value) || !chooseOther) {
                            return done(true);
                        }

                        return done(value.length <= 4);
                    },
                    digitsCustom: function (value, opts, state, done) {
                        var element = page.header.element,
                            chooseOther = element.findP("choose").is(":checked");

                        if (App.isUndefOrNullOrStrEmpty(value) || !chooseOther) {
                            return done(true);
                        }

                        return done(/^\d+$/.test(value));
                    },
                    existsCode: function (value, opts, state, done) {
                        var element = page.header.element,
                            chooseOther = element.findP("choose").is(":checked");
                        var cd_kojyo_validate = element.findP("cd_kojyo_validate").text();
                        
                        if (App.isUndefOrNullOrStrEmpty(value) || !chooseOther) {
                            return done(true);
                        }

                        return done(cd_kojyo_validate != "");
                    },
                    requiredKojo: function (value, opts, state, done) {
                        var element = page.header.element,
                            chooseOther = element.findP("choose").is(":checked");

                        if (!chooseOther) {
                            return done(true);
                        }

                        if (!App.isUndefOrNullOrStrEmpty(element.findP("cd_hin").val())
                            && App.isUndefOrNullOrStrEmpty(value) && chooseOther) {
                            return done(false);
                        }
                        if (state != null) {
                            if (state.mode == 1) {
                                return done(false);
                            }
                        }

                        return done(true);
                    }
                },
                options: {
                    name: "代表工場"
                },
                messages: {
                    maxlengthCustom: App.messages.base.maxlength,
                    digitsCustom: App.messages.base.digits,
                    existsCode: App.messages.app.AP0010,
                    requiredKojo: App.messages.app.AP0048,
                }
            },
            nm_seizo: {
                rules: {
                    maxbytelength: 80
                },
                options: {
                    name: "製造会社名",
                    byte: 40
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            cd_hin: {
                rules: {
                    maxlength: 7,
                    digits: true,
                    requiredname: function (value, opts, state, done) {
                        var element = page.header.element;

                        if (App.isUndefOrNullOrStrEmpty(value)) {
                            return done(true);
                        }
                        if (element.findP("label_nmhin").text() == "" && element.findP("cd_kojyo").val() == "") {
                            return done(true);
                        }

                        return done(element.findP("label_nmhin").text() != "");
                    }
                },
                options: {
                    name: "原料コード",
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits,
                    requiredname: App.messages.app.AP0010
                }
            },
            no_kikaku1: {
                rules: {
                    maxlength: 4,
                    digits: true
                },
                options: {
                    name: "規格書No.(会社コード)",
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits
                }
            },
            no_kikaku2: {
                rules: {
                    maxlength: 6,
                    digits: true
                },
                options: {
                    name: "規格書No.(店コード)",
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits
                }
            },
            no_kikaku3: {
                rules: {
                    maxlength: 5,
                    digits: true
                },
                options: {
                    name: "規格書No.(シーケンス)",
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits
                }
            },
            nm_hanbai: {
                rules: {
                    maxbytelength: 80
                },
                options: {
                    name: "販売会社名",
                    byte: 40
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            nm_hin: {
                rules: {
                    maxbytelength: 60
                },
                options: {
                    name: "原料名",
                    byte: 30
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            nm_kikaku: {
                rules: {
                    maxbytelength: 80
                },
                options: {
                    name: "規格書名",
                    byte: 40
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },

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
            element.find(":input[data-role='date']").on("keyup", App.data.addSlashForDateString);
            element.on("click", "#search", page.header.search);
            element.on("change", ":input", page.header.change);
            element.find("input[data-role='date']").datepicker();
            element.on("click", "#open-seihinKensaku", page.header.openSeihinKensakuDialog);
            element.on("click", "#open-hinmeiKaihatsu", page.header.openHinmeiKaihatsuDialog);
            element.findP("cd_seihin").complete({
                textLength: 6,
                ajax: function (val) {
                    return $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.ma_seihin_seiho, parseInt(val, 10))));
                },
                success: page.header.completeSeihin,
                error: page.header.clearSeihin,
                clear: page.header.clearSeihin
            });
        };

        /**
         * 画面明細のバリデーションフィルターを設定します。（必須チェックを行わない）
         */
        page.header.validationFilter = function (item, method, state, options) {
            return method !== "requiredname" && method != "requiredKojo";
        };

        // Check value is number
        page.header.isNumber = function (item) {
            return (parseInt(item) + '') === item;
        }

        /**
         * 画面ヘッダーの変更時処理を定義します。
         */
        page.header.change = function (e) {
            if ($("#nextsearch").hasClass("show-search")) {
                $("#nextsearch").removeClass("show-search").hide();
                App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
            }
            else if (page.detail.searchData) {
                // 保持検索データの消去
                page.detail.searchData = undefined;
                App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
            }

            var element = page.header.element,
            target = $(e.target),
            property = target.attr("data-prop"),
            valOld = target.val();
            var validateTarget = target;

            if (property == "cd_hin") {
                page.SetSuCodeStandard(page.header.element.findP("cd_kaisha").val());
                if (page.header.isNumber(valOld)) {
                    valNew = App.common.fillString(valOld, page.values.su_code_standard + 1, "0");
                    target.val(valNew);
                }
                if (valOld != "") {
                    return;
                }
            }

            if (property == "dt_seiho_sakusei_end") {
                validateTarget = page.header.element.find("[data-prop='dt_seiho_sakusei_end'], [data-prop='dt_seiho_sakusei_start']")
            }

            if (property == "no_seiho_nen") {
                valNew = App.common.getFullSeihoNen(valOld);
                target.val(valNew);
            }
            if (property == "no_kikaku2") {
                valNew = App.common.getFullString(valOld, "000000");
                target.val(valNew);
            }
            if (property == "cd_seihin") {
                valNew = App.common.getFullString(valOld, "000000");
                target.val(valNew);
                return;
            }
            if (property == "no_kikaku3") {
                valNew = App.common.getFullString(valOld, "00000");
                target.val(valNew);
            }

            if (property == "no_seiho_renban" || property == "no_seiho_kaisha" || property == "no_kikaku1") {
                valNew = App.common.getFullCdKaisha(valOld);
                target.val(valNew);
            }
            if (property == "def") {
                page.header.def();
            }
            if (property == "choose") {
                page.header.choose();
            }

            if (property == "cd_kaisha" || property == "nm_kaisha") {
                element.findP("cd_hin").val("").change(); //TOsVN [19075 - 2019/09/17] Request #15254
                element.findP("label_nmhin").text("");
                var cd_kaisha = target.val();
                page.SetSuCodeStandard(cd_kaisha);
                element.findP("cd_kaisha").val(App.common.getFullString(cd_kaisha, "0000"));
                if (App.ui.page.user.flg_kojyokan_sansyo) {
                    element.findP("nm_kojyo").children().remove();
                    element.findP("cd_kojyo").val("");
                }

                if (property == "nm_kaisha") {
                    element.findP("cd_kaisha").val(App.common.getFullString(cd_kaisha, "0000"));
                    element.findP("cd_kaisha_validate").text(cd_kaisha);
                    element.findP("nm_kaisha").val(cd_kaisha);

                }
                page.values.isResetKojyo = true;
                if (property == "cd_kaisha") {
                    if (App.isUndefOrNullOrStrEmpty(cd_kaisha)) {
                        element.findP("nm_kaisha").val(cd_kaisha);
                    }
                    if (cd_kaisha != "" && App.isNumeric(cd_kaisha)) {
                        cd_kaisha = parseInt(cd_kaisha);

                        page.checkData(App.str.format(page.header.urls.ma_kaisha, "$filter=cd_kaisha eq {0}"), cd_kaisha, "",
                                        element, "cd_kaisha", "cd_kaisha_validate", "nm_kaisha", cd_kaisha);
                    }
                }
                if (property == "nm_kaisha" || page.values.isResetKojyo) {
                    if (cd_kaisha != "" && App.isNumeric(cd_kaisha) && App.ui.page.user.flg_kojyokan_sansyo) {
                        cd_kaisha = parseInt(cd_kaisha);
                        page.kaishaKojoJotai(page.header.urls.vw_kaisha_kojyo, "nm_kojyo", "cd_kojyo", "nm_kojyo", cd_kaisha, true).then(function () {
                            var cd_kojyo = element.findP("nm_kojyo").val();
                            if (!App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                                element.findP("cd_kojyo").val(App.common.getFullString(cd_kojyo, "0000"));
                            }
                        });
                    }
                    page.header.validator.validate({
                        targets: element.find("[data-prop='cd_kaisha'], [data-prop='nm_kaisha'],[data-prop='cd_kojyo'], [data-prop='nm_kojyo']")
                    });
                }
            }
            if (property == "cd_kojyo") {
                var cd_kojyo = target.val();
                element.findP("cd_kojyo").val(App.common.getFullString(cd_kojyo, "0000"));
                element.findP("cd_kojyo_validate").text(cd_kojyo);
                element.findP("nm_kojyo").val(cd_kojyo);
                element.findP("cd_hin").val("").change();
                element.findP("label_nmhin").text("");

                if (App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                    element.findP("nm_kojyo").val(cd_kojyo);
                }
                if (cd_kojyo != "" && App.isNumeric(cd_kojyo)) {
                    cd_kojyo = parseInt(cd_kojyo);
                    var cd_kaisha = element.findP("nm_kaisha").val();

                    page.checkData(App.str.format(page.header.urls.vw_kaisha_kojyo, "{0}", " and cd_kojyo eq {1}"), cd_kaisha, cd_kojyo,
                                    element, "cd_kojyo", "cd_kojyo_validate", "nm_kojyo", cd_kojyo);
                }
            }
            if (property == "nm_kojyo") {
                var cd_kojyo = target.val();
                element.findP("cd_hin").val("").change();
                element.findP("label_nmhin").text("");

                element.findP("cd_kojyo").val(App.common.getFullString(cd_kojyo, "0000"));
                element.findP("cd_kojyo_validate").text(cd_kojyo);
                element.findP("nm_kojyo").val(cd_kojyo);
                page.header.validator.validate({
                    targets: element.find("[data-prop='cd_kojyo'], [data-prop='nm_kojyo']")
                });
            }
            if (property != "cd_hin" || property != "cd_seihin") {
                page.header.validator.validate({
                    targets: target
                });
            }
            else {
                page.header.validator.validate({
                    targets: target,
                    filter: page.header.validationFilter
                });
            }
        };

        /**
        * 検索処理を定義します。
        */
        page.checkData = function (url, param1, param2, element, cd_code, cd_validate, nm_name, value) {
            return $.ajax(App.ajax.odata.get(App.str.format(url, param1, param2))).then(function (result) {
                if (result.value.length > 0) {
                    element.findP(cd_code).val(App.common.getFullString(value, "0000"));
                    element.findP(cd_validate).text(value);
                    element.findP(nm_name).val(value);
                    page.values.isResetKojyo = true;
                } else {
                    element.findP(cd_validate).text("");
                    element.findP(nm_name).val("");
                    page.values.isResetKojyo = false;
                }
                page.header.validator.validate({
                    targets: element.find("[data-prop='" + cd_code + "'], [data-prop='" + nm_name + "']")
                });
            });
        }

        //Tosvn: bind nm_seihin
        page.header.completeSeihin = function (data) {
            if (data.value.length > 0) {
                page.header.element.findP("label_seihin").text(data.value[0].nm_seihin);
                page.header.validator.validate({
                    target: page.header.element.findP("cd_seihin")
                });
            }
            else {
                page.header.element.findP("label_seihin").text("");
                page.header.clearSeihin();
            }
        };

        //Tosvn: bind nm_hin
        page.header.clearSeihin = function (data) {
            var target = page.header.element.findP("cd_seihin");

            page.header.element.findP("label_seihin").text("");
                    page.header.validator.validate({
                        targets: target
                    });
        };

        page.header.completeHin = function (data) {
            var cd_hin = page.header.element.findP("cd_hin").val(),
                num;

            if (data.Items.length > 0) {
                cd_hin = page.SetFormatHinmei(data.Items[0]);
                page.header.element.findP("label_nmhin").text(data.Items[0].nm_hin);
                page.header.element.findP("label_nmhin").attr("title", data.Items[0].nm_hin);
                page.header.element.findP("cd_hin").val(cd_hin);
                page.values.kbn_hin = data.Items[0].kbn_hin,
                page.values.kbn_shikakari = data.Items[0].kbn_shikakari;
                var target = page.header.element.findP("cd_hin");
                page.header.validator.validate({
                    targets: target
                });
                App.ui.loading.close();
            }
            else {
                page.header.element.findP("label_nmhin").text("");
                page.header.clearHin();

            }
        };
        page.header.clearHin = function (data) {
            var target = page.header.element.findP("cd_hin");
            var kojyo = page.header.element.findP("cd_kojyo").val(),
                kaisha = page.header.element.findP("cd_kaisha").val();

            if ((kojyo == null || kojyo == "")) {
                var target = page.header.element.findP("cd_kojyo");
                page.header.validator.validate({
                    targets: target
                });
                App.ui.loading.close();
                return;
         
            }
            page.header.element.findP("label_nmhin").text("");
            //setTimeout(function () {
            page.header.validator.validate({
                targets: target
            });
            // }, 0);
            App.ui.loading.close();
        };
        /**
         * 検索処理を定義します。
         */
        page.header.search = function (isLoading) {

            var deferred = $.Deferred(),
                query;

            page.header.validator.validate().done(function () {

                page.options.skip = 0;
                var param = page.header.createFilter();


                if (isLoading) {
                    App.ui.loading.show();
                    App.ui.page.notifyAlert.clear();
                }

                return $.ajax(App.ajax.webapi.get(page.header.urls.searchHaigo, param))
                .done(function (result) {
                    if (page.values.flg_createHaigo) {
                        $("#203_HaigoTorokuKaihatsuBumon_Create").prop('disabled', false);
                    }
                    // パーツ開閉の判断
                    if (page.detail.isClose) {
                        // 検索データの保持
                        page.detail.searchData = result;
                    } else {
                        // データバインド
                        page.detail.bind(result);
                    }
                    deferred.resolve();
                }).fail(function (error) {
                    if (error.status == 500) {
                        App.ui.page.notifyAlert.message(App.messages.app.AP0195).show();
                    } else {
                        App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                    };
                        deferred.reject();
                }).always(function () {
                    if (isLoading) {
                        App.ui.loading.close();
                    }
                });
            });

            return deferred.promise();
        };

        /**
         * 検索条件のフィルターを定義します。
         */
        page.header.createFilter = function () {
            var data = page.header.element.form(page.header.options.bindOption).data(),
                filters = {};

            return filters = {
                no_seiho1: data.no_seiho_kaisha,
                no_seiho2: data.no_seiho_shurui,
                no_seiho3: data.no_seiho_nen,
                no_seiho4: data.no_seiho_renban,
                nm_seiho: data.nm_seiho,
                cd_seiho_bunrui: data.cd_seiho_bunrui,
                dt_seiho_sakusei_start: data.dt_seiho_sakusei_start,
                dt_seiho_sakusei_end: data.dt_seiho_sakusei_end,
                nm_seiho_sakusei: data.nm_seiho_sakusei,
                status: data.status,
                kbn_haishi: data.kbn_haishi,
                cd_seihin: parseInt(data.cd_seihin),
                nm_seihin: data.nm_seihin,
                nm_haigo: data.nm_haigo,
                cd_kaisha_daihyo: (data.def != null) ? null : parseInt(data.cd_kaisha), //TOsVN [19075 - 11/09/2019] Request #15254
                cd_kojyo_daihyo: parseInt(data.cd_kojyo),
                nm_seizo: data.nm_seizo,
                cd_hin: parseInt(data.cd_hin),
                kbn_shikakari: data.cd_hin != null ? page.values.kbn_shikakari : page.values.kbn_shikakari = null,
                kbn_hin: data.cd_hin != null
                            ? (page.values.kbn_hin == 6 ? page.values.kbn_hin = 1 : page.values.kbn_hin)
                            : page.values.kbn_hin = null,
                no_kikaku1: data.no_kikaku1,
                no_kikaku2: data.no_kikaku2,
                no_kikaku3: data.no_kikaku3,
                nm_hanbai: data.nm_hanbai,
                nm_hin: data.nm_hin,
                nm_kikaku: data.nm_kikaku,
                mode: page.values.M_SeihoIchiran,
                kbn_kengen: App.ui.page.user.cd_kengen,
                cd_kaisha: App.ui.page.user.cd_kaisha,
                sort: page.header.element.find('.header-command input[name=checkbox]:checked').val(),
                skip: page.options.skip,     // TODO:先頭からスキップするデータ数を指定します。
                top: page.options.top       // TODO:取得するデータ数を指定します。
            };
        };

        //TODO: 以下の page.header の各宣言は画面仕様ごとにことなるため、不要の場合は削除してください。
<%--
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
            page.header.element.findP("cd_torihiki").val(data.cd_torihiki).change();
            page.header.element.findP("nm_torihiki").text(data.nm_torihiki);
        };
--%>
        //TODO-END: 

        /**
         * 画面明細の初期化処理を行います。
         */
        page.detail.initialize = function () {

            var element = $(".detail"),
                table = element.find(".datatable"),
                datatable = table.dataTable({
                    height: 200,
                    resize: true,
                    //列固定横スクロールにする場合は、下記3行をコメント解除
                    //fixedColumn: true,                //列固定の指定
                    //fixedColumns: 3,                  //固定位置を指定（左端を0としてカウント）
                    //innerWidth: 1200,                 //可動列の合計幅を指定
                    onselect: page.detail.select
                });
            table = element.find(".datatable");         //列固定にした場合DOM要素が再作成されるため、変数を再取得
            App.settings.base.maxSearchDataCount = 5000;
            page.detail.element = element;
            page.detail.dataTable = datatable;

            element.on("click", "#nextsearch", page.detail.nextsearch);
            element.on("click", "#add-item", page.detail.addNewItem);

            // 行選択時に利用するテーブルインデックスを指定します
            page.detail.fixedColumnIndex = element.find(".fix-columns").length;

            // 明細パートオープン時の処理を指定します
            element.find(".part").on("expanded.aw.part", function () {
                page.detail.isClose = false;
                if (page.detail.searchData) {
                    App.ui.loading.show();
                    setTimeout(function () {
                        page.detail.bind(page.detail.searchData);
                        page.detail.searchData = undefined;
                        App.ui.loading.close();
                    }, 5);
                };
            });

            // 明細パートクローズ時の処理を指定します
            element.find(".part").on("collapsed.aw.part", function () {
                page.detail.isClose = true;
            });

            //TODO: 画面明細の初期化処理をここに記述します。
            //TODO: 画面明細で利用するコントロールのイベントの紐づけ処理をここに記述します。
            table.on("click", ".edit-select", page.detail.showEditPart);
            //TODO: 行選択による詳細画面の遷移を行う際は、上の行をコメント化し下の行のコメントを解除します。
            //table.on("click", "tbody", page.detail.showEditPart);

        };

        /**
         * 次のレコードを検索する処理を定義します。
         */
        page.detail.nextsearch = function () {

            var param = page.header.createFilter();

            App.ui.loading.show();
            App.ui.page.notifyAlert.clear();

            return $.ajax(App.ajax.webapi.get(page.header.urls.searchHaigo, param))
            .done(function (result) {
                page.detail.bind(result);
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            }).always(function () {
                App.ui.loading.close();
            });

        };

        //Tosvn: reset header
        //TOsVN [19075 - 2019/09/17 Request #15254 : comment code
        page.detail.resetHeader = function () {
            
            //var element = page.header.element;
            //page.values.reset = 1;
            ////TOsVN [19075 - 11/09/2019] Request #15254
            //element.find(":input:not([type='checkbox']):not([type='radio']):not([data-prop='cd_kaisha_lg']):not([data-prop='nm_kaisha_lg'])").val("");
            //element.find("[data-prop='cd_kaisha'], [data-prop='nm_kaisha'], [data-prop='cd_kojyo'], [data-prop='nm_kojyo'],[data-prop='cd_hin']").prop('disabled', true);
            ////End request 15254
            //element.findP("label_seihin").text("");
            //element.findP("cd_hin").val("");
            //element.findP("label_nmhin").text("");
            //element.findP("kbn_haishi").prop('checked', false);
            //element.findP("asc").prop('checked', true);
            //element.findP("desc").prop('checked', false);
            //element.findP("def").prop('checked', true);
            //page.detail.dataTable.dataTable("clear");
            //page.detail.element.findP("data_count").text("");
            //page.detail.element.findP("data_count_total").text("");
            //$("#nextsearch").removeClass("show-search").hide();
            //page.loadData();
            //page.loadMasterData()
            //    .then(function () {
            //    //setTimeout(function () {
            //        App.ui.page.notifyAlert.clear();
            //        page.header.validator.validate();
            //    //}, 300);
            //}).always(function () {
            //    page.values.reset = 0;
            //});    
            window.location.reload();
        };
        /**
         * TOsVN [19075 - 09/09/2019] edit : Request #15254 - search with kaisha and kojyo user login
         */
        page.header.def = function (isFirst) {
            App.ui.page.notifyAlert.clear();
            var element = page.header.element,
                cd_hin = element.findP("cd_hin"),
                label_nmhin = element.findP("label_nmhin");

            var target = element.find("[data-prop='cd_kaisha'], [data-prop='nm_kaisha'], [data-prop='cd_kojyo'], [data-prop='nm_kojyo'],[data-prop='cd_hin'],#open-hinmeiKaihatsu");
            target.val("");
            target.prop("disabled", true);
            if (cd_hin.val() != null || label_nmhin.text() != null) {
                element.findP("cd_hin").val("");
                element.findP("label_nmhin").text("");
                page.header.validator.validate({
                    targets: element.findP("cd_hin")
                });
            }
            if(App.ui.page.user.cd_kengen != null && App.ui.page.user.cd_kengen != page.values.cd_kengen){
                element.findP("cd_kaisha_lg").val(App.common.getFullCdKaisha(App.ui.page.user.cd_kaisha));
                element.findP("nm_kaisha_lg").val(App.ui.page.user.nm_kaisha);
            }else{
                element.find("[data-prop='cd_kaisha_lg'], [data-prop='nm_kaisha_lg']").val("");
            }
            if (!isFirst) {
                page.header.validator.validate({
                    targets: target
                });
            }

        }
        /**
         * TOsVN [19075 - 09/09/2019] edit : Request #15254 - search with kaisha and kojyo combobox
         */
        page.header.choose = function () {
            var element = page.header.element;
                var target = element.find("[data-prop='cd_kaisha'], [data-prop='nm_kaisha'], [data-prop='cd_kojyo'], [data-prop='nm_kojyo'],[data-prop='cd_hin'],#open-hinmeiKaihatsu")
                
                var cd_kaisha = element.findP("nm_kaisha").val();
                element.findP("nm_kaisha").val(App.ui.page.user.cd_kaisha); 
                cd_kaisha = App.ui.page.user.cd_kaisha;
                if (!App.isUndefOrNullOrStrEmpty(cd_kaisha)) {
                    element.findP("cd_kaisha").val(App.common.getFullString(cd_kaisha, "0000"));
                    element.findP("cd_kaisha_validate").text(cd_kaisha);


                    page.kaishaKojoJotai(page.header.urls.vw_kaisha_kojyo, "nm_kojyo", "cd_kojyo", "nm_kojyo", cd_kaisha, true).then(function () {
                        target.prop("disabled", false);
                        var cd_kojyo = element.findP("nm_kojyo").val();
                        element.findP("nm_kojyo").val(App.ui.page.user.cd_busho);
                        cd_kojyo = App.ui.page.user.cd_busho;
                        if (!App.ui.page.user.flg_kojyokan_sansyo) {
                            element.find("[data-prop='cd_kojyo'], [data-prop='nm_kojyo']").prop("disabled", true);
                        }
                        if (!App.ui.page.user.flg_kaishakan_sansyo) {
                            element.find("[data-prop='cd_kaisha'], [data-prop='nm_kaisha']").prop("disabled", true);
                        }

                        if (!App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                            element.findP("cd_kojyo").val(App.common.getFullString(cd_kojyo, "0000")); 
                            element.findP("cd_kojyo_validate").text(cd_kojyo);
                        }
                    });
                }
            }
            //Tosvn: Export file CSV
            //page.detail.DownloadCSV = function () {
            //    var param = page.header.createFilter();

            //    App.ui.loading.show();
            //    App.ui.page.notifyAlert.clear();

            //    return $.ajax(App.ajax.webapi.get(page.detail.options.downloadCSV, param))
            //   .done(function (result) {

            //   }).fail(function (error) {

            //       App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            //   }).always(function () {
            //       App.ui.loading.close();
            //   });

            //};
            page.detail.DownloadCSV = function () {
                var param = page.header.createFilter();
                App.ui.page.notifyAlert.clear();
                page.header.validator.validate().done(function () {
                    App.ui.loading.show();
                    return $.ajax(App.ajax.file.download(page.detail.options.downloadCSV, param))
                   .then(function (response, status, xhr) {
                       if (status !== "success") {
                           App.ui.page.notifyAlert.message(App.messages.base.MS0005).show();
                       } else {
                           App.file.save(response, App.ajax.file.extractFileNameDownload(xhr) || "CSVFile.csv");
                       }
                   }).fail(function (error) {
                       App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                   }).always(function () {
                       App.ui.loading.close();
                   });
                });
            };


            //Tosvn: open SeihoBunshoSakusei page
            page.detail.openSeihoBunshoSakusei = function () {
                var element = page.detail.element,
                   selected = element.find(".datatable .select-tab.selected").closest("tbody");
                var row, id, entity;
                App.ui.page.notifyAlert.remove();
                if (!selected.length) {
                    App.ui.page.notifyAlert.message(App.messages.app.AP0008).show();
                    return;
                }

                page.detail.dataTable.dataTable("getRow", selected, function (rowObject) {
                    row = rowObject.element;
                });
                id = row.attr("data-key");
                entity = page.detail.data.entry(id);

                if (entity.status == App.settings.app.kbn_status.jushinchu || entity.status == App.settings.app.kbn_status.kojokakunin) {
                    App.ui.page.notifyAlert.message(App.messages.app.AP0061).show();
                    return;
                }
                $.ajax(App.ajax.webapi.get(App.str.format(page.detail.options.CheckExists, entity.no_seiho))).then(function (result) {
                    //Task 15059: TosVN-nhan.nt-19075-2019/08/12
                    if (entity.status == App.settings.app.kbn_status.henshuchu ||
                       (entity.status != App.settings.app.kbn_status.henshuchu && result && result.value.length)) {
                        //End Task 15059
                        App.common.openSeihoBunsho({
                            no_seiho: entity.no_seiho,
                            no_seiho_saki: entity.no_seiho,
                            mode: App.settings.app.m_seiho_bunsho.shinsei,
                            openerID: App.settings.app.no_gamen.seiho_ichiran
                        });
                    }

                    else {
                        App.ui.page.notifyAlert.message(App.messages.app.AP0125).show();
                        return;
                    }
                });

            };

            //Tosvn: Create seiho use copy
            page.detail.HaigoTorokuKaihatsuBumon_Copy = function (e) {
                var selected = page.detail.getSelected(e);

                if (selected != undefined) {
                    var cd_haigo = selected.cd_haigo,
                       no_seiho = selected.no_seiho,
                        cd_kaisha_daihyo = selected.cd_kaisha_daihyo,
                    cd_kojyo_daihyo = selected.cd_kojyo_daihyo;

                    var link = "203_HaigoTorokuKaihatsuBumon.aspx?M_HaigoTorokuKaihatsu=" + App.settings.app.m_haigo_toroku_kaihatsu.seiho_copy
                        + "&cd_haigo=" + cd_haigo + "&no_seiho=" + no_seiho + "&cd_kaisha_daihyo=" + cd_kaisha_daihyo + "&cd_kojyo_daihyo=" + cd_kojyo_daihyo;
                    window.open(link);
                }
            };

            //Tosvn: Renew seiho
            page.detail.HaigoTorokuKaihatsuBumon_Renew = function (e) {
                var selected = page.detail.getSelected(e);

                if (selected != undefined) {
                    var cd_haigo = selected.cd_haigo,
                       no_seiho = selected.no_seiho,
                        cd_kaisha_daihyo = selected.cd_kaisha_daihyo,
                    cd_kojyo_daihyo = selected.cd_kojyo_daihyo;

                    var link = "203_HaigoTorokuKaihatsuBumon.aspx?M_HaigoTorokuKaihatsu=" + App.settings.app.m_haigo_toroku_kaihatsu.seiho_copy
                        + "&cd_haigo=" + cd_haigo + "&no_seiho=" + no_seiho + "&cd_kaisha_daihyo=" + cd_kaisha_daihyo + "&cd_kojyo_daihyo=" + cd_kojyo_daihyo + "&flg_renewal=1";
                    window.open(link);
                }
            };

            //Tosvn: Create new haigo
            page.detail.HaigoTorokuKaihatsuBumon_Create = function (e) {

                var selected = page.detail.getSelected(e);

                if (selected != undefined) {
                    var cd_haigo = selected.cd_haigo,
                       no_seiho = selected.no_seiho,
                       cd_kaisha_daihyo = selected.cd_kaisha_daihyo,
                        cd_kojyo_daihyo = selected.cd_kojyo_daihyo;

                    var link = "203_HaigoTorokuKaihatsuBumon.aspx?M_HaigoTorokuKaihatsu=" + App.settings.app.m_haigo_toroku_kaihatsu.haigo_shinki
                        + "&cd_haigo=" + cd_haigo + "&no_seiho=" + no_seiho + "&cd_kaisha_daihyo=" + cd_kaisha_daihyo + "&cd_kojyo_daihyo=" + cd_kojyo_daihyo;
                    window.open(link);
                }
            };

            //Tosvn: get data selected row
            page.detail.getSelected = function (e) {
                var element = page.detail.element,
                   selected = element.find(".datatable .select-tab.selected").closest("tbody");
                var row, id, entity;

                if (!selected.length) {
                    App.ui.page.notifyAlert.message(App.messages.app.AP0008).show();
                    return;
                }

                page.detail.dataTable.dataTable("getRow", selected, function (rowObject) {
                    row = rowObject.element;
                });
                id = row.attr("data-key");
                entity = page.detail.data.entry(id);
                return entity;
            };


            //Tosvn: Delete Haigo
            page.detail.deleteHaigo = function (e) {

                var element = page.detail.element,
                    selected = element.find(".datatable .select-tab.selected").closest("tbody");
                App.ui.page.notifyAlert.remove();
                if (!selected.length) {
                    App.ui.page.notifyAlert.message(App.messages.app.AP0008).show();
                    return;
                }

                var options = {
                    text: App.messages.app.AP0051
                };

                var row, id, entity;

                page.detail.dataTable.dataTable("getRow", selected, function (rowObject) {
                    row = rowObject.element;
                });

                id = row.attr("data-key");
                entity = page.detail.data.entry(id);

                if (entity.status != App.settings.app.kbn_status.henshuchu) {
                    App.ui.page.notifyAlert.message(App.messages.app.AP0049).show();
                    return;
                }
                if (entity.count_seiho <= 1) {
                    App.ui.page.notifyAlert.message(App.messages.app.AP0050).show();
                    return;
                }
                page.dialogs.confirmDialog.confirm(options)
                .then(function () {

                    App.ui.page.notifyWarn.clear();
                    App.ui.page.notifyAlert.clear();
                    App.ui.page.notifyInfo.clear();

                    App.ui.loading.show();
                    var changeset = page.detail.data.getChangeSet();
                    changeset.deleted.push(entity);

                    //TODO: データの更新処理をここに記述します。
                    $.ajax(App.ajax.webapi.post(page.detail.options.deleteHaigo, changeset))
                    .then(function (result) {

                        App.ui.page.notifyInfo.message(App.messages.base.MS0008).show();
                        page.header.search();
                    }).fail(function (error) {

                        //TODO: データの保存失敗時の処理をここに記述します。
                        App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

                    }).always(function () {
                        setTimeout(function () {
                            page.header.element.find(":input:first").focus();
                        }, 100);
                        App.ui.loading.close();
                    });
                });
            };

            //Tosvn:
            page.detail._202_G_LabelRenkei = function (e) {

                var selected = page.detail.getSelected(e);

                if (selected != undefined) {

                    App.ui.page.notifyAlert.message(App.messages.app.AP0056).remove();

                    if (selected.status != App.settings.app.kbn_status.densosumi && selected.status != App.settings.app.kbn_status.kojokakunin) {
                        App.ui.page.notifyAlert.message(App.messages.app.AP0056).show();
                        return
                    }

                    //Bug #15260 - START - 【200製法一覧】G-Label連携（権限処理）
                    //製法一覧画面よりG-Label連携を行う権限がありません
                    var isSubete = false;
                    $.each(page.options.kengenGlabel, function (index, item) {
                        if (item.id_kino == page.values.id_kino_glabel && item.id_data == page.values.id_data) {
                            isSubete = true;
                        }
                    });

                    //G-Label権限(id_gamen=710)が[本人のみ](id_data=1)が割り当てられているユーザでログインし製法一覧で、他人が作成した製法を選択しG-Label連携を行かない
                    App.ui.page.notifyAlert.message(App.messages.app.AP0095).remove();
                    if (!isSubete && selected.cd_toroku != App.ui.page.user.EmployeeCD) {
                        App.ui.page.notifyAlert.message(App.messages.app.AP0095).show();
                        return;
                    }
                    //Bug #15260 - END - 【200製法一覧】G-Label連携（権限処理）

                    var no_seiho = selected.no_seiho;

                    var link = "202_G-LabelRenkei.aspx?no_seiho=" + no_seiho;
                    window.open(link);
                }
            };

            //Tosvn:
            page.commands._211_ = function () {
                var targetRow = page.detail.element.find(".select-tab.selected").closest("tbody");
                if (targetRow.length > 0) {
                    var data = page.detail.data.entry(targetRow.attr("data-key")),
                        no_seiho = data.no_seiho;
                    window.open(App.str.format(page.urls.TenpuBunsho, no_seiho));
                }
            };

            //Tosvn: open dialog 802
            page.detail.openCopyHaniSelection_Dialog = function (e) {
                var selected = page.detail.getSelected(e);

            };


            page.header.options.bindOption = {
                converters: {
                    dt_seiho_sakusei_start: function (element, defVal) {
                        return (element.val() || null);
                    },
                    dt_seiho_sakusei_end: function (element, defVal) {
                        return (element.val() || null);
                    },
                    kbn_haishi: function (element, defVal) {
                        return element.is(':checked') ? App.settings.app.kbn_haishi.haishi : App.settings.app.kbn_haishi.shiyo;
                    },
                }
            };


            /**
             * 画面明細の各行にデータを設定する際のオプションを定義します。
             */
            page.detail.options.bindOption = {
                appliers: {
                    dt_seiho_sakusei: function (value, element) {
                        element.text(App.data.getDateString(new Date(value)));
                        return true;
                    },
                    kbn_haishi: function (value, element) {
                        element.prop("checked", value ? App.settings.app.kbn_haishi.haishi : App.settings.app.kbn_haishi.shiyo);
                        return true;
                    },
                    nm_seiho: function (value, element) {
                        element.attr("title", value);
                        return false;
                    },
                    nm_haigo: function (value, element) {
                        element.attr("title", value);
                        return false;
                    },
                    cd_seihin: function (value, element) {
                        var arr = value.split(',');
                        var res = "";
                        for (var i = 0; i < arr.length; i++) {
                            arr[i] = App.common.getFullString(arr[i], "000000");
                        }
                        element.text(arr.join(", "));
                        return true;
                    }

                },
                //converters: {
                //    dt_seiho_sakusei: function (element, defVal) {
                //        return (element.val() || null);
                //    }
                //}
            };

            /**
             * 画面明細へのデータバインド処理を行います。
             */
            page.detail.bind = function (data, isNewData) {
                var i, l, item, dataSet, dataCount, offsetHeight;

                dataCount = data.Count ? data.Count : 0;
                data = (data.Items) ? data.Items : data;

                if (page.options.skip === 0) {
                    dataSet = App.ui.page.dataSet();
                    page.detail.dataTable.dataTable("clear");
                } else {
                    dataSet = page.detail.data;
                }
                page.detail.data = dataSet;

                page.detail.dataTable.dataTable("addRows", data, function (row, item) {
                    (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
                    row.form(page.detail.options.bindOption).bind(item);
                    return row;
                }, true);

                page.options.skip += data.length;
                if (!isNewData) {
                    page.detail.element.findP("data_count").text(page.options.skip);
                    page.detail.element.findP("data_count_total").text(dataCount);
                }

                if (dataCount <= page.options.skip) {
                    $("#nextsearch").hide();
                }
                else {
                    $("#nextsearch").show();
                }

                if (page.options.skip >= App.settings.base.maxSearchDataCount) {
                    App.ui.page.notifyInfo.message(App.messages.base.MS0011).show();
                    $("#nextsearch").hide();
                }

                if (data.length) {
                    $.each(page.options.kengenSeiho, function (index, item) {
                        if (item.id_data == page.values.id_data) {
                            switch (item.id_kino) {
                                case page.values.id_kino_CSV:
                                    $("#CSV").prop('disabled', false);
                                    break;
                                case page.values.id_kino_ta_haigo:
                                    $(".edit-select").prop('disabled', false);
                                    break;
                                case page.values.id_kino_haigo_toroku:
                                    page.values.flg_createHaigo = true;
                                    $(".edit-select").prop("disabled", false);
                                    $("#add-item").prop('disabled', false);
                                    $("#203_HaigoTorokuKaihatsuBumon_Copy").prop('disabled', false);
                                    $("#203_HaigoTorokuKaihatsuBumon_Renew").prop('disabled', false);
                                    $("#203_HaigoTorokuKaihatsuBumon_Create").prop('disabled', false);
                                    $("#deleteHaigo").prop('disabled', false);
                                    $("#211_").prop('disabled', false);
                                    break;
                                case page.values.id_kino_shisaku_copy:
                                    $("#shisaku-copy").prop('disabled', false);
                                    break;
                                case page.values.id_kino_haigo_haishi:
                                    $(".edit-select").prop('disabled', false);
                                    break;
                                case page.values.id_kino_seihin_tsuika:
                                    break;
                                case page.values.id_kino_shinsei:
                                    $("#300_SeihoBunshoSakusei").prop('disabled', false);
                                    break;
                                default:
                                    var options = {
                                        text: App.messages.app.AP0089
                                    };

                                    page.dialogs.confirmDialog.confirm(options)
                                    .always(function () {
                                        window.location.href = page.urls.mainMenu;
                                    });
                            }
                        }
                        else {
                            var options = {
                                text: App.messages.app.AP0089
                            };

                            page.dialogs.confirmDialog.confirm(options)
                            .always(function () {
                                window.location.href = page.urls.mainMenu;
                            });
                        }

                    });

                    $.each(page.options.kengenGlabel, function (index, item) {
                        if (item.id_kino == page.values.id_kino_glabel) {
                            if (item.id_data == page.values.id_data_glabel || item.id_data == page.values.id_data) {
                                $("#202_G_LabelRenkei").prop('disabled', false);
                            }
                        }
                    });
                }
                offsetHeight = $("#nextsearch").is(":visible") ? $("#nextsearch").addClass("show-search").outerHeight() : 0;
                page.detail.dataTable.dataTable("setAditionalOffset", offsetHeight);
                //TODO: 画面明細へのデータバインド処理をここに記述します。

            };

            /**
             * 画面明細の一覧の行が選択された時の処理を行います。
             */
            page.detail.select = function (e, row) {
                //TODO: 単一行を作成する場合は、下記２行を利用します。
                $($(row.element[page.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab.selected")).removeClass("selected").addClass("unselected");
                $(row.element[page.detail.fixedColumnIndex].querySelectorAll(".select-tab")).removeClass("unselected").addClass("selected");
                //TODO: 多段行を作成する場合は、下記２行を有効にし上記２行は削除します。
                //$($(row.element[page.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab-2lines.selected")).removeClass("selected").addClass("unselected");
                //$(row.element[page.detail.fixedColumnIndex].querySelectorAll(".select-tab-2lines")).removeClass("unselected").addClass("selected");

                //TODO: 選択行全体に背景色を付ける場合は以下のコメントアウトを解除します。
                //if (!App.isUndefOrNull(page.detail.selectedRow)) {
                //    page.detail.selectedRow.element.find(".selected-row").removeClass("selected-row");
                //}
                //row.element.find("tr").addClass("selected-row");
                //page.detail.selectedRow = row;
                var id = row.element.attr("data-key");
                var entity = page.detail.data.entry(id);
                if (page.values.flg_createHaigo) {
                    $("#203_HaigoTorokuKaihatsuBumon_Create").prop('disabled', (entity.status != App.settings.app.kbn_status.henshuchu));
                }
            };

            /**
             * 画面明細の一覧に新規データを追加します。
             */
            page.detail.addNewItem = function () {
                var link = "203_HaigoTorokuKaihatsuBumon.aspx?M_HaigoTorokuKaihatsu=" + App.settings.app.m_haigo_toroku_kaihatsu.seiho_shinki_toroku;
                window.open(link);
            };

            /**
             * 画面明細の一覧から編集画面を表示します。
             */
            page.detail.showEditPart = function (e) {
                var element = page.detail.element,
                    target = $(e.target),
                    row = target.closest("tbody"),
                    id = row.attr("data-key"),
                    entity = page.detail.data.entry(id),
                    link;

                page.detail.dataTable.dataTable("getFirstViewRow", function (row) {
                    page.detail.firstViewRow = row;
                });
                var cd_haigo = entity.cd_haigo,
                    no_seiho = entity.no_seiho;

                if (page.values.M_SeihoIchiran == App.settings.app.m_seiho_ichiran.copy) {
                    link = "203_HaigoTorokuKaihatsuBumon.aspx?M_HaigoTorokuKaihatsu=" + App.settings.app.m_haigo_toroku_kaihatsu.seiho_bunsho_copy + "&cd_haigo=" + cd_haigo + "&no_seiho="
                        + no_seiho + "&no_seiho_sakusei=" + page.values.no_seiho_sakusei + "&no_seiho_copy=" + no_seiho;
                    var window203 = window.open(link);
                    App.common.addCloseTab({ openWindow: window203 });
                }
                else if (page.values.M_SeihoIchiran == App.settings.app.m_seiho_ichiran.search) {
                    link = "203_HaigoTorokuKaihatsuBumon.aspx?M_HaigoTorokuKaihatsu=" + App.settings.app.m_haigo_toroku_kaihatsu.shosai + "&cd_haigo=" + cd_haigo + "&no_seiho=" + no_seiho;
                    window.open(link);
                }
            };

            /**
             * 編集画面で更新された画面明細の行を最新化します。
             */
            page.detail.updateData = function (result) {

                //TODO: 更新されたデータのキーを取得します。
                //var updatedKey = result.updated[0].no_seq;

                // キーをもとに最新のデータを取得します。(検索結果取得サービスのurl, 更新行を取得する条件式)
                return $.ajax(App.ajax.webapi.get(/*TODO: 検索結果取得サービスの URL, */ /*TODO: 条件式 */"$filter=no_seq eq " + updatedKey))
                    .then(function (result) {
                        var newData = result.Items[0];
                        if (!newData) {
                            return;
                        }

                        page.detail.dataTable.dataTable("each", function (row, index) {
                            var entity = page.detail.data.entry(row.element.attr("data-key"));
                            if (entity/*TODO: キー項目値が一致する条件を設定 //.no_seq*/ == updatedKey) {
                                // 対象データの各値を最新の値で更新します。
                                Object.keys(newData).forEach(function (key) {
                                    if (key in entity) {
                                        entity[key] = newData[key];
                                    }
                                });
                                // 行にデータを再バインドします。
                                row.element.find("[data-prop]").val("").text("");
                                row.element.form(page.detail.options.bindOption).bind(entity);
                                return true;    //datatable.eachのループを抜けるためのreturn true;
                            }
                        });
                    });
            };

            /**
             * 編集画面で削除された画面明細の行を削除します。
             */
            page.detail.removeData = function (result) {

                //TODO: 削除されたデータのキーを取得します。
                //var removedKey = result.deleted[0].no_seq;

                // キーをもとに削除行を特定します。
                var deletedRow;
                page.detail.dataTable.dataTable("each", function (row, index) {
                    var entity = page.detail.data.entry(row.element.attr("data-key"));
                    if (entity/*TODO: キー項目値が一致する条件を設定 //.no_seq*/ == removedKey) {
                        deletedRow = row.element;
                        return true;    //datatable.eachのループを抜けるためのreturn true;
                    }
                });

                page.detail.dataTable.dataTable("deleteRow", deletedRow, function (row) {
                    var id = row.attr("data-key"),
                        newSelected;

                    if (!App.isUndefOrNull(id)) {
                        var entity = page.detail.data.entry(id);
                        page.detail.data.remove(entity);
                    }

                    newSelected = row.next().not(".item-tmpl");
                    if (!newSelected.length) {
                        newSelected = row.prev().not(".item-tmpl");
                    }
                    if (newSelected.length) {
                        //TODO: 単一行を作成する場合は、下記２行を利用します。
                        $($(newSelected[page.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab.selected")).removeClass("selected").addClass("unselected");
                        $(newSelected[page.detail.fixedColumnIndex].querySelectorAll(".select-tab")).removeClass("unselected").addClass("selected");
                        //TODO: 多段行を作成する場合は、下記２行を有効にし上記２行は削除します。
                        //$($(newSelected[page.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab-2lines.selected")).removeClass("selected").addClass("unselected");
                        //$(newSelected[page.detail.fixedColumnIndex].querySelectorAll(".select-tab-2lines")).removeClass("unselected").addClass("selected");

                        //TODO: 選択行全体に背景色を付ける場合は以下のコメントアウトを解除します。
                        //if (!App.isUndefOrNull(page.detail.selectedRow)) {
                        //    page.detail.selectedRow.element.find("tr").removeClass("selected-row");
                        //}
                        //newSelected.find("tr").addClass("selected-row");
                        //page.detail.dataTable.dataTable("getRow", newSelected, function (row) {
                        //    page.detail.selectedRow = row;
                        //});
                    }
                });

                page.detail.element.findP("data_count").text(parseFloat(page.detail.element.findP("data_count").text()) - 1);
                page.detail.element.findP("data_count_total").text(parseFloat(page.detail.element.findP("data_count_total").text()) - 1);
            };

            //TODO: 以下の page.detail の各宣言は画面仕様ごとにことなるため、不要の場合は削除してください。


            //TODO-END: 

            /**
             * 編集画面のバリデーションを定義します。
             */
            page.detailInput.options.validations = {
                //TODO: 新規追加画面のバリデーションの定義を記述します。

            };

            /**
             * 編集画面の初期化処理を行います。
             */
            page.detailInput.initialize = function () {

                var element = $(".detailInput");

                page.detailInput.element = element;
                page.detailInput.validator = element.validation(page.createValidator(page.detailInput.options.validations));
                element.on("change", ":input", page.detailInput.change);
                element.on("click", ".previous", page.detailInput.previous);
            };

            /**
             * 編集画面の表示処理を行います。
             */
            page.detailInput.show = function (data) {

                var element = page.detailInput.element;

                //TODO:項目をクリアする処理をここに記述します。
                element.find(":input:not([type='checkbox']):not([type='radio'])").val("");
                element.find("input[type='checkbox']").prop('checked', false);

                //TODO:radioボタンの初期表示は、画面要件に合わせて記述します。

                //TODO:入力項目以外の個別項目のクリアはここで記述

                //TODO:データバインド処理をここに記述します。
                page.detailInput.bind(data);

                //TODO:画面モードによる処理をここに記述します。
                if (page.detailInput.options.mode === page.detailInput.mode.input) {
                    $("#addItem").show();
                    $("#editItem").hide();
                    $("#deleteItem").hide();
                    //TODO:キー項目が画面入力の場合、新規モード時は修正可とします。
                    //element.findP("no_seq").prop("readonly", false).prop("tabindex", false);
                    element.validation().validate({
                        state: {
                            suppressMessage: true
                        }
                    });
                } else {
                    $("#addItem").hide();
                    $("#editItem").show();
                    $("#deleteItem").show();
                    //TODO:キー項目が画面入力の場合、編集モード時は修正不可とします。
                    //element.findP("no_seq").prop("readonly", true).prop("tabindex", -1);
                    element.validation().validate();
                }

                //TODO:パーツの表示・非表示処理を記述します。
                page.header.element.hide();
                page.detail.element.hide();
                element.show();
                element.find(":input:not([readonly]):first").focus();
            };

            /**
             * 画面明細に戻ります。
             */
            page.detailInput.previous = function (isConfirm) {
                var closeMessage = {},
                    previousPage = function () {
                        //TODO:パーツの表示・非表示処理を記述します。
                        page.header.element.show();
                        page.detail.element.show();
                        page.detailInput.element.hide();
                        page.detailInput.data = undefined;

                        if (!App.isUndefOrNull(page.detail.firstViewRow)) {
                            page.detail.dataTable.dataTable("scrollTop", page.detail.firstViewRow, function () { });
                        }

                        $("#addItem").hide();
                        $("#editItem").prop("disabled", true).hide();
                        $("#deleteItem").hide();

                        App.ui.page.notifyAlert.clear();
                    };

                if (page.detailInput.data.isChanged()) {
                    closeMessage.text = App.messages.base.exit;
                }

                if (isConfirm && closeMessage.text) {
                    page.dialogs.confirmDialog.confirm(closeMessage)
                    .then(function () {
                        previousPage();
                    });
                } else {
                    previousPage();
                };
            };

            /**
             * 編集画面へのデータバインド処理を行います。
             */
            page.detailInput.bind = function (data) {

                var setData = {},
                    dataSet = App.ui.page.dataSet();

                setData = App.isUndefOrNull(data) ? data : data.constructor();
                for (var attr in data) {
                    if (data.hasOwnProperty(attr)) setData[attr] = data[attr];
                }

                //TODO:画面モードによる処理をここに記述します。
                if (page.detailInput.options.mode === page.detailInput.mode.input) {
                    page.detailInput.data = dataSet;
                    page.detailInput.data.add.bind(dataSet)(setData);
                } else {
                    page.detailInput.data = dataSet;
                    page.detailInput.data.attach.bind(dataSet)(setData);
                }

                page.detailInput.element.form().bind(setData);

            };

            /**
             * 編集画面にある入力項目の変更イベントの処理を行います。
             */
            page.detailInput.change = function (e) {

                var element = page.detailInput.element,
                    target = $(e.target),
                    id = element.attr("data-key"),
                    property = target.attr("data-prop"),
                    entity = page.detailInput.data.entry(id),
                    data = element.form().data();

                var state = page.detailInput.data.entries[entity.__id].state;
                // 入力項目が削除済みの場合、処理を実行しない
                if (!App.isUndefOrNull(state)
                    && state === App.ui.page.dataSet.status.Deleted) {
                    App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
                    return;
                }

                page.values.isChangeRunning[property] = true;

                page.detailInput.validator.validate({
                    targets: target
                }).then(function () {
                    entity[property] = data[property];
                    page.detailInput.data.update(entity);
                    if ($("#editItem").is(":visible:disabled")) {
                        $("#editItem").prop("disabled", false);
                    }
                }).always(function () {
                    delete page.values.isChangeRunning[property];
                });
            };


            //TODO: 以下の page.detailInput の各宣言は画面仕様ごとにことなるため、不要の場合は削除してください。
            <%--
        /**
        * 編集追加画面にある検索ダイアログ起動時の処理を定義します。
        */
        page.detailInput.showSearchDialog = function () {
            page.dialogs.searchDialog.element.modal("show");
        };

        /**
         * 編集追加画面にある取引先コードに値を設定します。
         */
        page.detailInput.setTorihiki = function (data) {
            page.detailInput.element.findP("cd_torihiki").val(data.cd_torihiki).change();
            page.detailInput.element.findP("nm_torihiki").text(data.nm_torihiki);
        };
--%>
            //TODO-END: 

            /**
             * jQuery イベントで、ページの読み込み処理を定義します。
             */
            $(function () {
                // ページの初期化処理を呼び出します。
                page.initialize();
            });

    </script>

</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">

    <div class="content-wrap smaller">
        <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
        <div class="header">
            <div title="検索条件" class="part">
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>製法番号<span style="color: red">*</span></label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="tel" maxlength="4" style="width: 45px; ime-mode: disabled" class="limit-input-digit" data-prop="no_seiho_kaisha">
                        -
                        <select style="width: 60px;" data-prop="no_seiho_shurui"></select>
                        -
                        <input type="tel" maxlength="2" style="width: 30px; ime-mode: disabled" data-prop="no_seiho_nen" class="limit-input-digit">
                        -
                        <input type="tel" style="width: 45px; ime-mode: disabled" maxlength="4" data-prop="no_seiho_renban" class="limit-input-digit">
                    </div>
                    <div class="control-label col-xs-1">
                        <label>製法名</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="text" data-prop="nm_seiho" />
                    </div>
                    <div class="control-label col-xs-1">
                        <label>製法分類</label>
                    </div>
                    <div class="control col-xs-3">
                        <select style="width: 50%" data-prop="cd_seiho_bunrui" class="number"></select>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>製法作成日</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="tel" data-app-format="date" data-role='date' class="data-app-format" style="width: 100px" data-prop="dt_seiho_sakusei_start">
                        -
                        <input type="tel" data-app-format="date" data-role='date' class="data-app-format" style="width: 100px" data-prop="dt_seiho_sakusei_end">
                    </div>
                    <div class="control-label col-xs-1">
                        <label>製法作成者１・２</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="text" data-prop="nm_seiho_sakusei" />
                    </div>
                    <div class="control-label col-xs-1">
                        <label>ステータス</label>
                    </div>
                    <div class="control col-xs-1">
                        <select data-prop="status"></select>
                    </div>
                    <div class="control col-xs-2">
                        <label>
                            <input type="checkbox" style="vertical-align: middle" value="true" data-prop="kbn_haishi" />
                            廃止も含む
                        </label>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>製品コード</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="tel" style="width: 100px; float: left; margin-right: 5px; ime-mode: disabled" class="limit-input-digit" maxlength="6" data-prop="cd_seihin">
                        <button id="open-seihinKensaku" style="float: left" class="btn btn-sm btn-info">検索</button>
                        <label data-prop="label_seihin" class="overflow-ellipsis" style="float: left; width: calc(98% - 165px)"></label>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>製品名</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="text" data-prop="nm_seihin" />
                    </div>
                    <div class="control-label col-xs-1">
                        <label>配合名/コード</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="text" data-prop="nm_haigo" />
                    </div>
                </div>
                <div class="row">
                    <div class="control col-xs-1">
                        <div class="control-label col-xs-8">
                            <label>製造会社</label>
                        </div>
                        <div class="control col-xs-4">
                            <label><input data-prop="def" type="radio" name="def" checked="checked" value="0" style="vertical-align: sub;" /></label>
                        </div>
                    </div>
                    <div class="control col-xs-3 fix-input-xs">
                        <input type="tel" style="ime-mode: disabled" class="fixed limit-input-digit" maxlength="4" data-prop="cd_kaisha_lg" disabled>
                        <%--<label data-prop="cd_kaisha_validate" style="display: none;"></label>--%>
                        <input type="tel" style="ime-mode: disabled; margin-left: 5px !important;" class="floated" data-prop="nm_kaisha_lg" disabled>
                    </div>
                    <div class="control col-xs-8">
                    </div>
                </div>
                <div class="row">
                    <div class="control col-xs-12"></div>
                </div>
                <div class="row">
                    <div class="control col-xs-1">
                        <div class="control-label col-xs-8">
                            <label>代表会社<span style="color: red">*</span></label>
                        </div>
                        <div class="control col-xs-4">
                            <label><input data-prop="choose" type="radio" name="def" value="1" style="vertical-align: sub;" /></label>
                        </div>
                    </div>
                    <div class="control col-xs-3 fix-input-xs">
                        <input type="tel" style="ime-mode: disabled" class="fixed limit-input-digit" maxlength="4" data-prop="cd_kaisha" disabled>
                        <label data-prop="cd_kaisha_validate" style="display: none;"></label>
                        <select class="floated" data-prop="nm_kaisha" disabled></select>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>代表工場</label>
                    </div>
                    <div class="control col-xs-3 fix-input-xs">
                        <input type="tel" style="ime-mode: disabled" class="fixed limit-input-digit" maxlength="4" data-prop="cd_kojyo" disabled>
                        <label data-prop="cd_kojyo_validate" style="display: none;"></label>
                        <select class="floated" data-prop="nm_kojyo" disabled></select>
                    </div>
                    <%-- <div class="control col-xs-3 fix-input-xs">
                        <input type="text" data-prop="cd_kojyo" maxlength="4" class="fixed limit-input-int" style="ime-mode: disabled;" />
                        <label data-prop="cd_kojyo_validate" style="display: none;"></label>
                        <select data-prop="nm_kojyo" class="floated"></select>
                    </div>--%>
                    <div class="control-label col-xs-1">
                        <label>原料製造会社名</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="text" data-prop="nm_seizo">
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>原料コード</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="tel" style="width: 125px; float: left; margin-right: 5px; ime-mode: disabled" class="limit-input-digit" maxlength="7" data-prop="cd_hin">
                        <button id="open-hinmeiKaihatsu" style="float: left" class="btn btn-sm btn-info">検索</button>
                        <label data-prop="label_nmhin" class="overflow-ellipsis" style="float: left; width: calc(98% - 190px)"></label>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>規格書No.</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="tel" style="width: 12%; ime-mode: disabled" class="limit-input-digit" maxlength="4" data-prop="no_kikaku1">
                        -
                            <input type="tel" style="width: 15%; ime-mode: disabled" class="limit-input-digit" maxlength="6" data-prop="no_kikaku2" />
                        -
                            <input type="tel" style="width: 13%; ime-mode: disabled" class="limit-input-digit" maxlength="5" data-prop="no_kikaku3">
                    </div>
                    <div class="control-label col-xs-1">
                        <label>原料販売会社名</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="text" data-prop="nm_hanbai">
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>原料名</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="text" data-prop="nm_hin">
                    </div>
                    <div class="control-label col-xs-1">
                        <label>規格書名</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="text" data-prop="nm_kikaku">
                    </div>
                    <div class="control col-xs-4">
                    </div>
                </div>

                <div class="header-command">
                    <label>並べ替え順（製法作成日)</label>
                    <label style="margin-right: 2px">
                        <input data-prop="asc" type="radio" name="checkbox" value="0" checked="checked" style="vertical-align: sub; margin-right: 5px" />昇順</label>
                    <label>
                        <input data-prop="desc" type="radio" name="checkbox" value="1" style="margin-right: 5px; vertical-align: sub" />降順</label>
                    <button type="button" id="search" class="btn btn-sm btn-primary">検索</button>
                </div>
            </div>
        </div>
        <!--TODO: 明細を定義するHTMLをここに記述します。-->
        <div class="detail">
            <!--TODO: 明細をpartにする場合は以下を利用します。
            <div title="TODO: 明細一覧部のタイトルを設定します。" class="part">
            -->
            <div class="control-label toolbar">
                <i class="icon-th"></i>
                <div class="btn-group">
                    <button type="button" class="btn btn-default btn-xs connect" id="add-item" disabled style="display: none">新規</button>
                </div>
                <span class="data-count">
                    <span data-prop="data_count"></span>
                    <span>/</span>
                    <span data-prop="data_count_total"></span>
                </span>
            </div>
            <table class="datatable fix-table ellipsis">
                <!--TODO: 明細一覧のヘッダーを定義するHTMLをここに記述します。-->
                <thead>
                    <tr>
                        <!--TODO: 単一行を作成する場合は、下記を利用します。-->
                        <th style="width: 10px;"></th>
                        <th style="width: 40px;">詳細</th>
                        <th style="width: 140px;">製法番号</th>
                        <th style="width: 320px;">製法名</th>
                        <th style="width: 70px;">配合コード</th>
                        <th style="width: 320px;">配合名</th>
                        <th style="width: 50px;">品区分</th>
                        <th style="width: 150px;">製品コード</th>
                        <th style="width: 80px;">代表工場名</th>
                        <th style="width: 80px;">ステータス</th>
                        <th style="width: 90px;">製法作成日</th>
                        <th style="width: 50px;">廃止</th>
                        <!--TODO: 多段行を作成する場合は、以下を利用し、上記１行は削除します。
							<th rowspan="2" style="width: 10px;"></th>
                       	</tr>
                        <tr>
						-->
                    </tr>
                </thead>
                <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->
                <tbody class="item-tmpl" style="cursor: default; display: none;">
                    <tr>
                        <!--TODO: 単一行を作成する場合は、以下を利用します。-->
                        <td>
                            <span class="select-tab unselected"></span>
                        </td>
                        <td class="center">

                            <button class="edit-select btn btn-info btn-xs" disabled>詳細</button>
                        </td>
                        <td>
                            <label data-prop="no_seiho"></label>
                        </td>
                        <td>
                            <label data-prop="nm_seiho"></label>
                        </td>
                        <td>
                            <label data-prop="cd_haigo"></label>
                        </td>
                        <td>
                            <label data-prop="nm_haigo"></label>
                        </td>
                        <td class="center">
                            <label data-prop="nm_kbn_hin"></label>
                        </td>
                        <td>
                            <label data-prop="cd_seihin"></label>

                        </td>
                        <td>
                            <label data-prop="nm_busho"></label>
                        </td>
                        <td>
                            <label data-prop="nm_literal"></label>
                        </td>
                        <td>
                            <label data-prop="dt_seiho_sakusei"></label>
                        </td>
                        <td class="center">
                            <input type="checkbox" data-prop="kbn_haishi" value="true" disabled />
                        </td>
                        <!--TODO: 多段行を作成する場合は、以下を利用し、上記３行は削除します。
							
                            <td rowspan="2">
                                <span class="select-tab-2lines unselected"></span>
                            </td>
                        </tr>
                        <tr>
                        -->
                    </tr>
                </tbody>
            </table>
            <div class="detail-command">
                <button type="button" id="nextsearch" class="btn btn-sm btn-primary btn-next-search" style="display: none">次を検索</button>
            </div>
            <div class="part-command">
            </div>
            <!--TODO: 明細をpartにする場合は以下を利用します。
            </div>
            -->
        </div>

        <!--TODO: 単票入力・編集画面を定義するHTMLをここに記述します。-->
        <div class="detailInput" style="display: none;">

            <ul class="pager">
                <li class="previous"><a href="#">&larr; 一覧に戻る</a></li>
            </ul>

            <div title="TODO: 入力項目部のタイトルを設定します。" class="part">
                <div class="row">
                </div>
            </div>
        </div>

    </div>
</asp:Content>

<asp:Content ID="FooterContent" ContentPlaceHolderID="FooterContent" runat="server">
    <div class="command-left">
        <!--TODO: コマンドボタン（左寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" id="reset" class="btn btn-sm btn-primary" style="display: none;">クリア</button>
        <button type="button" id="CSV" class="btn btn-sm btn-primary" style="display: none;" disabled>CSV</button>

    </div>
    <div class="command">
        <!--TODO: コマンドボタン（右寄せ配置）を定義するHTMLをここに記述します。-->
        <%--<button type="button" id="addItem" class="btn btn-sm btn-primary" style="display: none;">保存</button>
        <button type="button" id="editItem" class="btn btn-sm btn-primary" disabled="disabled" style="display: none;">保存</button>
        <button type="button" id="deleteItem" class="btn btn-sm btn-default" style="display: none;">削除</button>--%>
        <button type="button" id="shisaku-copy" class="btn btn-sm btn-primary" style="display: none;" disabled>試作コピー</button>
        <button type="button" id="300_SeihoBunshoSakusei" class="btn btn-sm btn-primary" style="display: none;" disabled>申請/取消/承認</button>
        <button type="button" id="203_HaigoTorokuKaihatsuBumon_Copy" class="btn btn-sm btn-primary" style="display: none;" disabled>製法新規コピー</button>
        <button type="button" id="203_HaigoTorokuKaihatsuBumon_Renew" class="btn btn-sm btn-primary" style="display: none;" disabled>リニューアルコピー</button>
        <button type="button" id="203_HaigoTorokuKaihatsuBumon_Create" class="btn btn-sm btn-primary" style="display: none;" disabled>配合新規</button>
        <button type="button" id="deleteHaigo" class="btn btn-sm btn-primary" style="display: none;" disabled>配合削除</button>
        <button type="button" id="202_G_LabelRenkei" class="btn btn-sm btn-primary" style="display: none;" disabled>G-Label連携</button>
        <button type="button" id="211_" class="btn btn-sm btn-primary" style="display: none;" disabled>添付資料</button>
        <button type="button" id="802_CopyHaniSelection_Dialog" class="btn btn-sm btn-primary" style="display: none;">製法文書コピー</button>


    </div>

</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>
