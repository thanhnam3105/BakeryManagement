<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="_707_LineKenSaKu_DiaLog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs._707_LineKenSaKu_DiaLog" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>

<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var _707_LineKenSaKu_DiaLog = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.dialogDataTakeCount   // TODO:取得するデータ数を指定します。
        },
        urls: {
            linekensaku: "../api/_707_LineKenSaKu_DiaLog/Get"
        },
        parameter: {
            kbn_bumon: null,
            cd_kaisha: null,
            cd_kojyo: null
        }
    };

    /**
     * 検索ダイアログの初期化処理を行います。
     */
    _707_LineKenSaKu_DiaLog.initialize = function () {
        var element = $("#_707_LineKenSaKu_DiaLog");

        element.on("hidden.bs.modal", _707_LineKenSaKu_DiaLog.hidden);
        element.on("shown.bs.modal", _707_LineKenSaKu_DiaLog.shown);
        element.on("click", ".search", _707_LineKenSaKu_DiaLog.search);
        //TODO: 単一セレクトの場合は、下の１行を使用します
        element.on("click", ".search-list tbody ", _707_LineKenSaKu_DiaLog.select);
        //TODO: 複数セレクトの場合は、上の１行を削除し、下の３行をコメント解除します
        //element.on("click", ".select", _707_LineKenSaKu_DiaLog.select);
        //element.on("click", ".search-list tbody", _707_LineKenSaKu_DiaLog.selectOne);
        //element.find("[name='select_cd_all']").on("click", _707_LineKenSaKu_DiaLog.selectAll);
        _707_LineKenSaKu_DiaLog.element = element;

        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        _707_LineKenSaKu_DiaLog.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#_707_LineKenSaKu_DiaLog .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        _707_LineKenSaKu_DiaLog.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#_707_LineKenSaKu_DiaLog .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body",
                show: function () {
                    element.find(".alert-message").show();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                }
            });

        _707_LineKenSaKu_DiaLog.validator = element.validation(App.validation(_707_LineKenSaKu_DiaLog.options.validations, {
            success: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _707_LineKenSaKu_DiaLog.setColValidStyle(item.element);

                    _707_LineKenSaKu_DiaLog.notifyAlert.remove(item.element);
                }
            },
            fail: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _707_LineKenSaKu_DiaLog.setColInvalidStyle(item.element);
                    if (state && state.suppressMessage) {
                        continue;
                    }

                    _707_LineKenSaKu_DiaLog.notifyAlert.message(item.message, item.element).show();
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
    _707_LineKenSaKu_DiaLog.setColInvalidStyle = function (target) {
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
    _707_LineKenSaKu_DiaLog.setColValidStyle = function (target) {
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
    _707_LineKenSaKu_DiaLog.hidden = function (e) {
        var element = _707_LineKenSaKu_DiaLog.element,
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
            _707_LineKenSaKu_DiaLog.setColValidStyle(item);
        }

        _707_LineKenSaKu_DiaLog.notifyInfo.clear();
        _707_LineKenSaKu_DiaLog.notifyAlert.clear();
    };

    /**
     * 検索ダイアログ表示時処理を実行します。
     */
    _707_LineKenSaKu_DiaLog.shown = function (e) {
        var param = {
            kbn_bumon: _707_LineKenSaKu_DiaLog.parameter.kbn_bumon,
            cd_kaisha: _707_LineKenSaKu_DiaLog.parameter.cd_kaisha,
            cd_kojyo: _707_LineKenSaKu_DiaLog.parameter.cd_kojyo
        }
        return $.ajax(App.ajax.webapi.get(_707_LineKenSaKu_DiaLog.urls.linekensaku, param)).done(function (result) {
            _707_LineKenSaKu_DiaLog.bind(result);

        }).fail(function (error) {
            App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
        }).always(function () {
            App.ui.loading.close();
        })
        _707_LineKenSaKu_DiaLog.element.find(":input:not(button):first").focus();
    };

    /**
     * 検索ダイアログ　バリデーションルールを定義します。
     */
    _707_LineKenSaKu_DiaLog.options.validations = {
    };

    /**
     * 検索ダイアログの検索処理を実行します。
     */
    _707_LineKenSaKu_DiaLog.search = function () {
        var element = _707_LineKenSaKu_DiaLog.element,
            loadingTaget = element.find(".modal-content"),
            table = element.find(".search-list"),
            query;

        _707_LineKenSaKu_DiaLog.options.filter = _707_LineKenSaKu_DiaLog.createFilter();
        query = {
            url: _707_LineKenSaKu_DiaLog.urls/* TODO: 検索データ取得サービスの URL */,
            filter: _707_LineKenSaKu_DiaLog.options.filter,
            orderby: "TODO:ソート対象の列名",
            top: _707_LineKenSaKu_DiaLog.options.top,
            inlinecount: "allpages"
        };

        _707_LineKenSaKu_DiaLog.validator.validate()
        .then(function () {
            table.find("tbody:visible").remove();
            _707_LineKenSaKu_DiaLog.notifyAlert.clear();
            App.ui.loading.show("", loadingTaget);

            $.ajax(App.ajax.odata.get(App.data.toODataFormat(query)))
            .done(function (result) {

                _707_LineKenSaKu_DiaLog.bind(result);
            }).always(function () {

                App.ui.loading.close(loadingTaget);
            });
        });
    };

    /**
     * 検索ダイアログの検索条件を組み立てます
     */
    _707_LineKenSaKu_DiaLog.createFilter = function () {
        var criteria = _707_LineKenSaKu_DiaLog.element.find(".search-criteria").form().data(),
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
    _707_LineKenSaKu_DiaLog.bind = function (data) {
        var element = _707_LineKenSaKu_DiaLog.element,
            table = element.find(".search-list"),
            count = data.length,
            items = data.value ? data.value : data,
            i, l, item, clone;

        element.findP("data_count").text(items.length);
        element.findP("data_count_total").text(count);

        _707_LineKenSaKu_DiaLog.data = App.ui.page.dataSet();
        _707_LineKenSaKu_DiaLog.data.attach(items);

       
        table.find("tbody:visible").remove();

        for (i = 0, l = items.length; i < l; i++) {
            item = items[i];
            clone = table.find(".item-tmpl").clone();
            clone.form().bind(item);
            clone.appendTo(table).removeClass("item-tmpl").show();
        }

        if (count && count > App.settings.base.dialogDataTakeCount) {
            _707_LineKenSaKu_DiaLog.notifyInfo.message(App.messages.base.MS0011).show();
        }
    };

    /**
     * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
     */
    _707_LineKenSaKu_DiaLog.select = function (e) {
        var element = _707_LineKenSaKu_DiaLog.element,
            target = $(e.target),
            id = target.closest("tbody").attr("data-key"),
            data;

        if (App.isUndef(id)) {
            return;
        }

        data = _707_LineKenSaKu_DiaLog.data.entry(id);

        if (App.isFunc(_707_LineKenSaKu_DiaLog.dataSelected)) {
            if (!_707_LineKenSaKu_DiaLog.dataSelected(data)) {
                element.modal("hide");
            }
        } else {
            element.modal("hide");
        }
    };

    //TODO: 複数セレクトを使用する場合は、上の単一セレクト用select関数を削除し、下の複数セレクト用の関数をコメント解除します
    //TODO: 単一セレクトの場合は、不要なコメント部分は削除してください
    <%--
        /**
         * 一覧から行を選択された際の処理を実行します。（複数セレクト用）
         */
        _707_LineKenSaKu_DiaLog.select = function (e) {
            var element = _707_LineKenSaKu_DiaLog.element,
                items;

            //TODO: 選択された行から起動元画面に返却したい値を抽出します
            items = element.find(".search-list").find("input:checked[name='select_cd']").map(function (index, item) {
                var tbody = $(item).closest("tbody"),
                    id = tbody.attr("data-key"),
                    data = _707_LineKenSaKu_DiaLog.data.entry(id);
                return data.cd_torihiki;
            }).toArray();

            if (items.length == 0) {
                _707_LineKenSaKu_DiaLog.notifyAlert.message(App.messages.base.MS0020).show();
                return;
            }

            if (App.isFunc(_707_LineKenSaKu_DiaLog.dataSelected)) {
                if (!_707_LineKenSaKu_DiaLog.dataSelected(items)) {
                    element.modal("hide");
                }
            } else {
                element.modal("hide");
            }
        };

        /**
         * 一覧の行をクリックした際の処理を実行します。（複数セレクト用）
         */
        _707_LineKenSaKu_DiaLog.selectOne = function (e) {
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
        _707_LineKenSaKu_DiaLog.selectAll = function (e) {
            var target = $(e.target);
            _707_LineKenSaKu_DiaLog.element.find("[name='select_cd']:visible").prop("checked", target.is(":checked"));
        };
--%>
</script>

<div class="modal fade wide" tabindex="-1" id="_707_LineKenSaKu_DiaLog">
       <div class="modal-dialog" style="height: 350px; width: 41%">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">ライン検索</h4>
            </div>

            <div class="modal-body">
                <div class="search-criteria">
                    <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
                    <div class="row">
                        <%--
                        <div class="control-label col-xs-3">
                            <label>取引先名</label>
                        </div>
                        <div class="control col-xs-9">
                            <input type="text" class="ime-active" data-prop="nm_torihiki" />
                        </div>
--%>
                    </div>
                </div>
                <div style="position: relative; height: 50px;">
                  <%--  <button type="button" style="position: absolute; right: 5px; top: 5px;" class="btn btn-sm btn-primary search">検索</button>--%>
                    <div class="data-count" style="text-align:right;">
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
                                <th style="width: 15%;border-top:2px solid #dddddd; border-left:2px solid #dddddd;background-color:#eeeeee"></th>
                                <th style="width: 20%;text-align:center;border-top:2px solid #dddddd; border-left:2px solid #dddddd;background-color:#eeeeee ">コード</th>
                                <th style="text-align:center;border-top:2px solid #dddddd; border-left:2px solid #dddddd; border-right:2px solid #dddddd;background-color:#eeeeee">ライン名</th>

                            <%--    <%--単一セレクトの場合は、以下の１行を使用する--%>
                           <%--     <th style="width: 5%;"></th>--%>
                                <%--複数セレクトの場合は、上の１行をカットし、下の３行をコメント解除してください--%>
                                <%--                                <th style="width: 5%;">
                                    <input type="checkbox" name="select_cd_all" />
                                </th>--%>
                            <%--      <th style="width: 5%;">
                                    <label>コード</label>
                                </th>
                                <th style="width: 10%;">
                                    <label>ライン名</label>
                                </th>--%>
                            </tr>
                        </thead>
                    </table>
                </div>
              <div style="height: 274px; overflow: scroll; overflow-x: hidden;">
                    <table class="table table-striped table-condensed search-list">
                        <!--TODO: ダイアログの明細行を定義するHTMLをここに記述します。列幅の指定はヘッダーに合わせて定義します。-->
                        <tbody class="item-tmpl" style="display: none;">
                            <tr>
                                <%--単一セレクトの場合は、以下の３行を使用する--%>
                              <%--  <td style="width: 5%;">
                                    <button id="return_data" type="button" style="margin: 1px; padding: 1px;" class="btn btn-success btn-xs select">選択</button>
                                </td>--%>
                                <%--複数セレクトの場合は、上の３行をカットし、下の３行をコメント解除してください--%>
                                <%--                                <td style="width: 5%;">
                                    <input type="checkbox" name="select_cd" />
                                </td>--%>
                              <%--  <td style="width: 5%;">
                                    <label class="ime-active" data-prop="cd_line" ></label>
                                </td>
                                <td style="width: 10%;">
                                    <label class="ime-active" data-prop="nm_line" ></label>
                                </td>--%>
                                  <td style="width: 15%;text-align:center;border-left:2px solid #dddddd;border-bottom:2px solid #dddddd;border-top:none;">
                                    <button type="button" style="margin: 1px;padding: 1px;" class="btn btn-success btn-xs select">選択</button>
                                </td>
                                <td style="width: 20%;text-align:left;border-left:2px solid #dddddd;border-bottom:2px solid #dddddd;border-top:none;">
                                    <span data-prop="cd_line"></span>
                                </td>
                                <td style="border-left:2px solid #dddddd;border-bottom:2px solid #dddddd;border-top:none;text-align:left">
                                    <span data-prop="nm_line"></span>
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
