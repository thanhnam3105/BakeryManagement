<%@ Page Title="015_ログ出力" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="015_RoguShutsuryokuDownload.aspx.cs" Inherits="Tos.Web.Pages._015_RoguShutsuryokuDownload" %>
<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【CsvUploadDownload(Ver2.0)】 Template--%>

<asp:Content ID="IncludeContent" ContentPlaceHolderID="IncludeContent" runat="server">
    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/part.css") %>" type="text/css" />
    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/headerdetail.css") %>" type="text/css" />

    <% #if DEBUG %>
    <script src="<%=ResolveUrl("~/Scripts/part.js") %>" type="text/javascript"></script>
    <% #else %>
    <script src="<%=ResolveUrl("~/Scripts/part.min.js") %>" type="text/javascript"></script>
    <% #endif %>

</asp:Content>
<asp:Content ID="HeadContent" ContentPlaceHolderID="HeadContent" runat="server">

    <style type="text/css">

        .red {
            color: red;
        }

    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("_015_RoguShutsuryokuDownload", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {},
            values: {
                id_kino: 10,
                id_data: 9
            },
            urls: {
                downloadCsv: "../api/_015_RoguShutsuryokuCSVDownload",
                mainMenu: "MainMenu.aspx"
            },
            header: {
                options: {},
                values: {},
                urls: {}
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
                        next = $target.next();
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
                        next = $target.next();
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

        page.createValidator = function (target, options) {
            return App.validation(target, options || {
                success: page.validationSuccess,
                fail: page.validationFail,
                always: page.validationAlways
            });
        };

        /**
         * すべてのバリデーションを実行します。
         */
        page.validateAll = function () {

            var validations = [];

            validations.push(page.header.validator.validate());
            validations.push(page.detail.validateList());
            return App.async.all(validations);
        };

        /**
        * 画面の初期化処理を行います。
        */
        page.initialize = function () {
            App.ui.loading.show();

            page.initializeControl();
            page.initializeControlEvent();

            page.header.initialize();

            flgAble = false;

            //TODO: ヘッダー/明細以外の初期化の処理を記述します。

            page.loadMasterData().then(function (result) {
                //TODO: 画面の初期化処理成功時の処理を記述します。
                return page.loadDialogs().then(function () {
                    getKengenGamen(App.settings.app.id_gamen.rogu_shutsuryoku).then(function (results) {

                        if (results.length == 0) {
                            window.location.href = page.urls.mainMenu;
                        }

                        $.each(results, function (index, item) {
                            if (item.id_kino == page.values.id_kino && item.id_data == page.values.id_data) {
                                flgAble = true;
                            }
                        });

                        $("#download-btn").prop("disabled", !flgAble);
                    });
                })
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

            }).always(function (result) {

                page.header.element.find("#download-select").focus();
                App.ui.loading.close();
            });
        };

        /**
         * 画面コントロールの初期化処理を行います。 
         */
        page.initializeControl = function () {
            // 共通コントロールの初期化処理
            $(".part").part();
        };

        /**
         * コントロールへのイベントの紐づけを行います。
         */
        page.initializeControlEvent = function () {
            //TODO: 画面全体で利用するコントロールのイベントの紐づけ処理をここに記述します。
            $("#download-btn").on("click", page.commands.download);
        };

        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function () {
            return App.async.success();
        };

        /**
         * 共有ダイアログのロード処理を実行します。
         */
        page.loadDialogs = function () {
            return App.async.success();
        };

        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.validations = {
            dt_from: {
                rules: {
                    datestring: true,
                    lessthan_dt_to: true
                },
                options: {
                    name: "作業日（From）",
                    lessdate: "作業日（To）"
                },
                messages: {
                    datestring: App.messages.base.datestring,
                    lessthan_dt_to: App.messages.base.lessdate
                }
            },
            dt_to: {
                rules: {
                    datestring: true,
                },
                options: {
                    name: "作業日（To）",
                },
                messages: {
                    datestring: App.messages.base.datestring
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

            element.find(":input[data-role='date']").datepicker();
            element.find(":input[data-role='date']").on("keyup", App.data.addSlashForDateString);
            element.findP("dt_from").val(page.header.defaultDateFrom(true));
        };

        /**
         * データのダウンロード処理を実行します。
         */
        page.commands.download = function () {
            var element = page.header.element;

            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            page.header.validator.validate().done(function () {
                App.ui.loading.show();
                page.options.filter = page.header.createFilter();

                // CSV出力（ファイルStreamでダウンロード）
                return $.ajax(App.ajax.file.download(page.urls.downloadCsv, page.options.filter))
                    .then(function (response, status, xhr) {
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
         * 検索条件のフィルターを定義します。
         */
        page.header.createFilter = function () {
            var criteria = page.header.element.form().data(),
                filters = {};

            //TODO: 画面で設定された検索条件を取得し、データ取得サービスのフィルターオプションを組み立てます。
            if (!App.isUndefOrNull(criteria.dt_from)) {
                filters.dt_from = App.data.getDateString(new Date(criteria.dt_from), true);
            }

            if (!App.isUndefOrNull(criteria.dt_to)) {
                filters.dt_to = App.data.getDateString(new Date(criteria.dt_to), true);
            }

            return filters;
        };

        /**
         * 画面ヘッダーにある入力項目の変更イベントの処理を行います。
         */
        page.header.change = function (e) {
            var element = page.header.element,
                target = $(e.target);

            element.validation().validate({
                targets: target
            });
        };

        /**
         * get system date 2 weeks back 。
         */
        page.header.defaultDateFrom = function (isNotUtc) {
            var date = new Date();
            date.setDate(date.getDate() - 14);
            return App.data.getDateString(date, isNotUtc);
        };

        /**
         * validations => date from and date to 。
         */
        App.validation.addMethod("lessthan_dt_to", function (value, opts, state, done) {
            var element = page.header.element,
                data = element.form().data();

            if (!(/^[0-9]{4}\/[0-9]{2}\/[0-9]{2}$/.test(element.findP("dt_from").val()))) {
                return done(true);
            }

            if (!(/^[0-9]{4}\/[0-9]{2}\/[0-9]{2}$/.test(element.findP("dt_to").val()))) {
                return done(true);
            }

            if (App.isUndefOrNull(data.dt_to) || App.isUndefOrNull(data.dt_from)) {
                return done(true);
            }

            if (data.dt_to < data.dt_from) {
                return done(false);
            }

            return done(true);
        });

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
        <div class="header smaller">
            <%--<div title="データメンテナンス" class="part">--%>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label class="item-name">作業日</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="text" style="width: 120px" data-role="date" class="ime-active text-selectAll " data-prop="dt_from" />
                        ~
                        <input type="text" style="width: 120px" data-role="date" class="ime-active text-selectAll "  data-prop="dt_to" />
                    </div>
                    <div class="control col-xs-8">
                        <label class="red">※2週間前までのログが出力できます</label>
                    </div>
                </div>
                <div class="header-command">
                    <button type="button" id="download-btn" data-prop="download-btn" class="btn btn-sm btn-primary">CSV</button>
                </div>
            <%--</div>--%>
        </div>
    </div>
</asp:Content>
<asp:Content ID="FooterContent" ContentPlaceHolderID="FooterContent" runat="server">
    <div class="command-left">
        <%--<button type="button" id="download-btn" class="btn btn-sm btn-primary">CSV</button>--%>
    </div>
</asp:Content>
<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>
