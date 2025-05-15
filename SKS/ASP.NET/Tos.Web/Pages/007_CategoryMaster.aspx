<%@ Page Title="007_カテゴリマスタ" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="007_CategoryMaster.aspx.cs" Inherits="Tos.Web.Pages.CategoryMaster" %>

<asp:Content ID="IncludeContent" ContentPlaceHolderID="IncludeContent" runat="server">

    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/part.css") %>" type="text/css" />
    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/headerdetail.css") %>" type="text/css" />

    <script src="<%=ResolveUrl("~/Scripts/datatable.js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Scripts/part.js") %>" type="text/javascript"></script>

</asp:Content>

<asp:Content ID="HeadContent" ContentPlaceHolderID="HeadContent" runat="server">

    <style type="text/css">
        .part div .detail-command {
            text-align: center;
        }

        .memo {
            height: 110px;
        }
    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("007_CategoryMaster", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.maxInputDataCount,   // TODO:取得するデータ数を指定します。
                filter: ""
            },
            values: {
                isChangeRunning: {},
                temp: null,
                mode: null,
                cd_category: null,
                //kengen
                id_kino_loaddata: 10,
                id_kino_edit: 20,
                id_kino_CSV: 30,
                id_data_group: 1,
                id_data_kaisha: 2,
                id_data_kanrisha: 9
            },
            urls: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
                category: "../Services/ShisaQuickService.svc/ma_category?$orderby=cd_category &",
                //literal: "../Services/ShisaQuickService.svc/ma_literal?$orderby=no_sort &$filter=cd_category eq '{0}'",
                group: "../Services/ShisaQuickService.svc/ma_group?$orderby=flg_hyoji &",
                saiban: "../Services/ShisaQuickService.svc/ma_saiban",
                literal: "../api/CategoryMaster/GetLiteralCombo",
                mainMenu: "MainMenu.aspx"
            },
            header: {
                options: {},
                values: {
                },
                urls: {
                    downloadCSV: "../api/CategoryMaster_CSVDownload",
                    literalDetail: "../api/CategoryMaster",
                    Get_saiban: "../api/CategoryMaster/Get_saiban"
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
            
            validations.push(page.header.validator.validate());
            validations.push(page.detail.validator.validate());

            //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。

            return App.async.all(validations);
        };

        /**
         * Windows閉じる際のイベントを定義します。
         * @return 文字列を返却した場合に確認メッセージが表示されます。
         */
        App.ui.page.onclose = function () {
            return page.detail.isChanged;
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

            getKengenGamen(App.settings.app.id_gamen.category_master).then(function (results) {
                page.kengenGamen = results;
                $.each(results, function (index, item) {
                    switch (item.id_kino) {
                        case page.values.id_kino_loaddata: // id_kino = 10
                            switch (item.id_data) {
                                case page.values.id_data_group: // id_data = 1
                                    page.urls.category += "$filter=flg_edit eq 1 and( flg_gencho ne 1 or flg_gencho eq null)";
                                    //page.urls.literal += " and (cd_group eq " + App.ui.page.user.cd_group + " or cd_group eq null)";
                                    page.values.temp = App.ui.page.user.cd_group;
                                    page.values.mode = page.values.id_data_group;
                                    page.urls.group += "$filter=cd_group eq " + App.ui.page.user.cd_group;
                                    break;
                                case page.values.id_data_kaisha: // id_data = 2
                                    page.urls.category += "$filter=flg_edit eq 1 and( flg_gencho ne 1 or flg_gencho eq null)";
                                    page.values.temp = App.ui.page.user.cd_kaisha;
                                    page.values.mode = page.values.id_data_kaisha;
                                    page.urls.group += "$filter=cd_kaisha eq " + App.ui.page.user.cd_kaisha;
                                    break;
                                case page.values.id_data_kanrisha: // id_data = 9
                                    page.urls.category += "$filter=flg_gencho ne 1 or flg_gencho eq null";
                                    page.values.mode = page.values.id_data_kanrisha;
                                    break;
                            }
                            break;
                        case page.values.id_kino_edit: // id_kino = 20
                            $("#save").prop('disabled', false);
                            // kengen-disabled is flag disabled button remove
                            $("#remove").removeClass("kengen-disabled");
                            break;
                        case page.values.id_kino_CSV: // id_kino = 30
                            $("#downcsv").prop('disabled', false);
                            break;
                        default:
                            window.location.href = page.urls.mainMenu;
                            break;
                    }
                })
            }).then(function () {
                //TODO: ヘッダー/明細以外の初期化の処理を記述します。
                page.loadMasterData().then(function (result) {

                    return page.loadDialogs();
                }).then(function (result) {
                    //TODO: 画面の初期化処理成功時の処理を記述します。
                }).fail(function (error) {
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

                }).always(function (result) {
                    page.detail.isChanged = false;
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
        page.detail.bind = function (data, isNewData) {

            var dataSet = App.ui.page.dataSet(),
                element = page.detail.element;

            page.detail.data = dataSet;

            page.detail.clear();

            (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(data);
            page.detail.element.form().bind(data);

        };

        /**
         * クリアボタンを活性
         */
        page.commands.clear = function () {

            header = page.header.element;
            detail = page.detail.element;

            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();
            page.loadMasterData();
            header.findP("cd_category").val("");
            header.findP("cd_literal").val("");
            page.detail.clear();
            if (!$("#remove").hasClass("kengen-disabled")) { 
                $("#remove").prop("disabled", true);
                //$("#remove").addClass("kengen-disabled");
            }
            page.header.validator.validate({
                filter: page.header.validationFilter
            });;
            page.detail.validator.validate({
                filter: page.header.validationFilter
            });

            //isChange = false;
        }

        page.detail.clear = function () {
            element = page.detail.element;
            element.findP("nm_literal").val("").prop("disabled", false);
            element.findP("value1").val("").prop("disabled", false);
            element.findP("value2").val("").prop("disabled", false);
            element.findP("no_sort").val("").prop("disabled", false);
            element.findP("biko").val("").prop("disabled", false);
            element.findP("flg_edit").val(App.settings.app.flg_edit[1].value).prop("disabled", false);
            element.findP("cd_group").val("").prop("disabled", false);
            page.detail.isChanged = false;
        }
        /**
        * データの削除処理を実行します。
        */
        page.commands.remove = function () {

            header = page.header.element;
            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();
            App.ui.loading.show()
            var sleep = 0;
            var condition = "Object.keys(page.values.isChangeRunning).length == 0";
            App.ui.wait(sleep, condition, 100).then(function () {
                page.validateAll().then(function () {
                    var options = {
                        text: App.messages.base.MS0003
                    };
                    page.dialogs.confirmDialog.confirm(options).then(function () {

                        var id = page.detail.element.attr("data-key"),
                            entity = page.detail.data.entry(id),
                            changeSets;
                        page.detail.data.remove(entity);
                        changeSets = {
                            ma_literal: page.detail.data.getChangeSet(),
                        }

                        //TODO: データの削除処理をここに記述します。
                        $.ajax(App.ajax.webapi.post(page.header.urls.literalDetail, changeSets)).done(function (result) {
                            App.ui.page.notifyInfo.message(App.messages.base.MS0008).show();
                            header.findP("cd_category").val("");
                            header.findP("cd_literal").val("");
                            page.detail.clear();
                            $("#remove").prop("disabled", true);
                            $("#remove").removeClass("kengen-disabled");

                        }).fail(function (error) {
                            //TODO: データの削除失敗時の処理をここに記述します。
                            App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                        });
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
        * データの保存処理を実行します。
        */
        page.commands.save = function () {
            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();
            header = page.header.element;

            App.ui.loading.show();
            var sleep = 0;
            var condition = "Object.keys(page.values.isChangeRunning).length == 0";
            App.ui.wait(sleep, condition, 100).then(function () {
                page.validateAll().then(function () {
                    var options = {
                        text: App.messages.app.AP0004
                    }
                    page.dialogs.confirmDialog.confirm(options).then(function () {
                        var changeSets = {
                            ma_literal: page.detail.data.getChangeSet(),
                        }
                        if (changeSets.ma_literal.created.length > 0) {
                            $.each(changeSets.ma_literal.created, function (index, item) {
                                item.cd_category = page.header.element.findP("cd_category").val();
                                item.flg_edit = page.detail.element.findP("flg_edit").val();
                            });
                            $.each(changeSets.ma_literal.updated, function (index, item) {
                                item.flg_edit = page.detail.element.findP("flg_edit").val();
                            });
                        };
                        return $.ajax(App.ajax.webapi.post(page.header.urls.literalDetail, changeSets)).then(function (result) {
                            App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                            header.findP("cd_category").val("");
                            header.findP("cd_literal").val("");
                            page.detail.clear();
                            $("#remove").prop("disabled", true);
                            //$("#remove").removeClass("kengen-disabled");
                            page.loadMasterData();
                        }).fail(function (error) {
                            if (error.status === App.settings.base.conflictStatus) {
                                // TODO: 同時実行エラー時の処理を行っています。
                                // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                                App.ui.page.notifyAlert.clear();
                                App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
                                return;
                            }

                            ////TODO: データの保存失敗時の処理をここに記述します。
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

                            ////TODO: データの保存失敗時の処理をここに記述します。
                            App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                        }).always(function () {
                            //$("#remove").prop("disabled", true);
                        });
                    });
                });
            }).always(function () {

                setTimeout(function () {
                    page.header.element.find(":input:first").focus();
                }, 100);
                App.ui.loading.close();
            });
        }
     

        // default detail screen
        page.detail.defaultvalue = function () {
            return {
                nm_literal: "",
                value1: "",
                value2: "",
                no_sort: "",
                biko: "",
                flg_edit: App.settings.app.flg_edit[1].value,
                cd_group: ""
            }
        };

        /**
         * CSV出力を行います。(Download用フォルダに作成されたCSVファイルにアクセス)
         */
        page.commands.downloadCSV = function () {

            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            App.ui.loading.show();
            page.header.validator.validate().then(function () {
                //var options = {
                //    text: App.messages.app.AP0057
                //}
                //page.dialogs.confirmDialog.confirm(options).then(function () {

                    var url = page.header.urls.downloadCSV;

                    // CSV出力（ファイルStreamでダウンロード）
                    return $.ajax(App.ajax.file.download(url, { cd_category: page.header.element.findP("cd_category").val() })).then(function (response, status, xhr) {
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
                //});
            }).always(function () {

                setTimeout(function () {
                    page.header.element.find(":input:first").focus();
                }, 100);
                App.ui.loading.close();
            });
        };
        /**
        * コントロールへのイベントの紐づけを行います。
        */
        page.initializeControlEvent = function () {
            //TODO: 画面全体で利用するコントロールのイベントの紐づけ処理をここに記述します

            $("#clear").on("click", page.commands.clear);
            $("#remove").on("click", page.commands.remove);
            $("#downcsv").on("click", page.commands.downloadCSV);
            $("#save").on("click", page.commands.save);
           
        };
        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function () {

            var flg_edit = page.detail.element.findP("flg_edit");
            flg_edit.children().remove();

            App.ui.appendOptions(
                flg_edit,
                "value",
                "tittle",
                App.settings.app.flg_edit,
                false
            );
            flg_edit.val(App.settings.app.flg_edit[1].value);
            return $.ajax(App.ajax.odata.get(page.urls.category)).then(function (result) {

                var category = page.header.element.findP("cd_category");
                category.children().remove();

                App.ui.appendOptions(
                    category,
                    "cd_category",
                    "nm_category",
                    result.value,
                    true
            );

                var categoryCd = category.val();
                page.header.getLiteral(categoryCd);
                return $.ajax(App.ajax.odata.get(page.urls.group)).then(function (result1) {
                    var group = page.detail.element.findP("cd_group");
                    group.children().remove();
                    App.ui.appendOptions(
                    group,
                   "cd_group",
                   "nm_group",
                   result1.value,
                   true
               );
                });
            });
            //TODO: マスタデータのロード処理を実装後、下の1行を削除してください。
            return App.async.success();
        };
        // リテラルコードの取得処理
        page.header.getLiteral = function (categoryCd) {

            var param = {
                temp: page.values.temp,
                cd_category: categoryCd,
                mode: page.values.mode
            }
            return $.ajax(App.ajax.webapi.get(page.urls.literal, param)).then(function (result) {
                // リテラルコードは3桁で先頭は0で埋める
                for (var i = 0; i < result.length; i++) {
                    result[i].nm_literal = result[i].cd_literal + ':' + result[i].nm_literal;
                }
                var literal = page.header.element.findP("cd_literal");
                literal.children().remove();
                App.ui.appendOptions(
                    literal,
                    "cd_literal",
                    "nm_literal",
                    result,
                   true
                );
            });
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
            cd_category:
               {
                   rules: {
                       required: true
                   },
                   options: {
                       name: "カテゴリ"
                   },
                   messages: {
                       required: App.messages.base.required
                   }
               }
        };

        /**
        * 画面ヘッダーのバリデーションを定義します。
        * SKS Modified 2022/06/09 ADDED
        */
        page.detail.changeValidationRule = function (cd_category) {
            switch (cd_category)
            {
                case App.settings.app.cd_category.kbn_maker:
                case App.settings.app.cd_category.kbn_recycle_hyoji:
                case App.settings.app.cd_category.kbn_zaishitsu:
                    page.detail.options.validations.nm_literal.rules.maxlength = 80;
                    break;
                default:
                    page.detail.options.validations.nm_literal.rules.maxlength = 60;
                    break;
            }
        }

        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.detail.options.validations = {
            //TODO: 画面ヘッダーのバリデーションの定義を記述します。
            nm_literal:
               {
                   rules: {
                       required: true,
                       maxlength: 60
                   },
                   options: {
                       name: "リテラル名"
                   },
                   messages: {
                       required: App.messages.base.required,
                       maxlength: App.messages.base.maxlength
                   }
               },
            value1: {
                rules: {
                    number: true
                },
                options: {
                    name: "リテラル値1"
                },
                messages: {
                    number: App.messages.base.number
                }
            },
            value2: {
                rules: {
                    number: true
                },
                options: {
                    name: "リテラル値2"
                },
                messages: {
                    number: App.messages.base.number
                }
            },
            no_sort:
                {
                    rules: {
                        number: true,
                        required: true,
                        maxlength: 3
                    },
                    options: {
                        name: "表示順",
                    },
                    messages: {
                        number: App.messages.base.number,
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
            },
            flg_edit: {
                rules: {
                    required: true
                },
                options: {
                    name: "編集可否"
                },
                messages: {
                    required: App.messages.base.required
                }
            }
        };

        /**
         * 画面ヘッダーの初期化処理を行います。
         */
        page.header.initialize = function () {

            var element = $(".header");
            page.header.validator = element.validation(page.createValidator(page.header.options.validations));

            element.on("change",":input", page.header.change);

            element.on("change", "[data-prop='cd_literal']", page.header.search);
            page.header.element = element;
            isChange = false;   // 変更フラグ
            //TODO: 画面ヘッダーの初期化処理をここに記述します。
            //TODO: 画面ヘッダーで利用するコントロールのイベントの紐づけ処理をここに記述します。
        };

        /**
        * 画面ヘッダーの初期化処理を行います。
        */
        page.detail.initialize = function () {

            var element = $(".detail");
            page.detail.validator = element.validation(page.createValidator(page.detail.options.validations));

            element.on("change", ":input", page.detail.change);
            page.detail.element = element;

            page.detail.bind({}, true);
        };

        /**
         * 画面明細の一覧の入力項目の変更イベントの処理を行います。
         */
        page.detail.change = function (e) {
            var element = page.detail.element,
              target = $(e.target),
              id = element.attr("data-key"),
              property = target.attr("data-prop"),
              entity = page.detail.data.entry(id),
              data = element.form().data();
              
            entity[property] = data[property];
            page.detail.data.update(entity);

            page.detail.isChanged = false;

            var cd_category = page.header.element.findP("cd_category").val();

            if (!App.isUndefOrNullOrStrEmpty(cd_category)) {
                page.detail.isChanged = true;
            }

            page.detail.validator.validate({
                targets: target
            }).then(function () {
                //var cd_category = page.header.element.findP("cd_category").val();
                //var cd_literal = page.header.element.findP("cd_literal").val();
                //if (App.isUndefOrNullOrStrEmpty(cd_category) || App.isUndefOrNullOrStrEmpty(cd_literal)) {
                //    page.detail.isChanged = false;
                //} else {
                //    page.detail.isChanged = true;
                //}

            });

        }

        /**
         * 画面ヘッダーにある入力項目の変更イベントの処理を行います。
         */
        page.header.change = function (e) {
            var element = page.header.element,
            target = $(e.target),
            property = target.attr("data-prop"),
            val = target.val();

            if (property == "cd_category") {
                page.header.getLiteral(val);
                page.detail.bind(page.detail.defaultvalue(), true);
                page.detail.changeValidationRule(target.val()); // SKS Modified 2022/06/09 ADDED
            }
            if (property == "cd_literal") {
                if (!$("#remove").hasClass("kengen-disabled")) {
                    $("#remove").prop("disabled", (val == ""));
                }
            }            
            // Check if the page is changed
            if (property === "cd_category" || property === "cd_literal") {
                page.detail.isChanged = false;
            }

            page.header.validator.validate({
                targets: target
            });
        };


        /**
         * 画面明細のバリデーションフィルターを設定します。（必須チェックを行わない）
         */
        page.header.validationFilter = function (item, method, state, options) {
            return method !== "required";
        };
        /**
         * 検索処理を定義します。
         */
        page.header.search = function () {
            var criteria = page.header.element.form().data();
            var element = page.detail.element;

            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            page.setColValidStyle(element.find("[data-prop='nm_literal'], [data-prop='no_sort']"));

            //if (!App.isUndefOrNull(criteria.cd_literal)) {
            var param = {
                cd_literal: criteria.cd_literal,
                cd_category: criteria.cd_category
            };
            $.ajax(App.ajax.webapi.get(page.header.urls.literalDetail, param))
            .done(function (result) {
                if (App.isUndefOrNull(result)) {
                    page.detail.bind(page.detail.defaultvalue(), true);
                } else {
                    page.detail.bind(result);
                    if (result.flg_edit == App.settings.app.flg_edit[0].value && App.ui.page.user.cd_kengen != 6) {
                        element.findP("nm_literal").prop("disabled", true);
                        element.findP("value1").prop("disabled", true);
                        element.findP("value2").prop("disabled", true);
                        element.findP("no_sort").prop("disabled", true);
                        element.findP("biko").prop("disabled", true);
                        element.findP("flg_edit").prop("disabled", true);
                        element.findP("cd_group").prop("disabled", true);
                    }
                }
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            });
            //}
        }
        /**
         * 検索条件のフィルターを定義します。
         */
        //page.header.createFilter = function () {
        //    var criteria = page.header.element.form().data(),
        //        filters = [];

        //    /* TODO: 検索条件のフィルターを定義してください。*/
        //    if (!App.isUndefOrNull(criteria.cd_category) && criteria.cd_category.length > 0) {
        //        filters.push("cd_category eq " + criteria.cd_category);
        //    }
        //    if (!App.isUndefOrNull(criteria.cd_literal) && criteria.cd_literal.length > 0) {
        //        filters.push("cd_literal eq " + criteria.cd_literal);
        //    }
        //    return filters.join(" and ");
        //};

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
            <div title="">
                <%-- <table>--%>
                <div class="row">
                    <div class="control-label col-xs-2">
                        <label>カテゴリ<span style="color: red">*</span></label>
                    </div>
                    <div class="control col-xs-10">
                        <select data-prop="cd_category" style="width: 400px;"></select>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-2">
                        <label>リテラル</label>
                    </div>
                    <div class="control col-xs-10">
                        <select data-prop="cd_literal" style="width: 400px;"></select>
                        <%--<input type=text list=browsers style="width: 400px;">
                            <datalist id=browsers data-prop="cd_literal" ></datalist>--%>
                    </div>
                </div>
            </div>
        </div>
        <div class="detail">
            <div class="control col-xs-12">
                <hr />
            </div>
            <div class="row">
                <div class="control-label col-xs-2">
                    <label>リテラル名<span style="color: red">*</span></label>
                </div>
                <div class="control col-xs-10">
                    <input type="text" class="literalDetail" data-prop="nm_literal" style="width: 600px;" />
                </div>
            </div>
            <div class="row">
                <div class="control-label col-xs-2">
                    <label>リテラル値1</label>
                </div>
                <div class="control col-xs-10">
                    <input type="tel" class="literalDetail limit-input-digit comma-number"  data-prop="value1" data-number-format="#,#" style="width: 300px; ime-mode: disabled" maxlength="9" />
                </div>
            </div>
            <div class="row">
                <div class="control-label col-xs-2">
                    <label>リテラル値2</label>
                </div>
                <div class="control col-xs-10">
                    <input type="tel" class="literalDetail limit-input-digit comma-number" data-prop="value2" data-number-format="#,#" style="width: 300px; ime-mode: disabled" maxlength="9" />
                </div>
            </div>
            <div class="row">
                <div class="control-label col-xs-2">
                    <label>表示順<span style="color: red">*</span></label>
                </div>
                <div class="control col-xs-10">
                    <input type="tel" class="literalDetail limit-input-digit" data-prop="no_sort" style="width: 300px; ime-mode: disabled" maxlength="3"/>
                </div>
            </div>

            <div class="row memo">
                <div class=" control-label col-xs-2 memo">
                    <label>備考</label>
                </div>
                <div class=" control col-xs-10 memo">
                    <textarea rows="4" style="width: 600px; height: 100px; resize: none" name="biko_ran" class="literalDetail" data-prop="biko"></textarea>
                </div>
            </div>
            <div class="row">
                <div class="control-label col-xs-2">
                    <label>編集可否<span style="color: red">*</span></label>
                </div>
                <div class="control col-xs-10">
                    <select class="literalDetail" data-prop="flg_edit" style="width: 300px;"></select>
                </div>
            </div>
            <div class="row">
                <div class="control-label col-xs-2">
                    <label>グループ</label>
                </div>
                <div class="control col-xs-10">
                    <select class="literalDetail" data-prop="cd_group" style="width: 400px;"></select>
                </div>
            </div>


        </div>
    </div>

</asp:Content>

<asp:Content ID="FooterContent" ContentPlaceHolderID="FooterContent" runat="server">
    <div class="command-left">
        <!--TODO: コマンドボタン（左寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" id="clear" class="btn btn-sm btn-primary">クリア</button>
        <button type="button" id="downcsv" class="btn btn-sm btn-primary" disabled>CSV</button>

    </div>
    <div class="command">
        <!--TODO: コマンドボタン（右寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" id="save" class="btn btn-sm btn-primary" disabled>保存</button>
        <button type="button" id="remove" class="btn btn-sm btn-default kengen-disabled" disabled>削除</button>
    </div>

</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>
