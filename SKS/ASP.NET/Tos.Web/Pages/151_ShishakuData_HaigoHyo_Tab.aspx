<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="151_ShishakuData_HaigoHyo_Tab.aspx.cs" Inherits="Tos.Web.Pages._151_ShishakuData_HaigoHyo_Tab" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchInputDialog(Ver2.1)】 Template--%>
<style type="text/css">
    #HaigoHyo_Tab .new tr td {
        padding: 0px;
        margin: 0px;
        height: 16px;
        font-size: 12px;
    }

    #HaigoHyo_Tab .new tr {
        height: 16px !important;
        padding: 0px;
    }

    #HaigoHyo_Tab .item_total_kotei_tml, .item_total_finish_weight_tml {
        display: none!important;
    }

    #HaigoHyo_Tab thead tr th input[type='tel'],
    #HaigoHyo_Tab thead tr th input[type='text'] {
        border: none;
        padding: 0px;
        margin: 0px;
        width: 100%;
        height: 100%;
    }

    #HaigoHyo_Tab .new .col-sample {
        min-width: 135px!important;
    }

    #HaigoHyo_Tab .new input[type='tel'],
    #HaigoHyo_Tab .new input[type='text'] {
        border: none;
        padding: 0px;
        margin: 0px;
        width: 100%;
        height: 100%;
    }

    #HaigoHyo_Tab .new select {
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

    #HaigoHyo_Tab .new label {
        height: 100%;
        width: 100%;
        padding: 0px;
        margin: 0px;
    }

    #HaigoHyo_Tab .new checkbox {
        margin: 0px;
    }
    
    /*2021/06/06 14007 START : Support #24755:試作データの配合表で試作列がずれて表示される事象が発生しています。*/
    #HaigoHyo_Tab .dt-head thead tr,
    /*2021/06/06 14007 END : Support #24755:試作データの配合表で試作列がずれて表示される事象が発生しています。*/
    #HaigoHyo_Tab thead tr th {
        padding: 0px !important;
        margin: 0px;
        height: 16px;
        /*font-size:11px;*/
        font-size: 12px;
    }
    /*2021/06/06 14007 START : Support #24755:試作データの配合表で試作列がずれて表示される事象が発生しています。*/
    #HaigoHyo_Tab .dt-head thead th input,
    #HaigoHyo_Tab .dt-head thead th select,
    #HaigoHyo_Tab .dt-head thead th label,
    #HaigoHyo_Tab .dt-head thead th textarea{        
        height: inherit!important;
    }
    /*2021/06/06 14007 END : Support #24755:試作データの配合表で試作列がずれて表示される事象が発生しています。*/

    #HaigoHyo_Tab thead tr {
        padding: 0px;
    }

    #HaigoHyo_Tab thead select {
        padding: 0px;
        border: none;
        margin: 0px;
        width: 100%;
        height: 16px;
        font-size: inherit;
        float: left;
    }

    #HaigoHyo_Tab thead label {
        height: 100% !important;
        width: 100%;
        padding: 0px;
        margin: 0px;
    }

    #HaigoHyo_Tab thead tr th.check-content.middle {
        padding-left: 61px!important;
    }

    #HaigoHyo_Tab thead tr th.check-content input[type='checkbox'] {
        margin: 0px!important;
        float: left;
    }

    #HaigoHyo_Tab thead tr th.check-content {
        width: 135px;
        text-align: left;
    }

    #HaigoHyo_Tab .th-tmpl {
        display: none;
    }

    #HaigoHyo_Tab .memo {
        border: none;
        padding: 0px;
        margin: 0px!important;
        background-color: transparent;
        width: 100%;
        height: 100%;
    }

    #HaigoHyo_Tab .dt-container .fix-columns .datatable {
        margin-right: 0px;
    }

    #HaigoHyo_Tab .datatable .new .change_budomari {
        font-weight: bold!important;
        color: red!important;
    }

    #HaigoHyo_Tab .datatable .new .has-error,
    #HaigoHyo_Tab .datatable .new .error {
        color: black!important;
    }

    #HaigoHyo_Tab th input:disabled:not(.selectedInput),
    #HaigoHyo_Tab th input:read-only:not(.selectedInput),
    #HaigoHyo_Tab th select:disabled:not(.selectedInput),
    #HaigoHyo_Tab th textarea:read-only:not(.selectedInput) {
        background-color: #efefef;
    }

    #HaigoHyo_Tab .datatable tr .th-none-button {
        text-align: right;
        padding-right: 5px !important;
        border-right-width: 3px;
        border-bottom-color: #efefef;
    }

    /*2019-09-12 : START : Q&A #15319 選択行の色*/
    /*#HaigoHyo_Tab .datatable .new .col-sample input:disabled:not(.selectedInput),
    #HaigoHyo_Tab .datatable .new .col-sample input:read-only:not(.selectedInput),
    #HaigoHyo_Tab .datatable .new .iroHaigo input:disabled:not(.selectedInput),
    #HaigoHyo_Tab .datatable .new .iroHaigo input:read-only:not(.selectedInput) {
        background-color: #efefef!important;
        color: rgb(0, 0, 0);
    }*/
    /*2019-09-12 : START : Q&A #15319 選択行の色*/

    #HaigoHyo_Tab .datatable .new .col-sample input:disabled:not(.change_budomari),
    #HaigoHyo_Tab .datatable .new .col-sample input:read-only:not(.change_budomari),
    #HaigoHyo_Tab .datatable .new .iroHaigo input:disabled:not(.change_budomari),
    #HaigoHyo_Tab .datatable .new .iroHaigo input:read-only:not(.change_budomari) {
        color: rgb(0, 0, 0)!important;
    }

    /*2019-09-10 : START : Bug #15319 選択行の色*/
    #HaigoHyo_Tab .datatable .selectedInput {
        background-color: #7cfc00!important;
    }
    /*2019-09-10 : START : Bug #15319 選択行の色*/
</style>
<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var HaigoHyo_Tab = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.maxInputDataCount,   // TODO:取得するデータ数を指定します。
            filter: ""
        },
        values: {
            validationsCellDefault: {
                rules: {
                    numberBug15481: true,
                    min: 0,
                    pointlength_custom: function (value, param, otps, done) {
                        value = value == "." ? "" : value;
                        var check = page.numPointlength(value, [6, 60]);
                        return done(check);
                    },
                    //・原料コードが「N」頭ではなく、かつ、原料名の先頭が「★」である場合
                    checkKuroiSuta: function (value, param, otps, done) {
                        return done(HaigoHyo_Tab.checkKuroiSuta(value, param, otps, done));
                    },
                    checkCommandcode: function (value, param, otps, done) {
                        return done(HaigoHyo_Tab.checkCommandcode(value, param, otps, done));
                    }
                },
                options: {
                    name: "量"
                },
                messages: {
                    numberBug15481: App.messages.base.number,
                    min: App.messages.base.min,
                    pointlength_custom: App.str.format(App.messages.base.pointlength, { name: "量", param: [6, 4] }),
                    checkKuroiSuta: "checkKuroiSuta",//App.messages.app.AP0076,
                    checkCommandcode: "checkCommandcode"//App.messages.app.AP0076
                }
            },
            validationsTotalDefault: {
                rules: {
                    numberBug15481: true,
                    min: 0,
                    pointlength_custom: function (value, param, otps, done) {
                        value = value == "." ? "" : value;
                        var check = page.numPointlength(value, [10, 60]);
                        return done(check);
                    }
                },
                options: {
                    name: "工程仕上重量"
                },
                messages: {
                    numberBug15481: App.messages.base.number,
                    min: App.messages.base.min,
                    pointlength_custom: App.str.format(App.messages.base.pointlength, { name: "工程仕上重量", param: [10, 4] })
                }
            },
            flg_no_seiho: {
                isKopiLast: "*",//最後に製法コピーが行われた試作列に表示
                shisanIrai: "$"//原価試算表が出力した試作列に表示
            },
            isChangeRunning: [],
            pt_kotei: null,
            flg_shisanIrai: 1,//原価試作依頼されている列は値を変更できない（空白行も）
            taniHyoji: [],
            stackCallCount: 0
        },
        urls: {
            search: "../api/_151_ShishakuData_HaigoHyo_Tab_",
            getGenryoKojo: "../api/_151_ShishakuData_HaigoHyo_Tab_/Post_GenryoKojo",
            getGenryoFocus: "../api/_151_ShishakuData_HaigoHyo_Tab_/Post_GenryoFocus",
            getGenryoData: "../api/_151_ShishakuData_HaigoHyo_Tab_/GetGenryoData",
            getSaikinJoho: "../api/_151_ShishakuData_HaigoHyo_Tab_/Post_SaikinJoho",
            literal: "../Services/ShisaQuickService.svc/ma_literal?$filter=cd_category eq '{0}' and (value1 eq {1} or value1 eq {2})"
        },
        detail: {
            options: {
                dataKeyQuantity: {},
                maxSeqShisaku: 0,
                maxSeqKotei: 0,
                maxKotei: 0
            },
            values: {
                currentColChanged: undefined,
                lstOverLoadKanzan: []
            }
        },
        commands: {},
        dialogs: {},
        mode: {
            input: 0,
            edit: 1
        }
    };

    HaigoHyo_Tab.validationSuccessOnlyInput = function (results, state) {
        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {

            item = results[i];
            HaigoHyo_Tab.setColValidStyleOnlyInput(item.element);
            App.ui.page.notifyAlert.remove(item.element);
        }
    };

    /**
     * バリデーション成功時の処理を実行します。
     */
    HaigoHyo_Tab.validationSuccess = function (results, state) {
        var i = 0, l = results.length,
           item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                //列可変はグリッドでもセル単位で色付けを行う
                HaigoHyo_Tab.setColValidStyle(item.element, state.isGridValidation);
            } else {
                HaigoHyo_Tab.setColValidStyle(item.element);
            }

            App.ui.page.notifyAlert.remove(item.element);
        }
    };

    /**
     * バリデーション失敗時の処理を実行します。
     */
    HaigoHyo_Tab.validationFail = function (results, state) {

        var i = 0, l = results.length,
             item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                //列可変はグリッドでもセル単位で色付けを行う
                HaigoHyo_Tab.setColInvalidStyle(item.element, state.isGridValidation);
                //・原料コードが「N」頭ではなく、かつ、原料名の先頭が「★」である場合
                //・原料コードが「999999」かつ配合量が空白ではない場合、エラーメッセージ表示
                if (state.isCheckIrai) {
                    item.message = HaigoHyo_Tab.validationIraiFail(state, item);
                }
            } else {
                HaigoHyo_Tab.setColInvalidStyle(item.element);
            }

            if (state && state.suppressMessage) {
                continue;
            }
            App.ui.page.notifyAlert.message(item.message, item.element).show();
        }
    };

    /**
     * 原料コードが「N」頭ではなく、かつ、原料名の先頭が「★」である場合
     * 原料コードが「999999」かつ配合量が空白ではない場合、エラーメッセージ表示
     */
    HaigoHyo_Tab.validationIraiFail = function (state, tagets) {
        var id = state.tbody.findP(tagets.item).closest("td").attr("data-key");

        if (App.isUndefOrNull(id)) {
            return "";
        };

        var entityShisa = HaigoHyo_Tab.detail.dataShisaku.entry(id),
            cd_genryo = state.tbody.findP("cd_genryo").val(),
            nm_genryo = state.tbody.findP("nm_genryo").val();

        return App.str.format(App.messages.app.AP0076, {
            nm_sample: (entityShisa.nm_sample == null ? "" : entityShisa.nm_sample)
            , cd_genryo: cd_genryo
            , nm_genryo: nm_genryo
        });
    };

    HaigoHyo_Tab.validationFailOnlyInput = function (results, state) {
        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {

            item = results[i];
            HaigoHyo_Tab.setColInvalidStyleOnlyInput(item.element);

            if (state && state.suppressMessage) {
                continue;
            }

            App.ui.page.notifyAlert.message(item.message, item.element).show();
        }
    };

    /**
     * バリデーション後の処理を実行します。
     */
    HaigoHyo_Tab.validationAlways = function (results) {
        //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
    };

    /**
      * 指定された定義をもとにバリデータを作成します。
      * @param target バリデーション定義
      * @param options オプションに設定する値。指定されていない場合は、
      *                画面の success/fail/always のハンドル処理が指定されたオプションが設定されます。
      */
    HaigoHyo_Tab.createValidator = function (target, options) {
        return App.validation(target, options || {
            success: HaigoHyo_Tab.validationSuccess,
            fail: HaigoHyo_Tab.validationFail,
            always: HaigoHyo_Tab.validationAlways
        });
    };

    /**
    * 指定された定義をもとにバリデータを作成します。
    * @param target バリデーション定義
    * @param options オプションに設定する値。指定されていない場合は、
    *                画面の success/fail/always のハンドル処理が指定されたオプションが設定されます。
    */
    HaigoHyo_Tab.createValidatorOnlyInput = function (target, options) {
        return App.validation(target, options || {
            success: HaigoHyo_Tab.validationSuccessOnlyInput,
            fail: HaigoHyo_Tab.validationFailOnlyInput
        });
    };

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    HaigoHyo_Tab.setColInvalidStyle = function (target, isGrid) {
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
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    HaigoHyo_Tab.setColInvalidStyleOnlyInput = function (target) {

        var $target = HaigoHyo_Tab.detail.element.find(target);

        $target.addClass("has-error");
    };

    /**
     * 単項目要素をエラー無しのスタイルに設定します。
     * @param target 設定する要素
     */
    HaigoHyo_Tab.setColValidStyle = function (target, isGrid) {
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
     * 単項目要素をエラー無しのスタイルに設定します。
     * @param target 設定する要素
     */
    HaigoHyo_Tab.setColValidStyleOnlyInput = function (target) {

        var $target = HaigoHyo_Tab.detail.element.find(target);

        $target.removeClass("has-error");
    };

    /**
     * すべてのバリデーションを実行します。
     */
    HaigoHyo_Tab.validateAll = function (isSave) {

        var validations = [];

        validations.push(HaigoHyo_Tab.detail.validateList(false, isSave));
        validations.push(HaigoHyo_Tab.detail.validateHeader());
        validations.push(HaigoHyo_Tab.detail.checkOverloadKanzan());

        validations.push(HaigoHyo_Tab.detail.checkMaxRowBFSave());      // Bug #1738

        //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。

        return App.async.all(validations);
    };

    /**
     * Check row count before save
     * Bug #1738
     */
    HaigoHyo_Tab.detail.checkMaxRowBFSave = function () {
        if (HaigoHyo_Tab.detail.checkMaxRowBFS()) {         // Bug #1738
            return App.async.success();
        };
        return App.async.fail();
    }

    /**
     * 明細のバリデーションフィルターを設定します。（必須チェックを行わない）
     */
    HaigoHyo_Tab.detail.validationFilter = function (item, method, state, options) {
        return method !== "required" && method !== "requiredCustom";
    };

    HaigoHyo_Tab.detail.validateHeader = function (suppressMessage) {
        var validations = [],
            options = {
                state: {
                    suppressMessage: suppressMessage,
                }
            };

        var element = HaigoHyo_Tab.detail.elementShisaku;

        // セルに対してバリデーションを実行します。
        element.find(".col-sample").not(".th-tmpl").find(".fiel-validation:enabled").each(function (index, elem) {
            validations.push(HaigoHyo_Tab.detail.validator.validate({
                targets: $(elem),
                state: {
                    suppressMessage: suppressMessage,
                    tbody: element,
                    isGridValidation: true
                }
            }));
        });
        return App.async.all(validations);
    };

    /**
     * 明細の一覧全体のバリデーションを実行します。
     */
    HaigoHyo_Tab.detail.validateList = function (suppressMessage, isSave) {
        var validations = [],
            options = {
                state: {
                    suppressMessage: suppressMessage,
                    isSave: isSave
                }
            };

        //2019-10-04 : START : Bug #15484 (★ｹｲﾊﾟｯｸ向け明太処理液(無着色))
        cd_kaishaHin = HaigoHyo_Tab.getDataHeader().cd_kaisha;
        //2019-10-04 : END : Bug #15484 (★ｹｲﾊﾟｯｸ向け明太処理液(無着色))

        // AOH ext
        var el = ["kg_costrecipe", "ko_costrecipe", "su_fc", "su_brix", "su_ph", "su_ca", "juryo_shiagari_g"],
            elSel = [];
        $.each(el, function (i, e) {
            elSel.push(App.str.format("[data-prop='{0}']", e));
        });
        elSel = elSel.join(",");

        HaigoHyo_Tab.detail.dataTable.dataTable("each", function (row, index) {
            // セルに対してバリデーションを実行します。
            //row.element.find(".col-sample").not("th-tmpl").find(".fiel-validation").each(function (index, elem) {
            row.element.find(".fiel-validation:enabled").each(function (index, elem) {
                target = $(elem);
                var prop = target.attr("data-prop");
                if (el.indexOf(prop) >= 0) {
                    return;
                }
                validations.push(HaigoHyo_Tab.detail.validator.validate({
                    targets: target,
                    state: {
                        suppressMessage: suppressMessage,
                        tbody: row.element,
                        isGridValidation: true,
                        isSave: isSave,
                        isCheckIrai: target.hasClass("iraiChecked"),
                        //2019-10-04 : START : Bug #15484 (★ｹｲﾊﾟｯｸ向け明太処理液(無着色))
                        cd_kaishaHin: cd_kaishaHin
                        //2019-10-04 : END : Bug #15484 (★ｹｲﾊﾟｯｸ向け明太処理液(無着色))
                    }
                }));
            });

            row.element.find("td[data-key][index-col]").find(elSel).each(function (index, elem) {
                var target = $(elem);
                validations.push(HaigoHyo_Tab.detail.elementShisaku.validation().validate({
                    targets: target,
                    state: {
                        tbody: row.element,
                        isGridValidation: true,
                        isSave: isSave,
                    }
                }));
            });
        });

        return App.async.all(validations);
    };

    /**
     * ダイアログの初期化処理を行います。
     */
    HaigoHyo_Tab.initialize = function () {
        var element = $("#HaigoHyo_Tab");

        element.show();
        HaigoHyo_Tab.element = element;

        HaigoHyo_Tab.initializeControlEvent();
        HaigoHyo_Tab.detail.initialize();

        HaigoHyo_Tab.dataKeySample = {};

        HaigoHyo_Tab.loadMasterData().then(function () {

        }).fail(function (error) {
            if (error.status === 404) {
                App.ui.page.notifyWarn.message(App.ajax.handleError(error).message).show();
            }
            else {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            }
        })
    };

    /**
    * 注意事項を更新する。
    */
    HaigoHyo_Tab.detail.updateChui = function (data) {
        var element = HaigoHyo_Tab.detail.elementShisaku,
            dataChui = HaigoHyo_Tab.detail.dataChui.findAll(function (item, entity) {
                return item.no_chui == data.no_chui;
            });

        if (dataChui.length) {
            var id = dataChui[0].__id,
                entity = HaigoHyo_Tab.detail.dataChui.entry(id);

            entity["chuijiko"] = data["chuijiko"];
            HaigoHyo_Tab.detail.dataChui.update(entity);
        }
    };

    /**
    *注意事項Noを新たに採番し、配合表タブ.注意事項Noコンボボックスへ値を設定。
    */
    HaigoHyo_Tab.detail.addNewChui = function (data) {

        var element = HaigoHyo_Tab.detail.elementShisaku,
            chui = element.find(".no_chui"),
            isNewData = true;

        //（試作列未選択・選択関係なく、全試作列に対して注意事項Noが採番される）
        var $control;
        $.each(chui, function () {
            $control = element.find(this);
            $control.append("<option value='" + page.values.masterData.no_chui_max + "'>" + page.values.masterData.no_chui_max + "</option>");
        })

        //データに新しい項目を追加する
        //試作CD-社員CD
        data["cd_shain"] = page.values.cd_shain;
        //試作CD-年
        data["nen"] = page.values.nen;
        //試作CD-追番
        data["no_oi"] = page.values.no_oi;
        //注意事項NO
        data["no_chui"] = page.values.masterData.no_chui_max;
        //注意事項
        data["chuijiko"] = data["chuijiko"];

        (isNewData ? HaigoHyo_Tab.detail.dataChui.add : HaigoHyo_Tab.detail.dataChui.attach).bind(HaigoHyo_Tab.detail.dataChui)(data);

        App.ui.loading.close();
    };

    /**
    *試作列番号を取ってする。
    */
    HaigoHyo_Tab.detail.trialManualCheked = function () {
        var dataChuiOnScreen = function () {

            var dataChui = HaigoHyo_Tab.detail.dataChui.findAll(function (item, entity) {
                return entity.state !== App.ui.page.dataSet.status.Deleted;
            });

            return dataChui;
        };

        var deferred = $.Deferred(),
            element = HaigoHyo_Tab.detail.element,
            dt_head = HaigoHyo_Tab.detail.elementShisakuHead,
            head_check = dt_head.find('input:checked');

        if (head_check.length == 0) {
            deferred.resolve({
                seq_shisaku: null,
                entity: {},
                dataChui: dataChuiOnScreen()
            });

            return deferred;
        }

        var thcheck = element.find(head_check).closest("th"),
            indexCol = thcheck.attr("index-col"),
            id = thcheck.attr("data-key");

        if (App.isUndefOrNull(id)) {
            deferred.resolve({
                seq_shisaku: indexCol,
                entity: {},
                dataChui: dataChuiOnScreen()
            });

            return deferred;
        }
        deferred.resolve({
            seq_shisaku: indexCol,
            entity: HaigoHyo_Tab.detail.dataShisaku.entry(id),
            dataChui: dataChuiOnScreen()
        });

        return deferred;
    }


    /**
    *  契約の表示を増やすには色をチェックする
    */
    HaigoHyo_Tab.lightOrDark = function (color) {

        if (color == null || color == undefined || color == "" || !color) {
            return "";
        }

        if (color.indexOf("#") != 0) {
            return "";
        }
        // Variables for red, green, blue values
        var r, g, b, hsp;

        // Check the format of the color, HEX or RGB?
        if (color.match(/^rgb/)) {

            // If HEX --> store the red, green, blue values in separate variables
            color = color.match(/^rgba?\((\d+),\s*(\d+),\s*(\d+)(?:,\s*(\d+(?:\.\d+)?))?\)$/);

            r = color[1];
            g = color[2];
            b = color[3];
        }
        else {

            // If RGB --> Convert it to HEX
            color = +("0x" + color.slice(1).replace(
            color.length < 5 && /./g, '$&$&'));

            r = color >> 16;
            g = color >> 8 & 255;
            b = color & 255;
        }

        // HSP (Highly Sensitive Poo) 
        hsp = Math.sqrt(0.299 * (r * r) + 0.587 * (g * g) + 0.114 * (b * b));

        // Using the HSP value, determine whether the color is light or dark
        if (hsp > 127.5) {

            return 'black';
        }
        else {

            return 'white';
        }
    }

    /**
    * get shisaku column checked for copy to seiho
    */
    HaigoHyo_Tab.shisakuKopiChecked = function () {
        var deferred = $.Deferred();

        var element = HaigoHyo_Tab.detail.element,
            dt_head = HaigoHyo_Tab.detail.elementShisakuHead,
            head_check = dt_head.find('input:checked'),
            htmlSrc, itemSample;

        var inforSampleCheck = {
            seq_shisaku: undefined,
            total_juryo_shiagari_seq: 0,
            cd_kaisha: App.ui.page.user.cd_kaisha
        };

        App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0028, { strName: "試作列" })).remove();
        if (head_check.length == 0) {
            //試作列が選択されていること
            App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0028, { strName: "試作列" })).show();
            return deferred.reject();
        }

        HaigoHyo_Tab.detail.validator = element.validation(HaigoHyo_Tab.createValidator(HaigoHyo_Tab.detail.options.validationsKopiMode));
        HaigoHyo_Tab.validateKopi().then(function () {

            var id = element.find(head_check).closest("th").attr("data-key"),
                entity = HaigoHyo_Tab.detail.dataShisaku.entry(id);

            inforSampleCheck.seq_shisaku = entity.seq_shisaku;
            //合計重量（kg）
            inforSampleCheck.total_juryo_shiagari_seq = element.find(".sample-" + entity.seq_shisaku).findP("total_weight").val();

            deferred.resolve(inforSampleCheck);
        }).fail(function () {
            deferred.reject();
        })

        return deferred.promise();
    }

    /**
     * すべてのバリデーションを実行します。
     */
    HaigoHyo_Tab.validateKopi = function () {

        var validations = [];

        validations.push(HaigoHyo_Tab.detail.validateListKopi());

        //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。

        return App.async.all(validations);
    };


    /**
     * 明細の一覧全体のバリデーションを実行します。
     */
    HaigoHyo_Tab.detail.validateListKopi = function (suppressMessage) {
        var validations = [],
            options = {
                state: {
                    suppressMessage: suppressMessage,
                }
            };

        HaigoHyo_Tab.detail.dataTable.dataTable("each", function (row, index) {

            // セルに対してバリデーションを実行します。
            $(row.element[1]).find(":input").each(function (index, elem) {

                validations.push(HaigoHyo_Tab.detail.validator.validate({
                    targets: $(elem),
                    state: {
                        suppressMessage: suppressMessage,
                        tbody: row,
                        isGridValidation: true
                    }
                }));

            });
        });

        return App.async.all(validations);
    };

    /**
    * update from other tab change
    */
    HaigoHyo_Tab.detail.updateFromOtherTab = function (fiel, val, sample) {
        switch (fiel) {
            case "cd_kaisha":
            case "cd_kojo":
                HaigoHyo_Tab.detail.kaishaKojoKoshin(false);
                break;
            case "genryohiTotal":
                HaigoHyo_Tab.detail.updateGenryohi(val);
            case "flg_shisanIrai":
                HaigoHyo_Tab.detail.shisanIrai(val, sample);
                break;
            case "cd_tani":
            case "yoryo":
                HaigoHyo_Tab.detail.calculateTotal("ChangeTani");
                break;
            default:
                break;
        }
    }

    /**
    * 配合タブで原価試算依頼コントロールクラス
    */
    HaigoHyo_Tab.detail.shisanIrai = function (val, sample) {
        var element = HaigoHyo_Tab.detail.element.find("." + sample);

        //チェック時にクラスを追加
        if (val) {
            element.find(".fiel-validation").addClass("iraiChecked");
        } else {
            //チェックを外したときにクラスを削除する
            element.find(".iraiChecked").removeClass("iraiChecked");
        }
    }

    /**
    *　原料費と水相中。。。を更新する
    */
    HaigoHyo_Tab.detail.updateGenryohi = function (sampleData) {
        var element = HaigoHyo_Tab.detail.element,
            sample;

        //loop sample col
        $.each(sampleData, function (samp, data) {
            sample = element.find(samp);
            //loop property
            $.each(data, function (prop, value) {
                sample.findP(prop).val(value);
            })
        })
    };

    /**
    *　分析マスタの最新情報に更新。
    */
    HaigoHyo_Tab.detail.saikinJoho = function () {
        App.ui.loading.show();

        var element = HaigoHyo_Tab.detail.elementHaigo,
            //2019-12-19 : START : Bug #16083 : 【101試作データ②特性値】水相中MSGが計算されない。
            //genryo = element.find(".genryo-input:enabled");
            genryo = element.find(".genryo-input");
        //2019-12-19 : START : Bug #16083 : 【101試作データ②特性値】水相中MSGが計算されない。

        if (!genryo.length) {

            App.ui.loading.close();
            return;
        }

        var shisakuHin = HaigoHyo_Tab.getDataHeader(),//data shisaku hin
            listCheck = {},
            genryoList = [],
            bodyList = {},
            elem, val, tbody, id, entity;

        //update kaisha, kojo for all row genryo
        $.each(genryo, function () {
            elem = element.find(this);
            tbody = elem.closest("tbody");
            id = tbody.attr("data-key"),
            cd_genryo = elem.val();

            if (App.isUndefOrNull(id) || !cd_genryo.length) {
                return true;
            }

            entity = HaigoHyo_Tab.detail.dataHaigo.entry(id);

            var cd_genryo = elem.val();
            if (!listCheck[cd_genryo] && cd_genryo != null && cd_genryo.length && cd_genryo != App.settings.app.commentCode) {
                listCheck[cd_genryo] = true;//flag distinct genryo
                genryoList.push({
                    cd_genryo: cd_genryo,
                    //cd_kaisha: shisakuHin.cd_kaisha //2020-04-01 : Bug #17122 : 分析マスタの最新情報に更新 基本情報の会社を使わない原料の会社で調べます
                    cd_kaisha: entity.cd_kaisha
                })
            }

            bodyList[id] = {
                tbody: tbody,
                entity: entity,
                genryo: cd_genryo
            };
        })

        if (!genryoList.length) {
            App.ui.loading.close();
            return;
        }

        return $.ajax(App.ajax.webapi.post(HaigoHyo_Tab.urls.getSaikinJoho, value = genryoList)).then(function (result) {

            $.each(bodyList, function (id, item) {
                var genryoSaikin = jQuery.grep(result, function (n, i) {
                    //return (n.cd_genryo == item.genryo && n.cd_kaisha == shisakuHin.cd_kaisha);//2020-04-01 : Bug #17122 : 分析マスタの最新情報に更新 基本情報の会社を使わない原料の会社で調べます
                    return (n.cd_genryo == item.genryo && n.cd_kaisha == item.entity.cd_kaisha);
                });

                if (!genryoSaikin.length) {
                    return true;
                }

                genryoSaikin = genryoSaikin[0];
                entity = item.entity;

                //油含有率
                //ritu_abura = page.detail.beforFormatNumber(genryoSaikin.ritu_abura, 2);
                //item.tbody.findP("ritu_abura").val(ritu_abura.forDisp);
                //entity["ritu_abura"] = ritu_abura.forData;

                //酢酸
                entity["ritu_sakusan"] = genryoSaikin.ritu_sakusan;
                //食塩
                //entity["ritu_shokuen"] = genryoSaikin.ritu_shokuen;
                //総酸
                //entity["ritu_sousan"] = genryoSaikin.ritu_sousan;
                //率ＭＳＧ
                entity["ritu_msg"] = genryoSaikin.ritu_msg;

                HaigoHyo_Tab.detail.dataHaigo.update(entity);
            })

            if (App.isFunc(HaigoHyo_Tab.recalculation)) {
                //2019-12-12 START : Bug #16083 :【分析マスタの最新情報に更新】ボタンを押す時画面が計算しる。
                //HaigoHyo_Tab.recalculation("tokuseichi_tab", "油含有率");
                //var isNotCheckFlgAuto = true;
                //HaigoHyo_Tab.recalculation("tokuseichi_tab", "油含有率", undefined, isNotCheckFlgAuto);
                //2019-12-12 END : Bug #16083 :【分析マスタの最新情報に更新】ボタンを押す時画面が計算しる。
            } else {
                App.ui.loading.close();
            }

        }).fail(function () {
            App.ui.loading.close();
        })
    };

    /**
     * 原料マスタから特性値を取得し、画面に反映する
     */
    HaigoHyo_Tab.detail.kaishaKojoKoshin = function (isJohoKoshin) {
        App.ui.loading.show();

        var element = HaigoHyo_Tab.detail.elementHaigo,
            genryo = element.find(".genryo-input:enabled");

        if (!genryo.length) {

            App.ui.loading.close();
            return;
        }

        var shisakuHin = HaigoHyo_Tab.getDataHeader(),//data shisaku hin
            listCheck = {},
            genryoList = [],
            bodyList = {},
            elem, val, tbody, id, entity;

        //update kaisha, kojo for all row genryo
        $.each(genryo, function () {
            elem = element.find(this);
            tbody = elem.closest("tbody");
            id = tbody.attr("data-key"),
            cd_genryo = elem.val();

            if (App.isUndefOrNull(id)) {
                return true;
            }

            entity = HaigoHyo_Tab.detail.dataHaigo.entry(id);

            if (!cd_genryo.length) {
                //担当会社
                entity["cd_kaisha"] = shisakuHin.cd_kaisha;
                //担当工場
                entity["cd_busho"] = shisakuHin.cd_kojo;
                //Request #14303 : 基本、tr_haigoと同じデータ
                ////担当会社
                //entity["cd_kaisha_genryo"] = shisakuHin.cd_kaisha;
                ////担当工場
                //entity["cd_kojo_genryo"] = shisakuHin.cd_kojo;

                HaigoHyo_Tab.detail.dataHaigo.update(entity);
                return true;
            }


            var cd_genryo = elem.val();
            if (!listCheck[cd_genryo] && cd_genryo != null && cd_genryo.length && cd_genryo != App.settings.app.commentCode) {
                listCheck[cd_genryo] = true;//flag distinct genryo
                genryoList.push({
                    cd_genryo: cd_genryo,
                    cd_kaisha: shisakuHin.cd_kaisha
                })
            }

            bodyList[id] = {
                tbody: tbody,
                entity: entity,
                genryo: cd_genryo
            };
        })

        HaigoHyo_Tab.detail.Genryo(genryoList, {
            cd_kaisha: shisakuHin.cd_kaisha,
            cd_busho: shisakuHin.cd_kojo
        }).then(function (results) {
            HaigoHyo_Tab.detail.genryoKojoKoshin(results, bodyList, shisakuHin, isJohoKoshin);
        }).fail(function () {
            App.ui.loading.close();
        })
    };


    /**
    * update all genryo with new kaisha, kojo 
    */
    HaigoHyo_Tab.detail.genryoKojoKoshin = function (dataGenryo, bodyList, shisakuHin, isJohoKoshin) {
        var isUpdateCalcParam = false;
        var updateList = [];

        $.each(bodyList, function (id, item) {
            var cd_genryo = item.genryo,
                entity = item.entity;

            var eGenryo = item.tbody.findP("nm_genryo"),
                eTanka = item.tbody.findP("tanka");

            var addBlackStar = HaigoHyo_Tab.detail.addBlackStar,
                addWhiteStar = HaigoHyo_Tab.detail.addWhiteStar,
                disableInp = HaigoHyo_Tab.detail.disableInp;

            var isGenryoB = HaigoHyo_Tab.detail.genryoBCheck(cd_genryo);

            var syncCalcParamIns = HaigoHyo_Tab.detail.getCalcParam();

            //フォーカスが外れたときにクラスの自動オープンダイアログを削除
            //item.tbody.find(".focusout_open_dialog").removeClass(".focusout_open_dialog");
            //直すことをチェックできする。
            //原料名  
            item.tbody.findP("nm_genryo").prop("readonly", false);
            //単価（※）
            item.tbody.findP("tanka").prop("readonly", false);

            // Reset genryo status
            HaigoHyo_Tab.detail.setGenryoExistState(item.tbody);

            ////原料原本
            //entity["cd_genryo_gry"] = null;
            //歩留原本
            entity["budomari_gry"] = null;

            if (!cd_genryo.length) {
                HaigoHyo_Tab.detail.dataHaigo.update(entity);

                //フォントを赤色・太文字に変える。
                HaigoHyo_Tab.detail.iroBudomari(item.tbody, entity);

                // Clear all total param
                syncCalcParamIns.setSyncAllState();
                syncCalcParamIns.updateTotalCalcParam(entity, {});

                return;
            }

            //原料コードが全て""9""の場合は変更可能上記以外の場合は変更不可
            if (cd_genryo == App.settings.app.commentCode) {

                //担当会社
                entity["cd_kaisha"] = shisakuHin.cd_kaisha;
                //担当工場
                entity["cd_busho"] = shisakuHin.cd_kojo;

                HaigoHyo_Tab.detail.dataHaigo.update(entity);

                //フォントを赤色・太文字に変える。
                HaigoHyo_Tab.detail.iroBudomari(item.tbody, entity);

                // Clear all total param
                //HaigoHyo_Tab.detail.updateTotalCalcParam(entity, {});

                return;
            }

            var dataGenryoKojo = jQuery.grep(dataGenryo, function (n, i) {
                return (n.cd_genryo == cd_genryo);
            });

            //原料原本
            entity["tanka_org"] = entity["tanka"];
            //歩留原本
            entity["budomari_org"] = entity["budomari"];

            //原料名  
            var nm_genryo = HaigoHyo_Tab.detail.substringFirst((entity.nm_genryo || ""));
            if (dataGenryoKojo.length) {
                var new_genryo = dataGenryoKojo[0];
                new_genryo.nm_genryo = (new_genryo.nm_genryo || "");

                var isGenryoBNew = HaigoHyo_Tab.detail.genryoBCheck(new_genryo.cd_genryo);

                if (new_genryo.cd_kaisha == shisakuHin.cd_kaisha && new_genryo.cd_busho == shisakuHin.cd_kojo) {
                    //担当会社
                    entity["cd_kaisha"] = shisakuHin.cd_kaisha;
                    //担当工場
                    entity["cd_busho"] = shisakuHin.cd_kojo;
                    //原料名  
                    nm_genryo = new_genryo.nm_genryo;
                    disableInp(eGenryo);
                    //単価（※）
                    entity["tanka"] = new_genryo.tanka;
                    //disableInp(eTanka);
                    //歩留（※）
                    entity["budomari"] = new_genryo.budomari;
                    //isChangeCalcParam = true;
                    syncCalcParamIns.setSyncAllState();
                }

                if (new_genryo.cd_kaisha == shisakuHin.cd_kaisha && new_genryo.cd_busho != null && new_genryo.cd_busho != shisakuHin.cd_kojo) {
                    if (isGenryoBNew) {
                        //担当会社
                        entity["cd_kaisha"] = shisakuHin.cd_kaisha;
                        //担当工場
                        entity["cd_busho"] = 0;
                        //isChangeCalcParam = false;
                        syncCalcParamIns.setSyncApartState();
                    } else {
                        //担当会社
                        entity["cd_kaisha"] = shisakuHin.cd_kaisha;
                        //担当工場
                        entity["cd_busho"] = shisakuHin.cd_kojo;
                        //原料名  
                        nm_genryo = addWhiteStar(new_genryo.nm_genryo);
                        disableInp(eGenryo);
                        //単価（※）
                        //disableInp(eTanka);
                        //isChangeCalcParam = false;
                        syncCalcParamIns.setUnSyncState();
                    }
                }

                if (new_genryo.cd_kaisha != shisakuHin.cd_kaisha && new_genryo.cd_busho != null) {
                    if (isGenryoBNew) {
                        //担当会社
                        entity["cd_kaisha"] = shisakuHin.cd_kaisha;
                        //担当工場
                        entity["cd_busho"] = 0;
                        //原料名  
                        nm_genryo = addBlackStar(nm_genryo);
                        //isChangeCalcParam = false;
                        syncCalcParamIns.setUnSyncState();
                    } else {
                        //担当会社
                        entity["cd_kaisha"] = shisakuHin.cd_kaisha;
                        //担当工場
                        entity["cd_busho"] = shisakuHin.cd_kojo;
                        //直すことをチェックできする。
                        //原料名  
                        nm_genryo = addBlackStar(nm_genryo);
                        disableInp(eGenryo);
                        //isChangeCalcParam = false;
                        syncCalcParamIns.setUnSyncState();
                    }
                }

                if (new_genryo.cd_busho == null) {
                    //担当会社
                    entity["cd_kaisha"] = shisakuHin.cd_kaisha;
                    //担当工場
                    entity["cd_busho"] = shisakuHin.cd_kojo;

                    //原料名 
                    nm_genryo = addBlackStar("");
                    disableInp(eGenryo);
                    //isChangeCalcParam = false;
                    syncCalcParamIns.setSyncApartState();
                }

                //歩留原本
                entity["budomari_gry"] = new_genryo.budomari_gry;
            } else {
                if (isGenryoB) {
                    //担当会社
                    entity["cd_kaisha"] = shisakuHin.cd_kaisha;
                    //担当工場
                    entity["cd_busho"] = 0;

                    //原料名 
                    if (App.isUndefOrNullOrStrEmpty(nm_genryo)) {
                        nm_genryo = addBlackStar("");
                    }
                    //isChangeCalcParam = false;
                    syncCalcParamIns.setUnSyncState();
                } else {
                    //担当会社
                    entity["cd_kaisha"] = shisakuHin.cd_kaisha;
                    //担当工場
                    entity["cd_busho"] = shisakuHin.cd_kojo;
                    //原料名  
                    nm_genryo = addBlackStar("");
                    disableInp(eGenryo);
                    //isChangeCalcParam = false;
                    syncCalcParamIns.setUnSyncState();
                }
                // Genryo not exist
                HaigoHyo_Tab.detail.setGenryoNotExistState(item.tbody);
            }
            entity["nm_genryo"] = nm_genryo;

            // リテラルマスタ.リテラル値1 = '0' 又は該当データなしの時、単価開示不可
            //if (!HaigoHyo_Tab.detail.controlTankaHyoji(shisakuHin.cd_kaisha)) {
            //    //    //単価（※）
            //    //    var tanka = page.detail.beforFormatNumber(entity["tanka_org"], 2);
            //    //    item.tbody.findP("tanka").val(tanka.forDisp);
            //    //    entity["tanka"] = tanka.forData;
            //    //    //歩留（※）
            //    //    var budomari = page.detail.beforFormatNumber(entity["budomari_org"], 2);
            //    //    item.tbody.findP("budomari").val(budomari.forDisp);
            //    //    entity["budomari"] = budomari.forData;
            //    entity["tanka"] = null;
            //    entity["budomari"] = null;
            //}

            item.tbody.form(HaigoHyo_Tab.detail.options.bindOption).bind(HaigoHyo_Tab.detail.getChangeGenryoData(entity));

            //フォントを赤色・太文字に変える。
            HaigoHyo_Tab.detail.iroBudomari(item.tbody, entity);

            HaigoHyo_Tab.detail.dataHaigo.update(entity);
            if (syncCalcParamIns.isSync()) {
                isUpdateCalcParam = true;
                updateList.push(syncCalcParamIns.syncTotalCalcParam(entity.cd_genryo, entity.cd_kaisha, entity.cd_busho, entity, true));
            }
            HaigoHyo_Tab.detail.validateHiago(item.tbody);
        })
        // Sync calc param + recalc total
        if (isUpdateCalcParam) {
            App.async.all(updateList).then(function () {
                HaigoHyo_Tab.detail.calculateTotal("ChangeKojo");
            });
        }
        App.ui.loading.close();
    }

    /**
    * Validate row of current haigo
    */
    HaigoHyo_Tab.detail.validateHiago = function (row) {
        return HaigoHyo_Tab.detail.validator.validate({
            targets: row.find("input"),
            state: {
                tbody: row,
                isGridValidation: true
            }
        });
    }

    /**
    * 特徴コピー時にタブをリロードする
    */
    HaigoHyo_Tab.reLoadTab = function () {

        HaigoHyo_Tab.detail.options.maxKotei = 0;
        HaigoHyo_Tab.detail.options.maxSeqShisaku = 0;
        HaigoHyo_Tab.detail.options.maxSeqKotei = {};
        HaigoHyo_Tab.detail.options.validationsShisakuList = {};
        HaigoHyo_Tab.detail.options.dataKeyQuantity = {};
        HaigoHyo_Tab.dataKeySample = {};
        HaigoHyo_Tab.dataKeySample["sample-1"] = HaigoHyo_Tab.detail.elementShisakuHead.find(".sample-1").attr("data-key");

        //renew data set
        HaigoHyo_Tab.detail.dataHaigo = App.ui.page.dataSet();
        HaigoHyo_Tab.detail.dataKotei = App.ui.page.dataSet();
        HaigoHyo_Tab.detail.dataShisaku = App.ui.page.dataSet();
        HaigoHyo_Tab.detail.dataShisakuList = App.ui.page.dataSet();
        HaigoHyo_Tab.detail.dataChui = App.ui.page.dataSet();

        //clear table
        HaigoHyo_Tab.detail.dataTable.dataTable("clear");

        //clear sample
        var element = HaigoHyo_Tab.detail.element,
            quantity = HaigoHyo_Tab.detail.elementShisakuHead.find(".col-sample");

        if (!quantity.length) {
            return;
        }

        var index = 0,
            sample;

        while (quantity[index]) {
            sample = element.find(quantity[index]);
            index += 1;

            if (sample.hasClass("th-tmpl")) {
                continue;
            }
            var id = sample.attr("index-col");

            element.find(".sample-" + id).remove();

            //テーブルでサイズ変更
            HaigoHyo_Tab.detail.resizeTable(0);
        }

        //remove all siki area
        element.find(".col-sample").not(".th-tmpl").find(".siki_keisan, .siki_keisan_head, .flg_keisan").remove();


        var element = HaigoHyo_Tab.detail.elementShisaku,
            chui = element.find(".no_chui"), $control;

        $.each(chui, function () {
            $control = element.find(this);
            $control.children().remove();
        })

        page.values.masterData.no_chui_max = 0;
    }

    /**
    *  配合表タブの工程属性コンボボックスに空白の場合、
    * 「配合表の工程名を選択してください。(AP0028)」メッセージ表示する
    */
    HaigoHyo_Tab.detail.checkBlankKotei = function (isError, isCheckScal) {
        var deferred = $.Deferred();

        var element = HaigoHyo_Tab.detail.elementHaigo,
            kotei = element.find("select"),
            koteiBlank = [],
            isBlank = false;

        if (!kotei.length) {
            return;
        }

        $.each(kotei, function (index, item) {
            var thisItem = element.find(item),
                val = thisItem.val();

            if (thisItem.closest("tbody").hasClass("item-tmpl")) {
                return true;
            }
            if (!val || val == "") {
                koteiBlank.push(thisItem);
                isBlank = true;
            }
        })

        if (isBlank) {
            if (isError) {
                $.each(koteiBlank, function () {
                    App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0028, { strName: "配合表の工程名" }), $(this)).show();
                })
            } else {
                if (isCheckScal) {
                    deferred.reject();
                    return deferred.promise();
                }
                App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0028, { strName: "配合表の工程名" })).remove();
                App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0028, { strName: "配合表の工程名" })).show();
            }
            deferred.reject();
        } else {
            deferred.resolve();
        }

        return deferred.promise();
    }

    /**
    * 原価試算表印刷
    * 合計仕上重量
    */
    HaigoHyo_Tab.detail.checkGokeJuryo = function (checked, ele) {
        var deferred = $.Deferred();

        //その他・加食タイプ
        if (HaigoHyo_Tab.values.pt_kotei == App.settings.app.pt_kotei.sonohokaeki) {

            var element = HaigoHyo_Tab.detail.element,
                itemEl, gokeIsnull = false, gokei;

            $.each(checked, function (index, item) {
                itemEl = element.find("." + item);
                if (!itemEl.length) {
                    return true;
                }

                gokei = itemEl.findP("juryo_shiagari_g").val();

                if (gokei == "" || App.isUndefOrNull(gokei)) {

                    App.ui.page.notifyAlert.message(App.str.format(App.messages.base.required, { name: "仕上がり重量" }), itemEl.findP("juryo_shiagari_g")).show();
                    gokeIsnull = true;
                    //return false;
                }
            })

            if (gokeIsnull) {
                deferred.reject();
            } else {
                deferred.resolve();
            }
        } else {
            deferred.resolve();
        }

        return deferred.promise();
    };

    /**
    * check befor copy
    */
    HaigoHyo_Tab.detail.beforZenCopy = function () {

        var element = HaigoHyo_Tab.detail.elementShisaku,
            checkPrint = element.find(".flg_print:checked");

        if (!checkPrint.length) {
            App.ui.page.notifyInfo.message(App.messages.app.AP0029).remove();
            App.ui.page.notifyInfo.message(App.messages.app.AP0029).show(5000);
            return false;
        } else {
            App.ui.page.notifyInfo.message(App.messages.app.AP0029).remove();
        }

        //create name sample copy all
        var nm_sample = [], data,
            index;
        $.each(checkPrint, function () {
            index = element.find(this).closest("th").attr("index-col");
            if (!index) {
                return true;
            }
            data = element.find(".sample-" + index).form().data();

            nm_sample.push(App.str.format("【{0}】", data.nm_sample != null ? data.nm_sample : ""));
        })
        //end create name sample copy all

        var options = {
            text: App.str.format(App.messages.app.AP0026, { nm_sample: nm_sample.join("、") })
        };

        HaigoHyo_Tab.confirmDialog(options).then(function () {

            App.ui.loading.show();
            setTimeout(function () {
                // del all sample not select copy
                HaigoHyo_Tab.detail.delAllColNotCopy();

                //remove all siki area
                //HaigoHyo_Tab.detail.element.find(".col-sample").not(".th-tmpl").find(".siki_keisan, .siki_keisan_head, .flg_keisan").remove();

                //フラグ変更画面
                page.values.isChange = true;
            })
        })
    }

    /**
    * del all sample not select copy
    */
    HaigoHyo_Tab.detail.delAllColNotCopy = function () {
        var element = HaigoHyo_Tab.detail.element,
            dt_head = HaigoHyo_Tab.detail.elementShisaku;


        var eleItem, eleCol, td, iColDel,
            table = element.find(".dt-container .flow-container table"),
            flg_print = dt_head.find('.flg_print');

        if (!flg_print.length) {
            App.ui.loading.close();
            return;
        }

        $.each(flg_print, function (index, item) {
            eleItem = element.find(item);
            th = eleItem.closest("th");

            if (th.hasClass("th-tmpl") || !eleItem.length || eleItem[0].checked) {
                return true;
            }

            iColDel = dt_head.find('.flg_print').index(eleItem);

            var ipQuantity, dataProp, flg_delSample = false, itemSample;
            jQuery('tr', table).each(function () {
                cols = jQuery(this).children('th, td');
                htmlSrc = cols.eq(iColDel);


                ipQuantity = htmlSrc.find(".quantity");
                dataProp = ipQuantity.attr("data-prop");

                if (dataProp && dataProp.indexOf("quantity_") >= 0) {

                    id = ipQuantity.attr("data-key");
                    entity = HaigoHyo_Tab.detail.dataShisakuList.entry(id);
                    HaigoHyo_Tab.detail.dataShisakuList.remove(entity);

                    if (flg_delSample == false) {
                        id = ipQuantity.closest("td").attr("data-key");
                        if (!App.isUndefOrNull(id)) {
                            flg_delSample = true;
                            entity = HaigoHyo_Tab.detail.dataShisaku.entry(id);
                            itemSample = entity;
                            HaigoHyo_Tab.detail.dataShisaku.remove(entity);

                            //remove item key in list
                            if (HaigoHyo_Tab.dataKeySample["sample-" + entity.seq_shisaku]) {
                                delete HaigoHyo_Tab.dataKeySample["sample-" + entity.seq_shisaku];
                            }
                        }
                    }
                };

                htmlSrc.detach();
            });

            //テーブルでサイズ変更
            HaigoHyo_Tab.detail.resizeTable(0);

            //del col in 基本情報,原価試算
            if (App.isFunc(HaigoHyo_Tab.delSample)) {
                HaigoHyo_Tab.delSample(itemSample);
            }
        });

        //印刷FG解除	
        //全コピー実行後は、全列の印刷FGの解除を行う
        element.find(".flg_print_all").prop("checked", false).change();
        HaigoHyo_Tab.detail.elementHaigo.find(".item-kotei").prop("checked", false);
        HaigoHyo_Tab.detail.elementHaigo.find(".item-genryo").prop("checked", false);

        //item-kotei
        //reset uncheck for all manual
        HaigoHyo_Tab.detail.elementShisakuHead.find(":input").prop("checked", false);

        //input
        element.find(":input").prop("disabled", false);

        //油含有率
        element.find(".flg_shisanIraiSample, .irai-genka").text("");

        // renew data haigo 
        HaigoHyo_Tab.detail.renewDataHaigo();
    }

    /**
    * For each current display kotei
    */
    HaigoHyo_Tab.detail.eachKotei = function (callback) {

        var eKotei = HaigoHyo_Tab.detail.element.find("tbody.new").findP("zoku_kotei");
        $.each(eKotei, function (ind, e) {
            e = $(e);
            if (App.isFunc(callback)) {
                callback(e);
            }
        });
    }

    /**
    * renew data haigo 
    */
    HaigoHyo_Tab.detail.renewDataHaigo = function () {
        var element = HaigoHyo_Tab.detail.elementHaigo,
            dataHaigo = $.extend(true, {}, HaigoHyo_Tab.detail.dataHaigo.entries),
            dataHaigoDelete = [];

        $.each(dataHaigo, function (index, item) {
            if (item.state == App.ui.page.dataSet.status.Deleted) {
                dataHaigoDelete.push(item);
            } else {
                item.state = App.ui.page.dataSet.status.Added;
            }
        })

        if (dataHaigoDelete.length) {
            $.each(dataHaigoDelete, function (index, item) {
                delete dataHaigo[item.__id];
            })
        }

        HaigoHyo_Tab.detail.dataHaigo = App.ui.page.dataSet();
        HaigoHyo_Tab.detail.dataHaigo.entries = dataHaigo;

        //remove disabled input
        element.find(".shisanIraied").prop("disabled", false);
        element.find(".flg_shisanIrai_kotei, .flg_shisanIrai").text("");

        //renew data shisaku 
        HaigoHyo_Tab.detail.renewDataShisaku();
    }

    /**
    * renew data shisaku 
    */
    HaigoHyo_Tab.detail.renewDataShisaku = function () {

        var dataShisaku = $.extend(true, {}, HaigoHyo_Tab.detail.dataShisaku.entries),
            dataShisakuDelete = [];

        $.each(dataShisaku, function (index, item) {
            if (item.state == App.ui.page.dataSet.status.Deleted) {
                dataShisakuDelete.push(item);
            } else {
                item.state = App.ui.page.dataSet.status.Added;
            }
        })

        if (dataShisakuDelete.length) {
            $.each(dataShisakuDelete, function (index, item) {
                delete dataShisaku[item.__id];
            })
        }

        HaigoHyo_Tab.detail.dataShisaku = App.ui.page.dataSet();
        HaigoHyo_Tab.detail.dataShisaku.entries = dataShisaku;


        //renew se_shisaku
        HaigoHyo_Tab.detail.options.maxSeqShisaku = 0;

        HaigoHyo_Tab.detail.dataShisaku.findAll(function (item, entity) {
            HaigoHyo_Tab.detail.options.maxSeqShisaku = item.seq_shisaku > HaigoHyo_Tab.detail.options.maxSeqShisaku ? item.seq_shisaku : HaigoHyo_Tab.detail.options.maxSeqShisaku;
            return entity.state == App.ui.page.dataSet.status.Deleted;
        });

        //renew data shisaku list
        HaigoHyo_Tab.detail.renewDataCyuui();
    }

    /**
    * renew data cyuui
    */
    HaigoHyo_Tab.detail.renewDataCyuui = function () {

        var dataChui = $.extend(true, {}, HaigoHyo_Tab.detail.dataChui.entries),
            dataChuiDelete = [];

        $.each(dataChui, function (index, item) {
            if (item.state == App.ui.page.dataSet.status.Deleted) {
                dataChuiDelete.push(item);
            } else {
                item.state = App.ui.page.dataSet.status.Added;
            }
        })

        if (dataChuiDelete.length) {
            $.each(dataChuiDelete, function (index, item) {
                delete dataChui[item.__id];
            })
        }

        HaigoHyo_Tab.detail.dataChui = App.ui.page.dataSet();
        HaigoHyo_Tab.detail.dataChui.entries = dataChui;

        //renew data shisaku list
        HaigoHyo_Tab.detail.renewDataShisakuList();
    }

    /**
    * renew data shisaku list
    */
    HaigoHyo_Tab.detail.renewDataShisakuList = function () {

        var dataShisakuList = $.extend(true, {}, HaigoHyo_Tab.detail.dataShisakuList.entries),
            dataShisakuListDelete = [];

        $.each(dataShisakuList, function (index, item) {
            if (item.state == App.ui.page.dataSet.status.Deleted) {
                dataShisakuListDelete.push(item);
            } else {
                item.state = App.ui.page.dataSet.status.Added;
            }
        })

        if (dataShisakuListDelete.length) {
            $.each(dataShisakuListDelete, function (index, item) {
                delete dataShisakuList[item.__id];
            })
        }

        HaigoHyo_Tab.detail.dataShisakuList = App.ui.page.dataSet();
        HaigoHyo_Tab.detail.dataShisakuList.entries = dataShisakuList;

        if (App.isFunc(HaigoHyo_Tab.zenkopiPage)) {
            HaigoHyo_Tab.zenkopiPage();
        } else {
            App.ui.loading.close();
        }

        // Control display tani
        HaigoHyo_Tab.detail.eachKotei(function (eKotei) {
            HaigoHyo_Tab.detail.controlGenryoTani(eKotei);
        });
    }

    /**
    * event change combo 小数指定 on kihonjohyo tab
    */
    HaigoHyo_Tab.changeShosuShitei = function (shitei, isRecalcu) {
        shitei = shitei != null ? shitei : 0;
        //
        var element = HaigoHyo_Tab.element,
            elementHaigo = HaigoHyo_Tab.detail.elementHaigo,
            items = elementHaigo.find(".row-genryo");
        //lenght = items.length,
        //indexSet = 1;

        var rowHaigo, cd_genryo, quality, total = {};
        $.each(items, function (index, item) {

            rowHaigo = elementHaigo.find(item).closest("tbody");
            cd_genryo = rowHaigo.findP("cd_genryo").val();

            ////コンメントコードーらか何もしません
            //if (cd_genryo == App.settings.app.commentCode) {
            //    return true;
            //}

            HaigoHyo_Tab.detail.dataTable.dataTable("getRow", rowHaigo, function (rows) {

                quality = rows.element.find(".change_keta_shosu");

                var index = 0, cell, entity;
                while (quality[index]) {
                    cell = rows.element.find(quality[index]);
                    id = cell.attr("data-key");

                    if (App.isUndefOrNull(id)) {
                        index += 1;
                        continue;
                    }

                    entity = HaigoHyo_Tab.detail.dataShisakuList.entry(id);
                    var formatNumber = page.detail.formatNumber(entity.quantity, shitei);

                    entity.quantity = formatNumber.forData;
                    HaigoHyo_Tab.detail.dataShisakuList.update(entity);

                    cell.val(formatNumber.forDisp);
                    cell.attr("data-number-format-toFixed", Number(shitei));

                    //工程（kg）,合計重量（kg）
                    //if (!total[entity.seq_shisaku]) {
                    //    total[entity.seq_shisaku] = {};
                    //}

                    ////工程（kg）
                    //if (!total[entity.seq_shisaku]["total_kotei" + entity.cd_kotei]) {

                    //    total[entity.seq_shisaku]["total_kotei" + entity.cd_kotei] = Number(formatNumber.forData);

                    //} else {
                    //    total[entity.seq_shisaku]["total_kotei" + entity.cd_kotei] = new BigNumber(stringNumbers(total[entity.seq_shisaku]["total_kotei" + entity.cd_kotei])).plus(stringNumbers(Number(formatNumber.forData))).toNumber();

                    //}

                    ////合計重量（kg）
                    //if (!total[entity.seq_shisaku]["total_weight"]) {

                    //    total[entity.seq_shisaku]["total_weight"] = Number(formatNumber.forData);
                    //} else {

                    //    total[entity.seq_shisaku]["total_weight"] = new BigNumber(stringNumbers(total[entity.seq_shisaku]["total_weight"])).plus(stringNumbers(Number(formatNumber.forData))).toNumber();
                    //}

                    index += 1;
                }
            });
        });

        HaigoHyo_Tab.detail.calculateTotal("ChangeSukeita");


        //工程（kg）,合計重量（kg）
        //$.each(total, function (id, item) {
        //    var data = {};
        //    $.each(item, function (pro, val) {
        //        data[pro] = page.detail.formatNumber(val, shitei, 0).forDisp;
        //    });

        //    // update total
        //    HaigoHyo_Tab.detail.element.find(".sample-" + id).find(".item_total_cacu").closest("td").form().bind(data);
        //})

        //if (App.isFunc(HaigoHyo_Tab.recalculation) && isRecalcu) {
        //    //・原価試算タブ．原料費の再計算を行う
        //    //HaigoHyo_Tab.recalculation("tokuseichi_tab", "量");
        //}
    };

    /**
    * 工程パターンの変更イベント
    */
    HaigoHyo_Tab.changeKoteiPatan = function (val, renewKomoku) {
        var zoku_kotei = HaigoHyo_Tab.element.findP("zoku_kotei");

        //zoku_kotei.children().remove();

        //switch (val) {
        //    case App.settings.app.pt_kotei.chomieki_1://調味料1液タイプ

        //        App.ui.appendOptions(
        //            zoku_kotei,
        //            "cd_literal",
        //            "nm_literal",
        //            jQuery.grep(page.values.masterData.KoteiZokusei, function (n, i) {
        //                //殺菌調味液
        //                //１液充填
        //                return (n.cd_literal == App.settings.app.zoku_kotei.sakkinchomi_eki || n.cd_literal == App.settings.app.zoku_kotei.eki_juten1);
        //            }),
        //            true
        //        );
        //        break;

        //    case App.settings.app.pt_kotei.chomieki_2://調味料2液タイプ

        //        App.ui.appendOptions(
        //            zoku_kotei,
        //            "cd_literal",
        //            "nm_literal",
        //            jQuery.grep(page.values.masterData.KoteiZokusei, function (n, i) {
        //                //殺菌調味液
        //                //２液油相
        //                //２液水相
        //                return (n.cd_literal == App.settings.app.zoku_kotei.sakkinchomi_eki || n.cd_literal == App.settings.app.zoku_kotei.eki_yu_sho2 || n.cd_literal == App.settings.app.zoku_kotei.eki_suiso2);
        //            }),
        //            true
        //        );
        //        break;

        //    case App.settings.app.pt_kotei.sonohokaeki://その他・加食タイプ

        //        App.ui.appendOptions(
        //            zoku_kotei,
        //            "cd_literal",
        //            "nm_literal",
        //            jQuery.grep(page.values.masterData.KoteiZokusei, function (n, i) {
        //                //ソース
        //                //別充填具材
        //                return (n.cd_literal == App.settings.app.zoku_kotei.Sosu || n.cd_literal == App.settings.app.zoku_kotei.betsu_juten_guzai);
        //            }),
        //            true
        //        );
        //        break;

        //    default:
        //        break;
        //}

        if (renewKomoku && zoku_kotei.length) {
            var element = HaigoHyo_Tab.detail.element,
                koteiSelect;

            $.each(zoku_kotei, function () {
                koteiSelect = element.find(this)
                if (koteiSelect.closest("tbody").hasClass("item-tmpl")) {
                    return true;
                }

                rowSelect = koteiSelect.closest("tbody");
                HaigoHyo_Tab.detail.dataTable.dataTable("getRow", rowSelect, function (rows) {
                    rowSelect = rows;
                });

                //koteiSelect.change();
                HaigoHyo_Tab.detail.updateHaigoChildren(koteiSelect, rowSelect, false);
            })

            //if (App.isFunc(HaigoHyo_Tab.recalculation)) {
            //    HaigoHyo_Tab.recalculation("tokuseichi_tab", "油含有率");
            //} else {
            //    App.ui.loading.close();
            //}
            App.ui.loading.close();
        }
    };

    /**
     * get qty kanzan in seiho copy mode
     */
    HaigoHyo_Tab.detail.getKanzanSeihoCopyList = function () {
        var res = [];
        if (page.values.modeDisp != App.settings.app.m_shisaku_data.copy) {
            return res;
        }
        HaigoHyo_Tab.detail.dataShisakuList.findAll(function (item, entity) {
            res.push({
                seq_shisaku: item.seq_shisaku,
                cd_kotei: item.cd_kotei,
                seq_kotei: item.seq_kotei,
                quantity_kanzan: item.quantity_kanzan_seiho
            })
            return false;
        });
        return res;
    }

    /**
     * get changeset this tab。
     */
    HaigoHyo_Tab.getChangeSetTab = function (tokuseichiChangeSet, genryoChangeSet) {
        //megre change sample from tokuseichi tab to haigo sample data
        var entity;
        $.each(tokuseichiChangeSet, function (inxex, item) {
            if (HaigoHyo_Tab.dataKeySample[inxex]) {
                entity = HaigoHyo_Tab.detail.dataShisaku.entry(HaigoHyo_Tab.dataKeySample[inxex]);

                entity = $.extend(true, {}, entity, item);

                HaigoHyo_Tab.detail.dataShisaku.update(entity);
            }
        });

        //megre change sample from genka tab to haigo sample data
        var entity;
        $.each(genryoChangeSet.tr_shisaku, function (inxex, item) {
            if (HaigoHyo_Tab.dataKeySample[inxex]) {
                entity = HaigoHyo_Tab.detail.dataShisaku.entry(HaigoHyo_Tab.dataKeySample[inxex]);

                entity = $.extend(true, {}, entity, item);

                HaigoHyo_Tab.detail.dataShisaku.update(entity);
            }
        });

        return data = {
            tr_haigo: HaigoHyo_Tab.detail.dataHaigo.getChangeSet(),
            tr_shisaku: HaigoHyo_Tab.detail.dataShisaku.getChangeSet(),
            tr_shisaku_list: HaigoHyo_Tab.detail.dataShisakuList.getChangeSet(),
            tr_cyuui: HaigoHyo_Tab.detail.dataChui.getChangeSet(),
            kanzanSeiho: HaigoHyo_Tab.detail.getKanzanSeihoCopyList()
        };
    };

    /**
    * mode input。
    *create new data changeSet
    */
    HaigoHyo_Tab.detail.initInput = function () {
        //unit normal data
        var data = {
            haigoItem: {},
            sampleListOriginal: {},
            sampleItem: {},
            genryokojo: {}
        };

        HaigoHyo_Tab.detail.bindHaigo(data, true);

        //add blank kotei
        HaigoHyo_Tab.detail.addBlankKotei();

        //add total row
        HaigoHyo_Tab.detail.totalInit();

        //init inset total row mode input
        HaigoHyo_Tab.detail.controlDispTotalRow();

        //create sample 1
        HaigoHyo_Tab.detail.options.maxSeqShisaku = 0;
        HaigoHyo_Tab.detail.addNewTrialManual();
    };

    /**
    * init total row。
    */
    HaigoHyo_Tab.detail.totalInit = function () {
        var rowid = 0,
            addRowTotal = function (data) {
                HaigoHyo_Tab.detail.dataTable.dataTable("addRow", function (row) {
                    row.form().bind(data);
                    //create total
                    row.addClass("item_total_" + data.classIp);
                    row.find(".quantity").attr("data-prop", data.prop);

                    HaigoHyo_Tab.detail.options.controlDisplayItem(row, "total", (data.prop == "juryo_shiagari_g"));//data.prop == "total_finish_weight_tml" || 

                    if (data.flgInput) {
                        row.find(".quantity").removeAttr("readonly");
                    }

                    if (data.classUpdate) {
                        row.find(".quantity").addClass(data.classUpdate);
                    }

                    //小数指定
                    //if (data.classIp == "finish_weight_tml" || data.classIp == "finish_weight") {
                    //    row.find(".quantity").attr("data-number-format-toFixed", Number(page.values.keta_shosu));
                    //}

                    //if (data.prop == "total_finish_weight_tml") {
                    //    row.find(".quantity").attr("maxlength", 15).attr("data-number-format-toFixed", 3);
                    //}

                    if (data.prop == "juryo_shiagari_g") {
                        row.find(".quantity").attr("maxlength", 15).removeClass("number-kirisu");
                    }

                    var roundPosition = HaigoHyo_Tab.detail.getDisplayPosition(data.prop);
                    if (!App.isUndefOrNullOrStrEmpty(roundPosition)) {
                        row.find(".quantity").attr("maxlength", 15).attr("data-number-format-toFixed", roundPosition);
                    }

                    if (data.maxlength) {
                        row.find(".quantity").attr("maxlength", data.maxlength);
                    }

                    return row;
                });
            };

        //
        while (rowid <= 16) {
            //switch (rowid) {
            //    case 0:
            //        addRowTotal({ nm_genryo: "1工程（ｇ）", classIp: "kotei_tml", prop: "total_kotei_tml" });
            //        break;
            //    case 1:
            //        addRowTotal({ nm_genryo: "合計重量（ｇ）", classIp: "weight", prop: "total_weight", classUpdate: "item_total_cacu" });
            //        break;
            //    case 2:
            //        addRowTotal({ nm_genryo: "1工程仕上重量（ｇ）", classIp: "finish_weight_tml", prop: "total_finish_weight_tml" });
            //        break;
            //    case 3:
            //        addRowTotal({ nm_genryo: "合計仕上重量（ｇ）（※）", classIp: "finish_weight", prop: "juryo_shiagari_g", flgInput: true });
            //        break;
            //    case 4:
            //        addRowTotal({ nm_genryo: "原料費（ｋｇ）", classIp: "raw_cost_kg", prop: "genryohi" });
            //        break;
            //    case 5:
            //        addRowTotal({ nm_genryo: "原料費（１個）", classIp: "raw_cost_piece", prop: "genryohi1" });
            //        break;
            //    case 6:
            //        addRowTotal({ nm_genryo: "総酸（％）", classIp: "acid", prop: "ritu_sousan" });
            //        break;
            //    case 7:
            //        addRowTotal({ nm_genryo: "食塩（％）", classIp: "salt", prop: "ritu_shokuen" });
            //        break;
            //    case 8:
            //        addRowTotal({ nm_genryo: "水相中酸度", classIp: "acidity_h2o", prop: "sando_suiso" });
            //        break;
            //    case 9:
            //        addRowTotal({ nm_genryo: "水相中食塩", classIp: "salt_h2o", prop: "shokuen_suiso" });
            //        break;
            //    case 10:
            //        addRowTotal({ nm_genryo: "水相中酢酸", classIp: "acetic_acid_h2o", prop: "sakusan_suiso" });
            //        rowid = 10;
            //        break;
            //    default:
            //        break;
            //}
            switch (rowid) {
                case 0:
                    addRowTotal({ nm_genryo: "１工程（kg）", classIp: "kotei_tml", prop: "total_kotei_tml" });
                    break;
                case 1:
                    addRowTotal({ nm_genryo: "合計重量（kg）", classIp: "weight", prop: "total_weight", classUpdate: "item_total_cacu" });
                    break;
                case 2:
                    addRowTotal({ nm_genryo: "１工程仕上重量（kg）", classIp: "finish_weight_tml", flgInput: true, prop: "total_finish_weight_tml", classUpdate: "item_total_cacu" });
                    break;
                case 3:
                    addRowTotal({ nm_genryo: "合計仕上重量（kg）", classIp: "finish_weight", prop: "juryo_shiagari_g", classUpdate: "item_total_cacu u_shisaku" });
                    break;
                case 4:
                    addRowTotal({ nm_genryo: "レシピコスト（円／kg）", classIp: "kg_costrecipe", prop: "kg_costrecipe", classUpdate: "item_total_cacu u_shisaku" });
                    break;
                case 5:
                    addRowTotal({ nm_genryo: "レシピコスト（円／個）", classIp: "ko_costrecipe", prop: "ko_costrecipe", classUpdate: "item_total_cacu u_shisaku" });
                    break;
                case 6:
                    addRowTotal({ nm_genryo: "FC", classIp: "su_fc", prop: "su_fc", flgInput: true, classUpdate: "u_shisaku", maxlength: 5 });
                    break;
                case 7:
                    addRowTotal({ nm_genryo: "Brix(%)", classIp: "su_brix", prop: "su_brix", flgInput: true, classUpdate: "u_shisaku", maxlength: 4 });
                    break;
                case 8:
                    addRowTotal({ nm_genryo: "pH", classIp: "ph", prop: "su_ph", flgInput: true, classUpdate: "u_shisaku", maxlength: 5 });
                    break;
                case 9:
                    addRowTotal({ nm_genryo: "CA(%)", classIp: "ca", prop: "su_ca", flgInput: true, classUpdate: "u_shisaku", maxlength: 5 });
                    break;
                case 10:
                    addRowTotal({ nm_genryo: "投入全果肉量", classIp: "put_all", prop: "put_all", classUpdate: "item_total_cacu" });
                    break;
                case 11:
                    addRowTotal({ nm_genryo: "必要可溶性固形物", classIp: "essen_solid", prop: "essen_solid", classUpdate: "item_total_cacu" });
                    break;
                case 12:
                    addRowTotal({ nm_genryo: "投入可溶性固形物", classIp: "solid", prop: "solid", classUpdate: "item_total_cacu" });
                    break;
                case 13:
                    addRowTotal({ nm_genryo: "不足可溶性固形物", classIp: "insuff_solid", prop: "insuff_solid", classUpdate: "item_total_cacu" });
                    break;
                case 14:
                    addRowTotal({ nm_genryo: "濃縮率", classIp: "concent_rate", prop: "concent_rate", classUpdate: "item_total_cacu" });
                    break;
                case 15:
                    addRowTotal({ nm_genryo: "ペクチンガム濃度", classIp: "gum_concent", prop: "gum_concent", classUpdate: "item_total_cacu" });
                    break;
                case 16:
                    addRowTotal({ nm_genryo: "推定甘味度", classIp: "estimate_sweet", prop: "estimate_sweet", classUpdate: "item_total_cacu" });
                    break;
                default:
                    break;
            }
            rowid += 1;
        }
    }

    /**
     * ダイアログコントロールへのイベントの紐づけを行います。
     */
    HaigoHyo_Tab.initializeControlEvent = function () {
        var element = HaigoHyo_Tab.element;

        //TODO: 画面全体で利用するコントロールのイベントの紐づけ処理をここに記述します。

        ////試作列コピー
        element.on("click", ".shisaku-retu-tuika-dialog", HaigoHyo_Tab.detail.showShisakuRetuTuikaDialog);

        //
        //工程ボタングループ
        //
        //工程挿入
        element.on("click", ".add-kotei", HaigoHyo_Tab.detail.beforAddKotei);
        //工程移動▲
        element.on("click", ".move-up-kotei", HaigoHyo_Tab.detail.moveUpKotei);
        //工程移動▼
        element.on("click", ".move-down-kotei", HaigoHyo_Tab.detail.moveDownKotei);
        //工程削除
        element.on("click", ".del-kotei", HaigoHyo_Tab.detail.delKotei);

        //
        //原料ボタングループ
        //
        //原料挿入
        element.on("click", ".add-genryo", HaigoHyo_Tab.detail.addGenryo);
        //原料移動▲
        element.on("click", ".move-up-genryo", HaigoHyo_Tab.detail.moveUpGenryo);
        //原料移動▼
        element.on("click", ".move-down-genryo", HaigoHyo_Tab.detail.moveDownGenryo);
        //原料削除
        element.on("click", ".del-genryo", HaigoHyo_Tab.detail.delGenryo);

        //
        //試作列ボタングループ
        //
        //試作列追加
        element.on("click", ".add-trial-manual", function () { HaigoHyo_Tab.detail.beforAddNewTrialManual() });
        //試作列削除
        element.on("click", ".del-trial-manual", HaigoHyo_Tab.detail.delTrialManual);

        //試作列移動←
        element.on("click", ".move-column-to-left", HaigoHyo_Tab.detail.moveColumnToLeft);
        //試作列移動→
        element.on("click", ".move-column-to-right", HaigoHyo_Tab.detail.moveColumnToRight);
    };

    /**
    * init data befor add new sample col
    */
    HaigoHyo_Tab.detail.beforAddNewTrialManual = function (dataCopy) {
        var deferred = $.Deferred();
        var element = HaigoHyo_Tab.detail.element,
            dt_head = HaigoHyo_Tab.detail.elementShisakuHead,
            head_check = dt_head.find('input:checked');

        //break when manual not selected
        if (head_check.length == 0) {
            App.ui.loading.close();
            deferred.reject();
            return deferred;
        }

        App.ui.loading.show();
        setTimeout(function () {

            //フラグ変更画面
            page.values.isChange = true;

            var isEndCol = false,
                isNewData = true;

            HaigoHyo_Tab.detail.options.maxSeqShisaku += 1;
            var dataSample = {
                cd_shain: page.values.cd_shain,
                nen: page.values.nen,
                no_oi: page.values.no_oi,
                gokei: "0.000",//合計量（1本：g）（容量ｘ比重）
                hiju: "0.000",//比重
                reberu: "0.00",//レベル量（ｇ内容量ｘ入数）
                hizyubudomari: "0.00",//レベル量（ｇ内容量ｘ入数）
                zyusui: "0.00",//充填量水相（g）（※）
                zyuabura: "0.00",//充填量油相（g）（※）
                hiju: 1,//	製品比重
                flg_print: 0,//印刷Flg
                flg_auto: 0,//自動計算Flg
                //flg_sousan: 0,//総酸-出力Flg
                //ritu_sousan: "0.00",
                //flg_shokuen: 0,//食塩-出力Flg
                //ritu_shokuen: "0.00",
                //flg_sando_suiso: 0,//水相中酸度-出力Flg
                //flg_shokuen_suiso: 0,//水相中食塩-出力Flg
                //flg_sakusan_suiso: 0,//水相中酢酸-出力Flg
                //flg_sakusan_suiso: 0,//水相中酢酸-出力Flg
                msg_suiso: "0.00",
                //sando_suiso: "0.00",
                flg_toudo: 0,//糖度-出力Flg
                flg_nendo: 0,//粘度-出力Flg
                flg_ondo: 0,//温度-出力Flg
                flg_no_rotor: 0,//粘度ローターNo-Flg
                flg_speed: 0,//粘度スピード-Flg
                flg_nendo_cream: 0,//充填前（クリーム）粘度-Flg
                flg_ondo_cream: 0,//充填前（クリーム）粘度測定温度-Flg
                flg_no_rotor_cream: 0,//充填前（クリーム）粘度ローターNo-Flg
                flg_speed_cream: 0,//充填前（クリーム）粘度スピード-Flg
                flg_ph: 0,//PH-出力Flg
                flg_sousan_bunseki: 0,//総酸：分析-出力Flg
                flg_shokuen_bunseki: 0,//食塩：分析-出力Flg
                //shokuen_suiso: "0.00",
                flg_sando_bunseki_suiso: 0,//水相中総酸：分析-出力Flg
                //sakusan_suiso: "0.00",
                flg_shokuen_bunseki_suiso: 0,//水相中食塩：分析-出力Flg
                flg_hiju: 0,//比重-出力Flg
                flg_memo: 0,//作成メモ-出力Flg
                flg_hyoka: 0,//評価-出力Flg
                flg_hiju_sui: 0,//水相比重-出力Flg
                flg_jikkoSakusanNodo: 0,//実効酢酸濃度Flg
                jikkoSakusanNodo: "0.00",
                flg_jikkoHikairiSakusanSando: 0,//水相中非解離酢酸酸度Flg
                jikkoHikairiSakusanSando: "0.00",
                flg_msg_suiso: 0,//水相中ＭＳＧFlg
                flg_rank_biseibutsu: 0,//微生物ランクFlg
                flg_haigo_kyodo: 0,//配合強度Flg
                flg_oilmustard: 0,//製品オイルマスタード含有量Flg
                oilmustard: "0",
                flg_Brix: 0,//BrixFlg
                flg_AW: 0,//AWFlg
                flg_free1: 0,//フリー①-出力Flg
                flg_free2: 0,//フリー②-出力Flg
                flg_free3: 0,//フリー③-出力Flg
                flg_free4: 0,//フリー④-出力Flg
                flg_free5: 0,//フリー⑤-出力Flg
                flg_free6: 0,//フリー⑥-出力Flg
                flg_free7: 0,//フリー⑦-出力Flg
                flg_free8: 0,//フリー⑧-出力Flg
                flg_free9: 0,//フリー⑨-出力Flg
                flg_free10: 0,//フリー⑩-出力Flg
                flg_free11: 0,//フリー⑪-出力Flg
                flg_free12: 0,//フリー⑫-出力Flg
                flg_free13: 0,//フリー⑬-出力Flg
                flg_free14: 0,//フリー⑭-出力Flg
                flg_free15: 0,//フリー⑮-出力Flg
                flg_free16: 0,//フリー⑯-出力Flg
                flg_free17: 0,//フリー⑰-出力Flg
                flg_free18: 0,//フリー⑱-出力Flg
                flg_free19: 0,//フリー⑲-出力Flg
                flg_free20: 0,//フリー⑳-出力Flg
                seq_shisaku: HaigoHyo_Tab.detail.options.maxSeqShisaku,
                sort_shisaku: HaigoHyo_Tab.detail.options.maxSeqShisaku
            };

            //kotei mode edit
            if (page.values.modeDisp == App.settings.app.m_shisaku_data.shosai) {
                dataSample.cd_shain = page.values.cd_shain;
                dataSample.nen = page.values.nen;
                dataSample.no_oi = page.values.no_oi;
            }

            //試作列コピー
            if (dataCopy) {
                //サンプルNo
                dataSample.nm_sample = dataCopy.nm_sample;
                //計算式
                dataSample.siki_keisan = dataCopy.siki_keisan;
                dataSample.siki_keisan_head = dataCopy.siki_keisan;
                //全工程 : 0
                //計算 : 1
                dataSample.flg_keisan = dataCopy.flg_keisan;
                //合計仕上重量（kg）
                //dataSample.juryo_shiagari_g = dataCopy.juryo_shiagari_g;
                // For FC, Brix(%), pH, CA(%)
                dataSample.su_fc = dataCopy.su_fc;
                dataSample.su_brix = dataCopy.su_brix;
                dataSample.su_ph = dataCopy.su_ph;
                dataSample.su_ca = dataCopy.su_ca;
            }

            HaigoHyo_Tab.detail.addNewCol(isEndCol, dataSample, isNewData, dataCopy);
            //HaigoHyo_Tab.detail.caculateTotalSample()

            if (dataCopy) {
                setTimeout(function () {
                    deferred.resolve(HaigoHyo_Tab.detail.options.maxSeqShisaku + 1);
                }, 1)
            } else {
                deferred.resolve();
            }
        }, 1);

        return deferred.promise();
    }

    /**
    * Check if input kotei is [別添]
    */
    HaigoHyo_Tab.detail.isBettenKotei = function (zoku_kotei) {
        return zoku_kotei === App.settings.app.kotei_AOH.Betten;
    }

    /**
    * Get zoku kotei by cd_kotei
    */
    HaigoHyo_Tab.detail.getZokuKoteiByCdKotei = function (cd_kotei) {
        if (!cd_kotei) return null;

        var iKotei = HaigoHyo_Tab.detail.dataKotei.findAll(function (i, e) {
            return i.cd_kotei == cd_kotei && i.state !== App.ui.page.dataSet.status.Deleted;
        });

        if (iKotei && iKotei.length) {
            return iKotei[0].zoku_kotei;
        }
    }

    /**
    * control display row total kotei
    */
    HaigoHyo_Tab.detail.controlDispTotalRow = function () {
        var element = HaigoHyo_Tab.detail.element.find(".dt-container .dt-fix-body .datatable"),
           selectedKotei = element.find(".row-kotei");//get kotei is checked

        if (!selectedKotei.length) {
            App.ui.loading.close();
            return;
        }

        //renew validate total
        HaigoHyo_Tab.detail.options.validationsTotal = {};

        var index = 0,
            insertKotei = element.find(".item_total_kotei_tml"),
            insertKoteiWeight = element.find(".item_total_finish_weight_tml"),
            addNewTotalRow = function (data, dataweight) {
                //add total row kotei
                HaigoHyo_Tab.detail.dataTable.dataTable("insertRow", insertKotei, true, function (row) {
                    row.form().bind(data);

                    row.addClass(data.classIp);
                    row.find(".quantity").attr("data-prop", data.prop).addClass("item_total_cacu");
                    HaigoHyo_Tab.detail.options.controlDisplayItem(row, "total");
                    //renew insert kotei row
                    insertKotei = row;
                    return row;
                });

                //add total weight row kotei
                HaigoHyo_Tab.detail.dataTable.dataTable("insertRow", insertKoteiWeight, true, function (row) {
                    row.form().bind(dataweight);
                    row.addClass(dataweight.classIp);
                    row.find(".quantity").attr("data-prop", dataweight.prop).addClass("item_total_weight_cacu")
                                         .attr("data-number-format-toFixed", dataweight.roundPosition);
                    HaigoHyo_Tab.detail.options.controlDisplayItem(row, "total", false, true);
                    if (HaigoHyo_Tab.detail.isBettenKotei(dataweight.zoku_kotei)) {
                        row.find(".quantity").addClass("betten-kotei");
                    } else {
                        row.find(".quantity").prop("readonly", true);
                    }
                    row.find(".quantity").attr("maxlength", 15);
                    //renew insert kotei weight row
                    insertKoteiWeight = row;

                    //add item validate
                    HaigoHyo_Tab.detail.options.validationsTotal[dataweight.prop] = $.extend(true, {}, HaigoHyo_Tab.values.validationsTotalDefault);
                    HaigoHyo_Tab.detail.options.validationsTotal[dataweight.prop].options.name = dataweight.nm_genryo.replace("（kg）", "");
                    HaigoHyo_Tab.detail.options.validationsTotal[dataweight.prop].messages.pointlength_custom =
                        HaigoHyo_Tab.detail.options.validationsTotal[dataweight.prop].messages.pointlength_custom.replace("工程仕上重量", dataweight.nm_genryo.replace("（kg）", ""));

                    //row.findP("juryo_shiagari_g").attr("data-number-format-toFixed", "null").attr("toFixedMin", "4");
                    return row;
                });
            };

        var updateProperty = function (rowSelect, dataProp) {
            HaigoHyo_Tab.detail.dataTable.dataTable("getRow", rowSelect, function (rows) {
                rows.element.find(".quantity").attr("data-prop", dataProp);
                //小数指定
                if (dataProp.indexOf("total_finish_weight")) {
                    rows.element.find(".quantity").attr("data-number-format-toFixed", 3);
                }
                //rows.element.findP("juryo_shiagari_g").attr("data-number-format-toFixed", "null").attr("toFixedMin", "4");
            });
        }

        //add || edit row total
        while (selectedKotei[index]) {
            var item = element.find(selectedKotei[index]),
                id = item.attr("data-key"),
                entity;

            if (App.isUndefOrNull(id)) {
                index += 1;
                continue;
            }
            index += 1;

            entity = HaigoHyo_Tab.detail.dataKotei.entry(id);

            var exitTotal = element.find(".item_total_" + index);
            if (exitTotal.length) {
                //reset title 工程（kg）
                updateProperty(exitTotal, "total_kotei" + entity.cd_kotei);
                insertKotei = exitTotal;
                exitTotal.findP("nm_genryo").val(index + "工程（kg）");

                //reset title 工程仕上重量（kg）
                var weightRow = element.find(".item_total_finish_weight" + index);
                insertKoteiWeight = weightRow;
                updateProperty(weightRow, "total_finish_weight" + entity.cd_kotei);
                weightRow.findP("nm_genryo").val(index + "工程仕上重量（kg）");
                
                //Recovery 工程仕上重量 data
                var prop = "total_finish_weight" + entity.cd_kotei;
                HaigoHyo_Tab.detail.dataTable.dataTable("getRow", weightRow, function (r) {
                    $.each(r.element.findP(prop), function (i, e) {
                        var tt_wt = $(e);
                        var p = tt_wt.closest("td");
                        if (p.hasClass("th-tmpl")) {
                            return;
                        }
                        var d = HaigoHyo_Tab.detail.dataShisaku.entry(p.attr("data-key"));
                        tt_wt.val(d[prop]);
                        if (entity.zoku_kotei === App.settings.app.kotei_AOH.Betten) {
                            tt_wt.addClass("betten-kotei");
                        } else {
                            tt_wt.removeClass("betten-kotei");
                        }
                    });
                });

                //add item validate
                HaigoHyo_Tab.detail.options.validationsTotal["total_finish_weight" + entity.cd_kotei] = $.extend(true, {}, HaigoHyo_Tab.values.validationsTotalDefault);
                HaigoHyo_Tab.detail.options.validationsTotal["total_finish_weight" + entity.cd_kotei].options.name = index + "工程仕上重量";
                HaigoHyo_Tab.detail.options.validationsTotal["total_finish_weight" + entity.cd_kotei].messages.pointlength_custom = App.str.format(App.messages.base.pointlength, { name: index + "工程仕上重量", param: [10, HaigoHyo_Tab.detail.getRoundPosition("total_finish_weight")] });
                continue;
            }

            addNewTotalRow(
                //data 工程（kg）
                {
                    nm_genryo: index + "工程（kg）",
                    classIp: "item_total_" + index,
                    prop: "total_kotei" + entity.cd_kotei,
                    roundPosition: HaigoHyo_Tab.detail.getDisplayPosition("total_kotei")
                },
                //工程仕上重量（kg）
                {
                    nm_genryo: index + "工程仕上重量（kg）",
                    classIp: "item_total_finish_weight" + index,
                    prop: "total_finish_weight" + entity.cd_kotei,
                    zoku_kotei: entity.zoku_kotei,
                    roundPosition: HaigoHyo_Tab.detail.getDisplayPosition("total_finish_weight")
                }
            );
        }

        //delete all row after end
        index += 1;
        while (element.find(".item_total_" + index).length) {
            //delete 工程（kg）
            var rowDel = element.find(".item_total_" + index).closest("tbody");
            HaigoHyo_Tab.detail.dataTable.dataTable("deleteRow", rowDel, function (row) {
                row.find(".has-error, .error").each(function (i, elem) {
                    //remove mess error
                    App.ui.page.notifyAlert.remove(elem);
                });
            });

            //工程仕上重量（kg）
            rowDel = element.find(".item_total_finish_weight" + index).closest("tbody");
            HaigoHyo_Tab.detail.dataTable.dataTable("deleteRow", rowDel, function (row) {
                row.find(".has-error, .error").each(function (i, elem) {
                    //remove mess error
                    App.ui.page.notifyAlert.remove(elem);
                });
            });
            index += 1;
        }


        //renew validate list
        var validations = $.extend(true, {}, HaigoHyo_Tab.detail.options.validations, HaigoHyo_Tab.detail.options.validationsShisakuList, HaigoHyo_Tab.detail.options.validationsTotal);
        HaigoHyo_Tab.detail.validator = HaigoHyo_Tab.detail.element.validation(HaigoHyo_Tab.createValidator(validations));
    };

    /**
    * prevent dulicate event call
    */
    HaigoHyo_Tab.detail.bfCalculateTotal = function (eventName) {
        HaigoHyo_Tab.values.stackCallCount += 1;
        var stackCallCount = HaigoHyo_Tab.values.stackCallCount;
        setTimeout(function () {
            if (HaigoHyo_Tab.values.stackCallCount == stackCallCount) {
                HaigoHyo_Tab.detail.calculateTotal(eventName);
            }
        }, 100);
    }

    /**
    * recalculate total row。
    */
    HaigoHyo_Tab.detail.calculateTotal = function (eventName) {
        var element = HaigoHyo_Tab.detail.element.find(".dt-container .dt-fix-body .datatable"),
            elementShisakuHead = HaigoHyo_Tab.detail.elementShisakuHead,
            selectedKotei = element.find(".row-kotei"),
            head_check = elementShisakuHead.find('input');

        if (!head_check.length) {
            App.ui.loading.close();
            return;
        };

        //loop sample index
        var indexHead = 0,
            item, indexCol, data;
        while (head_check[indexHead]) {
            //var dataTotal = {};
            item = HaigoHyo_Tab.detail.element.find(head_check[indexHead]).closest("th"),
            indexCol = item.attr("index-col");

            if (!indexCol) {
                indexHead += 1;
                continue;
            }

            //各工程ごとの合計を再計算する
            HaigoHyo_Tab.detail.caculateTotalSample(indexCol, eventName);
            indexHead += 1;
        }

        //各イベント内にて発生
        //if (App.isFunc(HaigoHyo_Tab.recalculation)) {
        //    //特性値
        //    HaigoHyo_Tab.recalculation("tokuseichi_tab", "EventHaigo");
        //} else {
        //    App.ui.loading.close();
        //}
        App.ui.loading.close();
    };

    /**
    * 各工程ごとの合計を再計算する
    */
    HaigoHyo_Tab.detail.caculateTotalSample = function (indexCol, eventName) {
        //var dataTotal = {};
        //dataTotal["total_weight"] = 0;

        //data = HaigoHyo_Tab.detail.element.find(".sample-" + indexCol).form().data();

        ////各工程ごとの合計を再計算する
        //$.each(data, function (pro, val) {
        //    if (pro.indexOf("quantity_") >= 0) {
        //        val = page.detail.checkUndefi(val);
        //        var spl = "total_kotei" + pro.split("_")[1];
        //        dataTotal[spl] = (dataTotal[spl] ? dataTotal[spl] : 0) + val;

        //        //合計重量を再計算する
        //        dataTotal["total_weight"] += val;
        //    }
        //});

        //var defaultNum = "0.00000".substring(0, Number(page.values.keta_shosu) == 0 ? 1 : (2 + Number(page.values.keta_shosu)));
        ////小数指定
        //$.each(dataTotal, function (pro, val) {
        //    dataTotal[pro] = page.detail.formatNumber(val, Number(page.values.keta_shosu), defaultNum).forDisp;
        //})
        var sampleQuantityList = HaigoHyo_Tab.detail.element.find(".sample-" + indexCol);
        //var koteiDataList = HaigoHyo_Tab.detail.element.find(".dt-fix-body tbody.row-kotei.new");
        var koteiIndex = {};
        // Mapping between kotei index and kotei code
        //$.each(koteiDataList, function (i, kotei) {
        //    kotei = $(kotei).form().data();
        //    koteiIndex[kotei.sort_kotei] = kotei.zoku_kotei;
        //});
        var removeComma = function (val) {
            return (val || "").replace(/,/g, "");
        }
        var calcQuantityList = [];
        var calcResSet = {};
        $.each(sampleQuantityList, function (i, elem) {
            elem = $(elem);
            var prop = elem.find("[data-prop]").attr("data-prop"),
                val = elem.form().data()[prop];
            if (prop === "su_fc") {
                calcResSet.FC = removeComma(val);
                return;
            }
            if (prop === "su_brix") {
                calcResSet.Brix = removeComma(val);
                return;
            }
            if (prop && prop.indexOf("total_finish_weight") >= 0) {
                var ind = prop.replace("total_finish_weight", "");
                if (elem.findP(prop).hasClass("betten-kotei")) {
                    calcResSet.wt_betten = removeComma(val);
                    calcResSet.isEnableBetten = elem.findP(prop).is(":enabled");
                }
            }
            if (prop && prop.indexOf("quantity_") >= 0) {
                var genryoID = elem.closest("tbody").attr("data-key");
                var listID = elem.findP(prop).attr("data-key");
                var genryoData = HaigoHyo_Tab.detail.dataHaigo.entry(genryoID);
                var qty = $.extend({}, genryoData);
                qty.quantity = removeComma(val);
                qty.cd_tani_gamen = qty.cd_tani;
                qty.kotei_index = HaigoHyo_Tab.detail.getKoteiIndex(prop);
                qty.listID = listID;
                qty.elem = elem.findP(prop);
                if (!koteiIndex[qty.kotei_index]) {
                    koteiIndex[qty.kotei_index] = qty.zoku_kotei;
                }
                calcQuantityList.push(qty);
            }
        });

        HaigoHyo_Tab.detail.calcTotal(calcQuantityList, calcResSet, eventName);

        // Update quantity_kanzan
        $.each(calcQuantityList, function (i, d) {
            if (!d.listID) return;
            var gListData = HaigoHyo_Tab.detail.dataShisakuList.entry(d.listID);
            if (!gListData) return;
            if (page.values.modeDisp == App.settings.app.m_shisaku_data.copy) {
                gListData.quantity_kanzan_seiho = d.wt_kanzan_seiho;
            }
            if (["FirstLoad"].indexOf(eventName) < 0) {
                gListData.quantity_kanzan = d.wt_kanzan;
            }
            d.elem.data("quantity_p_606", d.r01.getRoundedValue(page.values.keta_shosu));

        });
        // update total
        // Remove Un-Update prop
        var inpList = [];
        $.each(calcResSet, function (p, val) {
            if (p.indexOf("total_finish_weight") >= 0 || p.indexOf("juryo_shiagari_g") >= 0) {
                inpList.push(p);
            }
        })
        if (["FirstLoad"].indexOf(eventName) >= 0) {
            $.each(inpList, function (i, p) {
                delete calcResSet[p];
            });
        }


        var element = HaigoHyo_Tab.detail.element.find(".sample-" + indexCol);
        // Control editable base on zoku_kotei
        $.each(calcResSet.r02, function (ind, kotei) {
            if (kotei.zoku_kotei === App.settings.app.kotei_AOH.Betten) {
                element.findP("total_finish_weight" + kotei.kotei_index).prop("readonly", !HaigoHyo_Tab.isEnableMode()).addClass("betten-kotei");
            } else {
                element.findP("total_finish_weight" + kotei.kotei_index).prop("readonly", true).removeClass("betten-kotei");
                element.findP("total_finish_weight" + kotei.kotei_index).data("hasSauce", calcResSet.r10.hasSauce);
            }
            if (eventName == "ChangeSukeita") {
                element.findP("total_finish_weight" + kotei.kotei_index).attr("data-number-format-tofixed", HaigoHyo_Tab.detail.getKetaSu());
            }
        });

        if (eventName == "ChangeSukeita") {
            element.findP("juryo_shiagari_g").attr("data-number-format-tofixed", HaigoHyo_Tab.detail.getKetaSu());
        }

        var prevFocus = $(document.activeElement.parentNode);

        element.find(".item_total_cacu").closest("td").form().bind(calcResSet);
        element.find(".item_total_weight_cacu").closest("td").form().bind(calcResSet);

        if (HaigoHyo_Tab.detail.values.currentColChanged && prevFocus.attr("index-col") == HaigoHyo_Tab.detail.values.currentColChanged) {
            var focusProp = prevFocus.find("input").attr("data-prop");
            if (focusProp) {
                $.each(calcResSet, function (prop, val) {
                    if (prop == focusProp) {
                        setTimeout(function () {
                            prevFocus.find("input").focus();
                        }, 10)
                        return false;
                    }
                });
            }
        }

        if (["FirstLoad"].indexOf(eventName) < 0) {
            $.each(inpList, function (i, p) {
                var el = element.findP(p);
                if (!el.hasClass("betten-kotei"))
                    el.change();
            });
            element.findP("kg_costrecipe").change();
            element.findP("ko_costrecipe").change();
            if (["試作列コピー"].indexOf(eventName) < 0) {
                HaigoHyo_Tab.detail.elementShisaku.validation().validate({
                    targets: element.findP("su_fc"),
                    filter: HaigoHyo_Tab.detail.validationFilter,
                    state: {
                        tbody: element,
                        isGridValidation: true
                    }
                });
                HaigoHyo_Tab.detail.validator.validate({
                    targets: element.find(".betten-kotei"),
                    state: {
                        tbody: element,
                        isGridValidation: true
                    }
                });
            }
            HaigoHyo_Tab.detail.setOverloadKanzan(calcQuantityList, indexCol);
        }

        // backup calc result
        var dShisaku = HaigoHyo_Tab.detail.getShisakuDataByColInd(indexCol);
        if (dShisaku) {
            dShisaku.calcResSet = calcResSet;
        }
    };

    HaigoHyo_Tab.detail.setOverloadKanzan = function (calcQuantityList, indexCol) {
        delete HaigoHyo_Tab.detail.values.lstOverLoadKanzan[indexCol];
        if (!calcQuantityList) return;
        $.each(calcQuantityList, function (ind, item) {
            if (!item.wt_kanzan) {
                return;
            }
            if ((new BigNumber(item.wt_kanzan)).greaterThan(App.settings.app.maxWtHaigoKanzan)) {
                HaigoHyo_Tab.detail.values.lstOverLoadKanzan[indexCol] = true;
                return false;
            }
        });

    }

    /**
    * Check if any wt_kanzan is overloaded
    */
    HaigoHyo_Tab.detail.checkOverloadKanzan = function () {
        if (Object.keys(HaigoHyo_Tab.detail.values.lstOverLoadKanzan).length) {
            App.ui.page.notifyAlert.message(App.messages.app.AP0202, "AP0202").show();
            return App.async.fail();
        }
        App.ui.page.notifyAlert.remove("AP0202");
        return App.async.success();
    }

    HaigoHyo_Tab.isEnableMode = function () {
        return (page.values.modeDisp !== App.settings.app.m_shisaku_data.etsuran && page.values.modeDisp !== App.settings.app.m_shisaku_data.copy);
    }

    /**
    * Get shisaku by column index
    */
    HaigoHyo_Tab.detail.getShisakuDataByColInd = function (colInd) {
        var eCol = HaigoHyo_Tab.detail.element.find("th.sample-" + colInd).findP("dt_shisaku").closest("th");
        if (!eCol.length) return null;
        var id = eCol.attr("data-key");
        return HaigoHyo_Tab.detail.dataShisaku.entry(id);
    }

    /**
    * Get binding kotei index
    */
    HaigoHyo_Tab.detail.getKoteiIndex = function (prop) {
        if (!prop) return null;
        return prop.split("_")[1];
    }

    HaigoHyo_Tab.detail.getKetaSu = function () {
        return Number(page.values.keta_shosu);
    }

    HaigoHyo_Tab.options.RoundConfig = {
        //工程（kg）- r02
        total_kotei: HaigoHyo_Tab.detail.getKetaSu,
        total_kotei_tml: HaigoHyo_Tab.detail.getKetaSu,
        r02: HaigoHyo_Tab.detail.getKetaSu,
        // R04
        r04: HaigoHyo_Tab.detail.getKetaSu,
        // 工程仕上重量（kg）- r11
        total_finish_weight: HaigoHyo_Tab.detail.getKetaSu,
        total_finish_weight_tml: HaigoHyo_Tab.detail.getKetaSu,
        r11: HaigoHyo_Tab.detail.getKetaSu,
        // 合計仕上重量（kg）- r12
        juryo_shiagari_g: HaigoHyo_Tab.detail.getKetaSu,
        r12: HaigoHyo_Tab.detail.getKetaSu,
        // レシピコスト（円／kg）- r06
        kg_costrecipe: 2,
        r06: 2,
        // レシピコスト（円／個）- r07
        ko_costrecipe: 2,
        r07: 2,
        // FC
        su_fc: 1,
        // Brix(%)
        su_brix: 1,
        // pH
        su_ph: 2,
        // CA(%)
        su_ca: 2,
        // 投入全果肉量 - r09
        put_all: 3,
        r09: 3,
        // r10
        r10: 3,
        // 必要可溶性固形物 - r15
        essen_solid: 3,
        r15: 3,
        // 投入可溶性固形物 - r16
        solid: 3,
        r16: 3,
        // 不足可溶性固形物 - r17
        insuff_solid: 3,
        r17: 3,
        // 濃縮率 - r18
        concent_rate: 1,
        r18: 1,
        // ペクチンガム濃度 - r19
        gum_concent: 0,
        r19: 0,
        // r20
        //r20: 0,
        // 推定甘味度 - r21
        estimate_sweet: 0,
        r21: 0
    }

    HaigoHyo_Tab.options.DisplayConfig = {
        // 工程仕上重量（kg）- r11
        total_finish_weight: HaigoHyo_Tab.detail.getKetaSu,
        total_finish_weight_tml: HaigoHyo_Tab.detail.getKetaSu,
        r11: HaigoHyo_Tab.detail.getKetaSu,
        // 合計仕上重量（kg）- r12
        r12: HaigoHyo_Tab.detail.getKetaSu,
        juryo_shiagari_g: HaigoHyo_Tab.detail.getKetaSu,
        // 合計重量（kg）- r_04
        total_weight: HaigoHyo_Tab.detail.getKetaSu,
        r_04: HaigoHyo_Tab.detail.getKetaSu,
    }

    HaigoHyo_Tab.detail.getRoundPosition = function (prop) {
        if (!prop) return "";
        var res = HaigoHyo_Tab.options.RoundConfig[prop];
        if (App.isFunc(res)) {
            return res();
        }
        return App.isUndefOrNullOrStrEmpty(res) ? "" : res;
    }

    HaigoHyo_Tab.detail.getDisplayPosition = function (prop) {
        if (!prop) return "";
        var res = (!App.isUndefOrNull(HaigoHyo_Tab.options.DisplayConfig[prop])) ? HaigoHyo_Tab.options.DisplayConfig[prop] : HaigoHyo_Tab.options.RoundConfig[prop];
        if (App.isFunc(res)) {
            return res();
        }
        return App.isUndefOrNullOrStrEmpty(res) ? "" : res;
    }

    /**
    * Footer informations
    */
    //cd_tani_master                単位（マスタ）
    //wt_kaniku                     果肉量
    //wt_ireme                      入れ目量
    //wt_1_kan                      1缶重量
    //wt_kayosei_kokeibutsu         可溶性固形物
    //wt_kanmido                    甘味度
    //wt_sato                       砂糖量
    //wt_hiju                       比重
    //kbn_sato                      砂糖・甘味料
    //kbn_gum                       ペクチン・ガム類
    //cd_tani_gamen                 単位(画面) 
    //tanka                         単価
    //budomari                      歩留
    HaigoHyo_Tab.detail.calcTotal = function (data, result, eventName) {
        // Add r01
        HaigoHyo_Tab.detail.calcSubtotalWt_01(data);
        HaigoHyo_Tab.detail.calcWtKanzan(data);
        if (page.values.modeDisp == App.settings.app.m_shisaku_data.copy) {
            HaigoHyo_Tab.detail.calcWtKanzanSeiho(data);
        }
        // Calc r02, r03, r04
        result.r02 = HaigoHyo_Tab.detail.calcEngWt_02(data);
        result.r03 = HaigoHyo_Tab.detail.calcSauceWt_03(result.r02);
        result.r04 = HaigoHyo_Tab.detail.calcTotalWt_04(result.r02);
        // Add r05
        HaigoHyo_Tab.detail.calcRecipeCostSub_05(data);
        // Add r08
        HaigoHyo_Tab.detail.calcPulpWt_08(data);
        // Calc r09, r10, r11, r12
        result.r09 = HaigoHyo_Tab.detail.calcPulpTotalWt_09(data);
        if (App.settings.app.m_shisaku_data.shinki === page.values.modeDisp && eventName === "FirstLoad") {
            result.r09.setToNull();
        }
        result.r10 = HaigoHyo_Tab.detail.calcRaiseWt_10(result.r09.value, result.r02, result.FC);
        result.r11 = HaigoHyo_Tab.detail.calcFinishWt_11(result.r10.value);
        result.r12 = HaigoHyo_Tab.detail.calcFinishTotalWt_12(result.r02, result.r11.value, result.wt_betten, result.r10.hasSauce);
        // Calc r06, r07
        result.r06 = HaigoHyo_Tab.detail.calcRecipeCost_06(data, result.r04.value, result.r10.value, result.r03.value);			//Request #1863
        result.r07 = HaigoHyo_Tab.detail.calcRecipeCost_07(result.r06.value);
        // Add r13, r14
        HaigoHyo_Tab.detail.calcSugarSubWt_13(data);
        HaigoHyo_Tab.detail.calcSolidSubWt_14(data);
        // Calc r15, r16, r17, r18, r19, r20, r21
        result.r15 = HaigoHyo_Tab.detail.calcNecSolid_15(result.r10.value, result.Brix);
        result.r16 = HaigoHyo_Tab.detail.calcInjSolid_16(data);
        result.r17 = HaigoHyo_Tab.detail.calcInsSolid_17(result.r15.value, result.r16.value);
        result.r18 = HaigoHyo_Tab.detail.calcConRate_18(result.r10.value, result.r03.value);
        result.r19 = HaigoHyo_Tab.detail.calcGumCon_19(data, result.r10.value);
        result.r20 = HaigoHyo_Tab.detail.calcSweetSub_20(data);
        result.r21 = HaigoHyo_Tab.detail.calcSweetEst_21(result.r20.value, result.r10.value);

        // Mapping data with Display attr
        // 工程（kg）- r02
        $.each(result.r02, function (i, d) {
            result["total_kotei" + d.kotei_index] = d.r02.getDisplayValue();
        });

        // 合計重量（kg）- r04
        result.total_weight = result.r04.getDisplayValue();
        if (["FirstLoad"].indexOf(eventName) < 0)
        {
            // 工程仕上重量（kg）- r11
            $.each(result.r02, function (i, kotei) {
                if (App.isUndefOrNullOrStrEmpty(kotei.zoku_kotei)) {
                    result["total_finish_weight" + kotei.kotei_index] = "";
                    return;
                }
                if (kotei.zoku_kotei === App.settings.app.kotei_AOH.Betten) {
                    result["total_finish_weight" + kotei.kotei_index] = HaigoHyo_Tab.detail.getCalcResNode("r11", "工程仕上重量").setValue(result.wt_betten).getDisplayValue();
                } else {
                    result["total_finish_weight" + kotei.kotei_index] = result.r11.getDisplayValue();
                }
            });
            // 合計仕上重量（kg）- r12
            result.juryo_shiagari_g = result.r12.getDisplayValue();
        }
        // レシピコスト（円／kg）- r06
        result.kg_costrecipe = result.r06.getDisplayValue();
        // レシピコスト（円／個）- r07
        result.ko_costrecipe = result.r07.getDisplayValue();
        // FC - none
        // Brix(%) - none
        // pH - none
        // CA(%) - none
        // 投入全果肉量 - r09
        result.put_all = result.r09.getDisplayValue();
        // 必要可溶性固形物 - r15
        result.essen_solid = result.r15.getDisplayValue();
        // 投入可溶性固形物 - r16
        result.solid = result.r16.getDisplayValue();
        // 不足可溶性固形物 - r17
        result.insuff_solid = result.r17.getDisplayValue();
        // 濃縮率 - r18
        result.concent_rate = result.r18.getDisplayValue();
        // ペクチンガム濃度 - r19
        result.gum_concent = result.r19.getDisplayValue();
        // 推定甘味度 - r21
        result.estimate_sweet = result.r21.getDisplayValue();

        return result;
    }

    /**
    * Detail calculate result node
    * value: BigNumber, hide flag, rounded value
    */
    HaigoHyo_Tab.detail.getCalcResNode = function (code, name, defVal) {
        var node = {
            code: code,
            name: name,     // Using for debug
            value: App.isUndefOrNull(defVal) ? null : new BigNumber(defVal),
            isHide: false,
            isPassed: false,
            isNull: App.isUndefOrNull(defVal),
            roundPosition: null
        };
        node.setToNull = function () {
            node.value = null;
            node.isNull = true;
            return node;
        }
        node.setValue = function (val, defVal) {
            var pCnv = node.checkNull(val, defVal);
            if (!App.isUndefOrNullOrStrEmpty(val)) {
                node.value = new BigNumber(pCnv);
                node.isNull = false;
            }
            return node;
        }
        // Set hide flag
        node.hide = function () {
            node.isHide = true;
            return node;
        }
        // Set pass flag
        node.pass = function () {
            node.isPassed = true;
            return node;
        }
        // Rounded value
        node.getRoundedValue = function (roundPosition) {
            // Implement is required
            if (node.isNull) return null;
            node.roundPosition = (App.isUndefOrNullOrStrEmpty(roundPosition) ? HaigoHyo_Tab.detail.getRoundPosition(node.code) : roundPosition);
            if (App.isUndefOrNullOrStrEmpty(node.roundPosition)) return node.value.toFixed();
            return node.value.toFixed(node.roundPosition);
        }
        // Display value
        node.getDisplayValue = function (isNullVal) {
            // Implement is required
            var res = node.getRoundedValue();
            if (node.isHide || node.isNull) res = isNullVal;
            var DisplayPosition = HaigoHyo_Tab.detail.getDisplayPosition(node.code);
            return (page.detail.formatNumber(res, DisplayPosition).forDisp || (isNullVal || ""));
        }
        node.checkNull = function (val, defVal) {
            val = App.isUndefOrNull(val) ? defVal : val
            if (App.isUndefOrNullOrStrEmpty(val)) {
                node.setToNull();
            }
            return val;
        }
        node.plus = function (val, defVal) {
            var pCnv = node.checkNull(val, defVal);
            if (!node.isNull) {
                node.value = node.value.plus(pCnv);
            }
            return node;
        }
        node.times = function (val, defVal) {
            var pCnv = node.checkNull(val, defVal);
            if (!node.isNull) {
                node.value = node.value.times(pCnv);
            }
            return node;
        }
        node.timesIgnoreZero = function (val, defVal) {
            var pCnv = node.checkNull(val, defVal);
            if (node.isNull || (new BigNumber(val)).eq(0)) {
                node.setToNull();
                return node;
            }
            if (!node.isNull) {
                node.value = node.value.times(pCnv);
            }
            return node;
        }
        node.div = function (val, defVal) {
            var pCnv = node.checkNull(val, defVal);
            if (!node.isNull) {
                pCnv = new BigNumber(pCnv);
                if (pCnv.equals(0)) {
                    return node.setToNull();
                }
                node.value = node.value.div(pCnv);
            }
            return node;
        }
        node.minus = function (val, defVal) {
            var pCnv = node.checkNull(val, defVal);
            if (!node.isNull) {
                node.value = node.value.minus(pCnv);
            }
            return node;
        }
        return node;
    }

    /**
    * ①配合重量小計
    * Add r01 to data list
    */
    HaigoHyo_Tab.detail.calcSubtotalWt_01 = function (data) {
        if (!data) return;
        // Each detail row of current sample
        $.each(data, function (ind, item) {
            item.r01 = HaigoHyo_Tab.detail.getCalcResNode("r01", "配合重量小計");
            if (App.isUndefOrNullOrStrEmpty(item.quantity)) {
                item.r01.setValue(0);
                item.r01.pass();
                return;
            }
            // 単位（マスタ）
            switch (item.cd_tani_master) {
                // Kg
                case App.settings.app.genryo_tani_AOH.Kg:
                    // 単位(画面) 
                    switch (item.cd_tani_gamen) {
                        // 缶
                        case App.settings.app.genryo_tani_AOH.Can:
                        case App.settings.app.genryo_tani_AOH.Can.toString():
                            // 配合量 x 入れ目量
                            item.r01.setValue(item.quantity).timesIgnoreZero(item.wt_ireme);
                            break;
                        // リットル
                        case App.settings.app.genryo_tani_AOH.Lit:
                        case App.settings.app.genryo_tani_AOH.Lit.toString():
                            // 配合量 x 比重
                            item.r01.setValue(item.quantity).timesIgnoreZero(item.wt_hiju);
                            break;
                        // g
                        case App.settings.app.genryo_tani_AOH.Gram:
                        case App.settings.app.genryo_tani_AOH.Gram.toString(): 
                            // 配合量 / 1000
                            item.r01.setValue(item.quantity).div(1000);
                            break;
                        // Kg
                        case App.settings.app.genryo_tani_AOH.Kg:
                        case App.settings.app.genryo_tani_AOH.Kg.toString(): 
                            // 配合量
                            item.r01.setValue(item.quantity);
                            break;
                    }
                    break;
                // 缶
                case App.settings.app.genryo_tani_AOH.Can: 
                    // 単位(画面) 
                    switch (item.cd_tani_gamen) {
                        // 缶
                        case App.settings.app.genryo_tani_AOH.Can:
                        case App.settings.app.genryo_tani_AOH.Can.toString():
                            // 配合量 x 1缶重量
                            item.r01.setValue(item.quantity).timesIgnoreZero(item.wt_1_kan);
                            break;
                        // g
                        case App.settings.app.genryo_tani_AOH.Gram:
                        case App.settings.app.genryo_tani_AOH.Gram.toString(): 
                            // 配合量 / 1000
                            item.r01.setValue(item.quantity).div(1000);
                            break;
                        // Else
                        default:
                            // 配合量
                            item.r01.setValue(item.quantity);
                            break;
                    }
                    break;
                // Else
                default:
                    // 配合量
                    item.r01.setValue(item.quantity);
                    break;
            }
        });
    }

    /**
    * ①配合重量小計
    * Add wt_kanzan to data list
    */
    HaigoHyo_Tab.detail.calcWtKanzan = function (data) {
        if (!data) return;
        // Each detail row of current sample
        $.each(data, function (ind, item) {
            var wtKanzan = HaigoHyo_Tab.detail.getCalcResNode("wt_kanzan", "配合重量小計", item.r01.getRoundedValue(6));
            if (App.isUndefOrNullOrStrEmpty(item.quantity)) {
                item.wt_kanzan = null;
            } else {
                item.wt_kanzan = wtKanzan.getRoundedValue(6);
            }
        });
    }

    /**
    * ①配合重量小計
    * Add wt_kanzan to data list
    */
    HaigoHyo_Tab.detail.calcWtKanzanSeiho = function (data) {
        if (!data) return;
        // Each detail row of current sample
        $.each(data, function (ind, item) {
            var wt_kz = HaigoHyo_Tab.detail.getCalcResNode("wt_kanzan", "配合重量小計", item.wt_kanzan);
            var wtKanzan = HaigoHyo_Tab.detail.getCalcResNode("wt_kanzan_seiho", "配合重量小計(seiho)", wt_kz.getRoundedValue(3));
            if (App.isUndefOrNullOrStrEmpty(item.quantity)) {
                item.wt_kanzan_seiho = null;
            } else {
                if (item.cd_tani_master == App.settings.app.genryo_tani_AOH.Lit) {
                    wtKanzan.div(item.wt_hiju);
                    if (item.cd_tani_gamen == App.settings.app.genryo_tani_AOH.Gram) {
                        item.wt_kanzan_seiho = wtKanzan.getRoundedValue(6);
                    } else {
                        item.wt_kanzan_seiho = wtKanzan.getRoundedValue(3);
                    }
                } else {
                    item.wt_kanzan_seiho = wtKanzan.getRoundedValue(3);
                }
            }
        });
    }

    /**
    * ②工程重量
    * Return list of BigNumber
    */
    HaigoHyo_Tab.detail.calcEngWt_02 = function (data) {
        if (!data || !data.length) return [];
        var result = [];
        var addNode = function (item, r01) {
            var rn;
            // Find kotei sum data
            $.each(result, function (i, rd) {
                if (rd.sort_kotei === item.sort_kotei) {
                    rn = rd;
                }
            });
            // Create new kotei
            if (!rn) {
                rn = {
                    sort_kotei: item.sort_kotei,
                    zoku_kotei: item.zoku_kotei,
                    kotei_index: item.kotei_index,
                    r02: HaigoHyo_Tab.detail.getCalcResNode("r02", "工程重量", r01.value),
                    isPassed: true
                }
                result.push(rn);
            } else {
                // Sum quantity
                rn.r02.plus(r01.value);
            }
            return rn;
        }
        $.each(data, function (ind, item) {
            var node = addNode(item, item.r01);
            if (!App.isUndefOrNullOrStrEmpty(item.quantity)) {
                node.isPassed = false;
            }
        });
        $.each(result, function (ind, item) {
            if (item.isPassed) {
                item.r02.setToNull();
            }
        })
        return result;
    }

    /**
    * ③ソースの工程重量
    * Return Calculated node
    */
    HaigoHyo_Tab.detail.calcSauceWt_03 = function (p_r02) {
        var result = HaigoHyo_Tab.detail.getCalcResNode("r03", "ソースの工程重量");
        if (!p_r02 || !p_r02.length) return result;
        // Find total weight of Sauce
        $.each(p_r02, function (i, rd) {
            if (rd.zoku_kotei === App.settings.app.kotei_AOH.Sauce) {
                result.setValue(rd.r02.value);
            }
        });
        return result;
    }
    
    /**
    * ④合計重量
    * Return Calculated node
    */
    HaigoHyo_Tab.detail.calcTotalWt_04 = function (p_r02) {
        var result = HaigoHyo_Tab.detail.getCalcResNode("r04", "合計重量");
        if (!p_r02 || !p_r02.length) return result;
        var isFirst = true;
        $.each(p_r02, function (i, rd) {
			if (rd.isPassed) {
				return;
			}
            if (isFirst && !rd.isNull) {
                result.setValue(0);
                isFirst = false;
            }
            result = result.plus(rd.r02.value);
        });
        return result;
    }

    /**
    * ⑤レシピコスト小計
    * Add r05 to data list
    */
    HaigoHyo_Tab.detail.calcRecipeCostSub_05 = function (data) {
        if (!data) return;
        $.each(data, function (ind, item) {
            item.r05 = HaigoHyo_Tab.detail.getCalcResNode("r05", "レシピコスト小計");
            if (App.isUndefOrNullOrStrEmpty(item.quantity)) {
                item.r05.setValue(0);
                item.r05.pass();
                return;
            }
            if (!item.tanka) {
                item.tanka = 0;
            }
            item.r05.setValue(item.r01.value).times(item.tanka).div(item.budomari).times(100);
        });
    }

    /**
    * ⑥レシピコスト(円／Kg)
    * Return Calculated node
    * Request #1863
    */
    HaigoHyo_Tab.detail.calcRecipeCost_06 = function (data, p_r04, p_r10, p_r03) {
        var result = HaigoHyo_Tab.detail.getCalcResNode("r06", "レシピコスト(円／Kg)");
        if (!data) return result;
        var isFirst = true;
        var SauseKotei = {
            sum_r05: HaigoHyo_Tab.detail.getCalcResNode("r00", "SauseKotei"),
            isPassed: true
        };
        var BettenKotei = {
            sum_r05: HaigoHyo_Tab.detail.getCalcResNode("r00", "BettenKotei"),
            isPassed: true
        };
        var getKotei = function (item) {
            if (item.zoku_kotei == App.settings.app.kotei_AOH.Sauce) {
                return SauseKotei;
            }
            else {
                return BettenKotei;
            }
        }
        // Sum r05
        $.each(data, function (ind, item) {
            if (item.r05.isPassed) { return }
            var kotei = getKotei(item);
            if (kotei.isPassed) {
                kotei.sum_r05.setValue(0);
                kotei.isPassed = false;
            }
            kotei.sum_r05 = kotei.sum_r05.plus(item.r05.value);
        });

        if (!SauseKotei.isPassed || !BettenKotei.isPassed) {
            result.setValue(0);
            // ソースコスト = SUM⑤ ÷ ④合計重量 ÷ ( ⑩上がり目方 ÷ ③ソースの工程重量 )
            if (!SauseKotei.isPassed) {
                if (SauseKotei.sum_r05.isNull || App.isUndefOrNullOrStrEmpty(p_r04) || p_r04.eq(0) || App.isUndefOrNullOrStrEmpty(p_r10) || App.isUndefOrNullOrStrEmpty(p_r03)) {
                    result.setToNull();
                } else if (p_r10.eq(0) || p_r03.eq(0)) {
                    result.setValue(0);
                } else {
                    result = result.plus(SauseKotei.sum_r05.div(p_r04).div(p_r10.div(p_r03)).value);
                }
            }
            // 別添コスト = SUM⑤ ÷ ④合計重量
            if (!BettenKotei.isPassed) {
                result = result.plus(BettenKotei.sum_r05.div(p_r04).value);
            }
        }
        // ソースコスト ＋ 別添コスト
        return result;

    }

    /**
    * ⑥レシピコスト(円／1個)
    * Return Calculated node
    */
    HaigoHyo_Tab.detail.calcRecipeCost_07 = function (p_r06) {
        var result = HaigoHyo_Tab.detail.getCalcResNode("r07", "レシピコスト(円／1個)");
        var td_tani = KihonJohyo_Tab.getTani();
        var base_tani = (!td_tani.cd_tani ? 0 : Number(td_tani.cd_tani));
        var netWt = !td_tani.yoryo ? 0 : td_tani.yoryo.replace(/,/g, "");
        switch (base_tani) {
            // Kg
            case App.settings.app.tani_AOH.Kg:
                // r06 x 内容量 x 1000
                result.setValue(p_r06).times(netWt);
                break;
            // g
            case App.settings.app.tani_AOH.Gram:
                // r06 x 内容量
                result.setValue(p_r06).times(netWt).div(1000);
                break;
            // L
            case App.settings.app.tani_AOH.Lit:
                // r06 x 内容量 x 1000
                result.setValue(p_r06).times(netWt);
                break;
            // ml
            case App.settings.app.tani_AOH.mL:
                // r06 x 内容量
                result.setValue(p_r06).times(netWt).div(1000);
                break;
        }
        return result;
    }

    /**
    * ⑧果肉量小計
    * Add r08 to data list
    */
    HaigoHyo_Tab.detail.calcPulpWt_08 = function (data) {
        if (!data) return;
        $.each(data, function (ind, item) {
            item.r08 = HaigoHyo_Tab.detail.getCalcResNode("r08", "果肉量小計");
            if (item.zoku_kotei === App.settings.app.kotei_AOH.Betten) {
                item.r08.setValue(0);
                return;
            }
            if (App.isUndefOrNullOrStrEmpty(item.quantity)) {
                item.r08.setValue(0);
                item.r08.pass();
                return;
            }
            // 単位（マスタ）
            switch (item.cd_tani_master) {
                // Kg
                case App.settings.app.genryo_tani_AOH.Kg:
                    // 単位(画面) 
                    switch (item.cd_tani_gamen) {
                        // Kg
                        case App.settings.app.genryo_tani_AOH.Kg:
                        case App.settings.app.genryo_tani_AOH.Kg.toString():
                            // 配合量 × 果肉量 ÷ 入れ目量
                            item.r08.setValue(item.quantity).times(item.wt_kaniku).div(item.wt_ireme);          // Request #1401
                            break;
                            // 缶
                        case App.settings.app.genryo_tani_AOH.Can:
                        case App.settings.app.genryo_tani_AOH.Can.toString():
                            // 配合量 × 果肉量
                            item.r08.setValue(item.quantity).times(item.wt_kaniku);                             // Request #1401
                            break;
                            // 缶
                        case App.settings.app.genryo_tani_AOH.Gram:
                        case App.settings.app.genryo_tani_AOH.Gram.toString():
                            // 配合量 × 果肉量 ÷ (入れ目量 × 1000)
                            item.r08.setValue(item.quantity).times(item.wt_kaniku).div(item.wt_ireme).div(1000);    // Request #1401
                            break;
                            // Else - 0
                        default:
                            item.r08.setValue(0);
                            break;
                    }
                    break;
                // 缶
                case App.settings.app.genryo_tani_AOH.Can:
                    // 単位(画面) 
                    switch (item.cd_tani_gamen) {
                        // Kg
                        case App.settings.app.genryo_tani_AOH.Kg:
                        case App.settings.app.genryo_tani_AOH.Kg.toString():
                            // 配合量 × 果肉量 ÷ 1缶重量
                            item.r08.setValue(item.quantity).times(item.wt_kaniku).div(item.wt_1_kan);          // Request #1401
                            break;
                        // g
                        case App.settings.app.genryo_tani_AOH.Gram:
                        case App.settings.app.genryo_tani_AOH.Gram.toString():
                            // 配合量 × 果肉量 ÷ 1缶重量
                            item.r08.setValue(item.quantity).times(item.wt_kaniku).div(item.wt_1_kan).div(1000);    // Request #1401
                            break;
                        // Else - 0
                        default:
                            item.r08.setValue(0);
                            break;
                    }
                    break;
                // Else - 0
                default:
                    item.r08.setValue(0);
                    break;
            }
        });
    }
    
    /**
    * ⑨投入全果肉量
    * Return Calculated node
    */
    HaigoHyo_Tab.detail.calcPulpTotalWt_09 = function (data) {
        var result = HaigoHyo_Tab.detail.getCalcResNode("r09", "投入全果肉量");
        if (!data) return result;
        var isFirst = true;
        $.each(data, function (ind, item) {
            if (App.isUndefOrNullOrStrEmpty(item.quantity)) {
                return;
            }
            if (isFirst) {
                result.setValue(0);
                isFirst = false;
            }
            result = result.plus(item.r08.value);
        });
        return result;
    }
            
    /**
    * ⑩上がり目方
    * Return Calculated node
    */
    HaigoHyo_Tab.detail.calcRaiseWt_10 = function (p_r09, p_r02, FC) {
        var pattern = page.detail.getKoteiPattern();
        var result = HaigoHyo_Tab.detail.getCalcResNode("r10", "上がり目方");
        result.hasSauce = false;
        if (!pattern) return result;
        if (pattern === App.settings.app.kotei_pattern_AOH.Fixed) {
            if (p_r02 && p_r02.length) {
                $.each(p_r02, function (ind, item) {
                    if (item.zoku_kotei === App.settings.app.kotei_AOH.Sauce) {
                        result.setValue(item.r02.value);
                    }
                });
            }
        } else {
            result.setValue(p_r09).div(FC).times(100);
        }
        if (p_r02 && p_r02.length) {
            $.each(p_r02, function (ind, item) {
                if (item.zoku_kotei === App.settings.app.kotei_AOH.Sauce) {
                    if (!item.isPassed) {
                        result.hasSauce = true;
                    }
                }
            });
        }
        return result;
    }

    /**
    * ⑪工程仕上重量
    * Return Calculated node
    */
    HaigoHyo_Tab.detail.calcFinishWt_11 = function (p_r10) {
        var result = HaigoHyo_Tab.detail.getCalcResNode("r11", "工程仕上重量", p_r10);
        if (p_r10) {
            result.hasSauce = p_r10.hasSauce
        }
        return result;
    }

    /**
    * ⑫合計仕上重量
    * Return Calculated node
    */
    HaigoHyo_Tab.detail.calcFinishTotalWt_12 = function (p_r02, p_r11, wt_betten, hasSauce) {
        //var result = HaigoHyo_Tab.detail.getCalcResNode("r12", "合計仕上重量");
        //if (!wt_betten) wt_betten = 0;
        var isFirst = true;
        var hasBetten = false;
        $.each(p_r02, function (i, d) {
            if (App.isUndefOrNullOrStrEmpty(d.zoku_kotei)) {
                return;
            }
            if (d.zoku_kotei === App.settings.app.kotei_AOH.Betten) {
                hasBetten = true;
            }
            //if (d.zoku_kotei === App.settings.app.kotei_AOH.Sauce) {
            //    hasSauce = true;
            //}
        });
        var result = HaigoHyo_Tab.detail.totalFnWt(p_r11, hasSauce, wt_betten, hasBetten);
        //$.each(p_r02, function (i, d) { 
        //    if (App.isUndefOrNullOrStrEmpty(d.zoku_kotei)) {
        //        return;
        //    }
        //    if (isFirst) {
        //        result.setValue(0);
        //        isFirst = false;
        //    }
        //    if (d.zoku_kotei === App.settings.app.kotei_AOH.Betten) {
        //        result = result.plus(wt_betten);
        //    } else {
        //        result = result.plus(p_r11);
        //    }
        //});
        return result;
    }

    /**
    * ⑬糖量小計
    * Add r13 to data list
    */
    HaigoHyo_Tab.detail.calcSugarSubWt_13 = function (data) {        
        if (!data) return;
        $.each(data, function (ind, item) {
            item.r13 = HaigoHyo_Tab.detail.getCalcResNode("r13", "糖量小計");
            if (App.isUndefOrNullOrStrEmpty(item.quantity)) {
                item.r13.setValue(0);
                return;
            }
            if (App.isUndefOrNull(item.wt_sato)) return;
            if (item.wt_sato == 0 && item.kbn_sato != 1) {
                item.r13.setValue(0);
                return;
            }
            if (!item.wt_sato && item.kbn_sato == 1) {
                // 単位（マスタ）
                if (item.cd_tani_master === App.settings.app.genryo_tani_AOH.Kg) {
                    // 単位(画面) 
                    switch (item.cd_tani_gamen) {
                        // Kg
                        case App.settings.app.genryo_tani_AOH.Kg:
                        case App.settings.app.genryo_tani_AOH.Kg.toString():
                            // 配合量 × 可溶性固形物
                            item.r13.setValue(item.quantity).times(item.wt_kayosei_kokeibutsu);			//Request #1865
                            break;
                        //g
                        case App.settings.app.genryo_tani_AOH.Gram:
                        case App.settings.app.genryo_tani_AOH.Gram.toString():
                            // 配合量 × 可溶性固形物 ÷ 1000
                            item.r13.setValue(item.quantity).times(item.wt_kayosei_kokeibutsu).div(1000);			//Request #1865
                            break;
                        // Else
                        default:
                            item.r13.setValue(0);
                            break;
                    }
                } else {
                    item.r13.setValue(0);
                }
            } else if (item.wt_sato != 0) {
                // 単位（マスタ）
                if (item.cd_tani_master === App.settings.app.genryo_tani_AOH.Kg) {
                    // 単位(画面) 
                    switch (item.cd_tani_gamen) {
                        // Kg
                        case App.settings.app.genryo_tani_AOH.Kg:
                        case App.settings.app.genryo_tani_AOH.Kg.toString():
                            // 配合量 × 砂糖量 ÷ 入れ目量
                            item.r13.setValue(item.quantity).timesIgnoreZero(item.wt_sato).div(item.wt_ireme);
                            break;
                        // 缶
                        case App.settings.app.genryo_tani_AOH.Can:
                        case App.settings.app.genryo_tani_AOH.Can.toString():
                            // 配合量 × 砂糖量
                            item.r13.setValue(item.quantity).timesIgnoreZero(item.wt_sato);
                            break;
                        //g
                        case App.settings.app.genryo_tani_AOH.Gram:
                        case App.settings.app.genryo_tani_AOH.Gram.toString():
                            // 配合量 × 砂糖量 ÷ (入れ目量 × 1000)
                            item.r13.setValue(item.quantity).timesIgnoreZero(item.wt_sato).div(item.wt_ireme).div(1000);
                            break;
                            // Else
                        default:
                            item.r13.setValue(0);
                            break;
                    }
                } else {
                    item.r13.setValue(0);
                }
            }
        });
    }

    /**
    * ⑭可溶性固形物量小計
    * Add r14 to data list
    */
    HaigoHyo_Tab.detail.calcSolidSubWt_14 = function (data) { 
        if (!data) return;
        $.each(data, function (ind, item) {
            item.r14 = HaigoHyo_Tab.detail.getCalcResNode("r14", "可溶性固形物量小計");
            if (item.zoku_kotei === App.settings.app.kotei_AOH.Betten) {
                item.r14.setValue(0);
                return;
            }
            if (App.isUndefOrNullOrStrEmpty(item.quantity)) {
                item.r14.setValue(0);
                item.r14.pass();
                return;
            }
            if (App.isUndefOrNull(item.wt_kaniku)) {
                return;
            }
            if (!item.wt_kaniku) {
                // 単位（マスタ）
                if (item.cd_tani_master === App.settings.app.genryo_tani_AOH.Kg) {
                    // 単位(画面) 
                    switch (item.cd_tani_gamen) {
                        // Kg
                        case App.settings.app.genryo_tani_AOH.Kg:
                        case App.settings.app.genryo_tani_AOH.Kg.toString():
                            // 配合量 × 可溶性固形物
                            item.r14.setValue(item.quantity).times(item.wt_kayosei_kokeibutsu);			//Request #1865
                            break;
                        // g
                        case App.settings.app.genryo_tani_AOH.Gram:
                        case App.settings.app.genryo_tani_AOH.Gram.toString():
                            // 配合量 × 可溶性固形物 ÷ 1000
                            item.r14.setValue(item.quantity).times(item.wt_kayosei_kokeibutsu).div(1000);			//Request #1865
                            break;
                        // Else
                        default:
                            item.r14.setValue(0);
                            break;
                    }
                } else {
                    item.r14.setValue(0);
                }

            } else {
                // 果肉量 <> 0
                // 単位（マスタ）
                if (item.cd_tani_master === App.settings.app.genryo_tani_AOH.Can) {
                    // 単位(画面) 
                    switch (item.cd_tani_gamen) {
                        // Kg
                        case App.settings.app.genryo_tani_AOH.Kg:
                        case App.settings.app.genryo_tani_AOH.Kg.toString():
                            // 配合量 × 可溶性固形物
                            item.r14.setValue(item.quantity).times(item.wt_kayosei_kokeibutsu);			//Request #1865
                            break;
                        // g
                        case App.settings.app.genryo_tani_AOH.Gram:
                        case App.settings.app.genryo_tani_AOH.Gram.toString():
                            // 配合量 × 可溶性固形物 ÷ 1000
                            item.r14.setValue(item.quantity).times(item.wt_kayosei_kokeibutsu).div(1000);			//Request #1865
                            break;
                        // Else
                        default:
                            item.r14.setValue(0);
                            break;
                    }

                } else {
                    // r08 × 可溶性固形物 + r13
                    if (!item.r13.value) {          //  Request #1396
                        item.r14.setToNull();
                    } else {
                        item.r14.setValue(item.r08.value).times(item.wt_kayosei_kokeibutsu).plus(item.r13.value);			//Request #1865
                    }
                }
            }
        });
    }

    /**
    * ⑮必要可溶性固形物
    * Return Calculated node
    */
    HaigoHyo_Tab.detail.calcNecSolid_15 = function (p_r10, Brix) {
        var result = HaigoHyo_Tab.detail.getCalcResNode("r15", "必要可溶性固形物");
        if (App.isUndefOrNullOrStrEmpty(Brix) || (new BigNumber(Brix)).eq(0)) {
            result.setToNull();
            return result;
        }
        // r10 × Brix ÷ 100
        return result.setValue(p_r10).times(Brix).div(100);
    }

    /**
    * ⑯投入可溶性固形物
    * Return Calculated node
    */
    HaigoHyo_Tab.detail.calcInjSolid_16 = function (data) {
        var result = HaigoHyo_Tab.detail.getCalcResNode("r16", "投入可溶性固形物");
        if (!data) return result;
        var isFirst = true;
        $.each(data, function (ind, item) {
            if (App.isUndefOrNullOrStrEmpty(item.quantity)) {
                return;
            }
            if (isFirst) {
                result.setValue(0);
                isFirst = false;
            }
            result = result.plus(item.r14.value);
        });
        return result;
    }

    /**
    * ⑰不足可溶性固形物 
    * Return Calculated node
    */
    HaigoHyo_Tab.detail.calcInsSolid_17 = function (p_r15, p_r16) {
        var res = HaigoHyo_Tab.detail.getCalcResNode("r17", "不足可溶性固形物");
        return res.setValue(p_r15).minus(p_r16);
    }

    /**
    * ⑱濃縮率計算
    * Return Calculated node
    */
    HaigoHyo_Tab.detail.calcConRate_18 = function (p_r10, p_r03) {
        var result = HaigoHyo_Tab.detail.getCalcResNode("r18", "濃縮率計算");
        return result.setValue(p_r10).div(p_r03).times(100);
    }

    /**
    * ⑲ペクチンガム濃度
    * Return Calculated node
    */
    HaigoHyo_Tab.detail.calcGumCon_19 = function (data, p_r10) {
        var result = HaigoHyo_Tab.detail.getCalcResNode("r19", "ペクチンガム濃度");
        if (!data && !data.length) return result;
        //result.setValue(0);
        // Check if any qty of genryo is inputted
        var isFirst = true;
        var existsKbnGum = false;
        // SUM(r14)
        $.each(data, function (ind, item) {
            if (App.isUndefOrNullOrStrEmpty(item.quantity)) {
                return;
            }
            if (App.isUndefOrNullOrStrEmpty(item.kbn_gum)) {
                result.setToNull();
                return false;
                //return;
            }
            if (isFirst) {
                result.setValue(0);
                isFirst = false;
            }
            if (item.kbn_gum == 0) {
                return;
            }
            if (item.kbn_gum == 1) {
                existsKbnGum = true;
                result = result.plus(item.r14.value);
            }
        });
        if (result.isNull || isFirst) {
            return result.setToNull();
        }
        if (!existsKbnGum) {
            return result;
        }
        // SUM(r14) × 100000 ÷ r10
        return result.times(100000).div(p_r10);
    }

    /**
    * ⑳甘味度小計
    * Return Calculated node
    */
    HaigoHyo_Tab.detail.calcSweetSub_20 = function (data) {
        var result = HaigoHyo_Tab.detail.getCalcResNode("r20", "甘味度小計");
        if (!data) return result;
        var isFirst = true;
        $.each(data, function (ind, item) {
            if (App.isUndefOrNullOrStrEmpty(item.quantity)) {
                return;
            }
            if (item.r14.isNull || App.isUndefOrNullOrStrEmpty(item.wt_kanmido)) {			//Request #1865
                result.setToNull();
                return false;
            }
            if (isFirst) {
                result.setValue(0);
                isFirst = false;
            }
            result = result.plus(item.r14.value.times(item.wt_kanmido));
        });
        // SUM(r14 x 甘味度)
        return result;
    }
    /**
    * ㉑推定甘味度計算
    * Return Calculated node
    */
    HaigoHyo_Tab.detail.calcSweetEst_21 = function (p_r20, p_r10) {
        var result = HaigoHyo_Tab.detail.getCalcResNode("r21", "推定甘味度計算");
        return result.setValue(p_r20).div(p_r10).times(100);
    }


    /**
    * resort genryo in this kotei 。
    */
    HaigoHyo_Tab.detail.sortGenryo = function (selectedGenryo) {
        var element = HaigoHyo_Tab.detail.element.find(".datatable"),
            kotei = selectedGenryo.prevAll(".row-kotei").first(),
            nextRow, seqGenryo = 1;

        if (!kotei.length) {
            return;
        }

        nextRow = kotei.next();
        while (nextRow.length > 0 && nextRow.find(".item-kotei:checkbox").length == 0 && !nextRow.hasClass("item_total_kotei_tml")) {

            id = nextRow.attr("data-key");
            entity = HaigoHyo_Tab.detail.dataHaigo.entry(id);

            if (page.values.modeDisp == App.settings.app.m_shisaku_data.shinki
                || page.values.modeDisp == page.values.modeDisplay.Zencopy
                || page.values.modeDisp == page.values.modeDisplay.Featurecopy) {
                //entity.seq_kotei = seqGenryo;
            }

            //entity.sort_kotei = entity.cd_kotei;
            entity.sort_genryo = seqGenryo;

            HaigoHyo_Tab.detail.dataHaigo.update(entity);
            HaigoHyo_Tab.detail.updateShisakuList(nextRow, entity);

            nextRow = nextRow.next();

            seqGenryo += 1;
        }
    };

    /**
     * resort label kotei group。
     */
    HaigoHyo_Tab.detail.sortKotei = function (isChangeKotei) {
        var element = HaigoHyo_Tab.detail.element.find(".datatable"),
            listKotei = element.find(".item-kotei:checkbox");

        var row, entity, id, nextRow,
            seqKotei = 0,
            seqGenryo = 0;
        $.each(listKotei, function (index, item) {
            row = element.find(item).closest("tbody");

            if (row.hasClass("item-tmpl")) {
                return true;
            };

            seqKotei += 1;
            seqGenryo = 1;

            row.findP("sort_kotei").text(seqKotei);

            nextRow = row.next();
            while (nextRow.length > 0 && nextRow.find(".item-kotei:checkbox").length == 0 && !nextRow.hasClass("item_total_kotei_tml")) {
                id = nextRow.attr("data-key");
                entity = HaigoHyo_Tab.detail.dataHaigo.entry(id);

                if (page.values.modeDisp == App.settings.app.m_shisaku_data.shinki
                || page.values.modeDisp == page.values.modeDisplay.Zencopy
                || page.values.modeDisp == page.values.modeDisplay.Featurecopy) {
                    entity.seq_kotei = seqGenryo;
                }

                entity.sort_kotei = seqKotei;
                entity.sort_genryo = seqGenryo;

                HaigoHyo_Tab.detail.dataHaigo.update(entity);
                HaigoHyo_Tab.detail.updateShisakuList(nextRow, entity);

                nextRow.findP("sort_kotei").text(seqKotei);
                nextRow = nextRow.next();

                seqGenryo += 1;
            }
        });

        //control display total
        HaigoHyo_Tab.detail.controlDispTotalRow();

        //recalculate total row
        HaigoHyo_Tab.detail.calculateTotal();
    }

    /**
     * update data shisaku list on row。
     */
    HaigoHyo_Tab.detail.updateShisakuList = function (row, data) {
        var shisa, count,
            conti = 0;

        HaigoHyo_Tab.detail.dataTable.dataTable("getRow", row, function (rows) {
            shisa = rows.element.find(".change_keta_shosu");
        });

        count = shisa.length;

        if (!count) {
            return;
        }


        while (conti < count) {
            var item = HaigoHyo_Tab.detail.element.find(shisa[conti]),
                id = item.attr("data-key"), entity;

            item.attr("data-prop", "quantity_" + data.cd_kotei + "_" + data.seq_kotei);

            if (App.isUndefOrNull(id)) {
                conti += 1;
                continue;
            }

            entity = HaigoHyo_Tab.detail.dataShisakuList.entry(id);

            //entity.cd_kotei = data.cd_kotei;
            entity.sort_kotei = data.sort_kotei;

            entity.seq_kotei = data.seq_kotei;
            entity.sort_genryo = data.sort_genryo;

            HaigoHyo_Tab.detail.dataShisakuList.update(entity);
            item.attr("data-prop", "quantity_" + entity.cd_kotei + "_" + entity.seq_kotei + "_" + entity.seq_shisaku);

            conti += 1;
        }
    };

    /**
    * サンプル説明書
    * 印刷FGが選択されていること
    */
    HaigoHyo_Tab.detail.shisakuPrintcheck = function () {
        var deferred = $.Deferred(),
            element = HaigoHyo_Tab.detail.elementShisaku,
            flg_print = element.find(".flg_print:checked"),
            keySample = [];

        $.each(flg_print, function (index, item) {
            indexEl = element.find(item);
            id = indexEl.closest("th").attr("index-col");
            if (App.isUndefOrNull(id)) {
                return true;
            }
            keySample.push(id);
        })

        deferred.resolve(keySample);
        return deferred.promise();
    }

    /**
    * show Tsuika Dialog
    */
    HaigoHyo_Tab.detail.showShisakuRetuTuikaDialog = function (e) {

        var validations = [];
        validations.push(HaigoHyo_Tab.detail.validateHeader());

        App.async.all(validations).then(function () {

            var element = HaigoHyo_Tab.detail.element,
                elementShisaku = HaigoHyo_Tab.detail.elementShisakuHead,
                head_check = elementShisaku.find('input:checked');

            //試作列が選択されていること
            App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0028, { strName: "試作列" })).remove();
            if (head_check.length == 0) {
                App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0028, { strName: "試作列" })).show();
                return;
            }

            var element = HaigoHyo_Tab.detail.element,
                elementHaigo = element.find(".dt-container .fix-columns .dt-fix-body .datatable"),
                first = elementHaigo.find(".row-kotei:eq(0)"),
                dataMaster = {
                    kotei: [],
                    shisaku: [],
                    keta_shosu: page.detail.tabs.kihonjohyo_tab.values.keta_shosu_org,
                    maxCol: HaigoHyo_Tab.detail.options.maxSeqShisaku + 1
                };

            //dataMaster.kotei =
            HaigoHyo_Tab.detail.dataKotei.findAll(function (item, entity) {
                if (entity.state !== App.ui.page.dataSet.status.Deleted) {
                    var ItemNew = $.extend(true, {}, item);
                    ItemNew.sort_kotei = elementHaigo.find(App.str.format("tbody[data-key='{0}']", item.__id)).findP("sort_kotei").text();
                    dataMaster.kotei.push(ItemNew);
                };
            });

            var colShi = elementShisaku.find(".col-sample:eq(0)");

            while (colShi.length) {
                if (colShi.hasClass("th-tmpl")) {
                    colShi = colShi.next();
                    continue;
                }

                var id = colShi.attr("index-col"),
                    datacol = element.find(".sample-" + id).form().data();

                datacol.seq_shisaku = id;
                dataMaster.shisaku.push(datacol);
                colShi = colShi.next();
            }

            if (App.isFunc(HaigoHyo_Tab.shisakuCopyDialog)) {
                HaigoHyo_Tab.shisakuCopyDialog(e, dataMaster);
            }
        });
    }

    /**
    * 工程移動▼
    */
    HaigoHyo_Tab.detail.moveDownKotei = function () {
        var element = HaigoHyo_Tab.detail.element.find(".datatable"),
                listKotei = element.find(".item-kotei:checkbox"),//get kotei is checked
                selectedKotei, endRow;

        selectedKotei = element.find(".item-kotei:checkbox:checked").closest('tbody');//get kotei is checked
        endRow = selectedKotei.nextAll(".row-kotei").first();

        //break when kotei is not checked
        if (selectedKotei.length == 0 || endRow.length == 0) {
            return;
        }

        App.ui.loading.show();
        setTimeout(function () {

            //フラグ変更画面
            page.values.isChange = true;

            var rowInsert1, rowInsert2, tbdSled1, tbdSled2,
                contn = true;

            rowInsert = HaigoHyo_Tab.detail.endRow(endRow);
            HaigoHyo_Tab.detail.dataTable.dataTable("getRow", rowInsert, function (rowObject) {
                rowInsert1 = rowObject.element[0];
                rowInsert2 = rowObject.element[1];
            });

            selectedKotei = endRow.prev();
            while (contn) {
                if (selectedKotei.hasClass("row-kotei") || selectedKotei.hasClass("item-tmpl")) {
                    contn = false;
                }

                HaigoHyo_Tab.detail.dataTable.dataTable("getRow", selectedKotei, function (rowObject) {
                    tbdSled1 = rowObject.element[0];
                    tbdSled2 = rowObject.element[1];
                });

                selectedKotei = endRow.prev();

                element.find(tbdSled1).detach().insertAfter(element.find(rowInsert1));
                element.find(tbdSled2).detach().insertAfter(element.find(rowInsert2));

            }

            //renew sort kotei
            HaigoHyo_Tab.detail.sortKotei();
        }, 1)
    }

    /**
    * 工程移動▲
    */
    HaigoHyo_Tab.detail.moveUpKotei = function () {

        var element = HaigoHyo_Tab.detail.element.find(".datatable"),
            listKotei = element.find(".item-kotei:checkbox"),//get kotei is checked
            selectedKotei, endRow;

        selectedKotei = element.find(".item-kotei:checkbox:checked").closest('tbody');//get kotei is checked
        rowInsert = selectedKotei.prevAll(".row-kotei").first();

        //break when kotei is not checked
        if (selectedKotei.length == 0 || rowInsert.length == 0) {
            App.ui.loading.close();
            return;
        }

        App.ui.loading.show();
        setTimeout(function () {

            //フラグ変更画面
            page.values.isChange = true;

            var rowInsert1, rowInsert2, tbdSled1, tbdSled2,
                contn = true;

            HaigoHyo_Tab.detail.dataTable.dataTable("getRow", rowInsert, function (rowObject) {
                rowInsert1 = rowObject.element[0];
                rowInsert2 = rowObject.element[1];
            });

            while (contn) {
                HaigoHyo_Tab.detail.dataTable.dataTable("getRow", selectedKotei, function (rowObject) {
                    tbdSled1 = rowObject.element[0];
                    tbdSled2 = rowObject.element[1];
                });


                selectedKotei = selectedKotei.next();

                element.find(tbdSled1).detach().insertBefore(element.find(rowInsert1));
                element.find(tbdSled2).detach().insertBefore(element.find(rowInsert2));

                if (selectedKotei.hasClass("row-kotei") || selectedKotei.hasClass("item_total_kotei_tml")) {
                    contn = false;
                }
            }

            //renew sort kotei
            HaigoHyo_Tab.detail.sortKotei();

            //App.ui.loading.close();
        }, 1)
    }

    /**
    * 工程削除
    */
    HaigoHyo_Tab.detail.delKotei = function () {

        var element = HaigoHyo_Tab.detail.element.find(".datatable"),
            selectedKotei = element.find(".item-kotei:checkbox:checked");//get kotei is checked

        //break when kotei is not checked
        if (selectedKotei.length == 0) {
            return;
        }

        var options = {
            text: App.str.format(App.messages.app.AP0027, { name: "工程行" })
        };

        //原価試作依頼されている工程を含む行（配合量に値が入っている行）は【工程削除】できない
        if (element.find(selectedKotei).closest('td').findP("flg_shisanIrai_kotei").text() == HaigoHyo_Tab.values.flg_shisanIrai) {
            App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0033, { strName: "工程削除" })).remove();
            App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0033, { strName: "工程削除" })).show();
            return;
        }

        HaigoHyo_Tab.confirmDialog(options).then(function () {

            App.ui.loading.show();
            setTimeout(function () {

                //フラグ変更画面
                page.values.isChange = true;

                //reset region out tbody
                selectedKotei = element.find(selectedKotei).closest('tbody');

                var id, entity, contn = true, nextRow, quantityInput;

                while (contn) {

                    nextRow = selectedKotei.next();

                    HaigoHyo_Tab.detail.dataTable.dataTable("deleteRow", selectedKotei, function (row) {
                        //del haigo
                        id = row.attr("data-key");

                        //remove mess error
                        row.find(".has-error, .error").each(function (i, elem) {
                            App.ui.page.notifyAlert.remove(elem);
                        });

                        if (!App.isUndefOrNull(id)) {
                            if (row.hasClass("row-kotei")) {
                                var entity = HaigoHyo_Tab.detail.dataKotei.entry(id);
                                HaigoHyo_Tab.detail.dataKotei.remove(entity);
                            } else {
                                var entity = HaigoHyo_Tab.detail.dataHaigo.entry(id);
                                HaigoHyo_Tab.detail.dataHaigo.remove(entity);
                            }
                        }

                        //del entity Quanlity
                        quantityInput = row.find(".quantity");
                        $.each(quantityInput, function (index, item) {
                            id = row.find(item).attr("data-key");
                            if (!App.isUndefOrNull(id)) {
                                var entity = HaigoHyo_Tab.detail.dataShisakuList.entry(id);
                                HaigoHyo_Tab.detail.dataShisakuList.remove(entity);
                            }
                        })
                    });

                    selectedKotei = nextRow;
                    if (nextRow.hasClass("row-kotei") || nextRow.hasClass("item_total_kotei_tml")) {
                        contn = false;
                    }
                }

                //check list kotei in table
                HaigoHyo_Tab.detail.checkExitKotei();

                //Validate kotei
                HaigoHyo_Tab.detail.eachKotei(function (eKotei) {
                    HaigoHyo_Tab.detail.validator.validate({
                        targets: eKotei,
                        state: {
                            tbody: eKotei.closest("tbody"),
                            isGridValidation: true
                        },
                        filter: HaigoHyo_Tab.detail.validationFilter
                    });
                });
                App.ui.loading.close();
            }, 1);

        })
    };

    /**
    *新工程を挿入する。
    */
    HaigoHyo_Tab.detail.addBlankKotei = function (isMove) {
        var element = HaigoHyo_Tab.detail.element.find(".datatable"),
            row, end,
            moveRow = function () {//move row to top 
                if (!isMove) {
                    return;
                }
                element.find(row[0]).detach().insertBefore(element.find(end[0]));
                element.find(row[1]).detach().insertBefore(element.find(end[1]));

            };

        if (isMove) {
            end = element.find(".item_total_kotei_tml");
            HaigoHyo_Tab.detail.dataTable.dataTable("getRow", end, function (row) {
                end = row.element;
            });

        };


        //data shisaku hin
        var dataHeader = HaigoHyo_Tab.getDataHeader();

        //reset index kotei and index seq kotei
        HaigoHyo_Tab.detail.options.maxKotei = 1;
        HaigoHyo_Tab.detail.options.maxSeqKotei = {};
        HaigoHyo_Tab.detail.options.maxSeqKotei[HaigoHyo_Tab.detail.options.maxKotei] = 0;

        //reset list validate item shisaku list
        HaigoHyo_Tab.detail.options.validationsShisakuList = {};

        //create kotei 1
        var dataKotei = {
            cd_shain: page.values.cd_shain,
            nen: page.values.nen,
            no_oi: page.values.no_oi,
            cd_kotei: HaigoHyo_Tab.detail.options.maxKotei,
            sort_kotei: HaigoHyo_Tab.detail.options.maxKotei,
            //工場　担当会社
            cd_kaisha: dataHeader.cd_kaisha,
            //工場　担当工場
            cd_busho: dataHeader.cd_kojo
            //seq_kotei: HaigoHyo_Tab.detail.options.maxSeqKotei
        }
        row = HaigoHyo_Tab.detail.addNewItem(dataKotei, true);
        moveRow(row);

        //create genryo 1
        HaigoHyo_Tab.detail.options.maxSeqKotei[HaigoHyo_Tab.detail.options.maxKotei] += 1;
        var dataGenryo = {
            cd_shain: page.values.cd_shain,
            nen: page.values.nen,
            no_oi: page.values.no_oi,
            cd_kotei: HaigoHyo_Tab.detail.options.maxKotei,
            seq_kotei: HaigoHyo_Tab.detail.options.maxSeqKotei[HaigoHyo_Tab.detail.options.maxKotei],
            sort_kotei: HaigoHyo_Tab.detail.options.maxKotei,
            sort_genryo: 1,
            //工場　担当会社
            cd_kaisha: dataHeader.cd_kaisha,
            //工場　担当工場
            cd_busho: dataHeader.cd_kojo,
            cd_tani: App.settings.app.genryo_tani_AOH.Kg
        }
        row = HaigoHyo_Tab.detail.addNewItem(dataGenryo, false);
        moveRow(row);
    };

    /**
    * 工程をチェックする。
    * add new when not exit kotei
    */
    HaigoHyo_Tab.detail.checkExitKotei = function () {
        var element = HaigoHyo_Tab.detail.element.find(".datatable"),
            listKotei = element.find(".item-kotei:checkbox"),//get kotei is checked
            flgMoveToTop = true;

        //break when kotei is exit
        if (listKotei.length > 1) {
            //renew sort kotei
            HaigoHyo_Tab.detail.sortKotei();
            return;
        }

        //add new kotei when list kotei is null
        HaigoHyo_Tab.detail.addBlankKotei(flgMoveToTop);

        //renew sort kotei
        HaigoHyo_Tab.detail.sortKotei();
    };

    /**
    *原料行＋工程行が150行を超えていないこと
    *Bug #1738
    */
    HaigoHyo_Tab.detail.checkMaxRowBFS = function () {
        var maxRow = HaigoHyo_Tab.detail.element.find(".dt-container .dt-fix-body .datatable .row-count");
        var msg = App.str.format(App.messages.app.AP0203, { maxRow: App.settings.app.maxRowHaigo });

        App.ui.page.notifyAlert.remove("AP0203_BFS");
        if (maxRow.length > App.settings.app.maxRowHaigo) {
            App.ui.page.notifyAlert.message(msg, "AP0203_BFS").show();
            return false;
        }
        return true;
    }

    /**
    *原料行＋工程行が150行を超えていないこと
    */
    HaigoHyo_Tab.detail.checkMaxRow = function (plusRow) {
        var maxRow = HaigoHyo_Tab.detail.element.find(".dt-container .dt-fix-body .datatable .row-count");

        App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0035, { maxRow: App.settings.app.maxRowHaigo })).remove();
        App.ui.page.notifyAlert.remove("AP0203_BFS");       //Bug #1738
        if (maxRow.length + plusRow > App.settings.app.maxRowHaigo) {
            App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0035, { maxRow: App.settings.app.maxRowHaigo })).show();
            return false;
        }
        return true;
    }

    HaigoHyo_Tab.detail.checkMaxKotei = function () {
        var koteiCount = HaigoHyo_Tab.detail.element.find("tbody.new.row-kotei").findP("zoku_kotei").length;
        //
        App.ui.page.notifyInfo.remove("maxKoteiOfShisa");
        if (koteiCount >= App.settings.app.maxKoteiOfShisa) {
            App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0197, { maxRow: App.settings.app.maxKoteiOfShisa }), "maxKoteiOfShisa").show();
            return false;
        }
        return true;
    }

    /**
    * 工程をチェックする。
    */
    HaigoHyo_Tab.detail.beforAddKotei = function () {
        var element = HaigoHyo_Tab.detail.element.find(".datatable"),
            selectedKotei = element.find(".item-kotei:checkbox:checked");//get kotei is checked

        //break when kotei is not checked
        if (selectedKotei.length == 0) {
            return;
        }

        //Kotei count is limited
        if (!HaigoHyo_Tab.detail.checkMaxKotei()) {
            return;
        }

        //原料行＋工程行が150行を超えていないこと
        if (!HaigoHyo_Tab.detail.checkMaxRow(2)) {
            return;
        }

        App.ui.loading.show();
        setTimeout(function () {
            //フラグ変更画面
            page.values.isChange = true;

            //HaigoHyo_Tab.detail.options.maxSeqKotei += 1;
            HaigoHyo_Tab.detail.options.maxKotei += 1;
            HaigoHyo_Tab.detail.options.maxSeqKotei[HaigoHyo_Tab.detail.options.maxKotei] = 0;

            //data kotei item
            var dataKotei = {
                cd_kotei: HaigoHyo_Tab.detail.options.maxKotei,
                cd_shain: page.values.cd_shain,
                nen: page.values.nen,
                no_oi: page.values.no_oi
                //seq_kotei: HaigoHyo_Tab.detail.options.maxSeqKotei
            }

            //kotei mode input
            //if (page.values.modeDisp == App.settings.app.m_shisaku_data.shinki
            //    || page.values.modeDisp == page.values.modeDisplay.Zencopy
            //    || page.values.modeDisp == page.values.modeDisplay.Featurecopy) {
            //    dataKotei.cd_shain = null;
            //    dataKotei.nen = null;
            //    dataKotei.no_oi = null;
            //}

            //kotei mode edit
            //if (page.values.modeDisp == App.settings.app.m_shisaku_data.shosai) {
            //    dataKotei.cd_shain = page.values.cd_shain;
            //    dataKotei.nen = page.values.nen;
            //    dataKotei.no_oi = page.values.no_oi;
            //}

            //data genryo item
            //HaigoHyo_Tab.detail.options.maxSeqKotei += 1;
            HaigoHyo_Tab.detail.options.maxSeqKotei[HaigoHyo_Tab.detail.options.maxKotei] += 1;
            var dataGenryo = {
                cd_shain: dataKotei.cd_shain,
                nen: dataKotei.nen,
                no_oi: dataKotei.no_oi,
                cd_kotei: HaigoHyo_Tab.detail.options.maxKotei,
                seq_kotei: HaigoHyo_Tab.detail.options.maxSeqKotei[HaigoHyo_Tab.detail.options.maxKotei],
                sort_genryo: 1,
                cd_tani: App.settings.app.genryo_tani_AOH.Kg
            }

            //data shisaku hin
            var dataHeader = HaigoHyo_Tab.getDataHeader();
            //基本情報.工場　担当会社
            dataGenryo.cd_kaisha = dataHeader.cd_kaisha;
            //基本情報.工場　担当工場
            dataGenryo.cd_busho = dataHeader.cd_kojo;

            //add new kotei
            HaigoHyo_Tab.detail.addKotei(selectedKotei, dataKotei, dataGenryo);
            //App.ui.loading.close();
        }, 1)
    }

    /**
    * 工程挿入
    */
    HaigoHyo_Tab.detail.addKotei = function (selectedKotei, dataKotei, dataGenryo) {
        var element = HaigoHyo_Tab.detail.element.find(".datatable"),
            isInsertAfter = true,
            entity, id;

        //reset region out tbody
        selectedKotei = element.find(selectedKotei).closest('tbody');

        id = selectedKotei.attr("data-key");
        entity = HaigoHyo_Tab.detail.dataKotei.entry(id);

        var dataSelected = selectedKotei.form().data(),
            endRow = HaigoHyo_Tab.detail.endRow(selectedKotei);

        if (endRow.length == 0) {
            App.ui.loading.close();
            return;
        }

        //add new row kotei
        HaigoHyo_Tab.detail.addNewItem(dataKotei, true, endRow);

        //add new row genryo
        endRow = endRow.next();
        HaigoHyo_Tab.detail.addNewItem(dataGenryo, false, endRow);

        //resort all kotei
        HaigoHyo_Tab.detail.sortKotei();
    };

    /**
    *end row to insert new
    */
    HaigoHyo_Tab.detail.endRow = function (tbody) {
        var element = HaigoHyo_Tab.detail.element.find(".datatable"),
            endRow = tbody.nextAll(".row-kotei");

        if (endRow.length == 0) {
            endRow = element.find(".item_total_kotei_tml");
        }

        return endRow.prev();
    };

    /**
    * move column data table
    */
    HaigoHyo_Tab.detail.moveColumn = function (table, from, to) {
        var cols;
        jQuery('tr', table).each(function () {
            cols = jQuery(this).children('th, td');
            cols.eq(from).detach().insertBefore(cols.eq(to));
        });
    }

    /**
    * 試作列移動→
    */
    HaigoHyo_Tab.detail.moveColumnToRight = function () {

        App.ui.loading.show();
        setTimeout(function () {

            var element = HaigoHyo_Tab.detail.element,
                dt_head = HaigoHyo_Tab.detail.elementShisakuHead,
                head_check = dt_head.find('input:checked');

            //break when manual not selected
            if (head_check.length == 0) {
                App.ui.loading.close();
                return;
            }

            var table = element.find(".dt-container .flow-container table"),
                iColMove = dt_head.find("input").index(head_check),
                countManual = dt_head.find("input").length - 1;

            //break when manual selected is last manual
            if (iColMove == countManual) {
                App.ui.loading.close();
                return;
            }

            //フラグ変更画面
            page.values.isChange = true;

            //move column
            HaigoHyo_Tab.detail.moveColumn(table, iColMove + 1, iColMove);

            //move column tokusechi tab and genkashisan tab
            if (App.isFunc(HaigoHyo_Tab.moveSample) && head_check.closest("th").attr("index-col")) {
                HaigoHyo_Tab.moveSample(head_check.closest("th").attr("index-col"), true);
            }

            //resort all col
            HaigoHyo_Tab.detail.sortShisaku(iColMove, 1);
            App.ui.loading.close();
        }, 1);
    }

    /**
    * 試作列移動←
    */
    HaigoHyo_Tab.detail.moveColumnToLeft = function () {

        App.ui.loading.show();
        setTimeout(function () {

            var element = HaigoHyo_Tab.detail.element,
                dt_head = HaigoHyo_Tab.detail.elementShisakuHead,
                head_check = dt_head.find('input:checked');

            //break when manual not selected
            if (head_check.length == 0) {
                App.ui.loading.close();
                return;
            }

            var table = element.find(".dt-container .flow-container table"),
                iColMove = dt_head.find("input").index(head_check);

            //break when manual selected is first manual
            if (!iColMove || iColMove <= 1) {
                App.ui.loading.close();
                return;
            }

            //フラグ変更画面
            page.values.isChange = true;

            //move column
            HaigoHyo_Tab.detail.moveColumn(table, iColMove, iColMove - 1);

            //move column tokusechi tab and genkashisan tab
            if (App.isFunc(HaigoHyo_Tab.moveSample) && head_check.closest("th").attr("index-col")) {
                HaigoHyo_Tab.moveSample(head_check.closest("th").attr("index-col"), false);
            }

            //resort all col
            HaigoHyo_Tab.detail.sortShisaku(iColMove - 1, 1);
            App.ui.loading.close();
        }, 1);
    }

    /**
    * 試作列削除
    */
    HaigoHyo_Tab.detail.delTrialManual = function () {
        var element = HaigoHyo_Tab.detail.element,
            dt_head = HaigoHyo_Tab.detail.elementShisakuHead,
            head_check = dt_head.find('input:checked'),
            htmlSrc, itemSample;

        if (head_check.length == 0) {
            App.ui.loading.close();
            return;
        }

        var thcheck = element.find(head_check).closest("th"),
            thSiki = thcheck.find(".siki_keisan_head").text(),
            indexCol = thcheck.attr("index-col");

        //「原価試作依頼されているため工程削除できません。 (AP0033)」メッセージを表示して削除しない
        if (thcheck.findP("flg_shisanIrai").text() == HaigoHyo_Tab.values.flg_shisanIrai) {
            App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0033, { strName: "列削除" })).remove();
            App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0033, { strName: "列削除" })).show();
            return;
        }

        //※他列の計算で使用されている場合、				
        //「他列の計算で使用されています。削除しますか？(AP0034)」確認メッセージを表示		
        //削除OKの場合、チェックされた列のみ削除し、孫列は以後計算の対象としない		
        var siki_keisan_head = dt_head.find(".siki_keisan_head"),
            options = {
                text: App.str.format(App.messages.app.AP0027, { name: " 試作列" })
            };

        if (siki_keisan_head.length) {
            var mess = undefined;
            $.each(siki_keisan_head, function () {
                var siki_keisan = dt_head.find(this).text();
                if (siki_keisan.indexOf("col-" + indexCol) >= 0 || (thSiki.length && siki_keisan.indexOf(thSiki) >= 0 && siki_keisan.replace(thSiki, "") != "")) {
                    options.text = App.messages.app.AP0034;
                    return false;
                }
            })
        }

        //「試作列の削除を行います。よろしいですか？(AP0027)」確認メッセージ表示			
        //はい　　→		
        //いいえ　→　処理中断	
        HaigoHyo_Tab.confirmDialog(options).then(function () {
            //remove mess error
            element.find(".sample-" + indexCol).find(".has-error, .error").each(function (i, elem) {
                App.ui.page.notifyAlert.remove(elem);
            })

            App.ui.loading.show();
            setTimeout(function () {

                //フラグ変更画面
                page.values.isChange = true;

                var table = element.find(".dt-container .flow-container table"),
                    iColDel = dt_head.find("input").index(head_check),
                    cols, flg_delSample = false;

                var id, target, entity, ipQuantity, dataProp;
                jQuery('tr', table).each(function () {
                    cols = jQuery(this).children('th, td');
                    htmlSrc = cols.eq(iColDel);

                    ipQuantity = htmlSrc.find(".quantity");
                    dataProp = ipQuantity.attr("data-prop");

                    if (dataProp && dataProp.indexOf("quantity_") >= 0) {

                        id = ipQuantity.attr("data-key");
                        entity = HaigoHyo_Tab.detail.dataShisakuList.entry(id);

                        HaigoHyo_Tab.detail.dataShisakuList.remove(entity);

                        if (flg_delSample == false) {
                            id = ipQuantity.closest("td").attr("data-key");
                            if (!App.isUndefOrNull(id)) {
                                flg_delSample = true;
                                entity = HaigoHyo_Tab.detail.dataShisaku.entry(id);
                                itemSample = entity;
                                HaigoHyo_Tab.detail.dataShisaku.remove(entity);

                                //remove item key in list
                                if (HaigoHyo_Tab.dataKeySample["sample-" + entity.seq_shisaku]) {
                                    delete HaigoHyo_Tab.dataKeySample["sample-" + entity.seq_shisaku];
                                }
                            }
                        }
                    };

                    htmlSrc.detach();
                });

                //テーブルでサイズ変更
                HaigoHyo_Tab.detail.resizeTable(0);

                if (App.isFunc(HaigoHyo_Tab.delSample)) {
                    HaigoHyo_Tab.delSample(itemSample);
                }

                //check end manual
                HaigoHyo_Tab.detail.checkTrialManualExit(iColDel);

                App.ui.loading.close();

            }, 1);
        })
    };

    /**
    * 試作がありませんから試作列追加をする。
    */
    HaigoHyo_Tab.detail.checkTrialManualExit = function (indexColDel) {
        var element = HaigoHyo_Tab.detail.element,
            dt_head = HaigoHyo_Tab.detail.elementShisakuHead,
            head_check = dt_head.find('.input-tmpl');

        if (head_check.length == 1) {
            //add new col
            HaigoHyo_Tab.detail.options.maxSeqShisaku = 0;
            HaigoHyo_Tab.detail.addNewTrialManual();
        } else {

            //resort all col
            HaigoHyo_Tab.detail.sortShisaku(indexColDel, 1);
        }
    };

    /**
    * Specifies the column to change the order.
    */
    HaigoHyo_Tab.detail.sortShisaku = function (indexCol, indexPrev) {
        var element = HaigoHyo_Tab.detail.element,
            dt_head = HaigoHyo_Tab.detail.elementShisakuHead,
            sort_shisaku = 1;

        //get index preview col selected
        if ((indexCol - indexPrev) > 0) {

            var col = HaigoHyo_Tab.detail.elementShisakuHead.find(dt_head.find("input")[indexCol - indexPrev]).closest("th"),
                id = col.attr("data-key"),
                entity;

            if (App.isUndefOrNull(id)) {
                return;
            }
            entity = HaigoHyo_Tab.detail.dataShisaku.entry(id);
            sort_shisaku = entity.sort_shisaku + 1;
        }

        //update next col
        while (dt_head.find("input")[indexCol]) {
            sort_shisaku += HaigoHyo_Tab.detail.sortShisakuList(dt_head.find("input")[indexCol], sort_shisaku);
            indexCol += 1;
        }
    };

    /**
    * update all level shisaku.
    */
    HaigoHyo_Tab.detail.sortShisakuList = function (col, sort_shisaku) {
        col = HaigoHyo_Tab.detail.elementShisakuHead.find(col).closest("th");

        var id = col.attr("data-key"),
            indexCol = col.attr("index-col"),
            entity;

        if (App.isUndefOrNull(id)) {
            return 0;
        }

        entity = HaigoHyo_Tab.detail.dataShisaku.entry(id);

        entity.sort_shisaku = sort_shisaku;
        HaigoHyo_Tab.detail.dataShisaku.update(entity);

        return 1;
    }

    /**
    *　試作列追加。
    */
    HaigoHyo_Tab.detail.addNewTrialManual = function () {
        var isEndCol = true,
            isNewData = true;

        HaigoHyo_Tab.detail.options.maxSeqShisaku += 1;
        var dataSample = {
            cd_shain: page.values.cd_shain,
            nen: page.values.nen,
            no_oi: page.values.no_oi,
            gokei: "0.000",//合計量（1本：g）（容量ｘ比重）
            hiju: "0.000",//比重
            reberu: "0.00",//レベル量（ｇ内容量ｘ入数）
            hizyubudomari: "0.00",//レベル量（ｇ内容量ｘ入数）
            zyusui: "0.00",//充填量水相（g）（※）
            zyuabura: "0.00",//充填量油相（g）（※）
            flg_print: 0,//印刷Flg
            flg_auto: 0,//自動計算Flg
            //flg_sousan: 0,//総酸-出力Flg
            //ritu_sousan: "0.00",
            //flg_shokuen: 0,//食塩-出力Flg
            //ritu_shokuen: "0.00",
            //flg_sando_suiso: 0,//水相中酸度-出力Flg
            //flg_shokuen_suiso: 0,//水相中食塩-出力Flg
            //flg_sakusan_suiso: 0,//水相中酢酸-出力Flg
            //flg_sakusan_suiso: 0,//水相中酢酸-出力Flg
            msg_suiso: "0.00",
            //sando_suiso: "0.00",
            flg_toudo: 0,//糖度-出力Flg
            flg_nendo: 0,//粘度-出力Flg
            flg_ondo: 0,//温度-出力Flg
            flg_no_rotor: 0,//粘度ローターNo-Flg
            flg_speed: 0,//粘度スピード-Flg
            flg_nendo_cream: 0,//充填前（クリーム）粘度-Flg
            flg_ondo_cream: 0,//充填前（クリーム）粘度測定温度-Flg
            flg_no_rotor_cream: 0,//充填前（クリーム）粘度ローターNo-Flg
            flg_speed_cream: 0,//充填前（クリーム）粘度スピード-Flg
            flg_ph: 0,//PH-出力Flg
            flg_sousan_bunseki: 0,//総酸：分析-出力Flg
            flg_shokuen_bunseki: 0,//食塩：分析-出力Flg
            //shokuen_suiso: "0.00",
            flg_sando_bunseki_suiso: 0,//水相中総酸：分析-出力Flg
            //sakusan_suiso: "0.00",
            flg_shokuen_bunseki_suiso: 0,//水相中食塩：分析-出力Flg
            flg_hiju: 0,//比重-出力Flg
            flg_memo: 0,//作成メモ-出力Flg
            flg_hyoka: 0,//評価-出力Flg
            flg_hiju_sui: 0,//水相比重-出力Flg
            flg_jikkoSakusanNodo: 0,//実効酢酸濃度Flg
            jikkoSakusanNodo: "0.00",
            flg_jikkoHikairiSakusanSando: 0,//水相中非解離酢酸酸度Flg
            jikkoHikairiSakusanSando: "0.00",
            flg_msg_suiso: 0,//水相中ＭＳＧFlg
            flg_rank_biseibutsu: 0,//微生物ランクFlg
            flg_haigo_kyodo: 0,//配合強度Flg
            flg_oilmustard: 0,//製品オイルマスタード含有量Flg
            oilmustard: "0",
            flg_Brix: 0,//BrixFlg
            flg_AW: 0,//AWFlg
            flg_free1: 0,//フリー①-出力Flg
            flg_free2: 0,//フリー②-出力Flg
            flg_free3: 0,//フリー③-出力Flg
            flg_free4: 0,//フリー④-出力Flg
            flg_free5: 0,//フリー⑤-出力Flg
            flg_free6: 0,//フリー⑥-出力Flg
            flg_free7: 0,//フリー⑦-出力Flg
            flg_free8: 0,//フリー⑧-出力Flg
            flg_free9: 0,//フリー⑨-出力Flg
            flg_free10: 0,//フリー⑩-出力Flg
            flg_free11: 0,//フリー⑪-出力Flg
            flg_free12: 0,//フリー⑫-出力Flg
            flg_free13: 0,//フリー⑬-出力Flg
            flg_free14: 0,//フリー⑭-出力Flg
            flg_free15: 0,//フリー⑮-出力Flg
            flg_free16: 0,//フリー⑯-出力Flg
            flg_free17: 0,//フリー⑰-出力Flg
            flg_free18: 0,//フリー⑱-出力Flg
            flg_free19: 0,//フリー⑲-出力Flg
            flg_free20: 0,//フリー⑳-出力Flg
            seq_shisaku: HaigoHyo_Tab.detail.options.maxSeqShisaku,//試作SEQ
            sort_shisaku: HaigoHyo_Tab.detail.options.maxSeqShisaku//試作表示順
        };

        //kotei mode edit
        if (page.values.modeDisp == App.settings.app.m_shisaku_data.shosai) {
            dataSample.cd_shain = page.values.cd_shain;
            dataSample.nen = page.values.nen;
            dataSample.no_oi = page.values.no_oi;
        }

        HaigoHyo_Tab.detail.addNewCol(isEndCol, dataSample, isNewData, false);
    };

    /**
    *　試作列追加。
    * isEndCol : true || fale
    * isEndCol = true : insert new col to after end col
    * isEndCol = fale : insert new col to first
    */
    HaigoHyo_Tab.detail.addNewCol = function (isEndCol, itemSample, isNewData, dataCopy) {

        //単位がmLの場合のみ表示、必須項目となる単位がgなら１
        var shisakuHin = HaigoHyo_Tab.getDataHeader();
        if (shisakuHin.cd_tani == App.settings.app.cd_tani.gram) {
            itemSample.hiju = "1.000";//	製品比重;
        } else {
            itemSample.hiju = null;//	製品比重;
        }

        //特性値の分析値を毎回自動計算をもらいする。
        //itemSample.flg_auto = HaigoHyo_Tab.autoFlagCheck();

        var element = HaigoHyo_Tab.detail.element,
            dt_head = HaigoHyo_Tab.detail.elementShisakuHead,
            head_check = dt_head.find('.th-tmpl .input-tmpl'),
            iColCopy = 0, iColInset = 0, indexCol = undefined;

        if (head_check.length == 0) {
            return;
        }

        var table = element.find(".dt-container .flow-container table"),
            cols, htmlSrc;

        //insert new col after end col
        if (isEndCol == true) {

            iColCopy = dt_head.find("input").index(head_check);
            iColInset = dt_head.find("input").length - 1;
        } else {

            head_check = dt_head.find('input:checked');
            iColInset = dt_head.find("input").index(head_check);

            indexCol = head_check.closest("th").attr("index-col");
        }

        //insert new col
        var ipQuantity, keyHaigo;

        jQuery('tr', table).each(function () {
            cols = jQuery(this).children('th, td');
            htmlSrc = cols.eq(iColCopy).clone().attr("index-col", itemSample.seq_shisaku).addClass("sample-" + itemSample.seq_shisaku).removeClass("th-tmpl");

            ipQuantity = htmlSrc.find(".quantity");
            dataProp = ipQuantity.attr("data-prop");

            if (dataProp && dataProp.indexOf("quantity_") >= 0) {
                var dataQuanlity = {
                    cd_shain: itemSample.cd_shain,
                    nen: itemSample.nen,
                    no_oi: itemSample.no_oi
                };

                idHaigo = element.find(cols.eq(iColCopy)).closest("tbody").attr("data-key");
                entityHaigo = HaigoHyo_Tab.detail.dataHaigo.entry(idHaigo);

                dataQuanlity.cd_kotei = entityHaigo.cd_kotei;
                dataQuanlity.seq_kotei = entityHaigo.seq_kotei;
                dataQuanlity.seq_shisaku = itemSample.seq_shisaku;

                (isNewData ? HaigoHyo_Tab.detail.dataShisakuList.add : HaigoHyo_Tab.detail.dataShisakuList.attach).bind(HaigoHyo_Tab.detail.dataShisakuList)(dataQuanlity);
                ipQuantity.attr("data-key", dataQuanlity.__id);

                //renew data prop
                ipQuantity.attr("data-prop", dataProp + "_" + itemSample.seq_shisaku).attr("data-number-format-toFixed", Number(page.values.keta_shosu));
                HaigoHyo_Tab.detail.options.validationsShisakuList[dataProp + "_" + itemSample.seq_shisaku] = HaigoHyo_Tab.values.validationsCellDefault;
            }

            if (dataProp && dataProp.indexOf("total_finish_weight") === 0) {
                ipQuantity.attr("data-number-format-toFixed", Number(page.values.keta_shosu));
            }

            htmlSrc.insertAfter(cols.eq(iColInset));
        });

        (isNewData ? HaigoHyo_Tab.detail.dataShisaku.add : HaigoHyo_Tab.detail.dataShisaku.attach).bind(HaigoHyo_Tab.detail.dataShisaku)(itemSample);
        //bind data for col
        var elementCol = element.find(".sample-" + itemSample.seq_shisaku);
        elementCol.form().bind(itemSample);

        //コピーと閲覧	
        if (page.values.modeDisp == App.settings.app.m_shisaku_data.etsuran || page.values.modeDisp == App.settings.app.m_shisaku_data.copy) {
            //何もしない
        } else {
            elementCol.find(":input[data-role='date']").datepicker({ dateFormat: "yy/mm/dd" });
            elementCol.find(":input[data-role='date']").on("keyup", App.data.addSlashForDateString);
        }

        //elementCol.find(".item_total_cacu").val(0);
        //elementCol.findP("juryo_shiagari_g").attr("data-number-format-toFixed", "null").attr("toFixedMin", "4");

        HaigoHyo_Tab.dataKeySample["sample-" + itemSample.seq_shisaku] = itemSample.__id;

        //renew validate list
        var validations = $.extend(true, {}, HaigoHyo_Tab.detail.options.validations, HaigoHyo_Tab.detail.options.validationsShisakuList, HaigoHyo_Tab.detail.options.validationsTotal);
        HaigoHyo_Tab.detail.validator = HaigoHyo_Tab.detail.element.validation(HaigoHyo_Tab.createValidator(validations));

        //テーブルでサイズ変更
        HaigoHyo_Tab.detail.resizeTable(1);

        if (App.isFunc(HaigoHyo_Tab.addSample)) {
            //試作列コピー。
            if (dataCopy) {
                if (dataCopy.flg_keisan) {
                    //計算式
                    itemSample.siki_keisan = null;
                    HaigoHyo_Tab.detail.dataShisaku.update(itemSample);
                }
                var valuejuryo_shiagari_g,
                    roundPosition = fixedPosition = element.attr("data-number-format-tofixed"),
                    formatDefault = new BigNumber(0).toFixed(roundPosition);

                if (dataCopy.flg_keisan == 1 || itemSample.juryo_shiagari_g == null || itemSample.juryo_shiagari_g === "" || itemSample.juryo_shiagari_g == undefined) {
                    formatDefault = "";
                }

                
                valuejuryo_shiagari_g = page.detail.formatNumber(itemSample.juryo_shiagari_g, roundPosition, formatDefault);

                itemSample.juryo_shiagari_g = valuejuryo_shiagari_g.forData;
                elementCol.findP("juryo_shiagari_g").val(valuejuryo_shiagari_g.forDisp);
                HaigoHyo_Tab.detail.setDataCopy(elementCol, dataCopy, elementCol.findP("juryo_shiagari_g"));
            }

            var isNewData = true;

            HaigoHyo_Tab.addSample(itemSample, isEndCol, isNewData, indexCol, dataCopy);
        }

        //remove input siki_keisan
        if (!itemSample.siki_keisan || App.isUndefOrNull(itemSample.siki_keisan) || itemSample.siki_keisan == "" || !itemSample.siki_keisan.length) {
            elementCol.find(".siki_keisan, .siki_keisan_head, .flg_keisan").remove();
        }

        //resort all col
        HaigoHyo_Tab.detail.sortShisaku(iColInset, 1);
    }

    /**
    *　試作列コピー。
    */
    HaigoHyo_Tab.detail.setDataCopy = function (elementCol, dataCopy, juryo_shiagari_g) {
        var validate = function (targets) {
            return HaigoHyo_Tab.detail.validator.validate({
                targets: targets,
                state: {
                    tbody: targets,
                    isGridValidation: true
                }
            });
        };

        $.each(dataCopy, function (prop, value) {
            //量
            if (prop.indexOf("quantity_") == 0) {
                var target = elementCol.findP(prop),
                    id = target.attr("data-key");

                if (App.isUndefOrNull(id)) {
                    return true;
                }

                value = page.detail.formatNumber(value, Number(page.values.keta_shosu));

                var entity = HaigoHyo_Tab.detail.dataShisakuList.entry(id);
                entity["quantity"] = value.forData;
                HaigoHyo_Tab.detail.dataShisakuList.update(entity);

                target.val(value.forDisp);
                validate(target);
            }

            //工程（kg）,合計重量（kg）
            if (prop.indexOf("total_kotei") == 0 || prop.indexOf("total_weight") == 0) {
                value = page.detail.formatNumber(value, Number(page.values.keta_shosu));
                elementCol.findP(prop).val(value.forDisp);

                validate(elementCol.findP(prop));
            }

            //工程仕上重量（kg）
            if (dataCopy.flg_keisan != 1 && prop.indexOf("total_finish_weight") == 0) {
                var formatDefault = "0.0000";
                if (value == null || value === "" || value == undefined) {
                    formatDefault = "";
                }

                value = page.detail.formatNumber(value, HaigoHyo_Tab.detail.getDisplayPosition("total_finish_weight"), formatDefault);
                elementCol.findP(prop).val(value.forDisp);

                validate(elementCol.findP(prop));

                var cd_kotei = prop.replace("total_finish_weight", "");
                shisakuItem = elementCol.find(".quantity_" + cd_kotei);

                var zoku_kotei = HaigoHyo_Tab.detail.getZokuKoteiByCdKotei(cd_kotei);
                if (HaigoHyo_Tab.detail.isBettenKotei(zoku_kotei)) {
                    elementCol.findP(prop).addClass("betten-kotei");
                } else {
                    elementCol.findP(prop).removeClass("betten-kotei");
                }

                if (!shisakuItem.length) {
                    return true;
                }

                var index = 0;
                while (shisakuItem[index]) {
                    id = elementCol.find(shisakuItem[index]).attr("data-key");

                    if (App.isUndefOrNull(id)) {
                        index += 1;
                        continue;
                    }

                    entity = HaigoHyo_Tab.detail.dataShisakuList.entry(id);
                    entity["juryo_shiagari_seq"] = value.forData;

                    HaigoHyo_Tab.detail.dataShisakuList.update(entity);
                    index += 1;
                }
            }
        })

        validate(juryo_shiagari_g);
    };

    /**
    *　試作列追加。
    * isEndCol = true || fale
    * isEndCol = true : insert new col to after end col
    * isEndCol = fale : insert new col to first
    */
    HaigoHyo_Tab.detail.addTrialManual = function (isEndCol, itemSample, isNewData) {
        var element = HaigoHyo_Tab.detail.element,
            dt_head = HaigoHyo_Tab.detail.elementShisakuHead,
            head_check = dt_head.find('.th-tmpl .input-tmpl'),
            iColCopy = 0, iColInset = 0;

        if (head_check.length == 0) {
            return;
        }

        var table = element.find(".dt-container .flow-container table"),
            cols, htmlSrc;

        //insert new col after end col
        if (isEndCol == true) {
            iColCopy = dt_head.find("input").index(head_check);
            iColInset = dt_head.find("input").length - 1;
        }

        //insert new col
        var ipQuantity, dataProp;

        jQuery('tr', table).each(function () {
            cols = jQuery(this).children('th, td');
            htmlSrc = cols.eq(iColCopy).clone().attr("index-col", itemSample.seq_shisaku).addClass("sample-" + itemSample.seq_shisaku).removeClass("th-tmpl");

            ipQuantity = htmlSrc.find(".quantity");
            dataProp = ipQuantity.attr("data-prop");

            if (dataProp && dataProp.indexOf("quantity_") >= 0) {
                idHaigo = element.find(cols.eq(iColCopy)).closest("tbody").attr("data-key");
                entityHaigo = HaigoHyo_Tab.detail.dataHaigo.entry(idHaigo);

                ipQuantity.attr("data-key", HaigoHyo_Tab.detail.options.dataKeyQuantity[
                [
                    entityHaigo.cd_shain,
                    entityHaigo.nen,
                    entityHaigo.no_oi,
                    itemSample.seq_shisaku,
                    entityHaigo.cd_kotei,
                    entityHaigo.seq_kotei
                ].join("-")]);

                ipQuantity.css("background", HaigoHyo_Tab.detail.options.dataKeyQuantity[
                [
                    entityHaigo.cd_shain,
                    entityHaigo.nen,
                    entityHaigo.no_oi,
                    itemSample.seq_shisaku,
                    entityHaigo.cd_kotei,
                    entityHaigo.seq_kotei,
                    "color"
                ].join("-")]);
                ipQuantity.css("color", HaigoHyo_Tab.lightOrDark(HaigoHyo_Tab.detail.options.dataKeyQuantity[
                [
                    entityHaigo.cd_shain,
                    entityHaigo.nen,
                    entityHaigo.no_oi,
                    itemSample.seq_shisaku,
                    entityHaigo.cd_kotei,
                    entityHaigo.seq_kotei,
                    "color"
                ].join("-")]));

                //renew data prop
                ipQuantity.attr("data-prop", dataProp + "_" + itemSample.seq_shisaku).attr("data-number-format-toFixed", Number(page.values.keta_shosu));
                HaigoHyo_Tab.detail.options.validationsShisakuList[dataProp + "_" + itemSample.seq_shisaku] = HaigoHyo_Tab.values.validationsCellDefault;
            }
            htmlSrc.insertAfter(cols.eq(iColInset));
        });

        (isNewData ? HaigoHyo_Tab.detail.dataShisaku.add : HaigoHyo_Tab.detail.dataShisaku.attach).bind(HaigoHyo_Tab.detail.dataShisaku)(itemSample);
        //bind data for col
        var elementCol = element.find(".sample-" + itemSample.seq_shisaku);
        elementCol.form(HaigoHyo_Tab.detail.options.sampleBindOption).bind(itemSample);

        //コピーと閲覧	
        if (page.values.modeDisp == App.settings.app.m_shisaku_data.etsuran || page.values.modeDisp == App.settings.app.m_shisaku_data.copy) {
            //何もしない
        } else {
            elementCol.find(":input[data-role='date']").datepicker({ dateFormat: "yy/mm/dd" });
            elementCol.find(":input[data-role='date']").on("keyup", App.data.addSlashForDateString);
        }

        //原価試作依頼されている列は値を変更できない（空白行も）		
        if (itemSample.flg_shisanIrai == HaigoHyo_Tab.values.flg_shisanIrai) {
            elementCol.find(":input").not(".input-shisaku").prop("disabled", true);
        }

        elementCol.find(".input-tmpl").prop("disabled", false);
        //elementCol.findP("juryo_shiagari_g").attr("data-number-format-toFixed", "null").attr("toFixedMin", "4");

        //remove input siki_keisan
        if (!itemSample.siki_keisan || App.isUndefOrNull(itemSample.siki_keisan) || itemSample.siki_keisan == "" || !itemSample.siki_keisan.length) {
            elementCol.find(".siki_keisan, .siki_keisan_head, .flg_keisan").remove();
        }

        //add data key
        HaigoHyo_Tab.dataKeySample["sample-" + itemSample.seq_shisaku] = itemSample.__id;
    };

    /**
    * マスターデータのロード処理を実行します。
    */
    HaigoHyo_Tab.loadMasterData = function () {

        var no_chui = HaigoHyo_Tab.element.findP("no_chui");
        no_chui.children().remove();
        App.ui.appendOptions(
            no_chui,
            "no_chui",
            "no_chui",
            page.values.masterData.CyuuiBango,
            true
        );
        // 単位
        var tani = HaigoHyo_Tab.element.findP("cd_tani");
        tani.children().remove();
        App.ui.appendOptions(
            tani,
            "cd_literal",
            "nm_literal",
            page.values.masterData.GenryoTani,
            false
        );

        var zoku_kotei = HaigoHyo_Tab.element.findP("zoku_kotei");
        zoku_kotei.children().remove();
        App.ui.appendOptions(
            zoku_kotei,
            "cd_literal",
            "nm_literal",
            page.values.masterData.KoteiZokusei,
            true
        );

        var dataSet = App.ui.page.dataSet(),
            isNewData = false;

        HaigoHyo_Tab.detail.dataChui = dataSet;
        $.each(page.values.masterData.CyuuiBango, function (index, item) {
            (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
        })

        HaigoHyo_Tab.changeKoteiPatan(HaigoHyo_Tab.values.pt_kotei);

        return $.ajax(App.ajax.odata.get(App.str.format(HaigoHyo_Tab.urls.literal, App.settings.app.cd_category.tanka_hyoujigaisha, App.settings.app.tanka_hyoji.hyoji1, App.settings.app.tanka_hyoji.hyoji9))).then(function (result) {
            HaigoHyo_Tab.values.taniHyoji = result.value;
        })
    };

    /**
     * ダイアログの検索処理を実行します。
     */
    HaigoHyo_Tab.detail.search = function (isLoading) {
        var element = HaigoHyo_Tab.element,
            deferred = $.Deferred(),
            dataShisahin = HaigoHyo_Tab.getDataHeader(),
            query;
        var shisakuHin = HaigoHyo_Tab.getDataHeader();
        query = {
            cd_shain: page.values.cd_shain,
            nen: page.values.nen,
            no_oi: page.values.no_oi,
            tanka_hyoujigaisha: App.settings.app.cd_category.tanka_hyoujigaisha,
            hyoji1: App.settings.app.tanka_hyoji.hyoji1,
            hyoji9: App.settings.app.tanka_hyoji.hyoji9,
            //daihyo_kaisha: App.settings.app.CD_DAIHYO_KAISHA.kewpie,
            daihyo_kaisha: shisakuHin.cd_kaisha,
            //daihyo_kojo: App.settings.app.CD_DAIHYO_KOJO.kewpie_kenkyu_kaihatsu,
            daihyo_kojo: shisakuHin.cd_kojo,
            keta_shosu: page.values.keta_shosu,
            seq_shisaku: dataShisahin.seq_shisaku
        };

        HaigoHyo_Tab.detail.dataTable.dataTable("clear");

        $.ajax(App.ajax.webapi.get(HaigoHyo_Tab.urls.search, query)).done(function (result) {
            HaigoHyo_Tab.detail.bindHaigo(result, false);
            deferred.resolve();
        }).fail(function (error) {
            App.ui.page.notifyWarn.message(App.ajax.handleError(error).message).show();
            deferred.reject();
            App.ui.loading.close();
        }).always(function () {

            if (!element.find(".save").is(":disabled")) {
                element.find(".save").prop("disabled", true);
            }
        });

        return deferred.promise();
    };

    /**
    *・原料コードが「999999」かつ配合量が空白ではない場合、エラーメッセージ表示
    */
    HaigoHyo_Tab.checkCommandcode = function (value, param, otps, done) {

        //イベント保存ボタンではない
        //コマンドコードではありません
        if (!otps || !otps.isSave || !otps.isCheckIrai) {
            return true;
        }

        var tbody = otps.tbody.element ? otps.tbody.element : otps.tbody;
        cd_genryo = tbody.findP("cd_genryo").val();

        if (cd_genryo != App.settings.app.commentCode) {
            return true;
        }

        //check null
        var isEmpty = function (val) {
            return App.isUndef(val) || App.isNull(val) || (App.isStr(val) && val.length === 0);
        }

        //not new check irai
        if (!value.length) {
            return true;
        }

        if (!isEmpty(value)) {
            return false;
        }

        return true;
    };

    /**
    *・原料コードが「N」頭ではなく、かつ、原料名の先頭が「★」である場合
    */
    HaigoHyo_Tab.checkKuroiSuta = function (value, param, otps, done) {
        //イベント保存ボタンではない
        if (!otps || !otps.isSave || !otps.isCheckIrai) {
            return true;
        }

        var tbody = otps.tbody.element ? otps.tbody.element : otps.tbody;
        cd_genryo = tbody.findP("cd_genryo").val();
        //check null
        var isEmpty = function (val) {
            return App.isUndef(val) || App.isNull(val) || (App.isStr(val) && val.length === 0);
        }

        //genryo emty
        if (isEmpty(cd_genryo)) {
            return true;
        }

        //「N」頭
        if (HaigoHyo_Tab.detail.genryoBCheck(cd_genryo)) {
            return true;
        }

        //nm_genryoの先頭が「★」である
        var nm_genryo = tbody.findP("nm_genryo").val();

        //not new check irai
        if (isEmpty(nm_genryo) || !value.length) {
            return true;
        }
        if (nm_genryo.indexOf("★") == 0 && !isEmpty(value)) {
            //2019-10-04 : START : Bug #15484 (★ｹｲﾊﾟｯｸ向け明太処理液(無着色))
            try {
                var id = tbody.attr("data-key"),
                    entry = HaigoHyo_Tab.detail.dataHaigo.entry(id);

                if (!otps.cd_kaishaHin || entry.cd_kaisha == otps.cd_kaishaHin) {
                    return true;
                }
            } catch (e) {
                return false;
            }

            //2019-10-04 : END : Bug #15484 (★ｹｲﾊﾟｯｸ向け明太処理液(無着色))
            return false;
        }

        return true;
    };

    /**
     * 試作 : ダイアログヘッダー　バリデーションルールを定義します。
     */
    HaigoHyo_Tab.detail.options.validationsShisaku = {
        dt_shisaku: {
            rules: {
                datestring: true
            },
            options: {
                name: "日付"
            },
            messages: {
                datestring: App.messages.base.datestring
            }
        },
        nm_sample: {
            rules: {
                maxlength: 200,
                check_exit: function (value, param, otps, done) {
                    var element = HaigoHyo_Tab.detail.element.find(".nm_sample_area .col-sample").not(".th-tmpl"),
                        data = element.find(".nm_sample");

                    //重複するすべてのサンプル名を数える
                    var index = 0, countExit = 0;
                    while (data[index]) {
                        if (element.find(data[index]).val() == value) {
                            countExit += 1;
                        }
                        index += 1;
                    }

                    return (done(countExit <= 1));
                },
                check_single_kotations: true
            },
            options: {
                name: "サンプルNo"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_exit: App.messages.app.AP0037,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        memo: {
            rules: {
                maxlength: 50,
                check_single_kotations: true
            },
            options: {
                name: "メモ"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        su_fc: {
            rules: {
                requiredCustom: function (value, param, otps, done) {
                    var pt_kotei = page.detail.getKoteiPattern();
                    if (pt_kotei === App.settings.app.kotei_pattern_AOH.Fixed) {
                        return done(true);
                    }
                    return done(!App.isUndefOrNullOrStrEmpty(value));
                },
                numberBug15481: true,
                min: 0,
                pointlengthBug15481: [3, 1]
            },
            options: {
                name: "FC"
            },
            messages: {
                requiredCustom: App.messages.base.required,
                numberBug15481: App.messages.base.number,
                min: App.messages.base.min,
                pointlengthBug15481: App.messages.base.pointlength
            }
        },
        su_brix: {
            rules: {
                numberBug15481: true,
                min: 0,
                pointlengthBug15481: [2, 1]
            },
            options: {
                name: "Brix(%)"
            },
            messages: {
                numberBug15481: App.messages.base.number,
                min: App.messages.base.min,
                pointlengthBug15481: App.messages.base.pointlength
            }
        },
        su_ph: {
            rules: {
                numberBug15481: true,
                min: 0,
                pointlengthBug15481: [2, 2]
            },
            options: {
                name: "pH"
            },
            messages: {
                numberBug15481: App.messages.base.number,
                min: App.messages.base.min,
                pointlengthBug15481: App.messages.base.pointlength
            }
        },
        su_ca: {
            rules: {
                numberBug15481: true,
                min: 0,
                pointlengthBug15481: [2, 2]
            },
            options: {
                name: "CA(%)"
            },
            messages: {
                numberBug15481: App.messages.base.number,
                min: App.messages.base.min,
                pointlengthBug15481: App.messages.base.pointlength
            }
        },
        juryo_shiagari_g: {
            rules: {
                numberBug15481: true,
                min: 0,
                pointlengthBug15481: [10, 4]
            },
            options: {
                name: "合計仕上重量"
            },
            messages: {
                numberBug15481: App.messages.base.number,
                min: App.messages.base.min,
                //pointlengthBug15481: App.messages.app.AP0151.replace("{name}", "合計仕上重量").replace("{param[0]}", "各工程仕上重量").replace("{param[1]}", "10").replace("{param[2]}", "4")
                pointlengthBug15481: App.messages.app.AP0201
            }
        },
        kg_costrecipe: {
            rules: {
                numberBug15481: true,
                min: 0,
                pointlengthBug15481: [8, 2]
            },
            options: {
                name: "レシピコスト（円／kg）"
            },
            messages: {
                numberBug15481: App.messages.base.number,
                min: App.messages.base.min,
                pointlengthBug15481: App.messages.app.AP0200
            }
        },
        ko_costrecipe: {
            rules: {
                numberBug15481: true,
                min: 0,
                pointlengthBug15481: [8, 2]
            },
            options: {
                name: "レシピコスト（円／個）"
            },
            messages: {
                numberBug15481: App.messages.base.number,
                min: App.messages.base.min,
                pointlengthBug15481: App.messages.app.AP0200
            }
        }
    };

    ///**
    // * ダイアログヘッダー　バリデーションルールを定義します。
    // */
    HaigoHyo_Tab.detail.options.validationsShisakuList = {
    };

    ///**
    // * ダイアログヘッダー　バリデーションルールを定義します。
    // */
    HaigoHyo_Tab.detail.options.validationsTotal = {
    };

    ///**
    // * ダイアログヘッダー　バリデーションルールを定義します。
    // */
    HaigoHyo_Tab.detail.options.validations = {
        zoku_kotei: {
            rules: {
                required: true,
                //check_exit: function (value, param, otps, done) {
                //    //not event save button
                //    if (!otps.isSave) {
                //        return done(true);
                //    }

                //    var tbody = otps.tbody.element ? otps.tbody.element : otps.tbody;

                //    //check null
                //    var isEmpty = function (val) {
                //        return App.isUndef(val) || App.isNull(val) || (App.isStr(val) && val.length === 0);
                //    }

                //    if (isEmpty(value)) {
                //        //data col checked irai
                //        var quality = tbody.find(".iraiChecked"),
                //            check = true;

                //        //not new check irai
                //        if (!quality.length) {
                //            return done(true);
                //        }

                //        return done(false);
                //    }
                //    return done(true);
                //},
                //ShohinKaihatsuSuppot Modify 2022FY: -st required when open dialog 606
                //check_required_from_606_dialog: function (value, param, otps, done) {

                //    if (!otps.is606) {
                //        return done(true);
                //    }

                //    if (!otps.ignoreKotei) {
                //        return done(true);
                //    }

                //    return done(!App.isUndefOrNullOrStrEmpty(value));
                //},
                //-ed
                duplicate: function (value, param, otps, done) {
                    var koteiList = HaigoHyo_Tab.detail.element.find("tbody.new").findP("zoku_kotei");
                    var currentKoteiCount = false;
                    $.each(koteiList, function (ind, item) {
                        if (value && value == $(item).val()) {
                            currentKoteiCount++;
                        }
                    });
                    return done(currentKoteiCount < 2);
                }
            },
            options: {
                name: "工程属性"
            },
            messages: {
                required: App.messages.base.required,
                check_exit: App.messages.base.required,
                check_required_from_606_dialog: App.messages.base.required,
                duplicate: App.messages.app.AP0198
            }
        },
        dt_shisaku: {
            rules: {
                datestring: true
            },
            options: {
                name: "日付"
            },
            messages: {
                datestring: App.messages.base.datestring
            }
        },
        nm_sample: {
            rules: {
                maxlength: 200,
                check_exit: function (value, param, otps, done) {
                    var element = HaigoHyo_Tab.detail.element.find(".nm_sample_area .col-sample").not(".th-tmpl"),
                        data = element.find(".nm_sample");

                    //重複するすべてのサンプル名を数える
                    var index = 0, countExit = 0;
                    while (data[index]) {
                        if (element.find(data[index]).val() == value) {
                            countExit += 1;
                        }
                        index += 1;
                    }

                    return (done(countExit <= 1));
                },
                check_single_kotations: true
            },
            options: {
                name: "サンプルNo"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_exit: App.messages.app.AP0037,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        memo: {
            rules: {
                maxlength: 50,
                check_single_kotations: true
            },
            options: {
                name: "メモ"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        cd_genryo: {
            rules: {
                haneisukigo: true,
                check_single_kotations: true,
                //分量に入力があるでcd_genryoが空欄ではないこと
                checkRequired: function (value, param, otps, done) {
                    //not event save button
                    if (!otps.isSave) {
                        return done(true);
                    }

                    var tbody = otps.tbody.element ? otps.tbody.element : otps.tbody;
                    //check null
                    var isEmpty = function (val) {
                        return App.isUndef(val) || App.isNull(val) || (App.isStr(val) && val.length === 0);
                    }

                    if (isEmpty(value)) {
                        //data col checked irai
                        var quality = tbody.find(".iraiChecked"),
                            check = true;

                        //not new check irai
                        if (!quality.length) {
                            return done(true);
                        }

                        //check exit quality
                        $.each(quality, function (index, item) {
                            value = tbody.find(item).val();
                            if (!isEmpty(value)) {
                                check = false;
                                return false;
                            }
                        })

                        return done(check);
                    }

                    return done(true);
                },
                //原料コードが「999999」かつ配合量が空白ではない場合、エラーメッセージ表示
                //checkCommandcode: function (value, param, otps, done) {

                //    //イベント保存ボタンではない
                //    //コマンドコードではありません
                //    if (!otps.isSave || value != App.settings.app.commentCode) {
                //        return done(true);
                //    }

                //    var tbody = otps.tbody.element ? otps.tbody.element : otps.tbody;
                //    //check null
                //    var isEmpty = function (val) {
                //        return App.isUndef(val) || App.isNull(val) || (App.isStr(val) && val.length === 0);
                //    }

                //    var quality = tbody.find(".iraiChecked");

                //    //not new check irai
                //    if (!quality.length) {
                //        return done(true);
                //    }

                //    //check exit quality
                //    var check = false;
                //    $.each(quality, function (index, item) {
                //        value = tbody.find(item).val();
                //        if (!isEmpty(value)) {
                //            check = true;
                //            return false;
                //        }
                //    })

                //    if (check) {
                //        return done(false);
                //    }

                //    return done(true);
                //},
            },
            options: {
                name: "原料コード"
            },
            messages: {
                haneisukigo: App.messages.base.haneisukigo,
                check_single_kotations: App.messages.app.AP0153,
                checkRequired: App.messages.app.AP0075,
                //checkCommandcode: App.messages.app.AP0076
            }
        },
        nm_kotei: {
            rules: {
                maxlength: 60,
                check_single_kotations: true
            },
            options: {
                name: "工程名"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        nm_genryo: {
            rules: {
                maxlength: 60,
                check_single_kotations: true
            },
            options: {
                name: "原料名"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        tanka: {
            rules: {
                numberBug15481: true,
                min: 0,
                pointlength_custom: function (value, param, otps, done) {
                    value = value == "." ? "" : value;
                    var check = page.numPointlength(value, [8, 10]);
                    return done(check);
                },
                //ShohinKaihatsuSuppot Modify 2022FY: -st required when open dialog 606
                //check_required_from_606_dialog: function (value, param, otps, done) {

                //    if (!otps.is606) {
                //        return done(true);
                //    }

                //    var tbody = otps.tbody.element ? otps.tbody.element : otps.tbody;
                //    var inputCheck = tbody.hasClass("row-kotei");

                //    if (inputCheck || otps.ignoreBlankQuantity) {
                //        return done(true);
                //    }

                //    return done(!App.isUndefOrNullOrStrEmpty(value));
                //}
                //-ed
            },
            options: {
                name: "単価"
            },
            messages: {
                numberBug15481: App.messages.base.number,
                min: App.messages.base.min,
                pointlength_custom: App.str.format(App.messages.base.pointlength, { name: "単価", param: [8, 2] }),
                //check_required_from_606_dialog: App.messages.base.required
            }
        },
        budomari: {
            rules: {
                numberBug15481: true,
                min: 0,
                pointlength_custom: function (value, param, otps, done) {
                    value = value == "." ? "" : value;
                    var check = page.numPointlength(value, [3, 5]);
                    return done(check);
                },
                //ShohinKaihatsuSuppot Modify 2022FY: -st required when open dialog 606
                //check_required_from_606_dialog: function (value, param, otps, done) {

                //    if (!otps.is606) {
                //        return done(true);
                //    }

                //    var tbody = otps.tbody.element ? otps.tbody.element : otps.tbody;
                //    var inputCheck = tbody.hasClass("row-kotei");

                //    if (inputCheck || otps.ignoreBlankQuantity) {
                //        return done(true);
                //    }

                //    return done(!App.isUndefOrNullOrStrEmpty(value));
                //}
                //-ed
            },
            options: {
                name: "歩留"
            },
            messages: {
                numberBug15481: App.messages.base.number,
                min: App.messages.base.min,
                pointlength_custom: App.str.format(App.messages.base.pointlength, { name: "歩留", param: [3, 2] }),
                //check_required_from_606_dialog: App.messages.base.required
            }
        },
        //ritu_abura: {
        //    rules: {
        //        numberBug15481: true,
        //        min: 0,
        //        pointlength_custom: function (value, param, otps, done) {
        //            value = value == "." ? "" : value;
        //            var check = page.numPointlength(value, [3, 5]);
        //            return done(check);
        //        }
        //    },
        //    options: {
        //        name: "油含有率"
        //    },
        //    messages: {
        //        numberBug15481: App.messages.base.number,
        //        min: App.messages.base.min,
        //        pointlength_custom: App.str.format(App.messages.base.pointlength, { name: "油含有率", param: [3, 2] })
        //    }
        //},
        //juryo_shiagari_g: {
        //    rules: {
        //        numberBug15481: true,
        //        min: 0,
        //        pointlengthBug15481: [10, 4]
        //    },
        //    options: {
        //        name: "合計仕上重量"
        //    },
        //    messages: {
        //        numberBug15481: App.messages.base.number,
        //        min: App.messages.base.min,
        //        pointlengthBug15481: App.messages.app.AP0151.replace("{name}", "合計仕上重量").replace("{param[0]}", "各工程仕上重量").replace("{param[1]}", "10").replace("{param[2]}", "4")
        //    }
        //}
    };

    /**
    * seihoにコピーするとき
    */
    HaigoHyo_Tab.detail.options.validationsKopiMode = {
        cd_genryo: {
            rules: {
                required: true,
                check_single_kotations: true,
                haneisukigo: true,
                checkNewGenryo: function (value, param, otps, done) {
                    //check null
                    var isEmpty = function (val) {
                        return App.isUndef(val) || App.isNull(val) || (App.isStr(val) && val.length === 0);
                    }

                    var tbody = otps.tbody.element ? otps.tbody.element : otps.tbody;
                    var inputCheck = tbody.find(".col-selected").val();

                    //quality empty
                    if (isEmpty(inputCheck)) {
                        return done(true);
                    }

                    if (inputCheck == 0) {
                        return done(true);
                    }

                    value = App.isNum(value) ? value + "" : value;
                    var regex = /^[0-9]+$/;

                    if (HaigoHyo_Tab.detail.genryoBCheck(value) || !regex.test(value)) {
                        return done(false);
                    }

                    return done(true);
                }
            },
            options: {
                name: "原料コード"
            },
            messages: {
                required: App.messages.base.required,
                check_single_kotations: App.messages.app.AP0153,
                haneisukigo: App.messages.base.haneisukigo,
                checkNewGenryo: App.messages.app.AP0030
            }
        },
        nm_kotei: {
            rules: {
                maxlength: 60,
                check_single_kotations: true
            },
            options: {
                name: "工程名"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        nm_genryo: {
            rules: {
                maxlength: 60,
                check_single_kotations: true
            },
            options: {
                name: "原料名"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        tanka: {
            rules: {
                numberBug15481: true,
                min: 0,
                pointlength_custom: function (value, param, otps, done) {
                    value = value == "." ? "" : value;
                    var check = page.numPointlength(value, [8, 10]);
                    return done(check);
                }
            },
            options: {
                name: "単価"
            },
            messages: {
                numberBug15481: App.messages.base.number,
                min: App.messages.base.min,
                pointlength_custom: App.str.format(App.messages.base.pointlength, { name: "単価", param: [8, 2] })
            }
        },
        budomari: {
            rules: {
                numberBug15481: true,
                min: 0,
                pointlength_custom: function (value, param, otps, done) {
                    value = value == "." ? "" : value;
                    var check = page.numPointlength(value, [3, 5]);
                    return done(check);
                }
            },
            options: {
                name: "歩留"
            },
            messages: {
                numberBug15481: App.messages.base.number,
                min: App.messages.base.min,
                pointlength_custom: App.str.format(App.messages.base.pointlength, { name: "歩留", param: [3, 2] })
            }
        },
        //ritu_abura: {
        //    rules: {
        //        numberBug15481: true,
        //        min: 0,
        //        pointlength_custom: function (value, param, otps, done) {
        //            value = value == "." ? "" : value;
        //            var check = page.numPointlength(value, [3, 5]);
        //            return done(check);
        //        }
        //    },
        //    options: {
        //        name: "油含有率"
        //    },
        //    messages: {
        //        numberBug15481: App.messages.base.number,
        //        min: App.messages.base.min,
        //        pointlength_custom: App.str.format(App.messages.base.pointlength, { name: "油含有率", param: [3, 2] })
        //    }
        //},
    };

    /**
     * 画面明細の初期化処理を行います。
     */
    HaigoHyo_Tab.detail.initialize = function () {

        var element = HaigoHyo_Tab.element,
            table = element.find(".datatable"),
            offsetHeight = $(window).height() * 15 / 100,
             datatable = table.dataTable({
                 height: 100,
                 resize: true,
                 fixedColumn: true,
                 fixedColumns: 7,
                 resizeOffset: 60,
                 innerWidth: 90,
                 divHeadScroll: true,
                 onselect: HaigoHyo_Tab.detail.select,
                 onchange: HaigoHyo_Tab.detail.change
             });

        table = element.find(".datatable");        //列固定にした場合DOM要素が再作成されるため、変数を再取得

        //メモ入力画面表示
        table.on("dblclick", ".memo", page.detail.showMemoDialog);
        table.on("focusout", ".quantity", HaigoHyo_Tab.detail.checkFocusOutColumn);
        table.on("focusin", ".quantity", function (e) {
            $(e.target).attr("data-before", valtarget($(e.target)));
        });

        //2019-09-10 : START : Bug #15319 選択行の色
        table.on("focusin", "td", function (e) {
            table.find(".selectedInput").removeClass("selectedInput");
            if (e.target.type == "checkbox") {
                $(e.target).parents("td").addClass("selectedInput");
                return;
            }
            $(e.target).addClass("selectedInput");
        });

        table.on("click", "td", function (e) {
            table.find(".selectedInput").removeClass("selectedInput");
            if (e.target.type == "checkbox") {
                $(e.target).parents("td").addClass("selectedInput");
                return;
            }
            $(e.target).addClass("selectedInput"); $(e.target).find(":input").addClass("selectedInput");
        });
        //2019-09-10 : END : Bug #15319 選択行の色

        HaigoHyo_Tab.detail.validator = element.validation(HaigoHyo_Tab.createValidator(HaigoHyo_Tab.detail.options.validations));
        HaigoHyo_Tab.detail.element = element;
        HaigoHyo_Tab.detail.dataTable = datatable;

        //Define area
        var elementHaigo = element.find(".dt-container .fix-columns .dt-body .datatable");
        var elementtr = element.find(".dt-container .flow-container .dt-head table thead tr");
        var elementShisaku = element.find(".dt-container .flow-container .dt-head table thead tr").not(".check-haigo");
        var elementShisakuHead = element.find(".dt-container .flow-container .dt-head table thead .check-haigo");

        HaigoHyo_Tab.detail.elementHaigo = elementHaigo;
        HaigoHyo_Tab.detail.elementShisaku = elementShisaku;
        HaigoHyo_Tab.detail.elementShisakuHead = elementShisakuHead;

        HaigoHyo_Tab.detail.validatorSampleHead = elementShisaku.validation(HaigoHyo_Tab.createValidatorOnlyInput(HaigoHyo_Tab.detail.options.validationsShisaku));

        elementShisaku.on("change", ":input", HaigoHyo_Tab.detail.changeShisaku);
        elementShisakuHead.on("change", ":input", HaigoHyo_Tab.detail.changeShisakuHead);

        //2019-09-10 : START : Bug #15319 選択行の色
        table.on("focusin", "th", function (e) {
            if (!App.isUndefOrNull(HaigoHyo_Tab.detail.selectedRow)) {
                HaigoHyo_Tab.detail.selectedRow.element.find(".selected-row").removeClass("selected-row");
            }
            HaigoHyo_Tab.detail.element.find(".selectedInput").removeClass("selectedInput");

            if (e.target.type == "checkbox" && !$(e.target).hasClass("flg_print_all")) {
                $(e.target).parents("th").addClass("selectedInput");
                return;
            }

            $(e.target).addClass("selectedInput");
        });
        elementtr.on("click", "th", function (e) {
            if (!App.isUndefOrNull(HaigoHyo_Tab.detail.selectedRow)) {
                HaigoHyo_Tab.detail.selectedRow.element.find(".selected-row").removeClass("selected-row");
            }
            HaigoHyo_Tab.detail.element.find(".selectedInput").removeClass("selectedInput");

            if (e.target.type == "checkbox" && !$(e.target).hasClass("flg_print_all")) {
                $(e.target).parents("th").addClass("selectedInput");
                return;
            }

            $(e.target).addClass("selectedInput");
            $(e.target).find(":input").addClass("selectedInput")
        });
        //2019-09-10 : END : Bug #15319 選択行の色

        //// 行選択時に利用するテーブルインデックスを指定します
        HaigoHyo_Tab.detail.fixedColumnIndex = element.find(".fix-columns").length;

        //TODO: 画面明細で利用するコントロールのイベントの紐づけ処理をここに記述します。
        element.on("dblclick", ".datatable .change_keta_shosu", HaigoHyo_Tab.detail.beforSetColorShisaList);
        element.on("change", ".flg_print_all", HaigoHyo_Tab.detail.AllPrintCheck);

        //選択した試作列のスクロールバーの位置情報を保持し、他のタブへ移動後、スクロールバーの位置を保持していた位置に移動する
        $("#HaigoHyo_Tab .dt-container .flow-container .dt-head").on('scroll', function () {
            var $target = $(this);

            $("#HaigoHyo_Tab .dt-container .flow-container .dt-body").scrollLeft($target.scrollLeft());
            $("#HaigoHyo_Tab .dt-container .flow-container .dt-foot").scrollLeft($target.scrollLeft());
            page.values.positionLeft = $target.scrollLeft();
        });

        //選択した試作列のスクロールバーの位置情報を保持し、他のタブへ移動後、スクロールバーの位置を保持していた位置に移動する
        $("#HaigoHyo_Tab .dt-container .flow-container .dt-body").scroll(function () {
            if (HaigoHyo_Tab.detail.values.offscroll) {
                page.values.positionLeft = $(this).scrollLeft();
            }
        });

        //原料一覧ダイアログ。
        element.on("dblclick", ".genryo-selected", function (e) {
            var genryo = $(this);
            if (genryo.is(":disabled") || genryo.is('[readonly]')) {
                //何もしません
                return;
            }

            //原料一覧画面起動
            if (App.isFunc(HaigoHyo_Tab.showGenryoIchiranDialog)) {
                HaigoHyo_Tab.showGenryoIchiranDialog(undefined);
            }
        })
    };

    /**
    * フォーカスアウトコラムをチェック。
    */
    HaigoHyo_Tab.detail.checkFocusOutColumn = function (e) {

        if (HaigoHyo_Tab.detail.values.currentColChanged == undefined) {
            return;
        }

        //色指定ダイアログが量アリアから呼び出されたとき何もしない
        if ($("#_602_IroShitei_Dialog").is(':visible') && page.dialogs.IroShitei_Dialog.options.isCheckHide) {
            return;
        }

        //データの変更を確認する
        //変更したデータときに何もしない
        var val = valtarget($(e.target));
        if (val != $(e.target).attr("data-before")) {
            if (val != "" && $(e.target).attr("data-before") != "") {
                if (Number(val) != Number($(e.target).attr("data-before"))) {
                    return;
                }
            } else {
                return;
            }
        }

        //同じ列にいるとき何もしない。
        if ($(e.target).parent().attr("index-col") != HaigoHyo_Tab.detail.values.currentColChanged) {
            return;
        }

        ////量更新チェック
        //if (!HaigoHyo_Tab.values.isChangeRunning[HaigoHyo_Tab.detail.values.currentColChanged] || !Object.keys(HaigoHyo_Tab.values.isChangeRunning[HaigoHyo_Tab.detail.values.currentColChanged]).length) {
        //    return;
        //}

        setTimeout(function () {
            var targetJuryo = HaigoHyo_Tab.detail.element.find(".sample-" + HaigoHyo_Tab.detail.values.currentColChanged).findP("juryo_shiagari_g");
            //フォーカスアウトコラムをチェック。
            HaigoHyo_Tab.detail.checkSikiKeisan().then(function (result) {
                if (result == "isOKDialog") {

                    //変更列が他列の計算式に使用されている場合に下記内容で計算する.
                    var id = targetJuryo.closest("td").attr("data-key"),
                        entityJuryo = HaigoHyo_Tab.detail.dataShisaku.entry(id);

                    // 変更列が他列の計算式に使用されている場合に下記内容で計算する.
                    HaigoHyo_Tab.detail.updateSikiKeisanTotal({ element: targetJuryo.closest("tbody") }, entityJuryo, targetJuryo.closest("td"), undefined).then(function () {
                        //if (App.isFunc(HaigoHyo_Tab.recalculation)) {
                        //    //・原価試算タブ．原料費の再計算を行う
                        //    HaigoHyo_Tab.recalculation("tokuseichi_tab", "量");
                        //} else {
                        //    App.ui.loading.close();
                        //}
                        App.ui.loading.close();
                    })
                }
            })
        }, 100)
    };

    /**
    * Add a black star to nm_genryo
    */
    HaigoHyo_Tab.detail.addBlackStar = function (val, isRemoveStar) {
        if (isRemoveStar) {
            val = HaigoHyo_Tab.detail.substringFirst((val || ""));
        }
        return "★" + val;
    }

    /**
    * Add a black star to nm_genryo
    */
    HaigoHyo_Tab.detail.addWhiteStar = function (val, isRemoveStar) {
        if (isRemoveStar) {
            val = HaigoHyo_Tab.detail.substringFirst((val || ""));
        }
        return "☆" + val;
    }

    /**
    * Check if cd_gneryo beginhing with 'B'
    */
    HaigoHyo_Tab.detail.genryoBCheck = function (val) {
        return val.indexOf("B") === 0;
    }

    HaigoHyo_Tab.detail.disableInp = function (e) {
        if (!e) return;
        return e.prop("readonly", true);
    }

    /**
    * 原料一覧データを表わす。
    */
    HaigoHyo_Tab.detail.dataGenryoSelected = function (dl, dataHin) {

        var genryo = HaigoHyo_Tab.element.find(".dt-fix-body .new .selected-row");

        if (!genryo.length) {
            return false;
        }

        var tbody = genryo.closest("tbody"),
            id = tbody.attr("data-key"),
            entity, nm_genryo;

        if (App.isUndefOrNull(id) || !tbody.hasClass("row-genryo")) {
            return false;
        }

        //原料CD	
        var genryo = tbody.findP("cd_genryo");
        if (genryo.is(":disabled") || genryo.is('[readonly]')) {
            return false;
        }

        // Data is always exist when return from Genryo dialog
        HaigoHyo_Tab.detail.setGenryoExistState(tbody);

        tbody.findP("nm_genryo").prop("readonly", false);
        tbody.findP("tanka").prop("readonly", false);

        var addBlackStar = HaigoHyo_Tab.detail.addBlackStar,
            addWhiteStar = HaigoHyo_Tab.detail.addWhiteStar,
            disableInp = HaigoHyo_Tab.detail.disableInp;

        var dKaisha = dl.cd_kaisha,
            dKojo = dl.cd_busho,
            budomari_gry = null,
            isGenryoB = HaigoHyo_Tab.detail.genryoBCheck(dl.cd_genryo),
            //isChangeCalcParam = false;
            syncCalcParamIns = HaigoHyo_Tab.detail.getCalcParam();

        var eGenryo = tbody.findP("nm_genryo"),
            eTanka = tbody.findP("tanka");

        var shisakuHin = HaigoHyo_Tab.getDataHeader();

        entity = HaigoHyo_Tab.detail.dataHaigo.entry(id);

        // The same kaisha
        if (shisakuHin.cd_kaisha == dl.cd_kaisha) {
            // The same kaisha - The same kojo
            if (shisakuHin.cd_kojo == dl.cd_busho) {
                // The code is not begin by 'B'
                if (!isGenryoB) {
                    // Shiyo
                    if (!dl.kbn_haishi) {
                        entity.nm_genryo = dl.nm_genryo;
                        disableInp(eGenryo);
                        entity.tanka = dl.tanka;
                        //disableInp(eTanka);
                        entity.budomari = budomari_gry = dl.budomari;
                        //isChangeCalcParam = true;
                        syncCalcParamIns.setSyncAllState();
                    } else {
                        //Haishi
                        entity.nm_genryo = addBlackStar(entity.nm_genryo, true);
                        disableInp(eGenryo).val(entity.nm_genryo);
                        //isChangeCalcParam = false;
                        syncCalcParamIns.setUnSyncState();
                    }
                } else {
                    // Data err
                    return false;
                }
            }
            // The same kaisha - Diff kojo
            else {
                // The code is not begin by 'B'
                if (!isGenryoB) {
                    entity.nm_genryo = addWhiteStar(dl.nm_genryo, true);
                    entity.tanka = dl.tanka;
                    entity.budomari = budomari_gry = dl.budomari;
                    disableInp(eGenryo);
                    //disableInp(eTanka);
                    //isChangeCalcParam = true;
                    syncCalcParamIns.setSyncAllState();
                } else {
                    // Shiyo
                    if (!dl.kbn_haishi) {
                        entity.nm_genryo = dl.nm_genryo;
                        entity.tanka = dl.tanka;
                        entity.budomari = budomari_gry = dl.budomari;
                        //isChangeCalcParam = true;
                        syncCalcParamIns.setSyncAllState();
                    } else {
                        // Haishi
                        entity.nm_genryo = addBlackStar(entity.nm_genryo, true);
                        //isChangeCalcParam = false;
                        syncCalcParamIns.setUnSyncState();
                    }
                }
            }

        }
        // Diff kaisha
        else {
            dKaisha = shisakuHin.cd_kaisha;
            dKojo = isGenryoB ? "0" : shisakuHin.cd_kojo;
            entity.nm_genryo = addBlackStar(entity.nm_genryo, true);
            if (!isGenryoB) {
                disableInp(eGenryo);
            }
            //isChangeCalcParam = false;
            syncCalcParamIns.setUnSyncState();
        }

        entity.cd_genryo = dl.cd_genryo;
        entity.cd_kaisha = dKaisha;
        entity.cd_busho = dKojo;
        entity.budomari_gry = budomari_gry;

        tbody.form(HaigoHyo_Tab.detail.options.bindOption).bind(HaigoHyo_Tab.detail.getChangeGenryoData(entity));

        HaigoHyo_Tab.detail.dataHaigo.update(entity);

        //フォントを赤色・太文字に変える。
        HaigoHyo_Tab.detail.iroBudomari(tbody, entity);

        // リテラルマスタ.リテラル値1 = '0' 又は該当データなしの時、単価開示不可
        //HaigoHyo_Tab.detail.controlTankaHyoji(entity.cd_kaisha, tbody);

        // Sync calc param + recalc total
        if (syncCalcParamIns.isSync()) {
            syncCalcParamIns.syncTotalCalcParam(entity.cd_genryo, entity.cd_kaisha, entity.cd_busho, entity).then(function () {
                //HaigoHyo_Tab.detail.calculateTotal("ChangeGenryo");
            });
        }

        HaigoHyo_Tab.detail.validateHiago(tbody);
        //フラグ変更画面
        page.values.isChange = true;
        return true;
    }

    /**
    * Get data for display
    */
    HaigoHyo_Tab.detail.getChangeGenryoData = function (entity) {
        return {
            cd_genryo: (entity.cd_genryo || ""),
            nm_genryo: (entity.nm_genryo || ""),
            tanka: App.isUndefOrNull(entity.tanka) ? "" : entity.tanka,
            budomari: App.isUndefOrNull(entity.budomari) ? "" : entity.budomari
        }
    }

    /**
    * 原料一覧データを表わす。
    */
    //HaigoHyo_Tab.detail.dataGenryoSelectedOld = function (data, dataHin) {

    //    var genryo = HaigoHyo_Tab.element.find(".dt-fix-body .new .selected-row");

    //    if (!genryo.length) {
    //        return false;
    //    }

    //    var tbody = genryo.closest("tbody"),
    //        id = tbody.attr("data-key"),
    //        entity, nm_genryo;

    //    if (App.isUndefOrNull(id) || !tbody.hasClass("row-genryo")) {
    //        return false;
    //    }

    //    //原料CD	
    //    var genryo = tbody.findP("cd_genryo");
    //    if (genryo.is(":disabled") || genryo.is('[readonly]')) {
    //        return false;
    //    }

    //    tbody.findP("nm_genryo").prop("readonly", false);
    //    tbody.findP("tanka").prop("readonly", false);

    //    if (data.cd_genryo.indexOf("B") != 0) {
    //        //原料名  
    //        tbody.findP("nm_genryo").prop("readonly", true);
    //        if (!data.kbn_haishi && data.flg_tanka_hyoji && data.cd_kaisha == dataHin.cd_kaisha) {
    //            //単価
    //            tbody.findP("tanka").prop("readonly", true);
    //        }
    //    }

    //    entity = HaigoHyo_Tab.detail.dataHaigo.entry(id);

    //    //歩留原本
    //    entity["budomari_gry"] = null;
    //    //担当会社
    //    entity["cd_kaisha"] = data.cd_kaisha;
    //    //担当工場
    //    entity["cd_busho"] = data.cd_busho;

    //    //原料CD	
    //    tbody.findP("cd_genryo").val(data.cd_genryo);
    //    entity["cd_genryo"] = data.cd_genryo;

    //    if (!data.kbn_haishi) {
    //        //原料名  
    //        tbody.findP("nm_genryo").val(data.nm_genryo != null ? data.nm_genryo : "");
    //        entity["nm_genryo"] = data.nm_genryo;
    //        //単価
    //        if (data.flg_tanka_hyoji) {

    //            var tanka = page.detail.beforFormatNumber(data.tanka, 2);
    //            tbody.findP("tanka").val(tanka.forDisp);
    //            entity["tanka"] = tanka.forData;
    //            //歩留
    //            var budomari = page.detail.beforFormatNumber(data.budomari, 2);
    //            tbody.findP("budomari").val(budomari.forDisp);
    //            entity["budomari"] = budomari.forData;
    //            //歩留原本
    //            entity["budomari_gry"] = budomari.forData;
    //        } else {
    //            tbody.findP("tanka").val("");
    //            entity["tanka"] = null;
    //            //歩留
    //            tbody.findP("budomari").val("");
    //            entity["budomari"] = null;
    //        }
    //        //油含有率
    //        //var ritu_abura = page.detail.beforFormatNumber(data.ritu_abura, 2);
    //        //tbody.findP("ritu_abura").val(ritu_abura.forDisp);
    //        //entity["ritu_abura"] = ritu_abura.forData;
    //        //酢酸
    //        entity["ritu_sakusan"] = data.ritu_sakusan;
    //        //食塩
    //        //entity["ritu_shokuen"] = data.ritu_shokuen;
    //        //総酸
    //        //entity["ritu_sousan"] = data.ritu_sousan;
    //        //率ＭＳＧ
    //        entity["ritu_msg"] = data.ritu_msg;

    //        //原料コードがＮ始まりのデータは対象外。
    //        if (data.cd_genryo.indexOf("B") != 0) {
    //            var nm_genryo = HaigoHyo_Tab.detail.substringFirst(tbody.findP("nm_genryo").val(), null);//.replace("★", "").replace("☆", "");
    //            //自会社の他工場に存在する原料の場合、原料名の先頭に「☆」を表示する。
    //            if (data.cd_kaisha == dataHin.cd_kaisha && data.cd_busho != dataHin.cd_kojo) {
    //                tbody.findP("nm_genryo").val("☆" + nm_genryo);
    //                entity["nm_genryo"] = "☆" + nm_genryo;
    //            }
    //            //他会社に存在する原料の場合、原料名の先頭に「★」を表示する。
    //            if (data.cd_kaisha != dataHin.cd_kaisha) {
    //                tbody.findP("nm_genryo").val("★" + nm_genryo);
    //                entity["nm_genryo"] = "★" + nm_genryo;
    //            }
    //        }
    //    } else {
    //        //原料名  
    //        nm_genryo = HaigoHyo_Tab.detail.substringFirst(tbody.findP("nm_genryo").val(), null);// .replace("★", "").replace("☆", "");
    //        //原料名  
    //        tbody.findP("nm_genryo").val("★" + nm_genryo);
    //        entity["nm_genryo"] = "★" + nm_genryo;
    //    }

    //    HaigoHyo_Tab.detail.dataHaigo.update(entity);

    //    //フォントを赤色・太文字に変える。
    //    HaigoHyo_Tab.detail.iroBudomari(tbody, entity);

    //    // リテラルマスタ.リテラル値1 = '0' 又は該当データなしの時、単価開示不可
    //    HaigoHyo_Tab.detail.controlTankaHyoji(entity.cd_kaisha, tbody);

    //    // Sync calc param + recalc total
    //    HaigoHyo_Tab.detail.syncTotalCalcParam(entity.cd_genryo, entity.cd_kaisha, entity).then(function () {
    //        HaigoHyo_Tab.detail.calculateTotal("ChangeGenryo");
    //    });

    //    //フラグ変更画面
    //    page.values.isChange = true;
    //    return true;
    //}

    /*
    * 19075 - 2022/06/06 - ShohinKaihatsuSuppot Modify 2022FY
    * binding data (quantity) from 原料費確認 dialog
    */
    HaigoHyo_Tab.detail.dataGenzairyoKakuninSelected = function (data) {

        if (App.isUndefOrNull(data)) {
            return false;
        }

        var shisakuNumber = data.shisakuNumber;

        var shisakuElement = HaigoHyo_Tab.detail.element.find(".sample-" + shisakuNumber);

        $.each(data.dataModified, function (index, item) {
            $.each(item, function (prop, val) {
                if (prop.indexOf("quantity_") >= 0) {
                    shisakuElement.findP(prop).val(val).change();
                }
            });
        });

        return true;
    }

    HaigoHyo_Tab.detail.substringFirst = function (string, strCheck) {
        if (strCheck != null) {
            var newString = string.indexOf(strCheck) == 0 ? string.substring(1) : string;
            return newString;
        }
        var newString = string.indexOf("★") == 0 ? string.substring(1) : string;
        newString = newString.indexOf("☆") == 0 ? newString.substring(1) : newString;
        return newString;
    }

    /**
    * 選択した試作列のスクロールバーの位置情報を保持し、他のタブへ移動後、スクロールバーの位置を保持していた位置に移動する。
    */
    $('#haigohyo_tab').on('shown.bs.tab', function (e) {
        HaigoHyo_Tab.detail.values.offscroll = false;
        var scrollLeft = $("#HaigoHyo_Tab .dt-container .flow-container .dt-body").scrollLeft();
        if (scrollLeft != page.values.positionLeft) {
            $("#HaigoHyo_Tab .dt-container .flow-container .dt-body").scrollLeft(page.values.positionLeft);
        }

        setTimeout(function () {
            HaigoHyo_Tab.detail.values.offscroll = true;
        }, 1)
    });

    HaigoHyo_Tab.setLastSavePosition = function () {
        HaigoHyo_Tab.detail.values.offscroll = false;
        var scrollLeft = $("#HaigoHyo_Tab .dt-container .flow-container .dt-body").scrollLeft();
        if (scrollLeft != page.values.positionLeft) {
            $("#HaigoHyo_Tab .dt-container .flow-container .dt-body").scrollLeft(page.values.positionLeft);
        }
        setTimeout(function () {
            HaigoHyo_Tab.detail.values.offscroll = true;
        }, 1)
    }

    /**
    * 配合表上のすべてのデータ原料から分析原料確認データ確認画面をくりする。
    */
    HaigoHyo_Tab.detail.dataGenryoOnTab = function () {
        var genryoList = [];

        HaigoHyo_Tab.detail.dataHaigo.findAll(function (item, entity) {
            if (entity.state !== App.ui.page.dataSet.status.Deleted) {
                var itemNew = $.extend(true, {}, item);
                genryoList.push(itemNew);
            }
        });

        return genryoList;
    }

    /**
    * 原料分析のデータが準備する。
    */
    HaigoHyo_Tab.detail.genryoBunsekiData = function () {
        var deferred = $.Deferred(),
            element = HaigoHyo_Tab.detail.element.find(".datatable"),
            kotei = element.find(".row-kotei").first(),
            dataHaigo = [];

        if (!kotei.length) {
            return deferred.reject();
        }

        var shisakuHin = HaigoHyo_Tab.getDataHeader();

        nextRow = kotei.next();
        while (nextRow.length > 0 && !nextRow.hasClass("item_total_kotei_tml")) {
            if (nextRow.find(".item-kotei:checkbox").length == 0) {
                id = nextRow.attr("data-key");
                entity = HaigoHyo_Tab.detail.dataHaigo.entry(id);

                //if (entity.cd_genryo == null || entity.cd_genryo == "" || entity.cd_genryo != App.settings.app.commentCode) {

                var itemNew = $.extend(true, {}, entity);

                HaigoHyo_Tab.detail.dataTable.dataTable("getRow", nextRow, function (rows) {
                    //量
                    itemNew["quantity"] = rows.element.find(".col-selected").val();
                    itemNew["quantity"] = itemNew["quantity"] == 0 ? null : itemNew["quantity"];
                })

                //セッションID
                itemNew["id_session"] = "";
                //Request #14303 : 基本、tr_haigoと同じデータ
                ////会社コード
                //itemNew["cd_kaisha"] = itemNew.cd_kaisha_genryo;
                ////工場コード
                itemNew["cd_kojo"] = itemNew.cd_busho;

                //for add new row but not change genryo 
                //Request #14303 : 基本、tr_haigoと同じデータ
                //if (itemNew.cd_kaisha_genryo == null || itemNew.cd_kaisha_genryo == 0) {
                //    //会社コード
                //    itemNew["cd_kaisha"] = shisakuHin.cd_kaisha;
                //    //工場コード
                //    itemNew["cd_kojo"] = shisakuHin.cd_kojo;
                //}
                dataHaigo.push(itemNew);
                //}
            }

            nextRow = nextRow.next();
        }

        deferred.resolve(dataHaigo);

        return deferred.promise();
    };

    /**
     * update flag print all shisaku when flg_print_all changed
     */
    HaigoHyo_Tab.detail.AllPrintCheck = function (e) {

        var target = $(e.target);

        if (target.length == 0) {
            return;
        }

        var checked = target[0].checked,
            element = HaigoHyo_Tab.detail.elementShisaku,
            print = element.findP("flg_print");

        if (!print.length) {
            return;
        }

        var itemTarget, th;
        $.each(print, function (index, item) {
            itemTarget = element.find(item);
            th = itemTarget.closest("th");

            if (!th.hasClass("th-tmpl")) {
                itemTarget.prop("checked", checked).change();
            }
        })
    }

    /**
     * show dialog select color。
     */
    HaigoHyo_Tab.detail.beforSetColorHaigo = function (e) {
        if (page.values.modeDisp == App.settings.app.m_shisaku_data.etsuran) {
            return;
        }

        var target = $(e.target);

        HaigoHyo_Tab.detail.isSetColorHaigo = true;
        HaigoHyo_Tab.detail.targetSetColor = target;

        HaigoHyo_Tab.showIroSettingDialog(e);
    }

    /**
     * show dialog select color。
     */
    HaigoHyo_Tab.detail.beforSetColorShisaList = function (e) {
        if (page.values.modeDisp == App.settings.app.m_shisaku_data.etsuran || page.values.modeDisp == App.settings.app.m_shisaku_data.copy) {
            return;
        }

        var target = $(e.target),
            id = target.attr("data-key");

        if (App.isUndefOrNull(id)) {
            return;
        }

        HaigoHyo_Tab.detail.targetSetColor = target;
        HaigoHyo_Tab.detail.isSetColorHaigo = false;

        HaigoHyo_Tab.showIroSettingDialog(e, true);
    }

    /**
    *  フォーカスアウトコラムをチェック。
    */
    HaigoHyo_Tab.detail.checkHideIroShitei = function () {
        setTimeout(function () {
            if (HaigoHyo_Tab.detail.values.currentColChanged == undefined) {
                return;
            }

            HaigoHyo_Tab.detail.checkSikiKeisan().then(function (result) {
                if (result) {
                    //if (App.isFunc(HaigoHyo_Tab.recalculation)) {
                    //    //・原価試算タブ．原料費の再計算を行う
                    //    HaigoHyo_Tab.recalculation("tokuseichi_tab", "量");
                    //} else {
                    //    App.ui.loading.close();
                    //}
                    App.ui.loading.close();
                }
            })
        }, 200);
    }

    /**
     * set color select for selected input。
     */
    HaigoHyo_Tab.detail.setColor = function (color) {

        if (color == undefined) {
            color = "";
        }

        //set color for haigo list
        if (HaigoHyo_Tab.detail.isSetColorHaigo) {
            HaigoHyo_Tab.detail.setColorHaigoList(color);
            return;
        }

        //set color for shisa list
        HaigoHyo_Tab.detail.setColorShisalist(color);
    };

    /**
     * set color select for selected input on haigolist。
     */
    HaigoHyo_Tab.detail.setColorHaigoList = function (color) {

        var target = HaigoHyo_Tab.detail.targetSetColor,
            tbody = HaigoHyo_Tab.detail.element.find(target).closest("tbody"),
            id = tbody.attr("data-key"),
            entity;

        if (App.isUndefOrNull(id)) {
            return;
        }

        //フラグ変更画面
        page.values.isChange = true;

        //update color
        entity = HaigoHyo_Tab.detail.dataHaigo.entry(id);
        entity.color = color != "" ? color : null;
        HaigoHyo_Tab.detail.dataHaigo.update(entity);

        //paint color
        tbody.find(".iroHaigo input, .iroHaigo select").css("background", color);
        tbody.find(".iroHaigo input, .iroHaigo select").css("color", HaigoHyo_Tab.lightOrDark(color));
    }

    /**
     * set color select for selected input on shisalist。
     */
    HaigoHyo_Tab.detail.setColorShisalist = function (color) {

        var target = HaigoHyo_Tab.detail.targetSetColor,
            id = target.attr("data-key"),
            entity;

        if (App.isUndefOrNull(id)) {
            return;
        }

        //フラグ変更画面
        page.values.isChange = true;

        //update color
        entity = HaigoHyo_Tab.detail.dataShisakuList.entry(id);
        entity.color = color != "" ? color : null;
        HaigoHyo_Tab.detail.dataShisakuList.update(entity);

        //paint color
        target.css("background", color);
        target.css("color", HaigoHyo_Tab.lightOrDark(color));
    }

    /**
     * create column sample from data shisaku。
     */
    HaigoHyo_Tab.detail.createChangesetShisakuList = function (data, isNewData) {
        var dataShisakuItem = (data.sampleListOriginal.Items) ? (data.sampleListOriginal.Items) : data.sampleListOriginal,
            count = dataShisakuItem.length,
            dataSetQuantity;

        dataSetQuantity = App.ui.page.dataSet();
        HaigoHyo_Tab.detail.dataShisakuList = dataSetQuantity;

        HaigoHyo_Tab.detail.options.dataKeyQuantity = {};

        if (count == 0) {

            HaigoHyo_Tab.detail.bindSample(data, isNewData);
            //2019-12-12 END : Bug #16120 : 現行仕様に合わせて都度計算して表示するようお願い致します。
            //App.ui.loading.close();
            return;
        }

        var index = 0, item;

        while (index < count) {
            item = dataShisakuItem[index];

            (isNewData ? dataSetQuantity.add : dataSetQuantity.attach).bind(dataSetQuantity)(item);
            //save id data
            HaigoHyo_Tab.detail.options.dataKeyQuantity[
                [
                    item.cd_shain,
                    item.nen,
                    item.no_oi,
                    item.seq_shisaku,
                    item.cd_kotei,
                    item.seq_kotei
                ].join("-")
            ] = item.__id;
            //save color
            HaigoHyo_Tab.detail.options.dataKeyQuantity[
                [
                    item.cd_shain,
                    item.nen,
                    item.no_oi,
                    item.seq_shisaku,
                    item.cd_kotei,
                    item.seq_kotei,
                    "color"
                ].join("-")
            ] = item.color;

            index += 1;
        }

        //bind col sample
        HaigoHyo_Tab.detail.bindSample(data, isNewData);
    };

    /**
     * resize with table change column.
     */
    HaigoHyo_Tab.detail.resizeTable = function (count) {
        var element = HaigoHyo_Tab.detail.element;


        //テーブルでサイズ変更
        var theadTable = element.find(".dt-container .flow-container .dt-head .datatable"),
            theadWith = theadTable.css("width").replace("px", ""),
            bodyTable = element.find(".dt-container .flow-container .dt-body .datatable");

        theadTable.css("width", (parseFloat(theadWith) + (count * 135) - 135) + "px");
        theadTable.css("max-width", (parseFloat(theadWith) + (count * 135) - 135) + "px");
        bodyTable.css("width", (parseFloat(theadWith) + (count * 135) - 135) + "px");
        bodyTable.css("max-width", (parseFloat(theadWith) + (count * 135) - 135) + "px");
    };

    /**
     * create column sample from data shisaku。
     */
    HaigoHyo_Tab.detail.bindSample = function (data, isNewData) {
        var element = HaigoHyo_Tab.detail.element,
            dataShisakuItem = (data.sampleItem.Items) ? (data.sampleItem.Items) : data.sampleItem,
            count = dataShisakuItem.length,
            dataSetSample, flg_irai = false;

        dataSetSample = App.ui.page.dataSet();
        HaigoHyo_Tab.detail.dataShisaku = dataSetSample;

        HaigoHyo_Tab.detail.options.maxSeqShisaku = 0;

        //create column
        var item;
        while (count > 0) {
            count = count - 1;
            item = dataShisakuItem[count];

            //原価試算表が出力した試作列に表示
            if (item.flg_shisanIrai) {
                item.flg_no_seiho1 = HaigoHyo_Tab.values.flg_no_seiho.shisanIrai;
            }

            //最後に製法コピーが行われた試作列に表示
            if (item.flg_isKopiLast) {
                item.flg_no_seiho1 = item.flg_no_seiho1 ? item.flg_no_seiho1 : "";
                item.flg_no_seiho1 += HaigoHyo_Tab.values.flg_no_seiho.isKopiLast;
            }

            HaigoHyo_Tab.detail.addTrialManual(false, item, isNewData);
            flg_irai = (flg_irai || (item.flg_shisanIrai == HaigoHyo_Tab.values.flg_shisanIrai));

            if (App.isFunc(HaigoHyo_Tab.addSample)) {

                var isAddEndCol = false,
                    isNewData = false;

                HaigoHyo_Tab.addSample(item, isAddEndCol, isNewData);
            }

            //log seq_shisaku max
            HaigoHyo_Tab.detail.options.maxSeqShisaku = item.seq_shisaku > HaigoHyo_Tab.detail.options.maxSeqShisaku ? item.seq_shisaku : HaigoHyo_Tab.detail.options.maxSeqShisaku;
        }
        //2019-12-12 START : Bug #16120 : 現行仕様に合わせて都度計算して表示するようお願い致します。
        page.detail.values.isCompleteTokuseichi = true;

        //renew validate list
        var validations = $.extend(true, {}, HaigoHyo_Tab.detail.options.validations, HaigoHyo_Tab.detail.options.validationsShisakuList, HaigoHyo_Tab.detail.options.validationsTotal);
        HaigoHyo_Tab.detail.validator = element.validation(HaigoHyo_Tab.createValidator(validations));

        //テーブルでサイズ変更
        HaigoHyo_Tab.detail.resizeTable(1);

        //工程パターンは原価試算依頼がある場合操作不可にする
        $("#pt_kotei").prop("disabled", flg_irai);

        //制御使用入力
        if (App.isFunc(HaigoHyo_Tab.controlInput)) {
            HaigoHyo_Tab.controlInput();
        }

        // Control display tani
        if (page.values.modeDisp == App.settings.app.m_shisaku_data.copy) {
            HaigoHyo_Tab.detail.eachKotei(function (eKotei) {
                HaigoHyo_Tab.detail.controlGenryoTani(eKotei, false, true);
            });
        }
    };

    /**
    * ダイアログ明細へのデータバインド処理を行います。
    */
    HaigoHyo_Tab.detail.bindHaigo = function (data, isNewData) {
        //list genryo
        HaigoHyo_Tab.detail.values.genryokojo = data.genryokojo;

        var i, l, item, dataSet, dataSetKotei, dataCount;

        HaigoHyo_Tab.detail.options.maxKotei = 0;
        HaigoHyo_Tab.detail.options.maxSeqKotei = {};
        HaigoHyo_Tab.dataKeySample = {};

        dataHaigoItem = (data.haigoItem.Items) ? (data.haigoItem.Items) : data.haigoItem;
        dataCount = dataHaigoItem.length ? dataHaigoItem.length : 0;

        dataSet = App.ui.page.dataSet();
        dataSetKotei = App.ui.page.dataSet();

        HaigoHyo_Tab.detail.dataHaigo = dataSet;
        HaigoHyo_Tab.detail.dataKotei = dataSetKotei;

        HaigoHyo_Tab.detail.dataTable.dataTable("clear");

        var cd_kotei = "";

        //reset list validate item shisaku list
        HaigoHyo_Tab.detail.options.validationsShisakuList = {};

        for (var i = 0 ; i < dataCount; i++) {
            var item = dataHaigoItem[i],
                row;

            HaigoHyo_Tab.detail.options.maxSortKotei = item.sort_kotei;

            if (item.cd_kotei != cd_kotei) {

                //bind kotei row
                var itemKotei = $.extend(true, {}, item);
                (isNewData ? dataSetKotei.add : dataSetKotei.attach).bind(dataSetKotei)(itemKotei);
                HaigoHyo_Tab.detail.addNewItem(itemKotei, true, false, true);
                HaigoHyo_Tab.detail.options.maxKotei = itemKotei.cd_kotei > HaigoHyo_Tab.detail.options.maxKotei ? itemKotei.cd_kotei : HaigoHyo_Tab.detail.options.maxKotei;

                cd_kotei = itemKotei.cd_kotei;
                HaigoHyo_Tab.detail.options.maxSeqKotei[cd_kotei] = 0;

                //bind genryo row
                (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);

                row = HaigoHyo_Tab.detail.addNewItem(item, false, false, true);

                //原価試算依頼がある場合、操作不可試作品テーブル.容量単位コード
                HaigoHyo_Tab.detail.controlInputGenryo(item, row);

                //リテラルマスタ.リテラル値1 = '0' 又は該当データなしの時、単価開示不可
                //if (!item.flg_tanka_hyoji) {
                //    //単価
                //    row.findP("tanka").hide();
                //    //歩留
                //    row.findP("budomari").hide();

                //    row.find(".tanka-replace").show().prop("readonly", true);
                //}

                //create prop data
                row.findP("quantity").attr("data-prop", "quantity_" + item.cd_kotei + "_" + item.seq_kotei).addClass("change_keta_shosu quantity_" + item.cd_kotei);

                HaigoHyo_Tab.detail.options.maxSeqKotei[cd_kotei] = item.seq_kotei > HaigoHyo_Tab.detail.options.maxSeqKotei[cd_kotei] ? item.seq_kotei : HaigoHyo_Tab.detail.options.maxSeqKotei[cd_kotei];
            } else {
                //bind genryo row
                (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
                row = HaigoHyo_Tab.detail.addNewItem(item, false, false, true);

                //原価試算依頼がある場合、操作不可試作品テーブル.容量単位コード
                HaigoHyo_Tab.detail.controlInputGenryo(item, row);

                //リテラルマスタ.リテラル値1 = '0' 又は該当データなしの時、単価開示不可
                //if (!item.flg_tanka_hyoji) {
                //    //単価
                //    row.findP("tanka").hide();
                //    //歩留
                //    row.findP("budomari").hide();

                //    row.find(".tanka-replace").show().prop("readonly", true);
                //}

                //create prop data
                row.findP("quantity").attr("data-prop", "quantity_" + item.cd_kotei + "_" + item.seq_kotei).addClass("change_keta_shosu quantity_" + item.cd_kotei);

                HaigoHyo_Tab.detail.options.maxSeqKotei[cd_kotei] = item.seq_kotei > HaigoHyo_Tab.detail.options.maxSeqKotei[cd_kotei] ? item.seq_kotei : HaigoHyo_Tab.detail.options.maxSeqKotei[cd_kotei];
            }
        }

        //renew validate
        var validations = $.extend(true, {}, HaigoHyo_Tab.detail.options.validations, HaigoHyo_Tab.detail.options.validationsShisakuList, HaigoHyo_Tab.detail.options.validationsTotal);
        HaigoHyo_Tab.detail.validator = HaigoHyo_Tab.detail.element.validation(HaigoHyo_Tab.createValidator(validations));

        //初期表示_詳細モード
        if (page.values.modeDisp == App.settings.app.m_shisaku_data.shosai
           || page.values.modeDisp == App.settings.app.m_shisaku_data.etsuran
                    || page.values.modeDisp == App.settings.app.m_shisaku_data.copy) {
            //add total row
            HaigoHyo_Tab.detail.totalInit();
            HaigoHyo_Tab.detail.controlDispTotalRow();
        }

        //init changeset for tr_shisaku_list
        HaigoHyo_Tab.detail.createChangesetShisakuList(data, isNewData);
    };

    /**
     * 原価試算依頼がある場合、操作不可試作品テーブル.容量単位コード
     */
    HaigoHyo_Tab.detail.controlInputGenryo = function (item, row) {
        //原料名
        if (item.cd_genryo == null || item.cd_genryo == "" || HaigoHyo_Tab.detail.genryoBCheck(item.cd_genryo) || item.cd_genryo == App.settings.app.commentCode) {
            row.findP("nm_genryo").prop("readonly", false);
            //単価（※）
            row.findP("tanka").prop("readonly", false);
        } else {
            row.findP("nm_genryo").prop("readonly", true);
            //単価（※）
            //row.findP("tanka").prop("readonly", item.flg_disabled_tanka);
        }

        //if (item.flg_open_dialog) {
        //    row.findP("cd_genryo").addClass("focusout_open_dialog");
        //}
    }

    /**
     * control display for row item.
     * isKotei : true [row kotei] || false [row genryo]
     * isTotalKesan : true [row total]
     */
    HaigoHyo_Tab.detail.options.controlDisplayItem = function (row, isKotei, isTotalKesan, isBettenRow) {
        switch (isKotei) {
            case true:
                row.find(":input:not(.input-kotei)").prop("readonly", true).val("");
                row.find(".remove-genryo").remove();
                row.addClass("row-kotei");
                row.find(".checkKotei").addClass("enableColor");
                break;
            case false:
                row.find(":input").prop("readonly", false);
                row.find(".remove-kotei").remove();
                row.find(".input-genryo").show();
                row.addClass("row-genryo");
                row.find(".checkGenryo").addClass("enableColor");
                break;
            case "total":
                if (isTotalKesan) {
                    row.find(":input,label").not(".input-total").not(".isTotalKesan").remove();
                } else {
                    row.find(":input,label").not(".input-total").remove();
                }
                if (isBettenRow) {
                    row.find(":input:not(.quantity)").prop("readonly", true);
                } else {
                    row.find(":input").prop("readonly", true);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 明細の各行にデータを設定する際のオプションを定義します。
     */
    HaigoHyo_Tab.detail.options.bindOption = {
        //TODO: 主キーが直接入力の場合には、修正の場合変更を不可とします。
        appliers: {
            tanka: function (value, element) {
                if (value === "") return false;
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.beforFormatNumber(value, 2).forDisp);
                return true;
            },
            budomari: function (value, element) {
                if (value === "") return false;
                if (value == 0) {
                    element.val("0.00");
                    return true;
                }
                element.val(page.detail.beforFormatNumber(value, 2).forDisp);
                return true;
            },
            //ritu_abura: function (value, element) {
            //    if (value == 0) {
            //        element.val("0.00");
            //        return true;
            //    }
            //    element.val(page.detail.beforFormatNumber(value, 2).forDisp);
            //    return true;
            //}
        }
    };

    /**
     * Applier for number cell
     */
    HaigoHyo_Tab.detail.options.applierNumber = function (value, element) {
        var fixedPosition = element.attr("data-number-format-tofixed");
        //if (!fixedPosition) return false;
        if (!value) {
            value = new BigNumber(0).toFixed(fixedPosition);
            element.val(value);
        } else {
            element.val(page.detail.formatNumber(value, fixedPosition).forDisp);
        }
        return true;
    }

    /**
     * 明細の各行にデータを設定する際のオプションを定義します。
     */
    HaigoHyo_Tab.detail.options.sampleBindOption = {
        //TODO: 主キーが直接入力の場合には、修正の場合変更を不可とします。
        appliers: {
            //合計仕上重量（kg）（※）
            juryo_shiagari_g: function (value, element) {
                var fixedPosition = element.attr("data-number-format-tofixed");
                if (value == 0) {
                    value = new BigNumber(0).toFixed(fixedPosition);
                    element.val(value);
                    return true;
                }
                element.val(page.detail.formatNumber(value, fixedPosition).forDisp);
                return true;
            },
            su_fc: HaigoHyo_Tab.detail.options.applierNumber,
            su_brix: HaigoHyo_Tab.detail.options.applierNumber,
            su_ph: HaigoHyo_Tab.detail.options.applierNumber,
            su_ca: HaigoHyo_Tab.detail.options.applierNumber
            ////原料費（ｋｇ）
            //genryohi: function (value, element) {
            //    if (value == 0) {
            //        element.val("0.00");
            //        return true;
            //    }
            //    element.val(page.detail.formatNumber(value, 2).forDisp);
            //    return true;
            //},
            ////原料費（１個）
            //genryohi1: function (value, element) {
            //    if (value == 0) {
            //        element.val("0.00");
            //        return true;
            //    }
            //    element.val(page.detail.formatNumber(value, 2).forDisp);
            //    return true;
            //},
            //総酸（％）
            //ritu_sousan: function (value, element) {
            //    if (value == 0) {
            //        element.val("0.00");
            //        return true;
            //    }
            //    element.val(page.detail.formatNumber(value, 2).forDisp);
            //    return true;
            //},
            //食塩（％）
            //ritu_shokuen: function (value, element) {
            //    if (value == 0) {
            //        element.val("0.00");
            //        return true;
            //    }
            //    element.val(page.detail.formatNumber(value, 2).forDisp);
            //    return true;
            //},
            //水相中酸度
            //sando_suiso: function (value, element) {
            //    if (value == 0) {
            //        element.val("0.00");
            //        return true;
            //    }
            //    element.val(page.detail.formatNumber(value, 2).forDisp);
            //    return true;
            //},
            //水相中食塩
            //shokuen_suiso: function (value, element) {
            //    if (value == 0) {
            //        element.val("0.00");
            //        return true;
            //    }
            //    element.val(page.detail.formatNumber(value, 2).forDisp);
            //    return true;
            //},
            //水相中酢酸
            //sakusan_suiso: function (value, element) {
            //    if (value == 0) {
            //        element.val("0.00");
            //        return true;
            //    }
            //    element.val(page.detail.formatNumber(value, 2).forDisp);
            //    return true;
            //}
        }
    };

    /**
    * 明細の一覧の行が選択された時の処理を行います。
    */
    HaigoHyo_Tab.detail.select = function (e, row) {
        //TODO: 単一行を作成する場合は、下記２行を利用します。
        HaigoHyo_Tab.detail.element.find($(row.element[HaigoHyo_Tab.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab.selected")).removeClass("selected").addClass("unselected");
        HaigoHyo_Tab.detail.element.find(row.element[HaigoHyo_Tab.detail.fixedColumnIndex].querySelectorAll(".select-tab")).removeClass("unselected").addClass("selected");
        //TODO: 多段行を作成する場合は、下記２行を有効にし上記２行は削除します。
        //$($(row.element[page.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab-2lines.selected")).removeClass("selected").addClass("unselected");
        //$(row.element[page.detail.fixedColumnIndex].querySelectorAll(".select-tab-2lines")).removeClass("unselected").addClass("selected");

        //TODO: 選択行全体に背景色を付ける場合は以下のコメントアウトを解除します。
        if (!App.isUndefOrNull(HaigoHyo_Tab.detail.selectedRow)) {
            HaigoHyo_Tab.detail.selectedRow.element.find(".selected-row").removeClass("selected-row");
        }
        row.element.find("tr").addClass("selected-row");
        HaigoHyo_Tab.detail.selectedRow = row;
    };

    /**
    * event change combobox manual in header
    */
    HaigoHyo_Tab.detail.changeShisakuHead = function (e) {
        var target = $(e.target);

        if (target.length == 0) {
            return;
        }

        var elementShisakuHead = HaigoHyo_Tab.detail.elementShisakuHead,
            element = HaigoHyo_Tab.detail.element;

        if (target[0].checked == false) {
            element.find(".col-selected").removeClass("col-selected");
            return;
        }

        //reset uncheck for all manual
        elementShisakuHead.find(":input").prop("checked", false);

        //recheck this item
        elementShisakuHead.find(target).prop("checked", true);

        var indexCol = target.closest("th").attr("index-col");

        element.find(".col-selected").removeClass("col-selected");
        element.find(".sample-" + indexCol).find(":input").addClass("col-selected");
    }

    /**
    * control event change on Quantity area
    */
    HaigoHyo_Tab.detail.changeShisakuList = function (e, row) {
        var target = $(e.target),
            id = target.attr("data-key"),
            indexCol = target.parent().attr("index-col"),
            property = target.attr("data-prop"),
            entity = HaigoHyo_Tab.detail.dataShisakuList.entry(id);

        var validate = function (targets) {
            return HaigoHyo_Tab.detail.validator.validate({
                targets: targets,
                state: {
                    tbody: row,
                    isGridValidation: true
                }
            });
        };

        //if (!HaigoHyo_Tab.values.isChangeRunning[indexCol]) {
        //    HaigoHyo_Tab.values.isChangeRunning[indexCol] = [];
        //}

        //HaigoHyo_Tab.values.isChangeRunning[indexCol][id] = true;
        //フラグ変更画面
        page.values.isChange = true;
        validate(target).then(function () {

            HaigoHyo_Tab.detail.values.currentColChanged = indexCol;

            if (!valtarget(target)) {
                entity["quantity"] = valtarget(target);
            } else {

                var quantity = page.detail.formatNumber(valtarget(target), Number(page.values.keta_shosu));

                entity["quantity"] = quantity.forData;

                target.val(quantity.forDisp);
            }

            HaigoHyo_Tab.detail.dataShisakuList.update(entity);

            //変更列が他列の計算式に使用されている場合に下記内容で計算する
            HaigoHyo_Tab.detail.checkSikiKeisan().then(function (result) {

                //各工程ごとの合計を再計算する
                HaigoHyo_Tab.detail.caculateTotalSample(target.closest("td").attr("index-col"));

                if (result == "isOKDialog") {

                    //変更列が他列の計算式に使用されている場合に下記内容で計算する.
                    var targetJuryo = HaigoHyo_Tab.detail.element.find(".sample-" + indexCol).findP("juryo_shiagari_g"),
                        id = targetJuryo.closest("td").attr("data-key"),
                        entityJuryo = HaigoHyo_Tab.detail.dataShisaku.entry(id);

                    HaigoHyo_Tab.detail.updateSikiKeisanTotal({ element: targetJuryo.closest("tbody") }, entityJuryo, targetJuryo.closest("td"), undefined).then(function () {
                        //if (App.isFunc(HaigoHyo_Tab.recalculation)) {
                        //    //・原価試算タブ．原料費の再計算を行う
                        //    HaigoHyo_Tab.recalculation("tokuseichi_tab", "量");
                        //} else {
                        //    App.ui.loading.close();
                        //}
                        App.ui.loading.close();
                    })
                    return;
                }

                //if (App.isFunc(HaigoHyo_Tab.recalculation)) {
                //    //・原価試算タブ．原料費の再計算を行う
                //    HaigoHyo_Tab.recalculation("tokuseichi_tab", "量");
                //} else {
                //    App.ui.loading.close();
                //}
                App.ui.loading.close();
            })
        })
        //    .fail(function () {
        //    delete HaigoHyo_Tab.values.isChangeRunning[indexCol][id];
        //})
    };

    /**
    * フォーカスアウトコラムをチェック。
    */
    HaigoHyo_Tab.detail.checkSikiKeisan = function () {
        var deferred = $.Deferred();

        //データが変わらないとき何もしない。
        if (HaigoHyo_Tab.detail.values.currentColChanged == undefined) {
            deferred.resolve(false);
            return deferred;
        }

        //同じ列にいるとき何もしない。
        if ($(document.activeElement.parentNode).attr("index-col") == HaigoHyo_Tab.detail.values.currentColChanged && $(document.activeElement).hasClass("quantity")) {
            deferred.resolve(false);
            return deferred;
        }
        if ($(document.activeElement.parentNode).attr("index-col") == HaigoHyo_Tab.detail.values.currentColChanged && $(document.activeElement).hasClass("betten-kotei")) {
            deferred.resolve(false);
            return deferred;
        }

        //色指定ダイアログが量アリアから呼び出されたとき何もしない
        if ($("#_602_IroShitei_Dialog").is(':visible') && page.dialogs.IroShitei_Dialog.options.isCheckHide) {
            deferred.resolve(false);
            return deferred;
        }

        ////量更新チェック
        //if (!HaigoHyo_Tab.values.isChangeRunning[HaigoHyo_Tab.detail.values.currentColChanged] || !Object.keys(HaigoHyo_Tab.values.isChangeRunning[HaigoHyo_Tab.detail.values.currentColChanged]).length) {
        //    deferred.resolve(false);
        //    return deferred;
        //}

        var element = HaigoHyo_Tab.detail.element,
            sample = element.find(".sample-" + HaigoHyo_Tab.detail.values.currentColChanged),
            changeFirst = sample.find(".change_keta_shosu").first().closest("td"),
            row = changeFirst.closest("tbody"),
            siki = row.find(".col-sample").not(".th-tmpl").find(".siki_keisan"),//all siki
            flagExit = false,//flag check exit : default false
            flgZenkotei = true,
            curentCol = HaigoHyo_Tab.detail.values.currentColChanged,
            curentSiki = changeFirst.findP("siki_keisan").val(),
            keisan = [], keisanUpdate = [];

        var itemEle, siki_keisan, quatity, id, cel, seq_shisaku, entity;
        //all keisan siki on row
        $.each(siki, function (index, item) {
            itemEle = row.find(item);
            siki_keisan = $.trim(itemEle.val());

            cel = itemEle.closest("td");
            seq_shisaku = cel.attr("index-col");
            quatity = cel.find(".change_keta_shosu");
            id = quatity.attr("data-key");

            if (App.isUndefOrNull(id) || !siki_keisan || siki_keisan == "") {
                return true;
            }

            //when exit col use current col in keisan siki : flag check exit true
            if (siki_keisan && (siki_keisan.indexOf("col-" + curentCol) >= 0 || siki_keisan.indexOf(curentSiki) >= 0) && seq_shisaku != curentCol && siki_keisan.replace(curentSiki, "") != "") {
                flagExit = true;
                return false;
            }
        })

        HaigoHyo_Tab.detail.values.currentColChanged = undefined;

        if (flagExit) {
            //use conform dialog
            HaigoHyo_Tab.confirmDialog({
                text: App.messages.app.AP0074
            }).then(function () {

                App.ui.loading.show();
                setTimeout(function () {

                    HaigoHyo_Tab.detail.sikiList(sample, deferred, curentCol);
                }, 500)
            }).fail(function () {
                deferred.resolve("isCancelDialog");
            })
        } else {
            deferred.resolve(false);
        }
        return deferred.promise();
    }

    /**
    * 変更列が他列の計算式に使用されている場合に下記内容で計算する
    */
    HaigoHyo_Tab.detail.sikiList = function (sample, deferred, indexCol) {
        var isNewDeferred = (deferred == undefined);

        if (isNewDeferred) {
            deferred = $.Deferred();
        }

        var element = sample,
            ketaShosu = element.find(".change_keta_shosu:not(:disabled):not([readonly])");

        if (!ketaShosu.length) {
            deferred.resolve(false);
            return deferred;
        }

        HaigoHyo_Tab.detail.values.colChangeSiki = [];

        $.each(ketaShosu, function (index, item) {
            var target = sample.find(item),
                id = target.attr("data-key");

            //if (HaigoHyo_Tab.values.isChangeRunning[indexCol]) {
            //    if (HaigoHyo_Tab.values.isChangeRunning[indexCol][id]) {
            entity = HaigoHyo_Tab.detail.dataShisakuList.entry(id);

            HaigoHyo_Tab.detail.updateSikiKeisan(target.closest("tbody"), $.extend(true, {}, entity), target.closest("td"));
            //    }
            //}
        });

        var eBetten = element.find(".betten-kotei:not(:disabled):not([readonly])");
        if (eBetten.length) {
            var id = eBetten.closest("td").attr("data-key");
            var target = eBetten;
            entity = HaigoHyo_Tab.detail.dataShisaku.entry(id);
            HaigoHyo_Tab.detail.updateSikiKeisanBetten(target.closest("tbody"), $.extend(true, {}, entity), target.closest("td"));

        }

        //if (HaigoHyo_Tab.values.isChangeRunning[indexCol]) {
        //    delete HaigoHyo_Tab.values.isChangeRunning[indexCol];
        //}

        //各工程ごとの合計を再計算する
        if (HaigoHyo_Tab.detail.values.colChangeSiki.length) {
            var processedList = [];
            $.each(HaigoHyo_Tab.detail.values.colChangeSiki, function (index, item) {
                if (processedList.indexOf(item) < 0) {
                    HaigoHyo_Tab.detail.caculateTotalSample(item);
                    processedList.push(item);
                }
            })
        }

        deferred.resolve("isOKDialog");

        if (isNewDeferred) {
            deferred.promise();
        }
        return deferred;
    };

    /**
    * 変更列が他列の計算式に使用されている場合に下記内容で計算する
    */
    HaigoHyo_Tab.detail.updateSikiKeisan = function (row, entity, target) {

        var validate = function (targets) {
            return HaigoHyo_Tab.detail.validator.validate({
                targets: targets,
                state: {
                    tbody: targets,
                    isGridValidation: true
                }
            });
        };

        var element = row,
            siki = row.find(".col-sample").not(".th-tmpl").find(".siki_keisan"),//all siki
            flagExit = false,//flag check exit : default false
            flgZenkotei = true,
            curentCol = entity.seq_shisaku,
            curentSiki = target.findP("siki_keisan").val(),
            keisan = [], keisanUpdate = [];

        var itemEle, siki_keisan, quatity, id, cel, seq_shisaku, entity;
        //all keisan siki on row
        $.each(siki, function (index, item) {
            itemEle = element.find(item);
            siki_keisan = $.trim(itemEle.val());

            cel = itemEle.closest("td");
            seq_shisaku = cel.attr("index-col");
            quatity = cel.find(".change_keta_shosu");
            flg_keisan = cel.find(".flg_keisan").val();
            id = quatity.attr("data-key");

            if (App.isUndefOrNull(id) || !siki_keisan || siki_keisan == "") {
                return true;
            }

            //when exit col use current col in keisan siki : flag check exit true
            if (siki_keisan && (siki_keisan.indexOf("col-" + curentCol) >= 0 || siki_keisan.indexOf(curentSiki) >= 0) && seq_shisaku != curentCol && siki_keisan.replace(curentSiki, "") != "") {
                flagExit = true;
            }

            //when exit col use kotei kesan : flag zenkotei false
            if (flg_keisan) {
                flgZenkotei = false;
            }

            //entity shisaku list
            entity = HaigoHyo_Tab.detail.dataShisakuList.entry(id);
            keisan.push({
                element: cel
                , quatity: quatity
                , entity: entity
                , siki_keisan: siki_keisan
                , seq_shisaku: seq_shisaku
                , flg_keisan: flg_keisan
                , data_prop: quatity.attr("data-prop")
            });

            keisanUpdate.push({
                seq_shisaku: seq_shisaku
                , siki_keisan: siki_keisan
                , lengthSiki: siki_keisan.length
            });
        })

        keisanUpdate = keisanUpdate.sort(function (a, b) {
            return a.lengthSiki == b.lengthSiki ? 0 : +(a.lengthSiki < b.lengthSiki) || -1;
        });

        if (flagExit) {
            //get all keisan siki in group current col
            //begin groupCol is col current
            var groupCol = [{ seq_shisaku: curentCol, siki_keisan: curentSiki }],
                groupKeisan = {};

            var replaceAllSiki = function (str, searchStr, replaceStr) {
                //var str = this;

                // no match exists in string?
                while (str.indexOf(searchStr) >= 0 && str.replace(searchStr, "") != "") {
                    str = str.replace(searchStr, replaceStr)
                }
                // replace and remove first match, and do another recursirve search/replace
                return str;
            }

            var updateSiki = function (siki_keisan, seq_shisaku) {

                $.each(keisanUpdate, function (index, item) {
                    if (item.seq_shisaku == seq_shisaku || Number(item.seq_shisaku) >= Number(seq_shisaku)) {
                        return true;
                    }

                    siki_keisan = replaceAllSiki(siki_keisan, item.siki_keisan, "{col-" + item.seq_shisaku + "}");
                })
                return siki_keisan;
            };

            //check group
            while (flagExit) {

                var siki; sikiNew = [];
                $.each(groupCol, function (index, col) {

                    siki = jQuery.grep(keisan, function (n, i) {
                        //property = col-seq_shisaku
                        return (n.siki_keisan.indexOf("col-" + col.seq_shisaku) >= 0 || (n.siki_keisan.indexOf(col.siki_keisan) >= 0 && n.siki_keisan.replace(col.siki_keisan, "") != ""));
                    });

                    if (siki.length) {
                        $.each(siki, function (index, item) {
                            groupKeisan[item.seq_shisaku] = item;
                            sikiNew.push(item)
                        })
                    }
                })

                //renew groupCol
                if (!sikiNew.length) {
                    flagExit = false;
                } else {
                    groupCol = sikiNew;
                }
            }

            var dataKotei = {},
                dataSikiAllKotei = {};

            //create new class for kotei keisan siki
            if (!flgZenkotei) {
                dataKotei = HaigoHyo_Tab.detail.dataKotei.findAll(function (item, entity) {
                    return entity.state !== App.ui.page.dataSet.status.Deleted;
                });

                var colShi = HaigoHyo_Tab.detail.elementShisakuHead.find(".col-sample:eq(0)");

                while (colShi.length) {
                    if (colShi.hasClass("th-tmpl")) {
                        colShi = colShi.next();
                        continue;
                    }

                    var id = colShi.attr("index-col");
                    if (id) {
                        //property = col- seq_shisaku - cd_kotei
                        $.each(dataKotei, function (index, item) {
                            dataSikiAllKotei["col-" + id + "-" + item.cd_kotei] = 0;
                        })
                    }
                    colShi = colShi.next();
                }
            }

            var slip, dataSiki = {},
                data = row.form().data();
            $.each(data, function (pro, val) {
                //convert data for eval
                if (pro.indexOf("quantity_") >= 0) {
                    slip = pro.split("_");
                    //property = col-seq_shisaku
                    if (slip.length == 4) {
                        if (val != null) {
                            val = (val + "").replace(/,/g, "");
                        }

                        dataSiki["col-" + slip[3]] = val;//page.detail.checkUndefi(val);
                        dataSiki["col-" + slip[3] + "-" + slip[1]] = val;//page.detail.checkUndefi(val);
                    }
                }
            })

            $.each(groupKeisan, function (index, item) {
                //全工程以外
                if (Number(item.flg_keisan) != 1) {
                    var sikiVal = null;

                    try {
                        //format string for eval
                        sikiVal = eval(App.str.format(updateSiki(item.siki_keisan, item.seq_shisaku), dataSiki));
                        if (sikiVal == 0) {
                            sikiVal = {
                                forDisp: (0).toFixed(Number(page.values.keta_shosu)),
                                forData: (0).toFixed(Number(page.values.keta_shosu))
                            };
                            //} else if (sikiVal < 0) {
                            //    sikiVal = {
                            //        forDisp: "",
                            //        forData: ""
                            //    };
                        } else {
                            //convert shosu
                            sikiVal = page.detail.formatNumber(sikiVal, Number(page.values.keta_shosu));
                        }
                    } catch (err) {
                        sikiVal = {
                            forDisp: "",//0,
                            forData: ""//0
                        };
                    }

                    if (item.quatity.is(":disabled") || item.quatity.is('[readonly]')) {
                        //何もしません
                    } else {
                        //update for shisaku list
                        item.entity.quantity = sikiVal.forData;
                        HaigoHyo_Tab.detail.dataShisakuList.update(item.entity);

                        //reset new value in column
                        item.quatity.val(sikiVal.forDisp);
                        validate(item.quatity);
                    }
                    slip = item.data_prop.split("_");
                    dataSiki["col-" + slip[3]] = sikiVal.forData;

                    //各工程ごとの合計を再計算する
                    HaigoHyo_Tab.detail.values.colChangeSiki.push(item.entity.seq_shisaku);
                    //HaigoHyo_Tab.detail.caculateTotalSample(item.entity.seq_shisaku);
                } else {
                    //計算です。
                    var sikiVal = null;

                    //renew data siki form data siki and data prop new
                    dataSiki = $.extend(true, {}, dataSikiAllKotei, dataSiki);

                    try {

                        //format string for eval
                        sikiVal = eval(App.str.format(updateSiki(item.siki_keisan, item.seq_shisaku), dataSiki));
                        if (sikiVal == 0) {
                            sikiVal = {
                                forDisp: (0).toFixed(Number(page.values.keta_shosu)),
                                forData: (0).toFixed(Number(page.values.keta_shosu))
                            };
                            //} else if (sikiVal < 0) {
                            //    sikiVal = {
                            //        forDisp: "",
                            //        forData: ""
                            //    };
                        } else {
                            //convert shosu
                            sikiVal = page.detail.formatNumber(sikiVal, Number(page.values.keta_shosu));
                        }
                    } catch (err) {
                        sikiVal = {
                            forDisp: "",//0,
                            forData: ""//0
                        };
                    }
                    if (item.quatity.is(":disabled") || item.quatity.is('[readonly]')) {
                        //何もしません
                    } else {
                        //update for shisaku list
                        item.entity.quantity = sikiVal.forData;
                        HaigoHyo_Tab.detail.dataShisakuList.update(item.entity);

                        //reset new value in column
                        item.quatity.val(sikiVal.forDisp);
                        validate(item.quatity);
                    }
                    slip = item.data_prop.split("_");
                    dataSiki["col-" + slip[3] + "-" + slip[1]] = sikiVal.forData;
                    //各工程ごとの合計を再計算する
                    HaigoHyo_Tab.detail.values.colChangeSiki.push(item.entity.seq_shisaku);
                    //HaigoHyo_Tab.detail.caculateTotalSample(item.entity.seq_shisaku);
                }
            })
        }
    }




    /**
    * 変更列が他列の計算式に使用されている場合に下記内容で計算する
    */
    HaigoHyo_Tab.detail.updateSikiKeisanBetten = function (row, entity, target) {

        var validate = function (targets) {
            return HaigoHyo_Tab.detail.validator.validate({
                targets: targets,
                state: {
                    tbody: targets,
                    isGridValidation: true
                }
            });
        };

        var element = row,
            siki = row.find(".col-sample").not(".th-tmpl").find(".siki_keisan"),//all siki
            flagExit = false,//flag check exit : default false
            flgZenkotei = true,
            curentCol = entity.seq_shisaku,
            curentSiki = entity.siki_keisan,
            keisan = [], keisanUpdate = [];

        var itemEle, siki_keisan, quatity, id, cel, seq_shisaku, entity;
        //all keisan siki on row
        $.each(row.find(".col-sample").not(".th-tmpl"), function (index, item) {
            item = $(item);
            var quatity = item.find("input")
            var id = item.attr("data-key");
            //entity shisaku list
            entity = HaigoHyo_Tab.detail.dataShisaku.entry(id);
            var siki_keisan = entity.siki_keisan;
            var seq_shisaku = entity.seq_shisaku;
            if (!siki_keisan || siki_keisan == "") {
                return true;
            }
            //when exit col use current col in keisan siki : flag check exit true
            if (siki_keisan && (siki_keisan.indexOf("col-" + curentCol) >= 0 || siki_keisan.indexOf(curentSiki) >= 0) && seq_shisaku != curentCol && siki_keisan.replace(curentSiki, "") != "") {
                flagExit = true;
            }
            flgZenkotei = false;
            keisan.push({
                element: item
                , quatity: quatity
                , entity: entity
                , siki_keisan: siki_keisan
                , seq_shisaku: seq_shisaku
                , flg_keisan: false
                , data_prop: quatity.attr("data-prop")
            });
            keisanUpdate.push({
                seq_shisaku: seq_shisaku
                , siki_keisan: siki_keisan
                , lengthSiki: siki_keisan.length
            });
        })
        
        keisanUpdate = keisanUpdate.sort(function (a, b) {
            return a.lengthSiki == b.lengthSiki ? 0 : +(a.lengthSiki < b.lengthSiki) || -1;
        });

        if (flagExit) {
            //get all keisan siki in group current col
            //begin groupCol is col current
            var groupCol = [{ seq_shisaku: curentCol, siki_keisan: curentSiki }],
                groupKeisan = {};

            var replaceAllSiki = function (str, searchStr, replaceStr) {
                //var str = this;

                // no match exists in string?
                while (str.indexOf(searchStr) >= 0 && str.replace(searchStr, "") != "") {
                    str = str.replace(searchStr, replaceStr)
                }
                // replace and remove first match, and do another recursirve search/replace
                return str;
            }

            var updateSiki = function (siki_keisan, seq_shisaku) {

                $.each(keisanUpdate, function (index, item) {
                    if (item.seq_shisaku == seq_shisaku || Number(item.seq_shisaku) >= Number(seq_shisaku)) {
                        return true;
                    }

                    siki_keisan = replaceAllSiki(siki_keisan, item.siki_keisan, "{col-" + item.seq_shisaku + "}");
                })
                return siki_keisan;
            };

            //check group
            while (flagExit) {

                var siki; sikiNew = [];
                $.each(groupCol, function (index, col) {

                    siki = jQuery.grep(keisan, function (n, i) {
                        //property = col-seq_shisaku
                        return (n.siki_keisan.indexOf("col-" + col.seq_shisaku) >= 0 || (n.siki_keisan.indexOf(col.siki_keisan) >= 0 && n.siki_keisan.replace(col.siki_keisan, "") != ""));
                    });

                    if (siki.length) {
                        $.each(siki, function (index, item) {
                            groupKeisan[item.seq_shisaku] = item;
                            sikiNew.push(item)
                        })
                    }
                })

                //renew groupCol
                if (!sikiNew.length) {
                    flagExit = false;
                } else {
                    groupCol = sikiNew;
                }
            }

            var dataKotei = {},
                dataSikiAllKotei = {};

            //create new class for kotei keisan siki
            if (!flgZenkotei) {
                dataKotei = HaigoHyo_Tab.detail.dataKotei.findAll(function (item, entity) {
                    return entity.state !== App.ui.page.dataSet.status.Deleted;
                });

                var colShi = HaigoHyo_Tab.detail.elementShisakuHead.find(".col-sample:eq(0)");

                while (colShi.length) {
                    if (colShi.hasClass("th-tmpl")) {
                        colShi = colShi.next();
                        continue;
                    }

                    var id = colShi.attr("index-col");
                    if (id) {
                        //property = col- seq_shisaku - cd_kotei
                        $.each(dataKotei, function (index, item) {
                            dataSikiAllKotei["col-" + id + "-" + item.cd_kotei] = 0;
                        })
                    }
                    colShi = colShi.next();
                }
            }

            var slip, dataSiki = {};

            $.each(row.find(".col-sample").not(".th-tmpl"), function (index, item) {
                item = $(item);
                var colInx = item.attr("index-col"),
                    val = item.find("input").val().replace(/,/g, "");
                dataSiki["col-" + colInx] = App.isUndefOrNullOrStrEmpty(val) ? null : val;
                //dataSiki["col-" + colInx] = val;
            });
            //    data = row.form().data();
            //$.each(data, function (pro, val) {
            //    //convert data for eval
            //    if (pro.indexOf("quantity_") >= 0) {
            //        slip = pro.split("_");
            //        //property = col-seq_shisaku
            //        if (slip.length == 4) {
            //            if (val != null) {
            //                val = (val + "").replace(/,/g, "");
            //            }

            //            dataSiki["col-" + slip[3]] = val;//page.detail.checkUndefi(val);
            //            dataSiki["col-" + slip[3] + "-" + slip[1]] = val;//page.detail.checkUndefi(val);
            //        }
            //    }
            //})

            $.each(groupKeisan, function (index, item) {
                //全工程以外
                if (Number(item.flg_keisan) != 1) {
                    var sikiVal = null;
                    var cd_kotei = item.data_prop.replace("total_finish_weight", "");

                    try {
                        //format string for eval
                        var fx = updateSiki(item.siki_keisan, item.seq_shisaku);
                        var fxIns = page.detail.parseFormular(fx);
                        if (fxIns.checkNullParam(dataSiki)) {
                            throw new Error('Parameter is null!');
                        }
                        sikiVal = eval(App.str.format(fx, dataSiki));
                        if (sikiVal == 0) {
                            sikiVal = {
                                forDisp: (0).toFixed(Number(page.values.keta_shosu)),
                                forData: (0).toFixed(Number(page.values.keta_shosu))
                            };
                            //} else if (sikiVal < 0) {
                            //    sikiVal = {
                            //        forDisp: "",
                            //        forData: ""
                            //    };
                        } else {
                            //convert shosu
                            sikiVal = page.detail.formatNumber(sikiVal, Number(page.values.keta_shosu));
                        }
                    } catch (err) {
                        sikiVal = {
                            forDisp: "",//0,
                            forData: ""//0
                        };
                    }

                    if (item.quatity.is(":disabled") || item.quatity.is('[readonly]')) {
                        //何もしません
                    } else {
                        //update for shisaku list
                        item.entity[item.data_prop] = sikiVal.forData;
                        HaigoHyo_Tab.detail.dataShisaku.update(item.entity);

                        var shisakuItem = HaigoHyo_Tab.detail.element.find(".sample-" + item.seq_shisaku).find(".quantity_" + cd_kotei);
                        $.each(shisakuItem, function (i, shisa) {
                            shisa = $(shisa);
                            var sid = shisa.attr("data-key");
                            if (App.isUndefOrNull(sid)) {
                                return;
                            }
                            dat = HaigoHyo_Tab.detail.dataShisakuList.entry(sid);
                            dat["juryo_shiagari_seq"] = sikiVal.forData;
                            HaigoHyo_Tab.detail.dataShisakuList.update(dat);
                        })

                        //reset new value in column
                        item.quatity.val(sikiVal.forDisp);
                        validate(item.quatity);
                    }
                    //slip = item.data_prop.replace("total_finish_weight", "");
                    dataSiki["col-" + cd_kotei] = sikiVal.forData;

                    //各工程ごとの合計を再計算する
                    HaigoHyo_Tab.detail.values.colChangeSiki.push(item.entity.seq_shisaku);
                    //HaigoHyo_Tab.detail.caculateTotalSample(item.entity.seq_shisaku);
                }
                //else {
                //    //計算です。
                //    var sikiVal = null;

                //    //renew data siki form data siki and data prop new
                //    dataSiki = $.extend(true, {}, dataSikiAllKotei, dataSiki);

                //    try {

                //        //format string for eval
                //        sikiVal = eval(App.str.format(updateSiki(item.siki_keisan, item.seq_shisaku), dataSiki));
                //        if (sikiVal == 0) {
                //            sikiVal = {
                //                forDisp: (0).toFixed(Number(page.values.keta_shosu)),
                //                forData: (0).toFixed(Number(page.values.keta_shosu))
                //            };
                //            //} else if (sikiVal < 0) {
                //            //    sikiVal = {
                //            //        forDisp: "",
                //            //        forData: ""
                //            //    };
                //        } else {
                //            //convert shosu
                //            sikiVal = page.detail.formatNumber(sikiVal, Number(page.values.keta_shosu));
                //        }
                //    } catch (err) {
                //        sikiVal = {
                //            forDisp: "",//0,
                //            forData: ""//0
                //        };
                //    }
                //    if (item.quatity.is(":disabled") || item.quatity.is('[readonly]')) {
                //        //何もしません
                //    } else {
                //        //update for shisaku list
                //        item.entity.quantity = sikiVal.forData;
                //        HaigoHyo_Tab.detail.dataShisaku.update(item.entity);

                //        //reset new value in column
                //        item.quatity.val(sikiVal.forDisp);
                //        validate(item.quatity);
                //    }
                //    slip = item.data_prop.replace("total_finish_weight", "");
                //    dataSiki["col-" + slip + "-" + slip[1]] = sikiVal.forData;
                //    //各工程ごとの合計を再計算する
                //    HaigoHyo_Tab.detail.values.colChangeSiki.push(item.entity.seq_shisaku);
                //    //HaigoHyo_Tab.detail.caculateTotalSample(item.entity.seq_shisaku);
                //}
            })
        }
    }

    /**
    * control event change juryo shisaku
    */
    HaigoHyo_Tab.detail.changeJuryoShisaku = function (e, row) {
        var target = $(e.target),
            indexCol = target.parent().attr("index-col"),
            property = target.attr("data-prop"),
            id = target.closest("td").attr("data-key");

        if (App.isUndefOrNull(property) || App.isUndefOrNull(id)) {
            return;
        }

        var validate = function (targets) {
            return HaigoHyo_Tab.detail.validator.validate({
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

            if (!HaigoHyo_Tab.detail.values.juryoChanged) {
                HaigoHyo_Tab.detail.values.currentColChanged = indexCol;
            }

            HaigoHyo_Tab.detail.values.juryoChanged = undefined;

            var entity = HaigoHyo_Tab.detail.dataShisaku.entry(id),
                fixedPosition = target.attr("data-number-format-tofixed"),
                value = page.detail.formatNumber(valtarget(target), fixedPosition);

            entity[property] = value.forData;
            HaigoHyo_Tab.detail.dataShisaku.update(entity);

            target.val(value.forDisp);

            //HaigoHyo_Tab.detail.updateSikiKeisanTotal(row, entity, target.closest("td")).then(function () {
            HaigoHyo_Tab.detail.checkSikiKeisanTotal(row, entity, target.closest("td")).then(function (result) {

                if (result == "isOkTotal") {
                    HaigoHyo_Tab.detail.sikiList(HaigoHyo_Tab.detail.element.find(".sample-" + indexCol), undefined, indexCol).always(function () {

                        //その他・加食タイプ
                        //if (HaigoHyo_Tab.values.pt_kotei == App.settings.app.pt_kotei.sonohokaeki) {
                        //    if (App.isFunc(HaigoHyo_Tab.recalculation)) {
                        //        HaigoHyo_Tab.recalculation("genkashisan_tab", "合計仕上重量（kg）");
                        //    } else {
                        //        App.ui.loading.close();
                        //    }
                        //} else {
                            App.ui.loading.close();
                        //}
                    })

                    return;
                }

                //その他・加食タイプ
                //if (HaigoHyo_Tab.values.pt_kotei == App.settings.app.pt_kotei.sonohokaeki) {
                //    if (App.isFunc(HaigoHyo_Tab.recalculation)) {
                //        HaigoHyo_Tab.recalculation("genkashisan_tab", "合計仕上重量（kg）");
                //    } else {
                //        App.ui.loading.close();
                //    }
                //} else {
                    App.ui.loading.close();
                //}
            })
        })
    };

    /**
    * フォーカスアウトコラムをチェック。
    */
    HaigoHyo_Tab.detail.checkSikiKeisanTotal = function (row, entity, target) {
        var deferred = $.Deferred();

        //データが変わらないとき何もしない。
        if (HaigoHyo_Tab.detail.values.currentColChanged == undefined) {
            deferred.resolve(false);
            return deferred;
        }

        //同じ列にいるとき何もしない。
        if ($(document.activeElement.parentNode).attr("index-col") == HaigoHyo_Tab.detail.values.currentColChanged && $(document.activeElement).hasClass("quantity")) {
            deferred.resolve(false);
            return deferred;
        }

        var element = row.element,
            flagExit = false,//flag check exit : default false
            flg_keisan = row.element.find(".col-sample").not(".th-tmpl").find(".flg_keisan"),
            curentCol = entity.seq_shisaku,
            curentSiki = target.findP("siki_keisan").val(),
            keisan = [], keisanUpdate = [];

        var itemEle, flagKeisan, cel, id, seq_shisaku, siki_keisan;
        $.each(flg_keisan, function (index, item) {

            itemEle = element.find(item);
            flagKeisan = itemEle.val();

            if (Number(flagKeisan) != 1) {
                cel = itemEle.closest("td");
                id = cel.attr("data-key");
                seq_shisaku = cel.attr("index-col");

                siki_keisan = cel.findP("siki_keisan").val();

                if (App.isUndefOrNull(id) || !siki_keisan || siki_keisan == "") {
                    return true;
                }

                if (siki_keisan && (siki_keisan.indexOf("col-" + curentCol) >= 0 || siki_keisan.indexOf(curentSiki) >= 0) && seq_shisaku != curentCol) {
                    flagExit = true;
                    return false;
                }
            }
        })

        HaigoHyo_Tab.detail.values.currentColChanged = undefined;

        if (flagExit) {
            HaigoHyo_Tab.confirmDialog({
                text: App.messages.app.AP0074
            }).then(function () {

                App.ui.loading.show();
                setTimeout(function () {
                    HaigoHyo_Tab.detail.updateSikiKeisanTotal(row, entity, target, deferred);
                }, 500)
            }).fail(function () {
                deferred.resolve(false);
            })
        } else {
            deferred.resolve();
        }

        return deferred.promise();
    };

    /**
    * 変更列が他列の計算式に使用されている場合に下記内容で計算する.
    */
    HaigoHyo_Tab.detail.updateSikiKeisanTotal = function (row, entity, target, deferred) {
        var isNewDeferred = (deferred == undefined);
        var validate = function (targets) {
            return HaigoHyo_Tab.detail.validator.validate({
                targets: targets,
                state: {
                    tbody: targets,
                    isGridValidation: true
                }
            });
        };

        if (isNewDeferred) {
            deferred = $.Deferred();
        }

        var element = row.element,
            flagExit = false,//flag check exit : default false
            flg_keisan = row.element.find(".col-sample").not(".th-tmpl").find(".flg_keisan"),
            curentCol = entity.seq_shisaku,
            curentSiki = target.findP("siki_keisan").val(),
            keisan = [], keisanUpdate = [];

        var itemEle, flagKeisan, cel, id, seq_shisaku, quatity, siki_keisan;
        $.each(flg_keisan, function (index, item) {

            itemEle = element.find(item);
            flagKeisan = itemEle.val();

            if (Number(flagKeisan) != 1) {
                cel = itemEle.closest("td");
                id = cel.attr("data-key");
                seq_shisaku = cel.attr("index-col");
                quatity = cel.findP("juryo_shiagari_g");

                siki_keisan = cel.findP("siki_keisan").val();

                if (App.isUndefOrNull(id) || !siki_keisan || siki_keisan == "") {
                    return true;
                }

                if (siki_keisan && (siki_keisan.indexOf("col-" + curentCol) >= 0 || siki_keisan.indexOf(curentSiki) >= 0) && seq_shisaku != curentCol) {
                    flagExit = true;
                }

                //entity shisaku list
                entity = HaigoHyo_Tab.detail.dataShisaku.entry(id);
                keisan.push({
                    element: cel
                    , quatity: quatity
                    , entity: entity
                    , siki_keisan: siki_keisan
                    , seq_shisaku: seq_shisaku
                });

                keisanUpdate.push({
                    seq_shisaku: seq_shisaku
                    , siki_keisan: siki_keisan
                    , lengthSiki: siki_keisan.length
                });
            }
        })

        keisanUpdate = keisanUpdate.sort(function (a, b) {
            return a.lengthSiki == b.lengthSiki ? 0 : +(a.lengthSiki < b.lengthSiki) || -1;
        });

        if (flagExit) {
            var groupCol = [{ seq_shisaku: curentCol, siki_keisan: curentSiki }],
                groupKeisan = {};

            var replaceAllSiki = function (str, searchStr, replaceStr) {
                //var str = this;

                // no match exists in string?
                while (str.indexOf(searchStr) >= 0 && str.replace(searchStr, "") != "") {
                    str = str.replace(searchStr, replaceStr)
                }
                // replace and remove first match, and do another recursirve search/replace
                return str;
            }

            var updateSiki = function (siki_keisan, seq_shisaku) {

                $.each(keisanUpdate, function (index, item) {
                    if (item.seq_shisaku == seq_shisaku || Number(item.seq_shisaku) >= Number(seq_shisaku)) {
                        return true;
                    }

                    siki_keisan = replaceAllSiki(siki_keisan, item.siki_keisan, "{col-" + item.seq_shisaku + "}");
                })
                return siki_keisan;
            };

            //check group
            while (flagExit) {
                var siki; sikiNew = [];
                $.each(groupCol, function (index, col) {

                    siki = jQuery.grep(keisan, function (n, i) {
                        //property = col-seq_shisaku
                        return (n.siki_keisan.indexOf("col-" + col.seq_shisaku) >= 0 || (n.siki_keisan.indexOf(col.siki_keisan) >= 0 && n.siki_keisan.replace(col.siki_keisan, "") != ""));
                    });

                    if (siki.length) {
                        $.each(siki, function (index, item) {
                            groupKeisan[item.seq_shisaku] = item;
                            sikiNew.push(item)
                        })
                    }
                })
                //renew groupCol
                if (!sikiNew.length) {
                    flagExit = false;
                } else {
                    groupCol = sikiNew;
                }
            }

            //create data row total weight
            var totalWeightEl = HaigoHyo_Tab.detail.element.find(".item_total_finish_weight .col-sample").not(".th-tmpl"),
                dataTotal = {};

            var ele, index;
            $.each(totalWeightEl, function (index) {
                ele = HaigoHyo_Tab.detail.element.find(totalWeightEl[index]);
                index = ele.attr("index-col");
                var val = ele.findP("juryo_shiagari_g").val()
                if (val != null) {
                    val = (val + "").replace(/,/g, "");
                }
                //dataTotal["col-" + index] = page.detail.checkUndefi(ele.findP("juryo_shiagari_g").val());
                dataTotal["col-" + index] = val;
            })

            $.each(groupKeisan, function (index, item) {
                var sikiVal = null, slip;

                try {
                    //format string for eval
                    sikiVal = eval(App.str.format(updateSiki(item.siki_keisan, item.seq_shisaku), dataTotal));
                    if (sikiVal == 0) {
                        sikiVal = {
                            forDisp: (0).toFixed(4),
                            forData: (0).toFixed(4)
                        };
                        //} else if (sikiVal < 0) {
                        //    sikiVal = {
                        //        forDisp: "",
                        //        forData: ""
                        //    };
                    } else {
                        //convert shosu
                        //page.values.keta_shosu
                        sikiVal = page.detail.formatNumber(sikiVal, 4);
                    }
                } catch (err) {
                    sikiVal = {
                        forDisp: "",//0,
                        forData: ""//0
                    };
                }
                if (item.quatity.is(":disabled") || item.quatity.is('[readonly]')) {
                    //何もしません
                } else {
                    //update for shisaku list
                    item.entity.juryo_shiagari_g = sikiVal.forData;
                    HaigoHyo_Tab.detail.dataShisaku.update(item.entity);

                    //reset new value in column
                    item.quatity.val(sikiVal.forDisp);
                    validate(item.quatity);
                }

                //renew data this column
                dataTotal["col-" + item.entity.seq_shisaku] = sikiVal.forData;
            })

            deferred.resolve("isOkTotal");
        } else {
            deferred.resolve("isOkTotal");
        }

        if (isNewDeferred) {
            return deferred.promise();
        }

        return deferred;
    };

    /**
    * 配合表①の「合計仕上重量」をクリアする
    */
    HaigoHyo_Tab.detail.resetAllJuryoShisaku = function () {
        var deferred = $.Deferred();
        var element = HaigoHyo_Tab.detail.element.find(".item_total_finish_weight"),
            row;

        HaigoHyo_Tab.detail.dataTable.dataTable("getRow", element, function (rowObject) {
            row = rowObject.element;
        });

        var quantity = row.find(".col-sample");

        if (!quantity.length) {
            deferred.resolve();
            return;
        }

        var index = 0,
            sample;

        while (quantity[index]) {
            sample = element.find(quantity[index]);
            index += 1;

            var id = sample.attr("data-key");

            if (sample.hasClass("th-tmpl") || App.isUndefOrNull(id)) {
                continue;
            }

            var entity = HaigoHyo_Tab.detail.dataShisaku.entry(id);

            entity["juryo_shiagari_g"] = null;
            HaigoHyo_Tab.detail.dataShisaku.update(entity);

            sample.findP("juryo_shiagari_g").val("");
        }
        deferred.resolve();

        return deferred.promise();
    }

    /**
    * control event change juryo shisaku
    */
    HaigoHyo_Tab.detail.changeJuryoShisakuList = function (e, row) {
        var element = HaigoHyo_Tab.detail.element,
            target = $(e.target),
            property = target.attr("data-prop"),
            cd_kotei = property.replace("total_finish_weight", ""),
            indexCol = target.closest("td").attr("index-col"),
            elementShisaku = element.find(".sample-" + indexCol),
            shisakuItem = elementShisaku.find(".quantity_" + cd_kotei);

        if (!shisakuItem.length) {
            return;
        }

        var id,
            index = 0,
            itemShisaku, entity;

        var validate = function (targets) {
            return HaigoHyo_Tab.detail.validator.validate({
                targets: targets,
                state: {
                    tbody: row,
                    isGridValidation: true
                }
            });
        };

        var shisakuID = target.closest("td").attr("data-key"),
            shisakuData = HaigoHyo_Tab.detail.dataShisaku.entry(shisakuID);
        shisakuData[property] = target.closest("td").form().data()[property];


        //フラグ変更画面
        page.values.isChange = true;
        validate(target).then(function () {
            var fixedPosition = target.attr("data-number-format-tofixed");
            var value = page.detail.formatNumber(valtarget(target), fixedPosition);
            while (shisakuItem[index]) {
                id = element.find(shisakuItem[index]).attr("data-key");

                if (App.isUndefOrNull(id)) {
                    index += 1;
                    continue;
                }

                entity = HaigoHyo_Tab.detail.dataShisakuList.entry(id);
                entity["juryo_shiagari_seq"] = value.forData;

                HaigoHyo_Tab.detail.dataShisakuList.update(entity);
                index += 1;
            }
            //conver 
            target.val(value.forDisp);


            //update juryo_shiagari_g
            if (target.hasClass("betten-kotei")) {
                HaigoHyo_Tab.detail.values.currentColChanged = indexCol;
                var dataJuryo = elementShisaku.find(".item_total_weight_cacu"),
                    totalJuryo = 0,
                    defVal = new BigNumber(0).toFixed(fixedPosition),
                    hasSauce = false,
                    hasBetten = false, wt_sauce, wt_betten;

                $.each(dataJuryo, function (ind, elem) {
                    elem = $(elem);
                    if (elem.hasClass("betten-kotei")) {
                        hasBetten = true;
                        wt_betten = elem.val().replace(/,/g, "");
                    } else {
                        if (elem.data("hasSauce")) {
                            hasSauce = true;
                            wt_sauce = elem.val().replace(/,/g, "");
                        }
                    }
                });
                var r12 = HaigoHyo_Tab.detail.totalFnWt(wt_sauce, hasSauce, wt_betten, hasBetten);
                //$.each(dataJuryo, function (pro, val) {
                //    totalJuryo = new BigNumber(stringNumbers(totalJuryo)).plus(stringNumbers(page.detail.checkUndefi(val))).toNumber();
                //});
                totalJuryo = r12.getDisplayValue();
                var focusTarget = $(document.activeElement);
                elementShisaku.findP("juryo_shiagari_g").val(totalJuryo);
                if (focusTarget.attr("data-prop") === "juryo_shiagari_g") {
                    focusTarget.focus();
                }
                HaigoHyo_Tab.detail.values.juryoChanged = true;

                HaigoHyo_Tab.detail.checkSikiKeisan().then(function (result) {
                    App.ui.loading.close();
                });
            }
            elementShisaku.findP("juryo_shiagari_g").change();
        })
    };

    /**
    * Sum Sauce wt with betten wt
    */
    HaigoHyo_Tab.detail.totalFnWt = function (wt_sauce, hasSauce, wt_betten, hasBetten) {
        var result = HaigoHyo_Tab.detail.getCalcResNode("r12", "合計仕上重量");
        if (hasBetten || hasSauce) {
            if (!wt_betten && hasSauce) wt_betten = 0;
            result.setValue(0);
            result = result.plus(wt_betten);
            if (hasSauce) {
                result = result.plus(wt_sauce);
            }
        }
        return result;
    }

    /**
    * control event change on sample area
    */
    HaigoHyo_Tab.detail.changeShisaku = function (e) {
        var target = $(e.target),
            property = target.attr("data-prop"),
            id = target.closest("th").attr("data-key"),
            entity;

        if (!id) {
            id = target.closest("td").attr("data-key");
        }

        if (App.isUndefOrNull(property) || App.isUndefOrNull(id)) {
            return;
        }

        entity = HaigoHyo_Tab.detail.dataShisaku.entry(id);

        //フラグ変更画面
        page.values.isChange = true;
        HaigoHyo_Tab.detail.elementShisaku.validation().validate({
            targets: target
        }).done(function () {
            entity[property] = valtarget(target);

            //印刷FG
            if (property == "flg_print") {
                entity[property] = target[0].checked;
            }

            HaigoHyo_Tab.detail.dataShisaku.update(entity);

            //check all サンプルNo
            if (property == "nm_sample") {
                HaigoHyo_Tab.detail.elementShisaku.validation().validate({
                    targets: HaigoHyo_Tab.detail.elementShisaku.find(".col-sample").not(".th-tmpl").find(".has-error, .error")
                })

                if (App.isFunc(HaigoHyo_Tab.changeSample)) {
                    HaigoHyo_Tab.changeSample(entity);
                }
            }

            if (["su_fc", "su_brix"].indexOf(property) >= 0) {
                HaigoHyo_Tab.detail.caculateTotalSample(entity.seq_shisaku, "Change-FC-Brix");
            }
        }).fail(function () {
            if (["su_fc", "su_brix"].indexOf(property) >= 0) {
                entity[property] = "";
            }
            if (["nm_sample"].indexOf(property) >= 0) {
                entity[property] = valtarget(target);
            }
        });
    };

    /**
    * update all genryo in kotei
    */
    HaigoHyo_Tab.detail.updateHaigoChildren = function (target, row, isRecalcu) {
        var property = target.attr("data-prop"),
            quality = row.element.form().data()[property]; //target.val();

        if (!property == "zoku_kotei" && !property == "nm_kotei") {
            return;
        }

        //フラグ変更画面
        page.values.isChange = true;

        var row_komodo = row.element.next();
        while (!row_komodo.hasClass("row-kotei") && !row_komodo.hasClass("item_total_kotei_tml")) {
            var id = row_komodo.attr("data-key")
                , entity;

            if (App.isUndefOrNull(id)) {
                row_komodo = row_komodo.next();
                continue;
            }

            entity = HaigoHyo_Tab.detail.dataHaigo.entry(id);

            entity[property] = quality;
            HaigoHyo_Tab.detail.dataHaigo.update(entity);

            row_komodo = row_komodo.next();
        }

        if (isRecalcu) {
            //if (App.isFunc(HaigoHyo_Tab.recalculation)) {
            //    HaigoHyo_Tab.recalculation("tokuseichi_tab", "油含有率");
            //} else {
            //    App.ui.loading.close();
            //}
            App.ui.loading.close();
        }
    }

    /**
    * Enable, disable, set default value .... for genryo tani
    */
    HaigoHyo_Tab.detail.controlGenryoTani = function (target, isSetDefVal, isPreventChange) {
        if (!target) return;
        var tbody = target.closest("tbody"),
            kotei_index = tbody.form().data().sort_kotei,
            genryoList = HaigoHyo_Tab.detail.element.find("tbody.new.row-genryo");
        $.each(genryoList, function (i, d) {
            d = $(d);
            if (d.form().data().sort_kotei === kotei_index) {
                HaigoHyo_Tab.detail.controlGenryoTaniByKotei(target.val(), d, isSetDefVal, isPreventChange);
            }
        });
    }

    /**
    * control genryo tani by zoku_kotei
    */
    HaigoHyo_Tab.detail.controlGenryoTaniByKotei = function (zoku_kotei, d, isSetDefVal, isPreventChange) {
        if (isSetDefVal) {
            d.findP("cd_tani").val(App.settings.app.genryo_tani_AOH.Kg);
        }
        if (zoku_kotei === App.settings.app.kotei_AOH.Betten) {
            d.findP("cd_tani").prop("disabled", true);
            d.findP("cd_tani").val(App.settings.app.genryo_tani_AOH.Kg);        // Bug #1437
        } else {
            d.findP("cd_tani").prop("disabled", false);
        }
        if (!isPreventChange) {
            d.findP("cd_tani").change();
        }
    }

    /**
    * control genryo tani by selected row
    */
    HaigoHyo_Tab.detail.controlGenryoTaniByRow = function (row, isSetDefVal) {
        if (!row) return;
        var dHaigo = HaigoHyo_Tab.detail.dataHaigo.entry(row.attr("data-key"));
        HaigoHyo_Tab.detail.controlGenryoTaniByKotei(dHaigo.zoku_kotei, row, isSetDefVal);
    }


    /**
    * control event change on haigo area
    */
    HaigoHyo_Tab.detail.changeHaigo = function (e, row) {
        var target = $(e.target),
            property = target.attr("data-prop");

        var validate = function (targets) {
            return HaigoHyo_Tab.detail.validator.validate({
                targets: targets,
                state: {
                    tbody: row,
                    isGridValidation: true
                }
            });
        };

        //フラグ変更画面
        page.values.isChange = true;
        if (property === "zoku_kotei" && row.element.hasClass("row-kotei")) {
            target.data("isUpdated", true);
        }
        validate(target).then(function () {
            //工程チェックボックス
            if (property == "col1_kotei") {
                HaigoHyo_Tab.detail.controlSelectKotei(target);
                return;
            }

            //材料チェックボックス
            if (property == "col3_genryo") {
                return;
            };

            //工程列
            if (row.element.hasClass("row-kotei")) {

                var id = row.element.attr("data-key"),
                    entity = HaigoHyo_Tab.detail.dataKotei.entry(id);

                entity[property] = row.element.form().data()[property];// target.val();
                HaigoHyo_Tab.detail.dataKotei.update(entity);

                HaigoHyo_Tab.detail.updateHaigoChildren(target, row, true);
                if (property === "zoku_kotei") {
                    HaigoHyo_Tab.detail.controlGenryoTani(target, true);
                    // Recheck other kotei
                    $.each(HaigoHyo_Tab.detail.element.find("tbody.new.row-kotei").findP("zoku_kotei"), function (i, e) {
                        e = $(e);
                        if (e.data("isUpdated")) {
                            validate(e);
                            e.data("isUpdated", false);
                        }
                    });
                }
                return;
            }

            //材料列
            var id = row.element.attr("data-key"),
            entity = HaigoHyo_Tab.detail.dataHaigo.entry(id);

            entity[property] = valtarget(target);
            HaigoHyo_Tab.detail.dataHaigo.update(entity);

            if (["zoku_kotei", "tanka", "budomari", "cd_tani"].indexOf(property) >= 0) {
                HaigoHyo_Tab.detail.bfCalculateTotal("HaigoChange");
            }

            if (property == "cd_genryo") {

                var genryo = $.trim(entity[property]);
                if ($.trim(entity[property]).length && App.isNumeric(entity[property])) {

                    genryo = App.common.getFullString($.trim(entity[property]), "000000");
                    entity[property] = genryo;
                    HaigoHyo_Tab.detail.dataHaigo.update(entity);

                    target.val(genryo);
                }

                if (entity[property].length) {
                    //原料コードロストフォーカス
                    HaigoHyo_Tab.detail.changeGenryo(entity, row.element, genryo).then(function () {
                        //再計算処理」を実行
                        //if (App.isFunc(HaigoHyo_Tab.recalculation)) {
                        //    HaigoHyo_Tab.recalculation("tokuseichi_tab", "油含有率");
                        //} else {
                        //    App.ui.loading.close();
                        //}
                        App.ui.loading.close();
                    })
                } else {
                    var shisakuHin = HaigoHyo_Tab.getDataHeader();

                    //原料名  
                    row.element.findP("nm_genryo").prop("readonly", false);
                    //単価（※
                    row.element.findP("tanka").prop("readonly", false);
                    //原料原本
                    entity["cd_genryo_gry"] = null;
                    //歩留原本
                    entity["budomari_gry"] = null;
                    //Request #14303 : 基本、tr_haigoと同じデータ
                    ////担当会社
                    //entity["cd_kaisha_genryo"] = shisakuHin.cd_kaisha;
                    ////担当工場
                    //entity["cd_kojo_genryo"] = shisakuHin.cd_kojo;

                    //フォントを赤色・太文字に変える。
                    HaigoHyo_Tab.detail.iroBudomari(row.element, entity);

                    HaigoHyo_Tab.detail.dataHaigo.update(entity);

                    //再計算処理」を実行
                    //if (App.isFunc(HaigoHyo_Tab.recalculation)) {
                    //    HaigoHyo_Tab.recalculation("tokuseichi_tab", "油含有率");
                    //} else {
                    //    App.ui.loading.close();
                    //}
                    App.ui.loading.close();
                }
            }

            //出力時、小数桁数が2桁になるようにゼロ埋め・切捨てを行う
            if (['tanka', 'budomari', 'ritu_abura'].indexOf(property) >= 0) {
                var value = page.detail.formatNumber(entity[property], 2);

                entity[property] = value.forData;
                HaigoHyo_Tab.detail.dataHaigo.update(entity);

                target.val(value.forDisp);

                //原価試算タブ．原料費
                if (['tanka', 'budomari'].indexOf(property) >= 0) {
                    if (App.isFunc(HaigoHyo_Tab.recalculation)) {
                        HaigoHyo_Tab.recalculation("genkashisan_tab", "単価-歩留");
                    }
                }

                //フォントを赤色・太文字に変える。
                if (property == 'budomari') {
                    HaigoHyo_Tab.detail.iroBudomari(row.element, entity);
                }
            }

            //特性値タブ．総酸、食塩、水相中酸度、水相中食塩、水相中酢酸の再計算を行う
            //if (property == "ritu_abura") {
            //    //if (App.isFunc(HaigoHyo_Tab.recalculation)) {
            //    //    HaigoHyo_Tab.recalculation("tokuseichi_tab", "油含有率");
            //    //}
            //}

            validate(row.element.find("input"));
        }).fail(function () {
            if (property === "zoku_kotei" && row.element.hasClass("row-kotei")) {
                var id = row.element.attr("data-key"),
                    entity = HaigoHyo_Tab.detail.dataKotei.entry(id);

                entity[property] = row.element.form().data()[property];
                HaigoHyo_Tab.detail.dataKotei.update(entity);

                HaigoHyo_Tab.detail.updateHaigoChildren(target, row, true);
                if (property === "zoku_kotei") {
                    HaigoHyo_Tab.detail.controlGenryoTani(target, true);
                    // Recheck other kotei
                    $.each(HaigoHyo_Tab.detail.element.find("tbody.new.row-kotei").findP("zoku_kotei"), function (i, e) {
                        e = $(e);
                        if (e.data("isUpdated")) {
                            validate(e);
                        }
                    });
                }
            }
        })
    };

    /**
    * 原料データを検索する
    */
    HaigoHyo_Tab.detail.Genryo = function (genryo_group, haigo) {
        if (!genryo_group.length) {
            return App.async.success([]);
        }

        return $.ajax(App.ajax.webapi.post(HaigoHyo_Tab.urls.getGenryoKojo, {
            genryo_group: genryo_group,
            cd_kaisha: haigo.cd_kaisha,
            cd_busho: haigo.cd_busho
            //flg_daihyo: (haigo.cd_kaisha == App.settings.app.CD_DAIHYO_KAISHA.kewpie && haigo.cd_busho == App.settings.app.CD_DAIHYO_KOJO.kewpie_kenkyu_kaihatsu)
        }))
    }

    /**
    * 原料コードロストフォーカス時
    */
    HaigoHyo_Tab.detail.changeGenryo = function (entity, row, cd_genryo) {

        //フォーカスが外れたときにクラスの自動オープンダイアログを削除
        //row.find(".focusout_open_dialog").removeClass(".focusout_open_dialog");

        App.ui.loading.show();

        var deferred = $.Deferred(),
            shisakuHin = HaigoHyo_Tab.getDataHeader();

        var syncCalcParamIns = HaigoHyo_Tab.detail.getCalcParam();

        //直すことをチェックできする。
        //原料名  
        row.findP("nm_genryo").prop("readonly", false);
        //単価（※）
        row.findP("tanka").prop("readonly", false);

        //原料コードが全て"9"場合	
        //原料コードロストフォーカスの時、原料名～量を空白を設定
        if (cd_genryo == App.settings.app.commentCode) {

            row.findP("nm_genryo")
            //Ｎ始まりの原料コード、原料コードが全て""9""の場合は変更可能上記以外の場合は変更不可
            //原料名  
            entity["nm_genryo"] = null;
            //単価（※）
            entity["tanka"] = null;
            //歩留（※）
            entity["budomari"] = null;
            //担当会社
            entity["cd_kaisha"] = shisakuHin.cd_kaisha;
            //担当工場
            entity["cd_busho"] = shisakuHin.cd_kojo;
            //歩留原本
            entity["budomari_gry"] = null;
            HaigoHyo_Tab.detail.dataHaigo.update(entity);

            row.form(HaigoHyo_Tab.detail.options.bindOption).bind(HaigoHyo_Tab.detail.getChangeGenryoData(entity));

            //フォントを赤色・太文字に変える。
            HaigoHyo_Tab.detail.iroBudomari(row, entity);

            // Clear all total param
            syncCalcParamIns.setSyncAllState();
            syncCalcParamIns.updateTotalCalcParam(entity, {});

            var quality = row.find(".change_keta_shosu"),
                elem, id, entity;

            $.each(quality, function () {
                elem = row.find(this);
                if (elem.is(':disabled')) {
                    return true;
                }
                id = elem.attr("data-key");

                if (App.isUndefOrNull(id)) {
                    return true;
                }
                elem.val("");

                entity = HaigoHyo_Tab.detail.dataShisakuList.entry(id);
                entity.quantity = null;

                HaigoHyo_Tab.detail.dataShisakuList.update(entity);

                //各工程ごとの合計を再計算する			
                //合計重量を再計算する			
                HaigoHyo_Tab.detail.caculateTotalSample(entity.seq_shisaku);
            })
            return deferred.resolve();
        }

        $.ajax(App.ajax.webapi.post(HaigoHyo_Tab.urls.getGenryoFocus, {
            cd_kaisha: shisakuHin.cd_kaisha,
            cd_busho: shisakuHin.cd_kojo,
            cd_genryo: cd_genryo
            //flg_daihyo: (shisakuHin.cd_kaisha == App.settings.app.CD_DAIHYO_KAISHA.kewpie && shisakuHin.cd_kojo == App.settings.app.CD_DAIHYO_KOJO.kewpie_kenkyu_kaihatsu)
        })).then(function (result) {
            var nm_genryo = HaigoHyo_Tab.detail.substringFirst((entity.nm_genryo || ""));
            //var isUpdateTotalCalcParam = false;
            var addBlackStar = HaigoHyo_Tab.detail.addBlackStar,
                addWhiteStar = HaigoHyo_Tab.detail.addWhiteStar,
                disableInp = HaigoHyo_Tab.detail.disableInp;
            var eGenryo = row.findP("nm_genryo"),
                eTanka = row.findP("tanka");
            var isGenryoB = HaigoHyo_Tab.detail.genryoBCheck(cd_genryo);

            // Reset exist status
            HaigoHyo_Tab.detail.setGenryoExistState(row);
            //歩留原本
            entity["budomari_gry"] = null;
            //原料原本
            entity["tanka_org"] = entity["tanka"];
            //歩留原本
            entity["budomari_org"] = entity["budomari"];

            if (result.data.length) {
                var new_genryo = result.data[0];
                new_genryo.nm_genryo = (new_genryo.nm_genryo || "");
                var isGenryoBNew = HaigoHyo_Tab.detail.genryoBCheck(new_genryo.cd_genryo)

                if (new_genryo.cd_kaisha == shisakuHin.cd_kaisha && new_genryo.cd_busho == shisakuHin.cd_kojo) {

                    if (!new_genryo.kbn_haishi) {
                        //担当会社
                        entity["cd_kaisha"] = new_genryo.cd_kaisha;
                        //担当工場
                        entity["cd_busho"] = new_genryo.cd_busho;
                        //原料名  
                        nm_genryo = new_genryo.nm_genryo;
                        disableInp(eGenryo);
                        //単価（※）
                        entity["tanka"] = new_genryo.tanka;
                        //disableInp(eTanka);
                        //歩留（※）
                        entity["budomari"] = new_genryo.budomari;
                        //isUpdateTotalCalcParam = true;
                        syncCalcParamIns.setSyncAllState();
                    } else {
                        //担当会社
                        entity["cd_kaisha"] = shisakuHin.cd_kaisha;
                        //担当工場
                        entity["cd_busho"] = shisakuHin.cd_kojo;
                        //原料名  
                        nm_genryo = addBlackStar(nm_genryo);
                        disableInp(eGenryo);
                        //isUpdateTotalCalcParam = false;
                        syncCalcParamIns.setUnSyncState();
                        //原料一覧画面起動
                        if (App.isFunc(HaigoHyo_Tab.showGenryoIchiranDialog)) {
                            HaigoHyo_Tab.showGenryoIchiranDialog({
                                zenKojo: App.settings.app.hyoji_kojyo_tate[0].value
                                , cd_genryo: new_genryo.cd_genryo
                            });
                        }
                    }
                }

                if (new_genryo.cd_kaisha == shisakuHin.cd_kaisha && new_genryo.cd_busho != null && new_genryo.cd_busho != shisakuHin.cd_kojo) {
                    if (isGenryoBNew) {
                        if (!new_genryo.kbn_haishi) {
                            //担当会社
                            entity["cd_kaisha"] = new_genryo.cd_kaisha;
                            //担当工場
                            entity["cd_busho"] = new_genryo.cd_busho;
                            //原料名  
                            nm_genryo = new_genryo.nm_genryo;
                            //単価（※）
                            entity["tanka"] = new_genryo.tanka;
                            //歩留（※）
                            entity["budomari"] = new_genryo.budomari;
                            //isUpdateTotalCalcParam = true;
                            syncCalcParamIns.setSyncAllState();
                        } else {
                            //担当会社
                            entity["cd_kaisha"] = shisakuHin.cd_kaisha;
                            //担当工場
                            entity["cd_busho"] = 0;
                            //原料名  
                            nm_genryo = addBlackStar(nm_genryo);
                            //isUpdateTotalCalcParam = false;
                            syncCalcParamIns.setUnSyncState();

                            //原料一覧画面起動
                            if (App.isFunc(HaigoHyo_Tab.showGenryoIchiranDialog)) {
                                HaigoHyo_Tab.showGenryoIchiranDialog({
                                    zenKojo: App.settings.app.hyoji_kojyo_tate[0].value
                                    , cd_genryo: new_genryo.cd_genryo
                                });
                            }
                        }
                    } else {
                        //担当会社
                        entity["cd_kaisha"] = shisakuHin.cd_kaisha;
                        //担当工場
                        entity["cd_busho"] = shisakuHin.cd_kojo;
                        //原料名  
                        nm_genryo = addBlackStar(nm_genryo);
                        //isUpdateTotalCalcParam = false;
                        syncCalcParamIns.setUnSyncState();

                        //原料一覧画面起動
                        if (App.isFunc(HaigoHyo_Tab.showGenryoIchiranDialog)) {
                            HaigoHyo_Tab.showGenryoIchiranDialog({
                                zenKojo: App.settings.app.hyoji_kojyo_tate[0].value
                                , cd_genryo: new_genryo.cd_genryo
                            });
                        }
                    }
                }

                if (new_genryo.cd_kaisha != shisakuHin.cd_kaisha && new_genryo.cd_busho != null) {
                    if (isGenryoBNew) {
                        //担当工場
                        entity["cd_kojo_genryo"] = 0;
                        //担当会社
                        entity["cd_kaisha"] = shisakuHin.cd_kaisha;
                        //担当工場
                        entity["cd_busho"] = 0;
                        //原料名  
                        nm_genryo = addBlackStar(nm_genryo);
                        //isUpdateTotalCalcParam = false;
                        syncCalcParamIns.setUnSyncState();
                    } else {
                        //担当会社
                        entity["cd_kaisha"] = shisakuHin.cd_kaisha;
                        //担当工場
                        entity["cd_busho"] = shisakuHin.cd_kojo;
                        //原料名  
                        nm_genryo = addBlackStar(nm_genryo);
                        disableInp(eGenryo);
                        //isUpdateTotalCalcParam = false;
                        syncCalcParamIns.setUnSyncState();
                    }
                }

                if (new_genryo.cd_busho == null) {
                    //担当会社
                    entity["cd_kaisha"] = shisakuHin.cd_kaisha;
                    //担当工場
                    entity["cd_busho"] = shisakuHin.cd_kojo;
                    //原料名  
                    nm_genryo = addBlackStar(nm_genryo);
                    disableInp(eGenryo);
                    //isUpdateTotalCalcParam = false;
                    syncCalcParamIns.setUnSyncState();
                }

                //歩留原本
                entity["budomari_gry"] = new_genryo.budomari_gry;
            } else {
                if (isGenryoB) {
                    //担当工場
                    entity["cd_kojo_genryo"] = 0;
                    //担当会社
                    entity["cd_kaisha"] = shisakuHin.cd_kaisha;
                    //担当工場
                    entity["cd_busho"] = 0;
                    //原料名  
                    nm_genryo = addBlackStar(nm_genryo);
                    //isUpdateTotalCalcParam = false;
                    syncCalcParamIns.setUnSyncState();

                    //原料一覧画面起動
                    if (result.isShowDialog && App.isFunc(HaigoHyo_Tab.showGenryoIchiranDialog)) {
                        HaigoHyo_Tab.showGenryoIchiranDialog({
                            zenKojo: App.settings.app.hyoji_kojyo_tate[0].value
                            , cd_genryo: cd_genryo
                        });
                    }
                } else {
                    //担当会社
                    entity["cd_kaisha"] = shisakuHin.cd_kaisha;
                    //担当工場
                    entity["cd_busho"] = shisakuHin.cd_kojo;
                    //原料名  
                    if (cd_genryo != App.settings.app.commentCode) {
                        nm_genryo = addBlackStar(nm_genryo);
                        disableInp(eGenryo);
                    } else {
                        nm_genryo = "";
                    }
                }

                // Genryo not exist
                HaigoHyo_Tab.detail.setGenryoNotExistState(row);
            }

            entity["nm_genryo"] = nm_genryo;

            // リテラルマスタ.リテラル値1 = '0' 又は該当データなしの時、単価開示不可
            //if (!HaigoHyo_Tab.detail.controlTankaHyoji(shisakuHin.cd_kaisha)) {
            //    //    ////原料原本
            //    //    //entity["tanka"] = entity["tanka_org"];
            //    //    ////歩留原本
            //    //    //entity["budomari"] = entity["budomari_org"];
            //    //    //単価（※）
            //    //    var tanka = page.detail.beforFormatNumber(entity["tanka_org"], 2);
            //    //    row.findP("tanka").val(tanka.forDisp);
            //    //    entity["tanka"] = tanka.forData;
            //    //    //歩留（※）
            //    //    var budomari = page.detail.beforFormatNumber(entity["budomari_org"], 2);
            //    //    row.findP("budomari").val(budomari.forDisp);
            //    //    entity["budomari"] = budomari.forData;
            //    entity["tanka"] = entity["tanka_org"];
            //    entity["tanka"] = entity["budomari_org"];
            //}

            row.form(HaigoHyo_Tab.detail.options.bindOption).bind(HaigoHyo_Tab.detail.getChangeGenryoData(entity));

            //フォントを赤色・太文字に変える。
            HaigoHyo_Tab.detail.iroBudomari(row, entity);

            HaigoHyo_Tab.detail.dataHaigo.update(entity);

            // Sync calc param
            if (syncCalcParamIns.isSync()) {
                syncCalcParamIns.syncTotalCalcParam(cd_genryo, shisakuHin.cd_kaisha, shisakuHin.cd_kojo, entity).always(function () {
                    deferred.resolve();
                });
            } else {
                deferred.resolve();
            }
            HaigoHyo_Tab.detail.validateHiago(row);
        }).fail(function (error) {
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

            App.ui.loading.close();
            deferred.reject();
        })
        return deferred.promise();
    };

    /**
    * 原料コードロストフォーカス時
    */
    //HaigoHyo_Tab.detail.changeGenryoOld = function (entity, row, cd_genryo) {

    //    //フォーカスが外れたときにクラスの自動オープンダイアログを削除
    //    //row.find(".focusout_open_dialog").removeClass(".focusout_open_dialog");

    //    App.ui.loading.show();

    //    var deferred = $.Deferred(),
    //        shisakuHin = HaigoHyo_Tab.getDataHeader();

    //    //直すことをチェックできする。
    //    //原料名  
    //    row.findP("nm_genryo").prop("readonly", false);
    //    //単価（※）
    //    row.findP("tanka").prop("readonly", false);

    //    //原料コードが全て"9"場合	
    //    //原料コードロストフォーカスの時、原料名～量を空白を設定
    //    if (cd_genryo == App.settings.app.commentCode) {

    //        row.findP("nm_genryo")
    //        //Ｎ始まりの原料コード、原料コードが全て""9""の場合は変更可能上記以外の場合は変更不可
    //        //原料名  
    //        row.findP("nm_genryo").val("")
    //        entity["nm_genryo"] = null;
    //        //単価（※）
    //        row.findP("tanka").val("")
    //        entity["tanka"] = null;
    //        //歩留（※）
    //        row.findP("budomari").val("");
    //        entity["budomari"] = null;
    //        //油含有率
    //        //row.findP("ritu_abura").val("");
    //        //entity["ritu_abura"] = null;
    //        //酢酸
    //        entity["ritu_sakusan"] = null;
    //        //食塩
    //        //entity["ritu_shokuen"] = null;
    //        //総酸
    //        //entity["ritu_sousan"] = null;
    //        //率ＭＳＧ
    //        entity["ritu_msg"] = null;
    //        //担当会社
    //        entity["cd_kaisha"] = shisakuHin.cd_kaisha;
    //        //担当工場
    //        entity["cd_busho"] = shisakuHin.cd_kojo;
    //        //Request #14303 : 基本、tr_haigoと同じデータ
    //        ////担当会社
    //        //entity["cd_kaisha_genryo"] = shisakuHin.cd_kaisha;
    //        ////担当工場
    //        //entity["cd_kojo_genryo"] = shisakuHin.cd_kojo;
    //        //原料原本
    //        entity["cd_genryo_gry"] = null;
    //        //歩留原本
    //        entity["budomari_gry"] = null;
    //        HaigoHyo_Tab.detail.dataHaigo.update(entity);

    //        //フォントを赤色・太文字に変える。
    //        HaigoHyo_Tab.detail.iroBudomari(row, entity);

    //        // リテラルマスタ.リテラル値1 = '0' 又は該当データなしの時、単価開示不可
    //        //HaigoHyo_Tab.detail.controlTankaHyoji(shisakuHin.cd_kaisha, row);

    //        var quality = row.find(".change_keta_shosu"),
    //            elem, id, entity;

    //        $.each(quality, function () {
    //            elem = row.find(this);
    //            if (elem.is(':disabled')) {
    //                return true;
    //            }
    //            id = elem.attr("data-key");

    //            if (App.isUndefOrNull(id)) {
    //                return true;
    //            }
    //            elem.val("");

    //            entity = HaigoHyo_Tab.detail.dataShisakuList.entry(id);
    //            entity.quantity = null;

    //            HaigoHyo_Tab.detail.dataShisakuList.update(entity);

    //            //各工程ごとの合計を再計算する			
    //            //合計重量を再計算する			
    //            HaigoHyo_Tab.detail.caculateTotalSample(entity.seq_shisaku);
    //        })
    //        return deferred.resolve();
    //    }

    //    $.ajax(App.ajax.webapi.post(HaigoHyo_Tab.urls.getGenryoFocus, {
    //        cd_kaisha: shisakuHin.cd_kaisha,
    //        cd_busho: shisakuHin.cd_kojo,
    //        cd_genryo: cd_genryo,
    //        flg_daihyo: (shisakuHin.cd_kaisha == App.settings.app.CD_DAIHYO_KAISHA.kewpie && shisakuHin.cd_kojo == App.settings.app.CD_DAIHYO_KOJO.kewpie_kenkyu_kaihatsu)
    //    })).then(function (result) {
    //        var nm_genryo = HaigoHyo_Tab.detail.substringFirst((entity.nm_genryo != null ? entity.nm_genryo : ""), null);//.replace("★", "").replace("☆", "");

    //        //Request #14303 : 基本、tr_haigoと同じデータ
    //        ////担当会社
    //        //entity["cd_kaisha_genryo"] = shisakuHin.cd_kaisha;
    //        ////担当工場
    //        //entity["cd_kojo_genryo"] = shisakuHin.cd_kojo;

    //        //歩留原本
    //        entity["budomari_gry"] = null;
    //        //原料原本
    //        entity["tanka_org"] = entity["tanka"];
    //        //歩留原本
    //        entity["budomari_org"] = entity["budomari"];

    //        if (result.data.length) {
    //            var new_genryo = result.data[0];
    //            new_genryo.nm_genryo = new_genryo.nm_genryo == null ? "" : new_genryo.nm_genryo;

    //            if (new_genryo.cd_kaisha == shisakuHin.cd_kaisha && new_genryo.cd_busho == shisakuHin.cd_kojo) {

    //                //Request #14303 : 基本、tr_haigoと同じデータ
    //                ////担当会社
    //                //entity["cd_kaisha_genryo"] = new_genryo.cd_kaisha;
    //                ////担当工場
    //                //entity["cd_kojo_genryo"] = new_genryo.cd_busho;

    //                if (!new_genryo.kbn_haishi) {
    //                    //担当会社
    //                    entity["cd_kaisha"] = new_genryo.cd_kaisha;
    //                    //担当工場
    //                    entity["cd_busho"] = new_genryo.cd_busho;
    //                    //原料名  
    //                    nm_genryo = new_genryo.nm_genryo;
    //                    //単価（※）
    //                    var tanka = page.detail.beforFormatNumber(new_genryo.tanka, 2);
    //                    row.findP("tanka").val(tanka.forDisp);
    //                    entity["tanka"] = tanka.forData;
    //                    //歩留（※）
    //                    var budomari = page.detail.beforFormatNumber(new_genryo.budomari, 2);
    //                    row.findP("budomari").val(budomari.forDisp);
    //                    entity["budomari"] = budomari.forData;
    //                    //油含有率
    //                    //var ritu_abura = page.detail.beforFormatNumber(new_genryo.ritu_abura, 2);
    //                    //row.findP("ritu_abura").val(ritu_abura.forDisp);
    //                    //entity["ritu_abura"] = ritu_abura.forData;
    //                    //酢酸
    //                    entity["ritu_sakusan"] = new_genryo.ritu_sakusan;
    //                    //食塩
    //                    //entity["ritu_shokuen"] = new_genryo.ritu_shokuen;
    //                    //総酸
    //                    //entity["ritu_sousan"] = new_genryo.ritu_sousan;
    //                    //率ＭＳＧ
    //                    entity["ritu_msg"] = new_genryo.ritu_msg;

    //                    if (shisakuHin.cd_kaisha == App.settings.app.CD_DAIHYO_KAISHA.kewpie) {
    //                        //直すことをチェックできする。
    //                        //原料名  
    //                        row.findP("tanka").prop("readonly", true);
    //                    }
    //                } else {
    //                    //担当会社
    //                    entity["cd_kaisha"] = shisakuHin.cd_kaisha;
    //                    //担当工場
    //                    entity["cd_busho"] = shisakuHin.cd_kojo;
    //                    //原料名  
    //                    nm_genryo = "★" + nm_genryo;

    //                    //原料一覧画面起動
    //                    if (App.isFunc(HaigoHyo_Tab.showGenryoIchiranDialog)) {
    //                        HaigoHyo_Tab.showGenryoIchiranDialog({
    //                            zenKojo: App.settings.app.hyoji_kojyo_tate[0].value
    //                            , cd_genryo: new_genryo.cd_genryo
    //                        });
    //                    }
    //                }
    //                //直すことをチェックできする。
    //                //原料名  
    //                row.findP("nm_genryo").prop("readonly", true);

    //                //if (nm_genryo.indexOf("★") != 0) {
    //                //    //原料原本
    //                //    entity["cd_genryo_gry"] = new_genryo.cd_genryo_gry;
    //                //    //歩留原本
    //                //    entity["budomari_gry"] = new_genryo.budomari_gry;
    //                //}
    //            }

    //            if (new_genryo.cd_kaisha == shisakuHin.cd_kaisha && new_genryo.cd_busho != null && new_genryo.cd_busho != shisakuHin.cd_kojo) {
    //                if (new_genryo.cd_genryo.indexOf("B") == 0) {
    //                    //Request #14303 : 基本、tr_haigoと同じデータ
    //                    ////担当会社
    //                    //entity["cd_kaisha_genryo"] = new_genryo.cd_kaisha;
    //                    ////担当工場
    //                    //entity["cd_kojo_genryo"] = new_genryo.cd_busho;

    //                    if (!new_genryo.kbn_haishi) {
    //                        //担当会社
    //                        entity["cd_kaisha"] = new_genryo.cd_kaisha;
    //                        //担当工場
    //                        entity["cd_busho"] = new_genryo.cd_busho;
    //                        //原料名  
    //                        nm_genryo = new_genryo.nm_genryo;
    //                        //単価（※）
    //                        var tanka = page.detail.beforFormatNumber(new_genryo.tanka, 2);
    //                        row.findP("tanka").val(tanka.forDisp);
    //                        entity["tanka"] = tanka.forData;
    //                        //歩留（※）
    //                        var budomari = page.detail.beforFormatNumber(new_genryo.budomari, 2);
    //                        row.findP("budomari").val(budomari.forDisp);
    //                        entity["budomari"] = budomari.forData;
    //                        //油含有率
    //                        //var ritu_abura = page.detail.beforFormatNumber(new_genryo.ritu_abura, 2);
    //                        //row.findP("ritu_abura").val(ritu_abura.forDisp);
    //                        //entity["ritu_abura"] = ritu_abura.forData;
    //                        //酢酸
    //                        entity["ritu_sakusan"] = new_genryo.ritu_sakusan;
    //                        //食塩
    //                        //entity["ritu_shokuen"] = new_genryo.ritu_shokuen;
    //                        //総酸
    //                        //entity["ritu_sousan"] = new_genryo.ritu_sousan;
    //                        //率ＭＳＧ
    //                        entity["ritu_msg"] = new_genryo.ritu_msg;
    //                    } else {
    //                        //担当会社
    //                        entity["cd_kaisha"] = new_genryo.cd_kaisha;
    //                        //担当工場
    //                        entity["cd_busho"] = 0;
    //                        //原料名  
    //                        nm_genryo = "★" + nm_genryo;

    //                        //原料一覧画面起動
    //                        if (App.isFunc(HaigoHyo_Tab.showGenryoIchiranDialog)) {
    //                            HaigoHyo_Tab.showGenryoIchiranDialog({
    //                                zenKojo: App.settings.app.hyoji_kojyo_tate[0].value
    //                                , cd_genryo: new_genryo.cd_genryo
    //                            });
    //                        }
    //                    }
    //                } else {
    //                    //担当会社
    //                    entity["cd_kaisha"] = shisakuHin.cd_kaisha;
    //                    //担当工場
    //                    entity["cd_busho"] = shisakuHin.cd_kojo;
    //                    //原料名  
    //                    nm_genryo = "★" + nm_genryo;

    //                    //原料一覧画面起動
    //                    if (App.isFunc(HaigoHyo_Tab.showGenryoIchiranDialog)) {
    //                        HaigoHyo_Tab.showGenryoIchiranDialog({
    //                            zenKojo: App.settings.app.hyoji_kojyo_tate[0].value
    //                            , cd_genryo: new_genryo.cd_genryo
    //                        });
    //                    }

    //                    //直すことをチェックできする。
    //                    //原料名  
    //                    row.findP("nm_genryo").prop("readonly", true);
    //                }

    //                //原料コードロストフォーカス時
    //                //・原料コードロストフォーカする場合（基本情報．担当会社 = CD_DAIHYO_KAISHA 　かつ　
    //                //・基本情報．担当工場<>  CD_DAIHYO_KOJO、又は基本情報．担当会社<> CD_DAIHYO_KAISHA）かつ
    //                //・入力された原料コードは自工場に存在しないが自会社の他工場に存在する原料の場合
    //                //if (shisakuHin.cd_kaisha != App.settings.app.CD_DAIHYO_KAISHA.kewpie || (shisakuHin.cd_kaisha == App.settings.app.CD_DAIHYO_KAISHA.kewpie && shisakuHin.cd_kojo != App.settings.app.CD_DAIHYO_KOJO.kewpie_kenkyu_kaihatsu)) {
    //                //    row.findP("cd_genryo").addClass("focusout_open_dialog");
    //                //}

    //                //if (nm_genryo.indexOf("★") != 0) {
    //                //    //原料原本
    //                //    entity["cd_genryo_gry"] = new_genryo.cd_genryo_gry;
    //                //    //歩留原本
    //                //    entity["budomari_gry"] = new_genryo.budomari_gry;
    //                //}
    //            }

    //            if (new_genryo.cd_kaisha != shisakuHin.cd_kaisha && new_genryo.cd_busho != null) {
    //                if (new_genryo.cd_genryo.indexOf("B") == 0) {
    //                    //担当工場
    //                    entity["cd_kojo_genryo"] = 0;
    //                    //担当会社
    //                    entity["cd_kaisha"] = shisakuHin.cd_kaisha;
    //                    //担当工場
    //                    entity["cd_busho"] = 0;
    //                    //原料名  
    //                    nm_genryo = "★" + nm_genryo;
    //                } else {
    //                    //担当会社
    //                    entity["cd_kaisha"] = shisakuHin.cd_kaisha;
    //                    //担当工場
    //                    entity["cd_busho"] = shisakuHin.cd_kojo;
    //                    //原料名  
    //                    nm_genryo = "★" + nm_genryo;

    //                    //直すことをチェックできする。
    //                    //原料名  
    //                    row.findP("nm_genryo").prop("readonly", true);
    //                }
    //            }

    //            if (new_genryo.cd_busho == null) {
    //                //担当会社
    //                entity["cd_kaisha"] = shisakuHin.cd_kaisha;
    //                //担当工場
    //                entity["cd_busho"] = shisakuHin.cd_kojo;
    //                //原料名  
    //                nm_genryo = "★" + nm_genryo;

    //                //原料名  
    //                row.findP("nm_genryo").prop("readonly", true);
    //            }

    //            //歩留原本
    //            entity["budomari_gry"] = new_genryo.budomari_gry;
    //        } else {
    //            if (cd_genryo.indexOf("B") == 0) {
    //                //担当工場
    //                entity["cd_kojo_genryo"] = 0;
    //                //担当会社
    //                entity["cd_kaisha"] = shisakuHin.cd_kaisha;
    //                //担当工場
    //                entity["cd_busho"] = 0;
    //                //原料名  
    //                nm_genryo = "★" + nm_genryo;

    //                //原料一覧画面起動
    //                if (result.isShowDialog && App.isFunc(HaigoHyo_Tab.showGenryoIchiranDialog)) {
    //                    HaigoHyo_Tab.showGenryoIchiranDialog({
    //                        zenKojo: App.settings.app.hyoji_kojyo_tate[0].value
    //                        , cd_genryo: cd_genryo
    //                    });
    //                }
    //            } else {
    //                //担当会社
    //                entity["cd_kaisha"] = shisakuHin.cd_kaisha;
    //                //担当工場
    //                entity["cd_busho"] = shisakuHin.cd_kojo;
    //                //原料名  
    //                if (cd_genryo != App.settings.app.commentCode) {
    //                    nm_genryo = "★" + nm_genryo;

    //                    //直すことをチェックできする。
    //                    //原料名  
    //                    row.findP("nm_genryo").prop("readonly", true);
    //                } else {
    //                    nm_genryo = "";
    //                }
    //            }
    //        }

    //        row.findP("nm_genryo").val(nm_genryo);
    //        entity["nm_genryo"] = nm_genryo;

    //        //フォントを赤色・太文字に変える。
    //        HaigoHyo_Tab.detail.iroBudomari(row, entity);

    //        // リテラルマスタ.リテラル値1 = '0' 又は該当データなしの時、単価開示不可
    //        if (!HaigoHyo_Tab.detail.controlTankaHyoji(shisakuHin.cd_kaisha)) {
    //            ////原料原本
    //            //entity["tanka"] = entity["tanka_org"];
    //            ////歩留原本
    //            //entity["budomari"] = entity["budomari_org"];
    //            //単価（※）
    //            var tanka = page.detail.beforFormatNumber(entity["tanka_org"], 2);
    //            row.findP("tanka").val(tanka.forDisp);
    //            entity["tanka"] = tanka.forData;
    //            //歩留（※）
    //            var budomari = page.detail.beforFormatNumber(entity["budomari_org"], 2);
    //            row.findP("budomari").val(budomari.forDisp);
    //            entity["budomari"] = budomari.forData;

    //        }

    //        HaigoHyo_Tab.detail.dataHaigo.update(entity);

    //        // Sync calc param
    //        HaigoHyo_Tab.detail.syncTotalCalcParam(cd_genryo, shisakuHin.cd_kaisha, entity).always(function () {
    //            deferred.resolve();
    //        })
    //    }).fail(function (error) {
    //        App.ui.page.notifyAlert.clear();
    //        App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

    //        App.ui.loading.close();
    //        deferred.reject();
    //    })
    //    return deferred.promise();
    //};

    /**
    * Sync genryo data when change
    */
    HaigoHyo_Tab.detail.getCalcParam = function () {
        var UN_SYNC = 0,
            SYNC_ALL = 1,
            SYNC_APART = 2;
        var ins = {
            sync_kbn: 0
        }
        // Set SYNC status
        ins.setUnSyncState = function () {
            ins.sync_kbn = UN_SYNC;
        }
        ins.setSyncAllState = function () {
            ins.sync_kbn = SYNC_ALL;
        }
        ins.setSyncApartState = function () {
            ins.sync_kbn = SYNC_APART;
        }
        ins.isSync = function () {
            return (ins.sync_kbn !== UN_SYNC);
        }
        // Ajax to get master data
        ins.syncTotalCalcParam = function (cd_genryo, cd_kaisha, cd_busho, entity, isPreventUpdateTotal) {
            if (!ins.isSync()) return App.async.success();
            var param = {
                cd_genryo: cd_genryo,
                cd_kaisha: cd_kaisha,
                cd_busho: cd_busho
            }
            return $.ajax(App.ajax.webapi.get(HaigoHyo_Tab.urls.getGenryoData, param)).then(function (result) {
                // for calc param
                ins.updateTotalCalcParam(entity, result, isPreventUpdateTotal);
            });
        }
        // Sync entity data
        ins.updateTotalCalcParam = function (entity, data, isPreventUpdateTotal) {
            if (!entity || !data || !ins.isSync()) return;
            if (ins.sync_kbn === SYNC_ALL || !App.isUndefOrNullOrStrEmpty(data.cd_kaisha_bf)) {
                entity.cd_tani_master = data.cd_tani_master;
                entity.wt_kaniku = data.wt_kaniku;
                entity.wt_ireme = data.wt_ireme;
                entity.wt_1_kan = data.wt_1_kan;
                entity.wt_kanmido = data.wt_kanmido;
                entity.wt_sato = data.wt_sato;
                entity.wt_hiju = data.wt_hiju;
                entity.kbn_sato = data.kbn_sato;
                entity.kbn_gum = data.kbn_gum;
            }
            if (ins.sync_kbn === SYNC_ALL || !App.isUndefOrNullOrStrEmpty(data.cd_busho_bf)) {
                entity.wt_kayosei_kokeibutsu = data.wt_kayosei_kokeibutsu;
            }
            if (!isPreventUpdateTotal) {
                HaigoHyo_Tab.detail.calculateTotal("ChangeGenryo");
            }
        }
        return ins;
    }
    ///**
    //* Sync genryo data when change
    //*/
    //HaigoHyo_Tab.detail.syncTotalCalcParam = function (cd_genryo, cd_kaisha, cd_busho, entity, isPreventUpdateTotal) {
    //    var param = {
    //        cd_genryo: cd_genryo,
    //        cd_kaisha: cd_kaisha,
    //        cd_busho: cd_busho
    //    }
    //    return $.ajax(App.ajax.webapi.get(HaigoHyo_Tab.urls.getGenryoData, param)).then(function (result) {
    //        // for calc param
    //        HaigoHyo_Tab.detail.updateTotalCalcParam(entity, result, isPreventUpdateTotal);
    //    });
    //}

    ///**
    //* Update total calc param
    //*/
    //HaigoHyo_Tab.detail.updateTotalCalcParam = function (entity, data, isPreventUpdateTotal) {
    //    if (!entity || !data) return;
    //    entity.cd_tani_master = data.cd_tani_master;
    //    entity.wt_kaniku = data.wt_kaniku;
    //    entity.wt_ireme = data.wt_ireme;
    //    entity.wt_1_kan = data.wt_1_kan;
    //    entity.wt_kayosei_kokeibutsu = data.wt_kayosei_kokeibutsu;
    //    entity.wt_kanmido = data.wt_kanmido;
    //    entity.wt_sato = data.wt_sato;
    //    entity.wt_hiju = data.wt_hiju;
    //    entity.kbn_sato = data.kbn_sato;
    //    entity.kbn_gum = data.kbn_gum;
    //    if (!isPreventUpdateTotal) {
    //        HaigoHyo_Tab.detail.calculateTotal("ChangeGenryo");
    //    }
    //}

    /**
    * リテラルマスタ.リテラル値1 = '0' 又は該当データなしの時、単価開示不可
    */
    HaigoHyo_Tab.detail.controlTankaHyoji = function (cd_kaisha) {
        var isShow = jQuery.grep(HaigoHyo_Tab.values.taniHyoji, function (n, i) {
            return (n.cd_literal == cd_kaisha);
        });

        return (isShow.length > 0);
    }

    /**
    * get all data fiel on screen
    */
    HaigoHyo_Tab.detail.allDataOnTab = function () {
        var deferred = $.Deferred();

        //get data haigo
        var elementHaigo = HaigoHyo_Tab.detail.element.find(".dt-container .fix-columns .dt-fix-body .datatable"),
            rowKotei = elementHaigo.find(".row-kotei:eq(0)"),
            dataZokuKotei = {},
            dataShisaku = {};

        if (!rowKotei.length) {
            return deferred.reject();
        }

        var nextRow = rowKotei.next(),
            id, entity;
        while (nextRow.next().length) {
            id = nextRow.attr("data-key");

            if (nextRow.hasClass("row-kotei") || App.isUndefOrNull(id)) {
                nextRow = nextRow.next();
                continue;
            }


            entity = HaigoHyo_Tab.detail.dataHaigo.entry(id);
            dataZokuKotei["quantity_" + entity.cd_kotei + "_" + entity.seq_kotei] = $.extend(true, {}, entity);

            nextRow = nextRow.next();
        }

        //get data shisaku
        var elementShisaku = HaigoHyo_Tab.detail.elementShisakuHead,
            colShi = elementShisaku.find(".col-sample"),
            colSeled, isCol;

        if (!colShi.length) {
            return deferred.reject();
        }

        colSeled = 0;
        var indexCol, data;
        while (colShi[colSeled]) {
            isCol = elementShisaku.find(colShi[colSeled]);

            if (isCol.hasClass("th-tmpl")) {// || isCol.findP("flg_shisanIrai").text() == HaigoHyo_Tab.values.flg_shisanIrai) {
                colSeled += 1;
                continue;
            }

            indexCol = isCol.attr("index-col");

            data = HaigoHyo_Tab.detail.element.find(".sample-" + indexCol).form().data();
            data.indexCol = indexCol;

            dataShisaku[indexCol] = data;
            colSeled += 1;
        }

        deferred.resolve({
            haigo: dataZokuKotei,
            shisaku: dataShisaku
        });

        return deferred.promise();
    }

    /**
    * get all data fiel on screen
    */
    HaigoHyo_Tab.detail.allDataOnTabFor606 = function () {
        var deferred = $.Deferred();

        //get data haigo
        var elementHaigo = HaigoHyo_Tab.detail.element.find(".dt-container .fix-columns .dt-fix-body .datatable"),
            rowKotei = elementHaigo.find(".row-kotei:eq(0)"),
            dataZokuKotei = {},
            dataShisaku = {};

        if (!rowKotei.length) {
            return deferred.reject();
        }

        var nextRow = rowKotei.next(),
            id, entity;
        while (nextRow.next().length) {
            id = nextRow.attr("data-key");

            if (nextRow.hasClass("row-kotei") || App.isUndefOrNull(id)) {
                nextRow = nextRow.next();
                continue;
            }


            entity = HaigoHyo_Tab.detail.dataHaigo.entry(id);
            dataZokuKotei["quantity_" + entity.cd_kotei + "_" + entity.seq_kotei] = $.extend(true, {}, entity);

            nextRow = nextRow.next();
        }

        //get data shisaku
        var elementShisaku = HaigoHyo_Tab.detail.elementShisakuHead,
            colShi = elementShisaku.find(".col-sample"),
            colSeled, isCol;

        if (!colShi.length) {
            return deferred.reject();
        }

        colSeled = 0;
        var indexCol, data;
        while (colShi[colSeled]) {
            isCol = elementShisaku.find(colShi[colSeled]);

            if (isCol.hasClass("th-tmpl")) {// || isCol.findP("flg_shisanIrai").text() == HaigoHyo_Tab.values.flg_shisanIrai) {
                colSeled += 1;
                continue;
            }

            indexCol = isCol.attr("index-col");

            var element = HaigoHyo_Tab.detail.element.find(".sample-" + indexCol),
                data = element.form().data();

            $.each(element, function (i, e) {
                e = $(e);
                var eQty = e.find("input.quantity");
                if (!eQty.length) return;
                var dProp = eQty.attr("data-prop");
                if (dProp && dProp.indexOf("quantity_") === 0) {
                    data[dProp] = eQty.data("quantity_p_606");
                }
            })
            data.indexCol = indexCol;
            data.r04 = 0;
            var dShisaku = HaigoHyo_Tab.detail.getShisakuDataByColInd(indexCol);
            if (dShisaku) {
                data.r04 = (dShisaku.calcResSet.r04.value);
            }

            dataShisaku[indexCol] = data;
            colSeled += 1;
        }

        deferred.resolve({
            haigo: dataZokuKotei,
            shisaku: dataShisaku
        });

        return deferred.promise();
    }

    /**
    * event change combobox Kotei in table
    */
    HaigoHyo_Tab.detail.controlSelectKotei = function (target) {
        if (target.length == 0) {
            return;
        }

        if (target[0].checked == false) {
            return;
        }

        var element = HaigoHyo_Tab.detail.element;

        //reset uncheck for all manual
        element.findP("col1_kotei").prop("checked", false);

        //recheck this item
        element.find(target).prop("checked", true);
    };

    /**
     * 明細の一覧の入力項目の変更イベントの処理を行います。 
     */
    HaigoHyo_Tab.detail.change = function (e, row) {
        var target = $(e.target),
            property = target.attr("data-prop");

        
        HaigoHyo_Tab.wait(target).then(function () {//2020-08-06 :14007 : Bug #18857 内容量0.1でエラーとなる

            if (target.hasClass("u_shisaku")) {
                HaigoHyo_Tab.detail.changeShisaku(e);
                return;
            }
            if (property == "juryo_shiagari_g") {
                HaigoHyo_Tab.detail.changeJuryoShisaku(e, row);
                return;
            }

            if (property.indexOf("total_finish_weight") == 0) {
                HaigoHyo_Tab.detail.changeJuryoShisakuList(e, row);
                return;
            }

            if (target.hasClass("change_keta_shosu")) {
                HaigoHyo_Tab.detail.changeShisakuList(e, row);
                return;
            }

            HaigoHyo_Tab.detail.changeHaigo(e, row);
        })
    };

    /**
     * 原料移動▼
     * 選択されている全ての原料行が、１つ下の原料行の後に移動する（工程が関係ない）
     */
    HaigoHyo_Tab.detail.moveDownGenryo = function () {
        var element = HaigoHyo_Tab.detail.element,
            selectedGenryo = element.find(".datatable .item-genryo:checkbox:checked");//get all genryo is checked

        //break when last checkbook is blank
        if (selectedGenryo.length == 0) {
            return;
        }

        //原価試作依頼されている原料を含む行（配合量に値が入っている行）は工程をまたがる【原料移動】はできない 
        var next1, tbody, checkMove = false;
        $.each(selectedGenryo, function (index, item) {
            tbody = element.find(item).closest("tbody");

            //check flg_shisanIrai
            if (tbody.findP("flg_shisanIrai").text() == HaigoHyo_Tab.values.flg_shisanIrai) {
                next1 = tbody.next();
            }

            //check next row is kotei
            if (next1 && next1.hasClass("row-kotei")) {
                checkMove = true;
                return false;
            }
        })

        if (checkMove) {
            App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0033, { strName: "移動" })).remove();
            App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0033, { strName: "移動" })).show();
            return;
        }

        App.ui.loading.show();
        //フラグ変更画面
        page.values.isChange = true;

        var next1, next2, tbody, selected;
        $.each(selectedGenryo.toArray().reverse(), function (index, item) {
            tbody = element.find(item).closest("tbody");

            HaigoHyo_Tab.detail.dataTable.dataTable("getRow", tbody, function (rowObject) {
                selected = rowObject.element;
            });

            next1 = element.find(selected[0]).next();
            next2 = element.find(selected[1]).next();

            if (next1.hasClass("item_total_kotei_tml") || next2.form().data().col3_genryo == 1) {
                return;
            }
            //update data
            HaigoHyo_Tab.detail.updateMoveGenryo(selected, next1);

            element.find(selected[0]).detach().insertAfter(element.find(next1));
            element.find(selected[1]).detach().insertAfter(element.find(next2));

            HaigoHyo_Tab.detail.controlGenryoTaniByRow(selected);
        });
        // Timeout to apply loading animation
        setTimeout(function () { 
            //check genryo no kotei. add new genryo when not exit
            HaigoHyo_Tab.detail.checkGenryo();

            App.ui.loading.close();
        }, 1);
    };

    /**
     * 原料移動▲
     * 選択されている全ての原料行が、１つ上の原料行の前に移動する（工程が関係ない）
     */
    HaigoHyo_Tab.detail.moveUpGenryo = function () {

        var element = HaigoHyo_Tab.detail.element,
            selectedGenryo = element.find(".datatable .item-genryo:checkbox:checked");//get all genryo is checked

        //break when last checkbook is blank
        if (selectedGenryo.length == 0) {
            App.ui.loading.close();
            return;
        }

        //原価試作依頼されている原料を含む行（配合量に値が入っている行）は工程をまたがる【原料移動】はできない 
        var next1, tbody, checkMove = false;
        $.each(selectedGenryo, function (index, item) {
            tbody = element.find(item).closest("tbody");

            //check flg_shisanIrai
            if (tbody.findP("flg_shisanIrai").text() == HaigoHyo_Tab.values.flg_shisanIrai) {
                next1 = tbody.prev();
            }

            //check next row is kotei
            if (next1 && next1.hasClass("row-kotei")) {
                next1 = next1.prev();
                if (next1 && next1.hasClass("new")) {
                    checkMove = true;
                    return false;
                }
            }
        })

        if (checkMove) {
            App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0033, { strName: "移動" })).remove();
            App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0033, { strName: "移動" })).show();
            return;
        }

        App.ui.loading.show();
        setTimeout(function () {

            //フラグ変更画面
            page.values.isChange = true;

            var prev1, prev2, tbody, selected;
            $.each(selectedGenryo, function (index, item) {
                tbody = element.find(item).closest("tbody");

                HaigoHyo_Tab.detail.dataTable.dataTable("getRow", tbody, function (rowObject) {
                    selected = rowObject.element;
                });

                prev1 = element.find(selected[0]).prev();
                prev2 = element.find(selected[1]).prev();


                if (prev1.prev().hasClass("item-tmpl") || prev2.form().data().col3_genryo == 1) {
                    return;
                }

                HaigoHyo_Tab.detail.updateMoveGenryo(selected, prev1.prev());

                element.find(selected[0]).detach().insertBefore(element.find(prev1));
                element.find(selected[1]).detach().insertBefore(element.find(prev2));

                HaigoHyo_Tab.detail.controlGenryoTaniByRow(selected);
            });

            //check genryo no kotei. add new genryo when not exit
            HaigoHyo_Tab.detail.checkGenryo();

            //App.ui.loading.close();
        }, 1);
    };

    /**
     * update data for row moved
     */
    HaigoHyo_Tab.detail.updateMoveGenryo = function (selected, previewRow) {
        var idPrev = previewRow.attr("data-key"),
            idSeled = selected.attr("data-key"),
            entitySeled = HaigoHyo_Tab.detail.dataHaigo.entry(idSeled),
            entityPrev, oldClass = "quantity_", entityHaigoNew;


        //get entity preveiew row
        if (previewRow.hasClass("row-kotei")) {
            entityPrev = HaigoHyo_Tab.detail.dataKotei.entry(idPrev);
        } else {
            entityPrev = HaigoHyo_Tab.detail.dataHaigo.entry(idPrev);
        }

        //exit when the same kotei
        if (entitySeled.cd_kotei == entityPrev.cd_kotei) {
            return;
        }

        //delete current item validate 
        if (HaigoHyo_Tab.detail.options.validationsShisakuList["quantity_" + entitySeled.cd_kotei + "_" + entitySeled.seq_kotei]) {
            delete HaigoHyo_Tab.detail.options.validationsShisakuList["quantity_" + entitySeled.cd_kotei + "_" + entitySeled.seq_kotei];
        }


        var totalRow = HaigoHyo_Tab.detail.element.find(".item_total_finish_weight" + entityPrev.sort_kotei);

        //update data haigo selected
        oldClass = oldClass + entitySeled.cd_kotei;
        HaigoHyo_Tab.detail.options.maxSeqKotei[entityPrev.cd_kotei] += 1;

        //coppy new data haigo from selected haigo
        entityHaigoNew = $.extend(true, {}, entitySeled);
        entityHaigoNew.cd_kotei = entityPrev.cd_kotei;
        entityHaigoNew.seq_kotei = HaigoHyo_Tab.detail.options.maxSeqKotei[entityPrev.cd_kotei];
        entityHaigoNew.nm_kotei = entityPrev.nm_kotei;
        entityHaigoNew.sort_kotei = entityPrev.cd_kotei;
        entityHaigoNew.zoku_kotei = entityPrev.zoku_kotei;

        //delete haigo selected
        HaigoHyo_Tab.detail.dataHaigo.remove(entitySeled);

        //add new haigo
        (true ? HaigoHyo_Tab.detail.dataHaigo.add : HaigoHyo_Tab.detail.dataHaigo.attach).bind(HaigoHyo_Tab.detail.dataHaigo)(entityHaigoNew);
        //update data-key for tbody row
        selected.attr("data-key", entityHaigoNew.__id);

        //update shisakulist on row
        var shisalist = selected.find(".change_keta_shosu"),
            index = 0, id, entity, entityNew, propOld, juryo_shiagari_seq, indexCol;


        while (shisalist[index]) {
            id = selected.find(shisalist[index]).attr("data-key");

            if (App.isUndefOrNull(id)) {
                index += 1;
                continue;
            }

            //update shisalist selected
            entity = HaigoHyo_Tab.detail.dataShisakuList.entry(id);
            entityNew = $.extend(true, {}, entity);
            entityNew.cd_kotei = entityHaigoNew.cd_kotei;
            entityNew.seq_kotei = entityHaigoNew.seq_kotei;
            entityNew.juryo_shiagari_seq = totalRow.find(".sample-" + entity.seq_shisaku).find(".item_total_weight_cacu").val();

            ////update fiel changed
            //if (!HaigoHyo_Tab.values.isChangeRunning[entity.seq_shisaku]) {
            //    HaigoHyo_Tab.values.isChangeRunning[entity.seq_shisaku] = [];
            //}

            ////delete fiel change in group key
            //if (HaigoHyo_Tab.values.isChangeRunning[entity.seq_shisaku][id]) {
            //    delete HaigoHyo_Tab.values.isChangeRunning[entity.seq_shisaku][id];
            //}

            //delete old data 
            HaigoHyo_Tab.detail.dataShisakuList.remove(entity);
            (true ? HaigoHyo_Tab.detail.dataShisakuList.add : HaigoHyo_Tab.detail.dataShisakuList.attach).bind(HaigoHyo_Tab.detail.dataShisakuList)(entityNew);

            selected.find(shisalist[index]).attr("data-key", entityNew.__id).attr("data-prop", "quantity_" + entityPrev.cd_kotei + "_" + HaigoHyo_Tab.detail.options.maxSeqKotei[entityPrev.cd_kotei] + "_" + entity.seq_shisaku);

            //add new fiel change in group key
            //HaigoHyo_Tab.values.isChangeRunning[entityNew.seq_shisaku][entityNew.__id] = true;

            index += 1;
        };
        // get class row
        var lastRow = HaigoHyo_Tab.detail.element.find(".quantity_" + entityPrev.cd_kotei + ":last").attr("class");
        if (previewRow.hasClass("row-kotei")) {
            lastRow = lastRow + " change_keta_shosu quantity_" + entityPrev.cd_kotei;
        }

        //renew class and data-prop for new row
        selected.find(".change_keta_shosu").attr("class", lastRow);
        //selected.find(".change_keta_shosu").attr("data-prop", "quantity_" + entityPrev.cd_kotei + "_" + HaigoHyo_Tab.detail.options.maxSeqKotei[entityPrev.cd_kotei]);

        //add new item validate shisaku list
        //HaigoHyo_Tab.detail.options.validationsShisakuList["quantity_" + entityPrev.cd_kotei + "_" + HaigoHyo_Tab.detail.options.maxSeqKotei[entityPrev.cd_kotei]] = HaigoHyo_Tab.values.validationsCellDefault;
    };

    /**
     * 原料挿入
     * 選択されている原料行の下に空白の原料行を１行追加する。
     */
    HaigoHyo_Tab.detail.addGenryo = function () {
        var element = HaigoHyo_Tab.detail.element.find(".datatable"),
            selectedGenryo = element.find(".item-genryo:checkbox:checked:last");//get last genryo is checked

        //break when genryo is not checked
        if (selectedGenryo.length == 0) {
            App.ui.loading.close();
            return;
        }

        //原料行＋工程行が150行を超えていないこと
        if (!HaigoHyo_Tab.detail.checkMaxRow(1)) {

            App.ui.loading.close();
            return;
        }

        App.ui.loading.show();
        setTimeout(function () {
            //フラグ変更画面
            page.values.isChange = true;

            var isInsertAfter = true,
                dataGenryo = {},
                entity, sample,
                id;

            //reset region out tbody
            selectedGenryo = element.find(selectedGenryo).closest('tbody');
            id = selectedGenryo.attr("data-key");

            if (!App.isUndefOrNull(id)) {
                entity = $.extend(true, {}, HaigoHyo_Tab.detail.dataHaigo.entry(id));

                HaigoHyo_Tab.detail.options.maxSeqKotei[entity.cd_kotei] += 1;

                dataGenryo.cd_shain = entity.cd_shain;
                dataGenryo.nen = entity.nen;
                dataGenryo.no_oi = entity.no_oi;
                dataGenryo.cd_kotei = entity.cd_kotei;
                dataGenryo.sort_kotei = entity.sort_kotei;
                dataGenryo.zoku_kotei = entity.zoku_kotei;
                dataGenryo.nm_kotei = entity.nm_kotei;
                dataGenryo.seq_kotei = HaigoHyo_Tab.detail.options.maxSeqKotei[entity.cd_kotei];
                dataGenryo.cd_tani = App.settings.app.genryo_tani_AOH.Kg;
            }

            //data shisaku hin
            var dataHeader = HaigoHyo_Tab.getDataHeader();
            //基本情報.工場　担当会社
            dataGenryo.cd_kaisha = dataHeader.cd_kaisha;
            //基本情報.工場　担当工場
            dataGenryo.cd_busho = dataHeader.cd_kojo;

            //insert new row after row last
            HaigoHyo_Tab.detail.addNewItem(dataGenryo, false, selectedGenryo);

            //sort genryo in this kotei
            HaigoHyo_Tab.detail.sortGenryo(selectedGenryo);

            App.ui.loading.close();
        }, 1);
    };

    /**
    * 原料削除
    */
    HaigoHyo_Tab.detail.delGenryo = function () {
        var element = HaigoHyo_Tab.detail.element.find(".datatable"),
            selectedGenryo = element.find(".item-genryo:checkbox:checked");//get all genryo is checked

        //break when genryo is not checked
        if (selectedGenryo.length == 0) {
            return;
        }

        var options = {
            text: App.str.format(App.messages.app.AP0027, { name: "原料行" })
        };

        //原価試作依頼されている原料を含む行（配合量に値が入っている行）は【原料削除】できない
        var flg_check_del = false, selected;
        $.each(selectedGenryo, function (index, item) {
            selected = element.find(item).closest('td').findP("flg_shisanIrai").text();
            if (selected == HaigoHyo_Tab.values.flg_shisanIrai) {
                flg_check_del = true;
                return false;
            }
        });

        if (flg_check_del) {
            App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0033, { strName: "原料削除" })).remove();
            App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0033, { strName: "原料削除" })).show();
            return;
        }

        HaigoHyo_Tab.confirmDialog(options).then(function () {

            App.ui.loading.show();
            setTimeout(function () {

                // Bug #1738
                App.ui.page.notifyAlert.remove("AP0203_BFS");
                //フラグ変更画面
                page.values.isChange = true;

                var selected, target;
                $.each(selectedGenryo, function (index, item) {
                    selected = element.find(item).closest("tbody");

                    if (!selected.length) {
                        return;
                    }

                    HaigoHyo_Tab.detail.dataTable.dataTable("deleteRow", selected, function (row) {
                        var id = row.attr("data-key");

                        row.find(":input").each(function (i, elem) {

                            //remove mess error
                            App.ui.page.notifyAlert.remove(elem);

                            target = row.find(this);
                            if (target.hasClass("quantity")) {

                                id = target.attr("data-key");
                                if (!App.isUndefOrNull(id)) {

                                    entity = HaigoHyo_Tab.detail.dataShisakuList.entry(id);
                                    HaigoHyo_Tab.detail.dataShisakuList.remove(entity);;
                                }
                            }
                        });

                        id = row.attr("data-key");
                        if (!App.isUndefOrNull(id)) {
                            var entity = HaigoHyo_Tab.detail.dataHaigo.entry(id);
                            HaigoHyo_Tab.detail.dataHaigo.remove(entity);
                        }
                    });
                });

                //check genryo no kotei. add new genryo when not exit
                HaigoHyo_Tab.detail.checkGenryo();
            }, 1);
        })
    };


    /**
     * check genryo no kotei. add new genryo when not exit
     */
    HaigoHyo_Tab.detail.checkGenryo = function () {
        var element = HaigoHyo_Tab.detail.element.find(".datatable"),
            kotei = element.find(".item-kotei:checkbox");

        var thisKotei, nextRow, id, entity,
            dataHeader = HaigoHyo_Tab.getDataHeader();//data shisaku hin

        $.each(kotei, function (index, item) {

            thisKotei = element.find(item).closest("tbody");
            nextRow = thisKotei.next();

            if (nextRow.hasClass("row-kotei") || nextRow.hasClass("item_total_kotei_tml")) {

                id = thisKotei.attr("data-key");
                entity = HaigoHyo_Tab.detail.dataKotei.entry(id);

                HaigoHyo_Tab.detail.options.maxSeqKotei[entity.cd_kotei] = 1;

                newData = {
                    cd_shain: entity.cd_shain,
                    nen: entity.nen,
                    no_oi: entity.no_oi,
                    cd_kotei: entity.cd_kotei,
                    //基本情報.工場　担当会社
                    cd_kaisha: dataHeader.cd_kaisha,
                    //基本情報.工場　担当工場
                    cd_busho: dataHeader.cd_kojo,

                    zoku_kotei: entity.zoku_kotei,
                    seq_kotei: HaigoHyo_Tab.detail.options.maxSeqKotei[entity.cd_kotei],
                    cd_tani: App.settings.app.genryo_tani_AOH.Kg
                };

                //insert new row genryo after this kotei
                HaigoHyo_Tab.detail.addNewItem(newData, false, thisKotei);
            }
        });

        //renew sort kotei
        HaigoHyo_Tab.detail.sortKotei();
    }

    /**
     * 明細の一覧に新規データを追加します。
     */
    HaigoHyo_Tab.detail.addNewItem = function (newData, isKotei, rowEnd, notBreak) {
        var rowOutput;
        //TODO:新規データおよび初期値を設定する処理を記述します。
        if (!isKotei && !notBreak) {
            HaigoHyo_Tab.detail.dataHaigo.add(newData);
        } else if (!notBreak) {
            HaigoHyo_Tab.detail.dataKotei.add(newData);
        }

        //add new row to end table
        if (!rowEnd) {
            HaigoHyo_Tab.detail.dataTable.dataTable("addRow", function (row) {
                row.form(HaigoHyo_Tab.detail.options.bindOption).bind(newData);

                rowOutput = row;
                return row;
            });
        }

        //add new row to end row selected
        if (rowEnd) {
            HaigoHyo_Tab.detail.dataTable.dataTable("insertRow", rowEnd, true, function (row) {
                row.form(HaigoHyo_Tab.detail.options.bindOption).bind(newData);

                rowOutput = row;
                return row;
            });
        }

        //control display kotei row || genrryo row
        rowOutput = $(rowOutput);
        HaigoHyo_Tab.detail.options.controlDisplayItem(rowOutput, isKotei);

        //change data prop for genryo row.
        if (!isKotei) {
            rowOutput.findP("nm_genryo").show();
            rowOutput.findP("nm_kotei").hide();
            rowOutput.find(".iroHaigo").on("dblclick", HaigoHyo_Tab.detail.beforSetColorHaigo);

            rowOutput.find(".iroHaigo input, .iroHaigo select").css("background", newData.color);
            rowOutput.find(".iroHaigo input, .iroHaigo select").css("color", HaigoHyo_Tab.lightOrDark(newData.color));
            HaigoHyo_Tab.detail.createDataQuanlityRow(rowOutput, newData);
            

            // Set genryo exist status
            if (newData.flg_exist_genryo) {
                HaigoHyo_Tab.detail.setGenryoExistState(rowOutput);
            } else {
                HaigoHyo_Tab.detail.setGenryoNotExistState(rowOutput);
            }
            //add new item validate
            //HaigoHyo_Tab.detail.options.validationsShisakuList["quantity_" + newData.cd_kotei + "_" + newData.seq_kotei] = HaigoHyo_Tab.values.validationsCellDefault;
            //フォントを赤色・太文字に変える。
            HaigoHyo_Tab.detail.iroBudomari(rowOutput, newData);

            //Ｎ始まりの原料コード、原料コード="999999"の場合は操作可能上記以外の場合は操作不可
            if (newData.cd_genryo && HaigoHyo_Tab.detail.genryoBCheck(newData.cd_genryo) && newData.cd_genryo != App.settings.app.commentCode) {
                rowOutput.findP("nm_genryo").prop("readonly", true);
                rowOutput.findP("tanka").prop("readonly", true);
            }

            if (newData.zoku_kotei === App.settings.app.kotei_AOH.Betten) {
                rowOutput.findP("cd_tani").val(App.settings.app.genryo_tani_AOH.Kg).prop("disabled", true);
            }

        } else {
            rowOutput.findP("nm_genryo").hide();
            rowOutput.findP("nm_kotei").show();
        }

        //原料行＋工程行が150行を超えていないこと
        rowOutput.addClass("row-count");

        //原価試作依頼されている原料を含む行（配合量に値が入っている行）は	
        //【工程属性】【原料CD】【原料名】【単価】【油含有率】【配合量】は変更できない
        //rowOutput.find(".shisanIraied").prop("disabled", newData.flg_shisanIrai == HaigoHyo_Tab.values.flg_shisanIrai)

        return rowOutput;
    };

    /**
    * Set cd_genryo exist status
    */
    HaigoHyo_Tab.detail.setGenryoExistState = function (row) {
        if (!row) return;
        row.findP("cd_genryo").data("not-exist", false);
    }

    /**
    * Set cd_genryo not exist status
    */
    HaigoHyo_Tab.detail.setGenryoNotExistState = function (row) {
        if (!row) return;
        row.findP("cd_genryo").data("not-exist", true);
    }

    /**
    * Check if cd_genryo does not exist in DB
    */
    HaigoHyo_Tab.detail.isNotExistGenryo = function (row) {
        if (!row) return true;
        return (row.findP("cd_genryo").data("not-exist") ? true : false);
    }

    /**
    * 歩留色
    * フォントを赤色・太文字に変える。
    */
    HaigoHyo_Tab.detail.iroBudomari = function (row, data) {

        //工原料工場マスタ．歩留と比較して相違がない場合
        //原料コードが存在なしの場合					
        //原料コードが全て"9"の場合					
        //原料コードがＮ始まりの場合					
        //原料名が★始まりの場合				
        row.findP("budomari").removeClass("change_budomari"); 5

        if (HaigoHyo_Tab.detail.isNotExistGenryo(row)) {
            return;
        }

        if (Number(data.budomari) == Number(data.budomari_gry)
            || data.cd_genryo == ""
            || data.cd_genryo == null
            || data.cd_genryo == App.settings.app.commentCode
            || (data.nm_genryo != null && data.nm_genryo.indexOf("★") == 0)) {
            return;
        }

        //原料工場マスタ．歩留と比較して相違がある場合
        if (Number(data.budomari) != Number(data.budomari_gry)) {
            row.findP("budomari").addClass("change_budomari");
        }

        //if (data.cd_genryo_gry != null && Number(data.budomari) != Number(data.budomari_gry)) {
        //    row.findP("budomari").addClass("change_budomari");
        //}
        //else {
        //    //工原料工場マスタ．歩留と比較して相違がない場合
        //    row.findP("budomari").removeClass("change_budomari");
        //}
    }

    /**
    * init data quanlity。
    */
    HaigoHyo_Tab.detail.createDataQuanlityRow = function (row, dataHaigo) {
        var element = HaigoHyo_Tab.detail.element.find(".datatable"),
            listQlt = row.find(".quantity"),
            isNewData = true;

        //create new data
        var elm, id, entity;
        $.each(listQlt, function (index, item) {
            var newDataQuanlity = {
                cd_shain: dataHaigo.cd_shain,
                nen: dataHaigo.nen,
                no_oi: dataHaigo.no_oi,
                cd_kotei: dataHaigo.cd_kotei,
                seq_kotei: dataHaigo.seq_kotei,
                seq_shisaku: null
            };

            elm = element.find(item).closest("td");

            if (elm.hasClass("th-tmpl")) {
                return true;
            }

            id = elm.attr("data-key");
            entity = HaigoHyo_Tab.detail.dataShisaku.entry(id);

            newDataQuanlity.seq_shisaku = entity.seq_shisaku;

            (isNewData ? HaigoHyo_Tab.detail.dataShisakuList.add : HaigoHyo_Tab.detail.dataShisakuList.attach).bind(HaigoHyo_Tab.detail.dataShisakuList)(newDataQuanlity);
            elm.find(".quantity").attr("data-key", newDataQuanlity.__id);

            //renew data prop
            elm.find(".quantity").attr("data-prop", "quantity_" + dataHaigo.cd_kotei + "_" + dataHaigo.seq_kotei + "_" + newDataQuanlity.seq_shisaku).attr("data-number-format-toFixed", Number(page.values.keta_shosu)).addClass("change_keta_shosu quantity_" + dataHaigo.cd_kotei);
            HaigoHyo_Tab.detail.options.validationsShisakuList["quantity_" + dataHaigo.cd_kotei + "_" + dataHaigo.seq_kotei + "_" + newDataQuanlity.seq_shisaku] = HaigoHyo_Tab.values.validationsCellDefault;
        });

        //renew validate list
        var validations = $.extend(true, {}, HaigoHyo_Tab.detail.options.validations, HaigoHyo_Tab.detail.options.validationsShisakuList, HaigoHyo_Tab.detail.options.validationsTotal);
        HaigoHyo_Tab.detail.validator = HaigoHyo_Tab.detail.element.validation(HaigoHyo_Tab.createValidator(validations));

        //change property name with seq_kotei
        row.findP("quantity").attr("data-prop", "quantity_" + dataHaigo.cd_kotei + "_" + dataHaigo.seq_kotei).addClass("change_keta_shosu quantity_" + dataHaigo.cd_kotei);
        //row.findP("juryo_shiagari_g").attr("data-number-format-toFixed", "null").attr("toFixedMin", "4");
    };

</script>

<%-- 2010-09-10 : START : Bug #15330 Add new class text-selectAll
************
************
2010-09-10 : END : Bug #15330 Add new class text-selectAll--%>

<div class="tab-pane active" id="HaigoHyo_Tab" style="margin-top: 3px">
    <div class="control-label toolbar" style="min-height: 26px; padding: 0px 2px">
        <div class="btn-group">
            <button type="button" class="btn btn-default btn-xs add-kotei">工程挿入</button>
            <button type="button" class="btn btn-default btn-xs move-up-kotei">工程移動 ▲</button>
            <button type="button" class="btn btn-default btn-xs move-down-kotei">工程移動 ▼</button>
            <button type="button" class="btn btn-default btn-xs del-kotei">工程削除</button>
        </div>
        <div class="btn-group" style="margin-left: 20px">
            <button type="button" class="btn btn-default btn-xs add-genryo">原料挿入</button>
            <button type="button" class="btn btn-default btn-xs move-up-genryo">原料移動 ▲</button>
            <button type="button" class="btn btn-default btn-xs move-down-genryo">原料移動 ▼</button>
            <button type="button" class="btn btn-default btn-xs del-genryo">原料削除</button>
        </div>
        <div class="btn-group" style="margin-left: 20px">
            <button type="button" class="btn btn-default btn-xs add-trial-manual">試作列追加</button>
            <button type="button" class="btn btn-default btn-xs del-trial-manual">試作列削除</button>
            <button type="button" class="btn btn-default btn-xs shisaku-retu-tuika-dialog">試作列コピー</button>
            <button type="button" class="btn btn-default btn-xs move-column-to-left">試作列移動<i class="icon-top icon-left icon-arrow-left"></i></button>
            <button type="button" class="btn btn-default btn-xs move-column-to-right">試作列移動<i class="icon-top icon-left icon-arrow-right"></i></button>
        </div>
    </div>
    <table class="datatable" style="display: block;">
        <!--TODO: 明細一覧のヘッダーを定義するHTMLをここに記述します。-->
        <thead>
            <tr class="check-haigo">
                <th rowspan="2" colspan="2" style="width: 38px;">工程</th>
                <th rowspan="2" colspan="2" style="width: 110px; height: 16px;">原料コード</th>
                <th rowspan="2" style="width: 246px;">原料名</th>
                <th rowspan="2" style="width: 80px;">単価</th>
                <th rowspan="2" style="width: 50px;">歩留</th>
                <th rowspan="2" style="width: 50px; border-right-width: 3px;">単位</th>
                <th class="check-content th-tmpl col-sample enableColor check-select-shisaku">
                    <input type="checkbox" class="input-tmpl seihokopi input-shisaku"/>
                    <span data-prop="seq_shisaku" style="margin-left: 5px;display: none"></span>
                    <label data-prop="siki_keisan_head" class="siki_keisan_head" style="display: none"></label>
                    <span data-prop="flg_no_seiho1" class="irai-genka"></span>
                </th>
            </tr>
            <tr>
                <th class="th-tmpl col-sample enableColor">
                    <select class="fiel-validation input-shisaku no_chui" data-prop="no_chui"></select>
                </th>
            </tr>
            <tr>
                <th colspan="8" class="th-none-button">日付</th>
                <th style="text-align: left; padding-left: 3px;" class="th-tmpl col-sample enableColor">
                    <input type="tel" data-prop="dt_shisaku" data-role="date" data-app-format="date" class="data-app-format fiel-validation input-shisaku text-selectAll" /></th>
            </tr>
            <tr class="nm_sample_area">
                <th colspan="8" class="th-none-button">サンプルNo</th>
                <th style="text-align: left; padding-left: 3px; height: 16px;" class="th-tmpl col-sample enableColor">
                    <input type="text" data-prop="nm_sample" class="fiel-validation nm_sample input-shisaku text-selectAll" />
                </th>

            </tr>
            <tr>
                <th colspan="8" class="th-none-button">メモ</th>
                <th style="text-align: left; padding-left: 3px;" class="th-tmpl col-sample enableColor">
                    <textarea class="memo fiel-validation input-shisaku text-selectAll" data-prop="memo" style="width: 100%; margin: 0px; resize: none; outline: none; overflow: hidden" />
                </th>
            </tr>
            <tr>
                <th colspan="8" style="text-align: right; border-right-width: 3px;">（全コピー時の列指定）印刷FG &nbsp<input type="checkbox" style="float: right" class="flg_print_all" /></th>
                <th class="check-content middle th-tmpl col-sample enableColor enableColor-etsuran">
                    <input type="checkbox" class="flg_print input-shisaku" data-prop="flg_print" value="1" />
                </th>
            </tr>
        </thead>
        <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->
        <tbody class="item-tmpl" style="display: none;">
            <tr>
                <td style="width: 15px; text-align: center;" class="checkKotei">
                    <input class="item-kotei remove-kotei input-kotei" type="checkbox" data-prop="col1_kotei" value="1" />
                    <label data-prop="flg_shisanIrai_kotei" style="display: none" class="flg_shisanIrai_kotei"></label>
                </td>
                <td style="width: 23px" class="left">
                    <label data-prop="sort_kotei"></label>
                </td>
                <td style="width: 15px; text-align: center;" class="checkGenryo">
                    <input class="item-genryo remove-genryo" type="checkbox" data-prop="col3_genryo" value="1" />
                    <label data-prop="flg_shisanIrai" style="display: none" class="flg_shisanIrai"></label>
                </td>
                <td style="width: 95px;" class="">
                    <input class="fiel-validation input-genryo remove-genryo shisanIraied genryo-input seihokopiInput genryo-selected text-selectAll" data-prop="cd_genryo" style="display: none" type="tel" maxlength="6" />
                    <select class="fiel-validation remove-kotei input-kotei shisanIraied" data-prop="zoku_kotei"></select>
                </td>
                <td class="iroHaigo ">
                    <input type="text" class="fiel-validation iroHaigo input-kotei input-total shisanIraied text-selectAll" data-prop="nm_kotei" style="display: none" />
                    <input type="text" class="fiel-validation iroHaigo input-kotei input-total shisanIraied seihokopiInput text-selectAll" data-prop="nm_genryo" />
                </td>
                <td class="iroHaigo">
                    <input type="tel" class="fiel-validation number-right iroHaigo limit-input-float-new shisanIraied seihokopiInput right number-kirisu text-selectAll" data-prop="tanka" maxlength="11" data-number-format-tofixed="2" />
                    <%--<input type="text" class="shisanIraied seihokopiInput iroHaigo tanka-replace" style="display: none" readonly="readonly" />--%>
                    <%--<input type="text" data-prop="flg_tanka_hyoji" style="display: none" />--%>
                </td>
                <td class="iroHaigo">
                    <input type="tel" class="fiel-validation number-right iroHaigo limit-input-float-new shisanIraied seihokopiInput right number-kirisu text-selectAll" data-prop="budomari" maxlength="6" data-number-format-tofixed="2" />
                    <%--<input type="text" class="shisanIraied seihokopiInput iroHaigo tanka-replace" style="display: none" readonly="readonly" />--%>
                </td>
                <td style="border-right-width: 3px;" class="iroHaigo">
                    <%--<input type="tel" class="fiel-validation number-right iroHaigo limit-input-float-new shisanIraied seihokopiInput right number-kirisu text-selectAll" data-prop="ritu_abura" maxlength="6" data-number-format-tofixed="2" />--%>
                    <input class="fiel-validation remove-kotei shisanIraied" data-prop="" type="tel" disabled="disabled" />
                    <select class="fiel-validation input-genryo remove-genryo shisanIraied seihokopiInput" data-prop="cd_tani"></select>
                </td>
                <td class="th-tmpl col-sample">
                    <input type="tel" class="fiel-validation quantity input-total limit-input-float-new right number-kirisu text-selectAll" data-prop="quantity" style="ime-mode: disabled" maxlength="11" />
                    <input type="text" class="siki_keisan isTotalKesan" data-prop="siki_keisan" style="display: none" />
                    <input type="text" class="flg_keisan isTotalKesan" data-prop="flg_keisan" style="display: none" />
                </td>
            </tr>
        </tbody>
    </table>
</div>
