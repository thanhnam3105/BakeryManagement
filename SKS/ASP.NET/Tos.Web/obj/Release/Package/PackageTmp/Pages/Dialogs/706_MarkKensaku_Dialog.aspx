<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="_706_MarkKensaku_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs._706_MarkKensaku_Dialog" %>
<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>

    <script type="text/javascript">

        /**
         * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
         */
        var markDialog = {
            options: {
                parameter: {
                    cd_kaisha: '',
                    cd_kojyo: '',
                    no_gamen: ''
                }
                //skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                //top: App.settings.base.dialogDataTakeCount   // TODO:取得するデータ数を指定します。
            },
            urls: {
                MarkKensaku_Dialog: "../api/MarkKensaku_Dialog",
            }
        };

        /**
         * 検索ダイアログの初期化処理を行います。
         */
        markDialog.initialize = function () {
            var element = $("#_706_MarkKensaku_Dialog");

            element.on("hidden.bs.modal", markDialog.hidden);
            element.on("shown.bs.modal", markDialog.shown);
            element.on("click", ".search", markDialog.search);
            //TODO: 単一セレクトの場合は、下の１行を使用します
            element.on("click", ".search-list tbody ", markDialog.select);
            //TODO: 複数セレクトの場合は、上の１行を削除し、下の３行をコメント解除します
            //element.on("click", ".select", markDialog.select);
            //element.on("click", ".search-list tbody", markDialog.selectOne);
            //element.find("[name='select_cd_all']").on("click", markDialog.selectAll);
            markDialog.element = element;

            element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
            element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

            markDialog.notifyInfo =
                 App.ui.notify.info(element, {
                     container: "#_706_MarkKensaku_Dialog .dialog-slideup-area .info-message",
                     bodyContainer: ".modal-body",
                     show: function () {
                         element.find(".info-message").show();
                     },
                     clear: function () {
                         element.find(".info-message").hide();
                     }
                 });
            markDialog.notifyAlert =
                App.ui.notify.alert(element, {
                    container: "#_706_MarkKensaku_Dialog .dialog-slideup-area .alert-message",
                    bodyContainer: ".modal-body",
                    show: function () {
                        element.find(".alert-message").show();
                    },
                    clear: function () {
                        element.find(".alert-message").hide();
                    }
                });

            markDialog.validator = element.validation(App.validation(markDialog.options.validations, {
                success: function (results, state) {
                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        markDialog.setColValidStyle(item.element);

                        markDialog.notifyAlert.remove(item.element);
                    }
                },
                fail: function (results, state) {
                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        markDialog.setColInvalidStyle(item.element);
                        if (state && state.suppressMessage) {
                            continue;
                        }

                        markDialog.notifyAlert.message(item.message, item.element).show();
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
        markDialog.setColInvalidStyle = function (target) {
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
        markDialog.setColValidStyle = function (target) {
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
        markDialog.hidden = function (e) {
            var element = markDialog.element,
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
                markDialog.setColValidStyle(item);
            }

            markDialog.notifyInfo.clear();
            markDialog.notifyAlert.clear();
        };

        /**
         * 検索ダイアログ表示時処理を実行します。
         */
        markDialog.shown = function (e) {

            markDialog.element.find(":input:not(button):first").focus();
            markDialog.search();
            
        };

        /**
         * 検索ダイアログ　バリデーションルールを定義します。
         */
        markDialog.options.validations = {
        };

        /**
         * 検索ダイアログの検索処理を実行します。
         */
        markDialog.search = function () {
            var element = markDialog.element,
                loadingTaget = element.find(".modal-content"),
                table = element.find(".search-list"),
                query;

            markDialog.options.filter = markDialog.createFilter();

            markDialog.validator.validate()
            .then(function () {
                table.find("tbody:visible").remove();
                markDialog.notifyAlert.clear();
                App.ui.loading.show("", loadingTaget);

                $.ajax(App.ajax.webapi.get(markDialog.urls.MarkKensaku_Dialog, markDialog.options.parameter))
                .done(function (result) {

                    markDialog.bind(result);
                }).always(function () {

                    App.ui.loading.close(loadingTaget);
                    $(table.find("tbody").not(".item-tmpl")[0]).find(":input:first").focus()
                });
            });
        };

        /**
         * 検索ダイアログの検索条件を組み立てます
         */
        markDialog.createFilter = function () {
            var criteria = markDialog.element.find(".search-criteria").form().data(),
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
        markDialog.bind = function (data) {
            
            var element = markDialog.element,
                table = element.find(".search-list"),
                count = data.length,
                items = data.value ? data.value : data,
                i, l, item, clone;

            element.findP("data_count").text(data.length);
            element.findP("data_count_total").text(count);

            markDialog.data = App.ui.page.dataSet();
            markDialog.data.attach(items);

            table.find("tbody:visible").remove();

            for (i = 0, l = items.length; i < l; i++) {
                item = items[i];
                clone = table.find(".item-tmpl").clone();
                clone.form().bind(item);
                clone.appendTo(table).removeClass("item-tmpl").show();
            }

            //if (count && count > App.settings.base.dialogDataTakeCount) {
            //    markDialog.notifyInfo.message(App.messages.base.MS0011).show();
            //}
        };

        /**
         * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
         */
        markDialog.select = function (e) {
            var element = markDialog.element,
                target = $(e.target),
                id = target.closest("tbody").attr("data-key"),
                data;

            if (App.isUndef(id)) {
                return;
            }
            
            data = markDialog.data.entry(id);

            if (App.isFunc(markDialog.dataSelected)) {
                if (!markDialog.dataSelected(data)) {
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
        markDialog.select = function (e) {
            var element = markDialog.element,
                items;

            //TODO: 選択された行から起動元画面に返却したい値を抽出します
            items = element.find(".search-list").find("input:checked[name='select_cd']").map(function (index, item) {
                var tbody = $(item).closest("tbody"),
                    id = tbody.attr("data-key"),
                    data = markDialog.data.entry(id);
                return data.cd_torihiki;
            }).toArray();

            if (items.length == 0) {
                markDialog.notifyAlert.message(App.messages.base.MS0020).show();
                return;
            }

            if (App.isFunc(markDialog.dataSelected)) {
                if (!markDialog.dataSelected(items)) {
                    element.modal("hide");
                }
            } else {
                element.modal("hide");
            }
        };

        /**
         * 一覧の行をクリックした際の処理を実行します。（複数セレクト用）
         */
        markDialog.selectOne = function (e) {
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
        markDialog.selectAll = function (e) {
            var target = $(e.target);
            markDialog.element.find("[name='select_cd']:visible").prop("checked", target.is(":checked"));
        };
--%>
    </script>

    <div class="modal fade wide" tabindex="-1" id="_706_MarkKensaku_Dialog">
    <div class="modal-dialog" style="height: 350px; width: 41%">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">マーク検索</h4>
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
                <div style="position: relative; height: 30px;">
                    <%--<button type="button" style="position: absolute; right: 5px; top: 5px;" class="btn btn-sm btn-primary search">検索</button>--%>
                    <div class="data-count text-right">
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
                                <th style="text-align:center;border-top:2px solid #dddddd; border-left:2px solid #dddddd; border-right:2px solid #dddddd;background-color:#eeeeee">マーク名</th>
                            </tr>
                        </thead>
                    </table>
                </div>
                <div style="height: 274px; overflow: scroll; overflow-x: hidden;">
                    <table class="table table-striped table-condensed search-list">
                    <!--TODO: ダイアログの明細行を定義するHTMLをここに記述します。列幅の指定はヘッダーに合わせて定義します。-->
                        <tbody class="item-tmpl" style="display: none; border-top:0px">
                            <tr>
                                <%--単一セレクトの場合は、以下の３行を使用する--%>
                                <td style="width: 15%;text-align:center;border-left:2px solid #dddddd;border-bottom:2px solid #dddddd;border-top:none;">
                                    <button type="button" style="margin: 1px;padding: 1px;" class="btn btn-success btn-xs select">選択</button>
                                </td>
                                <td style="width: 20%;text-align:center;border-left:2px solid #dddddd;border-bottom:2px solid #dddddd;border-top:none;">
                                    <span data-prop="mark"></span>
                                </td>
                                <td style="border-left:2px solid #dddddd;border-bottom:2px solid #dddddd;border-top:none;">
                                    <span data-prop="nm_mark"></span>
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