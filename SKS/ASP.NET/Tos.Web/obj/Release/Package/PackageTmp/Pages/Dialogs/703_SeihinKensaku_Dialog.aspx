<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="703_SeihinKensaku_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs.SeihinKensakuDialog" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchInputDialog(Ver2.1)】 Template--%>

<style>
    /*#SeihinKensakuDialog table.datatable .border_black th {
        border: solid 1px black;
    }*/

    /*#SeihinKensakuDialog table.datatable th {
        background-color: #C8C8FF!important;
    }*/

    #SeihinKensakuDialog table.datatable td button {
        padding: 0 2px 0 2px;
        min-width: 100%!important;
    }
</style>

<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var SeihinKensakuDialog = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.dialogDataTakeCount,   // TODO:取得するデータ数を指定します。
            filter: "",
            kbnKaishatsu: 1,
            kbnKojyo: 2
        },
        values: {
            isChangeRunning: {}
        },
        urls: {
            search: "../api/_703_SeihinKensaku_Dialog"
        },
        header: {
            options: {},
            values: {},
        },
        detail: {
            options: {},
            values: {}
        },
        param: {
            cd_kaisha: null,
            cd_kojyo: null,
            no_gamen: null,
            m_kirikae: null
        }
    };

    /**
     * バリデーション成功時の処理を実行します。
     */
    SeihinKensakuDialog.validationSuccess = function (results, state) {
        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                SeihinKensakuDialog.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                    if (row && row.element) {
                        row.element.find("tr").removeClass("has-error");
                    }
                });
            } else {
                SeihinKensakuDialog.setColValidStyle(item.element);
            }

            SeihinKensakuDialog.notifyAlert.remove(item.element);
        }
    };

    /**
     * バリデーション失敗時の処理を実行します。
     */
    SeihinKensakuDialog.validationFail = function (results, state) {

        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                SeihinKensakuDialog.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                    if (row && row.element) {
                        row.element.find("tr").addClass("has-error");
                    }
                });
            } else {
                SeihinKensakuDialog.setColInvalidStyle(item.element);
            }

            if (state && state.suppressMessage) {
                continue;
            }
            SeihinKensakuDialog.notifyAlert.message(item.message, item.element).show();
        }
    };

    /**
     * バリデーション後の処理を実行します。
     */
    SeihinKensakuDialog.validationAlways = function (results) {
        //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
    };

    /**
      * 指定された定義をもとにバリデータを作成します。
      * @param target バリデーション定義
      * @param options オプションに設定する値。指定されていない場合は、
      *                画面の success/fail/always のハンドル処理が指定されたオプションが設定されます。
      */
    SeihinKensakuDialog.createValidator = function (target, options) {
        return App.validation(target, options || {
            success: SeihinKensakuDialog.validationSuccess,
            fail: SeihinKensakuDialog.validationFail,
            always: SeihinKensakuDialog.validationAlways
        });
    };

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    SeihinKensakuDialog.setColInvalidStyle = function (target) {
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
    SeihinKensakuDialog.setColValidStyle = function (target) {
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
     * すべてのバリデーションを実行します。
     */
    SeihinKensakuDialog.validateAll = function () {

        var validations = [];

        validations.push(SeihinKensakuDialog.detail.validateList());

        //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。

        return App.async.all(validations);
    };

    /**
     * ダイアログの初期化処理を行います。
     */
    SeihinKensakuDialog.initialize = function () {
        var element = $("#SeihinKensakuDialog"),
            contentHeight = $(window).height() * 80 / 100;

        SeihinKensakuDialog.element = element;
        element.find(".modal-body").css("max-height", contentHeight);
        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        SeihinKensakuDialog.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#SeihinKensakuDialog .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body .detail",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        SeihinKensakuDialog.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#SeihinKensakuDialog .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body .detail",
                show: function () {
                    element.find(".alert-message").show();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                }
            });

        element.find(".modal-dialog").draggable({
            drag: true,
        });

        SeihinKensakuDialog.initializeControlEvent();
        SeihinKensakuDialog.header.initialize();
        SeihinKensakuDialog.loadMasterData();
        //SeihinKensakuDialog.loadDialogs();
    };

    /**
     * ダイアログコントロールへのイベントの紐づけを行います。
     */
    SeihinKensakuDialog.initializeControlEvent = function () {
        var element = SeihinKensakuDialog.element;

        //TODO: 画面全体で利用するコントロールのイベントの紐づけ処理をここに記述します。
        element.on("hidden.bs.modal", SeihinKensakuDialog.hidden);
        element.on("shown.bs.modal", SeihinKensakuDialog.shown);
        //element.on("click", ".save", SeihinKensakuDialog.commands.save);
    };

    /**
     * ダイアログ非表示時処理を実行します。
     */
    SeihinKensakuDialog.hidden = function (e) {
        var element = SeihinKensakuDialog.element;

        //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
        element.find(":input:not([type='checkbox']):not([type='radio'])").val("");
        element.find("input[type='checkbox']").prop("checked", false);
        //TODO:radioボタンの初期表示は、画面要件に合わせて記述します。

        SeihinKensakuDialog.detail.dataTable.dataTable("clear");

        element.findP("data_count").text("");
        element.findP("data_count_total").text("");

        var items = element.find(".search-criteria :input:not(button)");
        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            SeihinKensakuDialog.setColValidStyle(item);
        }

        SeihinKensakuDialog.element.find(".save").prop("disabled", true);
        SeihinKensakuDialog.notifyInfo.clear();
        SeihinKensakuDialog.notifyAlert.clear();
    };

    /**
     * ダイアログ表示時処理を実行します。
     */
    SeihinKensakuDialog.shown = function (e) {
        //初回起動時にdatatable作成
        
        if (App.isUndefOrNull(SeihinKensakuDialog.detail.fixedColumnIndex)) {
            SeihinKensakuDialog.detail.initialize();
        }
        SeihinKensakuDialog.header.initialize();
        SeihinKensakuDialog.element.find(":input:not(button):first").focus();
    };

    /**
     * マスターデータのロード処理を実行します。
     */
    SeihinKensakuDialog.loadMasterData = function () {

        //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
        //return $.ajax(App.ajax.odata.get(/* マスターデータ取得サービスの URL */)).then(function (result) {
        //    var cd_shiharai = SeihinKensakuDialog.element.findP("cd_shiharai");
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
     * 共有のダイアログのロード処理を実行します。
     */
    SeihinKensakuDialog.loadDialogs = function () {

        if ($.find("#ConfirmDialog").length == 0) {
            return App.async.all({
                confirmDialog: $.get(SeihinKensakuDialog.urls.confirmDialog)
            }).then(function (result) {
                $("#dialog-container").append(result.successes.confirmDialog);
                SeihinKensakuDialog.dialogs.confirmDialog = ConfirmDialog;
            });
        } else {
            SeihinKensakuDialog.dialogs.confirmDialog = ConfirmDialog;
        }
    };

    /**
     * ダイアログヘッダー　バリデーションルールを定義します。
     */
    SeihinKensakuDialog.header.options.validations = {
        seihin: {
            rules: {
                maxbytelength: 60
            },
            options: {
                name: "名称/コード",
                byte: 30,
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
    };

    /**
     * ダイアログヘッダーの初期化処理を行います。
     */
    SeihinKensakuDialog.header.initialize = function () {

        var element = SeihinKensakuDialog.element.find(".header");
        SeihinKensakuDialog.header.validator = element.validation(SeihinKensakuDialog.createValidator(SeihinKensakuDialog.header.options.validations));
        SeihinKensakuDialog.header.element = element;

        //TODO: 画面ヘッダーの初期化処理をここに記述します。
        //TODO: 画面ヘッダーで利用するコントロールのイベントの紐づけ処理をここに記述します。
        //element.on("click", ".search", SeihinKensakuDialog.header.search);

        element.on("change", SeihinKensakuDialog.change);

        if (SeihinKensakuDialog.param.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu // page no. 203
         || SeihinKensakuDialog.param.no_gamen == App.settings.app.no_gamen.seiho_ichiran) { // page no. 200
            element.find("#show_checkbox").show();
        }
        if (SeihinKensakuDialog.param.no_gamen == App.settings.app.no_gamen.haigo_ichiran // page no. 208
               || SeihinKensakuDialog.param.no_gamen == App.settings.app.no_gamen.haigo_toroku_kojyo) { // page no. 209
            if (SeihinKensakuDialog.param.m_kirikae == App.settings.app.m_kirikae.hyoji) {
                element.find("#show_checkbox").show();
            }
            else if (SeihinKensakuDialog.param.m_kirikae == App.settings.app.m_kirikae.foodprocs) {
                element.find("#show_checkbox").hide();
            }
            else {
                element.find("#show_checkbox").show();
            }
        }
    };

    /**
     * Element on change
     */
    SeihinKensakuDialog.change = function (e) {
        var target = $(e.target);
        SeihinKensakuDialog.header.validator.validate({
            targets: target
        })
    }


    /**
     * ダイアログの検索処理を実行します。
     */
    SeihinKensakuDialog.detail.search = function (isLoading) {
        var element = SeihinKensakuDialog.element,
            loadingTaget = element.find(".modal-content"),
            deferred = $.Deferred(),
            query;

        SeihinKensakuDialog.notifyInfo.clear();

        SeihinKensakuDialog.header.validator.validate().done(function () {

            SeihinKensakuDialog.options.filter = SeihinKensakuDialog.header.createFilter();

            //query = {
            //    url: SeihinKensakuDialog.urls.search,
            //    filter: SeihinKensakuDialog.options.filter,
            //    //orderby: "TODO: ソート対象の列名",
            //    top: SeihinKensakuDialog.options.top,
            //    inlinecount: "allpages"
            //};

            SeihinKensakuDialog.detail.dataTable.dataTable("clear");
            if (isLoading) {
                App.ui.loading.show("", loadingTaget);
                SeihinKensakuDialog.notifyAlert.clear();
            }
            
            $.ajax(App.ajax.webapi.get(SeihinKensakuDialog.urls.search, SeihinKensakuDialog.options.filter))
            .done(function (result) {

                SeihinKensakuDialog.detail.bind(result);
                deferred.resolve();
            }).fail(function (error) {

                SeihinKensakuDialog.notifyAlert.message(App.ajax.handleError(error).message).show();
                deferred.reject();
            }).always(function () {

                if (isLoading) {
                    App.ui.loading.close(loadingTaget);
                }
                if (!element.find(".save").is(":disabled")) {
                    element.find(".save").prop("disabled", true);
                }
            });
        });

        return deferred.promise();
    };

    /**
     * ダイアログの検索条件を組み立てます
     */
    SeihinKensakuDialog.header.createFilter = function () {
        var param = SeihinKensakuDialog.param,
            element = SeihinKensakuDialog.element,
            seihin = element.findP("seihin").val(),
            mode, check = false;
        if (param.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu || param.no_gamen == App.settings.app.no_gamen.seiho_ichiran)
            mode = 1;
        else if ((param.no_gamen == App.settings.app.no_gamen.haigo_toroku_kojyo
                || param.no_gamen == App.settings.app.no_gamen.haigo_ichiran)
                && param.m_kirikae == App.settings.app.m_kirikae.hyoji)
            mode = 2;
        else if ((param.no_gamen == App.settings.app.no_gamen.haigo_toroku_kojyo
                || param.no_gamen == App.settings.app.no_gamen.haigo_ichiran)
                && param.m_kirikae == App.settings.app.m_kirikae.foodprocs)
            mode = 3;
        else
            mode = 0;

        if (element.find("input[type='checkbox']").prop("checked") == false)
            check = false;
        else
            check = true;
        
        //element.find("input[type='checkbox']").prop("checked", false);
        var filter = {
            seihin: seihin,
            check: check,
            mode: mode,
            cd_kaisha: param.cd_kaisha,
            cd_kojyo: param.cd_kojyo,
            top: SeihinKensakuDialog.options.top
        };
        return filter;
    };

    /**
     * ダイアログヘッダー　バリデーションルールを定義します。
     */
    SeihinKensakuDialog.detail.options.validations = {
    };

    /**
     * 画面明細の初期化処理を行います。
     */
    SeihinKensakuDialog.detail.initialize = function () {

        var element = SeihinKensakuDialog.element.find(".detail"),
            table = element.find(".datatable"),
            offsetHeight = $(window).height() * 15 / 100,
            datatable = table.dataTable({
                height: 300,
                resize: true,
                resizeOffset: offsetHeight,
                //列固定横スクロールにする場合は、下記3行をコメント解除
                //fixedColumn: true,               //列固定の指定
                //fixedColumns: 1,                 //固定位置を指定（左端を0としてカウント）
                //innerWidth: 530,                 //可動列の合計幅を指定
                onselect: SeihinKensakuDialog.detail.select,
                onchange: SeihinKensakuDialog.detail.change
            });
        table = element.find(".datatable");        //列固定にした場合DOM要素が再作成されるため、変数を再取得

        SeihinKensakuDialog.detail.validator = element.validation(SeihinKensakuDialog.createValidator(SeihinKensakuDialog.detail.options.validations));
        SeihinKensakuDialog.detail.element = element;
        SeihinKensakuDialog.detail.dataTable = datatable;
        // 行選択時に利用するテーブルインデックスを指定します
        SeihinKensakuDialog.detail.fixedColumnIndex = element.find(".fix-columns").length;

        //TODO: 画面明細で利用するコントロールのイベントの紐づけ処理をここに記述します。
        //element.on("click", ".add-item", SeihinKensakuDialog.detail.addNewItem);
        //element.on("click", ".del-item", SeihinKensakuDialog.detail.deleteItem);
        element.on("click", ".select", SeihinKensakuDialog.detail.selectData);
        element.on("click", ".search", SeihinKensakuDialog.detail.search);

        //TODO: 画面明細の初期化処理をここに記述します。
        //SeihinKensakuDialog.detail.bind([], true);

        if (SeihinKensakuDialog.param.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu     //page no.203
         || SeihinKensakuDialog.param.no_gamen == App.settings.app.no_gamen.seiho_ichiran) {         //page no.200
            element.find(".haigo").hide();
            element.find(".seiho").show();
        }
        if (SeihinKensakuDialog.param.no_gamen == App.settings.app.no_gamen.haigo_ichiran            //page no.208
               || SeihinKensakuDialog.param.no_gamen == App.settings.app.no_gamen.haigo_toroku_kojyo) { //page no.209
            if (SeihinKensakuDialog.param.m_kirikae == App.settings.app.m_kirikae.hyoji) {
                element.find(".haigo").show();
                element.find(".seiho").hide();
            }
            else if (SeihinKensakuDialog.param.m_kirikae == App.settings.app.m_kirikae.foodprocs) {
                element.find(".haigo").show();
                element.find(".seiho").hide();
            }
            else {
                element.find(".haigo").show();
                element.find(".seiho").show();
            }
        }
    };

    /**
     * ダイアログ明細へのデータバインド処理を行います。
     */
    SeihinKensakuDialog.detail.bind = function (data, isNewData) {
        var i, l, item, dataSet, dataCount;

        dataCount = data.Count ? data.Count : 0;
        data = (data.Items) ? data.Items : data;

        dataSet = App.ui.page.dataSet();
        SeihinKensakuDialog.detail.data = dataSet;
        SeihinKensakuDialog.detail.dataTable.dataTable("clear");
        
        SeihinKensakuDialog.detail.dataTable.dataTable("addRows", data, function (row, item) {
            (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
            row.form(SeihinKensakuDialog.detail.options.bindOption).bind(item);
            return row;
        }, true);

        if (!isNewData) {
            SeihinKensakuDialog.detail.element.findP("data_count").text(data.length?data.length:0);
            SeihinKensakuDialog.detail.element.findP("data_count_total").text(dataCount);
        }

        if (dataCount > App.settings.base.maxSearchDataCount) {
            SeihinKensakuDialog.notifyInfo.message(App.str.format(App.messages.app.AP0021, dataCount)).show();
        }

        //if (dataCount == 0) {
        //    SeihinKensakuDialog.notifyInfo.message(App.messages.app.AP0007).show();
        //}

        //TODO: 画面明細へのデータバインド処理をここに記述します。


        //バリデーションを実行します。
        SeihinKensakuDialog.detail.validateList(true);
    };

    /**
     * 明細の各行にデータを設定する際のオプションを定義します。
     */
    SeihinKensakuDialog.detail.options.bindOption = {
        // TODO: 主キーが直接入力の場合には、修正の場合変更を不可とします。
        appliers: {
            cd_seihin: function (value, element) {
                //element.val(App.num.format(Number(value), "000000"));
                //element.text(App.num.format(Number(value), "000000"));
                element.text(App.common.fillString(value, 6));
                return true;
            }
        }
    };

    /**
     * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
     */
    SeihinKensakuDialog.detail.selectData = function (e) {
        var target = $(e.target),
            id, entity,
            selectData = function (entity) {
                if (App.isFunc(SeihinKensakuDialog.dataSelected)) {
                    if (!SeihinKensakuDialog.dataSelected(entity)) {
                        SeihinKensakuDialog.element.modal("hide");
                    }
                } else {
                    SeihinKensakuDialog.element.modal("hide");
                }
            };

        SeihinKensakuDialog.detail.dataTable.dataTable("getRow", target, function (row) {
            id = row.element.attr("data-key");
            entity = SeihinKensakuDialog.detail.data.entry(id);
        });

        if (App.isUndef(id)) {
            return;
        }
        if (SeihinKensakuDialog.detail.data.isUpdated(id)) {
            var options = {
                text: App.messages.base.MS0024
            };
            SeihinKensakuDialog.dialogs.confirmDialog.confirm(options)
                .then(function () {
                    selectData(entity);
                });
        } else {
            selectData(entity);
        }
    };

    /**
     * 明細の一覧の行が選択された時の処理を行います。
     */
    SeihinKensakuDialog.detail.select = function (e, row) {
        $($(row.element[SeihinKensakuDialog.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab.selected")).removeClass("selected").addClass("unselected");
        $(row.element[SeihinKensakuDialog.detail.fixedColumnIndex].querySelectorAll(".select-tab")).removeClass("unselected").addClass("selected");

        //選択行全体に背景色を付ける場合は以下のコメントアウトを解除します。
        //if (!App.isUndefOrNull(SeihinKensakuDialog.detail.selectedRow)) {
        //    SeihinKensakuDialog.detail.selectedRow.element.find(".selected-row").removeClass("selected-row");
        //}
        //row.element.find("tr").addClass("selected-row");
        //SeihinKensakuDialog.detail.selectedRow = row;
    };

    /**
     * 明細の一覧の入力項目の変更イベントの処理を行います。
     */
    SeihinKensakuDialog.detail.change = function (e, row) {
        var target = $(e.target),
            id = row.element.attr("data-key"),
            property = target.attr("data-prop"),
            entity = SeihinKensakuDialog.detail.data.entry(id),
            options = {
                filter: SeihinKensakuDialog.detail.validationFilter
            };

        SeihinKensakuDialog.values.isChangeRunning[property] = true;

        SeihinKensakuDialog.detail.executeValidation(target, row)
        .then(function () {
            entity[property] = row.element.form().data()[property];
            SeihinKensakuDialog.detail.data.update(entity);

            if (SeihinKensakuDialog.element.find(".save").is(":disabled")) {
                SeihinKensakuDialog.element.find(".save").prop("disabled", false);
            }

            //入力行の他の項目のバリデーション（必須チェック以外）を実施します
            SeihinKensakuDialog.detail.executeValidation(row.element.find(":input"), row, options);
        }).always(function () {
            delete SeihinKensakuDialog.values.isChangeRunning[property];
        });
    };

    ///**
    // * 明細の一覧に新規データを追加します。
    // */
    //SeihinKensakuDialog.detail.addNewItem = function () {
    //    //TODO:新規データおよび初期値を設定する処理を記述します。
    //    var newData = {
    //        //no_seq: page.values.no_seq
    //    };

    //    SeihinKensakuDialog.detail.data.add(newData);
    //    SeihinKensakuDialog.detail.dataTable.dataTable("addRow", function (row) {
    //        row.form(SeihinKensakuDialog.detail.options.bindOption).bind(newData);
    //        return row;
    //    }, true);

    //    if (SeihinKensakuDialog.element.find(".save").is(":disabled")) {
    //        SeihinKensakuDialog.element.find(".save").prop("disabled", false);
    //    }
    //};

    ///**
    // * 明細の一覧で選択されている行とデータを削除します。
    // */
    //SeihinKensakuDialog.detail.deleteItem = function (e) {
    //    var element = SeihinKensakuDialog.detail.element,
    //        selected = element.find(".datatable .select-tab.selected").closest("tbody");

    //    if (!selected.length) {
    //        return;
    //    }

    //    SeihinKensakuDialog.detail.dataTable.dataTable("deleteRow", selected, function (row) {
    //        var id = row.attr("data-key"),
    //            newSelected;

    //        row.find(":input").each(function (i, elem) {
    //            SeihinKensakuDialog.notifyAlert.remove(elem);
    //        });

    //        if (!App.isUndefOrNull(id)) {
    //            var entity = SeihinKensakuDialog.detail.data.entry(id);
    //            SeihinKensakuDialog.detail.data.remove(entity);
    //        }

    //        newSelected = row.next().not(".item-tmpl");
    //        if (!newSelected.length) {
    //            newSelected = row.prev().not(".item-tmpl");
    //        }
    //        if (newSelected.length) {
    //            for (var i = SeihinKensakuDialog.detail.fixedColumnIndex; i > -1; i--) {
    //                if ($(newSelected[i]).find(":focusable:first").length) {
    //                    $(newSelected[i]).find(":focusable:first").focus();
    //                    break;
    //                }
    //            }
    //        }
    //    });

    //    if (SeihinKensakuDialog.element.find(".save").is(":disabled")) {
    //        SeihinKensakuDialog.element.find(".save").prop("disabled", false);
    //    }

    //};

    /**
    * 明細のバリデーションを実行します。
    */
    SeihinKensakuDialog.detail.executeValidation = function (targets, row, options) {
        var defaultOptions = {
            targets: targets,
            state: {
                tbody: row,
                isGridValidation: true
            }
        },
            execOptions = $.extend(true, {}, defaultOptions, options);

        return SeihinKensakuDialog.detail.validator.validate(execOptions);
    };

    /**
     * 明細のバリデーションフィルターを設定します。（必須チェックを行わない）
     */
    SeihinKensakuDialog.detail.validationFilter = function (item, method, state, options) {
        return method !== "required";
    };

    /**
     * 明細の一覧全体のバリデーションを実行します。
     */
    SeihinKensakuDialog.detail.validateList = function (suppressMessage) {
        var validations = [],
            options = {
                state: {
                    suppressMessage: suppressMessage,
                }
            };

        SeihinKensakuDialog.detail.dataTable.dataTable("each", function (row, index) {
            validations.push(SeihinKensakuDialog.detail.executeValidation(row.element.find(":input"), row.element, options));
        });

        return App.async.all(validations);
    };

</script>

<div class="modal fade wide" tabindex="-1" id="SeihinKensakuDialog">
    <div class="modal-dialog" style="max-height: 85%; width: 50%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">製品検索</h4>
            </div>

            <div class="modal-body">
                <div class="modal-wrap">
                    <div class="header">
                        <div class="search-criteria">
                            <div class="row">
                                <div class="control-label col-xs-3" style="text-align: right;">
                                    <label>名称/コード</label>
                                </div>
                                <div class="control col-xs-5">
                                    <input type="text" data-prop="seihin" />
                                </div>
                                <div class="control col-xs-4">
                                    <%--<button type="button" class="btn btn-xs btn-default btn-primary search">検索</button>--%>
                                </div>
                            </div>

                            <div class="row" id="show_checkbox">
                                <div class="control-label col-xs-3">
                                </div>
                                <div class="control col-xs-9">
                                    <label>
                                        <input class="checkbox-inline " type="checkbox" data-prop="check_haigo" />
                                        <span>配合未登録製品のみ対象</span>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="detail">
                        <%--<div class="control-label toolbar">
                            <span class="data-count">
                                <span class="data-count-kojyo">該当件数は </span>
                                <span data-prop="data_count"></span>
                                <span>/</span>
                                <span data-prop="data_count_total"></span>
                                <span class="data-count-kojyo">件</span>
                            </span>
                        </div>--%>
                        <div style="position: relative; height: 50px;">
                            <button type="button" style="position: absolute; right: 5px; top: 5px;" class="btn btn-sm btn-primary search" value="">検索</button>
                            <div class="data-count">
                                <span data-prop="data_count"></span>
                                <span>/</span>
                                <span data-prop="data_count_total"></span>
                            </div>
                        </div>

                        <%--<div class="dialog-table">--%>
                        <table class="datatable">
                            <thead class="border_black">
                                <tr>
                                    <th style="width: 50px;"></th>
                                    <th style="width: 80px;">製品コード</th>
                                    <th>製品名</th>
                                    <th style="width: 150px;">荷姿</th>
                                    <th style="width: 150px;" class="seiho">製法番号</th>
                                    <th style="width: 100px;" class="haigo">配合コード</th>
                                    <th style="width: 150px;" class="haigo">配合名</th>
                                </tr>
                            </thead>
                            <%--</table>--%>
                            <%--</div>--%>
                            <%--<div style="height: 252px; overflow: scroll; overflow-x: hidden;">--%>
                            <%--<table class="table table-striped table-condensed search-list" id="detail">--%>
                            <tbody class="item-tmpl" style="cursor: default; display: none;">
                                <tr>
                                    <td>
                                        <button type="button" style="padding: 1px;" class="btn btn-success btn-xs select">選択</button>
                                    </td>
                                    <td>
                                        <label class="overflow-ellipsis" data-prop="cd_seihin"></label>
                                    </td>
                                    <td style="">
                                        <label class="overflow-ellipsis" data-prop="nm_seihin"></label>
                                    </td>
                                    <td>
                                        <label class="overflow-ellipsis" data-prop="nisugata_hyoji"></label>
                                    </td>
                                    <td class="seiho">
                                        <label class="overflow-ellipsis" data-prop="no_seiho"></label>
                                    </td>
                                    <td class="haigo">
                                        <label class="overflow-ellipsis" data-prop="cd_haigo_hyoji"></label>
                                    </td>
                                    <td class="haigo">
                                        <label class="overflow-ellipsis" data-prop="nm_haigo"></label>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <%-- </div>--%>
                    </div>
                </div>
            </div>

            <div class="modal-message message-area dialog-slideup-area">
                <div class="alert-message" style="display: none">
                    <ul>
                    </ul>
                </div>
                <div class="info-message" style="display: none">
                    <ul>
                    </ul>
                </div>
            </div>

            <div class="modal-footer">
                <%--<button type="button" class="btn btn-success save" name="save" disabled="disabled">保存</button>--%>
                <%--<button type="button" class="cancel-button btn btn-sm btn-default" data-dismiss="modal" name="close">閉じる</button>--%>
                <button type="button" class="cancel-button btn btn-sm btn-default " data-dismiss="modal" name="close">閉じる</button>
            </div>

        </div>
    </div>
</div>
