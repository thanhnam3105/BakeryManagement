<%@ Page Title="210_" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="210_CodeFurikae.aspx.cs" Inherits="Tos.Web.Pages.CodeFurikae" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【HeaderDetail(Ver1.7)】 Template--%>

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

        input[type="radio"], input[type="checkbox"] {
            vertical-align: middle !important;
        }

        .btn-next-search {
            width: 200px;
        }

        .isHide {
            display: none!important;
        }

        .color-seiho-base {
            background-color:#ebebe4!important;
        }

    </style>

    <script type="text/javascript">

        /// <reference path="../../Scripts/jquery-1.10.1.js" />

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("_210_CodeFurikae", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: 3999999999,// GET ALL //App.settings.base.maxInputDataCount,   // TODO:取得するデータ数を指定します。
                filter: ""
            },
            values: {
                isChangeRunning: {},
                m_kirikae: null,
                is_kirikae_hyoji_title: "表示用コード振替",
                is_kirikae_foodprocs_title: "コード振替",
                su_code_standard: 0,
                cd_kaisha: "",
                cd_kojyo: "",
                kbn_hin_old: "",
                kbn_hin_new: "",
                dataCount: 0,
                hijyu_old: null,
                hijyu_new: null,
                cd_tani_hin_old: null,
                cd_tani_hin_new: null,
                kbn_hin_1: 1,
                kbn_hin_3: 3,
                kbn_hin_4: 4,
                kbn_hin_5: 5,
                kbn_hin_6: 6,
                kbn_jyotai_new: null,
                nm_hin_new: null
            },
            urls: {
                hinmeiKaihatsuDialog: "Dialogs/710_HinmeiKaihatsu_Dialog.aspx",
                save: "../api/CodeFurikae",
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
            },
            header: {
                options: {},
                values: {},
                urls: {
                    vw_kaisha_kojyo: "../Services/ShisaQuickService.svc/vw_kaisha_kojyo?$filter=cd_kaisha eq {0}{1}&$orderby=cd_kaisha,cd_kojyo",
                    ma_kaisha: "../Services/ShisaQuickService.svc/ma_kaisha?{0}&$orderby=cd_kaisha",
                    shokuba: "../api/CodeFurikae/GetShokuba",
                    hinmeikensaku: "Dialogs/Hinmei_Kensaku.aspx",
                    getHinData: "../api/CodeFurikae/GetHinData",
                    checkHinCode: "../api/CodeFurikae/CheckHinCode",
                    search: "../api/CodeFurikae",
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
                            if (!(state && state.noCheck)) {
                                row.element.find("tr").removeClass("has-error");
                            }
                        }
                    });
                } else {
                    if (!(state && state.noCheck)) {
                        page.setColValidStyle(item.element);
                    }
                }
                if (!(state && state.noCheck)) {
                    App.ui.page.notifyAlert.remove(item.element);
                }
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
                            if (!(state && state.noCheck)) {
                                row.element.find("tr").addClass("has-error");
                            }
                        }
                    });
                } else {
                    if (!(state && state.noCheck)) {
                        page.setColInvalidStyle(item.element);
                    }
                }

                if (state && state.suppressMessage) {
                    continue;
                }
                if (!(state && state.noCheck)) {
                    App.ui.page.notifyAlert.message(item.message, item.element).show();
                }
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

                    if ((page.header.element.findP("hin_mode").prop("checked") || page.header.element.findP("name_hin_mode").prop("checked")) && page.values.dataCount == 0) {
                        App.ui.page.notifyAlert.message(App.messages.app.AP0084, "checkSave").show();
                        return;
                    }

                    if (page.header.element.findP("hin_mode").prop("checked") && App.isUndefOrNullOrStrEmpty(page.header.element.findP("cd_hin_new").val())) {
                        App.ui.page.notifyAlert.message(App.messages.app.AP0084, "checkSave").show();
                        return;
                    }

                    if (page.header.element.findP("hin_mode").prop("checked") && page.values.hijyu_old != page.values.hijyu_new) {
                        App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0126, { name: "比重" }), "checkSave").show();
                        return;
                    }

                    if (page.header.element.findP("hin_mode").prop("checked") && page.values.cd_tani_hin_old != page.values.cd_tani_hin_new) {
                        App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0126, { name: "使用単位" }), "checkSave").show();
                        return;
                    }

                    if (page.header.element.findP("flg_genryo").prop("checked") && page.values.kbn_hin_new != page.values.kbn_hin_1
                        && !page.header.element.findP("haigo_mode").prop("checked")) {
                        App.ui.page.notifyAlert.message(App.messages.app.AP0128, "checkSave").show();
                        return;
                    }

                    if ((page.values.kbn_hin_old == page.values.kbn_hin_1
                        || page.values.kbn_hin_old == page.values.kbn_hin_5
                        || page.values.kbn_hin_old == page.values.kbn_hin_6)
                        &&
                        (page.values.kbn_hin_new != page.values.kbn_hin_1
                        && page.values.kbn_hin_new != page.values.kbn_hin_5
                        && page.values.kbn_hin_new != page.values.kbn_hin_6)
                        && !page.header.element.findP("haigo_mode").prop("checked")) {
                        App.ui.page.notifyAlert.message(App.messages.app.AP0128, "checkSave").show();
                        return;
                    }

                    if (page.values.kbn_hin_old == page.values.kbn_hin_4
                        && (page.values.kbn_hin_new != page.values.kbn_hin_1
                        && page.values.kbn_hin_new != page.values.kbn_hin_4
                        && page.values.kbn_hin_new != page.values.kbn_hin_5
                        && page.values.kbn_hin_new != page.values.kbn_hin_6)
                        && !page.header.element.findP("haigo_mode").prop("checked")) {
                        App.ui.page.notifyAlert.message(App.messages.app.AP0128, "checkSave").show();
                        return;
                    }

                    var checkedRow = page.detail.element.find("[data-prop='flg_select']").filter(function () {
                        return !this.disabled && this.checked;
                    });

                    if (!page.header.element.findP("haigo_mode").prop("checked") && checkedRow.length == 0) {
                        App.ui.page.notifyAlert.message(App.messages.app.AP0127, "checkSave").show();
                        return;
                    }

                    var options = {
                        text: App.messages.app.AP0004
                    };

                    page.dialogs.confirmDialog.confirm(options)
                        .then(function () {
                            var haigoChange = page.detail.getDataSave();
                            var parameterSave = page.detail.getParaSave();

                            parameterSave.Detail = {
                                updated: haigoChange
                            };

                            //TODO: データの保存処理をここに記述します。
                            return $.ajax(App.ajax.webapi.post(page.urls.save, parameterSave))
                                .then(function (result) {

                                    //TODO: データの保存成功時の処理をここに記述します。

                                    //最後に再度データを取得しなおします。
                                    if (parameterSave.modeBind == 1) {
                                        var elementNew = page.header.element.findP("cd_hin_new"),
                                            elementOld = page.header.element.findP("cd_hin_old");
                                        // 保持検索データの消去
                                        page.detail.searchData = undefined;
                                        $("#nextsearch").hide();
                                        elementOld.val(elementNew.val()).change();
                                        elementNew.val("");
                                    }
                                    return App.async.all([page.header.search(false)]);
                                }).then(function () {
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
         * Get data save
         */
        page.detail.getDataSave = function () {
            var checkbox = page.detail.element.find(":input[data-prop='flg_select']:checked");
            return $.map(checkbox, function (item) {
                var element = $(item),
                    tbody = element.closest("tbody"),
                    id = tbody.attr("data-key"),
                    data = page.detail.data.entry(id);

                return data;
            });
        };

        /**
         * Get parameter save。
         */
        page.detail.getParaSave = function () {
            var criteria = page.header.element.form().data(),
                element = page.header.element,
                filters = {};

            /* TODO: 検索条件のフィルターを定義してください。*/
            filters.m_kirikae = page.values.m_kirikae;

            if (!App.isUndefOrNull(criteria.nm_kaisha) && criteria.nm_kaisha > 0) {
                filters.cd_kaisha = criteria.nm_kaisha;
            }

            if (!App.isUndefOrNull(criteria.nm_kojyo) && criteria.nm_kojyo > 0) {
                filters.cd_kojyo = criteria.nm_kojyo;
            }

            if (!App.isUndefOrNull(criteria.cd_hin_old) && criteria.cd_hin_old > 0) {
                filters.cd_hin_old = criteria.cd_hin_old;
            }

            if (!App.isUndefOrNull(criteria.cd_hin_new) && criteria.cd_hin_new > 0) {
                filters.cd_hin_new = criteria.cd_hin_new;
            }

            if (element.findP("flg_seihin").prop("checked")) {
                filters.flg_seihin = 1;
            }

            if (element.findP("flg_genryo").prop("checked")) {
                filters.flg_genryo = 1;
            }

            if (element.findP("flg_haigo").prop("checked")) {
                filters.flg_haigo = 1;
            }

            if (element.findP("flg_genzai").prop("checked")) {
                filters.flg_genzai = 1;
            }

            if (element.findP("flg_mishiyo").prop("checked")) {
                filters.flg_mishiyo = 1;
            }

            if (element.findP("haigo_mode").prop("checked")) {
                filters.modeBind = 1;
            }

            if (element.findP("hin_mode").prop("checked")) {
                filters.modeBind = 2;
            }

            if (element.findP("name_hin_mode").prop("checked")) {
                filters.modeBind = 3;
            }

            filters.kbn_hin_old = page.values.kbn_hin_old;
            filters.kbn_hin_new = page.values.kbn_hin_new;
            
            if (!App.isUndefOrNull(criteria.nm_hin_new)) {
                filters.nm_hin_new = criteria.nm_hin_new;
            }

            if (filters.modeBind == 2) {
                filters.nm_hin_new = page.values.nm_hin_new;
            }
            if (!App.isUndefOrNull(criteria.nm_shokuba) && criteria.nm_shokuba > 0) {
                filters.cd_shokuba = criteria.nm_shokuba;
            }
            filters.kbn_jyotai_new = page.values.kbn_jyotai_new;
            filters.qty_nisugata_new = page.values.qty_nisugata_new;

            return filters;
        }

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

            if (!App.isUndefOrNullOrStrEmpty(App.uri.queryStrings.M_kirikae) || !App.isUndefOrNullOrStrEmpty(App.uri.queryStrings.m_kirikae)) {
                page.values.m_kirikae = App.uri.queryStrings.M_kirikae;
                if (!App.isUndefOrNullOrStrEmpty(App.uri.queryStrings.m_kirikae)) {
                    page.values.m_kirikae = App.uri.queryStrings.m_kirikae;
                }
                if (page.values.m_kirikae == App.settings.app.m_kirikae.hyoji) {
                    $(".page-title").text(page.values.is_kirikae_hyoji_title);
                    $(".wrap, .footer").addClass("theme-pink");
                } else if (page.values.m_kirikae == App.settings.app.m_kirikae.foodprocs) {
                    $(".page-title").text(page.values.is_kirikae_foodprocs_title);
                } else {
                    App.ui.loading.close();
                    return;
                }
                document.title = $(".page-title").text() + document.title;
            }

            page.initializeControl();
            page.initializeControlEvent();

            page.header.initialize();
            page.detail.initialize();

            page.detail.element.find(".hin_hinmei").hide().change();


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
            var element = page.header.element;

            //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
            return page.kaishaKojoJotai(page.header.urls.ma_kaisha, "nm_kaisha", "cd_kaisha", "nm_kaisha", "", false).then(function () {
                var cd_kaisha = element.findP("nm_kaisha").val();
                element.findP("nm_kaisha").val(App.ui.page.user.cd_kaisha);
                cd_kaisha = App.ui.page.user.cd_kaisha;
                page.values.cd_kaisha = cd_kaisha;
                page.values.su_code_standard = App.ui.page.user.su_code_standard + 1;
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
                    }).then(function () {
                        page.header.loadModeRadio("haigo_mode");
                        page.header.options.validations.cd_hin_old.rules.maxlength = page.values.su_code_standard;
                        page.header.options.validations.cd_hin_new.rules.maxlength = page.values.su_code_standard;
                        element.findP("cd_hin_old").attr("maxlength", page.values.su_code_standard);
                        element.findP("cd_hin_new").attr("maxlength", page.values.su_code_standard);
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
         * Load combobox shokuba。
         */
        page.header.loadShokuba = function (cd_kaisha, cd_kojyo) {
            var element = page.header.element;

            //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
            return $.ajax(App.ajax.webapi.get(page.header.urls.shokuba, { cd_kaisha: cd_kaisha, cd_kojyo: cd_kojyo, cd_shokuba: null })).then(function (result) {

                var nm_shokuba = element.findP("nm_shokuba");
                nm_shokuba.children().remove();
                App.ui.appendOptions(
                    nm_shokuba,
                    "cd_shokuba",
                    "nm_shokuba",
                    result,
                    true
                );
            });
            return App.async.success();
        }

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
        * Load combobox 品区分。
        */
        page.loadApi = function (urlPara, dataprop, nameProp, cd_kaisha, cd_kojyo, blank) {
            var element = page.header.element;

            return $.ajax(App.ajax.webapi.get(urlPara, { cd_kaisha: cd_kaisha, cd_kojyo: cd_kojyo })).then(function (result) {
                var elementprop = element.findP(dataprop);
                elementprop.children().remove();
                App.ui.appendOptions(
                    elementprop,
                    dataprop,
                    nameProp,
                    result,
                    blank
                );
            });
        }

        /**
         * 共有ダイアログのロード処理を実行します。
         */
        page.loadDialogs = function () {

            //TODO: 共有ダイアログデータを取得し、画面にバインドする処理を記述します。
            return App.async.all({
                confirmDialog: $.get(page.urls.confirmDialog),
                hinmeiKaihatsuDialog: $.get(page.urls.hinmeiKaihatsuDialog),
            }).then(function (result) {

                $("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;

                // Dialog hinmeiKaihatsu
                $("#dialog-container").append(result.successes.hinmeiKaihatsuDialog);
                page.dialogs.hinmeiKaihatsuDialog = _710_HinmeiKaihatsu_Dialog;
                page.dialogs.hinmeiKaihatsuDialog.param.no_gamen = App.settings.app.no_gamen.code_furikae;
                page.dialogs.hinmeiKaihatsuDialog.initialize();
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
            cd_shokuba: {
                rules: {
                    //digits: true,
                    alphanum: true,
                    maxlength: 2,
                    existsCode: function (value, opts, state, done) {
                        var element = page.header.element;
                        var nm_shokuba = element.findP("nm_shokuba").val();
                        var cd_shokuba = element.findP("cd_shokuba").val();

                        if (App.isUndefOrNullOrStrEmpty(cd_shokuba)) {
                            return done(true);
                        }

                        return done(nm_shokuba != "" && nm_shokuba != null);
                    }
                },
                options: {
                    name: "職場（コード）"
                },
                messages: {
                    //digits: App.messages.base.digits,
                    alphanum: App.messages.base.alphanum,
                    maxlength: App.messages.base.maxlength,
                    existsCode: App.messages.app.AP0010
                }
            },
            cd_hin_old: {
                rules: {
                    required: true,
                    digits: true,
                    maxlength: 7,
                    existsCode: function (value, opts, state, done) {
                        var element = page.header.element;
                        if (App.isUndefOrNullOrStrEmpty(value)) {
                            return done(true);
                        }
                        var nm_hin_old = element.findP("nm_hin_old").text();

                        return done(nm_hin_old != "");
                    }
                },
                options: {
                    name: "元コード"
                },
                messages: {
                    required: App.messages.base.required,
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength,
                    existsCode: App.messages.app.AP0010
                }
            },
            cd_hin_new: {
                rules: {
                    required: true,
                    digits: true,
                    maxlength: 7,
                    existsCode: function (value, opts, state, done) {
                        var element = page.header.element;
                        if (App.isUndefOrNullOrStrEmpty(value)) {
                            return done(true);
                        }
                        if (element.findP("hin_mode").prop("checked")) {
                            var nm_hin_new = element.findP("nm_hin_new").text();

                            return done(nm_hin_new != "");
                        }
                        return done(true);
                    },
                    checkNotExists: function (value, opts, state, done) {
                        var element = page.header.element;

                        if (App.isUndefOrNullOrStrEmpty(value)) {
                            return done(true);
                        }

                        if (element.findP("haigo_mode").prop("checked")) {
                            var cd_kaisha = element.findP("nm_kaisha").val();
                            var cd_kojyo = element.findP("nm_kojyo").val();

                            var parameter = {};
                            parameter.cd_kaisha = cd_kaisha;
                            parameter.cd_kojyo = cd_kojyo;
                            parameter.su_code_standard = (page.values.su_code_standard - 1);
                            parameter.cd_hin_new = value;
                            parameter.m_kirikae = page.values.m_kirikae;

                            $.ajax(App.ajax.webapi.get(page.header.urls.checkHinCode, parameter)).then(function (result) {
                                page.header.options.validations.cd_hin_new.messages.checkNotExists = App.str.format(App.messages.app.AP0058, "配合", (page.values.m_kirikae == App.settings.app.m_kirikae.hyoji ? "表示用配合" : "配合"));
                                return done(result);
                            });
                        } else {
                            return done(true);
                        } 
                    }
                },
                options: {
                    name: "新コード"
                },
                messages: {
                    required: App.messages.base.required,
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength,
                    existsCode: App.messages.app.AP0010,
                    checkNotExists: App.str.format(App.messages.app.AP0058, "配合", (page.values.m_kirikae == App.settings.app.m_kirikae.hyoji? "表示用配合" : "配合"))
                }
            },
            nm_hin_new: {
                rules: {
                    requiredCustom: function (value, opts, state, done) {
                        var element = page.header.element;
                        if (element.findP("name_hin_mode").prop("checked")) {
                            if (App.isUndefOrNullOrStrEmpty(value)) {
                                return done(false);
                            }
                        }

                        return done(true);
                    },
                    maxbytelength: 60
                },
                options: {
                    name: "名称",
                    byte: 30
                },
                messages: {
                    requiredCustom: App.messages.base.required,
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
            element.on("click", ".hin-old", page.header.showHinmeiOldDialog);
            element.on("click", ".hin-new", page.header.showHinmeiNewDialog);

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
                    filter: page.detail.validationFilter
                };

            //App.ui.page.notifyInfo.clear();
            page.values.isChangeRunning[property] = true;

            if (property == "cd_hin_old" || property == "cd_hin_new") {
                if (!isNaN(Number(data[property]))) {
                    var wrap = page.addZeroList(page.values.su_code_standard);
                    target.val(page.addZero(data[property], wrap));
                }
            }

            if ($("#nextsearch").hasClass("show-search") && (property == "cd_kaisha" || property == "nm_kaisha" || property == "cd_kojyo" || property == "nm_kojyo" || property == "cd_hin_old"
                || property == "cd_shokuba" || property == "nm_shokuba" || property == "flg_seihin" || property == "flg_genzai" || property == "flg_genryo" || property == "flg_mishiyo")) {
                $("#nextsearch").removeClass("show-search").hide();
                App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
            }
            else if (page.detail.searchData && (property == "cd_kaisha" || property == "nm_kaisha" || property == "cd_kojyo" || property == "nm_kojyo" || property == "cd_hin_old"
                || property == "cd_shokuba" || property == "nm_shokuba" || property == "flg_seihin" || property == "flg_genzai" || property == "flg_genryo" || property == "flg_mishiyo")) {
                // 保持検索データの消去
                page.detail.searchData = undefined;
                App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
            }

            App.ui.page.notifyAlert.remove("checkSave");
            if (property == "haigo_mode" || property == "hin_mode" || property == "name_hin_mode") {
                page.setColValidStyle(element.findP("nm_hin_new"));
                App.ui.page.notifyAlert.remove(element.findP("nm_hin_new"));
                page.header.loadModeRadio(property);
                page.header.validator.validate(options);
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
                    if (!App.isUndefOrNullOrStrEmpty(su_code_standard)) {
                        page.values.su_code_standard = parseInt(su_code_standard) + 1;
                    }
                    if (cd_kaisha != "" && App.isNumeric(cd_kaisha) && App.ui.page.user.flg_kojyokan_sansyo) {
                        cd_kaisha = parseInt(cd_kaisha);
                        page.values.isChangeRunning["changeKaisha"] = true;
                        page.kaishaKojoJotai(page.header.urls.vw_kaisha_kojyo, "nm_kojyo", "cd_kojyo", "nm_kojyo", cd_kaisha, false).then(function () {
                            var cd_kojyo = element.findP("nm_kojyo").val();
                            if (!App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                                element.findP("cd_kojyo").val(page.addZero(cd_kojyo, "0000"));
                                element.findP("cd_kojyo_validate").text(cd_kojyo);
                            }
                            page.header.validator.validate({
                                targets: element.find("[data-prop='cd_kojyo'], [data-prop='nm_kojyo']")
                            });
                        }).always(function () {
                            delete page.values.isChangeRunning["changeKaisha"];
                        });
                    }
                    page.header.validator.validate({
                        targets: element.find("[data-prop='cd_kaisha'], [data-prop='nm_kaisha']")
                    });
                }

                if (!element.findP("haigo_mode").prop("checked")) {
                    if (!App.isUndefOrNullOrStrEmpty(element.findP("cd_hin_old").val())) {
                        element.findP("cd_hin_old").change();
                    }
                }
                if (element.findP("hin_mode").prop("checked")) {
                    if (!App.isUndefOrNullOrStrEmpty(element.findP("cd_hin_new").val())) {
                        element.findP("cd_hin_new").change();
                    }
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

            if (property == "cd_kaisha" || property == "nm_kaisha" || property == "cd_kojyo" || property == "nm_kojyo") {
                element.findP("nm_shokuba").children().remove();
            }

            if (property == "cd_hin_old") {
                var cd_hin_old = target.val();
                var cd_kaisha = element.findP("nm_kaisha").val();
                var cd_kojyo = element.findP("nm_kojyo").val();
                var zeroList = "";
                if (element.findP("haigo_mode").prop("checked")) {
                    zeroList = page.addZeroList(page.values.su_code_standard);
                    cd_hin_old = page.addZero(cd_hin_old, zeroList);
                }

                if (!App.isUndefOrNullOrStrEmpty(cd_hin_old) && !App.isUndefOrNullOrStrEmpty(cd_kaisha) && !App.isUndefOrNullOrStrEmpty(cd_kojyo) && App.isNumeric(cd_hin_old)) {
                    page.values.isChangeRunning["changeHin"] = true;
                    page.header.getHinData(cd_hin_old, null, cd_kaisha, cd_kojyo, true);
                }

                if (App.isUndefOrNullOrStrEmpty(cd_hin_old) || !App.isNumeric(cd_hin_old)) {
                    page.header.bindOld(null, null);
                }

                if (element.findP("name_hin_mode").prop("checked") && (App.isUndefOrNullOrStrEmpty(cd_hin_old) || !App.isNumeric(cd_hin_old))) {
                    page.header.bindNew(null, null);
                    page.header.clearNewData();
                }
            }

            if (property == "cd_hin_new") {
                var cd_hin_new = target.val();
                var cd_kaisha = element.findP("nm_kaisha").val();
                var cd_kojyo = element.findP("nm_kojyo").val();

                if (App.isUndefOrNullOrStrEmpty(cd_hin_new) || !App.isNumeric(cd_hin_new)) {
                    page.header.bindNew(null, null);
                }

                if (element.findP("hin_mode").prop("checked")) {
                    if (!App.isUndefOrNullOrStrEmpty(cd_hin_new) && !App.isUndefOrNullOrStrEmpty(cd_kaisha) && !App.isUndefOrNullOrStrEmpty(cd_kojyo) && App.isNumeric(cd_hin_new)) {
                        page.values.isChangeRunning["changeHin"] = true;
                        page.header.getHinData(cd_hin_new, null, cd_kaisha, cd_kojyo, false);
                    }
                } else {
                    //Check exists
                    if (!App.isUndefOrNullOrStrEmpty(cd_hin_new)) {
                        var zeroList = page.addZeroList(page.values.su_code_standard);
                        element.findP("cd_hin_new").val(page.addZero(zeroList + cd_hin_new, zeroList));
                    }
                }
            }

            if(property == "cd_shokuba") {
                var cd_shokuba = target.val();
                var cd_kaisha = element.findP("nm_kaisha").val();
                var cd_kojyo = element.findP("nm_kojyo").val();

                if (App.isUndefOrNullOrStrEmpty(cd_shokuba)) {
                    element.findP("nm_shokuba").val("");
                } else {
                    //if (App.isNumeric(cd_shokuba) && !App.isUndefOrNullOrStrEmpty(cd_kaisha) && !App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                    if (!App.isUndefOrNullOrStrEmpty(cd_kaisha) && !App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                        cd_shokuba = ("00" + cd_shokuba).slice(-2);
                        $.ajax(App.ajax.webapi.get(page.header.urls.shokuba, { cd_kaisha: cd_kaisha, cd_kojyo: cd_kojyo, cd_shokuba: cd_shokuba })).then(function (result) {
                            if (result.length > 0) {
                                element.findP("cd_shokuba").val(page.addZero(result[0].cd_shokuba, "00"));
                                element.findP("nm_shokuba").val(page.addZero(result[0].cd_shokuba, "00"));
                            } else {
                                element.findP("nm_shokuba").val("");
                            }
                        });
                    } else {
                        element.findP("nm_shokuba").val("");
                    }
                }
            }

            if(property == "nm_shokuba") {
                var nm_shokuba = target.val();
                element.findP("cd_shokuba").val(nm_shokuba);
                page.header.validator.validate({ targets: element.findP("cd_shokuba") });
            }

            delete page.values.isChangeRunning[property];

            var sleep = 0;
            var condition = "Object.keys(page.values.isChangeRunning).length == 0";
            App.ui.wait(sleep, condition, 100)
            .then(function () {
                if (!element.findP("haigo_mode").prop("checked")) {
                    if (property == "cd_kaisha" || property == "nm_kaisha" || property == "cd_kojyo" || property == "nm_kojyo" || property == "hin_mode" || property == "name_hin_mode") {
                        var cd_kaisha = element.findP("nm_kaisha").val(),
                            cd_kojyo = element.findP("nm_kojyo").val();
                            
                        if (!App.isUndefOrNullOrStrEmpty(cd_kaisha) && !App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                            page.header.loadShokuba(cd_kaisha, cd_kojyo).then(function () {
                                var cd_shokuba = element.findP("cd_shokuba").val();
                                if (!App.isUndefOrNullOrStrEmpty(cd_shokuba)) {
                                    element.findP("cd_shokuba").change();
                                }
                            });
                        } else {
                            element.findP("cd_shokuba").change();
                        }
                          
                    }
                } else {
                    element.findP("nm_shokuba").children().remove();
                }

                if (property == "cd_kaisha" || property == "nm_kaisha" || property == "cd_kojyo" || property == "nm_kojyo") {
                    if (!App.isUndefOrNullOrStrEmpty(element.findP("cd_hin_old").val())) {
                        element.findP("cd_hin_old").change();
                    }
                    if (element.findP("hin_mode").prop("checked") && !App.isUndefOrNullOrStrEmpty(element.findP("cd_hin_new").val())) {
                        element.findP("cd_hin_new").change();
                    }
                }
                if (property == "cd_kojyo" || property == "nm_kojyo") {
                    page.header.validator.validate({
                        targets: element.find("[data-prop='cd_kojyo'], [data-prop='nm_kojyo']")
                    });
                }

                page.header.validator.validate({ targets: target });
                if (property == "cd_kaisha") {
                    page.header.validator.validate({ targets: element.findP("cd_kojyo") });
                }
                if (property == "cd_hin_old" && element.findP("name_hin_mode").prop("checked")) {
                    page.header.executeValidation(element.findP("nm_hin_new"), options);
                }
            });
        };

        /**
       * 検索処理を定義します。
       */
        page.header.changMaxLengthHin = function (mode, kbn_hin, isOld) {
            var element = page.header.element;
            page.header.options.validations.cd_hin_old.rules.maxlength = page.values.su_code_standard;
            page.header.options.validations.cd_hin_new.rules.maxlength = page.values.su_code_standard;
            element.findP("cd_hin_old").attr("maxlength", page.values.su_code_standard);
            element.findP("cd_hin_new").attr("maxlength", page.values.su_code_standard);
        }
        //page.header.changMaxLengthHin = function (mode, kbn_hin, isOld) {
        //    var element = page.header.element;
        //    if (mode == 1) {
        //        page.header.options.validations.cd_hin_old.rules.maxlength = page.values.su_code_standard;
        //        page.header.options.validations.cd_hin_new.rules.maxlength = page.values.su_code_standard;
        //        element.findP("cd_hin_old").attr("maxlength", page.values.su_code_standard);
        //        element.findP("cd_hin_new").attr("maxlength", page.values.su_code_standard);
        //    } else {
        //        if (kbn_hin == page.values.kbn_hin_1 || kbn_hin == page.values.kbn_hin_6) {
        //            if (isOld) {
        //                page.header.options.validations.cd_hin_old.rules.maxlength = (page.values.su_code_standard - 1);
        //                element.findP("cd_hin_old").attr("maxlength", (page.values.su_code_standard - 1));
        //            } else {
        //                page.header.options.validations.cd_hin_new.rules.maxlength = (page.values.su_code_standard - 1);
        //                element.findP("cd_hin_new").attr("maxlength", (page.values.su_code_standard - 1));
        //            }
        //        } else {
        //            if (isOld) {
        //                page.header.options.validations.cd_hin_old.rules.maxlength = page.values.su_code_standard;
        //                element.findP("cd_hin_old").attr("maxlength", page.values.su_code_standard);
        //            } else {
        //                page.header.options.validations.cd_hin_new.rules.maxlength = page.values.su_code_standard;
        //                element.findP("cd_hin_new").attr("maxlength", page.values.su_code_standard);
        //            }
        //        }
        //    }
        //}

        /**
        * 検索処理を定義します。
        */
        page.header.loadModeRadio = function (property) {
            var element = page.header.element;

            page.values.dataCount = 0;
            page.detail.dataTable.dataTable("clear");
            page.detail.element.findP("data_count").text("");
            page.detail.element.findP("data_count_total").text("");
            element.find("[data-prop='flg_seihin'], [data-prop='flg_genryo'], [data-prop='flg_haigo'], [data-prop='flg_genzai'], [data-prop='flg_mishiyo']").prop("checked", false);
            if (property == "haigo_mode") {
                $(".haigo").show();
                $(".hincode, .hinmei, .hinHeader").hide();
                element.find("[data-prop='cd_shokuba'], [data-prop='nm_shokuba']").prop("disabled", true);
                element.find("[data-prop='flg_seihin'], [data-prop='flg_genryo'], [data-prop='flg_haigo'], [data-prop='flg_genzai'], [data-prop='flg_mishiyo']").prop("disabled", true);
                element.findP("nm_hin_new").replaceWith("<label data-prop='nm_hin_new' class='hinmei_clear' /></label>");
                element.findP("cd_hin_new").prop("disabled", false);
                element.find(".hin-new").prop("disabled", true);
                $("[data-prop='flg_check_all']").prop("disabled", true);
                element.findP("cd_shokuba").val("");
                page.header.validator.validate({ targets: element.findP("cd_shokuba") });
            } else if (property == "hin_mode") {
                $(".hincode, .hinHeader").show();
                $(".haigo, .hinmei").hide();
                element.find("[data-prop='cd_shokuba'], [data-prop='nm_shokuba']").prop("disabled", false);
                element.find("[data-prop='flg_genzai'], [data-prop='flg_mishiyo']").prop("disabled", false);
                element.find("[data-prop='flg_seihin'], [data-prop='flg_genryo'], [data-prop='flg_haigo']").prop("disabled", true);
                element.findP("nm_hin_new").replaceWith("<label data-prop='nm_hin_new' class='hinmei_clear' /></label>");
                element.findP("nm_hin_new").text(element.findP("nm_hin_old").text());
                element.findP("cd_hin_new").prop("disabled", false);
                element.find(".hin-new").prop("disabled", false);
                $("[data-prop='flg_check_all']").prop("checked", false);
                $("[data-prop='flg_check_all']").prop("disabled", false);
            } else if (property == "name_hin_mode") {
                $(".hinmei, .hinHeader").show();
                $(".haigo, .hincode").hide();
                element.findP("nm_hin_new").replaceWith("<input type='text' style='width: 60%;' data-prop='nm_hin_new' class='hinmei_clear' />");
                element.findP("nm_hin_new").val(element.findP("nm_hin_old").text());
                element.findP("cd_hin_new").prop("disabled", true);
                element.find("[data-prop='cd_shokuba'], [data-prop='nm_shokuba']").prop("disabled", false);
                element.find("[data-prop='flg_genzai'], [data-prop='flg_mishiyo']").prop("disabled", false);
                element.find("[data-prop='flg_seihin'], [data-prop='flg_genryo'], [data-prop='flg_haigo']").prop("disabled", true);
                element.find(".hin-new").prop("disabled", true);
                $("[data-prop='flg_check_all']").prop("disabled", false);
                $("[data-prop='flg_check_all']").prop("checked", false);
            }
            page.header.clearNewData();

            if (element.findP("cd_hin_old").val() != "") {
                element.findP("cd_hin_old").change();
            }
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
         * 検索処理を定義します。
         */
        page.header.search = function (isLoading) {

            var deferred = $.Deferred(),
                query,
                options = {
                    filter: page.detail.validationFilter
                };

            page.header.validator.validate(options);
            page.header.validator.validate({ targets: page.header.element.find("[data-prop='cd_kaisha'], [data-prop='cd_kojyo'], [data-prop='cd_hin_old'], [data-prop='cd_shokuba'], [data-prop='cd_hin_new']") }).done(function () {
                page.options.skip = 0;
                page.values.dataCount = 0;
                page.options.filter = page.header.createFilter();

                if (isLoading) {
                    App.ui.loading.show();
                    App.ui.page.notifyAlert.clear();
                }

                $("[data-prop='flg_check_all']").prop("checked", false);

                return $.ajax(App.ajax.webapi.get(page.header.urls.search, page.options.filter))
                .done(function (result) {
                    // パーツ開閉の判断
                    if (page.detail.isClose) {
                        // 検索データの保持
                        page.detail.searchData = result;
                    } else {
                        page.detail.searchData = result;
                        // データバインド
                        page.detail.bind(result, null, result.modeBind);
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
         * 検索条件のフィルターを定義します。
         */
        page.header.createFilter = function () {
            var criteria = page.header.element.form().data(),
                element = page.header.element,
                filters = {};

            /* TODO: 検索条件のフィルターを定義してください。*/
            if (!App.isUndefOrNull(criteria.nm_kaisha) && criteria.nm_kaisha > 0) {
                filters.cd_kaisha = criteria.nm_kaisha;
            }

            if (!App.isUndefOrNull(criteria.nm_kojyo) && criteria.nm_kojyo > 0) {
                filters.cd_kojyo = criteria.nm_kojyo;
            }

            if (!App.isUndefOrNull(criteria.nm_shokuba) && criteria.nm_shokuba != "") {
                filters.cd_shokuba = criteria.nm_shokuba;
            }

            if (!App.isUndefOrNull(criteria.cd_hin_old) && criteria.cd_hin_old > 0) {
                filters.cd_hin_old = criteria.cd_hin_old;
            }

            if (element.findP("flg_seihin").prop("checked")) {
                filters.flg_seihin = 1;
            }

            if (element.findP("flg_genryo").prop("checked")) {
                filters.flg_genryo = 1;
            }

            if (element.findP("flg_haigo").prop("checked")) {
                filters.flg_haigo = 1;
            }

            if (element.findP("flg_genzai").prop("checked")) {
                filters.flg_genzai = 1;
            }

            if (element.findP("flg_mishiyo").prop("checked")) {
                filters.flg_mishiyo = null;
            } else {
                filters.flg_mishiyo = 0;
            }

            if (element.findP("haigo_mode").prop("checked")) {
                filters.modeBind = 1;
            }

            if (element.findP("hin_mode").prop("checked")) {
                filters.modeBind = 2;
            }

            if (element.findP("name_hin_mode").prop("checked")) {
                filters.modeBind = 3;
            }

            filters.kbn_hin_old = page.values.kbn_hin_old;
            filters.m_kirikae = page.values.m_kirikae;
            filters.skip = page.options.skip;
            filters.top = page.options.top;

            return filters;
        };

        //TODO: 以下の page.header の各宣言は画面仕様ごとにことなるため、不要の場合は削除してください。

        /**
         * 画面ヘッダーにある検索ダイアログ起動時の処理を定義します。
         */
        page.header.showHinmeiOldDialog = function () {
            var element = page.header.element,
                cd_kaisha = element.findP("nm_kaisha").val(),
                cd_kojyo = element.findP("nm_kojyo").val();

            if (!App.isUndefOrNullOrStrEmpty(cd_kaisha) && !App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                page.dialogs.hinmeiKaihatsuDialog.param.no_gamen = App.settings.app.no_gamen.code_furikae;
                page.dialogs.hinmeiKaihatsuDialog.param.cd_kaisha = cd_kaisha;
                page.dialogs.hinmeiKaihatsuDialog.param.cd_kojyo = cd_kojyo;
                page.dialogs.hinmeiKaihatsuDialog.param.M_kirikae = page.values.m_kirikae;

                if (element.findP("haigo_mode").prop("checked")) {
                    page.dialogs.hinmeiKaihatsuDialog.param.kbn_hin_search = App.settings.app.kbn_hin_search.haigo;
                } else {
                    page.dialogs.hinmeiKaihatsuDialog.param.kbn_hin_search = App.settings.app.kbn_hin_search.genryo;
                }


                page.dialogs.hinmeiKaihatsuDialog.element.modal("show");

                page.dialogs.hinmeiKaihatsuDialog.dataSelected = function (data) {
                    page.header.getHinData(data.cd_hin, data.kbn_hin, cd_kaisha, cd_kojyo, true);
                    if ($("#nextsearch").hasClass("show-search") ) {
                        $("#nextsearch").removeClass("show-search").hide();
                        App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
                    }
                    else if (page.detail.searchData ) {
                        // 保持検索データの消去
                        page.detail.searchData = undefined;
                        App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
                    }
                    delete page.dialogs.hinmeiKaihatsuDialog.dataSelected;
                }
            }
        };

        /**
         * 画面ヘッダーにある検索ダイアログ起動時の処理を定義します。
         */
        page.header.showHinmeiNewDialog = function () {
            var element = page.header.element,
                cd_kaisha = element.findP("nm_kaisha").val(),
                cd_kojyo = element.findP("nm_kojyo").val();

            if (!App.isUndefOrNullOrStrEmpty(cd_kaisha) && !App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                page.dialogs.hinmeiKaihatsuDialog.param.no_gamen = App.settings.app.no_gamen.code_furikae;
                page.dialogs.hinmeiKaihatsuDialog.param.cd_kaisha = cd_kaisha;
                page.dialogs.hinmeiKaihatsuDialog.param.cd_kojyo = cd_kojyo;
                page.dialogs.hinmeiKaihatsuDialog.param.M_kirikae = page.values.m_kirikae;

                if (element.findP("haigo_mode").prop("checked")) {
                    page.dialogs.hinmeiKaihatsuDialog.param.kbn_hin_search = App.settings.app.kbn_hin_search.haigo;
                } else {
                    page.dialogs.hinmeiKaihatsuDialog.param.kbn_hin_search = App.settings.app.kbn_hin_search.genryo;
                }


                page.dialogs.hinmeiKaihatsuDialog.element.modal("show");

                page.dialogs.hinmeiKaihatsuDialog.dataSelected = function (data) {
                    page.header.getHinData(data.cd_hin, data.kbn_hin, cd_kaisha, cd_kojyo, false);
                    delete page.dialogs.hinmeiKaihatsuDialog.dataSelected;
                }
            }
        };

        /**
        * Get hin data old。
        */
        page.header.getHinData = function (cd_hin, kbn_hin, cd_kaisha, cd_kojyo, isOld) {
            var element = page.header.element;
            App.ui.loading.show();

            var mode;
            if (element.findP("haigo_mode").prop("checked")) {
                mode = 1;
            } else if (element.findP("hin_mode").prop("checked")) {
                mode = 2;
            } else if (element.findP("name_hin_mode").prop("checked")) {
                mode = 3;
            }

            if (App.isUndefOrNullOrStrEmpty(kbn_hin)) {
                kbn_hin = null;
            }

            $.ajax(App.ajax.webapi.get(page.header.urls.getHinData, { cd_kaisha: cd_kaisha, cd_kojyo: cd_kojyo, modeSearch: mode, m_kirikae: page.values.m_kirikae, cd_hin: cd_hin, kbn_hin: kbn_hin })).then(function (result) {
                if (isOld) {
                    page.header.bindOld(result, mode);
                } else {
                    page.header.bindNew(result, mode);
                }
                if (!App.isUndefOrNullOrStrEmpty(result)) {
                    page.header.changMaxLengthHin(mode, result.kbn_hin, isOld);
                } else {
                    page.header.changMaxLengthHin(mode, kbn_hin || 0, isOld);
                }
            }).always(function () {
                if (isOld) {
                    page.header.validator.validate({
                        targets: element.findP("cd_hin_old")
                    });
                } else {
                    page.header.validator.validate({
                        targets: element.findP("cd_hin_new")
                    });
                }
                delete page.values.isChangeRunning["changeHin"];
                App.ui.loading.close();
            });
        }

        /**
       * Bind data old。
       */
        page.header.bindOld = function (data, mode) {
            var element = page.header.element;
            if (!App.isUndefOrNullOrStrEmpty(data)) {
                var zeroList = "";
                if (mode == 1) {
                    zeroList = page.addZeroList(page.values.su_code_standard);
                } else {
                    if (!App.isUndefOrNullOrStrEmpty(data.kbn_hin)) {
                        if (data.kbn_hin == page.values.kbn_hin_1 || data.kbn_hin == page.values.kbn_hin_6) {
                            zeroList = page.addZeroList(page.values.su_code_standard - 1);
                        } else {
                            zeroList = page.addZeroList(page.values.su_code_standard);
                        }
                    }
                }
                page.values.kbn_hin_old = data.kbn_hin;

                element.findP("cd_hin_old").val(page.addZero(zeroList + data.cd_hin, zeroList));
                element.findP("nm_hin_old").text(data.nm_hin);
                element.findP("nm_kbn_old").text(data.nm_kbn_hin);
                var qty_nisugata = App.num.format((data.qty_nisugata == null ? 0 : data.qty_nisugata), "#,0.000");
                var nm_tani = "";
                if (!App.isUndefOrNullOrStrEmpty(data.nm_tani)) {
                    nm_tani = " " + data.nm_tani;
                }
                if (page.values.kbn_hin_old == page.values.kbn_hin_1 || page.values.kbn_hin_old == page.values.kbn_hin_6) {
                    element.findP("qty_nisugata_old").text(qty_nisugata + nm_tani);
                } else {
                    element.findP("qty_nisugata_old").text("");
                }

                if(mode == 1 || mode == 2) {
                    if (!App.isUndefOrNullOrStrEmpty(data.kbn_hin)) {
                        if (data.kbn_hin == page.values.kbn_hin_3 && mode == 1) {
                            element.findP("flg_seihin").prop("disabled", false);
                        } else {
                            element.findP("flg_seihin").prop("checked", false);
                            element.findP("flg_seihin").prop("disabled", true);
                        }
                        if (data.kbn_hin == page.values.kbn_hin_1 && mode == 2) {
                            element.findP("flg_genryo").prop("disabled", false);
                        } else {
                            element.findP("flg_genryo").prop("checked", false);
                            element.findP("flg_genryo").prop("disabled", true);
                        }
                    } else {
                        element.findP("flg_seihin").prop("checked", false);
                        element.findP("flg_genryo").prop("checked", false);
                        element.findP("flg_seihin").prop("disabled", true);
                        element.findP("flg_genryo").prop("disabled", true);
                    }
                }

                if (mode == 2) {
                    page.values.hijyu_old = data.hijyu;
                    page.values.cd_tani_hin_old = data.cd_tani_hin;
                }
            } else {
                element.find("[data-prop='nm_hin_old'], [data-prop='cd_hin_old'], [data-prop='nm_kbn_old'], [data-prop='qty_nisugata_old']").text("");
                element.findP("flg_seihin").prop("checked", false);
                element.findP("flg_genryo").prop("checked", false);
                element.findP("flg_seihin").prop("disabled", true);
                element.findP("flg_genryo").prop("disabled", true);
            }
            if (mode == 3) {
                page.header.bindNew(data, mode);
                if (App.isUndefOrNullOrStrEmpty(data)) {
                    page.header.clearNewData();
                }
            }

            if(mode == 2) {
                if ((page.values.kbn_hin_old == page.values.kbn_hin_1 || page.values.kbn_hin_old == page.values.kbn_hin_6)
                    || (page.values.kbn_hin_new == page.values.kbn_hin_1 || page.values.kbn_hin_new == page.values.kbn_hin_6))
                {
                    element.findP("flg_haigo").prop("disabled", false);
                } else {
                    element.findP("flg_haigo").prop("checked", false);
                    element.findP("flg_haigo").prop("disabled", true);
                }
            } else {
                element.findP("flg_haigo").prop("checked", false);
                element.findP("flg_haigo").prop("disabled", true);
            }
        }

        /**
       * Bind data new。
       */
        page.header.bindNew = function (data, mode) {
            var element = page.header.element;
            if (!App.isUndefOrNullOrStrEmpty(data)) {
                var zeroList = "";
                if (mode == 1) {
                    zeroList = page.addZeroList(page.values.su_code_standard);
                } else {
                    if (!App.isUndefOrNullOrStrEmpty(data.kbn_hin)) {
                        if (data.kbn_hin == page.values.kbn_hin_1 || data.kbn_hin == page.values.kbn_hin_6) {
                            zeroList = page.addZeroList(page.values.su_code_standard - 1);
                        } else {
                            zeroList = page.addZeroList(page.values.su_code_standard);
                        }
                    }
                }
                page.values.kbn_hin_new = data.kbn_hin;
                element.findP("cd_hin_new").val(page.addZero(zeroList + data.cd_hin, zeroList));
                element.findP("nm_hin_new").text(data.nm_hin);
                element.findP("nm_hin_new").val(data.nm_hin);
                element.findP("nm_kbn_new").text(data.nm_kbn_hin);
                var qty_nisugata = App.num.format((data.qty_nisugata == null ? 0 : data.qty_nisugata), "#,0.000");
                var nm_tani = "";
                if (!App.isUndefOrNullOrStrEmpty(data.nm_tani)) {
                    nm_tani = " " + data.nm_tani;
                }
                if (page.values.kbn_hin_new == page.values.kbn_hin_1 || page.values.kbn_hin_new == page.values.kbn_hin_6) {
                    element.findP("qty_nisugata_new").text(qty_nisugata + nm_tani);
                } else {
                    element.findP("qty_nisugata_new").text("");
                }
                if (mode == 2) {
                    page.values.hijyu_new = data.hijyu;
                    page.values.cd_tani_hin_new = data.cd_tani_hin;
                    page.values.kbn_jyotai_new = data.kbn_jyotai;
                    page.values.nm_hin_new = data.nm_hin;
                    page.values.qty_nisugata_new = data.qty_nisugata;
                }
                if (mode == 3) {
                    var options = {
                        filter: page.detail.validationFilter
                    };
                    page.header.validator.validate({ targets: element.find("[data-prop='nm_hin_new']") });
                }
            } else {
                page.values.kbn_hin_new = null;
                page.values.hijyu_new = null;
                page.values.cd_tani_hin_new = null;
                page.values.kbn_jyotai_new = null;
                page.values.nm_hin_new = null;
                page.values.qty_nisugata_new = null;
                element.find("[data-prop='nm_hin_new'], [data-prop='nm_kbn_new'], [data-prop='qty_nisugata_new']").text("");
            }

            if(mode == 2) {
                if ((page.values.kbn_hin_old == page.values.kbn_hin_1 || page.values.kbn_hin_old == page.values.kbn_hin_6)
                    || (page.values.kbn_hin_new == page.values.kbn_hin_1 || page.values.kbn_hin_new == page.values.kbn_hin_6)) {
                    element.findP("flg_haigo").prop("disabled", false);
                } else {
                    element.findP("flg_haigo").prop("checked", false);
                    element.findP("flg_haigo").prop("disabled", true);
                }
            } else {
                element.findP("flg_haigo").prop("checked", false);
                element.findP("flg_haigo").prop("disabled", true);
            }
        }

        /**
        * Clear data new。
        */
        page.header.clearNewData = function () {
            var element = page.header.element;

            element.find("[data-prop='nm_hin_new'], [data-prop='nm_kbn_new'], [data-prop='qty_nisugata_new']").text("");
            element.findP("cd_hin_new").val("");
            element.findP("nm_hin_new").val("");
        }

        /**
        * Add list zero。
        */
        page.addZeroList = function (su_code_standard) {
            var zeroList = "";
            for (var i = 0; i < su_code_standard; i++) {
                zeroList = zeroList + "0";
            }
            return zeroList;
        }

        //TODO-END: 


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
            element.on("click", "#nextsearch", page.detail.nextsearch);
            element.on("change", "[data-prop='flg_check_all']", page.detail.checkedAll);

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
            //appliers: {
            //    qty_haigo_h: function (value, element) {
            //        if (!App.isUndefOrNullOrStrEmpty(value)) {
            //            element.text(App.num.format(value, "#,0"));
            //        }
            //        return true;
            //    }
            //}
        };

        /**
         * 画面明細へのデータバインド処理を行います。
         */
        page.detail.bind = function (data, isNewData, modeBind) {
            var i, l, item, dataSet, dataCount = 0
                , kbn_nmacs_kojyo = null;

            //dataCount = data.Count ? data.Count : 0;
            //data = (data.Items) ? data.Items : data;
            kbn_nmacs_kojyo = data.kbn_nmacs_kojyo;
            if (data.Items1) {
                if (data.Items1.length > 0) {
                    dataCount = data.Count1;
                    data = data.Items1;
                }
            }
            else if (data.Items2) {
                if (data.Items2.length > 0) {
                    dataCount = data.Count2;
                    data = data.Items2;
                }
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
                if (item.flg_seiho_base) {
                    row.findP("flg_select").prop("disabled", true);
                    row.addClass("color-seiho-base");
                }
                if (modeBind == 1) {
                    row.find(".modeBind1").show("modeBind1");
                    row.find(".modeBind1").removeClass("isHide");
                    row.find(".modeBind2").hide("modeBind2");
                } else {
                    row.find(".modeBind1").hide("modeBind1");
                    row.find(".modeBind2").show("modeBind2");
                    row.find(".modeBind2").removeClass("isHide");
                }

                return row;
            }, true);

            if (page.values.m_kirikae == App.settings.app.m_kirikae.foodprocs
                    && modeBind != 1
                    && kbn_nmacs_kojyo == 2) {
                $(".showHideHinWeight").show();
            } else {
                $(".showHideHinWeight").hide();
            } 

            page.options.skip += page.options.top;

            if (dataCount < page.options.skip) {
                page.options.skip = dataCount;
            }

            if (!isNewData || isNewData == null) {
                page.detail.element.findP("data_count").text(page.options.skip);
                page.detail.element.findP("data_count_total").text(dataCount);
            }

            page.values.dataCount = dataCount;

            //if (dataCount > 0) {
            //    $("#save").prop("disabled", false);
            //} else {
            //    $("#save").prop("disabled", true);
            //}
            if (!isNewData || isNewData == null) {
                $("#save").prop("disabled", false);
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

            //TODO: 画面明細へのデータバインド処理をここに記述します。
            offsetHeight = $("#nextsearch").is(":visible") ? $("#nextsearch").addClass("show-search").outerHeight() : 0;
            page.detail.dataTable.dataTable("setAditionalOffset", offsetHeight);

            //バリデーションを実行します。
            page.detail.validateList(true);

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
                page.detail.bind(result, null,result.modeBind);
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            }).always(function () {
                App.ui.loading.close();
            });

        };

        /**
         * Checked all。
         */
        page.detail.checkedAll = function () {
            var element = page.detail.element,
                checkedAll = element.find("[data-prop='flg_check_all']").prop("checked");

            var rowEnabled = element.not(".item-tmpl").find("[data-prop='flg_select']").filter(function () {
                return !this.disabled;
            });

            rowEnabled.prop("checked", checkedAll);
            $(".item-tmpl [data-prop='flg_select']").prop("checked", false);
        }

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
                element = page.detail.element,
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

                //if ($("#save").is(":disabled")) {
                //    $("#save").prop("disabled", false);
                //}
                var rowEnabled = element.find("[data-prop='flg_select']").filter(function () {
                    return !this.disabled && !this.checked;
                });
                if (rowEnabled.length > 1) {
                    element.find("[data-prop='flg_check_all']").prop("checked", false);
                } else {
                    element.find("[data-prop='flg_check_all']").prop("checked", true);
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
         * 画面明細のバリデーションを実行します。
         */
        page.header.executeValidation = function (targets, options) {
            var defaultOptions = {
                targets: targets,
                state: {
                    isGridValidation: true
                }
            },
            execOptions = $.extend(true, {}, defaultOptions, options);

            return page.header.validator.validate(execOptions);
        };

        /**
         * 画面明細のバリデーションフィルターを設定します。（必須チェックを行わない）
         */
        page.detail.validationFilter = function (item, method, state, options) {
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
                if (row.element.hasClass("item-tmpl")) {
                    return;
                }

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
        $(document).ready(function () {
            // ページの初期化処理を呼び出します。
            page.initialize();
        });

    </script>

</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">

    <div class="content-wrap">
        <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
        <div class="header">
            <div class="row theme-color">
                <div class="col-xs-12 control"></div>
            </div>
            <div class="row">
                <div class="control col-xs-2">
                    <label>
                        <input type="radio" name="hyoji" data-prop="haigo_mode" checked="checked" />
                        <i>配合コード変換</i>
                    </label>
                </div>
                <div class="control col-xs-2">
                    <label>
                        <input type="radio" name="hyoji" data-prop="hin_mode" />
                        <i>品コード入替</i>
                    </label>
                </div>
                <div class="control col-xs-2">
                    <label>
                        <input type="radio" name="hyoji" data-prop="name_hin_mode" />
                        <i>品名入替</i>
                    </label>
                </div>
                <div class="control col-xs-6">
                    <label style="white-space: nowrap;"></label>
                </div>
            </div>
            <div class="row">
                <div class="control-label col-xs-1">
                    <label>処理説明</label>
                </div>
                <div class="control col-xs-11">
                    <label class="haigo">指定した配合コードを新しいコードに変換します。（例：配合Aのコードを2000001を6000001に変換します。）</label>
                    <label class="hincode" style="display: none;">配合レシピ中のコードを別のコードに入れ替えます。（例：000001原料Aを000002原料Bに入れ替えます。）</label>
                    <label class="hinmei" style="display: none;">配合レシピ中の指定コードの名称を入れ替えます。（例：000001 原料Ａを原料Ｂに入れ替えます）</label>
                </div>
            </div>
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
                    <label>職場</label>
                </div>
                <div class="control col-xs-5">
                    <input type="tel" data-prop="cd_shokuba" style="width: 50px;" class="hin_code hin_mei" maxlength="2" />
                    <select data-prop="nm_shokuba" style="width: 150px;" class="hin_code hin_mei"></select>
                    <label>の配合レシピのみ対象</label>
                </div>
                <div class="control col-xs-6">
                    <label style="white-space: nowrap;"></label>
                </div>
            </div>

            <div class="row">
                <div class="control-label col-xs-1">
                    <label>元コード<span class="red-color">*</span></label>
                </div>
                <div class="control col-xs-1">
                    <input type="tel" data-prop="cd_hin_old" class="limit-input-digit" />
                </div>
                <div class="control col-xs-1">
                    <button class="btn btn-info btn-xs hin-old">検索</button>
                </div>
                <div class="control col-xs-3">
                    <label style="white-space: nowrap;"></label>
                </div>
                <div class="control-label col-xs-1">
                    <label>新コード</label>
                </div>
                <div class="control col-xs-1">
                    <input type="tel" data-prop="cd_hin_new" class="haigo_code hin_code hinmei_clear limit-input-digit" />
                </div>
                <div class="control col-xs-1">
                    <button class="btn btn-info btn-xs hin-new" disabled>検索</button>
                </div>
                <div class="control col-xs-3">
                    <label style="white-space: nowrap;"></label>
                </div>
            </div>
            <div class="row">
                <div class="control-label col-xs-1">
                    <label>名称</label>
                </div>
                <div class="control col-xs-5">
                    <label data-prop="nm_hin_old"></label>
                </div>
                <div class="control-label col-xs-1">
                    <label>名称</label>
                </div>
                <div class="control col-xs-5">
                    <label data-prop="nm_hin_new" class="hinmei_clear"></label>
                </div>
            </div>
            <div class="row">
                <div class="control-label col-xs-1">
                    <label>品区分</label>
                </div>
                <div class="control col-xs-5">
                    <label data-prop="nm_kbn_old"></label>
                </div>
                <div class="control-label col-xs-1">
                    <label>品区分</label>
                </div>
                <div class="control col-xs-5">
                    <label data-prop="nm_kbn_new" class="hinmei_clear"></label>
                </div>
            </div>
            <div class="row">
                <div class="control-label col-xs-1">
                    <label>一個の量</label>
                </div>
                <div class="control col-xs-5">
                    <label class="comma-number" data-prop="qty_nisugata_old"></label>
                </div>
                <div class="control-label col-xs-1">
                    <label>一個の量</label>
                </div>
                <div class="control col-xs-5">
                    <label class="comma-number" data-prop="qty_nisugata_new"></label>
                </div>
            </div>
            <div class="row">
                <div class="control col-xs-2">
                    <label>
                        <input type="checkbox" data-prop="flg_seihin" disabled="disabled" />
                        <i>製品マスタの配合コードも対象</i>
                    </label>
                </div>
                <div class="control col-xs-3">
                    <label>
                        <input type="checkbox" data-prop="flg_genryo" disabled="disabled" />
                        <i>前処理原料マスタの元原料コードも対象</i>
                    </label>
                </div>
                <div class="control col-xs-3">
                    <label>
                        <input type="checkbox" data-prop="flg_haigo" disabled="disabled" />
                        <i>配合レシピの荷姿重量も更新対象</i>
                    </label>
                </div>
                <div class="control col-xs-2">
                    <label>
                        <input type="checkbox" data-prop="flg_genzai" class="hin_code hin_mei" disabled="disabled" />
                        <i>現在有効な版を対象</i>
                    </label>
                </div>
                <div class="control col-xs-2">
                    <label>
                        <input type="checkbox" data-prop="flg_mishiyo" class="hin_code hin_mei" disabled="disabled" />
                        <i>未使用も対象</i>
                    </label>
                </div>
            </div>


            <!--
                <div class="row">
                    <div class="control-label col-xs-2">
                        <label>品名</label>
                    </div>
                    <div class="control col-xs-10">
                        <input type="text" class="ime-active" data-prop="nm_hinmei" />
                    </div>
                </div>
                -->
            <div class="header-command">
                <button type="button" id="search" class="btn btn-sm btn-primary">検索</button>
            </div>
        </div>
        <!--TODO: 明細を定義するHTMLをここに記述します。-->
        <div class="detail">
            <!--TODO: 明細をpartにする場合は以下を利用します。
            <div title="TODO: 明細一覧部のタイトルを設定します。"  class="part">
            -->
            <div class="control-label toolbar row">
                <i class="icon-th"></i>
                <label>使用一覧</label>
                <div class="btn-group">
                    <label>
                    <input type="checkbox" data-prop="flg_check_all" />
                        <i>全選択</i>
                    </label>
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
                    <tr class="haigo">
                        <!--TODO: 単一行を作成する場合は、下記を利用します。-->
                        <th style="width: 10px;"></th>
                        <th style="width: 140px;">コード</th>
                        <th style="width: 220px;">品区分</th>
                        <th style="width: 450px;">名称</th>
                        <th style="background: white; border-color: white;"></th>
                        <!--TODO: 多段行を作成する場合は、以下を利用し、上記１行は削除します。
							<th rowspan="2" style="width: 10px;"></th>
                       	</tr>
                        <tr>
						-->
                    </tr>
                    <tr class="hinHeader" style="display: none;">
                        <!--TODO: 単一行を作成する場合は、下記を利用します。-->
                        <th style="width: 10px;"></th>
                        <th style="width: 30px;"></th>
                        <th style="width: 220px;">品区分</th>
                        <th style="width: 140px;">コード</th>
                        <th style="width: 150px;" class="showHideHinWeight">配合重量</th>
                        <th style="width: 50px;">版</th>
                        <th style="width: 50px;">指</th>
                        <th style="width: 450px;">名称</th>
                        <th style="background: white; border-color: white;"></th>
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
                        <td class="modeBind1 isHide">
                            <label data-prop="cd_hin"></label>
                        </td>
                        <td class="modeBind1 isHide">
                            <label data-prop="nm_kbn_hin"></label>
                        </td>
                        <td class="modeBind1 isHide">
                            <label data-prop="nm_hin"></label>
                        </td>
                        <td class="text-center modeBind2 isHide">
                            <input type="checkbox" class="check" data-prop="flg_select" />
                        </td>
                        <td class="modeBind2 isHide" >
                            <label data-prop="nm_kbn_hin"></label>
                        </td>
                        <td class="modeBind2 isHide">
                            <label data-prop="cd_hin"></label>
                        </td>
                        <td class="modeBind2 showHideHinWeight isHide text-right">
                            <label data-prop="qty_haigo_h" class="comma-number"></label>
                        </td>
                        <td class="text-center modeBind2 isHide">
                            <label data-prop="no_han"></label>
                        </td>
                        <td class="modeBind2 isHide text-center">
                            <label data-prop="kbn_shitei" style="font-size: 18px;"></label>
                        </td>
                        <td class="modeBind2 isHide">
                            <label data-prop="nm_hin"></label>
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
            <div class="part-command">
            </div>
            <div class="detail-command">
                <button type="button" id="nextsearch" class="btn btn-sm btn-primary btn-next-search" style="display: none">次を検索</button>
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
