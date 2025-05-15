<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="300_SeihoBunshoSakusei_SeizoKoteizu_Tab.aspx.cs" Inherits="Tos.Web.Pages._300_SeihoBunshoSakusei_SeizoKoteizu_Tab" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>

<style type="text/css">
    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .control-tab {
        background-color: #f6f6f6;
        padding: 2px;
        margin-bottom: 2px;
    }

    /*#_300_SeihoBunshoSakusei_SeizoKoteizu_Tab select {
        height: 19px !important;
        border: none;
        padding: 0px;
        margin: 0px;
        width: 100%;
        height: 100%;
    }*/


    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab select {
        border: none;
        padding: 0px;
        margin: 0px;
        width: 100%;
        height: 100%;
    }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .flow-detail {
        /*overflow: auto;*/
        /*height: 400px;*/
        white-space: nowrap;
        /*border: 1px solid #ccc;*/
    }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable-float {
        float: left;
        display: inline-block;
        width: 210px !important;
    }

        #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable-float .datatable {
            width: 200px !important;
            white-space: normal;
        }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable .width-column {
        width: 9% !important;
    }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable th {
        border: double;
        height: 60px !important;
    }

        #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable th .max-lines {
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-box-orient: vertical;
            -webkit-line-clamp: 2;
        }

        #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable th label {
            padding: 0px;
        }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable .height-th-td {
        height: 10px !important;
        border: none;
    }

        #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable .height-th-td td {
            border-top: 1px solid #fff;
            border-left: 1px solid #fff;
            border-right: 1px solid #fff;
        }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable .padding-left {
        padding-left: 0px !important;
        padding-right: 0px !important;
    }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable tr td .bold {
        font-weight: 900;
    }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable .border-item-prev {
        border-bottom: none;
    }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable .border-item {
        border: 1px solid #000 !important;
    }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable .underline {
        text-decoration: underline;
    }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable .bg-white {
        background-color: #fff;
    }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable .bg-yellow {
        background-color: yellow;
    }

        #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable .bg-yellow select {
            background-color: transparent;
        }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable .bg-blue {
        background-color: #b3c6ff;
    }

        #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable .bg-blue select, #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable .bg-blue label {
            background-color: transparent;
        }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable .bg-red {
        background-color: #f00;
        color: #fff;
    }

        #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable .bg-red select, #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable .bg-red label {
            background-color: transparent;
            color: #fff;
        }


    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable .bg-orange {
        background-color: #ffd800;
    }

        #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable .bg-orange select, #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable .bg-orange label {
            background-color: transparent;
        }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable .bg-green {
        background-color: #4dff4d;
    }

        #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable .bg-green select, #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .datatable .bg-green label {
            background-color: transparent;
        }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .check-group {
        padding-right: 27px;
    }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .fix-content table.kotei-table {
        width: 590px;
    }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .fix-content .dropdown-content {
        width: 516px;
    }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .mark-cell {
        width: 100%;
        height: 38px;
        float: left;
        border: none;
        background-color: transparent;
        text-align: left;
        padding-left: 3px!important;
    }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .validate-material {
        float: left;
        width: 0;
        height: 0;
        border: 0;
        background-color: transparent;
        margin-top: -4px;
    }

        #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .validate-material:focus {
            outline: none;
        }

    .fix-wrapper {
        position: absolute;
        top: 70px;
        border: 1px solid #ccc;
        width: 600px;
        border-right: 0;
        border-bottom: 0;
        height: 400px;
        background-color: white;
        overflow: hidden;
    }

    .fix-header {
        position: absolute;
        width: 580px;
        background-color: white;
    }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .mishiyo {
        background-color: lightgray!important;
    }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .no-data-found {
        position: fixed; 
        display: none; 
        opacity: 0.2; 
        font-size: 80px;
        top: calc((100% - 100px) / 2);
        left: calc(50% - 520px);
        z-index: 1000;
    }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .dropdown-content .active {
        background-color: #1e90ff;
    }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .dropdown-content .active label {
        color: white!important;
    }

    #_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .select-cell {
        height: 100%; 
        min-width: 0px;
        padding: 8px 0px 0px 3px;
    }
</style>

