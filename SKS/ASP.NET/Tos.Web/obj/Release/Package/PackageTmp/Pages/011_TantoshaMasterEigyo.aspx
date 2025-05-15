<%@ Page Title="011_担当者マスタ（営業）" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="011_TantoshaMasterEigyo.aspx.cs" Inherits="Tos.Web.Pages._011_TantoshaMaster" %>

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

        .detailInput .required {
            color: red;
        }

        .detailInput .tanto-tmpl {
            display: none;
        }
    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("_011_TantoshaMaster", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.dataTakeCount,   // TODO:取得するデータ数を指定します。
                filter: ""
            },
            values: {
                isChangeRunning: {},
                isChangeInputRunning: {},
                ippan: 100,// 一般
                kari_toroku_yuza: 101,//仮登録ユーザ
                honbu_kengen: 102,//本部権限
                shisutemu_kanrisha: 103, //システム管理者
                kino_104: 104,
                kino_105: 105
            },
            urls: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
                kaisha: "../api/TantoshaMasterEigyo/GetComboboxKaisha",
                busho: "../api/TantoshaMasterEigyo/GetComboboxBusho",
                kengen: "../Services/ShisaQuickService.svc/ma_kengen?$filter=kbn_eigyo ne null&$orderby=cd_kengen",
                TantoshaIchiranEigyoDialog: "Dialogs/501_TantoshaIchiranEigyo_Dialog.aspx",
                TantoshaMasterEigyo: "../api/TantoshaMasterEigyo/",
                mausernew: "../api/TantoshaMasterEigyo/GetMaUserNew",
                user: "../Services/ShisaQuickService.svc/ma_user_togo?$filter=id_user eq {0} and cd_kaisha eq {1}",
                ma_user: "../Services/ShisaQuickService.svc/ma_user_togo?$filter=id_user eq {0}M"
            },
            header: {
                options: {},
                values: {},
                urls: {
                    search: "../api/TantoshaMasterEigyo/GetData",
                    member: "../api/TantoshaMasterEigyo/GetMember"
                }
            },
            detail: {
                options: {},
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
                TantoshaIchiranEigyoDialog: {},
            },
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

            App.ui.loading.show();

            var sleep = 0;
            var condition = "Object.keys(page.values.isChangeRunning).length == 0";
            App.ui.wait(sleep, condition, 100)
            .then(function () {
                page.detailInput.executeValidation(page.detailInput.element.find("select, input").not(".user_member")).then(function () {
                    var closeMessage = {
                        text: App.messages.app.AP0004
                    };
                    page.dialogs.confirmDialog.confirm(closeMessage).then(function () {

                        var changeSets = {
                            ma_user_togo: page.detailInput.data.getChangeSet(),
                            ma_member: page.detailInput.dataMember.getChangeSet(),
                            id_user: page.detailInput.element.findP("id_user").val()
                        };
                        //TODO: データの保存処理をここに記述します。
                        return $.ajax(App.ajax.webapi.post(page.urls.TantoshaMasterEigyo, changeSets))
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
                                    //page.detailInput.previous(false);
                                    //page.header.search(false);
                                    App.ui.page.notifyAlert.clear();
                                    App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
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
            App.ui.wait(sleep, condition, 100).then(function () {
                page.detailInput.executeValidation(page.detailInput.element.find("select, input").not(".user_member")).then(function () {
                    var closeMessage = {
                        text: App.messages.app.AP0004
                    };
                    page.dialogs.confirmDialog.confirm(closeMessage).then(function () {

                        var changeSets = {
                            ma_user_togo: page.detailInput.data.getChangeSet(),
                            ma_member: page.detailInput.dataMember.getChangeSet(),
                            id_user: page.detailInput.element.findP("id_user").val(),
                            cd_kaisha: page.detailInput.element.findP("cd_kaisha").val()
                        };
                        var itemDeleted, itemCreated, isChangeKaisha = false, cd_kaisha_original;
                        for (key in page.detailInput.data.entries) {
                            if (!page.detailInput.data.entries.hasOwnProperty(key)) {
                                continue;
                            }
                            if (page.detailInput.data.entries[key].state === App.ui.page.dataSet.status.Modified) {
                                if (page.detailInput.data.entries[key].current.cd_kaisha != page.detailInput.data.entries[key].original.cd_kaisha) {
                                    cd_kaisha_original = page.detailInput.data.entries[key].original.cd_kaisha;
                                    isChangeKaisha = true;
                                    break;
                                }
                            }
                        }
                        //change changeSets.ma_user_togo when update kaisha
                        if (isChangeKaisha) {
                            var updates = changeSets.ma_user_togo.updated;
                            for (var i = 0; i < updates.length; i++) {
                                itemCreated = $.extend({}, updates[i]);
                                itemDeleted = $.extend({}, updates[i]);
                                itemDeleted.cd_kaisha = cd_kaisha_original;

                                changeSets.ma_user_togo.created.push(itemCreated);  //move data update current into changeSets created
                                changeSets.ma_user_togo.deleted.push(itemDeleted); //move data update original into changeSets deleted

                                changeSets.ma_user_togo.updated.splice(i, 1); // remove data update
                                i--;
                            }
                        }
                        //TODO: データの更新処理をここに記述します。
                        return $.ajax(App.ajax.webapi.put(page.urls.TantoshaMasterEigyo, changeSets))
                            .then(function (result) {

                                //TODO: データの保存成功時の処理をここに記述します。
                                return page.detail.updateData(changeSets);
                            }).then(function (result) {
                                page.detailInput.values.isChange = false;
                                page.detailInput.previous(false);
                                App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                                //return App.async.all([page.header.search(false)]);
                            }).fail(function (error) {

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
                    entity = $.extend(true, {}, page.detailInput.data.entry(id)),
                    changeSets;

                //page.detailInput.data.remove(entity);
                //changeSets = page.detailInput.data.getChangeSet();

                var dataSet = App.ui.page.dataSet();
                page.detailInput.dataDelete = dataSet;

                page.detailInput.dataDelete.attach(entity);
                page.detailInput.dataDelete.remove(entity);

                var changeSets = {
                    ma_user_togo: page.detailInput.dataDelete.getChangeSet(),
                    ma_member: page.detailInput.dataMember.getChangeSet(),
                    id_user: page.detailInput.element.findP("id_user").val()
                }

                //TODO: データの更新処理をここに記述します。
                $.ajax(App.ajax.webapi.delete(page.urls.TantoshaMasterEigyo, changeSets)).then(function (result) {

                    //TODO: データの保存成功時の処理をここに記述します。
                    page.detail.removeData(changeSets.ma_user_togo);
                    page.detailInput.previous(false);

                    App.ui.page.notifyInfo.message(App.messages.base.MS0008).show();
                }).fail(function (error) {
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

            return page.detailInput.flagAddChange;
            //if (page.detailInput.data) {
            //    detailInput = page.detailInput.data.getChangeSet();
            //    if (detailInput.created.length || detailInput.updated.length || detailInput.deleted.length) {

            //        //Delete changeSets
            //        //page.detail.removeData(changeSets);

            //        return closeMessage;
            //    }
            //}

            ////check exit change data tanto kaisha
            //if (page.detailInput.dataMember) {
            //    detailTanto = page.detailInput.dataMember.getChangeSet();
            //    if (detailTanto.created.length || detailTanto.updated.length || detailTanto.deleted.length) {

            //        //Delete changeSets
            //        //page.detail.removeData(changeSets);

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

            //TODO: ヘッダー/明細以外の初期化の処理を記述します。

            page.flgEditAble = {
                ippan: false, // 一般
                kari_toroku_yuza: false, //仮登録ユーザ
                honbu_kengen: false, //本部権限
                shisutemu_kanrisha: false, //システム管理者
                kino_104: false,
                kino_105: false
            }

            //TODO: ヘッダー/明細以外の初期化の処理を記述します。
            getKengenGamen(App.settings.app.id_gamen.tantosha_master_eigyo).then(function (results) {
                var i = 0,
                    resultLength = results.length,
                    id_kino = 0;
                for (i; i < resultLength; i++) {
                    if (results[i].id_kino >= id_kino) {
                        id_kino = results[i].id_kino;
                    }
                }
                if (id_kino == page.values.ippan) {
                    page.flgEditAble.ippan = true;
                }
                if (id_kino == page.values.kari_toroku_yuza ) {
                    page.flgEditAble.kari_toroku_yuza = true;
                }
                if (id_kino == page.values.honbu_kengen) {
                    page.flgEditAble.honbu_kengen = true;
                }
                if (id_kino == page.values.shisutemu_kanrisha) {
                    page.flgEditAble.shisutemu_kanrisha = true;
                }
                if (id_kino == page.values.kino_104) {
                    page.flgEditAble.kino_104 = true;
                }
                if (id_kino == page.values.kino_105) {
                    page.flgEditAble.kino_105 = true;
                }

                

                return page.loadMasterData(".header, .detailInput");
            }).then(function (result) {

                return page.loadDialogs();
            }).then(function (result) {
                //TODO: 画面の初期化処理成功時の処理を記述します。
                page.controlEditGamen();

            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

            }).always(function (result) {
                page.detailInput.values.isChange = false;
                page.header.element.find(":input:first").focus();
                App.ui.loading.close();
            });
        };

        /**
        * 画面表示を設定する。
        */
        page.controlEditGamen = function () {
            
            //not containt kengen
            if (!page.flgEditAble.ippan && !page.flgEditAble.honbu_kengen && !page.flgEditAble.shisutemu_kanrisha
                && !page.flgEditAble.kari_toroku_yuza && !page.flgEditAble.kino_104 && !page.flgEditAble.kino_105) {
                var options = {
                    text: App.messages.app.AP0089,
                    hideCancel: true
                };
                //page.dialogs.confirmDialog.confirm(options)
                //.always(function () {
                window.location.href = App.settings.app.toMainMenuLink;
                //});
                //window.location = '<=ResolveUrl("~/AuthorizedError.aspx") %>';
            }

            if (page.flgEditAble.ippan) {

                //header
                var new_id_user = ("0000000000" + App.ui.page.user.EmployeeCD).slice(-10);

                page.header.element.findP("id_user").val(new_id_user);
                page.header.element.findP("id_user").prop("disabled", page.flgEditAble.ippan);

                //detail
                page.detail.element.find("#add-item").prop("disabled", page.flgEditAble.ippan);

                //detailInput
                page.detailInput.element.findP("cd_kengen").prop("disabled", page.flgEditAble.ippan);
                page.detailInput.element.findP("cd_kaisha").prop("disabled", page.flgEditAble.ippan);
                page.detailInput.element.findP("cd_busho").prop("disabled", page.flgEditAble.ippan);
                $("#deleteItem").prop("disabled", page.flgEditAble.ippan);
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
        element.find("input[type='tel']").val("");

        page.detailInput.values.isChange = false;

        //renew data member
        dataSet = App.ui.page.dataSet();
        page.detailInput.dataMember = dataSet;
        //reload data combobox
        page.detailInput.reLoadDataCombo(page.detailInput.dataUserOriginal, ".detailInput").then(function () {
            page.detailInput.bind(page.detailInput.dataUserOriginal);
            page.detailInput.bindMember(page.detailInput.dataMemberOriginal, false);
            if (element.findP("cd_kengen").find("option:selected").attr("kbn_eigyoval") != App.settings.app.kbn_eigyo.eigyo_nashi) {
                $(".add-user").prop("disabled", true);
            } else {
                $(".add-user").prop("disabled", false);
            }
        }).always(function () {
            $("#editItem").prop("disabled", true);
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
        page.loadMasterData();

        //reload table detail
        page.detail.dataTable.dataTable("clear");
        elemtntDetail.find("#nextsearch").hide();

        //reload count row
        elemtntDetail.findP("data_count").text("");
        elemtntDetail.findP("data_count_total").text("");

        if (page.flgEditAble.honnin_nomi) {
            var new_id_user = ("0000000000" + App.ui.page.user.EmployeeCD).slice(-10);

            page.header.element.findP("id_user").val(new_id_user).prop("disabled", page.flgEditAble.honnin_nomi);
        }
    }

    /**
     * マスターデータのロード処理を実行します。
     */
    page.loadMasterData = function () {

        //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
        return $.ajax(App.ajax.webapi.get(page.urls.kaisha)).then(function (result) {//

            var cd_kaisha = $(".header, .detailInput").findP("cd_kaisha");
            cd_kaisha.children().remove();
            App.ui.appendOptions(
                cd_kaisha,
                "cd_kaisha",
                "nm_kaisha",
                result,
                true
            );
        })
        .then(function (result) {

            return page.loadMasterDataKengen();
        });
        //TODO: マスタデータのロード処理を実装後、下の1行を削除してください。
        return App.async.success();
    };

    /**
    * 所属部署データのロード処理を実行します。
    */
    page.loadMasterDataBusho = function (kaisha, cd_busho, kengen, elementClass) {
        var busho = $(elementClass).findP("cd_busho");
        busho.children().remove();

        if (!App.isUndefOrNullOrStrEmpty(kaisha)) {
            return $.ajax(App.ajax.webapi.get(page.urls.busho, { cd_kaisha: kaisha, cd_kengen: kengen })).done(function (result) {

                App.ui.appendOptions(
                    busho,
                    "cd_busho",
                    "nm_busho",
                    result,
                    true
                );

                if (!App.isUndefOrNullOrStrEmpty(cd_busho)) {
                    busho.val(cd_busho);
                }
            });
        }
    };

    /**
    * 所属部署データのロード処理を実行します。
    */
    page.loadMasterDataKengen = function () {
        var cd_kengen = $(".header, .detailInput").findP("cd_kengen");
        cd_kengen.children().remove();

        return $.ajax(App.ajax.odata.get(page.urls.kengen)).done(function (result) {

            App.common.appendOptions(
                cd_kengen,
                "cd_kengen",
                "nm_kengen",
                result.value,
                true,
                [],
                false,
                "",
                "kbn_eigyo"
            );
            page.values.kbn_kengen_bunrui = [];
            $.each(result.value, function (ind, kengen) {
                page.values.kbn_kengen_bunrui[kengen.cd_kengen.toString()] = kengen.kbn_kengen_bunrui;
            })
            //if (!App.isUndefOrNullOrStrEmpty(cd_kengen)) {
            //    cd_kengen.val(cd_kengen);
            //}
        })
    };

    /**
     * 共有ダイアログのロード処理を実行します。
     */
    page.loadDialogs = function () {

        //TODO: 共有ダイアログデータを取得し、画面にバインドする処理を記述します。
        return App.async.all({

            confirmDialog: $.get(page.urls.confirmDialog),
            TantoshaIchiranEigyoDialog: $.get(page.urls.TantoshaIchiranEigyoDialog)

            //searchDialog: $.get(/* TODO:共有ダイアログの URL */),
        }).then(function (result) {

            $("#dialog-container").append(result.successes.confirmDialog);
            page.dialogs.confirmDialog = ConfirmDialog;

            //dialog TantoshaIchiranEigyoDialog
            $("#dialog-container").append(result.successes.TantoshaIchiranEigyoDialog);
            page.dialogs.TantoshaIchiranEigyoDialog = TantoshaIchiranEigyoDialog;
            //page.dialogs.TantoshaIchiranEigyoDialog.options.parameter.no_gamen = page.values.no_gamen;
            page.dialogs.TantoshaIchiranEigyoDialog.initialize();

        });
    };

    /**
     * 画面明細の一覧から検索ダイアログを表示します。
     */
    page.detailInput.showTantoshaIchiranEigyoDialog = function (e) {
        var element = page.detailInput.element,
                tbody = $(e.target).closest("tbody"),
                row;

        page.detailInput.executeValidation(element.find("select").not("select:first"))
            .then(function () {

                page.dialogs.TantoshaIchiranEigyoDialog.options.parameter.cd_kaisha = element.findP("cd_kaisha").val();
                page.dialogs.TantoshaIchiranEigyoDialog.options.parameter.cd_busho = element.findP("cd_busho").val();

                App.ui.page.notifyAlert.clear();
                page.dialogs.TantoshaIchiranEigyoDialog.element.modal("show");

                //検索ダイアログでデータ選択が実行された時に呼び出される関数を設定しています。
                page.dialogs.TantoshaIchiranEigyoDialog.dataSelected = function (result) {
                    App.ui.page.notifyAlert.clear();
                    var id_user = element.findP("id_user").val(),
                        listResult = [],
                        flagMessage = false;

                    $.each(result, function (index, item) {

                        var flag = false;

                        //check member  exist
                        if (element.findP("nm_member").not(":first").length > 0) {
                            $.each(element.findP("nm_member").not(":first"), function (index, data) {
                                var id = $(data).closest("div").attr("data-key");
                                var entity = page.detailInput.dataMember.entry(id);

                                if (item.id_user == entity.id_member) {
                                    flag = true;
                                    if (!flagMessage) {
                                        flagMessage = true;
                                        App.ui.page.notifyAlert.message(App.messages.app.AP0086).show();
                                    }
                                    return false;
                                } else if (item.id_user == id_user) {
                                    flag = true;
                                    App.ui.page.notifyAlert.message(App.messages.app.AP0085).show();
                                    return false;
                                }

                            });
                        } else {
                            if (item.id_user == id_user) {
                                flag = true;
                                App.ui.page.notifyAlert.message(App.messages.app.AP0085).show();
                            }
                        }
                        if (!flag) {
                            listResult.push(item);
                        }
                    });
                    //if (!flag) {
                    page.detailInput.addMember(listResult, true, id_user);

                    $("#editItem").prop("disabled", false);
                    //App.ui.page.notifyAlert.clear();
                    //}
                    delete page.dialogs.TantoshaIchiranEigyoDialog.dataSelected;

                }
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
                name: "ユーザーID"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                digits: App.messages.base.digits
            }
        },
        nm_user: {
            rules: {
                maxlength: 60
            },
            options: {
                name: "担当者名"
            },
            messages: {
                maxlength: App.messages.base.maxlength
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
        element.validation().validate().done(function () {
            if (property == "id_user") {
                if (data.id_user >= 0) {
                    var new_id_user = ("0000000000" + data.id_user).slice(-10);

                    target.val(new_id_user);
                }
            }
            if (property == "cd_kaisha") {
                page.loadMasterDataBusho(data.cd_kaisha, data.cd_busho, null, ".header");
            }
            if ($("#nextsearch").hasClass("show-search")) {
                $("#nextsearch").removeClass("show-search").hide();
                App.ui.page.notifyInfo.clear();
                App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
            }

        });
        //else if (page.detail.searchData) {
        //    // 保持検索データの消去
        //    page.detail.searchData = undefined;
        //    App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
        //}
    };

    /**
     * 検索処理を定義します。
     */
    page.header.search = function (isLoading) {

        var deferred = $.Deferred(),
            query;

        page.header.validator.validate().done(function () {

            page.options.skip = 0;
            page.options.filter = page.header.createFilter();

            if (isLoading) {
                App.ui.loading.show();
                App.ui.page.notifyAlert.clear();
            }

            return $.ajax(App.ajax.webapi.get(page.header.urls.search, page.options.filter))
            .done(function (result) {

                page.detail.bind(result);

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
            filters = {};

        /* TODO: 検索条件のフィルターを定義してください。*/
        if (!App.isUndefOrNull(criteria.id_user) && criteria.id_user >= 0) {
            filters.id_user = criteria.id_user;
        }
        if (!App.isUndefOrNull(criteria.nm_user)) {
            filters.nm_user = criteria.nm_user;
        }
        if (!App.isUndefOrNull(criteria.cd_kaisha) && criteria.cd_kaisha > 0) {
            filters.cd_kaisha = criteria.cd_kaisha;
        }
        if (!App.isUndefOrNull(criteria.cd_busho) && criteria.cd_busho > 0) {
            filters.cd_busho = criteria.cd_busho;
        }

        //gamen
        if (page.flgEditAble.honbu_kengen) {
            filters.id_kino = page.values.honbu_kengen;
        }

        if (page.flgEditAble.shisutemu_kanrisha) {
            filters.id_kino = page.values.shisutemu_kanrisha;
        }

        return filters;
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

        //var query = {
        //        url: "TODO: 検索結果取得サービスの URL を設定してください。",
        //        filter: page.options.filter,
        //        orderby: "TODO: ソート対象の列名",
        //        skip: page.options.skip,     // TODO:先頭からスキップするデータ数を指定します。
        //        top: page.options.top,       // TODO:取得するデータ数を指定します。
        //        inlinecount: "allpages"
        //    };

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

    /**
     * 画面明細の各行にデータを設定する際のオプションを定義します。
     */
    page.detailInput.options.bindOption = {
        appliers: {
            id_user: function (value, element) {
                if (value === "") {
                    element.val(value);
                    return true;
                }
                value = ("0000000000" + value).slice(-10);

                element.val(value);
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
                value = ("0000000000" + value).slice(-10);

                element.text(value);
                return true;
            },
            kbn_eigyo: function (value, element) {
                if (value == App.settings.app.kbn_eigyo.eigyo_ari) {
                    element.text(App.settings.app.honbu_kengen.ari);
                }
                else {
                    element.text(App.settings.app.honbu_kengen.nashi);
                }
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

        App.ui.loading.show();

        $.ajax(App.ajax.webapi.get(page.header.urls.member, { id_user: entity.id_user })).done(function (result) {

            page.detail.dataTable.dataTable("getFirstViewRow", function (row) {
                page.detail.firstViewRow = row;
            });

            page.detailInput.options.mode = page.detailInput.mode.edit;
            page.detailInput.show(entity, result);

        }).fail(function (error) {
            App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
        }).always(function () {
            App.ui.loading.close();
        });
    };

    /**
     * 編集画面で更新された画面明細の行を最新化します。
     */
    page.detail.updateData = function (result) {
        var updatedKey = result.ma_user_togo.updated.length > 0 ? result.ma_user_togo.updated[0] : result.ma_user_togo.created[0],
            element = page.detail.element;
        var filters = {};
        filters.id_user = updatedKey == undefined ? result.id_user : updatedKey.id_user;
        filters.cd_kaisha = updatedKey == undefined ? result.cd_kaisha : updatedKey.cd_kaisha;
        return $.ajax(App.ajax.webapi.get(page.header.urls.search, filters))
        .then(function (result) {
            var newData = result.Items[0];
            if (!newData) {
                return;
            }
            var selectedRow = element.find(".selected").closest("tbody"),
                id = selectedRow.attr("data-key");
            if (App.isUndefOrNull(id)) {
                return;
            }
            var entity = page.detail.data.entry(id);
            //対象データの各値を最新の値で更新します。
            Object.keys(newData).forEach(function (key) {
                if (key in entity) {
                    entity[key] = newData[key];
                }
            });
            //行にデータを再バインドします。
            page.detail.dataTable.dataTable("getRow", element.find(selectedRow), function (row) {
                selectedRow = row.element;
            });
            selectedRow.find("[data-prop]").val("").text("");
            selectedRow.form(page.detail.options.bindOption).bind(entity);
            return true;
        });
    };

    /**
     * 編集画面で削除された画面明細の行を削除します。
     */
    page.detail.removeData = function (result) {

        //TODO: 削除されたデータのキーを取得します。
        //var removedKey = result.deleted[0].no_seq;

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
            selectedRow = element.find(".selected").closest("tbody");
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
    };

    /**
     * 編集画面の初期化処理を行います。
     */
    page.detailInput.initialize = function () {

        var element = $(".detailInput");
        page.detailInput.memberData = 0;
        page.detailInput.flagAddChange = false;
        page.detailInput.element = element;
        page.detailInput.validator = element.validation(page.createValidator(page.detailInput.options.validations));
        element.on("change", ":input", page.detailInput.change);
        element.on("click", ".previous", page.detailInput.previous);
        element.on("click", ".close-input", page.detailInput.delMember);

        element.on("click", ".add-user", page.detailInput.showTantoshaIchiranEigyoDialog);
    };

    /**
    * 新しいデータをコンボボックスにリロードする.
    */
    page.detailInput.reLoadDataCombo = function (entity, area) {
        App.ui.loading.show();
        //所属グループ
        return page.loadMasterData().then(function () {

            //所属部署
            return page.loadMasterDataBusho(entity.cd_kaisha, entity.cd_busho, entity.cd_kengen, area);
        }).fail(function (error) {
            App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

        }).always(function (result) {
            App.ui.loading.close();
        });
    };

    /**
     * 画面明細のバリデーションフィルターを設定します。（必須チェックを行わない）
     */
    page.detailInput.validationFilter = function (item, method, state, options) {
        return (method !== "required" && method !== "minlength" && method !== "check_type");
    };

    /**
     * 画面明細のバリデーションを実行します。
     */
    page.detailInput.executeValidation = function (targets, options) {
        var defaultOptions = {
            targets: targets
        };
        options = $.extend(true, {}, defaultOptions, options);

        return page.detailInput.validator.validate(options);
    };

    /**
     * 編集画面の表示処理を行います。
     */
    page.detailInput.show = function (data, result) {

        page.detailInput.values.isChange = false;

        var element = page.detailInput.element;
        setTimeout(function () {
            // Clear all header validation
            page.header.validator.validate({
                filter: function () {
                    return false;
                }
            });
        }, 200);

        //TODO:項目をクリアする処理をここに記述します。
        element.find(":input:not([type='checkbox']):not([type='radio'])").val("");
        element.find("input[type='checkbox']").prop('checked', false);
        element.findP("cd_kengen").prop("disabled", false);
        element.findP("cd_kaisha").prop("disabled", false);
        element.findP("cd_busho").prop("disabled", false);
        element.find(".add-user").prop("disabled", true);
        //TODO:radioボタンの初期表示は、画面要件に合わせて記述します。

        //TODO:入力項目以外の個別項目のクリアはここで記述

        //TODO:データバインド処理をここに記述します。
        page.detailInput.dataUserOriginal = $.extend(true, {}, data);

        page.detailInput.bind(data);

        //TODO:画面モードによる処理をここに記述します。
        if (page.detailInput.options.mode === page.detailInput.mode.input) {

            element.findP("cd_busho").children().remove();

            page.detailInput.memberData = 0;
            page.detailInput.dataMemberOriginal = [];
            page.detailInput.bindMember([], false);

            $("#addItem, #clearInput").show();
            $("#editItem, #deleteItem, #clearDetail, #clearEdit").hide();
            element.find(".input-mode-edit").prop("readonly", false).prop("disabled", false);
            //TODO:キー項目が画面入力の場合、新規モード時は修正可とします。
            //element.findP("no_seq").prop("readonly", false).prop("tabindex", false);

            //element.validation().validate({
            //    state: {
            //        suppressMessage: true
            //    }
            //});

        } else {
            page.detailInput.memberData = 0;
            page.detailInput.dataMemberOriginal = $.extend(true, [], result);

            page.detailInput.bindMember(result, false);

            $("#editItem, #deleteItem, #clearEdit").show();
            $("#addItem, #clearDetail, #clearInput").hide();
            element.find(".input-mode-edit").prop("readonly", true).prop("disabled", true);
            //TODO:キー項目が画面入力の場合、編集モード時は修正不可とします。
            //element.findP("no_seq").prop("readonly", true).prop("tabindex", -1);
            page.loadMasterDataBusho(data.cd_kaisha, data.cd_busho, data.cd_kengen, ".detailInput");

            if (data.kbn_eigyo != App.settings.app.kbn_eigyo.eigyo_nashi) {
                $(".add-user").prop("disabled", true);
            } else {
                $(".add-user").prop("disabled", false);
            }

            setTimeout(function () {
                element.validation().validate();
            }, 300);
        }

        //TODO:パーツの表示・非表示処理を記述します。
        page.header.element.hide();
        page.detail.element.hide();
        element.show();
        //element.find(":input:not([readonly]):first").focus();
        //page.detailInput.element.find(".previous a").focus();

        page.controlEditGamen();

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
                page.detailInput.values.isChange = false;

                if (!App.isUndefOrNull(page.detail.firstViewRow)) {
                    page.detail.dataTable.dataTable("scrollTop", page.detail.firstViewRow, function () { });
                }

                $("#addItem").hide();
                $("#addItem").prop("disabled", true);
                $("#editItem").prop("disabled", true).hide();
                $("#deleteItem").hide();

                page.detailInput.element.find(".control-required").removeClass("control-required").addClass("control-success");
                page.detailInput.element.find(".control-required-label").removeClass("control-required-label").addClass("control-success-label");

                $("#clearInput, #clearEdit").hide();
                $("#clearDetail").show();

                //renew data member
                dataSet = App.ui.page.dataSet();
                page.detailInput.dataMember = dataSet;
                page.detailInput.flagAddChange = false;
                App.ui.page.notifyAlert.clear();

                $(window).resize();
                // Recovery header validations
                page.header.validator.validate();
            };

        //check exit change data tanto kaisha
        if (page.detailInput.dataMember) {
            detailMember = page.detailInput.dataMember.getChangeSet();
            if (detailMember.created.length || detailMember.updated.length || detailMember.deleted.length) {
                closeMessage.text = App.messages.base.exit;
            }
        }

        if (page.detailInput.data) {
            data = page.detailInput.data.getChangeSet();
            if (data.updated.length || data.deleted.length) {
                closeMessage.text = App.messages.base.exit;
            } else {
                if (page.detailInput.flagAddChange) {
                    closeMessage.text = App.messages.base.exit;
                }
            }
        }
        //if (page.detailInput.data.isChanged()) {
        //    closeMessage.text = App.messages.base.exit;
        //}

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
    page.detailInput.bind = function (data, isExistedUserData) {

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

        page.detailInput.element.form(page.detailInput.options.bindOption).bind(setData);
        if (isExistedUserData && !App.isUndefOrNullOrStrEmpty(data.cd_kaisha)) {
            page.loadMasterDataBusho(data.cd_kaisha, data.cd_busho, data.cd_kengen, page.detailInput.element).always(function () {
                page.detailInput.validator.validate();
            });
        }
    };

    /**
    * 担当製造会社のデータバインド処理を行います
    */
    page.detailInput.bindMember = function (result, isNewData) {
        var element = page.detailInput.element,
            l = result.length;

        element.find(".tanto_row").remove();
        element.findP("nm_member").val("");

        dataSet = App.ui.page.dataSet();
        page.detailInput.dataMember = dataSet;

        var tantoArea = element.find(".tanto-area");

        for (i = 0; i < result.length; i++) {
            for (j = i + 1; j < result.length; j++) {
                if (result[j].no_sort < result[i].no_sort) {

                    var tmp = result[i];
                    result[i] = result[j];
                    result[j] = tmp;
                }
            }
        }

        for (var i = 0 ; i < l; i++) {

            var temp = element.find(".tanto-tmpl").clone(),
                item = $.extend(true, {}, result[i]);

            temp.appendTo(tantoArea).removeClass("tanto-tmpl").addClass("tanto_row").show();

            (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
            temp.form().bind(item);
        }
    };

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

        page.detailInput.values.isChange = false;

        element.find("input[type='checkbox']").prop("checked", false);
        element.find("select").val("");
        element.find("input[type='text']").val("");
        element.find("input[type='tel']").val("");
        element.find("input[type='password']").val("");
        element.findP("cd_busho").children().remove();
        page.detailInput.memberData = 0;

        $(".add-user").prop("disabled", true);

        //reload data combobox
        page.loadMasterData(".detailInput").then(function () {
            page.detailInput.bind(page.detailInput.dataUserOriginal);
            page.detailInput.bindMember([], false);

            //element.validation().validate({
            //    state: {
            //        suppressMessage: true
            //    }
            //});
        }).always(function () {
            $("#addItem").prop("disabled", true);
        })
    }


    /**
    * 担当製造会社のデータ子目を新規する。
    */
    page.detailInput.addMember = function (result, isNewData, id_user) {
        App.ui.loading.show();

        setTimeout(function () {
            var element = page.detailInput.element,
                l = result.length,
                no_sort = 1,
                dataOriginal = page.detailInput.dataMemberOriginal;

            no_sort += page.detailInput.dataMemberOriginal.length + page.detailInput.dataMember.getChangeSet().created.length + page.detailInput.dataMember.getChangeSet().deleted.length + page.detailInput.memberData;

            var tantoArea = element.find(".tanto-area");

            for (var i = 0 ; i < l; i++) {
                result[i].id_member = result[i].id_user;
                result[i].id_user = id_user;
                result[i].nm_member = result[i].nm_user;
                result[i].no_sort = no_sort++;
                var temp = element.find(".tanto-tmpl").clone(),
                    item = $.extend(true, {}, result[i]);

                temp.appendTo(tantoArea).removeClass("tanto-tmpl").addClass("tanto_row").show();

                (isNewData ? page.detailInput.dataMember.add : page.detailInput.dataMember.attach).bind(dataSet)(item);
                temp.form().bind(item);
            }

            //create changset user when change
            //if (!page.detailInput.dataMember.isChanged()) {
            //    element.findP("nm_user").change();
            //}

            App.ui.loading.close();
        }, 1)
    };

    /**
    * 担当製造会社のデータ子目を削除する。
    */
    page.detailInput.delMember = function (e) {
        var element = page.detailInput.element;
        //本人のみ(氏名、パスワード)
        //2：製法作成支援(開発)
        //３：製法作成支援(工場)

        if (element.find(".add-user").is(":visible:disabled")) {
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

        entity = page.detailInput.dataMember.entry(id);
        page.detailInput.dataMember.remove(entity);

        target.remove();
        page.detailInput.memberData++;
        //create changset user when change tantokaisha
        if (!page.detailInput.data.isChanged()) {
            page.detailInput.element.findP("nm_user").change();
        }
    };

    /**
     * delete all kaisha tanto
     */
    page.detailInput.deleteMember = function () {

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

    page.detailInput.checkExistUser = function (id_user) {
        var numIDUser = Number(id_user);
        if (App.isUndefOrNullOrStrEmpty(id_user) || isNaN(numIDUser)) {
            return App.async.success();
        }
        return $.ajax(App.ajax.odata.get(App.str.format(page.urls.ma_user, numIDUser)), {}, { async: false }).then(function (result) {
            if (result && result.value.length) {
                var text = App.str.format(App.messages.app.AP0157, "社員", "担当者マスタ又は、担当者マスタ（営業）");
                page.infoDialog.confirm({
                    text: text,
                    hideCancel: true
                }).always(function () {
                    var userData = {
                        id_user: "",
                        password: "",
                        cd_kengen: "",
                        nm_user: "",
                        cd_kaisha: "",
                        cd_busho: ""
                    };
                    page.detailInput.loadUser(userData, true);
                    var element = page.detailInput.element;
                    element.findP("cd_kaisha").prop("disabled", false);
                    element.findP("cd_busho").prop("disabled", false);
                    element.findP("cd_kengen").prop("disabled", false);
                    //page.detailInput.displayProcess();
                });
                page.detailInput.flagAddChange = false;
                return App.async.success(false);
            }
            return App.async.success(true);
        }).fail(function (error) {
            if (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            }
            return App.async.success(true);
        });
    }

    page.detailInput.displayProcess = function () {
        var element = page.detailInput.element,
            target = element.findP("cd_kengen");
        //-- Disable [所属会社], [所属部署] 
        if (page.detailInput.options.mode == page.detailInput.mode.input) {
        }
    }

    page.detailInput.loadUser = function (userData, isExistedUserData) {
        var element = page.detailInput.element,
            options = {
                filter: page.detailInput.validationFilter
            };
        if (!App.isUndefOrNull(userData)) {

            if (userData.kbn_eigyo == null) {
                element.find(".add-user").prop("disabled", true);
                element.findP("cd_kengen").prop("disabled", true);
                element.findP("cd_kaisha").prop("disabled", true);
                element.findP("cd_busho").prop("disabled", true);
            }
            else if (userData.kbn_eigyo != App.settings.app.kbn_eigyo.eigyo_nashi) {
                $(".add-user").prop("disabled", true);
            }
            else {
                element.find(".add-user").prop("disabled", false);
                element.findP("cd_kengen").prop("disabled", false);
                element.findP("cd_kaisha").prop("disabled", false);
                element.findP("cd_busho").prop("disabled", false);
            }

            element.find("input[type='password']").val("");

            page.detailInput.bind(userData, isExistedUserData);
            page.loadMasterDataBusho(userData.cd_kaisha, userData.cd_busho, userData.cd_kengen, ".detailInput");

        }
        else {
            element.find("select").val("");
            element.find("input").not(".input-mode-edit").val("");
            element.findP("cd_kengen").prop("disabled", false);
            element.findP("cd_kaisha").prop("disabled", false);
            element.findP("cd_busho").prop("disabled", false);
            element.find(".add-user").prop("disabled", true);
        }
        //page.detailInput.executeValidation(element.find("select, input").not(".input-mode-edit"), options);
        page.detailInput.executeValidation(element.find("select, input"), options);
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
            data = element.form().data(),
            options = {
                filter: page.detailInput.validationFilter
            };
        page.detailInput.flagAddChange = true;
        var state = page.detailInput.data.entries[entity.__id].state;
        // 入力項目が削除済みの場合、処理を実行しない
        if (!App.isUndefOrNull(state)
            && state === App.ui.page.dataSet.status.Deleted) {
            App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
            return;
        }

        //page.values.isChangeInputRunning[property] = true;

        if (property == "id_user") {
            $(".tanto-area div").not("div:first").remove();
            page.detailInput.checkExistUser(target.val()).then(function (isContinue) {
                if (!isContinue) {
                    return;
                }
                App.ui.page.notifyAlert.clear();
                dataSet = App.ui.page.dataSet();
                page.detailInput.dataMember = dataSet;
                page.detailInput.memberData = 0;
                //deleted data member
                $.each(page.detailInput.dataMember, function (index, value) {
                    $.each(value, function (index, res) {
                        page.detailInput.dataMember.remove(res);
                    });
                    return false;
                });

                if (!App.isUndefOrNull(data.id_user)) {

                    page.values.isChangeInputRunning[property] = true;
                    $.ajax(App.ajax.webapi.get(page.urls.mausernew, { id_user: data.id_user })).then(function (result) {
                        // Load user data in ma_user_new
                        page.detailInput.loadUser(result);

                    }).fail(function (error) {
                        App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                    }).always(function () {
                        delete page.values.isChangeInputRunning[property];
                    });
                } else {
                    element.find("select").val("");
                    element.find("input").not(".input-mode-edit").val("");

                    element.findP("cd_kengen").prop("disabled", false);
                    element.findP("cd_kaisha").prop("disabled", false);
                    element.findP("cd_busho").prop("disabled", false);
                    element.find(".add-user").prop("disabled", true);

                    page.detailInput.executeValidation(element.find("select, input"), options);
                    //page.detailInput.displayProcess();

                    //delete page.values.isChangeInputRunning[property];
                }
            }).fail(function () {
                //page.detailInput.executeValidation(element.find("select, input").not(".input-mode-edit"), options);
            });

        } else if (property == "cd_kengen") {

            element.findP("cd_busho").children().remove();
            if (data.cd_kengen != null) {
                var kbn_eigyoval = element.findP("cd_kengen").find("option:selected").attr("kbn_eigyoval");

                if (kbn_eigyoval != App.settings.app.kbn_eigyo.eigyo_nashi) {
                    $(".add-user").prop("disabled", true);
                } else {
                    $(".add-user").prop("disabled", false);
                }

            } else {
                element.find(".add-user").prop("disabled", true);
            }
            
            //delete page.values.isChangeInputRunning[property];
        } else {
            //delete page.values.isChangeInputRunning[property];
        }

        if (property == "cd_kaisha") {

            page.loadMasterDataBusho(data.cd_kaisha, data.cd_busho, data.cd_kengen, ".detailInput");
        }

        //page.values.isChangeRunning[property] = true;

        page.detailInput.values.isChange = true;

        //var sleep = 0;
        //var condition = "Object.keys(page.values.isChangeInputRunning).length == 0";
        //App.ui.wait(sleep, condition, 100)
        //.then(function () {

        page.detailInput.validator.validate({
            targets: target
        }).then(function () {

            entity[property] = data[property];

            //update date change password
            //if (property == "password") {
            //    entity["dt_password"] = null;
            //}

            //convert data id_user
            if (property == "id_user" && data.id_user >= 0) {
                var new_id_user = ("0000000000" + data.id_user).slice(-10);

                target.val(new_id_user);
            };

            if (property == "cd_kengen") {
                element.findP("cd_busho").children().remove();
                page.loadMasterDataBusho(data.cd_kaisha, null, data.cd_kengen, ".detailInput");
            }

            //page.detailInput.executeValidation(element.find(":input").not("input[type=password]"), options);


            if (page.detailInput.options.mode === page.detailInput.mode.input) {
                if ($("#addItem").is(":visible:disabled")) {
                    $("#addItem").prop("disabled", false);
                }
            } else {
                if ($("#editItem").is(":visible:disabled")) {
                    $("#editItem").prop("disabled", false);
                }
            }
            if (property == "nm_user") {
                entity[property] = $(".user_detail").val();
            }
            page.detailInput.data.update(entity);

        }).always(function () {
            //delete page.values.isChangeRunning[property];
        });
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
            dialog.modal("show");//時にoption指定
            //dialog.modal({
            //    show: true,
            //    backdrop: _backdrop,
            //    keyboard: _keyboard
            //});
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
                </div>

                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>ユーザーID</label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="tel" data-prop="id_user" class="number-right number limit-input-digit" maxlength="10" />
                    </div>

                    <div class="control-label col-xs-1">
                        <label>担当者名</label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="text" data-prop="nm_user" />
                    </div>

                    <div class="control-label col-xs-1">
                        <label>所属会社</label>
                    </div>
                    <div class="control col-xs-2">
                        <select data-prop="cd_kaisha"></select>
                    </div>

                    <div class="control-label col-xs-1">
                        <label>所属部署</label>
                    </div>
                    <div class="control col-xs-2">
                        <select data-prop="cd_busho"></select>
                    </div>
                </div>

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
                    <button type="button" class="btn btn-default btn-xs" id="add-item">新規</button>
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
                        <th style="width: 70px;">詳細</th>
                        <th style="width: 90px;">ユーザーID</th>
                        <th style="width: 300px;">担当者名</th>
                        <th style="width: 210px;">所属会社</th>
                        <th style="width: 210px;">所属部署</th>
                        <th style="width: 60px;">本部権限</th>
                        <th style="">共有メンバー</th>

                    </tr>
                </thead>
                <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->
                <tbody class="item-tmpl" style="cursor: default; display: none;">
                    <tr>
                        <!--TODO: 単一行を作成する場合は、以下を利用します。-->
                        <td>
                            <span class="select-tab unselected"></span>
                        </td>
                        <%--<td rowspan="2">
                                <span class="select-tab-2lines unselected"></span>
                            </td>--%>

                        <td class="text-center">
                            <a href="#" class="edit-select btn btn-info btn-xs">詳細</a>
                        </td>
                        <td>
                            <span data-prop="id_user" class="number-right number"></span>
                        </td>
                        <td>
                            <label data-prop="nm_user"></label>
                        </td>
                        <td>
                            <span data-prop="nm_kaisha"></span>
                        </td>
                        <td>
                            <span data-prop="nm_busho"></span>
                        </td>
                        <td>
                            <span data-prop="kbn_eigyo"></span>
                        </td>
                        <td>
                            <span data-prop="nm_member"></span>
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

            <%--<div title="TODO: 入力項目部のタイトルを設定します。" class="part">--%>
            <div class="row">

                <div class="control-label col-xs-3">
                    <label class="item-name">社員番号 <span class="required">*</span></label>
                </div>
                <div class="control col-xs-2">
                    <input type="tel" data-prop="id_user" style="width: 98%;" class="input-mode-edit number-right number limit-input-digit" maxlength="10" />
                </div>
                <div class="control col-xs-7">
                </div>
            </div>

            <div class="row">
                <div class="control-label col-xs-3">
                    <label class="item-name">パスワード <span class="required">*</span></label>
                </div>
                <div class="control col-xs-2">
                    <input type="password" data-prop="password" style="width: 98%; border: 1px solid #cccccc; border-radius: 2px; margin: 1px; padding: 1px; display: inline-block; height: 26px; font-family: Meiryo, MS PGothic, Segoe UI;" />
                </div>
                <div class="control col-xs-7">
                </div>
            </div>

            <div class="row">
                <div class="control-label col-xs-3">
                    <label class="item-name">権限 <span class="required">*</span></label>
                </div>
                <div class="control col-xs-2">
                    <select data-prop="cd_kengen"></select>
                </div>
                <div class="control col-xs-7">
                </div>
            </div>

            <div class="row">
                <div class="control-label col-xs-3">
                    <label>氏名 <span class="required">*</span></label>
                </div>
                <div class="control col-xs-2">
                    <input type="text" data-prop="nm_user" class="user_detail" style="width: 98%;" />
                </div>
                <div class="control col-xs-7">
                </div>
            </div>

            <div class="row">
                <div class="control-label col-xs-3">
                    <label class="item-name">所属会社 <span class="required">*</span></label>
                </div>
                <div class="control col-xs-2">
                    <select data-prop="cd_kaisha" class="input-mode-edit"></select>
                </div>
                <div class="control col-xs-7">
                </div>
            </div>
            <div class="row">
                <div class="control-label col-xs-3">
                    <label class="item-name">所属部署 <span class="required">*</span></label>
                </div>
                <div class="control col-xs-2">
                    <select data-prop="cd_busho"></select>
                </div>
                <div class="control col-xs-7">
                </div>
            </div>

            <div class="row">
                <div class="control-label col-xs-3" style="height: 93px !important">
                    <label>共有メンバー</label>
                </div>
                <div class="control col-xs-9" style="height: 93px !important">
                    <div class="tanto-area" style="height: 90px!important; overflow-y: scroll; width: 23.1%; float: left;">
                        <div style="position: relative;" class="tanto-tmpl">
                            <input type="text" data-prop="nm_member" class="user_member" style="width: 98%;" disabled="disabled" />
                            <span class="close-input" style="cursor: pointer; position: absolute; right: 13px; top: 5px; color: gray;">X</span>
                        </div>
                    </div>
                    <button class="btn btn-xs btn-primary add-user">追加</button>
                </div>
            </div>

            <%--</div> TODO div--%>
        </div>
    </div>

</asp:Content>

<asp:Content ID="FooterContent" ContentPlaceHolderID="FooterContent" runat="server">
    <div class="command-left">
        <!--TODO: コマンドボタン（左寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" id="clearInput" class="btn btn-sm btn-primary" style="display: none">クリア</button>
        <button type="button" id="clearEdit" class="btn btn-sm btn-primary" style="display: none">クリア</button>
    </div>
    <div class="command">
        <!--TODO: コマンドボタン（右寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" id="addItem" class="btn btn-sm btn-primary" style="display: none;" disabled="disabled">保存</button>
        <button type="button" id="editItem" class="btn btn-sm btn-primary" disabled="disabled" style="display: none;">保存</button>
        <button type="button" id="deleteItem" class="btn btn-sm btn-default" style="display: none;">削除</button>
    </div>

</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container">
        <div class="modal fade confirm" tabindex="-1" id="InfoDialog" data-backdrop="static" data-keyboard="false">
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
