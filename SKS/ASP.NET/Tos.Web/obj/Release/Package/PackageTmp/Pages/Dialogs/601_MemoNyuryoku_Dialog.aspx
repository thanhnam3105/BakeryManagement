<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="601_MemoNyuryoku_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs._601_MemoNyuryoku_Dialog" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>
<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var _601_MemoNyuryoku_Dialog = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.dialogDataTakeCount   // TODO:取得するデータ数を指定します。
        },
        urls: {},
        values: {
            memo: "",
            isChange: false
        }
    };


    /**
     * 検索ダイアログの初期化処理を行います。
     */
    _601_MemoNyuryoku_Dialog.initialize = function () {
        var element = $("#_601_MemoNyuryoku_Dialog");

        element.on("hidden.bs.modal", _601_MemoNyuryoku_Dialog.hidden);
        element.on("shown.bs.modal", _601_MemoNyuryoku_Dialog.shown);
        element.on("show.bs.modal", _601_MemoNyuryoku_Dialog.show);
        //element.on("click", ".search", _601_MemoNyuryoku_Dialog.search);
        element.find('.modal-body').on("change", ":input", _601_MemoNyuryoku_Dialog.change);
        //TODO: 単一セレクトの場合は、下の１行を使用します
        //element.on("click", ".search-list tbody ", _601_MemoNyuryoku_Dialog.select);
        //TODO: 複数セレクトの場合は、上の１行を削除し、下の３行をコメント解除します
        element.on("click", ".select", _601_MemoNyuryoku_Dialog.select);
        //element.on("click", ".search-list tbody", _601_MemoNyuryoku_Dialog.selectOne);
        //element.find("[name='select_cd_all']").on("click", _601_MemoNyuryoku_Dialog.selectAll);
        _601_MemoNyuryoku_Dialog.element = element;

        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        _601_MemoNyuryoku_Dialog.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#_601_MemoNyuryoku_Dialog .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        _601_MemoNyuryoku_Dialog.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#_601_MemoNyuryoku_Dialog .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body",
                show: function () {
                    element.find(".alert-message").show();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                }
            });

        _601_MemoNyuryoku_Dialog.validator = element.validation(App.validation(_601_MemoNyuryoku_Dialog.options.validations, {
            success: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _601_MemoNyuryoku_Dialog.setColValidStyle(item.element);

                    _601_MemoNyuryoku_Dialog.notifyAlert.remove(item.element);
                }
            },
            fail: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;
                for (; i < l; i++) {
                    item = results[i];
                    _601_MemoNyuryoku_Dialog.setColInvalidStyle(item.element);
                    if (state && state.suppressMessage) {
                        continue;
                    }

                    _601_MemoNyuryoku_Dialog.notifyAlert.message(item.message, item.element).show();
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
    _601_MemoNyuryoku_Dialog.setColInvalidStyle = function (target) {
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
    _601_MemoNyuryoku_Dialog.setColValidStyle = function (target) {
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
    _601_MemoNyuryoku_Dialog.hidden = function (e) {
        var element = _601_MemoNyuryoku_Dialog.element,
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
            _601_MemoNyuryoku_Dialog.setColValidStyle(item);
        }
        _601_MemoNyuryoku_Dialog.setColValidStyle(element.findP("nm_memo"));

        _601_MemoNyuryoku_Dialog.notifyInfo.clear();
        _601_MemoNyuryoku_Dialog.notifyAlert.clear();
    };

    _601_MemoNyuryoku_Dialog.show = function () {
        var element = _601_MemoNyuryoku_Dialog.element,
            target = _601_MemoNyuryoku_Dialog.element.findP("nm_memo");

        _601_MemoNyuryoku_Dialog.element.find(".modal-dialog").css("top", 182);
        _601_MemoNyuryoku_Dialog.element.find(".modal-dialog").css("left", -31);
        target.val(_601_MemoNyuryoku_Dialog.values.memo);

        _601_MemoNyuryoku_Dialog.values.isChange = false;
        //_601_MemoNyuryoku_Dialog.validator.validate({
        //    targets: target
        //});
    }
    /**
     * 検索ダイアログ表示時処理を実行します。
     */
    _601_MemoNyuryoku_Dialog.shown = function (e) {
        var element = _601_MemoNyuryoku_Dialog.element,
            target = _601_MemoNyuryoku_Dialog.element.findP("nm_memo");

        setTimeout(function () {
            _601_MemoNyuryoku_Dialog.element.findP("nm_memo").focus();
        }, 200);

    };

    /**
     * 検索ダイアログ　バリデーションルールを定義します。
     */
    _601_MemoNyuryoku_Dialog.options.validations = {
        nm_memo: {
            rules: {
                check_single_kotations: true,
                maxlength: 50
            },
            options: {
                name: "メモ"
            },
            messages: {
                check_single_kotations: App.messages.app.AP0153,
                maxlength: App.messages.base.maxlength
            }
        }
    };


    _601_MemoNyuryoku_Dialog.change = function (e) {
        var element = _601_MemoNyuryoku_Dialog.element,
            target = $(e.target);

        _601_MemoNyuryoku_Dialog.validator.validate({
            targets: target
        }).then(function () {
            _601_MemoNyuryoku_Dialog.values.isChange = true;
        });

    }
    /**
     * 検索ダイアログの検索処理を実行します。
     */
    _601_MemoNyuryoku_Dialog.search = function () {
        var element = _601_MemoNyuryoku_Dialog.element,
            loadingTaget = element.find(".modal-content"),
            table = element.find(".search-list"),
            query;

        _601_MemoNyuryoku_Dialog.options.filter = _601_MemoNyuryoku_Dialog.createFilter();
        query = {
            url: _601_MemoNyuryoku_Dialog.urls/* TODO: 検索データ取得サービスの URL */,
            filter: _601_MemoNyuryoku_Dialog.options.filter,
            orderby: "TODO:ソート対象の列名",
            top: _601_MemoNyuryoku_Dialog.options.top,
            inlinecount: "allpages"
        };

        _601_MemoNyuryoku_Dialog.validator.validate()
        .then(function () {
            table.find("tbody:visible").remove();
            _601_MemoNyuryoku_Dialog.notifyAlert.clear();
            App.ui.loading.show("", loadingTaget);

            $.ajax(App.ajax.odata.get(App.data.toODataFormat(query)))
            .done(function (result) {

                _601_MemoNyuryoku_Dialog.bind(result);
            }).always(function () {

                App.ui.loading.close(loadingTaget);
            });
        });
    };

    /**
     * 検索ダイアログの検索条件を組み立てます
     */
    _601_MemoNyuryoku_Dialog.createFilter = function () {
        var criteria = _601_MemoNyuryoku_Dialog.element.find(".search-criteria").form().data(),
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
    _601_MemoNyuryoku_Dialog.bind = function (data) {
        var element = _601_MemoNyuryoku_Dialog.element,
            table = element.find(".search-list"),
            count = data["odata.count"],
            items = data.value ? data.value : data,
            i, l, item, clone;

        element.findP("data_count").text(data.value.length);
        element.findP("data_count_total").text(count);

        _601_MemoNyuryoku_Dialog.data = App.ui.page.dataSet();
        _601_MemoNyuryoku_Dialog.data.attach(items);

        table.find("tbody:visible").remove();

        for (i = 0, l = items.length; i < l; i++) {
            item = items[i];
            clone = table.find(".item-tmpl").clone();
            clone.form().bind(item);
            clone.appendTo(table).removeClass("item-tmpl").show();
        }

        if (count && count > App.settings.base.dialogDataTakeCount) {
            _601_MemoNyuryoku_Dialog.notifyInfo.message(App.messages.base.MS0011).show();
        }
    };

    /**
     * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
     */
    _601_MemoNyuryoku_Dialog.select = function (e) {
        var element = _601_MemoNyuryoku_Dialog.element;
        _601_MemoNyuryoku_Dialog.validator.validate()
        .then(function () {
            data = element.findP("nm_memo").val();

            if (App.isFunc(_601_MemoNyuryoku_Dialog.dataSelected) && _601_MemoNyuryoku_Dialog.values.isChange) {
                if (!_601_MemoNyuryoku_Dialog.dataSelected(data)) {
                    element.modal("hide");
                }
            } else {
                element.modal("hide");
            }

        })

    };

    //TODO: 複数セレクトを使用する場合は、上の単一セレクト用select関数を削除し、下の複数セレクト用の関数をコメント解除します
    //TODO: 単一セレクトの場合は、不要なコメント部分は削除してください
    <%--
        /**
         * 一覧から行を選択された際の処理を実行します。（複数セレクト用）
         */
        _601_MemoNyuryoku_Dialog.select = function (e) {
            var element = _601_MemoNyuryoku_Dialog.element,
                items;

            //TODO: 選択された行から起動元画面に返却したい値を抽出します
            items = element.find(".search-list").find("input:checked[name='select_cd']").map(function (index, item) {
                var tbody = $(item).closest("tbody"),
                    id = tbody.attr("data-key"),
                    data = _601_MemoNyuryoku_Dialog.data.entry(id);
                return data.cd_torihiki;
            }).toArray();

            if (items.length == 0) {
                _601_MemoNyuryoku_Dialog.notifyAlert.message(App.messages.base.MS0020).show();
                return;
            }

            if (App.isFunc(_601_MemoNyuryoku_Dialog.dataSelected)) {
                if (!_601_MemoNyuryoku_Dialog.dataSelected(items)) {
                    element.modal("hide");
                }
            } else {
                element.modal("hide");
            }
        };

        /**
         * 一覧の行をクリックした際の処理を実行します。（複数セレクト用）
         */
        _601_MemoNyuryoku_Dialog.selectOne = function (e) {
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
        _601_MemoNyuryoku_Dialog.selectAll = function (e) {
            var target = $(e.target);
            _601_MemoNyuryoku_Dialog.element.find("[name='select_cd']:visible").prop("checked", target.is(":checked"));
        };
--%>
</script>

<div class="modal fade wide" tabindex="-1" id="_601_MemoNyuryoku_Dialog">
    <div class="modal-dialog" style="height: 350px; width: 30%">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">メモ入力</h4>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12 control" style="height: 100px">
                        <textarea class="form-control nm_memo" rows="5" cols="10" style="height: 100%; width: 100%; resize: none; margin: 0px" data-prop="nm_memo">
                        </textarea>
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
                <button type="button" class="btn btn-success select" name="select">OK</button>
                <button type="button" class="cancel-button btn btn-sm btn-default" data-dismiss="modal" name="close">キャンセル</button>
            </div>

        </div>
    </div>
</div>