<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */

    var _300_SeihoBunshoSakusei_SeizoKoteizu_Tab = {
        options: {
            defaultKoteiContent: {
                type: "genryo",
                kbn_nyuryoku: 1,
                flg_copy_genryo: 0
            },
            grayColor: "lightgray",
            mode: {
                edit: 1,
                view: 2
            },
            cbbType: {
                genryo: {
                    kbn_nyuryoku: 1,
                    name: "genryo"
                },
                kikai: {
                    kbn_nyuryoku: 2,
                    name: "kikai"
                },
                kotei: {
                    kbn_nyuryoku: 3,
                    name: "kotei"
                },
                furi_nyuryoku: {
                    kbn_nyuryoku: 4,
                    name: "furi_nyuryoku"
                },
                shikake_genryo: {
                    kbn_nyuryoku: 5,
                    name: "shikake_genryo"
                },
                shikake_kikai: {
                    kbn_nyuryoku: 6,
                    name: "shikake_kikai"
                },
                shikake_kotei: {
                    kbn_nyuryoku: 7,
                    name: "shikake_kotei"
                },
                shikake_furi_nyuryoku: {
                    kbn_nyuryoku: 8,
                    name: "shikake_furi_nyuryoku"
                }
            }
        },
        values: {
            isChangeRunning: {},
            focusedKotei: ''
        },
        param: {
            no_seiho: "0001-A01-19-0068",
            mode: "",
            haigoMapping: ""
        },
        urls: {
            search: "../api/_300_SeihoBunshoSakusei_SeizoKoteizu_Tab/Search",
            getCopyData: "../api/_300_SeihoBunshoSakusei_SeizoKoteizu_Tab/getCopyData"
        },
        name: "【製造工程図】",
        comboInstance: {},
        defaultColor: [
            "#B9FFB9",
            "#FFFFB9",
            "#FFB9B9",
            "#B9E3FF",
            "#FFB9FF",
            "#B9FFE2",
            "#D5FFB9",
            "#FFE2B9",
            "#8BFFFF",
            "#E2B9FF",
            "#B9D5FF",
            "#B9FFF0",
            "#C7FFB9",
            "#FFF0B9",
            "#FFB9C7",
            "#F0B9FF",
            "#FF8BFF",
            "#B9C7FF",
            "#B9FFFF"
        ],
        searchData: [],
        ComboIndex: 0
    };


    // Config function for combobox container
    // This config use to generate a columns.
    // Each column is a [ShikakariHin]
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.DefaultComboContainerConfig = {
        removeTemplateFragment: function (target) {
            target.removeClass("combo-tmpl").show();
            target.addClass("option-row");
            //target.find(".combo-tmpl").remove();
            return target;
        },
        getNewContainer: function (content, data, options) {
            var clone = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element.find(".fix-detail table.kotei-table.combo-tmpl").clone(),
                lstCbb = data.detail,
                cbbType = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.cbbType;
            if (lstCbb) {
                var genryoData = data.masterGenryo;
                // Convert Display options for genryo cbb
                $.each(genryoData, function (ind, item) {
                    item.cd_hin_opt = App.common.fillString(item.cd_hin, item.su_code_standard);
                    item.nm_hin_opt = item.nm_hin;
                });
                // Convert Display options for kikai cbb
                $.each(data.masterKikai, function (ind, item) {
                    item.cd_hin_opt = item.cd_hin;
                    item.nm_hin_opt = item.nm_hin;
                });
                // Convert Display options for nyuryoku cbb
                $.each(data.masterNyuryoku, function (ind, item) {
                    item.cd_hin_opt = item.cd_hin;
                    item.nm_hin_opt = item.nm_hin;
                });
                // 表示内容取得
                var instance;
                // 入力種別 = 「原料・仕掛原料」
                instance = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(cbbType.genryo.name);
                instance.bindTemplateOption(genryoData, clone, instance);
                // 入力種別 = 「機械・仕掛機械」
                instance = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(cbbType.kikai.name);
                instance.bindTemplateOption(data.masterKikai, clone, instance);
                // 入力種別 = 「工程・仕掛工程」
                instance = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(cbbType.kotei.name);
                instance.bindTemplateOption(data.masterNyuryoku, clone, instance);

                $.each(lstCbb, function (ind, comboData) {
                    var type = comboData.type.replace(" ", "_");
                    var CBoptions = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(type);
                    var cbbType = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.cbbType;
                    switch (type) {
                        case cbbType.genryo.name:
                        case cbbType.shikake_genryo.name:
                            var newCombo = CBoptions.insertCombo(clone, comboData, CBoptions.LAST_POS, CBoptions);
                            CBoptions.bindOtptions(newCombo, genryoData, CBoptions);
                            break;
                        default:
                            var newCombo = CBoptions.insertCombo(clone, comboData, CBoptions.LAST_POS, CBoptions);
                            break;
                    }
                });
            }
            options.removeTemplateFragment(clone);
            clone.appendTo(content);
            return clone;
        },
        addFixContent: function (data, options) {
            var fixDetailContent = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element.find(".fix-detail");
            return options.getNewContainer(fixDetailContent, data, options);
        },
        addFlowContent: function (data, options) {
            var flowDetailContent = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element.find(".flow-wrapper .flow-detail");
            return options.getNewContainer(flowDetailContent, data, options);
        }
    }

    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.DefaultComboContainerHeaderConfig = {
        getHeaderContent: function () {
            return _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element.find(".flow-header");
        },
        removeTemplateFragment: function (target) {
            target.removeClass("combo-tmpl").show();
            target.addClass("option-row");
            return target;
        },
        getNewHeader: function (content, data, options) {
            var clone = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element.find(".fix-header table.kotei-table.combo-tmpl").clone();
            if (data) {
                clone.form(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.bindOptions).bind(data);
                clone.attr("data-code", data.cd_hin);
            }
            if (data.flg_mishiyo == App.settings.app.flg_mishiyo.mishiyo) {
                clone.addClass("mishiyo");
            } else {
                clone.css("background-color", data.id_color);
                clone.attr("data-color", data.id_color);
            }
            options.removeTemplateFragment(clone);
            clone.appendTo(content);
            return clone;
        },
        addFixContent: function (data, options) {
            var fixHeaderContent = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element.find(".fix-header");
            return options.getNewHeader(fixHeaderContent, data, options);
        },
        addFlowContent: function (data, options) {
            var flowHeaderContent = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element.find(".flow-header .kotei-header");
            return options.getNewHeader(flowHeaderContent, data, options);
        },
        findHeaderByCD: function (code, options) {
            if (code) {
                var headerContent = options.getHeaderContent(),
                    target = headerContent.find("table.kotei-table[data-code='" + code + "']");
                return target;
            }
            return null;
        },
        setHeaderColor: function (code, color, options) {
            var target = options.findHeaderByCD(code, options);
            //if (page.lightOrDark(color) == "light") {
            //    target.find("label").css("color", "");
            //} else {
            //    target.find("label").css("color", "white")
            //}
            if (target) {
                target.css("background-color", color);
                target.attr("data-color", color);
            }
        }
    }

    // Default settings for combobox reactions
    // 
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.DefaultComboConfig = {
        TYPE: "default",
        LAST_POS: 1,
        ABOVE_POS: 2,
        UNDER_POS: 3,
        // 入力種別 = 「原料・機械・工程・フリー入力」
        ARROW: "↓",
        //EmptyOptionData: {
        //    cd_hin_opt: "",
        //    nm_hin_opt: ""
        //},
        getContentByTarget: function (target, options) {
            var Content = target.closest(".drop-content"),
                optContent = Content.find(".dropdown-content"),
                inputContent = Content.find("td.inp"),
                labelContent = Content.find("td.lb");
            return {
                content: Content,
                optContent: optContent,
                inputContent: inputContent,
                labelContent: labelContent
            }
        },
        getContentByEvent: function (event, options) {
            return options.getContentByTarget($(event.target), options);
        },
        getComboContainer: function (target) {
            return target.closest("table");
        },
        getTemplateCombo: function (container) {
            return container.find("tr.drop-content.combo-tmpl.other");
        },
        getNewCombo: function (options, container) {
            var clone = options.getTemplateCombo(container).clone();
            clone.find(".dropdown-content").children().remove();
            return clone;
        },
        bindCombo: function (target, data, options, isChangeType) {
            var dataSet = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.data;
            data.arrow = options.ARROW;
            if (!isChangeType) {
                if (data.ts) {
                    dataSet.attach.bind(dataSet)(data);
                    dataSet.remove(dataSet.entry(data.__id));
                }
                var addData = $.extend({}, data);
                addData.__id = null;
                addData.ts = null;
                dataSet.add.bind(dataSet)(addData);
                target.form(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.bindOptions).bind(addData);
            } else {
                target.form(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.bindOptions).bind(data);
            }
            if (data.flg_copy_genryo) {
                target.addClass("mishiyo");
                //data.id_color = "";
            }
            if (!page.values.isFirstLoading || !data.flg_copy_genryo) {
                target.css("background-color", data.id_color);
            }
            if (_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.isHaigoData(data.cd_input_genryo)) {
                target.attr("data-haigo", data.cd_input_genryo);
            }
            if (_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.values.tabMode !== _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.mode.edit) {
                target.findP("select").prop("disabled", true);
            }
            return target;
        },
        applySpecialStyle: function (target) {
            var options = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(target);
            if (options && App.isFunc(options.setSpecialStyle))
                options.setSpecialStyle(target);
        },
        bindOtptions: function (target, data, options) {
            var element = options.getContentByTarget(target);
            var emptyData = options.EmptyOptionData;
            if (data) {
                element.optContent.find("div:not(.combo-tmpl)").remove();
                $.each(data, function (ind, optData) {
                    if (ind == 0 && emptyData) {
                        var clone = element.optContent.find(".combo-tmpl").clone();
                        clone.form(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.bindOptions).bind(emptyData);
                        options.removeTemplateFragment(clone);
                        clone.appendTo(element.optContent);
                    }
                    var clone = element.optContent.find(".combo-tmpl").clone();
                    clone.form(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.bindOptions).bind(optData);
                    if (optData.flg_mishiyo == App.settings.app.flg_mishiyo.mishiyo) {
                        clone.addClass("opt-mishiyo");
                    }
                    options.removeTemplateFragment(clone);
                    clone.appendTo(element.optContent);
                });
            }
        },
        bindTemplateOption: function (data, container, options) {
            if (data) {
                var tmpl = options.getTemplateCombo(container);
                options.bindOtptions(tmpl, data, options);
            }
        },
        removeTemplateFragment: function (target, options) {
            target.removeClass("combo-tmpl").show();
            target.addClass("option-row");
            return target;
        },
        insertCombo: function (target, data, position, options, isChangeType) {
            var container = options.getComboContainer(target),
                newCombo = options.getNewCombo(options, container),
                type = data.type.replace(" ", "_");
            if (data) {
                options.bindCombo(newCombo, data, options, isChangeType);
            }
            newCombo.attr("data-type", type);
            newCombo.attr("index", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.ComboIndex);
            _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.ComboIndex++;
            newCombo.addClass(data.type.replace("_", " "));
            options.removeTemplateFragment(newCombo);
            switch (position) {
                case options.LAST_POS:
                    newCombo.appendTo(container);
                    break;
                case options.ABOVE_POS:

                    newCombo.insertBefore(target.closest("tr"));
                    break;
                case options.UNDER_POS:
                    newCombo.insertAfter(target.closest("tr"));
                    break;
            }
            if (App.isFunc(options.setSpecialStyle))
                options.setSpecialStyle(newCombo, data, options);
            _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.autoAjustContentHeight();
            //options.setFocus(newCombo, options);
            return newCombo;
        },
        changeType: function (target, newData, newCBoptions, options) {
            if (App.isFunc(options.removeStyle))
                options.removeStyle(target, options);
            var next = target.next(),
                newCB = newCBoptions.insertCombo(target, newData, options.ABOVE_POS, newCBoptions, true);
            App.ui.page.notifyAlert.remove(target.findP("nm_genryo_validate"));
            App.ui.page.notifyAlert.remove(target.findP("nm_free_mark"));
            target.remove();
            newCBoptions.setFocus(newCB, newCBoptions);
            options.applySpecialStyle(next);
            return newCB;
        },
        deleteCombo: function (target, options) {
            if (App.isFunc(options.removeStyle))
                options.removeStyle(target, options);
            var next = target.next(),
                nextOptions = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(next);
            App.ui.page.notifyAlert.remove(target.findP("nm_genryo_validate"));
            App.ui.page.notifyAlert.remove(target.findP("nm_free_mark"));
            target.remove();
            _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.data.remove(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.data.entry(target.attr("data-key")));
            options.applySpecialStyle(next);
            _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.autoAjustContentHeight();
        },
        moveUpCombo: function (target, options) {
            var upTarget = target.prev();
            if (!upTarget.length || upTarget.hasClass("tr-frame") || upTarget.findP("select").is(":checked")) {
                return;
            }
            var upOptions = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(upTarget);
            if (App.isFunc(upOptions.removeStyle))
                upOptions.removeStyle(upTarget, upOptions);
            target.insertBefore(upTarget);
            options.applySpecialStyle(target);
        },
        moveDownCombo: function (target, options) {
            var downTarget = target.next();
            if (!downTarget.length || downTarget.hasClass("tr-frame") || downTarget.findP("select").is(":checked")) {
                return;
            }
            var downOptions = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(downTarget);
            if (App.isFunc(options.removeStyle))
                options.removeStyle(target, options);
            target.insertAfter(downTarget);
            downOptions.applySpecialStyle(downTarget);
        },
        isActivedCombobox: function (content) {
            return content.inputContent.is(":visible");
        },
        activeCombobox: function (target, options) {
            if (_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.values.tabMode === _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.mode.edit) {
                var element = options.getContentByTarget(target, options);
                if (!options.isActivedCombobox(element)) {
                    ///////////////////////////////////////////////////////
                    var container = options.getComboContainer(target);
                    var dropOption = options.getTemplateCombo(container).find(".dropdown-content").children().clone();
                    element.optContent.children().remove();
                    dropOption.appendTo(element.optContent);
                    ///////////////////////////////////////////////////////
                    element.labelContent.hide();
                    element.inputContent.show();
                    var tarInput = element.inputContent.find("input");
                    tarInput.focus();
                    options.keyUp(tarInput, options);
                    element.optContent.addClass("show");
                }
            }
            return target;
        },
        deActiveCombobox: function (target, options) {
            var element = options.getContentByTarget(target, options);
            if (options.isActivedCombobox(element)) {
                element.labelContent.show();
                element.inputContent.hide();
                element.optContent.removeClass("show");
                ///////////////////////////////////////////////////////
                element.optContent.children().remove();
                ///////////////////////////////////////////////////////
                element.labelContent.find("label").text(element.inputContent.find("input").val());
            }
            element.content.find(".option-row.active").removeClass("active");
            return target;
        },
        chooseOption: function (target, options) {
            if (target.length > 1) {
                target = $(target[target.length]);
            }
            var element = options.getContentByTarget(target, options),
                text = $.trim(target.text()),
                flg_mishiyo = target.closest("div").hasClass("opt-mishiyo");

            if (flg_mishiyo == App.settings.app.flg_mishiyo.mishiyo) {
                //element.inputContent.addClass("mishiyo");
                element.content.addClass("mishiyo");
            } else {
                //element.inputContent.removeClass("mishiyo");
                element.content.removeClass("mishiyo");
            }
            element.inputContent.find("input").val(text).change();
            options.deActiveCombobox(target, options);
            return target;
        },
        toggleCombobox: function (target, options) {
            return target;
        },
        actionCombobox: function (target, options) {
            var isAction = true;
            if (target.closest(".dropdown-content").length && !target.hasClass("dropdown-content")) {
                options.chooseOption(target, options);
                isAction = false;
            }
            else if (target.closest(".swicth").length) {
                options.activeCombobox(target, options);
                isAction = false;
            }
            else if (target.closest("td.last").length) {
                options.toggleCombobox(target, options);
                isAction = false;
            }
            return isAction;
        },
        toggleCombobox: function (target, options) {
            var element = options.getContentByTarget(target);
            if (options.isActivedCombobox(element))
                options.deActiveCombobox(target, options);
            else
                options.activeCombobox(target, options);
            return target;
        },
        setFocus: function (target, options) {
            if (target.hasClass("selected-kotei") || !target.length)
                return;
            target.addClass("selected-kotei");
            if (_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.CurrentSelected) {
                _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.CurrentSelected.removeClass("selected-kotei");
                var oldSelected = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.CurrentSelected);
                oldSelected.deActiveCombobox(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.CurrentSelected, oldSelected);
            }
            _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.CurrentSelected = target;
            options.setFocusCheckBox(target);
            // internal function to disable delete btn
            _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.disableBtnDelete(target);
            // Uncheck all check box of diff current kotei
            _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.UncheckAll(target, true);
            return target;
        },
        setFocusCheckBox: function (target) {
            $(".check-group [value='" + target.attr("data-type") + "']").prop("checked", true);
        },
        // Search like function 
        keyUp: function (target, options) {
            var element = options.getContentByTarget(target, options),
                optList = element.optContent.find("div:not(.combo-tmpl)"),
                keyWord = target.val().toUpperCase();
            for (i = 0; i < optList.length; i++) {
                var option = $(optList[i]),
                    dropValue = option.find("label").text().toUpperCase();
                //if (keyWord == "" || dropValue == "" || dropValue.indexOf(keyWord) > -1) {
                if (keyWord == "" || dropValue == "" || dropValue.indexOf(keyWord) == 0) {
                    optList[i].style.display = "";
                } else {
                    optList[i].style.display = "none";
                }
            }
        }
    }
    // These methods have some difference with default and it will run instead of defaults
    // Change code here for genryo combobox only - [原料]
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.GenryoComboConfig = {
        TYPE: "genryo",
        EmptyOptionData: null,
        isActivedCombobox: function (content) {
            return content.optContent.is(":visible");
        },
        activeCombobox: function (target, options) {
            if (_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.values.tabMode === _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.mode.edit) {
                var element = options.getContentByTarget(target);
                element.content.findP("nm_genryo_troll").focus();
                if (!options.isActivedCombobox(element)) {
                    var container = options.getComboContainer(target);
                    var dropOption = options.getTemplateCombo(container).find(".dropdown-content").children().clone();
                    element.optContent.children().remove();
                    dropOption.appendTo(element.optContent);
                    element.optContent.addClass("show");

                    //setTimeout(function () {
                    //    element.Content.findP("cd_genryo").focus();
                    //}, 200)
                }
            }
            return target;
        },
        deActiveCombobox: function (target, options) {
            var element = options.getContentByTarget(target);
            if (options.isActivedCombobox(element)) {
                element.optContent.removeClass("show");
                element.optContent.children().remove();
            }
            element.content.find(".option-row.active").removeClass("active");
            return target;
        },
        getTemplateCombo: function (container) {
            return container.find("tr.drop-content.combo-tmpl.genryo");
        },
        setSpecialStyle: function (target, data) {
            var prev = target.prev();
            prev.addClass("genryo-prev");
            if (data && data.flg_copy_genryo != 1)
                target.css("background-color", data.id_color)
        },
        actionCombobox: function (target, options) {
            var isAction = true;
            if (target.closest(".dropdown-content").length && !target.hasClass("dropdown-content")) {
                options.chooseOption(target, options);
                isAction = false;
            }
            else if (target.closest("td.side:not(.arrow)").length) {
                options.toggleCombobox(target, options);
                isAction = false;
            }
            else if (target.closest("td.last").length) {
                options.toggleCombobox(target, options);
                isAction = false;
            }
            return isAction;
        },
        chooseOption: function (target, options) {
            if (target.length > 1) {
                target = $(target[target.length]);
            }
            var Content = target.closest("tr"),
                dataContent = target.closest("div"),
                code = $.trim(dataContent.findP("cd_hin_opt").text()),
                name = $.trim(dataContent.findP("nm_hin_opt").text()),
                flg_mishiyo = target.closest("div").hasClass("opt-mishiyo");

            if (flg_mishiyo == App.settings.app.flg_mishiyo.mishiyo) {
                Content.addClass("mishiyo");
            } else {
                Content.removeClass("mishiyo");
            }

            Content.findP("nm_input_genryo").text(name);
            Content.findP("cd_input_genryo").val(code).change();
            Content.findP("nm_genryo_validate").val(code);
            if (_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.isHaigoData(code)) {
                Content.attr("data-haigo", code);
            } else {
                Content.attr("data-haigo", code);
            }
            options.deActiveCombobox(target, options);
            //_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.setDomainColor(Content);
            return target;
        },
        getNewCombo: function (options, container) {
            var clone = options.getTemplateCombo(container).clone();
            clone.find(".dropdown-content").children().remove();
            return clone;
        },
        keyUp: function (target, options) {
            return;
        },
        removeStyle: function (target, options) {
            target.prev().removeClass("genryo-prev");
        }
    }
    // Change code here for other combobox only
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.FnyuryokuComboConfig = {
    }
    // 仕掛
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.ShikakeComboConfig = {
        ARROW: "←"
    }
    // 機械
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.KikaiComboConfig = {
        TYPE: "Kikkai"
    }
    // 工程
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.KoteiComboConfig = {
        TYPE: "Kotei",
        getTemplateCombo: function (container) {
            return container.find("tr.drop-content.combo-tmpl.kotei");
        }
    }
    // フリー入力
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.FuriNyuryokuComboConfig = {
        TYPE: "FuriNyuryoku",
        activeCombobox: function (target, options) {
            if (_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.values.tabMode === _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.mode.edit) {
                var element = options.getContentByTarget(target, options);
                if (!options.isActivedCombobox(element)) {
                    element.labelContent.hide();
                    element.inputContent.show();
                    var tarInput = element.inputContent.find("input");
                    tarInput.focus();
                    options.keyUp(tarInput, options);
                }
            }
            return target;
        }
    }
    // 仕掛原料
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.ShikakeGenryoComboConfig = {
        TYPE: "ShikakeGenryo",
        ARROW: "←"
    }
    // 仕掛機械
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.ShikakeKikaiComboConfig = {
        TYPE: "ShikakeKikai",
        ARROW: "←"
    }
    // 仕掛工程
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.ShikakeKoteiComboConfig = {
        TYPE: "ShikakeKotei",
        ARROW: "←"
    }
    // 仕掛フリー入力
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.ShikakeFuriNyuryokuComboConfig = {
        TYPE: "ShikakeFuriNyuryoku",
        ARROW: "←"
    }
    // Change code here for other combobox only
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.OtherComboConfig = {

    }

    /**
     * 検索ダイアログの初期化処理を行います。
     */
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.initialize = function () {
        var element = $("#_300_SeihoBunshoSakusei_SeizoKoteizu_Tab");

        //element.on("hidden.bs.modal", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.hidden);
        element.on("shown.bs.modal", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.shown);
        element.on("click", ".search", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.search);
        //TODO: 単一セレクトの場合は、下の１行を使用します
        element.on("click", ".search-list tbody ", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.select);
        element.on("change", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.change);
        element.on("focusin", "[data-prop='nm_genryo_validate']", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.locateInvalid);
        element.on("focus", "[data-prop='nm_free_mark']", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.locateInvalidByMark);
        element.on("keydown", "[data-prop='nm_input_genryo'], [data-prop='nm_genryo_troll']", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.directChooseOptions);
        element.on("click", "div.detail-content", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.UncheckAll);
        //TODO: 複数セレクトの場合は、上の１行を削除し、下の３行をコメント解除します
        //element.on("click", ".select", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.select);
        //element.on("click", ".search-list tbody", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.selectOne);
        //element.find("[name='select_cd_all']").on("click", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.selectAll);
        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element = element;

        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#_300_SeihoBunshoSakusei_SeizoKoteizu_Tab .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body",
                show: function () {
                    element.find(".alert-message").show();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                }
            });

        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.validator = element.validation(App.validation(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.validations, {
            success: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.setColValidStyle(item.element);

                    App.ui.page.notifyAlert.remove(item.element);
                }
            },
            fail: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.setColInvalidStyle(item.element);
                    if (state && state.suppressMessage) {
                        continue;
                    }

                    App.ui.page.notifyAlert.message(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.name + item.message, item.element).show();
                }
            },
            always: function (results) {
                //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
            }
        }));

        //element.on("click", ".dropdown-front", App.common.toggleDropdown);

        //element.on("click", ".kotei-table td:not(.first)", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.swicthInput);
        //element.on("focusout", ".kotei-table td.inp.swicth input", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.swicthLabel);
        element.on("keyup", ".kotei-table td.inp.swicth input.com-inp", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.searchComboData);
        element.on("keydown", ".kotei-table td.inp.swicth input.com-inp", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.keyDown);
        element.on("keyup", ".kotei-table .dropdown-content div", function (e) {
            if (e.which == 13)
                $(e.target).find("label").click();
        });
        //element.on("click", ".dropdown-content", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.chooseDropDownOption);
        //element.on("click", ".kotei-table td", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.setKoteiFocus);
        element.on("click", "#ins-bef", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.insertBeforeCombobox);
        element.on("click", "#ins-aft", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.insertAfterCombobox);
        element.on("click", "#del-kotei", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.deleteComboboxs);
        element.on("click", "#move-up", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.moveUpComboboxs);
        element.on("click", "#move-down", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.moveDownComboboxs);
        //element.on("click", "#clear-all", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.clearAll);
        element.on("change", "input[name='nyuryoku']", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.changeTypeCombo);
        element.on("mouseenter", ".ellipsis-line-2", function () {
            var target = $(this);
            target.prop("title", target.text());
        });

        $(document).on("click", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.clickEvent);
        $(document).on("dblclick", ".kotei-table td", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.openIroSettingDialog);
        //$(document).on("focus", "*", _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.focusEvent);

        var fixBody = element.find(".fix-wrapper"),
            flowHeader = element.find(".flow-header"),
            flowBody = element.find(".flow-wrapper");

        flowBody.on("scroll", function () {
            flowHeader.scrollLeft(flowBody.scrollLeft());
            fixBody.scrollTop(flowBody.scrollTop());
        });

        fixBody.on('mousewheel', function (e) {
            if ($(e.target).closest(".dropdown-content").length)
                return;
            var target = $(this);
            var delta = e.originalEvent.wheelDelta;
            var top = target.scrollTop() - (delta);
            flowBody.scrollTop(top);

            if (top > 0) {
                return false;
            }
        });

        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.fixBody = fixBody;
        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.flowBody = flowBody;

        var DefaultCombo = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.DefaultComboConfig;

        var GenryoCombo = $.extend($.extend({}, _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.DefaultComboConfig), _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.GenryoComboConfig);
        var KikaiCombo = $.extend($.extend({}, _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.DefaultComboConfig), _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.KikaiComboConfig);
        var KoteiCombo = $.extend($.extend({}, _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.DefaultComboConfig), _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.KoteiComboConfig);
        var FuriNyuryokuCombo = $.extend($.extend({}, _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.DefaultComboConfig), _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.FuriNyuryokuComboConfig);

        var ShikakeGenryo = $.extend($.extend({}, GenryoCombo), _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.ShikakeGenryoComboConfig);
        var ShikakeKikai = $.extend($.extend({}, KikaiCombo), _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.ShikakeKikaiComboConfig);
        var ShikakeKotei = $.extend($.extend({}, KoteiCombo), _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.ShikakeKoteiComboConfig);
        var ShikakeFuriNyuryoku = $.extend($.extend({}, FuriNyuryokuCombo), _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.ShikakeFuriNyuryokuComboConfig);


        // Instance of 8 type of comboboxs
        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.comboInstance.kikai = KikaiCombo;
        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.comboInstance.kotei = KoteiCombo;
        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.comboInstance.shikake_kikai = ShikakeKikai;
        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.comboInstance.shikake_kotei = ShikakeKotei;
        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.comboInstance.furi_nyuryoku = FuriNyuryokuCombo;
        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.comboInstance.shikake_furi_nyuryoku = ShikakeFuriNyuryoku;
        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.comboInstance.shikake_genryo = ShikakeGenryo;
        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.comboInstance.genryo = GenryoCombo;

        // 画面からモードのパラメータを引き受けてモードを判定する。
        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.applyTabMode();

        ((!_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.param.canSearch) ? App.async.success({ headerData: [], detailData: [], masterData: {} }) :
        $.ajax(App.ajax.webapi.post(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.urls.search, _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.createFilter())))
            .done(function (result) {

                _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.data = App.ui.page.dataSet();
                _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.searchData = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.convertDataForBinding(result);

                //DefaultCombo.bindTemplateOption(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.kikaiData, DefaultCombo);
                _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.bind(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.searchData);

                element.removeClass("active");
            });
        page.finishLoading("TAB_Initilize", "TAB5", 5);
    };

    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.directChooseOptions = function (e) {
        var target = $(e.target);
        var CBBoptions = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(target);
        var element = CBBoptions.getContentByTarget(target);
        // Check if any options is hovering
        //if (element.Content.find(".dropdown-content :hover").length) {
        //    return true;
        //}
        var dropContent = element.content.find(".dropdown-content");
        var chosenOption = dropContent.find(".active");
        var nextOption = null;
        // key esc
        if (e.which == 27) {
            CBBoptions.deActiveCombobox(target, CBBoptions);
            return false;
        }
        // down arrow
        if (e.which == 40) {
            nextOption = chosenOption.length > 0 ? chosenOption.next() : element.content.find(".dropdown-content .option-row:visible:first");
            if (!nextOption || nextOption.length === 0 || !nextOption.is(":visible")) {
                return false;
            }
        }
        // up arrow
        if (e.which == 38) {
            nextOption = chosenOption.length > 0 ? chosenOption.prev() : element.content.find(".dropdown-content .option-row:visible:last");
            if (!nextOption || nextOption.length === 0 || !nextOption.is(":visible")) {
                return false;
            }
        }
        // key enter
        if (e.which == 13) {
            if (chosenOption.length) {
                chosenOption.click();
                return true;
            }
        }
        // Next target acquired
        if (!nextOption || nextOption.length === 0) {
            return true;
        }
        dropContent.scrollTop(nextOption[0].offsetTop - 30);
        chosenOption.removeClass("active");
        nextOption.addClass("active");
        return false;
    }

    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.bindOptions = {
        appliers: {
            cd_hin: function (value, element) {
                element.text(App.common.fillString(value, page.values.su_code_standard));
                return true;
            },
            cd_input_genryo: function (value, element) {
                element.val(App.common.fillString(value, page.values.su_code_standard));
                return true;
            },
            cd_hin_opt: function (value, element) {
                element.text(App.common.fillString(value, page.values.su_code_standard));
                return true;
            }
        }
    }

    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.createFilter = function () {
        var cbbType = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.cbbType;
        return {
            no_seiho: _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.param.no_seiho,
            no_seiho_copy: _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.param.no_seiho_copy,
            cd_haigo: 208702,
            kbn_genryo: App.settings.app.kbnHin.genryo,
            kbn_haigo: App.settings.app.kbnHin.haigo,
            kbn_shikakari: App.settings.app.kbnHin.shikakari,
            kbn_maeshori: App.settings.app.kbnHin.maeshoriGenryo,
            kbn_sagyo: App.settings.app.kbnHin.sagyo,
            kbn_kikai: cbbType.kikai.kbn_nyuryoku,
            kbn_shikake_kikai: cbbType.shikake_kikai.kbn_nyuryoku,
            kbn_kotei: cbbType.kotei.kbn_nyuryoku,
            kbn_shikake_kotei: cbbType.shikake_kotei.kbn_nyuryoku,
            kbn_shikakari_kaihatsu: App.settings.app.kbn_shikakari.kaihatsu,
            kbn_shikakari_FP: App.settings.app.kbn_shikakari.foodprocs,
            loop_count: App.settings.app.tenkaiLoopCount,
            mode: _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.param.mode,
            lstHaigoCopy: _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getHaigoCopyFromMapping(),
            su_code_standard: page.values.su_code_standard
        }
    }

    // Get list of cd_haigo to copy in other no_seiho
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getHaigoCopyFromMapping = function () {
        var haigo_mapping = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.param.haigoMapping,
            result = [];
        $.each(haigo_mapping, function (ind, item) {
            result.push(item.cd_haigo_moto);
        });
        return result;
    }

    // 画面からモードのパラメータを引き受けてモードを判定する。
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.applyTabMode = function () {
        var element = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element,
            modes = page.options.mode;
        // Default mode for addnew and edit
        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.values.tabMode = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.mode.edit;
        switch (_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.param.mode.toString()) {
            // 新規モード（モード = 1）
            case modes.new:
            case modes.new_copy:
                // Do not thing
                break;
                // 編集モード（モード = 2）
            case modes.edit:
            case modes.edit_copy:
                // Do not thing
                break;
                // 申請・閲覧モード（モード = 3、4、5）
            case modes.view:
                // Disable all things
                element.find(".command-row").find("button, input[type='radio']").prop("disabled", true);
                element.findP("nm_free_mark").prop("readonly", true);
                // View mode 
                _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.values.tabMode = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.mode.view;
                break;
        }
    }

    // Focus to invalid combobox data
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.locateInvalid = function (e) {
        var target = $(e.target);
        var instance = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(target);
        setTimeout(function () {
            instance.setFocus(target.closest("tr"), instance);
            instance.activeCombobox(target, instance);
        }, 1);
    }

    // Focus to invalid combobox data
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.locateInvalidByMark = function (e) {
        var target = $(e.target);
        target.click();
    }

    // Function for [全クリア]
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.clearAll = function () {
        page.dialogs.confirmDialog.confirm({
            text: App.messages.app.AP0055
        }).then(function () {
            // Remove all data in entity
            var entities = [];
            var items = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.data.findAll(function (item, entity) {
                if (entity.state !== App.ui.page.dataSet.status.Deleted) {
                    entities.push(entity);
                }
            });
            for (var i = 0 ; i < entities.length ; i++) {
                _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.data.remove(entities[i]);
            }
            // Refresh display items
            // Prepare display data
            for (var i = 0; i < _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.searchData.length; i++) {
                _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.searchData[i].detail = [$.extend({}, _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.defaultKoteiContent)];
            }
            // Remove all old display data
            _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element.find(".kotei-table:visible").remove();
            // Bind new data
            _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.bind(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.searchData)

        })
    }

    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.isHaigoData = function (cd_haigo) {
        var searchData = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.searchData;
        var isHaigoData = $.grep(searchData, function (item) {
            return item.header.cd_hin == cd_haigo;
        }).length > 0;
        return isHaigoData;
    }

    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.setDomainColor = function (target) {
        if (!target || target.hasClass("mishiyo")) {
            return;
        }
        var element = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element,
            data = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.data.entry(target.attr("data-key")),
            cd_haigo = target.findP("cd_input_genryo").val(),
            headerContent = element.find(".kotei-header table[data-code='" + Number(cd_haigo) + "']");

        var id_color = "";
        if (headerContent.length) {
            id_color = headerContent.attr("data-color");
        }
        target.css("background-color", id_color);
        data.id_color = id_color;
    }

    // Reload copy data in copy mode
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.searchCopy = function () {
        $.ajax(App.ajax.webapi.post(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.urls.getCopyData, _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.createFilter()))
        .done(function (result) {
            var currentData = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.data.findAll(function (item, entity) {
                return entity.state === App.ui.page.dataSet.status.Added;
            });
            // Refresh display items
            // Prepare display data
            for (var i = 0; i < _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.searchData.length; i++) {
                var haigoData = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.searchData[i],
                    copyData = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getReplacedHaigo(haigoData.header.cd_hin, true);
                // If current data is replaced by copy data
                if (copyData) {
                    haigoData.detail = [];
                    $.grep(result, function (item) {
                        if (item.cd_haigo == copyData.cd_haigo_moto) {
                            haigoData.detail.push(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.prepairGenryoData(item, copyData.cd_haigo_saki, haigoData.masterGenryo));
                        };
                    });
                } else {
                    // Re-use current data
                    haigoData.detail = [];
                    $.grep(currentData, function (item) {
                        if (item.cd_haigo == haigoData.header.cd_hin) {
                            var newItem = $.extend({}, item);
                            haigoData.detail.push(newItem);
                        }
                        return false;
                    });
                }

            }
            // Remove all current data
            $.each(currentData, function (ind, item) {
                _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.data.remove(item);
            });
            // Remove all old display data
            _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element.find(".kotei-table:visible").remove();
            // Bind new data
            _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.bind(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.searchData)
        });
    }

    // Get the replaced haigo in copy mode
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getReplacedHaigo = function (cd_haigo, isHaigoSaki) {
        var param = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.param;
        if (!param.haigoMapping || !param.haigoMapping.length) {
            return false;
        }
        var resultSet = [];
        if (param.mode == "new_copy" || param.mode == "edit_copy") {
            for (var i = 0; i < param.haigoMapping.length; i++) {
                var mapping = param.haigoMapping[i];
                if (isHaigoSaki && mapping.cd_haigo_saki == cd_haigo) {
                    return mapping;
                }
                if (!isHaigoSaki && mapping.cd_haigo_moto == cd_haigo) {
                    resultSet.push(mapping);
                }
            }
        }
        if (!isHaigoSaki) {
            return resultSet;
        }
        return false;
    }

    /// Repair data before bind
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.prepairGenryoData = function (genryoData, cd_haigo_saki, masterGenryo) {
        if (cd_haigo_saki) {
            genryoData.cd_haigo = cd_haigo_saki;
            // Use as new data
            genryoData.ts = null;
        }
        // Check if haigo is exist in list haigo in combobox genryo
        var isExistsMeta = true;
        if (genryoData.cd_input_genryo < 999990 || genryoData.cd_input_genryo > 999999) {
            isExistsMeta = $.grep(masterGenryo, function (genryo) {
                return genryo.cd_haigo == genryoData.cd_haigo
                    && (App.isUndefOrNullOrStrEmpty(genryoData.cd_input_genryo)
                    || (genryo.cd_hin == genryoData.cd_input_genryo
                    && genryo.flg_mishiyo == App.settings.app.flg_mishiyo.shiyo));
            }).length > 0;
        }
        genryoData.type = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.convertCBBType(genryoData.kbn_nyuryoku);
        genryoData.nm_genryo_validate = genryoData.nm_input_genryo;
        genryoData.flg_copy_genryo = isExistsMeta ? 0 : 1;
        return genryoData;
    }

    // Convert data from search data to bind data
    // Add some variables support the binding
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.convertDataForBinding = function (data) {
        if (data) {
            var result = [];
            var classified = {};
            // Delete all old data
            $.each(data.wastedDetailData, function (ind, dData) {
                _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.data.attach(dData);
                _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.data.remove(dData);
            });
            // Process current seiho data
            $.each(data.detailData, function (ind, dData) {
                // Delete the replaced haigo
                if (_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getReplacedHaigo(dData.cd_haigo, true)) {
                    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.data.attach(dData);
                    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.data.remove(dData);
                    return;
                }
                dData = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.prepairGenryoData(dData, null, data.masterData.genryoData);
                if (App.isUndef(classified["_" + dData.cd_haigo])) {
                    classified["_" + dData.cd_haigo] = [];
                }
                classified["_" + dData.cd_haigo].push(dData);
            });
            // Process copy seiho data
            $.each(data.detailDataCopy, function (ind, dcData) {
                // Modified cd_haigo
                var mappings = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getReplacedHaigo(dcData.cd_haigo);
                if (!mappings) {
                    return;
                }
                var dData = $.extend({}, dcData);
                $.each(mappings, function (ind, mapping) {
                    if (!mapping) {
                        return;
                    }
                    // Retrive toroku info
                    //var dbData = $.grep(data.detailData, function (item) {
                    //    return (item.cd_haigo == mapping.cd_haigo_saki && item.no_seq_sakusei_ho == dData.no_seq_sakusei_ho);
                    //});
                    //dbData = dbData.length > 0 ? dbData[0] : {};
                    dData.cd_toroku = null;
                    dData.dt_toroku = null;
                    // Prepair genryo data
                    dData = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.prepairGenryoData(dData, mapping.cd_haigo_saki, data.masterData.genryoData);
                    if (App.isUndef(classified["_" + dData.cd_haigo])) {
                        classified["_" + dData.cd_haigo] = [];
                    }
                    classified["_" + dData.cd_haigo].push(dData);
                })
            });

            // Auto generate detail data in new mode or copy mode
            $.each(data.koteiData, function (ind, dData) {
                if (_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.param.mode === page.options.mode.new
                    || _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.param.mode === page.options.mode.new_copy
                    || _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getReplacedHaigo(dData.cd_haigo, true)) {
                    var kbn_nyuryoku = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.cbbType.genryo.kbn_nyuryoku
                        var convertedData = {
                            no_seiho: _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.param.no_seiho,
                            cd_haigo: dData.cd_haigo,
                            no_seq_sakusei_ho: 0,
                            kbn_nyuryoku: kbn_nyuryoku,
                            cd_input_genryo: dData.cd_hin,
                            nm_input_genryo: dData.nm_hin,
                            type: _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.convertCBBType(kbn_nyuryoku)
                        }
                    convertedData = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.prepairGenryoData(convertedData, null, data.masterData.genryoData);
                    if (App.isUndef(classified["_" + convertedData.cd_haigo])) {
                        classified["_" + convertedData.cd_haigo] = [];
                    }
                    classified["_" + convertedData.cd_haigo].push(convertedData);
                }
            });

            $.each(data.masterData.genryoData, function (i, e) {
                e.cd_hin = App.common.fillString(e.cd_hin, e.su_code_standard);
            });
            $.each(data.headerData, function (ind, hData) {
                var node = {
                    header: hData,
                    detail: classified["_" + hData.cd_hin],
                    masterGenryo: $.grep(data.masterData.genryoData, function (item) {
                        return item.cd_haigo == hData.cd_hin;
                    }),
                    masterKikai: data.masterData.kikaiData,
                    masterNyuryoku: data.masterData.nyuryokuData
                };
                if (!node.detail || !node.detail.length) {
                    node.detail = [$.extend({}, _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.defaultKoteiContent)];
                }
                if (ind != 0) {
                    //var existColor = $.grep(data.detailData, function (item) {
                    //    return item.cd_input_genryo == hData.cd_hin;
                    //});
                    //if (existColor.length) {
                    //    node.header.id_color = existColor[0].id_color;
                    //} else {
                    //    if (node.header.level && node.header.level.length == 2) {
                    //    } else {
                    //        node.header.id_color = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.defaultColor[ind];
                    //    }
                    //}
                    if (node.header.level && node.header.level.length == 2) {
                    } else {
                        node.header.id_color = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.defaultColor[ind];
                    }
                }
                result.push(node);
            });
            return result;
        }
        return null;
    }

    // Convert combobox type from kbn_nyuryoku (number) to binding type (string)
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.convertCBBType = function (kbn_nyuryoku) {
        var cbbType = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.cbbType;
        switch (kbn_nyuryoku) {
            case cbbType.genryo.kbn_nyuryoku:
                return cbbType.genryo.name;
            case cbbType.kikai.kbn_nyuryoku:
                return cbbType.kikai.name;
            case cbbType.kotei.kbn_nyuryoku:
                return cbbType.kotei.name;
            case cbbType.furi_nyuryoku.kbn_nyuryoku:
                return cbbType.furi_nyuryoku.name;
            case cbbType.shikake_genryo.kbn_nyuryoku:
                return cbbType.shikake_genryo.name;
            case cbbType.shikake_kikai.kbn_nyuryoku:
                return cbbType.shikake_kikai.name;
            case cbbType.shikake_kotei.kbn_nyuryoku:
                return cbbType.shikake_kotei.name;
            case cbbType.shikake_furi_nyuryoku.kbn_nyuryoku:
                return cbbType.shikake_furi_nyuryoku.name;

        }
    };

    // Revert combobox type from string to number for save data
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.revertCBBType = function (type) {
        var cbbType = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.cbbType;
        switch (type) {
            case cbbType.genryo.name:
                return cbbType.genryo.kbn_nyuryoku;
            case cbbType.kikai.name:
                return cbbType.kikai.kbn_nyuryoku;
            case cbbType.kotei.name:
                return cbbType.kotei.kbn_nyuryoku;
            case cbbType.furi_nyuryoku.name:
                return cbbType.furi_nyuryoku.kbn_nyuryoku;
            case cbbType.shikake_genryo.name:
                return cbbType.shikake_genryo.kbn_nyuryoku;
            case cbbType.shikake_kikai.name:
                return cbbType.shikake_kikai.kbn_nyuryoku;
            case cbbType.shikake_kotei.name:
                return cbbType.shikake_kotei.kbn_nyuryoku;
            case cbbType.shikake_furi_nyuryoku.name:
                return cbbType.shikake_furi_nyuryoku.kbn_nyuryoku;

        }
    }

    // Function for [項目追加（上）] and [項目追加（下）]
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.insertCombobox = function (position, CBoptions, type) {
        var target = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element.find(".selected-kotei");

        CBoptions = CBoptions ? CBoptions : _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(target);
        type = type ? type : target.attr("data-type");
        if (target.length) {
            var content = target.closest("table"),
                existGenryo = content.find("tr.genryo:first").find(".dropdown-content div").clone(),
                newCB = CBoptions.insertCombo(target, { type: type }, position, CBoptions),
                cbbType = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.cbbType;
            if (type === cbbType.genryo.name || type === cbbType.shikake_genryo.name) {
                // Cheat here
                newCB.find(".dropdown-content div").remove();
                //existGenryo.appendTo(newCB.find(".dropdown-content"));
            }
            CBoptions.setFocus(newCB, CBoptions);
            page.setIsChangeValue();
        }
    }

    // Open dialog [色指定]
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.openIroSettingDialog = function () {
        if (_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.values.tabMode === _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.mode.edit) {
            page.dialogs._602_IroShitei_Dialog.element.modal("show");
            page.dialogs._602_IroShitei_Dialog.setReturnColor = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.setComboColor;
        }
    }

    // Set color to combobox (return color from [色指定])
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.setComboColor = function (color) {
        var target = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element.find(".selected-kotei");
        //if (!color || target.hasClass("mishiyo")) {
        if (!color) {
            color = "";
        }
        if (target.length) {
            var code = target.findP("cd_input_genryo").val();
            target.css("background-color", color);
            // Update entity
            var entity = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.data.entry(target.attr("data-key"));
            if (entity) {
                entity.id_color = color;
            }
            _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.data.update(entity);
            page.setIsChangeValue();
            //if (page.lightOrDark(color) == "light") {
            //    target.find(".combo-lb, span.caret").css("color", "");
            //} else {
            //    target.find(".combo-lb, span.caret").css("color", "white");
            //}
            //if (code) {
            //    //if (!target.hasClass("mishiyo")) {
            //    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.DefaultComboContainerHeaderConfig.setHeaderColor(Number(code), color, _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.DefaultComboContainerHeaderConfig);
            //    //}
            //    var targets = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element.find("tr[data-haigo='" + code + "']");
            //    $.each(targets, function (ind, item) {
            //        item = $(item);
            //        //if (!item.hasClass("mishiyo")) {
            //        item.css("background-color", color);
            //        var tarData = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.data.entry(item.attr("data-key"));
            //        tarData.id_color = color;
            //        //}
            //    })
            //}
        }
    }

    // Function for [項目追加（上）]
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.insertBeforeCombobox = function () {
        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.insertCombobox(2);
    }

    // Function for [項目追加（下）]
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.insertAfterCombobox = function () {
        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.insertCombobox(3);
    }

    // Control display status of btn [項目削除]
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.disableBtnDelete = function (targetKotei) {
        if (_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.values.tabMode === _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.mode.view) {
            return;
        }
        var comboCount = targetKotei.closest("table").find("tr").length;
        if (comboCount < 6) {
            //$("#del-kotei").prop("disabled", true);
            return true;
        } else {
            //$("#del-kotei").prop("disabled", false);
            return false;
        }
    }

    // Move up 1 level all selected comboboxs
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.moveUpComboboxs = function () {
        var targetContainer = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element.find(".selected-kotei").closest("table");
        if (!targetContainer.length) {
            return;
        }
        var targets = targetContainer.find("[data-prop='select']:checked");
        $.each(targets, function (ind, item) {
            _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.moveUpCombobox(($(item)).closest(".drop-content"));
        })
    }

    // Move up 1 level for the seleted 
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.moveUpCombobox = function (target) {
        var CBoptions = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(target);
        CBoptions.moveUpCombo(target, CBoptions);
    }

    // Move down 1 level all selected comboboxs
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.moveDownComboboxs = function () {
        var targetContainer = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element.find(".selected-kotei").closest("table");
        if (!targetContainer.length) {
            return;
        }
        var targets = targetContainer.find("[data-prop='select']:checked");
        for (var i = targets.length - 1; i >= 0; i--) {
            _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.moveDownCombobox(($(targets[i])).closest(".drop-content"));
        }
    }

    // Move down 1 level for the seleted 
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.moveDownCombobox = function (target) {
        var CBoptions = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(target);
        CBoptions.moveDownCombo(target, CBoptions);
    }

    // Delete all selected comboboxs
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.deleteComboboxs = function () {
        var targetContainer = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element.find(".selected-kotei").closest("table");
        if (!targetContainer.length) {
            return;
        }
        var targets = targetContainer.find("[data-prop='select']:checked");
        $.each(targets, function (ind, item) {
            _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.deleteCombobox(($(item)).closest(".drop-content"), false);
        })
    }

    // Function for [項目削除]
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.deleteCombobox = function (target, isfocus) {
        //var target = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element.find(".selected-kotei");
        if (target.length) {
            var comboCount = target.closest("table").find("tr").length,
                nextCB = target.next(),
                CBoptions = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(target);
            //if (comboCount < 6) {
            //    page.dialogs.confirmDialog.confirm({
            //        text: "配合内のすべての入力項目を削除することはできません。",
            //        hideCancel: true
            //    });
            //    return;
            //}
            if (_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.disableBtnDelete(target)) {
                var newCbbOptions = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.cbbType.genryo.name);
                var newCbbData = $.extend({}, _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.defaultKoteiContent);
                var newCbb = newCbbOptions.insertCombo(target.closest("table"), newCbbData, newCbbOptions.LAST_POS, newCbbOptions);
                nextCB = newCbb;
            }
            CBoptions.deleteCombo(target, CBoptions);
            if (isfocus && nextCB.length) {
                var nextCBoptions = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(nextCB);
                nextCBoptions.setFocus(nextCB, nextCBoptions);
            }
            //_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.disableBtnDelete(target);
            page.setIsChangeValue();
        }
    }

    // Function when change cbb type when click [入力種別]
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.changeTypeCombo = function () {
        var target = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element.find(".selected-kotei");
        if (target.length) {
            var oldType = target.attr("data-type"),
                newType = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element.find("input[name='nyuryoku']:checked").val(),
                //newKbnNyuryoku = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.revertCBBType(newType),
                OldCBoptions = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(target),
                NewCBoptions = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(newType),
                oldData = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.data.entry(target.closest("tr").attr("data-key"));
            if (oldType != newType) {
                var confirmDialog = App.async.success();
                //if (!_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.isEmptyRow(target)) {
                //    confirmDialog = page.dialogs.confirmDialog.confirm({ text: "選択されている項目の入力種別を変更しますが、よろしいですか？" });
                //}
                confirmDialog.then(function () {
                    ///////////////////////////////////////////////////////////////
                    oldData.type = newType;
                    oldData.cd_input_genryo = "";
                    oldData.nm_input_genryo = "";
                    oldData.select = "";
                    //oldData.id_color = "";
                    //oldData.kbn_nyuryoku = newKbnNyuryoku;
                    var content = target.closest("table"),
                        existGenryo = content.find("tr.genryo:first").find(".dropdown-content div").clone(),
                        newCB = OldCBoptions.changeType(target, oldData, NewCBoptions, OldCBoptions),
                        cbbType = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.cbbType;
                    if (newType === cbbType.genryo.name || newType === cbbType.shikake_genryo.name) {
                        // Cheat here
                        newCB.find(".dropdown-content div").remove();
                        existGenryo.appendTo(newCB.find(".dropdown-content"));
                    }
                    page.setIsChangeValue();
                    ///////////////////////////////////////////////////////////////
                }).fail(function () {
                    OldCBoptions.setFocusCheckBox(target);
                });
            }
        }
    }

    // Auto setup the tab width when binding data
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.autoAjustContentWidth = function () {
        var element = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element,
            headerContent = element.find(".float-content .kotei-header"),
            detailContent = element.find(".float-content .flow-detail"),
            koteiCount = headerContent.find(".kotei-table:not(.combo-tmpl)").length,
            width = (koteiCount * 410) + 600; // change table width

        headerContent.css("width", width);
        detailContent.css("width", width - 2);
    }

    // Auto resize the tab height when add or remove cbb
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.autoAjustContentHeight = function () {
        var position = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.fixBody.scrollTop();
        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.fixBody.find(".fix-detail").css("height", "");
        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.flowBody.find(".flow-detail").css("height", "");
        var fixHeight = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.fixBody.find(".fix-detail").height(),
            flowHeight = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.flowBody.find(".flow-detail").height(),
            //maxHeight = (fixHeight > flowHeight ? fixHeight : flowHeight) - 1;
            maxHeight = (fixHeight > flowHeight ? fixHeight : flowHeight) + 250;

        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.flowBody.find(".flow-detail").css("height", maxHeight);
        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.fixBody.find(".fix-detail").css("height", maxHeight);
        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.flowBody.scrollTop(position);
        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.fixBody.scrollTop(position);
    }

    //_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.autoAjustContentHeight = function () {
    //    var position = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.fixBody.scrollTop();
    //    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.fixBody.find(".fix-detail").css("height", "");
    //    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.flowBody.find(".flow-detail").css("height", "");
    //    var fixHeight = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.fixBody.prop("scrollHeight"),
    //        flowHeight = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.flowBody.prop("scrollHeight"),
    //        maxHeight = (fixHeight > flowHeight ? fixHeight : flowHeight) - 1;

    //    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.flowBody.find(".flow-detail").css("height", maxHeight);
    //    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.fixBody.find(".fix-detail").css("height", maxHeight);
    //    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.flowBody.scrollTop(position);
    //}

    // Get cbb instance, base on cbb type
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance = function (target) {
        var comboType = "";
        if (typeof (target) == "string") {
            comboType = target;
        }
        else {
            var content = target.closest("tr");
            comboType = content.attr("data-type");
        }
        return _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.comboInstance[comboType];
    }

    // Function to control event click in cbb
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.clickEvent = function (e) {
        var target = $(e.target),
            content = target.closest("tr.drop-content"),
            isAction = true,
            comboIndex = -1;
        if (content.length) {
            var CBoptions = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(content);
            isAction = CBoptions.actionCombobox(target, CBoptions);
            comboIndex = content.attr("index");
        }
        if (_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.CurrentSelected && (isAction || comboIndex !== _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.CurrentSelected.attr("index"))) {
            var CBoptions = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.CurrentSelected);
            CBoptions.deActiveCombobox(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.CurrentSelected, CBoptions);
            //_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.autoAjustContentHeight();
        }
        if (content.length) {
            var CBoptions = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(content);
            CBoptions.setFocus(content, CBoptions);
            //_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.autoAjustContentHeight();
            if (target.closest(".select-cell").length == 0) {
                e.preventDefault();
                return false;
            }
        }
    }

    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.keyDown = function (e) {
        var target = $(e.target),
            CBoptions = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(target);
        // Lost focus when key up is 'Enter'
        if (e.which == 13) {
            CBoptions.deActiveCombobox(target, CBoptions);
        }
    }

    // Auto search when typing in cbb
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.searchComboData = function (e) {
        var target = $(e.target),
            CBoptions = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(target);

        CBoptions.keyUp(target, CBoptions);
    }

    // Check if the cbb in no data.
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.isEmptyRow = function (target) {
        var type = target.attr("data-type"),
            cbbType = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.cbbType;

        if ($.trim(target.findP("nm_free_mark").val()) != "") {
            return false;
        }
        switch (type) {
            case cbbType.genryo.name:
            case cbbType.shikake_genryo.name:
                return ($.trim(target.findP("cd_input_genryo").val()) == "");
                break;
            default:
                return $.trim(target.find("input[data-prop='nm_input_genryo']").val()) == "";
                break;
        }
    };

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.setColInvalidStyle = function (target) {
        var tar = $(target);
        if (tar && tar.length) {
            tar.closest("tr").addClass("has-error");
        }
    };

    /**
     * 単項目要素をエラー無しのスタイルに設定します。
     * @param target 設定する要素
     */
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.setColValidStyle = function (target) {
        var tar = $(target);
        if (tar && tar.length) {
            tar.closest("tr").removeClass("has-error");
        }
    };

    /**
     * 検索ダイアログ非表示時処理を実行します。
     */
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.hidden = function (e) {
        var element = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element,
            table = element.find(".search-list");

        //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
        element.find(":input").val("");
        //TODO: 複数セレクトの場合は、下の１行をコメント解除します。
        //element.find(":checked").prop("checked", false);
        table.find("tbody").not(".item-tmpl").remove();

        element.findP("data_count").text("");
        element.findP("data_count_total").text("");

        var items = element.find(".modal-body :input:not(button)");
        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.setColValidStyle(item);
        }

        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.notifyInfo.clear();
        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.notifyAlert.clear();
    };

    /**
     * 検索ダイアログ表示時処理を実行します。
     */
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.shown = function (e) {

        //_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element.find(":input:not(button):first").focus();
    };

    /**
     * 検索ダイアログ　バリデーションルールを定義します。
     */
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.validations = {
        nm_genryo_validate: {
            rules: {
                required_custom: function (value, param, otps, done) {
                    var row = (otps || {}).row;
                    if (row) {
                        if (App.isUndefOrNullOrStrEmpty(row.findP("nm_free_mark").val())) {
                            return done(true);
                        }
                        return done(!App.isUndefOrNullOrStrEmpty(value));
                    }
                    done(true);
                },
                maxbytelength: 100
            },
            options: {
                name: "入力原料名",
                byte: 50
            },
            messages: {
                required_custom: App.messages.base.required,
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_free_mark: {
            rules: {
                maxbytelength: 2
            },
            options: {
                name: "フリー項目マーク",
                byte: 1
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        }
    };

    /**
     * 検索ダイアログの検索処理を実行します。
     */
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.search = function () {
        ///// Search function move to .initilize function 
    };

    // Change func
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.change = function (e) {
        var target = $(e.target),
            property = target.attr("data-prop"),
            tbody = target.closest("tr"),
            validateTarget = target;
        if (!tbody.length) {
            return;
        }
        var id = tbody.attr("data-key"),
            entity = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.data.entry(id);

        if (property === "nm_input_genryo") {
            var validate = tbody.findP("nm_genryo_validate");
            validate.val(target.val());
            validateTarget = validate;
        }

        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.validator.validate({
            targets: validateTarget,
            filter: page.options.changeValidationFilter
        }).then(function () {
            page.setIsChangeValue();
            if (entity) {
                entity[property] = target.val();
                cbbType = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.cbbType;
                if (property === "cd_input_genryo") {
                    entity.nm_input_genryo = tbody.findP("nm_input_genryo").text();
                    entity.flg_copy_genryo = tbody.hasClass("mishiyo") ? 1 : 0;
                }
                //if (entity.kbn_nyuryoku === cbbType.genryo.kbn_nyuryoku || entity.kbn_nyuryoku === cbbType.furi_nyuryoku.kbn_nyuryoku) {
                //    color = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element.find(".kotei-header [data-code='" + entity.cd_input_genryo + "']").attr("data-color");
                //    entity.color = color;
                //    target.closest(".drop-content").css("background-color", color || "");
                //}
            }
            _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.validator.validate({
                targets: tbody.find("[data-prop='nm_genryo_validate'], [data-prop='nm_free_mark']"),
                filter: page.options.changeValidationFilter
            })
        });
    }

    /**
     * 検索ダイアログの一覧にデータをバインドします。
     */
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.bind = function (searchData) {

        var element = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element;
        var ComboContainer = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.DefaultComboContainerConfig;
        var ComboHeaderContainer = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.DefaultComboContainerHeaderConfig;
        var fixDetailContent = element.find(".fix-detail");
        var fixHeaderContent = element.find(".fix-header");
        var flowHeaderContent = element.find(".flow-header .kotei-header");
        var flowDetailContent = element.find(".flow-wrapper .flow-detail");

        //searchData = null;
        if (searchData && searchData.length > 0) {
            _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element.find(".no-data-found").hide();
            $.each(searchData, function (ind, item) {
                if (!ind) {
                    ComboContainer.addFixContent(item, ComboContainer).attr("data-hin-code", item.header.cd_hin);
                    ComboHeaderContainer.addFixContent(item.header, ComboHeaderContainer);
                }
                else {
                    ComboContainer.addFlowContent(item, ComboContainer).attr("data-hin-code", item.header.cd_hin);
                    ComboHeaderContainer.addFlowContent(item.header, ComboHeaderContainer);
                }
            });
        } else {
            _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element.find(".no-data-found").show();
            // Disable all things
            element.find(".command-row").find("button, input[type='radio']").prop("disabled", true);
            element.findP("nm_free_mark").prop("readonly", true);
            // View mode 
            _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.values.tabMode = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.options.mode.view;
        }


        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.autoAjustContentHeight();
        _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.autoAjustContentWidth();

        var firstKotei = element.find("tr.drop-content:visible:first");
        if (firstKotei.length) {
            var firstIns = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getInstance(firstKotei);
            firstIns.setFocus(firstKotei, firstIns);
        }
        page.finishLoading("TAB_Bind", "TAB5", 5);
    };

    /**
     * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
     */
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.select = function (e) {
        var element = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element,
            target = $(e.target),
            id = target.closest("tbody").attr("data-key"),
            data;

        if (App.isUndef(id)) {
            return;
        }

        data = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.data.entry(id);

        if (App.isFunc(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.dataSelected)) {
            if (!_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.dataSelected(data)) {
                element.modal("hide");
            }
        } else {
            element.modal("hide");
        }
    };

    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getIndex = function () {
        var element = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element,
            haigoCols = element.find(".flow-wrapper .kotei-table:not(.combo-tmpl), .fix-wrapper .kotei-table:not(.combo-tmpl)"),
            dataSet = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.data,
            changeSets = {
                created: [],
                updated: [],
                deleted: _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.data.findAll(function (item, entity) {
                    return entity.state === App.ui.page.dataSet.status.Deleted;
                })
            };
        // Attach base info
        $.each(haigoCols, function (ind, haigo) {
            var koteis = $(haigo),
                cd_haigo = koteis.attr("data-hin-code"),
                kotei = koteis.find("tr:not(.combo-tmpl)"),
                index = 0;
            $.each(kotei, function (ind, item) {
                if (!_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.isEmptyRow($(item))) {
                    var id = $(item).attr("data-key");
                    if (id) {
                        entity = dataSet.entry(id);
                        if (entity) {
                            index++;
                            entity.no_seq_sakusei_ho = index;
                            entity.cd_haigo = cd_haigo;
                            entity.no_seiho = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.param.no_seiho;
                            entity.kbn_nyuryoku = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.revertCBBType($(item).attr("data-type"));
                            // Replace NULL value
                            entity.nm_input_genryo = entity.nm_input_genryo || null;
                            entity.id_color = entity.id_color || null;

                            changeSets.created.push(entity);
                        }
                    }
                }
            });

        });
        // Get changeset
        return changeSets
    }

    // Check all validate in the tab
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.validateAll = function (filter) {
        var targets = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.element.find(".flow-wrapper .kotei-table:not(.combo-tmpl) tr:not(.combo-tmpl), .fix-wrapper .kotei-table:not(.combo-tmpl) tr:not(.combo-tmpl)"),
            validateList = [];
        $.each(targets, function (ind, row) {
            validateList.push(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.validator.validate({
                targets: $(row).find(":input"),
                filter: filter,
                state: {
                    row: $(row),
                    suppressMessage: false
                }
            }))
        })
        return App.async.all(validateList).then(function (result) {
            // If page mode not in APPLY or not [shonin] yet
            if (_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.param.sub_mode !== page.options.mode.apply
                //Get shonin level in TAB 1
                || _300_SeihoBunshoSakusei_Hyoshi_Tab.getShoninLevel() === 0) {
                return App.async.success(result);
            }
            // Skip next validations in view mode
            if (_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.param.mode === page.options.mode.view) {
                return App.async.success(result);
            }
            var searchData = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.searchData;
            //①作成担当＿申請の場合
            if (searchData) {
                var lstHaigoHaishi = [],
                    lstGenryoHaishi = [];

                $.each(searchData, function (ind, haigoData) {
                    //・配合コード、仕掛品コードに対して、配合ヘッダマスタ．未使用フラグ＝3.15区分／コード一覧\定数/セッティング用\未使用フラグの場合、
                    //又は配合ヘッダマスタma_haigo_header．廃止区分kbn_haishi＝3.15区分／コード一覧\定数/セッティング用\廃止区分\廃止の場合
                    if (haigoData && haigoData.header.flg_mishiyo == App.settings.app.flg_mishiyo.mishiyo) {
                        lstHaigoHaishi.push(App.common.fillString(haigoData.header.cd_hin, page.values.su_code_standard));
                    }
                });

                //・原料情報に対して、配合明細マスタに
                //存在しない原料の場合、又は原料が廃止された場合
                _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.data.findAll(function (item, entity) {
                    if (entity.state !== App.ui.page.dataSet.status.Added) {
                        return false;
                    }
                    if (item.flg_copy_genryo) {
                        lstGenryoHaishi.push(App.common.fillString(item.cd_input_genryo, page.values.su_code_standard));
                    }
                    return false;
                });

                var isError = false;
                //ALERTメッセージ（AP0098）を表示する
                //引数：本配合コード又は仕掛品コード（「、」で区切り）
                if (lstHaigoHaishi.length > 0) {
                    var mess = App.str.format(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.name + App.messages.app.AP0098, lstHaigoHaishi.join("、"));
                    App.ui.page.notifyAlert.message(mess, "++++_300_SeihoBunshoSakusei_SeizoKoteizu_Tab").show();
                    isError = true;
                }
                //ALERTメッセージ（AP0099）を表示する
                //引数：原料コード（「、」で区切り）
                if (lstGenryoHaishi.length > 0) {
                    var mess = App.str.format(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.name + App.messages.app.AP0099, lstGenryoHaishi.join("、"));
                    App.ui.page.notifyAlert.message(mess, "+++_300_SeihoBunshoSakusei_SeizoKoteizu_Tab").show();
                    isError = true;
                }
                var detailList = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getIndex();
                if (searchData.length === 0 || (!detailList || detailList.created.length === 0)) {
                    if (_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.risingEmptyError()) {
                        isError = true;
                    }
                }
                if (isError) {
                    return App.async.fail();
                }
            } else {
                if (_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.risingEmptyError()) {
                    return App.async.fail();
                };
            }
            return App.async.success(result);
        });
    }

    //// Rising required error when hon_haigo not found or koteizu list is empty
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.risingEmptyError = function () {
        // Skip required when seiho classed 19
        if (page.getSeihoShubetsu() == App.settings.app.seiho_shubetsu.hyoji) {
            return false;
        }
        // Skip required in view mode or un skiped validations
        if (_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.param.mode != "view" && !page.isSkipApplyValidation()) {
            App.ui.page.notifyAlert.message(App.str.format(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.name + App.messages.base.required, { name: "製造工程図の情報" }), "+++++_300_SeihoBunshoSakusei_SeizoKoteizu_Tab").show();
            return true;
        }
        return false;
    }

    // Uncheck all check box of diff current kotei
    _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.UncheckAll = function (target, isRecal) {
        if (!isRecal) {
            target = $(target.target);
        }
        var hinCode = target.closest('table').attr('data-hin-code'),
            curHinCode = null,
            unCheckAll = function () {
                _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.values.focusedKotei.findP('select').prop('checked', false);
            };
        if (_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.values.focusedKotei) {
            curHinCode = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.values.focusedKotei.attr('data-hin-code');
        }
        // In-side click => Nothing unchecked
        if (hinCode && hinCode == curHinCode) {
            return;
        }
        // Out-side click => uncheck all current kotei
        if (!hinCode && _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.values.focusedKotei) {
            unCheckAll();
            _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.values.focusedKotei = null;
        }
        // In-side diff kotei
        if (hinCode && hinCode != curHinCode && _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.values.focusedKotei) {
            unCheckAll();
        }
        // Update current kotei
        if (hinCode && hinCode != curHinCode) {
            _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.values.focusedKotei = target.closest('table');
        }
    }
    //_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.save = function () {
    //    App.ui.page.notifyWarn.clear();
    //    App.ui.page.notifyAlert.clear();
    //    App.ui.page.notifyInfo.clear();

    //    App.ui.loading.show();

    //    var sleep = 0;
    //    var condition = "Object.keys(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.values.isChangeRunning).length == 0";
    //    App.ui.wait(sleep, condition, 100)
    //        .then(function () {
    //            var changeSets = _300_SeihoBunshoSakusei_SeizoKoteizu_Tab.getIndex();
    //            return $.ajax(App.ajax.webapi.post(_300_SeihoBunshoSakusei_SeizoKoteizu_Tab.urls.saveData, changeSets))
    //                  .then(function (result) {
    //                      App.ui.page.notifyInfo.message(App.messages.app.AP0007).show();
    //                  }).fail(function (error) {
    //                      if (error.status === App.settings.base.conflictStatus) {
    //                          // TODO: 同時実行エラー時の処理を行っています。
    //                          // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
    //                          App.ui.page.notifyAlert.clear();
    //                          App.ui.page.notifyAlert.message(App.messages.base.MS0009).show();
    //                          return;
    //                      }
    //                      //TODO: データの保存失敗時の処理をここに記述します。
    //                      App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
    //                  });
    //        }).always(function () {
    //            App.ui.loading.close();
    //        });
    //};
</script>

<div class="tab-pane" tabindex="-1" id="_300_SeihoBunshoSakusei_SeizoKoteizu_Tab">
    <div class="sub-tab-content">
        <div class="part roof" style="margin-bottom: 0px; margin-right: 0px;">
            <div class="part-content">
                <div class="row command-row">
                    <div class="control-tab col-xs-20">
                        <button type="button" class="btn btn-primary btn-xs" id="ins-bef">項目追加（上）</button>
                        <button type="button" class="btn btn-primary btn-xs" id="ins-aft">項目追加（下）</button>
                        <button type="button" class="btn btn-primary btn-xs" id="del-kotei">[✓]項目削除</button>
                        <%--<button type="button" class="btn btn-primary btn-sm width-lg" id="clear-all" style="margin-left: 8px">全クリア</button>--%>
                        <button type="button" class="btn btn-primary btn-xs" id="move-up">[✓]移動（上）</button>
                        <button type="button" class="btn btn-primary btn-xs" id="move-down">[✓]移動（下）</button>
                        <label style="margin-left: 15px">入力種別：</label>
                        <label class="check-group">
                            <input type="radio" name="nyuryoku" value="genryo" checked>原料</label>
                        <label class="check-group">
                            <input type="radio" name="nyuryoku" value="kikai">機械</label>
                        <label class="check-group">
                            <input type="radio" name="nyuryoku" value="kotei">工程</label>
                        <label class="check-group">
                            <input type="radio" name="nyuryoku" value="furi_nyuryoku">フリー入力</label>
                        <label class="check-group">
                            <input type="radio" name="nyuryoku" value="shikake_genryo">前処理原料</label>
                        <label class="check-group">
                            <input type="radio" name="nyuryoku" value="shikake_kikai">前処理機械</label>
                        <label class="check-group">
                            <input type="radio" name="nyuryoku" value="shikake_kotei">前処理工程</label>
                        <label class="check-group">
                            <input type="radio" name="nyuryoku" value="shikake_furi_nyuryoku">前処理フリー入力</label>
                    </div>
                </div>

                <!-- table detail-->
                <div class="row">
                    <div class="detail-content" style="position: relative; overflow-x: hidden; overflow-y: hidden">
                        <div class="fix-content">
                            <div class="fix-header" style="height: 70px">
                                <div class="kotei-header">
                                    <table class="kotei-table combo-tmpl">
                                        <tr>
                                            <th>
                                                <label class="code" data-prop="cd_hin">
                                                </label>
                                                <label class="name ellipsis-line-2" data-prop="nm_hin">
                                                </label>
                                            </th>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                            <div class="fix-wrapper fix-auto-height" style="z-index: 500">
                                <div class="fix-detail">
                                    <table class="kotei-table combo-tmpl">
                                        <tr class="drop-content combo-tmpl other">
                                            <td class="first center">
                                                 <label class="select-cell">                                                    
                                                    <input type="checkbox" data-prop="select" class="combo-lb" />
                                                </label>
                                            </td>
                                            <td class="first center">
                                                <input type="text" data-prop="nm_free_mark" class="mark-cell combo-lb" maxlength="2" />
                                            </td>
                                            <td class="side center arrow">
                                                <label data-prop="arrow" class="min-zero dis-label combo-lb"></label>
                                                <input type="text" class="validate-material" data-prop="nm_genryo_validate" style="float: left; width: 0; height: 0; border: 0" tabindex="-1" />
                                            </td>
                                            <td class="swicth lb side" colspan="2">
                                                <label class="ellipsis-line-2 combo-lb dis-label" data-prop="nm_input_genryo"></label>
                                            </td>
                                            <td class="swicth inp side" colspan="2" style="display: none">
                                                <div class="dropdown full-width" data-prop="nm_haigo">
                                                    <div class="dropdown-front">
                                                        <input type="text" placeholder="" class="dropbtn com-inp" data-prop="nm_input_genryo" maxlength="100" />
                                                    </div>
                                                    <div class="dropdown-content">
                                                        <div class="combo-tmpl" tabindex="0">
                                                            <label data-prop="nm_hin_opt" class="opt-name" style="width: 100%"></label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                            <td class="last">
                                                <button type="button" style="border: 0; background-color: transparent; min-width: 100%; padding: 10px 0px;">
                                                    <span class="caret"></span>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr class="drop-content combo-tmpl kotei">
                                            <td class="first center">
                                                 <label class="select-cell">                                                    
                                                    <input type="checkbox" data-prop="select" class="combo-lb" />
                                                </label>
                                            </td>
                                            <td class="first center">
                                                <input type="text" data-prop="nm_free_mark" class="mark-cell combo-lb" maxlength="2" />
                                            </td>
                                            <td class="side center arrow">
                                                <label data-prop="arrow" class="min-zero dis-label combo-lb"></label>
                                                <input type="text" class="validate-material" data-prop="nm_genryo_validate" style="float: left; width: 0; height: 0; border: 0" tabindex="-1" />
                                            </td>
                                            <td class="swicth lb side" colspan="2">
                                                <label class="ellipsis-line-2 combo-lb dis-label" data-prop="nm_input_genryo"></label>
                                            </td>
                                            <td class="swicth inp side" colspan="2" style="display: none">
                                                <div class="dropdown full-width" data-prop="nm_haigo">
                                                    <div class="dropdown-front">
                                                        <input type="text" placeholder="" class="dropbtn com-inp" data-prop="nm_input_genryo" maxlength="100" />
                                                    </div>
                                                    <div class="dropdown-content">
                                                        <div class="combo-tmpl" tabindex="0">
                                                            <label data-prop="nm_hin_opt" class="opt-name" style="width: 100%"></label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                            <td class="last">
                                                <button type="button" style="border: 0; background-color: transparent; min-width: 100%; padding: 10px 0px;">
                                                    <span class="caret"></span>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr class="drop-content combo-tmpl genryo">
                                            <td class="first center">
                                                <label class="select-cell">                                                    
                                                    <input type="checkbox" data-prop="select" class="combo-lb" />
                                                </label>
                                            </td>
                                            <td class="first center">
                                                <input type="text" data-prop="nm_free_mark" class="mark-cell combo-lb" maxlength="2" />
                                            </td>
                                            <td class="side center arrow">
                                                <label data-prop="arrow" class="min-zero dis-label combo-lb"></label>
                                                <input type="text" class="validate-material" data-prop="nm_genryo_troll" style="float: left; width: 0; height: 0; border: 0" tabindex="-1" />
                                                <input type="text" class="validate-material" data-prop="nm_genryo_validate" style="float: left; width: 0; height: 0; border: 0" tabindex="-1" />
                                            </td>
                                            <td class="swicth lb side">
                                                <div class="dropdown full-width" data-prop="nm_haigo">
                                                    <input type="text" data-prop="cd_input_genryo" class="min-zero genryo-code combo-lb" disabled />
                                                    <div class="dropdown-content">
                                                        <div class="combo-tmpl" tabindex="-1">
                                                            <label data-prop="cd_hin_opt" class="center opt-code overflow-ellipsis" style="width: 65px; min-width: 0px;"></label>
                                                            <label data-prop="nm_hin_opt" class="opt-name overflow-ellipsis" style="width: calc(100% - 65px);"></label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                            <td class="swicth lb side">
                                                <label class="ellipsis-line-2 combo-lb genryo-name" data-prop="nm_input_genryo"></label>
                                            </td>
                                            <td class="last">
                                                <button type="button" style="border: 0; background-color: transparent; min-width: 100%; padding: 10px 0px;">
                                                    <span class="caret"></span>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr class="tr-frame">
                                            <td style="width: 20px"></td>
                                            <td style="width: 24px"></td>
                                            <td style="width: 30px"></td>
                                            <td style="width: 62px"></td>
                                            <td></td>
                                            <td style="width: 17px"></td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="float-content" style="width: 100%; overflow: auto">
                            <div class="flow-header" style="width: calc(100% - 17px); overflow: hidden; height: 70px">
                                <div class="kotei-header" style="padding-left: 600px; float: left; width: 2200px">
                                </div>
                            </div>
                            <div class="flow-wrapper flow-auto-height" style="overflow: auto; overflow-y: scroll; width: calc(100%); border: 1px #ccc solid;">
                                <div class="flow-detail" style="width: 2200px; float: left; padding-left: 600px;">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="no-data-found">
        本配合のデータが未登録です。
    </div>
</div>
