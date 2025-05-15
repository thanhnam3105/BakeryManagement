<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="S_IroSettingDialog2.aspx.cs" Inherits="Tos.Web.Pages.Dialogs.S_IroSettingDialog2" %>

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
        var S_IroSettingDialog2 = {
            options: {},
            urls: {}
        };

        /**
         * 検索ダイアログの初期化処理を行います。
         */
        S_IroSettingDialog2.initialize = function () {
            var element = $("#S_IroSettingDialog2");

            element.on("hidden.bs.modal", S_IroSettingDialog2.hidden);
            element.on("click", ".search", S_IroSettingDialog2.search);
            //TODO: 単一セレクトの場合は、下の１行を使用します
            //element.on("click", ".search-list tbody ", S_IroSettingDialog2.select);
            //TODO: 複数セレクトの場合は、上の１行を削除し、下の３行をコメント解除します
            //element.on("click", ".select", S_IroSettingDialog2.select);
            //element.on("click", ".search-list tbody", S_IroSettingDialog2.selectOne);
            //element.find("[name='select_cd_all']").on("click", S_IroSettingDialog2.selectAll);

            element.on("click", ".color-picker td", S_IroSettingDialog2.returnColor);
            element.on("click", "button[name='select']", S_IroSettingDialog2.resetColor);

            S_IroSettingDialog2.element = element;
            S_IroSettingDialog2.setUpColor();

            element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
            element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

            S_IroSettingDialog2.notifyInfo =
                 App.ui.notify.info(element, {
                     container: "#S_IroSettingDialog2 .dialog-slideup-area .info-message",
                     messageContainerQuery: "ul",
                     show: function () {
                         element.find(".info-message").show();
                     },
                     clear: function () {
                         element.find(".info-message").hide();
                     }
                 });
            S_IroSettingDialog2.notifyAlert =
                App.ui.notify.alert(element, {
                    container: "#S_IroSettingDialog2 .dialog-slideup-area .alert-message",
                    messageContainerQuery: "ul",
                    show: function () {
                        element.find(".alert-message").show();
                    },
                    clear: function () {
                        element.find(".alert-message").hide();
                    }
                });

            S_IroSettingDialog2.validator = element.validation(App.validation(S_IroSettingDialog2.options.validations, {
                success: function (results, state) {
                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        S_IroSettingDialog2.setColValidStyle(item.element);

                        S_IroSettingDialog2.notifyAlert.remove(item.element);
                    }
                },
                fail: function (results, state) {

                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        S_IroSettingDialog2.setColInvalidStyle(item.element);
                        if (state && state.suppressMessage) {
                            continue;
                        }

                        S_IroSettingDialog2.notifyAlert.message(item.message, item.element).show();
                    }
                },
                always: function (results) {
                    //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
                }
            }));

            element.find(".modal-dialog").draggable({
                handle: ".modal-header"
            });
        };

        S_IroSettingDialog2.resetColor = function (e) {
            //var target = $(e.target),
            //    oldTarget = S_IroSettingDialog2.element.find("td.focus");
            //oldTarget.removeClass("focus");
            //target.addClass("focus");
            S_IroSettingDialog2.setReturnColor();
        }

        S_IroSettingDialog2.returnColor = function (e) {
            var color = $(e.target).attr("data-color");
            if (color && S_IroSettingDialog2.setReturnColor) {
                S_IroSettingDialog2.setReturnColor(color);
            }
            S_IroSettingDialog2.element.modal("hide");
        }

        S_IroSettingDialog2.fillString = function (val, num) {
            var wrap = "0000000000000000";
            num = num ? num : 2;
            if (App.isUndefOrNull(val)) {
                val = "";
            }
            val = wrap + val;
            return val.substr(val.length - num);
        }

        S_IroSettingDialog2.getMainColor = function (begColor, colorStep, numColor, cIndex, sign, vColor) {
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

        S_IroSettingDialog2.getActiveColor = function (color, tRow, iRow) {
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

        S_IroSettingDialog2.setUpColor = function () {
            var tCol = 29,
                tRow = 9,
                table = S_IroSettingDialog2.element.find("table"),
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
            var mainColor = S_IroSettingDialog2.getMainColor({ red: vRed, green: vGreen, blue: vBlue }, colorStep, tCol, cIndex, sign, vColor);
            //Tao dong that dua tren don giua va khoang cach giua dong hien tai va dong giua
            var width = 100 / tCol;
            var height = 17;
            for (var row = 0; row < tRow; row++) {
                var cRow = fRow.clone();
                iRow = tRow - avgRow;
                for (var col = 0; col < tCol; col++) {
                    var cCell = cRow.find("td.item-tmpl").clone(),
                        vColor = S_IroSettingDialog2.getActiveColor(mainColor[col], tRow, row),
                        vRed = S_IroSettingDialog2.fillString(vColor.red.toString(16)),
                        vGreen = S_IroSettingDialog2.fillString(vColor.green.toString(16)),
                        vBlue = S_IroSettingDialog2.fillString(vColor.blue.toString(16));

                    color = "#" + vRed + vGreen + vBlue;
                    cCell.css("background-color", color);
                    cCell.attr("data-color", color);
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

            //var tCol = 10,
            //    tRow = 2,
            //    table = S_IroSettingDialog2.element.find("table"),
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
            ////var mainColor = S_IroSettingDialog2.getMainColor({ red: vRed, green: vGreen, blue: vBlue }, colorStep, tCol, cIndex, sign, vColor);
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
        S_IroSettingDialog2.setColInvalidStyle = function (target) {
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
        S_IroSettingDialog2.setColValidStyle = function (target) {
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
        S_IroSettingDialog2.hidden = function (e) {

            var element = S_IroSettingDialog2.element,
                table = element.find(".search-list");

            //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
            element.find(":input").val("");
            //TODO: 複数セレクトの場合は、下の１行をコメント解除します。
            //element.find(":checked").prop("checked", false);
            table.find("tbody").not(".item-tmpl").remove();

            element.findP("data_count").text("");
            element.findP("data_count_total").text("");

            var items = element.find(".modal-body :input");
            for (var i = 0; i < items.length; i++) {
                var item = items[i];
                S_IroSettingDialog2.setColValidStyle(item);
            }

            S_IroSettingDialog2.notifyInfo.clear();
            S_IroSettingDialog2.notifyAlert.clear();
        };

        /**
         * 検索ダイアログ　バリデーションルールを定義します。
         */
        S_IroSettingDialog2.options.validations = {
        };

        /**
         * 検索ダイアログの一覧にデータをバインドします。
         */
        S_IroSettingDialog2.bind = function (data) {
            var element = S_IroSettingDialog2.element,
                table = element.find(".search-list"),
                count = data["odata.count"],
                items = data.value ? data.value : data,
                i, l, item, clone;

            element.findP("data_count").text(data.value.length);
            element.findP("data_count_total").text(count);

            S_IroSettingDialog2.data = App.ui.page.dataSet();
            S_IroSettingDialog2.data.attach(items);

            table.find("tbody:visible").remove();

            for (i = 0, l = items.length; i < l; i++) {
                item = items[i];
                clone = table.find(".item-tmpl").clone();
                clone.form().bind(item);
                clone.appendTo(table).removeClass("item-tmpl").show();
            }

            if (count && count > App.settings.base.dialogDataTakeCount) {
                S_IroSettingDialog2.notifyInfo.message(App.messages.base.MS0011).show();
            }

        };

        /**
         * 検索ダイアログの検索処理を実行します。
         */
        S_IroSettingDialog2.search = function () {
            var element = S_IroSettingDialog2.element,
                loadingTaget = element.find(".modal-content"),
                criteria = [],
                table = element.find(".search-list"),
                nameCriteria; 

            //TODO: 検索条件を構築する処理をここに記述します。
            //nameCriteria = element.findP("nm_torihiki").val();
            //if (nameCriteria) {
            //    criteria.push("substringof('" + encodeURIComponent(nameCriteria) + "', nm_torihiki) eq true");
            //}

            S_IroSettingDialog2.validator.validate()
            .then(function () {
                table.find("tbody:visible").remove();
                S_IroSettingDialog2.notifyAlert.clear();

                App.ui.loading.show("", loadingTaget);

                //TODO: 検索処理をここに記述します。
                $.ajax(App.ajax.odata.get(S_IroSettingDialog2.urls/* TODO: 検索データ取得サービスの URL */ +
                        "?$top=" + App.settings.base.dialogDataTakeCount + "&$inlinecount=allpages" +
                        (criteria.length ? "&$filter=" + criteria.join(" and ") : "")
                )).done(function (result) {

                    S_IroSettingDialog2.bind(result);

                }).always(function () {

                    App.ui.loading.close(loadingTaget);

                });
            });
        };

        /**
         * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
         */
        S_IroSettingDialog2.select = function (e) {
            var element = S_IroSettingDialog2.element,
                button = $(e.target),
                tbody = button.closest("tbody"),
                id = tbody.attr("data-key"),
                data;

            if (App.isUndef(id)) {
                return;
            }

            data = S_IroSettingDialog2.data.entry(id);

            if (App.isFunc(S_IroSettingDialog2.dataSelected)) {
                if (!S_IroSettingDialog2.dataSelected(data)) {
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
        S_IroSettingDialog2.select = function (e) {
            var element = S_IroSettingDialog2.element,
                data;
            //TODO: 選択された行から起動元画面に返却したい値を抽出します
            var items = element.find(".search-list").find("input:checked[name='select_cd']").map(function (index, item) {
                var tbody = $(item).closest("tbody");
                var id = tbody.attr("data-key");
                var data = S_IroSettingDialog2.data.entry(id);
                //return data.cd_torihiki;
            }).toArray();

            if (items.length == 0) {
                S_IroSettingDialog2.notifyAlert.message(App.messages.base.MS0020).show();
                return;
            }

            if (App.isFunc(S_IroSettingDialog2.dataSelected)) {
                if (!S_IroSettingDialog2.dataSelected(items)) {
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
        S_IroSettingDialog2.selectOne = function (e) {

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
        S_IroSettingDialog2.selectAll = function (e) {

            var $select_cd_all = $(e.target),
                isChecked = $select_cd_all.is(":checked");

            if (isChecked) {
                S_IroSettingDialog2.element.find("[name='select_cd']:visible").prop("checked", true);
            } else {
                S_IroSettingDialog2.element.find("[name='select_cd']:visible").prop("checked", false);
            }
            S_IroSettingDialog2.element.find("[name='select_cd']:visible").change();
        };
--%>
    </script>
    <div class="modal fade wide" id="S_IroSettingDialog2">
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