<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="802_CopyHaniSelection_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs._802_CopyHaniSelection_Dialog" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>


<style>
    #_802_CopyHaniSelection_Dialog table.datatable th,
    #_802_CopyHaniSelection_Dialog table.datatable td {
        padding: 2px 6px;
        vertical-align: middle;
    }

        #_802_CopyHaniSelection_Dialog table.datatable td input {
            margin-top: 0px;
        }

    #_802_CopyHaniSelection_Dialog table.datatable th {
        border: 1px solid #ccc;
    }

    #_802_CopyHaniSelection_Dialog table.datatable label {
        min-width: 100%;
    }
</style>

<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    /// All dialogs will be loaded in 'Main page' 
    /// This dialog will call the method to active or some things else without 'Loading child dialogs'
    var _802_CopyHaniSelection_Dialog = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.dialogDataTakeCount   // TODO:取得するデータ数を指定します。
        },
        urls: {},
        mapping: App.settings.app.SeihobunshoTabName,
        param: {
            no_seiho: "",
            no_seiho_sakusei: "",
            no_seiho_copy: "",
            openerID: "",
            mode: ""
        }
    };

    /**
     * 検索ダイアログの初期化処理を行います。
     */
    _802_CopyHaniSelection_Dialog.initialize = function () {
        var element = $("#_802_CopyHaniSelection_Dialog");

        element.on("shown.bs.modal", _802_CopyHaniSelection_Dialog.shown);
        element.on("show.bs.modal", _802_CopyHaniSelection_Dialog.show);
        element.on("hidden.bs.modal", _802_CopyHaniSelection_Dialog.hidden);
        
        element.on("click", ".select", _802_CopyHaniSelection_Dialog.select);
        element.on("click", "td input[type='checkbox']", _802_CopyHaniSelection_Dialog.change);
        element.find(".select-all").on("click", _802_CopyHaniSelection_Dialog.selectAll);
        _802_CopyHaniSelection_Dialog.element = element;

        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        _802_CopyHaniSelection_Dialog.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#_802_CopyHaniSelection_Dialog .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        _802_CopyHaniSelection_Dialog.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#_802_CopyHaniSelection_Dialog .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body",
                show: function () {
                    element.find(".alert-message").show();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                }
            });

        _802_CopyHaniSelection_Dialog.validator = element.validation(App.validation(_802_CopyHaniSelection_Dialog.options.validations, {
            success: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _802_CopyHaniSelection_Dialog.setColValidStyle(item.element);

                    _802_CopyHaniSelection_Dialog.notifyAlert.remove(item.element);
                }
            },
            fail: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _802_CopyHaniSelection_Dialog.setColInvalidStyle(item.element);
                    if (state && state.suppressMessage) {
                        continue;
                    }

                    _802_CopyHaniSelection_Dialog.notifyAlert.message(item.message, item.element).show();
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
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    _802_CopyHaniSelection_Dialog.setColInvalidStyle = function (target) {
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
    _802_CopyHaniSelection_Dialog.setColValidStyle = function (target) {
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
    _802_CopyHaniSelection_Dialog.hidden = function (e) {
        var element = _802_CopyHaniSelection_Dialog.element,
            table = element.find(".search-list");

        //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
        element.find("input[type='checkbox']").prop("checked", false);
        element.find(".select").prop("disabled", true);

        _802_CopyHaniSelection_Dialog.notifyInfo.clear();
        _802_CopyHaniSelection_Dialog.notifyAlert.clear();
    };

    /**
     * 検索ダイアログ表示時処理を実行します。
     */
    _802_CopyHaniSelection_Dialog.shown = function (e) {

        _802_CopyHaniSelection_Dialog.element.find(":input:not(button):first").focus();
    };


    /**
     * Load dialog 805 if inneeded
     */
    _802_CopyHaniSelection_Dialog.show = function () {
        if (typeof _805_ShikakarihinSentaku_Dialog === "undefined") {
            _802_CopyHaniSelection_Dialog.isLoad805 = true;
            $.get("Dialogs/805_ShikakarihinSentaku_Dialog.aspx").then(function (result) {
                $("#dialog-container").append(result);
                _805_ShikakarihinSentaku_Dialog.initialize();
                if (_802_CopyHaniSelection_Dialog.isLoad805) {
                    _802_CopyHaniSelection_Dialog.isLoad805 = false;
                    App.ui.loading.close();
                }
            });
        }

    }

    /**
     * 検索ダイアログ　バリデーションルールを定義します。
     */
    _802_CopyHaniSelection_Dialog.options.validations = {
    };

    /**
     * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
     */
    _802_CopyHaniSelection_Dialog.select = function (e) {
        var element = _802_CopyHaniSelection_Dialog.element.find("td input[type='checkbox']:visible:checked"),
            mapping = _802_CopyHaniSelection_Dialog.mapping,
            param = _802_CopyHaniSelection_Dialog.param,
            waitingHaigoMapping = false,
            resultSet = [];

        // Reset result list
        _802_CopyHaniSelection_Dialog.lstTab = "";
        _802_CopyHaniSelection_Dialog.lstKoteizu = "";
        // gather selected tabs
        $.each(element, function (ind, checked) {
            checked = $(checked);
            var id = checked.attr("id");
            if (mapping[id]) {
                resultSet.push(mapping[id]);
            }
            // gather selected koteizu
            if (id == App.settings.app.SeihobunshoTabName.SeizoKoteizu) {
                if (_802_CopyHaniSelection_Dialog.isLoad805) {
                    App.ui.loading.show();
                    return;
                }
                // active dialog 803
                waitingHaigoMapping = true;
                _805_ShikakarihinSentaku_Dialog.dataSelected = _802_CopyHaniSelection_Dialog.selectHaigoMapping;
                _805_ShikakarihinSentaku_Dialog.param.no_seiho_sakusei = param.no_seiho_sakusei;
                _805_ShikakarihinSentaku_Dialog.param.no_seiho_copy = param.no_seiho_copy;
                _805_ShikakarihinSentaku_Dialog.element.modal("show");
            }
        });
        // Return copy targets
        _802_CopyHaniSelection_Dialog.lstTab = resultSet;
        if (!waitingHaigoMapping) {
            _802_CopyHaniSelection_Dialog.returnData();
        }
    };

    _802_CopyHaniSelection_Dialog.selectHaigoMapping = function (mapping) {
        _802_CopyHaniSelection_Dialog.lstKoteizu = mapping;
        _802_CopyHaniSelection_Dialog.returnData();
    }

    /**
    * Return copy target or open new seihobunsho
    */
    _802_CopyHaniSelection_Dialog.returnData = function () {
        var param = _802_CopyHaniSelection_Dialog.param,
            lstTab = _802_CopyHaniSelection_Dialog.lstTab || [],
            lstKoteizu = _802_CopyHaniSelection_Dialog.lstKoteizu || [],
            targetMode = App.settings.app.m_seiho_bunsho.undef;

        // Find parent window
        // Find the parent mode
        var target = window;
        while (target.opener) {
            target = target.opener;
            if (target.page && target.page.values.isWaitingForCopy300) {
                targetMode = target.page.param.mode;
                target.close();
                //window.open("", target.name);
                //target.page.activeCopy(lstTab, lstKoteizu, param.no_seiho_copy);
                //window.close();
                //target.page.closeTabs();
                //return;
            }
        }
        // The parent page was not found
        if (targetMode == App.settings.app.m_seiho_bunsho.undef && page.shortcutParam.seihoBunshoMode) {
            targetMode = page.shortcutParam.seihoBunshoMode;
        }
        // If cannot retrive the parent mode -> system will use the undef mode (new or edit)
        App.common.openSeihoBunsho({
            no_seiho: param.no_seiho_sakusei,
            no_seiho_sakusei: param.no_seiho_sakusei,
            no_seiho_copy: param.no_seiho_copy,
            haigo_copy_mapping: lstKoteizu,
            mode: targetMode,
            no_Tab: lstTab,
            openerID: param.openerID
        });
        if (_802_CopyHaniSelection_Dialog.param.id_gamen == 300) {
            window.close();
        } else {
            _802_CopyHaniSelection_Dialog.element.modal("hide");
        }
    }

    /// Check all status handler
    _802_CopyHaniSelection_Dialog.change = function () {
        var element = _802_CopyHaniSelection_Dialog.element;
        element.find("input.select-all").prop("checked", element.find("td input[type='checkbox']:visible:not(:checked)").length == 0);
        element.find(".select").prop("disabled", element.find("td input[type='checkbox']:visible:checked").length == 0);
    }

    /**
    * 一覧のヘッダ部のckeckbox(ALLチェック用）をクリックした際の処理を実行します。（複数セレクト用）
    */
    _802_CopyHaniSelection_Dialog.selectAll = function (e) {
        var target = $(e.target);
        _802_CopyHaniSelection_Dialog.element.find("td input[type='checkbox']:visible").prop("checked", target.is(":checked"));
        _802_CopyHaniSelection_Dialog.element.find(".select").prop("disabled", !target.is(":checked"));
    };

</script>

<div class="modal fade wide" tabindex="-1" id="_802_CopyHaniSelection_Dialog">
    <div class="modal-dialog" style="height: 400px; width: 450px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">コピー範囲の選択</h4>
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
                                <th>製法文書</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td style="width: 15%; text-align: center;">
                                    <label>
                                        <input type="checkbox" data-prop="Hyoshi" id="Hyoshi" />
                                    </label>
                                </td>
                                <td>
                                    <label data-prop="" for="Hyoshi">表紙</label>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center;">
                                    <label>
                                        <input type="checkbox" data-prop="YoukiHousou" id="YoukiHousou" />
                                    </label>
                                </td>
                                <td>
                                    <label data-prop="" for="YoukiHousou">容器包装</label>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center;">
                                    <label>
                                        <input type="checkbox" data-prop="GenryoSetsubi" id="GenryoSetsubi" />
                                    </label>
                                </td>
                                <td>
                                    <label data-prop="" for="GenryoSetsubi">原料・機械設備・製造方法・表示事項</label>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center;">
                                    <label>
                                        <input type="checkbox" data-prop="HaigoSeizoChuiJiko" id="HaigoSeizoChuiJiko" />
                                    </label>
                                </td>
                                <td>
                                    <label data-prop="" for="HaigoSeizoChuiJiko">配合・製造上の注意事項</label>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center;">
                                    <label>
                                        <input type="checkbox" data-prop="SeizoKoteizu" id="SeizoKoteizu" />
                                    </label>
                                </td>
                                <td>
                                    <label data-prop="" for="SeizoKoteizu">製造工程図</label>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center;">
                                    <label>
                                        <input type="checkbox" data-prop="SeihinKikakuan" id="SeihinKikakuan" />
                                    </label>
                                </td>
                                <td>
                                    <label data-prop="" for="SeihinKikakuan">製品規格案及び取扱基準</label>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center;">
                                    <label>
                                        <input type="checkbox" data-prop="SeihojyoKakuninJiko" id="SeihojyoKakuninJiko" />
                                    </label>
                                </td>
                                <td>
                                    <label data-prop="" for="SeihojyoKakuninJiko">製法上の確認事項</label>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center;">
                                    <label>
                                        <input type="checkbox" data-prop="ShomikigenSetteiHyo" id="ShomikigenSetteiHyo" />
                                    </label>
                                </td>
                                <td>
                                    <label data-prop="" for="ShomikigenSetteiHyo">賞味期間設定表</label>
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
                <button type="button" class="btn btn-primary select" name="select" disabled="disabled">コピー</button>
                <button type="button" class="cancel-button btn btn-sm btn-default" data-dismiss="modal" name="close">閉じる</button>
            </div>

        </div>
    </div>
</div>
