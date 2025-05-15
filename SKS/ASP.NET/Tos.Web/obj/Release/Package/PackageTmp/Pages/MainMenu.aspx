<%@ Page Language="C#" Title="002_メインメニュー" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="MainMenu.aspx.cs" Inherits="Tos.Web.Pages.MainMenu" %>
<%@ MasterType VirtualPath="~/Site.Master" %>

<asp:Content ID="IncludeContent" ContentPlaceHolderID="IncludeContent" runat="server">
    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/part.css") %>" type="text/css" />
    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/headerdetail.css") %>" type="text/css" />
</asp:Content>

<asp:Content ID="HeadContent" ContentPlaceHolderID="HeadContent" runat="server">

    <style type="text/css">
        body {
            display: none;
        }
    </style>

    <script type="text/javascript">

         /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("MainMenu", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {

            },
            values: {},
            urls: {
                //news: "../api/SampleNews"
            },
            menu: {
                options: {},
                values: {},
                urls: {
                }
            },
            news: {
                options: {},
                values: {},
                url: {
                    countkojyo: "../Services/ShisaQuickService.svc/ma_seiho_denso?$filter=cd_kaisha eq {0} and cd_kojyo eq {1} and flg_denso_taisho eq true and flg_denso_jyotai eq false"
                }
            },
            //Shohin Support Modify 2023 : Log -st 
            logs: {
                options: {},
                values: {},
                urls: {
                    urlGetLog: "../api/Common/GetLogCreen"
                }
            },
            // Shohin Support Modify 2023 : Log -ed
            dialogs: {
            },
            commands: {}
        });

        /**
        * 画面の初期化処理を行います。
        */
        page.initialize = function () {
            
            App.ui.loading.show();

            page.initializeControl();
            page.initializeControlEvent();

            page.news.initialize();
            page.menu.initialize();
            page.logs.initialize();
           
        };

        /**
        * 画面コントロールの初期化処理を行います。
        */
        page.initializeControl = function () {

            //TODO: 画面全体で利用するコントロールの初期化処理をここに記述します。

        };

        /**
        * コントロールへのイベントの紐づけを行います。
        */
        page.initializeControlEvent = function () {

            //TODO: 画面全体で利用するコントロールのイベントの紐づけ処理をここに記述します。
        };

        page.news.initialize = function () {
            var element = $(".news");
            page.news.element = element;
            page.news.loadData();
        };

        page.news.loadData = function () {

            App.ui.loading.show();
            App.ui.page.notifyAlert.clear();

            if (App.isUndefOrNull(App.ui.page.user.cd_busho)) {
                page.news.url.countkojyo = App.str.format("../Services/ShisaQuickService.svc/ma_seiho_denso?$filter=cd_kaisha eq {0} and flg_denso_taisho eq true and flg_denso_jyotai eq false", -1);
            }
            else {
                page.news.url.countkojyo = App.str.format(page.news.url.countkojyo, App.ui.page.user.cd_kaisha, App.ui.page.user.cd_busho)
            }

            return $.ajax(App.ajax.odata.get(page.news.url.countkojyo))
                .done(function (result) {
                    page.news.bind(result);
                    //count_kojyo
                }).fail(function (error) {
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

                }).always(function () {
                    App.ui.loading.close();

                });
        };

        page.news.options.bindOption = {
            //appliers: {
            //    dt_news: function (value, element) {
            //        value = App.data.parseJsonDate(value);
            //        element.text(App.date.format(value, "yyyy-MM-dd"));
            //        return true;
            //    }
            //}
        };

        page.news.bind = function (data) {

            var i, l, item, dataset, clone;

            data = (data.items) ? data.items : data;

            var element = page.news.element,
                count = element.findP("count_kojyo");
            count.text(data.value.length);
            if (App.ui.page.user.kbn_kengen_bunrui == App.settings.app.kbn_kengen_bunrui.seihoshien_kojo || App.ui.page.user.kbn_kengen_bunrui == App.settings.app.kbn_kengen_bunrui.shisaquick_seihoshien_kojo) {
                $(".news-count-kojyo").show();
            }
            
        };

        /**
         * Shohin Support Modify 2023 : Log
         * appliers data logs
         */
        page.logs.options.bindOption = {
            appliers: {
                count: function (value, element) {
                    if (!App.isUndefOrNull(value)) {
                        element.text(value + "件");
                    }
                    
                    return true;
                }
            }
        };

        /**
         * Shohin Support Modify 2023 : Log
         * binding data log
         */
        page.logs.bind = function (data) {
            var element = page.logs.element,
            table = element.find(".datatable"),
            items = data.Items ? data.Items : data,
            i, l, item, clone;

            table.find("tbody:visible").remove();

            for (i = 0, l = items.length; i < l; i++) {
                item = items[i];
                clone = table.find(".item-tmpl").clone();
                clone.form(page.logs.options.bindOption).bind(item);
                clone.appendTo(table).removeClass("item-tmpl").show();
            }

            if (items.length > 0) {
                page.logs.element.show();
            } else {
                page.logs.element.hide();
            }
        };

        /**
         * Shohin Support Modify 2023 : Log
         * initialize log table
         */
        page.logs.initialize = function () {
            var element = $(".logs");
            page.logs.element = element;
            page.logs.loadData();
        };

        /**
         * Shohin Support Modify 2023 : Log
         * load log data
         */
        page.logs.loadData = function () {
            App.ui.loading.show();
            App.ui.page.notifyAlert.clear();

            var params = {
                cd_kaisha: App.ui.page.user.cd_kaisha,
                id_user: App.ui.page.user.EmployeeCD
            };

            return $.ajax(App.ajax.webapi.get(page.logs.urls.urlGetLog, params))
                .done(function (result) {
                    page.logs.bind(result);

                }).fail(function (error) {
                    App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

                }).always(function () {
                    App.ui.loading.close();
                });
        };

        /**
        * 画面メニューの初期化処理を定義します。
        */
        page.menu.initialize = function () {

            var element = $(".menu"),
                baseUrl = '<%=ResolveUrl("~/") %>';

            page.menu.element = element;
            //TODO: 画面メニューの初期化処理をここに記述します。
            App.ui.treemenu.setup(App.ui.page.lang, App.ui.page.user.Roles, ".menu", baseUrl, App.ui.ddlmenu.settingsObj);

            //TODO: 画面メニューで利用するコントロールのイベントの紐づけ処理をここに記述します。

            $("#closetree").on("click", page.menu.closeTree);
            $("#opentree").on("click", page.menu.openTree);
        };

        page.menu.closeTree = function (e) {
            page.menu.element.find("i.icon-minus").click();
        }

        page.menu.openTree = function (e) {
            page.menu.element.find("i.icon-plus").click();
        }

        
        if (App.uri.queryStrings.directLink) {
            var link = sessionStorage.getItem("menuDirectLink");
            if (link) {
                sessionStorage.removeItem("menuDirectLink");
                page.isStopLoading = true;
                window.location.href= link;
            }
        }

        /**
        * jQuery イベントで、ページの読み込み処理を定義します。
        */
        $(function () {
            if (page.isStopLoading) {
                return;
            }
            $("body").show();
            // ページの初期化処理を呼び出します。
            page.initialize();
        });

    </script>

