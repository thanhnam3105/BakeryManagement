<%@ Page Title="204_製法登録" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="204_SeihoToroku.aspx.cs" Inherits="Tos.Web.Pages.SeihoToroku" %>

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

        .padding-left-50 {
            padding-left: 50px;
        }

        .vertical-align-top {
            vertical-align: top;
        }

        .hidden-204 {
            display: none!important;
        }

        .dt-container {
            overflow-x: scroll!important;
        }

        .custom-model table td {
            border: 1px solid #cccccc!important;
        }

        .custom-model .modal-body {
            padding: 10px!important;
        }

        .padding-2 {
            padding: 2px;
        }

        .btn-next-search {
            width: 200px;
        }
    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("_204_SeihoToroku", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {
                skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
                top: 50,//App.settings.base.maxInputDataCount,   // TODO:取得するデータ数を指定します。
                filter: ""
            },
            values: {
                isChangeRunning: {},
                isResetKojyo: true,
                addTrDertail: "<tr class='hidden-204 {0}'>{1}"
                            //+ "<td><label data-prop='nm_kaisha'></label></td>"
                            + " <td><label data-prop='nm_kojyo'></label></td>"
                            + " <td class='center'><label data-prop='nm_kigo'></label></td>"
                            + " <td class='center'><input type='checkbox' data-prop='flg_denso_taisho'/></td>"
                            + " <td class='center'><a class='haigo-data' data-prop='nm_jyotai'></a></td>"
                            + " <td class='center'><button class='btn btn-xs biko-class'><img src='../Images/iconpaperspace.png' alt='icon' style='height: 20px;'></button></td>"
                            + " <td><label data-prop='nm_tanto_shinsei'></label></td>"
                            + " <td><label data-prop='dt_seiho_shinsei'></label></td>"
                            + " <td><label data-prop='dt_seiho_sakusei'></label></td>"
                            + " <td><label data-prop='nm_tanto_denso'></label></td>"
                            + " <td><label data-prop='dt_denso_toroku'></label></td>"
                        + " </tr>",
                isFirstDialog: {},
                lstKojyo: [],
                cd_kaisha_daihyo: null,
                cd_kojyo_daihyo: null,
                flg_daihyo_kojyo: null,
                marginLeftBikoFixed: 0,
                marginTopBikoFixed: 0,
                id_kino_search: 10,
                id_kino_ippan: 20,
                id_kino_hon_seizo: 30,
                id_data_jisshi_kano: 9,
                isRole_search: false,
                isRole_ippan: false,
                cd_kengen_6: 6,
                listSearch: [],
                isRoleFull: false
            },
            urls: {
                search: "../api/SeihoToroku",
                save: "../api/SeihoToroku",
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
                seizoKojoShiteiDialog: "Dialogs/704_SeizoKojoShitei_Dialog.aspx",
                denyApply: "../api/SeihoToroku",
                mainMenu: "MainMenu.aspx"
            },
            header: {
                options: {},
                values: {},
                urls: {
                    vw_no_seiho_shurui: "../Services/ShisaQuickService.svc/vw_no_seiho_shurui?$orderby=cd_hin_syurui,no_sort",
                    vw_kaisha_kojyo: "../Services/ShisaQuickService.svc/vw_kaisha_kojyo?$filter=cd_kaisha eq {0}{1}&$orderby=cd_kaisha,cd_kojyo",
                    ma_kaisha: "../Services/ShisaQuickService.svc/ma_kaisha?{0}&$orderby=cd_kaisha",
                    ma_literal: "../Services/ShisaQuickService.svc/ma_literal?$filter=cd_category eq '{0}' &$orderby=no_sort",
                }
            },
            detail: {
                options: {},
                values: {
                    listDelete: [],
                    isChanged: false,
                    bikoElement: {}
                },
                urls: {
                    getHaigo: "../api/SeihoToroku/GetHaigo",
                    checkHaigoStatus: "../api/SeihoToroku/CheckHaigoStatus",
                }
            },
            dialogs: {
                seizoKojoShiteiDialog: {},
            },
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
                    page.detail.validateBiko();
                    if (App.ui.page.notifyAlert.Count > 0) {
                        return;
                    }
                }).then(function () {
                    if (App.ui.page.notifyAlert.count() == 0) {
                        if (!page.detail.data.isChanged() && page.detail.values.listDelete.length == 0) {
                            App.ui.page.notifyInfo.clear();
                            App.ui.page.notifyInfo.message(App.messages.app.AP0054).show();

                            return;
                        }
                        var options = {
                            text: App.messages.app.AP0004
                        };

                        page.dialogs.confirmDialog.confirm(options)
                        .then(function () {
                            var changeSets = {
                                seiho: page.detail.data.getChangeSet(),
                                deleteData: page.detail.values.listDelete,
                                cd_kaisha_login: App.ui.page.user.cd_kaisha,
                                id_user_login: App.ui.page.user.EmployeeCD
                            };
                            //TODO: データの保存処理をここに記述します。
                            return $.ajax(App.ajax.webapi.post(page.urls.save, changeSets))
                                .then(function (result) {

                                    //TODO: データの保存成功時の処理をここに記述します。

                                    //最後に再度データを取得しなおします。
                                    page.detail.data = App.ui.page.dataSet();
                                    page.detail.values.listDelete = [];
                                    return App.async.all([page.header.beforeSearh(false)]);
                                }).then(function () {

                                    App.ui.page.notifyInfo.message(App.messages.base.MS0002).show();
                                }).fail(function (error) {
                                    if (error.status === App.settings.base.conflictStatus) {
                                        // TODO: 同時実行エラー時の処理を行っています。
                                        // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                                        //page.header.search(false);
                                        App.ui.page.notifyAlert.clear();
                                        App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
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
                    }
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
         * Validate biko。
         */
        page.detail.validateBiko = function () {
            page.detail.dataTable.dataTable("each", function (row) {
                var element = row.element.find("tr");

                for (var l = 0; l < element.length; l++) {
                    var tr = $(element[l]);

                    var id = tr.attr("data-key"),
                        entity = page.detail.data.entry(id);

                    var iret = 0;
                    if (!App.isUndefOrNullOrStrEmpty(entity.biko)) {
                        for (var i = 0; i < entity.biko.length; i++) {
                            var c = entity.biko.charCodeAt(i);
                            if ((c >= 0x0 && c < 0x81) || (c == 0xf8f0) || (c >= 0xff61 && c < 0xffa0) || (c >= 0xf8f1 && c < 0xf8f4)) {
                                iret = iret + 1;
                            }
                            else {
                                iret = iret + 2;
                            }
                        }
                    }

                    if (iret <= 256) {
                        tr.find("button").closest("td").removeClass("control-required");
                    } else {
                        tr.find("button").closest("td").addClass("control-required");
                        App.ui.page.notifyAlert.message(App.str.format(App.messages.base.maxbytelength, { name: "備考", byte: "128", param: "256" }), tr.find("button")).show();
                    }
                }
            });
        }
        /**
         * 製造工場変更。
         */
        page.commands.SeizoKojoHenko = function () {
            var element = page.detail.element,
                selected = element.find(".datatable .select-tab.selected").closest("tbody");

            if (!selected.length) {
                App.ui.page.notifyAlert.clear();
                App.ui.page.notifyAlert.message(App.messages.app.AP0008).show();

                return;
            }
            App.ui.page.notifyAlert.clear();
            var id = selected.attr("data-key"),
                entity = page.detail.data.entry(id);

            if (entity.no_seiho.substring(6, 8) == "01" && page.values.isRole_ippan) {
                App.ui.page.notifyAlert.clear();
                App.ui.page.notifyAlert.message(App.messages.app.AP0096).show();

                return;
            }

            if (App.isUndefOrNullOrStrEmpty(entity.lstKojyo) && App.isUndefOrNullOrStrEmpty(entity.lstDelete)) {
                page.dialogs.seizoKojoShiteiDialog.options.parameter.flg_shoki = 1;
                page.dialogs.seizoKojoShiteiDialog.options.parameter.no_gamen = App.settings.app.no_gamen.seiho_toroku;
                page.dialogs.seizoKojoShiteiDialog.options.parameter.no_seiho = entity.no_seiho;
                page.dialogs.seizoKojoShiteiDialog.options.parameter.lstKojyo = [];
                page.dialogs.seizoKojoShiteiDialog.options.parameter.lstDelete = [];

                var trListSearch = selected.find("tr");
                var listSearch = [];
                for (var i = 0; i < trListSearch.length; i++) {
                    var elementRow = trListSearch[i];

                    var idSearch = $(elementRow).attr("data-key"),
                       entitySearch = page.detail.data.entry(idSearch);
                    var item = {
                        cd_kaisha: entitySearch.cd_kaisha,
                        cd_kojyo: entitySearch.cd_kojyo
                    }
                    listSearch.push(item)
                }

                page.dialogs.seizoKojoShiteiDialog.options.parameter.listSearch = listSearch;

            } else {
                page.dialogs.seizoKojoShiteiDialog.options.parameter.flg_shoki = 0;
                page.dialogs.seizoKojoShiteiDialog.options.parameter.no_gamen = App.settings.app.no_gamen.seiho_toroku;
                page.dialogs.seizoKojoShiteiDialog.options.parameter.no_seiho = entity.no_seiho;
                page.dialogs.seizoKojoShiteiDialog.options.parameter.lstKojyo = entity.lstKojyo;
                page.dialogs.seizoKojoShiteiDialog.options.parameter.lstDelete = entity.lstDelete;
                page.dialogs.seizoKojoShiteiDialog.options.parameter.listSearch = page.values.listSearch;
            }



            page.dialogs.seizoKojoShiteiDialog.element.modal("show");

            page.values.isFirstDialog = false;
            page.dialogs.seizoKojoShiteiDialog.dataSelected = function (data) {

                page.values.lstKojyo = data.lstKojyo;
                page.values.listSearch = data.listSearch;
                var idList = selected.attr("data-key"),
                entityList = page.detail.data.entry(idList);
                entityList.lstKojyo = data.lstKojyo;
                entityList.lstDelete = data.lstDelete;

                page.detail.updateDataDelete(data.lstDelete);

                if (page.values.lstKojyo.length > 0) {
                    for (var i = 0; i < page.values.lstKojyo.length; i++) {
                        var item = page.values.lstKojyo[i];
                        if (!item.flg_database || (item.flg_database && item.flgSearch)) {
                            item.cd_kaisha = parseInt(item.cd_kaisha);
                            item.cd_kojyo = parseInt(item.cd_kojyo);
                            if (selected.find("." + item.cd_kaisha + item.cd_kojyo).length == 0) {
                                //Check exists company
                                var isExistsKaisha = selected.find(".kaisha-" + item.cd_kaisha);

                                if (isExistsKaisha.length > 0) {
                                    var kaishaElement = selected.find(".kaisha-" + item.cd_kaisha);

                                    var elementCurrent = kaishaElement[(kaishaElement.length - 1)];

                                    var tbody = $(elementCurrent);
                                    tbody.after(App.str.format(page.values.addTrDertail, (parseInt(item.cd_kaisha) + "" + parseInt(item.cd_kojyo)) + " selected-row isNew", ""));
                                    page.detail.data.add(item);
                                    tbody.next().form(page.detail.options.bindOption).bind(item);

                                    tbody.next().attr("data-key", item.__id);
                                    tbody.next().addClass("kaisha-" + parseInt(item.cd_kaisha));
                                    tbody.next().attr("cd_kojyo", parseInt(item.cd_kojyo));
                                    tbody.next().attr("cd_kaisha", parseInt(item.cd_kaisha));

                                    if (item.no_seiho.substring(6, 8) == "01" && page.values.isRole_ippan) {
                                        tbody.next().findP("flg_denso_taisho").prop("disabled", true);
                                    }
                                    if (entityList.kbn_status == App.settings.app.kbn_status.henshuchu) {
                                        tbody.next().findP("flg_denso_taisho").prop("disabled", true);
                                    }
                                    if (App.isUndefOrNullOrStrEmpty(item.biko)) {
                                        tbody.next().find(".biko-class").find("img").attr("src", "../Images/iconpaper.png");
                                    }

                                    var idMain = selected.find(".main").attr("data-key"),
                                        entityMain = page.detail.data.entry(idMain);

                                    if (App.isUndefOrNullOrStrEmpty(entityMain.row_seiho)) {
                                        entityMain.row_seiho = selected.find("tr").length;
                                        selected.attr("data-key", selected.find(".main").attr("data-key"));
                                    }
                                    entityMain.row_seiho = entityMain.row_seiho + 1;

                                    var rowMain = selected.find(".main");
                                    rowMain.find(".select-tab").closest("td").attr("rowspan", entityMain.row_seiho);
                                    rowMain.findP("no_seiho").closest("td").attr("rowspan", entityMain.row_seiho);
                                    rowMain.findP("nm_seiho").closest("td").attr("rowspan", entityMain.row_seiho);

                                    selected = element.find(".datatable .select-tab.selected").closest("tbody");

                                    var numberKaisha = selected.find(".kaisha-" + item.cd_kaisha);
                                    var rowKaisha = selected.find(".main-" + item.cd_kaisha);
                                    rowKaisha.findP("nm_kaisha").closest("td").attr("rowspan", numberKaisha.length);
                                } else {
                                    selected = element.find(".datatable .select-tab.selected").closest("tbody");
                                    var tr = selected.find("tr");
                                    var elementCurrent = tr[(tr.length - 1)];


                                    var tbody = $(elementCurrent);

                                    tbody.after(App.str.format(page.values.addTrDertail, (item.cd_kaisha + "" + item.cd_kojyo) + " main-" + item.cd_kaisha + " kaisha-" + item.cd_kaisha + " selected-row isNew", "<td><label data-prop='nm_kaisha'></label></td>"));
                                    tbody.next().attr("cd_kojyo", item.cd_kojyo);
                                    tbody.next().attr("cd_kaisha", item.cd_kaisha);
                                    page.detail.data.add(item);
                                    tbody.next().form(page.detail.options.bindOption).bind(item);
                                    tbody.next().attr("data-key", item.__id);
                                    if (item.no_seiho.substring(6, 8) == "01" && page.values.isRole_ippan) {
                                        tbody.next().findP("flg_denso_taisho").prop("disabled", true);
                                    }
                                    if (entityList.kbn_status == App.settings.app.kbn_status.henshuchu) {
                                        tbody.next().findP("flg_denso_taisho").prop("disabled", true);
                                    }
                                    if (App.isUndefOrNullOrStrEmpty(item.biko)) {
                                        tbody.next().find(".biko-class").find("img").attr("src", "../Images/iconpaper.png");
                                    }

                                    var idMain = selected.find(".main").attr("data-key"),
                                        entityMain = page.detail.data.entry(idMain);

                                    if (App.isUndefOrNullOrStrEmpty(entityMain.row_seiho)) {
                                        entityMain.row_seiho = selected.find("tr").length;
                                        selected.attr("data-key", selected.find(".main").attr("data-key"));
                                    }
                                    entityMain.row_seiho = entityMain.row_seiho + 1;

                                    var rowMain = selected.find(".main");
                                    rowMain.find(".select-tab").closest("td").attr("rowspan", entityMain.row_seiho);
                                    rowMain.findP("no_seiho").closest("td").attr("rowspan", entityMain.row_seiho);
                                    rowMain.findP("nm_seiho").closest("td").attr("rowspan", entityMain.row_seiho);
                                }
                                page.detail.element.find(".hidden-204").removeClass("hidden-204");
                            }
                        }
                    }
                }

                var trList = selected.find("tr");

                for (var m = 0; m < trList.length; m++) {
                    var elementRow = trList[m];
                    var id = $(elementRow).attr("data-key"),
                        entity = page.detail.data.entry(id);

                    if (!page.keyExists((entity.cd_kaisha + "" + entity.cd_kojyo), page.values.lstKojyo)) {
                        if ($(elementRow).hasClass("main-" + entity.cd_kaisha) && selected.find(".kaisha-" + entity.cd_kaisha).length > 0) {
                            if ($(elementRow).next().findP("nm_kaisha").length == 0) {
                                $(elementRow).next().findP("nm_kojyo").closest("td").before("<td><label data-prop='nm_kaisha'>" + entity.nm_kaisha + "</label></td>");
                            }
                            if (idList == id) {
                                $(elementRow).next().findP("nm_kaisha").closest("td").before("<td><label data-prop='nm_seiho'></label>" + entity.nm_seiho + "</label></td>");
                                $(elementRow).next().findP("nm_seiho").closest("td").before("<td><label data-prop='no_seiho'>" + entity.no_seiho + "</label></td>");
                                $(elementRow).next().findP("no_seiho").closest("td").before("<td><label class='select-tab selected'></label></label></td>");
                            }

                            if ($(elementRow).next().length > 0 && idList == id) {
                                var idNext = $(elementRow).next().attr("data-key");
                                var entityNext = page.detail.data.entry(idNext);
                                var selectedCurrent = element.find(".datatable .select-tab.selected").closest("tbody");

                                $(elementRow).next().addClass("main");
                                $(elementRow).next().addClass("main-" + entityNext.cd_kaisha);

                                var numberKaisha = selectedCurrent.find(".kaisha-" + entityNext.cd_kaisha);
                                var rowKaisha = selectedCurrent.find(".main-" + entityNext.cd_kaisha);

                                if (numberKaisha.length > 1) {
                                    rowKaisha.findP("nm_kaisha").closest("td").attr("rowspan", numberKaisha.length);
                                } else {
                                    rowKaisha.findP("nm_kaisha").closest("td").attr("rowspan", "");
                                }
                            }

                            if ($(elementRow).hasClass("main-" + entity.cd_kaisha) && $(elementRow).next().hasClass("kaisha-" + entity.cd_kaisha)) {
                                $(elementRow).next().addClass("main-" + entity.cd_kaisha);
                            }
                        }
                        if (selected.find("tr").length > 0) {
                            var idMain = selected.find(".main").attr("data-key"),
                                entityMain = page.detail.data.entry(idMain);

                            if (App.isUndefOrNullOrStrEmpty(entityMain.row_seiho)) {
                                entityMain.row_seiho = selected.find("tr").length;
                                var datakeyRemove = $(trList[(m + 1)]).closest("tr").attr("data-key");
                                selected.attr("data-key", datakeyRemove);
                                var entityRemove = page.detail.data.entry(datakeyRemove);
                                entityRemove.lstKojyo = data.lstKojyo;
                                entityRemove.lstDelete = data.lstDelete;
                            }
                            entityMain.row_seiho = entityMain.row_seiho - 1;

                            var rowMain = selected.find(".main");
                            rowMain.find(".select-tab").closest("td").attr("rowspan", entityMain.row_seiho);
                            rowMain.findP("no_seiho").closest("td").attr("rowspan", entityMain.row_seiho);
                            rowMain.findP("nm_seiho").closest("td").attr("rowspan", entityMain.row_seiho);

                            $(elementRow).remove();

                            selected = element.find(".datatable .select-tab.selected").closest("tbody");

                            var numberKaisha = selected.find(".kaisha-" + entity.cd_kaisha);
                            var rowKaisha = selected.find(".main-" + entity.cd_kaisha);
                            if (numberKaisha.length > 1) {
                                rowKaisha.findP("nm_kaisha").closest("td").attr("rowspan", numberKaisha.length);
                            } else {
                                rowKaisha.findP("nm_kaisha").closest("td").attr("rowspan", "");
                            }

                            if (idList == id && numberKaisha.length > 0) {
                                var trNumber = selected.find("tr");
                                if (trNumber.length > 1) {
                                    rowKaisha.find("[data-prop='nm_seiho'], [data-prop='no_seiho'], .select-tab").closest("td").attr("rowspan", trNumber.length);
                                }
                                var dataKey = rowKaisha.closest("tr").attr("data-key");
                                selected.attr("data-key", dataKey);
                                idList = dataKey;
                                var entityMain = page.detail.data.entry(dataKey);
                                entityMain.lstKojyo = data.lstKojyo;
                                entityMain.lstDelete = data.lstDelete;
                            }
                            if (numberKaisha.length == 0) {
                                var trNumber = selected.find("tr");
                                if (trNumber.length > 1) {
                                    rowKaisha.find("[data-prop='nm_seiho'], [data-prop='no_seiho'], .select-tab").closest("td").attr("rowspan", trNumber.length);
                                }
                                var dataKey = $(trList[(m + 1)]).closest("tr").attr("data-key");
                                var isMain = $(trList[(m + 1)]).closest("tr").hasClass("main");
                                if (!App.isUndefOrNullOrStrEmpty(dataKey) && isMain) {
                                    $(trList[(m + 1)]).attr("data-key", dataKey);
                                    idList = dataKey;
                                    var entityMain = page.detail.data.entry(dataKey);
                                    entityMain.lstKojyo = data.lstKojyo;
                                    entityMain.lstDelete = data.lstDelete;
                                }
                            }
                            page.detail.data.remove(entity);
                        }
                    }

                    selected = element.find(".datatable .select-tab.selected").closest("tbody");
                    var trListEnd = selected.find("tr");

                    var rowMain = selected.find(".main");
                    rowMain.find(".select-tab").closest("td").attr("rowspan", trListEnd.length);
                    rowMain.findP("no_seiho").closest("td").attr("rowspan", trListEnd.length);
                    rowMain.findP("nm_seiho").closest("td").attr("rowspan", trListEnd.length);
                }

                //if ($("#save").is(":disabled")) {
                //    $("#save").prop("disabled", false);
                //}
            }
        }

        /**
         * Check key exists。
         */
        page.detail.updateDataDelete = function (listData) {
            if (listData.length > 0) {
                var listSeiho = findObjectByKey("no_seiho", listData[0].no_seiho);
            }
            for (var l = 0; l < listData.length; l++) {
                page.detail.values.listDelete.push(listData[l]);
            }
        }

        function findObjectByKey(key, value) {
            page.detail.values.listDelete = page.detail.values.listDelete.filter(function (obj) {
                return obj[key] !== value;
            });
        }

        /**
         * Check key exists。
         */
        page.keyExists = function keyExists(key, data) {
            if (!data || (data.constructor !== Array && data.constructor !== Object)) {
                return false;
            }
            for (var i = 0; i < data.length; i++) {
                var item = parseInt(data[i].cd_kaisha) + "" + parseInt(data[i].cd_kojyo);
                if (item === key) {
                    return true;
                }
            }
            return false;
        }

        /**
         * 製造工場変更。
         */
        page.commands.DenyApply = function () {
            var element = page.detail.element,
               selected = element.find(".datatable .select-tab.selected").closest("tbody");

            App.ui.page.notifyAlert.clear();

            if (!selected.length) {
                App.ui.page.notifyAlert.clear();
                App.ui.page.notifyAlert.message(App.messages.app.AP0008).show();

                return;
            }

            var id = selected.attr("data-key"),
               entity = page.detail.data.entry(id);

            var options = {
                text: App.messages.app.AP0063
            };

            if (entity.flg_edit) {
                App.ui.page.notifyAlert.clear();
                App.ui.page.notifyAlert.message(App.messages.app.AP0061).show();

                return;
            }

            if (entity.no_seiho.substring(6, 8) == "01" && page.values.isRole_ippan) {
                App.ui.page.notifyAlert.clear();
                App.ui.page.notifyAlert.message(App.messages.app.AP0062).show();

                return;
            }

            page.dialogs.confirmDialog.confirm(options)
            .then(function () {
                App.ui.loading.show();
                var parameter = {
                    no_seiho: entity.no_seiho,
                    nm_shori: "申請取消",
                    nm_ope: "登録",
                    cd_kaisha_login: App.ui.page.user.cd_kaisha,
                    id_user_login: App.ui.page.user.EmployeeCD

                };
                return $.ajax(App.ajax.webapi.put(page.urls.denyApply, parameter))
                .then(function (result) {
                    page.detail.data = App.ui.page.dataSet();
                    page.detail.values.listDelete = [];

                    return App.async.all([page.header.beforeSearh(false)]);
                }).then(function () {
                        App.ui.page.notifyInfo.message(App.messages.base.MS0008).show();
                }).fail(function (error) {
                    if (error.status === App.settings.base.conflictStatus) {
                        App.ui.page.notifyAlert.message(App.messages.app.AP0061).show();
                        return;
                    }
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                }).always(function () {
                    App.ui.loading.close();
                });
            });
        }

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
        App.ui.page.onclose = function () {

            var detail,
                closeMessage = App.messages.base.exit;

            if (page.detail.data) {
                detail = page.detail.data.getChangeSet();
                if (detail.created.length || detail.updated.length || detail.deleted.length) {
                    return closeMessage;
                }
            }
        };

        /**
        * 画面の初期化処理を行います。
        */
        page.initialize = function () {

            App.ui.loading.show();
            $(".wrap, .footer").addClass("theme-yellow");

            page.initializeControl();
            page.initializeControlEvent();

            page.header.initialize();
            page.detail.initialize();

            //TODO: ヘッダー/明細以外の初期化の処理を記述します。

            page.loadMasterData().then(function (result) {
                var element = page.header.element;

                element.findP("no_seiho_kaisha").val(("0000" + App.ui.page.user.cd_kaisha).slice(-4));
                element.findP("no_seiho_nen").val(new Date().getFullYear().toString().substr(2, 2));
                var dateNow = new Date();
                var dateBefore3 = dateNow.setMonth(dateNow.getMonth() - 3);
//                element.findP("dt_seiho_sakusei_from").val(App.data.getDateString(new Date(dateBefore3)));
//                element.findP("dt_seiho_sakusei_to").val(App.data.getDateString(new Date()));
                element.findP("dt_seiho_sakusei_from").val(App.data.getDateString(new Date(dateBefore3),true));
                element.findP("dt_seiho_sakusei_to").val(App.data.getDateString(new Date(),true));

                return page.loadDialogs();
            }).then(function (result) {
                //TODO: 画面の初期化処理成功時の処理を記述します。
                getKengenGamen(App.settings.app.id_gamen.seiho_toroku).then(function (results) {
                    page.kengenGamen = results;
                    var isRoleScreen = false;
                    $.each(results, function (index, item) {
                        if (item.id_kino == page.values.id_kino_search && item.id_data == page.values.id_data_jisshi_kano) {
                            isRoleScreen = true;
                            page.values.isRole_search = true;
                        }
                        if (item.id_kino == page.values.id_kino_ippan && item.id_data == page.values.id_data_jisshi_kano) {
                            isRoleScreen = true;
                            page.values.isRole_ippan = true;
                        }
                        if (item.id_kino == page.values.id_kino_hon_seizo && item.id_data == page.values.id_data_jisshi_kano) {
                            isRoleScreen = true;
                            page.values.isRoleFull = true;
                        }
                    });

                    if (page.values.isRoleFull) {
                        page.values.isRole_search = false;
                        page.values.isRole_ippan = false;
                    } else {
                        if (page.values.isRole_ippan) {
                            page.values.isRole_search = false;
                        }
                    }
                    if (!isRoleScreen) {
                        window.location.href = page.urls.mainMenu;
                    }
                });
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

            }).always(function (result) {
                if (App.ui.page.user.cd_kengen != page.values.cd_kengen_6) {
                    page.header.element.findP("no_seiho_kaisha").prop("disabled", true);
                }
                page.header.element.find(":input:first").focus();
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
            $("#SeizoKojoHenko").on("click", page.commands.SeizoKojoHenko);
            $("#denyapply").on("click", page.commands.DenyApply);
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

                page.kaishaKojoJotai(page.header.urls.ma_kaisha, "nm_kaisha", "cd_kaisha", "nm_kaisha", "", true).then(function () {
                    var cd_kaisha = element.findP("nm_kaisha").val();
                    if (!App.ui.page.user.flg_kaishakan_sansyo) {
                        element.findP("nm_kaisha").val(App.ui.page.user.cd_kaisha);
                        cd_kaisha = App.ui.page.user.cd_kaisha;

                        element.find("[data-prop='cd_kaisha'], [data-prop='nm_kaisha']").prop("disabled", true);
                    }

                    if (!App.isUndefOrNullOrStrEmpty(cd_kaisha)) {
                        element.findP("cd_kaisha").val(page.addZero(cd_kaisha, "0000"));
                        element.findP("cd_kaisha_validate").text(cd_kaisha);


                        page.kaishaKojoJotai(page.header.urls.vw_kaisha_kojyo, "nm_kojyo", "cd_kojyo", "nm_kojyo", cd_kaisha, true).then(function () {
                            var cd_kojyo = element.findP("nm_kojyo").val();

                            if (!App.ui.page.user.flg_kojyokan_sansyo) {
                                element.findP("nm_kojyo").val(App.ui.page.user.cd_busho);
                                cd_kojyo = App.ui.page.user.cd_busho;

                                element.find("[data-prop='cd_kojyo'], [data-prop='nm_kojyo']").prop("disabled", true);
                            }

                            if (!App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                                element.findP("cd_kojyo").val(page.addZero(cd_kojyo, "0000"));
                                element.findP("cd_kojyo_validate").text(cd_kojyo);
                            }
                        });
                    }
                });
            }).then(function () {

                page.kaishaKojoJotai(page.header.urls.ma_literal, "kbn_denso_jyotai", "cd_literal", "nm_literal", App.settings.app.cd_category.kbn_denso_jyotai, true).then(function () {
                    page.header.element.findP("kbn_denso_jyotai").val(page.header.element.find("[data-prop='kbn_denso_jyotai'] option:eq(1)").val());
                });
            });

            //TODO: マスタデータのロード処理を実装後、下の1行を削除してください。
            return App.async.success();
        };

        /**
         * Load combobox 製造会社, 製造工場, 状態。
         */
        page.kaishaKojoJotai = function (url, dataPropElement, codeProps, nameProps, param, blank) {

            //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
            return $.ajax(App.ajax.odata.get(App.str.format(url, param))).then(function (result) {

                var element = page.header.element.findP(dataPropElement);
                element.children().remove();
                App.ui.appendOptions(
                    element,
                    codeProps,
                    nameProps,
                    result.value,
                    blank
                );
            });
        };

        /**
         * 共有ダイアログのロード処理を実行します。
         */
        page.loadDialogs = function () {

            //TODO: 共有ダイアログデータを取得し、画面にバインドする処理を記述します。
            return App.async.all({

                confirmDialog: $.get(page.urls.confirmDialog),
                seizoKojoShiteiDialog: $.get(page.urls.seizoKojoShiteiDialog)
            }).then(function (result) {

                $("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;

                $("#dialog-container").append(result.successes.seizoKojoShiteiDialog);
                page.dialogs.seizoKojoShiteiDialog = seizoKojoShiteiDialog;
                page.dialogs.seizoKojoShiteiDialog.initialize();

                CustomDialog.initialize();
            });
        }

        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.validations = {
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
            cd_kaisha: {
                rules: {
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
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits,
                    existsCode: App.messages.app.AP0010
                }
            },
            cd_kojyo: {
                rules: {
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
                    name: "製造工場"
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                    digits: App.messages.base.digits,
                    existsCode: App.messages.app.AP0010
                }
            },
            dt_seiho_sakusei_from: {
                rules: {
                    datestring: true,
                    maxlength: 10,
                    rangeYearMonthDay: [ "1950/01/01", "3000/12/31"],
                    lessthan_ymd_to: [true, page.header, "dt_seiho_sakusei_to"],
                },
                options: {
                    name: "製法作成日(開始)",
                },
                messages: {
                    datestring: App.messages.base.datestring,
                    maxlength: App.messages.base.maxlength,
                    rangeYearMonthDay: App.messages.base.range,
                    lessthan_ymd_to: App.messages.base.MS0014
                }
            },
            dt_seiho_sakusei_to: {
                rules: {
                    datestring: true,
                    maxlength: 10,
                    rangeYearMonthDay: ["1950/01/01", "3000/12/31"]
                },
                options: {
                    name: "製法作成日(終了)",
                },
                messages: {
                    datestring: App.messages.base.datestring,
                    maxlength: App.messages.base.maxlength,
                    rangeYearMonthDay: App.messages.base.range
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
            },
            nm_tanto_shinsei: {
                rules: {
                    maxbytelength: 60
                },
                options: {
                    name: "製法申請者",
                    byte: 30
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            nm_tanto_denso: {
                rules: {
                    maxbytelength: 60
                },
                options: {
                    name: "伝送登録者",
                    byte: 30
                },
                messages: {
                    maxbytelength: App.messages.base.maxbytelength
                }
            },
            //TODO: 画面ヘッダーのバリデーションの定義を記述します。
        };

        /**
         * 画面ヘッダーの初期化処理を行います。
         */
        page.header.initialize = function () {

            var element = $(".header");
            page.header.validator = element.validation(page.createValidator(page.header.options.validations));
            page.header.element = element;

            element.find(":input[data-role='date']").datepicker();
            element.find(":input[data-role='date']").on("keyup", App.data.addSlashForDateString);
            //TODO: 画面ヘッダーの初期化処理をここに記述します。
            //TODO: 画面ヘッダーで利用するコントロールのイベントの紐づけ処理をここに記述します。
            element.on("click", "#search", function () {
                page.header.beforeSearh(true);
            });
            element.on("change", ":input", page.header.change);

        };

        /**
         * Change header。
         */
        page.header.change = function (e) {
            var target = $(e.target),
                element = page.header.element,
                id = element.attr("data-key"),
                property = target.attr("data-prop");

            if ($("#nextsearch").hasClass("show-search")) {
                $("#nextsearch").removeClass("show-search").hide();
                App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
            }
            else if (page.detail.searchData) {
                // 保持検索データの消去
                page.detail.searchData = undefined;
                App.ui.page.notifyInfo.message(App.messages.base.MS0010).show();
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
                    if (cd_kaisha != "" && App.isNumeric(cd_kaisha) && App.ui.page.user.flg_kojyokan_sansyo) {
                        cd_kaisha = parseInt(cd_kaisha);
                        page.kaishaKojoJotai(page.header.urls.vw_kaisha_kojyo, "nm_kojyo", "cd_kojyo", "nm_kojyo", cd_kaisha, true).then(function () {
                            var cd_kojyo = element.findP("nm_kojyo").val();
                            if (!App.isUndefOrNullOrStrEmpty(cd_kojyo)) {
                                element.findP("cd_kojyo").val(page.addZero(cd_kojyo, "0000"));
                            }
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

            if (property == "no_seiho_kaisha" || property == "no_seiho_nen" || property == "no_seiho_renban") {
                var value = target.val(),
                    zeroList = "0000";

                if (property == "no_seiho_nen") {
                    zeroList = "00";
                }
                if (value != "") {
                    element.findP(property).val(page.addZero(value, zeroList));
                }
            }

            page.header.validator.validate();
        }

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
         * 次のレコードを検索する処理を定義します。
         */
        page.detail.nextsearch = function () {

            page.options.filter.skip = page.options.skip;

            App.ui.loading.show();
            App.ui.page.notifyAlert.clear();

            return $.ajax(App.ajax.webapi.get(page.urls.search, page.options.filter))
            .done(function (result) {
                page.detail.bind(result);
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            }).always(function () {
                App.ui.loading.close();
            });

        };

        var CustomDialog = {
            values: {
                entity: {},
                isArea: true
            }
        }
        /**
         * Show biko edit textarea。
         */
        page.detail.showBikoArea = function (e) {
            var target = $(e.target),
               id = target.closest("tr").attr("data-key"),
               entity = page.detail.data.entry(id);

            page.values.marginLeftBikoFixed = e.clientX;
            page.values.marginTopBikoFixed = e.clientY;

            CustomDialog.values.entity = entity;
            CustomDialog.values.isArea = true;

            page.detail.values.bikoElement = target.closest("tr");

            var element = $(".custom-model");
            element.modal("show");
        }

        CustomDialog.initialize = function () {
            var element = $(".custom-model");

            $(".custom-model").on("shown.bs.modal", function () {
                $(".custom-model").find(".modal-dialog").css("margin-left", page.values.marginLeftBikoFixed + "px");
                $(".custom-model").find(".modal-dialog").css("margin-top", page.values.marginTopBikoFixed + "px");
                $('.modal-backdrop').removeClass("modal-backdrop");
                element.findP("biko").prop("disabled", false);

                if (CustomDialog.values.isArea) {
                    element.find(".modal-dialog").css("width", "18%");
                    element.findP("biko").val(CustomDialog.values.entity.biko);
                    element.findP("biko").show();
                    element.find(".haigo-show").hide();
                    element.findP("biko").focus();

                    if (page.values.isRole_search || (CustomDialog.values.entity.no_seiho.substring(6, 8) == "01" && page.values.isRole_ippan)) {
                        element.findP("biko").prop("disabled", true);
                    } else {
                        App.ui.loading.show();
                        $.ajax(App.ajax.webapi.get(page.detail.urls.checkHaigoStatus, { no_seiho: CustomDialog.values.entity.no_seiho }))
                        .done(function (result) {
                            if (result) {
                                element.findP("biko").prop("disabled", true);
                            }
                        }).always(function () {
                            App.ui.loading.close();
                        });
                    }

                    if (CustomDialog.values.entity.kbn_status == App.settings.app.kbn_status.henshuchu || CustomDialog.values.entity.cd_jyotai == App.settings.app.cd_jyotai.cd_jyotai_2 || (App.isNull(CustomDialog.values.entity.flg_biko) && !App.isUndef(CustomDialog.values.entity.flg_biko))) {
                        element.findP("biko").prop("disabled", true);
                    }
                } else {
                    element.find(".modal-dialog").css("width", "35%");
                    element.findP("biko").hide();
                    element.find(".haigo-show").show();
                    App.ui.loading.show();
                    $.ajax(App.ajax.webapi.get(page.detail.urls.getHaigo, { no_seiho: CustomDialog.values.entity.no_seiho, kbn_status: App.settings.app.cd_category.kbn_status }))
                    .done(function (result) {
                        var table = element.find(".haigo-show");
                        table.find("tbody:visible").remove();
                        for (i = 0, l = result.length; i < l; i++) {
                            item = result[i];
                            clone = table.find(".item-tmpl").clone();
                            clone.form().bind(item);
                            clone.appendTo(table).removeClass("item-tmpl").show();
                        }
                    }).always(function () {
                        App.ui.loading.close();
                    });

                }

            });

            $(window).on("hidden.bs.modal", function () {
                element.findP("biko").hide();
                element.find(".haigo-show").hide();
                element.find("tbody").not(".item-tmpl").remove();
            });

            $(".custom-model").on("change", ":input", function () {

                CustomDialog.values.entity.biko = element.findP("biko").val();

                var iret = 0;
                for (var i = 0; i < CustomDialog.values.entity.biko.length; ++i) {
                    var c = CustomDialog.values.entity.biko.charCodeAt(i);
                    if ((c >= 0x0 && c < 0x81) || (c == 0xf8f0) || (c >= 0xff61 && c < 0xffa0) || (c >= 0xf8f1 && c < 0xf8f4)) {
                        iret = iret + 1;
                    }
                    else {
                        iret = iret + 2;
                    }
                }
                App.ui.page.notifyAlert.remove(page.detail.values.bikoElement.find("button"));
                page.detail.data.update(CustomDialog.values.entity);

                if (App.isUndefOrNullOrStrEmpty(CustomDialog.values.entity.biko)) {
                    page.detail.values.bikoElement.find(".biko-class").find("img").attr("src", "../Images/iconpaper.png");
                } else {
                    page.detail.values.bikoElement.find(".biko-class").find("img").attr("src", "../Images/iconpaperspace.png");
                }

                if (iret <= 256) {
                    page.detail.values.bikoElement.findP("biko").val(CustomDialog.values.entity.biko);
                    page.detail.values.bikoElement.find("button").closest("td").removeClass("control-required");
                } else {
                    page.detail.values.bikoElement.find("button").closest("td").addClass("control-required");
                    App.ui.page.notifyAlert.message(App.str.format(App.messages.base.maxbytelength, { name: "備考", byte: "128", param: "256" }), page.detail.values.bikoElement.find("button")).show();
                }
                //if ($("#save").is(":disabled")) {
                //    $("#save").prop("disabled", false);
                //}

            });
        }

        /**
         * Show haigo。
         */
        page.detail.showHaigo = function (e) {
            var target = $(e.target),
               id = target.closest("tr").attr("data-key"),
               entity = page.detail.data.entry(id);

            page.values.marginLeftBikoFixed = e.clientX;
            page.values.marginTopBikoFixed = e.clientY;
            CustomDialog.values.entity = entity;

            CustomDialog.values.isArea = false;
            $(".custom-model").modal("show");
        }

        /**
        * Check data change before search。
        */
        page.header.beforeSearh = function (isLoading) {
            page.header.validator.validate().done(function () {
                page.options.skip = 0;
                page.options.filter = page.header.createFilter();

                if (page.detail.data.isChanged() || page.detail.values.listDelete.length > 0) {
                    var options = {
                        text: App.messages.app.AP0053
                    };

                    page.dialogs.confirmDialog.confirm(options)
                    .then(function () {
                        page.detail.values.listDelete = [];
                        page.header.search(isLoading);
                    });
                } else {
                    page.header.search(isLoading);
                }
            });
        }

        /**
         * 検索処理を定義します。
         */
        page.header.search = function (isLoading) {

            var deferred = $.Deferred(),
                query;

            //TODO: データ取得サービスの URLとオプションを記述します。
            page.values.lstKojyo = [];
            page.detail.values.listDelete = [];

            if (isLoading) {
                App.ui.loading.show();
                App.ui.page.notifyAlert.clear();
            }

            return $.ajax(App.ajax.webapi.get(page.urls.search, page.options.filter))
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

            return deferred.promise();

        };

        /**
         * 検索条件のフィルターを定義します。
         */
        page.header.createFilter = function () {
            var criteria = page.header.element.form().data(),
                filters = {};

            /* TODO: 検索条件のフィルターを定義してください。*/
            if (!App.isUndefOrNull(criteria.no_seiho_kaisha) && criteria.no_seiho_kaisha > 0) {
                filters.no_seiho_kaisha = criteria.no_seiho_kaisha;
            }
            if (!App.isUndefOrNull(criteria.no_seiho_shurui) && criteria.no_seiho_shurui.length > 0) {
                filters.no_seiho_shurui = criteria.no_seiho_shurui;
            }
            if (!App.isUndefOrNull(criteria.no_seiho_nen) && criteria.no_seiho_nen.length > 0) {
                filters.no_seiho_nen = criteria.no_seiho_nen;
            }
            if (!App.isUndefOrNull(criteria.no_seiho_renban) && criteria.no_seiho_renban.length > 0) {
                filters.no_seiho_renban = criteria.no_seiho_renban;
            }

            if (!App.isUndefOrNull(criteria.nm_seiho) && criteria.nm_seiho.length > 0) {
                filters.nm_seiho = criteria.nm_seiho;
            }
            if (!App.isUndefOrNull(criteria.nm_kaisha) && criteria.nm_kaisha.length > 0) {
                filters.nm_kaisha = criteria.nm_kaisha;
            }
            if (!App.isUndefOrNull(criteria.kbn_denso_jyotai) && criteria.kbn_denso_jyotai.length > 0) {
                filters.kbn_denso_jyotai = criteria.kbn_denso_jyotai;
            }
            if (!App.isUndefOrNull(criteria.nm_kojyo) && criteria.nm_kojyo.length > 0) {
                filters.nm_kojyo = criteria.nm_kojyo;
            }
            if (!App.isUndefOrNullOrStrEmpty(criteria.dt_seiho_sakusei_from)) {
                filters.dt_seiho_sakusei_from = App.data.getDateString(new Date(criteria.dt_seiho_sakusei_from), true);
            }
            if (!App.isUndefOrNullOrStrEmpty(criteria.dt_seiho_sakusei_to)) {
                filters.dt_seiho_sakusei_to = App.data.getDateString(new Date(criteria.dt_seiho_sakusei_to), true);
            }

            if (!App.isUndefOrNull(criteria.nm_tanto_shinsei) && criteria.nm_tanto_shinsei.length > 0) {
                filters.nm_tanto_shinsei = criteria.nm_tanto_shinsei;
            }
            if (!App.isUndefOrNull(criteria.nm_tanto_denso) && criteria.nm_tanto_denso.length > 0) {
                filters.nm_tanto_denso = criteria.nm_tanto_denso;
            }
            var kbn_sort = 0;
            var mode_sort = $("input[name='modesort']:checked").val();
            if (criteria.kbn_sort == 0 && mode_sort == 0) {
                kbn_sort = 1;
            }
            if (criteria.kbn_sort == 0 && mode_sort == 1) {
                kbn_sort = 2;
            }
            if (criteria.kbn_sort == 1 && mode_sort == 0) {
                kbn_sort = 3;
            }
            if (criteria.kbn_sort == 1 && mode_sort == 1) {
                kbn_sort = 4;
            }

            filters.kbn_sort = kbn_sort;

            filters.cd_category_27 = App.settings.app.cd_category.kbn_denso_jyotai;
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

            table.on("click", ".biko-class", page.detail.showBikoArea);
            table.on("click", ".haigo-data", page.detail.showHaigo);

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
                flg_denso_taisho: function (value, element) {
                    element.prop("checked", value);
                    return true;
                }
            }
        };

        /**
         * 画面明細へのデータバインド処理を行います。
         */
        page.detail.bind = function (data, isNewData) {
            var i, l, item, dataSet, dataCount;

            dataCount = data.Count ? data.Count : 0;
            data = (data.Items) ? data.Items : data;

            if (page.options.skip === 0) {
                dataSet = App.ui.page.dataSet();
                page.detail.dataTable.dataTable("clear");
            } else {
                dataSet = page.detail.data;
            }
            page.detail.data = dataSet;

            var tbody;
            var rowNew, cd_kaisha_old, j;
            for (var i = 0; i < data.length; i++) {
                var item = data[i];

                if (item.flg_daihyo_kojyo == 1) {
                    item.nm_kojyo = "★" + (item.nm_kojyo == null ? "" : item.nm_kojyo);
                }


                if (item.row_num == 1) {
                    page.detail.dataTable.dataTable("addRow", function (tbody) {
                        (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
                        item.idMain = [];
                        if (item.row_num == 1 && item.row_seiho > 1) {
                            tbody.find(".select-tab").closest("td").attr("rowspan", item.row_seiho);
                            tbody.findP("no_seiho").closest("td").attr("rowspan", item.row_seiho);
                            tbody.findP("nm_seiho").closest("td").attr("rowspan", item.row_seiho);
                            tbody.findP("nm_kaisha").closest("td").attr("rowspan", item.row_kaisha);
                        }
                        if (item.kbn_status == App.settings.app.kbn_status.henshuchu) {
                            tbody.findP("flg_denso_taisho").prop("disabled", true);
                        }
                        tbody.form(page.detail.options.bindOption).bind(item);
                        cd_kaisha_old = item.cd_kaisha;
                        tbody.find("tr").attr("data-key", item.__id);
                        tbody.find("tr").addClass("main-" + item.cd_kaisha);
                        tbody.find("tr").addClass("kaisha-" + item.cd_kaisha);
                        tbody.find("tr").addClass("main");
                        tbody.find("tr").addClass(item.cd_kaisha + "" + item.cd_kojyo);
                        if (item.no_seiho.substring(6, 8) == "01" && page.values.isRole_ippan) {
                            tbody.find("tr").findP("flg_denso_taisho").prop("disabled", true);
                        }

                        if (App.isUndefOrNullOrStrEmpty(item.biko)) {
                            tbody.find("tr").find(".biko-class").find("img").attr("src", "../Images/iconpaper.png");
                        }

                        item.idMain.push(item.__id);

                        if (item.cd_jyotai == App.settings.app.cd_jyotai.cd_jyotai_2) {
                            tbody.findP("flg_denso_taisho").prop("disabled", true);
                            tbody.findP("biko-class").prop("disabled", true);
                        }
                        tbody.find("tr").attr("cd_kojyo", item.cd_kojyo);
                        tbody.find("tr").attr("cd_kaisha", item.cd_kaisha);

                        rowNew = tbody;

                        return tbody;
                    }, false);
                } else {
                    var idMain = rowNew.attr("data-key"),
                        entityMain = page.detail.data.entry(idMain);

                    (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
                    if (cd_kaisha_old == item.cd_kaisha) {
                        rowNew.append(App.str.format(page.values.addTrDertail, (item.cd_kaisha + "" + item.cd_kojyo) + " kaisha-" + item.cd_kaisha));
                    } else {
                        if (item.row_kaisha == 1) {
                            rowNew.append(App.str.format(page.values.addTrDertail, (item.cd_kaisha + "" + item.cd_kojyo) + " main-" + item.cd_kaisha + " kaisha-" + item.cd_kaisha, "<td><label data-prop='nm_kaisha'></label></td>"));
                        } else {
                            rowNew.append(App.str.format(page.values.addTrDertail, (item.cd_kaisha + "" + item.cd_kojyo) + " main-" + item.cd_kaisha + " kaisha-" + item.cd_kaisha, "<td rowspan=" + item.row_kaisha + "><label data-prop='nm_kaisha'></label></td>"));
                        }
                    }
                    rowNew.find("." + (item.cd_kaisha + "" + item.cd_kojyo)).attr("cd_kojyo", item.cd_kojyo);
                    rowNew.find("." + (item.cd_kaisha + "" + item.cd_kojyo)).attr("cd_kaisha", item.cd_kaisha);
                    rowNew.find("." + (item.cd_kaisha + "" + item.cd_kojyo)).form(page.detail.options.bindOption).bind(item);
                    if (item.kbn_status == App.settings.app.kbn_status.henshuchu) {
                        rowNew.find("." + (item.cd_kaisha + "" + item.cd_kojyo)).findP("flg_denso_taisho").prop("disabled", true);
                    }
                    if (App.isUndefOrNullOrStrEmpty(item.biko)) {
                        rowNew.find("." + (item.cd_kaisha + "" + item.cd_kojyo)).find(".biko-class").find("img").attr("src", "../Images/iconpaper.png");
                    }
                    cd_kaisha_old = item.cd_kaisha;
                    if (item.no_seiho.substring(6, 8) == "01" && page.values.isRole_ippan) {
                        rowNew.find("." + (item.cd_kaisha + "" + item.cd_kojyo)).findP("flg_denso_taisho").prop("disabled", true);
                    }
                    rowNew.find("tr ." + (item.cd_kaisha + "" + item.cd_kojyo)).attr("data-key", item.__id);
                    if (item.cd_jyotai == App.settings.app.cd_jyotai.cd_jyotai_2) {
                        rowNew.find("." + (item.cd_kaisha + "" + item.cd_kojyo)).findP("flg_denso_taisho").prop("disabled", true);
                        rowNew.find("." + (item.cd_kaisha + "" + item.cd_kojyo)).findP("biko-class").prop("disabled", true);
                    }

                    entityMain.idMain.push(item.__id);
                }
                if (i == (data.length - 1)) {
                    page.detail.element.find(".hidden-204").removeClass("hidden-204");
                }
            }

            $(page.detail.element.findP("no_seiho")[page.options.skip + 1]).trigger("click");
            if (page.values.isRole_search) {
                page.detail.element.findP("flg_denso_taisho").prop("disabled", true);
            }

            page.options.skip += page.options.top;

            if (dataCount < page.options.skip) {
                page.options.skip = dataCount;
            }

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

            if (dataCount == 0 && !isNewData) {
                //App.ui.page.notifyInfo.clear();
                //App.ui.page.notifyInfo.message(App.messages.app.AP0007).show();
                $("#SeizoKojoHenko, #denyapply").prop("disabled", true);
                $("#save").prop("disabled", true);
            } else {
                if (page.values.isRole_ippan || page.values.isRoleFull) {
                    $("#save").prop("disabled", false);
                }
            }

            if (page.options.skip >= App.settings.base.maxSearchDataCount) {
                App.ui.page.notifyInfo.message(App.messages.base.MS0011).show();
                $("#nextsearch").hide();

            }

            if (dataCount > 0 && !isNewData && !page.values.isRole_search) {
                $("#SeizoKojoHenko, #denyapply").prop("disabled", false);
            }
            offsetHeight = $("#nextsearch").is(":visible") ? $("#nextsearch").addClass("show-search").outerHeight() : 0;
            page.detail.dataTable.dataTable("setAditionalOffset", offsetHeight);
            //TODO: 画面明細へのデータバインド処理をここに記述します。


            //バリデーションを実行します。
            page.detail.validateList(true);

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
                id = target.closest("tr").attr("data-key"),
                property = target.attr("data-prop"),
                entity = page.detail.data.entry(id),
                options = {
                    filter: page.detail.validationFilter
                },
                row = target.closest("tr");

            page.values.isChangeRunning[property] = true;

            page.detail.executeValidation(target, row)
            .then(function () {
                if (property == "flg_denso_taisho") {
                    entity[property] = 0;
                    if (row.findP("flg_denso_taisho").prop("checked")) {
                        entity[property] = 1;
                    }
                } else {
                    entity[property] = row.form().data()[property];
                }

                page.detail.data.update(entity);

                //if ($("#save").is(":disabled")) {
                //    $("#save").prop("disabled", false);
                //}

                //入力行の他の項目のバリデーション（必須チェック以外）を実施します
                page.detail.executeValidation(row.find(":input"), row, options);
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

            //if ($("#save").is(":disabled")) {
            //    $("#save").prop("disabled", false);
            //}
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

            //if ($("#save").is(":disabled")) {
            //    $("#save").prop("disabled", false);
            //}
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
        <!--TODO: 検索条件を定義するHTMLをここに記述します。-->
        <div class="header">
            <div title="検索条件" class="part">
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>製法番号</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="tel" maxlength="4" style="width: 45px; ime-mode: disabled;" class="limit-input-digit" data-prop="no_seiho_kaisha">
                        -
                        <select style="width: 60px;" data-prop="no_seiho_shurui"></select>
                        -
                        <input type="tel" maxlength="2" style="width: 30px; ime-mode: disabled;" data-prop="no_seiho_nen" class="limit-input-digit">
                        -
                        <input type="tel" style="width: 45px; ime-mode: disabled;" maxlength="4" data-prop="no_seiho_renban" class="limit-input-digit">
                    </div>
                    <div class="control-label col-xs-1">
                        <label>状態</label>
                    </div>
                    <div class="control col-xs-3">
                        <select data-prop="kbn_denso_jyotai" style="width: 202px"></select>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>製法申請者</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="text" data-prop="nm_tanto_shinsei" />
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>製造会社</label>
                    </div>
                    <div class="control col-xs-3 fix-input-xs">
                        <input type="tel" data-prop="cd_kaisha" maxlength="4" class="fixed limit-input-digit" style="ime-mode: disabled;" />
                        <label data-prop="cd_kaisha_validate" style="display: none;"></label>
                        <select data-prop="nm_kaisha" class="floated"></select>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>製造工場</label>
                    </div>
                    <div class="control col-xs-3 fix-input-xs">
                        <input type="tel" data-prop="cd_kojyo" maxlength="4" class="fixed limit-input-digit" style="ime-mode: disabled;" />
                        <label data-prop="cd_kojyo_validate" style="display: none;"></label>
                        <select data-prop="nm_kojyo" class="floated"></select>
                    </div>
                    <div class="control-label col-xs-1">
                        <label>伝送登録者</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="text" data-prop="nm_tanto_denso" />
                    </div>
                </div>
                <div class="row">
                    <div class="control-label col-xs-1">
                        <label>製法名</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="text" data-prop="nm_seiho" />
                    </div>
                    <div class="control-label col-xs-1">
                        <label>製法作成日</label>
                    </div>
                    <div class="control col-xs-3">
                        <input type="tel" data-prop="dt_seiho_sakusei_from" style="width: 90px;" data-app-format="date" data-role='date' class="data-app-format" />
                        <span style="width: 10px;">～</span>
                        <input type="tel" data-prop="dt_seiho_sakusei_to" style="width: 90px;" data-app-format="date" data-role='date' class="data-app-format" />
                    </div>
                    <div class="control-label col-xs-1">
                        <label>並べ替え順</label>
                    </div>
                    <div class="control col-xs-1">
                        <select data-prop="kbn_sort">
                            <option value="0">製法申請日</option>
                            <option value="1">製法作成日</option>
                        </select>
                    </div>
                    <div class="control col-xs-2 padding-left-50">

                        <label>
                            <input type="radio" name="modesort" data-prop="sort_asc_desc" class="vertical-align-top" value="0" checked="checked" />
                            昇順
                        </label>

                        <label>
                            <input type="radio" name="modesort" data-prop="sort_asc_desc" class="vertical-align-top" value="1" />
                            降順
                        </label>
                    </div>
                </div>
                <div class="header-command sort-container">
                    <button type="button" id="search" class="btn btn-sm btn-primary">検索</button>
                </div>
            </div>
        </div>
        <!--TODO: 明細を定義するHTMLをここに記述します。-->
        <div class="detail">
            <!--TODO: 明細をpartにする場合は以下を利用します。-->
            <div class="control-label toolbar">
                <i class="icon-th"></i>
                <%--<div class="btn-group">
                    <button type="button" class="btn btn-default btn-xs" id="add-item">追加</button>
                    <button type="button" class="btn btn-default btn-xs" id="del-item">削除</button>
                </div>--%>
                <span class="data-count">
                    <span data-prop="data_count"></span>
                    <span>/</span>
                    <span data-prop="data_count_total"></span>
                </span>
            </div>
            <table class="datatable ellipsis" style="overflow-x: scroll;">
                <!--TODO: 明細一覧のヘッダーを定義するHTMLをここに記述します。-->
                <thead>
                    <tr>
                        <!--TODO: 単一行を作成する場合は、下記を利用します。-->
                        <th style="width: 10px;"></th>
                        <th style="width: 150px;">製法番号</th>
                        <th style="width: 200px;">製法名</th>
                        <th style="width: 150px;">製造会社
                        </th>
                        <th style="width: 150px;">製造工場
                        </th>
                        <th style="width: 50px;">記号
                        </th>
                        <th style="width: 50px;">伝送
                        </th>
                        <th style="width: 50px;">状態
                        </th>
                        <th style="width: 50px;">備考
                        </th>
                        <th style="width: 150px;">製法申請者
                        </th>
                        <th style="width: 90px;">製法申請日
                        </th>
                        <th style="width: 90px;">製法作成日
                        </th>
                        <th style="width: 150px;">伝送登録者
                        </th>
                        <th style="width: 90px;">伝送登録日
                        </th>
                        <!--TODO: 多段行を作成する場合は、以下を利用し、上記１行は削除します。
						<th rowspan="2" style="width: 10px;"></th>
                    </tr>
                    <tr>
					-->
                    </tr>
                </thead>
                <!--TODO: 明細一覧の明細行を定義するHTMLをここに記述します。-->
                <tbody class="hidden-204 item-tmpl" style="cursor: default; display: none;">
                    <tr>
                        <!--TODO: 単一行を作成する場合は、以下を利用します。-->
                        <td>
                            <label class="select-tab unselected"></label>
                        </td>
                        <td>
                            <label data-prop="no_seiho"></label>
                        </td>
                        <td>
                            <label data-prop="nm_seiho"></label>
                        </td>
                        <td>
                            <label data-prop="nm_kaisha"></label>
                        </td>
                        <td>
                            <label data-prop="nm_kojyo"></label>
                        </td>
                        <td class="center">
                            <label data-prop="nm_kigo"></label>
                        </td>
                        <td class="center">
                            <input type="checkbox" data-prop="flg_denso_taisho" />
                        </td>
                        <td class="center">
                            <a class="haigo-data" data-prop="nm_jyotai"></a>
                        </td>
                        <td class="center">
                            <button class="btn btn-xs biko-class">
                                <img src="../Images/iconpaperspace.png" alt="icon" style="height: 20px;">
                                <input type="text" data-prop="biko" style="display: none;" />
                            </button>
                        </td>
                        <td>
                            <label data-prop="nm_tanto_shinsei"></label>
                        </td>
                        <td>
                            <label data-prop="dt_seiho_shinsei"></label>
                        </td>
                        <td>
                            <label data-prop="dt_seiho_sakusei"></label>
                        </td>
                        <td>
                            <label data-prop="nm_tanto_denso"></label>
                        </td>
                        <td>
                            <label data-prop="dt_denso_toroku"></label>
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="detail-command">
                <button type="button" id="nextsearch" class="btn btn-sm btn-primary btn-next-search" style="display: none">次を検索</button>
            </div>
            <div class="part-command">
            </div>

            <!--TODO: 明細をpartにする場合は以下を利用します。-->
            <%--</div>--%>
        </div>
    </div>

</asp:Content>

<asp:Content ID="FooterContent" ContentPlaceHolderID="FooterContent" runat="server">
    <div class="command-left">
        <!--TODO: コマンドボタン（左寄せ配置）を定義するHTMLをここに記述します。-->
    </div>
    <div class="command">
        <!--TODO: コマンドボタン（右寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" id="SeizoKojoHenko" class="btn btn-sm btn-primary" disabled="disabled">製造工場変更</button>
        <button type="button" id="denyapply" class="btn btn-sm btn-primary" disabled="disabled">申請取消</button>
        <button type="button" id="save" class="btn btn-sm btn-primary" disabled="disabled">保存</button>
    </div>

</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
    <div class="modal custom-model" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <textarea data-prop="biko" style="height: 153px; width: 100%; display: none;"></textarea>
                    <table class="haigo-show" style="display: none;">
                        <tbody class="item-tmpl" style="display: none;">
                            <tr>
                                <td class="text-right padding-2" style="width: 100px">
                                    <label data-prop="cd_haigo"></label>
                                </td>
                                <td class="padding-2" style="width: 350px">
                                    <label data-prop="nm_haigo"></label>
                                </td>
                                <td class="padding-2" style="width: 140px">
                                    <label data-prop="nm_status"></label>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</asp:Content>
