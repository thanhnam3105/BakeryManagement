<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="102_GenryoIchiranDialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs.GenryoIchiranDialog" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchInputDialog(Ver2.1)】 Template--%>
<style type="text/css">
    #GenryoIchiranDialog .search {
        height: 23px;
        margin-right: 2px;
        font-size: 12px;
        padding: 0px 20px;
        margin-top: 1px;
        margin-bottom: 0px;
    }

    #GenryoIchiranDialog .komoku_template_head,
    #GenryoIchiranDialog .komoku_template_body {
        display: none;
    }

    #GenryoIchiranDialog input[type="checkbox"],
    #GenryoIchiranDialog input[type="radio"] {
        margin-top: 8px!important;
    }

    #GenryoIchiranDialog .modal-message {
        bottom: 49px;
    }

    #GenryoIchiranDialog .new input[type='text'] {
        border: none;
        padding: 0px;
        margin: 0px;
        width: 100%;
        height: 90%;
    }

    #GenryoIchiranDialog .haishi_aru {
        background-color: #d3d3d3;
    }
</style>
<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var GenryoIchiranDialog = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.dialogDataTakeCount,   // TODO:取得するデータ数を指定します。
            filter: "",
            with_default: 1180,//全て子目
            with_col_hide_default: 550//横の子目
        },
        values: {
            isChangeRunning: {},
            dataHin: {},
            dataDefault: undefined
        },
        urls: {
            loadMasterData: "../api/_102_GenryoIchiranDialog/GetMasterData",
            search: "../api/_102_GenryoIchiranDialog/GetDetailData"
        },
        header: {
            options: {},
            values: {},
        },
        detail: {
            options: {},
            values: {}
        },
        commands: {
            urls: {
                bunsekiNyuryoku: "005_BunsekichiNyuryoku.aspx?cd_kaisha={cd_kaisha}&cd_busho={cd_busho}&cd_genryo={cd_genryo}"
            }
        },
        dialogs: {}
    };

    /**
     * バリデーション成功時の処理を実行します。
     */
    GenryoIchiranDialog.validationSuccess = function (results, state) {
        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                GenryoIchiranDialog.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                    if (row && row.element) {
                        row.element.find("tr").removeClass("has-error");
                    }
                });
            } else {
                GenryoIchiranDialog.setColValidStyle(item.element);
            }

            GenryoIchiranDialog.notifyAlert.remove(item.element);
        }
    };

    /**
     * バリデーション失敗時の処理を実行します。
     */
    GenryoIchiranDialog.validationFail = function (results, state) {

        var i = 0, l = results.length,
            item, $target;

        for (; i < l; i++) {
            item = results[i];
            if (state && state.isGridValidation) {
                GenryoIchiranDialog.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                    if (row && row.element) {
                        row.element.find("tr").addClass("has-error");
                    }
                });
            } else {
                GenryoIchiranDialog.setColInvalidStyle(item.element);
            }

            if (state && state.suppressMessage) {
                continue;
            }
            GenryoIchiranDialog.notifyAlert.message(item.message, item.element).show();
        }
    };

    /**
     * バリデーション後の処理を実行します。
     */
    GenryoIchiranDialog.validationAlways = function (results) {
        //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
    };

    /**
      * 指定された定義をもとにバリデータを作成します。
      * @param target バリデーション定義
      * @param options オプションに設定する値。指定されていない場合は、
      *                画面の success/fail/always のハンドル処理が指定されたオプションが設定されます。
      */
    GenryoIchiranDialog.createValidator = function (target, options) {
        return App.validation(target, options || {
            success: GenryoIchiranDialog.validationSuccess,
            fail: GenryoIchiranDialog.validationFail,
            always: GenryoIchiranDialog.validationAlways
        });
    };

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    GenryoIchiranDialog.setColInvalidStyle = function (target) {
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
    GenryoIchiranDialog.setColValidStyle = function (target) {
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
    //GenryoIchiranDialog.commands.save = function () {
    //    var loadingTaget = GenryoIchiranDialog.element.find(".modal-content");

    //    GenryoIchiranDialog.notifyAlert.clear();
    //    GenryoIchiranDialog.notifyInfo.clear();

    //    App.ui.loading.show("", loadingTaget);

    //    var sleep = 0;
    //    var condition = "Object.keys(GenryoIchiranDialog.values.isChangeRunning).length == 0";
    //    App.ui.wait(sleep, condition, 100)
    //    .then(function () {
    //        GenryoIchiranDialog.validateAll().then(function () {

    //            var changeSets = GenryoIchiranDialog.detail.data.getChangeSet();

    //            //TODO: データの保存処理をここに記述します。
    //            return $.ajax(App.ajax.webapi.post(/* TODO: データ保存サービスの URL を設定してください。, */changeSets))
    //                .then(function (result) {

    //                    //TODO: データの保存成功時の処理をここに記述します。


    //                    //最後に再度データを取得しなおします。
    //                    return App.async.all([GenryoIchiranDialog.header.search(false)]);
    //                }).then(function () {
    //                    GenryoIchiranDialog.notifyInfo.message(App.messages.base.MS0002).show();
    //                }).fail(function (error) {

    //                    if (error.status === App.settings.base.conflictStatus) {
    //                        // TODO: 同時実行エラー時の処理を行っています。
    //                        // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
    //                        GenryoIchiranDialog.header.search(false);
    //                        GenryoIchiranDialog.notifyAlert.clear();
    //                        GenryoIchiranDialog.notifyAlert.message(App.messages.base.MS0009).show();
    //                        return;
    //                    }

    //                    //TODO: データの保存失敗時の処理をここに記述します。
    //                    if (error.status === App.settings.base.validationErrorStatus) {
    //                        var errors = error.responseJSON;
    //                        $.each(errors, function (index, err) {
    //                            GenryoIchiranDialog.notifyAlert.message(
    //                                err.Message +
    //                                (App.isUndefOrNull(err.InvalidationName) ? "" : err.Data[err.InvalidationName])
    //                            ).show();
    //                        });
    //                        return;
    //                    }

    //                    GenryoIchiranDialog.notifyAlert.message(App.ajax.handleError(error).message).show();

    //                });
    //        });
    //    }).fail(function () {
    //        GenryoIchiranDialog.notifyAlert.message(App.messages.base.MS0006).show();
    //    }).always(function () {
    //        setTimeout(function () {
    //            GenryoIchiranDialog.header.element.find(":input:first").focus();
    //        }, 100);
    //        App.ui.loading.close(loadingTaget);
    //    });
    //};

    /**
     * すべてのバリデーションを実行します。
     */
    GenryoIchiranDialog.validateAll = function () {

        var validations = [];

        //validations.push(GenryoIchiranDialog.detail.validateList());

        //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。

        return App.async.all(validations);
    };

    /**
     * ダイアログの初期化処理を行います。
     */
    GenryoIchiranDialog.initialize = function () {

        var element = $("#GenryoIchiranDialog"),
            contentHeight = $(window).height() * 80 / 100;

        GenryoIchiranDialog.element = element;
        element.find(".modal-body").css("max-height", contentHeight);
        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        GenryoIchiranDialog.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#GenryoIchiranDialog .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body .detail",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        GenryoIchiranDialog.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#GenryoIchiranDialog .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body .detail",
                show: function () {
                    element.find(".alert-message").show();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                }
            });

        element.draggable({
            drag: true,
        });

        GenryoIchiranDialog.initializeControlEvent();
        GenryoIchiranDialog.header.initialize();
        //GenryoIchiranDialog.detail.initialize();
        GenryoIchiranDialog.loadMasterData();
        GenryoIchiranDialog.loadDialogs();
        GenryoIchiranDialog.resizeDialogActive();
    };

    /**
     * ダイアログコントロールへのイベントの紐づけを行います。
     */
    GenryoIchiranDialog.initializeControlEvent = function () {
        var element = GenryoIchiranDialog.element;

        //TODO: 画面全体で利用するコントロールのイベントの紐づけ処理をここに記述します。
        element.on("hidden.bs.modal", GenryoIchiranDialog.hidden);
        element.on("shown.bs.modal", GenryoIchiranDialog.shown);
        element.on("click", ".detail_button", GenryoIchiranDialog.commands.bunsekiNyuryoku);
    };

    /**
    * 【005_分析値入力】画面の編集モードで遷移する。
    */
    GenryoIchiranDialog.commands.bunsekiNyuryoku = function () {
        var element = GenryoIchiranDialog.detail.element,
            select = element.find(".selected");

        if (!select.length) {
            return;
        }

        var elem = select.closest("tbody"),
            id = elem.attr("data-key"),
            entity;

        if (App.isUndefOrNull(id)) {
            return;
        }

        entity = GenryoIchiranDialog.detail.data.entry(id);


        var link = App.str.format(GenryoIchiranDialog.commands.urls.bunsekiNyuryoku, { cd_kaisha: entity.cd_kaisha, cd_busho: entity.cd_busho, cd_genryo: entity.cd_genryo }); //"005_分析値入力"

        window.open(link);
    }

    /**
     * ダイアログ非表示時処理を実行します。
     */
    GenryoIchiranDialog.hidden = function (e) {
        var element = GenryoIchiranDialog.element;

        //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
        element.find(":input:not([type='checkbox']):not([type='radio'])").val("");
        element.find("input[type='checkbox']").prop("checked", false);
        //TODO:radioボタンの初期表示は、画面要件に合わせて記述します。

        GenryoIchiranDialog.detail.dataTable.dataTable("clear");

        element.findP("data_count").text("");
        element.findP("data_count_total").text("");

        var items = element.find(".search-criteria :input:not(button)");
        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            GenryoIchiranDialog.setColValidStyle(item);
        }

        GenryoIchiranDialog.element.find(".detail_button").prop("disabled", true);
        GenryoIchiranDialog.notifyInfo.clear();
        GenryoIchiranDialog.notifyAlert.clear();

        GenryoIchiranDialog.resizeDialog();

        if (App.isFunc(GenryoIchiranDialog.checkRecalcu)) {
            GenryoIchiranDialog.checkRecalcu();
        }

        element.find(".komoku_new").remove();
        element.find(".hyoji_kojyo_tate").show();
        GenryoIchiranDialog.resizeTable(GenryoIchiranDialog.options.with_default);

        //resize fix column table
        GenryoIchiranDialog.detail.resizeFixColumn(element, undefined);

        //隠の時何もしない
        GenryoIchiranDialog.resizeDialog = GenryoIchiranDialog.resizeDialogDenice;
    };

    $(window).resize(function () {
        GenryoIchiranDialog.resizeDialog();
    });

    GenryoIchiranDialog.resizeDialog = function () { }

    /**
    *　隠の時何もしない。
    */
    GenryoIchiranDialog.resizeDialogDenice = function () { }

    /**
    *　隠の時何もしない。
    */
    GenryoIchiranDialog.resizeDialogActive = function () {
        var height = ($(window).height() - ($(window).height() / 100 * 80)) / 2;
        var width = ($(window).width() - ($(window).width() / 100 * 80)) / 2;

        $("#GenryoIchiranDialog").css("top", height);
        $("#GenryoIchiranDialog").css("left", width);
        setTimeout(function () {
            GenryoIchiranDialog.resizeDetailDialog();
        }, 100);
    }

    /**
    *　画面サイズ変更。
    */
    GenryoIchiranDialog.resizeDetailDialog = function () {
        var element = GenryoIchiranDialog.element;

        if (element.is(":visible")) {
            var element = GenryoIchiranDialog.element,
                //dialog height
                body = element.find(".modal-content").height();

            //「dialog height」 - 「modal-footer」-「modal-header」-「padding」
            element.find(".modal-body").css("max-height", body - 44 - 56 - 20);
            element.find(".modal-body").css("height", body - 44 - 56 - 20);

            //「header table」
            var modalHeader = element.find(".dt-head").outerHeight();

            //alert(modalHeader)
            //「modal-body」 -「header(1row *3)」-「header table」-「scrolbar + padding」
            element.find(".dt-body").css("height", (body - 44 - 56 - 20) - (32 * 3) - modalHeader - (15 + 40));

            element.find(".dt-fix-body").css("height", (body - 44 - 56 - 20) - (32 * 3) - modalHeader - (15 + 40) - 17);

            var slip = 0;
            while (element.find(".modal-body").get(0).scrollHeight > element.find(".modal-body").get(0).clientHeight && slip < 3) {
                slip += 1;
                var newHeight = element.find(".dt-body").outerHeight() - 10;

                element.find(".dt-body").css("height", newHeight);

                element.find(".dt-fix-body").css("height", newHeight - 17);
            }
        }
    };

    /**
     * ダイアログ表示時処理を実行します。
     */
    GenryoIchiranDialog.shown = function (e) {
        GenryoIchiranDialog.resizeDialog = GenryoIchiranDialog.resizeDialogActive;

        //$(document).off('focusin.modal');
        //初回起動時にdatatable作成
        if (App.isUndefOrNull(GenryoIchiranDialog.detail.fixedColumnIndex)) {
            GenryoIchiranDialog.detail.initialize();
        }
        GenryoIchiranDialog.header.element.findP("cd_kaisha").val(GenryoIchiranDialog.values.dataHin["cd_kaisha"]);
        GenryoIchiranDialog.header.loadDataBusho(GenryoIchiranDialog.values.dataHin["cd_kaisha"], GenryoIchiranDialog.values.dataHin["cd_kojo"]);
        //GenryoIchiranDialog.element.find(".flow-container").resize();

        //2019-08-29 : START : Bug #15194 初期フォーカス・IME設定-名前　→　コード　→　会社　→　工場　→　三ヶ月/全件　→　Button 検索　
        GenryoIchiranDialog.header.element.findP("nm_genryo").focus();
        //2019-08-29 : END : Bug #15194 初期フォーカス・IME設定-名前　→　コード　→　会社　→　工場　→　三ヶ月/全件　→　Button 検索　

        if (GenryoIchiranDialog.values.dataDefault != undefined && GenryoIchiranDialog.values.dataDefault["zenKojo"] == App.settings.app.hyoji_kojyo_tate[0].value) {
            GenryoIchiranDialog.header.element.findP("cd_genryo").val(GenryoIchiranDialog.values.dataDefault["cd_genryo"]);
            setTimeout(function () {
                GenryoIchiranDialog.header.element.findP("cd_busho").val(App.settings.app.hyoji_kojyo_tate[0].value);
                GenryoIchiranDialog.header.controlRadioChecked(App.settings.app.hyoji_kojyo_tate[0].value, GenryoIchiranDialog.header.element, GenryoIchiranDialog.header.element.findP("cd_genryo"));
                GenryoIchiranDialog.header.search(true);
            }, 1)
        }

        GenryoIchiranDialog.resizeDetailDialog();
        GenryoIchiranDialog.detail.element.find(".dt-container .flow-container .dt-body .dt-vscroll").css("width", GenryoIchiranDialog.options.with_default + 60);
    };

    /**
     * マスターデータのロード処理を実行します。
     */
    GenryoIchiranDialog.loadMasterData = function () {

        //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。

        var param = {
            cd_kengen: App.ui.page.user.cd_kengen,
            busho_hyoji: App.settings.app.busho_hyoji.hyoji,
            cd_kaisha_change_name: App.settings.app.CD_DAIHYO_KAISHA.kewpie,//会社コード　=　1（キユーピー）の場合、
            cd_busho_change_name: App.settings.app.shinki_genryo.value,//・部署コード　=　1（研究開発本部）　→　【区分／コード一覧＃定数/セッティング用＃新規原料＃新規登録原料】でセット
            name_change: App.settings.app.shinki_genryo.text
        };

        return $.ajax(App.ajax.webapi.get(GenryoIchiranDialog.urls.loadMasterData, param)).then(function (result) {

            GenryoIchiranDialog.values.dataMaster = result;

            var cd_kaisha = GenryoIchiranDialog.element.findP("cd_kaisha");
            cd_kaisha.children().remove();
            App.ui.appendOptions(
                cd_kaisha,
                "cd_kaisha",
                "nm_kaisha",
                result.kaisha
            );
        })

        //TODO: マスタデータのロード処理を実装後、下の1行を削除してください。
        return App.async.success();
    };

    /**
     *工場
     */
    GenryoIchiranDialog.header.loadDataBusho = function (cd_kaisha, busho_selected) {
        var cd_busho = GenryoIchiranDialog.element.findP("cd_busho");
        cd_busho.children().remove();

        //※先頭（1行目）に【区分／コード一覧＃定数/セッティング用＃表示工場＃全工場_縦】を追加する。
        App.ui.appendOptions(
            cd_busho,
            "value",
            "text",
             App.settings.app.hyoji_kojyo_tate
        );

        //	会社コード　=　1（キユーピー）の場合、			
        //・2行目に【区分／コード一覧＃定数/セッティング用＃表示工場＃全工場_横】を追加する。		
        if (cd_kaisha == App.settings.app.CD_DAIHYO_KAISHA.kewpie) {
            App.ui.appendOptions(
                cd_busho,
                "value",
                "text",
                 App.settings.app.hyoji_kojyo_yoko
            );
        }

        App.ui.appendOptions(
            cd_busho,
            "cd_busho",
            "nm_busho",
             jQuery.grep(GenryoIchiranDialog.values.dataMaster.busho, function (n, i) {
                 return (n.cd_kaisha == cd_kaisha);
             })
        );

        if (busho_selected != undefined) {
            cd_busho.val(busho_selected);
        }

        /**
         * 三ヶ月と全件をチェックする。
         */
        GenryoIchiranDialog.header.controlRadioChecked(cd_busho.val(), GenryoIchiranDialog.header.element, cd_busho);
    }

    /**
     * 共有のダイアログのロード処理を実行します。
     */
    GenryoIchiranDialog.loadDialogs = function () {

        if ($.find("#ConfirmDialog").length == 0) {
            return App.async.all({
                confirmDialog: $.get(GenryoIchiranDialog.urls.confirmDialog)
            }).then(function (result) {
                $("#dialog-container").append(result.successes.confirmDialog);
                GenryoIchiranDialog.dialogs.confirmDialog = ConfirmDialog;
            });
        } else {
            GenryoIchiranDialog.dialogs.confirmDialog = ConfirmDialog;
        }
    };

    /**
     * ダイアログヘッダー　バリデーションルールを定義します。
     */
    GenryoIchiranDialog.header.options.validations = {
        cd_genryo: {
            rules: {
                maxlength: 11,
                hankaku: true
            },
            options: {
                name: "コード"
            },
            messages: {
                maxlength: App.messages.base.maxlength,
                hankaku: App.messages.base.hankaku
            }
        },
        nm_genryo: {
            rules: {
                maxbytelength: 30
            },
            options: {
                name: "名前",
                byte: 15
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
    };

    /**
     * ダイアログヘッダーの初期化処理を行います。
     */
    GenryoIchiranDialog.header.initialize = function () {

        var element = GenryoIchiranDialog.element.find(".header");
        GenryoIchiranDialog.header.validator = element.validation(GenryoIchiranDialog.createValidator(GenryoIchiranDialog.header.options.validations));
        GenryoIchiranDialog.header.element = element;

        //TODO: 画面ヘッダーの初期化処理をここに記述します。
        //TODO: 画面ヘッダーで利用するコントロールのイベントの紐づけ処理をここに記述します。
        element.on("click", ".search", GenryoIchiranDialog.header.search);
        element.find(":input").on("change", GenryoIchiranDialog.header.change);

        //2019-08-29 : START : Bug #15194 初期フォーカス・IME設定-名前　→　コード　→　会社　→　工場　→　三ヶ月/全件　→　Button 検索　
        element.find(".search").on("keydown", function (e) {
            var code = e.keyCode || e.which;
            if (!e.shiftKey && code === 9) {
                setTimeout(function () {
                    if (GenryoIchiranDialog.detail.firstRow != undefined) {
                        GenryoIchiranDialog.detail.firstRow.find(".select").focus();
                    } else {
                        GenryoIchiranDialog.element.find(".cancel-button").focus();
                    }
                }, 1)
            }
        })

        GenryoIchiranDialog.element.find(".cancel-button").on("keydown", function (e) {
            var code = e.keyCode || e.which;
            if (e.shiftKey && code === 9) {
                setTimeout(function () {
                    if (GenryoIchiranDialog.element.find(".detail_button").is(":disabled")) {
                        element.find(".search").focus();
                    }
                }, 1)
            }
        })

        //2019-08-29 : START : Bug #15194 初期フォーカス・IME設定-名前　→　コード　→　会社　→　工場　→　三ヶ月/全件　→　Button 検索　
    };

    /**
    * 画面ヘッダーにある入力項目の変更イベントの処理を行います。
    */
    GenryoIchiranDialog.header.change = function (e) {
        var element = GenryoIchiranDialog.header.element,
            target = $(e.target),
            property = target.attr("data-prop"),
            data = element.form().data();

        GenryoIchiranDialog.header.validator.validate({
            targets: target
        }).then(function () {
            if (property == "cd_kaisha") {
                GenryoIchiranDialog.header.loadDataBusho(data[property], undefined);
            }

            if (property == "cd_busho") {
                GenryoIchiranDialog.header.controlRadioChecked(data[property], element, target);
            }

        }).always(function () {
            //画面サイズ変更。
            GenryoIchiranDialog.resizeDetailDialog();
        })
    }

    /**
     * 三ヶ月と全件をチェックする。
     */
    GenryoIchiranDialog.header.controlRadioChecked = function (cd_busho, element, target) {
        switch (Number(cd_busho)) {
            //工場 <> 「全工場_縦」、「全工場_横」、「新規登録原料」の場合のみ操作可能																	
            case App.settings.app.hyoji_kojyo_tate[0].value:
            case App.settings.app.hyoji_kojyo_yoko[0].value:
                element.find("#sankagatsu").prop("disabled", true).prop("checked", false);
                element.find("#subeteko").prop("checked", true);
                break;
            default:
                //工場 <> 「全工場_縦」、「全工場_横」、「新規登録原料」の場合のみ操作可能																	
                if (element.find(target).find("option:selected").html() == App.settings.app.shinki_genryo.text) {
                    element.find("#sankagatsu").prop("disabled", true).prop("checked", false);
                    element.find("#subeteko").prop("checked", true);
                }
                else {
                    //工場 = 「全工場_縦」、「全工場_横」、「新規登録原料」の場合のみ操作可能																	
                    element.find("#sankagatsu").prop("disabled", false).prop("checked", true);
                    element.find("#subeteko").prop("checked", false);
                }
                break;
        }
    }

    /**
     * ダイアログの検索処理を実行します。
     */
    GenryoIchiranDialog.header.search = function (isLoading) {
        var element = GenryoIchiranDialog.element,
            loadingTaget = element.find(".modal-content"),
            deferred = $.Deferred();

        GenryoIchiranDialog.header.validator.validate().done(function () {

            GenryoIchiranDialog.options.filter = GenryoIchiranDialog.header.createFilter();
            GenryoIchiranDialog.detail.dataTable.dataTable("clear");

            if (isLoading) {
                App.ui.loading.show("", loadingTaget);
                GenryoIchiranDialog.notifyAlert.clear();
            }

            return $.ajax(App.ajax.webapi.get(GenryoIchiranDialog.urls.search, GenryoIchiranDialog.options.filter)).then(function (result) {
                GenryoIchiranDialog.detail.bind(result);
                deferred.resolve();
            }).fail(function (error) {

                GenryoIchiranDialog.notifyAlert.message(App.ajax.handleError(error).message).show();
                deferred.reject();
            }).always(function () {

                if (isLoading) {
                    App.ui.loading.close(loadingTaget);
                }
            });
        });

        return deferred.promise();
    };

    /**
     * ダイアログの検索条件を組み立てます
     */
    GenryoIchiranDialog.header.createFilter = function () {
        var criteria = GenryoIchiranDialog.header.element.form().data(),
            filters = {};

        //TODO: 画面で設定された検索条件を取得し、データ取得サービスのフィルターオプションを組み立てます。
        //コード
        if (!App.isUndefOrNullOrStrEmpty(criteria.cd_genryo)) {
            filters["cd_genryo"] = criteria.cd_genryo;
        }
        //会社
        if (!App.isUndefOrNullOrStrEmpty(criteria.cd_kaisha)) {
            filters["cd_kaisha"] = criteria.cd_kaisha;
        }
        //名前
        if (!App.isUndefOrNullOrStrEmpty(criteria.nm_genryo)) {
            filters["nm_genryo"] = criteria.nm_genryo;
        }

        filters["flg_hyoji_table"] = false;

        //工場
        if (!App.isUndefOrNullOrStrEmpty(criteria.cd_busho)) {
            filters["cd_busho"] = criteria.cd_busho;
            //全工場_縦
            if (filters["cd_busho"] == App.settings.app.hyoji_kojyo_tate[0].value || filters["cd_busho"] == App.settings.app.hyoji_kojyo_yoko[0].value) {
                filters["cd_busho"] = null;
            }

            //全工場_横
            if (criteria.cd_busho == App.settings.app.hyoji_kojyo_yoko[0].value) {
                filters["flg_hyoji_table"] = true;
            }
        }
        //三ヶ月
        filters["sankagatsu"] = criteria["sankagatsu"] == "on";
        //全件
        filters["subeteko"] = criteria["subeteko"] == "on";

        filters["tanka_hyoujigaisha"] = App.settings.app.cd_category.tanka_hyoujigaisha;
        filters["hyoji1"] = App.settings.app.tanka_hyoji.hyoji1;
        filters["hyoji9"] = App.settings.app.tanka_hyoji.hyoji9;

        filters["top"] = GenryoIchiranDialog.options.top;

        return filters;
    };

    /**
     * ダイアログヘッダー　バリデーションルールを定義します。
     */
    //GenryoIchiranDialog.detail.options.validations = {
    //};

    /**
     * 画面明細の初期化処理を行います。
     */
    GenryoIchiranDialog.detail.initialize = function () {

        var element = GenryoIchiranDialog.element.find(".detail"),
            table = element.find(".datatable"),
            offsetHeight = $(window).height() * 15 / 100,
            datatable = table.dataTable({
                height: 100,
                //resize: true,
                //resizeOffset: offsetHeight,
                //列固定横スクロールにする場合は、下記3行をコメント解除
                fixedColumn: true,               //列固定の指定
                fixedColumns: 1,                 //固定位置を指定（左端を0としてカウント）
                innerWidth: GenryoIchiranDialog.options.with_default,                 //可動列の合計幅を指定
                onselect: GenryoIchiranDialog.detail.select
                //onchange: GenryoIchiranDialog.detail.change
            });
        table = element.find(".datatable");        //列固定にした場合DOM要素が再作成されるため、変数を再取得

        //GenryoIchiranDialog.detail.validator = element.validation(GenryoIchiranDialog.createValidator(GenryoIchiranDialog.detail.options.validations));
        GenryoIchiranDialog.detail.element = element;
        GenryoIchiranDialog.detail.dataTable = datatable;
        // 行選択時に利用するテーブルインデックスを指定します
        GenryoIchiranDialog.detail.fixedColumnIndex = element.find(".fix-columns").length;

        //TODO: 画面明細で利用するコントロールのイベントの紐づけ処理をここに記述します。
        element.on("click", ".select", GenryoIchiranDialog.detail.selectData);

        //TODO: 画面明細の初期化処理をここに記述します。
        GenryoIchiranDialog.options.filter = { flg_hyoji_table: false };
        GenryoIchiranDialog.resizeDialogActive()
    };

    /**
     * テーブルサイズ変更。
     */
    GenryoIchiranDialog.resizeTable = function (with_table) {
        var element = GenryoIchiranDialog.detail.element;
        //テーブルでサイズ変更
        var theadTable = element.find(".dt-container .flow-container .dt-head .datatable"),
            theadWith = theadTable.css("width").replace("px", ""),
            bodyTable = element.find(".dt-container .flow-container .dt-body .datatable"),
            dtvscroll = element.find(".dt-container .flow-container .dt-body .dt-vscroll");

        theadTable.css("width", with_table + "px");
        theadTable.css("max-width", with_table + "px");
        bodyTable.css("width", with_table + "px");
        bodyTable.css("max-width", with_table + "px");
        dtvscroll.css("width", with_table + "px");
    };

    /**
     * ダイアログ明細へのデータバインド処理を行います。
     */
    GenryoIchiranDialog.detail.bind = function (data, isNewData) {
        var appliersItem = function (value, element) {
            if (value == 0) {
                element.text("0.00");
                return true;
            }
            element.text(page.detail.formatNumber(value, 2).forDisp);
            return true;
        };

        GenryoIchiranDialog.detail.options.bindOption["appliers"] = {
            ritu_sakusan: appliersItem,
            ritu_shokuen: appliersItem,
            ritu_sousan: appliersItem,
            ritu_abura: appliersItem
        };
        GenryoIchiranDialog.detail.options.bindOption["appliers"]["tanka"] = appliersItem;
        GenryoIchiranDialog.detail.options.bindOption["appliers"]["budomari"] = appliersItem;

        var element = GenryoIchiranDialog.detail.element;


        var i, l, item, dataSet, dataCount;

        dataCount = data.Count ? data.Count : 0;
        data = (data.Items) ? data.Items : data;

        dataSet = App.ui.page.dataSet();
        GenryoIchiranDialog.detail.data = dataSet;
        GenryoIchiranDialog.detail.dataTable.dataTable("clear");

        element.find(".komoku_new").remove();

        if (GenryoIchiranDialog.options.filter["flg_hyoji_table"] == false) {

            //resize fix column table
            GenryoIchiranDialog.detail.resizeFixColumn(element, 20);

            element.find(".hyoji_kojyo_tate").show();
            GenryoIchiranDialog.resizeTable(GenryoIchiranDialog.options.with_default);

        } else {

            var table = element.find(".dt-container .flow-container table"), iColCopy = 1, iColInset = 1;

            var bushoList = jQuery.grep(GenryoIchiranDialog.values.dataMaster.busho, function (n, i) {
                //会社コード　=　1（キユーピー）の場合、
                //・部署コード　=　1（研究開発本部）　→　【区分／コード一覧＃定数/セッティング用＃新規原料＃新規登録原料】でセット。
                if (GenryoIchiranDialog.options.filter["cd_kaisha"] == App.settings.app.CD_DAIHYO_KAISHA.kewpie && n.cd_busho == App.settings.app.shinki_genryo.value) {
                    n.nm_busho = App.settings.app.shinki_genryo.text
                }
                return (n.cd_kaisha == GenryoIchiranDialog.options.filter["cd_kaisha"]);
            })

            //歩留１
            $.each(bushoList.reverse(), function (index, item) {
                jQuery('tr', table).each(function () {
                    cols = jQuery(this).children('th, td');
                    htmlSrc = cols.eq(iColCopy).clone().removeClass("th-tmpl").addClass("komoku_new");
                    label = htmlSrc.find("label");

                    if (label.length) {
                        label.attr("data-prop", "budomari_" + item.cd_busho);
                    }
                    else {
                        if (htmlSrc.html().length) {
                            htmlSrc.html(item.nm_busho + "歩留１")
                        }
                    }

                    htmlSrc.css("width", "50px");
                    htmlSrc.addClass("right");
                    htmlSrc.insertAfter(cols.eq(iColInset));
                });
            });

            //単価
            $.each(bushoList, function (index, item) {
                jQuery('tr', table).each(function () {
                    cols = jQuery(this).children('th, td');
                    htmlSrc = cols.eq(iColCopy).clone().removeClass("th-tmpl").addClass("komoku_new");
                    label = htmlSrc.find("label");

                    if (label.length) {
                        label.attr("data-prop", "tanka_" + item.cd_busho);
                        GenryoIchiranDialog.detail.options.bindOption["appliers"]["tanka_" + item.cd_busho] = appliersItem;
                        GenryoIchiranDialog.detail.options.bindOption["appliers"]["budomari_" + item.cd_busho] = appliersItem;
                    }
                    else {
                        if (htmlSrc.html().length) {
                            htmlSrc.html(item.nm_busho)
                        }
                    }
                    htmlSrc.css("width", "120px");
                    htmlSrc.addClass("right");
                    htmlSrc.insertAfter(cols.eq(iColInset));
                });
            });

            element.find(".hyoji_kojyo_tate").hide();

            //テーブルサイズ変更。
            GenryoIchiranDialog.resizeTable(GenryoIchiranDialog.options.with_default - GenryoIchiranDialog.options.with_col_hide_default + (bushoList.length * 50) + (bushoList.length * 120));
        }

        GenryoIchiranDialog.detail.firstRow = undefined;

        GenryoIchiranDialog.detail.dataTable.dataTable("addRows", data, function (row, item) {
            //check this
            //・廃止区分(kbn_haishi)＝１の場合、「廃止」を表示して対象行の背景色が灰色で表示する。
            if (item.kbn_haishi == 1) {
                item.kbn_haishi_hyoji = "廃止";
                row.addClass("haishi_aru");
            } else {
                //・廃止区分(kbn_haishi)＝0の場合「使用可能」を表示して対象行の背景色の変更なしで表示。
                item.kbn_haishi_hyoji = "使用可能";
            }

            //単価
            item["budomari_" + item.cd_busho] = item.budomari;
            //歩留１
            item["tanka_" + item.cd_busho] = item.tanka;

            (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
            row.form(GenryoIchiranDialog.detail.options.bindOption).bind(item);
            if (GenryoIchiranDialog.detail.firstRow == undefined) {
                GenryoIchiranDialog.detail.firstRow = row;
            }
            return row;
        }, true);

        //2019-08-29 : START : Bug #15194 初期フォーカス・IME設定-名前　→　コード　→　会社　→　工場　→　三ヶ月/全件　→　Button 検索　
        if (GenryoIchiranDialog.detail.firstRow != undefined) {
            GenryoIchiranDialog.detail.firstRow.find(".select").on("keydown", function (e) {
                var code = e.keyCode || e.which;
                if (e.shiftKey && code === 9) {
                    setTimeout(function () {
                        GenryoIchiranDialog.header.element.find(".search").focus();
                    }, 1)
                }
            })
        }
        //2019-08-29 : END : Bug #15194 初期フォーカス・IME設定-名前　→　コード　→　会社　→　工場　→　三ヶ月/全件　→　Button 検索　

        if (!isNewData) {
            GenryoIchiranDialog.detail.element.findP("data_count").text(data.length);
            GenryoIchiranDialog.detail.element.findP("data_count_total").text(dataCount);
        }

        if (dataCount >= App.settings.base.dialogDataTakeCount) {
            //check this : param mess
            GenryoIchiranDialog.notifyInfo.message(App.str.format(App.messages.app.AP0021, dataCount)).show();
        }

        //TODO: 画面明細へのデータバインド処理をここに記述します。

        //バリデーションを実行します。
        //GenryoIchiranDialog.detail.validateList(true);
        //$(GenryoIchiranDialog.element).resize();

        //resize fix column table
        GenryoIchiranDialog.detail.resizeFixColumn(element, undefined);

        //詳細
        GenryoIchiranDialog.element.find(".detail_button").prop("disabled", data.length == 0 || App.ui.page.user.cd_kaisha == App.settings.app.kaisha_AOH);

        //画面サイズ変更。
        setTimeout(function () {
            GenryoIchiranDialog.resizeDetailDialog();
        }, 1)
    };

    /**
     * resize fix column table。
     */
    GenryoIchiranDialog.detail.resizeFixColumn = function (element, trAuto) {

        if (trAuto == undefined) {
            trAuto = element.find(".flow-container .dt-head .datatable thead tr").height();
        }

        element.find(".dt-container .fix-columns .dt-fix-head .datatable thead tr").css("height", trAuto + "px");
        element.find(".dt-container .fix-columns .dt-fix-body").css("top", (trAuto + 1) + "px");
    }

    /**
     * 明細の各行にデータを設定する際のオプションを定義します。
     */
    GenryoIchiranDialog.detail.options.bindOption = {
        // TODO: 主キーが直接入力の場合には、修正の場合変更を不可とします。
        appliers: {
            //tanka_hyoji: function (value, element) {
            //    element.text(page.detail.formatNumber(value, 2).forDisp);
            //    return true;
            //}
        }
    };

    /**
     * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
     */
    GenryoIchiranDialog.detail.selectData = function (e) {
        var target = $(e.target),
            id, entity,
            selectData = function (entity) {
                if (App.isFunc(GenryoIchiranDialog.dataSelected)) {
                    GenryoIchiranDialog.dataSelected(entity);
                    //if (!GenryoIchiranDialog.dataSelected(entity)) {
                    //    GenryoIchiranDialog.element.modal("hide");
                    //}
                }
                //else {
                //    GenryoIchiranDialog.element.modal("hide");
                //}
            };

        GenryoIchiranDialog.detail.dataTable.dataTable("getRow", target, function (row) {
            id = row.element.attr("data-key");
            entity = GenryoIchiranDialog.detail.data.entry(id);
        });

        if (App.isUndef(id)) {
            return;
        }

        //if (GenryoIchiranDialog.detail.data.isUpdated(id)) {
        //    var options = {
        //        text: App.messages.base.MS0024
        //    };
        //    GenryoIchiranDialog.dialogs.confirmDialog.confirm(options)
        //        .then(function () {
        //            selectData(entity);
        //        });
        //} else {
        selectData(entity);
        //}
    };

    //GenryoIchiranDialog.detail.selectCopyData = function () {
    //    var element = GenryoIchiranDialog.detail.element,
    //        selected = element.find(".datatable .select-tab.selected").closest("tbody");

    //    if (!selected.length) {
    //        return;
    //    }
    //    var id = selected.attr("data-key"),
    //        entity = GenryoIchiranDialog.detail.data.entry(id);

    //    if (App.isFunc(GenryoIchiranDialog.dataSelected)) {
    //        GenryoIchiranDialog.dataSelected(entity, true);
    //    }
    //}


    /**
     * 明細の一覧の行が選択された時の処理を行います。
     */
    GenryoIchiranDialog.detail.select = function (e, row) {
        $($(row.element[GenryoIchiranDialog.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab.selected")).removeClass("selected").addClass("unselected");
        $(row.element[GenryoIchiranDialog.detail.fixedColumnIndex].querySelectorAll(".select-tab")).removeClass("unselected").addClass("selected");

        //選択行全体に背景色を付ける場合は以下のコメントアウトを解除します。
        if (!App.isUndefOrNull(GenryoIchiranDialog.detail.selectedRow)) {
            GenryoIchiranDialog.detail.selectedRow.element.find(".selected-row").removeClass("selected-row");
        }
        row.element.find("tr").addClass("selected-row");
        GenryoIchiranDialog.detail.selectedRow = row;
    };

    /**
     * 明細の一覧の入力項目の変更イベントの処理を行います。
     */
    //GenryoIchiranDialog.detail.change = function (e, row) {
    //    var target = $(e.target),
    //        id = row.element.attr("data-key"),
    //        property = target.attr("data-prop"),
    //        entity = GenryoIchiranDialog.detail.data.entry(id),
    //        options = {
    //            filter: GenryoIchiranDialog.detail.validationFilter
    //        };

    //    GenryoIchiranDialog.values.isChangeRunning[property] = true;

    //    GenryoIchiranDialog.detail.executeValidation(target, row)
    //    .then(function () {
    //        //entity[property] = row.element.form().data()[property];
    //        //GenryoIchiranDialog.detail.data.update(entity);

    //        //if (GenryoIchiranDialog.element.find(".save").is(":disabled")) {
    //        //    GenryoIchiranDialog.element.find(".save").prop("disabled", false);
    //        //}

    //        //入力行の他の項目のバリデーション（必須チェック以外）を実施します
    //        //GenryoIchiranDialog.detail.executeValidation(row.element.find(":input"), row, options);
    //    }).always(function () {
    //        delete GenryoIchiranDialog.values.isChangeRunning[property];
    //    });
    //};

    /**
     * 明細の一覧に新規データを追加します。
     */
    //GenryoIchiranDialog.detail.addNewItem = function () {
    //    //TODO:新規データおよび初期値を設定する処理を記述します。
    //    var newData = {
    //        //no_seq: page.values.no_seq
    //    };

    //    GenryoIchiranDialog.detail.data.add(newData);
    //    GenryoIchiranDialog.detail.dataTable.dataTable("addRow", function (row) {
    //        row.form(GenryoIchiranDialog.detail.options.bindOption).bind(newData);
    //        return row;
    //    }, true);

    //    if (GenryoIchiranDialog.element.find(".save").is(":disabled")) {
    //        GenryoIchiranDialog.element.find(".save").prop("disabled", false);
    //    }
    //};

    /**
     * 明細の一覧で選択されている行とデータを削除します。
     */
    //GenryoIchiranDialog.detail.deleteItem = function (e) {
    //    var element = GenryoIchiranDialog.detail.element,
    //        selected = element.find(".datatable .select-tab.selected").closest("tbody");

    //    if (!selected.length) {
    //        return;
    //    }

    //    GenryoIchiranDialog.detail.dataTable.dataTable("deleteRow", selected, function (row) {
    //        var id = row.attr("data-key"),
    //            newSelected;

    //        row.find(":input").each(function (i, elem) {
    //            GenryoIchiranDialog.notifyAlert.remove(elem);
    //        });

    //        if (!App.isUndefOrNull(id)) {
    //            var entity = GenryoIchiranDialog.detail.data.entry(id);
    //            GenryoIchiranDialog.detail.data.remove(entity);
    //        }

    //        newSelected = row.next().not(".item-tmpl");
    //        if (!newSelected.length) {
    //            newSelected = row.prev().not(".item-tmpl");
    //        }
    //        if (newSelected.length) {
    //            for (var i = GenryoIchiranDialog.detail.fixedColumnIndex; i > -1; i--) {
    //                if ($(newSelected[i]).find(":focusable:first").length) {
    //                    $(newSelected[i]).find(":focusable:first").focus();
    //                    break;
    //                }
    //            }
    //        }
    //    });

    //    if (GenryoIchiranDialog.element.find(".save").is(":disabled")) {
    //        GenryoIchiranDialog.element.find(".save").prop("disabled", false);
    //    }

    //};

    /**
    * 明細のバリデーションを実行します。
    */
    //GenryoIchiranDialog.detail.executeValidation = function (targets, row, options) {
    //    var defaultOptions = {
    //        targets: targets,
    //        state: {
    //            tbody: row,
    //            isGridValidation: true
    //        }
    //    },
    //        execOptions = $.extend(true, {}, defaultOptions, options);

    //    return GenryoIchiranDialog.detail.validator.validate(execOptions);
    //};

    /**
     * 明細のバリデーションフィルターを設定します。（必須チェックを行わない）
     */
    GenryoIchiranDialog.detail.validationFilter = function (item, method, state, options) {
        return method !== "required";
    };

    /**
     * 明細の一覧全体のバリデーションを実行します。
     */
    //GenryoIchiranDialog.detail.validateList = function (suppressMessage) {
    //    var validations = [],
    //        options = {
    //            state: {
    //                suppressMessage: suppressMessage,
    //            }
    //        };

    //    GenryoIchiranDialog.detail.dataTable.dataTable("each", function (row, index) {
    //        validations.push(GenryoIchiranDialog.detail.executeValidation(row.element.find(":input"), row.element, options));
    //    });

    //    return App.async.all(validations);
    //};

</script>

<div class="modal fade wide" tabindex="-1" id="GenryoIchiranDialog" style="width: 80%; height: 80%; overflow: hidden">
    <div class="modal-dialog" style="height: 100%; width: 100%;">
        <%-- style="height: 100%; width: 980px; margin: 5px;">--%>
        <div class="modal-content" style="height: 90%; width: 99.3%; top: 5px; left: 5px; right: 5px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">原料一覧</h4>
            </div>

            <div class="modal-body">
                <div class="modal-wrap">
                    <div class="header">
                        <div class="search-criteria">
                            <div class="row">

                                <div class="control-label col-xs-1">
                                    <label>コード</label>
                                </div>
                                <div class="control col-xs-3">
                                    <%--2019-08-29 : START : Bug #15194 初期フォーカス・IME設定-②コードは半角項目なのでフォーカスをあてた時IME設定を半角英数字にしてください。--%>
                                    <%--<input type="text" class="ime-active" data-prop="cd_genryo" tabindex="2"/>--%>
                                    <input type="tel" class="ime-active" data-prop="cd_genryo" tabindex="2"/>
                                    <%--2019-08-29 : END : Bug #15194 初期フォーカス・IME設定--%>
                                </div>
                                <div class="control-label col-xs-1">
                                    <label>会社</label>
                                </div>
                                <div class="control col-xs-3">
                                    <%--2019-08-29 : Bug #15194 初期フォーカス・IME設定-名前　→　コード　→　会社　→　工場　→　三ヶ月/全件　→　Button 検索--%>
                                    <select data-prop="cd_kaisha" tabindex="3"></select>
                                </div>
                                <div class="control-label col-xs-1">
                                    <label style="min-width: 50px" for="sankagatsu">三ヶ月</label>
                                </div>
                                <div class="control col-xs-1">
                                    <%--2019-08-29 : Bug #15194 初期フォーカス・IME設定-名前　→　コード　→　会社　→　工場　→　三ヶ月/全件　→　Button 検索--%>
                                    <input type="radio" name="gender" data-prop="sankagatsu" id="sankagatsu" tabindex="5" />
                                </div>
                                <div class="control col-xs-2">
                                    <label style="background-color: red; color: white; min-width: 110px; margin-top: 2px; font-size: 15px; padding: 1px" class="center">レベル１</label>
                                </div>
                            </div>
                            <div class="row">

                                <div class="control-label col-xs-1">
                                    <label>名前</label>
                                </div>
                                <div class="control col-xs-3">
                                    <%--2019-08-29 : Bug #15194 初期フォーカス・IME設定-名前　→　コード　→　会社　→　工場　→　三ヶ月/全件　→　Button 検索--%>
                                    <input type="text" class="ime-active" data-prop="nm_genryo" tabindex="1" />
                                </div>
                                <div class="control-label col-xs-1">
                                    <label>工場</label>
                                </div>
                                <div class="control col-xs-3">
                                    <%--2019-08-29 : Bug #15194 初期フォーカス・IME設定-名前　→　コード　→　会社　→　工場　→　三ヶ月/全件　→　Button 検索--%>
                                    <select data-prop="cd_busho" tabindex="4"></select>
                                </div>
                                <div class="control-label col-xs-1">
                                    <label style="min-width: 50px" for="subeteko">全件</label>
                                </div>
                                <div class="control col-xs-1">
                                    <%--2019-08-29 : Bug #15194 初期フォーカス・IME設定-名前　→　コード　→　会社　→　工場　→　三ヶ月/全件　→　Button 検索--%>
                                    <input type="radio" name="gender" data-prop="subeteko" checked='checked' id="subeteko" tabindex="5" />
                                </div>
                                <div class="control col-xs-2">
                                    <%--2019-08-29 : Bug #15194 初期フォーカス・IME設定-名前　→　コード　→　会社　→　工場　→　三ヶ月/全件　→　Button 検索--%>
                                    <button type="button" class="btn btn-sm btn-primary search" style="float: right" tabindex="6">検索</button>
                                </div>
                            </div>
                        </div>
                        <%--   <div style="position: relative; height: 50px;">
                            <button type="button" style="position: absolute; right: 5px; top: 5px;" class="btn btn-sm btn-primary search">検索</button>
                        </div>--%>
                    </div>
                    <div class="detail">
                        <div class="control-label toolbar">
                            <%--<i class="icon-th"></i>
                            <div class="btn-group">
                                <button type="button" class="btn btn-default btn-xs add-item">追加</button>
                                <button type="button" class="btn btn-default btn-xs del-item">削除</button>
                            </div>--%>
                            <span class="data-count">
                                <span data-prop="data_count"></span>
                                <span>/</span>
                                <span data-prop="data_count_total"></span>
                            </span>
                        </div>
                        <table class="datatable">
                            <!--TODO: 明細一覧のヘッダーを定義するHTMLをここに記述します。-->
                            <thead>
                                <tr>
                                    <th style="width: 10px;"></th>
                                    <th style="width: 50px;"></th>
                                    <th style="width: 60px;">原料<br />
                                        コード</th>
                                    <th style="width: 180px;">原料名</th>
                                    <th style="width: 100px;" class="hyoji_kojyo_tate">工場名</th>
                                    <th style="width: 100px;" class="hyoji_kojyo_tate">単価</th>
                                    <th style="width: 50px;" class="hyoji_kojyo_tate">三ヶ月</th>
                                    <th style="width: 50px;" class="hyoji_kojyo_tate">未使用</th>
                                    <%--TOsVN - 19075 - 2023/06/20: st ShohinKaihatuSupport Modify2023--%>
                                    <th style="width: 50px;" class="">原料<br />
                                        原産地</th>
                                    <%--ed ShohinKaihatuSupport Modify2023--%>
                                    <th style="width: 50px;" class="hyoji_kojyo_tate">歩留１</th>
                                    <th style="width: 50px;" class="befor_ritu_sakusan_head">酢酸（％）</th>
                                    <th style="width: 50px;">食塩（％）</th>
                                    <th style="width: 50px;">総酸（％）</th>
                                    <th style="width: 60px;">油含有率<br />
                                        （％）</th>
                                    <th style="width: 150px;">メモ</th>
                                    <th style="width: 70px;">廃止</th>
                                    <th style="width: 100px;">確定コード</th>
                                    <th style="width: 0px;" class="komoku_template_head"></th>
                                </tr>
                            </thead>
                            <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->
                            <tbody class="item-tmpl" style="cursor: default; display: none;">
                                <tr>
                                    <td style="width: 10px;">
                                        <span class="select-tab unselected"></span>
                                    </td>
                                    <td style="width: 50px;">
                                        <button type="button" style="margin: 1px; padding: 0px 7px;" class="btn btn-success btn-xs select">選択</button>
                                        <%--<button type="button" class="btn btn-sm btn-primary select">検索</button>--%>
                                    </td>
                                    <td style="width: 60px;">
                                        <%--<label data-prop="cd_genryo"></label>--%>
                                        <label data-prop="cd_genryo" class="overflow-ellipsis"></label>
                                    </td>
                                    <td style="width: 180px;">
                                        <label data-prop="nm_genryo" class="overflow-ellipsis"></label>
                                    </td>
                                    <td style="width: 100px;" class="hyoji_kojyo_tate">
                                        <label data-prop="nm_busho" class="overflow-ellipsis"></label>
                                    </td>
                                    <td style="width: 100px;" class="hyoji_kojyo_tate right">
                                        <label data-prop="tanka"></label>
                                    </td>
                                    <td style="width: 50px;" class="hyoji_kojyo_tate center">
                                        <label data-prop="flg_shiyo"></label>
                                    </td>
                                    <td style="width: 50px;" class="hyoji_kojyo_tate center">
                                        <label data-prop="flg_mishiyo"></label>
                                    </td>
                                    <%--TOsVN - 19075 - 2023/06/20: st ShohinKaihatuSupport Modify2023--%>
                                    <td style="width: 50px;" class="center">
                                        <label data-prop="flg_gensanchi"></label>
                                    </td>
                                    <%-- ed ShohinKaihatuSupport Modify2023--%>
                                    <td style="width: 50px;" class="hyoji_kojyo_tate right">
                                        <label data-prop="budomari"></label>
                                    </td>
                                    <td style="width: 50px;" class="right">
                                        <label data-prop="ritu_sakusan"></label>
                                    </td>
                                    <td style="width: 50px;" class="right">
                                        <label data-prop="ritu_shokuen"></label>
                                    </td>
                                    <td style="width: 50px;" class="right">
                                        <label data-prop="ritu_sousan"></label>
                                    </td>
                                    <td style="width: 60px;" class="right">
                                        <label data-prop="ritu_abura"></label>
                                    </td>
                                    <td style="width: 150px;">
                                        <label data-prop="memo" class="overflow-ellipsis"></label>
                                    </td>
                                    <td style="width: 70px;">
                                        <label data-prop="kbn_haishi_hyoji"></label>
                                    </td>
                                    <td style="width: 100px;">
                                        <label data-prop="cd_kakutei"></label>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
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
                <button type="button" class="btn btn-sm btn-primary detail_button" disabled="disabled" name="save" style="float: left">詳細</button>
                <button type="button" class="cancel-button btn btn-sm btn-default" data-dismiss="modal" name="close">閉じる</button>
            </div>

        </div>
    </div>
</div>