</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">

    <div class="content-wrap">
        <div class="row">
           <div class="col-sm-6">

               <!-- メニュー表示のコンテナー -->
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">メニュー
                            <span class="btn-group pull-right">
                                <button type="button" class="btn btn-xs btn-default" id="opentree">開く</button>
                                <button type="button" class="btn btn-xs btn-default" id="closetree">閉じる</button>
                            </span>
                        </h3>
                    </div>
                    <div class="panel-body">
                        <div class="menu">
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <!-- お知らせ表示のコンテナー -->
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">お知らせ</h3>
                    </div>
                    <div class="panel-body">
                        <div class="panel news-tmpl" style="display:none;">
                            <div class="panel-heading">
                                <h3 class="panel-title">
                                    <span data-prop="dt_news" class="data-app-format" data-app-format="date"></span>
                                    <span data-prop="nm_title"></span>
                                </h3>
                            </div>
                            <div class="panel-body">
                                <span data-prop="nm_content"></span>
                            </div>
                        </div>
                        <div class="news news-count-kojyo" style="display:none;">
                            配合未受信<span data-prop="count_kojyo"></span>件です。
                        </div>

                        <%--Shohin Support Modify 2023 : -st Log--%>
                        <br/ >
                        <div class="logs" style="display:none;">
                            <span>前日の操作実績</span>
                            <table class="datatable ellipsis">
                                <thead>
                                    <tr>
                                        <th style="width: 250px;">画面（操作）</th>
                                        <th style="width: 70px;">件数</th>
                                        <th style="background: white; border-color: white;"></th>
                                    </tr>
                                </thead>
                                <tbody class="item-tmpl" style="cursor: default; display: none;">
                                    <tr>
                                        <td class="text-left">
                                            <label data-prop="nm_literal"></label>
                                        </td>
                                        <td class="text-right">
                                            <label data-prop="count"></label>
                                        </td>
                                        <td style="background: white; border-color: white;"></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <%--Shohin Support Modify 2023 : -ed Log--%>
                    </div>
                </div>
            </div>
 
        </div>
    </div>

</asp:Content>

<asp:Content ID="FooterContent" ContentPlaceHolderID="FooterContent" runat="server">
    <div class="command-left">
    </div>

    <div class="command">
    </div>

</asp:Content>