<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="803_ExcelHaniSelection_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs._803_ExcelHaniSelection_Dialog" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>


<style>
    #_803_ExcelHaniSelection_Dialog table.datatable th,
    #_803_ExcelHaniSelection_Dialog table.datatable td {
        padding: 2px 6px;
        vertical-align: middle;
    }

        #_803_ExcelHaniSelection_Dialog table.datatable td input {
            margin-top: 0px;
        }

    #_803_ExcelHaniSelection_Dialog table.datatable th {
        border: 1px solid #ccc;
    }

    #_803_ExcelHaniSelection_Dialog table.datatable label {
        min-width: 100%;
    }
</style>

<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var _803_ExcelHaniSelection_Dialog = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.dialogDataTakeCount   // TODO:取得するデータ数を指定します。
        },
        urls: {},
        mapping: App.settings.app.SeihobunshoTabName
    };

    /**
     * 検索ダイアログの初期化処理を行います。
     */
    _803_ExcelHaniSelection_Dialog.initialize = function () {
        var element = $("#_803_ExcelHaniSelection_Dialog");

        element.on("shown.bs.modal", _803_ExcelHaniSelection_Dialog.shown);
        element.on("hidden.bs.modal", _803_ExcelHaniSelection_Dialog.hidden);
        element.on("click", ".select", _803_ExcelHaniSelection_Dialog.select);
        element.on("click", "td input[type='checkbox']", _803_ExcelHaniSelection_Dialog.change);
        element.find(".select-all").on("click", _803_ExcelHaniSelection_Dialog.selectAll);
        _803_ExcelHaniSelection_Dialog.element = element;

        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        _803_ExcelHaniSelection_Dialog.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#_803_ExcelHaniSelection_Dialog .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        _803_ExcelHaniSelection_Dialog.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#_803_ExcelHaniSelection_Dialog .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body",
                show: function () {
                    element.find(".alert-message").show();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                }
            });

        _803_ExcelHaniSelection_Dialog.validator = element.validation(App.validation(_803_ExcelHaniSelection_Dialog.options.validations, {
            success: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _803_ExcelHaniSelection_Dialog.setColValidStyle(item.element);

                    _803_ExcelHaniSelection_Dialog.notifyAlert.remove(item.element);
                }
            },
            fail: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _803_ExcelHaniSelection_Dialog.setColInvalidStyle(item.element);
                    if (state && state.suppressMessage) {
                        continue;
                    }

                    _803_ExcelHaniSelection_Dialog.notifyAlert.message(item.message, item.element).show();
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
    _803_ExcelHaniSelection_Dialog.setColInvalidStyle = function (target) {
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
    _803_ExcelHaniSelection_Dialog.setColValidStyle = function (target) {
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
    _803_ExcelHaniSelection_Dialog.hidden = function (e) {
        var element = _803_ExcelHaniSelection_Dialog.element,
            table = element.find(".search-list");

        //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
        element.find("input[type='checkbox']").prop("checked", false);
        element.find(".select").prop("disabled", true);

        _803_ExcelHaniSelection_Dialog.notifyInfo.clear();
        _803_ExcelHaniSelection_Dialog.notifyAlert.clear();
    };

    /**
     * 検索ダイアログ表示時処理を実行します。
     */
    _803_ExcelHaniSelection_Dialog.shown = function (e) {

        _803_ExcelHaniSelection_Dialog.element.find(".select-all").focus();
    };

    /**
     * 検索ダイアログ　バリデーションルールを定義します。
     */
    _803_ExcelHaniSelection_Dialog.options.validations = {
    };

    /**
     * [コピーする]
     */
    _803_ExcelHaniSelection_Dialog.openSeihoBunsyo = function () {
        var lstTab = _803_ExcelHaniSelection_Dialog.lstTab,
            lstKoteizu = _803_ExcelHaniSelection_Dialog.lstKoteizu;


    }


    /**
     * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
     */
    _803_ExcelHaniSelection_Dialog.select = function (e) {
        var element = _803_ExcelHaniSelection_Dialog.element.find("td input[type='checkbox']:visible:checked"),
            mapping = _803_ExcelHaniSelection_Dialog.mapping,
            resultSet = [];

        // Reset result list
        _803_ExcelHaniSelection_Dialog.lstTab = [];
        // gather selected tabs
        $.each(element, function (ind, checked) {
            checked = $(checked);
            var id = checked.attr("data-prop");
            if (mapping[id]) {
                resultSet.push(mapping[id]);
            }
        });
        // Return copy targets
        _803_ExcelHaniSelection_Dialog.lstTab = resultSet;
        if (_803_ExcelHaniSelection_Dialog.dataSelected) {
            _803_ExcelHaniSelection_Dialog.dataSelected(resultSet);
        }
        _803_ExcelHaniSelection_Dialog.element.modal("hide");
    };

    /// Check all status handler
    _803_ExcelHaniSelection_Dialog.change = function () {
        var element = _803_ExcelHaniSelection_Dialog.element;
        element.find("input.select-all").prop("checked", element.find("td input[type='checkbox']:visible:not(:checked)").length == 0);
        element.find(".select").prop("disabled", element.find("td input[type='checkbox']:visible:checked").length == 0);
    }

    /**
    * 一覧のヘッダ部のckeckbox(ALLチェック用）をクリックした際の処理を実行します。（複数セレクト用）
    */
    _803_ExcelHaniSelection_Dialog.selectAll = function (e) {
        var target = $(e.target);
        _803_ExcelHaniSelection_Dialog.element.find("td input[type='checkbox']:visible").prop("checked", target.is(":checked"));
        _803_ExcelHaniSelection_Dialog.element.find(".select").prop("disabled", !target.is(":checked"));
    };

</script>

<div class="modal fade wide" tabindex="-1" id="_803_ExcelHaniSelection_Dialog">
    <div class="modal-dialog" style="height: 400px; width: 450px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Excel出力範囲の選択</h4>
            </div>

            <div class="modal-body">
                <div>
                    <table class="table datatable">
                        <thead>
                            <tr>
                                <th style="width: 15%;">
                                    <label>
                                        <input type="checkbox" class="select-all" />
                                    </label>
                                </th>
                                <th>Excel出力範囲</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td style="width: 15%; text-align: center;">
                                    <label>
                                        <input type="checkbox" data-prop="Hyoshi" id="Hyoshi_803" />
                                    </label>
                                </td>
                                <td>
                                    <label data-prop="" for="Hyoshi_803">表紙</label>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center;">
                                    <label>
                                        <input type="checkbox" data-prop="YoukiHousou" id="YoukiHousou_803" />
                                    </label>
                                </td>
                                <td>
                                    <label data-prop="" for="YoukiHousou_803">容器包装</label>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center;">
                                    <label>
                                        <input type="checkbox" data-prop="GenryoSetsubi" id="GenryoSetsubi_803" />
                                    </label>
                                </td>
                                <td>
                                    <label data-prop="" for="GenryoSetsubi_803">原料・機械設備・製造方法・表示事項</label>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center;">
                                    <label>
                                        <input type="checkbox" data-prop="HaigoSeizoChuiJiko" id="HaigoSeizoChuiJiko_803" />
                                    </label>
                                </td>
                                <td>
                                    <label data-prop="" for="HaigoSeizoChuiJiko_803">配合・製造上の注意事項</label>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center;">
                                    <label>
                                        <input type="checkbox" data-prop="SeizoKoteizu" id="SeizoKoteizu_803" />
                                    </label>
                                </td>
                                <td>
                                    <label data-prop="" for="SeizoKoteizu_803">製造工程図</label>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center;">
                                    <label>
                                        <input type="checkbox" data-prop="SeihinKikakuan" id="SeihinKikakuan_803" />
                                    </label>
                                </td>
                                <td>
                                    <label data-prop="" for="SeihinKikakuan_803">製品規格案及び取扱基準</label>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center;">
                                    <label>
                                        <input type="checkbox" data-prop="SeihojyoKakuninJiko" id="SeihojyoKakuninJiko_803" />
                                    </label>
                                </td>
                                <td>
                                    <label data-prop="" for="SeihojyoKakuninJiko_803">製法上の確認事項</label>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center;">
                                    <label>
                                        <input type="checkbox" data-prop="ShomikigenSetteiHyo" id="ShomikigenSetteiHyo_803" />
                                    </label>
                                </td>
                                <td>
                                    <label data-prop="" for="ShomikigenSetteiHyo_803">賞味期間設定表</label>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center;">
                                    <label>
                                        <input type="checkbox" data-prop="RirekiHyo" id="RirekiHyo" />
                                    </label>
                                </td>
                                <td>
                                    <label data-prop="" for="RirekiHyo">商品開発履歴表</label>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center;">
                                    <label>
                                        <input type="checkbox" data-prop="HaigoHyo" id="HaigoHyo" />
                                    </label>
                                </td>
                                <td>
                                    <label data-prop="" for="HaigoHyo">配合表</label>
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
                <button type="button" class="btn btn-primary select" name="select" disabled="disabled">OK</button>
                <button type="button" class="cancel-button btn btn-sm btn-default" data-dismiss="modal" name="close">閉じる</button>
            </div>

        </div>
    </div>
</div>
