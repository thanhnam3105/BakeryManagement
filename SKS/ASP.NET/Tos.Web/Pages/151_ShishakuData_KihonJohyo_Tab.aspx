<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="151_ShishakuData_KihonJohyo_Tab.aspx.cs" Inherits="Tos.Web.Pages._151_ShishakuData_KihonJohyo_Tab" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchInputDialog(Ver2.1)】 Template--%>
<style type="text/css">
    #KihonJohyo_Tab .new tr td {
        padding: 0px;
        margin: 0px;
        height: 19px;
        font-size: 12px;
    }

    #KihonJohyo_Tab .new tr {
        height: 19px !important;
        padding: 0px;
    }

    #KihonJohyo_Tab .new input[type='tel'],
    #KihonJohyo_Tab .new input[type='text'] {
        border: none;
        padding: 0px;
        padding-left: 4px;
        margin: 0px;
        width: 100%;
        height: 100%;
        padding-left: 2px;
    }

    #KihonJohyo_Tab .new select {
        padding: 0px;
        border: none;
        border-width: 0px;
        margin: 0px;
        width: 100%;
        height: 100% !important;
        font-size: 12px;
        text-align: left;
        padding-left: 2px;
    }

    #KihonJohyo_Tab .new label {
        /*height:100%;
            width:100%;*/
        padding: 0px;
        padding-left: 4px;
        margin: 0px;
    }


    #KihonJohyo_Tab .input-active.dropdown-active div.item {
        display: none!important;
    }

    .input-active.dropdown-active input {
        width: calc(100% - 10px)!important;
    }


    #KihonJohyo_Tab .new .required {
        color: red;
    }

    #KihonJohyo_Tab .textarea-validate .has-error {
        background-color: #ffdab9!important;
    }

    #KihonJohyo_Tab .new .tani-combo {
        width: 50%;
        float: right;
        border-left: 1px solid;
        border-left-color: darkgray;
    }

        #KihonJohyo_Tab .new .tani-combo option {
            background-color: white!important;
        }

    /*2019-09-12 : START : Q&A #15319 選択行の色*/
    #KihonJohyo_Tab .new .label-not-color {
        background-color: #efefef;
        color: rgb(0, 0, 0);
        cursor: default;
    }
    /*2019-09-12 : END : Q&A #15319 選択行の色*/

    #KihonJohyo_Tab .new select option {
        background-color: white;
    }

    #KihonJohyo_Tab .textarea-validate textarea:read-only {
        background-color: #efefef;
    }

    #KihonJohyo_Tab .datalistcb {
        padding-left: 6px!important;
    }
</style>

