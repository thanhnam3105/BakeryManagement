<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="101_ShishakuData_GenkaShisan_Tab.aspx.cs" Inherits="Tos.Web.Pages.ShishakuData_GenkaShisan_Tab" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchInputDialog(Ver2.1)】 Template--%>
<style type="text/css">
    #GenkaShisan_Tab .col-sample {
        min-width: 135px!important;
        max-width: 135px!important;
    }

    #GenkaShisan_Tab .new tr td {
        padding: 0px;
        margin: 0px;
        height: 19px;
        font-size: 12px;
    }

    #GenkaShisan_Tab .new tr {
        height: 19px !important;
        padding: 0px;
    }

    #GenkaShisan_Tab .new input[type='tel'],
    #GenkaShisan_Tab .new input[type='text'] {
        border: none;
        padding: 0px;
        padding-left: 4px;
        margin: 0px;
        width: 100%;
        height: 100%;
        padding-left: 2px;
    }

    #GenkaShisan_Tab .new select {
        padding: 0px;
        border: none;
        border-width: 0px;
        margin: 0px;
        width: 100%;
        height: 100% !important;
        font-size: 11px;
        text-align: left;
        padding-left: 2px;
    }

    #GenkaShisan_Tab .new label {
        padding: 0px;
        padding-left: 4px;
        margin: 0px;
    }

    #GenkaShisan_Tab .new .required {
        color: red;
    }

    #GenkaShisan_Tab .textarea-validate .has-error {
        background-color: #ffdab9!important;
    }




    #GenkaShisan_Tab .new .label-not-color {
        cursor: default;
    }

    #GenkaShisan_Tab .new select option {
        background-color: white;
    }


    #GenkaShisan_Tab .new checkbox {
        margin: 0px;
    }

    #GenkaShisan_Tab .th-tmpl {
        display: none;
    }

    #GenkaShisan_Tab .dt-container .fix-columns .datatable {
        margin-right: 0px;
    }

    /*2019-09-10 : START : Bug #15319 選択行の色*/
    #GenkaShisan_Tab .selectedInput {
        background-color: #7cfc00!important;
    }

    #GenkaShisan_Tab .selected-row .enableColor:not(.selectedInput) {
        background-color: #fffed4!important;
    }

    /*2019-09-10 : START : Bug #15319 選択行の色*/

    #GenkaShisan_Tab .separatorColor:not(.selectedInput) {
        background-color: #b7b7b7!important;
    }

    /*#GenkaShisan_Tab .transparent {
        background-color: transparent!important;
    }*/
</style>

