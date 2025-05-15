<%@ Page Title="403_関連資料・添付資料" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="403_KanrenShiryo.aspx.cs" Inherits="Tos.Web.Pages._403_KanrenShiryo" %>

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

        .header textarea {
            width: 98%;
            height: 100%;
            resize: none;
        }

        .control-textarea {
            height: 100px;
        }

        .btn-download, .btn-url {
            width: 90%;
            padding: 1px !important;
        }

        textarea {
            background: #ebebe4 !important;
        }
    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("_403_KanrenShiryo", {
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.dataTakeCount,   // TODO:取得するデータ数を指定します。
                filter: "",
                id_kino_edit: 10,
                id_data_edit: 9
            },
            values: {
                isChangeRunning: {},
                cd_shain: null,
                nen: null,
                no_gate: null,
                no_bunrui: null,
                no_check: null,
                no_meisai: null,
                mode: null,
                isSekkei: true,
                isModeView: false,
                isDelete: false,
                kbn_btn: null
            },
            urls: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
                save: "../api/_403_KanrenShiryo/Post",
                remove: "../api/_403_KanrenShiryo/Delete",
                upload: "../api/_403_KanrenShiryo/Upload",
                download: "../api/_403_KanrenShiryo/DowloadFile",
                maxNoAttach: "../api/_403_KanrenShiryo/GetMaxNoAttach",
            },
            header: {
                options: {},
                values: {
                    maxNoAttach: 1
                },
                urls: {
                    vw_ma_gate_check: "../Services/ShisaQuickService.svc/vw_ma_gate_check?$filter=no_gate eq {0} and no_bunrui eq {1}",
                    getGateBunrui: "../Services/ShisaQuickService.svc/ma_gate_bunrui?$filter=no_gate eq {0} and no_bunrui eq {1}",
                    search: "../api/_403_KanrenShiryo"
                }
            },
            detail: {
                options: {},
                values: {
                    kbnAttachFile: "ダウンロード",
                    kbnAttachLink: "URLを開く",
                    flg_delete: 1,
                    count: 0
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
         * Upload file to temp folder
         */
        page.commands.uploadFile = function () {

            var element = page.header.element,
                target = element.findP("nm_attach");

            if ((target.val() == "" || target[0].files[0].size === 0) && !$('#radioUrl').is(':checked')) {
                App.ui.page.notifyAlert.message(App.messages.app.AP0087, target).show();
                return App.async.fail("file_size_err");
            }
            return $.ajax(App.ajax.file.upload(page.urls.upload, target[0].files[0]));
        }     

        /**
         * Download the file
         */
        page.detail.downloadFile = function (e) {

            var target = $(e.target),
                id = target.closest("tbody").attr("data-key"),
                data = page.detail.data.entry(id);

            if (data.kbn_attach == App.settings.app.kbn_attach.file) {
                if (!data || !data.isExistFile) {
                    return page.dialogs.confirmDialog.confirm({
                        text: App.messages.app.AP0147,
                        hideCancel: true
                    });
                }

                return $.ajax(App.ajax.file.download(page.urls.download, data, "POST")).then(function (response, status, xhr) {
                    if (status === "success") {
                        App.file.save(response, App.ajax.file.extractFileNameDownload(xhr));
                    }
                }).fail(function (error) {
                    if (typeof error.responseJSON == "string" && error.responseJSON == "AP0147") {
                        page.dialogs.confirmDialog.confirm({
                            text: App.messages.app.AP0147,
                            hideCancel: true
                        });
                    }
                    //App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                });
            }
            else {
                if (data.nm_attach != "") {
                    window.open(data.nm_attach);
                }
            }
        }

        /**
         * データの保存処理を実行します。
         */
        page.commands.save = function () {

            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();
            App.ui.loading.show();
            page.values.isDelete = false;
            page.validateAll().then(function () {
                page.dialogs.confirmDialog.confirm({ text: App.messages.app.AP0004 }).then(function () {
                    App.ui.loading.show();
                    var changeSets = {};
                    var isUploadPassed = false;
                    return page.commands.uploadFile().then(function (tempFilePath) {
                        isUploadPassed = true;
                        changeSets = {
                            value: page.header.data.getChangeSet(),
                            tempFilePath: tempFilePath,
                            isSekkei: page.values.isSekkei,
                            userLogin: App.ui.page.user.EmployeeCD,
                            allowEdit: page.values.allowEdit
                        };
                        var filter = {
                            cd_shain: page.values.cd_shain
                            , nen: parseFloat(page.values.nen)
                            , no_oi: parseFloat(page.values.no_oi)
                            , no_gate: parseInt(page.values.no_gate, 10)
                            , no_bunrui: parseInt(page.values.no_bunrui, 10)
                            , no_check: parseInt(page.values.no_check, 10)
                            , no_meisai: parseInt(page.values.no_meisai, 10)
                        }
                        return $.ajax(App.ajax.webapi.get(page.urls.maxNoAttach, filter))
                       .then(function (result) {
                            if (changeSets.value.created.length > 0 && result) {
                                changeSets.value.created[0].no_attach = result;
                            }
                            return $.ajax(App.ajax.webapi.post(page.urls.save, changeSets));
                        }).then(function () {
                            return App.async.all([page.header.search(false)]);
                        }).then(function () {
                            page.header.setStatusElement();
                            App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                        }).fail(function (error) {
                            if (error) {
                                if (error.status === App.settings.base.conflictStatus) {
                                    App.ui.page.notifyAlert.clear();
                                    App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
                                    return;
                                }
                                if (!App.isUndefOrNull(error.responseJSON) && (error.responseJSON.Message == "AP0172" || error.responseJSON.Message == "AP0183")) {
                                    var element = error.responseJSON.Message == "AP0172" ? page.header.element.findP("nm_attach_url") : page.header.element.findP("nm_attach");
                                    page.setColInvalidStyle(element);
                                    App.ui.page.notifyAlert.message(App.messages.app[error.responseJSON.Message], element).show();
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
                                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                            }
                        });
                    }).fail(function (error) {
                        if (error != "file_size_err" && !isUploadPassed) {
                            App.ui.page.notifyAlert.message(App.messages.app.AP0184).show();
                        }
                    }).always(function () {
                        App.ui.loading.close();
                    });
                });
            }).always(function () {
                setTimeout(function () {
                    page.header.element.find(":input:first").focus();
                }, 100);
                App.ui.loading.close();
            });
        };

        /**
        * set kengen for page
        */
        page.setKengen = function () {

            if (page.values.mode != App.settings.app.mode_SekkeiHinshutsuGeto.edit) {
                page.header.element.find(":input").prop("disabled", true);
                page.detail.element.findP("flg_delete").prop("disabled", true);
                $("#save").prop("disabled", true);
                $("#delete").prop("disabled", true);
                page.values.isModeView = true;
            }
        }

        /**
        * データの削除処理を実行します。
        */
        page.commands.remove = function () {

            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyInfo.clear();
            App.ui.page.notifyAlert.clear();

            // No data found or data not valid
            if (page.detail.element.find("td [data-prop='flg_delete']:checked:visible").length === 0) {
                App.ui.page.notifyAlert.message(App.messages.app.AP0008).show();
                return;
            }
            page.values.isDelete = true;
            page.validateAll()
            .then(function () {
                page.dialogs.confirmDialog.confirm({ text: App.messages.base.MS0004 }).then(function () {

                    App.ui.loading.show();

                    var changeSets = {
                        value: page.detail.data.getChangeSet(),
                        isSekkei: page.values.isSekkei
                    }

                    var updated = changeSets.value.updated
                        , deleted;

                    for (var i = 0; i < updated.length; i++) {
                        if (updated[i].flg_delete == "1") {
                            deleted = $.extend({}, updated[i]);
                            changeSets.value.deleted.push(deleted);
                        }
                        changeSets.value.updated.splice(i, 1);
                        i--;
                    }

                    return $.ajax(App.ajax.webapi.post(page.urls.remove, changeSets)).then(function (result) {
                        //最後に再度データを取得しなおします。
                        return App.async.all([page.header.search(false)]);
                    }).then(function () {
                        App.ui.page.notifyInfo.message(App.messages.base.MS0008).show();
                    }).fail(function (error) {
                        if (error) {
                            if (error.status === App.settings.base.conflictStatus) {
                                //page.header.search(false);
                                App.ui.page.notifyAlert.clear();
                                App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
                                return;
                            }
                            App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                        }
                    }).always(function () {
                        App.ui.loading.close();
                    });
                });
            });
        }

        /**
         * すべてのバリデーションを実行します。
         */
        page.validateAll = function () {

            var validations = [];

            validations.push(page.detail.validateList());
            validations.push(page.header.validator.validate());

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

            page.values.cd_shain = !App.isUndefOrNull(App.uri.queryStrings.cd_shain) ? parseFloat(App.uri.queryStrings.cd_shain): parseFloat(page.values.cd_shain);
            page.values.nen = !App.isUndefOrNull(App.uri.queryStrings.nen) ? App.uri.queryStrings.nen : page.values.nen;
            page.values.no_oi = !App.isUndefOrNull(App.uri.queryStrings.no_oi) ? App.uri.queryStrings.no_oi : page.values.no_oi;
            page.values.no_gate = !App.isUndefOrNull(App.uri.queryStrings.no_gate) ? App.uri.queryStrings.no_gate : page.values.no_gate;
            page.values.no_bunrui = !App.isUndefOrNull(App.uri.queryStrings.no_bunrui) ? App.uri.queryStrings.no_bunrui : page.values.no_bunrui;
            page.values.no_check = !App.isUndefOrNull(App.uri.queryStrings.no_check) ? App.uri.queryStrings.no_check : page.values.no_check;
            page.values.no_meisai = !App.isUndefOrNull(App.uri.queryStrings.no_meisai) ? App.uri.queryStrings.no_meisai : page.values.no_meisai;
            page.values.mode = !App.isUndefOrNull(App.uri.queryStrings.mode) ? App.uri.queryStrings.mode : page.values.mode;
            page.values.kbn_btn = !App.isUndefOrNull(App.uri.queryStrings.kbn_btn) ? App.uri.queryStrings.kbn_btn : page.values.kbn_btn;

            var opener = window.opener,
                idGamen = App.settings.app.id_gamen.shisaku_data;   //id_gamen = 10

            // If the page call from 402
            if (opener && (opener._402_ChekkuMasutaIchiran)) {
                page.values.isSekkei = false;
                idGamen = App.settings.app.id_gamen.gate_check;     //id_gamen = 730
            }

            // Get kengen with id_gamen 
            getKengenGamen(idGamen).then(function (results) {

                // Check kengen with id_kino = 10 and id_data = 9
                if (results) {
                    page.values.allowEdit = $.grep(results, function (item) {
                        return item.id_kino === page.options.id_kino_edit && item.id_data === page.options.id_data_edit
                    }).length > 0;
                }

                page.loadMasterData().then(function (result) {

                    return page.loadDialogs();
                }).then(function (result) {

                    page.header.search();
                    page.setKengen();
                }).fail(function (error) {
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

                }).always(function (result) {

                    page.header.element.find(":input:first").focus();
                    App.ui.loading.close();
                });
            });
        };

        /**
         * 画面コントロールの初期化処理を行います。
         */
        page.initializeControl = function () {
            $(".part").part();
        };

        /**
         * コントロールへのイベントの紐づけを行います。
         */
        page.initializeControlEvent = function () {
            $("#save").on("click", page.commands.save);
            $("#delete").on("click", page.commands.remove);
        };

        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function () {

            var element = page.header.element,
                noGate;

            if (page.values.no_gate == App.settings.app.no_gate_val.no_gate_1) {
                noGate = App.settings.app.no_gate[0].text;
            }
            else if (page.values.no_gate == App.settings.app.no_gate_val.no_gate_2) {
                noGate = App.settings.app.no_gate[1].text;
            }
            else if (page.values.no_gate == App.settings.app.no_gate_val.no_gate_3) {
                noGate = App.settings.app.no_gate[2].text;
            }
            else if (page.values.no_gate == App.settings.app.no_gate_val.no_gate_4) {
                noGate = App.settings.app.no_gate[3].text;
            }

            element.findP("no_gate").val(noGate);

            return $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.getGateBunrui, page.values.no_gate, page.values.no_bunrui))).then(function (result) {

                if (result.value.length > 0) {
                    element.findP("nm_bunrui").val(result.value[0].nm_bunrui);
                }
                
                var url = page.header.urls.vw_ma_gate_check;
                if ((page.values.cd_shain != 0 && parseFloat(page.values.nen) != 0 && parseFloat(page.values.no_oi)) || parseFloat(page.values.no_check) == 0) {
                    url = url + "and no_check eq {2} and cd_shain eq {3}M and nen eq {4} and no_oi eq {5} and no_meisai eq {6}";
                    url = App.str.format(url, page.values.no_gate, page.values.no_bunrui, page.values.no_check, page.values.cd_shain, page.values.nen, page.values.no_oi, page.values.no_meisai)
                }else{
                    url = App.str.format(url + "and no_check eq {2}", page.values.no_gate, page.values.no_bunrui, page.values.no_check)
                }
                return $.ajax(App.ajax.odata.get(url));
            }).then(function (result) {

                if (result.value.length > 0) {
                    element.findP("nm_check").val(result.value[0].nm_check);
                }
            });
        };

        /**
         * 共有ダイアログのロード処理を実行します。
         */
        page.loadDialogs = function () {

            return App.async.all({
                confirmDialog: $.get(page.urls.confirmDialog),
            }).then(function (result) {

                $("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;
            });
        }

        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.validations = {
            nm_attach: {
                rules: {
                    check_required: function (value, opts, state, done) {
                        if (page.values.isDelete) {
                            return done(true);
                        }
                        if (!$('#radioUrl').is(':checked') && value == "") {
                            return done(false);
                        }
                        return done(true);
                    },
                    maxbytelength: 132
                },
                options: {
                    name: "アップロードファイル",
                    byte: 120,
                    length: 60
                },
                messages: {
                    check_required: App.messages.base.required,
                    maxbytelength: App.messages.app.AP0148
                }
            },
            nm_attach_url: {
                rules: {
                    check_required: function (value, opts, state, done) {
                        if (page.values.isDelete) {
                            return done(true);
                        }
                        if ($('#radioUrl').is(':checked') && value == "") {
                            return done(false);
                        }
                        return done(true);
                    },
                    hankaku: true,
                    maxlength: 1000
                },
                options: {
                    name: "URL"
                },
                messages: {
                    check_required: App.messages.base.required,
                    hankaku: App.messages.base.hankaku,
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_attach_note: {
                rules: {
                    maxlengthCus: function (value, opts, state, done) {
                        if (page.values.isDelete) {
                            return done(true);
                        }
                        if (App.isUndefOrNullOrStrEmpty(value)) {
                            return done(true);

                        }
                        value = App.isNum(value) ? value + "" : value;
                        var length = App.isArray(value) ? value.length : value.length;

                        if (((value || "") + "") === "" || length <= 100) {
                            return done(true);
                        } else {
                            var option = {
                                name: "資料の説明",
                                param: 100
                            };
                            page.header.options.validations.nm_attach_note.messages.maxlengthCus = App.str.format(App.messages.base.maxlength, option);
                            return done(false);
                        }
                    }
                },
                options: {
                    name: "資料の説明"
                },
                messages: {
                    maxlengthCus: App.messages.base.maxlength
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

            element.on("change", ":input", page.header.change);
            element.find(":input:not([type='radio']):not([type='file'])").prop("disabled", true);
            element.findP("nm_attach_note").prop("disabled", false);

            page.header.bind();
        };

        /**
         * 検索処理を定義します。
         */
        page.header.search = function (isLoading) {

            var deferred = $.Deferred(),
                element = page.header.element;

            page.options.skip = 0;
            page.options.filter = page.header.createFilter();

            if (isLoading) {
                App.ui.loading.show();
                App.ui.page.notifyAlert.clear();
            }

            return $.ajax(App.ajax.webapi.get(page.header.urls.search, page.options.filter)).done(function (result) {
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
            return deferred.promise();
        };

        /**
         * 検索条件のフィルターを定義します。
         */
        page.header.createFilter = function () {
            var criteria = page.header.element.form().data();

            var filter = {
                cd_shain: page.values.cd_shain,
                nen: page.values.nen,
                no_oi: page.values.no_oi,
                no_gate: page.values.no_gate,
                no_bunrui: page.values.no_bunrui,
                no_check: page.values.no_check,
                no_meisai: page.values.no_meisai,
                cd_kaisha: App.ui.page.user.cd_kaisha,
                skip: page.options.skip,
                top: page.options.top,
                isSekkei: page.values.isSekkei,
                kbn_btn: page.values.kbn_btn,
                mode: page.values.mode
            };
            return filter;
        };

        /**
         * 画面ヘッダーの変更時処理を定義します。
         */
        page.header.change = function (e) {

            var target = $(e.target),
                element = page.header.element,
                id = element.attr("data-key"),
                property = target.attr("data-prop"),
                entity = page.header.data.entry(id),
                data = element.form().data(),
                nm_attach_note = element.findP("nm_attach_note").val();

            page.values.isChangeRunning[property] = true;

            page.header.validator.validate({
                targets: target
            }).then(function () {

                entity[property] = data[property];
                entity["kbn_attach"] = false;
                entity["cd_shain"] = page.values.cd_shain;
                entity["nen"] = page.values.nen;
                entity["no_oi"] = page.values.no_oi;
                entity["no_gate"] = page.values.no_gate;
                entity["no_bunrui"] = page.values.no_bunrui;
                entity["no_check"] = page.values.no_check;
                entity["no_meisai"] = page.values.no_meisai;
                entity["nm_attach_note"] = nm_attach_note == "" ? null : nm_attach_note;
                entity["no_attach"] = page.header.values.maxNoAttach;
                if ($('#radioUrl').is(':checked')) {
                    entity["kbn_attach"] = true;
                }

                if (property == "nm_attach") {
                    var name = page.header.getFileName();
                    target.prop("title", name);
                    entity["nm_attach"] = name;
                }
                if ($('#radioUrl').is(':checked')) {
                    entity["nm_attach"] = element.findP("nm_attach_url").val();
                    element.findP("nm_attach").prop("disabled", true).val("");
                    element.findP("nm_attach_url").prop("disabled", false);
                    element.validation().validate({
                        targets: element.findP("nm_attach")
                    });
                }
                else {
                    entity["nm_attach"] = page.header.getFileName();
                    element.findP("nm_attach").prop("disabled", false);
                    element.findP("nm_attach_url").prop("disabled", true).val("");
                    element.validation().validate({
                        targets: element.findP("nm_attach_url")
                    });
                }

                page.header.data.update(entity);
            }).always(function () {
                delete page.values.isChangeRunning[property];
            });
        }

        /**
        * get File Name 
        */
        page.header.getFileName = function () {
            return page.header.element.findP("nm_attach").val().split(/(\\|\/)/g).pop()
        }

        /**
         * set status and clear value elements
         */
        page.header.setStatusElement = function () {

            var element = page.header.element;

            element.find(":input:not([data-prop='no_gate']):not([data-prop='nm_bunrui']):not([data-prop='nm_check'])").val("");
            element.find("#radioFile").prop("checked", true);
            element.findP("nm_attach_url").prop("disabled", true);
            element.findP("nm_attach").prop("disabled", false);
        }

        /**
         * 画面明細のバリデーションを定義します。
         */
        page.detail.options.validations = {
            flg_delete: {
                rules: {
                    checkDelete: function (value, opts, state, done) {
                        var $tbody = state.tbody.element ? state.tbody.element : state.tbody,
                            data = page.detail.data.entry($tbody.attr("data-key"));
                        if (!page.values.isDelete) {
                            return done(true);
                        }
                        if (!$tbody.findP("flg_delete").is(":checked")) {
                            return done(true);
                        }
                        if (App.ui.page.user.EmployeeCD != data.id_toroku && data.kbn_attach == App.settings.app.kbn_attach.file && !page.values.allowEdit && page.values.isSekkei) {
                            page.detail.options.validations.flg_delete.messages.checkDelete = App.messages.app.AP0173;
                            return done(false);
                        }
                        else if (App.ui.page.user.EmployeeCD != data.id_toroku && data.kbn_attach == App.settings.app.kbn_attach.url && !page.values.allowEdit && page.values.isSekkei) {
                            page.detail.options.validations.flg_delete.messages.checkDelete = App.messages.app.AP0174;
                            return done(false);
                        }
                        return done(true);
                    }
                },
                options: {
                    name: ""
                },
                messages: {
                    checkDelete: App.messages.app.AP0173
                }
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

            element.on("click", ".btn-download", page.detail.downloadFile);

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

            page.detail.bind([], true);
        };

        /**
         * 画面明細の各行にデータを設定する際のオプションを定義します。
         */
        page.detail.options.bindOption = {
            appliers: {
                dt_koshin: function (value, element) {
                    element.text(App.data.getDateString(new Date(value)));
                    return true;
                }
            }
        };

        /**
         * 編集画面へのデータバインド処理を行います。
         */
        page.header.bind = function () {

            var data = {},
                setData = {},
                dataSet = App.ui.page.dataSet();

            setData = App.isUndefOrNull(data) ? data : data.constructor();
            for (var attr in data) {
                if (data.hasOwnProperty(attr)) setData[attr] = data[attr];
            }

            page.header.data = dataSet;
            page.header.data.add.bind(dataSet)(setData);
            page.header.element.form(page.header.options.bindOption).bind(setData);
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
            page.detail.values.count = data.length;

            page.detail.dataTable.dataTable("addRows", data, function (row, item) {
                (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
                row.form(page.detail.options.bindOption).bind(item);

                if (item.kbn_attach == App.settings.app.kbn_attach.file) {
                    row.find(".btn-download").html(page.detail.values.kbnAttachFile);
                }
                else if (item.kbn_attach == App.settings.app.kbn_attach.url) {
                    row.find(".btn-download").html(page.detail.values.kbnAttachLink);
                }
                return row;
            }, true);

            page.options.skip += data.length;

            if (page.options.skip >= App.settings.base.maxSearchDataCount) {
                App.ui.page.notifyInfo.message(App.messages.base.MS0011).show();
            }

            if (data.length > 0 && !page.values.isModeView) {
                $("#delete").prop("disabled", false);
            }
            else {
                $("#delete").prop("disabled", true);
            }
            //offsetHeight = $("#nextsearch").is(":visible") ? $("#nextsearch").addClass("show-search").outerHeight() : 0;
            //page.detail.dataTable.dataTable("setAditionalOffset", offsetHeight);
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
                id = row.element.attr("data-key"),
                property = target.attr("data-prop"),
                entity = page.detail.data.entry(id),
                options = {
                    filter: page.detail.validationFilter
                };

            page.values.isChangeRunning[property] = true;

            page.detail.executeValidation(target, row, { filter: page.detail.validationFilterCheckDelete }).then(function () {
                entity[property] = row.element.form().data()[property];

                page.detail.data.update(entity);
                if ($("#delete").is(":disabled")) {
                    $("#delete").prop("disabled", false);
                }

                //入力行の他の項目のバリデーション（必須チェック以外）を実施します
                page.detail.executeValidation(row.element.find(":input"), row, options);
            }).always(function () {
                delete page.values.isChangeRunning[property];
            });
        };

        /**
         * Allow edit when
         * cd_toroku is the user login
         * and exist id_kino = 10 and id_data = 9
         */
        page.checkAccess = function (item) {
            if (App.isUndefOrNull(item)) {
                return false;
            }
            if (item.id_toroku == App.ui.page.user.EmployeeCD) {
                return true;
            }
            if (page.values.allowEdit) {
                return true;
            }

            return false;
        }

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
            return method !== "required" && method !== "checkDelete";
        };

        /**
         * 画面明細のバリデーションフィルターを設定します。（必須チェックを行わない）
         */
        page.detail.validationFilterCheckDelete = function (item, method, state, options) {
            return method !== "checkDelete";
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
        <div class="header">
            <div title="" class="part">
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>ゲート</label>
                    </div>
                    <div class="control col-xs-2">
                        <input data-prop="no_gate" type="text"/>
                    </div>
                    <div class="control col-xs-1"></div>
                    <div class="control-label col-xs-1">
                        <label>分類</label>
                    </div>
                    <div class="control col-xs-2">
                        <input data-prop="nm_bunrui" type="text" style="width: 94%;" />
                    </div>
                    <div class="control col-xs-5"></div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1 control-textarea">
                        <label>チェック項目</label>
                    </div>
                    <div class="control col-xs-6 control-textarea">
                        <textarea data-prop="nm_check"></textarea>
                    </div>
                    <div class="control col-xs-5 control-textarea"></div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-12">
                        <label>
                            <input type="radio" name="kbn_attach" value="0" id="radioFile" checked="checked" />
                            <span>ファイルをアップロードする</span>
                        </label>
                        <label>
                            <input type="radio" name="kbn_attach" value="1" id="radioUrl" />
                            <span>URLを指定する</span>
                        </label>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-2">
                        <label>アップロードファイル</label>
                    </div>
                    <div class="control col-xs-5">
                        <input id="btnUpload" data-prop="nm_attach" type="file" style="width:98%;">
                    </div>
                    <div class="control col-xs-5"></div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>URL</label>
                    </div>
                    <div class="control col-xs-6">
                        <input data-prop="nm_attach_url" type="text" />
                    </div>
                    <div class="control col-xs-5"></div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>資料の説明</label>
                    </div>
                    <div class="control col-xs-6">
                        <input data-prop="nm_attach_note" type="text" />
                    </div>
                    <div class="control col-xs-5"></div>
                </div>
            </div>
        </div>
        <div class="detail">
            <table class="datatable">
                <thead>
                    <tr>
                        <th style="width: 8px;"></th>
                        <th style="width: 25px;"></th>
                        <th style="width: 90px;">関連資料</th>
                        <th style="width: 350px;">ファイルまたはURL</th>
                        <th style="width: 600px;">資料の説明</th>
                        <th style="width: 110px;">最終更新者・更新日</th>
                    </tr>
                </thead>
                <tbody class="item-tmpl" style="cursor: default; display: none;">
                    <tr>
                        <td>
                            <span class="select-tab unselected"></span>
                        </td>
                        <td class="text-center">
                            <input type="checkbox" value="1" data-prop="flg_delete" />
                        </td>
                        <td class="text-center">
                            <button type="button" class="btn btn-xs btn-primary btn-download">ダウンロード</button>
                        </td>
                        <td>
                            <label data-prop="kbn_attach" class="overflow-ellipsis" style="display: none;"></label>
                            <label data-prop="nm_attach" class="overflow-ellipsis"></label>
                        </td>
                        <td>
                            <label data-prop="nm_attach_note" class="overflow-ellipsis"></label>
                        </td>
                        <td>
                            <label data-prop="nm_koshin" class="overflow-ellipsis"></label>
                            <br />
                            <label data-prop="dt_koshin" class="overflow-ellipsis"></label>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

</asp:Content>

<asp:Content ID="FooterContent" ContentPlaceHolderID="FooterContent" runat="server">
    <div class="command-left">
    </div>
    <div class="command">
        <button type="button" id="delete" class="btn btn-sm btn-primary">削除</button>
        <button type="button" id="save" class="btn btn-sm btn-primary">保存</button>
    </div>
</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>
