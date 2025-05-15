<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="_500_SeizoTantoKaishaKensaku_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs._500_SeizoTantoKaishaKensaku_Dialog" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>

<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var _500_SeizoTantoKaishaKensaku_Dialog = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.dialogDataTakeCount   // TODO:取得するデータ数を指定します。
        },
        urls: {
            kaisha: "../Services/ShisaQuickService.svc/ma_kaisha"
        },
        values: {
            selected: []
        }
    };



    /**
     * 検索ダイアログの初期化処理を行います。
     */
    _500_SeizoTantoKaishaKensaku_Dialog.initialize = function () {
        var element = $("#_500_SeizoTantoKaishaKensaku_Dialog");

        element.on("hidden.bs.modal", _500_SeizoTantoKaishaKensaku_Dialog.hidden);
        element.on("shown.bs.modal", _500_SeizoTantoKaishaKensaku_Dialog.shown);
        element.on("click", ".search", _500_SeizoTantoKaishaKensaku_Dialog.search);
        //TODO: 単一セレクトの場合は、下の１行を使用します
        //element.on("click", ".search-list tbody ", _500_SeizoTantoKaishaKensaku_Dialog.select);
        //TODO: 複数セレクトの場合は、上の１行を削除し、下の３行をコメント解除します
        element.on("click", ".select", _500_SeizoTantoKaishaKensaku_Dialog.select);
        element.on("click", ".search-list tbody", _500_SeizoTantoKaishaKensaku_Dialog.selectOne);
        //element.find("[name='select_cd_all']").on("click", _500_SeizoTantoKaishaKensaku_Dialog.selectAll);
        element.on("change", ".ime-active", _500_SeizoTantoKaishaKensaku_Dialog.lostFocus);
        _500_SeizoTantoKaishaKensaku_Dialog.element = element;

        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        _500_SeizoTantoKaishaKensaku_Dialog.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#_500_SeizoTantoKaishaKensaku_Dialog .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        _500_SeizoTantoKaishaKensaku_Dialog.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#_500_SeizoTantoKaishaKensaku_Dialog .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body",
                show: function () {
                    element.find(".alert-message").show();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                }
            });

        _500_SeizoTantoKaishaKensaku_Dialog.validator = element.validation(App.validation(_500_SeizoTantoKaishaKensaku_Dialog.options.validations, {
            success: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _500_SeizoTantoKaishaKensaku_Dialog.setColValidStyle(item.element);

                    _500_SeizoTantoKaishaKensaku_Dialog.notifyAlert.remove(item.element);
                }
            },
            fail: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _500_SeizoTantoKaishaKensaku_Dialog.setColInvalidStyle(item.element);
                    if (state && state.suppressMessage) {
                        continue;
                    }

                    _500_SeizoTantoKaishaKensaku_Dialog.notifyAlert.message(item.message, item.element).show();
                }
            },
            always: function (results) {
                //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
            }
        }));

        element.find(".modal-dialog").draggable({
            drag: false,
        });
    };

    _500_SeizoTantoKaishaKensaku_Dialog.lostFocus = function () {
        _500_SeizoTantoKaishaKensaku_Dialog.validateAll();
    };
    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    _500_SeizoTantoKaishaKensaku_Dialog.setColInvalidStyle = function (target) {
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
    _500_SeizoTantoKaishaKensaku_Dialog.setColValidStyle = function (target) {
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
     * 検索ダイアログ非表示時処理を実行します。
     */
    _500_SeizoTantoKaishaKensaku_Dialog.hidden = function (e) {
        var element = _500_SeizoTantoKaishaKensaku_Dialog.element,
            table = element.find(".search-list");

        element.find(":input").val("");
        //element.find(":checked").prop("checked", false);
        table.find("tbody").not(".item-tmpl").remove();

        element.findP("data_count").text("");
        element.findP("data_count_total").text("");
        element.find(":checked").prop("checked", false);
        element.find(".select").prop("disabled", true);

        _500_SeizoTantoKaishaKensaku_Dialog.values.selected = undefined;
        var items = element.find(".search-criteria :input:not(button)");
        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            _500_SeizoTantoKaishaKensaku_Dialog.setColValidStyle(item);
        }

        _500_SeizoTantoKaishaKensaku_Dialog.notifyInfo.clear();
        _500_SeizoTantoKaishaKensaku_Dialog.notifyAlert.clear();
    };

    /**
     * 検索ダイアログ表示時処理を実行します。
     */
    _500_SeizoTantoKaishaKensaku_Dialog.shown = function (e) {

        _500_SeizoTantoKaishaKensaku_Dialog.element.find(":input:not(button):first").focus();
    };

    /**
     * 検索ダイアログ　バリデーションルールを定義します。
     */
    _500_SeizoTantoKaishaKensaku_Dialog.options.validations = {
        nm_kaisha: {
            rules: {
                maxbytelength: 20
            },
            options: {
                name: "会社名",
                byte: 10
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
    };

    /**
     * 検索ダイアログの検索処理を実行します。
     */
    _500_SeizoTantoKaishaKensaku_Dialog.validateAll = function () {
        var validations = [];
        validations.push(_500_SeizoTantoKaishaKensaku_Dialog.validator.validate());
        return App.async.all(validations);
    };

    _500_SeizoTantoKaishaKensaku_Dialog.search = function () {
        //var element = _500_SeizoTantoKaishaKensaku_Dialog.element,
        //    loadingTaget = element.find(".modal-content"),
        //    table = element.find(".search-list"),
        //    criteria = [],
        //    nameCriteria = element.findP("nm_kaisha").val(),
        //    query;
        //_500_SeizoTantoKaishaKensaku_Dialog.notifyInfo.clear();
        //_500_SeizoTantoKaishaKensaku_Dialog.validateAll().then(function () {
        //    if (nameCriteria) {
        //        criteria.push("substringof('" + encodeURIComponent(nameCriteria) + "', nm_kaisha) eq true");
        //    }
        //    table.find("tbody:visible").remove();
        //    App.ui.loading.show("", loadingTaget);

        //    $.ajax(App.ajax.odata.get(_500_SeizoTantoKaishaKensaku_Dialog.urls.kaisha +
        //        "?$top=" + App.settings.base.dialogDataTakeCount + "&$inlinecount=allpages" + "&$orderby=cd_kaisha" +
        //    (criteria.length ? "?$filter=" + criteria.join(" and ") : "")
        //        )).done(function (result) {
        //            _500_SeizoTantoKaishaKensaku_Dialog.bind(result);
        //        }).always(function () {
        //            var items = element.find(".search-list").find("input:checked[name='select_cd']"),
        //                checkboxesCount = element.find(".search-list").find("[name='select_cd']");
        //            if (items.length != 0) {
        //                element.find(".select").prop("disabled", false);
        //            }

        //            App.ui.loading.close(loadingTaget);
        //        });
        //})


        var element = _500_SeizoTantoKaishaKensaku_Dialog.element,
            loadingTaget = element.find(".modal-content"),
            table = element.find(".search-list"),
            query;

        _500_SeizoTantoKaishaKensaku_Dialog.options.filter = _500_SeizoTantoKaishaKensaku_Dialog.createFilter();
        query = {
            url: _500_SeizoTantoKaishaKensaku_Dialog.urls.kaisha,
            filter: _500_SeizoTantoKaishaKensaku_Dialog.options.filter,
            orderby: "cd_kaisha",
            top: _500_SeizoTantoKaishaKensaku_Dialog.options.top,
            inlinecount: "allpages"
        };

        //_500_SeizoTantoKaishaKensaku_Dialog.validator.validate()
        _500_SeizoTantoKaishaKensaku_Dialog.validateAll()
        .then(function () {
            table.find("tbody:visible").remove();
            _500_SeizoTantoKaishaKensaku_Dialog.notifyAlert.clear();
            App.ui.loading.show("", loadingTaget);

            $.ajax(App.ajax.odata.get(App.data.toODataFormat(query)))
            .done(function (result) {

                _500_SeizoTantoKaishaKensaku_Dialog.bind(result);
            }).always(function () {
                var items = element.find(".search-list").find("input:checked[name='select_cd']"),
                        checkboxesCount = element.find(".search-list").find("[name='select_cd']");
                if (items.length != 0) {
                    element.find(".select").prop("disabled", false);
                }
                App.ui.loading.close(loadingTaget);
                var nm_kaisha = element.findP("nm_kaisha").focus();
            });
        });
    };

    /**
     * 検索ダイアログの検索条件を組み立てます
     */
    _500_SeizoTantoKaishaKensaku_Dialog.createFilter = function () {
        var criteria = _500_SeizoTantoKaishaKensaku_Dialog.element.find(".search-criteria").form().data(),
            filters = [];

        if (!App.isUndefOrNullOrStrEmpty(criteria.nm_kaisha)) {
            filters.push("substringof('" + encodeURIComponent(criteria.nm_kaisha) + "', nm_kaisha) eq true");
        }

        return filters.join(" and ");
    };

    /**
     * 検索ダイアログの一覧にデータをバインドします。
     */
    _500_SeizoTantoKaishaKensaku_Dialog.bind = function (data) {
        var element = _500_SeizoTantoKaishaKensaku_Dialog.element,
            table = element.find(".search-list"),
            count = data["odata.count"],
            items = data.value ? data.value : data,
            i, l, item, clone;

        element.findP("data_count").text(data.value.length);
        element.findP("data_count_total").text(count);

        _500_SeizoTantoKaishaKensaku_Dialog.data = App.ui.page.dataSet();
        _500_SeizoTantoKaishaKensaku_Dialog.data.attach(items);
        table.find("tbody:visible").remove();

        for (i = 0, l = items.length; i < l; i++) {
            item = items[i];

            clone = table.find(".item-tmpl").clone();
            clone.form(_500_SeizoTantoKaishaKensaku_Dialog.options.bindOption).bind(item);
            clone.appendTo(table).removeClass("item-tmpl").show();

            if (!App.isUndefOrNull(_500_SeizoTantoKaishaKensaku_Dialog.values.selected) && $.inArray(item.cd_kaisha, _500_SeizoTantoKaishaKensaku_Dialog.values.selected) >= 0) {
                clone.find("[name='select_cd']").prop("checked", true);
            }
        }

        if (count && count > App.settings.base.dialogDataTakeCount) {
            _500_SeizoTantoKaishaKensaku_Dialog.notifyInfo.message(App.messages.base.MS0011).show();
        }
    };

    _500_SeizoTantoKaishaKensaku_Dialog.options.bindOption = {
        appliers: {
            cd_kaisha: function (value, element) {
                value = "00000" + value;
                var l = value.length;

                element.text(value.substr(l - 5, l));
                return true;
            }
        }
    };
    /**
     * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
     */
    _500_SeizoTantoKaishaKensaku_Dialog.select = function (e) {
        var element = _500_SeizoTantoKaishaKensaku_Dialog.element,
            data;
        _500_SeizoTantoKaishaKensaku_Dialog.notifyAlert.clear();

        var items = element.find(".search-list").find("input:checked[name='select_cd']").map(function (index, item) {
            var tbody = $(item).closest("tbody"),
                id = tbody.attr("data-key"),
                data = _500_SeizoTantoKaishaKensaku_Dialog.data.entry(id);
            return data;
        }).toArray();

        if (items.length == 0 && App.isUndefOrNull(_500_SeizoTantoKaishaKensaku_Dialog.values.selected)) {
            _500_SeizoTantoKaishaKensaku_Dialog.notifyAlert.message(App.messages.base.MS0020).show();
            return;
        }

        if (App.isFunc(_500_SeizoTantoKaishaKensaku_Dialog.dataSelected)) {
            if (!_500_SeizoTantoKaishaKensaku_Dialog.dataSelected(items, true)) {
                element.modal("hide");
            }
        }
        else {
            element.modal("hide");
        }
    };

    //TODO: 複数セレクトを使用する場合は、上の単一セレクト用select関数を削除し、下の複数セレクト用の関数をコメント解除します
    //TODO: 単一セレクトの場合は、不要なコメント部分は削除してください
        <%--
        /**
         * 一覧から行を選択された際の処理を実行します。（複数セレクト用）
         */
        _500_SeizoTantoKaishaKensaku_Dialog.select = function (e) {
            var element = _500_SeizoTantoKaishaKensaku_Dialog.element,
                items;

            //TODO: 選択された行から起動元画面に返却したい値を抽出します
            items = element.find(".search-list").find("input:checked[name='select_cd']").map(function (index, item) {
                var tbody = $(item).closest("tbody"),
                    id = tbody.attr("data-key"),
                    data = _500_SeizoTantoKaishaKensaku_Dialog.data.entry(id);
                return data.cd_torihiki;
            }).toArray();

            if (items.length == 0) {
                _500_SeizoTantoKaishaKensaku_Dialog.notifyAlert.message(App.messages.base.MS0020).show();
                return;
            }

            if (App.isFunc(_500_SeizoTantoKaishaKensaku_Dialog.dataSelected)) {
                if (!_500_SeizoTantoKaishaKensaku_Dialog.dataSelected(items)) {
                    element.modal("hide");
                }
            } else {
                element.modal("hide");
            }
        };

        /**
         * 一覧の行をクリックした際の処理を実行します。（複数セレクト用）
         */
        _500_SeizoTantoKaishaKensaku_Dialog.selectOne = function (e) {
            var target = $(e.target),
                tr = target.closest("tr");

            if (target.is("[name='select_cd']")) {
                return;
            }

            var check = tr.find("[name='select_cd']");
            check.prop("checked", !check.is(":checked"));
        };

        /**
         * 一覧のヘッダ部のckeckbox(ALLチェック用）をクリックした際の処理を実行します。（複数セレクト用）
         */
        _500_SeizoTantoKaishaKensaku_Dialog.selectAll = function (e) {
            var target = $(e.target);
            _500_SeizoTantoKaishaKensaku_Dialog.element.find("[name='select_cd']:visible").prop("checked", target.is(":checked"));
        };
--%>
    _500_SeizoTantoKaishaKensaku_Dialog.selectOne = function (e) {
        var element = _500_SeizoTantoKaishaKensaku_Dialog.element,
            target = $(e.target),
            tbody = target.closest("tbody"),
            checkboxes = element.find(".search-list").find("input:checked[name='select_cd']"),
            checkboxesCount = element.find(".search-list").find("[name='select_cd']");
        var check = tbody.find("[name='select_cd']");
        if (target.is("[name='select_cd']")) {
            var items = element.find(".search-list").find("input:checked[name='select_cd']").change();
            if (items.length == 0)
                _500_SeizoTantoKaishaKensaku_Dialog.element.find(".select").prop("disabled", true);
            else
                _500_SeizoTantoKaishaKensaku_Dialog.element.find(".select").prop("disabled", false);
        }
        if (check.is(":checked")) {
            check.prop("checked", false);
        }
        else {
            check.prop("checked", true);
        }
        check.prop("checked", !check.is(":checked"));
    };

    /**
     * 一覧のヘッダ部のckeckbox(ALLチェック用）をクリックした際の処理を実行します。（複数セレクト用）
     */
    _500_SeizoTantoKaishaKensaku_Dialog.selectAll = function (e) {
        var target = $(e.target);
        _500_SeizoTantoKaishaKensaku_Dialog.element.find("[name='select_cd']:visible").prop("checked", target.is(":checked"));
    };
</script>

<div class="modal fade wide" tabindex="-1" id="_500_SeizoTantoKaishaKensaku_Dialog">
    <div class="modal-dialog" style="height: 350px; width: 33%">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">製造担当会社検索</h4>
            </div>

            <div class="modal-body">
                <div class="search-criteria">
                    <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
                    <div class="row">
                        <div class="control-label col-xs-3">
                            <label>会社名</label>
                        </div>
                        <div class="control col-xs-9">
                            <input type="text" class="ime-active" data-prop="nm_kaisha" />
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

                <div style="padding-right: 16px;">
                    <table class="table table-striped table-condensed " style="margin-bottom: 0px;">
                        <!--TODO: ダイアログのヘッダーを定義するHTMLをここに記述します。列幅の合計が100％になるように定義します。-->
                        <thead>
                            <tr>
                                <th style="width: 50px;"></th>
                                <th style="width: 80px" class="left">会社CD</th>
                                <th class="left">会社名</th>
                            </tr>
                        </thead>
                    </table>
                </div>
                <div style="height: 252px; overflow: scroll; overflow-x: hidden;">

                    <table class="table table-striped table-condensed search-list">
                        <!--TODO: ダイアログの明細行を定義するHTMLをここに記述します。列幅の指定はヘッダーに合わせて定義します。-->
                        <tbody class="item-tmpl" style="display: none;">
                            <tr>
                                <%--複数セレクトの場合は、上の３行をカットし、下の３行をコメント解除してください--%>
                                <%--                                <td style="width: 5%;">
                                    <input type="checkbox" name="select_cd" />
                                </td>--%>
                                <td style="width: 50px;" class="text-center">
                                    <input type="checkbox" name="select_cd" />
                                </td>
                                <td style="width: 80px" class="left">
                                    <span data-prop="cd_kaisha"></span>
                                </td>
                                <td>
                                    <span data-prop="nm_kaisha" class="overflow-ellipsis"></span>
                                </td>
                            </tr>
                        </tbody>
                    </table>
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
                <%--複数セレクトの場合は、下の１行をコメント解除してください--%>
                <%--<button type="button" class="btn btn-success select" name="select" >選択</button>--%>
                <button type="button" class="btn btn-success select" name="select" disabled="disabled">選択</button>
                <button type="button" class="cancel-button btn btn-sm btn-default" data-dismiss="modal" name="close">閉じる</button>
            </div>

        </div>
    </div>
</div>
