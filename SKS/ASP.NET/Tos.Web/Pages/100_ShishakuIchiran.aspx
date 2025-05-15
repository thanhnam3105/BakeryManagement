<%@ Page Title="100_試作データ一覧" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="100_ShishakuIchiran.aspx.cs" Inherits="Tos.Web.Pages.ShishakuIchiran" %>

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

        div.row label.check-group {
            padding-right: 20px;
        }

            div.row label.check-group input {
                margin-left: 3px;
            }
    </style>

    <script type="text/javascript">
        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("ShishakuIchiran", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.dataTakeCount,   // TODO:取得するデータ数を指定します。
                filter: ""
            },
            values: {
                isChangeRunning: {},
                //id_kino: null,
                id_data: null,
                isShowEditWindow: {},
                isShowSekkeiHinshutsuGeto: {},
                transferPages: []
            },
            urls: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
                copyMotoSentakuDialog: "Dialogs/405_CopyMotoSentakuDialog.aspx",
                mainMenu: "MainMenu.aspx",
                Post_haita: "../api/ShishakuData/Post_haita",
                checkhaita400: "../api/SekkeiHinshutsuGeto/checkHaita"
            },
            header: {
                options: {},
                values: {
                    cd_kaisha: ""
                },
                urls: {
                    group: "../Services/ShisaQuickService.svc/ma_group?$orderby=flg_hyoji",
                    kaisha: "../Services/ShisaQuickService.svc/ma_group?$filter=cd_group eq {0}",
                    genre: "../Services/ShisaQuickService.svc/ma_literal?$filter=cd_category eq 'K_jyanru'&$orderby=no_sort",
                    team: "../Services/ShisaQuickService.svc/ma_team?$filter=cd_group eq {0} &$orderby=flg_hyoji",
                    tanto: "../Services/ShisaQuickService.svc/ma_user_togo?$filter=cd_group eq {0} and cd_kaisha eq {1} and cd_team eq {2} &$orderby=id_user",
                    user: "../Services/ShisaQuickService.svc/ma_literal?$filter=cd_category eq 'K_yuza'&$orderby=no_sort",
                    ikatsu: "../Services/ShisaQuickService.svc/ma_literal?$filter=cd_category eq 'K_ikatuhyouzi'&$orderby=no_sort",
                    search: "../api/Shishakuichiran",
                    gettantokaisha: "../Services/ShisaQuickService.svc/ma_tantokaisya?$filter=id_user eq {0}M",
                    getGateHeader: "../Services/ShisaQuickService.svc/tr_gate_header?$filter=cd_shain eq {0}M and nen eq {1}M and no_oi eq {2}M",
                    getUserInfo: "../api/SekkeiHinshutsuGeto/userInfo"
                }
            },
            detail: {
                options: {},
                values: {
                    paramHaita: {}
                }
            },
            detailInput: {
                options: {},
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

                    var changeSets = page.detailInput.data.getChangeSet();
                    //TODO: データの保存処理をここに記述します。
                    return $.ajax(App.ajax.webapi.post(/* TODO: データ保存サービスの URL を設定してください。, */  changeSets))
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
         * copy
         */
        page.commands.copyItem = function () {
            var element = page.detail.element,
                winObj,
                selected = element.find(".datatable .select-tab.selected").closest("tbody");
            if (!selected.length) {
                return;
            }
            //window.open("101_ShishakuData.aspx?mode=2")
            //window.open("101_ShishakuData.aspx?mode=2", "_blank");
            winObj = App.ui.transfer("101_ShishakuData.aspx?mode=2", { wins: page.values.transferPages });
            page.values.transferPages[winObj.name] = winObj.win;
        }

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
            $(".wrap, .footer").addClass("theme-blue");

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
                getKengenGamen(App.settings.app.id_gamen.shisaku_data).then(function (results) {
                    page.kengenGamen = results;
                    if (results.length < 1) {
                        window.location.href = page.urls.mainMenu;
                    }
                    $.each(results, function (index, item) {
                        if (item.id_kino == 10) {
                            page.values.id_data = item.id_data;
                            if (item.id_data != 9) {
                                var group = page.header.element.findP("group");
                                group.val(App.ui.page.user.cd_group);
                                group.prop("disabled", true);
                            }
                        }
                        else if (item.id_kino == 20 && item.id_data == 9) {
                            $("#add-item").prop("disabled", false);
                        }
                        else if (item.id_kino == 30 && item.id_data == 9) {
                            $(".edit-select").prop("disabled", false);
                            $("#sekkei_hinshitsu_gate").prop("disabled", false);
                            $("#copy_sekkei_hinshitsu_gate").prop("disabled", false);
                        }
                        else if (item.id_kino == 40 && item.id_data == 9) {
                            $("#copy").prop("disabled", false);
                        } else if (item.id_kino == 50 && item.id_data == 9) {
                            $("#reset").prop("disabled", false);
                            $("#reset400").prop("disabled", false);
                        }
                        else {
                            window.location.href = page.urls.mainMenu;
                        }
                    });
                    page.header.bind({}, true);
                });
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
            $("#clear").on("click" , page.commands.clear);
            $("#copy").on("click", page.detail.showEditWindow);
            $("#sekkei_hinshitsu_gate").on("click", page.detail.showSekkeiHinshutsuGeto);
            $("#copy_sekkei_hinshitsu_gate").on("click", page.detail.showCopyMotoSentakuDialog);
            $("#reset").on("click", function () {
                var element = page.detail.element,
                selected = element.find(".datatable .select-tab.selected").closest("tbody");
                if (!selected.length) {
                    App.ui.page.notifyInfo.remove("AP0008_reset");
                    App.ui.page.notifyInfo.message(App.messages.app.AP0008, "AP0008_reset").show();
                    return;
                }
                if (!App.isUndefOrNull(page.detail.selectedRow)) {
                    App.ui.loading.show();
                    var id = page.detail.selectedRow.element.attr("data-key"),
                        entity = page.detail.data.entry(id);
                    page.values.cd_shain = entity.no_shisaku.substring(0, 10);
                    page.values.nen = entity.no_shisaku.substring(11, 13);
                    page.values.no_oi = entity.no_shisaku.substring(14, 18);
                    page.checkHaitaKubun(false).then(function (result) {
                        App.ui.page.notifyInfo.remove("AP0188");
                        App.ui.page.notifyInfo.message(App.messages.app.AP0188,"AP0188").show();
                    }).fail(function (error) {
                        page.dialogs.confirmDialog.confirm({
                            text: App.messages.app.AP0187,
                            hideCancel: true,
                            multiModal: true,
                            backdrop: "static",
                            keyboard: false
                        });
                    }).always(function () {
                        App.ui.loading.close();
                    });
                }
            });
            //排他クリア（設計品質ｹﾞｰﾄ）
            $("#reset400").on("click", function () {
                var element = page.detail.element,
                selected = element.find(".datatable .select-tab.selected").closest("tbody");
                if (!selected.length) {
                    App.ui.page.notifyInfo.remove("AP0008_reset400");
                    App.ui.page.notifyInfo.message(App.messages.app.AP0008, "AP0008_reset400").show();
                    return;
                }
                if (!App.isUndefOrNull(page.detail.selectedRow)) {
                    App.ui.loading.show();
                    var id = page.detail.selectedRow.element.attr("data-key"),
                        entity = page.detail.data.entry(id);
                    page.values.cd_shain = entity.no_shisaku.substring(0, 10);
                    page.values.nen = entity.no_shisaku.substring(11, 13);
                    page.values.no_oi = entity.no_shisaku.substring(14, 18);
                    page.checkHaitaKubun400(false).then(function (result) {
                        App.ui.page.notifyInfo.remove("AP0192");
                        App.ui.page.notifyInfo.message(App.messages.app.AP0188, "AP0192").show();
                    }).fail(function (error) {
                        page.dialogs.confirmDialog.confirm({
                            text: App.messages.app.AP0187,
                            hideCancel: true,
                            multiModal: true,
                            backdrop: "static",
                            keyboard: false
                        });
                    }).always(function () {
                        App.ui.loading.close();
                    });
                }
            });
        };

        /**
        *　画面を開きるとき排他区分がEmployeeCDになる。
        *　画面を閉めるとき排他区分がnullになる。
        */
        page.checkHaitaKubun = function (isOpen) {
            var deferred = $.Deferred();
            var paraSearch = {
                cd_shain: page.values.cd_shain
                , nen: page.values.nen
                , no_oi: page.values.no_oi
                //, EmployeeCD: App.ui.page.user.EmployeeCD
                , isOpen: isOpen
            };

            $.ajax(App.ajax.webapi.post(page.urls.Post_haita, paraSearch)).then(function (result) {
                deferred.resolve(result);
            }).fail(function (error) {

                deferred.reject(error);
            });
            return deferred.promise();
        }

        /**
        *　画面を開きるとき排他区分がEmployeeCDになる。
        *　画面を閉めるとき排他区分がnullになる。
        */
        page.checkHaitaKubun400 = function (isOpen) {
            var deferred = $.Deferred();
            $.ajax(App.ajax.webapi.get(page.urls.checkhaita400, { cd_shain: page.values.cd_shain, nen: page.values.nen, no_oi: page.values.no_oi, EmployeeCD: null, isOpen: isOpen }))
            .then(function (result) {
                deferred.resolve(result);
            }).fail(function (error) {
                deferred.reject(error);
            });
            return deferred.promise();
        }

        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function () {

            //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
            return $.ajax(App.ajax.odata.get(page.header.urls.group)).then(function (result) {
                var group = page.header.element.findP("group");
                group.children().remove();
                App.ui.appendOptions(
                    group,
                    "cd_group",
                    "nm_group",
                    result.value,
                    true
                );
                group.val(App.ui.page.user.cd_group);
                return $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.team, App.ui.page.user.cd_group)))
            }).then(function (result) {
                var team = page.header.element.findP("team");
                team.children().remove();
                App.ui.appendOptions(
                    team,
                    "cd_team",
                    "nm_team",
                    result.value,
                    true
                );
                team.val(App.ui.page.user.cd_team);
                page.header.values.cd_kaisha = App.ui.page.user.cd_kaisha;
                return $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.tanto, App.ui.page.user.cd_group, App.ui.page.user.cd_kaisha, App.ui.page.user.cd_team)))
            }).then(function (result) {
                var tanto = page.header.element.findP("tanto");
                tanto.children().remove();
                App.ui.appendOptions(
                    tanto,
                    "id_user",
                    "nm_user",
                    result.value,
                    true
                );
                tanto.val(App.ui.page.user.EmployeeCD)
                return $.ajax(App.ajax.odata.get(page.header.urls.genre))
            }).then(function (result) {
                var genre = page.header.element.findP("genre");
                genre.children().remove();
                App.ui.appendOptions(
                    genre,
                    "cd_literal",
                    "nm_literal",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(page.header.urls.ikatsu))
            }).then(function (result) {
                var ikatsu = page.header.element.findP("ikatsu");
                ikatsu.children().remove();
                App.ui.appendOptions(
                    ikatsu,
                    "cd_literal",
                    "nm_literal",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(page.header.urls.user))
            }).then(function (result) {
                var user = page.header.element.findP("user");
                user.children().remove();
                App.ui.appendOptions(
                    user,
                    "cd_literal",
                    "nm_literal",
                    result.value,
                    true
                );
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
                copyMotoSentakuDialog: $.get(page.urls.copyMotoSentakuDialog)
            }).then(function (result) {

                $("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;

                $("#dialog-container").append(result.successes.copyMotoSentakuDialog);
                page.dialogs.copyMotoSentakuDialog = CopyMotoSentakuDialog;
                page.dialogs.copyMotoSentakuDialog.initialize();

            });
        };

        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.validations = {
            //TODO: 画面ヘッダーのバリデーションの定義を記述します。
            cd_shain_nen_no_oi: {
                rules: {
                    maxlength: 17,
                    //haneisukigo: true
                    digits_cus: function (value, opts, state, done) {
                        if (App.validation.isEmpty(value)) {
                            return done(true);
                        }
                        var length = value.split('-').length;
                        if (length > 3)
                            return done(false);
                        for (var i = 0; i < length; i++) {
                            if (!/^[0-9]*$/.test(value.split('-')[i]))
                                return done(false);
                        }
                        return done(true);
                    }

                },
                options: {
                    name: "試作No"
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                    digits_cus: App.str.format(App.messages.app.AP0144, { name: "試作No", param: "0~9、'-'の２個" })
                }
            },
            no_seiho1: {
                rules: {
                    maxlength: 16,
                    //haneisukigo: true
                    digits_cus: function (value, opts, state, done) {
                        if (App.validation.isEmpty(value)) {
                            return done(true);
                        }
                        value = value.toLocaleUpperCase();
                        var length = value.split('-').length;
                        if (length > 4)
                            return done(false);
                        for (var i = 0; i < length; i++) {
                            if (!/^[A-Z0-9]*$/.test(value.split('-')[i]))
                                return done(false);
                        }
                        return done(true);
                    }
                },
                options: {
                    name: "製法No"
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                    digits_cus: App.str.format(App.messages.app.AP0144, { name: "製法No", param: "0~9、A~Z、'-'の３個" })
                }
            },
            nm_hin: {
                rules: {
                    maxbytelength: 100
                },
                options: {
                    name: "試作名",
                    byte: 50

                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            youto: {
                rules: {
                    maxbytelength: 60
                },
                options: {
                    name: "製品の用途",
                    byte: 30

                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            tokuchogenryo: {
                rules: {
                    maxbytelength: 60
                },
                options: {
                    name: "特徴原料",
                    byte: 30
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
            page.header.element = element;

            //TODO: 画面ヘッダーの初期化処理をここに記述します。
            //TODO: 画面ヘッダーで利用するコントロールのイベントの紐づけ処理をここに記述します。
            element.on("click", "#search", page.header.search);
            
            element.on("change", ":input", page.header.change);
            //var userInfo = App.ui.page.user;
            //element.findP("cd_kaisha").text(userInfo.nm_kaisha);
            //element.findP("nm_busho").text(userInfo.nm_busho);
            //element.findP("cd_tanto").text(userInfo.nm_tantokaisha);
        };

        /**
         * 画面ヘッダーへのデータバインド処理を行います。
         */
        page.header.bind = function (data, isNewData) {

            var element = page.header.element,
                dataSet = App.ui.page.dataSet();

            page.header.data = dataSet;

            (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(data);
            page.header.element.form().bind(data);

            //バリデーションを実行します。
            page.header.validator.validate({
                state: {
                    suppressMessage: true
                }
            });
        };

        /**
        * 画面明細のバリデーションフィルターを設定します。（必須チェックを行わない）
        */
        page.header.validationFilter = function (item, method, state, options) {
            if (item == "no_seiho1") {
                if (!page.header.element.findP("check_no_seiho1").is(":checked")) {
                    return false;
                }
            }
            return true;
        };

        /**
         * 画面ヘッダーの変更時処理を定義します。
         */
        page.header.change = function (e) {
            var element = page.header.element,
                target = $(e.target),
                property = target.attr("data-prop"),
                id = element.attr("data-key"),
                entity = page.header.data.entry(id),
                data = element.form().data();
            if ($("#nextsearch").hasClass("show-search")) {
                $("#nextsearch").removeClass("show-search").hide();
                App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
            }
            else if (page.detail.searchData) {
                // 保持検索データの消去
                page.detail.searchData = undefined;
                App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
            }
            if (property == "check_no_seiho1") {
                element.findP("no_seiho1").prop("disabled", !element.findP(property).is(":checked"));
                target = element.find("[data-prop='check_no_seiho1'], [data-prop='no_seiho1']");
            }
            element.validation().validate({
                targets: target,
                filter: page.header.validationFilter
            }).then(function () {
                entity[property] = data[property];
                page.header.data.update(entity);
                if (property == "group") {
                    $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.kaisha, data[property]), {}, { async: false })).then(function (result) {
                        page.header.values.cd_kaisha = result.value[0] !== undefined ? result.value[0].cd_kaisha : "";

                    });
                    $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.team, data[property]), {}, { async: false }))
                    .then(function (result) {
                        var team = element.findP("team");
                        team.children().remove();
                        App.ui.appendOptions(
                            team,
                            "cd_team",
                            "nm_team",
                            result.value,
                            true
                        );
                        var tanto = page.header.element.findP("tanto");
                        tanto.children().remove();
                        App.ui.appendOptions(
                            tanto,
                            "id_user",
                            "nm_user",
                            [],
                            true
                        );
                    });

                }
                else if (property == "team") {
                    var tanto = element.findP("tanto");
                    tanto.children().remove();
                    if (element.findP("team").val() != "") {
                        $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.tanto, element.findP("group").val(), page.header.values.cd_kaisha, element.findP("team").val())))
                       .then(function (result) {
                           App.ui.appendOptions(
                               tanto,
                               "id_user",
                               "nm_user",
                               result.value,
                               true
                           );
                       });
                    }
                }
            }).always(function () {
                delete page.values.isChangeRunning[property];
            });
        };

        /**
         * 検索処理を定義します。
         */
        page.header.search = function (isLoading) {

            var deferred = $.Deferred(),
                element = page.header.element,
                query;

            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            page.header.validator.validate({
                filter: page.header.validationFilter
            }).done(function () {

                page.options.skip = 0;
                var param = page.header.createFilter();

                if (isLoading) {
                    App.ui.loading.show();
                    App.ui.page.notifyAlert.clear();
                }

                return $.ajax(App.ajax.webapi.get(page.header.urls.search, param))
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

        /**
         * clear header and detail
         */
        page.commands.clear = function () {
            //clear header
            App.ui.loading.show();
            var element = page.header.element;
            element.find(":input[type='text'],select").val("");
            element.find(":input[type='checkbox']").prop('checked', false);
            element.findP("no_seiho1").prop("disabled", true);
            element.findP("checkAndOr").prop('checked', true);
            page.loadMasterData().then(function () {
                //clear detail
                page.options.skip = 0;
                page.detail.dataTable.dataTable("clear");
                page.detail.element.findP("data_count").text("");
                page.detail.element.findP("data_count_total").text("");
                $("#nextsearch").removeClass("show-search").hide();
                page.header.validator.validate();
            }).always(function () {
                page.header.element.find(":input:first").focus();
                App.ui.loading.close();
            });
        };

        /**
         * 検索条件のフィルターを定義します。
         */
        page.header.createFilter = function () {
            var element = page.header.element,
                criteria = page.header.element.form().data(),
                filters = {};

            var cd_shain_nen_no_oi, no_seiho_list;
            cd_shain_nen_no_oi = element.findP("cd_shain_nen_no_oi").val().split('-');
            element.findP("check_no_seiho1").is(":checked") ? no_seiho_list = element.findP("no_seiho1").val().split('-') : no_seiho_list = "";

            return filters = {
                checkAndOr: element.findP("checkAndOr").is(":checked") ? true : null,
                checkHaishi: element.findP("checkHaishi").is(":checked") ? true : null,
                cd_shain: cd_shain_nen_no_oi[0] == null || cd_shain_nen_no_oi == "" || cd_shain_nen_no_oi == undefined ? null : cd_shain_nen_no_oi[0],
                nen: cd_shain_nen_no_oi[1] == null || cd_shain_nen_no_oi == "" || cd_shain_nen_no_oi == undefined ? null : cd_shain_nen_no_oi[1],
                no_oi: cd_shain_nen_no_oi[2] == null || cd_shain_nen_no_oi == "" || cd_shain_nen_no_oi == undefined ? null : cd_shain_nen_no_oi[2],
                no_seiho1: no_seiho_list[0] == null || no_seiho_list == "" || no_seiho_list == undefined ? null : no_seiho_list[0],
                no_seiho2: no_seiho_list[1] == null || no_seiho_list == "" || no_seiho_list == undefined ? null : no_seiho_list[1],
                no_seiho3: no_seiho_list[2] == null || no_seiho_list == "" || no_seiho_list == undefined ? null : no_seiho_list[2],
                no_seiho4: no_seiho_list[3] == null || no_seiho_list == "" || no_seiho_list == undefined ? null : no_seiho_list[3],
                nm_hin: criteria.nm_hin,
                cd_group: criteria.group,
                cd_team: criteria.team,
                id_toroku: criteria.tanto,
                cd_user: criteria.user,
                cd_genre: criteria.genre,
                cd_ikatu: criteria.ikatsu,
                youto: criteria.youto,
                tokuchogenryo: criteria.tokuchogenryo,
                flg_shisanIrai: element.findP("flg_shisanIrai").is(":checked") ? true : null,
                nm_genryo: criteria.cd_nm_genryo,
                id_data: page.values.id_data == 1 || page.values.id_data == 2 || page.values.id_data == 3 ? page.values.id_data : null,
                skip: page.options.skip,
                top: page.options.top
            }
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
                    onselect: page.detail.select
                });
            table = element.find(".datatable");         //列固定にした場合DOM要素が再作成されるため、変数を再取得

            page.detail.element = element;
            page.detail.dataTable = datatable;

            element.on("click", "#nextsearch", page.detail.nextsearch);
            element.on("click", "#add-item", function () {
                //window.open("101_ShishakuData.aspx?modeDisp=" + App.settings.app.m_shisaku_data.shinki);
                //window.open("101_ShishakuData.aspx?modeDisp=" + App.settings.app.m_shisaku_data.shinki, "_blank");
                var winObj;
                winObj = App.ui.transfer("101_ShishakuData.aspx?modeDisp=" + App.settings.app.m_shisaku_data.shinki, { wins: page.values.transferPages });
                page.values.transferPages[winObj.name] = winObj.win;
            });

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
            table.on("click", ".edit-select", page.detail.showEditWindow);
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

            return $.ajax(App.ajax.webapi.get(page.header.urls.search, param))
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
            page.detail.dataTable.dataTable("getFirstViewRow", function (row) {
                page.detail.firstViewRow = row;
            });

            //TODO:新規データおよび初期値を設定する処理を記述します。
            var newData = {
                //no_seq: page.values.no_seq
            };
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

            page.detailInput.options.mode = page.detailInput.mode.edit;
            page.detailInput.show(entity);
        };

        /**
         * Show edit window
         */
        page.detail.showEditWindow = function (e) {
            var element = page.detail.element,
                // TOsVN - loc.nt R#464 ST EDIT 2023-02-16
                //selected = element.find(".datatable .select-tab.selected").closest("tbody"),
                selected = $(e.target).closest("tbody"),
                // TOsVN - loc.nt R#464 ED EDIT 2023-02-16
                mode,
                cd_shain,
                nen,
                no_oi,
                isCopy = $(e.target).attr("id") === "copy";
            App.ui.page.notifyAlert.remove("AP0159");
            // TOsVN - loc.nt R#464 ST EDIT 2023-02-16
            if (!selected.length) {
                selected = element.find(".datatable .select-tab.selected").closest("tbody");
            }
            // TOsVN - loc.nt R#464 ED EDIT 2023-02-16
            if (!selected.length) {
                App.ui.page.notifyInfo.message(App.messages.app.AP0008).show();
                return;
            }
            page.detail.dataTable.dataTable("getRow", selected, function (rowObject) {
                row = rowObject.element;
            });
            id = row.attr("data-key");
            entity = page.detail.data.entry(id);

            if (entity.flg_secret == 1) {
                if (App.ui.page.user.EmployeeCD != entity.id_toroku) {
                    if ((App.ui.page.user.cd_group == entity.cd_group && App.ui.page.user.cd_team != entity.cd_team && App.ui.page.user.cd_literal != App.settings.app.cd_yakushoku.group_leader)//the same group and not the same team and not lead group
                        || (App.ui.page.user.cd_group != entity.cd_group && App.ui.page.user.cd_literal != App.settings.app.cd_yakushoku.group_leader)) {//not the same group and not lead group
                        App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0159, { shisakuNo: entity.no_shisaku }), "AP0159").show();
                        return;
                    }
                }
            }

            page.values.isShowEditWindow["isFinish"] = true;
            if (page.values.id_data != 2 && page.values.id_data != 3) {
                delete page.values.isShowEditWindow["isFinish"];
            }
            switch (page.values.id_data) {
                case 1:
                    if (App.ui.page.user.cd_group == entity.cd_group && App.ui.page.user.cd_team != entity.cd_team && App.ui.page.user.cd_literal != App.settings.app.cd_yakushoku.group_leader)//the same group and not the same team and not lead group)
                    {
                        mode = App.settings.app.m_shisaku_data.etsuran;
                    }
                    else if (App.ui.page.user.cd_literal == App.settings.app.cd_yakushoku.team_leader || App.ui.page.user.cd_literal == App.settings.app.cd_yakushoku.group_leader) {
                        mode = isCopy ? App.settings.app.m_shisaku_data.copy : App.settings.app.m_shisaku_data.shosai;
                    }
                    else if (App.ui.page.user.cd_literal == App.settings.app.cd_yakushoku.employee) {
                        mode = App.ui.page.user.EmployeeCD == entity.id_toroku
                                ? (isCopy ? App.settings.app.m_shisaku_data.copy : App.settings.app.m_shisaku_data.shosai)
                                : App.settings.app.m_shisaku_data.etsuran;
                    }
                    break;
                case 2:
                    $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.gettantokaisha, App.ui.page.user.EmployeeCD))).then(function (result) {
                        var item = $.grep(result.value, function (e) {
                            return e.cd_tantokaisha == entity.cd_kaisha;
                        });

                        if (item.length > 0) {
                            mode = isCopy ? App.settings.app.m_shisaku_data.copy : App.settings.app.m_shisaku_data.shosai;
                        }
                        else {
                            mode = App.settings.app.m_shisaku_data.etsuran;
                        }
                    }).always(function () {
                        delete page.values.isShowEditWindow["isFinish"];
                    });
                    break;
                case 3:
                    if (App.ui.page.user.cd_literal == App.settings.app.cd_yakushoku.team_leader || App.ui.page.user.cd_literal == App.settings.app.cd_yakushoku.group_leader) {
                        if (App.ui.page.user.cd_team == entity.cd_team) {
                            $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.gettantokaisha, App.ui.page.user.EmployeeCD))).then(function (result) {
                                var item = $.grep(result.value, function (e) {
                                    return (e.cd_tantokaisha == entity.cd_kaisha);
                                });
                                if (item.length > 0) {
                                    mode = isCopy ? App.settings.app.m_shisaku_data.copy : App.settings.app.m_shisaku_data.shosai;
                                } else {
                                    mode = App.settings.app.m_shisaku_data.etsuran;
                                }
                            }).always(function () {
                                delete page.values.isShowEditWindow["isFinish"];
                            });
                        } else {
                            mode = App.settings.app.m_shisaku_data.etsuran;
                            delete page.values.isShowEditWindow["isFinish"];
                        }
                    }
                    else if (App.ui.page.user.cd_literal == App.settings.app.cd_yakushoku.employee) {
                        $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.gettantokaisha, App.ui.page.user.EmployeeCD))).then(function (result) {
                            var item = $.grep(result.value, function (e) {
                                return (e.cd_tantokaisha == entity.cd_kaisha && App.ui.page.user.EmployeeCD == entity.id_toroku);
                            });

                            if (item.length > 0) {
                                mode = isCopy ? App.settings.app.m_shisaku_data.copy : App.settings.app.m_shisaku_data.shosai;
                            }
                            else {
                                mode = App.settings.app.m_shisaku_data.etsuran;
                            }
                        }).always(function () {
                            delete page.values.isShowEditWindow["isFinish"];
                        });
                    }
                    break;
                case 9:
                    mode = isCopy ? App.settings.app.m_shisaku_data.copy : App.settings.app.m_shisaku_data.shosai;
                    break;
            };
            var sleep = 0;
            var condition = "Object.keys(page.values.isShowEditWindow).length == 0";
            App.ui.wait(sleep, condition, 100).then(function () {
                cd_shain = entity.no_shisaku.substring(0, 10);
                nen = entity.no_shisaku.substring(11, 13);
                no_oi = entity.no_shisaku.substring(14, 18);
                var link = "101_ShishakuData.aspx?modeDisp=" + mode + "&cd_shain=" + cd_shain + "&nen=" + nen + "&no_oi=" + no_oi;
                //window.open(link);
                //window.open(link, "_blank");
                var winObj;
                winObj = App.ui.transfer(link, { wins: page.values.transferPages });
                page.values.transferPages[winObj.name] = winObj.win;

            });
        };

        /**
         * Show page 400
         */
        page.detail.showSekkeiHinshutsuGeto = function (e) {
            var element = page.detail.element,
                selected = element.find(".datatable .select-tab.selected").closest("tbody");
            if (!selected.length) {
                App.ui.page.notifyInfo.message(App.messages.app.AP0008).show();
                return;
            }
            page.detail.dataTable.dataTable("getRow", selected, function (rowObject) {
                row = rowObject.element;
            });
            var id = row.attr("data-key"),
                entity = page.detail.data.entry(id),
                url = page.header.urls.gettantokaisha,
                cd_shain = entity.no_shisaku.substring(0, 10),
                nen = entity.no_shisaku.substring(11, 13),
                no_oi = entity.no_shisaku.substring(14, 18);
                mode = App.settings.app.mode_SekkeiHinshutsuGeto.view;
            url = url + " and cd_tantokaisha eq {1}";
            if (page.values.id_data != 9) {
                if (App.ui.page.user.EmployeeCD != entity.id_toroku) {
                    if (App.ui.page.user.cd_group != entity.cd_group ) {//not the same group
                        App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0159, { shisakuNo: entity.no_shisaku }), "AP0159").show();
                        return;
                    }
                }
            }
            var flg_kakutei = entity.no_row_cus.search("%") >= 0 ? true : false;
            page.values.isShowSekkeiHinshutsuGeto["isFinish"] = true;
            switch (page.values.id_data) {
                case 1:
                    if (App.ui.page.user.cd_literal == App.settings.app.cd_yakushoku.group_leader
                        || (App.ui.page.user.cd_group == entity.cd_group && App.ui.page.user.cd_team == entity.cd_team && App.ui.page.user.cd_literal == App.settings.app.cd_yakushoku.team_leader)) {
                        mode = flg_kakutei ? App.settings.app.mode_SekkeiHinshutsuGeto.viewEdit : App.settings.app.mode_SekkeiHinshutsuGeto.edit;
                    }
                    else if (App.ui.page.user.cd_literal == App.settings.app.cd_yakushoku.employee) {
                        mode = App.ui.page.user.EmployeeCD == entity.id_toroku ? flg_kakutei ? App.settings.app.mode_SekkeiHinshutsuGeto.viewEdit : App.settings.app.mode_SekkeiHinshutsuGeto.edit : mode;
                    }
                    delete page.values.isShowSekkeiHinshutsuGeto["isFinish"];
                    break;
                case 2:
                    $.ajax(App.ajax.odata.get(App.str.format(url, App.ui.page.user.EmployeeCD, entity.cd_kaisha))).then(function (result) {
                        if (result.value.length > 0) {
                            mode = flg_kakutei ? App.settings.app.mode_SekkeiHinshutsuGeto.viewEdit : App.settings.app.mode_SekkeiHinshutsuGeto.edit;
                        }
                    }).always(function () {
                        delete page.values.isShowSekkeiHinshutsuGeto["isFinish"];
                    });
                    break;
                case 3:
                    if (App.ui.page.user.cd_group == entity.cd_group
                        && App.ui.page.user.cd_team == entity.cd_team
                        && (App.ui.page.user.cd_literal == App.settings.app.cd_yakushoku.team_leader
                            || App.ui.page.user.cd_literal == App.settings.app.cd_yakushoku.group_leader)) {
                        $.ajax(App.ajax.odata.get(App.str.format(url, App.ui.page.user.EmployeeCD, entity.cd_kaisha))).then(function (result) {
                            if (result.value.length > 0) {
                                mode = flg_kakutei ? App.settings.app.mode_SekkeiHinshutsuGeto.viewEdit : App.settings.app.mode_SekkeiHinshutsuGeto.edit;
                            }
                        }).always(function () {
                            delete page.values.isShowSekkeiHinshutsuGeto["isFinish"];
                        });
                    }
                    else if (App.ui.page.user.cd_literal == App.settings.app.cd_yakushoku.employee) {
                        $.ajax(App.ajax.odata.get(App.str.format(url, App.ui.page.user.EmployeeCD, entity.cd_kaisha))).then(function (result) {
                            if (result.value.length > 0 && App.ui.page.user.EmployeeCD == entity.id_toroku) {
                                mode = flg_kakutei ? App.settings.app.mode_SekkeiHinshutsuGeto.viewEdit : App.settings.app.mode_SekkeiHinshutsuGeto.edit;
                            }
                        }).always(function () {
                            delete page.values.isShowSekkeiHinshutsuGeto["isFinish"];
                        });
                    } else {
                        delete page.values.isShowSekkeiHinshutsuGeto["isFinish"];
                    }
                    break;
                case 9:
                    mode = flg_kakutei ? App.settings.app.mode_SekkeiHinshutsuGeto.viewEdit : App.settings.app.mode_SekkeiHinshutsuGeto.edit;
                    delete page.values.isShowSekkeiHinshutsuGeto["isFinish"];
                    break;
            };
            var sleep = 0;
            var condition = "Object.keys(page.values.isShowSekkeiHinshutsuGeto).length == 0";
            App.ui.wait(sleep, condition, 100).then(function () {
                //var link = "400_SekkeiHinshutsuGeto.aspx?mode=" + mode + "&cd_shain=" + cd_shain + "&nen=" + nen + "&no_oi=" + no_oi;
                //App.ui.transfer("../Pages/" + link, { target: (new Date()).getTime(),features:"" });
                var winObj;
                winObj = App.ui.transfer("400_SekkeiHinshutsuGeto.aspx?mode=" + mode + "&cd_shain=" + cd_shain + "&nen=" + nen + "&no_oi=" + no_oi, { wins: page.values.transferPages });
                page.values.transferPages[winObj.name] = winObj.win;
            });
        };

        /**
         * Show dialog 405 コピー元選択ダイアログ
         */
        page.detail.showCopyMotoSentakuDialog = function (e) {
            var element = page.detail.element,
                selected = element.find(".datatable .select-tab.selected").closest("tbody");
            if (!selected.length) {
                App.ui.page.notifyInfo.message(App.messages.app.AP0008).show();
                return;
            }
            App.ui.loading.show();
            page.detail.dataTable.dataTable("getRow", selected, function (rowObject) {
                row = rowObject.element;
            });
            var id = row.attr("data-key"),
                entity = page.detail.data.entry(id),
                url = page.header.urls.gettantokaisha,
                cd_shain = entity.no_shisaku.substring(0, 10),
                nen = entity.no_shisaku.substring(11, 13),
                no_oi = entity.no_shisaku.substring(14, 18);
            mode = App.settings.app.mode_SekkeiHinshutsuGeto.view;
            url = url + " and cd_tantokaisha eq {1}";
            if (page.values.id_data != 9) {
                if (App.ui.page.user.EmployeeCD != entity.id_toroku) {
                    if (App.ui.page.user.cd_group != entity.cd_group) {//not the same group
                        App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0159, { shisakuNo: entity.no_shisaku }), "AP0159").show();
                        App.ui.loading.close();
                        return;
                    }
                }
            }
            page.values.isShowSekkeiHinshutsuGeto["isFinish"] = true;
            switch (page.values.id_data) {
                case 1:
                    if (App.ui.page.user.cd_literal == App.settings.app.cd_yakushoku.group_leader
                        || (App.ui.page.user.cd_group == entity.cd_group && App.ui.page.user.cd_team == entity.cd_team && App.ui.page.user.cd_literal == App.settings.app.cd_yakushoku.team_leader)) {
                        mode = App.settings.app.mode_SekkeiHinshutsuGeto.edit;
                    }
                    else if (App.ui.page.user.cd_literal == App.settings.app.cd_yakushoku.employee && App.ui.page.user.EmployeeCD == entity.id_toroku) {
                        mode =  App.settings.app.mode_SekkeiHinshutsuGeto.edit;
                    }
                    delete page.values.isShowSekkeiHinshutsuGeto["isFinish"];
                    break;
                case 2:
                    $.ajax(App.ajax.odata.get(App.str.format(url, App.ui.page.user.EmployeeCD, entity.cd_kaisha))).then(function (result) {
                        if (result.value.length > 0) {
                            mode = App.settings.app.mode_SekkeiHinshutsuGeto.edit;
                        }
                    }).always(function () {
                        delete page.values.isShowSekkeiHinshutsuGeto["isFinish"];
                    });
                    break;
                case 3:
                    if (App.ui.page.user.cd_group == entity.cd_group
                        && App.ui.page.user.cd_team == entity.cd_team
                        && (App.ui.page.user.cd_literal == App.settings.app.cd_yakushoku.team_leader
                            || App.ui.page.user.cd_literal == App.settings.app.cd_yakushoku.group_leader)) {
                        $.ajax(App.ajax.odata.get(App.str.format(url, App.ui.page.user.EmployeeCD, entity.cd_kaisha))).then(function (result) {
                            if (result.value.length > 0) {
                                mode = App.settings.app.mode_SekkeiHinshutsuGeto.edit;
                            }
                        }).always(function () {
                            delete page.values.isShowSekkeiHinshutsuGeto["isFinish"];
                        });
                    }
                    else if (App.ui.page.user.cd_literal == App.settings.app.cd_yakushoku.employee) {
                        $.ajax(App.ajax.odata.get(App.str.format(url, App.ui.page.user.EmployeeCD, entity.cd_kaisha))).then(function (result) {
                            if (result.value.length > 0 && App.ui.page.user.EmployeeCD == entity.id_toroku) {
                                mode = App.settings.app.mode_SekkeiHinshutsuGeto.edit;
                            }
                        }).always(function () {
                            delete page.values.isShowSekkeiHinshutsuGeto["isFinish"];
                        });
                    } else {
                        delete page.values.isShowSekkeiHinshutsuGeto["isFinish"];
                    }
                    break;
                case 9:
                    mode = App.settings.app.mode_SekkeiHinshutsuGeto.edit;
                    delete page.values.isShowSekkeiHinshutsuGeto["isFinish"];
                    break;
            };
            var sleep = 0;
            var condition = "Object.keys(page.values.isShowSekkeiHinshutsuGeto).length == 0";
            App.ui.wait(sleep, condition, 100).then(function () {
                if (mode != App.settings.app.mode_SekkeiHinshutsuGeto.edit) {
                    page.dialogs.confirmDialog.confirm({
                        text: App.str.format(App.messages.app.AP0179, App.ui.page.user.nm_kaisha, App.ui.page.user.nm_busho, App.ui.page.user.Name),
                        hideCancel: true,
                        multiModal: true,
                        backdrop: "static",
                        keyboard: false
                    });
                    App.ui.loading.close();
                    return;
                } else {
                    page.dialogs.copyMotoSentakuDialog.options.cd_shain = cd_shain;
                    page.dialogs.copyMotoSentakuDialog.options.nen = nen;
                    page.dialogs.copyMotoSentakuDialog.options.no_oi = no_oi;
                    page.detail.values.paramHaita = {
                        cd_shain: cd_shain,
                        nen: nen,
                        no_oi: no_oi,
                        id: (new Date()).getTime()
                    }
                    page.detail.checkKakutei(parseFloat(cd_shain), parseFloat(nen), parseFloat(no_oi)).then(function (result) {
                        if (result != "NG") {
                            if (result == "OK") {
                                App.ui.loading.close();
                                page.dialogs.copyMotoSentakuDialog.element.modal("show");
                                page.dialogs.copyMotoSentakuDialog.dataSelected = function (data) {
                                    page.detail.values.paramHaita.cd_shain = "";
                                    if (data) {
                                        //var link = "400_SekkeiHinshutsuGeto.aspx?mode=1&cd_shain=" + cd_shain + "&nen=" + nen + "&no_oi=" + no_oi;
                                        //App.ui.transfer("../Pages/" + link, { target: page.detail.values.paramHaita.id, features: "" });
                                        var winObj;
                                        winObj = App.ui.transfer("400_SekkeiHinshutsuGeto.aspx?mode=1&cd_shain=" + cd_shain + "&nen=" + nen + "&no_oi=" + no_oi, { wins: page.values.transferPages });
                                        page.values.transferPages[winObj.name] = winObj.win;
                                    }
                                    delete page.dialogs.copyMotoSentakuDialog.dataSelected;
                                }
                            } else {
                                var no_seiho = page.detail.values.paramHaita.cd_shain + "-" + page.detail.values.paramHaita.nen + "-" + page.detail.values.paramHaita.no_oi;
                                $.ajax(App.ajax.webapi.get(page.header.urls.getUserInfo, { cd_shain: result }))
                                .then(function (result) {
                                    page.dialogs.confirmDialog.confirm({
                                        text: App.str.format(App.messages.app.AP0178, no_seiho, result.nm_kaisha, result.nm_busho, result.nm_user),
                                        hideCancel: true,
                                        multiModal: true,
                                        backdrop: "static",
                                        keyboard: false
                                    });
                                }).fail(function (error) {
                                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                                }).always(function () {
                                    App.ui.loading.close();
                                });
                            }
                        }
                    });
                }
            });
        };

        /**
        *　check リーダー承認（品位確定時）
        */
        page.detail.checkKakutei = function (cd_shain, nen, no_oi) {
            var deferred = $.Deferred();
            $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.getGateHeader, cd_shain, nen, no_oi)))
            .then(function (result) {
                if (result.value[0].flg_shonin_reader_confirm == "1") {
                    App.ui.loading.close();
                    page.dialogs.confirmDialog.confirm({
                        text: App.messages.app.AP0185,
                        hideCancel: true,
                        multiModal: true,
                        backdrop: "static",
                        keyboard: false
                    });
                    deferred.resolve("NG");
                } else {
                    var str = result.value[0].kbn_haita == null ? "OK" : result.value[0].kbn_haita;
                    deferred.resolve(str);
                }
            }).fail(function (error) {
                deferred.reject();
            });
            return deferred.promise();
        };

        /**
        *　画面を開きるとき排他区分がEmployeeCDになる。
        */
        page.detail.checkHaitaKubun = function (data) {
            var deferred = $.Deferred();
            var no_seiho = page.detail.values.paramHaita.cd_shain + "-" + page.detail.values.paramHaita.nen + "-" + page.detail.values.paramHaita.no_oi;
            $.ajax(App.ajax.webapi.get(page.header.urls.getUserInfo, { cd_shain: data }))
            .then(function (result) {
                page.dialogs.confirmDialog.confirm({
                    text: App.str.format(App.messages.app.AP0178, no_seiho, result.kaisha, result.busho, result.user),
                    hideCancel: true,
                    multiModal: true,
                    backdrop: "static",
                    keyboard: false
                });
            }).fail(function (error) {
                deferred.reject();
            }).always(function () {
                App.ui.loading.close();
            });
            return deferred.promise();
        }

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

        /**
         * jQuery イベントで、ページの読み込み処理を定義します。
         */
        $(function () {
            // ページの初期化処理を呼び出します。
            page.initialize();
        });

        $(document).on("keydown", "[data-tab-direct]", function (e) {
            var code = e.keyCode || e.which;

            if (code === 9) {
                var target = $(e.target),
                    nextIndex = target.attr("data-tab-direct");
                if (nextIndex) {
                    var nextTarget = target.closest(".header").findP(nextIndex);
                    while (nextTarget.length && (nextTarget.is(":disabled") || !nextTarget.is(":visible"))) {
                        var passNextIndex = nextTarget.attr("data-tab-direct");
                        if (!passNextIndex) {
                            return;
                        }
                        nextTarget = target.closest(".header").findP(passNextIndex);
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

    <div class="content-wrap smaller">
        <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
        <div class="header">
            <%--<div class="row">
                <div class="control col-xs-6">
                    <span style="white-space: nowrap;"></span>
                </div>
                <div class="control col-xs-2">
                    <label style="width: 80px;">所属会社:</label>
                    <span style="width: 150px;" data-prop="cd_kaisha"></span>
                </div>
                <div class="control col-xs-2">
                    <label style="width: 80px;">所属部署:</label>
                    <span style="width: 150px;" data-prop="nm_busho"></span>
                </div>
                <div class="control col-xs-2">
                    <label style="width: 80px;">担当者:</label>
                    <span style="width: 150px;" data-prop="cd_tanto"></span>
                </div>
            </div>--%>
            <div title="検索条件" class="part">
                <div class="row">
                    <div class="col-xs-5 control">
                        <label class="check-group">
                            製法も検索対象
                            <input type="checkbox" data-prop="check_no_seiho1" />
                        </label>
                        <label class="check-group">
                            条件で絞込み
                            <input type="checkbox" data-prop="checkAndOr" checked="checked" />
                        </label>
                        <label class="check-group">
                            廃止も検索対象
                            <input type="checkbox" data-prop="checkHaishi" />
                        </label>
                        <label class="check-group">
                            原価依頼のみ
                            <input type="checkbox" data-prop="flg_shisanIrai" data-tab-direct="cd_shain_nen_no_oi" />
                        </label>
                    </div>
                    <div class="control col-xs-1">
                    </div>
                    <div class="control-label col-xs-1">
                        <label>原料</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="text" data-prop="cd_nm_genryo" data-tab-direct="genre" />
                    </div>
                    <div class="control col-xs-2">
                        <label>※半角カンマ区切りで複数入力</label>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>試作No</label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="text" data-prop="cd_shain_nen_no_oi" maxlength="17" data-tab-direct="no_seiho1" />
                    </div>
                    <div class="control-label col-xs-1">
                        <label>所属グループ</label>
                    </div>
                    <div class="control col-xs-2">
                        <select data-prop="group" data-tab-direct="team"></select>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>ジャンル</label>
                    </div>
                    <div class="control col-xs-2">
                        <select data-prop="genre" data-tab-direct="ikatsu"></select>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>製品の用途</label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="text" data-prop="youto" data-tab-direct="tokuchogenryo" />
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>製法No</label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="text" data-prop="no_seiho1" maxlength="16" disabled="disabled" data-tab-direct="nm_hin" style="text-transform:uppercase;" />
                    </div>
                    <div class="control-label col-xs-1">
                        <label>所属チーム</label>
                    </div>
                    <div class="control col-xs-2">
                        <select data-prop="team" data-tab-direct="tanto"></select>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>一括表示名称</label>
                    </div>
                    <div class="control col-xs-2">
                        <select data-prop="ikatsu" data-tab-direct="user"></select>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>特徴原料</label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="text" data-prop="tokuchogenryo" data-tab-direct="search" />
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>試作名</label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="text" data-prop="nm_hin" data-tab-direct="group" />
                    </div>
                    <div class="control-label col-xs-1">
                        <label>担当者</label>
                    </div>
                    <div class="control col-xs-2">
                        <select data-prop="tanto" data-tab-direct="cd_nm_genryo"></select>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>ユーザー</label>
                    </div>
                    <div class="control col-xs-2">
                        <select data-prop="user" data-tab-direct="youto"></select>
                    </div>
                    <div class="control col-xs-3">
                    </div>
                </div>
                <div class="header-command">
                    <button type="button" id="search" data-prop="search" class="btn btn-sm btn-primary">検索</button>
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
                    <button type="button" class="btn btn-default btn-xs" id="add-item" disabled>新規</button>
                </div>
                <span class="data-count">
                    <span data-prop="data_count"></span>
                    <span>/</span>
                    <span data-prop="data_count_total"></span>
                </span>
            </div>
            <table class="datatable ellipsis">
                <!--TODO: 明細一覧のヘッダーを定義するHTMLをここに記述します。-->
                <thead>
                    <tr>
                        <!--TODO: 単一行を作成する場合は、下記を利用します。-->
                        <th style="width: 10px;"></th>
                        <th style="width: 55px;"></th>
                        <th style="width: 55px;">詳細</th>
                        <th style="width: 150px;">試作No</th>
                        <th style="width: 140px;">製法No</th>
                        <th style="width: 360px;">試作名</th>
                        <th style="width: 95px;">担当者</th>
                        <th style="width: 168px;">ユーザー</th>
                        <th style="width: 90px;">ジャンル</th>
                        <th style="width: 140px;">一括表示名称</th>
                        <th style="width: 120px;">製品の用途</th>
                        <th style="width: 120px;">特徴原料</th>
                        <th style="width: 70px;">廃止</th>
                    </tr>
                </thead>
                <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->
                <tbody class="item-tmpl" style="cursor: default; display: none;">
                    <tr>
                        <!--TODO: 単一行を作成する場合は、以下を利用します。-->
                        <td>
                            <span class="select-tab unselected"></span>
                        </td>
                        <td class="right">
                            <label data-prop="no_row_cus"></label>
                        </td>
                        <td class="center">
                            <button type="button" class="btn btn-info btn-xt min-zero edit-select" style="width: 80%" disabled>詳細</button>
                        </td>
                        <td>
                            <label data-prop="no_shisaku"></label>
                        </td>
                        <td>
                            <label data-prop="no_seiho"></label>
                        </td>
                        <td>
                            <label data-prop="nm_hin"></label>
                        </td>
                        <td>
                            <label data-prop="nm_tanto"></label>
                        </td>
                        <td>
                            <label data-prop="nm_user"></label>
                        </td>
                        <td>
                            <label data-prop="nm_genre"></label>
                        </td>
                        <td>
                            <label data-prop="nm_ikatu"></label>
                        </td>
                        <td>
                            <label data-prop="nm_youto"></label>
                        </td>
                        <td>
                            <label data-prop="nm_genryo"></label>
                        </td>
                        <td>
                            <label data-prop="nm_haishi"></label>
                        </td>

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
        <button type="button" id="clear" class="btn btn-sm btn-primary">クリア</button>
    </div>
    <div class="command">
        <!--TODO: コマンドボタン（右寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" id="reset400" class="btn btn-sm btn-primary" disabled>排他クリア（設計品質ｹﾞｰﾄ）</button>
        <button type="button" id="reset" class="btn btn-sm btn-primary" disabled>排他クリア（試作）</button>
        <button type="button" id="copy_sekkei_hinshitsu_gate" class="btn btn-sm btn-primary" disabled>設計品質ｹﾞｰﾄｺﾋﾟｰ</button>
        <button type="button" style="margin-right:40px;" id="sekkei_hinshitsu_gate" class="btn btn-sm btn-primary" disabled>設計品質ｹﾞｰﾄ</button>
        <button type="button" id="copy" class="btn btn-sm btn-primary" disabled>製法支援コピー</button>
    </div>

</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>
