<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="300_SeihoBunshoSakusei_SeihinKikakuan_Tab.aspx.cs" Inherits="Tos.Web.Pages._300_SeihoBunshoSakusei_SeihinKikakuan_Tab" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>

<style type="text/css">
    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab .part-content .col-xs-12 {
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab .row-table {
        height: 128px;
        background-color: #f6f6f6;
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab .row-table-parent {
        height: 720px!important;
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab .row-area-parent {
        height: 246px !important;
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab .row-select-parent {
        height: 110px !important;
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab .row-area {
        height: 146px !important;
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab select {
        height: 22px !important;
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab table.datatable select {
        background-color: transparent;
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab table.datatable td input[type='text'] {
        border: none;
        background-color: transparent;
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab table.datatable th input[type='text'] {
        border: none;
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab textarea {
        width: 100%;
        resize: none;
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab .row-padding {
        padding-left: 20px !important;
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab .row-padding-tab {
        padding-left: 30px !important;
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab table.datatable tbody td, th {
        padding: 0;
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab .text-content {
        height: 360px;
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab .select2-container {
        width: 100%!important;
        background-color: transparent;
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab .select2-container .select2-selection--single {
        height: 22px;
        border: none;
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab .select2-container--default .select2-selection--single .select2-selection__arrow {
        height: 18px;
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab .select2-container--default .select2-selection--single {
        background-color: transparent;
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab th.has-error input {
        background-color: transparent;
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab .selectize-input {
        background-color: transparent!important;        
        border: none;
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab th.has-error,
    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab td.has-error,
    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab div.sokutei-hoho input.error,
    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab div.shomi-kigen .error,
    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab div.naiyoryo .error {
        background-color: #ffdab9!important;
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab div.shomi-kigen option,
    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab div.naiyoryo option {
        background-color: white!important;
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab th input,
    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab td input {
        width: 100%
    }

    #_300_SeihoBunshoSakusei_SeihinKikakuan_Tab .group-control * {
        float: left;
    }

</style>
<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var _300_SeihoBunshoSakusei_SeihinKikakuan_Tab = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.dialogDataTakeCount,   // TODO:取得するデータ数を指定します。
            freeCode: 99
        },
        param: {
            no_seiho: "0001-A01-19-0068",
            mode: "edit"
        },
        urls: {
            ma_literal: "../Services/ShisaQuickService.svc/ma_literal?$filter=cd_category eq '{0}'&$orderby=no_sort&$select=cd_literal, nm_literal",
            search: "../api/_300_SeihoBunshoSakusei_SeihinKikakuan_Tab",
            getCopyData: "../api/_300_SeihoBunshoSakusei_SeihinKikakuan_Tab/getCopyData",
            getseihintokusechi: "../Services/ShisaQuickService.svc/tr_shisaku?$filter=cd_shain eq {0}M and nen eq {1} and no_oi eq {2} and seq_shisaku eq {3}&$select={4}",
            getPtKotei: "../Services/ShisaQuickService.svc/tr_shisakuhin?$filter=cd_shain eq {cd_shain}M and nen eq {nen} and no_oi eq {no_oi}&$select=pt_kotei"
        },
        name: "【製品規格案及び取扱基準】",
        isFirstLoading: true,
        isChange: true,
        propertyMapping: [{
            key: "1",
            property: "ritu_sousan",
            "class": "ronrichi"
        }, {
            key: "2",
            property: "ritu_shokuen",
            "class": "ronrichi"
        }, {
            key: "3",
            property: "sando_suiso",
            "class": "ronrichi"
        }, {
            key: "4",
            property: "shokuen_suiso",
            "class": "ronrichi"
        }, {
            key: "5",
            property: "msg_suiso",
            "class": "ronrichi"
        }, {
            key: "6",
            property: "rank_biseibutsu",
            "class": "ronrichi"
        }, {
            key: "7",
            property: "haigo_kyodo",
            "class": "ronrichi",
            url: "../api/_300_SeihoBunshoSakusei_SeihinKikakuan_Tab/getHaigoKyodo?cd_shain={cd_shain}&nen={nen}&no_oi={no_oi}&seq_shisaku={seq_shisaku}&cd_category=" + App.settings.app.cd_category.kbn_haigo_kyodo
        }, {
            key: "8",
            // Bug #16426
            //property: "sando_suiso",
            property: "sakusan_suiso",
            // End
            "class": "ronrichi"
        }, {
            key: "9",
            property: "jikkoSakusanNodo",
            "class": "ronrichi"
        }, {
            key: "10",
            property: "jikkoHikairiSakusanSando",
            "class": "ronrichi"
        }, {
            key: "11",
            property: "oilmustard",
            "class": "ronrichi"
        }, {
            key: "12",
            property: "ritu_sousan_bunseki",
            "class": "kenshisakuhin"
        }, {
            key: "13",
            property: "ritu_shokuen_bunseki",
            "class": "kenshisakuhin"
        }, {
            key: "14",
            property: "Brix",
            "class": "kenshisakuhin"
        }, {
            key: "15",
            property: "ph",
            "class": "kenshisakuhin"
        }, {
            key: "16",
            property: "nendo",
            "class": "kenshisakuhin"
        }, {
            key: "17",
            property: "ondo",
            "class": "kenshisakuhin"
        }, {
            key: "18",
            property: "no_rotor",
            "class": "kenshisakuhin"
        }, {
            key: "19",
            property: "speed",
            "class": "kenshisakuhin"
        }, {
            key: "20",
            property: "nendo_cream",
            "class": "kenshisakuhin"
        }, {
            key: "21",
            property: "ondo_cream",
            "class": "kenshisakuhin"
        }, {
            key: "22",
            property: "no_rotor_cream",
            "class": "kenshisakuhin"
        }, {
            key: "23",
            property: "speed_cream",
            "class": "kenshisakuhin"
        }, {
            key: "24",
            property: "AW",
            "class": "kenshisakuhin"
        }, {
            key: "25",
            property: "hiju",
            "class": "kenshisakuhin"
        }, {
            key: "26",
            property: "hiju_sui",
            "class": "kenshisakuhin"
        }, {
            key: "27",
            property: "ritu_sando_bunseki_suiso",
            "class": "kenshisakuhin"
        }, {
            key: "28",
            property: "ritu_shokuen_bunseki_suiso",
            "class": "kenshisakuhin"
        }]
    };

    /**
     * 検索ダイアログの初期化処理を行います。
     */
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.initialize = function () {
        var element = $("#_300_SeihoBunshoSakusei_SeihinKikakuan_Tab");

        //element.on("hidden.bs.modal", _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.hidden);
        element.on("shown.bs.modal", _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.shown);
        element.on("click", ".search", _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.search);
        element.on("change", ":input", _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.change);
        //TODO: 単一セレクトの場合は、下の１行を使用します
        element.on("click", ".search-list tbody ", _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.select);
        element.on("click", "#open-hinmei-sentaku", _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.openHinmeiSentaku);
        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element = element;

        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#_300_SeihoBunshoSakusei_SeihinKikakuan_Tab .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#_300_SeihoBunshoSakusei_SeihinKikakuan_Tab .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body",
                show: function () {
                    element.find(".alert-message").show();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                }
            });

        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.validator = element.validation(App.validation(_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.options.validations, {
            success: function (results, state) {
                var i = 0, l = results.length,
                item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    if ($(item.element).closest("table").length > 0) {
                        $(item.element).closest("td, th").removeClass("has-error");
                    } else if ($(item.element).closest(".shomi-kigen").length || $(item.element).closest(".naiyoryo").length) {
                        //$(item.element).removeClass("has-error");
                    } else {
                        if (item.item == "su_nendo_sokuteichi" || item.item == "dt_nendo_sokuteichi") {
                            //_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.setColValidStyle(item.element.closest("div.control"));
                        } else {
                            _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.setColValidStyle(item.element);
                        }
                    }

                    App.ui.page.notifyAlert.remove(item.element);
                }
            },
            fail: function (results, state) {
                var i = 0, l = results.length,
                 item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    if ($(item.element).closest("table").length > 0) {
                        $(item.element).closest("td, th").addClass("has-error");
                    } else if ($(item.element).closest(".shomi-kigen").length || $(item.element).closest(".naiyoryo").length) {
                        //$(item.element).addClass("has-error");
                    }
                    else {
                        if (item.item == "su_nendo_sokuteichi" || item.item == "dt_nendo_sokuteichi") {
                            //_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.setColInvalidStyle(item.element.closest("div.control"));
                        } else {
                            _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.setColInvalidStyle(item.element);
                        }
                    }

                    if (state && state.suppressMessage) {
                        continue;
                    }
                    App.ui.page.notifyAlert.message(_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.name + item.message, item.element).show();
                }
            },
            always: function (results) {
                //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
            }
        }));
        $(".inp-lb").prop("tabindex", -1);
        // Mark as finish loading
        page.finishLoading("TAB_Initilize", "TAB6", 5);
        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.contructDetail();
        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.detailElement = element.find(".search-list tbody tr:not(.item-tmpl)");
        // Load master Data
        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.loadMasterData().then(function () {
            // Load tab data
            _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.search();
        })
    };



    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.contructDetail = function () {
        var tbody = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element.find("table.datatable tbody"),
            tempRow = tbody.find(".item-tmpl");
        for (var i = 1; i < 31; i++) {
            var row = tempRow.clone();
            row.findP("no_seq_seihintokusechi").text(i);
            row.findP("nm_seihintokusechi_combo_input").attr("id", "kbn_seihintokuseichi_" + i);
            row.removeClass("item-tmpl").appendTo(tbody).show();
        }
        // Disabled filter when type in datalist
        //_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element.on("input", "input[list]", function (e) {
        //    var target = $(e.target);
        //    var data = target.data("datalist");
        //    var dataList = target.closest(".datalist-content").find("datalist");
        //    dataList.empty();
        //    $.each(data, function (ind, item) {
        //        dataList.append(App.str.format("<option value='{0}' data-value='{1}'></option>", item.text, item.value));
        //    });
        //});
    }

    /**
    * 画面ヘッダーの変更時処理を定義します。
    */
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.change = function (e) {
        var target = $(e.target),
            isGrid = target.closest("tbody").length;

        //if (target.hasClass("number-only")) {
        //    target.val(App.data.getCommaNumberString(target.val()))
        //}
        if (!_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.isFirstLoading) {
            _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.isChange = true;
        }
        if (isGrid) {
            _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.changeDetail(target);
        } else {
            // Let the focus out event run first
            setTimeout(function () {
                _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.changeHeader(target);
            }, 1);
        }
    }

    /// Change detail data
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.changeDetail = function (target) {
        var tbody = target.closest("tr"),
            id = tbody.attr("data-key"),
            entity = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.detailData.entry(id),
            data = tbody.form().data(),
            property = target.attr("data-prop");

        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.validator.validate({
            targets: target,
            filter: page.options.changeValidationFilter,
            state: {
                isGridValidation: true
            }
        }).then(function () {
            page.setIsChangeValue();
            if (property === "nm_seihintokusechi_combo_input") {
                // Refresh [項目] data
                var kbn_seihintokuseichi = page.getValueFromList(target);
                var refreshToken = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.getRefreshToken(target, kbn_seihintokuseichi);
                if (refreshToken || kbn_seihintokuseichi === "0") {
                    if (refreshToken) {
                        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.refreshseihintokusechi(target, refreshToken);
                    }
                    entity.kbn_seihintokuseichi = kbn_seihintokuseichi
                    entity.nm_seihintokusechi_combo_input = target.val();
                    if (kbn_seihintokuseichi === "0") {
                        // Clear [理論値], [研試作品]
                        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.setseihintokusechi(tbody, entity, "", "");
                    }
                } else {
                    var freeInput = $.trim(target.val());
                    if (freeInput) {
                        entity.kbn_seihintokuseichi = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.options.freeCode;
                    } else {
                        entity.kbn_seihintokuseichi = null;
                    }
                    entity.nm_seihintokusechi_combo_input = freeInput;
                    // Clear [理論値], [研試作品]
                    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.setseihintokusechi(tbody, entity, "", "");
                }
                target.attr("data-value", kbn_seihintokuseichi);
            } else {
                entity[property] = data[property];
            }
            _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.detailData.update(entity);

            _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.validator.validate({
                targets: tbody.find(":input"),
                state: {
                    isGridValidation: true,
                    checkAll: true
                }
            })
        })
    };

    // Convert string to number
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.convertToNumber = function (value) {
        if (App.isUndefOrNullOrStrEmpty(value)) {
            return null;
        }
        var converted = Number(value);
        if (!isNaN(converted)) {
            return converted;
        }
        return null;
    }

    // Process when change shisaku in header
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.changeShisaku = function () {
        var id = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element.attr("data-key"),
            entity = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.headerData.entry(id);

        // Reload all [項目] data
        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.refreshAllseihintokusechi();
        // Reload pt_kotei
        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.reloadPtKotei(entity);
        // Trigger display TAB 6 [測定方法]
        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.processShokuteiInfo();
        // Reload su_naiyoryo, shomikikan
        entity.su_naiyoryo = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.convertToNumber(_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.param.yoryo);
        entity.cd_naiyoryo_tani = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.param.cd_naiyoryo_tani;
        entity.su_shomikikan_free_input = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.param.shomikikan;
        entity.kbn_shomikikan_tani = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.param.shomikikan_tani;
        entity.kbn_toriatsukai_ondo = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.param.cd_ondo;
        // Update display
        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element.findP("su_naiyoryo").val(App.num.format(entity.su_naiyoryo, "#,#.##########"));
        //_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element.findP("nm_naiyoryo_tani").text(_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.param.nm_naiyoryo_tani);
        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element.findP("cd_naiyoryo_tani").val(_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.param.cd_naiyoryo_tani);
        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element.findP("su_shomikikan_free_input").val(App.data.getCommaNumberString(entity.su_shomikikan_free_input));
        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element.findP("kbn_shomikikan_tani").val(entity.kbn_shomikikan_tani);
        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element.findP("kbn_toriatsukai_ondo").val(entity.kbn_toriatsukai_ondo);

        // Re-check validate
        var element = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element;
        var targets = element.find("[data-prop='su_naiyoryo'], [data-prop='cd_naiyoryo_tani'], [data-prop='su_shomikikan_free_input'], [data-prop='kbn_shomikikan_tani'], [data-prop='kbn_toriatsukai_ondo']");
        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.validator.validate({
            targets: targets,
            state: {
                isCell: false
            },
            filter: page.options.changeValidationFilter
        });
    }

    /// Change header data
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.changeHeader = function (target) {
        var property = target.attr("data-prop"),
            id = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element.attr("data-key"),
            entity = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.headerData.entry(id),
            state = {
                isCell: target.closest("tr").length ? true : false
            },
            isPassValidate = false;

        //if (property === "kbn_shomikikan" || property === "kbn_shomikikan_seizo_fukumu" || property === "kbn_shomikikan_tani") {
        //    isPassValidate = true;
        //}

        (isPassValidate ? App.async.success() :
        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.validator.validate({
            targets: target,
            state: state,
            filter: page.options.changeValidationFilter
        })).then(function () {
            page.setIsChangeValue();
            if (property == "su_naiyoryo" || property == "su_shomikikan_free_input") {
                entity[property] = target.val().replace(/,/g, "");
            } else {
                entity[property] = target.val() || null;
            }
            _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.headerData.update(entity);
            if (target.hasClass("shomi-refer")) {
                _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.activeShomikigenRefer(property, target.val());
            }
        })
    }

    /// Reload pt_kotei
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.reloadPtKotei = function (entity) {
        if (entity) {
            entity.pt_kotei = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.param.pt_kotei;
        }
    }

    /// Reload data for [理論値], [研試作品] (for ALL)
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.refreshAllseihintokusechi = function () {
        var targets = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.detailElement.findP("nm_seihintokusechi_combo_input");
        var isFirstLoading = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.isFirstLoading;
        var reloadList = [];

        if (_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.param.mode !== page.options.mode.view) {
            $.each(targets, function (ind, target) {
                target = $(target);
                var refreshToken = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.getRefreshToken(target, target.attr("data-value"));
                if (refreshToken) {

                    reloadList.push(_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.refreshseihintokusechi(target, refreshToken));
                }
            });
        }
        App.async.all(reloadList).always(function () {
            /// Ending first loading
            page.finishLoading("TAB_Bind", "TAB6", 5);
        });
    }


    /// Get reload property when change [項目]
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.getRefreshToken = function (target, kbn_seihintokuseichi) {
        //if (!target.is(":focus")) {
        //    return null;
        //}
        //var kbn_seihintokuseichi = page.getDataFromDatalist(target);
        if (!App.isNumeric(kbn_seihintokuseichi)) {
            return null;
        }
        var items = $.grep(_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.propertyMapping, function (item) {
            return item.key == kbn_seihintokuseichi;
        });
        if (items.length) {
            return items[0];
        }
        return null;
    }

    /// Reload data for [理論値], [研試作品]
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.refreshseihintokusechi = function (target, token) {
        var element = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element,
            tbody = target.closest("tr"),
            entity = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.headerData.entry(element.attr("data-key")),
            detailEntity = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.detailData.entry(tbody.attr("data-key"));

        var param = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.param;
        if (!param.cd_shain) {
            _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.setseihintokusechi(tbody, detailEntity, "", "");
            return App.async.success();
        }
        var url = App.str.format(_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.urls.getseihintokusechi,
                                 param.cd_shain,
                                 param.nen,
                                 param.no_oi,
                                 param.seq_shisaku,
                                 token.property);
        if (token.url) {
            url = App.str.format(token.url, param);
        }
        return $.ajax(App.ajax.webapi.get(url)).then(function (result) {
            var ronrichiText = "",
                kenshisakuhinText = "",
                tmpText = "";
            if (result) {
                if (result.value && result.value.length) {
                    var data = result.value[0];
                    tmpText = data[token.property]
                }
                if (!result.value) {
                    tmpText = result;
                }
                if (token.class == "ronrichi") {
                    // Set [理論値]
                    ronrichiText = tmpText;
                } else {
                    // Set [研試作品]
                    kenshisakuhinText = tmpText;
                }
            }
            // Set seihintokusechi
            _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.setseihintokusechi(tbody, detailEntity, ronrichiText, kenshisakuhinText);
        }).fail(function () {
            // Clear [理論値], [研試作品]
            _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.setseihintokusechi(tbody, detailEntity, "", "");
        });
    }

    /// Set data for [理論値], [研試作品]
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.setseihintokusechi = function (tbody, entity, nm_seihintokusechi_ronrichi, nm_seihintokusechi_kenshisakuhin) {
        if (tbody) {
            tbody.findP("nm_seihintokusechi_ronrichi").val(nm_seihintokusechi_ronrichi);
            tbody.findP("nm_seihintokusechi_kenshisakuhin").val(nm_seihintokusechi_kenshisakuhin);
        }
        if (entity) {
            entity.nm_seihintokusechi_ronrichi = nm_seihintokusechi_ronrichi;
            entity.nm_seihintokusechi_kenshisakuhin = nm_seihintokusechi_kenshisakuhin;
        }
    }

    /**
     * マスターデータのロード処理を実行します。
     */
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.loadMasterData = function () {
        var url = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.urls.ma_literal,
            element = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element;
        return App.async.all({
            // Combobox 賞味期間区分          リテラルマスタ-カテゴリコード = 106
            kbn_shomikikan: $.ajax(App.ajax.odata.get(App.str.format(url, App.settings.app.cd_category.kbn_shomikikan))),
            // Combobox 製造日を含め区分      リテラルマスタ-カテゴリコード = 107
            kbn_shomikikan_seizo_fukumu: $.ajax(App.ajax.odata.get(App.str.format(url, App.settings.app.cd_category.kbn_shomikikan_seizo_fukumu))),
            // Combobox 単位区分             リテラルマスタ-カテゴリコード = 23
            kbn_shomikikan_tani: $.ajax(App.ajax.odata.get(App.str.format(url, App.settings.app.cd_category.kbn_shomikikan_tani))),
            // Combobox 取扱温度              リテラルマスタ-カテゴリコード = K_toriatukaiondo
            kbn_toriatsukai_ondo: $.ajax(App.ajax.odata.get(App.str.format(url, App.settings.app.cd_category.toriatsukai_ondo))),
            // Combobox 名称（品名）区分      リテラルマスタ-カテゴリコード = 108
            kbn_meisho: $.ajax(App.ajax.odata.get(App.str.format(url, App.settings.app.cd_category.kbn_meisho))),
            // Combobox 製品特性値項目        リテラルマスタ-カテゴリコード = 109
            kbn_seihin_tokuseichi: $.ajax(App.ajax.odata.get(App.str.format(url, App.settings.app.cd_category.kbn_seihin_tokuseichi))),
            // Combobox 単位                 リテラルマスタ-カテゴリコード = K_tani
            cd_naiyoryo_tani: $.ajax(App.ajax.odata.get(App.str.format(url, App.settings.app.cd_category.yoryo_tani))),
            // Combobox 年月表示対応          リテラルマスタ-カテゴリコード = 112
            cd_nengetsu_hyoji: $.ajax(App.ajax.odata.get(App.str.format(url, App.settings.app.cd_category.kbn_hinmei))),
        }).then(function (result) {

            var data = result.successes;

            // Combobox [賞味期間区分]
            var kbn_shomikikan = element.findP("kbn_shomikikan");
            kbn_shomikikan.children().remove();
            App.ui.appendOptions(
                kbn_shomikikan,
                "cd_literal",
                "nm_literal",
                data.kbn_shomikikan.value,
                true
            );

            // Combobox [製造日を含め区分]
            var kbn_shomikikan_seizo_fukumu = element.findP("kbn_shomikikan_seizo_fukumu");
            kbn_shomikikan_seizo_fukumu.children().remove();
            App.ui.appendOptions(
                kbn_shomikikan_seizo_fukumu,
                "cd_literal",
                "nm_literal",
                data.kbn_shomikikan_seizo_fukumu.value,
                true
            );

            // Combobox [単位区分]
            var kbn_shomikikan_tani = element.findP("kbn_shomikikan_tani");
            kbn_shomikikan_tani.children().remove();
            App.ui.appendOptions(
                kbn_shomikikan_tani,
                "cd_literal",
                "nm_literal",
                data.kbn_shomikikan_tani.value,
                true
            );

            // Combobox [取扱温度]
            var kbn_toriatsukai_ondo = element.findP("kbn_toriatsukai_ondo");
            kbn_toriatsukai_ondo.children().remove();
            App.ui.appendOptions(
                kbn_toriatsukai_ondo,
                "cd_literal",
                "nm_literal",
                data.kbn_toriatsukai_ondo.value,
                true
            );

            // Combobox [名称（品名）区分]
            var kbn_meisho = element.findP("kbn_meisho");
            kbn_meisho.children().remove();
            App.ui.appendOptions(
                kbn_meisho,
                "cd_literal",
                "nm_literal",
                data.kbn_meisho.value,
                true
            );

            // Combobox [製品特性値項目]
            var kbn_seihintokuseichi = element.findP("nm_seihintokusechi_combo_input");
            page.selectList(kbn_seihintokuseichi, {
                value: "cd_literal",
                text: "nm_literal",
                data: data.kbn_seihin_tokuseichi.value,
                isDefaultOption: true
            });

            // Combobox 単位
            var cd_naiyoryo_tani = element.findP("cd_naiyoryo_tani");
            cd_naiyoryo_tani.children().remove();
            App.ui.appendOptions(
                cd_naiyoryo_tani,
                "cd_literal",
                "nm_literal",
                data.cd_naiyoryo_tani.value,
                true
            );

            var mode = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.param.mode;
            if (mode == page.options.mode.view) {
                element.find(":input").prop("disabled", true);
                element.find("select").prop("disabled", true);
            }
            // Active select2
            //kbn_seihintokuseichi.selectize(page.options.kbnSeihintokuseichiSelectizeOptions);

            _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.masterData = {
                kbn_shomikikan: data.kbn_shomikikan,
                kbn_shomikikan_seizo_fukumu: data.kbn_shomikikan_seizo_fukumu,
                kbn_shomikikan_tani: data.kbn_shomikikan_tani,
                cd_nengetsu_hyoji: data.cd_nengetsu_hyoji
            }

            _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.activeMasterShomikigenRefer();
            
            return App.async.success();
        });
    }

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.setColInvalidStyle = function (target) {
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
        if ($target.hasClass("control")) {
            $target.addClass("control-required");
        }
        if ($target.hasClass("control-label")) {
            $target.addClass("control-required-label");
        }
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
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.setColValidStyle = function (target) {
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
        $target.removeClass("control-required control-required-label");
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
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.hidden = function (e) {
        var element = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element,
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
            _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.setColValidStyle(item);
        }

        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.notifyInfo.clear();
        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.notifyAlert.clear();
    };

    /**
     * 検索ダイアログ表示時処理を実行します。
     */
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.shown = function (e) {

        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element.find(":input:not(button):first").focus();
    };

    /**
     * 検索ダイアログ　バリデーションルールを定義します。
     */
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.options.validations = {
        su_naiyoryo: {
            rules: {
                required: true,
                number: true,
                range: [0.1, 9999999.9],
                pointlength: [7, 1]
                //maxlength: 8 
            },
            options: {
                name: "内容量"
            },
            messages: {
                required: App.messages.base.required,
                number: App.messages.base.number,
                maxlength: App.messages.base.maxlength,
                range: App.messages.base.range,
                pointlength: App.messages.base.pointlength
            }
        },
        nm_free_tokuseichi: {
            rules: {
                //required: true,
                maxbytelength: 2000    
            },
            options: {
                name: "特性値",
                byte: 1000
            },
            messages: {
                required: App.messages.base.required,
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_free_biseibutsu: {
            rules: {
                required: true,
                maxbytelength: 1000
            },
            options: {
                name: "微生物",
                byte: 500
            },
            messages: {
                required: App.messages.base.required,
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        kbn_shomikikan: {
            rules: {
                required: true
            },
            options: {
                name: "賞味期間情報"
            },
            messages: {
                required: App.messages.base.required
            }
        },
        kbn_shomikikan_seizo_fukumu: {
            rules: {
                required: true
            },
            options: {
                name: "製造日を含め区分"
            },
            messages: {
                required: App.messages.base.required
            }
        },
        kbn_shomikikan_tani: {
            rules: {
                required: true
            },
            options: {
                name: "単位区分"
            },
            messages: {
                required: App.messages.base.required
            }
        },
        su_shomikikan_free_input: {
            rules: {
                required: true,
                integer: true,
                range: [1, 9999]               
            },
            options: {
                name: "賞味期間日数"
            },
            messages: {
                required: App.messages.base.required,
                integer: App.messages.base.integer,
                range: App.messages.base.range
            }
        },
        nm_meisho_hinmei: {
            rules: {
                maxbytelength: 100
            },
            options: {
                name: "名称（品名）",
                byte: 50
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        su_nendo_sokuteichi: {
            rules: {
                number: true,
                //maxlength: 10,               // CCONFIRM
                range: [0, 9999999999]
            },
            options: {
                name: "数字（フリー入力）"
            },
            messages: {
                number: App.messages.base.number,
                maxlength: App.messages.base.maxlength,
                range: App.messages.base.range
            }
        },
        dt_nendo_sokuteichi: {
            rules: {
                maxbytelength: 20      
            },
            options: {
                name: "日付（フリー入力）",
                byte: 10
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_seihintokusechi_combo_input: {
            rules: {
                maxbytelength: 100        
            },
            options: {
                name: "項目",
                byte: 50
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_seihintokusechi_tp: {
            rules: {
                maxbytelength: 20
            },
            options: {
                name: "TP品",
                byte: 10
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_seihintokusechi_tp: {
            rules: {
                maxbytelength: 20
            },
            options: {
                name: "TP品",
                byte: 10
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_seihintokusechi_title1: {
            rules: {
                maxbytelength: 60
            },
            options: {
                name: "フリー入力タイトル１",
                byte: 30
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_seihintokusechi_title2: {
            rules: {
                maxbytelength: 60
            },
            options: {
                name: "フリー入力タイトル２",
                byte: 30
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_seihintokusechi_title3: {
            rules: {
                maxbytelength: 60
            },
            options: {
                name: "フリー入力タイトル３",
                byte: 30
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_seihintokusechi_input1: {
            rules: {
                maxbytelength: 20
            },
            options: {
                name: "フリー入力内容１",
                byte: 10
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_seihintokusechi_input2: {
            rules: {
                maxbytelength: 20
            },
            options: {
                name: "フリー入力内容２",
                byte: 10
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_seihintokusechi_input3: {
            rules: {
                maxbytelength: 20
            },
            options: {
                name: "フリー入力内容３",
                byte: 10
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        kbn_toriatsukai_ondo: {
            rules: {
                required: true
            },
            options: {
                name: "取扱温度"
            },
            messages: {
                required: App.messages.base.required
            }
        },
        cd_naiyoryo_tani: {
            rules: {
                required: true
            },
            options: {
                name: "内容量単位"
            },
            messages: {
                required: App.messages.base.required
            }
        }
    };


    /**
     * Function for [連携選択]
     */
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.openHinmeiSentaku = function () {
        page.dialogs._800_HinmeiSentaku_Dialog.dataSelected = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.selectHinmei;
        page.dialogs._800_HinmeiSentaku_Dialog.element.modal("show");
    }

    /**
     * Set return hinmei data from dialog [名称（品名）選択]
     */
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.selectHinmei = function (data) {
        if (data) {
            _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element.findP("nm_meisho_hinmei").val(data.KEN1_HINNM_NM).change();
        }
    }

    /**
     * Reload copy data
     */
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.searchCopy = function () {
        var element = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element,
            url = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.urls.getCopyData;

        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.options.filter = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.createFilter();
        $.ajax(App.ajax.webapi.get(url, _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.options.filter))
        .done(function (result) {
            var data = {},
                currentDetailData = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.detailData.findAll(function (item, entity) {
                    return entity.state === App.ui.page.dataSet.status.Added;
                });
            data.headerData = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.mergeSearchData(result.headerData);
            // Remove current detail data
            $.each(currentDetailData, function (ind, item) {
                _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.detailData.remove(item);
            });
            // Get copy detail data
            data.detailData = result.detailData;
            $.each(result.detailData, function (ind, item) {
                _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.detailData.add(item);
            });
            // Bind new merged data
            _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.bind(data);
        }).always(function () {
        });
    }

    /**
     * 検索ダイアログの検索処理を実行します。
     */
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.search = function () {
        var element = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element,
            loadingTaget = element.find(".modal-content"),
            url = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.urls.search;

        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.options.filter = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.createFilter();

        //_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.validator.validate()
        //.then(function () {

            $.ajax(App.ajax.webapi.get(url, _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.options.filter))
            .done(function (result) {
                var data = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.prepairBindingData(result);
                _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.bind(data);
            }).always(function () {

                //App.ui.loading.close(loadingTaget);
            });
        //});
    };

    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.prepairBindingData = function (searchData) {
        searchData = searchData || {};
        var param = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.param,
            element = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element,
            headerData = searchData.headerData || {},
            hDataSet = App.ui.page.dataSet(),
            detailData = searchData.detailData || [],
            dDataSet = App.ui.page.dataSet(),
            modes = page.options.mode;

        // Bind header data [製法文書_Tab6_製品規格案及び取扱基準]
        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.headerData = hDataSet;
        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.hData = headerData;
        // Bind detail data [製法文書_Tab6_製品特性値一覧]
        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.detailData = dDataSet;
        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.dData = detailData;

        switch (param.mode) {
            case modes.new:
                hDataSet.add(headerData);
                break;
            case modes.new_copy:
                //_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.mergeSearchData(searchData.headerDataCopy);
                hDataSet.add(headerData);
                $.each(detailData, function (ind, item) {
                    item.no_seiho = param.no_seiho;
                    item.cd_toroku = null;
                    item.dt_toroku = null;
                    dDataSet.add(item);
                });
                break;
            case modes.edit:
                hDataSet.attach(headerData);
                hDataSet.update(headerData);
                $.each(detailData, function (ind, item) {
                    var removeItem = $.extend({}, item);
                    // remove current data
                    dDataSet.attach(removeItem);
                    dDataSet.remove(removeItem);
                    // Add new data
                    dDataSet.add(item);
                });
                break;
            case modes.edit_copy:
                //_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.mergeSearchData(searchData.headerDataCopy);
                hDataSet.attach(headerData);
                hDataSet.update(headerData);
                $.each(searchData.detailDataOld, function (ind, item) {
                    // remove current data
                    dDataSet.attach(item);
                    dDataSet.remove(item);
                });
                $.each(detailData, function (ind, item) {
                    // Retrive toroku info
                    //var dbData = $.grep(searchData.detailDataOld, function (dbItem) {
                    //    return (dbItem.no_seq_seihintokusechi == item.no_seq_seihintokusechi);
                    //});
                    //dbData = dbData.length > 0 ? dbData[0] : {};
                    item.cd_toroku = null;
                    item.dt_toroku = null;
                    // add new data
                    dDataSet.add(item);
                });
                break;
            case modes.view:
                hDataSet.attach(headerData);
                $.each(detailData, function (ind, item) {
                    dDataSet.attach(item);
                });
                break;
        }

        return {
            headerData: headerData,
            detailData: detailData
        }
    }

    /**
    * Merge current data with copy data
    */
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.mergeSearchData = function (copyData) {
        var currentData = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.hData;
        copyData = copyData || {}
        if (currentData) {
            currentData.su_naiyoryo = copyData.su_naiyoryo || "";
            currentData.cd_naiyoryo_tani = copyData.cd_naiyoryo_tani || "";
            currentData.nm_free_tokuseichi = copyData.nm_free_tokuseichi || "";
            currentData.nm_free_biseibutsu = copyData.nm_free_biseibutsu || "";
            currentData.kbn_shomikikan = copyData.kbn_shomikikan || "";
            currentData.kbn_shomikikan_seizo_fukumu = copyData.kbn_shomikikan_seizo_fukumu || "";
            currentData.su_shomikikan_free_input = copyData.su_shomikikan_free_input || "";
            currentData.kbn_shomikikan_tani = copyData.kbn_shomikikan_tani || "";
            currentData.kbn_toriatsukai_ondo = copyData.kbn_toriatsukai_ondo || "";
            currentData.kbn_meisho = copyData.kbn_meisho || "";
            currentData.nm_meisho_hinmei = copyData.nm_meisho_hinmei || "";
            currentData.nm_seihintokusechi_title1 = copyData.nm_seihintokusechi_title1 || "";
            currentData.nm_seihintokusechi_title2 = copyData.nm_seihintokusechi_title2 || "";
            currentData.nm_seihintokusechi_title3 = copyData.nm_seihintokusechi_title3 || "";
            if (currentData.pt_kotei == App.settings.app.pt_kotei.sonohokaeki || page.header.element.findP("cd_shain").val() === "") {
                currentData.su_nendo_sokuteichi = "";
                currentData.dt_nendo_sokuteichi = "";
            } else {
                currentData.su_nendo_sokuteichi = copyData.su_nendo_sokuteichi || "";
                currentData.dt_nendo_sokuteichi = copyData.dt_nendo_sokuteichi || "";
            }
        }
        return currentData;
    }

    /**
     * 検索ダイアログの検索条件を組み立てます
     */
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.createFilter = function () {
        var param = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.param;
        return {
            no_seiho: param.no_seiho,
            cd_shain: param.cd_shain,
            nen: param.nen,
            no_oi: param.no_oi,
            no_seiho_copy: param.no_seiho_copy,
            yoryo_tani: App.settings.app.cd_category.yoryo_tani,
            cd_naiyoryo_tani: param.cd_naiyoryo_tani,
            pt_kotei: param.pt_kotei,
            mode: param.mode,
            cd_category_shomikikan: App.settings.app.cd_category.shomikikan
        }
    };

    /**
     * Data Appliers
     */
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.bindHeaderOption = {
        appliers: {
            su_naiyoryo: function (value, element) {
                if (value !== "") {
                    element.val(App.num.format(Number(value), "#,#.#"));
                }
                return true;
            },
            su_shomikikan_free_input: function (value, element) {
                if (value !== "") {
                    element.val(App.num.format(Number(value), "#,#"));
                }
                return true;
            },
            su_nendo_sokuteichi: function (value, element) {
                if (value !== "") {
                    element.val(App.num.format(Number(value), "#,#"));
                }
                return true;
            },
            cd_naiyoryo_tani: function (value, element) {
                if (value !== "") {
                    element.val(App.common.fillString(value, 3));
                }
                return true;
            }
        }
    };
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.bindDetailOption = {
        appliers: {
            //kbn_seihintokuseichi: function (value, element) {
            //    element.setValue(value);
            //    return true;
            //}
        }
    };

    /**
    * Show / Hide [測定方法] base on pt_kotei
    */
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.processShokuteiInfo = function () {
        var pt_kotei = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.hData.pt_kotei,
            element = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element;

        // Transform TAB layout
        // 以下の処理で取得した工程パターン = 「003」の場合、画面上の測定方法情報が非表示される。
        if (pt_kotei == App.settings.app.pt_kotei.sonohokaeki || page.header.element.findP("cd_shain").val() === "") {
            element.find(".sokutei-hoho").hide();
        } else {
            element.find(".sokutei-hoho").show();
        }

    }

    /**
     * 検索ダイアログの一覧にデータをバインドします。
     */
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.bind = function (data) {
        var element = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element,
            detailElement = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.detailElement,
            freeCode = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.options.freeCode;
        
        /// Bind referance to tab 8
        _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.bindShomikigen(data.headerData);

        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element.form(_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.bindHeaderOption).bind(data.headerData);

        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.processShokuteiInfo();

        $.each(detailElement, function (ind, row) {
            row = $(row),
            existData = false;
            row.find(":input").val("");
            // if exist data in database
            for (var i = 0; i < data.detailData.length; i++) {
                if (data.detailData[i].no_seq_seihintokusechi == (ind + 1)) {
                    var dData = data.detailData[i];
                    // Bind the data
                    row.form(_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.bindDetailOption).bind(dData);
                    row.findP("nm_seihintokusechi_combo_input").attr("data-value", dData.kbn_seihintokuseichi);
                    existData = true;
                    break;
                }
            }
            if (!existData) {
                dData = {
                    no_seq_seihintokusechi: row.find("td:first label").text()
                }
                _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.detailData.add(dData);
                // Bind the data
                row.form(_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.bindDetailOption).bind(dData);
            }

        });

        _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.refreshAllseihintokusechi();
        
        setTimeout(function () {
            _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.isFirstLoading = false;
        }, 1000);

        // Active select2
        //detailElement.findP("kbn_seihintokuseichi").select2({
        //    tags: true,
        //    selectOnClose: true,
        //    "language": {
        //        "noResults": function () {
        //            return "結果が見つかりません";
        //        }
        //    },
        //    escapeMarkup: function (markup) {
        //        return markup;
        //    }
        //});
    };

    /**
     * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
     */
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.select = function (e) {
        var element = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element,
            target = $(e.target),
            id = target.closest("tbody").attr("data-key"),
            data;

        if (App.isUndef(id)) {
            return;
        }

        data = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.data.entry(id);

        if (App.isFunc(_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.dataSelected)) {
            if (!_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.dataSelected(data)) {
                element.modal("hide");
            }
        } else {
            element.modal("hide");
        }
    };

    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.isEmptyDetailRow = function (data) {
        if (!data) {
            return true;
        }
        if (App.isUndefOrNullOrStrEmpty(data.nm_seihintokusechi_ronrichi)
            && App.isUndefOrNullOrStrEmpty(data.nm_seihintokusechi_kenshisakuhin)
            && App.isUndefOrNullOrStrEmpty(data.nm_seihintokusechi_tp)
            && App.isUndefOrNullOrStrEmpty(data.nm_seihintokusechi_input1)
            && App.isUndefOrNullOrStrEmpty(data.nm_seihintokusechi_input2)
            && App.isUndefOrNullOrStrEmpty(data.nm_seihintokusechi_input3)
            && App.isUndefOrNullOrStrEmpty(data.kbn_seihintokuseichi)) {
            return true;
        }
        return false;
    }

    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.getSaveData = function () {
        // No change found
        if (!_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.isChange) {
            return null;
        }
        // View mode
        if (!_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.param.mode == page.options.mode.view) {
            return null;
        }
        // Any change found
        // Prepair save data
        // Reset [測定方法] When pt_kotei = 003 
        var headerData = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.hData;
        if (headerData && _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.param.pt_kotei == App.settings.app.pt_kotei.sonohokaeki) {
            headerData.su_nendo_sokuteichi = null;
            headerData.dt_nendo_sokuteichi = null;
        }
        var result = {
            headerData: _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.headerData.getChangeSet(),
            detailData: {
                created: [],
                updated: [],
                deleted: []
            }
        }, detailChangeset = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.detailData.getChangeSet();
        // Deleted change set
        result.detailData.deleted = detailChangeset.deleted;
        // Consider the update changeset, if the row is empty -> delete the row
        $.each(detailChangeset.updated, function (ind, item) {
            item.no_seiho = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.param.no_seiho;
            if (!_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.isEmptyDetailRow(item)) {
                result.detailData.updated.push(item);
            } else {
                result.detailData.deleted.push(item)
            }
        });
        // Consider the update changeset, if the row is not empty -> create the row
        $.each(detailChangeset.created, function (ind, item) {
            item.no_seiho = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.param.no_seiho;
            if (!_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.isEmptyDetailRow(item)) {
                result.detailData.created.push(item);
            }
        });
        return result;
    }

    /**
    * Check all validate
    */
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.validateAll = function (filter) {
        var validateHeader = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.validator.validate({
                targets: _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element.find(".part:not(.detail-content) :input"),
                filter: filter,
                state: {
                    suppressMessage: false
                }
            }),
            validateHeaderDetail = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.validator.validate({
                targets: _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.element.find("table th :input"),
                filter: filter,
                state: {
                    suppressMessage: false
                }
            }),
            detailElement = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.detailElement,
            validateDetail = [];

        $.each(detailElement, function (ind, row) {
            row = $(row);
            validateDetail.push(_300_SeihoBunshoSakusei_SeihinKikakuan_Tab.validator.validate({
                targets: row.find(":input"),
                filter: filter,
                state: {
                    suppressMessage: false,
                    isGridValidation: true
                }
            }))
        });

        return App.async.all({
            validateHeader: validateHeader,
            validateHeaderDetail: validateHeaderDetail,
            validateDetail: App.async.all(validateDetail)
        });

    }

    /**
    * Loadmaster data 賞味期間 in Tab 8 賞味期間設定表
    * 賞味期間:  賞味期間区分, 製造日を含め区分, 日数, 単位区分
    */
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.activeMasterShomikigenRefer = function () {
        var data = _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.masterData;
        _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.bindMasterShomikigen(data);
    }

    /**
    * Change 賞味期間 in Tab 8 賞味期間設定表
    * 賞味期間:  賞味期間区分, 製造日を含め区分, 日数, 単位区分
    */
    _300_SeihoBunshoSakusei_SeihinKikakuan_Tab.activeShomikigenRefer = function (property, value) {
        _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_Tab.setShomikigen(property, value);
    }
</script>

<div class="tab-pane" id="_300_SeihoBunshoSakusei_SeihinKikakuan_Tab">
    <div class="sub-tab-content smaller">
        <%--<div class="col-xs-12 col-zr roof">
            <div class="part">
                <div class="part-content">
                    <div class="row">
                        <div class="col-xs-1 control-label">
                            <label>試作No</label>
                        </div>
                        <div class="col-xs-11 control">
                            <input type="tel" style="width: 70px;" disabled="disabled" class="seiho" data-prop="cd_shain" />
                            <span style="width: 30px;">－</span>
                            <input type="tel" style="width: 30px;" disabled="disabled" class="seiho" data-prop="nen" />
                            <span style="width: 30px;">－</span>
                            <input type="tel" style="width: 40px;" disabled="disabled" class="seiho" data-prop="no_oi" />
                            <span style="width: 30px;">（</span>
                            <input type="tel" style="width: 120px;" disabled="disabled" class="seiho" data-prop="nm_sample" />
                            <span style="width: 30px;">）</span>
                            <button type="button" class="btn btn-xs btn-primary" id="open-hinmei-sentaku">連携選択</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>--%>
        <div class="col-xs-6 tab-padding roof">
            <div class="part">
                <div class="part-content">
                    <div class="row naiyoryo">
                        <div class="control-label col-xs-2">
                            <label>内容量</label>
                        </div>
                        <div class="control col-xs-10 group-control">
                            <input type="tel" class="right number-only limit-input-float" style="width: 100px" data-prop="su_naiyoryo" maxlength="9" data-number-format="#,#.########" />
                            <%--<label data-prop="nm_naiyoryo_tani"></label>--%>
                            <select data-prop="cd_naiyoryo_tani" style="width: 60px; margin-left: 3px;"></select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="part col-xs-6 col-zr">
                    <div class="part-content">
                        <div class="col-xs-12 col-zr">
                            <div class="row">
                                <div class="control-label col-xs-12 text-content">
                                    <div class="row">
                                        <div class="small-height col-xs-12">
                                            <label>特性値</label>
                                        </div>
                                    </div>
                                    <textarea class="textarea-height fit-25" data-prop="nm_free_tokuseichi"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
               <div class="part col-xs-6 col-zr" style="width: calc(50% - 4px)">
                    <div class="part-content">
                        <div class="col-xs-12 col-zr">
                            <div class="row">
                                <div class="control-label col-xs-12 text-content">
                                    <div class="row">
                                        <div class="small-height col-xs-12">
                                            <label>微生物</label>
                                        </div>
                                    </div>
                                    <textarea class="textarea-height fit-25" data-prop="nm_free_biseibutsu"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="part">
                <div class="part-content">
                    <div class="row shomi-kigen">
                        <div class="control-label col-xs-2">
                            <label>賞味期間</label>
                        </div>
                        <div class="control col-xs-2">
                            <select data-prop="kbn_shomikikan" class="shomi-refer"></select>
                        </div>
                        <div class="control col-xs-3">
                            <select data-prop="kbn_shomikikan_seizo_fukumu" class="shomi-refer"></select>
                        </div>
                        <div class="control col-xs-1 with-next-col">
                            <input type="tel" class="shomi-refer right limit-input-int" data-prop="su_shomikikan_free_input" maxlength="4" data-number-format="#,#.######" />
                        </div>
                        <div class="control col-xs-1 with-next-col">
                            <select data-prop="kbn_shomikikan_tani" class="shomi-refer"></select>
                        </div>
                        <div class="control col-xs-3">
                        </div>
                    </div>
                    <div class="row">
                         <div class="control-label col-xs-2">
                            <label>取扱温度</label>
                        </div>
                        <div class="control col-xs-2">
                            <select data-prop="kbn_toriatsukai_ondo">
                                <option>冷凍</option>
                            </select>
                        </div>
                        <div class="control col-xs-8">
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-label col-xs-12">
                        </div>
                    </div>
                </div>
            </div>
            <div class="part">
                <div class="part-content">
                    <div class="row">
                        <div class="control-label col-xs-12">
                            <label>取扱基準</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-label col-xs-2">
                            <label>名称（品名）</label>
                        </div>
                        <div class="control col-xs-3">
                            <select data-prop="kbn_meisho">
                                <option></option>
                            </select>
                        </div>
                        <div class="col-xs-7 control">
                            <button type="button" class="btn btn-xs btn-primary" id="open-hinmei-sentaku">選択</button>
                            <input type="text" style="width: calc(100% - 100px)" data-prop="nm_meisho_hinmei" />
                        </div>
                    </div>
                </div>
            </div>
            <div class="part sokutei-hoho">
                <div class="part-content">
                    <div class="row">
                        <div class="control-label col-xs-2" style="height: 192px;">
                            <label>粘度</label>
                        </div>
                        <div class="control col-xs-10" style="height: 192px;">
                            <textarea style="height: 135px" readonly="readonly" class="inp-lb" tabindex="-1">粘度ガイドライン測定方法（全国マヨネーズ・ドレッシング類協会） 
・測定機器：ブルックフィールド回転粘度計　BH型 
・測定温度：20℃±5℃
・測定容器：200mlトールビーカー ・サンプル量：200ml 
・ローター、スピード：No.6-4rpm 
・測定時の条件：回転を開始してから1分後の示度 
・測定値の計算方法：測定示度を2.5倍する。（パスカル・秒）</textarea>
                            <%--<label style="width: 100%"></label>--%>
                            <div class="col-xs-6">
                                <input type="tel" class="number-only limit-input-int" style="margin-bottom: 2px" data-prop="su_nendo_sokuteichi" maxlength="10" placeholder="数字（フリー入力）" data-number-format="#,#" />
                                <input type="text" data-prop="dt_nendo_sokuteichi"  placeholder="日付（フリー入力）"/>
                            </div>
                            <div class="col-xs-6">
                                <label style="padding-top: 10px; font-size: 20px;">Pa・ｓ</label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-6 col-zr roof">
            <div class="part detail-content" style="margin-right: 0px;">
                <div class="part-content">
                    <div class="col-xs-12 col-zr">                        
                        <div class="row">
                            <div class="control-label col-xs-12">
                                <label>製品特性値</label>
                            </div>
                        </div>
                        <div class="row row-table-parent">
                            <div class="control col-xs-12 row-table-parent">
                                <div>
                                    <table class="datatable table-striped table-condensed search-list">
                                        <!--TODO: 明細一覧のヘッダーを定義するHTMLをここに記述します。-->
                                        <thead>
                                            <tr>
                                                <th style="width: 30px">No</th>
                                                <th>項目</th>
                                                <th style="width: 11%">理論値</th>
                                                <th style="width: 11%">研試作品</th>
                                                <th style="width: 11%">TP品</th>
                                                <th style="width: 11%"><input type="text" class="" data-prop="nm_seihintokusechi_title1" /></th>
                                                <th style="width: 11%"><input type="text" class="" data-prop="nm_seihintokusechi_title2" /></th>
                                                <th style="width: 11%"><input type="text" class="" data-prop="nm_seihintokusechi_title3" /></th>
                                            </tr>
                                        </thead>
                                    </table>
                                </div>
                                <div style="height: 700px; overflow: auto;">
                                    <table class="datatable table-striped-even table-condensed search-list" style="margin-top: -1px; margin-right: 0">
                                        <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->
                                        <tbody style="cursor: default;">
                                            <tr class="item-tmpl" style="display: none;">
                                                <td style="width: 30px" class="center">
                                                    <label data-prop="no_seq_seihintokusechi">1</label></td>
                                                <td>
                                                    <select data-prop="nm_seihintokusechi_combo_input"></select>
                                                </td>
                                                <td style="width: 11%">
                                                    <input type="text" readonly="readonly" class="right inp-lb" data-prop="nm_seihintokusechi_ronrichi" />
                                                </td>
                                                <td style="width: 11%">
                                                    <input type="text" readonly="readonly" class="right inp-lb" data-prop="nm_seihintokusechi_kenshisakuhin" />
                                                <td style="width: 11%"  class="center">
                                                    <input type="text" value="" class="right" data-prop="nm_seihintokusechi_tp" />
                                                </td>
                                                <td style="width: 11%"  class="center">
                                                    <input type="text" value="" class="right" data-prop="nm_seihintokusechi_input1" />
                                                </td>
                                                <td style="width: 11%"  class="center">
                                                    <input type="text" value="" class="right" data-prop="nm_seihintokusechi_input2" />
                                                </td>
                                                <td style="width: 11%"  class="center">
                                                    <input type="text" value="" class="right" data-prop="nm_seihintokusechi_input3"/>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>