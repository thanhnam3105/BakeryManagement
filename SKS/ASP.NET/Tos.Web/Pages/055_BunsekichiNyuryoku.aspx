<%@ Page Title="055_分析値入力 （アヲハタ）" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="055_BunsekichiNyuryoku.aspx.cs" Inherits="Tos.Web.Pages._055_BunsekichiNyuryoku" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchInputDetail(Ver2.1)】 Template--%>

<asp:Content ID="IncludeContent" ContentPlaceHolderID="IncludeContent" runat="server">

    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/part.css") %>" type="text/css" />
    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/headerdetail.css") %>" type="text/css" />

    <% #if DEBUG %>
    <script src="<%=ResolveUrl("~/Scripts/datatable.js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Scripts/part.js") %>" type="text/javascript"></script>
    <% #else %>
    <script src="<%=ResolveUrl("~/Scripts/datatable.min.js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Scripts/part.min.js") %>" type="text/javascript"></script>
    <% #endif %>
</asp:Content>

<asp:Content ID="HeadContent" ContentPlaceHolderID="HeadContent" runat="server">

    <style type="text/css">
        div .detail-command {
            text-align: center;
        }

        .btn-next-search {
            width: 200px;
        }

        .caution {
            color: red;
        }

        /*textareaが指定されている枠のクラス*/
        .textarea-height {
            height: 110px;
        }

        /*textareaのテキストボックス自体のクラス*/
        .textareabox {
            height: 90px;
            width: 1430px;
        }

        /*原料使用可否代替推奨コードのクラス*/
        .textarea-cdgenryo {
            height: 60px;
        }

        .textfont {
            font-size: 12px;
        }
    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("_055_BunsekichiNyuryoku", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.dataTakeCount,   // TODO:取得するデータ数を指定します。
                filter: ""
            },
            values: {
                isChangeRunning: {},
                hasChange: false,
                hasChangeGenOAH: false,
                hasChangeGenKojoOAH: false,
                firstChange: true,
                //廃止権限
                id_kino_edit: 10,
                id_data_edit: 9
            },
            urls: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
                loadData: "../api/_055_BunsekichiNyuryoku/Get",
                bunekichiNyuryoku: "../api/_055_BunsekichiNyuryoku"
            },
            header: {
                options: {},
                values: {},
                urls: {}
            },
            detail: {
                options: {},
                values: {}
            },
            detailInput: {
                options: {},
                values: {},
                mode: {
                    input: "input",
                    edit: "edit",
                    datanone: "datanone"
                },
                urls: {
                    kaisha: "../Services/ShisaQuickService.svc/ma_kaisha?$filter=cd_kaisha eq {0}",
                    tani: "../Services/ShisaQuickService.svc/ma_literal?$filter=cd_category eq '{0}' &$orderby=no_sort",
                }
            },
            dialogs: {},
            commands: {}
        });

        /**
         * 単項目要素をエラーのスタイルに設定します。
         * @param target 設定する要素
         */
        page.setColInvalidStyle = function (target) {
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
        page.setColValidStyle = function (target) {
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
        page.validationSuccess = function (results, state) {
            var i = 0, l = results.length,
                item, $target;

            for (; i < l; i++) {
                item = results[i];
                if (state && state.isGridValidation) {
                    page.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                        if (row && row.element) {
                            row.element.find("tr").removeClass("has-error");
                        }
                    });
                } else {
                    page.setColValidStyle(item.element);
                }

                App.ui.page.notifyAlert.remove(item.element);
            }
        };

        /**
         * バリデーション失敗時の処理を実行します。
         */
        page.validationFail = function (results, state) {

            var i = 0, l = results.length,
                item, $target;

            for (; i < l; i++) {
                item = results[i];
                if (state && state.isGridValidation) {
                    page.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                        if (row && row.element) {
                            row.element.find("tr").addClass("has-error");
                        }
                    });
                } else {
                    page.setColInvalidStyle(item.element);
                }

                if (state && state.suppressMessage) {
                    continue;
                }
                App.ui.page.notifyAlert.message(item.message, item.element).show();
            }
        };

        /**
         * バリデーション後の処理を実行します。
         */
        page.validationAlways = function (results) {
            //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
        };

        /**
          * 指定された定義をもとにバリデータを作成します。
          * @param target バリデーション定義
          * @param options オプションに設定する値。指定されていない場合は、
          *                画面の success/fail/always のハンドル処理が指定されたオプションが設定されます。
          */
        page.createValidator = function (target, options) {
            return App.validation(target, options || {
                success: page.validationSuccess,
                fail: page.validationFail,
                always: page.validationAlways
            });
        };

        /**
         * 編集明細の一覧に新しい行を追加します。
         */
        page.commands.addItem = function () {

            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            App.ui.loading.show();

            var sleep = 0;
            kbnHaishi = 0;
            var condition = "Object.keys(page.values.isChangeRunning).length == 0";
            App.ui.wait(sleep, condition, 100)
            .then(function () {
                page.validateAll().then(function () {
                    var options = {
                        text: App.messages.app.AP0004
                    };
                    page.dialogs.confirmDialog.confirm(options)
                    .then(function () {
                        App.ui.loading.show();
                        //廃止にチェックがはいっているときは1で、チェックが入っていないときは0で保存する
                        if ($('#kbn_haishi').is(':checked')) {
                            kbnHaishi = 1;
                        }
                        else {
                            kbnHaishi = 0;
                        }

                        //確認日時、確認者IDが空白だったらnullにする
                        var id_kakunin = page.detailInput.element.findP("id_kakunin").val(),
                            dt_kakunin = page.detailInput.element.findP("dt_kakunin").val();
                        if (dt_kakunin == "") {
                            dt_kakunin = null;
                        }
                        if (id_kakunin == "") {
                            id_kakunin = null;
                        }

                        changeSets = {
                            ma_genryo: page.detailInput.data.getChangeSet(),
                            nm_genryo: page.detailInput.element.findP("nm_genryo").val(),
                            cd_busho: 0,
                            flg_haishi: kbnHaishi,
                            id_kakunin: id_kakunin,
                            dt_kakunin: dt_kakunin,
                            updateNmGenryo: updateNmGenryo
                        }

                        var cd_kaisha = page.detailInput.element.findP("cd_kaisha").val(),
                            cd_genryo = page.detailInput.element.findP("cd_genryo").val();

                        //該当の会社で原料が存在しない時はメッセージを表示して保存処理を抜ける
                        if (!flgGenryoCk) {
                            App.ui.page.notifyAlert.message(
                                App.str.format(App.messages.app.AP0102, cd_kaisha, "原料コード", cd_genryo)
                            ).show();
                            App.ui.loading.close();
                            return;
                        }

                        //TODO: データの保存処理をここに記述します。
                        return $.ajax(App.ajax.webapi.post(page.urls.bunekichiNyuryoku, changeSets))
                            .then(function (result) {
                                //TODO: データの保存成功時の処理をここに記述します。
                                page.loadDialogs();
                                page.detailInput.options.mode = page.detailInput.mode.edit;
                                page.detailInput.bind(result);
                                chageFlg = false;
                                App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                                App.ui.loading.close();
                            }).fail(function (error) {

                                if (error.status === App.settings.base.conflictStatus) {
                                    // TODO: 同時実行エラー時の処理を行っています。
                                    // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                                    page.header.search(false);
                                    App.ui.page.notifyAlert.clear();
                                    App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
                                    return;
                                }
                                ////TODO: データの保存失敗時の処理をここに記述します。
                                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                                App.ui.loading.close();
                            });
                    });
                });
            }).fail(function () {
                App.ui.page.notifyAlert.message(App.messages.base.MS0006).show();
            }).always(function () {
                setTimeout(function () {
                    page.detailInput.element.find(":input:first").focus();
                }, 100);
                App.ui.loading.close();
            });
        };

        /**
        * 画面明細の対象行を更新します。
        */
        page.commands.editItem = function () {

            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            App.ui.loading.show();

            var sleep = 0;
            var condition = "Object.keys(page.values.isChangeRunning).length == 0";
            App.ui.wait(sleep, condition, 100)
            .then(function () {
                page.validateAll().then(function () {

                    if ($('#old_chk_kakunin').is(':checked') && $('#chk_kakunin').is(':checked')) {
                        var options = {
                            text: App.messages.app.AP0101
                        };
                        clearKakunin = true;
                    }
                    else {
                        var options = {
                            text: App.messages.app.AP0004
                        };
                    }
                    page.dialogs.confirmDialog.confirm(options)
                    .then(function () {
                        App.ui.loading.show();

                        if (clearKakunin) {
                            $('#chk_kakunin').prop('checked', false);
                            page.detailInput.element.findP("id_kakunin").val("");
                            page.detailInput.element.findP("nm_kakunin").val("");
                            page.detailInput.element.findP("dt_kakunin").val("");
                        }

                        //廃止にチェックがはいっているときは1で、チェックが入っていないときは0で保存する
                        if ($('#kbn_haishi').is(':checked')) {
                            kbnHaishi = 1;
                        }
                        else {
                            kbnHaishi = 0;
                        }

                        //確認日時、確認者IDが空白だったらnullにする
                        var id_kakunin = page.detailInput.element.findP("id_kakunin").val(),
                            dt_kakunin = page.detailInput.element.findP("dt_kakunin").val();
                        if (dt_kakunin == "") {
                            dt_kakunin = null;
                        }
                        if (id_kakunin == "") {
                            id_kakunin = null;
                        }


                        changeSets = {
                            ma_genryo: page.detailInput.data.getChangeSet(),
                            nm_genryo: page.detailInput.element.findP("nm_genryo").val(),
                            cd_busho: page.detailInput.element.findP("cd_busho").val(),
                            flg_haishi: kbnHaishi,
                            dt_kakunin: dt_kakunin,
                            id_kakunin: id_kakunin,
                            updateNmGenryo: updateNmGenryo,
                            chageFlg: page.values.hasChange,
                            chageFlgGenAOH: page.values.hasChangeGenOAH,
                            chageFlgGenKojoAOH: page.values.hasChangeGenKojoOAH
                        }

                        //TODO: データの更新処理をここに記述します。
                        return $.ajax(App.ajax.webapi.put(page.urls.bunekichiNyuryoku, changeSets))
                            .then(function (result) {
                                ////TODO: データの保存成功時の処理をここに記述します。                            
                                page.loadDialogs();
                                page.detailInput.options.mode = page.detailInput.mode.edit;
                                page.detailInput.bind(result);
                                chageFlg = false;
                                page.values.hasChange = false;
                                page.values.hasChangeGenOAH = false;
                                page.values.hasChangeGenKojoOAH = false;
                                App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                                page.detailInput.element.findP("cd_kaisha").val(result.cd_kaisha);
                                App.ui.loading.close();
                            }).fail(function (error) {
                                if (error.status === App.settings.base.conflictStatus) {
                                    // TODO: 同時実行エラー時の処理を行っています。
                                    // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                                    //page.header.search(false);
                                    page.initialize();
                                    App.ui.page.notifyAlert.clear();
                                    App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
                                    App.ui.loading.close();
                                    return;
                                }

                                //TODO: データの保存失敗時の処理をここに記述します。
                                if (error.status === App.settings.base.validationErrorStatus) {
                                    var errors = error.responseJSON;

                                    $.each(errors, function (index, err) {
                                        if (typeof (err) == "string") {
                                            App.ui.page.notifyAlert.remove("messages");
                                            App.ui.page.notifyAlert.message(err, "messages").show();
                                        } else {
                                            App.ui.page.notifyAlert.message(
                                                err.Message +
                                                (App.isUndefOrNull(err.InvalidationName) ? "" : err.Data[err.InvalidationName])
                                            ).show();
                                        }
                                    });

                                    App.ui.loading.close();
                                    return;
                                }

                                //TODO: データの保存失敗時の処理をここに記述します。
                                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                                App.ui.loading.close();
                            });
                    });
                });
            }).fail(function () {
                App.ui.page.notifyAlert.message(App.messages.base.MS0006).show();
            }).always(function () {
                setTimeout(function () {
                    page.detailInput.element.find(":input:first").focus();
                }, 100);
                App.ui.loading.close();
            });

        };

        /**
         * 画面明細の対象行を削除します。
         */
        page.commands.deleteItem = function () {

            var options = {
                text: App.messages.base.MS0003
            };

            page.dialogs.confirmDialog.confirm(options)
            .then(function () {

                App.ui.page.notifyWarn.clear();
                App.ui.page.notifyAlert.clear();
                App.ui.page.notifyInfo.clear();

                App.ui.loading.show();

                var element = page.detailInput.element,
                    id = element.attr("data-key"),
                    entity = page.detailInput.data.entry(id),
                    changeSets;

                page.detailInput.data.remove(entity);
                changeSets = page.detailInput.data.getChangeSet();

                //TODO: データの更新処理をここに記述します。
                $.ajax(App.ajax.webapi["delete"](/* TODO: データ更新サービスの URL を設定してください。, */  changeSets))
                .then(function (result) {

                    //TODO: データの保存成功時の処理をここに記述します。
                    page.detail.removeData(changeSets);
                    page.detailInput.previous(false);

                    App.ui.page.notifyInfo.message(App.messages.base.MS0008).show();
                }).fail(function (error) {

                    //TODO: データの保存失敗時の処理をここに記述します。
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

                }).always(function () {
                    setTimeout(function () {
                        page.header.element.find(":input:first").focus();
                    }, 100);
                    App.ui.loading.close();
                });
            });
        };

        /**
         * すべてのバリデーションを実行します。
         */
        page.validateAll = function () {

            var validations = [];

            validations.push(page.detailInput.validator.validate());

            //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。

            return App.async.all(validations);
        };

        /**
         * Windows閉じる際のイベントを定義します。
         * @return 文字列を返却した場合に確認メッセージが表示されます。
         */
        App.ui.page.onclose = function () {
            var detailInput,
                closeMessage = App.messages.base.exit;

            //項目が変更されていたらメッセージを表示する
            if (chageFlg) {
                return closeMessage;
            }
        };

        /**
        * 画面の初期化処理を行います。
        */
        page.initialize = function () {

            App.ui.loading.show();

            page.initializeControl();
            page.initializeControlEvent();

            //TODO: URLパラメーターで渡された値を取得し、保持する処理を記述します。
            //会社コード
            if (!App.isUndefOrNull(App.uri.queryStrings.cd_kaisha)) {
                page.values.cd_kaisha = App.uri.queryStrings.cd_kaisha;
            } else {
                page.values.cd_kaisha = "";
            }
            //部署コード
            if (!App.isUndefOrNull(App.uri.queryStrings.cd_busho)) {
                page.values.cd_busho = App.uri.queryStrings.cd_busho;
            } else {
                page.values.cd_busho = "";
            }
            //原料コード
            if (!App.isUndefOrNull(App.uri.queryStrings.cd_genryo)) {
                page.values.cd_genryo = App.uri.queryStrings.cd_genryo;
            } else {
                page.values.cd_genryo = "";
            }

            page.detailInput.initialize();

            flghaishiAble = false;
            chageFlg = false;                       //項目が変更されているかどうか判断するためのフラグ

            //TODO: ヘッダー/明細以外の初期化の処理を記述します。
            getKengenGamen(App.settings.app.id_gamen.bunsekiNyuryoku_AOH).then(function (results) {
                page.kengenGamen = results;

                $.each(results, function (index, item) {
                    if (item.id_kino == page.values.id_kino_edit && item.id_data == page.values.id_data_edit) {
                        flghaishiAble = true;
                    }
                });
            });

            page.loadMasterData().then(function (result) {

                return page.loadDialogs();
            }).then(function (result) {
                //TODO: 画面の初期化処理成功時の処理を記述します。
                return page.loadData();

            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

            }).always(function (result) {

                App.ui.loading.close();
            });

        };


        /**
         * 画面コントロールの初期化処理を行います。
         */
        page.initializeControl = function () {

            //TODO: 画面全体で利用するコントロールの初期化処理をここに記述します。
            $(".part").part();

        };

        /**
         * コントロールへのイベントの紐づけを行います。
         */
        page.initializeControlEvent = function () {

            //TODO: 画面全体で利用するコントロールのイベントの紐づけ処理をここに記述します。
            $("#addItem").on("click", page.commands.addItem);
            $("#editItem").on("click", page.commands.editItem);
            $("#deleteItem").on("click", page.commands.deleteItem);
            $("#chk_kakunin").on("click", checkKakunin);

        };

        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function () {
            //接続しているユーザーのID取得
            var userInfo = App.ui.page.user,
                id_user = userInfo.EmployeeCD;
            //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
            return $.ajax(App.ajax.odata.get(App.str.format(page.detailInput.urls.kaisha, App.settings.app.kaisha_AOH))).then(function (result) {

                var cd_kaisha = $(".detailInput").findP("cd_kaisha"),
                    nm_kaisha = $(".detailInput").findP("nm_kaisha");

                if (result.value.length > 0) {
                    cd_kaisha.val(result.value[0].cd_kaisha);
                    nm_kaisha.val(result.value[0].nm_kaisha);
                }

                return $.ajax(App.ajax.odata.get(App.str.format(page.detailInput.urls.tani, App.settings.app.cd_category.genryo_tani_AOH))).then(function (result) {
                    var tani = $(".detailInput").findP("cd_tani_master");

                    //容量単位
                    tani.children().remove();
                    App.ui.appendOptions(
                        tani,
                        "cd_literal",
                        "nm_literal",
                        result.value,
                        true
                    );
                });
            });
        };

        /**
         * 共有ダイアログのロード処理を実行します。
         */
        page.loadDialogs = function () {

            //flgKakuninChk = false;　//分析情報確認チェックボックスにチェックが入ったかを確認するフラグ
            clearKakunin = false;   //確認者と確認日時ををクリアにするかを確認するフラグ
            flgGenryoCk = true;     //原料コードがマスタに存在するかを確認するフラグ
            updateNmGenryo = false; //原料名が変更されたかを確認するフラグ

            //TODO: 共有ダイアログデータを取得し、画面にバインドする処理を記述します。
            return App.async.all({
                confirmDialog: $.get(page.urls.confirmDialog)
            }).then(function (result) {

                $("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;

            });
        };

        /**
        * 画面で処理の対象とするデータのロード処理を実行します。
        */
        page.loadData = function () {

            //会社コード、部署コード、原料コードがあれば編集モードにする 
            if (page.values.cd_kaisha && page.values.cd_busho && page.values.cd_genryo) {

                //TODO: 定型データを取得し、画面にバインドする処理を記述します。

                return $.ajax(App.ajax.webapi.get(page.urls.loadData, {
                                cd_kaisha: page.values.cd_kaisha
                                , cd_busho: page.values.cd_busho
                                , cd_genryo: page.values.cd_genryo
                })).then(function (result) {
                    //原料マスタ、原料工場マスタに存在しなかったら「原料が存在しません」と表示する
                    if (result.Count == 0) {
                        page.detailInput.options.mode = page.detailInput.mode.input;
                        page.detailInput.element.findP("cd_genryo").val(page.values.cd_genryo);
                        page.detailInput.element.findP("cd_kaisha").val(page.values.cd_kaisha);
                        page.detailInput.element.findP("cd_kaisha").prop("disabled", true);
                        page.detailInput.element.findP("nm_genryo").val("原料は存在しません");
                        flgGenryoCk = false;
                        page.detailInput.bind([]);
                    }
                    else {
                        page.detailInput.options.mode = page.detailInput.mode.edit;
                        result = result.Items[0];
                        page.detailInput.bind(result);
                    }
                }).fail(function (result) {
                    if (result.status === 404) {
                        page.detailInput.bind([]);
                        return App.async.success();
                    }
                });
            }
            else {
                //新規作成の時の処理
                page.detailInput.options.mode = page.detailInput.mode.input;

                var newData = {
                    cd_tani_master: App.settings.app.genryo_tani_AOH.Kg
                }

                page.detailInput.bind(newData);
            }

            chageFlg = false;
        };

        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.validations = {
            //TODO: 画面ヘッダーのバリデーションの定義を記述します。
        };

        ///**
        // * 画面ヘッダーの初期化処理を行います。
        // */
        page.header.initialize = function () {

            var element = $(".header");

            page.header.validator = element.validation(page.createValidator(page.header.options.validations));
            page.header.element = element;

            //TODO: 画面ヘッダーの初期化処理をここに記述します。
            //TODO: 画面ヘッダーで利用するコントロールのイベントの紐づけ処理をここに記述します。
            element.on("click", "#search", page.header.search);
            element.on("change", ":input", page.header.change);

        };

        /**
         * 画面ヘッダーの変更時処理を定義します。
         */
        page.header.change = function () {
            if ($("#nextsearch").hasClass("show-search")) {
                $("#nextsearch").removeClass("show-search").hide();
                App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
            }
            else if (page.detail.searchData) {
                // 保持検索データの消去
                page.detail.searchData = undefined;
                App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
            }
        };

        /**
         * 検索処理を定義します。
         */
        page.header.search = function (isLoading) {

            var deferred = $.Deferred(),
                query;

            page.header.validator.validate().done(function () {

                page.options.skip = 0;
                page.options.filter = page.header.createFilter();

                //TODO: データ取得サービスの URLとオプションを記述します。
                query = {
                    url: "TODO: 検索結果取得サービスの URL を設定してください。",
                    filter: page.options.filter,
                    orderby: "TODO: ソート対象の列名",
                    skip: page.options.skip,     // TODO:先頭からスキップするデータ数を指定します。
                    top: page.options.top,       // TODO:取得するデータ数を指定します。
                    inlinecount: "allpages"
                };

                if (isLoading) {
                    App.ui.loading.show();
                    App.ui.page.notifyAlert.clear();
                }

                return $.ajax(App.ajax.webapi.get(App.data.toODataFormat(query)))
                .done(function (result) {
                    // パーツ開閉の判断
                    if (page.detail.isClose) {
                        // 検索データの保持
                        page.detail.searchData = result;
                    } else {
                        // データバインド
                        page.detail.bind(result);
                    }
                    deferred.resolve();
                }).fail(function (error) {
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                    deferred.reject();
                }).always(function () {
                    if (isLoading) {
                        App.ui.loading.close();
                    }
                });
            });

            return deferred.promise();
        };

        /**
         * 検索条件のフィルターを定義します。
         */
        page.header.createFilter = function () {
            var criteria = page.header.element.form().data(),
                filters = [];

            /* TODO: 検索条件のフィルターを定義してください。*/
            //if (!App.isUndefOrNull(criteria.cd_shiharai) && criteria.cd_shiharai > 0) {
            //    filters.push("cd_shiharai eq " + criteria.cd_shiharai);
            //}
            //if (!App.isUndefOrNull(criteria.cd_torihiki) && criteria.cd_torihiki.length > 0) {
            //    filters.push("cd_torihiki eq " + criteria.cd_torihiki);
            //}
            //if (!App.isUndefOrNull(criteria.nm_hinmei) && criteria.nm_hinmei.length > 0) {
            //    filters.push("substringof('" + encodeURIComponent(criteria.nm_hinmei) + "', nm_hinmei) eq true");
            //}

            return filters.join(" and ");
        };

        //TODO: 以下の page.header の各宣言は画面仕様ごとにことなるため、不要の場合は削除してください。
<%--
        /**
        * 画面ヘッダーにある検索ダイアログ起動時の処理を定義します。
        */
        page.header.showSearchDialog = function () {
            page.dialogs.searchDialog.element.modal("show");
        };

        /**
         * 画面ヘッダーにある取引先コードに値を設定します。
         */
        page.header.setTorihiki = function (data) {
            page.header.element.findP("cd_torihiki").val(data.cd_torihiki).change();
            page.header.element.findP("nm_torihiki").text(data.nm_torihiki);
        };
--%>
        //TODO-END: 

        /**
         * 画面明細の初期化処理を行います。
         */
        page.detail.initialize = function () {

            var element = $(".detail"),
                table = element.find(".datatable"),
                datatable = table.dataTable({
                    height: 200,
                    resize: true,
                    //列固定横スクロールにする場合は、下記3行をコメント解除
                    //fixedColumn: true,                //列固定の指定
                    //fixedColumns: 3,                  //固定位置を指定（左端を0としてカウント）
                    //innerWidth: 1200,                 //可動列の合計幅を指定
                    onselect: page.detail.select
                });
            table = element.find(".datatable");         //列固定にした場合DOM要素が再作成されるため、変数を再取得

            page.detail.element = element;
            page.detail.dataTable = datatable;

            element.on("click", "#nextsearch", page.detail.nextsearch);
            element.on("click", "#add-item", page.detail.addNewItem);

            // 行選択時に利用するテーブルインデックスを指定します
            page.detail.fixedColumnIndex = element.find(".fix-columns").length;

            // 明細パートオープン時の処理を指定します
            element.find(".part").on("expanded.aw.part", function () {
                page.detail.isClose = false;
                if (page.detail.searchData) {
                    App.ui.loading.show();
                    setTimeout(function () {
                        page.detail.bind(page.detail.searchData);
                        page.detail.searchData = undefined;
                        App.ui.loading.close();
                    }, 5);
                };
            });

            // 明細パートクローズ時の処理を指定します
            element.find(".part").on("collapsed.aw.part", function () {
                page.detail.isClose = true;
            });

            //TODO: 画面明細の初期化処理をここに記述します。
            //TODO: 画面明細で利用するコントロールのイベントの紐づけ処理をここに記述します。
            table.on("click", ".edit-select", page.detail.showEditPart);
            //TODO: 行選択による詳細画面の遷移を行う際は、上の行をコメント化し下の行のコメントを解除します。
            //table.on("click", "tbody", page.detail.showEditPart);

        };

        /**
         * 次のレコードを検索する処理を定義します。
         */
        page.detail.nextsearch = function () {

            var query = {
                url: "TODO: 検索結果取得サービスの URL を設定してください。",
                filter: page.options.filter,
                orderby: "TODO: ソート対象の列名",
                skip: page.options.skip,     // TODO:先頭からスキップするデータ数を指定します。
                top: page.options.top,       // TODO:取得するデータ数を指定します。
                inlinecount: "allpages"
            };

            App.ui.loading.show();
            App.ui.page.notifyAlert.clear();

            return $.ajax(App.ajax.webapi.get(App.data.toODataFormat(query)))
            .done(function (result) {
                page.detail.bind(result);
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            }).always(function () {
                App.ui.loading.close();
            });

        };

        /**
      * 画面単票にデータを設定する際のオプションを定義します。
      */
        page.detailInput.options.bindOption = {
            appliers: {

                wt_kaniku: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.val("");
                    }
                    else {
                        element.val(App.num.format(Number(value), "#,#.000"));
                    }
                    return true;
                },

                wt_sato: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.val("");
                    }
                    else {
                        element.val(App.num.format(Number(value), "#,#.000"));
                    }
                    return true;
                },

                wt_kayosei_kokeibutsu: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.val("");
                    }
                    else {
                        element.val(App.num.format(Number(value), "0.000"));
                    }
                    return true;
                },

                wt_kanmido: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.val("");
                    }
                    else {
                        element.val(App.num.format(Number(value), "#,#.00"));
                    }
                    return true;
                },

                wt_hiju: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.val("");
                    }
                    else {
                        element.val(App.num.format(Number(value), "0.00"));
                    }
                    return true;
                },

                wt_1_kan: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.val("");
                    }
                    else {
                        element.val(App.num.format(Number(value), "#,#.000"));
                    }
                    return true;
                },

                wt_ireme: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.val("");
                    }
                    else {
                        element.val(App.num.format(Number(value), "#,#.000"));
                    }
                    return true;
                },

                tanka: function (value, element) {
                    if (App.isUndefOrNull(value) || value.toString() == "") {
                        element.val("");
                    }
                    else {
                        element.val(App.num.format(Number(value), "#,#.00"));
                    }
                    return true;
                },

                kbn_gum: function (value, element) {
                    if (App.isUndefOrNull(value) || value == 0) {
                        element.prop("checked", false);
                    }
                    else {
                        element.prop("checked", true);
                    }
                    return true;
                },

                kbn_sato: function (value, element) {
                    if (App.isUndefOrNull(value) || value == 0) {
                        element.prop("checked", false);
                    }
                    else {
                        element.prop("checked", true);
                    }
                    return true;
                },
            }
        };


        ///**
        // * 画面明細の各行にデータを設定する際のオプションを定義します。
        // */
        page.detail.options.bindOption = {
            appliers: {
            }
        };

        ///**
        // * 画面明細へのデータバインド処理を行います。
        // */
        page.detail.bind = function (data, isNewData) {
            var i, l, item, dataSet, dataCount, offsetHeight;

            dataCount = data.Count ? data.Count : 0;
            data = (data.Items) ? data.Items : data;

            if (page.options.skip === 0) {
                dataSet = App.ui.page.dataSet();
                page.detail.dataTable.dataTable("clear");
            } else {
                dataSet = page.detail.data;
            }
            page.detail.data = dataSet;

            page.detail.dataTable.dataTable("addRows", data, function (row, item) {
                (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
                row.form(page.detail.options.bindOption).bind(item);
                return row;
            }, true);

            page.options.skip += data.length;
            if (!isNewData) {
                page.detail.element.findP("data_count").text(page.options.skip);
                page.detail.element.findP("data_count_total").text(dataCount);
            }

            if (dataCount <= page.options.skip) {
                $("#nextsearch").hide();
            }
            else {
                $("#nextsearch").show();
            }

            if (page.options.skip >= App.settings.base.maxSearchDataCount) {
                App.ui.page.notifyInfo.message(App.messages.base.MS0011).show();
                $("#nextsearch").hide();
            }

            offsetHeight = $("#nextsearch").is(":visible") ? $("#nextsearch").addClass("show-search").outerHeight() : 0;
            page.detail.dataTable.dataTable("setAditionalOffset", offsetHeight);
            //TODO: 画面明細へのデータバインド処理をここに記述します。)


        };

        ///**
        // * 画面明細の一覧の行が選択された時の処理を行います。
        // */
        page.detail.select = function (e, row) {
            //TODO: 単一行を作成する場合は、下記２行を利用します。
            $($(row.element[page.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab.selected")).removeClass("selected").addClass("unselected");
            $(row.element[page.detail.fixedColumnIndex].querySelectorAll(".select-tab")).removeClass("unselected").addClass("selected");
            //TODO: 多段行を作成する場合は、下記２行を有効にし上記２行は削除します。
            //$($(row.element[page.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab-2lines.selected")).removeClass("selected").addClass("unselected");
            //$(row.element[page.detail.fixedColumnIndex].querySelectorAll(".select-tab-2lines")).removeClass("unselected").addClass("selected");

            //TODO: 選択行全体に背景色を付ける場合は以下のコメントアウトを解除します。
            //if (!App.isUndefOrNull(page.detail.selectedRow)) {
            //    page.detail.selectedRow.element.find(".selected-row").removeClass("selected-row");
            //}
            //row.element.find("tr").addClass("selected-row");
            //page.detail.selectedRow = row;
        };

        ///**
        // * 画面明細の一覧に新規データを追加します。
        // */
        page.detail.addNewItem = function () {
            page.detail.dataTable.dataTable("getFirstViewRow", function (row) {
                page.detail.firstViewRow = row;
            });

            //TODO:新規データおよび初期値を設定する処理を記述します。
            var newData = {
                //no_seq: page.values.no_seq
            };
            page.detailInput.options.mode = page.detailInput.mode.input;
            page.detailInput.show(newData);
        };

        ///**
        // * 画面明細の一覧から編集画面を表示します。
        // */
        page.detail.showEditPart = function (e) {
            var element = page.detail.element,
                target = $(e.target),
                row = target.closest("tbody"),
                id = row.attr("data-key"),
                entity = page.detail.data.entry(id);

            page.detail.dataTable.dataTable("getFirstViewRow", function (row) {
                page.detail.firstViewRow = row;
            });

            page.detailInput.options.mode = page.detailInput.mode.edit;
            page.detailInput.show(entity);
        };

        /**
         * 編集画面で更新された画面明細の行を最新化します。
         */
        page.detail.updateData = function (result) {

            //TODO: 更新されたデータのキーを取得します。
            //var updatedKey = result.updated[0].no_seq;

            // キーをもとに最新のデータを取得します。(検索結果取得サービスのurl, 更新行を取得する条件式)
            return $.ajax(App.ajax.webapi.get(/*TODO: 検索結果取得サービスの URL, */ /*TODO: 条件式 */"$filter=no_seq eq " + updatedKey))
                .then(function (result) {
                    var newData = result.Items[0];
                    if (!newData) {
                        return;
                    }

                    page.detail.dataTable.dataTable("each", function (row, index) {
                        var entity = page.detail.data.entry(row.element.attr("data-key"));
                        if (entity/*TODO: キー項目値が一致する条件を設定 //.no_seq*/ == updatedKey) {
                            // 対象データの各値を最新の値で更新します。
                            Object.keys(newData).forEach(function (key) {
                                if (key in entity) {
                                    entity[key] = newData[key];
                                }
                            });
                            // 行にデータを再バインドします。
                            row.element.find("[data-prop]").val("").text("");
                            row.element.form(page.detail.options.bindOption).bind(entity);
                            return true;    //datatable.eachのループを抜けるためのreturn true;
                        }
                    });
                });
        };

        /**
         * 編集画面で削除された画面明細の行を削除します。
         */
        page.detail.removeData = function (result) {

            //TODO: 削除されたデータのキーを取得します。
            //var removedKey = result.deleted[0].no_seq;

            // キーをもとに削除行を特定します。
            var deletedRow;
            page.detail.dataTable.dataTable("each", function (row, index) {
                var entity = page.detail.data.entry(row.element.attr("data-key"));
                if (entity/*TODO: キー項目値が一致する条件を設定 //.no_seq*/ == removedKey) {
                    deletedRow = row.element;
                    return true;    //datatable.eachのループを抜けるためのreturn true;
                }
            });

            page.detail.dataTable.dataTable("deleteRow", deletedRow, function (row) {
                var id = row.attr("data-key"),
                    newSelected;

                if (!App.isUndefOrNull(id)) {
                    var entity = page.detail.data.entry(id);
                    page.detail.data.remove(entity);
                }

                newSelected = row.next().not(".item-tmpl");
                if (!newSelected.length) {
                    newSelected = row.prev().not(".item-tmpl");
                }
                if (newSelected.length) {
                    //TODO: 単一行を作成する場合は、下記２行を利用します。
                    $($(newSelected[page.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab.selected")).removeClass("selected").addClass("unselected");
                    $(newSelected[page.detail.fixedColumnIndex].querySelectorAll(".select-tab")).removeClass("unselected").addClass("selected");
                    //TODO: 多段行を作成する場合は、下記２行を有効にし上記２行は削除します。
                    //$($(newSelected[page.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab-2lines.selected")).removeClass("selected").addClass("unselected");
                    //$(newSelected[page.detail.fixedColumnIndex].querySelectorAll(".select-tab-2lines")).removeClass("unselected").addClass("selected");

                    //TODO: 選択行全体に背景色を付ける場合は以下のコメントアウトを解除します。
                    //if (!App.isUndefOrNull(page.detail.selectedRow)) {
                    //    page.detail.selectedRow.element.find("tr").removeClass("selected-row");
                    //}
                    //newSelected.find("tr").addClass("selected-row");
                    //page.detail.dataTable.dataTable("getRow", newSelected, function (row) {
                    //    page.detail.selectedRow = row;
                    //});
                }
            });

            page.detail.element.findP("data_count").text(parseFloat(page.detail.element.findP("data_count").text()) - 1);
            page.detail.element.findP("data_count_total").text(parseFloat(page.detail.element.findP("data_count_total").text()) - 1);
        };

        //TODO: 以下の page.detail の各宣言は画面仕様ごとにことなるため、不要の場合は削除してください。


        //TODO-END: 

        /**
         * 編集画面のバリデーションを定義します。
         */
        page.detailInput.options.validations = {
            //TODO: 新規追加画面のバリデーションの定義を記述します。
            cd_kaisha: {
                rules: {
                    required: true
                },
                options: {
                    name: '会社'
                },
                messages: {
                    required: App.messages.base.required,
                }
            },
            nm_genryo: {
                rules: {
                    required: true
                },
                options: {
                    name: '原料名'
                },
                messages: {
                    required: App.messages.base.required
                }
            },
            wt_kaniku: {
                rules: {
                    number: true,
                    range: [0, 999.999],
                    pointlength: [3, 3]
                },
                options: {
                    name: '果肉量',
                },
                messages: {
                    number: App.messages.base.number,
                    range: App.messages.base.range,
                    pointlength: App.messages.base.pointlength
                }
            },
            wt_sato: {
                rules: {
                    number: true,
                    range: [0, 999.999],
                    pointlength: [3, 3]
                },
                options: {
                    name: '砂糖量',
                },
                messages: {
                    number: App.messages.base.number,
                    range: App.messages.base.range,
                    pointlength: App.messages.base.pointlength
                }
            },
            wt_kayosei_kokeibutsu: {
                rules: {
                    number: true,
                    range: [0, 9.999],
                    pointlength: [1, 3]

                },
                options: {
                    name: '可溶性固形物',
                },
                messages: {
                    number: App.messages.base.number,
                    range: App.messages.base.range,
                    pointlength: App.messages.base.pointlength
                }
            },
            wt_kanmido: {
                rules: {
                    number: true,
                    range: [0, 999.99],
                    pointlength: [3, 2]
                },
                options: {
                    name: '甘味度',
                },
                messages: {
                    number: App.messages.base.number,
                    range: App.messages.base.range,
                    pointlength: App.messages.base.pointlength
                }
            },
            wt_hiju: {
                rules: {
                    number: true,
                    range: [0, 9.99],
                    pointlength: [1, 2]
                },
                options: {
                    name: '比重',
                },
                messages: {
                    number: App.messages.base.number,
                    range: App.messages.base.range,
                    pointlength: App.messages.base.pointlength
                }
            },
            wt_ireme: {
                rules: {
                    number: true,
                    range: [1, 999.999],
                    pointlength: [3, 3]
                },
                options: {
                    name: '入れ目量',
                },
                messages: {
                    number: App.messages.base.number,
                    range: App.messages.base.range,
                    pointlength: App.messages.base.pointlength
                }
            },
            wt_1_kan: {
                rules: {
                    number: true,
                    range: [1, 999.999],
                    pointlength: [3, 3]
                },
                options: {
                    name: '1缶重量',
                },
                messages: {
                    number: App.messages.base.number,
                    range: App.messages.base.range,
                    pointlength: App.messages.base.pointlength
                }
            },
            cd_kakutei: {
                rules: {
                    number: true
                },
                options: {
                    name: '確定コード',
                },
                messages: {
                    number: App.messages.base.number
                }
            }
        };

        /**
         * 編集画面の初期化処理を行います。
         */
        page.detailInput.initialize = function () {

            var element = $(".detailInput");

            page.detailInput.element = element;
            page.detailInput.validator = element.validation(page.createValidator(page.detailInput.options.validations));
            element.on("change", ":input", page.detailInput.change);
            element.on("click", ".previous", page.detailInput.previous);
        };


        /**
         * 編集画面の画面制御を行います。
         */
        page.detailInput.seigyo = function () {

            var element = $(".detailInput"),
                mode = page.detailInput.options.mode;

            element.find(":input[data-role='date']").datepicker({ dateFormat: "yy/mm/dd" });
            element.find(":input[data-role='date']").on("keyup", App.data.addSlashForDateString);
            page.detailInput.element.findP("cd_kaisha").change();

            //新規モード時・データが存在しない時の画面制御の画面制御
            if (mode == page.detailInput.mode.input) {

                //廃止権限がある場合は廃止区を活性化する
                if (flghaishiAble == true) {
                    $("#kbn_haishi").prop("disabled", false);
                }
                else {
                    $("#kbn_haishi").prop('disabled', true);
                }

                //ボタンの表示
                $("#addItem").show();
                $("#editItem").hide();
            }

                //編集モード時の画面制御
            else {
                //原料コードの最初の１文字がBの場合（画面から作成したデータ）と数字のみの場合（FPから伝送されたデータ）で画面制御を分ける
                var cd_genryo = page.detailInput.element.findP("cd_genryo").val();
                chageFlg = false;
                if (cd_genryo != "") {
                    //原料コードの最初の１文字を取得する
                    var cd_genryo_index = cd_genryo.substring(0, 1);
                    //原料コードの最初の１文字がBの場合（画面から作成したデータ）の画面制御
                    if (cd_genryo_index == "B") {
                        page.detailInput.element.findP("nm_genryo").prop("disabled", false);
                        page.detailInput.element.findP("cd_kakutei").prop("disabled", false);
                        //廃止権限がある場合は廃止区を活性化する
                        if (flghaishiAble == true) {
                            $("#kbn_haishi").prop("disabled", false);
                        }
                    }
                        //原料コードが数字のみ（FPから伝送されたデータ）の画面制御
                    else {
                        page.detailInput.element.findP("nm_genryo").prop("disabled", true);
                        page.detailInput.element.findP("cd_kakutei").prop("disabled", true);
                        $("#kbn_haishi").prop("disabled", true);
                    }
                }
                //確認日時、確認者のデータがあるときは、「分析情報確認」チェックボックス、「DB上の分析情報確認」チェックボックスにチェックをつける
                var dt_kakunin = page.detailInput.element.findP("dt_kakunin").val(),
                    id_kakunin = page.detailInput.element.findP("id_kakunin").val(),
                    chkKakunin = $('#chk_kakunin'),
                    oldChkKakunin = $('#old_chk_kakunin'),
                    chkKakunin = $('#chk_kakunin');

                if (dt_kakunin != "" || id_kakunin != "") {
                    chkKakunin.prop('checked', true);
                    oldChkKakunin.prop('checked', true);
                }
                else {
                    chkKakunin.prop('checked', false);
                    oldChkKakunin.prop('checked', false);
                }
                page.detailInput.element.findP("cd_kaisha").prop("disabled", true);
                //ボタンの表示
                $("#editItem").show();
                $("#editItem").prop("disabled", true);
                $("#addItem").hide();
            }

            return App.async.success();
        }

        page.detailInput.show = function (data) {

            var element = page.detailInput.element;

            //TODO:項目をクリアする処理をここに記述します。
            element.find(":input:not([type='checkbox']):not([type='radio'])").val("");
            element.find("input[type='checkbox']").prop('checked', false);


            //TODO:radioボタンの初期表示は、画面要件に合わせて記述します。

            //TODO:入力項目以外の個別項目のクリアはここで記述

            //TODO:データバインド処理をここに記述します。

            if (page.detailInput.options.mode === page.detailInput.mode.input) {

                //page.detailInput.bindTantoKaisha([], false);

                $("#addItem").show();
                $("#editItem").hide();

                //$("#deleteItem").hide();
                //TODO:キー項目が画面入力の場合、新規モード時は修正可とします。
                //element.findP("no_seq").prop("readonly", false).prop("tabindex", false);
                //element.validation().validate({
                //    state: {
                //        suppressMessage: true
                //    }
                //});
            } else {
                //page.detailInput.dataTantoOriginal = $.extend(true, [], data);

                //page.detailInput.bindTantoKaisha(data, false);

                $("#addItem").hide();
                $("#editItem").show();
                //$("#deleteItem").show();
                //TODO:キー項目が画面入力の場合、編集モード時は修正不可とします。
                //element.findP("no_seq").prop("readonly", true).prop("tabindex", -1);
                //element.validation().validate();
            }

            //TODO:パーツの表示・非表示処理を記述します。
            page.header.element.hide();
            page.detail.element.hide();

            element.show();
            element.find(":input:not([readonly]):first").focus();
        };

        /**
         * 画面明細に戻ります。
         */
        page.detailInput.previous = function (isConfirm) {
            var closeMessage = {},
                previousPage = function () {
                    //TODO:パーツの表示・非表示処理を記述します。
                    page.header.element.show();
                    page.detail.element.show();
                    page.detailInput.element.hide();
                    page.detailInput.data = undefined;

                    if (!App.isUndefOrNull(page.detail.firstViewRow)) {
                        page.detail.dataTable.dataTable("scrollTop", page.detail.firstViewRow, function () { });
                    }

                    $("#addItem").hide();
                    $("#editItem").prop("disabled", true).hide();
                    //$("#deleteItem").hide();

                    App.ui.page.notifyAlert.clear();
                };

            if (page.detailInput.data.isChanged()) {
                closeMessage.text = App.messages.base.exit;
            }

            if (isConfirm && closeMessage.text) {
                page.dialogs.confirmDialog.confirm(closeMessage)
                .then(function () {
                    previousPage();
                });
            } else {
                previousPage();
            };
        };

        /**
         * 編集画面へのデータバインド処理を行います。
         */
        page.detailInput.bind = function (data) {

            var setData = {},
                dataSet = App.ui.page.dataSet();

            setData = App.isUndefOrNull(data) ? data : data.constructor();

            for (var attr in data) {
                if (data.hasOwnProperty(attr)) setData[attr] = data[attr];
            }

            //TODO:画面モードによる処理をここに記述します。
            if (page.detailInput.options.mode === page.detailInput.mode.input) {
                page.detailInput.data = dataSet;
                page.detailInput.data.add.bind(dataSet)(setData);
            } else {
                page.detailInput.data = dataSet;
                page.detailInput.data.attach.bind(dataSet)(setData);
            }

            page.detailInput.element.form(page.detailInput.options.bindOption).bind(setData);

            if (setData.kbn_haishi == 1) {
                $('#kbn_haishi').prop('checked', true);
            }
            else {
                $('#kbn_haishi').prop('checked', false);
            }

            page.detailInput.seigyo();

            if (page.values.firstChange) {
                page.waitFirstChange();
            }
            
        };

        //wait change function in seigyo done
        page.waitFirstChange = function () {
            var sleep = 0;
            var condition = "Object.keys(page.values.isChangeRunning).length == 0";
            App.ui.wait(sleep, condition, 100).then(function () {
                page.values.firstChange = false;
            });
        };

        /**
         * 編集画面にある入力項目の変更イベントの処理を行います。
         */
        page.detailInput.change = function (e) {

            var element = page.detailInput.element,
                target = $(e.target),
                id = element.attr("data-key"),
                property = target.attr("data-prop"),
                data = element.form().data(),
                entity = page.detailInput.data.entry(id);

            //原料名が変更されていたら原料工場マスタを更新するのでtrueにする
            if (property == "nm_genryo") {
                updateNmGenryo = true;
            };

            if (property == "cd_kakutei") {
                var cd_kakutei = element.findP("cd_kakutei").val();
                element.findP("cd_kakutei").val(("000000" + cd_kakutei).slice(-6));
            };

            if (property != "dt_kakunin" && property != "id_kakunin" && property != "chk_kakunin" && !page.values.firstChange
                && (property == "hyojian" || property == "tenkabutu" || property == "kbn_haishi" || property == "cd_kakutei")) {
                page.values.hasChange = true;
            }

            if (property == "wt_kaniku" || property == "wt_sato" || property == "wt_kanmido" || property == "wt_hiju" || property == "cd_tani_master"
                || property == "wt_ireme" || property == "wt_1_kan" || property == "kbn_sato" || property == "kbn_gum" || property == "memo"
                && !page.values.firstChange) {
                page.values.hasChangeGenOAH = true;
            }

            if (property == "wt_kayosei_kokeibutsu" && !page.values.firstChange) {
                page.values.hasChangeGenKojoOAH = true;
            }

            if (property == "kbn_sato" || property == "kbn_gum") {
                data[property] = target[0].checked;
            }

            var state = page.detailInput.data.entries[entity.__id].state;
            // 入力項目が削除済みの場合、処理を実行しない
            if (!App.isUndefOrNull(state)
                && state === App.ui.page.dataSet.status.Deleted) {
                App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
                return;
            }

            page.values.isChangeRunning[property] = true;
            chageFlg = true;

            setTimeout(function () { 
                page.detailInput.validator.validate({
                    targets: target
                }).then(function () {
                    entity[property] = data[property];
                    page.detailInput.data.update(entity);
                    if ($("#editItem").is(":visible:disabled")) {
                        $("#editItem").prop("disabled", page.values.firstChange);
                    }
                }).always(function () {
                    delete page.values.isChangeRunning[property];
                });
            }, 10)
        };

        //TODO: 以下の page.detailInput の各宣言は画面仕様ごとにことなるため、不要の場合は削除してください。
<%--
        /**
        * 編集追加画面にある検索ダイアログ起動時の処理を定義します。
        */
        page.detailInput.showSearchDialog = function () {
            page.dialogs.searchDialog.element.modal("show");
        };

        /**
         * 編集追加画面にある取引先コードに値を設定します。
         */
        page.detailInput.setTorihiki = function (data) {
            page.detailInput.element.findP("cd_torihiki").val(data.cd_torihiki).change();
            page.detailInput.element.findP("nm_torihiki").text(data.nm_torihiki);
        };
--%>
        //TODO-END: 


        /**
         * jQuery イベントで、ページの読み込み処理を定義します。
         */
        $(function () {
            // ページの初期化処理を呼び出します。
            page.initialize();

        });

        /**
   * 一覧のヘッダ部のckeckbox(ALLチェック用）をクリックした際の処理を実行します。（複数セレクト用）
   */
        checkKakunin = function (e) {
            var target = $(e.target);
            //接続しているユーザーのID取得
            var userInfo = App.ui.page.user,
                nm_tanto = userInfo.Name,
                cd_tanto = userInfo.EmployeeCD,
                today = new Date(),
            　　today = App.data.getDateTimeString(today, true),
                chkKakunin = $('#chk_kakunin');

            if (chkKakunin.is(':checked')) {
                page.detailInput.element.findP("dt_kakunin").val(today);
                page.detailInput.element.findP("nm_kakunin").val(nm_tanto);
                page.detailInput.element.findP("id_kakunin").val(cd_tanto);
                page.detailInput.element.findP("dt_kakunin").change();
                page.detailInput.element.findP("id_kakunin").change();
            }
            else {
                page.detailInput.element.findP("dt_kakunin").val("");
                page.detailInput.element.findP("nm_kakunin").val("");
                page.detailInput.element.findP("id_kakunin").val("");
                page.detailInput.element.findP("dt_kakunin").change();
                page.detailInput.element.findP("id_kakunin").change();
            }
        };


    </script>

</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">

    <div class="content-wrap">
        <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
        <div class="header"></div>

        <!--TODO: 明細を定義するHTMLをここに記述します。-->
        <div class="detail"></div>

        <!--TODO: 単票入力・編集画面を定義するHTMLをここに記述します。-->
        <div class="detailInput textfont">

            <div title="分析値入力">
                <div class="row">
                    <%--会社--%>
                    <div class="control-label col-xs-1">
                        <label>
                            会社
                            <i class="caution">*</i>
                        </label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="text" data-prop="cd_kaisha" style="display: none;" />
                        <input type="text" data-prop="nm_kaisha" disabled="disabled" />
                        <%--部署コード--%>
                        <input type="text" data-prop="cd_busho" hidden />
                    </div>
                    <%--原料コード--%>
                    <div class="control-label col-xs-1">
                        <label>原料コード</label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="text" data-prop="cd_genryo" disabled="disabled" />
                    </div>
                    <%--原料名--%>
                    <div class="control-label col-xs-1">
                        <label>
                            原料名
                            <i class="caution">*</i>
                        </label>
                    </div>
                    <%--<div class="control col-xs-2 genryokojo-area">--%>
                    <div class="control col-xs-3">
                        <input type="text" data-prop="nm_genryo" maxlength="60" />
                    </div>
                    <div class="control col-xs-2">
                        <span style="white-space: nowrap;"></span>
                    </div>
                </div>
                <div class="row">
                    <%--果肉量--%>
                    <div class="control-label col-xs-1">
                        <label>果肉量</label>
                    </div>
                    <div class="control col-xs-1">
                        <input type="tel" data-prop="wt_kaniku" class="number-right" maxlength="7" data-number-format="0.000" />
                    </div>
                    <%--砂糖量--%>
                    <div class="control-label col-xs-1">
                        <label>砂糖量</label>
                    </div>
                    <div class="control col-xs-1">
                        <input type="tel" data-prop="wt_sato" class="number-right" maxlength="7" data-number-format="0.000" />
                    </div>
                    <%--可溶性固形物--%>
                    <div class="control-label col-xs-1">
                        <label>可溶性固形物</label>
                    </div>
                    <div class="control col-xs-1">
                        <input type="tel" data-prop="wt_kayosei_kokeibutsu" class="number-right" maxlength="5" data-number-format="0.000" />
                    </div>
                    <%--甘味度--%>
                    <div class="control-label col-xs-1">
                        <label>甘味度</label>
                    </div>
                    <div class="control col-xs-1">
                        <input type="tel" data-prop="wt_kanmido" class="number-right" maxlength="6" data-number-format="0.00" />
                    </div>
                    <%--比重--%>
                    <div class="control-label col-xs-1">
                        <label>比重</label>
                    </div>
                    <div class="control col-xs-1">
                        <input type="tel" data-prop="wt_hiju" class="number-right" maxlength="4" data-number-format="0.00" />
                    </div>
                    <div class="control col-xs-2">
                        <span style="white-space: nowrap;"></span>
                    </div>
                </div>
                <div class="row">
                    <%--入れ目量--%>
                    <div class="control-label col-xs-1">
                        <label>入れ目量</label>
                    </div>
                    <div class="control col-xs-1">
                        <input type="tel" data-prop="wt_ireme" class="number-right" maxlength="7" data-number-format="0.000" />
                    </div>
                    <div class="control-label col-xs-1">
                        <label>1缶重量</label>
                    </div>
                    <div class="control col-xs-1">
                        <input type="tel" data-prop="wt_1_kan" class="number-right" maxlength="7" data-number-format="0.000" />
                    </div>
                    <div class="control-label col-xs-1">
                        <label>砂糖・甘味料</label>
                    </div>
                    <div class="control col-xs-1">
                        <input type="checkbox" data-prop="kbn_sato" />
                    </div>
                    <div class="control-label col-xs-1">
                        <label>ペクチン・ガム類</label>
                    </div>
                    <div class="control col-xs-1">
                        <input type="checkbox" data-prop="kbn_gum" />
                    </div>
                    <div class="control col-xs-4">
                        <span style="white-space: nowrap;"></span>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>単位</label>
                    </div>
                    <div class="control col-xs-1">
                        <select data-prop="cd_tani_master"></select>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>単価</label>
                    </div>
                    <div class="control col-xs-1">
                        <input type="tel" data-prop="tanka" class="number-right" disabled="disabled" />
                    </div>
                    <div class="control col-xs-8">
                        <span style="white-space: nowrap;"></span>
                    </div>
                </div>
                <div class="row textarea-height">
                    <%--表示案--%>
                    <div class="control-label col-xs-1 textarea-height">
                        <label>表示案</label>
                    </div>
                    <div class="control col-xs-11 textarea-height">
                        <textarea data-prop="hyojian" wrap="soft" class="textareabox" maxlength="200"></textarea>
                    </div>
                </div>
                <div class="row textarea-height">
                    <%--表示案Ｇ-Ｍｅｒ--%>
                    <div class="control-label col-xs-1 textarea-height">
                        <label>表示案</label>
                        <label>Ｇ-Ｍｅｒ</label>
                    </div>
                    <div class="control col-xs-11 textarea-height">
                        <textarea data-prop="RMLABELSAMPLE" wrap="soft" class="textareabox" disabled="disabled"></textarea>
                    </div>
                </div>
                <div class="row textarea-height">
                    <%--添加物--%>
                    <div class="control-label col-xs-1 textarea-height">
                        <label>添加物</label>
                    </div>
                    <div class="control col-xs-11  textarea-height">
                        <textarea data-prop="tenkabutu" wrap="soft" class="textareabox" maxlength="200"></textarea>
                    </div>
                </div>
                <div class="row textarea-height">
                    <%--添加物Ｇ-Ｍｅｒ(表示要)--%>
                    <div class="control-label col-xs-1  textarea-height">
                        <label>添加物</label>
                        <label>Ｇ-Ｍｅｒ(表示要)</label>
                    </div>
                    <div class="control col-xs-11  textarea-height">
                        <textarea data-prop="AMS_YO" wrap="soft" class="textareabox" disabled="disabled"></textarea>
                        <%--添加物表示要否--%>
                        <input type="text" data-prop="AMADISP_YN" style="display: none;" />
                    </div>
                </div>
                <div class="row textarea-height">
                    <%--添加物Ｇ-Ｍｅｒ(表示不要)--%>
                    <div class="control-label col-xs-1  textarea-height">
                        <label>添加物</label>
                        <label>Ｇ-Ｍｅｒ(表示不要)</label>
                    </div>
                    <div class="control col-xs-11  textarea-height">
                        <textarea data-prop="AMS_FUYO" wrap="soft" class="textareabox" disabled="disabled"></textarea>
                    </div>
                </div>
                <div class="row textarea-height">
                    <%--アレルギー情報Ｇ-Ｍｅｒ(表示要)--%>
                    <div class="control-label col-xs-1  textarea-height">
                        <label>アレルギー情報</label>
                        <label>Ｇ-Ｍｅｒ(表示要)</label>
                    </div>
                    <div class="control col-xs-11  textarea-height">
                        <textarea data-prop="ALG_ALR" wrap="soft" class="textareabox" disabled="disabled"></textarea>
                        <%--アレルギー表示要否--%>
                        <%-- <input type="text" data-prop ="ALGDISP_YN" style="display: none;" />--%>
                    </div>
                </div>
                <div class="row textarea-height">
                    <%--アレルギー情報Ｇ-Ｍｅｒ(表示不要)--%>
                    <div class="control-label col-xs-1  textarea-height">
                        <label>アレルギー情報</label>
                        <label>Ｇ-Ｍｅｒ(表示不要)</label>
                    </div>
                    <div class="control col-xs-11  textarea-height">
                        <textarea data-prop="ALG_FUYO" wrap="soft" class="textareabox" disabled="disabled"></textarea>
                    </div>
                </div>
                <div class="row textarea-height">
                    <%--メモ--%>
                    <div class="control-label col-xs-1  textarea-height">
                        <label>メモ</label>
                    </div>
                    <div class="control col-xs-11  textarea-height">
                        <textarea data-prop="memo" wrap="soft" class="textareabox" maxlength="1000"></textarea>
                    </div>
                </div>
                <div class="row">
                    <%--入力日--%>
                    <div class="control-label col-xs-1">
                        <label>入力日</label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="text" data-prop="dt_nyuryoku" disabled="disabled" />
                    </div>
                    <%--入力者--%>
                    <div class="control-label col-xs-1">
                        <label>入力者</label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="text" data-prop="nm_nyuryoku" disabled="disabled" />
                    </div>
                    <div class="control col-xs-6">
                        <span style="white-space: nowrap;"></span>
                    </div>
                </div>
                <div class="row">
                    <%--分析情報確認--%>
                    <div class="control-label col-xs-1">
                        <label>分析情報確認</label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="checkbox" data-prop="chk_kakunin" id="chk_kakunin" value="true" />
                        <%--DB上の分析情報確認--%>
                        <%--保存時に確認済データかどうかを判断するために使用--%>
                        <input type="checkbox" data-prop="old_chk_kakunin" id="old_chk_kakunin" value="true" hidden />
                    </div>
                    <div class="control col-xs-9">
                        <span style="white-space: nowrap;"></span>
                    </div>
                </div>
                <div class="row">
                    <%--確認日時--%>
                    <div class="control-label col-xs-1">
                        <label>確認日時</label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="text" data-prop="dt_kakunin" disabled="disabled" />
                    </div>
                    <%--確認者--%>
                    <div class="control-label col-xs-1">
                        <label>確認者</label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="text" data-prop="nm_kakunin" disabled="disabled" />
                        <%--確認者コード--%>
                        <input type="text" data-prop="id_kakunin" disabled="disabled" hidden />
                    </div>
                    <div class="control col-xs-6">
                        <span style="white-space: nowrap;"></span>
                    </div>
                </div>
                <div class="row">
                    <%--廃止区--%>
                    <div class="control-label col-xs-1">
                        <label>廃止区</label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="checkbox" data-prop="kbn_haishi" id="kbn_haishi" value="true" disabled="disabled" />
                    </div>
                    <%--確定コード--%>
                    <div class="control-label col-xs-1">
                        <label>確定コード</label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="tel" data-prop="cd_kakutei" maxlength="11" />
                    </div>
                    <div class="control col-xs-6">
                        <span style="white-space: nowrap;"></span>
                    </div>
                </div>
                <div class="row">
                    <%--単価更新日--%>
                    <div class="control-label col-xs-1">
                        <label>単価更新日</label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="text" data-prop="dt_tanka_koshin" disabled="disabled" />
                    </div>
                    <%--単価更新者--%>
                    <div class="control-label col-xs-1">
                        <label>単価更新者</label>
                    </div>
                    <div class="control col-xs-2">
                        <input type="text" data-prop="nm_tanka_koshin" disabled="disabled" />
                    </div>
                    <div class="control col-xs-6">
                        <span style="white-space: nowrap;"></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</asp:Content>

<asp:Content ID="FooterContent" ContentPlaceHolderID="FooterContent" runat="server">
    <div class="command-left">
        <!--TODO: コマンドボタン（左寄せ配置）を定義するHTMLをここに記述します。-->
    </div>
    <div class="command">
        <!--TODO: コマンドボタン（右寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" id="addItem" class="btn btn-sm btn-primary" style="display: none;">保存</button>
        <button type="button" id="editItem" class="btn btn-sm btn-primary" disabled="disabled" style="display: none;">保存</button>
        <%--     <button type="button" id="deleteItem" class="btn btn-sm btn-default" style="display:none;">削除</button>--%>
    </div>

</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>
