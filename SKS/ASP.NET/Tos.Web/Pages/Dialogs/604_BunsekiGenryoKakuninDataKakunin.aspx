<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="604_BunsekiGenryoKakuninDataKakunin.aspx.cs" Inherits="Tos.Web.Pages.Dialogs._604_BunsekiGenryoKakuninDataKakunin" %>
<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>

    <script type="text/javascript">

        /**
         * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
         */
        var _604_BunsekiGenryoKakuninDataKakunin = {
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.dialogDataTakeCount   // TODO:取得するデータ数を指定します。
            },
            urls: {
                getGenryo: "../api/_604_BunsekiGenryoKakuninDataKakunin_Dialog",
            },
            param: {
                cd_shain: 1,
                nen: 8,
                no_oi: 1
            }
        };

        /**
         * 検索ダイアログの初期化処理を行います。
         */
        _604_BunsekiGenryoKakuninDataKakunin.initialize = function () {
            var element = $("#_604_BunsekiGenryoKakuninDataKakunin");

            element.on("hidden.bs.modal", _604_BunsekiGenryoKakuninDataKakunin.hidden);
            element.on("shown.bs.modal", _604_BunsekiGenryoKakuninDataKakunin.shown);
            element.on("click", ".search", _604_BunsekiGenryoKakuninDataKakunin.search);
            //TODO: 単一セレクトの場合は、下の１行を使用します
            element.on("click", ".search-list tbody ", _604_BunsekiGenryoKakuninDataKakunin.select);
            //TODO: 複数セレクトの場合は、上の１行を削除し、下の３行をコメント解除します
            //element.on("click", ".select", _604_BunsekiGenryoKakuninDataKakunin.select);
            //element.on("click", ".search-list tbody", _604_BunsekiGenryoKakuninDataKakunin.selectOne);
            //element.find("[name='select_cd_all']").on("click", _604_BunsekiGenryoKakuninDataKakunin.selectAll);
            _604_BunsekiGenryoKakuninDataKakunin.element = element;

            element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
            element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

            _604_BunsekiGenryoKakuninDataKakunin.notifyInfo =
                 App.ui.notify.info(element, {
                     container: "#_604_BunsekiGenryoKakuninDataKakunin .dialog-slideup-area .info-message",
                     bodyContainer: ".modal-body",
                     show: function () {
                         element.find(".info-message").show();
                     },
                     clear: function () {
                         element.find(".info-message").hide();
                     }
                 });
            _604_BunsekiGenryoKakuninDataKakunin.notifyAlert =
                App.ui.notify.alert(element, {
                    container: "#_604_BunsekiGenryoKakuninDataKakunin .dialog-slideup-area .alert-message",
                    bodyContainer: ".modal-body",
                    show: function () {
                        element.find(".alert-message").show();
                    },
                    clear: function () {
                        element.find(".alert-message").hide();
                    }
                });

            _604_BunsekiGenryoKakuninDataKakunin.validator = element.validation(App.validation(_604_BunsekiGenryoKakuninDataKakunin.options.validations, {
                success: function (results, state) {
                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        _604_BunsekiGenryoKakuninDataKakunin.setColValidStyle(item.element);

                        _604_BunsekiGenryoKakuninDataKakunin.notifyAlert.remove(item.element);
                    }
                },
                fail: function (results, state) {
                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        _604_BunsekiGenryoKakuninDataKakunin.setColInvalidStyle(item.element);
                        if (state && state.suppressMessage) {
                            continue;
                        }

                        _604_BunsekiGenryoKakuninDataKakunin.notifyAlert.message(item.message, item.element).show();
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
        _604_BunsekiGenryoKakuninDataKakunin.setColInvalidStyle = function (target) {
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
        _604_BunsekiGenryoKakuninDataKakunin.setColValidStyle = function (target) {
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
        _604_BunsekiGenryoKakuninDataKakunin.hidden = function (e) {
            var element = _604_BunsekiGenryoKakuninDataKakunin.element,
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
                _604_BunsekiGenryoKakuninDataKakunin.setColValidStyle(item);
            }

            _604_BunsekiGenryoKakuninDataKakunin.notifyInfo.clear();
            _604_BunsekiGenryoKakuninDataKakunin.notifyAlert.clear();
        };

        /**
         * 検索ダイアログ表示時処理を実行します。
         */
        _604_BunsekiGenryoKakuninDataKakunin.shown = function (e) {

            _604_BunsekiGenryoKakuninDataKakunin.element.find(":input:not(button):first").focus();
            _604_BunsekiGenryoKakuninDataKakunin.search();
        };

        /**
         * 検索ダイアログ　バリデーションルールを定義します。
         */
        _604_BunsekiGenryoKakuninDataKakunin.options.validations = {
        };

        /**
         * 検索ダイアログの検索処理を実行します。
         */
        _604_BunsekiGenryoKakuninDataKakunin.search = function () {
            var element = _604_BunsekiGenryoKakuninDataKakunin.element,
                loadingTaget = element.find(".modal-content"),
                table = element.find(".search-list"),
                query;
            //_604_BunsekiGenryoKakuninDataKakunin.options.filter = _604_BunsekiGenryoKakuninDataKakunin.createFilter();
            //query = {
            //    url: _604_BunsekiGenryoKakuninDataKakunin.urls/* TODO: 検索データ取得サービスの URL */,
            //    filter: _604_BunsekiGenryoKakuninDataKakunin.options.filter,
            //    orderby: "TODO:ソート対象の列名",
            //    top: _604_BunsekiGenryoKakuninDataKakunin.options.top,
            //    inlinecount: "allpages"
            //};

            _604_BunsekiGenryoKakuninDataKakunin.validator.validate()
            .then(function () {
                table.find("tbody:visible").remove();
                _604_BunsekiGenryoKakuninDataKakunin.notifyAlert.clear();
                App.ui.loading.show("", loadingTaget);
                
                for (var i = 0 ; i < page.dialogs.BunsekiGenryoKakunin_Dialog.dataGenryo.length; i++) {
                    var item = page.dialogs.BunsekiGenryoKakunin_Dialog.dataGenryo[i];
                    if (!App.isUndefOrNull(item.nm_genryo) && (item.nm_genryo.charAt(0) === "☆" || item.nm_genryo.charAt(0) === "★")) {
                        if (item.nm_genryo.length > 1) {
                            page.dialogs.BunsekiGenryoKakunin_Dialog.dataGenryo[i].nm_genryo = page.dialogs.BunsekiGenryoKakunin_Dialog.dataGenryo[i].nm_genryo.substr(1);
                        }
                        else {
                            page.dialogs.BunsekiGenryoKakunin_Dialog.dataGenryo[i].nm_genryo = "";
                        }
                    }
                }
                
                var parameter = {
                    data: page.dialogs.BunsekiGenryoKakunin_Dialog.dataGenryo
                };

                $.ajax(App.ajax.webapi.post(_604_BunsekiGenryoKakuninDataKakunin.urls.getGenryo, parameter))
                .done(function (result) {

                    _604_BunsekiGenryoKakuninDataKakunin.bind(result);
                }).always(function () {

                    App.ui.loading.close(loadingTaget);
                });
            });
        };

        /**
         * 検索ダイアログの検索条件を組み立てます
         */
        _604_BunsekiGenryoKakuninDataKakunin.createFilter = function () {
            //var criteria = _604_BunsekiGenryoKakuninDataKakunin.element.find(".search-criteria").form().data(),
            //    filters = [];

            //TODO: 画面で設定された検索条件を取得し、データ取得サービスのフィルターオプションを組み立てます。
            //if (!App.isUndefOrNullOrStrEmpty(criteria.nm_torihiki)) {
            //    filters.push("substringof('" + encodeURIComponent(criteria.nm_torihiki) + "', nm_torihiki) eq true");
            //}
            var filters = {};
            filters = {
                cd_shain : _604_BunsekiGenryoKakuninDataKakunin.param.cd_shain,
                nen: _604_BunsekiGenryoKakuninDataKakunin.param.nen,
                no_oi: _604_BunsekiGenryoKakuninDataKakunin.param.no_oi
            };

            return filters;
        };

        /**
         * 検索ダイアログの一覧にデータをバインドします。
         */
        _604_BunsekiGenryoKakuninDataKakunin.bind = function (data) {
            var element = _604_BunsekiGenryoKakuninDataKakunin.element,
                items = data,
                i, l, item;
            var text = "";
            //element.findP("data_count").text(data.value.length);
            //element.findP("data_count_total").text(count);
            if (items.length == 0) {
                text = "変更されている原料はありません";
            }
            _604_BunsekiGenryoKakuninDataKakunin.data = App.ui.page.dataSet();
            _604_BunsekiGenryoKakuninDataKakunin.data.attach(items);

            
            for (i = 0, l = items.length; i < l; i++) {
                item = items[i];
                //clone = table.find(".item-tmpl").clone();
                //clone.form().bind(item);
                //clone.appendTo(table).removeClass("item-tmpl").show();
                //if (item.nm_genryo != item.nm_genryo1
			    //    || item.ritu_abura != item.ritu_abura1
			    //    || item.ritu_sakusan != item.ritu_sakusan1
			    //    || item.ritu_shokuen != item.ritu_shokuen1
			    //    || item.ritu_sousan != item.ritu_sousan1
			    //    || item.ritu_msg != item.ritu_msg1) {
                    text += "--------------------------------------------------------  \n"
                             + "工程順：" + item.sort_kotei + " \n"
                             + "原料コード：" + item.cd_genryo + " \n"
                             + "会社名：" + item.nm_kaisha + " \n"
                             + "部署名：" + item.nm_busho + " \n"
                             + "--------------------------------------------------------  \n"
                             + "■原料名：" + item.nm_genryo + " → " + item.nm_genryo1 + " \n"
                             + "■油含有率：" + item.ritu_abura + " → " + item.ritu_abura1 + " \n"
                             + "■酢酸：" + item.ritu_sakusan + " → " + item.ritu_sakusan1 + " \n"
                             + "■食塩：" + item.ritu_shokuen + " → " + item.ritu_shokuen1 + " \n"
                             + "■総酸：" + item.ritu_sousan + " → " + item.ritu_sousan1 + " \n"
                             + "■MSG：" + item.ritu_msg + " → " + item.ritu_msg1 + " \n"
                             + "--------------------------------------------------------  \n" ;
                //}
            }
            element.findP("nm_memo").val(text);

        };

        /**
         * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
         */
        _604_BunsekiGenryoKakuninDataKakunin.select = function (e) {
            var element = _604_BunsekiGenryoKakuninDataKakunin.element,
                target = $(e.target),
                id = target.closest("tbody").attr("data-key"),
                data;

            if (App.isUndef(id)) {
                return;
            }

            data = _604_BunsekiGenryoKakuninDataKakunin.data.entry(id);

            if (App.isFunc(_604_BunsekiGenryoKakuninDataKakunin.dataSelected)) {
                if (!_604_BunsekiGenryoKakuninDataKakunin.dataSelected(data)) {
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
        _604_BunsekiGenryoKakuninDataKakunin.select = function (e) {
            var element = _604_BunsekiGenryoKakuninDataKakunin.element,
                items;

            //TODO: 選択された行から起動元画面に返却したい値を抽出します
            items = element.find(".search-list").find("input:checked[name='select_cd']").map(function (index, item) {
                var tbody = $(item).closest("tbody"),
                    id = tbody.attr("data-key"),
                    data = _604_BunsekiGenryoKakuninDataKakunin.data.entry(id);
                return data.cd_torihiki;
            }).toArray();

            if (items.length == 0) {
                _604_BunsekiGenryoKakuninDataKakunin.notifyAlert.message(App.messages.base.MS0020).show();
                return;
            }

            if (App.isFunc(_604_BunsekiGenryoKakuninDataKakunin.dataSelected)) {
                if (!_604_BunsekiGenryoKakuninDataKakunin.dataSelected(items)) {
                    element.modal("hide");
                }
            } else {
                element.modal("hide");
            }
        };

        /**
         * 一覧の行をクリックした際の処理を実行します。（複数セレクト用）
         */
        _604_BunsekiGenryoKakuninDataKakunin.selectOne = function (e) {
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
        _604_BunsekiGenryoKakuninDataKakunin.selectAll = function (e) {
            var target = $(e.target);
            _604_BunsekiGenryoKakuninDataKakunin.element.find("[name='select_cd']:visible").prop("checked", target.is(":checked"));
        };
--%>
    </script>

    <div class="modal fade wide" tabindex="-1" id="_604_BunsekiGenryoKakuninDataKakunin">
    <div class="modal-dialog" style="height: 350px; width: 700px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">分析原料確認データ確認</h4>
            </div>

            <div class="modal-body">
                <div class="row">
                    <b> 分析原料確認データ確認</b>
                </div>
                <div class="row">
                    <div class="col-xs-12 control" style="height: 350px">
                        <textarea disabled   style="overflow-y: scroll;height: 100%;width:100%;resize: none;margin:0px" data-prop="nm_memo">
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
                <%--<button type="button" class="btn btn-success select" name="select" >選択</button>--%>
                <button type="button" class="cancel-button btn btn-sm btn-default" data-dismiss="modal" name="close">閉じる</button>
            </div>

        </div>
    </div>
    </div>