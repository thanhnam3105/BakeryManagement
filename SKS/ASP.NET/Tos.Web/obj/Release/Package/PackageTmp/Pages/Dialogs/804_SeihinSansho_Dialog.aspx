<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="804_SeihinSansho_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs._804_SeihinSansho_Dialog" %>
<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>

    <script type="text/javascript">

        /**
         * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
         */
        var _804_SeihinSansho_Dialog = {
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.dialogDataTakeCount   // TODO:取得するデータ数を指定します。
            },
            urls: {
                search: "../api/_804_SeihinSansho_Dialog"
            },
            param: {
                cd_haigo: ""
            }
        };

        /**
         * 検索ダイアログの初期化処理を行います。
         */
        _804_SeihinSansho_Dialog.initialize = function () {
            var element = $("#_804_SeihinSansho_Dialog");

            element.on("hidden.bs.modal", _804_SeihinSansho_Dialog.hidden);
            element.on("shown.bs.modal", _804_SeihinSansho_Dialog.shown);
            element.on("show.bs.modal", _804_SeihinSansho_Dialog.show);
            //TODO: 単一セレクトの場合は、下の１行を使用します
            //element.on("click", ".search-list tbody ", _804_SeihinSansho_Dialog.select);
            //TODO: 複数セレクトの場合は、上の１行を削除し、下の３行をコメント解除します
            //element.on("click", ".select", _804_SeihinSansho_Dialog.select);
            //element.on("click", ".search-list tbody", _804_SeihinSansho_Dialog.selectOne);
            //element.find("[name='select_cd_all']").on("click", _804_SeihinSansho_Dialog.selectAll);
            _804_SeihinSansho_Dialog.element = element;

            element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
            element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

            _804_SeihinSansho_Dialog.notifyInfo =
                 App.ui.notify.info(element, {
                     container: "#_804_SeihinSansho_Dialog .dialog-slideup-area .info-message",
                     bodyContainer: ".modal-body",
                     show: function () {
                         element.find(".info-message").show();
                     },
                     clear: function () {
                         element.find(".info-message").hide();
                     }
                 });
            _804_SeihinSansho_Dialog.notifyAlert =
                App.ui.notify.alert(element, {
                    container: "#_804_SeihinSansho_Dialog .dialog-slideup-area .alert-message",
                    bodyContainer: ".modal-body",
                    show: function () {
                        element.find(".alert-message").show();
                    },
                    clear: function () {
                        element.find(".alert-message").hide();
                    }
                });

            _804_SeihinSansho_Dialog.validator = element.validation(App.validation(_804_SeihinSansho_Dialog.options.validations, {
                success: function (results, state) {
                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        _804_SeihinSansho_Dialog.setColValidStyle(item.element);

                        _804_SeihinSansho_Dialog.notifyAlert.remove(item.element);
                    }
                },
                fail: function (results, state) {
                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        _804_SeihinSansho_Dialog.setColInvalidStyle(item.element);
                        if (state && state.suppressMessage) {
                            continue;
                        }

                        _804_SeihinSansho_Dialog.notifyAlert.message(item.message, item.element).show();
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
        _804_SeihinSansho_Dialog.setColInvalidStyle = function (target) {
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
        _804_SeihinSansho_Dialog.setColValidStyle = function (target) {
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
        _804_SeihinSansho_Dialog.hidden = function (e) {
            var element = _804_SeihinSansho_Dialog.element,
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
                _804_SeihinSansho_Dialog.setColValidStyle(item);
            }

            _804_SeihinSansho_Dialog.notifyInfo.clear();
            _804_SeihinSansho_Dialog.notifyAlert.clear();
        };

        /**
         * 検索ダイアログ表示時処理を実行します。
         */
        _804_SeihinSansho_Dialog.shown = function (e) {

            _804_SeihinSansho_Dialog.element.find(":input:not(button):first").focus();
        };

        _804_SeihinSansho_Dialog.show = function () {
            _804_SeihinSansho_Dialog.search();
        }

        /**
         * 検索ダイアログ　バリデーションルールを定義します。
         */
        _804_SeihinSansho_Dialog.options.validations = {
        };

        /**
         * Fill full length of cd_hin
         */
        _804_SeihinSansho_Dialog.options.bindOptions = {
            appliers: {
                cd_hin: function (value, element) {
                    element.text(App.common.fillString(value, _804_SeihinSansho_Dialog.param.su_code_standard));
                    return true;
                }
            }
        };

        /**
         * 検索ダイアログの検索処理を実行します。
         */
        _804_SeihinSansho_Dialog.search = function () {
            var element = _804_SeihinSansho_Dialog.element,
                loadingTaget = element.find(".modal-content"),
                table = element.find(".search-list"),
                query;

            _804_SeihinSansho_Dialog.options.filter = _804_SeihinSansho_Dialog.createFilter();
            query = {
                url: _804_SeihinSansho_Dialog.urls.search,
                filter: _804_SeihinSansho_Dialog.options.filter,
                orderby: "no_yusen"
            };

            _804_SeihinSansho_Dialog.validator.validate()
            .then(function () {
                table.find("tbody:visible").remove();
                _804_SeihinSansho_Dialog.notifyAlert.clear();
                App.ui.loading.show("", loadingTaget);

                $.ajax(App.ajax.webapi.get(_804_SeihinSansho_Dialog.urls.search, _804_SeihinSansho_Dialog.options.filter))
                .done(function (result) {

                    _804_SeihinSansho_Dialog.bind(result);
                }).always(function () {

                    App.ui.loading.close(loadingTaget);
                });
            });
        };

        /**
         * 検索ダイアログの検索条件を組み立てます
         */
        _804_SeihinSansho_Dialog.createFilter = function () {
            return {
                lst_haigo: (_804_SeihinSansho_Dialog.param.cd_haigo || []).join(",")
            }
        };

        /**
         * 検索ダイアログの一覧にデータをバインドします。
         */
        _804_SeihinSansho_Dialog.bind = function (data) {
            var element = _804_SeihinSansho_Dialog.element,
                table = element.find(".search-list"),
                items = data.Items ? data.Items : data,
                i, l, item, clone;

            element.findP("data_count").text(items.length);
            element.findP("data_count_total").text(items.length);

            _804_SeihinSansho_Dialog.data = App.ui.page.dataSet();
            _804_SeihinSansho_Dialog.data.attach(items);

            table.find("tbody:visible").remove();

            for (i = 0, l = items.length; i < l; i++) {
                item = items[i];
                clone = table.find(".item-tmpl").clone();
                clone.form(_804_SeihinSansho_Dialog.options.bindOptions).bind(item);
                clone.appendTo(table).removeClass("item-tmpl").show();
            }

            //if (!items.length) {
            //    _804_SeihinSansho_Dialog.notifyInfo.message(App.messages.app.AP0007, "804_AP0007").show();
            //}
        };

        /**
         * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
         */
        _804_SeihinSansho_Dialog.select = function (e) {
            var element = _804_SeihinSansho_Dialog.element,
                target = $(e.target),
                id = target.closest("tbody").attr("data-key"),
                data;

            if (App.isUndef(id)) {
                return;
            }

            data = _804_SeihinSansho_Dialog.data.entry(id);

            if (App.isFunc(_804_SeihinSansho_Dialog.dataSelected)) {
                if (!_804_SeihinSansho_Dialog.dataSelected(data)) {
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
        _804_SeihinSansho_Dialog.select = function (e) {
            var element = _804_SeihinSansho_Dialog.element,
                items;

            //TODO: 選択された行から起動元画面に返却したい値を抽出します
            items = element.find(".search-list").find("input:checked[name='select_cd']").map(function (index, item) {
                var tbody = $(item).closest("tbody"),
                    id = tbody.attr("data-key"),
                    data = _804_SeihinSansho_Dialog.data.entry(id);
                return data.cd_torihiki;
            }).toArray();

            if (items.length == 0) {
                _804_SeihinSansho_Dialog.notifyAlert.message(App.messages.base.MS0020).show();
                return;
            }

            if (App.isFunc(_804_SeihinSansho_Dialog.dataSelected)) {
                if (!_804_SeihinSansho_Dialog.dataSelected(items)) {
                    element.modal("hide");
                }
            } else {
                element.modal("hide");
            }
        };

        /**
         * 一覧の行をクリックした際の処理を実行します。（複数セレクト用）
         */
        _804_SeihinSansho_Dialog.selectOne = function (e) {
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
        _804_SeihinSansho_Dialog.selectAll = function (e) {
            var target = $(e.target);
            _804_SeihinSansho_Dialog.element.find("[name='select_cd']:visible").prop("checked", target.is(":checked"));
        };
--%>
    </script>

    <div class="modal fade wide" tabindex="-1" id="_804_SeihinSansho_Dialog">
    <div class="modal-dialog" style="height: 350px; width: 600px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">製品参照</h4>
            </div>

            <div class="modal-body">
                <div style="position: relative; height: 50px;">
                    <div class="data-count">
                        <span data-prop="data_count"></span>
                        <span>/</span>
                        <span data-prop="data_count_total"></span>
                    </div>
                </div>
                <div style="padding-right: 16px;">
                    <table class="table table-striped table-condensed " style="margin-bottom: 0px;">
                        <thead>
                            <tr>
                                <th style="width: 105px">コード</th>
                                <th>名称</th>
                            </tr>
                        </thead>
                    </table>
                </div>
                <div style="height: 252px; overflow: scroll; overflow-x: hidden; margin-top: -2px; border-top: 2px solid #ddd;">
                    <table class="table table-striped table-condensed search-list" style="margin-top: -2px;">
                        <tbody class="item-tmpl" style="display: none;">
                            <tr style="cursor: default">
                                <td style="width: 105px">
                                    <label data-prop="cd_hin"></label>
                                </td>
                                <td>
                                    <label data-prop="nm_seihin" class="overflow-ellipsis"></label>
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
                <button type="button" class="cancel-button btn btn-sm btn-default" data-dismiss="modal" name="close">閉じる</button>
            </div>

        </div>
    </div>
    </div>