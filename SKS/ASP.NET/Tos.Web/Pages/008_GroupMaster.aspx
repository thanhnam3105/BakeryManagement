<%@ Page Title="008_グループマスタ" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="008_GroupMaster.aspx.cs" Inherits="Tos.Web.Pages._008_GroupMaster" %>

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
    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("_008_GroupMaster", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。sa
            options: {},
            values: {
                isChangeRunning: {},
                mode: 1,
                id_kino: 20,
                id_data: 9
            },
            urls: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
                ma_group: "../Services/ShisaQuickService.svc/ma_group?$orderby=flg_hyoji",
                ma_team: "../Services/ShisaQuickService.svc/ma_team?$filter=cd_team eq {0} and cd_group eq {1}",
                //busho_kaisha: "../Services/ShisaQuickService.svc/vw_shohinkaihatsu_008_busho_kaisha?$orderby=cd_kaisha",
                busho_kaisha: "../Services/ShisaQuickService.svc/ma_kaisha?$orderby=cd_kaisha",
                search: "../api/GroupMaster",
                save: "../api/GroupMaster",
                mainMenu: "MainMenu.aspx"
            },
            header: {
                options: {},
                values: {},
                urls: {
                    ma_team: "../Services/ShisaQuickService.svc/ma_team?$filter=cd_group eq {0}&$orderby=flg_hyoji"
                }
            },
            detail: {
                options: {},
                values: {}
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
                    var options = {
                        text: App.messages.app.AP0004
                    };

                    page.dialogs.confirmDialog.confirm(options)
                    .then(function () {
                        var id = page.header.element.attr("data-key"),
                            //entity = page.header.data.entry(id),
                            changeSets;

                        //page.header.data.update(entity);

                        changeSets = page.header.getDataSet();

                        //TODO: データの保存処理をここに記述します。
                        return $.ajax(App.ajax.webapi.post(page.urls.save, changeSets))
                                .then(function () {
                                    setTimeout(function () {
                                        App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                                    }, 100);
                                }).then(function (result) {
                                    page.commands.remove();
                                }).fail(function (error) {
                                    if (error.status === App.settings.base.conflictStatus) {
                                        // TODO: 同時実行エラー時の処理を行っています。
                                        // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                                        //page.loadData();
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

                                    //TODO: データの保存失敗時の処理をここに記述します。
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

        page.header.getDataSet = function () {
            var element = page.header.element,
                filter = {},
                mode = page.values.mode;

                filter = {
                    mode: mode,
                    cd_group: element.findP("group").val(),
                    cd_kaisha: element.findP("kaisha").val(),
                    nm_group: element.findP("nm_group").val(),
                    cd_team: element.findP("team").val(),
                    nm_team: element.findP("nm_team").val(),
                    ts_group: element.findP("ts_group").val(),
                    ts_team: element.findP("ts_team").val()
                }

            return filter;
        };


        /**
         * データの削除処理を実行します。
         */
        page.commands.remove = function () {
            var options = {
                //text: App.messages.base.MS0003
            };

            //page.dialogs.confirmDialog.confirm(options)
            //.then(function () {
            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();
            App.ui.loading.show();
            page.setColValidStyle(page.header.element.find("[data-prop='nm_group'], [data-prop='kaisha'], [data-prop='group'], [data-prop='team'], [data-prop='nm_team']"));
            page.detail.isChanged = false;
            var sleep = 0;
            var condition = "Object.keys(page.values.isChangeRunning).length == 0";
            App.ui.wait(sleep, condition, 100).then(function () {

                var id = page.header.element.attr("data-key"),
                    //entity = page.header.data.entry(id),
                    element = page.header.element,
                    changeSets;

                //page.header.data.remove(entity);

                page.values.mode = 1;
                element.findP("group").val("");
                element.findP("nm_group").val("");
                element.findP("kaisha").val("");
                element.findP("team").val("");
                element.findP("nm_team").val("");

                $("#check_group").prop("checked", true);
                element.findP("nm_group").prop("disabled", false);
                element.findP("kaisha").prop("disabled", false);
                element.findP("team").prop("disabled", true);
                element.findP("nm_team").prop("disabled", true);
                element.find("#nm_group").show();
                element.find("#kaisha").show();
                element.find("#nm_team").hide();

                $("#save").prop("disabled", true);

                page.loadMasterData();

                element.findP("team").children().remove();
                element.findP("nm_team").children().remove();

            }).always(function () {
                setTimeout(function () {
                    page.header.element.find(":input:first").focus();
                }, 100);
                App.ui.loading.close();
            });
        };

        /**
         * すべてのバリデーションを実行します。
         */
        page.validateAll = function () {

            var validations = [],
                element = page.header.element;
            if (element.find('input[name=seq_check][value=1]').is(":checked")) {
                validations.push(page.header.group_validator.validate());
            }
            else
                validations.push(page.header.team_validator.validate());
            //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。

            return App.async.all(validations);
        };

        /**
         * Windows閉じる際のイベントを定義します。
         * @return 文字列を返却した場合に確認メッセージが表示されます。
         */
        App.ui.page.onclose = function () {
            return page.detail.isChanged;
            //var header,
            //    closeMessage = App.messages.base.exit;

            //if (page.header.data) {
            //    header = page.header.data.getChangeSet();
            //    if (header.created.length || header.updated.length || header.deleted.length) {
            //        return closeMessage;
            //    }
            //}
        };

        /**
        * 画面の初期化処理を行います。
        */
        page.initialize = function () {

            App.ui.loading.show();

            page.initializeControl();
            page.initializeControlEvent();

            page.header.initialize();

            //TODO: ヘッダー/明細以外の初期化の処理を記述します。

            //Set page is new create
            page.detail.isChanged = false;

            page.loadMasterData().then(function (result) {

                return page.loadDialogs();
            }).then(function (result) {
                //TODO: 画面の初期化処理成功時の処理を記述します。
                getKengenGamen(App.settings.app.id_gamen.group_master).then(function (results) {
                    page.kengenGamen = results;
                    var isRoleScreen = false;
                    $.each(results, function (index, item) {
                        if (item.id_kino == page.values.id_kino && item.id_data == page.values.id_data) {
                            isRoleScreen = true;
                        }
                    });

                    if (!isRoleScreen) {
                        //var options = {
                        //    text: App.messages.app.AP0089,
                        //    hideCancel: true
                        //};

                        //page.dialogs.confirmDialog.confirm(options)
                        //.always(function () {
                            window.location.href = page.urls.mainMenu;
                        //});
                    }
                });
            }).fail(function (error) {
                if (error.status === 404) {
                    App.ui.page.notifyWarn.message(App.ajax.handleError(error).message).show();
                }
                else {
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                }
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
            //TODO: タブ間の通信を初期化する処理を記述します。※親画面と通信可能となるように設定します。
            //page.options.tabComm = App.ui.tabComm.getOrCreate({
            //    connectToOpener: true
            //});
        };

        /**
         * コントロールへのイベントの紐づけを行います。
         */
        page.initializeControlEvent = function () {

            //TODO: 画面全体で利用するコントロールのイベントの紐づけ処理をここに記述します。
            $("#save").on("click", page.commands.save);
            $("#remove").on("click", page.commands.remove);
        };

        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function () {

            //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。

            return $.ajax(App.ajax.odata.get(page.urls.ma_group)).then(function (result) {
                var group = page.header.element.findP("group");
                group.children().remove();
                App.common.appendOptions(
                    group,
                    "cd_group",
                    "nm_group",
                    result.value,
                    true,
                    {},
                    true,
                    "000"
                );

                return $.ajax(App.ajax.odata.get(page.urls.busho_kaisha)).then(function (result2) {
                    var kaisha = page.header.element.findP("kaisha");
                    //for (var i = 0; i < result2.value.length; i++)
                    //{
                    //    result2.value[i].nm_kaisha = (result2.value[i].nm_kaisha == null) ? "" : result2.value[i].nm_kaisha;
                    //}
                    kaisha.children().remove();
                    App.common.appendOptions(
                        kaisha,
                        "cd_kaisha",
                        "nm_kaisha",
                        result2.value,
                        true,
                        {},
                        true,
                        "000"
                    );
                });
            });
            //TODO: マスタデータのロード処理を実装後、下の1行を削除してください。
            return App.async.success();
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
        page.header.options.group_validations = {
            //TODO: 画面ヘッダーのバリデーションの定義を記述します
            nm_group: {
                rules: {
                    maxlength: 100,
                    required: true
                },
                options: {
                    name: "グループ名"
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                    required: App.messages.base.required,
                }
            },
            kaisha: {
                rules: {
                    required: true
                },
                options: {
                    name: "会社",

                },
                messages: {
                    required: App.messages.base.required,
                }
            }
        };

        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.team_validations = {
            //TODO: 画面ヘッダーのバリデーションの定義を記述します。
            group: {
                rules: {
                    required: true
                },
                options: {
                    name: "グループ",
                },
                messages: {
                    required: App.messages.base.required
                }
            },
            nm_team: {
                rules: {
                    maxlength: 100,
                    required: true
                },
                options: {
                    name: "チーム名",

                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                    required: App.messages.base.required,
                }
            },
        };

        /**
         * 画面ヘッダーの初期化処理を行います。
         */
        page.header.initialize = function () {

            var element = $(".header");
            page.header.group_validator = element.validation(page.createValidator(page.header.options.group_validations));
            page.header.team_validator = element.validation(page.createValidator(page.header.options.team_validations));
            page.header.element = element;

            //TODO: 画面ヘッダーの初期化処理をここに記述します。
            //TODO: 画面ヘッダーで利用するコントロールのイベントの紐づけ処理をここに記述します。
            element.on("change", ":input", page.header.change);

            page.values.mode = 1;
            $("#check_group").prop("checked", true);
            element.findP("nm_group").prop("disabled", false);
            element.findP("kaisha").prop("disabled", false);
            element.findP("team").prop("disabled", true);
            element.findP("nm_team").prop("disabled", true);

            element.find("#nm_group").show();
            element.find("#kaisha").show();
            element.find("#nm_team").hide();

            //element.on("change", ".search", page.header.search);
            //page.header.bind([], true);
        };

        /**
         * ダイアログの検索処理を実行します。
         */
        //page.header.search = function (isLoading) {
        //    var element = page.header.element,
        //        cd_group = element.findP("group").val(),
        //        deferred = $.Deferred(),
        //        validator;

        //    App.ui.page.notifyWarn.clear();
        //    App.ui.page.notifyAlert.clear();
        //    App.ui.page.notifyInfo.clear();

        //    if (element.find('input[name=seq_check][value=1]').is(":checked")) {
        //        validator = page.header.group_validator.validate();
        //    } else {
        //        validator = page.header.team_validator.validate();
        //    }

        //    validator.done(function () {

        //        //page.options.filter = page.header.createFilter();

        //        if (isLoading) {
        //            App.ui.loading.show();
        //            App.ui.page.notifyAlert.clear();
        //        }

        //        return $.ajax(App.ajax.webapi.get(page.urls.search, { cd_group: cd_group }))
        //        .done(function (result) {
        //            //page.header.bind(result);
        //            deferred.resolve();
        //        }).fail(function (error) {
        //            page.notifyAlert.message(App.ajax.handleError(error).message).show();
        //            deferred.reject();
        //        }).always(function () {

        //            if (isLoading) {
        //                App.ui.loading.close();
        //            }
        //            if (!element.find(".save").is(":disabled")) {
        //                element.find(".save").prop("disabled", true);
        //            }
        //        });
        //    });

        //    return deferred.promise();
        //};

        /**
         * 画面ヘッダーへのデータバインド処理を行います。
         */
        page.header.bind = function (data, isNewData) {

            var element = page.header.element,
                dataSet = App.ui.page.dataSet(),
                item;

            page.header.data = dataSet;

            (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(data);
            page.header.element.form().bind(data);

            //if (!isNewData) {
            //    page.header.validator.validate();
            //}
        };

        /**
        * 画面ヘッダーにデータを設定する際のオプションを定義します。
        */
        page.header.options.bindOption = {
            appliers: {
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
                //entity = page.header.data.entry(id),
                data = element.form().data(),
                validatior;

            //page.values.isChangeRunning[property] = true;

            //entity[property] = data[property];

            if (property == "seq_check") {
                App.ui.page.notifyWarn.clear();
                App.ui.page.notifyAlert.clear();
                App.ui.page.notifyInfo.clear();

                page.setColValidStyle(element.find("[data-prop='nm_group'], [data-prop='kaisha'], [data-prop='group'], [data-prop='team'], [data-prop='nm_team']"));

                if (element.find('input[name=seq_check][value=1]').is(":checked")) {
                    page.values.mode = 1;
                    element.findP("nm_group").prop("disabled", false);
                    element.findP("kaisha").prop("disabled", false);
                    element.findP("team").prop("disabled", true);
                    element.findP("nm_team").prop("disabled", true);

                    element.find("#nm_group").show();
                    element.find("#kaisha").show();
                    element.find("#nm_team").hide();

                    element.findP("nm_team").children().remove();
                    element.findP("nm_team").val("");
                }
                else {
                    page.values.mode = 2;
                    element.findP("nm_group").prop("disabled", true);
                    element.findP("kaisha").prop("disabled", true);
                    element.findP("team").prop("disabled", false);
                    element.findP("nm_team").prop("disabled", false);

                    element.find("#nm_group").hide();
                    element.find("#kaisha").hide();
                    element.find("#nm_team").show();
                }

                page.header.searchGroup();
            }

            if (property == "group") {
                page.header.searchGroup();
                App.ui.page.notifyWarn.clear();
                App.ui.page.notifyAlert.clear();
                App.ui.page.notifyInfo.clear();
                page.setColValidStyle(element.find("[data-prop='nm_group'], [data-prop='kaisha'], [data-prop='group'], [data-prop='team'], [data-prop='nm_team']"));
            }

            if (property == "team") {
                var cd_team = element.findP("team").val(),
                    cd_group = element.findP("group").val();

                App.ui.page.notifyAlert.clear();
                page.setColValidStyle(element.find("[data-prop='team'], [data-prop='nm_team']"));

                $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_team, cd_team, cd_group))).then(function (result) {
                    element.findP("ts_team").val(result.value[0].ts);
                    element.findP("nm_team").children().remove();
                    element.findP("nm_team").val(result.value[0].nm_team);
                    element.findP("nm_team").text(result.value[0].nm_team);
                });

                if (element.findP("team").val() == null || element.findP("team").val() == "") {
                    page.setColValidStyle(element.find("[data-prop='nm_team']"));
                    element.findP("nm_team").children().remove();
                    element.findP("nm_team").val("");
                }
            }

            if (property == "nm_group" || property == "kaisha" || property == "nm_team")
            {
                //Set page is change
                page.detail.isChanged = true;
            }

            if (element.findP("group").val() == ""
                && element.findP("nm_group").val() == ""
                && (element.findP("kaisha").val() == "" || element.findP("kaisha").val() == null)
                && (element.findP("team").val() == null || element.findP("team").val() == "")
                && element.findP("nm_team").val() == "") {
                page.setColValidStyle(element.find("[data-prop='nm_group'], [data-prop='kaisha'], [data-prop='team'], [data-prop='nm_team']"));
                $("#save").prop("disabled", true);
            }
            else {
                if (property != "seq_check") {
                    var sleep = 0;
                    var condition = "Object.keys(page.values.isChangeRunning).length == 0";
                    App.ui.wait(sleep, condition, 100)
                    .then(function () {

                        if (element.find('input[name=seq_check][value=1]').is(":checked")) {
                            validatior = page.header.group_validator.validate({
                                targets: target
                            });
                        } else {
                            if (element.findP("group").val() != "") {
                                validatior = page.header.team_validator.validate({
                                    targets: target
                                });
                            }
                            else
                                validatior = page.header.team_validator.validate();
                        }

                        validatior.then(function () {
                            if ($("#save").is(":disabled")) {
                                $("#save").prop("disabled", false);
                            }
                        }).always(function () {
                            delete page.values.isChangeRunning[property];
                        });
                    });
                }
            }

            //page.header.data.update(entity);
        };


        /**
        *ダイアログの検索処理を実行します。
        */
        page.header.searchGroup = function () {
            var element = page.header.element,
                cd_group = element.findP("group").val(),
                mode = page.values.mode;

            element.findP("nm_team").children().remove();
            element.findP("nm_team").val("");

            //When comboBox "グループ" change value
            if (cd_group !== "") {
                
                $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.ma_team,cd_group))).then(function (result) {
                    var team = $.findP("team");
                    team.children().remove();
                    App.common.appendOptions(
                        team,
                        "cd_team",
                        "nm_team",
                       result.value,
                        true,
                        {},
                        true,
                        "000"
                    );
                });

                $.ajax(App.ajax.webapi.get(page.urls.search, { cd_group: cd_group, mode: mode })).then(function (result) {
                    element.findP("kaisha").val(result[0].cd_kaisha);

                    if (page.values.mode == 1) {
                        element.findP("nm_group").val(result[0].nm_group);
                        element.findP("nm_group").text(result[0].nm_group);
                    }

                    if (page.values.mode == 2) {
                        element.findP("nm_group").children().remove();
                        element.findP("nm_group").val("");
                    }

                    element.findP("ts_group").val(result[0].ts_group);
                });
            }

            //When comboBox "グループ" equal blank
            else {
                element.findP("team").children().remove();
                element.findP("kaisha").children().remove();

                $.ajax(App.ajax.odata.get(page.urls.busho_kaisha)).then(function (result) {
                    var kaisha = page.header.element.findP("kaisha");
                    for (var i = 0; i < result.value.length; i++) {
                        result.value[i].nm_kaisha = (result.value[i].nm_kaisha == null) ? "" : result.value[i].nm_kaisha;
                    }
                    kaisha.children().remove();
                    App.common.appendOptions(
                        kaisha,
                        "cd_kaisha",
                        "nm_kaisha",
                        result.value,
                        true,
                        {},
                        true,
                        "000"
                    );
                });

                element.findP("nm_team").children().remove();
                element.findP("nm_team").val("");

                element.findP("nm_group").children().remove();
                element.findP("nm_group").val("");
            }
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
        //TODO-END: 以下の page.header の各宣言は画面仕様ごとにことなるため、不要の場合は削除してください。


        //TODO-END: 以下の page.detail の各宣言は画面仕様ごとにことなるため、不要の場合は削除してください。

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
        <!--TODO: ヘッダーを定義するHTMLをここに記述します。-->
        <div class="header">
            <%--<div title="グループマスタ" class="part">--%>
            <div class="row">
                <div class="control col-xs-12"></div>
            </div>

            <div class="row">
                <div class="control col-xs-1">
                    <label for="check_group" style="min-width: 70px">
                        グループ
                    </label>
                    <input id="check_group" name="seq_check" data-prop="seq_check" type="radio" value="1" style="vertical-align: sub;" />
                </div>
                <div class="control col-xs-1">
                    <label for="check_team" style="min-width: 55px">
                        チーム
                    </label>
                    <input id="check_team" name="seq_check" data-prop="seq_check" type="radio" value="2" style="vertical-align: sub;"/>
                </div>
                <div class="control col-xs-10"></div>
            </div>

            <div class="row">
                <div class="control-label col-xs-1">
                    <label>
                        グループ
                            <span style="color: red">*</span>
                    </label>
                </div>
                <div class="control col-xs-3">
                    <select class="search" data-prop="group"></select>
                </div>
                <div class="control col-xs-8"></div>
            </div>

            <div class="row">
                <div class="control-label col-xs-1">
                    <label>
                        グループ名
                            <span id="nm_group" style="color: red">*</span>
                    </label>
                </div>
                <div class="control col-xs-3">
                    <input type="text" data-prop="nm_group" />
                </div>
                <div class="control col-xs-8"></div>
            </div>

            <div class="row">
                <div class="control-label col-xs-1">
                    <label>
                        会社
                            <span id="kaisha" style="color: red">*</span>
                    </label>
                </div>
                <div class="control col-xs-3">
                    <select data-prop="kaisha"></select>
                </div>
                <div class="control col-xs-8"></div>
            </div>

            <div class="row">
                <div class="control-label col-xs-1">
                    <label>チーム</label>
                </div>
                <div class="control col-xs-3">
                    <select data-prop="team"></select>
                </div>
                <div class="control col-xs-8"></div>
            </div>

            <div class="row">
                <div class="control-label col-xs-1">
                    <label>
                        チーム名
                            <span id="nm_team" style="color: red">*</span>
                    </label>
                </div>
                <div class="control col-xs-3">
                    <input type="text" data-prop="nm_team" />
                </div>
                <div class="control col-xs-8">
                    <span data-prop="ts_group" hidden></span>
                    <span data-prop="ts_team" hidden></span>
                </div>
                <%--</div>--%>
            </div>
        </div>
    </div>
</asp:Content>

<asp:Content ID="FooterContent" ContentPlaceHolderID="FooterContent" runat="server">
    <div class="command-left">
        <!--TODO: コマンドボタン（左寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" id="remove" class="btn btn-sm btn-primary">クリア</button>
    </div>
    <div class="command">
        <!--TODO: コマンドボタン（右寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" id="save" disabled="disabled" class="btn btn-sm btn-primary">保存</button>
    </div>
</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>
