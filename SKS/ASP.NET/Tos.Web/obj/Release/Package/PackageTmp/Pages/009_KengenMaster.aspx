<%@ Page Title="009_権限マスタ" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="009_KengenMaster.aspx.cs" Inherits="Tos.Web.Pages.KengenMaster" %>

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
        .part div .detail-command {
            text-align: center;
        }

        select[disabled] {
            background: rgb(235, 235, 228)!important;
        }

        input[disabled] {
            background: rgb(235, 235, 228)!important;
        }

        .red-color {
            color: red;
        }
    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("_009_KengenMaster", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.maxInputDataCount,   // TODO:取得するデータ数を指定します。
                filter: ""
            },
            values: {
                isChangeRunning: {},
                countDetail: 0,
                id_kino: 20,
                id_data: 9
            },
            urls: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
                mainMenu: "MainMenu.aspx"
            },
            header: {
                options: {},
                values: {},
                urls: {
                    kengen: "../Services/ShisaQuickService.svc/ma_kengen?$orderby=cd_kengen",
                    userCount: "../Services/ShisaQuickService.svc/ma_user_togo?$filter=cd_kengen eq {0}&$orderby=id_user",
                    search: "../api/KengenMaster"
                }
            },
            detail: {
                options: {},
                values: {},
                urls: {
                    getGamenKinoData: "../api/KengenMaster/GetGamenKinoData",
                    save: "../api/KengenMaster"
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
                page.header.validator.validate()
               .then(function () {
                   page.validateAll().then(function () {
                       var options = {
                           text: App.messages.app.AP0004
                       };
                       var headerdata = page.header.data.getChangeSet(),
                           detaildata = page.detail.data.getChangeSet();

                       var rows = page.detail.element.find("tbody").length;
                       if ((headerdata.created.length > 0 && detaildata.created == 0) || rows == 1) {
                            App.ui.page.notifyAlert.message(App.str.format(App.messages.base.required, { name: page.detail.options.validations.nm_kino.options.name }), "nm_kino").show();
                            App.ui.page.notifyAlert.message(App.str.format(App.messages.base.required, { name: page.detail.options.validations.id_gamen.options.name }), "id_gamen").show();
                            App.ui.page.notifyAlert.message(App.str.format(App.messages.base.required, { name: page.detail.options.validations.id_kino.options.name }), "id_kino").show();
                            App.ui.page.notifyAlert.message(App.str.format(App.messages.base.required, { name: page.detail.options.validations.id_data.options.name }), "id_data").show();

                           return;
                       }

                       page.dialogs.confirmDialog.confirm(options)
                       .then(function () {
                           var changeSets = {
                               header: page.header.data.getChangeSet(),
                               detail: page.detail.data.getChangeSet(),
                               cd_kengen: page.header.element.findP("cd_kengen").val()
                           }

                           //TODO: データの保存処理をここに記述します。
                           return $.ajax(App.ajax.webapi.post(page.detail.urls.save, changeSets))
                               .then(function (result) {

                                   //TODO: データの保存成功時の処理をここに記述します。
                                   var cd_kengen = page.header.element.findP("cd_kengen").val();
                                   //最後に再度データを取得しなおします。
                                   page.loadMasterData(true).then(function(){
                                       
                                       if(result.Header.length > 0){
                                            cd_kengen = result.Header[0].cd_kengen;
                                       } else {
                                            if(result.Detail.length > 0){
                                                cd_kengen = result.Detail[0].cd_kengen;
                                            }
                                       }
                                       page.header.element.findP("cd_kengen").val(cd_kengen);
                                       return App.async.all([page.header.search(false)]);
                                   });
                                   
                               }).then(function () {

                                    $("#kengen_user, #btn-delete").prop("disabled", false);
                                   App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                               }).fail(function (error) {
                                   if (error.status === App.settings.base.conflictStatus) {
                                       // TODO: 同時実行エラー時の処理を行っています。
                                       // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                                       //page.header.search(false);
                                       App.ui.page.notifyAlert.clear();
                                       App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
                                       return;
                                   }

                                   //TODO: データの保存失敗時の処理をここに記述します。
                                   if (error.status === App.settings.base.validationErrorStatus) {
                                       var errors = error.responseJSON;
                                       $.each(errors, function (index, err) {
                                           var keyError ="";
                                           if (!App.isUndefOrNull(err.InvalidationName)) {
                                               if (err.InvalidationName == "id_gamen") {
                                                   keyError = "," + (App.isUndefOrNull(err.InvalidationName) ? "" : err.Data["id_gamen"])
                                                                + "," + (App.isUndefOrNull(err.InvalidationName) ? "" : err.Data["id_kino"]);
                                               }
                                           }
                                           App.ui.page.notifyAlert.message(
                                               err.Message +
                                               (App.isUndefOrNull(err.InvalidationName) ? "" : err.Data["cd_kengen"])
                                               + keyError
                                           ).show();
                                       });
                                       return;
                                   }

                                   App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

                               });
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
         * データの保存処理を実行します。
         */
        page.commands.delete = function () {
            var options = {
                text: App.messages.base.MS0003
            };

            page.dialogs.confirmDialog.confirm(options)
            .then(function () {
                return $.ajax(App.ajax.webapi.delete(page.detail.urls.save, {cd_kengen: page.header.element.findP("cd_kengen").val() }))
                .then(function (result) {
                    page.loadMasterData(true).then(function () {
                        if (page.header.element.findP("cd_kengen").val() != "") {
                            $("#kengen_user, #btn-delete").prop("disabled", false);
                        } else {
                            $("#kengen_user, #btn-delete").prop("disabled", true);
                        }
                    });
                    
                    App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                    setTimeout(function () { 
                        page.commands.clear();
                    }, 500);
                });
            });
        }

        /**
         * Clear data。
         */
        page.commands.clear = function () {
            App.ui.page.notifyAlert.clear();
            page.header.element.find("[data-prop='cd_kengen'], [data-prop='nm_kengen']").val("");
            page.detail.dataTable.dataTable("clear");
            $("#kengen_user, #btn-delete, #save").prop("disabled", true);
            page.detail.element.findP("data_count").text("");
            page.detail.element.findP("data_count_total").text("");
            page.header.bind([{ nm_kengen: "" }], true);
            page.values.countDetail = 0;

            page.header.element.findP("cd_kengen").focus();
        }

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
                getKengenGamen(App.settings.app.id_gamen.kengen_master).then(function (results) {
                page.kengenGamen = results;
                var isRoleScreen = false;
                $.each(results, function (index, item) {
                    if (item.id_kino == page.values.id_kino && item.id_data == page.values.id_data) {
                        isRoleScreen = true;
                    }
                });

                if(!isRoleScreen){
                    window.location.href = page.urls.mainMenu;
                }
            });
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
            $("#btn-delete").on("click", page.commands.delete);
            $("#clear").on("click", page.commands.clear);
            
        };

        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function (isReload) {
            var element = page.header.element;

            //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
            return $.ajax(App.ajax.odata.get(page.header.urls.kengen)).then(function (result) {

                var cd_kengen = element.findP("cd_kengen");
                cd_kengen.children().remove();
                App.common.appendOptions(
                    cd_kengen,
                    "cd_kengen",
                    "nm_kengen",
                    result.value,
                    true,
                    {},
                    true,
                    "0000",
                    "nm_kengen"
                );
                if (App.isUndefOrNull(isReload)) {
                    page.detail.loadGamenKinoData("gamen", null, null, null, page.detail.element, "id_gamen", null);
                }
            });

            //TODO: マスタデータのロード処理を実装後、下の1行を削除してください。
            return App.async.success();
        };

        /**
        * Load combobox gamen。
        */
        page.detail.loadGamenKinoData = function (kbn_type, id_gamen, id_kino, id_data, element, dataProp, value) {

            return $.ajax(App.ajax.webapi.get(page.detail.urls.getGamenKinoData, { kbn_type: kbn_type, id_gamen: id_gamen, id_kino: id_kino, id_data: id_data })).done(function (result) {
                var cd_element = element.findP(dataProp);
                cd_element.children().remove();
                App.ui.appendOptions(
                    cd_element,
                    "id_code",
                    "nm_name",
                    result,
                    true
                );
                if(value != null){
                    cd_element.val(value);
                }
            });
        }
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
            });
        }

        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.validations = {
            nm_kengen: {
                rules: {
                    required: true,
                    maxlength: 60
                },
                options: {
                    name: "権限名"
                },
                messages: {
                    required: App.messages.base.required,
                    maxlength: App.messages.base.maxlength
                }
            }
            //TODO: 画面ヘッダーのバリデーションの定義を記述します。
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
            element.on("click", "#kengen_user", page.header.kengenCountUser);

            //TODO: 画面明細の初期化処理をここに記述します。
            page.header.bind([{nm_kengen: ""}], true);

        };

        /**
         * Change header。
         */
        page.header.change = function (e) {
            var target = $(e.target),
                element = page.header.element,
                id = element.attr("data-key"),
                property = target.attr("data-prop"),
                entity = page.header.data.entry(id);

            if (property == "cd_kengen") {
                if (element.findP("cd_kengen").val() != "") {
                    $("#kengen_user, #btn-delete, #add-item, #del-item").prop("disabled", false);
                } else {
                    $("#kengen_user, #btn-delete, #add-item, #del-item").prop("disabled", true);
                }
                page.detail.dataTable.dataTable("clear");
                element.findP("nm_kengen").val("");

                page.header.search(true);
            }

            if (property == "nm_kengen") {
                page.header.validator.validate()
                .then(function () {
                    entity[property] = element.form().data()[property];
                    page.header.data.update(entity);

                    if ($("#save").is(":disabled")) {
                        $("#save").prop("disabled", false);
                    }
                }).always(function () {
                });
            }
        }

        /**
         * Count user use kengen code。
         */
        page.header.kengenCountUser = function () {
            var element = page.header.element;
            var cd_kengen = element.findP("cd_kengen").val();

            $.ajax(App.ajax.odata.get(App.str.format(page.header.urls.userCount, cd_kengen)))
            .then(function (result) {
                var countData = 0;
                if (result.value.length > 0) {
                    countData = result.value.length;
                }
                var options = {
                    text: App.str.format(App.messages.app.AP0100, countData),
                    hideCancel: true
                };

                page.dialogs.confirmDialog.confirm(options)
                .then(function () {

                });
            });
        }

        /**
         * 検索処理を定義します。
         */
        page.header.search = function (isLoading) {

            var deferred = $.Deferred(),
                query;

            //page.header.validator.validate().done(function () {

                page.options.filter = page.header.createFilter();

                //TODO: データ取得サービスの URLとオプションを記述します。

                if (isLoading) {
                    App.ui.loading.show();
                    App.ui.page.notifyAlert.clear();
                }

                page.values.countDetail = 0;

                return $.ajax(App.ajax.webapi.get(page.header.urls.search, page.options.filter))
                .done(function (result) {
                    // パーツ開閉の判断
                    if (page.detail.isClose) {
                        // 検索データの保持
                        page.detail.searchData = result;
                    } else {
                        // データバインド
                        if (result.header.length == 0) {
                            result.header = [{ nm_kengen: "" }];
                        }
                        page.header.bind(result.header);
                        page.detail.bind(result.detail);
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
            //});

            return deferred.promise();

        };

        /**
         * 検索条件のフィルターを定義します。
         */
        page.header.createFilter = function () {
            var criteria = page.header.element.form().data(),
                filters = {};

            /* TODO: 検索条件のフィルターを定義してください。*/
            filters.cd_kengen = null;
            if (!App.isUndefOrNull(criteria.cd_kengen) && criteria.cd_kengen > 0) {
                filters.cd_kengen = criteria.cd_kengen;
            }

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
            id_gamen: {
                rules: {
                    required: true,
                },
                options: {
                    name: "画面",
                },
                messages: {
                    required: App.messages.base.required,
                }
            },
            id_kino: {
                rules: {
                    required: true,
                },
                options: {
                    name: "機能",
                },
                messages: {
                    required: App.messages.base.required,
                }
            },
            id_data: {
                rules: {
                    required: true,
                },
                options: {
                    name: "参照可能データ",
                },
                messages: {
                    required: App.messages.base.required,
                }
            },
            nm_kino: {
                rules: {
                    required: true,
                    maxlength: 60
                },
                options: {
                    name: "機能名"
                },
                messages: {
                    required: App.messages.base.required,
                    maxlength: App.messages.base.maxlength
                }
            },
            biko: {
                rules: {
                    maxlength: 60
                },
                options: {
                    name: "備考"
                },
                messages: {
                    maxlength: App.messages.base.maxlength
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
         * Bind data for header。
         */
        page.header.bind = function (data, isNewData) {
            var i, l, item, dataSet, dataCount;

            dataSet = App.ui.page.dataSet();
            page.header.data = dataSet;
            page.header.element.findP("nm_kengen").val("");

            if (page.header.element.findP("cd_kengen").val() == "") {
                isNewData = true;
            }

            if (data.length > 0) {
                var item = data[0];
                (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
                page.header.element.form().bind(item);
            } else {
                dataSet.add.bind(dataSet)([{nm_kengen: ""}]);
                page.header.element.form().bind([{ nm_kengen: "" }]);
            }

            if (!isNewData) {
                page.header.validator.validate();
            }

        };
        /**
         * 画面明細へのデータバインド処理を行います。
         */
        page.detail.bind = function (data, isNewData) {
            var i, l, item, dataSet, dataCount;

            dataCount = data.length ? data.length : 0;
            data = (data.Items) ? data.Items : data;

            dataSet = App.ui.page.dataSet();
            page.detail.data = dataSet;
            page.detail.dataTable.dataTable("clear");

            page.detail.dataTable.dataTable("addRows", data, function (row, item) {
                (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
                row.form(page.detail.options.bindOption).bind(item);

                page.detail.loadGamenKinoData("kino", item.id_gamen, null, null, row, "id_kino", item.id_kino);
                row.findP("id_data").children().remove();

                page.detail.loadGamenKinoData("data", item.id_gamen, item.id_kino, null, row, "id_data", item.id_data);

                row.find("[data-prop='id_gamen'], [data-prop='id_kino']").prop("disabled", true);

                return row;
            }, true);

            if (!isNewData) {
                page.detail.element.findP("data_count").text(data.length);
                page.detail.element.findP("data_count_total").text(dataCount);
            }

            page.values.countDetail = dataCount;

            //if (dataCount >= App.settings.base.maxInputDataCount) {
            //    App.ui.page.notifyInfo.message(App.messages.base.MS0011).show();
            //}

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

            if (property == "id_gamen") {
                page.detail.loadGamenKinoData("kino", row.element.form().data()[property], null, null, row.element, "id_kino", null);
                row.element.findP("id_data").children().remove();
            }

            if (property == "id_kino") {
                page.detail.loadGamenKinoData("data", entity.id_gamen, row.element.form().data()[property], null, row.element, "id_data", null);
            }


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
            App.ui.page.notifyAlert.remove("id_gamen");
            App.ui.page.notifyAlert.remove("id_kino");
            App.ui.page.notifyAlert.remove("id_data");
            App.ui.page.notifyAlert.remove("nm_kino");

            var newData = {
                cd_kengen: page.header.element.findP("cd_kengen").val()
            };

            page.detail.data.add(newData);
            page.detail.dataTable.dataTable("addRow", function (tbody) {
                tbody.form(page.detail.options.bindOption).bind(newData);
                return tbody;
            }, true);

            page.detail.countDetail(1);

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

            page.detail.countDetail(-1);

            if ($("#save").is(":disabled")) {
                $("#save").prop("disabled", false);
            }
        };

         /**
         * Count data in detail。
         */
        page.detail.countDetail = function(value){
            page.values.countDetail = page.values.countDetail + value;

            page.detail.element.findP("data_count").text(page.values.countDetail);
            page.detail.element.findP("data_count_total").text(page.values.countDetail);
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
            <div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>権限</label>
                    </div>
                    <div class="control col-xs-3">
                        <select data-prop="cd_kengen"></select>
                    </div>
                    <div class="control col-xs-2">
                        <button class="btn btn-info btn-xs" disabled="disabled" id="kengen_user">権限使用ユーザ確認</button>
                    </div>
                    <div id="nm_kengen_label" class="control-label col-xs-1">
                        <label>権限名<span class="red-color">*</span></label>
                    </div>
                    <div id="nm_kengen" class="control col-xs-3">
                        <input type="text" data-prop="nm_kengen" />
                    </div>
                    <div class="control col-xs-2">
                        <span style="white-space: nowrap;"></span>
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
                <div class="btn-group">
                    <button type="button" class="btn btn-default btn-xs" id="add-item" disabled="disabled">追加</button>
                    <button type="button" class="btn btn-default btn-xs" id="del-item" disabled="disabled">削除</button>
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
                        <th style="width: 10px;"></th>
                        <th class="control" style="width:300px">機能名</th>
                        <th class="control" style="width:280px">画面</th>
                        <th class="control" style="width:205px">機能</th>
                        <th class="control" style="width:385px">参照可能データ</th>
                        <th style="">備考</th>
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
                            <input style="width: 100%; margin-bottom: 0px;" data-prop="nm_kino" />
                        </td>
                        <td>
                            <select data-prop="id_gamen"></select>
                        </td>
                        <td>
                            <select data-prop="id_kino"></select>
                        </td>
                        <td>
                            <select data-prop="id_data"></select>
                        </td>
                        <td>
                            <input style="width: 100%; margin-bottom: 0px;" data-prop="biko" />
                        </td>
                    </tr>
                </tbody>
            </table>
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

        <button type="button" id="btn-delete" class="btn btn-sm btn-primary" disabled="disabled">削除</button>
        <button type="button" id="save" class="btn btn-sm btn-primary" disabled="disabled">保存</button>
    </div>

</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>
