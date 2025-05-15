<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="805_ShikakarihinSentaku_Dialog.aspx.cs" Inherits="Tos.Web.Pages._805_ShikakarihinSentaku_Dialog" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchInputDialog(Ver2.1)】 Template--%>
<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var _805_ShikakarihinSentaku_Dialog = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.maxInputDataCount,   // TODO:取得するデータ数を指定します。
            filter: ""
        },
        values: {
            isChangeRunning: {}
        },
        urls: {
            search: "../api/_805_ShikakarihinSentaku_Dialog"
        },
        param: {
            no_seiho_copy: "0001-E19-05-0002",
            no_seiho_sakusei: "0001-E19-05-0002"
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
    _805_ShikakarihinSentaku_Dialog.validationSuccess = function (results, state) {
        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                _805_ShikakarihinSentaku_Dialog.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                    if (row && row.element) {
                        row.element.find("tr").removeClass("has-error");
                    }
                });
            } else {
                _805_ShikakarihinSentaku_Dialog.setColValidStyle(item.element);
            }

            _805_ShikakarihinSentaku_Dialog.notifyAlert.remove(item.element);
        }
    };

    /**
     * バリデーション失敗時の処理を実行します。
     */
    _805_ShikakarihinSentaku_Dialog.validationFail = function (results, state) {

        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                _805_ShikakarihinSentaku_Dialog.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                    if (row && row.element) {
                        row.element.find("tr").addClass("has-error");
                    }
                });
            } else {
                _805_ShikakarihinSentaku_Dialog.setColInvalidStyle(item.element);
            }

            if (state && state.suppressMessage) {
                continue;
            }
            _805_ShikakarihinSentaku_Dialog.notifyAlert.message(item.message, item.element).show();
        }
    };

    /**
     * バリデーション後の処理を実行します。
     */
    _805_ShikakarihinSentaku_Dialog.validationAlways = function (results) {
        //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
    };

    /**
      * 指定された定義をもとにバリデータを作成します。
      * @param target バリデーション定義
      * @param options オプションに設定する値。指定されていない場合は、
      *                画面の success/fail/always のハンドル処理が指定されたオプションが設定されます。
      */
    _805_ShikakarihinSentaku_Dialog.createValidator = function (target, options) {
        return App.validation(target, options || {
            success: _805_ShikakarihinSentaku_Dialog.validationSuccess,
            fail: _805_ShikakarihinSentaku_Dialog.validationFail,
            always: _805_ShikakarihinSentaku_Dialog.validationAlways
        });
    };

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    _805_ShikakarihinSentaku_Dialog.setColInvalidStyle = function (target) {
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
    _805_ShikakarihinSentaku_Dialog.setColValidStyle = function (target) {
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
    _805_ShikakarihinSentaku_Dialog.validateAll = function () {

        var validations = [];

        validations.push(_805_ShikakarihinSentaku_Dialog.detail.validateList());

        //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。

        return App.async.all(validations);
    };

    /**
     * ダイアログの初期化処理を行います。
     */
    _805_ShikakarihinSentaku_Dialog.initialize = function () {

        var element = $("#_805_ShikakarihinSentaku_Dialog"),
            contentHeight = $(window).height() * 80 / 100;

        _805_ShikakarihinSentaku_Dialog.element = element;
        element.find(".modal-body").css("max-height", contentHeight);
        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        _805_ShikakarihinSentaku_Dialog.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#_805_ShikakarihinSentaku_Dialog .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body .detail",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        _805_ShikakarihinSentaku_Dialog.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#_805_ShikakarihinSentaku_Dialog .dialog-slideup-area .alert-message",
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

        _805_ShikakarihinSentaku_Dialog.initializeControlEvent();
        _805_ShikakarihinSentaku_Dialog.loadMasterData();
    };

    /**
     * ダイアログコントロールへのイベントの紐づけを行います。
     */
    _805_ShikakarihinSentaku_Dialog.initializeControlEvent = function () {
        var element = _805_ShikakarihinSentaku_Dialog.element;

        //TODO: 画面全体で利用するコントロールのイベントの紐づけ処理をここに記述します。
        element.on("hidden.bs.modal", _805_ShikakarihinSentaku_Dialog.hidden);
        element.on("shown.bs.modal", _805_ShikakarihinSentaku_Dialog.shown);
        element.on("show.bs.modal", _805_ShikakarihinSentaku_Dialog.show);
        element.on("click", "[name='select']", _805_ShikakarihinSentaku_Dialog.commands.select);
    };

    /**
     * ダイアログ非表示時処理を実行します。
     */
    _805_ShikakarihinSentaku_Dialog.hidden = function (e) {
        var element = _805_ShikakarihinSentaku_Dialog.element;

        //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
        element.find(":input:not([type='checkbox']):not([type='radio'])").val("");
        element.find("input[type='checkbox']").prop("checked", false);
        //TODO:radioボタンの初期表示は、画面要件に合わせて記述します。

        _805_ShikakarihinSentaku_Dialog.detail.dataTable.dataTable("clear");

        //var items = element.find(".search-criteria :input:not(button)");
        //for (var i = 0; i < items.length; i++) {
        //    var item = items[i];
        //    _805_ShikakarihinSentaku_Dialog.setColValidStyle(item);
        //}

        _805_ShikakarihinSentaku_Dialog.element.find("[name='select']").prop("disabled", true);
        _805_ShikakarihinSentaku_Dialog.notifyInfo.clear();
        _805_ShikakarihinSentaku_Dialog.notifyAlert.clear();
    };

    /**
     * Search when show dialog
     */
    _805_ShikakarihinSentaku_Dialog.show = function (e) {
        _805_ShikakarihinSentaku_Dialog.isEmptyHaigo = false;
    }

    /**
     * ダイアログ表示時処理を実行します。
     */
    _805_ShikakarihinSentaku_Dialog.shown = function (e) {
        //初回起動時にdatatable作成
        if (App.isUndefOrNull(_805_ShikakarihinSentaku_Dialog.detail.fixedColumnIndex)) {
            _805_ShikakarihinSentaku_Dialog.detail.initialize();
        }
        $(window).resize();
        //setTimeout(function () {
        _805_ShikakarihinSentaku_Dialog.header.search(true);
        //}, 300);
    };

    /**
     * マスターデータのロード処理を実行します。
     */
    _805_ShikakarihinSentaku_Dialog.loadMasterData = function () {
        return App.async.success();
    };

    /**
     * ダイアログヘッダー　バリデーションルールを定義します。
     */
    _805_ShikakarihinSentaku_Dialog.header.options.validations = {
    };

    /**
     * ダイアログの検索処理を実行します。
     */
    _805_ShikakarihinSentaku_Dialog.header.search = function (isLoading) {
        var element = _805_ShikakarihinSentaku_Dialog.element,
            loadingTaget = element.find(".modal-content"),
            deferred = $.Deferred(),
            url = _805_ShikakarihinSentaku_Dialog.urls.search;


        _805_ShikakarihinSentaku_Dialog.options.filter = _805_ShikakarihinSentaku_Dialog.header.createFilter();

        _805_ShikakarihinSentaku_Dialog.detail.dataTable.dataTable("clear");
        if (isLoading) {
            App.ui.loading.show("", loadingTaget);
            _805_ShikakarihinSentaku_Dialog.notifyAlert.clear();
        }

        $.ajax(App.ajax.webapi.get(url, _805_ShikakarihinSentaku_Dialog.options.filter))
        .done(function (result) {

            _805_ShikakarihinSentaku_Dialog.detail.bind(result);
            deferred.resolve();
        }).fail(function (error) {

            _805_ShikakarihinSentaku_Dialog.notifyAlert.message(App.ajax.handleError(error).message).show();
            deferred.reject();
        }).always(function () {

            if (isLoading) {
                App.ui.loading.close(loadingTaget);
            }
        });

        return deferred.promise();
    };

    /**
     * Set return value when click [コピー] btn
     */
    _805_ShikakarihinSentaku_Dialog.commands.select = function () {
        _805_ShikakarihinSentaku_Dialog.validateAll().then(function () {
            var selectedItems = _805_ShikakarihinSentaku_Dialog.detail.data.findAll(function (item, entity) {
                return (item.sentaku == true);
            });
            var result = [];
            $.each(selectedItems, function (ind, item) {
                result.push({
                    cd_haigo_moto: item.src_cd_haigo,
                    cd_haigo_saki: item.cd_hin
                })
            });
            _805_ShikakarihinSentaku_Dialog.element.modal("hide");
            if (_805_ShikakarihinSentaku_Dialog.dataSelected) {
                _805_ShikakarihinSentaku_Dialog.dataSelected(result);
            };
        });
    }

    /**
     * ダイアログの検索条件を組み立てます
     */
    _805_ShikakarihinSentaku_Dialog.header.createFilter = function () {
        var param = _805_ShikakarihinSentaku_Dialog.param,
            filter = {
                no_seiho_copy: param.no_seiho_copy,
                no_seiho_sakusei: param.no_seiho_sakusei,
                kbn_haigo: App.settings.app.kbnHin.haigo,
                kbn_shikakari: App.settings.app.kbnHin.shikakari,
                loop_count: App.settings.app.tenkaiLoopCount
            };
        return filter;
    };

    /**
     * ダイアログヘッダー　バリデーションルールを定義します。
     */
    _805_ShikakarihinSentaku_Dialog.detail.options.validations = {
        src_cd_haigo: {
            rules: {
                requiredCustom: function (value, opts, state, done) {
                    var tbody = state.tbody;
                    if (tbody.findP("sentaku").is(":checked")) {
                        return done(value ? true : false);
                    }
                    done(true);
                }
            },
            options: {
                strName: "紐づける仕掛品工程"
            },
            messages: {
                requiredCustom: App.messages.app.AP0028
            }
        }
    };

    /**
     * 画面明細の初期化処理を行います。
     */
    _805_ShikakarihinSentaku_Dialog.detail.initialize = function () {

        var element = _805_ShikakarihinSentaku_Dialog.element.find(".detail"),
            table = element.find(".datatable"),
            offsetHeight = $(window).height() * 40 / 100,
            datatable = table.dataTable({
                height: 300,
                resize: true,
                resizeOffset: offsetHeight,
                onselect: _805_ShikakarihinSentaku_Dialog.detail.select,
                onchange: _805_ShikakarihinSentaku_Dialog.detail.change
            });
        table = element.find(".datatable");        //列固定にした場合DOM要素が再作成されるため、変数を再取得

        _805_ShikakarihinSentaku_Dialog.detail.validator = element.validation(_805_ShikakarihinSentaku_Dialog.createValidator(_805_ShikakarihinSentaku_Dialog.detail.options.validations));
        _805_ShikakarihinSentaku_Dialog.detail.element = element;
        _805_ShikakarihinSentaku_Dialog.detail.dataTable = datatable;
        // 行選択時に利用するテーブルインデックスを指定します
        _805_ShikakarihinSentaku_Dialog.detail.fixedColumnIndex = element.find(".fix-columns").length;

        element.on("click", ".sentaku_all", _805_ShikakarihinSentaku_Dialog.detail.checkAll);

        //TODO: 画面明細の初期化処理をここに記述します。
        //_805_ShikakarihinSentaku_Dialog.detail.bind([], true);
    };

    _805_ShikakarihinSentaku_Dialog.detail.checkAll = function (e) {
        var target = $(e.target),
            isChecked = target.is(":checked");

        if (_805_ShikakarihinSentaku_Dialog.isEmptyHaigo) {
            return;
        }

        _805_ShikakarihinSentaku_Dialog.detail.dataTable.dataTable("each", function (row) {
            var data = _805_ShikakarihinSentaku_Dialog.detail.data.entry(row.element.attr("data-key"));
            data.sentaku = isChecked;
            row.element.findP("sentaku").prop("checked", isChecked);
        });
        _805_ShikakarihinSentaku_Dialog.element.find("[name='select']").prop("disabled", !isChecked);
    }

    /**
     * ダイアログ明細へのデータバインド処理を行います。
     */
    _805_ShikakarihinSentaku_Dialog.detail.bind = function (data, isNewData) {
        var desc_data = data ? data.desc_haigo : [],
            src_data = data ? data.src_haigo : [],
            element = _805_ShikakarihinSentaku_Dialog.element,
            haigo_cbb = element.findP("src_cd_haigo");

        dataSet = App.ui.page.dataSet();
        _805_ShikakarihinSentaku_Dialog.detail.data = dataSet;
        _805_ShikakarihinSentaku_Dialog.detail.dataTable.dataTable("clear");

        /// 紐づける仕掛品コンボボックス
        haigo_cbb.children().remove();
        $.each(src_data, function (ind, item) {
            // 配合コード(cd_haigo)＋'　'＋配合名(nm_haigo)
            item.src_nm_haigo = item.cd_hin + "　" + item.nm_hin;
        })
        App.ui.appendOptions(
            haigo_cbb,
            "cd_hin",
            "src_nm_haigo",
            src_data,
            true
        );

        _805_ShikakarihinSentaku_Dialog.detail.dataTable.dataTable("addRows", desc_data, function (row, item) {

            (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
            // 配合コード(cd_haigo)＋'　'＋配合名(nm_haigo)
            item.desc_cd_haigo = item.cd_hin + "　" + item.nm_hin;
            row.form(_805_ShikakarihinSentaku_Dialog.detail.options.bindOption).bind(item);
            return row;
        }, true);

        if (!desc_data || desc_data.length == 0) {
            _805_ShikakarihinSentaku_Dialog.notifyInfo.message(App.messages.app.AP0097, "805_AP0097").show();
            _805_ShikakarihinSentaku_Dialog.isEmptyHaigo = true;
        } else {
            element.find(".sentaku_all").prop("disabled", false);
        }

        setTimeout(function () {
            _805_ShikakarihinSentaku_Dialog.element.find("[data-prop='src_cd_haigo']:visible:first").focus();
        }, 1)

    };

    /**
     * 明細の各行にデータを設定する際のオプションを定義します。
     */
    _805_ShikakarihinSentaku_Dialog.detail.options.bindOption = {
        converters: {
            sentaku: function (element, defVal) {
                return element.is(":checked") ? true : false;
            }
        }
    };

    /**
     * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
     */
<%--        _805_ShikakarihinSentaku_Dialog.detail.selectData = function (e) {
            var target = $(e.target),
                id, entity,
                selectData = function (entity) {
                    if (App.isFunc(_805_ShikakarihinSentaku_Dialog.dataSelected)) {
                        if (!_805_ShikakarihinSentaku_Dialog.dataSelected(entity)) {
                            _805_ShikakarihinSentaku_Dialog.element.modal("hide");
                        }
                    } else {
                        _805_ShikakarihinSentaku_Dialog.element.modal("hide");
                    }
                };

            _805_ShikakarihinSentaku_Dialog.detail.dataTable.dataTable("getRow", target, function (row) {
                id = row.element.attr("data-key");
                entity = _805_ShikakarihinSentaku_Dialog.detail.data.entry(id);
            });

            if (App.isUndef(id)) {
                return;
            }
            if (_805_ShikakarihinSentaku_Dialog.detail.data.isUpdated(id)) {
                var options = {
                    text: App.messages.base.MS0024
                };
                _805_ShikakarihinSentaku_Dialog.dialogs.confirmDialog.confirm(options)
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
    _805_ShikakarihinSentaku_Dialog.detail.select = function (e, row) {
        $($(row.element[_805_ShikakarihinSentaku_Dialog.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab.selected")).removeClass("selected").addClass("unselected");
        $(row.element[_805_ShikakarihinSentaku_Dialog.detail.fixedColumnIndex].querySelectorAll(".select-tab")).removeClass("unselected").addClass("selected");

        //選択行全体に背景色を付ける場合は以下のコメントアウトを解除します。
        //if (!App.isUndefOrNull(_805_ShikakarihinSentaku_Dialog.detail.selectedRow)) {
        //    _805_ShikakarihinSentaku_Dialog.detail.selectedRow.element.find(".selected-row").removeClass("selected-row");
        //}
        //row.element.find("tr").addClass("selected-row");
        //_805_ShikakarihinSentaku_Dialog.detail.selectedRow = row;
    };

    /**
     * 明細の一覧の入力項目の変更イベントの処理を行います。
     */
    _805_ShikakarihinSentaku_Dialog.detail.change = function (e, row) {
        var target = $(e.target),
            id = row.element.attr("data-key"),
            property = target.attr("data-prop"),
            entity = _805_ShikakarihinSentaku_Dialog.detail.data.entry(id),
            options = {
                filter: _805_ShikakarihinSentaku_Dialog.detail.validationFilter
            };

        //_805_ShikakarihinSentaku_Dialog.values.isChangeRunning[property] = true;

        //_805_ShikakarihinSentaku_Dialog.detail.executeValidation(target, row)
        //.then(function () {
        entity[property] = row.element.form(_805_ShikakarihinSentaku_Dialog.detail.options.bindOption).data()[property];
        _805_ShikakarihinSentaku_Dialog.detail.data.update(entity);

        if (property == "sentaku") {
            // チェック行がある場合のみ活性する
            if (_805_ShikakarihinSentaku_Dialog.element.find("td [type='checkbox']:visible:checked").length) {
                _805_ShikakarihinSentaku_Dialog.element.find("[name='select']").prop("disabled", false);
            } else {
                _805_ShikakarihinSentaku_Dialog.element.find("[name='select']").prop("disabled", true);
            }
            if (_805_ShikakarihinSentaku_Dialog.element.find("td [type='checkbox']:visible:not(:checked)").length) {
                _805_ShikakarihinSentaku_Dialog.element.findP("sentaku_all").prop("checked", false);
            } else {
                _805_ShikakarihinSentaku_Dialog.element.findP("sentaku_all").prop("checked", true);
            }
        }
        //}).always(function () {
        //    delete _805_ShikakarihinSentaku_Dialog.values.isChangeRunning[property];
        //});
    };

    /**
    * 明細のバリデーションを実行します。
    */
    _805_ShikakarihinSentaku_Dialog.detail.executeValidation = function (targets, row, options) {
        var defaultOptions = {
            targets: targets,
            state: {
                tbody: row,
                isGridValidation: true
            }
        },
            execOptions = $.extend(true, {}, defaultOptions, options);

        return _805_ShikakarihinSentaku_Dialog.detail.validator.validate(execOptions);
    };

    /**
     * 明細のバリデーションフィルターを設定します。（必須チェックを行わない）
     */
    _805_ShikakarihinSentaku_Dialog.detail.validationFilter = function (item, method, state, options) {
        return method !== "required";
    };

    /**
     * 明細の一覧全体のバリデーションを実行します。
     */
    _805_ShikakarihinSentaku_Dialog.detail.validateList = function (suppressMessage) {
        var validations = [],
            options = {
                state: {
                    suppressMessage: suppressMessage,
                }
            };

        _805_ShikakarihinSentaku_Dialog.detail.dataTable.dataTable("each", function (row, index) {
            validations.push(_805_ShikakarihinSentaku_Dialog.detail.executeValidation(row.element.find(":input"), row.element, options));
        });

        return App.async.all(validations);
    };

</script>

<div class="modal fade wide" tabindex="-1" id="_805_ShikakarihinSentaku_Dialog">
    <div class="modal-dialog" style="max-height: 600px; width: 800px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">仕掛品選択</h4>
            </div>

            <div class="modal-body">
                <div class="modal-wrap">
                    <div class="detail">
                        <div class="row">
                            <label>製法工程図</label>
                        </div>
                        <table class="datatable">
                            <thead>
                                <tr>
                                    <%--<th style="width: 10px;"></th>--%>
                                    <th style="width: 50px">
                                        <label class="full-width" style="min-width: 0px;"><input type="checkbox" data-prop="sentaku_all" class="sentaku_all" disabled="disabled" /></label>
                                    </th>
                                    <th>仕掛品</th>
                                    <th>紐づける仕掛品工程</th>
                                </tr>
                            </thead>
                            <tbody class="item-tmpl" style="cursor: default; display: none;">
                                <tr>
                                    <%--<td>
                                        <span class="select-tab unselected"></span>
                                    </td>--%>
                                    <td class="center">
                                        <label class="full-width"><input type="checkbox" data-prop="sentaku" /></label>
                                    </td>
                                    <td>
                                        <label class="overflow-ellipsis" data-prop="desc_cd_haigo"></label>
                                    </td>
                                    <td>
                                        <select data-prop="src_cd_haigo"></select>
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
                <button type="button" class="btn btn-primary" name="select" disabled="disabled">コピー</button>
                <button type="button" class="cancel-button btn btn-sm btn-default" data-dismiss="modal" name="close">閉じる</button>
            </div>

        </div>
    </div>
</div>
