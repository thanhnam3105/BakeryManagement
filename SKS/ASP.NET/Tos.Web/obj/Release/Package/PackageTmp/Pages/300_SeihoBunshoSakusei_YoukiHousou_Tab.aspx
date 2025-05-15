<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="300_SeihoBunshoSakusei_YoukiHousou_Tab.aspx.cs" Inherits="Tos.Web.Pages._300_SeihoBunshoSakusei_YoukiHousou_Tab" %>
<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>
<style type="text/css">
    #_300_SeihoBunshoSakusei_YoukiHousou_Tab .part-content .col-xs-12 {
    }

    #_300_SeihoBunshoSakusei_YoukiHousou_Tab .row-area {
        height: 376px!important;
    }

    #_300_SeihoBunshoSakusei_YoukiHousou_Tab textarea {
        width: 100%;
        resize: none;
        border: none;
        margin: 0px;
    }
    
    
    #_300_SeihoBunshoSakusei_YoukiHousou_Tab input {
        border: none;
        margin: 0px;
        width: 100%;
        height: 100%;
    }

    #_300_SeihoBunshoSakusei_YoukiHousou_Tab .datatable tbody tr {
        height: 20px !important;
        padding: 0px;
    }

    #_300_SeihoBunshoSakusei_YoukiHousou_Tab .datatable .width-column {
        width: 9% !important;
    }

    #_300_SeihoBunshoSakusei_YoukiHousou_Tab .datatable tr .background {
        background-color: #fff !important;
        border-right: 1px solid #fff !important;
        border-top: 1px solid #fff !important;
    }

    #_300_SeihoBunshoSakusei_YoukiHousou_Tab .datatable .padding {
        padding-left: 20px !important;
    }

    #_300_SeihoBunshoSakusei_YoukiHousou_Tab .datatable tr td:first-child {
        vertical-align: top;
        background-color: #efefef;
    }

    #_300_SeihoBunshoSakusei_YoukiHousou_Tab .nav-tabs .tab-a {
        padding: 0px 3.7px;
        background-color: #fff;
    }

    #_300_SeihoBunshoSakusei_YoukiHousou_Tab .nav-tabs .active {
        font-weight: bold;
        border: 1px solid #fff !important;
    }
    #_300_SeihoBunshoSakusei_YoukiHousou_Tab .nav-tabs .active a{
        border: 1px solid #fff !important;
        color: #555;
    }
    #_300_SeihoBunshoSakusei_YoukiHousou_Tab .detail_6 {
        height: 300px;
    }
    #_300_SeihoBunshoSakusei_YoukiHousou_Tab tr.th-frame {
        border-top: 0!important;
        border-left: 0!important;
        border-right: 0!important;
        height: 0px!important;
    }

    #_300_SeihoBunshoSakusei_YoukiHousou_Tab tr.th-frame th {
        border: 0px;
    }
    #_300_SeihoBunshoSakusei_YoukiHousou_Tab .select2-container {
        width: 100%!important;
    }

    #_300_SeihoBunshoSakusei_YoukiHousou_Tab .datatable-header .select2-container {
        width: 200px!important;
    }

    #_300_SeihoBunshoSakusei_YoukiHousou_Tab .select2-container .select2-selection--single {
        height: 22px;
        border: none;
    }

    #_300_SeihoBunshoSakusei_YoukiHousou_Tab .datatable-header .select2-container .select2-selection--single {
        border: 1px solid #ccc;
    }

    #_300_SeihoBunshoSakusei_YoukiHousou_Tab .select2-container--default .select2-selection--single .select2-selection__arrow {
        height: 18px;
    }

    #_300_SeihoBunshoSakusei_YoukiHousou_Tab tr.has-error th {
        background-color: #ffdab9;
    }

    #_300_SeihoBunshoSakusei_YoukiHousou_Tab .select2-container--default .select2-selection--single .select2-selection__rendered {
        line-height: 20px;
    }
    #_300_SeihoBunshoSakusei_YoukiHousou_Tab td .selectize-input { 
        height: 20px; 
        border: none;
    }
    #_300_SeihoBunshoSakusei_YoukiHousou_Tab td select {
        display: none!important;
    }
    #_300_SeihoBunshoSakusei_YoukiHousou_Tab td.has-error input,
    #_300_SeihoBunshoSakusei_YoukiHousou_Tab td.has-error textarea,
    #_300_SeihoBunshoSakusei_YoukiHousou_Tab td.has-error .selectize-input {
        background-color: #ffdab9!important;
    }

