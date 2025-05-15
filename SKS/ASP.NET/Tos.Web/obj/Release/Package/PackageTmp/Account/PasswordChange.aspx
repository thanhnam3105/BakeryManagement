<%@ Page Language="C#" Title="003_パスワード変更" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="PasswordChange.aspx.cs" Inherits="Tos.Web.Account.PasswordChange" %>
<%--/** 最終更新日 : 2018-01-25 **/--%>
<asp:Content ID="IncludeContent" ContentPlaceHolderID="IncludeContent" runat="server">
    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/headerdetail.css") %>" type="text/css" />

</asp:Content>
<asp:Content ID="HeadContent" ContentPlaceHolderID="HeadContent" runat="server">

    <style type="text/css">
        .container {
            margin-top: 50px;
            max-width: 600px;
        }

        label, input[type="text"], input[type="password"], .container span {
            font-size: 12pt;
        }

        .control-label, .control {
            height: 35px;   
        }
    </style>
    <script type="text/javascript">

        /**
         * ページのレイアウト構造に対応するオブジェクトを定義します。
         */
        var page = App.define("PasswordChange", {
            //TODO: ページのレイアウト構造に対応するオブジェクト定義を記述します。
            options: {},
            values: {},
            urls: {
                passwordchange: "../api/PasswordChange",
                checkpassword: "../api/PasswordChange/checkpassword"
            },
            password: {
                options: {},
                values: {},
                urls: {}
            },
            dialogs: {},
            commands: {},
            pageName: "PasswordChange"
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
         * すべてのバリデーションを実行します。
         */
        page.validateAll = function () {

            var validations = [];

            validations.push(page.password.validator.validate());

            //TODO: 画面内で定義されているバリデーションを実行する処理を記述します。
            return App.async.all(validations);
        };


        /**
        * 画面の初期化処理を行います。
        */
        page.initialize = function () {

            App.ui.loading.show();
            //TODO: URLパラメーターで渡された値を取得し、保持する処理を記述します。
            if (!App.isUndefOrNull(App.uri.queryStrings.disp_menu)) {
                page.values.disp_menu = App.uri.queryStrings.disp_menu;
            } else {
                page.values.disp_menu = false;
            }

            page.initializeControl();
            page.initializeControlEvent();

            page.password.initialize();

            page.loadDialog().always(function () {
                App.ui.loading.close();
            })

        };

        /**
         * 画面コントロールの初期化処理を行います。
         */
        page.initializeControl = function () {

            //TODO: 画面全体で利用するコントロールの初期化処理をここに記述します。
            if (!page.values.disp_menu) {
                $("#menu-toggle").hide();
                $(".navbar-brand").removeClass("cursor").off("click");
            }

        };

        /**
         * コントロールへのイベントの紐づけを行います。
         */
        page.initializeControlEvent = function () {

            //TODO: 画面全体で利用するコントロールのイベントの紐づけ処理をここに記述します。
        };

        /**
         * コンテンツ部の初期化処理を行います。
         */
        page.password.initialize = function () {

            var element = $(".container");
            page.password.validator = element.validation(page.createValidator(page.password.options.validations));
            page.password.element = element;

            //TODO: コンテンツ部の初期化処理をここに記述します。
            page.password.element.findP("kaisha").text(App.common.getFullCdKaisha(App.ui.page.user.cd_kaisha) + "：" + App.ui.page.user.nm_kaisha);
            page.password.element.findP("userId").text(App.common.getFullCdUser(App.ui.page.user.EmployeeCD) + "：" + App.ui.page.user.Name);

            //TODO: コンテンツ部で利用するコントロールのイベントの紐づけ処理をここに記述します。
            element.on("click", "#changepassword", page.password.changePassword);
            element.on("click", "#cancel", page.password.cancel);

            element.on("change", page.password.change);

            $("[data-prop='confirmpassword']").bind("cut copy paste", function (e) {
                e.preventDefault();
            });

        };

        // Catch validate when change
        page.password.change = function (e) {
            var target = $(e.target);
            page.password.validator.validate({
                targets: target,
                filter: function (item, method, state, options) {
                    return method != "correctpassword" && (item != "newpassword" || method != "checkconfirmpassword")
                }
            });
        }

        // Load dialog confirm
        page.loadDialog = function () {
            return App.async.all({
                confirmDialog: $.get("../Pages/Dialogs/ConfirmDialog.aspx")
            }).then(function (result) {
                $("#dialog-container").append(result.successes.confirmDialog);
                $("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;
            });
        }

        /**
         * パスワードのバリデーションルールを定義します。
         */
        page.password.options.validations = {
            password: {
                rules: {
                    required: true,
                    correctpassword: function (value, opts, state, done) {
                        var nowpassword = page.password.element.findP("password").val();
                        var options = {
                            cd_user: App.ui.page.user.EmployeeCD,
                            password: nowpassword
                        }
                        $.ajax(App.ajax.webapi.get(page.urls.checkpassword, options, { acsyn: false })).then(function (result) {
                            if (result) {
                                done(true);
                            } else {
                                done(false);
                            }
                        }).fail(function () {
                            done(false);
                        })
                        
                    }
                },
                options: {
                    name: "現在のパスワード"
                },
                messages: {
                    required: App.messages.base.required,
                    correctpassword: App.messages.app.AP0002
                }
            },
            newpassword: {
                rules: {
                    required: true,
                    minlength: 6,
                    hankaku: true,
                    alphanum: true,
                    samenowpassword: function (value, opts, state, done) {
                        var nowpassword = page.password.element.findP("password").val();

                        if (!App.validation.isEmpty(value) || !App.validation.isEmpty(nowpassword)) {
                            if (value == nowpassword) {
                                done(false);
                                return;
                            }
                        }
                        done(true);
                    },
                    checkconfirmpassword: function (value, opts, state, done) {
                        var confirmpassword = page.password.element.findP("confirmpassword").val();

                        if (!App.validation.isEmpty(value) || !App.validation.isEmpty(confirmpassword)) {
                            if (value != confirmpassword) {
                                done(false);
                                return;
                            }
                        }
                        done(true);
                    },
                    checkComplexity: function (value, opts, state, done) {
                        // 大文字・小文字・数字・記号(! @ # $ % ^ & * ( ) _ { } | ')のうち3つを含むか。
                        //var reg = [/(?=.*[A-Z]+).+$/, /(?=.*[a-z]+).+$/, /(?=.*[0-9]+).+$/, /(?=.*[!@#$%^&*()_{}|']+).+$/];
                        var reg = [/(?=.*[A-Z]+).+$/, /(?=.*[a-z]+).+$/, /(?=.*[0-9]+).+$/];
                        var cnt = 0;
                        if (!App.validation.isEmpty(value)) {
                            for (i = 0; i < 4 ; i += 1) {
                                if (value.match(reg[i])) {
                                    cnt += 1;
                                }
                            }
                            if (cnt < 3) {
                                done(false);
                                return;
                            }
                        }
                        done(true);
                    }
                },
                options: {
                    name: "新しいパスワード"
                },
                messages: {
                    required: App.messages.base.required,
                    minlength: App.messages.app.AP0003,
                    hankaku: App.messages.app.AP0003,
                    samenowpassword: App.messages.base.MS0001,
                    checkconfirmpassword: App.messages.base.MS0007,
                    checkComplexity: App.messages.app.AP0003,
                    alphanum: App.messages.app.AP0003
                }
            },
            confirmpassword: {
                rules: {
                    required: true,
                    minlength: 6,
                    hankaku: true,
                    checknewpassword: function (value, opts, state, done) {
                        var newpassword = page.password.element.findP("newpassword").val();

                        if (!App.validation.isEmpty(value) || !App.validation.isEmpty(newpassword)) {
                            if (value != newpassword) {
                                done(false);
                                return;
                            }
                        }
                        done(true);
                    }
                },
                options: {
                    name: "新しいパスワード（確認）"
                },
                messages: {
                    required: App.messages.base.required,
                    minlength: App.messages.app.AP0003,
                    hankaku: App.messages.app.AP0003,
                    checknewpassword: App.messages.base.MS0007
                }
            }
        };

        /**
         * パスワード変更処理を定義します。
         */
        page.password.changePassword = function () {
            var criteria = page.password.element.form().data();

            App.ui.page.notifyAlert.clear();

            page.password.validator.validate().then(function () {

                var inputdata = {
                    userid: App.ui.page.user.EmployeeCD,
                    cd_kaisha: App.ui.page.user.cd_kaisha,
                    password: criteria.password,
                    newpassword: criteria.newpassword
                }

                page.dialogs.confirmDialog.confirm({
                    text: App.messages.app.AP0004,
                    multiModal: true
                }).then(function () {
                    //TODO: データの保存処理をここに記述します。
                    return $.ajax(App.ajax.webapi.post(page.urls.passwordchange, inputdata))
                        .done(function (result) {
                            //TODO: パスワード変更成功時の処理をここに記述します。
                            //window.location = '<%= successRedirectUrl %>';
                            page.dialogs.confirmDialog.confirm({
                                text: App.messages.app.AP0005,
                                hideCancel: true
                            }).always(function () {
                                // Re-login
                                //$("#logout").click();
                                window.location.href = "Login.aspx?cd_kaisha=" + App.uri.queryStrings.cd_kaisha + "&user_id=" + App.uri.queryStrings.user_id;
                            });
                        }).fail(function (error) {
                            //TODO: パスワード変更失敗時の処理をここに記述します。
                            App.ui.page.notifyAlert.message(App.ajax.handleError(error).message).show();

                        });
                });
            });

        };

        // Btn [キャンセル]
        page.password.cancel = function () {
            $("#logout").click();
        }

        // Overwrite func get user info in site master
        page.getUserInfo = function () {
            var user_id = App.uri.queryStrings.user_id,
                cd_kaisha = App.uri.queryStrings.cd_kaisha;

            if (!user_id || !cd_kaisha) {
                window.location = '<%=ResolveUrl("~/AuthorizedError.aspx") %>';
            }
            // ユーザー情報の設定
            $.ajax(App.ajax.webapi.get('<%=ResolveUrl("~/api/PasswordChange/GetUserInfo") %>', {
                user_id: user_id,
                cd_kaisha: cd_kaisha
            }, { async: false }))
            .done(function (result) {
                if (result === null) {
                    window.location = '<%=ResolveUrl("~/AuthorizedError.aspx") %>';
                }
                // ユーザー情報の保持
                App.ui.page.user = result;
            }).fail(function () {
                window.location = '<%=ResolveUrl("~/AuthorizedError.aspx") %>';
            }).always(function () {
                $("#logout").on("click", function () {

                    var done = function (isLogout) {
                        if (isLogout === true) {
                            $("#isLogout").val(true);
                            //form1.submit();
                            $("#form1").submit();  //IE対応
                        }
                    };

                    if (App.ui.page.onlogout) {
                        App.ui.page.onlogout(done);
                    }
                    else {
                        done(true);
                    }

                    return false;
                });
            });
        }

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
    <div class="container">
         <div class="row">
            <div class="control-label col-xs-5">
                <label>会社</label>
            </div>
            <div class="control col-xs-7">
                <span data-prop="kaisha" ></span>
            </div>
        </div>
        <div class="row">
            <div class="control-label col-xs-5">
                <label >ユーザID</label>
            </div>
            <div class="control col-xs-7">
                <span id="userid" data-prop="userId" ></span>
            </div>
        </div>
        <div class="row">
            <div class="control-label col-xs-5">
                <label >現在のパスワード<span class="red">*</span></label>
            </div>
            <div class="control col-xs-7">
                <input type="password" class="text-selectAll" data-prop="password" maxlength="50" />
            </div>
        </div>
        <div class="row">
            <div class="control-label col-xs-5">
                <label >新しいパスワード<span class="red">*</span></label>
            </div>
            <div class="control col-xs-7">
                <input type="password" class="text-selectAll" data-prop="newpassword"  maxlength="50" />
            </div>
        </div>
        <div class="row">
            <div class="control-label col-xs-5">
                <label >新しいパスワード（確認）<span class="red">*</span></label>
            </div>
            <div class="control col-xs-7">
                <input type="password" class="text-selectAll" data-prop="confirmpassword"  maxlength="50" />
            </div>
        </div>
        <div class="row control" style="height: auto;">
            <p class="pull-right">
                <button type="button" id="changepassword" class="btn btn-primary" >パスワードの変更</button>
                <button type="button" id="cancel" class="btn btn-primary" >キャンセル</button>
            </p>
        </div>
    </div>
</asp:Content>

<asp:Content ID="FooterContent" ContentPlaceHolderID="FooterContent" runat="server">
    <div class="command"></div>
</asp:Content>

<asp:Content ID="DialogsContent" ContentPlaceHolderID="DialogsContent" runat="server">
    <div id="dialog-container"></div>
</asp:Content>
