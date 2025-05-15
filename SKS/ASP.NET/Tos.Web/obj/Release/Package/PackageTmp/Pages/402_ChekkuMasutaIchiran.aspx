<%@ Page Title="402_チェックマスタ" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="402_ChekkuMasutaIchiran.aspx.cs" Inherits="Tos.Web.Pages._402_ChekkuMasutaIchiran" %>

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
            width: 75px;
        }

        textarea {
            width: 100%;
            height: 100%;
            resize: none;
        }

        .control-textarea {
            height: 100px;
        }

        .command-left-custom {
            right: 15px !important;
            left: unset !important;
        }

        .datatable tbody textarea {
            height: 100px;
            margin-bottom: -7px;
            margin-top: -2px;
            margin-left: -2px;
            width: 101.6% !important;
        }

        .disabled-bg {
            background: white !important;
        }

        .datatable tr.selected-row textarea {
            background-color: #fffed4 !important;
        }
        textarea:disabled {
            background: #efefef;
        }
    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("_402_ChekkuMasutaIchiran", {
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.dataTakeCount,   // TODO:取得するデータ数を指定します。
                filter: ""
            },
            values: {
                isChangeRunning: {},
                isChangeDetail: false,
                noSort: 1,
                transferPage404: "../Pages/404_KoshinRireki.aspx",
                transferPage403: "../Pages/403_KanrenShiryo.aspx",
                idFunctionPage: 2,
                cd_shain: 0,
                nen: 0,
                no_oi: 0,
                no_meisai: 0,
                mode: App.settings.app.mode_SekkeiHinshutsuGeto.edit
            },
            urls: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
                save: "../api/_402_ChekkuMasutaIchiran/Post",
                ma_gate_check: "../Services/ShisaQuickService.svc/ma_gate_check?$filter=no_gate eq {0} and no_bunrui eq {1} and no_check eq {2}",
                kanrenShiryo: "403_KanrenShiryo.aspx",
                deleteItems: "../api/_402_ChekkuMasutaIchiran/DeleteFiles"
            },
            header: {
                options: {},
                values: {
                    currentGate: App.settings.app.no_gate_val.no_gate_1,
                    currentBunrui: null
                },
                urls: {
                    ma_gate_bunrui: "../Services/ShisaQuickService.svc/ma_gate_bunrui?$filter=no_gate eq {0}&$orderby=no_sort",
                    search: "../api/_402_ChekkuMasutaIchiran"
                }
            },
            detail: {
                options: {},
                values: {}
            },
            detailInput: {
                options: {
                    changeCheck: false
                },
                values: {
                    maxNoCheck: 1,
                    defaultNoCheck: 1,
                    defaultNoSort: 1
                },
                urls: {
                    ma_gate_check: "../Services/ShisaQuickService.svc/ma_gate_check?$filter=",
                    getNoCheck: "../api/_402_ChekkuMasutaIchiran/getSaiban"
                },
                mode: {
                    input: "input",
                    edit: "edit"
                }
            },
            dialogs: {},
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
         * show page 関連資料
         */
        page.commands.showKanrenShiryo = function () {

            var element = page.detailInput.element,
            noCheckVal = element.findP("no_check").val(),
            noCheck = noCheckVal == "" ? page.detailInput.values.maxNoCheck : noCheckVal;

            var pathLink = page.values.transferPage403
                + "?cd_shain=" + page.values.cd_shain
                + "&nen=" + page.values.nen
                + "&no_oi=" + page.values.no_oi
                + "&no_gate=" + element.findP("no_gate").val()
                + "&no_bunrui=" + element.findP("no_bunrui").val()
                + "&no_check=" + noCheck
                + "&no_meisai=" + page.values.no_meisai
                + "&mode=" + page.values.mode;

            window.open(pathLink);
        }

        /**
         * show page 更新履歴一覧
         */
        page.commands.showHistory = function () {
            var pathLink = page.values.transferPage404 + "?id_function=" + page.values.idFunctionPage;
            window.open(pathLink);
        }

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
            App.ui.wait(sleep, condition, 100).then(function () {
                page.validateAll().then(function () {
                    page.dialogs.confirmDialog.confirm({ text: App.messages.app.AP0180 }).then(function () {

                        var element = page.detailInput.element,
                            flg_check_disp = element.findP("flg_check_disp").val();

                        page.detail.data.add(page.detailInput.data.getChangeSet().created[0]);

                        var changeSets = page.detail.data.getChangeSet();

                        if (changeSets.created.length > 0) {
                            changeSets.created[0].no_gate = element.findP("no_gate").val();
                            changeSets.created[0].no_bunrui = element.findP("no_bunrui").val();
                            changeSets.created[0].kbn_disp = element.findP("kbn_disp").val();
                            changeSets.created[0].flg_check_disp = flg_check_disp == App.settings.app.flg_check_disp_val.show ? true : false;
                        }

                        return $.ajax(App.ajax.webapi.post(page.urls.save, changeSets)).then(function (result) {

                            page.detailInput.previous(false);
                            return App.async.all([page.header.search(false)]);
                        }).then(function () {
                            App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                        }).fail(function (error) {

                            if (error.status === App.settings.base.conflictStatus) {
                                page.header.search(false);
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
            App.ui.wait(sleep, condition, 100).then(function () {
                page.validateAll().then(function () {
                    page.dialogs.confirmDialog.confirm({ text: App.messages.app.AP0180 }).then(function () {

                        var changeSets = page.detailInput.data.getChangeSet();
                        return $.ajax(App.ajax.webapi.post(page.urls.save, changeSets)).then(function (result) {
                        }).then(function (result) {

                            return page.detail.updateData(changeSets);
                        }).then(function (result) {

                            page.detailInput.previous(false);
                            App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                        }).fail(function (error) {

                            if (error.status === App.settings.base.conflictStatus) {
                                // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                                page.detailInput.loadData();
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

        //load data
        page.detailInput.loadData = function () {

            var deferred = $.Deferred(),
                element = page.detailInput.element,
                no_gate = element.findP("no_gate").val(),
                no_bunrui = element.findP("no_bunrui").val(),
                no_check = element.findP("no_check").val(),
                url = page.detailInput.urls.ma_gate_check + "no_gate eq " + no_gate + " and no_bunrui eq " + no_bunrui + "and no_check eq " + no_check;

            App.ui.loading.show();

            $.ajax(App.ajax.odata.get(url)).then(function (result) {
                deferred.resolve();
                page.detailInput.data = App.ui.page.dataSet();
                page.detailInput.bind(result.value[0]);
                page.detailInput.data.attach(result.value[0]);

            }).fail(function (error) {

                deferred.reject(error);
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            }).always(function () {

                App.ui.loading.close();
            });
        }

        /**
         * すべてのバリデーションを実行します。
         */
        page.validateAll = function () {

            var validations = [];

            validations.push(page.detailInput.validator.validate());

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

            page.initializeControl();
            page.initializeControlEvent();

            page.header.initialize();
            page.detail.initialize();
            page.detailInput.initialize();

            page.loadMasterData().then(function (result) {

                return page.loadDialogs();
            }).then(function (result) {

                page.header.search();
                return $.ajax(App.ajax.webapi.post(page.urls.deleteItems, { isSekkei: false , userLogin: App.ui.page.user.EmployeeCD}));
            }).fail(function (error) {

                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            }).always(function (result) {

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
            $("#addItem").on("click", page.commands.addItem);
            $("#editItem").on("click", page.commands.editItem);
            $("#deleteItem").on("click", page.commands.deleteItem);
            $("#save").on("click", page.commands.save);
            $("#historyItem").on("click", page.commands.showHistory);
            $("#btnKanrenShiryo").on("click", page.commands.showKanrenShiryo);
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
            App.ui.wait(sleep, condition, 100).then(function () {

                page.validateAll().then(function () {

                    page.dialogs.confirmDialog.confirm({ text: App.messages.app.AP0180 }).then(function () {

                        var changeSets = page.detail.data.getChangeSet();

                        return $.ajax(App.ajax.webapi.post(page.urls.save, changeSets)).then(function (result) {

                            page.values.isChangeDetail = false;
                            //最後に再度データを取得しなおします。
                            return App.async.all([page.header.search(true)]);
                        }).then(function () {

                            App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                        }).fail(function (error) {

                            if (error.status === App.settings.base.conflictStatus) {

                                // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                                page.values.isChangeDetail = false;
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
                    });
                });
            }).fail(function () {
                App.ui.page.notifyAlert.clear();
                App.ui.page.notifyAlert.message(App.messages.base.MS0006).show();
            }).always(function () {
                setTimeout(function () {
                    page.header.element.find(":input:first").focus();
                }, 100);
                App.ui.loading.close();
            });
        };

        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function () {

            var noGate = App.settings.app.no_gate_val.no_gate_1,
                url = page.header.urls.ma_gate_bunrui;

            return $.ajax(App.ajax.odata.get(App.str.format(url, noGate))).then(function (result) {

                var no_gate = $(".header").findP("no_gate"),
                    no_bunrui = $(".header").findP("no_bunrui"),
                    flg_check_disp = $(".detailInput").findP("flg_check_disp"),
                    kbn_disp = $(".detailInput").findP("kbn_disp");

                no_gate.children().remove();
                no_bunrui.children().remove();
                flg_check_disp.children().remove();
                kbn_disp.children().remove();

                App.ui.appendOptions(
                    no_bunrui,
                    "no_bunrui",
                    "nm_bunrui",
                    result.value
                );

                App.ui.appendOptions(
                    no_gate,
                    "value",
                    "text",
                    App.settings.app.no_gate
                );

                App.ui.appendOptions(
                    flg_check_disp,
                    "value",
                    "text",
                    App.settings.app.flg_check_disp
                );

                App.ui.appendOptions(
                    kbn_disp,
                    "value",
                    "text",
                    App.settings.app.kbn_disp
                );

                page.header.values.currentBunrui = result.value.length > 0 ? result.value[0].no_bunrui : null;
            });
        };

        /**
         * 画面明細の一覧で選択されている行とデータを削除します。
         */
        page.detail.deleteItem = function (e) {
            var element = page.detail.element,
                selected = element.find(".datatable .select-tab.selected").closest("tbody");

            if (!selected.length) {
                return;
            }
            page.values.isChangeDetail = true;
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

                newSelected = row.next().not(".item-tmpl");

                if (!newSelected.length) {
                    newSelected = row.prev().not(".item-tmpl");
                }
                if (newSelected.length) {

                    for (var i = page.detail.fixedColumnIndex; i > -1; i--) {
                        if ($(newSelected[i]).find(":focusable:first").length) {
                            $(newSelected[i]).find(":focusable:first").focus();
                            break;
                        }
                    }
                }
            });

            if ($("#save").is(":disabled")) {
                $("#save").prop("disabled", false);
            }

            page.detail.controlNosort();
        };
        /**
         * 共有ダイアログのロード処理を実行します。
         */
        page.loadDialogs = function () {

            return App.async.all({

                confirmDialog: $.get(page.urls.confirmDialog),
            }).then(function (result) {

                $("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;

            });
        };

        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.validations = {
            no_gate: {
                rules: {
                    required: true
                },
                options: {
                    name: "ゲート"
                },
                messages: {
                    required: App.messages.base.required,
                }
            },
            no_bunrui: {
                rules: {
                    required: true
                },
                options: {
                    name: "分類"
                },
                messages: {
                    required: App.messages.base.required,
                }
            }
        };

        /**
         * 画面ヘッダーの初期化処理を行います。
         */
        page.header.initialize = function () {

            var element = $(".header");

            page.header.validator = element.validation(page.createValidator(page.header.options.validations));
            page.header.element = element;

            element.on("change", ":input", page.header.change);

        };

        /**
         * 画面ヘッダーの変更時処理を定義します。
         */
        page.header.change = function (e) {

            var element = page.header.element,
                target = $(e.target),
                property = target.attr("data-prop"),
                no_gate = element.findP("no_gate").val(),
                no_bunrui = element.findP("no_bunrui"),
                url = App.str.format(page.header.urls.ma_gate_bunrui, no_gate);

            if (!page.values.isChangeDetail) {
                page.header.values.currentGate = element.findP("no_gate").val();
            }
            if (property === "no_gate") {
                target = element.findP("no_bunrui");
                page.loadChildrenCombobox(url, no_bunrui, "no_bunrui", "nm_bunrui", true);
            }
            if (property === "no_bunrui") {
                page.header.search();
            }
        };

        /*
         * Load children Combobox
         */
        page.loadChildrenCombobox = function (url, elementChild, cdOption, nameOption, isSearch) {
            return $.ajax(App.ajax.odata.get(url)).then(function (result) {
                elementChild.children().remove();

                $.each(result.value, function (index, option) {
                    if (option[nameOption] == null) {
                        option[nameOption] = "";
                    }
                });

                App.ui.appendOptions(
                    elementChild,
                    cdOption,
                    nameOption,
                    result.value
                );

                if (isSearch) {
                    page.header.values.currentBunrui = result.value.length > 0 ? result.value[0].no_bunrui : null;
                }
            }).then(function () {
                if (isSearch) {
                    page.header.search();
                }
            });
        }

        /**
         * 検索処理を定義します。
         */
        page.header.search = function (isLoading) {

            var deferred = $.Deferred(),
                element = page.header.element;

            page.options.skip = 0;
            page.options.filter = page.header.createFilter();

            if (page.values.isChangeDetail) {
                page.dialogs.confirmDialog.confirm({ text: App.messages.app.AP0168 }).then(function () {

                    page.header.values.currentBunrui = element.findP("no_bunrui").val();
                    page.header.values.currentGate = element.findP("no_gate").val();
                    page.values.isChangeDetail = false;
                    $("#save").prop("disabled", true);
                    searchHeader();

                }).fail(function (error) {

                    var no_gate = page.header.values.currentGate,
                        no_bunrui = element.findP("no_bunrui"),
                        url = App.str.format(page.header.urls.ma_gate_bunrui, no_gate);

                    page.loadChildrenCombobox(url, no_bunrui, "no_bunrui", "nm_bunrui", false);

                    element.findP("no_gate").val(page.header.values.currentGate);
                    element.findP("no_bunrui").val(page.header.values.currentBunrui);
                });
            }
            else {
                searchHeader();
            }

            function searchHeader() {
                page.header.validator.validate().done(function () {
                    if (isLoading) {
                        App.ui.loading.show();
                    }
                    App.ui.page.notifyAlert.clear();
                    return $.ajax(App.ajax.webapi.get(page.header.urls.search, page.options.filter)).done(function (result) {
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
                        App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                        deferred.reject();
                    }).always(function () {
                        if (isLoading) {
                            App.ui.loading.close();
                        }
                    });
                });
            }

            return deferred.promise();
        };

        /**
         * 検索条件のフィルターを定義します。
         */
        page.header.createFilter = function () {
            var criteria = page.header.element.form().data();

            var filter = {
                no_gate: criteria.no_gate,
                no_bunrui: criteria.no_bunrui,
                nm_ari: App.settings.app.honbu_kengen.ari,
                nm_nashi: App.settings.app.honbu_kengen.nashi,
                skip: page.options.skip,
                top: page.options.top
            };
            return filter;
        };

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
                    onselect: page.detail.select,
                    onchange: page.detail.change
                });
            table = element.find(".datatable");         //列固定にした場合DOM要素が再作成されるため、変数を再取得

            page.detail.element = element;
            page.detail.dataTable = datatable;

            element.on("click", "#nextsearch", page.detail.nextsearch);
            element.on("click", "#add-item", page.detail.addNewItem);
            element.on("click", "#del-item", page.detail.deleteItem);
            element.on("click", "#up-sort", page.detail.moveUp);
            element.on("click", "#down-sort", page.detail.moveDown);

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

            table.on("click", ".edit-select", page.detail.showEditPart);

        };

        /**
         * 次のレコードを検索する処理を定義します。
         */
        page.detail.nextsearch = function () {

            App.ui.loading.show();
            App.ui.page.notifyAlert.clear();

            page.options.filter = page.header.createFilter();

            return $.ajax(App.ajax.webapi.get(page.header.urls.search, page.options.filter)).done(function (result) {
                page.detail.bind(result);
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            }).always(function () {
                App.ui.loading.close();
            });

        };

        /**
         * 画面明細の各行にデータを設定する際のオプションを定義します。
         */
        page.detail.options.bindOption = {
            appliers: {
                kbn_disp: function (value, element) {
                    if (value == App.settings.app.kbn_disp_val.kbn_disp_1) {
                        element.text(App.settings.app.kbn_disp[0].text);
                    }
                    else if (value == App.settings.app.kbn_disp_val.kbn_disp_2) {
                        element.text(App.settings.app.kbn_disp[1].text);
                    }
                    else if (value == App.settings.app.kbn_disp_val.kbn_disp_3) {
                        element.text(App.settings.app.kbn_disp[2].text);
                    }
                    return true;
                },
                flg_check_disp: function (value, element) {
                    if (value == App.settings.app.flg_check_disp_val.hide) {
                        element.text(App.settings.app.flg_check_disp[1].text);
                    }
                    else if (value == App.settings.app.flg_check_disp_val.show) {
                        element.text(App.settings.app.flg_check_disp[0].text);
                    }
                    return true;
                }
            }
        };

        /*
      *   Up value sort
      */
        page.detail.moveUp = function (e) {
            var element = page.detail.element,
                selected = element.find(".datatable .select-tab.selected").closest("tbody"),
                rowIdCurr = undefined,
                firstSort = 0;

            if (!selected.length) {
                return;
            }

            page.values.isChangeDetail = true;

            if (selected.prev().not(".item-tmpl").length < 1) {
                return;
            }
            var id = selected.attr("data-key"),
                entity = page.detail.data.entry(id);

            var prev1, prev2, tbody;
            page.detail.dataTable.dataTable("getRow", selected, function (rowObject) {
                selected = rowObject.element;
            });

            prev1 = element.find(selected[0]).prev();
            prev2 = element.find(selected[1]).prev();

            var idPrev = prev1.attr("data-key"),
                entityPrev = page.detail.data.entry(idPrev);

            element.find(selected[0]).detach().insertBefore(element.find(prev1));
            element.find(selected[1]).detach().insertBefore(element.find(prev2));

            var newSort = $.extend(true, {}, entity);

            entity["no_sort"] = entityPrev["no_sort"];
            entityPrev["no_sort"] = newSort["no_sort"];
            page.detail.data.update(entity);
            page.detail.data.update(entityPrev);

            $("#save").prop("disabled", false);
        };

        /*
        *   Down value sort
        */
        page.detail.moveDown = function (e) {
            var element = page.detail.element,
                selected = element.find(".datatable .select-tab.selected").closest("tbody"),
                rowIdCurr = undefined,
                firstSort = 0;

            if (!selected.length) {
                return;
            }

            page.values.isChangeDetail = true;

            if (selected.next().not(".item-tmpl").length < 1) {
                return;
            }
            var id = selected.attr("data-key"),
                entity = page.detail.data.entry(id);

            var prev1, prev2, tbody;
            page.detail.dataTable.dataTable("getRow", selected, function (rowObject) {
                selected = rowObject.element;
            });

            prev1 = element.find(selected[0]).next();
            prev2 = element.find(selected[1]).next();

            var idPrev = prev1.attr("data-key"),
                entityPrev = page.detail.data.entry(idPrev);

            element.find(selected[0]).detach().insertAfter(element.find(prev1));
            element.find(selected[1]).detach().insertAfter(element.find(prev2));

            var newSort = $.extend(true, {}, entity);

            entity["no_sort"] = entityPrev["no_sort"];
            entityPrev["no_sort"] = newSort["no_sort"];
            page.detail.data.update(entity);
            page.detail.data.update(entityPrev);

            $("#save").prop("disabled", false);
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

            offsetHeight = $("#nextsearch").is(":visible") ? $("#nextsearch").addClass("show-search").outerHeight() : 0;
            page.detail.dataTable.dataTable("setAditionalOffset", offsetHeight);
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
            if (!App.isUndefOrNull(page.detail.selectedRow)) {
                page.detail.selectedRow.element.find(".selected-row").removeClass("selected-row");
            }
            row.element.find("tr").addClass("selected-row");
            page.detail.selectedRow = row;
        };

        /**
         * 画面明細の一覧に新規データを追加します。
         */
        page.detail.addNewItem = function () {
            if (page.header.element.findP("no_bunrui").val() != null) {
                page.detail.dataTable.dataTable("getFirstViewRow", function (row) {
                    page.detail.firstViewRow = row;
                });

                var newData = {
                    no_sort: page.values.noSort
                };
                page.detailInput.options.changeCheck = false;
                page.detailInput.options.mode = page.detailInput.mode.input;

                if (page.values.isChangeDetail) {
                    page.dialogs.confirmDialog.confirm({ text: App.messages.app.AP0169 }).then(function () {
                        page.values.isChangeDetail = false;
                        page.detailInput.show(newData);
                        page.header.search();
                    });
                }
                else {
                    page.detailInput.show(newData);
                }
            }
        };

        /**
         * 画面明細の一覧から編集画面を表示します。
         */
        page.detail.showEditPart = function (e) {

            var element = page.detail.element,
                target = $(e.target),
                row = target.closest("tbody"),
                id = row.attr("data-key"),
                entity = page.detail.data.entry(id);

            page.detail.dataTable.dataTable("getFirstViewRow", function (row) {
                page.detail.firstViewRow = row;
            });

            page.detailInput.options.changeCheck = false;
            page.detailInput.options.mode = page.detailInput.mode.edit;

            if (page.values.isChangeDetail) {
                page.dialogs.confirmDialog.confirm({ text: App.messages.app.AP0169 }).then(function () {

                    var no_gate = page.header.element.findP("no_gate").val(),
                        no_bunrui = page.header.element.findP("no_bunrui").val();

                    $("#save").prop("disabled", true);
                    page.values.isChangeDetail = false;

                    return $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_gate_check, entity.no_gate, entity.no_bunrui, entity.no_check))).then(function (result) {
                        page.detailInput.show(result.value[0]);
                        page.header.search();
                    });
                });
            }
            else {
                page.detailInput.show(entity);
            }
        };

        /**
         * 編集画面で更新された画面明細の行を最新化します。
         */
        page.detail.updateData = function (result) {

            // 更新されたデータのキーを取得します。
            var noGate = result.updated[0].no_gate,
                noBunrui = result.updated[0].no_bunrui,
                noCheck = result.updated[0].no_check;

            // キーをもとに最新のデータを取得します。
            return $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_gate_check, noGate, noBunrui, noCheck))).then(function (result) {
                var newData = result.value[0];
                if (!newData) {
                    return;
                }

                page.detail.dataTable.dataTable("each", function (row, index) {
                    var entity = page.detail.data.entry(row.element.attr("data-key"));
                    if (entity.no_gate == noGate && entity.no_bunrui == noBunrui && entity.no_check == noCheck) {
                        //対象データの各値を最新の値で更新します。
                        Object.keys(newData).forEach(function (key) {
                            if (key in entity) {
                                entity[key] = newData[key];
                            }
                        });
                        //行にデータを再バインドします。
                        row.element.find("[data-prop]").val("").text("");
                        row.element.form(page.detail.options.bindOption).bind(entity);
                    }
                });
            });
        };

        /**
         * Control no_sort
         */
        page.detail.controlNosort = function () {
            var no_sort = 1;
            $.each(page.detail.element.find(".datatable").find("tbody").not(".item-tmpl"), function (idx, tbody) {
                var id = $(tbody).attr("data-key"),
                    entity = page.detail.data.entry(id);

                if (entity.no_sort != no_sort) {
                    entity["no_sort"] = no_sort;
                    page.detail.data.update(entity);
                }
                no_sort++
            });
        }

        /**
         * 画面明細の一覧の入力項目の変更イベントの処理を行います。
         */
        page.detail.change = function (e, row) {
            var target = $(e.target),
                id = row.element.attr("data-key"),
                property = target.attr("data-prop"),
                entity = page.detail.data.entry(id),
                options = {
                    filter: page.detail.validationFilter
                };

            page.values.isChangeRunning[property] = true;

            page.detail.executeValidation(target, row).then(function () {
                entity[property] = row.element.form().data()[property];

                page.detail.data.update(entity);

                if ($("#save").is(":disabled")) {
                    $("#save").prop("disabled", false);
                }

                //入力行の他の項目のバリデーション（必須チェック以外）を実施します
                page.detail.executeValidation(row.element.find(":input"), row, options);
            }).always(function () {
                delete page.values.isChangeRunning[property];
            });
        };

        /**
         * 編集画面のバリデーションを定義します。
         */
        page.detailInput.options.validations = {
            nm_check_bunrui: {
                rules: {
                    maxlength: 30
                },
                options: {
                    name: "チェック分類"
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                }
            },
            nm_check: {
                rules: {
                    maxlength: 2000
                },
                options: {
                    name: "チェック項目"
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                }
            },
            nm_check_note1: {
                rules: {
                    maxlength: 2000
                },
                options: {
                    name: "入力欄１"
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                }
            },
            nm_check_note2: {
                rules: {
                    maxlength: 2000
                },
                options: {
                    name: "入力欄２"
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                }
            },
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

            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            var element = page.detailInput.element,
                noGate = page.header.element.find("#no_gate :selected").val(),
                noBunrui = page.header.element.find("#no_bunrui :selected").val();

            element.find(":input:not([type='checkbox']):not([type='radio'])").val("");
            element.find("input[type='checkbox']").prop('checked', false);
            $("#historyItem").hide();
            page.detailInput.bind(data);

            if (page.detailInput.options.mode === page.detailInput.mode.input) {
                
                var items = {},
                    selected = page.detail.element.find(".datatable .select-tab.selected").closest("tbody");

                page.detailInput.data = App.ui.page.dataSet();

                $("#addItem").show();
                $("#editItem").hide();
                $("#deleteItem").hide();
                element.findP("kbn_disp").val(App.settings.app.kbn_disp_val.kbn_disp_1);
                element.findP("flg_check_disp").val(App.settings.app.flg_check_disp_val.show);

                if (page.header.element.find("#no_gate :selected").val() == App.settings.app.no_gate_val.no_gate_3
                    || page.header.element.find("#no_gate :selected").val() == App.settings.app.no_gate_val.no_gate_2) {
                    element.findP("nm_check_note2").prop("disabled", false);
                }
                else {
                    element.findP("nm_check_note2").prop("disabled", true);
                }

                //get max no_check
                $.ajax(App.ajax.webapi.get(page.detailInput.urls.getNoCheck, { key1: App.settings.app.saiban.saiban_402_key1, key2: App.settings.app.saiban.saiban_402_key2 })).then(function (result) {
                    if (result) {
                        page.detailInput.values.maxNoCheck = result;
                        items.no_check = page.detailInput.values.maxNoCheck;
                    }
                });

                if (selected.length) {
                    page.detail.dataTable.dataTable("getRow", selected, function (rowObject) {
                        row = rowObject.element;
                    });
                    var id = row.attr("data-key"),
                        entity = page.detail.data.entry(id),
                        no_sort = 1;
                    items.no_sort = entity.no_sort + 1;

                    $.each(page.detail.element.find(".datatable").find("tbody").not(".item-tmpl"), function (idx, tbody) {
                        var id2 = $(tbody).attr("data-key"),
                            entityDetail = page.detail.data.entry(id2);

                        if (entityDetail.no_sort > entity.no_sort) {
                            entityDetail["no_sort"] = no_sort + 1;
                            page.detail.data.update(entityDetail);
                        }

                        no_sort++
                    });
                }
                else {
                    page.detail.data = App.ui.page.dataSet();
                    items.no_check = page.detailInput.values.defaultNoCheck;
                    items.no_sort = page.detailInput.values.defaultNoSort;
                }

                page.detailInput.data.add(items);
                element.form().bind(items);
                element.validation().validate({
                    state: {
                        suppressMessage: true
                    }
                });

            }
            else {
                $("#addItem").hide();
                $("#editItem").show();
                $("#deleteItem").show();

                if (data.no_gate == App.settings.app.no_gate_val.no_gate_3 || data.no_gate == App.settings.app.no_gate_val.no_gate_2) {
                    element.findP("nm_check_note2").prop("disabled", false);
                }
                else {
                    element.findP("nm_check_note2").prop("disabled", true);
                }

                element.validation().validate();
            }

            element.find("#nm_no_gate").prop("disabled", true);
            element.find("#nm_no_bunrui").prop("disabled", true);
            element.find("#nm_no_gate").val(page.header.element.find("#no_gate :selected").text());
            element.find("#nm_no_bunrui").val(page.header.element.find("#no_bunrui :selected").text());
            element.findP("no_gate").val(page.header.element.find("#no_gate :selected").val());
            element.findP("no_bunrui").val(page.header.element.find("#no_bunrui :selected").val());

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
                    $("#historyItem").show();
                    $("#addItem").hide();
                    $("#editItem").prop("disabled", true).hide();
                    $("#deleteItem").hide();

                    App.ui.page.notifyAlert.clear();
                };

            if (page.detailInput.options.changeCheck) {
                closeMessage.text = App.messages.base.exit;
            }

            if (isConfirm && closeMessage.text) {
                page.dialogs.confirmDialog.confirm(closeMessage).then(function () {
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

            if (page.detailInput.options.mode === page.detailInput.mode.input) {
                page.detailInput.data = dataSet;
                page.detailInput.data.add.bind(dataSet)(setData);
            } else {
                page.detailInput.data = dataSet;
                page.detailInput.data.attach.bind(dataSet)(setData);
            }

            page.detailInput.element.form(page.detailInput.options.bindOption).bind(setData);
        };

        /**
        * 画面明細の各行にデータを設定する際のオプションを定義します。
        */
        page.detailInput.options.bindOption = {
            appliers: {
                flg_check_disp: function (value, element) {
                    if (value == App.settings.app.flg_check_disp_val.hide) {
                        element.val(App.settings.app.flg_check_disp_val.hide);
                    }
                    else if (value == App.settings.app.flg_check_disp_val.show) {
                        element.val(App.settings.app.flg_check_disp_val.show);
                    }
                    return true;
                }
            }
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
            page.detailInput.options.changeCheck = true;

            page.detailInput.validator.validate({
                targets: target
            }).then(function () {
                entity[property] = data[property];
                entity["no_gate"] = element.findP("no_gate").val();
                entity["no_bunrui"] = element.findP("no_bunrui").val();
                entity["kbn_disp"] = element.findP("kbn_disp").val();

                if (element.findP("flg_check_disp").val() == App.settings.app.flg_check_disp_val.show) {
                    entity["flg_check_disp"] = true;
                }
                else {
                    entity["flg_check_disp"] = false;
                }

                page.detailInput.data.update(entity);
                if ($("#editItem").is(":visible:disabled")) {
                    $("#editItem").prop("disabled", false);
                }
            }).always(function () {
                delete page.values.isChangeRunning[property];
            });
        };

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

    <div class="content-wrap">
        <div class="header">
            <div title="検察条件" class="part">
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>ゲート</label>
                    </div>
                    <div class="control col-xs-2">
                        <select data-prop="no_gate" id="no_gate"></select>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>分類</label>
                    </div>
                    <div class="control col-xs-2">
                        <select data-prop="no_bunrui" id="no_bunrui"></select>
                    </div>
                    <div class="control col-xs-6"></div>
                </div>
            </div>
        </div>

        <div class="detail">
            <div class="control-label toolbar">
                <i class="icon-th"></i>
                <div class="btn-group">
                    <button type="button" class="btn btn-default btn-xs" id="add-item">新規</button>
                    <button type="button" class="btn btn-default btn-xs" id="del-item">削除</button>
                    <button type="button" class="btn btn-default btn-xs" id="up-sort">移動▲</button>
                    <button type="button" class="btn btn-default btn-xs" id="down-sort">移動▼</button>
                </div>
                <span class="data-count">
                    <span data-prop="data_count"></span>
                    <span>/</span>
                    <span data-prop="data_count_total"></span>
                </span>
            </div>
            <table class="datatable">
                <thead>
                    <tr>
                       <th style="width: 10px;"></th>
                        <th style="width: 45px;"></th>
                        <th style="width: 200px;">表示区分</th>
                        <th style="width: 80px;">チェック</th>
                        <th>チェック分類</th>
                        <th style="width: 260px;">チェック項目</th>
                        <th style="width: 260px;">入力欄１</th>
                        <th style="width: 260px;">入力欄２</th>
                        <th style="width: 80px;">関連資料</th>
                    </tr>
                </thead>
                <tbody class="item-tmpl" style="cursor: default; display: none;">
                    <tr>
                        <td>
                            <span class="select-tab unselected"></span>
                        </td>
                        <td class="center">
                            <button type="button" class="btn btn-info btn-xs edit-select">詳細</button>
                        </td>
                        <td>
                            <label data-prop="kbn_disp" class="overflow-ellipsis"></label>
                        </td>
                        <td>
                            <label data-prop="flg_check_disp" class="overflow-ellipsis"></label>
                        </td>
                        <td>
                            <label data-prop="nm_check_bunrui" class="overflow-ellipsis"></label>
                        </td>
                        <td>
                            <textarea data-prop="nm_check" disabled="disabled" class="disabled-bg"></textarea>
                        </td>
                        <td>
                            <textarea data-prop="nm_check_note1" disabled="disabled" class="disabled-bg"></textarea>
                        </td>
                        <td>
                            <textarea data-prop="nm_check_note2" disabled="disabled" class="disabled-bg"></textarea>
                        </td>
                        <td>
                            <label data-prop="kanren_shiryo" class="overflow-ellipsis"></label>
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="detail-command">
                <button type="button" id="nextsearch" class="btn btn-sm btn-primary btn-next-search" style="display: none">次を検索</button>
            </div>
            <div class="part-command">
            </div>
        </div>
        <div class="detailInput" style="display: none;">
            <ul class="pager">
                <li class="previous"><a href="#">&larr; 一覧に戻る</a></li>
            </ul>

            <div title="" class="part">
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>ゲート</label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="text" id="nm_no_gate" class="" />
                        <input type="text" data-prop="no_gate" style="display: none;" />
                        <input type="text" data-prop="no_check" style="display: none;" />
                    </div>
                    <div class="control col-xs-1"></div>
                    <div class="control-label col-xs-1">
                        <label>分類</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="text" id="nm_no_bunrui" style="width: 100%;" />
                        <input type="text" data-prop="no_bunrui" style="display: none;" />
                    </div>
                    <div class="control col-xs-4"></div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>表示区分</label>
                    </div>
                    <div class="control col-xs-2">
                        <select data-prop="kbn_disp"></select>
                    </div>
                    <div class="control col-xs-9"></div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>チェック</label>
                    </div>
                    <div class="control col-xs-2">
                        <select data-prop="flg_check_disp"></select>
                    </div>
                    <div class="control col-xs-9"></div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>チェック分類</label>
                    </div>
                    <div class="control col-xs-7">
                        <input type="text" data-prop="nm_check_bunrui" style="width:100%;" />
                    </div>
                    <div class="control col-xs-4"></div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1 control-textarea">
                        <label>チェック項目</label>
                    </div>
                    <div class="control col-xs-7 control-textarea">
                        <textarea data-prop="nm_check"></textarea>
                    </div>
                    <div class="control col-xs-4 control-textarea"></div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1 control-textarea">
                        <label>入力欄１</label>
                    </div>
                    <div class="control col-xs-7 control-textarea">
                        <textarea data-prop="nm_check_note1"></textarea>
                    </div>
                    <div class="control col-xs-4 control-textarea">備考（全体）<br />チェック項目２（菌制御）<br />備考/リスクの具体的な内容（事前確認）<br />備考（妥当性）</div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1 control-textarea">
                        <label>入力欄２</label>
                    </div>
                    <div class="control col-xs-7 control-textarea">
                        <textarea data-prop="nm_check_note2"></textarea>
                    </div>
                    <div class="control col-xs-4 control-textarea">備考（菌制御）<br />リスクがある場合のアクションプラン（事前確認）</div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1"></div>
                    <div class="control col-xs-11">
                        <button type="button" id="btnKanrenShiryo" class="btn btn-xs btn-primary">関連資料</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

</asp:Content>

<asp:Content ID="FooterContent" ContentPlaceHolderID="FooterContent" runat="server">
    <div class="command-left command-left-custom">
        <button type="button" id="historyItem" class="btn btn-sm btn-primary">更新履歴</button>
        <button type="button" id="save" class="btn btn-sm btn-primary" disabled="disabled">保存</button>
    </div>
    <div class="command">
        <button type="button" id="addItem" class="btn btn-sm btn-primary" style="display: none;">保存</button>
        <button type="button" id="editItem" class="btn btn-sm btn-primary" disabled="disabled" style="display: none;">保存</button>
    </div>

</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>
