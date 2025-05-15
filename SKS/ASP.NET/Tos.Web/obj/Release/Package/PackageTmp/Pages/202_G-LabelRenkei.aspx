<%@ Page Title="202_G-Label連携" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="_202_G_LabelRenkei.aspx.cs" Inherits="Tos.Web.Pages._202_G_LabelRenkei" %>

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
        div .detail-command {
            text-align: center;
        }
    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("_202_G_LabelRenkei", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.dataTakeCount,   // TODO:取得するデータ数を指定します。
            },
            values: {
                isChangeRunning: {},
                no_seiho: ""
            },
            param: {
                GLabelRenkei: "GLabelRenkei"
            },
            urls: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
                search: "../api/_202_G_LabelRenkei/Get",
                kakurei_han: "../api/_202_G_LabelRenkei/Get_kakutei"
            },
            header: {
                options: {},
                values: {},
                urls: {}
            },
            detail: {
                options: {},
                values: {},
                urls: {
                    dropdown: "../api/_202_G_LabelRenkei/Get_dropdown",
                    shisaquick_log: "../api/_202_G_LabelRenkei/Post_LOG",
                    g3_renkei: "../api/_202_G_LabelRenkei/Post_G3",
                    shisaquick_log_delete: "../api/_202_G_LabelRenkei/Post_Delete_LOG",
                    g3_search: "../api/_202_G_LabelRenkei/get_G3"
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

        ///**
        // * データの連携するデータを取得します。
        // */
        page.detail.renkei = function () {

            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            App.ui.loading.show();

            var sleep = 0;
            var condition = "Object.keys(page.values.isChangeRunning).length == 0";

            page.validateAll().then(function () {
                var options = {
                    text: App.messages.app.AP0149,
                    mode: page.param.GLabelRenkei
                };

                page.dialogs.confirmDialog.confirm(options)
                    .then(function () {

                    //    page.validateAll().then(function () {
                    //        var options = {
                    //            text: App.messages.app.AP0150,
                    //            mode: page.param.GLabelRenkei
                    //        };

                    //        page.dialogs.confirmDialog.confirm(options)
                    //.then(function () {

                        //シサクイックのログ削除時に該当のデータを削除するため時間を取得する。
                        var date = new Date(jQuery.now()).toLocaleString(),
                        //ラジオボタンにチェックのついているデータを取得する。
                        target = page.detail.element.find("tbody.new input[type='radio']:checked");
                        id = $(target).closest("tbody").attr("data-key");
                        var param = {
                            condition: [],
                            no_seiho: page.values.no_seiho,
                            date: date
                        };
                        param.condition.push(page.detail.data.entry(id));

                        // ローディング表示
                        //App.ui.loading.show();
                        //App.ui.page.notifyAlert.clear();

                        //シサクイックDBにログを登録。
                        return $.ajax(App.ajax.webapi.post(page.detail.urls.shisaquick_log, param))
                        .then(function (result) {
                            //シサクイックDBにログが登録できたらG3DBに連携処理を実施。
                            if (result == "OK") {

                                return $.ajax(App.ajax.webapi.post(page.detail.urls.g3_renkei, param))
                          .then(function (result) {

                              //G3DBへの連携処理が完了したら処理完了。
                              if (result == "OK") {
                                  App.ui.page.notifyInfo.message(App.messages.app.AP0093).show();

                                  //G3DBのユーザーマスタに登録がない。
                              } else if (result == "LOGIN") {
                                  return $.ajax(App.ajax.webapi.post(page.detail.urls.shisaquick_log_delete, param))
                                  .then(function (result) {
                                      App.ui.page.notifyAlert.message(App.messages.app.AP0131).show();

                                  });

                                  //開発１Lの編集権限がない。
                              } else if (result == "HENSYU") {
                                  return $.ajax(App.ajax.webapi.post(page.detail.urls.shisaquick_log_delete, param))
                                  .then(function (result) {
                                      App.ui.page.notifyAlert.message(App.messages.app.AP0143).show();

                                  });

                                  //G3DBへの連携処理が失敗したらシサクイックDBのログを削除。
                              } else {
                                  return $.ajax(App.ajax.webapi.post(page.detail.urls.shisaquick_log_delete, param))
                                  .then(function (result) {
                                      App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0042, { strName: "連携" })).show();

                                  });
                              };

                          });
                            } else {
                                App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0042, { strName: "連携" })).show();
                            };
                        }).fail(function (error) {

                            //TODO: データの保存失敗時の処理をここに記述します。
                            App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

                        }).always(function () {
                            setTimeout(function () {
                                page.detail.element.find(":input:first").focus();
                            }, 100);
                            App.ui.loading.close();
                        });
                    //});
                    //    });
                    });
                //setTimeout(function () {
                //    page.detail.element.find(":input:first").focus();
                //}, 100);
                //App.ui.loading.close();
            });
            setTimeout(function () {
                page.detail.element.find(":input:first").focus();
            }, 100);
            App.ui.loading.close();
        };


        /**
         * データの削除処理を実行します。
         */
        page.commands.remove = function () {
            var options = {
                text: App.messages.base.MS0003
            };

            page.dialogs.confirmDialog.confirm(options)
            .then(function () {

                App.ui.loading.show();

                var id = page.header.element.attr("data-key"),
                    entity = page.header.data.entry(id),
                    changeSets;

                //TODO: 論理削除フラグを更新する処理をここに記述します。
                //entity.flg_del = true;
                //page.header.data.update(entity);
                changeSets = page.header.data.getChangeSet();

                //TODO: データの削除処理をここに記述します。
                $.ajax(App.ajax.webapi.post(/* TODO: データ削除サービスのURL , */ changeSets))
                .done(function (result) {

                    App.ui.page.notifyAlert.clear();
                    App.ui.page.notifyInfo.clear();

                    //TODO: 親画面にデータ削除通知イベントを発行する処理を記述します
                    //page.options.tabComm.sendToOpener("removeDetailData", entity);
                    App.ui.page.notifyInfo.message(App.messages.base.MS0008).show();

                    setTimeout(function () {
                        //TODO: 画面遷移処置をここに記述します。 
                        //既定の処理では、次画面の新規登録の状態に画面遷移します。
                        $(window).off("beforeunload");
                        var paramIndex = top.location.href.indexOf('?');
                        var shortURL = paramIndex < 0 ? window.location.href : window.location.href.substring(0, paramIndex);
                        window.location = shortURL;
                    }, 100);
                }).fail(function (error) {
                    //TODO: データの削除失敗時の処理をここに記述します。
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

            validations.push(page.header.validator.validate());
            validations.push(page.detail.validateList());

            //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。

            return App.async.all(validations);
        };

        /**
         * Windows閉じる際のイベントを定義します。
         * @return 文字列を返却した場合に確認メッセージが表示されます。
         */
        App.ui.page.onclose = function () {

            var header, detail,
                closeMessage = App.messages.base.exit;

            if (page.header.data) {
                header = page.header.data.getChangeSet();
                if (header.created.length || header.updated.length || header.deleted.length) {
                    return closeMessage;
                }
            }
            if (page.detail.data) {
                detail = page.detail.data.getChangeSet();
                if (detail.created.length || detail.updated.length || detail.deleted.length) {
                    return closeMessage;
                }
            }
        };

        /**
        * 画面の初期化処理を行います。
        */
        page.initialize = function () {

            App.ui.loading.show();

            //TODO: URLパラメーターで渡された値を取得し、保持する処理を記述します。
            //if (!App.isUndefOrNull(App.uri.queryStrings.no_seq)) {
            //    page.values.no_seq = parseFloat(App.uri.queryStrings.no_seq);
            //} else {
            //    page.values.no_seq = 0;
            //}

            //if (!App.isUndefOrNull(App.uri.queryStrings.no_fixed)) {
            //    page.values.no_fixed = parseFloat(App.uri.queryStrings.no_fixed);
            //} else {
            //    page.values.no_fixed = 0;
            //}

            //製法一覧画面ができるまではコメント
            //製法一覧からパラメータ(製法番号）を取得
            var no_seiho = App.uri.queryStrings.no_seiho;
            page.values.no_seiho = no_seiho;

            page.initializeControl();
            page.initializeControlEvent();

            page.header.initialize();
            page.detail.initialize();

            //TODO: ヘッダー/明細以外の初期化の処理を記述します。

            page.loadMasterData().then(function (result) {

                return page.loadDialogs();
            }).then(function (result) {

                return page.loadData();
            }).then(function (result) {
                //TODO: 画面の初期化処理成功時の処理を記述します。

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
            $("#save").on("click", page.detail.renkei);
            //$("#remove").on("click", page.commands.remove);
            //$("#renkei").on("click", page.commands.renkei);
        };

        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function () {

            //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。

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
         * 画面で処理の対象とするデータのロード処理を実行します。
         */
        page.loadData = function () {

            var query;
            //TODO: 定型データを取得し、画面にバインドする処理を記述します。

            return $.ajax(App.ajax.webapi.get(page.urls.search, { no_seiho: page.values.no_seiho, skip: page.options.skip, top: page.options.top }))
                    .fail(function (error) {
                        //.fail(function (result) {
                        if (result.status === 404) {
                            page.header.bind({}, true);
                            page.detail.bind([], true);
                            return App.async.success();
                        }
                    }).then(function (result) {
                        page.header.bind(result.Header);
                        $("#save").prop("disabled", true);
                        // パーツ開閉の判断
                        if (page.detail.isClose) {
                            // 検索データの保持
                            page.detail.searchData = { "data": result.Detail, "isNew": true };
                        } else {
                            // データバインド
                            page.detail.bind(result.Detail);
                        }
                    });

            //if (!page.values.no_seq) {
            //    page.header.bind({}, true);
            //    page.detail.bind([], true);
            //    return App.async.success();
            //}
        };

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
            //コメントアウト
            //element.on("change", ":input", page.header.change);
            page.header.element = element;

            //TODO: 画面ヘッダーの初期化処理をここに記述します。
            //TODO: 画面ヘッダーで利用するコントロールのイベントの紐づけ処理をここに記述します。

        };

        /**
         * 画面ヘッダーへのデータバインド処理を行います。
         */
        page.header.bind = function (data, isNewData) {

            var element = page.header.element,
                dataSet = App.ui.page.dataSet();

            page.header.data = dataSet;

            (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(data);
            page.header.element.form(page.header.options.bindOption).bind(data);

            if (isNewData) {

                //TODO: 新規データの場合の処理を記述します。
                //ドロップダウンなど初期値がある場合は、
                //DataSetに値を反映させるために change 関数を呼び出します。
                //element.findP("cd_shiharai").change();
            }

            //バリデーションを実行します。
            //page.header.validator.validate({
            //    state: {
            //        suppressMessage: true
            //    }
            //});
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
                entity = page.header.data.entry(id),
                data = element.form().data();

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

        /**
         * 画面明細のバリデーションを定義します。
         */
        page.detail.options.validations = {
            //TODO: 画面明細のバリデーションの定義を記述します。
            no_han: {
                rules: {
                    han_required: function (value, opts, state, done) {
                        var $tbody = state.tbody.element ? state.tbody.element : state.tbody;
                        if (($tbody.findP("select_cd").prop("checked")) && ($tbody.findP("no_han").val() == "")) {
                            done(false)
                        } else {
                            done(true)
                        }
                    },
                    han_kakutei: function (value, opts, state, done) {
                        var $tbody = state.tbody.element ? state.tbody.element : state.tbody;

                        if (($tbody.findP("select_cd").prop("checked")) && ($tbody.findP("no_han").val() != "")) {
                            var query = {
                                cd_hin: $tbody.findP("cd_seihin").text(),
                                no_han: $tbody.findP("no_han").val()
                            };
                            $.ajax(App.ajax.webapi.get(page.urls.kakurei_han, query))
                            .then(function (result) {
                                if (result.Items.length > 0) {
                                    done(false);
                                } else {
                                    done(true);
                                }
                            }).fail(function (result) {
                                done(true);
                            })
                        } else {
                            done(true);
                        }
                    }
                },
                options: {
                    name: "版数"
                },
                messages: {
                    han_required: App.messages.app.AP0094,
                    han_kakutei: App.messages.app.AP0092
                }
            }
        };

        /**
         * 画面明細の初期化処理を行います。
         */
        page.detail.initialize = function () {

            var element = $(".detail"),
                table = element.find(".datatable"),
                datatable = table.dataTable({
                    height: 300,
                    //列固定横スクロールにする場合は、下記3行をコメント解除
                    //fixedColumn: true,                //列固定の指定
                    //fixedColumns: 3,                  //固定位置を指定（左端を0としてカウント）
                    //innerWidth: 1200,                 //可動列の合計幅を指定
                    //TODO: データテーブルでソート機能を利用しない場合はfalseを指定します。
                    sortable: true,
                    resize: true,
                    onselect: page.detail.select,
                    onchange: page.detail.change
                });

            table = element.find(".datatable"); //列固定にした場合DOM要素が再作成されるため、変数を再取得

            page.detail.validator = element.validation(page.createValidator(page.detail.options.validations));
            page.detail.element = element;
            page.detail.dataTable = datatable;

            element.on("click", "#nextsearch", page.detail.nextsearch);
            //element.on("click", "#add-item", page.detail.addNewItem);
            //element.on("click", "#del-item", page.detail.deleteItem);
            element.on("change", "input[type='radio']", page.detail.check);
            <%-- 上に行追加、下に行追加ボタンを利用時はコメント解除
            element.on("click", "#insert-item-before", page.detail.insertNewItemBefore);
            element.on("click", "#insert-item-after", page.detail.insertNewItemAfter);
            --%>

            // 行選択時に利用するテーブルインデックスを指定します
            page.detail.fixedColumnIndex = element.find(".fix-columns").length;

            // 明細パートオープン時の処理を指定します。
            element.find(".part").on("expanded.aw.part", function () {
                page.detail.isClose = false;
                if (page.detail.searchData) {
                    App.ui.loading.show();
                    setTimeout(function () {
                        page.detail.bind(page.detail.searchData.data, page.detail.searchData.isNew);
                        page.detail.searchData = undefined;
                        App.ui.loading.close();
                    }, 5);
                };
            });

            // 明細パートクローズ時の処理を指定します。
            element.find(".part").on("collapsed.aw.part", function () {
                page.detail.isClose = true;
            });

            //TODO: 画面明細の初期化処理をここに記述します。

            //TODO: 画面明細で利用するコントロールのイベントの紐づけ処理をここに記述します。
        };

        /**
        * 次のレコードを検索する処理を定義します。
        */
        page.detail.nextsearch = function () {

            page.options.filter = {
                no_seiho: page.values.no_seiho,
                skip: page.options.skip,     // TODO:先頭からスキップするデータ数を指定します。
                top: page.options.top       // TODO:取得するデータ数を指定します。
            };

            App.ui.loading.show();
            App.ui.page.notifyAlert.clear();

            return $.ajax(App.ajax.webapi.get(page.urls.search, page.options.filter))
            .done(function (result) {
                page.detail.bind(result.Detail);
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            }).always(function () {
                App.ui.loading.close();
            });

        };

        /**
         * ラジオボタンが選択されているかどうかで連携ボタンの活性・非活性を変更します。
         */
        page.detail.check = function () {
            if (page.detail.element.find("tbody.new input[type='radio']:checked:first").length) {
                $("#save").prop("disabled", false);
            }
            else {
                $("#save").prop("disabled", true);
            }
        }

        /**
         * 画面明細の各行にデータを設定する際のオプションを定義します。
         */
        page.detail.options.bindOption = {
            appliers: {
            }
        };

        /**
          * 明細行に表示する版のドロップダウンリストを設定します。
          */
        page.detail.dropdown = function (seihin, row) {

            return $.ajax(App.ajax.odata.get(page.detail.urls.dropdown, { cd_hin: seihin }
            )).then(function (result) {
                var no_han = row.find(".no_han");
                no_han.children().remove();
                App.ui.appendOptions(
                    no_han,
                    "MERC_EDITION",
                    "MERC_EDITION",
                    result.Items,
                    true
                    );
            });
        };

        /**
         * 画面明細へのデータバインド処理を行います。
         */
        page.detail.bind = function (data, isNewData) {
            var element = page.detail.element,
                i, l, item, dataSet, offsetHeight;

            dataCount = data.length ? data[0].COUNT_ALL : 0;

            if (page.options.skip === 0) {
                dataSet = App.ui.page.dataSet();
                page.detail.dataTable.dataTable("clear");
            } else {
                dataSet = page.detail.data
            }

            page.detail.data = dataSet;

            var str = "000000";

            page.detail.dataTable.dataTable("addRows", data, function (row, item) {
                (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
                row.form(page.detail.options.bindOption).bind(item);
                //TODO: 画面明細へのデータバインド処理をここに記述します。
                //版数を取得します。
                page.detail.dropdown(item.cd_seihin, row);
                //製品コードを0埋めします。
                var cd_seihin_new = str + item.cd_seihin;
                cd_seihin_new = cd_seihin_new.substr(cd_seihin_new.length - 6, 6);
                row.findP("cd_seihin").text(cd_seihin_new)

                return row;
            }, true);

            page.options.skip += data.length;
                page.detail.element.findP("data_count").text(page.options.skip);
                page.detail.element.findP("data_count_total").text(dataCount);

            if (dataCount <= page.options.skip) {
                $("#nextsearch").hide();
            }
            else {
                $("#nextsearch").show();
            }

            offsetHeight = $("#nextsearch").is(":visible") ? $("#nextsearch").addClass("show-search").outerHeight() : 0;
            page.detail.dataTable.dataTable("setAditionalOffset", offsetHeight);
            //バリデーションを実行します。
            page.detail.validateList(true);

            //TODO:合計計算用の処理です。不要な場合は削除してください。
            //page.detail.calculate();
        };

        /**
         * 画面明細の一覧の行が選択された時の処理を行います。
         */
        page.detail.select = function (e, row) {
            //TODO: 単一行を作成する場合は、下記２行を利用します。
            $($(row.element[page.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab.selected")).removeClass("selected").addClass("unselected");
            $(row.element[page.detail.fixedColumnIndex].querySelectorAll(".select-tab")).removeClass("unselected").addClass("selected");
            //TODO: 多段行を作成する場合は、下記２行を有効にし、上記の２行は削除します。
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
         * 画面明細の一覧の入力項目の変更イベントの処理を行います。
         */
        page.detail.change = function (e, row) {

            //    App.ui.page.notifyWarn.clear();
            //    App.ui.page.notifyAlert.clear();
            //    App.ui.page.notifyInfo.clear();

            var target = $(e.target),
                id = row.element.attr("data-key"),
                property = target.attr("data-prop"),
                entity = page.detail.data.entry(id),
                options = {
                    filter: page.detail.validationFilter
                };

            page.values.isChangeRunning[property] = true;

            //page.detail.executeValidation(target, row)
            //.then(function () {
            entity[property] = row.element.form().data()[property];
            page.detail.data.update(entity);


            //入力行の他の項目のバリデーション（必須チェック以外）を実施します
            //page.detail.executeValidation(row.element.find(":input"), row, options);
            //}).always(function () {
            delete page.values.isChangeRunning[property];
            //});

            //TODO:合計計算用の処理です。不要な場合は削除してください。
            //page.detail.calculate();
        };

        /**
         * 画面明細の一覧に新規データを追加します。
         */
        page.detail.addNewItem = function () {
            //TODO:新規データおよび初期値を設定する処理を記述します。
            var newData = {
                no_seq: page.values.no_seq
            };

            page.detail.data.add(newData);
            page.detail.dataTable.dataTable("addRow", function (tbody) {
                tbody.form().bind(newData);
                //TODO: 画面明細へのデータバインド処理をここに記述します。
                return tbody;
            }, true);
        };

        /**
         * 画面明細の一覧で選択されている行とデータを削除します。
         */
        page.detail.deleteItem = function (e) {
            var element = page.detail.element,
                //TODO: 単一行を作成する場合は、下記を利用します。
                selected = element.find(".datatable .select-tab.selected").closest("tbody");
            //TODO: 多段行を作成する場合は、以下を利用し、上記１行は削除します。
            //selected = element.find(".datatable .select-tab-2lines.selected").closest("tbody");

            if (!selected.length) {
                return;
            }

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

            //TODO:合計計算用の処理です。不要な場合は削除してください。
            page.detail.calculate();
        };

        <%--
        /**
         * 画面明細の一覧に対して、選択行の前に新規データを挿入します。
         */
        page.detail.insertNewItemBefore = function () {
            //TODO:新規データおよび初期値を設定する処理を記述します。
            var newData = {
                no_seq: page.values.no_seq
            };

            // 新規データを挿入（前）
            page.detail.insertRow(newData, true, false);
        };

        /**
         * 画面明細の一覧に対して、選択行の後に新規データを挿入します。
         */
        page.detail.insertNewItemAfter = function () {
            //TODO:新規データおよび初期値を設定する処理を記述します。
            var newData = {
                no_seq: page.values.no_seq
            };

            // 新規データを挿入（後）
            page.detail.insertRow(newData, true, true);
        };

        /**
        * 画面明細の一覧に、選択行の後に新しい行を挿入します。
        */
        page.detail.insertRow = function (newData, isFocus, isInsertAfter) {
            var element = page.detail.element,
                //TODO: 単一行を作成する場合は、下記を利用します。
                selected = element.find(".datatable .select-tab.selected").closest("tbody");
                //TODO: 多段行を作成する場合は、以下を利用し、上記１行は削除します。
                //selected = element.find(".datatable .select-tab-2lines.selected").closest("tbody");

            if (!selected.length) {
                // 選択行が無ければこれまでどおり最終行に行追加
                page.detail.addNewItem();
                return;
            }

            page.detail.data.add(newData);
            page.detail.dataTable.dataTable("insertRow", selected, isInsertAfter, function (tbody) {
                tbody.form().bind(newData);

                //TODO: 画面明細へのデータバインド処理をここに記述します。
                return tbody;
            }, isFocus);

        };
        --%>

        /**
         * 画面明細のバリデーションを実行します。
         */
        page.detail.executeValidation = function (targets, row, options) {
            var defaultOptions = {
                targets: targets,
                state: {
                    tbody: row,
                    isGridValidation: true
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
        page.detail.validateList = function (suppressMessage) {
            var validations = [],
                options = {
                    state: {
                        suppressMessage: suppressMessage
                    }
                };

            page.detail.dataTable.dataTable("each", function (row) {
                validations.push(page.detail.executeValidation(row.element.find(":input"), row.element, options));
            });

            return App.async.all(validations);
        };

        //TODO: 以下の page.detail の各宣言は画面仕様ごとにことなるため、不要の場合は削除してください。
<%--
        /**
         * 画面明細の一覧から検索ダイアログを表示します。
         */
        page.detail.showSearchDialog = function (e) {
            var element = page.detail.element,
                tbody = $(e.target).closest("tbody"),
                row;

            page.detail.dataTable.dataTable("getRow", tbody, function (rowObject) {
                row = rowObject.element;
            });

            page.dialogs.searchDialog.element.modal("show");

            //検索ダイアログでデータ選択が実行された時に呼び出される関数を設定しています。
            page.dialogs.searchDialog.dataSelected = function (data) {

                row.findP("cd_buhin").val(data.cd_buhin).change();
                row.findP("nm_komoku").text(data.nm_buhin);

                delete page.dialogs.searchDialog.dataSelected;
            }
        };
--%>
        /**
         * 画面明細の支払単価と数量をもとにした合計金額を計算し、表示します。
         */
        page.detail.calculate = function () {
            var items,
                total;

            if (page.detail.values.suppressCalculate) {
                return;
            }

            items = page.detail.data.findAll(function (item, entity) {
                return entity.state !== App.ui.page.dataSet.status.Deleted;
            });

            total = items.reduce(function (init, value) {
                return init;
            }, 0);

            //page.detail.element.find(".kei_shiire_kingaku").text(total);
        };

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
            <div title="製法情報" class="part">
                <div class="row">
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>製法番号</label>
                    </div>
                    <div class="control col-xs-4">
                        <label data-prop="no_seiho"></label>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>製法名</label>
                    </div>
                    <div class="control col-xs-6">
                        <label data-prop="nm_seiho"></label>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>ステータス</label>
                    </div>
                    <div class="control col-xs-11">
                        <label data-prop="status"></label>
                    </div>
                </div>
            </div>
        </div>
        <!--TODO: 明細を定義するHTMLをここに記述します。-->
        <div class="detail">
            <%--<div title="製法情報" class="part">--%>
                <%--<div class="control-label toolbar">
                    <i class="icon-th"></i>
                    <div class="btn-group">
                        <button type="button" class="btn btn-default btn-xs" id="add-item">追加</button>
                        <button type="button" class="btn btn-default btn-xs" id="del-item">削除</button>
                        <%-- 
                        <button type="button" class="btn btn-default btn-xs" id="insert-item-before">上に行追加</button>
                        <button type="button" class="btn btn-default btn-xs" id="insert-item-after">下に行追加</button>
                        
                    </div>
                </div>--%>
                <div class="control-label toolbar">
                <i class="icon-th"></i>
                <span>製法情報  </span>
                <div class="btn-group">
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
                            <th style="width: 20px;"></th>
                            <th style="width: 100px;">優先順位</th>
                            <th style="width: 100px;">製品コード</th>
                            <th style="width: 300px;">製品名</th>
                            <th style="width: 200px;">荷姿</th>
                            <th style="width: 100px;">版数</th>
                            <!--TODO: 多段行を作成する場合は、以下を利用し、上記１行は削除します。
							<th rowspan="2" style="width: 10px;"></th>
                       	</tr>
                        <tr>
						-->
                        </tr>
                    </thead>
                    <!--TODO: 明細一覧のフッターを定義するHTMLをここに記述します。-->
                    <tfoot>
                        <%-- <tr>
                            
                        </tr>
                    </tfoot>--%>
                    <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->
                    <tbody class="item-tmpl" style="cursor: default; display: none;">
                        <tr>
                            <!--TODO: 単一行を作成する場合は、下記を利用します。-->
                         <%--   <td>
                                <span class="select-tab unselected"></span>
                            </td>--%>
                            <td class="center">
                                <input type="radio" name="select_cd" data-prop="select_cd" />
                            </td>
                            <td>
                                <span data-prop="no_yusen" class="number-right number"></span>
                            </td>
                            <td>
                                <span data-prop="cd_seihin"></span>
                            </td>
                            <td>
                                <span data-prop="nm_seihin"></span>
                            </td>
                            <td>
                                <span data-prop="nisugata_hyoji"></span>
                            </td>
                            <td>
                                <select data-prop="no_han" class="no_han"></select>
                                <input type="hidden" data-prop="kbn_meisho" />
                                <input type="hidden" data-prop="nm_meisho_hinmei" />
                                <input type="hidden" data-prop="kbn_shomikikan" />
                                <input type="hidden" data-prop="kbn_shomikikan_seizo_fukumu" />
                                <input type="hidden" data-prop="su_shomikikan_free_input" />
                                <input type="hidden" data-prop="kbn_shomikikan_tani" />
                                <input type="hidden" data-prop="su_naiyoryo" />
                                <input type="hidden" data-prop="cd_naiyoryo_tani" />
                                <input type="hidden" data-prop="nm_toriatsukai_ondo" />
                                <input type="hidden" data-prop="nm_seihintokusechi_kenshisakuhin" />
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
                    <button type="button" id="nextsearch" class="btn btn-sm btn-primary btn-next-search command-right" style="display:none" >次を検索</button>
                </div>
            </div>
        <%--</div>--%>

    </div>
</asp:Content>

<asp:Content ID="FooterContent" ContentPlaceHolderID="FooterContent" runat="server">
    <div class="command-left">
        <!--TODO: コマンドボタン（左寄せ配置）を定義するHTMLをここに記述します。-->
    </div>
    <div class="command">
        <!--TODO: コマンドボタン（右寄せ配置）を定義するHTMLをここに記述します。-->
        <%--<button type="button" id="remove" style="display:none" class="btn btn-sm btn-default">削除</button>--%>
        <button type="button" id="save" class="btn btn-sm btn-primary">連携</button>
    </div>
</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>
