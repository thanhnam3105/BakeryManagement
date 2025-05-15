<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="602_IroShitei_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs._602_IroShitei_Dialog" %>
<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>

    <script type="text/javascript">

        /**
         * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
         */
        var _602_IroShitei_Dialog = {
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.dialogDataTakeCount,   // TODO:取得するデータ数を指定します。
                dataColor: {}
            },
            urls: {}
        };

        /**
         * 検索ダイアログの初期化処理を行います。
         */
        _602_IroShitei_Dialog.initialize = function () {
            var element = $("#_602_IroShitei_Dialog");

            element.on("hidden.bs.modal", _602_IroShitei_Dialog.hidden);
            element.on("shown.bs.modal", _602_IroShitei_Dialog.shown);
            element.on("click", ".search", _602_IroShitei_Dialog.search);
            //TODO: 単一セレクトの場合は、下の１行を使用します
            element.on("click", ".search-list tbody ", _602_IroShitei_Dialog.select);
            //TODO: 複数セレクトの場合は、上の１行を削除し、下の３行をコメント解除します
            //element.on("click", ".select", _602_IroShitei_Dialog.select);
            //element.on("click", ".search-list tbody", _602_IroShitei_Dialog.selectOne);
            //element.find("[name='select_cd_all']").on("click", _602_IroShitei_Dialog.selectAll);

            element.on("click", ".color-picker td", _602_IroShitei_Dialog.returnColor);
            element.on("click", "button[name='select']", _602_IroShitei_Dialog.resetColor);

            _602_IroShitei_Dialog.element = element;
            _602_IroShitei_Dialog.setUpColor();

            element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
            element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

            _602_IroShitei_Dialog.notifyInfo =
                 App.ui.notify.info(element, {
                     container: "#_602_IroShitei_Dialog .dialog-slideup-area .info-message",
                     bodyContainer: ".modal-body",
                     show: function () {
                         element.find(".info-message").show();
                     },
                     clear: function () {
                         element.find(".info-message").hide();
                     }
                 });
            _602_IroShitei_Dialog.notifyAlert =
                App.ui.notify.alert(element, {
                    container: "#_602_IroShitei_Dialog .dialog-slideup-area .alert-message",
                    bodyContainer: ".modal-body",
                    show: function () {
                        element.find(".alert-message").show();
                    },
                    clear: function () {
                        element.find(".alert-message").hide();
                    }
                });

            _602_IroShitei_Dialog.validator = element.validation(App.validation(_602_IroShitei_Dialog.options.validations, {
                success: function (results, state) {
                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        _602_IroShitei_Dialog.setColValidStyle(item.element);

                        _602_IroShitei_Dialog.notifyAlert.remove(item.element);
                    }
                },
                fail: function (results, state) {
                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        _602_IroShitei_Dialog.setColInvalidStyle(item.element);
                        if (state && state.suppressMessage) {
                            continue;
                        }

                        _602_IroShitei_Dialog.notifyAlert.message(item.message, item.element).show();
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

        _602_IroShitei_Dialog.resetColor = function (e) {
            //var target = $(e.target),
            //    oldTarget = _602_IroShitei_Dialog.element.find("td.focus");
            //oldTarget.removeClass("focus");
            //target.addClass("focus");
            _602_IroShitei_Dialog.setReturnColor();
        }

        _602_IroShitei_Dialog.returnColor = function (e) {
            var color = $(e.target).attr("data-color");
            if (color && _602_IroShitei_Dialog.setReturnColor) {
                _602_IroShitei_Dialog.setReturnColor(color);
            }
            _602_IroShitei_Dialog.element.modal("hide");
        }

        _602_IroShitei_Dialog.fillString = function (val, num) {
            var wrap = "0000000000000000";
            num = num ? num : 2;
            if (App.isUndefOrNull(val)) {
                val = "";
            }
            val = wrap + val;
            return val.substr(val.length - num);
        }


        _602_IroShitei_Dialog.getMainColor = function (begColor, colorStep, numColor, cIndex, sign, vColor) {
            var vRed = begColor.red,
                vGreen = begColor.green,
                vBlue = begColor.blue,
                res = [];
            for (var col = 0; col < numColor; col++) {
                vColor += (colorStep * sign);
                vColor = vColor > 255 ? 255 : vColor;
                vColor = vColor < 0 ? 0 : vColor;
                //svColor = iRow < 0;
                switch (cIndex) {
                    case 0:
                        vRed = Math.floor(vColor);
                        break;
                    case 1:
                        vGreen = Math.floor(vColor);
                        break;
                    case 2:
                        vBlue = Math.floor(vColor);
                        break;
                }
                if (vColor == 255 || vColor == 0) {
                    sign *= -1;
                    cIndex = (cIndex + 1) % 3;
                }
                res.push({
                    red: vRed,
                    green: vGreen,
                    blue: vBlue
                })
            }
            return res;
        }

        _602_IroShitei_Dialog.getActiveColor = function (color, tRow, iRow) {
            var res = {},
                avgRow = Math.floor(tRow / 2),
                cRow = avgRow - iRow,
                eco = 1.5;
            if (cRow < 0) {
                res.red = Math.floor(color.red + color.red * (cRow / (avgRow + eco)));
                res.green = Math.floor(color.green + color.green * (cRow / (avgRow + eco)));
                res.blue = Math.floor(color.blue + color.blue * (cRow / (avgRow + eco)));
            }
            else {
                res.red = Math.floor(255 - (((iRow + eco) / (avgRow + eco)) * (255 - color.red)));
                res.green = Math.floor(255 - (((iRow + eco) / (avgRow + eco)) * (255 - color.green)));
                res.blue = Math.floor(255 - (((iRow + eco) / (avgRow + eco)) * (255 - color.blue)));
            }
            return res;
        }

        _602_IroShitei_Dialog.setUpColor = function () {
            var tCol = 29,
                tRow = 9,
                table = _602_IroShitei_Dialog.element.find("table"),
                fRow = table.find("tr.item-tmpl"),
                colorStep = 51.2,
                colorIndex = 0,
                vColor = 0,
                vRed = 0,
                vGreen = 0,
                vBlue = 255,
                cIndex = 1,
                sign = 1,
                avgRow = Math.ceil(tRow / 2), iRow;
            table.find("tr:not(.item-tmpl)");
            var mainColor = _602_IroShitei_Dialog.getMainColor({ red: vRed, green: vGreen, blue: vBlue }, colorStep, tCol, cIndex, sign, vColor);
            //Tao dong that dua tren don giua va khoang cach giua dong hien tai va dong giua
            if (App.isUndefOrNull(_602_IroShitei_Dialog.options.dataColor.lstColor)) {
                var width = 100 / tCol;
                var height = 17;
                for (var row = 0; row < tRow; row++) {
                    var cRow = fRow.clone();
                    iRow = tRow - avgRow;
                    for (var col = 0; col < tCol; col++) {
                        var cCell = cRow.find("td.item-tmpl").clone(),
                            vColor = _602_IroShitei_Dialog.getActiveColor(mainColor[col], tRow, row),
                            vRed = _602_IroShitei_Dialog.fillString(vColor.red.toString(16)),
                            vGreen = _602_IroShitei_Dialog.fillString(vColor.green.toString(16)),
                            vBlue = _602_IroShitei_Dialog.fillString(vColor.blue.toString(16));

                        color = "#" + vRed + vGreen + vBlue;
                        cCell.css("background-color", color);
                        cCell.attr("data-color", color);
                        cCell.attr("title", vColor.red + " , " + vColor.green + " , " + vColor.blue);
                        cCell.css("width", width + "%");
                        cCell.css("height", height);
                        if (row > 5)
                            cCell.addClass("white");
                        else
                            cCell.addClass("black");
                        cCell.removeClass("item-tmpl").show();
                        cCell.appendTo(cRow);
                        colorIndex++;
                    }
                    cRow.removeClass("item-tmpl").show();
                    cRow.appendTo(table);
                }
            } else {
                tCol = _602_IroShitei_Dialog.options.dataColor.col;
                tRow = _602_IroShitei_Dialog.options.dataColor.row;
                var width = 100 / tCol;
                var height = 17;
                var k = 0;
                for (var row = 0; row < tRow; row++) {
                    var cRow = fRow.clone();
                    iRow = tRow - avgRow;
                    for (var col = 0; col < tCol; col++) {
                        var cCell = cRow.find("td.item-tmpl").clone(),
                        color = _602_IroShitei_Dialog.options.dataColor.lstColor[k];
                        k = k + 1;
                        cCell.css("background-color", color);
                        cCell.attr("data-color", color);
                        cCell.attr("title", color);
                        cCell.css("width", width + "%");
                        cCell.css("height", height);
                        if (row > 5)
                            cCell.addClass("white");
                        else
                            cCell.addClass("black");
                        cCell.removeClass("item-tmpl").show();
                        cCell.appendTo(cRow);
                        colorIndex++;
                    }
                    cRow.removeClass("item-tmpl").show();
                    cRow.appendTo(table);
                }
            }

            //var tCol = 10,
            //    tRow = 2,
            //    table = _602_IroShitei_Dialog.element.find("table"),
            //    fRow = table.find("tr.item-tmpl"),
            //    colorStep = 51.2,
            //    colorIndex = 0,
            //    vColor = 0,
            //    vRed = 0,
            //    vGreen = 0,
            //    vBlue = 255,
            //    cIndex = 1,
            //    sign = 1,
            //    avgRow = Math.ceil(tRow / 2), iRow,
            //    colorArr = [
            //        { red: 255, green: 255, blue: 255 },
            //        { red: 231, green: 230, blue: 230 },
            //        { red: 0, green: 0, blue: 0 },
            //        { red: 68, green: 84, blue: 106 },
            //        { red: 91, green: 155, blue: 213 },
            //        { red: 237, green: 125, blue: 49 },
            //        { red: 165, green: 165, blue: 165 },
            //        { red: 255, green: 192, blue: 0 },
            //        { red: 68, green: 114, blue: 196 },
            //        { red: 112, green: 173, blue: 71 },
            //        { red: 195, green: 23, blue: 24 },
            //        { red: 255, green: 0, blue: 0 },
            //        { red: 255, green: 191, blue: 0 },
            //        { red: 255, green: 255, blue: 0 },
            //        { red: 146, green: 208, blue: 80 },
            //        { red: 0, green: 176, blue: 80 },
            //        { red: 0, green: 176, blue: 240 },
            //        { red: 0, green: 112, blue: 192 },
            //        { red: 0, green: 22, blue: 89 },
            //        { red: 112, green: 48, blue: 160 }
            //    ];
            //table.find("tr:not(.item-tmpl)");
            ////var mainColor = _602_IroShitei_Dialog.getMainColor({ red: vRed, green: vGreen, blue: vBlue }, colorStep, tCol, cIndex, sign, vColor);
            ////Tao dong that dua tren don giua va khoang cach giua dong hien tai va dong giua

            //for (var row = 0; row < tRow; row++) {
            //    var cRow = fRow.clone();
            //    for (var col = 0; col < tCol; col++) {
            //        var cCell = cRow.find("td.item-tmpl").clone();

            //        color = "rgb(" + colorArr[row * 10 + col * 1].red.toString() + "," + colorArr[row * 10 + col * 1].green.toString() + "," + colorArr[row * 10 + col * 1].blue.toString() + ")";
            //        cCell.css("background-color", color);
            //        cCell.removeClass("item-tmpl").show();
            //        cCell.appendTo(cRow);
            //        colorIndex++;
            //    }
            //    cRow.removeClass("item-tmpl").show();
            //    cRow.appendTo(table);
            //}
        }
        /**
         * 単項目要素をエラーのスタイルに設定します。
         * @param target 設定する要素
         */
        _602_IroShitei_Dialog.setColInvalidStyle = function (target) {
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
        _602_IroShitei_Dialog.setColValidStyle = function (target) {
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
        _602_IroShitei_Dialog.hidden = function (e) {
            var element = _602_IroShitei_Dialog.element,
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
                _602_IroShitei_Dialog.setColValidStyle(item);
            }

            _602_IroShitei_Dialog.notifyInfo.clear();
            _602_IroShitei_Dialog.notifyAlert.clear();

            if (_602_IroShitei_Dialog.options.isCheckHide) {
                if (App.isFunc(_602_IroShitei_Dialog.isCheckHide)) {
                    _602_IroShitei_Dialog.isCheckHide();
                }
            }
        };

        /**
         * 検索ダイアログ表示時処理を実行します。
         */
        _602_IroShitei_Dialog.shown = function (e) {

            _602_IroShitei_Dialog.element.find(":input:not(button):first").focus();
        };

        /**
         * 検索ダイアログ　バリデーションルールを定義します。
         */
        _602_IroShitei_Dialog.options.validations = {
        };

        /**
         * 検索ダイアログの検索処理を実行します。
         */
        _602_IroShitei_Dialog.search = function () {
            var element = _602_IroShitei_Dialog.element,
                loadingTaget = element.find(".modal-content"),
                table = element.find(".search-list"),
                query;

            _602_IroShitei_Dialog.options.filter = _602_IroShitei_Dialog.createFilter();
            query = {
                url: _602_IroShitei_Dialog.urls/* TODO: 検索データ取得サービスの URL */,
                filter: _602_IroShitei_Dialog.options.filter,
                orderby: "TODO:ソート対象の列名",
                top: _602_IroShitei_Dialog.options.top,
                inlinecount: "allpages"
            };

            _602_IroShitei_Dialog.validator.validate()
            .then(function () {
                table.find("tbody:visible").remove();
                _602_IroShitei_Dialog.notifyAlert.clear();
                App.ui.loading.show("", loadingTaget);

                $.ajax(App.ajax.odata.get(App.data.toODataFormat(query)))
                .done(function (result) {

                    _602_IroShitei_Dialog.bind(result);
                }).always(function () {

                    App.ui.loading.close(loadingTaget);
                });
            });
        };

        /**
         * 検索ダイアログの検索条件を組み立てます
         */
        _602_IroShitei_Dialog.createFilter = function () {
            var criteria = _602_IroShitei_Dialog.element.find(".search-criteria").form().data(),
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
        _602_IroShitei_Dialog.bind = function (data) {
            var element = _602_IroShitei_Dialog.element,
                table = element.find(".search-list"),
                count = data["odata.count"],
                items = data.value ? data.value : data,
                i, l, item, clone;

            element.findP("data_count").text(data.value.length);
            element.findP("data_count_total").text(count);

            _602_IroShitei_Dialog.data = App.ui.page.dataSet();
            _602_IroShitei_Dialog.data.attach(items);

            table.find("tbody:visible").remove();

            for (i = 0, l = items.length; i < l; i++) {
                item = items[i];
                clone = table.find(".item-tmpl").clone();
                clone.form().bind(item);
                clone.appendTo(table).removeClass("item-tmpl").show();
            }

            if (count && count > App.settings.base.dialogDataTakeCount) {
                _602_IroShitei_Dialog.notifyInfo.message(App.messages.base.MS0011).show();
            }
        };

        /**
         * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
         */
        _602_IroShitei_Dialog.select = function (e) {
            var element = _602_IroShitei_Dialog.element,
                target = $(e.target),
                id = target.closest("tbody").attr("data-key"),
                data;

            if (App.isUndef(id)) {
                return;
            }

            data = _602_IroShitei_Dialog.data.entry(id);

            if (App.isFunc(_602_IroShitei_Dialog.dataSelected)) {
                if (!_602_IroShitei_Dialog.dataSelected(data)) {
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
        _602_IroShitei_Dialog.select = function (e) {
            var element = _602_IroShitei_Dialog.element,
                items;

            //TODO: 選択された行から起動元画面に返却したい値を抽出します
            items = element.find(".search-list").find("input:checked[name='select_cd']").map(function (index, item) {
                var tbody = $(item).closest("tbody"),
                    id = tbody.attr("data-key"),
                    data = _602_IroShitei_Dialog.data.entry(id);
                return data.cd_torihiki;
            }).toArray();

            if (items.length == 0) {
                _602_IroShitei_Dialog.notifyAlert.message(App.messages.base.MS0020).show();
                return;
            }

            if (App.isFunc(_602_IroShitei_Dialog.dataSelected)) {
                if (!_602_IroShitei_Dialog.dataSelected(items)) {
                    element.modal("hide");
                }
            } else {
                element.modal("hide");
            }
        };

        /**
         * 一覧の行をクリックした際の処理を実行します。（複数セレクト用）
         */
        _602_IroShitei_Dialog.selectOne = function (e) {
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
        _602_IroShitei_Dialog.selectAll = function (e) {
            var target = $(e.target);
            _602_IroShitei_Dialog.element.find("[name='select_cd']:visible").prop("checked", target.is(":checked"));
        };
--%>
    </script>

    <div class="modal fade wide" tabindex="-1" id="_602_IroShitei_Dialog">
    <div class="modal-dialog" style="height: 350px; width: 550px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">色指定</h4>
            </div>

            <div class="modal-body">
                <table class="full-width color-picker" style="border: 3px solid #333">
                    <tr class="item-tmpl" style="display: none;">
                        <td class="item-tmpl" style="display: none;" tabindex="0">
                        </td>
                    </tr>
                </table>
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
                <button type="button" class="cancel-button btn btn-sm btn-primary" data-dismiss="modal" name="select">選択色解除</button>
                <button type="button" class="cancel-button btn btn-sm btn-default" data-dismiss="modal" name="close">キャンセル</button>
            </div>

        </div>
    </div>
    </div>