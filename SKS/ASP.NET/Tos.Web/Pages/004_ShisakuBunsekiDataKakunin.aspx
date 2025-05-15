<%@ Page Title="試作分析データ確認" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="004_ShisakuBunsekiDataKakunin.aspx.cs" Inherits="Tos.Web.Pages._004_ShisakuBunsekiDataKakunin" %>
<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchInputTable(Ver2.1)】 Template--%>

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

		/*TOsVN - 19075 - 2023/06/20: st ShohinKaihatuSupport Modify 2023*/
        textarea {
            resize: none;
            white-space: pre-wrap !important;
            background-color: transparent;
        }

       .datatable tbody td textarea {
            height: 75px !important;
            outline: none;
            border: none;
            margin: 0px;
            padding: 0px;
            vertical-align: middle;
        }

        .ellipsis-line {
            display: -webkit-box;
            -webkit-line-clamp: 4;
            -webkit-box-orient: vertical;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: normal;
            /*height: 36px;*/
            word-break: break-all;
        }
		/*ed ShohinKaihatuSupport Modify 2023*/

    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("004_ShisakuBunsekiDataKakunin", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.dataTakeCount,   // TODO:取得するデータ数を指定します。
                filter: ""
            },
            values: {
                isChangeRunning: {},
                transferDetail: "005_BunsekichiNyuryoku.aspx?cd_kaisha={0}&cd_busho={1}&cd_genryo={2}",
                transferNew: "005_BunsekichiNyuryoku.aspx",
                transferPages: {}
            },
            urls: {
                search: "../api/_004_ShisakuBunsekiDataKakunin",
                confirmDialog: "Dialogs/ConfirmDialog.aspx"
            },
            header: {
                options: {},
                values: {},
                urls: {
                    kaisha: "../api/BunsekichiNyuryoku/Get_kaisha",
                    kojyo: "../api/_004_ShisakuBunsekiDataKakunin/Get_busho",
                }
            },
            detail: {
                options: {},
                values: {},
                urls: {
                    downcsv: "../api/_004_ShisakuBunsekiDataKakunin/Get_csv"
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
                //page.validateAll().then(function () {

                var options = {
                        text: App.messages.app.AP0004
                    };

                    page.dialogs.confirmDialog.confirm(options)
                    .then(function () {
                        App.ui.loading.show();
                    var changeSets = page.detail.data.getChangeSet();
                    //TODO: データの保存処理をここに記述します。
                    return $.ajax(App.ajax.webapi["delete"](page.urls.search, changeSets))
                        .then(function (result) {

                            //TODO: データの保存成功時の処理をここに記述します。

                            //最後に再度データを取得しなおします。
                            return App.async.all([page.header.search(false)]);
                        }).then(function () {
                            App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                            App.ui.loading.close();
                        }).fail(function (error) {
                            if (error.status === App.settings.base.conflictStatus) {
                                // TODO: 同時実行エラー時の処理を行っています。
                                // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                                //page.header.search(false);
                                return App.async.all([page.header.search(false)])
                                .then(function () {
                                    App.ui.page.notifyAlert.clear();
                                    App.ui.page.notifyAlert.message(App.messages.base.MS0009).show();
                                    App.ui.loading.close();
                                    return;
                                });
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
                                App.ui.loading.close();
                                return;
                            }

                            App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                            App.ui.loading.close();

                        });
                        });
                //});
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
         * すべてのバリデーションを実行します。
         */
        page.validateAll = function () {

            var validations = [];

            validations.push(page.detail.validateList());

            //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。

            return App.async.all(validations);
        };

        /**
         * Windows閉じる際のイベントを定義します。
         * @return 文字列を返却した場合に確認メッセージが表示されます。
         */
        App.ui.page.onclose = function () {

            var detail,
                closeMessage = App.messages.base.exit;

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
            if (!App.isUndefOrNull(App.uri.queryStrings.id_session)) {//id_session
                page.values.id_session = parseFloat(App.uri.queryStrings.id_session);
            } else {
                page.values.id_session = null;
            }

            if (!App.isUndefOrNull(App.uri.queryStrings.cd_kaisha)) {//会社コード
                page.values.cd_kaisha = App.uri.queryStrings.cd_kaisha;
            } else {
                page.values.cd_kaisha = null;
            }

            if (!App.isUndefOrNull(App.uri.queryStrings.cd_kojo)) {//部署コード
                page.values.cd_kojo = App.uri.queryStrings.cd_kojo;
            } else {
                page.values.cd_kojo = null;
            }

            page.initializeControl();
            page.initializeControlEvent();

            page.header.initialize();
            page.detail.initialize();

            //TODO: ヘッダー/明細以外の初期化の処理を記述します。

            page.loadMasterData().then(function (result) {

                return page.loadDialogs();
            }).then(function (result) {
                //TODO: 画面の初期化処理成功時の処理を記述します。

                return page.loadData();
            }).then(function (result) {
                //工場コンボボックス
                if (!App.isUndefOrNull(page.values.cd_kojo)) {
                    $(".header").findP("cd_busho").val(page.values.cd_kojo);
                }

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
            $("#save").on("click", page.commands.save);
            $("#clear").on("click", page.header.clear);
            $("#downcsv").on("click", page.commands.csvDownload);
        };

        /**
         * CSVの出力を行います。
         */

        page.commands.csvDownload = function () {

            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            //TOsVN - 19075 - 2023/06/20: st ShohinKaihatuSupport Modify 2023
            if (page.values.id_session != null && page.values.id_session != 0 && page.values.paramIdSession != undefined) {
                page.commands.csvIdSessionDownload();
            } else {

                page.header.validator.validate().then(function () {

                    var query = page.header.createFilter(),
                    url = page.detail.urls.downcsv;



                    dataCount = page.detail.element.findP("data_count_total").text();
                    query.top = 1000000;

                    // ローディング表示
                    App.ui.loading.show();
                    App.ui.page.notifyAlert.clear();

                    // CSV出力（実体ファイルにアクセス）
                    return $.ajax(App.ajax.file.download(url, query)
                     ).then(function (response, status, xhr) {
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
            }
        }

        /**
        * Shohin Support 2023 : mode id_session
         * CSVの出力を行います。
         */
        page.commands.csvIdSessionDownload = function () {

            var query = page.values.paramIdSession,
                url = page.detail.urls.downcsv;

            dataCount = page.detail.element.findP("data_count_total").text();
            query.top = 1000000;

            // ローディング表示
            App.ui.loading.show();
            App.ui.page.notifyAlert.clear();

            // CSV出力（実体ファイルにアクセス）
            return $.ajax(App.ajax.file.download(url, query)
             ).then(function (response, status, xhr) {
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
        }

        /**
         * クリアボタンを活性
         */
        page.header.clear = function () {

            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            var element = page.header.element;

            element.findP("flg_shiyo").prop("disabled", true);
            element.findP("zenken").prop("checked", true);
            element.findP("checkGenryo").prop("checked", true);
            element.findP("checkNGenryo").prop("checked", true);
            element.findP("cd_genryo").val("");
            element.findP("nm_genryo").val("");
            page.loadMasterData();

            //clear detail
            page.options.skip = 0;
            page.detail.dataTable.dataTable("clear");
            page.detail.element.findP("data_count").text("");
            page.detail.element.findP("data_count_total").text("");
            $("#nextsearch").removeClass("show-search").hide();

            page.header.validator.validate();
            if (!$("#save").is(":disabled")) {
                $("#save").prop("disabled", true);
            }

        };

        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function () {

            //接続しているユーザーのID取得
            var userInfo = App.ui.page.user,
                id_user = userInfo.EmployeeCD;

            //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
            return $.ajax(App.ajax.odata.get(page.header.urls.kaisha, { id_user: id_user })).then(function (result) {

                var cd_kaisha = $(".header").findP("cd_kaisha");
                cd_kaisha.children().remove();
                App.ui.appendOptions(
                    cd_kaisha,
                    "cd_kaisha",
                    "nm_kaisha",
                    result.Items,
                    false
                );

                var cd_kaisha = Number($.findP("cd_kaisha").val());
                return $.ajax(App.ajax.odata.get(page.header.urls.kojyo, { cd_kaisha: cd_kaisha, fg_hyoji: 1 }))
            }).then(function (result) {

                    var cd_busho = $(".header").findP("cd_busho");
                    cd_busho.children().remove();
                    App.ui.appendOptions(
                        cd_busho,
                        "cd_busho",
                        "nm_busho",
                        result,
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
                //searchDialog: $.get(/* TODO:有ダイアログの URL */),
            }).then(function (result) {

                $("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;
                //$("#dialog-container").append(result.successes.searchDialog);
                //page.dialogs.searchDialog = /* TODO:共有ダイアログ変数名 */;
                //page.dialogs.searchDialog.initialize();
            });
        }

        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.validations = {
            //TODO: 画面ヘッダーのバリデーションの定義を記述します。
            cd_genryo:{
                rules: {
                    hankaku: true
                },
                options: {
                    name: '原料コード'
                },
                messages: {
                    hankaku: App.messages.base.hankaku
                }
            },
            cd_kaisha: {
                rules: {
                    required: true
                },
                options: {
                    name: '会社'
                },
                messages: {
                    required: App.messages.base.required
                }
            },
            checkGenryo: {
                rules: {
                    checkGenryo: function (value, opts, state, done) {
                        var checkGenryo = $.findP("checkGenryo").is(":checked"),
                            checkNGenryo = $.findP("checkNGenryo").is(":checked");
                        if (checkGenryo == false && checkNGenryo == false) {
                            done(false);
                        } else {
                            done(true);
                        }
                    }
                },
                options: {},
                messages: {
                    checkGenryo: App.messages.app.AP0160
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
            element.findP("flg_shiyo").prop("disabled", true);

            //TODO: 画面ヘッダーで利用するコントロールのイベントの紐づけ処理をここに記述します。
            element.on("click", "#search", page.header.before);
            element.on("change", ":input", page.header.change);



        };

        /**
         * 画面ヘッダーの変更時処理を定義します。
         */
        page.header.change = function (e) {
            var target = $(e.target),
                property = target.attr("data-prop")

            if (property == 'cd_kaisha') {
                page.header.kaishaCombox();
                $.findP("flg_shiyo").prop("disabled", true);
                $.findP("zenken").prop("checked", true);
            }

            if (property == 'cd_busho') {
                var cd_busho = $.findP("cd_busho").val();
                if (cd_busho == "") {
                    $.findP("flg_shiyo").prop("disabled", true);
                    $.findP("zenken").prop("checked", true);
                } else {
                    $.findP("flg_shiyo").prop("disabled", false);
                    $.findP("flg_shiyo").prop("checked", true);
                }
            }

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

        /*連動コンボボックス*/
        page.header.kaishaCombox = function () {
            cd_kaisha = page.header.element.findP("cd_kaisha").val();
            return $.ajax(App.ajax.odata.get(page.header.urls.kojyo, { cd_kaisha: cd_kaisha, fg_hyoji: 1 }))
                    .then(function (result) {

                        var cd_busho = $(".header").findP("cd_busho");
                        cd_busho.children().remove();
                        App.ui.appendOptions(
                            cd_busho,
                            "cd_busho",
                            "nm_busho",
                            result,
                            true
                        );
                    });
        };

        /**
        * 検索の前処理を定義します。
        */
        page.header.before = function (isLoading) {

            page.header.validator.validate().done(function () {
                var options = {
                    text: App.messages.app.AP0053
                };

                //検索の場合は明細変更チェックを行う
                if (isLoading) {
                    if (page.detail.data) {
                        detail = page.detail.data.getChangeSet();

                        if (detail.created.length || detail.updated.length || detail.deleted.length) {
                            page.dialogs.confirmDialog.confirm(options).then(function () {
                                page.header.search(isLoading)
                            });
                        } else {
                            page.header.search(isLoading)
                        }
                    }
                } else {
                    page.header.search(isLoading)
                }
            });
        };


        /**
         * 検索処理を定義します。
         */
        page.header.search = function (isLoading) {

            var deferred = $.Deferred(),
                element = page.header.element,
                query;

            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            page.header.validator.validate().done(function () {

                page.options.filter = page.header.createFilter();

                ////TODO: データ取得サービスの URLとオプションを記述します。
                //query = {
                //    url: "TODO: 検索結果取得サービスの URL を設定してください。",
                //    filter: page.options.filter,
                //    orderby: "TODO: ソート対象の列名", 
                //    top: page.options.top,       // TODO:取得するデータ数を指定します。
                //    inlinecount: "allpages"
                //};

                //TOsVN - 19075 - 2023/06/20: st ShohinKaihatuSupport Modify 2023
                if (!$("#downcsv").is(":disabled") && !App.isUndefOrNull(page.values.paramIdSession)) {
                    $("#downcsv").prop("disabled", true);
                }
                // ed ShohinKaihatuSupport Modify 2023
                
                var criteria = page.header.element.form().data();
                page.values.id_session = 0;
                page.options.filter = {
                    cd_kaisha: Number(criteria.cd_kaisha),
                    cd_busho: criteria.cd_busho,
                    cd_genryo: criteria.cd_genryo,
                    nm_genryo: criteria.nm_genryo,
                    checkGenryo: element.findP("checkGenryo").is(":checked") ? false : true,
                    checkNGenryo: element.findP("checkNGenryo").is(":checked") ? false : true,
                    flg_shiyo: element.find(".flg_shiyo").is(":checked") ? 1 : 0,
                    id_session: page.values.id_session,
                    skip: page.options.skip,
                    top: page.options.top
                }
                

                if (isLoading) {
                    App.ui.loading.show();
                    App.ui.page.notifyAlert.clear();
                }
                

                return $.ajax(App.ajax.webapi.get(page.urls.search, page.options.filter))
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
                    if (!$("#save").is(":disabled")) {
                        $("#save").prop("disabled", true);
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
                element = page.header.element,
                parameter = {};

            /* TODO: 検索条件のフィルターを定義してください。*/
            var criteria = page.header.element.form().data();

            page.options.skip = 0;
            parameter = {
                cd_kaisha: Number(criteria.cd_kaisha),
                cd_busho: criteria.cd_busho,
                cd_genryo: criteria.cd_genryo,
                nm_genryo: criteria.nm_genryo,
                checkGenryo: element.findP("checkGenryo").is(":checked") ? false : true,
                checkNGenryo: element.findP("checkNGenryo").is(":checked") ? false : true,
                flg_shiyo: element.find(".flg_shiyo").is(":checked") ? 1 : 0,
                skip: page.options.skip,
                top: page.options.top
            };

            return parameter;
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
         * 画面明細のバリデーションを定義します。
         */
        page.detail.options.validations = {
            //TODO: 画面明細のバリデーションの定義を記述します。
                cd_genryo: {
                    rules: { 
                        checkNgenryo:function (value, opts, state, done) {
                                var $tbody = state.tbody.element ? state.tbody.element : state.tbody;
                                var cd_genryo = $tbody.findP("cd_genryo").text(),
                                    cd_genryo = cd_genryo.substr(0, 1);
                                if (cd_genryo !== "N") {
                                    done(false);
                                } else {
                                    done(true);
                                }
                            }
                    },
                    options: {},
                    messages: {
                        checkNgenryo: App.messages.app.AP0103
                    }
                },
                haishi: {
                    rules: {
                        checkHaishi: function (value, opts, state, done) {
                                var $tbody = state.tbody.element ? state.tbody.element : state.tbody;
                                var haishi = $tbody.findP("haishi").text();
                                if (haishi !== "廃止") {
                                    done(false);
                                } else {
                                    done(true);
                                }
                            }
                    },
                    options: {},
                    messages: {
                        checkHaishi: App.messages.app.AP0104
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
                    height: 200,
                    resize: true,
                    //列固定横スクロールにする場合は、下記3行をコメント解除
					//TOsVN - 19075 - 2023/06/20: st ShohinKaihatuSupport Modify 2023
                    fixedColumn: true,                //列固定の指定
                    fixedColumns: 3,                  //固定位置を指定（左端を0としてカウント）
                    innerWidth: 1200,                 //可動列の合計幅を指定
                    // ed ShohinKaihatuSupport Modify 2023
                    onselect: page.detail.select,
                    onchange: page.detail.change
                });

            table = element.find(".datatable"); //列固定にした場合DOM要素が再作成されるため、変数を再取得します。

            page.detail.validator = element.validation(page.createValidator(page.detail.options.validations));
            page.detail.element = element;
            page.detail.dataTable = datatable;

            element.on("click", "#nextsearch", page.detail.nextsearch);
            element.on("click", "#add-item", page.detail.addNewItem);
            element.on("click", "#del-item", page.detail.deleteItem);

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
            page.detail.bind([], true);

            //TODO: 画面明細で利用するコントロールのイベントの紐づけ処理をここに記述します。
            table.on("click", ".transfer", page.detail.showSelect)

            //TOsVN - 19075 - 2023/06/20: st ShohinKaihatuSupport Modify 2023
            element.on("mouseenter", ".ellipsis-line", function (e) {
                var target = $(e.target);
                if (target.is(":input")) {
                    target.prop("title", target.val());
                } else {
                    target.prop("title", target.text());
                }
            });
            //ed ShohinKaihatuSupport Modify 2023
        };

        /**
         * 画面で処理の対象とするデータのロード処理を実行します。
         */
        page.loadData = function () {//試作データ画面遷移時
            if (!App.isUndefOrNull(page.values.cd_kaisha)) {//会社コンボボックス
                $(".header").findP("cd_kaisha").val(page.values.cd_kaisha);
                page.header.kaishaCombox();
            }
            if (page.values.id_session !== null) {

                //TOsVN - 19075 - 2023/06/20: st ShohinKaihatuSupport Modify 2023
                //if ($("#downcsv").is(":visible")) {
                //    $("#downcsv").hide();
                //}
                //ed

                //TODO: 画面内の処理の対象となるデータを取得し、画面にバインドする処理を記述します。
                var param = {
                    cd_kaisha: null,
                    cd_busho: null,
                    cd_genryo: null,
                    nm_genryo: null,
                    checkGenryo: null,
                    checkNGenryo: null,
                    flg_shiyo: null,
                    id_session: page.values.id_session,
                    skip: page.options.skip,
                    top: page.options.top
                }

                //TOsVN - 19075 - 2023/06/20: st ShohinKaihatuSupport Modify 2023
                page.values.paramIdSession = $.extend({}, param);
                //ed ShohinKaihatuSupport Modify 2023

                return $.ajax(App.ajax.webapi.get(page.urls.search, param)).then(function (result) {
                    page.detail.bind(result);
                });
            }

        };

        /**
         * 次のレコードを検索する処理を定義します。
         */
        page.detail.nextsearch = function () {

            var deferred = $.Deferred(),
                element = page.header.element,
                query;

            var criteria = page.header.element.form().data();
            page.options.filter = {
                cd_kaisha: Number(criteria.cd_kaisha),
                cd_busho: criteria.cd_busho,
                cd_genryo: criteria.cd_genryo,
                nm_genryo: criteria.nm_genryo,
                checkGenryo: element.findP("checkGenryo").is(":checked") ? false : true,
                checkNGenryo: element.findP("checkNGenryo").is(":checked") ? false : true,
                flg_shiyo: element.find(".flg_shiyo").is(":checked") ? 1 : 0,
                id_session: page.values.id_session,
                skip: page.options.skip,
                top: page.options.top
            }

            App.ui.loading.show();
            App.ui.page.notifyAlert.clear();

            return $.ajax(App.ajax.webapi.get(page.urls.search, page.options.filter))
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
            // TODO: 主キーが直接入力の場合には、修正の場合変更を不可とします。
            //appliers: {
            //    no_seq: function (value, element) {
            //        element.val(value);
            //        element.prop("readonly", true).prop("tabindex", -1);
            //        return true;
            //    }
            //}

            appliers: {

                ritu_sakusan: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.text("");
                    }
                    else {
                        element.text(App.num.format(Number(value), "#,#.00"));
                    }
                    return true;
                },

                ritu_shokuen: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.text("");
                    }
                    else {
                        element.text(App.num.format(Number(value), "#,#.00"));
                    }
                    return true;
                },

                ritu_sousan: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.text("");
                    }
                    else {
                        element.text(App.num.format(Number(value), "#,#.00"));
                    }
                    return true;
                },

                ritu_msg: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.text("");
                    }
                    else {
                        element.text(App.num.format(Number(value), "#,#.00"));
                    }
                    return true;
                },

                ritu_abura: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.text("");
                    }
                    else {
                        element.text(App.num.format(Number(value), "#,#.00"));
                    }
                    return true;
                },
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

                if (item.quantity !== null) {
                    row.find("td").addClass("back-yellow");
                }
                if (item.haishi == '廃止') {
                    row.find("td").addClass("back-gray");
                }

                row.form(page.detail.options.bindOption).bind(item);

                return row;
            }, true);

            if (!isNewData) {
                page.detail.element.findP("data_count").text(data.length);
                page.detail.element.findP("data_count_total").text(dataCount);
            }

            //if (dataCount >= App.settings.base.maxInputDataCount) {
               // App.ui.page.notifyInfo.message(App.messages.base.MS0011).show();
            //}

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
            

            //バリデーションを実行します。
            //page.detail.validateList(true);

        };

        /**
         * 画面明細の一覧の行が選択された時の処理を行います。
         */
        page.detail.select = function (e, row) {
            //TODO: 単一行を作成する場合は、下記２行を利用します。
            //$($(row.element[page.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab.selected")).removeClass("selected").addClass("unselected");
            //$(row.element[page.detail.fixedColumnIndex].querySelectorAll(".select-tab")).removeClass("unselected").addClass("selected");
            //TODO: 多段行を作成する場合は、下記２行を有効にし、上記の２行は削除します。
            $($(row.element[page.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab-2lines.selected")).removeClass("selected").addClass("unselected");
            $(row.element[page.detail.fixedColumnIndex].querySelectorAll(".select-tab-2lines")).removeClass("unselected").addClass("selected");

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

                if ($("#save").is(":disabled")) {
                    $("#save").prop("disabled", false);
                }

                //入力行の他の項目のバリデーション（必須チェック以外）を実施します
                page.detail.executeValidation(row.element.find("input"), row, options);
            }).always(function () {
                delete page.values.isChangeRunning[property];
            });
        };

        /**
         * 画面明細の一覧に新規データを追加します。
         */
        page.detail.addNewItem = function () {
            //TODO:新規データおよび初期値を設定する処理を記述します。
            //var newData = {
            //    //no_seq: page.values.no_seq
            //};

            //page.detail.data.add(newData);
            //page.detail.dataTable.dataTable("addRow", function (tbody) {
            //    tbody.form(page.detail.options.bindOption).bind(newData);
            //    return tbody;
            //}, true);

            //if ($("#save").is(":disabled")) {
            //    $("#save").prop("disabled", false);
            //}

            url = App.str.format(page.values.transferNew);

            winObj = App.ui.transfer(url, { wins: page.values.transferPages });
            page.values.transferPages[winObj.name] = winObj.win;
        };

        /**
         * 画面明細の一覧で選択されている行とデータを削除します。
         */
        page.detail.deleteItem = function (e) {
                var element = page.detail.element,
                    //TODO: 単一行を作成する場合は、下記を利用します。
                    //selected = element.find(".datatable .select-tab.selected").closest("tbody");
                    //TODO: 多段行を作成する場合は、以下を利用し、上記１行は削除します。
                    selected = element.find(".datatable .select-tab-2lines.selected").closest("tbody");

                if (!selected.length) {
                    return;
                }
                page.detail.dataTable.dataTable("getRow", selected, function (row){
                    App.ui.page.notifyWarn.clear();
                    App.ui.page.notifyAlert.clear();
                    App.ui.page.notifyInfo.clear();
                    $("tr").removeClass("has-error");

                    page.detail.executeValidation(row.element.find("span"), row).then(function () {

                        page.detail.dataTable.dataTable("deleteRow", selected, function (row) {
                            var id = row.attr("data-key"),
                            newSelected;
                    
                            row.find(":input").each(function (i, elem) {
                                App.ui.page.notifyAlert.remove(elem);
                            });

                            if (!App.isUndefOrNull(id)) {
                                var entity = page.detail.data.entry(id);
                                page.detail.data.remove(entity);

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
                            }
                            if ($("#save").is(":disabled")) {
                                $("#save").prop("disabled", false);
                            }

                        });
                });
            });
   
        };

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
            },
            execOptions = $.extend(true, {}, defaultOptions, options);

            return page.detail.validator.validate(execOptions);
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
                validations.push(page.detail.executeValidation(row.element.find("span"), row.element, options));
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

                row.findP("cd_torihiki").val(data.cd_torihiki).change();
                row.findP("nm_torihiki").text(data.nm_torihiki);

                delete page.dialogs.searchDialog.dataSelected;
            }
        };
--%>

        //画面明細の一覧から詳細画面に遷移します。
        page.detail.showSelect = function (e) {

            var elem = $(e.target).closest("tbody"),
                tbody, id, data, url, winObj;

            page.detail.dataTable.dataTable("getRow", elem, function (row) {
                tbody = row.element;
            });

            e.preventDefault();
            id = tbody.attr("data-key");
            if (App.isUndef(id)) {
                return;
            }

            data = page.detail.data.entry(id);
            url = App.str.format(page.values.transferDetail, data.cd_kaisha, data.cd_busho, data.cd_genryo);

            winObj = App.ui.transfer(url, { wins: page.values.transferPages });
            page.values.transferPages[winObj.name] = winObj.win;
        }


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
                    <div class="control col-xs-4">
                        <label style="width:100px;">新規原料</label>
                        <input type="checkbox" style="width: 45px;" checked data-prop="checkGenryo"/ >
                        <label style="width: 100px;">既存原料</label>
                        <input type="checkbox" style="width: 45px;" checked data-prop="checkNGenryo"/ >
                    </div>
                    <div class="control col-xs-8">
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>原料コード</label>
                    </div>
                    <div class="control col-xs-1">
                        <input type="tel" data-prop="cd_genryo" maxlength="6"/>
                    </div>
                    <div class="control col-xs-3">
                        <span>※データチェックと確認チェックは別に行ってください</span>
                    </div>
                    <div class="control-label col-xs-2">
                        <label style="width: 10px;">三ヶ月</label>
                        <input type="radio" style="width: 50px;" name="1" data-prop="flg_shiyo" class="flg_shiyo"/>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>会社<i class="caution">*</i></label>
                    </div>
                    <div class="control col-xs-2">
                        <select data-prop="cd_kaisha"></select>
                    </div>
                    <div class="control col-xs-2">
                        <span style="white-space:nowrap;"></span>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>原料名称</label>
                    </div>
                    <div class="control col-xs-1">
                        <input type="text" data-prop="nm_genryo" maxlength="60"/>
                    </div>
                    <div class="control col-xs-3">
                        <span>※データ変換すると確認項目は消えます</span>
                    </div>
                    <div class="control-label col-xs-2">
                        <label style="width: 10px;">全件</label>
                        <input type="radio" style="width: 50px;" name="1" checked data-prop="zenken"/>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>工場</label>
                    </div>
                    <div class="control col-xs-2">
                        <select data-prop="cd_busho"></select>
                    </div>
                    <div class="control col-xs-2">
                        <span style="white-space:nowrap;"></span>
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
            <div title="TODO: 明細一覧部のタイトルを設定します。"  class="part">
            -->
                <div class="control-label toolbar">
                    <i class="icon-th"></i>
                    <div class="btn-group">
                        <button type="button" class="btn btn-default btn-xs" id="add-item">新規</button>
                        <button type="button" class="btn btn-default btn-xs" id="del-item">削除</button>
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
                           <%-- <th style="width: 10px;"></th>--%>
                        <%--TODO: 多段行を作成する場合は、以下を利用し、上記１行は削除します。--%>
                            <th rowspan="2" style="width: 10px;"></th>
                            <th rowspan="2" style="width: 40px;">詳細</th>
                            <th rowspan="2" style="width: 100px;">原料CD</th>
                            <th rowspan="2" style="width: 200px;">原料名</th>
                            <th rowspan="2" style="width: 50px;"hidden>会社</th>
                            <th rowspan="2" style="width: 200px;">工場</th>
                            <th style="width: 100px;">三ヶ月</th>
                            <th rowspan="2" style="width: 60px;">酢酸<br>(%)</th>
                            <th rowspan="2" style="width: 60px;">食塩<br>(%)</th>
                            <th rowspan="2" style="width: 60px;">総酸<br>(%)</th>
                            <th rowspan="2" style="width: 60px;">MSG<br>(%)</th>
                            <th rowspan="2" style="width: 60px;">油含<br>(%)</th>
                            <th rowspan="2" style="width: 500px;">表示案</th>
                            <th rowspan="2" style="width: 500px;">表示案<br />G-Mer</th>
                            <th rowspan="2" style="width: 500px;">添加物</th>
                            <th rowspan="2" style="width: 500px;">添加物<br>G-Mer（表示要）</th>
                            <th rowspan="2" style="width: 500px;">添加物<br>G-Mer（表示不要）</th>
                            <th rowspan="2" style="width: 250px;">アレルギー情報<br>G-Mer（表示要）</th>
                            <th rowspan="2" style="width: 250px;">アレルギー情報<br>G-Mer（表示不要）</th>
                            <th rowspan="2" style="width: 500px;">メモ</th>
                            <th rowspan="2" style="width: 150px;">ラボ<br>荷姿</th>
                            <th rowspan="2" style="width: 100px;">ラボ<br>異物ランク</th>
                            <th rowspan="2" style="width: 100px;">ラボ<br>原料使用可否</th>
                            <th rowspan="2" style="width: 500px;">ラボ<br>原料使用可否<br>設定理由</th>
                            <th rowspan="2" style="width: 100px;">ラボ<br>原料使用可否<br>代替推奨原料<br>コード</th>
                            <th rowspan="2" style="width: 250px;">ラボ<br>原料使用可否<br>代替推奨原料名</th>
                            <th rowspan="2" style="width: 100px;">ラボ<br>過去トラブル<br>情報</th>
                            <th rowspan="2" style="width: 250px;">ラボ<br>トラブル概要</th>
                            <th rowspan="2" style="width: 500px;">ラボ<br>トラブル内容<br>詳細</th>
                            <th rowspan="2" style="width: 100px;">ラボ<br>殺菌調味液<br>要否</th>
                            <th rowspan="2" style="width: 500px;">ラボ<br>殺菌調味液<br>条件</th>
                            <th rowspan="2" style="width: 100px;">ラボ<br>NB限定原料</th>
                            <th rowspan="2" style="width: 250px;">ラボ<br>NB限定原料<br>条件</th>
                            <th rowspan="2" style="width: 250px;">ラボ<br>NB限定原料<br>設定理由</th>
                            <th rowspan="2" style="width: 100px;">ラボ<br>酵素活性</th>
                            <th rowspan="2" style="width: 100px;">ラボ<br>合成添加物申請</th>
                            <th rowspan="2" style="width: 500px;">ラボ<br>備考</th>
                            <th rowspan="2" style="width: 100px;">最終使用日</th>
                            <th rowspan="2" style="width: 100px;">確定コード</th>
                            <th rowspan="2" style="width: 100px;">廃止区</th>
                            <th rowspan="2" style="width: 100px;" hidden>量</th>
                       	</tr>
                        <tr>
						    <th style="width: 50px;">未使用</th>
                        </tr>
                    </thead>
                    <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->
                    <tbody class="item-tmpl" style="cursor: default; display: none;">
                        <tr style="height: 40px">
                        <!--TODO: 単一行を作成する場合は、以下を利用します。-->
						    <%--<td>
                                <span class="select-tab unselected"></span>
                            </td>--%>
                        <%--TODO: 多段行を作成する場合は、以下を利用し、上記３行は削除します。--%>
							
                            <td rowspan="2">
                                <span class="select-tab-2lines unselected"></span>
                            </td>
                            <td rowspan="2" >
                                <button type="button" class="transfer btn btn-info btn-xs">詳細</button>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word; ">
                                <span data-prop="cd_genryo"></span>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word; ">
                                <span data-prop="nm_genryo"></span>
                            </td>
                            <td rowspan="2" hidden>
                                <span data-prop="cd_kaisha"></span>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word; ">
                                <span data-prop="cd_busho" style="display: none"></span>
                                <%--st ShohinKaihatuSupport Modify 2023: span -> textarea --%>
                                <span data-prop="nm_busho"></span>
                                <%--<textarea data-prop="nm_busho" class="ellipsis-line" disabled="disabled"></textarea>--%>
                            </td>
                            <td style="word-wrap:break-word;" class="text-center">
                                <span data-prop="shiyo"></span>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word; vertical-align: top !important;" class="text-right">
                                <span data-prop="ritu_sakusan"></span>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word; vertical-align: top !important;" class="text-right">
                                <span data-prop="ritu_shokuen"></span>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word; vertical-align: top !important;" class="text-right">
                                <span data-prop="ritu_sousan"></span>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word; vertical-align: top !important;" class="text-right">
                                <span data-prop="ritu_msg"></span>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word; vertical-align: top !important;" class="text-right">
                                <span data-prop="ritu_abura"></span>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word;">
                                <%--st ShohinKaihatuSupport Modify 2023: span -> textarea --%>
                                <%--<span data-prop="hyojian"></span>--%>
                                <textarea data-prop="hyojian" class="ellipsis-line" disabled="disabled"></textarea>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word;">
                                <%--st ShohinKaihatuSupport Modify 2023: span -> textarea --%>
                                <%--<span data-prop="RMLABELSAMPLE"></span>--%>
                                <textarea data-prop="RMLABELSAMPLE" class="ellipsis-line" disabled="disabled"></textarea>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word;">
                                <%--st ShohinKaihatuSupport Modify 2023: span -> textarea --%>
                                <%--<span data-prop="tenkabutu"></span>--%>
                                <textarea data-prop="tenkabutu" class="ellipsis-line" disabled="disabled"></textarea>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word;">
                                <%--st ShohinKaihatuSupport Modify 2023: span -> textarea --%>
                                <%--<span data-prop="AMS_YO"></span>--%>
                                <textarea data-prop="AMS_YO" class="ellipsis-line" disabled="disabled"></textarea>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word;">
                                <%--st ShohinKaihatuSupport Modify 2023: span -> textarea --%>
                                <%--<span data-prop="AMS_FUYO"></span>--%>
                                <textarea data-prop="AMS_FUYO" class="ellipsis-line" disabled="disabled"></textarea>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word;">
                                <%--st ShohinKaihatuSupport Modify 2023: span -> textarea --%>
                                <%--<span data-prop="ALG_ALR"></span>--%>
                                <textarea data-prop="ALG_ALR" class="ellipsis-line" disabled="disabled"></textarea>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word;">
                                <%--st ShohinKaihatuSupport Modify 2023: span -> textarea --%>
                                <%--<span data-prop="ALG_FUYO"></span>--%>
                                <textarea data-prop="ALG_FUYO" class="ellipsis-line" disabled="disabled"></textarea>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word;">
                                <%--st ShohinKaihatuSupport Modify 2023: span -> textarea --%>
                                <%--<span data-prop="memo"></span>--%>
                                <textarea data-prop="memo" class="ellipsis-line" disabled="disabled"></textarea>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word; vertical-align: top !important;">
                                <span data-prop="nisugata_hyoji"></span>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word; vertical-align: top !important;">
                                <span data-prop="rank_ibutsu"></span>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word; vertical-align: top !important;">
                                <span data-prop="shiyokahi_genryo"></span>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word;">
                                <%--st ShohinKaihatuSupport Modify 2023: span -> textarea --%>
                                <%--<span data-prop="shiyokahi_riyu"></span>--%>
                                <textarea data-prop="shiyokahi_riyu" class="ellipsis-line" disabled="disabled"></textarea>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word; vertical-align: top !important;">
                                <span data-prop="shiyokahi_cd_genryo_daitai"></span>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word; vertical-align: top !important;">
                                <span data-prop="shiyokahi_nm_genryo_daitai"></span>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word; vertical-align: top !important;">
                                <span data-prop="trouble_joho"></span>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word;">
                                <%--st ShohinKaihatuSupport Modify 2023: span -> textarea --%>
                                <%--<span data-prop="trouble_gaiyo"></span>--%>
                                <textarea data-prop="trouble_gaiyo" class="ellipsis-line" disabled="disabled"></textarea>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word;">
                                <%--st ShohinKaihatuSupport Modify 2023: span -> textarea --%>
                                <%--<span data-prop="trouble_naiyo_shosai"></span>--%>
                                <textarea data-prop="trouble_naiyo_shosai" class="ellipsis-line" disabled="disabled"></textarea>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word;">
                                <%--st ShohinKaihatuSupport Modify 2023: span -> textarea --%>
                                <%--<span data-prop="sakkin_chomieki_yohi"></span>--%>
                                <textarea data-prop="sakkin_chomieki_yohi" class="ellipsis-line" disabled="disabled"></textarea>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word;" class="number">
                                <%--st ShohinKaihatuSupport Modify 2023: span -> textarea --%>
                                <%--<span data-prop="sakkin_chomieki_joken"></span>--%>
                                <textarea data-prop="sakkin_chomieki_joken" class="ellipsis-line" disabled="disabled"></textarea>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word; vertical-align: top !important;">
                                <span data-prop="NB_genteigenryo"></span>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word;">
                                <%--st ShohinKaihatuSupport Modify 2023: span -> textarea --%>
                                <%--<span data-prop="NB_joken"></span>--%>
                                <textarea data-prop="NB_joken" class="ellipsis-line" disabled="disabled"></textarea>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word;">
                                <%--st ShohinKaihatuSupport Modify 2023: span -> textarea --%>
                                <%--<span data-prop="NB_riyu"></span>--%>
                                <textarea data-prop="NB_riyu" class="ellipsis-line" disabled="disabled"></textarea>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word; vertical-align: top !important;">
                                <span data-prop="kasseikoso"></span>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word;">
                                <%--st ShohinKaihatuSupport Modify 2023: span -> textarea --%>
                                <%--<span data-prop="gosei_tenkabutu"></span>--%>
                                <textarea data-prop="gosei_tenkabutu" class="ellipsis-line" disabled="disabled"></textarea>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word;">
                                <%--st ShohinKaihatuSupport Modify 2023: span -> textarea --%>
                                <%--<span data-prop="biko"></span>--%>
                                <textarea data-prop="biko" class="ellipsis-line" disabled="disabled"></textarea>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word; vertical-align: top !important;">
                                <span data-prop="dt_koshin" ></span>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word; vertical-align: top !important;">
                                <span data-prop="cd_kakutei"></span>
                            </td>
                            <td rowspan="2" style="word-wrap:break-word; vertical-align: top !important;">
                                <span data-prop="haishi"></span>
                            </td>
                            <td rowspan="2" hidden>
                                <span data-prop="quantity"></span>
                            </td>
                        </tr>
                        <tr style="height: 40px">
                            <td style="word-wrap:break-word;" class="text-center">
                                <span data-prop="mishiyo"></span>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="detail-command">
                    <button type="button" id="nextsearch" class="btn btn-sm btn-primary btn-next-search" style="display: none">次を検索</button>
                </div>
                <div class="part-command">
                </div>

            <%--TODO: 明細をpartにする場合は以下を利用します。--%>
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
        <button type="button" id="save" class="btn btn-sm btn-primary" disabled="disabled">保存</button>
    </div>

</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>