<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="800_HinmeiSentaku_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs._800_HinmeiSentaku_Dialog" %>
<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>

    <script type="text/javascript">

        /**
         * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
         */
        var _800_HinmeiSentaku_Dialog = {
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.dialogDataTakeCount   // TODO:取得するデータ数を指定します。
            },
            urls: {
                search: "../Services/ShisaQuickService.svc/vw_shohinkaihatsu_dialog_g3_nm_meisho_hinmei"
            }
        };

        /**
         * 検索ダイアログの初期化処理を行います。
         */
        _800_HinmeiSentaku_Dialog.initialize = function () {
            var element = $("#_800_HinmeiSentaku_Dialog");

            element.on("hidden.bs.modal", _800_HinmeiSentaku_Dialog.hidden);
            element.on("shown.bs.modal", _800_HinmeiSentaku_Dialog.shown);
            element.on("click", ".search", _800_HinmeiSentaku_Dialog.search);
            //TODO: 単一セレクトの場合は、下の１行を使用します
            element.on("click", ".search-list tbody ", _800_HinmeiSentaku_Dialog.select);
            element.on("change", _800_HinmeiSentaku_Dialog.change);
            _800_HinmeiSentaku_Dialog.element = element;

            element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
            element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

            _800_HinmeiSentaku_Dialog.notifyInfo =
                 App.ui.notify.info(element, {
                     container: "#_800_HinmeiSentaku_Dialog .dialog-slideup-area .info-message",
                     bodyContainer: ".modal-body",
                     show: function () {
                         element.find(".info-message").show();
                     },
                     clear: function () {
                         element.find(".info-message").hide();
                     }
                 });
            _800_HinmeiSentaku_Dialog.notifyAlert =
                App.ui.notify.alert(element, {
                    container: "#_800_HinmeiSentaku_Dialog .dialog-slideup-area .alert-message",
                    bodyContainer: ".modal-body",
                    show: function () {
                        element.find(".alert-message").show();
                    },
                    clear: function () {
                        element.find(".alert-message").hide();
                    }
                });

            _800_HinmeiSentaku_Dialog.validator = element.validation(App.validation(_800_HinmeiSentaku_Dialog.options.validations, {
                success: function (results, state) {
                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        _800_HinmeiSentaku_Dialog.setColValidStyle(item.element);

                        _800_HinmeiSentaku_Dialog.notifyAlert.remove(item.element);
                    }
                },
                fail: function (results, state) {
                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        _800_HinmeiSentaku_Dialog.setColInvalidStyle(item.element);
                        if (state && state.suppressMessage) {
                            continue;
                        }

                        _800_HinmeiSentaku_Dialog.notifyAlert.message(item.message, item.element).show();
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
        _800_HinmeiSentaku_Dialog.setColInvalidStyle = function (target) {
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
        _800_HinmeiSentaku_Dialog.setColValidStyle = function (target) {
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
        _800_HinmeiSentaku_Dialog.hidden = function (e) {
            var element = _800_HinmeiSentaku_Dialog.element,
                table = element.find(".search-list");

            //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
            element.findP("nm_hinmei").val("");
            element.findP("sort").val("asc");
            //TODO: 複数セレクトの場合は、下の１行をコメント解除します。
            //element.find(":checked").prop("checked", false);
            table.find("tbody").not(".item-tmpl").remove();

            element.findP("data_count").text("");
            element.findP("data_count_total").text("");

            var items = element.find(".search-criteria :input:not(button)");
            for (var i = 0; i < items.length; i++) {
                var item = items[i];
                _800_HinmeiSentaku_Dialog.setColValidStyle(item);
            }

            _800_HinmeiSentaku_Dialog.notifyInfo.clear();
            _800_HinmeiSentaku_Dialog.notifyAlert.clear();
        };

        /**
         * 検索ダイアログ表示時処理を実行します。
         */
        _800_HinmeiSentaku_Dialog.shown = function (e) {

            _800_HinmeiSentaku_Dialog.element.find(":input:not(button):first").focus();
        };

        /**
         * 検索ダイアログ　バリデーションルールを定義します。
         */
        _800_HinmeiSentaku_Dialog.options.validations = {
            nm_hinmei: {
                rules: {
                    maxbytelength: 120
                },
                options: {
                    name: "コード・名称",
                    byte: 60
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            }
        };

        /**
         * 検索ダイアログの検索処理を実行します。
         */
        _800_HinmeiSentaku_Dialog.search = function () {
            var element = _800_HinmeiSentaku_Dialog.element,
                loadingTaget = element.find(".modal-content"),
                table = element.find(".search-list"),
                query;

            _800_HinmeiSentaku_Dialog.options.filter = _800_HinmeiSentaku_Dialog.createFilter();
            query = {
                url: _800_HinmeiSentaku_Dialog.urls.search,
                filter: _800_HinmeiSentaku_Dialog.options.filter,
                orderby: "KEN1_HINNM_NM " + element.findP("sort").val(),
                top: _800_HinmeiSentaku_Dialog.options.top,
                inlinecount: "allpages"
            };

            _800_HinmeiSentaku_Dialog.validator.validate()
            .then(function () {
                table.find("tbody:visible").remove();
                _800_HinmeiSentaku_Dialog.notifyAlert.clear();
                App.ui.loading.show("", loadingTaget);

                $.ajax(App.ajax.odata.get(App.data.toODataFormat(query)))
                .done(function (result) {

                    _800_HinmeiSentaku_Dialog.bind(result);
                }).always(function () {

                    App.ui.loading.close(loadingTaget);
                });
            });
        };

        /**
         * Element on change
         */
        _800_HinmeiSentaku_Dialog.change = function (e) {
            var target = $(e.target);
            _800_HinmeiSentaku_Dialog.validator.validate({
                targets: target
            })
        }

        /**
         * 検索ダイアログの検索条件を組み立てます
         */
        _800_HinmeiSentaku_Dialog.createFilter = function () {
            var criteria = _800_HinmeiSentaku_Dialog.element.find(".search-criteria").form().data(),
                filters = [];

            if (!App.isUndefOrNullOrStrEmpty(criteria.nm_hinmei)) {
                filters.push("( substringof('" + encodeURIComponent(criteria.nm_hinmei) + "', KEN1_HINNM_C) eq true or substringof('" + encodeURIComponent(criteria.nm_hinmei) + "', KEN1_HINNM_NM) eq true )");
            }

            return filters.join(" and ");
        };

        /**
         * 検索ダイアログの一覧にデータをバインドします。
         */
        _800_HinmeiSentaku_Dialog.bind = function (data) {
            var element = _800_HinmeiSentaku_Dialog.element,
                table = element.find(".search-list"),
                count = data["odata.count"],
                items = data.value ? data.value : data,
                i, l, item, clone;

            element.findP("data_count").text(data.value.length);
            element.findP("data_count_total").text(count);

            _800_HinmeiSentaku_Dialog.data = App.ui.page.dataSet();
            _800_HinmeiSentaku_Dialog.data.attach(items);

            table.find("tbody:visible").remove();

            for (i = 0, l = items.length; i < l; i++) {
                item = items[i];
                clone = table.find(".item-tmpl").clone();
                clone.form().bind(item);
                clone.appendTo(table).removeClass("item-tmpl").show();
            }

            if (count && count > App.settings.base.dialogDataTakeCount) {
                _800_HinmeiSentaku_Dialog.notifyInfo.message(App.str.format(App.messages.app.AP0021, count), "800_HinmeiSentaku_AP0021").show();
            }
        };

        /**
         * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
         */
        _800_HinmeiSentaku_Dialog.select = function (e) {
            var element = _800_HinmeiSentaku_Dialog.element,
                target = $(e.target),
                id = target.closest("tbody").attr("data-key"),
                data;

            if (App.isUndef(id)) {
                return;
            }

            data = _800_HinmeiSentaku_Dialog.data.entry(id);

            if (App.isFunc(_800_HinmeiSentaku_Dialog.dataSelected)) {
                if (!_800_HinmeiSentaku_Dialog.dataSelected(data)) {
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
        _800_HinmeiSentaku_Dialog.select = function (e) {
            var element = _800_HinmeiSentaku_Dialog.element,
                items;

            //TODO: 選択された行から起動元画面に返却したい値を抽出します
            items = element.find(".search-list").find("input:checked[name='select_cd']").map(function (index, item) {
                var tbody = $(item).closest("tbody"),
                    id = tbody.attr("data-key"),
                    data = _800_HinmeiSentaku_Dialog.data.entry(id);
                return data.cd_torihiki;
            }).toArray();

            if (items.length == 0) {
                _800_HinmeiSentaku_Dialog.notifyAlert.message(App.messages.base.MS0020).show();
                return;
            }

            if (App.isFunc(_800_HinmeiSentaku_Dialog.dataSelected)) {
                if (!_800_HinmeiSentaku_Dialog.dataSelected(items)) {
                    element.modal("hide");
                }
            } else {
                element.modal("hide");
            }
        };

        /**
         * 一覧の行をクリックした際の処理を実行します。（複数セレクト用）
         */
        _800_HinmeiSentaku_Dialog.selectOne = function (e) {
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
        _800_HinmeiSentaku_Dialog.selectAll = function (e) {
            var target = $(e.target);
            _800_HinmeiSentaku_Dialog.element.find("[name='select_cd']:visible").prop("checked", target.is(":checked"));
        };
--%>
    </script>

    <div class="modal fade wide smaller" tabindex="-1" id="_800_HinmeiSentaku_Dialog">
    <div class="modal-dialog" style="height: 350px; width: 600px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">名称（品名）選択</h4>
            </div>

            <div class="modal-body">
                <div class="search-criteria">
                    <div class="row">
                        <div class="control-label col-xs-3">
                            <label>コード・名称</label>
                        </div>
                        <div class="control col-xs-5">
                            <input type="text" class="ime-active" data-prop="nm_hinmei" />
                        </div>
                        <div class="control-label col-xs-2">
                            <label>ソート</label>
                        </div>
                        <div class="control col-xs-2">
                            <select data-prop="sort">
                                <option value="asc">昇順</option>
                                <option value="desc">降順</option>
                            </select>
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

                <div class="dialog-table">
                    <table class="table table-striped table-condensed " style="margin-bottom: 0px;">
                    <!--TODO: ダイアログのヘッダーを定義するHTMLをここに記述します。列幅の合計が100％になるように定義します。-->
                        <thead>
                            <tr>
                                <th style="width: 65px;"></th>
                                <th style="width: 100px; text-align: center">コード</th>
                                <th>名称</th>
                            </tr>
                        </thead>
                    </table>
                </div>
                <div style="height: 252px; overflow: scroll; overflow-x: hidden;">
                    <table class="table table-striped table-condensed search-list" style="margin-top: -2px">
                    <!--TODO: ダイアログの明細行を定義するHTMLをここに記述します。列幅の指定はヘッダーに合わせて定義します。-->
                        <tbody class="item-tmpl" style="display: none;">
                            <tr>
                                <td style="width: 65px;">
                                    <button type="button" style="margin: 1px;padding: 1px;" class="btn btn-success btn-xs select">選択</button>
                                </td>
                                <td style="width: 100px; text-align: right;">
                                    <label data-prop="KEN1_HINNM_C" class="overflow-ellipsis"></label>
                                </td>
                                <td>
                                    <label data-prop="KEN1_HINNM_NM" class="overflow-ellipsis"></label>
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