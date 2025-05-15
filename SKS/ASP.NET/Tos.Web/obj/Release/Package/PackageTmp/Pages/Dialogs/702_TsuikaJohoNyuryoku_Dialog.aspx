<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="702_TsuikaJohoNyuryoku_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs.TsuikaJohoNyuryoku_Dialog" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver1.7)】 Template--%>
<style type="text/css">
    #tsuikaJohoNyuryokuDialog input[type="tel"] {
        width: 98%;
        border: 1px solid #cccccc;
        margin: 1px;
        padding: 1px;
        display: inline-block;
        font-family: Meiryo, MS PGothic, Segoe UI;
        ime-mode: inactive;
    }
</style>
<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var tsuikaJohoNyuryokuDialog = {
        options: {
            parameter: {
                cd_kaisha: null,
                cd_kojyo: null,
                flg_shoki: 1,
                no_haigo: null,
                no_gamen: null,
                kbn_hin: null,
                flg_edit: null,
                M_kirikae: 1,
                flg_seiho_base: null,
                no_han:null,
                M_HaigoToroku:null,
                modeReadOnly: false
            }
        },
        urls: {
            lineDialog: "Dialogs/707_LineKenSaKu_DiaLog.aspx",
            ma_bunrui: "../Services/ShisaQuickService.svc/ma_bunrui?$orderby=cd_bunrui",
            ma_setsubi: "../Services/ShisaQuickService.svc/ma_setsubi?$orderby=cd_setsubi",
            tsuikaJohoNyuryoku_Dialog: "../api/TsuikaJohoNyuryoku_Dialog"
        },
        values: {
            isChangeRunning: {},
            isChangeComplete: {}
        },

        header: {
            options: {},
        },
        detail: {
            options: {}
        }
    };

    /**
     * 検索ダイアログの初期化処理を行います。
     */
    tsuikaJohoNyuryokuDialog.initialize = function () {

        var element = $("#tsuikaJohoNyuryokuDialog"),
            table = element.find(".datatable"),
            datatable = table.dataTable({
                height: 185,
                resize: false,
                //列固定横スクロールにする場合は、下記3行をコメント解除
                //fixedColumn: true,                //列固定の指定
                //fixedColumns: 3,                  //固定位置を指定（左端を0としてカウント）
                //innerWidth: 1200,                 //可動列の合計幅を指定
                onselect: tsuikaJohoNyuryokuDialog.select,
                onchange: tsuikaJohoNyuryokuDialog.changeDetail
            });

        table = element.find(".datatable"); //列固定にした場合DOM要素が再作成されるため、変数を再取得します。

        tsuikaJohoNyuryokuDialog.dataTable = datatable;
        tsuikaJohoNyuryokuDialog.element = element;

        // 行選択時に利用するテーブルインデックスを指定します
        tsuikaJohoNyuryokuDialog.fixedColumnIndex = element.find(".fix-columns").length;

        element.on("shown.bs.modal", tsuikaJohoNyuryokuDialog.shown);
        element.on("hidden.bs.modal", tsuikaJohoNyuryokuDialog.hidden);
        element.on("click", "#add-item-tsuikaJohoNyuryokuDialog", tsuikaJohoNyuryokuDialog.addNewItem);
        element.on("click", "#del-item-tsuikaJohoNyuryokuDialog", tsuikaJohoNyuryokuDialog.deleteItem);
        element.on("click", "#up-tsuikaJohoNyuryokuDialog,#down-tsuikaJohoNyuryokuDialog", tsuikaJohoNyuryokuDialog.moveUpDown);
        element.on("click", ".lineDialog-tsuikaJohoNyuryokuDialog", tsuikaJohoNyuryokuDialog.showLineDialog);
        element.on("click", ".close-tsuikaJohoNyuryokuDialog", tsuikaJohoNyuryokuDialog.closeDialog);
        element.find(".tsuikaJohoNyuryokuDialog-header").on("change", ":input", tsuikaJohoNyuryokuDialog.changeHeader);
        element.find(".tsuikaJohoNyuryokuDialog-header").on("focus", ".format-when-focus", tsuikaJohoNyuryokuDialog.focusIn);
        element.find(".tsuikaJohoNyuryokuDialog-header").on("focusout", ".format-when-focus", tsuikaJohoNyuryokuDialog.focusOut);
        //TODO: 単一セレクトの場合は、下の１行を使用します

        tsuikaJohoNyuryokuDialog.element = element;

        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        tsuikaJohoNyuryokuDialog.header.validator = element.find(".tsuikaJohoNyuryokuDialog-header").validation(tsuikaJohoNyuryokuDialog.createValidator(tsuikaJohoNyuryokuDialog.header.options.validations));
        tsuikaJohoNyuryokuDialog.detail.validator = element.find(".tsuikaJohoNyuryokuDialog-detail").validation(tsuikaJohoNyuryokuDialog.createValidator(tsuikaJohoNyuryokuDialog.detail.options.validations));

        tsuikaJohoNyuryokuDialog.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#tsuikaJohoNyuryokuDialog .dialog-slideup-area .info-message",
                 messageContainerQuery: "ul",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        tsuikaJohoNyuryokuDialog.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#tsuikaJohoNyuryokuDialog .dialog-slideup-area .alert-message",
                messageContainerQuery: "ul",
                show: function () {
                    element.find(".alert-message").show();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                }
            });

        element.find(".modal-dialog").draggable({
            drag: true,
        });

        tsuikaJohoNyuryokuDialog.bind([], true);

        if (tsuikaJohoNyuryokuDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
            element.find(".mode-kojyo").remove();
            element.find(".not-mode-kojyo").show();
        }
        else {
            element.find(".not-mode-kojyo").remove();
            element.find(".mode-kojyo").show();
        }

        tsuikaJohoNyuryokuDialog.loadDialogs();
        
    };

    /**
    * Focus in 
    */
    tsuikaJohoNyuryokuDialog.focusIn = function (e) {
        var target = $(e.target),
            currentVal = target.val();
        target.val(currentVal.replace(/,/g, ""));
    }

    /**
     * Focus out 
     */
    tsuikaJohoNyuryokuDialog.focusOut = function (e) {
        var target = $(e.target),
            property = target.attr("data-prop");
        tsuikaJohoNyuryokuDialog.header.validator.validate({
            targets: target
        }).then(function () {
            if (property == "qty_max"){
                var valOld = target.val().replace(/,/g, "")
                if (valOld != "") {;
                    var valNew = App.num.format(Number(valOld), "#,#.000");
                    target.val(valNew);
                }
            }

            else if (property == "qty_kihon") {
                var valOld = target.val().replace(/,/g, "")
                if (valOld != "") {;
                    var valNew = App.num.format(Number(valOld), "#,#");
                    target.val(valNew);
                }
            }
        })
    }

    /**
     * close Dialog
     */
    tsuikaJohoNyuryokuDialog.closeDialog = function () {
        var element = tsuikaJohoNyuryokuDialog.element,
            data = {}, flg_error = false;

        tsuikaJohoNyuryokuDialog.notifyAlert.clear();

        tsuikaJohoNyuryokuDialog.validateAll().then(function () {
            for (var i = 0 ; i < tsuikaJohoNyuryokuDialog.element.find(".new").length; i++) {
                var row = $(tsuikaJohoNyuryokuDialog.element.find(".new")[i]),
                    cd_line = parseInt(row.findP("cd_line").val(), 10);
                if (App.isUndefOrNull(data[cd_line])) {
                    data[cd_line] = true;
                }
                else {
                    tsuikaJohoNyuryokuDialog.notifyAlert.message(App.str.format(App.messages.app.AP0047, "", row.findP("cd_line").val()), row.findP("cd_line")).show();
                    flg_error = true;
                }
            }

            if (flg_error) {
                return;
            }
            element.modal("hide");
        })
    }

    /**
     * Load master combobox
     */
    tsuikaJohoNyuryokuDialog.loadMasterData = function () {
        var deferred = $.Deferred();

        if(tsuikaJohoNyuryokuDialog.options.parameter.cd_kaisha){

            return tsuikaJohoNyuryokuDialog.loadComboboxBunrui().then(function () {
                return tsuikaJohoNyuryokuDialog.loadComboboxSetsubi()
            }).then(function () {
                deferred.resolve();
            }).fail(function (error) {
                deferred.reject();
            })
            return deferred.promise();
        }
        else {
            deferred.resolve();
            return deferred.promise();
        }
    }

    /**
     * Load data
     */
    tsuikaJohoNyuryokuDialog.loadData = function (clearValue,loadBegin) {
        var deferred = $.Deferred();

        tsuikaJohoNyuryokuDialog.resetStatusControl(clearValue);
        tsuikaJohoNyuryokuDialog.setStatusControl();

        return tsuikaJohoNyuryokuDialog.loadMasterData().then(function(){
        }).then(function(){
            return tsuikaJohoNyuryokuDialog.searchData(loadBegin)
        }).then(function(){
            deferred.resolve();
        }).fail(function(error){
            deferred.reject(error);
        })
        return deferred.promise();
    }

    /**
     * Search data in case not have no_haigo
     */
    tsuikaJohoNyuryokuDialog.searchDataInCaseNotHaveNoHaigo = function () {
        var element = tsuikaJohoNyuryokuDialog.element,
            table = element.find(".datatable"),
            modeKojyo = true,
            deferred = $.Deferred();

        if (tsuikaJohoNyuryokuDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
            modeKojyo = false;
        }

        var parameter = {
            cd_kaisha: tsuikaJohoNyuryokuDialog.options.parameter.cd_kaisha,
            cd_kojyo: tsuikaJohoNyuryokuDialog.options.parameter.cd_kojyo,
            modeKojyo: modeKojyo
        }

        return $.ajax(App.ajax.webapi.get(tsuikaJohoNyuryokuDialog.urls.tsuikaJohoNyuryoku_Dialog + "/getDataInCaseNotNoHaigo", parameter)
        ).then(function (result) {
            if (result.length > 0) {
                var item = result[0];

                tsuikaJohoNyuryokuDialog.options.parameter.su_linecode_standard = item.su_linecode_standard
                tsuikaJohoNyuryokuDialog.detail.options.validations.cd_line.rules.maxlength = item.su_linecode_standard
                table.find(".item-tmpl").findP("cd_line").prop("maxlength", item.su_linecode_standard);

                tsuikaJohoNyuryokuDialog.element.findP("budomari").val(App.num.format(Number(item.budomari),"#,#.00"));
                tsuikaJohoNyuryokuDialog.element.findP("hijyu").val(item.hijyu != null ? App.num.format(Number(item.hijyu),"#,#.000") : "");
                tsuikaJohoNyuryokuDialog.element.findP("qty_max").val(item.qty_max != null ? App.num.format(Number(item.qty_max), "#.000") : "");
                tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val(App.num.format(Number(item.qty_kihon), "#,#"));
                if (tsuikaJohoNyuryokuDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
                    tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi").val(item.cd_setsubi);
                    tsuikaJohoNyuryokuDialog.values.defaultSetsubi = item.cd_setsubi;
                } else {
                    var cd_setsubi_disp = App.common.fillString(item.cd_setsubi, 2);
                    tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi").val(cd_setsubi_disp);
                    tsuikaJohoNyuryokuDialog.values.defaultSetsubi = cd_setsubi_disp;
                }
                    
                var dataDetail = {
                        no_yusen: 1,
                        cd_line: tsuikaJohoNyuryokuDialog.formatLineCode(item.cd_line),
                        nm_line: item.nm_line
                    };
                tsuikaJohoNyuryokuDialog.dataTable.dataTable("addRow", function (tbody) {
                    tbody.form(tsuikaJohoNyuryokuDialog.options.bindOption).bind(dataDetail);
                    return tbody;
                }, true);
            }

        }).then(function () {
            deferred.resolve();
        }).fail(function (error) {
            deferred.reject(error);
        })
        return deferred.promise();

    }

    /**
     * Search data in case  have no_haigo
     */
    tsuikaJohoNyuryokuDialog.searchDataInCaseHaveNoHaigo = function () {
        var element = tsuikaJohoNyuryokuDialog.element,
            table = element.find(".datatable"),
            modeKojyo = true,
            deferred = $.Deferred();
        if (tsuikaJohoNyuryokuDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
            modeKojyo = false;
        }

        var parameter = {
            cd_kaisha: tsuikaJohoNyuryokuDialog.options.parameter.cd_kaisha,
            cd_kojyo: tsuikaJohoNyuryokuDialog.options.parameter.cd_kojyo,
            cd_haigo: tsuikaJohoNyuryokuDialog.options.parameter.no_haigo,
            M_kirikae: tsuikaJohoNyuryokuDialog.options.parameter.M_kirikae,
            modeKojyo: modeKojyo
        }

        return $.ajax(App.ajax.webapi.get(tsuikaJohoNyuryokuDialog.urls.tsuikaJohoNyuryoku_Dialog + "/getDataInCaseNoHaigo", parameter)
        ).then(function (result) {
            if (result.Header.cd_haigo) {
                var item = result.Header;

                tsuikaJohoNyuryokuDialog.options.parameter.su_linecode_standard = item.su_linecode_standard;
                tsuikaJohoNyuryokuDialog.detail.options.validations.cd_line.rules.maxlength = item.su_linecode_standard;
                table.find(".item-tmpl").findP("cd_line").prop("maxlength", item.su_linecode_standard)

                tsuikaJohoNyuryokuDialog.element.findP("budomari").val(App.num.format(Number(item.budomari), "#,#.00"));
                tsuikaJohoNyuryokuDialog.element.findP("hijyu").val(item.hijyu != null ? App.num.format(Number(item.hijyu), "#,#.000") : "");
                tsuikaJohoNyuryokuDialog.element.findP("qty_max").val(item.qty_max != null ? App.num.format(Number(item.qty_max), "#.000") : "");
                tsuikaJohoNyuryokuDialog.element.findP("qty_kihon").val(App.num.format(Number(item.qty_kihon), "#,#"));
                if (tsuikaJohoNyuryokuDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
                    tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi").val(item.cd_setsubi);
                    tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").val(item.cd_bunrui);
                    tsuikaJohoNyuryokuDialog.values.defaultSetsubi = item.cd_setsubi;
                }
                else {
                    tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi").val(item.cd_setsubi_string);
                    tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui").val(item.cd_bunrui_string);
                    tsuikaJohoNyuryokuDialog.values.defaultSetsubi = item.cd_setsubi_string;
                }

                if (item.flg_gasan) {
                    tsuikaJohoNyuryokuDialog.element.findP("flg_gasan").prop("checked", true);
                }

                if (item.flg_shorihin) {
                    tsuikaJohoNyuryokuDialog.element.findP("flg_shorihin").prop("checked", true);
                }

                if (result.Detail.length) {
                    for (var i = 0 ; i < result.Detail.length; i++) {
                        var dataDetail = {
                            no_yusen: result.Detail[i].no_yusen,
                            cd_line: tsuikaJohoNyuryokuDialog.formatLineCode(result.Detail[i].cd_line),
                            nm_line: result.Detail[i].nm_line
                        };
                        tsuikaJohoNyuryokuDialog.dataTable.dataTable("addRow", function (tbody) {
                            tbody.form(tsuikaJohoNyuryokuDialog.options.bindOption).bind(dataDetail);
                            return tbody;
                        }, true);
                    }
                }
            }
        }).then(function () {
            deferred.resolve();
        }).fail(function (error) {
            deferred.reject(error);
        })
        return deferred.promise();
    }

    /**
     * Format cd_line. EX: 1 -> 001
     */
    tsuikaJohoNyuryokuDialog.formatLineCode = function (value) {
        var pad = "0000000000";
        return pad.substr(0, tsuikaJohoNyuryokuDialog.options.parameter.su_linecode_standard - value.toString().length) + value;
    }

    /**
     * Search data
     */
    tsuikaJohoNyuryokuDialog.searchData = function (loadBegin) {
        var element = tsuikaJohoNyuryokuDialog.element,
            deferred = $.Deferred();

        if (!tsuikaJohoNyuryokuDialog.options.parameter.cd_kaisha) {

            if (loadBegin) {
                var dataDetail = {
                    no_yusen: 1
                };
                tsuikaJohoNyuryokuDialog.dataTable.dataTable("addRow", function (tbody) {
                    tbody.form(tsuikaJohoNyuryokuDialog.options.bindOption).bind(dataDetail);
                    tbody.addClass("tbody-remove");
                    return tbody;
                }, true);
            }
            
            deferred.resolve();
            return deferred.promise();
        }
        else {

            if (!tsuikaJohoNyuryokuDialog.options.parameter.no_haigo) {
                tsuikaJohoNyuryokuDialog.searchDataInCaseNotHaveNoHaigo().then(function () {
                    deferred.resolve();
                }).fail(function (error) {
                    deferred.reject(error);
                })
                return deferred.promise();
            }
            else {
                tsuikaJohoNyuryokuDialog.searchDataInCaseHaveNoHaigo().then(function () {
                    deferred.resolve();
                }).fail(function (error) {
                    deferred.reject(error);
                })
                return deferred.promise();
            }
        }
    }

    /**
    * Set enable status controls.
    */
    tsuikaJohoNyuryokuDialog.setStatusControl = function () {
        var element = tsuikaJohoNyuryokuDialog.element,
            table = element.find(".datatable"),
            cancelEdit;

        if (tsuikaJohoNyuryokuDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
            cancelEdit = tsuikaJohoNyuryokuDialog.options.parameter.flg_edit ? true : false;
        }
        else {
            if (tsuikaJohoNyuryokuDialog.options.parameter.flg_seiho_base || tsuikaJohoNyuryokuDialog.options.parameter.no_han != 1) {
                cancelEdit = false;
            }
            else {
                cancelEdit = true;
            }
        }

        if (cancelEdit) {

            element.findP("budomari").prop("disabled", false);
            element.findP("hijyu").prop("disabled", false);
            element.findP("cd_setsubi").prop("disabled", false);
            element.findP("qty_max").prop("disabled", false);
            element.findP("flg_shorihin").prop("disabled", false);
            element.find("#add-item-tsuikaJohoNyuryokuDialog").prop("disabled", false);
            element.find("#up-tsuikaJohoNyuryokuDialog").prop("disabled", false);
            element.find("#down-tsuikaJohoNyuryokuDialog").prop("disabled", false);
            element.find("#del-item-tsuikaJohoNyuryokuDialog").prop("disabled", false);

            if (tsuikaJohoNyuryokuDialog.options.parameter.kbn_hin == App.settings.app.kbnHin.shikakari) {
                element.findP("cd_bunrui").prop("disabled", false);
                element.findP("flg_gasan").prop("disabled", false);
            }

            if (tsuikaJohoNyuryokuDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kojyo
                && tsuikaJohoNyuryokuDialog.options.parameter.M_kirikae == App.settings.app.m_kirikae.foodprocs
                && (tsuikaJohoNyuryokuDialog.options.parameter.M_HaigoToroku == App.settings.app.m_haigo_toroku.shinki
                || tsuikaJohoNyuryokuDialog.options.parameter.M_HaigoToroku == App.settings.app.m_haigo_toroku.copy)
                ) {
                element.findP("qty_kihon").prop("disabled", false);

            }

            if (tsuikaJohoNyuryokuDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kojyo
                && tsuikaJohoNyuryokuDialog.options.parameter.M_kirikae == App.settings.app.m_kirikae.hyoji) {
                element.findP("qty_kihon").prop("disabled", false);
            }

            table.find(".item-tmpl").find(":input").prop("disabled", false);
        }
        
    }

    /**
    * Reset  status controls to disabled
    */
    tsuikaJohoNyuryokuDialog.resetStatusControl = function (clearValue) {
        var element = tsuikaJohoNyuryokuDialog.element,
            table = element.find(".datatable");

        element.find(":input").prop("disabled", true);
        element.find(".close-tsuikaJohoNyuryokuDialog").prop("disabled", false);
        table.find(".tbody-remove").remove();

        if (clearValue) {
            element.find(":input").val("");
            element.find(":checked").prop("checked", false);
            table.find("tbody").not(".item-tmpl").remove();

            tsuikaJohoNyuryokuDialog.notifyAlert.clear();
        }
    }

    /**
     * Load Bunrui combobox
     */
    tsuikaJohoNyuryokuDialog.loadComboboxBunrui = function () {
        var deferred = $.Deferred(),
            modeKojyo = true;
        if (tsuikaJohoNyuryokuDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
            modeKojyo = false;
        }

        var parameter = {
            cd_kaisha: tsuikaJohoNyuryokuDialog.options.parameter.cd_kaisha,
            cd_kojyo: tsuikaJohoNyuryokuDialog.options.parameter.cd_kojyo,
            kbn_hin: App.settings.app.kbnHin.shikakari,
            modeKojyo: modeKojyo
        }
                
        $.ajax(App.ajax.webapi.get(tsuikaJohoNyuryokuDialog.urls.tsuikaJohoNyuryoku_Dialog + "/getDataBunruiCombobox",parameter)
        ).then(function (result) {
            var cd_bunrui = tsuikaJohoNyuryokuDialog.element.findP("cd_bunrui");
            cd_bunrui.children().remove();
            App.ui.appendOptions(
                cd_bunrui,
                "cd_bunrui",
                "nm_bunrui",
                result,
                true
            );
        }).then(function () {
            deferred.resolve();
        }).fail(function (error) {
            deferred.reject(error);
        })
        return deferred.promise();
    }

    /**
     * Load Setsubi combobox
     */
    tsuikaJohoNyuryokuDialog.loadComboboxSetsubi = function () {
        var deferred = $.Deferred();
            modeKojyo = true;
            if (tsuikaJohoNyuryokuDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
            modeKojyo = false;
        }

        var parameter = {
            cd_kaisha: tsuikaJohoNyuryokuDialog.options.parameter.cd_kaisha,
            cd_kojyo: tsuikaJohoNyuryokuDialog.options.parameter.cd_kojyo,
            modeKojyo: modeKojyo
        }

        $.ajax(App.ajax.webapi.get(tsuikaJohoNyuryokuDialog.urls.tsuikaJohoNyuryoku_Dialog + "/getDataSetsubiCombobox", parameter)
        ).then(function (result) {
            var cd_setsubi = tsuikaJohoNyuryokuDialog.element.findP("cd_setsubi");
            cd_setsubi.children().remove();
            App.ui.appendOptions(
                cd_setsubi,
                "cd_setsubi",
                "nm_setsubi",
                result,
                true
            );
        }).then(function () {
            deferred.resolve();
        }).fail(function (error) {
            deferred.reject(error);
        })
        return deferred.promise();
       
    }

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    tsuikaJohoNyuryokuDialog.setColInvalidStyle = function (target) {
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
    tsuikaJohoNyuryokuDialog.setColValidStyle = function (target) {
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
     * バリデーション成功時の処理を実行します。
     */
    tsuikaJohoNyuryokuDialog.validationSuccess = function (results, state) {
        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                tsuikaJohoNyuryokuDialog.dataTable.dataTable("getRow", $(item.element), function (row) {
                    if (row && row.element) {
                        row.element.find("tr").removeClass("has-error");
                    }
                });
            } else {
                tsuikaJohoNyuryokuDialog.setColValidStyle(item.element);
            }

            tsuikaJohoNyuryokuDialog.notifyAlert.remove(item.element);
        }
    };

    /**
     * バリデーション失敗時の処理を実行します。
     */
    tsuikaJohoNyuryokuDialog.validationFail = function (results, state) {

        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                tsuikaJohoNyuryokuDialog.dataTable.dataTable("getRow", $(item.element), function (row) {
                    if (row && row.element) {
                        row.element.find("tr").addClass("has-error");
                    }
                });
            } else {
                tsuikaJohoNyuryokuDialog.setColInvalidStyle(item.element);
            }

            if (state && state.suppressMessage) {
                continue;
            }
            tsuikaJohoNyuryokuDialog.notifyAlert.message(item.message, item.element).show();
        }
    };

    /**
     * バリデーション後の処理を実行します。
     */
    tsuikaJohoNyuryokuDialog.validationAlways = function (results) {
        //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
    };

    /**
      * 指定された定義をもとにバリデータを作成します。
      * @param target バリデーション定義
      * @param options オプションに設定する値。指定されていない場合は、
      *                画面の success/fail/always のハンドル処理が指定されたオプションが設定されます。
      */
    tsuikaJohoNyuryokuDialog.createValidator = function (target, options) {
        return App.validation(target, options || {
            success: tsuikaJohoNyuryokuDialog.validationSuccess,
            fail: tsuikaJohoNyuryokuDialog.validationFail,
            always: tsuikaJohoNyuryokuDialog.validationAlways
        });
    };

    /**
    * ダイアログ表示時処理を実行します。
    */
    tsuikaJohoNyuryokuDialog.shown = function (e) {
        var element = tsuikaJohoNyuryokuDialog.element;
        if (tsuikaJohoNyuryokuDialog.options.parameter.modeReadOnly) {
            element.find("button").not(".cancel-button").not(".close-tsuikaJohoNyuryokuDialog").prop("disabled", true);
            element.find("input").prop("disabled", true);
            element.find("select").prop("disabled", true);
        }
    };

    /**
     * 検索ダイアログ非表示時処理を実行します。
     */
    tsuikaJohoNyuryokuDialog.hidden = function (e) {
        

        //var element = tsuikaJohoNyuryokuDialog.element,
        //    table = element.find(".datatable");

        ////TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
        //element.find(":input").val("");

        //element.findP("cd_bunrui").children().remove().prop("disabled", true);
        //element.findP("cd_setsubi").children().remove();
        //element.findP("flg_gasan").prop("disabled", true).prop("checked", false);
        //element.findP("flg_shorihin").prop("checked", false);

        ////TODO: 複数セレクトの場合は、下の１行をコメント解除します。
        ////element.find(":checked").prop("checked", false);
        //table.find("tbody").not(".item-tmpl").remove();

        //tsuikaJohoNyuryokuDialog.notifyInfo.clear();
        //tsuikaJohoNyuryokuDialog.notifyAlert.clear();
    };

    /**
     * 検索ダイアログ　バリデーションルールを定義します。
     */
    tsuikaJohoNyuryokuDialog.header.options.validations = {
        budomari: {
            rules: {
                required: true,
                number: true,
                range: [0, 999.99],
                pointlength: [3, 2]
            },
            options: {
                name: "歩留"
            },
            messages: {
                required: App.messages.base.required,
                number: App.messages.base.number,
                range: App.messages.base.range,
                pointlength: App.messages.base.pointlength
            }
        },

        hijyu: {
            rules: {
                required: true,
                number: true,
                range: [0, 99.999],
                pointlength: [2, 3]
            },
            options: {
                name: "比重"
            },
            messages: {
                required: App.messages.base.required,
                number: App.messages.base.number,
                range: App.messages.base.range,
                pointlength: App.messages.base.pointlength
            }
        },

        qty_max: {
            rules: {
                number: true,
                range: [0, 99999999.999],
                pointlength: [8, 3]
            },
            options: {
                name: "仕込最大重量"
            },
            messages: {
                number: App.messages.base.number,
                range: App.messages.base.range,
                pointlength: App.messages.base.pointlength
            }
        },

        qty_kihon: {
            rules: {
                required: true,
                number: true,
                range: [1, 99999999],
                pointlength: [8, 0]
            },
            options: {
                name: "基本重量"
            },
            messages: {
                required:App.messages.base.required,
                number: App.messages.base.number,
                range: App.messages.base.range,
                pointlength: App.messages.base.pointlength
            }
        }

        
    };

    tsuikaJohoNyuryokuDialog.detail.options.validations = {
        cd_line: {
            rules: {
                required: true,
                digits: true,
                maxlength: 3,
                checkExistsLine: function (value, opts, state, done) {
                    if (state && state.check) {
                        var tbody = state.tbody.element ? state.tbody.element : state.tbody
                        return done(tbody.findP("nm_line").text() != "");
                    }
                    return done(true);
                },
            },
            options: {
                name: "コード"
            },
            messages: {
                required: App.messages.base.required,
                digits: App.messages.base.digits,
                maxlength: App.messages.base.maxlength,
                checkExistsLine: App.messages.app.AP0010
            }
        }
    }

    /**
     * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
     */
    tsuikaJohoNyuryokuDialog.select = function (e) {
        var element = tsuikaJohoNyuryokuDialog.element,
            button = $(e.target),
            tbody = button.closest("tbody"),
            id = tbody.attr("data-key"),
            data;

        if (App.isUndef(id)) {
            return;
        }

        data = tsuikaJohoNyuryokuDialog.data.entry(id);

        if (App.isFunc(tsuikaJohoNyuryokuDialog.dataSelected)) {
            if (!tsuikaJohoNyuryokuDialog.dataSelected(data)) {
                element.modal("hide");
            }
        }
        else {
            element.modal("hide");
        }

    };

    /**
     * 共有ダイアログのロード処理を実行します。
     */
    tsuikaJohoNyuryokuDialog.loadDialogs = function () {

        //TODO: 共有ダイアログデータを取得し、画面にバインドする処理を記述します。
        return App.async.all({
            lineDialog: $.get(tsuikaJohoNyuryokuDialog.urls.lineDialog)
        }).then(function (result) {

            $("#dialog-container").append(result.successes.lineDialog);
            tsuikaJohoNyuryokuDialog.lineDialog = _707_LineKenSaKu_DiaLog;
            tsuikaJohoNyuryokuDialog.lineDialog.initialize();

        });
    }

    /**
     * 画面明細の各行にデータを設定する際のオプションを定義します。
     */
    tsuikaJohoNyuryokuDialog.options.bindOption = {
        // TODO: 主キーが直接入力の場合には、修正の場合変更を不可とします。
        //appliers: {
        //    no_seq: function (value, element) {
        //        element.val(value);
        //        element.prop("readonly", true).prop("tabindex", -1);
        //        return true;
        //    }
        //}
    };

    /**
     * 検索ダイアログの一覧にデータをバインドします。
     */
    tsuikaJohoNyuryokuDialog.bind = function (data, isNewData) {
        var i, l, item, dataSet, dataCount;

        dataCount = data.Count ? data.Count : 0;
        data = (data.Items) ? data.Items : data;

        dataSet = App.ui.page.dataSet();
        tsuikaJohoNyuryokuDialog.data = dataSet;
        tsuikaJohoNyuryokuDialog.dataTable.dataTable("clear");
        if (data && data.length) {
            tsuikaJohoNyuryokuDialog.values.defaultSetsubi = data[0].cd_setsubi;
        }

        tsuikaJohoNyuryokuDialog.dataTable.dataTable("addRows", data, function (row, item) {
            (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
            row.form(tsuikaJohoNyuryokuDialog.options.bindOption).bind(item);
            return row;
        }, true);

        //if (dataCount >= App.settings.base.maxInputDataCount) {
        //    App.ui.page.notifyInfo.message(App.messages.base.MS0011).show();
        //}

        //TODO: 画面明細へのデータバインド処理をここに記述します。


        //バリデーションを実行します。
        tsuikaJohoNyuryokuDialog.detail.validateList(true);

    };

    /**
     * 画面明細の一覧の行が選択された時の処理を行います。
     */
    tsuikaJohoNyuryokuDialog.select = function (e, row) {
        //TODO: 単一行を作成する場合は、下記２行を利用します。
        $($(row.element[tsuikaJohoNyuryokuDialog.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab.selected")).removeClass("selected").addClass("unselected");
        $(row.element[tsuikaJohoNyuryokuDialog.fixedColumnIndex].querySelectorAll(".select-tab")).removeClass("unselected").addClass("selected");
        //TODO: 多段行を作成する場合は、下記２行を有効にし、上記の２行は削除します。
        //$($(row.element[tsuikaJohoNyuryokuDialog.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab-2lines.selected")).removeClass("selected").addClass("unselected");
        //$(row.element[tsuikaJohoNyuryokuDialog.fixedColumnIndex].querySelectorAll(".select-tab-2lines")).removeClass("unselected").addClass("selected");

        //TODO: 選択行全体に背景色を付ける場合は以下のコメントアウトを解除します。
        //if (!App.isUndefOrNull(tsuikaJohoNyuryokuDialog.selectedRow)) {
        //    tsuikaJohoNyuryokuDialog.selectedRow.element.find(".selected-row").removeClass("selected-row");
        //}
        //row.element.find("tr").addClass("selected-row");
        //tsuikaJohoNyuryokuDialog.selectedRow = row;
    };
    
    /**
     * Change header
     */
    tsuikaJohoNyuryokuDialog.changeHeader = function (e) {

        var element = tsuikaJohoNyuryokuDialog.element,
            target = $(e.target),
            property = target.attr("data-prop");

        $("#save").prop("disabled", false);

        tsuikaJohoNyuryokuDialog.header.validator.validate({
            targets: target
        }).then(function () {
            if (property == "budomari") {
                var valOld = target.val();
                if (valOld != "") {
                    target.val(App.num.format(Number(valOld), "#,#.00"))
                }
            }
            else if (property == "qty_max") {
                var valOld = target.val();
                if (valOld != "") {
                    target.val(App.num.format(Number(valOld), "#,#.000"))
                }
            }
            else if (property == "hijyu") {
                var valOld = target.val();
                if (valOld != "") {
                    target.val(App.num.format(Number(valOld), "#,#.000"))
                }
            }
            else if (property == "qty_kihon") {
                var valOld = target.val();
                if (valOld != "") {
                    target.val(App.num.format(Number(valOld), "#,#"))
                }
            }
            else if (property == "cd_setsubi") {
                tsuikaJohoNyuryokuDialog.values.isChangeSetsubi = true;
            }
        })
    }
   
    /**
     * 画面明細の一覧の入力項目の変更イベントの処理を行います。
     */
    tsuikaJohoNyuryokuDialog.changeDetail = function (e, row) {
        var target = $(e.target),
            loadingTaget = tsuikaJohoNyuryokuDialog.element.find(".modal-content");

        $("#save").prop("disabled", false);

        tsuikaJohoNyuryokuDialog.detail.validator.validate({
            targets: target,
            state: {
                tbody: row.element,
                isGridValidation: true
            }
        }).then(function () {
            var valOld = parseInt(target.val(), 10),
                valNew = tsuikaJohoNyuryokuDialog.formatLineCode(valOld),
                modeKojyo = true;

            if (tsuikaJohoNyuryokuDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
                modeKojyo = false;
            }

            var parameter = 
                {
                    cd_kaisha: tsuikaJohoNyuryokuDialog.options.parameter.cd_kaisha,
                    cd_kojyo: tsuikaJohoNyuryokuDialog.options.parameter.cd_kojyo,
                    cd_line: valOld,
                    su_linecode_standard: tsuikaJohoNyuryokuDialog.options.parameter.su_linecode_standard,
                    modeKojyo: modeKojyo
                };

            target.val(valNew);

            App.ui.loading.show("", loadingTaget);

            $.ajax(App.ajax.webapi.get(tsuikaJohoNyuryokuDialog.urls.tsuikaJohoNyuryoku_Dialog + "/getLineName",parameter)
            ).then(function (result) {
                if (result) {
                    row.element.findP("nm_line").text(result.nm_line);
                    tsuikaJohoNyuryokuDialog.notifyAlert.remove(target);
                }
                else {
                    row.element.findP("nm_line").text("");
                    
                }
                tsuikaJohoNyuryokuDialog.detail.validator.validate({
                    targets: row.element.findP("cd_line"),
                    state: {
                        tbody: row.element,
                        isGridValidation: true,
                        check: true
                    }
                });

            }).fail(function (error) {
                row.element.findP("nm_line").text("");
                tsuikaJohoNyuryokuDialog.detail.validator.validate({
                    targets: row.element.findP("cd_line"),
                    state: {
                        tbody: row.element,
                        isGridValidation: true,
                        check: true
                    }
                });
            }).always(function () {
                App.ui.loading.close(loadingTaget);
            })
        }).fail(function () {
            row.element.findP("nm_line").text("");
        })
    };

    

    /**
     * 画面明細の一覧に新規データを追加します。
     */
    tsuikaJohoNyuryokuDialog.addNewItem = function () {
        //TODO:新規データおよび初期値を設定する処理を記述します。
        $("#save").prop("disabled", false);
        var maxValue = tsuikaJohoNyuryokuDialog.dataTable.find("tbody").not(".item-tmpl").length + 1
        var newData = {
            no_yusen: maxValue
        };

        //tsuikaJohoNyuryokuDialog.data.add(newData);
        tsuikaJohoNyuryokuDialog.dataTable.dataTable("addRow", function (tbody) {
            tbody.form(tsuikaJohoNyuryokuDialog.options.bindOption).bind(newData);
            return tbody;
        }, true);
    };

    /**
     * 画面明細の一覧で選択されている行とデータを削除します。
     */
    tsuikaJohoNyuryokuDialog.deleteItem = function (e) {
        var element = tsuikaJohoNyuryokuDialog.element,
            table = element.find(".datatable"),
            //TODO: 単一行を作成する場合は、下記を利用します。
            selected = element.find(".datatable .select-tab.selected").closest("tbody");
        //TODO: 多段行を作成する場合は、以下を利用し、上記１行は削除します。


        tsuikaJohoNyuryokuDialog.notifyAlert.remove("tsuikaJohoNyuryokuDialog-AP0008");

        if (!selected.length) {
            tsuikaJohoNyuryokuDialog.notifyAlert.message(App.messages.app.AP0008, "tsuikaJohoNyuryokuDialog-AP0008").show();
            return;
        }

        $("#save").prop("disabled", false);

        tsuikaJohoNyuryokuDialog.dataTable.dataTable("deleteRow", selected, function (row) {
            var id = row.attr("data-key"),
                newSelected;

            row.find(":input").each(function (i, elem) {
                tsuikaJohoNyuryokuDialog.notifyAlert.remove(elem);
            });

            if (!App.isUndefOrNull(id)) {
                var entity = tsuikaJohoNyuryokuDialog.data.entry(id);
                tsuikaJohoNyuryokuDialog.data.remove(entity);
            }

            newSelected = row.next().not(".item-tmpl");
            if (!newSelected.length) {
                newSelected = row.prev().not(".item-tmpl");
            }
            if (newSelected.length) {
                for (var i = tsuikaJohoNyuryokuDialog.fixedColumnIndex; i > -1; i--) {
                    if ($(newSelected[i]).find(":focusable:first").length) {
                        $(newSelected[i]).find(":focusable:first").focus();
                        break;
                    }
                }
            }

            // Set priority of next row when delete seleted row
            tsuikaJohoNyuryokuDialog.updateRowIndex(row);
        });

        var length = table.find("tbody").not(".item-tmpl").length;
        if (!length) {
            tsuikaJohoNyuryokuDialog.dataTable.dataTable("addRow", function (tbody) {
                tbody.form(tsuikaJohoNyuryokuDialog.options.bindOption).bind({ no_yusen: 1 });
                return tbody;
            }, true);
        }
    };

    /**
     * Move selected row UP or DOWN in table
     */
    tsuikaJohoNyuryokuDialog.moveUpDown = function (e) {
        var element = tsuikaJohoNyuryokuDialog.element,
            selected = element.find(".datatable .select-tab.selected").closest("tbody"),
            $reindex_start,
            no_seq = 0;

        if (!selected.length) {
            return;
        }

        $("#save").prop("disabled", false);

        if ($(this).is('#up-tsuikaJohoNyuryokuDialog')) {
            // Get Prev Row
            $reindex_start = selected.prev().not(".item-tmpl");
            // Set priority of row
            if ($reindex_start.length) {
                no_seq = Number(selected.findP("no_yusen").text());
                selected.findP("no_yusen").text(no_seq - 1);
                $reindex_start.findP("no_yusen").text(no_seq);
            }
            // Change postion of selected row and focus to selected row
            selected.insertBefore($reindex_start);
            selected.find(":focusable:first").focus();
        } else {
            // Get Next Row
            $reindex_start = selected.next().not(".item-tmpl");
            // Set priority of row
            if ($reindex_start.length) {
                no_seq = Number($reindex_start.findP("no_yusen").text());
                selected.findP("no_yusen").text(no_seq);
                $reindex_start.findP("no_yusen").text(no_seq - 1);
            }
            // Change postion of selected row and focus to selected row
            selected.insertAfter($reindex_start);
            selected.find(":focusable:first").focus();
        }
    };

    /**
     *  Set priority of next row when delete any row
     */
    tsuikaJohoNyuryokuDialog.updateRowIndex = function (row) {
        var nextRow = row.next().not(".item-tmpl");
        while (nextRow.length > 0) {
            var no_seq = Number(nextRow.findP("no_yusen").text());
            nextRow.findP("no_yusen").text(no_seq - 1);
            nextRow = nextRow.next().not(".item-tmpl");
        }
    };

    /**
    * 画面明細の一覧から検索ダイアログを表示します。
    */
    tsuikaJohoNyuryokuDialog.showLineDialog = function (e) {
        var element = tsuikaJohoNyuryokuDialog.element,
            tbody = $(e.target).closest("tbody"),
            row;

        tsuikaJohoNyuryokuDialog.dataTable.dataTable("getRow", tbody, function (rowObject) {
            row = rowObject.element;
        });

        tsuikaJohoNyuryokuDialog.lineDialog.parameter.cd_kaisha = tsuikaJohoNyuryokuDialog.options.parameter.cd_kaisha;
        tsuikaJohoNyuryokuDialog.lineDialog.parameter.cd_kojyo = tsuikaJohoNyuryokuDialog.options.parameter.cd_kojyo;

        if (tsuikaJohoNyuryokuDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
            tsuikaJohoNyuryokuDialog.lineDialog.parameter.kbn_bumon = 1;
        }
        else {
            tsuikaJohoNyuryokuDialog.lineDialog.parameter.kbn_bumon = 2;
        }

        tsuikaJohoNyuryokuDialog.lineDialog.element.modal("show");

        //検索ダイアログでデータ選択が実行された時に呼び出される関数を設定しています。
        tsuikaJohoNyuryokuDialog.lineDialog.dataSelected = function (data) {
            
            row.findP("cd_line").val(tsuikaJohoNyuryokuDialog.formatLineCode(data.cd_line));
            row.findP("nm_line").text(data.nm_line);

            tsuikaJohoNyuryokuDialog.detail.validator.validate({
                targets: row.findP("cd_line"),
                state: {
                    tbody: row,
                    isGridValidation: true,
                    check: true
                }
            });

            $("#save").prop("disabled", false);

            delete tsuikaJohoNyuryokuDialog.lineDialog.dataSelected;
        }
    };

    /**
     * 画面明細のバリデーションを実行します。
     */
    tsuikaJohoNyuryokuDialog.detail.executeValidation = function (targets, row, options) {
        var defaultOptions = {
            targets: targets,
            state: {
                tbody: row,
                isGridValidation: true
            }
        },
        execOptions = $.extend(true, {}, defaultOptions, options);

        return tsuikaJohoNyuryokuDialog.detail.validator.validate(execOptions);
    };

    /**
         * すべてのバリデーションを実行します。
         */
    tsuikaJohoNyuryokuDialog.validateAll = function () {

        var validations = [];

        validations.push(tsuikaJohoNyuryokuDialog.header.validator.validate({
            targets: tsuikaJohoNyuryokuDialog.element.find(".tsuikaJohoNyuryokuDialog-header :input:not([disabled])")
        }));
        validations.push(tsuikaJohoNyuryokuDialog.detail.validateList());

        //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。

        return App.async.all(validations);
    };

    /**
     * 画面明細のバリデーションフィルターを設定します。（必須チェックを行わない）
     */
    tsuikaJohoNyuryokuDialog.validationFilter = function (item, method, state, options) {
        return method !== "required";
    };

    /**
     * 画面明細の一覧のバリデーションを実行します。
     */
    tsuikaJohoNyuryokuDialog.detail.validateList = function (suppressMessage) {
        var validations = [],
            options = {
                state: {
                    suppressMessage: suppressMessage,
                    check: true
                }
            };

        tsuikaJohoNyuryokuDialog.dataTable.dataTable("each", function (row) {
            if (row.element.hasClass("item-tmpl")) {
                return;
            }

            validations.push(tsuikaJohoNyuryokuDialog.detail.executeValidation(row.element.find(":input:not([disabled])"), row.element, options));
        });

        return App.async.all(validations);
    };

    //TODO: 複数セレクトを使用する場合は、上の単一セレクト用select関数を削除し、下の複数セレクト用の関数をコメント解除します
    //TODO: 単一セレクトの場合は、不要なコメント部分は削除してください
    <%--
        /**
         * 一覧から行を選択された際の処理を実行します。（複数セレクト用）
         */
        tsuikaJohoNyuryokuDialog.select = function (e) {
            var element = tsuikaJohoNyuryokuDialog.element,
                data;
            //TODO: 選択された行から起動元画面に返却したい値を抽出します
            var items = element.find(".search-list").find("input:checked[name='select_cd']").map(function (index, item) {
                var tbody = $(item).closest("tbody");
                var id = tbody.attr("data-key");
                var data = tsuikaJohoNyuryokuDialog.data.entry(id);
                //return data.cd_torihiki;
            }).toArray();

            if (items.length == 0) {
                tsuikaJohoNyuryokuDialog.notifyAlert.message(App.messages.base.MS0020).show();
                return;
            }

            if (App.isFunc(tsuikaJohoNyuryokuDialog.dataSelected)) {
                if (!tsuikaJohoNyuryokuDialog.dataSelected(items)) {
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
        tsuikaJohoNyuryokuDialog.selectOne = function (e) {

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
        tsuikaJohoNyuryokuDialog.selectAll = function (e) {

            var $select_cd_all = $(e.target),
                isChecked = $select_cd_all.is(":checked");

            if (isChecked) {
                tsuikaJohoNyuryokuDialog.element.find("[name='select_cd']:visible").prop("checked", true);
            } else {
                tsuikaJohoNyuryokuDialog.element.find("[name='select_cd']:visible").prop("checked", false);
            }
            tsuikaJohoNyuryokuDialog.element.find("[name='select_cd']:visible").change();
        };
--%>
</script>

<div class="modal fade wide" tabindex="-1" id="tsuikaJohoNyuryokuDialog" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog" style="height: 350px; width: 800px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close close-tsuikaJohoNyuryokuDialog">&times;</button>
                <h4 class="modal-title">追加情報入力</h4>
            </div>

            <div class="modal-body">
                <div class="tsuikaJohoNyuryokuDialog-header">
                    <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
                    <div class="row">
                        <div class="control-label col-xs-2">
                            <label>歩留<span style="color: red">*</span></label>
                        </div>
                        <div class="control col-xs-2">
                            <input class="text-right limit-input-float" maxlength="6" style="ime-mode:disabled;" type="tel" data-prop="budomari" />
                        </div>
                        <div class="control col-xs-2">
                            <label>%</label>
                        </div>
                        <div class="control-label col-xs-2">
                            <label>比重<span style="color: red">*</span></label>
                        </div>
                        <div class="control col-xs-2">
                            <input class="text-right limit-input-float" maxlength="6" style="ime-mode:disabled;" type="tel" data-prop="hijyu" />
                        </div>
                        <div class="control col-xs-2">
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-label col-xs-2">
                            <label>仕掛品分類</label>
                        </div>
                        <div class="control col-xs-3">
                            <select class="number" disabled="disabled" data-prop="cd_bunrui">
                            </select>
                        </div>
                        <div class="control col-xs-1">
                        </div>
                        <div class="control-label col-xs-2">
                            <label>設備</label>
                        </div>
                        <div class="control col-xs-3">
                            <select class="number" data-prop="cd_setsubi">
                            </select>
                        </div>
                        <div class="control col-xs-1">
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-label col-xs-2">
                            <label>仕込最大重量</label>
                        </div>
                        <div class="control col-xs-3">
                            <input class="text-right limit-input-float format-when-focus" style="ime-mode:disabled;" maxlength="12" type="tel" data-prop="qty_max" />
                        </div>
                        <div class="control col-xs-1">
                        </div>
                        <div class="control col-xs-6 not-mode-kojyo">
                            <label class="check-group">
                                <input data-prop="flg_gasan" disabled="disabled" type="checkbox" value="1" />
                                <label>仕込合算あり</label>
                            </label>
                        </div>

                        <div class="control-label col-xs-2 mode-kojyo" style="display:none;">
                            <label>基本重量<span style="color: red">*</span></label>
                        </div>
                        <div class="control col-xs-2 mode-kojyo" style="display:none;">
                            <input class="text-right limit-input-int" maxlength="8" style="ime-mode:disabled;" type="tel" data-prop="qty_kihon" />
                        </div>
                        <div class="control col-xs-2 mode-kojyo" style="display:none;">
                        </div>
                    </div>

                    <div class="row mode-kojyo" style="display:none;">
                        <div class="control-label col-xs-6">
                            <label class="check-group">
                                <input data-prop="flg_shorihin" value ="1" type="checkbox" />
                                処理品区分
                            </label>
                        </div>
                        <div class="control col-xs-6">
                            <label class="check-group">
                                <input data-prop="flg_gasan" disabled="disabled" value="1" type="checkbox" />
                                仕込合算あり
                            </label>
                        </div>
                    </div>
                </div>

                <div class="tsuikaJohoNyuryokuDialog-detail">
                    <!--TODO: 明細をpartにする場合は以下を利用します。
                    <div title="TODO: 明細一覧部のタイトルを設定します。"  class="part">
                    -->
                    <div class="control-label toolbar">
                        <i class="icon-th"></i>
                        <div class="btn-group">
                            <button type="button" class="btn btn-default btn-xs" id="add-item-tsuikaJohoNyuryokuDialog">行追加</button>
                            <button type="button" class="btn btn-default btn-xs" id="up-tsuikaJohoNyuryokuDialog">行移動▲</button>
                            <button type="button" class="btn btn-default btn-xs" id="down-tsuikaJohoNyuryokuDialog">行移動▼</button>
                            <button type="button" class="btn btn-default btn-xs" id="del-item-tsuikaJohoNyuryokuDialog">行削除</button>
                        </div>
                        <%--<span class="data-count">
                                <span data-prop="data_count"></span>
                                <span>/</span>
                                <span data-prop="data_count_total"></span>
                            </span>--%>
                    </div>
                    <table class="datatable">
                        <!--TODO: 明細一覧のヘッダーを定義するHTMLをここに記述します。-->
                        <thead>
                            <tr>
                                <!--TODO: 単一行を作成する場合は、下記を利用します。-->
                                <th style="width: 10px;"></th>
                                <th style="width: 70px;">優先順位</th>
                                <th style="width: 150px;">コード</th>
                                <th style="">ライン名</th>
                            </tr>
                        </thead>
                        <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->
                        <tbody class="item-tmpl" style="cursor: default; display: none;">
                            <tr>
                                <!--TODO: 単一行を作成する場合は、以下を利用します。-->
                                <td>
                                    <span class="select-tab unselected"></span>
                                </td>
                                <td class="text-right">
                                    <span data-prop="no_yusen"></span>
                                </td>
                                <td>
                                    <input type="tel" data-prop="cd_line" maxlength="3" class="limit-input-int" style="width: 65%;ime-mode:disabled" />
                                    <%--<input type="text" data-prop="cd_line_text" style="display: none" />--%>
                                    <button type="button" class="btn btn-xs btn-info lineDialog-tsuikaJohoNyuryokuDialog" style="min-width: 25px; margin-right: 5px;">検索</button>
                                </td>
                                <td>
                                    <span data-prop="nm_line"></span>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <div class="part-command">
                    </div>

                    <!--TODO: 明細をpartにする場合は以下を利用します。
                    </div>
                    -->
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
            </div>

            <div class="modal-footer">
                <%--複数セレクトの場合は、下の１行をコメント解除してください--%>
                <%--<button type="button" class="btn btn-success select" name="select" >選択</button>--%>
                <%--<button type="button" class="cancel-button btn btn-sm btn-default close-tsuikaJohoNyuryokuDialog" data-dismiss="modal" name="close">閉じる</button>--%>
                <button type="button" class="cancel-button btn btn-sm btn-default close-tsuikaJohoNyuryokuDialog">閉じる</button>
            </div>

        </div>
    </div>
</div>
