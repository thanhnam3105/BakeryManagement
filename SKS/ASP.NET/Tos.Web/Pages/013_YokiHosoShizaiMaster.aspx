<%@ Page Title="013_容器包装資材マスタ" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="_013_YokiHosoShizaiMaster.aspx.cs" Inherits="Tos.Web.Pages._013_YokiHosoShizaiMaster" %>

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

        /*SKS Modifed 2022/06/09 - ST*/
        .detailInput input,
        .detailInput select {
            width: calc(100% - 10px)!important;
        }
        /*SKS Modifed 2022/06/09 - ED*/
    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("_013_YokiHosoShizaiMaster", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.dataTakeCount,   // TODO:取得するデータ数を指定します。
                filter: ""
            },
            values: {
                isChangeRunning: {}
            },
            urls: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
                ma_literal: "../Services/ShisaQuickService.svc/ma_literal?$filter=cd_category eq '{0}'&$orderby=cd_literal",
                ma_yoki_hoso_shizai: "../api/_013_YokiHosoShizaiMaster"
            },
            header: {
                options: {},
                values: {},
                urls: {
                    search: "../api/_013_YokiHosoShizaiMaster/Search"
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
                values: {},
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
                    var options = {
                        text: App.messages.app.AP0004
                    };

                    page.dialogs.confirmDialog.confirm(options)
                    .then(function () {

                        var changeSets = page.detailInput.data.getChangeSet();
                        //TODO: データの保存処理をここに記述します。
                        return $.ajax(App.ajax.webapi.post(page.urls.ma_yoki_hoso_shizai, changeSets))
                            .then(function (result) {

                                //TODO: データの保存成功時の処理をここに記述します。
                                page.detailInput.previous(false);

                                //TODO: 新規行追加後に一覧再検索をする場合は、下記2行のコメントを外します
                                return App.async.all([page.header.search(false)]);
                            }).then(function () {
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
                    var options = {
                        text: App.messages.app.AP0004
                    };

                    page.dialogs.confirmDialog.confirm(options)
                    .then(function () {

                        var changeSets = page.detailInput.data.getChangeSet();
                        //TODO: データの更新処理をここに記述します。
                        return $.ajax(App.ajax.webapi.put(page.urls.ma_yoki_hoso_shizai, changeSets))
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
                                    page.detailInput.previous();
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
                text: App.messages.app.AP0051
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
                $.ajax(App.ajax.webapi["delete"](page.urls.ma_yoki_hoso_shizai, changeSets))
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

            if (page.detailInput.options.changeCheck) {
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

            //TODO: ヘッダー/明細以外の初期化の処理を記述します。

            page.loadMasterData().then(function (result) {

                return page.loadDialogs();
            }).then(function (result) {
                //TODO: 画面の初期化処理成功時の処理を記述します。

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

            //TODO: 画面全体で利用するコントロールのイベントの紐づけ処理をここに記述します。
            $("#addItem").on("click", page.commands.addItem);
            $("#editItem").on("click", page.commands.editItem);
            $("#deleteItem").on("click", page.commands.deleteItem);
            $("#clear").on("click", page.detailInput.clear);

        };

        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function () {

            //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
            return $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_literal, App.settings.app.cd_category.kbn_maker))).then(function (result) {

                var cd_maker01 = $(".detailInput").findP("cd_maker01");
                cd_maker01.children().remove();
                App.ui.appendOptions(
                    cd_maker01,
                    "cd_literal",
                    "nm_literal",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_literal, App.settings.app.cd_category.kbn_maker)));
            }).then(function (result) {
                var cd_maker02 = $(".detailInput").findP("cd_maker02");
                cd_maker02.children().remove();
                App.ui.appendOptions(
                    cd_maker02,
                    "cd_literal",
                    "nm_literal",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_literal, App.settings.app.cd_category.kbn_maker)));
            }).then(function (result) {
                var cd_maker03 = $(".detailInput").findP("cd_maker03");
                cd_maker03.children().remove();
                App.ui.appendOptions(
                    cd_maker03,
                    "cd_literal",
                    "nm_literal",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_literal, App.settings.app.cd_category.kbn_maker)));
            }).then(function (result) {
                var cd_maker04 = $(".detailInput").findP("cd_maker04");
                cd_maker04.children().remove();
                App.ui.appendOptions(
                    cd_maker04,
                    "cd_literal",
                    "nm_literal",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_literal, App.settings.app.cd_category.kbn_maker)));
            }).then(function (result) {
                var cd_maker05 = $(".detailInput").findP("cd_maker05");
                cd_maker05.children().remove();
                App.ui.appendOptions(
                    cd_maker05,
                    "cd_literal",
                    "nm_literal",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_literal, App.settings.app.cd_category.kbn_recycle_hyoji)));
            }).then(function (result) {
                var cd_recycle_hyoji01 = $(".detailInput").findP("cd_recycle_hyoji01");
                cd_recycle_hyoji01.children().remove();
                App.ui.appendOptions(
                    cd_recycle_hyoji01,
                    "cd_literal",
                    "nm_literal",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_literal, App.settings.app.cd_category.kbn_recycle_hyoji)));
            }).then(function (result) {
                var cd_recycle_hyoji02 = $(".detailInput").findP("cd_recycle_hyoji02");
                cd_recycle_hyoji02.children().remove();
                App.ui.appendOptions(
                    cd_recycle_hyoji02,
                    "cd_literal",
                    "nm_literal",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_literal, App.settings.app.cd_category.kbn_recycle_hyoji)));
            }).then(function (result) {
                var cd_recycle_hyoji03 = $(".detailInput").findP("cd_recycle_hyoji03");
                cd_recycle_hyoji03.children().remove();
                App.ui.appendOptions(
                    cd_recycle_hyoji03,
                    "cd_literal",
                    "nm_literal",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_literal, App.settings.app.cd_category.kbn_recycle_hyoji)));
            }).then(function (result) {
                var cd_recycle_hyoji04 = $(".detailInput").findP("cd_recycle_hyoji04");
                cd_recycle_hyoji04.children().remove();
                App.ui.appendOptions(
                    cd_recycle_hyoji04,
                    "cd_literal",
                    "nm_literal",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_literal, App.settings.app.cd_category.kbn_recycle_hyoji)));
            }).then(function (result) {
                var cd_recycle_hyoji05 = $(".detailInput").findP("cd_recycle_hyoji05");
                cd_recycle_hyoji05.children().remove();
                App.ui.appendOptions(
                    cd_recycle_hyoji05,
                    "cd_literal",
                    "nm_literal",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_literal, App.settings.app.cd_category.kbn_zaishitsu)));
            }).then(function (result) {
                var cd_zaishitsu01 = $(".detailInput").findP("cd_zaishitsu01");
                cd_zaishitsu01.children().remove();
                App.ui.appendOptions(
                    cd_zaishitsu01,
                    "cd_literal",
                    "nm_literal",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_literal, App.settings.app.cd_category.kbn_zaishitsu)));
            }).then(function (result) {
                var cd_zaishitsu02 = $(".detailInput").findP("cd_zaishitsu02");
                cd_zaishitsu02.children().remove();
                App.ui.appendOptions(
                    cd_zaishitsu02,
                    "cd_literal",
                    "nm_literal",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_literal, App.settings.app.cd_category.kbn_zaishitsu)));
            }).then(function (result) {
                var cd_zaishitsu03 = $(".detailInput").findP("cd_zaishitsu03");
                cd_zaishitsu03.children().remove();
                App.ui.appendOptions(
                    cd_zaishitsu03,
                    "cd_literal",
                    "nm_literal",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_literal, App.settings.app.cd_category.kbn_zaishitsu)));
            }).then(function (result) {
                var cd_zaishitsu04 = $(".detailInput").findP("cd_zaishitsu04");
                cd_zaishitsu04.children().remove();
                App.ui.appendOptions(
                    cd_zaishitsu04,
                    "cd_literal",
                    "nm_literal",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_literal, App.settings.app.cd_category.kbn_zaishitsu)));
            }).then(function (result) {
                var cd_zaishitsu05 = $(".detailInput").findP("cd_zaishitsu05");
                cd_zaishitsu05.children().remove();
                App.ui.appendOptions(
                    cd_zaishitsu05,
                    "cd_literal",
                    "nm_literal",
                    result.value,
                    true
                );
            });

            //TODO: マスタデータのロード処理を実装後、下の1行を削除してください。
            //return App.async.success();
        };

        /**
         * 共有ダイアログのロード処理を実行します。
         */
        page.loadDialogs = function () {

            //TODO: 共有ダイアログデータを取得し、画面にバインドする処理を記述します。
            return App.async.all({

                confirmDialog: $.get(page.urls.confirmDialog),
                //searchDialog: $.get(/* TODO:共有ダイアログの URL */),
            }).then(function (result) {

                $("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;

                //$("#dialog-container").append(result.successes.searchDialog);
                //page.dialogs.searchDialog = /* TODO:共有ダイアログ変数名 */;
                //page.dialogs.searchDialog.initialize();

            });
        };

        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        // SKS - Modified 2022 - ST
        page.header.options.validations = {
            //TODO: 画面ヘッダーのバリデーションの定義を記述します。
            nm_yoki_hoso_shizai: {
                rules: {
                    maxlength: 100
                },
                options: {
                    name: "容器包装資材名",
                    //byte: 30
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_maker: {
                rules: {
                    maxlength: 80
                },
                options: {
                    name: "メーカー"
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_recycle_hyoji: {
                rules: {
                    maxlength: 80
                },
                options: {
                    name: "リサイクル表示"
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
            no_shizai_kikakusho: {
                rules: {
                    number: true,
                    maxlength: 15
                },
                options: {
                    name: "資材規格書番号"
                },
                messages: {
                    number: App.messages.base.number,
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_size: {
                rules: {
                    maxbytelength: 80
                },
                options: {
                    name: "サイズ",
                    byte: 40
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            nm_zaishitsu: {
                rules: {
                    maxlength: 80
                },
                options: {
                    name: "材質"
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            }
        };
        // SKS - Modified 2022 - ED

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

        };

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
        page.header.search = function (isLoading) {

            var deferred = $.Deferred(),
                query;

            page.header.validator.validate().done(function () {

                page.options.skip = 0;

                var criteria = page.header.element.form().data();
                page.options.filter = {
                    cd_yoki_hoso_shizai: null,
                    nm_yoki_hoso_shizai: criteria.nm_yoki_hoso_shizai,
                    nm_maker: criteria.nm_maker,
                    nm_recycle_hyoji: criteria.nm_recycle_hyoji,
                    no_shizai_kikakusho: criteria.no_shizai_kikakusho,
                    nm_size: criteria.nm_size,
                    nm_zaishitsu: criteria.nm_zaishitsu,
                    top: page.options.top,       // TODO:取得するデータ数を指定します。
                    skip: page.options.skip     // TODO:先頭からスキップするデータ数を指定します。
                };

                if (isLoading) {
                    App.ui.loading.show();
                    App.ui.page.notifyAlert.clear();
                }

                return $.ajax(App.ajax.webapi.post(page.header.urls.search, page.options.filter))
                .done(function (result) {
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

            return deferred.promise();
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

            var criteria = page.header.element.form().data();
            page.options.filter = {
                cd_yoki_hoso_shizai: null,
                nm_yoki_hoso_shizai: criteria.nm_yoki_hoso_shizai,
                nm_maker: criteria.nm_maker,
                nm_recycle_hyoji: criteria.nm_recycle_hyoji,
                no_shizai_kikakusho: criteria.no_shizai_kikakusho,
                nm_size: criteria.nm_size,
                nm_zaishitsu: criteria.nm_zaishitsu,
                skip: page.options.skip,     // TODO:先頭からスキップするデータ数を指定します。
                top: page.options.top       // TODO:取得するデータ数を指定します。
            };

            App.ui.loading.show();
            App.ui.page.notifyAlert.clear();

            return $.ajax(App.ajax.webapi.post(page.header.urls.search, page.options.filter))
            .done(function (result) {
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
            }
        };

        /**
         * 画面明細へのデータバインド処理を行います。
         */
        page.detail.bind = function (data, isNewData) {
            var i, l, item, dataSet, dataCount, offsetHeight;

            dataCount = data.Count ? data.Items[0].COUNT_ALL : 0;
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
        };

        /**
         * 画面明細の一覧に新規データを追加します。
         */
        page.detail.addNewItem = function () {
            page.detail.dataTable.dataTable("getFirstViewRow", function (row) {
                page.detail.firstViewRow = row;
            });

            //TODO:新規データおよび初期値を設定する処理を記述します。
            var newData = {
                //no_seq: page.values.no_seq
            };
            page.detailInput.options.changeCheck = false;
            page.detailInput.options.mode = page.detailInput.mode.input;
            page.detailInput.show(newData);
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
            page.detailInput.show(entity);
        };

        /**
         * 編集画面で更新された画面明細の行を最新化します。
         */
        page.detail.updateData = function (result) {

            //TODO: 更新されたデータのキーを取得します。
            var updatedKey = result.updated[0].cd_yoki_hoso_shizai;

            page.options.filter = {
                cd_yoki_hoso_shizai: updatedKey,
                nm_yoki_hoso_shizai: null,
                nm_maker: null,
                nm_recycle_hyoji: null,
                no_shizai_kikakusho: null,
                nm_size: null,
                nm_zaishitsu: null,
                top: page.options.top,       // TODO:取得するデータ数を指定します。
                skip: 0    // TODO:先頭からスキップするデータ数を指定します。
            };

            // キーをもとに最新のデータを取得します。(検索結果取得サービスのurl, 更新行を取得する条件式)
            return $.ajax(App.ajax.webapi.post(page.header.urls.search, page.options.filter))
                .then(function (result) {
                    var newData = result.Items[0];
                    if (!newData) {
                        return;
                    }

                    page.detail.dataTable.dataTable("each", function (row, index) {
                        var entity = page.detail.data.entry(row.element.attr("data-key"));
                        if (entity.cd_yoki_hoso_shizai == updatedKey) {
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
            var removedKey = result.deleted[0].cd_yoki_hoso_shizai;

            // キーをもとに削除行を特定します。
            var deletedRow;
            page.detail.dataTable.dataTable("each", function (row, index) {
                var entity = page.detail.data.entry(row.element.attr("data-key"));
                if (entity.cd_yoki_hoso_shizai == removedKey) {
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
            nm_yoki_hoso_shizai: {
                // SKS Modifed 2022/06/09 - ST
                rules: {
                    required: true,
                    maxlength: 100
                },
                options: {
                    name: "容器包装資材名",
                    //byte: 30
                },
                messages: {
                    required: App.messages.base.required,
                    maxlength: App.messages.base.maxlength
                }
                // SKS Modifed 2022/06/09 - ED
            },
            cd_maker01: {
                rules: {
                    required: true
                },
                options: {
                    name: "メーカー1",
                    byte: 30
                },
                messages: {
                    required: App.messages.base.required
                }
            },
            no_shizai_kikakusho01: {
                rules: {
                    number: true
                },
                options: {
                    name: "資材規格書番号1",
                },
                messages: {
                    number: App.messages.base.number
                }
            },
            no_shizai_kikakusho02: {
                rules: {
                    number: true
                },
                options: {
                    name: "資材規格書番号2",
                },
                messages: {
                    number: App.messages.base.number
                }
            },
            no_shizai_kikakusho03: {
                rules: {
                    number: true
                },
                options: {
                    name: "資材規格書番号3",
                },
                messages: {
                    number: App.messages.base.number
                }
            },
            no_shizai_kikakusho04: {
                rules: {
                    number: true
                },
                options: {
                    name: "資材規格書番号4",
                },
                messages: {
                    number: App.messages.base.number
                }
            },
            no_shizai_kikakusho05: {
                rules: {
                    number: true
                },
                options: {
                    name: "資材規格書番号5",
                },
                messages: {
                    number: App.messages.base.number
                }
            },
            nm_size01: {
                rules: {
                    maxbytelength: 80
                },
                options: {
                    name: "サイズ1",
                    byte: 40
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            nm_size02: {
                rules: {
                    maxbytelength: 80
                },
                options: {
                    name: "サイズ2",
                    byte: 40
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            nm_size03: {
                rules: {
                    maxbytelength: 80
                },
                options: {
                    name: "サイズ3",
                    byte: 40
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            nm_size04: {
                rules: {
                    maxbytelength: 80
                },
                options: {
                    name: "サイズ4",
                    byte: 40
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            nm_size05: {
                rules: {
                    maxbytelength: 80
                },
                options: {
                    name: "サイズ5",
                    byte: 40
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            }
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
         * 編集画面のクリア処理を行います。
         */
        page.detailInput.clear = function () {
            var closeMessage = {},
                previousPage = function () {
                    page.detailInput.options.changeCheck = true;
                    page.detailInput.element.find(".clear").val("").text("").change();
                    page.detailInput.element.find(".control-label,.control").removeClass("control-required-label control-required");

                    //画面内のドロップダウンの初期化
                    page.loadMasterData().then(function (result) {
                        App.ui.page.notifyAlert.clear();
                    })
                };

            closeMessage.text = App.messages.app.AP0055;

            if (closeMessage.text) {
                page.dialogs.confirmDialog.confirm(closeMessage)
                .then(function () {
                    previousPage();
                });
            } else {
                previousPage();
            };
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
                $("#clear").show();
                element.find(".control-label,.control").removeClass("control-required-label control-required");
                //TODO:キー項目が画面入力の場合、新規モード時は修正可とします。
                //element.findP("no_seq").prop("readonly", false).prop("tabindex", false);
                //element.validation().validate({
                //    state: {
                //        suppressMessage: true
                //    }
                //});
            } else {
                $("#addItem").hide();
                $("#editItem").show();
                $("#deleteItem").show();
                $("#clear").show();
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
                    $("#clear").hide();

                    App.ui.page.notifyAlert.clear();
                };

            if (page.detailInput.options.changeCheck) {
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

            App.ui.page.notifyAlert.clear();
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

            entity[property] = data[property];
            page.detailInput.data.update(entity);
            if ($("#editItem").is(":visible:disabled")) {
                $("#editItem").prop("disabled", false);
            }
            page.detailInput.options.changeCheck = true;
            delete page.values.isChangeRunning[property];

            //page.detailInput.validator.validate({
            //    targets: target
            //}).then(function () {
            //    entity[property] = data[property];
            //    page.detailInput.data.update(entity);
            //    if ($("#editItem").is(":visible:disabled")) {
            //        $("#editItem").prop("disabled", false);
            //    }
            //}).always(function () {
            //    page.detailInput.options.changeCheck = true;
            //    delete page.values.isChangeRunning[property];
            //});
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

    <div class="content-wrap">
        <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
        <div class="header">
            <div title="検索条件" class="part">
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>容器包装資材名</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="text" data-prop="nm_yoki_hoso_shizai" maxlength="100" />
                    </div>
                    <div class="control col-xs-8"></div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>メーカー</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="text" data-prop="nm_maker" maxlength="80" />
                    </div>
                    <div class="control-label col-xs-1">
                        <label>リサイクル表示</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="text" data-prop="nm_recycle_hyoji" maxlength="80" />
                    </div>
                    <div class="control col-xs-4"></div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>資材規格書番号</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="tel" data-prop="no_shizai_kikakusho" maxlength="15" />
                    </div>
                    <div class="control-label col-xs-1">
                        <label>サイズ</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="text" data-prop="nm_size" maxlength="80" />
                    </div>
                    <div class="control col-xs-4"></div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>材質</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="text" data-prop="nm_zaishitsu" maxlength="80" />
                    </div>
                    <div class="control col-xs-8"></div>
                </div>
                <!--
                <div class="row">
                    <div class="control-label col-xs-2">
                        <label>品名</label>
                    </div>
                    <div class="control col-xs-10">
                        <input type="text" class="ime-active" data-prop="nm_hinmei" />
                    </div>
                </div>
                -->
                <div class="header-command">
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
                    <div class="control col-xs-3" style="width: 100px">
                        <label>容器包装資材</label>
                    </div>
                    <button type="button" class="btn btn-default btn-xs" id="add-item">新規</button>
                </div>
                <span class="data-count">
                    <span data-prop="data_count"></span>
                    <span>/</span>
                    <span data-prop="data_count_total"></span>
                </span>
            </div>
            <table class="datatable">
                <!--TODO: 明細一覧のヘッダーを定義するHTMLをここに記述します。-->
                <thead>
                    <tr>
                        <!--TODO: 単一行を作成する場合は、下記を利用します。-->
                        <th style="width: 10px;"></th>
                        <th style="width: 50px">詳細</th>
                        <th style="width: 45px">コード</th>
                        <th style="width: 260px">容器包装資材名</th>
                        <th style="width: 260px">メーカー</th>
                        <th style="width: 100px">資材規格書番号</th>
                        <th style="width: 100px">材質</th>
                        <th style="width: 100px">リサイクル表示</th>
                        <th style="width: 150px">サイズ</th>
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
                            <a href="#" class="edit-select btn btn-info btn-xs">詳細</a>
                        </td>
                        <td>
                            <span data-prop="cd_yoki_hoso_shizai_key"></span>
                        </td>
                        <td style="word-wrap:break-word;">
                            <span data-prop="nm_yoki_hoso_shizai"></span>
                        </td>
                        <td style="word-wrap:break-word;">
                            <span data-prop="nm_maker"></span>
                        </td>
                        <td>
                            <span data-prop="no_shizai_kikakusho01"></span>
                        </td>
                        <%--Support #28789 TOsVN toan.nt 2022/08/25 ST--%>
                        <%--<td>--%>
                        <td style="word-wrap:break-word;">
                        <%--Support #28789 TOsVN toan.nt 2022/08/25 ED--%>
                            <span data-prop="nm_zaishitsu"></span>
                        </td>
                        <td style="word-wrap:break-word;">
                            <span data-prop="nm_recycle_hyoji"></span>
                        </td>
                        <td style="word-wrap:break-word;">
                            <span data-prop="nm_size01"></span>
                        </td>
                        <%-- 詳細画面推移時に使用予定（仮） --%>
                        <td hidden>
                            <span data-prop="cd_yoki_hoso_shizai" hidden></span>
                            <span data-prop="cd_maker01" hidden></span>
                            <span data-prop="cd_maker02" hidden></span>
                            <span data-prop="cd_maker03" hidden></span>
                            <span data-prop="cd_maker04" hidden></span>
                            <span data-prop="cd_maker05" hidden></span>
                            <span data-prop="no_shizai_kikakusho02" hidden></span>
                            <span data-prop="no_shizai_kikakusho03" hidden></span>
                            <span data-prop="no_shizai_kikakusho04" hidden></span>
                            <span data-prop="no_shizai_kikakusho05" hidden></span>
                            <span data-prop="cd_zaishitsu01" hidden></span>
                            <span data-prop="cd_zaishitsu02" hidden></span>
                            <span data-prop="cd_zaishitsu03" hidden></span>
                            <span data-prop="cd_zaishitsu04" hidden></span>
                            <span data-prop="cd_zaishitsu05" hidden></span>
                            <span data-prop="cd_recycle_hyoji01" hidden></span>
                            <span data-prop="cd_recycle_hyoji02" hidden></span>
                            <span data-prop="cd_recycle_hyoji03" hidden></span>
                            <span data-prop="cd_recycle_hyoji04" hidden></span>
                            <span data-prop="cd_recycle_hyoji05" hidden></span>
                            <span data-prop="nm_size02" hidden></span>
                            <span data-prop="nm_size03" hidden></span>
                            <span data-prop="nm_size04" hidden></span>
                            <span data-prop="nm_size05" hidden></span>
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

            <%--<div title="TODO: 入力項目部のタイトルを設定します。" class="part">
                <div class="row">
                </div>
            </div>--%>
            <div class="row">
                <div class="control-label col-xs-1">
                    <label>容器包装資材コード</label>
                </div>
                <div class="control col-xs-3">
                    <input type="text" data-prop="cd_yoki_hoso_shizai_key" disabled />
                </div>
                <div class="control col-xs-8"></div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>容器包装資材名<i class="caution">*</i></label>
                    </div>
                    <div class="control col-xs-11">
                        <input type="text" data-prop="nm_yoki_hoso_shizai" class="clear" maxlength="100" />
                    </div>
                </div>
                <div class="row">
                    <div class="control col-xs-12"></div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>メーカー1<i class="caution">*</i></label>
                    </div>
                    <div class="control col-xs-4">
                        <select data-prop="cd_maker01" class="clear"></select>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>リサイクル表示1</label>
                    </div>
                    <div class="control col-xs-6">
                        <select data-prop="cd_recycle_hyoji01" class="clear"></select>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>メーカー2</label>
                    </div>
                    <div class="control col-xs-4">
                        <select data-prop="cd_maker02" class="clear"></select>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>リサイクル表示2</label>
                    </div>
                    <div class="control col-xs-6">
                        <select data-prop="cd_recycle_hyoji02" class="clear"></select>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>メーカー3</label>
                    </div>
                    <div class="control col-xs-4">
                        <select data-prop="cd_maker03" class="clear"></select>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>リサイクル表示3</label>
                    </div>
                    <div class="control col-xs-6">
                        <select data-prop="cd_recycle_hyoji03" class="clear"></select>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>メーカー4</label>
                    </div>
                    <div class="control col-xs-4">
                        <select data-prop="cd_maker04" class="clear"></select>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>リサイクル表示4</label>
                    </div>
                    <div class="control col-xs-6">
                        <select data-prop="cd_recycle_hyoji04" class="clear"></select>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>メーカー5</label>
                    </div>
                    <div class="control col-xs-4">
                        <select data-prop="cd_maker05" class="clear"></select>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>リサイクル表示5</label>
                    </div>
                    <div class="control col-xs-6">
                        <select data-prop="cd_recycle_hyoji05" class="clear"></select>
                    </div>
                </div>
                <div class="row">
                    <div class="control col-xs-12"></div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>資材規格書番号1</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="tel" data-prop="no_shizai_kikakusho01" maxlength="15" class="clear" />
                    </div>
                    <div class="control col-xs-1"></div>
                    <div class="control-label col-xs-1">
                        <label>サイズ1</label>
                    </div>
                    <div class="control col-xs-4">
                        <input type="text" data-prop="nm_size01" class="clear" maxlength="80" />
                    </div>
                    <div class="control col-xs-2"></div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>資材規格書番号2</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="tel" data-prop="no_shizai_kikakusho02" maxlength="15" class="clear" />
                    </div>
                    <div class="control col-xs-1"></div>
                    <div class="control-label col-xs-1">
                        <label>サイズ2</label>
                    </div>
                    <div class="control col-xs-4">
                        <input type="text" data-prop="nm_size02" class="clear" maxlength="80" />
                    </div>
                    <div class="control col-xs-2"></div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>資材規格書番号3</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="tel" data-prop="no_shizai_kikakusho03" maxlength="15" class="clear" />
                    </div>
                    <div class="control col-xs-1"></div>
                    <div class="control-label col-xs-1">
                        <label>サイズ3</label>
                    </div>
                    <div class="control col-xs-4">
                        <input type="text" data-prop="nm_size03" class="clear" maxlength="80" />
                    </div>
                    <div class="control col-xs-2"></div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>資材規格書番号4</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="tel" data-prop="no_shizai_kikakusho04" maxlength="15" class="clear" />
                    </div>
                    <div class="control col-xs-1"></div>
                    <div class="control-label col-xs-1">
                        <label>サイズ4</label>
                    </div>
                    <div class="control col-xs-4">
                        <input type="text" data-prop="nm_size04" class="clear" maxlength="80" />
                    </div>
                    <div class="control col-xs-2"></div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>資材規格書番号5</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="tel" data-prop="no_shizai_kikakusho05" maxlength="15" class="clear" />
                    </div>
                    <div class="control col-xs-1"></div>
                    <div class="control-label col-xs-1">
                        <label>サイズ5</label>
                    </div>
                    <div class="control col-xs-4">
                        <input type="text" data-prop="nm_size05" class="clear" maxlength="80" />
                    </div>
                    <div class="control col-xs-2"></div>
                </div>
                <div class="row">
                    <div class="control col-xs-12"></div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>材質01</label>
                    </div>
                    <div class="control col-xs-11">
                        <select data-prop="cd_zaishitsu01" class="clear"></select>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>材質02</label>
                    </div>
                    <div class="control col-xs-11">
                        <select data-prop="cd_zaishitsu02" class="clear"></select>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>材質03</label>
                    </div>
                    <div class="control col-xs-11">
                        <select data-prop="cd_zaishitsu03" class="clear"></select>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>材質04</label>
                    </div>
                    <div class="control col-xs-11">
                        <select data-prop="cd_zaishitsu04" class="clear"></select>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>材質05</label>
                    </div>
                    <div class="control col-xs-11">
                        <select data-prop="cd_zaishitsu05" class="clear"></select>
                    </div>
                </div>
            </div>
        </div>
    </div>

</asp:Content>

<asp:Content ID="FooterContent" ContentPlaceHolderID="FooterContent" runat="server">
    <div class="command-left">
        <!--TODO: コマンドボタン（左寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" id="clear" class="btn btn-sm btn-primary" style="display: none;">クリア</button>
    </div>
    <div class="command">
        <!--TODO: コマンドボタン（右寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" id="addItem" class="btn btn-sm btn-primary" style="display: none;">保存</button>
        <button type="button" id="editItem" class="btn btn-sm btn-primary" disabled="disabled" style="display: none;">保存</button>
        <button type="button" id="deleteItem" class="btn btn-sm btn-default" style="display: none;">削除</button>
    </div>

</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>
