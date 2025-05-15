<%@ Page Title="010_担当者マスタ" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="010_TantoshaMaster.aspx.cs" Inherits="Tos.Web.Pages.TantoshaMaster" %>

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

        /*.btn-next-search {
            width: 200px;
        }*/

        .detailInput .tanto-tmpl {
            display: none;
        }

        .detailInput .seizokaisha-height .close-input {
            position: absolute;
            right: 13px;
            top: 5px;
            color: gray;
        }

        .detailInput .seizokaisha-height {
            height: 93px!important;
        }

        .detailInput .required {
            color: red;
        }

        .detailInput input[type='password'] {
            border: 1px solid #cccccc;
            border-radius: 2px;
            margin: 1px;
            padding: 1px;
            display: inline-block;
            height: 26px;
            font-family: Meiryo, MS PGothic, Segoe UI;
        }

        .detailInput input[type='checkbox'] {
            margin-top: 6px;
        }
    </style>


    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("TantoshaMaster", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.dataTakeCount,   // TODO:取得するデータ数を指定します。
                filter: ""
            },
            values: {
                isChangeRunning: {},
                //2019-08-15 : START
                //[2019-06-28 : spec change kino value from 20 to 21]
                //id_kino_edit: 21,//20,//編集 (edit) 
                //Request #15091 : 【権限】権限の変更について
                //担当者マスタメンテナンス→id_kinoを21→20に変更
                id_kino_edit: 20,
                id_kino_person: 21,
                //2019-08-15 : END
                honnin_nomi: 1,//本人のみ(氏名、パスワード)
                shozoku_kaisha: 2,//所属会社
                subete: 9//すべて 
            },
            urls: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
                kaisha: "../Services/ShisaQuickService.svc/ma_kaisha?$orderby=cd_kaisha",
                group: "../Services/ShisaQuickService.svc/ma_group?$filter=cd_kaisha eq {0}&$orderby=flg_hyoji",
                busho: "../api/TantoshaMaster/GetBusho",
                team: "../Services/ShisaQuickService.svc/ma_team?$filter=cd_group eq {0}&$orderby=flg_hyoji",
                literal: "../Services/ShisaQuickService.svc/ma_literal?$filter=cd_category eq '{0}'&$orderby=no_sort",
                kengen: "../Services/ShisaQuickService.svc/ma_kengen?$filter=kbn_eigyo eq null&$orderby=cd_kengen",
                TantoshaMaster: "../api/TantoshaMaster/",
                kaishaSearchDialog: "Dialogs/500_SeizoTantoKaishaKensaku_Dialog.aspx",
                mainMenu: "MainMenu.aspx"
            },
            header: {
                options: {},
                values: {},
                urls: {
                    search: "../api/TantoshaMaster/GetData",
                    tantoKaisha: "../api/TantoshaMaster/GetTantoKaisha"
                }
            },
            detail: {
                options: {},
                values: {}
            },
            detailInput: {
                options: {},
                values: {
                },
                mode: {
                    input: "input",
                    edit: "edit"
                },
                urls: {
                    ma_user_togo: "../Services/ShisaQuickService.svc/ma_user_togo?$filter=id_user eq {id_user}",
                    ma_user_new: "../Services/ShisaQuickService.svc/ma_user_new?$filter=id_user eq {id_user}",
                    //2019/11/20 : START : Request #15896
                    //010担当者マスタの営業の判断を＜011担当者マスタ(営業)と同じように＞
                    //「権限マスタの営業区分」で行う
                    //●「権限マスタの営業区分」がNULLのユーザを対象とする
                    //eigyo: "../Services/ShisaQuickService.svc/ma_busho?$filter=cd_kaisha eq {cd_kaisha}M and cd_busho eq {cd_busho}M and flg_eigyo eq {flg_eigyo}",
                    //2019/11/20 : END : Request #15896
                }
            },
            dialogs: {},
            commands: {},
            infoDialog: {}
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

            var sleep = 0;
            var condition = "Object.keys(page.values.isChangeRunning).length == 0";
            App.ui.wait(sleep, condition, 100).then(function () {

                page.validateAll().then(function () {

                    //2019/11/20 : START : Request #15829
                    //商品開発サポートでは、新規登録時、IDを入力してロストフォーカスすると
                    //エラーメッセージが表示されるため、新たに別会社での登録ができません。
                    //登録ができるように、ロストフォーカス時に行っているチェック処理を
                    //保存ボタン押下時にしていただけないでしょうか。
                    var element = page.detailInput.element,
                        id_user = Number(element.findP("id_user").val()) + "M",
                        cd_kaisha = element.findP("cd_kaisha").val();

                    $.ajax(App.ajax.odata.get(App.str.format(page.detailInput.urls.ma_user_togo + " and cd_kaisha eq {cd_kaisha}", { id_user: id_user, cd_kaisha: cd_kaisha }))).then(function (results) {
                        if (results.value.length) {
                            page.infoDialog.confirm({
                                text: App.str.format(App.messages.app.AP0157, "社員", "担当者マスタ又は、担当者マスタ（営業）"),
                                backdrop: "static",
                                hideCancel: true,
                                keyboard: false
                            })
                        } else {

                            page.dialogs.confirmDialog.confirm({
                                text: App.messages.app.AP0004
                            }).then(function () {

                                App.ui.loading.show();

                                var element = page.detailInput.element,
                                    check = page.kengenBunrui(element.findP("cd_kengen").val());

                                var changeSets = {
                                    ma_user_togo: page.detailInput.data.getChangeSet(),
                                    ma_tantokaisya: page.detailInput.dataTanto.getChangeSet(),
                                    id_user: page.detailInput.element.findP("id_user").val(),
                                    cd_category: App.settings.app.cd_category.yakushoku,
                                    flg_eigyo: App.settings.app.kbn_eigyo.eigyo_nashi,
                                    kbn_kengen_bunrui: (check == App.settings.app.kbn_kengen_bunrui.shisaquick || check == App.settings.app.kbn_kengen_bunrui.shisaquick_seihoshien_kaihatsu || check == App.settings.app.kbn_kengen_bunrui.shisaquick_seihoshien_kojo),
                                    password_org: page.detailInput.values.password_org
                                }

                                //TODO: データの保存処理をここに記述します。
                                return $.ajax(App.ajax.webapi.post(page.urls.TantoshaMaster, changeSets)).then(function (result) {

                                    //TODO: データの保存成功時の処理をここに記述します。
                                    page.detailInput.previous(false, true);
                                    return App.async.all([page.header.search(false)]);
                                }).then(function () {

                                    App.ui.loading.close();
                                    App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                                }).fail(function (error) {

                                    App.ui.loading.close();
                                    if (error.status === App.settings.base.conflictStatus) {
                                        // TODO: 同時実行エラー時の処理を行っています。
                                        // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                                        //page.detailInput.previous(false);
                                        //page.header.search(false);
                                        App.ui.page.notifyAlert.clear();
                                        App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
                                        return;
                                    }

                                    //営業担当者チェック
                                    //if (error.status === App.settings.base.validationErrorStatus && error.responseJSON == "flg_eigyo") {
                                    //    App.ui.page.notifyAlert.message(App.messages.app.AP0121).show();
                                    //    return;
                                    //}

                                    //営業担当者チェック
                                    if (error.status === App.settings.base.validationErrorStatus && error.responseJSON == "isNotExitUserNew") {
                                        App.ui.page.notifyAlert.message(App.messages.app.AP0122).show();
                                        return;
                                    }

                                    //TODO: データの保存失敗時の処理をここに記述します。
                                    if (error.status === App.settings.base.validationErrorStatus) {
                                        var errors = error.responseJSON;
                                        $.each(errors, function (index, err) {
                                            var InvalidationName = "";
                                            switch (err.InvalidationName) {

                                                case "ma_user_togo":
                                                    InvalidationName = err.Data["id_user"] + "、" + err.Data["cd_kaisha"];
                                                    break
                                                case "ma_tantokaisya":
                                                    InvalidationName = err.Data["id_user"] + "、" + err.Data["cd_tantokaisha"];
                                                    break;
                                                default:
                                                    InvalidationName = (App.isUndefOrNull(err.InvalidationName) ? "" : err.Data[err.InvalidationName]);
                                                    break;
                                            }

                                            App.ui.page.notifyAlert.message(err.Message + InvalidationName).show();
                                        });
                                        return;
                                    }
                                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                                });
                            })
                        }
                    }).fail(function (error) {
                        App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                    })

                });

            }).fail(function () {
                App.ui.page.notifyAlert.message(App.messages.base.MS0006).show();
            }).always(function () {
                setTimeout(function () {
                    page.header.element.find(":input:first").focus();
                }, 100);
            });
        };

        /**
        * 画面明細の対象行を更新します。
        */
        page.commands.editItem = function () {

            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            var sleep = 0;
            var condition = "Object.keys(page.values.isChangeRunning).length == 0";
            App.ui.wait(sleep, condition, 100).then(function () {
                page.validateAll().then(function () {

                    page.dialogs.confirmDialog.confirm({
                        text: App.messages.app.AP0004
                    }).then(function () {

                        App.ui.loading.show();

                        var element = page.detailInput.element,
                            check = page.kengenBunrui(element.findP("cd_kengen").val());

                        var changeSets = {
                            ma_user_togo: page.detailInput.data.getChangeSet(),
                            ma_tantokaisya: page.detailInput.dataTanto.getChangeSet(),
                            id_user: page.detailInput.element.findP("id_user").val(),
                            cd_category: App.settings.app.cd_category.yakushoku,
                            flg_eigyo: App.settings.app.kbn_eigyo.eigyo_nashi,
                            kbn_kengen_bunrui: (check == App.settings.app.kbn_kengen_bunrui.shisaquick || check == App.settings.app.kbn_kengen_bunrui.shisaquick_seihoshien_kaihatsu || check == App.settings.app.kbn_kengen_bunrui.shisaquick_seihoshien_kojo),
                            password_org: page.detailInput.values.password_org
                        }

                        //TODO: データの更新処理をここに記述します。
                        return $.ajax(App.ajax.webapi.put(page.urls.TantoshaMaster, changeSets)).then(function (result) {

                            //TODO: データの保存成功時の処理をここに記述します。
                            page.detailInput.previous(false, false);
                            //TODO: データの保存成功時の処理をここに記述します。
                            return page.detail.updateData(changeSets, result);
                        }).then(function (result) {

                            App.ui.loading.close();
                            App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                        }).fail(function (error) {

                            App.ui.loading.close();
                            if (error.status === App.settings.base.conflictStatus) {
                                // TODO: 同時実行エラー時の処理を行っています。
                                // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                                //page.detailInput.previous(false);
                                //page.header.search(false);
                                App.ui.page.notifyAlert.clear();
                                App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
                                return;
                            }

                            //営業担当者チェック
                            //if (error.status === App.settings.base.validationErrorStatus && error.responseJSON == "flg_eigyo") {
                            //    App.ui.page.notifyAlert.message(App.messages.app.AP0121).show();
                            //    return;
                            //}

                            //営業担当者チェック
                            if (error.status === App.settings.base.validationErrorStatus && error.responseJSON == "isNotExitUserNew") {
                                App.ui.page.notifyAlert.message(App.messages.app.AP0122).show();
                                return;
                            }

                            //TODO: データの保存失敗時の処理をここに記述します。
                            if (error.status === App.settings.base.validationErrorStatus) {
                                var errors = error.responseJSON;
                                $.each(errors, function (index, err) {
                                    var InvalidationName = "";
                                    switch (err.InvalidationName) {

                                        case "ma_user_togo":
                                            InvalidationName = err.Data["id_user"] + "、" + err.Data["cd_kaisha"];
                                            break
                                        case "ma_tantokaisya":
                                            InvalidationName = err.Data["id_user"] + "、" + err.Data["cd_tantokaisha"];
                                            break;
                                            break;
                                        default:
                                            InvalidationName = (App.isUndefOrNull(err.InvalidationName) ? "" : err.Data[err.InvalidationName]);
                                            break;
                                    }

                                    App.ui.page.notifyAlert.message(err.Message + InvalidationName).show();
                                });
                                return;
                            }
                            App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                        });
                    })
                });
            }).fail(function () {
                App.ui.page.notifyAlert.message(App.messages.base.MS0006).show();
            }).always(function () {
                setTimeout(function () {
                    page.header.element.find(":input:first").focus();
                }, 100);
            });

        };

        /**
         * 画面明細の対象行を削除します。
         */
        page.commands.deleteItem = function () {

            var options = {
                text: App.messages.base.MS0003
            };

            page.dialogs.confirmDialog.confirm(options).then(function () {

                App.ui.page.notifyWarn.clear();
                App.ui.page.notifyAlert.clear();
                App.ui.page.notifyInfo.clear();

                App.ui.loading.show();

                var element = page.detailInput.element,
                    changeSets;

                var dataSet = App.ui.page.dataSet();
                page.detailInput.dataDelete = dataSet;

                page.detailInput.dataDelete.attach(page.detailInput.dataDelOriginal);
                page.detailInput.dataDelete.remove(page.detailInput.dataDelOriginal);

                var changeSets = {
                    ma_user_togo: page.detailInput.dataDelete.getChangeSet(),
                    ma_tantokaisya: page.detailInput.dataTanto.getChangeSet(),
                    id_user: page.detailInput.element.findP("id_user").val(),
                    cd_kaisha: page.detailInput.element.findP("cd_kaisha").val(),
                    cd_category: App.settings.app.cd_category.yakushoku,
                    flg_eigyo: App.settings.app.kbn_eigyo.eigyo_nashi
                }

                //TODO: データの更新処理をここに記述します。
                $.ajax(App.ajax.webapi["delete"](page.urls.TantoshaMaster, changeSets)).then(function (result) {

                    //TODO: データの保存成功時の処理をここに記述します。
                    page.detail.removeData(changeSets.ma_user_togo);
                    page.detailInput.previous(false, true);

                    App.ui.page.notifyInfo.message(App.messages.base.MS0008).show();
                }).fail(function (error) {
                    //営業担当者チェック
                    //if (error.status === App.settings.base.validationErrorStatus && error.responseJSON == "flg_eigyo") {
                    //    App.ui.page.notifyAlert.message(App.messages.app.AP0121).show();
                    //    return;
                    //}

                    //営業担当者チェック
                    if (error.status === App.settings.base.validationErrorStatus && error.responseJSON == "isNotExitUserNew") {
                        App.ui.page.notifyAlert.message(App.messages.app.AP0122).show();
                        return;
                    }

                    if (error.status === App.settings.base.conflictStatus) {
                        // TODO: 同時実行エラー時の処理を行っています。
                        // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                        //page.detailInput.previous(false);
                        //page.header.search(false);
                        App.ui.page.notifyAlert.clear();
                        App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
                        return;
                    }
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

            return page.detailInput.values.isChange;
            //if (page.detailInput.data) {
            //    detailInput = page.detailInput.data.getChangeSet();
            //    if (detailInput.created.length || detailInput.updated.length || detailInput.deleted.length) {
            //        return closeMessage;
            //    }
            //}

            ////check exit change data tanto kaisha
            //if (page.detailInput.dataTanto) {
            //    detailTanto = page.detailInput.dataTanto.getChangeSet();
            //    if (detailTanto.created.length || detailTanto.updated.length || detailTanto.deleted.length) {
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
            page.detail.initialize();
            page.detailInput.initialize();

            page.flgEditAble = {
                honnin_nomi: false,
                shozoku_kaisha: false,
                subete: false
            }

            //TODO: ヘッダー/明細以外の初期化の処理を記述します。
            getKengenGamen(App.settings.app.id_gamen.tantosha_master).then(function (results) {
                page.kengenGamen = results;

                $.each(results, function (index, item) {
                    if (item.id_kino == page.values.id_kino_person && item.id_data == page.values.honnin_nomi) {
                        page.flgEditAble.honnin_nomi = true;
                    }
                    if (item.id_kino == page.values.id_kino_edit && item.id_data == page.values.shozoku_kaisha) {
                        page.flgEditAble.shozoku_kaisha = true;
                    }
                    if (item.id_kino == page.values.id_kino_edit && item.id_data == page.values.subete) {
                        page.flgEditAble.subete = true;
                    }
                });

                // Get max kengen in case many kengen have been found
                // [subete] > [honnin_nomi] > [shozoku_kaisha]
                if (page.flgEditAble.subete) {
                    page.flgEditAble.honnin_nomi = false;
                    page.flgEditAble.shozoku_kaisha = false;
                }
                if (page.flgEditAble.shozoku_kaisha) {
                    page.flgEditAble.honnin_nomi = false;
                }

                return page.loadMasterData(".header, .detailInput");
            }).then(function (result) {
                page.controlEditGamen();

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
        * 画面表示を設定する。
        */
        page.controlEditGamen = function () {
            var element = page.detailInput.element;

            //所属部と署所属会社を活性する。
            element.find(".busho_kaisha_bunrui").prop("disabled", false);

            //ケンゲンを含まない
            if (!page.flgEditAble.honnin_nomi && !page.flgEditAble.shozoku_kaisha && !page.flgEditAble.subete) {
                window.location.href = page.urls.mainMenu;
            }

            //本人のみ(氏名、パスワード)
            if (page.flgEditAble.honnin_nomi) {

                var new_id_user = "0000000000" + App.ui.page.user.EmployeeCD,
                    length = new_id_user.length;

                page.header.element.findP("id_user").val(new_id_user.substr(length - 10, length));

                $(".honnin_nomi").prop("disabled", page.flgEditAble.honnin_nomi);
                $(".shozoku_kaisha").prop("disabled", page.flgEditAble.honnin_nomi);
            }
            //所属会社 
            if (page.flgEditAble.shozoku_kaisha) {

                $(".shozoku_kaisha").prop("disabled", page.flgEditAble.shozoku_kaisha);
            }

            //選択された権限名に、権限分類が1,4,5の権限を選択した場合は所属部と署所属会社を非活性。
            var check = page.kengenBunrui(element.findP("cd_kengen").val());
            if ((check == App.settings.app.kbn_kengen_bunrui.shisaquick || check == App.settings.app.kbn_kengen_bunrui.shisaquick_seihoshien_kaihatsu || check == App.settings.app.kbn_kengen_bunrui.shisaquick_seihoshien_kojo)) {
                element.find(".busho_kaisha_bunrui").prop("disabled", true);
            }

            if (page.detailInput.options.mode !== page.detailInput.mode.input) {
                element.find(".input-mode-edit").prop("readonly", true).prop("disabled", true);
            }
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
            $("#clearDetail").on("click", page.detail.clearResult);
            $("#clearInput").on("click", page.detailInput.clearInput);
            $("#clearEdit").on("click", page.detailInput.clearEdit);

        };

        /**
        * 明細の初期化処理を行います。
        */
        page.detailInput.clearEdit = function () {
            //remove all messenger
            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            var element = page.detailInput.element;

            element.find(".control-required").removeClass("control-required").addClass("control-success");
            element.find(".control-required-label").removeClass("control-required-label").addClass("control-success-label");

            element.find("input[type='checkbox']").prop("checked", false);
            element.find("select").val("");
            element.find("input[type='text']").val("");
            element.find(":input").val("");

            //reload data combobox
            page.detailInput.reLoadDataCombo(page.detailInput.dataUserOriginal, ".detailInput").then(function () {
                page.detailInput.bind(page.detailInput.dataUserOriginal);
                page.detailInput.bindTantoKaisha(page.detailInput.dataTantoOriginal, false);
            }).always(function () {
                $("#editItem").prop("disabled", true);
            })
        }


        /**
        * 明細の初期化処理を行います。
        */
        page.detailInput.clearInput = function () {
            //remove all messenger
            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            var element = page.detailInput.element;

            element.find(".control-required").removeClass("control-required").addClass("control-success");
            element.find(".control-required-label").removeClass("control-required-label").addClass("control-success-label");


            element.find("input[type='checkbox']").prop("checked", false);
            element.find("select").val("");
            element.find("input[type='text']").val("");
            element.find(":input").val("");

            //reload data combobox
            page.loadMasterData(".detailInput").then(function () {


                page.detailInput.bind(page.detailInput.dataUserOriginal);
                page.detailInput.bindTantoKaisha([], false);

                $.each(page.detailInput.element.find(":input"), function () {
                    page.setColValidStyle(this);
                    App.ui.page.notifyAlert.remove(this);
                })
            }).always(function () {
                $("#addItem").prop("disabled", true);
            })
        }

        /**
        * 画面の初期化処理を行います。
        */
        page.detail.clearResult = function () {
            //remove all messenger
            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            var element = page.header.element,
                elemtntDetail = page.detail.element;

            //reset header
            element.find("[data-prop]").val("").text("");
            element.find(".control-required").removeClass("control-required").addClass("control-success");
            element.find(".control-required-label").removeClass("control-required-label").addClass("control-success-label");
            //reload data combobox
            page.loadMasterData(".header, .detailInput");

            //reload table detail
            page.detail.dataTable.dataTable("clear");
            elemtntDetail.find("#nextsearch").removeClass("show-search").hide();

            //reload count row
            elemtntDetail.findP("data_count").text("");
            elemtntDetail.findP("data_count_total").text("");

            if (page.flgEditAble.honnin_nomi) {
                var new_id_user = "0000000000" + App.ui.page.user.EmployeeCD,
                    length = new_id_user.length;

                page.header.element.findP("id_user").val(new_id_user.substr(length - 10, length)).prop("disabled", page.flgEditAble.honnin_nomi);
            }
        }

        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function (area) {
            var cd_kaisha = "";
            //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
            return $.ajax(App.ajax.odata.get(page.urls.kaisha)).then(function (result) {

                var kaisha = $(area).findP("cd_kaisha");
                kaisha.children().remove();
                App.ui.appendOptions(
                    kaisha,
                    "cd_kaisha",
                    "nm_kaisha",
                    result.value,
                    true
                );

                if (!page.flgEditAble.subete) {
                    kaisha.val(App.ui.page.user.cd_kaisha);
                }
                cd_kaisha = kaisha.val();
                //所属グループ
                return page.loadMasterDataGroup(cd_kaisha, null, area);
            }).then(function (result) {

                //所属部署
                return page.loadMasterDataBusho(cd_kaisha, null, area);
            }).then(function (result) {
                var cd_group = page.header.element.findP("cd_group").val();
                if (area == ".detailInput") {
                    var cd_group = page.detailInput.element.findP("cd_group").val();
                }
                //所属チーム
                return page.loadMasterDataTeam(cd_group, null, area);
            }).then(function () {
                return $.ajax(App.ajax.odata.get(App.str.format(page.urls.literal, App.settings.app.cd_category.yakushoku)));
            }).then(function (result) {
                var yakushoku = $(".detailInput").findP("cd_yakushoku");
                yakushoku.children().remove();
                App.ui.appendOptions(
                    yakushoku,
                    "cd_literal",
                    "nm_literal",
                    result.value,
                    true
                );

                return $.ajax(App.ajax.odata.get(page.urls.kengen));
            }).then(function (result) {
                page.kengenBunruiData = result.value;

                var kengen = $(".detailInput").findP("cd_kengen");
                kengen.children().remove();
                App.ui.appendOptions(
                    kengen,
                    "cd_kengen",
                    "nm_kengen",
                    result.value,
                    true
                );

            })
        };

        /**
        * 権限分類。
        */
        page.kengenBunrui = function (cd_kengen) {
            var bunrui = jQuery.grep(page.kengenBunruiData, function (n, i) {
                return (n.cd_kengen == cd_kengen);
            }),

            kbn_kengen_bunrui = "";

            if (bunrui.length) {
                kbn_kengen_bunrui = bunrui[0].kbn_kengen_bunrui;
            }
            return kbn_kengen_bunrui;
        }

        /**
        * 所属グループデータのロード処理を実行します。
        */
        page.loadMasterDataGroup = function (cd_kaisha, cd_group, area) {
            var group = $(area).findP("cd_group");
            group.children().remove();

            if (cd_kaisha == "" || App.isUndefOrNull(cd_kaisha)) {
                return App.async.success();
            }

            return $.ajax(App.ajax.odata.get(App.str.format(page.urls.group, cd_kaisha))).then(function (result) {
                App.ui.appendOptions(
                    group,
                    "cd_group",
                    "nm_group",
                    result.value,
                    true
                );

                group.val(cd_group);
            })
        }

        /**
        * 所属部署データのロード処理を実行します。
        */
        page.loadMasterDataBusho = function (cd_kaisha, cd_busho, area) {
            var busho = $(area).findP("cd_busho");
            busho.children().remove();

            if (cd_kaisha == "" || App.isUndefOrNull(cd_kaisha)) {
                return App.async.success();
            }

            return $.ajax(App.ajax.webapi.get(page.urls.busho, { cd_kaisha: cd_kaisha, kbn_kengen_bunrui: App.ui.page.user.kbn_kengen_bunrui })).done(function (result) {

                App.ui.appendOptions(
                    busho,
                    "cd_busho",
                    "nm_busho",
                    result,
                    true
                );

                busho.val(cd_busho);
            })
        }

        /**
        * 所属チームデータのロード処理を実行します。
        */
        page.loadMasterDataTeam = function (cd_group, cd_team, area) {

            var team = $(area).findP("cd_team");
            team.children().remove();

            if (cd_group == "" || App.isUndefOrNull(cd_group)) {
                return App.async.success();
            }
            return $.ajax(App.ajax.odata.get(App.str.format(page.urls.team, cd_group))).then(function (result) {
                App.ui.appendOptions(
                    team,
                    "cd_team",
                    "nm_team",
                    result.value,
                    true
                );

                team.val(cd_team);
            })
        }


        /**
         * 共有ダイアログのロード処理を実行します。
         */
        page.loadDialogs = function () {

            //TODO: 共有ダイアログデータを取得し、画面にバインドする処理を記述します。
            return App.async.all({

                confirmDialog: $.get(page.urls.confirmDialog),
                kaishaSearchDialog: $.get(page.urls.kaishaSearchDialog)
                //searchDialog: $.get(/* TODO:共有ダイアログの URL */),
            }).then(function (result) {

                $("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;

                $("#dialog-container").append(result.successes.kaishaSearchDialog);
                page.dialogs.kaishaSearchDialog = _500_SeizoTantoKaishaKensaku_Dialog;
                page.dialogs.kaishaSearchDialog.initialize();
                page.dialogs.kaishaSearchDialog.dataSelected = page.detailInput.addTantoKaisha;
            });
        };
        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.validations = {
            //TODO: 画面ヘッダーのバリデーションの定義を記述します。
            id_user: {
                rules: {
                    maxlength: 10,
                    digits: true
                },
                options: {
                    name: "ユーザID"
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits
                }
            },
            //nm_user: {
            //    rules: {
            //        maxlength: 60
            //    },
            //    options: {
            //        name: "担当者名"
            //    },
            //    messages: {
            //        maxlength: App.messages.base.maxlength
            //    }
            //},
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

        };

        /**
         * 画面ヘッダーの変更時処理を定義します。
         */
        page.header.change = function (e) {
            var element = page.header.element,
                target = $(e.target),
                property = target.attr("data-prop"),
                data = element.form().data();

            page.header.validator.validate({
                targets: target
            }).then(function () {
                if (property == "cd_kaisha") {
                    page.loadMasterDataGroup(data.cd_kaisha, null, ".header");
                    page.loadMasterDataBusho(data.cd_kaisha, null, ".header");
                    page.loadMasterDataTeam(null, null, ".header");
                }

                if (property == "cd_group") {
                    page.loadMasterDataTeam(data.cd_group, null, ".header");
                }

                if (property == "id_user" && data.id_user) {
                    var new_id_user = "0000000000" + data.id_user,
                        length = new_id_user.length;

                    target.val(new_id_user.substr(length - 10, length));
                }

                //App.ui.page.notifyInfo.message(App.messages.base.MS0010).remove();
                if ($("#nextsearch").hasClass("show-search")) {
                    $("#nextsearch").removeClass("show-search").hide();
                    App.ui.page.notifyInfo.message(App.messages.base.MS0010, $("#nextsearch")).show();
                } else {
                    App.ui.page.notifyAlert.remove($("#nextsearch"));
                }
            })
        };

        /**
         * 検索処理を定義します。
         */
        page.header.search = function (isLoading) {

            var deferred = $.Deferred(),
                query;

            page.header.validator.validate().done(function () {

                page.options.skip = 0;
                var data = page.header.element.form().data();

                page.options.filter = {
                    id_user: data.id_user,
                    cd_kaisha: data.cd_kaisha,
                    cd_group: data.cd_group,
                    nm_user: data.nm_user,
                    cd_busho: data.cd_busho,
                    cd_team: data.cd_team,
                    cd_category: App.settings.app.cd_category.yakushoku,
                    skip: page.options.skip,     // TODO:先頭からスキップするデータ数を指定します。
                    top: page.options.top       // TODO:取得するデータ数を指定します。
                };

                if (isLoading) {
                    App.ui.loading.show();
                    App.ui.page.notifyAlert.clear();
                }

                App.ui.page.notifyWarn.clear();

                return $.ajax(App.ajax.webapi.get(page.header.urls.search, page.options.filter)).then(function (result) {
                    page.detail.bind(result);
                    //if (result.Items.length) {
                    //    var tanto = jQuery.grep(result.Items, function (n, i) {
                    //        return ((data.id_user != null && n.id_user == Number(data.id_user))
                    //            && n.flg_eigyo == App.settings.app.kbn_eigyo.eigyo_nashi);
                    //    });

                    //    if (tanto.length) {
                    //        App.ui.page.notifyWarn.message(App.messages.app.AP0121).show();
                    //    }
                    //}
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
         * 検索条件のフィルターを定義します。
         */
        page.header.createFilter = function () {
            var criteria = page.header.element.form().data(),
                filters = [];

            /* TODO: 検索条件のフィルターを定義してください。*/
            //if (!App.isUndefOrNull(criteria.cd_shiharai) && criteria.cd_shiharai > 0) {
            //    filters.push("cd_shiharai eq " + criteria.cd_shiharai);
            //}
            //if (!App.isUndefOrNull(criteria.cd_torihiki) && criteria.cd_torihiki.length > 0) {
            //    filters.push("cd_torihiki eq " + criteria.cd_torihiki);
            //}
            //if (!App.isUndefOrNull(criteria.nm_hinmei) && criteria.nm_hinmei.length > 0) {
            //    filters.push("substringof('" + encodeURIComponent(criteria.nm_hinmei) + "', nm_hinmei) eq true");
            //}

            return filters.join(" and ");
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
                    height: 100,
                    resize: true,
                    //列固定横スクロールにする場合は、下記3行をコメント解除
                    fixedColumn: true,                //列固定の指定
                    fixedColumns: 3,                  //固定位置を指定（左端を0としてカウント）
                    innerWidth: 1320,                 //可動列の合計幅を指定
                    onselect: page.detail.select,
                    resizeOffset: 70
                });
            table = element.find(".datatable");         //列固定にした場合DOM要素が再作成されるため、変数を再取得

            page.detail.element = element;
            page.detail.dataTable = datatable;

            element.on("click", "#nextsearch", page.detail.nextsearch);
            element.on("click", "#add-item", page.detail.addNewItem);

            // 行選択時に利用するテーブルインデックスを指定します
            page.detail.fixedColumnIndex = element.find(".fix-columns").length;

            //// 明細パートオープン時の処理を指定します
            //element.find(".part").on("expanded.aw.part", function () {
            //    page.detail.isClose = false;
            //    if (page.detail.searchData) {
            //        App.ui.loading.show();
            //        setTimeout(function () {
            //            page.detail.bind(page.detail.searchData);
            //            page.detail.searchData = undefined;
            //            App.ui.loading.close();
            //        }, 5);
            //    };
            //});

            //// 明細パートクローズ時の処理を指定します
            //element.find(".part").on("collapsed.aw.part", function () {
            //    page.detail.isClose = true;
            //});

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

            //var query = {
            //    url: "TODO: 検索結果取得サービスの URL を設定してください。",
            //    filter: page.options.filter,
            //    orderby: "TODO: ソート対象の列名",
            //    skip: page.options.skip,     // TODO:先頭からスキップするデータ数を指定します。
            //    top: page.options.top,       // TODO:取得するデータ数を指定します。
            //    inlinecount: "allpages"
            //};

            page.options.filter.skip = page.options.skip;

            App.ui.loading.show();
            App.ui.page.notifyAlert.clear();

            return $.ajax(App.ajax.webapi.get(page.header.urls.search, page.options.filter))
            .done(function (result) {
                page.detail.bind(result);
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            }).always(function () {
                App.ui.loading.close();
            });

        };

        page.detailInput.options.bindOption = {
            appliers: {
                id_user: function (value, element) {
                    value = "0000000000" + value;
                    var l = value.length;

                    element.val(value.substr(l - 10, l));
                    return true;
                }
            }
        };

        /**
         * 画面明細の各行にデータを設定する際のオプションを定義します。
         */
        page.detail.options.bindOption = {
            appliers: {
                id_user: function (value, element) {
                    value = "0000000000" + value;
                    var l = value.length;

                    element.text(value.substr(l - 10, l));
                    return true;
                }
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
                row.addClass("data_key_row_" + item.id_user + "_" + item.cd_kaisha);
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
                App.ui.page.notifyInfo.clear();
                App.ui.page.notifyInfo.message(App.messages.base.MS0011).show();
                $("#nextsearch").hide();
            }

            //if (!dataCount) {
            //    App.ui.page.notifyInfo.clear();
            //    App.ui.page.notifyInfo.message(App.messages.app.AP0007).show();
            //}

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

            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            page.detail.dataTable.dataTable("getFirstViewRow", function (row) {
                page.detail.firstViewRow = row;
            });

            page.detailInput.options.mode = page.detailInput.mode.input;

            page.detailInput.beforShow(page.detailInput.createNewItem(null));
        };

        page.detailInput.createNewItem = function (id_user) {
            //TODO:新規データおよび初期値を設定する処理を記述します。
            var newData = {
                cd_kaisha: App.ui.page.user.cd_kaisha,
                flg_kaishakan_sansyo: false,
                flg_kojyokan_sansyo: false,
                id_user: id_user
            };

            if (page.flgEditAble.subete) {
                newData.cd_kaisha = null;
            }

            switch (App.ui.page.user.kbn_kengen_bunrui) {

                case App.settings.app.kbn_kengen_bunrui.seihoshien_kaihatsu:
                case App.settings.app.kbn_kengen_bunrui.seihoshien_kojo:

                    newData.flg_kojyokan_sansyo = true;
                    newData.cd_group = null;
                    newData.cd_team = null;
                    newData.cd_yakushoku = null;
                    break;

                case App.settings.app.kbn_kengen_bunrui.shisaquick_seihoshien_kaihatsu:
                    newData.flg_kaishakan_sansyo = true;
                    newData.flg_kojyokan_sansyo = true;
                    break;
            }

            return newData;
        };

        /**
         * 画面明細の一覧から編集画面を表示します。
         */
        page.detail.showEditPart = function (e) {

            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            var element = page.detail.element,
                target = $(e.target),
                row = target.closest("tbody"),
                id = row.attr("data-key"),
                entity = page.detail.data.entry(id);

            App.ui.loading.show();

            $.ajax(App.ajax.webapi.get(page.header.urls.tantoKaisha, { id_user: entity.id_user })).done(function (result) {

                page.detail.dataTable.dataTable("getFirstViewRow", function (row) {
                    page.detail.firstViewRow = row;
                });

                page.detailInput.options.mode = page.detailInput.mode.edit;

                page.detailInput.beforShow(entity, result);
                //page.detailInput.show(entity, result);

            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            }).always(function () {
                App.ui.loading.close();
            });
        };

        /**
         * 編集画面で更新された画面明細の行を最新化します。
         */
        page.detail.updateData = function (changeSets, result) {
            var newData = result,
                element = page.detail.element;

            if (!newData) {
                return;
            }

            var selectedRow = element.find(".selected").closest("tbody"),
                id = selectedRow.attr("data-key");

            if (App.isUndefOrNull(id)) {
                return;
            }

            var entity = page.detail.data.entry(id);

            // 対象データの各値を最新の値で更新します。
            Object.keys(newData).forEach(function (key) {
                if (key in entity) {
                    entity[key] = newData[key];
                }
            });

            page.detail.dataTable.dataTable("getRow", element.find(selectedRow), function (row) {
                selectedRow = row.element;
            });

            selectedRow.find("[data-prop]").val("").text("");

            selectedRow.form(page.detail.options.bindOption).bind(entity);
            selectedRow.findP("flg_kaishakan_sansyo").prop('checked', entity.flg_kaishakan_sansyo);
            selectedRow.findP("flg_kojyokan_sansyo").prop('checked', entity.flg_kojyokan_sansyo);

            //App.ui.page.notifyWarn.message(App.messages.app.AP0121).remove();
            //if (page.detail.data) {
            //    var tanto = page.detail.data.findAll(function (item, entity) {
            //        return entity.state !== App.ui.page.dataSet.status.Deleted;
            //    }), id_user = page.header.element.findP("id_user").val();

            //    var tanto = jQuery.grep(tanto, function (n, i) {
            //        return ((id_user != null && n.id_user == Number(id_user))
            //            && n.flg_eigyo == App.settings.app.kbn_eigyo.eigyo_nashi);
            //    });

            //    if (tanto.length) {
            //        App.ui.page.notifyWarn.message(App.messages.app.AP0121).show();
            //    }
            //}

            //App.ui.page.notifyWarn.message(App.messages.app.AP0121).remove();
            //if (page.detail.data) {
            //    var tanto = page.detail.data.findAll(function (item, entity) {
            //        return entity.state !== App.ui.page.dataSet.status.Deleted;
            //    }), id_user = page.header.element.findP("id_user").val();

            //    var tanto = jQuery.grep(tanto, function (n, i) {
            //        return ((id_user != null && n.id_user == Number(id_user))
            //            && n.flg_eigyo == App.settings.app.kbn_eigyo.eigyo_nashi);
            //    });

            //    if (tanto.length) {
            //        App.ui.page.notifyWarn.message(App.messages.app.AP0121).show();
            //    }
            //}

            //TODO: 更新されたデータのキーを取得します。
            //var updatedKey = result.updated[0].no_seq;

            // キーをもとに最新のデータを取得します。(検索結果取得サービスのurl, 更新行を取得する条件式)
            //return $.ajax(App.ajax.webapi.get(/*TODO: 検索結果取得サービスの URL, */ /*TODO: 条件式 */"$filter=no_seq eq " + updatedKey)).then(function (result) {       

            //page.detail.dataTable.dataTable("each", function (row, index) {
            //    var entity = page.detail.data.entry(row.element.attr("data-key"));
            //    if (entity/*TODO: キー項目値が一致する条件を設定 //.no_seq*/ == updatedKey) {
            //        // 対象データの各値を最新の値で更新します。
            //        Object.keys(newData).forEach(function (key) {
            //            if (key in entity) {
            //                entity[key] = newData[key];
            //            }
            //        });
            //        // 行にデータを再バインドします。
            //        row.element.find("[data-prop]").val("").text("");
            //        row.element.form(page.detail.options.bindOption).bind(entity);
            //        return true;    //datatable.eachのループを抜けるためのreturn true;
            //    }
            //});
            //});
        };

        /**
         * 編集画面で削除された画面明細の行を削除します。
         */
        page.detail.removeData = function (result) {

            //TODO: 削除されたデータのキーを取得します。
            var removedKey = result.deleted[0];

            // キーをもとに削除行を特定します。
            //var deletedRow;
            //page.detail.dataTable.dataTable("each", function (row, index) {
            //    var entity = page.detail.data.entry(row.element.attr("data-key"));
            //    if (entity/*TODO: キー項目値が一致する条件を設定 //.no_seq*/ == removedKey) {
            //        deletedRow = row.element;
            //        return true;    //datatable.eachのループを抜けるためのreturn true;
            //    }
            //});

            var element = page.detail.element,
                selectedRow = element.find(".data_key_row_" + removedKey.id_user + "_" + removedKey.cd_kaisha);

            //id = selectedRow.attr("data-key");

            if (!selectedRow.length) {
                return;
            }

            page.detail.dataTable.dataTable("getRow", element.find(selectedRow), function (row) {
                selectedRow = row.element;
            });

            page.detail.dataTable.dataTable("deleteRow", selectedRow, function (row) {
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
                    newSelected.find(".select-tab").removeClass("unselected").addClass("selected");
                }
            });

            element.findP("data_count").text(parseFloat(element.findP("data_count").text()) - 1);
            element.findP("data_count_total").text(parseFloat(element.findP("data_count_total").text()) - 1);

        };

        //TODO: 以下の page.detail の各宣言は画面仕様ごとにことなるため、不要の場合は削除してください。


        //TODO-END: 

        /**
         * 編集画面のバリデーションを定義します。
         */
        page.detailInput.options.validations = {
            //TODO: 新規追加画面のバリデーションの定義を記述します。
            //TODO: 画面ヘッダーのバリデーションの定義を記述します。
            id_user: {
                rules: {
                    required: true,
                    digits: true,
                    maxlength: 10
                },
                options: {
                    name: "社員番号"
                },
                messages: {
                    required: App.messages.base.required,
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength
                }
            },
            password: {
                rules: {
                    required: true,
                    minlength: 6,
                    check_type: function (value, param, otps, done) {

                        var regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;
                        return done(regex.test(value));
                    },
                    maxlength: 50
                },
                options: {
                    name: "パスワード"
                },
                messages: {
                    required: App.messages.base.required,
                    minlength: App.messages.base.minlength,
                    check_type: App.messages.app.AP0003,
                    maxlength: App.messages.base.maxlength
                }
            },
            cd_kengen: {
                rules: {
                    required: true
                },
                options: {
                    name: "権限"
                },
                messages: {
                    required: App.messages.base.required
                }
            },
            nm_user: {
                rules: {
                    required: true,
                    maxlength: 60
                },
                options: {
                    name: "氏名"
                },
                messages: {
                    required: App.messages.base.required,
                    maxlength: App.messages.base.maxlength
                }
            },
            cd_kaisha: {
                rules: {
                    required: true
                },
                options: {
                    name: "所属会社"
                },
                messages: {
                    required: App.messages.base.required
                }
            },
            cd_busho: {
                rules: {
                    required: true
                },
                options: {
                    name: "所属部署"
                },
                messages: {
                    required: App.messages.base.required
                }
            },
            cd_group: {
                rules: {
                    required: true
                },
                options: {
                    name: "所属グループ"
                },
                messages: {
                    required: App.messages.base.required
                }
            },
            cd_team: {
                rules: {
                    required: true
                },
                options: {
                    name: "所属チーム"
                },
                messages: {
                    required: App.messages.base.required
                }
            },
            cd_yakushoku: {
                rules: {
                    required: true
                },
                options: {
                    name: "役職"
                },
                messages: {
                    required: App.messages.base.required
                }
            }
        };


        /**
        * 編集画面のバリデーションを定義します。
        */
        page.detailInput.options.validationsShisa = {

            cd_group: {
                rules: {
                    required: false
                },
                options: {
                    name: "所属グループ"
                },
                messages: {
                    required: App.messages.base.required
                }
            },
            cd_team: {
                rules: {
                    required: false
                },
                options: {
                    name: "所属チーム"
                },
                messages: {
                    required: App.messages.base.required
                }
            },
            cd_yakushoku: {
                rules: {
                    required: false
                },
                options: {
                    name: "役職"
                },
                messages: {
                    required: App.messages.base.required
                }
            }
        };

        /**
         * 編集画面の初期化処理を行います。
         */
        page.detailInput.initialize = function () {

            var element = $(".detailInput"),
                validations = page.detailInput.options.validations;

            //：製法作成支援(開発)
            //：製法作成支援(工場)
            if (App.ui.page.user.kbn_kengen_bunrui == App.settings.app.kbn_kengen_bunrui.seihoshien_kaihatsu || App.ui.page.user.kbn_kengen_bunrui == App.settings.app.kbn_kengen_bunrui.seihoshien_kojo) {
                validations = $.extend(true, {}, validations, page.detailInput.options.validationsShisa);
            }

            page.detailInput.element = element;

            page.detailInput.validator = element.validation(page.createValidator(validations));
            element.on("change", ":input", page.detailInput.change);
            element.on("click", ".previous", function () { page.detailInput.previous(true, true); });
            element.on("click", ".close-input", page.detailInput.delTantoKaisha);
            element.on("click", ".kaisaselect", page.detailInput.showKaishaDialog);
        };

        page.detailInput.showKaishaDialog = function () {

            page.dialogs.kaishaSearchDialog.element.modal("show");
        };
        /**
        * 新しいデータをコンボボックスにリロードする.
        */
        page.detailInput.reLoadDataCombo = function (entity, area) {
            App.ui.loading.show();
            //所属グループ
            return page.loadMasterDataGroup(entity.cd_kaisha, null, area).then(function () {

                //所属部署
                return page.loadMasterDataBusho(entity.cd_kaisha, null, area);
            }).then(function () {

                //所属チーム
                return page.loadMasterDataTeam(entity.cd_group, null, area);
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

            }).always(function (result) {
                App.ui.loading.close();
            });
        };

        /**
        * 新しいデータをコンボボックスにリロードする.
        */
        page.detailInput.beforShow = function (entity, result) {
            page.detailInput.reLoadDataCombo(entity, ".detailInput").then(function () {
                page.detailInput.show(entity, result);
            })
        };

        /**
         * 編集画面の表示処理を行います。
         */
        page.detailInput.show = function (data, result) {

            page.detailInput.values.isChange = false;

            var element = page.detailInput.element;

            //TODO:項目をクリアする処理をここに記述します。
            element.find(":input:not([type='checkbox']):not([type='radio'])").val("");
            element.find("input[type='checkbox']").prop('checked', false);

            //TODO:radioボタンの初期表示は、画面要件に合わせて記述します。

            //TODO:入力項目以外の個別項目のクリアはここで記述

            //TODO:データバインド処理をここに記述します。
            page.detailInput.dataUserOriginal = $.extend(true, {}, data);
            page.detailInput.dataDelOriginal = $.extend(true, {}, data);

            page.detailInput.bind(data);

            //TODO:画面モードによる処理をここに記述します。
            if (page.detailInput.options.mode === page.detailInput.mode.input) {

                page.detailInput.bindTantoKaisha([], false);

                $("#addItem, #clearInput").show();
                $("#addItem").prop("disabled", true);
                $("#editItem, #deleteItem, #clearDetail, #clearEdit").hide();
                element.find(".input-mode-edit").prop("readonly", false).prop("disabled", false);
                //TODO:キー項目が画面入力の場合、新規モード時は修正可とします。
                //element.findP("no_seq").prop("readonly", false).prop("tabindex", false);
                //element.validation().validate({
                //    state: {
                //        suppressMessage: true
                //    }
                //});

                page.detailInput.controlDisplay(data.cd_kengen, data);
            } else {

                page.detailInput.dataTantoOriginal = $.extend(true, [], result);

                page.detailInput.bindTantoKaisha(result, false);

                $("#editItem, #deleteItem, #clearEdit").show();
                $("#editItem").prop("disabled", true);
                $("#addItem, #clearDetail, #clearInput").hide();
                //TODO:キー項目が画面入力の場合、編集モード時は修正不可とします。
                //element.findP("no_seq").prop("readonly", true).prop("tabindex", -1);
                page.detailInput.values.password_org = data.password;

                page.detailInput.controlDisplay(data.cd_kengen);

                setTimeout(function () {
                    element.findP("flg_kaishakan_sansyo").prop("checked", data.flg_kaishakan_sansyo);
                    element.findP("flg_kojyokan_sansyo").prop("checked", data.flg_kojyokan_sansyo);
                })

                element.find(".input-mode-edit").prop("readonly", true).prop("disabled", true);
                //if (data.flg_eigyo == App.settings.app.kbn_eigyo.eigyo_nashi) {
                //    App.ui.page.notifyWarn.message(App.messages.app.AP0121).show();
                //}
            }

            //TODO:パーツの表示・非表示処理を記述します。
            page.header.element.hide();
            page.detail.element.hide();
            element.show();
            element.find(":input:not([readonly]):first").focus();
        };

        /**
        * 管理 所属グループ,所属チーム,役職,担当製造会社,会社間参照,工場間参照 ブタン
        */
        page.detailInput.controlDisplay = function (cd_kengen, entity) {
            App.ui.loading.show();

            setTimeout(function () {
                var kbn_kengen_bunrui = page.kengenBunrui(cd_kengen),
                    element = page.detailInput.element,
                    validations = page.detailInput.options.validations;

                element.find(".shisa_seiho").prop("disabled", false);
                element.find(".seihoshien").show();

                switch (kbn_kengen_bunrui) {

                    case App.settings.app.kbn_kengen_bunrui.shisaquick://1：シサクイック

                        element.find(".shisaquick").prop("disabled", true);

                        if (entity) {
                            entity["flg_kaishakan_sansyo"] = false;
                            entity["flg_kojyokan_sansyo"] = false;

                            element.findP("flg_kaishakan_sansyo").prop("checked", entity["flg_kaishakan_sansyo"]);
                            element.findP("flg_kojyokan_sansyo").prop("checked", entity["flg_kojyokan_sansyo"]);
                        }

                        break;

                    case App.settings.app.kbn_kengen_bunrui.seihoshien_kaihatsu://2：製法作成支援(開発)
                        if (entity) {
                            page.detailInput.deleteTantoKaisha();
                        }

                        element.find(".seihoshien_kaihatsu").prop("disabled", true);
                        element.find(".seihoshien").hide();
                        validations = $.extend(true, {}, validations, page.detailInput.options.validationsShisa);

                        if (entity) {
                            entity["flg_kaishakan_sansyo"] = false;
                            entity["flg_kojyokan_sansyo"] = true;
                            entity["cd_group"] = null;
                            entity["cd_team"] = null;
                            entity["cd_yakushoku"] = null;

                            element.findP("flg_kaishakan_sansyo").prop("checked", entity["flg_kaishakan_sansyo"]);
                            element.findP("flg_kojyokan_sansyo").prop("checked", entity["flg_kojyokan_sansyo"]);

                            element.findP("cd_group").val(entity["cd_group"]);
                            element.findP("cd_team").val(entity["cd_team"]);
                            element.findP("cd_yakushoku").val(entity["cd_yakushoku"]);
                        }

                        break;

                    case App.settings.app.kbn_kengen_bunrui.seihoshien_kojo://３：製法作成支援(工場)
                        if (entity) {
                            page.detailInput.deleteTantoKaisha();
                        }

                        element.find(".seihoshien_kojo").prop("disabled", true);
                        element.find(".seihoshien").hide();
                        validations = $.extend(true, {}, validations, page.detailInput.options.validationsShisa);

                        if (entity) {
                            entity["flg_kaishakan_sansyo"] = false;
                            entity["flg_kojyokan_sansyo"] = true;
                            entity["cd_group"] = null;
                            entity["cd_team"] = null;
                            entity["cd_yakushoku"] = null;

                            element.findP("flg_kaishakan_sansyo").prop("checked", entity["flg_kaishakan_sansyo"]);
                            element.findP("flg_kojyokan_sansyo").prop("checked", entity["flg_kojyokan_sansyo"]);

                            element.findP("cd_group").val(entity["cd_group"]);
                            element.findP("cd_team").val(entity["cd_team"]);
                            element.findP("cd_yakushoku").val(entity["cd_yakushoku"]);
                        }

                        break;

                    case App.settings.app.kbn_kengen_bunrui.shisaquick_seihoshien_kaihatsu://４：シサクイック＋製法作成支援(開発)

                        element.find(".shisaquick_seihoshien_kaihatsu").prop("disabled", true);

                        if (entity) {
                            entity["flg_kaishakan_sansyo"] = true;
                            entity["flg_kojyokan_sansyo"] = true;

                            element.findP("flg_kaishakan_sansyo").prop("checked", entity["flg_kaishakan_sansyo"]);
                            element.findP("flg_kojyokan_sansyo").prop("checked", entity["flg_kojyokan_sansyo"]);
                        }

                        break;

                    case App.settings.app.kbn_kengen_bunrui.shisaquick_seihoshien_kojo://５：シサクイック＋製法作成支援(工場)

                        element.find(".shisaquick_seihoshien_kojo").prop("disabled", true);

                        if (entity) {
                            entity["flg_kaishakan_sansyo"] = false;
                            entity["flg_kojyokan_sansyo"] = false;

                            element.findP("flg_kaishakan_sansyo").prop("checked", entity["flg_kaishakan_sansyo"]);
                            element.findP("flg_kojyokan_sansyo").prop("checked", entity["flg_kojyokan_sansyo"]);
                        }

                        break;
                    default:
                        if (entity) {
                            entity["flg_kaishakan_sansyo"] = false;
                            entity["flg_kojyokan_sansyo"] = false;

                            element.findP("flg_kaishakan_sansyo").prop("checked", entity["flg_kaishakan_sansyo"]);
                            element.findP("flg_kojyokan_sansyo").prop("checked", entity["flg_kojyokan_sansyo"]);
                        }
                        break;
                }

                //renew validate
                page.detailInput.validator = element.validation(page.createValidator(validations));
                if (entity) {
                    $.each(page.detailInput.element.find(".validate_kengen").find(":input"), function () {
                        page.setColValidStyle(this);
                        App.ui.page.notifyAlert.remove(this);
                    })
                }

                page.controlEditGamen();
                App.ui.loading.close();
            }, 1)
        };

        /**
         * delete all kaisha tanto
         */
        page.detailInput.deleteTantoKaisha = function () {

            var element = page.detailInput.element,
                close = element.find(".tanto_row");

            if (!close.length) {
                App.ui.loading.close();
                return;
            }

            $.each(close, function (index, item) {
                element.find(item).find(".close-input").trigger("click");
            })
        };

        /**
         * 画面明細に戻ります。
         */
        page.detailInput.previous = function (isConfirm, isCheckEigyo) {
            var closeMessage = {},
                previousPage = function () {
                    //TODO:パーツの表示・非表示処理を記述します。
                    page.header.element.show();
                    page.detail.element.show();
                    page.detailInput.element.hide();
                    page.detailInput.data = undefined;
                    // Reset isChange value
                    page.detailInput.values.isChange = false;

                    if (!App.isUndefOrNull(page.detail.firstViewRow)) {
                        page.detail.dataTable.dataTable("scrollTop", page.detail.firstViewRow, function () { });
                    }

                    $("#addItem").hide();
                    $("#editItem").prop("disabled", true).hide();
                    $("#deleteItem").hide();

                    $("#clearInput, #clearEdit").hide();
                    $("#clearDetail").show();

                    //renew data tanto kaisha
                    dataSet = App.ui.page.dataSet();
                    page.detailInput.dataTanto = dataSet;

                    App.ui.page.notifyAlert.clear();
                    //App.ui.page.notifyWarn.message(App.messages.app.AP0121).remove();

                    $.each(page.detailInput.element.find(":input"), function () {
                        page.setColValidStyle(this);
                    })

                    page.header.validator.validate();

                    //if (page.detail.data && isCheckEigyo) {
                    //    var tanto = page.detail.data.findAll(function (item, entity) {
                    //        return entity.state !== App.ui.page.dataSet.status.Deleted;
                    //    }), id_user = page.header.element.findP("id_user").val();

                    //    var tanto = jQuery.grep(tanto, function (n, i) {
                    //        return ((id_user != null && n.id_user == Number(id_user))
                    //            && n.flg_eigyo == App.settings.app.kbn_eigyo.eigyo_nashi);
                    //    });

                    //    if (tanto.length) {
                    //        App.ui.page.notifyWarn.message(App.messages.app.AP0121).show();
                    //    }
                    //}
                    $(window).resize();
                };


            //check exit change data tanto kaisha
            if (page.detailInput.dataTanto) {
                detailTanto = page.detailInput.dataTanto.getChangeSet();
                if (detailTanto.created.length || detailTanto.updated.length || detailTanto.deleted.length) {
                    closeMessage.text = App.messages.base.exit;
                }
            }

            if (page.detailInput.data.isChanged()) {
                closeMessage.text = App.messages.base.exit;
            }

            if (isConfirm && closeMessage.text && page.detailInput.values.isChange) {
                page.dialogs.confirmDialog.confirm(closeMessage).then(function () {
                    previousPage();
                });
            } else {
                previousPage();
            };
        };

        /**
        * 担当製造会社のデータバインド処理を行います
        */
        page.detailInput.bindTantoKaisha = function (result, isNewData) {
            var element = page.detailInput.element,
                l = result.length;

            element.find(".tanto_row").remove();
            element.findP("nm_kaisha").val("");

            dataSet = App.ui.page.dataSet();
            page.detailInput.dataTanto = dataSet;

            var tantoArea = element.find(".tanto-area");

            for (var i = 0 ; i < l; i++) {

                var temp = element.find(".tanto-tmpl").clone(),
                    item = $.extend(true, {}, result[i]);

                temp.appendTo(tantoArea).removeClass("tanto-tmpl").addClass("tanto_row").show();

                (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
                temp.form().bind(item);
            }
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

            page.detailInput.values.password_org = data["password"];

            page.detailInput.element.form(page.detailInput.options.bindOption).bind(setData);

        };

        /**
        * 担当製造会社のデータ子目を新規する。
        */
        page.detailInput.addTantoKaisha = function (result, isNewData) {
            App.ui.loading.show();

            //※会社を検索する
            var checkExitKaisha = function (itemCheck, dataTanto) {
                var tanto = jQuery.grep(dataTanto, function (n, i) {
                    return (n.cd_kaisha == itemCheck.cd_kaisha);
                });

                return (tanto.length == 0)
            };

            setTimeout(function () {
                var element = page.detailInput.element,
                    l = result.length,
                    id_user = element.findP("id_user").val(),
                    tantoArea = element.find(".tanto-area");

                var dataTanto = page.detailInput.dataTanto.findAll(function (item, entity) {
                    return entity.state !== App.ui.page.dataSet.status.Deleted;
                }), updated = false;

                for (var i = 0 ; i < l; i++) {
                    var item = $.extend(true, {}, result[i]);

                    //※会社名が重複しないように表示する
                    if (checkExitKaisha(item, dataTanto)) {

                        //担当製造会社                        
                        item["cd_tantokaisha"] = item.cd_kaisha;
                        //社員番号
                        item["id_user"] = id_user;

                        var temp = element.find(".tanto-tmpl").clone();

                        temp.appendTo(tantoArea).removeClass("tanto-tmpl").addClass("tanto_row").show();

                        (isNewData ? page.detailInput.dataTanto.add : page.detailInput.dataTanto.attach).bind(dataSet)(item);
                        temp.form().bind(item);
                        updated = true
                    }
                }

                //create changset user when change tantokaisha
                if (updated) {
                    element.findP("flg_kaishakan_sansyo").change();
                }

                App.ui.loading.close();
            }, 1)
        }

        /**
        * 担当製造会社のデータ子目を削除する。
        */
        page.detailInput.delTantoKaisha = function (e) {
            var element = page.detailInput.element;
            //本人のみ(氏名、パスワード)
            //2：製法作成支援(開発)
            //３：製法作成支援(工場)

            if (element.find(".add-item-tanto").is(":visible:disabled")) {
                return;
            }

            var target = $(e.target).closest("div"),
                id = target.attr("data-key");

            if ($("#editItem").is(":visible:disabled")) {
                $("#editItem").prop("disabled", false);
            }

            if (App.isUndefOrNull(id)) {
                target.remove();
                return;
            }

            entity = page.detailInput.dataTanto.entry(id);
            page.detailInput.dataTanto.remove(entity);

            target.remove();

            //create changset user when change tantokaisha
            if (!page.detailInput.data.isChanged()) {
                page.detailInput.element.findP("flg_kaishakan_sansyo").change();
            }
        }

        /**
         * 編集画面にある入力項目の変更イベントの処理を行います。
         */
        page.detailInput.change = function (e) {
            var element = page.detailInput.element,
                target = $(e.target),
                id = element.attr("data-key");

            if (App.isUndefOrNull(id)) {
                return;
            }

            var property = target.attr("data-prop"),
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

            page.detailInput.values.isChange = true;

            page.detailInput.validator.validate({
                targets: target
            }).then(function () {
                entity[property] = data[property];

                //update date change password
                if (property == "password") {
                    entity["dt_password"] = null;
                }

                //ユーザIDロストフォーカスする時
                if (property == "id_user") {

                    App.ui.loading.show();

                    var new_id_user = App.common.getFullString(entity[property], "0000000000");

                    target.val(new_id_user);
                    //ユーザー情報を検索する
                    page.detailInput.loadDataUser(new_id_user);
                }

                //所属会社ロストフォーカスする時
                if (property == "cd_kaisha") {

                    App.ui.loading.show();

                    page.loadMasterDataGroup(entity.cd_kaisha, null, ".detailInput");
                    page.loadMasterDataBusho(entity.cd_kaisha, null, ".detailInput");
                    page.loadMasterDataTeam(null, null, ".detailInput");

                    App.ui.loading.close();
                }

                if (property == "cd_group") {
                    page.loadMasterDataTeam(entity.cd_group, null, ".detailInput");
                }

                if (property == "cd_kengen") {
                    page.detailInput.controlDisplay(data[property], entity);
                }

                if (property == "flg_kaishakan_sansyo" || property == "flg_kojyokan_sansyo") {
                    entity[property] = target[0].checked;
                }

                page.detailInput.data.update(entity);

                if (page.detailInput.options.mode === page.detailInput.mode.input) {
                    if ($("#addItem").is(":visible:disabled")) {
                        $("#addItem").prop("disabled", false);
                    }
                } else {
                    if ($("#editItem").is(":visible:disabled")) {
                        $("#editItem").prop("disabled", false);
                    }
                }

            }).always(function () {
                delete page.values.isChangeRunning[property];
                if (page.detailInput.options.mode === page.detailInput.mode.input) {
                    $("#addItem").prop("disabled", false);
                    return;
                }
                $("#editItem").prop("disabled", false);
            });
        };

        /**
        * ユーザIDロストフォーカス。
        * ユーザー情報を検索する
        */
        page.detailInput.loadDataUser = function (id_user) {

            var accessData = function (item, isCheckBunruiKengen) {
                //会社間参照
                item["flg_kaishakan_sansyo"] = item["flg_kaishakan_sansyo"] != undefined ? item["flg_kaishakan_sansyo"] : false;
                //工場間参照
                item["flg_kojyokan_sansyo"] = item["flg_kojyokan_sansyo"] != undefined ? item["flg_kojyokan_sansyo"] : false;

                page.detailInput.reLoadDataCombo(item, ".detailInput").then(function () {

                    page.detailInput.element.find(":input").val("");

                    page.detailInput.bind(item);

                    page.detailInput.dataDelOriginal

                    page.detailInput.element.findP("flg_kaishakan_sansyo").prop('checked', item.flg_kaishakan_sansyo);
                    page.detailInput.element.findP("flg_kojyokan_sansyo").prop('checked', item.flg_kojyokan_sansyo);

                    page.detailInput.bindTantoKaisha([], false);
                    page.detailInput.controlDisplay(item.cd_kengen, isCheckBunruiKengen ? item : isCheckBunruiKengen);

                    $.each(page.detailInput.element.find(":input"), function () {
                        page.setColValidStyle(this);
                        App.ui.page.notifyAlert.remove(this);
                    })

                }).fail(function (error) {
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                }).always(function () {

                    App.ui.loading.close();
                })
            };

            var filter = Number(id_user) + "M";
            App.ui.page.notifyAlert.clear();
            //ユーザーマスタからユーザーIDを検索する。
            //抽出条件：ユーザーM．ユーザID＝画面．ユーザID
            return $.ajax(App.ajax.odata.get(App.str.format(page.detailInput.urls.ma_user_togo, { id_user: filter }))).then(function (results) {

                //・データがない場合
                if (!results.value.length) {

                    //※ログインユーザの権限にid_data値は９（すべて）ではない場合、下記の条件を追加する。新ユーザーマスタ．会社コード＝ログインユーザ．会社コード
                    if (!page.flgEditAble.subete) {
                        filter = filter + " and cd_kaisha eq " + App.ui.page.user.cd_kaisha;
                    }

                    //新ユーザーマスタにユーザー情報を検索する
                    //ユーザーM．ユーザID＝画面．ユーザID
                    return $.ajax(App.ajax.odata.get(App.str.format(page.detailInput.urls.ma_user_new, { id_user: filter }))).then(function (results) {
                        //上記の処理で取得したデータを画面に設定します。
                        if (results.value.length) {
                            var itemUserNew = results.value[0];

                            //2019/11/20 : START : Request #15896
                            //010担当者マスタの営業の判断を＜011担当者マスタ(営業)と同じように＞
                            //「権限マスタの営業区分」で行う
                            //●「権限マスタの営業区分」がNULLのユーザを対象とする
                            var kengen_eigyo = jQuery.grep(page.kengenBunruiData, function (n, i) {
                                return (n.cd_kengen == itemUserNew.cd_kengen);
                            });

                            //営業担当者チェックする。
                            //部署マスタ．flg_eigyo ＝１			
                            //部署マスタ．会社コード＝会社コード			
                            //部署マスタ．部署コード＝部署コード			
                            //return page.detailInput.loadBusho({ cd_kaisha: itemUserNew.cd_kaisha, cd_busho: itemUserNew.cd_busho }).then(function (results) {
                            //if (kengen_eigyo.length && kengen_eigyo[0].kbn_eigyo != null) {
                                //営業権限を持っている場合、	
                                //メッセージダイアログを表示します。「営業担当者は、担当者マスタ営業からご確認ください。(AP0121)」
                            //    page.infoDialog.confirm({
                            //        text: App.messages.app.AP0121,
                            //        backdrop: "static",
                            //        hideCancel: true,
                            //        keyboard: false
                            //    }).always(function () {
                            //        accessData(page.detailInput.createNewItem(null), true);

                            //        page.detailInput.values.isChange = false;

                            //    });
                           // } else {
                                //新ユーザーマスタで検索した新ユーザーマスタ情報を画面に表示する。
                                accessData(itemUserNew);

                                page.detailInput.values.isChange = false;

                            //}
                            //})
                            //2019/11/20 : END : Request #15896
                        } else {
                            accessData(page.detailInput.createNewItem(id_user), true);
                        }
                    }).fail(function (error) {
                        App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                    }).always(function () {
                        App.ui.loading.close();
                    })
                } else {
                    //2019/11/20 : START : Request #15829
                    //商品開発サポートでは、新規登録時、IDを入力してロストフォーカスすると
                    //エラーメッセージが表示されるため、新たに別会社での登録ができません。
                    //登録ができるように、ロストフォーカス時に行っているチェック処理を
                    //保存ボタン押下時にしていただけないでしょうか。

                    //App.ui.loading.close();

                    //page.infoDialog.confirm({
                    //    text: App.str.format(App.messages.app.AP0157, "社員", "担当者マスタ又は、担当者マスタ（営業）"),
                    //    backdrop: "static",
                    //    hideCancel: true,
                    //    keyboard: false
                    //}).always(function () {

                    accessData(page.detailInput.createNewItem(id_user), true);
                    page.detailInput.values.isChange = false;
                    //});
                    //2019/11/20 : END : Request #15829
                }
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                App.ui.loading.close();
            })
        };

        /**
        * 営業担当者チェックする。
        * 部署マスタ．flg_eigyo ＝１			
        * 部署マスタ．会社コード＝会社コード			
        * 部署マスタ．部署コード＝部署コード		
        */
        page.detailInput.loadBusho = function (data) {

            return $.ajax(App.ajax.odata.get(App.str.format(page.detailInput.urls.eigyo, {
                cd_kaisha: data.cd_kaisha,
                cd_busho: data.cd_busho,
                flg_eigyo: App.settings.app.kbn_eigyo.eigyo_nashi
            })));

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

        /**
         * 確認ダイアログを表示します
         * param options 
         *    title: タイトル,
         *    text: 確認メッセージ,
         *    ok: OKボタン表示名,
         *    cancel: Cancelボタン表示名,
         *    hideCanel: Cancelボタンを非表示にする場合はtrue,
         *    backdrop: 背景クリックでダイアログ閉じる挙動をさせない場合、"static",
         *    keyboard: escapeでダイアログ閉じる挙動をさせない場合、false
         */
        page.infoDialog.confirm = function (options) {
            options = options || {};

            var ok = options.ok || "OK",
                cancel = options.cancel || "キャンセル",
                title = options.title || "確認",
                text = options.text,
                _backdrop = options.backdrop === undefined ? true : options.backdrop,
                _keyboard = options.keyboard === undefined ? true : options.keyboard,
                _hideCancel = options.hideCancel === true ? true : false,
                dialog = $("#InfoDialog"),
                header = dialog.find(".modal-header"),
                footer = dialog.find(".modal-footer"),
                defer = $.Deferred(),
                isOk = false,
                isMultiModal = options.multiModal || false,
                show = function (el, text) {
                    if (text) {
                        el.html(text).show();
                    } else {
                        el.hide();
                    }
                },
                mode = options.mode;

            dialog.find(".modal-body").css("padding-bottom", 0);
            dialog.find(".modal-body .item-label").css("font-size", 14).css("height", "100%");

            show(dialog.find(".modal-header h4"), title);
            show(dialog.find(".modal-body .item-label"), text);
            footer.find(".btn-ok").off("click").html(ok);
            footer.find(".btn-cancel").off("click").html(cancel);
            dialog.find(".modal-dialog").draggable({
                drag: true,
            });

            if (_hideCancel) {
                footer.find(".btn-cancel").hide();
            } else {
                footer.find(".btn-cancel").show();
            }

            dialog.on("shown.bs.modal", function () {
                if (mode == "GLabelRenkei") {
                    footer.find(".btn-cancel").focus();
                } else {
                    footer.find(".btn-ok").focus();
                }
            });
            //dialog.modal("show")時にoption指定
            dialog.modal({
                show: true,
                backdrop: _backdrop,
                keyboard: _keyboard
            });
            dialog.css("padding-top", "15%").css("z-index", 1060);

            footer.find(".btn-ok").on("click", function (e) {
                isOk = true;
                dialog.modal("hide");
            });
            footer.find(".btn-cancel").on("click", function (e) {
                isOk = false;
                dialog.modal("hide");
            });

            dialog.on("hide.bs.modal", function () {
                if (!isMultiModal) {
                    (isOk ? defer.resolve : defer.reject)();
                }
                //else {
                //    setTimeout(function () {
                //        (isOk ? defer.resolve : defer.reject)();
                //    }, 300);
                //}
            });

            dialog.on("hidden.bs.modal", function () {
                if (isMultiModal) {
                    (isOk ? defer.resolve : defer.reject)();
                }
            });

            return defer.promise();
        };

    </script>

</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">

    <div class="content-wrap">
        <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
        <div class="header">
            <div title="検索条件" class="part">

                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>ユーザID</label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="tel" class="honnin_nomi limit-input-int" data-prop="id_user" maxlength="10" />
                    </div>

                    <div class="control-label col-xs-1">
                        <label>所属会社</label>
                    </div>
                    <div class="control col-xs-2">
                        <select data-prop="cd_kaisha" class="shozoku_kaisha"></select>
                    </div>

                    <div class="control-label col-xs-1">
                        <label>所属グループ</label>
                    </div>
                    <div class="control col-xs-2">
                        <select data-prop="cd_group" class=""></select>
                    </div>

                    <div class="control col-xs-3">
                    </div>
                </div>

                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>担当者名</label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="text" class="ime-active" data-prop="nm_user" />
                    </div>


                    <div class="control-label col-xs-1">
                        <label>所属部署</label>
                    </div>
                    <div class="control col-xs-2">
                        <select data-prop="cd_busho" class=""></select>
                    </div>

                    <div class="control-label col-xs-1">
                        <label>所属チーム</label>
                    </div>
                    <div class="control col-xs-2">
                        <select data-prop="cd_team" class=""></select>
                    </div>

                    <div class="control col-xs-3">
                    </div>
                </div>
                <div class="header-command">
                    <button type="button" id="search" class="btn btn-sm btn-primary">検索</button>
                </div>
            </div>
        </div>

        <!--TODO: 明細を定義するHTMLをここに記述します。-->
        <div class="detail smaller">
            <!--TODO: 明細をpartにする場合は以下を利用します。
            <div title="TODO: 明細一覧部のタイトルを設定します。" class="part">
            -->
            <div class="control-label toolbar">
                <i class="icon-th"></i>
                <div class="btn-group">
                    <button type="button" class="btn btn-default btn-xs honnin_nomi" id="add-item">新規</button>
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
                        <th style="width: 40px;">詳細</th>
                        <th style="width: 100px;">ユーザID</th>
                        <th style="width: 100px;">権限</th>
                        <th style="width: 150px;">氏名</th>
                        <th style="width: 100px;">所属会社</th>
                        <th style="width: 140px;">所属部署</th>
                        <th style="width: 100px;">所属グループ</th>
                        <th style="width: 180px;">所属チーム</th>
                        <th style="width: 100px;">役職</th>
                        <th style="width: 200px;">担当製造会社</th>
                        <th style="width: 60px;">会社間参照</th>
                        <th style="width: 60px;">工場間参照</th>

                    </tr>
                </thead>
                <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->
                <tbody class="item-tmpl" style="cursor: default; display: none;">
                    <tr>
                        <!--TODO: 単一行を作成する場合は、以下を利用します。-->
                        <td>
                            <span class="select-tab unselected"></span>
                        </td>
                        <td style="text-align: center">
                            <button class="btn btn-info btn-xs edit-select">詳細	</button>
                        </td>
                        <td>
                            <label data-prop="id_user"></label>
                        </td>
                        <td>
                            <label data-prop="nm_kengen" class="overflow-ellipsis"></label>
                        </td>
                        <td>
                            <label data-prop="nm_user" class="overflow-ellipsis"></label>
                        </td>
                        <td>
                            <label data-prop="nm_kaisha" class="overflow-ellipsis"></label>
                        </td>
                        <td>
                            <label data-prop="nm_busho" class="overflow-ellipsis"></label>
                        </td>
                        <td>
                            <label data-prop="nm_group" class="overflow-ellipsis"></label>
                        </td>
                        <td>
                            <label data-prop="nm_team" class="overflow-ellipsis"></label>
                        </td>
                        <td>
                            <label data-prop="nm_yakushoku" class="overflow-ellipsis"></label>
                        </td>
                        <td>
                            <label data-prop="nm_kaisha_tanto" class="overflow-ellipsis"></label>
                        </td>
                        <td style="text-align: center">
                            <input type="checkbox" data-prop="flg_kaishakan_sansyo" value="true" disabled="disabled" />
                        </td>
                        <td style="text-align: center">
                            <input type="checkbox" data-prop="flg_kojyokan_sansyo" value="true" disabled="disabled" />
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

            <div class="row">
                <div class="control-label col-xs-1">
                    <label>社員番号<span class="required">*</span></label>
                </div>
                <div class="control col-xs-2">
                    <input type="tel" data-prop="id_user" class="honnin_nomi limit-input-int input-mode-edit" maxlength="10" />
                </div>
                <div class="control col-xs-9">
                </div>
            </div>

            <div class="row">
                <div class="control-label col-xs-1">
                    <label>パスワード<span class="required">*</span></label>
                </div>
                <div class="control col-xs-2">
                    <input type="password" data-prop="password" style="width: 98%;">
                </div>
                <div class="control col-xs-9">
                </div>
            </div>

            <div class="row">
                <div class="control-label col-xs-1">
                    <label>権限<span class="required">*</span></label>
                </div>
                <div class="control col-xs-2">
                    <select data-prop="cd_kengen" class="honnin_nomi"></select>
                </div>
                <div class="control col-xs-9">
                </div>
            </div>
            <div class="row">
                <div class="control-label col-xs-1">
                    <label>氏名<span class="required">*</span></label>
                </div>
                <div class="control col-xs-2">
                    <input type="text" data-prop="nm_user" />
                </div>
                <div class="control col-xs-9">
                </div>
            </div>
            <div class="row">
                <div class="control-label col-xs-1">
                    <label>所属会社<span class="required">*</span></label>
                </div>
                <div class="control col-xs-2">
                    <select data-prop="cd_kaisha" class="honnin_nomi shozoku_kaisha input-mode-edit busho_kaisha_bunrui"></select>
                </div>
                <div class="control col-xs-9">
                </div>
            </div>
            <div class="row">
                <div class="control-label col-xs-1">
                    <label>所属部署<span class="required">*</span></label>
                </div>
                <div class="control col-xs-2">
                    <select data-prop="cd_busho" class="honnin_nomi busho_kaisha_bunrui"></select>
                </div>
                <div class="control col-xs-9">
                </div>
            </div>
            <div class="row validate_kengen">
                <div class="control-label col-xs-1">
                    <label>所属グループ<span class="required seihoshien">*</span></label>
                </div>
                <div class="control col-xs-2">
                    <select data-prop="cd_group" class="honnin_nomi seihoshien_kaihatsu seihoshien_kojo shisa_seiho"></select>
                </div>
                <div class="control col-xs-9">
                </div>
            </div>
            <div class="row validate_kengen">
                <div class="control-label col-xs-1">
                    <label>所属チーム<span class="required seihoshien">*</span></label>
                </div>
                <div class="control col-xs-2">
                    <select data-prop="cd_team" class="honnin_nomi seihoshien_kaihatsu seihoshien_kojo shisa_seiho"></select>
                </div>
                <div class="control col-xs-9">
                </div>
            </div>
            <div class="row validate_kengen">
                <div class="control-label col-xs-1">
                    <label>役職<span class="required seihoshien">*</span></label>
                </div>
                <div class="control col-xs-2">
                    <select data-prop="cd_yakushoku" class="honnin_nomi seihoshien_kaihatsu seihoshien_kojo shisa_seiho"></select>
                </div>
                <div class="control col-xs-9">
                </div>
            </div>
            <div class="row">
                <div class="control-label col-xs-1 seizokaisha-height">
                    <label>担当製造会社</label>
                </div>
                <div class="control col-xs-11 seizokaisha-height">
                    <div class="tanto-area" style="height: 90px!important; width: 19.17%; float: left; overflow-y: scroll">
                        <div style="position: relative;" class="tanto-tmpl">
                            <input type="text" data-prop="nm_kaisha" disabled="disabled" />
                            <span class="close-input seihoshien_kaihatsu seihoshien_kojo" style="cursor: pointer">X</span>
                        </div>
                    </div>
                    <button class="btn btn-info add-item-tanto honnin_nomi seihoshien_kaihatsu seihoshien_kojo shisa_seiho kaisaselect">追加</button>
                </div>
            </div>
            <div class="row">
                <div class="control-label col-xs-1">
                    <label>会社間参照</label>
                </div>
                <div class="control col-xs-2">
                    <input type="checkbox" data-prop="flg_kaishakan_sansyo" value="true" class="honnin_nomi shisaquick seihoshien_kojo shisaquick_seihoshien_kojo shisa_seiho" />
                </div>
                <div class="control col-xs-9">
                </div>
            </div>
            <div class="row">
                <div class="control-label col-xs-1">
                    <label>工場間参照</label>
                </div>
                <div class="control col-xs-2">
                    <input type="checkbox" data-prop="flg_kojyokan_sansyo" value="true" class="honnin_nomi shisaquick seihoshien_kaihatsu shisaquick_seihoshien_kaihatsu shisa_seiho" />
                </div>
                <div class="control col-xs-9">
                </div>
            </div>
        </div>
    </div>

</asp:Content>

<asp:Content ID="FooterContent" ContentPlaceHolderID="FooterContent" runat="server">
    <div class="command-left">
        <button type="button" id="clearDetail" class="btn btn-sm btn-primary">クリア</button>
        <button type="button" id="clearInput" class="btn btn-sm btn-primary" style="display: none">クリア</button>
        <button type="button" id="clearEdit" class="btn btn-sm btn-primary" style="display: none">クリア</button>
    </div>
    <div class="command">
        <!--TODO: コマンドボタン（右寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" id="addItem" class="btn btn-sm btn-primary" style="display: none;" disabled="disabled">保存</button>
        <button type="button" id="editItem" class="btn btn-sm btn-primary" disabled="disabled" style="display: none;">保存</button>
        <button type="button" id="deleteItem" class="btn btn-sm btn-default honnin_nomi" style="display: none;">削除</button>
    </div>

</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container">
        <div class="modal fade confirm" tabindex="-1" id="InfoDialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title"></h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="control-success-label col-xs-12 control-height" style="height: auto">
                                <label class="item-label" style="height: auto; vertical-align: middle;"></label>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-sm btn-primary btn-ok"></button>
                        <button type="button" class="btn btn-sm btn-cancel" data-dismiss="modal"></button>
                    </div>

                </div>
            </div>
        </div>
    </div>
</asp:Content>
