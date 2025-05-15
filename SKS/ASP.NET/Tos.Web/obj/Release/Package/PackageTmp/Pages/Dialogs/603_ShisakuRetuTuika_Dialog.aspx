<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="603_ShisakuRetuTuika_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs.ShisakuRetuTuika_Dialog" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchInputDialog(Ver2.1)】 Template--%>
<style type="text/css">
    table .datatable {
        margin-right: 0px !important;
    }

    .th-tmpl {
        display: none;
    }

    #ShisakuRetuTuikaDialog .search-list {
        width: 225px;
    }

    .hidden {
        display: none;
    }

    #ShisakuRetuTuikaDialog .font_size_12 {
        font-size: 12px;
    }

    #ShisakuRetuTuikaDialog input[type="text"] {
        height: 24px;
    }
</style>
<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var ShisakuRetuTuikaDialog = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.maxInputDataCount,   // TODO:取得するデータ数を指定します。
            filter: ""
        },
        values: {
            isChangeRunning: {},
            lstheader: [],
            lstbody: [],
            lstformula: [],
            lstformulanew : [],
            lstformulasample : [],
            lstLenght: [],
            dataMaster: {
                kotei: [],
                shisaku: [],
                keta_shosu : 0,
                maxCol: 1
            },
            resultData: {},
            numberOverflow: false,
            stringOverflow: false,
            beforeShisaku: undefined
        },
        urls: {},
        header: {
            options: {},
            values: {},
        },
        detail: {
            options: {},
            values: {}
        },
        checkUndefi: null,
        commands: {},
        dialogs: {}
    };

    /**
     * バリデーション成功時の処理を実行します。
     */
    ShisakuRetuTuikaDialog.validationSuccess = function (results, state) {
        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                ShisakuRetuTuikaDialog.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                    if (row && row.element) {
                        row.element.find("tr").removeClass("has-error");
                    }
                });
            } else {
                ShisakuRetuTuikaDialog.setColValidStyle(item.element);
            }

            ShisakuRetuTuikaDialog.notifyAlert.remove(item.element);
        }
    };

    /**
     * バリデーション失敗時の処理を実行します。
     */
    ShisakuRetuTuikaDialog.validationFail = function (results, state) {

        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                ShisakuRetuTuikaDialog.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                    if (row && row.element) {
                        row.element.find("tr").addClass("has-error");
                    }
                });
            } else {
                ShisakuRetuTuikaDialog.setColInvalidStyle(item.element);
            }

            if (state && state.suppressMessage) {
                continue;
            }
            ShisakuRetuTuikaDialog.notifyAlert.message(item.message, item.element).show();
        }
    };

    /**
     * バリデーション後の処理を実行します。
     */
    ShisakuRetuTuikaDialog.validationAlways = function (results) {
        //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
    };

    /**
      * 指定された定義をもとにバリデータを作成します。
      * @param target バリデーション定義
      * @param options オプションに設定する値。指定されていない場合は、
      *                画面の success/fail/always のハンドル処理が指定されたオプションが設定されます。
      */
    ShisakuRetuTuikaDialog.createValidator = function (target, options) {
        return App.validation(target, options || {
            success: ShisakuRetuTuikaDialog.validationSuccess,
            fail: ShisakuRetuTuikaDialog.validationFail,
            always: ShisakuRetuTuikaDialog.validationAlways
        });
    };

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    ShisakuRetuTuikaDialog.setColInvalidStyle = function (target) {
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
    ShisakuRetuTuikaDialog.setColValidStyle = function (target) {
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
    ShisakuRetuTuikaDialog.validateAll = function () {

        var validations = [];

        validations.push(ShisakuRetuTuikaDialog.detail.validateList());

        //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。

        return App.async.all(validations);
    };

    /**
     * ダイアログの初期化処理を行います。
     */
    ShisakuRetuTuikaDialog.initialize = function () {

        var element = $("#ShisakuRetuTuikaDialog"),
            contentHeight = $(window).height() * 80 / 100;

        ShisakuRetuTuikaDialog.element = element;
        element.find(".modal-body").css("max-height", contentHeight);
        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        ShisakuRetuTuikaDialog.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#ShisakuRetuTuikaDialog .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body .detail",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        ShisakuRetuTuikaDialog.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#ShisakuRetuTuikaDialog .dialog-slideup-area .alert-message",
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

        ShisakuRetuTuikaDialog.initializeControlEvent();
        ShisakuRetuTuikaDialog.header.initialize();
        //ShisakuRetuTuikaDialog.loadMasterData();
        ShisakuRetuTuikaDialog.loadDialogs();
    };

    /**
     * ダイアログコントロールへのイベントの紐づけを行います。
     */
    ShisakuRetuTuikaDialog.initializeControlEvent = function () {
        var element = ShisakuRetuTuikaDialog.element;

        //TODO: 画面全体で利用するコントロールのイベントの紐づけ処理をここに記述します。
        element.on("hidden.bs.modal", ShisakuRetuTuikaDialog.hidden);
        element.on("shown.bs.modal", ShisakuRetuTuikaDialog.shown);
        element.on("click", ".add-item", ShisakuRetuTuikaDialog.commands.addNewItem);
        element.on("click", ".apply-item", ShisakuRetuTuikaDialog.commands.applyItem);
    };

    /**
     * ダイアログ非表示時処理を実行します。
     */
    ShisakuRetuTuikaDialog.hidden = function (e) {
        ShisakuRetuTuikaDialog.values.beforeShisaku = undefined;

        var element = ShisakuRetuTuikaDialog.element;

        //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
        element.find(":input:not([type='checkbox']):not([type='radio']):not([disabled])").val("");
        element.find(":input[data-prop='PlusOrMinus']").val("+");

        //element.find("input[type='checkbox']").prop("checked", false);
        //TODO:radioボタンの初期表示は、画面要件に合わせて記述します。

        //ShisakuRetuTuikaDialog.detail.dataTable.dataTable("clear");
        var element = $("#ShisakuRetuTuikaDialog").find(".search-list"),
            lstTmp = element.find(".addNew");
        $.each(lstTmp, function (ind, cell) {
            cell = $(cell);
            cell.remove();
        });
        ShisakuRetuTuikaDialog.header.element.findP("allStage").prop("checked", true);
        ShisakuRetuTuikaDialog.element.find(".detail").find(".search-list").css("width", 225);

        var items = element.find(".search-criteria :input:not(button)");
        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            ShisakuRetuTuikaDialog.setColValidStyle(item);
        }

        if (!$(".modal.in").length) {
            $(".modal-dialog").css({
                top: 0,
                left: 0
            });
        }

        ShisakuRetuTuikaDialog.notifyInfo.clear();
        ShisakuRetuTuikaDialog.notifyAlert.clear();
    };

    /**
     * ダイアログ表示時処理を実行します。
     */
    ShisakuRetuTuikaDialog.shown = function (e) {
        //初回起動時にdatatable作成
        if (App.isUndefOrNull(ShisakuRetuTuikaDialog.detail.fixedColumnIndex)) {
            ShisakuRetuTuikaDialog.detail.initialize();
        }
        if (!App.isUndefOrNull(ShisakuRetuTuikaDialog.values.dataMaster)) {
            var result = ShisakuRetuTuikaDialog.values.dataMaster.kotei;
            //sort result by sort_kotei
            result.sort((a,b) => (parseInt(a.sort_kotei) > parseInt(b.sort_kotei)) ? 1 : ((parseInt(b.sort_kotei) > parseInt(a.sort_kotei)) ? -1 : 0));
            //result.sort(function (a, b) { return a.sort_kotei.localeCompare(b.sort_kotei); });
            result[0].MultiplyOrDivide = "x";
            result[0].number = "0.0";
            result[0].all = "全工程";
        }
        ShisakuRetuTuikaDialog.loadMasterData();
        ShisakuRetuTuikaDialog.detail.bind(result);
        ShisakuRetuTuikaDialog.element.find(":input:not(button):first").focus();
    };

    /**
     * マスターデータのロード処理を実行します。
     */
    ShisakuRetuTuikaDialog.loadMasterData = function () {
        var result = ShisakuRetuTuikaDialog.values.dataMaster.shisaku;

        var seq_shisaku = ShisakuRetuTuikaDialog.element.findP("seq_shisaku");
        seq_shisaku.children().remove();
        
        App.ui.appendOptions(
            seq_shisaku,
            "seq_shisaku",
            "nm_sample",
            result,
            false
        );
        
        $.each($(seq_shisaku).find("option"), function (ind, val) {
            var value = $(val).text();
            if (value == "null") {
                $(val).text("");
            }
        });
        //TODO: マスタデータのロード処理を実装後、下の1行を削除してください。
        return App.async.success();
    };

    /**
     * 共有のダイアログのロード処理を実行します。
     */
    ShisakuRetuTuikaDialog.loadDialogs = function () {

        if ($.find("#ConfirmDialog").length == 0) {
            return App.async.all({
                confirmDialog: $.get(ShisakuRetuTuikaDialog.urls.confirmDialog)
            }).then(function (result) {
                $("#dialog-container").append(result.successes.confirmDialog);
                ShisakuRetuTuikaDialog.dialogs.confirmDialog = ConfirmDialog;
            });
        } else {
            ShisakuRetuTuikaDialog.dialogs.confirmDialog = ConfirmDialog;
        }
    };

    /**
     * ダイアログヘッダー　バリデーションルールを定義します。
     */
    ShisakuRetuTuikaDialog.header.options.validations = {
    };

    /**
     * ダイアログヘッダーの初期化処理を行います。
     */
    ShisakuRetuTuikaDialog.header.initialize = function () {

        var element = ShisakuRetuTuikaDialog.element.find(".header");
        ShisakuRetuTuikaDialog.header.validator = element.validation(ShisakuRetuTuikaDialog.createValidator(ShisakuRetuTuikaDialog.header.options.validations));
        ShisakuRetuTuikaDialog.header.element = element;

        //TODO: 画面ヘッダーの初期化処理をここに記述します。
        //TODO: 画面ヘッダーで利用するコントロールのイベントの紐づけ処理をここに記述します。
        element.on("change", ":input", ShisakuRetuTuikaDialog.header.change);
    };

    ShisakuRetuTuikaDialog.header.change = function (e) {
        var element = ShisakuRetuTuikaDialog.header.element,
            target = $(e.target),
            property = target.attr("data-prop");
        var elementDetail = $("#ShisakuRetuTuikaDialog").find(".search-list"),
            lsttbody = elementDetail.find("tbody").not(".item-tmpl");

        if (property == "allStage") {            
            
            ShisakuRetuTuikaDialog.notifyAlert.remove("AP0009");
            ShisakuRetuTuikaDialog.notifyAlert.remove("AP0046");

            $.each(lsttbody, function (ind, row) {
                row = $(row);
                if (ind == 0) {
                    row.findP("all").removeClass("hidden");
                    row.findP("nm_kotei").addClass("hidden");
                    row.findP("number").val("0.0");
                    row.findP("MultiplyOrDivide").val("x");
                } else {
                    row.addClass("hidden");
                    row.findP("nm_kotei").addClass("hidden");
                    row.findP("number").val("0.0");
                    row.findP("MultiplyOrDivide").val("x");
                }
            });
        }
        if (property == "nm_kotei") {
            
            ShisakuRetuTuikaDialog.notifyAlert.remove("AP0009");
            ShisakuRetuTuikaDialog.notifyAlert.remove("AP0046");

            $.each(lsttbody, function (ind, row) {
                row = $(row);
                row.removeClass("hidden");
                row.findP("all").addClass("hidden");
                row.findP("nm_kotei").removeClass("hidden");
                row.findP("number").val("0.0");
                row.findP("MultiplyOrDivide").val("x");
            });
        }
        ShisakuRetuTuikaDialog.detail.validateList(true);
    };

    /**
     * ダイアログヘッダー　バリデーションルールを定義します。
     */
    ShisakuRetuTuikaDialog.detail.options.validations = {
        number: {
            rules: {
                number: true,
                pointlength: [3, 5],
                min: 0
            },
            options: {
                name: "計算値"
            },
            messages: {
                number: App.messages.base.number,
                pointlength: App.messages.base.pointlength,
                min: App.messages.base.min
            }
        }
    };

    /**
     * 画面明細の初期化処理を行います。
     */
    ShisakuRetuTuikaDialog.detail.initialize = function () {

        var element = ShisakuRetuTuikaDialog.element.find(".detail"),
            table = element.find(".search-list"),
            offsetHeight = $(window).height() * 15 / 100,
            datatable = table.dataTable({
                height: 250,
                //resize: true,
                resizeOffset: offsetHeight,
                //列固定横スクロールにする場合は、下記3行をコメント解除
                fixedColumn: false,               //列固定の指定
                //fixedColumns: 1,                 //固定位置を指定（左端を0としてカウント）
                //innerWidth: 530,                 //可動列の合計幅を指定
                onselect: ShisakuRetuTuikaDialog.detail.select,
                onchange: ShisakuRetuTuikaDialog.detail.change
            });
        table = element.find(".search-list");        //列固定にした場合DOM要素が再作成されるため、変数を再取得
        table.css("width", 225);

        ShisakuRetuTuikaDialog.detail.validator = element.validation(ShisakuRetuTuikaDialog.createValidator(ShisakuRetuTuikaDialog.detail.options.validations));
        ShisakuRetuTuikaDialog.detail.element = element;
        ShisakuRetuTuikaDialog.detail.dataTable = datatable;
        // 行選択時に利用するテーブルインデックスを指定します
        ShisakuRetuTuikaDialog.detail.fixedColumnIndex = element.find(".fix-columns").length;

        ShisakuRetuTuikaDialog.detail.bind([], true);
    };

    /**
     * ダイアログ明細へのデータバインド処理を行います。
     */
    ShisakuRetuTuikaDialog.detail.bind = function (data, isNewData) {
        var i, l, item, dataSet, dataCount;

        data = (data.Items) ? data.Items : data;

        dataSet = App.ui.page.dataSet();
        ShisakuRetuTuikaDialog.detail.data = dataSet;
        ShisakuRetuTuikaDialog.detail.dataTable.dataTable("clear");

        ShisakuRetuTuikaDialog.detail.dataTable.dataTable("addRows", data, function (row, item) {

            (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
            //if (parseInt(item.nm_kotei) > 1) {
            //    row.addClass("hidden");
            //}
            if (App.isUndefOrNull(item.all)) {
                row.addClass("hidden");
            }
            item.nm_kotei = item.sort_kotei;
            row.form().bind(item);
            return row;
        }, true);



        //バリデーションを実行します。
        ShisakuRetuTuikaDialog.detail.validateList(true);
    };

    /**
     * 明細の各行にデータを設定する際のオプションを定義します。
     */
    ShisakuRetuTuikaDialog.detail.options.bindOption = {
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
     * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
     */
<%--        ShisakuRetuTuikaDialog.detail.selectData = function (e) {
            var target = $(e.target),
                id, entity,
                selectData = function (entity) {
                    if (App.isFunc(ShisakuRetuTuikaDialog.dataSelected)) {
                        if (!ShisakuRetuTuikaDialog.dataSelected(entity)) {
                            ShisakuRetuTuikaDialog.element.modal("hide");
                        }
                    } else {
                        ShisakuRetuTuikaDialog.element.modal("hide");
                    }
                };

            ShisakuRetuTuikaDialog.detail.dataTable.dataTable("getRow", target, function (row) {
                id = row.element.attr("data-key");
                entity = ShisakuRetuTuikaDialog.detail.data.entry(id);
            });

            if (App.isUndef(id)) {
                return;
            }
            if (ShisakuRetuTuikaDialog.detail.data.isUpdated(id)) {
                var options = {
                    text: App.messages.base.MS0024
                };
                ShisakuRetuTuikaDialog.dialogs.confirmDialog.confirm(options)
                    .then(function () {
                        selectData(entity);
                    });
            } else {
                selectData(entity);
            }
        }; --%>

    /**
     * 明細の一覧の行が選択された時の処理を行います。
     */
    ShisakuRetuTuikaDialog.detail.select = function (e, row) {
        $($(row.element[ShisakuRetuTuikaDialog.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab.selected")).removeClass("selected").addClass("unselected");
        $(row.element[ShisakuRetuTuikaDialog.detail.fixedColumnIndex].querySelectorAll(".select-tab")).removeClass("unselected").addClass("selected");

        //選択行全体に背景色を付ける場合は以下のコメントアウトを解除します。
        //if (!App.isUndefOrNull(ShisakuRetuTuikaDialog.detail.selectedRow)) {
        //    ShisakuRetuTuikaDialog.detail.selectedRow.element.find(".selected-row").removeClass("selected-row");
        //}
        //row.element.find("tr").addClass("selected-row");
        //ShisakuRetuTuikaDialog.detail.selectedRow = row;
    };

    /**
     * 明細の一覧の入力項目の変更イベントの処理を行います。
     */
    ShisakuRetuTuikaDialog.detail.change = function (e, row) {
        var target = $(e.target),
            id = row.element.attr("data-key"),
            property = target.attr("data-prop"),
            entity = ShisakuRetuTuikaDialog.detail.data.entry(id),
            options = {
                filter: ShisakuRetuTuikaDialog.detail.validationFilter
            };
        //if (property == "number") {
        //    if (target.val() == "" || !$.isNumeric(target.val())) {
        //        target.val("0.0");
        //    } else {
        //        var quantity = (Math.round(Number(Math.abs(target.val())) * 10000) / 10000);
        //        target.val(quantity.toFixed(1));
        //    }
        //}
        ShisakuRetuTuikaDialog.values.isChangeRunning[property] = true;

        ShisakuRetuTuikaDialog.detail.executeValidation(target, row)
        .then(function () {
            entity[property] = row.element.form().data()[property];
            ShisakuRetuTuikaDialog.detail.data.update(entity);

            //if (ShisakuRetuTuikaDialog.element.find(".save").is(":disabled")) {
            //    ShisakuRetuTuikaDialog.element.find(".save").prop("disabled", false);
            //}

            //入力行の他の項目のバリデーション（必須チェック以外）を実施します
            ShisakuRetuTuikaDialog.detail.executeValidation(row.element.find(":input"), row, options);
        }).always(function () {
            delete ShisakuRetuTuikaDialog.values.isChangeRunning[property];
        });
    };
    
    /**    
     * count columns for allStage mode
     */
    ShisakuRetuTuikaDialog.checkColAllStage = function(){
        var allStage = $("#ShisakuRetuTuikaDialog").findP("allStage").is(":checked");

        ShisakuRetuTuikaDialog.notifyAlert.remove("AP0046");

        if(!allStage){
            return true;
        }
    
        var element = $("#ShisakuRetuTuikaDialog").find(".search-list"),
                lstTmp = element.find(".th-tmpl"), maxIndex, flg_err = false;


        $.each(lstTmp, function (ind, cell) {
            cell = $(cell);
            var row = cell.closest("tr");
            if (row.hasClass("spe-pros") && row.closest(".dt-head").length) {
                maxIndex = parseInt(row.find("th:visible:last").text());
                if (maxIndex > 10) {
                    flg_err = true;
                }
            }
        });

        if (flg_err) {
            ShisakuRetuTuikaDialog.notifyAlert.message(App.messages.app.AP0046, "AP0046").show();
            return false;
        }
        return true;

    };

    /**
     * Add new columns
     */
    ShisakuRetuTuikaDialog.commands.addNewItem = function () {
        var element = $("#ShisakuRetuTuikaDialog").find(".search-list"),
            lstTmp = element.find(".th-tmpl"), maxIndex, flg_err = false;

        var allStage = $("#ShisakuRetuTuikaDialog").findP("allStage").is(":checked");//2020-07-01 : Bug #18153 : 全工程(連動あり)の場合に列追を１０列以上チェックする。

        $.each(lstTmp, function (ind, cell) {
            cell = $(cell);
            var row = cell.closest("tr"),
                clone = cell.clone().removeClass("th-tmpl").addClass("addNew");
            clone.css("text-align", "left");
            if (row.hasClass("spe-pros") && row.closest(".dt-head").length) {
                maxIndex = parseInt(row.find("th:visible:last").text());
                maxIndex++;
                if (maxIndex > 10 && allStage) {//2020-07-01 ： Bug #18153 : 全工程(連動あり)の場合に列追を１０列以上チェックする。
                    flg_err = true;
                }
                clone.text(maxIndex.toString());
            }
            if (!flg_err) {
                clone.appendTo(row);
            }
        });
        element.css("text-align", "left");
        if (!flg_err) {
            element.css("width", maxIndex * 225);
        } else {

            ShisakuRetuTuikaDialog.notifyAlert.message(App.messages.app.AP0046, "AP0046").show();
        }
    };

    /**
     * return data
     */
    ShisakuRetuTuikaDialog.commands.applyItem = function (e) {

        ShisakuRetuTuikaDialog.notifyAlert.clear();
        ShisakuRetuTuikaDialog.notifyInfo.clear();
        App.ui.loading.show();

        setTimeout(function(){        

            //2020-07-01 ： START : Bug #18153 : 全工程(連動あり)の場合に列追を１０列以上チェックする。
            var isContinute = ShisakuRetuTuikaDialog.checkColAllStage();
            if(!isContinute){
                App.ui.loading.close();        
                return;
            }
            //2020-07-01 ： END : Bug #18153 : 全工程(連動あり)の場合に列追を１０列以上チェックする。

            ShisakuRetuTuikaDialog.validateAll().then(function () {
                //App.ui.loading.show();
                var elementHeader = $("#ShisakuRetuTuikaDialog").find(".header"),
                elementDetail_Header = $("#ShisakuRetuTuikaDialog").find(".dt-head").find(".search-list").find("thead"),
                lstheader = elementDetail_Header.find("tr").not(".spe-pros").find("th").not(".th-tmpl"),
                elementDetail_Body = $("#ShisakuRetuTuikaDialog").find(".dt-body").find(".search-list").find("tbody").not(".item-tmpl"),
                lstbody = elementDetail_Body.find("tr").find("td").not(".th-tmpl"),
                checkReceipe = false;
                ShisakuRetuTuikaDialog.values.numberOverflow = false;
                ShisakuRetuTuikaDialog.values.stringOverflow = false;

                ShisakuRetuTuikaDialog.values.lstheader = []; //seq_shisaku and PlusOrMinus
                ShisakuRetuTuikaDialog.values.lstbody = []; //MultiplyOrDivide, number, cd_kotei
                ShisakuRetuTuikaDialog.values.lstformula = []; //formula
                ShisakuRetuTuikaDialog.values.lstformulanew = []; //formula new
                ShisakuRetuTuikaDialog.values.lstformulasample = []; //formula name sample
                ShisakuRetuTuikaDialog.values.lstLenght = [];

                //get list column and Plus_Or_Minus
                $.each(lstheader, function (ind, cell) {
                    cell = $(cell);
                    var header = {
                        seq_shisaku: cell.findP("seq_shisaku").val() == "" ? 0 : cell.findP("seq_shisaku").val(),
                        PlusOrMinus: cell.findP("PlusOrMinus").val()
                    }
                    ShisakuRetuTuikaDialog.values.lstheader.push(header);
                });

                var index = 0;
                var bodyList = [];

                //get list Multiply_Or_Divide, number, cd_kotei according to kotei 
                $.each(lstbody, function (ind, cell) {
                    cell = $(cell);
                    var body = {
                        MultiplyOrDivide: cell.findP("MultiplyOrDivide").val(),
                        number: cell.findP("number").val().replace(/,/g, ''),
                        cd_kotei: cell.findP("cd_kotei").val()
                    };
                    if (body.MultiplyOrDivide == "/" && (Number(body.number) == 0)) {
                        ShisakuRetuTuikaDialog.notifyAlert.message(App.messages.app.AP0138, "AP0138").show();
                        App.ui.loading.close();
                        checkReceipe = true;
                    }
                    
                    bodyList.push(body);
                    index++;
                    if (index == ShisakuRetuTuikaDialog.values.lstheader.length) {
                        ShisakuRetuTuikaDialog.values.lstbody.push(bodyList);
                        index = 0;
                        bodyList = [];
                    }
                });
                //check receipe / = 0
                if (!checkReceipe) {
                    var listHeader = ShisakuRetuTuikaDialog.values.lstheader,
                        listBody = ShisakuRetuTuikaDialog.values.lstbody,
                        listBodyLength = listBody.length,
                        lengthBodySub = listBody[0].length,
                        formula = "",
                        formula_new = "",
                        formula_sample = "",
                        recipe = "",
                        recipe_new = "",
                        recipe_sample = "",
                        listDataJuryoShiagari = {},
                        totalDataJuryoShiagari = 0,
                        juryo_shiagari_g = 0;

                    var isKoteiSiki = true;//2020-07-01 : Bug #18153 : 試作列コピー時に連動なしでコピーを行おうとするだけとエラーが発生する

                    //Get the recipe
                    if (elementHeader.findP("allStage").is(":checked")) {
                        //全工程
                        ShisakuRetuTuikaDialog.values.resultData['flg_keisan'] = 0;
                        for (var i = 0; i < listBodyLength ; i++) {
                            var total = 0,
                                recipeWeight = "",
                                valueItem = {},
                                number = "";
                            for (var j = 0; j < lengthBodySub; j++) {
                                number = listBody[i][j].number == "" ? "1" : listBody[i][j].number;
                                numberAll = listBody[0][j].number == "" ? "1" : listBody[0][j].number;
                                formula = formula + "({col-" + listHeader[j].seq_shisaku + "}" + listBody[i][j].MultiplyOrDivide + (listBody[i][j].MultiplyOrDivide == "/" ? (number != "0.0" ? number : 1) : number) + ")";
                                if(listBody[i][j].number !== ""){
                                    formula_sample = formula_sample + "{col-" + listHeader[j].seq_shisaku + "}" + listBody[i][j].MultiplyOrDivide + (listBody[i][j].MultiplyOrDivide == "/" ? (number != "0.0" ? number : 1) : number);
                                }else{
                                    formula_sample = formula_sample + "{col-" + listHeader[j].seq_shisaku + "}"
                                }
                                formula = formula + listHeader[j].PlusOrMinus;
                                formula_sample = formula_sample + listHeader[j].PlusOrMinus;
                                recipeWeight = recipeWeight + "({col-" + listHeader[j].seq_shisaku + "-" + listBody[i][j].cd_kotei + "}" + listBody[0][j].MultiplyOrDivide + (listBody[0][j].MultiplyOrDivide == "/" ? (numberAll != "0.0" ? numberAll : 1) : numberAll) + ")";
                                recipeWeight = recipeWeight + listHeader[j].PlusOrMinus;
                                var checkValue;
                                //get list val juryo_shiagari_g
                                $.each(ShisakuRetuTuikaDialog.values.dataMaster.shisaku, function (ind, item) {
                                    if (item.seq_shisaku == listHeader[j].seq_shisaku) {
                                        listDataJuryoShiagari["col-" + listHeader[j].seq_shisaku] = item.juryo_shiagari_g;
                                        juryo_shiagari_g = item.juryo_shiagari_g;
                                        var siki = item.siki_keisan == undefined ? "({col-" + listHeader[j].seq_shisaku + "}" : "(" + item.siki_keisan;
                                        formula_new = formula_new + siki + listBody[i][j].MultiplyOrDivide + (listBody[i][j].MultiplyOrDivide == "/" ? (number != "0.0" ? number : 1) : number) + ")";
                                        formula_new = formula_new + listHeader[j].PlusOrMinus;
                                        //get value finish weight
                                        checkValue = item["total_finish_weight" + listBody[i][j].cd_kotei];
                                        valueItem["col-" + listHeader[j].seq_shisaku + "-" + listBody[i][j].cd_kotei] = checkValue === undefined ? 0 : (checkValue === null ? checkValue : checkValue.replace(/,/g, ''));
                                        return false;
                                    }
                                });
                            }
                            ShisakuRetuTuikaDialog.values.lstformula.push(formula.replace(/x/g, "*"));
                            ShisakuRetuTuikaDialog.values.lstformulanew.push(formula_new.replace(/x/g, "*"));
                            ShisakuRetuTuikaDialog.values.lstformulasample.push(formula_sample.replace(/x/g, "*"));
                            formula = "";
                            formula_new = "";
                            formula_sample = "";
                            //Calculate finish weight
                            recipeWeight = recipeWeight.substr(0, recipeWeight.length - 1).replace(/x/g, "*");
                            total = checkValue === null ? null : eval(App.str.format(recipeWeight, valueItem));
                            ShisakuRetuTuikaDialog.values.resultData["total_finish_weight" + listBody[i][0].cd_kotei] = total === null ? null: new BigNumber(Math.round(new BigNumber(stringNumbers(Number(total))).times(stringNumbers(10000)).toNumber())).div(stringNumbers(10000)).toNumber();
                        }
                        recipe = ShisakuRetuTuikaDialog.values.lstformula[0];
                        recipe_new = ShisakuRetuTuikaDialog.values.lstformulanew[0];
                        recipe_sample = ShisakuRetuTuikaDialog.values.lstformulasample[0];
                        recipe = recipe.substr(0, recipe.length - 1);
                        recipe_new = recipe_new.substr(0, recipe_new.length - 1);
                        recipe_sample = recipe_sample.substr(0, recipe_sample.length - 1);
                        //calculate total juryo_shiagari_g
                        totalDataJuryoShiagari = App.isUndefOrNull(juryo_shiagari_g) ? null : eval(App.str.format(recipe, listDataJuryoShiagari).replace(/,/g, ""));
                        if (!App.isUndefOrNull(totalDataJuryoShiagari)) {
                            var totalSub = new BigNumber(Math.round(new BigNumber(stringNumbers(Number(totalDataJuryoShiagari))).times(stringNumbers(10000)).toNumber())).div(stringNumbers(10000)).toNumber();
                            totalDataJuryoShiagari = totalSub.toFixed(4);
                        }
                    } else {                        
                        
                        isKoteiSiki = false;//2020-07-01 : Bug #18153 : 試作列コピー時に連動なしでコピーを行おうとするだけとエラーが発生する

                        //計算
                        ShisakuRetuTuikaDialog.values.resultData['flg_keisan'] = 1;
                        for (var i = 0; i < listBodyLength; i++) {
                            var valueNumber = "",
                                number = "",
                                item;
                            for (var j = 0; j < lengthBodySub; j++) {
                                number = listBody[i][j].number == "" ? "1" : listBody[i][j].number;
                                formula = formula + "({col-" + listHeader[j].seq_shisaku + "-" + listBody[i][j].cd_kotei + "}" + listBody[i][j].MultiplyOrDivide + (listBody[i][j].MultiplyOrDivide == "/" ? (number != "0.0" ? number : 1) : number) + ")";
                                formula = formula + listHeader[j].PlusOrMinus;
                                $.each(ShisakuRetuTuikaDialog.values.dataMaster.shisaku, function (ind, item) {
                                    if (item.seq_shisaku == listHeader[j].seq_shisaku) {
                                        var siki = item.siki_keisan == undefined ? "({col-" + listHeader[j].seq_shisaku + "-" + listBody[i][j].cd_kotei + "}" : "(" + item.siki_keisan;
                                        formula_new = formula_new + siki + listBody[i][j].MultiplyOrDivide + (listBody[i][j].MultiplyOrDivide == "/" ? (number != "0.0" ? number : 1) : number) + ")";
                                        formula_new = formula_new + listHeader[j].PlusOrMinus;
                                        valueNumber = number;
                                        return false;
                                    }
                                });
                            }
                            item = {"formula":formula.replace(/x/g, "*"),"cd_kotei":listBody[i][0].cd_kotei};
                            ShisakuRetuTuikaDialog.values.lstLenght.push(valueNumber.length);
                            ShisakuRetuTuikaDialog.values.lstformula.push(item);
                            ShisakuRetuTuikaDialog.values.lstformulanew.push(formula_new.replace(/x/g, "*"));
                            formula = "";
                            formula_new = "";
                        }
                        $.each(ShisakuRetuTuikaDialog.values.lstformula, function (ind, val) {
                            recipe += "(" + val.formula.substr(0, val.length - 1) + ")+";
                        });
                        $.each(ShisakuRetuTuikaDialog.values.lstformulanew, function (ind, val) {
                            recipe_new += "(" + val.substr(0, val.length - 1) + ")+";
                        });
                        recipe = recipe.substr(0, recipe.length - 1);
                        recipe_new = recipe_new.substr(0, recipe_new.length - 1);
                    }

                    var listKotei = [],
                        slip,
                        listQuantity = [],
                        listDialogSelected = [],
                        dataShisaku = ShisakuRetuTuikaDialog.values.dataMaster.shisaku,
                        shisakuLength = ShisakuRetuTuikaDialog.values.dataMaster.shisaku.length;

                    //create new empty resultData
                    $.each(dataShisaku[0], function (pro, val) {
                        if (pro.indexOf("quantity_") >= 0) {
                            var listsub = pro.split("_");
                            var property = pro.substr(0, pro.length - (listsub[listsub.length-1].length)) + ShisakuRetuTuikaDialog.values.dataMaster.maxCol;
                            ShisakuRetuTuikaDialog.values.resultData[property] = null;
                        } else {
                            if (pro.indexOf("total_finish_weight") < 0 || pro.indexOf("total_finish_weight_") > -1)
                                ShisakuRetuTuikaDialog.values.resultData[pro] = null;
                        }
                    });

                    //2020-07-01 : START Bug #18153 : 試作列コピー時に連動なしでコピーを行おうとするだけとエラーが発生する
                    //check validate
                    //if (recipe_new.length > 400 ) {
                    if (recipe_new.length > 400 && isKoteiSiki) {
                        ShisakuRetuTuikaDialog.values.stringOverflow = true;
                        var message = App.str.format(App.messages.app.AP0009, { calculItem: "計算式", maxChar: 400, inputItems: "配合表のサンプルNO" });
                        ShisakuRetuTuikaDialog.notifyAlert.message(message, "AP0009").show();
                        App.ui.loading.close();
                        return;
                    }
                    //2020-07-01 : END Bug #18153 : 試作列コピー時に連動なしでコピーを行おうとするだけとエラーが発生する

                    //set recipe for resultData
                    ShisakuRetuTuikaDialog.values.resultData["siki_keisan_new"] = recipe;
                    ShisakuRetuTuikaDialog.values.resultData["siki_keisan"] = recipe_new;
                    ShisakuRetuTuikaDialog.values.resultData["siki_keisan_head"] = recipe_new;
                    ShisakuRetuTuikaDialog.values.resultData["seq_shisaku"] = ShisakuRetuTuikaDialog.values.dataMaster.maxCol;

                    //set name nm_sample for resultData
                    ShisakuRetuTuikaDialog.commands.setNameSample(listHeader, listDialogSelected, shisakuLength, dataShisaku, slip, recipe_sample);

                    //get all property with name quantity_ from listDialogSelected
                    $.each(listDialogSelected, function (ind, item) {
                        $.each(item, function (pro, val) {
                            if (pro.indexOf("quantity_") >= 0) {
                                var quantity = {};
                                quantity[pro] = val;
                                listQuantity.push(quantity);
                            }
                        });
                    });

                    //Set key for listQuantity to calculate according to kotei
                    var listQuantityLength = listQuantity.length;

                    for (var i = 0; i < listQuantityLength; i++) {
                        $.each(listQuantity[i], function (pro, val) {
                            slip = pro.split("_");
                            listQuantity[i].key = slip[1] + "_" + slip[2];
                        });

                    }

                    //Get list of kotei with the same key
                    listKotei = $.map(listQuantity, function (value, keys) { return value.key; }).filter(function (el, index, arr) {
                        return index == arr.indexOf(el);
                    });

                    //calculate all quantity_ in each kotei
                    ShisakuRetuTuikaDialog.commands.calculateAllQuantity(listKotei, listQuantity, listQuantityLength, slip, recipe);
                    if (ShisakuRetuTuikaDialog.values.numberOverflow) {
                        App.ui.loading.close();
                        return;
                    }

                    //total kotei and weigth
                    ShisakuRetuTuikaDialog.commands.totalAllKoteiWeight(slip);

                    //check validate juryo_shiagari_g
                    if (!page.numPointlength(totalDataJuryoShiagari, [10, 4])) {
                        var message = App.str.format(App.messages.app.AP0009, { calculItem: "合計仕上重量", maxChar: "整部の10", inputItems: "計算値" });
                        ShisakuRetuTuikaDialog.notifyAlert.message(message, "AP0009").show();
                        App.ui.loading.close();
                        return;
                    }
                    //total juryo_shiagari_g
                    //ShisakuRetuTuikaDialog.values.resultData['juryo_shiagari_g'] = totalDataJuryoShiagari == 0 ? "" : totalDataJuryoShiagari;
                    ShisakuRetuTuikaDialog.values.resultData['juryo_shiagari_g'] = totalDataJuryoShiagari;
                    //result data

                    if(ShisakuRetuTuikaDialog.values.beforeShisaku == ShisakuRetuTuikaDialog.values.dataMaster["maxCol"]){
                        App.ui.loading.close();
                        return;
                    }
                    
                    ShisakuRetuTuikaDialog.values.resultData['flg_keisan'] = 0;
                    if(!isKoteiSiki){                    
                        ShisakuRetuTuikaDialog.values.resultData['flg_keisan'] = 1;
                    }

                    if (App.isFunc(ShisakuRetuTuikaDialog.dataSelected)) {
                        if (!ShisakuRetuTuikaDialog.dataSelected(ShisakuRetuTuikaDialog.values.resultData)) {
                            ShisakuRetuTuikaDialog.values.resultData = {};
                            //ShisakuRetuTuikaDialog.element.modal("hide");
                        }
                    
                        ShisakuRetuTuikaDialog.values.beforeShisaku = ShisakuRetuTuikaDialog.values.dataMaster["maxCol"]; 
                    }
                    //else {
                    //    ShisakuRetuTuikaDialog.element.modal("hide");
                    //}
                }
            }).fail(function () {
                App.ui.loading.close();
            });
        
        },10)
    };

    /**
    * Get Recipe
    */
    ShisakuRetuTuikaDialog.commands.getRecipe = function (listBodyLength, listBody, lengthBodySub, listHeader, formula, recipe) {
        var elementHeader = $("#ShisakuRetuTuikaDialog").find(".header");
        
    };

    /**
    * Set name nm_sample
    */
    ShisakuRetuTuikaDialog.commands.setNameSample = function (listHeader, listDialogSelected, shisakuLength, dataShisaku, slip, recipe) {
        var elementHeader = $("#ShisakuRetuTuikaDialog").find(".header");

        //Get the column selected for calculation
        for (var i = 0 ; i < shisakuLength ; i++) {
            $.each(listHeader, function (ind, val) {
                if (val.seq_shisaku == dataShisaku[i].seq_shisaku) {
                    listDialogSelected.push(dataShisaku[i]);
                }
            });
        }
        if (elementHeader.findP("allStage").is(":checked")) {
            var listDialogLength = listDialogSelected.length;
            var nm_sample = recipe;
            for (var i = 0; i < listDialogLength; i++) {
                $.each(listDialogSelected[i], function (pro, val) {
                    if (pro.indexOf("quantity_") >= 0) {
                        slip = pro.split("_");
                        if (recipe.indexOf("{col-" + slip[3] + "}") >= 0) {
                            nm_sample = nm_sample.split("{col-" + slip[3] + "}").join(listDialogSelected[i].nm_sample == null ? "()" : "(" + listDialogSelected[i].nm_sample + ")");
                            return false;
                        }
                    }
                });
            }
            ShisakuRetuTuikaDialog.values.resultData["nm_sample"] = nm_sample;
        } else {
            ShisakuRetuTuikaDialog.values.resultData["nm_sample"] = null;
        }
    };

    /**
    * Calculate all quantity_ in each kotei
    */
    ShisakuRetuTuikaDialog.commands.calculateAllQuantity = function (listKotei, listQuantity, listQuantityLength, slip, recipe) {
        var elementHeader = $("#ShisakuRetuTuikaDialog").find(".header"),
            listKoteiLength = listKotei.length;

        for (var i = 0; i < listKoteiLength; i++) {
            var item = {}, list = [];

            //Get the list of values of quantity_ same key
            for (var j = 0; j < listQuantityLength; j++) {
                if (listKotei[i] == listQuantity[j].key) {

                    $.each(listQuantity[j], function (pro, val) {
                        if (pro.indexOf("quantity_") >= 0) {
                            slip = pro.split("_");
                            if (slip.length == 4) {

                                if (elementHeader.findP("allStage").is(":checked")) {
                                    item["col-" + slip[3]] = ShisakuRetuTuikaDialog.checkUndefi(val);
                                    list.push(item);
                                } else {
                                    item["col-" + slip[3] + "-" + slip[1]] = ShisakuRetuTuikaDialog.checkUndefi(val);
                                    list.push(item);
                                }
                            }
                        }
                    });
                }
            }

            //total quantity
            if (elementHeader.findP("allStage").is(":checked")) {

                var total = eval(App.str.format(recipe, list[0])),
                slip = listKotei[i].split("_");

                if (!App.isUndefOrNull(total)) {
                    var totalSub = new BigNumber(Math.round(new BigNumber(stringNumbers(Number(total))).times(stringNumbers(10000)).toNumber())).div(stringNumbers(10000)).toNumber();
                    total = totalSub.toFixed(page.values.keta_shosu);
                }
                //check validate
                if (!page.numPointlength(total, [5, page.values.keta_shosu])) {
                    var message = App.str.format(App.messages.app.AP0009, { calculItem: "計算結果", maxChar: "整部の5", inputItems: "計算値" });
                    ShisakuRetuTuikaDialog.notifyAlert.message(message, "AP0009").show();
                    ShisakuRetuTuikaDialog.values.numberOverflow = true;
                    return;
                }
                ShisakuRetuTuikaDialog.values.resultData["quantity_" + slip[0] + "_" + slip[1] + "_" + ShisakuRetuTuikaDialog.values.dataMaster.maxCol] = total == null || total == "" ? 0:total;

            } else {

                var total = 0, split, repiceSub = "";
                slip = listKotei[i].split("_");
                
                //Calculate quantity_ in kotei
                for (var k = 0; k < ShisakuRetuTuikaDialog.values.lstformula.length; k++) {
                    if (ShisakuRetuTuikaDialog.values.lstformula[k].cd_kotei == slip[0]) {
                        repiceSub=ShisakuRetuTuikaDialog.values.lstformula[k].formula;
                        total = eval(App.str.format(repiceSub.substr(0, repiceSub.length - 1), list[0]));
                        if (!App.isUndefOrNull(total)) {
                            var totalSub = new BigNumber(Math.round(new BigNumber(stringNumbers(Number(total))).times(stringNumbers(10000)).toNumber())).div(stringNumbers(10000)).toNumber();
                            total = totalSub.toFixed(page.values.keta_shosu);
                        }
                        //check validate
                        if (!page.numPointlength(total, [5, page.values.keta_shosu])) {
                            var message = App.str.format(App.messages.app.AP0009, { calculItem: "計算結果", maxChar: "整部の5", inputItems: "計算値" });
                            ShisakuRetuTuikaDialog.notifyAlert.message(message, "AP0009").show();
                            ShisakuRetuTuikaDialog.values.numberOverflow = true;
                            return;
                        }
                        ShisakuRetuTuikaDialog.values.resultData["quantity_" + slip[0] + "_" + slip[1] + "_" + ShisakuRetuTuikaDialog.values.dataMaster.maxCol] = total == null || total == "" ? 0:total;
                    }
                }
            }
        }
    };

    /**
    * Total kotei and weigth
    */
    ShisakuRetuTuikaDialog.commands.totalAllKoteiWeight = function (slip) {
        var totalAll = 0, totalWeight = 0, 
            koteiLength = ShisakuRetuTuikaDialog.values.dataMaster.kotei.length;
        for (var i = 0; i < koteiLength; i++) {
            var koteiTotal = 0, valueKoteiTotal = 0;
            $.each(ShisakuRetuTuikaDialog.values.resultData, function (pro, val) {
                if (pro.indexOf("quantity_") >= 0) {
                    slip = pro.split("_");
                    if (slip[1] == ShisakuRetuTuikaDialog.values.dataMaster.kotei[i].cd_kotei) {
                        koteiTotal += parseFloat(page.detail.checkUndefi(val));
                    }
                }

            });
            valueKoteiTotal = new BigNumber(Math.round(new BigNumber(stringNumbers(Number(koteiTotal))).times(stringNumbers(10000)).toNumber())).div(stringNumbers(10000)).toNumber();
            valueKoteiTotal = valueKoteiTotal.toFixed(page.values.keta_shosu);
            totalAll += parseFloat(valueKoteiTotal);

            ShisakuRetuTuikaDialog.values.resultData["total_kotei" + ShisakuRetuTuikaDialog.values.dataMaster.kotei[i].cd_kotei] = valueKoteiTotal;
        }
        totalWeight = new BigNumber(Math.round(new BigNumber(stringNumbers(Number(totalAll))).times(stringNumbers(10000)).toNumber())).div(stringNumbers(10000)).toNumber();
        totalWeight = totalWeight.toFixed(page.values.keta_shosu);

        ShisakuRetuTuikaDialog.values.resultData["total_weight"] = totalWeight;
    };

    /**
    * 明細のバリデーションを実行します。
    */
    ShisakuRetuTuikaDialog.detail.executeValidation = function (targets, row, options) {
        var defaultOptions = {
            targets: targets,
            state: {
                tbody: row,
                isGridValidation: true
            }
        },
            execOptions = $.extend(true, {}, defaultOptions, options);

        return ShisakuRetuTuikaDialog.detail.validator.validate(execOptions);
    };

    /**
     * 明細のバリデーションフィルターを設定します。（必須チェックを行わない）
     */
    ShisakuRetuTuikaDialog.detail.validationFilter = function (item, method, state, options) {
        return method !== "required";
    };

    /**
     * 明細の一覧全体のバリデーションを実行します。
     */
    ShisakuRetuTuikaDialog.detail.validateList = function (suppressMessage) {
        var validations = [],
            options = {
                state: {
                    suppressMessage: suppressMessage,
                }
            };

        ShisakuRetuTuikaDialog.detail.dataTable.dataTable("each", function (row, index) {
            validations.push(ShisakuRetuTuikaDialog.detail.executeValidation(row.element.find(":input"), row.element, options));
        });

        return App.async.all(validations);
    };

</script>

<div class="modal fade wide" tabindex="-1" id="ShisakuRetuTuikaDialog">
    <div class="modal-dialog" style="max-height: 85%; width: 55%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">試作列追加</h4>
            </div>

            <div class="modal-body">
                <div class="modal-wrap">
                    <div class="header">
                        <div class="search-criteria">
                            <div class="row font_size_12">
                                <div class="col-xs-2 control">
                                    <label for="SRT-radio1" class="full-width check-group">
                                        全工程(連動あり)
                                        <input type="radio" id="SRT-radio1" data-prop="allStage" name="radio-group1" checked>
                                    </label>
                                </div>
                                <div class="col-xs-2 control">
                                    <label for="SRT-radio2" class="full-width check-group">
                                        計算(連動なし)
                                        <input type="radio" id="SRT-radio2" data-prop="nm_kotei" name="radio-group1">
                                    </label>
                                </div>
                                <div class="col-xs-8 control"></div>
                            </div>
                        </div>
                    </div>
                    <div class="detail">
                        <div style="height: 300px;">
                            <table class="datatable font_size_12" style="margin-bottom: 0px; width: 150px; height: 350px; float: left; overflow-x: hidden; overflow-y: hidden; margin-right: 0px !important;">
                                <thead>
                                    <tr>
                                        <th style="height: 35px;">計算列数</th>
                                    </tr>
                                    <tr>
                                        <th style="height: 35px;">サンプルNo</th>
                                    </tr>
                                    <tr style="height: 250px;">
                                        <th style="width: 100px">工程</th>
                                    </tr>
                                </thead>
                            </table>
                            <table class="datatable search-list font_size_12" style="float: left; margin-right: 0px !important;">
                                <thead>
                                    <tr class="spe-pros">
                                        <th style="width: 225px; height: 35px;" class="th-tmpl"></th>
                                        <th style="width: 225px; height: 35px; text-align: left;">1</th>
                                    </tr>
                                    <tr>
                                        <th style="width: 225px; height: 35px;" class="th-tmpl">
                                            <select style="float: left; width: 150px; height: 24px;" data-prop="seq_shisaku">
                                            </select>
                                            <select style="float: left; width: 70px; height: 24px;" data-prop="PlusOrMinus">
                                                <option>+</option>
                                                <option>-</option>
                                            </select>
                                        </th>
                                        <th style="width: 225px; height: 35px;">
                                            <select style="float: left; width: 150px; height: 24px;" data-prop="seq_shisaku">
                                            </select>
                                            <select style="float: left; width: 70px; height: 24px;" data-prop="PlusOrMinus">
                                                <option>+</option>
                                                <option>-</option>
                                            </select>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody class="item-tmpl" style="cursor: default; display: none;">
                                    <tr style="height: 35px;">
                                        <td class="th-tmpl">
                                            <input type="text" style="float: left; width: 100px; height: 24px;" data-prop="all" disabled="disabled" />
                                            <input type="text" class="hidden" style="float: left; width: 100px; height: 24px;" data-prop="nm_kotei" disabled="disabled" />
                                            <input type="text" class="hidden" style="float: left; width: 100px; height: 24px;" data-prop="cd_kotei" />
                                            <select style="float: left; width: 50px; height: 24px;" data-prop="MultiplyOrDivide">
                                                <option>x</option>
                                                <option>/</option>
                                            </select>
                                            <input type="tel" class="limit-input-float" style="float: left; width: 70px; height: 24px; ime-mode: disabled;" data-prop="number" maxlength="9" value="0.0" data-number-format-tofixed="0" />
                                        </td>
                                        <td>
                                            <input type="text" style="float: left; width: 100px; height: 24px;" data-prop="all" disabled="disabled" />
                                            <input type="text" class="hidden" style="float: left; width: 100px; height: 24px;" data-prop="nm_kotei" disabled="disabled" />
                                            <input type="text" class="hidden" style="float: left; width: 100px; height: 24px;" data-prop="cd_kotei" />
                                            <select style="float: left; width: 50px; height: 24px;" data-prop="MultiplyOrDivide">
                                                <option>x</option>
                                                <option>/</option>
                                            </select>
                                            <input type="tel" class="limit-input-float" style="float: left; width: 70px; height: 24px; ime-mode: disabled;" data-prop="number" maxlength="9" value="0.0" data-number-format-tofixed="0" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
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

            <div class="modal-footer" style="height: 50px;">
                <button type="button" class="btn btn-primary btn-sm add-item">列追加</button>
                <button type="button" class="btn btn-primary btn-sm apply-item">採用</button>
                <button type="button" class="cancel-button btn btn-sm btn-default" data-dismiss="modal" name="close">キャンセル</button>
            </div>

        </div>
    </div>
</div>