</style>
    <script type="text/javascript">

        /**
         * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
         */
        var _300_SeihoBunshoSakusei_YoukiHousou_Tab = {
            options: {
                defaultValue: {
                    yokiHoso: {
                        // 容器包装コード
                        cd_yoki_hoso: 9999,
                        // 容器包装資材コード
                        cd_yoki_hoso_shizai01: 9001,
                        cd_yoki_hoso_shizai02: 9002,
                        cd_yoki_hoso_shizai03: 9003,
                        cd_yoki_hoso_shizai04: 9004,
                        cd_yoki_hoso_shizai05: 9005,
                        cd_yoki_hoso_shizai06: 9006,
                        cd_yoki_hoso_shizai07: 9007,
                        cd_yoki_hoso_shizai08: 9008,
                        cd_yoki_hoso_shizai09: 9009,
                        cd_yoki_hoso_shizai10: 9010
                    },
                    shizai: {
                        // メーカー
                        cd_maker01: null,
                        cd_maker02: null,
                        cd_maker03: null,
                        cd_maker04: null,
                        cd_maker05: null,
                        // 材質
                        cd_zaishitsu01: null,
                        cd_zaishitsu02: null,
                        cd_zaishitsu03: null,
                        cd_zaishitsu04: null,
                        cd_zaishitsu05: null,
                        // リサイクル表示
                        nm_recycle_hyoji01: null,
                        nm_recycle_hyoji02: null,
                        nm_recycle_hyoji03: null,
                        nm_recycle_hyoji04: null,
                        nm_recycle_hyoji05: null
                    }
                },
                lstMaster: ["nm_yoki_hoso_shizai", "nm_maker", "nm_zaishitsu", "nm_recycle_hyoji"]
            },
            urls: {
                search: "../api/_300_SeihoBunshoSakusei_YoukiHousou_Tab",
                searchCopy: "../api/_300_SeihoBunshoSakusei_YoukiHousou_Tab/GetCopyData",
                searchFreeDetail: "../api/_300_SeihoBunshoSakusei_YoukiHousou_Tab/GetDetailFree",
                searchYokiDetail: "../api/_300_SeihoBunshoSakusei_YoukiHousou_Tab/GetDetailYokihoso",
                searchHosoMaster: "../api/_300_SeihoBunshoSakusei_YoukiHousou_Tab/GetYokiHosoComboData",
                searchMasterData: "../api/_300_SeihoBunshoSakusei_YoukiHousou_Tab/GetMasterData"
            },
            param: {
                no_seiho: "0001-A01-19-0068",
                mode: "edit"
            },
            name: "【容器包装】",
            isChange: false
        };

        /**
         * 検索ダイアログの初期化処理を行います。
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.initialize = function () {
            var element = $("#_300_SeihoBunshoSakusei_YoukiHousou_Tab");

            element.on("change", ":input", _300_SeihoBunshoSakusei_YoukiHousou_Tab.change);
            _300_SeihoBunshoSakusei_YoukiHousou_Tab.element = element;

            element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
            element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

            element.find("td input, td textarea").prop("disabled", true);

            _300_SeihoBunshoSakusei_YoukiHousou_Tab.notifyInfo =
                 App.ui.notify.info(element, {
                     container: "#_300_SeihoBunshoSakusei_YoukiHousou_Tab .dialog-slideup-area .info-message",
                     bodyContainer: ".modal-body",
                     show: function () {
                         element.find(".info-message").show();
                     },
                     clear: function () {
                         element.find(".info-message").hide();
                     }
                 });
            _300_SeihoBunshoSakusei_YoukiHousou_Tab.notifyAlert =
                App.ui.notify.alert(element, {
                    container: "#_300_SeihoBunshoSakusei_YoukiHousou_Tab .dialog-slideup-area .alert-message",
                    bodyContainer: ".modal-body",
                    show: function () {
                        element.find(".alert-message").show();
                    },
                    clear: function () {
                        element.find(".alert-message").hide();
                    }
                });

            _300_SeihoBunshoSakusei_YoukiHousou_Tab.validator = [];
            _300_SeihoBunshoSakusei_YoukiHousou_Tab.validationList = [];
            for (var i = 0; i < 10; i++) {
                var validations = _300_SeihoBunshoSakusei_YoukiHousou_Tab.getValidation(i);
                _300_SeihoBunshoSakusei_YoukiHousou_Tab.validationList.push(validations);
                var validator = element.validation(App.validation(validations, {
                    success: function (results, state) {
                        var i = 0, l = results.length,
                            item, $target;

                        for (; i < l; i++) {
                            item = results[i];
                            $(item.element).closest("td, th").removeClass("has-error");
                            //App.ui.page.setColValidStyle(item.element);

                            App.ui.page.notifyAlert.remove(item.element);
                        }
                    },
                    fail: function (results, state) {
                        var i = 0, l = results.length,
                            item, $target;

                        for (; i < l; i++) {
                            item = results[i];
                            //_300_SeihoBunshoSakusei_YoukiHousou_Tab.setColInvalidStyle(item.element);
                            $(item.element).closest("td, th").addClass("has-error");
                            if (state && state.suppressMessage) {
                                continue;
                            }

                            App.ui.page.notifyAlert.message(_300_SeihoBunshoSakusei_YoukiHousou_Tab.name + item.message, item.element).show();
                        }
                    },
                    always: function (results) {
                        //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
                    }
               }));
               _300_SeihoBunshoSakusei_YoukiHousou_Tab.validator.push(validator);
            }

            var table = element.find(".datatable-header");
            _300_SeihoBunshoSakusei_YoukiHousou_Tab.datatableHeader = table;
            _300_SeihoBunshoSakusei_YoukiHousou_Tab.contructGrid();
            _300_SeihoBunshoSakusei_YoukiHousou_Tab.beforeSearch();
            // Prevent confirm when change cd_yokihoso in new mode
            if (_300_SeihoBunshoSakusei_YoukiHousou_Tab.param.mode === page.options.mode.new) {
                _300_SeihoBunshoSakusei_YoukiHousou_Tab.isChange = false;
            }
            page.finishLoading("TAB_Initilize", "TAB2", 5);
            element.on("mouseenter", "[data-prop]", function (e) {
                var target = $(e.target);
                if (target.is(":input")) {
                    target.prop("title", target.val());
                } else {
                    target.prop("title", target.text());
                }
                //if (target.prop("offsetWidth") < target.prop("scrollWidth") && !target.prop("title")) {
                //    if (target.is(":input")) {
                //        target.prop("title", target.val());
                //    } else {
                //        target.prop("title", target.text());
                //    }
                //} else {
                //    target.prop("title", "");
                //}
            });
            element.find('[data-prop]').addClass("overflow-ellipsis");
        };

        /**
         * Get validate options with name message add index
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.getValidation = function (index) {
            var validation = {};
            $.extend(true, validation, _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.validations_tmp);
            $.each(validation, function (name, item) {
                if (item.options.isAutoParam) {
                    item.options.name += "_" + (index + 1);
                }
            });
            return validation;
        }

        /**
         * Contruction of combobox
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.contructGrid = function () {
            var element = _300_SeihoBunshoSakusei_YoukiHousou_Tab.element,
                combobox = element.find("td select");
            combobox.each(function (i, e) {
                var item = $(e),
                    colIndex = item.closest("td").attr("data-col");
                if (colIndex) {
                    item.attr("id", item.attr("data-prop") + "_" + colIndex);
                }
            });
            page.selectList(combobox);
            element.find("td input").prop("disabled", true);
        }

        /**
         * 
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.bindContainerPacking = function (data) {
            var element = _300_SeihoBunshoSakusei_YoukiHousou_Tab.element,
                nav_tabs = element.find(".number_th ul"),
                data_html = "";
            nav_tabs.children().remove();
            for (i = 1; i <= data; i++) {
                var item_html = "<li class='" + ((i == 29) ? "active" : "") + "' ><a data-toggle='tab' tabindex='-1' class='tab-a' href='#" + i + "' id='" + i + "'>" + i + "</a></li>";
                data_html += item_html;
            }
            nav_tabs.append(data_html);
        };

        /**
         * 単項目要素をエラーのスタイルに設定します。
         * @param target 設定する要素
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.setColInvalidStyle = function (target) {
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
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.setColValidStyle = function (target) {
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

        _300_SeihoBunshoSakusei_YoukiHousou_Tab.getChangeCDYokiHoso = function (target) {
            var cd_yoki_hoso = page.getValueFromList(target);
            cd_yoki_hoso = cd_yoki_hoso || _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.defaultValue.yokiHoso.cd_yoki_hoso;
            return cd_yoki_hoso;
        }

        /**
         * Element on change function 
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.change = function (e) {
            var target = $(e.target),
                property = target.attr("data-prop");

            if (!property) {
                return;
            }

            if (property === "nm_yoki_hoso") {
                var cd_yoki_hoso = _300_SeihoBunshoSakusei_YoukiHousou_Tab.getChangeCDYokiHoso(target);
                return _300_SeihoBunshoSakusei_YoukiHousou_Tab.validator[0].validate({
                    targets: target,
                    filter: page.options.changeValidationFilter
                }).then(function () {
                    if (_300_SeihoBunshoSakusei_YoukiHousou_Tab.isRevertChange) {
                        _300_SeihoBunshoSakusei_YoukiHousou_Tab.isRevertChange = false;
                        return;
                    }
                    if (_300_SeihoBunshoSakusei_YoukiHousou_Tab.options.cd_yoki_hoso == cd_yoki_hoso) {
                        _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.nm_yoki_hoso = target.val();
                        return;
                    }
                    var text = App.messages.app.AP0133;
                    return ((_300_SeihoBunshoSakusei_YoukiHousou_Tab.isChange)
                            ? page.dialogs.confirmDialog.confirm({ text: text }) : App.async.success()).then(function () {

                                _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.nm_yoki_hoso = target.val();
                                _300_SeihoBunshoSakusei_YoukiHousou_Tab.hData.nm_yoki_hoso = _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.nm_yoki_hoso;
                                _300_SeihoBunshoSakusei_YoukiHousou_Tab.element.findP("cd_yoki_hoso").text(cd_yoki_hoso);
                                _300_SeihoBunshoSakusei_YoukiHousou_Tab.searchCopy(cd_yoki_hoso, true);
                                _300_SeihoBunshoSakusei_YoukiHousou_Tab.isChange = false;
                                page.setIsChangeValue();
                            }).fail(function () {
                                //_300_SeihoBunshoSakusei_YoukiHousou_Tab.isRevertChange = true;
                                target.val(_300_SeihoBunshoSakusei_YoukiHousou_Tab.options.nm_yoki_hoso);
                                //target.val(_300_SeihoBunshoSakusei_YoukiHousou_Tab.options.cd_yoki_hoso).change();
                            }).always(function () {
                            });
                })
            } else {
                _300_SeihoBunshoSakusei_YoukiHousou_Tab.isChange = true;
            }


            var isShizaiData = (target.closest("table.shizai").length > 0 && property != "nm_free_title_komoku"),
                id = isShizaiData ? target.closest("td").attr("data-key") : _300_SeihoBunshoSakusei_YoukiHousou_Tab.element.attr("data-key");
            if (!id) {
                return;
            }
            var entity = isShizaiData ? _300_SeihoBunshoSakusei_YoukiHousou_Tab.shizaiData.entry(id) :
                                        _300_SeihoBunshoSakusei_YoukiHousou_Tab.hosoData.entry(id);
                
            var index = Number(target.closest("[data-col]").attr("data-col"));
            if (index == 0) {
                index = 1;
            }
            _300_SeihoBunshoSakusei_YoukiHousou_Tab.validator[index - 1].validate({
                targets: target,
                filter: page.options.changeValidationFilter
            }).then(function () {
                page.setIsChangeValue();
                switch (property) {
                    case "nm_free_title_komoku":
                        _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.nm_free_title_komoku = target.val();
                        entity[property] = target.val();
                        break;
                    default:
                        if (target.closest(".datalist-content").length) {
                            entity[property] = target.val();
                            entity[property.replace("nm", "cd")] = page.getValueFromList(target);
                        } else {
                            entity[property] = target.val();
                        }
                        break;
                }
            })
        }

        _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.validations = {};

        /**
         * 検索ダイアログ　バリデーションルールを定義します。
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.validations_tmp = {
            cd_yoki_hoso: {
                rules: {
                    //required: true
                },
                options: {
                    name: "容器包装"
                },
                messages: {
                    required: App.messages.base.required
                }
            },
            nm_yoki_hoso: {
                rules: {
                    //required_custom: function (value, param, otps, done) {
                    //    if (_300_SeihoBunshoSakusei_YoukiHousou_Tab.element.findP("cd_yoki_hoso").val())
                    //    {
                    //        done(!App.isUndefOrNullOrStrEmpty(value));
                    //    }
                    //    done(true);
                    //},
                    required: true,
                    maxbytelength: 80
                },
                options: {
                    name: "容器包装名",
                    byte: 40
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength,
                    required: App.messages.base.required
                }
            },
            nm_yoki_hoso_shizai01: {
                rules: {
                    //required: true,
                    maxlength: 100
                },
                options: {
                    name: "資材_1",
                    //byte: 30
                },
                messages: {
                    required: App.messages.base.required,
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_yoki_hoso_shizai02: {
                rules: {
                    //required: true,
                    maxlength: 100
                },
                options: {
                    name: "資材_2",
                    byte: 30
                },
                messages: {
                    required: App.messages.base.required,
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_yoki_hoso_shizai03: {
                rules: {
                    //required: true,
                    maxlength: 100
                },
                options: {
                    name: "資材_3",
                    byte: 30
                },
                messages: {
                    required: App.messages.base.required,
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_yoki_hoso_shizai04: {
                rules: {
                    //required: true,
                    maxlength: 100
                },
                options: {
                    name: "資材_4",
                    byte: 30
                },
                messages: {
                    required: App.messages.base.required,
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_yoki_hoso_shizai05: {
                rules: {
                    //required: true,
                    maxlength: 100
                },
                options: {
                    name: "資材_5",
                    byte: 30
                },
                messages: {
                    required: App.messages.base.required,
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_yoki_hoso_shizai06: {
                rules: {
                    //required: true,
                    maxlength: 100
                },
                options: {
                    name: "資材_6",
                    byte: 30
                },
                messages: {
                    required: App.messages.base.required,
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_yoki_hoso_shizai07: {
                rules: {
                    //required: true,
                    maxlength: 100
                },
                options: {
                    name: "資材_7",
                    byte: 30
                },
                messages: {
                    required: App.messages.base.required,
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_yoki_hoso_shizai08: {
                rules: {
                    //required: true,
                    maxlength: 100
                },
                options: {
                    name: "資材_8",
                    byte: 30
                },
                messages: {
                    required: App.messages.base.required,
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_yoki_hoso_shizai09: {
                rules: {
                    //required: true,
                    maxlength: 100
                },
                options: {
                    name: "資材_9",
                    byte: 30
                },
                messages: {
                    required: App.messages.base.required,
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_yoki_hoso_shizai10: {
                rules: {
                    //required: true,
                    maxlength: 100
                },
                options: {
                    name: "資材_10",
                    byte: 30
                },
                messages: {
                    required: App.messages.base.required,
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_maker01: {
                rules: {
                    maxlength: 80
                },
                options: {
                    name: "メーカー1",
                    //byte: 30,
                    isAutoParam: true
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_maker02: {
                rules: {
                    maxlength: 80
                },
                options: {
                    name: "メーカー2",
                    byte: 30,
                    isAutoParam: true
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_maker03: {
                rules: {
                    maxlength: 80
                },
                options: {
                    name: "メーカー3",
                    byte: 30,
                    isAutoParam: true
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_maker04: {
                rules: {
                    maxlength: 80
                },
                options: {
                    name: "メーカー4",
                    byte: 30,
                    isAutoParam: true
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_maker05: {
                rules: {
                    maxlength: 80
                },
                options: {
                    name: "メーカー5",
                    byte: 30,
                    isAutoParam: true
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
            no_shizai_kikakusho01: {
                rules: {
                    digits: true,
                    maxlength: 15
                },
                options: {
                    name: "資材規格書番号1",
                    isAutoParam: true
                },
                messages: {
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength
                }
            },
            no_shizai_kikakusho02: {
                rules: {
                    digits: true,
                    maxlength: 15
                },
                options: {
                    name: "資材規格書番号2",
                    isAutoParam: true
                },
                messages: {
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength
                }
            },
            no_shizai_kikakusho03: {
                rules: {
                    digits: true,
                    maxlength: 15
                },
                options: {
                    name: "資材規格書番号3",
                    isAutoParam: true
                },
                messages: {
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength
                }
            },
            no_shizai_kikakusho04: {
                rules: {
                    digits: true,
                    maxlength: 15
                },
                options: {
                    name: "資材規格書番号4",
                    isAutoParam: true
                },
                messages: {
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength
                }
            },
            no_shizai_kikakusho05: {
                rules: {
                    digits: true,
                    maxlength: 15
                },
                options: {
                    name: "資材規格書番号5",
                    isAutoParam: true
                },
                messages: {
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_zaishitsu01: {
                rules: {
                    maxlength: 80
                },
                options: {
                    name: "材質1",
                    byte: 30,
                    isAutoParam: true
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_zaishitsu02: {
                rules: {
                    maxlength: 80
                },
                options: {
                    name: "材質2",
                    byte: 30,
                    isAutoParam: true
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_zaishitsu03: {
                rules: {
                    maxlength: 80
                },
                options: {
                    name: "材質3",
                    byte: 30,
                    isAutoParam: true
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_zaishitsu04: {
                rules: {
                    maxlength: 80
                },
                options: {
                    name: "材質4",
                    byte: 30,
                    isAutoParam: true
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_zaishitsu05: {
                rules: {
                    maxlength: 80
                },
                options: {
                    name: "材質5",
                    byte: 30,
                    isAutoParam: true
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_recycle_hyoji01: {
                rules: {
                    maxlength: 80
                },
                options: {
                    name: "リサイクル表示1",
                    //byte: 30,
                    isAutoParam: true
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_recycle_hyoji02: {
                rules: {
                    maxlength: 80
                },
                options: {
                    name: "リサイクル表示2",
                    byte: 30,
                    isAutoParam: true
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_recycle_hyoji03: {
                rules: {
                    maxlength: 80
                },
                options: {
                    name: "リサイクル表示3",
                    byte: 30,
                    isAutoParam: true
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_recycle_hyoji04: {
                rules: {
                    maxlength: 80
                },
                options: {
                    name: "リサイクル表示4",
                    byte: 30,
                    isAutoParam: true
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_recycle_hyoji05: {
                rules: {
                    maxlength: 80
                },
                options: {
                    name: "リサイクル表示5",
                    byte: 30,
                    isAutoParam: true
                },
                messages: {
                    maxlength: App.messages.base.maxlength
                }
            },
            nm_size01: {
                rules: {
                    maxbytelength: 80
                },
                options: {
                    name: "サイズ1",
                    byte: 40,
                    isAutoParam: true
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            nm_size02: {
                rules: {
                    maxbytelength: 80
                },
                options: {
                    name: "サイズ2",
                    byte: 40,
                    isAutoParam: true
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            nm_size03: {
                rules: {
                    maxbytelength: 80
                },
                options: {
                    name: "サイズ3",
                    byte: 40,
                    isAutoParam: true
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            nm_size04: {
                rules: {
                    maxbytelength: 80
                },
                options: {
                    name: "サイズ4",
                    byte: 40,
                    isAutoParam: true
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            nm_size05: {
                rules: {
                    maxbytelength: 80
                },
                options: {
                    name: "サイズ5",
                    byte: 40,
                    isAutoParam: true
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            nm_free_title_komoku: {
                rules: {
                    maxbytelength: 30
                },
                options: {
                    name: "フリー項目タイトル",
                    byte: 15
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            nm_free_komoku: {
                rules: {
                    maxbytelength: 80
                },
                options: {
                    name: "フリー項目",
                    byte: 40,
                    isAutoParam: true
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            }
        };

        /**
         * Bind cd_yoki_hoso combobox
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.bindHosoMaster = function (filter) {
            var element = _300_SeihoBunshoSakusei_YoukiHousou_Tab.element;

            return $.ajax(App.ajax.odata.get(_300_SeihoBunshoSakusei_YoukiHousou_Tab.urls.searchHosoMaster, filter))
            .done(function (result) {

                // Bind [容器包装]
                var cd_yoki_hoso = element.findP("nm_yoki_hoso");
                page.selectList(cd_yoki_hoso, {
                    data: result.lstYokiHoso,
                    value: "cd_yoki_hoso",
                    text: "nm_yoki_hoso",
                    isDefaultOption: true
                });
                if (_300_SeihoBunshoSakusei_YoukiHousou_Tab.param.mode == "view") {
                    element.findP("nm_yoki_hoso").prop("disabled", true);
                }
                //cd_yoki_hoso.children().remove();
                //App.ui.appendOptions(
                //    cd_yoki_hoso,
                //    "cd_yoki_hoso",
                //    "nm_yoki_hoso",
                //    result.lstYokiHoso,
                //    true
                //);
                //cd_yoki_hoso.val(result.cd_yoki_hoso);
                //cd_yoki_hoso.selectize({
                //    createOnBlur: true,
                //    create: true,
                //    displayTooltip: true,
                //    allowEmptyOption: true,
                //    removeFreeOptionWhenAddOption: true,
                //    firstEmptyResult: true,
                //    plugins: ['continue_editing'],
                //    //freeCode: _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.defaultValue.yokiHoso.cd_yoki_hoso
                //});
                element.findP("cd_yoki_hoso").text(result.cd_yoki_hoso);
                element.findP("nm_yoki_hoso").val(result.nm_yoki_hoso);
                _300_SeihoBunshoSakusei_YoukiHousou_Tab.yokiHosoMaster = {};
                $.each(result.lstYokiHoso, function (ind, item) {
                    if (item.cd_yoki_hoso != _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.defaultValue.yokiHoso.cd_yoki_hoso) {
                        _300_SeihoBunshoSakusei_YoukiHousou_Tab.yokiHosoMaster[item.cd_yoki_hoso] = item.nm_yoki_hoso;
                    }
                });
            });
        }

        /**
         * Prepair process before search
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.beforeSearch = function () {
            var element = _300_SeihoBunshoSakusei_YoukiHousou_Tab.element,
                param = _300_SeihoBunshoSakusei_YoukiHousou_Tab.param,
                defHeaderData = {
                    no_seiho: param.no_seiho,
                    cd_yoki_hoso: _300_SeihoBunshoSakusei_YoukiHousou_Tab.getCdYokiHoso()
                };
            _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.filter = _300_SeihoBunshoSakusei_YoukiHousou_Tab.createFilter();

            _300_SeihoBunshoSakusei_YoukiHousou_Tab.shizaiData = App.ui.page.dataSet();
            _300_SeihoBunshoSakusei_YoukiHousou_Tab.hosoData = App.ui.page.dataSet();
            _300_SeihoBunshoSakusei_YoukiHousou_Tab.hData = defHeaderData;
            _300_SeihoBunshoSakusei_YoukiHousou_Tab.hosoData.add(defHeaderData);

            // Search Combobox data
            _300_SeihoBunshoSakusei_YoukiHousou_Tab.bindHosoMaster(_300_SeihoBunshoSakusei_YoukiHousou_Tab.options.filter)
            .then(function (result) {
                // Load master data
                _300_SeihoBunshoSakusei_YoukiHousou_Tab.loadMasterData().then(function () {
                    var modes = page.options.mode;
                    // Search Tab data
                    switch (param.mode) {
                        case modes.new:
                            page.finishLoading("TAB_Bind", "TAB2", 5);
                            break;
                        case modes.new_copy:
                        case modes.edit:
                        case modes.edit_copy:
                        case modes.view:
                            cd_yoki_hoso = _300_SeihoBunshoSakusei_YoukiHousou_Tab.getCdYokiHoso();
                            //if (cd_yoki_hoso) {
                                _300_SeihoBunshoSakusei_YoukiHousou_Tab.search(cd_yoki_hoso);
                            //} else {
                            //    page.finishLoading("TAB_Bind", "TAB2", 5);
                            //}
                            break;
                    }
                })
            });

        }

        /**
         * Get current value of cd_yoki_hoso
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.getCdYokiHoso = function () {
            var element = _300_SeihoBunshoSakusei_YoukiHousou_Tab.element,
                yoki_hoso = element.findP("cd_yoki_hoso"),
                currentHoso = yoki_hoso.text();
            return currentHoso;
        }

        /**
         * Load master data (not append to combobox)
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.loadMasterData = function () {
            var element = _300_SeihoBunshoSakusei_YoukiHousou_Tab.element;

            _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.filter = _300_SeihoBunshoSakusei_YoukiHousou_Tab.createFilter();
            return $.ajax(App.ajax.odata.get(_300_SeihoBunshoSakusei_YoukiHousou_Tab.urls.searchMasterData, _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.filter)).then(function (result) {
                _300_SeihoBunshoSakusei_YoukiHousou_Tab.freeMasterData = result;
            });
        }

        /**
         * Rebind master data when change cd_yoki_hoso
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.bindMasterData = function (cd_yoki_hoso) {
            var element = _300_SeihoBunshoSakusei_YoukiHousou_Tab.element;
            $.each(_300_SeihoBunshoSakusei_YoukiHousou_Tab.options.lstMaster, function (ind, property) {
                var targets = element.find("tr[data-master='" + property + "'] .datalist-content"),
                    data = $.grep(_300_SeihoBunshoSakusei_YoukiHousou_Tab.freeMasterData, function (item) {
                        return (item.property == property);
                    });
                if (cd_yoki_hoso == _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.defaultValue.yokiHoso.cd_yoki_hoso) {
                    //App.ui.appendOptions(
                    //    targets,
                    //    "name",
                    //    "name",
                    //    data,
                    //    true
                    //);
                    page.appendOptions2(targets, data, "name", "name", true, true);
                }
            });
            // Reset all data
            element.find("input, textarea").val("");
            return App.async.success();
        }

        /**
         * 検索ダイアログの検索処理を実行します。
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.search = function (cd_yoki_hoso, isChangeYokihoso) {
            var element = _300_SeihoBunshoSakusei_YoukiHousou_Tab.element;

            isChangeYokihoso = false;
            _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.filter = _300_SeihoBunshoSakusei_YoukiHousou_Tab.createFilter(isChangeYokihoso);
            
            _300_SeihoBunshoSakusei_YoukiHousou_Tab.notifyAlert.clear();
            if (isChangeYokihoso) {
                App.ui.loading.show();
            }
            // Bind master data
            
            var url = _300_SeihoBunshoSakusei_YoukiHousou_Tab.urls.searchYokiDetail;
            if (cd_yoki_hoso) {
                _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.filter.cd_yoki_hoso = cd_yoki_hoso;
            }
            return $.ajax(App.ajax.odata.get(url, _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.filter)).then(function (result) {
                var data = _300_SeihoBunshoSakusei_YoukiHousou_Tab.prepairBindingData(result);
                _300_SeihoBunshoSakusei_YoukiHousou_Tab.bind(data, isChangeYokihoso);
            }).always(function () {
                
            });
        };

        /**
         * 検索ダイアログの検索処理を実行します。
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.searchCopy = function (cd_yoki_hoso, isChangeYokihoso) {
            var element = _300_SeihoBunshoSakusei_YoukiHousou_Tab.element;

            _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.filter = _300_SeihoBunshoSakusei_YoukiHousou_Tab.createFilter(isChangeYokihoso);

            _300_SeihoBunshoSakusei_YoukiHousou_Tab.notifyAlert.clear();
            if (isChangeYokihoso) {
                App.ui.loading.show();
            }
            // Bind master data

            var url = _300_SeihoBunshoSakusei_YoukiHousou_Tab.urls.searchCopy;
            if (cd_yoki_hoso) {
                _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.filter.cd_yoki_hoso = cd_yoki_hoso;
            }
            return $.ajax(App.ajax.odata.get(url, _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.filter)).then(function (result) {
                if (isChangeYokihoso && result && result.headerData) {
                    result.headerData.nm_yoki_hoso = _300_SeihoBunshoSakusei_YoukiHousou_Tab.hData.nm_yoki_hoso;
                }
                var data = _300_SeihoBunshoSakusei_YoukiHousou_Tab.prepairCopyData(result);
                //_300_SeihoBunshoSakusei_YoukiHousou_Tab.changeYokihosoCopy();
                _300_SeihoBunshoSakusei_YoukiHousou_Tab.bind(data, isChangeYokihoso);
            }).always(function () {

            });
        };

        /**
         * Change display of cd_yoki_hoso in copy mode
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.changeYokihosoCopy = function () {
            var data = _300_SeihoBunshoSakusei_YoukiHousou_Tab.hData;
            if (data) {
                var target = _300_SeihoBunshoSakusei_YoukiHousou_Tab.element.findP("nm_yoki_hoso");
                _300_SeihoBunshoSakusei_YoukiHousou_Tab.element.findP("cd_yoki_hoso").text(data.cd_yoki_hoso);
            }
        }

        /**
         * 検索ダイアログの検索条件を組み立てます
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.createFilter = function (isChangeYokihoso) {
            var param = _300_SeihoBunshoSakusei_YoukiHousou_Tab.param,
                settings = App.settings.app;
            return {
                no_seiho: param.no_seiho,
                no_seiho_copy: param.no_seiho_copy,
                mode: param.mode,
                cd_yoki_hoso: _300_SeihoBunshoSakusei_YoukiHousou_Tab.getCdYokiHoso(),
                cd_yoki_hoso_free: _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.defaultValue.yokiHoso.cd_yoki_hoso,
                cd_category_maker: settings.cd_category.kbn_maker,
                cd_category_zaishitsu: settings.cd_category.kbn_zaishitsu,
                cd_category_recycle: settings.cd_category.kbn_recycle_hyoji,
                isChangeYokihoso: isChangeYokihoso
            };
        };

        /**
         * Merge data with copy data
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.mergeCopyData = function (copyHeader, copyDetail) {
            var curHeader = _300_SeihoBunshoSakusei_YoukiHousou_Tab.hData;

            if (curHeader && copyHeader) {
                curHeader.cd_yoki_hoso = copyHeader.cd_yoki_hoso || "";
                curHeader.nm_yoki_hoso = copyHeader.nm_yoki_hoso || "";
                curHeader.cd_yoki_hoso_shizai01 = copyHeader.cd_yoki_hoso_shizai01 || "";
                curHeader.nm_yoki_hoso_shizai01 = copyHeader.nm_yoki_hoso_shizai01 || "";
                curHeader.cd_yoki_hoso_shizai02 = copyHeader.cd_yoki_hoso_shizai02 || "";
                curHeader.nm_yoki_hoso_shizai02 = copyHeader.nm_yoki_hoso_shizai02 || "";
                curHeader.cd_yoki_hoso_shizai03 = copyHeader.cd_yoki_hoso_shizai03 || "";
                curHeader.nm_yoki_hoso_shizai03 = copyHeader.nm_yoki_hoso_shizai03 || "";
                curHeader.cd_yoki_hoso_shizai04 = copyHeader.cd_yoki_hoso_shizai04 || "";
                curHeader.nm_yoki_hoso_shizai04 = copyHeader.nm_yoki_hoso_shizai04 || "";
                curHeader.cd_yoki_hoso_shizai05 = copyHeader.cd_yoki_hoso_shizai05 || "";
                curHeader.nm_yoki_hoso_shizai05 = copyHeader.nm_yoki_hoso_shizai05 || "";
                curHeader.cd_yoki_hoso_shizai06 = copyHeader.cd_yoki_hoso_shizai06 || "";
                curHeader.nm_yoki_hoso_shizai06 = copyHeader.nm_yoki_hoso_shizai06 || "";
                curHeader.cd_yoki_hoso_shizai07 = copyHeader.cd_yoki_hoso_shizai07 || "";
                curHeader.nm_yoki_hoso_shizai07 = copyHeader.nm_yoki_hoso_shizai07 || "";
                curHeader.cd_yoki_hoso_shizai08 = copyHeader.cd_yoki_hoso_shizai08 || "";
                curHeader.nm_yoki_hoso_shizai08 = copyHeader.nm_yoki_hoso_shizai08 || "";
                curHeader.cd_yoki_hoso_shizai09 = copyHeader.cd_yoki_hoso_shizai09 || "";
                curHeader.nm_yoki_hoso_shizai09 = copyHeader.nm_yoki_hoso_shizai09 || "";
                curHeader.cd_yoki_hoso_shizai10 = copyHeader.cd_yoki_hoso_shizai10 || "";
                curHeader.nm_yoki_hoso_shizai10 = copyHeader.nm_yoki_hoso_shizai10 || "";
                curHeader.nm_free_title_komoku = copyHeader.nm_free_title_komoku || "";
            }
            if (copyDetail) {
                var currentDetail = _300_SeihoBunshoSakusei_YoukiHousou_Tab.detailData.findAll(function (item, entity) {
                    return entity.state === App.ui.page.dataSet.status.Added;
                });
                $.each(currentDetail, function (ind, item) {
                    _300_SeihoBunshoSakusei_YoukiHousou_Tab.detailData.remove(item);
                });
                $.each(copyDetail, function (ind, item) {
                    _300_SeihoBunshoSakusei_YoukiHousou_Tab.detailData.add(item);
                });
            }
            return {
                headerData: curHeader,
                detailData: copyDetail
            }

        };

        /**
         * Fill miss data
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.fillDetailData = function (headerData, detailData, oldDetailData) {
            var detailCount = detailData ? detailData.length : 0,
                modes = page.options.mode,
                mode = _300_SeihoBunshoSakusei_YoukiHousou_Tab.param.mode;
            for (var i = 0; i < 10; i++)
            {
                // Add new row to 10 rows
                if (detailCount <= i) {
                    detailData.push(_300_SeihoBunshoSakusei_YoukiHousou_Tab.createShizaiData(headerData.cd_yoki_hoso))
                } else if (detailData[i] == null) {
                    // Replace null data
                    detailData[i] = _300_SeihoBunshoSakusei_YoukiHousou_Tab.createShizaiData(headerData.cd_yoki_hoso);
                }
                // Megre old detail data
                if (mode != modes.new_copy && mode != modes.edit_copy) {
                    if (oldDetailData && oldDetailData.length && oldDetailData.length > i) {
                        detailData[i].nm_free_komoku = oldDetailData[i].nm_free_komoku;
                    }
                }
            }
            return detailData;
        }

        /**
         * Prepair binding data in copy mode
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.prepairCopyData = function (copyData) {
            copyData = copyData || {};
            var param = _300_SeihoBunshoSakusei_YoukiHousou_Tab.param,
                dDataSet = _300_SeihoBunshoSakusei_YoukiHousou_Tab.shizaiData,
                hDataSet = _300_SeihoBunshoSakusei_YoukiHousou_Tab.hosoData;
            _300_SeihoBunshoSakusei_YoukiHousou_Tab.mergeCopyData(copyData.headerData);

            // Remomve current detail data
            var currentData = dDataSet.findAll(function (item, entity) {
                return entity.state === App.ui.page.dataSet.status.Added;
            });
            $.each(currentData, function (ind, item) {
                dDataSet.remove(item);
            });
            // Fill miss data
            if (copyData.detailData && copyData.headerData) {
                copyData.detailData = _300_SeihoBunshoSakusei_YoukiHousou_Tab.fillDetailData(copyData.headerData, copyData.detailData)
            }
            // Add copy detail
            $.each(copyData.detailData, function (ind, item) {
                dDataSet.add(item);
            });
            return {
                headerData: _300_SeihoBunshoSakusei_YoukiHousou_Tab.hData,
                detailData: copyData.detailData
            }
        }

        /**
         * Prepair binding data in normal mode
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.prepairBindingData = function (searchData) {
            searchData = searchData || {};
            var param = _300_SeihoBunshoSakusei_YoukiHousou_Tab.param,
                modes = page.options.mode,
                headerData = searchData.headerData || {},
                detailData = searchData.detailData || [],
                hDataSet = App.ui.page.dataSet(),
                dDataSet = App.ui.page.dataSet();

            detailData = _300_SeihoBunshoSakusei_YoukiHousou_Tab.fillDetailData(headerData, detailData, searchData.detailDataOld);

            _300_SeihoBunshoSakusei_YoukiHousou_Tab.hData = headerData;
            _300_SeihoBunshoSakusei_YoukiHousou_Tab.dData = detailData;

            _300_SeihoBunshoSakusei_YoukiHousou_Tab.hosoData = hDataSet;
            _300_SeihoBunshoSakusei_YoukiHousou_Tab.shizaiData = dDataSet;
            switch (param.mode) {
                case modes.new_copy:
                    _300_SeihoBunshoSakusei_YoukiHousou_Tab.mergeCopyData(searchData.headerDataCopy);
                    //_300_SeihoBunshoSakusei_YoukiHousou_Tab.changeYokihosoCopy();
                case modes.new:
                    hDataSet.add(headerData);
                    $.each(detailData, function (ind, item) {
                        dDataSet.add(item);
                    });
                    break;
                case modes.edit_copy:
                case modes.edit:
                    var removeHeader = $.extend({}, headerData);
                    _300_SeihoBunshoSakusei_YoukiHousou_Tab.mergeCopyData(searchData.headerDataCopy);
                    hDataSet.attach(removeHeader);
                    hDataSet.remove(removeHeader);
                    hDataSet.add(headerData);
                    // Back up meta data of shizai columns
                    if (searchData.detailDataOld && searchData.detailDataOld.length) {
                        _300_SeihoBunshoSakusei_YoukiHousou_Tab.shizaiMeta = {
                            cd_toroku: searchData.detailDataOld[0].cd_toroku,
                            dt_toroku: searchData.detailDataOld[0].dt_toroku
                        }
                    }
                    $.each(searchData.detailDataOld, function (ind, item) {
                        dDataSet.attach(item);
                        dDataSet.remove(item);
                    });
                    $.each(detailData, function (ind, item) {
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
         * Applier for selectize combobox
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.selectizeApplier = function (value, element) {
            element.setValue(value);
            return true;
        }

        /**
         * Binding applier
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.bindOpitions = {
            applies: {
                nm_yoki_hoso_shizai01: _300_SeihoBunshoSakusei_YoukiHousou_Tab.selectizeApplier
            }
        }

        /**
         * Bind header data
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.bindHeaderData = function (data) {
            data = data || {};
            var element = _300_SeihoBunshoSakusei_YoukiHousou_Tab.element;
            // Bind to cd_yoki_hoso_shizai cbb
            if (data.cd_yoki_hoso != _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.defaultValue.yokiHoso.cd_yoki_hoso) {
                for (var i = 0; i < 10 ; i++) {
                    var ind = (i < 9 ? "0" : "") + (i + 1);
                    var nm_prop = "nm_yoki_hoso_shizai" + ind,
                        cd_prop = "cd_yoki_hoso_shizai" + ind,
                        target = element.findP(nm_prop);

                    App.ui.appendOptions(
                        target,
                        "name",
                        "name",
                        [{
                            code: data[cd_prop] || "",
                            name: data[nm_prop] || ""
                        }]
                    );
                }
            }
            // Bind header data
            element.form(_300_SeihoBunshoSakusei_YoukiHousou_Tab.bindOpitions).bind(data);
        }

        /**
         * Create new detail row data
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.createShizaiData = function (cd_yoki_hoso) {
            return {
                no_seiho: _300_SeihoBunshoSakusei_YoukiHousou_Tab.param.no_seiho,
                cd_yoki_hoso: cd_yoki_hoso
            }
        }

        /**
         * Bind detail data
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.bindDetailData = function (data, cd_yoki_hoso, isChangeYokihoso) {
            data = data || [];
            var element = _300_SeihoBunshoSakusei_YoukiHousou_Tab.element;
            for (var i = 0; i < 10; i++) {
                var dData = data[i] || { no_seiho: _300_SeihoBunshoSakusei_YoukiHousou_Tab.param.no_seiho };
                var displayData = $.extend({}, dData);
                var element = _300_SeihoBunshoSakusei_YoukiHousou_Tab.element;
                var targetCol = element.find("td[data-col='" + (i + 1) + "']");
                if (!dData.cd_yoki_hoso_shizai) {
                    dData.cd_yoki_hoso_shizai = 9000 + i + 1;
                }
                // Bind to detail cbb
                if (cd_yoki_hoso == _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.defaultValue.yokiHoso.cd_yoki_hoso) {
                    // メーカー
                    displayData.cd_maker01 = displayData.nm_maker01;
                    displayData.cd_maker02 = displayData.nm_maker02;
                    displayData.cd_maker03 = displayData.nm_maker03;
                    displayData.cd_maker04 = displayData.nm_maker04;
                    displayData.cd_maker05 = displayData.nm_maker05;
                    // 材質
                    displayData.cd_zaishitsu01 = displayData.nm_zaishitsu01;
                    displayData.cd_zaishitsu02 = displayData.nm_zaishitsu02;
                    displayData.cd_zaishitsu03 = displayData.nm_zaishitsu03;
                    displayData.cd_zaishitsu04 = displayData.nm_zaishitsu04;
                    displayData.cd_zaishitsu05 = displayData.nm_zaishitsu05;
                    // リサイクル表示
                    displayData.cd_recycle_hyoj01 = displayData.nm_recycle_hyoj01;
                    displayData.cd_recycle_hyoj02 = displayData.nm_recycle_hyoj02;
                    displayData.cd_recycle_hyoj03 = displayData.nm_recycle_hyoj03;
                    displayData.cd_recycle_hyoj04 = displayData.nm_recycle_hyoj04;
                    displayData.cd_recycle_hyoj05 = displayData.nm_recycle_hyoj05;
                } else {
                    var lstCombo = targetCol.find("select");

                    //yokiData[cd_yoki_hoso_shizai_name_prop] = shizaiData.nm_yoki_hoso_shizai;
                    $.each(lstCombo, function (ind, item) {
                        item = $(item);
                        var name_prop = item.attr("data-prop"),
                            prop = (name_prop || "").replace("nm", "cd"),
                            optData = [];

                        optData.push({
                            code: dData[prop] || "",
                            name: dData[name_prop] || ""
                        });
                        item.children().remove();
                        App.ui.appendOptions(
                            item,
                            "code",
                            "name",
                            optData,
                            false
                        );
                        displayData[name_prop] = dData[prop];
                    });
                }
                targetCol.form().bind(displayData);
                //if (!isChangeYokihoso) {
                //    targetCol.find("select").selectize(page.options.deafultSelectizeOptions);
                //}
            }
        }

        /**
         * Bind data after search
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.bind = function (data, isChangeYokihoso) {
            var element = _300_SeihoBunshoSakusei_YoukiHousou_Tab.element,
                param = _300_SeihoBunshoSakusei_YoukiHousou_Tab.param,
                cd_yoki_hoso = _300_SeihoBunshoSakusei_YoukiHousou_Tab.getCdYokiHoso();

            data = data || {};
            _300_SeihoBunshoSakusei_YoukiHousou_Tab.bindMasterData(cd_yoki_hoso).then(function () {
                _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.cd_yoki_hoso = cd_yoki_hoso;
                _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.nm_yoki_hoso = data.headerData.nm_yoki_hoso;
                // Bind header hoso data
                _300_SeihoBunshoSakusei_YoukiHousou_Tab.bindHeaderData(data.headerData);
                // Bind detail hoso data
                _300_SeihoBunshoSakusei_YoukiHousou_Tab.bindDetailData(data.detailData, cd_yoki_hoso, isChangeYokihoso);
                if (cd_yoki_hoso == _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.defaultValue.yokiHoso.cd_yoki_hoso && param.mode != "view") {
                    element.find("td input, textarea").prop("disabled", false);
                    element.find("td select").prop("disabled", false);
                } else {
                    element.find("td input").prop("disabled", true);
                    element.find("td select").prop("disabled", true);
                }
                if (param.mode != "view") {
                    element.find("textarea").prop("disabled", false);
                } else {
                    element.find("textarea").prop("disabled", true);
                }
                var targets = element.find("td select");
                //targets.selectize(page.options.deafultSelectizeOptions);
                if (isChangeYokihoso) {
                    App.ui.loading.close();
                    _300_SeihoBunshoSakusei_YoukiHousou_Tab.validateAll(page.options.changeValidationFilter);
                }
            });
            page.finishLoading("TAB_Bind", "TAB2", 5);
        }
        
        /**
         * Validate all before save
         * Too hard to understand ?? Ask the designer.
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.validateAll = function (filter, isRequired) {
            // check required nm_hoso_shizai when the columns has any data
            for (var i = 0; i < 10; i++) {
                var nm_yoki_hoso_shizai = "nm_yoki_hoso_shizai" + App.common.fillString(i + 1, 2);
                if (_300_SeihoBunshoSakusei_YoukiHousou_Tab.checkExistShizaiDetail(i)) {
                    _300_SeihoBunshoSakusei_YoukiHousou_Tab.validationList[0][nm_yoki_hoso_shizai].rules.required = true;
                } else {
                    delete _300_SeihoBunshoSakusei_YoukiHousou_Tab.validationList[0][nm_yoki_hoso_shizai].rules.required;
                }
            }
            // Check required if nothing is inputed
            if (_300_SeihoBunshoSakusei_YoukiHousou_Tab.checkExistShizai()) {
                //delete _300_SeihoBunshoSakusei_YoukiHousou_Tab.validationList[0].nm_yoki_hoso_shizai01.rules.required;
            } else {
                _300_SeihoBunshoSakusei_YoukiHousou_Tab.validationList[0].nm_yoki_hoso_shizai01.rules.required = true;
            }

            var validateList = [],
                targets = _300_SeihoBunshoSakusei_YoukiHousou_Tab.element.find(".datatable-header, .datatable-detail-header").find("[data-prop]");
            // Validate yoki_hoso
            validateList.push(_300_SeihoBunshoSakusei_YoukiHousou_Tab.validator[0].validate({
                    targets: targets,
                    filter: filter,
                    state: {
                        suppressMessage: false
                    }
                })
            );

            // Validate yoki_hoso_shizai
            for (var i = 0; i < 10; i++) {
                var targetRow = _300_SeihoBunshoSakusei_YoukiHousou_Tab.element.find("td[data-col='" + (i + 1) + "'] :input");
                validateList.push(
                    _300_SeihoBunshoSakusei_YoukiHousou_Tab.validator[i].validate({
                        targets: targetRow,
                        filter: filter,
                        state: {
                            suppressMessage: false
                        }
                    })
                );
            }
            // Validate all
            return App.async.all(validateList);
        }

        /**
         * Check if exists 1 detail shizai at least
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.checkExistShizai = function () {
            if (_300_SeihoBunshoSakusei_YoukiHousou_Tab.hosoData) {
                var id = _300_SeihoBunshoSakusei_YoukiHousou_Tab.element.attr("data-key");
                if (!id) {
                    return false;
                }
                var data = _300_SeihoBunshoSakusei_YoukiHousou_Tab.hosoData.entry(id);
                if (data && (
                    !App.isUndefOrNullOrStrEmpty(data.nm_yoki_hoso_shizai01) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_yoki_hoso_shizai02) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_yoki_hoso_shizai03) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_yoki_hoso_shizai04) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_yoki_hoso_shizai05) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_yoki_hoso_shizai06) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_yoki_hoso_shizai07) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_yoki_hoso_shizai08) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_yoki_hoso_shizai09) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_yoki_hoso_shizai10)
                    )) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Check if each shizai column has any data
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.checkExistShizaiDetail = function (colIndex) {
            colIndex += 1;
            var id = _300_SeihoBunshoSakusei_YoukiHousou_Tab.element.find("[data-col='" + colIndex + "']:first").attr("data-key");
            if (!id) {
                return false;
            }
            var data = _300_SeihoBunshoSakusei_YoukiHousou_Tab.shizaiData.entry(id);
            return (data && (
                    !App.isUndefOrNullOrStrEmpty(data.nm_maker01) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_maker02) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_maker03) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_maker04) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_maker05) ||
                    !App.isUndefOrNullOrStrEmpty(data.no_shizai_kikakusho01) ||
                    !App.isUndefOrNullOrStrEmpty(data.no_shizai_kikakusho02) ||
                    !App.isUndefOrNullOrStrEmpty(data.no_shizai_kikakusho03) ||
                    !App.isUndefOrNullOrStrEmpty(data.no_shizai_kikakusho04) ||
                    !App.isUndefOrNullOrStrEmpty(data.no_shizai_kikakusho05) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_zaishitsu01) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_zaishitsu02) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_zaishitsu03) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_zaishitsu04) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_zaishitsu05) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_recycle_hyoji01) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_recycle_hyoji02) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_recycle_hyoji03) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_recycle_hyoji04) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_recycle_hyoji05) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_size01) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_size02) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_size03) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_size04) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_size05) ||
                    !App.isUndefOrNullOrStrEmpty(data.nm_free_komoku)
                ));
        }

        /**
         * Get save data
         */
        _300_SeihoBunshoSakusei_YoukiHousou_Tab.getSaveData = function () {
            _300_SeihoBunshoSakusei_YoukiHousou_Tab.hosoData.findAll(function (item, entity) {
                $.each(item, function (prop, value) {
                    if (item[prop] === "") {
                        item[prop] = null;
                    }
                });
            });
            _300_SeihoBunshoSakusei_YoukiHousou_Tab.shizaiData.findAll(function (item, entity) {
                $.each(item, function (prop, value) {
                    if (item[prop] === "") {
                        item[prop] = null;
                    }
                });
            });
            var result = {
                headerData: _300_SeihoBunshoSakusei_YoukiHousou_Tab.hosoData.getChangeSet(),
                detailData: _300_SeihoBunshoSakusei_YoukiHousou_Tab.shizaiData.getChangeSet()
            }
            var header = null;
            if (result.headerData && result.headerData.created && result.headerData.created.length) {
                header = result.headerData.created[0];
                header.nm_yoki_hoso = _300_SeihoBunshoSakusei_YoukiHousou_Tab.element.findP("nm_yoki_hoso").val();
                //if (header.cd_yoki_hoso == _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.defaultValue.yokiHoso.cd_yoki_hoso) {
                //    $.extend(header, _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.defaultValue.yokiHoso);
                //}
                header = $.extend({}, _300_SeihoBunshoSakusei_YoukiHousou_Tab.options.defaultValue.yokiHoso, header);
                result.headerData.created[0] = header;
            }
            if (result.detailData && result.detailData.created && result.detailData.created.length) {
                var shizaiMeta = _300_SeihoBunshoSakusei_YoukiHousou_Tab.shizaiMeta || {};
                var i = 0;
                $.each(result.detailData.created, function (ind, item) {
                    item.cd_toroku = shizaiMeta.cd_toroku;
                    item.dt_toroku = shizaiMeta.dt_toroku;
                    if (header != null) {
                        header["cd_yoki_hoso_shizai" + App.common.fillString(i + 1, 2)] = item.cd_yoki_hoso_shizai;
                    }
                    i++;
                });
            } else {
                if (header && !header.cd_yoki_hoso) {
                    result.detailData.created = [];
                    for (var i = 0; i < 10; i++) {
                        result.detailData.created.push({
                            no_seiho: _300_SeihoBunshoSakusei_YoukiHousou_Tab.param.no_seiho,
                            cd_yoki_hoso: header.cd_yoki_hoso,
                            cd_yoki_hoso_shizai: 9001 + i
                        })
                    }
                }
            }

            return result;
        }

    </script>
