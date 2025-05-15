<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="710_HinmeiKaihatsu_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs._710_HinmeiKaihatsu_Dialog" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>


<style>
    #_710_HinmeiKaihatsu_Dialog table {
        table-layout: fixed;
    }

        #_710_HinmeiKaihatsu_Dialog table th {
            border: 0;
        }

    #_710_HinmeiKaihatsu_Dialog .search-list {
        margin-top: -2px;
    }

    #_710_HinmeiKaihatsu_Dialog .modal-body {
        padding-bottom: 0px!important;
    }

</style>
<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var _710_HinmeiKaihatsu_Dialog = {
        options: {
            skip: 0,                             
            top: App.settings.base.dialogDataTakeCount,
            kbnKaishatsu: 1,
            kbnKojyo: 2
        },
        urls: {
            hinCombo: "../Services/ShisaQuickService.svc/ma_kbn_hin?&$select=nm_kbn_hin, kbn_hin&$orderby=kbn_hin",
            bunruiCombo: "../Services/ShisaQuickService.svc/vw_ma_bunrui?&$select=nm_bunrui, cd_bunrui&$orderby=cd_bunrui",
            bunruiComboFP: "../api/_710_HinmeiKaihatsu_Dialog/getFBBunrui?cd_kaisha={0}&cd_kojyo={1}&kbn_hin={2}",
            seihoCombo: "../api/_710_HinmeiKaihatsu_Dialog/GetSeihoCode",
            searchData: "../api/_710_HinmeiKaihatsu_Dialog/Search",
            getKojyoName: "../api/_710_HinmeiKaihatsu_Dialog/GetKojyoName"
        },
        // 呼出元画面からパラメータを貰う
        param: {
            cd_kaisha: "",
            cd_kojyo: "",
            no_gamen: "",
            M_kirikae: "",
            getPageID: function () {
                var param = _710_HinmeiKaihatsu_Dialog.param;
                return param.gamenID || param.pageID || param.IDGamen || param.no_gamen;
            },
            kbn_hin_search: 1
        },
        // 品区分Combobox
        hinComboConfig: {
            mode: [
                // 起動元画面が203_配合登録_開発画面、209_配合登録_工場部門（表示・FP）画面の場合は
                {
                    ID_Gamens: [App.settings.app.no_gamen.haigo_toroku_kaihatsu, App.settings.app.no_gamen.haigo_toroku_kojyo],
                    getComboUrl: function () {
                        var filter = "&$filter=kbn_hin ne 2 and kbn_hin ne 3"
                        return _710_HinmeiKaihatsu_Dialog.urls.hinCombo + filter;
                    }
                },
                // 起動元画面が208_配合一覧（表示・FP）、210_コード振替（表示・FP）の場合は
                {
                    ID_Gamens: [App.settings.app.no_gamen.haigo_ichiran, 210, 705],
                    getComboUrl: function () {
                        var filter = "&$filter=kbn_hin ne 2 and kbn_hin ne 3 and kbn_hin ne 9";
                        if (_710_HinmeiKaihatsu_Dialog.param.kbn_hin_search == 2) {
                            filter = "&$filter=kbn_hin eq 3 or kbn_hin eq 4";
                        }
                        return _710_HinmeiKaihatsu_Dialog.urls.hinCombo + filter;
                    }
                },
                // 起動元画面が200_製法一覧画面
                {
                    ID_Gamens: [App.settings.app.no_gamen.seiho_ichiran],
                    getComboUrl: function () {
                        var filter = "&$filter=kbn_hin ne 2 and kbn_hin ne 3 and kbn_hin ne 9";
                        return _710_HinmeiKaihatsu_Dialog.urls.hinCombo + filter;
                    }
                },
                // 上記以外の場合は
                {
                    ID_Gamens: "DEFAULT",
                    getComboUrl: function () {
                        return _710_HinmeiKaihatsu_Dialog.urls.hinCombo;
                    }
                }
            ],
            // Ajax get combobox data + tranform data
            getData: function () {
                var config = App.common.getModeByPageID(_710_HinmeiKaihatsu_Dialog.hinComboConfig.mode, _710_HinmeiKaihatsu_Dialog.param.getPageID()),
                    url = config.getComboUrl();
                return $.ajax(App.ajax.odata.get(url)).then(function (result) {
                    // 品区分( ６ )製品は名称を「自家原料」に変更して表示する。
                    // Setting in settings.app
                    if (result && result.value && result.value.length) {
                        for (var i = 0 ; i < result.value.length; i++) {
                            var newLiteral = App.settings.app.hinKbnLiteralTranform["_" + result.value[i].kbn_hin]
                            if (!App.isUndefOrNull(newLiteral)) {
                                result.value[i].nm_kbn_hin = newLiteral;
                            }
                        }
                    }
                    return App.async.success(result);
                });
            },
            reload: function () {
                return _710_HinmeiKaihatsu_Dialog.hinComboConfig.getData().then(function (result) {
                    var kbn_hin = _710_HinmeiKaihatsu_Dialog.element.findP("kbn_hin");
                    kbn_hin.children().remove();
                    App.ui.appendOptions(
                        kbn_hin,
                        "kbn_hin",
                        "nm_kbn_hin",
                        result.value,
                        false
                    );
                })
            }
        },
        // 分類Combobox
        bunruiComboConfig: {
            mode: [
                // 起動元画面が203_配合登録_開発画面または200_製法一覧画面の場合は
                {
                    ID_Gamens: [App.settings.app.no_gamen.haigo_toroku_kaihatsu, App.settings.app.no_gamen.seiho_ichiran],
                    getComboUrl: function () {
                        var filter = "&$filter=cd_kaisha eq {0} and cd_kojyo eq {1} and kbn_hin eq {2} and flg_mishiyo eq false",
                            param = _710_HinmeiKaihatsu_Dialog.param;

                        return _710_HinmeiKaihatsu_Dialog.urls.bunruiCombo + App.str.format(filter, param.cd_kaisha, param.cd_kojyo, _710_HinmeiKaihatsu_Dialog.element.findP("kbn_hin").val());
                    },
                    convertToList: function (odataObj) {
                        return odataObj && odataObj.value ? odataObj.value : [];
                    }
                },
                // 上記以外の場合は
                {
                    ID_Gamens: "DEFAULT",
                    getComboUrl: function () {
                        var url = _710_HinmeiKaihatsu_Dialog.urls.bunruiComboFP,
                            param = _710_HinmeiKaihatsu_Dialog.param;

                        return App.str.format(url, param.cd_kaisha, param.cd_kojyo, _710_HinmeiKaihatsu_Dialog.element.findP("kbn_hin").val());
                    },
                    convertToList: function (listData) {
                        return listData ? listData : [];
                    }
                }
            ],
            // Ajax get combobox data
            getData: function () {
                var config = App.common.getModeByPageID(_710_HinmeiKaihatsu_Dialog.bunruiComboConfig.mode, _710_HinmeiKaihatsu_Dialog.param.getPageID()),
                    url = config.getComboUrl();
                return $.ajax(App.ajax.odata.get(url)).then(function (result) {
                    return App.async.success(config.convertToList(result));
                });
            },
            reload: function () {
                _710_HinmeiKaihatsu_Dialog.bunruiComboConfig.getData().then(function (result) {
                    var cd_bunrui = _710_HinmeiKaihatsu_Dialog.element.findP("cd_bunrui");
                    cd_bunrui.children().remove();
                    App.ui.appendOptions(
                        cd_bunrui,
                        "cd_bunrui",
                        "nm_bunrui",
                        result,
                        true
                    );                    
                })
            }
        },
        // 製法番号（管理番号） Combobox
        seihoCodeComboConfig: {
            mode: [
                {
                    ID_Gamens: "DEFAULT",
                    getComboUrl: function () {
                        return _710_HinmeiKaihatsu_Dialog.urls.seihoCombo;
                    }
                }
            ],
            // Ajax get combobox data
            getData: function () {
                var config = App.common.getModeByPageID(_710_HinmeiKaihatsu_Dialog.seihoCodeComboConfig.mode, _710_HinmeiKaihatsu_Dialog.param.getPageID()),
                    url = config.getComboUrl();
                return $.ajax(App.ajax.webapi.get(url));
            }
        },
        // Initlize and tranform dialog UI
        displayConfig: {
            mode: [
                // 画面ID＝２００（製法一覧画面）または２０３（配合登録_開発画面）の場合は
                {
                    ID_Gamens: [App.settings.app.no_gamen.seiho_ichiran, App.settings.app.no_gamen.haigo_toroku_kaihatsu],//["200", "203"],
                    initilized: function () {
                        var element = _710_HinmeiKaihatsu_Dialog.element;
                        element.find(".kaihatsu-only").show();
                        element.find(".nisugata").show();
                        element.find(".cd_kikaku").show();

                    },
                    // 品区分 (kbn_hin)＝４（仕掛品）-> Enable radio choose kojyo
                    // Show or hide detail properties
                    resumed: function () {
                        var element = _710_HinmeiKaihatsu_Dialog.element,
                            nisugata = element.find(".nisugata"),
                            cd_kikaku = element.find(".cd_kikaku"),
                            kbn_hin = element.findP("kbn_hin").val(),
                            isShikakari = kbn_hin == App.settings.app.kbnHin.shikakari ? true : false;
                        //_710_HinmeiKaihatsu_Dialog.displayConfig.getDefaultUI();
                        switch (kbn_hin) {
                            // 1 - 原料
                            case App.settings.app.kbnHin.genryo.toString():
                                nisugata.show();
                                cd_kikaku.show();
                                break;
                            // 3 - 配合
                            // 4 - 仕掛品
                            case App.settings.app.kbnHin.haigo.toString():
                            case App.settings.app.kbnHin.shikakari.toString():
                                nisugata.hide();
                                cd_kikaku.hide();
                                break;
                            // 5 - 前処理原料
                            case App.settings.app.kbnHin.maeshoriGenryo.toString():
                                nisugata.hide();
                                cd_kikaku.show();
                                break;
                            // 6 - 自家原料
                            case App.settings.app.kbnHin.jikaGenryo.toString():
                                nisugata.hide();
                                cd_kikaku.show();
                                break;
                            // Others
                            default:
                                nisugata.hide();
                                cd_kikaku.hide();
                                break;
                        }
                    },
                    resumeChanged: function () {
                        var element = _710_HinmeiKaihatsu_Dialog.element,
                            kbn_hin = element.findP("kbn_hin").val();
                        //_710_HinmeiKaihatsu_Dialog.displayConfig.getDefaultUI();
                        switch (kbn_hin) {
                            // 3 - 配合
                            // 4 - 仕掛品
                            case App.settings.app.kbnHin.haigo.toString():
                            case App.settings.app.kbnHin.shikakari.toString():
                                // 仕掛品区分
                                //element.find(".check-group.name-kojo *").prop("disabled", false);
                                element.findP("kaihatsu").prop("checked", true);
                                // 製法番号
                                element.find(".seiho").prop("disabled", false);
                                break;
                            default: 
                                // 仕掛品区分
                                element.find(".check-group.name-kojo *").prop("disabled", true).prop("checked", false);
                                // 製法番号
                                element.find(".seiho").prop("disabled", true).val("");
                                break;
                        }
                    }
                },
                // 画面ID＝208（配合一覧（表示・FP））、210_コード振替（表示・FP）または209（配合登録_工場部門（表示・FP））、705_配合データコピーの場合は
                {
                    ID_Gamens: [App.settings.app.no_gamen.haigo_ichiran, 210, App.settings.app.no_gamen.haigo_toroku_kojyo, 705],//["208", "210", "209", "705"],
                    initilized: function () {
                        var element = _710_HinmeiKaihatsu_Dialog.element;
                        element.find(".kaihatsu-only").hide();
                        element.find(".nisugata").show();
                        element.find(".cd_kikaku").show();
                    },
                    resumed: function () {
                        var element = _710_HinmeiKaihatsu_Dialog.element,
                            nisugata = element.find(".nisugata"),
                            kbn_hin = element.findP("kbn_hin").val(),
                            cd_kikaku = element.find(".cd_kikaku");
                        //_710_HinmeiKaihatsu_Dialog.displayConfig.getDefaultUI();
                        switch (kbn_hin) {
                            // 1 - 原料
                            case App.settings.app.kbnHin.genryo.toString():
                                nisugata.show();
                                cd_kikaku.show();
                                break;
                            // 3 - 配合
                            // 4 - 仕掛品
                            case App.settings.app.kbnHin.haigo.toString():
                            case App.settings.app.kbnHin.shikakari.toString():
                                nisugata.hide();
                                cd_kikaku.hide();
                                break;
                            // 5 - 前処理原料
                            case App.settings.app.kbnHin.maeshoriGenryo.toString():
                                nisugata.hide();
                                cd_kikaku.show();
                                break;
                            // 6 - 自家原料
                            case App.settings.app.kbnHin.jikaGenryo.toString():
                                nisugata.hide();
                                cd_kikaku.show();
                                break;
                            // Others
                            default:
                                nisugata.hide();
                                cd_kikaku.hide();
                                break;
                        }
                    },
                    resumeChanged: function () {
                        var element = _710_HinmeiKaihatsu_Dialog.element,
                            kbn_hin = element.findP("kbn_hin").val();
                        //_710_HinmeiKaihatsu_Dialog.displayConfig.getDefaultUI();
                        switch (kbn_hin) {
                            // 3 - 配合
                            // 4 - 仕掛品
                            case App.settings.app.kbnHin.haigo.toString():
                            case App.settings.app.kbnHin.shikakari.toString():
                                // 製法番号
                                element.find(".seiho").prop("disabled", false);
                                break;
                            default: 
                                // 製法番号
                                element.find(".seiho").prop("disabled", true).val("");
                                break;
                        }
                    }
                }
            ],
            // Initilized display when active the dialog
            initilized: function () {
                var mode = App.common.getModeByPageID(_710_HinmeiKaihatsu_Dialog.displayConfig.mode, _710_HinmeiKaihatsu_Dialog.param.getPageID());
                if (mode && mode.initilized) {
                    mode.initilized();
                }
            },
            // resume display when change properties that change the UI
            resumed: function () {
                var mode = App.common.getModeByPageID(_710_HinmeiKaihatsu_Dialog.displayConfig.mode, _710_HinmeiKaihatsu_Dialog.param.getPageID());
                if (mode && mode.resumed) {
                    mode.resumed();
                }
            },
            // resume display when change properties that change the UI
            resumeChanged: function () {
                var mode = App.common.getModeByPageID(_710_HinmeiKaihatsu_Dialog.displayConfig.mode, _710_HinmeiKaihatsu_Dialog.param.getPageID());
                var element = _710_HinmeiKaihatsu_Dialog.element;
                var kbn_hin = element.findP("kbn_hin").val();
                if (kbn_hin == App.settings.app.kbnHin.maeshoriGenryo || kbn_hin == App.settings.app.kbnHin.sagyo) {
                    element.findP("cd_bunrui").prop("disabled", true);
                } else {
                    element.findP("cd_bunrui").prop("disabled", false);
                }
                if (mode && mode.resumeChanged) {
                    mode.resumeChanged();
                }
            },
            clearDetail: function () {
                _710_HinmeiKaihatsu_Dialog.bind([], true);
            },
            getKojyoName: function () {
                var param = _710_HinmeiKaihatsu_Dialog.param;
                $.ajax(App.ajax.webapi.get(_710_HinmeiKaihatsu_Dialog.urls.getKojyoName, param)).then(function (result) {
                    if (result) {
                        _710_HinmeiKaihatsu_Dialog.element.findP("nm_kojyo").text(result.nm_kojyo);
                        _710_HinmeiKaihatsu_Dialog.su_code_standard = result.su_code_standard;
                    } 
                })
            },
            getDefaultUI: function () {
                var element = _710_HinmeiKaihatsu_Dialog.element;
                // 代表工場
                //_710_HinmeiKaihatsu_Dialog.displayConfig.getKojyoName();
                // 仕掛品区分
                element.find(".check-group.name-kojo *").prop("checked", false).prop("disabled", true);
                // 分類
                element.findP("cd_bunrui").val("").prop("disabled", false);
                // 名称/コード
                element.findP("nm_hinmei").prop("disabled", false);
                // 製法番号　（会社コード）
                element.findP("cd_kaisha").prop("disabled", true);
                // 製法番号　（管理番号）
                element.findP("seiho_shurui").prop("disabled", true);
                // 製法番号　（年）
                element.findP("seiho_nen").prop("disabled", true);
                // 製法番号　（シーケンス番号）
                element.findP("seiho_renban").prop("disabled", true);
            }
        },
        // search function 
        searchConfig: {
            mode: [
                // 起動元画面は開発部門　（画面ID＝２００（製法一覧画面）または２０３（配合登録_開発画面））の場合は
                {
                    ID_Gamens: [App.settings.app.no_gamen.seiho_ichiran, App.settings.app.no_gamen.haigo_toroku_kaihatsu],//["200", "203"],
                    getMode: function () {
                        var element = _710_HinmeiKaihatsu_Dialog.element,
                            param = _710_HinmeiKaihatsu_Dialog.param,
                            searchCondition = _710_HinmeiKaihatsu_Dialog.element.find(".search-criteria").form().data();
                        switch (searchCondition.kbn_hin) {
                            // 品区分＝１（原料）で検索の時
                            case App.settings.app.kbnHin.genryo.toString():
                                return 1;
                                break;
                            // 品区分＝４（仕掛品）で検索の時
                            case App.settings.app.kbnHin.shikakari.toString():
                                // 画面で開発部門ラジオボタンを選択する場合は
                                if (element.findP("kaihatsu").is(":checked")) {
                                    return 2;
                                }
                                // 画面でFOODPROCSラジオボタンを選択する場合は
                                else {
                                    return 3;
                                }
                                break;
                            // 品区分＝５（前処理原料）で検索の時
                            case App.settings.app.kbnHin.maeshoriGenryo.toString():
                                return 4;
                                break;
                            // 品区分＝６（自家原料）で検索の時
                            case App.settings.app.kbnHin.jikaGenryo.toString():
                                return 5;
                                break;
                            // 品区分＝９（作業指示）で検索の時 (２０３（配合登録画面）のみ）
                            case App.settings.app.kbnHin.sagyo.toString():
                                return 6;
                                break;
                        }
                        return null;                        
                    }
                },
                // 起動元画面は工場（画面ID＝２０8（配合一覧（表示・FP））、210_コード振替（表示・FP）または２０9（配合登録_工場部門（表示・FP））、705_配合データコピーの場合は
                {
                    ID_Gamens: [App.settings.app.no_gamen.haigo_ichiran, 210, App.settings.app.no_gamen.haigo_toroku_kojyo, 705],//["208", "210", "209", "705"],
                    getMode: function () {
                        var element = _710_HinmeiKaihatsu_Dialog.element,
                            param = _710_HinmeiKaihatsu_Dialog.param,
                            searchCondition = _710_HinmeiKaihatsu_Dialog.element.find(".search-criteria").form().data();
                        switch (searchCondition.kbn_hin) {
                            // 品区分＝１（原料）で検索の時
                            case App.settings.app.kbnHin.genryo.toString():
                                return 7;
                                break;
                            // 品区分＝４（仕掛品）または３（配合）で検索の時
                            case App.settings.app.kbnHin.haigo.toString():
                            case App.settings.app.kbnHin.shikakari.toString():
                                // 切替モード (M_kirikae)　＝　１（表示用）の　場合は
                                if (param.M_kirikae == App.settings.app.m_kirikae.hyoji) {
                                    return 8;
                                }
                                // 切替モード (M_kirikae)　＝　２（FOODPROCS）の　場合は
                                else {
                                    return 9;
                                }
                                break;
                            // 品区分＝５（前処理原料）で検索の時
                            case App.settings.app.kbnHin.maeshoriGenryo.toString():
                                return 10;
                                break;
                            // 品区分＝６（自家原料）で検索の時
                            case App.settings.app.kbnHin.jikaGenryo.toString():
                                return 11;
                                break;
                            // 品区分＝９（作業指示）で検索の時 ２０9_配合登録_工場部門（表示・FP）画面のみ
                            case App.settings.app.kbnHin.sagyo.toString():
                                return 12;
                                break;
                        }
                        return null;
                    }
                }
            ],
            // get parameters send to search controller
            getSearchParam: function () {
                var mode = App.common.getModeByPageID(_710_HinmeiKaihatsu_Dialog.searchConfig.mode, _710_HinmeiKaihatsu_Dialog.param.getPageID()),
                    dialogParam = _710_HinmeiKaihatsu_Dialog.param,
                    element = _710_HinmeiKaihatsu_Dialog.element;
                if (mode && mode.getMode) {
                    var param = element.find(".search-criteria").form().data();
                    param.mode = mode.getMode();
                    // 製法番号
                    param.seiho_kaisha = param.cd_kaisha;
                    // 代表工場
                    param.cd_kaisha = dialogParam.cd_kaisha;
                    param.cd_kojyo = dialogParam.cd_kojyo;
                    param.no_seiho_kaisha = dialogParam.no_seiho_kaisha;
                    // 開発部門 OR FOODPROCS
                    param.kbn_kojo = element.findP("kaihatsu").is(":checked") ? 1 : element.findP("FP_kojo").is(":checked") ? 2 : 0;
                    // Select Top
                    param.top = _710_HinmeiKaihatsu_Dialog.options.top;
                    // Const
                    $.extend(param, App.common.getSettingsByName(["kbnHin"]));
                    // Special change param
                    // For kbn_hin = 4 and gamen 200, 203
                    if (param.mode == 2 && dialogParam.getPageID() === App.settings.app.no_gamen.seiho_ichiran) {
                        param.no_seiho_kaisha = param.cd_kaisha;
                    }
                    if (param.mode) {
                        return param;
                    }
                }
                return null;
            },
            // search hinmei
            search: function () {
                var searchParam = _710_HinmeiKaihatsu_Dialog.searchConfig.getSearchParam();
                //var element = _710_HinmeiKaihatsu_Dialog.element;

                // Get su_code_standard
                //_710_HinmeiKaihatsu_Dialog.su_code_standard_display = _710_HinmeiKaihatsu_Dialog.su_code_standard;
                //if (searchParam.mode > 6) {
                //    _710_HinmeiKaihatsu_Dialog.su_code_standard_display = 0;
                //} else {
                //    var kbn_hin = searchParam.kbn_hin;
                //    if (kbn_hin == App.settings.app.kbnHin.haigo
                //        || kbn_hin == App.settings.app.kbnHin.maeshoriGenryo
                //        || (kbn_hin == App.settings.app.kbnHin.shikakari && element.findP("FP_kojo").is(":checked"))) {
                //        _710_HinmeiKaihatsu_Dialog.su_code_standard_display++;
                //    }
                //}

                if (searchParam) {
                    searchParam.su_code_standard = _710_HinmeiKaihatsu_Dialog.su_code_standard;
                    return $.ajax(App.ajax.webapi.post(_710_HinmeiKaihatsu_Dialog.urls.searchData, searchParam))
                }
                return App.async.fail();
            }
        }
    };

    /**
     * 検索ダイアログの初期化処理を行います。
     */
    _710_HinmeiKaihatsu_Dialog.initialize = function () {
        var element = $("#_710_HinmeiKaihatsu_Dialog");

        element.on("hidden.bs.modal", _710_HinmeiKaihatsu_Dialog.hidden);
        element.on("shown.bs.modal", _710_HinmeiKaihatsu_Dialog.shown);
        element.on("show.bs.modal", _710_HinmeiKaihatsu_Dialog.show);
        element.on("click", ".search", _710_HinmeiKaihatsu_Dialog.search);
        element.on("change", ".search-criteria :input", _710_HinmeiKaihatsu_Dialog.change);
        //単一セレクトの場合は、下の１行を使用します
        //element.on("click", ".search-list tbody ", _710_HinmeiKaihatsu_Dialog.select);
        //複数セレクトの場合は、上の１行を削除し、下の３行をコメント解除します
        element.on("click", ".select", _710_HinmeiKaihatsu_Dialog.select);
        //element.on("click", ".search-list tbody", _710_HinmeiKaihatsu_Dialog.selectOne);
        //element.find("[name='select_cd_all']").on("click", _710_HinmeiKaihatsu_Dialog.selectAll);
        _710_HinmeiKaihatsu_Dialog.element = element;
        _710_HinmeiKaihatsu_Dialog.displayConfig.initilized();

        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        _710_HinmeiKaihatsu_Dialog.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#_710_HinmeiKaihatsu_Dialog .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        _710_HinmeiKaihatsu_Dialog.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#_710_HinmeiKaihatsu_Dialog .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body",
                show: function () {
                    element.find(".alert-message").show();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                }
            });

        _710_HinmeiKaihatsu_Dialog.validator = element.validation(App.validation(_710_HinmeiKaihatsu_Dialog.options.validations, {
            success: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _710_HinmeiKaihatsu_Dialog.setColValidStyle(item.element);

                    _710_HinmeiKaihatsu_Dialog.notifyAlert.remove(item.element);
                }
            },
            fail: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _710_HinmeiKaihatsu_Dialog.setColInvalidStyle(item.element);
                    if (state && state.suppressMessage) {
                        continue;
                    }

                    _710_HinmeiKaihatsu_Dialog.notifyAlert.message(item.message, item.element).show();
                }
            },
            always: function (results) {
                //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
            }
        }));

        element.find(".modal-dialog").draggable({
            drag: true,
        });

        //TODO: 検索条件の初期化処理を記述します。

        _710_HinmeiKaihatsu_Dialog.loadMasterData().then(function (result) {

            //return _710_HinmeiKaihatsu_Dialog.loadDialogs();
        }).then(function (result) {
            //TODO: 画面の初期化処理成功時の処理を記述します。

        }).fail(function (error) {
            _710_HinmeiKaihatsu_Dialog.notifyAlert.message(App.ajax.handleError(error).message).show();

        }).always(function (result) {
            
        });
    };
    /**
     * マスターデータのロード処理を実行します。
     */
    _710_HinmeiKaihatsu_Dialog.loadMasterData = function () {

        // 抽出条件
        return App.async.all({
            //hinCombo: _710_HinmeiKaihatsu_Dialog.hinComboConfig.getData(),
            //bunruiCombo: _710_HinmeiKaihatsu_Dialog.bunruiComboConfig.getData(),
            seihoCombo: _710_HinmeiKaihatsu_Dialog.seihoCodeComboConfig.getData()
        }).then(function (result) {
            
            var element = _710_HinmeiKaihatsu_Dialog.element;

            //var kbn_hin = element.findP("kbn_hin");
            //kbn_hin.children().remove();
            //App.ui.appendOptions(
            //    kbn_hin,
            //    "kbn_hin",
            //    "nm_kbn_hin",
            //    result.successes.hinCombo.value,
            //    true
            //);

            //var cd_bunrui = element.findP("cd_bunrui");
            //cd_bunrui.children().remove();
            //App.ui.appendOptions(
            //    cd_bunrui,
            //    "cd_bunrui",
            //    "nm_bunrui",
            //    result.successes.bunruiCombo.value,
            //    true
            //);

            var seiho_shurui = element.findP("seiho_shurui");
            seiho_shurui.children().remove();
            App.ui.appendOptions(
                seiho_shurui,
                "seiho_shurui",
                "seiho_shurui",
                result.successes.seihoCombo,
                true
            );
        })

        //TODO: マスタデータのロード処理を実装後、下の1行を削除してください。
        //return App.async.success();
    };
    

    //* 単項目要素をエラーのスタイルに設定します。
    //* @param target 設定する要素

    _710_HinmeiKaihatsu_Dialog.setColInvalidStyle = function (target) {
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
    _710_HinmeiKaihatsu_Dialog.setColValidStyle = function (target) {
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
    _710_HinmeiKaihatsu_Dialog.hidden = function (e) {
        var element = _710_HinmeiKaihatsu_Dialog.element,
            table = element.find(".search-list");

        //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
        element.find(":input").val("");
        element.findP("cd_bunrui").children().remove();
        //TODO: 複数セレクトの場合は、下の１行をコメント解除します。
        //element.find(":checked").prop("checked", false);
        table.find("tbody").not(".item-tmpl").remove();

        element.findP("data_count").text("");
        element.findP("data_count_total").text("");

        var items = element.find(".search-criteria :input:not(button)");
        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            _710_HinmeiKaihatsu_Dialog.setColValidStyle(item);
        }

        _710_HinmeiKaihatsu_Dialog.notifyInfo.clear();
        _710_HinmeiKaihatsu_Dialog.notifyAlert.clear();
    };

    /**
     * 検索ダイアログ表示時処理を実行します。
     */
    _710_HinmeiKaihatsu_Dialog.shown = function (e) {

        _710_HinmeiKaihatsu_Dialog.element.find(":input:not(button):first").focus();
    };

    _710_HinmeiKaihatsu_Dialog.show = function () {
        _710_HinmeiKaihatsu_Dialog.displayConfig.getKojyoName();
        _710_HinmeiKaihatsu_Dialog.displayConfig.getDefaultUI();
        _710_HinmeiKaihatsu_Dialog.hinComboConfig.reload().then(function () {
            _710_HinmeiKaihatsu_Dialog.displayConfig.resumed();
            _710_HinmeiKaihatsu_Dialog.element.findP("kbn_hin").change();
        });
    }
    /**
     * 検索ダイアログ　バリデーションルールを定義します。
     */
    _710_HinmeiKaihatsu_Dialog.options.validations = {
        kbn_hin: {
            rules: {
                required: true
            },
            options: {
                name: "品区分"
            },
            messages: {
                required: App.messages.base.required
            }
        },
        nm_hinmei: {
            rules: {
                maxbytelength: 60
            },
            options: {
                name: "名称／コード",
                byte: 30
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        cd_kaisha: {
            rules: {
                digits: true,
                maxlength: 4
            },
            options: {
                name: "製法番号（会社コード）"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                digits: App.messages.base.digits
            }
        },
        seiho_nen: {
            rules: {
                digits: true,
                maxlength: 2
            },
            options: {
                name: "製法番号（年）"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                digits: App.messages.base.digits
            }
        },
        seiho_renban: {
            rules: {
                digits: true,
                maxlength: 4
            },
            options: {
                name: "製法番号（シーケンス番号）"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                digits: App.messages.base.digits
            }
        }
    };

    /**
     * 検索ダイアログの検索処理を実行します。
     */
    _710_HinmeiKaihatsu_Dialog.search = function () {

        _710_HinmeiKaihatsu_Dialog.validator.validate()
        .then(function () {
            var loadingTaget = _710_HinmeiKaihatsu_Dialog.element.find(".modal-content");
            //table.find("tbody:visible").remove();
            _710_HinmeiKaihatsu_Dialog.notifyAlert.clear();
            App.ui.loading.show("", loadingTaget);

            _710_HinmeiKaihatsu_Dialog.searchConfig.search()
            .done(function (result) {
                _710_HinmeiKaihatsu_Dialog.displayConfig.clearDetail();
                _710_HinmeiKaihatsu_Dialog.displayConfig.resumed();
                _710_HinmeiKaihatsu_Dialog.bind(result);
            }).always(function () {

                App.ui.loading.close(loadingTaget);
            });
        });
    };

    _710_HinmeiKaihatsu_Dialog.change = function (e) {
        var target = $(e.target),
            property = target.attr("data-prop"),
            validateTarget = target;

        _710_HinmeiKaihatsu_Dialog.validator.validate({
            targets: validateTarget
        }).then(function () {
            switch (property) {
                case "kbn_hin":
                    _710_HinmeiKaihatsu_Dialog.bunruiComboConfig.reload();
                    _710_HinmeiKaihatsu_Dialog.displayConfig.resumeChanged();
                    break;
                case "cd_kaisha":
                    target.val(App.common.getFullCdKaisha(target.val()));
                    break;
                case "seiho_nen":
                    target.val(App.common.getFullSeihoNen(target.val()));
                    break;
                case "seiho_renban":
                    target.val(App.common.getFullSeihoRenban(target.val()));
                    break;
            }
            if (target.hasClass("seiho")) {
                validateTarget = _710_HinmeiKaihatsu_Dialog.element.find(":input.seiho");
                _710_HinmeiKaihatsu_Dialog.validator.validate({
                    targets: validateTarget
                })
            }
        });
    }

    /**
     * 検索ダイアログの検索条件を組み立てます
     * search base on many mode -> use searchConfig insteads
     */
    _710_HinmeiKaihatsu_Dialog.createFilter = function () {
    };

    _710_HinmeiKaihatsu_Dialog.bindOptions = {
        appliers: {
            cd_hin: function (value, element) {
                var su_code_standard = _710_HinmeiKaihatsu_Dialog.su_code_standard_display;
                element.text(App.common.fillString(value, su_code_standard))
                return true;
            }
        }
    }

    /**
     * 検索ダイアログの一覧にデータをバインドします。
     */
    _710_HinmeiKaihatsu_Dialog.bind = function (data, isInitlized) {
        var element = _710_HinmeiKaihatsu_Dialog.element,
            table = element.find(".search-list"),
            count = data.Count,
            items = data.Items ? data.Items : data,
            i, l, item, clone;

        if (isInitlized) {
            element.findP("data_count").text("");
            element.findP("data_count_total").text("");
        } else {
            element.findP("data_count").text(items.length);
            element.findP("data_count_total").text(count);
        }

        _710_HinmeiKaihatsu_Dialog.data = App.ui.page.dataSet();
        _710_HinmeiKaihatsu_Dialog.data.attach(items);

        table.find("tbody:visible").remove();

        for (i = 0, l = items.length; i < l; i++) {
            item = items[i];
            clone = table.find(".item-tmpl").clone();
            clone.form(_710_HinmeiKaihatsu_Dialog.bindOptions).bind(item);
            clone.appendTo(table).removeClass("item-tmpl").show();
        }

        if (count && count > App.settings.base.dialogDataTakeCount) {
            _710_HinmeiKaihatsu_Dialog.notifyInfo.message(App.str.format(App.messages.app.AP0021, count), "710_HinmeiKaihatsu_AP0021").show();
        }
    };

    /**
     * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
     */
    _710_HinmeiKaihatsu_Dialog.select = function (e) {
        var element = _710_HinmeiKaihatsu_Dialog.element,
            target = $(e.target),
            id = target.closest("tbody").attr("data-key"),
            data;

        if (App.isUndef(id)) {
            return;
        }

        data = _710_HinmeiKaihatsu_Dialog.data.entry(id);

        if (App.isFunc(_710_HinmeiKaihatsu_Dialog.dataSelected)) {
            if (!_710_HinmeiKaihatsu_Dialog.dataSelected(data)) {
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
        _710_HinmeiKaihatsu_Dialog.select = function (e) {
            var element = _710_HinmeiKaihatsu_Dialog.element,
                items;

            //TODO: 選択された行から起動元画面に返却したい値を抽出します
            items = element.find(".search-list").find("input:checked[name='select_cd']").map(function (index, item) {
                var tbody = $(item).closest("tbody"),
                    id = tbody.attr("data-key"),
                    data = _710_HinmeiKaihatsu_Dialog.data.entry(id);
                return data.cd_torihiki;
            }).toArray();

            if (items.length == 0) {
                _710_HinmeiKaihatsu_Dialog.notifyAlert.message(App.messages.base.MS0020).show();
                return;
            }

            if (App.isFunc(_710_HinmeiKaihatsu_Dialog.dataSelected)) {
                if (!_710_HinmeiKaihatsu_Dialog.dataSelected(items)) {
                    element.modal("hide");
                }
            } else {
                element.modal("hide");
            }
        };

        /**
         * 一覧の行をクリックした際の処理を実行します。（複数セレクト用）
         */
        _710_HinmeiKaihatsu_Dialog.selectOne = function (e) {
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
        _710_HinmeiKaihatsu_Dialog.selectAll = function (e) {
            var target = $(e.target);
            _710_HinmeiKaihatsu_Dialog.element.find("[name='select_cd']:visible").prop("checked", target.is(":checked"));
        };
--%>
</script>

<div class="modal fade wide smaller" tabindex="-1" id="_710_HinmeiKaihatsu_Dialog">
    <div class="modal-dialog" style="height: 350px; width: 750px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">品名検索</h4>
            </div>

            <div class="modal-body">
                <div class="search-criteria">
                    <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
                    <div class="row kaihatsu-only">
                        <div class="control-label col-xs-2">
                            <label>代表工場</label>
                        </div>
                        <div class="control col-xs-10">
                            <label data-prop="nm_kojyo" class="overflow-ellipsis"></label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-label col-xs-2">
                            <label>品区分</label>
                        </div>
                        <div class="control col-xs-3">
                            <select data-prop="kbn_hin" class="hin"></select>
                        </div>
                        <div class="control col-xs-1">
                        </div>
                        <div class="control col-xs-2">
                            <label class="check-group kaihatsu-only name-kojo">
                                <input type="radio" name="nm_kojo" value="1" data-prop="kaihatsu" checked />開発部門
                            </label>
                        </div>
                        <div class="control col-xs-4">
                            <label class="check-group kaihatsu-only name-kojo">
                                <input type="radio" name="nm_kojo" value="2" data-prop="FP_kojo" />FOODPROCS
                            </label>
                        </div>
                        <%--<div class="control col-xs-4">
                        <span style="white-space:nowrap;"></span>
                    </div>--%>
                    </div>
                    <div class="row">
                        <div class="control-label col-xs-2">
                            <label>分類</label>
                        </div>
                        <div class="control col-xs-3">
                            <select class="bunrui" data-prop="cd_bunrui"></select>
                        </div>
                        <div class="control col-xs-7">
                            <span style="white-space: nowrap;"></span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-label col-xs-2">
                            <label>名称／コード</label>
                        </div>
                        <div class="control col-xs-10">
                            <input type="text" data-prop="nm_hinmei" maxlength="60" style="width: 303px;"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-label col-xs-2">
                            <label>製法番号</label>
                        </div>
                        <div class="control col-xs-10">
                            <input type="tel" style="width: 70px;" disabled="disabled" class="seiho limit-input-int" data-prop="cd_kaisha" maxlength="4" />
                            <span style="width: 30px;">-</span>
                            <select style="width: 70px;" disabled="disabled" class="seiho limit-input-int" data-prop="seiho_shurui"></select>
                            <span style="width: 30px;">-</span>
                            <input type="tel" style="width: 50px;" disabled="disabled" class="seiho limit-input-int" data-prop="seiho_nen" maxlength="2" />
                            <span style="width: 30px;">-</span>
                            <input type="tel" style="width: 70px;" disabled="disabled" class="seiho limit-input-int" data-prop="seiho_renban" maxlength="4" />
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
                </div>

                <div class="dialog-table">
                    <table class="table table-striped table-condensed " style="margin-bottom: 0px;">
                        <!--TODO: ダイアログのヘッダーを定義するHTMLをここに記述します。列幅の合計が100％になるように定義します。-->
                        <thead>
                            <tr>
                                <th style="width: 80px;"></th>
                                <th style="width: 80px;">コード</th>
                                <th style="">名称</th>
                                <th style="width: 60px;" class="nisugata">荷姿</th>
                                <th style="width: 155px;" class="cd_kikaku">規格書番号</th>
                            </tr>
                        </thead>
                    </table>
                </div>
                <div style="height: 252px; overflow: scroll; overflow-x: hidden;">
                    <table class="table table-striped table-condensed search-list" id="detail">
                        <!--TODO: ダイアログの明細行を定義するHTMLをここに記述します。列幅の指定はヘッダーに合わせて定義します。-->
                        <tbody class="item-tmpl" style="display: none;">
                            <tr>
                                <%--単一セレクトの場合は、以下の３行を使用する--%>
                                <td style="width: 80px;">
                                    <button type="button" style="margin: 1px; padding: 1px;" class="btn btn-success btn-xs select">選択</button>
                                </td>
                                <td style="width: 80px;">
                                    <label data-prop="cd_hin_disp"></label>
                                </td>
                                <td style="">
                                    <label data-prop="nm_hin" class="overflow-ellipsis"></label>
                                </td>
                                <td style="width: 60px;" class="nisugata">
                                    <label data-prop="kbn_hin"></label>
                                </td>
                                <td style="width: 155px;" class="cd_kikaku">
                                    <label data-prop="no_kikaku"></label>
                                </td>
                                <%--複数セレクトの場合は、上の３行をカットし、下の３行をコメント解除してください--%>
                                <%--                                <td style="width: 5%;">
                                    <input type="checkbox" name="select_cd" />
                                </td>--%>
                            </tr>
                        </tbody>
                    </table>
                </div>
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
