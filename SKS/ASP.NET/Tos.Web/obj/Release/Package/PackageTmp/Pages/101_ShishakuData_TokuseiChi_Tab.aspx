<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="101_ShishakuData_TokuseiChi_Tab.aspx.cs" Inherits="Tos.Web.Pages.ShishakuData_TokuseiChi_Tab" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchInputDialog(Ver2.1)】 Template--%>
<style type="text/css">
    #TokuseiChi_Tab thead tr {
        padding: 0px;
    }

    #TokuseiChi_Tab .new tr {
        height: 16px !important;
        padding: 0px;
    }

    #TokuseiChi_Tab .pt-kotei-display-none {
        display: none;
    }

    #TokuseiChi_Tab thead tr th {
        padding: 0px !important;
        margin: 0px;
        height: 16px;
        font-size: 12px;
    }

    #TokuseiChi_Tab .col-sample {
        /*min-width: 88px!important;*/
        max-width: 135px!important;
        /*width: 100%;*/
    }

    #TokuseiChi_Tab .new tr td {
        padding: 0px;
        margin: 0px;
        height: 16px;
        font-size: 12px;
    }

    #TokuseiChi_Tab thead th input[type='tel'],
    #TokuseiChi_Tab thead th input[type='text'] {
        border: none;
        border-width: 0px;
        padding: 0px;
        margin: 0px;
        height: 100%;
        width: 100%;
        background-color: initial;
        padding-left: 2px;
    }

    #TokuseiChi_Tab .new input[type='tel'],
    #TokuseiChi_Tab .new input[type='text'] {
        border: none;
        border-width: 0px;
        padding: 0px;
        margin: 0px;
        height: 100%;
        width: 100%;
        padding-left: 2px;
    }

    #TokuseiChi_Tab .new select {
        padding: 0px;
        padding: 0px;
        border: none;
        border-width: 0px;
        margin: 0px;
        width: 100%;
        height: 18px;
        font-size: inherit;
        float: left;
    }

    #TokuseiChi_Tab .new label {
        height: 100%;
        width: 100%;
        padding: 0px;
        margin: 0px;
    }

    #TokuseiChi_Tab .new checkbox {
        margin: 0px;
    }

    #TokuseiChi_Tab .new textarea {
        margin: 0px;
        padding: 0px;
        border: none;
        resize: none;
        outline: none;
        height: 100%;
        font-size: 12px;
        overflow-y: auto;
    }

    #TokuseiChi_Tab .th-tmpl {
        display: none;
    }

    #TokuseiChi_Tab .first-col-head, .new .border-right-head {
        border-right-width: 3px;
    }

    #TokuseiChi_Tab .new .required {
        color: red;
    }

    #TokuseiChi_Tab .dt-container .fix-columns .datatable {
        margin-right: 0px;
    }

    /*2019-09-10 : START : Bug #15319 選択行の色*/
    #TokuseiChi_Tab .selectedInput {
        background-color: #7cfc00!important;
    }
    /*2019-09-10 : START : Bug #15319 選択行の色*/
</style>

