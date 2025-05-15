<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="501_TantoshaIchiranEigyo_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs._501_TantoshaIchiran_Dialog" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchInputDialog(Ver2.1)】 Template--%>
<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var TantoshaIchiranEigyoDialog = {
        options: {
            parameter: {
                cd_kaisha: '',
                cd_busho: '',
                flag_eigyo: 1
            },
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.maxInputDataCount,   // TODO:取得するデータ数を指定します。
            filter: ""
        },
        values: {
            isChangeRunning: {},
            flg_eigyo: 1
        },
        urls: {
            TantoshaIchiranEigyo_Dialog: "../api/TantoshaIchiranEigyo_Dialog",
            kaisha: "../Services/ShisaQuickService.svc/vw_shohinkaihatsu_dialog_kaisha_501?$filter=cd_kaisha eq {0} and flg_eigyo eq {1} &$orderby=cd_kaisha",
            busho: "../Services/ShisaQuickService.svc/ma_busho?$filter=flg_eigyo eq {0} and cd_kaisha eq {1} and cd_busho eq {2} &$orderby=cd_busho",
            search: "../api/TantoshaIchiranEigyo_Dialog/GetData",
        },
        header: {
            options: {},
            values: {},
        },
        detail: {
            options: {},
            values: {}
        },
        commands: {},
        dialogs: {}
    };

    /**
     * バリデーション成功時の処理を実行します。
     */
    TantoshaIchiranEigyoDialog.validationSuccess = function (results, state) {
        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                TantoshaIchiranEigyoDialog.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                    if (row && row.element) {
                        row.element.find("tr").removeClass("has-error");
                    }
                });
            } else {
                TantoshaIchiranEigyoDialog.setColValidStyle(item.element);
            }

            TantoshaIchiranEigyoDialog.notifyAlert.remove(item.element);
        }
    };

    /**
     * バリデーション失敗時の処理を実行します。
     */
    TantoshaIchiranEigyoDialog.validationFail = function (results, state) {

        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                TantoshaIchiranEigyoDialog.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                    if (row && row.element) {
                        row.element.find("tr").addClass("has-error");
                    }
                });
            } else {
                TantoshaIchiranEigyoDialog.setColInvalidStyle(item.element);
            }

            if (state && state.suppressMessage) {
                continue;
            }
            TantoshaIchiranEigyoDialog.notifyAlert.message(item.message, item.element).show();
        }
    };

    /**
     * バリデーション後の処理を実行します。
     */
    TantoshaIchiranEigyoDialog.validationAlways = function (results) {
        //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
    };

    /**
      * 指定された定義をもとにバリデータを作成します。
      * @param target バリデーション定義
      * @param options オプションに設定する値。指定されていない場合は、
      *                画面の success/fail/always のハンドル処理が指定されたオプションが設定されます。
      */
    TantoshaIchiranEigyoDialog.createValidator = function (target, options) {
        return App.validation(target, options || {
            success: TantoshaIchiranEigyoDialog.validationSuccess,
            fail: TantoshaIchiranEigyoDialog.validationFail,
            always: TantoshaIchiranEigyoDialog.validationAlways
        });
    };

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    TantoshaIchiranEigyoDialog.setColInvalidStyle = function (target) {
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
    TantoshaIchiranEigyoDialog.setColValidStyle = function (target) {
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
     * データの保存処理を実行します。
     */
    TantoshaIchiranEigyoDialog.commands.save = function () {
        var dataSelected = TantoshaIchiranEigyoDialog.element.find("input[type=checkbox]:checked"),
            data = [];

        $.each(dataSelected, function (index, item) {
            var id = $(item).closest("tbody").attr("data-key");
            data.push(TantoshaIchiranEigyoDialog.detail.data.entry(id));
        });

        if (App.isFunc(TantoshaIchiranEigyoDialog.dataSelected)) {
            if (!TantoshaIchiranEigyoDialog.dataSelected(data)) {
                TantoshaIchiranEigyoDialog.element.modal("hide");
            }
        } else {
            TantoshaIchiranEigyoDialog.element.modal("hide");
        }

    };

    /**
     * すべてのバリデーションを実行します。
     */
    TantoshaIchiranEigyoDialog.validateAll = function () {

        var validations = [];

        validations.push(TantoshaIchiranEigyoDialog.detail.validateList());

        //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。

        return App.async.all(validations);
    };

    /**
     * ダイアログの初期化処理を行います。
     */
    TantoshaIchiranEigyoDialog.initialize = function () {

        var element = $("#TantoshaIchiranEigyoDialog"),
            contentHeight = $(window).height() * 80 / 100;

        TantoshaIchiranEigyoDialog.element = element;
        element.find(".modal-body").css("max-height", contentHeight);
        element.find(".modal-body").css("overflow-y", "hidden");
        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        TantoshaIchiranEigyoDialog.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#TantoshaIchiranEigyoDialog .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body .detail",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        TantoshaIchiranEigyoDialog.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#TantoshaIchiranEigyoDialog .dialog-slideup-area .alert-message",
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

        TantoshaIchiranEigyoDialog.initializeControlEvent();
        TantoshaIchiranEigyoDialog.header.initialize();
        TantoshaIchiranEigyoDialog.loadMasterData();
        TantoshaIchiranEigyoDialog.loadDialogs();
    };

    /**
     * ダイアログコントロールへのイベントの紐づけを行います。
     */
    TantoshaIchiranEigyoDialog.initializeControlEvent = function () {
        var element = TantoshaIchiranEigyoDialog.element;

        //TODO: 画面全体で利用するコントロールのイベントの紐づけ処理をここに記述します。
        element.on("hidden.bs.modal", TantoshaIchiranEigyoDialog.hidden);
        element.on("shown.bs.modal", TantoshaIchiranEigyoDialog.shown);
        element.on("click", ".save", TantoshaIchiranEigyoDialog.commands.save);
    };

    /**
     * ダイアログ非表示時処理を実行します。
     */
    TantoshaIchiranEigyoDialog.hidden = function (e) {
        var element = TantoshaIchiranEigyoDialog.element;

        //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
        element.find(":input:not([type='checkbox']):not([type='radio'])").val("");
        element.find("input[type='checkbox']").prop("checked", false);
        //TODO:radioボタンの初期表示は、画面要件に合わせて記述します。

        TantoshaIchiranEigyoDialog.detail.dataTable.dataTable("clear");

        element.findP("data_count").text("");
        element.findP("data_count_total").text("");

        var items = element.find(".search-criteria :input:not(button)");
        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            TantoshaIchiranEigyoDialog.setColValidStyle(item);
        }

        if (!$(".modal.in").length) {
            $(".modal-dialog").css({
                top: 0,
                left: 0
            });
        }

        TantoshaIchiranEigyoDialog.element.find(".save").prop("disabled", true);
        TantoshaIchiranEigyoDialog.notifyInfo.clear();
        TantoshaIchiranEigyoDialog.notifyAlert.clear();
    };

    /**
     * ダイアログ表示時処理を実行します。
     */
    TantoshaIchiranEigyoDialog.shown = function (e) {
        $("#TantoshaIchiranEigyoDialog").on('loaded.bs.modal', function () {
            $(this).find('.modal-dialog').css({
                'margin-top': function () {
                    return (($(window).outerHeight() / 2) - ($(this).outerHeight() / 2));
                }
            });
        });

        TantoshaIchiranEigyoDialog.loadComboboxData();

        //初回起動時にdatatable作成
        if (App.isUndefOrNull(TantoshaIchiranEigyoDialog.detail.fixedColumnIndex)) {
            TantoshaIchiranEigyoDialog.detail.initialize();
        }

        TantoshaIchiranEigyoDialog.element.find(":input:not(button):first").focus();
    };

    /**
     * マスターデータのロード処理を実行します。
     */
    TantoshaIchiranEigyoDialog.loadMasterData = function () {
        //var element = TantoshaIchiranEigyoDialog.element;
        //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。

        //TODO: マスタデータのロード処理を実装後、下の1行を削除してください。
        return App.async.success();
    };
    /**
     * マスターデータのロード処理を実行します。
     */
    TantoshaIchiranEigyoDialog.loadComboboxData = function () {
        var element = TantoshaIchiranEigyoDialog.element;
        //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
        return $.ajax(App.ajax.odata.get(App.str.format(TantoshaIchiranEigyoDialog.urls.kaisha, TantoshaIchiranEigyoDialog.options.parameter.cd_kaisha, TantoshaIchiranEigyoDialog.options.parameter.flag_eigyo))).then(function (result) {//

            var cd_kaisha = element.findP("cd_kaisha");
            cd_kaisha.children().remove();
            App.ui.appendOptions(
                cd_kaisha,
                "cd_kaisha",
                "nm_kaisha",
                result.value,
                true
            );
            if (!App.isUndefOrNullOrStrEmpty(cd_kaisha)) {
                cd_kaisha.val(result.value[0].cd_kaisha);
            }
        })
        .then(function (result) {

            return $.ajax(App.ajax.odata.get(App.str.format(TantoshaIchiranEigyoDialog.urls.busho, TantoshaIchiranEigyoDialog.values.flg_eigyo, TantoshaIchiranEigyoDialog.options.parameter.cd_kaisha, TantoshaIchiranEigyoDialog.options.parameter.cd_busho)))
                .then(function (result) {

                    var cd_busho = element.findP("cd_busho");
                    cd_busho.children().remove();

                    App.ui.appendOptions(
                        cd_busho,
                        "cd_busho",
                        "nm_busho",
                        result.value,
                        true
                    );
                    if (!App.isUndefOrNullOrStrEmpty(cd_busho)) {
                        cd_busho.val(result.value[0].cd_busho);
                    }
                });
        });
        //TODO: マスタデータのロード処理を実装後、下の1行を削除してください。
        return App.async.success();
    };

    /**
     * 共有のダイアログのロード処理を実行します。
     */
    TantoshaIchiranEigyoDialog.loadDialogs = function () {

        if ($.find("#ConfirmDialog").length == 0) {
            return App.async.all({
                confirmDialog: $.get(TantoshaIchiranEigyoDialog.urls.confirmDialog)
            }).then(function (result) {
                $("#dialog-container").append(result.successes.confirmDialog);
                TantoshaIchiranEigyoDialog.dialogs.confirmDialog = ConfirmDialog;
            });
        } else {
            TantoshaIchiranEigyoDialog.dialogs.confirmDialog = ConfirmDialog;
        }
    };

    /**
     * ダイアログヘッダー　バリデーションルールを定義します。
     */
    TantoshaIchiranEigyoDialog.header.options.validations = {
        id_user: {
            rules: {
                maxlength: 10,
                digits: true
            },
            options: {
                name: "ユーザーID"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                digits: App.messages.base.digits
            }
        },
        nm_user: {
            rules: {
                maxlength: 60
            },
            options: {
                name: "担当者名"
            },
            messages: {
                maxlength: App.messages.base.maxlength
            }
        },

    };

    /**
     * ダイアログヘッダーの初期化処理を行います。
     */
    TantoshaIchiranEigyoDialog.header.initialize = function () {

        var element = TantoshaIchiranEigyoDialog.element.find(".header");
        TantoshaIchiranEigyoDialog.header.validator = element.validation(TantoshaIchiranEigyoDialog.createValidator(TantoshaIchiranEigyoDialog.header.options.validations));
        TantoshaIchiranEigyoDialog.header.element = element;

        //TODO: 画面ヘッダーの初期化処理をここに記述します。
        //TODO: 画面ヘッダーで利用するコントロールのイベントの紐づけ処理をここに記述します。
        element.on("click", ".search", TantoshaIchiranEigyoDialog.header.search);
        element.on("change", ":input", TantoshaIchiranEigyoDialog.header.change);
    };

    /**
     * 画面ヘッダーの変更時処理を定義します。
     */
    TantoshaIchiranEigyoDialog.header.change = function (e) {
        var element = TantoshaIchiranEigyoDialog.header.element,
        target = $(e.target),
        property = target.attr("data-prop"),
        data = element.form().data();
        element.validation().validate().done(function () {
            if (property == "id_user") {
                if (data.id_user >= 0) {
                    var new_id_user = ("0000000000" + data.id_user).slice(-10);

                    target.val(new_id_user);
                }
            }
        });
    };


    /**
     * ダイアログの検索処理を実行します。
     */
    TantoshaIchiranEigyoDialog.header.search = function (isLoading) {
        var element = TantoshaIchiranEigyoDialog.element,
            loadingTaget = element.find(".modal-content"),
            deferred = $.Deferred(),
            query;

        TantoshaIchiranEigyoDialog.header.validator.validate().done(function () {

            TantoshaIchiranEigyoDialog.options.filter = TantoshaIchiranEigyoDialog.header.createFilter();

            TantoshaIchiranEigyoDialog.detail.dataTable.dataTable("clear");
            if (isLoading) {
                App.ui.loading.show("", loadingTaget);
                TantoshaIchiranEigyoDialog.notifyAlert.clear();
            }

            return $.ajax(App.ajax.webapi.get(TantoshaIchiranEigyoDialog.urls.search, TantoshaIchiranEigyoDialog.options.filter))

            .done(function (result) {

                TantoshaIchiranEigyoDialog.detail.bind(result);
                deferred.resolve();
            }).fail(function (error) {

                TantoshaIchiranEigyoDialog.notifyAlert.message(App.ajax.handleError(error).message).show();
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
    TantoshaIchiranEigyoDialog.header.createFilter = function () {
        var criteria = TantoshaIchiranEigyoDialog.header.element.form().data(),
            filters = {};

        //TODO: 画面で設定された検索条件を取得し、データ取得サービスのフィルターオプションを組み立てます。
        if (!App.isUndefOrNull(criteria.id_user) && criteria.id_user >= 0) {
            filters.id_user = criteria.id_user;
        }
        if (!App.isUndefOrNull(criteria.nm_user)) {
            filters.nm_user = criteria.nm_user;
        }
        if (!App.isUndefOrNull(criteria.cd_kaisha) && criteria.cd_kaisha > 0) {
            filters.cd_kaisha = criteria.cd_kaisha;
        }
        if (!App.isUndefOrNull(criteria.cd_busho) && criteria.cd_busho > 0) {
            filters.cd_busho = criteria.cd_busho;
        }

        return filters;
    };

    /**
     * ダイアログヘッダー　バリデーションルールを定義します。
     */
    TantoshaIchiranEigyoDialog.detail.options.validations = {
    };

    /**
     * 画面明細の初期化処理を行います。
     */
    TantoshaIchiranEigyoDialog.detail.initialize = function () {

        var element = TantoshaIchiranEigyoDialog.element.find(".detail"),
            table = element.find(".datatable"),
            offsetHeight = $(window).height(),
            datatable = table.dataTable({
                height: 420,
                resize: true,
                resizeOffset: offsetHeight,
                //列固定横スクロールにする場合は、下記3行をコメント解除
                //fixedColumn: true,               //列固定の指定
                //fixedColumns: 1,                 //固定位置を指定（左端を0としてカウント）
                //innerWidth: 530,                 //可動列の合計幅を指定
                onselect: TantoshaIchiranEigyoDialog.detail.select,
                onchange: TantoshaIchiranEigyoDialog.detail.change
            });
        table = element.find(".datatable");        //列固定にした場合DOM要素が再作成されるため、変数を再取得

        TantoshaIchiranEigyoDialog.detail.validator = element.validation(TantoshaIchiranEigyoDialog.createValidator(TantoshaIchiranEigyoDialog.detail.options.validations));
        TantoshaIchiranEigyoDialog.detail.element = element;
        TantoshaIchiranEigyoDialog.detail.dataTable = datatable;
        // 行選択時に利用するテーブルインデックスを指定します
        TantoshaIchiranEigyoDialog.detail.fixedColumnIndex = element.find(".fix-columns").length;

        //TODO: 画面明細で利用するコントロールのイベントの紐づけ処理をここに記述します。
        //element.on("click", ".select", TantoshaIchiranEigyoDialog.detail.selectData);

        //TODO: 画面明細の初期化処理をここに記述します。
        TantoshaIchiranEigyoDialog.detail.bind([], true);
    };

    /**
     * ダイアログ明細へのデータバインド処理を行います。
     */
    TantoshaIchiranEigyoDialog.detail.bind = function (data, isNewData) {
        var i, l, item, dataSet, dataCount;

        dataCount = data.Count ? data.Count : 0;
        data = (data.Items) ? data.Items : data;

        dataSet = App.ui.page.dataSet();
        TantoshaIchiranEigyoDialog.detail.data = dataSet;
        TantoshaIchiranEigyoDialog.detail.dataTable.dataTable("clear");

        TantoshaIchiranEigyoDialog.detail.dataTable.dataTable("addRows", data, function (row, item) {
            if (App.isUndefOrNull(item.kbn_eigyo)) {
                item.kbn_eigyo = 0;
            }
            (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
            row.form(TantoshaIchiranEigyoDialog.detail.options.bindOption).bind(item);

            return row;
        }, true);

        if (!isNewData) {
            TantoshaIchiranEigyoDialog.detail.element.findP("data_count").text(data.length);
            TantoshaIchiranEigyoDialog.detail.element.findP("data_count_total").text(dataCount);
        }

        if (dataCount >= App.settings.base.maxInputDataCount) {
            TantoshaIchiranEigyoDialog.notifyInfo.message(App.messages.base.MS0011).show();
        }
        //if (!dataCount && !isNewData) {
        //    TantoshaIchiranEigyoDialog.notifyInfo.clear();
        //    TantoshaIchiranEigyoDialog.notifyInfo.message(App.messages.app.AP0007).show();
        //}

        //TODO: 画面明細へのデータバインド処理をここに記述します。


        //バリデーションを実行します。
        TantoshaIchiranEigyoDialog.detail.validateList(true);
    };

    /**
     * 明細の各行にデータを設定する際のオプションを定義します。
     */
    TantoshaIchiranEigyoDialog.detail.options.bindOption = {
        // TODO: 主キーが直接入力の場合には、修正の場合変更を不可とします。
        appliers: {
            id_user: function (value, element) {
                value = ("0000000000" + value).slice(-10);

                element.text(value);
                return true;
            },
            kbn_eigyo: function (value, element) {
                if (value !== App.settings.app.kbn_eigyo.eigyo_ari) {
                    element.text(App.settings.app.honbu_kengen.nashi);
                }
                else {
                    element.text(App.settings.app.honbu_kengen.ari);
                }
                return true;
            }
        }
    };

    /**
     * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
     */
<%--        TantoshaIchiranEigyoDialog.detail.selectData = function (e) {
            var target = $(e.target),
                id, entity,
                selectData = function (entity) {
                    if (App.isFunc(TantoshaIchiranEigyoDialog.dataSelected)) {
                        if (!TantoshaIchiranEigyoDialog.dataSelected(entity)) {
                            TantoshaIchiranEigyoDialog.element.modal("hide");
                        }
                    } else {
                        TantoshaIchiranEigyoDialog.element.modal("hide");
                    }
                };

            TantoshaIchiranEigyoDialog.detail.dataTable.dataTable("getRow", target, function (row) {
                id = row.element.attr("data-key");
                entity = TantoshaIchiranEigyoDialog.detail.data.entry(id);
            });

            if (App.isUndef(id)) {
                return;
            }
            if (TantoshaIchiranEigyoDialog.detail.data.isUpdated(id)) {
                var options = {
                    text: App.messages.base.MS0024
                };
                TantoshaIchiranEigyoDialog.dialogs.confirmDialog.confirm(options)
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
    TantoshaIchiranEigyoDialog.detail.select = function (e, row) {
        $($(row.element[TantoshaIchiranEigyoDialog.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab.selected")).removeClass("selected").addClass("unselected");
        $(row.element[TantoshaIchiranEigyoDialog.detail.fixedColumnIndex].querySelectorAll(".select-tab")).removeClass("unselected").addClass("selected");

        //選択行全体に背景色を付ける場合は以下のコメントアウトを解除します。
        if (!App.isUndefOrNull(TantoshaIchiranEigyoDialog.detail.selectedRow)) {
            TantoshaIchiranEigyoDialog.detail.selectedRow.element.find(".selected-row").removeClass("selected-row");
        }
        row.element.find("tr").addClass("selected-row");
        TantoshaIchiranEigyoDialog.detail.selectedRow = row;
    };

    /**
     * 明細の一覧の入力項目の変更イベントの処理を行います。
     */
    TantoshaIchiranEigyoDialog.detail.change = function (e, row) {
        var target = $(e.target),
            id = row.element.attr("data-key"),
            property = target.attr("data-prop"),
            entity = TantoshaIchiranEigyoDialog.detail.data.entry(id),
            options = {
                filter: TantoshaIchiranEigyoDialog.detail.validationFilter
            };
        if (TantoshaIchiranEigyoDialog.element.find("input[type=checkbox]:checked").length > 0) {
            TantoshaIchiranEigyoDialog.element.find(".save").prop("disabled", false);
        } else {
            TantoshaIchiranEigyoDialog.element.find(".save").prop("disabled", true);
        }

    };


    /**
    * 明細のバリデーションを実行します。
    */
    TantoshaIchiranEigyoDialog.detail.executeValidation = function (targets, row, options) {
        var defaultOptions = {
            targets: targets,
            state: {
                tbody: row,
                isGridValidation: true
            }
        },
            execOptions = $.extend(true, {}, defaultOptions, options);

        return TantoshaIchiranEigyoDialog.detail.validator.validate(execOptions);
    };

    /**
     * 明細のバリデーションフィルターを設定します。（必須チェックを行わない）
     */
    TantoshaIchiranEigyoDialog.detail.validationFilter = function (item, method, state, options) {
        return method !== "required";
    };

    /**
     * 明細の一覧全体のバリデーションを実行します。
     */
    TantoshaIchiranEigyoDialog.detail.validateList = function (suppressMessage) {
        var validations = [],
            options = {
                state: {
                    suppressMessage: suppressMessage,
                }
            };

        TantoshaIchiranEigyoDialog.detail.dataTable.dataTable("each", function (row, index) {
            validations.push(TantoshaIchiranEigyoDialog.detail.executeValidation(row.element.find(":input"), row.element, options));
        });

        return App.async.all(validations);
    };

</script>

<style type="text/css">
    #TantoshaIchiranEigyoDialog table.datatable td label {
        padding-top: 5px;
    }
</style>

<div class="modal fade wide" tabindex="-1" id="TantoshaIchiranEigyoDialog">
    <div class="modal-dialog" style="max-height: 85%; width: 70%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">担当者一覧（営業）</h4>
            </div>

            <div class="modal-body" style="max-height: none;">
                <div class="modal-wrap">
                    <div class="header">
                        <div class="search-criteria">
                            <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
                            <div class="row">
                                <div class="control-label col-xs-1">
                                    <label>ユーザーID</label>
                                </div>
                                <div class="control col-xs-2">
                                    <input type="tel" data-prop="id_user" class="number-right number limit-input-digit" maxlength="10" />
                                </div>

                                <div class="control-label col-xs-1">
                                    <label>担当者名</label>
                                </div>
                                <div class="control col-xs-2">
                                    <input type="text" data-prop="nm_user" />
                                </div>

                                <div class="control-label col-xs-1">
                                    <label>所属会社</label>
                                </div>
                                <div class="control col-xs-2">
                                    <select data-prop="cd_kaisha" disabled="disabled"></select>
                                </div>

                                <div class="control-label col-xs-1">
                                    <label>所属部署</label>
                                </div>
                                <div class="control col-xs-2">
                                    <select data-prop="cd_busho" disabled="disabled"></select>
                                </div>
                            </div>
                        </div>
                        <div style="position: relative; height: 50px;">
                            <button type="button" style="position: absolute; right: 5px; top: 5px;" class="btn btn-sm btn-primary search">検索</button>
                        </div>
                    </div>
                    <div class="detail">
                        <div class="control-label toolbar" style="border-left: none;">
                            <!--<i class="icon-th"></i>
                            <div class="btn-group">
                                <button type="button" class="btn btn-default btn-xs add-item">追加</button>
                                <button type="button" class="btn btn-default btn-xs del-item">削除</button>
                            </div>-->
                            <span class="data-count">
                                <span data-prop="data_count"></span>
                                <span>/</span>
                                <span data-prop="data_count_total"></span>
                            </span>
                        </div>
                        <table class="datatable ellipsis">
                            <!--TODO: 明細一覧のヘッダーを定義するHTMLをここに記述します。-->
                            <thead>
                                <tr>
                                    <!--TODO: 単一行を作成する場合は、下記を利用します。-->
                                    <th style="width: 30px;"></th>
                                    <th style="width: 90px;">ユーザーID</th>
                                    <th style="width: 200px;">担当者名</th>
                                    <th style="width: 210px;">所属会社</th>
                                    <th style="width: 210px;">所属部署</th>
                                    <th style="width: 60px;">本部権限</th>
                                    <th style="">共有メンバー</th>

                                </tr>
                            </thead>
                            <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->
                            <tbody class="item-tmpl" style="cursor: default; display: none;">
                                <tr>
                                    <!--TODO: 単一行を作成する場合は、以下を利用します。-->
                                    <td class="text-center">
                                        <input type="checkbox" data-prop="checkbox_kaisha" />
                                    </td>
                                    <td>
                                        <label data-prop="id_user" class="number-right number"></label>
                                    </td>
                                    <td>
                                        <label data-prop="nm_user" class="overflow-ellipsis"></label>
                                    </td>
                                    <td>
                                        <label data-prop="nm_kaisha"></label>
                                    </td>
                                    <td>
                                        <label data-prop="nm_busho"></label>
                                    </td>
                                    <td>
                                        <label data-prop="kbn_eigyo"></label>
                                    </td>
                                    <td>
                                        <label data-prop="nm_member" class="overflow-ellipsis"></label>
                                    </td>

                                </tr>
                            </tbody>
                        </table>
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
                <button type="button" class="btn btn-success save" name="save" disabled="disabled">選択</button>
                <button type="button" class="cancel-button btn btn-sm btn-default" data-dismiss="modal" name="close">閉じる</button>
            </div>

        </div>
    </div>
</div>
