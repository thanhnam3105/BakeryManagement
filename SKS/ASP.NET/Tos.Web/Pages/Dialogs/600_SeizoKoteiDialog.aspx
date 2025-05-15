<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="600_SeizoKoteiDialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs.SeizoKoteiDialog" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchInputDialog(Ver2.1)】 Template--%>
<style type="text/css">
    #SeizoKoteiDialog .required {
        color: red;
    }

    #SeizoKoteiDialog label {
        padding: 2px!important;
    }
</style>

<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var SeizoKoteiDialog = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.maxInputDataCount,   // TODO:取得するデータ数を指定します。
            filter: ""
        },
        values: {
            isChangeRunning: {}
        },
        urls: {},
        header: {
            options: {},
            values: {
                modeDisp: App.settings.app.m_shisaku_data.shinki,
                seq_shisaku: null,
                no_chui: null,
                entity: {},
                shisakuHin: {},
                dataChui: [],
                isComplete:undefined
            },
        },
        commands: {},
        dialogs: {}
    };

    /**
     * バリデーション成功時の処理を実行します。
     */
    SeizoKoteiDialog.validationSuccess = function (results, state) {
        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                //SeizoKoteiDialog.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                //    if (row && row.element) {
                //        row.element.find("tr").removeClass("has-error");
                //    }
                //});
            } else {
                SeizoKoteiDialog.setColValidStyle(item.element);
            }

            SeizoKoteiDialog.notifyAlert.remove(item.element);
        }
    };

    /**
     * バリデーション失敗時の処理を実行します。
     */
    SeizoKoteiDialog.validationFail = function (results, state) {

        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                //SeizoKoteiDialog.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                //    if (row && row.element) {
                //        row.element.find("tr").addClass("has-error");
                //    }
                //});
            } else {
                SeizoKoteiDialog.setColInvalidStyle(item.element);
            }

            if (state && state.suppressMessage) {
                continue;
            }
            SeizoKoteiDialog.notifyAlert.message(item.message, item.element).show();
        }
    };

    /**
     * バリデーション後の処理を実行します。
     */
    SeizoKoteiDialog.validationAlways = function (results) {
        //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
    };

    /**
      * 指定された定義をもとにバリデータを作成します。
      * @param target バリデーション定義
      * @param options オプションに設定する値。指定されていない場合は、
      *                画面の success/fail/always のハンドル処理が指定されたオプションが設定されます。
      */
    SeizoKoteiDialog.createValidator = function (target, options) {
        return App.validation(target, options || {
            success: SeizoKoteiDialog.validationSuccess,
            fail: SeizoKoteiDialog.validationFail,
            always: SeizoKoteiDialog.validationAlways
        });
    };

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    SeizoKoteiDialog.setColInvalidStyle = function (target) {
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
    SeizoKoteiDialog.setColValidStyle = function (target) {
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
     * データの新規処理を実行します。
     */
    SeizoKoteiDialog.commands.koshinChui = function (mode) {

        var element = SeizoKoteiDialog.element;

        SeizoKoteiDialog.notifyAlert.clear();
        SeizoKoteiDialog.notifyInfo.clear();

        SeizoKoteiDialog.header.values.isAddNew = true;
        //形チェック表示内容	シングルクォーテーションが入力できないこと	
        SeizoKoteiDialog.header.options.validations.chuijiko.rules["check_single_kotations"] = true;
        SeizoKoteiDialog.header.options.validations.chuijiko.messages["check_single_kotations"] = App.str.format(App.messages.app.AP0153, { name: mode == "addNew" ? "注意事項" : "試作メモ" });
        SeizoKoteiDialog.header.validator = element.validation(SeizoKoteiDialog.createValidator(SeizoKoteiDialog.header.options.validations));

        SeizoKoteiDialog.validateAll().then(function () {
            App.ui.loading.show();

            var data = element.form().data();

            data["mode"] = mode;
            data["no_chui"] = SeizoKoteiDialog.header.values.no_chui;

            if (page.dialogs.SeizoKoteiDialog.header.values.isComplete == undefined) {
                App.ui.page.notifyInfo.message("undefined").show();
                App.ui.loading.close();
                return;
            }

            if (App.isFunc(SeizoKoteiDialog.dataSelected)) {
                page.dialogs.SeizoKoteiDialog.header.values.isComplete = undefined;
                SeizoKoteiDialog.dataSelected(data);
            } else {
                App.ui.loading.close();
            }
        }).always(function () {
            SeizoKoteiDialog.header.values.isAddNew = undefined;
        })
    }

    SeizoKoteiDialog.afterKoshin = function () {
        var element = SeizoKoteiDialog.element,
            data = element.form().data();

        if (data.cb_chui == App.settings.app.flg_chui.chuijiko_hyoji) {
            SeizoKoteiDialog.controlNoChui(element);
        }
    }

    /**
     * すべてのバリデーションを実行します。
     */
    SeizoKoteiDialog.validateAll = function () {

        var validations = [];


        validations.push(SeizoKoteiDialog.header.validator.validate());

        //validations.push(SeizoKoteiDialog.detail.validateList());

        //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。

        return App.async.all(validations);
    };

    /**
     * ダイアログの初期化処理を行います。
     */
    SeizoKoteiDialog.initialize = function () {

        var element = $("#SeizoKoteiDialog"),
            contentHeight = $(window).height() * 80 / 100;

        SeizoKoteiDialog.element = element;
        element.find(".modal-body").css("max-height", contentHeight);
        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        SeizoKoteiDialog.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#SeizoKoteiDialog .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body .detail",
                 show: function () {
                     element.find(".info-message").show();

                     SeizoKoteiDialog.resizeHeightContent();
                     var myVar = setInterval(SeizoKoteiDialog.resizeHeightContent, 500);

                     setTimeout(function () {
                         clearInterval(myVar);
                     }, 4000)
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                     SeizoKoteiDialog.resizeHeightContent();
                 }
             });
        SeizoKoteiDialog.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#SeizoKoteiDialog .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body .detail",
                show: function () {
                    element.find(".alert-message").show();
                    SeizoKoteiDialog.resizeHeightContent();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                    SeizoKoteiDialog.resizeHeightContent();
                }
            });

        element.find(".modal-dialog").draggable({
            drag: true,
        });

        SeizoKoteiDialog.initializeControlEvent();
        SeizoKoteiDialog.header.initialize();
        SeizoKoteiDialog.loadMasterData();
        SeizoKoteiDialog.loadDialogs();
    };

    /**
     * メッセンジャーの表示時に高さテキストエリアを自動サイズ変更する。
     */
    SeizoKoteiDialog.resizeHeightContent = function () {
        SeizoKoteiDialog.element.findP("chuijiko").css("height", (330 - SeizoKoteiDialog.element.find(".message-area").outerHeight()) + "px");
    }

    /**
     * ダイアログコントロールへのイベントの紐づけを行います。
     */
    SeizoKoteiDialog.initializeControlEvent = function () {
        var element = SeizoKoteiDialog.element;

        //TODO: 画面全体で利用するコントロールのイベントの紐づけ処理をここに記述します。
        element.on("hidden.bs.modal", SeizoKoteiDialog.hidden);
        element.on("shown.bs.modal", SeizoKoteiDialog.shown);

        element.on("click", "#add_new_chui", function () { SeizoKoteiDialog.commands.koshinChui("addNew"); });
        element.on("click", "#update_chui", function () {

            SeizoKoteiDialog.notifyInfo.clear();

            var data = element.form().data();
            if (data.cb_chui == App.settings.app.flg_chui.chuijiko_hyoji && !SeizoKoteiDialog.header.values.no_chui) {
                SeizoKoteiDialog.notifyInfo.message(App.str.format(App.messages.app.AP0028, { strName: "工程版" })).show();
                return;
            }

            SeizoKoteiDialog.commands.koshinChui("update");
        });
    };

    /**
     * ダイアログ非表示時処理を実行します。
     */
    SeizoKoteiDialog.hidden = function (e) {

        SeizoKoteiDialog.header.values.isComplete = undefined;

        var element = SeizoKoteiDialog.element;

        //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
        element.find(":input:not([type='checkbox']):not([type='radio'])").val("");
        element.find("input[type='checkbox']").prop("checked", false);
        //TODO:radioボタンの初期表示は、画面要件に合わせて記述します。

        SeizoKoteiDialog.setColValidStyle(element.findP("chuijiko"));
        SeizoKoteiDialog.notifyInfo.clear();
        SeizoKoteiDialog.notifyAlert.clear();
    };

    /**
     * ダイアログ表示時処理を実行します。
     */
    SeizoKoteiDialog.shown = function (e) {

        SeizoKoteiDialog.values.flg_chui_org = undefined;

        var element = SeizoKoteiDialog.element;
        SeizoKoteiDialog.controlComboChui(element);
        SeizoKoteiDialog.controlInputChui(element);
        SeizoKoteiDialog.controlCheckChui(element);
        SeizoKoteiDialog.controlNoChui(element);
        SeizoKoteiDialog.controlKomoku();
    };


    SeizoKoteiDialog.searchDataChui = function () {
        var dataGenryoKojo = jQuery.grep(SeizoKoteiDialog.header.values.dataChui, function (n, i) {
            return (n.no_chui == SeizoKoteiDialog.header.values.no_chui);
        });

        if (dataGenryoKojo.length) {
            return !dataGenryoKojo[0]["chuijiko"] ? "" : dataGenryoKojo[0]["chuijiko"];
        } else {
            return "";
        }
    }

    SeizoKoteiDialog.controlNoChui = function (element, isHiden) {
        var data = element.form().data();

        //■「試作メモ」が選択されている場合
        //■「製法No」が選択されている場合
        if (data.cb_chui != App.settings.app.flg_chui.chuijiko_hyoji) {
            element.find(".no_chui").hide();
            return;
        }

        element.find(".no_chui").show();
        if (SeizoKoteiDialog.header.values.no_chui) {
            element.findP("chui_bango").removeClass("required").text(SeizoKoteiDialog.header.values.no_chui);
        } else {
            element.findP("chui_bango").addClass("required").text("未選択です");
        }
    }

    SeizoKoteiDialog.controlCheckChui = function (element) {
        var data = element.form().data();

        if (data["cb_chui"] == App.settings.app.flg_chui.other || data["cb_chui"] == null || data["cb_chui"] == undefined) {
            element.findP("flg_chui").prop("checked", false);
            return;
        }
        element.findP("flg_chui").prop("checked", SeizoKoteiDialog.header.values.shisakuHin["flg_chui"] == element.findP("cb_chui").val());
    }

    SeizoKoteiDialog.controlInputChui = function (element) {
        var data = element.form().data();
        switch (data.cb_chui) {
            case App.settings.app.flg_chui.other:

                var chuijiko = [];

                chuijiko.push(SeizoKoteiDialog.header.values.entity["no_seiho1"]);
                chuijiko.push(SeizoKoteiDialog.header.values.entity["no_seiho2"]);
                chuijiko.push(SeizoKoteiDialog.header.values.entity["no_seiho3"]);
                chuijiko.push(SeizoKoteiDialog.header.values.entity["no_seiho4"]);
                chuijiko.push(SeizoKoteiDialog.header.values.entity["no_seiho5"]);

                chuijiko = jQuery.grep(chuijiko, function (n, i) {
                    return (n != null && n != "")
                });

                element.findP("chuijiko").val(chuijiko.join("\n"));

                break;
            case App.settings.app.flg_chui.chuijiko_hyoji:

                element.findP("chuijiko").val(SeizoKoteiDialog.searchDataChui());

                break;
            case App.settings.app.flg_chui.shisamemo_hyoji:

                element.findP("chuijiko").val(SeizoKoteiDialog.header.values.shisakuHin["memo_shisaku"]);

                break;
            default:
                element.findP("chuijiko").val("");
                break
        }
    }

    SeizoKoteiDialog.controlComboChui = function (element) {
        switch (SeizoKoteiDialog.header.values.shisakuHin["flg_chui"]) {
            case App.settings.app.flg_chui.shisamemo_hyoji:
                element.findP("cb_chui").val(App.settings.app.flg_chui.shisamemo_hyoji);
                break;
            default:
                element.findP("cb_chui").val(App.settings.app.flg_chui.chuijiko_hyoji);
                break
        }
    }

    /**
     * マスターデータのロード処理を実行します。
     */
    SeizoKoteiDialog.loadMasterData = function () {

        //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
        //return $.ajax(App.ajax.odata.get(/* マスターデータ取得サービスの URL */)).then(function (result) {
        //});

        var cb_chui = SeizoKoteiDialog.element.findP("cb_chui");
        cb_chui.children().remove();
        App.ui.appendOptions(
            cb_chui,
            "value",
            "name",
            App.settings.app.cb_chui
        );

        //TODO: マスタデータのロード処理を実装後、下の1行を削除してください。
        return App.async.success();
    };

    /**
     * 共有のダイアログのロード処理を実行します。
     */
    SeizoKoteiDialog.loadDialogs = function () {

        if ($.find("#ConfirmDialog").length == 0) {
            return App.async.all({
                confirmDialog: $.get(SeizoKoteiDialog.urls.confirmDialog)
            }).then(function (result) {
                $("#dialog-container").append(result.successes.confirmDialog);
                SeizoKoteiDialog.dialogs.confirmDialog = ConfirmDialog;
            });
        } else {
            SeizoKoteiDialog.dialogs.confirmDialog = ConfirmDialog;
        }
    };

    /**
     * ダイアログヘッダー　バリデーションルールを定義します。
     */
    SeizoKoteiDialog.header.options.validations = {
        chuijiko: {
            rules: {
                checkRequired: function (value, param, otps, done) {
                    if (SeizoKoteiDialog.header.values.isAddNew) {
                        var element = SeizoKoteiDialog.element,
                            data = element.form().data();

                        //試作メモ表示
                        //上記以外
                        if (data.cb_chui != App.settings.app.flg_chui.chuijiko_hyoji) {
                            return done(true);
                        }

                        //check null
                        var isEmpty = function (val) {
                            return App.isUndef(val) || App.isNull(val) || (App.isStr(val) && val.length === 0);
                        }

                        if (isEmpty(value)) {
                            return done(false);
                        }

                        return done(true);
                    } else {

                        return done(true);
                    }
                },
                check_single_kotations: true,
                maxlength: 2000
            },
            options: {
                name: "表示内容"
            },
            messages: {
                checkRequired: App.messages.app.AP0082,
                check_single_kotations: App.messages.app.AP0153,
                maxlength: App.messages.base.maxlength
            }
        }
    };

    /**
     * ダイアログヘッダーの初期化処理を行います。
     */
    SeizoKoteiDialog.header.initialize = function () {

        var element = SeizoKoteiDialog.element;
        SeizoKoteiDialog.header.validator = element.validation(SeizoKoteiDialog.createValidator(SeizoKoteiDialog.header.options.validations));
        SeizoKoteiDialog.header.element = element;

        //TODO: 画面ヘッダーの初期化処理をここに記述します。
        //TODO: 画面ヘッダーで利用するコントロールのイベントの紐づけ処理をここに記述します。
        element.on("change", ":input", SeizoKoteiDialog.header.change);

    };

    /**
     * 明細の入力項目の変更イベントの処理を行います。
     */
    SeizoKoteiDialog.header.change = function (e) {
        var element = SeizoKoteiDialog.element,
            target = $(e.target),
            property = target.attr("data-prop"),
            data = element.form().data();

        delete SeizoKoteiDialog.header.options.validations.chuijiko.rules["check_single_kotations"];
        SeizoKoteiDialog.header.validator = element.validation(SeizoKoteiDialog.createValidator(SeizoKoteiDialog.header.options.validations));

        element.validation().validate({
            targets: target
        }).then(function () {
            if (property == "cb_chui") {
                SeizoKoteiDialog.controlInputChui(element);
                SeizoKoteiDialog.controlCheckChui(element);
                SeizoKoteiDialog.controlNoChui(element);
                SeizoKoteiDialog.controlKomoku();

                element.validation().validate({
                    targets: element.findP("chuijiko")
                })
            }

            //常に表示
            if (property == "flg_chui") {
                SeizoKoteiDialog.values.flg_chui_org = target[0].checked;
            }
        })
    };

    /**
     * 
     */
    SeizoKoteiDialog.controlKomoku = function () {
        var element = SeizoKoteiDialog.element,
            data = element.form().data();

        element.find("input").prop("disabled", false);
        element.find("input[type='checkbox']").prop('disabled', false);
        element.find("select").prop('disabled', false);
        element.find("button").prop('disabled', false);
        element.find("textarea").prop('disabled', false);

        //■製法コピーモード、閲覧モード時
        if (SeizoKoteiDialog.header.values.modeDisp == App.settings.app.m_shisaku_data.copy
            || SeizoKoteiDialog.header.values.modeDisp == App.settings.app.m_shisaku_data.etsuran) {
            element.find(".copy_red_mode").prop('disabled', true);
            return;
        }

        switch (data.cb_chui) {
            case App.settings.app.flg_chui.shisamemo_hyoji://■「試作メモ」が選択されている場合
                //新規
                element.find("#add_new_chui").prop('disabled', true);
                break;
            case App.settings.app.flg_chui.other://■「製法No」が選択されている場合
                element.find("#add_new_chui, #update_chui").prop('disabled', true);
                element.findP("flg_chui").prop('checked', false).prop('disabled', true);
                element.find("textarea").prop('disabled', true);
                //新規, 更新
                break;
            default:
                break;
        }
    }