<script type="text/javascript">

    /**
    * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
    */
    var TokuseiChi_Tab = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.maxInputDataCount,   // TODO:取得するデータ数を指定します。
            filter: ""
        },
        values: {
            isChangeRunning: {}
        },
        urls: {
            search: "../api/ShishakuData_TokuseiChi_Tab_",
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
    TokuseiChi_Tab.validationSuccess = function (results, state) {
        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                //列可変はグリッドでもセル単位で色付けを行う
                TokuseiChi_Tab.setColValidStyle(item.element, state.isGridValidation);
            } else {
                TokuseiChi_Tab.setColValidStyle(item.element);
            }

            App.ui.page.notifyAlert.remove(item.element);
        }
    };

    /**
     * バリデーション失敗時の処理を実行します。
     */
    TokuseiChi_Tab.validationFail = function (results, state) {

        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                //列可変はグリッドでもセル単位で色付けを行う
                TokuseiChi_Tab.setColInvalidStyle(item.element, state.isGridValidation);
            } else {
                TokuseiChi_Tab.setColInvalidStyle(item.element);
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
    TokuseiChi_Tab.validationAlways = function (results) {
        //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
    };

    /**
      * 指定された定義をもとにバリデータを作成します。
      * @param target バリデーション定義
      * @param options オプションに設定する値。指定されていない場合は、
      *                画面の success/fail/always のハンドル処理が指定されたオプションが設定されます。
      */
    TokuseiChi_Tab.createValidator = function (target, options) {
        return App.validation(target, options || {
            success: TokuseiChi_Tab.validationSuccess,
            fail: TokuseiChi_Tab.validationFail,
            always: TokuseiChi_Tab.validationAlways
        });
    };

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    TokuseiChi_Tab.setColInvalidStyle = function (target, isGrid) {
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
    TokuseiChi_Tab.setColValidStyle = function (target, isGrid) {
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
    TokuseiChi_Tab.validateAll = function (suppressMessage, isSave) {

        var validations = [];

        validations.push(TokuseiChi_Tab.detail.validateList(suppressMessage, isSave));

        //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。

        return App.async.all(validations);
    };

    /**
     * ダイアログの初期化処理を行います。
     */
    TokuseiChi_Tab.initialize = function () {

        var element = $("#TokuseiChi_Tab");
        element.show();

        TokuseiChi_Tab.element = element;

        TokuseiChi_Tab.detail.initialize();
        TokuseiChi_Tab.detail.loadMasterData();
        TokuseiChi_Tab.detail.firstCol = true;

        //remove html col first in table
        element.find(".first-col-head").html("");
        element.css("display", "");
    };

    /**
    * 全コピーブタン
    */
    TokuseiChi_Tab.detail.zenCopy = function () {
        var element = TokuseiChi_Tab.detail.element;

        element.find(":input").not(".biseibutsu").prop("disabled", false);
        element.find(".shisanIraiked").removeClass("shisanIraiked");
    };

    /**
    * get all data fiel on screen
    */
    TokuseiChi_Tab.detail.allDataOnTab = function () {
        var deferred = $.Deferred(),
            element = TokuseiChi_Tab.detail.element,
            header = TokuseiChi_Tab.detail.element_head_sample,
            col = header.find(".col-sample");

        var result = {},
            id, ele;
        $.each(col, function (index, item) {
            id = element.find(item).attr("data-key-sample");
            if (App.isUndefOrNull(id)) {
                return true;
            }
            result[id] = { hiju: element.find(".new ." + id).findP("hiju").val() };
        })

        deferred.resolve(result);

        //col-sample
        return deferred.promise();
    }

    /**
     * control befor recacu tab
     */
    //2019-12-12 : Bug #16083 :【分析マスタの最新情報に更新】ボタンを押す時画面が計算しる。
    //TokuseiChi_Tab.detail.recalculation = function (isFiel, data) {
    TokuseiChi_Tab.detail.recalculation = function (isFiel, data, isNotCheckFlgAuto) {

        //選択された場合		
        //	【2画面編集仕様（初期表示_詳細モード）】の自動計算項目を計算して表示する	
        //	【【補足】計算式一覧】参照	
        switch (isFiel) {
            case "油含有率":
            case "量":
            case "工程パターン":
            case "毎回自動計算":
            case "試作列コピー":
            case "ph":
            case "hiju_sui":
                //2019-12-12 : Bug #16083 :【分析マスタの最新情報に更新】ボタンを押す時画面が計算しる。
                //TokuseiChi_Tab.detail.recalcuTokusei(data);
                TokuseiChi_Tab.detail.recalcuTokusei(data, isNotCheckFlgAuto);
                break;
            case "EventHaigo":
                //2019-12-12 : Bug #16083 :【分析マスタの最新情報に更新】ボタンを押す時画面が計算しる。
                //TokuseiChi_Tab.detail.recalcuTokusei(data);
                TokuseiChi_Tab.detail.recalcuTokusei(data, isNotCheckFlgAuto);
                break;
            default:
                App.ui.loading.close();
                break;
        }
    }

    /**
     * calculator this tab
     */
    //2019-12-12 : Bug #16083 :【分析マスタの最新情報に更新】ボタンを押す時画面が計算しる。
    //TokuseiChi_Tab.detail.recalcuTokusei = function (data) {
    TokuseiChi_Tab.detail.recalcuTokusei = function (data, isNotCheckFlgAuto) {


        //選択されていない場合		
        //処理無し	
        //2019-12-12 : Bug #16083 :【分析マスタの最新情報に更新】ボタンを押す時画面が計算しる。
        //if (!TokuseiChi_Tab.detail.element.findP("flg_auto").is(":checked")
        if (!TokuseiChi_Tab.detail.element.findP("flg_auto").is(":checked") && (isNotCheckFlgAuto == undefined || isNotCheckFlgAuto == null)) {
            //製品比重の再計算を行う。
            TokuseiChi_Tab.detail.recalcuHijuOnly(data);
            App.ui.loading.close();
            return;
        }

        var genryohiTotal = {},//原料費（ｋｇ）、原料費（１個）
            validate = false,
            inputItems = "配合量、ｐH",
            maxChar;

        $.each(data.shisaku, function (index, item) {

            var kakuShisakuRetsu = 0,//カテゴリマスタ登録割合
                //gaitoHaigoRyo = 0,//該当配合量                
                msgSokutei = 0,//ＭＳＧ合計量
                sosanGokeiYyo = 0,//総酸合計量  
                shokuenGokeiYyo = 0,//食塩合計量   
                sakuGokeiRyo = 0,//酢酸合計量
                aburaGokeiRyo = 0,//油含有合計量


                hiju = "",//製品比重
                oilmustard = 0,//製品オイルマスタード含有量
                jikkoHikairiSakusanSando = "",//0,//水相中　非解離酢酸酸度（％）
                jikkoSakusanNodo = "",//0,//水相中　実効酢酸濃度（％）
                msg_suiso = 0,//水相中　ＭＳＧ含有率（％）
                sakusan_suiso = 0,//水相中　酢酸酸度（％）
                shokuen_suiso = 0,//水相中　食塩（％）
                sando_suiso = 0,//水相中　酸度（％）
                ritu_shokuen = 0,//食塩（％）
                ritu_sousan = 0,//総酸（％）

                total_weight = 0,//合計重量
                dblYusoHiju = App.settings.app.keisansikiyo.value_092,//油相比重
                dblSuisoHiju = 0,//水相比重
                dblYusoGokeiryo = 0,//油相合計重量(g) 
                dblSuisoGokeiryo = 0,//水相合計重量(g) 
                dblSakinGokeiryo = 0;//殺菌調味液重量(g)

            if (data.shisaku_hin.pt_kotei != App.settings.app.pt_kotei.sonohokaeki) {
                $.each(item, function (pro, val) {

                    if (pro.indexOf("quantity_") < 0) {
                        return true;
                    }

                    val = page.detail.checkUndefi(val);

                    var spli = pro.split("_"),
                     haigo = data.haigo[spli[0] + "_" + spli[1] + "_" + spli[2]];

                    if (!haigo) {
                        return true;
                    }
                    if (data.shisaku_hin.pt_kotei == App.settings.app.pt_kotei.chomieki_2 && data.shisaku_hin.cd_tani == App.settings.app.cd_tani.ml) {
                        //油相（リテラルマスタのvalue1 =1  AND value2 = 3）の「量」の合計　
                        //dblYusoGokeiryo += TokuseiChi_Tab.detail.findKoteiZokusei(haigo, val, App.settings.app.kote_value2.aburasho);
                        dblYusoGokeiryo = new BigNumber(stringNumbers(dblYusoGokeiryo)).plus(stringNumbers(TokuseiChi_Tab.detail.findKoteiZokusei(haigo, val, App.settings.app.kote_value2.aburasho))).toNumber();
                        //水相（リテラルマスタのvalue1 =1  AND value2 = 2）の「量」の合計　
                        //dblSuisoGokeiryo += TokuseiChi_Tab.detail.findKoteiZokusei(haigo, val, App.settings.app.kote_value2.suisho);
                        dblSuisoGokeiryo = new BigNumber(stringNumbers(dblSuisoGokeiryo)).plus(stringNumbers(TokuseiChi_Tab.detail.findKoteiZokusei(haigo, val, App.settings.app.kote_value2.suisho))).toNumber();
                        //殺菌調味液（リテラルマスタのvalue1 =1  AND value2 = 1）の「量」の合計　
                        //dblSakinGokeiryo += TokuseiChi_Tab.detail.findKoteiZokusei(haigo, val, App.settings.app.kote_value2.chomiryo);
                        dblSakinGokeiryo = new BigNumber(stringNumbers(dblSakinGokeiryo)).plus(stringNumbers(TokuseiChi_Tab.detail.findKoteiZokusei(haigo, val, App.settings.app.kote_value2.chomiryo))).toNumber();
                    }

                    //カテゴリコード110のリテラル値2に登録されている値
                    //kakuShisakuRetsu += TokuseiChi_Tab.detail.findToroku(haigo, val, "value1");
                    kakuShisakuRetsu = new BigNumber(stringNumbers(kakuShisakuRetsu)).plus(stringNumbers(TokuseiChi_Tab.detail.findToroku(haigo, val, "value1"))).toNumber();
                    //カテゴリコード110のリテラル値1に登録されている品コードのシサクイックでの配合量
                    //gaitoHaigoRyo += TokuseiChi_Tab.detail.findToroku(haigo, val, "value2");
                    //各試作列のＭＳＧ量（配合量×ＭＳＧ）の合計値
                    //msgSokutei += page.detail.checkUndefi(val * page.detail.checkUndefi(haigo.ritu_msg));
                    msgSokutei = new BigNumber(stringNumbers(msgSokutei)).plus(stringNumbers(new BigNumber(stringNumbers(val)).times(stringNumbers(page.detail.checkUndefi(haigo.ritu_msg))).toNumber())).toNumber();
                    //各試作列の総酸量（配合量×総酸）の合計値
                    //sosanGokeiYyo += page.detail.checkUndefi(val * page.detail.checkUndefi(haigo.ritu_sousan));
                    sosanGokeiYyo = new BigNumber(stringNumbers(sosanGokeiYyo)).plus(stringNumbers(new BigNumber(stringNumbers(val)).times(stringNumbers(page.detail.checkUndefi(haigo.ritu_sousan))).toNumber())).toNumber();
                    //各試作列の食塩量（配合量×食塩）の合計値
                    //shokuenGokeiYyo += page.detail.checkUndefi(val * page.detail.checkUndefi(haigo.ritu_shokuen));
                    shokuenGokeiYyo = new BigNumber(stringNumbers(shokuenGokeiYyo)).plus(stringNumbers(new BigNumber(stringNumbers(val)).times(stringNumbers(page.detail.checkUndefi(haigo.ritu_shokuen))).toNumber())).toNumber();
                    //各試作列の酢酸量（配合量×酢酸）の合計値
                    //sakuGokeiRyo += page.detail.checkUndefi(val * page.detail.checkUndefi(haigo.ritu_sakusan));
                    sakuGokeiRyo = new BigNumber(stringNumbers(sakuGokeiRyo)).plus(stringNumbers(new BigNumber(stringNumbers(val)).times(stringNumbers(page.detail.checkUndefi(haigo.ritu_sakusan))).toNumber())).toNumber();
                    //各試作列の油含有量（配合量×油含有率）の合計値
                    //aburaGokeiRyo += page.detail.checkUndefi(val * page.detail.checkUndefi(haigo.ritu_abura) / App.settings.app.keisansikiyo.value_100);
                    aburaGokeiRyo = new BigNumber(stringNumbers(aburaGokeiRyo)).plus(stringNumbers(new BigNumber(stringNumbers(val)).times(stringNumbers(page.detail.checkUndefi(haigo.ritu_abura))).div(stringNumbers(App.settings.app.keisansikiyo.value_100)).toNumber())).toNumber();
                })

                //各試作列の配合量の合計
                total_weight = page.detail.checkUndefi(item.total_weight);

                //2019/12/04 START : Request #15984 : 「現：該当配合量×カテゴリマスタ登録割合÷合計重量」から「該当配合量×カテゴリマスタ登録割合÷100÷合計重量×1000000」までです
                //該当配合量×カテゴリマスタ登録割合÷合計重量
                //oilmustard = page.detail.checkUndefi(kakuShisakuRetsu / total_weight);
                //oilmustard = page.detail.checkUndefi(new BigNumber(stringNumbers(kakuShisakuRetsu)).div(stringNumbers(total_weight)).toNumber());
                //該当配合量×カテゴリマスタ登録割合÷100÷合計重量×1000000
                oilmustard = page.detail.checkUndefi(new BigNumber(stringNumbers(kakuShisakuRetsu)).div(stringNumbers(100)).div(stringNumbers(total_weight)).times(stringNumbers(1000000)).toNumber());
                //2019/12/04 END : Request #15984 : 「現：該当配合量×カテゴリマスタ登録割合÷合計重量」から「該当配合量×カテゴリマスタ登録割合÷100÷合計重量×1000000」までです

                //総酸合計量÷合計重量
                //ritu_sousan = page.detail.checkUndefi(sosanGokeiYyo / total_weight);
                ritu_sousan = page.detail.checkUndefi(new BigNumber(stringNumbers(sosanGokeiYyo)).div(stringNumbers(total_weight)).toNumber());
                //食塩合計量÷合計重量
                //ritu_shokuen = page.detail.checkUndefi(shokuenGokeiYyo / total_weight);
                ritu_shokuen = page.detail.checkUndefi(new BigNumber(stringNumbers(shokuenGokeiYyo)).div(stringNumbers(total_weight)).toNumber());
                //総酸合計量÷（合計重量-油含有率合計量）
                //sando_suiso = page.detail.checkUndefi(sosanGokeiYyo / (total_weight - aburaGokeiRyo));
                sando_suiso = page.detail.checkUndefi(new BigNumber(stringNumbers(sosanGokeiYyo)).div(stringNumbers(new BigNumber(stringNumbers(total_weight)).minus(stringNumbers(aburaGokeiRyo)).toNumber())).toNumber());
                //食塩合計量÷（合計重量-油含有率合計量）
                //shokuen_suiso = page.detail.checkUndefi(shokuenGokeiYyo / (total_weight - aburaGokeiRyo));
                shokuen_suiso = page.detail.checkUndefi(new BigNumber(stringNumbers(shokuenGokeiYyo)).div(stringNumbers(new BigNumber(stringNumbers(total_weight)).minus(stringNumbers(aburaGokeiRyo)).toNumber())).toNumber());
                //酢酸合計量÷（合計重量-油含有率合計量）
                //sakusan_suiso = page.detail.checkUndefi(sakuGokeiRyo / (total_weight - aburaGokeiRyo));
                sakusan_suiso = page.detail.checkUndefi(new BigNumber(stringNumbers(sakuGokeiRyo)).div(stringNumbers(new BigNumber(stringNumbers(total_weight)).minus(stringNumbers(aburaGokeiRyo)).toNumber())).toNumber());
                //ＭＳＧ合計量÷（合計重量-油含有率合計量）
                //msg_suiso = page.detail.checkUndefi(msgSokutei / (total_weight - aburaGokeiRyo));
                msg_suiso = page.detail.checkUndefi(new BigNumber(stringNumbers(msgSokutei)).div(stringNumbers(new BigNumber(stringNumbers(total_weight)).minus(stringNumbers(aburaGokeiRyo)).toNumber())).toNumber());

                //Bug #15197 : START
                //率ＭＳＧ
                ritu_msg = page.detail.checkUndefi(new BigNumber(stringNumbers(msgSokutei)).div(stringNumbers(total_weight)).toNumber());

                var elementCol = TokuseiChi_Tab.detail.element.find(".sample-" + item.indexCol),
                    id = elementCol.attr("data-key-sample"),
                    dataGenkaShisaku = elementCol.form().data();

                //Bug #15197 : START
                //率ＭＳＧ
                ritu_msg = page.detail.formatNumber(page.detail.checkUndefi(ritu_msg), 2);
                if (!page.numPointlength(ritu_msg.forData, [3, 2])) {
                    calculItem = "率ＭＳＧ";

                    maxChar = 5;
                    validate = true;
                    return false;
                }
                TokuseiChi_Tab.detail.setUpdate(id, "ritu_msg", ritu_msg.forData);
                //Bug #15197 : END

                //水相比重
                dblSuisoHiju = page.detail.checkUndefi(dataGenkaShisaku.hiju_sui);
                //工程パターン：調味料2液タイプ、容量（数値入力）（※）：ml
                if (data.shisaku_hin.pt_kotei == App.settings.app.pt_kotei.chomieki_2 && data.shisaku_hin.cd_tani == App.settings.app.cd_tani.ml) {
                    //debugger
                    //{ 殺菌調味液重量(g) + ドレ水相合計重量(g) +ドレ油相合計重量(g) } / 
                    //{ [(殺菌調味液重量(g) + ドレ水相合計重量(g) )/水相比重] + [ドレ油相合計重量(g) / 0.92] }
                    //hiju = page.detail.checkUndefi((dblSakinGokeiryo + dblSuisoGokeiryo + dblYusoGokeiryo) / (((dblSakinGokeiryo + dblSuisoGokeiryo) / dblSuisoHiju) + (dblYusoGokeiryo / dblYusoHiju)));

                    //Bug #15230 : 計算に使用する項目が未入力の場合計算対象外として頂けません
                    if (checkEvalBeforCalcu([dataGenkaShisaku.hiju_sui])) {
                        hiju = page.detail.checkUndefi(new BigNumber(stringNumbers(dblSakinGokeiryo)).plus(stringNumbers(dblSuisoGokeiryo)).plus(stringNumbers(dblYusoGokeiryo)).div(stringNumbers(new BigNumber(stringNumbers(dblSakinGokeiryo)).plus(stringNumbers(dblSuisoGokeiryo)).div(stringNumbers(dblSuisoHiju)).plus(stringNumbers(new BigNumber(stringNumbers(dblYusoGokeiryo)).div(stringNumbers(dblYusoHiju)).toNumber())).toNumber())).toNumber());
                    }
                }

                //Bug #15230 : 計算に使用する項目が未入力の場合計算対象外として頂けません
                if (checkEvalBeforCalcu([dataGenkaShisaku.ph])) {
                    //水相中酢酸酸度(%)-((水相中グルタミン酸Na濃度(%)×(0.5791×pH-1.9104)/187.13)*60
                    //jikkoSakusanNodo = page.detail.checkUndefi(sakusan_suiso - ((msg_suiso * (App.settings.app.keisansikiyo.value_05791 * page.detail.checkUndefi(dataGenkaShisaku.ph) - App.settings.app.keisansikiyo.value_19104) / App.settings.app.keisansikiyo.value_18713)) * App.settings.app.keisansikiyo.value_60);
                    var jikko_suiso = page.detail.formatNumber(msg_suiso, 2, 0),
                        jikko_sakusan_suiso = page.detail.formatNumber(sakusan_suiso, 2, 0);
                    //    numCal = new BigNumber(stringNumbers(App.settings.app.keisansikiyo.value_05791)).times(stringNumbers(page.detail.checkUndefi(dataGenkaShisaku.ph))).minus(stringNumbers(App.settings.app.keisansikiyo.value_19104)).toNumber();

                    //numCal = new BigNumber(stringNumbers(jikko_suiso.forData)).times(stringNumbers(numCal)).div(stringNumbers(App.settings.app.keisansikiyo.value_18713)).toNumber();
                    //jikkoSakusanNodo = new BigNumber(stringNumbers(jikko_sakusan_suiso.forData)).minus(stringNumbers(new BigNumber(stringNumbers(numCal)).times(stringNumbers(App.settings.app.keisansikiyo.value_60)).toNumber())).toNumber();
                    //jikkoSakusanNodo = sakusan_suiso - ((msg_suiso * (App.settings.app.keisansikiyo.value_05791 * page.detail.checkUndefi(dataGenkaShisaku.ph) - App.settings.app.keisansikiyo.value_19104) / App.settings.app.keisansikiyo.value_18713)) * App.settings.app.keisansikiyo.value_60;
                    var jsn = new BigNumber(stringNumbers(dataGenkaShisaku.ph)).times(stringNumbers(App.settings.app.keisansikiyo.value_05791)).toNumber();// 0.5791×ph
                    jsn = new BigNumber(stringNumbers(jsn)).minus(stringNumbers(App.settings.app.keisansikiyo.value_19104)).toNumber();// jsn－1.9104
                    if (msg_suiso > 0 && jsn > 0) {
                        jsn = new BigNumber(stringNumbers(jsn)).times(stringNumbers(jikko_suiso.forData)).toNumber();// jsn×水相中MSG
                        jsn = new BigNumber(stringNumbers(jsn)).div(stringNumbers(App.settings.app.keisansikiyo.value_18713)).toNumber();// jsn÷187.13
                        jsn = page.detail.formatNumber(jsn, 4).forData;
                        jsn = new BigNumber(stringNumbers(jsn)).times(stringNumbers(App.settings.app.keisansikiyo.value_60)).toNumber();// jsn×60
                        jsn = new BigNumber(stringNumbers(jikko_sakusan_suiso.forData)).minus(stringNumbers(jsn)).toNumber();// 水相中酢酸－jsn
                        if (jsn > 0) {
                            //jsn = page.detail.formatNumber( jsn.setScale(2, BigDecimal.ROUND_HALF_UP);
                        } else {
                            // 最後の減算でマイナス値になる場合
                            jsn = "0.00";
                        }
                    } else {
                        // 割り算する前に0値があった場合
                        jsn = "0.00";
                    }
                    jikkoSakusanNodo = jsn;

                    //水相中酢酸酸度(%)×(1/(1+10^(pH-4.48)))
                    //jikkoHikairiSakusanSando = page.detail.checkUndefi(sakusan_suiso * (App.settings.app.keisansikiyo.value_1 / (App.settings.app.keisansikiyo.value_1 + Math.pow(App.settings.app.keisansikiyo.value_10, (page.detail.checkUndefi(dataGenkaShisaku.ph) - App.settings.app.keisansikiyo.value_448)))));
                    jikkoHikairiSakusanSando = page.detail.checkUndefi(new BigNumber(stringNumbers(sakusan_suiso)).times(stringNumbers(new BigNumber(stringNumbers(App.settings.app.keisansikiyo.value_1)).div(stringNumbers(new BigNumber(stringNumbers(App.settings.app.keisansikiyo.value_1)).plus(stringNumbers(Math.pow(App.settings.app.keisansikiyo.value_10, (new BigNumber(stringNumbers(page.detail.checkUndefi(dataGenkaShisaku.ph))).minus(stringNumbers(App.settings.app.keisansikiyo.value_448)).toNumber())))).toNumber())).toNumber())).toNumber());
                }

                if (data.shisaku_hin.pt_kotei == App.settings.app.pt_kotei.chomieki_2 && data.shisaku_hin.cd_tani == App.settings.app.cd_tani.ml) {
                    //製品比重
                    hiju = page.detail.formatNumber(page.detail.checkUndefi(hiju), 3, undefined, 1000);
                    if (!page.stringPointlength(hiju.forData, { after: 3, max: 5 })) {
                        calculItem = "製品比重";
                        maxChar = 5;
                        validate = true;
                        return false;
                    }
                    elementCol.findP("hiju").val(hiju.forDisp == 0 ? "" : hiju.forDisp);
                    TokuseiChi_Tab.detail.setUpdate(id, "hiju", (hiju.forDisp != null && hiju.forDisp != "") ? hiju.forDisp.toString().replace(/,/g, "") : null);

                    if (App.isFunc(TokuseiChi_Tab.detail.updateOtherTab)) {
                        TokuseiChi_Tab.detail.updateOtherTab("genkashisan_tab", "hiju", hiju.forData, id);
                    }
                }

                //製品オイルマスタード含有量（ｐｐｍ）
                oilmustard = page.detail.formatNumber(page.detail.checkUndefi(oilmustard), 0, "0");
                //2019/12/04 START : Request #15984
                //if (!page.stringPointlength(oilmustard.forData, { after: 2, max: 4 })) {
                if (!page.numPointlength(oilmustard.forData, [7, 0])) {
                    calculItem = "製品オイルマスタード含有量（ｐｐｍ）";
                    //maxChar = 4;
                    maxChar = 7;
                    validate = true;
                    return false;
                }
                //2019/12/04 END: Request #15984

                elementCol.findP("oilmustard").val(oilmustard.forDisp);
                TokuseiChi_Tab.detail.setUpdate(id, "oilmustard", (oilmustard.forDisp != null && oilmustard.forDisp != "") ? oilmustard.forDisp.toString().replace(/,/g, "") : null);
                //総酸（％）
                ritu_sousan = page.detail.formatNumber(page.detail.checkUndefi(ritu_sousan), 2, "0.00");
                if (!page.numPointlength(ritu_sousan.forData, [3, 2])) {
                    calculItem = "総酸（％）";

                    maxChar = 5;
                    validate = true;
                    return false;
                }
                elementCol.findP("ritu_sousan").val(ritu_sousan.forDisp);
                TokuseiChi_Tab.detail.setUpdate(id, "ritu_sousan", ritu_sousan.forData);
                //食塩（％）
                ritu_shokuen = page.detail.formatNumber(page.detail.checkUndefi(ritu_shokuen), 2, "0.00");
                if (!page.numPointlength(ritu_shokuen.forData, [3, 2])) {
                    calculItem = "食塩（％）";
                    maxChar = 5;
                    validate = true;
                    return false;
                }
                elementCol.findP("ritu_shokuen").val(ritu_shokuen.forDisp);
                TokuseiChi_Tab.detail.setUpdate(id, "ritu_shokuen", ritu_shokuen.forData);
                //水相中　酸度（％）
                sando_suiso = page.detail.formatNumber(page.detail.checkUndefi(sando_suiso), 2, "0.00");
                if (!page.numPointlength(sando_suiso.forData, [3, 2])) {
                    calculItem = "水相中　酸度（％）";
                    maxChar = 5;
                    validate = true;
                    return false;
                }
                elementCol.findP("sando_suiso").val(sando_suiso.forDisp);
                TokuseiChi_Tab.detail.setUpdate(id, "sando_suiso", sando_suiso.forData);
                //水相中　食塩（％）
                shokuen_suiso = page.detail.formatNumber(page.detail.checkUndefi(shokuen_suiso), 2, "0.00");
                if (!page.numPointlength(shokuen_suiso.forData, [3, 2])) {
                    calculItem = "水相中　食塩（％）";
                    maxChar = 5;
                    validate = true;
                    return false;
                }
                elementCol.findP("shokuen_suiso").val(shokuen_suiso.forDisp);
                TokuseiChi_Tab.detail.setUpdate(id, "shokuen_suiso", shokuen_suiso.forData);
                //水相中　酢酸酸度（％）
                sakusan_suiso = page.detail.formatNumber(page.detail.checkUndefi(sakusan_suiso), 2, "0.00");
                if (!page.numPointlength(sakusan_suiso.forData, [3, 2])) {
                    calculItem = "水相中　酢酸酸度（％）";
                    maxChar = 5;
                    validate = true;
                    return false;
                }
                elementCol.findP("sakusan_suiso").val(sakusan_suiso.forDisp);
                TokuseiChi_Tab.detail.setUpdate(id, "sakusan_suiso", sakusan_suiso.forData);
                //水相中　ＭＳＧ含有率（％）
                msg_suiso = page.detail.formatNumber(page.detail.checkUndefi(msg_suiso), 2, "0.00");
                if (!page.numPointlength(msg_suiso.forData, [3, 2])) {
                    calculItem = "水相中　ＭＳＧ含有率（％）";
                    maxChar = 5;
                    validate = true;
                    return false;
                }
                elementCol.findP("msg_suiso").val(msg_suiso.forDisp);
                TokuseiChi_Tab.detail.setUpdate(id, "msg_suiso", msg_suiso.forData);
                //水相中　実効酢酸濃度（％）
                jikkoSakusanNodo = page.detail.formatNumber(page.detail.checkUndefi(jikkoSakusanNodo), 2, "0.00");
                if (!page.numPointlength(jikkoSakusanNodo.forData, [3, 2])) {
                    calculItem = "水相中　実効酢酸濃度（％）";
                    maxChar = 5;
                    validate = true;
                    return false;
                }
                elementCol.findP("jikkoSakusanNodo").val(jikkoSakusanNodo.forDisp);
                //TokuseiChi_Tab.detail.setUpdate(id, "jikkoSakusanNodo", jikkoSakusanNodo.forData);
                TokuseiChi_Tab.detail.setUpdate(id, "jikkoSakusanNodo", (jikkoSakusanNodo.forDisp != null && jikkoSakusanNodo.forDisp != "") ? jikkoSakusanNodo.forDisp.toString().replace(/,/g, "") : null);
                //水相中　非解離酢酸酸度（％）
                jikkoHikairiSakusanSando = page.detail.formatNumber(page.detail.checkUndefi(jikkoHikairiSakusanSando), 2, "0.00");
                if (!page.numPointlength(jikkoHikairiSakusanSando.forData, [3, 2])) {
                    calculItem = "水相中　非解離酢酸酸度（％）";
                    maxChar = 5;
                    validate = true;
                    return false;
                }
                elementCol.findP("jikkoHikairiSakusanSando").val(jikkoHikairiSakusanSando.forDisp);
                //TokuseiChi_Tab.detail.setUpdate(id, "jikkoHikairiSakusanSando", jikkoHikairiSakusanSando.forData);
                TokuseiChi_Tab.detail.setUpdate(id, "jikkoHikairiSakusanSando", (jikkoHikairiSakusanSando.forDisp != null && jikkoHikairiSakusanSando.forDisp != "") ? jikkoHikairiSakusanSando.forDisp.toString().replace(/,/g, "") : null);

                <%-- 19075 - 2022/06/06 - ShohinKaihatsuSuppot Modify 2022FY: -st --%>
                TokuseiChi_Tab.detail.changeHaigoKyodo(id);
                TokuseiChi_Tab.detail.changeRankBiseibutsu(id);
                <%-- -ed change text box to combobox --%>
            }

                //2019-09-11 : START : Bug #15327 特性値　食塩(%)が計算されない
            else {
                $.each(item, function (pro, val) {

                    if (pro.indexOf("quantity_") < 0) {
                        return true;
                    }

                    val = page.detail.checkUndefi(val);

                    var spli = pro.split("_"),
                     haigo = data.haigo[spli[0] + "_" + spli[1] + "_" + spli[2]];

                    if (!haigo) {
                        return true;
                    }
                    //各試作列のＭＳＧ量（配合量×ＭＳＧ）の合計値
                    //msgSokutei += page.detail.checkUndefi(val * page.detail.checkUndefi(haigo.ritu_msg));
                    msgSokutei = new BigNumber(stringNumbers(msgSokutei)).plus(stringNumbers(new BigNumber(stringNumbers(val)).times(stringNumbers(page.detail.checkUndefi(haigo.ritu_msg))).toNumber())).toNumber();
                    //各試作列の総酸量（配合量×総酸）の合計値
                    //sosanGokeiYyo += page.detail.checkUndefi(val * page.detail.checkUndefi(haigo.ritu_sousan));
                    sosanGokeiYyo = new BigNumber(stringNumbers(sosanGokeiYyo)).plus(stringNumbers(new BigNumber(stringNumbers(val)).times(stringNumbers(page.detail.checkUndefi(haigo.ritu_sousan))).toNumber())).toNumber();
                    //各試作列の食塩量（配合量×食塩）の合計値
                    //shokuenGokeiYyo += page.detail.checkUndefi(val * page.detail.checkUndefi(haigo.ritu_shokuen));
                    shokuenGokeiYyo = new BigNumber(stringNumbers(shokuenGokeiYyo)).plus(stringNumbers(new BigNumber(stringNumbers(val)).times(stringNumbers(page.detail.checkUndefi(haigo.ritu_shokuen))).toNumber())).toNumber();
                    ////各試作列の酢酸量（配合量×酢酸）の合計値
                    //sakuGokeiRyo += page.detail.checkUndefi(val * page.detail.checkUndefi(haigo.ritu_sakusan));
                    sakuGokeiRyo = new BigNumber(stringNumbers(sakuGokeiRyo)).plus(stringNumbers(new BigNumber(stringNumbers(val)).times(stringNumbers(page.detail.checkUndefi(haigo.ritu_sakusan))).toNumber())).toNumber();
                    //各試作列の油含有量（配合量×油含有率）の合計値
                    //aburaGokeiRyo += page.detail.checkUndefi(val * page.detail.checkUndefi(haigo.ritu_abura) / App.settings.app.keisansikiyo.value_100);
                    aburaGokeiRyo = new BigNumber(stringNumbers(aburaGokeiRyo)).plus(stringNumbers(new BigNumber(stringNumbers(val)).times(stringNumbers(page.detail.checkUndefi(haigo.ritu_abura))).div(stringNumbers(App.settings.app.keisansikiyo.value_100)).toNumber())).toNumber();
                })

                //各試作列の配合量の合計
                total_weight = page.detail.checkUndefi(item.total_weight);
                //総酸合計量÷合計重量
                //ritu_sousan = page.detail.checkUndefi(sosanGokeiYyo / total_weight);
                ritu_sousan = page.detail.checkUndefi(new BigNumber(stringNumbers(sosanGokeiYyo)).div(stringNumbers(total_weight)).toNumber());
                //食塩合計量÷合計重量
                //ritu_shokuen = page.detail.checkUndefi(shokuenGokeiYyo / total_weight);
                ritu_shokuen = page.detail.checkUndefi(new BigNumber(stringNumbers(shokuenGokeiYyo)).div(stringNumbers(total_weight)).toNumber());
                //総酸合計量÷（合計重量-油含有率合計量）
                //sando_suiso = page.detail.checkUndefi(sosanGokeiYyo / (total_weight - aburaGokeiRyo));
                sando_suiso = page.detail.checkUndefi(new BigNumber(stringNumbers(sosanGokeiYyo)).div(stringNumbers(new BigNumber(stringNumbers(total_weight)).minus(stringNumbers(aburaGokeiRyo)).toNumber())).toNumber());
                //食塩合計量÷（合計重量-油含有率合計量）
                //shokuen_suiso = page.detail.checkUndefi(shokuenGokeiYyo / (total_weight - aburaGokeiRyo));
                shokuen_suiso = page.detail.checkUndefi(new BigNumber(stringNumbers(shokuenGokeiYyo)).div(stringNumbers(new BigNumber(stringNumbers(total_weight)).minus(stringNumbers(aburaGokeiRyo)).toNumber())).toNumber());
                //酢酸合計量÷（合計重量-油含有率合計量）
                //sakusan_suiso = page.detail.checkUndefi(sakuGokeiRyo / (total_weight - aburaGokeiRyo));
                sakusan_suiso = page.detail.checkUndefi(new BigNumber(stringNumbers(sakuGokeiRyo)).div(stringNumbers(new BigNumber(stringNumbers(total_weight)).minus(stringNumbers(aburaGokeiRyo)).toNumber())).toNumber());

                //Bug #15197 : START
                //率ＭＳＧsakuGokeiRyo
                ritu_msg = page.detail.checkUndefi(new BigNumber(stringNumbers(msgSokutei)).div(stringNumbers(total_weight)).toNumber());

                var elementCol = TokuseiChi_Tab.detail.element.find(".sample-" + item.indexCol),
                    id = elementCol.attr("data-key-sample");
                //dataGenkaShisaku = elementCol.form().data();

                //Bug #15197 : START
                //率ＭＳＧ
                ritu_msg = page.detail.formatNumber(page.detail.checkUndefi(ritu_msg), 2);
                if (!page.numPointlength(ritu_msg.forData, [3, 2])) {
                    calculItem = "率ＭＳＧ";

                    maxChar = 5;
                    validate = true;
                    return false;
                }
                TokuseiChi_Tab.detail.setUpdate(id, "ritu_msg", ritu_msg.forData);
                //Bug #15197 : END

                //総酸（％）
                ritu_sousan = page.detail.formatNumber(page.detail.checkUndefi(ritu_sousan), 2, "0.00");
                if (!page.numPointlength(ritu_sousan.forData, [3, 2])) {
                    calculItem = "総酸（％）";

                    maxChar = 5;
                    validate = true;
                    return false;
                }
                elementCol.findP("ritu_sousan").val(ritu_sousan.forDisp);
                TokuseiChi_Tab.detail.setUpdate(id, "ritu_sousan", ritu_sousan.forData);
                //食塩（％）
                ritu_shokuen = page.detail.formatNumber(page.detail.checkUndefi(ritu_shokuen), 2, "0.00");
                if (!page.numPointlength(ritu_shokuen.forData, [3, 2])) {
                    calculItem = "食塩（％）";
                    maxChar = 5;
                    validate = true;
                    return false;
                }
                elementCol.findP("ritu_shokuen").val(ritu_shokuen.forDisp);
                TokuseiChi_Tab.detail.setUpdate(id, "ritu_shokuen", ritu_shokuen.forData);
                //水相中　酸度（％）
                sando_suiso = page.detail.formatNumber(page.detail.checkUndefi(sando_suiso), 2, "0.00");
                if (!page.numPointlength(sando_suiso.forData, [3, 2])) {
                    calculItem = "水相中　酸度（％）";
                    maxChar = 5;
                    validate = true;
                    return false;
                }
                elementCol.findP("sando_suiso").val(sando_suiso.forDisp);
                TokuseiChi_Tab.detail.setUpdate(id, "sando_suiso", sando_suiso.forData);
                //水相中　食塩（％）
                shokuen_suiso = page.detail.formatNumber(page.detail.checkUndefi(shokuen_suiso), 2, "0.00");
                if (!page.numPointlength(shokuen_suiso.forData, [3, 2])) {
                    calculItem = "水相中　食塩（％）";
                    maxChar = 5;
                    validate = true;
                    return false;
                }
                elementCol.findP("shokuen_suiso").val(shokuen_suiso.forDisp);
                TokuseiChi_Tab.detail.setUpdate(id, "shokuen_suiso", shokuen_suiso.forData);
                //水相中　酢酸酸度（％）
                sakusan_suiso = page.detail.formatNumber(page.detail.checkUndefi(sakusan_suiso), 2, "0.00");
                if (!page.numPointlength(sakusan_suiso.forData, [3, 2])) {
                    calculItem = "水相中　酢酸酸度（％）";
                    maxChar = 5;
                    validate = true;
                    return false;
                }
                elementCol.findP("sakusan_suiso").val(sakusan_suiso.forDisp);
                TokuseiChi_Tab.detail.setUpdate(id, "sakusan_suiso", sakusan_suiso.forData);
            }

            //2019-09-11 : END : Bug #15327 特性値　食塩(%)が計算されない

            //水相中酸度, 水相中食塩, 水相中酢酸
            if (!genryohiTotal[".sample-" + item.indexCol]) {
                genryohiTotal[".sample-" + item.indexCol] = {};
            }
            //総酸（％）
            genryohiTotal[".sample-" + item.indexCol]["ritu_sousan"] = ritu_sousan.forDisp;
            //食塩（％）
            genryohiTotal[".sample-" + item.indexCol]["ritu_shokuen"] = ritu_shokuen.forDisp;
            //水相中酸度
            genryohiTotal[".sample-" + item.indexCol]["sando_suiso"] = sando_suiso.forDisp;
            //水相中食塩
            genryohiTotal[".sample-" + item.indexCol]["shokuen_suiso"] = shokuen_suiso.forDisp;
            //水相中酢酸
            genryohiTotal[".sample-" + item.indexCol]["sakusan_suiso"] = sakusan_suiso.forDisp;
        });

        TokuseiChi_Tab.values.messTarget = TokuseiChi_Tab.detail.element.findP("batch_check");
        App.ui.page.notifyAlert.remove(TokuseiChi_Tab.values.messTarget);
        //計算結果は桁数がオーバーの場合、メッセージ表示。		
        if (validate) {
            TokuseiChi_Tab.values.messCalculator = App.str.format(App.messages.app.AP0009, { calculItem: calculItem, maxChar: maxChar, inputItems: inputItems });
            App.ui.page.notifyAlert.message(TokuseiChi_Tab.values.messCalculator, TokuseiChi_Tab.values.messTarget).show();
            App.ui.loading.close();
            return;
        } else {
            TokuseiChi_Tab.values.messCalculator = null;
            TokuseiChi_Tab.values.messTarget = null;
        }

        //水相中。。。を更新する
        if (App.isFunc(TokuseiChi_Tab.detail.updateOtherTab)) {
            TokuseiChi_Tab.detail.updateOtherTab("haigohyo_tab", "genryohiTotal", genryohiTotal);
        }

        if (App.isFunc(TokuseiChi_Tab.recalculation) && !TokuseiChi_Tab.detail.values.isPh) {
            //充填量水相
            TokuseiChi_Tab.recalculation("genkashisan_tab", "EventTokuseichi", data);
            TokuseiChi_Tab.detail.values.isPh = undefined;
        } else {
            App.ui.loading.close();
            TokuseiChi_Tab.detail.values.isPh = undefined;
        }
    };

    /**
    *製品比重の再計算を行う。
    */
    TokuseiChi_Tab.detail.recalcuHijuOnly = function (data) {
        var genryohiTotal = {},//原料費（ｋｇ）、原料費（１個）
            validate = false,
            inputItems = "配合量、ｐH",
            maxChar;

        $.each(data.shisaku, function (index, item) {

            var dblYusoHiju = App.settings.app.keisansikiyo.value_092,//油相比重
                dblSuisoHiju = 0,//水相比重
                dblYusoGokeiryo = 0,//油相合計重量(g) 
                dblSuisoGokeiryo = 0,//水相合計重量(g) 
                dblSakinGokeiryo = 0;//殺菌調味液重量(g)

            if (data.shisaku_hin.pt_kotei != App.settings.app.pt_kotei.sonohokaeki) {
                $.each(item, function (pro, val) {

                    if (pro.indexOf("quantity_") < 0) {
                        return true;
                    }

                    val = page.detail.checkUndefi(val);

                    var spli = pro.split("_"),
                     haigo = data.haigo[spli[0] + "_" + spli[1] + "_" + spli[2]];

                    if (!haigo) {
                        return true;
                    }

                    if (data.shisaku_hin.pt_kotei == App.settings.app.pt_kotei.chomieki_2 && data.shisaku_hin.cd_tani == App.settings.app.cd_tani.ml) {
                        //油相（リテラルマスタのvalue1 =1  AND value2 = 3）の「量」の合計　
                        //dblYusoGokeiryo += TokuseiChi_Tab.detail.findKoteiZokusei(haigo, val, App.settings.app.kote_value2.aburasho);
                        dblYusoGokeiryo = new BigNumber(stringNumbers(dblYusoGokeiryo)).plus(stringNumbers(TokuseiChi_Tab.detail.findKoteiZokusei(haigo, val, App.settings.app.kote_value2.aburasho))).toNumber();
                        //水相（リテラルマスタのvalue1 =1  AND value2 = 2）の「量」の合計　
                        //dblSuisoGokeiryo += TokuseiChi_Tab.detail.findKoteiZokusei(haigo, val, App.settings.app.kote_value2.suisho);
                        dblSuisoGokeiryo = new BigNumber(stringNumbers(dblSuisoGokeiryo)).plus(stringNumbers(TokuseiChi_Tab.detail.findKoteiZokusei(haigo, val, App.settings.app.kote_value2.suisho))).toNumber();
                        //殺菌調味液（リテラルマスタのvalue1 =1  AND value2 = 1）の「量」の合計　
                        //dblSakinGokeiryo += TokuseiChi_Tab.detail.findKoteiZokusei(haigo, val, App.settings.app.kote_value2.chomiryo);
                        dblSakinGokeiryo = new BigNumber(stringNumbers(dblSakinGokeiryo)).plus(stringNumbers(TokuseiChi_Tab.detail.findKoteiZokusei(haigo, val, App.settings.app.kote_value2.chomiryo))).toNumber();
                    }
                })

                var elementCol = TokuseiChi_Tab.detail.element.find(".sample-" + item.indexCol),
                    id = elementCol.attr("data-key-sample"),
                    dataGenkaShisaku = elementCol.form().data();

                //水相比重
                dblSuisoHiju = page.detail.checkUndefi(dataGenkaShisaku.hiju_sui);
                //工程パターン：調味料2液タイプ、容量（数値入力）（※）：ml
                if (data.shisaku_hin.pt_kotei == App.settings.app.pt_kotei.chomieki_2 && data.shisaku_hin.cd_tani == App.settings.app.cd_tani.ml) {

                    //{ 殺菌調味液重量(g) + ドレ水相合計重量(g) +ドレ油相合計重量(g) } / 
                    //{ [(殺菌調味液重量(g) + ドレ水相合計重量(g) )/水相比重] + [ドレ油相合計重量(g) / 0.92] }
                    //hiju = page.detail.checkUndefi((dblSakinGokeiryo + dblSuisoGokeiryo + dblYusoGokeiryo) / (((dblSakinGokeiryo + dblSuisoGokeiryo) / dblSuisoHiju) + (dblYusoGokeiryo / dblYusoHiju)));
                    var hiju = "";

                    //Bug #15230 : 計算に使用する項目が未入力の場合計算対象外として頂けません
                    if (checkEvalBeforCalcu([dataGenkaShisaku.hiju_sui])) {
                        hiju = page.detail.checkUndefi(new BigNumber(stringNumbers(dblSakinGokeiryo)).plus(stringNumbers(dblSuisoGokeiryo)).plus(stringNumbers(dblYusoGokeiryo)).div(stringNumbers(new BigNumber(stringNumbers(dblSakinGokeiryo)).plus(stringNumbers(dblSuisoGokeiryo)).div(stringNumbers(dblSuisoHiju)).plus(stringNumbers(new BigNumber(stringNumbers(dblYusoGokeiryo)).div(stringNumbers(dblYusoHiju)).toNumber())).toNumber())).toNumber());
                    }

                    //製品比重
                    hiju = page.detail.formatNumber(hiju, 3, undefined, 1000);
                    if (!page.stringPointlength(hiju.forData, { after: 3, max: 5 })) {
                        calculItem = "製品比重";
                        maxChar = 5;
                        validate = true;
                        return false;
                    }
                    elementCol.findP("hiju").val(hiju.forDisp == 0 ? "" : hiju.forDisp);
                    TokuseiChi_Tab.detail.setUpdate(id, "hiju", (hiju.forDisp != null && hiju.forDisp != "") ? hiju.forDisp.toString().replace(/,/g, "") : null);

                    if (App.isFunc(TokuseiChi_Tab.detail.updateOtherTab)) {
                        TokuseiChi_Tab.detail.updateOtherTab("genkashisan_tab", "hiju", hiju.forData, id);
                    }
                }
            }
        });

        TokuseiChi_Tab.values.messTarget = TokuseiChi_Tab.detail.element.findP("batch_check");
        App.ui.page.notifyAlert.remove(TokuseiChi_Tab.values.messTarget);
        //計算結果は桁数がオーバーの場合、メッセージ表示。		
        if (validate) {
            TokuseiChi_Tab.values.messCalculator = App.str.format(App.messages.app.AP0009, { calculItem: calculItem, maxChar: maxChar, inputItems: inputItems });
            App.ui.page.notifyAlert.message(TokuseiChi_Tab.values.messCalculator, TokuseiChi_Tab.values.messTarget).show();
            App.ui.loading.close();
            return;
        } else {
            TokuseiChi_Tab.values.messCalculator = null;
            TokuseiChi_Tab.values.messTarget = null;
        }

        if (App.isFunc(TokuseiChi_Tab.recalculation) && !TokuseiChi_Tab.detail.values.isPh) {
            //充填量水相
            TokuseiChi_Tab.recalculation("genkashisan_tab", "EventTokuseichi", data);
            TokuseiChi_Tab.detail.values.isPh = undefined;
        } else {
            App.ui.loading.close();
            TokuseiChi_Tab.detail.values.isPh = undefined;
        }
    };

    /**
    *再計算後にデータを更新する
    */
    TokuseiChi_Tab.detail.setUpdate = function (id, property, val) {
        //create new id
        if (!TokuseiChi_Tab.detail.data[id]) {
            TokuseiChi_Tab.detail.data[id] = {};
        };

        //update property
        TokuseiChi_Tab.detail.data[id][property] = val;
    }

    /**
    *殺菌調味液（リテラルマスタのvalue1 =1  AND value2 = 1）の「量」の合計　
    *水相（リテラルマスタのvalue1 =1  AND value2 = 2）の「量」の合計　
    *油相（リテラルマスタのvalue1 =1  AND value2 = 3）の「量」の合計　
    */
    TokuseiChi_Tab.detail.findKoteiZokusei = function (haigo, val, val2) {
        var check = jQuery.grep(page.values.masterData.KoteiZokusei, function (n, i) {
            return (n.cd_literal == haigo.zoku_kotei && n.value1 == App.settings.app.kote_value1.chomiryoType && n.value2 == val2);
        });

        if (check.length) {
            return page.detail.checkUndefi(val);
        } else {
            return 0;
        }
    }

    /**
    *カテゴリコード110のリテラル値1に登録されている品コードのシサクイックでの配合量
    *カテゴリコード110のリテラル値2に登録されている値
    */
    TokuseiChi_Tab.detail.findToroku = function (haigo, val, prop) {
        //カテゴリマスタ登録割合 : カテゴリコード110のリテラル値2に登録されている値
        var kakuShisakuRetsu = jQuery.grep(page.values.masterData.TorokuMeisho, function (n, i) {
            return (App.common.getFullString(n[prop], "000000") == haigo.cd_genryo);
        });

        //該当配合量 : カテゴリコード110のリテラル値1に登録されている品コードのシサクイックでの配合量
        var gaitoHaigoRyo = jQuery.grep(page.values.masterData.TorokuMeisho, function (n, i) {
            return (App.common.getFullString(n[prop], "000000") == haigo.cd_genryo);
        });

        if (kakuShisakuRetsu.length && gaitoHaigoRyo.length) {
            return (new BigNumber(stringNumbers(page.detail.checkUndefi(val))).times(stringNumbers(page.detail.checkUndefi(gaitoHaigoRyo[0]["value2"]))).toNumber());
        } else {
            return 0;
        }
    }

    /**
     * event change sample name
     */
    TokuseiChi_Tab.detail.changeSampleName = function (data) {
        var element = TokuseiChi_Tab.element;

        element.find(".sample-" + data.seq_shisaku + " .nm_sample").text(data.nm_sample);
    };

    /**
    * 工程パターンの変更イベント
    */
    TokuseiChi_Tab.changeKoteiPatan = function (patan, cd_tani, isRefresh) {

        if (!cd_tani) {
            cd_tani = "";
        }

        var element = TokuseiChi_Tab.detail.element;

        //製品比重
        //水相比重
        element.find(".hiju-hiju_sui").not(".shisanIraiked").val("").prop("readonly", true);

        switch (patan + "-" + cd_tani) {
            //調味料1液タイプ【ｍｌ】
            case App.settings.app.pt_kotei.chomieki_1 + "-" + App.settings.app.cd_tani.ml:
                element.find(".pt-kotei-display").addClass("pt-kotei-display-none");
                element.find(".chomieki-1-mL, .chomieki-1-2").removeClass("pt-kotei-display-none");
                //製品比重
                element.find(".hiju-input").not(".shisanIraiked").prop("readonly", false);
                break;
                //調味料1液タイプ【ｇ】
            case App.settings.app.pt_kotei.chomieki_1 + "-" + App.settings.app.cd_tani.gram:
            case App.settings.app.pt_kotei.chomieki_1 + "-":
                element.find(".pt-kotei-display").addClass("pt-kotei-display-none");
                element.find(".chomieki-1-g, .chomieki-1-2").removeClass("pt-kotei-display-none");
                break;
                //調味料2液タイプ【ｍｌ】
            case App.settings.app.pt_kotei.chomieki_2 + "-" + App.settings.app.cd_tani.ml:
            case App.settings.app.pt_kotei.chomieki_2 + "-":
                element.find(".pt-kotei-display").addClass("pt-kotei-display-none");
                element.find(".chomieki-2-mL, .chomieki-1-2").removeClass("pt-kotei-display-none");
                //製品比重
                element.find(".hiju_sui-input").not(".shisanIraiked").prop("readonly", false);
                break;
                //調味料2液タイプ【ｇ】
            case App.settings.app.pt_kotei.chomieki_2 + "-" + App.settings.app.cd_tani.gram:
                element.find(".pt-kotei-display").addClass("pt-kotei-display-none");
                element.find(".chomieki-2-g, .chomieki-1-2").removeClass("pt-kotei-display-none");
                break;
                //その他・加食タイプ
            default:
                element.find(".pt-kotei-display").addClass("pt-kotei-display-none");
                element.find(".sonohokaeki").removeClass("pt-kotei-display-none");
                break;
        }

        //「特性値」をクリアします
        if (isRefresh == "refresh") {
            TokuseiChi_Tab.detail.refresh();
        }
        //容量(数値入力)コンボボックスの更新時に製品比重も更新する
        if (isRefresh == "changeTani") {
            var shisakuHin = TokuseiChi_Tab.getDataHeader(),
                hiju = false,
                flg_auto = element.findP("flg_auto")[0].checked;

            if (shisakuHin.cd_tani == App.settings.app.cd_tani.gram) {
                hiju = true;//	製品比重;
            }

            $.each(TokuseiChi_Tab.detail.dataOriginal, function (prop, data) {
                if (prop == "HeadRow") {
                    //何もしない
                } else {
                    var col = element.find("." + prop);

                    if (!TokuseiChi_Tab.detail.data[prop]) {
                        TokuseiChi_Tab.detail.data[prop] = {};
                    };

                    dataHiju = null;
                    //update property
                    if (hiju) {
                        dataHiju = "1.000";
                    }

                    col.findP("hiju").val(dataHiju == null ? "" : dataHiju);
                    TokuseiChi_Tab.detail.data[prop]["hiju"] = dataHiju;
                    TokuseiChi_Tab.detail.data[prop]["hiju_sui"] = null;

                    //分析値を毎回自動計算
                    TokuseiChi_Tab.detail.data[prop]["flg_auto"] = flg_auto;

                    if (App.isFunc(TokuseiChi_Tab.detail.updateOtherTab)) {
                        TokuseiChi_Tab.detail.updateOtherTab("genkashisan_tab", "hiju", (dataHiju == null ? "" : dataHiju), prop);
                        TokuseiChi_Tab.detail.updateOtherTab("genkashisan_tab", "hiju_sui", null, prop);
                    }
                }
            })
        }
    };

    /**
    * 特性値」をクリアします
    */
    TokuseiChi_Tab.detail.refresh = function () {
        var element = TokuseiChi_Tab.detail.element,
            flg_auto = element.findP("flg_auto")[0].checked,
            hiju = false;

        //単位がmLの場合のみ表示、必須項目となる単位がgなら１
        var shisakuHin = TokuseiChi_Tab.getDataHeader();
        //shisakuHin.pt_kotei == App.settings.app.pt_kotei.chomieki_1 &&
        if (shisakuHin.cd_tani == App.settings.app.cd_tani.gram) {
            hiju = true;//	製品比重;
        }

        //renew data change
        TokuseiChi_Tab.detail.data = {};
        var dataHiju;
        //rebind table
        $.each(TokuseiChi_Tab.detail.dataOriginal, function (prop, data) {

            if (prop == "HeadRow") {
                TokuseiChi_Tab.detail.bindHeadRow(data, true);
            } else {

                var col = element.find("." + prop);
                col.find(":input").val("");
                col.form(TokuseiChi_Tab.detail.options.bindOption).bind(data);

				// 19075 - 2022/06/06 - ShohinKaihatsuSuppot Modify 2022FY -st
                TokuseiChi_Tab.detail.changeHaigoKyodo(prop);
                TokuseiChi_Tab.detail.changeRankBiseibutsu(prop);
				//-ed

                if (!TokuseiChi_Tab.detail.data[prop]) {
                    TokuseiChi_Tab.detail.data[prop] = {};
                };

                dataHiju = null;
                //update property
                if (hiju) {
                    dataHiju = "1.000";
                }

                col.findP("hiju").val(dataHiju == null ? "" : dataHiju);
                TokuseiChi_Tab.detail.data[prop]["hiju"] = dataHiju;

                //分析値を毎回自動計算
                TokuseiChi_Tab.detail.data[prop]["flg_auto"] = flg_auto;

                if (App.isFunc(TokuseiChi_Tab.detail.updateOtherTab)) {
                    TokuseiChi_Tab.detail.updateOtherTab("genkashisan_tab", "hiju", (dataHiju == null ? "" : dataHiju), prop);
                }
            }
        })

        //一括チェック／解除
        if (TokuseiChi_Tab.detail.element.findP("batch_check")[0].checked) {
            TokuseiChi_Tab.detail.element.findP("batch_check").change();
        }

        TokuseiChi_Tab.validateAll(true);
    }

    /**
     * マスターデータのロード処理を実行します。
     */
    TokuseiChi_Tab.detail.loadMasterData = function () {
        TokuseiChi_Tab.detail.data = {};
        TokuseiChi_Tab.detail.dataOriginal = {};
        TokuseiChi_Tab.detail.dataNew = {};


        var haigo_kyodo = TokuseiChi_Tab.element.findP("haigo_kyodo");
        haigo_kyodo.children().remove();
        App.ui.appendOptions(
            haigo_kyodo,
            "cd_literal",
             "nm_literal",
            TokuseiChi_Tab.dataHaigoKyodo,
            true
        );

        // 19075 - 2022/06/06 - ShohinKaihatsuSuppot Modify 2022FY: -st change textbox to combobox
        var rank_biseibutsu = TokuseiChi_Tab.element.findP("rank_biseibutsu");
        rank_biseibutsu.children().remove();
        App.ui.appendOptions(
            rank_biseibutsu,
            "value",
             "text",
            App.settings.app.dataRankBiseibutsu,
            true
        );
        // -ed change textbox to combobox

        //control display detail row
        TokuseiChi_Tab.changeKoteiPatan(TokuseiChi_Tab.values.pt_kotei, TokuseiChi_Tab.values.cd_tani, "loadMasterData");
    };

    /**
     * ダイアログヘッダー　バリデーションルールを定義します。
     */
    TokuseiChi_Tab.detail.options.validations = {
        ritu_sousan_bunseki: {
            rules: {
                numberBug15481: true
            },
            options: {
                name: "総酸（％）分析値"
            },
            messages: {
                numberBug15481: App.messages.base.number
            }
        },
        ritu_shokuen_bunseki: {
            rules: {
                numberBug15481: true
            },
            options: {
                name: "食塩（％）分析値"
            },
            messages: {
                numberBug15481: App.messages.base.number
            }
        },
        ph: {
            rules: {
                numberBug15481: true
            },
            options: {
                name: "pH"
            },
            messages: {
                numberBug15481: App.messages.base.number
            }
        },
        nendo_cream: {
            rules: {
                numberBug15481: true
            },
            options: {
                name: "充填前（クリーム）粘度"
            },
            messages: {
                numberBug15481: App.messages.base.number
            }
        },
        ondo_cream: {
            rules: {
                numberBug15481: true
            },
            options: {
                name: "充填前（クリーム）粘度－測定時品温（℃）"
            },
            messages: {
                numberBug15481: App.messages.base.number
            }
        },
        no_rotor_cream: {
            rules: {
                haneisukigo: true,
                maxlength: 2
            },
            options: {
                name: "充填前（クリーム）粘度－ローターＮｏ．"
            },
            messages: {
                haneisukigo: App.messages.base.haneisukigo,
                maxlength: App.messages.base.maxlength
            }
        },
        speed_cream: {
            rules: {
                number: true,
                min: 0
            },
            options: {
                name: "充填前（クリーム）粘度－スピード（ｒｐｍ）"
            },
            messages: {
                number: App.messages.base.number,
                min: App.str.format(App.messages.app.AP0140, { param: 0, name: "充填前（クリーム）粘度－スピード（ｒｐｍ）" })
            }
        },
        nendo: {
            rules: {
                numberBug15481: true
            },
            options: {
                name: "粘度（製品）"
            },
            messages: {
                numberBug15481: App.messages.base.number
            }
        },
        ondo: {
            rules: {
                numberBug15481: true
            },
            options: {
                name: "粘度（製品）－測定時品温（℃）"
            },
            messages: {
                numberBug15481: App.messages.base.number
            }
        },
        no_rotor: {
            rules: {
                haneisukigo: true,
                maxlength: 2
            },
            options: {
                name: "粘度（製品）－ローターＮｏ．"
            },
            messages: {
                haneisukigo: App.messages.base.haneisukigo,
                maxlength: App.messages.base.maxlength
            }
        },
        speed: {
            rules: {
                number: true,
                min: 0
            },
            options: {
                name: "粘度（製品）－スピード（ｒｐｍ）"
            },
            messages: {
                number: App.messages.base.number,
                min: App.str.format(App.messages.app.AP0140, { param: 0, name: "粘度（製品）－スピード（ｒｐｍ）" })
            }
        },
        Brix: {
            rules: {
                numberBug15481: true,
                pointlengthBug15481: [2, 1]
            },
            options: {
                name: "Brix（％）"
            },
            messages: {
                numberBug15481: App.messages.base.number,
                pointlengthBug15481: App.messages.base.pointlength
            }
        },
        AW: {
            rules: {
                numberBug15481: true,
                pointlengthBug15481: [1, 3]
            },
            options: {
                name: "Ａｗ"
            },
            messages: {
                numberBug15481: App.messages.base.number,
                pointlengthBug15481: App.messages.base.pointlength
            }
        },

        hiju: {
            rules: {
                numberBug15481: true,
                pointlengthBug15481: [1, 3],
                check_required: function (value, param, otps, done) {

                    //not event save button
                    if (!otps.isSave) {
                        //for change event
                        if ($.trim(value).length && value <= 0) {
                            return done(false);
                        }
                        return done(true);
                    }

                    var check = false;

                    if (!value || App.isUndefOrNull(value)) {
                        check = true;
                    }

                    if (!$.trim(value).length || value <= 0) {
                        check = true;
                    }

                    if (check) {

                        var datashisahin = TokuseiChi_Tab.getDataHeader();
                        //調味料1液タイプ（単位がml）
                        if (datashisahin.pt_kotei == App.settings.app.pt_kotei.chomieki_1 && datashisahin.cd_tani == App.settings.app.cd_tani.ml) {
                            if (!otps.targets.hasClass("iraiChecked")) {
                                return done(true);
                            };

                            return done(false);
                        }
                        return done(true);
                    } else {

                        return done(true);
                    }
                },
                //ShohinKaihatsuSuppot Modify 2022FY: -st required when open dialog 606
                check_required_from_606_dialog: function (value, param, otps, done) {
                    if (otps.is606) {
                        var datashisahin = TokuseiChi_Tab.getDataHeader();
                        if (datashisahin.pt_kotei == App.settings.app.pt_kotei.chomieki_1 && datashisahin.cd_tani == App.settings.app.cd_tani.ml && App.isUndefOrNullOrStrEmpty(value)) {
                            return done(false);
                        }
                    }

                    return done(true);
                }
                //-ed
            },
            options: {
                name: "製品比重"
            },
            messages: {
                numberBug15481: App.messages.base.number,
                pointlengthBug15481: App.messages.app.AP0155.replace("{param[0]}", "1").replace("{param[1]}", "3"),
                check_required: App.messages.app.AP0155.replace("{param[0]}", "1").replace("{param[1]}", "3"),
                check_required_from_606_dialog: App.messages.app.AP0155.replace("{param[0]}", "1").replace("{param[1]}", "3")
            }
        },

        //2019-10-07 : START :Request #15485 総酸と食塩の分析値の項目名
        //工程パターンが調味料2液充填のときの、以下の特性値の項目名を変更してください。
        //※調味料1液充填は、あっています
        //2020-06-18 : revert Request #15485

        ritu_sando_bunseki_suiso: {
            rules: {
                number: true
            },
            options: {
                name: "水相中　酸度（％）分析値"
            },
            messages: {
                number: App.messages.base.number
            }
        },
        ritu_shokuen_bunseki_suiso: {
            rules: {
                number: true
            },
            options: {
                name: "水相中　食塩（％）分析値"
            },
            messages: {
                number: App.messages.base.number
            }
        },

        //2019-10-07 : END :Request #15485 総酸と食塩の分析値の項目名
        //工程パターンが調味料2液充填のときの、以下の特性値の項目名を変更してください。
        //※調味料1液充填は、あっています

        rank_biseibutsu: {
            rules: {
                maxlength: 1
            },
            options: {
                name: "微生物ランク"
            },
            messages: {
                maxlength: App.messages.base.maxlength
            }
        },
        hiju_sui: {
            rules: {
                numberBug15481: true,
                pointlengthBug15481: [1, 8],
                check_required: function (value, param, otps, done) {
                    //not event save button
                    if (!otps.isSave) {
                        //for change event
                        if ($.trim(value).length && value <= 0) {
                            return done(false);
                        }
                        return done(true);
                    }

                    var check = false;

                    if (!value || App.isUndefOrNull(value)) {
                        check = true;
                    }

                    if (!$.trim(value).length || value <= 0) {
                        check = true;
                    }

                    if (check) {

                        var datashisahin = TokuseiChi_Tab.getDataHeader();
                        //調味料1液タイプ（単位がml）
                        if (datashisahin.pt_kotei == App.settings.app.pt_kotei.chomieki_2 && datashisahin.cd_tani == App.settings.app.cd_tani.ml) {

                            if (!otps.targets.hasClass("iraiChecked")) {
                                return done(true);
                            };

                            return done(false);
                        }
                        return done(true);
                    } else {

                        return done(true);
                    }
                },
                //ShohinKaihatsuSuppot Modify 2022FY: -st required when open dialog 606
                check_required_from_606_dialog: function (value, param, otps, done) {
                    if (otps.is606) {
                        var datashisahin = TokuseiChi_Tab.getDataHeader();
                        if (datashisahin.pt_kotei == App.settings.app.pt_kotei.chomieki_2 && datashisahin.cd_tani == App.settings.app.cd_tani.ml && App.isUndefOrNullOrStrEmpty(value)) {
                            return done(false);
                        }
                    }

                    return done(true);
                }
                //-ed
            },
            options: {
                name: "水相比重"
            },
            messages: {
                numberBug15481: App.messages.base.number,
                pointlengthBug15481: App.messages.app.AP0155.replace("{param[0]}", "1").replace("{param[1]}", "8"),
                check_required: App.messages.app.AP0155.replace("{param[0]}", "1").replace("{param[1]}", "8"),
                check_required_from_606_dialog: App.messages.app.AP0155.replace("{param[0]}", "1").replace("{param[1]}", "8")
            }
        },
        free_title1: {
            rules: {
                maxlength: 30,
                check_single_kotations: true
            },
            options: {
                name: "フリータイトル①"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_value1: {
            rules: {
                maxlength: 20,
                check_single_kotations: true
            },
            options: {
                name: "フリー内容①"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_title2: {
            rules: {
                maxlength: 30,
                check_single_kotations: true
            },
            options: {
                name: "フリータイトル②"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_value2: {
            rules: {
                maxlength: 20,
                check_single_kotations: true
            },
            options: {
                name: "フリー内容②"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_title3: {
            rules: {
                maxlength: 30,
                check_single_kotations: true
            },
            options: {
                name: "フリータイトル③"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_value3: {
            rules: {
                maxlength: 20,
                check_single_kotations: true
            },
            options: {
                name: "フリー内容③"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_title4: {
            rules: {
                maxlength: 30,
                check_single_kotations: true
            },
            options: {
                name: "フリータイトル④"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_value4: {
            rules: {
                maxlength: 20,
                check_single_kotations: true
            },
            options: {
                name: "フリー内容④"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_title5: {
            rules: {
                maxlength: 30,
                check_single_kotations: true
            },
            options: {
                name: "フリータイトル⑤"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_value5: {
            rules: {
                maxlength: 20,
                check_single_kotations: true
            },
            options: {
                name: "フリー内容⑤"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_title6: {
            rules: {
                maxlength: 30,
                check_single_kotations: true
            },
            options: {
                name: "フリータイトル⑥"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_value6: {
            rules: {
                maxlength: 20,
                check_single_kotations: true
            },
            options: {
                name: "フリー内容⑥"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_title7: {
            rules: {
                maxlength: 30,
                check_single_kotations: true
            },
            options: {
                name: "フリータイトル⑦"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_value7: {
            rules: {
                maxlength: 20,
                check_single_kotations: true
            },
            options: {
                name: "フリー内容⑦"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_title8: {
            rules: {
                maxlength: 30,
                check_single_kotations: true
            },
            options: {
                name: "フリータイトル⑧"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_value8: {
            rules: {
                maxlength: 20,
                check_single_kotations: true
            },
            options: {
                name: "フリー内容⑧"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_title9: {
            rules: {
                maxlength: 30,
                check_single_kotations: true
            },
            options: {
                name: "フリータイトル⑨"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_value9: {
            rules: {
                maxlength: 20,
                check_single_kotations: true
            },
            options: {
                name: "フリー内容⑨"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_title10: {
            rules: {
                maxlength: 30,
                check_single_kotations: true
            },
            options: {
                name: "フリータイトル⑩"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_value10: {
            rules: {
                maxlength: 20,
                check_single_kotations: true
            },
            options: {
                name: "フリー内容⑩"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_title11: {
            rules: {
                maxlength: 30,
                check_single_kotations: true
            },
            options: {
                name: "フリータイトル⑪"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_value11: {
            rules: {
                maxlength: 20,
                check_single_kotations: true
            },
            options: {
                name: "フリー内容⑪"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_title12: {
            rules: {
                maxlength: 30,
                check_single_kotations: true
            },
            options: {
                name: "フリータイトル⑫"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_value12: {
            rules: {
                maxlength: 20,
                check_single_kotations: true
            },
            options: {
                name: "フリー内容⑫"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_title13: {
            rules: {
                maxlength: 30,
                check_single_kotations: true
            },
            options: {
                name: "フリータイトル⑬"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_value13: {
            rules: {
                maxlength: 20,
                check_single_kotations: true
            },
            options: {
                name: "フリー内容⑬"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_title14: {
            rules: {
                maxlength: 30,
                check_single_kotations: true
            },
            options: {
                name: "フリータイトル⑭"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_value14: {
            rules: {
                maxlength: 20,
                check_single_kotations: true
            },
            options: {
                name: "フリー内容⑭"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_title15: {
            rules: {
                maxlength: 30,
                check_single_kotations: true
            },
            options: {
                name: "フリータイトル⑮"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_value15: {
            rules: {
                maxlength: 20,
                check_single_kotations: true
            },
            options: {
                name: "フリー内容⑮"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_title16: {
            rules: {
                maxlength: 30,
                check_single_kotations: true
            },
            options: {
                name: "フリータイトル⑯"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_value16: {
            rules: {
                maxlength: 20,
                check_single_kotations: true
            },
            options: {
                name: "フリー内容⑯"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_title17: {
            rules: {
                maxlength: 30,
                check_single_kotations: true
            },
            options: {
                name: "フリータイトル⑰"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_value17: {
            rules: {
                maxlength: 20,
                check_single_kotations: true
            },
            options: {
                name: "フリー内容⑰"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_title18: {
            rules: {
                maxlength: 30,
                check_single_kotations: true
            },
            options: {
                name: "フリータイトル⑱"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_value18: {
            rules: {
                maxlength: 20,
                check_single_kotations: true
            },
            options: {
                name: "フリー内容⑱"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_title19: {
            rules: {
                maxlength: 30,
                check_single_kotations: true
            },
            options: {
                name: "フリータイトル⑲"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_value19: {
            rules: {
                maxlength: 20,
                check_single_kotations: true
            },
            options: {
                name: "フリー内容⑲"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_title20: {
            rules: {
                maxlength: 30,
                check_single_kotations: true
            },
            options: {
                name: "フリータイトル⑳"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        free_value20: {
            rules: {
                maxlength: 20,
                check_single_kotations: true
            },
            options: {
                name: "フリー内容⑳"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        memo_sakusei: {
            rules: {
                maxlength: 150,
                check_single_kotations: true
            },
            options: {
                name: "作成メモ"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        hyoka: {
            rules: {
                maxlength: 300,
                check_single_kotations: true
            },
            options: {
                name: "評価"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        }
    };

    /**
     * 画面明細の初期化処理を行います。
     */
    TokuseiChi_Tab.detail.initialize = function () {

        var element = TokuseiChi_Tab.element,
            table = element.find(".datatable"),
            offsetHeight = $(window).height() * 15 / 100,
            datatable = table.dataTable({
                height: 100,
                resize: true,
                fixedColumn: true,
                fixedColumns: 1,
                innerWidth: 0,
                resizeOffset: 60,
                onchange: TokuseiChi_Tab.detail.change
                //onselect: TokuseiChi_Tab.detail.select
            });
        table = element.find(".datatable");        //列固定にした場合DOM要素が再作成されるため、変数を再取得

        //2019-09-10 : START : Bug #15319 選択行の色
        table.on("focusin", "td", function (e) {
            if ($(e.target).parents().hasClass("Header")) {
                table.find(".selectedInput").removeClass("selectedInput");
                return;
            }
            if ($(e.target).parents().hasClass("col-sample")) {
                table.find(".selectedInput").removeClass("selectedInput");
                $(e.target).addClass("selectedInput");
            }
        });

        element.on("click", "thead", function (e) {
            element.find(".selectedInput").removeClass("selectedInput");
            $(e.target).addClass("selectedInput");
        });
        //2019-09-10 : START : Bug #15319 選択行の色

        TokuseiChi_Tab.detail.validator = element.validation(TokuseiChi_Tab.createValidator(TokuseiChi_Tab.detail.options.validations));
        TokuseiChi_Tab.detail.element = element;
        TokuseiChi_Tab.detail.dataTable = datatable;

        //Define manual area
        var element_head_sample = element.find('.dt-container .flow-container .dt-head table thead tr');
        TokuseiChi_Tab.detail.element_head_sample = element_head_sample;

        element.on("change", ".check-on-head", TokuseiChi_Tab.detail.changeAllHead);

        // 行選択時に利用するテーブルインデックスを指定します
        TokuseiChi_Tab.detail.fixedColumnIndex = element.find(".fix-columns").length;

        TokuseiChi_Tab.detail.bind();

        //選択した試作列のスクロールバーの位置情報を保持し、他のタブへ移動後、スクロールバーの位置を保持していた位置に移動する
        $("#TokuseiChi_Tab .dt-container .flow-container .dt-body").scroll(function () {
            if (TokuseiChi_Tab.detail.values.offscroll) {
                page.values.positionLeft = $(this).scrollLeft();
            }
        });

        //次焦点が作りする
        element.findP("batch_check").on("keydown", function (e) {
            var code = e.keyCode || e.which;
            if (!e.shiftKey && code === 9) {
                setTimeout(function () {
                    element.find(".dt-fix-body .datatable .new").find('input:enabled:visible:first').focus();
                }, 1)
            }
        })

        $("#HenkoKakunin").on("keydown", function (e) {
            var code = e.keyCode || e.which;
            if (e.shiftKey && code === 9) {
                setTimeout(function () {
                    element.find(".dt-container .flow-container .dt-body .new").find('textarea:enabled:visible:last').focus();
                }, 1)
            }
        })

        table.on("click", "tr", TokuseiChi_Tab.detail.select);
    };

    //選択した試作列のスクロールバーの位置情報を保持し、他のタブへ移動後、スクロールバーの位置を保持していた位置に移動する
    $('#tokuseichi_tab').on('shown.bs.tab', function (e) {
        TokuseiChi_Tab.detail.values.offscroll = false;
        var scrollLeft = $("#TokuseiChi_Tab .dt-container .flow-container .dt-body").scrollLeft();
        if (scrollLeft != page.values.positionLeft) {
            $("#TokuseiChi_Tab .dt-container .flow-container .dt-body").scrollLeft(page.values.positionLeft);
        }

        setTimeout(function () {
            TokuseiChi_Tab.detail.values.offscroll = true;
        }, 1)
    })

    /**
    * move column data table
    */
    TokuseiChi_Tab.detail.moveColumn = function (table, from, to) {
        var cols;
        jQuery('tr', table).each(function () {
            cols = jQuery(this).children('th, td');
            cols.eq(from).detach().insertBefore(cols.eq(to));
        });
    }

    /**
    * move sample to left
    */
    TokuseiChi_Tab.detail.moveSample = function (index, isRight) {
        var element = TokuseiChi_Tab.detail.element,
            dt_head = TokuseiChi_Tab.detail.element_head_sample,
            head_check = dt_head.find(".col-sample"),
            selectCol = dt_head.find(".sample-" + index);


        var table = element.find(".dt-container .flow-container table"),
            iColMove = head_check.index(selectCol);

        if (iColMove == 0) {
            return;
        }

        //move column
        if (isRight) {
            TokuseiChi_Tab.detail.moveColumn(table, iColMove + 1, iColMove);
        } else {
            TokuseiChi_Tab.detail.moveColumn(table, iColMove, iColMove - 1);
        }

    };

    /**
    * del col with data from haigo tab。
    */
    TokuseiChi_Tab.detail.delSample = function (data) {
        var element = TokuseiChi_Tab.detail.element.find(".sample-" + data.seq_shisaku);

        //remove mess error
        element.find(".has-error, .error").each(function (i, elem) {
            App.ui.page.notifyAlert.remove(elem);
        });

        element.remove();

        //delete sample item in data tab
        if (TokuseiChi_Tab.detail.data["sample-" + data.seq_shisaku]) {
            delete TokuseiChi_Tab.detail.data["sample-" + data.seq_shisaku];
        }

        if (TokuseiChi_Tab.detail.dataOriginal["sample-" + data.seq_shisaku]) {
            delete TokuseiChi_Tab.detail.dataOriginal["sample-" + data.seq_shisaku];
        }

        if (TokuseiChi_Tab.detail.dataNew["sample-" + data.seq_shisaku]) {
            delete TokuseiChi_Tab.detail.dataNew["sample-" + data.seq_shisaku];
        }
        //テーブルでサイズ変更
        TokuseiChi_Tab.detail.adjustWithTable(-1);
    };

    /**
    * 新しいサンプル列を追加する。
    */
    TokuseiChi_Tab.detail.addSample = function (sample, isEndCol, indexCol, isNewData) {

        if (TokuseiChi_Tab.detail.firstCol) {

            TokuseiChi_Tab.detail.firstCol = false;
            TokuseiChi_Tab.detail.bindHeadRow(sample);

            // 分析値を毎回自動計算
            TokuseiChi_Tab.detail.element.findP("flg_auto").prop("checked", sample.flg_auto);

            //save data original for hed check
            TokuseiChi_Tab.detail.dataOriginal["HeadRow"] = $.extend(true, {}, sample);
        }

        var element = TokuseiChi_Tab.detail.element,
            dt_head = TokuseiChi_Tab.detail.element_head_sample,
            head_check = dt_head.find('.th-tmpl'),
            iColCopy = 0, iColInset = 0;

        if (head_check.length == 0) {
            return;
        }

        if (isEndCol == true) {
            iColInset = dt_head.find("input").length - 1;
        }

        if (indexCol) {
            head_check = dt_head.find("input");
            indexCol = dt_head.find(".sample-" + indexCol).find("input");
            iColInset = head_check.index(indexCol);
        }

        var table = element.find(".dt-container .flow-container table"),
            cols, th, colsClone, area;

        jQuery('tr', table).each(function () {
            cols = jQuery(this).children('th, td');
            colsClone = cols.eq(iColCopy).clone().removeClass("th-tmpl");

            area = "sample-" + sample.seq_shisaku;

            colsClone.addClass(area).attr("data-key-sample", area);

            colsClone.insertAfter(cols.eq(iColInset));
        });
        var colElem = element.find("." + area);
        colElem.form(TokuseiChi_Tab.detail.options.bindOption).bind(sample);

        //save data original for col sample
        TokuseiChi_Tab.detail.dataOriginal[area] = $.extend(true, {}, sample);

        if (isNewData) {
            TokuseiChi_Tab.detail.dataNew[area] = 1;
        }

        //原価試作依頼されている原料を含む行（配合量に値が入っている行）は変更できない 
        if (sample.flg_shisanIrai) {
            //element.find("." + area).find(":input").prop("disabled", true);
            colElem.find(".hiju-hiju_sui").addClass("shisanIraiked");
        }

        //次焦点が作りする : 評価
        colElem.findP("hyoka").on("keydown", function (e) {
            var code = e.keyCode || e.which,
                index;
            if (!e.shiftKey && code === 9) {
                index = element.find(e.target).closest("td").next();
                if (!index.length) {
                    setTimeout(function () {
                        $("#command_left, #command_right").find(':button:enabled:visible:first').focus();
                    }, 1)
                }
            }
        })

        //次焦点が作りする : 総酸（％）
        colElem.findP("ritu_sousan").on("keydown", function (e) {
            var code = e.keyCode || e.which,
                index;

            if (e.shiftKey && e.which == 9) {
                index = element.find(e.target).closest("td").prev().not(".th-tmpl");
                if (!index.length) {
                    setTimeout(function () {
                        element.findP("flg_sousan").focus();
                    }, 10)
                }
            }
        })

        //次焦点が作りする : 総酸（％）分析値
        colElem.findP("ritu_sousan_bunseki").on("keydown", function (e) {
            var code = e.keyCode || e.which,
                index;

            if (e.shiftKey && e.which == 9) {
                index = element.find(e.target).closest("td").prev().not(".th-tmpl");
                if (!index.length) {
                    setTimeout(function () {
                        element.findP("flg_sousan_bunseki").focus();
                    }, 10)
                }
            }
        })

        // 19075 - 2022/06/06 - ShohinKaihatsuSuppot Modify 2022FY -st
        if (isNewData) {
            TokuseiChi_Tab.detail.changeHaigoKyodo(area);
            TokuseiChi_Tab.detail.changeRankBiseibutsu(area);
        }
		// -ed

    }

    /**
    * 特徴コピー時にタブをリロードする
    */
    TokuseiChi_Tab.reLoadTab = function () {
        var element = TokuseiChi_Tab.detail.element,
           dt_head = TokuseiChi_Tab.detail.element_head_sample,
           head_check = dt_head.find(".col-sample");

        //renew data set
        TokuseiChi_Tab.detail.data = {};
        TokuseiChi_Tab.detail.dataOriginal = {};
        TokuseiChi_Tab.detail.dataNew = {};

        //clear table
        TokuseiChi_Tab.detail.dataTable.dataTable("clear");

        //del all col
        var data_key, ele;
        $.each(head_check, function (index, item) {
            ele = element.find(item);
            data_key = ele.attr("data-key-sample");

            if (data_key) {
                element.find("." + data_key).remove();

                //テーブルでサイズ変更
                TokuseiChi_Tab.detail.adjustWithTable(-1);
            }
        })

        //renew header
        TokuseiChi_Tab.detail.dataTable.dataTable("addRow", function (row) {//次焦点が作りする : 評価
            row.findP("hyoka").on("keydown", function (e) {
                var code = e.keyCode || e.which,
                    index;
                if (!e.shiftKey && code === 9) {
                    index = row.find(e.target).next();
                    if (!index.length) {
                        setTimeout(function () {
                            $("#command_left, #command_right").find(':button:enabled:visible:first').focus();
                        }, 1)
                    }
                }
            })

            //次焦点が作りする : 評価, 総酸（％）分析値
            row.find(".prev_focus").on("keydown", function (e) {
                var code = e.keyCode || e.which,
                    index;

                if (e.shiftKey && e.which == 9) {
                    index = row.find(e.target).attr("data-prop");
                    if ((TokuseiChi_Tab.values.pt_kotei == App.settings.app.pt_kotei.sonohokaeki || TokuseiChi_Tab.values.pt_kotei == "") && index == "flg_sousan_bunseki") {
                        element.findP("batch_check").focus();
                    }
                    if ((TokuseiChi_Tab.values.pt_kotei != App.settings.app.pt_kotei.sonohokaeki && TokuseiChi_Tab.values.pt_kotei != "") && index == "flg_sousan") {
                        element.findP("batch_check").focus();
                    }
                }
            })
            return row;
        });

        element.find(".shisanIraiked").removeClass("shisanIraiked");
    }


    /**
    * ダイアログ明細へのデータバインド処理を行います。
    */
    TokuseiChi_Tab.detail.bind = function () {
        var element = TokuseiChi_Tab.element;

        TokuseiChi_Tab.detail.data = {};
        TokuseiChi_Tab.detail.dataOriginal = {};
        TokuseiChi_Tab.detail.dataNew = {};

        TokuseiChi_Tab.detail.dataTable.dataTable("clear");

        TokuseiChi_Tab.detail.dataTable.dataTable("addRow", function (row) {

            //次焦点が作りする : 評価
            row.findP("hyoka").on("keydown", function (e) {
                var code = e.keyCode || e.which,
                    index;
                if (!e.shiftKey && code === 9) {
                    index = row.find(e.target).next();
                    if (!index.length) {
                        setTimeout(function () {
                            $("#command_left, #command_right").find(':button:enabled:visible:first').focus();
                        }, 1)
                    }
                }
            })

            //次焦点が作りする : 評価, 総酸（％）分析値
            row.find(".prev_focus").on("keydown", function (e) {
                var code = e.keyCode || e.which,
                    index;

                if (e.shiftKey && e.which == 9) {
                    index = row.find(e.target).attr("data-prop");
                    if ((TokuseiChi_Tab.values.pt_kotei == App.settings.app.pt_kotei.sonohokaeki || TokuseiChi_Tab.values.pt_kotei == "") && index == "flg_sousan_bunseki") {
                        element.findP("batch_check").focus();
                    }
                    if ((TokuseiChi_Tab.values.pt_kotei != App.settings.app.pt_kotei.sonohokaeki && TokuseiChi_Tab.values.pt_kotei != "") && index == "flg_sousan") {
                        element.findP("batch_check").focus();
                    }
                }
            })
            return row;
        });
    };

    /**
    * bind check head。
    */
    TokuseiChi_Tab.detail.bindHeadRow = function (sample, isRefresh) {
        var data = $.extend(true, {}, sample),
            element = TokuseiChi_Tab.detail.element.find(".new .Header");

        if (isRefresh) {
            //reset all titel input free
            TokuseiChi_Tab.detail.element.find(".new .free_title").val("");

            //reset all checkbok
            element.find(":checkbox").prop("checked", false);
        }

        element.form().bind(data);
    };

    /**
    * テーブルでサイズ変更
    */
    TokuseiChi_Tab.detail.adjustWithTable = function (col) {
        var element = TokuseiChi_Tab.detail.element,
            theadTable = element.find(".dt-container .flow-container .dt-head .datatable"),
            theadWith = theadTable.css("width").replace("px", ""),
            bodyTable = element.find(".dt-container .flow-container .dt-body .datatable");

        theadTable.css("width", (parseFloat(theadWith) + (135 * col)) + "px");
        theadTable.css("max-width", (parseFloat(theadWith) + (135 * col)) + "px");
        bodyTable.css("width", (parseFloat(theadWith) + (135 * col)) + "px");
        bodyTable.css("max-width", (parseFloat(theadWith) + (135 * col)) + "px");

    };

    /**
     * 明細の各行にデータを設定する際のオプションを定義します。
     */
    TokuseiChi_Tab.detail.options.bindOption = {
        // TODO: 主キーが直接入力の場合には、修正の場合変更を不可とします。
        appliers: {
            //総酸（％）
            ritu_sousan: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 2).forDisp);
                return true;
            },
            //食塩（％）
            ritu_shokuen: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 2).forDisp);
                return true;
            },
            //水相中　酸度（％）
            sando_suiso: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 2).forDisp);
                return true;
            },
            //水相中　食塩（％）
            shokuen_suiso: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 2).forDisp);
                return true;
            },
            //水相中　酢酸酸度（％）
            sakusan_suiso: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 2).forDisp);
                return true;
            },
            //水相中　ＭＳＧ含有率（％）
            msg_suiso: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }

                element.val(page.detail.formatNumber(value, 2).forDisp);
                return true;
            },
            //水相中　実効酢酸濃度（％）
            jikkoSakusanNodo: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 2).forDisp);
                return true;
            },
            //水相中　非解離酢酸酸度（％）
            jikkoHikairiSakusanSando: function (value, element) {
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 2).forDisp);
                return true;
            },
            //製品比重
            hiju: function (value, element) {
                if (value == 0) {
                    element.val("0.000");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 3).forDisp);
                return true;
            },
            //水相比重
            //Request #15081 START : 【101_試作データ　②特性値タブ】水相比重について
            //整数1桁、小数8桁でお願いいたします。
            //また1.5のような値が入力された場合は、1.50000000ではなく1.5と表示するようにしていただきたいです。
            //hiju_sui: function (value, element) {
            //    if (value == 0) {
            //        element.val("0.000");
            //        return true;
            //    }
            //    element.val(page.detail.formatNumber(value, 3).forDisp);
            //    return true;
            //},
            //Request #15081 END : 【101_試作データ　②特性値タブ】水相比重について
            //整数1桁、小数8桁でお願いいたします。
            //また1.5のような値が入力された場合は、1.50000000ではなく1.5と表示するようにしていただきたいです。
            //Brix（％）
            Brix: function (value, element) {
                if (value == 0) {
                    element.val("0.0");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 1).forDisp);
                return true;
            },
            //Ａｗ
            AW: function (value, element) {
                if (value == 0) {
                    element.val("0.000");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 3).forDisp);
                return true;
            },
            //総酸（％）分析値
            ritu_sousan_bunseki: function (value, element) {
                element.val(page.detail.stringNumber(value));
                return true;
            },
            //食塩（％）分析値
            ritu_shokuen_bunseki: function (value, element) {
                element.val(page.detail.stringNumber(value));
                return true;
            },
            //pH
            ph: function (value, element) {
                element.val(page.detail.stringNumber(value, true));
                return true;
            },
            //充填前（クリーム）粘度
            nendo_cream: function (value, element) {
                element.val(page.detail.stringNumber(value, true));
                return true;
            },
            //充填前（クリーム）粘度－測定時品温（℃）
            ondo_cream: function (value, element) {
                element.val(page.detail.stringNumber(value));
                return true;
            },
            //粘度（製品）
            nendo: function (value, element) {
                element.val(page.detail.stringNumber(value, true));
                return true;
            },
            //粘度（製品）－測定時品温（℃）
            ondo: function (value, element) {
                element.val(page.detail.stringNumber(value, true));
                return true;
            },
            //2019/12/04 START : Request #15984
            //製品オイルマスタード含有量（ｐｐｍ）
            oilmustard: function (value, element) {
                if (value == 0) {
                    element.val("0");
                    return true;
                }
                element.val(page.detail.formatNumber(value, 0).forDisp);
                return true;
            },
            //2019/12/04 END : Request #15984

            //2019-10-07 : START :Request #15485 総酸と食塩の分析値の項目名
            //工程パターンが調味料2液充填のときの、以下の特性値の項目名を変更してください。
            //※調味料1液充填は、あっています
            //2020-06-18 : revert Request #15485

            //水相中　酸度（％）分析値
            ritu_sando_bunseki_suiso: function (value, element) {
                element.val(page.detail.stringNumber(value));
                return true;
            },
            //水相中　食塩（％）分析値
            ritu_shokuen_bunseki_suiso: function (value, element) {
                element.val(page.detail.stringNumber(value));
                return true;
            }

            //2019-10-07 : END :Request #15485 総酸と食塩の分析値の項目名
            //工程パターンが調味料2液充填のときの、以下の特性値の項目名を変更してください。
            //※調味料1液充填は、あっています
        }
    };

    /**
     * 明細の一覧の行が選択された時の処理を行います。
     */
    TokuseiChi_Tab.detail.select = function (e) {
        TokuseiChi_Tab.detail.element.find(".selected-row").removeClass("selected-row");
        TokuseiChi_Tab.detail.element.find(".data-index-tr-" + $(this).attr("data-index-tr")).addClass("selected-row");
        //$($(row.element[TokuseiChi_Tab.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab tr .selected")).removeClass("selected").addClass("unselected");
        //$(row.element[TokuseiChi_Tab.detail.fixedColumnIndex].querySelectorAll(".select-tab tr")).removeClass("unselected").addClass("selected");

        ////選択行全体に背景色を付ける場合は以下のコメントアウトを解除します。
        //if (!App.isUndefOrNull(TokuseiChi_Tab.detail.selectedRow)) {
        //    TokuseiChi_Tab.detail.selectedRow.element.find(".selected-row tr").removeClass("selected-row");
        //}
        //row.element.find("tr").addClass("selected-row");
        //TokuseiChi_Tab.detail.selectedRow = row;
    };


    /**
     * get changeset this tab。
     */
    TokuseiChi_Tab.getChangeSetTab = function () {
        //TokuseiChi_Tab.validateAll();
        //return;

        var element = TokuseiChi_Tab.detail.element,
            element_head_sample = TokuseiChi_Tab.detail.element_head_sample,
            flg_auto = element.findP("flg_auto")[0].checked,
            elementHd = element.find(".Header"),
            dataHeader = elementHd.form().data(),
            dataShisaHin = TokuseiChi_Tab.getDataHeader(),
            newData = {};

        //update checked
        $.each(dataHeader, function (pro, val) {
            if (pro.indexOf("flg_") == 0) {
                dataHeader[pro] = elementHd.findP(pro)[0].checked || elementHd.findP(pro)[1].checked;


                //update group checked
                switch (pro) {
                    case "flg_sousan"://総酸（％）,食塩（％）

                        dataHeader["flg_shokuen"] = dataHeader[pro];
                        break

                    case "flg_sousan_bunseki": //総酸（％）分析値, 食塩（％）分析値

                        dataHeader["flg_shokuen_bunseki"] = dataHeader[pro];
                        break;

                    case "flg_nendo_cream"://充填前（クリーム）粘度, 充填前（クリーム）粘度－測定時品温（℃）, 充填前（クリーム）粘度－ローターＮｏ．,充填前（クリーム）粘度－スピード（ｒｐｍ）

                        dataHeader["flg_ondo_cream"] = dataHeader[pro];
                        dataHeader["flg_no_rotor_cream"] = dataHeader[pro];
                        dataHeader["flg_speed_cream"] = dataHeader[pro];
                        break;

                    case "flg_nendo"://粘度（製品）, 粘度（製品）－測定時品温（℃）, 粘度（製品）－ローターＮｏ．, 粘度（製品）－スピード（ｒｐｍ）

                        dataHeader["flg_ondo"] = dataHeader[pro];
                        dataHeader["flg_no_rotor"] = dataHeader[pro];
                        dataHeader["flg_speed"] = dataHeader[pro];
                        break;

                    case "flg_sando_suiso"://水相中　酸度（％）, 水相中　食塩（％）, 水相中　酢酸酸度（％）, 水相中　ＭＳＧ含有率（％）

                        dataHeader["flg_shokuen_suiso"] = dataHeader[pro];
                        dataHeader["flg_sakusan_suiso"] = dataHeader[pro];
                        dataHeader["flg_msg_suiso"] = dataHeader[pro];
                        break;

                    case "flg_jikkoHikairiSakusanSando"://水相中　非解離酢酸酸度（％）, 配合強度

                        dataHeader["flg_haigo_kyodo"] = dataHeader[pro];
                        break;

                    case "flg_sando_bunseki_suiso"://水相中　酸度（％）分析値, 水相中　食塩（％）分析値

                        dataHeader["flg_shokuen_bunseki_suiso"] = dataHeader[pro];
                        break;

                    default:
                        break

                }
            }
        })

        if (page.values.isChange && page.values.modeDisp != App.settings.app.m_shisaku_data.copy) {

            var shisaku = element_head_sample.find(".nm_sample"),
                th, keySample;

            $.each(shisaku, function (index, item) {
                th = element.find(shisaku[index]).closest("th");
                if (th.hasClass("th-tmpl")) {
                    return true;
                }
                keySample = th.attr("data-key-sample");
                if (!TokuseiChi_Tab.detail.data[keySample]) {
                    TokuseiChi_Tab.detail.data[keySample] = {};
                };

                //総酸（％）//1m; //1g_blank; //2ml_blank; //2g;
                //ritu_sousan
                //食塩（％）//1m; //1g_blank; //2ml_blank; //2g;
                //ritu_shokuen
                //総酸（％）分析値 //1m; //1g_blank; //その他
                //ritu_sousan_bunseki
                //食塩（％）分析値 //1m; //1g_blank; //その他
                //ritu_shokuen_bunseki

                //水相中　酸度（％）分析値 //2ml_blank; //2g;
                //ritu_sando_bunseki_suiso
                //水相中　食塩（％）分析値 //2ml_blank; //2g;
                //ritu_shokuen_bunseki_suiso
                //Brix（％） //その他
                //Brix
                //pH //1m; //1g_blank; //2ml_blank; //2g; //その他
                //ph
                //充填前（クリーム）粘度 //1m; //1g_blank; //2ml_blank; //2g; //その他
                //nendo_cream
                //充填前（クリーム）粘度－測定時品温（℃） //1m; //1g_blank; //2ml_blank; //2g; //その他
                //ondo_cream
                //充填前（クリーム）粘度－ローターＮｏ． //1m; //1g_blank; //2ml_blank; //2g; //その他
                //no_rotor_cream
                //充填前（クリーム）粘度－スピード（ｒｐｍ） //1m; //1g_blank; //2ml_blank; //2g; //その他
                //speed_cream
                //Ａｗ //その他
                //AW
                //粘度（製品） //1m; //1g_blank; //2ml_blank; //2g;
                //nendo
                //粘度（製品）－測定時品温（℃） //1m; //1g_blank; //2ml_blank; //2g;
                //ondo
                //粘度（製品）－ローターＮｏ． //1m; //1g_blank; //2ml_blank; //2g;
                //no_rotor
                //粘度（製品）－スピード（ｒｐｍ） //1m; //1g_blank; //2ml_blank; //2g;
                //speed
                //製品比重 //1m; //2ml_blank;
                //hiju
                //水相比重 //2ml_blank; //2g;
                //hiju_sui
                //水相中　酸度（％） //1m; //1g_blank; //2ml_blank; //2g;
                //sando_suiso
                //水相中　食塩（％） //1m; //1g_blank; //2ml_blank; //2g;
                //shokuen_suiso
                //水相中　酢酸酸度（％） //1m; //1g_blank; //2ml_blank; //2g;
                //sakusan_suiso
                //水相中　ＭＳＧ含有率（％） //1m; //1g_blank; //2ml_blank; //2g;
                //msg_suiso
                //水相中　実効酢酸濃度（％） //1m; //1g_blank; //2ml_blank; //2g;
                //jikkoSakusanNodo
                //微生物ランク //1m; //1g_blank; //2ml_blank; //2g;
                //rank_biseibutsu
                //水相中　非解離酢酸酸度（％） //1m; //1g_blank; //2ml_blank; //2g;
                //jikkoHikairiSakusanSando
                //配合強度 //1m; //1g_blank; //2ml_blank; //2g;
                //haigo_kyodo
                //製品オイルマスタード含有量（ｐｐｍ） //1m; //1g_blank; //2ml_blank; //2g;
                //oilmustard
                switch (dataShisaHin.pt_kotei) {
                    case App.settings.app.pt_kotei.chomieki_1:

                        //2019-10-07 : START :Request #15485 総酸と食塩の分析値の項目名
                        //工程パターンが調味料2液充填のときの、以下の特性値の項目名を変更してください。
                        //※調味料1液充填は、あっています
                        //2020-06-18 : revert Request #15485

                        //水相中　酸度（％）分析値
                        TokuseiChi_Tab.detail.data[keySample]["flg_sando_bunseki_suiso"] = 0;
                        TokuseiChi_Tab.detail.data[keySample]["ritu_sando_bunseki_suiso"] = null;
                        //水相中　食塩（％）分析値
                        TokuseiChi_Tab.detail.data[keySample]["flg_shokuen_bunseki_suiso"] = 0;
                        TokuseiChi_Tab.detail.data[keySample]["ritu_shokuen_bunseki_suiso"] = null;

                        //2019-10-07 : END :Request #15485 総酸と食塩の分析値の項目名
                        //工程パターンが調味料2液充填のときの、以下の特性値の項目名を変更してください。
                        //※調味料1液充填は、あっています

                        //Brix（％）
                        TokuseiChi_Tab.detail.data[keySample]["flg_Brix"] = 0;
                        TokuseiChi_Tab.detail.data[keySample]["Brix"] = null;

                        //Ａｗ
                        TokuseiChi_Tab.detail.data[keySample]["flg_AW"] = 0;
                        TokuseiChi_Tab.detail.data[keySample]["AW"] = null;
                        //水相比重
                        TokuseiChi_Tab.detail.data[keySample]["flg_hiju_sui"] = 0;
                        TokuseiChi_Tab.detail.data[keySample]["hiju_sui"] = null;
                        //製品比重
                        if (dataShisaHin.cd_tani != App.settings.app.cd_tani.ml) {
                            TokuseiChi_Tab.detail.data[keySample]["flg_hiju"] = 0;
                        }

                        if (dataShisaHin.cd_tani == App.settings.app.cd_tani.gram) {
                            TokuseiChi_Tab.detail.data[keySample]["hiju"] = "1.000";
                        }
                        break;

                    case App.settings.app.pt_kotei.chomieki_2:
                        //2019-10-07 : START :Request #15485 総酸と食塩の分析値の項目名
                        //工程パターンが調味料2液充填のときの、以下の特性値の項目名を変更してください。
                        //※調味料1液充填は、あっています
                        //2020-06-18 : revert Request #15485

                        ////総酸（％）分析値 
                        TokuseiChi_Tab.detail.data[keySample]["flg_sousan_bunseki"] = 0;
                        TokuseiChi_Tab.detail.data[keySample]["ritu_sousan_bunseki"] = null;
                        ////食塩（％）分析値 
                        TokuseiChi_Tab.detail.data[keySample]["flg_shokuen_bunseki"] = 0;
                        TokuseiChi_Tab.detail.data[keySample]["ritu_shokuen_bunseki"] = null;

                        //2019-10-07 : END :Request #15485 総酸と食塩の分析値の項目名
                        //工程パターンが調味料2液充填のときの、以下の特性値の項目名を変更してください。
                        //※調味料1液充填は、あっています

                        //Brix（％）                    
                        TokuseiChi_Tab.detail.data[keySample]["flg_Brix"] = 0;
                        TokuseiChi_Tab.detail.data[keySample]["Brix"] = null;
                        //Ａｗ                    
                        TokuseiChi_Tab.detail.data[keySample]["flg_AW"] = 0;
                        TokuseiChi_Tab.detail.data[keySample]["AW"] = null;
                        //製品比重
                        if (dataShisaHin.cd_tani == App.settings.app.cd_tani.gram) {
                            TokuseiChi_Tab.detail.data[keySample]["flg_hiju"] = 0;
                            TokuseiChi_Tab.detail.data[keySample]["hiju"] = "1.000";
                            //水相比重
                            TokuseiChi_Tab.detail.data[keySample]["flg_hiju_sui"] = 0;
                            TokuseiChi_Tab.detail.data[keySample]["hiju_sui"] = null;
                        }
                        break;

                    case App.settings.app.pt_kotei.sonohokaeki:
                    case null:

                        //総酸（％）
                        TokuseiChi_Tab.detail.data[keySample]["flg_sousan"] = 0;
                        //2019-09-11 : START : Bug #15327 特性値　食塩(%)が計算されない
                        //TokuseiChi_Tab.detail.data[keySample]["ritu_sousan"] = null;
                        //2019-09-11 : END : Bug #15327 特性値　食塩(%)が計算されない
                        //食塩（％）
                        TokuseiChi_Tab.detail.data[keySample]["flg_shokuen"] = 0;
                        //2019-09-11 : START : Bug #15327 特性値　食塩(%)が計算されない
                        //TokuseiChi_Tab.detail.data[keySample]["ritu_shokuen"] = null;
                        //2019-09-11 : END : Bug #15327 特性値　食塩(%)が計算されない

                        //2019-10-07 : START :Request #15485 総酸と食塩の分析値の項目名
                        //工程パターンが調味料2液充填のときの、以下の特性値の項目名を変更してください。
                        //※調味料1液充填は、あっています
                        //2020-06-18 : revert Request #15485

                        //水相中　酸度（％）分析値 
                        TokuseiChi_Tab.detail.data[keySample]["flg_sando_bunseki_suiso"] = 0;
                        TokuseiChi_Tab.detail.data[keySample]["ritu_sando_bunseki_suiso"] = null;
                        //水相中　食塩（％）分析値 
                        TokuseiChi_Tab.detail.data[keySample]["flg_shokuen_bunseki_suiso"] = 0;
                        TokuseiChi_Tab.detail.data[keySample]["ritu_shokuen_bunseki_suiso"] = null;

                        //2019-10-07 : END :Request #15485 総酸と食塩の分析値の項目名
                        //工程パターンが調味料2液充填のときの、以下の特性値の項目名を変更してください。
                        //※調味料1液充填は、あっています

                        //粘度（製品）
                        TokuseiChi_Tab.detail.data[keySample]["flg_nendo"] = 0;
                        TokuseiChi_Tab.detail.data[keySample]["nendo"] = null;
                        //粘度（製品）－測定時品温（℃） 
                        TokuseiChi_Tab.detail.data[keySample]["flg_ondo"] = 0;
                        TokuseiChi_Tab.detail.data[keySample]["ondo"] = null;
                        //粘度（製品）－ローターＮｏ． 
                        TokuseiChi_Tab.detail.data[keySample]["flg_no_rotor"] = 0;
                        TokuseiChi_Tab.detail.data[keySample]["no_rotor"] = null;
                        //粘度（製品）－スピード（ｒｐｍ）
                        TokuseiChi_Tab.detail.data[keySample]["flg_speed"] = 0;
                        TokuseiChi_Tab.detail.data[keySample]["speed"] = null;
                        //製品比重
                        TokuseiChi_Tab.detail.data[keySample]["flg_hiju"] = 0;
                        TokuseiChi_Tab.detail.data[keySample]["hiju"] = null;
                        if (dataShisaHin.cd_tani == App.settings.app.cd_tani.gram) {
                            TokuseiChi_Tab.detail.data[keySample]["hiju"] = "1.000";
                        }
                        //水相比重
                        TokuseiChi_Tab.detail.data[keySample]["flg_hiju_sui"] = 0;
                        TokuseiChi_Tab.detail.data[keySample]["hiju_sui"] = null;
                        //水相中　酸度（％）
                        TokuseiChi_Tab.detail.data[keySample]["flg_sando_suiso"] = 0;
                        //2019-09-11 : START : Bug #15327 特性値　食塩(%)が計算されない
                        //TokuseiChi_Tab.detail.data[keySample]["sando_suiso"] = null;
                        //2019-09-11 : END : Bug #15327 特性値　食塩(%)が計算されない
                        //水相中　食塩（％）
                        TokuseiChi_Tab.detail.data[keySample]["flg_shokuen_suiso"] = 0;
                        //2019-09-11 : START : Bug #15327 特性値　食塩(%)が計算されない
                        //TokuseiChi_Tab.detail.data[keySample]["shokuen_suiso"] = null;
                        //2019-09-11 : END : Bug #15327 特性値　食塩(%)が計算されない
                        //水相中　酢酸酸度（％）
                        TokuseiChi_Tab.detail.data[keySample]["flg_sakusan_suiso"] = 0;
                        //2019-09-11 : START : Bug #15327 特性値　食塩(%)が計算されない
                        //TokuseiChi_Tab.detail.data[keySample]["sakusan_suiso"] = null;
                        //2019-09-11 : END : Bug #15327 特性値　食塩(%)が計算されない
                        //水相中　ＭＳＧ含有率（％）
                        TokuseiChi_Tab.detail.data[keySample]["flg_msg_suiso"] = 0;
                        TokuseiChi_Tab.detail.data[keySample]["msg_suiso"] = null;
                        //水相中　実効酢酸濃度（％）
                        TokuseiChi_Tab.detail.data[keySample]["flg_jikkoSakusanNodo"] = 0;
                        TokuseiChi_Tab.detail.data[keySample]["jikkoSakusanNodo"] = null;
                        //微生物ランク
                        TokuseiChi_Tab.detail.data[keySample]["flg_rank_biseibutsu"] = 0;
                        TokuseiChi_Tab.detail.data[keySample]["rank_biseibutsu"] = null;
                        //水相中　非解離酢酸酸度（％）
                        TokuseiChi_Tab.detail.data[keySample]["flg_jikkoHikairiSakusanSando"] = 0;
                        TokuseiChi_Tab.detail.data[keySample]["jikkoHikairiSakusanSando"] = null;
                        //配合強度

                        TokuseiChi_Tab.detail.data[keySample]["haigo_kyodo"] = null;
                        TokuseiChi_Tab.detail.data[keySample]["flg_haigo_kyodo"] = 0;
                        //製品オイルマスタード含有量（ｐｐｍ）
                        TokuseiChi_Tab.detail.data[keySample]["flg_oilmustard"] = 0;
                        TokuseiChi_Tab.detail.data[keySample]["oilmustard"] = null;
                        break;

                    default:
                        break;
                }
            })
        }

        $.each(TokuseiChi_Tab.detail.dataNew, function (index, item) {
            //create new id
            if (!newData[index]) {
                newData[index] = {};
            };

            newData[index] = $.extend(true, {}, newData[index], dataHeader);
            //分析値を毎回自動計算
            newData[index]["flg_auto"] = flg_auto;

            if (dataShisaHin.pt_kotei == App.settings.app.pt_kotei.chomieki_2) {
                //総酸（％）分析値 
                newData[index]["flg_sousan_bunseki"] = 0;
                newData[index]["ritu_sousan_bunseki"] = null;
                //食塩（％）分析値 
                newData[index]["flg_shokuen_bunseki"] = 0;
                newData[index]["ritu_shokuen_bunseki"] = null;
            }
            if (dataShisaHin.pt_kotei == App.settings.app.pt_kotei.chomieki_1) {
                //水相中　酸度（％）分析値
                newData[index]["flg_sando_bunseki_suiso"] = 0;
                newData[index]["ritu_sando_bunseki_suiso"] = null;
                //水相中　食塩（％）分析値
                newData[index]["flg_shokuen_bunseki_suiso"] = 0;
                newData[index]["ritu_shokuen_bunseki_suiso"] = null;
            }
        })

        return $.extend(true, {}, TokuseiChi_Tab.detail.data, newData);
    };

    /**
     *分析値を毎回自動計算
     *update add sample when change flg_auto 
     */
    TokuseiChi_Tab.detail.changeFlgAuto = function (checked) {
        var element = TokuseiChi_Tab.detail.element_head_sample,
            sample = element.find(".col-sample");

        if (!sample.length) {
            return;
        }
        App.ui.loading.show();
        //フラグ変更画面
        page.values.isChange = true;

        var index = 0, itemSample;
        while (sample[index]) {
            itemSample = element.find(sample[index]);
            index += 1;
            if (itemSample.hasClass("th-tmpl")) {
                continue;
            }

            var id = itemSample.attr("data-key-sample");

            //create new id
            if (!TokuseiChi_Tab.detail.data[id]) {
                TokuseiChi_Tab.detail.data[id] = {};
            };

            //分析値を毎回自動計算
            TokuseiChi_Tab.detail.data[id]["flg_auto"] = checked;
        }

        if (checked) {
            //再計算処理」を実行
            if (App.isFunc(TokuseiChi_Tab.recalculation)) {
                TokuseiChi_Tab.recalculation("tokuseichi_tab", "毎回自動計算");
            } else {
                App.ui.loading.close();
            }
        } else {
            App.ui.loading.close();
        }
    };

    /**
    * 分析値を毎回自動計算チェックボックス以外、
    * 残る全てチェックボックスがチェック入れ／解除する
    */
    TokuseiChi_Tab.detail.updateAllcheckboxHeader = function (checked) {

        //フラグ変更画面
        page.values.isChange = true;

        var element = TokuseiChi_Tab.detail.element.find(".new .Header");
        element.find(":checkbox:visible").prop("checked", checked).change();
    }

    /**
    * update from other tab change
    */
    TokuseiChi_Tab.detail.updateFromOtherTab = function (fiel, val, sample) {
        switch (fiel) {
            case "flg_shisanIrai":
                TokuseiChi_Tab.detail.shisanIrai(val, sample);
                break;
            default:
                break;
        }
    }

    /**
    * 配合タブで原価試算依頼コントロールクラス
    */
    TokuseiChi_Tab.detail.shisanIrai = function (val, sample) {
        var element = TokuseiChi_Tab.detail.element.find("." + sample);

        //チェック時にクラスを追加
        if (val) {
            element.find(".hiju-hiju_sui").addClass("iraiChecked");
        } else {
            //チェックを外したときにクラスを削除する
            element.find(".iraiChecked").removeClass("iraiChecked");

            var validate = function (targets) {
                return TokuseiChi_Tab.detail.validator.validate({
                    targets: element.find(".hiju-hiju_sui"),
                    state: {
                        tbody: element,
                        targets: element.find(".hiju-hiju_sui"),
                        isGridValidation: true
                    }
                });
            };

            validate(element.find(".hiju-hiju_sui"));
        }
    }

    /**
     * 明細の分析値を毎回自動計算と一括チェック／解除の変更イベントの処理を行います。
     */
    TokuseiChi_Tab.detail.changeAllHead = function (e) {
        var target = $(e.target),
            property = target.attr("data-prop");

        //分析値を毎回自動計算
        if (property == "flg_auto") {
            TokuseiChi_Tab.detail.changeFlgAuto(target[0].checked);
            return;
        }

        //一括チェック／解除
        if (property == "batch_check") {
            TokuseiChi_Tab.detail.updateAllcheckboxHeader(target[0].checked);
        }
    }

    /**
     * 明細の一覧の入力項目の変更イベントの処理を行います。
     */
    TokuseiChi_Tab.detail.change = function (e, row) {
        var target = $(e.target),
            id = target.closest("td").attr("data-key-sample"),
            property = target.attr("data-prop");
        
        TokuseiChi_Tab.wait(target).then(function () {//2020-08-06 :14007 : Bug #18857 内容量0.1でエラーとなる

            var validate = function (targets) {
                return TokuseiChi_Tab.detail.validator.validate({
                    targets: targets,
                    state: {
                        tbody: row.element,
                        targets: targets,
                        isGridValidation: true
                    }
                });
            };

            //分析値を毎回自動計算
            if (property == "flg_auto") {
                TokuseiChi_Tab.detail.changeFlgAuto(target[0].checked);
                return;
            }

            //一括チェック／解除
            if (property == "batch_check") {
                TokuseiChi_Tab.detail.updateAllcheckboxHeader(target[0].checked);
                return;
            }

            if (target.hasClass("check-head") || target.hasClass("free_title")) {
                TokuseiChi_Tab.detail.changeGroupHead(property, target, row);
                return;
            };

            //フラグ変更画面
            page.values.isChange = true;
            validate(target).then(function () {
                //create new id
                if (!TokuseiChi_Tab.detail.data[id]) {
                    TokuseiChi_Tab.detail.data[id] = {};
                };

                if (property.indexOf("free_title") >= 0 || property.indexOf("free_value") >= 0 || property == "memo_sakusei" || property == "hyoka") {
                    TokuseiChi_Tab.detail.data[id][property] = valtarget(target) + "";
                } else {
                    //if (["", "no_rotor_cream", "speed_cream", "no_rotor", "speed", "rank_biseibutsu"].indexOf(property) > 0) {
                    var val = valtarget(target).replace(/,/g, "");
                    if (property == "speed_cream" || property == "speed") {
                        val = (val + "").replace(".", "");

                        while (val != 0 && val.indexOf("0") == 0) {
                            val = val.substr(1, val.length);
                        }

                        val = (val != "" && val == 0) ? 0 : val;

                        valtarget(target);
                    }
                    TokuseiChi_Tab.detail.data[id][property] = val;
                    //} else {

                    //    //update property
                    //    TokuseiChi_Tab.detail.data[id][property] = page.detail.checkUndefi(target.val(), { replace: true, valReplace: null });
                    //}
                }

                //Brix（％）:99.9,Ａｗ:9.999
                if (property == "Brix" || property == "AW") {
                    var val = page.detail.formatNumber(valtarget(target), (property == "Brix") ? 1 : 3);
                    target.val(val.forDisp);

                    //update property
                    TokuseiChi_Tab.detail.data[id][property] = val.forData;
                }

                //製品比重
                if (property == "hiju") {
                    var hiju = page.detail.formatNumber(valtarget(target), 3);
                    target.val(hiju.forDisp);

                    //update property
                    TokuseiChi_Tab.detail.data[id][property] = hiju.forDisp.replace(/,/g, "");

                    if (TokuseiChi_Tab.values.pt_kotei == App.settings.app.pt_kotei.chomieki_1) {
                        if (App.isFunc(TokuseiChi_Tab.detail.updateOtherTab)) {
                            TokuseiChi_Tab.detail.updateOtherTab("genkashisan_tab", property, hiju.forData, id);
                        }
                        if (App.isFunc(TokuseiChi_Tab.recalculation)) {
                            //充填量水相
                            TokuseiChi_Tab.recalculation("genkashisan_tab", "EventTokuseichi");
                        }
                    }
                }

                //製品比重
                if (property == "ph") {
                    TokuseiChi_Tab.detail.values.isPh = true;
                    TokuseiChi_Tab.detail.getDataTab("haigohyo_tab").then(function (data) {
                        data["shisaku_hin"] = TokuseiChi_Tab.getDataHeader();
                        TokuseiChi_Tab.detail.recalculation("ph", data);
                    })
                }

                //水相比重
                if (property == "hiju_sui") {

                    //Request #15081 START : 【101_試作データ　②特性値タブ】水相比重について
                    //整数1桁、小数8桁でお願いいたします。
                    //また1.5のような値が入力された場合は、1.50000000ではなく1.5と表示するようにしていただきたいです。
                    hiju_sui = Number(valtarget(target))
                    if (valtarget(target) == "") {
                        hiju_sui = null;
                    } else {
                        target.val(hiju_sui);
                    }
                    //Request #15081 END : 【101_試作データ　②特性値タブ】水相比重について
                    //整数1桁、小数8桁でお願いいたします。
                    //また1.5のような値が入力された場合は、1.50000000ではなく1.5と表示するようにしていただきたいです。

                    //update property
                    TokuseiChi_Tab.detail.data[id][property] = hiju_sui;

                    if (TokuseiChi_Tab.values.pt_kotei == App.settings.app.pt_kotei.chomieki_2) {
                        if (App.isFunc(TokuseiChi_Tab.detail.updateOtherTab)) {
                            TokuseiChi_Tab.detail.updateOtherTab("genkashisan_tab", property, hiju_sui, id);
                        }

                        if (App.isFunc(TokuseiChi_Tab.recalculation)) {
                            TokuseiChi_Tab.recalculation("tokuseichi_tab", "hiju_sui");
                        }
                    }
                }
            });
        })
    };

    /**
    * control change combobox head。
    */
    TokuseiChi_Tab.detail.changeGroupHead = function (property, target, row) {
        var element = TokuseiChi_Tab.detail.element,
            sample = TokuseiChi_Tab.detail.element.find(".flow-container .dt-head .datatable .col-sample").not(".th-tmpl"),
            isHeadTitle = target.hasClass("free_title");

        if (sample.length == 0) {
            return;
        }

        var val = target[0].checked;

        //data when change title free
        if (isHeadTitle) {
            val = target.val();
        }

        //フラグ変更画面
        page.values.isChange = true;
        TokuseiChi_Tab.detail.executeValidation(target, row).then(function () {
            $.each(sample, function (index, item) {
                id = element.find(this).attr("data-key-sample");

                if (!TokuseiChi_Tab.detail.data[id]) {
                    TokuseiChi_Tab.detail.data[id] = {};
                };

                TokuseiChi_Tab.detail.data[id][property] = val;

                //break when change title free
                if (isHeadTitle) {
                    return true;
                }

                switch (property) {
                    case "flg_sousan"://総酸（％）,食塩（％）

                        TokuseiChi_Tab.detail.data[id]["flg_shokuen"] = val;
                        break

                    case "flg_sousan_bunseki": //総酸（％）分析値, 食塩（％）分析値

                        TokuseiChi_Tab.detail.data[id]["flg_shokuen_bunseki"] = val;
                        break;

                    case "flg_nendo_cream"://充填前（クリーム）粘度, 充填前（クリーム）粘度－測定時品温（℃）, 充填前（クリーム）粘度－ローターＮｏ．,充填前（クリーム）粘度－スピード（ｒｐｍ）

                        TokuseiChi_Tab.detail.data[id]["flg_ondo_cream"] = val;
                        TokuseiChi_Tab.detail.data[id]["flg_no_rotor_cream"] = val;
                        TokuseiChi_Tab.detail.data[id]["flg_speed_cream"] = val;
                        break;

                    case "flg_nendo"://粘度（製品）, 粘度（製品）－測定時品温（℃）, 粘度（製品）－ローターＮｏ．, 粘度（製品）－スピード（ｒｐｍ）

                        TokuseiChi_Tab.detail.data[id]["flg_ondo"] = val;
                        TokuseiChi_Tab.detail.data[id]["flg_no_rotor"] = val;
                        TokuseiChi_Tab.detail.data[id]["flg_speed"] = val;
                        break;

                    case "flg_sando_suiso"://水相中　酸度（％）, 水相中　食塩（％）, 水相中　酢酸酸度（％）, 水相中　ＭＳＧ含有率（％）

                        TokuseiChi_Tab.detail.data[id]["flg_shokuen_suiso"] = val;
                        TokuseiChi_Tab.detail.data[id]["flg_sakusan_suiso"] = val;
                        TokuseiChi_Tab.detail.data[id]["flg_msg_suiso"] = val;
                        break;

                    case "flg_jikkoHikairiSakusanSando"://水相中　非解離酢酸酸度（％）, 配合強度

                        TokuseiChi_Tab.detail.data[id]["flg_haigo_kyodo"] = val;
                        break;

                    case "flg_sando_bunseki_suiso"://水相中　酸度（％）分析値, 水相中　食塩（％）分析値

                        TokuseiChi_Tab.detail.data[id]["flg_shokuen_bunseki_suiso"] = val;
                        break;

                    default:
                        break

                }
            })
        })
    };

    /**
    * auto change value of 配合強度 follow 食塩 and 非解離酢酸酸度。
    * 19075 - 2022/06/06 - ShohinKaihatsuSuppot Modify 2022FY
    */
    TokuseiChi_Tab.detail.changeHaigoKyodo = function (id) {
        var element = TokuseiChi_Tab.detail.element.find(".flow-container .dt-body .datatable .new .col-sample." + id).not(".th-tmpl"),
            haigo_kyodo = element.findP("haigo_kyodo");

        var oldVal = haigo_kyodo.val();

        if (haigo_kyodo.val() == App.settings.app.kbn_haigo_kyodo.sakkin_nashi || haigo_kyodo.val() == App.settings.app.kbn_haigo_kyodo.tekiyo_gai) {
            return;
        }

        var shokuenSuisoValue = element.findP("shokuen_suiso").val(),
            jikkoHikairiSakusanSandoValue = element.findP("jikkoHikairiSakusanSando").val();

        if (App.isUndefOrNullOrStrEmpty(shokuenSuisoValue) || App.isUndefOrNullOrStrEmpty(jikkoHikairiSakusanSandoValue)) {
            haigo_kyodo.val("");
        } else {

            if (jikkoHikairiSakusanSandoValue < App.settings.app.hi_jikkoHikairiSakusanSando.lower) {
                if (shokuenSuisoValue < App.settings.app.hi_shokuen_suiso.lower) {
                    haigo_kyodo.val(App.settings.app.kbn_haigo_kyodo.aka);
                } else if (shokuenSuisoValue < App.settings.app.hi_shokuen_suiso.high) {
                    haigo_kyodo.val(App.settings.app.kbn_haigo_kyodo.aka);
                } else {
                    haigo_kyodo.val(App.settings.app.kbn_haigo_kyodo.ki);
                }
            } else if (jikkoHikairiSakusanSandoValue < App.settings.app.hi_jikkoHikairiSakusanSando.high) {
                if (shokuenSuisoValue < App.settings.app.hi_shokuen_suiso.lower) {
                    haigo_kyodo.val(App.settings.app.kbn_haigo_kyodo.aka);
                } else if (shokuenSuisoValue < App.settings.app.hi_shokuen_suiso.high) {
                    haigo_kyodo.val(App.settings.app.kbn_haigo_kyodo.ki);
                } else {
                    haigo_kyodo.val(App.settings.app.kbn_haigo_kyodo.ao);
                }
            } else {
                if (shokuenSuisoValue < App.settings.app.hi_shokuen_suiso.lower) {
                    haigo_kyodo.val(App.settings.app.kbn_haigo_kyodo.ki);
                } else if (shokuenSuisoValue < App.settings.app.hi_shokuen_suiso.high) {
                    haigo_kyodo.val(App.settings.app.kbn_haigo_kyodo.ao);
                } else {
                    haigo_kyodo.val(App.settings.app.kbn_haigo_kyodo.ao);
                }
            }
        }

        TokuseiChi_Tab.detail.setUpdate(id, "haigo_kyodo", haigo_kyodo.val());

        var newVal = haigo_kyodo.val();

        TokuseiChi_Tab.detail.isChangeDataCBB(oldVal, newVal);
    }

    /**
    * auto change value of 微生物ランク follow 水相中酢酸酸度 and 水相中実効酢酸濃度。
    * 19075 - 2022/06/06 - ShohinKaihatsuSuppot Modify 2022FY
    */
    TokuseiChi_Tab.detail.changeRankBiseibutsu = function (id) {

        var element = TokuseiChi_Tab.detail.element.find(".flow-container .dt-body .datatable .new .col-sample." + id).not(".th-tmpl"),
            sakusanSuisoValue = element.findP("sakusan_suiso").val(),
            jikkoSakusanNodoValue = element.findP("jikkoSakusanNodo").val(),
            rank_biseibutsu = element.findP("rank_biseibutsu"),
            msg_suiso = element.findP("msg_suiso");

        var oldVal = rank_biseibutsu.val();

        if (App.isUndefOrNullOrStrEmpty(msg_suiso.val())) {
            rank_biseibutsu.val("");

        } else {

            if (msg_suiso.val() < App.settings.app.hi_MSG.ichi) {
                if (sakusanSuisoValue < App.settings.app.hi_sakusan_suiso_nodo.lower) {
                    rank_biseibutsu.val(App.settings.app.rankBiseibutsu.S)
                } else if (sakusanSuisoValue < App.settings.app.hi_sakusan_suiso_nodo.medium) {
                    rank_biseibutsu.val(App.settings.app.rankBiseibutsu.A)
                } else if (sakusanSuisoValue < App.settings.app.hi_sakusan_suiso_nodo.high) {
                    rank_biseibutsu.val(App.settings.app.rankBiseibutsu.B)
                } else {
                    rank_biseibutsu.val(App.settings.app.rankBiseibutsu.C)
                }
            } else {
                if (jikkoSakusanNodoValue < App.settings.app.hi_sakusan_suiso_nodo.lower) {
                    rank_biseibutsu.val(App.settings.app.rankBiseibutsu.S)
                } else if (jikkoSakusanNodoValue < App.settings.app.hi_sakusan_suiso_nodo.medium) {
                    rank_biseibutsu.val(App.settings.app.rankBiseibutsu.A)
                } else if (jikkoSakusanNodoValue < App.settings.app.hi_sakusan_suiso_nodo.high) {
                    rank_biseibutsu.val(App.settings.app.rankBiseibutsu.B)
                } else {
                    rank_biseibutsu.val(App.settings.app.rankBiseibutsu.C)
                }
            }
        }

        TokuseiChi_Tab.detail.setUpdate(id, "rank_biseibutsu", rank_biseibutsu.val());
        
        var newVal = rank_biseibutsu.val();

        TokuseiChi_Tab.detail.isChangeDataCBB(oldVal, newVal);
    }

    /**
    * compare value of 微生物ランク and 配合強度
    * 19075 - 2022/08/09 - ShohinKaihatsuSuppot Modify 2022FY
    */
    TokuseiChi_Tab.detail.isChangeDataCBB = function (oldVal, newVal) {
        if (!page.values.isChange) {
            if (oldVal != newVal && !App.isUndef(oldVal) && !App.isUndef(newVal)) {
                page.values.isChange = true;
            }
        }
    }

    /**
    * 明細のバリデーションを実行します。
    */
    TokuseiChi_Tab.detail.executeValidation = function (targets, row, options) {
        var defaultOptions = {
            targets: targets,
            state: {
                tbody: row,
                isGridValidation: true
            }
        },
            execOptions = $.extend(true, {}, defaultOptions, options);

        return TokuseiChi_Tab.detail.validator.validate(execOptions);
    };

    /**
     * 明細のバリデーションフィルターを設定します。（必須チェックを行わない）
     */
    TokuseiChi_Tab.detail.validationFilter = function (item, method, state, options) {
        return method !== "required";
    };

    /**
     * 明細の一覧全体のバリデーションを実行します。
     */
    TokuseiChi_Tab.detail.validateList = function (suppressMessage, isSave) {
        var validations = [],
            options = {
                state: {
                    suppressMessage: suppressMessage,
                    isSave: isSave
                }
            };

        TokuseiChi_Tab.detail.dataTable.dataTable("each", function (row, index) {
            //validations.push(TokuseiChi_Tab.detail.executeValidation(row.element.find(":input"), row.element, options));
            // セルに対してバリデーションを実行します。
            row.element.find(":input").each(function (index, elem) {
                if ($(elem).closest("td").hasClass("th-tmpl")) {
                    return true;
                }
                validations.push(TokuseiChi_Tab.detail.validator.validate({
                    targets: $(elem),
                    state: {
                        suppressMessage: suppressMessage,
                        tbody: row.element,
                        targets: $(elem),
                        isGridValidation: true,
                        isSave: isSave
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

<div class="tab-pane" id="TokuseiChi_Tab" style="margin-top: 3px">
    <div class="row">
        <div class="col-xs-2" style="padding-left: 5px;">
            <label class="check-group" style="padding-top: 0px">
                <input type="checkbox" data-prop="flg_auto" value="1" class="check-on-head" />
                分析値を毎回自動計算
            </label>
        </div>
        <div class="col-xs-2">
            <label class="check-group" style="padding-top: 0px">
                <input type="checkbox" data-prop="batch_check" class="check-on-head" />
                一括チェック／解除
            </label>
        </div>
    </div>
    <table class="datatable" style="display: block;">
        <!--TODO: 明細一覧のヘッダーを定義するHTMLをここに記述します。-->
        <thead>
            <tr>
                <th class="first-col-head" colspan="2" style="width: 265px">Sample</th>
                <th class="th-tmpl th-header col-sample" style="text-align: left">
                    <label data-prop="nm_sample" class="overflow-ellipsis nm_sample" style="height: 18px; float: left"></label>
                    <input style="display: none" /><%--input for template--%>
                </th>
            </tr>
        </thead>
        <tbody class="item-tmpl">
            <tr class="pt-kotei-display chomieki-1-2 data-index-tr-1" data-index-tr="1">
                <td style="width: 15px; text-align: center;" rowspan="2" class="Header enableColor">
                    <input type="checkbox" class="check-head prev_focus" data-prop="flg_sousan" value="1" tabindex="1" />
                </td>
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="総酸（％）" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="ritu_sousan" readonly="readonly" tabindex="1" class="text-selectAll" />
                </td>
            </tr>
            <tr class="pt-kotei-display chomieki-1-2 data-index-tr-38" data-index-tr="38">
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="食塩（％）" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="ritu_shokuen" readonly="readonly" tabindex="2" class="text-selectAll" />
                </td>
            </tr>
            <%--  </tbody>

        <tbody class=" pt-kotei-display chomieki-1-mL chomieki-1-g">--%>
            <tr class=" pt-kotei-display chomieki-1-mL chomieki-1-g sonohokaeki data-index-tr-2" data-index-tr="2">

                <td style="width: 15px; text-align: center;" rowspan="2" class="Header enableColor">
                    <input type="checkbox" class="check-head prev_focus" data-prop="flg_sousan_bunseki" value="1" tabindex="3" />
                </td>
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="総酸（％）分析値" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" data-prop="ritu_sousan_bunseki" class="limit-input-float-new text-selectAll" maxlength="15" tabindex="3" data-number-format-tofixed="null" />
                </td>
            </tr>
            <tr class=" pt-kotei-display chomieki-1-mL chomieki-1-g sonohokaeki data-index-tr-39" data-index-tr="39">
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="食塩（％）分析値" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" data-prop="ritu_shokuen_bunseki" class="limit-input-float-new text-selectAll" maxlength="15" tabindex="4" data-number-format-tofixed="null" />
                </td>
            </tr>
            <%-- </tbody>
                
        <tbody class=" pt-kotei-display chomieki-2-mL chomieki-2-g">--%>

            <%-- 2019-10-07 : START :Request #15485 総酸と食塩の分析値の項目名
            工程パターンが調味料2液充填のときの、以下の特性値の項目名を変更してください。
            ※調味料1液充填は、あっています--%>
            <%--2020-06-18 : revert Request #15485--%>

            <tr class=" pt-kotei-display chomieki-2-mL chomieki-2-g data-index-tr-3" data-index-tr="3">

                <td style="width: 15px; text-align: center;" rowspan="2" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_sando_bunseki_suiso" value="1" tabindex="5" />
                </td>
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="水相中　酸度（％）分析値" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" data-prop="ritu_sando_bunseki_suiso" class="limit-input-float-new text-selectAll" maxlength="15" tabindex="5" data-number-format-tofixed="null" />
                </td>
            </tr>
            <tr class=" pt-kotei-display chomieki-2-mL chomieki-2-g data-index-tr-40" data-index-tr="40">
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="水相中　食塩（％）分析値" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" data-prop="ritu_shokuen_bunseki_suiso" class="limit-input-float-new text-selectAll" maxlength="15" tabindex="6" data-number-format-tofixed="null" />
                </td>
            </tr>

            <%--   2019-10-07 : END :Request #15485 総酸と食塩の分析値の項目名
            工程パターンが調味料2液充填のときの、以下の特性値の項目名を変更してください。
            ※調味料1液充填は、あっています--%>
            <%-- </tbody>
                

        <tbody class=" pt-kotei-display sonohokaeki">--%>
            <tr class=" pt-kotei-display sonohokaeki data-index-tr-4" data-index-tr="4">

                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_Brix" value="1" tabindex="7" />
                </td>
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="Brix（％）" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" data-prop="Brix" class="limit-input-float-new text-selectAll" maxlength="4" tabindex="8" />
                </td>
            </tr>
            <%--        </tbody>

        <tbody class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g sonohokaeki">--%>
            <tr class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g sonohokaeki data-index-tr-5" data-index-tr="5">

                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_ph" value="1" tabindex="8" />
                </td>
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="pH" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" data-prop="ph" class="limit-input-float-new text-selectAll" maxlength="15" tabindex="8" data-number-format-tofixed="null" />
                </td>
            </tr>
            <%--</tbody>

        <tbody class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g sonohokaeki">--%>
            <tr class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g sonohokaeki data-index-tr-6" data-index-tr="6">
                <td style="width: 15px; text-align: center;" rowspan="4" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_nendo_cream" value="1" tabindex="9" />
                </td>
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="充填前（クリーム）粘度" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" data-prop="nendo_cream" maxlength="15" class="limit-input-float-new text-selectAll" tabindex="9" data-number-format-tofixed="null" />
                </td>
            </tr>
            <tr class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g sonohokaeki data-index-tr-41" data-index-tr="41">
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="充填前（クリーム）粘度－測定時品温（℃）" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" data-prop="ondo_cream" class="limit-input-float-new text-selectAll" maxlength="15" tabindex="10" data-number-format-tofixed="null" />
                </td>
            </tr>
            <tr class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g sonohokaeki data-index-tr-42" data-index-tr="42">
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="充填前（クリーム）粘度－ローターＮｏ．" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" data-prop="no_rotor_cream" tabindex="11" class="text-selectAll" />
                </td>
            </tr>
            <tr class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g sonohokaeki data-index-tr-43" data-index-tr="43">
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="充填前（クリーム）粘度－スピード（ｒｐｍ）" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" data-prop="speed_cream" class="limit-input-int text-selectAll" maxlength="2" tabindex="12" />
                </td>
            </tr>
            <%--        </tbody>

        <tbody class=" pt-kotei-display sonohokaeki">--%>
            <tr class=" pt-kotei-display sonohokaeki data-index-tr-7" data-index-tr="7">

                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_AW" value="1" tabindex="13" />
                </td>
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="Ａｗ" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" data-prop="AW" maxlength="5" class="limit-input-float-new text-selectAll" tabindex="13" />
                </td>
            </tr>
            <%-- </tbody>

        <tbody class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g">--%>
            <tr class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g data-index-tr-8" data-index-tr="8">
                <td style="width: 15px; text-align: center;" rowspan="4" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_nendo" value="1" tabindex="14" />
                </td>
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="粘度（製品）" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" data-prop="nendo" class="limit-input-float-new text-selectAll" maxlength="15" tabindex="14" data-number-format-tofixed="null" />
                </td>
            </tr>
            <tr class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g data-index-tr-44" data-index-tr="44">
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="粘度（製品）－測定時品温（℃）" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" data-prop="ondo" class="limit-input-float-new text-selectAll" maxlength="15" tabindex="15" data-number-format-tofixed="null" />
                </td>
            </tr>
            <tr class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g data-index-tr-45" data-index-tr="45">
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="粘度（製品）－ローターＮｏ．" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" data-prop="no_rotor" tabindex="16" class="text-selectAll" />
                </td>
            </tr>
            <tr class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g data-index-tr-46" data-index-tr="46">
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="粘度（製品）－スピード（ｒｐｍ）" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" data-prop="speed" class="limit-input-int text-selectAll" maxlength="2" tabindex="17" />
                </td>
            </tr>
            <%--        </tbody>

        <tbody class=" pt-kotei-display chomieki-1-mL chomieki-2-mL">--%>
            <tr class=" pt-kotei-display chomieki-1-mL chomieki-2-mL data-index-tr-9" data-index-tr="9">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_hiju" value="1" tabindex="18" />
                </td>
                <td style="width: 250px" class="border-right-head enableNoneColor">
                    <label style="margin-left: 1px; height: 18px; color: rgb(0, 0, 0)">製品比重<span class="required seihoshien">*</span></label>
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" data-prop="hiju" class="limit-input-float-new hiju-hiju_sui hiju-input text-selectAll" maxlength="5" tabindex="18" />
                </td>
            </tr>
            <%--        </tbody>

        <tbody class=" pt-kotei-display chomieki-2-mL chomieki-2-g">--%>
            <tr class=" pt-kotei-display chomieki-2-mL data-index-tr-10" data-index-tr="10">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_hiju_sui" value="1" tabindex="19" />
                </td>
                <td style="width: 250px" class="border-right-head enableNoneColor">
                    <label style="margin-left: 1px; height: 18px; color: rgb(0, 0, 0)">水相比重<span class="required seihoshien">*</span></label>
                </td>
                <td class="th-tmpl col-sample">
                    <%--Request #15081 START : 【101_試作データ　②特性値タブ】水相比重について
                整数1桁、小数8桁でお願いいたします。--%>
                    <input type="tel" data-prop="hiju_sui" class="limit-input-float-new hiju-hiju_sui hiju_sui-input text-selectAll" maxlength="10" tabindex="19" />
                    <%--Request #15081 END : 【101_試作データ　②特性値タブ】水相比重について
                整数1桁、小数8桁でお願いいたします。--%>
                </td>
            </tr>
            <%--        </tbody>

        <tbody class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g">--%>
            <tr class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g data-index-tr-11" data-index-tr="11">
                <td style="width: 15px; text-align: center;" rowspan="4" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_sando_suiso" value="1" tabindex="20" />
                </td>
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="水相中　酸度（％）" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="sando_suiso" readonly="readonly" tabindex="20" class="text-selectAll" />
                </td>
            </tr>
            <tr class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g data-index-tr-47" data-index-tr="47">
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="水相中　食塩（％）" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="shokuen_suiso" readonly="readonly" tabindex="21" class="text-selectAll" />
                </td>
            </tr>
            <tr class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g data-index-tr-48" data-index-tr="48">
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="水相中　酢酸酸度（％）" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="sakusan_suiso" readonly="readonly" tabindex="22" class="text-selectAll" />
                </td>
            </tr>
            <tr class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g data-index-tr-49" data-index-tr="49">
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="水相中　ＭＳＧ含有率（％）" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="msg_suiso" readonly="readonly" tabindex="23" class="text-selectAll" />
                </td>
            </tr>
            <%--        </tbody>

        <tbody class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g">--%>
            <tr class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g data-index-tr-12" data-index-tr="12">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_jikkoSakusanNodo" value="1" tabindex="24" />
                </td>
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="水相中　実効酢酸濃度（％）" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="jikkoSakusanNodo" readonly="readonly" tabindex="24" class="text-selectAll" />
                </td>
            </tr>
            <%--        </tbody>

        <tbody class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g">--%>
            <tr class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g data-index-tr-13" data-index-tr="13">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_rank_biseibutsu" value="1" tabindex="25" />
                </td>
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="微生物ランク" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <%-- 19075 - 2022/06/06 - ShohinKaihatsuSuppot Modify 2022FY: -st change textbox to combobox --%>
                    <%--<input type="text" data-prop="rank_biseibutsu" tabindex="25" class="text-selectAll" />--%>
                    <select data-prop="rank_biseibutsu" tabindex="25" class="text-selectAll biseibutsu" disabled></select>
                    <%-- -ed change textbox to combobox --%>
                </td>
            </tr>
            <%--        </tbody>

        <tbody class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g">--%>
            <tr class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g data-index-tr-14" data-index-tr="14">
                <td style="width: 15px; text-align: center;" rowspan="2" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_jikkoHikairiSakusanSando" value="1" tabindex="26" />
                </td>
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="水相中　非解離酢酸酸度（％）" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="jikkoHikairiSakusanSando" readonly="readonly" tabindex="26" class="text-selectAll" />
                </td>
            </tr>
            <tr class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g data-index-tr-50" data-index-tr="50">
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="配合強度" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <select data-prop="haigo_kyodo" tabindex="27"></select>
                </td>
            </tr>
            <%--        </tbody>

        <tbody class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g">--%>
            <tr class=" pt-kotei-display chomieki-1-mL chomieki-1-g chomieki-2-mL chomieki-2-g data-index-tr-15" data-index-tr="15">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_oilmustard" value="1" tabindex="28" />
                </td>
                <td style="width: 250px" class="border-right-head">
                    <input type="text" value="製品オイルマスタード含有量（ｐｐｍ）" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" data-prop="oilmustard" readonly="readonly" tabindex="29" class="text-selectAll" />
                </td>
            </tr>
            <%--        </tbody>

        <tbody class="">--%>
            <tr data-index-tr="16" class="data-index-tr-16">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_free1" value="1" tabindex="30" />
                </td>
                <td style="width: 250px" class="border-right-head Header">
                    <input type="text" data-prop="free_title1" class="free_title text-selectAll" tabindex="30" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="free_value1" tabindex="30" class="text-selectAll" />
                </td>
            </tr>
            <%--        </tbody>

        <tbody class="">--%>
            <tr data-index-tr="17" class="data-index-tr-17">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head " data-prop="flg_free2" value="1" tabindex="31" />
                </td>
                <td style="width: 250px" class="border-right-head Header">
                    <input type="text" data-prop="free_title2" class="free_title text-selectAll" tabindex="31" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="free_value2" tabindex="31" class="text-selectAll" />
                </td>
            </tr>
            <%--        </tbody>

        <tbody class="">--%>
            <tr data-index-tr="18" class="data-index-tr-18">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_free3" value="1" tabindex="32" />
                </td>
                <td style="width: 250px" class="border-right-head Header">
                    <input type="text" data-prop="free_title3" class="free_title text-selectAll" tabindex="32" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="free_value3" tabindex="32" class="text-selectAll" />
                </td>
            </tr>
            <%--        </tbody>

        <tbody class="">--%>
            <tr data-index-tr="19" class="data-index-tr-19">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_free4" value="1" tabindex="33" />
                </td>
                <td style="width: 250px" class="border-right-head Header">
                    <input type="text" data-prop="free_title4" class="free_title text-selectAll" tabindex="33" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="free_value4" tabindex="33" class="text-selectAll" />
                </td>
            </tr>
            <%--        </tbody>

        <tbody class="">--%>
            <tr data-index-tr="20" class="data-index-tr-20">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_free5" value="1" tabindex="34" />
                </td>
                <td style="width: 250px" class="border-right-head Header">
                    <input type="text" data-prop="free_title5" class="free_title text-selectAll" tabindex="34" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="free_value5" tabindex="34" class="text-selectAll" />
                </td>
            </tr>
            <%--        </tbody>

        <tbody class="">--%>
            <tr data-index-tr="21" class="data-index-tr-21">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_free6" value="1" tabindex="35" />
                </td>
                <td style="width: 250px" class="border-right-head Header">
                    <input type="text" data-prop="free_title6" class="free_title text-selectAll" tabindex="35" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="free_value6" tabindex="35" class="text-selectAll" />
                </td>
            </tr>
            <%--        </tbody>

        <tbody class="">--%>
            <tr data-index-tr="22" class="data-index-tr-22">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_free7" value="1" tabindex="36" />
                </td>
                <td style="width: 250px" class="border-right-head Header">
                    <input type="text" data-prop="free_title7" class="free_title text-selectAll" tabindex="36" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="free_value7" tabindex="36" class="text-selectAll" />
                </td>
            </tr>
            <%--       </tbody>

        <tbody class="">--%>
            <tr data-index-tr="23" class="data-index-tr-23">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_free8" value="1" tabindex="37" />
                </td>
                <td style="width: 250px" class="border-right-head Header">
                    <input type="text" data-prop="free_title8" class="free_title text-selectAll" tabindex="37" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="free_value8" tabindex="37" class="text-selectAll" />
                </td>
            </tr>
            <%--        </tbody>

        <tbody class="">--%>
            <tr data-index-tr="24" class="data-index-tr-24">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_free9" value="1" tabindex="38" />
                </td>
                <td style="width: 250px" class="border-right-head Header">
                    <input type="text" data-prop="free_title9" class="free_title text-selectAll" tabindex="38" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="free_value9" tabindex="38" class="text-selectAll" />
                </td>
            </tr>
            <%--        </tbody>

        <tbody class="">--%>
            <tr data-index-tr="25" class="data-index-tr-25">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_free10" value="1" tabindex="39" />
                </td>
                <td style="width: 250px" class="border-right-head Header">
                    <input type="text" data-prop="free_title10" class="free_title text-selectAll" tabindex="39" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="free_value10" tabindex="39" class="text-selectAll" />
                </td>
            </tr>
            <%--  </tbody>

        <tbody class="">--%>
            <tr data-index-tr="26" class="data-index-tr-26">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_free11" value="1" tabindex="40" />
                </td>
                <td style="width: 250px" class="border-right-head Header">
                    <input type="text" data-prop="free_title11" class="free_title text-selectAll" tabindex="40" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="free_value11" tabindex="40" class="text-selectAll" />
                </td>
            </tr>
            <%--        </tbody>

        <tbody class="">--%>
            <tr data-index-tr="27" class="data-index-tr-27">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_free12" value="1" tabindex="41" />
                </td>
                <td style="width: 250px" class="border-right-head Header">
                    <input type="text" data-prop="free_title12" class="free_title text-selectAll" tabindex="41" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="free_value12" tabindex="41" class="text-selectAll" />
                </td>
            </tr>
            <%--  </tbody>

        <tbody class="">--%>
            <tr data-index-tr="28" class="data-index-tr-28">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_free13" value="1" tabindex="42" />
                </td>
                <td style="width: 250px" class="border-right-head Header">
                    <input type="text" data-prop="free_title13" class="free_title text-selectAll" tabindex="42" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="free_value13" tabindex="42" class="text-selectAll" />
                </td>
            </tr>
            <%--        </tbody>

        <tbody class="">--%>
            <tr data-index-tr="29" class="data-index-tr-29">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_free14" value="1" tabindex="43" />
                </td>
                <td style="width: 250px" class="border-right-head Header">
                    <input type="text" data-prop="free_title14" class="free_title text-selectAll" tabindex="43" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="free_value14" tabindex="43" class="text-selectAll" />
                </td>
            </tr>
            <%--      </tbody>

        <tbody class="">--%>
            <tr data-index-tr="30" class="data-index-tr-30">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_free15" value="1" tabindex="44" />
                </td>
                <td style="width: 250px" class="border-right-head Header">
                    <input type="text" data-prop="free_title15" class="free_title text-selectAll" tabindex="44" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="free_value15" tabindex="44" class="text-selectAll" />
                </td>
            </tr>
            <%--     </tbody>

        <tbody class="">--%>
            <tr data-index-tr="31" class="data-index-tr-31">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_free16" value="1" tabindex="45" />
                </td>
                <td style="width: 250px" class="border-right-head Header">
                    <input type="text" data-prop="free_title16" class="free_title text-selectAll" tabindex="45" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="free_value16" tabindex="45" class="text-selectAll" />
                </td>
            </tr>
            <%--       </tbody>

        <tbody class="">--%>
            <tr data-index-tr="32" class="data-index-tr-32">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_free17" value="1" tabindex="46" />
                </td>
                <td style="width: 250px" class="border-right-head Header">
                    <input type="text" data-prop="free_title17" class="free_title text-selectAll" tabindex="46" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="free_value17" tabindex="46" class="text-selectAll" />
                </td>
            </tr>
            <%--        </tbody>

        <tbody class="">--%>
            <tr data-index-tr="33" class="data-index-tr-33">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_free18" value="1" tabindex="47" />
                </td>
                <td style="width: 250px" class="border-right-head Header">
                    <input type="text" data-prop="free_title18" class="free_title text-selectAll" tabindex="47" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="free_value18" tabindex="47" class="text-selectAll" />
                </td>
            </tr>
            <%--        </tbody>

        <tbody class="">--%>
            <tr data-index-tr="34" class="data-index-tr-34">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_free19" value="1" tabindex="48" />
                </td>
                <td style="width: 250px" class="border-right-head Header">
                    <input type="text" data-prop="free_title19" class="free_title text-selectAll" tabindex="48" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="free_value19" tabindex="48" class="text-selectAll" />
                </td>
            </tr>
            <%--    </tbody>

        <tbody class="">--%>
            <tr data-index-tr="35" class="data-index-tr-35">
                <td style="width: 15px; text-align: center;" class="Header enableColor">
                    <input type="checkbox" class="check-head" data-prop="flg_free20" value="1" tabindex="49" />
                </td>
                <td style="width: 250px" class="border-right-head Header">
                    <input type="text" data-prop="free_title20" class="free_title text-selectAll" tabindex="49" />
                </td>
                <td class="th-tmpl col-sample">
                    <input type="text" data-prop="free_value20" tabindex="49" class="text-selectAll" />
                </td>
            </tr>
            <%--        </tbody>

        <tbody class="">--%>
            <tr data-index-tr="36" class="data-index-tr-36">
                <td style="width: 15px; text-align: center; height: 88px!important" colspan="2" class="border-right-head">
                    <input type="text" value="作成メモ" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample" style="height: 88px!important">
                    <textarea data-prop="memo_sakusei" tabindex="50" class="text-selectAll"></textarea>
                </td>
            </tr>
            <%--        </tbody>
        <tbody class="">--%>
            <tr style="height: 88px!important" data-index-tr="37" class="data-index-tr-37">
                <td style="width: 15px; text-align: center; height: 88px!important" colspan="2" class="border-right-head">
                    <input type="text" value="評価" readonly="readonly" tabindex="-1" />
                </td>
                <td class="th-tmpl col-sample" style="height: 88px!important">
                    <textarea data-prop="hyoka" tabindex="51" class="text-selectAll"></textarea>
                </td>
            </tr>
        </tbody>
    </table>
</div>
