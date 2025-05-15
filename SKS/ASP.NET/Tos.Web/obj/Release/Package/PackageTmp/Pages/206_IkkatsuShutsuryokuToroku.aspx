<%@ Page Title="206_一括出力登録" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="206_IkkatsuShutsuryokuToroku.aspx.cs" Inherits="Tos.Web.Pages._206_IkkatsuShutsuryokuToroku" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchInputTable(Ver2.1)】 Template--%>

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
        /*.control-row {
            background-color: #f6f6f6;
            padding: 2px;
            margin-bottom: 2px;
            height: 30px;
        }*/
    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("_206_IkkatsuShutsuryokuToroku", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.maxInputDataCount,   // TODO:取得するデータ数を指定します。
                filter: ""
            },
            values: {
                isChangeRunning: {},
                isChangeHeaderRunning: {},
                limitMonth: 6

            },
            urls: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
                ikkatsuPostCopyFile: "../api/_206_IkkatsuShutsuryokuToroku/PostCopyFile",
                ikkatsuShutsuryoToroku: "../api/_206_IkkatsuShutsuryokuToroku/",
                literal: "../api/_206_IkkatsuShutsuryokuToroku/Get",
                getheader: "../Services/ShisaQuickService.svc/vw_shohinkaihatsu_ikkatsuShutsuryoToroku_206?$filter=no_ikkatsu eq {0}&$top=1",
                search: "../api/_206_IkkatsuShutsuryokuToroku/GetSearch",
                searchDetail: "../api/_206_IkkatsuShutsuryokuToroku/GetSearchDetail"
            },
            header: {
                options: {},
                values: {},
                urls: {}
            },
            detail: {
                options: {
                    errCopy: false
                },
                values: {
                    flag_1: 1,
                    flag_2: 2
                },
                mode: {
                    input: "input",
                    edit: "edit",
                    view: "view"
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

            if (Object.keys(page.values.isChangeHeaderRunning).length != 0) {
                App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
                App.ui.loading.close();

            } else {

                var sleep = 0;
                var condition = "Object.keys(page.values.isChangeRunning).length == 0";
                App.ui.wait(sleep, condition, 100)
                .then(function () {
                    page.header.validator.validate().done(function () {

                        var closeMessage = {
                            text: App.messages.app.AP0109,
                            multiModal:true
                        };
                        page.dialogs.confirmDialog.confirm(closeMessage).then(function () {

                            var elementHeader = page.header.element,
                                criteria = elementHeader.form().data(),
                                elementDetail = page.header.element,
                                dataSet,
                                changeSets,
                                item = {
                                    flg_denso: elementHeader.findP("flg_denso").is(":checked") ? 1 : 0,
                                    flg_kakunin: elementHeader.findP("flg_kakunin").is(":checked") ? 1 : 0,
                                    dt_from: App.data.getDateString(new Date(criteria.dt_from), true),
                                    dt_to: App.data.getDateString(new Date(criteria.dt_to), true),
                                    flg_01: elementHeader.findP("flg_01").is(":checked") ? 1 : 0,
                                    flg_19: elementHeader.findP("flg_19").is(":checked") ? 1 : 0,
                                    flg_20: elementHeader.findP("flg_20").is(":checked") ? 1 : 0,
                                    flg_21: elementHeader.findP("flg_21").is(":checked") ? 1 : 0,
                                    memo: criteria.memo
                                };

                            dataSet = App.ui.page.dataSet();
                            page.header.data = dataSet;
                            page.header.data.add(item);
                            changeSets = {
                                header: page.header.data.getChangeSet(),
                                detail: page.detail.data.getChangeSet(),
                                table: page.detail.data.getChangeSet(),
                            }

                            //TODO: データの保存処理をここに記述します。
                             $.ajax(App.ajax.webapi.post(page.urls.ikkatsuPostCopyFile, changeSets))
                                .then(function (result) {

                                    if (result.resCopy) {
                                        page.detail.options.errCopy = true;
                                    } else {
                                        page.detail.options.errCopy = false;
                                    }
                                    
                                    //最後に再度データを取得しなおします。
                                    $.ajax(App.ajax.webapi.post(page.urls.ikkatsuShutsuryoToroku, result)).then(function () {
                                        return App.async.all([page.loadHeaderData(result.no_ikkatsu)]);
                                    });
                                }).then(function () {
                                    if (page.detail.options.errCopy) {
                                        var closeMes = {
                                            text: App.messages.app.AP0111,
                                            hideCancel: true
                                        };
                                        page.dialogs.confirmDialog.confirm(closeMes).then(function () {
                                            $(".modal-backdrop").remove();
                                        });
                                    } else {
                                        App.ui.page.notifyInfo.message(App.messages.app.AP0110).show();
                                    }
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
            }
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
                mode = App.uri.queryStrings.mode,
                closeMessage = App.messages.base.exit;
            if ($("#save").is(":disabled")) {
                mode = "view";
            }

            if (page.detail.data && page.detail.mode.view !== mode) {
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
            $(".wrap, .footer").addClass("theme-yellow");

            page.initializeControl();
            page.initializeControlEvent();

            page.header.initialize();
            page.detail.initialize();

            //TODO: ヘッダー/明細以外の初期化の処理を記述します。

            page.loadMasterData().then(function (result) {
                var mode = App.uri.queryStrings.mode,
                no_ikkatsu = App.uri.queryStrings.no_ikkatsu;

                if (page.detail.mode.view == mode) {
                    page.loadHeaderData(no_ikkatsu);
                }
            }).then(function (result) {
                //TODO: 画面の初期化処理成功時の処理を記述します。
                page.loadDialogs();
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
            $("#clear").on("click", page.commands.clear);

        };
        /**
         * clear header and detail
         */
        page.commands.clear = function () {
            //remove all messenger
            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            var element = page.header.element;
            //clear header
            element.findP("flg_01").prop('checked', true);
            element.findP("flg_19").prop('checked', false);
            element.findP("flg_20").prop('checked', false);
            element.findP("flg_21").prop('checked', false);
            element.findP("flg_denso").prop('checked', true);
            element.findP("flg_kakunin").prop('checked', false);
            element.findP("dt_from").val("").text("");
            element.findP("dt_to").val("").text("");
            element.findP("memo").val("").text("");
            element.find(".control-required").removeClass("control-required").addClass("control-success");
            element.find(".control-required-label").removeClass("control-required-label").addClass("control-success-label");

            //clear detail
            page.options.skip = 0;
            page.detail.dataTable.dataTable("clear");
            page.detail.data = App.ui.page.dataSet();//reset ChangeSet
            page.detail.element.findP("data_count").text("");
            page.detail.element.findP("data_count_total").text("");
            $("#nextsearch").removeClass("show-search").hide();
            $("#save").prop("disabled", true);
        };
        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function () {
            return App.async.success();
        };

        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadHeaderData = function (no_ikkatsu) {
            //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
            var element = page.header.element;

            $.ajax(App.ajax.odata.get(App.str.format(page.urls.getheader, no_ikkatsu ))).then(function (result) {
                // リテラルコードは3桁で先頭は0で埋める
                var date_from = App.data.getDateString(new Date(result.value[0].dt_from), true),
                    date_to = App.data.getDateString(new Date(result.value[0].dt_to), true),
                    dt_toroku = App.data.getDateString(new Date(result.value[0].dt_toroku), true);

                element.findP("dt_from").val(date_from);
                element.findP("dt_to").val(date_to);
                element.findP("memo").val(result.value[0].memo);
                element.findP("dt_toroku").text(dt_toroku);
                element.findP("nm_user").text(result.value[0].nm_user);
                element.findP("flg_denso").prop("checked", result.value[0].flg_denso == 1 ? true : false);
                element.findP("flg_kakunin").prop("checked", result.value[0].flg_kakunin == 1 ? true : false);
                element.findP("flg_01").prop("checked", result.value[0].flg_01 == 1 ? true : false);
                element.findP("flg_19").prop("checked", result.value[0].flg_19 == 1 ? true : false);
                element.findP("flg_20").prop("checked", result.value[0].flg_20 == 1 ? true : false);
                element.findP("flg_21").prop("checked", result.value[0].flg_21 == 1 ? true : false);
                element.find(":input").prop("disabled", true);
                $("#save").prop("disabled", true);
                $("#clear").prop("disabled", true);

                //search detail
                page.detail.search(no_ikkatsu,true);
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
            flg_denso: {
                rules: {
                    requiredTaishobi: true
                },
                options: {
                    name: "対象日"
                },
                messages: {
                    requiredTaishobi: App.messages.base.required
                }
            },
            dt_from: {
                rules: {
                    required: true,
                    datestring: true,
                    lessthan_dt_to: true,
                    range_dt_to: true
                },
                options: {
                    name: "日付（開始)",
                },
                messages: {
                    required: App.messages.base.required,
                    datestring: App.messages.base.datestring,
                    lessthan_dt_to: App.messages.base.MS0014,
                    range_dt_to: App.str.format(App.messages.app.AP0108, page.values.limitMonth),
                }
            },
            dt_to: {
                rules: {
                    required: true,
                    datestring: true
                },
                options: {
                    name: "日付（終了)",
                },
                messages: {
                    required: App.messages.base.required,
                    datestring: App.messages.base.datestring
                }
            },
            memo: {
                rules: {
                    maxbytelength: 40
                },
                options: {
                    name: "メモ",
                    byte: 20
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            flg_01: {
                rules: {
                    requiredSeihoShubetsu: true
                },
                options: {
                    name: "製法種別"
                },
                messages: {
                    requiredSeihoShubetsu: App.messages.base.required
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
        App.validation.addMethod("range_dt_to", function (value, opts, state, done) {
            var element = page.header.element;
            dt_fromSixMonth = new Date(element.findP("dt_from").val()),
            //dt_from = new Date(element.findP("dt_from").val()),
            dt_to = new Date(element.findP("dt_to").val());
            //diffTime = Math.abs(dt_to.getTime() - dt_from.getTime()),
            //diffMonth = Math.floor(diffTime / (1000 * 60 * 60 * 30 * 24));
            dt_fromSixMonth.setMonth(dt_fromSixMonth.getMonth() + 6);
            //var sixMonth = dt_fromSixMonth.getFullYear() + "/" + dt_fromSixMonth.getMonth() + 1 + "/" + dt_fromSixMonth.getDate();
            if (dt_fromSixMonth.getTime() <= dt_to.getTime()) {
                return done(false);
            }

            return done(true);
        });
        App.validation.addMethod("requiredTaishobi", function (value, opts, state, done) {
            var element = page.header.element;

            if (element.findP("flg_denso")[0].checked == true || element.findP("flg_kakunin")[0].checked == true) {
                return done(true);
            }
            return done(false);
        });
        App.validation.addMethod("requiredSeihoShubetsu", function (value, opts, state, done) {
            var element = page.header.element;

            if (element.findP("flg_01")[0].checked == true || element.findP("flg_19")[0].checked == true || element.findP("flg_20")[0].checked == true || element.findP("flg_21")[0].checked == true) {
                return done(true);
            }
            return done(false);
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
         * 画面明細のバリデーションを実行します。
         */
        page.header.executeValidation = function (targets, options) {
            var defaultOptions = {
                targets: targets
            };
            options = $.extend(true, {}, defaultOptions, options);

            return page.header.validator.validate(options);
        };
        /**
        * 画面ヘッダーの変更時処理を定義します。
        */
        page.header.change = function (e) {
            var element = page.header.element,
            target = $(e.target),
            property = target.attr("data-prop"),
            data = element.form().data(),
            options = {
                filter: page.header.validationFilter
            };
            if (property != "memo") {
                page.values.isChangeHeaderRunning[property] = true;
            }

           if (property == "flg_kakunin") {
                target = element.find("[data-prop='flg_denso']");
            } else if (property == "flg_19" || property == "flg_20" || property == "flg_21") {
                target = element.find("[data-prop='flg_01']");
            }

            element.validation().validate({
                targets: target
            }).then(function () {
                if (property == "dt_from" || property == "dt_to") {
                    page.header.executeValidation(element.find("[data-prop='dt_from'], [data-prop='dt_to']"), options);
                }
            }).always(function () {
                //delete page.values.isChangeRunning[property];
            });
        };

        /**
         * 検索処理を定義します。
         */
        page.header.search = function (isLoading) {

            var deferred = $.Deferred(),
                query;
            //App.ui.page.notifyAlert.clear();
            page.header.validator.validate().done(function () {

                page.options.skip = 0;
                page.options.filter = page.header.createFilter();

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
                    page.values.isChangeHeaderRunning = {};
                });
            });

            return deferred.promise();

        };

        /**
         * 検索条件のフィルターを定義します。
         */
        page.header.createFilter = function () {
            var element = page.header.element,
                criteria = element.form().data(),
                flg_seiho = "",
                filters = {};

            /* TODO: 検索条件のフィルターを定義してください。*/
            if (element.findP("flg_01").is(":checked")) {
                flg_seiho += criteria.cd_literal01.trim().substring(0, 2) + ",";
            }
            if (element.findP("flg_19").is(":checked")) {
                flg_seiho += criteria.cd_literal19.trim().substring(0, 2) + ",";
            }
            if (element.findP("flg_20").is(":checked")) {
                flg_seiho += criteria.cd_literal20.trim().substring(0, 2) + ",";
            }
            if (element.findP("flg_21").is(":checked")) {
                flg_seiho += criteria.cd_literal21.trim().substring(0, 2);
            }
            if (!App.isUndefOrNull(criteria.flg_denso)) {
                filters.flg_denso = criteria.flg_denso;
            }
            if (!App.isUndefOrNull(criteria.flg_kakunin)) {
                filters.flg_kakunin = criteria.flg_kakunin;
            }

            filters.flg_seiho = flg_seiho;
            
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

            return filters;
        };

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
                    height: 200,
                    resize: true,
                    //列固定横スクロールにする場合は、下記3行をコメント解除
                    //fixedColumn: true,                //列固定の指定
                    //fixedColumns: 3,                  //固定位置を指定（左端を0としてカウント）
                    //innerWidth: 1200,                 //可動列の合計幅を指定
                    onselect: page.detail.select,
                    onchange: page.detail.change
                });

            table = element.find(".datatable"); //列固定にした場合DOM要素が再作成されるため、変数を再取得します。

            page.detail.validator = element.validation(page.createValidator(page.detail.options.validations));
            page.detail.element = element;
            page.detail.dataTable = datatable;

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
         * 画面明細の各行にデータを設定する際のオプションを定義します。
         */
        page.detail.options.bindOption = {
            // TODO: 主キーが直接入力の場合には、修正の場合変更を不可とします。
            appliers: {
                flg_seihobunsho_output: function (value, element) {
                    if (value == page.detail.values.flag_1) {
                        element.text("○");
                        element.css({ "font-size": "15px" });
                    } else if (value == page.detail.values.flag_2) {
                        element.text("×");
                        element.css({ "color": "red", "font-weight": "bold", "font-size": "15px" });
                    }else{
                        element.text("");
                    }
                    return true;
                }
            }
        };

        /**
         * 検索処理を定義します。
         */
        page.detail.search = function (no_ikkatsu,isLoading) {

            var deferred = $.Deferred(),
                query;

            page.header.validator.validate().done(function () {

                if (isLoading) {
                    App.ui.loading.show();
                    App.ui.page.notifyAlert.clear();
                }

                return $.ajax(App.ajax.webapi.get(page.urls.searchDetail, { no_ikkatsu: no_ikkatsu }))
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
         * 画面明細へのデータバインド処理を行います。
         */
        page.detail.bind = function (data, isNewData) {
            var i, l, item, dataSet, dataCount;

            dataCount = data.Count ? data.Count : 0;
            data = (data.Items) ? data.Items : data;

            dataSet = App.ui.page.dataSet();
            page.detail.data = dataSet;
            page.detail.dataTable.dataTable("clear");

            page.detail.dataTable.dataTable("addRows", data, function (row, item) {
                //(isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
                dataSet.add(item);
                row.form(page.detail.options.bindOption).bind(item);
                return row;
            }, true);

            if (!isNewData) {
                page.detail.element.findP("data_count").text(data.length);
                page.detail.element.findP("data_count_total").text(dataCount);
            }

            //TODO: 画面明細へのデータバインド処理をここに記述します。
            if (data.length) {
                $("#save").prop("disabled", false);
            } else {
                $("#save").prop("disabled", true);
            }

            //バリデーションを実行します。
            page.detail.validateList(true);

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
        page.header.validationFilter = function (item, method, state, options) {
            return method !== "required";
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
                        <label>登録日</label>
                    </div>
                    <div class="control col-xs-5">
                        <label data-prop="dt_toroku"></label>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>登録担当者</label>
                    </div>
                    <div class="control col-xs-5">
                        <label data-prop="nm_user" class="overflow-ellipsis"></label>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>期間<span style="color: red">*</span></label>
                    </div>
                    <div class="control col-xs-5 checkbox" style="margin-top:0px !important;">
                        <label style="margin-left:17px;">
                            <input type="checkbox" class="check-box-vertical" data-prop="flg_denso" checked value="1">伝送登録日
                        </label>

                        <label style="margin-left:15px;">
                            <input type="checkbox" class="check-box-vertical" data-prop="flg_kakunin" value="1">工場確認日
                        </label>

                        <input type="text" style="width: 120px" data-role="date" class="ime-active text-selectAll " data-prop="dt_from" />
                        ~
                        <input type="text" style="width: 120px" data-role="date" class="ime-active text-selectAll " data-prop="dt_to" />
                    </div>
                    <div class="control-label col-xs-1">
                        <label>製法種別<span style="color: red">*</span></label>
                    </div>
                    <div class="control col-xs-5 checkbox" style="margin-top:0px !important;">
                        <label style="margin-left:18px;" data-prop="cd_literal01">
                            <input type="checkbox" class="check-box-vertical checked-row" data-prop="flg_01" checked>01:本製法
                        </label>

                        <label style="margin-left:15px;" data-prop="cd_literal19">
                            <input type="checkbox" class="check-box-vertical checked-row" data-prop="flg_19">19:表示用製法
                        </label>

                        <label style="margin-left:15px;" data-prop="cd_literal20">
                            <input type="checkbox" class="check-box-vertical checked-row" data-prop="flg_20">20:試験製法
                        </label>

                        <label style="margin-left:15px;" data-prop="cd_literal21">
                            <input type="checkbox" class="check-box-vertical checked-row" data-prop="flg_21">21:短期製法
                        </label>
                    </div>
                    
                </div>

                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>メモ</label>
                    </div>
                    <div class="control col-xs-5">
                        <input type="text" class="ime-active" data-prop="memo" style="width:461px;" />
                    </div>
                    <div class="control col-xs-6">
                    </div>
                </div>
                <div class="header-command">
                    <button type="button" id="search" class="btn btn-sm btn-primary">検索</button>
                </div>
            </div>
        </div>
        <!--TODO: 明細を定義するHTMLをここに記述します。-->
      <div class="detail">
           <%-- TODO: 明細をpartにする場合は以下を利用します。--%>
           <%-- <div title="" class="part">--%>
            
            <div class="control-label toolbar">
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
                        <th style="width: 10px;"></th>
                        <th style="width: 220px;">製法番号</th>
                        <th style="">製法名</th>
                        <th style="width: 150px">ステータス</th>
                        <th style="width: 150px">製法作成日</th>
                        <th style="width: 150px">伝送登録日</th>
                        <th style="width: 150px">工場確認日</th>
                        <th style="width: 150px">製法文書</th>

                    </tr>
                </thead>
                <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->
                <tbody class="item-tmpl" style="cursor: default; display: none;">
                    <tr>
                        <!--TODO: 単一行を作成する場合は、以下を利用します。-->
                        <td>
                            <span class="select-tab unselected"></span>
                        </td>
                        <td style="text-align: left">
                            <label data-prop="no_seiho"></label>
                        </td>
                        <td style="text-align: left">
                            <label data-prop="nm_seiho"></label>
                        </td>
                        <td style="text-align: left">
                            <label data-prop="nm_literal"></label>
                            <input type="text" data-prop="flg_denso_jyotai" class="hidden" />
                        </td>
                        <td style="text-align: left">
                            <label data-prop="dt_seiho_sakusei" class=""></label>
                        </td>
                        <td style="text-align: left">
                            <label data-prop="dt_denso_toroku" class=""></label>
                        </td>
                        <td style="text-align: left">
                            <label data-prop="dt_denso_kanryo" class=""></label>
                        </td>
                        <td style="text-align: center">
                            <label data-prop="flg_seihobunsho_output" class=""></label>
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="part-command">
            </div>

        <%--    TODO: 明細をpartにする場合は以下を利用します。
          </div> --%> 
            
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
        <button type="button" id="save" class="btn btn-sm btn-primary" disabled="disabled">実行</button>
    </div>

</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>
