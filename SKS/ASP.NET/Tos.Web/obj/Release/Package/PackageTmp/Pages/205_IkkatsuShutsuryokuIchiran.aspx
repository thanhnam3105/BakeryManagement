<%@ Page Title="205_一括出力一覧" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="_205_IkkatsuShutsuryokuIchiran.aspx.cs" Inherits="Tos.Web.Pages._205_IkkatsuShutsuryokuIchiran" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchInputDetail(Ver2.1)】 Template--%>

<asp:Content ID="IncludeContent" ContentPlaceHolderID="IncludeContent" runat="server">

    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/part.css") %>" type="text/css" />
    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/headerdetail.css") %>" type="text/css" />

    <script src="<%=ResolveUrl("~/Scripts/jquery.ui.monthpicker.js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Scripts/jquery.ui.datepicker-ja.js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Scripts/jquery.ui.monthpicker-ja.js") %>" type="text/javascript"></script>
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
        input[type="radio"], input[type="checkbox"] {
            margin-top:0px;
        }
        /*table.datatable tr td {
           height: 31px!important;
       }
       table.datatable tr {
           height: auto!important;
       }*/
    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("_205_IkkatsuShutsuryokuIchiran", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.dataTakeCount,   // TODO:取得するデータ数を指定します。
                filter: ""
            },
            values: {
                isChangeRunning: {},
                id_kino: 10,
                id_data: 9
            },
            urls: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
                openFolder: "../api/_205_IkkatsuShutsuryokuIchiran/GetFolder",
                mainMenu: "MainMenu.aspx"
            },
            header: {
                options: {},
                values: {},
                urls: {
                    ikkatsu: "../api/_205_IkkatsuShutsuryokuIchiran/Get"
                },
                mode: {
                    // 新規表示
                    input: "input"
                }
            },
            detail: {
                options: {},
                values: {},
                urls: {
                    downcsv: "../api/_205_IkkatsuShutsuryokuIchiranCSVDownload/Get",
                    urltoroku: "/Pages/206_IkkatsuShutsuryokuToroku.aspx"
                }
            },
            detailInput: {
                options: {},
                values: {},
                mode: {
                    input: "input",
                    edit: "edit",
                    view: "view",
                    "new": "new"
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

            page.initializeControl();
            page.initializeControlEvent();

            page.header.initialize();
            page.detail.initialize();
            page.detailInput.initialize();

            //TODO: ヘッダー/明細以外の初期化の処理を記述します。
            $("#downcsv").prop("disabled", true);

            page.loadMasterData().then(function (result) {

                return page.loadDialogs();
            }).then(function (result) {
                //TODO: 画面の初期化処理成功時の処理を記述します。
                getKengenGamen(App.settings.app.id_gamen.ikkatsu_shutsuryoku_ichiran).then(function (results) {
                    page.kengenGamen = results;
                    var isRoleScreen = false;
                    $.each(results, function (index, item) {
                        if (item.id_kino == page.values.id_kino && item.id_data == page.values.id_data) {
                            isRoleScreen = true;
                        }
                    });

                    if (!isRoleScreen) {
                        //var options = {
                        //    text: App.messages.app.AP0089
                        //};

                        //page.dialogs.confirmDialog.confirm(options)
                        //.always(function () {
                            window.location.href = page.urls.mainMenu;
                        //});
                    }
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

            //TODO: 画面全体で利用するコントロールのイベントの紐づけ処理をここに記述します。
            $("#addItem").on("click", page.commands.addItem);
            $("#editItem").on("click", page.commands.editItem);
            $("#deleteItem").on("click", page.commands.deleteItem);
            $("#clear").on("click", page.detail.clear);
            $("#downcsv").on("click", page.commands.downcsv);
        };
        /**
       * CSV出力を行います。(Download用フォルダに作成されたCSVファイルにアクセス)
       */
        page.commands.downcsv = function () {
            var seq_check, no_ikkatsu, element, id, entity;
            element = page.detail.element;

            App.ui.page.notifyAlert.clear();

            page.header.validator.validate().done(function () {

                seq_check = element.find(".checked-row:checked");

                if (!seq_check.length) {
                    App.ui.page.notifyAlert.message(App.messages.app.AP0008, "AP0008").show();
                    return;
                }
                //Get row check and check quyen
                var checkAu = false;
                var isCheck, no_ikkatsu_list = [];
                $.each(seq_check, function (index, item) {
                    isCheck = element.find(item).closest("tbody");
                    
                    if (isCheck.findP("kbn_kaihatsu_kaisha").text() == App.settings.app.kbn_kaihatsu_kaisha.jisha_nomi) {
                        page.detail.dataTable.dataTable("getRow", isCheck, function (rowObject) {
                            row = rowObject.element;
                            no_ikkatsu_list.push(row.findP("no_ikkatsu").text());
                        });
                    }
                    else if (isCheck.findP("kbn_kaihatsu_kaisha").text() == App.settings.app.kbn_kaihatsu_kaisha.zensha) {
                        if (App.ui.page.user.flg_kaishakan_sansyo) {
                            page.detail.dataTable.dataTable("getRow", isCheck, function (rowObject) {
                                row = rowObject.element;
                                no_ikkatsu_list.push(row.findP("no_ikkatsu").text());
                            });
                        }
                        else {
                            App.ui.page.notifyAlert.message(App.messages.app.AP0105, "AP0105").show();
                            checkAu = true;
                        }
                    }

                });
                if (checkAu) {
                    return;
                }
                var query = {
                    url: page.detail.urls.downcsv,
                    options: no_ikkatsu_list
                };
                // CSV出力（ファイルStreamでダウンロード）
                return $.ajax(App.ajax.file.download(App.data.toODataFormat(query), { options: no_ikkatsu_list })).then(function (response, status, xhr) {
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
        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function () {

            //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
            //return $.ajax(App.ajax.odata.get(/* マスターデータ取得サービスの URL */)).then(function (result) {

            //    var cd_shiharai = $(".header, .detailInput").findP("cd_shiharai");
            //    cd_shiharai.children().remove();
            //    App.ui.appendOptions(
            //        cd_shiharai,
            //        "cd_shiharai",
            //        "nm_joken_shiharai",
            //        result.value,
            //        true
            //    );
            //});

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
        page.header.options.validations = {
            //TODO: 画面ヘッダーのバリデーションの定義を記述します。
            nm_user: {
                rules: {
                    maxlength: 60
                },
                options: {
                    name: "登録担当者"
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
            dt_from: {
                rules: {
                    datestring: true,
                    lessthan_dt_to: true
                },
                options: {
                    name: "登録日(開始)"
                },
                messages: {
                    datestring: App.messages.base.datestring,
                    lessthan_dt_to: App.messages.base.MS0014
                }
            },
            dt_to: {
                rules: {
                    datestring: true,
                },
                options: {
                    name: "登録日(終了)",
                },
                messages: {
                    datestring: App.messages.base.datestring
                }
            }

        };
        App.validation.addMethod("lessthan_dt_to", function (value, opts, state, done) {
            var data = page.header.element.form().data();

            if (!(/^[0-9]{4}\/[0-9]{2}\/[0-9]{2}$/.test(page.header.element.findP("dt_from").val()))) {
                return done(true);
            }

            if (!(/^[0-9]{4}\/[0-9]{2}\/[0-9]{2}$/.test(page.header.element.findP("dt_to").val()))) {
                return done(true);
            }

            if (App.isUndefOrNull(data.dt_to) || App.isUndefOrNull(data.dt_from)) {
                return done(true);
            }

            if (data.dt_to < data.dt_from) {
                $("#NumberOfDate").text("");
                return done(false);
            }
            return done(true);
        });
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

            var dates = element.find("input[data-role='date']");
            dates.keydown(page.disabledEnterForPicker);
            dates.focus(function () {
                if (page.values.isMonth !== false) {
                    page.values.isMonth = false;
                    $("#ui-datepicker-div").remove();
                    dates.datepicker({ dateFormat: "yy/mm/dd", changeYear: true, yearRange: '-100:+10', });
                    $(this).datepicker("show");
                }
            })
            element.find(":input[data-role='date']").on("keyup", App.data.addSlashForDateString);
        };
        /**
         * 画面ヘッダーの変更時処理を定義します。
         */
        page.header.change = function () {
            var element = page.header.element;

            element.validation().validate().done(function () {

                if ($("#nextsearch").hasClass("show-search")) {
                    $("#nextsearch").removeClass("show-search").hide();
                    App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
                }
                else if (page.detail.searchData) {
                    // 保持検索データの消去
                    page.detail.searchData = undefined;
                    App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
                }
            });
        };
        /**
         * clear header and detail
         */
        page.detail.clear = function () {
            //remove all messenger
            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            var element = page.header.element;
            //clear header
            element.find("[data-prop]:not([data-prop='seq_check'])").val("").text("");
            element.find(".control-required").removeClass("control-required").addClass("control-success");
            element.find(".control-required-label").removeClass("control-required-label").addClass("control-success-label");
            element.find(".check_dsc").prop("checked", true);
            element.find(".check_asc").prop("checked", false);
            //clear detail
            page.options.skip = 0;
            page.detail.dataTable.dataTable("clear");
            page.detail.element.findP("data_count").text("");
            page.detail.element.findP("data_count_total").text("");
            $("#nextsearch").removeClass("show-search").hide();
            //footer
            $("#downcsv").prop("disabled", true);
        };
        /**
         * 検索処理を定義します。
         */
        page.header.search = function (isLoading) {

            var deferred = $.Deferred(),
                param;

            //App.ui.page.notifyAlert.clear();

            page.header.validator.validate().done(function () {

                page.options.skip = 0;
                page.options.filter = page.header.createFilter();

                //TODO: データ取得サービスの URLとオプションを記述します。

                if (isLoading) {
                    App.ui.loading.show();
                    App.ui.page.notifyAlert.clear();
                }
                return $.ajax(App.ajax.webapi.get(page.header.urls.ikkatsu, page.options.filter)).done(function (result) {

                    // パーツ開閉の判断
                    //if (result = 0) {
                    //    App.ui.page.notifyInfo.message(App.messages.app.AP007).show();
                    //}
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
         * 検索条件のフィルターを定義します。
         */
        page.header.createFilter = function () {
            var criteria = page.header.element.form().data(),
                filters = {};
            /* TODO: 検索条件のフィルターを定義してください。*/
            if (!App.isUndefOrNull(criteria.nm_user)) {
                filters.nm_user = criteria.nm_user;
            }
            if (!App.isUndefOrNull(criteria.dt_from)) {
                filters.dt_from = App.data.getDateString(new Date(criteria.dt_from), true);
            } else {
                filters.dt_from = null;
            }
            if (!App.isUndefOrNull(criteria.dt_to)) {
                filters.dt_to = App.data.getDateString(new Date(criteria.dt_to), true);
            } else {
                filters.dt_to = null;
            }

            if (!App.isUndefOrNull(criteria.seq_check)) {
                filters.seq_check = criteria.seq_check;
            }


            return filters;
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
                    fixedColumn: true,                //列固定の指定
                    fixedColumns: 12,                  //固定位置を指定（左端を0としてカウント）
                    innerWidth: 500,                 //可動列の合計幅を指定
                    onselect: page.detail.select
                });
            table = element.find(".datatable");         //列固定にした場合DOM要素が再作成されるため、変数を再取得

            page.detail.element = element;
            page.detail.dataTable = datatable;

            element.on("click", "#nextsearch", page.detail.nextsearch);
            element.on("click", "#add-item", page.detail.addNewItem);
            element.on("click", ".checked-row", page.detail.change);

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
            page.detail.fixedColumnIndex = element.find(".fix-columns").length;
            // 明細パートクローズ時の処理を指定します
            element.find(".part").on("collapsed.aw.part", function () {
                page.detail.isClose = true;
            });

            //TODO: 画面明細の初期化処理をここに記述します。
            //TODO: 画面明細で利用するコントロールのイベントの紐づけ処理をここに記述します。
            table.on("click", ".edit-select", page.detail.showEditPart);
            table.on("click", ".open-folder", page.detail.openFolder);

            //TODO: 行選択による詳細画面の遷移を行う際は、上の行をコメント化し下の行のコメントを解除します。
            //table.on("click", "tbody", page.detail.showEditPart);

        };

        /**
         * 次のレコードを検索する処理を定義します。
         */
        page.detail.nextsearch = function () {

            page.options.filter.skip = page.options.skip;

            App.ui.loading.show();
            App.ui.page.notifyAlert.clear();
            return $.ajax(App.ajax.webapi.get(page.header.urls.ikkatsu, page.options.filter)).done(function (result) {
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
                flg_01: function (value, element) {
                    element.prop("checked", value ? 1 : 0);
                    return true;
                },
                flg_19: function (value, element) {
                    element.prop("checked", value ? 1 : 0);
                    return true;
                },
                flg_20: function (value, element) {
                    element.prop("checked", value ? 1 : 0);
                    return true;
                },
                flg_21: function (value, element) {
                    element.prop("checked", value ? 1 : 0);
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
            if (data.length) {
                $("#downcsv").prop("disabled", false);
            } else {
                $("#downcsv").prop("disabled", true);
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

            var urltoroku = page.detail.urls.urltoroku;

            page.detail.dataTable.dataTable("getFirstViewRow", function (row) {
                page.detail.firstViewRow = row;
            });
            parameter = {
                // cd_user: App.ui.page.user.EmployeeCD,
                mode: page.detailInput.mode.new
            }
            //TODO:新規データおよび初期値を設定する処理を記述します。
            var newData = {
                //no_seq: page.values.no_seq
            };
            //  page.detailInput.options.mode = page.detailInput.mode.input;
            //  page.detailInput.show(newData);

            var baseUrl = '<%=ResolveUrl("~/") %>';
            url = urltoroku + "?" + $.param(parameter),

            url = baseUrl + url.substring(1, url.length);
            //一括出力登録画面対応　st
            window.open(url);
            //sessionStorage.setItem("menuDirectLink", url)
            //window.open("MainMenu.aspx?directLink=true", "_blank");
            //一括出力登録画面対応　end

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
                check_authority = false;
            //Check authority
            if (entity.kbn_kaihatsu_kaisha == App.settings.app.kbn_kaihatsu_kaisha.zensha) {
                if (!App.ui.page.user.flg_kaishakan_sansyo) {
                    App.ui.page.notifyAlert.remove("AP0105");
                    App.ui.page.notifyAlert.message(App.messages.app.AP0105, "AP0105").show();
                    check_authority = true;
                }
            };
            if (check_authority) {
                return;
            }
            //Move page
            var urltoroku = page.detail.urls.urltoroku;
            page.detail.dataTable.dataTable("getFirstViewRow", function (row) {
                page.detail.firstViewRow = row;
            });
            parameter = {
                no_ikkatsu: entity.no_ikkatsu,
                mode: page.detailInput.mode.view
            }
            var baseUrl = '<%=ResolveUrl("~/") %>';
            url = urltoroku + "?" + $.param(parameter);

            url = baseUrl + url.substring(1, url.length);
            //一括出力登録画面対応　st
            window.open(url);
            //sessionStorage.setItem("menuDirectLink", url)
            //window.open("MainMenu.aspx?directLink=true", "_blank");
            //一括出力登録画面対応　end

        };

        /**
         * 画面明細の一覧から編集画面を表示します。
         */
        page.detail.openFolder = function (e) {
            var element = page.detail.element,
                target = $(e.target),
                row = target.closest("tbody"),
                id = row.attr("data-key"),
                entity = page.detail.data.entry(id),
                check_authority = false;

            App.ui.page.notifyAlert.clear();

            //Check authority
            if (entity.kbn_kaihatsu_kaisha == App.settings.app.kbn_kaihatsu_kaisha.zensha) {
                if (!App.ui.page.user.flg_kaishakan_sansyo) {
                    App.ui.page.notifyAlert.message(App.messages.app.AP0105, "AP0105").show();
                    check_authority = true;
                }
            };
            if (check_authority) {
                return;
            }
            return $.ajax(App.ajax.webapi.get(page.urls.openFolder, { no_ikkatsu: entity.no_ikkatsu }))
                .then(function (result) {
                    if (result.error == true) {
                        App.ui.page.notifyAlert.message(App.messages.app.AP0106.replace("%s", result.path), "AP0106").show();
                    } else {
                        var path = result.path;
                        window.open(path);
                    }
                });
        };
        /**
         * 画面明細の一覧から編集画面を表示します。
         */
        page.detail.change = function (e) {
            var element = page.detail.element,
                target = $(e.target),
                row = target.closest("tbody"),
                id = row.attr("data-key"),
                entity = page.detail.data.entry(id),
                checked = $(target).is(":checked");
                
            $.each(element.find(".checked-row"), function (ind, val) {
                $(val).prop("checked", false);
            });

            $(target).prop("checked", checked);

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

    <div class="content-wrap">
        <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
        <div class="header">
            <div title="検索条件" class="part">
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>登録担当者</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="text" style="width: 280px" class="ime-active" data-prop="nm_user" />
                    </div>

                    <div class="control-label col-xs-1">
                        <label>登録日</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="text" style="width: 120px" data-role="date" class="ime-active text-selectAll " data-prop="dt_from" />
                        ~
                        <input type="text" style="width: 120px" data-role="date" class="ime-active text-selectAll "  data-prop="dt_to" />
                    </div>
                    <div class="control-label col-xs-2">
                        <label>ソート順(登録日)</label>
                    </div>
                    <div class="control col-xs-2 radio" style="margin-top:0px !important;">
                        <label for="check_dsc" style="margin-left:15px;">
                            <input id="check_dsc" class="check_dsc" name="seq_check" data-prop="seq_check" type="radio" value="0" checked ="checked" style="vertical-align:sub;"/>降順
                        </label>
                        <label for="check_asc" style="margin-left:15px;">
                            <input id="check_asc" class="check_asc" name="seq_check" data-prop="seq_check" type="radio" value="1" style="vertical-align:sub;" />昇順
                        </label>
                    </div>
                    <%--<div class="control col-xs-1 ">
                        <label for="check_asc">
                            <input id="check_asc" class="check_asc" name="seq_check" data-prop="seq_check" type="radio" value="1" style="vertical-align:sub;" />昇順
                        </label>
                    </div>--%>
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
                        <th style="width: 30px;"></th>
                        <th style="width: 70px;">詳細</th>
                        <th style="width: 90px">登録日</th>
                        <th style="width: 270px">登録担当者</th>
                        <th style="width: 80px">製法文書</th>
                        <th style="width: 90px">期間開始日</th>
                        <th style="width: 90px">期間終了日</th>
                        <th style="width: 60px;">01本</th>
                        <th style="width: 60px;">19表示</th>
                        <th style="width: 60px;">20試験</th>
                        <th style="width: 60px;">21短期</th>
                        <th style="width: 100px;">開発会社</th>
                        <th style="width: 400px;">メモ</th>
                        <th style="width: 100px;">No</th>

                    </tr>
                </thead>
                <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->
                <tbody class="item-tmpl" style="cursor: default; display: none;">
                    <tr>
                        <!--TODO: 単一行を作成する場合は、以下を利用します。-->
                        <td>
                            <span class="select-tab unselected"></span>
                        </td>
                        <td class="text-center">
                            <input type="checkbox" class="check-box-vertical checked-row" />
                        </td>
                        <td class="text-center">
                            <a href="#" class=" edit-select btn btn-info btn-xs" id="edit-item">詳細</a>
                        </td>
                        <td class="text-left">
                            <label data-prop="dt_toroku"></label>
                        </td>
                        <td class="text-left">
                            <label data-prop="nm_user"></label>
                        </td>
                        <td class="text-center">
                            <a href="#" class=" open-folder btn btn-info btn-xs">参照</a>
                        </td>
                        <td class="text-left">
                            <label data-prop="dt_from" class=""></label>
                        </td>
                        <td class="text-left">
                            <label data-prop="dt_to" class=""></label>
                        </td>
                        <td class="text-center">
                            <input type="checkbox" class="check-box-vertical" data-prop="flg_01" disabled />
                        </td>
                        <td class="text-center">
                            <input type="checkbox" class="check-box-vertical" data-prop="flg_19" disabled />
                        </td>
                        <td class="text-center">
                            <input type="checkbox" class="check-box-vertical" data-prop="flg_20" disabled />
                        </td>
                        <td class="text-center">
                            <input type="checkbox" class="check-box-vertical" data-prop="flg_21" disabled />
                        </td>
                        <td class="text-left">
                            <label data-prop="kbn_kaihatsu_kaisha" class=""></label>
                        </td>
                        <td class="text-left">
                            <label data-prop="memo" class=""></label>
                        </td>
                        <td class="text-right">
                            <label data-prop="no_ikkatsu" class=""></label>
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
        <button type="button" id="downcsv" class="btn btn-sm btn-primary">CSV</button>
    </div>
    <div class="command">
        <!--TODO: コマンドボタン（右寄せ配置）を定義するHTMLをここに記述します。-->
        <%--   <button type="button" id="addItem" class="btn btn-sm btn-primary" style="display: none;">保存</button>
        <button type="button" id="editItem" class="btn btn-sm btn-primary" disabled="disabled" style="display: none;">保存</button>
        <button type="button" id="deleteItem" class="btn btn-sm btn-default" style="display: none;">削除</button>--%>
    </div>

</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>