<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var KihonJohyo_Tab = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.maxInputDataCount,   // TODO:取得するデータ数を指定します。
            filter: ""
        },
        values: {
            isChangeRunning: {},
            dataDetail: null
        },
        urls: {
            loadMasterData: "../api/_151_ShishakuData_KihonJohyo_Tab_/GetMasterData",
            loadSeizoLine: "../api/_151_ShishakuData_KihonJohyo_Tab_/GetSeizoLine"
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
    KihonJohyo_Tab.validationSuccess = function (results, state) {
        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                //列可変はグリッドでもセル単位で色付けを行う
                KihonJohyo_Tab.setColValidStyle(item.element, state.isGridValidation);
            } else {
                KihonJohyo_Tab.setColValidStyle(item.element);
            }

            App.ui.page.notifyAlert.remove(item.element);
        }
    };

    /**
     * バリデーション失敗時の処理を実行します。
     */
    KihonJohyo_Tab.validationFail = function (results, state) {

        var i = 0, l = results.length,
           item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                //列可変はグリッドでもセル単位で色付けを行う
                KihonJohyo_Tab.setColInvalidStyle(item.element, state.isGridValidation);
            } else {
                KihonJohyo_Tab.setColInvalidStyle(item.element);
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
    KihonJohyo_Tab.validationAlways = function (results) {
        //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
    };

    /**
      * 指定された定義をもとにバリデータを作成します。
      * @param target バリデーション定義
      * @param options オプションに設定する値。指定されていない場合は、
      *                画面の success/fail/always のハンドル処理が指定されたオプションが設定されます。
      */
    KihonJohyo_Tab.createValidator = function (target, options) {
        return App.validation(target, options || {
            success: KihonJohyo_Tab.validationSuccess,
            fail: KihonJohyo_Tab.validationFail,
            always: KihonJohyo_Tab.validationAlways
        });
    };

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    KihonJohyo_Tab.setColInvalidStyle = function (target, isGrid) {
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
            //if ($target.hasClass("selectized")) {
            //    $(target).closest("td").addClass("has-error");
            //}
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
    KihonJohyo_Tab.setColValidStyle = function (target, isGrid) {
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
            //if ($target.hasClass("selectized")) {
            //    $(target).closest("td").removeClass("has-error");
            //}
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
    KihonJohyo_Tab.validateAll = function (suppressMessage, isSave) {

        var validations = [];

        validations.push(KihonJohyo_Tab.detail.validateList(suppressMessage, isSave));

        //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。

        return App.async.all(validations);
    };

    /**
     * ダイアログの初期化処理を行います。
     */
    KihonJohyo_Tab.initialize = function () {

        var element = $("#KihonJohyo_Tab");

        element.show();
        KihonJohyo_Tab.element = element;

        //KihonJohyo_Tab.initializeControlEvent();
        KihonJohyo_Tab.detail.initialize();

        KihonJohyo_Tab.detail.loadMasterData().then(function (result) {

            KihonJohyo_Tab.detail.loadData();
        }).fail(function (error) {
            if (error.status === 404) {
                App.ui.page.notifyWarn.message(App.ajax.handleError(error).message).show();
            }
            else {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            }
        }).always(function (result) {

            element.css("display", "");
        });
    };

    /**
    * Get tani
    */
    KihonJohyo_Tab.getTani = function () {
        var td_tani = KihonJohyo_Tab.detail.element.find(".td_tani").form().data();
        return td_tani;
    }

    /**
    * 種別
    */
    KihonJohyo_Tab.detail.shubetsuLabel = function () {
        var element = KihonJohyo_Tab.detail.element;

        return $.trim(element.find(".new").findP("cd_shubetu").find("option:selected").text());
    }

    /**
    * 全コピーブタン
    */
    KihonJohyo_Tab.detail.zenCopy = function () {
        var element = KihonJohyo_Tab.element;

        //所属グループ
        element.findP("nm_group").val(App.ui.page.user.nm_group);
        //所属チーム
        element.findP("nm_team").val(App.ui.page.user.nm_team);

        //コピー時に小数指定、容器・包材、容量（数値入力）（※）をできます。
        element.find(".iraied").prop("disabled", false);
    };

    /**
    * 特徴コピー時にタブをリロードする
    */
    KihonJohyo_Tab.reLoadTab = function () {
        var element = KihonJohyo_Tab.element;

        //所属グループ
        element.findP("nm_group").val(App.ui.page.user.nm_group);
        //所属チーム
        element.findP("nm_team").val(App.ui.page.user.nm_team);
        //所属チーム
        //element.findP("cd_tani").children().remove();

        //コピー時に小数指定、容器・包材、容量（数値入力）（※）をできます。
        element.find(".iraied").prop("disabled", false);
    }

    /**
    * 工程パターンのイベント
    */
    KihonJohyo_Tab.changeKoteiPatan = function (pt_kotei, isValidate) {

        var element = KihonJohyo_Tab.element,
            cd_tani = element.findP("cd_tani"),
            entity = KihonJohyo_Tab.getDataHeader();

        //cd_tani.children().remove();

        //var validate = function (targets) {
        //    return KihonJohyo_Tab.detail.validator.validate({
        //        targets: cd_tani,
        //        state: {
        //            tbody: cd_tani,
        //            isGridValidation: true
        //        }
        //    });
        //};

        //switch (pt_kotei) {
        //    case App.settings.app.pt_kotei.sonohokaeki://その他・加食タイプ

        //        App.ui.appendOptions(
        //            cd_tani,
        //            "cd_literal",
        //            "nm_literal",
        //             jQuery.grep(KihonJohyo_Tab.values.dataKTani, function (n, i) {
        //                 return (n.cd_literal == App.settings.app.cd_tani.gram);
        //             })
        //        );

        //        cd_tani.val(App.settings.app.cd_tani.gram);
        //        entity["cd_tani"] = App.settings.app.cd_tani.gram;

        //        if (isValidate) {
        //            validate(cd_tani);
        //        }

        //        break;

        //    case App.settings.app.pt_kotei.chomieki_2://調味料2液タイプ

        //        App.ui.appendOptions(
        //            cd_tani,
        //            "cd_literal",
        //            "nm_literal",
        //            KihonJohyo_Tab.values.dataKTani
        //        );

        //        cd_tani.val(App.settings.app.cd_tani.ml);
        //        entity["cd_tani"] = App.settings.app.cd_tani.ml;

        //        if (isValidate) {
        //            validate(cd_tani);
        //        }

        //        break;

        //    case App.settings.app.pt_kotei.chomieki_1://調味料1液タイプ

        //        App.ui.appendOptions(
        //            cd_tani,
        //            "cd_literal",
        //            "nm_literal",
        //            KihonJohyo_Tab.values.dataKTani
        //        );

        //        cd_tani.val("");
        //        entity["cd_tani"] = null;
        //        break;

        //    default://「空白」

        //        entity["cd_tani"] = null;
        //        break;

        //}

        return entity["cd_tani"];

    }

    /**
     * init data tab。
     */
    KihonJohyo_Tab.detail.loadData = function () {
        KihonJohyo_Tab.detail.bind(KihonJohyo_Tab.values.dataDetail);
    };

    ///**
    // * ダイアログコントロールへのイベントの紐づけを行います。
    // */
    //KihonJohyo_Tab.initializeControlEvent = function () {
    //    var element = KihonJohyo_Tab.element;

    //    //TODO: 画面全体で利用するコントロールのイベントの紐づけ処理をここに記述します。
    //    //element.on("hidden.bs.modal", KihonJohyo_Tab.hidden);
    //    //element.on("shown.bs.modal", KihonJohyo_Tab.shown);
    //    //element.on("click", ".save", KihonJohyo_Tab.commands.save);
    //};

    /**
     * Load seizoline cbb when change Kojo
     */
    KihonJohyo_Tab.detail.reloadSeizoLine = function (cd_kojo, cd_hoho) {        
        if (App.isUndefOrNullOrStrEmpty(cd_kojo)) {
            cd_kojo = KihonJohyo_Tab.detail.element.find("tbody.new").findP("cd_kojo").val();
        }
        var ajax;
        if (!App.isUndefOrNullOrStrEmpty(cd_kojo)) {
            var paraSearch = {
                B_seizoline: App.settings.app.cd_category.B_seizoline,
                cd_group: App.ui.page.user.cd_group,
                cd_kojo: cd_kojo
            }
            ajax = $.ajax(App.ajax.webapi.get(KihonJohyo_Tab.urls.loadSeizoLine, paraSearch));
        } else {
            ajax = App.async.success([]);
        }
        return ajax.then(function (result) {
            var B_seizoline = KihonJohyo_Tab.detail.element.findP("cd_hoho")
            //製造ライン
            B_seizoline.children().remove();
            App.ui.appendOptions(
                B_seizoline,
                "cd_literal",
                "nm_literal",
                result,
                true
            );

            if (!App.isUndefOrNullOrStrEmpty(cd_hoho)) {
                B_seizoline.val(cd_hoho);
            }
        });
    }

    /**
     * マスターデータのロード処理を実行します。
     */
    KihonJohyo_Tab.detail.loadMasterData = function () {
        var element = KihonJohyo_Tab.element,
            paraSearch = {
                //リテラルマスタ-カテゴリコード
                K_syubetu: App.settings.app.cd_category.shubetsu,
                K_shosu: App.settings.app.cd_category.shosu_shitei,
                B_sakinhoho: App.settings.app.cd_category.B_sakinhoho,
                B_yoki: App.settings.app.cd_category.B_yoki,
                K_syomikikan: App.settings.app.cd_category.kbn_shomikikan_tani,
                B_toriatukaiondo: App.settings.app.cd_category.B_toriatukaiondo,
                B_naiyobunrui: App.settings.app.cd_category.naiyobunrui,
                B_brand: App.settings.app.cd_category.brand,
                B_seizoline: App.settings.app.cd_category.B_seizoline,
                B_tani: App.settings.app.cd_category.tani_AOH,
                //部署表示判別 : 表示
                busho_hyoji: App.settings.app.busho_hyoji.hyoji,
                //権限
                //2019-09-20 : START : Bug #15396 : 試作データの基本情報
                //cd_kengen: App.ui.page.user.cd_kengen,
                cd_kengen: page.flgEditAble.subete,
                //2019-09-20 : END : Bug #15396 : 試作データの基本情報
                EmployeeCD: App.ui.page.user.EmployeeCD,
                cd_busho: App.ui.page.user.cd_busho,
                cd_group: App.ui.page.user.cd_group,
                cd_kaisha: App.settings.app.kaisha_AOH,
                cd_kaisha_change_name: App.settings.app.CD_DAIHYO_KAISHA.kewpie,
                cd_busho_change_name: App.settings.app.CD_DAIHYO_KOJO.kewpie_kenkyu_kaihatsu,
                name_change: App.settings.app.daihyo_busho.kenkyu_honbu
            };

        return $.ajax(App.ajax.webapi.get(KihonJohyo_Tab.urls.loadMasterData, paraSearch)).then(function (result) {

            //工場　担当工場データを保存する。
            //KihonJohyo_Tab.values.dataTantokojo = result.Tanto_kojo;
            //特徴原料データを保存する。 
            //KihonJohyo_Tab.values.dataTokuchogenryo = result.K_tokucyogenryo;
            //容量データを保存する。  
            //KihonJohyo_Tab.values.dataKTani = result.K_tani;
            //用途データを保存する。			
            //KihonJohyo_Tab.values.dataKyoto = result.K_yoto;
            //殺菌方法データを保存する。							
            KihonJohyo_Tab.values.dataKsakinhoho = result.B_sakinhoho;
            //容器・包材データを保存する。		
            KihonJohyo_Tab.values.dataKyoki = result.B_yoki;
            //荷姿データを保存する。	
            //KihonJohyo_Tab.values.dataKnisugata = result.K_nisugata;

            var K_syubetu = element.findP("cd_shubetu"),
                K_shosu = element.findP("keta_shosu"),
                B_sakinhoho = element.findP("hoho_sakin_data"),
                B_yoki = element.findP("youki_data"),
                B_toriatukaiondo = element.findP("cd_ondo"),
                B_naiyobunrui = element.findP("cd_naiyobunrui"),
                B_brand = element.findP("brand_data"),
                //B_seizoline = element.findP("cd_hoho"),
                B_tani = element.findP("cd_tani"),
                K_syomikikan = element.findP("shomikikan_tani"),
                Tanto_kojo = element.findP("cd_kojo");
                //Tanto_kaisha = element.findP("cd_kaisha"),
                //HanbaiSeki_kaisha = element.findP("cd_hanseki");
            
            if (result.nm_kaisha) {
                element.findP("nm_kaisha").text(result.nm_kaisha);
                element.findP("nm_hanseki").text(result.nm_kaisha);
            }

            //種別				
            K_syubetu.children().remove();
            App.ui.appendOptions(
                K_syubetu,
                "cd_literal",
                "nm_literal",
                result.K_syubetu,
                true
            );

            //小数指定					
            K_shosu.children().remove();
            App.ui.appendOptions(
                K_shosu,
                "cd_literal",
                "nm_literal",
                result.K_shosu,
                true
            );

            //殺菌方法						
            B_sakinhoho.children().remove();
            App.ui.appendOptions(
                B_sakinhoho,
                "nm_literal",
                "nm_literal",
                result.B_sakinhoho,
                true
            );

            //容器・包材		
            B_yoki.children().remove();
            App.ui.appendOptions(
                B_yoki,
                "nm_literal",
                "nm_literal",
                result.B_yoki,
                true
            );

            //取扱温度	
            B_toriatukaiondo.children().remove();
            App.ui.appendOptions(
                B_toriatukaiondo,
                "cd_literal",
                "nm_literal",
                result.B_toriatukaiondo,
                true
            );

            //賞味期間	
            K_syomikikan.children().remove();
            App.ui.appendOptions(
                K_syomikikan,
                "cd_literal",
                "nm_literal",
                result.K_syomikikan,
                true
            );

            //内容分類	
            B_naiyobunrui.children().remove();
            App.ui.appendOptions(
                B_naiyobunrui,
                "cd_literal",
                "nm_literal",
                result.B_naiyobunrui,
                true
            );

            //ブランド
            B_brand.children().remove();
            App.ui.appendOptions(
                B_brand,
                "nm_literal",
                "nm_literal",
                result.B_brand,
                true
            );



            //容量単位
            B_tani.children().remove();
            App.ui.appendOptions(
                B_tani,
                "cd_literal",
                "nm_literal",
                result.B_tani,
                true
            );

            //工場　担当工場
            Tanto_kojo.children().remove();
            App.ui.appendOptions(
                Tanto_kojo,
                "cd_busho",
                "nm_busho",
                result.Tanto_kojo,
                false
            );

            ////賞味期間	
            //Tanto_kaisha.children().remove();
            //App.ui.appendOptions(
            //    Tanto_kaisha,
            //    "cd_kaisha",
            //    "nm_kaisha",
            //    result.Tanto_kaisha
            //);

            ////販責会社	
            //HanbaiSeki_kaisha.children().remove();
            //App.ui.appendOptions(
            //    HanbaiSeki_kaisha,
            //    "cd_kaisha",
            //    "nm_kaisha",
            //    result.Hanbai_kaisha
            //);
        });
    };

    /**
     * ダイアログヘッダー　バリデーションルールを定義します。
     */
    KihonJohyo_Tab.detail.options.validations = {

        hoho_sakin: {
            rules: {
                maxlength: 60,
                check_single_kotations: true
            },
            options: {
                name: "殺菌方法"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        brand: {
            rules: {
                maxlength: 60,
                check_single_kotations: true
            },
            options: {
                name: "ブランド"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        youki: {
            rules: {
                maxlength: 60,
                check_single_kotations: true
            },
            options: {
                name: "容器・包材"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        cd_shubetu: {
            rules: {
                required: true
            },
            options: {
                name: "種別"
            },
            messages: {
                required: App.messages.base.required
            }
        },
        youki: {
            rules: {
                maxlength: 60,
                check_single_kotations: true
            },
            options: {
                name: "容器・包材"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        yoryo: {
            rules: {
                //required: true,
                number: true,
                range: [0.1, 9999999.9],
                pointlength: [7, 1]
            },
            options: {
                name: "容量（数値入力）"
            },
            messages: {
                required: App.messages.base.required,
                number: App.messages.base.number,
                range: App.messages.base.range,
                pointlength: App.messages.base.pointlength
            }
        },
        cd_tani: {
            rules: {
                //required: true
            },
            options: {
                name: "容量単位"
            },
            messages: {
                required: App.messages.base.required
            }
        },
        su_iri: {
            rules: {
                //required: true,
                num_custom: function (value, param, otps, done) {
                    //check null
                    var isEmpty = function (val) {
                        return App.isUndef(val) || App.isNull(val) || (App.isStr(val) && val.length === 0);
                    }

                    if (!value) {
                        return done(true);
                    }
                    if (isEmpty(value)) {
                        return done(true);
                    }

                    value = "" + value;
                    value = value.replace(/,/g, "");

                    var test = (/^\d+$/.test(value));
                    if (test && value > 0) {
                        return done(true);
                    }
                    return done(false);
                }
            },
            options: {
                name: "入数"
            },
            messages: {
                required: App.messages.base.required,
                num_custom: App.str.format(App.messages.app.AP0140, { param: 1, name: "入数" })
            }
        },
        shomikikan: {
            rules: {
                num_custom: function (value, param, otps, done) {
                    //check null
                    var isEmpty = function (val) {
                        return App.isUndef(val) || App.isNull(val) || (App.isStr(val) && val.length === 0);
                    }

                    if (!value) {
                        return done(true);
                    }
                    if (isEmpty(value)) {
                        return done(true);
                    }

                    value = "" + value;
                    value = value.replace(/,/g, "");

                    var test = (/^\d+$/.test(value));
                    if (test && value > 0) {
                        return done(true);
                    }
                    return done(false);
                },
                required_custom: function (value, param, otps, done) {
                    //not event save button
                    if (!otps.isSave) {
                        return done(true);
                    }

                    var isEmpty = function (val) {
                        return App.isUndef(val) || App.isNull(val) || (App.isStr(val) && val.length === 0);
                    }

                    var shomikikan_tani = KihonJohyo_Tab.detail.element.find(".new").findP("shomikikan_tani").val();

                    return done((isEmpty(value) && isEmpty(shomikikan_tani)) || (!isEmpty(value) && !isEmpty(shomikikan_tani)));
                }
            },
            options: {
                name: "賞味期間"
            },
            messages: {
                num_custom: App.str.format(App.messages.app.AP0140, { param: 1, name: "賞味期間" }),
                required_custom: App.messages.app.AP0142
            }
        },
        tokuchogenryo: {
            rules: {
                maxlength: 60,
                check_single_kotations: true
            },
            options: {
                name: "特徴原料"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        youto: {
            rules: {
                maxlength: 60,
                check_single_kotations: true
            },
            options: {
                name: "用途"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        cd_nisugata: {
            rules: {
                maxbytelength: 26,
                check_single_kotations: true
            },
            options: {
                name: "荷姿",
                byte: 13
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        genka: {
            rules: {
                maxlength: 60,
                check_single_kotations: true
            },
            options: {
                name: "小売価格"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        baika: {
            rules: {
                numberBug15481: true,
                min: 0,
                pointlength_custom: function (value, param, otps, done) {
                    value = value == "." ? "" : value;
                    var check = page.stringPointlength(value, { after: 2, max: 15 });
                    return done(check);
                }
            },
            options: {
                name: "売価希望", ketasu1: "15", ketasu2: "12", ketasu3: "2"
            },
            messages: {
                numberBug15481: App.messages.base.number,
                min: App.messages.base.min,
                pointlength_custom: App.messages.app.AP0044
            }
        },
        buturyo: {
            rules: {
                maxlength: 60,
                check_single_kotations: true
            },
            options: {
                name: "年間販売予定数"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        hatubai: {
            rules: {
                maxlength: 60,
                check_single_kotations: true
            },
            options: {
                name: "販売予定日"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                check_single_kotations: App.messages.app.AP0153
            }
        },
        //uriage_k: {
        //    rules: {
        //        maxlength: 60,
        //        check_single_kotations: true
        //    },
        //    options: {
        //        name: "計画売上"
        //    },
        //    messages: {
        //        maxlength: App.messages.base.maxlength,
        //        check_single_kotations: App.messages.app.AP0153
        //    }
        //},
        //rieki_k: {
        //    rules: {
        //        maxlength: 60,
        //        check_single_kotations: true
        //    },
        //    options: {
        //        name: "計画利益"
        //    },
        //    messages: {
        //        maxlength: App.messages.base.maxlength,
        //        check_single_kotations: App.messages.app.AP0153
        //    }
        //},
        //uriage_h: {
        //    rules: {
        //        maxlength: 60,
        //        check_single_kotations: true
        //    },
        //    options: {
        //        name: "販売後売上"
        //    },
        //    messages: {
        //        maxlength: App.messages.base.maxlength,
        //        check_single_kotations: App.messages.app.AP0153
        //    }
        //},
        //rieki_h: {
        //    rules: {
        //        maxlength: 60,
        //        check_single_kotations: true
        //    },
        //    options: {
        //        name: "販売後利益"
        //    },
        //    messages: {
        //        maxlength: App.messages.base.maxlength,
        //        check_single_kotations: App.messages.app.AP0153
        //    }
        //},
        memo: {
            rules: {
                maxlength: 3000,
                check_single_kotations: App.messages.app.AP0153
            },
            options: {
                name: "総合メモ"
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
    KihonJohyo_Tab.detail.initialize = function () {

        var element = KihonJohyo_Tab.element,
            table = element.find(".datatable"),
            offsetHeight = $(window).height() * 15 / 100,
            datatable = table.dataTable({
                height: 100,
                resize: true,
                resizeOffset: 60,
                //onselect: KihonJohyo_Tab.detail.select,
                onchange: KihonJohyo_Tab.detail.change
            });
        table = element.find(".datatable");        //列固定にした場合DOM要素が再作成されるため、変数を再取得

        KihonJohyo_Tab.detail.validator = element.validation(KihonJohyo_Tab.createValidator(KihonJohyo_Tab.detail.options.validations));
        KihonJohyo_Tab.detail.element = element;
        KihonJohyo_Tab.detail.dataTable = datatable;

        //Define manual area
        var element_head_sample = element.find('.dt-container .flow-container .dt-head table thead tr');
        KihonJohyo_Tab.detail.element_head_sample = element_head_sample;

        element.findP("memo").on("change", KihonJohyo_Tab.detail.changeMemo);

        // 行選択時に利用するテーブルインデックスを指定します
        KihonJohyo_Tab.detail.fixedColumnIndex = element.find(".fix-columns").length;

        table.on("click", "tr", KihonJohyo_Tab.detail.select);
    };

    /**
    * 荷姿CD : 容量＋容量単位+'/'+入り数
    */
    KihonJohyo_Tab.detail.createNisugata = function () {
        var element = KihonJohyo_Tab.detail.element.find(".new"),
            yoryo = element.findP("yoryo").val(),//容量（数値入力）
            tani_name = element.findP("cd_tani").find("option:selected").html(),
            su_iri = element.findP("su_iri").val();//入り数（※）

        var isEmpty = function (val) {
            if (App.isUndef(val) || App.isNull(val) || (App.isStr(val) && val.length === 0)) {
                return "";
            } else {
                return val;
            }
        }

        return App.async.success((isEmpty(yoryo) + isEmpty(tani_name) + "/" + isEmpty(su_iri)));
    }

    /**
    * event change memo
    */
    KihonJohyo_Tab.detail.changeMemo = function () {
        var element = KihonJohyo_Tab.element,
            entity = KihonJohyo_Tab.getDataHeader();


        var validate = function (targets) {
            return KihonJohyo_Tab.detail.validator.validate({
                targets: targets,
                state: {
                    tbody: KihonJohyo_Tab.detail.element.findP("memo"),
                    isGridValidation: true
                }
            });
        };

        //フラグ変更画面
        page.values.isChange = true;
        validate(KihonJohyo_Tab.detail.element.findP("memo")).then(function () {
            entity["memo"] = element.form().data()["memo"];

            if (App.isFunc(KihonJohyo_Tab.detail.changeKihonTab)) {
                KihonJohyo_Tab.detail.changeKihonTab("memo", entity["memo"]);
            }
        })
    };

    /**
    * 担当営業検索。
    */
    KihonJohyo_Tab.detail.tantoshaEigyoSelect = function (data) {

        var element = KihonJohyo_Tab.element;

        element.findP("cd_eigyo").val(data.id_user).change();
        element.findP("nm_eigyo").text(data.nm_user);
    }

    /**
    * ダイアログ明細へのデータバインド処理を行います。
    */
    KihonJohyo_Tab.detail.bind = function (data, isNewData) {
        KihonJohyo_Tab.detail.dataTable.dataTable("addRow", function (row) {
            row.find(".btn-tanto-eigyo").on("click", KihonJohyo_Tab.detail.tantoshaDialog);
            return row;
        });

        //工程パターンのイベント
        KihonJohyo_Tab.changeKoteiPatan(data.pt_kotei);

        //新規モード時に一つ目が使う。
        if (page.values.modeDisp == App.settings.app.m_shisaku_data.shinki) {
            ////販責会社
            //data.cd_hanseki = KihonJohyo_Tab.detail.element.find(".item-tmpl").findP("cd_hanseki").val();
            ////工場　担当会社
            //data.cd_kaisha = KihonJohyo_Tab.detail.element.find(".item-tmpl").findP("cd_kaisha").val();
            //工場　担当工場
            var hData = page.header.getDataOrg();
            if (hData) {
                hData.cd_kojo = KihonJohyo_Tab.detail.element.find(".item-tmpl").findP("cd_kojo").val();
            }
            //工場　担当工場
            //var kojio = jQuery.grep(KihonJohyo_Tab.values.dataTantokojo, function (n, i) {
            //    return (n.cd_kaisha == data.cd_kaisha);
            //})

            //if (kojio.length) {
            //    data.cd_kojo = kojio[0].cd_busho;
            //} else {
            //    data.cd_kojo = null;
            //}
        }

        data.keta_shosu = data.keta_shosu == null ? data.keta_shosu : "00" + data.keta_shosu;
        KihonJohyo_Tab.detail.reloadSeizoLine(data.cd_kojo).always(function () {
            KihonJohyo_Tab.element.form(KihonJohyo_Tab.detail.options.bindOption).bind(data);
        });

        //bind data kojo
        //KihonJohyo_Tab.detail.changeTantoKaisha(data.cd_kaisha, data.cd_kojo, true);

        //原価試算依頼時に小数指定、容器・包材、容量（数値入力）（※）をできなくする
        //if (page.values.modeDisp == App.settings.app.m_shisaku_data.shosai) {
        //    KihonJohyo_Tab.detail.element.find(".iraied").prop("disabled", data.flg_shisanIrai == 1);
        //}
    };

    /**
     * 明細の各行にデータを設定する際のオプションを定義します。
     */
    KihonJohyo_Tab.detail.options.bindOption = {
        // TODO: 主キーが直接入力の場合には、修正の場合変更を不可とします。
        appliers: {
            //賞味期間
            shomikikan: function (value, element) {
                element.val(page.detail.formatNumber(value, null).forDisp);
                return true;
            },
            //入り数（※）
            su_iri: function (value, element) {
                element.val(page.detail.formatNumber(value, null).forDisp);
                return true;
            },
            //容量（数値入力）
            yoryo: function (value, element) {
                //2020年度改修案件
                //2020/06/17 : START : 「容量（数値入力）（※）*」項目に少数1桁まで入力可能変更
                //element.val(page.detail.formatNumber(value, null).forDisp);
                element.val(page.detail.formatNumber(value, 1).forDisp);
                //2020/06/17 : END : 「容量（数値入力）（※）*」項目に少数1桁まで入力可能変更
                return true;
            },
            //売価希望（※）
            baika: function (value, element) {
                element.val(page.detail.formatNumber(value, null).forDisp);
                return true;
            }
        }
    };

    /**
     * 明細の一覧の行が選択された時の処理を行います。
     */
    KihonJohyo_Tab.detail.select = function (e, row) {

        KihonJohyo_Tab.detail.element.find(".selected-row").removeClass("selected-row");
        KihonJohyo_Tab.detail.element.find(this).addClass("selected-row");
        //$($(row.element[KihonJohyo_Tab.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab.selected")).removeClass("selected").addClass("unselected");
        //$(row.element[KihonJohyo_Tab.detail.fixedColumnIndex].querySelectorAll(".select-tab")).removeClass("unselected").addClass("selected");

        ////選択行全体に背景色を付ける場合は以下のコメントアウトを解除します。
        //if (!App.isUndefOrNull(KihonJohyo_Tab.detail.selectedRow)) {
        //    KihonJohyo_Tab.detail.selectedRow.element.find(".selected-row").removeClass("selected-row");
        //}
        //row.element.find("tr").addClass("selected-row");
        //KihonJohyo_Tab.detail.selectedRow = row;
    };

    /**
     * 明細の一覧の入力項目の変更イベントの処理を行います。
     */
    KihonJohyo_Tab.detail.change = function (e, row) {
        var target = $(e.target),
            element = KihonJohyo_Tab.element,
            property = target.attr("data-prop"),
            entity = KihonJohyo_Tab.getDataHeader(),
            isRecalcu = false;

        if (property == undefined) {
            return;
        }

        KihonJohyo_Tab.wait(target).then(function () {//2020-08-06 :14007 : Bug #18857 内容量0.1でエラーとなる

            var validate = function (targets) {
                return KihonJohyo_Tab.detail.validator.validate({
                    targets: targets,
                    state: {
                        tbody: row,
                        isGridValidation: true
                    }
                });
            };

            var changeKihonTab = function () {
                if (App.isFunc(KihonJohyo_Tab.detail.changeKihonTab)) {
                    KihonJohyo_Tab.detail.changeKihonTab(property, entity[property], isRecalcu);
                }

                //工場　担当会社
                //if (property == "cd_kaisha") {

                //    entity["cd_kojo"] = null;
                //    //KihonJohyo_Tab.detail.changeTantoKaisha(entity[property]);
                //}

                //原価試算タブの容量変更,入り数変更,売価希望変更を登録する。
                //if (["", "yoryo", "su_iri", "baika"].indexOf(property) > 0) {
                //    if (App.isFunc(KihonJohyo_Tab.detail.updateOtherTab)) {
                //        KihonJohyo_Tab.detail.updateOtherTab("genkashisan_tab", property, entity[property]);
                //    }
                //}
            }, property_org = entity[property];

            //フラグ変更画面
            page.values.isChange = true;
            validate(target).then(function () {

                if (property == "shomikikan_tani") {
                    validate(element.find(".new").findP("shomikikan"));
                }

                entity[property] = element.form().data()[property];

                if (property === "cd_kojo") {
                    KihonJohyo_Tab.detail.reloadSeizoLine();
                }
                //賞味期間
                if (property == "shomikikan") {
                    if (entity[property]) {
                        value = page.detail.formatNumber(entity[property], null);
                        entity[property] = value.forData + "";
                        target.val(value.forDisp);
                    }
                }

                //容量変更,入り数変更,売価希望変更
                //if (["", "yoryo", "su_iri", "baika"].indexOf(property) > 0) {//2020-08-06 :14007 : Bug #18857 内容量0.1でエラーとなる
                if (property == "yoryo") {
                    if (entity[property]) {
                        var yoryo = entity[property].toString().replace(/,/g, "");
                        entity[property] = yoryo;
                    }
                }
                if (["","su_iri", "baika"].indexOf(property) > 0) {
                    if (entity[property]) {

                        var numFormat = page.detail.formatNumber(entity[property], null);
                        entity[property] = numFormat.forData + "";

                        target.val(numFormat.forDisp);
                    }
                }

                //小数指定
                if (property == "keta_shosu") {
                    if (Number(entity[property]) > Number(property_org)) {

                        changeKihonTab();
                    } else {

                        var options = {
                            text: App.str.format(App.messages.app.AP0038, { keta_shosu: Number(entity[property]) })
                        };

                        KihonJohyo_Tab.confirmDialog(options).then(function () {

                            isRecalcu = true;
                            changeKihonTab();
                        }).fail(function () {

                            entity[property] = property_org;
                            target.val(App.common.getFullString(Number(property_org), "000"));
                        })
                    }
                } else if (property == "cd_tani") {
                    var options = {
                        text: undefined
                    }

                    //if (entity["pt_kotei"] == App.settings.app.pt_kotei.chomieki_1 || entity["pt_kotei"] == App.settings.app.pt_kotei.chomieki_2) {
                    //    if (property_org == App.settings.app.cd_tani.ml && entity[property] == App.settings.app.cd_tani.gram) {
                    //        options.text = App.str.format(App.messages.app.AP0024, { strName: "「比重」" });
                    //    }
                    //}

                    if (options.text) {
                        KihonJohyo_Tab.confirmDialog(options).then(function () {
                            changeKihonTab();
                        }).fail(function () {
                            target.val(property_org);
                        })
                    } else {
                        changeKihonTab();
                    }
                } else {
                    changeKihonTab();
                }
            });
        })
    };

    /**
     * reload data【工場　担当工場】with【工場　担当会社】value。　
     */
    KihonJohyo_Tab.detail.changeTantoKaisha = function (cd_kaisha, cd_kojo, isBind) {
        var element = KihonJohyo_Tab.element,
            Tanto_kojo = element.findP("cd_kojo");

        Tanto_kojo.children().remove();
        App.ui.appendOptions(
            Tanto_kojo,
            "cd_busho",
            "nm_busho",
            jQuery.grep(KihonJohyo_Tab.values.dataTantokojo, function (n, i) {
                return (n.cd_kaisha == cd_kaisha);
            })
        );

        if (isBind) {
            Tanto_kojo.val(cd_kojo);
            if (App.isFunc(KihonJohyo_Tab.detail.checkShinkiUpdate)) {
                KihonJohyo_Tab.detail.checkShinkiUpdate();
            }
        } else {
            if (App.isFunc(KihonJohyo_Tab.detail.changeKihonTab)) {
                KihonJohyo_Tab.detail.changeKihonTab("cd_kojo", Tanto_kojo.val());
            }
        }
    };

    /**
     * 明細のバリデーションフィルターを設定します。（必須チェックを行わない）
     */
    KihonJohyo_Tab.detail.validationFilter = function (item, method, state, options) {
        return method !== "required";
    };

    /**
     * 明細の一覧全体のバリデーションを実行します。
     */
    KihonJohyo_Tab.detail.validateList = function (suppressMessage, isSave) {
        var validations = [],
            options = {
                state: {
                    suppressMessage: suppressMessage,
                    isSave: isSave
                }
            };

        KihonJohyo_Tab.detail.dataTable.dataTable("each", function (row, index) {
            row.element.find(":input").each(function (index, elem) {
                if ($(elem).closest("td").hasClass("th-tmpl")) {
                    return true;
                }
                validations.push(KihonJohyo_Tab.detail.validator.validate({
                    targets: $(elem),
                    state: {
                        suppressMessage: suppressMessage,
                        tbody: row.element,
                        isGridValidation: true,
                        isSave: isSave
                    }
                }));

            });
        });

        validations.push(KihonJohyo_Tab.detail.validator.validate({
            targets: KihonJohyo_Tab.detail.element.findP("memo"),
            state: {
                suppressMessage: suppressMessage,
                tbody: KihonJohyo_Tab.detail.element.findP("memo"),
                isGridValidation: true,
                isSave: isSave
            }
        }));

        return App.async.all(validations);
    };

</script>

<%-- 2010-09-10 : START : Bug #15330 Add new class text-selectAll
************
************
2010-09-10 : END : Bug #15330 Add new class text-selectAll--%>

<div class="tab-pane KihonJohyo_Tab" id="KihonJohyo_Tab" style="margin-top: 3px;">
    <div class="row">
        <div class="col-xs-6" style="padding-left: 0px; padding-right: 0px">
            <table class="datatable" style="width: 99.9%">
                <!--TODO: 明細一覧のヘッダーを定義するHTMLをここに記述します。-->
                <thead>
                    <tr>
                        <th style="width: 20px; background-color: #ffffff; border-left: none; border-right: none; border-top: none;"></th>
                        <th style="width: 300px; background-color: #ffffff; border-left: none; border-right: none; border-top: none;"></th>
                        <th style="background-color: #ffffff; border-left: none; border-right: none; border-top: none;"></th>
                    </tr>
                </thead>
                <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->

                <tbody class="item-tmpl" style="display: none;">
                    <tr>
                        <td rowspan="4" style="background-color: #efefef">
                            <label style="padding: 2px; margin: 2px;">試作表</label>
                        </td>
                        <td>
                            <label>所属グループ</label>
                        </td>
                        <td>
                            <input type="text" class="label-not-color text-selectAll" data-prop="nm_group" readonly="readonly" />
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label>所属チーム</label>
                        </td>
                        <td>
                            <input type="text" class="label-not-color text-selectAll" data-prop="nm_team" readonly="readonly" />
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label>内容分類</label>
                        </td>
                        <td>
                            <select data-prop="cd_naiyobunrui"></select>
                        </td>

                    </tr>

                    <tr>
                        <td>
                            <label>ブランド</label>
                        </td>
                        <td>
                            <input type="text" data-prop="brand" class="back-yellow text-selectAll datalistcb" list="brand_data">
                            <datalist id="brand_data" data-prop="brand_data"></datalist>
                        </td>

                    </tr>

                    <tr>
                        <td rowspan="2" style="background-color: #efefef">
                            <label></label>
                        </td>
                        <td>
                            <label>種別</label><span class="required">*</span>
                        </td>
                        <td>
                            <select data-prop="cd_shubetu"></select>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label>小数指定</label>
                        </td>
                        <td>
                            <select data-prop="keta_shosu" class="iraied"></select>
                        </td>
                    </tr>
                    <tr>
                        <td rowspan="13" style="background-color: #efefef">
                            <label style="padding: 2px; margin: 2px;">原価試算表</label>
                        </td>
                        <td>
                            <label>販責会社</label>
                        </td>
                        <td>
                            <label data-prop="nm_hanseki"></label>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label>工場　担当会社</label>
                        </td>
                        <td>
                            <label data-prop="nm_kaisha"></label>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label>工場　担当工場</label>
                        </td>
                        <td>
                            <select data-prop="cd_kojo" class=""></select>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label>製造ライン</label>
                        </td>
                        <td>
                            <select data-prop="cd_hoho" class=""></select>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label>殺菌方法</label>
                        </td>
                        <td>
                            <%--<select class="select-input back-yellow" data-prop="hoho_sakin"></select>--%>
                            <input type="text" data-prop="hoho_sakin" class="back-yellow text-selectAll datalistcb" list="hoho_sakin_data">
                            <datalist id="hoho_sakin_data" data-prop="hoho_sakin_data"></datalist>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label>容器・包材</label>
                        </td>
                        <td>
                            <%--<select class="select-input back-yellow" data-prop="youki"></select>--%>
                            <input type="text" data-prop="youki" class="back-yellow text-selectAll datalistcb" list="youki_data">
                            <datalist id="youki_data" data-prop="youki_data"></datalist>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label>容量（数値入力）</label>
                        </td>
                        <td class="td_tani">
                            <%--2020年度改修案件
                            2020/06/17 : START : 「容量（数値入力）（※）*」項目に少数1桁まで入力可能変更
							2020-08-06 : 14007 : Bug #18857 内容量0.1でエラーとなる--%>
                            <%--<input type="tel" data-prop="yoryo" style="width: 50%" class="limit-input-int iraied text-selectAll" maxlength="7" data-number-format-tofixed="0" />--%>
                            <input type="tel" data-prop="yoryo" style="width: 50%" class="limit-input-float-new iraied text-selectAll" maxlength="9" data-number-format-tofixed="0" toFixedMin="1"/>
                            <%--2020/06/17 : END : 「容量（数値入力）（※）*」項目に少数1桁まで入力可能変更--%>
                            <select data-prop="cd_tani" class="iraied tani-combo"></select>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label>入数</label>
                        </td>
                        <td>
                            <input type="tel" data-prop="su_iri" class="limit-input-int text-selectAll" data-number-format-tofixed="null" maxlength="15" />
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label>取扱温度</label>
                        </td>
                        <td>
                            <select data-prop="cd_ondo"></select>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label>賞味期間</label>
                        </td>
                        <td>
                            <input type="tel" data-prop="shomikikan" style="width: 50%" class="limit-input-int text-selectAll" maxlength="4" data-number-format-tofixed="null" />
                            <select data-prop="shomikikan_tani" class="tani-combo"></select>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label>小売価格</label>
                        </td>
                        <td>
                            <input type="text" class="text-selectAll" data-prop="genka" />
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label>年間販売予定数</label>
                        </td>
                        <td>
                            <input type="text" class="text-selectAll" data-prop="buturyo"  />
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label>販売予定日</label>
                        </td>
                        <td>
                            <input type="text" data-prop="hatubai" class="text-selectAll" />
                        </td>
                    </tr>

                </tbody>
            </table>
        </div>
        <div class="col-xs-6" style="padding-top: 2px; padding-left: 5px">
            <div style="height: 300px;">
                <div style="height: 32px; background-color: #f6f6f6; padding-top: 4px; border: 1px solid #ccc;">
                    <label style="vertical-align: middle;">総合メモ</label>
                </div>
                <div style="margin-top: -1px;" class="textarea-validate">
                    <textarea rows="16" style="width: 100%; height: 300px; margin: 0px; resize: none; outline: none; overflow-y: auto" data-prop="memo" class="text-selectAll"></textarea>
                </div>
                <%--     <div style="margin-top: 12px">半角入力</div>
                <div>半角入力</div>--%>
                <div>
                    <div style="float: left; width: 10px; height: 10px; margin-top: 8px; border: 1px solid #333" class="back-yellow"></div>
                    <label>のコンボボックスは編集可能</label>
                </div>
            </div>

        </div>
    </div>
</div>
