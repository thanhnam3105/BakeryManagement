<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="S_ShisakuRetuTuikaDialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs.S_ShisakuRetuTuikaDialog" %>
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
        var S_ShisakuRetuTuikaDialog = {
            options: {},
            urls: {}
        };

        /**
         * 検索ダイアログの初期化処理を行います。
         */
        S_ShisakuRetuTuikaDialog.initialize = function () {
            var element = $("#S_ShisakuRetuTuikaDialog");

            element.on("hidden.bs.modal", S_ShisakuRetuTuikaDialog.hidden);
            element.on("click", ".search", S_ShisakuRetuTuikaDialog.search);
            //TODO: 単一セレクトの場合は、下の１行を使用します
            element.on("click", ".search-list tbody ", S_ShisakuRetuTuikaDialog.select);
            //TODO: 複数セレクトの場合は、上の１行を削除し、下の３行をコメント解除します
            //element.on("click", ".select", S_ShisakuRetuTuikaDialog.select);
            //element.on("click", ".search-list tbody", S_ShisakuRetuTuikaDialog.selectOne);
            //element.find("[name='select_cd_all']").on("click", S_ShisakuRetuTuikaDialog.selectAll);
            S_ShisakuRetuTuikaDialog.element = element;

            element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
            element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

            S_ShisakuRetuTuikaDialog.notifyInfo =
                 App.ui.notify.info(element, {
                     container: "#S_ShisakuRetuTuikaDialog .dialog-slideup-area .info-message",
                     messageContainerQuery: "ul",
                     show: function () {
                         element.find(".info-message").show();
                     },
                     clear: function () {
                         element.find(".info-message").hide();
                     }
                 });
            S_ShisakuRetuTuikaDialog.notifyAlert =
                App.ui.notify.alert(element, {
                    container: "#S_ShisakuRetuTuikaDialog .dialog-slideup-area .alert-message",
                    messageContainerQuery: "ul",
                    show: function () {
                        element.find(".alert-message").show();
                    },
                    clear: function () {
                        element.find(".alert-message").hide();
                    }
                });

            S_ShisakuRetuTuikaDialog.validator = element.validation(App.validation(S_ShisakuRetuTuikaDialog.options.validations, {
                success: function (results, state) {
                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        S_ShisakuRetuTuikaDialog.setColValidStyle(item.element);

                        S_ShisakuRetuTuikaDialog.notifyAlert.remove(item.element);
                    }
                },
                fail: function (results, state) {

                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        S_ShisakuRetuTuikaDialog.setColInvalidStyle(item.element);
                        if (state && state.suppressMessage) {
                            continue;
                        }

                        S_ShisakuRetuTuikaDialog.notifyAlert.message(item.message, item.element).show();
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
        S_ShisakuRetuTuikaDialog.setColInvalidStyle = function (target) {
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
        S_ShisakuRetuTuikaDialog.setColValidStyle = function (target) {
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
        S_ShisakuRetuTuikaDialog.hidden = function (e) {

            var element = S_ShisakuRetuTuikaDialog.element,
                table = element.find(".search-list");

            //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
            element.find(":input").val("");
            //TODO: 複数セレクトの場合は、下の１行をコメント解除します。
            //element.find(":checked").prop("checked", false);
            table.find("tbody").not(".item-tmpl").remove();

            element.findP("data_count").text("");
            element.findP("data_count_total").text("");

            var items = element.find(".modal-body :input:not(button)");
            for (var i = 0; i < items.length; i++) {
                var item = items[i];
                S_ShisakuRetuTuikaDialog.setColValidStyle(item);
            }

            S_ShisakuRetuTuikaDialog.notifyInfo.clear();
            S_ShisakuRetuTuikaDialog.notifyAlert.clear();
        };

        /**
         * 検索ダイアログ　バリデーションルールを定義します。
         */
        S_ShisakuRetuTuikaDialog.options.validations = {
        };

        /**
         * 検索ダイアログの一覧にデータをバインドします。
         */
        S_ShisakuRetuTuikaDialog.bind = function (data) {
            var element = S_ShisakuRetuTuikaDialog.element,
                table = element.find(".search-list"),
                count = data["odata.count"],
                items = data.value ? data.value : data,
                i, l, item, clone;

            element.findP("data_count").text(data.value.length);
            element.findP("data_count_total").text(count);

            S_ShisakuRetuTuikaDialog.data = App.ui.page.dataSet();
            S_ShisakuRetuTuikaDialog.data.attach(items);

            table.find("tbody:visible").remove();

            for (i = 0, l = items.length; i < l; i++) {
                item = items[i];
                clone = table.find(".item-tmpl").clone();
                clone.form().bind(item);
                clone.appendTo(table).removeClass("item-tmpl").show();
            }

            if (count && count > App.settings.base.dialogDataTakeCount) {
                S_ShisakuRetuTuikaDialog.notifyInfo.message(App.messages.base.MS0011).show();
            }

        };

        /**
         * 検索ダイアログの検索処理を実行します。
         */
        S_ShisakuRetuTuikaDialog.search = function () {
            var element = S_ShisakuRetuTuikaDialog.element,
                loadingTaget = element.find(".modal-content"),
                criteria = [],
                table = element.find(".search-list"),
                nameCriteria; 

            //TODO: 検索条件を構築する処理をここに記述します。
            //nameCriteria = element.findP("nm_torihiki").val();
            //if (nameCriteria) {
            //    criteria.push("substringof('" + encodeURIComponent(nameCriteria) + "', nm_torihiki) eq true");
            //}

            S_ShisakuRetuTuikaDialog.validator.validate()
            .then(function () {
                table.find("tbody:visible").remove();
                S_ShisakuRetuTuikaDialog.notifyAlert.clear();

                App.ui.loading.show("", loadingTaget);

                //TODO: 検索処理をここに記述します。
                $.ajax(App.ajax.odata.get(S_ShisakuRetuTuikaDialog.urls/* TODO: 検索データ取得サービスの URL */ +
                        "?$top=" + App.settings.base.dialogDataTakeCount + "&$inlinecount=allpages" +
                        (criteria.length ? "&$filter=" + criteria.join(" and ") : "")
                )).done(function (result) {

                    S_ShisakuRetuTuikaDialog.bind(result);

                }).always(function () {

                    App.ui.loading.close(loadingTaget);

                });
            });
        };

        /**
         * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
         */
        S_ShisakuRetuTuikaDialog.select = function (e) {
            var element = S_ShisakuRetuTuikaDialog.element,
                button = $(e.target),
                tbody = button.closest("tbody"),
                id = tbody.attr("data-key"),
                data;

            if (App.isUndef(id)) {
                return;
            }

            data = S_ShisakuRetuTuikaDialog.data.entry(id);

            if (App.isFunc(S_ShisakuRetuTuikaDialog.dataSelected)) {
                if (!S_ShisakuRetuTuikaDialog.dataSelected(data)) {
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
        S_ShisakuRetuTuikaDialog.select = function (e) {
            var element = S_ShisakuRetuTuikaDialog.element,
                data;
            //TODO: 選択された行から起動元画面に返却したい値を抽出します
            var items = element.find(".search-list").find("input:checked[name='select_cd']").map(function (index, item) {
                var tbody = $(item).closest("tbody");
                var id = tbody.attr("data-key");
                var data = S_ShisakuRetuTuikaDialog.data.entry(id);
                //return data.cd_torihiki;
            }).toArray();

            if (items.length == 0) {
                S_ShisakuRetuTuikaDialog.notifyAlert.message(App.messages.base.MS0020).show();
                return;
            }

            if (App.isFunc(S_ShisakuRetuTuikaDialog.dataSelected)) {
                if (!S_ShisakuRetuTuikaDialog.dataSelected(items)) {
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
        S_ShisakuRetuTuikaDialog.selectOne = function (e) {

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
        S_ShisakuRetuTuikaDialog.selectAll = function (e) {

            var $select_cd_all = $(e.target),
                isChecked = $select_cd_all.is(":checked");

            if (isChecked) {
                S_ShisakuRetuTuikaDialog.element.find("[name='select_cd']:visible").prop("checked", true);
            } else {
                S_ShisakuRetuTuikaDialog.element.find("[name='select_cd']:visible").prop("checked", false);
            }
            S_ShisakuRetuTuikaDialog.element.find("[name='select_cd']:visible").change();
        };
--%>
    </script>
    <style>
        #S_ShisakuRetuTuikaDialog table input[type='text'],
        #S_ShisakuRetuTuikaDialog table select {
            margin-bottom: 1px;
            height: 26px;
        }
        #S_ShisakuRetuTuikaDialog input[type='radio'] {
            margin-left: 20px!important;
        }
    </style>
    <div class="modal fade wide" id="S_ShisakuRetuTuikaDialog">
    <div class="modal-dialog" style="height: 350px; width: 800px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">試作列追加</h4>
            </div>

            <div class="modal-body">
                <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
                <div class="row">
                    <div class="col-xs-2 control-label">
                        <label for="SRT-radio1" class="full-width check-group">
                            計算
                            <input type="radio" id="SRT-radio1" name="radio-group1" checked>
                        </label>
                    </div>
                    <div class="col-xs-10 control-label">
                        <label for="SRT-radio2" class="full-width check-group">
                            全工程
                            <input type="radio" id="SRT-radio2" name="radio-group1">
                        </label>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-2 control-label">
                        <label>
                            計算列数
                        </label>
                    </div>
                    <div class="col-xs-10 control">
                        <label>
                            1
                        </label>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-2 control-label">
                        <label>
                            サンプルNo
                        </label>
                    </div>
                    <div class="col-xs-10 control">
                        <div class="float-left">
                            <select data-prop="unknow" style="width: 200px">
                                <option>1:555</option>
                                <option>1:777</option>
                            </select>
                        </div>
                        <div class="float-left">
                            <select data-prop="unknow" style="width: 100px">
                                <option>+</option>
                                <option>-</option>
                                <option>*</option>
                                <option>/</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row" style="height: 300px;">
                    <div class="col-xs-2 control-label full-height">
                        <label style="padding-top: 140px;">工程</label>
                    </div>
                    <div class="col-xs-10 col-zr full-height control">
                        <div style="overflow: scroll; overflow-x: hidden;" class="full-height">
                            <table class="table table-striped table-condensed search-list height-balance">
                                <!--TODO: ダイアログの明細行を定義するHTMLをここに記述します。列幅の指定はヘッダーに合わせて定義します。-->
                                <tbody class="item-tmpl" style="display: none;">
                                    <tr>
                                        <td style="width: 114px;" class="col-zr">
                                            <label>1</label>
                                        </td>
                                        <td style="width: 90px;" class="col-zr">
                                            <select><option>x</option></select>
                                        </td>
                                        <td style="width: 102px; padding: 0px" class="col-zr">
                                            <input type="text" class="number-zone full-height" value="0.0"/>
                                        </td>
                                        <td></td>
                                    </tr>
                                </tbody>
                                <tbody class="item-tmpl">
                                    <tr>
                                        <td style="width: 114px;" class="col-zr">
                                            <label>1</label>
                                        </td>
                                        <td style="width: 90px;" class="col-zr">
                                            <select><option>x</option></select>
                                        </td>
                                        <td style="width: 102px;" class="col-zr">
                                            <input type="text" class="number-zone full-height" value="0.0"/>
                                        </td>
                                        <td></td>
                                    </tr>
                                </tbody>
                                <tbody class="item-tmpl">
                                    <tr>
                                        <td style="width: 114px;" class="col-zr">
                                            <label>1</label>
                                        </td>
                                        <td style="width: 90px;" class="col-zr">
                                            <select><option>x</option></select>
                                        </td>
                                        <td style="width: 102px; padding: 0px" class="col-zr">
                                            <input type="text" class="number-zone full-height" value="0.0"/>
                                        </td>
                                        <td></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
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
                <%--<button type="button" class="btn btn-success select" name="select" >選択</button>--%>
                <button type="button" class="cancel-button btn btn-sm btn-primary">列追加</button>
                <button type="button" class="cancel-button btn btn-sm btn-primary">採用</button>
                <button type="button" class="cancel-button btn btn-sm btn-primary" data-dismiss="modal" name="close">キャンセル</button>
            </div>

        </div>
    </div>
    </div>