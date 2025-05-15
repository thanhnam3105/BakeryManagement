<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="405_CopyMotoSentakuDialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs.CopyMotoSentakuDialog" %>
<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>

    <script type="text/javascript">

        /**
         * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
         */
        var CopyMotoSentakuDialog = {
            options: {
                cd_shain: "",
                nen: "",
                no_oi: "",
                id: ""
            },
            urls: {
                getShisakuhin: "../Services/ShisaQuickService.svc/tr_shisakuhin?$filter=cd_shain eq {0}M and nen eq {1}M and no_oi eq {2}M",
                getGateMeisai: "../Services/ShisaQuickService.svc/tr_gate_meisai?$filter=cd_shain eq {0}M and nen eq {1}M and no_oi eq {2}M",
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
                SekkeiHinshutsuGeto: "../Pages/400_SekkeiHinshutsuGeto.aspx?mode={0}&cd_shain={1}&nen={2}&no_oi={3}&nm_hin={4}",
                copy: "../api/CopyMotoSentaku_Dialog",
                checkExistsOpenNoSeiho: "../api/SekkeiHinshutsuGeto/UpdateOpening"
            },
            dialogs: {}
        };

        /**
         * 検索ダイアログの初期化処理を行います。
         */
        CopyMotoSentakuDialog.initialize = function () {
            var element = $("#CopyMotoSentakuDialog");

            element.on("hidden.bs.modal", CopyMotoSentakuDialog.hidden);
            element.on("shown.bs.modal", CopyMotoSentakuDialog.shown);
            element.on("click", "button[name='copy']", CopyMotoSentakuDialog.copy);
            element.findP("cd_shain").complete({
                textLength: 10,
                ajax: function (val) {
                    element.findP("cd_shain").val(App.common.getFullString(val, "0000000000"));
                    if (val == "" || element.findP("nen").val() == "" || element.findP("no_oi").val() == "") {
                        return App.async.success();
                    }
                    var loadingTaget = CopyMotoSentakuDialog.element.find(".modal-content");
                    App.ui.loading.show("",loadingTaget);
                    var cd_shain = parseFloat(val),
                        nen = parseFloat(element.findP("nen").val()),
                        no_oi = parseFloat(element.findP("no_oi").val());
                    return $.ajax(App.ajax.odata.get(App.str.format(CopyMotoSentakuDialog.urls.getShisakuhin, cd_shain, nen, no_oi)))
                },
                success: CopyMotoSentakuDialog.setShisakuhin,
                error: CopyMotoSentakuDialog.FailShisakuhin,
                clear: CopyMotoSentakuDialog.clearShisakuhin
            });
            element.findP("nen").complete({
                textLength: 2,
                ajax: function (val) {
                    element.findP("nen").val(App.common.getFullString(val, "00"));
                    if (val == "" || element.findP("cd_shain").val() == "" || element.findP("no_oi").val() == "") {
                        return App.async.success();
                    }
                    var loadingTaget = CopyMotoSentakuDialog.element.find(".modal-content");
                    App.ui.loading.show("",loadingTaget);
                    var cd_shain = parseFloat(element.findP("cd_shain").val()),
                        nen = parseFloat(val),
                        no_oi = parseFloat(element.findP("no_oi").val());
                    return $.ajax(App.ajax.odata.get(App.str.format(CopyMotoSentakuDialog.urls.getShisakuhin, cd_shain, nen, no_oi)))
                },
                success: CopyMotoSentakuDialog.setShisakuhin,
                error: CopyMotoSentakuDialog.FailShisakuhin,
                clear: CopyMotoSentakuDialog.clearShisakuhin
            });
            element.findP("no_oi").complete({
                textLength: 3,
                ajax: function (val) {
                    element.findP("no_oi").val(App.common.getFullString(val, "000"));
                    if (val == "" || element.findP("cd_shain").val() == "" || element.findP("nen").val() == "") {
                        return App.async.success();
                    }
                    var loadingTaget = CopyMotoSentakuDialog.element.find(".modal-content");
                    App.ui.loading.show("",loadingTaget);
                    var cd_shain = parseFloat(element.findP("cd_shain").val()),
                        nen = parseFloat(element.findP("nen").val()),
                        no_oi = parseFloat(val);
                    return $.ajax(App.ajax.odata.get(App.str.format(CopyMotoSentakuDialog.urls.getShisakuhin, cd_shain, nen, no_oi)))
                },
                success: CopyMotoSentakuDialog.setShisakuhin,
                error: CopyMotoSentakuDialog.FailShisakuhin,
                clear: CopyMotoSentakuDialog.clearShisakuhin
            });
            CopyMotoSentakuDialog.element = element;

            element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
            element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

            CopyMotoSentakuDialog.notifyInfo =
                 App.ui.notify.info(element, {
                     container: "#CopyMotoSentakuDialog .dialog-slideup-area .info-message",
                     bodyContainer: ".modal-body",
                     show: function () {
                         element.find(".info-message").show();
                     },
                     clear: function () {
                         element.find(".info-message").hide();
                     }
                 });
            CopyMotoSentakuDialog.notifyAlert =
                App.ui.notify.alert(element, {
                    container: "#CopyMotoSentakuDialog .dialog-slideup-area .alert-message",
                    bodyContainer: ".modal-body",
                    show: function () {
                        element.find(".alert-message").show();
                    },
                    clear: function () {
                        element.find(".alert-message").hide();
                    }
                });

            CopyMotoSentakuDialog.validator = element.validation(App.validation(CopyMotoSentakuDialog.options.validations, {
                success: function (results, state) {
                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        CopyMotoSentakuDialog.setColValidStyle(item.element);

                        CopyMotoSentakuDialog.notifyAlert.remove(item.element);
                    }
                },
                fail: function (results, state) {
                    var i = 0, l = results.length,
                        item, $target;

                    for (; i < l; i++) {
                        item = results[i];
                        CopyMotoSentakuDialog.setColInvalidStyle(item.element);
                        if (state && state.suppressMessage) {
                            continue;
                        }

                        CopyMotoSentakuDialog.notifyAlert.message(item.message, item.element).show();
                    }
                },
                always: function (results) {
                    //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
                }
            }));

            element.find(".modal-dialog").draggable({
                drag: true,
            });

            CopyMotoSentakuDialog.loadDialogs();
        };

        /**
         * 単項目要素をエラーのスタイルに設定します。
         * @param target 設定する要素
         */
        CopyMotoSentakuDialog.setColInvalidStyle = function (target) {
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
        CopyMotoSentakuDialog.setColValidStyle = function (target) {
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
        CopyMotoSentakuDialog.hidden = function (e) {
            var element = CopyMotoSentakuDialog.element;

            element.find(":input").val("").data("lastVal","");
            element.findP("nm_hin").text("");
            page.detail.values.paramHaita.cd_shain = "";
            CopyMotoSentakuDialog.element.find(":input").prop("disabled", false);

            var items = element.find(".search-criteria :input:not(button)");
            for (var i = 0; i < items.length; i++) {
                var item = items[i];
                CopyMotoSentakuDialog.setColValidStyle(item);
            }

            CopyMotoSentakuDialog.notifyInfo.clear();
            CopyMotoSentakuDialog.notifyAlert.clear();
        };

        /**
         * 検索ダイアログ表示時処理を実行します。
         */
        CopyMotoSentakuDialog.shown = function (e) {
            CopyMotoSentakuDialog.element.find(":input:not(button):first").focus();
        };

        /**
         * 検索ダイアログ　バリデーションルールを定義します。
         */
        CopyMotoSentakuDialog.options.validations = {
            cd_shain: {
                rules: {
                    required: true,
                    maxlength: 10,
                    digits: true,
                    checkExists: function (value, opts, state, done) {
                        var element = CopyMotoSentakuDialog.element,
                            cd_shain = parseFloat(element.findP("cd_shain").val()),
                            nen = parseFloat(element.findP("nen").val()),
                            no_oi = parseFloat(element.findP("no_oi").val());
                        if (isNaN(cd_shain) || isNaN(nen) || isNaN(no_oi) || element.findP("cd_shain").val().length > 10 || element.findP("nen").val().lenght > 2 || element.findP("no_oi").val().length > 3) {
                            return done(true);
                        } else {
                            return done(CopyMotoSentakuDialog.element.findP("nm_hin").prop("existsData"));
                        }
                    },
                    checkSameNoSeiho: function (value, opts, state, done) {
                        var element = CopyMotoSentakuDialog.element,
                            cd_shain = parseFloat(element.findP("cd_shain").val()),
                            nen = parseFloat(element.findP("nen").val()),
                            no_oi = parseFloat(element.findP("no_oi").val());
                        if (isNaN(cd_shain) || isNaN(nen) || isNaN(no_oi)) {
                            return done(true);
                        } else {
                            if (cd_shain == parseFloat(CopyMotoSentakuDialog.options.cd_shain)
                                && nen == parseFloat(CopyMotoSentakuDialog.options.nen)
                                && no_oi == parseFloat(CopyMotoSentakuDialog.options.no_oi)) {
                                return done(false);
                            }
                        }
                        return done(true);
                    }
                },
                options: {
                    name: "試作No（社員コード）"
                },
                messages: {
                    required: App.messages.base.required,
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits,
                    checkExists: App.messages.app.AP0007,
                    checkSameNoSeiho: App.messages.app.AP0177,
                }
            },
            nen: {
                rules: {
                    required: true,
                    maxlength: 2,
                    digits: true
                },
                options: {
                    name: "試作No（年）"
                },
                messages: {
                    required: App.messages.base.required,
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits
                }
            },
            no_oi: {
                rules: {
                    required: true,
                    maxlength: 3,
                    digits: true
                },
                options: {
                    name: "試作No（追番）"
                },
                messages: {
                    required: App.messages.base.required,
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits
                }
            }
        };

        /**
         * 共有のダイアログのロード処理を実行します。
         */
        CopyMotoSentakuDialog.loadDialogs = function () {

            if ($.find("#ConfirmDialog").length == 0) {
                return App.async.all({
                    confirmDialog: $.get(CopyMotoSentakuDialog.urls.confirmDialog)
                }).then(function (result) {
                    $("#dialog-container").append(result.successes.confirmDialog);
                    CopyMotoSentakuDialog.dialogs.confirmDialog = ConfirmDialog;
                });
            } else {
                CopyMotoSentakuDialog.dialogs.confirmDialog = ConfirmDialog;
            }
        };

        /**
        * copy data
        */
        CopyMotoSentakuDialog.copy = function () {
           
            CopyMotoSentakuDialog.validator.validate({
            }).done(function () {
                App.ui.loading.show("", CopyMotoSentakuDialog.element.find(".modal-content"));
                CopyMotoSentakuDialog.dialogs.confirmDialog.confirm({
                    text: App.messages.app.AP0182,
                    multiModal: true,
                    backdrop: "static",
                    keyboard: false
                }).then(function () {
                    CopyMotoSentakuDialog.processedCopy();
                }).fail(function () {
                    CopyMotoSentakuDialog.element.find(":input:not(button):first").focus();
                    App.ui.loading.close(CopyMotoSentakuDialog.element.find(".modal-content"));
                });
            });
        };

        /**
        * processed copy data
        */
        CopyMotoSentakuDialog.processedCopy = function () {
            var cd_shain = parseFloat(CopyMotoSentakuDialog.element.findP("cd_shain").val()),
                nen = parseFloat(CopyMotoSentakuDialog.element.findP("nen").val()),
                no_oi = parseFloat(CopyMotoSentakuDialog.element.findP("no_oi").val());
                //check data destination if have data then show dialog confrim msg AP0176(yes ->processed copy data, no -> go to page 試作データ一覧(100_ShishakuIchiran.aspx)) 
                //                       else processed copy data and go to page 設計品質ゲート(400_SekkeiHinshutsuGeto.aspx)
                $.ajax(App.ajax.odata.get(App.str.format(CopyMotoSentakuDialog.urls.getGateMeisai, parseFloat(CopyMotoSentakuDialog.options.cd_shain), parseFloat(CopyMotoSentakuDialog.options.nen), parseFloat(CopyMotoSentakuDialog.options.no_oi))))
                .then(function (result) {
                    var changeSets = {
                        noSeihoSource: {
                            cd_shain: parseFloat(CopyMotoSentakuDialog.element.findP("cd_shain").val()),
                            nen: parseFloat(CopyMotoSentakuDialog.element.findP("nen").val()),
                            no_oi: parseFloat(CopyMotoSentakuDialog.element.findP("no_oi").val())
                        },
                        noSeihoDestination: {
                            cd_shain: parseFloat(CopyMotoSentakuDialog.options.cd_shain),
                            nen: parseFloat(CopyMotoSentakuDialog.options.nen),
                            no_oi: parseFloat(CopyMotoSentakuDialog.options.no_oi)
                        }
                    }
                    //have data destination
                    if (result.value.length > 0) {
                        CopyMotoSentakuDialog.dialogs.confirmDialog.confirm({
                            text: App.messages.app.AP0176,
                            multiModal: true,
                            backdrop: "static",
                            keyboard: false
                        }).then(function () {
                            //processed copy data(del + copy)
                            $.ajax(App.ajax.webapi.post(CopyMotoSentakuDialog.urls.copy, changeSets))
                            .then(function () {
                                CopyMotoSentakuDialog.dataSelected(true);
                                CopyMotoSentakuDialog.element.modal("hide");
                            }).fail(function (error) {
                                if (error.status === App.settings.base.validationErrorStatus) {
                                    var errors = error.responseJSON;
                                    $.each(errors, function (index, err) {
                                        CopyMotoSentakuDialog.notifyAlert.message(
                                            err.Message +
                                            (App.isUndefOrNull(err.InvalidationName) ? "" : err.Data[err.InvalidationName])
                                        ).show();
                                    });
                                    return;
                                }
                                CopyMotoSentakuDialog.notifyAlert.message(App.ajax.handleError(error).message).show();
                            });
                        }).fail(function () {
                            CopyMotoSentakuDialog.dataSelected(false);
                            App.ui.loading.close(CopyMotoSentakuDialog.element.find(".modal-content"));
                            CopyMotoSentakuDialog.element.modal("hide");
                        }).always(function () {
                            App.ui.loading.close(CopyMotoSentakuDialog.element.find(".modal-content"));
                        });
                    } else {
                        //processed copy data
                        $.ajax(App.ajax.webapi.post(CopyMotoSentakuDialog.urls.copy, changeSets))
                        .then(function () {
                            CopyMotoSentakuDialog.dataSelected(true);
                            CopyMotoSentakuDialog.element.modal("hide");
                        }).fail(function (error) {
                            if (error.status === App.settings.base.validationErrorStatus) {
                                var errors = error.responseJSON;
                                $.each(errors, function (index, err) {
                                    CopyMotoSentakuDialog.notifyAlert.message(
                                        err.Message +
                                        (App.isUndefOrNull(err.InvalidationName) ? "" : err.Data[err.InvalidationName])
                                    ).show();
                                });
                                return;
                            }
                            CopyMotoSentakuDialog.notifyAlert.message(App.ajax.handleError(error).message).show();
                        }).always(function () {
                            App.ui.loading.close(CopyMotoSentakuDialog.element.find(".modal-content"));
                        });
                    }
                }).fail(function(){
                    App.ui.loading.close(CopyMotoSentakuDialog.element.find(".modal-content"));
                });
        };

        /**
       * set data
       */
        CopyMotoSentakuDialog.setShisakuhin = function (data, $element) {
            var property = $element.attr("data-prop");
            if (data && data.value.length > 0) {
                CopyMotoSentakuDialog.element.findP("nm_hin").text(data.value[0].nm_hin);
                CopyMotoSentakuDialog.element.findP("nm_hin").prop("existsData", true);
                CopyMotoSentakuDialog.validator.validate({
                    targets: CopyMotoSentakuDialog.element.find("[data-prop='cd_shain'],[data-prop='nen'],[data-prop='no_oi']")
                }).always(function () {
                    var loadingTaget = CopyMotoSentakuDialog.element.find(".modal-content");
                    App.ui.loading.close(loadingTaget);
                });
            }
            else {
                CopyMotoSentakuDialog.clearShisakuhin($element);
            }
        };

        /**
        * processed fail
        **/
        CopyMotoSentakuDialog.FailShisakuhin = function (error, $element) {
            CopyMotoSentakuDialog.clearShisakuhin($element);
        };

        /**
       * clear data
       */
        CopyMotoSentakuDialog.clearShisakuhin = function ($element) {
            var element = CopyMotoSentakuDialog.element;
            var property = $element.attr("data-prop");
            if (element.findP("cd_shain").val() !== "" && element.findP("nen").val() !== "" && element.findP("no_oi").val() !== "") {
                CopyMotoSentakuDialog.element.findP("nm_hin").text("");
                CopyMotoSentakuDialog.element.findP("nm_hin").prop("existsData", false);
            }
            CopyMotoSentakuDialog.validator.validate({
                targets: CopyMotoSentakuDialog.element.find("[data-prop='cd_shain'],[data-prop='nen'],[data-prop='no_oi']"),
                filter: CopyMotoSentakuDialog.validationFilter
            }).always(function () {
                App.ui.loading.close(CopyMotoSentakuDialog.element.find(".modal-content"));
            });
        };

        /**
         * 画面明細のバリデーションフィルターを設定します。（必須チェックを行わない）
         */
        CopyMotoSentakuDialog.validationFilter = function (item, method, state, options) {
            return method !== "required";
        };

    </script>

    <div class="modal fade wide" tabindex="-1" id="CopyMotoSentakuDialog">
    <div class="modal-dialog" style="height: 220px; width: 40%; padding-top:15%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">コピー元選択ダイアログ</h4>
            </div>

            <div class="modal-body">
                <div class="search-criteria">
                    <div class="row">
                        <div class="control-label col-xs-6">
                            <label>試作No</label>
                        </div>
                        <div class="control-label col-xs-6">
                            <label> 試作名</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="control col-xs-6 with-next-col">
                            <input type="tel" class="right limit-input-int" data-prop="cd_shain" maxlength="10" style="width:90px;" />
                            <span>-</span>
                            <input type="tel" class="right limit-input-int" data-prop="nen" maxlength="2" style="width:35px;" />
                            <span>-</span>
                            <input type="tel" class="right limit-input-int" data-prop="no_oi" maxlength="3" style="width:40px;" />
                        </div>
                        <div class="control col-xs-6">
                            <label data-prop="nm_hin" class="overflow-ellipsis"></label>
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
                <button type="button" class="cancel-button btn btn-sm btn-primary" name="copy">コピー</button>
                <button type="button" class="cancel-button btn btn-sm btn-default" data-dismiss="modal" name="close">閉じる</button>
            </div>

        </div>
    </div>
    </div>