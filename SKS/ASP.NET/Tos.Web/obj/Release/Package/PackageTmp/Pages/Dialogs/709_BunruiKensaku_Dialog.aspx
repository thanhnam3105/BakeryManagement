<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="709_BunruiKensaku_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs._709_BunruiKensaku_Dialog" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>

<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var _709_BunruiKensaku_Dialog = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.dialogDataTakeCount   // TODO:取得するデータ数を指定します。
        },
        urls: {
            ma_bunrui: "../Services/ShisaQuickService.svc/vw_ma_bunrui",
            Get_ma_kbn_hin_lab: "../api/_709_BunruiKensaku_Dialog/Get_ma_kbn_hin_lab",
            Get_ma_kbn_hin_factory: "../api/_709_BunruiKensaku_Dialog/Get_ma_kbn_hin_factory",
            ma_group: "../Services/ShisaQuickService.svc/ma_group?$orderby=flg_hyoji",
            search_lab: "../api/_709_BunruiKensaku_Dialog/Get_Bunrui_Lab",
            search_factory: "../api/_709_BunruiKensaku_Dialog/Get_Bunrui_factory",
        },
        param: {
            kbn_bumon: null,
            cd_kaisha: null,
            cd_kojyo: null,
            kbn_hin: null
        }
    };

    /**
     * 検索ダイアログの初期化処理を行います。
     */
    _709_BunruiKensaku_Dialog.initialize = function () {
        var element = $("#_709_BunruiKensaku_Dialog");

        element.on("hidden.bs.modal", _709_BunruiKensaku_Dialog.hidden);
        element.on("shown.bs.modal", _709_BunruiKensaku_Dialog.shown);
        element.on("click", ".search", _709_BunruiKensaku_Dialog.search);
        //TODO: 単一セレクトの場合は、下の１行を使用します
        //element.on("click", ".search-list tbody ", _709_BunruiKensaku_Dialog.select);
        //TODO: 複数セレクトの場合は、上の１行を削除し、下の３行をコメント解除します
        element.on("click", ".select", _709_BunruiKensaku_Dialog.select);
        element.on("change", _709_BunruiKensaku_Dialog.change);
        //element.on("click", ".search-list tbody", _709_BunruiKensaku_Dialog.selectOne);
        //element.find("[name='select_cd_all']").on("click", _709_BunruiKensaku_Dialog.selectAll);
        _709_BunruiKensaku_Dialog.element = element;

        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        _709_BunruiKensaku_Dialog.loadMasterData();

        _709_BunruiKensaku_Dialog.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#_709_BunruiKensaku_Dialog .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        _709_BunruiKensaku_Dialog.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#_709_BunruiKensaku_Dialog .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body",
                show: function () {
                    element.find(".alert-message").show();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                }
            });

        _709_BunruiKensaku_Dialog.validator = element.validation(App.validation(_709_BunruiKensaku_Dialog.options.validations, {
            success: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _709_BunruiKensaku_Dialog.setColValidStyle(item.element);

                    _709_BunruiKensaku_Dialog.notifyAlert.remove(item.element);
                }
            },
            fail: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _709_BunruiKensaku_Dialog.setColInvalidStyle(item.element);
                    if (state && state.suppressMessage) {
                        continue;
                    }

                    _709_BunruiKensaku_Dialog.notifyAlert.message(item.message, item.element).show();
                }
            },
            always: function (results) {
                //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
            }
        }));

        element.find(".modal-dialog").draggable({
            drag: true,
        });
    };

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    _709_BunruiKensaku_Dialog.setColInvalidStyle = function (target) {
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
    _709_BunruiKensaku_Dialog.setColValidStyle = function (target) {
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
    _709_BunruiKensaku_Dialog.hidden = function (e) {
        var element = _709_BunruiKensaku_Dialog.element,
            table = element.find(".search-list");
        //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
        element.find(":input").val("");
        //TODO: 複数セレクトの場合は、下の１行をコメント解除します。
        //element.find(":checked").prop("checked", false);
        table.find("tbody").not(".item-tmpl").remove();

        element.findP("data_count").text("");
        element.findP("data_count_total").text("");

        var items = element.find(".search-criteria :input:not(button)");
        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            _709_BunruiKensaku_Dialog.setColValidStyle(item);
        }

        _709_BunruiKensaku_Dialog.notifyInfo.clear();
        _709_BunruiKensaku_Dialog.notifyAlert.clear();
        //_709_BunruiKensaku_Dialog.loadMasterData();
    };

    /**
     * 検索ダイアログ表示時処理を実行します。
     */
    _709_BunruiKensaku_Dialog.shown = function (e) {
        _709_BunruiKensaku_Dialog.element.find(":input:not(button):first").focus();
        var element = _709_BunruiKensaku_Dialog.element,
           table = element.find(".search-list");
        //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
        element.find(":input").val("");
        //TODO: 複数セレクトの場合は、下の１行をコメント解除します。
        //element.find(":checked").prop("checked", false);
        table.find("tbody").not(".item-tmpl").remove();

        element.findP("data_count").text("");
        element.findP("data_count_total").text("");

        var items = element.find(".search-criteria :input:not(button)");
        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            _709_BunruiKensaku_Dialog.setColValidStyle(item);
        }

        _709_BunruiKensaku_Dialog.notifyInfo.clear();
        _709_BunruiKensaku_Dialog.notifyAlert.clear();
        _709_BunruiKensaku_Dialog.loadMasterData();

    };

    /**
     * マスターデータのロード処理を実行します。
     */
    _709_BunruiKensaku_Dialog.loadMasterData = function () {
        var kbn_bumon = _709_BunruiKensaku_Dialog.param.kbn_bumon,
            kbn_hin = _709_BunruiKensaku_Dialog.param.kbn_hin;

        if (kbn_bumon == App.settings.app.kbn_bumon.lab) {
            return $.ajax(App.ajax.webapi.get(_709_BunruiKensaku_Dialog.urls.Get_ma_kbn_hin_lab, { kbn_hin: kbn_hin })).then(function (result) {
                var nm_kbn_hin = _709_BunruiKensaku_Dialog.element.findP("nm_kbn_hin");
                nm_kbn_hin.children().remove();
                //nm_kbn_hin.append("<option>" + result.nm_kbn_hin + "</option>");
                nm_kbn_hin.append("<option value='" + result.kbn_hin + "'>" + result.nm_kbn_hin + "</option>");

                //App.ui.appendOptions(
                //    nm_kbn_hin,
                //    "kbn_hin",
                //    "nm_kbn_hin",
                //    result,
                //    false
                //);
            });
        }

        var filter_factory = {
            kbn_hin: kbn_hin,
            cd_kaisha: _709_BunruiKensaku_Dialog.param.cd_kaisha,
            cd_kojyo: _709_BunruiKensaku_Dialog.param.cd_kojyo
        }

        if (kbn_bumon == App.settings.app.kbn_bumon.factory) {
            return $.ajax(App.ajax.webapi.get(_709_BunruiKensaku_Dialog.urls.Get_ma_kbn_hin_factory, filter_factory)).then(function (result) {
                var nm_kbn_hin = _709_BunruiKensaku_Dialog.element.findP("nm_kbn_hin");
                nm_kbn_hin.children().remove();
                //nm_kbn_hin.append("<option>" + result.nm_kbn_hin + "</option>");
                nm_kbn_hin.append("<option value='" + result.kbn_hin + "'>" + result.nm_kbn_hin + "</option>");
            });
        }

        return App.async.success();
    };

    /**
     * 検索ダイアログ　バリデーションルールを定義します。
     */
    _709_BunruiKensaku_Dialog.options.validations = {
        bunrui: {
            rules: {
                maxbytelength: 20
            },
            options: {
                name: "分類名／コード",
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
    _709_BunruiKensaku_Dialog.search = function () {
        var element = _709_BunruiKensaku_Dialog.element,
            loadingTaget = element.find(".modal-content"),
            table = element.find(".search-list"),
            param = _709_BunruiKensaku_Dialog.param,
            query;

        _709_BunruiKensaku_Dialog.notifyInfo.clear();
        _709_BunruiKensaku_Dialog.notifyAlert.clear();

        _709_BunruiKensaku_Dialog.options.filter = _709_BunruiKensaku_Dialog.createFilter();
        query = {
            url: _709_BunruiKensaku_Dialog.urls.ma_bunrui,
            filter: _709_BunruiKensaku_Dialog.options.filter,
            orderby: "cd_bunrui",
            //top: _709_BunruiKensaku_Dialog.options.top,
            inlinecount: "allpages"
        };

        if (param.kbn_bumon == App.settings.app.kbn_bumon.lab) {
            urls_search = _709_BunruiKensaku_Dialog.urls.search_lab;
        }
        if (param.kbn_bumon == App.settings.app.kbn_bumon.factory) {
            urls_search = _709_BunruiKensaku_Dialog.urls.search_factory;
        }

        _709_BunruiKensaku_Dialog.validator.validate()
        .then(function () {
            table.find("tbody:visible").remove();
            _709_BunruiKensaku_Dialog.notifyAlert.clear();
            App.ui.loading.show("", loadingTaget);
            //$.ajax(App.ajax.odata.get(App.data.toODataFormat(query)))
            $.ajax(App.ajax.webapi.get(urls_search, _709_BunruiKensaku_Dialog.options.filter))
            .done(function (result) {
                _709_BunruiKensaku_Dialog.bind(result);
            }).fail(function (error) {
                _709_BunruiKensaku_Dialog.notifyAlert.message(App.ajax.handleError(error).message).show();
            }).always(function () {

                App.ui.loading.close(loadingTaget);
            });
        });
    };

    /**
     * 検索ダイアログの検索条件を組み立てます
     */
    _709_BunruiKensaku_Dialog.createFilter = function () {
        var criteria = _709_BunruiKensaku_Dialog.element.find(".search-criteria").form().data(),
            param = _709_BunruiKensaku_Dialog.param,
            filters = [];

        //TODO: 画面で設定された検索条件を取得し、データ取得サービスのフィルターオプションを組み立てます。

        var filter = {
            bunrui: criteria.bunrui,
            kbn_hin: criteria.nm_kbn_hin,
            cd_kaisha: param.cd_kaisha,
            cd_kojyo: param.cd_kojyo
        };
        return filter;
    };

    /**
     * 検索ダイアログの一覧にデータをバインドします。
     */
    _709_BunruiKensaku_Dialog.bind = function (data) {
        var element = _709_BunruiKensaku_Dialog.element,
            table = element.find(".search-list"),
            //count = data["odata.count"],
            count = data.length,
            items = data.value ? data.value : data,
            i, l, item, clone;

        element.findP("data_count").text(data.length);
        element.findP("data_count_total").text(count);

        _709_BunruiKensaku_Dialog.data = App.ui.page.dataSet();
        _709_BunruiKensaku_Dialog.data.attach(items);

        table.find("tbody:visible").remove();

        for (i = 0, l = items.length; i < l; i++) {
            item = items[i];

            if (item.cd_bunrui < 10 && _709_BunruiKensaku_Dialog.param.kbn_bumon == App.settings.app.kbn_bumon.lab) {
                item.cd_bunrui = "0" + item.cd_bunrui;
            }

            clone = table.find(".item-tmpl").clone();
            clone.form().bind(item);
            clone.appendTo(table).removeClass("item-tmpl").show();
        }

        //if (count && count > App.settings.base.dialogDataTakeCount) {
        //    _709_BunruiKensaku_Dialog.notifyInfo.message(App.messages.base.MS0011).show();
        //}
    };

    /**
     * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
     */
    _709_BunruiKensaku_Dialog.select = function (e) {
        var element = _709_BunruiKensaku_Dialog.element,
            target = $(e.target),
            id = target.closest("tbody").attr("data-key"),
            data;

        if (App.isUndef(id)) {
            return;
        }

        data = _709_BunruiKensaku_Dialog.data.entry(id);

        if (App.isFunc(_709_BunruiKensaku_Dialog.dataSelected)) {
            if (!_709_BunruiKensaku_Dialog.dataSelected(data)) {
                element.modal("hide");
            }
        } else {
            element.modal("hide");
        }
    };

    /**
        * Element on change
        */
    _709_BunruiKensaku_Dialog.change = function (e) {
        var target = $(e.target);
        _709_BunruiKensaku_Dialog.validator.validate({
            targets: target
        })
    }

    //TODO: 複数セレクトを使用する場合は、上の単一セレクト用select関数を削除し、下の複数セレクト用の関数をコメント解除します
    //TODO: 単一セレクトの場合は、不要なコメント部分は削除してください
    <%--
        /**
         * 一覧から行を選択された際の処理を実行します。（複数セレクト用）
         */
        _709_BunruiKensaku_Dialog.select = function (e) {
            var element = _709_BunruiKensaku_Dialog.element,
                items;

            //TODO: 選択された行から起動元画面に返却したい値を抽出します
            items = element.find(".search-list").find("input:checked[name='select_cd']").map(function (index, item) {
                var tbody = $(item).closest("tbody"),
                    id = tbody.attr("data-key"),
                    data = _709_BunruiKensaku_Dialog.data.entry(id);
                return data.cd_torihiki;
            }).toArray();

            if (items.length == 0) {
                _709_BunruiKensaku_Dialog.notifyAlert.message(App.messages.base.MS0020).show();
                return;
            }

            if (App.isFunc(_709_BunruiKensaku_Dialog.dataSelected)) {
                if (!_709_BunruiKensaku_Dialog.dataSelected(items)) {
                    element.modal("hide");
                }
            } else {
                element.modal("hide");
            }
        };

        /**
         * 一覧の行をクリックした際の処理を実行します。（複数セレクト用）
         */
        _709_BunruiKensaku_Dialog.selectOne = function (e) {
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
        _709_BunruiKensaku_Dialog.selectAll = function (e) {
            var target = $(e.target);
            _709_BunruiKensaku_Dialog.element.find("[name='select_cd']:visible").prop("checked", target.is(":checked"));
        };
--%>
</script>

<div class="modal fade wide" tabindex="-1" id="_709_BunruiKensaku_Dialog">
    <div class="modal-dialog" style="height: 350px; width: 45%">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">分類検索</h4>
            </div>

            <div class="modal-body">
                <div class="search-criteria">
                    <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
                    <div class="row">
                        <div class="control-label col-xs-3">
                            <label>品区分</label>
                        </div>
                        <div class="control col-xs-3">
                            <select class="" data-prop="nm_kbn_hin" disabled></select>
                        </div>
                        <div class="control col-xs-6"></div>
                    </div>

                    <div class="row">
                        <div class="control-label col-xs-3">
                            <label>分類名／コード</label>
                        </div>
                        <div class="control col-xs-5">
                            <input type="text" class="ime-active" data-prop="bunrui"/>
                        </div>
                        <div class="control col-xs-4"></div>
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
                                <%--単一セレクトの場合は、以下の１行を使用する--%>
                                <th style="width: 15%;"></th>
                                <th style="width: 25%;">分類コード</th>
                                <th style="width: 60%;">分類名</th>
                            </tr>
                        </thead>
                    </table>
                </div>
                <div style="height: 252px; overflow: scroll; overflow-x: hidden;">
                    <table class="table table-striped table-condensed search-list">
                        <!--TODO: ダイアログの明細行を定義するHTMLをここに記述します。列幅の指定はヘッダーに合わせて定義します。-->
                        <tbody class="item-tmpl" style="display: none;">
                            <tr>
                                <%--単一セレクトの場合は、以下の３行を使用する--%>
                                <td style="width: 15%;">
                                    <button type="button" style="margin: 1px; padding: 1px;" class="btn btn-success btn-xs select">選択</button>
                                </td>
                                <td style="width: 25%;">
                                    <label data-prop="cd_bunrui"></label>
                                </td>
                                <td style="width: 60%;">
                                    <label data-prop="nm_bunrui"></label>
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
                <button type="button" class="cancel-button btn btn-sm btn-default" data-dismiss="modal" name="close">閉じる</button>
            </div>

        </div>
    </div>
</div>
