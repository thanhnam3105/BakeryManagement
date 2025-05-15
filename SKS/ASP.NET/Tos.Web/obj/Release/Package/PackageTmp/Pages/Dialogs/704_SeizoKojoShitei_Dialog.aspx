<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="704_SeizoKojoShitei_Dialog.aspx.cs" Inherits="Tos.Web.Pages.Dialogs.SeizoKojoShitei_Dialog" %>
<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver1.6)】 Template--%>

    <style type="text/css">

        #seizoKojoShiteiDialog .modal-dialog .modal-body table {
            margin-top: -2px;
        }

        #seizoKojoShiteiDialog .modal-dialog .modal-body table .HideBorderTop {
            border-top-color: #f9f9f9;
        }

        #seizoKojoShiteiDialog .modal-dialog .modal-body table .HideBorderTop td {
            border-top-color:#f9f9f9; 
        }

        #seizoKojoShiteiDialog .modal-dialog .modal-body table .ShowBorderBottom {
            border-bottom: 2px solid #dddddd;
        }

        #seizoKojoShiteiDialog .modal-dialog .modal-body table .ShowBorderBottom td {
            border-bottom: 1px solid #dddddd;
        }

    </style>
    <script type="text/javascript">

        /**
         * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
         */
        var seizoKojoShiteiDialog = {
            options: {
                parameter: {
                    no_gamen:null,
                    no_seiho: null,
                    cd_kaisha: null,
                    cd_kojyo: null,
                    flg_daihyo_kojyo: null,
                    flg_edit: 1,
                    flg_shoki: 1,
                    flg_denso_jyotai:0,
                    lstCheckboxKojyo: [],
                    lstKaisha: [],
                    lstCheckboxKaisha: [],
                    radio_select: null,
                    lstKojyo: [],
                    lstDelete: [],
                    modeReadOnly: false,
                    listSearch: [],

                }
            },
            urls: {
                vw_kaisha_kojyo: "../Services/ShisaQuickService.svc/vw_kaisha_kojyo?$orderby=cd_kaisha,cd_kojyo",
                ma_seiho_denso: "../Services/ShisaQuickService.svc/ma_seiho_denso?$filter=no_seiho eq '{0}' &$orderby=cd_kaisha,cd_kojyo"
            },
            confirmDialog: {},
            initValues: {},
            values: {
                cd_kaisha: null,                // cd_kaisha param is set from parent page
                cd_kojyo: null,                 // cd_kojyo param is set from parent page
                kengen: null,                   // kengen param is set from parent page (BELONGS TO SESSION PROCESS)
                flg_daihyo: null,               // flg_daihyo param is set from parent page
                flg_disabled: null,             // flg_disabled param is set from parent page
                parentGamen: null,              // flg_daihyo param is set from parent page
                flg_kaishakan_sansyo: null,     // flg_kaishakan_sansyo param is set from parent page (BELONGS TO SESSION PROCESS)
                flg_kojyokan_sansyo: null,      // flg_kojyokan_sansyo param is set from parent page (BELONGS TO SESSION PROCESS)
                jyushinchu: null,               // jyushinchu param is set from parent page
                no_seiho: null,                 // no_seiho param is set from parent page
                kaishaKojyoList: null,          // kaishaList param is set from parent page
            }
        };

        /**
         * 検索ダイアログの初期化処理を行います。
         */
        seizoKojoShiteiDialog.initialize = function () {
            var element = $("#seizoKojoShiteiDialog"),
                contentHeight = $(window).height() * 80 / 100;
            
            element.on("hidden.bs.modal", seizoKojoShiteiDialog.hidden);
            element.on("shown.bs.modal", seizoKojoShiteiDialog.shown);
            
            seizoKojoShiteiDialog.element = element;
            
            element.find(".modal-body").css("max-height", contentHeight);
            element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
            element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

            element.find(".data-count-kojyo").hide();
            
            element.on("click", ".icon-plus-sign", seizoKojoShiteiDialog.showDataRow);
            element.on("click", ".icon-minus-sign",seizoKojoShiteiDialog.hideDataRow);
            element.on("change", ":input", seizoKojoShiteiDialog.change);
            element.on("click", ".select", seizoKojoShiteiDialog.select);

            element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
            element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

            seizoKojoShiteiDialog.notifyInfo =
                 App.ui.notify.info(element, {
                     container: "#seizoKojoShiteiDialog .dialog-slideup-area .info-message",
                     bodyContainer: ".modal-body .detail",
                     show: function () {
                         element.find(".info-message").show();
                     },
                     clear: function () {
                         element.find(".info-message").hide();
                     }
                 });
            seizoKojoShiteiDialog.notifyAlert =
                App.ui.notify.alert(element, {
                    container: "#seizoKojoShiteiDialog .dialog-slideup-area .alert-message",
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
        };

        /**
         * change detail dialog
         */
        seizoKojoShiteiDialog.change = function (e) {
            var target = $(e.target),
                property = target.attr("data-prop"),
                row = target.closest("tbody");

            if (property == "checkbox_kaisha") {
                var cd_kaisha = App.num.format(row.findP("cd_kaisha").text(), "0000");
                if (target.prop("checked")) {
                    row.findP("icon_plus").removeClass("icon-plus-sign").addClass("icon-minus-sign");
                    seizoKojoShiteiDialog.element.find("." + cd_kaisha).show();
                    seizoKojoShiteiDialog.element.find(".td-kojyo-" + cd_kaisha).show();
                    seizoKojoShiteiDialog.element.find(".checkbox-kojyo-" + cd_kaisha).prop("checked", true);
                    seizoKojoShiteiDialog.element.find(".radio-select-" + cd_kaisha).show();
                }
                else {

                    if (seizoKojoShiteiDialog.element.find(".checkbox-kojyo-" + cd_kaisha + ":disabled").length > 0) {
                        target.prop("checked", true);
                        seizoKojoShiteiDialog.notifyAlert.remove("AP0022");
                        seizoKojoShiteiDialog.notifyAlert.message(App.messages.app.AP0022,"AP0022").show();
                    }
                    else {
                        seizoKojoShiteiDialog.element.find(".checkbox-kojyo-" + cd_kaisha).prop("checked", false);
                        seizoKojoShiteiDialog.element.find(".radio-select-" + cd_kaisha).hide();
                        seizoKojoShiteiDialog.element.find(".radio-select-" + cd_kaisha).prop("checked", false);
                        seizoKojoShiteiDialog.element.find(".td-kojyo-" + cd_kaisha).css("font-weight", "");
                    }
                }
            }
            else if (property == "checkbox_kojyo") {
                var cd_kaisha = row.findP("cd_kaisha").text();
                if (target.prop("checked")) {
                    row.findP("radio_select").show()

                    if (seizoKojoShiteiDialog.element.find(".checkbox-kojyo-" + cd_kaisha).length == seizoKojoShiteiDialog.element.find(".checkbox-kojyo-" + cd_kaisha + ":checked").length) {
                        seizoKojoShiteiDialog.element.find(".parent-" + cd_kaisha).findP("checkbox_kaisha").prop("checked", true);
                    }
                }
                else {
                    row.findP("radio_select").prop("checked", false).hide();
                    row.find(".td-kojyo").css("font-weight", "");
                    seizoKojoShiteiDialog.element.find(".parent-" + cd_kaisha).findP("checkbox_kaisha").prop("checked", false);
                }
            }
            else if (property == "radio_select") {
                $(".modal-body table .td-kojyo").css("font-weight", "");
                row.find(".td-kojyo").css("font-weight", "bold");
            }
        }

        /**
         * Processes when show dialog
         */
        seizoKojoShiteiDialog.shown = function () {
            
            var element = $("#seizoKojoShiteiDialog");
            

            if (seizoKojoShiteiDialog.options.parameter.no_seiho == null) {
                element.findP("no_seiho").text("新規製法登録中");
            }
            else {
                element.findP("no_seiho").text(seizoKojoShiteiDialog.options.parameter.no_seiho);
            }
            
            
            App.ui.loading.show();

            var strFilter = "";
            
            if (!App.ui.page.user.flg_kaishakan_sansyo) {
                strFilter = "&$filter=cd_kaisha eq " + App.ui.page.user.cd_kaisha
                if (!App.ui.page.user.flg_kojyokan_sansyo) {
                    strFilter = strFilter +  " and cd_kojyo eq " + App.ui.page.user.cd_busho + "M"
                }
            }
            else {
                if (!App.ui.page.user.flg_kojyokan_sansyo) {
                    strFilter = "&$filter=cd_kojyo eq " + App.ui.page.user.cd_busho + "M"
                }
            }


            var result_kojyo;
            return $.ajax(App.ajax.odata.get(seizoKojoShiteiDialog.urls.vw_kaisha_kojyo + strFilter)).then(function (result) {
                result_kojyo = result;
                return seizoKojoShiteiDialog.loadSeihoDenso()
            }).then(function () {
                seizoKojoShiteiDialog.bind(result_kojyo);
                seizoKojoShiteiDialog.setCheckBoxRadio();
                seizoKojoShiteiDialog.setStatusControl();
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            }).always(function () {
                App.ui.loading.close();
                seizoKojoShiteiDialog.options.parameter.flg_shoki = 0;
            })
        };

        /**
         * Load data in ma_seiho_denso table
         */
        seizoKojoShiteiDialog.setStatusControl = function () {
            var element = seizoKojoShiteiDialog.element;
            if (seizoKojoShiteiDialog.options.parameter.modeReadOnly) {
                element.find(".checkbox-kaisha").prop("disabled", true);
                element.find(".checkbox-kojyo").prop("disabled", true);
                element.find(".radio-select").prop("disabled", true);
                element.find(".select").prop("disabled", true);
            }
        }

        /**
         * 
         * Load data in ma_seiho_denso table
         */
        seizoKojoShiteiDialog.loadSeihoDenso = function () {
            var deferred = $.Deferred();
            if (seizoKojoShiteiDialog.options.parameter.no_seiho == null || !seizoKojoShiteiDialog.options.parameter.flg_shoki) {
                deferred.resolve();
            }
            else {
                $.ajax(App.ajax.odata.get(App.str.format(seizoKojoShiteiDialog.urls.ma_seiho_denso, seizoKojoShiteiDialog.options.parameter.no_seiho))).then(function (result) {
                  
                    for(var i = 0;i < result.value.length;i++){
                        var data = result.value[i],
                            item = {};
                        item.cd_kaisha = App.num.format(Number(data.cd_kaisha),"0000");
                        item.cd_kojyo = App.num.format(Number(data.cd_kojyo), "0000");
                        item.flg_daihyo_kojyo = data.flg_daihyo_kojyo ? 1 : 0;
                        item.flg_denso_jyotai = data.flg_denso_jyotai ? 1 : 0;
                        item.flg_database = true;
                        seizoKojoShiteiDialog.options.parameter.lstKojyo.push(item);
                    }
                    deferred.resolve();
                    
                }).fail(function (error) {
                    deferred.reject(error);
                })
            }
            return deferred.promise();
        }

        /**
         * 単項目要素をエラーのスタイルに設定します。
         * @param target 設定する要素
         */
        seizoKojoShiteiDialog.setColInvalidStyle = function (target) {
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
        seizoKojoShiteiDialog.setColValidStyle = function (target) {
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
        seizoKojoShiteiDialog.hidden = function (e) {

            var element = seizoKojoShiteiDialog.element,
                table = element.find(".search-list");

            //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
            element.find(":input").val("");
           // //TODO: 複数セレクトの場合は、下の１行をコメント解除します。
            element.find(":checked").prop("checked", false);
            table.find("tbody").not(".item-tmpl").remove();

            element.findP("data_count").text("");

            element.find(".data-count-kojyo").hide();

            seizoKojoShiteiDialog.notifyInfo.clear();
            seizoKojoShiteiDialog.notifyAlert.clear();
        };

        /**
         * 検索ダイアログ　バリデーションルールを定義します。
         */
        seizoKojoShiteiDialog.options.validations = {
        };

        /**
         * 検索ダイアログの一覧にデータをバインドします。
         */
        seizoKojoShiteiDialog.bind = function (data) {
            
            var element = seizoKojoShiteiDialog.element,
                table = element.find(".search-list"),
                count = data["odata.count"],
                items = data.value ? data.value : data,
                i, l, item, clone, cd_kaisha,cd_kaisha_last

            element.find(".data-count-kojyo").show();

            element.findP("data_count").text(data.value.length);

            seizoKojoShiteiDialog.data = App.ui.page.dataSet();
            seizoKojoShiteiDialog.data.attach(items);

            table.find("tbody:visible").remove();

            if (items[0]) {
                cd_kaisha = items[0].cd_kaisha;
                cd_kaisha_last = items[items.length - 1].cd_kaisha;
            }

            for (i = 0, l = items.length; i < l; i++) {
                item = items[i];
                var cd_kaisha_format = App.num.format(item.cd_kaisha, "0000"),
                    cd_kojyo_format = App.num.format(item.cd_kojyo, "0000");

                clone = table.find(".item-tmpl").clone();   

                clone.findP("cd_kaisha").text(cd_kaisha_format);
                clone.findP("nm_kaisha").text(item.nm_kaisha);
                clone.findP("cd_kojyo").text(cd_kojyo_format);
                clone.findP("nm_kojyo").text(item.nm_kojyo);
                clone.findP("qty_kihon").text(item.qty_kihon);
                clone.findP("su_code_standard").text(item.su_code_standard);
                clone.findP("radio_select").hide();
                clone.find(".td-kojyo").hide();

                clone.findP("checkbox_kojyo").addClass("checkbox-kojyo-" + cd_kaisha_format).addClass("checkbox-kojyo-" + cd_kaisha_format + "-" + cd_kojyo_format);
                clone.find(".td-kojyo").addClass("td-kojyo-" + cd_kaisha_format);
                clone.findP("radio_select").addClass("radio-select-" + cd_kaisha_format).addClass("radio-select-" + cd_kaisha_format + "-" + cd_kojyo_format);
                clone.findP("checkbox_kaisha").addClass("checkbox-kaisha-" + cd_kaisha_format).addClass("checkbox-kaisha");

                clone.appendTo(table).removeClass("item-tmpl").addClass("new");
                if (i == 0) {
                    clone.findP("icon_plus").addClass("icon-plus-sign");
                    clone.addClass("parent-" + cd_kaisha_format)
                    
                    clone.show();
                }
                else {
                    if (item.cd_kaisha == cd_kaisha) {
                        clone.addClass(cd_kaisha_format)
                        clone.find(".td-kaisha").hide();
                        clone.findP("checkbox_kaisha").hide();
                        clone.addClass("HideBorderTop");
                        clone.hide();
                    }
                    else {
                        cd_kaisha = item.cd_kaisha;
                        clone.findP("icon_plus").addClass("icon-plus-sign");
                        clone.addClass("parent-" + cd_kaisha_format)
                        clone.show();

                        if (cd_kaisha == cd_kaisha_last) {
                            clone.addClass("ShowBorderBottom");
                        }
                    }
                }

                if (i == items.length - 1) {
                    clone.addClass("ShowBorderBottom")
                }

                for (var j = 0; j < seizoKojoShiteiDialog.options.parameter.listSearch.length; j++) {
                    var dataSearch = seizoKojoShiteiDialog.options.parameter.listSearch[j];

                    if (dataSearch.cd_kaisha == item.cd_kaisha && dataSearch.cd_kojyo == item.cd_kojyo) {
                        clone.addClass("isSearch");
                    }
                }
            }
        };

        /**
         * Set checkbox radio when 
         */
        seizoKojoShiteiDialog.setCheckBoxRadio = function () {
            
            var element = seizoKojoShiteiDialog.element,
                table = element.find(".search-list"),
                cd_kaisha_first;

            for (var i = 0; i < seizoKojoShiteiDialog.options.parameter.lstKojyo.length; i++) {
                var cd_kaisha = App.num.format(seizoKojoShiteiDialog.options.parameter.lstKojyo[i].cd_kaisha, "0000"),
                    cd_kojyo = App.num.format(seizoKojoShiteiDialog.options.parameter.lstKojyo[i].cd_kojyo, "0000"),
                    checkbox_kojyo = table.find(".checkbox-kojyo-" + cd_kaisha + "-" + cd_kojyo),
                    row = checkbox_kojyo.closest("tbody"),
                    radio_select = row.findP("radio_select");

                if (row.length == 0) {
                    continue;
                }

                if (seizoKojoShiteiDialog.options.parameter.lstKojyo[i].flg_database) {
                    row.addClass("isDatabase");
                }

                checkbox_kojyo.prop("checked", true);

                radio_select.show();

                if (seizoKojoShiteiDialog.options.parameter.lstKojyo[i].flg_daihyo_kojyo) {
                    radio_select.prop("checked", true);
                    row.find(".td-kojyo").css("font-weight", "bold");
                    if (!seizoKojoShiteiDialog.options.parameter.flg_edit || seizoKojoShiteiDialog.options.parameter.no_gamen == App.settings.app.no_gamen.seiho_toroku) {
                        table.find(".new .radio-select").prop("disabled", true);
                        checkbox_kojyo.prop("disabled", true);
                    }
                }

                if (seizoKojoShiteiDialog.options.parameter.lstKojyo[i].flg_denso_jyotai) {
                    checkbox_kojyo.prop("disabled", true);
                    checkbox_kojyo.addClass("flg-denso-jyotai");
                    table.find(".new .radio-select").prop("disabled", true);
                }

                if (seizoKojoShiteiDialog.options.parameter.lstKojyo[i + 1]) {
                    var cd_kaisha_next = App.num.format(seizoKojoShiteiDialog.options.parameter.lstKojyo[i + 1].cd_kaisha, "0000");
                    if (cd_kaisha != cd_kaisha_next) {
                        var row_parent = table.find(".parent-" + cd_kaisha),
                            icon_plus_sign = row_parent.findP("icon_plus"),
                            classCheckBoxKojyo = "checkbox-kojyo-" + cd_kaisha;

                        seizoKojoShiteiDialog.showDataRow(icon_plus_sign);
                        if (table.find("." + classCheckBoxKojyo).length == table.find("." + classCheckBoxKojyo + ":checked").length) {
                            row_parent.findP("checkbox_kaisha").prop("checked", true);
                        }
                    }
                }
                else {
                    var row_parent = table.find(".parent-" + cd_kaisha),
                        icon_plus_sign = row_parent.findP("icon_plus"),
                        classCheckBoxKojyo = "checkbox-kojyo-" + cd_kaisha;

                    seizoKojoShiteiDialog.showDataRow(icon_plus_sign);
                    if (table.find("." + classCheckBoxKojyo).length == table.find("." + classCheckBoxKojyo + ":checked").length) {
                        row_parent.findP("checkbox_kaisha").prop("checked", true);
                    }
                }
            }

            for (var k = 0; k < seizoKojoShiteiDialog.options.parameter.lstDelete.length; k++) {
                var item = seizoKojoShiteiDialog.options.parameter.lstDelete[k];

                var cd_kaisha = App.num.format(item.cd_kaisha, "0000"),
                    cd_kojyo = App.num.format(item.cd_kojyo, "0000"),
                    checkbox_kojyo = table.find(".checkbox-kojyo-" + cd_kaisha + "-" + cd_kojyo),
                    row = checkbox_kojyo.closest("tbody");

                if (row.length > 0) {
                    row.addClass("isDatabase");
                }
            }
        }

        /**
         * Show data when click icon-plus-sign button
         */
        seizoKojoShiteiDialog.showDataRow = function (e) {
            var target;

            if (e.target) {
                var target = $(e.target);
            }
            else {
                target = e;
            }

            var tbody = target.closest("tbody"),
                flag = false;

            tbody.findP("icon_plus").removeClass("icon-plus-sign");
            tbody.findP("icon_plus").addClass("icon-minus-sign");
            tbody.find(".td-kojyo").show();

            if (tbody.findP("checkbox_kojyo").prop("checked")) {
                tbody.findP("radio_select").show();
            }

            var rows = seizoKojoShiteiDialog.element.find("." + tbody.findP("cd_kaisha").text());

            for (var i = 0 ; i < rows.length; i++) {
                var row = $(rows[i]);
                row.show();
                row.find(".td-kojyo").show();

                if (row.findP("checkbox_kojyo").prop("checked")) {
                    row.findP("radio_select").show();
                }

                if (row.hasClass("ShowBorderBottom")) {
                    if (!row.hasClass("parent-" + row.findP("cd_kaisha").text())) {
                        tbody.removeClass("ShowBorderBottom");
                    }
                }
            }
        }

        /**
         * Show data when click icon-minus-sign button
         */
        seizoKojoShiteiDialog.hideDataRow = function (e) {
            var target = $(e.target),
                tbody = target.closest("tbody");

            tbody.findP("icon_plus").removeClass("icon-minus-sign");
            tbody.findP("icon_plus").addClass("icon-plus-sign");
            tbody.find(".td-kojyo").hide();
            tbody.findP("radio_select").hide();

            var rows = seizoKojoShiteiDialog.element.find("." + tbody.findP("cd_kaisha").text());

            for (var i = 0 ; i < rows.length; i++) {
                var row = $(rows[i]);
                row.hide();
                row.find(".td-kojyo").hide();
                row.findP("radio_select").hide();

                if (row.hasClass("ShowBorderBottom")) {
                    tbody.addClass("ShowBorderBottom");
                }
            }
        }

        /**
         * Select kaisha, kojyo
         */
        seizoKojoShiteiDialog.select = function (e) {
            var element = seizoKojoShiteiDialog.element,
                table = element.find(".search-list"),
                kojyoChecked = table.find(".checkbox-kojyo:checked"),
                jsonKaishaKojyo = {};
            
            data = {
                lstKojyo: [],
                lstDelete: [],
                flg_daihyo_kojyo: 0,
                cd_kaisha: null,
                cd_kojyo:null,
                nm_kojyo: null,
                listSearch: seizoKojoShiteiDialog.options.parameter.listSearch
            }
            lstKojyo = [];

            for (var i = 0 ; i < kojyoChecked.length; i++) {
                var checked = $(kojyoChecked[i]),
                    row = checked.closest("tbody"),
                    cd_kaisha = row.findP("cd_kaisha").text(),
                    cd_kojyo = row.findP("cd_kojyo").text(),
                    qty_kihon = row.findP("qty_kihon").text(),
                    su_code_standard = row.findP("su_code_standard").text(),
                    flg_daihyo_kojyo = row.findP("radio_select").prop("checked") ? 1 : 0,
                    isDatabase = row.hasClass("isDatabase");
                    
                var item = {
                    no_seiho: seizoKojoShiteiDialog.options.parameter.no_seiho,
                    cd_kaisha: cd_kaisha,
                    cd_kojyo: cd_kojyo,
                    flg_daihyo_kojyo: flg_daihyo_kojyo,
                    flg_denso_jyotai: checked.hasClass("flg-denso-jyotai") ? 1 : 0,
                    nm_kaisha: row.findP("nm_kaisha").text(),
                    nm_kojyo: row.findP("nm_kojyo").text(),
                    flg_denso_taisho: false,
                    flg_database: isDatabase,
                    flgSearch: $(row).hasClass("isSearch")
                };
                data.lstKojyo.push(item);
                if (flg_daihyo_kojyo) {
                    var nm_kaisha = row.findP("nm_kaisha").text(),
                        nm_kojyo = row.findP("nm_kojyo").text();
                    data.cd_kaisha = parseInt(cd_kaisha,10);
                    data.cd_kojyo = parseInt(cd_kojyo,10);
                    data.flg_daihyo_kojyo = flg_daihyo_kojyo;
                    data.nm_kojyo = nm_kaisha + "　　" + nm_kojyo;
                    data.qty_kihon = qty_kihon;
                    data.su_code_standard = su_code_standard;
                }

                jsonKaishaKojyo[cd_kaisha + "_" + cd_kojyo] = true;
            }
            lstDelete = [];

            var isDatabaseElement = table.find(".isDatabase");

            for (var j = 0; j < isDatabaseElement.length; j++) {
                var row = $(isDatabaseElement)[j],
                    cd_kaisha = $(row).findP("cd_kaisha").text(),
                    cd_kojyo = $(row).findP("cd_kojyo").text();

                if ($(row).find(".checkbox-kojyo:checked").length == 0) {
                    var item = {
                        no_seiho: seizoKojoShiteiDialog.options.parameter.no_seiho,
                        cd_kaisha: cd_kaisha,
                        cd_kojyo: cd_kojyo,
                        flg_database: true,
                        flgSearch: $(row).hasClass("isSearch")
                    };
                    data.lstDelete.push(item);
                }
            }

            for (var i = 0; i < seizoKojoShiteiDialog.options.parameter.lstKojyo.length; i++) {
                var cd_kaisha = App.num.format(Number(seizoKojoShiteiDialog.options.parameter.lstKojyo[i].cd_kaisha), "0000"),
                    cd_kojyo = App.num.format(Number(seizoKojoShiteiDialog.options.parameter.lstKojyo[i].cd_kojyo), "0000"),
                    length = table.find(".checkbox-kojyo-" + cd_kaisha + "-" + cd_kojyo).length;

                if (App.isUndefOrNull(jsonKaishaKojyo[cd_kaisha + "_" + cd_kojyo]) && !length) {

                    var item = {
                        no_seiho: seizoKojoShiteiDialog.options.parameter.no_seiho,
                        cd_kaisha: seizoKojoShiteiDialog.options.parameter.lstKojyo[i].cd_kaisha,
                        cd_kojyo: seizoKojoShiteiDialog.options.parameter.lstKojyo[i].cd_kojyo,
                        flg_daihyo_kojyo: seizoKojoShiteiDialog.options.parameter.lstKojyo[i].flg_daihyo_kojyo ? 1 : 0,
                        flg_denso_jyotai: seizoKojoShiteiDialog.options.parameter.lstKojyo[i].flg_denso_jyotai ? 1 : 0,
                        nm_kaisha: "",
                        nm_kojyo: "",
                        flg_denso_taisho: false,
                        flg_database: true,
                        flgSearch: false
                    };

                    data.lstKojyo.push(item);
                }
            }


            if (App.isFunc(seizoKojoShiteiDialog.dataSelected)) {
                if (!seizoKojoShiteiDialog.dataSelected(data)) {
                    element.modal("hide");
                }
            } else {
                element.modal("hide");
            }
        }

        //TODO: 複数セレクトを使用する場合は、上の単一セレクト用select関数を削除し、下の複数セレクト用の関数をコメント解除します
        //TODO: 単一セレクトの場合は、不要なコメント部分は削除してください
    </script>

    <div class="modal fade wide" id="seizoKojoShiteiDialog" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog" style="height: 350px; width: 60%">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">製造工場指定</h4>
            </div>

            <div class="modal-body">
                
                <div class="row">
                    <div class="control col-xs-1">
                        <label><b>製法番号：</b></label>
                    </div>
                    <div class="control col-xs-3">
                        <label><b data-prop="no_seiho"></b></label>
                    </div>
                    <div class="control col-xs-4">
                        <label>この製法番号に対する設定になります。</label>
                    </div>
                    <div class="control col-xs-4">
                    </div>
                </div>

                <div class="row">

                    <div class="control col-xs-12" style="text-align:right;">
                        <span class="data-count-kojyo">該当件数は</span>
                        <span class="data-count-kojyo" data-prop="data_count"></span>
                        <span class="data-count-kojyo">件です</span>
                    </div>
                </div>

                <div style="padding-right: 16px;">
                    <table class="table table-striped table-condensed " style="margin-bottom: 0px;">
                    <!--TODO: ダイアログのヘッダーを定義するHTMLをここに記述します。列幅の合計が100％になるように定義します。-->
                        <thead>
                            <tr>
                                <%--単一セレクトの場合は、以下の１行を使用する--%>
                                
                                                              
                                <%--複数セレクトの場合は、上の１行をカットし、下の３行をコメント解除してください--%>
                                <th style="width: 35%;border-top:2px solid #dddddd; border-left:2px solid #dddddd; text-align:center;">製造会社</th>
                                <th style="width: 10%;text-align:center;border-top:2px solid #dddddd; border-left:2px solid #dddddd; ">全選択</th>
                                <th style="border-top:2px solid #dddddd; border-left:2px solid #dddddd; text-align:center;">製造工場</th>
                                <th style="width: 10%;text-align:center;border-top:2px solid #dddddd; border-left:2px solid #dddddd; border-right:2px solid #dddddd;text-align:center;">代表工場</th>  

                                <%--<th style="width: 5%;">
                                    <input type="checkbox" name="select_cd_all" />
                                </th>--%>
                            </tr>
                        </thead>
                    </table>
                </div>
                <div style="height: 252px; overflow: scroll; overflow-x: hidden;">
                    <table class="table table-striped table-condensed search-list">
                    <!--TODO: ダイアログの明細行を定義するHTMLをここに記述します。列幅の指定はヘッダーに合わせて定義します。-->
                        <tbody class="item-tmpl" style="display: none;">
                            <tr>
                                <td style="width: 35%;border-left:2px solid #dddddd;">
                                    <label  class="overflow-ellipsis">
                                        <span class="td-kaisha" data-prop="icon_plus" style="margin-top:2px;"></span>
                                        <span class="td-kaisha" data-prop="cd_kaisha"></span>
                                        <span class="td-kaisha" data-prop="nm_kaisha"></span>
                                    </label>
                                </td>
                                <td style="width: 10%;text-align:center;border-left:2px solid #dddddd;">
                                    <input type="checkbox" data-prop="checkbox_kaisha"/>
                                </td>
                                <td style="border-left:2px solid #dddddd;">
                                    <label>
                                        <input type="checkbox" class="td-kojyo checkbox-kojyo" data-prop="checkbox_kojyo"/>
                                        <span class="td-kojyo" data-prop="cd_kojyo"></span>
                                        <span class="td-kojyo" data-prop="nm_kojyo"></span>
                                    </label>
                                </td>
                                <td style="width: 10%;text-align:center;border-left:2px solid #dddddd;">
                                    <input type="radio" name="radio_select" data-prop="radio_select"  class="radio-select"/>
                                    <label style="display:none;" data-prop="qty_kihon"></label>
                                    <label style="display:none;" data-prop="su_code_standard"></label>
                                </td>
                            </tr>
                        </tbody>
                    </table>
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
                <button type="button" class="btn btn-success select" name="select" >設定</button>
                <button type="button" class="cancel-button btn btn-sm btn-default" data-dismiss="modal" name="close">閉じる</button>
            </div>

        </div>
    </div>
    </div>