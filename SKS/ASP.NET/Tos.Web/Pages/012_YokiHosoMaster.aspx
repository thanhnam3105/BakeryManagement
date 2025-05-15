<%@ Page Title="012_容器包装マスタ" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="_012_YokiHosoMaster.aspx.cs" Inherits="Tos.Web.Pages._012_YokiHosoMaster" %>

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
    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("_012_YokiHosoMaster", {
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
                ma_yoki_hoso: "../api/_012_YokiHosoMaster",
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
            },
            header: {
                options: {},
                values: {},
                urls: {
                    search: "../api/_012_YokiHosoMaster",
                }
            },
            detail: {
                options: {},
                values: {},
                urls: {
                    ma_yoki_hoso_shizai: "../Services/ShisaQuickService.svc/vw_yoki_hoso_shizai"
                }
            },
            dialogs: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
            },
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
                page.validateAll().then(function () {
                    var options = {
                        text: App.messages.app.AP0004
                    };

                    page.dialogs.confirmDialog.confirm(options)
                    .then(function () {
                        App.ui.loading.show();
                        var changeSets = page.detail.data.getChangeSet();
                        //TODO: データの保存処理をここに記述します。
                        return $.ajax(App.ajax.webapi.post(page.urls.ma_yoki_hoso, changeSets))
                            .then(function (result) {

                                //TODO: データの保存成功時の処理をここに記述します。

                                //App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                                //最後に再度データを取得しなおします。
                                return App.async.all([page.header.search(false)]);
                            }).then(function () {
                                App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                                App.ui.loading.close();
                            }).fail(function (error) {

                                if (error.status === App.settings.base.conflictStatus) {
                                    // TODO: 同時実行エラー時の処理を行っています。
                                    // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                                    page.header.search(false);
                                    App.ui.page.notifyAlert.clear();
                                    App.ui.page.notifyAlert.message(App.messages.base.MS0009).show();
                                    return;
                                    //} else {
                                    //    page.header.search(false);
                                    //    App.ui.page.notifyAlert.clear();
                                    //    App.ui.page.notifyAlert.message(App.messages.base.MS0009).show();
                                    //    App.ui.loading.close();
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
                            }).always(function () {
                                setTimeout(function () {
                                    page.header.element.find(":input:first").focus();
                                }, 100);
                                App.ui.loading.close();
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

            page.initializeControl();
            page.initializeControlEvent();

            page.header.initialize();
            page.detail.initialize();

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
            $("#save").on("click", page.commands.save);
        };

        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function () {

            //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
            return $.ajax(App.ajax.odata.get(page.detail.urls.ma_yoki_hoso_shizai)).then(function (result) {

                var cd_yoki_hoso_shizai01 = $.findP("cd_yoki_hoso_shizai01");
                cd_yoki_hoso_shizai01.children().remove();
                App.ui.appendOptions(
                    cd_yoki_hoso_shizai01,
                    "cd_yoki_hoso_shizai",
                    "nm_yoki_hoso_shizai",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(page.detail.urls.ma_yoki_hoso_shizai))
            }).then(function (result) {

                var cd_yoki_hoso_shizai02 = $.findP("cd_yoki_hoso_shizai02");
                cd_yoki_hoso_shizai02.children().remove();
                App.ui.appendOptions(
                    cd_yoki_hoso_shizai02,
                    "cd_yoki_hoso_shizai",
                    "nm_yoki_hoso_shizai",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(page.detail.urls.ma_yoki_hoso_shizai))
            })
            .then(function (result) {

                var cd_yoki_hoso_shizai03 = $.findP("cd_yoki_hoso_shizai03");
                cd_yoki_hoso_shizai03.children().remove();
                App.ui.appendOptions(
                    cd_yoki_hoso_shizai03,
                    "cd_yoki_hoso_shizai",
                    "nm_yoki_hoso_shizai",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(page.detail.urls.ma_yoki_hoso_shizai))
            }).then(function (result) {

                var cd_yoki_hoso_shizai04 = $.findP("cd_yoki_hoso_shizai04");
                cd_yoki_hoso_shizai04.children().remove();
                App.ui.appendOptions(
                    cd_yoki_hoso_shizai04,
                    "cd_yoki_hoso_shizai",
                    "nm_yoki_hoso_shizai",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(page.detail.urls.ma_yoki_hoso_shizai))
            }).then(function (result) {

                var cd_yoki_hoso_shizai05 = $.findP("cd_yoki_hoso_shizai05");
                cd_yoki_hoso_shizai05.children().remove();
                App.ui.appendOptions(
                    cd_yoki_hoso_shizai05,
                    "cd_yoki_hoso_shizai",
                    "nm_yoki_hoso_shizai",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(page.detail.urls.ma_yoki_hoso_shizai))
            }).then(function (result) {

                var cd_yoki_hoso_shizai06 = $.findP("cd_yoki_hoso_shizai06");
                cd_yoki_hoso_shizai06.children().remove();
                App.ui.appendOptions(
                    cd_yoki_hoso_shizai06,
                    "cd_yoki_hoso_shizai",
                    "nm_yoki_hoso_shizai",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(page.detail.urls.ma_yoki_hoso_shizai))
            }).then(function (result) {

                var cd_yoki_hoso_shizai07 = $.findP("cd_yoki_hoso_shizai07");
                cd_yoki_hoso_shizai07.children().remove();
                App.ui.appendOptions(
                    cd_yoki_hoso_shizai07,
                    "cd_yoki_hoso_shizai",
                    "nm_yoki_hoso_shizai",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(page.detail.urls.ma_yoki_hoso_shizai))
            }).then(function (result) {

                var cd_yoki_hoso_shizai08 = $.findP("cd_yoki_hoso_shizai08");
                cd_yoki_hoso_shizai08.children().remove();
                App.ui.appendOptions(
                    cd_yoki_hoso_shizai08,
                    "cd_yoki_hoso_shizai",
                    "nm_yoki_hoso_shizai",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(page.detail.urls.ma_yoki_hoso_shizai))
            }).then(function (result) {

                var cd_yoki_hoso_shizai09 = $.findP("cd_yoki_hoso_shizai09");
                cd_yoki_hoso_shizai09.children().remove();
                App.ui.appendOptions(
                    cd_yoki_hoso_shizai09,
                    "cd_yoki_hoso_shizai",
                    "nm_yoki_hoso_shizai",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(page.detail.urls.ma_yoki_hoso_shizai))
            }).then(function (result) {

                var cd_yoki_hoso_shizai10 = $.findP("cd_yoki_hoso_shizai10");
                cd_yoki_hoso_shizai10.children().remove();
                App.ui.appendOptions(
                    cd_yoki_hoso_shizai10,
                    "cd_yoki_hoso_shizai",
                    "nm_yoki_hoso_shizai",
                    result.value,
                    true
                );
                return $.ajax(App.ajax.odata.get(page.detail.urls.ma_yoki_hoso_shizai))
            });

            //TODO: マスタデータのロード処理を実装後、下の1行を削除してください。
            //return App.async.success();
        };

        /**
         * 共有ダイアログのロード処理を実行します。

        // */
        //page.loadDialogs = function () {

        //    //TODO: 共有ダイアログデータを取得し、画面にバインドする処理を記述します。
        //    return App.async.all({

        //        //searchDialog: $.get(/* TODO:有ダイアログの URL */),
        //    }).then(function (result) {

        //        //$("#dialog-container").append(result.successes.searchDialog);
        //        //page.dialogs.searchDialog = /* TODO:共有ダイアログ変数名 */;
        //        //page.dialogs.searchDialog.initialize();
        //    });
        //}
        page.loadDialogs = function () {

            //TODO: 共有ダイアログデータを取得し、画面にバインドする処理を記述します。
            return App.async.all({
                confirmDialog: $.get(page.urls.confirmDialog)
            }).then(function (result) {

                $("#dialog-container").append(result.successes.confirmDialog);
                $("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;
            });
        }
        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.validations = {
            //TODO: 画面ヘッダーのバリデーションの定義を記述します。
            nm_yoki_hoso: {
                rules: {
                    maxbytelength: 80
                },
                options: {
                    name: "容器包装名",
                    byte: 40
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            nm_yoki_hoso_shizai: {
                rules: {
                    maxlength: 100
                },
                options: {
                    name: "容器包装資材名",
                },
                messages: {
                    maxlength: App.messages.base.maxlength
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
        * 検索の前処理を定義します。
        */
        page.header.search = function (isLoading) {

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
                                page.header.searchrun(isLoading)
                            });
                        } else {
                            page.header.searchrun(isLoading)
                        }
                    }
                } else {
                    page.header.searchrun(isLoading)
                }
            });
        };

        /**
         * 検索処理を定義します。
         */

        page.header.searchrun = function (isLoading) {

            var deferred = $.Deferred(),
                query;
            page.options.skip = 0;
            page.header.validator.validate().done(function () {
                //TODO: データ取得サービスの URLとオプションを記述します。
                var criteria = page.header.element.form().data();
                page.options.filter = {
                    nm_yoki_hoso_shizai: criteria.nm_yoki_hoso_shizai,
                    nm_yoki_hoso: criteria.nm_yoki_hoso,
                    top: page.options.top,       // TODO:取得するデータ数を指定します。
                    skip: page.options.skip     // TODO:先頭からスキップするデータ数を指定します。
                };

                if (isLoading) {
                    App.ui.loading.show();
                    App.ui.page.notifyAlert.clear();
                }

                return $.ajax(App.ajax.webapi.get(page.header.urls.search, page.options.filter))
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
            var criteria = page.header.element.form().data();
            //    filters = [];

            ////    /* TODO: 検索条件のフィルターを定義してください。*/
            //    if (!App.isUndefOrNull(criteria.nm_yoki_hoso) && criteria.nm_yoki_hoso.length > 0) {
            //        filters.push("substringof('" + encodeURIComponent(criteria.nm_yoki_hoso) + "', nm_yoki_hoso) eq true");
            //    }

            //    if (!App.isUndefOrNull(criteria.nm_yoki_hoso_shizai) && criteria.nm_yoki_hoso_shizai.length > 0) {
            //        filters.push("substringof('" + encodeURIComponent(criteria.nm_yoki_hoso_shizai) + "', nm_yoki_hoso_shizai01) eq true");
            //    }

            //    return filters.join(" and ");

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
            nm_yoki_hoso: {
                rules: {
                    maxbytelength: 80,
                    required: true
                },
                options: {
                    name: "容器包装名",
                    byte: 40
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength,
                    required: App.messages.base.required
                }
            },
            cd_yoki_hoso_shizai01: {
                rules: {
                    required: true,
                    cd_shizai_checked: function (value, opts, state, done) {
                        done(page.detail.cd_shizai_checked(value, state));
                    }
                },
                options: {
                    name: "資材1",
                    byte: 30
                },
                messages: {
                    required: App.messages.base.required,
                    cd_shizai_checked: App.messages.app.AP0163
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
                   fixedColumn: true,                //列固定の指定
                   fixedColumns: 2,                  //固定位置を指定（左端を0としてカウント）
                   innerWidth: 3000,                 //可動列の合計幅を指定
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

        };
        /**
        * 次のレコードを検索する処理を定義します。
        */
        page.detail.nextsearch = function () {

            var criteria = page.header.element.form().data();
            page.options.filter = {
                nm_yoki_hoso_shizai: criteria.nm_yoki_hoso_shizai,
                nm_yoki_hoso: criteria.nm_yoki_hoso,
                skip: page.options.skip,     // TODO:先頭からスキップするデータ数を指定します。
                top: page.options.top       // TODO:取得するデータ数を指定します。
            };

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
        page.detail.options.bindOption = {
            //// TODO: 主キーが直接入力の場合には、修正の場合変更を不可とします。
            //appliers: {
            //    cd_yoki_hoso: function (value, element) {
            //        element.text(value);
            //        element.prop("readonly", true).prop("tabindex", -1);
            //        return true;
            //    }
            //}
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


            //バリデーションを実行します。
            //page.detail.validateList(true);

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
         * 画面明細の一覧に新規データを追加します。
         */
        page.detail.addNewItem = function () {
            //TODO:新規データおよび初期値を設定する処理を記述します。
            var newData = {
                //no_seq: page.values.no_seq
            };

            page.detail.data.add(newData);
            page.detail.dataTable.dataTable("addRow", function (tbody) {
                tbody.form(page.detail.options.bindOption).bind(newData);
                return tbody;
            }, true);

            if ($("#save").is(":disabled")) {
                $("#save").prop("disabled", false);
            }
        };
        ////* 保存前のチェックを行います。
        //searchBeforeCK  = function (e) {
        //    //DB上の分析情報確認チェックボックスにチェックが入ってるデータが変更された場合は確認ダイアログを表示する
        //    var oldChkKakunin = $('#old_chk_kakunin');
        //    var element = page.detailInput.element,
        //       target = $(e.target),
        //       id = element.attr("data-key"),
        //       property = target.attr("data-prop"),
        //       data = element.form().data(),
        //       entity = page.detailInput.data.entry(id);

        //    if (oldChkKakunin.is(':checked') && flgKakuninChk == false) {
        //        var options = {
        //            text: App.messages.app.AP0053
        //        };
        //    }
        //    else {
        //        page.commands.editItem();
        //    }

        //};
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

            if ($("#save").is(":disabled")) {
                $("#save").prop("disabled", false);
            }
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
            return (method !== "required" && method !== "cd_shizai_checked");
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

        /**
         * 画面明細の資材１～１０の組み合わせチェックを実行します。
         */
        page.detail.cd_shizai_checked = function (value, state) {
            var $tbody = state.tbody.element ? state.tbody.element : state.tbody,
                shizai01 = $tbody.findP("cd_yoki_hoso_shizai01").val(),
                shizai02 = $tbody.findP("cd_yoki_hoso_shizai02").val(),
                shizai03 = $tbody.findP("cd_yoki_hoso_shizai03").val(),
                shizai04 = $tbody.findP("cd_yoki_hoso_shizai04").val(),
                shizai05 = $tbody.findP("cd_yoki_hoso_shizai05").val(),
                shizai06 = $tbody.findP("cd_yoki_hoso_shizai06").val(),
                shizai07 = $tbody.findP("cd_yoki_hoso_shizai07").val(),
                shizai08 = $tbody.findP("cd_yoki_hoso_shizai08").val(),
                shizai09 = $tbody.findP("cd_yoki_hoso_shizai09").val(),
                shizai10 = $tbody.findP("cd_yoki_hoso_shizai10").val(),
                //資材１～１０までの値で配列を作成
                shizaiArray = new Array(shizai01,shizai02,shizai03,shizai04,shizai05,shizai06,shizai07,shizai08,shizai09,shizai10),
                //重複している値だけのリストを作成
                duplicateList = shizaiArray.filter(function (x, i, self) {
                return self.indexOf(x) === i && i !== self.lastIndexOf(x);
                    });
            //重複データなしまたは未選択データのみ重複している場合
            if(duplicateList.length == 0 || (duplicateList.length == 1 && duplicateList[0] == "")){
                return true;
            }else{
                return false;
            }
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
                        <label>容器包装名</label>
                    </div>
                    <div class="control col-xs-5">
                        <input type="text" class="ime-active" data-prop="nm_yoki_hoso" />
                    </div>
                    <div class="control col-xs-6">
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>資材</label>
                    </div>
                    <div class="control col-xs-5">
                        <input type="text" class="ime-active" data-prop="nm_yoki_hoso_shizai" />
                    </div>
                    <div class="header-command">
                        <button type="button" id="search" class="btn btn-sm btn-primary">検索</button>
                    </div>
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
                <span>容器包装  </span>
                <div class="btn-group">
                    <button type="button" class="btn btn-default btn-xs" id="add-item">追加</button>
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
                        <th rowspan="1" style="width: 10px;"></th>
                        <th style="width: 60px;" class="code">コード</th>
                        <th rowspan="1" style="width: 600px;">容器包装名<i class="caution">*</i></th>
                        <th rowspan="1" style="width: 300px;" class="shizai1">資材1<i class="caution">*</i></th>
                        <th rowspan="1" style="width: 300px;" class="shizai2">資材2</th>
                        <th rowspan="1" style="width: 300px;" class="shizai3">資材3</th>
                        <th rowspan="1" style="width: 300px;" class="shizai4">資材4</th>
                        <th rowspan="1" style="width: 300px;" class="shizai5">資材5</th>
                        <th rowspan="1" style="width: 300px;" class="shizai6">資材6</th>
                        <th rowspan="1" style="width: 300px;" class="shizai7">資材7</th>
                        <th rowspan="1" style="width: 300px;" class="shizai8">資材8</th>
                        <th rowspan="1" style="width: 300px;" class="shizai9">資材9</th>
                        <th rowspan="1" style="width: 300px;" class="shizai10">資材10</th>

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
                        <td>
                            <span data-prop="cd_yoki_hoso"></span>
                        </td>
                        <td>
                            <input type="text" data-prop="nm_yoki_hoso" />
                        </td>
                        <td>
                            <select data-prop="cd_yoki_hoso_shizai01"></select>
                        </td>
                        <td>
                            <select data-prop="cd_yoki_hoso_shizai02"></select>
                        </td>
                        <td>
                            <select data-prop="cd_yoki_hoso_shizai03"></select>
                        </td>
                        <td>
                            <select data-prop="cd_yoki_hoso_shizai04"></select>
                        </td>
                        <td>
                            <select data-prop="cd_yoki_hoso_shizai05"></select>
                        </td>
                        <td>
                            <select data-prop="cd_yoki_hoso_shizai06"></select>
                        </td>
                        <td>
                            <select data-prop="cd_yoki_hoso_shizai07"></select>
                        </td>
                        <td>
                            <select data-prop="cd_yoki_hoso_shizai08"></select>
                        </td>
                        <td>
                            <select data-prop="cd_yoki_hoso_shizai09"></select>
                        </td>
                        <td>
                            <select data-prop="cd_yoki_hoso_shizai10"></select>
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
            <div class="part-command">
            </div>

            <!--TODO: 明細をpartにする場合は以下を利用します。
            </div>
            -->
        </div>
    </div>

</asp:Content>

<asp:Content ID="FooterContent" ContentPlaceHolderID="FooterContent" runat="server">
    <div class="command-left">
        <!--TODO: コマンドボタン（左寄せ配置）を定義するHTMLをここに記述します。-->
    </div>
    <div class="command">
        <!--TODO: コマンドボタン（右寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" id="save" class="btn btn-sm btn-primary" disabled="disabled">保存</button>
    </div>

</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>
