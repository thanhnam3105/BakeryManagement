<%@ Page Title="212_" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="212_GenryoIchiran.aspx.cs" Inherits="Tos.Web.Pages._212_GenryoIchiran" %>

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
    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("_212_GenryoIchiran", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.dataTakeCount,   // TODO:取得するデータ数を指定します。
                filter: ""
            },
            values: {
                isChangeRunning: {},
                Kbn_bumon: null,
                bumon: { lab: 1, factory: 2 },
                is_bumon_lab_title: "原料一覧",
                is_bumon_factory_title: "原料一覧(FOODPROCS)",
                cd_kaisha: "",
                cd_kojyo: "",
                cd_hin: "",
                kbn_hin_ini: 1,
                flg_bumon: true,
                su_code_standard: null,
                nm_kbn_hin_6: "自家原料",
                kbn_hin_1: 1,
                kbn_hin_6: 6,
                fix_column_lab: 4,
                fix_column_factory: 5,
                default_su_code: 6,
                lab_width: 2660,
                factory_width: 2340
            },
            urls: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx"
            },
            header: {
                options: {},
                values: {},
                urls: {
                    ma_kaisha: "../Services/ShisaQuickService.svc/ma_kaisha?{0}&$orderby=cd_kaisha",
                    vw_kaisha_kojyo: "../Services/ShisaQuickService.svc/vw_kaisha_kojyo?$filter=cd_kaisha eq {0}{1}&$orderby=cd_kaisha,cd_kojyo",
                    ma_kbn_hin: "../Services/ShisaQuickService.svc/ma_kbn_hin?$orderby=kbn_hin&$filter=kbn_hin eq {0} or kbn_hin eq {1}",
                    ma_bunrui: "../api/_212_GenryoIchiran/getBunrui",
                    search: "../api/_212_GenryoIchiran/",
                    maxLengthHin: "../Services/ShisaQuickService.svc/ma_kaisha?$filter=cd_kaisha eq {0}"
                }
            },
            detail: {
                options: {
                    downloadCSV: "../api/_212_GenryoIchiranCSVDownload",
                },
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
                GenryoMasterSanshoDialog: "../Pages/Dialogs/708_GenryoMasterSansho_Dialog.aspx",
                BunruiKensakuDialog: "../Pages/Dialogs/709_BunruiKensaku_Dialog.aspx"
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

            if (!App.isUndefOrNullOrStrEmpty(App.uri.queryStrings.Kbn_bumon) || !App.isUndefOrNullOrStrEmpty(App.uri.queryStrings.Kbn_bumon)) {
                page.values.Kbn_bumon = App.uri.queryStrings.Kbn_bumon;
                if (!App.isUndefOrNullOrStrEmpty(App.uri.queryStrings.Kbn_bumon)) {
                    page.values.Kbn_bumon = App.uri.queryStrings.Kbn_bumon;
                }
                if (page.values.Kbn_bumon == App.settings.app.kbn_bumon.lab) {
                    $(".page-title").text(page.values.is_bumon_lab_title);
                    $(".wrap, .footer").addClass("theme-yellow");
                } else if (page.values.Kbn_bumon == App.settings.app.kbn_bumon.factory) {
                    $(".page-title").text(page.values.is_bumon_factory_title);
                } else {
                    App.ui.loading.close();
                    return;
                }
                document.title = $(".page-title").text() + document.title;
            }
            page.detail.setTable();
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
                if (!App.isUndefOrNullOrStrEmpty(App.uri.queryStrings.Kbn_bumon)) {
                    page.values.Kbn_bumon = App.uri.queryStrings.Kbn_bumon;
                }
                if (page.values.Kbn_bumon == App.settings.app.kbn_bumon.lab) {
                    page.header.options.validations.cd_kojyo.rules.required = false;
                } else {
                    page.header.options.validations.cd_kojyo.rules.required = true;
                }
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
            $("#csv-download").on("click", page.commands.downloadCSV);
        };
        /**
        *Download CSV_212
        **/
        page.commands.downloadCSV = function (isLoading) {
            var param = page.header.createFilter();

            App.ui.page.notifyAlert.clear();
            page.header.validator.validate().done(function () {
                if (isLoading) {
                    App.ui.loading.show();
                    App.ui.page.notifyAlert.clear();
                }
                return $.ajax(App.ajax.file.download(page.detail.options.downloadCSV, param))
               .then(function (response, status, xhr) {
                   if (status !== "success") {
                       App.ui.page.notifyAlert.message(App.messages.base.MS0005).show();
                   } else {
                       App.file.save(response, App.ajax.file.extractFileNameDownload(xhr) || "CSVFile.csv");
                       if (param.Kbn_bumon == 1) {

                           //TOsVN - 19075 - 2023/06/20: st ShohinKaihatuSupport Modify 2023
                           App.common.Log.write({
                               cd_game: App.settings.app.log_mode.gamen.GenryoIchiranCSV
                               , cd_taisho_data: null
                               , nm_mode: null
                           });
                           //ed
                       }
                   }
               }).fail(function (error) {
                   App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
               }).always(function () {
                   if (isLoading) {
                       App.ui.loading.close();
                   }
               });
            });
        };
        /**
         * set nm_kbn_hin with kbn_hin = 6
         */
        page.setBunrui = function (result) {
            var i, item = [],
                n = result.value.length;
            for (i = 0 ; i < n ; i++) {
                item.push(result.value[i]);
                if (item[i].kbn_hin == page.values.kbn_hin_6) {
                    item[i].nm_kbn_hin = page.values.nm_kbn_hin_6
                }
            }
            return item;
        }
        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function () {
            var element = page.header.element,
                kbn_hin_1 = page.values.kbn_hin_1,
                kbn_hin_6 = page.values.kbn_hin_6;
            //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
            return $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.ma_kbn_hin, kbn_hin_1, kbn_hin_6))).then(function (result) {
                var kbn_hin = page.header.element.findP("kbn_hin");
                kbn_hin.children().remove();
                result = page.setBunrui(result);
                App.ui.appendOptions(
                    kbn_hin,
                    "kbn_hin",
                    "nm_kbn_hin",
                    result,
                    false
                );


                return page.kaishaKojoJotai(page.header.urls.ma_kaisha, "nm_kaisha", "cd_kaisha", "nm_kaisha", "", false).then(function () {
                    var cd_kaisha = element.findP("nm_kaisha").val();
                    element.findP("nm_kaisha").val(App.ui.page.user.cd_kaisha);
                    cd_kaisha = App.ui.page.user.cd_kaisha;
                    page.values.cd_kaisha = cd_kaisha;
                    page.SetSuCodeStandard(page.values.cd_kaisha);
                    if (!App.ui.page.user.flg_kaishakan_sansyo) {
                        element.find("[data-prop='cd_kaisha'], [data-prop='nm_kaisha']").prop("disabled", true);
                    }

                    if (!App.isUndefOrNullOrStrEmpty(cd_kaisha)) {
                        element.findP("cd_kaisha").val(App.common.getFullCdKaisha(cd_kaisha));
                        element.findP("cd_kaisha_validate").text(cd_kaisha);
                        var flg_bumon = page.values.flg_bumon;
                        if (page.values.Kbn_bumon == page.values.bumon.factory) {
                            flg_bumon = !page.values.flg_bumon;
                        }
                        page.kaishaKojoJotai(page.header.urls.vw_kaisha_kojyo, "nm_kojyo", "cd_kojyo", "nm_kojyo", cd_kaisha, flg_bumon).then(function () {
                            var cd_kojyo = element.findP("nm_kojyo").val();
                            if ((App.ui.page.user.cd_busho == null || App.ui.page.user.cd_busho == "") && page.values.Kbn_bumon == App.settings.app.kbn_bumon.factory) {
                                cd_kojyo = element.find("[data-prop=nm_kojyo] option:selected").val();
                            }
                            else { 
                            element.findP("nm_kojyo").val(App.ui.page.user.cd_busho);
                            cd_kojyo = App.ui.page.user.cd_busho;
                            }
                            page.values.cd_kojyo = cd_kojyo;
                            if (!App.ui.page.user.flg_kojyokan_sansyo) {
                                element.find("[data-prop='cd_kojyo'], [data-prop='nm_kojyo']").prop("disabled", true);
                            }

                            if (!App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                                element.findP("cd_kojyo").val(App.common.getFullString(cd_kojyo, "0000"));
                                element.findP("cd_kojyo_validate").text(cd_kojyo);
                            }
                        });

                    }
                });

            });

            return App.async.success();
        };
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
         * Load combobox 製造会社, 製造工場
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
                GenryoMasterSanshoDialog: $.get(page.dialogs.GenryoMasterSanshoDialog),
                BunruiKensakuDialog: $.get(page.dialogs.BunruiKensakuDialog)
            }).then(function (result) {

                $("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;
                $("#dialog-container").append(result.successes.GenryoMasterSanshoDialog);
                page.dialogs.GenryoMasterSanshoDialog = _708_GenryoMasterSansho_Dialog;
                page.dialogs.GenryoMasterSanshoDialog.param.kbn_bumon = parseInt(page.values.Kbn_bumon);
                page.dialogs.GenryoMasterSanshoDialog.initialize();

                $("#dialog-container").append(result.successes.BunruiKensakuDialog);
                page.dialogs.BunruiKensakuDialog = _709_BunruiKensaku_Dialog;
                page.dialogs.BunruiKensakuDialog.param.kbn_bumon = parseInt(page.values.Kbn_bumon);
                page.dialogs.BunruiKensakuDialog.param.kbn_hin = page.values.kbn_hin_ini;
                page.dialogs.BunruiKensakuDialog.param.cd_kaisha = page.values.cd_kaisha;
                page.dialogs.BunruiKensakuDialog.param.cd_kojyo = page.values.cd_kojyo;
                page.dialogs.BunruiKensakuDialog.initialize();
            });
        };
        /**
        * Set su_code_standard
        **/

        page.SetSuCodeStandard = function (cd_kaisha) {
            $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.maxLengthHin, parseInt(cd_kaisha))))
                   .then(function (result) {
                       if (result) {
                           if (page.values.Kbn_bumon == App.settings.app.kbn_bumon.lab) {
                               page.values.su_code_standard = result.value[0].su_code_standard;
                               page.header.options.validations.cd_hin.rules.maxlength = Number(page.values.su_code_standard);
                               page.header.element.findP("cd_hin").prop("maxlength", Number(page.values.su_code_standard));
                           
                           } else {
                               page.values.su_code_standard = page.values.default_su_code;
                           }
                           page.header.options.validations.cd_hin.rules.maxlength = page.values.su_code_standard;
                           page.header.element.findP("cd_hin").attr("maxlength", page.values.su_code_standard);
                       }

                   });
        }
        /*
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
                        var element = page.header.element,
                            cd_kaisha = element.findP("cd_kaisha").val(),
                            cd_kaisha_validate = element.findP("cd_kaisha_validate").text();
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
                    },
                    requiredKojo: function (value, opts, state, done) {
                        var element = page.header.element,
                            cd_hin = element.findP("cd_hin").val(),
                            cd_bunrui = element.findP("cd_bunrui").val(),
                            check_left = element.findP("check_left"),
                            cd_kojyo = element.findP("cd_kojyo").val();
                        if (!App.isUndefOrNullOrStrEmpty(cd_bunrui) && App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                            return done(false);
                        }
                        if (check_left.prop("checked") && App.isUndefOrNullOrStrEmpty(cd_kojyo) ) {
                            return done(false);
                        }
                        if (state != null) {
                            if (state.mode == 1) {
                                return done(false);
                            }
                        }
                        return done(true);
                    }
                },
                options: {
                    name: "工場コード"
                },
                messages: {
                    required: App.messages.base.required,
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits,
                    existsCode: App.messages.app.AP0010,
                    requiredKojo: App.messages.base.required,
                }
            },
            cd_bunrui: {
                rules: {
                    maxlength: 2,
                    digits: true,
                        requiredname: function (value, opts, state, done) {
                            var element = page.header.element,
                                cd_bunrui = element.findP("cd_bunrui").val(),
                                cd_kojyo = element.findP("cd_kojyo").val(),
                                nm_bunrui = element.findP("nm_bunrui").text();
                            if (App.isUndefOrNullOrStrEmpty(value)) {
                                return done(true);
                            }
                            if (App.isUndefOrNullOrStrEmpty(nm_bunrui) && App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                                return done(true);
                            }
                            return done(nm_bunrui != "");
                        }
                },
                options: {
                    name: "分類コード"
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits,
                    requiredname: App.messages.app.AP0010
                }
            },
            cd_hin: {
                rules: {
                    maxlength: page.values.su_code_standard,
                    digits: true,
                    existsCode_Hin: function (value, opts, state, done) {
                        var element = page.header.element,
                            cd_hin = element.findP("cd_hin").val(),
                            check_left = element.findP("check_left"),
                            cd_hin_validate = element.findP("cd_hin_validate").text();
                        setTimeout(function(){
                            if (App.isUndefOrNullOrStrEmpty(cd_hin) && check_left.prop("checked")) {
                                return done(false);
                            } else {
                                return done(true);
                            }
                        }, 100);
                    }
                },
                options: {
                    name: "原料コード指定"
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits,
                    existsCode_Hin: App.messages.base.required
                }
            },
            no_kikaku1: {
                rules: {
                    maxlength: 4,
                    digits: true
                },
                options: {
                    name: "規格書No",
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits
                }
            },
            no_kikaku2: {
                rules: {
                    maxlength: 6,
                    digits: true
                },
                options: {
                    name: "規格書No",
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits
                }
            },
            no_kikaku3: {
                rules: {
                    maxlength: 5,
                    digits: true
                },
                options: {
                    name: "規格書No",
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits
                }
            },
            nm_hin: {
                rules: {
                    maxbytelength: 60
                },
                options: {
                    name: "原料名／コード",
                    byte: 30
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            nm_bussitsu: {
                rules: {
                    maxbytelength: 74
                },
                options: {
                    name: "物質名",
                    byte: 37
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            nm_kikaku: {
                rules: {
                    maxbytelength: 80
                },
                options: {
                    name: "規格書商品名",
                    byte: 40
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            nm_hanbai: {
                rules: {
                    maxbytelength: 80
                },
                options: {
                    name: "販売社名",
                    byte: 40
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            nm_seizo: {
                rules: {
                    maxbytelength: 80
                },
                options: {
                    name: "製造社名",
                    byte: 40
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
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
            element.on("click", "#709_Dialog", page.header.BunruiKensaku);
            page.header.initializeBumon();
            element.findP("cd_bunrui").complete({
                textLength: 2,
                ajax: function (val) {

                    var target = page.header.element.findP("cd_kojyo"),
                        kojyo = target.val(),
                        kaisha = page.header.element.findP("cd_kaisha").val();

                    if (kojyo == null || kojyo == "") {
                        var target = page.header.element.findP("cd_kojyo");
                        page.header.validator.validate({
                            targets: target
                        });
                    }
                    var params = {
                        Kbn_bumon: page.values.Kbn_bumon,
                        cd_kaisha: element.findP("cd_kaisha").val(),
                        cd_kojyo: element.findP("cd_kojyo").val(),
                        kbn_hin: element.findP("kbn_hin").val(),
                        cd_bunrui: element.findP("cd_bunrui").val()
                    };
                    return $.ajax(App.ajax.webapi.get(page.header.urls.ma_bunrui, params));
                },
                success: page.header.completeBunrui,
                error: page.header.clearBunrui,
                clear: page.header.clearBunrui
            });
        };
        /**
        *Initialize with kbn_bumon
        **/
        page.header.initializeBumon = function () {
            var element = page.header.element;
            if (page.values.Kbn_bumon == App.settings.app.kbn_bumon.factory) {
                element.findP("kbn_hin").prop("disabled", true);
                element.findP("sort").children('option[value=0]').remove();
                element.findP("check_right1").parent().hide();
            } else {
                element.findP("label").children().remove();

            }
        }
        /**
        *Open dialog 709
        **/
        page.header.BunruiKensaku = function () {
            var element = page.header.element,
                cd_kaisha = element.findP("cd_kaisha").val(),
                cd_kojyo = element.findP("cd_kojyo").val(),
                kbn_hin = element.findP("kbn_hin").val(),
                kbn_bumon = parseInt(page.values.Kbn_bumon);
            App.ui.page.notifyAlert.clear();
            if (cd_kojyo == null || cd_kojyo == "") {
                var target = element.findP("cd_kojyo");
                page.header.validator.validate({
                    targets: target,
                    state: { mode: 1 }
                });
                return;
            }
            page.dialogs.BunruiKensakuDialog.param.kbn_bumon = kbn_bumon;
            page.dialogs.BunruiKensakuDialog.param.kbn_hin = kbn_hin
            page.dialogs.BunruiKensakuDialog.param.cd_kaisha = cd_kaisha;
            page.dialogs.BunruiKensakuDialog.param.cd_kojyo = cd_kojyo;
            page.dialogs.BunruiKensakuDialog.element.modal("show");
            page.dialogs.BunruiKensakuDialog.dataSelected = function (data) {
                element.findP("cd_bunrui").val(data.cd_bunrui).change();
                element.findP("nm_bunrui").text(data.nm_bunrui);
            }
        }
        /**
        *Bind nm_hin 
        **/
        page.header.completeBunrui = function (data) {
            var cd_kojyo = page.header.element.findP("cd_kojyo").val();
            if (data.length > 0 && !App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                page.header.element.findP("nm_bunrui").text(data[0].nm_bunrui);
                page.header.validator.validate({
                    targets: page.header.element.findP("cd_bunrui")
                });
            }
            else {
                page.header.element.findP("nm_bunrui").text("");
                page.header.clearBunrui();
            }
        }
        /**
        *Action when bind nm_hin fail 
        **/
        page.header.clearBunrui = function (data) {
            var target = page.header.element.findP("cd_bunrui"),
                kojyo = page.header.element.findP("cd_kojyo").val(),
                kaisha = page.header.element.findP("cd_kaisha").val();

            if (kojyo == null || kojyo == "") {
                return;
            }
            page.header.element.findP("nm_bunrui").text("");
            page.header.validator.validate({
                targets: target
            });

        };

        /**
         * 画面明細のバリデーションフィルターを設定します。（必須チェックを行わない）
         */
        page.header.validationFilter = function (item, method, state, options) {
            return method !== "requiredname";
        };

        /**
         * 画面明細のバリデーションフィルターを設定します。（必須チェックを行わない）
         */
        page.header.validationFilterHin = function (item, method, state, options) {
            return method !== "existsCode_Hin";
        };
        
        /**
         * 画面ヘッダーの変更時処理を定義します。
         */
        page.header.change = function (e) {
            if ($("#nextsearch").hasClass("show-search")) {
                $("#nextsearch").removeClass("show-search").hide();
                App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
            }
            else if (page.detail.searchData) {
                // 保持検索データの消去
                page.detail.searchData = undefined;
                App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
            }

            var element = page.header.element,
            target = $(e.target),
            property = target.attr("data-prop"),
            valOld = target.val(),
            options = {
                targets: element.findP("cd_hin")
                , filter: page.header.validationFilterHin
            };
            var validateTarget = target;

            if (property == "no_kikaku1") {
                valNew = App.common.getFullString(valOld, "0000");
                target.val(valNew);
            }

            else if (property == "no_kikaku2") {
                valNew = App.common.getFullString(valOld, "000000");
                target.val(valNew);
            }

            else if (property == "no_kikaku3") {
                valNew = App.common.getFullString(valOld, "00000");
                target.val(valNew);
            }
            else if (property == "cd_bunrui") {
                    valNew = App.common.getFullString(valOld, "00");
                    target.val(valNew);
                if (valOld != "") {
                    return;
                }
            }
            else if (property == "kbn_hin") {
                var cd_bunrui = element.findP("cd_bunrui"),
                    nm_bunrui = element.findP("nm_bunrui");
                cd_bunrui.val("").change();
                nm_bunrui.text("");
            }
            else if (property == "cd_hin") {
                var cd_hin = element.findP("cd_hin").val(),
                    cd_kaisha = element.findP("cd_kaisha").val(),
                    cd_kojyo = element.findP("cd_kojyo").val();

                //page.SetSuCodeStandard(cd_kaisha);
                if (App.isNumeric(valOld)) {
                    element.findP("cd_hin").prop("maxlength", page.values.su_code_standard);
                    valNew = App.common.fillString(valOld, page.values.su_code_standard, "0");
                    target.val(valNew);
                }
                //if (!App.isUndefOrNullOrStrEmpty(cd_hin) && check_left) {
                //    page.header.validator.validate({
                //        targets: element.findP("cd_hin")
                //    })
                //}
             

            }
            else if (property == "check_left") {
                    var check_left = element.findP("check_left");
                    if (check_left.prop("checked")) {
                        element.findP("check_right3").prop("disabled", true);
                   
                    } else {
                    
                        element.findP("check_right3").prop("disabled", false);
                        target = element.find("[data-prop='cd_hin'],[data-prop='cd_kojyo']");
                    }
                    page.header.validator.validate(options);

            }
            else if (property == "check_right3") {
                var check_right3 = element.findP("check_right3");
                if (check_right3.prop("checked")) {
                    element.findP("check_left").prop("disabled", true);
                } else {
                    element.findP("check_left").prop("disabled", false);
                }
            }
            else if (property == "cd_kaisha" || property == "nm_kaisha") {
                var cd_kaisha = target.val();
                if (App.ui.page.user.flg_kojyokan_sansyo) {
                    element.findP("nm_kojyo").children().remove();
                    element.findP("cd_kojyo").val("");  
                    element.findP("cd_bunrui").val("").data("lastVal","").change();
                    element.findP("nm_bunrui").text("");
                }
                page.values.isChangeRunning["changeKaisha_Kojyo"] = true;
                element.findP("cd_kaisha").val(App.common.getFullCdKaisha(cd_kaisha));
                element.findP("cd_kaisha_validate").text(cd_kaisha);
                element.findP("nm_kaisha").val(cd_kaisha);
                page.SetSuCodeStandard(cd_kaisha);
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
                    if((cd_kaisha != "" && !App.isNumeric(cd_kaisha)) || cd_kaisha == "") {
                        delete page.values.isChangeRunning["changeKaisha_Kojyo"];
                    }
                    
                }

                if (property == "nm_kaisha" || page.values.isResetKojyo) {
                    
                        cd_kaisha = parseInt(cd_kaisha);
                        var flg_bumon = page.values.flg_bumon;
                        if (page.values.Kbn_bumon == page.values.bumon.factory) {
                            flg_bumon = !page.values.flg_bumon;
                        }
                       if (cd_kaisha != "" && App.isNumeric(cd_kaisha) && App.ui.page.user.flg_kojyokan_sansyo) {
                        page.kaishaKojoJotai(page.header.urls.vw_kaisha_kojyo, "nm_kojyo", "cd_kojyo", "nm_kojyo", cd_kaisha, flg_bumon).then(function () {
                            var cd_kojyo = element.findP("nm_kojyo").val();
                            if (!App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                                element.findP("cd_kojyo").val(App.common.getFullCdKojyo(cd_kojyo));
                                element.findP("cd_kojyo_validate").text(cd_kojyo);
                            }
                        }).then(function(){
                           page.header.validator.validate({
                               targets: element.find("[data-prop='cd_kaisha'], [data-prop='nm_kaisha'],[data-prop='cd_kojyo'], [data-prop='nm_kojyo'],[data-prop='cd_hin']")
                           });
                        }).always(function () {
                           if (page.values.isChangeRunning["changeKaisha_Kojyo"]) {
                                delete page.values.isChangeRunning["changeKaisha_Kojyo"];
                            }
                        });
                    }
                    if ((cd_kaisha == "" || !App.isNumeric(cd_kaisha)) && page.values.Kbn_bumon == App.settings.app.kbn_bumon.factory) {
                        page.header.validator.validate({
                            targets: element.find("[data-prop='cd_kojyo'], [data-prop='nm_kojyo']")
                        });
                    }
                   // var sleep = 0;
                    //var condition = "Object.keys(page.values.isChangeRunning).length == 0";
                   // App.ui.wait(sleep, condition, 100)
                   // .then(function () {
                    //    if (cd_kojyo == undefined) {
                    //       page.header.validator.validate({
                     //           targets: element.find("[data-prop='cd_kojyo'], [data-prop='nm_kojyo']")
                     //       });
                     ///  }
                    //});
                }
            }
            else if (property == "cd_kojyo") {
                var cd_kojyo = target.val();
                element.findP("cd_kojyo").val(App.common.getFullString(cd_kojyo, "0000"));
                element.findP("cd_kojyo_validate").text(cd_kojyo);
                element.findP("cd_bunrui").val("").change();
                element.findP("nm_bunrui").text("");
                element.findP("nm_kojyo").val(cd_kojyo);
                if (App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                    element.findP("nm_kojyo").val(cd_kojyo);
                }
                if (cd_kojyo != "" && !App.isNumeric(cd_kojyo)) {
                    page.header.validator.validate({
                        targets: element.findP("cd_kojyo")
                    });
                    return;
                }
                if (cd_kojyo != "" && App.isNumeric(cd_kojyo)) {
                    cd_kojyo = parseInt(cd_kojyo);
                    var cd_kaisha = element.findP("nm_kaisha").val();

                    page.checkData(App.str.format(page.header.urls.vw_kaisha_kojyo, "{0}", " and cd_kojyo eq {1}"), cd_kaisha, cd_kojyo,
                                    element, "cd_kojyo", "cd_kojyo_validate", "nm_kojyo", cd_kojyo);
                }
            }
            else if (property == "nm_kojyo") {
                var cd_kojyo = target.val();

                element.findP("cd_kojyo").val(App.common.getFullString(cd_kojyo, "0000")).change();
                //element.findP("cd_kojyo_validate").text(cd_kojyo);
                //element.findP("cd_bunrui").val("").data("lastVal", "");
                //element.findP("nm_bunrui").text("");
                //element.findP("nm_kojyo").val(cd_kojyo);
                //page.header.validator.validate({
                //    targets: element.findP("cd_kojyo")
                //});
            }
            else if (property != "cd_bunrui" && (property == "cd_kaisha" || property == "nm_kaisha")) {
                page.header.validator.validate({
                    targets: element.findP("cd_bunrui")
                });
            }
            
            //delete page.values.isChangeRunning["validateHin"];
            var sleep = 0;
            var condition = "Object.keys(page.values.isChangeRunning).length == 0";
            App.ui.wait(sleep, condition, 100).then(function () {
                
                page.header.validator.validate({
                    targets: target,
                    filter: page.header.validationFilter
                });
            })   ;     
        };
        /**
        * 検索処理を定義します。
        */
        page.checkData = function (url, param1, param2, element, cd_code, cd_validate, nm_name, value) {
            return $.ajax(App.ajax.odata.get(App.str.format(url, param1, param2))).then(function (result) {
                if (result.value.length > 0) {
                    element.findP(cd_code).val(App.common.getFullString(value, "0000"));
                    element.findP(cd_validate).text(value);
                    element.findP(nm_name).val(value);
                    page.values.isResetKojyo = true;
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
         * 検索処理を定義します。
         */
        page.header.search = function (isLoading) {

            var deferred = $.Deferred(),
                query;

            App.ui.page.notifyAlert.clear();

            page.header.validator.validate().done(function () {

                page.options.skip = 0;
                page.options.filter = page.header.createFilter();



                //TODO: データ取得サービスの URLとオプションを記述します。
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
                });
            });

            return deferred.promise();
        };

        /**
         * 検索条件のフィルターを定義します。
         */
        page.header.createFilter = function () {
            page.header.createFilter
            var criteria = page.header.element.form().data(),
                filters = {};

            /* TODO: 検索条件のフィルターを定義してください。*/
            if (!App.isUndefOrNullOrStrEmpty(page.values.Kbn_bumon)) {
                filters.Kbn_bumon = page.values.Kbn_bumon;
            }


            if (!App.isUndefOrNullOrStrEmpty(criteria.cd_kaisha) && criteria.cd_kaisha > 0) {
                filters.cd_kaisha = criteria.cd_kaisha;
            }
            if (!App.isUndefOrNullOrStrEmpty(criteria.cd_kojyo) && criteria.cd_kojyo > 0) {
                filters.cd_kojyo = criteria.cd_kojyo;
            }
            if (!App.isUndefOrNullOrStrEmpty(criteria.kbn_hin) && criteria.kbn_hin > 0) {
                filters.kbn_hin = criteria.kbn_hin;
            }
            filters.cd_bunrui = criteria.cd_bunrui;
            filters.no_kikaku1 = criteria.no_kikaku1;
            filters.no_kikaku2 = criteria.no_kikaku2;
            filters.no_kikaku3 = criteria.no_kikaku3;
            filters.cd_hin = criteria.cd_hin;
            filters.nm_kikaku = criteria.nm_kikaku;
            filters.nm_hin = criteria.nm_hin;
            filters.nm_hanbai = criteria.nm_hanbai;
            filters.nm_bussitsu = criteria.nm_bussitsu;
            filters.nm_seizo = criteria.nm_seizo;
            filters.sort = criteria.sort;
            filters.top = page.options.top;
            filters.skip = page.options.skip;
            filters.cd_hin_str = criteria.cd_hin;
            filters.cd_bunrui_str = criteria.cd_bunrui;
            if (page.header.element.findP("check_left").prop("checked")) {
                filters.check_left = true;
            } else {
                filters.check_left = false;
            }
            if (page.header.element.findP("check_right1").prop("checked")) {
                filters.check_right1 = true;
            } else {
                filters.check_right1 = false;
            }
            if (page.header.element.findP("check_right2").prop("checked")) {
                filters.check_right2 = true;
            } else {
                filters.check_right2 = false;
            }
            if (page.header.element.findP("check_right3").prop("checked")) {
                filters.check_right3 = true;
            } else {
                filters.check_right3 = false;
            }

            return filters;
        };

        //TODO: 以下の page.header の各宣言は画面仕様ごとにことなるため、不要の場合は削除してください。
        //TODO-END: 

        /**
         * 画面明細の初期化処理を行います。
         */
        page.detail.initialize = function () {
            var element = $(".detail"),
                table = element.find(".datatable"),
                bumon = parseInt(page.values.Kbn_bumon),
                fixColums = (bumon == App.settings.app.kbn_bumon.lab) ? page.values.fix_column_lab : page.values.fix_column_factory;
                innerwidth = (bumon == App.settings.app.kbn_bumon.lab) ? page.values.lab_width : page.values.factory_width;
            datatable = table.dataTable({
                height: 300,
                resize: true,
                //列固定横スクロールにする場合は、下記3行をコメント解除
                fixedColumn: true,                //列固定の指定
                fixedColumns: fixColums,                 //固定位置を指定（左端を0としてカウント）
                innerWidth: innerwidth,                 //可動列の合計幅を指定
                onselect: page.detail.select
            });
            table = element.find(".datatable");         //列固定にした場合DOM要素が再作成されるため、変数を再取得

            page.detail.element = element;
            page.detail.dataTable = datatable;

            element.on("click", "#nextsearch", page.detail.nextsearch);
            element.on("click", "#add-item", page.detail.addNewItem);
            element.on("click", "#708_Dialog", page.detail.GenryoMasterSansho)

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

        };
        /**
            * Edit table with kbn_bumon
            **/
        page.detail.setTable = function () {
            var element = $(".detail"),
            table = element.find(".datatable"),
            bumon = parseInt(page.values.Kbn_bumon);
            if (bumon == App.settings.app.kbn_bumon.factory) {
                table.findP("th_cd_kojyo").hide();
                table.findP("cd_kojyo").closest("td").hide();
                table.findP("th_nm_busho").hide();
                table.findP("nm_busho").closest("td").hide();
                table.findP("th_kbn_haishi").hide();
                table.findP("kbn_haishi").closest("td").hide();
            } else {
                table.findP("th_cd_kojyo").show();
                table.findP("cd_kojyo").closest("td").show();
                table.findP("th_nm_busho").show();
                table.findP("nm_busho").closest("td").show();
                table.findP("th_kbn_haishi").show();
                table.findP("kbn_haishi").closest("td").show();
            }
        }
        /**
        * Move to display 708_原料マスタ参照
        **/
        page.detail.GenryoMasterSansho = function (e) {
            var target = $(e.target),
                element = page.detail.element,
                id = target.closest("tbody").attr("data-key"),
                entity = page.detail.data.entry(id);
            var Bumon = page.values.Kbn_bumon;
            if (Bumon == App.settings.app.kbn_bumon.lab) {
                page.dialogs.GenryoMasterSanshoDialog.param.cd_kaisha = entity.cd_kaisha;
                page.dialogs.GenryoMasterSanshoDialog.param.cd_kojyo = entity.cd_kojyo;
            } else {
                page.values.cd_kaisha = page.header.element.findP("cd_kaisha").val();
                page.values.cd_kojyo = page.header.element.findP("cd_kojyo").val();
                page.dialogs.GenryoMasterSanshoDialog.param.cd_kaisha = page.values.cd_kaisha;
                page.dialogs.GenryoMasterSanshoDialog.param.cd_kojyo = page.values.cd_kojyo;
            }
            page.dialogs.GenryoMasterSanshoDialog.param.kbn_bumon = Bumon;
            page.dialogs.GenryoMasterSanshoDialog.param.cd_hin = entity.cd_hin;
            page.dialogs.GenryoMasterSanshoDialog.element.modal("show");
        }
        /**
         * 次のレコードを検索する処理を定義します。
         */
        page.detail.nextsearch = function () {
            App.ui.loading.show();
            App.ui.page.notifyAlert.clear();
            page.options.filter = page.header.createFilter();
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
            appliers: {
                dt_toroku: function (value, element) {
                    element.text(App.data.getDateString(new Date(value)))
                    return true;
                },
                cd_hin: function (value, element) {
                    if (page.values.Kbn_bumon == App.settings.app.kbn_bumon.lab) {
                        element.text(App.common.fillString(value, page.values.su_code_standard, "0"))
                        return true;
                    }
                },
                kbn_haishi: function (value, element) {
                    if (value == 1 || value == 2) {
                        element.prop("checked", true)
                        return true;
                    }
                },
                flg_mishiyo: function (value, element) {
                    if (value == true) {
                        element.prop("checked", true)
                        return true;
                    }
                },
                cd_kojyo: function (value, element) {
                    element.text(App.common.getFullCdKojyo(value))
                    return true;
                }
            }
        };

        /**
         * 画面明細へのデータバインド処理を行います。
         */
        page.detail.bind = function (data, isNewData) {
            var i, l, item, dataSet, dataCount, offsetHeight;
            var table = page.detail.element.find(".datatable");

            dataCount = data.Count ? data.Count : 0;
            data = (data.Items) ? data.Items : data;
            if (data.length > 0) {
                dataCount = data[0].total_rows;
            }
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
            //page.detail.dataTable.dataTable("setAditionalOffset", offsetHeight);
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
                    <div class="control col-xs-3 with-next-col fix-input-xs">
                        <input type="tel" data-prop="cd_kaisha" maxlength="4" class="fixed limit-input-digit" style="ime-mode: disabled;" />
                        <label data-prop="cd_kaisha_validate" style="display: none;"></label>
                        <select data-prop="nm_kaisha" class="floated"></select>
                    </div>
                    <div class="control col-xs-1">
                    </div>
                    <div class="control-label col-xs-1">
                        <label data-prop="label">工場<span class="red-color">*</span></label>
                    </div>
                    <div class="control col-xs-3 with-next-col fix-input-xs ">
                        <input type="tel" data-prop="cd_kojyo" maxlength="4" class="fixed limit-input-digit error" style="ime-mode: disabled;" />
                        <label data-prop="cd_kojyo_validate" style="display: none;"></label>
                        <select data-prop="nm_kojyo" class="floated"></select>
                    </div>
                    <div class="control with-next-col col-xs-3">
                    </div>
                </div>
                <div class="row">
                    <%--data-prop修正必要--%>
                    <div class="control-label col-xs-1">
                        <label>分類</label>
                    </div>
                    <div class="control col-xs-4 joken_genryo">
                        <select data-prop="kbn_hin" style="width: 100px;"></select>
                        <label data-prop="cd_bunrui_validate" style="display: none;"></label>
                        <input type="tel" data-prop="cd_bunrui" maxlength="2" class="limit-input-digit" style="ime-mode: disabled; width: 60px;" />
                        <button id="709_Dialog" type="button" class="btn btn-xs btn-info joken_genryo btn-genryo">検索</button>
                        <label data-prop="nm_bunrui" style="position: absolute;"></label>
                    </div>
                    <%--<div class="control col-xs-2">
                        <button id="709_Dialog" type="button" class="btn btn-sm btn-info">検索</button>
                        <label data-prop="nm_bunrui" style="position:absolute;"></label>
                    </div>--%>
                    <div class="control-label col-xs-1">
                        <label>規格書No<%--<span class="red-color">*</span>--%></label>
                    </div>
                    <div class="control col-xs-3 fix-input-xs">
                        <input type="tel" data-prop="no_kikaku1" style="width: 13%; ime-mode: disabled" class="limit-input-digit" maxlength="4" />
                        -
                        <input type="tel" data-prop="no_kikaku2" style="width: 15%; ime-mode: disabled" class="limit-input-digit" maxlength="6" />
                        -
                        <input type="tel" data-prop="no_kikaku3" style="width: 15%; ime-mode: disabled" class="limit-input-digit" maxlength="5" />
                    </div>
                    <div class="control col-xs-1"></div>
                    <div class="control col-xs-2">
                        <label>
                            <input type="checkbox" class="joken_haigo" data-prop="check_right1" style="vertical-align: middle;" />
                            <i class="joken_haigo">廃止区も含む</i>
                        </label>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1 joken_haigo">
                        <label class="joken_haigo">原料コード指定</label>
                    </div>
                    <div class="control col-xs-1 joken_haigo">
                        <label data-prop="cd_hin_validate" style="display: none;"></label>
                        <input type="tel" class="limit-input-digit joken_haigo" data-prop="cd_hin" />
                    </div>
                    <div class="control col-xs-3 joken_haigo">
                        <label>
                            <input type="checkbox" class="joken_haigo" data-prop="check_left" style="vertical-align: middle;" />
                            <i class="joken_haigo">同等品を含む</i>
                        </label>
                    </div>
                    <div class="control-label col-xs-1 joken_haigo">
                        <label class="joken_haigo">規格書商品名</label>
                    </div>
                    <div class="control col-xs-3 joken_haigo">
                        <input type="text" data-prop="nm_kikaku" style="width: 200px;" class="joken_haigo" />
                    </div>
                    <div class="control col-xs-1 joken_haigo">
                    </div>
                    <div class="control col-xs-2 joken_haigo">
                        <label>
                            <input type="checkbox" class="joken_haigo" data-prop="check_right2" style="vertical-align: middle;" />
                            <i class="joken_haigo">未使用を含む</i>
                        </label>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1 joken_haigo">
                        <label class="joken_haigo">原料名／コード</label>
                    </div>
                    <div class="control col-xs-4 joken_haigo">
                        <input type="text" data-prop="nm_hin" style="width: 200px;" class="joken_haigo" />
                    </div>
                    <div class="control-label col-xs-1 joken_haigo">
                        <label class="joken_haigo">販売社名</label>
                    </div>
                    <div class="control col-xs-3 joken_haigo">
                        <input type="text" data-prop="nm_hanbai" class="joken_haigo" style="width: 200px;" />
                    </div>
                    <div class="control col-xs-1 joken_haigo">
                    </div>
                    <div class="control col-xs-2 joken_haigo">
                        <label>
                            <input type="checkbox" class="joken_haigo" data-prop="check_right3" style="vertical-align: middle;" />
                            <i class="joken_haigo">特殊原料</i>
                        </label>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1 joken_haigo">
                        <label class="joken_haigo">物質名</label>
                    </div>
                    <div class="control col-xs-4 joken_haigo">
                        <input type="text" data-prop="nm_bussitsu" style="width: 200px" class="joken_haigo" />
                    </div>
                    <div class="control-label col-xs-1 joken_haigo">
                        <label class="joken_haigo">製造社名</label>
                    </div>
                    <div class="control col-xs-3 joken_haigo">
                        <input type="text" data-prop="nm_seizo" class="joken_haigo" style="width: 200px;" />

                    </div>
                    <div class="control col-xs-1 ">
                        <label style="float: left; margin-left: 40%;">ソート条件</label>
                    </div>
                    <div class="control col-xs-2 ">
                        <select style="width: 150px;" data-prop="sort">
                            <option value="0">工場コード</option>
                            <option value="1">原料コード</option>
                            <option value="2">原料名</option>
                        </select>
                    </div>
                </div>
                <div class="header-command">
                    <!--TODO: 検索を定義するHTMLをここに記述します。-->
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

                <span class="data-count">
                    <span data-prop="data_count"></span>
                    <span>/</span>
                    <span data-prop="data_count_total"></span>
                </span>
            </div>
            <table class="datatable fix-table ellipsis" style="table-layout: fixed;">
                <!--TODO: 明細一覧のヘッダーを定義するHTMLをここに記述します。-->
                <thead>
                    <tr>
                        <th style="width: 10px;"></th>
                        <th style="width: 40px;">詳細</th>
                        <th data-prop="th_cd_kojyo" style="width: 70px;">工場コード</th>
                        <th data-prop="th_nm_busho" style="width: 250px;">工場名</th>
                        <th style="width: 100px;">原料コード</th>
                        <th style="width: 250px;">原料名</th>
                        <th style="width: 200px;">原料名略称</th>
                        <th style="width: 150px;">荷姿</th>
                        <th style="width: 100px;">分類</th>
                        <th style="width: 70px;">集計コード</th>
                        <th style="width: 250px;">物質名</th>
                        <th style="width: 120px;">規格書No</th>
                        <th style="width: 250px;">規格書商品名</th>
                        <th style="width: 250px;">販売社名</th>
                        <th style="width: 250px;">製造社名</th>
                        <th style="width: 120px;">品位</th>
                        <th style="width: 70px;">登録日</th>
                        <th data-prop="th_kbn_haishi" style="width: 50px; width: 50px;">廃止区</th>
                        <th style="width: 50px;">未使用</th>
                    </tr>
                </thead>
                <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->
                <tbody class="item-tmpl" style="cursor: default; display: none;">
                    <tr>
                        <td>
                            <span class="select-tab unselected"></span>
                        </td>
                        <td class="center">
                            <button id="708_Dialog" class="edit-select btn btn-info btn-xs">詳細</button>
                        </td>
                        <td>
                            <label data-prop="cd_kojyo"></label>
                        </td>
                        <td>
                            <label data-prop="nm_busho"></label>
                        </td>
                        <td>
                            <label data-prop="cd_hin"></label>
                        </td>
                        <td>
                            <label data-prop="nm_hin"></label>
                        </td>
                        <td>
                            <label data-prop="nm_hin_r"></label>
                        </td>
                        <td>
                            <label data-prop="nisugata_hyoji"></label>
                        </td>
                        <td>
                            <label data-prop="nm_bunrui"></label>
                        </td>
                        <td>
                            <label data-prop="cd_shukei"></label>
                        </td>
                        <td>
                            <label data-prop="nm_bussitsu"></label>
                        </td>
                        <td>
                            <label data-prop="no_kikaku"></label>
                        </td>
                        <td>
                            <label data-prop="nm_kikaku"></label>
                        </td>
                        <td>
                            <label data-prop="nm_hanbai"></label>
                        </td>
                        <td>
                            <label data-prop="nm_seizo"></label>
                        </td>
                        <td>
                            <label data-prop="nm_hini"></label>
                        </td>
                        <td>
                            <label data-prop="dt_toroku"></label>
                        </td>
                        <td class="center">
                            <input type="checkbox" data-prop="kbn_haishi" disabled />
                        </td>
                        <td class="center">
                            <input type="checkbox" data-prop="flg_mishiyo" disabled />
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
        <button type="button" id="csv-download" class="btn btn-sm btn-primary">CSV</button>
    </div>
    <div class="command">
        <!--TODO: コマンドボタン（右寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" id="addItem" class="btn btn-sm btn-primary" style="display: none;">保存</button>
        <button type="button" id="editItem" class="btn btn-sm btn-primary" disabled="disabled" style="display: none;">保存</button>
        <button type="button" id="deleteItem" class="btn btn-sm btn-default" style="display: none;">削除</button>

    </div>

</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>