</script>

<div class="modal fade wide" tabindex="-1" id="SeizoKoteiDialog">
    <div class="modal-dialog" style="height: 350px; width: 500px">
        <div class="modal-content" style="height: 591px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">製造工程</h4>
            </div>

            <div class="modal-body">
                <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
                <div class="row">
                    <div class="col-xs-12 control">
                        <select data-prop="cb_chui" class="number" style="width: 99.5%"></select>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12 control free-height">
                        <textarea rows="20" data-prop="chuijiko" style="resize: none; width: 99.5%; height: 330px; overflow-y: auto; float: left" class="copy_red_mode"></textarea>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12 control">
                        <label class="float-right">常に表示<input type="checkbox" data-prop="flg_chui" style="margin-left: 10px" /></label>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12 control no_chui">
                        <label class="float-right">工程版：<label class="float-right" data-prop="chui_bango"></label></label>
                        <%--<span style="margin-left: 5px" class="float-right">工程版：<span class="float-right" data-prop="chui_bango"></span></span>--%>
                    </div>
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
                <button type="button" class="cancel-button btn btn-sm btn-primary copy_red_mode" id="add_new_chui">新規</button>
                <button type="button" class="cancel-button btn btn-sm btn-primary copy_red_mode" id="update_chui">更新</button>
            </div>

        </div>
    </div>
</div>
