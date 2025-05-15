<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="_807_RenewalMotoSentaku.aspx.cs" Inherits="Tos.Web.Pages.Dialogs._807_RenewalMotoSentaku" %>
<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>

    <script type="text/javascript">

        /**
         * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
         */
        var _807_RenewalMotoSentaku = {
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.dialogDataTakeCount,   // TODO:取得するデータ数を指定します。
                search_mode: (App.ui.page.user.cd_kengen == App.settings.app.kbn_kengen_bunrui.system_admin ? 1 : 2),
                login_kaisha: App.common.getFullCdKaisha(App.ui.page.user.cd_kaisha)
            },
            urls: {
                search: "../api/_807_RenewalMotoSentaku_Dialog/Get"
            }
        };

        /**
         * 検索ダイアログの初期化処理を行います。
         */
        _807_RenewalMotoSentaku.initialize = function (seihoShuruiData) {
            var element = $("#_807_RenewalMotoSentaku");

            element.on("hidden.bs.modal", _807_RenewalMotoSentaku.hidden);
            element.on("shown.bs.modal", _807_RenewalMotoSentaku.shown);
            element.on("show.bs.modal", _807_RenewalMotoSentaku.show);
            element.on("click", ".search", _807_RenewalMotoSentaku.search);
            //TODO: 単一セレクトの場合は、下の１行を使用します
            //element.on("click", ".search-list tbody ", _807_RenewalMotoSentaku.select);
            //TODO: 複数セレクトの場合は、上の１行を削除し、下の３行をコメント解除します
            element.on("click", ".select", _807_RenewalMotoSentaku.select);
            element.on("click", ".remove-renewal", _807_RenewalMotoSentaku.removeSelect);
            //element.on("click", ".search-list tbody", _807_RenewalMotoSentaku.selectOne);
            //element.find("[name='select_cd_all']").on("click", _807_RenewalMotoSentaku.selectAll);
            element.on("change", ".search-criteria :input", _807_RenewalMotoSentaku.change);
            _807_RenewalMotoSentaku.element = element;
            // Load combobox seiho shurui
            var no_seiho_shurui = element.findP("no_seiho_shurui");
            no_seiho_shurui.children().remove();
            App.ui.appendOptions(
                no_seiho_shurui,
                "no_seiho_shurui",
                "no_seiho_shurui",
                seihoShuruiData,
                true
            );

            element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
            element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

            _807_RenewalMotoSentaku.notifyInfo =
                 App.ui.notify.info(element, {
                     container: "#_807_RenewalMotoSentaku .dialog-slideup-area .info-message",
                     bodyContainer: ".modal-body",
                     show: function () {
                         element.find(".info-message").show();
                     },
                     clear: function () {
                         element.find(".info-message").hide();
                     }
                 });
            _807_RenewalMotoSentaku.notifyAlert =
                App.ui.notify.alert(element, {
                    container: "#_807_RenewalMotoSentaku .dialog-slideup-area .alert-message",
                    bodyContainer: ".modal-body",
                    show: function () {
                        element.find(".alert-message").show();
                    },
                    clear: function () {
                        element.find(".alert-message").hide();
                    }
                });

            _807_RenewalMotoSentaku.validator = element.validation(App.validation(_807_RenewalMotoSentaku.options.validations, {
                success: function (results, state) {
                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        _807_RenewalMotoSentaku.setColValidStyle(item.element);

                        _807_RenewalMotoSentaku.notifyAlert.remove(item.element);
                    }
                },
                fail: function (results, state) {
                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        _807_RenewalMotoSentaku.setColInvalidStyle(item.element);
                        if (state && state.suppressMessage) {
                            continue;
                        }

                        _807_RenewalMotoSentaku.notifyAlert.message(item.message, item.element).show();
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
        _807_RenewalMotoSentaku.setColInvalidStyle = function (target) {
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
        _807_RenewalMotoSentaku.setColValidStyle = function (target) {
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
        _807_RenewalMotoSentaku.hidden = function (e) {
            var element = _807_RenewalMotoSentaku.element,
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
                _807_RenewalMotoSentaku.setColValidStyle(item);
            }

            _807_RenewalMotoSentaku.notifyInfo.clear();
            _807_RenewalMotoSentaku.notifyAlert.clear();
        };

        /**
         * 検索ダイアログ表示時処理を実行します。
         */
        _807_RenewalMotoSentaku.shown = function (e) {

            _807_RenewalMotoSentaku.element.find(":input:not(button):first").focus();
        };

        /**
        * Reset default values
        */
        _807_RenewalMotoSentaku.show = function () {
            _807_RenewalMotoSentaku.element.findP("no_seiho_kaisha").val(App.ui.page.user.cd_kaisha).change();
            // Enable controls when kbn_hin = 3 and disabled for else
            if (_807_RenewalMotoSentaku.options.kbn_hin == App.settings.app.kbn_hin.kbn_hin_3) {
                _807_RenewalMotoSentaku.element.find(":input:not(button)").prop("disabled", false);
                _807_RenewalMotoSentaku.element.find("button.search").prop("disabled", false);
            } else {
                _807_RenewalMotoSentaku.element.find(":input:not(button)").prop("disabled", true);
                _807_RenewalMotoSentaku.element.find("button.search").prop("disabled", true);
            }
        }

        /**
         * 検索ダイアログ　バリデーションルールを定義します。
         */
        _807_RenewalMotoSentaku.options.validations = {
            no_seiho_kaisha: {
                rules: {
                    digits: true,
                    maxlength: 4
                },
                options: {
                    name: "製法番号（会社コード）"
                },
                messages: {
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength
                }
            },
            no_seiho_nen: {
                rules: {
                    digits: true,
                    maxlength: 2
                },
                options: {
                    name: "製法番号（年）"
                },
                messages: {
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength
                }
            },
            no_seiho_renban: {
                rules: {
                    digits: true,
                    maxlength: 4
                },
                options: {
                    name: "製法番号（シーケンス番号）"
                },
                messages: {
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_seiho: {
                rules: {
                    maxbytelength: 120
                },
                options: {
                    name: "製法名",
                    byte: 60
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            cd_haigo: {
                rules: {
                    digits: true,
                    maxlength: 6
                },
                options: {
                    name: "製品コード"
                },
                messages: {
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_haigo: {
                rules: {
                    maxbytelength: 60
                },
                options: {
                    name: "製品名",
                    byte: 30
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            }
        };

        /**
         * Search condition on change
         */
        _807_RenewalMotoSentaku.change = function (e) {
            var target = $(e.target),
                property = target.attr("data-prop");
            switch (property) {
                case "no_seiho_kaisha":
                    target.val(App.common.getFullCdKaisha(target.val()));
                    break;
                case "no_seiho_nen":
                    target.val(App.common.getFullSeihoNen(target.val()));
                    break;
                case "no_seiho_renban":
                    target.val(App.common.getFullSeihoRenban(target.val()));
                    break;
                case "cd_haigo":
                    target.val(App.common.getFullString(target.val(), "000000"));
                    break;
            }

            _807_RenewalMotoSentaku.validator.validate({
                targets: target
            });
        }

        /**
         * 検索ダイアログの検索処理を実行します。
         */
        _807_RenewalMotoSentaku.search = function () {
            var element = _807_RenewalMotoSentaku.element,
                loadingTaget = element.find(".modal-content"),
                table = element.find(".search-list"),
                query;

            _807_RenewalMotoSentaku.options.filter = _807_RenewalMotoSentaku.createFilter();

            _807_RenewalMotoSentaku.validator.validate()
            .then(function () {
                table.find("tbody:visible").remove();
                _807_RenewalMotoSentaku.notifyAlert.clear();
                App.ui.loading.show("", loadingTaget);

                $.ajax(App.ajax.webapi.get(_807_RenewalMotoSentaku.urls.search, _807_RenewalMotoSentaku.options.filter))
                .done(function (result) {

                    _807_RenewalMotoSentaku.bind(result);
                }).always(function () {

                    App.ui.loading.close(loadingTaget);
                });
            });
        };

        /**
         * 検索ダイアログの検索条件を組み立てます
         */
        _807_RenewalMotoSentaku.createFilter = function () {
            var criteria = _807_RenewalMotoSentaku.element.find(".search-criteria").form().data(),
                element = _807_RenewalMotoSentaku.element;
            return {
                no_seiho_kaisha: criteria.no_seiho_kaisha,
                no_seiho_shurui: (element.findP("no_seiho_shurui").val() ? element.findP("no_seiho_shurui").val() : null),
                no_seiho_nen: criteria.no_seiho_nen,
                no_seiho_renban: criteria.no_seiho_renban,
                nm_seiho: criteria.nm_seiho,
                cd_haigo: criteria.cd_haigo,
                nm_haigo: criteria.nm_haigo,
                mode: _807_RenewalMotoSentaku.options.search_mode,
                login_kaisha: _807_RenewalMotoSentaku.options.login_kaisha,
                top: _807_RenewalMotoSentaku.options.top
            }
        };

        /**
         * 検索ダイアログの一覧にデータをバインドします。
         */
        _807_RenewalMotoSentaku.bind = function (data) {
            var element = _807_RenewalMotoSentaku.element,
                table = element.find(".search-list"),
                count = data.Count,
                items = data.Items ? data.Items : data,
                i, l, item, clone;

            element.findP("data_count").text(items.length);
            element.findP("data_count_total").text(count);

            _807_RenewalMotoSentaku.data = App.ui.page.dataSet();
            _807_RenewalMotoSentaku.data.attach(items);

            table.find("tbody:visible").remove();

            for (i = 0, l = items.length; i < l; i++) {
                item = items[i];
                clone = table.find(".item-tmpl").clone();
                clone.form().bind(item);
                clone.appendTo(table).removeClass("item-tmpl").show();
            }

            if (count && count > App.settings.base.dialogDataTakeCount) {
                _807_RenewalMotoSentaku.notifyInfo.message(App.str.format(App.messages.app.AP0021, count), "807_AP0021").show();
            }
        };

        /**
         * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
         */
        _807_RenewalMotoSentaku.select = function (e) {
            var element = _807_RenewalMotoSentaku.element,
                target = $(e.target),
                id = target.closest("tbody").attr("data-key"),
                data;

            if (App.isUndef(id)) {
                return;
            }

            data = _807_RenewalMotoSentaku.data.entry(id);
            data = {
                no_seiho: data.no_seiho,
                cd_haigo: data.cd_haigo,
                flg_renewal: 1
            }

            if (App.isFunc(_807_RenewalMotoSentaku.dataSelected)) {
                if (!_807_RenewalMotoSentaku.dataSelected(data)) {
                    element.modal("hide");
                }
            } else {
                element.modal("hide");
            }
        };

        /**
         * リニューアル解除 on click
         */
        _807_RenewalMotoSentaku.removeSelect = function (e) {
            var element = _807_RenewalMotoSentaku.element;
            page.dialogs.confirmDialog.confirm({
                text: App.messages.app.AP0167
            }).then(function () {
                if (App.isFunc(_807_RenewalMotoSentaku.dataSelected)) {
                    _807_RenewalMotoSentaku.dataSelected({
                        no_seiho: null,
                        cd_haigo: null,
                        flg_renewal: 0
                    })
                }
                element.modal("hide");
            });
        }

        //TODO: 複数セレクトを使用する場合は、上の単一セレクト用select関数を削除し、下の複数セレクト用の関数をコメント解除します
        //TODO: 単一セレクトの場合は、不要なコメント部分は削除してください
<%--
        /**
         * 一覧から行を選択された際の処理を実行します。（複数セレクト用）
         */
        _807_RenewalMotoSentaku.select = function (e) {
            var element = _807_RenewalMotoSentaku.element,
                items;

            //TODO: 選択された行から起動元画面に返却したい値を抽出します
            items = element.find(".search-list").find("input:checked[name='select_cd']").map(function (index, item) {
                var tbody = $(item).closest("tbody"),
                    id = tbody.attr("data-key"),
                    data = _807_RenewalMotoSentaku.data.entry(id);
                return data.cd_torihiki;
            }).toArray();

            if (items.length == 0) {
                _807_RenewalMotoSentaku.notifyAlert.message(App.messages.base.MS0020).show();
                return;
            }

            if (App.isFunc(_807_RenewalMotoSentaku.dataSelected)) {
                if (!_807_RenewalMotoSentaku.dataSelected(items)) {
                    element.modal("hide");
                }
            } else {
                element.modal("hide");
            }
        };

        /**
         * 一覧の行をクリックした際の処理を実行します。（複数セレクト用）
         */
        _807_RenewalMotoSentaku.selectOne = function (e) {
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
        _807_RenewalMotoSentaku.selectAll = function (e) {
            var target = $(e.target);
            _807_RenewalMotoSentaku.element.find("[name='select_cd']:visible").prop("checked", target.is(":checked"));
        };
--%>
    </script>

    <div class="modal fade wide" tabindex="-1" id="_807_RenewalMotoSentaku" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog" style="height: 350px; width: 900px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">製法番号選択（リニューアル元選択）</h4>
            </div>

            <div class="modal-body">
                <div class="search-criteria">
                <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
                    <div class="row">
                        <div class="control-label col-xs-2">
                            <label>製法番号</label>
                        </div>
                        <div class="control col-xs-10">
                            <%--<label>製法番号<span style="color: red">*</span></label>--%>
                            <input type="tel" data-prop="no_seiho_kaisha" maxlength="4" class="limit-input-int no-seiho-group" style="width:45px;ime-mode:disabled"/>
                            <span style="width:5px;">-</span>
                            <select class="number no-seiho-group" data-prop="no_seiho_shurui" style="width:60px;"></select>
                            <span>-</span>
                            <input type="tel" maxlength="2" data-prop="no_seiho_nen" class="limit-input-int no-seiho-group" style="width:30px;ime-mode:disabled;"/>
                            <span>-</span>
                            <input type="tel" class="limit-input-int" data-prop="no_seiho_renban" style="width:45px;" maxlength="4" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-label col-xs-2">
                            <label>製法名</label>
                        </div>
                        <div class="control col-xs-8 with-next-col">
                            <input type="text" data-prop="nm_seiho" class="ime-active"/>
                        </div>
                        <div class="control col-xs-2">
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-label col-xs-2">
                            <label>製品コード</label>
                        </div>
                        <div class="control col-xs-10">
                            <input type="tel" data-prop="cd_haigo" class="limit-input-int" style="width: 80px;" maxlength="6" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-label col-xs-2">
                            <label>製品名</label>
                        </div>
                        <div class="control col-xs-8 with-next-col">
                            <input type="text" data-prop="nm_haigo" class="ime-active"/>
                        </div>
                        <div class="control col-xs-2">
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
                        <thead>
                            <tr>
                                <th style="width: 70px;"></th>
                                <th style="width: 140px;">製法番号</th>
                                <th style="width: 240px;">製法名</th>
                                <th style="width: 80px;">配合コード</th>
                                <th style="">配合名</th>
                                <th style="width: 50px;">品区分</th>
                            </tr>
                        </thead>
                    </table>
                </div>
                <div style="height: 252px; overflow: scroll; overflow-x: hidden;">
                    <table class="table table-striped table-condensed search-list">
                        <tbody class="item-tmpl" style="display: none;">
                            <tr>
                                <td style="width: 70px;">
                                    <button type="button" style="margin: 1px;padding: 1px;" class="btn btn-success btn-xs select">選択</button>
                                </td>
                                <td style="width: 140px;">
                                    <label data-prop="no_seiho"></label>
                                </td>
                                <td style="width: 240px;">
                                    <label data-prop="nm_seiho" class="overflow-ellipsis"></label>
                                </td>
                                <td style="width: 80px;">
                                    <label data-prop="cd_haigo" style="min-width: 0px;"></label>
                                </td>
                                <td style="">
                                    <label data-prop="nm_haigo" class="overflow-ellipsis"></label>
                                </td>
                                <td style="width: 50px; text-align: center;">
                                    <label data-prop="kbn_hin_literal" style="min-width: 0px;"></label>
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
                <button type="button" class="btn btn-success remove-renewal" name="select" >リニューアル解除</button>
                <button type="button" class="cancel-button btn btn-sm btn-default" data-dismiss="modal" name="close">閉じる</button>
            </div>

        </div>
    </div>
    </div>