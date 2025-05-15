<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="605_TantoEigyoKensaku_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs._605_TantoEigyoKensaku_Dialog" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchInputDialog(Ver2.1)】 Template--%>
<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var _605_TantoEigyoKensaku_Dialog = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.maxInputDataCount,   // TODO:取得するデータ数を指定します。
            filter: ""
        },
        values: {
            isChangeRunning: {},
            firstKaisha : null,
        },
        urls: {
            getKaisha: "../api/_605_TantoEigyoKensaku_Dialog/GetKaisha",
            ma_busho: "../Services/ShisaQuickService.svc/ma_busho?$filter=cd_kaisha eq {0} and flg_eigyo eq 1 &$orderby=cd_busho",
        },
        header: {
            options: {
                getData: "../api/_605_TantoEigyoKensaku_Dialog/Get",
            },
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
    _605_TantoEigyoKensaku_Dialog.validationSuccess = function (results, state) {
        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                _605_TantoEigyoKensaku_Dialog.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                    if (row && row.element) {
                        row.element.find("tr").removeClass("has-error");
                    }
                });
            } else {
                _605_TantoEigyoKensaku_Dialog.setColValidStyle(item.element);
            }

            _605_TantoEigyoKensaku_Dialog.notifyAlert.remove(item.element);
        }
    };

    /**
     * バリデーション失敗時の処理を実行します。
     */
    _605_TantoEigyoKensaku_Dialog.validationFail = function (results, state) {

        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                _605_TantoEigyoKensaku_Dialog.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                    if (row && row.element) {
                        row.element.find("tr").addClass("has-error");
                    }
                });
            } else {
                _605_TantoEigyoKensaku_Dialog.setColInvalidStyle(item.element);
            }

            if (state && state.suppressMessage) {
                continue;
            }
            _605_TantoEigyoKensaku_Dialog.notifyAlert.message(item.message, item.element).show();
        }
    };

    /**
     * バリデーション後の処理を実行します。
     */
    _605_TantoEigyoKensaku_Dialog.validationAlways = function (results) {
        //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
    };

    /**
      * 指定された定義をもとにバリデータを作成します。
      * @param target バリデーション定義
      * @param options オプションに設定する値。指定されていない場合は、
      *                画面の success/fail/always のハンドル処理が指定されたオプションが設定されます。
      */
    _605_TantoEigyoKensaku_Dialog.createValidator = function (target, options) {
        return App.validation(target, options || {
            success: _605_TantoEigyoKensaku_Dialog.validationSuccess,
            fail: _605_TantoEigyoKensaku_Dialog.validationFail,
            always: _605_TantoEigyoKensaku_Dialog.validationAlways
        });
    };

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    _605_TantoEigyoKensaku_Dialog.setColInvalidStyle = function (target) {
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
    _605_TantoEigyoKensaku_Dialog.setColValidStyle = function (target) {
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
    _605_TantoEigyoKensaku_Dialog.commands.save = function () {
        var loadingTaget = _605_TantoEigyoKensaku_Dialog.element.find(".modal-content");

        _605_TantoEigyoKensaku_Dialog.notifyAlert.clear();
        _605_TantoEigyoKensaku_Dialog.notifyInfo.clear();

        App.ui.loading.show("", loadingTaget);

        var sleep = 0;
        var condition = "Object.keys(_605_TantoEigyoKensaku_Dialog.values.isChangeRunning).length == 0";
        App.ui.wait(sleep, condition, 100)
        .then(function () {
            _605_TantoEigyoKensaku_Dialog.validateAll().then(function () {

                var changeSets = _605_TantoEigyoKensaku_Dialog.detail.data.getChangeSet();

                //TODO: データの保存処理をここに記述します。
                return $.ajax(App.ajax.webapi.post(/* TODO: データ保存サービスの URL を設定してください。, */changeSets))
                    .then(function (result) {

                        //TODO: データの保存成功時の処理をここに記述します。


                        //最後に再度データを取得しなおします。
                        return App.async.all([_605_TantoEigyoKensaku_Dialog.header.search(false)]);
                    }).then(function () {
                        _605_TantoEigyoKensaku_Dialog.notifyInfo.message(App.messages.base.MS0002).show();
                    }).fail(function (error) {

                        if (error.status === App.settings.base.conflictStatus) {
                            // TODO: 同時実行エラー時の処理を行っています。
                            // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                            _605_TantoEigyoKensaku_Dialog.header.search(false);
                            _605_TantoEigyoKensaku_Dialog.notifyAlert.clear();
                            _605_TantoEigyoKensaku_Dialog.notifyAlert.message(App.messages.base.MS0009).show();
                            return;
                        }

                        //TODO: データの保存失敗時の処理をここに記述します。
                        if (error.status === App.settings.base.validationErrorStatus) {
                            var errors = error.responseJSON;
                            $.each(errors, function (index, err) {
                                _605_TantoEigyoKensaku_Dialog.notifyAlert.message(
                                    err.Message +
                                    (App.isUndefOrNull(err.InvalidationName) ? "" : err.Data[err.InvalidationName])
                                ).show();
                            });
                            return;
                        }

                        _605_TantoEigyoKensaku_Dialog.notifyAlert.message(App.ajax.handleError(error).message).show();

                    });
            });
        }).fail(function () {
            _605_TantoEigyoKensaku_Dialog.notifyAlert.message(App.messages.base.MS0006).show();
        }).always(function () {
            setTimeout(function () {
                _605_TantoEigyoKensaku_Dialog.header.element.find(":input:first").focus();
            }, 100);
            App.ui.loading.close(loadingTaget);
        });
    };

    /**
     * すべてのバリデーションを実行します。
     */
    _605_TantoEigyoKensaku_Dialog.validateAll = function () {

        var validations = [];

        validations.push(_605_TantoEigyoKensaku_Dialog.detail.validateList());

        //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。

        return App.async.all(validations);
    };

    /**
     * ダイアログの初期化処理を行います。
     */
    _605_TantoEigyoKensaku_Dialog.initialize = function () {

        var element = $("#_605_TantoEigyoKensaku_Dialog"),
            contentHeight = $(window).height() * 80 / 100;

        _605_TantoEigyoKensaku_Dialog.element = element;
        element.find(".modal-body").css("max-height", contentHeight);
        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);


        _605_TantoEigyoKensaku_Dialog.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#_605_TantoEigyoKensaku_Dialog .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body .detail",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        _605_TantoEigyoKensaku_Dialog.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#_605_TantoEigyoKensaku_Dialog .dialog-slideup-area .alert-message",
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

        _605_TantoEigyoKensaku_Dialog.initializeControlEvent();
        _605_TantoEigyoKensaku_Dialog.header.initialize();
        _605_TantoEigyoKensaku_Dialog.loadMasterData();
        _605_TantoEigyoKensaku_Dialog.loadDialogs();
    };

    /**
     * ダイアログコントロールへのイベントの紐づけを行います。
     */
    _605_TantoEigyoKensaku_Dialog.initializeControlEvent = function () {
        var element = _605_TantoEigyoKensaku_Dialog.element;

        //TODO: 画面全体で利用するコントロールのイベントの紐づけ処理をここに記述します。
        element.on("hidden.bs.modal", _605_TantoEigyoKensaku_Dialog.hidden);
        element.on("shown.bs.modal", _605_TantoEigyoKensaku_Dialog.shown);
        element.on("show.bs.modal", _605_TantoEigyoKensaku_Dialog.show);
        element.on("click", ".save", _605_TantoEigyoKensaku_Dialog.commands.save);
    };

    /**
     * ダイアログ非表示時処理を実行します。
     */
    _605_TantoEigyoKensaku_Dialog.hidden = function (e) {
        var element = _605_TantoEigyoKensaku_Dialog.element;

        //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
        element.find(":input:not([type='checkbox']):not([type='radio'])").val("");
        element.find("input[type='checkbox']").prop("checked", false);
        //TODO:radioボタンの初期表示は、画面要件に合わせて記述します。

        _605_TantoEigyoKensaku_Dialog.detail.dataTable.dataTable("clear");

        element.findP("data_count").text("");
        element.findP("data_count_total").text("");

        var items = element.find(".search-criteria :input:not(button)");
        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            _605_TantoEigyoKensaku_Dialog.setColValidStyle(item);
        }

        _605_TantoEigyoKensaku_Dialog.element.find(".save").prop("disabled", true);
        _605_TantoEigyoKensaku_Dialog.notifyInfo.clear();
        _605_TantoEigyoKensaku_Dialog.notifyAlert.clear();
    };

    /**
     * ダイアログ表示時処理を実行します。
     */
    _605_TantoEigyoKensaku_Dialog.shown = function (e) {
        //初回起動時にdatatable作成
        if (App.isUndefOrNull(_605_TantoEigyoKensaku_Dialog.detail.fixedColumnIndex)) {
            _605_TantoEigyoKensaku_Dialog.detail.initialize();
        }

        _605_TantoEigyoKensaku_Dialog.element.find(":input:not(button):first").focus();
    };

    _605_TantoEigyoKensaku_Dialog.show = function () {
        var cd_kaisha = _605_TantoEigyoKensaku_Dialog.header.element.findP("cd_kaisha");
        cd_kaisha.val(_605_TantoEigyoKensaku_Dialog.values.firstKaisha).change();
    }

    /**
     * マスターデータのロード処理を実行します。
     */
    _605_TantoEigyoKensaku_Dialog.loadMasterData = function () {

        //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
        //return $.ajax(App.ajax.odata.get(/* マスターデータ取得サービスの URL */)).then(function (result) {
        //    var cd_shiharai = _605_TantoEigyoKensaku_Dialog.element.findP("cd_shiharai");
        //    cd_shiharai.children().remove();
        //    App.ui.appendOptions(
        //        cd_shiharai,
        //        "cd_shiharai",
        //        "nm_joken_shiharai",
        //        result.value,
        //        true
        //    );
        //});
        var element = _605_TantoEigyoKensaku_Dialog.element;
        return $.ajax(App.ajax.webapi.get(_605_TantoEigyoKensaku_Dialog.urls.getKaisha)).then(function (result) {
            var cd_kaisha = element.findP("cd_kaisha");
            cd_kaisha.children().remove();
            App.ui.appendOptions(
                cd_kaisha,
                "cd_kaisha",
                "nm_kaisha",
                result,
                false
             );
            _605_TantoEigyoKensaku_Dialog.values.firstKaisha =result[0].cd_kaisha;
            return $.ajax(App.ajax.odata.get(App.str.format(_605_TantoEigyoKensaku_Dialog.urls.ma_busho, _605_TantoEigyoKensaku_Dialog.values.firstKaisha)))
        }).then(function (result) {
            var cd_busho = element.findP("cd_busho");
            cd_busho.children().remove();
            App.ui.appendOptions(
                cd_busho,
                "cd_busho",
                "nm_busho",
                result.value,
                true
            );
        });
        //TODO: マスタデータのロード処理を実装後、下の1行を削除してください。
        return App.async.success();
    };

    /**
     * 共有のダイアログのロード処理を実行します。
     */
    _605_TantoEigyoKensaku_Dialog.loadDialogs = function () {

        if ($.find("#ConfirmDialog").length == 0) {
            return App.async.all({
                confirmDialog: $.get(_605_TantoEigyoKensaku_Dialog.urls.confirmDialog)
            }).then(function (result) {
                $("#dialog-container").append(result.successes.confirmDialog);
                _605_TantoEigyoKensaku_Dialog.dialogs.confirmDialog = ConfirmDialog;
            });
        } else {
            _605_TantoEigyoKensaku_Dialog.dialogs.confirmDialog = ConfirmDialog;
        }
    };

    /**
     * ダイアログヘッダー　バリデーションルールを定義します。
     */
    _605_TantoEigyoKensaku_Dialog.header.options.validations = {
        nm_user: {
            rules: {
                maxlength : 60
            },
            options: {
                name: "担当者名"
                
            },
            messages: {
                maxlength: App.messages.base.maxlength
            }
        }
    };

    /**
     * ダイアログヘッダーの初期化処理を行います。
     */
    _605_TantoEigyoKensaku_Dialog.header.initialize = function () {

        var element = _605_TantoEigyoKensaku_Dialog.element.find(".header");
        _605_TantoEigyoKensaku_Dialog.header.validator = element.validation(_605_TantoEigyoKensaku_Dialog.createValidator(_605_TantoEigyoKensaku_Dialog.header.options.validations));
        _605_TantoEigyoKensaku_Dialog.header.element = element;

        //TODO: 画面ヘッダーの初期化処理をここに記述します。
        //TODO: 画面ヘッダーで利用するコントロールのイベントの紐づけ処理をここに記述します。
        element.on("click", ".search", _605_TantoEigyoKensaku_Dialog.header.search);
        element.on("change", ":input", _605_TantoEigyoKensaku_Dialog.header.change);
        
    };

    //change header
    _605_TantoEigyoKensaku_Dialog.header.change = function (e) {
        var element = _605_TantoEigyoKensaku_Dialog.header.element,
            target = $(e.target),
            property = target.attr("data-prop");


        element.validation().validate({
            targets: target
        }).then(function () {
            if (property == "cd_kaisha") {
                $.ajax(App.ajax.odata.get(App.str.format(_605_TantoEigyoKensaku_Dialog.urls.ma_busho, element.findP("cd_kaisha").val())))
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
                });
            }
        })
    }


    /**
     * ダイアログの検索処理を実行します。
     */
    _605_TantoEigyoKensaku_Dialog.header.search = function (isLoading) {
        var element = _605_TantoEigyoKensaku_Dialog.element,
            loadingTaget = element.find(".modal-content"),
            deferred = $.Deferred(),
            query;

        _605_TantoEigyoKensaku_Dialog.header.validator.validate().done(function () {

            _605_TantoEigyoKensaku_Dialog.options.filter = _605_TantoEigyoKensaku_Dialog.header.createFilter();
            

            _605_TantoEigyoKensaku_Dialog.detail.dataTable.dataTable("clear");
            if (isLoading) {
                App.ui.loading.show("", loadingTaget);
                _605_TantoEigyoKensaku_Dialog.notifyAlert.clear();
            }

            $.ajax(App.ajax.webapi.get(_605_TantoEigyoKensaku_Dialog.header.options.getData, _605_TantoEigyoKensaku_Dialog.options.filter))
            .done(function (result) {

                _605_TantoEigyoKensaku_Dialog.detail.bind(result);
                deferred.resolve();
            }).fail(function (error) {

                _605_TantoEigyoKensaku_Dialog.notifyAlert.message(App.ajax.handleError(error).message).show();
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
    _605_TantoEigyoKensaku_Dialog.header.createFilter = function () {
        var element = _605_TantoEigyoKensaku_Dialog.header.element,
            criteria = element.form().data(),
            filters = {};

        return filters = { 
            nm_user     : criteria.nm_user,
            cd_kaisha   : criteria.cd_kaisha,
            cd_busho    : criteria.cd_busho
        };
    };

    /**
     * ダイアログヘッダー　バリデーションルールを定義します。
     */
    _605_TantoEigyoKensaku_Dialog.detail.options.validations = {
    };

    /**
     * 画面明細の初期化処理を行います。
     */
    _605_TantoEigyoKensaku_Dialog.detail.initialize = function () {

        var element = _605_TantoEigyoKensaku_Dialog.element.find(".detail"),
            table = element.find(".datatable"),
            offsetHeight = $(window).height() * 45 / 100,
            datatable = table.dataTable({
                height: 100,
                resize: true,
                resizeOffset: offsetHeight,
                //列固定横スクロールにする場合は、下記3行をコメント解除
                //fixedColumn: true,               //列固定の指定
                //fixedColumns: 1,                 //固定位置を指定（左端を0としてカウント）
                //innerWidth: 530,                 //可動列の合計幅を指定
                onselect: _605_TantoEigyoKensaku_Dialog.detail.select,
                onchange: _605_TantoEigyoKensaku_Dialog.detail.change
            });
        table = element.find(".datatable");        //列固定にした場合DOM要素が再作成されるため、変数を再取得

        _605_TantoEigyoKensaku_Dialog.detail.validator = element.validation(_605_TantoEigyoKensaku_Dialog.createValidator(_605_TantoEigyoKensaku_Dialog.detail.options.validations));
        _605_TantoEigyoKensaku_Dialog.detail.element = element;
        _605_TantoEigyoKensaku_Dialog.detail.dataTable = datatable;
        // 行選択時に利用するテーブルインデックスを指定します
        _605_TantoEigyoKensaku_Dialog.detail.fixedColumnIndex = element.find(".fix-columns").length;

        //TODO: 画面明細で利用するコントロールのイベントの紐づけ処理をここに記述します。
        element.on("click", ".add-item", _605_TantoEigyoKensaku_Dialog.detail.addNewItem);
        element.on("click", ".del-item", _605_TantoEigyoKensaku_Dialog.detail.deleteItem);
        element.on("click", ".select", _605_TantoEigyoKensaku_Dialog.detail.selectData);

        //TODO: 画面明細の初期化処理をここに記述します。
        _605_TantoEigyoKensaku_Dialog.detail.bind([], true);
    };

    /**
     * ダイアログ明細へのデータバインド処理を行います。
     */
    _605_TantoEigyoKensaku_Dialog.detail.bind = function (data, isNewData) {
        var i, l, item, dataSet, dataCount, count = 0, dataBind = [];

        dataCount = data.Count ? data.Count : 0;
        data = (data.Items) ? data.Items : data;

        for (var i = 0; i < data.length && i < App.settings.base.maxSearchDataCount; i++) {
            dataBind.push(data[i]);
        }

        dataSet = App.ui.page.dataSet();
        _605_TantoEigyoKensaku_Dialog.detail.data = dataSet;
        _605_TantoEigyoKensaku_Dialog.detail.dataTable.dataTable("clear");

        _605_TantoEigyoKensaku_Dialog.detail.dataTable.dataTable("addRows", dataBind, function (row, item) {
            
            (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
            row.form(_605_TantoEigyoKensaku_Dialog.detail.options.bindOption).bind(item);
            return row;
        }, true);

        if (!isNewData) {
            _605_TantoEigyoKensaku_Dialog.header.element.findP("data_count").text(dataBind.length);
            _605_TantoEigyoKensaku_Dialog.header.element.findP("data_count_total").text(dataCount);
        }

        if (dataCount >= App.settings.base.maxSearchDataCount) {
            _605_TantoEigyoKensaku_Dialog.notifyInfo.clear();
            _605_TantoEigyoKensaku_Dialog.notifyInfo.message(App.str.format(App.messages.app.AP0021, dataCount)).show();
        }

        //TODO: 画面明細へのデータバインド処理をここに記述します。


        //バリデーションを実行します。
        _605_TantoEigyoKensaku_Dialog.detail.validateList(true);
    };

    /**
     * 明細の各行にデータを設定する際のオプションを定義します。
     */
    _605_TantoEigyoKensaku_Dialog.detail.options.bindOption = {
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
    _605_TantoEigyoKensaku_Dialog.detail.selectData = function (e) {
            var target = $(e.target),
                id, entity,
                selectData = function (entity) {
                    if (App.isFunc(_605_TantoEigyoKensaku_Dialog.dataSelected)) {
                        if (!_605_TantoEigyoKensaku_Dialog.dataSelected(entity)) {
                            _605_TantoEigyoKensaku_Dialog.element.modal("hide");
                        }
                    } else {
                        _605_TantoEigyoKensaku_Dialog.element.modal("hide");
                    }
                };

            _605_TantoEigyoKensaku_Dialog.detail.dataTable.dataTable("getRow", target, function (row) {
                id = row.element.attr("data-key");
                entity = _605_TantoEigyoKensaku_Dialog.detail.data.entry(id);
            });

            if (App.isUndef(id)) {
                return;
            }
            if (_605_TantoEigyoKensaku_Dialog.detail.data.isUpdated(id)) {
                var options = {
                    text: App.messages.base.MS0024
                };
                _605_TantoEigyoKensaku_Dialog.dialogs.confirmDialog.confirm(options)
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
    _605_TantoEigyoKensaku_Dialog.detail.select = function (e, row) {
        $($(row.element[_605_TantoEigyoKensaku_Dialog.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab.selected")).removeClass("selected").addClass("unselected");
        $(row.element[_605_TantoEigyoKensaku_Dialog.detail.fixedColumnIndex].querySelectorAll(".select-tab")).removeClass("unselected").addClass("selected");

        //選択行全体に背景色を付ける場合は以下のコメントアウトを解除します。
        //if (!App.isUndefOrNull(_605_TantoEigyoKensaku_Dialog.detail.selectedRow)) {
        //    _605_TantoEigyoKensaku_Dialog.detail.selectedRow.element.find(".selected-row").removeClass("selected-row");
        //}
        //row.element.find("tr").addClass("selected-row");
        //_605_TantoEigyoKensaku_Dialog.detail.selectedRow = row;
    };

    /**
     * 明細の一覧の入力項目の変更イベントの処理を行います。
     */
    _605_TantoEigyoKensaku_Dialog.detail.change = function (e, row) {
        var target = $(e.target),
            id = row.element.attr("data-key"),
            property = target.attr("data-prop"),
            entity = _605_TantoEigyoKensaku_Dialog.detail.data.entry(id),
            options = {
                filter: _605_TantoEigyoKensaku_Dialog.detail.validationFilter
            };

        _605_TantoEigyoKensaku_Dialog.values.isChangeRunning[property] = true;

        _605_TantoEigyoKensaku_Dialog.detail.executeValidation(target, row)
        .then(function () {
            entity[property] = row.element.form().data()[property];
            _605_TantoEigyoKensaku_Dialog.detail.data.update(entity);

            if (_605_TantoEigyoKensaku_Dialog.element.find(".save").is(":disabled")) {
                _605_TantoEigyoKensaku_Dialog.element.find(".save").prop("disabled", false);
            }

            //入力行の他の項目のバリデーション（必須チェック以外）を実施します
            _605_TantoEigyoKensaku_Dialog.detail.executeValidation(row.element.find(":input"), row, options);
        }).always(function () {
            delete _605_TantoEigyoKensaku_Dialog.values.isChangeRunning[property];
        });
    };

    /**
     * 明細の一覧に新規データを追加します。
     */
    _605_TantoEigyoKensaku_Dialog.detail.addNewItem = function () {
        //TODO:新規データおよび初期値を設定する処理を記述します。
        var newData = {
            //no_seq: page.values.no_seq
        };

        _605_TantoEigyoKensaku_Dialog.detail.data.add(newData);
        _605_TantoEigyoKensaku_Dialog.detail.dataTable.dataTable("addRow", function (row) {
            row.form(_605_TantoEigyoKensaku_Dialog.detail.options.bindOption).bind(newData);
            return row;
        }, true);

        if (_605_TantoEigyoKensaku_Dialog.element.find(".save").is(":disabled")) {
            _605_TantoEigyoKensaku_Dialog.element.find(".save").prop("disabled", false);
        }
    };

    /**
     * 明細の一覧で選択されている行とデータを削除します。
     */
    _605_TantoEigyoKensaku_Dialog.detail.deleteItem = function (e) {
        var element = _605_TantoEigyoKensaku_Dialog.detail.element,
            selected = element.find(".datatable .select-tab.selected").closest("tbody");

        if (!selected.length) {
            return;
        }

        _605_TantoEigyoKensaku_Dialog.detail.dataTable.dataTable("deleteRow", selected, function (row) {
            var id = row.attr("data-key"),
                newSelected;

            row.find(":input").each(function (i, elem) {
                _605_TantoEigyoKensaku_Dialog.notifyAlert.remove(elem);
            });

            if (!App.isUndefOrNull(id)) {
                var entity = _605_TantoEigyoKensaku_Dialog.detail.data.entry(id);
                _605_TantoEigyoKensaku_Dialog.detail.data.remove(entity);
            }

            newSelected = row.next().not(".item-tmpl");
            if (!newSelected.length) {
                newSelected = row.prev().not(".item-tmpl");
            }
            if (newSelected.length) {
                for (var i = _605_TantoEigyoKensaku_Dialog.detail.fixedColumnIndex; i > -1; i--) {
                    if ($(newSelected[i]).find(":focusable:first").length) {
                        $(newSelected[i]).find(":focusable:first").focus();
                        break;
                    }
                }
            }
        });

        if (_605_TantoEigyoKensaku_Dialog.element.find(".save").is(":disabled")) {
            _605_TantoEigyoKensaku_Dialog.element.find(".save").prop("disabled", false);
        }

    };

    /**
    * 明細のバリデーションを実行します。
    */
    _605_TantoEigyoKensaku_Dialog.detail.executeValidation = function (targets, row, options) {
        var defaultOptions = {
            targets: targets,
            state: {
                tbody: row,
                isGridValidation: true
            }
        },
            execOptions = $.extend(true, {}, defaultOptions, options);

        return _605_TantoEigyoKensaku_Dialog.detail.validator.validate(execOptions);
    };

    /**
     * 明細のバリデーションフィルターを設定します。（必須チェックを行わない）
     */
    _605_TantoEigyoKensaku_Dialog.detail.validationFilter = function (item, method, state, options) {
        return method !== "required";
    };

    /**
     * 明細の一覧全体のバリデーションを実行します。
     */
    _605_TantoEigyoKensaku_Dialog.detail.validateList = function (suppressMessage) {
        var validations = [],
            options = {
                state: {
                    suppressMessage: suppressMessage,
                }
            };

        _605_TantoEigyoKensaku_Dialog.detail.dataTable.dataTable("each", function (row, index) {
            validations.push(_605_TantoEigyoKensaku_Dialog.detail.executeValidation(row.element.find(":input"), row.element, options));
        });

        return App.async.all(validations);
    };

</script>

<div class="modal fade wide smaller" tabindex="-1" id="_605_TantoEigyoKensaku_Dialog">
    <div class="modal-dialog" style="max-height: 85%; width: 55%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">担当営業検索</h4>
            </div>

            <div class="modal-body">
                <div class="modal-wrap">
                    <div class="header">
                        <div class="search-criteria">
                            <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
                            <div class="row">
                                <div class="control-label col-xs-2">
                                    <label>担当者名</label>
                                </div>
                                <div class="control col-xs-3">
                                    <input type="text" data-prop="nm_user" />
                                </div>
                                <div class="control col-xs-7">
                                </div>
                            </div>
                            <div class="row">
                                <div class="control-label col-xs-2">
                                    <label>所属会社</label>
                                </div>
                                <div class="control col-xs-3">
                                    <select data-prop="cd_kaisha"></select>
                                </div>
                                <div class="control col-xs-7">
                                </div>
                            </div>
                            <div class="row">
                                <div class="control-label col-xs-2">
                                    <label>所属部署</label>
                                </div>
                                <div class="control col-xs-3">
                                    <select data-prop="cd_busho"></select>
                                </div>
                                <div class="control col-xs-7">
                                </div>
                            </div>
                        </div>
                        <div style="position: relative; height: 50px;">
                           <button type="button" style="position: absolute; right: 5px; top: 5px;" class="btn btn-sm btn-primary search">検索</button>
                            <div class="data-count">
                                <span data-prop="data_count"></span>
                                <span>/</span>
                                <span data-prop="data_count_total"></span>
                                </div>
                            </div>
                            </div>
                    <div class="detail">
                       
                        <table class="datatable">
                            <!--TODO: 明細一覧のヘッダーを定義するHTMLをここに記述します。-->
                            <thead>
                                <tr>
                                    <!--TODO: 単一行を作成する場合は、下記を利用します。-->
                                    <th style="width: 40px;"></th>
                                    <th style="width: 30px;">No</th>
                                    <th >担当者名</th>
                                    <th style="width: 20%;">所属会社</th>
                                    <th style="width: 20%;">所属部署</th>
                                    <th style="width:70px">本部権限</th>
                                    <th style="width: 20%;">担当上司</th>

                                    <!--TODO: 多段行を作成する場合は、以下を利用し、上記１行は削除します。
                                    <th rowspan="2" style="width: 10px"></th>
                       	        </tr>
                                <tr>
                                -->
                                </tr>
                            </thead>
                            <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->
                            <tbody class="item-tmpl" style="cursor: default; display: none;">
                                <tr>
                                    <!--TODO: 単一行を作成する場合は、以下を利用します。-->
                                    <%-- <td>
                                        <span class="select-tab unselected"></span>
                                    </td>--%>
                                    <!--TODO: 多段行を作成する場合は、以下を利用し、上記３行は削除します。
                                    <td rowspan="2">
                                        <span class="select-tab-2lines unselected"></span>
                                    </td>
                                </tr>
                                <tr> -->
                                    <td class="center" style="width: 40px;">
                                        <button class="btn btn-xs btn-success select ">選択</button>
                                    </td>
                                    <td class="right" style="width: 30px;">
                                        <label data-prop="RN"></label>
                                    </td>
                                    <td>
                                        <label class="overflow-ellipsis" data-prop="nm_user"></label>
                                    </td>
                                    <td style="width: 20%;">
                                        <label  class="overflow-ellipsis" data-prop="nm_kaisha"></label>
                                    </td>
                                    <td style="width: 20%;">
                                        <label  class="overflow-ellipsis" data-prop="nm_busho"></label>
                                    </td>
                                    <td style="width:70px">
                                        <label data-prop="kbn_eigyo"></label>
                                    </td>
                                    <td style="width: 20%;">
                                        <label class="overflow-ellipsis" data-prop="nm_josi"></label>
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
                <%--<button type="button" class="btn btn-success save" name="save" disabled="disabled">保存</button>--%>
                <button type="button" class="cancel-button btn btn-sm btn-default" data-dismiss="modal" name="close">閉じる</button>
            </div>

        </div>
    </div>
</div>
