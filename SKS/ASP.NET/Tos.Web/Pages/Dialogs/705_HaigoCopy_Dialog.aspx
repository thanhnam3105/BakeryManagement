<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="705_HaigoCopy_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs.HaigoCopy_Dialog" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchInputDialog(Ver2.1)】 Template--%>
<style type="text/css">
    #_705_HaigoCopy_Dialog.float-left {
        float: left!important;
    }

    #ui-datepicker-div {
        z-index: 1051!important;
    }

    div#_705_HaigoCopy_Dialog.modal {
        z-index: 1049;
    }

    .color-red {
        color: red!important;
    }

</style>
<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var _705_HaigoCopy_Dialog = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.maxInputDataCount,   // TODO:取得するデータ数を指定します。
            filter: ""
        },
        values: {
            isChangeRunning: {},
            M_kirikae: null,
            cd_kaisha: null,
            cd_kojyo: null,
            cd_haigo: null,
            no_han: null,
            su_code_standard: null,
            kbn_hin_search: 2,
            kbn_hin: null,
            modeShow: 0,
            qty_kihon_old: "",
            kbn_nmacs_relate: 1,
            kbn_nmacs_general: 2
        },
        urls: {
            getHanList: "../api/HaigoCopy_Dialog/GetNoHanList",
            search: "../api/HaigoCopy_Dialog",
            haigoTo: "../api/HaigoCopy_Dialog/GetHaigoTo",
            vw_kaisha_kojyo: "../Services/ShisaQuickService.svc/vw_kaisha_kojyo?$filter=cd_kaisha eq {0}{1}&$orderby=cd_kaisha,cd_kojyo",
            HinmeiKaihatsuDialog: "../Pages/Dialogs/710_HinmeiKaihatsu_Dialog.aspx",
            save: "../api/HaigoCopy_Dialog",

        },
        header: {
            options: {},
            values: {},
        },
        haigoto: {
            options: {},
            values: {}
        },
        commands: {},
        dialogs: {}
    };

    /**
     * バリデーション成功時の処理を実行します。
     */
    _705_HaigoCopy_Dialog.validationSuccess = function (results, state) {
        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {

            } else {
                _705_HaigoCopy_Dialog.setColValidStyle(item.element);
            }

            _705_HaigoCopy_Dialog.notifyAlert.remove(item.element);
        }
        _705_HaigoCopy_Dialog.setMargin();
    };

    /**
     * バリデーション失敗時の処理を実行します。
     */
    _705_HaigoCopy_Dialog.validationFail = function (results, state) {

        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {


            } else {
                _705_HaigoCopy_Dialog.setColInvalidStyle(item.element);
            }

            if (state && state.suppressMessage) {
                continue;
            }
            _705_HaigoCopy_Dialog.notifyAlert.message(item.message, item.element).show();
        }
        _705_HaigoCopy_Dialog.setMargin();
    };

    /**
     * Set margin-bottom error。
     */
    _705_HaigoCopy_Dialog.setMargin = function () {

        var errorQuantity = _705_HaigoCopy_Dialog.notifyAlert.count();
        var heightMargin = 32;
        if (errorQuantity > 0) {
            heightMargin = heightMargin + errorQuantity * 26;
        } else {
            heightMargin = 0;
        }

        $(".haigo-to").css("margin-bottom", heightMargin);
    }


    /**
     * バリデーション後の処理を実行します。
     */
    _705_HaigoCopy_Dialog.validationAlways = function (results) {
        //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
    };

    /**
      * 指定された定義をもとにバリデータを作成します。
      * @param target バリデーション定義
      * @param options オプションに設定する値。指定されていない場合は、
      *                画面の success/fail/always のハンドル処理が指定されたオプションが設定されます。
      */
    _705_HaigoCopy_Dialog.createValidator = function (target, options) {
        return App.validation(target, options || {
            success: _705_HaigoCopy_Dialog.validationSuccess,
            fail: _705_HaigoCopy_Dialog.validationFail,
            always: _705_HaigoCopy_Dialog.validationAlways
        });
    };

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    _705_HaigoCopy_Dialog.setColInvalidStyle = function (target) {
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
    _705_HaigoCopy_Dialog.setColValidStyle = function (target) {
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
     * データの保存処理を実行します。
     */
    _705_HaigoCopy_Dialog.commands.save = function () {
        var loadingTaget = _705_HaigoCopy_Dialog.element.find(".modal-content");

        _705_HaigoCopy_Dialog.notifyAlert.clear();
        _705_HaigoCopy_Dialog.notifyInfo.clear();

        App.ui.loading.show("", loadingTaget);

        var sleep = 0;
        var condition = "Object.keys(_705_HaigoCopy_Dialog.values.isChangeRunning).length == 0";
        App.ui.wait(sleep, condition, 100)
        .then(function () {
            _705_HaigoCopy_Dialog.haigoto.validator.validate().then(function () {
                var errorCount = 0;
                if(_705_HaigoCopy_Dialog.values.kbn_hin == 4 && _705_HaigoCopy_Dialog.header.element.findP("seihin_chk").prop("checked")) {
                    _705_HaigoCopy_Dialog.notifyAlert.message(App.messages.app.AP0017).show();
                    errorCount++;
                }

                if(_705_HaigoCopy_Dialog.values.M_kirikae == App.settings.app.m_kirikae.foodprocs
                    && _705_HaigoCopy_Dialog.haigoto.element.findP("kbn_seizo").prop("checked")
                    && parseInt(_705_HaigoCopy_Dialog.values.cd_kojyo) == _705_HaigoCopy_Dialog.haigoto.element.findP("nm_kojyo").val()){
                    _705_HaigoCopy_Dialog.notifyAlert.message(App.messages.app.AP0070).show();
                    errorCount++;
                }
                var idFrom = $(".haigo-from").attr("data-key"),
                entityFrom = _705_HaigoCopy_Dialog.header.data.entry(idFrom);

                var idTo = $(".haigo-to").attr("data-key"),
                entityTo = _705_HaigoCopy_Dialog.haigoto.data.entry(idTo);
                if (entityFrom.kbn_vw != entityTo.kbn_vw && !App.isUndefOrNullOrStrEmpty(entityTo.kbn_vw)) {
                    _705_HaigoCopy_Dialog.notifyAlert.message(App.messages.app.AP0112).show();
                    errorCount++;
                }

                var zenbanChecked = _705_HaigoCopy_Dialog.header.element.findP("zenban_chk").prop("checked");
                if (zenbanChecked && (entityTo.modeBind == 1 || entityTo.modeBind == 2)) {
                    _705_HaigoCopy_Dialog.notifyAlert.message(App.messages.app.AP0069, "AP0069").show();
                    errorCount++;
                }

                if (errorCount > 0) {
                    _705_HaigoCopy_Dialog.setMargin();
                    return;
                }

                var options = {
                    text: App.messages.app.AP0004
                };
                 _705_HaigoCopy_Dialog.dialogs.confirmDialog.confirm(options)
                .then(function () {
                    var parameter = {
                        HaigoFrom: _705_HaigoCopy_Dialog.filterFromSave(),
                        HaigoTo: _705_HaigoCopy_Dialog.filterToSave()
                    };

                    //TODO: データの保存処理をここに記述します。
                    return $.ajax(App.ajax.webapi.post(_705_HaigoCopy_Dialog.urls.save, parameter))
                        .then(function (result) {

                            //TODO: データの保存成功時の処理をここに記述します。


                            //最後に再度データを取得しなおします。
                            return App.async.all([_705_HaigoCopy_Dialog.afterSave()]);
                        }).then(function () {
                            _705_HaigoCopy_Dialog.notifyInfo.message(App.messages.base.MS0002).show();
                        }).fail(function (error) {

                            if (error.status === App.settings.base.conflictStatus) {
                                // TODO: 同時実行エラー時の処理を行っています。
                                // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                                _705_HaigoCopy_Dialog.header.search(false);
                                _705_HaigoCopy_Dialog.notifyAlert.clear();
                                _705_HaigoCopy_Dialog.notifyAlert.message(App.messages.base.MS0009).show();
                                return;
                            }

                            //TODO: データの保存失敗時の処理をここに記述します。
                            if (error.status === App.settings.base.validationErrorStatus) {
                                var errors = error.responseJSON;
                                if (errors == "AP0009") {
                                    _705_HaigoCopy_Dialog.notifyAlert.message(App.str.format(App.messages.app.AP0009, { calculItem: "レシピ基本重量", maxChar: "", inputItems: "倍率" })).show();
                                }
                                else if (errors == "AP0113") {
                                    _705_HaigoCopy_Dialog.notifyAlert.message(App.messages.app.AP0113).show();
                                }
                                else if (errors == "AP0115") {
                                    _705_HaigoCopy_Dialog.notifyAlert.message(App.messages.app.AP0115).show();
                                }
                                else if (errors == "MS0017") {
                                    _705_HaigoCopy_Dialog.notifyAlert.message(App.messages.base.MS0017).show();
                                }
                                else {
                                    $.each(errors, function (index, err) {
                                        _705_HaigoCopy_Dialog.notifyAlert.message(
                                            err.Message +
                                            (App.isUndefOrNull(err.InvalidationName) ? "" : err.Data[err.InvalidationName])
                                        ).show();
                                    });
                                }
                                return;
                            }

                            _705_HaigoCopy_Dialog.notifyAlert.message(App.ajax.handleError(error).message).show();

                        });
                });
            });
        }).fail(function () {
            _705_HaigoCopy_Dialog.notifyAlert.message(App.messages.base.MS0006).show();
        }).always(function () {
            setTimeout(function () {
                _705_HaigoCopy_Dialog.header.element.find(":input:first").focus();
            }, 100);
            _705_HaigoCopy_Dialog.setMargin();
            App.ui.loading.close(loadingTaget);
        });
    };

    /**
     * Disabled information after save。
     */
    _705_HaigoCopy_Dialog.afterSave = function () {
        var element = _705_HaigoCopy_Dialog.header.element,
            elementTo = _705_HaigoCopy_Dialog.haigoto.element;

        element.find(":input").prop("disabled", true);
        elementTo.find(":input").prop("disabled", true);

        _705_HaigoCopy_Dialog.element.find(".save").prop("disabled", true);
    }


    /**
     * Clear information enter input。
     */
    _705_HaigoCopy_Dialog.commands.clearScreen = function () {
        var element = _705_HaigoCopy_Dialog.element;

        element.find(":input").val("");
        element.find(":input").prop("disabled", false);
        _705_HaigoCopy_Dialog.element.find(".save").prop("disabled", false);
        _705_HaigoCopy_Dialog.header.element.findP("zenban_chk").prop("checked", false);
        _705_HaigoCopy_Dialog.header.element.findP("seihin_chk").prop("checked", false);

        _705_HaigoCopy_Dialog.shown();

        var options = {
            filter: _705_HaigoCopy_Dialog.haigoto.validationFilter
        };
        _705_HaigoCopy_Dialog.haigoto.validator.validate(options);
    }

    /**
     * すべてのバリデーションを実行します。
     */
    _705_HaigoCopy_Dialog.validateAll = function () {

        var validations = [];

        validations.push(_705_HaigoCopy_Dialog.detail.validateList());

        //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。

        return App.async.all(validations);
    };

    /**
     * ダイアログの初期化処理を行います。
     */
    _705_HaigoCopy_Dialog.initialize = function () {

        var element = $("#_705_HaigoCopy_Dialog"),
            contentHeight = $(window).height() * 80 / 100;

        _705_HaigoCopy_Dialog.element = element;
        element.find(".modal-body").css("max-height", contentHeight);
        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        _705_HaigoCopy_Dialog.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#_705_HaigoCopy_Dialog .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body .detail",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        _705_HaigoCopy_Dialog.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#_705_HaigoCopy_Dialog .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body .detail",
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

        _705_HaigoCopy_Dialog.initializeControlEvent();
        _705_HaigoCopy_Dialog.header.initialize();
        _705_HaigoCopy_Dialog.haigoto.initialize();
        _705_HaigoCopy_Dialog.loadDialogs();
        _705_HaigoCopy_Dialog.loadHaigoDialogs();
    };

    /**
     * ダイアログコントロールへのイベントの紐づけを行います。
     */
    _705_HaigoCopy_Dialog.initializeControlEvent = function () {
        var element = _705_HaigoCopy_Dialog.element;

        //TODO: 画面全体で利用するコントロールのイベントの紐づけ処理をここに記述します。
        element.on("hidden.bs.modal", _705_HaigoCopy_Dialog.hidden);
        element.on("shown.bs.modal", _705_HaigoCopy_Dialog.shown);
        element.on("click", ".save", _705_HaigoCopy_Dialog.commands.save);
        element.on("click", ".clear", _705_HaigoCopy_Dialog.commands.clearScreen);
    };

    /**
     * ダイアログ非表示時処理を実行します。
     */
    _705_HaigoCopy_Dialog.hidden = function (e) {
        var element = _705_HaigoCopy_Dialog.element;

        element.find(":input").prop("disabled", false);
        element.find(".save").prop("disabled", false);
        element.findP("zenban_chk").prop("checked", false);
        element.findP("seihin_chk").prop("checked", false);
        //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
        element.find(":input:not([type='checkbox']):not([type='radio'])").val("");
        element.find("input[type='checkbox']").prop("checked", false);
        //TODO:radioボタンの初期表示は、画面要件に合わせて記述します。

        element.findP("data_count").text("");
        element.findP("data_count_total").text("");
        element.find(".kbnhin3").hide();
        element.findP("info_to").text("");

        element.find("[data-prop='nm_genryo'], [data-prop='cd_haigo'], [data-prop='nm_haigo']").text("");

        // Reset variables
        _705_HaigoCopy_Dialog.values.kbn_hin = null;

        var items = element.find(".header :input:not(button)");
        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            _705_HaigoCopy_Dialog.setColValidStyle(item);
        }

        _705_HaigoCopy_Dialog.notifyInfo.clear();
        _705_HaigoCopy_Dialog.notifyAlert.clear();
    };

    /**
     * ダイアログ表示時処理を実行します。
     */
    _705_HaigoCopy_Dialog.shown = function (e) {
        //初回起動時にdatatable作成
        _705_HaigoCopy_Dialog.loadMasterData().then(function () {

            //Check ini 製造用配合, 表示用配合
            //---------------------------------------------------------
            if (_705_HaigoCopy_Dialog.values.M_kirikae == App.settings.app.m_kirikae.hyoji) {
                _705_HaigoCopy_Dialog.haigoto.element.findP("kbn_seizo").prop("checked", true);
                _705_HaigoCopy_Dialog.haigoto.element.find("[data-prop='cd_kojyo'], [data-prop='nm_kojyo']").prop("disabled", false);
            } else {
                _705_HaigoCopy_Dialog.haigoto.element.findP("kbn_hyoji").prop("checked", true);
                _705_HaigoCopy_Dialog.haigoto.element.find("[data-prop='cd_kojyo'], [data-prop='nm_kojyo']").prop("disabled", true);
            }

            if (_705_HaigoCopy_Dialog.values.M_kirikae == App.settings.app.m_kirikae.hyoji || !App.ui.page.user.flg_kojyokan_sansyo) {
                _705_HaigoCopy_Dialog.haigoto.element.find("[data-prop='kbn_seizo'], [data-prop='kbn_hyoji']").prop("disabled", true);
            }
            //---------------------------------------------------------

            if (!App.ui.page.user.flg_kojyokan_sansyo) {
                _705_HaigoCopy_Dialog.haigoto.element.find("[data-prop='cd_kojyo'], [data-prop='nm_kojyo']").prop("disabled", true);
            }

            _705_HaigoCopy_Dialog.header.search(true);

            _705_HaigoCopy_Dialog.element.find(":input:not(button):first").focus();

        });
    };

    /**
     * マスターデータのロード処理を実行します。
     */
    _705_HaigoCopy_Dialog.loadMasterData = function () {

        _705_HaigoCopy_Dialog.options.filter = _705_HaigoCopy_Dialog.header.createFilter();

        //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
        return $.ajax(App.ajax.webapi.get(_705_HaigoCopy_Dialog.urls.getHanList, _705_HaigoCopy_Dialog.options.filter)).then(function (result) {
            var no_han = _705_HaigoCopy_Dialog.header.element.findP("no_han");
            no_han.children().remove();
            App.ui.appendOptions(
                no_han,
                "no_han",
                "no_han",
                result,
                false
            );

            no_han.val(_705_HaigoCopy_Dialog.options.filter.no_han);

            return $.ajax(App.ajax.odata.get(App.str.format(_705_HaigoCopy_Dialog.urls.vw_kaisha_kojyo, _705_HaigoCopy_Dialog.options.filter.cd_kaisha))).then(function (result) {

                var element = _705_HaigoCopy_Dialog.haigoto.element.findP("nm_kojyo");
                element.children().remove();

                App.ui.appendOptions(
                    element,
                    "cd_kojyo",
                    "nm_kojyo",
                    result.value,
                    false
                );

                element.val(parseInt(_705_HaigoCopy_Dialog.options.filter.cd_kojyo));
                _705_HaigoCopy_Dialog.haigoto.element.findP("cd_kojyo").val(_705_HaigoCopy_Dialog.addZero(_705_HaigoCopy_Dialog.options.filter.cd_kojyo, "0000"));
                _705_HaigoCopy_Dialog.haigoto.element.findP("cd_kojyo_validate").text(_705_HaigoCopy_Dialog.options.filter.cd_kaisha);

                // SKS_MOD_2020
                _705_HaigoCopy_Dialog.values.lstKojyo = result.value;
                _705_HaigoCopy_Dialog.changeNMACS();
                // SKS_MOD_2020
            });
        }).then(function () {
            _705_HaigoCopy_Dialog.haigoto.element.findP("cd_genryo").prop("maxlength", _705_HaigoCopy_Dialog.values.su_code_standard);
            _705_HaigoCopy_Dialog.haigoto.element.findP("cd_genryo").val(_705_HaigoCopy_Dialog.values.cd_haigo);
            _705_HaigoCopy_Dialog.haigoto.options.validations.cd_genryo.rules.maxlength = _705_HaigoCopy_Dialog.values.su_code_standard;
        });

        //TODO: マスタデータのロード処理を実装後、下の1行を削除してください。
        return App.async.success();
    };

    /**
    * Change NMACS value when change kojyo
    */
    _705_HaigoCopy_Dialog.changeNMACS = function () {
        var cd_kojyo = _705_HaigoCopy_Dialog.haigoto.element.findP("cd_kojyo").val();
        $.each(_705_HaigoCopy_Dialog.values.lstKojyo, function (ind, item) {
            if (Number(item.cd_kojyo) === Number(cd_kojyo)) {
                _705_HaigoCopy_Dialog.values.kbn_nmacs_kojyo = item.kbn_nmacs_kojyo;
            }
        })
    }

    /**
    * Load no_han to。
    */
    _705_HaigoCopy_Dialog.loadNoHanTo = function (nohanCode) {
        var data = [{ no_han: nohanCode }];
        var element = _705_HaigoCopy_Dialog.haigoto.element.findP("no_han");
        element.children().remove();

        App.ui.appendOptions(
            element,
            "no_han",
            "no_han",
            data,
            false
        );
    }

    /**
    * Add zero before number。
    */
    _705_HaigoCopy_Dialog.addZero = function (value, zeroList) {
        var length = zeroList.length;
        if (!App.isUndefOrNullOrStrEmpty(value) && App.isNumeric(value)) {
            value = (zeroList + value).slice(-length);
        }
        return value;
    }

    /**
     * 共有のダイアログのロード処理を実行します。
     */
    _705_HaigoCopy_Dialog.loadDialogs = function () {

        if ($.find("#ConfirmDialog").length == 0) {
            return App.async.all({
                confirmDialog: $.get(_705_HaigoCopy_Dialog.urls.confirmDialog)
            }).then(function (result) {
                $("#dialog-container").append(result.successes.confirmDialog);
                _705_HaigoCopy_Dialog.dialogs.confirmDialog = ConfirmDialog;
            });
        } else {
            _705_HaigoCopy_Dialog.dialogs.confirmDialog = ConfirmDialog;
        }
    };

    /**
     * 共有のダイアログのロード処理を実行します。
     */
    _705_HaigoCopy_Dialog.loadHaigoDialogs = function () {

        if ($.find("#_710_HinmeiKaihatsu_Dialog").length == 0) {
            return App.async.all({
                HinmeiKaihatsuDialog: $.get(_705_HaigoCopy_Dialog.urls.HinmeiKaihatsuDialog),
            }).then(function (result) {
                $("#dialog-container").append(result.successes.HinmeiKaihatsuDialog);
                _705_HaigoCopy_Dialog.dialogs.HinmeiKaihatsuDialog = _710_HinmeiKaihatsu_Dialog;
                _705_HaigoCopy_Dialog.dialogs.HinmeiKaihatsuDialog.param.kbn_hin_search = _705_HaigoCopy_Dialog.values.kbn_hin_search;
                _705_HaigoCopy_Dialog.dialogs.HinmeiKaihatsuDialog.initialize();
            });
        } else {
            _705_HaigoCopy_Dialog.dialogs.HinmeiKaihatsuDialog = _710_HinmeiKaihatsu_Dialog;
        }
    };

    /**
     * ダイアログヘッダー　バリデーションルールを定義します。
     */
    _705_HaigoCopy_Dialog.haigoto.options.validations = {
        dt_from: {
            rules: {
                requiedCustom705: true,
                datestring: true,
                maxlength: 10,
                rangeYearMonthDay: ["1950/01/01", "3000/12/31"],
                lessthan_ymd_to: [true, _705_HaigoCopy_Dialog, "dt_to"],
            },
            options: {
                name: "有効期限(開始)",
            },
            messages: {
                requiedCustom705: App.messages.base.required,
                datestring: App.messages.base.datestring,
                maxlength: App.messages.base.maxlength,
                rangeYearMonthDay: App.messages.base.range,
                lessthan_ymd_to: App.messages.base.MS0014
            }
        },
        dt_to: {
            rules: {
                requiedCustom705: true,
                datestring: true,
                maxlength: 10,
                rangeYearMonthDay: ["1950/01/01", "3000/12/31"]
            },
            options: {
                name: "有効期限(終了)",
            },
            messages: {
                requiedCustom705: App.messages.base.required,
                datestring: App.messages.base.datestring,
                maxlength: App.messages.base.maxlength,
                rangeYearMonthDay: App.messages.base.range
            }
        },
        cd_kojyo: {
            rules: {
                required: true,
                maxlength: 4,
                digits: true,
                existsCode: function (value, opts, state, done) {
                    var element = _705_HaigoCopy_Dialog.haigoto.element;
                    var cd_kojyo = element.findP("cd_kojyo").val();
                    var cd_kojyo_validate = element.findP("cd_kojyo_validate").text();

                    if (App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                        return done(true);
                    }


                    return done(cd_kojyo_validate != "");
                }
            },
            options: {
                name: "工場"
            },
            messages: {
                required: App.messages.base.required,
                maxlength: App.messages.base.maxlength,
                digits: App.messages.base.digits,
                existsCode: App.messages.app.AP0010
            }
        },
        cd_genryo: {
            rules: {
                required: true,
                maxlength: _705_HaigoCopy_Dialog.values.su_code_standard,
                digits: true,
                genryoAdmin: function (value, opts, state, done) {
                    if(App.isUndefOrNullOrStrEmpty(value)){
                        return done(true);
                    }

                    if ((_705_HaigoCopy_Dialog.values.su_code_standard - 1) == 6) {
                        if ((value >= 9999990 && value <= 9999999) || (value >= 999990 && value <= 999999)) {
                            return done(false);
                        }
                    }
                    if ((_705_HaigoCopy_Dialog.values.su_code_standard - 1) == 11) {
                        if ((value >= 99999999990 && value <= 99999999999) || (value >= 999999999990 && value <= 999999999999)) {
                            return done(false);
                        }
                    }

                    return done(true);
                }
            },
            options: {
                name: "配合コード"
            },
            messages: {
                required: App.messages.base.required,
                maxlength: App.messages.base.maxlength,
                digits: App.messages.app.AP0165,
                genryoAdmin: App.messages.app.AP0139
            }
        },
        ritsu_kihon: {
            rules: {
                required: true,
                pointlengthCustom_dialog: [2, 2],
                isValueZero: true
            },
            options: {
                name: "倍率"
            },
            messages: {
                required: App.messages.base.required,
                pointlengthCustom_dialog: App.messages.base.pointlength,
                isValueZero: App.messages.app.AP0066
            }
        },
        qty_kihon: {
            rules: {
                required: true,
                maxlength: 10,
                number: true,
                isValueZero: true
            },
            options: {
                name: "基本重量"
            },
            messages: {
                required: App.messages.base.required,
                maxlength: App.messages.base.maxlength.replace('{param}', '8'),
                number: App.messages.base.digits,
                isValueZero: App.messages.app.AP0114
            }
        },
        no_han: {
            rules: {
                requiedCustom705: true
            },
            options: {
                name: "版番号"
            },
            messages: {
                requiedCustom705: App.messages.base.required
            }
        }
    };

    /**
    * Custom required。
    */
    App.validation.addMethod("requiedCustom705", function (value, param, opts, done) {
        var zenbanChecked = _705_HaigoCopy_Dialog.header.element.findP("zenban_chk").prop("checked");

        if (App.isUndefOrNullOrStrEmpty(value) && !zenbanChecked) {
            return done(false);
        }

        return done(true);
    });

    // 入力引数： value[対象数値],beforePoint[整数部の桁数],
    //            afterPoint[小数点以下桁数],minus[マイナス可不可]
    App.validation.addMethod("pointlengthCustom_dialog", function (value, param, opts, done) {
        if (App.isUndefOrNullOrStrEmpty(value)) {
            return done(true);
        }

        var beforePoint = param[0];
        var afterPoint = param[1];
        var minus = param[2];
        //文字列がnullの時はtrueを返す
        value = App.isNum(value) ? value + "" : value;
        //カンマがあったら削除
        value = value.toString();
        value = value.replace(/,/g, "");

        var isPoint = false;
        if (afterPoint > 0) {
            isPoint = true;
        }
        if (!App.isNumeric(value)) {
            return done(false);
        }
        value = App.num.format(parseFloat(value), "#,#.########");
        afterPoint = parseFloat(afterPoint);
        beforePoint = parseFloat(beforePoint);

        //小数点以下の数をチェック
        var point = value.indexOf("."),
            after, before;
        if (point >= 0) {
            after = value.substring((point + 1));
            if (after.length > afterPoint) {
                return done(false);
            }
            before = value.substring(0, point);
        }
        else {
            before = value;
        }

        //整数部分から"-"を取り除く
        if (minus && before.match(/^-/)) {
            before = before.substring(1);
        }
        //整数部分のチェック
        if (before.length > beforePoint) {
            return done(false);
        }
        
        done(true);
    }, "integer {param[0]}-digit, entered in {param[1]} decimal places");


    /**
    * Custom required。
    */
    App.validation.addMethod("isValueZero", function (value, param, opts, done) {
        if (App.isUndefOrNullOrStrEmpty(value)) {
            return done(true);
        }
        if(value == "0" || value == "0.00"){
            return done(false);
        }
        return done(true);
    });

    /**
     * ダイアログヘッダーの初期化処理を行います。
     */
    _705_HaigoCopy_Dialog.header.initialize = function () {
        var element = _705_HaigoCopy_Dialog.element.find(".haigo-from");
        _705_HaigoCopy_Dialog.header.element = element;

        //TODO: 画面ヘッダーの初期化処理をここに記述します。
        //TODO: 画面ヘッダーで利用するコントロールのイベントの紐づけ処理をここに記述します。
        element.on("change", ":input", _705_HaigoCopy_Dialog.header.change);
    };

    /**
     * ダイアログヘッダーの初期化処理を行います。
     */
    _705_HaigoCopy_Dialog.haigoto.initialize = function () {

        var element = _705_HaigoCopy_Dialog.element.find(".haigo-to");
        _705_HaigoCopy_Dialog.haigoto.validator = element.validation(_705_HaigoCopy_Dialog.createValidator(_705_HaigoCopy_Dialog.haigoto.options.validations));
        _705_HaigoCopy_Dialog.haigoto.element = element;

        //TODO: 画面ヘッダーの初期化処理をここに記述します。
        //TODO: 画面ヘッダーで利用するコントロールのイベントの紐づけ処理をここに記述します。
        element.on("change", ":input", _705_HaigoCopy_Dialog.haigoto.change);
        element.on("focusout", "[data-prop='ritsu_kihon'], [data-prop='qty_kihon']", _705_HaigoCopy_Dialog.haigoto.focusoutInput);
        element.on("focus", "[data-prop='ritsu_kihon'], [data-prop='qty_kihon']", _705_HaigoCopy_Dialog.haigoto.focusInput);

        element.find(":input[data-role='date']").datepicker();
        element.find(":input[data-role='date']").on("keyup", App.data.addSlashForDateString);
        element.on("click", ".haigo-kensaku", _705_HaigoCopy_Dialog.openHaigoDialog);
    };

    /**
     * Focus format number 。
     */
    _705_HaigoCopy_Dialog.haigoto.focusInput = function(e) {
        var target = $(e.target),
            value = target.val();

        if (value === "") {
            return;
        }
        value = Number($.trim(value).replace(/,/g, ""));
        if (isNaN(value)) {
            return;
        }
        value = value.toString();
        target.val(value);
    }

    /**
     * Focus out format number 。
     */
    _705_HaigoCopy_Dialog.haigoto.focusoutInput = function(e) {
        var target = $(e.target),
            value = target.val(),
            format = target.attr("format-number");

        if (value === "") {
            return;
        }
        value = Number($.trim(value).replace(/,/g, ""));
        if (isNaN(value)) {
            return;
        }
        value = App.num.format(value, format);
        target.val(value);
    }

    /**
     * Open dialog 710_HinmeiKaihatsu_Dialog 。
     */
    _705_HaigoCopy_Dialog.openHaigoDialog = function () {
        var filter = _705_HaigoCopy_Dialog.header.createFilterSearch();
        _705_HaigoCopy_Dialog.notifyAlert.clear();

        _705_HaigoCopy_Dialog.dialogs.HinmeiKaihatsuDialog.param.cd_kojyo = filter.cd_kojyo;
        _705_HaigoCopy_Dialog.dialogs.HinmeiKaihatsuDialog.param.cd_kaisha = filter.cd_kaisha;
        _705_HaigoCopy_Dialog.dialogs.HinmeiKaihatsuDialog.param.no_gamen = App.settings.app.no_gamen.haigo_copy_dialog;
        _705_HaigoCopy_Dialog.dialogs.HinmeiKaihatsuDialog.param.kbn_hin_search = _705_HaigoCopy_Dialog.values.kbn_hin_search;
        if (_705_HaigoCopy_Dialog.haigoto.element.findP("kbn_seizo").prop("checked")) {
            _705_HaigoCopy_Dialog.dialogs.HinmeiKaihatsuDialog.param.M_kirikae = 2;
        }
        if (_705_HaigoCopy_Dialog.haigoto.element.findP("kbn_hyoji").prop("checked")) {
            _705_HaigoCopy_Dialog.dialogs.HinmeiKaihatsuDialog.param.M_kirikae = 1;
        }

        _705_HaigoCopy_Dialog.dialogs.HinmeiKaihatsuDialog.element.modal("show");

        _705_HaigoCopy_Dialog.dialogs.HinmeiKaihatsuDialog.dataSelected = function (data) {
            var element = _705_HaigoCopy_Dialog.haigoto.element,
                id = element.attr("data-key"),
                property = element.findP("cd_genryo").attr("data-prop"),
                entity = _705_HaigoCopy_Dialog.haigoto.data.entry(id);

            var zeroList = "";
            for (var i = 0; i < _705_HaigoCopy_Dialog.values.su_code_standard; i++) {
                zeroList = zeroList + "0";
            }

            element.findP("cd_genryo").val(_705_HaigoCopy_Dialog.addZero(data.cd_hin, zeroList));
            element.findP("nm_genryo").text(data.nm_hin);

            entity.cd_genryo = data.cd_hin;
            entity.nm_genryo = data.nm_hin;
            entity.kbn_hin = data.kbn_hin;

            _705_HaigoCopy_Dialog.haigoto.data.update(entity);

            _705_HaigoCopy_Dialog.reloadHaigoToDialog();

            _705_HaigoCopy_Dialog.setMargin();
        }
    }

    /**
     * Research haigo to with cd_haigo from dialog。
     */
    _705_HaigoCopy_Dialog.reloadHaigoToDialog = function () {
        
        _705_HaigoCopy_Dialog.options.filter = _705_HaigoCopy_Dialog.header.createFilterSearch();
        if (!App.isUndefOrNullOrStrEmpty(_705_HaigoCopy_Dialog.options.filter.cd_kojyo) && !App.isUndefOrNullOrStrEmpty(_705_HaigoCopy_Dialog.options.filter.cd_haigo)) {
            App.ui.loading.show();
            _705_HaigoCopy_Dialog.notifyAlert.clear();
            $.ajax(App.ajax.webapi.get(_705_HaigoCopy_Dialog.urls.haigoTo, _705_HaigoCopy_Dialog.options.filter))
            .done(function (result) {
                if (result != null) {
                    _705_HaigoCopy_Dialog.haigoto.bind(result);
                }
            }).fail(function (error) {
                _705_HaigoCopy_Dialog.notifyAlert.message(App.ajax.handleError(error).message).show();
            }).always(function () {
                App.ui.loading.close();
            });
        }
    }

    /**
     * Research haigo to with cd_haigo from screen。
     */
    _705_HaigoCopy_Dialog.reloadHaigoToScreen = function () {
        _705_HaigoCopy_Dialog.options.filter = _705_HaigoCopy_Dialog.header.createFilterSearch();

        if (!App.isUndefOrNullOrStrEmpty(_705_HaigoCopy_Dialog.options.filter.cd_kojyo) && !App.isUndefOrNullOrStrEmpty(_705_HaigoCopy_Dialog.options.filter.cd_haigo)) {
            _705_HaigoCopy_Dialog.haigoto.validator.validate({
                targets: _705_HaigoCopy_Dialog.haigoto.element.find("[data-prop='cd_genryo'],[data-prop='cd_kojyo']")
            }).then(function () {
                App.ui.loading.show();
                _705_HaigoCopy_Dialog.notifyAlert.clear();
                $.ajax(App.ajax.webapi.get(_705_HaigoCopy_Dialog.urls.haigoTo, _705_HaigoCopy_Dialog.options.filter))
                .done(function (result) {
                    if (result != null) {
                        _705_HaigoCopy_Dialog.haigoto.bind(result, null, _705_HaigoCopy_Dialog.options.filter.cd_haigo);
                    } else {                        
                        _705_HaigoCopy_Dialog.resetValidate();
                    }
                }).fail(function (error) {
                    _705_HaigoCopy_Dialog.notifyAlert.message(App.ajax.handleError(error).message).show();
                    _705_HaigoCopy_Dialog.resetValidate();
                }).always(function () {
                    App.ui.loading.close();
                });
            }).fail(function () {
                _705_HaigoCopy_Dialog.resetValidate();
            });
        } else {
            _705_HaigoCopy_Dialog.resetValidate();
        }
    }

    /**
     * Reset validation of [dt_from], [dt_to]
     */
    _705_HaigoCopy_Dialog.resetValidate = function () {
        _705_HaigoCopy_Dialog.haigoto.validator.validate({
            targets: _705_HaigoCopy_Dialog.haigoto.element.find("[data-prop='dt_from'], [data-prop='dt_to']"),
            filter: function (item, method, state, options) {
                return method !== "requiedCustom705"
            }
        });
    }

    /**
     * ダイアログの検索処理を実行します。
     */
    _705_HaigoCopy_Dialog.header.change = function (e) {
        var target = $(e.target),
            element = _705_HaigoCopy_Dialog.header.element,
            elementTo = _705_HaigoCopy_Dialog.haigoto.element,
            id = element.attr("data-key"),
            property = target.attr("data-prop");

        if (property == "zenban_chk") {
            _705_HaigoCopy_Dialog.controlMode(property);
        }
    }

    // SKS_MOD_2020
    /**
     * Function change element status when dialog changed
     */
    _705_HaigoCopy_Dialog.controlMode = function (property) {
        var element = _705_HaigoCopy_Dialog.header.element,
            elementTo = _705_HaigoCopy_Dialog.haigoto.element,
            dataTo,
            zenbanChecked = element.findP("zenban_chk").prop("checked"),
            kbnSeizoChecked = elementTo.findP("kbn_seizo").prop("checked"), // 製造用配合
            kbnHyojiChecked = elementTo.findP("kbn_hyoji").prop("checked"); // 表示用配合

        $.each(_705_HaigoCopy_Dialog.haigoto.data.entries, function (id, item) {
            dataTo = _705_HaigoCopy_Dialog.haigoto.data.entry(id);
        })
        if (zenbanChecked) {
            
            _705_HaigoCopy_Dialog.loadNoHanTo("");
            elementTo.find("[data-prop='dt_from'], [data-prop='dt_to'], [data-prop='no_han']").prop("disabled", true);
            elementTo.find("[data-prop='dt_from'], [data-prop='dt_to']").val("");
            if (property == "zenban_chk") {
                if (_705_HaigoCopy_Dialog.values.modeShow == 1) {
                    elementTo.findP("nm_genryo").text("");
                    elementTo.find("[data-prop='cd_genryo'], [data-prop='qty_kihon']").val("");
                    elementTo.findP("info_to").text("");
                    _705_HaigoCopy_Dialog.haigoto.validator.validate({
                        targets: elementTo.find("[data-prop='cd_genryo'], [data-prop='qty_kihon']"),
                        filter: function (item, method, state, options) {
                            return method !== "required"
                        }
                    });
                }
            }
            _705_HaigoCopy_Dialog.haigoto.validator.validate({
                targets: elementTo.find("[data-prop='dt_from'], [data-prop='dt_to']")
            });
        } else {
            if (App.isUndefOrNullOrStrEmpty(elementTo.findP("cd_genryo").val())) {
                elementTo.findP("no_han").val("").prop("disabled", false);
                if (kbnHyojiChecked) {
                    elementTo.findP("dt_from").val("").prop("disabled", false);
                    elementTo.findP("dt_to").val("").prop("disabled", false);
                } else {
                    if (_705_HaigoCopy_Dialog.values.kbn_nmacs_kojyo == _705_HaigoCopy_Dialog.values.kbn_nmacs_general) {
                        elementTo.findP("dt_from").val("").prop("disabled", false);
                        elementTo.findP("dt_to").val("3000/12/31").prop("disabled", true);
                        dataTo.dt_to = "3000/12/31";
                    } else {
                        elementTo.findP("dt_from").val("").prop("disabled", false);
                        elementTo.findP("dt_to").val("").prop("disabled", false);
                    }
                }
                if (property == "zenban_chk") {
                    _705_HaigoCopy_Dialog.loadNoHanTo("");
                }
            } else {
                if (property == "zenban_chk") {
                    _705_HaigoCopy_Dialog.loadNoHanTo(1);
                    elementTo.findP("dt_from").val(dataTo.dt_from);
                    elementTo.findP("dt_to").val(dataTo.dt_to);
                }
                if (!_705_HaigoCopy_Dialog.values.existGenryo) {
                    elementTo.findP("no_han").prop("disabled", true);
                    elementTo.findP("dt_from").prop("disabled", true);
                    elementTo.findP("dt_to").prop("disabled", true);
                } else {
                    if (property == "zenban_chk") {
                        _705_HaigoCopy_Dialog.loadNoHanTo((dataTo.no_han_max && dataTo.isDuplicate) ? dataTo.no_han_max + 1 : 1);
                        elementTo.findP("no_han").prop("disabled", false);
                    }
                    if (kbnHyojiChecked) {
                        elementTo.findP("dt_from").val("").prop("disabled", false);
                        elementTo.findP("dt_to").val("").prop("disabled", false);
                    } else {
                        elementTo.findP("dt_from").val("").prop("disabled", false);
                        elementTo.findP("dt_to").val("").prop("disabled", false);
                        if (_705_HaigoCopy_Dialog.values.kbn_nmacs_kojyo == _705_HaigoCopy_Dialog.values.kbn_nmacs_general) {
                            elementTo.findP("dt_to").val("3000/12/31").prop("disabled", true);
                            dataTo.dt_to = "3000/12/31";
                        }
                    }
                }
            }

            _705_HaigoCopy_Dialog.notifyAlert.remove("AP0069");

        }

        if (!elementTo.findP("cd_genryo").val()) {
            _705_HaigoCopy_Dialog.haigoto.element.findP("qty_kihon").val("").prop("disabled", true);
        } else if (dataTo.modeBind == 1 || dataTo.modeBind == 2) {
            _705_HaigoCopy_Dialog.haigoto.element.findP("qty_kihon").prop("disabled", true);
        }
        else {
            _705_HaigoCopy_Dialog.haigoto.element.findP("qty_kihon").prop("disabled", false);
        }
    }
    // SKS_MOD_2020

    /**
     * ダイアログの検索処理を実行します。
     */
    _705_HaigoCopy_Dialog.haigoto.change = function (e) {
        var target = $(e.target),
            element = _705_HaigoCopy_Dialog.haigoto.element,
            id = element.attr("data-key"),
            property = target.attr("data-prop"),
            entity = _705_HaigoCopy_Dialog.haigoto.data.entry(id),
            options = {
                filter: _705_HaigoCopy_Dialog.haigoto.validationFilter
            };


        if (property == "kbn_seizo" || property == "kbn_hyoji") {
            if(property == "kbn_hyoji"){
                element.findP("nm_kojyo").val(parseInt(_705_HaigoCopy_Dialog.values.cd_kojyo)).change();
            }
            if (App.ui.page.user.flg_kojyokan_sansyo) {
                if (element.findP("kbn_seizo").prop("checked")) {
                    element.find("[data-prop='cd_kojyo'], [data-prop='nm_kojyo']").prop("disabled", false);
                } else {
                    element.find("[data-prop='cd_kojyo'], [data-prop='nm_kojyo']").prop("disabled", true);
                }
            }
            _705_HaigoCopy_Dialog.controlMode(property);
        }

        if (property == "cd_kojyo") {
            var cd_kojyo = target.val();
            element.findP("cd_kojyo").val(_705_HaigoCopy_Dialog.addZero(cd_kojyo, "0000"));
            element.findP("cd_kojyo_validate").text(cd_kojyo);
            element.findP("nm_kojyo").val(cd_kojyo);
            if (App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                element.findP("nm_kojyo").val(cd_kojyo);
            }
            if (cd_kojyo != "" && App.isNumeric(cd_kojyo)) {
                cd_kojyo = parseInt(cd_kojyo);

                _705_HaigoCopy_Dialog.checkData(App.str.format(_705_HaigoCopy_Dialog.urls.vw_kaisha_kojyo, "{0}", " and cd_kojyo eq {1}")
                                                , _705_HaigoCopy_Dialog.values.cd_kaisha, cd_kojyo,
                                        element, "cd_kojyo", "cd_kojyo_validate", "nm_kojyo", cd_kojyo);
            }

        }

        if (property == "nm_kojyo") {
            var cd_kojyo = target.val();

            element.findP("cd_kojyo").val(_705_HaigoCopy_Dialog.addZero(cd_kojyo, "0000"));
            element.findP("cd_kojyo_validate").text(cd_kojyo);
            element.findP("nm_kojyo").val(cd_kojyo);

            _705_HaigoCopy_Dialog.haigoto.validator.validate({
                targets: element.findP("cd_kojyo")
            });
        }

        if (property == "cd_genryo") {
            var zeroList = "";
            var cd_genryo = target.val();
            for (var i = 0; i < _705_HaigoCopy_Dialog.values.su_code_standard; i++) {
                zeroList = zeroList + "0";
            }

            element.findP("cd_genryo").val(_705_HaigoCopy_Dialog.addZero(cd_genryo, zeroList));
            element.findP("nm_genryo").text("");

            if (App.isUndefOrNullOrStrEmpty(cd_genryo)) {
                _705_HaigoCopy_Dialog.loadNoHanTo("");
                element.findP("dt_from").val("");
                element.findP("dt_to").val("");
                element.findP("qty_kihon").val("");
            }
        }
        if (property == "cd_kojyo" || property == "cd_genryo" || property == "nm_kojyo") {
            _705_HaigoCopy_Dialog.notifyAlert.remove("AP0058");
        }

        _705_HaigoCopy_Dialog.haigoto.validator.validate({
            targets: target
        }).then(function () {
            entity[property] = element.form().data()[property];
            _705_HaigoCopy_Dialog.haigoto.data.update(entity);

            if (property == "cd_genryo") {
                _705_HaigoCopy_Dialog.reloadHaigoToScreen();
            }

            if (property == "cd_kojyo" || property == "nm_kojyo") {
                _705_HaigoCopy_Dialog.reloadHaigoToScreen();
            }

            if (property == "kbn_seizo" || property == "kbn_hyoji") {
                _705_HaigoCopy_Dialog.reloadHaigoToScreen();
            }
            if(property == "dt_to"){
                _705_HaigoCopy_Dialog.haigoto.validator.validate({
                    targets: _705_HaigoCopy_Dialog.haigoto.element.findP("dt_from")
                });
            }

            // SKS_MOD 2020
            if (property == "cd_kojyo" || property == "nm_kojyo") {
                _705_HaigoCopy_Dialog.changeNMACS();
                _705_HaigoCopy_Dialog.controlMode();
            }
            // SKS_MOD_2020
        }).fail(function () {
            if (property == "cd_kojyo" || property == "nm_kojyo") {
                _705_HaigoCopy_Dialog.resetValidate();
            }
        }).always(function () {
        });
    }


    /**
   * 検索処理を定義します。
   */
    _705_HaigoCopy_Dialog.checkData = function (url, param1, param2, element, cd_code, cd_validate, nm_name, value) {
        if (param1 == "") {
            param1 = "null";
        }
        return $.ajax(App.ajax.odata.get(App.str.format(url, param1, param2))).then(function (result) {
            if (result.value.length > 0) {
                element.findP(cd_code).val(_705_HaigoCopy_Dialog.addZero(value, "0000"));
                element.findP(cd_validate).text(value);
                element.findP(nm_name).val(value);
            } else {
                element.findP(cd_validate).text("");
                element.findP(nm_name).val("");
            }
            _705_HaigoCopy_Dialog.haigoto.validator.validate({
                targets: element.find("[data-prop='" + cd_code + "'], [data-prop='" + nm_name + "']")
            });
        });
    }

    /**
     * 明細の各行にデータを設定する際のオプションを定義します。
     */
    _705_HaigoCopy_Dialog.header.options.bindOption = {
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
    * ダイアログ明細へのデータバインド処理を行います。
    */
    _705_HaigoCopy_Dialog.header.bind = function (data, isNewData) {
        var i, l, dataSet;

        dataSet = App.ui.page.dataSet();
        _705_HaigoCopy_Dialog.header.data = dataSet;

        if (data != null) {
            if (data.length > 0) {
                var item = data[0];
                dataSet.attach.bind(dataSet)(item);
                _705_HaigoCopy_Dialog.header.element.form(_705_HaigoCopy_Dialog.header.options.bindOption).bind(item);

                if (item.kbn_hin == App.settings.app.kbn_hin.kbn_hin_3) {
                    _705_HaigoCopy_Dialog.header.element.find(".kbnhin3").show();
                    if (_705_HaigoCopy_Dialog.values.M_kirikae == App.settings.app.m_kirikae.foodprocs) {
                        _705_HaigoCopy_Dialog.header.element.findP("seihin_chk").prop("checked", true);
                    }
                }
            }
        }
        //バリデーションを実行します。
        //_705_HaigoCopy_Dialog.detail.validateList(true);
    };

    /**
    * ダイアログ明細へのデータバインド処理を行います。
    */
    _705_HaigoCopy_Dialog.haigoto.bind = function (data, haigoFrom, cd_haigo_to) {
        var i, l, dataSet;

        dataSet = App.ui.page.dataSet();
        _705_HaigoCopy_Dialog.haigoto.data = dataSet;
        _705_HaigoCopy_Dialog.haigoto.element.findP("info_to").text("");
        if (data != null && data.length > 0) {
            var item = data[0];
            if (!App.isUndefOrNullOrStrEmpty(item.isError)) {
                _705_HaigoCopy_Dialog.notifyAlert.message(App.str.format(App.messages.app.AP0058, "配合", item.isError), "AP0058").show();
            }

            var zenbanChecked = _705_HaigoCopy_Dialog.header.element.findP("zenban_chk").prop("checked");
            if (zenbanChecked && (item.modeBind == 1 || item.modeBind == 2)) {
                _705_HaigoCopy_Dialog.notifyAlert.message(App.messages.app.AP0069, "AP0069").show();
            }
            if (haigoFrom != null) {
                if (haigoFrom.length > 0) {
                    var from = haigoFrom[0];
                    _705_HaigoCopy_Dialog.values.qty_kihon_old = from.qty_kihon;
                }
            }
            if (item.modeBind == 1) {
                item.dt_from = "1950/01/01";
                item.dt_to = "3000/12/31";
                item.ritsu_kihon = "1.00";
                if (haigoFrom != null) {
                    if (haigoFrom.length > 0) {
                        var from = haigoFrom[0];

                        item.cd_genryo = from.cd_haigo;
                        item.qty_kihon = from.qty_kihon;
                        _705_HaigoCopy_Dialog.values.qty_kihon_old = from.qty_kihon;
                    }
                } else {
                    item.qty_kihon = _705_HaigoCopy_Dialog.values.qty_kihon_old;
                }

                dataSet.attach.bind(dataSet)(item);
                _705_HaigoCopy_Dialog.loadNoHanTo(1);
                _705_HaigoCopy_Dialog.values.modeShow = 0;
                _705_HaigoCopy_Dialog.values.existGenryo = 1;
                if (!item.isDuplicate) {
                    _705_HaigoCopy_Dialog.values.existGenryo = 0;
                }
                _705_HaigoCopy_Dialog.haigoto.element.find("[data-prop='dt_from'], [data-prop='dt_to'], [data-prop='no_han']").prop("disabled", true);
                
                _705_HaigoCopy_Dialog.values.kbn_hin = item.kbn_hin;
                _705_HaigoCopy_Dialog.haigoto.element.findP("qty_kihon").prop("disabled", false);
                _705_HaigoCopy_Dialog.controlMode("bind");
            }

            if (item.modeBind == 2) {
                _705_HaigoCopy_Dialog.values.modeShow = 1;
                _705_HaigoCopy_Dialog.values.existGenryo = 1;
                if (!item.isDuplicate) {
                    _705_HaigoCopy_Dialog.values.existGenryo = 0;
                }
                if (item.flg_mishiyo && item.countSeihin > 0) {
                    item.info_to = "製品情報登録済の未使用配合です。";
                }
                else if (item.flg_mishiyo && item.countSeihin == 0) {
                    item.info_to = "未使用配合です。";
                }
                else if (!item.flg_mishiyo && item.countSeihin > 0) {
                    item.info_to = "製品情報登録済の配合です。";
                } else {
                    item.info_to = "";
                }
                item.ritsu_kihon = "1.00";
                item.dt_from = null;
                item.dt_to = null;
                _705_HaigoCopy_Dialog.haigoto.element.find("[data-prop='dt_from'], [data-prop='dt_to'], [data-prop='no_han']").val("");
                _705_HaigoCopy_Dialog.loadNoHanTo(item.no_han_max + 1);
                _705_HaigoCopy_Dialog.values.kbn_hin = item.kbn_hin;
                _705_HaigoCopy_Dialog.haigoto.element.find("[data-prop='dt_from'], [data-prop='dt_to'], [data-prop='no_han']").prop("disabled", false);
                _705_HaigoCopy_Dialog.haigoto.element.findP("qty_kihon").prop("disabled", true);
            }

            dataSet.attach.bind(dataSet)(item);
            _705_HaigoCopy_Dialog.haigoto.element.form(_705_HaigoCopy_Dialog.haigoto.options.bindOption).bind(item);
            _705_HaigoCopy_Dialog.haigoto.element.findP("nm_genryo").removeClass("color-red");
            _705_HaigoCopy_Dialog.controlMode("bind");

        } else {
            var item = {
                nm_genryo: "",
                dt_from: "1950/01/01",
                dt_to: "3000/12/31",
                ritsu_kihon: "1.00",
                cd_genryo: "",
                nm_genryo: "",
                qty_kihon: ""
            };

            _705_HaigoCopy_Dialog.values.existGenryo = 0;

            if (!App.isUndefOrNullOrStrEmpty(cd_haigo_to)) {
                item.cd_genryo = cd_haigo_to;
            }
            _705_HaigoCopy_Dialog.haigoto.element.findP("nm_genryo").addClass("color-red");
            if (_705_HaigoCopy_Dialog.haigoto.element.findP("kbn_seizo").prop("checked")) {
                item.nm_genryo = "製造用配合マスタに存在しません。";
            } else {
                item.nm_genryo = "表示用配合マスタに存在しません。";
            }

            if (haigoFrom != null) {
                if (haigoFrom.length > 0) {
                    var from = haigoFrom[0];

                    item.cd_genryo = from.cd_haigo;
                    item.qty_kihon = from.qty_kihon;
                    _705_HaigoCopy_Dialog.values.qty_kihon_old = from.qty_kihon;
                }
            } else {
                item.qty_kihon = _705_HaigoCopy_Dialog.values.qty_kihon_old;
            }
            //Load combobox no_han [To]
            _705_HaigoCopy_Dialog.loadNoHanTo(1);
            _705_HaigoCopy_Dialog.values.modeShow = 0;
            _705_HaigoCopy_Dialog.haigoto.element.findP("qty_kihon").prop("disabled", false);
            dataSet.attach.bind(dataSet)(item);
            _705_HaigoCopy_Dialog.haigoto.element.form(_705_HaigoCopy_Dialog.haigoto.options.bindOption).bind(item);

            //_705_HaigoCopy_Dialog.haigoto.element.find("[data-prop='dt_from'], [data-prop='dt_to'], [data-prop='no_han']").prop("disabled", true);
            _705_HaigoCopy_Dialog.controlMode("bind");
        }

        _705_HaigoCopy_Dialog.setMargin();
        //バリデーションを実行します。
        var options = {
            filter: _705_HaigoCopy_Dialog.haigoto.validationFilter
        };
        _705_HaigoCopy_Dialog.haigoto.validator.validate(options);
    };


    /**
     * 明細の各行にデータを設定する際のオプションを定義します。
     */
    _705_HaigoCopy_Dialog.haigoto.options.bindOption = {
        // TODO: 主キーが直接入力の場合には、修正の場合変更を不可とします。
        appliers: {
            dt_from: function (value, element) {
                element.val(App.data.getDateString(new Date(value), true));
                return true;
            },
            dt_to: function (value, element) {
                element.val(App.data.getDateString(new Date(value), true));
                return true;
            },
            cd_genryo: function (value, element) {
                var zeroList = "";
                for (var i = 0; i < _705_HaigoCopy_Dialog.values.su_code_standard; i++) {
                    zeroList = zeroList + "0";
                }

                element.val(_705_HaigoCopy_Dialog.addZero(value, zeroList));
                return true;
            }
        }
    };


    /**
     * ダイアログの検索処理を実行します。
     */
    _705_HaigoCopy_Dialog.header.search = function (isLoading) {
        var element = _705_HaigoCopy_Dialog.element,
            loadingTaget = element.find(".modal-content"),
            deferred = $.Deferred(),
            query;

        _705_HaigoCopy_Dialog.options.filter = _705_HaigoCopy_Dialog.header.createFilterSearch();

        if (isLoading) {
            App.ui.loading.show("", loadingTaget);
            _705_HaigoCopy_Dialog.notifyAlert.clear();
        }

        $.ajax(App.ajax.webapi.get(_705_HaigoCopy_Dialog.urls.search, _705_HaigoCopy_Dialog.options.filter))
        .done(function (result) {
            if (result != null) {
                _705_HaigoCopy_Dialog.header.bind(result.DataFrom);
                _705_HaigoCopy_Dialog.haigoto.bind(result.DataTo, result.DataFrom);
            }
            deferred.resolve();
        }).fail(function (error) {

            _705_HaigoCopy_Dialog.notifyAlert.message(App.ajax.handleError(error).message).show();
            deferred.reject();
        }).always(function () {

            if (isLoading) {
                App.ui.loading.close(loadingTaget);
            }

        });


        return deferred.promise();
    };

    /**
     * ダイアログの検索条件を組み立てます
     */
    _705_HaigoCopy_Dialog.header.createFilter = function () {
        var filters = {};

        //TODO: 画面で設定された検索条件を取得し、データ取得サービスのフィルターオプションを組み立てます。
        if (!App.isUndefOrNullOrStrEmpty(_705_HaigoCopy_Dialog.values.M_kirikae)) {
            filters.m_kirikae = _705_HaigoCopy_Dialog.values.M_kirikae;
        }

        if (!App.isUndefOrNullOrStrEmpty(_705_HaigoCopy_Dialog.values.cd_kaisha)) {
            filters.cd_kaisha = _705_HaigoCopy_Dialog.values.cd_kaisha;
        }

        if (!App.isUndefOrNullOrStrEmpty(_705_HaigoCopy_Dialog.values.cd_kojyo)) {
            filters.cd_kojyo = _705_HaigoCopy_Dialog.values.cd_kojyo;
        }

        if (!App.isUndefOrNullOrStrEmpty(_705_HaigoCopy_Dialog.values.cd_haigo)) {
            filters.cd_haigo = _705_HaigoCopy_Dialog.values.cd_haigo;
        }

        if (!App.isUndefOrNullOrStrEmpty(_705_HaigoCopy_Dialog.values.no_han)) {
            filters.no_han = _705_HaigoCopy_Dialog.values.no_han;
        }

        if (_705_HaigoCopy_Dialog.haigoto.element.findP("kbn_seizo").prop("checked")) {
            filters.radio_kirikae = 2;
        }
        if (_705_HaigoCopy_Dialog.haigoto.element.findP("kbn_hyoji").prop("checked")) {
            filters.radio_kirikae = 1;
        }

        return filters;
    };

    /**
     * ダイアログの検索条件を組み立てます
     */
    _705_HaigoCopy_Dialog.header.createFilterSearch = function () {
        var criteria = _705_HaigoCopy_Dialog.header.element.form().data(),
            criteriaTo = _705_HaigoCopy_Dialog.haigoto.element.form().data(),
            filters = {};

        //TODO: 画面で設定された検索条件を取得し、データ取得サービスのフィルターオプションを組み立てます。
        if (!App.isUndefOrNullOrStrEmpty(_705_HaigoCopy_Dialog.values.M_kirikae)) {
            filters.m_kirikae = _705_HaigoCopy_Dialog.values.M_kirikae;
        }

        if (!App.isUndefOrNullOrStrEmpty(_705_HaigoCopy_Dialog.values.cd_kaisha)) {
            filters.cd_kaisha = _705_HaigoCopy_Dialog.values.cd_kaisha;
        }

        if (!App.isUndefOrNullOrStrEmpty(criteriaTo.cd_kojyo)) {
            filters.cd_kojyo = criteriaTo.cd_kojyo;
        }

        if (!App.isUndefOrNullOrStrEmpty(criteriaTo.cd_genryo)) {
            filters.cd_haigo = criteriaTo.cd_genryo;
        }

        if (!App.isUndefOrNullOrStrEmpty(criteria.no_han)) {
            filters.no_han = criteria.no_han;
        }

        if (_705_HaigoCopy_Dialog.haigoto.element.findP("kbn_seizo").prop("checked")) {
            filters.radio_kirikae = 2;
        }
        if (_705_HaigoCopy_Dialog.haigoto.element.findP("kbn_hyoji").prop("checked")) {
            filters.radio_kirikae = 1;
        }

        return filters;
    };

    /**
     * Parameter HAIGO FROM Save
     */
    _705_HaigoCopy_Dialog.filterFromSave = function () {
        var paraSave = {};
        var criteria = _705_HaigoCopy_Dialog.header.element.form().data();

        if (!App.isUndefOrNullOrStrEmpty(_705_HaigoCopy_Dialog.values.M_kirikae)) {
            paraSave.m_kirikae = _705_HaigoCopy_Dialog.values.M_kirikae;
        }

        if (!App.isUndefOrNullOrStrEmpty(_705_HaigoCopy_Dialog.values.cd_kaisha)) {
            paraSave.cd_kaisha_from = _705_HaigoCopy_Dialog.values.cd_kaisha;
        }

        if (!App.isUndefOrNullOrStrEmpty(_705_HaigoCopy_Dialog.values.cd_kojyo)) {
            paraSave.cd_kojyo_from = _705_HaigoCopy_Dialog.values.cd_kojyo;
        }

        if (!App.isUndefOrNullOrStrEmpty(_705_HaigoCopy_Dialog.values.cd_haigo)) {
            paraSave.cd_haigo_from = _705_HaigoCopy_Dialog.values.cd_haigo;
        }

        if (!App.isUndefOrNullOrStrEmpty(criteria.no_han)) {
            paraSave.no_han_from = criteria.no_han;
        }

        paraSave.zenban_chk = 0;
        if (_705_HaigoCopy_Dialog.header.element.findP("zenban_chk").prop("checked")) {
            paraSave.zenban_chk = 1;
        }

        paraSave.seihin_chk = 0;
        if (_705_HaigoCopy_Dialog.header.element.findP("seihin_chk").prop("checked")) {
            paraSave.seihin_chk = 1;
        }

        return paraSave;
    }

    /**
     * Parameter HAIGO TO Save
     */
    _705_HaigoCopy_Dialog.filterToSave = function () {
        var paraSave = {};
        var id = $(".haigo-to").attr("data-key"),
            entity = _705_HaigoCopy_Dialog.haigoto.data.entry(id);
        var criteriaTo = _705_HaigoCopy_Dialog.haigoto.element.form().data();

        if (_705_HaigoCopy_Dialog.haigoto.element.findP("kbn_seizo").prop("checked")) {
            paraSave.radio_kirikae = 2;
        }
        if (_705_HaigoCopy_Dialog.haigoto.element.findP("kbn_hyoji").prop("checked")) {
            paraSave.radio_kirikae = 1;
        }

        if (!App.isUndefOrNullOrStrEmpty(criteriaTo.cd_genryo)) {
            paraSave.cd_haigo_to = criteriaTo.cd_genryo;
        }

        if (!App.isUndefOrNullOrStrEmpty(criteriaTo.cd_kojyo)) {
            paraSave.cd_kojyo_to = criteriaTo.cd_kojyo;
        }

        paraSave.no_han_to = 1;
        if (!App.isUndefOrNullOrStrEmpty(criteriaTo.no_han)) {
            paraSave.no_han_to = criteriaTo.no_han;
        }

        if (!App.isUndefOrNullOrStrEmpty(criteriaTo.dt_from)) {
            paraSave.dt_from = App.data.getDateString(new Date(criteriaTo.dt_from), true);
        }

        if (!App.isUndefOrNullOrStrEmpty(criteriaTo.dt_to)) {
            paraSave.dt_to = App.data.getDateString(new Date(criteriaTo.dt_to), true);
        }

        if (!App.isUndefOrNullOrStrEmpty(criteriaTo.qty_kihon)) {
            paraSave.qty_kihon = criteriaTo.qty_kihon;
        }

        if (!App.isUndefOrNullOrStrEmpty(criteriaTo.ritsu_kihon)) {
            paraSave.ritsu_kihon = criteriaTo.ritsu_kihon;
        }

        paraSave.kbn_hin = entity.kbn_hin;
        paraSave.flg_mishiyo = entity.flg_mishiyo;
        paraSave.isDuplicate = entity.isDuplicate;
        paraSave.isError = entity.isError;

        return paraSave;
    }

    /**
     * 明細のバリデーションフィルターを設定します。（必須チェックを行わない）
     */
    _705_HaigoCopy_Dialog.haigoto.validationFilter = function (item, method, state, options) {
        return method !== "required" && method !== "isValueZero" && method !== "requiedCustom705";
    };

</script>

<div class="modal fade wide" tabindex="-1" id="_705_HaigoCopy_Dialog">
    <div class="modal-dialog" style="max-height: 85%; width: 55%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">配合データコピー</h4>
            </div>

            <div class="modal-body">
                <div class="modal-wrap">
                    <div class="header">
                        <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
                        <div class="part haigo-from" style="padding: 10px;">
                            <div class="row">
                                <div class="control-label col-xs-2">
                                    <label>配合コード</label>
                                </div>
                                <div class="control col-xs-4">
                                    <label data-prop="cd_haigo"></label>
                                </div>
                                <div class="control-label col-xs-2">
                                    <label>配合名</label>
                                </div>
                                <div class="control col-xs-4">
                                    <label class="overflow-ellipsis" data-prop="nm_haigo"></label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="control-label col-xs-2">
                                    <label>版番号</label>
                                </div>
                                <div class="control col-xs-2">
                                    <select data-prop="no_han" style="width: 50%"></select>
                                </div>
                                <div class="control col-xs-2">
                                    <label>
                                        <input type="checkbox" data-prop="zenban_chk" />
                                        <i>全版対象</i>
                                    </label>
                                </div>
                                <div class="control col-xs-6">
                                    <label class="kbnhin3" style="display: none;">
                                        <input type="checkbox" class="copy" data-prop="seihin_chk" />
                                        <i>製品情報もコピー</i>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="part haigo-to" style="padding: 10px;">
                            <div class="row">
                                <div class="control col-xs-2">
                                    <label>
                                        <input type="radio" class="haigo" checked="checked" data-prop="kbn_seizo" name="haigo" />
                                        <i>製造用配合</i>
                                    </label>
                                </div>
                                <div class="control col-xs-2">
                                    <label>
                                        <input type="radio" class="haigo" data-prop="kbn_hyoji" name="haigo" />
                                        <i>表示用配合</i>
                                    </label>
                                </div>
                                <div class="control col-xs-8">
                                    <label style="white-space: nowrap;"></label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="control-label col-xs-2">
                                    <label>工場<span class="red-color">*</span></label>
                                </div>
                                <div class="control col-xs-10">
                                    <input type="tel" style="width: 80px; ime-mode: disabled;" data-prop="cd_kojyo" class="hyoji limit-input-digit" maxlength="4" />
                                    <label data-prop="cd_kojyo_validate" style="display: none;"></label>
                                    <select style="width: 250px;" data-prop="nm_kojyo" class="hyoji"></select>
                                </div>
                            </div>
                            <div class="row">
                                <div class="control-label col-xs-2">
                                    <label>配合コード<span class="red-color">*</span></label>
                                </div>
                                <div class="control col-xs-2">
                                    <input type="tel" data-prop="cd_genryo" class="limit-input-digit" style="ime-mode: disabled;" />
                                </div>
                                <div class="control col-xs-8">
                                    <button class="btn btn-info btn-xs haigo-kensaku">検索</button>
                                    <label class="caution" data-prop="info_to"></label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="control-label col-xs-2">
                                    <label>配合名</label>
                                </div>
                                <div class="control col-xs-10">
                                    <label class="overflow-ellipsis" data-prop="nm_genryo"></label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="control-label col-xs-2">
                                    <label>版番号<span class="red-color">*</span></label>
                                </div>
                                <div class="control col-xs-10">
                                    <select data-prop="no_han" style="width: 80px;"></select>
                                </div>
                            </div>
                            <div class="row">
                                <div class="control-label col-xs-2">
                                    <label>有効期限<span class="red-color">*</span></label>
                                </div>
                                <div class="control col-xs-10">
                                    <input type="text" data-role="date" style="width: 100px;" data-prop="dt_from" class="allselect" />
                                    <span style="width: 70px;">～</span>
                                    <input type="text" data-role="date" style="width: 100px;" data-prop="dt_to" class="allselect" />
                                </div>
                            </div>
                            <div class="row">
                                <div class="control-label col-xs-2">
                                    <label>基本重量<span class="red-color">*</span></label>
                                </div>
                                <div class="control col-xs-10">
                                    <input type="tel" class="text-right limit-input-digit comma-number" format-number="#,#" style="width: 100px" data-prop="qty_kihon" maxlength="8" />
                                </div>
                            </div>
                            <div class="row">
                                <div class="control-label col-xs-2">
                                    <label>倍率<span class="red-color">*</span></label>
                                </div>
                                <div class="control col-xs-1">
                                    <input type="tel" class="text-right limit-input-float-positive" format-number="#,#.00#####" data-prop="ritsu_kihon" maxlength="5" />
                                </div>
                                <div class="control col-xs-9">
                                    <label class="caution">※スケールを変更してコピーできます。</label>
                                </div>
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
                <button type="button" class="btn btn-sm btn-primary save" name="save">保存</button>
                <button type="button" class="clear btn btn-sm btn-primary float-left">クリア</button>
            </div>

        </div>
    </div>
</div>
