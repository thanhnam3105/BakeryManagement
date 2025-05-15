<%@ Page Title="207_配合受信" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="207_HaigoJyushin.aspx.cs" Inherits="Tos.Web.Pages.HaigoJyushin" %>
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

        input[type="radio"], input[type="checkbox"] {
            vertical-align: middle !important;
        }

        /*label {
            padding: 2px!important;
        }*/

        .red-color {
            color: red;
        }

        .custom-model table td {
            border: 1px solid #cccccc!important;
        }

        .custom-model .modal-body {
            padding: 10px!important;
        }

        .btn-next-search {
            width: 200px;
        }

        .hidden-207 {
            display: none!important;
        }

        textarea {
           resize: none;
        }
    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("_207_HaigoJyushin", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.maxInputDataCount,   // TODO:取得するデータ数を指定します。
                filter: ""
            },
            values: {
                isChangeRunning: {},
                marginLeftBikoFixed: 0,
                marginTopBikoFixed: 0,
                su_code_standard: 0,
                cd_kaisha: 0,
                cd_kojyo: 0,
                addTrDertail: "<tr class='hidden-207 {0}'>"
                    + "<td class='center'>"
                    + "<input type='checkbox' data-prop='flg_zyufuku_hyoji' disabled='disabled'/>"
                    + "</td>"
                    + "<td class='center'>"
                    + "<input type='checkbox' data-prop='flg_zyufuku' disabled='disabled'/>"
                    + "</td>"
                    + "<td>"
                    + "<label data-prop='cd_haigo'></label>"
                    + "</td>"
                    + "<td>"
                    + "<input type='tel' data-prop='cd_haigo_new' class='limit-input-digit' disabled='disabled' />"
                    + "</td>"
                    + "<td>"
                    + "<label data-prop='nm_haigo' ></label>"
                    + "</td>"
                + "</tr>",
            },
            urls: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
                save: "../api/HaigoJyushin"
            },
            header: {
                options: {},
                values: {},
                urls: {
                    vw_kaisha_kojyo: "../Services/ShisaQuickService.svc/vw_kaisha_kojyo?$filter=cd_kaisha eq {0}{1}&$orderby=cd_kaisha,cd_kojyo",
                    ma_kaisha: "../Services/ShisaQuickService.svc/ma_kaisha?{0}&$orderby=cd_kaisha",
                    search: "../api/HaigoJyushin"
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
                    if (item) {
                        $(item.element).removeClass("has-error");
                    }
                    //page.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                    //    if (row && row.element) {
                    //        row.element.find("tr").removeClass("has-error");
                    //    }
                    //});
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
                    if (item) {
                        $(item.element).addClass("has-error");
                    }
                    //page.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                    //    if (row && row.element) {
                    //        row.element.find("tr").addClass("has-error");
                    //    }
                    //});
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

                    var countRowProcess = page.detail.element.find(".checkedProcess");
                    if (countRowProcess.length == 0) {
                        App.ui.page.notifyAlert.message(App.messages.app.AP0008).show();
                        return;
                    }
                    var options = {
                        text: App.messages.app.AP0130
                    };

                    page.dialogs.confirmDialog.confirm(options)
                    .then(function () {
                    
                        var changeSets = {
                             DataUpdate: page.detail.data.getChangeSet(),
                             su_code_standard: page.values.su_code_standard,
                             cd_kaisha: page.values.cd_kaisha,
                             cd_kojyo: page.values.cd_kojyo
                        };
                        if (App.isUndefOrNullOrStrEmpty(changeSets.su_code_standard) || isNaN(page.values.su_code_standard)) {
                            var changeSet = page.detail.data.getChangeSet();
                            if (changeSet.updated.length > 0) {
                                changeSets.su_code_standard = changeSet.updated[0].su_code_standard;
                            }
                        }

                        var flg_error = 0;
                        App.ui.loading.show();
                        //TODO: データの保存処理をここに記述します。
                        return $.ajax(App.ajax.webapi.post(page.urls.save, changeSets))
                            .then(function (result) {
                                //TODO: データの保存成功時の処理をここに記述します。
                                flg_error = result;
                                //最後に再度データを取得しなおします。
                                return App.async.all([page.header.search(false)]);
                            }).then(function () {
                                App.ui.page.notifyInfo.message(App.messages.app.AP0136).show();
                                if (flg_error > 0) {
                                    App.ui.page.notifyAlert.message("", "AP0059").show();
                                    $(".alert-notify-text .error").replaceWith("<pre class='error'>" + App.str.format(App.messages.app.AP0059, flg_error) + "</pre>");
                                }
                            }).fail(function (error) {
                                if (error.status === App.settings.base.conflictStatus) {
                                    // TODO: 同時実行エラー時の処理を行っています。
                                    // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                                    //page.header.search(false);
                                    App.ui.page.notifyAlert.clear();
                                    App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
                                    return;
                                }

                                if (error.status === App.settings.app.badGateway) {
                                    App.ui.page.notifyAlert.clear();
                                    App.ui.page.notifyAlert.message(error.responseJSON).show();

                                    return;
                                }

                                //TODO: データの保存失敗時の処理をここに記述します。
                                if (error.status === App.settings.base.validationErrorStatus) {
                                    var errors = error.responseJSON;

                                    if(errors == "AP0132"){
                                        App.ui.page.notifyAlert.message(App.messages.app.AP0132).show();
                                    } else if(errors == "AP0058"){
                                        App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0058, "配合", "表示用")).show();
                                    } else {
                                        $.each(errors, function (index, err) {
                                            App.ui.page.notifyAlert.message(
                                                err.Message +
                                                (App.isUndefOrNull(err.InvalidationName) ? "" : err.Data[err.InvalidationName])
                                            ).show();
                                        });
                                    }
                                    return;
                                }

                                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

                            }).always(function () {
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

                page.values.su_code_standard = App.ui.page.user.su_code_standard;

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
            var element = page.header.element;

            //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
            return page.kaishaKojoJotai(page.header.urls.ma_kaisha, "nm_kaisha", "cd_kaisha", "nm_kaisha", "", false).then(function () {
                    var cd_kaisha = element.findP("nm_kaisha").val();
                    element.findP("nm_kaisha").val(App.ui.page.user.cd_kaisha);
                    cd_kaisha = App.ui.page.user.cd_kaisha;
                    page.values.cd_kaisha = cd_kaisha;
                    page.values.su_code_standard = App.ui.page.user.su_code_standard + 1;
                    element.findP("cd_genryo").attr("maxlength", page.values.su_code_standard);
                    if (!App.ui.page.user.flg_kaishakan_sansyo) {
                        element.find("[data-prop='cd_kaisha'], [data-prop='nm_kaisha']").prop("disabled", true);
                    }

                    if (!App.isUndefOrNullOrStrEmpty(cd_kaisha)) {
                        element.findP("cd_kaisha").val(page.addZero(cd_kaisha, "0000"));
                        element.findP("cd_kaisha_validate").text(cd_kaisha);

                        var cd_kojyo = "";
                        page.kaishaKojoJotai(page.header.urls.vw_kaisha_kojyo, "nm_kojyo", "cd_kojyo", "nm_kojyo", cd_kaisha, false).then(function () {
                            cd_kojyo = element.findP("nm_kojyo").val();
                            element.findP("nm_kojyo").val(App.ui.page.user.cd_busho);
                            cd_kojyo = App.ui.page.user.cd_busho;
                            page.values.cd_kojyo = cd_kojyo;
                            if (!App.ui.page.user.flg_kojyokan_sansyo) {
                                element.find("[data-prop='cd_kojyo'], [data-prop='nm_kojyo']").prop("disabled", true);
                            }

                            if (!App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                                element.findP("cd_kojyo").val(page.addZero(cd_kojyo, "0000"));
                                element.findP("cd_kojyo_validate").text(cd_kojyo);
                            }
                        }).fail(function (error) {
                            App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                        });
                    }
                }).fail(function (error) {
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                });

            //TODO: マスタデータのロード処理を実装後、下の1行を削除してください。
            return App.async.success();
        };

        /**
         * Load combobox 製造会社, 製造工場, 状態。
         */
        page.kaishaKojoJotai = function (url, dataPropElement, codeProps, nameProps, param, blank) {

            //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
            return $.ajax(App.ajax.odata.get(App.str.format(url, param))).then(function (result) {

                var element = page.header.element.findP(dataPropElement);
                element.children().remove();
                if (codeProps != "cd_kaisha") {
                    App.ui.appendOptions(
                        element,
                        codeProps,
                        nameProps,
                        result.value,
                        blank
                    );
                } else {
                    App.common.appendOptions(
                        element,
                        codeProps,
                        nameProps,
                        result.value,
                        blank,
                        {},
                        false,
                        "",
                        "su_code_standard"
                    );
                }
            });
        };

        /**
         * 共有ダイアログのロード処理を実行します。
         */
        page.loadDialogs = function () {

            //TODO: 共有ダイアログデータを取得し、画面にバインドする処理を記述します。
            return App.async.all({

                confirmDialog: $.get(page.urls.confirmDialog),
            }).then(function (result) {

                $("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;

                CustomDialog.initialize();
            });
        }

        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.validations = {
            //TODO: 画面ヘッダーのバリデーションの定義を記述します。
            cd_kaisha: {
                rules: {
                    required: true,
                    maxlength: 4,
                    digits: true,
                    existsCode: function (value, opts, state, done) {
                        var element = page.header.element;
                        var cd_kaisha = element.findP("cd_kaisha").val();
                        var cd_kaisha_validate = element.findP("cd_kaisha_validate").text();

                        if (App.isUndefOrNullOrStrEmpty(cd_kaisha)) {
                            return done(true);
                        }


                        return done(cd_kaisha_validate != "");
                    }
                },
                options: {
                    name: "会社コード"
                },
                messages: {
                    required: App.messages.base.required,
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits,
                    existsCode: App.messages.app.AP0010
                }
            },
            cd_kojyo: {
                rules: {
                    required: true,
                    maxlength: 4,
                    digits: true,
                    existsCode: function (value, opts, state, done) {
                        var element = page.header.element;
                        var cd_kojyo = element.findP("cd_kojyo").val();
                        var cd_kojyo_validate = element.findP("cd_kojyo_validate").text();

                        if (App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                            return done(true);
                        }


                        return done(cd_kojyo_validate != "");
                    }
                },
                options: {
                    name: "工場コード"
                },
                messages: {
                    required: App.messages.base.required,
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits,
                    existsCode: App.messages.app.AP0010
                }
            },
            dt_denso_toroku_from: {
                rules: {
                    datestring: true,
                    maxlength: 10,
                    rangeYearMonthDay: ["1950/01/01", "3000/12/31"],
                    lessthan_ymd_to: [true, page.header, "dt_denso_toroku_to"],
                },
                options: {
                    name: "伝送登録日(開始)",
                },
                messages: {
                    datestring: App.messages.base.datestring,
                    maxlength: App.messages.base.maxlength,
                    rangeYearMonthDay: App.messages.base.range,
                    lessthan_ymd_to: App.messages.base.MS0014
                }
            },
            dt_denso_toroku_to: {
                rules: {
                    datestring: true,
                    maxlength: 10,
                    rangeYearMonthDay: ["1950/01/01", "3000/12/31"]
                },
                options: {
                    name: "伝送登録日(終了)",
                },
                messages: {
                    datestring: App.messages.base.datestring,
                    maxlength: App.messages.base.maxlength,
                    rangeYearMonthDay: App.messages.base.range
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


            element.find(":input[data-role='date']").datepicker();
            element.find(":input[data-role='date']").on("keyup", App.data.addSlashForDateString);

            //TODO: 画面ヘッダーの初期化処理をここに記述します。
            //TODO: 画面ヘッダーで利用するコントロールのイベントの紐づけ処理をここに記述します。
            element.on("click", "#search", function () {
                page.header.beforeSearh(true);
            });
            element.on("change", ":input", page.header.change);

        };

        /**
         * 画面ヘッダーの変更時処理を定義します。
         */
        page.header.change = function (e) {
            var element = page.header.element,
                target = $(e.target),
                id = element.attr("data-key"),
                property = target.attr("data-prop"),
                data = element.form().data(),
                options = {
                    filter: page.header.validationFilter
                };

            App.ui.page.notifyAlert.remove("AP0059");
            page.values.isChangeRunning[property] = true;

            if ($("#nextsearch").hasClass("show-search")) {
                $("#nextsearch").removeClass("show-search").hide();
                App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
            }
            else if (page.detail.searchData) {
                // 保持検索データの消去
                page.detail.searchData = undefined;
                App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
            }

            if (property == "cd_kaisha" || property == "nm_kaisha") {
                var cd_kaisha = target.val();
                if (App.ui.page.user.flg_kojyokan_sansyo) {
                    element.findP("nm_kojyo").children().remove();
                    element.findP("cd_kojyo").val("");
                }


                element.findP("cd_kaisha").val(page.addZero(cd_kaisha, "0000"));
                element.findP("cd_kaisha_validate").text(cd_kaisha);
                element.findP("nm_kaisha").val(cd_kaisha);

                page.values.isResetKojyo = true;
                if (property == "cd_kaisha") {
                    if (App.isUndefOrNullOrStrEmpty(cd_kaisha)) {
                        element.findP("nm_kaisha").val(cd_kaisha);
                    }
                    if (cd_kaisha != "" && App.isNumeric(cd_kaisha)) {
                        cd_kaisha = parseInt(cd_kaisha);

                        page.checkData(App.str.format(page.header.urls.ma_kaisha, "$filter=cd_kaisha eq {0}"), cd_kaisha, "",
                                        element, "cd_kaisha", "cd_kaisha_validate", "nm_kaisha", cd_kaisha);
                    }
                }

                if (property == "nm_kaisha" || page.values.isResetKojyo) {
                    var su_code_standard = element.findP("nm_kaisha").children("option:selected").attr("su_code_standardval");
                    page.values.su_code_standard = parseInt(su_code_standard) + 1;
                    if (cd_kaisha != "" && App.isNumeric(cd_kaisha) && App.ui.page.user.flg_kojyokan_sansyo) {
                        cd_kaisha = parseInt(cd_kaisha);
                        page.values.isChangeRunning["changeKaisha"] = true;
                        page.kaishaKojoJotai(page.header.urls.vw_kaisha_kojyo, "nm_kojyo", "cd_kojyo", "nm_kojyo", cd_kaisha, false).then(function () {
                            var cd_kojyo = element.findP("nm_kojyo").val();
                            if (!App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                                element.findP("cd_kojyo").val(page.addZero(cd_kojyo, "0000"));
                                element.findP("cd_kojyo_validate").text(cd_kojyo);
                            }

                        }).always(function () {
                            delete page.values.isChangeRunning["changeKaisha"];
                        });
                    }
                    page.header.validator.validate({
                        targets: element.find("[data-prop='cd_kaisha'], [data-prop='nm_kaisha']")
                    });
                }
            }

            if (property == "cd_kojyo") {
                var cd_kojyo = target.val();
                element.findP("cd_kojyo").val(page.addZero(cd_kojyo, "0000"));
                element.findP("cd_kojyo_validate").text(cd_kojyo);
                element.findP("nm_kojyo").val(cd_kojyo);
                if (App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                    element.findP("nm_kojyo").val(cd_kojyo);
                }
                if (cd_kojyo != "" && App.isNumeric(cd_kojyo)) {
                    cd_kojyo = parseInt(cd_kojyo);
                    var cd_kaisha = element.findP("nm_kaisha").val();

                    page.checkData(App.str.format(page.header.urls.vw_kaisha_kojyo, "{0}", " and cd_kojyo eq {1}"), cd_kaisha, cd_kojyo,
                                    element, "cd_kojyo", "cd_kojyo_validate", "nm_kojyo", cd_kojyo);
                }

            }
            if (property == "nm_kojyo") {
                var cd_kojyo = target.val();

                element.findP("cd_kojyo").val(page.addZero(cd_kojyo, "0000"));
                element.findP("cd_kojyo_validate").text(cd_kojyo);
                element.findP("nm_kojyo").val(cd_kojyo);
            }

            delete page.values.isChangeRunning[property];


            var sleep = 0;
            var condition = "Object.keys(page.values.isChangeRunning).length == 0";
            App.ui.wait(sleep, condition, 100)
            .then(function () {
                page.header.validator.validate();
            });
        }

        /**
        * 検索処理を定義します。
        */
        page.checkData = function (url, param1, param2, element, cd_code, cd_validate, nm_name, value) {
            if (param1 == "") {
                param1 = "null";
            }
            return $.ajax(App.ajax.odata.get(App.str.format(url, param1, param2))).then(function (result) {
                if (result.value.length > 0) {
                    element.findP(cd_code).val(page.addZero(value, "0000"));
                    element.findP(cd_validate).text(value);
                    element.findP(nm_name).val(value);
                    page.values.isResetKojyo = true;
                    if (cd_code == "cd_kaisha") {
                        page.values.su_code_standard = result.value[0].su_code_standard + 1;
                    }
                } else {
                    element.findP(cd_validate).text("");
                    element.findP(nm_name).val("");
                    page.values.isResetKojyo = false;
                }
                page.header.validator.validate({
                    targets: element.find("[data-prop='" + cd_code + "'], [data-prop='" + nm_name + "']")
                });
            });
        }

        /**
         * Add zero before number。
         */
        page.addZero = function (value, zeroList) {
            var length = zeroList.length;
            if (!App.isUndefOrNullOrStrEmpty(value) && App.isNumeric(value)) {
                value = (zeroList + value).slice(-length);
            }
            return value;
        }

        /**
        * Check data change before search。
        */
        page.header.beforeSearh = function (isLoading) {
            page.header.validator.validate().done(function () {
                page.options.skip = 0;
                page.options.filter = page.header.createFilter();

                if (page.detail.data.isChanged()) {
                    var options = {
                        text: App.messages.app.AP0053
                    };

                    page.dialogs.confirmDialog.confirm(options)
                    .then(function () {
                        page.header.search(isLoading);
                    });
                } else {
                    page.header.search(isLoading);
                }
            });
        }

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
                    //if (!$("#save").is(":disabled")) {
                    //    $("#save").prop("disabled", true);
                    //}
                });
            });

            return deferred.promise();

        };

        /**
         * 次のレコードを検索する処理を定義します。
         */
        page.detail.nextsearch = function () {
            page.options.filter = page.header.createFilter();

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
         * 検索条件のフィルターを定義します。
         */
        page.header.createFilter = function () {
            var criteria = page.header.element.form().data(),
                filters = {};

            /* TODO: 検索条件のフィルターを定義してください。*/
            if (!App.isUndefOrNullOrStrEmpty(criteria.cd_kaisha) && criteria.cd_kaisha > 0) {
                filters.cd_kaisha = criteria.cd_kaisha;
            }
            if (!App.isUndefOrNullOrStrEmpty(criteria.cd_kojyo) && criteria.cd_kojyo > 0) {
                filters.cd_kojyo = criteria.cd_kojyo;
            }

            if (!App.isUndefOrNullOrStrEmpty(criteria.dt_denso_toroku_from)) {
                filters.dt_denso_toroku_from = App.data.getDateString(new Date(criteria.dt_denso_toroku_from), true);
            }
            if (!App.isUndefOrNullOrStrEmpty(criteria.dt_denso_toroku_to)) {
                filters.dt_denso_toroku_to = App.data.getDateString(new Date(criteria.dt_denso_toroku_to), true);
            }
            if (!App.isUndefOrNull(criteria.flg_denso_jyotai)) {
                filters.flg_denso_jyotai = criteria.flg_denso_jyotai;
            }

            var kbn_sort = 0;
            var mode_sort = $("input[name='jyotai']:checked").val();
            if (mode_sort == 0) {
                kbn_sort = 0;
            } else {
                kbn_sort = 1;
            }
            filters.kbn_sort = kbn_sort;

            filters.su_code_standard = page.values.su_code_standard;

            filters.skip = page.options.skip;
            filters.top = page.options.top;

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
         * 画面明細のバリデーションを定義します。
         */
        page.detail.options.validations = {
            //TODO: 画面明細のバリデーションの定義を記述します。
            cd_haigo_new: {
                rules: {
                    requiredCustom: function (value, opts, state, done) {
                        var tbody = state.tbody.element ? state.tbody.element : state.tbody;

                        if (tbody.findP("cd_haigo_new").prop("disabled")) {
                            return done(true);
                        }

                        if (App.isUndefOrNullOrStrEmpty(value)) {
                            return done(false);
                        }

                        return done(true);
                    },
                    maxlength: page.values.su_code_standard,
                    digits: true,
                    duplicate: function (value, opts, state, done) {
                        App.extend.duplicate207(value, ["cd_haigo_new"], state, done);
                    }
                },
                options: {
                    name: "新配合コード"
                },
                messages: {
                    requiredCustom: App.messages.base.required,
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits,
                    duplicate: "Setting in app.extend.js"
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
            element.on("click", "#nextsearch", page.detail.nextsearch);
            table.on("click", ".biko-class", page.detail.showBikoArea);

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

        var CustomDialog = {
            values: {
                entity: {}
            }
        }

        /**
         * Show biko edit textarea。
         */
        page.detail.showBikoArea = function (e) {
            var target = $(e.target),
               id = target.closest("tbody").attr("data-key"),
               entity = page.detail.data.entry(id);

            page.values.marginLeftBikoFixed = e.clientX;
            page.values.marginTopBikoFixed = e.clientY;

            CustomDialog.values.entity = entity;

            var element = $(".custom-model");
            element.modal("show");
        }

        CustomDialog.initialize = function () {
            var element = $(".custom-model");

            $(".custom-model").on("shown.bs.modal", function () {
                $(".custom-model").find(".modal-dialog").css("margin-left", page.values.marginLeftBikoFixed + "px");
                $(".custom-model").find(".modal-dialog").css("margin-top", page.values.marginTopBikoFixed + "px");
                $('.modal-backdrop').removeClass("modal-backdrop");

                element.find(".modal-dialog").css("width", "18%");
                element.findP("shubetsu_seiho").val(CustomDialog.values.entity.shubetsu_seiho);

                element.findP("shubetsu_seiho").focus();

            });
        }


        /**
         * 画面明細の各行にデータを設定する際のオプションを定義します。
         */
        page.detail.options.bindOption = {
            // TODO: 主キーが直接入力の場合には、修正の場合変更を不可とします。
            appliers: {
                flg_denso_jyotai: function (value, element) {
                    if (value) {
                        element.prop("checked", true);
                        element.prop("disabled", true);
                    }
                    return true;
                },
                flg_zyufuku_hyoji: function (value, element) {
                    if (value) {
                        element.prop("checked", true);
                    }
                    return true;
                },
                flg_zyufuku: function (value, element) {
                    if (value) {
                        element.prop("checked", true);
                    }
                    return true;
                },
                cd_haigo: function (value, element) {
                    var cd_haigo = value + "0000000000000";
                    element.text(cd_haigo.slice(0, (parseInt(page.values.su_code_standard) + 1)));

                    return true;
                }

            }
        };

        /**
         * 画面明細へのデータバインド処理を行います。
         */
        page.detail.bind = function (data, isNewData) {
            var i, l, item, dataSet, dataCount;

            dataCount = data.Count ? data.Count : 0;
            data = (data.Items) ? data.Items : data;

            if (page.options.skip === 0) {
                dataSet = App.ui.page.dataSet();
                page.detail.dataTable.dataTable("clear");
            } else {
                dataSet = page.detail.data;
            }
            page.detail.data = dataSet;

            page.values.su_code_standard = page.header.element.findP("nm_kaisha").children("option:selected").attr("su_code_standardval");
            //page.detail.dataTable.dataTable("addRows", data, function (row, item) {
            //    (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
            //    row.form(page.detail.options.bindOption).bind(item);
            //    item.su_code_standard = page.values.su_code_standard;
            //    if (App.isUndefOrNullOrStrEmpty(item.shubetsu_seiho)) {
            //        row.find(".biko-class").find("img").attr("src", "../Images/iconpaper.png");
            //    }

            //    if (item.flg_denso_jyotai) {
            //        row.findP("cd_haigo_new").prop("disabled", true);
            //    } else {
            //        row.findP("cd_haigo_new").val("");
            //        row.findP("cd_haigo_new").prop("disabled", true);
            //    }

            //    return row;
            //}, true);
            var rowNew;
            for (var i = 0; i < data.length; i++) {
                var item = data[i];

                if (item.row_num == 1) {
                    page.detail.dataTable.dataTable("addRow", function (tbody) {
                        (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
                        tbody.form(page.detail.options.bindOption).bind(item);
                        tbody.find("tr").attr("data-key", item.__id);
                        tbody.find("tr").addClass(item.no_seiho + "-" + item.row_num);
                        item.su_code_standard = page.values.su_code_standard;
                        if (App.isUndefOrNullOrStrEmpty(item.shubetsu_seiho)) {
                            tbody.find(".biko-class").find("img").attr("src", "../Images/iconpaper.png");
                        }

                        if (App.isUndefOrNullOrStrEmpty(item.shubetsu_seiho) || item.flg_denso_jyotai) {
                            tbody.find(".biko-class").prop("disabled", true);
                        }

                        if (item.flg_denso_jyotai) {
                            tbody.findP("cd_haigo_new").prop("disabled", true);
                        } else {
                            tbody.findP("cd_haigo_new").val("");
                            tbody.findP("cd_haigo_new").prop("disabled", true);
                        }
                        if (item.row_num_total > 1) {
                            tbody.find(".select-tab").closest("td").attr("rowspan", item.row_num_total);
                            tbody.findP("no_seiho").closest("td").attr("rowspan", item.row_num_total);
                            tbody.findP("nm_seiho").closest("td").attr("rowspan", item.row_num_total);
                            tbody.findP("no_seiho_sanko").closest("td").attr("rowspan", item.row_num_total);
                            tbody.findP("biko").closest("td").attr("rowspan", item.row_num_total);
                            tbody.findP("shubetsu_seiho").closest("td").attr("rowspan", item.row_num_total);
                            tbody.findP("nm_tanto_denso").closest("td").attr("rowspan", item.row_num_total);
                            tbody.findP("dt_denso_toroku").closest("td").attr("rowspan", item.row_num_total);
                            tbody.findP("dt_denso_kanryo").closest("td").attr("rowspan", item.row_num_total);
                            tbody.findP("flg_denso_jyotai").closest("td").attr("rowspan", item.row_num_total);
                        }

                        rowNew = tbody;

                        return tbody;
                    }, false);
                } else {
                    (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
                    rowNew.append(App.str.format(page.values.addTrDertail, item.no_seiho + "-" + item.row_num));
                    rowNew.find("." + item.no_seiho + "-" + item.row_num).form(page.detail.options.bindOption).bind(item);
                    if (item.flg_denso_jyotai) {
                        rowNew.find("." + item.no_seiho + "-" + item.row_num).findP("cd_haigo_new").prop("disabled", true);
                    } else {
                        rowNew.find("." + item.no_seiho + "-" + item.row_num).findP("cd_haigo_new").val("");
                        rowNew.find("." + item.no_seiho + "-" + item.row_num).findP("cd_haigo_new").prop("disabled", true);
                    }

                }
                if (i == (data.length - 1)) {
                    page.detail.element.find(".hidden-207").removeClass("hidden-207");
                }
            }
            $(page.detail.element.findP("no_seiho")[page.options.skip + 1]).trigger("click");
            page.options.skip += page.options.top;

            if (data.length > 0) {
                $("#save").prop("disabled", false);
            } else {
                $("#save").prop("disabled", true);
            }

            if (dataCount < page.options.skip) {
                page.options.skip = dataCount;
            }

            if (!isNewData) {
                page.detail.element.findP("data_count").text(page.options.skip);
                page.detail.element.findP("data_count_total").text(dataCount);
            }

            if (dataCount == 0 && !isNewData) {
                App.ui.page.notifyInfo.clear();
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

            page.values.su_code_standard = page.header.element.findP("nm_kaisha").children("option:selected").attr("su_code_standardval");
            page.detail.options.validations.cd_haigo_new.rules.maxlength = (parseInt(page.values.su_code_standard) + 1);
            page.detail.element.findP("cd_haigo_new").attr("maxlength", (parseInt(page.values.su_code_standard) + 1));
            page.values.cd_kaisha = page.header.element.findP("nm_kaisha").val();
            page.values.cd_kojyo = page.header.element.findP("nm_kojyo").val();

            //TODO: 画面明細へのデータバインド処理をここに記述します。
            offsetHeight = $("#nextsearch").is(":visible") ? $("#nextsearch").addClass("show-search").outerHeight() : 0;
            page.detail.dataTable.dataTable("setAditionalOffset", offsetHeight);

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
            if (!App.isUndefOrNull(page.detail.selectedRow)) {
                page.detail.selectedRow.element.find(".selected-row").removeClass("selected-row");
            }
            row.element.find("tr").addClass("selected-row");
            page.detail.selectedRow = row;
        };

        /**
         * 画面明細の一覧の入力項目の変更イベントの処理を行います。
         */
        page.detail.change = function (e, row) {
            var target = $(e.target),
                id = target.closest("tr").attr("data-key"),
                property = target.attr("data-prop"),
                entity = page.detail.data.entry(id),
                options = {
                    filter: page.detail.validationFilter
                },
                row = target.closest("tr");

            App.ui.page.notifyAlert.remove("AP0059");
            page.values.isChangeRunning[property] = true;

            if (property == "cd_haigo_new") {
                page.detail.validateListHaigo(target, row);
            }

            page.detail.executeValidation(target, row)
            .then(function () {
                
                if (property == "flg_denso_jyotai") {
                    if (row.findP("flg_denso_jyotai").prop("checked")) {
                        entity[property] = 1;
                        row.findP("flg_denso_jyotai").closest("tbody").addClass("checkedProcess");
                    } else {
                        entity[property] = 0;
                        row.findP("flg_denso_jyotai").closest("tbody").removeClass("checkedProcess");
                    }
                } else {
                    entity[property] = row.form().data()[property];
                }
                if(property == "cd_haigo_new") {
                    var cd_haigo_new = target.val();
                    if(cd_haigo_new != ""){
                        
                        var zeroList = "";
                        var su_code_standard = (parseInt(page.values.su_code_standard) + 1);
                        for (var i = 0; i < su_code_standard; i++) {
                            zeroList = zeroList + "0";
                        }
                        row.findP("cd_haigo_new").val(page.addZero(cd_haigo_new, zeroList));

                        entity[property] = page.addZero(cd_haigo_new, zeroList);
                    }
                }
                page.detail.data.update(entity);

                if (property == "flg_denso_jyotai") {
                    if (entity.flg_denso_jyotai) {
                        if (entity.flg_zyufuku_hyoji || entity.flg_zyufuku) {
                            row.findP("cd_haigo_new").val("");
                            row.findP("cd_haigo_new").prop("disabled", false);
                        } else {
                            var haigoCode = row.findP("cd_haigo").text();
                            row.findP("cd_haigo_new").val(haigoCode);
                            entity["cd_haigo_new"] = haigoCode;
                            row.findP("cd_haigo_new").prop("disabled", false);
                        }
                    } else {
                        row.findP("cd_haigo_new").val("");
                        row.findP("cd_haigo_new").prop("disabled", true);
                        entity["cd_haigo_new"] = null;
                    }
                    page.detail.enabledHaigo(row, entity);
                }

                page.detail.data.update(entity);

                //if ($("#save").is(":disabled")) {
                //    $("#save").prop("disabled", false);
                //}

                //入力行の他の項目のバリデーション（必須チェック以外）を実施します
                page.detail.executeValidation(row.find(":input"), row, options);

            }).fail(function () {
                if (property == "cd_haigo_new") {
                    var cd_haigo_new = target.val();
                    if (cd_haigo_new != "") {

                        var zeroList = "";
                        var su_code_standard = (parseInt(page.values.su_code_standard) + 1);
                        for (var i = 0; i < su_code_standard; i++) {
                            zeroList = zeroList + "0";
                        }
                        row.findP("cd_haigo_new").val(page.addZero(cd_haigo_new, zeroList));

                        entity[property] = page.addZero(cd_haigo_new, zeroList);
                    }
                }
            })
            .always(function () {
                page.detail.validateDuplicateHaigo();
                delete page.values.isChangeRunning[property];
            });
        };

        /**
        * Enabled haigo new。
        */
        page.detail.enabledHaigo = function (row, dataRow) {
            var i = 2,
                options = {
                    filter: page.detail.validationFilter
                };

            var rowNext = row.closest("tbody").find("." + dataRow.no_seiho + "-" + i);
            while (i <= dataRow.row_num_total) {
                var id = rowNext.closest("tr").attr("data-key"),
                entity = page.detail.data.entry(id);

                if (dataRow.flg_denso_jyotai) {
                    if (entity.flg_zyufuku_hyoji || entity.flg_zyufuku) {
                        rowNext.findP("cd_haigo_new").val("");
                        rowNext.findP("cd_haigo_new").prop("disabled", false);
                    } else {
                        var haigoCode = rowNext.findP("cd_haigo").text();
                        rowNext.findP("cd_haigo_new").val(haigoCode);
                        entity["cd_haigo_new"] = haigoCode;
                        rowNext.findP("cd_haigo_new").prop("disabled", false);
                    }
                    entity.flg_denso_jyotai = 1;
                } else {
                    rowNext.findP("cd_haigo_new").val("");
                    rowNext.findP("cd_haigo_new").prop("disabled", true);
                    entity["cd_haigo_new"] = null;
                    entity.flg_denso_jyotai = 0;
                }

                page.detail.data.update(entity);

                page.detail.executeValidation(rowNext.find(":input"), rowNext, options);

                i++;
                rowNext = row.closest("tbody").find("." + dataRow.no_seiho + "-" + i);
            }
            

        }

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

            //if ($("#save").is(":disabled")) {
            //    $("#save").prop("disabled", false);
            //}
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

            //if ($("#save").is(":disabled")) {
            //    $("#save").prop("disabled", false);
            //}
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
            return (method !== "required" && method !== "requiredCustom");
        };

        /**
         * 画面明細のバリデーションフィルターを設定します。（必須チェックを行わない）
         */
        page.detail.validationFilterDuplicate = function (item, method, state, options) {
            return (method !== "required" && method !== "requiredCustom");
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
                if (row.element.hasClass("checkedProcess")) {
                    var lengthRow = row.element.find("tr").length;
                    var i = 0;
                    while (i < lengthRow) {
                        var element = row.element.find("tr")[i];
                        validations.push(page.detail.executeValidation($(element).find(":input"), $(element), options));
                        i++;
                    }
                }
            });

            return App.async.all(validations);
        };

        /**
         * 画面明細の一覧のバリデーションを実行します。
         */
        page.detail.validateListHaigo = function (targets, row, suppressMessage) {
            var validations = [],
                options = {
                    targets: targets,
                    state: {
                        tbody: row,
                        suppressMessage: suppressMessage,
                    },
                    filter: page.detail.validationFilter
                };

            page.detail.dataTable.dataTable("each", function (row) {
                if (row.element.hasClass("checkedProcess")) {
                    var lengthRow = row.element.find("tr").length;
                    var i = 0;
                    while (i < lengthRow) {
                        var element = row.element.find("tr")[i];
                        validations.push(page.detail.executeValidation($(element).find(":input"), $(element), options));
                        i++;
                    }
                }
            });

            return App.async.all(validations);
        };

        /**
         * 画面明細の一覧のバリデーションを実行します。
         */
        page.detail.validateDuplicateHaigo = function (suppressMessage) {
            var validations = [],
                options = {
                    state: {
                        suppressMessage: suppressMessage,
                    },
                    filter: page.detail.validationFilterDuplicate
                };

            page.detail.dataTable.dataTable("each", function (row) {
                if (row.element.hasClass("checkedProcess")) {
                    var lengthRow = row.element.find("tr").length;
                    var i = 0;
                    while (i < lengthRow) {
                        var element = row.element.find("tr")[i];
                        if ($(element).findP("cd_haigo_new").val() != "") {
                            validations.push(page.detail.executeValidation($(element).find(":input"), $(element), options));
                        }
                        i++;
                    }
                }
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
                        <label>会社<span class="red-color">*</span></label>
                    </div>
                    <div class="control col-xs-3 fix-input-xs with-next-col">
                        <input type="tel" data-prop="cd_kaisha" maxlength="4" class="fixed limit-input-digit" style="ime-mode: disabled;" />
                        <label data-prop="cd_kaisha_validate" style="display: none;"></label>
                        <select data-prop="nm_kaisha" class="floated"></select>
                    </div>
                    <div class="control col-xs-2">
                    </div>
                    <div class="control-label col-xs-1">
                        <label>工場<span class="red-color">*</span></label>
                    </div>
                    <div class="control col-xs-3 fix-input-xs with-next-col">
                        <input type="tel" data-prop="cd_kojyo" maxlength="4" class="fixed limit-input-digit" style="ime-mode: disabled;" />
                        <label data-prop="cd_kojyo_validate" style="display: none;"></label>
                        <select data-prop="nm_kojyo" class="floated"></select>
                    </div>
                    <div class="control col-xs-2">
                    </div>
                </div>
                
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>伝送登録日</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="text"  data-prop="dt_denso_toroku_from" data-role="date" class="data-app-format" style="width:90px;"/>
                        <span>～</span>                 
                        <input type="text"  data-prop="dt_denso_toroku_to" data-role="date" class="data-app-format" style="width:90px;"/>                   
                    </div>
                    <div class="control-label col-xs-1">
                        <label>状態</label>
                    </div>
                    <div class="control col-xs-1">
                        <select  data-prop="flg_denso_jyotai" >
                            <option></option>
                            <option value="0" selected="selected">未受信</option>
                            <option value="1">受信済</option>
                        </select>
                    </div>
                    <div class="control-label col-xs-2">
                        <label>並べ替え順（製法作成日）</label>
                        </div>
                    <div class="control col-xs-2">
                        <label>
                            <input type="radio" name="jyotai" checked value="0" />
                            <i>昇順</i>
                        </label>
                        <label>
                            <input type="radio" name="jyotai" value="1" />
                            <i>降順</i>
                        </label>
                    </div>
                    <div class="control col-xs-2">
                    </div>
                </div>
                
                <div class="header-command">
                    <button type="button" id="search" class="btn btn-sm btn-primary" >検索</button>
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
                            <th style="width: 10px;" rowspan ="2"></th>
                            <th style="width: 30px;" rowspan ="2">受信</th>
                            <th style="width: 370px;" rowspan ="2">製法番号</th>
                            <th style="width: 100px;" colspan="2">重複</th>
                            <th style="width: 100px;"rowspan ="2">配合コード</th>
                            <th style="width: 100px;"rowspan ="2">新配合コード</th>
                            <th style="width: 200px;"rowspan ="2">配合名</th>
                            <th style="width: 150px;"rowspan ="2">参考製法番号</th>
                            <th style="width: 40px;"rowspan ="2">製種</th>
                            <th style="width: 30px;"rowspan ="2">備考</th>
                            <th style="width: 100px;"rowspan ="2">伝送登録者</th>
                            <th style="width: 90px;"rowspan ="2">伝送登録日</th>
                            <th style="width: 90px;"rowspan ="2">受信完了日</th>
                        </tr>
                        <tr>
                            <th style="width: 5px;">表示</th>
                            <th style="width: 5px;">製造</th>

                        </tr>
                        
                        <!--TODO: 多段行を作成する場合は、以下を利用し、上記１行は削除します。
							<th rowspan="2" style="width: 10px;"></th>
                       	</tr>
                        <tr>
						-->
                        
                    </thead>
                    <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->
                    <tbody class="hidden-207 item-tmpl" style="cursor: default; display: none;">
                        <tr>
                        <!--TODO: 単一行を作成する場合は、以下を利用します。-->
						    <td>
                                <span class="select-tab unselected"></span>
                            </td>
                            <td class="center">
                                <input type="checkbox" data-prop="flg_denso_jyotai"/>
                            </td>
                            <td>
                                <label data-prop="no_seiho"></label><br />
                                <label class="overflow-ellipsis" data-prop="nm_seiho"></label>
                            </td>
                            <td class="center">
                                <input type="checkbox" data-prop="flg_zyufuku_hyoji" disabled="disabled"/>
                            </td>
                            <td class="center">
                                <input type="checkbox" data-prop="flg_zyufuku" disabled="disabled"/>
                            </td>
                            <td>
                                <label data-prop="cd_haigo"></label>
                            </td>
                            <td>
                                <input type="tel" data-prop="cd_haigo_new" class="limit-input-digit" disabled="disabled" />
                            </td>
                            <td>
                                <label class="overflow-ellipsis" data-prop="nm_haigo" ></label>
                            <td>
                                <label data-prop="no_seiho_sanko"></label>
                            </td>
                            <td class="text-center">
                                <label class="overflow-ellipsis" data-prop="biko"></label>
                            </td>
                            <td class="center">
                                <button class="btn btn-xs biko-class">
                                    <img src="../Images/iconpaperspace.png" alt="icon" style="height: 20px;">
                                    <input type="text" data-prop="shubetsu_seiho" style="display: none;" />
                                </button>
                            </td>
                            <td>
                                <label data-prop="nm_tanto_denso"></label>
                            </td>
                            <td>
                                <label data-prop="dt_denso_toroku"></label>
                            </td>
                            <td>
                                <label data-prop="dt_denso_kanryo"></label>
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
                    <button type="button" id="nextsearch" class="btn btn-sm btn-primary btn-next-search" style="display: none">次を検索</button>
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
        <button type="button" id="save" class="btn btn-sm btn-primary" disabled="disabled">表示用受信</button>
    </div>

</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
    <div class="modal custom-model" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <textarea data-prop="shubetsu_seiho" style="height: 153px; width: 100%;" disabled="disabled"></textarea>
                </div>
            </div>
        </div>
    </div>
</asp:Content>