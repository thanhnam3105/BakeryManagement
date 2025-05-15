<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="S_MemoDialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs.S_MemoDialog" %>
<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver1.7)】 Template--%>

    <script type="text/javascript">

        /** readme
        *********************************************************************************************************************************************
        *=================================
        *********************************************************************************************************************************************
         *2019-02-22
         * From : 14007
         * Đây là bản thử nghiệm trên version farmwork 1.7
         * Khi code chính thức hãy xóa bản này đi và làm việc trên teamplate farmwork 2.1          
        *********************************************************************************************************************************************
        *=================================
        *********************************************************************************************************************************************
        */

        /**
         * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
         */
        var S_MemoDialog = {
            options: {},
            urls: {},
            values: {
                memo: ""
            }
        };

        /**
         * 検索ダイアログの初期化処理を行います。
         */
        S_MemoDialog.initialize = function () {
            var element = $("#S_MemoDialog");

            element.on("hidden.bs.modal", S_MemoDialog.hidden);
            element.on("shown.bs.modal", S_MemoDialog.show);
            element.on("click", ".search", S_MemoDialog.search);
            //TODO: 単一セレクトの場合は、下の１行を使用します
            //element.on("click", ".search-list tbody ", S_MemoDialog.select);
            //TODO: 複数セレクトの場合は、上の１行を削除し、下の３行をコメント解除します
            element.on("click", ".select", S_MemoDialog.select);
            //element.on("click", ".search-list tbody", S_MemoDialog.selectOne);
            //element.find("[name='select_cd_all']").on("click", S_MemoDialog.selectAll);
            S_MemoDialog.element = element;

            element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
            element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

            S_MemoDialog.notifyInfo =
                 App.ui.notify.info(element, {
                     container: "#S_MemoDialog .dialog-slideup-area .info-message",
                     messageContainerQuery: "ul",
                     show: function () {
                         element.find(".info-message").show();
                     },
                     clear: function () {
                         element.find(".info-message").hide();
                     }
                 });
            S_MemoDialog.notifyAlert =
                App.ui.notify.alert(element, {
                    container: "#S_MemoDialog .dialog-slideup-area .alert-message",
                    messageContainerQuery: "ul",
                    show: function () {
                        element.find(".alert-message").show();
                    },
                    clear: function () {
                        element.find(".alert-message").hide();
                    }
                });

            S_MemoDialog.validator = element.validation(App.validation(S_MemoDialog.options.validations, {
                success: function (results, state) {
                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        S_MemoDialog.setColValidStyle(item.element);

                        S_MemoDialog.notifyAlert.remove(item.element);
                    }
                },
                fail: function (results, state) {

                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        S_MemoDialog.setColInvalidStyle(item.element);
                        if (state && state.suppressMessage) {
                            continue;
                        }

                        S_MemoDialog.notifyAlert.message(item.message, item.element).show();
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
        S_MemoDialog.setColInvalidStyle = function (target) {
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
        S_MemoDialog.setColValidStyle = function (target) {
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
        S_MemoDialog.hidden = function (e) {

            var element = S_MemoDialog.element,
                table = element.find(".search-list");

            //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
            element.find(":input").val("");
            //TODO: 複数セレクトの場合は、下の１行をコメント解除します。
            //element.find(":checked").prop("checked", false);

            S_MemoDialog.notifyInfo.clear();
            S_MemoDialog.notifyAlert.clear();
        };

        /**
         * Event when dialog show
         */
        S_MemoDialog.show = function () {
            S_MemoDialog.element.findP("nm_memo").val(S_MemoDialog.values.memo);
            setTimeout(function () {
                S_MemoDialog.element.findP("nm_memo").focus();
            }, 200);
        };

        /**
         * 検索ダイアログ　バリデーションルールを定義します。
         */
        S_MemoDialog.options.validations = {
        };

        /**
         * 検索ダイアログの一覧にデータをバインドします。
         */
        S_MemoDialog.bind = function (data) {
            var element = S_MemoDialog.element,
                table = element.find(".search-list"),
                count = data["odata.count"],
                items = data.value ? data.value : data,
                i, l, item, clone;

            element.findP("data_count").text(data.value.length);
            element.findP("data_count_total").text(count);

            S_MemoDialog.data = App.ui.page.dataSet();
            S_MemoDialog.data.attach(items);

            table.find("tbody:visible").remove();

            for (i = 0, l = items.length; i < l; i++) {
                item = items[i];
                clone = table.find(".item-tmpl").clone();
                clone.form().bind(item);
                clone.appendTo(table).removeClass("item-tmpl").show();
            }

            if (count && count > App.settings.base.dialogDataTakeCount) {
                S_MemoDialog.notifyInfo.message(App.messages.base.MS0011).show();
            }

        };

        /**
         * 検索ダイアログの検索処理を実行します。
         */
        S_MemoDialog.search = function () {
            var element = S_MemoDialog.element,
                loadingTaget = element.find(".modal-content"),
                criteria = [],
                table = element.find(".search-list"),
                nameCriteria; 

            //TODO: 検索条件を構築する処理をここに記述します。
            //nameCriteria = element.findP("nm_torihiki").val();
            //if (nameCriteria) {
            //    criteria.push("substringof('" + encodeURIComponent(nameCriteria) + "', nm_torihiki) eq true");
            //}

            S_MemoDialog.validator.validate()
            .then(function () {
                table.find("tbody:visible").remove();
                S_MemoDialog.notifyAlert.clear();

                App.ui.loading.show("", loadingTaget);

                //TODO: 検索処理をここに記述します。
                $.ajax(App.ajax.odata.get(S_MemoDialog.urls/* TODO: 検索データ取得サービスの URL */ +
                        "?$top=" + App.settings.base.dialogDataTakeCount + "&$inlinecount=allpages" +
                        (criteria.length ? "&$filter=" + criteria.join(" and ") : "")
                )).done(function (result) {

                    S_MemoDialog.bind(result);

                }).always(function () {

                    App.ui.loading.close(loadingTaget);

                });
            });
        };

        ///**
        // * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
        // */
        //S_MemoDialog.select = function (e) {
        //    var element = S_MemoDialog.element,
        //        button = $(e.target),
        //        tbody = button.closest("tbody"),
        //        id = tbody.attr("data-key"),
        //        data;

        //    if (App.isUndef(id)) {
        //        return;
        //    }

        //    data = S_MemoDialog.data.entry(id);

        //    if (App.isFunc(S_MemoDialog.dataSelected)) {
        //        if (!S_MemoDialog.dataSelected(data)) {
        //            element.modal("hide");
        //        }
        //    }
        //    else {
        //        element.modal("hide");
        //    }

        //};

        /**
         * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
         */
        S_MemoDialog.select = function (e) {
            var element = S_MemoDialog.element;

            data = element.findP("nm_memo").val();

            if (App.isFunc(S_MemoDialog.dataSelected)) {
                if (!S_MemoDialog.dataSelected(data)) {
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
        S_MemoDialog.select = function (e) {
            var element = S_MemoDialog.element,
                data;
            //TODO: 選択された行から起動元画面に返却したい値を抽出します
            var items = element.find(".search-list").find("input:checked[name='select_cd']").map(function (index, item) {
                var tbody = $(item).closest("tbody");
                var id = tbody.attr("data-key");
                var data = S_MemoDialog.data.entry(id);
                //return data.cd_torihiki;
            }).toArray();

            if (items.length == 0) {
                S_MemoDialog.notifyAlert.message(App.messages.base.MS0020).show();
                return;
            }

            if (App.isFunc(S_MemoDialog.dataSelected)) {
                if (!S_MemoDialog.dataSelected(items)) {
                    element.modal("hide");
                }
            }
            else {
                element.modal("hide");
            }
        };

        /**
         * 一覧の行をクリックした際の処理を実行します。（複数セレクト用）
         */
        S_MemoDialog.selectOne = function (e) {

            var target = $(e.target),
                tr = target.closest("tr");

            if (target.is("[name='select_cd']")) {
                return;
            }

            var check = tr.find("[name='select_cd']");
            if (check.is(":checked")) {
                check.prop("checked", false);
            } else {
                check.prop("checked", true);
            }
        };

        /**
         * 一覧のヘッダ部のckeckbox(ALLチェック用）をクリックした際の処理を実行します。（複数セレクト用）
         */
        S_MemoDialog.selectAll = function (e) {

            var $select_cd_all = $(e.target),
                isChecked = $select_cd_all.is(":checked");

            if (isChecked) {
                S_MemoDialog.element.find("[name='select_cd']:visible").prop("checked", true);
            } else {
                S_MemoDialog.element.find("[name='select_cd']:visible").prop("checked", false);
            }
            S_MemoDialog.element.find("[name='select_cd']:visible").change();
        };
--%>
    </script>

    <div class="modal fade wide" tabindex="-1" id="S_MemoDialog">
    <div class="modal-dialog" style="height: 350px; width: 40%">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">メモ入力</h4>
            </div>

            <div class="modal-body">
                <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
                <div class="row">
                    <textarea rows="10" style="resize:none; width: 100%" data-prop="nm_memo">
                    </textarea>
                </div>
                <div class="message-area dialog-slideup-area">
                    <div class="alert-message" style="display: none">
                        <ul>
                        </ul>
                    </div>
                    <div class="info-message" style="display: none">
                        <ul>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <%--複数セレクトの場合は、下の１行をコメント解除してください--%>
                <button type="button" class="btn btn-success select" name="select" >OK</button>
                <button type="button" class="cancel-button btn btn-sm btn-default" data-dismiss="modal" name="close">閉じる</button>
            </div>

        </div>
    </div>
    </div>