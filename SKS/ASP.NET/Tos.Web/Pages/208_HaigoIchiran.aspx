<%@ Page Title="208_" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="208_HaigoIchiran.aspx.cs" Inherits="Tos.Web.Pages.HaigoIchiran" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchInputTable(Ver2.1)】 Template--%>

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

        input[type="radio"], input[type="checkbox"] {
            vertical-align: middle !important;
        }

        /*label {
            padding: 2px!important;
        }*/

        .red-color {
            color: red;
        }

        .btn-next-search {
            width: 200px;
        }
    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("_208_HaigoIchiran", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: App.settings.base.maxInputDataCount,   // TODO:取得するデータ数を指定します。
                filter: ""
            },
            values: {
                isChangeRunning: {},
                is_kirikae_hyoji_title: "表示用配合一覧",
                is_kirikae_foodprocs_title: "配合一覧",
                m_kirikae: null,
                su_code_standard: 0,
                isNewCheckRadio: true,
                cd_kaisha: "",
                cd_kojyo: "",
                M_HaigoToroku: {
                    create: 1,
                    shosai: 2,
                    copy: 3
                },
                id_kino_search: 10,
                id_kino_ippan: 20,
                id_kino_hon_seizo: 30,
                id_data_jisshi_kano: 9,
                isRoleJisshiKano: false
            },
            urls: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
                HinmeiKaihatsuDialog: "../Pages/Dialogs/710_HinmeiKaihatsu_Dialog.aspx",
                seihinKensakuDialog: "../Pages/Dialogs/703_SeihinKensaku_Dialog.aspx",
                haigoTorokuKojyoBumon: "../Pages/209_HaigoTorokuKojyoBumon.aspx",
                tenpuBunsho: "../Pages/211_TenpuBunsho.aspx",
                mainMenu: "MainMenu.aspx",
                haigoCopy: "../Pages/Dialogs/705_HaigoCopy_Dialog.aspx",
            },
            header: {
                options: {
                    partone: {},
                    parttwo: {},
                    partthree: {}
                },
                values: {
                    kbn_hin: null
                },
                urls: {
                    vw_no_seiho_shurui: "../Services/ShisaQuickService.svc/vw_no_seiho_shurui?$orderby=cd_hin_syurui,no_sort",
                    vw_kaisha_kojyo: "../Services/ShisaQuickService.svc/vw_kaisha_kojyo?$filter=cd_kaisha eq {0}{1}&$orderby=cd_kaisha,cd_kojyo",
                    ma_kaisha: "../Services/ShisaQuickService.svc/ma_kaisha?{0}&$orderby=cd_kaisha",
                    ma_bunrui: "../api/Common/GetMaBunrui",
                    kbn_hin: "../api/Common/GetShinaKubun",
                    getHaigo: "../api/Common/GetHaigo",
                    getSeihin: "../api/Common/GetSeihin",
                    getGenryo: "../api/Common/GetGenryo",
                    search: "../api/HaigoIchiran"
                }
            },
            detail: {
                options: {
                    downloadCSV: "../api/_208_HaigoIchiranDownloadCSV"
                },
                values: {}
            },
            dialogs: {},
            commands: {
                urls: {
                    checkSeiho: "../api/HaigoIchiran/CheckSeiho",
                    deleteData: "../api/HaigoIchiran/DeleteData"
                }
            }
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
         * データの保存処理を実行します。
         */
        page.commands.save = function () {

            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            App.ui.loading.show();

            var sleep = 0;
            var condition = "Object.keys(page.values.isChangeRunning).length == 0";
            App.ui.wait(sleep, condition, 100)
            .then(function () {
                page.validateAll().then(function () {

                    var changeSets = page.detail.data.getChangeSet();
                    //TODO: データの保存処理をここに記述します。
                    return $.ajax(App.ajax.webapi.post(/* TODO: データ保存サービスの URL を設定してください。, */ changeSets))
                        .then(function (result) {

                            //TODO: データの保存成功時の処理をここに記述します。

                            //最後に再度データを取得しなおします。
                            return App.async.all([page.header.search(false)]);
                        }).then(function () {
                            App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                        }).fail(function (error) {
                            if (error.status === App.settings.base.conflictStatus) {
                                // TODO: 同時実行エラー時の処理を行っています。
                                // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                                page.header.search(false);
                                App.ui.page.notifyAlert.clear();
                                App.ui.page.notifyAlert.message(App.messages.base.MS0009).show();
                                return;
                            }

                            //TODO: データの保存失敗時の処理をここに記述します。
                            if (error.status === App.settings.base.validationErrorStatus) {
                                var errors = error.responseJSON;
                                $.each(errors, function (index, err) {
                                    App.ui.page.notifyAlert.message(
                                        err.Message +
                                        (App.isUndefOrNull(err.InvalidationName) ? "" : err.Data[err.InvalidationName])
                                    ).show();
                                });
                                return;
                            }

                            App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

                        });
                });
            }).fail(function () {
                App.ui.page.notifyAlert.message(App.messages.base.MS0006).show();
            }).always(function () {
                setTimeout(function () {
                    page.header.element.find(":input:first").focus();
                }, 100);
                App.ui.loading.close();
            });
        };

        /**
         * Clear condition。
         */
        page.commands.ClearCondition = function () {
            var element = page.header.element;

            $(element.find("[name='haigou_seihin']")[0]).prop("checked", true);
            $(element.find("[name='jyotai']")[0]).prop("checked", true);

            element.findP("jkn_sort").val(element.find("[data-prop='jkn_sort'] option:eq(0)").val());
            page.loadMasterData().then(function () {
                $(element.findP("kansakujyoho")[0]).prop("checked", true).change();
            });

            page.header.clearHaigo();
            page.header.clearTenkai();
            page.header.cleargenryo();
        }

        /**
         * Clear condition。
         */
        page.header.clearHaigo = function () {
            var element = page.header.element;

            element.find(".joken_haigo :input").val("");
            element.findP("flg_seiho_base").prop("checked", true);
            element.find("[data-prop='flg_hinkan'], [data-prop='flg_seizo'], [data-prop='flg_genzai'], [data-prop='flg_mishiyo']").prop("checked", false);
        }

        /**
         * Clear condition。
         */
        page.header.clearTenkai = function () {
            var element = page.header.element;

            element.find(".joken_tenkai :input").val("");
            element.find("[data-prop='nm_haigo_parttwo'], [data-prop='kbn_haigo_parttwo'], [data-prop='nm_hin_parttwo'], [data-prop='nm_genryo'], [data-prop='kbn_hin_genryo']").text("");
//            element.findP("dt_hidzuke").val(App.data.getDateString(new Date()));
            element.findP("dt_hidzuke").val(App.data.getDateString(new Date(),true));
        }

        /**
         * Clear condition。
         */
        page.header.cleargenryo = function () {
            var element = page.header.element;

            element.find(".joken_genryo :input").val("");
            element.find("[data-prop='nm_genryo'], [data-prop='kbn_hin_genryo']").text("");

        }

        /**
         * Move page 209_配合登録_工場部門 screen (Mode: edit)。
         */
        page.detail.HaigoShosai = function (e) {
            var target = $(e.target),
               id = target.closest("tbody").attr("data-key"),
               entity = page.detail.data.entry(id);

            var pathLink = page.urls.haigoTorokuKojyoBumon + "?cd_kaisha=" + parseInt(page.values.cd_kaisha) + "&cd_kojyo=" + parseInt(page.values.cd_kojyo) + "&M_HaigoToroku=" + page.values.M_HaigoToroku.shosai
                            + "&M_kirikae=" + page.values.m_kirikae + "&cd_haigo=" + entity.cd_haigo + "&no_han=" + entity.no_han;
            window.open(pathLink);
        }

        /**
         * Move page 209_配合登録_工場部門 screen (Mode: new, edit)。
         */
        page.commands.HaigoNew = function (e) {
            var element = page.header.element;

            var cd_kaisha = element.findP("cd_kaisha").val();
            var cd_kojyo = element.findP("cd_kojyo").val();

            page.header.validator.validate().then(function(){
                var pathLink = page.urls.haigoTorokuKojyoBumon + "?cd_kaisha=" + parseInt(cd_kaisha) + "&cd_kojyo=" + parseInt(cd_kojyo) + "&M_HaigoToroku=" + page.values.M_HaigoToroku.create
                                + "&M_kirikae=" + page.values.m_kirikae;
                window.open(pathLink);
            });
        }

        /**
        * Move page 209_配合登録_工場部門 screen (Mode: new, edit)。
        */
        page.commands.HaigoCopy = function (e) {
            var selected = page.detail.element.find(".datatable .select-tab.selected").closest("tbody");
            if (!selected.length) {
                App.ui.page.notifyAlert.remove("AP008");
                App.ui.page.notifyAlert.message(App.messages.app.AP0008, "AP008").show();
                return;
            }

            var id = selected.attr("data-key"),
                entity = page.detail.data.entry(id);

            var pathLink = page.urls.haigoTorokuKojyoBumon + "?cd_kaisha=" + parseInt(page.values.cd_kaisha) + "&cd_kojyo=" + parseInt(page.values.cd_kojyo) + "&M_HaigoToroku=" + page.values.M_HaigoToroku.copy
                            + "&M_kirikae=" + page.values.m_kirikae + "&cd_haigo=" + entity.cd_haigo + "&no_han=" + entity.no_han;
            window.open(pathLink);
        }

        /**
         * Move page 705 dialog。
         */
        page.commands.HaigoCopyData = function (e) {
            var selected = page.detail.element.find(".datatable .select-tab.selected").closest("tbody");
            if (!selected.length) {
                App.ui.page.notifyAlert.remove("AP008");
                App.ui.page.notifyAlert.message(App.messages.app.AP0008, "AP008").show();
                return;
            }

            var id = selected.attr("data-key"),
                entity = page.detail.data.entry(id);
            page.dialogs.haigoCopy.values.cd_kaisha = page.values.cd_kaisha;
            page.dialogs.haigoCopy.values.cd_kojyo = page.values.cd_kojyo;
            page.dialogs.haigoCopy.values.no_han = entity.no_han;
            page.dialogs.haigoCopy.values.cd_haigo = entity.cd_haigo;
            page.dialogs.haigoCopy.values.su_code_standard = page.values.su_code_standard;
            page.dialogs.haigoCopy.values.kbn_nmacs_kojyo = page.values.kbn_nmacs_kojyo;

            page.dialogs.haigoCopy.element.modal({ backdrop: 'static', keyboard: false });
        }

        /**
         * Move page 211 添付文書画面 screen。
         */
        page.commands.TenpuShiryo = function (e) {
            var selected = page.detail.element.find(".datatable .select-tab.selected").closest("tbody");
            if (!selected.length) {
                App.ui.page.notifyAlert.remove("AP008");
                App.ui.page.notifyAlert.message(App.messages.app.AP0008, "AP008").show();
                return;
            }

            var id = selected.attr("data-key"),
                entity = page.detail.data.entry(id);

            return $.ajax(App.ajax.webapi.get(page.commands.urls.checkSeiho, { cd_kaisha: parseInt(page.values.cd_kaisha), cd_kojyo: parseInt(page.values.cd_kojyo), no_seiho: entity.no_seiho })).then(function (result) {
                if (result) {
                    var pathLink = page.urls.tenpuBunsho + "?no_seiho=" + entity.no_seiho;
                    window.open(pathLink);
                } else {
                    App.ui.page.notifyAlert.remove("AP0117");
                    App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0117, { name: "添付文書" }), "AP0117").show();
                }
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            });
        }

        /**
         * Update delete haigo。
         */
        page.commands.HaigoDelete = function (e) {
            var selected = page.detail.element.find(".datatable .select-tab.selected").closest("tbody");
            if (!selected.length) {
                App.ui.page.notifyAlert.remove("AP008");
                App.ui.page.notifyAlert.message(App.messages.app.AP0008, "AP008").show();
                return;
            }


            var id = selected.attr("data-key"),
                entity = page.detail.data.entry(id);


            if (page.values.m_kirikae == App.settings.app.m_kirikae.hyoji && entity.flg_original == 1) {
                App.ui.page.notifyAlert.remove("AP0064");
                App.ui.page.notifyAlert.message(App.messages.app.AP0064, "AP0064").show();
                return;
            }

            var options = {
                text: App.messages.app.AP0065
            };

            page.dialogs.confirmDialog.confirm(options).then(function () {

                App.ui.loading.show();

                return $.ajax(App.ajax.webapi.get(page.commands.urls.deleteData, { m_kirikae: page.values.m_kirikae, cd_kaisha: parseInt(page.values.cd_kaisha), cd_kojyo: parseInt(page.values.cd_kojyo), cd_haigo: entity.cd_haigo })).then(function (result) {
                    return App.async.all([page.header.search(false)]);
                }).then(function () {
                    App.ui.page.notifyInfo.message(App.messages.base.MS0008).show();
                }).fail(function (error) {
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                }).always(function () {
                    App.ui.loading.close();
                });
            });

        }
        /**
        *Download CSV 208
        **/
        page.commands.downloadCSV = function (isLoading) {
                var param = page.header.createFilter(),
                    element = page.header.element;

                App.ui.page.notifyAlert.clear();
                page.header.validator.validate().then(function () {
                    if (element.findP("kansakujyoho")[0].checked == true) {
                        page.header.validatorone.validate();
                    } else if (element.findP("kansakujyoho")[1].checked == true) {
                        page.header.validatortwo.validate();
                    } else {
                        page.header.validatorthree.validate();
                    }
                    if (App.ui.page.notifyAlert.count() > 0) {
                        return;
                    } else {
                        if (isLoading) {
                            App.ui.loading.show();
                            App.ui.page.notifyAlert.clear();
                        }
                        return $.ajax(App.ajax.file.download(page.detail.options.downloadCSV, param))
                       .then(function (response, status, xhr) {
                           if (status !== "success") {
                               App.ui.page.notifyAlert.message(App.messages.base.MS0005).show();
                           } else {
                               App.file.save(response, App.ajax.file.extractFileNameDownload(xhr) || "CSVFile.csv");
                           }
                       }).fail(function (error) {
                           App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                       }).always(function () {
                           App.ui.loading.close();
                       });
                    }
                });
            };

        /**
         * すべてのバリデーションを実行します。
         */
        page.validateAll = function () {

            var validations = [];

            validations.push(page.detail.validateList());

            //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。

            return App.async.all(validations);
        };

        /**
         * Windows閉じる際のイベントを定義します。
         * @return 文字列を返却した場合に確認メッセージが表示されます。
         */
        //App.ui.page.onclose = function () {

        //    var detail,
        //        closeMessage = App.messages.base.exit;

        //    if (page.detail.data) {
        //        detail = page.detail.data.getChangeSet();
        //        if (detail.created.length || detail.updated.length || detail.deleted.length) {
        //            return closeMessage;
        //        }
        //    }
        //};

        /**
        * 画面の初期化処理を行います。
        */
        page.initialize = function () {

            App.ui.loading.show();

            if (!App.isUndefOrNullOrStrEmpty(App.uri.queryStrings.M_kirikae) || !App.isUndefOrNullOrStrEmpty(App.uri.queryStrings.m_kirikae)) {
                page.values.m_kirikae = App.uri.queryStrings.M_kirikae;
                if (!App.isUndefOrNullOrStrEmpty(App.uri.queryStrings.m_kirikae)) {
                    page.values.m_kirikae = App.uri.queryStrings.m_kirikae;
                }
                if (page.values.m_kirikae == App.settings.app.m_kirikae.hyoji) {
                    $(".page-title").text(page.values.is_kirikae_hyoji_title);
                    $(".wrap, .footer").addClass("theme-pink");
                } else if (page.values.m_kirikae == App.settings.app.m_kirikae.foodprocs) {
                    $(".page-title").text(page.values.is_kirikae_foodprocs_title);
                } else {
                    App.ui.loading.close();
                    return;
                }
                document.title = $(".page-title").text() + document.title;
            }

            page.initializeControl();
            page.initializeControlEvent();

            page.header.initialize();
            page.detail.initialize();

            //TODO: ヘッダー/明細以外の初期化の処理を記述します。

            page.loadMasterData().then(function (result) {

                return page.loadDialogs();
            }).then(function (result) {
                //TODO: 画面の初期化処理成功時の処理を記述します。
                page.header.options.partone.validations.cd_haigo.rules.maxlength = (App.ui.page.user.su_code_standard + 1);
                page.header.element.find("[data-prop='cd_haigo']").attr("maxlength", (App.ui.page.user.su_code_standard + 1));

            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

            }).always(function (result) {
                if (!App.isUndefOrNullOrStrEmpty(App.uri.queryStrings.M_kirikae) || !App.isUndefOrNullOrStrEmpty(App.uri.queryStrings.m_kirikae)) {
                    page.values.m_kirikae = App.uri.queryStrings.M_kirikae;
                    if (!App.isUndefOrNullOrStrEmpty(App.uri.queryStrings.m_kirikae)) {
                        page.values.m_kirikae = App.uri.queryStrings.m_kirikae;
                    }
                    var isRoleScreen = false;

                    if (page.values.m_kirikae == App.settings.app.m_kirikae.hyoji) {
                        getKengenGamen(App.settings.app.id_gamen.haigo_ichiran_hyoji).then(function (results) {
                            page.kengenGamen = results;
                            $.each(results, function (index, item) {
                                if ((item.id_kino == page.values.id_kino_search && item.id_data == page.values.id_data_jisshi_kano)
                                    || (item.id_kino == page.values.id_kino_ippan && item.id_data == page.values.id_data_jisshi_kano)) {
                                    $("#haigo-shoshai").prop("disabled", false);
                                    isRoleScreen = true;
                                }
                                if (item.id_kino == page.values.id_kino_hon_seizo && item.id_data == page.values.id_data_jisshi_kano) {
                                    $("#btn-new").prop("disabled", false);
                                    page.values.isRoleJisshiKano = true;
                                    isRoleScreen = true;
                                }
                            });

                            if (!isRoleScreen) {
                                window.location.href = page.urls.mainMenu;
                            }
                        });
                    }

                    if (page.values.m_kirikae == App.settings.app.m_kirikae.foodprocs) {
                        getKengenGamen(App.settings.app.id_gamen.haigo_ichiran_foodprocs).then(function (results) {
                            page.kengenGamen = results;
                            $.each(results, function (index, item) {
                                if ((item.id_kino == page.values.id_kino_search && item.id_data == page.values.id_data_jisshi_kano)
                                    || (item.id_kino == page.values.id_kino_ippan && item.id_data == page.values.id_data_jisshi_kano)) {
                                    $("#haigo-shoshai").prop("disabled", false);
                                    isRoleScreen = true;
                                }
                                if (item.id_kino == page.values.id_kino_hon_seizo && item.id_data == page.values.id_data_jisshi_kano) {
                                    $("#btn-new").prop("disabled", false);
                                    page.values.isRoleJisshiKano = true;
                                    isRoleScreen = true;
                                }
                            });

                            if (!isRoleScreen) {
                                window.location.href = page.urls.mainMenu;
                            }
                        });
                    }
                }

                var element = page.header.element;
                if (page.values.m_kirikae == App.settings.app.m_kirikae.foodprocs) {
                    $(page.detail.element.find(".chk-haigo-header")[0]).text("表示用有無");
                }
//                element.findP("dt_hidzuke").val(App.data.getDateString(new Date()));
                element.findP("dt_hidzuke").val(App.data.getDateString(new Date(),true));
                element.find("[data-prop='cd_haigo'], [data-prop='cd_haigo_parttwo']").attr("maxlength", (App.ui.page.user.su_code_standard + 1));
                element.findP("cd_hin_parttwo").attr("maxlength", App.ui.page.user.su_code_standard);
                
                element.find(":input:first").focus();

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
            $("#save").on("click", page.commands.save);

            $("#btn-clear").on("click", page.commands.ClearCondition);
            $("#btn-new").on("click", page.commands.HaigoNew);
            $("#btn-coppy").on("click", page.commands.HaigoCopy);
            $("#btn-copy-data").on("click", page.commands.HaigoCopyData);
            $("#btn-tenpu-shiryo").on("click", page.commands.TenpuShiryo);
            $("#btn-delete").on("click", page.commands.HaigoDelete);
            $("#csv-download").on("click", page.commands.downloadCSV);
        };

        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function () {
            var element = page.header.element;

            //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
            return $.ajax(App.ajax.odata.get(page.header.urls.vw_no_seiho_shurui)).then(function (result) {

                var no_seiho_shurui = element.findP("no_seiho_shurui");
                no_seiho_shurui.children().remove();
                App.ui.appendOptions(
                    no_seiho_shurui,
                    "no_seiho_shurui",
                    "no_seiho_shurui",
                    result.value,
                    true
                );
                page.kaishaKojoJotai(page.header.urls.ma_kaisha, "nm_kaisha", "cd_kaisha", "nm_kaisha", "", false).then(function () {
                    var cd_kaisha = element.findP("nm_kaisha").val();
                    element.findP("nm_kaisha").val(App.ui.page.user.cd_kaisha);
                    cd_kaisha = App.ui.page.user.cd_kaisha;
                    page.values.cd_kaisha = cd_kaisha;
                    page.values.su_code_standard = App.ui.page.user.su_code_standard + 1;
                    element.findP("cd_genryo").attr("maxlength", page.values.su_code_standard);
                    if (!App.ui.page.user.flg_kaishakan_sansyo) {
                        element.find("[data-prop='cd_kaisha'], [data-prop='nm_kaisha']").prop("disabled", true);
                    }

                    if (!App.isUndefOrNullOrStrEmpty(cd_kaisha)) {
                        element.findP("cd_kaisha").val(page.addZero(cd_kaisha, "0000"));
                        element.findP("cd_kaisha_validate").text(cd_kaisha);

                        var cd_kojyo = "";
                        page.kaishaKojoJotai(page.header.urls.vw_kaisha_kojyo, "nm_kojyo", "cd_kojyo", "nm_kojyo", cd_kaisha, false).then(function () {
                            cd_kojyo = element.findP("nm_kojyo").val();
                            element.findP("nm_kojyo").val(App.ui.page.user.cd_busho);
                            cd_kojyo = App.ui.page.user.cd_busho;
                            page.values.cd_kojyo = cd_kojyo;
                            if (!App.ui.page.user.flg_kojyokan_sansyo) {
                                element.find("[data-prop='cd_kojyo'], [data-prop='nm_kojyo']").prop("disabled", true);
                            }

                            if (!App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                                element.findP("cd_kojyo").val(page.addZero(cd_kojyo, "0000"));
                                element.findP("cd_kojyo_validate").text(cd_kojyo);
                            }
                        }).then(function () {
                            page.loadApi(page.header.urls.kbn_hin, "kbn_hin", "nm_kbn_hin", cd_kaisha, cd_kojyo, true);
                        }).then(function () {
                            page.loadApi(page.header.urls.ma_bunrui, "cd_bunrui", "nm_bunrui", cd_kaisha, cd_kojyo, true);
                        }).fail(function (error) {
                            App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                        });
                    }
                }).fail(function (error) {
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                });
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            });

            //TODO: マスタデータのロード処理を実装後、下の1行を削除してください。
            return App.async.success();
        };

        /**
         * Load combobox 品区分。
         */
        page.loadApi = function (urlPara, dataprop, nameProp, cd_kaisha, cd_kojyo, blank) {
            var element = page.header.element;

            return $.ajax(App.ajax.webapi.get(urlPara, { cd_kaisha: cd_kaisha, cd_kojyo: cd_kojyo })).then(function (result) {
                var elementprop = element.findP(dataprop);
                elementprop.children().remove();
                App.ui.appendOptions(
                    elementprop,
                    dataprop,
                    nameProp,
                    result,
                    blank
                );
            });
        }

        /**
        * Load combobox 品区分。
        */
        page.getDataApi = function (urlPara, dataprop, nameProp, codeData, nameData, cd_kaisha, cd_kojyo, cd_code) {
            var element = page.header.element;

            element.findP(nameProp).text("");
            if (!App.isUndefOrNullOrStrEmpty(cd_code)) {
                return $.ajax(App.ajax.webapi.get(urlPara, { m_kirikae: page.values.m_kirikae, cd_kaisha: cd_kaisha, cd_kojyo: cd_kojyo, cd_code: cd_code })).then(function (result) {
                    if (result != null) {
                        if (result.length > 0) {
                            var item = result[0];
                            element.findP(nameProp).text(item[nameData]);
                            if (dataprop == "cd_genryo") {
                                page.header.values.kbn_hin = item.kbn_hin;
                                if (page.header.values.kbn_hin == 1 || page.header.values.kbn_hin == 6) {
                                    var zeroList = "";
                                    for (var i = 0; i < (page.values.su_code_standard - 1) ; i++) {
                                        zeroList = zeroList + "0";
                                    }
                                    element.findP(dataprop).val(page.addZero(item[codeData], zeroList));
                                }

                                if (page.header.values.kbn_hin == 1 || page.header.values.kbn_hin == 6) {
                                    page.header.options.partthree.validations.cd_genryo.rules.maxlength = (page.values.su_code_standard - 1);
                                    element.findP("cd_genryo").attr("maxlength", (page.values.su_code_standard - 1));
                                } else {
                                    page.header.options.partthree.validations.cd_genryo.rules.maxlength = page.values.su_code_standard;
                                    element.findP("cd_genryo").attr("maxlength", page.values.su_code_standard);
                                }
                            }
                            
                        }
                        if (dataprop == "cd_genryo" && result.length == 0) {
                            page.header.values.kbn_hin = null;
                            page.header.options.partthree.validations.cd_genryo.rules.maxlength = page.values.su_code_standard;
                            element.findP("cd_genryo").attr("maxlength", page.values.su_code_standard);
                        }
                    }
                }).fail(function (error) {
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                }).always(function () {
                    delete page.values.isChangeRunning[dataprop];
                    delete page.values.isChangeRunning["getDataApi"];
                });
            } else {
                if (dataprop == "cd_genryo") {
                    page.header.values.kbn_hin == null;
                    page.header.options.partthree.validations.cd_genryo.rules.maxlength = page.values.su_code_standard;
                    element.findP("cd_genryo").attr("maxlength", page.values.su_code_standard);
                }
                delete page.values.isChangeRunning[dataprop];
                delete page.values.isChangeRunning["getDataApi"];
            }
        }



        /**
         * Load combobox 製造会社, 製造工場, 状態。
         */
        page.kaishaKojoJotai = function (url, dataPropElement, codeProps, nameProps, param, blank) {

            //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
            return $.ajax(App.ajax.odata.get(App.str.format(url, param))).then(function (result) {

                var element = page.header.element.findP(dataPropElement);
                element.children().remove();
                if (codeProps != "cd_kaisha") {
                    App.ui.appendOptions(
                        element,
                        codeProps,
                        nameProps,
                        result.value,
                        blank
                    );
                } else {
                    App.common.appendOptions(
                        element,
                        codeProps,
                        nameProps,
                        result.value,
                        blank,
                        {},
                        false,
                        "",
                        "su_code_standard"
                    );
                }

                // SKS_MOD_2020
                if (dataPropElement == "nm_kojyo") {
                    page.values.lstKojyo = result.value;
                }
                // SKS_MOD_2020
            });
        };

        /**
         * 共有ダイアログのロード処理を実行します。
         */
        page.loadDialogs = function () {

            //TODO: 共有ダイアログデータを取得し、画面にバインドする処理を記述します。
            return App.async.all({

                confirmDialog: $.get(page.urls.confirmDialog),
                seihinKensakuDialog: $.get(page.urls.seihinKensakuDialog),
                HinmeiKaihatsuDialog: $.get(page.urls.HinmeiKaihatsuDialog),
                haigoCopy: $.get(page.urls.haigoCopy),
            }).then(function (result) {

                $("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;

                $("#dialog-container").append(result.successes.seihinKensakuDialog);
                page.dialogs.seihinKensakuDialog = SeihinKensakuDialog;
                page.dialogs.seihinKensakuDialog.param.no_gamen = App.settings.app.no_gamen.haigo_ichiran;
                page.dialogs.seihinKensakuDialog.param.m_kirikae = page.values.m_kirikae;
                page.dialogs.seihinKensakuDialog.initialize();

                $("#dialog-container").append(result.successes.HinmeiKaihatsuDialog);
                page.dialogs.HinmeiKaihatsuDialog = _710_HinmeiKaihatsu_Dialog;
                page.dialogs.HinmeiKaihatsuDialog.param.no_gamen = App.settings.app.no_gamen.haigo_ichiran;
                page.dialogs.HinmeiKaihatsuDialog.param.M_kirikae = page.values.m_kirikae;
                page.dialogs.HinmeiKaihatsuDialog.initialize();

                $("#dialog-container").append(result.successes.haigoCopy);
                page.dialogs.haigoCopy = _705_HaigoCopy_Dialog;
                page.dialogs.haigoCopy.values.M_kirikae = page.values.m_kirikae;
                page.dialogs.haigoCopy.initialize();
            });
        }

        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.validations = {
            //TODO: 画面ヘッダーのバリデーションの定義を記述します。
            cd_kaisha: {
                rules: {
                    required: true,
                    maxlength: 4,
                    digits: true,
                    existsCode: function (value, opts, state, done) {
                        var element = page.header.element;
                        var cd_kaisha = element.findP("cd_kaisha").val();
                        var cd_kaisha_validate = element.findP("cd_kaisha_validate").text();

                        if (App.isUndefOrNullOrStrEmpty(cd_kaisha)) {
                            return done(true);
                        }


                        return done(cd_kaisha_validate != "");
                    }
                },
                options: {
                    name: "会社コード"
                },
                messages: {
                    required: App.messages.base.required,
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits,
                    existsCode: App.messages.app.AP0010
                }
            },
            cd_kojyo: {
                rules: {
                    required: true,
                    maxlength: 4,
                    digits: true,
                    existsCode: function (value, opts, state, done) {
                        var element = page.header.element;
                        var cd_kojyo = element.findP("cd_kojyo").val();
                        var cd_kojyo_validate = element.findP("cd_kojyo_validate").text();

                        if (App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                            return done(true);
                        }


                        return done(cd_kojyo_validate != "");
                    }
                },
                options: {
                    name: "工場コード"
                },
                messages: {
                    required: App.messages.base.required,
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits,
                    existsCode: App.messages.app.AP0010
                }
            },

        };

        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.partone.validations = {
            cd_haigo: {
                rules: {
                    maxlength: page.values.su_code_standard,
                    digits: true
                },
                options: {
                    name: "配合コード指定"
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits
                }
            },
            no_seiho_kaisha: {
                rules: {
                    maxlength: 4,
                    digits: true,
                },
                options: {
                    name: "製法番号(会社コード)"
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits,
                }
            },
            no_seiho_nen: {
                rules: {
                    maxlength: 2,
                    digits: true,
                },
                options: {
                    name: "製法番号(年)"
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits,
                }
            },
            no_seiho_renban: {
                rules: {
                    maxlength: 4,
                    digits: true,
                },
                options: {
                    name: "製法番号(シーケンス番号)"
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits,
                }
            },
            nm_haigo: {
                rules: {
                    maxbytelength: 60
                },
                options: {
                    name: "配合名/コード",
                    byte: 30
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            nm_seihin: {
                rules: {
                    maxbytelength: 60
                },
                options: {
                    name: "製品名/コード",
                    byte: 30
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
        }

        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.parttwo.validations = {
            dt_hidzuke: {
                rules: {
                    required: true,
                    datestring: true,
                    maxlength: 10,
                    rangeYearMonthDay: ["1950/01/01", "3000/12/31"]
                },
                options: {
                    name: "日付",
                },
                messages: {
                    required: App.messages.base.required,
                    datestring: App.messages.base.datestring,
                    maxlength: App.messages.base.maxlength,
                    rangeYearMonthDay: App.messages.base.range
                }
            },
            cd_haigo_parttwo: {
                rules: {
                    requiredCustom: function (value, opts, state, done) {
                        var element = page.header.element;

                        if (element.findP("codecheck")[0].checked == true && App.isUndefOrNullOrStrEmpty(value)) {
                            return done(false);
                        }
                        return done(true);
                    },
                    maxlength: page.values.su_code_standard,
                    digits: true,
                    existsCode: function (value, opts, state, done) {
                        var element = page.header.element;
                        var cd_haigo_parttwo = element.findP("cd_haigo_parttwo").val();
                        var nm_haigo_parttwo = element.findP("nm_haigo_parttwo").text();

                        if (!App.isUndefOrNullOrStrEmpty(cd_haigo_parttwo) && App.isUndefOrNullOrStrEmpty(nm_haigo_parttwo) && element.findP("codecheck")[0].checked == true) {
                            return done(false);
                        }


                        return done(true);
                    }
                },
                options: {
                    name: "配合コード"
                },
                messages: {
                    requiredCustom: App.messages.base.required,
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits,
                    existsCode: App.messages.app.AP0010
                }
            },
            cd_hin_parttwo: {
                rules: {
                    requiredCustom: function (value, opts, state, done) {
                        var element = page.header.element;

                        if (element.findP("codecheck")[1].checked == true && App.isUndefOrNullOrStrEmpty(value)) {
                            return done(false);
                        }
                        return done(true);
                    },
                    maxlength: page.values.su_code_standard,
                    digits: true,
                    existsCode: function (value, opts, state, done) {
                        var element = page.header.element;
                        var cd_hin_parttwo = element.findP("cd_hin_parttwo").val();
                        var nm_hin_parttwo = element.findP("nm_hin_parttwo").text();

                        if (!App.isUndefOrNullOrStrEmpty(cd_hin_parttwo) && App.isUndefOrNullOrStrEmpty(nm_hin_parttwo) && element.findP("codecheck")[1].checked == true) {
                            return done(false);
                        }

                        return done(true);
                    }
                },
                options: {
                    name: "製品コード"
                },
                messages: {
                    requiredCustom: App.messages.base.required,
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits,
                    existsCode: App.messages.app.AP0010
                }
            },
        }

        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.partthree.validations = {
            cd_genryo: {
                rules: {
                    maxlength: page.values.su_code_standard,
                    digits: true,
                    existsCode: function (value, opts, state, done) {
                        var element = page.header.element;
                        var cd_genryo = element.findP("cd_genryo").val();
                        var nm_genryo = element.findP("nm_genryo").text();

                        if (!App.isUndefOrNullOrStrEmpty(cd_genryo) && App.isUndefOrNullOrStrEmpty(nm_genryo)) {
                            return done(false);
                        }

                        return done(true);
                    }
                },
                options: {
                    name: "原料コード"
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits,
                    existsCode: App.messages.app.AP0010
                }
            },
            nm_genryo_text: {
                rules: {
                    maxbytelength: 60
                },
                options: {
                    name: "原料名",
                    byte: 30
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            cd_kikaku: {
                rules: {
                    maxlength: 17,
                },
                options: {
                    name: "規格書No"
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                }
            },
            nm_kikaku_shohin: {
                rules: {
                    maxbytelength: 80
                },
                options: {
                    name: "規格書商品名",
                    byte: 40
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            nm_seiho_mei: {
                rules: {
                    maxbytelength: 80
                },
                options: {
                    name: "製造社名",
                    byte: 40
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            nm_hanbai_mei: {
                rules: {
                    maxbytelength: 80
                },
                options: {
                    name: "販売社名",
                    byte: 40
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
        }

        /**
         * 画面ヘッダーの初期化処理を行います。
         */
        page.header.initialize = function () {

            var element = $(".header");
            page.header.validator = element.validation(page.createValidator(page.header.options.validations));
            page.header.validatorone = element.validation(page.createValidator(page.header.options.partone.validations));
            page.header.validatortwo = element.validation(page.createValidator(page.header.options.parttwo.validations));
            page.header.validatorthree = element.validation(page.createValidator(page.header.options.partthree.validations));
            page.header.element = element;

            //TODO: 画面ヘッダーの初期化処理をここに記述します。
            //TODO: 画面ヘッダーで利用するコントロールのイベントの紐づけ処理をここに記述します。
            element.find(":input[data-role='date']").datepicker();
            element.find(":input[data-role='date']").on("keyup", App.data.addSlashForDateString);
            element.on("click", "#search", page.header.search);
            element.on("change", ":input", page.header.change);
            element.on("click", ".btn-seihin", page.header.openSeihinKensakuDialog);
            element.on("click", ".btn-haigo", page.header.openHinmeiKaihatsuDialog);
            element.on("click", ".btn-genryo", page.header.openHinmeiKaihatsuGenryoDialog);


        };


        /**
         * Open SeihinKensakuDialog。
         */
        page.header.openSeihinKensakuDialog = function () {
            var element = page.header.element;
            var cd_kaisha = element.findP("cd_kaisha").val(),
                cd_kojyo = element.findP("cd_kojyo").val();

            page.dialogs.seihinKensakuDialog.param.cd_kaisha = cd_kaisha;
            page.dialogs.seihinKensakuDialog.param.cd_kojyo = cd_kojyo;

            page.dialogs.seihinKensakuDialog.element.modal("show");
            page.dialogs.seihinKensakuDialog.dataSelected = function (data) {
                element.findP("nm_hin_parttwo").text(data.nm_seihin);
                element.findP("cd_hin_parttwo").val(data.cd_seihin).change();
            }
        }

        /**
        * Open HinmeiKaihatsuDialog。
        */
        page.header.openHinmeiKaihatsuDialog = function () {
            var element = page.header.element;
            var cd_kaisha = element.findP("cd_kaisha").val(),
                cd_kojyo = element.findP("cd_kojyo").val();


            page.dialogs.HinmeiKaihatsuDialog.param.cd_kaisha = cd_kaisha;
            page.dialogs.HinmeiKaihatsuDialog.param.cd_kojyo = cd_kojyo;
            page.dialogs.HinmeiKaihatsuDialog.param.kbn_hin_search = 2;
            page.dialogs.HinmeiKaihatsuDialog.param.no_gamen = App.settings.app.no_gamen.haigo_ichiran;

            page.dialogs.HinmeiKaihatsuDialog.element.modal("show");
            page.dialogs.HinmeiKaihatsuDialog.dataSelected = function (data) {
                element.findP("nm_haigo_parttwo").text(data.nm_hin);
                element.findP("kbn_haigo_parttwo").text(data.nm_hin);
                element.findP("cd_haigo_parttwo").val(data.cd_hin).change();
            }
        }

        /**
        * Open HinmeiKaihatsuDialog Genryo。
        */
        page.header.openHinmeiKaihatsuGenryoDialog = function () {
            var element = page.header.element;
            var cd_kaisha = element.findP("cd_kaisha").val(),
                cd_kojyo = element.findP("cd_kojyo").val();


            page.dialogs.HinmeiKaihatsuDialog.param.cd_kaisha = cd_kaisha;
            page.dialogs.HinmeiKaihatsuDialog.param.cd_kojyo = cd_kojyo;
            page.dialogs.HinmeiKaihatsuDialog.param.kbn_hin_search = 1;

            page.dialogs.HinmeiKaihatsuDialog.element.modal("show");
            page.dialogs.HinmeiKaihatsuDialog.dataSelected = function (data) {
                var zeroList = "";
                var su_code_standard = page.values.su_code_standard;
                if (data.kbn_hin == 1 || data.kbn_hin == 6) {
                    su_code_standard = su_code_standard - 1;
                }
                for (var i = 0; i < su_code_standard; i++) {
                    zeroList = zeroList + "0";
                }
                element.findP("cd_genryo").attr("maxlength", su_code_standard);
                element.findP("cd_genryo").val(page.addZero(data.cd_hin, zeroList));
                element.findP("nm_genryo").text(data.nm_hin);
                element.findP("kbn_hin_genryo").text(data.kbn_hin);
                page.header.values.kbn_hin = data.kbn_hin;

                page.header.validatorthree.validate({
                    targets: element.findP("cd_genryo")
                });
            }
        }

        /**
         * 画面ヘッダーの変更時処理を定義します。
         */
        page.header.change = function (e) {
            var element = page.header.element,
                target = $(e.target),
                id = element.attr("data-key"),
                property = target.attr("data-prop"),
                data = element.form().data(),
                options = {
                    filter: page.header.validationFilter
                };

            page.values.isChangeRunning[property] = true;

            if ($("#nextsearch").hasClass("show-search")) {
                $("#nextsearch").removeClass("show-search").hide();
                App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
            }
            else if (page.detail.searchData) {
                // 保持検索データの消去
                page.detail.searchData = undefined;
                App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
            }
            //タブ切り替え
            if (property == "kansakujyoho") {
                App.ui.page.notifyAlert.clear();
                if (element.findP("kansakujyoho")[0].checked == true) {
                    $(".joken_haigo").show();
                    $(".joken_tenkai").hide();
                    $(".joken_genryo").hide();
                    page.header.clearTenkai();
                    page.header.cleargenryo();
                } else if (element.findP("kansakujyoho")[1].checked == true) {
                    $(".joken_haigo").hide();
                    $(".joken_tenkai").show();
                    $(".joken_genryo").hide();
                    page.header.clearHaigo();
                    page.header.cleargenryo();
                    page.header.validatortwo.validate(options);
                } else {
                    $(".joken_haigo").hide();
                    $(".joken_tenkai").hide();
                    $(".joken_genryo").show();
                    page.header.clearHaigo();
                    page.header.clearTenkai();
                }
            }
            //配合コードと製品コードの活性・非活性
            if (property == "codecheck") {
                if (element.findP("codecheck")[0].checked == true) {
                    $(".haigo_check").prop("disabled", false);
                    $(".seizo_check").prop("disabled", true);
                    element.findP("cd_hin_parttwo").val("");
                    element.findP("nm_hin_parttwo").text("");
                } else if (element.findP("codecheck")[1].checked == true) {
                    $(".haigo_check").prop("disabled", true);
                    $(".seizo_check").prop("disabled", false);
                    element.findP("cd_haigo_parttwo").val("");
                    element.findP("nm_haigo_parttwo").text("");
                }
            }
            //品区分仕掛品時の分類解放
            if (property == "kbn_hin") {
                if (data.kbn_hin == 2) {
                    element.findP("cd_bunrui").prop("disabled", false);
                } else {
                    element.findP("cd_bunrui").prop("disabled", true).val("");
                }
            }

            if (property == "cd_kaisha" || property == "nm_kaisha") {
                var cd_kaisha = target.val();
                if (App.ui.page.user.flg_kojyokan_sansyo) {
                    element.findP("nm_kojyo").children().remove();
                    element.findP("cd_kojyo").val("");
                }


                element.findP("cd_kaisha").val(page.addZero(cd_kaisha, "0000"));
                element.findP("cd_kaisha_validate").text(cd_kaisha);
                element.findP("nm_kaisha").val(cd_kaisha);

                page.values.isResetKojyo = true;
                if (property == "cd_kaisha") {
                    if (App.isUndefOrNullOrStrEmpty(cd_kaisha)) {
                        element.findP("nm_kaisha").val(cd_kaisha);
                    }
                    if (cd_kaisha != "" && App.isNumeric(cd_kaisha)) {
                        cd_kaisha = parseInt(cd_kaisha);

                        page.checkData(App.str.format(page.header.urls.ma_kaisha, "$filter=cd_kaisha eq {0}"), cd_kaisha, "",
                                        element, "cd_kaisha", "cd_kaisha_validate", "nm_kaisha", cd_kaisha);
                    }
                }

                if (property == "nm_kaisha" || page.values.isResetKojyo) {
                    var su_code_standard = element.findP("nm_kaisha").children("option:selected").attr("su_code_standardval");
                    if (!App.isUndefOrNullOrStrEmpty(su_code_standard)) {
                        page.values.su_code_standard = parseInt(su_code_standard) + 1;
                    }
                    if (cd_kaisha != "" && App.isNumeric(cd_kaisha) && App.ui.page.user.flg_kojyokan_sansyo) {
                        cd_kaisha = parseInt(cd_kaisha);
                        page.values.isChangeRunning["changeKaisha"] = true;
                        page.kaishaKojoJotai(page.header.urls.vw_kaisha_kojyo, "nm_kojyo", "cd_kojyo", "nm_kojyo", cd_kaisha, false).then(function () {
                            var cd_kojyo = element.findP("nm_kojyo").val();
                            if (!App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                                element.findP("cd_kojyo").val(page.addZero(cd_kojyo, "0000"));
                                element.findP("cd_kojyo_validate").text(cd_kojyo);
                            }

                        }).always(function () {
                            delete page.values.isChangeRunning["changeKaisha"];
                        });
                    }
                    page.header.validator.validate({
                        targets: element.find("[data-prop='cd_kaisha'], [data-prop='nm_kaisha']")
                    });
                }
            }

            if (property == "cd_kojyo") {
                var cd_kojyo = target.val();
                element.findP("cd_kojyo").val(page.addZero(cd_kojyo, "0000"));
                element.findP("cd_kojyo_validate").text(cd_kojyo);
                element.findP("nm_kojyo").val(cd_kojyo);
                if (App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                    element.findP("nm_kojyo").val(cd_kojyo);
                }
                if (cd_kojyo != "" && App.isNumeric(cd_kojyo)) {
                    cd_kojyo = parseInt(cd_kojyo);
                    var cd_kaisha = element.findP("nm_kaisha").val();

                    page.checkData(App.str.format(page.header.urls.vw_kaisha_kojyo, "{0}", " and cd_kojyo eq {1}"), cd_kaisha, cd_kojyo,
                                    element, "cd_kojyo", "cd_kojyo_validate", "nm_kojyo", cd_kojyo);
                }

            }
            if (property == "nm_kojyo") {
                var cd_kojyo = target.val();

                element.findP("cd_kojyo").val(page.addZero(cd_kojyo, "0000"));
                element.findP("cd_kojyo_validate").text(cd_kojyo);
                element.findP("nm_kojyo").val(cd_kojyo);
            }

            // SKS_MOD_2020
            if (property == "cd_kojyo" || property == "nm_kojyo") {
                if (page.values.lstKojyo) {
                    $.each(page.values.lstKojyo, function (ind, item) {
                        if (item.cd_kojyo == target.val()) {
                            page.values.kbn_nmacs_kojyo = item.kbn_nmacs_kojyo;
                        }
                    })
                }
            }
            // SKS_MOD_2020

            if (property == "no_seiho_kaisha" || property == "no_seiho_nen" || property == "no_seiho_renban" || property == "cd_haigo"
                || property == "cd_haigo_parttwo" || property == "cd_hin_parttwo" || property == "cd_genryo") {
                var value = target.val(),
                    zeroList = "0000";

                if (property == "no_seiho_nen") {
                    zeroList = "00";
                }

                if (property == "cd_haigo" || property == "cd_haigo_parttwo" || property == "cd_hin_parttwo") {
                    zeroList = "";
                    var su_code_standard = page.values.su_code_standard;
                    if (property == "cd_hin_parttwo") {
                        su_code_standard = su_code_standard - 1;
                    }
                    for (var i = 0; i < su_code_standard; i++) {
                        zeroList = zeroList + "0";
                    }
                }

                if (property == "cd_genryo") {
                    zeroList = "";
                    var su_code_standard = page.values.su_code_standard;
                    for (var i = 0; i < su_code_standard; i++) {
                        zeroList = zeroList + "0";
                    }
                }

                if (value != "") {
                    element.findP(property).val(page.addZero(value, zeroList));
                }
            }

            if (property == "cd_haigo_parttwo") {
                var cd_haigo = target.val(),
                    cd_kaisha = element.findP("cd_kaisha").val(),
                    cd_kojyo = element.findP("cd_kojyo").val();
                page.header.validator.validate().then(function () {
                    page.values.isChangeRunning["getDataApi"] = true;
                    page.getDataApi(page.header.urls.getHaigo, "cd_haigo_parttwo", "nm_haigo_parttwo", "cd_haigo", "nm_haigo", cd_kaisha, cd_kojyo, cd_haigo);
                }).always(function () {
                    delete page.values.isChangeRunning[property];
                });
            } else if (property == "cd_hin_parttwo") {
                var cd_hin = target.val(),
                    cd_kaisha = element.findP("cd_kaisha").val(),
                    cd_kojyo = element.findP("cd_kojyo").val();
                page.header.validator.validate().then(function () {
                    page.values.isChangeRunning["getDataApi"] = true;
                    page.getDataApi(page.header.urls.getSeihin, "cd_hin_parttwo", "nm_hin_parttwo", "cd_hin", "nm_hin", cd_kaisha, cd_kojyo, cd_hin);
                }).always(function () {
                    delete page.values.isChangeRunning[property];
                });
            } else if (property == "cd_genryo") {
                var cd_genryo = target.val(),
                    cd_kaisha = element.findP("cd_kaisha").val(),
                    cd_kojyo = element.findP("cd_kojyo").val();

                if (!App.isUndefOrNullOrStrEmpty(cd_genryo) && App.isNumeric(cd_genryo)) {
                    page.header.validator.validate().then(function () {
                        page.values.isChangeRunning["getDataApi"] = true;
                        page.getDataApi(page.header.urls.getGenryo, "cd_genryo", "nm_genryo", "cd_hin", "nm_hin", cd_kaisha, cd_kojyo, cd_genryo);
                    }).always(function () {
                        delete page.values.isChangeRunning[property];
                    });
                } else {
                    page.header.values.kbn_hin = null;
                    page.header.options.partthree.validations.cd_genryo.rules.maxlength = page.values.su_code_standard;
                    element.findP("cd_genryo").attr("maxlength", page.values.su_code_standard);
                    delete page.values.isChangeRunning["getDataApi"];
                    element.findP("nm_genryo").text("");
                    delete page.values.isChangeRunning[property];
                }
            }
            else {
                delete page.values.isChangeRunning[property];
            }

            if (property == "kbn_hin") {
                var kbn_hin = target.val();
                if (kbn_hin == App.settings.app.kbnHin.shikakari) {
                    element.findP("cd_bunrui").prop("disabled", false);
                } else {
                    element.findP("cd_bunrui").val("");
                    element.findP("cd_bunrui").prop("disabled", true);
                }

            }

            var sleep = 0;
            var condition = "Object.keys(page.values.isChangeRunning).length == 0";
            App.ui.wait(sleep, condition, 100)
            .then(function () {

                if (property == "cd_kaisha" || property == "nm_kaisha" || property == "cd_kojyo" || property == "nm_kojyo") {
                    var kbn_hin = element.findP("kbn_hin");
                    kbn_hin.children().remove();
                    var cd_kaisha = element.findP("cd_kaisha").val(),
                        cd_kojyo = element.findP("cd_kojyo").val();

                    if (!App.isUndefOrNullOrStrEmpty(cd_kaisha) && !App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                        page.loadApi(page.header.urls.kbn_hin, "kbn_hin", "nm_kbn_hin", cd_kaisha, cd_kojyo, true);
                        page.loadApi(page.header.urls.ma_bunrui, "cd_bunrui", "nm_bunrui", cd_kaisha, cd_kojyo, true);
                    }
                    element.find("[data-prop='cd_haigo_parttwo'], [data-prop='cd_hin_parttwo'], [data-prop='cd_genryo']").val("");
                    element.find("[data-prop='cd_haigo_parttwo'], [data-prop='nm_haigo_parttwo'], [data-prop='kbn_haigo_parttwo']").text("");
                    element.findP("nm_hin_parttwo").text("");
                    element.find("[data-prop='nm_genryo'], [data-prop='kbn_hin_genryo']").text("");
                }

                page.header.options.partone.validations.cd_haigo.rules.maxlength = page.values.su_code_standard;
                page.header.options.parttwo.validations.cd_haigo_parttwo.rules.maxlength = page.values.su_code_standard;
                page.header.options.parttwo.validations.cd_hin_parttwo.rules.maxlength = (page.values.su_code_standard - 1);
                element.find("[data-prop='cd_haigo'], [data-prop='cd_haigo_parttwo']").attr("maxlength", page.values.su_code_standard);
                element.findP("cd_hin_parttwo").attr("maxlength", (page.values.su_code_standard - 1));

                if (page.header.values.kbn_hin == 1 || page.header.values.kbn_hin == 6) {
                    page.header.options.partthree.validations.cd_genryo.rules.maxlength = (page.values.su_code_standard - 1);
                    element.findP("cd_genryo").attr("maxlength", (page.values.su_code_standard - 1));
                } else {
                    page.header.options.partthree.validations.cd_genryo.rules.maxlength = page.values.su_code_standard;
                    element.findP("cd_genryo").attr("maxlength", page.values.su_code_standard);
                }

                if (element.findP("kansakujyoho")[0].checked == true) {
                    page.header.validatorone.validate();
                } else if (element.findP("kansakujyoho")[1].checked == true) {
                    page.header.validatortwo.validate({
                        targets: element.findP(property)
                    });
                    if (element.findP("codecheck")[0].checked == true) {
                        page.header.validatortwo.validate({
                            targets: element.findP("cd_hin_parttwo")
                        });
                    }
                    else if (element.findP("codecheck")[1].checked == true) {
                        page.header.validatortwo.validate({
                            targets: element.findP("cd_haigo_parttwo")
                        });
                    }
                } else {
                    page.header.validatorthree.validate();
                }
                App.ui.page.notifyAlert.remove("AP008");
                page.header.validator.validate();
            });
        };

        /**
        * 検索処理を定義します。
        */
        page.checkData = function (url, param1, param2, element, cd_code, cd_validate, nm_name, value) {
            if (param1 == "") {
                param1 = "null";
            }
            return $.ajax(App.ajax.odata.get(App.str.format(url, param1, param2))).then(function (result) {
                if (result.value.length > 0) {
                    element.findP(cd_code).val(page.addZero(value, "0000"));
                    element.findP(cd_validate).text(value);
                    element.findP(nm_name).val(value);
                    page.values.isResetKojyo = true;
                    if (cd_code == "cd_kaisha") {
                        page.values.su_code_standard = result.value[0].su_code_standard + 1;
                    }
                } else {
                    element.findP(cd_validate).text("");
                    element.findP(nm_name).val("");
                    page.values.isResetKojyo = false;
                }
                page.header.validator.validate({
                    targets: element.find("[data-prop='" + cd_code + "'], [data-prop='" + nm_name + "']")
                });
            });
        }

        /**
         * Add zero before number。
         */
        page.addZero = function (value, zeroList) {
            var length = zeroList.length;
            if (!App.isUndefOrNullOrStrEmpty(value) && App.isNumeric(value)) {
                value = (zeroList + value).slice(-length);
            }
            return value;
        }


        /**
         * 検索処理を定義します。
         */
        page.header.search = function (isLoading) {

            App.ui.page.notifyAlert.clear();
            var deferred = $.Deferred(),
                query;
            var element = page.header.element;

            //Disabled search button.
            $("#search").prop("disabled", true);

            var sleep = 0;
            var condition = "Object.keys(page.values.isChangeRunning).length == 0";
            App.ui.wait(sleep, condition, 100)
            .then(function () {
                page.header.validator.validate().then(function () {
                    if (element.findP("kansakujyoho")[0].checked == true) {
                        page.header.validatorone.validate();
                    } else if (element.findP("kansakujyoho")[1].checked == true) {
                        page.header.validatortwo.validate();
                    } else {
                        page.header.validatorthree.validate();
                    }
                    if (App.ui.page.notifyAlert.count() > 0) {
                        return;
                    } else {
                        page.options.skip = 0;
                        page.options.filter = page.header.createFilter();

                        if (isLoading) {
                            App.ui.loading.show();
                            App.ui.page.notifyAlert.clear();
                        }

                        return $.ajax(App.ajax.webapi.get(page.header.urls.search, page.options.filter))
                        .done(function (result) {
                            // パーツ開閉の判断
                            if (page.detail.isClose) {
                                // 検索データの保持
                                page.detail.searchData = result;
                            } else {
                                // データバインド
                                page.values.cd_kaisha = page.options.filter.cd_kaisha;
                                page.values.cd_kojyo = page.options.filter.cd_kojyo;
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
                            if (!$("#save").is(":disabled")) {
                                $("#save").prop("disabled", true);
                            }
                        });
                    }
                });
            }).always(function () {
                //Enabled search button.
                $("#search").prop("disabled", false);
            });

            return deferred.promise();

        };

        /**
         * 検索条件のフィルターを定義します。
         */
        page.header.createFilter = function () {
            var criteria = page.header.element.form().data(),
                filters = {};

            /* TODO: 検索条件のフィルターを定義してください。*/
            if (!App.isUndefOrNullOrStrEmpty(page.values.m_kirikae)) {
                filters.m_kirikae = page.values.m_kirikae;
                if (page.values.m_kirikae == App.settings.app.m_kirikae.hyoji) {
                    filters.id_system = 2;
                }
                if (page.values.m_kirikae == App.settings.app.m_kirikae.foodprocs) {
                    filters.id_system = 1;
                }
            }
            if (page.header.element.findP("kansakujyoho")[0].checked == true) {
                filters.kansakujyoho = 1;
            } else if (page.header.element.findP("kansakujyoho")[1].checked == true) {
                filters.kansakujyoho = 2;
            } else {
                filters.kansakujyoho = 3;
            }

            if (!App.isUndefOrNullOrStrEmpty(criteria.cd_kaisha) && criteria.cd_kaisha > 0) {
                filters.cd_kaisha = criteria.cd_kaisha;
            }
            if (!App.isUndefOrNullOrStrEmpty(criteria.cd_kojyo) && criteria.cd_kojyo > 0) {
                filters.cd_kojyo = criteria.cd_kojyo;
            }
            var kbn_sort = 0;
            var mode_sort = $("input[name='jyotai']:checked").val();
            if (criteria.jkn_sort == 0 && mode_sort == 0) {
                kbn_sort = 1;
            }
            if (criteria.jkn_sort == 0 && mode_sort == 1) {
                kbn_sort = 2;
            }
            if (criteria.jkn_sort == 1 && mode_sort == 0) {
                kbn_sort = 3;
            }
            if (criteria.jkn_sort == 1 && mode_sort == 1) {
                kbn_sort = 4;
            }
            filters.kbn_sort = kbn_sort;
            //配合情報で検索
            if (!App.isUndefOrNullOrStrEmpty(criteria.cd_haigo)) {
                filters.cd_haigo = criteria.cd_haigo;
            }
            var flg_seiho_base = page.header.element.findP("flg_seiho_base").prop("checked");
            if (!flg_seiho_base) {
                filters.flg_seiho_base = 0;
            }
            if (!App.isUndefOrNullOrStrEmpty(criteria.no_seiho_kaisha)) {
                filters.no_seiho_kaisha = criteria.no_seiho_kaisha;
            }
            if (!App.isUndefOrNullOrStrEmpty(criteria.no_seiho_shurui)) {
                filters.no_seiho_shurui = criteria.no_seiho_shurui;
            }
            if (!App.isUndefOrNullOrStrEmpty(criteria.no_seiho_nen)) {
                filters.no_seiho_nen = criteria.no_seiho_nen;
            }
            if (!App.isUndefOrNullOrStrEmpty(criteria.no_seiho_renban)) {
                filters.no_seiho_renban = criteria.no_seiho_renban;
            }
            if (!App.isUndefOrNullOrStrEmpty(criteria.nm_haigo)) {
                filters.nm_haigo = criteria.nm_haigo;
            }
            if (!App.isUndefOrNullOrStrEmpty(criteria.kbn_hin)) {
                filters.kbn_hin = criteria.kbn_hin;
            }
            if (!App.isUndefOrNullOrStrEmpty(criteria.cd_bunrui)) {
                filters.cd_bunrui = criteria.cd_bunrui;
            }
            if (!App.isUndefOrNullOrStrEmpty(criteria.nm_seihin)) {
                filters.nm_seihin = criteria.nm_seihin;
            }

            var flg_hinkan = page.header.element.findP("flg_hinkan").prop("checked");
            if (flg_hinkan) {
                filters.flg_hinkan = 0;
            }

            var flg_seizo = page.header.element.findP("flg_seizo").prop("checked");
            if (flg_seizo) {
                filters.flg_seizo = 0;
            }

            var flg_genzai = page.header.element.findP("flg_genzai").prop("checked");
            if (flg_genzai) {
                filters.flg_genzai = 1;
            }

            var flg_mishiyo = page.header.element.findP("flg_mishiyo").prop("checked");
            if (!flg_mishiyo) {
                filters.flg_mishiyo = 0;
            }

            //展開情報で検索
            if (!App.isUndefOrNullOrStrEmpty(criteria.dt_hidzuke)) {
                filters.dt_hidzuke = App.data.getDateString(new Date(criteria.dt_hidzuke), true);
            }
            if (!App.isUndefOrNullOrStrEmpty(criteria.cd_haigo_parttwo)) {
                filters.cd_haigo_parttwo = criteria.cd_haigo_parttwo;
            }
            if (!App.isUndefOrNullOrStrEmpty(criteria.cd_hin_parttwo)) {
                filters.cd_hin_parttwo = criteria.cd_hin_parttwo;
            }
            //原料情報で検索
            if (!App.isUndefOrNullOrStrEmpty(criteria.cd_genryo)) {
                filters.cd_genryo = criteria.cd_genryo;
                var kbn_hin_genryo = page.header.values.kbn_hin;
                if (!App.isUndefOrNullOrStrEmpty(kbn_hin_genryo)) {
                    filters.kbn_hin_genryo = kbn_hin_genryo;
                    if(filters.kbn_hin_genryo == 6 && page.values.m_kirikae == App.settings.app.m_kirikae.hyoji) {
                        filters.kbn_hin_genryo = 1;
                    }
                }
            }
            if (!App.isUndefOrNullOrStrEmpty(criteria.nm_genryo_text)) {
                filters.nm_genryo_text = criteria.nm_genryo_text;
            }
            if (!App.isUndefOrNullOrStrEmpty(criteria.cd_kikaku)) {
                filters.cd_kikaku = criteria.cd_kikaku;
            }
            if (!App.isUndefOrNullOrStrEmpty(criteria.nm_kikaku_shohin)) {
                filters.nm_kikaku_shohin = criteria.nm_kikaku_shohin;
            }
            if (!App.isUndefOrNullOrStrEmpty(criteria.nm_seiho_mei)) {
                filters.nm_seiho_mei = criteria.nm_seiho_mei;
            }
            if (!App.isUndefOrNullOrStrEmpty(criteria.nm_hanbai_mei)) {
                filters.nm_hanbai_mei = criteria.nm_hanbai_mei;
            }

            filters.skip = page.options.skip;
            filters.top = page.options.top;

            return filters;
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
         * 画面明細のバリデーションを定義します。
         */
        page.detail.options.validations = {
            //TODO: 画面明細のバリデーションの定義を記述します。
        };

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
                    onselect: page.detail.select,
                    onchange: page.detail.change
                });

            table = element.find(".datatable"); //列固定にした場合DOM要素が再作成されるため、変数を再取得します。

            page.detail.validator = element.validation(page.createValidator(page.detail.options.validations));
            page.detail.element = element;
            page.detail.dataTable = datatable;

            element.on("click", "#add-item", page.detail.addNewItem);
            element.on("click", "#del-item", page.detail.deleteItem);
            element.on("click", "#nextsearch", page.detail.nextsearch);

            table.on("click", "#haigo-shoshai", page.detail.HaigoShosai);
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
            page.detail.bind([], true);

            //TODO: 画面明細で利用するコントロールのイベントの紐づけ処理をここに記述します。

        };

        /**
         * 画面明細の各行にデータを設定する際のオプションを定義します。
         */
        page.detail.options.bindOption = {
            // TODO: 主キーが直接入力の場合には、修正の場合変更を不可とします。
            appliers: {
                //dt_from: function (value, element) {
                //    element.text(App.data.getDateString(new Date(value)));
                //    return true;
                //},
                //dt_to: function (value, element) {
                //    element.text(App.data.getDateString(new Date(value)));
                //    return true;
                //},
                flg_hinkan: function (value, element) {
                    element.prop("checked", value);
                    return true;
                },
                flg_seizo: function (value, element) {
                    element.prop("checked", value);
                    return true;
                },
                flg_mishiyo: function (value, element) {
                    element.prop("checked", value);
                    return true;
                },
                chk_haigo: function (value, element) {
                    element.prop("checked", value);
                    return true;
                },
            }
        };

        /**
         * 画面明細へのデータバインド処理を行います。
         */
        page.detail.bind = function (data, isNewData) {
            var i, l, item, dataSet, dataCount;

            dataCount = data.Count ? data.Count : 0;
            data = (data.Items) ? data.Items : data;
            if (data.length > 0) {
                dataCount = data[0].total_rows;
            }

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
                if (!App.isUndefOrNullOrStrEmpty(item.nm_seiho)) {
                    row.findP("no_seiho").closest("td").attr("title", item.nm_seiho);
                }

                return row;
            }, true);

            page.options.skip += page.options.top;

            if (dataCount < page.options.skip) {
                page.options.skip = dataCount;
            }

            if (!isNewData) {
                page.detail.element.findP("data_count").text(page.options.skip);
                page.detail.element.findP("data_count_total").text(dataCount);
            }

            //if (dataCount == 0 && !isNewData) {
                //App.ui.page.notifyInfo.clear();
            //}
            if (dataCount <= page.options.skip) {
                $("#nextsearch").hide();
            }
            else {
                $("#nextsearch").show();
            }
            if (dataCount > 0 && !isNewData && page.values.isRoleJisshiKano) {
                $("#haigo-shoshai, #btn-coppy, #btn-copy-data, #btn-delete").prop("disabled", false);
            }
            if (dataCount > 0) {
                $("#btn-tenpu-shiryo").prop("disabled", false);
            }
            if (page.options.skip >= App.settings.base.maxSearchDataCount) {
                App.ui.page.notifyInfo.message(App.messages.base.MS0011).show();
                $("#nextsearch").hide();
            }

            //TODO: 画面明細へのデータバインド処理をここに記述します。
            offsetHeight = $("#nextsearch").is(":visible") ? $("#nextsearch").addClass("show-search").outerHeight() : 0;
            page.detail.dataTable.dataTable("setAditionalOffset", offsetHeight);

            //バリデーションを実行します。
            page.detail.validateList(true);

        };


        /**
         * 次のレコードを検索する処理を定義します。
         */
        page.detail.nextsearch = function () {
            page.options.filter = page.header.createFilter();

            App.ui.loading.show();
            App.ui.page.notifyAlert.clear();

            return $.ajax(App.ajax.webapi.get(page.header.urls.search, page.options.filter))
            .done(function (result) {
                page.detail.bind(result);
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            }).always(function () {
                App.ui.loading.close();
            });

        };

        /**
         * 画面明細の一覧の行が選択された時の処理を行います。
         */
        page.detail.select = function (e, row) {
            //TODO: 単一行を作成する場合は、下記２行を利用します。
            $($(row.element[page.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab.selected")).removeClass("selected").addClass("unselected");
            $(row.element[page.detail.fixedColumnIndex].querySelectorAll(".select-tab")).removeClass("unselected").addClass("selected");
            //TODO: 多段行を作成する場合は、下記２行を有効にし、上記の２行は削除します。
            //$($(row.element[page.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab-2lines.selected")).removeClass("selected").addClass("unselected");
            //$(row.element[page.detail.fixedColumnIndex].querySelectorAll(".select-tab-2lines")).removeClass("unselected").addClass("selected");

            //TODO: 選択行全体に背景色を付ける場合は以下のコメントアウトを解除します。
            if (!App.isUndefOrNull(page.detail.selectedRow)) {
                page.detail.selectedRow.element.find(".selected-row").removeClass("selected-row");
            }
            row.element.find("tr").addClass("selected-row");
            page.detail.selectedRow = row;
        };

        /**
         * 画面明細の一覧の入力項目の変更イベントの処理を行います。
         */
        page.detail.change = function (e, row) {
            var target = $(e.target),
                id = row.element.attr("data-key"),
                property = target.attr("data-prop"),
                entity = page.detail.data.entry(id),
                options = {
                    filter: page.detail.validationFilter
                };

            page.values.isChangeRunning[property] = true;

            page.detail.executeValidation(target, row)
            .then(function () {
                entity[property] = row.element.form().data()[property];
                page.detail.data.update(entity);

                if ($("#save").is(":disabled")) {
                    $("#save").prop("disabled", false);
                }

                //入力行の他の項目のバリデーション（必須チェック以外）を実施します
                page.detail.executeValidation(row.element.find(":input"), row, options);
            }).always(function () {
                delete page.values.isChangeRunning[property];
            });
        };

        /**
         * 画面明細の一覧に新規データを追加します。
         */
        page.detail.addNewItem = function () {
            //TODO:新規データおよび初期値を設定する処理を記述します。
            var newData = {
                //no_seq: page.values.no_seq
            };

            page.detail.data.add(newData);
            page.detail.dataTable.dataTable("addRow", function (tbody) {
                tbody.form(page.detail.options.bindOption).bind(newData);
                return tbody;
            }, true);

            if ($("#save").is(":disabled")) {
                $("#save").prop("disabled", false);
            }
        };

        /**
         * 画面明細の一覧で選択されている行とデータを削除します。
         */
        page.detail.deleteItem = function (e) {
            var element = page.detail.element,
                //TODO: 単一行を作成する場合は、下記を利用します。
                selected = element.find(".datatable .select-tab.selected").closest("tbody");
            //TODO: 多段行を作成する場合は、以下を利用し、上記１行は削除します。
            //selected = element.find(".datatable .select-tab-2lines.selected").closest("tbody");

            if (!selected.length) {
                return;
            }

            page.detail.dataTable.dataTable("deleteRow", selected, function (row) {
                var id = row.attr("data-key"),
                    newSelected;

                row.find(":input").each(function (i, elem) {
                    App.ui.page.notifyAlert.remove(elem);
                });

                if (!App.isUndefOrNull(id)) {
                    var entity = page.detail.data.entry(id);
                    page.detail.data.remove(entity);
                }

                newSelected = row.next().not(".item-tmpl");
                if (!newSelected.length) {
                    newSelected = row.prev().not(".item-tmpl");
                }
                if (newSelected.length) {
                    for (var i = page.detail.fixedColumnIndex; i > -1; i--) {
                        if ($(newSelected[i]).find(":focusable:first").length) {
                            $(newSelected[i]).find(":focusable:first").focus();
                            break;
                        }
                    }
                }
            });

            if ($("#save").is(":disabled")) {
                $("#save").prop("disabled", false);
            }
        };

        /**
         * 画面明細のバリデーションを実行します。
         */
        page.detail.executeValidation = function (targets, row, options) {
            var defaultOptions = {
                targets: targets,
                state: {
                    tbody: row,
                    isGridValidation: true
                }
            },
            execOptions = $.extend(true, {}, defaultOptions, options);

            return page.detail.validator.validate(execOptions);
        };

        /**
         * 画面明細のバリデーションフィルターを設定します。（必須チェックを行わない）
         */
        page.detail.validationFilter = function (item, method, state, options) {
            return method !== "required";
        };

        /**
         * 画面明細のバリデーションフィルターを設定します。（必須チェックを行わない）
         */
        page.header.validationFilter = function (item, method, state, options) {
            return method !== "requiredCustom";
        };

        /**
         * 画面明細の一覧のバリデーションを実行します。
         */
        page.detail.validateList = function (suppressMessage) {
            var validations = [],
                options = {
                    state: {
                        suppressMessage: suppressMessage
                    }
                };

            page.detail.dataTable.dataTable("each", function (row) {
                validations.push(page.detail.executeValidation(row.element.find(":input"), row.element, options));
            });

            return App.async.all(validations);
        };

        //TODO: 以下の page.detail の各宣言は画面仕様ごとにことなるため、不要の場合は削除してください。
<%--
        /**
         * 画面明細の一覧から検索ダイアログを表示します。
         */
        page.detail.showSearchDialog = function (e) {
            var element = page.detail.element,
                tbody = $(e.target).closest("tbody"),
                row;

            page.detail.dataTable.dataTable("getRow", tbody, function (rowObject) {
                row = rowObject.element;
            });

            page.dialogs.searchDialog.element.modal("show");

            //検索ダイアログでデータ選択が実行された時に呼び出される関数を設定しています。
            page.dialogs.searchDialog.dataSelected = function (data) {

                row.findP("cd_torihiki").val(data.cd_torihiki).change();
                row.findP("nm_torihiki").text(data.nm_torihiki);

                delete page.dialogs.searchDialog.dataSelected;
            }
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

    </script>

</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="content-wrap">
        <!--TODO: ヘッダーを定義するHTMLをここに記述します。-->
        <div class="header">
            <div title="検索条件" class="part">
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>会社<span class="red-color">*</span></label>
                    </div>
                    <div class="control col-xs-3 fix-input-xs with-next-col">
                        <input type="tel" data-prop="cd_kaisha" maxlength="4" class="fixed limit-input-digit" style="ime-mode: disabled;" />
                        <label data-prop="cd_kaisha_validate" style="display: none;"></label>
                        <select data-prop="nm_kaisha" class="floated"></select>
                    </div>
                    <div class="control col-xs-2">
                    </div>
                    <div class="control-label col-xs-1">
                        <label>工場<span class="red-color">*</span></label>
                    </div>
                    <div class="control col-xs-3 fix-input-xs with-next-col">
                        <input type="tel" data-prop="cd_kojyo" maxlength="4" class="fixed limit-input-digit" style="ime-mode: disabled;" />
                        <label data-prop="cd_kojyo_validate" style="display: none;"></label>
                        <select data-prop="nm_kojyo" class="floated"></select>
                    </div>
                    <div class="control col-xs-2">
                    </div>
                </div>
                <div class="row">
                    <%--data-prop修正必要--%>
                    <div class="control-label col-xs-2">
                        <label>
                            <input type="radio" name="kensaku_jyoho" checked value="1" data-prop="kansakujyoho" />
                            <i>配合情報で検索</i>
                        </label>
                    </div>
                    <div class="control col-xs-2">
                        <label>
                            <input type="radio" name="kensaku_jyoho" value="2" data-prop="kansakujyoho" />
                            <i>展開情報で検索</i>
                        </label>
                    </div>
                    <div class="control col-xs-2">
                        <label>
                            <input type="radio" name="kensaku_jyoho" value="3" data-prop="kansakujyoho" />
                            <i>原料情報で検索</i>
                        </label>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>ソート順</label>
                    </div>
                    <div class="control col-xs-5">
                        <select style="width: 120px" data-prop="jkn_sort">
                            <option value="0" selected>配合コード</option>
                            <option value="1">製法番号</option>
                        </select>
                        <label>
                            <input type="radio" name="jyotai" checked value="0" />
                            <i>昇順</i>
                        </label>
                        <label>
                            <input type="radio" name="jyotai" value="1" />
                            <i>降順</i>
                        </label>

                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1 joken_haigo">
                        <label class="joken_haigo">配合コード指定</label>
                    </div>
                    <div class="control col-xs-1 joken_haigo">
                        <input type="tel" class="limit-input-digit joken_haigo" data-prop="cd_haigo" />
                    </div>
                    <div class="control col-xs-4 joken_haigo">
                        <label>
                            <input type="checkbox" class="joken_haigo" data-prop="flg_seiho_base" checked />
                            <i class="joken_haigo">原本も含む</i>
                        </label>
                    </div>
                    <div class="control-label col-xs-1 joken_haigo">
                        <label class="joken_haigo">製法番号</label>
                    </div>
                    <div class="control col-xs-5 joken_haigo">
                        <input type="tel" maxlength="4" style="width: 45px; ime-mode: disabled;" class="limit-input-digit" data-prop="no_seiho_kaisha">
                        -
                        <select style="width: 60px;" data-prop="no_seiho_shurui"></select>
                        -
                        <input type="tel" maxlength="2" style="width: 30px; ime-mode: disabled;" data-prop="no_seiho_nen" class="limit-input-digit">
                        -
                        <input type="tel" style="width: 45px; ime-mode: disabled;" maxlength="4" data-prop="no_seiho_renban" class="limit-input-digit">
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1 joken_haigo">
                        <label class="joken_haigo">配合名/コード</label>
                    </div>
                    <div class="control col-xs-5 joken_haigo">
                        <input type="text" data-prop="nm_haigo" style="width: 300px" class="joken_haigo" />
                    </div>
                    <div class="control-label col-xs-1 joken_haigo">
                        <label class="joken_haigo">品区分</label>
                    </div>
                    <div class="control col-xs-5 joken_haigo">
                        <select data-prop="kbn_hin" style="width: 300px" class="joken_haigo"></select>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1 joken_haigo">
                        <label class="joken_haigo">仕掛品分類</label>
                    </div>
                    <div class="control col-xs-5 joken_haigo">
                        <select data-prop="cd_bunrui" style="width: 300px" class="joken_haigo" disabled></select>
                    </div>
                    <div class="control col-xs-1 joken_haigo"></div>
                    <div class="control col-xs-2 joken_haigo">
                        <label>
                            <input type="checkbox" data-prop="flg_hinkan" class="joken_haigo" />
                            <i class="joken_haigo">品管未確認のみ</i>
                        </label>
                    </div>
                    <div class="control col-xs-3 joken_haigo">
                        <label>
                            <input type="checkbox" data-prop="flg_seizo" class="joken_haigo" />
                            <i class="joken_haigo">製造未確認のみ</i>
                        </label>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1 joken_haigo">
                        <label class="joken_haigo">製品名/コード</label>
                    </div>
                    <div class="control col-xs-5 joken_haigo">
                        <input type="text" data-prop="nm_seihin" style="width: 300px" class="joken_haigo" />
                    </div>
                    <div class="control col-xs-1 joken_haigo"></div>
                    <div class="control col-xs-2 joken_haigo">
                        <label>
                            <input type="checkbox" data-prop="flg_genzai" class="joken_haigo" />
                            <i class="joken_haigo">現在有効な版を対象</i>
                        </label>
                    </div>
                    <div class="control col-xs-3 joken_haigo">
                        <label>
                            <input type="checkbox" data-prop="flg_mishiyo" class="joken_haigo" />
                            <i class="joken_haigo">未使用を含む</i>
                        </label>
                    </div>
                </div>
                <%--展開情報--%>
                <div class="row">
                    <div class="control-label col-xs-1 joken_tenkai" style="display: none">
                        <label style="display: none" class="joken_tenkai">日付<span class="red-color">*</span></label>
                    </div>
                    <div class="control col-xs-11 joken_tenkai" style="display: none">
                        <input type="text" data-prop="dt_hidzuke" style="width: 200px; display: none" class="joken_tenkai data-app-format" data-app-format="date" data-role='date' />
                        <label style="display: none" class="joken_tenkai">※指定した日付に有効な版が検索対象となります。</label>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1 joken_tenkai" style="display: none">
                        <label>
                            <input type="radio" name="haigou_seihin" value="1" checked style="display: none" class="joken_tenkai" data-prop="codecheck" />
                            <i style="display: none" class="joken_tenkai">配合コード<span class="red-color">*</span></i>
                        </label>
                    </div>
                    <div class="control col-xs-11 joken_tenkai" style="display: none">
                        <input type="tel" data-prop="cd_haigo_parttwo" style="width: 200px; display: none" class="joken_tenkai haigo_check limit-input-digit" />
                        <button type="button" class="btn btn-xs btn-info joken_tenkai haigo_check btn-haigo" style="min-width: 35px; margin-right: 5px; display: none;">配合検索</button>
                        <label data-prop="nm_haigo_parttwo"></label>
                        <label data-prop="kbn_haigo_parttwo" style="display: none;"></label>
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1 joken_tenkai" style="display: none">
                        <label>
                            <input type="radio" name="haigou_seihin" value="1" style="display: none" class="joken_tenkai" data-prop="codecheck" />
                            <i style="display: none" class="joken_tenkai">製品コード<span class="red-color">*</span></i>
                        </label>
                    </div>
                    <div class="control col-xs-11 joken_tenkai" style="display: none">
                        <input type="tel" data-prop="cd_hin_parttwo" style="width: 200px; display: none" class="joken_tenkai seizo_check limit-input-digit" disabled="disabled" />
                        <button type="button" class="btn btn-xs btn-info joken_tenkai seizo_check btn-seihin" style="min-width: 35px; margin-right: 5px; display: none;" disabled="disabled">製品検索</button>
                        <label data-prop="nm_hin_parttwo"></label>
                    </div>
                </div>
                <%--原料情報で検索--%>
                <div class="row">
                    <div class="control-label col-xs-1 joken_genryo" style="display: none">
                        <label style="display: none" class="joken_genryo">原料コード</label>
                    </div>
                    <div class="control col-xs-5 joken_genryo" style="display: none">
                        <input type="tel" data-prop="cd_genryo" style="width: 200px; display: none" class="joken_genryo limit-input-digit" />
                        <button type="button" class="btn btn-xs btn-info joken_genryo btn-genryo" style="min-width: 35px; margin-right: 5px; display: none">原料検索</button>
                        <label class="overflow-ellipsis" style="width: 57%; float:right" data-prop="nm_genryo"></label>
                        <label data-prop="kbn_hin_genryo" style="display: none"></label>
                    </div>
                    <div class="control-label col-xs-1 joken_genryo" style="display: none">
                        <label style="display: none" class="joken_genryo">原料名</label>
                    </div>
                    <div class="control col-xs-5 joken_genryo" style="display: none">
                        <input type="text" data-prop="nm_genryo_text" style="width: 300px; display: none" class="joken_genryo" />
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1 joken_genryo" style="display: none">
                        <label style="display: none" class="joken_genryo">規格書No</label>
                    </div>
                    <div class="control col-xs-5 joken_genryo" style="display: none">
                        <input type="text" data-prop="cd_kikaku" style="width: 200px; display: none" class="joken_genryo" />
                    </div>
                    <div class="control-label col-xs-1 joken_genryo" style="display: none">
                        <label style="display: none" class="joken_genryo">規格書商品名</label>
                    </div>
                    <div class="control col-xs-5 joken_genryo" style="display: none">
                        <input type="text" data-prop="nm_kikaku_shohin" style="width: 300px; display: none" class="joken_genryo" />
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1 joken_genryo" style="display: none">
                        <label style="display: none" class="joken_genryo">製造社名</label>
                    </div>
                    <div class="control col-xs-5 joken_genryo" style="display: none">
                        <input type="text" data-prop="nm_seiho_mei" style="width: 300px; display: none" class="joken_genryo" />
                    </div>
                    <div class="control-label col-xs-1 joken_genryo" style="display: none">
                        <label style="display: none" class="joken_genryo">販売社名</label>
                    </div>
                    <div class="control col-xs-5 joken_genryo" style="display: none">
                        <input type="text" data-prop="nm_hanbai_mei" style="width: 300px; display: none" class="joken_genryo" />
                    </div>
                </div>
                <div class="header-command">
                    <!--TODO: 検索を定義するHTMLをここに記述します。-->
                    <button type="button" id="search" class="btn btn-sm btn-primary">検索</button>
                </div>
            </div>
        </div>
        <!--TODO: 明細を定義するHTMLをここに記述します。-->
        <div class="detail">
            <!--TODO: 明細をpartにする場合は以下を利用します。-->
            <div>
                <div class="control-label toolbar">
                    <i class="icon-th"></i>
                    <div class="btn-group">
                        <button type="button" id="btn-new" class="btn btn-default btn-xs" disabled="disabled">新規</button>
                    </div>
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
                            <!--TODO: 単一行を作成する場合は、下記を利用します。-->
                            <th style="width: 10px;"></th>
                            <th style="width: 50px;"></th>
                            <th style="width: 150px;">配合コード</th>
                            <th style="width: 580px;">配合名</th>
                            <th style="width: 80px;">品区分</th>
                            <th style="width: 80px;">版</th>
                            <th style="width: 100px;">有効期限開始日</th>
                            <th style="width: 100px;">有効期限終了日</th>
                            <th style="width: 200px;">製法番号</th>
                            <th style="width: 50px;">品管</th>
                            <th style="width: 50px;">製造</th>
                            <th style="width: 50px;">未使用</th>
                            <th class="chk-haigo-header" style="width: 50px;">製造用有無</th>
                            <!--TODO: 多段行を作成する場合は、以下を利用し、上記１行は削除します。
							<th rowspan="2" style="width: 10px;"></th>
                       	</tr>
                        <tr>
						-->
                        </tr>
                    </thead>
                    <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->
                    <tbody class="item-tmpl" style="cursor: default; display: none;">
                        <tr>
                            <!--TODO: 単一行を作成する場合は、以下を利用します。-->
                            <td>
                                <span class="select-tab unselected"></span>
                            </td>
                            <td class="text-center">
                                <button type="button" id="haigo-shoshai" class="btn btn-sm btn-info" disabled="disabled">詳細</button>
                            </td>
                            <td>
                                <label data-prop="cd_haigo"></label>
                            </td>
                            <td>
                                <label data-prop="nm_haigo"></label>
                            </td>
                            <td class="text-center">
                                <label data-prop="nm_kbn_hin"></label>
                            </td>
                            <td class="text-right">
                                <label data-prop="no_han"></label>
                            </td>
                            <td>
                                <label data-prop="dt_from"></label>
                            </td>
                            <td>
                                <label data-prop="dt_to"></label>
                            </td>
                            <td class="overflow-ellipsis" style="float: none;">
                                <label data-prop="no_seiho"></label>
                            </td>
                            <td class="center">
                                <input type="checkbox" data-prop="flg_hinkan" disabled="disabled" />
                            </td>
                            <td class="center">
                                <input type="checkbox" data-prop="flg_seizo" disabled="disabled" />
                            </td>
                            <td class="center">
                                <input type="checkbox" data-prop="flg_mishiyo" disabled="disabled" />
                            </td>
                            <td class="center">
                                <input type="checkbox" data-prop="chk_haigo" disabled="disabled" />
                            </td>
                            <!--TODO: 多段行を作成する場合は、以下を利用し、上記３行は削除します。
							
                            <td rowspan="2">
                                <span class="select-tab-2lines unselected"></span>
                            </td>
                        </tr>
                        <tr>
                        -->
                        </tr>
                    </tbody>
                </table>
                <div class="detail-command">
                    <button type="button" id="nextsearch" class="btn btn-sm btn-primary btn-next-search" style="display: none">次を検索</button>
                </div>
                <!--TODO: 明細をpartにする場合は以下を利用します。-->
                <div class="part-command">
                </div>

            </div>
            <!--TODO: 明細をpartにする場合は以下を利用します。-->
        </div>

    </div>
</asp:Content>

<asp:Content ID="FooterContent" ContentPlaceHolderID="FooterContent" runat="server">
    <div class="command-left">
        <!--TODO: コマンドボタン（左寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" id="btn-clear" class="btn btn-sm btn-primary">クリア</button>
        <button type="button" id="csv-download" class="btn btn-sm btn-primary">CSV</button>
    </div>
    <div class="command">
        <!--TODO: コマンドボタン（右寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" id="btn-coppy" class="btn btn-sm btn-primary" disabled="disabled">新規（コピー）</button>
        <button type="button" id="btn-copy-data" class="btn btn-sm btn-primary" disabled="disabled">配合データコピー</button>
        <button type="button" id="btn-tenpu-shiryo" class="btn btn-sm btn-primary" disabled="disabled">添付資料</button>
        <button type="button" id="btn-delete" class="btn btn-sm btn-primary" disabled="disabled">削除</button>

    </div>

</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>
