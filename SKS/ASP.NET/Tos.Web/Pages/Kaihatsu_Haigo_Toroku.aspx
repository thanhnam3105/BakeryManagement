<%@ Page Title="配合登録（開発部門）" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="Kaihatsu_Haigo_Toroku.aspx.cs" Inherits="Tos.Web.Pages.Kaihatsu_Haigo_Toroku" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchList(Ver1.6)】 Template--%>

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
        .hide {
            display:none;
        }
    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("Kaihatu_Haigou_Touroku", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {},
            values: {
                isChangeRunning: {},
                lstSeihin: []
            },
            urls: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
                haigou_torikomi: "Dialogs/Kaihatsu_Haigo_Torikomi.aspx",
                seizo_kojyo: "Dialogs/Seizo_Kojyo_Shitei.aspx",
                hinmeiDialog: "Dialogs/Hinmei_Kensaku.aspx",
                markDialog: "Dialogs/MarkDialog.aspx",
                koteimeiDialog: "Dialogs/KoteimeiDialog.aspx",
                SeihinjyohoDialog: "Dialogs/SeihinjyohoDialog.aspx",
                TuikaJyohoDialog: "Dialogs/TuikaJyohoDialog.aspx"
            },
            header: {
                options: {},
                values: {},
                urls: {                 
                    no_seiho: "../Resources/mockup/seiho_bango.js",
                    bunrui: "../Resources/mockup/Hin_bunrui.js",
                    kbn: "../Resources/mockup/Hyojiyo_Haigo_Toroku_kbn.js",                  
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

                    var id = page.header.element.attr("data-key"),
                        entity = page.header.data.entry(id),
                        changeSets;

                    page.header.data.update(entity);

                    <%--
                    //TODO: 明細に表示順で番号をセットする場合はここに記述します
                    page.detail.dataTable.dataTable("eachSorted", function (row, index) {
                        id = row.element.attr("data-key"),
                        entity = page.detail.data.entry(id);
                        entity["no_komoku"] = index;
                        page.detail.data.update(entity);
                    });
                    --%>

                    changeSets = {
                        Header: page.header.data.getChangeSet(),
                        Detail: page.detail.data.getChangeSet()
                    };

                    //TODO: データの保存処理をここに記述します。
                    return $.ajax(App.ajax.webapi.post(/* TODO: データ保存サービスのURL , */ changeSets))
                            .then(function (result) {

                                //TODO: データの保存成功時の処理をここに記述します。

                                //if (result.Header) {
                                //    page.values.no_seq = result.Header.no_seq;
                                //} else {
                                //    page.values.no_seq = page.header.data.find(function () { return true; }).no_seq;
                                //}

                                //最後に再度データを取得しなおします。
                                page.loadData();
                                App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();

                            }).fail(function (error) {
                                if (error.status === App.settings.base.conflictStatus) {
                                    // TODO: 同時実行エラー時の処理を行っています。
                                    // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                                    page.loadData();
                                    App.ui.page.notifyAlert.clear();
                                    App.ui.page.notifyAlert.message(App.messages.base.MS0009).show();
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
            return $.ajax(App.ajax.odata.get(page.header.urls.no_seiho)).then(function (result) {

                var no_seiho_shurui = page.header.element.findP("no_seiho_shurui");
                no_seiho_shurui.children().remove();
                App.ui.appendOptions(
                    no_seiho_shurui,
                    "no_seiho_shurui",
                    "nm_seiho_shurui",
                    result.value,
                    false
                );
            }).then(function (result) {
                $.ajax(App.ajax.odata.get(page.header.urls.bunrui)).then(function (result) {

                    var cd_seiho_bunrui = page.header.element.findP("cd_seiho_bunrui");
                    cd_seiho_bunrui.children().remove();
                    App.ui.appendOptions(
                        cd_seiho_bunrui,
                        "cd_bunrui",
                        "nm_bunrui",
                        result.value,
                        true
                    );
                });
            }).then(function (result) {
                $.ajax(App.ajax.odata.get(page.header.urls.kbn)).then(function (result) {

                    var kbn_hin = page.header.element.findP("kbn_hin");
                    kbn_hin.children().remove();
                    App.ui.appendOptions(
                        kbn_hin,
                        "kbn_hin",
                        "nm_hin",
                        result.value,
                        true
                    );
                });
            })
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
                searchDialog: $.get(page.urls.haigou_torikomi),
                headersearchDialog: $.get(page.urls.seizo_kojyo),
                hinmeiDialog: $.get(page.urls.hinmeiDialog),
                markDialog: $.get(page.urls.markDialog),
                koteimeiDialog: $.get(page.urls.koteimeiDialog),
                seihinjyohoDialog: $.get(page.urls.SeihinjyohoDialog),
                tuikaJyohoDialog: $.get(page.urls.TuikaJyohoDialog)
            }).then(function (result) {

                $("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;

                $("#dialog-container").append(result.successes.searchDialog);
                page.dialogs.searchDialog = Kaihatsu_Haigo_Torikomi;
                page.dialogs.searchDialog.initialize();

                $("#dialog-container").append(result.successes.headersearchDialog);
                page.dialogs.headersearchDialog = Seizo_Kojyo_Shitei;
                page.dialogs.headersearchDialog.confirmDialog = ConfirmDialog;
                page.dialogs.headersearchDialog.initialize();

                $("#dialog-container").append(result.successes.hinmeiDialog);
                page.dialogs.hinmeiDialog = Hinmei_Kensaku;
                page.dialogs.hinmeiDialog.initialize();

                $("#dialog-container").append(result.successes.markDialog);
                page.dialogs.markDialog = MarkDialog;
                page.dialogs.markDialog.initialize();

                $("#dialog-container").append(result.successes.koteimeiDialog);
                page.dialogs.koteimeiDialog = KoteimeiDialog;
                page.dialogs.koteimeiDialog.initialize();

                $("#dialog-container").append(result.successes.seihinjyohoDialog);
                page.dialogs.seihinjyohoDialog = SeihinjyohoDialog;
                page.dialogs.seihinjyohoDialog.options.mode = App.settings.app.dialog_mode.kaihatsu;
                page.dialogs.seihinjyohoDialog.initialize();

                $("#dialog-container").append(result.successes.tuikaJyohoDialog);
                page.dialogs.tuikaJyohoDialog = TuikaJyohoDialog;
                page.dialogs.tuikaJyohoDialog.initialize();
            });
        };

        /**
         * 画面で処理の対象とするデータのロード処理を実行します。
         */
        page.loadData = function () {

            if (!page.values.no_seq && page.values.no_fixed) {

                //TODO: 定型データを取得し、画面にバインドする処理を記述します。

                return $.ajax(App.ajax.webapi.get(/* TODO: 定型データ取得サービスの URL を設定してください。*/page.urls, {/* 定型データ取得サービスのパラメータを設定します */ }))
                    .fail(function (result) {
                        if (result.status === 404) {
                            page.header.bind({}, true);
                            page.detail.bind([], true);
                            return App.async.success();
                        }
                    }).then(function (result) {
                        page.header.bind(result.Header, true);
                        // パーツ開閉の判断
                        if (page.detail.isClose) {
                            // 検索データの保持
                            page.detail.searchData = { "data": result.Detail, "isNew": true };
                        } else {
                            // データバインド
                            page.detail.bind(result.Detail, true);
                        }
                    });
            }

            if (!page.values.no_seq) {
                page.header.bind({}, true);
                page.detail.bind([], true);
                return App.async.success();
            }

            //TODO: 画面内の処理の対象となるデータを取得し、画面にバインドする処理を記述します。

            return $.ajax(App.ajax.webapi.get(/* TODO: データ取得サービスの URL を設定してください。*/page.urls, {/* データ取得サービスのパラメータを設定します */ }))
            .fail(function (result) {
                if (result.status === 404) {
                    page.header.bind({}, true);
                    page.detail.bind([], true);
                    return App.async.success();
                }
            }).then(function (result) {
                $("#remove").show();

                page.header.bind(result.Header);
                // パーツ開閉の判断
                if (page.detail.isClose) {
                    // 検索データの保持
                    page.detail.searchData = { "data": result.Detail, "isNew": false };
                } else {
                    // データバインド
                    page.detail.bind(result.Detail);
                }
            });
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
            element.on("change", ":input", page.header.change);
            page.header.element = element;

            //TODO: 画面ヘッダーの初期化処理をここに記述します。
            //TODO: 画面ヘッダーで利用するコントロールのイベントの紐づけ処理をここに記述します。
            element.on("click", ".btn_haigou_torikomi", page.header.TorikomishowSearchDialog);
            element.on("click", ".btn_kojyo_shitei", page.header.SeizoshowSearchDialog);
            element.on("click", ".btnSeihinNyuroku", page.header.SeihinJyohoDialog);
            element.on("click", ".btn_tsuika_nyuryoku", page.header.TuikaJyohoDialog);
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
            page.header.validator.validate({
                state: {
                    suppressMessage: true
                }
            });
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

        /**
        * 画面ヘッダーにある検索ダイアログ起動時の処理を定義します。
        */
        page.header.TorikomishowSearchDialog = function () {
            page.dialogs.searchDialog.element.modal("show");
        };

        /**
         * 画面ヘッダーにある取引先コードに値を設定します。
         */
        page.header.setTorihiki = function (data) {
            page.header.element.findP("cd_torihiki").val(data.cd_torihiki).change();
            page.header.element.findP("nm_torihiki").text(data.nm_torihiki);
        };

        /**
       * 画面ヘッダーにある検索ダイアログ起動時の処理を定義します。
       */
        page.header.SeizoshowSearchDialog = function () {
            page.dialogs.headersearchDialog.element.modal("show");
        };

        /**
         * 画面ヘッダーにある取引先コードに値を設定します。
         */
        page.header.setTorihiki = function (data) {
            page.header.element.findP("cd_torihiki").val(data.cd_torihiki).change();
            page.header.element.findP("nm_torihiki").text(data.nm_torihiki);
        };

        /**
        *  Show SeihinJyohoDialog (製品情報入力)
        */
        page.header.SeihinJyohoDialog = function () {
            page.dialogs.seihinjyohoDialog.values.lstSeihin = page.values.lstSeihin;
            page.dialogs.seihinjyohoDialog.element.modal("show");

            page.dialogs.seihinjyohoDialog.dataSelected = function (data) {
                page.values.lstSeihin = data;
                delete page.dialogs.seihinjyohoDialog.dataSelected;
            }
        };

        /**
        *  Show TuikaJyohoDialog (追加情報入力)
        */
        page.header.TuikaJyohoDialog = function () {
            page.dialogs.tuikaJyohoDialog.element.modal("show");
        };
        //TODO-END: 以下の page.header の各宣言は画面仕様ごとにことなるため、不要の場合は削除してください。

        /**
         * 画面明細のバリデーションを定義します。
         */
        page.detail.options.validations = {
            //TODO: 画面明細のバリデーションの定義を記述します。
        };

        /**
         * 画面明細の初期化処理を行います。
         */
        page.detail.initialize = function () {

            var element = $(".detail"),
                table = element.find(".datatable"),
                datatable = table.dataTable({
                    height: 100,
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

            element.on("click", "#add-item", page.detail.addNewItem);
            element.on("click", "#add-row", page.detail.addNewRow);
            element.on("click", "#del-item", page.detail.deleteItem);
            element.on("click", ".cd_hin", page.detail.showHinmeiDialog);
            element.on("click", ".mark", page.detail.showMarkDialog);
            element.on("click", ".koteimei", page.detail.showKoteimeiDialog);
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
            var element = page.detail.element,
                i, l, item,
                dataSet = App.ui.page.dataSet();

            page.detail.data = dataSet;

            page.detail.dataTable.dataTable("clear");

            page.detail.dataTable.dataTable("addRows", data, function (row, item) {
                (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
                row.form(page.detail.options.bindOption).bind(item);
                //TODO: 画面明細へのデータバインド処理をここに記述します。

                return row;
            }, true);

            page.detail.dataTable.dataTable("setAditionalOffset", -30);
            //バリデーションを実行します。
            page.detail.validateList(true);

            //TODO:合計計算用の処理です。不要な場合は削除してください。
            page.detail.calculate();
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
            var target = $(e.target),
                id = row.element.attr("data-key"),
                property = target.attr("data-prop"),
                entity = page.detail.data.entry(id),
                options = {
                    filter: page.detail.validationFilter
                };

            page.values.isChangeRunning[property] = true;

            page.detail.executeValidation(target, row)
            .then(function () {
                entity[property] = row.element.form().data()[property];
                page.detail.data.update(entity);

                page.detail.executeValidation(row.element.find(":input"), row, options);
            }).always(function () {
                delete page.values.isChangeRunning[property];
            });

            //TODO:合計計算用の処理です。不要な場合は削除してください。
            page.detail.calculate();
        };

        /**
         * 画面明細の一覧に新規データを追加します。（工程追加押下時）
         */
        page.detail.addNewItem = function () {
            //TODO:新規データおよび初期値を設定する処理を記述します。
            var newData = {
                no_seq: page.values.no_seq
            };
            page.detail.data.add(newData);
            page.detail.dataTable.dataTable("addRow", function (tbody) {
                var kotei = tbody.find(".kotei"),
                row_kotei = tbody.find(".row_kotei");
                kotei.removeClass("hide");
                row_kotei.addClass("hide");
                tbody.form(page.detail.options.bindOption).bind(newData);

                //TODO: 画面明細へのデータバインド処理をここに記述します。
                return tbody;
            }, true);
            page.detail.addNewRow();
        };

        /**
         * 画面明細の一覧に新規データを追加します。（行追加押下時）
         */
        page.detail.addNewRow = function () {
            //TODO:新規データおよび初期値を設定する処理を記述します。
            var newData = {
                no_seq: page.values.no_seq
            };
            page.detail.data.add(newData);
            page.detail.dataTable.dataTable("addRow", function (tbody) {
                var kotei = tbody.find(".kotei"),
                row_kotei = tbody.find(".row_kotei");
                row_kotei.removeClass("hide");
                kotei.addClass("hide");

                tbody.form(page.detail.options.bindOption).bind(newData);

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

            page.detail.data.add(newData);

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

            page.detail.data.add(newData);

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
                if (row.element.hasClass("item-tmpl")) {
                    return;
                }

                validations.push(page.detail.executeValidation(row.element.find(":input"), row.element, options));
            });

            return App.async.all(validations);
        };

        //TODO: 以下の page.detail の各宣言は画面仕様ごとにことなるため、不要の場合は削除してください。

        /**
         * 画面明細の一覧から検索ダイアログを表示します。
         */
        page.detail.showHinmeiDialog = function (e) {
            var element = page.detail.element,
                tbody = $(e.target).closest("tbody"),
                row;

            page.detail.dataTable.dataTable("getRow", tbody, function (rowObject) {
                row = rowObject.element;
            });

            page.dialogs.hinmeiDialog.element.modal("show");

            //検索ダイアログでデータ選択が実行された時に呼び出される関数を設定しています。
            page.dialogs.hinmeiDialog.dataSelected = function (data) {

                row.findP("cd_hin").val(data.cd_hinmei).change();
                row.findP("nm_hin").val(data.nm_hinmei).change();

                delete page.dialogs.hinmeiDialog.dataSelected;
            }
        };

        /**
         * 画面明細の一覧から検索ダイアログを表示します。
         */
        page.detail.showMarkDialog = function (e) {
            var element = page.detail.element,
                tbody = $(e.target).closest("tbody"),
                row;

            page.detail.dataTable.dataTable("getRow", tbody, function (rowObject) {
                row = rowObject.element;
            });

            page.dialogs.markDialog.element.modal("show");
            page.dialogs.markDialog.search();

            //検索ダイアログでデータ選択が実行された時に呼び出される関数を設定しています。
            page.dialogs.markDialog.dataSelected = function (data) {

                row.findP("mark").text(data.mark);

                delete page.dialogs.markDialog.dataSelected;
            }
        };
        /**
         * 画面明細の一覧から検索ダイアログを表示します。
         */
        page.detail.showKoteimeiDialog = function (e) {
            var element = page.detail.element,
                tbody = $(e.target).closest("tbody"),
                row;

            page.detail.dataTable.dataTable("getRow", tbody, function (rowObject) {
                row = rowObject.element;
            });

            page.dialogs.koteimeiDialog.element.modal("show");
            page.dialogs.koteimeiDialog.search();

            //検索ダイアログでデータ選択が実行された時に呼び出される関数を設定しています。
            page.dialogs.koteimeiDialog.dataSelected = function (data) {

                row.findP("nm_kotei").val(data.nm_kotei).change();

                delete page.dialogs.koteimeiDialog.dataSelected;
            }
        };
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
        $(document).ready(function () {
            // ページの初期化処理を呼び出します。
            page.initialize();
        });

    </script>
</asp:Content>
<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="content-wrap smaller">
        <!--TODO: ヘッダーを定義するHTMLをここに記述します。-->
        <div class="header">
            <div title="製法" class="part">
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>製法番号</label>
                    </div>
                    <div class="control col-xs-2 seiho-group">
                        <input type="text" data-prop="no_seiho_kaisha" value="0001" class="cd_kaisha"/>
                        <label>-</label>
                        <select data-prop="no_seiho_shurui" class="cd_shurui"></select>
                        <label>-</label>
                        <input type="text" data-prop="no_seiho_nen" value="18" class="nendo"/>
                        <label>-</label>
                        <input type="text" data-prop="no_seiho_renban" class="seq" />
                    </div>
                    <%--<div class="control col-xs-3">
                        <input data-prop="no_seiho_kaisha" style="width: 50px;" class="number" />
                        <span style="width: 10px;" class="">_</span>
                        <select style="width: 80px;" data-prop="no_seiho_shurui" class=""></select>
                        <span style="width: 10px;" class="">_</span>
                        <input data-prop="no_seiho_nen" style="width: 25px;" class="number" />
                        <span style="width: 10px;" class="joken_haigo">_</span>
                        <input data-prop="no_seiho_renban" style="width: 50px" class="number" />
                    </div>--%>
                    <div class="control col-xs-1 han-group">
                        <label>第</label>
                        <input type="text" data-prop="no_han" class="no_han center" value="1"/>
                        <label>版</label>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>製品種類</label>
                    </div>
                    <div class="control col-xs-1">
                        <label data-prop="nm_seihin_shurui"></label>
                    </div>
                    <div class="control-label col-xs-3">
                        <label>登録日</label>
                        <label data-prop="dt_toroku">2018/01/09 11:42:23</label>
                        <label data-prop="nm_tanto_toroku">瀬部　光太郎</label>
                    </div>
                    <div class="control-label col-xs-3">
                        <label>更新日</label>
                        <label data-prop="dt_henko">2018/01/09 11:44:51</label>
                        <label data-prop="nm_tanto_koshin">瀬部　光太郎</label>
                    </div>
                    <%--<div class="control-label col-xs-2">
                        <label>製法分類</label>
                    </div>
                    <div class="control col-xs-3">
                        <select data-prop="cd_seiho_bunrui" style="width: 200px;"></select>
                    </div>--%>
                </div>

                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>製法名</label>
                    </div>
                    <div class="control col-xs-8">
                        <input type="text" data-prop="nm_seiho"/>
                    </div>
                    <div class="control col-xs-3 right">
                        <button type="button" id="Button4" class="btn btn-xs btn-primary btn_kojyo_shitei">&nbsp;&nbsp;製造工場指定&nbsp;&nbsp;</button>
                        <button type="button" id="Button5" class="btn btn-xs btn-primary btn_seiho_sansho" data-transfer="/Pages/W_SeihoBunsyoShakusei2.aspx">&nbsp;&nbsp;製法文書&nbsp;&nbsp;</button>
                    </div>
                </div>

            </div>
            <div title="配合情報" class="part">
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>配合コード</label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="text" style="width: 100px;" data-prop="cd_haigo" />
                    </div>
                    <div class="control-label col-xs-1">
                        <label>ステータス</label>
                    </div>
                    <div class="control col-xs-1">
                        <label data-prop="nm_status">編集中</label>
                    </div>
                    <div class="control col-xs-2">
                        <%--<span>(</span>
                        <input type="checkbox" data-prop="kbn_mishiyo" />
                        <span>未使用&nbsp;&nbsp;</span>
                        <input type="checkbox" data-prop="kbn_haishi" />
                        <span>廃止)</span>--%>
                        <label class="min-zero">（</label>
                        <label class="check-group min-zero">
                            <input type="checkbox" data-prop="kbn_mishiyo" />
                            未使用
                        </label>
                        <label class="check-group min-zero">
                            <input type="checkbox" data-prop="kbn_haishi" />
                            廃止
                        </label>
                        <label class="min-zero">）</label>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>同一製法</label>
                    </div>
                    <div class="control col-xs-4">
                        <label>0001-A19-18-9999</label>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>配合名</label>
                    </div>
                    <div class="control col-xs-6">
                        <input type="text" class="ime-active" data-prop="nm_haigo"/>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>配合名略称</label>
                    </div>
                    <div class="control col-xs-4">
                        <input type="text" class="ime-active" data-prop="nm_haigo_r" style="width: 400px;" />
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>品区分</label>
                    </div>
                    <div class="control col-xs-2">
                        <select class="" data-prop="kbn_hin"></select>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>基本倍率（係数）</label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="text" class="number-right" data-prop="ritsu_kihon" value="1" style="width: 50px"/>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>V/W区分</label>
                    </div>
                    <div class="control col-xs-2">
                        <%--<input type="radio" class="" data-prop="rdoVWKbnKg" checked name="vw_kubun" value="1" />
                        <span>Kg</span>
                        <input type="radio" class="" data-prop="rdoVWKbnL" name="vw_kubun" value="2" />
                        <span>L</span>--%>
                        <label class="check-group" style="min-width: 60px">
                            <input type="radio" class="" data-prop="rdoVWKbnKg" checked name="vw_kubun" value="1" />
                            Kg
                        </label>
                        <label class="check-group" style="min-width: 60px">
                            <input type="radio" class="" data-prop="rdoVWKbnL" name="vw_kubun" value="2" />
                            L
                        </label>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>製品情報</label>
                    </div>
                    <div class="control col-xs-2">
                        <label></label>
                    </div>
                </div>

                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>改良元製法番号</label>
                    </div>
                    <div class="control col-xs-2">
                        <label>0001-A19-17-8888</label>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>改良元配合コード</label>
                    </div>
                    <div class="control col-xs-1">
                        <label data-prop="cd_haigo_sanko" >123456</label>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>世代数　3</label>
                        <label class="min-zero"></label>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>代表工場</label>
                    </div>
                    <div class="control col-xs-2">
                        <label data-prop="nm_kojyo">代表工場を指定してください</label>
                    </div>
                    <div class="control col-xs-3 right">
                        <button type="button" id="Button7" class="btn btn-xs btn-primary btn_haigou_torikomi">&nbsp;配合取込&nbsp;</button>
                        <button type="button" id="Button1" class="btn btn-xs btn-primary btn_tsuika_nyuryoku">&nbsp;追加情報入力&nbsp;</button>
                        <button type="button" id="Button2" class="btn btn-xs btn-primary btnSeihinNyuroku">&nbsp;製品情報入力&nbsp;</button>
                    </div>
                </div>
            </div>
        </div>
        <!--TODO: 明細を定義するHTMLをここに記述します。-->
        <div class="detail">
                <div class="control-label toolbar">
                    <div class="row">
                        <div class="control col-xs-12">
                            <button type="button" class="btn btn-default btn-xs" id="add-item">工程追加</button>
                            <button type="button" class="btn btn-default btn-xs" id="Button21">工程移動▲</button>
                            <button type="button" class="btn btn-default btn-xs" id="Button22">工程移動▼</button>
                            <button type="button" class="btn btn-default btn-xs" id="del-item">工程削除</button>
                            <button type="button" class="btn btn-default btn-xs" id="add-row" style="margin-left: 70px;">行追加</button>
                            <button type="button" class="btn btn-default btn-xs" id="Button27">行移動▲</button>
                            <button type="button" class="btn btn-default btn-xs" id="Button28">行移動▼</button>
                            <button type="button" class="btn btn-default btn-xs" id="Button29">行削除</button>
                        </div>
                    </div>
                </div>
                <table class="datatable">
                    <!--TODO: 明細一覧のヘッダーを定義するHTMLをここに記述します。-->
                    <thead>
                        <tr>
                            <!--TODO: 単一行を作成する場合は、下記を利用します。-->
                            <th style="width: 10px;"></th>
                            <th style="width: 50px;">分割</th>
                            <th style="width: 50px;"></th>
                            <th style="width: 150px;">コード</th>
                            <th style="width: 50px;">指</th>
                            <th style="">品名/作業指示</th>
                            <th style="width: 80px;">マーク</th>
                            <th style="width: 150px;">配合重量</th>
                            <th style="width: 100px;">単位</th>
                            <th style="width: 150px;">荷姿重量</th>
                            <th style="width: 100px;">比重</th>
                            <th style="width: 100px;">歩留</th>
                            <th style="width: 200px;">規格書番号</th>
                            <th style="width: 80px;">誤差</th>
                            <!--TODO: 多段行を作成する場合は、以下を利用し、上記１行は削除します。
							<th rowspan="2" style="width: 10px;"></th>
                       	</tr>
                        <tr>
						-->
                        </tr>
                    </thead>
                    <!--TODO: 明細一覧のフッターを定義するHTMLをここに記述します。-->
                    <tfoot>
                        <tr>
                            <td colspan="8">
                                <div class="control col-xs-6"></div>
                                <div class="control col-xs-6 right" style="padding-right: 20px">
                                    <label class="qty_haigo_kei" style="padding-right: 30px">配合合計</label>
                                    <label class="">0.000000</label>
                                    <span>Kg</span>
                                
                                    <label style="padding-left: 70px">仕上量</label>
                                
                                    <label class="check-group" style="margin-left: 20px;">
                                        <input type="radio" name="shiageryo" checked class="" data-prop="rdo_shiage" />
                                        配合量と同じ
                                    </label>
                               
                                    <label class="check-group">
                                        <input type="radio" name="shiageryo" class="" data-prop="rdo_shiage" />
                                        指定
                                    </label>
                                    <input style="width: 150px;text-align: right;" value="0.000000" data-prop="qty_max" />
                                    <span class="">Kg</span>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td rowspan="2" colspan="1" class="center">
                                <label>メモ</label>
                            </td>
                            <td rowspan="2" colspan="7">
                                <textarea name="memo" cols="10" rows="2" style="resize: none"></textarea>
                            </td>
                        </tr>
                    </tfoot>
                    <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->
                    <!--工程名追加　工程追加ボタン押下時に行追加-->
                    <tbody class="item-tmpl" style="cursor: default; display: none;">
                        <tr>
                            <!--TODO: 単一行を作成する場合は、下記を利用します。-->
                            <td>
                                <span class="select-tab unselected"></span>
                            </td>
                            <td class="kotei">
                                <span data-prop=""></span>
                            </td>
                            <td class="kotei">
                                <span data-prop=""></span>
                            </td>
                            <td class="kotei">
                                <span data-prop="">工程名</span>
                            </td>
                            <td colspan="5" class="kotei">
                                <input type="text" data-prop="nm_kotei" style="width: 95%;" />
                                <button class="btn btn-xs btn-info koteimei" style="min-width: 25px; margin-right: 5px;">&nbsp;</button>
                            </td>
                            <td class="row_kotei">
                                <input type="text" data-prop="kbn_bunkatsu" />
                            </td>
                            <td class="row_kotei">
                                <span data-prop="cd_hin"></span>
                            </td>
                            <td class="row_kotei">
                                <input type="text" data-prop="cd_hin" style="width: 70%; white-space: nowrap;" />
                                <button type="button" class="btn btn-xs btn-info cd_hin" style="min-width: 25px; margin-right: 5px;">&nbsp;</button>
                            </td>
                            <td class="row_kotei center">
                                <input type="checkbox" data-prop="flg_shitei" />
                            </td>
                            <td class="row_kotei">
                                <input type="text" data-prop="nm_hin" />
                            </td>
                            <td class="row_kotei">
                                <label data-prop="mark" style="width: 50%;"></label>
                                <button type="button" class="btn btn-xs btn-info mark" style="width: 40%;">&nbsp;</button>
                            </td>
                            <td class="row_kotei">
                                <input type="text" data-prop="qty_haigo" />
                            </td>
                            <td class="row_kotei">
                                <label data-prop="tani"></label>
                            </td>
                            <td class="row_kotei">
                                <input type="text" data-prop="qty_nisugata" />
                            </td>
                            <td class="row_kotei">
                                <label data-prop="hijyu"></label>
                            </td>
                            <td class="row_kotei">
                                <input type="text" data-prop="budomari" />
                            </td>
                            <td class="row_kotei">
                                <label data-prop="no_kikaku"></label>
                            </td>
                            <td class="row_kotei">
                                <input type="text" data-prop="gosa" />
                            </td>
                        </tr>
                    </tbody>
                    <!-- 工程内行追加　行追加ボタン　押下時に行追加-->
                    <%--<tbody class="item-tmpl row_kotei" style="cursor: default; display: none;">
                        <tr>
                            <!--TODO: 単一行を作成する場合は、下記を利用します。-->
                            <td>
                                <span class="select-tab unselected"></span>
                            </td>
                            <td>
                                <input type="text" data-prop="kbn_bunkatsu" />
                            </td>
                            <td>
                                <span data-prop="cd_hin"></span>
                            </td>
                            <td>
                                <input type="text" data-prop="cd_hin" style="width: 70%; white-space: nowrap;" />
                                <button type="button" class="btn btn-xs btn-info cd_hin" style="min-width: 25px; margin-right: 5px;">&nbsp;</button>
                            </td>
                            <td>
                                <input type="checkbox" data-prop="flg_shitei" />
                            </td>
                            <td>
                                <input type="text" data-prop="nm_hin" />
                            </td>
                            <td>
                                <label data-prop="mark" style="width: 50%;"></label>
                                <button type="button" class="btn btn-xs btn-info mark" style="width: 40%;">&nbsp;</button>
                            </td>
                            <td>
                                <input type="text" data-prop="qty_haigo" />
                            </td>
                            <td>
                                <label data-prop="tani"></label>
                            </td>
                            <td>
                                <input type="text" data-prop="qty_nisugata" />
                            </td>
                            <td>
                                <label data-prop="hijyu"></label>
                            </td>
                            <td>
                                <input type="text" data-prop="budomari" />
                            </td>
                            <td>
                                <label data-prop="no_kikaku"></label>
                            </td>
                            <td>
                                <input type="text" data-prop="gosa" />
                            </td>
                            <!--TODO: 多段行を作成する場合は、以下を利用し、上記３行は削除します。
                            <td rowspan="2">
                                <span class="select-tab-2lines unselected"></span>
                            </td>
                        </tr>
                        <tr>
                        -->
                        </tr>
                    </tbody>--%>
                </table>
        </div>

    </div>
</asp:Content>

<asp:Content ID="FooterContent" ContentPlaceHolderID="FooterContent" runat="server">
    <div class="command-left">
        <!--TODO: コマンドボタン（左寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" id="Button3" class="btn btn-sm btn-primary">版追加</button>
        <button type="button" id="Button6" class="btn btn-sm btn-primary">版削除</button>
    </div>
    <div class="command">
        <!--TODO: コマンドボタン（右寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" id="remove" style="display: none" class="btn btn-sm btn-default">削除</button>
        <button type="button" id="Button9" class="btn btn-sm btn-primary">CSV出力</button>
        <button type="button" id="save" class="btn btn-sm btn-primary">保存</button>
    </div>
</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>