<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var GenkaShisan_Tab = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.maxInputDataCount,   // TODO:取得するデータ数を指定します。
            filter: ""
        },
        values: {
            isChangeRunning: {},
            flg_shisanIrai: 1,
            maxCheckPrint: 3,//印刷FGが4つ以上選択されていないこと
            messCalculator: null//※計算結果は桁数がオーバーの場合、メッセージ表示。		
        },
        urls: {
            search: "../api/ShishakuData_GenkaShisan_Tab_"
        },
        header: {
            options: {},
            values: {},
        },
        detail: {
            options: {},
            values: {}
        },
        commands: {},
        dialogs: {}
    };

    /**
     * バリデーション成功時の処理を実行します。
     */
    GenkaShisan_Tab.validationSuccess = function (results, state) {
        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                //列可変はグリッドでもセル単位で色付けを行う
                GenkaShisan_Tab.setColValidStyle(item.element, state.isGridValidation);
            } else {
                GenkaShisan_Tab.setColValidStyle(item.element);
            }

            App.ui.page.notifyAlert.remove(item.element);
        }
    };

    /**
     * バリデーション失敗時の処理を実行します。
     */
    GenkaShisan_Tab.validationFail = function (results, state) {

        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                //列可変はグリッドでもセル単位で色付けを行う
                GenkaShisan_Tab.setColInvalidStyle(item.element, state.isGridValidation);
            } else {
                GenkaShisan_Tab.setColInvalidStyle(item.element);
            }

            if (state && state.suppressMessage) {
                continue;
            }
            App.ui.page.notifyAlert.message(item.message, item.element).show();
        }
    };

    /**
     * バリデーション後の処理を実行します。
     */
    GenkaShisan_Tab.validationAlways = function (results) {
        //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
    };

    /**
      * 指定された定義をもとにバリデータを作成します。
      * @param target バリデーション定義
      * @param options オプションに設定する値。指定されていない場合は、
      *                画面の success/fail/always のハンドル処理が指定されたオプションが設定されます。
      */
    GenkaShisan_Tab.createValidator = function (target, options) {
        return App.validation(target, options || {
            success: GenkaShisan_Tab.validationSuccess,
            fail: GenkaShisan_Tab.validationFail,
            always: GenkaShisan_Tab.validationAlways
        });
    };

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    GenkaShisan_Tab.setColInvalidStyle = function (target, isGrid) {
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

        if (isGrid) {
            $target = $(target);//.closest("td");
            $target.addClass("has-error");
        } else {
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
        }
    };

    /**
     * 単項目要素をエラー無しのスタイルに設定します。
     * @param target 設定する要素
     */
    GenkaShisan_Tab.setColValidStyle = function (target, isGrid) {
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

        if (isGrid) {
            $target = $(target);//.closest("td");
            $target.removeClass("has-error");
        } else {
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
        }
    };

    /**
     * すべてのバリデーションを実行します。
     */
    GenkaShisan_Tab.validateAll = function () {

        var validations = [];

        validations.push(GenkaShisan_Tab.detail.validateList());

        //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。

        return App.async.all(validations);
    };

    /**
     * ダイアログの初期化処理を行います。
     */
    GenkaShisan_Tab.initialize = function () {

        var element = $("#GenkaShisan_Tab");

        element.show();
        GenkaShisan_Tab.element = element;

        GenkaShisan_Tab.initializeControlEvent();
        GenkaShisan_Tab.detail.initialize();

        GenkaShisan_Tab.detail.loadData();
        //原価試算登録
        GenkaShisan_Tab.detail.controlKakutei();
        element.css("display", "");

        element.on('shown.bs.tab', function (e) {
            element.find(".dt-container .flow-container .dt-body").scrollLeft(page.values.position);
        })
    };

    /**
    * 印刷FGが未選択されていないこと
    */
    GenkaShisan_Tab.detail.checkFlgPrint = function (ele) {
        var deferred = $.Deferred();
        var element = GenkaShisan_Tab.detail.element,
            checked = element.find(".flow-container .dt-body .datatable .new .flg_print:checked");

        if (!checked.length) {
            App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0028, { strName: "印刷FG" }), ele).show();
            deferred.reject();
        } else if (checked.length > GenkaShisan_Tab.values.maxCheckPrint) {
            App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0036, { strName: "原価試算表", colCount: GenkaShisan_Tab.values.maxCheckPrint }), ele).show();
            deferred.reject();
        }
        else {
            var checkedList = {
                keySample: []
                , sample: []
            }, indexEl, id;
            $.each(checked, function (index, item) {
                indexEl = element.find(item);
                id = indexEl.closest("td").attr("data-key-sample");

                if (App.isUndefOrNull(id)) {
                    return true;
                }
                checkedList.keySample.push(id);
                checkedList.sample.push(id.split("-")[1]);
            })

            deferred.resolve(checkedList);
        }

        return deferred.promise();
    }

    /**
    * 原価試算登録
    */
    GenkaShisan_Tab.detail.controlKakutei = function () {
        var element = $("#GenkaShisan_Tab");

        element.find(".kakutei").prop("disabled", page.values.modeDisp != App.settings.app.m_shisaku_data.shosai);
    }


    //GenkaShisan_Tab.detail.updateData = function (data) {

    //    var deferred = $.Deferred();
    //    var element = GenkaShisan_Tab.detail.element,
    //        genryoData = $.extend(true, {}, GenkaShisan_Tab.detail.data.entries),
    //        genryoDataDelete = [];

    //    $.each(genryoData, function (index, item) {
    //        if (item.state == App.ui.page.dataSet.status.Deleted) {
    //            genryoDataDelete.push(item);
    //        } else {
    //            item.state = App.ui.page.dataSet.status.Unchanged;
    //        }
    //    })

    //    if (genryoDataDelete.length) {
    //        $.each(genryoDataDelete, function (index, item) {
    //            delete genryoData[item.__id];
    //        })
    //    }

    //    GenkaShisan_Tab.detail.data = App.ui.page.dataSet();
    //    GenkaShisan_Tab.detail.data.entries = genryoData;

    //    deferred.resolve();

    //    return deferred.promise();
    //}

    /**
    * 特徴コピー時にタブをリロードする
    */
    GenkaShisan_Tab.reLoadTab = function () {
        var element = GenkaShisan_Tab.detail.element,
            headlength = GenkaShisan_Tab.detail.element_head_sample.find(".col-sample").length;

        var adjust = function () {
            var theadTable = element.find(".dt-container .flow-container .dt-head .datatable"),
                theadWith = theadTable.css("width").replace("px", ""),
                bodyTable = element.find(".dt-container .flow-container .dt-body .datatable");

            theadTable.css("width", "135px");
            theadTable.css("max-width", "135px");
            bodyTable.css("width", "135px");
            bodyTable.css("max-width", "135px");
        }

        //renew data set
        GenkaShisan_Tab.detail.data = App.ui.page.dataSet();

        //init data change for sample
        GenkaShisan_Tab.detail.dataSample = {};

        //remove all sample
        element.find('.col-sample').not(".th-tmpl").remove();
        element.find(".flow-container .dt-head .datatable thead th").not(".th-tmpl").remove();

        //試算確定サンプルＮｏ
        GenkaShisan_Tab.detail.loadDataShisanKakutei([]);

        //原価試算登録
        GenkaShisan_Tab.detail.controlKakutei();

        //テーブルでサイズ変更
        adjust();

        element.find(".shisanIraiked").removeClass("shisanIraiked");
    }

    /**
    * 全コピー時にタブをリロードする
    */
    GenkaShisan_Tab.detail.zenCopy = function () {
        //GenkaShisan_Tab.detail.data

        var element = GenkaShisan_Tab.detail.element,
            genryoData = $.extend(true, {}, GenkaShisan_Tab.detail.data.entries),
            genryoDataDelete = [];

        $.each(genryoData, function (index, item) {
            if (item.state == App.ui.page.dataSet.status.Deleted) {
                genryoDataDelete.push(item);
            } else {
                item.state = App.ui.page.dataSet.status.Added;
            }
        })

        if (genryoDataDelete.length) {
            $.each(genryoDataDelete, function (index, item) {
                delete genryoData[item.__id];
            })
        }

        GenkaShisan_Tab.detail.data = App.ui.page.dataSet();
        GenkaShisan_Tab.detail.data.entries = genryoData;

        //試算確定サンプルＮｏ
        GenkaShisan_Tab.detail.loadDataShisanKakutei([]);

        element.find(":input").prop("disabled", false);

        //印刷FG, 原価試算依頼
        element.find(".flg_shisanIrai, .cancel-irai-sample").prop("checked", false).prop("disabled", false).change();

        //依頼キャンセル
        element.find(".cancel-irai-sample").prop("checked", false).prop("disabled", true);

        //原価試算登録
        GenkaShisan_Tab.detail.controlKakutei();

        element.find(".shisanIraiked").removeClass("shisanIraiked");
    }

    /**
    * update from other tab change
    */
    GenkaShisan_Tab.detail.updateFromOtherTab = function (fiel, val, sample) {
        var element = GenkaShisan_Tab.detail.element;
        if (["", "yoryo", "su_iri", "baika"].indexOf(fiel) > 0) {
            fiel = fiel == "baika" ? "ko_baika" : fiel;
            fiel = fiel == "su_iri" ? "irisu" : fiel;

            var listUpdate = element.find(".update-" + fiel),
                index = 0, countRecalcu = 0;

            while (listUpdate[index]) {
                var elem = element.find(listUpdate[index]),
                    id = elem.attr("data-key");

                if (App.isUndefOrNull(id) || elem.findP(fiel).is(':disabled')) {
                    index += 1;
                    continue;
                }

                var entity = GenkaShisan_Tab.detail.data.entry(id);
                entity[fiel] = val;
                GenkaShisan_Tab.detail.data.update(entity);

                //2020年度改修案件
                //2020/06/17 : START : 「容量（数値入力）（※）*」項目に少数1桁まで入力可能変更
                //var formatNumber = page.detail.formatNumber(entity[fiel], fiel == "ko_baika" ? 2 : 0);
                var odd = 0;
                if (fiel == "ko_baika") {
                    odd = 2;
                }
                if (fiel == "yoryo") {
                    odd = 1;
                }
                var formatNumber = page.detail.formatNumber(entity[fiel], odd);
                //2020/06/17 : END : 「容量（数値入力）（※）*」項目に少数1桁まで入力可能変更

                elem.findP(fiel).val(formatNumber.forDisp);

                index += 1;
                countRecalcu += 1;
            }

            if (countRecalcu) {
                GenkaShisan_Tab.detail.recalculation("EventThisTab");
            }

            return;
        }

        var elementUpdate = element.find("." + sample),
            id = elementUpdate.attr("data-key");

        if (App.isUndefOrNull(id)) {
            return;
        }

        switch (fiel) {
            case "hiju":
            case "gokei":
            case "hiju_sui":
                val = page.detail.formatNumber(val, 3);
                break;
            default:
                val = page.detail.formatNumber(val, 2);
                break;
        }

        var entity = GenkaShisan_Tab.detail.data.entry(id);
        entity[fiel] = GenkaShisan_Tab.detail.stringForData(val.forDisp);
        GenkaShisan_Tab.detail.data.update(entity);

        GenkaShisan_Tab.element.find("." + sample).findP(fiel).val(val.forDisp);
    }

    /**
    * 工程パターンの変更イベント
    */
    GenkaShisan_Tab.changeKoteiPatan = function (patan, cd_tani) {
        var element = GenkaShisan_Tab.detail.element.find(".new"),
            zyusui = element.find(".zyusui-input").not(".shisanIraiked");

        //すべて更新
        var ele, id, entity;
        $.each(zyusui, function () {
            ele = element.find(this);
            id = ele.closest("td").attr("data-key");

            if (App.isUndefOrNull(id)) {
                return true;
            }

            entity = GenkaShisan_Tab.detail.data.entry(id);
            //充填量水相（g）（※）
            entity["zyusui"] = 0;
            //充填量油相（g）（※）
            entity["zyuabura"] = 0;

            GenkaShisan_Tab.detail.data.update(entity);
            //GenkaShisan_Tab.detail.validator.validate({targets: targets})
        })


        ////編集不可（初期値：0.00）
        $.each(element.find(".zyusui-input, .zyuabura-input").not(".shisanIraiked"), function () {
            var elem = element.find(this);

            //編集不可（初期値：0.00）
            elem.val("0.00").prop('readonly', true);;

            GenkaShisan_Tab.detail.validator.validate({
                targets: elem,
                state: {
                    tbody: element,
                    isGridValidation: true
                }
            })
        })

        //その他・加食タイプ【blank】,【ｇ】
        if (patan == App.settings.app.pt_kotei.sonohokaeki && (!cd_tani || cd_tani == App.settings.app.cd_tani.gram)) {
            element.find(".zyusui-input, .zyuabura-input").not(".shisanIraiked").prop('readonly', false);
        }
    };

    /**
     * control befor recacu tab
     */
    GenkaShisan_Tab.detail.recalculation = function (isFiel, data, isNewGenryo) {
        var isExitBlankKotei = false;
        page.detail.tabs.haigohyo_tab.detail.checkBlankKotei(undefined, true).fail(function () {
            isExitBlankKotei = true;
        }).always(function () {
            switch (isFiel) {
                case "単価-歩留":
                case "量":
                case "合計仕上重量（ｇ）":
                case "工程パターン":
                case "試作列コピー":
                case "FirstStart"://2019-12-12 : Bug #16120 : 現行仕様に合わせて都度計算して表示するようお願い致します。
                    GenkaShisan_Tab.detail.recalcuGenka(data, false, isExitBlankKotei);
                    break;
                case "EventHaigo":
                    GenkaShisan_Tab.detail.recalcuGenka(data, isNewGenryo, isExitBlankKotei);
                    break;
                case "EventTokuseichi":
                    GenkaShisan_Tab.detail.recalcuGenka(data, false, isExitBlankKotei);
                    break;
                case "EventThisTab":
                    App.ui.loading.show();
                    var result;
                    //配合タブーのデータ
                    GenkaShisan_Tab.detail.getDataTab("haigohyo_tab").then(function (dataHaigo) {
                        result = dataHaigo;
                        result["shisaku_hin"] = GenkaShisan_Tab.getDataHeader();

                        return page.detail.getDataTab("tokuseichi_tab");
                    }).then(function (dataToku) {
                        result["dataToku"] = dataToku;
                        GenkaShisan_Tab.detail.recalcuGenka(result, false, isExitBlankKotei);
                    }).fail(function () {
                        App.ui.loading.close();
                    })
                    break;
                default:
                    App.ui.loading.close();
                    break;
            }
        })
    }

    /**
    * 原価試算
    */
    GenkaShisan_Tab.detail.recalcuGenka = function (data, isNewGenryo, isExitBlankKotei) {
        var dataBind = {},
            validate = false,
            inputItems = "配合量、比重、容量",
            calculItem,
            genryohiTotal = {};//原料費（ｋｇ）、原料費（１個）

        //その他・加食タイプ
        if (data.shisaku_hin.pt_kotei == App.settings.app.pt_kotei.sonohokaeki) {

            //var cs_genryo, quatity;
            $.each(data.shisaku, function (index, item) {
                var tokuseichi = data["dataToku"]["sample-" + item.indexCol];
                var cs_genryo = 0,//「金額計」
                    quatity = 0;//計

                $.each(item, function (pro, val) {

                    var quantity = 0;
                    var haigo, zoku_kotei;

                    if (pro.indexOf("quantity_") < 0) {
                        return true;
                    }

                    val = page.detail.checkUndefi(val);

                    var spli = pro.split("_"),
                        haigo = data.haigo[spli[0] + "_" + spli[1] + "_" + spli[2]];

                    if (!haigo) {
                        return true;
                    }

                    zoku_kotei = GenkaShisan_Tab.confirmKoteiZokusei(haigo.zoku_kotei);

                    //ソース, 別充填具材
                    if (zoku_kotei.value1 == App.settings.app.kote_value1.otherType) {
                        //「量」 × 「単価」 ÷ ( 「歩留」 ÷ 100 )
                        //quatity = val * haigo.tanka / (haigo.budomari / App.settings.app.keisansikiyo.value_100);
                        quatity = new BigNumber(stringNumbers(val)).times(stringNumbers(haigo.tanka)).div(stringNumbers(new BigNumber(stringNumbers(haigo.budomari)).div(stringNumbers(App.settings.app.keisansikiyo.value_100)).toNumber())).toNumber();
                        //cs_genryo += page.detail.checkUndefi(quatity);
                        cs_genryo = new BigNumber(stringNumbers(cs_genryo)).plus(stringNumbers(page.detail.checkUndefi(quatity))).toNumber();
                    }
                });

                var elementCol = GenkaShisan_Tab.detail.element.find(".sample-" + item.indexCol),
                    id = elementCol.attr("data-key"),
                    entity = GenkaShisan_Tab.detail.data.entry(id);

                var //reberu = page.detail.checkUndefi(data.shisaku_hin.yoryo) * page.detail.checkUndefi(data.shisaku_hin.su_iri),//レベル量(g内容量ｘ入数) : 「容量」 × 「入数」
                    reberu = "",//new BigNumber(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.yoryo))).times(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.su_iri))).toNumber(),//レベル量(g内容量ｘ入数) : 「容量」 × 「入数」
                    //gokei = page.detail.checkUndefi(data.shisaku_hin.yoryo) * page.detail.checkUndefi(tokuseichi.hiju),//合計量(１本：g) :「容量」 × 「比重」
                    gokei = "",//new BigNumber(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.yoryo))).times(stringNumbers(page.detail.checkUndefi(tokuseichi.hiju))).toNumber(),//合計量(１本：g) :「容量」 × 「比重」
                    //hizyubudomari = page.detail.checkUndefi(entity.heikinzyu) * page.detail.checkUndefi(tokuseichi.hiju);//比重加算量（ｇ平均充填量ｘ比重）:「平均充填量」 × 「比重」
                    hizyubudomari = "";//new BigNumber(stringNumbers(page.detail.checkUndefi(entity.heikinzyu))).times(stringNumbers(page.detail.checkUndefi(tokuseichi.hiju))).toNumber();//比重加算量（ｇ平均充填量ｘ比重）:「平均充填量」 × 「比重」

                //Bug #15230 : 計算に使用する項目が未入力の場合計算対象外として頂けません
                if (checkEvalBeforCalcu([data.shisaku_hin.su_iri, data.shisaku_hin.yoryo])) {
                    reberu = new BigNumber(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.yoryo))).times(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.su_iri))).toNumber();//レベル量(g内容量ｘ入数) : 「容量」 × 「入数」
                }
                if (checkEvalBeforCalcu([tokuseichi.hiju, data.shisaku_hin.yoryo])) {
                    gokei = new BigNumber(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.yoryo))).times(stringNumbers(page.detail.checkUndefi(tokuseichi.hiju))).toNumber();//合計量(１本：g) :「容量」 × 「比重」
                }
                if (checkEvalBeforCalcu([tokuseichi.hiju, entity.heikinzyu])) {
                    hizyubudomari = new BigNumber(stringNumbers(page.detail.checkUndefi(entity.heikinzyu))).times(stringNumbers(page.detail.checkUndefi(tokuseichi.hiju))).toNumber();//比重加算量（ｇ平均充填量ｘ比重）:「平均充填量」 × 「比重」
                }

                var isExitGenryo = false;
                var genryohiGenryo = undefined;
                //「金額計」 ÷ 「仕上がり合計重量」 × 「平均充填量」 ÷ 1000 ÷ ( 「有効歩留（％）」 ÷ 100 )
                //Bug #15230 : 計算に使用する項目が未入力の場合計算対象外として頂けません
                if (checkEvalBeforCalcu([entity.yukobudomari, entity.heikinzyu]) && !isExitBlankKotei) {
                    try {
                        isExitGenryo = true;
                        if (item.juryo_shiagari_g != "" && item.juryo_shiagari_g != null && item.juryo_shiagari_g != undefined) {
                            //cs_genryo = cs_genryo / item.juryo_shiagari_g * page.detail.checkUndefi(entity.heikinzyu) / App.settings.app.keisansikiyo.value_1000 / (page.detail.checkUndefi(entity.yukobudomari) / App.settings.app.keisansikiyo.value_100);
                            cs_genryo = new BigNumber(stringNumbers(cs_genryo, true)).div(stringNumbers(item.juryo_shiagari_g, true)).times(stringNumbers(entity.heikinzyu, true)).div(stringNumbers(App.settings.app.keisansikiyo.value_1000, true)).div(new BigNumber(stringNumbers(entity.yukobudomari, true)).div(stringNumbers(App.settings.app.keisansikiyo.value_100, true)).toNumber() + "").toNumber();

                            if (cs_genryo == 0) {
                                genryohiGenryo = "0.00";
                            }
                        } else {
                            cs_genryo = "0.00";
                            genryohiGenryo = "0.00";
                        }
                    } catch (ex) {
                        isExitGenryo = false;
                        cs_genryo = "";
                        genryohiGenryo = "";
                    }
                } else {
                    cs_genryo = "";
                    genryohiGenryo = "";
                }
                cs_genryo = page.detail.checkUndefi(cs_genryo);

                //レベル量(g内容量ｘ入数) 
                reberu = page.detail.formatNumber(page.detail.checkUndefi(reberu), 2, "0.00", 100);
                if (!page.stringPointlength(reberu.forData, { after: 2, max: 15 })) {
                    calculItem = "レベル量(g内容量ｘ入数) ";
                    validate = true;
                    return false;
                }
                entity["reberu"] = GenkaShisan_Tab.detail.stringForData(reberu.forDisp);
                elementCol.findP("reberu").val(reberu.forDisp);

                //合計量(１本：g)
                gokei = page.detail.formatNumber(page.detail.checkUndefi(gokei), 3, "0.000", 1000);
                if (!page.stringPointlength(gokei.forData, { after: 3, max: 15 })) {
                    calculItem = "合計量(１本：g）";
                    validate = true;
                    return false;
                }
                entity["gokei"] = GenkaShisan_Tab.detail.stringForData(gokei.forDisp);
                elementCol.findP("gokei").val(gokei.forDisp);

                //比重加算量（ｇ平均充填量ｘ比重）
                hizyubudomari = page.detail.formatNumber(page.detail.checkUndefi(hizyubudomari), 2, "0.00", undefined, 100);
                if (!page.stringPointlength(hizyubudomari.forData, { after: 2, max: 15 })) {
                    calculItem = "比重加算量（ｇ平均充填量ｘ比重）";
                    validate = true;
                    return false;
                }
                entity["hizyubudomari"] = GenkaShisan_Tab.detail.stringForData(hizyubudomari.forDisp);
                elementCol.findP("hizyubudomari").val(hizyubudomari.forDisp);

                var genryohi1 = "",// 原料費（１本当）:「原料費/ケース」 ÷ 「入数」
                    genryohi = "";//原料費（ｋg) :「原料費/ケース」 ÷（ 「レベル量(g内容量ｘ入数)」×「比重」） × 1000

                //Bug #15230 : 計算に使用する項目が未入力の場合計算対象外として頂けません
                if (genryohiGenryo != undefined) {
                    genryohi1 = genryohiGenryo;
                } else if (checkEvalBeforCalcu([data.shisaku_hin.su_iri, entity.yukobudomari])) {
                    //genryohi1 = cs_genryo / page.detail.checkUndefi(data.shisaku_hin.su_iri);
                    genryohi1 = new BigNumber(stringNumbers(cs_genryo)).div(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.su_iri))).toNumber();
                }
                genryohi1 = page.detail.checkUndefi(genryohi1);

                //genryohi = cs_genryo / (reberu.forData * page.detail.checkUndefi(tokuseichi.hiju)) * App.settings.app.keisansikiyo.value_1000;
                //Bug #15230 : 計算に使用する項目が未入力の場合計算対象外として頂けません
                if (genryohiGenryo != undefined) {
                    genryohi = genryohiGenryo;
                } else if (checkEvalBeforCalcu([tokuseichi.hiju, entity.yukobudomari])) {
                    genryohi = new BigNumber(stringNumbers(cs_genryo)).div(stringNumbers(new BigNumber(stringNumbers(reberu.forData)).times(stringNumbers(page.detail.checkUndefi(tokuseichi.hiju))).toNumber())).times(stringNumbers(App.settings.app.keisansikiyo.value_1000)).toNumber();
                }

                //原料費（１本当）
                genryohi1 = page.detail.formatNumber(page.detail.checkUndefi(genryohi1), 2, genryohiGenryo, 100);
                if (!page.stringPointlength(genryohi1.forData, { after: 2, max: 15 })) {
                    calculItem = "原料費（１本当）";
                    validate = true;
                    return false;
                }
                entity["genryohi1"] = GenkaShisan_Tab.detail.stringForData(genryohi1.forDisp);
                elementCol.findP("genryohi1").val(genryohi1.forDisp);

                //原料費（ｋg)
                genryohi = page.detail.formatNumber(page.detail.checkUndefi(genryohi), 2, genryohiGenryo, 100);
                if (!page.stringPointlength(genryohi.forData, { after: 2, max: 15 })) {
                    calculItem = "原料費（ｋg)";
                    validate = true;
                    return false;
                }
                entity["genryohi"] = GenkaShisan_Tab.detail.stringForData(genryohi.forDisp);
                elementCol.findP("genryohi").val(genryohi.forDisp);

                var cs_genka = "",//原価計/CS : 「原料費/ケース」 ＋ 「材料費/ケース」 ＋ 「固定費/ケース」
                    ko_genka = "",//原価計/個 : 「原価計/ケース」 ÷ 「入数」
                    ko_riritu = "";//粗利（％）: （ 1 － （ 「原価計/個」 ÷ 「売価」 ) ） × 100

                //cs_genka = cs_genryo + page.detail.checkUndefi(entity.cs_zairyohi) + page.detail.checkUndefi(entity.cs_keihi);
                if (isExitGenryo) {
                    cs_genka = new BigNumber(stringNumbers(cs_genryo)).plus(stringNumbers(page.detail.checkUndefi(entity.cs_zairyohi))).plus(stringNumbers(page.detail.checkUndefi(entity.cs_keihi))).toNumber();
                }

                cs_genka = page.detail.checkUndefi(cs_genka);

                //Bug #15230 : 計算に使用する項目が未入力の場合計算対象外として頂けません
                if (checkEvalBeforCalcu([data.shisaku_hin.su_iri]) && isExitGenryo) {
                    ko_genka = new BigNumber(stringNumbers(cs_genka)).div(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.su_iri))).toNumber();
                }
                ko_genka = page.detail.checkUndefi(ko_genka);

                //Bug #15230 : 計算に使用する項目が未入力の場合計算対象外として頂けません
                if (checkEvalBeforCalcu([data.shisaku_hin.su_iri, data.shisaku_hin.baika]) && isExitGenryo) {
                    //ko_riritu = (App.settings.app.keisansikiyo.value_1 - (ko_genka / page.detail.checkUndefi(data.shisaku_hin.baika))) * App.settings.app.keisansikiyo.value_100;
                    ko_riritu = new BigNumber(stringNumbers(new BigNumber((App.settings.app.keisansikiyo.value_1)).minus(stringNumbers(new BigNumber(stringNumbers(ko_genka)).div(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.baika))).toNumber())).toNumber())).times(stringNumbers(App.settings.app.keisansikiyo.value_100)).toNumber();
                }
                ko_riritu = page.detail.checkUndefi(ko_riritu)

                //１c/s当たりの計算【原料費】
                cs_genryo = page.detail.formatNumber(page.detail.checkUndefi(cs_genryo), 2, (isExitGenryo ? "0.00" : undefined), 100);
                if (!page.stringPointlength(cs_genryo.forData, { after: 2, max: 15 })) {
                    calculItem = "原料費";
                    validate = true;
                    return false;
                }
                entity["cs_genryo"] = GenkaShisan_Tab.detail.stringForData(cs_genryo.forDisp);
                elementCol.findP("cs_genryo").val(cs_genryo.forDisp);

                //原価計/CS 
                cs_genka = page.detail.formatNumber(page.detail.checkUndefi(cs_genka), 2, undefined, 100);
                if (!page.stringPointlength(cs_genka.forData, { after: 2, max: 15 })) {
                    calculItem = "原価計/CS";
                    validate = true;
                    return false;
                }
                entity["cs_genka"] = GenkaShisan_Tab.detail.stringForData(cs_genka.forDisp);
                elementCol.findP("cs_genka").val(cs_genka.forDisp);
                //原価計/個
                ko_genka = page.detail.formatNumber(page.detail.checkUndefi(ko_genka), 2, undefined, 100);
                if (!page.stringPointlength(ko_genka.forData, { after: 2, max: 15 })) {
                    calculItem = "原価計/個";
                    validate = true;
                    return false;
                }
                entity["ko_genka"] = GenkaShisan_Tab.detail.stringForData(ko_genka.forDisp);
                elementCol.findP("ko_genka").val(ko_genka.forDisp);
                //粗利（％）
                var koRirituFormat = undefined;
                if (cs_genryo.forDisp == "") {
                    ko_riritu = cs_genryo.forDisp;
                    koRirituFormat = cs_genryo.forDisp;
                } else if (!checkEvalBeforCalcu([data.shisaku_hin.baika])) {
                    ko_riritu = "0.00";
                    koRirituFormat = "0.00";
                }
                ko_riritu = page.detail.formatNumber(page.detail.checkUndefi(ko_riritu), 2, koRirituFormat, 100);
                if (!page.stringPointlength(ko_riritu.forData, { after: 2, max: 15 })) {
                    calculItem = "粗利（％）";
                    validate = true;
                    return false;
                }
                entity["ko_riritu"] = GenkaShisan_Tab.detail.stringForData(ko_riritu.forDisp);
                elementCol.findP("ko_riritu").val(ko_riritu.forDisp);

                //原価原料テーブルにデータがなくてもサンプルNoの列は表示されるようになっています。
                if (isNewGenryo == true) {
                    //原料費（ｋg)
                    genryohi.forDisp = "0.00";
                    entity["genryohi"] = genryohi.forDisp;
                    elementCol.findP("genryohi").val(genryohi.forDisp);

                    //原料費（１本当）
                    genryohi1.forDisp = "0.00";
                    entity["genryohi1"] = genryohi1.forDisp;
                    elementCol.findP("genryohi1").val(genryohi1.forDisp);

                    //原価計/個
                    ko_genka.forDisp = "0.00";
                    entity["ko_genka"] = ko_genka.forDisp;
                    elementCol.findP("ko_genka").val(ko_genka.forDisp);

                    //原価計/CS 
                    cs_genka.forDisp = "0.00";
                    entity["cs_genka"] = cs_genka.forDisp;
                    elementCol.findP("cs_genka").val(cs_genka.forDisp);

                    //１c/s当たりの計算【原料費】
                    cs_genryo.forDisp = "0.00";
                    entity["cs_genryo"] = cs_genryo.forDisp;
                    elementCol.findP("cs_genryo").val(cs_genryo.forDisp);
                }

                GenkaShisan_Tab.detail.data.update(entity);

                //原料費（ｋｇ）
                //原料費（１個）
                if (!genryohiTotal[".sample-" + item.indexCol]) {
                    genryohiTotal[".sample-" + item.indexCol] = {};
                }
                //原料費（ｋｇ）
                genryohiTotal[".sample-" + item.indexCol]["genryohi"] = genryohi.forDisp;
                //
                //原料費（１個）
                genryohiTotal[".sample-" + item.indexCol]["genryohi1"] = genryohi1.forDisp;
            })
        }

        //調味料1液タイプ, 調味料2液タイプ
        if (data.shisaku_hin.pt_kotei == App.settings.app.pt_kotei.chomieki_1 || data.shisaku_hin.pt_kotei == App.settings.app.pt_kotei.chomieki_2) {



            $.each(data.shisaku, function (index, item) {
                var tokuseichi = data["dataToku"]["sample-" + item.indexCol];
                var abura_sho_juryu = 0,    //油相合計重量
                abura_aihaka = 0,       //油相合計単価
                suiso_no_juryo = 0,     //水相合計重量
                suiso_tanka = 0;        //水相合計単価
                var cs_genryo = "",
                    zyusui = 0,//充填量水相（ｇ）
                    dblSakinGokeiryo = 0,//殺菌調味液重量(g)
                    dblSuisoGokeiryo = 0,//水相合計重量(g) 
                    dblYusoGokeiryo = 0,//油相合計重量(g) 
                    dblYusoZyuten = "",//0,//充填量油相
                    dblSuisoZyuten = "",//0,//充填量水相
                    dblSuisoHiju = 0,//水相比重
                    dblYusoHiju = 0.92,//油相比重  
                    dblSeihinYoryo = data.shisaku_hin.yoryo,//製品容量
                    quatity = 0;//計

                $.each(item, function (pro, val) {

                    var quantity = 0;
                    var haigo, zoku_kotei;

                    if (pro.indexOf("quantity_") < 0) {
                        return true;
                    }

                    val = page.detail.checkUndefi(val);

                    var spli = pro.split("_"),
                        haigo = data.haigo[spli[0] + "_" + spli[1] + "_" + spli[2]];

                    if (!haigo) {
                        return true;
                    }

                    zoku_kotei = GenkaShisan_Tab.confirmKoteiZokusei(haigo.zoku_kotei);

                    if (zoku_kotei.value1 == App.settings.app.kote_value1.chomiryoType) {
                        //殺菌調味液（リテラルマスタのvalue1 =1  AND value2 = 1）の「量」の合計　
                        //dblSakinGokeiryo += GenkaShisan_Tab.detail.findKoteiZokusei(haigo, val, 1);
                        dblSakinGokeiryo = new BigNumber(stringNumbers(dblSakinGokeiryo)).plus(stringNumbers(GenkaShisan_Tab.detail.findKoteiZokusei(haigo, val, 1))).toNumber();
                        //水相（リテラルマスタのvalue1 =1  AND value2 = 2）の「量」の合計　
                        //dblSuisoGokeiryo += GenkaShisan_Tab.detail.findKoteiZokusei(haigo, val, 2);
                        dblSuisoGokeiryo = new BigNumber(stringNumbers(dblSuisoGokeiryo)).plus(stringNumbers(GenkaShisan_Tab.detail.findKoteiZokusei(haigo, val, 2))).toNumber();
                        //油相（リテラルマスタのvalue1 =1  AND value2 = 3）の「量」の合計　
                        //dblYusoGokeiryo += GenkaShisan_Tab.detail.findKoteiZokusei(haigo, val, 3);
                        dblYusoGokeiryo = new BigNumber(stringNumbers(dblYusoGokeiryo)).plus(stringNumbers(GenkaShisan_Tab.detail.findKoteiZokusei(haigo, val, 3))).toNumber();

                        //油相合計重量
                        //油相合計単価
                        //【工程 = 油相】 
                        if (zoku_kotei.value2 == App.settings.app.kote_value2.aburasho) {
                            //「量」の油相工程  
                            //abura_sho_juryu += val;
                            abura_sho_juryu = new BigNumber(stringNumbers(abura_sho_juryu)).plus(stringNumbers(val)).toNumber();
                            //油相合計単価
                            //「量」 × 「単価」 ÷ ( 「歩留」 ÷ 100 ) の 油相の合計
                            //quatity = val * page.detail.checkUndefi(haigo.tanka) / (page.detail.checkUndefi(haigo.budomari) / App.settings.app.keisansikiyo.value_100);
                            quatity = new BigNumber(stringNumbers(val)).times(stringNumbers(page.detail.checkUndefi(haigo.tanka))).div(stringNumbers(new BigNumber(stringNumbers(page.detail.checkUndefi(haigo.budomari))).div(stringNumbers(App.settings.app.keisansikiyo.value_100)).toNumber())).toNumber();
                            //abura_aihaka += page.detail.checkUndefi(quatity);
                            abura_aihaka = new BigNumber(stringNumbers(abura_aihaka)).plus(stringNumbers(page.detail.checkUndefi(quatity))).toNumber();
                        }

                        //水相合計重量
                        //水相合計単価
                        //【工程 = 殺菌調味液 & 水相】
                        if (zoku_kotei.value2 == App.settings.app.kote_value2.chomiryo || zoku_kotei.value2 == App.settings.app.kote_value2.suisho) {
                            //「量」の水相工程
                            //suiso_no_juryo += val;
                            suiso_no_juryo = new BigNumber(stringNumbers(suiso_no_juryo)).plus(stringNumbers(val)).toNumber();

                            //「量」 × 「単価」 ÷ ( 「歩留」 ÷ 100 ) の 水相の合計
                            //quatity = val * page.detail.checkUndefi(haigo.tanka) / (page.detail.checkUndefi(haigo.budomari) / App.settings.app.keisansikiyo.value_100);
                            quatity = new BigNumber(stringNumbers(val)).times(stringNumbers(page.detail.checkUndefi(haigo.tanka))).div(stringNumbers(new BigNumber(stringNumbers(page.detail.checkUndefi(haigo.budomari))).div(stringNumbers(App.settings.app.keisansikiyo.value_100)).toNumber())).toNumber();
                            //suiso_tanka += page.detail.checkUndefi(quatity);
                            suiso_tanka = new BigNumber(stringNumbers(suiso_tanka)).plus(stringNumbers(page.detail.checkUndefi(quatity))).toNumber();
                        }
                    }
                });

                var elementCol = GenkaShisan_Tab.detail.element.find(".sample-" + item.indexCol),
                    dataGenkaShisaku = elementCol.form().data(),
                    id = elementCol.attr("data-key"),
                    entity = GenkaShisan_Tab.detail.data.entry(id),
                    flg_dblSuisoZyuten = false,
                    flg_dblYusoZyuten = false;

                dblSuisoHiju = page.detail.checkUndefi(dataGenkaShisaku.hiju_sui);//特性値．水相比重

                //調味料2液タイプ、【gram】
                if (data.shisaku_hin.pt_kotei == App.settings.app.pt_kotei.chomieki_2 && data.shisaku_hin.cd_tani == App.settings.app.cd_tani.gram) {
                    //[殺菌調味液重量(g) +  水相合計重量(g) ] X  																									
                    //{ 製品容量(g)  /  [ 殺菌調味液重量(g) + ドレ水相合計重量(g) ) + ドレ油相合計重量(g) ] }			
                    if (checkEvalBeforCalcu([data.shisaku_hin.yoryo])) {
                        //dblSuisoZyuten = (dblSakinGokeiryo + dblSuisoGokeiryo) * (dblSeihinYoryo / ((dblSakinGokeiryo + dblSuisoGokeiryo) + dblYusoGokeiryo));
                        dblSuisoZyuten = new BigNumber(stringNumbers(new BigNumber(stringNumbers(dblSakinGokeiryo)).plus(stringNumbers(dblSuisoGokeiryo)).toNumber())).times(stringNumbers(new BigNumber(stringNumbers(dblSeihinYoryo)).div(stringNumbers(new BigNumber(stringNumbers(dblSakinGokeiryo)).plus(stringNumbers(dblSuisoGokeiryo)).plus(stringNumbers(dblYusoGokeiryo)).toNumber())).toNumber())).toNumber();
                    }
                    flg_dblSuisoZyuten = true;

                    //油相合計重量(g)  X  																								
                    //{ 製品容量(g)  /  [ 殺菌調味液重量(g) + ドレ水相合計重量(g) ) + ドレ油相合計重量(g) ] }			
                    if (checkEvalBeforCalcu([data.shisaku_hin.yoryo])) {
                        //dblYusoZyuten = dblYusoGokeiryo * (dblSeihinYoryo / ((dblSakinGokeiryo + dblSuisoGokeiryo) + dblYusoGokeiryo));
                        dblYusoZyuten = new BigNumber(stringNumbers(dblYusoGokeiryo)).times(stringNumbers(new BigNumber(stringNumbers(dblSeihinYoryo)).div(stringNumbers(new BigNumber(stringNumbers(dblSakinGokeiryo)).plus(stringNumbers(dblSuisoGokeiryo)).plus(stringNumbers(dblYusoGokeiryo)).toNumber())).toNumber())).toNumber();
                    }
                    flg_dblYusoZyuten = true;
                }

                //調味料2液タイプ、【ml】
                if (data.shisaku_hin.pt_kotei == App.settings.app.pt_kotei.chomieki_2 && data.shisaku_hin.cd_tani == App.settings.app.cd_tani.ml) {
                    //[殺菌調味液重量(g) +  水相合計重量(g) ] X  																														
                    //{ 製品容量(ml)  / { [(殺菌調味液重量(g) + ドレ水相合計重量(g) )/水相比重] + [ドレ油相合計重量(g) / 油相比重] } }		
                    if (checkEvalBeforCalcu([data.shisaku_hin.yoryo])) {
                        //dblSuisoZyuten = (dblSakinGokeiryo + dblSuisoGokeiryo) * (dblSeihinYoryo / (((dblSakinGokeiryo + dblSuisoGokeiryo) / dblSuisoHiju) + (dblYusoGokeiryo / dblYusoHiju)));
                        var numCal = new BigNumber(stringNumbers(dblSakinGokeiryo)).plus(stringNumbers(dblSuisoGokeiryo)).toNumber(),
                            numCal1 = new BigNumber(stringNumbers(new BigNumber(stringNumbers(dblSakinGokeiryo)).plus(stringNumbers(dblSuisoGokeiryo)).toNumber())).div(stringNumbers(dblSuisoHiju)).toNumber();

                        numCal1 = new BigNumber(stringNumbers(numCal1)).plus(stringNumbers(new BigNumber(stringNumbers(dblYusoGokeiryo)).div(stringNumbers(dblYusoHiju)).toNumber())).toNumber();
                        dblSuisoZyuten = new BigNumber(stringNumbers(numCal)).times(stringNumbers(new BigNumber(stringNumbers(dblSeihinYoryo)).div(stringNumbers(numCal1)).toNumber())).toNumber();
                    }
                    //dblSuisoZyuten = new BigNumber(stringNumbers(new BigNumber(stringNumbers(dblSakinGokeiryo)).plus(stringNumbers(dblSuisoGokeiryo)).toNumber())).times(stringNumbers(new BigNumber(stringNumbers(dblSeihinYoryo)).div(stringNumbers(new BigNumber(stringNumbers(new BigNumber(stringNumbers(new BigNumber(stringNumbers(dblSakinGokeiryo)).plus(stringNumbers(dblSuisoGokeiryo)).toNumber()))).div(stringNumbers(dblSuisoHiju)).toNumber()).plus(stringNumbers(new BigNumber(stringNumbers(dblYusoGokeiryo)).div(stringNumbers(dblYusoHiju)).toNumber())).toNumber())).toNumber())).toNumber();
                    flg_dblSuisoZyuten = true;

                    //油相合計重量(g)  X  																														
                    //{ 製品容量(ml)  / { [(殺菌調味液重量(g) + ドレ水相合計重量(g) )/水相比重] + [ドレ油相合計重量(g) /油相比重] } }		
                    if (checkEvalBeforCalcu([data.shisaku_hin.yoryo])) {
                        //dblYusoZyuten = dblYusoGokeiryo * (dblSeihinYoryo / (((dblSakinGokeiryo + dblSuisoGokeiryo) / dblSuisoHiju) + (dblYusoGokeiryo / dblYusoHiju)));
                        dblYusoZyuten = new BigNumber(stringNumbers(dblYusoGokeiryo)).times(stringNumbers(new BigNumber(stringNumbers(dblSeihinYoryo)).div(stringNumbers(new BigNumber(stringNumbers(new BigNumber(stringNumbers(new BigNumber(stringNumbers(dblSakinGokeiryo)).plus(stringNumbers(dblSuisoGokeiryo)).toNumber())).div(stringNumbers(dblSuisoHiju)).toNumber())).plus(stringNumbers(new BigNumber(stringNumbers(dblYusoGokeiryo)).div(stringNumbers(dblYusoHiju)).toNumber())).toNumber())).toNumber())).toNumber();
                    }
                    flg_dblYusoZyuten = true;
                }

                //調味料1液タイプ、【ml,g】
                if (data.shisaku_hin.pt_kotei == App.settings.app.pt_kotei.chomieki_1 && (data.shisaku_hin.cd_tani == App.settings.app.cd_tani.ml || data.shisaku_hin.cd_tani == App.settings.app.cd_tani.gram)) {
                    // 製品容量 X 製品比重					
                    //Bug #15230 : 計算に使用する項目が未入力の場合計算対象外として頂けません
                    if (checkEvalBeforCalcu([tokuseichi.hiju, data.shisaku_hin.yoryo])) {
                        //dblSuisoZyuten = dblSeihinYoryo * page.detail.checkUndefi(tokuseichi.hiju);
                        dblSuisoZyuten = new BigNumber(stringNumbers(dblSeihinYoryo)).times(stringNumbers(page.detail.checkUndefi(tokuseichi.hiju))).toNumber();
                    }
                    flg_dblSuisoZyuten = true;
                }

                //充填量水相
                dblSuisoZyuten = page.detail.checkUndefi(dblSuisoZyuten);
                //充填量油相（ｇ）
                dblYusoZyuten = page.detail.checkUndefi(dblYusoZyuten);

                //充填量水相（ｇ）
                if (flg_dblSuisoZyuten) {
                    dblSuisoZyuten = page.detail.formatNumber(dblSuisoZyuten, 2, "0.00");
                    if (!page.stringPointlength(dblSuisoZyuten.forData, { after: 2, max: 15 })) {
                        calculItem = "充填量水相";
                        inputItems = "配合量、製品比重、水相比重"
                        validate = true;
                        return false;
                    }
                    entity["zyusui"] = GenkaShisan_Tab.detail.stringForData(dblSuisoZyuten.forDisp);
                    elementCol.findP("zyusui").val(dblSuisoZyuten.forDisp);
                }
                //充填量油相（ｇ）
                if (flg_dblYusoZyuten) {
                    dblYusoZyuten = page.detail.formatNumber(dblYusoZyuten, 2, "0.00");
                    if (!page.stringPointlength(dblYusoZyuten.forData, { after: 2, max: 15 })) {
                        calculItem = "充填量油相";
                        inputItems = "配合量、製品比重、水相比重"
                        validate = true;
                        return false;
                    }
                    entity["zyuabura"] = GenkaShisan_Tab.detail.stringForData(dblYusoZyuten.forDisp);
                    elementCol.findP("zyuabura").val(dblYusoZyuten.forDisp);
                }

                var //reberu = page.detail.checkUndefi(data.shisaku_hin.yoryo) * page.detail.checkUndefi(data.shisaku_hin.su_iri),//レベル量(g内容量ｘ入数) : 「容量」 × 「入数」
                    reberu = "",//new BigNumber(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.yoryo))).times(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.su_iri))).toNumber(),//レベル量(g内容量ｘ入数) : 「容量」 × 「入数」
                    //gokei = page.detail.checkUndefi(data.shisaku_hin.yoryo) * page.detail.checkUndefi(tokuseichi.hiju),//合計量(１本：g) :「容量」 × 「比重」
                    gokei = "",//new BigNumber(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.yoryo))).times(stringNumbers(page.detail.checkUndefi(tokuseichi.hiju))).toNumber(),//合計量(１本：g) :「容量」 × 「比重」
                    //hizyubudomari = page.detail.checkUndefi(entity.heikinzyu) * page.detail.checkUndefi(tokuseichi.hiju);//比重加算量（ｇ平均充填量ｘ比重）:「平均充填量」 × 「比重」
                    hizyubudomari = "";//new BigNumber(stringNumbers(page.detail.checkUndefi(entity.heikinzyu))).times(stringNumbers(page.detail.checkUndefi(tokuseichi.hiju))).toNumber();//比重加算量（ｇ平均充填量ｘ比重）:「平均充填量」 × 「比重」

                //Bug #15230 : 計算に使用する項目が未入力の場合計算対象外として頂けません
                if (checkEvalBeforCalcu([data.shisaku_hin.yoryo, data.shisaku_hin.su_iri])) {
                    reberu = new BigNumber(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.yoryo))).times(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.su_iri))).toNumber();//レベル量(g内容量ｘ入数) : 「容量」 × 「入数」
                }
                if (checkEvalBeforCalcu([tokuseichi.hiju, data.shisaku_hin.yoryo])) {
                    gokei = new BigNumber(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.yoryo))).times(stringNumbers(page.detail.checkUndefi(tokuseichi.hiju))).toNumber();//合計量(１本：g) :「容量」 × 「比重」
                }
                if (checkEvalBeforCalcu([tokuseichi.hiju, entity.heikinzyu])) {
                    hizyubudomari = new BigNumber(stringNumbers(page.detail.checkUndefi(entity.heikinzyu))).times(stringNumbers(page.detail.checkUndefi(tokuseichi.hiju))).toNumber();//比重加算量（ｇ平均充填量ｘ比重）:「平均充填量」 × 「比重」
                }

                //( （「水相合計単価」 ÷ 「水相合計重量」 × 「充填量水相（ｇ）」 ÷ 1000）
                // ＋ （「油相合計単価」 ÷ 「油相合計重量」 × 「充填量油相（ｇ）」 ÷ 1000) )
                // × 「平均充填量」
                // ÷ 「レベル量(g内容量ｘ入数)」
                // ÷ (「有効歩留」 ÷ 100)
                // × 「入数」
                //next day
                //cs_genryo =
                //    (page.detail.checkUndefi((suiso_tanka / suiso_no_juryo * page.detail.checkUndefi(entity.zyusui) / App.settings.app.keisansikiyo.value_1000))
                //    + page.detail.checkUndefi((abura_aihaka / abura_sho_juryu * page.detail.checkUndefi(entity.zyuabura) / App.settings.app.keisansikiyo.value_1000)))
                //    * page.detail.checkUndefi(entity.heikinzyu)
                //    / reberu
                //    / page.detail.checkUndefi((page.detail.checkUndefi(entity.yukobudomari) / App.settings.app.keisansikiyo.value_100))
                //    * page.detail.checkUndefi(data.shisaku_hin.su_iri);

                //cs_genryo = new BigNumber(stringNumbers(page.detail.checkUndefi(new BigNumber(stringNumbers(suiso_tanka)).div(stringNumbers(suiso_no_juryo)).times(stringNumbers(page.detail.checkUndefi(entity.zyusui))).div(stringNumbers(App.settings.app.keisansikiyo.value_1000)).toNumber()))).plus(stringNumbers(
                //    page.detail.checkUndefi(new BigNumber(stringNumbers(abura_aihaka)).div(stringNumbers(abura_sho_juryu)).times(stringNumbers(page.detail.checkUndefi(entity.zyuabura))).div(stringNumbers(App.settings.app.keisansikiyo.value_1000)).toNumber()))).toNumber()
                //    * page.detail.checkUndefi(entity.heikinzyu)
                //    / reberu
                //    / page.detail.checkUndefi(new BigNumber(stringNumbers(page.detail.checkUndefi(entity.yukobudomari))).div(stringNumbers(App.settings.app.keisansikiyo.value_100)).toNumber())
                //    * page.detail.checkUndefi(data.shisaku_hin.su_iri);

                //（「水相合計単価」 ÷ 「水相合計重量」 × 「充填量水相（ｇ）」 ÷ 1000）
                // ＋ （「油相合計単価」 ÷ 「油相合計重量」 × 「充填量油相（ｇ）」 ÷ 1000) )
                var cs_genryo1 = new BigNumber(stringNumbers(page.detail.checkUndefi(new BigNumber(stringNumbers(suiso_tanka)).div(stringNumbers(suiso_no_juryo)).times(stringNumbers(page.detail.checkUndefi(entity.zyusui))).div(stringNumbers(App.settings.app.keisansikiyo.value_1000)).toNumber()))).plus(stringNumbers(
                    page.detail.checkUndefi(new BigNumber(stringNumbers(abura_aihaka)).div(stringNumbers(abura_sho_juryu)).times(stringNumbers(page.detail.checkUndefi(entity.zyuabura))).div(stringNumbers(App.settings.app.keisansikiyo.value_1000)).toNumber()))).toNumber();
                //(「有効歩留」 ÷ 100)

                var isExitGenryo = false;
                var genryohiGenryo = undefined;
                //Bug #15230 : 計算に使用する項目が未入力の場合計算対象外として頂けません
                if (checkEvalBeforCalcu([data.shisaku_hin.su_iri, entity.yukobudomari, entity.heikinzyu]) && !isExitBlankKotei) {
                    try {
                        isExitGenryo = true;
                        var cs_genryo2 = new BigNumber(stringNumbers(entity.yukobudomari, true)).div(stringNumbers(App.settings.app.keisansikiyo.value_100, true)).toNumber();
                        cs_genryo = new BigNumber(stringNumbers(cs_genryo1, true)).times(stringNumbers(entity.heikinzyu, true)).div(stringNumbers(reberu, true)).div(stringNumbers(cs_genryo2, true)).times(stringNumbers(data.shisaku_hin.su_iri, true)).toNumber();

                        if (cs_genryo == 0) {
                            genryohiGenryo = "0.00";
                        }
                    } catch (ex) {
                        isExitGenryo = false;
                        cs_genryo = "";
                        genryohiGenryo = "";
                    }
                } else {
                    cs_genryo = "";
                    genryohiGenryo = "";
                }
                cs_genryo = page.detail.checkUndefi(cs_genryo);

                //レベル量(g内容量ｘ入数) 
                reberu = page.detail.formatNumber(reberu, 2, "0.00", 100);
                if (!page.stringPointlength(reberu.forData, { after: 2, max: 15 })) {
                    calculItem = "レベル量(g内容量ｘ入数) ";
                    validate = true;
                    return false;
                }
                entity["reberu"] = GenkaShisan_Tab.detail.stringForData(reberu.forDisp);
                elementCol.findP("reberu").val(reberu.forDisp);
                //合計量(１本：g)
                gokei = page.detail.formatNumber(gokei, 3, "0.000", 1000);
                if (!page.stringPointlength(gokei.forData, { after: 3, max: 15 })) {
                    calculItem = "合計量(１本：g) ";
                    validate = true;
                    return false;
                }
                entity["gokei"] = GenkaShisan_Tab.detail.stringForData(gokei.forDisp);
                elementCol.findP("gokei").val(gokei.forDisp);
                //比重加算量（ｇ平均充填量ｘ比重）
                hizyubudomari = page.detail.formatNumber(hizyubudomari, 2, "0.00", undefined, 100);
                if (!page.stringPointlength(hizyubudomari.forData, { after: 2, max: 15 })) {
                    calculItem = "合計量(１本：g) ";
                    validate = true;
                    return false;
                }
                entity["hizyubudomari"] = GenkaShisan_Tab.detail.stringForData(hizyubudomari.forDisp);
                elementCol.findP("hizyubudomari").val(hizyubudomari.forDisp);

                var genryohi1 = "",// 原料費（１本当）:「原料費/ケース」 ÷ 「入数」
                    genryohi = "";//原料費（ｋg) :「原料費/ケース」 ÷（ 「レベル量(g内容量ｘ入数)」×「比重」） × 1000

                if (genryohiGenryo != undefined) {
                    genryohi1 = genryohiGenryo;
                } else if (checkEvalBeforCalcu([data.shisaku_hin.su_iri, entity.yukobudomari])) {
                    //genryohi1 = page.detail.checkUndefi(cs_genryo / page.detail.checkUndefi(data.shisaku_hin.su_iri));
                    genryohi1 = page.detail.checkUndefi(new BigNumber(stringNumbers(cs_genryo)).div(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.su_iri))).toNumber());
                }

                //Bug #15230 : 計算に使用する項目が未入力の場合計算対象外として頂けません
                if (genryohiGenryo != undefined) {
                    genryohi = genryohiGenryo;
                } else if (checkEvalBeforCalcu([tokuseichi.hiju, entity.yukobudomari])) {
                    //genryohi = page.detail.checkUndefi(cs_genryo / (reberu.forData * page.detail.checkUndefi(tokuseichi.hiju)) * App.settings.app.keisansikiyo.value_1000);
                    genryohi = page.detail.checkUndefi(new BigNumber(stringNumbers(cs_genryo)).div(stringNumbers(new BigNumber(stringNumbers(reberu.forData)).times(stringNumbers(page.detail.checkUndefi(tokuseichi.hiju))).toNumber())).times(stringNumbers(App.settings.app.keisansikiyo.value_1000)).toNumber());
                }

                //原料費（１本当）
                genryohi1 = page.detail.formatNumber(genryohi1, 2, genryohiGenryo, 100);
                if (!page.stringPointlength(genryohi1.forData, { after: 2, max: 15 })) {
                    calculItem = "原料費（１本当）";
                    validate = true;
                    return false;
                }
                entity["genryohi1"] = GenkaShisan_Tab.detail.stringForData(genryohi1.forDisp);
                elementCol.findP("genryohi1").val(genryohi1.forDisp);
                //原料費（ｋg)
                genryohi = page.detail.formatNumber(genryohi, 2, genryohiGenryo, 100);
                if (!page.stringPointlength(genryohi.forData, { after: 2, max: 15 })) {
                    calculItem = "原料費（ｋg)";
                    validate = true;
                    return false;
                }
                entity["genryohi"] = GenkaShisan_Tab.detail.stringForData(genryohi.forDisp);
                elementCol.findP("genryohi").val(genryohi.forDisp);

                var cs_genka = 0,//原価計/CS : 「原料費/ケース」 ＋ 「材料費/ケース」 ＋ 「固定費/ケース」
                    ko_genka = "",//原価計/個 : 「原価計/ケース」 ÷ 「入数」
                    ko_riritu = "";//粗利（％）: （ 1 － （ 「原価計/個」 ÷ 「売価」 ) ） × 100

                if (isExitGenryo) {
                    //cs_genka = page.detail.checkUndefi(cs_genryo + page.detail.checkUndefi(entity.cs_zairyohi) + page.detail.checkUndefi(entity.cs_keihi));
                    cs_genka = page.detail.checkUndefi(new BigNumber(stringNumbers(cs_genryo)).plus(stringNumbers(page.detail.checkUndefi(entity.cs_zairyohi))).plus(stringNumbers(page.detail.checkUndefi(entity.cs_keihi))).toNumber());
                }

                if (checkEvalBeforCalcu([data.shisaku_hin.su_iri])) {
                    if (isExitGenryo) {
                        //ko_genka = page.detail.checkUndefi(cs_genka / page.detail.checkUndefi(data.shisaku_hin.su_iri));
                        ko_genka = page.detail.checkUndefi(new BigNumber(stringNumbers(cs_genka)).div(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.su_iri))).toNumber());
                    } else {
                        ko_genka = page.detail.checkUndefi(ko_genka);
                    }

                    if (checkEvalBeforCalcu([data.shisaku_hin.baika]) && isExitGenryo) {
                        //ko_riritu = page.detail.checkUndefi((App.settings.app.keisansikiyo.value_1 - (ko_genka / page.detail.checkUndefi(data.shisaku_hin.baika))) * App.settings.app.keisansikiyo.value_100);
                        ko_riritu = page.detail.checkUndefi(new BigNumber(stringNumbers(new BigNumber(stringNumbers(App.settings.app.keisansikiyo.value_1)).minus(stringNumbers(new BigNumber(stringNumbers(ko_genka)).div(stringNumbers(page.detail.checkUndefi(data.shisaku_hin.baika))).toNumber())).toNumber())).times(stringNumbers(App.settings.app.keisansikiyo.value_100)).toNumber());
                    }
                }

                //１c/s当たりの計算【原料費】
                cs_genryo = page.detail.formatNumber(cs_genryo, 2, (isExitGenryo ? "0.00" : undefined), 100);
                if (!page.stringPointlength(cs_genryo.forData, { after: 2, max: 15 })) {
                    calculItem = "原料費";
                    validate = true;
                    return false;
                }
                entity["cs_genryo"] = GenkaShisan_Tab.detail.stringForData(cs_genryo.forDisp);
                elementCol.findP("cs_genryo").val(cs_genryo.forDisp);
                //原価計/CS 
                cs_genka = page.detail.formatNumber(cs_genka, 2, undefined, 100);
                if (!page.stringPointlength(cs_genka.forData, { after: 2, max: 15 })) {
                    calculItem = "原価計/CS ";
                    validate = true;
                    return false;
                }
                entity["cs_genka"] = GenkaShisan_Tab.detail.stringForData(cs_genka.forDisp);
                elementCol.findP("cs_genka").val(cs_genka.forDisp);
                //原価計/個
                ko_genka = page.detail.formatNumber(ko_genka, 2, undefined, 100);
                if (!page.stringPointlength(ko_genka.forData, { after: 2, max: 15 })) {
                    calculItem = "原価計/個";
                    validate = true;
                    return false;
                }
                entity["ko_genka"] = GenkaShisan_Tab.detail.stringForData(ko_genka.forDisp);
                elementCol.findP("ko_genka").val(ko_genka.forDisp);

                //粗利（％）
                var koRirituFormat = undefined;
                if (cs_genryo.forDisp == "") {
                    ko_riritu = cs_genryo.forDisp;
                    koRirituFormat = cs_genryo.forDisp;
                } else if (!checkEvalBeforCalcu([data.shisaku_hin.baika])) {
                    ko_riritu = "0.00";
                    koRirituFormat = "0.00";
                }

                ko_riritu = page.detail.formatNumber(ko_riritu, 2, koRirituFormat, 100);
                if (!page.stringPointlength(ko_riritu.forData, { after: 2, max: 15 })) {
                    calculItem = "粗利（％）";
                    validate = true;
                    return false;
                }
                entity["ko_riritu"] = GenkaShisan_Tab.detail.stringForData(ko_riritu.forDisp);
                elementCol.findP("ko_riritu").val(ko_riritu.forDisp);

                //原価原料テーブルにデータがなくてもサンプルNoの列は表示されるようになっています。
                if (isNewGenryo == true) {
                    //原料費（ｋg)
                    genryohi.forDisp = "0.00";
                    entity["genryohi"] = genryohi.forDisp;
                    elementCol.findP("genryohi").val(genryohi.forDisp);

                    //原料費（１本当）
                    genryohi1.forDisp = "0.00";
                    entity["genryohi1"] = genryohi1.forDisp;
                    elementCol.findP("genryohi1").val(genryohi1.forDisp);

                    //原価計/個
                    ko_genka.forDisp = "0.00";
                    entity["ko_genka"] = ko_genka.forDisp;
                    elementCol.findP("ko_genka").val(ko_genka.forDisp);

                    //原価計/CS 
                    cs_genka.forDisp = "0.00";
                    entity["cs_genka"] = cs_genka.forDisp;
                    elementCol.findP("cs_genka").val(cs_genka.forDisp);

                    //１c/s当たりの計算【原料費】
                    cs_genryo.forDisp = "0.00";
                    entity["cs_genryo"] = cs_genryo.forDisp;
                    elementCol.findP("cs_genryo").val(cs_genryo.forDisp);
                }

                GenkaShisan_Tab.detail.data.update(entity);

                //原料費（ｋｇ）
                //原料費（１個）
                if (!genryohiTotal[".sample-" + item.indexCol]) {
                    genryohiTotal[".sample-" + item.indexCol] = {};
                }
                //原料費（ｋｇ）
                genryohiTotal[".sample-" + item.indexCol]["genryohi"] = genryohi.forDisp;
                //
                //原料費（１個）
                genryohiTotal[".sample-" + item.indexCol]["genryohi1"] = genryohi1.forDisp;
            })
        }
        GenkaShisan_Tab.values.messTarget = GenkaShisan_Tab.detail.element.find(".new .th-tmpl .cancel-irai-sample");
        App.ui.page.notifyAlert.remove(GenkaShisan_Tab.values.messTarget);
        //計算結果は桁数がオーバーの場合、メッセージ表示。		
        if (validate) {
            GenkaShisan_Tab.values.messCalculator = App.str.format(App.messages.app.AP0009, { calculItem: calculItem, maxChar: 15, inputItems: inputItems });
            App.ui.page.notifyAlert.message(GenkaShisan_Tab.values.messCalculator, GenkaShisan_Tab.values.messTarget).show();
            App.ui.loading.close();
            return;
        } else {
            GenkaShisan_Tab.values.messCalculator = null;
            GenkaShisan_Tab.values.messTarget = null;
        }

        //原料費を更新する
        if (App.isFunc(GenkaShisan_Tab.detail.updateOtherTab)) {
            GenkaShisan_Tab.detail.updateOtherTab("haigohyo_tab", "genryohiTotal", genryohiTotal);
        }
        App.ui.loading.close();
    }

    /**
    * 殺菌調味液（リテラルマスタのvalue1 =1  AND value2 = 1）の「量」の合計　
    */
    GenkaShisan_Tab.detail.findKoteiZokusei = function (haigo, val, val2) {
        var check = jQuery.grep(page.values.masterData.KoteiZokusei, function (n, i) {
            return (n.cd_literal == haigo.zoku_kotei && n.value1 == App.settings.app.kote_value1.chomiryoType && n.value2 == val2);
        });

        if (check.length) {
            return val;
        } else {
            return 0;
        }
    }

    /**
    * event change sample name
    */
    GenkaShisan_Tab.detail.changeSampleName = function (data) {
        var element = GenkaShisan_Tab.element;

        element.find(".sample-" + data.seq_shisaku + " .nm_sample").text(data.nm_sample);
    };

    /**
     * get change set this tab。
     */
    GenkaShisan_Tab.getChangeSetTab = function () {

        return GenkaShisan_Tab.detail.getIraiChecked().then(function (iraichecked) {
            return {
                tr_genryo: GenkaShisan_Tab.detail.data.getChangeSet(),
                tr_shisaku: GenkaShisan_Tab.detail.dataSample,
                iraichecked: iraichecked.checkList,
                cancel: iraichecked.cancel
            }
        })
    };

    /**
    * 原価試作依頼されている列がある場合、担当会社変更できないこと
    */
    GenkaShisan_Tab.detail.checkIraiChangKaisha = function () {
        var element = GenkaShisan_Tab.detail.element.find(".new"),
            checked = element.find(".flg_shisanIrai:checked:disabled"),
            checkLenght = 0;

        if (!checked.length) {
            return checkLenght;
        }

        checked.each(function (index, item) {
            var check = {}, idsample, cancel
            sample = element.find(item).closest("td");

            if (sample.hasClass("th-tmpl") || !sample.length) {
                return true;
            }

            idsample = sample.attr("data-key-sample");
            cancel = element.find("." + idsample).find(".cancel-irai-sample");

            if (!cancel[0].checked) {
                checkLenght = 1;
                return false;
            }
        })

        return checkLenght;
    };

    /**
    * 原価試算．依頼キャンセルがチェックあるサンプルNoに対して。
    */
    GenkaShisan_Tab.detail.getIraiChecked = function () {
        var deferred = $.Deferred();

        var element = GenkaShisan_Tab.detail.element.find(".new"),
            checked = element.find(".flg_shisanIrai:checked"),
            checkList = [], nm_sample = [];

        var entity, id, sample, idsample;
        checked.each(function (index, item) {
            var check = {};
            sample = element.find(item).closest("td");

            if (sample.hasClass("th-tmpl") || !sample.length) {
                return true;
            }
            //flag disabled irai
            check.flg_disabled = sample.find("input")[0].disabled;


            id = sample.attr("data-key");
            idsample = sample.attr("data-key-sample");

            if (App.isUndefOrNull(id) || App.isUndefOrNull(idsample)) {
                return true;
            }
            entity = GenkaShisan_Tab.detail.data.entry(id);

            //flag seq_shisaku
            check.seq_shisaku = entity.seq_shisaku;

            sample = element.find("." + idsample);

            //flag cancel
            check.flg_cancel = sample.find(".cancel-irai-sample").is(":checked");

            if (check.flg_cancel) {
                nm_sample.push(App.str.format("【{0}】", entity.nm_sample != null ? entity.nm_sample : ""));
            }
            checkList.push(check);
        });

        deferred.resolve({ checkList: checkList, cancel: nm_sample });
        return deferred.promise();
    }

    /**
     * ダイアログコントロールへのイベントの紐づけを行います。
     */
    GenkaShisan_Tab.initializeControlEvent = function () {
        var element = GenkaShisan_Tab.element;

        //element.find("#ShisanKakutei").on("click", GenkaShisan_Tab.kakutei);

        //TODO: 画面全体で利用するコントロールのイベントの紐づけ処理をここに記述します。
        //element.on("hidden.bs.modal", GenkaShisan_Tab.hidden);
        //element.on("shown.bs.modal", GenkaShisan_Tab.shown);
        //element.on("click", ".save", GenkaShisan_Tab.commands.save);
    };

    ///**
    // * 原価試算登録。
    // */
    //GenkaShisan_Tab.kakutei = function () {
    //    var shisahin = GenkaShisan_Tab.getDataHeader(),
    //        shisa = GenkaShisan_Tab.element.findP("shisanKakutei"),
    //        paramKakutei = {
    //            cd_kaisha: shisahin.cd_kaisha,
    //            nen: shisahin.nen,
    //            cd_shain: shisahin.cd_shain,
    //            no_oi: shisahin.no_oi,
    //            seq_shisaku: shisa.val(),
    //            nm_sample: shisa.find("option:selected").text(),
    //            EmployeeCD: App.ui.page.user.EmployeeCD
    //        };

    //    App.ui.page.notifyAlert.clear();

    //    return $.ajax(App.ajax.webapi.post(GenkaShisan_Tab.urls.search, paramKakutei)).then(function (result) {
    //        ////* 画面．試算日 = システム日付
    //        ////* 画面．試算確定 = 登録を行ったサンプルNo
    //        //if (App.isFunc(GenkaShisan_Tab.detail.updateOtherTab)) {
    //        //    GenkaShisan_Tab.detail.updateOtherTab("header_kakutei", "", result);
    //        //}

    //        //登録が成功した場合	・「正常に原価試算登録処理が完了しました。(AP0043)」メッセージを表示
    //        App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0043, { strName: "原価試算登録" })).show();
    //    }).fail(function (error) {
    //        App.ui.page.notifyAlert.clear();
    //        if (error.status === 400 && error.responseJSON == "ma_saiban") {
    //            App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0042, { strName: "原価試算 試算履歴自動採番" })).show();
    //            return;
    //        }

    //        if (error.status === 400 && error.responseJSON == "tr_shisan_rireki") {
    //            App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0083, { strShisakuRetu: paramKakutei.seq_shisaku, strSampleNo: paramKakutei.nm_sample })).show();
    //            return;
    //        }

    //        //登録が失敗した場合、エラーメッセージを表示.「原価試算登録処理が失敗しました。(AP0042)」	
    //        App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0042, { strName: "原価試算登録" })).show();
    //    })
    //}

    /**
     * ダイアログ非表示時処理を実行します。
     */
    //GenkaShisan_Tab.hidden = function (e) {
    //    var element = GenkaShisan_Tab.element;

    //    //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
    //    element.find(":input:not([type='checkbox']):not([type='radio'])").val("");
    //    element.find("input[type='checkbox']").prop("checked", false);
    //    //TODO:radioボタンの初期表示は、画面要件に合わせて記述します。

    //    GenkaShisan_Tab.detail.dataTable.dataTable("clear");

    //    element.findP("data_count").text("");
    //    element.findP("data_count_total").text("");

    //    var items = element.find(".search-criteria :input:not(button)");
    //    for (var i = 0; i < items.length; i++) {
    //        var item = items[i];
    //        GenkaShisan_Tab.setColValidStyle(item);
    //    }

    //    GenkaShisan_Tab.element.find(".save").prop("disabled", true);
    //    GenkaShisan_Tab.notifyInfo.clear();
    //    GenkaShisan_Tab.notifyAlert.clear();
    //};

    /**
     * ダイアログ表示時処理を実行します。
     */
    //GenkaShisan_Tab.shown = function (e) {
    //    //初回起動時にdatatable作成
    //    if (App.isUndefOrNull(GenkaShisan_Tab.detail.fixedColumnIndex)) {
    //        GenkaShisan_Tab.detail.initialize();
    //    }

    //    GenkaShisan_Tab.element.find(":input:not(button):first").focus();
    //};

    /**
     * マスターデータのロード処理を実行します。
     */
    GenkaShisan_Tab.detail.loadData = function () {

        GenkaShisan_Tab.detail.bind({});

        //mode edit
        if (page.values.modeDisp == App.settings.app.m_shisaku_data.shosai
            || page.values.modeDisp == App.settings.app.m_shisaku_data.etsuran
                    || page.values.modeDisp == App.settings.app.m_shisaku_data.copy) {
            GenkaShisan_Tab.detail.search();
        }

        //試算確定サンプルＮｏ
        GenkaShisan_Tab.detail.loadDataShisanKakutei([]);
    };

    /**
    * 試算確定サンプルＮｏ
    */
    GenkaShisan_Tab.detail.loadDataShisanKakutei = function (data) {
        var shisan = GenkaShisan_Tab.detail.element.findP("shisanKakutei");
        shisan.children().remove();
        App.ui.appendOptions(
            shisan,
            "seq_shisaku",
            "nm_sample",
            data
        );
    }

    /**
     * ダイアログの検索処理を実行します。
     */
    GenkaShisan_Tab.detail.search = function () {
        var element = GenkaShisan_Tab.element,
            deferred = $.Deferred(),
            query;

        query = {
            cd_shain: page.values.cd_shain,
            nen: page.values.nen,
            no_oi: page.values.no_oi
        };


        $.ajax(App.ajax.webapi.get(GenkaShisan_Tab.urls.search, query)).done(function (result) {

            GenkaShisan_Tab.detail.bindSample(result);
            deferred.resolve();
        }).fail(function (error) {
            App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            deferred.reject();
        });

        return deferred.promise();
    };

    /**
     * ダイアログヘッダー　バリデーションルールを定義します。
     */
    GenkaShisan_Tab.detail.options.validations = {
        zyusui: {
            rules: {
                numberBug15481: true,
                pointlength_custom: function (value, param, otps, done) {

                    var check = page.detail.formatNumber(page.detail.checkUndefi(value), 2, undefined, 100);

                    check = page.stringPointlength(check.forDisp, { after: 2, max: 15 });

                    return done(check);
                },
                min: 0
            },
            options: {
                name: "充填量水相", ketasu1: "15", ketasu2: "12", ketasu3: "2"
            },
            messages: {
                numberBug15481: App.messages.base.number,
                pointlength_custom: App.messages.app.AP0044,
                min: App.messages.base.min
            }
        },
        zyuabura: {
            rules: {
                numberBug15481: true,
                pointlength_custom: function (value, param, otps, done) {

                    var check = page.detail.formatNumber(page.detail.checkUndefi(value), 2, undefined, 100);

                    check = page.stringPointlength(check.forDisp, { after: 2, max: 15 });

                    return done(check);
                },
                min: 0
            },
            options: {
                name: "充填量油相", ketasu1: "15", ketasu2: "12", ketasu3: "2"
            },
            messages: {
                numberBug15481: App.messages.base.number,
                pointlength_custom: App.messages.app.AP0044,
                min: App.messages.base.min
            }
        },
        yukobudomari: {
            rules: {
                numberBug15481: true,
                pointlength_custom: function (value, param, otps, done) {
                    var check = page.detail.formatNumber(page.detail.checkUndefi(value), 2, undefined, 100);

                    check = page.stringPointlength(check.forDisp, { after: 2, max: 15 });

                    return done(check);
                },
                min: 0,
                //ShohinKaihatsuSuppot Modify 2022FY: -st required when open dialog 606
                check_required_from_606_dialog: function (value, param, otps, done) {

                    if (!otps.is606) {
                        return done(true);
                    }

                    return done(!App.isUndefOrNullOrStrEmpty(value));
                }
                //-ed
            },
            options: {
                name: "有効歩留", ketasu1: "15", ketasu2: "12", ketasu3: "2"
            },
            messages: {
                numberBug15481: App.messages.base.number,
                pointlength_custom: App.messages.app.AP0044,
                min: App.messages.base.min,
                check_required_from_606_dialog: App.messages.base.required
            }
        },
        heikinzyu: {
            rules: {
                numberBug15481: true,
                pointlength_custom: function (value, param, otps, done) {

                    var check = page.detail.formatNumber(page.detail.checkUndefi(value), 2, undefined, 100);

                    check = page.stringPointlength(check.forDisp, { after: 2, max: 15 });

                    return done(check);
                },
                min: 0,
                //ShohinKaihatsuSuppot Modify 2022FY: -st required when open dialog 606
                check_required_from_606_dialog: function (value, param, otps, done) {

                    if (!otps.is606) {
                        return done(true);
                    }

                    return done(!App.isUndefOrNullOrStrEmpty(value));
                }
                //-ed
            },
            options: {
                name: "平均充填量", ketasu1: "15", ketasu2: "12", ketasu3: "2"
            },
            messages: {
                numberBug15481: App.messages.base.number,
                pointlength_custom: App.messages.app.AP0044,
                min: App.messages.base.min,
                check_required_from_606_dialog: App.messages.base.required
            }
        },
        cs_zairyohi: {
            rules: {
                numberBug15481: true,
                pointlength_custom: function (value, param, otps, done) {

                    var check = page.detail.formatNumber(page.detail.checkUndefi(value), 2, undefined, 100);

                    check = page.stringPointlength(check.forDisp, { after: 2, max: 15 });

                    return done(check);
                },
                min: 0
            },
            options: {
                name: "材料費", ketasu1: "15", ketasu2: "12", ketasu3: "2"
            },
            messages: {
                numberBug15481: App.messages.base.number,
                pointlength_custom: App.messages.app.AP0044,
                min: App.messages.base.min
            }
        },
        cs_keihi: {
            rules: {
                numberBug15481: true,
                pointlength_custom: function (value, param, otps, done) {

                    var check = page.detail.formatNumber(page.detail.checkUndefi(value), 2, undefined, 100);

                    check = page.stringPointlength(check.forDisp, { after: 2, max: 15 });

                    return done(check);
                },
                min: 0
            },
            options: {
                name: "固定費", ketasu1: "15", ketasu2: "12", ketasu3: "2"
            },
            messages: {
                numberBug15481: App.messages.base.number,
                pointlength_custom: App.messages.app.AP0044,
                min: App.messages.base.min
            }
        },
    };

    /**
     * 画面明細の初期化処理を行います。
     */
    GenkaShisan_Tab.detail.initialize = function () {

        var element = GenkaShisan_Tab.element,
            table = element.find(".datatable"),
                datatable = table.dataTable({
                    height: 100,
                    resize: true,
                    fixedColumn: true,
                    fixedColumns: 1,
                    innerWidth: 0,
                    resizeOffset: 60,
                    //onselect: GenkaShisan_Tab.detail.select,
                    onchange: GenkaShisan_Tab.detail.change
                });
        table = element.find(".datatable");        //列固定にした場合DOM要素が再作成されるため、変数を再取得

        //2019-09-10 : START : Bug #15319 選択行の色
        table.on("click", "td", function (e) {
            if ($(e.target).parents().hasClass("col-sample") || $(e.target).hasClass("col-sample")) {
                table.find(".selectedInput").removeClass("selectedInput");

                if (e.target.type == "checkbox") {
                    $(e.target).parents("td").addClass("selectedInput");
                    return;
                }

                $(e.target).addClass("selectedInput");
            }
        });

        table.on("focusin", "td", function (e) {
            table.find(".selectedInput").removeClass("selectedInput");
            if (e.target.type == "checkbox") {
                $(e.target).parents("td").addClass("selectedInput");
                return;
            }
            $(e.target).addClass("selectedInput");
        });
        //2019-09-10 : END : Bug #15319 選択行の色

        GenkaShisan_Tab.detail.validator = element.validation(GenkaShisan_Tab.createValidator(GenkaShisan_Tab.detail.options.validations));
        GenkaShisan_Tab.detail.element = element;
        GenkaShisan_Tab.detail.dataTable = datatable;

        ////Define manual area
        var element_head_sample = element.find('.dt-container .flow-container .dt-body .dt-vscroll .datatable .new tr:eq(0)');
        GenkaShisan_Tab.detail.element_head_sample = element_head_sample;

        //element.on("dblclick", ".memo", page.detail.tokucyo.showMemoDialog);

        // 行選択時に利用するテーブルインデックスを指定します
        GenkaShisan_Tab.detail.fixedColumnIndex = element.find(".fix-columns").length;

        //TODO: 画面明細で利用するコントロールのイベントの紐づけ処理をここに記述します。
        //element.on("click", "#seizo-kotei", page.detail.showSeizhoKouteiDialog);
        //element.on("dblclick", ".datatable tbody", page.detail.showIroSettingDialog);

        //選択した試作列のスクロールバーの位置情報を保持し、他のタブへ移動後、スクロールバーの位置を保持していた位置に移動する
        $("#GenkaShisan_Tab .dt-container .flow-container .dt-body").scroll(function () {
            if (GenkaShisan_Tab.detail.values.offscroll) {
                page.values.positionLeft = $(this).scrollLeft();
            }
        });

        table.on("click", "tr", GenkaShisan_Tab.detail.select);
    };

    //選択した試作列のスクロールバーの位置情報を保持し、他のタブへ移動後、スクロールバーの位置を保持していた位置に移動する
    $('#genkashisan_tab').on('shown.bs.tab', function (e) {
        GenkaShisan_Tab.detail.values.offscroll = false;
        var scrollLeft = $("#GenkaShisan_Tab .dt-container .flow-container .dt-body").scrollLeft();
        if (scrollLeft != page.values.positionLeft) {
            $("#GenkaShisan_Tab .dt-container .flow-container .dt-body").scrollLeft(page.values.positionLeft);
        }

        setTimeout(function () {
            GenkaShisan_Tab.detail.values.offscroll = true;
        }, 1)
    })

    /**
    * move column data table
    */
    GenkaShisan_Tab.detail.moveColumn = function (table, from, to) {
        var cols;
        jQuery('tr', table).each(function () {
            cols = jQuery(this).children('th, td');
            cols.eq(from).detach().insertBefore(cols.eq(to));
        });
    }

    /**
    * move sample to left
    */
    GenkaShisan_Tab.detail.moveSample = function (index, isRight) {
        var element = GenkaShisan_Tab.detail.element,
            dt_head = element.find(".new").find("tr:first"),
            head_check = dt_head.find(".col-sample"),
            selectCol = dt_head.find(".sample-" + index);

        var table = element.find(".dt-container .flow-container table"),
            iColMove = head_check.index(selectCol);

        if (iColMove == 0) {
            return;
        }

        //move column
        if (isRight) {
            GenkaShisan_Tab.detail.moveColumn(table, iColMove + 1, iColMove);
        } else {
            GenkaShisan_Tab.detail.moveColumn(table, iColMove, iColMove - 1);
        }
    };

    /**
    * del col with data from haigo tab。
    */
    GenkaShisan_Tab.detail.delSample = function (data) {
        var dt_head = GenkaShisan_Tab.detail.element_head_sample.find(".sample-" + data.seq_shisaku),
            element = GenkaShisan_Tab.detail.element.find(".sample-" + data.seq_shisaku),
            id = dt_head.attr("data-key"),
            entity;

        //remove mess error
        element.find(".has-error, .error").each(function (i, elem) {
            App.ui.page.notifyAlert.remove(elem);
        });

        if (App.isUndefOrNull(id)) {
            element.remove();

            //テーブルでサイズ変更
            GenkaShisan_Tab.detail.adjustWithTable(0);
            return;
        }

        entity = GenkaShisan_Tab.detail.data.entry(id);
        GenkaShisan_Tab.detail.data.remove(entity);

        element.remove();

        //テーブルでサイズ変更
        GenkaShisan_Tab.detail.adjustWithTable(0);

        //init data change for sample
        if (GenkaShisan_Tab.detail.dataSample["sample-" + data.seq_shisaku]) {
            delete GenkaShisan_Tab.detail.dataSample["sample-" + data.seq_shisaku];
        }
    };

    /**
    * 新しいサンプル列を追加する。
    */
    GenkaShisan_Tab.detail.addSample = function (item, isNewData, isEndCol, indexCol) {
        var element = GenkaShisan_Tab.detail.element,
            dt_head = GenkaShisan_Tab.detail.element_head_sample,
            head_check = element.find('.th-tmpl'),
            iColCopy = 0, iColInset = 0;

        if (head_check.length == 0) {
            return;
        }

        //比重
        if (isNewData && item.hiju == null) {
            item.hiju = "0.000";
        }
        var table = element.find(".dt-container .flow-container table"),
            cols;


        if (isEndCol == true) {
            iColInset = dt_head.find(".col-sample").length - 1;
        }

        if (indexCol) {
            head_check = dt_head.find("input");
            indexCol = dt_head.find(".sample-" + indexCol).find("input");
            iColInset = head_check.index(indexCol);
        }

        jQuery('tr', table).each(function () {
            cols = jQuery(this).children('th, td');
            colsClone = cols.eq(iColCopy).clone().removeClass("th-tmpl");
            colsClone.addClass("sample-" + item.seq_shisaku).attr("data-key-sample", "sample-" + item.seq_shisaku);

            colsClone.insertAfter(cols.eq(iColInset));
        });

        (isNewData ? GenkaShisan_Tab.detail.data.add : GenkaShisan_Tab.detail.data.attach).bind(GenkaShisan_Tab.detail.data)(item);
        //bind data for col
        element = element.find(".sample-" + item.seq_shisaku);
        element.form(GenkaShisan_Tab.detail.options.bindOption).bind(item);

        //原価試算依頼
        element.findP("flg_shisanIrai").prop("disabled", item.flg_shisanIrai == GenkaShisan_Tab.values.flg_shisanIrai);
        if (item.flg_shisanIrai == GenkaShisan_Tab.values.flg_shisanIrai) {
            element.findP("flg_shisanIrai").closest("td").removeClass("enableColor");
        }

        //原価試作依頼されている原料を含む行（配合量に値が入っている行）は変更できない
        if (item.flg_shisanIrai) {
            //element.find(":input").prop("disabled", true);
            element.find(".zyusui-input, .zyuabura-input").addClass("shisanIraiked");
        }

        //依頼キャンセル
        element.find(".cancel-irai-sample").prop("disabled", item.flg_shisanIrai != GenkaShisan_Tab.values.flg_shisanIrai);
        if (item.flg_shisanIrai != GenkaShisan_Tab.values.flg_shisanIrai) {
            element.find(".cancel-irai-sample").closest("td").removeClass("enableColor");
        }

        //原価試算の有効歩留、平均充填量は一番左側にある列からコピーする
        if (isNewData && page.values.modeDisp != App.settings.app.m_shisaku_data.copy) {
            GenkaShisan_Tab.detail.copyBudomaJutenryo(element, item);
        }
    };

    /**
     * 原価試算の有効歩留、平均充填量は一番左側にある列からコピーする
     */
    GenkaShisan_Tab.detail.copyBudomaJutenryo = function (sampleNew, item) {
        var element = GenkaShisan_Tab.detail.element,
            keySample = element.find('.new .th-tmpl').next().attr("data-key-sample"),
            element = element.find(".new ." + keySample);

        //有効歩留（％）（※）
        var yukobudomari = element.findP("yukobudomari").val();
        sampleNew.findP("yukobudomari").val(yukobudomari);
        item["yukobudomari"] = stringNumbers(yukobudomari, true);

        //平均充填量（ｇ）（※）
        var heikinzyu = element.findP("heikinzyu").val();
        sampleNew.findP("heikinzyu").val(heikinzyu);
        item["heikinzyu"] = stringNumbers(heikinzyu, true);

        GenkaShisan_Tab.detail.data.update(item);

        sampleNew.find(":input").prop("disabled", false);

        //印刷FG, 原価試算依頼
        sampleNew.find(".flg_print, .flg_shisanIrai").prop("checked", false).prop("disabled", false);

        //依頼キャンセル
        sampleNew.find(".cancel-irai-sample").prop("checked", false).prop("disabled", true);
    }

    /**
     * resize with table change column.
     */
    GenkaShisan_Tab.detail.adjustWithTable = function (col) {

        var element = GenkaShisan_Tab.detail.element,
            theadTable = element.find(".dt-container .flow-container .dt-head .datatable"),
            theadWith = theadTable.css("width").replace("px", ""),
            bodyTable = element.find(".dt-container .flow-container .dt-body .datatable");

        theadTable.css("width", (parseFloat(theadWith) + (135 * col)) - 135 + "px");
        theadTable.css("max-width", (parseFloat(theadWith) + (135 * col)) - 135 + "px");
        bodyTable.css("width", (parseFloat(theadWith) + (135 * col)) - 135 + "px");
        bodyTable.css("max-width", (parseFloat(theadWith) + (135 * col)) - 135 + "px");
    }

    /**
    * ダイアログ明細へのデータバインド処理を行います。
    */
    GenkaShisan_Tab.detail.bind = function (data, isNewData) {
        var i, l, item, dataSet, dataCount;

        //init data change for sample
        GenkaShisan_Tab.detail.dataSample = {};

        dataSet = App.ui.page.dataSet();
        GenkaShisan_Tab.detail.data = dataSet;
        GenkaShisan_Tab.detail.dataTable.dataTable("clear");

        GenkaShisan_Tab.detail.dataTable.dataTable("addRow", function (row) {
            return row;
        }, true);

        //Define manual area
        var element_head_sample = GenkaShisan_Tab.detail.element.find('.dt-container .flow-container .dt-body .datatable .new tr:eq(0)');
        GenkaShisan_Tab.detail.element_head_sample = element_head_sample;
    };

    /**
    * bind all sample。
    */
    GenkaShisan_Tab.detail.bindSample = function (data) {

        //原価原料テーブルにデータがなくてもサンプルNoの列は表示されるようになっています。
        page.detail.tabs.genkashisan_tab.detail.values.isNewGenryo = [];

        var i, l, item, dataSet, dataCount;

        data = (data.genryo.Items) ? data.genryo.Items : data.genryo;
        dataCount = data.length ? data.length : 0;

        while (dataCount > 0) {

            dataCount = dataCount - 1;
            GenkaShisan_Tab.detail.addSample(data[dataCount], data[dataCount].isNew == true);

            //原価原料テーブルにデータがなくてもサンプルNoの列は表示されるようになっています。
            if (data[dataCount].isNew == true) {
                page.detail.tabs.genkashisan_tab.detail.values.isNewGenryo.push(data[dataCount].seq_shisaku);
            }
        }
        //2019-12-12 : Bug #16120 : 現行仕様に合わせて都度計算して表示するようお願い致します。
        page.detail.values.isCompleteGenka = true;

        //テーブルでサイズ変更
        GenkaShisan_Tab.detail.adjustWithTable(data.length);

        //試算確定サンプルＮｏ
        GenkaShisan_Tab.detail.loadDataShisanKakutei(data);

        //充填量水相（g）（※）,充填量油相
        GenkaShisan_Tab.detail.controlWithKoteiPatan();
    };

    /**
     *充填量水相（g）（※）と充填量油相（g）（※）は表示をチェックする。
     */
    GenkaShisan_Tab.detail.controlWithKoteiPatan = function () {
        var element = GenkaShisan_Tab.detail.element.find(".new"),
            shisakuHin = GenkaShisan_Tab.getDataHeader();

        //編集不可（初期値：0.00）
        element.find(".zyusui-input, .zyuabura-input").not(".shisanIraiked").prop('readonly', true);

        //その他・加食タイプ【blank】,【ｇ】
        if (shisakuHin.pt_kotei == App.settings.app.pt_kotei.sonohokaeki && (!shisakuHin.cd_tani || shisakuHin.cd_tani == App.settings.app.cd_tani.gram)) {
            element.find(".zyusui-input, .zyuabura-input").not(".shisanIraiked").prop('readonly', false);
        }
    }

    /**
     * 明細の各行にデータを設定する際のオプションを定義します。
     */
    GenkaShisan_Tab.detail.options.bindOption = {
        // TODO: 主キーが直接入力の場合には、修正の場合変更を不可とします。
        appliers: {
            //充填量水相（g）（※）
            zyusui: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 2).forDisp);
                return true;
            },
            //充填量油相（g）（※）
            zyuabura: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 2).forDisp);
                return true;
            },
            //合計量（1本：g）（容量ｘ比重）
            gokei: function (value, element) {
                if (value == 0) {
                    element.val("0.000");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 3).forDisp);
                return true;
            },
            //原料費(kg)
            genryohi: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 2).forDisp);
                return true;
            },
            //原料費(１本当)
            genryohi1: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 2).forDisp);
                return true;
            },
            //比重
            hiju: function (value, element) {
                if (value == 0) {
                    element.val("0.000");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 3).forDisp);
                return true;
            },
            hiju_sui: function (value, element) {
                if (value == 0) {
                    element.val("0.000");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 3).forDisp);
                return true;
            },
            //容量
            yoryo: function (value, element) {
                //2020年度改修案件
                //2020/06/17 : START : 「容量（数値入力）（※）*」項目に少数1桁まで入力可能変更
                //element.val(page.detail.formatNumber(value, null).forDisp);
                element.val(page.detail.formatNumber(value, 1).forDisp);
                //2020/06/17 : END : 「容量（数値入力）（※）*」項目に少数1桁まで入力可能変更
                return true;
            },
            //入り数
            irisu: function (value, element) {
                element.val(page.detail.formatNumber(value, null).forDisp);
                return true;
            },
            //有効歩留（％）（※）
            yukobudomari: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 2).forDisp);
                return true;
            },
            //レベル量（ｇ内容量ｘ入数）
            reberu: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 2).forDisp);
                return true;
            },
            //比重加算量（ｇ平均充填量ｘ比重）
            hizyubudomari: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 2).forDisp);
                return true;
            },
            //平均充填量（g）（※）
            heikinzyu: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 2).forDisp);
                return true;
            },
            //原料費
            cs_genryo: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 2).forDisp);
                return true;
            },
            //材料費（※）
            cs_zairyohi: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 2).forDisp);
                return true;
            },
            //固定費（※）
            cs_keihi: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 2).forDisp);
                return true;
            },
            //原価計/CS
            cs_genka: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 2).forDisp);
                return true;
            },
            //原価計/個
            ko_genka: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 2).forDisp);
                return true;
            },
            //売価
            ko_baika: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 2).forDisp);
                return true;
            },
            //粗利（％）
            ko_riritu: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 2).forDisp);
                return true;
            }
        }
    };

    /**
     * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
     */
