<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="711_ShisakuCopy_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs._711_ShisakuCopy_Dialog" %>
<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>

    <script type="text/javascript">

        /**
         * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
         */
        var _711_ShisakuCopy_Dialog = {
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.dialogDataTakeCount   // TODO:取得するデータ数を指定します。
            },
            urls: {
                ma_literal: "../Services/ShisaQuickService.svc/ma_literal?$filter=cd_category eq '{0}'&$orderby=cd_literal",
                copy: "../api/_711_ShisakuCopy_Dialog"
            },
            param: {

            }
        };

        /**
         * 検索ダイアログの初期化処理を行います。
         */
        _711_ShisakuCopy_Dialog.initialize = function () {
            var element = $("#_711_ShisakuCopy_Dialog");

            element.on("hidden.bs.modal", _711_ShisakuCopy_Dialog.hidden);
            element.on("shown.bs.modal", _711_ShisakuCopy_Dialog.shown);
            element.on("click", ".copy", _711_ShisakuCopy_Dialog.copy);
            //TODO: 単一セレクトの場合は、下の１行を使用します
            //element.on("click", ".search-list tbody ", _711_ShisakuCopy_Dialog.select);
            //TODO: 複数セレクトの場合は、上の１行を削除し、下の３行をコメント解除します
            //element.on("click", ".select", _711_ShisakuCopy_Dialog.select);
            //element.on("click", ".search-list tbody", _711_ShisakuCopy_Dialog.selectOne);
            //element.find("[name='select_cd_all']").on("click", _711_ShisakuCopy_Dialog.selectAll);
            _711_ShisakuCopy_Dialog.element = element;
            _711_ShisakuCopy_Dialog.loadMasterData();

            element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
            element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

            _711_ShisakuCopy_Dialog.notifyInfo =
                 App.ui.notify.info(element, {
                     container: "#_711_ShisakuCopy_Dialog .dialog-slideup-area .info-message",
                     bodyContainer: ".modal-body",
                     show: function () {
                         element.find(".info-message").show();
                     },
                     clear: function () {
                         element.find(".info-message").hide();
                     }
                 });
            _711_ShisakuCopy_Dialog.notifyAlert =
                App.ui.notify.alert(element, {
                    container: "#_711_ShisakuCopy_Dialog .dialog-slideup-area .alert-message",
                    bodyContainer: ".modal-body",
                    show: function () {
                        element.find(".alert-message").show();
                    },
                    clear: function () {
                        element.find(".alert-message").hide();
                    }
                });

            _711_ShisakuCopy_Dialog.validator = element.validation(App.validation(_711_ShisakuCopy_Dialog.options.validations, {
                success: function (results, state) {
                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        _711_ShisakuCopy_Dialog.setColValidStyle(item.element);

                        _711_ShisakuCopy_Dialog.notifyAlert.remove(item.element);
                    }
                },
                fail: function (results, state) {
                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        _711_ShisakuCopy_Dialog.setColInvalidStyle(item.element);
                        if (state && state.suppressMessage) {
                            continue;
                        }

                        _711_ShisakuCopy_Dialog.notifyAlert.message(item.message, item.element).show();
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
        _711_ShisakuCopy_Dialog.setColInvalidStyle = function (target) {
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
        _711_ShisakuCopy_Dialog.setColValidStyle = function (target) {
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
        _711_ShisakuCopy_Dialog.hidden = function (e) {
            var element = _711_ShisakuCopy_Dialog.element,
                table = element.find(".search-list");

            //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
            element.findP("kotei_pattern").val(element.findP("kotei_pattern").find("option:first").val());
            //TODO: 複数セレクトの場合は、下の１行をコメント解除します。
            //element.find(":checked").prop("checked", false);
            table.find("tbody").not(".item-tmpl").remove();

            element.findP("data_count").text("");
            element.findP("data_count_total").text("");

            var items = element.find(".search-criteria :input:not(button)");
            for (var i = 0; i < items.length; i++) {
                var item = items[i];
                _711_ShisakuCopy_Dialog.setColValidStyle(item);
            }

            _711_ShisakuCopy_Dialog.notifyInfo.clear();
            _711_ShisakuCopy_Dialog.notifyAlert.clear();
            _711_ShisakuCopy_Dialog.element.find("button.copy").prop("disabled", false);
        };

        /**
         * 検索ダイアログ表示時処理を実行します。
         */
        _711_ShisakuCopy_Dialog.shown = function (e) {

            _711_ShisakuCopy_Dialog.element.find(":input:not(button):first").focus();
        };

        /**
         * 検索ダイアログ　バリデーションルールを定義します。
         */
        _711_ShisakuCopy_Dialog.options.validations = {
        };

        /**
         * 検索ダイアログの検索処理を実行します。
         */
        _711_ShisakuCopy_Dialog.copy = function () {
            var element = _711_ShisakuCopy_Dialog.element,
                loadingTaget = element.find(".modal-content"),
                table = element.find(".search-list"),
                query;

            _711_ShisakuCopy_Dialog.options.filter = _711_ShisakuCopy_Dialog.createFilter();

            _711_ShisakuCopy_Dialog.validator.validate()
            .then(function () {
                table.find("tbody:visible").remove();
                _711_ShisakuCopy_Dialog.notifyAlert.clear();
                App.ui.loading.show("", loadingTaget);

                $.ajax(App.ajax.odata.get(_711_ShisakuCopy_Dialog.urls.copy, _711_ShisakuCopy_Dialog.options.filter))
                .done(function (result) {
                    if (result && result.length) {
                        $.each(result, function (ind, err) {
                            if (App.messages.app[err]) {
                                err = App.messages.app[err];
                            }
                            _711_ShisakuCopy_Dialog.notifyAlert.message(err).show();
                        });
                    } else {
                        // Show success message
                        _711_ShisakuCopy_Dialog.notifyInfo.message(App.messages.base.MS0002, "711_MS0002").show();
                        _711_ShisakuCopy_Dialog.element.find("button.copy").prop("disabled", true);
                        setTimeout(function () {
                            _711_ShisakuCopy_Dialog.element.modal("hide");
                        }, 3000)
                    }
                }).fail(function (error) {
                    _711_ShisakuCopy_Dialog.notifyAlert.message(App.ajax.handleError(error).message).show();
                }).always(function () {

                    App.ui.loading.close(loadingTaget);
                });
            });
        };
        /**
         * マスターデータのロード処理を実行します。
         */
        _711_ShisakuCopy_Dialog.loadMasterData = function () {
            return $.ajax(App.ajax.odata.get(App.str.format(_711_ShisakuCopy_Dialog.urls.ma_literal, App.settings.app.cd_category.kotei_patan))).then(function (result) {
                var kotei_pattern = _711_ShisakuCopy_Dialog.element.findP("kotei_pattern");
                kotei_pattern.children().remove();
                App.ui.appendOptions(
                    kotei_pattern,
                    "cd_literal",
                    "nm_literal",
                    result.value

                );
            });
        }

        /**
         * 検索ダイアログの検索条件を組み立てます
         */
        _711_ShisakuCopy_Dialog.createFilter = function () {
            var criteria = _711_ShisakuCopy_Dialog.element.find(".search-criteria").form().data();

            criteria.cd_haigo = _711_ShisakuCopy_Dialog.param.cd_haigo;
            criteria.cd_shain = App.ui.page.user.EmployeeCD;
            criteria.status_densosumi = App.settings.app.kbn_status.densosumi;
            criteria.status_kojokakunin = App.settings.app.kbn_status.kojokakunin;
            criteria.kbnhin_maeshoriGenryo = App.settings.app.kbnHin.maeshoriGenryo;
            criteria.kbnhin_haigo = App.settings.app.kbnHin.haigo;
            criteria.kbnhin_shikakari = App.settings.app.kbnHin.shikakari;

            return criteria;
        };

        /**
         * 検索ダイアログの一覧にデータをバインドします。
         */
        _711_ShisakuCopy_Dialog.bind = function (data) {
            var element = _711_ShisakuCopy_Dialog.element,
                table = element.find(".search-list"),
                count = data["odata.count"],
                items = data.value ? data.value : data,
                i, l, item, clone;

            element.findP("data_count").text(data.value.length);
            element.findP("data_count_total").text(count);

            _711_ShisakuCopy_Dialog.data = App.ui.page.dataSet();
            _711_ShisakuCopy_Dialog.data.attach(items);

            table.find("tbody:visible").remove();

            for (i = 0, l = items.length; i < l; i++) {
                item = items[i];
                clone = table.find(".item-tmpl").clone();
                clone.form().bind(item);
                clone.appendTo(table).removeClass("item-tmpl").show();
            }

            if (count && count > App.settings.base.dialogDataTakeCount) {
                _711_ShisakuCopy_Dialog.notifyInfo.message(App.messages.base.MS0011).show();
            }
        };

        /**
         * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
         */
        _711_ShisakuCopy_Dialog.select = function (e) {
            var element = _711_ShisakuCopy_Dialog.element,
                target = $(e.target),
                id = target.closest("tbody").attr("data-key"),
                data;

            if (App.isUndef(id)) {
                return;
            }

            data = _711_ShisakuCopy_Dialog.data.entry(id);

            if (App.isFunc(_711_ShisakuCopy_Dialog.dataSelected)) {
                if (!_711_ShisakuCopy_Dialog.dataSelected(data)) {
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
        _711_ShisakuCopy_Dialog.select = function (e) {
            var element = _711_ShisakuCopy_Dialog.element,
                items;

            //TODO: 選択された行から起動元画面に返却したい値を抽出します
            items = element.find(".search-list").find("input:checked[name='select_cd']").map(function (index, item) {
                var tbody = $(item).closest("tbody"),
                    id = tbody.attr("data-key"),
                    data = _711_ShisakuCopy_Dialog.data.entry(id);
                return data.cd_torihiki;
            }).toArray();

            if (items.length == 0) {
                _711_ShisakuCopy_Dialog.notifyAlert.message(App.messages.base.MS0020).show();
                return;
            }

            if (App.isFunc(_711_ShisakuCopy_Dialog.dataSelected)) {
                if (!_711_ShisakuCopy_Dialog.dataSelected(items)) {
                    element.modal("hide");
                }
            } else {
                element.modal("hide");
            }
        };

        /**
         * 一覧の行をクリックした際の処理を実行します。（複数セレクト用）
         */
        _711_ShisakuCopy_Dialog.selectOne = function (e) {
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
        _711_ShisakuCopy_Dialog.selectAll = function (e) {
            var target = $(e.target);
            _711_ShisakuCopy_Dialog.element.find("[name='select_cd']:visible").prop("checked", target.is(":checked"));
        };
--%>
    </script>

    <style>
        #_711_ShisakuCopy_Dialog {
            overflow: hidden;
        }
    </style>

    <div class="modal fade wide" tabindex="-1" id="_711_ShisakuCopy_Dialog">
    <div class="modal-dialog" style="height: 350px; width: 700px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">工程パターン選択</h4>
            </div>

            <div class="modal-body" style="min-height: 100px;">
                <div class="search-criteria">
                    <div class="row">
                        <div class="control-label col-xs-3">
                            <label>工程パターン</label>
                        </div>
                        <div class="control col-xs-9">
                            <select data-prop="kotei_pattern"></select>
                        </div>
                    </div>
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
                <button type="button" class="btn btn-sm btn-default copy" style="background-color: #ffc90e;border-color: #ffc90e;color:white;">コピー</button>
            </div>

        </div>
    </div>
    </div>