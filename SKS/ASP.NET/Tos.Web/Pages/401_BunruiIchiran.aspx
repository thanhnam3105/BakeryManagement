<%@ Page Title="401_分類マスタ" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="401_BunruiIchiran.aspx.cs" Inherits="Tos.Web.Pages._401_BunruiIchiran" %>
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
            width: 75px;
        }
    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("_401_BunruiIchiran", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.dataTakeCount,   // TODO:取得するデータ数を指定します。
                filter: ""
            },
            values: {
                isChangeRunning: {},
                isChangeDetail: false,
                noSort: 1,
                isSearch: false,
                transferPage: "../Pages/404_KoshinRireki.aspx",
                idFunctionPage:1
            },
            urls: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
                save: "../api/_401_BunruiIchiran/Post"
            },
            header: {
                options: {},
                values: {
                    currentGate: App.settings.app.no_gate_val.no_gate_1
                },
                urls: {
                    ma_gate_bunrui: "../Services/ShisaQuickService.svc/ma_gate_bunrui?$filter=",
                    search: "../api/_401_BunruiIchiran"
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
         * show page 更新履歴一覧
         */
        page.commands.showHistory = function () {
            var pathLink = page.values.transferPage + "?id_function=" + page.values.idFunctionPage;
            window.open(pathLink);
        }

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
            App.ui.wait(sleep, condition, 100).then(function () {
                page.validateAll().then(function () {
                    page.dialogs.confirmDialog.confirm({ text: App.messages.app.AP0180 }).then(function () {
                        var changeSets = {
                            Bunrui: page.detail.data.getChangeSet(),
                            isSearch:page.values.isSearch
                        }

                        return $.ajax(App.ajax.webapi.post(page.urls.save, changeSets)).then(function (result) {

                            page.values.isChangeDetail = false;
                            return App.async.all([page.header.search(false)]);
                        }).then(function () {
                            App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                        }).fail(function (error) {
                            if (error.status === App.settings.base.conflictStatus) {
                                // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                                page.values.isChangeDetail = false;
                                App.ui.page.notifyAlert.clear();
                                App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
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

            page.loadMasterData().then(function (result) {
                return page.loadDialogs();
            }).then(function (result) {
                page.header.search();
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
            $(".part").part();
        };

        /**
         * コントロールへのイベントの紐づけを行います。
         */
        page.initializeControlEvent = function () {
            $("#save").on("click", page.commands.save);
            $("#historyItem").on("click", page.commands.showHistory);
        };

        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function () {

            var no_gate = page.header.element.findP("no_gate"),
                kbn_disp = page.detail.element.findP("kbn_disp");

            no_gate.children().remove();
            kbn_disp.children().remove();

            App.ui.appendOptions(
                no_gate,
                "value",
                "text",
                App.settings.app.no_gate
            );

            App.ui.appendOptions(
                kbn_disp,
                "value",
                "text",
                App.settings.app.kbn_disp
            );
            return App.async.success();
        };

        /**
         * 共有ダイアログのロード処理を実行します。
         */
        page.loadDialogs = function () {

            return App.async.all({
                confirmDialog: $.get(page.urls.confirmDialog)
            }).then(function (result) {

                $("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;
            });
        }

        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.validations = {
           no_gate: {
                rules: {
                    required: true
                },
                options: {
                    name: "ゲート"
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
            page.header.element = element;

            element.on("change", ":input", page.header.change);

        };

        /**
         * 検索処理を定義します。
         */
        page.header.search = function (isLoading) {

            var deferred = $.Deferred(),
                element = page.header.element,
                query;

            page.header.validator.validate().done(function () {

                page.options.skip = 0;
                page.options.filter = page.header.createFilter();

                if (page.values.isChangeDetail) {

                    page.dialogs.confirmDialog.confirm({ text: App.messages.app.AP0168 }).then(function (result) {

                        page.header.values.currentGate = element.findP("no_gate").val();
                        page.values.isChangeDetail = false;
                        $("#save").prop("disabled", true);
                        searchHeader();

                    }).fail(function () {
                        element.findP("no_gate").val(page.header.values.currentGate);
                        
                    });
                }
                else {
                    searchHeader();
                }

                function searchHeader() {
                    if (isLoading) {
                        App.ui.loading.show();
                    }
                    App.ui.page.notifyAlert.clear();

                    return $.ajax(App.ajax.webapi.get(page.header.urls.search, page.options.filter)).done(function (result) {
                        // パーツ開閉の判断
                        if (page.detail.isClose) {
                            // 検索データの保持
                            page.detail.searchData = result;
                        } else {
                            // データバインド
                            page.detail.bind(result);
                        }
                        page.values.isSearch = true;
                        
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
                no_gate: criteria.no_gate,
                skip: page.options.skip,
                top: page.options.top
            };
            return filter;
        };

        /**
        * 画面ヘッダーの変更時処理を定義します。
        */
        page.header.change = function (e) {

            var element = page.header.element,
                target = $(e.target),
                property = target.attr("data-prop");

            if (!page.values.isChangeDetail) {
                page.header.values.currentGate = element.findP("no_gate").val();
            }
            page.header.search();
        };

        /**
         * 画面明細のバリデーションを定義します。
         */
        page.detail.options.validations = {
            nm_bunrui: {
                rules: {
                    required:true,
                    maxlength: 30
                },
                options: {
                    name: "分類"
                },
                messages: {
                    required: App.messages.base.required,
                    maxlength: App.messages.base.maxlength,
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

            element.on("click", "#nextsearch", page.detail.nextsearch);
            element.on("click", "#add-item", page.detail.insertNewItemAfter);
            element.on("click", "#del-item", page.detail.deleteItem);
            element.on("click", "#up-sort", page.detail.moveUp);
            element.on("click", "#down-sort", page.detail.moveDown);

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
         * 画面明細の一覧に対して、選択行の後に新規データを挿入します。
         */
        page.detail.insertNewItemAfter = function () {
            var newData = {
                //no_seq: page.values.no_seq
            };

            // 新規データを挿入（後）
            page.detail.insertRow(newData, true, true).then(function () {
                if (page.values.isSearch) {
                    page.detail.controlNosort();
                }
                page.values.isChangeDetail = true;

                if ($("#save").is(":disabled")) {
                    $("#save").prop("disabled", false);
                }
            });
            

        };

        /**
        * 画面明細の一覧に、選択行の後に新しい行を挿入します。
        */
        page.detail.insertRow = function (newData, isFocus, isInsertAfter) {
            var element = page.detail.element,
                row,
                selected = element.find(".datatable .select-tab.selected").closest("tbody");

            if (!selected.length) {
                // 選択行が無ければこれまでどおり最終行に行追加
                page.detail.addNewItem();
                return App.async.success();
            }

            page.detail.dataTable.dataTable("getRow", selected, function (rowObject) {
                row = rowObject.element;
            });

            var id = row.attr("data-key"),
                entity = page.detail.data.entry(id);

            newData.no_sort = entity.no_sort + 1;
            page.detail.data.add(newData);
            page.detail.dataTable.dataTable("insertRow", selected, isInsertAfter, function (tbody) {
                tbody.form(page.detail.options.bindOption).bind(newData);

                return tbody;
            }, isFocus);
            return App.async.success();

        };

        /*
       *   Up value sort
       */
        page.detail.moveUp = function (e) {
            var element = page.detail.element,
                selected = element.find(".datatable .select-tab.selected").closest("tbody"),
                rowIdCurr = undefined,
                firstSort = 0;

            if (!selected.length) {
                return;
            }

            if (selected.prev().not(".item-tmpl").length < 1) {
                return;
            }
            var id = selected.attr("data-key"),
                entity = page.detail.data.entry(id);

            var prev1, prev2, tbody;
            page.detail.dataTable.dataTable("getRow", selected, function (rowObject) {
                selected = rowObject.element;
            });

            prev1 = element.find(selected[0]).prev();
            prev2 = element.find(selected[1]).prev();

            var idPrev = prev1.attr("data-key"),
                entityPrev = page.detail.data.entry(idPrev);

            element.find(selected[0]).detach().insertBefore(element.find(prev1));
            element.find(selected[1]).detach().insertBefore(element.find(prev2));

            var newSort = $.extend(true, {}, entity);

            entity["no_sort"] = entityPrev["no_sort"];
            entityPrev["no_sort"] = newSort["no_sort"];
            page.detail.data.update(entity);
            page.detail.data.update(entityPrev);

            $("#save").prop("disabled", false);
            page.values.isChangeDetail = true;
        };

        /*
        *   Down value sort
        */
        page.detail.moveDown = function (e) {
            var element = page.detail.element,
                selected = element.find(".datatable .select-tab.selected").closest("tbody"),
                rowIdCurr = undefined,
                firstSort = 0;

            if (!selected.length) {
                return;
            }

            if (selected.next().not(".item-tmpl").length < 1) {
                return;
            }
            var id = selected.attr("data-key"),
                entity = page.detail.data.entry(id);

            var prev1, prev2, tbody;
            page.detail.dataTable.dataTable("getRow", selected, function (rowObject) {
                selected = rowObject.element;
            });

            prev1 = element.find(selected[0]).next();
            prev2 = element.find(selected[1]).next();

            var idPrev = prev1.attr("data-key"),
                entityPrev = page.detail.data.entry(idPrev);

            element.find(selected[0]).detach().insertAfter(element.find(prev1));
            element.find(selected[1]).detach().insertAfter(element.find(prev2));

            var newSort = $.extend(true, {}, entity);

            entity["no_sort"] = entityPrev["no_sort"];
            entityPrev["no_sort"] = newSort["no_sort"];
            page.detail.data.update(entity);
            page.detail.data.update(entityPrev);

            $("#save").prop("disabled", false);
            page.values.isChangeDetail = true;
        };

        /**
         * 次のレコードを検索する処理を定義します。
         */
        page.detail.nextsearch = function () {

            App.ui.loading.show();
            App.ui.page.notifyAlert.clear();

            page.options.filter = page.header.createFilter();

            return $.ajax(App.ajax.webapi.get(page.header.urls.search, page.options.filter)).done(function (result) {
                page.detail.bind(result);
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            }).always(function () {
                App.ui.loading.close();
            });

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
            page.values.isChangeDetail = true;

            page.detail.executeValidation(target, row).then(function () {
                entity[property] = row.element.form().data()[property];
                entity["no_gate"] = page.header.element.findP("no_gate").val();
                entity["kbn_disp"] = row.element.findP("kbn_disp").val();

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
        page.detail.addNewItem = function (e,row) {
            var newData = {
                no_sort: page.values.noSort
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
                selected = element.find(".datatable .select-tab.selected").closest("tbody");

            if (!selected.length) {
                return;
            }
            page.values.isChangeDetail = true;
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

            if (page.values.isSearch) {
                page.detail.controlNosort();
            }
        };

        /**
         * Control no_sort
         */
        page.detail.controlNosort = function () {
            var no_sort = 1;
            $.each(page.detail.element.find(".datatable").find("tbody").not(".item-tmpl"), function (idx, tbody) {
                var id = $(tbody).attr("data-key"),
                    entity = page.detail.data.entry(id);

                //update all data
                entity["no_sort"] = no_sort;
                page.detail.data.update(entity);
                no_sort++;
            });
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
            <div title="検察条件" class="part">
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>ゲート</label>
                    </div>
                    <div class="control col-xs-2">
                        <select data-prop="no_gate" id="no_gate"></select>
                    </div>
                    <div class="control col-xs-9"></div>
                </div>
            </div>
        </div>
        <!--TODO: 明細を定義するHTMLをここに記述します。-->
        <div class="detail">
                <div class="control-label toolbar">
                    <i class="icon-th"></i>
                    <div class="btn-group">
                        <button type="button" class="btn btn-default btn-xs" id="add-item">新規</button>
                        <button type="button" class="btn btn-default btn-xs" id="del-item">削除</button>
                        <button type="button" class="btn btn-default btn-xs" id="up-sort">移動▲</button>
                        <button type="button" class="btn btn-default btn-xs" id="down-sort">移動▼</button>
                    </div>
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
                            <th style="width: 400px;">表示区分</th>
                            <th>分類</th>
                        </tr>
                    </thead>
                    <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->
                    <tbody class="item-tmpl" style="cursor: default; display: none;">
                        <tr>
						    <td>
                                <span class="select-tab unselected"></span>
                            </td>
                            <td class="center"  style="padding: 2px 4px !important;">
                                <label data-prop="kbn_disp_label" class="overflow-ellipsis"></label>
                                <select data-prop="kbn_disp"></select>
                            </td>
                            <td  style="padding: 2px 4px !important;">
                                <input data-prop="nm_bunrui" type="text" />
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="detail-command">
                    <button type="button" id="nextsearch" class="btn btn-sm btn-primary btn-next-search" style="display:none" >次を検索</button>
                </div>
                <div class="part-command">
                </div>
        </div>
    </div>

</asp:Content>

<asp:Content ID="FooterContent" ContentPlaceHolderID="FooterContent" runat="server">
    <div class="command-left">
    </div>
    <div class="command">
        <button type="button" id="historyItem" class="btn btn-sm btn-primary" >更新履歴</button>
        <button type="button" id="save" class="btn btn-sm btn-primary" disabled="disabled">保存</button>
    </div>

</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>