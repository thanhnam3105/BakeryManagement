<%@ Page Title="211_添付文書" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="211_TenpuBunsho.aspx.cs" Inherits="Tos.Web.Pages._211_TenpuBunsho" %>
<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchInputTable(Ver2.1)】 Template--%>

<asp:Content ID="IncludeContent" ContentPlaceHolderID="IncludeContent" runat="server">

    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/part.css") %>" type="text/css" />
    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/headerdetail.css") %>" type="text/css" />

    <% #if DEBUG %>
    <script src="<%=ResolveUrl("~/Scripts/datatable.js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Scripts/part.js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Scripts/uploadfile.js") %>" type="text/javascript"></script>
    <% #else %>
    <script src="<%=ResolveUrl("~/Scripts/datatable.min.js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Scripts/part.min.js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Scripts/uploadfile.min.js") %>" type="text/javascript"></script>
    <% #endif %>

</asp:Content>

<asp:Content ID="HeadContent" ContentPlaceHolderID="HeadContent" runat="server">

    <style type="text/css">
        .part div .detail-command {
            text-align: center;
        }

        div.row.biko-zone .control,
        div.row.biko-zone .control-label {
            height: 45px;
        }

        div.row.biko-zone textarea {
            resize: none;
            height: 40px;
            width: 645px;
        }
        label.bold {
            font-weight: bold;
        }
        td label.ellipsis-line-2 {
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: normal;
            height: 36px;
            word-break: break-all;
        }

        input[type='file'] {
            width: 98%;
        }

    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("_211_TenpuBunsho", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.dataTakeCount,   // TODO:取得するデータ数を指定します。
                filter: "",
                kbn_system_kaihatsu: 0,
                kbn_system_kojo: 1,
                id_kino_edit: 20,
                id_data_edit: 9
            },
            values: {
                isChangeRunning: {}
            },
            urls: {
                search: "../api/_211_TenpuBunsho",
                save: "../api/_211_TenpuBunsho",
                remove: "../api/_211_TenpuBunsho/Delete",
                upload: "../api/_211_TenpuBunsho/Upload",
                download: "../api/_211_TenpuBunsho/DowloadFile",
                confirmDialog: "../Pages/Dialogs/ConfirmDialog.aspx"
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
            var target = page.header.element.findP("nm_file"),
                risingErr = function () {
                    App.ui.page.notifyAlert.message(App.messages.app.AP0087, target).show();
                    return App.async.fail("file_size_err");
                };
            if (target[0].files.length == 0 || target[0].files[0].size === 0) {
                return risingErr();
            }
            return $.ajax(App.ajax.file.upload(page.urls.upload, target[0].files[0]));
        }

        /**
         * Get data base on header data
         */
        page.detail.getDataForSave = function () {
            var changeSet = {
                Created: [],
                Updated: [],
                Deleted: []
            }
            var headerData = page.header.element.form().data();
            headerData.cd_toroku_kaisha = App.ui.page.user.cd_kaisha;
            headerData.cd_toroku = App.ui.page.user.EmployeeCD;
            headerData.nm_toroku = App.ui.page.user.Name;
            headerData.nm_file = page.header.getFileName();

            changeSet.Created.push(headerData);
            // Check if data is exists
            var existedData = page.detail.data.findAll(function (item, entity) {
                return (item.no_seiho === headerData.no_seiho && item.nm_file === headerData.nm_file);
            });
            if (existedData.length > 0) {
                // Rising error when update in different owner file
                if (!page.checkAccess(existedData[0])) {
                    App.ui.page.notifyAlert.message(App.messages.app.AP0088, page.header.element.findP("nm_file")).show();
                    return null;
                }
                changeSet.Deleted.push(existedData[0]);
            }
            return changeSet;
        }

        /**
         * Get data base on checked box in detail
         */
        page.detail.getDataForDelete = function () {
            var changeSet = {
                Created: [],
                Updated: [],
                Deleted: []
            }

            var selected = page.detail.element.find("td [data-prop='select']:checked:visible");
            selected.each(function (ind, item) {
                var tbody = $(item).closest("tbody");
                var data = page.detail.data.entry(tbody.attr("data-key"));
                changeSet.Deleted.push(data);
            });

            if (selected.length === 0) {
                App.ui.page.notifyAlert.message(App.messages.app.AP0008, "AP0008").show();
                return null;
            }

            return changeSet;
        }

        /**
         * Download the file
         */
        page.detail.downloadFile = function (e) {
            var target = $(e.target),
                id = target.closest("tbody").attr("data-key"),
                data = page.detail.data.entry(id);
            var errorRising = function () {
                return page.dialogs.confirmDialog.confirm({
                    text: App.messages.app.AP0147,
                    hideCancel: true
                });
            }
            if (!data || !data.isExistFile) {
                return errorRising();
            }
            return $.ajax(App.ajax.file.download(page.urls.download, data, "POST"))
            .then(function (response, status, xhr) {
                if (status === "success") {
                    App.file.save(response, App.ajax.file.extractFileNameDownload(xhr));

                    //TOsVN - 19075 - 2023/06/20: st ShohinKaihatuSupport Modify 2023
                    App.common.Log.write({
                        cd_game: App.settings.app.log_mode.gamen.TenpuBunshoFile
                        , cd_taisho_data: page.header.element.findP("no_seiho").text()
                        , nm_mode: null
                    });
                    //ed
                }
            }).fail(function (error) {
                if (typeof error.responseJSON == "string" && error.responseJSON == "AP0147") {
                    return errorRising();
                }
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            });
        }

        /**
         * データの保存処理を実行します。
         */
        page.commands.save = function () {

            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            // Get data set
            var changeSets = { };

            page.validateAll().then(function () {
                // Get data set
                changeSets = {
                    value: page.detail.getDataForSave(),
                    kbn_system: page.values.kbn_system
                }
                // No data found or data not valid
                if (!changeSets.value) {
                    return App.async.fail();
                }

                return page.dialogs.confirmDialog.confirm({
                    text: App.messages.app.AP0004
                });
            }).then(function () {

                App.ui.loading.show();
                var isUploadPassed = false;

                return page.commands.uploadFile().then(function (tempFilePath) {
                    changeSets.tempFilePath = tempFilePath;
                    isUploadPassed = true;
                    return $.ajax(App.ajax.webapi.post(page.urls.save, changeSets))
                    .then(function (result) {
                        //最後に再度データを取得しなおします。
                        page.header.reload();
                        return App.async.all([page.header.search(false)]);
                    }).then(function () {
                        App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                    }).fail(function (error) {
                        if (error) {
                            // Conflict error
                            if (error.status === App.settings.base.conflictStatus) {
                                //page.header.search(false);
                                App.ui.page.notifyAlert.clear();
                                App.ui.page.notifyAlert.message(App.messages.base.MS0017, "MS0017").show();
                                return;
                            }
                            // System error
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
        };

        page.commands.remove = function () {
            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyInfo.clear();

            App.ui.page.notifyAlert.remove("AP0087");
            App.ui.page.notifyAlert.remove("AP0088");

            page.header.validator.validate({
                filter: function (item, method) {
                    return method == "maxbytelength";
                }
            })

            var changeSets = {
                value: page.detail.getDataForDelete(),
                kbn_system: page.values.kbn_system
            }
            // No data found or data not valid
            if (!changeSets.value) {
                return;
            }

            return page.dialogs.confirmDialog.confirm({
                text: App.messages.base.MS0004
            }).then(function () {

                App.ui.loading.show();
                return $.ajax(App.ajax.webapi.post(page.urls.remove, changeSets))
                .then(function (result) {
                    //最後に再度データを取得しなおします。
                    return App.async.all([page.header.search(false)]);
                }).then(function () {
                    App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                }).fail(function (error) {
                    if (error) {
                        // Conflict error
                        if (error.status === App.settings.base.conflictStatus) {
                            //page.header.search(false);
                            App.ui.page.notifyAlert.clear();
                            App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
                            return;
                        }
                        // System error
                        App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                    }
                }).always(function () {
                    App.ui.loading.close();
                });
            });
        }

        /**
         * すべてのバリデーションを実行します。
         */
        page.validateAll = function () {

            var validations = [];

            //validations.push(page.detail.validateList());
            validations.push(page.header.validator.validate());

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

            page.values.no_seiho = App.uri.queryStrings.no_seiho;
            page.values.kbn_system = null;
            var opener = window.opener;
            // If the page call from 200 or 203 -> set kbn_system to 0
            if (opener && (opener._200_SeihoIchiran || opener.HaigoTorokuKaihatsuBumon)) {
                page.values.kbn_system = page.options.kbn_system_kaihatsu;
            }
            // If the page call from 208 or 209 -> set kbn_system to 1
            if (opener && (opener._208_HaigoIchiran || opener.HyoujiyouHaigouTouroku)) {
                page.values.kbn_system = page.options.kbn_system_kojo;
            }

            // Get kengen with id_gamen = 500
            getKengenGamen(App.settings.app.id_gamen.tenpu_bunsho).then(function (results) {

                // Check kengen with id_kino = 20 and id_data = 9
                if (results) {
                    page.values.allowEdit = $.grep(results, function (item) {
                        return item.id_kino === page.options.id_kino_edit &&
                               item.id_data === page.options.id_data_edit
                    }).length > 0;
                }

            page.initializeControl();
            page.initializeControlEvent();

            page.header.initialize();
            page.detail.initialize();

            //TODO: ヘッダー/明細以外の初期化の処理を記述します。

            page.loadMasterData().then(function (result) {

                return page.loadDialogs();
            }).then(function (result) {
                return page.header.search();

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

            //TODO: 画面全体で利用するコントロールの初期化処理をここに記述します。
            $(".part").part();

        };

        /**
         * コントロールへのイベントの紐づけを行います。
         */
        page.initializeControlEvent = function () {

            //TODO: 画面全体で利用するコントロールのイベントの紐づけ処理をここに記述します。
            $("#save").on("click", page.commands.save);
            $("#delete").on("click", page.commands.remove);
            $("#nextsearch").on("click", page.commands.nextsearch);
        };

        page.commands.nextsearch = function (e) {
            page.detail.bind([], false, true);
        }

        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function () {

            //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
            //return $.ajax(App.ajax.odata.get(/* マスターデータ取得サービスの URL */)).then(function (result) {

            //    var cd_shiharai = $.findP("cd_shiharai");
            //    cd_shiharai.children().remove();
            //    App.ui.appendOptions(
            //        cd_shiharai,
            //        "cd_shiharai",
            //        "nm_joken_shiharai",
            //        result.value,
            //        true
            //    );
            //});

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
            }).then(function (result) {

                $("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;
                //page.dialogs.confirmDialog.initialize();
            });
        }

        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.validations = {
            nm_file: {
                rules: {
                    required: true,
                    maxbytelength: 132
                },
                options: {
                    name: "アップロードファイル",
                    byte: 120,
                    length: 60
                },
                messages: {
                    required: App.messages.base.selectfile,
                    maxbytelength: App.messages.app.AP0148
                }
            },
            setsumei: {
                rules: {
                    maxbytelength: 100
                },
                options: {
                    name: "文書の説明",
                    byte: 50
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
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

            element.findP("no_seiho").text(page.values.no_seiho);

            //TODO: 画面ヘッダーの初期化処理をここに記述します。
            //TODO: 画面ヘッダーで利用するコントロールのイベントの紐づけ処理をここに記述します。
            element.on("click", "#search", page.header.search);
            element.on("change", ":input", page.header.change);
            //element.on("keydown", "[data-prop='setsumei']", function (e) {
            //    if (e.which && e.which == 13) {
            //        return false;
            //    }
            //});

        };

        /**
         * Windows閉じる際のイベントを定義します。
         * @return 文字列を返却した場合に確認メッセージが表示されます。
         */
        App.ui.page.onclose = function () {

            return page.values.isChanged;
        };

        /**
         * Change data header
         */
        page.header.change = function (e) {
            var target = $(e.target),
                property = target.attr("data-prop"),
                filter = null;
            
            if (property == "nm_file") {
                target.prop("title", page.header.getFileName());
                filter = function () {
                    return false;
                }
            }
            page.header.validator.validate({
                targets: target,
                filter: filter
            }).then(function () {
                page.values.isChanged = true;
            });
        }

        page.header.getFileName = function () {
            return page.header.element.findP("nm_file").val().split(/(\\|\/)/g).pop()
        }

        page.header.reload = function () {
            // Reset header data
            page.header.element.findP("setsumei").val("");
            page.header.validator.validate({
                filter: function () { return false; }
            });
            // Reset file after save
            var fileZone = page.header.element.find(".file-zone");
            fileZone.children().remove();
            fileZone.append($("<input type='file' data-prop='nm_file' />"));
            page.values.isChanged = false;
        }

        /**
         * 検索処理を定義します。
         */
        page.header.search = function (isLoading) {

            var deferred = $.Deferred(),
                query;

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
                    if (result && result.Count > page.options.top) {
                        $("#nextsearch").show();
                    }
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

            return deferred.promise();

        };

        /**
         * 検索条件のフィルターを定義します。
         */
        page.header.createFilter = function () {
            var criteria = page.header.element.form().data();
            criteria.top = page.options.top;
            criteria.skip = 0;
            return criteria;
        };

        //Use tooltip for element use class overflow-ellipsis
        $(document).on("mouseenter", ".ellipsis-line-2", function (e) {
            var target = $(e.target);
            if (target.prop("offsetHeight") < target.prop("scrollHeight") && !target.prop("title")) {
                target.prop("title", target.text());
            }
        });
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

            element.on("click", "[data-prop='download']", page.detail.downloadFile);
            element.on("click", "[data-prop='select-all']", page.detail.selectAll)

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
            //    no_seq: function (value, element) {
            //        element.val(value);
            //        element.prop("readonly", true).prop("tabindex", -1);
            //        return true;
            //    }
            //}
        };

        /**
         * 画面明細へのデータバインド処理を行います。
         * Next search will be process in client because the [Conflict prevent] got the critical issue with general method
         * Dont know why this page NEED THE NEXT SEARCH
         */
        page.detail.bind = function (data, isNewData, isNextSearch) {
            var i, l, item, dataSet, dataCount;

            if (!isNextSearch) {
                dataCount = data.Count ? data.Count : 0;
                data = (data.Items) ? data.Items : data;

                dataSet = App.ui.page.dataSet();
                page.detail.data = dataSet;
                page.detail.dataTable.dataTable("clear");
                // Store search data for next search
                page.values.searchData = $.merge([], data);
                // Set data to dataset
                $.each(page.values.searchData, function (ind, dataRow) {
                    dataSet.attach(dataRow);
                })
                page.options.skip = 0;
            } else {
                dataSet = page.detail.data;
            }
            data = [];
            $.each(page.values.searchData, function (ind, dataRow) {
                if (ind >= page.options.skip && ind < page.options.skip + page.options.top) {
                    data.push(dataRow);
                }
            });

            page.detail.dataTable.dataTable("addRows", data, function (row, item) {
                row.form(page.detail.options.bindOption).bind(item);
                // Disabled checked box 
                if (!page.checkAccess(item)) {
                    row.findP("select").prop("disabled", true);
                }
                return row;
            }, true);

            if (!isNextSearch) {
                // Enabled / Disabled btn control
                $("#delete").prop("disabled", dataCount == 0);
                $.findP("select-all").prop("disabled", dataCount == 0);
                page.values.resultCount = dataCount;
                // set data count
                if (!isNewData) {
                    page.detail.element.findP("data_count").text(data.length);
                    page.detail.element.findP("data_count_total").text(dataCount);
                }
                // Reset header focus
                setTimeout(function () {
                    page.header.element.findP("nm_file").focus();
                }, 300)
            } else {                
                page.detail.element.findP("data_count").text(page.options.skip + data.length);
            }
            page.options.skip += data.length;
            // Pass 1000 record
            if (page.options.skip >= App.settings.base.maxSearchDataCount) {
                App.ui.page.notifyInfo.message(App.messages.base.MS0011).show();
                $("#nextsearch").hide();
            }
            if (page.options.skip >= page.values.resultCount) {
                $("#nextsearch").hide();
            }
            page.detail.checkSelectAll();
            //if (page.options.skip >= page.values.resultCount) {
            //    $("#nextsearch").hide();
            //} else {
            //    $("#nextsearch").show();
            //}

            //TODO: 画面明細へのデータバインド処理をここに記述します。


            //バリデーションを実行します。
            //page.detail.validateList(true);

        };

        /**
         * Allow edit when
         * cd_toroku is the user login
         * or kbn_system = 0 and exist id_kino = 20 and id_data = 9
         */
        page.checkAccess = function (item) {
            if (App.isUndefOrNull(item)) {
                return false;
            }
            if (item.cd_toroku == App.ui.page.user.EmployeeCD) {
                return true;
            }
            if (page.values.kbn_system === page.options.kbn_system_kaihatsu && page.values.allowEdit) {
                return true;
            }

            return false;
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
                entity = page.detail.data.entry(id);

            if (property === "select") {
                page.detail.checkSelectAll();
                App.ui.page.notifyAlert.remove("AP0008");
            }
        };

        /**
         * Select all click event
         */
        page.detail.selectAll = function () {
            var element = page.detail.element;
            var isChecked = element.findP("select-all").is(":checked");
            var targets = element.find("td [data-prop='select']:visible:enabled");
            targets.prop("checked", isChecked);
            App.ui.page.notifyAlert.remove("AP0008");
        }

        /**
         * Check if all checkbox is checked
         */
        page.detail.checkSelectAll = function () {
            var element = page.detail.element;
            var uncheckElem = element.find("td [data-prop='select']:visible:enabled:not(:checked)");
            var checkedElem = element.find("td [data-prop='select']:visible:enabled:checked");
            element.findP("select-all").prop("checked", uncheckElem.length === 0 && checkedElem.length > 0);
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
            <div class="part">
                <div class="row">
                    <div class="col-xs-2 control-label">
                        <label>                            
                            製法番号
                        </label>
                    </div>
                    <div class="col-xs-10 control">
                        <label data-prop="no_seiho" class="bold"></label>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-2 control-label">
                        <label>
                            アップロードファイル
                        </label>
                    </div>
                    <div class="col-xs-3 control file-zone">
                        <input type="file" data-prop="nm_file" />
                    </div>
                    <div class="col-xs-7 control">
                        <label>※空ファイル(0KB)は添付できません。</label>
                    </div>
                </div>
                <div class="row biko-zone">
                    <div class="col-xs-2 control-label">
                        <label>
                            文書の説明
                        </label>
                    </div>
                    <div class="col-xs-10 control">
                        <textarea data-prop="setsumei"></textarea>
                    </div>
                </div>
            </div>
        </div>
        <!--TODO: 明細を定義するHTMLをここに記述します。-->
        <div class="detail">
            <!--TODO: 明細をpartにする場合は以下を利用します。
            <div title="TODO: 明細一覧部のタイトルを設定します。"  class="part">
            -->
                <div class="control toolbar">
                    <span class="data-count">
                    <span data-prop="data_count"></span>
                    <span>/</span>
                    <span data-prop="data_count_total"></span>
                </span>
                </div>
                <table class="datatable">
                    <thead>
                        <tr>
                            <th style="width: 10px;"></th>
                            <th style="width: 30px;">
                                <input type="checkbox" data-prop="select-all" disabled="disabled" />
                            </th>
                            <th style="width: 100px;">ダウンロード</th>
                            <th style="">ファイル名</th>
                            <th style="width: 645px;">文書の説明</th>
                            <th style="width: 90px;">サイズ(KB)</th>
                            <th style="width: 100px;">登録日時</th>
                            <th style="width: 100px;">更新日時</th>
                            <th style="width: 150px;">最終登録者</th>
                        </tr>
                    </thead>
                    <tbody class="item-tmpl" style="cursor: default; display: none;">
                        <tr>
						    <td>
                                <span class="select-tab unselected"></span>
                            </td>
                            <td class="center">
                                <input type="checkbox" data-prop="select" />
                            </td>
                            <td class="center">
                                <button type="button" data-prop="download" class="btn btn-xs btn-primary">ダウンロード</button>
                            </td>
                            <td>
                                <label data-prop="nm_file" style="word-break: break-all" class="ellipsis-line-2"></label>
                            </td>
                            <td>
                                <label data-prop="setsumei" style="word-break: break-all" class="ellipsis-line-2"></label>
                            </td>
                            <td class="right">
                                <label data-prop="file_size" class="comma-number" style="padding-right: 5px;"></label>
                            </td>
                            <td>
                                <label data-prop="dt_create" class="data-app-format" style="padding-left: 5px;"></label>
                            </td>
                            <td>
                                <label data-prop="dt_update" class="data-app-format" style="padding-left: 5px;"></label>
                            </td>
                            <td>
                                <label data-prop="nm_toroku"></label>
                            </td>
                        </tr>
                    </tbody>
                </table>                
                <div class="detail-command center">
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
    </div>
    <div class="command">
        <button type="button" id="delete" class="btn btn-sm btn-primary" disabled="disabled">削除</button>
        <button type="button" id="save" class="btn btn-sm btn-primary">保存</button>
    </div>

</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>