<div class="tab-pane fade smaller" tabindex="-1" id="_300_SeihoBunshoSakusei_YoukiHousou_Tab">
    <div class="part sub-tab-content smaller">
        <div class="part-content">
            <!-- table header -->
            <table class="datatable datatable-header">
                <thead>
                    <tr class="th-frame">
                        <th></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                    </tr>
                    <tr>
                        <th class="left"><label>容器包装</label></th>
                        <th class="left width-column" colspan="10">
                            <input class="float-left center inp-lb" data-prop="cd_yoki_hoso" style="width: 35px; margin-left: 10px; margin-right: 10px; height: 21px; background-color: transparent" disabled="disabled" />
                            <select class="float-left" style="width: 200px!important;height: 22px !important;border: 1px solid #cccccc !important;" data-prop="nm_yoki_hoso" id="nm_yoki_hoso">
                                <option></option>
                            </select>
                        </th>
                    </tr>
                    <tr>
                        <th class="left"><label>No</label></th>
                        <th class="center">1</th>
                        <th class="center">2</th>
                        <th class="center">3</th>
                        <th class="center">4</th>
                        <th class="center">5</th>
                        <th class="center">6</th>
                        <th class="center">7</th>
                        <th class="center">8</th>
                        <th class="center">9</th>
                        <th class="center">10</th>
                    </tr>
                </thead>
            </table>
            <!-- table detail header 1 -->
            <table class="datatable datatable-detail-header">
                <thead>
                    <tr class="th-frame">
                        <th></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                    </tr>
                </thead>
                <tbody class="item-tmpl" style="cursor: default;">
                    <tr data-master="nm_yoki_hoso_shizai">
                        <td><label>資材</label></td>
                        <td class="width-column" data-col="">
                            <select data-prop="nm_yoki_hoso_shizai01" id="nm_yoki_hoso_shizai01"></select>
                        </td>
                        <td class="width-column" data-col="">
                            <select data-prop="nm_yoki_hoso_shizai02" id="nm_yoki_hoso_shizai02"></select>
                        </td>
                        <td class="width-column" data-col="">
                            <select data-prop="nm_yoki_hoso_shizai03" id="nm_yoki_hoso_shizai03"></select>
                        </td>
                        <td class="width-column" data-col="">
                            <select data-prop="nm_yoki_hoso_shizai04" id="nm_yoki_hoso_shizai04"></select>
                        </td>
                        <td class="width-column" data-col="">
                            <select data-prop="nm_yoki_hoso_shizai05" id="nm_yoki_hoso_shizai05"></select>
                        </td>
                        <td class="width-column" data-col="">
                            <select data-prop="nm_yoki_hoso_shizai06" id="nm_yoki_hoso_shizai06"></select>
                        </td>
                        <td class="width-column" data-col="">
                            <select data-prop="nm_yoki_hoso_shizai07" id="nm_yoki_hoso_shizai07"></select>
                        </td>
                        <td class="width-column" data-col="">
                            <select data-prop="nm_yoki_hoso_shizai08" id="nm_yoki_hoso_shizai08"></select>
                        </td>
                        <td class="width-column" data-col="">
                            <select data-prop="nm_yoki_hoso_shizai09" id="nm_yoki_hoso_shizai09"></select>
                        </td>
                        <td class="width-column" data-col="">
                            <select data-prop="nm_yoki_hoso_shizai10" id="nm_yoki_hoso_shizai10"></select>
                        </td>
                    </tr>
                </tbody>
            </table>

            <!-- table detail 1: メーカー -->
            <table class="datatable detail_1 shizai">
                <thead>
                    <tr class="th-frame">
                        <th></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                    </tr>
                </thead>
                <tbody class="item-tmpl" style="cursor: default;">
                    <tr data-master="nm_maker">
                        <td><label>メーカー1</label></td>
                        <td class="width-column" data-col="1">
                            <select data-prop="nm_maker01"></select>
                        </td>
                        <td class="width-column" data-col="2">
                            <select data-prop="nm_maker01"></select>
                        </td>
                        <td class="width-column" data-col="3">
                            <select data-prop="nm_maker01"></select>
                        </td>
                        <td class="width-column" data-col="4">
                            <select data-prop="nm_maker01"></select>
                        </td>
                        <td class="width-column" data-col="5">
                            <select data-prop="nm_maker01"></select>
                        </td>
                        <td class="width-column" data-col="6">
                            <select data-prop="nm_maker01"></select>
                        </td>
                        <td class="width-column" data-col="7">
                            <select data-prop="nm_maker01"></select>
                        </td>
                        <td class="width-column" data-col="8">
                            <select data-prop="nm_maker01"></select>
                        </td>
                        <td class="width-column" data-col="9">
                            <select data-prop="nm_maker01"></select>
                        </td>
                        <td class="width-column" data-col="10">
                            <select data-prop="nm_maker01"></select>
                        </td>
                    </tr>
                    <tr data-master="nm_maker">
                        <td><label>メーカー2</label></td>
                        <td class="width-column" data-col="1">
                            <select data-prop="nm_maker02"></select>
                        </td>
                        <td class="width-column" data-col="2">
                            <select data-prop="nm_maker02"></select>
                        </td>
                        <td class="width-column" data-col="3">
                            <select data-prop="nm_maker02"></select>
                        </td>
                        <td class="width-column" data-col="4">
                            <select data-prop="nm_maker02"></select>
                        </td>
                        <td class="width-column" data-col="5">
                            <select data-prop="nm_maker02"></select>
                        </td>
                        <td class="width-column" data-col="6">
                            <select data-prop="nm_maker02"></select>
                        </td>
                        <td class="width-column" data-col="7">
                            <select data-prop="nm_maker02"></select>
                        </td>
                        <td class="width-column" data-col="8">
                            <select data-prop="nm_maker02"></select>
                        </td>
                        <td class="width-column" data-col="9">
                            <select data-prop="nm_maker02"></select>
                        </td>
                        <td class="width-column" data-col="10">
                            <select data-prop="nm_maker02"></select>
                        </td>
                    </tr>
                    <tr data-master="nm_maker">
                        <td><label>メーカー3</label></td>
                        <td class="width-column" data-col="1">
                            <select data-prop="nm_maker03"></select>
                        </td>
                        <td class="width-column" data-col="2">
                            <select data-prop="nm_maker03"></select>
                        </td>
                        <td class="width-column" data-col="3">
                            <select data-prop="nm_maker03"></select>
                        </td>
                        <td class="width-column" data-col="4">
                            <select data-prop="nm_maker03"></select>
                        </td>
                        <td class="width-column" data-col="5">
                            <select data-prop="nm_maker03"></select>
                        </td>
                        <td class="width-column" data-col="6">
                            <select data-prop="nm_maker03"></select>
                        </td>
                        <td class="width-column" data-col="7">
                            <select data-prop="nm_maker03"></select>
                        </td>
                        <td class="width-column" data-col="8">
                            <select data-prop="nm_maker03"></select>
                        </td>
                        <td class="width-column" data-col="9">
                            <select data-prop="nm_maker03"></select>
                        </td>
                        <td class="width-column" data-col="10">
                            <select data-prop="nm_maker03"></select>
                        </td>
                    </tr>
                    <tr data-master="nm_maker">
                        <td><label>メーカー4</label></td>
                        <td class="width-column" data-col="1">
                            <select data-prop="nm_maker04"></select>
                        </td>
                        <td class="width-column" data-col="2">
                            <select data-prop="nm_maker04"></select>
                        </td>
                        <td class="width-column" data-col="3">
                            <select data-prop="nm_maker04"></select>
                        </td>
                        <td class="width-column" data-col="4">
                            <select data-prop="nm_maker04"></select>
                        </td>
                        <td class="width-column" data-col="5">
                            <select data-prop="nm_maker04"></select>
                        </td>
                        <td class="width-column" data-col="6">
                            <select data-prop="nm_maker04"></select>
                        </td>
                        <td class="width-column" data-col="7">
                            <select data-prop="nm_maker04"></select>
                        </td>
                        <td class="width-column" data-col="8">
                            <select data-prop="nm_maker04"></select>
                        </td>
                        <td class="width-column" data-col="9">
                            <select data-prop="nm_maker04"></select>
                        </td>
                        <td class="width-column" data-col="10">
                            <select data-prop="nm_maker04"></select>
                        </td>
                    </tr>
                    <tr data-master="nm_maker">
                        <td><label>メーカー5</label></td>
                        <td class="width-column" data-col="1">
                            <select data-prop="nm_maker05"></select>
                        </td>
                        <td class="width-column" data-col="2">
                            <select data-prop="nm_maker05"></select>
                        </td>
                        <td class="width-column" data-col="3">
                            <select data-prop="nm_maker05"></select>
                        </td>
                        <td class="width-column" data-col="4">
                            <select data-prop="nm_maker05"></select>
                        </td>
                        <td class="width-column" data-col="5">
                            <select data-prop="nm_maker05"></select>
                        </td>
                        <td class="width-column" data-col="6">
                            <select data-prop="nm_maker05"></select>
                        </td>
                        <td class="width-column" data-col="7">
                            <select data-prop="nm_maker05"></select>
                        </td>
                        <td class="width-column" data-col="8">
                            <select data-prop="nm_maker05"></select>
                        </td>
                        <td class="width-column" data-col="9">
                            <select data-prop="nm_maker05"></select>
                        </td>
                        <td class="width-column" data-col="10">
                            <select data-prop="nm_maker05"></select>
                        </td>
                    </tr>
                </tbody>
            </table>


            <!-- table detail 2: 資材規格書番号 -->
            <table class="datatable detail_2 shizai">
                <thead>
                    <tr class="th-frame">
                        <th></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                    </tr>
                </thead>
                <tbody class="item-tmpl" style="cursor: default;">
                    <tr>
                        <td><label>資材規格書番号1</label></td>
                        <td class="width-column" data-col="1">
                            <input type="tel" data-prop="no_shizai_kikakusho01" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="2">
                            <input type="tel" data-prop="no_shizai_kikakusho01" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="3">
                            <input type="tel" data-prop="no_shizai_kikakusho01" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="4">
                            <input type="tel" data-prop="no_shizai_kikakusho01" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="5">
                            <input type="tel" data-prop="no_shizai_kikakusho01" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="6">
                            <input type="tel" data-prop="no_shizai_kikakusho01" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="7">
                            <input type="tel" data-prop="no_shizai_kikakusho01" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="8">
                            <input type="tel" data-prop="no_shizai_kikakusho01" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="9">
                            <input type="tel" data-prop="no_shizai_kikakusho01" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="10">
                            <input type="tel" data-prop="no_shizai_kikakusho01" class="limit-input-int" maxlength="15" />
                        </td>
                    </tr>
                    <tr>
                        <td><label>資材規格書番号2</label></td>
                        <td class="width-column" data-col="1">
                            <input type="tel" data-prop="no_shizai_kikakusho02" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="2">
                            <input type="tel" data-prop="no_shizai_kikakusho02" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="3">
                            <input type="tel" data-prop="no_shizai_kikakusho02" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="4">
                            <input type="tel" data-prop="no_shizai_kikakusho02" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="5">
                            <input type="tel" data-prop="no_shizai_kikakusho02" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="6">
                            <input type="tel" data-prop="no_shizai_kikakusho02" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="7">
                            <input type="tel" data-prop="no_shizai_kikakusho02" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="8">
                            <input type="tel" data-prop="no_shizai_kikakusho02" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="9">
                            <input type="tel" data-prop="no_shizai_kikakusho02" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="10">
                            <input type="tel" data-prop="no_shizai_kikakusho02" class="limit-input-int" maxlength="15" />
                        </td>
                    </tr>
                    <tr>
                        <td><label>資材規格書番号3</label></td>
                        <td class="width-column" data-col="1">
                            <input type="tel" data-prop="no_shizai_kikakusho03" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="2">
                            <input type="tel" data-prop="no_shizai_kikakusho03" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="3">
                            <input type="tel" data-prop="no_shizai_kikakusho03" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="4">
                            <input type="tel" data-prop="no_shizai_kikakusho03" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="5">
                            <input type="tel" data-prop="no_shizai_kikakusho03" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="6">
                            <input type="tel" data-prop="no_shizai_kikakusho03" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="7">
                            <input type="tel" data-prop="no_shizai_kikakusho03" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="8">
                            <input type="tel" data-prop="no_shizai_kikakusho03" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="9">
                            <input type="tel" data-prop="no_shizai_kikakusho03" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="10">
                            <input type="tel" data-prop="no_shizai_kikakusho03" class="limit-input-int" maxlength="15" />
                        </td>
                    </tr>
                    <tr>
                        <td><label>資材規格書番号4</label></td>
                        <td class="width-column" data-col="1">
                            <input type="tel" data-prop="no_shizai_kikakusho04" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="2">
                            <input type="tel" data-prop="no_shizai_kikakusho04" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="3">
                            <input type="tel" data-prop="no_shizai_kikakusho04" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="4">
                            <input type="tel" data-prop="no_shizai_kikakusho04" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="5">
                            <input type="tel" data-prop="no_shizai_kikakusho04" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="6">
                            <input type="tel" data-prop="no_shizai_kikakusho04" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="7">
                            <input type="tel" data-prop="no_shizai_kikakusho04" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="8">
                            <input type="tel" data-prop="no_shizai_kikakusho04" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="9">
                            <input type="tel" data-prop="no_shizai_kikakusho04" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="10">
                            <input type="tel" data-prop="no_shizai_kikakusho04" class="limit-input-int" maxlength="15" />
                        </td>
                    </tr>
                    <tr>
                        <td><label>資材規格書番号5</label></td>
                        <td class="width-column" data-col="1">
                            <input type="tel" data-prop="no_shizai_kikakusho05" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="2">
                            <input type="tel" data-prop="no_shizai_kikakusho05" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="3">
                            <input type="tel" data-prop="no_shizai_kikakusho05" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="4">
                            <input type="tel" data-prop="no_shizai_kikakusho05" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="5">
                            <input type="tel" data-prop="no_shizai_kikakusho05" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="6">
                            <input type="tel" data-prop="no_shizai_kikakusho05" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="7">
                            <input type="tel" data-prop="no_shizai_kikakusho05" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="8">
                            <input type="tel" data-prop="no_shizai_kikakusho05" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="9">
                            <input type="tel" data-prop="no_shizai_kikakusho05" class="limit-input-int" maxlength="15" />
                        </td>
                        <td class="width-column" data-col="10">
                            <input type="tel" data-prop="no_shizai_kikakusho05" class="limit-input-int" maxlength="15" />
                        </td>
                    </tr>
                </tbody>
            </table>


            <!-- table detail 3: 材質 -->
            <table class="datatable detail_3 shizai">
                <thead>
                    <tr class="th-frame">
                        <th></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                    </tr>
                </thead>
                <tbody class="item-tmpl" style="cursor: default;">
                    <tr data-master="nm_zaishitsu">
                        <td><label>材質1</label></td>
                        <td class="width-column" data-col="1">
                            <select data-prop="nm_zaishitsu01"></select>
                        </td>
                        <td class="width-column" data-col="2">
                            <select data-prop="nm_zaishitsu01"></select>
                        <td class="width-column" data-col="3">
                            <select data-prop="nm_zaishitsu01"></select>
                        </td>
                        <td class="width-column" data-col="4">
                            <select data-prop="nm_zaishitsu01"></select>
                        </td>
                        <td class="width-column" data-col="5">
                            <select data-prop="nm_zaishitsu01"></select>
                        </td>
                        <td class="width-column" data-col="6">
                            <select data-prop="nm_zaishitsu01"></select>
                        </td>
                        <td class="width-column" data-col="7">
                            <select data-prop="nm_zaishitsu01"></select>
                        </td>
                        <td class="width-column" data-col="8">
                            <select data-prop="nm_zaishitsu01"></select>
                        </td>
                        <td class="width-column" data-col="9">
                            <select data-prop="nm_zaishitsu01"></select>
                        </td>
                        <td class="width-column" data-col="10">
                            <select data-prop="nm_zaishitsu01"></select>
                        </td>
                    </tr>
                    <tr data-master="nm_zaishitsu">
                        <td><label>材質2</label></td>
                        <td class="width-column" data-col="1">
                            <select data-prop="nm_zaishitsu02"></select>
                        </td>
                        <td class="width-column" data-col="2">
                            <select data-prop="nm_zaishitsu02"></select>
                        </td>
                        <td class="width-column" data-col="3">
                            <select data-prop="nm_zaishitsu02"></select>
                        </td>
                        <td class="width-column" data-col="4">
                            <select data-prop="nm_zaishitsu02"></select>
                        </td>
                        <td class="width-column" data-col="5">
                            <select data-prop="nm_zaishitsu02"></select>
                        </td>
                        <td class="width-column" data-col="6">
                            <select data-prop="nm_zaishitsu02"></select>
                        </td>
                        <td class="width-column" data-col="7">
                            <select data-prop="nm_zaishitsu02"></select>
                        </td>
                        <td class="width-column" data-col="8">
                            <select data-prop="nm_zaishitsu02"></select>
                        </td>
                        <td class="width-column" data-col="9">
                            <select data-prop="nm_zaishitsu02"></select>
                        </td>
                        <td class="width-column" data-col="10">
                            <select data-prop="nm_zaishitsu02"></select>
                        </td>
                    </tr>
                    <tr data-master="nm_zaishitsu">
                        <td><label>材質3</label></td>
                        <td class="width-column" data-col="1">
                            <select data-prop="nm_zaishitsu03"></select>
                        </td>
                        <td class="width-column" data-col="2">
                            <select data-prop="nm_zaishitsu03"></select>
                        </td>
                        <td class="width-column" data-col="3">
                            <select data-prop="nm_zaishitsu03"></select>
                        </td>
                        <td class="width-column" data-col="4">
                            <select data-prop="nm_zaishitsu03"></select>
                        </td>
                        <td class="width-column" data-col="5">
                            <select data-prop="nm_zaishitsu03"></select>
                        </td>
                        <td class="width-column" data-col="6">
                            <select data-prop="nm_zaishitsu03"></select>
                        </td>
                        <td class="width-column" data-col="7">
                            <select data-prop="nm_zaishitsu03"></select>
                        </td>
                        <td class="width-column" data-col="8">
                            <select data-prop="nm_zaishitsu03"></select>
                        </td>
                        <td class="width-column" data-col="9">
                            <select data-prop="nm_zaishitsu03"></select>
                        </td>
                        <td class="width-column" data-col="10">
                            <select data-prop="nm_zaishitsu03"></select>
                        </td>
                    </tr>
                    <tr data-master="nm_zaishitsu">
                        <td><label>材質4</label></td>
                        <td class="width-column" data-col="1">
                            <select data-prop="nm_zaishitsu04"></select>
                        </td>
                        <td class="width-column" data-col="2">
                            <select data-prop="nm_zaishitsu04"></select>
                        </td>
                        <td class="width-column" data-col="3">
                            <select data-prop="nm_zaishitsu04"></select>
                        </td>
                        <td class="width-column" data-col="4">
                            <select data-prop="nm_zaishitsu04"></select>
                        </td>
                        <td class="width-column" data-col="5">
                            <select data-prop="nm_zaishitsu04"></select>
                        </td>
                        <td class="width-column" data-col="6">
                            <select data-prop="nm_zaishitsu04"></select>
                        </td>
                        <td class="width-column" data-col="7">
                            <select data-prop="nm_zaishitsu04"></select>
                        </td>
                        <td class="width-column" data-col="8">
                            <select data-prop="nm_zaishitsu04"></select>
                        </td>
                        <td class="width-column" data-col="9">
                            <select data-prop="nm_zaishitsu04"></select>
                        </td>
                        <td class="width-column" data-col="10">
                            <select data-prop="nm_zaishitsu04"></select>
                        </td>
                    </tr>
                    <tr data-master="nm_zaishitsu">
                        <td><label>材質5</label></td>
                        <td class="width-column" data-col="1">
                            <select data-prop="nm_zaishitsu05"></select>
                        </td>
                        <td class="width-column" data-col="2">
                            <select data-prop="nm_zaishitsu05"></select>
                        </td>
                        <td class="width-column" data-col="3">
                            <select data-prop="nm_zaishitsu05"></select>
                        </td>
                        <td class="width-column" data-col="4">
                            <select data-prop="nm_zaishitsu05"></select>
                        </td>
                        <td class="width-column" data-col="5">
                            <select data-prop="nm_zaishitsu05"></select>
                        </td>
                        <td class="width-column" data-col="6">
                            <select data-prop="nm_zaishitsu05"></select>
                        </td>
                        <td class="width-column" data-col="7">
                            <select data-prop="nm_zaishitsu05"></select>
                        </td>
                        <td class="width-column" data-col="8">
                            <select data-prop="nm_zaishitsu05"></select>
                        </td>
                        <td class="width-column" data-col="9">
                            <select data-prop="nm_zaishitsu05"></select>
                        </td>
                        <td class="width-column" data-col="10">
                            <select data-prop="nm_zaishitsu05"></select>
                        </td>
                    </tr>
                </tbody>
            </table>


            <!-- table detail 4: リサイクル表示 -->
            <table class="datatable detail_4 shizai">
                <thead>
                    <tr class="th-frame">
                        <th></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                    </tr>
                </thead>
                <tbody class="item-tmpl" style="cursor: default;">
                    <tr data-master="nm_recycle_hyoji">
                        <td><label>リサイクル表示1</label></td>
                        <td class="width-column" data-col="1">
                            <select data-prop="nm_recycle_hyoji01"></select>
                        </td>
                        <td class="width-column" data-col="2">
                            <select data-prop="nm_recycle_hyoji01"></select>
                        </td>
                        <td class="width-column" data-col="3">
                            <select data-prop="nm_recycle_hyoji01"></select>
                        </td>
                        <td class="width-column" data-col="4">
                            <select data-prop="nm_recycle_hyoji01"></select>
                        </td>
                        <td class="width-column" data-col="5">
                            <select data-prop="nm_recycle_hyoji01"></select>
                        </td>
                        <td class="width-column" data-col="6">
                            <select data-prop="nm_recycle_hyoji01"></select>
                        </td>
                        <td class="width-column" data-col="7">
                            <select data-prop="nm_recycle_hyoji01"></select>
                        </td>
                        <td class="width-column" data-col="8">
                            <select data-prop="nm_recycle_hyoji01"></select>
                        </td>
                        <td class="width-column" data-col="9">
                            <select data-prop="nm_recycle_hyoji01"></select>
                        </td>
                        <td class="width-column" data-col="10">
                            <select data-prop="nm_recycle_hyoji01"></select>
                        </td>
                    </tr>
                    <tr data-master="nm_recycle_hyoji">
                        <td><label>リサイクル表示2</label></td>
                        <td class="width-column" data-col="1">
                            <select data-prop="nm_recycle_hyoji02"></select>
                        </td>
                        <td class="width-column" data-col="2">
                            <select data-prop="nm_recycle_hyoji02"></select>
                        </td>
                        <td class="width-column" data-col="3">
                            <select data-prop="nm_recycle_hyoji02"></select>
                        </td>
                        <td class="width-column" data-col="4">
                            <select data-prop="nm_recycle_hyoji02"></select>
                        </td>
                        <td class="width-column" data-col="5">
                            <select data-prop="nm_recycle_hyoji02"></select>
                        </td>
                        <td class="width-column" data-col="6">
                            <select data-prop="nm_recycle_hyoji02"></select>
                        </td>
                        <td class="width-column" data-col="7">
                            <select data-prop="nm_recycle_hyoji02"></select>
                        </td>
                        <td class="width-column" data-col="8">
                            <select data-prop="nm_recycle_hyoji02"></select>
                        </td>
                        <td class="width-column" data-col="9">
                            <select data-prop="nm_recycle_hyoji02"></select>
                        </td>
                        <td class="width-column" data-col="10">
                            <select data-prop="nm_recycle_hyoji02"></select>
                        </td>
                    </tr>
                    <tr data-master="nm_recycle_hyoji">
                        <td><label>リサイクル表示3</label></td>
                        <td class="width-column" data-col="1">
                            <select data-prop="nm_recycle_hyoji03"></select>
                        </td>
                        <td class="width-column" data-col="2">
                            <select data-prop="nm_recycle_hyoji03"></select>
                        </td>
                        <td class="width-column" data-col="3">
                            <select data-prop="nm_recycle_hyoji03"></select>
                        </td>
                        <td class="width-column" data-col="4">
                            <select data-prop="nm_recycle_hyoji03"></select>
                        </td>
                        <td class="width-column" data-col="5">
                            <select data-prop="nm_recycle_hyoji03"></select>
                        </td>
                        <td class="width-column" data-col="6">
                            <select data-prop="nm_recycle_hyoji03"></select>
                        </td>
                        <td class="width-column" data-col="7">
                            <select data-prop="nm_recycle_hyoji03"></select>
                        </td>
                        <td class="width-column" data-col="8">
                            <select data-prop="nm_recycle_hyoji03"></select>
                        </td>
                        <td class="width-column" data-col="9">
                            <select data-prop="nm_recycle_hyoji03"></select>
                        </td>
                        <td class="width-column" data-col="10">
                            <select data-prop="nm_recycle_hyoji03"></select>
                        </td>
                    </tr>
                    <tr data-master="nm_recycle_hyoji">
                        <td><label>リサイクル表示4</label></td>
                        <td class="width-column" data-col="1">
                            <select data-prop="nm_recycle_hyoji04"></select>
                        </td>
                        <td class="width-column" data-col="2">
                            <select data-prop="nm_recycle_hyoji04"></select>
                        </td>
                        <td class="width-column" data-col="3">
                            <select data-prop="nm_recycle_hyoji04"></select>
                        </td>
                        <td class="width-column" data-col="4">
                            <select data-prop="nm_recycle_hyoji04"></select>
                        </td>
                        <td class="width-column" data-col="5">
                            <select data-prop="nm_recycle_hyoji04"></select>
                        </td>
                        <td class="width-column" data-col="6">
                            <select data-prop="nm_recycle_hyoji04"></select>
                        </td>
                        <td class="width-column" data-col="7">
                            <select data-prop="nm_recycle_hyoji04"></select>
                        </td>
                        <td class="width-column" data-col="8">
                            <select data-prop="nm_recycle_hyoji04"></select>
                        </td>
                        <td class="width-column" data-col="9">
                            <select data-prop="nm_recycle_hyoji04"></select>
                        </td>
                        <td class="width-column" data-col="10">
                            <select data-prop="nm_recycle_hyoji04"></select>
                        </td>
                    </tr>
                    <tr data-master="nm_recycle_hyoji">
                        <td><label>リサイクル表示5</label></td>
                        <td class="width-column" data-col="1">
                            <select data-prop="nm_recycle_hyoji05"></select>
                        </td>
                        <td class="width-column" data-col="2">
                            <select data-prop="nm_recycle_hyoji05"></select>
                        </td>
                        <td class="width-column" data-col="3">
                            <select data-prop="nm_recycle_hyoji05"></select>
                        </td>
                        <td class="width-column" data-col="4">
                            <select data-prop="nm_recycle_hyoji05"></select>
                        </td>
                        <td class="width-column" data-col="5">
                            <select data-prop="nm_recycle_hyoji05"></select>
                        </td>
                        <td class="width-column" data-col="6">
                            <select data-prop="nm_recycle_hyoji05"></select>
                        </td>
                        <td class="width-column" data-col="7">
                            <select data-prop="nm_recycle_hyoji05"></select>
                        </td>
                        <td class="width-column" data-col="8">
                            <select data-prop="nm_recycle_hyoji05"></select>
                        </td>
                        <td class="width-column" data-col="9">
                            <select data-prop="nm_recycle_hyoji05"></select>
                        </td>
                        <td class="width-column" data-col="10">
                            <select data-prop="nm_recycle_hyoji05"></select>
                        </td>
                    </tr>
                </tbody>
            </table>


            <!-- table detail 5: サイズ -->
            <table class="datatable detail_5 shizai">
                <thead>
                    <tr class="th-frame">
                        <th></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                    </tr>
                </thead>
                <tbody class="item-tmpl" style="cursor: default;">
                    <tr>
                        <td><label>サイズ1</label></td>
                        <td class="width-column" data-col="1">
                            <input type="text" data-prop="nm_size01" /></td>
                        <td class="width-column" data-col="2">
                            <input type="text" data-prop="nm_size01" /></td>
                        <td class="width-column" data-col="3">
                            <input type="text" data-prop="nm_size01" /></td>
                        <td class="width-column" data-col="4">
                            <input type="text" data-prop="nm_size01" /></td>
                        <td class="width-column" data-col="5">
                            <input type="text" data-prop="nm_size01" /></td>
                        <td class="width-column" data-col="6">
                            <input type="text" data-prop="nm_size01" /></td>
                        <td class="width-column" data-col="7">
                            <input type="text" data-prop="nm_size01" /></td>
                        <td class="width-column" data-col="8">
                            <input type="text" data-prop="nm_size01" /></td>
                        <td class="width-column" data-col="9">
                            <input type="text" data-prop="nm_size01" /></td>
                        <td class="width-column" data-col="10">
                            <input type="text" data-prop="nm_size01" /></td>
                    </tr>
                    <tr>
                        <td><label>サイズ2</label></td>
                        <td class="width-column" data-col="1">
                            <input type="text" data-prop="nm_size02" /></td>
                        <td class="width-column" data-col="2">
                            <input type="text" data-prop="nm_size02" /></td>
                        <td class="width-column" data-col="3">
                            <input type="text" data-prop="nm_size02" /></td>
                        <td class="width-column" data-col="4">
                            <input type="text" data-prop="nm_size02" /></td>
                        <td class="width-column" data-col="5">
                            <input type="text" data-prop="nm_size02" /></td>
                        <td class="width-column" data-col="6">
                            <input type="text" data-prop="nm_size02" /></td>
                        <td class="width-column" data-col="7">
                            <input type="text" data-prop="nm_size02" /></td>
                        <td class="width-column" data-col="8">
                            <input type="text" data-prop="nm_size02" /></td>
                        <td class="width-column" data-col="9">
                            <input type="text" data-prop="nm_size02" /></td>
                        <td class="width-column" data-col="10">
                            <input type="text" data-prop="nm_size02" /></td>
                    </tr>
                    <tr>
                        <td><label>サイズ3</label></td>
                        <td class="width-column" data-col="1">
                            <input type="text" data-prop="nm_size03" /></td>
                        <td class="width-column" data-col="2">
                            <input type="text" data-prop="nm_size03" /></td>
                        <td class="width-column" data-col="3">
                            <input type="text" data-prop="nm_size03" /></td>
                        <td class="width-column" data-col="4">
                            <input type="text" data-prop="nm_size03" /></td>
                        <td class="width-column" data-col="5">
                            <input type="text" data-prop="nm_size03" /></td>
                        <td class="width-column" data-col="6">
                            <input type="text" data-prop="nm_size03" /></td>
                        <td class="width-column" data-col="7">
                            <input type="text" data-prop="nm_size03" /></td>
                        <td class="width-column" data-col="8">
                            <input type="text" data-prop="nm_size03" /></td>
                        <td class="width-column" data-col="9">
                            <input type="text" data-prop="nm_size03" /></td>
                        <td class="width-column" data-col="10">
                            <input type="text" data-prop="nm_size03" /></td>
                    </tr>
                    <tr>
                        <td><label>サイズ4</label></td>
                        <td class="width-column" data-col="1">
                            <input type="text" data-prop="nm_size04" /></td>
                        <td class="width-column" data-col="2">
                            <input type="text" data-prop="nm_size04" /></td>
                        <td class="width-column" data-col="3">
                            <input type="text" data-prop="nm_size04" /></td>
                        <td class="width-column" data-col="4">
                            <input type="text" data-prop="nm_size04" /></td>
                        <td class="width-column" data-col="5">
                            <input type="text" data-prop="nm_size04" /></td>
                        <td class="width-column" data-col="6">
                            <input type="text" data-prop="nm_size04" /></td>
                        <td class="width-column" data-col="7">
                            <input type="text" data-prop="nm_size04" /></td>
                        <td class="width-column" data-col="8">
                            <input type="text" data-prop="nm_size04" /></td>
                        <td class="width-column" data-col="9">
                            <input type="text" data-prop="nm_size04" /></td>
                        <td class="width-column" data-col="10">
                            <input type="text" data-prop="nm_size04" /></td>
                    </tr>
                    <tr>
                        <td><label>サイズ5</label></td>
                        <td class="width-column" data-col="1">
                            <input type="text" data-prop="nm_size05" /></td>
                        <td class="width-column" data-col="2">
                            <input type="text" data-prop="nm_size05" /></td>
                        <td class="width-column" data-col="3">
                            <input type="text" data-prop="nm_size05" /></td>
                        <td class="width-column" data-col="4">
                            <input type="text" data-prop="nm_size05" /></td>
                        <td class="width-column" data-col="5">
                            <input type="text" data-prop="nm_size05" /></td>
                        <td class="width-column" data-col="6">
                            <input type="text" data-prop="nm_size05" /></td>
                        <td class="width-column" data-col="7">
                            <input type="text" data-prop="nm_size05" /></td>
                        <td class="width-column" data-col="8">
                            <input type="text" data-prop="nm_size05" /></td>
                        <td class="width-column" data-col="9">
                            <input type="text" data-prop="nm_size05" /></td>
                        <td class="width-column" data-col="10">
                            <input type="text" data-prop="nm_size05" /></td>
                    </tr>
                </tbody>
            </table>


            <!-- table detail 6: Note -->
            <table class="datatable detail_6 shizai">
                <thead>
                    <tr class="th-frame">
                        <th></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                        <th class="width-column"></th>
                    </tr>
                </thead>
                <tbody class="item-tmpl" style="cursor: default;">
                    <tr>
                        <td  data-col="1">
                            <textarea class="textarea-height bar-less" data-prop="nm_free_title_komoku" style="background-color: #efefef"></textarea>
                        <td class="width-column" data-col="1">
                            <textarea class="textarea-height bar-less" data-prop="nm_free_komoku"></textarea></td>
                        <td class="width-column" data-col="2">
                            <textarea class="textarea-height bar-less" data-prop="nm_free_komoku"></textarea></td>
                        <td class="width-column" data-col="3">
                            <textarea class="textarea-height bar-less" data-prop="nm_free_komoku"></textarea></td>
                        <td class="width-column" data-col="4">
                            <textarea class="textarea-height bar-less" data-prop="nm_free_komoku"></textarea></td>
                        <td class="width-column" data-col="5">
                            <textarea class="textarea-height bar-less" data-prop="nm_free_komoku"></textarea></td>
                        <td class="width-column" data-col="6">
                            <textarea class="textarea-height bar-less" data-prop="nm_free_komoku"></textarea></td>
                        <td class="width-column" data-col="7">
                            <textarea class="textarea-height bar-less" data-prop="nm_free_komoku"></textarea></td>
                        <td class="width-column" data-col="8">
                            <textarea class="textarea-height bar-less" data-prop="nm_free_komoku"></textarea></td>
                        <td class="width-column" data-col="9">
                            <textarea class="textarea-height bar-less" data-prop="nm_free_komoku"></textarea></td>
                        <td class="width-column" data-col="10">
                            <textarea class="textarea-height bar-less" data-prop="nm_free_komoku"></textarea></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
