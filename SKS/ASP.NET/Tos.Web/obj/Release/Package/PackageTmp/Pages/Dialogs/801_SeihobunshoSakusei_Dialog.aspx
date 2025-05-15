<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="801_SeihobunshoSakusei_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs._801_SeihobunshoSakusei_Dialog" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>

<style>
    #_801_SeihobunshoSakusei_Dialog .row {
        margin: 5px;
    }
    #_801_SeihobunshoSakusei_Dialog .modal-body button {
        height: 35px;
    }
</style>

<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var _801_SeihobunshoSakusei_Dialog = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.dialogDataTakeCount   // TODO:取得するデータ数を指定します。
        },
        param: {
            no_seiho: ""
        },
        urls: {
            search: "../api/_801_SeihobunshoSakusei_Dialog",
            tab1Data: "../Services/ShisaQuickService.svc/ma_seiho_bunsho_hyoshi?$filter=no_seiho eq '{0}'",
            transfer200: "200_SeihoIchiran.aspx?M_SeihoIchiran={0}&no_seiho={1}&no_seiho_sakusei={2}"
        }
    };

    /**
     * 検索ダイアログの初期化処理を行います。
     */
    _801_SeihobunshoSakusei_Dialog.initialize = function () {
        var element = $("#_801_SeihobunshoSakusei_Dialog");

        element.on("show.bs.modal", _801_SeihobunshoSakusei_Dialog.show);
        element.on("shown.bs.modal", _801_SeihobunshoSakusei_Dialog.shown);
        element.on("click", "#recipe-copy", _801_SeihobunshoSakusei_Dialog.recipeCopy);
        element.on("click", "#method-copy", _801_SeihobunshoSakusei_Dialog.methodCopy);
        element.on("click", "#new-formula", _801_SeihobunshoSakusei_Dialog.newFormula);
        element.on("click", "#recipe-view", _801_SeihobunshoSakusei_Dialog.recipeEdit);

        _801_SeihobunshoSakusei_Dialog.element = element;

        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        _801_SeihobunshoSakusei_Dialog.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#_801_SeihobunshoSakusei_Dialog .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        _801_SeihobunshoSakusei_Dialog.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#_801_SeihobunshoSakusei_Dialog .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body",
                show: function () {
                    element.find(".alert-message").show();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                }
            });

        _801_SeihobunshoSakusei_Dialog.validator = element.validation(App.validation(_801_SeihobunshoSakusei_Dialog.options.validations, {
            success: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _801_SeihobunshoSakusei_Dialog.setColValidStyle(item.element);

                    _801_SeihobunshoSakusei_Dialog.notifyAlert.remove(item.element);
                }
            },
            fail: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _801_SeihobunshoSakusei_Dialog.setColInvalidStyle(item.element);
                    if (state && state.suppressMessage) {
                        continue;
                    }

                    _801_SeihobunshoSakusei_Dialog.notifyAlert.message(item.message, item.element).show();
                }
            },
            always: function (results) {
                //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
            }
        }));

        element.find(".modal-dialog").draggable({
            drag: true,
        });
        // Get and throw the shortcut param
        if (window.opener && window.opener.page) {
            page.shortcutParam = $.extend(true, {}, window.opener.page.shortcutParam);
        }
    };

    /**
     * transfer to page 200_SeihoIchiran
     */
    _801_SeihobunshoSakusei_Dialog.recipeCopy = function () {
        var url = App.str.format(_801_SeihobunshoSakusei_Dialog.urls.transfer200, App.settings.app.m_seiho_ichiran.copy, _801_SeihobunshoSakusei_Dialog.param.no_seiho, _801_SeihobunshoSakusei_Dialog.param.no_seiho);
        window.open(url);
        _801_SeihobunshoSakusei_Dialog.element.modal("hide");
    }

    /**
     * transfer to page 300_SeihoBunsho in new mode
     */
    _801_SeihobunshoSakusei_Dialog.newFormula = function () {
        var param = _801_SeihobunshoSakusei_Dialog.param;
        $.ajax(App.ajax.webapi.get(App.str.format(_801_SeihobunshoSakusei_Dialog.urls.tab1Data, param.no_seiho))).then(function (result) {
            if (result && result.value.length) {
                return ConfirmDialog.confirm({
                    text: App.messages.app.AP0129,
                    hideCancel: true
                }).always(function () {
                    _801_SeihobunshoSakusei_Dialog.element.modal("hide");
                })
            }
            App.common.openSeihoBunsho({
                no_seiho: param.no_seiho,
                no_seiho_sakusei: param.no_seiho,
                mode: App.settings.app.m_seiho_bunsho.shinki
            });
            _801_SeihobunshoSakusei_Dialog.element.modal("hide");
        });
    }

    /**
     * transfer to page 300_SeihoBunsho in edit mode
     */
    _801_SeihobunshoSakusei_Dialog.recipeEdit = function () {
        var param = _801_SeihobunshoSakusei_Dialog.param;
        App.common.openSeihoBunsho({
            no_seiho: param.no_seiho,
            no_seiho_sakusei: param.no_seiho,
            mode: App.settings.app.m_seiho_bunsho.henshu
        });
        _801_SeihobunshoSakusei_Dialog.element.modal("hide");
    }

    /**
     * Copy method active dialog 802_CopyHaniSelection_Dialog
     */
    _801_SeihobunshoSakusei_Dialog.methodCopy = function () {
        var param = _801_SeihobunshoSakusei_Dialog.param;
        if (_801_SeihobunshoSakusei_Dialog.isLoad802) {
            App.ui.loading.show();
            return;
        }
        _802_CopyHaniSelection_Dialog.param.no_seiho = param.no_seiho;
        _802_CopyHaniSelection_Dialog.param.no_seiho_sakusei = param.no_seiho_sakusei;
        _802_CopyHaniSelection_Dialog.param.no_seiho_copy = param.no_seiho_copy;
        _802_CopyHaniSelection_Dialog.element.modal("show");
        _801_SeihobunshoSakusei_Dialog.element.modal("hide");
    }

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    _801_SeihobunshoSakusei_Dialog.setColInvalidStyle = function (target) {
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
    _801_SeihobunshoSakusei_Dialog.setColValidStyle = function (target) {
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
     * 検索ダイアログ表示時処理を実行します。
     */
    _801_SeihobunshoSakusei_Dialog.shown = function (e) {

        _801_SeihobunshoSakusei_Dialog.element.find(":input:not(button):first").focus();
    };

    _801_SeihobunshoSakusei_Dialog.show = function () {
        _801_SeihobunshoSakusei_Dialog.search();
        if (typeof _802_CopyHaniSelection_Dialog === "undefined") {
            _801_SeihobunshoSakusei_Dialog.isLoad802 = true;
            $.get("Dialogs/802_CopyHaniSelection_Dialog.aspx").then(function (result) {
                $("#dialog-container").append(result);
                _802_CopyHaniSelection_Dialog.initialize();
                if (_801_SeihobunshoSakusei_Dialog.isLoad802) {
                    _801_SeihobunshoSakusei_Dialog.isLoad802 = false;
                    App.ui.loading.close();
                }
            });
        }
    }

    /**
     * 検索ダイアログ　バリデーションルールを定義します。
     */
    _801_SeihobunshoSakusei_Dialog.options.validations = {
    };

    /**
     * 検索ダイアログの検索処理を実行します。
     */
    _801_SeihobunshoSakusei_Dialog.search = function () {
        var element = _801_SeihobunshoSakusei_Dialog.element,
            loadingTaget = element.find(".modal-content"),
            no_seiho = _801_SeihobunshoSakusei_Dialog.param.no_seiho;

        _801_SeihobunshoSakusei_Dialog.validator.validate()
        .then(function () {
            App.ui.loading.show("", loadingTaget);
            $.ajax(App.ajax.webapi.get(_801_SeihobunshoSakusei_Dialog.urls.search, { no_seiho: no_seiho }))
            .done(function (result) {
                _801_SeihobunshoSakusei_Dialog.param.no_seiho_sanko = result.no_seiho_sanko;

                if (result.methodStatus) {
                    $("#method-copy").closest(".row").show();
                } else {
                    $("#method-copy").closest(".row").hide();
                }
                if (result.editStatus) {
                    $("#recipe-view").closest(".row").show();
                    $("#new-formula").closest(".row").hide();
                } else {
                    $("#new-formula").closest(".row").show();
                    $("#recipe-view").closest(".row").hide();
                }

            }).always(function () {
                //$("#method-copy, #new-formula, #recipe-view").closest(".row").show();
                App.ui.loading.close(loadingTaget);
            });
        });
    };

    /**
     * 検索ダイアログの検索条件を組み立てます
     */
    _801_SeihobunshoSakusei_Dialog.createFilter = function () {
        var criteria = _801_SeihobunshoSakusei_Dialog.element.find(".search-criteria").form().data(),
            filters = [];

        //TODO: 画面で設定された検索条件を取得し、データ取得サービスのフィルターオプションを組み立てます。
        //if (!App.isUndefOrNullOrStrEmpty(criteria.nm_torihiki)) {
        //    filters.push("substringof('" + encodeURIComponent(criteria.nm_torihiki) + "', nm_torihiki) eq true");
        //}

        return filters.join(" and ");
    };

    /**
     * 検索ダイアログの一覧にデータをバインドします。
     */
    _801_SeihobunshoSakusei_Dialog.bind = function (data) {
        var element = _801_SeihobunshoSakusei_Dialog.element,
            table = element.find(".search-list"),
            count = data["odata.count"],
            items = data.value ? data.value : data,
            i, l, item, clone;

        element.findP("data_count").text(data.value.length);
        element.findP("data_count_total").text(count);

        _801_SeihobunshoSakusei_Dialog.data = App.ui.page.dataSet();
        _801_SeihobunshoSakusei_Dialog.data.attach(items);

        table.find("tbody:visible").remove();

        for (i = 0, l = items.length; i < l; i++) {
            item = items[i];
            clone = table.find(".item-tmpl").clone();
            clone.form().bind(item);
            clone.appendTo(table).removeClass("item-tmpl").show();
        }

        if (count && count > App.settings.base.dialogDataTakeCount) {
            _801_SeihobunshoSakusei_Dialog.notifyInfo.message(App.messages.base.MS0011).show();
        }
    };

    /**
     * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
     */
    _801_SeihobunshoSakusei_Dialog.select = function (e) {
        var element = _801_SeihobunshoSakusei_Dialog.element,
            target = $(e.target),
            id = target.closest("tbody").attr("data-key"),
            data;

        if (App.isUndef(id)) {
            return;
        }

        data = _801_SeihobunshoSakusei_Dialog.data.entry(id);

        if (App.isFunc(_801_SeihobunshoSakusei_Dialog.dataSelected)) {
            if (!_801_SeihobunshoSakusei_Dialog.dataSelected(data)) {
                element.modal("hide");
            }
        } else {
            element.modal("hide");
        }
    };

</script>

<div class="modal fade wide" tabindex="-1" id="_801_SeihobunshoSakusei_Dialog">
    <div class="modal-dialog" style="height: 350px; width: 400px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">製法文書作成方法の選択</h4>
            </div>

            <div class="modal-body">
                <div class="search-criteria">
                    <div class="row">
                        <div class="col-xs-10 col-xs-offset-1">
                            <button class="btn btn-xs btn-primary full-width" id="recipe-copy">他製法の製法文書をコピー</button>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10 col-xs-offset-1">
                            <button class="btn btn-xs btn-primary full-width" id="method-copy">コピー元製法の製法文書をコピー</button>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10 col-xs-offset-1">
                            <button class="btn btn-xs btn-primary full-width" id="new-formula">製法文書を新規に作成</button>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10 col-xs-offset-1">
                            <button class="btn btn-xs btn-primary full-width" id="recipe-view">製法文書を表示</button>
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

            <div class="modal-footer">
                <%--複数セレクトの場合は、下の１行をコメント解除してください--%>
                <%--<button type="button" class="btn btn-success select" name="select" >選択</button>--%>
                <button type="button" class="cancel-button btn btn-sm btn-default" data-dismiss="modal" name="close">閉じる</button>
            </div>

        </div>
    </div>
</div>
