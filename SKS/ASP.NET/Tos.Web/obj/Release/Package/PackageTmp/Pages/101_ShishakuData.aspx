<%@ Page Title="101_試作データ" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="101_ShishakuData.aspx.cs" Inherits="Tos.Web.Pages.ShishakuData" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【HeaderDetail(Ver2.1)】 Template--%>

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
    <title></title>
    <style type="text/css">
        div .detail-command {
            text-align: center;
        }

        .btn-next-search {
            width: 200px;
        }

        .header-checkbox[type="checkbox"]:disabled {
            cursor: default;
        }

        #ConfirmDialog {
            padding-top: 300px!important;
        }

        .icon-top {
            margin-top: 3px;
        }

        .icon-left {
            margin-left: 2px;
        }

        .styleLi {
            padding-top: 0px;
        }

            .styleLi > a {
                background-color: #f6f6f6;
                border: 1px solid #dddddd !important;
            }

        .row-header-2 label {
            min-width: 60px;
        }

        .light-green,
        tr.light-green th {
            background-color: #ccffcc!important;
        }

        .header .serect-color {
            background: #fbaeb3;
        }

        .header .serect-with {
            min-width: 35px!important;
        }

        .command-button {
            display: none;
        }

        /*.detail input:focus,
        .detail textarea:focus {
            outline: none;
        }*/

        /*.detail select:focus {
            outline: none;
        }*/

        .detail table.datatable input[type="text"], table.datatable input[type="tel"], table.datatable select, table.datatable textarea {
            float: left;
        }

        .header .required {
            color: red;
        }

        .header .required-label {
            min-width: 20px;
        }

        table.datatable .has-error {
            background-color: #ffdab9!important;
        }

            table.datatable .has-error .error {
                background-color: transparent;
            }

        textarea:focus,
        input[type="checkbox"]:focus {
            outline-offset: -2px!important;
            outline: -webkit-focus-ring-color auto 5px!important;
        }

        select option {
            background: white;
        }

        .tab-content .enableColor,
        .tab-pane .enableColor,
        .tab-content .enableColor select,
        .tab-pane .enableColor select,
        .tab-content .enableColor textarea:not(.error),
        .tab-pane .enableColor textarea:not(.error) {
            background-color: white!important;
        }

        .tab-content .datatable .new td,
        .tab-pane .datatable .new td,
        .tab-content .datatable .new td input:disabled,
        .tab-pane .datatable .new td input:disabled,
        .tab-content .datatable .new td input:read-only,
        .tab-pane .datatable .new td input:read-only,
        .tab-content .datatable .new td select:disabled,
        .tab-pane .datatable .new td select:disabled,
        .tab-content .datatable .new td textarea:read-only,
        .tab-pane .datatable .new td textarea:read-only {
            background-color: #efefef;
            color: rgb(0, 0, 0);
        }

        .tab-content .datatable .new .selected-row td,
        .tab-pane .datatable .new .selected-row td,
        .tab-content .datatable .new .selected-row td input:disabled,
        .tab-pane .datatable .new .selected-row td input:disabled,
        .tab-content .datatable .new .selected-row td input:read-only,
        .tab-pane .datatable .new .selected-row td input:read-only,
        .tab-content .datatable .new .selected-row td select:disabled,
        .tab-pane .datatable .new .selected-row td select:disabled,
        .tab-content .datatable .new .selected-row td textarea:read-only,
        .tab-pane .datatable .new .selected-row td textarea:read-only {
            background-color: transparent;
        }

        /*2019-09-12 : START : Q&A #15319 選択行の色*/
        .tab-content .tab-pane:not(.KihonJohyo_Tab) .datatable .new .selected-row input[type="text"],
        .tab-content .tab-pane:not(.KihonJohyo_Tab) .datatable .new .selected-row input[type="tel"],
        .tab-content .tab-pane:not(.KihonJohyo_Tab) .datatable .new .selected-row select,
        .tab-content .tab-pane:not(.KihonJohyo_Tab) .datatable .new .selected-row textarea {
            background-color: transparent;
        }

        /*2019-09-12 : END : Q&A #15319 選択行の色*/

        .detail input[type="radio"],
        .detail input[type="checkbox"] {
            margin: 1px 0 0;
        }

        .header input:disabled,
        .header input:read-only {
            background-color: rgb(235, 235, 228);
        }
        .navbar {
            display:none;
        }
        .closebtn {
            background-color:#008000;
            border-color:#008000;
        }
        .closebtn:focus{
            background-color:#008000;
            border-color:#008000;
        }

        .closebtn:hover{
            background-color:#008000;
            border-color:#008000;
        }
         .modal-mid {
            width:750px;
        }

        /*TOsVN - 19075 - Shohin Support 2023: st Bug #2254*/
        .tab-content .datatable th select:disabled {
            opacity: inherit !important;
        }
        /*ed Bug #2254*/

    </style>

    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("ShishakuData", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。sa
            options: {
                id_data_subete: 9 //2019-09-20 : Bug #15396 : 試作データの基本情報
            },
            values: {
                isChangeRunning: {},
                masterData: {},
                modeDisplay: {
                    Zencopy: 5,//全コピーモード
                    Featurecopy: 6//特徴コピー
                },
                isChange: false,
                positionLeft: 0,//選択した試作列のスクロールバーの位置情報を保持し、他のタブへ移動後、スクロールバーの位置を保持していた位置に移動する
                paraExcel105: 105,
                paraExcel106: 106
            },
            urls: {
                confirmDialog: "Dialogs/ConfirmDialog.aspx",
                haigohyo_tab: "../Pages/101_ShishakuData_HaigoHyo_Tab.aspx",
                tokuseichi_tab: "../Pages/101_ShishakuData_TokuseiChi_Tab.aspx",
                kihonjohyo_tab: "../Pages/101_ShishakuData_KihonJohyo_Tab.aspx",
                genkashisan_tab: "../Pages/101_ShishakuData_GenkaShisan_Tab.aspx",
                checkSeiho: "../Services/ShisaQuickService.svc/ma_hin_syurui?$filter=cd_hin_syurui eq '{0}'",
                checkEigyoCancel: "../Services/ShisaQuickService.svc/tr_shisan_status?$filter=cd_shain eq {cd_shain}M and nen eq {nen}M and no_oi eq {no_oi}M and st_eigyo ge {st_eigyo}",
                checkShisanHiCancel: "../Services/ShisaQuickService.svc/tr_shisan_shisaku?$filter=cd_shain eq {cd_shain}M and nen eq {nen}M and no_oi eq {no_oi}M and ({seq_shisaku})",
                checkHaita: "../Services/ShisaQuickService.svc/tr_shisan_shisakuhin?$filter=cd_shain eq {cd_shain}M and nen eq {nen}M and no_oi eq {no_oi}M and haita_id_user ne null"
            },
            header: {
                options: {},
                values: {},
                urls: {
                    shisakuhin: "../api/ShishakuData",
                    loadMasterData: "../api/ShishakuData/GetMasterData",
                    //checkIrai: "../api/ShishakuData/Post_CheckIrai",
                    genryoDelivery: "../api/ShishakuData/Post_GenryoDelivery",
                    Post_haita: "../api/ShishakuData/Post_haita"//2019-12-17 : Request #16147 : 使用中（他の人が開いている）の場合、初期表示時に「他の方が開いているため、参照モードで開きます」のメッセージを通知し、参照モードで開く。
                }
            },
            detail: {
                tabs: {
                    haigohyo_tab: {},
                    tokuseichi_tab: {},
                    kihonjohyo_tab: {},
                    genkashisan_tab: {}
                },
                options: {},
                values: {
                    //2019-12-12 START : Bug #16120 : 現行仕様に合わせて都度計算して表示するようお願い致します。
                    isCompleteGenka: false,
                    isCompleteTokuseichi: false,
                    //2019-12-12 END : Bug #16120 : 現行仕様に合わせて都度計算して表示するようお願い致します。
                    maxPrintSetsumeisho: 4,//印刷FGが5つ以上選択されていないこと
                    //maxPrintShutsuryoku: 10,//印刷FGが11つ以上選択されていないこと
                    //2019-07-26 : スペックを10列から20列に変更する。
                    maxPrintShutsuryoku: 20//印刷FGが21つ以上選択されていないこと
                },
                haigohyo_tab: {
                    options: {},
                    data: {},
                    values: {
                        isFirstClick: true
                    }
                },
                tokuseichi_tab: {
                    options: {},
                    data: {},
                    values: {
                        isFirstClick: true
                    }
                },
                kihonjohyo_tab: {
                    options: {},
                    data: {},
                    values: {
                        isFirstClick: true
                    }
                },
                genkashisan_tab: {
                    options: {},
                    data: {},
                    values: {
                        isFirstClick: true
                    }
                }
            },
            dialogs: {
                urls: {
                    confirmDialog: "Dialogs/ConfirmDialog.aspx",
                    IroShitei_Dialog: "Dialogs/602_IroShitei_Dialog.aspx",
                    ShisakuRetuTuikaDialog: "Dialogs/603_ShisakuRetuTuika_Dialog.aspx",
                    TantoEigyoKensaku_Dialog: "Dialogs/605_TantoEigyoKensaku_Dialog.aspx",
                    MemoNyuryoku_Dialog: "Dialogs/601_MemoNyuryoku_Dialog.aspx",
                    BunsekiGenryoKakunin_Dialog: "Dialogs/604_BunsekiGenryoKakuninDataKakunin.aspx",
                    SeizoKoteiDialog: "Dialogs/600_SeizoKoteiDialog.aspx",
                    // 19075 - 2022/06/06 - ShohinKaihatsuSuppot Modify 2022FY: -st
                    GenryoIchiranDialog: "Dialogs/102_GenryoIchiranDialog.aspx",
                    GenzairyoKakuninDialog: "Dialogs/606_GenzairyoKakunin_Dialog.aspx"
                    // -ed
                }
            },
            commands: {
                urls: {
                    save: "../api/ShishakuData",
                    genryoDelivery: "004_ShisakuBunsekiDataKakunin.aspx?id_session={id_session}&cd_kaisha={cd_kaisha}&cd_kojo={cd_kojo}",
                    shishakuDataGenkaShisanSonotaExcel108: "../api/_108_ShisakuData_GenkaShisan_Sonota_Excel_/GetExcel",
                    shishakuDataHyojiYokoExcel103: "../api/_103_ShishakuData_HyojiYoko_Excel_/GetExcel",
                    shishakuDataHaigoHyoExcel104: "../api/_104_ShishakuData_HaigoHyo_Excel_",
                    shishakuDataGenkaShisanChomiryoExcel107: "../api/_107_ShisakuData_GenkaShisan_Chomiryo_Excel_/GetExcel",
                    sanpuruSetsumeishoExcel105: "../api/_105_ShishakuData_SanpuruSetsumeisho_Excel_"
                }
            }
        });

        /**2020-08-06 :14007 : Bug #18857 内容量0.1でエラーとなる
        * 指定変更確認し。
        * @param target 条件確認する（100ミリ秒）
        */
        page.wait = function (target) {
            // Deferredのインスタンスを作成
            var deferred = $.Deferred()
            
            setTimeout(function () {
                if (target.attr("data-number-format-toFixed") == undefined) {
                    return deferred.resolve();
                }
                
                setTimeout(function () {
                    return deferred.resolve();
                },100)
            }, 1)

            return deferred.promise();
        }

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
            if (element.hasClass("break-validate")) {
                return;
            }
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
                    //page.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                    //    if (row && row.element) {
                    //        row.element.find("tr").removeClass("has-error");
                    //    }
                    //});
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
                    //page.detail.dataTable.dataTable("getRow", $(item.element), function (row) {
                    //    if (row && row.element) {
                    //        row.element.find("tr").addClass("has-error");
                    //    }
                    //});
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
        * get changeset from other tab
        */
        page.commands.beforSave = function () {

            //画面内容は変更がありません
            App.ui.page.notifyInfo.message(App.messages.app.AP0141).clear();
            if (!page.values.isChange && page.values.modeDisp != App.settings.app.m_shisaku_data.copy) {
                App.ui.page.notifyInfo.message(App.messages.app.AP0141).show()
                return;
            }

            //原価試作依頼されている列がある場合、担当会社変更できないこと
            var dataShisan = page.header.getData();
            App.ui.page.notifyInfo.message(App.messages.app.AP0156).clear();

            if (dataShisan.cd_kaisha != dataShisan.cd_kaisha_org) {

                if (GenkaShisan_Tab.detail.checkIraiChangKaisha() > 0) {

                    App.ui.page.notifyInfo.message(App.messages.app.AP0156).show();
                    App.ui.loading.close();
                    return;
                }
            }

            App.ui.loading.show();
            setTimeout(function () {

                if (page.values.modeDisp == App.settings.app.m_shisaku_data.copy) {
                    page.detail.KopiSeiho();
                } else {
                    page.commands.getChangeSetPage([]);
                }
            }, 10)
        };

        /**
         * all changeset
         */
        page.commands.getChangeSetPage = function (inforKopi) {
            var breakSave = false;
            //特性値タブの再計算する時にエラーがありますから。何手もしません。
            if (page.detail.tabs.tokuseichi_tab.values.messCalculator != null && page.detail.tabs.tokuseichi_tab.values.messCalculator != undefined) {
                App.ui.page.notifyAlert.remove(page.detail.tabs.tokuseichi_tab.values.messTarget);
                //計算結果は桁数がオーバーの場合、メッセージ表示。
                App.ui.page.notifyAlert.message(page.detail.tabs.tokuseichi_tab.values.messCalculator, page.detail.tabs.tokuseichi_tab.values.messTarget).show();

                App.ui.loading.close();
                breakSave = true;
            }

            //原価試算タブの再計算する時にエラーがありますから。何手もしません。
            if (page.detail.tabs.genkashisan_tab.values.messCalculator != null && page.detail.tabs.genkashisan_tab.values.messCalculator != undefined) {
                App.ui.page.notifyAlert.remove(page.detail.tabs.genkashisan_tab.values.messTarget);
                //計算結果は桁数がオーバーの場合、メッセージ表示。		
                App.ui.page.notifyAlert.message(page.detail.tabs.genkashisan_tab.values.messCalculator, page.detail.tabs.genkashisan_tab.values.messTarget).show();

                App.ui.loading.close();
                breakSave = true;
            }

            if (breakSave) {
                App.ui.loading.close();
                return;
            }

            App.ui.page.notifyWarn.clear();
            App.ui.page.notifyAlert.clear();
            App.ui.page.notifyInfo.clear();

            var savePage = function (genryoChangeSet) {
                var tokuseichiChangeSet = page.detail.tabs.tokuseichi_tab.getChangeSetTab();
                var haigoChangeSet = page.detail.tabs.haigohyo_tab.getChangeSetTab(tokuseichiChangeSet, genryoChangeSet);
                page.commands.save(haigoChangeSet, genryoChangeSet, inforKopi);
            }

            page.validateAll().then(function () {

                page.detail.tabs.genkashisan_tab.getChangeSetTab().then(function (genryoChangeSet) {
                    // 依頼キャンセルがある場合									
                    // 以下確認メッセージ表示								
                    //「下記サンプルNoの原価試算依頼を取り消します。よろしいですか？(AP0077)				
                    if (genryoChangeSet.cancel.length) {

                        //サンプルNoの営業ステータス（tr_shisan_status.st_eigyo）が２以上の場合
                        var nm_sample = genryoChangeSet.cancel.join("、");
                        page.detail.checkEigyoCancel(nm_sample).then(function () {

                            page.detail.checkShisanHiCancel(genryoChangeSet.iraichecked, nm_sample).then(function () {

                                var options = {
                                    text: App.messages.app.AP0077 + "　" + nm_sample,
                                    multiModal: true
                                };

                                App.ui.loading.close();
                                page.dialogs.confirmDialog.confirm(options).then(function () {

                                    App.ui.loading.show();
                                    savePage(genryoChangeSet);
                                })
                            }).fail(function () {
                                App.ui.loading.close();
                            })

                        })
                    } else {
                        savePage(genryoChangeSet);
                    }

                }).fail(function () {
                    App.ui.loading.close();
                })
            }).fail(function () {
                App.ui.loading.close();
            })
        };

        /**
        * 原価試算．依頼キャンセルがチェックあるサンプルNoに対して		
	    * ・サンプルNoの試算日が入力されている
        */
        page.detail.checkShisanHiCancel = function (cancel, nm_sample) {
            var deferred = $.Deferred(),
                filters = [];
            var filCancel = jQuery.grep(cancel, function (n, i) {

                if (n.flg_cancel == true || n.flg_cancel == "true") {
                    filters.push("(seq_shisaku eq " + n.seq_shisaku + "M and dt_shisan ne null)");
                }
            });

            if (!filters.length) {
                deferred.resolve();
                return deferred.promise();
            }

            filters = filters.join(" or ");

            $.ajax(App.ajax.odata.get(App.str.format(page.urls.checkShisanHiCancel, { cd_shain: page.values.cd_shain, nen: page.values.nen, no_oi: page.values.no_oi, seq_shisaku: filters }))).then(function (result) {
                if (!result.value.length) {

                    //他ユーザが原価試算中（tr_shisan_shisakuhin.haita_id_user <> NULL）場合エラーメッセージ表示	
                    $.ajax(App.ajax.odata.get(App.str.format(page.urls.checkHaita, { cd_shain: page.values.cd_shain, nen: page.values.nen, no_oi: page.values.no_oi }))).then(function (result) {
                        if (!result.value.length) {

                            App.ui.loading.close();
                            deferred.resolve();
                        } else {
                            App.ui.page.notifyAlert.message(App.messages.app.AP0158).show();

                            App.ui.loading.close();
                            deferred.reject();
                        }
                    }).fail(function (error) {
                        App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

                        App.ui.loading.close();
                        deferred.reject();
                    })

                } else {
                    App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0135, { nm_sample: nm_sample })).show();

                    App.ui.loading.close();
                    deferred.reject();
                }
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

                App.ui.loading.close();
                deferred.reject();
            })

            return deferred.promise();
        };

        /**
        * 原価試算．依頼キャンセルがチェックあるサンプルNoに対して		
	    * サンプルNoの営業ステータス（tr_shisan_status.st_eigyo）が２以上の場合
        */
        page.detail.checkEigyoCancel = function (nm_sample) {

            var deferred = $.Deferred();

            $.ajax(App.ajax.odata.get(App.str.format(page.urls.checkEigyoCancel, {
                cd_shain: page.values.cd_shain,
                nen: page.values.nen,
                no_oi: page.values.no_oi,
                st_eigyo: App.settings.app.genka_shisan_status.irai
            }))).then(function (result) {
                if (!result.value.length) {
                    deferred.resolve();
                } else {
                    $.each(result.value, function (index, item) {
                        App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0134, {
                            cd_shain: item["cd_shain"],
                            nen: item["nen"],
                            no_oi: item["no_oi"],
                            no_eda: item["no_eda"]
                        })).show();
                    })

                    App.ui.loading.close();
                    deferred.reject();
                }
            }).fail(function (error) {
                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

                App.ui.loading.close();
                deferred.reject();
            })

            return deferred.promise();
        };

        /**
         * 製法にコピー前にチェックします
         */
        page.detail.KopiSeiho = function () {
            var element = page.header.element;

            //種別番号が選択されていること
            element.validation().validate({
                targets: element.findP("no_shubetu")
            }).then(function () {

                page.detail.tabs.haigohyo_tab.shisakuKopiChecked().then(function (seq_shisaku_checked) {
                    //種別コードが製法支援システムに登録されていること
                    var shubetsuLabel = page.detail.tabs.kihonjohyo_tab.detail.shubetsuLabel(),//種別
                        inforKopi = {
                            seq_shisaku: seq_shisaku_checked.seq_shisaku,//試作SEQ
                            cd_kaisha: seq_shisaku_checked.cd_kaisha,
                            total_juryo_shiagari_seq: seq_shisaku_checked.total_juryo_shiagari_seq
                        };

                    $.ajax(App.ajax.odata.get(App.str.format(page.urls.checkSeiho, shubetsuLabel))).then(function (result) {
                        if (!result.value.length) {
                            App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0031, { name: "種別コード" })).show();
                            App.ui.loading.close();
                            return;
                        }
                        //種別 + 種別番号
                        inforKopi.shubetu = shubetsuLabel + $.trim(page.header.element.findP("no_shubetu").find("option:selected").text());

                        page.commands.getChangeSetPage(inforKopi);
                    }).fail(function (error) {
                        App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                        App.ui.loading.close();
                    })
                }).fail(function () {
                    App.ui.loading.close();
                })
            }).fail(function () {
                App.ui.loading.close();
            })
        };

        /**
        * 試作品テーブルの排他区分を更新してから。試作品テーブルのChangeSetを貰います
        */
        page.header.getChangeSetData = function () {
            var element = page.header.element,
                id = element.attr("data-key"),
                entity = page.header.data.entry(id);

            //Bug #15393 : START : 排他区分
            //排他区分
            //entity["kbn_haita"] = App.ui.page.user.EmployeeCD;
            //Bug #15393 : END : 排他区分

            //販責会社
            //2020-04-01 : START : Bug #16894 : 試作データ：販責会社がNULLになる
            //販責会社がNULLのデータをコピーしたときに販責会社を変更しなかったら画面表示の通りキユーピーで登録するようにしてください。
            entity.cd_hanseki = $("#KihonJohyo_Tab").find(".new").findP("cd_hanseki").val();
            debugger
            //2020-04-01 : END : Bug #16894 : 試作データ：販責会社がNULLになる

            page.header.data.update(entity);

            return page.header.data.getChangeSet();
        }

        /**
        * データの保存処理を実行します。
        */
        page.commands.save = function (haigoChangeSet, genryoChangeSet, inforKopi) {

            var options = {
                text: App.messages.app.AP0004,
                multiModal: true
            };

            App.ui.loading.close();
            page.dialogs.confirmDialog.confirm(options).then(function () {

                return page.detail.tabs.kihonjohyo_tab.detail.createNisugata();
            }).then(function (cd_nisugata) {

                App.ui.loading.show();
                setTimeout(function () {
                    var changeSets = {
                        mode_update: page.values.modeDisp
                        , cd_kaisha: App.ui.page.user.cd_kaisha//会社
                        , cd_busho: App.ui.page.user.cd_busho//工場
                        , cd_shain: page.values.cd_shain
                        , nen: page.values.nen
                        , no_oi: page.values.no_oi
                        , tr_shisakuhin: page.header.getChangeSetData()
                        , tr_haigo: haigoChangeSet.tr_haigo
                        , tr_shisaku: haigoChangeSet.tr_shisaku
                        , tr_shisaku_list: haigoChangeSet.tr_shisaku_list
                        , tr_cyuui: haigoChangeSet.tr_cyuui
                        , tr_genryo: genryoChangeSet.tr_genryo
                        , iraiSelected: genryoChangeSet.iraichecked
                        , kopi: inforKopi
                        , cd_nisugata: cd_nisugata
                        , settingDefault: {
                            genka_shisan_status_nashi: App.settings.app.genka_shisan_status.nashi
                            , genka_shisan_status_irai: App.settings.app.genka_shisan_status.irai
                            , kbn_hin_1: App.settings.app.kbn_hin.kbn_hin_1
                            , kbn_hin_3: App.settings.app.kbn_hin.kbn_hin_3
                            , kbn_hin_9: App.settings.app.kbn_hin.kbn_hin_9
                            , flg_gasan: App.settings.app.flg_gasan
                            , kbn_vw: App.settings.app.kbn_vw
                            , hijyu: App.settings.app.hijyu
                            , gokeishiagarinashi: App.settings.app.kbn_shiagari.gokeishiagarinashi
                            , gokeishiagariari: App.settings.app.kbn_shiagari.gokeishiagariari
                            , henshuchu: App.settings.app.kbn_status.henshuchu
                            , flg_shitei_nashi: App.settings.app.flg_shitei
                            , mark_18: App.settings.app.cd_mark.mark_18
                            , mark_0: App.settings.app.cd_mark.mark_0
                            , shikakari_kaihatsu: App.settings.app.kbn_shikakari.kaihatsu
                            , kbn_bunkatsu_0: App.settings.app.kbn_bunkatsu_0
                            , manbudomari: App.settings.app.manbudomari
                            , flg_denso_jyotai: App.settings.app.flg_denso_jyotai
                            , flg_daihyo_kojyo: App.settings.app.flg_daihyo_kojyo
                            , daihyogaisha: App.settings.app.cd_category.daihyogaisha
                            , fg_keisan: App.settings.app.fg_keisan
                            , shiyo_nashi: App.settings.app.flg_shiyo.shiyo_nashi
                            , shiyo_ari: App.settings.app.flg_shiyo.shiyo_ari
                            , eigyo_status_saiyo: App.settings.app.genka_shisan_status.saiyo
                            , cd_command: App.settings.app.commentCode
                        }
                    };

                    //TODO: データの保存処理をここに記述します。
                    return $.ajax(App.ajax.webapi.post(page.commands.urls.save, changeSets)).then(function (result) {
                        //フラグ変更画面
                        page.values.isChange = false;

                        //アクティブタブ
                        var activeTab = $(".tabbable .nav-tabs .active").attr("data-for");
                        //if (page.values.modeDisp == App.settings.app.m_shisaku_data.shinki
                        //    || page.values.modeDisp == page.values.modeDisplay.Zencopy
                        //    || page.values.modeDisp == page.values.modeDisplay.Featurecopy) {
                        //    activeTab = "HaigoHyo_Tab";
                        //}

                        var href = App.str.format("101_ShishakuData.aspx?modeDisp={mode}&cd_shain={cd_shain}&nen={nen}&no_oi={no_oi}", {
                            mode: page.values.modeDisp == App.settings.app.m_shisaku_data.copy ? App.settings.app.m_shisaku_data.copy : App.settings.app.m_shisaku_data.shosai
                            , cd_shain: result.cd_shain
                            , nen: result.nen
                            , no_oi: result.no_oi
                        })
                        window.location.href = href;

                        //メッセージIDをつくする。
                        var storeIDmess = App.str.format("101_ShishakuData_mess_{user}_{cd_shain}_{nen}_{no_oi}", {
                            user: App.ui.page.user.EmployeeCD
                            , cd_shain: result.cd_shain
                            , nen: result.nen
                            , no_oi: result.no_oi
                        }),
                         //左の位置IDをつくする。
                        storeIDpositionLeft = App.str.format("101_ShishakuData_positionLeft_{user}_{cd_shain}_{nen}_{no_oi}", {
                            user: App.ui.page.user.EmployeeCD
                            , cd_shain: result.cd_shain
                            , nen: result.nen
                            , no_oi: result.no_oi
                        }),
                        //アクティブタブIDをつくする。
                        storeIDactiveTab = App.str.format("101_ShishakuData_activeTab_{user}_{cd_shain}_{nen}_{no_oi}", {
                            user: App.ui.page.user.EmployeeCD
                            , cd_shain: result.cd_shain
                            , nen: result.nen
                            , no_oi: result.no_oi
                        });

                        localStorage.setItem(storeIDmess, JSON.stringify(App.messages.base.MS0002));
                        localStorage.setItem(storeIDpositionLeft, JSON.stringify(page.values.positionLeft));
                        localStorage.setItem(storeIDactiveTab, JSON.stringify(activeTab));

                    }).fail(function (error) {

                        localStorage.clear();

                        App.ui.loading.close();

                        if (error.status === 400 && error.responseJSON == "ma_shisaku_saiban") {
                            App.ui.page.notifyAlert.clear();
                            App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0042, { strName: "試作コードの自動採番" })).show();
                            return;
                        }

                        //関連会社（代表会社（[ma_literal].[cd_category]= "K_daihyogaisha"）ではない）での試算依頼チェックを行う		
                        //関連会社が指定されている場合、エラーメッセージ表示		
                        //「関連会社での試算依頼はできません。(AP0078)」	
                        if (error.status === 400 && error.responseJSON == "K_daihyogaisha") {
                            App.ui.page.notifyAlert.clear();
                            App.ui.page.notifyAlert.message(App.messages.app.AP0078).show();
                            return;
                        }

                        //対象試作の営業ステータス（tr_shisan_status.st_eigyo）が全て4（採用）の場合、エラーメッセージ表示
                        if (error.status === 400 && error.responseJSON == "st_eigyo_4") {
                            App.ui.page.notifyAlert.clear();
                            App.ui.page.notifyAlert.message(App.messages.app.AP0079).show();
                            return;
                        }

                        //Bug #15389 : START : 製法コピー時
                        //付番される配合コードが200000～299999でないと保存できないようになっている
                        if (error.status === 400 && error.responseJSON == "Haigo_range_check") {
                            App.ui.page.notifyAlert.clear();
                            App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0162, "製法支援コピー")).show();
                            return;
                        }
                        //Bug #15389 : END : 製法コピー時

                        if (error.status === App.settings.base.conflictStatus) {
                            // TODO: 同時実行エラー時の処理を行っています。
                            // 既定では、メッセージを表示し、現在の入力情報を切り捨ててサーバーの最新情報を取得しています。
                            //page.loadData();
                            App.ui.page.notifyAlert.clear();
                            App.ui.page.notifyAlert.message(App.messages.base.MS0017).show();
                            return;
                        }

                        if (error.status === App.settings.base.validationErrorStatus) {
                            var errors = error.responseJSON;
                            $.each(errors, function (index, err) {
                                var InvalidationName = "";
                                switch (err.InvalidationName) {

                                    case "tr_shisakuhin":
                                        InvalidationName = err.Data["cd_shain"] + "、" + err.Data["nen"] + "、" + err.Data["no_oi"];
                                        break
                                    case "tr_haigo":
                                        InvalidationName = err.Data["cd_shain"] + "、" + err.Data["nen"] + "、" + err.Data["no_oi"] + "、" + err.Data["cd_kotei"] + "、" + err.Data["seq_kotei"];
                                        break;
                                    case "tr_shisaku":
                                        InvalidationName = err.Data["cd_shain"] + "、" + err.Data["nen"] + "、" + err.Data["no_oi"] + "、" + err.Data["seq_shisaku"];
                                        break;
                                    case "tr_shisaku_list":
                                        InvalidationName = err.Data["cd_shain"] + "、" + err.Data["nen"] + "、" + err.Data["no_oi"] + "、" + err.Data["seq_shisaku"] + "、" + err.Data["cd_kotei"] + "、" + err.Data["seq_kotei"];
                                        break;
                                    case "tr_genryo":
                                        InvalidationName = err.Data["cd_shain"] + "、" + err.Data["nen"] + "、" + err.Data["no_oi"] + "、" + err.Data["seq_shisaku"];
                                        break;
                                    case "tr_cyuui":
                                        InvalidationName = err.Data["cd_shain"] + "、" + err.Data["nen"] + "、" + err.Data["no_oi"] + "、" + err.Data["no_chui"];
                                        break;
                                    default:
                                        InvalidationName = (App.isUndefOrNull(err.InvalidationName) ? "" : err.Data[err.InvalidationName]);
                                        break;
                                }

                                App.ui.page.notifyAlert.message(err.Message + InvalidationName).show();
                            });
                            return;
                        }

                        //TODO: データの保存失敗時の処理をここに記述します。
                        App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                        //App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0042, { strName: "登録" })).show();

                    });
                })
            })
        };

        /**
        * サンプル説明書
        */
        page.commands.shisakuSetsumeisho = function () {
            //・画面で変更内容がある場合、「実行する前、保存してください！(AP0018)」メッセージ表示、処理中断
            if (page.values.isChange) {
                App.ui.page.notifyInfo.clear();
                App.ui.page.notifyInfo.message(App.messages.app.AP0018).show();
                return;
            }

            // 印刷FGが選択されていること
            // 印刷FGが5つ以上選択されていないこと
            page.detail.tabs.haigohyo_tab.detail.shisakuPrintcheck().then(function (keySample) {
                // 印刷FGが選択されていること
                App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0028, { strName: "印刷FG" })).remove();
                if (!keySample.length) {
                    App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0028, { strName: "印刷FG" })).show();
                    return;
                }

                // 印刷FGが5つ以上選択されていないこと
                App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0036, { strName: "サンプル説明書", colCount: page.detail.values.maxPrintSetsumeisho })).remove();
                if (keySample.length > page.detail.values.maxPrintSetsumeisho) {
                    App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0036, { strName: "サンプル説明書", colCount: page.detail.values.maxPrintSetsumeisho })).show();
                    return;
                }

                var filter = {
                    cd_shain: page.values.cd_shain,
                    nen: page.values.nen,
                    no_oi: page.values.no_oi,
                    sample: keySample,
                    mode: page.values.paraExcel105
                };

                // ローディング表示
                App.ui.loading.show();
                var ExcelFile1, ExcelFile2, xhr1, xhr2, status1, status2;

                return $.ajax(App.ajax.file.download(page.commands.urls.sanpuruSetsumeishoExcel105, filter)).then(function (response, status, xhr) {

                    ExcelFile1 = response;
                    xhr1 = xhr;
                    status1 = status;
                    filter.mode = page.values.paraExcel106;

                    $.ajax(App.ajax.file.download(page.commands.urls.sanpuruSetsumeishoExcel105, filter)).then(function (response, status, xhr) {

                        ExcelFile2 = response;
                        xhr2 = xhr;
                        status2 = status;

                        if (status1 == "success" && status2 == "success") {
                            App.file.save(ExcelFile1, App.ajax.file.extractFileNameDownload(xhr1) || "ExcelFile.xlsm");
                            App.file.save(ExcelFile2, App.ajax.file.extractFileNameDownload(xhr2) || "ExcelFile.xlsm");
                        }
                    }).fail(function (error) {

                        App.ui.page.notifyAlert.clear();

                        if (error.status === 400 && error.responseJSON == "AP0007") {
                            App.ui.page.notifyAlert.message(App.messages.app.AP0007).show();
                        } else {
                            App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                        }
                    }).always(function () {

                        App.ui.loading.close();
                    });

                }).fail(function (error) {

                    App.ui.page.notifyAlert.clear();

                    if (error.status === 400 && error.responseJSON == "AP0007") {

                        App.ui.page.notifyAlert.message(App.messages.app.AP0007).show();
                    } else {

                        App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                    }
                    App.ui.loading.close();
                })
            });
        }

        /**
        * 論理チェック
        */
        page.commands.genkaInsatsu = function () {
            var element = $("#GenkaInsatsu");

            App.ui.page.notifyAlert.remove(element);

            //・画面で変更内容がある場合、「実行する前、保存してください！(AP0018)」メッセージ表示、処理中断
            if (page.values.isChange) {
                App.ui.page.notifyInfo.clear();
                App.ui.page.notifyInfo.message(App.messages.app.AP0018).show();
                return;
            }

            var sample = [];
            //配合表．工程属性コンボボックスで空白ではないこと
            page.detail.tabs.haigohyo_tab.detail.checkBlankKotei(true).then(function () {

                //印刷FGが未選択されていないこと
                return page.detail.tabs.genkashisan_tab.detail.checkFlgPrint(element);
            }).then(function (results) {

                sample = results.sample;
                //配合表①．合計仕上重量
                return page.detail.tabs.haigohyo_tab.detail.checkGokeJuryo(results.keySample, element);
            }).then(function (results) {

                var filter = {
                    cd_shain: page.values.cd_shain,
                    nen: page.values.nen,
                    no_oi: page.values.no_oi,
                    sample: sample
                };

                var pt_kotei = page.detail.element.findP("pt_kotei").val();
                var urlExcel = "";

                if (pt_kotei == App.settings.app.pt_kotei.chomieki_1 || pt_kotei == App.settings.app.pt_kotei.chomieki_2) {
                    urlExcel = page.commands.urls.shishakuDataGenkaShisanChomiryoExcel107
                }
                else if (pt_kotei == App.settings.app.pt_kotei.sonohokaeki) {
                    urlExcel = page.commands.urls.shishakuDataGenkaShisanSonotaExcel108;
                }

                // ローディング表示
                App.ui.loading.show();

                return $.ajax(App.ajax.file.download(urlExcel, filter)).then(function (response, status, xhr) {

                    App.ui.page.notifyAlert.clear();

                    if (status !== "success") {
                        App.ui.page.notifyAlert.message(App.messages.base.MS0016).show();
                    } else {
                        App.file.save(response, App.ajax.file.extractFileNameDownload(xhr) || "ExcelFile.xlsm");
                    }

                }).fail(function (error) {

                    App.ui.page.notifyAlert.clear();

                    if (error.status === 400 && error.responseJSON == "AP0007") {
                        App.ui.page.notifyAlert.message(App.messages.app.AP0007).show();
                    } else {
                        App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                    }

                }).always(function () {
                    App.ui.loading.close();
                });
            })
        }

        /**
        * 試作表出力ボタン押下
        */
        page.commands.shisakuExport = function () {

            //・画面で変更内容がある場合、「実行する前、保存してください！(AP0018)」メッセージ表示、処理中断
            if (page.values.isChange) {
                App.ui.page.notifyInfo.clear();
                App.ui.page.notifyInfo.message(App.messages.app.AP0018).show();
                return;
            }

            //・画面で変更内容がある場合、「実行する前、保存してください！(AP0018)」メッセージ表示、処理中断
            if (page.values.isChange) {
                App.ui.page.notifyInfo.clear();
                App.ui.page.notifyInfo.message(App.messages.app.AP0018).show();
                return;
            }

            //印刷FGが選択されていること
            //印刷FGが11つ以上選択されていないこと
            page.detail.tabs.haigohyo_tab.detail.shisakuPrintcheck().then(function (keySample) {
                // 印刷FGが選択されていること
                App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0028, { strName: "印刷FG" })).remove();
                if (!keySample.length) {
                    App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0028, { strName: "印刷FG" })).show();
                    return;
                }

                // 印刷FGが5つ以上選択されていないこと
                App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0036, { strName: "試作表出力", colCount: page.detail.values.maxPrintShutsuryoku })).remove();
                if (keySample.length > page.detail.values.maxPrintShutsuryoku) {
                    App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0036, { strName: "試作表出力", colCount: page.detail.values.maxPrintShutsuryoku })).show();
                    return;
                }

                var filter = {
                    cd_shain: page.values.cd_shain,
                    nen: page.values.nen,
                    no_oi: page.values.no_oi,
                    sample: keySample,
                    id_user: App.ui.page.user.EmployeeCD,
                    nm_user: App.ui.page.user.Name,
                    cd_category_haigo_hyodo: App.settings.app.cd_category.kbn_haigo_kyodo
                };

                App.ui.loading.show();
                var ExcelFile1, ExcelFile2, xhr1, xhr2, status1, status2;

                return $.ajax(App.ajax.file.download(page.commands.urls.shishakuDataHyojiYokoExcel103, filter)).then(function (response, status, xhr) {

                    ExcelFile1 = response;
                    xhr1 = xhr;
                    status1 = status;

                    $.ajax(App.ajax.file.download(page.commands.urls.shishakuDataHaigoHyoExcel104, filter)).then(function (response, status, xhr) {

                        ExcelFile2 = response;
                        xhr2 = xhr;
                        status2 = status;

                        if (status1 == "success" && status2 == "success") {
                            App.file.save(ExcelFile1, App.ajax.file.extractFileNameDownload(xhr1) || "ExcelFile.xlsm");
                            App.file.save(ExcelFile2, App.ajax.file.extractFileNameDownload(xhr2) || "ExcelFile.xlsm");
                        } else {
                            App.ui.page.notifyAlert.message(App.messages.base.MS0016).show();
                        }

                    }).fail(function (error) {

                        App.ui.page.notifyAlert.clear();

                        if (error.status === 400 && error.responseJSON == "AP0007") {
                            App.ui.page.notifyAlert.message(App.messages.app.AP0007).show();
                        } else {
                            App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                        }

                    }).always(function () {
                        App.ui.loading.close();
                    });

                }).fail(function (error) {

                    App.ui.page.notifyAlert.clear();

                    if (error.status === 400 && error.responseJSON == "AP0007") {
                        App.ui.page.notifyAlert.message(App.messages.app.AP0007).show();
                    } else {
                        App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                    }

                    App.ui.loading.close();
                })

            })
        }

        /**
        * 分析マスタの最新情報に更新。
        */
        page.commands.genryoJohoKoshin = function () {

            page.dialogs.confirmDialog.confirm({
                text: App.messages.app.AP0039
            }).then(function () {
                //原料マスタから特性値を取得し、画面に反映する
                //page.detail.tabs.haigohyo_tab.detail.kaishaKojoKoshin(true);
                page.detail.tabs.haigohyo_tab.detail.saikinJoho();
            })
        };

        /**
        * 特徴コピーと全コピーは閲覧の時に全てインプットを入力できる。
        */
        page.commands.removeClassCopy = function () {
            if (page.values.modeDisp != App.settings.app.m_shisaku_data.etsuran) {
                return;
            }

            var element = $("#command_left, #command_right, .content-wrap");
            element.find(".inputCopy").prop("readonly", false);
            element.find(".checkboxCopy").prop('disabled', false);
            element.find(".selectCopy").prop('disabled', false);
            element.find(".buttonCopy").prop('disabled', false);
            element.find(".textareaCopy").prop('readonly', false);
            element.find(".enableColorCopy").addClass("enableColor");
        }

        /**
        * 特徴コピー
        */
        page.commands.Tokuchokopi = function () {

            page.dialogs.confirmDialog.confirm({
                text: App.messages.app.AP0145,
                ok: "はい",
                cancel: "いいえ",
                multiModal: true
            }).then(function () {

                App.ui.loading.show();

                setTimeout(function () {
                    //2020-02-04 START : Bug #16491 : 全コピー・特徴コピーについて
                    //閲覧モードで開いたときに全コピー・閲覧モードボタン・印刷FGを活性にする。
                    page.commands.removeClassCopy();
                    //2020-02-04 END : Bug #16491 : 全コピー・特徴コピーについて

                    var element = page.header.element,
                        id = element.attr("data-key"),
                        dataCopi = $.extend(true, {}, page.header.data.entry(id));

                    //refresh header
                    element.find(".tokucho-copy-input-blank").val("");
                    element.find(".tokucho-copy-label-blank").text("");

                    dataCopi.cd_shain = null;//試作コード
                    dataCopi.nen = null;
                    dataCopi.no_oi = null;
                    //dataCopi.no_irai = null;//依頼番号IR@                
                    dataCopi.nm_toroku = App.ui.page.user.Name;//登録者              
                    dataCopi.nm_koshin = App.ui.page.user.Name;//更新者               
                    dataCopi.cd_group = App.ui.page.user.cd_group;//グループCD             
                    dataCopi.nm_group = App.ui.page.user.nm_group;//グループ名            
                    dataCopi.cd_team = App.ui.page.user.cd_team;//チームCD         
                    dataCopi.nm_team = App.ui.page.user.nm_team;//チーム名
                    dataCopi.dt_toroku = new Date(),
                    dataCopi.dt_koshin = null;//更新日
                    dataCopi.kbn_haishi = false;//廃止                
                    dataCopi.flg_secret = false;//シークレット               
                    dataCopi.dt_shisan = null;//試算日        
                    //dataCopi.nm_hin = null;//試算確定
                    dataCopi.nm_sample = null;//試算確定
                    dataCopi.seiho_no_shain = null;//製法No
                    dataCopi.seiho_no_nen = null;//製法No
                    dataCopi.seiho_no_oi = null;//製法No
                    dataCopi.seiho_no = null;//製法No
                    dataCopi.seq_shisaku = 0;
                    dataCopi.pt_kotei = null;//工程パターン  
                    dataCopi.cd_tani = null;
                    dataCopi.flg_chui = 0;
                    dataCopi.kbn_haita = App.ui.page.user.EmployeeCD;

                    //工程パターン 
                    page.detail.element.findP("pt_kotei").val(dataCopi.pt_kotei);
                    page.values.dataPatanTokuseichi = dataCopi.pt_kotei;
                    page.detail.tabs.tokuseichi_tab.changeKoteiPatan(dataCopi.pt_kotei, null, "refresh");

                    //特徴コピーブタン、全コピーブタン
                    page.commands.element.find(".command-haigohyo-tab-edit").prop("disabled", true);

                    //特徴コピーモード
                    page.values.modeDisp = page.values.modeDisplay.Featurecopy;

                    //reload haigo tab
                    page.detail.tabs.haigohyo_tab.reLoadTab();

                    //reload tokuseichi tab
                    page.detail.tabs.tokuseichi_tab.reLoadTab();

                    //reload kihonjohyo_tab tab
                    page.detail.tabs.kihonjohyo_tab.reLoadTab();

                    //reload tokuseichi tab
                    page.detail.tabs.genkashisan_tab.reLoadTab($.extend(true, {}, dataCopi));

                    //renew header
                    page.header.bind(dataCopi, true);

                    //シークレット
                    element.findP("flg_secret").text("OFF").removeClass("serect-color");

                    setTimeout(function () {
                        //renew tab
                        page.detail.tabs.haigohyo_tab.detail.initInput();

                        page.values.positionLeft = 0;

                        //フラグ変更画面
                        page.values.isChange = true;

                        App.ui.loading.close();
                    }, 1);
                }, 1);
            })
        };

        /**
        * 全コピー
        */
        page.commands.zenCopy = function () {
            //配合表タブを全コピーする。
            page.detail.tabs.haigohyo_tab.detail.beforZenCopy();
        };

        /**
        * 全コピー
        */
        page.detail.zenkopiPage = function () {

            //2020-02-04 START : Bug #16491 : 全コピー・特徴コピーについて
            //閲覧モードで開いたときに全コピー・閲覧モードボタン・印刷FGを活性にする。
            page.commands.removeClassCopy();
            //2020-02-04 END : Bug #16491 : 全コピー・特徴コピーについて

            //renew data shisaku hin
            var element = page.header.element,
                shisakuHinData = $.extend(true, {}, page.header.data.entries),
                shisakuHinDataDelete = [];

            $.each(shisakuHinData, function (index, item) {
                if (item.state == App.ui.page.dataSet.status.Deleted) {
                    shisakuHinDataDelete.push(item);
                } else {
                    item.state = App.ui.page.dataSet.status.Added;
                }
            })

            if (shisakuHinDataDelete.length) {
                $.each(shisakuHinDataDelete, function (index, item) {
                    delete shisakuHinData[item.__id];
                })
            }

            page.header.data = App.ui.page.dataSet();
            page.header.data.entries = shisakuHinData;

            element.find(".zen-copy-blank").val("").change();

            var id = element.attr("data-key"),
                entity = page.header.data.entry(id);

            entity.cd_shain = null;//試作コード
            entity.nen = null;
            entity.no_oi = null;
            //entity.no_irai = null;//依頼番号IR@                
            entity.nm_toroku = App.ui.page.user.Name;//登録者         
            entity.nm_koshin = App.ui.page.user.Name;//更新者               
            entity.cd_group = App.ui.page.user.cd_group;//グループCD             
            entity.nm_group = App.ui.page.user.nm_group;//グループ名            
            entity.cd_team = App.ui.page.user.cd_team;//チームCD         
            entity.nm_team = App.ui.page.user.nm_team;//チーム名
            entity.dt_toroku = new Date(),//$.datepicker.formatDate("yy-mm-ddTHH:mmZ", new Date());//登録日
            entity.dt_koshin = null;//更新日
            entity.kbn_haishi = false;//廃止                
            entity.flg_secret = false;//シークレット               
            entity.dt_shisan = null;//試算日        
            //entity.nm_hin = null;//品名  
            entity.nm_sample = null;//試算確定
            entity.seiho_no_shain = null;//製法No
            entity.seiho_no_nen = null;//製法No
            entity.seiho_no_oi = null;//製法No
            entity.seiho_no = null;//製法No
            entity.seq_shisaku = 0;
            entity.kbn_haita = App.ui.page.user.EmployeeCD;

            page.header.data.update(entity);
            element.form().bind(entity)

            //登録日
            element.findP("dt_toroku").text(($.datepicker.formatDate("yy-mm-dd", new Date())).replace(/-/g, "/"));
            //更新日
            element.findP("dt_koshin").text("");
            //登録者
            element.findP("nm_toroku").text(entity.nm_toroku);
            //更新者
            element.findP("nm_koshin").text(entity.nm_toroku);
            //試算日
            //element.findP("dt_shisan").text("");
            //試算確定
            element.findP("nm_sample").text("");

            //廃止 
            element.findP("kbn_haishi").prop("checked", false);
            //シークレット
            element.findP("flg_secret").text("OFF").removeClass("serect-color");

            //特徴コピーブタン、全コピーブタン
            page.commands.element.find(".command-haigohyo-tab-edit").prop("disabled", true);

            //全コピーモード
            page.values.modeDisp = page.values.modeDisplay.Zencopy;

            //原価試算タブを全コピーする。
            page.detail.tabs.genkashisan_tab.detail.zenCopy();

            //原価試算タブを全コピーする。
            page.detail.tabs.kihonjohyo_tab.detail.zenCopy();

            //基本情報タブを全コピーする。
            page.detail.tabs.tokuseichi_tab.detail.zenCopy();
            //工程パターン 
            page.detail.element.findP("pt_kotei").prop("disabled", false);

            setTimeout(function () {
                App.ui.loading.close();
            }, 1)
        }

        /**
         * データの削除処理を実行します。
         */
        //page.commands.remove = function () {
        //    var options = {
        //        text: App.messages.base.MS0003
        //    };

        //    page.dialogs.confirmDialog.confirm(options)
        //    .then(function () {

        //        App.ui.loading.show();

        //        var id = page.header.element.attr("data-key"),
        //            entity = page.header.data.entry(id),
        //            changeSets;

        //        //TODO: 論理削除フラグを更新する処理をここに記述します。
        //        //entity.flg_del = true;
        //        //page.header.data.update(entity);
        //        changeSets = page.header.data.getChangeSet();

        //        //TODO: データの削除処理をここに記述します。
        //        $.ajax(App.ajax.webapi.post(/* TODO: データ削除サービスのURL , */ changeSets))
        //        .done(function (result) {

        //            App.ui.page.notifyAlert.clear();
        //            App.ui.page.notifyInfo.clear();

        //            //TODO: 親画面にデータ削除通知イベントを発行する処理を記述します
        //            //page.options.tabComm.sendToOpener("removeDetailData", entity);
        //            App.ui.page.notifyInfo.message(App.messages.base.MS0008).show();

        //            setTimeout(function () {
        //                //TODO: 画面遷移処置をここに記述します。 
        //                //既定の処理では、次画面の新規登録の状態に画面遷移します。
        //                $(window).off("beforeunload");
        //                var paramIndex = top.location.href.indexOf('?');
        //                var shortURL = paramIndex < 0 ? window.location.href : window.location.href.substring(0, paramIndex);
        //                window.location = shortURL;
        //            }, 100);
        //        }).fail(function (error) {
        //            //TODO: データの削除失敗時の処理をここに記述します。
        //            App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

        //        }).always(function () {
        //            setTimeout(function () {
        //                page.header.element.find(":input:first").focus();
        //            }, 100);
        //            App.ui.loading.close();
        //        });
        //    });
        //};

        /**
        * Show ShisakuRetuTuikaDialog (試作列追加)
        */
        page.commands.showShisakuRetuTuikaDialog = function (e, dataMaster) {
            var target = $(e.target);
            if (target.prop("disabled")) {
                return;
            }
            page.dialogs.ShisakuRetuTuikaDialog.values.dataMaster = dataMaster;
            page.dialogs.ShisakuRetuTuikaDialog.element.modal("show");
        };

        /**
         * すべてのバリデーションを実行します。
         */
        page.validateAll = function () {

            var validations = [];
            //共通ヘッダー情報
            validations.push(page.header.validator.validate());
            //配合表
            validations.push(page.detail.tabs.haigohyo_tab.validateAll(true));
            //特性値
            validations.push(page.detail.tabs.kihonjohyo_tab.validateAll(false, true));
            //基本情報
            validations.push(page.detail.tabs.genkashisan_tab.validateAll());
            //原価試算
            validations.push(page.detail.tabs.tokuseichi_tab.validateAll(false, true));

            //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。TokuseiChi_Tab.validateAll

            return App.async.all(validations);
        };

        /**
         * Windows閉じる際のイベントを定義します。
         * @return 文字列を返却した場合に確認メッセージが表示されます。
         */
        App.ui.page.onclose = function () {

            var closeMessage = App.messages.base.exit;
            //2020-02-04 START : Bug #16491 : 全コピー・特徴コピーについて
            //閲覧モードで開いたときに全コピー・閲覧モードボタン・印刷FGを活性にする。
            //if (page.values.isChange)
            if (page.values.isChange && page.values.modeDisp != App.settings.app.m_shisaku_data.etsuran) {
                return closeMessage;
            }
            //2020-02-04 END : Bug #16491 : 全コピー・特徴コピーについて           
        };

        //2019-12-17 START : Request #16147 : 使用中（他の人が開いている）の場合、初期表示時に「他の方が開いているため、参照モードで開きます」のメッセージを通知し、参照モードで開く。
        /**
         * 排他区分をチェックする。
         */
        page.checkHaita = function () {

            App.ui.loading.show();
            $(".wrap, .footer").addClass("theme-blue");

            //TODO: URLパラメーターで渡された値を取得し、保持する処理を記述します。
            //モード
            if (!App.isUndefOrNull(App.uri.queryStrings.modeDisp)) {
                page.values.modeDisp = parseFloat(App.uri.queryStrings.modeDisp);
            } else {
                page.values.modeDisp = App.settings.app.m_shisaku_data.shinki;
            }
            //試作コード（社員）
            if (!App.isUndefOrNull(App.uri.queryStrings.cd_shain)) {
                page.values.cd_shain = parseFloat(App.uri.queryStrings.cd_shain);
            } else {
                page.values.cd_shain = null;
            }

            //試作コード（年）
            if (!App.isUndefOrNull(App.uri.queryStrings.nen)) {
                page.values.nen = parseFloat(App.uri.queryStrings.nen);
            } else {
                page.values.nen = null;
            }
            //試作コード（追番）
            if (!App.isUndefOrNull(App.uri.queryStrings.no_oi)) {
                page.values.no_oi = parseFloat(App.uri.queryStrings.no_oi);
            } else {
                page.values.no_oi = null;
            }

            return page.loadDialogsConfirm().then(function (result) {
                //画面を開きるとき排他区分がEmployeeCDになる。
                return page.checkHaitaKubun(true);
            }).then(function (result) {

                if (result == "OK") {
                    page.initialize();
                    return;
                }

                App.ui.loading.close();

                var usedDataInfor = jQuery.parseJSON(result);

                page.dialogs.confirmDialog.confirm({
                    text: App.str.format(App.messages.app.AP0164, usedDataInfor.userHaitaKaisha, usedDataInfor.userHaitaBusho, usedDataInfor.userHaitaName),
                    hideCancel: true,
                    multiModal: true,
                    backdrop: "static",
                    keyboard: false
                }).then(function () {

                    App.ui.loading.show();

                    setTimeout(function () {

                        //閲覧	
                        page.values.modeDisp = App.settings.app.m_shisaku_data.etsuran;
                        page.initialize();
                    }, 1);
                })

            }).fail(function (error) {

                App.ui.loading.close();

                if (error.status === 400 && error.responseJSON == "AP0007") {
                    App.ui.page.notifyAlert.message(App.messages.app.AP0007).show();
                    return;
                }

                App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
            })
        };
        //2019-12-17 END : Request #16147 : 使用中（他の人が開いている）の場合、初期表示時に「他の方が開いているため、参照モードで開きます」のメッセージを通知し、参照モードで開く。

        /**
        * 画面の初期化処理を行います。
        */
        page.initialize = function () {

            //2019-12-17 START : Request #16147 : 使用中（他の人が開いている）の場合、初期表示時に「他の方が開いているため、参照モードで開きます」のメッセージを通知し、参照モードで開く。
            //App.ui.loading.show();

            ////TODO: URLパラメーターで渡された値を取得し、保持する処理を記述します。
            ////モード
            //if (!App.isUndefOrNull(App.uri.queryStrings.modeDisp)) {
            //    page.values.modeDisp = parseFloat(App.uri.queryStrings.modeDisp);
            //} else {
            //    page.values.modeDisp = App.settings.app.m_shisaku_data.shinki;
            //}
            ////試作コード（社員）
            //if (!App.isUndefOrNull(App.uri.queryStrings.cd_shain)) {
            //    page.values.cd_shain = parseFloat(App.uri.queryStrings.cd_shain);
            //} else {
            //    page.values.cd_shain = null;
            //}

            ////試作コード（年）
            //if (!App.isUndefOrNull(App.uri.queryStrings.nen)) {
            //    page.values.nen = parseFloat(App.uri.queryStrings.nen);
            //} else {
            //    page.values.nen = null;
            //}
            ////試作コード（追番）
            //if (!App.isUndefOrNull(App.uri.queryStrings.no_oi)) {
            //    page.values.no_oi = parseFloat(App.uri.queryStrings.no_oi);
            //} else {
            //    page.values.no_oi = null;
            //}
            //2019-12-17 END : Request #16147 : 使用中（他の人が開いている）の場合、初期表示時に「他の方が開いているため、参照モードで開きます」のメッセージを通知し、参照モードで開く。

            page.initializeControl();
            page.initializeControlEvent();

            page.header.initialize();
            page.detail.initialize();

            page.flgEditAble = {
                subete: false
            }

            //2019-09-20 : START : Bug #15396 : 試作データの基本情報
            getKengenGamen(App.settings.app.id_gamen.shisaku_data).then(function (resultsKengen) {
                var subete = jQuery.grep(resultsKengen, function (n, i) {
                    return (n.id_kino == App.settings.app.id_kino.id_kino_shisaku_data && n.id_data == page.options.id_data_subete);
                });

                page.flgEditAble.subete = subete.length;
                //2019-09-20 : END : Bug #15396 : 試作データの基本情報

                return page.loadMasterData();
            }).then(function (result) {

                return page.loadDialogs();
            }).then(function (result) {

                return page.loadData();
            }).then(function (result) {

                return page.loadTabs();
            }).then(function (result) {

                if (page.values.modeDisp == App.settings.app.m_shisaku_data.shosai
                    || page.values.modeDisp == App.settings.app.m_shisaku_data.etsuran
                    || page.values.modeDisp == App.settings.app.m_shisaku_data.copy) {
                    //init data haigo
                    page.detail.tabs.haigohyo_tab.detail.search(true);

                    //2019-12-12 START : Bug #16120 : 現行仕様に合わせて都度計算して表示するようお願い致します。
                    page.detail.element.find("#haigohyo_tab").trigger("click");

                    page.detail.flgWaitFistStart = true;
                    page.detail.recalcuFistStart();
                    //2019-12-12 END : Bug #16120 : 現行仕様に合わせて都度計算して表示するようお願い致します。

                } else if (page.values.modeDisp == App.settings.app.m_shisaku_data.shinki) {
                    //init data haigo
                    page.detail.tabs.haigohyo_tab.detail.initInput();

                    page.detail.element.find("#kihonjohyo_tab").trigger("click");

                    App.ui.loading.close();
                } else {
                    App.ui.loading.close();
                }

                //TODO: 画面の初期化処理成功時の処理を記述します。
            }).fail(function (error) {
                if (error.status === 404) {
                    App.ui.page.notifyWarn.message(App.ajax.handleError(error).message).show();
                }
                else if (error.status === 400 && error.responseJSON == "AP0007") {
                    App.ui.page.notifyAlert.message(App.messages.app.AP0007).show();
                } else {
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();
                }
                App.ui.loading.close();
            }).always(function (result) {
                if (page.values.modeDisp == App.settings.app.m_shisaku_data.copy) {
                    //種別番号
                    page.header.element.find(".shubetu-required").show();
                }
                if (page.values.modeDisp == App.settings.app.m_shisaku_data.shinki) {
                    page.header.element.find(":input:not(:disabled):not([readonly]):first").focus();
                }
            });
        };

        //2019-12-17 START : Request #16147 : 使用中（他の人が開いている）の場合、初期表示時に「他の方が開いているため、参照モードで開きます」のメッセージを通知し、参照モードで開く。
        /**
        *　画面を開きるとき排他区分がEmployeeCDになる。
        *　画面を閉めるとき排他区分がnullになる。
        */
        page.checkHaitaKubun = function (isOpen) {
            var deferred = $.Deferred();

            if (page.values.modeDisp == App.settings.app.m_shisaku_data.shinki || page.values.modeDisp == App.settings.app.m_shisaku_data.etsuran) {
                deferred.resolve("OK");
            } else {

                var paraSearch = {
                    cd_shain: page.values.cd_shain
                   , nen: page.values.nen
                   , no_oi: page.values.no_oi
                   , EmployeeCD: App.ui.page.user.EmployeeCD
                   , isOpen: isOpen
                };

                $.ajax(App.ajax.webapi.post(page.header.urls.Post_haita, paraSearch)).then(function (result) {

                    deferred.resolve(result);
                }).fail(function (error) {

                    deferred.reject(error);
                })
            }
            return deferred.promise();
        }
        //2019-12-17 END : Request #16147 : 使用中（他の人が開いている）の場合、初期表示時に「他の方が開いているため、参照モードで開きます」のメッセージを通知し、参照モードで開く。


        //検索時に自動再計算
        //2019-12-12 START : Bug #16120 : 現行仕様に合わせて都度計算して表示するようお願い致します。
        page.detail.recalcuFistStart = function () {

            var clearMyTimer = function () {
                clearInterval(myInterval);
            }

            var myTimer = function () {

                if (!page.detail.values.isCompleteGenka || !page.detail.values.isCompleteTokuseichi) {
                    return;
                }

                clearMyTimer()

                setTimeout(function () {
                    page.detail.recalculation("genkashisan_tab", "FirstStart");
                }, 1000)

            };

            var myInterval = setInterval(myTimer, 1000);
        }
        //2019-12-12 END : Bug #16120 : 現行仕様に合わせて都度計算して表示するようお願い致します。

        /**
        * 制御使用入力
        */
        page.controlInput = function () {
            //初期表示_閲覧モード
            if (page.values.modeDisp == App.settings.app.m_shisaku_data.etsuran) {

                //2020-02-04 START : Bug #16491 : 全コピー・特徴コピーについて
                //閲覧モードで開いたときに全コピー・閲覧モードボタン・印刷FGを活性にする。
                var element = $("#command_left, #command_right, .content-wrap");

                //element.find("input").prop("readonly", true);
                //element.find("input[type='checkbox']").prop('disabled', true);
                //element.find("select").prop('disabled', true);
                //element.find("button").prop('disabled', true);
                //element.find("textarea").prop('readonly', true);
                //element.find(".enableColor").removeClass("enableColor");

                element.find("input:not([disabled]):not([readonly])").addClass("inputCopy").prop("readonly", true);
                element.find("input[type='checkbox']:not([disabled])").addClass("checkboxCopy").prop('disabled', true);
                element.find("select:not([disabled])").addClass("selectCopy").prop('disabled', true);
                element.find("button:not([disabled])").addClass("buttonCopy").prop('disabled', true);
                element.find("textarea:not([disabled]):not([readonly])").addClass("textareaCopy").prop('readonly', true);
                element.find(".enableColor").addClass("enableColorCopy").removeClass("enableColor");

                //19075 - 2022/06/06 - ShohinKaihatsuSuppot Modify 2022FY: -st edit : enable button #GenzairyoKakunin
                element.find("#Tokuchokopi, #Zenkopi, #close, #GenzairyoKakunin").prop('disabled', false);
                // -ed

                page.detail.tabs.haigohyo_tab.element.find(".flg_print, .flg_print_all").prop('disabled', false).prop("readonly", false);
                page.detail.tabs.haigohyo_tab.element.find(".enableColor-etsuran").addClass("enableColor");
                //2020-02-04 END: Bug #16491 : 全コピー・特徴コピーについて
            }

            //製法コピ
            if (page.values.modeDisp == App.settings.app.m_shisaku_data.copy) {
                var element = $("#command_left, #command_right, .content-wrap")

                element.find("input").not(".seihokopiInput").prop("readonly", true);
                element.find("input[type='checkbox']").prop('disabled', true);
                element.find("select").prop('disabled', true);
                element.find("button").prop('disabled', true);
                element.find("textarea").prop('readonly', true);
                element.find(".seihokopi").not(".shisanIraied").not(".tanka-replace").prop('disabled', false).removeAttr('readonly');
                element.find(".enableColor").removeClass("enableColor");
                element.find(".check-select-shisaku").addClass("enableColor");
                element.find("#close").prop('disabled', false);
            }

            setTimeout(function () {
                //左の位置IDを貰います
                var storeIDpositionLeft = App.str.format("101_ShishakuData_positionLeft_{user}_{cd_shain}_{nen}_{no_oi}", {
                    user: App.ui.page.user.EmployeeCD
                    , cd_shain: page.values.cd_shain
                    , nen: page.values.nen
                    , no_oi: page.values.no_oi
                }),
                positionLeftFromPage = JSON.parse(localStorage.getItem(storeIDpositionLeft)),

                //アクティブタブIDを貰います
                storeIDactiveTab = App.str.format("101_ShishakuData_activeTab_{user}_{cd_shain}_{nen}_{no_oi}", {
                    user: App.ui.page.user.EmployeeCD
                    , cd_shain: page.values.cd_shain
                    , nen: page.values.nen
                    , no_oi: page.values.no_oi
                }),
                activeTabFromPage = JSON.parse(localStorage.getItem(storeIDactiveTab));

                page.detail.afterSave = true;
                //左の位置IDとアクティブタブIDを使用する。それから、ローカルストレージから削除する
                if (positionLeftFromPage) {
                    page.values.positionLeft = Number(positionLeftFromPage);
                    if (activeTabFromPage) {
                        page.detail.element.find(".tabbable .nav-tabs li[data-for='" + activeTabFromPage + "']").find("a").trigger("click");
                        //2019-12-12 START : Bug #16120 : 現行仕様に合わせて都度計算して表示するようお願い致します。
                        if (!page.detail.flgWaitFistStart) {
                            App.ui.loading.close();
                        }
                        //2019-12-12 END : Bug #16120 : 現行仕様に合わせて都度計算して表示するようお願い致します。
                    } else {

                        var target = $(".tabbable .nav-tabs .active").attr("data-for");
                        $("#" + target + " .dt-container .flow-container .dt-body").scrollLeft(page.values.positionLeft);
                    }
                } else if (activeTabFromPage) {
                    page.detail.element.find(".tabbable .nav-tabs li[data-for='" + activeTabFromPage + "']").find("a").trigger("click");
                    //2019-12-12 START : Bug #16120 : 現行仕様に合わせて都度計算して表示するようお願い致します。
                    if (!page.detail.flgWaitFistStart) {
                        App.ui.loading.close();
                    }
                    //2019-12-12 END : Bug #16120 : 現行仕様に合わせて都度計算して表示するようお願い致します。
                }

                setTimeout(function () {
                    //保存のメッセージを貰います
                    var storeIDmess = App.str.format("101_ShishakuData_mess_{user}_{cd_shain}_{nen}_{no_oi}", {
                        user: App.ui.page.user.EmployeeCD
                        , cd_shain: page.values.cd_shain
                        , nen: page.values.nen
                        , no_oi: page.values.no_oi
                    });

                    //保存のメッセージを見ます。それから、ローカルストレージから削除する
                    var MessFromPage = JSON.parse(localStorage.getItem(storeIDmess));
                    if (MessFromPage) {
                        App.ui.page.notifyInfo.message(MessFromPage).show();
                    }

                    localStorage.clear();

                    page.detail.afterSave = false;

                    setTimeout(function () {
                        //原価原料テーブルにデータがなくてもサンプルNoの列は表示されるようになっています。
                        page.detail.checkNewGenryo();

                        $(window).resize();
                        page.header.element.find(":input:not(:disabled):not([readonly]):first").focus();

                    }, 500)
                }, 1)
            }, 1)
        }

        /**
        * 原価原料テーブルにデータがなくてもサンプルNoの列は表示されるようになっています。
        */
        page.detail.checkNewGenryo = function () {
            if (!page.detail.tabs.genkashisan_tab.detail.values.isNewGenryo) {
                //2019-12-12 START : Bug #16120 : 現行仕様に合わせて都度計算して表示するようお願い致します。
                if (!page.detail.flgWaitFistStart) {
                    App.ui.loading.close();
                }
                //2019-12-12 END : Bug #16120 : 現行仕様に合わせて都度計算して表示するようお願い致します。
                return;
            }

            if (!page.detail.tabs.genkashisan_tab.detail.values.isNewGenryo.length) {
                //2019-12-12 START : Bug #16120 : 現行仕様に合わせて都度計算して表示するようお願い致します。
                if (!page.detail.flgWaitFistStart) {
                    App.ui.loading.close();
                }
                //2019-12-12 END : Bug #16120 : 現行仕様に合わせて都度計算して表示するようお願い致します。
                return;
            }

            page.detail.getDataTab("haigohyo_tab").then(function (dataHaigo) {

                var element = page.header.element,
                id = element.attr("data-key"),
                entity = $.extend(true, {}, page.header.data.entry(id));

                dataHaigo["shisaku_hin"] = entity;

                var shisakuData = {};

                $.each(page.detail.tabs.genkashisan_tab.detail.values.isNewGenryo, function (index, item) {
                    shisakuData[item] = dataHaigo.shisaku[item];
                })

                page.detail.tabs.genkashisan_tab.detail.values.isNewGenryo = [];
                dataHaigo.shisaku = shisakuData;

                page.detail.getDataTab("tokuseichi_tab").then(function (dataToku) {
                    dataHaigo["dataToku"] = dataToku;
                    page.detail.tabs.genkashisan_tab.detail.recalculation("EventHaigo", dataHaigo, true);
                })
            })
        }

        page.loadTabs = function () {
            //TODO: 共有ダイアログデータを取得し、画面にバインドする処理を記述します。
            return App.async.all({
                haigohyo_tab: $.get(page.urls.haigohyo_tab),
                tokuseichi_tab: $.get(page.urls.tokuseichi_tab),
                kihonjohyo_tab: $.get(page.urls.kihonjohyo_tab),
                genkashisan_tab: $.get(page.urls.genkashisan_tab)
            }).then(function (result) {
                var tab_container = page.detail.element.find("#tab-container");

                //配合表
                tab_container.append(result.successes.haigohyo_tab);
                page.detail.tabs.haigohyo_tab = HaigoHyo_Tab;
                page.detail.tabs.haigohyo_tab.values.pt_kotei = page.detail.element.findP("pt_kotei").val();
                page.detail.tabs.haigohyo_tab.initialize();
                page.detail.tabs.haigohyo_tab.controlInput = page.controlInput;
                page.detail.tabs.haigohyo_tab.confirmDialog = page.dialogs.confirmDialog.confirm;
                page.detail.tabs.haigohyo_tab.shisakuCopyDialog = page.commands.showShisakuRetuTuikaDialog;
                page.detail.tabs.haigohyo_tab.addSample = page.detail.addSample;
                page.detail.tabs.haigohyo_tab.delSample = page.detail.delSample;
                page.detail.tabs.haigohyo_tab.autoFlagCheck = page.detail.autoFlagCheck;
                page.detail.tabs.haigohyo_tab.changeSample = page.detail.changeSample;
                page.detail.tabs.haigohyo_tab.moveSample = page.detail.moveSample;
                page.detail.tabs.haigohyo_tab.showIroSettingDialog = page.detail.showIroSettingDialog;
                page.detail.tabs.haigohyo_tab.showGenryoIchiranDialog = page.commands.showGenryoIchiranDialog;
                page.detail.tabs.haigohyo_tab.recalculation = page.detail.recalculation;
                page.detail.tabs.haigohyo_tab.zenkopi = page.detail.zenkopi;
                page.detail.tabs.haigohyo_tab.zenkopiPage = page.detail.zenkopiPage;
                page.detail.tabs.haigohyo_tab.getDataHeader = page.header.getData;
                page.detail.tabs.haigohyo_tab.wait = page.wait;//2020-08-06 :14007 : Bug #18857 内容量0.1でエラーとなる

                //特性値
                tab_container.append(result.successes.tokuseichi_tab);
                page.detail.tabs.tokuseichi_tab = TokuseiChi_Tab;
                page.detail.tabs.tokuseichi_tab.values.cd_tani = page.values.dataTaniTokuseichi;
                page.detail.tabs.tokuseichi_tab.values.pt_kotei = page.values.dataPatanTokuseichi;
                page.detail.tabs.tokuseichi_tab.detail.getDataTab = page.detail.getDataTab;
                page.detail.tabs.tokuseichi_tab.detail.updateOtherTab = page.detail.updateOtherTab;
                page.detail.tabs.tokuseichi_tab.dataHaigoKyodo = page.values.masterData.HaigoKyodo;
                page.detail.tabs.tokuseichi_tab.initialize();
                page.detail.tabs.tokuseichi_tab.recalculation = page.detail.recalculation;
                page.detail.tabs.tokuseichi_tab.getDataHeader = page.header.getData;
                page.detail.tabs.tokuseichi_tab.wait = page.wait;//2020-08-06 :14007 : Bug #18857 内容量0.1でエラーとなる

                //基本情報
                tab_container.append(result.successes.kihonjohyo_tab);
                page.detail.tabs.kihonjohyo_tab = KihonJohyo_Tab;
                page.detail.tabs.kihonjohyo_tab.values.dataDetail = page.values.dataBindKihonJohyo;
                page.detail.tabs.kihonjohyo_tab.initialize();
                page.detail.tabs.kihonjohyo_tab.detail.tantoshaDialog = page.detail.showTantoshaIchiranEigyoDialog;
                page.detail.tabs.kihonjohyo_tab.detail.changeKihonTab = page.header.changeFromKihonTab;
                page.detail.tabs.kihonjohyo_tab.detail.checkShinkiUpdate = page.header.checkShinkiUpdate;
                //page.detail.tabs.kihonjohyo_tab.changeShosuShitei = page.detail.changeShosuShitei;
                page.detail.tabs.kihonjohyo_tab.detail.updateOtherTab = page.detail.updateOtherTab;
                page.detail.tabs.kihonjohyo_tab.confirmDialog = page.dialogs.confirmDialog.confirm;
                page.detail.tabs.kihonjohyo_tab.getDataHeader = page.header.getData;
                page.detail.tabs.kihonjohyo_tab.wait = page.wait;//2020-08-06 :14007 : Bug #18857 内容量0.1でエラーとなる

                //原価試算
                tab_container.append(result.successes.genkashisan_tab);
                page.detail.tabs.genkashisan_tab = GenkaShisan_Tab;
                page.detail.tabs.genkashisan_tab.initialize();
                page.detail.tabs.genkashisan_tab.detail.updateOtherTab = page.detail.updateOtherTab;
                page.detail.tabs.genkashisan_tab.getDataHeader = page.header.getData;
                page.detail.tabs.genkashisan_tab.detail.getDataTab = page.detail.getDataTab;
                page.detail.tabs.genkashisan_tab.confirmDialog = page.dialogs.confirmDialog.confirm;
                page.detail.tabs.genkashisan_tab.confirmKoteiZokusei = page.confirmKoteiZokusei;
                page.detail.tabs.genkashisan_tab.wait = page.wait;//2020-08-06 :14007 : Bug #18857 内容量0.1でエラーとなる
                $("#close").prop('disabled', false);
            });
        };

        /**
        * 特性値の分析値を毎回自動計算をもらいする。
        */
        page.detail.autoFlagCheck = function () {
            return page.detail.tabs.tokuseichi_tab.detail.element.findP("flg_auto")[0].checked;
        }

        /**
        * 新規モード時に一つ目が使う。
        */
        page.header.checkShinkiUpdate = function () {
            if (page.values.modeDisp == App.settings.app.m_shisaku_data.shinki) {
                var element = page.header.element,
                    id = element.attr("data-key"),
                    entity = page.header.data.entry(id);

                var kihonElement = $("#KihonJohyo_Tab").find(".new");
                //販責会社
                
                entity.cd_hanseki = kihonElement.findP("cd_hanseki").val();
                debugger
                //工場　担当会社
                entity.cd_kaisha = kihonElement.findP("cd_kaisha").val();
                //工場　担当工場
                entity.cd_kojo = kihonElement.findP("cd_kojo").val();

                page.header.data.update(entity);
            }
        }

        /**
        * update shisakuhin when kihon tab changed
        */
        page.header.changeFromKihonTab = function (property, val, isRecalcu) {
            var element = page.header.element,
                id = element.attr("data-key"),
                entity = page.header.data.entry(id);

            //フラグ変更画面
            page.values.isChange = true;

            entity[property] = val;

            page.header.data.update(entity);

            //小数指定
            page.values.keta_shosu = entity.keta_shosu;

            //工場　担当会社,工場　担当工場
            if (["", "cd_kojo"].indexOf(property) > 0) {
                page.detail.updateOtherTab("haigohyo_tab", property, $.extend(true, {}, entity));
            }

            //容量（数値入力）（※）
            if (property == "cd_tani") {
                page.detail.changeTani(entity);
            }

            //小数指定
            if (property == "keta_shosu") {
                //page.detail.changeShosuShitei(val, isRecalcu);
                page.detail.tabs.haigohyo_tab.changeShosuShitei(val, isRecalcu);
            }
        }

        /**
        * update other tab
        */
        page.detail.updateOtherTab = function (tabName, fiel, val, sample) {
            switch (tabName) {
                case "genkashisan_tab":
                    page.detail.tabs.genkashisan_tab.detail.updateFromOtherTab(fiel, val, sample);
                    break;

                case "haigohyo_tab":
                    page.detail.tabs.haigohyo_tab.detail.updateFromOtherTab(fiel, val, sample);
                    break;

                case "tokuseichi_tab":
                    page.detail.tabs.tokuseichi_tab.detail.updateFromOtherTab(fiel, val, sample);
                    break;

                    //case "header_kakutei"://原価試算登録ボタン押下
                    //page.header.kakuteiInfo(val);
                    break;
                default:
                    break;
            }
        }

        ///**
        //* 原価試算登録ボタン押下
        //* 登録が成功した場合
        //* 画面．試算日 = システム日付
        //* 画面．試算確定 = 登録を行ったサンプルNo
        //*/
        //page.header.kakuteiInfo = function (val) {
        //    var element = page.header.element;

        //    //試算確定 
        //    element.findP("seq_shisaku_rireki").val(val.seq_shisaku);

        //    //試算日 
        //    element.findP("dt_shisan").text(($.datepicker.formatDate("yy-mm-dd", new Date(val.dt_shisan))).replace(/\-/g, "/"));
        //    //試算確定 
        //    element.findP("nm_sample").text(val.nm_sample);
        //}

        /**
        * get all data in haigo tab
        */
        page.detail.getDataTab = function (tab) {
            switch (tab) {

                case "haigohyo_tab":

                    return page.detail.tabs.haigohyo_tab.detail.allDataOnTab();
                    break;
                case "tokuseichi_tab":

                    return page.detail.tabs.tokuseichi_tab.detail.allDataOnTab();
                    break;
                default:
                    return App.async.reject();
                    break;
            }
        }

        /**
        * control display haigo tab when change fractional designation
        */

        //2019-12-12 : Bug #16083 :【分析マスタの最新情報に更新】ボタンを押す時画面が計算しる。
        //page.detail.recalculation = function (isTab, isFiel, data) {
        page.detail.recalculation = function (isTab, isFiel, data, isNotCheckFlgAuto) {
            App.ui.loading.show();

            page.detail.getDataTab("haigohyo_tab").then(function (dataHaigo) {

                var element = page.header.element,
                id = element.attr("data-key"),
                entity = $.extend(true, {}, page.header.data.entry(id));

                dataHaigo["shisaku_hin"] = entity;

                switch (isTab) {
                    case "tokuseichi_tab"://特性値

                        //2019-12-12 : Bug #16083 :【分析マスタの最新情報に更新】ボタンを押す時画面が計算しる。
                        //page.detail.tabs.tokuseichi_tab.detail.recalculation(isFiel, dataHaigo);
                        page.detail.tabs.tokuseichi_tab.detail.recalculation(isFiel, dataHaigo, isNotCheckFlgAuto);

                        break;
                    case "genkashisan_tab"://原価試算
                        page.detail.getDataTab("tokuseichi_tab").then(function (dataToku) {
                            dataHaigo["dataToku"] = dataToku;
                            page.detail.tabs.genkashisan_tab.detail.recalculation(isFiel, dataHaigo);
                        })

                        break;
                    default:
                        App.ui.loading.close();
                        break;
                }
            }).fail(function () {
                App.ui.loading.close();
            })
        };

        ///**
        //* control display haigo tab when change fractional designation
        //*/
        //page.detail.changeShosuShitei = function (keta_shosu, isRecalcu) {
        //    page.detail.tabs.haigohyo_tab.changeShosuShitei(keta_shosu, isRecalcu);
        //}

        /**
        * control display tokuseichi tab when change cd_tani
        */
        page.detail.changeTani = function (entity) {
            //特性値
            page.detail.tabs.tokuseichi_tab.changeKoteiPatan(entity.pt_kotei, entity.cd_tani, "changeTani");

            //原価試算
            page.detail.tabs.genkashisan_tab.changeKoteiPatan(entity.pt_kotei, entity.cd_tani);

            //原価試算タブ．原料費の再計算を行う
            //if (App.isFunc(HaigoHyo_Tab.recalculation)) {
            //・原価試算タブ．原料費の再計算を行う
            page.detail.recalculation("tokuseichi_tab", "工程パターン");
            //}
        };

        /**
        * event del sample in haigo tab。
        */
        page.detail.delSample = function (data) {
            //試作列削除時に試算試作品の試作SEQをチェックする。同ときに試作SEQが０をなります
            page.header.checkDelShisakuEndCopy(data.seq_shisaku);
            page.detail.tabs.tokuseichi_tab.detail.delSample(data);
            page.detail.tabs.genkashisan_tab.detail.delSample(data);
        };

        /**
        * move sample in tokusechi tab and genkashisan tab
        */
        page.detail.moveSample = function (index, isRight) {
            page.detail.tabs.tokuseichi_tab.detail.moveSample(index, isRight);
            page.detail.tabs.genkashisan_tab.detail.moveSample(index, isRight);
        }

        /**
        * control display name sample in tokusechi tab and genkashisan tab
        */
        page.detail.changeSample = function (data) {
            //var element = page.header.element;
            ////※配合表タブで試作列のサンプルNoが変更される場合、「試算確定」も変更
            //if (element.findP("seq_shisaku_rireki").val() == data.seq_shisaku) {
            //    page.header.element.findP("nm_sample").text(data.nm_sample);
            //}

            page.detail.tabs.tokuseichi_tab.detail.changeSampleName(data);
            page.detail.tabs.genkashisan_tab.detail.changeSampleName(data);
        }

        /**
        * event add new sample in haigo tab。
        */
        page.detail.addSample = function (data, isAddEndCol, isNewData, indexCol, isCopyCol) {
            var dataAdd;

            dataAdd = $.extend(true, {}, data);

            //add new col in tokusechi tab
            page.detail.tabs.tokuseichi_tab.detail.addSample(dataAdd, isAddEndCol, indexCol, isNewData);
            page.detail.tabs.tokuseichi_tab.detail.adjustWithTable(1);

            //add new col in genkashisan tab
            if (isNewData) {
                var shisakuhin = page.header.getData();
                //容量
                dataAdd["yoryo"] = shisakuhin.yoryo;
                //入り数
                dataAdd["irisu"] = shisakuhin.su_iri;
                //売価
                dataAdd["ko_baika"] = shisakuhin.baika;
                page.detail.tabs.genkashisan_tab.detail.addSample(dataAdd, isNewData, isAddEndCol, indexCol);
                page.detail.tabs.genkashisan_tab.detail.adjustWithTable(1);

                //試作列コピー。
                page.detail.recalculation("tokuseichi_tab", "試作列コピー");
            }
        };

        /**
         * 画面コントロールの初期化処理を行います。
         */
        page.initializeControl = function () {

            //TODO: 画面全体で利用するコントロールの初期化処理をここに記述します。
            $(".part").part();
        };

        /**
        * 原料分析のデータが準備する。
        */
        page.commands.genryoBunsekiMae = function () {

            App.ui.loading.show();

            page.detail.tabs.haigohyo_tab.detail.genryoBunsekiData().then(function (dataGenryo) {

                App.ui.page.notifyAlert.remove($("#GenryoBunseki"));
                return $.ajax(App.ajax.webapi.post(page.header.urls.genryoDelivery, dataGenryo)).then(function (result) {

                    var shisakuhin = page.header.getData(),
                        link = App.str.format(page.commands.urls.genryoDelivery, { id_session: result, cd_kaisha: shisakuhin.cd_kaisha, cd_kojo: shisakuhin.cd_kojo });//試作表分析データ確認画面起動

                    window.open(link, "_blankGenryo");
                }).fail(function (error) {
                    //TODO: データの保存失敗時の処理をここに記述します。
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message, $("#GenryoBunseki")).show();
                }).always(function () {
                    App.ui.loading.close();
                })
            }).fail(function () {
                App.ui.loading.close();
            })
        }

        /**
        * コントロールへのイベントの紐づけを行います。
        */
        page.initializeControlEvent = function () {

            var element = $("#command_left, #command_right");
            page.commands.element = element;

            //原料一覧            
            element.find("#GenryoIchiran").on("click", function () { page.commands.showGenryoIchiranDialog(undefined) });

            //原料分析            
            element.find("#GenryoBunseki").on("click", page.commands.genryoBunsekiMae);

            //分析値の変更確認
            element.find("#HenkoKakunin").on("click", page.detail.showHenkoKakuninDialog);

            //試作表出力
            element.find("#ShisakuShutsuryoku").on("click", page.commands.shisakuExport);

            //ｻﾝﾌﾟﾙ説明書
            element.find("#ShisakuSetsumeisho").on("click", page.commands.shisakuSetsumeisho);

            //分析マスタの最新情報に更新
            element.find("#JohoKoshin").on("click", page.commands.genryoJohoKoshin);

            //原価試算表印刷
            element.find("#GenkaInsatsu").on("click", page.commands.genkaInsatsu);

            //特徴コピー            
            element.find("#Tokuchokopi").on("click", page.commands.Tokuchokopi);

            //全コピー            
            element.find("#Zenkopi").on("click", page.commands.zenCopy);

            //19075 - 2022/06/06 - ShohinKaihatsuSuppot Modify 2022FY: -st
            //原料費確認
            element.find("#GenzairyoKakunin").on("click", page.commands.beforeOpenGenzairyoKakuninDialog);
            //-ed

            //保存
            element.find("#Save").on("click", page.commands.beforSave);

            //原料一覧            
            element.find("#close").on("click", function () {
                if (page.values.isChange && page.values.modeDisp != App.settings.app.m_shisaku_data.etsuran) {
                    page.dialogs.confirmDialog.confirm({
                        text: App.messages.base.exit,
                        multiModal: true,
                        backdrop: "static",
                        keyboard: false
                    }).then(function () {
                        page.checkHaitaKubun(false).then(function (result) {
                            page.values.isChange = false;
                            window.close();
                        }).fail(function (error) {
                            $("#ConfirmDialog").find(".modal-dialog").addClass("modal-mid");
                            page.dialogs.confirmDialog.confirm({
                                text: App.messages.app.AP0186,
                                multiModal: true,
                                backdrop: "static",
                                keyboard: false
                            }).then(function () {
                                $("#ConfirmDialog").find(".modal-dialog").removeClass("modal-mid");
                                page.values.isChange = false;
                                window.close();
                            }).fail(function () {
                                $("#ConfirmDialog").find(".modal-dialog").removeClass("modal-mid");
                            });
                        });
                        
                    });
                } else {
                    page.checkHaitaKubun(false).then(function (result) {
                        window.close();
                    }).fail(function (error) {
                        $("#ConfirmDialog").find(".modal-dialog").addClass("modal-mid");
                        page.dialogs.confirmDialog.confirm({
                            text: App.messages.app.AP0186,
                            multiModal: true,
                            backdrop: "static",
                            keyboard: false
                        }).then(function () {
                            $("#ConfirmDialog").find(".modal-dialog").removeClass("modal-mid");
                            page.values.isChange = false;
                            window.close();
                        }).fail(function () {
                            $("#ConfirmDialog").find(".modal-dialog").removeClass("modal-mid");
                        });
                    });
                }
                
            });
        };

        /**
        * 原料一覧ダイアログ。
        */
        page.commands.showGenryoIchiranDialog = function (dataDefault) {
            page.dialogs.GenryoIchiranDialog.values.dataHin = page.header.getData();
            page.dialogs.GenryoIchiranDialog.values.dataDefault = dataDefault;

            page.dialogs.GenryoIchiranDialog.element.modal({
                backdrop: false,
                keyboard: true
            });
            page.dialogs.GenryoIchiranDialog.values.isSelected = false;
            page.dialogs.GenryoIchiranDialog.element.modal("show");
        };

        /**
        * 分析原料確認データ確認。
        */
        page.detail.showHenkoKakuninDialog = function (e) {

            var memo = $(e.target).val();

            page.dialogs.BunsekiGenryoKakunin_Dialog.param = {
                cd_shain: page.values.cd_shain,
                nen: page.values.nen,
                no_oi: page.values.no_oi
            };

            //配合表上のすべてのデータ原料を貰らう。
            page.dialogs.BunsekiGenryoKakunin_Dialog.dataGenryo = page.detail.tabs.haigohyo_tab.detail.dataGenryoOnTab();
            page.dialogs.BunsekiGenryoKakunin_Dialog.element.modal("show");
        };

        /**
        * 19075 - 2022/06/06 - ShohinKaihatsuSuppot Modify 2022FY: -st
        * check calculation validate fail。
        */
        page.commands.beforeOpenGenzairyoKakuninDialog = function (e) {
            App.ui.loading.show();

            setTimeout(function () {

                if (!ConfirmDialog.isOpen) {

                    var breakSave = false;
                    //特性値タブの再計算する時にエラーがありますから。何手もしません。
                    if (!App.isUndefOrNull(page.detail.tabs.tokuseichi_tab.values.messCalculator)) {
                        App.ui.page.notifyAlert.remove(page.detail.tabs.tokuseichi_tab.values.messTarget);
                        //計算結果は桁数がオーバーの場合、メッセージ表示。
                        App.ui.page.notifyAlert.message(page.detail.tabs.tokuseichi_tab.values.messCalculator, page.detail.tabs.tokuseichi_tab.values.messTarget).show();
                        App.ui.loading.close();
                        breakSave = true;
                    }

                    //原価試算タブの再計算する時にエラーがありますから。何手もしません。
                    if (!App.isUndefOrNull(page.detail.tabs.genkashisan_tab.values.messCalculator)) {
                        App.ui.page.notifyAlert.remove(page.detail.tabs.genkashisan_tab.values.messTarget);
                        //計算結果は桁数がオーバーの場合、メッセージ表示。		
                        App.ui.page.notifyAlert.message(page.detail.tabs.genkashisan_tab.values.messCalculator, page.detail.tabs.genkashisan_tab.values.messTarget).show();
                        App.ui.loading.close();
                        breakSave = true;
                    }

                    if (breakSave) {
                        App.ui.loading.close();
                        return;
                    } else {
                        page.commands.showGenzairyoKakuninDialog(e);
                    }
                } else {
                    App.ui.loading.close();
                }
            }, 100)
        }

        /**
        * 19075 - 2022/06/06 - ShohinKaihatsuSuppot Modify 2022FY: -st
        * 分析原料確認データ確認。
        */
        page.commands.showGenzairyoKakuninDialog = function (e) {

            var shisakuSelected = page.detail.tabs.haigohyo_tab.detail.element.find(".selectedInput"),
                isSelected = false,
                keySample,
                entryKey,
                dataQuanity;

            if (!App.isUndefOrNull(shisakuSelected)) {
                keySample = shisakuSelected.closest("td").attr("index-col") || shisakuSelected.closest("th").attr("index-col");
                if (!App.isUndefOrNull(keySample)) {
                    isSelected = true;
                }
            }

            if (!isSelected) {
                App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0028, { strName: "試作列" })).remove();
                App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0028, { strName: "試作列" })).show();
                App.ui.loading.close();
                return;
            } else {
                App.ui.page.notifyInfo.message(App.str.format(App.messages.app.AP0028, { strName: "試作列" })).remove();
            }

            page.dialogs.GenzairyoKakuninDialog.ValidateListFieldsAllTabs(keySample).then(function () {

                entryKey = HaigoHyo_Tab.dataKeySample["sample-" + keySample];

                if (App.isUndefOrNull(entryKey)) {
                    App.ui.loading.close();
                    return;
                }

                dataQuanity = page.detail.tabs.haigohyo_tab.detail.dataShisaku.entry(entryKey);

                var element = page.header.element,
                    id = element.attr("data-key"),
                    entity = $.extend(true, {}, page.header.data.entry(id)),
                    data = {};

                var elementColGenkaShisan = page.detail.tabs.genkashisan_tab.detail.element.find(".sample-" + dataQuanity.seq_shisaku),
                        idGenkaShisan = elementColGenkaShisan.attr("data-key"),
                        entityGenkaShisan = page.detail.tabs.genkashisan_tab.detail.data.entry(idGenkaShisan);

                page.detail.tabs.genkashisan_tab.detail.getDataTab("haigohyo_tab").then(function (dataHaigo) {

                    data = dataHaigo;

                    data["seq_shisaku"] = dataQuanity.seq_shisaku;

                    data["shisaku_hin"] = entity;

                    data["genka_shisan"] = entityGenkaShisan;

                    return page.detail.getDataTab("tokuseichi_tab");
                }).then(function (dataToku) {
                    data["dataToku"] = dataToku;

                    page.dialogs.GenzairyoKakuninDialog.values.data = data;

                    page.dialogs.GenzairyoKakuninDialog.element.modal("show");
                
                }).then(function () {
                    App.ui.loading.close();
                }).fail(function () {
                    App.ui.loading.close();
                });
            }).fail(function () {
                App.ui.loading.close();
            });
        };
        // -ed

        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function () {
            page.values.masterData = {};

            var paraSearch = {
                cd_shain: page.values.cd_shain,
                nen: page.values.nen,
                no_oi: page.values.no_oi,
                shubetsu_bango: App.settings.app.cd_category.shubetsu_bango,
                kotei_patan: App.settings.app.cd_category.kotei_patan,
                Kotei_zokusei: App.settings.app.cd_category.Kotei_zokusei,
                kbn_meisho: App.settings.app.cd_category.kbn_masutado,
                kbn_haigo_kyodo: App.settings.app.cd_category.kbn_haigo_kyodo,
                cd_kengen: App.ui.page.user.cd_kengen,
                cd_group: App.ui.page.user.cd_group
            }
            //TODO: 画面内のドロップダウンなどで利用されるマスターデータを取得し、画面にバインドする処理を記述します。
            return $.ajax(App.ajax.webapi.get(page.header.urls.loadMasterData, paraSearch)).then(function (result) {
                page.values.masterData = result;

                //種別番号
                var no_shubetu = page.header.element.findP("no_shubetu");
                no_shubetu.children().remove();
                App.ui.appendOptions(
                    no_shubetu,
                    "cd_literal",
                    "nm_literal",
                    jQuery.grep(result.ShubetsuBango, function (n, i) {
                        n["cd_literal"] = Number(n.nm_literal)
                        return n;
                    }),
                    true
                );

                var pt_kotei = page.detail.element.findP("pt_kotei");
                pt_kotei.children().remove();
                App.ui.appendOptions(
                    pt_kotei,
                    "cd_literal",
                    "nm_literal",
                    result.KoteiPatan,
                    true
                );
            });
        };

        /**
        * 工程を確認する
        */
        page.confirmKoteiZokusei = function (cd_literal) {
            KoteiZokusei = jQuery.grep(page.values.masterData.KoteiZokusei, function (n, i) {
                return (n.cd_literal == cd_literal);
            })

            if (KoteiZokusei.length) {
                return KoteiZokusei[0];
            } else {
                return [];
            }
        }

        /**
         * 共有ダイアログのロード処理を実行します。
         */
        page.loadDialogsConfirm = function () {

            //TODO: 共有ダイアログデータを取得し、画面にバインドする処理を記述します。
            return App.async.all({
                confirmDialog: $.get(page.urls.confirmDialog)
            }).then(function (result) {

                $("#dialog-container").append(result.successes.confirmDialog);
                //$("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;
            });
        };

        /**
         * 共有ダイアログのロード処理を実行します。
         */
        page.loadDialogs = function () {

            //TODO: 共有ダイアログデータを取得し、画面にバインドする処理を記述します。
            return App.async.all({
                //confirmDialog: $.get(page.urls.confirmDialog),
                IroShitei_Dialog: $.get(page.dialogs.urls.IroShitei_Dialog),
                ShisakuRetuTuikaDialog: $.get(page.dialogs.urls.ShisakuRetuTuikaDialog),
                TantoEigyoKensaku_Dialog: $.get(page.dialogs.urls.TantoEigyoKensaku_Dialog),
                MemoNyuryoku_Dialog: $.get(page.dialogs.urls.MemoNyuryoku_Dialog),
                BunsekiGenryoKakunin_Dialog: $.get(page.dialogs.urls.BunsekiGenryoKakunin_Dialog),
                SeizoKoteiDialog: $.get(page.dialogs.urls.SeizoKoteiDialog),
                GenryoIchiranDialog: $.get(page.dialogs.urls.GenryoIchiranDialog),
                //19075 - 2022/06/06 - ShohinKaihatsuSuppot Modify 2022FY: -st
                GenzairyoKakuninDialog: $.get(page.dialogs.urls.GenzairyoKakuninDialog)
                //-ed
            }).then(function (result) {

                //$("#dialog-container").append(result.successes.confirmDialog);
                //$("#dialog-container").append(result.successes.confirmDialog);
                //page.dialogs.confirmDialog = ConfirmDialog;

                $("#dialog-container").append(result.successes.IroShitei_Dialog);
                page.dialogs.IroShitei_Dialog = _602_IroShitei_Dialog;
                page.dialogs.IroShitei_Dialog.initialize();
                page.dialogs.IroShitei_Dialog.setReturnColor = page.detail.setColor;
                page.dialogs.IroShitei_Dialog.isCheckHide = page.detail.checkHideIroShitei;

                $("#dialog-container").append(result.successes.ShisakuRetuTuikaDialog);
                page.dialogs.ShisakuRetuTuikaDialog = ShisakuRetuTuikaDialog;
                page.dialogs.ShisakuRetuTuikaDialog.initialize();
                page.dialogs.ShisakuRetuTuikaDialog.dataSelected = page.detail.addShisaku;
                page.dialogs.ShisakuRetuTuikaDialog.checkUndefi = page.detail.checkUndefi;

                $("#dialog-container").append(result.successes.TantoEigyoKensaku_Dialog);
                page.dialogs.TantoEigyoKensaku_Dialog = _605_TantoEigyoKensaku_Dialog;
                page.dialogs.TantoEigyoKensaku_Dialog.initialize();
                page.dialogs.TantoEigyoKensaku_Dialog.dataSelected = page.detail.tantoshaEigyoSelect;

                $("#dialog-container").append(result.successes.MemoNyuryoku_Dialog);
                page.dialogs.MemoNyuryoku_Dialog = _601_MemoNyuryoku_Dialog;
                page.dialogs.MemoNyuryoku_Dialog.initialize();

                $("#dialog-container").append(result.successes.BunsekiGenryoKakunin_Dialog);
                page.dialogs.BunsekiGenryoKakunin_Dialog = _604_BunsekiGenryoKakuninDataKakunin;
                page.dialogs.BunsekiGenryoKakunin_Dialog.initialize();

                $("#dialog-container").append(result.successes.SeizoKoteiDialog);
                page.dialogs.SeizoKoteiDialog = SeizoKoteiDialog;
                page.dialogs.SeizoKoteiDialog.initialize();
                page.dialogs.SeizoKoteiDialog.dataSelected = page.detail.SeizoKoteiKoshin;

                $("#dialog-container").append(result.successes.GenryoIchiranDialog);
                page.dialogs.GenryoIchiranDialog = GenryoIchiranDialog;
                page.dialogs.GenryoIchiranDialog.initialize();
                page.dialogs.GenryoIchiranDialog.checkRecalcu = page.detail.tabs.haigohyo_tab.checkRecalcu;
                page.dialogs.GenryoIchiranDialog.dataSelected = page.detail.tabs.haigohyo_tab.dataSelected;

                // 19075 - ShohinKaihatsuSuppot Modify 2022FY: -st
                $("#dialog-container").append(result.successes.GenzairyoKakuninDialog);
                page.dialogs.GenzairyoKakuninDialog = _606_GenzairyoKakunin_Dialog;
                page.dialogs.GenzairyoKakuninDialog.initialize();
                page.dialogs.GenzairyoKakuninDialog.dataSelected = page.detail.tabs.haigohyo_tab.dataGenzairyoKakuninSelected;
                // -ed
            });
        }

        /**
        * 原料一覧から配合表タップに原料を表示する。
        */
        page.detail.tabs.haigohyo_tab.dataSelected = function (data) {

            var isRecacu = page.detail.tabs.haigohyo_tab.detail.dataGenryoSelected(data, page.dialogs.GenryoIchiranDialog.values.dataHin);

            if (isRecacu) {
                page.dialogs.GenryoIchiranDialog.values.isSelected = isRecacu;
            }
        }

        /**
        * 19075 - 2022/06/06 - ShohinKaihatsuSuppot Modify 2022FY: -st
        * Get data from 原料費確認。
        */
        page.detail.tabs.haigohyo_tab.dataGenzairyoKakuninSelected = function (data) {
            page.detail.tabs.haigohyo_tab.detail.dataGenzairyoKakuninSelected(data);
            //delete page.dialogs.GenzairyoKakuninDialog.dataSelected;
        }

        /**
        * インプットのマークを削除する。
        */
        page.detail.tabs.haigohyo_tab.checkRecalcu = function () {
            //check recalculate
            if (page.dialogs.GenryoIchiranDialog.values.isSelected) {
                page.dialogs.GenryoIchiranDialog.values.isSelected = false;
                page.detail.recalculation("tokuseichi_tab", "EventHaigo");
            }
        }

        /**
        * 担当営業検索
        */
        page.detail.tantoshaEigyoSelect = function (data) {
            page.detail.tabs.kihonjohyo_tab.detail.tantoshaEigyoSelect(data);
        };

        /**
        * 担当営業検索
        */
        page.detail.showTantoshaIchiranEigyoDialog = function (e) {
            page.dialogs.TantoEigyoKensaku_Dialog.element.modal("show");
        };

        /**
        * data new from dialog copy 
        */
        page.detail.addShisaku = function (data) {
            page.detail.tabs.haigohyo_tab.detail.beforAddNewTrialManual(data).then(function (addNewColCopy) {

                //試作列追加の新seq_shisakuをこしんする
                if (addNewColCopy != null && addNewColCopy != undefined) {
                    setTimeout(function () {
                        page.dialogs.ShisakuRetuTuikaDialog.values.dataMaster["maxCol"] = addNewColCopy;
                    }, 100)
                };
            }).always(function () {
                App.ui.loading.close();
            })
        }

        /**
        *  フォーカスアウトコラムをチェック。
        */
        page.detail.checkHideIroShitei = function () {
            page.detail.tabs.haigohyo_tab.detail.checkHideIroShitei();
        };

        /**
        * Show S_Memo Dialog
        */
        page.detail.setColor = function (color) {
            page.detail.tabs.haigohyo_tab.detail.setColor(color);
        };

        /**
        * Show S_Memo Dialog
        */
        page.detail.showMemoDialog = function (e) {
            if (page.values.modeDisp == App.settings.app.m_shisaku_data.etsuran || page.values.modeDisp == App.settings.app.m_shisaku_data.copy) {
                return;
            };

            var memo = $(e.target).val();

            page.dialogs.MemoNyuryoku_Dialog.values.memo = memo;

            page.dialogs.MemoNyuryoku_Dialog.element.modal("show");

            page.dialogs.MemoNyuryoku_Dialog.dataSelected = function (data) {
                $(e.target).val(data).change();
                delete page.dialogs.MemoNyuryoku_Dialog.dataSelected
            }
        };

        /**
        * Show S_SeizhoKouteiDialog (製造工程)
        */
        page.detail.showSeizhoKouteiDialog = function (e) {
            var target = $(e.target);
            if (target.prop("disabled")) {
                return;
            }

            page.dialogs.SeizoKoteiDialog.header.values.modeDisp = page.values.modeDisp;

            HaigoHyo_Tab.detail.trialManualCheked().then(function (results) {

                page.dialogs.SeizoKoteiDialog.header.values.isComplete = true;

                page.dialogs.SeizoKoteiDialog.header.values.seq_shisaku = results.seq_shisaku;
                page.dialogs.SeizoKoteiDialog.header.values.no_chui = (results.entity["no_chui"] != "" && results.entity["no_chui"]) ? results.entity["no_chui"] : null;
                page.dialogs.SeizoKoteiDialog.header.values.entity = results.entity;
                page.dialogs.SeizoKoteiDialog.header.values.shisakuHin = page.header.getData();
                page.dialogs.SeizoKoteiDialog.header.values.dataChui = results.dataChui;
                page.dialogs.SeizoKoteiDialog.element.modal("show");
            })
        };

        /**
        * Show 製造工程
        */
        page.detail.SeizoKoteiKoshin = function (data) {

            //フラグ変更画面
            page.values.isChange = true;

            var element = page.header.element,
                id = element.attr("data-key"),
                entity = page.header.data.entry(id);

            //オリジナル常に表示が変更しました。
            if (SeizoKoteiDialog.values.flg_chui_org != undefined) {
                entity["flg_chui"] = data["flg_chui"] ? data["cb_chui"] : App.settings.app.flg_chui.other;
            }

            if (data["mode"] == "addNew") {
                //注意事項NO
                page.values.masterData.no_chui_max = Number(page.values.masterData.no_chui_max) + 1;
                HaigoHyo_Tab.detail.addNewChui(data);
            } else {
                if (data["cb_chui"] == App.settings.app.flg_chui.chuijiko_hyoji) {

                    HaigoHyo_Tab.detail.updateChui(data);
                } else {
                    entity["memo_shisaku"] = data["chuijiko"];
                }
            }

            page.header.data.update(entity);

            HaigoHyo_Tab.detail.trialManualCheked().then(function (results) {
                page.dialogs.SeizoKoteiDialog.header.values.seq_shisaku = results.seq_shisaku;
                //page.dialogs.SeizoKoteiDialog.header.values.no_chui = (results.entity["no_chui"] != "" && results.entity["no_chui"]) ? results.entity["no_chui"] : null;
                page.dialogs.SeizoKoteiDialog.header.values.entity = results.entity;
                page.dialogs.SeizoKoteiDialog.header.values.shisakuHin = page.header.getData();
                page.dialogs.SeizoKoteiDialog.header.values.dataChui = results.dataChui;

                App.ui.loading.close();
                page.dialogs.confirmDialog.confirm({
                    text: data["mode"] == "addNew" ? App.messages.app.AP0080 : App.messages.app.AP0081,
                    hideCancel: true,
                    multiModal: true
                }).always(function () {
                    //オリジナル常に表示
                    SeizoKoteiDialog.values.flg_chui_org = undefined;

                    if (data["mode"] == "addNew") {
                        SeizoKoteiDialog.header.values.no_chui = page.values.masterData.no_chui_max;
                        page.dialogs.SeizoKoteiDialog.afterKoshin();
                    }
                    setTimeout(function () {
                        page.dialogs.SeizoKoteiDialog.header.values.isComplete = true;
                    }, 100)
                })
            }).fail(function () {
                setTimeout(function () {
                    page.dialogs.SeizoKoteiDialog.header.values.isComplete = true;
                }, 100)
            })
        };

        /**
        * Show _602_IroShitei_Dialog (色指定)
        */
        page.detail.showIroSettingDialog = function (e, isCheckHide) {
            //var target = $(e.target);

            //if (target.prop("disabled") || target.attr("readonly")) {
            //    return;
            //}

            //var checkInput = target.find("input");
            //if (checkInput.length) {
            //    if (target.find(checkInput[0]).prop("disabled") || target.find(checkInput[0]).attr("readonly")) {
            //        return;
            //    }
            //}
            page.dialogs.IroShitei_Dialog.options.isCheckHide = isCheckHide;
            page.dialogs.IroShitei_Dialog.element.modal("show");
        };

        /**
         * 画面で処理の対象とするデータのロード処理を実行します。
         */
        page.loadData = function () {
            if (page.values.modeDisp == App.settings.app.m_shisaku_data.shinki) {
                page.header.bind({
                    cd_tani: null,//容量単位CD
                    nm_toroku: App.ui.page.user.Name,//登録者
                    nm_koshin: App.ui.page.user.Name,//更新者
                    pt_kotei: null,//工程パターン
                    no_shubetu: null,//種別番号
                    cd_group: App.ui.page.user.cd_group,//グループCD
                    nm_group: App.ui.page.user.nm_group,//グループCD
                    cd_team: App.ui.page.user.cd_team,//チーム名
                    nm_team: App.ui.page.user.nm_team,//チーム名
                    dt_toroku: new Date(),//$.datepicker.formatDate("yy-mm-ddTHH:mmZ", new Date()),
                    kbn_haishi: 0,//廃止区
                    seq_shisaku: 0,//試作SEQ
                    flg_chui: 0,
                    //cd_hanseki: App.ui.page.user.cd_kaisha,//販責会社
                    //cd_kaisha: App.ui.page.user.cd_kaisha,//工場　担当会社
                    //cd_kojo: App.ui.page.user.cd_busho,//工場　担当工場,
                    keta_shosu: null,
                    //2019-12-17 : Request #16147 : 使用中（他の人が開いている）の場合、初期表示時に「他の方が開いているため、参照モードで開きます」のメッセージを通知し、参照モードで開く。
                    kbn_haita: App.ui.page.user.EmployeeCD
                }, true);

                return App.async.success();
            }

            //TODO: 画面内の処理の対象となるデータを取得し、画面にバインドする処理を記述します。
            var paraSearch = {
                cd_shain: page.values.cd_shain,
                nen: page.values.nen,
                no_oi: page.values.no_oi
            }
            return $.ajax(App.ajax.webapi.get(page.header.urls.shisakuhin, paraSearch)).then(function (result) {
                page.header.bind(result);
            })
        };

        /**
        * check pointlength for string。
        */
        page.stringPointlength = function (value, param) {
            if (App.isUndef(value) || App.isNull(value) || (App.isStr(value) && value.length === 0) || value.toString().indexOf("NaN") > -1) {
                return true;
            }

            var afterPoint = param.after,
                maxlength = param.max;

            //文字列がnullの時はtrueを返す
            value = App.isNum(value) ? value + "" : value;
            //カンマがあったら削除
            value = value.toString();
            value = value.replace(/,/g, "").replace("-", "");

            var isPoint = false;
            if (afterPoint > 0) {
                isPoint = true;
            }
            if (!App.isNumeric(value)) {
                return false;
            }
            afterPoint = parseFloat(afterPoint);

            //小数点以下の数をチェック
            var point = value.indexOf("."),
                after, before;

            if (point >= 0) {
                after = value.substring((point + 1));
                if (after.length > afterPoint) {
                    return false;
                }
            }
            //整数部分のチェック
            if (value.length > maxlength) {
                return false;
            }

            return true;
        }

        page.numPointlength = function (value, param) {

            if (App.isUndef(value) || App.isNull(value) || (App.isStr(value) && value.length === 0)) {
                return true;
            }

            var beforePoint = param[0];
            var afterPoint = param[1];
            var minus = param[2];
            //文字列がnullの時はtrueを返す
            value = App.isNum(value) ? value + "" : value;
            //カンマがあったら削除
            value = value.toString();
            value = value.replace(/,/g, "").replace("-", "");

            var isPoint = false;
            if (afterPoint > 0) {
                isPoint = true;
            }
            if (!App.isNumeric(value)) {
                return false;
            }
            afterPoint = parseFloat(afterPoint);
            beforePoint = parseFloat(beforePoint);

            //小数点以下の数をチェック
            var point = value.indexOf("."),
                after, before;
            if (point >= 0) {
                after = value.substring((point + 1));
                if (after.length > afterPoint) {
                    return false;
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
                return false;
            }

            return true;
        };

        /**
         * 画面ヘッダーのバリデーションを定義します。
         */
        page.header.options.validations = {
            //TODO: 画面ヘッダーのバリデーションの定義を記述します。
            nm_hin: {
                rules: {
                    required: true,
                    maxbytelength: 100,
                    check_charactor_range: true
                },
                options: {
                    name: "品名",
                    byte: 50
                },
                messages: {
                    required: App.messages.base.required,
                    maxbytelength: App.messages.base.maxbytelength,
                    check_charactor_range: App.messages.app.AP0154.replace("{param}", "; / ? : @ & = + $ ,")
                }
            },
            no_irai: {
                rules: {
                    maxlength: 8,
                    haneisukigo: true,
                    check_single_kotations: true
                },
                options: {
                    name: "依頼番号IR@"
                },
                messages: {
                    maxlength: App.messages.base.maxlength,
                    haneisukigo: App.messages.base.haneisukigo,
                    check_single_kotations: App.messages.app.AP0153
                }
            },
            no_shubetu: {
                rules: {
                    check_required: function (value, param, otps, done) {
                        if (page.values.modeDisp != App.settings.app.m_shisaku_data.copy) {
                            return done(true);
                        }

                        if (!value || App.isUndefOrNull(value)) {

                            return done(false);
                        }

                        if (!$.trim(value).length) {

                            return done(false);
                        }

                        return done(true);
                    }
                },
                options: {
                    name: "種別番号"
                },
                messages: {
                    check_required: App.str.format(App.messages.app.AP0028, { strName: "種別番号" })
                }
            }
        };

        /**
        * 画面ヘッダーの初期化処理を行います。
        */
        page.header.initialize = function () {

            var element = $(".header");
            page.header.validator = element.validation(page.createValidator(page.header.options.validations));
            element.on("change", ":input", page.header.change);
            page.header.element = element;

            //TODO: 画面ヘッダーの初期化処理をここに記述します。
            //TODO: 画面ヘッダーで利用するコントロールのイベントの紐づけ処理をここに記述します。
            element.find(".swicth-serect").on("click", page.header.changeSerect);
        };

        /**
        * 画面ヘッダーへのデータバインド処理を行います。
        */
        page.header.bind = function (data, isNewData) {
            var element = page.header.element,
                dataSet = App.ui.page.dataSet();

            data = data ? data : {};

            page.header.data = dataSet;

            (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(data);
            page.header.element.form(page.header.options.bindOption).bind(data);

            if (isNewData) {

                //TODO: 新規データの場合の処理を記述します。
                //ドロップダウンなど初期値がある場合は、
                //DataSetに値を反映させるために change 関数を呼び出します。
                //element.findP("cd_shiharai").change();
            } else {
                //工程パターン 
                page.detail.element.findP("pt_kotei").val(data.pt_kotei);
            }

            //バリデーションを実行します。
            //page.header.validator.validate({
            //    state: {
            //        suppressMessage: true
            //    }
            //});

            //define value for KihonJohyo tab
            page.values.dataBindKihonJohyo = $.extend(true, {}, data);
            //容量（数値入力）（※）
            page.values.dataTaniTokuseichi = data.cd_tani;
            //工程パターン 
            page.values.dataPatanTokuseichi = data.pt_kotei;
            //小数指定
            page.values.keta_shosu = data.keta_shosu != null ? data.keta_shosu : 0;

            //シークレット
            if (data.flg_secret) {
                element.findP("flg_secret").text("ON").addClass("serect-color");
            }
            else {
                element.findP("flg_secret").text("OFF").removeClass("serect-color");
            }
        };

        /**
        * 画面ヘッダーにデータを設定する際のオプションを定義します。
        */
        page.header.options.bindOption = {
            appliers: {
                dt_toroku: function (value, element) {
                    element.text(App.data.getDateString(new Date(value)));
                    return true;
                },
                dt_koshin: function (value, element) {
                    element.text(App.data.getDateString(new Date(value)));
                    return true;
                },
            }
        };

        /**
        * シークレット設定 ON || OFF
        */
        page.header.changeSerect = function () {
            var element = page.header.element,
                id = element.attr("data-key"),
                target = element.findP("flg_secret"),
                entity = page.header.data.entry(id)

            if (target.text() === "OFF") {
                target.text("ON").addClass("serect-color");
                entity["flg_secret"] = true;
            }
            else {
                target.text("OFF").removeClass("serect-color");
                entity["flg_secret"] = false;
            }

            //フラグ変更画面
            page.values.isChange = true;

            page.header.data.update(entity);
        }

        /**
         * 画面ヘッダーにある入力項目の変更イベントの処理を行います。
         */
        page.header.change = function (e) {
            var element = page.header.element,
                target = $(e.target),
                id = element.attr("data-key"),
                property = target.attr("data-prop"),
                entity = page.header.data.entry(id),
                data = element.form().data();

            page.values.isChangeRunning[property] = true;

            element.validation().validate({
                targets: target
            }).then(function () {
                entity[property] = data[property];

                //廃止
                if (property == "kbn_haishi") {
                    entity[property] = target[0].checked;
                }

                page.header.data.update(entity);
            }).always(function () {
                delete page.values.isChangeRunning[property];

                //フラグ変更画面
                page.values.isChange = true;
            });
        };

        /**
        * 削除したい試作列の試作SEQが試算試作品の試作SEQと同じかどうかをチェックする。同じ場合は試算試作品の試作SEQ＝０になる。
        */
        page.header.checkDelShisakuEndCopy = function (seq_shisaku_del) {
            var element = page.header.element,
                id = element.attr("data-key"),
                entity = page.header.data.entry(id);

            if (entity.seq_shisaku == seq_shisaku_del) {
                entity.seq_shisaku = 0;
                page.header.data.update(entity);
            }
        }

        /**
        * 試作品テーブルデータを取りする。
        */
        page.header.getData = function () {
            var element = page.header.element,
               id = element.attr("data-key"),
               entity = page.header.data.entry(id);

            return $.extend(true, {}, entity);
        }

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
        //TODO-END: 以下の page.header の各宣言は画面仕様ごとにことなるため、不要の場合は削除してください。

        /**
         * 画面明細のバリデーションを定義します。
         */
        //page.detail.options.validations = {
        //    //TODO: 画面明細のバリデーションの定義を記述します。
        //};

        /**
        * event change 工程パターン 。
        */
        page.detail.changeKoteiPatan = function (e) {
            var element = page.header.element,
                target = $(e.target),
                id = element.attr("data-key"),
                entity = page.header.data.entry(id),
                data = target.val(),
                flgAP0025 = false;

            var updateAcces = function () {
                entity["pt_kotei"] = data;
                page.header.data.update(entity);

                page.detail.tabs.haigohyo_tab.values.pt_kotei = data;
                page.detail.tabs.tokuseichi_tab.values.pt_kotei = data;

                page.values.dataPatanTokuseichi = data;

                //配合表．工程属性コンボボックスの選択値を初期化	
                page.detail.tabs.haigohyo_tab.changeKoteiPatan(data, true);

                //基本情報．容量、特性値．製品比重、水相比重、原価試算．充填量水相、油相を設定
                //基本情報タブ					
                //特性値
                var cd_tani = page.detail.tabs.kihonjohyo_tab.changeKoteiPatan(data, true);

                entity["cd_tani"] = cd_tani;
                page.header.data.update(entity);

                page.detail.tabs.tokuseichi_tab.changeKoteiPatan(data, cd_tani, "refresh");

                //原価試算
                page.detail.tabs.genkashisan_tab.changeKoteiPatan(data, cd_tani);
                //原価試算タブ．原料費の再計算を行う
                //if (App.isFunc(HaigoHyo_Tab.recalculation)) {
                //・原価試算タブ．原料費の再計算を行う
                page.detail.recalculation("tokuseichi_tab", "工程パターン");
                //}

                //フラグ変更画面
                page.values.isChange = true;
            }

            //変更前と変更後が同じ値の場合は処理しない				
            //変更時に下記処理を行う				
            if (data != page.values.dataPatanTokuseichi) {
                var options = {
                    text: ""
                }

                //■「空白」から
                if (!page.values.dataPatanTokuseichi) {
                    options.text = App.str.format(App.messages.app.AP0024, { strName: "「合計仕上重量」と 「特性値」" });
                } else if (!data) {
                    options.text = App.str.format(App.messages.app.AP0024, { strName: "「合計仕上重量」、「特性値」、「工程」、「単位」" });
                } else {
                    flgAP0025 = true;
                    options.text = App.str.format(App.messages.app.AP0024, { strName: "「合計仕上重量」と 「特性値」" });
                }

                page.dialogs.confirmDialog.confirm(options).then(function () {

                    //AP0025を表示し、次処理を行う
                    if (flgAP0025) {
                        App.ui.page.notifyInfo.message(App.messages.app.AP0025).remove();
                        App.ui.page.notifyInfo.message(App.messages.app.AP0025).show();
                    }

                    //配合表①の「合計仕上重量」をクリアする
                    page.detail.tabs.haigohyo_tab.detail.resetAllJuryoShisaku().then(function () {
                        updateAcces();
                    })
                }).fail(function () {
                    target.val(page.values.dataPatanTokuseichi);
                })
            }
        }

        /**
         * 画面明細の初期化処理を行います。
         */
        page.detail.initialize = function () {

            var element = $(".detail");
            page.detail.element = element;

            element.on("click", "#seizo-kotei", page.detail.showSeizhoKouteiDialog);
            element.findP("pt_kotei").on("change", page.detail.changeKoteiPatan);

            var elementCm = page.commands.element;

            //配合表
            element.find("#haigohyo_tab").click(function () {

                //control display button command area
                elementCm.find(".command-button").hide();
                elementCm.find(".command-haigohyo-tab").show();

                elementCm.find(".command-haigohyo-tab-edit").prop("disabled", true).show();

                if (page.values.modeDisp == App.settings.app.m_shisaku_data.shosai) {
                    elementCm.find(".command-haigohyo-tab-edit").prop("disabled", false);
                }

                element.find("#seizo-kotei").show();

                //2020-02-04 START : Bug #16491 : 全コピー・特徴コピーについて
                //閲覧モードで開いたときに全コピー・閲覧モードボタン・印刷FGを活性にする。
                if (page.values.modeDisp == App.settings.app.m_shisaku_data.etsuran) {
                    $("#Tokuchokopi, #Zenkopi, #close").prop('disabled', false);
                }
                //2020-02-04 END: Bug #16491 : 全コピー・特徴コピーについて

                setTimeout(function () {
                    $(window).resize();
                }, 1)
            });

            //特性値
            element.find("#tokuseichi_tab").click(function () {

                //control display button command area
                elementCm.find(".command-button").hide();
                elementCm.find(".command-tokuseichi-tab").show();

                //分析値の変更確認
                elementCm.find("#HenkoKakunin").prop('disabled', false);

                element.find("#seizo-kotei").hide();

                setTimeout(function () {
                    $(window).resize();
                }, 1)
            });

            //基本情報
            element.find("#kihonjohyo_tab").click(function () {
                //control display button command area
                elementCm.find(".command-button").hide();
                elementCm.find(".command-kihonjohyo-tab").show();

                element.find("#seizo-kotei").hide();

                setTimeout(function () {
                    $(window).resize();
                }, 1)
            });

            //原価試算
            element.find("#genkashisan_tab").click(function () {

                elementCm.find(".command-button").hide();
                elementCm.find(".command-genkashisan-tab").show();
                elementCm.find("#GenkaInsatsu").prop("disabled", page.values.modeDisp != App.settings.app.m_shisaku_data.shosai);

                element.find("#seizo-kotei").hide();

                //原価試算タブ押下
                if (!page.detail.afterSave) {
                    page.detail.tabs.haigohyo_tab.detail.checkBlankKotei();
                }

                setTimeout(function () {
                    $(window).resize();
                }, 1)
            });
        };

        /**
        * Change format when focus        
        */
        $(document).on("focusin", "[data-number-format-toFixed]", function (e) {
            var target = $(e.target);

            if (target.is('[readonly]') || target.is(':disabled')) {
                return
            }

            var value = target.val();

            if (value === "") {
                return;
            }
            value = $.trim(value).replace(/,/g, "");
            if (isNaN(value)) {
                return;
            }

            //value = value.toString();
            //split = value.split(".");

            //if (split.length == 1) {
            //    target.val(value);
            //    return;
            //}

            //if (Number(split[1]) > 0) {
            //    target.val(value);
            //    return;
            //}

            target.val(value);
            target.select();
        });

        //2019-10-16 : START : Bug #15481 0.5と値を入力する際、実際には0を省略して「.5」と入力します。
        //Only limit decimals
        $(document).on("keypress", ".limit-input-float-new", function (event) {
            if (event.which != 45 && ((event.which != 46) && (event.which < 48 || event.which > 57))) {
                event.preventDefault();
            }
        });

        $(document).on("focusout", ".limit-input-float-new", function (e) {
            var target = $(e.target);

            if (target.attr("data-number-format-toFixed") == undefined) {
                value = target.val();
                if ($.trim(value) === ".") {
                    target.val("");
                }
            }
        })
        //2019-10-16 : END : Bug #15481 0.5と値を入力する際、実際には0を省略して「.5」と入力します。

        /**
        * Change format when focus out
        */
        $(document).on("focusout", "[data-number-format-toFixed]", function (e) {
            var target = $(e.target);

            if (target.is('[readonly]') || target.is(':disabled')) {
                return
            }

            var format = Number(target.attr("data-number-format-toFixed")),
                value = target.val();

            //2019-10-16 : START : Bug #15481 0.5と値を入力する際、実際には0を省略して「.5」と入力します。
            if ($.trim(value) === ".") {
                target.val("");
                return;
            }
            //2019-10-16 : END : Bug #15481 0.5と値を入力する際、実際には0を省略して「.5」と入力します。

            if (value === "") {
                return;
            }

            //2019-10-16 : START : Bug #15481 0.5と値を入力する際、実際には0を省略して「.5」と入力します。
            value = $.trim(value).replace(/,/g, "");
            value = value.indexOf(".") == 0 ? ("0" + value) : value;
            value = value.indexOf(".") == (value.length - 1) ? (value + "0") : value;
            value = Number(value);
            //2019-10-16 : END : Bug #15481 0.5と値を入力する際、実際には0を省略して「.5」と入力します。

            if (isNaN(value)) {
                return;
            }

            if (target.hasClass("number-kirisu")) {
                split = (value + "").split(".");
                //add new item when「.」not exit
                if (split.length == 1) {
                    split.push("00000");
                }
                //add new charactor
                split[1] = split[1] + "00000";
                split[1] = split[1].substr(0, format);
                if (!format) {
                    target.val(page.detail.formatNum(value));
                } else {
                    target.val(page.detail.formatNum(value) + "." + split[1]);
                }
                return;
            }

            if (format) {
                //value = (Math.round(value * 10000) / 10000);
                value = Math.round(new BigNumber(stringNumbers(value)).times("10000").toNumber());
                value = new BigNumber(stringNumbers(value)).div("10000").toNumber();
                value = value.toFixed(format);

                split = (value + "").split(".");
            } else {
                value = $.trim(target.val()).replace(/,/g, "");

                //2019-10-16 : START : Bug #15481 0.5と値を入力する際、実際には0を省略して「.5」と入力します。
                value = value.indexOf(".") == 0 ? ("0" + value) : value;
                value = value.indexOf(".") == (value.length - 1) ? (value + "0") : value;
                //2019-10-16 : END : Bug #15481 0.5と値を入力する際、実際には0を省略して「.5」と入力します。

                split = value.split(".");

                if (target.hasClass("to-fixed")) {
                    if (split.length == 1) {
                        split.push("0000");
                        split[1] = (split[1] + "0000").substr(0, 2);
                    }
                }

                toFixedMin = target.attr("toFixedMin");
                if (toFixedMin && split.length == 1) {
                    split.push(App.common.getFullString(0, "000000".substr(0, Number(toFixedMin))));
                }
            }

            target.val(page.detail.formatNum(value) + ((split[1] != undefined && split[1] != "") ? ("." + split[1]) : ""));
        });

        page.detail.formatNum = function (value) {
            return App.num.format(Number(value), "#,0.####").toString().split('.')[0];
        }

        /**
        * check number
        */
        page.detail.checkUndefi = function (number, replace) {
            if (App.isUndefOrNull(number) || number == undefined || number == null || number == "" || number == "null" || number.toString().indexOf("NaN") > -1) {
                if (replace && replace["replace"] && number == 0) {
                    return replace["valReplace"];
                }
                return 0;
            }

            number = number.toString().replace(/,/g, "");

            if (!App.isNumeric(number)) {
                return 0;
            }

            return Number(number);
        }

        //2019-10-16 : START : Bug #15481 0.5と値を入力する際、実際には0を省略して「.5」と入力します。
        function valtarget(target) {
            var value = target.val();

            if (!target.hasClass("limit-input-float-new")) {
                return value;
            }

            if (value == "" || value == "." || value == undefined || value == null) {
                return "";
            }

            value = value.indexOf(".") == 0 ? ("0" + value) : value;
            value = value.indexOf(".") == (value.length - 1) ? (value + "0") : value;

            return value;
        };
        //2019-10-16 : END : Bug #15481 0.5と値を入力する際、実際には0を省略して「.5」と入力します。

        /**
        * number to string
        */
        function stringNumbers(number, unCheck) {

            if (unCheck) {
                return (number + "").replace(/,/g, "");
            }

            if (App.isUndefOrNull(number) || number == undefined || number == null || number == "" || number == "null" || number.toString().indexOf("NaN") > -1) {
                return "0";
            }

            return (number + "").replace(/,/g, "");
        }

        /**
        * 計算に使用する項目が未入力の場合計算対象外として頂けませんでしょうか。
        */
        function checkEvalBeforCalcu(checkEval) {
            var isResult = true;

            $.each(checkEval, function (index, number) {

                if (number == 0) {
                    return true;
                }

                if (App.isUndefOrNull(number) || number == undefined || number == null || number == "" || number == "null" || number.toString().indexOf("NaN") > -1 || number.toString().indexOf("Infinity") > -1) {
                    isResult = false;
                    return isResult;
                }
            })

            return isResult;
        }

        /**
        * check is zero before format number
        */
        page.detail.beforFormatNumber = function (number, decimal, replace, ceil, floor) {
            var results = { forDisp: "", forData: null };

            if (number == 0) {
                results = { forDisp: App.common.fillTrailingZero(number, decimal), forData: App.common.fillTrailingZero(number, decimal) };
                return results;
            }

            if (number == null || number == undefined) {
                return results;
            }

            return page.detail.formatNumber(number, decimal, replace, ceil, floor);
        }

        /**
        *値表示時、小数桁数が桁になるようにゼロ埋め・切上げを行う
        */
        page.detail.formatNumber = function (number, decimal, replace, ceil, floor) {
            var result = {
                forDisp: replace != undefined ? replace : 0,
                forData: replace != undefined ? replace : 0
            }, split;

            if (App.isUndefOrNull(number) || number == undefined || number == null || number == "" || number == "null" || number.toString().indexOf("NaN") > -1 || number.toString().indexOf("Infinity") > -1) {
                return result = {
                    forDisp: replace != undefined ? replace : "",
                    forData: replace != undefined ? replace : ""
                }
            }

            number = number.toString().replace(/,/g, "");

            if (!App.isNumeric(number)) {
                result = {
                    forDisp: number,
                    forData: number
                }
                return result;
            }

            if (decimal != null) {
                if (floor) {
                    //number = Math.floor(number * floor) / floor;
                    number = Math.floor(new BigNumber(stringNumbers(number)).times(floor).toNumber());
                    number = new BigNumber(stringNumbers(number)).div(stringNumbers(floor)).toNumber();
                } else if (ceil) {
                    var abs = 1;
                    if (number < 0) {
                        abs = -1;
                    }
                    //number = Math.ceil((number * abs) * ceil) / ceil;
                    //number = number * abs;
                    number = new BigNumber(stringNumbers(number)).times(stringNumbers(abs)).toNumber();
                    number = new BigNumber(stringNumbers(number)).times(stringNumbers(ceil)).toNumber();
                    number = new BigNumber(stringNumbers(Math.ceil(number))).div(stringNumbers(ceil)).toNumber();
                    number = new BigNumber(stringNumbers(number)).times(stringNumbers(abs)).toNumber();
                } else {
                    var denum = 1;
                    switch (Number(decimal)) {
                        case 1:
                            //number = (Math.round(Number(number) * 10) / 10);        
                            denum = 10;
                            break;
                        case 2:
                            //number = (Math.round(Number(number) * 100) / 100);
                            denum = 100;
                            break;
                        case 3:
                            //number = (Math.round(Number(number) * 1000) / 1000);
                            denum = 1000;
                            break;
                        case 4:
                            //number = (Math.round(Number(number) * 10000) / 10000);
                            denum = 10000;
                            break;
                        default:
                            //number = (Math.round(Number(number) * 100000) / 100000);
                            denum = 100000;
                            break;
                    }
                    denum = denum + "";
                    //number = (Math.round(Number(number) * denum) / denum);
                    number = Math.round(new BigNumber(stringNumbers(Number(number))).times(denum).toNumber());
                    number = new BigNumber(stringNumbers(number)).div(denum).toNumber();
                }
                number = number.toFixed(Number(decimal));
            }

            split = (number + "").split(".");

            result = {
                forDisp: page.detail.formatNum(number) + (split[1] != undefined ? ("." + split[1]) : ""),
                forData: Number(number.replace(/,/g, ""))
            }
            return result;
        };

        /**
         * 。
         */
        page.detail.stringNumber = function (value, isReturn) {
            var number = value;

            if (number == 0) {
                return number;
            }

            if (App.isUndefOrNull(number) || number == undefined || number == null || number == "" || number == "null" || number.toString().indexOf("NaN") > -1 || number.toString().indexOf("Infinity") > -1) {
                return "";
            }

            number = number.toString().replace(/,/g, "");
            var split = number.split("."),
                result = page.detail.formatNum(number) + ((split[1] != undefined && split[1] != "") ? ("." + split[1]) : "");

            if (isReturn && (page.detail.formatNum(result) == undefined || result.toString().indexOf("NaN") >= 0)) {
                return value;
            }

            return result;
        };

        /**
         * 画面明細の各行にデータを設定する際のオプションを定義します。
         */
        //page.detail.options.bindOption = {
        //    appliers: {
        //    }
        //};

        /**
         * 画面明細へのデータバインド処理を行います。
         */
        //page.detail.bind = function (data, isNewData) {
        //    var element = page.detail.element,
        //        i, l, item,
        //        dataSet = App.ui.page.dataSet();

        //    page.detail.data = dataSet;

        //    page.detail.dataTable.dataTable("clear");

        //    page.detail.dataTable.dataTable("addRows", data, function (row, item) {
        //        (isNewData ? dataSet.add : dataSet.attach).bind(dataSet)(item);
        //        row.form(page.detail.options.bindOption).bind(item);
        //        //TODO: 画面明細へのデータバインド処理をここに記述します。

        //        return row;
        //    }, true);


        //    //バリデーションを実行します。
        //    page.detail.validateList(true);

        //    //TODO:合計計算用の処理です。不要な場合は削除してください。
        //    page.detail.calculate();
        //};

        /**
         * 画面明細の一覧の行が選択された時の処理を行います。
         */
        //page.detail.select = function (e, row) {
        //    //TODO: 単一行を作成する場合は、下記２行を利用します。
        //    $($(row.element[page.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab.selected")).removeClass("selected").addClass("unselected");
        //    $(row.element[page.detail.fixedColumnIndex].querySelectorAll(".select-tab")).removeClass("unselected").addClass("selected");
        //    //TODO: 多段行を作成する場合は、下記２行を有効にし、上記の２行は削除します。
        //    //$($(row.element[page.detail.fixedColumnIndex]).closest("table")[0].querySelectorAll(".select-tab-2lines.selected")).removeClass("selected").addClass("unselected");
        //    //$(row.element[page.detail.fixedColumnIndex].querySelectorAll(".select-tab-2lines")).removeClass("unselected").addClass("selected");

        //    //TODO: 選択行全体に背景色を付ける場合は以下のコメントアウトを解除します。
        //    //if (!App.isUndefOrNull(page.detail.selectedRow)) {
        //    //    page.detail.selectedRow.element.find(".selected-row").removeClass("selected-row");
        //    //}
        //    //row.element.find("tr").addClass("selected-row");
        //    //page.detail.selectedRow = row;
        //};

        /**
         * 画面明細の一覧の入力項目の変更イベントの処理を行います。
         */
        //page.detail.change = function (e, row) {
        //    var target = $(e.target),
        //        id = row.element.attr("data-key"),
        //        property = target.attr("data-prop"),
        //        entity = page.detail.data.entry(id),
        //        options = {
        //            filter: page.detail.validationFilter
        //        };

        //    page.values.isChangeRunning[property] = true;

        //    page.detail.executeValidation(target, row)
        //    .then(function () {
        //        entity[property] = row.element.form().data()[property];
        //        page.detail.data.update(entity);

        //        //入力行の他の項目のバリデーション（必須チェック以外）を実施します
        //        page.detail.executeValidation(row.element.find(":input"), row, options);
        //    }).always(function () {
        //        delete page.values.isChangeRunning[property];
        //    });

        //    //TODO:合計計算用の処理です。不要な場合は削除してください。
        //    page.detail.calculate();
        //};

        /**
         * 画面明細の一覧に新規データを追加します。
         */
        //page.detail.addNewItem = function () {
        //    //TODO:新規データおよび初期値を設定する処理を記述します。
        //    var newData = {
        //        no_seq: page.values.no_seq
        //    };

        //    page.detail.data.add(newData);
        //    page.detail.dataTable.dataTable("addRow", function (tbody) {
        //        tbody.form().bind(newData);
        //        //TODO: 画面明細へのデータバインド処理をここに記述します。
        //        return tbody;
        //    }, true);
        //};

        /**
         * 画面明細の一覧で選択されている行とデータを削除します。
         */
        //page.detail.deleteItem = function (e) {
        //    var element = page.detail.element,
        //        //TODO: 単一行を作成する場合は、下記を利用します。
        //        selected = element.find(".datatable .select-tab.selected").closest("tbody");
        //    //TODO: 多段行を作成する場合は、以下を利用し、上記１行は削除します。
        //    //selected = element.find(".datatable .select-tab-2lines.selected").closest("tbody");

        //    if (!selected.length) {
        //        return;
        //    }

        //    page.detail.dataTable.dataTable("deleteRow", selected, function (row) {
        //        var id = row.attr("data-key"),
        //            newSelected;

        //        row.find(":input").each(function (i, elem) {
        //            App.ui.page.notifyAlert.remove(elem);
        //        });

        //        if (!App.isUndefOrNull(id)) {
        //            var entity = page.detail.data.entry(id);
        //            page.detail.data.remove(entity);
        //        }

        //        newSelected = row.next().not(".item-tmpl");
        //        if (!newSelected.length) {
        //            newSelected = row.prev().not(".item-tmpl");
        //        }
        //        if (newSelected.length) {
        //            for (var i = page.detail.fixedColumnIndex; i > -1; i--) {
        //                if ($(newSelected[i]).find(":focusable:first").length) {
        //                    $(newSelected[i]).find(":focusable:first").focus();
        //                    break;
        //                }
        //            }
        //        }
        //    });

        //    //TODO:合計計算用の処理です。不要な場合は削除してください。
        //    page.detail.calculate();
        //};

        <%--
        /**
         * 画面明細の一覧に対して、選択行の前に新規データを挿入します。
         */
        page.detail.insertNewItemBefore = function () {
            //TODO:新規データおよび初期値を設定する処理を記述します。
            var newData = {
                no_seq: page.values.no_seq
            };

            // 新規データを挿入（前）
            page.detail.insertRow(newData, true, false);
        };

        /**
         * 画面明細の一覧に対して、選択行の後に新規データを挿入します。
         */
        page.detail.insertNewItemAfter = function () {
            //TODO:新規データおよび初期値を設定する処理を記述します。
            var newData = {
                no_seq: page.values.no_seq
            };

            // 新規データを挿入（後）
            page.detail.insertRow(newData, true, true);
        };

        /**
        * 画面明細の一覧に、選択行の後に新しい行を挿入します。
        */
        page.detail.insertRow = function (newData, isFocus, isInsertAfter) {
            var element = page.detail.element,
                //TODO: 単一行を作成する場合は、下記を利用します。
                selected = element.find(".datatable .select-tab.selected").closest("tbody");
                //TODO: 多段行を作成する場合は、以下を利用し、上記１行は削除します。
                //selected = element.find(".datatable .select-tab-2lines.selected").closest("tbody");

            if (!selected.length) {
                // 選択行が無ければこれまでどおり最終行に行追加
                page.detail.addNewItem();
                return;
            }

            page.detail.data.add(newData);
            page.detail.dataTable.dataTable("insertRow", selected, isInsertAfter, function (tbody) {
                tbody.form().bind(newData);

                //TODO: 画面明細へのデータバインド処理をここに記述します。
                return tbody;
            }, isFocus);

        };
        --%>

        /**
         * 画面明細のバリデーションを実行します。
         */
        //page.detail.executeValidation = function (targets, row, options) {
        //    var defaultOptions = {
        //        targets: targets,
        //        state: {
        //            tbody: row,
        //            isGridValidation: true
        //        }
        //    };
        //    options = $.extend(true, {}, defaultOptions, options);

        //    return page.detail.validator.validate(options);
        //};

        /**
         * 画面明細のバリデーションフィルターを設定します。（必須チェックを行わない）
         */
        page.header.validationFilter = function (item, method, state, options) {
            return item !== "no_shubetu";
        };

        /**
         * 画面明細の一覧のバリデーションを実行します。
         */
        //page.detail.validateList = function (suppressMessage) {
        //    var validations = [],
        //        options = {
        //            state: {
        //                suppressMessage: suppressMessage
        //            }
        //        };

        //    page.detail.dataTable.dataTable("each", function (row) {
        //        validations.push(page.detail.executeValidation(row.element.find(":input"), row.element, options));
        //    });

        //    return App.async.all(validations);
        //};

        /**
         * 画面明細の支払単価と数量をもとにした合計金額を計算し、表示します。
         */
        //page.detail.calculate = function () {
        //    var items,
        //        total;

        //    if (page.detail.values.suppressCalculate) {
        //        return;
        //    }

        //    items = page.detail.data.findAll(function (item, entity) {
        //        return entity.state !== App.ui.page.dataSet.status.Deleted;
        //    });

        //    total = items.reduce(function (init, value) {
        //        return init;
        //    }, 0);

        //    //page.detail.element.find(".kei_shiire_kingaku").text(total);
        //};

        //TODO-END: 以下の page.detail の各宣言は画面仕様ごとにことなるため、不要の場合は削除してください。

        /**
         * jQuery イベントで、ページの読み込み処理を定義します。
         */
        $(function () {
            // ページの初期化処理を呼び出します。
            //2019-12-17 START : Request #16147 : 使用中（他の人が開いている）の場合、初期表示時に「他の方が開いているため、参照モードで開きます」のメッセージを通知し、参照モードで開く。
            //page.initialize();
            page.checkHaita();
            //2019-12-17 END : Request #16147 : 使用中（他の人が開いている）の場合、初期表示時に「他の方が開いているため、参照モードで開きます」のメッセージを通知し、参照モードで開く。
        });

        //2019-12-17 START : Request #16147 : 使用中（他の人が開いている）の場合、初期表示時に「他の方が開いているため、参照モードで開きます」のメッセージを通知し、参照モードで開く。
        /**
        *　画面を閉めるとき排他区分がnullになる。
        */
        $(window).bind("beforeunload", function (e) {

            //if (navigator.appName == "Microsoft Internet Explorer") {
            //    // Detects the browser close "X" button click
            //    if (event.clientX > document.body.clientWidth && event.clientY < 0 || event.altKey) {
            //        page.checkHaitaKubun(false);
            //    }
            //}
            //else {
            //    page.checkHaitaKubun(false);
            //}
        });
        //2019-12-17 END : Request #16147 : 使用中（他の人が開いている）の場合、初期表示時に「他の方が開いているため、参照モードで開きます」のメッセージを通知し、参照モードで開く。

    </script>
</asp:Content>

<%-- 2010-09-10 : START : Bug #15330 Add new class text-selectAll
************
************
2010-09-10 : END : Bug #15330 Add new class text-selectAll--%>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="content-wrap">
        <!--TODO: ヘッダーを定義するHTMLをここに記述します。-->

        <div class="header smaller">
            <div title="共通ヘッダー情報" class="part">
                <div class="row">
                    <div class="col-xs-6" style="padding: 0px;">
                        <div class="row">
                            <div class="control-label col-xs-2">
                                <label class="item-name">試作コード</label>
                            </div>
                            <div class="control col-xs-5">
                                <input type="tel" disabled="disabled" style="width: 124px;" data-prop="cd_shain" class="zen-copy-blank tokucho-copy-input-blank text-selectAll">
                                -
                                <input type="tel" disabled="disabled" style="width: 30px;" data-prop="nen" class="zen-copy-blank tokucho-copy-input-blank text-selectAll">
                                -
                                <input type="tel" disabled="disabled" style="width: 50px;" data-prop="no_oi" class="zen-copy-blank tokucho-copy-input-blank text-selectAll">
                            </div>
                            <div class="control-label col-xs-2">
                                <label style="width: 100px;">依頼番号IR@</label>
                            </div>
                            <div class="control col-xs-2">
                                <input type="text" data-prop="no_irai" class="zen-copy-blank tokucho-copy-input-blank text-selectAll">
                            </div>
                            <div class="control col-xs-1">
                            </div>
                        </div>
                        <div class="row">
                            <div class="control-label col-xs-2">
                                <label class="item-name required-label">品名</label><span class="required">*</span>
                            </div>
                            <div class="control col-xs-10">
                                <input type="text" data-prop="nm_hin" class="tokucho-copy-input-blank text-selectAll" />
                            </div>
                        </div>
                        <div class="row">
                            <div class="control-label col-xs-2">
                                <label class="item-name">製法No</label>
                            </div>
                            <div class="control col-xs-5">
                                <input type="tel" disabled="disabled" style="width: 50px;" data-prop="seiho_no_shain" class="zen-copy-blank tokucho-copy-input-blank text-selectAll">
                                -
                                <input type="tel" disabled="disabled" style="width: 60px;" data-prop="seiho_no_nen" class="zen-copy-blank tokucho-copy-input-blank text-selectAll">
                                -
                                <input type="tel" disabled="disabled" style="width: 30px;" data-prop="seiho_no_oi" class="zen-copy-blank tokucho-copy-input-blank text-selectAll">
                                -
                                <input type="tel" disabled="disabled" style="width: 50px;" data-prop="seiho_no" class="zen-copy-blank tokucho-copy-input-blank text-selectAll">
                            </div>
                            <div class="control-label col-xs-1">
                                <label class="item-name">廃止</label>
                            </div>
                            <div class="control col-xs-4">
                                <input type="checkbox" style="margin-top: 6px;" data-prop="kbn_haishi" value="1">
                            </div>
                        </div>
                    </div>

                    <div class="col-xs-6" style="padding: 0px;">
                        <div class="row">
                            <div class="control-label col-xs-3 fix-label-sm">
                                <label class="item-name lb">登録日</label>
                                <label class="item-name prop tokucho-copy-label-blank" data-prop="dt_toroku"></label>
                            </div>
                            <div class="control-label col-xs-4 fix-label-sm">
                                <label class="item-name lb">登録者</label>
                                <label class="item-name prop  tokucho-copy-label-blank overflow-ellipsis" data-prop="nm_toroku" style="float: right"></label>
                            </div>
                            <div class="control-label col-xs-5">
                                <label style="background-color: red; color: white; min-width: 110px; margin-left: 5px; font-size: 15px; padding: 1px" class="center">レベル１</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="control-label col-xs-3 fix-label-sm">
                                <label class="item-name lb">更新日</label>
                                <label class="item-name prop tokucho-copy-label-blank" data-prop="dt_koshin"></label>
                            </div>
                            <div class="control-label col-xs-4 fix-label-sm">
                                <label class="item-name lb">更新者</label>
                                <label class="item-name prop tokucho-copy-label-blank overflow-ellipsis" data-prop="nm_koshin" style="float: right"></label>
                            </div>
                            <div class="control-label col-xs-5 fix-label-xmd break-validate">
                                <label class="item-name required-label">種別番号</label><span class="required shubetu-required" style="display: none">*</span>
                                <select class="number prop seihokopi" data-prop="no_shubetu" style="width: 70px; margin-left: 70px"></select>
                                <%-- <label class="item-name lb required-label">種別番号</label><span class="required shubetu-required" style="display: none">*</span>
                                <select class="number prop seihokopi" data-prop="no_shubetu" style="width: 70px"></select>--%>
                            </div>
                        </div>
                        <div class="row">
                            <div class="control col-xs-3 fix-label-sm">
                                <%-- <label class="item-name lb">試算日</label>
                                <label class="item-name prop  data-app-format tokucho-copy-label-blank" data-prop="dt_shisan" data-app-format="date"></label>--%>
                            </div>
                            <div class="control col-xs-4 fix-label-sm">
                                <%--<label class="item-name lb" style="float: left">試算確定</label>--%>
                                <%-- <input data-prop="seq_shisaku_rireki" style="display: none!important" />--%>
                                <%--<label class="item-name prop tokucho-copy-label-blank overflow-ellipsis" data-prop="nm_sample" title=""></label>--%>
                            </div>
                            <div class="control-label col-xs-5 fix-label-xmd">
                                <div class="lb">
                                    <label style="padding-right: 0px">シークレット</label>
                                </div>
                                <div class="prop">
                                    <label class="min-zero serect-with tokucho-copy-label-blank" data-prop="flg_secret">OFF</label>
                                    <button type="button" style="padding: 3px 2px;" class="btn btn-sm btn-primary swicth-serect seihokopi">シークレット設定</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--TODO: 明細を定義するHTMLをここに記述します。-->
        <div class="detail">
            <div id="flexcolumn"></div>
            <div class="row toggle-display">
                <div class="col-xs-3" style="padding-left: 0px; height: 26px;">
                    <div class="tabbable" style="margin-top: 3px">
                        <ul class="nav nav-tabs" style="height: 15px; border-bottom: #ffffff">
                            <li class="styleLi" data-for="HaigoHyo_Tab"><a href="#HaigoHyo_Tab" data-toggle="tab" id="haigohyo_tab" tabindex="-1" style="padding: 2px 10px;">配合表</a></li>
                            <li class="styleLi" data-for="TokuseiChi_Tab"><a href="#TokuseiChi_Tab" data-toggle="tab" id="tokuseichi_tab" tabindex="-1" style="padding: 2px 10px;">特性値</a></li>
                            <li class="styleLi" data-for="KihonJohyo_Tab"><a href="#KihonJohyo_Tab" data-toggle="tab" id="kihonjohyo_tab" tabindex="-1" style="padding: 2px 10px;">基本情報</a></li>
                            <li class="styleLi" data-for="GenkaShisan_Tab"><a href="#GenkaShisan_Tab" data-toggle="tab" id="genkashisan_tab" tabindex="-1" style="padding: 2px 10px;">原価試算</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-xs-3 kotei-patan   " style="height: 23px;">
                    <label class="item-name">工程パターン</label>
                    <select class="number" id="pt_kotei" data-prop="pt_kotei" style="width: 60%; height: 23px;"></select>
                </div>
                <div class="col-xs-2" style="height: 23px;">
                    <label style="width: 203px;">（※）は原料費の計算に必要です</label>
                </div>
                <div class="col-xs-3" style="height: 23px;">
                </div>
                <div class="col-xs-1 " style="height: 23px;">
                    <button type="button" id="seizo-kotei" class="btn btn-sm btn-primary seihokopi" style="padding: 1px 5px; margin-left: 15px;">製造工程</button>
                </div>
            </div>

            <div class="tab-content" id="tab-container" style="border-top: 1px solid #ccc">
            </div>
        </div>
    </div>
</asp:Content>


<asp:Content ID="FooterContent" ContentPlaceHolderID="FooterContent" runat="server">
    <div class="command-left" id="command_left">
        <button type="button" id="ShisakuShutsuryoku" class="btn btn-sm btn-primary command-haigohyo-tab command-button">試作表出力</button>
        <button type="button" id="ShisakuSetsumeisho" class="btn btn-sm btn-primary command-haigohyo-tab command-button">ｻﾝﾌﾟﾙ説明書</button>
    </div>
    <div class="command toggle-display" id="command_right">
        <!--TODO: コマンドボタン（右寄せ配置）を定義するHTMLをここに記述します。-->
        <button type="button" id="GenzairyoKakunin" class="btn btn-sm btn-primary command-haigohyo-tab command-button seihokopi">コスト</button>
        <button type="button" id="GenryoIchiran" class="btn btn-sm btn-primary command-haigohyo-tab command-button seihokopi">原料一覧</button>
        <button type="button" id="GenryoBunseki" class="btn btn-sm btn-primary command-haigohyo-tab command-button seihokopi">原料分析</button>
        <button type="button" id="HenkoKakunin" class="btn btn-sm btn-primary command-tokuseichi-tab command-button seihokopi" style="min-width: 200px;">分析値の変更確認</button>
        <button type="button" id="JohoKoshin" class="btn btn-sm btn-primary command-tokuseichi-tab command-button seihokopi" style="min-width: 200px">分析マスタの最新情報に更新</button>
        <button type="button" id="GenkaInsatsu" class="btn btn-sm btn-primary command-genkashisan-tab command-button" disabled="disabled">原価試算表印刷</button>
        <label style="min-width: 50px"></label>
        <button type="button" id="Tokuchokopi" class="btn btn-sm btn-primary command-genkashisan-tab command-tokuseichi-tab command-haigohyo-tab-edit command-kihonjohyo-tab command-button" disabled="disabled">特徴コピー</button>
        <button type="button" id="Zenkopi" class="btn btn-sm btn-primary command-genkashisan-tab command-tokuseichi-tab command-haigohyo-tab-edit command-kihonjohyo-tab command-button" disabled="disabled">全コピー</button>
        <label style="min-width: 50px"></label>
        <button type="button" id="Save" class="btn btn-sm btn-primary seihokopi">保存</button>
        <button type="button" id="close" class="btn btn-sm btn-primary closebtn" >閉じる</button>
    </div>

</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>
