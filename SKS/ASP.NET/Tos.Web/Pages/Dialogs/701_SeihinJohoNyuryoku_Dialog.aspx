<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="701_SeihinJohoNyuryoku_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs.SeihinJohoNyuryoku_Dialog" %>
<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver1.7)】 Template--%>

    <style type="text/css">
        .height-up {
            height:55px;
        }
    </style>

    <script type="text/javascript">


        /**
         * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
         */
        var seihinJohoNyuryokuDialog = {
            options: {
                mode: null,
                parameter:
                {
                    cd_kaisha: null,
                    cd_kojyo: null,
                    flg_shoki: 1,
                    no_haigo: null,
                    no_gamen: null,
                    flg_edit: null,
                    M_kirikae: 1,
                    flg_seiho_base: null,
                    su_code_standard: null,
                    modeReadOnly: false
                }
            },
            urls: {
                seihinKensakuDialog: "Dialogs/703_SeihinKensaku_Dialog.aspx",
                searchSeihin: "../Services/SEIHODATAService.svc/vw_seihin_kaihatsu_bumon?$filter=cd_hin eq {0}M&$inlinecount=allpages&$top=1",
                seihinJohoNyuryoku_Dialog: "../api/SeihinJohoNyuryoku_Dialog"
            },
            seihinDialog: {},
            confirmDialog:{},
            values: {
                isChangeRunning: {},
                isChangeComplete: {},
                lstSeihin: []
            }
        };

        /**
         * 検索ダイアログの初期化処理を行います。
         */
        seihinJohoNyuryokuDialog.initialize = function () {

            var element = $("#seihinJohoNyuryokuDialog"),
                table = element.find(".datatable"),
                datatable = table.dataTable({
                    height: 185,
                    resize: false,
                    //列固定横スクロールにする場合は、下記3行をコメント解除
                    //fixedColumn: true,                //列固定の指定
                    //fixedColumns: 3,                  //固定位置を指定（左端を0としてカウント）
                    //innerWidth: 1200,                 //可動列の合計幅を指定
                    onselect: seihinJohoNyuryokuDialog.select,
                    onchange: seihinJohoNyuryokuDialog.change
                });

            table = element.find(".datatable"); //列固定にした場合DOM要素が再作成されるため、変数を再取得します。

            seihinJohoNyuryokuDialog.dataTable = datatable;

            //// Load dialogs
            seihinJohoNyuryokuDialog.loadDialogs();

            // 行選択時に利用するテーブルインデックスを指定します
            seihinJohoNyuryokuDialog.fixedColumnIndex = element.find(".fix-columns").length;

            element.on("hidden.bs.modal", seihinJohoNyuryokuDialog.hidden);
            element.on("shown.bs.modal", seihinJohoNyuryokuDialog.shown);
            element.on("click", "#add-item-seihinJohoNyuryokuDialog", seihinJohoNyuryokuDialog.addNewItem);
            element.on("click", "#del-item-seihinJohoNyuryokuDialog", seihinJohoNyuryokuDialog.deleteItem);
            element.on("click", "#up-seihinJohoNyuryokuDialog,#down-seihinJohoNyuryokuDialog", seihinJohoNyuryokuDialog.moveUpDown);
            element.on("click", ".cd_hin-seihinJohoNyuryokuDialog", seihinJohoNyuryokuDialog.showSeihinKensakuDialog);

            element.on("click", ".close-seihinJohoNyuryokuDialog", seihinJohoNyuryokuDialog.closeDialog);
          

            //TODO: 単一セレクトの場合は、下の１行を使用します

            seihinJohoNyuryokuDialog.element = element;

            element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
            element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

            seihinJohoNyuryokuDialog.validator = element.validation(seihinJohoNyuryokuDialog.createValidator(seihinJohoNyuryokuDialog.options.validations));

            seihinJohoNyuryokuDialog.notifyInfo =
                 App.ui.notify.info(element, {
                     container: "#seihinJohoNyuryokuDialog .dialog-slideup-area .info-message",
                     messageContainerQuery: "ul",
                     show: function () {
                         element.find(".info-message").show();
                     },
                     clear: function () {
                         element.find(".info-message").hide();
                     }
                 });
            seihinJohoNyuryokuDialog.notifyAlert =
                App.ui.notify.alert(element, {
                    container: "#seihinJohoNyuryokuDialog .dialog-slideup-area .alert-message",
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
            
            if (seihinJohoNyuryokuDialog.options.parameter.no_gamen != App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
                element.find(".caution").hide();
            }
        };

        /**
         * 単項目要素をエラーのスタイルに設定します。
         * @param target 設定する要素
         */
        seihinJohoNyuryokuDialog.setColInvalidStyle = function (target) {
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
        seihinJohoNyuryokuDialog.setColValidStyle = function (target) {
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
        seihinJohoNyuryokuDialog.validationSuccess = function (results, state) {
            var i = 0, l = results.length,
                item, $target;

            for (; i < l; i++) {
                item = results[i];
                if (state && state.isGridValidation) {
                    seihinJohoNyuryokuDialog.dataTable.dataTable("getRow", $(item.element), function (row) {
                        if (row && row.element) {
                            row.element.find("tr").removeClass("has-error");
                        }
                    });
                } else {
                    seihinJohoNyuryokuDialog.setColValidStyle(item.element);
                }

                seihinJohoNyuryokuDialog.notifyAlert.remove(item.element);
            }
        };

        /**
         * バリデーション失敗時の処理を実行します。
         */
        seihinJohoNyuryokuDialog.validationFail = function (results, state) {

            var i = 0, l = results.length,
                item, $target;

            for (; i < l; i++) {
                item = results[i];
                if (state && state.isGridValidation) {
                    seihinJohoNyuryokuDialog.dataTable.dataTable("getRow", $(item.element), function (row) {
                        if (row && row.element) {
                            row.element.find("tr").addClass("has-error");
                        }
                    });
                } else {
                    seihinJohoNyuryokuDialog.setColInvalidStyle(item.element);
                }

                if (state && state.suppressMessage) {
                    continue;
                }
                seihinJohoNyuryokuDialog.notifyAlert.message(item.message, item.element).show();
            }
        };

        /**
         * バリデーション後の処理を実行します。
         */
        seihinJohoNyuryokuDialog.validationAlways = function (results) {
            //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
        };

        /**
          * 指定された定義をもとにバリデータを作成します。
          * @param target バリデーション定義
          * @param options オプションに設定する値。指定されていない場合は、
          *                画面の success/fail/always のハンドル処理が指定されたオプションが設定されます。
          */
        seihinJohoNyuryokuDialog.createValidator = function (target, options) {
            return App.validation(target, options || {
                success: seihinJohoNyuryokuDialog.validationSuccess,
                fail: seihinJohoNyuryokuDialog.validationFail,
                always: seihinJohoNyuryokuDialog.validationAlways
            });
        };

        /**
         * 検索ダイアログ非表示時処理を実行します。
         */
        seihinJohoNyuryokuDialog.hidden = function (e) {

            var element = seihinJohoNyuryokuDialog.element,
                length = element.find(".new").length;

            for (var i = 0; i < length; i++) {
                var row = $(element.find(".new")[i]);

                if (row.findP("cd_hin").val().toString() == "") {
                    row.addClass("new-edit");
                    continue;
                }
                row.findP("no_yusen").text([i + 1]);
            }

            element.find(".new-edit").removeClass("new");

            var rows = element.find(".new");
            if (rows.length > 0) {
                var row = $(rows[0]);
                if (App.isFunc(seihinJohoNyuryokuDialog.dataSelected)) {
                    seihinJohoNyuryokuDialog.dataSelected(row.findP("nm_seihin").text());
                }
                else {
                    seihinJohoNyuryokuDialog.dataSelected("");
                }
            }
            else {
                seihinJohoNyuryokuDialog.dataSelected("");
            }

        };

        /**
         * Event close dialog
         */
        seihinJohoNyuryokuDialog.closeDialog = function (e) {

            var element = seihinJohoNyuryokuDialog.element,
            data = {}, flg_error = false;

            seihinJohoNyuryokuDialog.notifyAlert.clear();

            seihinJohoNyuryokuDialog.validateList().then(function () {
                for (var i = 0 ; i < seihinJohoNyuryokuDialog.element.find(".new").length; i++) {
                    var row = $(seihinJohoNyuryokuDialog.element.find(".new")[i]);
                    if (row.findP("cd_hin").val().toString() != "") {
                        var cd_hin = parseInt(row.findP("cd_hin").val(), 10);
                        if (App.isUndefOrNull(data[cd_hin])) {
                            data[cd_hin] = true;
                        }
                        else {
                            seihinJohoNyuryokuDialog.notifyAlert.message(App.str.format(App.messages.app.AP0047, "", row.findP("cd_hin").val()), row.findP("cd_hin")).show();
                            flg_error = true;
                        }
                    }
                }

                if (flg_error) {
                    return;
                }
                element.modal("hide");
            })
        }
        
        /**
         * Event when show dialog
         */
        seihinJohoNyuryokuDialog.shown = function () {
            
            var element = seihinJohoNyuryokuDialog.element;

            element.find(".new-edit").addClass("new").removeClass("new-edit");
            var length = element.find(".new").length;

            if (length == 0) {
                var newData = {
                    no_yusen: 1
                };
                seihinJohoNyuryokuDialog.dataTable.dataTable("addRow", function (tbody) {
                    tbody.form(seihinJohoNyuryokuDialog.options.bindOption).bind(newData);
                    return tbody;
                }, true);
            }
            else {
                for (var i = 0 ; i < length; i++) {
                    var row = $(element.find(".new")[i]);
                    row.findP("no_yusen").text(i + 1);
                }
            }

            if (seihinJohoNyuryokuDialog.options.parameter.modeReadOnly) {
                element.find("button").not(".cancel-button").not(".close-seihinJohoNyuryokuDialog").prop("disabled", true);
                element.find("input").prop("disabled", true);
            }
            if (seihinJohoNyuryokuDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kojyo) {
                seihinJohoNyuryokuDialog.options.validations.cd_hin.rules.maxlength = seihinJohoNyuryokuDialog.options.parameter.su_code_standard;
            }
        };

        /**
        * Load data
        */
        seihinJohoNyuryokuDialog.loadData = function () {
            var deferred = $.Deferred(),
                element = seihinJohoNyuryokuDialog.element;

            if (seihinJohoNyuryokuDialog.options.parameter.flg_shoki) {

                return seihinJohoNyuryokuDialog.searchData().then(function () {

                }).then(function () {
                    seihinJohoNyuryokuDialog.options.parameter.flg_shoki = 0;
                    deferred.resolve();
                }).fail(function (error) {
                    deferred.reject(error);
                }).always(function () {
                    if (seihinJohoNyuryokuDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
                        if (seihinJohoNyuryokuDialog.options.parameter.flg_edit) {
                            element.find("button").prop("disabled", false);
                            element.find("input").prop("disabled", false);
                        }
                        else {
                            element.find("button").not(".close-seihinJohoNyuryokuDialog").prop("disabled", true);
                            element.find("input").prop("disabled", true);
                        }
                    }
                    else {
                        if (seihinJohoNyuryokuDialog.options.parameter.M_kirikae == App.settings.app.m_kirikae.hyoji) {

                            if (!seihinJohoNyuryokuDialog.options.parameter.flg_seiho_base) {
                                element.find("button").prop("disabled", false);
                                element.find("input").prop("disabled", false);
                            }
                            else {
                                element.find("button").not(".close-seihinJohoNyuryokuDialog").prop("disabled", true);
                                element.find("input").prop("disabled", true);
                            }
                        }
                        else {
                            element.find("button").prop("disabled", false);
                            element.find("input").prop("disabled", false);
                        }
                    }
                })
                
            }
            else {
                if (seihinJohoNyuryokuDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
                    if (seihinJohoNyuryokuDialog.options.parameter.flg_edit) {
                        element.find("button").prop("disabled", false);
                        element.find("input").prop("disabled", false);
                    }
                    else {
                        element.find("button").not(".close-seihinJohoNyuryokuDialog").prop("disabled", true);
                        element.find("input").prop("disabled", true);
                    }
                }
                else {
                    if (seihinJohoNyuryokuDialog.options.parameter.M_kirikae == App.settings.app.m_kirikae.hyoji) {

                        if (!seihinJohoNyuryokuDialog.options.parameter.flg_seiho_base) {
                            element.find("button").prop("disabled", false);
                            element.find("input").prop("disabled", false);
                        }
                        else {
                            element.find("button").not(".close-seihinJohoNyuryokuDialog").prop("disabled", true);
                            element.find("input").prop("disabled", true);
                        }
                    }
                    else {
                        element.find("button").prop("disabled", false);
                        element.find("input").prop("disabled", false);
                    }
                }
                deferred.resolve();
            }

            return deferred.promise();
        }
        

        /**
         * Search data in case  have no_haigo
         */
        seihinJohoNyuryokuDialog.searchDataInCaseHaveNoHaigo = function () {
            var element = seihinJohoNyuryokuDialog.element,
                modeKojyo = true,
                deferred = $.Deferred();
            if (seihinJohoNyuryokuDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
                modeKojyo = false;
            }

            var parameter = {
                cd_kaisha: seihinJohoNyuryokuDialog.options.parameter.cd_kaisha,
                cd_kojyo: seihinJohoNyuryokuDialog.options.parameter.cd_kojyo,
                cd_haigo: seihinJohoNyuryokuDialog.options.parameter.no_haigo,
                M_kirikae: seihinJohoNyuryokuDialog.options.parameter.M_kirikae,
                modeKojyo: modeKojyo
            }

            return $.ajax(App.ajax.webapi.get(seihinJohoNyuryokuDialog.urls.seihinJohoNyuryoku_Dialog + "/getData", parameter)
            ).then(function (result) {

                if (seihinJohoNyuryokuDialog.options.parameter.no_gamen != App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
                    result = result.data;
                }

                if (result.length) {
                    for (var i = 0 ; i < result.length; i++) {
                        var dataDetail = {
                            no_yusen: result[i].no_yusen,
                            cd_hin: App.common.fillString(result[i].cd_hin, 6),
                            nm_seihin: result[i].nm_seihin,
                            nisugata_hyoji:result[i].nisugata_hyoji 
                        };
                        seihinJohoNyuryokuDialog.dataTable.dataTable("addRow", function (tbody) {
                            tbody.form(seihinJohoNyuryokuDialog.options.bindOption).bind(dataDetail);
                            return tbody;
                        }, true);
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
         * Search data
         */
        seihinJohoNyuryokuDialog.searchData = function () {
            var element = seihinJohoNyuryokuDialog.element,
                deferred = $.Deferred();

            if (!seihinJohoNyuryokuDialog.options.parameter.no_haigo) {
                deferred.resolve();
                return deferred.promise();
            }
            else {
                seihinJohoNyuryokuDialog.searchDataInCaseHaveNoHaigo().then(function () {
                    deferred.resolve();
                }).fail(function (error) {
                    deferred.reject(error);
                })
                return deferred.promise();
            }
        }

        /**
         * 検索ダイアログ　バリデーションルールを定義します。
         */
        seihinJohoNyuryokuDialog.options.validations = {
            cd_hin: {
                rules: {
                    //required: true,
                    digits: true,
                    maxlength: 6,
                    checkExistsHimmei: function (value, opts, state, done) {
                        if (state && state.check) {
                            var tbody = state.tbody.element ? state.tbody.element : state.tbody
                            if(tbody.findP("cd_hin").val().toString() == ""){
                                return done(true);
                            }
                            return done(tbody.findP("nm_seihin").text() != "");
                        }
                        return done(true);
                    },
                },
                options: {
                    name: "コード"
                },
                messages: {
                    //required: App.messages.base.required,
                    digits: App.messages.base.digits,
                    maxlength: App.messages.base.maxlength,
                    checkExistsHimmei: App.messages.app.AP0010,
                }
            },
        };

        /**
         * 画面明細の一覧の入力項目の変更イベントの処理を行います。
         */
        seihinJohoNyuryokuDialog.change = function (e, row) {

            var target = $(e.target),
            loadingTaget = seihinJohoNyuryokuDialog.element.find(".modal-content");

            $("#save").prop("disabled", false);

            seihinJohoNyuryokuDialog.validator.validate({
                targets: target,
                state: {
                    tbody: row.element,
                    isGridValidation: true
                }
            }).then(function () {

                if(target.val().toString() == ""){
                    row.element.findP("nm_seihin").text("");
                    row.element.findP("nisugata_hyoji").text("");
                }
                else{
                    var valOld = parseInt(target.val(), 10),
                    valNew,
                    parameter =
                    {
                        cd_kaisha: seihinJohoNyuryokuDialog.options.parameter.cd_kaisha,
                        cd_kojyo: seihinJohoNyuryokuDialog.options.parameter.cd_kojyo,
                        cd_hin: valOld
                    };

                    if (seihinJohoNyuryokuDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
                        //valNew = App.num.format(Number(valOld), "000000");
                        valNew = App.common.fillString(valOld, 6);
                        parameter.modeKojyo = false;
                        parameter.M_kirikae = null;
                    }
                    else {
                        var pad = "0000000000000";
                        valNew = App.num.format(Number(valOld),pad.substr(0, seihinJohoNyuryokuDialog.options.parameter.su_code_standard))
                        parameter.modeKojyo = true;
                        parameter.M_kirikae = seihinJohoNyuryokuDialog.options.parameter.M_kirikae;
                    }

                    App.ui.loading.show("", loadingTaget);

                    $.ajax(App.ajax.webapi.get(seihinJohoNyuryokuDialog.urls.seihinJohoNyuryoku_Dialog + "/getHinName", parameter)
                    ).then(function (result) {
                        if (result) {
                        
                            if (result.cd_haigo && (App.isUndefOrNull(seihinJohoNyuryokuDialog.options.parameter.no_haigo) || Number(result.cd_haigo) != Number(seihinJohoNyuryokuDialog.options.parameter.no_haigo))) {

                                var div = $("#ConfirmDialog").find(".item-label").closest("div")

                                div.addClass("height-up");

                                seihinJohoNyuryokuDialog.confirmDialog.confirm({text: App.messages.app.AP0023})
                                .then(function () {
                                    target.val(valNew);
                                    row.element.findP("nm_seihin").text(result.nm_seihin);
                                    row.element.findP("nisugata_hyoji").text(result.nisugata_hyoji == null ? "" : result.nisugata_hyoji);

                                    div.removeClass("height-up");

                                }).fail(function () {
                                    target.val("");
                                    row.element.findP("nm_seihin").text("");
                                    row.element.findP("nisugata_hyoji").text("");

                                    div.removeClass("height-up")

                                    seihinJohoNyuryokuDialog.validator.validate({
                                        targets: target,
                                        state: {
                                            tbody: row.element,
                                            isGridValidation: true,
                                            check: true
                                        }
                                    })
                                })
                            }
                            else {
                                target.val(valNew);
                                row.element.findP("nm_seihin").text(result.nm_seihin);
                                row.element.findP("nisugata_hyoji").text(result.nisugata_hyoji == null ? "" : result.nisugata_hyoji);
                            }

                        }
                        else {
                            row.element.findP("nm_seihin").text("");
                            row.element.findP("nisugata_hyoji").text("");

                            seihinJohoNyuryokuDialog.validator.validate({
                                targets: target,
                                state: {
                                    tbody: row.element,
                                    isGridValidation: true,
                                    check: true
                                }
                            })
                        }
                    }).fail(function (error) {
                        row.element.findP("nm_seihin").text("");
                        row.element.findP("nisugata_hyoji").text("");
                        seihinJohoNyuryokuDialog.validator.validate({
                            targets: target,
                            state: {
                                tbody: row.element,
                                isGridValidation: true,
                                check: true
                            }
                        })
                    }).always(function () {
                        App.ui.loading.close(loadingTaget);
                    })
                }

                
            }).fail(function () {
                row.element.findP("nm_seihin").text("");
                row.element.findP("nisugata_hyoji").text("");
            })
        };

        /**
         * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
         */
        seihinJohoNyuryokuDialog.select = function (e) {
            var element = seihinJohoNyuryokuDialog.element,
                button = $(e.target),
                tbody = button.closest("tbody"),
                id = tbody.attr("data-key"),
                data;

            if (App.isUndef(id)) {
                return;
            }

            data = seihinJohoNyuryokuDialog.data.entry(id);

            if (App.isFunc(seihinJohoNyuryokuDialog.dataSelected)) {
                if (!seihinJohoNyuryokuDialog.dataSelected(data)) {
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
        seihinJohoNyuryokuDialog.loadDialogs = function () {

            //TODO: 共有ダイアログデータを取得し、画面にバインドする処理を記述します。
            return App.async.all({
                seihinKensakuDialog: $.get(seihinJohoNyuryokuDialog.urls.seihinKensakuDialog)
            }).then(function (result) {

                $("#dialog-container").append(result.successes.seihinKensakuDialog);
                seihinJohoNyuryokuDialog.seihinKensakuDialog = SeihinKensakuDialog;
                seihinJohoNyuryokuDialog.seihinKensakuDialog.param.no_gamen = seihinJohoNyuryokuDialog.options.parameter.no_gamen;
                seihinJohoNyuryokuDialog.seihinKensakuDialog.param.cd_kaisha = seihinJohoNyuryokuDialog.options.parameter.cd_kaisha;
                seihinJohoNyuryokuDialog.seihinKensakuDialog.param.cd_kojyo = seihinJohoNyuryokuDialog.options.parameter.cd_kojyo;
                if (seihinJohoNyuryokuDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
                    seihinJohoNyuryokuDialog.seihinKensakuDialog.param.m_kirikae = null;
                }
                else {
                    seihinJohoNyuryokuDialog.seihinKensakuDialog.param.m_kirikae = seihinJohoNyuryokuDialog.options.parameter.M_kirikae;
                }
                seihinJohoNyuryokuDialog.seihinKensakuDialog.initialize();

                seihinJohoNyuryokuDialog.confirmDialog = ConfirmDialog;
            });
        }

        /**
         * 画面明細の各行にデータを設定する際のオプションを定義します。
         */
        seihinJohoNyuryokuDialog.options.bindOption = {
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
         * 画面明細の一覧の行が選択された時の処理を行います。
         */
        seihinJohoNyuryokuDialog.select = function (e, row) {
            //TODO: 単一行を作成する場合は、下記２行を利用します。
            $($(row.element[seihinJohoNyuryokuDialog.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab.selected")).removeClass("selected").addClass("unselected");
            $(row.element[seihinJohoNyuryokuDialog.fixedColumnIndex].querySelectorAll(".select-tab")).removeClass("unselected").addClass("selected");
            //TODO: 多段行を作成する場合は、下記２行を有効にし、上記の２行は削除します。
            //$($(row.element[seihinJohoNyuryokuDialog.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab-2lines.selected")).removeClass("selected").addClass("unselected");
            //$(row.element[seihinJohoNyuryokuDialog.fixedColumnIndex].querySelectorAll(".select-tab-2lines")).removeClass("unselected").addClass("selected");

            //TODO: 選択行全体に背景色を付ける場合は以下のコメントアウトを解除します。
            //if (!App.isUndefOrNull(seihinJohoNyuryokuDialog.selectedRow)) {
            //    seihinJohoNyuryokuDialog.selectedRow.element.find(".selected-row").removeClass("selected-row");
            //}
            //row.element.find("tr").addClass("selected-row");
            //seihinJohoNyuryokuDialog.selectedRow = row;
        };

        /**
        * 画面明細の一覧に新規データを追加します。
        */
        seihinJohoNyuryokuDialog.addNewItem = function () {
            //TODO:新規データおよび初期値を設定する処理を記述します。
            var maxValue = seihinJohoNyuryokuDialog.dataTable.find("tbody").not(".item-tmpl").length + 1
            var newData = {
                no_yusen: maxValue
            };
            
            $("#save").prop("disabled", false);

            seihinJohoNyuryokuDialog.dataTable.dataTable("addRow", function (tbody) {
                tbody.form(seihinJohoNyuryokuDialog.options.bindOption).bind(newData);
                return tbody;
            }, true);
        };

        /**
         * 画面明細の一覧で選択されている行とデータを削除します。
         */
        seihinJohoNyuryokuDialog.deleteItem = function (e) {
            var element = seihinJohoNyuryokuDialog.element,
                table = element.find(".datatable"),
                //TODO: 単一行を作成する場合は、下記を利用します。
                selected = element.find(".datatable .select-tab.selected").closest("tbody");
            //TODO: 多段行を作成する場合は、以下を利用し、上記１行は削除します。

            $("#save").prop("disabled", false);

            seihinJohoNyuryokuDialog.notifyAlert.remove("seihinJohoNyuryokuDialog-AP0008");

            if (!selected.length) {
                seihinJohoNyuryokuDialog.notifyAlert.message(App.messages.app.AP0008, "seihinJohoNyuryokuDialog-AP0008").show();
                return;
            }

            seihinJohoNyuryokuDialog.dataTable.dataTable("deleteRow", selected, function (row) {
                var id = row.attr("data-key"),
                    newSelected;

                row.find(":input").each(function (i, elem) {
                    seihinJohoNyuryokuDialog.notifyAlert.remove(elem);
                });

                if (!App.isUndefOrNull(id)) {
                    var entity = seihinJohoNyuryokuDialog.data.entry(id);
                    seihinJohoNyuryokuDialog.data.remove(entity);
                }

                newSelected = row.next().not(".item-tmpl");
                if (!newSelected.length) {
                    newSelected = row.prev().not(".item-tmpl");
                }
                if (newSelected.length) {
                    for (var i = seihinJohoNyuryokuDialog.fixedColumnIndex; i > -1; i--) {
                        if ($(newSelected[i]).find(":focusable:first").length) {
                            $(newSelected[i]).find(":focusable:first").focus();
                            break;
                        }
                    }
                }

                // Set priority of next row when delete seleted row
                seihinJohoNyuryokuDialog.updateRowIndex(row);
            });

            if(seihinJohoNyuryokuDialog.element.find(".new").length == 0){
                var newData = {
                    no_yusen: 1
                };
                seihinJohoNyuryokuDialog.dataTable.dataTable("addRow", function (tbody) {
                    tbody.form(seihinJohoNyuryokuDialog.options.bindOption).bind(newData);
                    return tbody;
                }, true);
            }
        };

        /**
         * Move selected row UP or DOWN in table
         */
        seihinJohoNyuryokuDialog.moveUpDown = function (e) {
            var element = seihinJohoNyuryokuDialog.element,
                selected = element.find(".datatable .select-tab.selected").closest("tbody"),
                $reindex_start,
                no_seq = 0;

            if (!selected.length) {
                return;
            }

            $("#save").prop("disabled", false);

            if ($(this).is('#up-seihinJohoNyuryokuDialog')) {
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
        seihinJohoNyuryokuDialog.updateRowIndex = function (row) {
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
        seihinJohoNyuryokuDialog.showSeihinKensakuDialog = function (e) {
            var element = seihinJohoNyuryokuDialog.element,
                tbody = $(e.target).closest("tbody"),
                row;

            seihinJohoNyuryokuDialog.seihinKensakuDialog.param.no_gamen = seihinJohoNyuryokuDialog.options.parameter.no_gamen;
            seihinJohoNyuryokuDialog.seihinKensakuDialog.param.cd_kaisha = seihinJohoNyuryokuDialog.options.parameter.cd_kaisha;
            seihinJohoNyuryokuDialog.seihinKensakuDialog.param.cd_kojyo = seihinJohoNyuryokuDialog.options.parameter.cd_kojyo;
            if (seihinJohoNyuryokuDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
                seihinJohoNyuryokuDialog.seihinKensakuDialog.param.m_kirikae = null;
            }
            else {
                seihinJohoNyuryokuDialog.seihinKensakuDialog.param.m_kirikae = seihinJohoNyuryokuDialog.options.parameter.M_kirikae;
            }

            seihinJohoNyuryokuDialog.seihinKensakuDialog.element.modal("show");

            //検索ダイアログでデータ選択が実行された時に呼び出される関数を設定しています。
            seihinJohoNyuryokuDialog.seihinKensakuDialog.dataSelected = function (data) {
                $("#save").prop("disabled", false);
                var valOld = data.cd_seihin,
                    valNew, loadingTaget = seihinJohoNyuryokuDialog.element.find(".modal-content");
                    parameter =
                    {
                        cd_kaisha: seihinJohoNyuryokuDialog.options.parameter.cd_kaisha,
                        cd_kojyo: seihinJohoNyuryokuDialog.options.parameter.cd_kojyo,
                        cd_hin: valOld
                    };

                if (seihinJohoNyuryokuDialog.options.parameter.no_gamen == App.settings.app.no_gamen.haigo_toroku_kaihatsu) {
                    //valNew = App.num.format(Number(valOld), "000000");
                    valNew = App.common.fillString(valOld, 6);
                    parameter.modeKojyo = false;
                    parameter.M_kirikae = null;
                }
                else {
                    //var pad = "0000000000000";
                    valNew = App.common.fillString(valOld, seihinJohoNyuryokuDialog.options.parameter.su_code_standard);
                    parameter.modeKojyo = true;
                    parameter.M_kirikae = seihinJohoNyuryokuDialog.options.parameter.M_kirikae;
                }

                App.ui.loading.show("", loadingTaget);

                $.ajax(App.ajax.webapi.get(seihinJohoNyuryokuDialog.urls.seihinJohoNyuryoku_Dialog + "/getHinName", parameter)
                ).then(function (result) {
                    if (result) {

                        if (result.cd_haigo) {

                            var div = $("#ConfirmDialog").find(".item-label").closest("div")

                            div.addClass("height-up");

                            seihinJohoNyuryokuDialog.confirmDialog.confirm({ text: App.messages.app.AP0023 })
                            .then(function () {
                                tbody.findP("cd_hin").val(valNew);
                                tbody.findP("nm_seihin").text(result.nm_seihin);
                                tbody.findP("nisugata_hyoji").text(result.nisugata_hyoji == null ? "" : result.nisugata_hyoji);

                                seihinJohoNyuryokuDialog.validator.validate({
                                    targets: tbody.findP("cd_hin"),
                                    state: {
                                        tbody: tbody,
                                        isGridValidation: true,
                                        check: true
                                    }
                                })

                                div.removeClass("height-up");

                            }).fail(function () {
                                tbody.findP("cd_hin").val("");
                                tbody.findP("nm_seihin").text("");
                                tbody.findP("nisugata_hyoji").text("");

                                div.removeClass("height-up")

                                seihinJohoNyuryokuDialog.validator.validate({
                                    targets: tbody.findP("cd_hin"),
                                    state: {
                                        tbody: tbody,
                                        isGridValidation: true,
                                        check: true
                                    }
                                })
                            })
                        }
                        else {
                            tbody.findP("cd_hin").val(valNew);
                            tbody.findP("nm_seihin").text(result.nm_seihin);
                            tbody.findP("nisugata_hyoji").text(result.nisugata_hyoji == null ? "" : result.nisugata_hyoji);

                            seihinJohoNyuryokuDialog.validator.validate({
                                targets: tbody.findP("cd_hin"),
                                state: {
                                    tbody: tbody,
                                    isGridValidation: true,
                                    check: true
                                }
                            })
                        }

                    }
                    else {
                        tbody.findP("nm_seihin").text("");
                        tbody.findP("nisugata_hyoji").text("");

                        seihinJohoNyuryokuDialog.validator.validate({
                            targets: tbody.findP("cd_hin"),
                            state: {
                                tbody: tbody,
                                isGridValidation: true,
                                check: true
                            }
                        })
                    }
                }).fail(function (error) {
                    tbody.findP("nm_seihin").text("");
                    tbody.findP("nisugata_hyoji").text("");
                    seihinJohoNyuryokuDialog.validator.validate({
                        targets: tbody.findP("cd_hin"),
                        state: {
                            tbody: tbody,
                            isGridValidation: true,
                            check: true
                        }
                    })
                }).always(function () {
                    App.ui.loading.close(loadingTaget);
                })

                delete seihinJohoNyuryokuDialog.seihinKensakuDialog.dataSelected;
            }
        };

        /**
         * 画面明細のバリデーションを実行します。
         */
        seihinJohoNyuryokuDialog.executeValidation = function (targets, row, options) {
            var defaultOptions = {
                targets: targets,
                state: {
                    tbody: row,
                    isGridValidation: true,
                    check:true
                }
            },
            execOptions = $.extend(true, {}, defaultOptions, options);

            return seihinJohoNyuryokuDialog.validator.validate(execOptions);
        };

        /**
         * 画面明細のバリデーションフィルターを設定します。（必須チェックを行わない）
         */
        seihinJohoNyuryokuDialog.validationFilter = function (item, method, state, options) {
            return method !== "required";
        };

        /**
         * 画面明細の一覧のバリデーションを実行します。
         */
        seihinJohoNyuryokuDialog.validateList = function (suppressMessage) {
            // Skip all validate with view mode
            if (seihinJohoNyuryokuDialog.options.parameter.modeReadOnly) {
                return App.async.success();
            }

            var validations = [],
                options = {
                    state: {
                        suppressMessage: suppressMessage,
                        check:true
                    }
                };

            seihinJohoNyuryokuDialog.dataTable.dataTable("each", function (row) {
                if (row.element.hasClass("item-tmpl")) {
                    return;
                }

                validations.push(seihinJohoNyuryokuDialog.executeValidation(row.element.find(":input"), row.element, options));
            });

            return App.async.all(validations);
        };
        
        //TODO: 複数セレクトを使用する場合は、上の単一セレクト用select関数を削除し、下の複数セレクト用の関数をコメント解除します
        //TODO: 単一セレクトの場合は、不要なコメント部分は削除してください

    </script>

    <div class="modal fade wide" tabindex="-1" id="seihinJohoNyuryokuDialog" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog" style="height: 350px; width: 60%">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close close-seihinJohoNyuryokuDialog">&times;</button>
                <h4 class="modal-title">製品情報入力</h4>
            </div>

            <div class="modal-body">
                <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
                <div class="row">
                </div>
                <div class="row caution">
                    <label class="caution">表示用配合として使用する場合は必ず入力して下さい。</label>
                </div>
                <div class="row caution">
                    <label class="caution">同一配合の場合、表示用配合として製品コード追加登録が可能です。</label>
                </div>
                <div class="row caution">
                    <label class="caution">この製法番号の製品コードと一致しているとは限りませんのでご注意ください。</label>
                </div>
                <div class="detail">
                    <!--TODO: 明細をpartにする場合は以下を利用します。
                    <div title="TODO: 明細一覧部のタイトルを設定します。"  class="part">
                    -->
                        <div class="control-label toolbar">
                            <i class="icon-th"></i>
                            <div class="btn-group">
                                <button type="button" class="btn btn-default btn-xs" id="add-item-seihinJohoNyuryokuDialog">行追加</button>
                                <button type="button" class="btn btn-default btn-xs" id="up-seihinJohoNyuryokuDialog">行移動▲</button>
                                <button type="button" class="btn btn-default btn-xs" id="down-seihinJohoNyuryokuDialog">行移動▼</button>
                                <button type="button" class="btn btn-default btn-xs" id="del-item-seihinJohoNyuryokuDialog">行削除</button>
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
                                    <th style="">製品名</th>
                                    <th style="width: 250px;">荷姿</th>
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
                                        <label data-prop="no_yusen"></label>
                                    </td>
                                    <td>
                                        <input type="tel" data-prop="cd_hin" class="limit-input-int" maxlength="6" style="width: 70%; white-space: nowrap; ime-mode:disabled;" />
                                        <input type="text" data-prop="cd_hin_text" style="display:none"/>
                                        <input type="text" data-prop="cd_haigo" style="display:none"/>
                                        <button type="button" class="btn btn-xs btn-info cd_hin-seihinJohoNyuryokuDialog" style="min-width: 25px; margin-right: 5px;">検索</button>
                                    </td>
                                    <td>
                                        <label class="overflow-ellipsis" data-prop="nm_seihin"></label>
                                    </td>
                                    <td>
                                        <label class="overflow-ellipsis" data-prop="nisugata_hyoji"></label>
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
                <button type="button" class="cancel-button btn btn-sm btn-default close-seihinJohoNyuryokuDialog">閉じる</button>
            </div>

        </div>
    </div>
    </div>