<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="700_HaigoTorikomi_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs.HaigoTorikomi_Dialog" %>
<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver1.6)】 Template--%>

<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var haigoTorikomiDialog = {
        options: {
            parameter: {
                no_gamen: null,
                cd_kaisha: null,
                cd_kojyo: null,
                M_kirikae: null
            },
            validations: {
                haigoTorokuKaihatsuBumon: {},
                haigoTorokuKojyoBumon: {}
            }
        },
        urls: {
            vw_no_seiho_shurui: "../Services/ShisaQuickService.svc/vw_no_seiho_shurui?$orderby=cd_hin_syurui,cd_literal",
            haigoTorikomiDialogController: "../api/HaigoTorikomi_Dialog"
        },
        values: {
            hasShowDialog: false,
            listHaigoMeisai:{},
            top: App.settings.base.dataTakeCount,
            skip: 0
        },
        detailHaigo: {},
        detailKotei: {},
        resultHaigo: {}
    };

    /**
         * バリデーション成功時の処理を実行します。
         */
    haigoTorikomiDialog.validationSuccess = function (results, state) {
        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                haigoTorikomiDialog.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                    if (row && row.element) {
                        row.element.find("tr").removeClass("has-error");
                    }
                });
            } else {
                if (!(state && state.noMessage)) {
                    haigoTorikomiDialog.setColValidStyle(item.element);
                }
            }
            if (!(state && state.noMessage)) {
                haigoTorikomiDialog.notifyAlert.remove(item.element);
            }
        }
    };

    /**
     * バリデーション失敗時の処理を実行します。
     */
    haigoTorikomiDialog.validationFail = function (results, state) {

        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                haigoTorikomiDialog.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                    if (row && row.element) {
                        row.element.find("tr").addClass("has-error");
                    }
                });
            } else {
                if (!(state && state.noMessage)) {
                    haigoTorikomiDialog.setColInvalidStyle(item.element);
                }
            }

            if (state && (state.suppressMessage || state.noMessage)) {
                continue;
            }
            haigoTorikomiDialog.notifyAlert.message(item.message, item.element).show();
        }
    };

    /**
     * バリデーション後の処理を実行します。
     */
    haigoTorikomiDialog.validationAlways = function (results) {
        //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
    };

    /**
      * 指定された定義をもとにバリデータを作成します。
      * @param target バリデーション定義
      * @param options オプションに設定する値。指定されていない場合は、
      *                画面の success/fail/always のハンドル処理が指定されたオプションが設定されます。
      */
    haigoTorikomiDialog.createValidator = function (target, options) {
        return App.validation(target, options || {
            success: haigoTorikomiDialog.validationSuccess,
            fail: haigoTorikomiDialog.validationFail,
            always: haigoTorikomiDialog.validationAlways
        });
    };

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    haigoTorikomiDialog.setColInvalidStyle = function (target) {
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
    haigoTorikomiDialog.setColValidStyle = function (target) {
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
     * 検索ダイアログの初期化処理を行います。
     */
    haigoTorikomiDialog.initialize = function () {
        var element = $("#haigoTorikomiDialog"),
            validations;

        element.on("hidden.bs.modal", haigoTorikomiDialog.hidden);
        element.on("shown.bs.modal", haigoTorikomiDialog.shown);
        element.on("click", ".search", haigoTorikomiDialog.search);
        //TODO: 単一セレクトの場合は、下の１行を使用します
        //element.on("click", ".search-list tbody ", haigoTorikomiDialog.selectTorikomi);
        //TODO: 複数セレクトの場合は、上の１行を削除し、下の３行をコメント解除します
        element.on("click", ".select", haigoTorikomiDialog.selectData);
        element.on("click", ".next-search", haigoTorikomiDialog.nextSearch);
        element.on("change", ":input", haigoTorikomiDialog.change);
        haigoTorikomiDialog.element = element;

        if (haigoTorikomiDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
            haigoTorikomiDialog.element.find(".209-part").hide();
            haigoTorikomiDialog.element.find(".203-part").show();
            haigoTorikomiDialog.element.find(".209-part").remove();
            element.findP("no_seiho_kaisha").val(App.num.format(Number(App.ui.page.user.cd_kaisha), "0000"));
            validations = haigoTorikomiDialog.options.validations.haigoTorokuKaihatsuBumon;
        }
        else {
            haigoTorikomiDialog.element.find(".209-part").show();
            haigoTorikomiDialog.element.find(".203-part").hide();
            haigoTorikomiDialog.element.find(".203-part").remove();
            validations = haigoTorikomiDialog.options.validations.haigoTorokuKojyoBumon;
        }

        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        haigoTorikomiDialog.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#haigoTorikomiDialog .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body .detail",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        haigoTorikomiDialog.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#haigoTorikomiDialog .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body .detail",
                show: function () {
                    element.find(".alert-message").show();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                }
            });

        var detail_haigo = element.find(".detail-haigo-torikomi"),
            datatable_haigo = detail_haigo.find(".datatable").dataTable({
                height: 150,
                //resize: true,
                onselect: haigoTorikomiDialog.selectHaigoTorikomi,
                onchange: haigoTorikomiDialog.changeHaigoTorikomi
            });

        haigoTorikomiDialog.detailHaigo.dataTable = datatable_haigo;

        var detail_kotei = element.find(".detail-kotei"),
            datatable_kotei = detail_kotei.find(".datatable").dataTable({
                height: 150,
                onselect: haigoTorikomiDialog.selectKotei,
                onchange: haigoTorikomiDialog.changeKotei
            });

        haigoTorikomiDialog.detailKotei.dataTable = datatable_kotei;

        haigoTorikomiDialog.validator = element.validation(haigoTorikomiDialog.createValidator(validations));

        haigoTorikomiDialog.detailKotei.validator = element.validation(haigoTorikomiDialog.createValidator(haigoTorikomiDialog.options.validations.detailKotei));

        element.find(".modal-dialog").draggable({
            drag: true,
        });
    };

    

    /**
     * Event change input.
     */
    haigoTorikomiDialog.change = function (e) {
        var target = $(e.target),
            property = target.attr("data-prop"),
            element = $("#haigoTorikomiDialog");

        if (property == "no_seiho_kaisha" || property == "no_seiho_shurui" || property == "no_seiho_nen" || property == "no_seiho_renban" || property == "cd_haigo_search" || property == "nm_haigo_search" ||property == "nm_seiho") {

            if ($(".next-search").hasClass("show-search")) {
                $(".next-search").removeClass("show-search").hide();
                haigoTorikomiDialog.notifyInfo.message(App.messages.base.MS0010).show();
            }

            if (property == "cd_haigo_search") {
                haigoTorikomiDialog.validator.validate({
                    targets: target,
                }).then(function () {
                })
            }

            if (property == "nm_haigo_search") {
                haigoTorikomiDialog.validator.validate({
                    targets: target,
                }).then(function () {
                })
            }

            if (property == "nm_seiho") {
                haigoTorikomiDialog.validator.validate({
                    targets: target
                }).then(function () {

                })
            }

            if (property == "no_seiho_kaisha") {
                haigoTorikomiDialog.validator.validate({
                    targets: target,
                }).then(function () {
                    var newVal = target.val();
                    if (newVal != "" && !isNaN(newVal)) {
                        target.val(App.num.format(Number(newVal), "0000"));
                    }

                    haigoTorikomiDialog.validator.validate({
                        targets: element.find(".no-seiho-dialog")
                    })
                })
            }

            if (property == "no_seiho_nen") {
                haigoTorikomiDialog.validator.validate({
                    targets: target,
                }).then(function () {
                    var newVal = target.val();
                    if (newVal != "") {
                        target.val(App.num.format(Number(newVal), "00"));
                    }
                    haigoTorikomiDialog.validator.validate({
                        targets: element.find(".no-seiho-dialog")
                    })
                })
            }

            if (property == "no_seiho_renban") {
                haigoTorikomiDialog.validator.validate({
                    targets: target
                }).then(function () {
                    var newVal = target.val();
                    if (newVal != "") {
                        target.val(App.num.format(Number(newVal), "0000"));
                    }
                    haigoTorikomiDialog.validator.validate({
                        targets: element.find(".no-seiho-dialog")
                    })
                })
            }
        }

        if (property == "checkbox_kotei_all") {
            if (target.prop("checked")) {
                haigoTorikomiDialog.detailKotei.dataTable.find(".new .checkbox-kotei").prop("checked", true);
            }
            else {
                haigoTorikomiDialog.detailKotei.dataTable.find(".new .checkbox-kotei").prop("checked", false);
            }
        }

        if (property == "bairitsu") {
            haigoTorikomiDialog.detailKotei.validator.validate({
                targets: target
            }).then(function () {
                var valOld = target.val(),
                valNew = App.num.format(Number(valOld), "#.00");
                target.val(valNew);
            })
        }
    }

    /**
     * 検索ダイアログ非表示時処理を実行します。
     */
    haigoTorikomiDialog.hidden = function (e) {

        var element = haigoTorikomiDialog.element;

        ////TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
        element.find(":input").val("");

        if (haigoTorikomiDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
            haigoTorikomiDialog.element.findP("no_seiho_kaisha").val(App.num.format(Number(App.ui.page.user.cd_kaisha), "0000"));
        }

        haigoTorikomiDialog.element.findP("bairitsu").val("1.00")

        haigoTorikomiDialog.detailHaigo.dataTable.dataTable("clear");
        haigoTorikomiDialog.detailKotei.dataTable.dataTable("clear");

        element.findP("checkbox_kotei_all").prop("checked", false).prop("disabled", true);

        element.findP("data_count_haigo").text("");
        element.findP("data_count_total_haigo").text("");

        element.findP("data_count_kotei").text("");
        element.findP("data_count_total_kotei").text("");

        element.find(".next-search").removeClass("show-search").hide();

        //element.findP("data_count").text("");
        //element.findP("data_count_total").text("");

        var items = element.find(".modal-body :input");
        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            if ($(item).closest("tbody").length) {
                continue;
            }
            haigoTorikomiDialog.setColValidStyle(item);
        }

        haigoTorikomiDialog.notifyInfo.clear();
        haigoTorikomiDialog.notifyAlert.clear();
    };

    /**
    * 検索ダイアログ表示時処理を実行します。
    */
    haigoTorikomiDialog.shown = function (e) {

        if (!haigoTorikomiDialog.hasShowDialog) {
            var loadingTaget = haigoTorikomiDialog.element.find(".modal-content");
            App.ui.loading.show("", loadingTaget);
            $.ajax(App.ajax.odata.get(haigoTorikomiDialog.urls.vw_no_seiho_shurui)
                ).then(function (result) {
                    var no_seiho_shurui = haigoTorikomiDialog.element.findP("no_seiho_shurui");
                    no_seiho_shurui.children().remove();
                    App.ui.appendOptions(
                        no_seiho_shurui,
                        "no_seiho_shurui",
                        "no_seiho_shurui",
                        result.value,
                        haigoTorikomiDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu ? false : true
                    );
                }).fail(function (error) {
                    haigoTorikomiDialog.notifyAlert.message(App.ajax.handleError(error).message).show();
                }).always(function () {
                    App.ui.loading.close(loadingTaget);
                    haigoTorikomiDialog.element.find(":input:not(button):first").focus();
                })
        }
        else {
            haigoTorikomiDialog.element.find(":input:not(button):first").focus();
        }
    };

    /**
     * 検索ダイアログ　バリデーションルールを定義します。
     */
    haigoTorikomiDialog.options.validations.haigoTorokuKaihatsuBumon = {

        no_seiho_nen: {
            rules: {
                digits: true,
                maxlength: 2
            },
            options: {
                name: "製法番号（年）"
            },
            messages: {
                digits: App.messages.base.digits,
                maxlength: App.messages.base.maxlength
            }
        },

        no_seiho_renban: {
            rules: {
                digits: true,
                maxlength: 4
            },
            options: {
                name: "製法番号（シーケンス番号）"
            },
            messages: {
                digits: App.messages.base.digits,
                maxlength: App.messages.base.maxlength
            }
        },

        nm_seiho: {
            rules: {
                maxbytelength: 120
            },
            options: {
                name: "製法名",
                byte: 60
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        }
    };

    /**
     * 検索ダイアログ　バリデーションルールを定義します。
     */
    haigoTorikomiDialog.options.validations.haigoTorokuKojyoBumon = {

        cd_haigo_search: {
            rules: {
                digits: true,
                maxlength: 13
            },
            options: {
                name: "配合コード"
            },
            messages: {
                digits: App.messages.base.digits,
                maxlength: App.messages.base.maxlength
            }
        },

        nm_haigo_search: {
            rules: {
                maxbytelength: 60
            },
            options: {
                name: "配合名/コード",
                byte:30
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },

        no_seiho_kaisha: {
            rules: {
                digits: true,
                maxlength: 4
            },
            options: {
                name: "製法番号（会社コード）"
            },
            messages: {
                digits: App.messages.base.digits,
                maxlength: App.messages.base.maxlength
            }
        },

        no_seiho_nen: {
            rules: {
                digits: true,
                maxlength: 2
            },
            options: {
                name: "製法番号（年）"
            },
            messages: {
                digits: App.messages.base.digits,
                maxlength: App.messages.base.maxlength
            }
        },

        no_seiho_renban: {
            rules: {
                digits: true,
                maxlength: 4
            },
            options: {
                name: "製法番号（シーケンス番号）"
            },
            messages: {
                digits: App.messages.base.digits,
                maxlength: App.messages.base.maxlength
            }
        }
    };

    haigoTorikomiDialog.options.validations.detailKotei =  {
        bairitsu: {
            rules: {
                required: true,
                number: true,
                //range: [0.01, 99.99],
                pointlength: [2, 2]
            },
            options: {
                name: "倍率"
            },
            messages: {
                required: App.messages.base.required,
                number: App.messages.base.number,
                range: App.messages.base.range,
                pointlength: App.messages.base.pointlength
            }
        }
    }
    

    /**
     * 検索ダイアログの検索処理を実行します。
     */
    haigoTorikomiDialog.search = function () {
        var element = haigoTorikomiDialog.element,
            loadingTaget = element.find(".modal-content");
        //debugger;
        haigoTorikomiDialog.validator.validate()
        .then(function () {
            
            haigoTorikomiDialog.values.skip = 0;

            haigoTorikomiDialog.notifyAlert.clear();

            haigoTorikomiDialog.detailKotei.dataTable.dataTable("clear");

            App.ui.loading.show("", loadingTaget);
            var parameter = {},
                getMethod = "";

            if (haigoTorikomiDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
                var no_seiho_kaisha = element.findP("no_seiho_kaisha").val(),
                    no_seiho_shurui = element.findP("no_seiho_shurui").val(),
                    no_seiho_nen = element.findP("no_seiho_nen").val(),
                    no_seiho_renban = element.findP("no_seiho_renban").val(),
                    nm_seiho = element.findP("nm_seiho").val();

                getMethod = "/getDataHaigoModeKaihatsu"
                parameter = {
                    no_seiho_kaisha: no_seiho_kaisha == "" ? null : no_seiho_kaisha,
                    no_seiho_shurui: no_seiho_shurui == "" ? null : no_seiho_shurui,
                    no_seiho_nen: no_seiho_nen == "" ? null : no_seiho_nen,
                    no_seiho_renban: no_seiho_renban == "" ? null : no_seiho_renban,
                    nm_seiho: nm_seiho == "" ? null : nm_seiho
                };
            }
            else {
                var cd_haigo = element.findP("cd_haigo_search").val(),
                    nm_haigo = element.findP("nm_haigo_search").val(),
                    no_seiho_kaisha = element.findP("no_seiho_kaisha").val(),
                    no_seiho_shurui = element.findP("no_seiho_shurui").val(),
                    no_seiho_nen = element.findP("no_seiho_nen").val(),
                    no_seiho_renban = element.findP("no_seiho_renban").val();

                getMethod = "/getDataHaigoModeKojyo"

                parameter = {
                    cd_kaisha: haigoTorikomiDialog.options.parameter.cd_kaisha,
                    cd_kojyo: haigoTorikomiDialog.options.parameter.cd_kojyo,
                    M_kirikae: haigoTorikomiDialog.options.parameter.M_kirikae,
                    //M_kirikae: 1,
                    cd_haigo: cd_haigo == "" ? null : cd_haigo,
                    nm_haigo: nm_haigo == "" ? null : nm_haigo,
                    no_seiho_kaisha: no_seiho_kaisha == "" ? null : no_seiho_kaisha,
                    no_seiho_shurui: no_seiho_shurui == "" ? null : no_seiho_shurui,
                    no_seiho_nen: no_seiho_nen == "" ? null : no_seiho_nen,
                    no_seiho_renban: no_seiho_renban == "" ? null : no_seiho_renban,
                };
                   
            }

            return $.ajax(App.ajax.webapi.get(haigoTorikomiDialog.urls.haigoTorikomiDialogController + getMethod, parameter))
                .done(function (result) {

                    if (haigoTorikomiDialog.options.parameter.no_gamen != App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
                        result = result.Table;
                    }

                    haigoTorikomiDialog.resultHaigo.Count = result.length;
                    haigoTorikomiDialog.resultHaigo.Items = result.slice(0, haigoTorikomiDialog.values.top);
                    haigoTorikomiDialog.resultHaigo.data = result;

                    haigoTorikomiDialog.bindDetailHaigo(haigoTorikomiDialog.resultHaigo);
                }).fail(function (error) {
                    haigoTorikomiDialog.notifyAlert.message(App.ajax.handleError(error).message).show();
                }).always(function () {
                    App.ui.loading.close(loadingTaget);
                });
        });
    };

    /**
    * 次のレコードを検索する処理を定義します。
    */
    haigoTorikomiDialog.nextSearch = function () {

        var element = haigoTorikomiDialog.element,
            loadingTaget = element.find(".modal-content");

        App.ui.loading.show("", loadingTaget);
        haigoTorikomiDialog.notifyAlert.clear();
        setTimeout(function () {
            haigoTorikomiDialog.resultHaigo.Items = haigoTorikomiDialog.resultHaigo.data.slice(haigoTorikomiDialog.values.skip, haigoTorikomiDialog.values.skip + haigoTorikomiDialog.values.top);
            haigoTorikomiDialog.bindDetailHaigo(haigoTorikomiDialog.resultHaigo);
            App.ui.loading.close(loadingTaget);
        },100)
    };

    /**
    * 画面明細へのデータバインド処理を行います。
    */
    haigoTorikomiDialog.bindDetailHaigo = function (data) {
        var i, l, item, dataSet, dataCount, offsetHeight;

        dataCount = data.Count;
        data = (data.Items) ? data.Items : data;
        
        if (haigoTorikomiDialog.values.skip === 0) {
            dataSet = App.ui.page.dataSet();
            haigoTorikomiDialog.detailHaigo.dataTable.dataTable("clear");
        }
        else {
            dataSet = haigoTorikomiDialog.detailHaigo.data;
        }

        haigoTorikomiDialog.detailHaigo.data = dataSet

        haigoTorikomiDialog.detailHaigo.dataTable.dataTable("addRows", data, function (row, item) {
            haigoTorikomiDialog.detailHaigo.data.add(item);
            row.form(haigoTorikomiDialog.options.bindOptionDetailHaigo).bind(item);

            row.dblclick(function () {
                if (!row.findP("checkbox_haigo").prop("checked")) {
                    row.findP("checkbox_haigo").prop("checked", true).change();
                }
            })

            return row;
        }, true);

        //debugger;
        haigoTorikomiDialog.values.skip += data.length;
        haigoTorikomiDialog.element.findP("data_count_haigo").text(haigoTorikomiDialog.values.skip);
        haigoTorikomiDialog.element.findP("data_count_total_haigo").text(dataCount);

        if (dataCount <= haigoTorikomiDialog.values.skip) {
            haigoTorikomiDialog.element.find(".next-search").removeClass("show-search").hide();
        }
        else {
            haigoTorikomiDialog.element.find(".next-search").show();
        }

        if (haigoTorikomiDialog.values.skip >= App.settings.base.maxSearchDataCount) {
            haigoTorikomiDialog.notifyInfo.message(App.messages.base.MS0011).show();
            haigoTorikomiDialog.element.find(".next-search").removeClass("show-search").hide();
        }

        offsetHeight = haigoTorikomiDialog.element.find(".next-search").is(":visible") ? haigoTorikomiDialog.element.find(".next-search").addClass("show-search").outerHeight() : 0;
        haigoTorikomiDialog.detailHaigo.dataTable.dataTable("setAditionalOffset", offsetHeight);
        
        //TODO: 画面明細へのデータバインド処理をここに記述します。
    };

    /**
         * 画面明細の各行にデータを設定する際のオプションを定義します。
         */
    haigoTorikomiDialog.options.bindOptionDetailHaigo = {
        //// TODO: 主キーが直接入力の場合には、修正の場合変更を不可とします。
        //appliers: {
        //    no_mitsumori: function (value, element) {
        //        element.val(value);
        //        element.prop("readonly", true).prop("tabindex", -1);
        //        return true;
        //    }
        //}
    };

    /**
         * get new bignumber
         */
    haigoTorikomiDialog.getBigNumber = function (value) {
        if (App.isUndefOrNull(value)) {
            value = "";
        }
        var val = Number(value);
        if (isNaN(val)) {
            val = 0;
        }
        val = val.toString();
        return new BigNumber(val);
    }

    /**
     * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
     */
    haigoTorikomiDialog.selectHaigoTorikomi = function (e,row) {
        //選択行全体に背景色を付ける場合は以下のコメントアウトを解除します。
        if (!App.isUndefOrNull(haigoTorikomiDialog.detailHaigo.selectedRow)) {
            haigoTorikomiDialog.detailHaigo.selectedRow.element.find("tr").removeClass("selected-row");
        }
        row.element.find("tr").addClass("selected-row");
        haigoTorikomiDialog.detailHaigo.selectedRow = row;
    };

    /**
     * change HaigoTorikomi table;
     */
    haigoTorikomiDialog.changeHaigoTorikomi = function (e, row) {
        var target = $(e.target),
            property = target.attr("data-prop"),    
            id = row.element.attr("data-key"),
            entity = haigoTorikomiDialog.detailHaigo.data.entry(id);

        if (property == "checkbox_haigo") {

            if (!target.prop("checked")) {
                haigoTorikomiDialog.detailKotei.dataTable.dataTable("clear");
                haigoTorikomiDialog.element.findP("checkbox_kotei_all").prop("checked", false).prop("disabled", true);
                haigoTorikomiDialog.notifyAlert.remove("AP0067");
                haigoTorikomiDialog.element.findP("data_count_kotei").text("");
                haigoTorikomiDialog.element.findP("data_count_total_kotei").text("");
            }
            else {

                haigoTorikomiDialog.element.find(".checkbox-haigo").not(target).prop("checked", false);

                //Mode kaihatsu
                if (haigoTorikomiDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
                    var paramKaisha = haigoTorikomiDialog.options.parameter.cd_kaisha,
                        paramKojyo = haigoTorikomiDialog.options.parameter.cd_kojyo;
                    // Skip new mode
                    if (haigoTorikomiDialog.options.parameter.M_kirikae != App.settings.app.m_haigo_toroku_kaihatsu.haigo_shinki
                        // Check cd_kaisha_daihyo and cd_kojyo_daihyo
                        && (entity["cd_kaisha_daihyo"] != paramKaisha || entity["cd_kojyo_daihyo"] != paramKojyo)) {
                        haigoTorikomiDialog.detailKotei.dataTable.dataTable("clear");
                        haigoTorikomiDialog.element.findP("checkbox_kotei_all").prop("checked", false).prop("disabled", true);
                        haigoTorikomiDialog.notifyAlert.remove("AP0067");
                        haigoTorikomiDialog.notifyAlert.message(App.messages.app.AP0067, "AP0067").show();
                        haigoTorikomiDialog.element.findP("data_count_kotei").text("");
                        haigoTorikomiDialog.element.findP("data_count_total_kotei").text("");
                    }
                    else {
                        haigoTorikomiDialog.notifyAlert.remove("AP0067");
                        var loadingTaget = haigoTorikomiDialog.element.find(".modal-content");
                        App.ui.loading.show("", loadingTaget);

                        $.ajax(App.ajax.webapi.get(haigoTorikomiDialog.urls.haigoTorikomiDialogController + "/getDataKoteiModeKaihatsu", { cd_haigo: entity["cd_haigo"], cd_kaisha: paramKaisha, cd_kojyo: paramKojyo, no_seiho_kaisha: parseInt(entity["no_seiho"].substr(0, 4), 10) }))
                        .done(function (result) {
                            //debugger;
                            var listData = [],
                                data = {};
                            haigoTorikomiDialog.values.listHaigoMeisai = {};
                            for (var i = 0 ; i < result.listHaigoMeisai.length; i++) {
                                var item = result.listHaigoMeisai[i],
                                    addColumnMeisai = result.listAddColumnMeisai[i];
                                // Recovery qty_haigo base on tani
                                if (addColumnMeisai.cd_tani_shiyo == App.settings.app.taniShiyo.g) {
                                    item.qty_haigo = haigoTorikomiDialog.getBigNumber(item.qty_haigo).times(1000).toNumber();
                                }
                                if (!data[item.no_kotei]) {
                                    data[item.no_kotei] = {};
                                    data[item.no_kotei].no_kotei = item.no_kotei;
                                    data[item.no_kotei].nm_hin = item.nm_hin;
                                    data[item.no_kotei].qty_haigo = App.isUndefOrNull(item.qty_haigo) ? 0 : item.qty_haigo;
                                    data[item.no_kotei].cd_haigo = item.cd_haigo;
                                    haigoTorikomiDialog.values.listHaigoMeisai[item.no_kotei] = [];

                                    item.hijyu = addColumnMeisai.hijyu;
                                    item.no_kikaku = addColumnMeisai.no_kikaku;
                                    item.kbn_shikakari = addColumnMeisai.kbn_shikakari;
                                    item.cd_tani_hin = addColumnMeisai.cd_tani_hin;
                                    item.kbn_hin = addColumnMeisai.kbn_hin;
                                    item.no_seiho = entity["no_seiho"];
                                    item.hasHinmei = addColumnMeisai.hasHinmei;
                                    haigoTorikomiDialog.values.listHaigoMeisai[item.no_kotei].push(item);
                                }
                                else {
                                    //data[item.no_kotei].qty_haigo = haigoTorikomiDialog.getBigNumber(data[item.no_kotei].qty_haigo).plus(App.isUndefOrNull(item.qty_haigo) ? 0 : item.qty_haigo).toNumber();
                                    data[item.no_kotei].qty_haigo = haigoTorikomiDialog.getBigNumber(data[item.no_kotei].qty_haigo).plus((item.qty_haigo || 0).toString()).toNumber();
                                    item.hijyu = addColumnMeisai.hijyu;
                                    item.no_kikaku = addColumnMeisai.no_kikaku;
                                    item.kbn_shikakari = addColumnMeisai.kbn_shikakari;
                                    item.cd_tani_hin = addColumnMeisai.cd_tani_hin;
                                    item.kbn_hin = addColumnMeisai.kbn_hin;
                                    item.no_seiho = entity["no_seiho"];
                                    item.hasHinmei = addColumnMeisai.hasHinmei;
                                    haigoTorikomiDialog.values.listHaigoMeisai[item.no_kotei].push(item);
                                }
                            }
                            $.each(data, function (key, item) {
                                listData.push(item);
                            })
                            haigoTorikomiDialog.bindDetailKotei(listData);
                        }).fail(function (error) {
                            haigoTorikomiDialog.notifyAlert.message(App.ajax.handleError(error).message).show();
                        }).always(function () {
                            App.ui.loading.close(loadingTaget);
                        });
                    }
                }
                else {

                    var loadingTaget = haigoTorikomiDialog.element.find(".modal-content");
                    App.ui.loading.show("", loadingTaget);

                    var parameter = {
                        cd_kaisha: haigoTorikomiDialog.options.parameter.cd_kaisha,
                        cd_kojyo: haigoTorikomiDialog.options.parameter.cd_kojyo,
                        cd_haigo: entity["cd_haigo"],
                        no_han: entity["no_han"],
                        qty_haigo_h: entity["qty_haigo_h"],
                        M_kirikae: haigoTorikomiDialog.options.parameter.M_kirikae 
                    }

                    $.ajax(App.ajax.webapi.get(haigoTorikomiDialog.urls.haigoTorikomiDialogController + "/getDataKoteiModeKojyo", parameter))
                    .done(function (result) {
                        var listData = [],
                            data = {};
                        haigoTorikomiDialog.values.listHaigoMeisai = {};

                        for (var i = 0 ; i < result.listHaigoMeisai.length; i++) {
                            var item = result.listHaigoMeisai[i],
                                addColumnMeisai = result.listAddColumnMeisai[i];
                            if (!data[item.no_kotei]) {
                                data[item.no_kotei] = {};
                                data[item.no_kotei].no_kotei = item.no_kotei;
                                data[item.no_kotei].nm_hin = item.nm_hin;
                                data[item.no_kotei].qty_haigo = item.qty_haigo ? 0 : item.qty_haigo;
                                haigoTorikomiDialog.values.listHaigoMeisai[item.no_kotei] = [];

                                haigoTorikomiDialog.values.listHaigoMeisai[item.no_kotei].push(item);
                            }
                            else {
                                data[item.no_kotei].qty_haigo = haigoTorikomiDialog.getBigNumber(data[item.no_kotei].qty_haigo).plus((item.qty_haigo || 0).toString()).toNumber();
                                haigoTorikomiDialog.values.listHaigoMeisai[item.no_kotei].push(item);
                            }

                            item.hijyu_label = addColumnMeisai.hijyu;
                            item.no_kikaku = addColumnMeisai.no_kikaku;
                            item.cd_tani_hin = addColumnMeisai.cd_tani_hin;
                            item.kbn_hin = addColumnMeisai.kbn_hin;
                            item.kbn_hin_toroku = addColumnMeisai.kbn_hin_toroku;
                            item.no_seiho = entity["no_seiho"];
                        }
                        $.each(data, function (key, item) {
                            listData.push(item);
                        })
                        haigoTorikomiDialog.bindDetailKotei(listData);
                    }).fail(function (error) {
                        haigoTorikomiDialog.notifyAlert.message(App.ajax.handleError(error).message).show();
                    }).always(function () {
                        App.ui.loading.close(loadingTaget);
                    });
                }
            }
        }
    }
    
    /**
     * Bind detail kotei.
     */
    haigoTorikomiDialog.bindDetailKotei = function (data) {
        var i, l, item, dataSet, dataCount;

        dataCount = data.Count ? data.Count : 0;
        data = (data.Items) ? data.Items : data;

        dataSet = App.ui.page.dataSet();
        haigoTorikomiDialog.detailKotei.data = dataSet;
        haigoTorikomiDialog.detailKotei.dataTable.dataTable("clear");

        if (data.length > 0) {
            haigoTorikomiDialog.element.findP("checkbox_kotei_all").prop("checked", false).prop("disabled", false);
        }

        haigoTorikomiDialog.detailKotei.dataTable.dataTable("addRows", data, function (row, item) {

            haigoTorikomiDialog.detailKotei.data.add(item);
            row.form().bind(item);
            
            return row;
        }, true);
        
        haigoTorikomiDialog.element.findP("data_count_kotei").text(data.length);
        haigoTorikomiDialog.element.findP("data_count_total_kotei").text(data.length);

        if (dataCount >= App.settings.base.maxInputDataCount) {
            haigoTorikomiDialog.notifyInfo.message(App.messages.base.MS0011).show();
        }
    }
    

    /**
     * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
     */
    haigoTorikomiDialog.selectKotei = function (e, row) {
        //選択行全体に背景色を付ける場合は以下のコメントアウトを解除します。
        if (!App.isUndefOrNull(haigoTorikomiDialog.detailKotei.selectedRow)) {
            haigoTorikomiDialog.detailKotei.selectedRow.element.find("tr").removeClass("selected-row");
        }
        row.element.find("tr").addClass("selected-row");
        haigoTorikomiDialog.detailKotei.selectedRow = row;
    };

    /**
     * change Kotei table;
     */
    haigoTorikomiDialog.changeKotei = function (e) {
        var target = $(e.target),
            property = target.attr("data-prop");

        var length = haigoTorikomiDialog.detailKotei.dataTable.find(".new .checkbox-kotei:checked").length;

        if (length == 0) {
            haigoTorikomiDialog.element.findP("checkbox_kotei_all").prop("checked", false);
        }

        if (length == haigoTorikomiDialog.detailKotei.dataTable.find(".new").length) {
            haigoTorikomiDialog.element.findP("checkbox_kotei_all").prop("checked", true);
        }
        else {
            haigoTorikomiDialog.element.findP("checkbox_kotei_all").prop("checked", false);
        }
    }

    //TODO: 複数セレクトを使用する場合は、上の単一セレクト用select関数を削除し、下の複数セレクト用の関数をコメント解除します
    //TODO: 単一セレクトの場合は、不要なコメント部分は削除してください

    ///**
    // * 一覧から行を選択された際の処理を実行します。（複数セレクト用）
    // */
    haigoTorikomiDialog.selectData = function (e) {
        var element = haigoTorikomiDialog.element,
            detail_kotei = element.find(".detail-kotei"),
            data;
        //debugger;
        var listKoteichecked = detail_kotei.find(".checkbox-kotei:checked");
        haigoTorikomiDialog.notifyAlert.remove("AP0008");
        haigoTorikomiDialog.notifyAlert.remove("AP0066");
        haigoTorikomiDialog.notifyAlert.remove("AP0009");
        if (listKoteichecked.length == 0) {
            haigoTorikomiDialog.notifyAlert.message(App.messages.app.AP0008, "AP0008").show();
            return;
        }
        
        haigoTorikomiDialog.detailKotei.validator.validate({
            targets: element.findP("bairitsu")
        }).then(function () {
            var bairitsu = Number(detail_kotei.findP("bairitsu").val());
            if (bairitsu == 0) {
                haigoTorikomiDialog.notifyAlert.message(App.messages.app.AP0066, "AP0066").show();
                return;
            }

            var data = [];
            for (var i = 0; i < listKoteichecked.length; i++) {
                var checked = $(listKoteichecked[i]),
                    row = checked.closest("tbody"),
                    no_kotei = row.findP("no_kotei").text();
                //debugger;
                for (var j = 0 ; j < haigoTorikomiDialog.values.listHaigoMeisai[no_kotei].length; j++) {
                    var item = $.extend({}, haigoTorikomiDialog.values.listHaigoMeisai[no_kotei][j]);
                    item.qty_haigo = App.isUndefOrNull(item.qty_haigo) ? null : haigoTorikomiDialog.getBigNumber(item.qty_haigo).times(bairitsu).toNumber();
                    if (!App.isUndefOrNull(item.qty_haigo) && item.qty_haigo > 99999999.999) {
                        haigoTorikomiDialog.notifyAlert.message(App.messages.app.AP0009.replace("{calculItem}", "配合重量").replace("{inputItems}", "倍率").replace("{maxChar}",""), "AP0009").show();
                        return;
                    }
                    data.push(item);
                }

            }

            if (App.isFunc(haigoTorikomiDialog.dataSelected)) {
                if (!haigoTorikomiDialog.dataSelected(data)) {
                    element.modal("hide");
                }
            }
            else {
                element.modal("hide");
            }

        })

        
    };

    ///**
    // * 一覧の行をクリックした際の処理を実行します。（複数セレクト用）
    // */
    //haigoTorikomiDialog.selectOne = function (e) {

    //    var target = $(e.target),
    //        tr = target.closest("tr");

    //    if (target.is("[name='select_cd']")) {
    //        return;
    //    }

    //    var check = tr.find("[name='select_cd']");
    //    if (check.is(":checked")) {
    //        check.prop("checked", false);
    //    } else {
    //        check.prop("checked", true);
    //    }
    //};

    ///**
    // * 一覧のヘッダ部のckeckbox(ALLチェック用）をクリックした際の処理を実行します。（複数セレクト用）
    // */
    //haigoTorikomiDialog.selectAll = function (e) {

    //    var $select_cd_all = $(e.target),
    //        isChecked = $select_cd_all.is(":checked");

    //    if (isChecked) {
    //        haigoTorikomiDialog.element.find("[name='select_cd']:visible").prop("checked", true);
    //    } else {
    //        haigoTorikomiDialog.element.find("[name='select_cd']:visible").prop("checked", false);
    //    }
    //    haigoTorikomiDialog.element.find("[name='select_cd']:visible").change();
    //};
    
    </script>

    <div class="modal fade wide" id="haigoTorikomiDialog" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog" style="height: 350px; width: 60%">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">配合取込</h4>
                </div>

                <div class="modal-body">
                    <!--TODO: 検索条件を定義するHTMLをここに記述します。-->

                    <div class="203-part">
                        <div class="row">
                            <div class="control-label col-xs-2">
                                <label>製法番号</label>
                            </div>
                            <div class="control col-xs-10">
                                <input type="tel" data-prop="no_seiho_kaisha" maxlength="4" class="limit-input-int no-seiho-dialog" disabled="disabled" style="width:45px;ime-mode:disabled"/>
                                <span style="width:5px;">-</span>
                                <select class="number no-seiho-dialog" data-prop="no_seiho_shurui" style="width:60px;"></select>
                                <span>-</span>
                                <input type="tel" maxlength="2" data-prop="no_seiho_nen" class="limit-input-int no-seiho-dialog" style="width:30px;ime-mode:disabled;"/>
                                <span>-</span>
                                <input type="tel" maxlength ="4" data-prop="no_seiho_renban" class="limit-input-int no-seiho-dialog" style="width:45px;ime-mode:disabled;"/>                           
                            </div>
                        </div>
                        <div class="row">
                            <div class="control-label col-xs-2">
                                <label>製法名</label>
                            </div>
                            <div class="control col-xs-10">
                                <input type="text" class="ime-active" data-prop="nm_seiho" style="width:300px;" />
                            </div>
                        </div>
                    </div>

                    <div class="209-part">
                        <div class="row">
                            <div class="control-label col-xs-2">
                                <label>配合コード</label>
                            </div>
                            <div class="control col-xs-2">
                                <input type="tel" data-prop="cd_haigo_search" maxlength="13" class="limit-input-int" style="ime-mode:disabled;"/>
                            </div>
                            <div class="control-label col-xs-2">
                                <label>配合名/コード</label>
                            </div>
                            <div class="control col-xs-6">
                                <input type="text" data-prop="nm_haigo_search" class="ime-active" />
                            </div>
                        </div>
                        <div class="row">
                            <div class="control-label col-xs-2">
                                <label>製法番号</label>
                            </div>
                            <div class="control col-xs-10">
                                <input type="tel" data-prop="no_seiho_kaisha" maxlength="4" class="limit-input-int no-seiho-dialog" style="width:45px;"/>
                                <span style="width:5px;">-</span>
                                <select class="number no-seiho-dialog" data-prop="no_seiho_shurui" style="width:60px;"></select>
                                <span>-</span>
                                <input type="tel" maxlength="2" data-prop="no_seiho_nen" class="limit-input-int no-seiho-dialog" style="width:30px;ime-mode:disabled;"/>
                                <span>-</span>
                                <input type="tel" maxlength ="4" data-prop="no_seiho_renban" class="limit-input-int no-seiho-dialog" style="width:45px;ime-mode:disabled;"/>                           
                            </div>
                        </div>
                    
                    </div>
                    <div style="position: relative; height: 50px;">
                        <button type="button" style="position: absolute; right: 5px; top: 5px;" class="btn btn-sm btn-primary search">検索</button>
                    </div>

                    <div>
                        <div class="part detail-haigo-torikomi">
                            <div class="control-label toolbar">
                                <i class="icon-th"></i>
                                <span class="data-count">
                                    <span data-prop="data_count_haigo"></span>
                                    <span>/</span>
                                    <span data-prop="data_count_total_haigo"></span>
                                </span>
                            </div>
                            <table class="datatable">
                                <thead>
                                    <tr>
                                    
                                        <th style="width: 30px;"></th>
                                        <th style="width: 150px;" class="203-part">製法番号</th>
                                        <th style="width: 200px;" class="203-part">製法名</th>
                                        <th style="width: 80px;">配合コード</th>
                                        <th>配合名</th>
                                        <th style="width: 200px;" class="203-part">代表工場</th>
                                        <th style="width: 30px;" class="209-part">版</th>
                                       <%-- <th style="">備考</th>--%>
                                    </tr>
                                </thead>
                                <tbody class="item-tmpl" style="cursor: pointer; display: none;">
                                    <tr>
                                        <td style="text-align:center;">
                                            <input type="checkbox" class="checkbox-haigo" data-prop="checkbox_haigo"/>
                                        </td>

                                        <td class="203-part">
                                            <label data-prop="no_seiho"></label>
                                        </td>

                                        <td class="203-part">
                                            <label class="overflow-ellipsis" data-prop="nm_seiho"></label>
                                        </td>
                                        <td style="text-align:right;">
                                            <label data-prop="cd_haigo"></label>
                                        </td>
                                        <td>
                                            <label class="overflow-ellipsis" data-prop="nm_haigo"></label>
                                        </td>
                                        <td class="203-part">
                                            <label class="overflow-ellipsis" data-prop="nm_daihyo"></label>
                                        </td>
                                        <td class="209-part" style="text-align:right;">
                                            <label data-prop="no_han"></label>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <div class="detail-command" style="text-align:center;">
                                <button type="button"  class="btn btn-sm btn-primary btn-next-search next-search" style="display: none; width:200px;">次を検索</button>
                            </div>
                            <div class="part-command">
                            </div>
                        </div>

                        <div class="part detail-kotei">
                            <div class="control-label toolbar">
                                <i class="icon-th"></i>
                                <span class="data-count">
                                    <span data-prop="data_count_kotei"></span>
                                    <span>/</span>
                                    <span data-prop="data_count_total_kotei"></span>
                                </span>
                            </div>
                            <table class="datatable">
                                <thead>
                                    <tr>
                                        <th style="width: 30px;"><input type="checkbox" class="checkbox-kotei-all" disabled="disabled" data-prop="checkbox_kotei_all"/></th>
                                        <th style="width: 60px;">工程</th>
                                        <th>工程名</th>
                                    </tr>
                                </thead>

                                <tbody class="item-tmpl" style="cursor: pointer; display: none;">
                                    <tr>
                                        <td style="text-align:center;">
                                            <input type="checkbox" class="checkbox-kotei" data-prop="checkbox_kotei"/>
                                        </td>

                                        <td>
                                            <label data-prop="no_kotei"></label>
                                        </td>

                                        <td>
                                            <label class="overflow-ellipsis" data-prop="nm_hin"></label>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <div style="padding-right: 16px;">                   
                                <div class="control col-xs-9">                           
                                </div>
                                <div class="control col-xs-3">
                                    <label>倍率</label>
                                    <input type="tel" class="limit-input-float" style="width:30%;text-align:right;ime-mode:disabled;" maxlength="5" value="1.00" data-prop="bairitsu"/>
                                </div>                   
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal-message message-area dialog-slideup-area">
                    <div class="alert-message" style="display: none">
                        <ul>
                        </ul>
                    </div>
                    <div class="info-message" style="display: none">
                        <ul>
                        </ul>
                    </div>
                </div>

                <div class="modal-footer">
                    <%--複数セレクトの場合は、下の１行をコメント解除してください--%>
                    <button type="button" class="btn btn-success select" name="select" >選択</button>
                    <button type="button" class="cancel-button btn btn-sm btn-default" data-dismiss="modal" name="close">閉じる</button>
                </div>

            </div>
        </div>
    </div>