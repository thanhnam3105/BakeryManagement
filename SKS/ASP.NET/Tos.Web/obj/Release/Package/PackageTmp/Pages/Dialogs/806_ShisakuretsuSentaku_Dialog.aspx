<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="806_ShisakuretsuSentaku_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs._806_ShisakuretsuSentaku_Dialog" %>
<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>

    <script type="text/javascript">

        /**
         * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
         */
        var _806_ShisakuretsuSentaku_Dialog = {
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.dialogDataTakeCount   // TODO:取得するデータ数を指定します。
            },
            urls: {
                search: "../api/_806_ShisakuretsuSentaku_Dialog"
            }
        };

        /**
         * 検索ダイアログの初期化処理を行います。
         */
        _806_ShisakuretsuSentaku_Dialog.initialize = function () {
            var element = $("#_806_ShisakuretsuSentaku_Dialog");

            element.on("hidden.bs.modal", _806_ShisakuretsuSentaku_Dialog.hidden);
            element.on("shown.bs.modal", _806_ShisakuretsuSentaku_Dialog.shown);
            element.on("click", ".search", _806_ShisakuretsuSentaku_Dialog.search);
            //TODO: 単一セレクトの場合は、下の１行を使用します
            element.on("click", "button.select", _806_ShisakuretsuSentaku_Dialog.select);
            element.on("change", ".search-criteria :input", _806_ShisakuretsuSentaku_Dialog.change);
            _806_ShisakuretsuSentaku_Dialog.element = element;

            element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
            element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

            _806_ShisakuretsuSentaku_Dialog.notifyInfo =
                 App.ui.notify.info(element, {
                     container: "#_806_ShisakuretsuSentaku_Dialog .dialog-slideup-area .info-message",
                     bodyContainer: ".modal-body",
                     show: function () {
                         element.find(".info-message").show();
                     },
                     clear: function () {
                         element.find(".info-message").hide();
                     }
                 });
            _806_ShisakuretsuSentaku_Dialog.notifyAlert =
                App.ui.notify.alert(element, {
                    container: "#_806_ShisakuretsuSentaku_Dialog .dialog-slideup-area .alert-message",
                    bodyContainer: ".modal-body",
                    show: function () {
                        element.find(".alert-message").show();
                    },
                    clear: function () {
                        element.find(".alert-message").hide();
                    }
                });

            _806_ShisakuretsuSentaku_Dialog.validator = element.validation(App.validation(_806_ShisakuretsuSentaku_Dialog.options.validations, {
                success: function (results, state) {
                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        _806_ShisakuretsuSentaku_Dialog.setColValidStyle(item.element);

                        _806_ShisakuretsuSentaku_Dialog.notifyAlert.remove(item.element);
                    }
                },
                fail: function (results, state) {
                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        _806_ShisakuretsuSentaku_Dialog.setColInvalidStyle(item.element);
                        if (state && state.suppressMessage) {
                            continue;
                        }

                        _806_ShisakuretsuSentaku_Dialog.notifyAlert.message(item.message, item.element).show();
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
        _806_ShisakuretsuSentaku_Dialog.setColInvalidStyle = function (target) {
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
        _806_ShisakuretsuSentaku_Dialog.setColValidStyle = function (target) {
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
        _806_ShisakuretsuSentaku_Dialog.hidden = function (e) {
            var element = _806_ShisakuretsuSentaku_Dialog.element,
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
                _806_ShisakuretsuSentaku_Dialog.setColValidStyle(item);
            }

            _806_ShisakuretsuSentaku_Dialog.notifyInfo.clear();
            _806_ShisakuretsuSentaku_Dialog.notifyAlert.clear();
        };

        /**
         * 検索ダイアログ表示時処理を実行します。
         */
        _806_ShisakuretsuSentaku_Dialog.shown = function (e) {

            _806_ShisakuretsuSentaku_Dialog.element.find(":input:not(button):first").focus();
        };

        /**
         * 検索ダイアログ　バリデーションルールを定義します。
         */
        _806_ShisakuretsuSentaku_Dialog.options.validations = {
            cd_shain: {
                rules: {
                    digits: true,
                    maxlength: 10
                },
                options: {
                    name: "試作No-社員コード"
                },
                messages: {
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength
                }
            },
            nen: {
                rules: {
                    digits: true,
                    maxlength: 2
                },
                options: {
                    name: "試作No-年"
                },
                messages: {
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength
                }
            },
            no_oi: {
                rules: {
                    digits: true,
                    maxlength: 3
                },
                options: {
                    name: "試作No-追番"
                },
                messages: {
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_hin: {
                rules: {
                    maxlength: 100
                },
                options: {
                    name: "試作名"
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
        };

        /**
         * 検索ダイアログの検索処理を実行します。
         */
        _806_ShisakuretsuSentaku_Dialog.search = function () {
            var element = _806_ShisakuretsuSentaku_Dialog.element,
                loadingTaget = element.find(".modal-content"),
                table = element.find(".search-list"),
                url = _806_ShisakuretsuSentaku_Dialog.urls.search;

            _806_ShisakuretsuSentaku_Dialog.options.filter = _806_ShisakuretsuSentaku_Dialog.createFilter();

            _806_ShisakuretsuSentaku_Dialog.validator.validate()
            .then(function () {
                table.find("tbody:visible").remove();
                _806_ShisakuretsuSentaku_Dialog.notifyAlert.clear();
                App.ui.loading.show("", loadingTaget);

                $.ajax(App.ajax.webapi.get(url, _806_ShisakuretsuSentaku_Dialog.options.filter))
                .done(function (result) {

                    _806_ShisakuretsuSentaku_Dialog.bind(result);
                }).always(function () {

                    App.ui.loading.close(loadingTaget);
                });
            });
        };

        /**
         * 検索ダイアログの検索条件を組み立てます
         */
        _806_ShisakuretsuSentaku_Dialog.createFilter = function () {
            var criteria = _806_ShisakuretsuSentaku_Dialog.element.find(".search-criteria").form().data();

            return {
                cd_shain: criteria.cd_shain,
                nen: criteria.nen,
                no_oi: criteria.no_oi,
                nm_hin: criteria.nm_hin,
                yoryo_tani: App.settings.app.cd_category.yoryo_tani,
                top: _806_ShisakuretsuSentaku_Dialog.options.top
            }
        };

        /**
         * 検索ダイアログの一覧にデータをバインドします。
         */
        _806_ShisakuretsuSentaku_Dialog.bind = function (data) {
            var element = _806_ShisakuretsuSentaku_Dialog.element,
                table = element.find(".search-list"),
                count = data.Count,
                items = data.Items ? data.Items : data,
                i, l, item, clone;

            element.findP("data_count").text(items.length);
            element.findP("data_count_total").text(count);

            _806_ShisakuretsuSentaku_Dialog.data = App.ui.page.dataSet();
            _806_ShisakuretsuSentaku_Dialog.data.attach(items);

            table.find("tbody:visible").remove();

            for (i = 0, l = items.length; i < l; i++) {
                item = items[i];
                item.no_shisaku = App.common.getFullCdUser(item.cd_shain) + "-"
                                  + App.common.getFullShisakuNen(item.nen) + "-"
                                  + App.common.getFullShisaOiban(item.no_oi);
                clone = table.find(".item-tmpl").clone();
                clone.form().bind(item);
                clone.appendTo(table).removeClass("item-tmpl").show();
            }

            //if (count == 0) {
            //    _806_ShisakuretsuSentaku_Dialog.notifyInfo.remove("806_AP0007");
            //    _806_ShisakuretsuSentaku_Dialog.notifyInfo.message(App.messages.app.AP0007, "806_AP0007").show();
            //}
            if (count && count > App.settings.base.dialogDataTakeCount) {
                _806_ShisakuretsuSentaku_Dialog.notifyInfo.message(App.str.format(App.messages.app.AP0021, count), "806_AP0021").show();
            }
        };

        _806_ShisakuretsuSentaku_Dialog.change = function (e) {
            var target = $(e.target);

            if (target.closest(".shisaku-group").length) { 
                if (!isNaN(Number(target.val()))) {
                    target.val(App.common.fillString(target.val(), target.attr("data-length")));
                }
                target = target.closest(".shisaku-group").find("input");
            }
            _806_ShisakuretsuSentaku_Dialog.validator.validate({
                targets: target
            });
        }

        /**
         * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
         */
        _806_ShisakuretsuSentaku_Dialog.select = function (e) {
            var element = _806_ShisakuretsuSentaku_Dialog.element,
                target = $(e.target),
                id = target.closest("tbody").attr("data-key"),
                data;

            if (App.isUndef(id)) {
                return;
            }

            data = _806_ShisakuretsuSentaku_Dialog.data.entry(id);

            if (App.isFunc(_806_ShisakuretsuSentaku_Dialog.dataSelected)) {
                if (!_806_ShisakuretsuSentaku_Dialog.dataSelected(data)) {
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
        _806_ShisakuretsuSentaku_Dialog.select = function (e) {
            var element = _806_ShisakuretsuSentaku_Dialog.element,
                items;

            //TODO: 選択された行から起動元画面に返却したい値を抽出します
            items = element.find(".search-list").find("input:checked[name='select_cd']").map(function (index, item) {
                var tbody = $(item).closest("tbody"),
                    id = tbody.attr("data-key"),
                    data = _806_ShisakuretsuSentaku_Dialog.data.entry(id);
                return data.cd_torihiki;
            }).toArray();

            if (items.length == 0) {
                _806_ShisakuretsuSentaku_Dialog.notifyAlert.message(App.messages.base.MS0020).show();
                return;
            }

            if (App.isFunc(_806_ShisakuretsuSentaku_Dialog.dataSelected)) {
                if (!_806_ShisakuretsuSentaku_Dialog.dataSelected(items)) {
                    element.modal("hide");
                }
            } else {
                element.modal("hide");
            }
        };

        /**
         * 一覧の行をクリックした際の処理を実行します。（複数セレクト用）
         */
        _806_ShisakuretsuSentaku_Dialog.selectOne = function (e) {
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
        _806_ShisakuretsuSentaku_Dialog.selectAll = function (e) {
            var target = $(e.target);
            _806_ShisakuretsuSentaku_Dialog.element.find("[name='select_cd']:visible").prop("checked", target.is(":checked"));
        };
--%>
    </script>

    <div class="modal fade wide" tabindex="-1" id="_806_ShisakuretsuSentaku_Dialog">
    <div class="modal-dialog" style="height: 350px; width: 850px;">
        <div class="modal-content smaller">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">試作列選択</h4>
            </div>

            <div class="modal-body">
                <div class="search-criteria">
                <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
                    <div class="row">
                        <div class="control-label col-xs-3">
                            <label>試作No</label>
                        </div>
                        <div class="control col-xs-9 shisaku-group">
                            <input type="tel" class="float-left right limit-input-int" data-prop="cd_shain" data-length="10" maxlength="10" style="width: 110px" />
                            <label style="min-width: 10px; padding: 4px 0px;" class="float-left center">-</label>
                            <input type="tel" class="float-left right limit-input-int" data-prop="nen" data-length="2" maxlength="2" style="width: 40px" />
                            <label style="min-width: 10px; padding: 4px 0px;" class="float-left center">-</label>
                            <input type="tel" class="float-left right limit-input-int" data-prop="no_oi" data-length="3" maxlength="3" style="width: 60px" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-label col-xs-3">
                            <label>試作名</label>
                        </div>
                        <div class="control col-xs-6">
                            <input type="text" class="" data-prop="nm_hin" maxlength="100" />
                        </div>
                        <div class="control col-xs-3">
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

                <div style="padding-right: 16px;">
                    <table class="table table-striped table-condensed " style="margin-bottom: 0px;">
                    <!--TODO: ダイアログのヘッダーを定義するHTMLをここに記述します。列幅の合計が100％になるように定義します。-->
                        <thead>
                            <tr>
                                <th style="width: 72px;"></th>
                                <th style="width: 160px;">試作No</th>
                                <th>試作名</th>
                                <!--<th style="width: 80px;">試作列No</th>-->
                                <th style="width: 200px;">サンプル名</th>
                            </tr>
                        </thead>
                    </table>
                </div>
                <div style="height: 252px; overflow: scroll; overflow-x: hidden; margin-top: -2px; border-top: 2px solid #ddd;">
                    <table class="table table-striped table-condensed search-list" style="margin-top: -2px;">
                    <!--TODO: ダイアログの明細行を定義するHTMLをここに記述します。列幅の指定はヘッダーに合わせて定義します。-->
                        <tbody class="item-tmpl" style="display: none;">
                            <tr>
                                <%--単一セレクトの場合は、以下の３行を使用する--%>
                                <td style="width: 72px;">
                                    <button type="button" style="margin: 1px;padding: 1px;" class="btn btn-success btn-xs select">選択</button>
                                </td>
                                <td style="width: 160px;">
                                    <label data-prop="no_shisaku"></label>
                                </td>
                                <td>
                                    <label data-prop="nm_hin" class="overflow-ellipsis"></label>
                                </td>
                                <!--<td style="width: 80px;padding-right: 20px" class="right">
                                    <label data-prop="seq_shisaku" style="min-width: 0px"></label>
                                </td>-->
                                <td style="width: 200px;">
                                    <label data-prop="nm_sample" class="overflow-ellipsis"></label>
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