<%--        GenkaShisan_Tab.detail.selectData = function (e) {
            var target = $(e.target),
                id, entity,
                selectData = function (entity) {
                    if (App.isFunc(GenkaShisan_Tab.dataSelected)) {
                        if (!GenkaShisan_Tab.dataSelected(entity)) {
                            GenkaShisan_Tab.element.modal("hide");
                        }
                    } else {
                        GenkaShisan_Tab.element.modal("hide");
                    }
                };

            GenkaShisan_Tab.detail.dataTable.dataTable("getRow", target, function (row) {
                id = row.element.attr("data-key");
                entity = GenkaShisan_Tab.detail.data.entry(id);
            });

            if (App.isUndef(id)) {
                return;
            }
            if (GenkaShisan_Tab.detail.data.isUpdated(id)) {
                var options = {
                    text: App.messages.base.MS0024
                };
                GenkaShisan_Tab.dialogs.confirmDialog.confirm(options)
                    .then(function () {
                        selectData(entity);
                    });
            } else {
                selectData(entity);
            }
        }; --%>

    /**
     * 明細の一覧の行が選択された時の処理を行います。
     */
    GenkaShisan_Tab.detail.select = function (e, row) {
        if (!$(this).attr("data-index-tr")) {
            return;
        }

        GenkaShisan_Tab.detail.element.find(".selected-row").removeClass("selected-row");
        GenkaShisan_Tab.detail.element.find(".data-index-tr-" + $(this).attr("data-index-tr")).addClass("selected-row");
        //$($(row.element[GenkaShisan_Tab.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab.selected")).removeClass("selected").addClass("unselected");
        //$(row.element[GenkaShisan_Tab.detail.fixedColumnIndex].querySelectorAll(".select-tab")).removeClass("unselected").addClass("selected");

        //選択行全体に背景色を付ける場合は以下のコメントアウトを解除します。
        //if (!App.isUndefOrNull(GenkaShisan_Tab.detail.selectedRow)) {
        //    GenkaShisan_Tab.detail.selectedRow.element.find(".selected-row").removeClass("selected-row");
        //}
        //row.element.find("tr").addClass("selected-row");
        //GenkaShisan_Tab.detail.selectedRow = row;
    };

    /**
    * 配合タブで原価試算依頼コントロールクラス
    */
    GenkaShisan_Tab.detail.checkShisanIrai = function (id, target, property) {
        if (target.is(":enabled")) {
            //原料費を更新する
            if (App.isFunc(GenkaShisan_Tab.detail.updateOtherTab)) {
                //配合表
                GenkaShisan_Tab.detail.updateOtherTab("haigohyo_tab", property, target[0].checked, id);
                //特性値
                GenkaShisan_Tab.detail.updateOtherTab("tokuseichi_tab", property, target[0].checked, id);
            }
        }
    }

    GenkaShisan_Tab.detail.convertNull = function (num, convert) {
        if (convert == null || convert == "") {
            return null;
        }
        return num;
    };

    GenkaShisan_Tab.detail.stringForData = function (string) {
        if (string == null || string == "") {
            return null;
        }
        return string.replace(/,/g, "");
    };


    /**
     * 明細の一覧の入力項目の変更イベントの処理を行います。
     */
    GenkaShisan_Tab.detail.change = function (e, row) {
        var target = $(e.target),
            id = target.closest("td").attr("data-key"),
            property = target.attr("data-prop"),
            entity, old_data = null;

        if (App.isUndefOrNull(id)) {
            return;
        }

        GenkaShisan_Tab.wait(target).then(function () {//2020-08-06 :14007 : Bug #18857 内容量0.1でエラーとなる

            var validate = function (targets) {
                return GenkaShisan_Tab.detail.validator.validate({
                    targets: targets,
                    state: {
                        tbody: row,
                        isGridValidation: true
                    }
                });
            };

            //フラグ変更画面
            page.values.isChange = true;
            validate(target).then(function () {

                //原価試算依頼 : 試算依頼flg
                if (["", "flg_shisanIrai"].indexOf(property) > 0) {

                    id = target.closest("td").attr("data-key-sample");
                    //create new id
                    if (!GenkaShisan_Tab.detail.dataSample[id]) {
                        GenkaShisan_Tab.detail.dataSample[id] = {};
                    };

                    //試算依頼flg
                    GenkaShisan_Tab.detail.dataSample[id][property] = target[0].checked;
                    //原価試算依頼がある場合、1で登録依頼キャンセルがある場合、NULLで登録
                    if (!target[0].checked) {
                        GenkaShisan_Tab.detail.dataSample[id][property] = null;
                    }

                    GenkaShisan_Tab.detail.checkShisanIrai(id, target, property);
                    return;
                }

                entity = GenkaShisan_Tab.detail.data.entry(id);

                old_data = entity[property];
                entity[property] = target.val();

                var numFormat = {
                    forDisp: 0,
                    forData: 0
                }

                //値表示時、小数桁数が2桁になるようにゼロ埋め・切上げを行う																																			
                if (target.hasClass("to-fixed")) {
                    if (property == "zyusui" || property == "zyuabura") {
                        numFormat = page.detail.formatNumber(entity[property], 2);
                    } else {
                        numFormat = page.detail.formatNumber(entity[property], 2, undefined, 100);
                    }

                    target.val(numFormat.forDisp);

                    numFormat.forDisp = GenkaShisan_Tab.detail.convertNull(numFormat.forDisp, target.val());

                    entity[property] = GenkaShisan_Tab.detail.stringForData(numFormat.forDisp);
                }

                //印刷FG
                if (["", "flg_print"].indexOf(property) > 0) {
                    entity[property] = target[0].checked;
                }

                GenkaShisan_Tab.detail.data.update(entity);

                //有効歩留（％）（※）, 平均充填量（g）（※）, 材料費（※）, 固定費（※）
                //if (["", "zyusui", "zyuabura", "yukobudomari", "heikinzyu", "cs_zairyohi", "cs_keihi"].indexOf(property) > 0) {
                if (["", "yukobudomari", "heikinzyu", "cs_zairyohi", "cs_keihi"].indexOf(property) > 0) {
                    switch (property) {
                        case "zyusui":
                        case "zyuabura"://調味料１液タイプ、調味料２液タイプの場合

                            var shisakuhin = GenkaShisan_Tab.getDataHeader();
                            if (shisakuhin.pt_kotei == App.settings.app.pt_kotei.chomieki_1 || shisakuhin.pt_kotei == App.settings.app.pt_kotei.chomieki_2) {
                                //原価計、粗利の計算処理を行う
                                GenkaShisan_Tab.detail.recalculation("EventThisTab");
                            }
                            break;
                        case "yukobudomari":
                        case "heikinzyu":
                            //前回入力したものと異なる値だった場合に、確認メッセージを表示し、他列へも反映
                            GenkaShisan_Tab.detail.updateYukoHei(property, numFormat, old_data, row, validate);
                            break;
                        default:
                            //原価計、粗利の計算処理を行う
                            GenkaShisan_Tab.detail.recalculation("EventThisTab");
                            break;
                    }
                }
            })
        })
    };

    /**
    * 前回入力したものと異なる値だった場合に、確認メッセージを表示し、他列へも反映
    */
    GenkaShisan_Tab.detail.updateYukoHei = function (property, new_data, old_data, row, validate) {
        var element_head_sample = GenkaShisan_Tab.detail.element_head_sample,
            recalculation = function () {
                GenkaShisan_Tab.detail.recalculation("EventThisTab");
            };

        if (Number(new_data.forData) != Number(old_data) && element_head_sample.find(".col-sample").not(".th-tmpl").length > 1) {
            var options = {
                text: App.str.format(App.messages.app.AP0045, { name: property == "yukobudomari" ? "有効歩留" : "平均充填量" })
            }
            GenkaShisan_Tab.confirmDialog(options).then(function () {
                var element = GenkaShisan_Tab.detail.element,
                    update = row.element.find(".update-" + property + ":not([readonly]):not([disabled])");

                var id, entity, ele;
                $.each(update, function (index, item) {
                    ele = row.element.find(item);
                    id = row.element.find(item).closest("td").attr("data-key");

                    if (App.isUndefOrNull(id)) {
                        return true;
                    }
                    entity = GenkaShisan_Tab.detail.data.entry(id);
                    entity[property] = GenkaShisan_Tab.detail.stringForData(new_data.forDisp);
                    GenkaShisan_Tab.detail.data.update(entity);

                    row.element.find(item).val(new_data.forDisp);

                    validate(row.element.find(item));
                });

                setTimeout(function () {
                    //原価計、粗利の計算処理を行う
                    recalculation();
                }, 1)
            }).fail(function () {
                //原価計、粗利の計算処理を行う
                recalculation();
            })
            return;
        }

        //原価計、粗利の計算処理を行う
        recalculation();
    };

    /**
     * 明細の一覧に新規データを追加します。
     */
    //GenkaShisan_Tab.detail.addNewItem = function () {
    //    //TODO:新規データおよび初期値を設定する処理を記述します。
    //    var newData = {
    //        //no_seq: page.values.no_seq
    //    };

    //    GenkaShisan_Tab.detail.data.add(newData);
    //    GenkaShisan_Tab.detail.dataTable.dataTable("addRow", function (row) {
    //        row.form(GenkaShisan_Tab.detail.options.bindOption).bind(newData);
    //        return row;
    //    }, true);

    //    if (GenkaShisan_Tab.element.find(".save").is(":disabled")) {
    //        GenkaShisan_Tab.element.find(".save").prop("disabled", false);
    //    }
    //};

    /**
     * 明細の一覧で選択されている行とデータを削除します。
     */
    //GenkaShisan_Tab.detail.deleteItem = function (e) {
    //    var element = GenkaShisan_Tab.detail.element,
    //        selected = element.find(".datatable .select-tab.selected").closest("tbody");

    //    if (!selected.length) {
    //        return;
    //    }

    //    GenkaShisan_Tab.detail.dataTable.dataTable("deleteRow", selected, function (row) {
    //        var id = row.attr("data-key"),
    //            newSelected;

    //        row.find(":input").each(function (i, elem) {
    //            GenkaShisan_Tab.notifyAlert.remove(elem);
    //        });

    //        if (!App.isUndefOrNull(id)) {
    //            var entity = GenkaShisan_Tab.detail.data.entry(id);
    //            GenkaShisan_Tab.detail.data.remove(entity);
    //        }

    //        newSelected = row.next().not(".item-tmpl");
    //        if (!newSelected.length) {
    //            newSelected = row.prev().not(".item-tmpl");
    //        }
    //        if (newSelected.length) {
    //            for (var i = GenkaShisan_Tab.detail.fixedColumnIndex; i > -1; i--) {
    //                if ($(newSelected[i]).find(":focusable:first").length) {
    //                    $(newSelected[i]).find(":focusable:first").focus();
    //                    break;
    //                }
    //            }
    //        }
    //    });

    //    if (GenkaShisan_Tab.element.find(".save").is(":disabled")) {
    //        GenkaShisan_Tab.element.find(".save").prop("disabled", false);
    //    }

    //};

    /**
    * 明細のバリデーションを実行します。
    */
    GenkaShisan_Tab.detail.executeValidation = function (targets, row, options) {
        var defaultOptions = {
            targets: targets,
            state: {
                tbody: row,
                isGridValidation: true
            }
        },
            execOptions = $.extend(true, {}, defaultOptions, options);

        return GenkaShisan_Tab.detail.validator.validate(execOptions);
    };

    /**
     * 明細のバリデーションフィルターを設定します。（必須チェックを行わない）
     */
    GenkaShisan_Tab.detail.validationFilter = function (item, method, state, options) {
        return method !== "required";
    };

    /**
     * 明細の一覧全体のバリデーションを実行します。
     */
    GenkaShisan_Tab.detail.validateList = function (suppressMessage) {
        var validations = [],
            options = {
                state: {
                    suppressMessage: suppressMessage,
                }
            };

        GenkaShisan_Tab.detail.dataTable.dataTable("each", function (row, index) {
            //validations.push(GenkaShisan_Tab.detail.executeValidation(row.element.find(":input"), row.element, options));
            // セルに対してバリデーションを実行します。
            row.element.find(":input").each(function (index, elem) {
                if ($(elem).closest("td").hasClass("th-tmpl")) {
                    return true;
                }
                validations.push(GenkaShisan_Tab.detail.validator.validate({
                    targets: $(elem),
                    state: {
                        suppressMessage: suppressMessage,
                        tbody: row.element,
                        isGridValidation: true
                    }
                }));

            });
        });

        return App.async.all(validations);
    };

</script>

<%-- 2010-09-10 : START : Bug #15330 Add new class text-selectAll
************
************
2010-09-10 : END : Bug #15330 Add new class text-selectAll--%>

<div class="tab-pane" id="GenkaShisan_Tab" style="margin-top: 3px;">

    <table class="datatable" style="display: block;">
        <!--TODO: 明細一覧のヘッダーを定義するHTMLをここに記述します。-->
        <thead>
            <tr>
                <th style="width: 110px; background-color: #ffffff!important; border-left: none; border-right: none; border-top: none;"></th>
                <th style="width: 200px; background-color: #ffffff!important; border-left: none; border-right: none; border-top: none;"></th>
                <th style="width: 135px; background-color: #ffffff!important; border-left: none; border-right: none; border-top: none;" class="th-tmpl"></th>
            </tr>

        </thead>
        <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->

        <tbody class="item-tmpl" style="display: none;">

            <tr>
                <td rowspan="7" style="text-align: center;">
                    <label>配合表</label>
                </td>
                <td style="border-right-width: 3px;">
                    <label>サンプルNO</label>
                </td>
                <td style="" class="th-tmpl col-sample">
                    <label class="nm_sample overflow-ellipsis" data-prop="nm_sample" style="height: 17px"></label>
                    <input style="display: none" />
                    <%--input for template--%>
                </td>
            </tr>

            <tr class="data-index-tr-2" data-index-tr="2">
                <td style="border-right-width: 3px;">
                    <label>印刷FG</label>
                </td>
                <td style="text-align: center;" class="th-tmpl col-sample enableColor">
                    <input type="checkbox" data-prop="flg_print" class="flg_print" value="1" />
                </td>
            </tr>


            <tr class="data-index-tr-3" data-index-tr="3">
                <td style="border-right-width: 3px;">
                    <label>原価試算依頼</label>
                </td>
                <td style="text-align: center;" class="th-tmpl col-sample enableColor">
                    <input type="checkbox" data-prop="flg_shisanIrai" class="flg_shisanIrai" value="1" />
                </td>
            </tr>

            <tr class="data-index-tr-4" data-index-tr="4">
                <td style="border-right-width: 3px;">
                    <label>充填量水相（g）（※）</label>
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" class="zyusui-input limit-input-float-new to-fixed text-selectAll" data-prop="zyusui" style="text-align: right" maxlength="15" data-number-format-tofixed="null" readonly="readonly" />
                </td>
            </tr>

            <tr class="data-index-tr-5" data-index-tr="5">
                <td style="border-right-width: 3px;">
                    <label>充填量油相（g）（※）</label>
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" class="zyuabura-input limit-input-float-new to-fixed text-selectAll" data-prop="zyuabura" style="text-align: right" maxlength="15" data-number-format-tofixed="null" readonly="readonly" />
                </td>
            </tr>

            <tr>
                <td style="border-right-width: 3px;">
                    <label></label>
                </td>
                <td class="th-tmpl col-sample separatorColor" style="border-left-color: #b7b7b7; border-right-color: #b7b7b7">
                    <input type="text" readonly="readonly" class="separatorColor" />
                </td>
            </tr>

            <tr class="data-index-tr-7" data-index-tr="7">
                <td style="border-right-width: 3px;">
                    <label>合計量（1本：g）（容量ｘ比重）</label>
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="gokei" readonly="readonly" style="text-align: right" />
                </td>
            </tr>

            <tr class="data-index-tr-8" data-index-tr="8">
                <td rowspan="2" style="background-color: #ffffff!important; border-left: 0px;"></td>
                <td style="border-right-width: 3px;">
                    <label>原料費(kg)</label>
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="genryohi" readonly="readonly" style="text-align: right" />
                </td>
            </tr>
            <tr class="data-index-tr-9" data-index-tr="9">
                <td style="border-right-width: 3px;">
                    <label>原料費(１本当)</label>
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="genryohi1" readonly="readonly" style="text-align: right" />
                </td>
            </tr>


            <tr class="data-index-tr-10" data-index-tr="10">
                <td style="text-align: center; background-color: #efefef">
                    <label>特性値</label>
                </td>
                <td style="border-right-width: 3px;">
                    <label>比重</label>
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="hiju" readonly="readonly" style="text-align: right" />
                    <input type="text" data-prop="hiju_sui" style="display: none" />
                </td>
            </tr>

            <tr class="data-index-tr-11" data-index-tr="11">
                <td rowspan="2" style="text-align: center; background-color: #efefef">
                    <label>基本情報</label>
                </td>
                <td style="border-right-width: 3px;">
                    <label>容量</label>
                </td>
                <td class="th-tmpl col-sample update-yoryo">
                    <input type="text" data-prop="yoryo" readonly="readonly" style="text-align: right" />
                </td>
            </tr>

            <tr class="data-index-tr-12" data-index-tr="12">
                <td style="border-right-width: 3px;">
                    <label>入り数</label>
                </td>
                <td class="th-tmpl col-sample update-irisu">
                    <input type="text" data-prop="irisu" readonly="readonly" style="text-align: right" />
                </td>
            </tr>

            <tr class="data-index-tr-13" data-index-tr="13">
                <td rowspan="5" style="background-color: #ffffff!important; border-left: 0px;"></td>
                <td style="border-right-width: 3px;">
                    <label>有効歩留（％）（※）</label>
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" class="limit-input-float-new to-fixed update-yukobudomari text-selectAll" data-prop="yukobudomari" style="text-align: right" maxlength="15" data-number-format-tofixed="null" />
                </td>
            </tr>

            <tr>
                <td style="border-right-width: 3px;">
                    <label></label>
                </td>
                <td class="th-tmpl col-sample separatorColor" style="border-left-color: #b7b7b7; border-right-color: #b7b7b7">
                    <input type="text" readonly="readonly" class="separatorColor" />
                </td>
            </tr>

            <tr class="data-index-tr-15" data-index-tr="15">
                <td style="border-right-width: 3px;">
                    <label>レベル量（ｇ内容量ｘ入数）</label>
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="reberu" readonly="readonly" style="text-align: right" />
                </td>
            </tr>

            <tr class="data-index-tr-16" data-index-tr="16">
                <td style="border-right-width: 3px;">
                    <label>比重加算量（ｇ平均充填量ｘ比重）</label>
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="hizyubudomari" readonly="readonly" style="text-align: right" />
                </td>
            </tr>

            <tr class="data-index-tr-17" data-index-tr="17">
                <td style="border-right-width: 3px;">
                    <label>平均充填量（g）（※）</label>
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" class="limit-input-float-new to-fixed update-heikinzyu text-selectAll" data-prop="heikinzyu" style="text-align: right" maxlength="15" data-number-format-tofixed="null" />
                </td>
            </tr>

            <tr class="data-index-tr-18" data-index-tr="18">
                <td style="text-align: center; background-color: #efefef" rowspan="4">
                    <label>１c/s当たりの計算</label>
                </td>
                <td style="border-right-width: 3px;">
                    <label>原料費</label>
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="cs_genryo" readonly="readonly" style="text-align: right" />
                </td>
            </tr>

            <tr class="data-index-tr-19" data-index-tr="19">
                <td style="border-right-width: 3px;">
                    <label>材料費（※）</label>
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" class="limit-input-float-new to-fixed text-selectAll" data-prop="cs_zairyohi" style="text-align: right" maxlength="15" data-number-format-tofixed="null" />
                </td>
            </tr>

            <tr class="data-index-tr-20" data-index-tr="20">
                <td style="border-right-width: 3px;">
                    <label>固定費（※）</label>
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" class="limit-input-float-new to-fixed text-selectAll" data-prop="cs_keihi" style="text-align: right" maxlength="15" data-number-format-tofixed="null" />
                </td>
            </tr>

            <tr class="data-index-tr-21" data-index-tr="21">
                <td style="border-right-width: 3px;">
                    <label>原価計/CS</label>
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="cs_genka" readonly="readonly" style="text-align: right" />
                </td>
            </tr>

            <tr class="data-index-tr-22" data-index-tr="22">
                <td style="text-align: center; background-color: #efefef">
                    <label>１個当たりの計算</label>
                </td>
                <td style="border-right-width: 3px;">
                    <label>原価計/個</label>
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="ko_genka" readonly="readonly" style="text-align: right" />
                </td>
            </tr>

            <tr class="data-index-tr-23" data-index-tr="23">
                <td rowspan="3" style="background-color: #ffffff!important; border-left: 0px; border-bottom: 0px;"></td>
                <td style="border-right-width: 3px;">
                    <label>売価</label>
                </td>
                <td class="th-tmpl col-sample update-ko_baika">
                    <input type="text" data-prop="ko_baika" readonly="readonly" style="text-align: right" />
                </td>
            </tr>

            <tr class="data-index-tr-24" data-index-tr="24">
                <td style="border-right-width: 3px;">
                    <label>粗利（％）</label>
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="ko_riritu" readonly="readonly" style="text-align: right" />
                </td>
            </tr>
            <tr class="data-index-tr-25" data-index-tr="25">
                <td style="border-right-width: 3px;">
                    <label>依頼キャンセル</label>
                </td>
                <td class="center th-tmpl col-sample enableColor">
                    <input type="checkbox" class="cancel-irai-sample" disabled="disabled" />
                </td>
            </tr>

        </tbody>
    </table>
</div>
