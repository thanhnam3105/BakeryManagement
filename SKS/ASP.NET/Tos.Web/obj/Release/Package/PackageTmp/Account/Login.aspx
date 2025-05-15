<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Login.aspx.cs" Inherits="Tos.Web.Account.Login" ClientIDMode="Static" EnableViewState="false" %>
<%--/** 最終更新日 : 2018-08-07 **/--%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title><%= Page.Title %></title>
    <!-- CSS -->
    <% #if DEBUG %>
    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/bootstrap.css") %>" type="text/css" />
    <% #else %>
    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/bootstrap.min.css") %>" type="text/css" />
    <% #endif %>
    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/site.css") %>" type="text/css" />
    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/themes/base/jquery-ui.css") %>" type="text/css" />
    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/menu.css") %>" type="text/css" />
    <link media="all" rel="stylesheet" href="<%=ResolveUrl("~/Content/icons.css") %>" type="text/css" />

    <!-- 
        下記の KB 提供されているパッチが適用されていない Internet Explorer 8 において
        ネイティブ JSON ライブラリに Stackoverflow が発生してしまう可能性があるため JSON2 ライブラリを利用するように対応します。
        http://support.microsoft.com/kb/976662
        Site.Masterで下記の実装を行うことにより必ず全てのページに対応されます。
    -->
    <!--[if IE 8]>
    <script type="text/javascript">
        if (navigator.appVersion.indexOf("MSIE 8.") != -1 || navigator.appVersion.indexOf("MSIE 7.") != -1) {
            JSON = undefined;
        }
    </script>        
    <![endif]-->

    <!-- JavaScript -->
    <% #if DEBUG %>
    <script src="<%=ResolveUrl("~/Scripts/json2.js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Scripts/jquery-3.3.1.js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Scripts/bootstrap.js") %>" type="text/javascript"></script>
    <% #else %>
    <script src="<%=ResolveUrl("~/Scripts/json2.min.js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Scripts/jquery-3.3.1.min.js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Scripts/bootstrap.min.js") %>" type="text/javascript"></script>
    <% #endif %>

    <!--[if lt IE 9]>
      <script src="<%=ResolveUrl("~/Scripts/html5shiv.js") %>"></script>
      <script src="<%=ResolveUrl("~/Scripts/respond.min.js") %>"></script>
    <![endif]-->
    
    <% #if DEBUG %>
    <script src="<%=ResolveUrl("~/Scripts/jquery-ui-1.12.1.js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Scripts/app.base.js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Scripts/app.validation.js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Scripts/app.validation.method.js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Scripts/app.obj.js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Scripts/app.ajax.js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Scripts/app.ui.js") %>" type="text/javascript"></script>
    <% #else %>
    <script src="<%=ResolveUrl("~/Scripts/jquery-ui-1.12.1.min.js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Scripts/app.min.js") %>" type="text/javascript"></script>
    <% #endif %>
    
    <!-- TODO: 言語ごとのリソースファイルの読み込み -->
    <script src="<%=ResolveUrl("~/Resources/message." + lang + ".js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Resources/message.app." + lang + ".js") %>"></script>
    <script src="<%=ResolveUrl("~/Resources/menu." + lang + ".js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Resources/pagedata-all." + lang + ".js") %>" type="text/javascript"></script>
    <script src="<%=ResolveUrl("~/Resources/account/pagedata-login." + lang + ".js") %>" type="text/javascript"></script>

    <style type="text/css">
        .container {
            margin-top: 50px;
            max-width: 480px;
        }

        label, select, input[type="tel"], input[type="text"], input[type="password"] {
            font-size: 15px!important;
        }

        .control-label, .control {
            height: 35px;   
        }

        label
        {
            margin-top: 9px;
        }

        input[type="text"], input[type="password"]
        {
            padding: 6px;
            margin: 2px;
        }

        .width-65 {
            width: 65px;
        }

        .width-69-percent {
            width: 74%;
            float:right;
        }

        .padding-bottom-0 {
            padding-bottom: 0px;
        }

        .padding-top-0 {
            padding-top: 0px;
        }

        .red-color {
            color: red;
        }

        .control-required {
            height: 35px!important; 
        }

        .height-auto {
            height: 35px!important; 
            padding:2px!important;
        }

        .width-99-percent {
            width: 99%;
        }

        .height-42 {
            height:42px!important;
        }

        .control-height {
            height: 56px!important;
        }

        .form-control {
            height: 34px!important;
            border-radius: 2px!important;
        }
        .margin-top-2 {
            margin-top: 2px!important;
        }

        .padding-input {
            padding-top: 4px!important;
        }
    </style>


    <script type="text/javascript">
        /**
        * Initialize variable。
        */
        var page = {
            login: {
                values: {
                    cd_kaisha: null,
                    user_id: null,
                    isChangeRunning: {},
                    isCheckPassword: true
                },
                options: {},
                urls: {
                    ma_kaisha: "../api/User/GetCompany",
                    ma_user_togo: "../api/User/GetUser",
                    confirmDialog: "../Pages/Dialogs/ConfirmDialog.aspx"
                },
            },
            dialogs: {}
        };

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
        * パスワードのバリデーションルールを定義します。
        */
        page.login.options.validations = {
            cd_kaisha: {
                rules: {
                    required: true,
                    digits: true,
                    existsCode: function (value, opts, state, done) {
                        var element = $(".container");
                        var cd_kaisha = element.findP("cd_kaisha").val();
                        var cd_kaisha_validate = element.findP("cd_kaisha_validate").text();

                        if (cd_kaisha == "") {
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
                    digits: App.messages.base.digits,
                    existsCode: App.messages.app.AP0010
                }
            },
            //nm_kaisha: {
            //    rules: {
            //        required: true
            //    },
            //    options: {
            //        name: "会社名"
            //    },
            //    messages: {
            //        required: App.messages.base.required
            //    }
            //},
            userId: {
                rules: {
                    required: true,
                    digits: true,
                },
                options: {
                    name: "ユーザーID"
                },
                messages: {
                    required: App.messages.base.required,
                    digits: App.messages.base.digits,
                }
            },
            password: {
                rules: {
                    required: true,
                    minlength: 6,
                    hankaku: true,
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
                    },
                    alphanum: true
                },
                options: {
                    name: "パスワード",
                    param: 6
                },
                messages: {
                    required: App.messages.base.required,
                    minlength: App.messages.app.AP0161,
                    hankaku: App.messages.app.AP0161,
                    checkComplexity: App.messages.app.AP0161,
                    alphanum: App.messages.app.AP0161
                }
            }
        };

        $(function () {
            App.ui.page.lang = "<%= System.Threading.Thread.CurrentThread.CurrentUICulture.TwoLetterISOLanguageName%>";
            App.ui.pagedata.lang.applySetting(App.ui.page.lang);
            App.ui.loading.show();
            //var validation = App.validation(
            //    App.ui.pagedata.validation(App.ui.page.lang),
            //    {
            //        success: function (results) {
            //            var i = 0, l = results.length;
            //            for (; i < l; i++) {
            //                App.ui.page.notifyAlert.remove();
            //            }
            //        },
            //        fail: function (results) {
            //            var i = 0, l = results.length;
            //            for (; i < l; i++) {
            //                App.ui.page.notifyAlert.message(results[i].message).show();
            //            }
            //        }
            //    }
            //);

            //$(".container").validation(validation);

            page.loadDialogs().then(function () {

                var element = $(".container");
                page.login.validator = element.validation(page.createValidator(page.login.options.validations));


                // 通知の設定
                App.ui.page.setNotify(App.ui.notify.info(document.body, {
                    container: ".slideup-area .info-message",
                    show: function () {
                        $(".info-message").show();
                    },
                    clear: function () {
                        $(".info-message").hide();
                    }
                }), App.ui.notify.alert(document.body, {
                    container: ".slideup-area .alert-message",
                    show: function () {
                        $(".alert-message").show();
                    },
                    clear: function () {
                        $(".alert-message").hide();
                    }
                }));

                var userIdElem = $("#userid"),
                    userIdHintElem = $("#useridhint");

                $("#passwordhint, #useridhint").on("focus", function (e) {
                    $("#" + e.currentTarget.id.replace("hint", "")).show().focus();
                    $("#" + e.currentTarget.id).hide();
                });

                //ログインエラー
                var loginError = '<%= LoginErrorMessage %>',
                    loginErrorPassword = '<%=LoginErrorPasswordMessage %>'
                loginErrorId = App.uuid();

                //エラーメッセージを表示
                if (loginError) {
                    App.ui.page.notifyAlert.message(loginError, loginErrorId).show();
                }

                $("#login").on("click", function () {
                    App.ui.page.notifyAlert.clear();
                    App.ui.page.notifyAlert.remove("existsPassword");

                    page.login.values.isChangeRunning["password"] = true;
                    page.login.checkPassword(false);
                    var sleep = 0;
                    var condition = "Object.keys(page.login.values.isChangeRunning).length == 0";
                    App.ui.wait(sleep, condition, 100)
                    .then(function () {
                        if (!page.login.values.isCheckPassword) {
                            return;
                        }
                        page.login.validator.validate({
                            targets: $(".container").find(":input:visible")
                        }).done(function (result) {
                            if (loginError) {
                                App.ui.page.notifyAlert.remove(loginErrorId);
                            }
                            if (result.fails.length) {
                                return;
                            }
                            $("#form1").submit();
                        });
                     });
                });

                if (loginError) {
                    App.ui.page.notifyAlert.message(loginError, loginErrorId).show();
                    var element = $(".container");
                    page.loadMasterData(element, false).then(function () {
                        var cd_kaisha = $("#cd_kaisha").val();

                        if (cd_kaisha != "") {
                            element.findP("cd_kaisha_validate").text(parseInt(cd_kaisha));
                            element.findP("nm_kaisha").val(parseInt(cd_kaisha));
                        }
                    });
                }

                if (loginErrorPassword) {
                    var options = {
                        text: App.messages.app.AP0052,
                        hideCancel: true
                    };
                    var element = $(".container");
                    var cd_kaisha = $("#cd_kaisha").val();
                    user_id = $("#userid").val();
                    element.findP("userId").val(user_id);
                    element.findP("cd_kaisha").val(cd_kaisha);

                    page.dialogs.confirmDialog.confirm(options)
                    .then(function () {
                        window.location.href = "PasswordChange.aspx?cd_kaisha=" + cd_kaisha + "&user_id=" + user_id;
                    });
                }

                //$(".container").on("keydown", function (evt) {
                //    if (evt.keyCode === 13) {
                //        $("#login").click();
                //    }
                //});

                /**
                 * jQuery イベントで、ページの読み込み処理を定義します。
                 */
                page.initialize(loginError);
            }).always(function () {
                App.ui.loading.close();
            });
        });

        
        /**
        * 画面の初期化処理を行います。
        */
        page.initialize = function (loginError) {
            var element = $(".container");
            App.ui.loading.show();
            if (!App.isUndefOrNullOrStrEmpty(App.uri.queryStrings.cd_kaisha)) {
                page.login.values.cd_kaisha = parseInt(App.uri.queryStrings.cd_kaisha);
            }
            if (!App.isUndefOrNullOrStrEmpty(App.uri.queryStrings.user_id)) {
                page.login.values.user_id = App.uri.queryStrings.user_id;
            }

            if (!loginError) {
                page.loadMasterData(element, true).then(function () {
                    if (!App.isUndefOrNullOrStrEmpty(page.login.values.cd_kaisha)) {
                        element.findP("cd_kaisha").val(("000" + page.login.values.cd_kaisha).slice(-4));
                        element.findP("nm_kaisha").val(page.login.values.cd_kaisha);
                        element.findP("cd_kaisha_validate").text(page.login.values.cd_kaisha);
                    }
                    if (!App.isUndefOrNullOrStrEmpty(page.login.values.user_id)) {
                        element.findP("userId").val(page.login.values.user_id);
                    }
                }).always(function () {
                    App.ui.loading.close();
                });
            } else {
                App.ui.loading.close();
            }

            element.on("change", ":input", page.login.change);

            element.on("click", "#changepassword", page.login.changePassword);

            element.findP("cd_kaisha").focus();

            element.on("keypress", ".limit-input-int", function (event) {
                if ((event.which < 48 || event.which > 57)) {
                    event.preventDefault();
                }
            });
        }

        /**
         * 共有ダイアログのロード処理を実行します。
         */
        page.loadDialogs = function () {

            //TODO: 共有ダイアログデータを取得し、画面にバインドする処理を記述します。
            return App.async.all({
                confirmDialog: $.get(page.login.urls.confirmDialog),
            }).then(function (result) {

                $("#dialog-container").append(result.successes.confirmDialog);
                page.dialogs.confirmDialog = ConfirmDialog;

            });
        };

        /**
         * マスターデータのロード処理を実行します。
         */
        page.loadMasterData = function (element, isLoadCompany) {
            App.ui.loading.show();
            return $.ajax(App.ajax.webapi.get(page.login.urls.ma_kaisha, {cd_kaisha: null})).then(function (result) {
                var nm_kaisha = element.findP("nm_kaisha");
                nm_kaisha.children().remove();
                App.ui.appendOptions(
                    nm_kaisha,
                    "cd_kaisha",
                    "nm_kaisha",
                    result,
                    false
                );
                if (isLoadCompany) {
                    nm_kaisha.change();
                }
            }).always(function () {
                App.ui.loading.close();
            });
        }

        /**
         * Change input。
         */
        page.login.change = function (e) {
            var element = $(".container"),
                target = $(e.target),
                property = target.attr("data-prop");

            if (property == "cd_kaisha") {
                if (element.findP("cd_kaisha").val() == "" && !App.isNumeric(element.findP("cd_kaisha").val())) {
                    element.findP("nm_kaisha").val("");
                    element.findP("cd_kaisha_validate").text("");
                } else {
                    element.findP("nm_kaisha").val("");
                    element.findP("cd_kaisha_validate").text(element.findP("cd_kaisha").val());
                }
            }

            App.ui.page.notifyAlert.remove("existsPassword");
            

            if (property != "password") {
                page.login.validator.validate({
                    targets: target
                }).then(function () {
                    // Company code change。
                    if (property == "cd_kaisha") {
                        var cd_kaisha = element.findP("cd_kaisha").val();

                        if (cd_kaisha != "" && App.isNumeric(cd_kaisha)) {
                            cd_kaisha = parseInt(cd_kaisha);

                            return $.ajax(App.ajax.webapi.get(page.login.urls.ma_kaisha, { cd_kaisha: cd_kaisha })).then(function (result) {
                                if (result.length > 0) {
                                    element.findP("cd_kaisha").val(("000" + cd_kaisha).slice(-4));
                                    element.findP("nm_kaisha").val(cd_kaisha);
                                    element.findP("cd_kaisha_validate").text(cd_kaisha);
                                } else {
                                    element.findP("cd_kaisha_validate").text("");
                                    element.findP("nm_kaisha").val("");
                                }
                                page.login.validator.validate({
                                    targets: target
                                });
                            });
                        } else {
                            element.findP("nm_kaisha").val("");
                            element.findP("cd_kaisha_validate").text("");
                        }
                    }
                    //Company name change。
                    if (property == "nm_kaisha") {
                        var nm_kaisha = element.findP("nm_kaisha").val();

                        element.findP("cd_kaisha").val(("000" + nm_kaisha).slice(-4));
                        element.findP("cd_kaisha_validate").text(nm_kaisha);
                        App.ui.page.notifyAlert.remove(element.findP("cd_kaisha"));
                        page.login.validator.validate({
                            targets: element.findP("cd_kaisha")
                        });
                    }

                    //User code change
                    if (property == "userId") {
                        var userId = element.findP("userId").val();

                        element.findP("userId").val(("0000000000" + userId).slice(-10));
                    }

                }).always(function () {
                    App.ui.loading.close();
                });
            } else {
                page.setColValidStyle(target);
                App.ui.page.notifyAlert.remove(target);
                var options = {
                    targets: target,
                    filter: page.login.validationFilter
                };

                page.login.validator.validate(options);
            }
        }

        /**
         * 画面明細のバリデーションフィルターを設定します。（必須チェックを行わない）
         */
        page.login.validationFilter = function (item, method, state, options) {
            return (method !== "minlength" && method !== "hankaku" && method !== "alphanum" && method !== "checkComplexity");
        };

        /**
         * Check password。
         */
        page.login.checkPassword = function (isChange) {
            App.ui.page.notifyAlert.clear();

            var element = $(".container");
            var cd_kaisha = element.findP("cd_kaisha_validate").text(),
                user_id = element.findP("userId").val(),
                password = element.findP("password").val();

            if (App.isUndefOrNullOrStrEmpty(cd_kaisha) || App.isUndefOrNullOrStrEmpty(user_id) || App.isUndefOrNullOrStrEmpty(password)) {
                delete page.login.values.isChangeRunning["password"];
                page.login.values.isCheckPassword = true;
                return true;
            }

            $.ajax(App.ajax.webapi.get(page.login.urls.ma_user_togo, { id_user: user_id, cd_kaisha: cd_kaisha })).then(function (result) {
                if (result.length > 0) {
                    var item = result[0];
                    if (item.password != password) {
                        if (!isChange) {
                            element.findP("password").val("");
                            App.ui.page.notifyAlert.message("ユーザーIDまたはパスワードが違います。", "existsPassword").show();
                        }

                        page.login.values.isCheckPassword = false;
                        return false;
                    } else {
                        page.login.values.isCheckPassword = true;
                        return true;
                    }
                } else {
                    page.login.values.isCheckPassword = false;
                    if (!isChange) {
                        element.findP("password").val("");
                        App.ui.page.notifyAlert.message("ユーザーIDまたはパスワードが違います。", "existsPassword").show();
                    }
                    return false;
                }
            }).fail(function () {
                page.login.values.isCheckPassword = true;
                return true;
            }).always(function(){
                delete page.login.values.isChangeRunning["password"];
            });
        }

        /**
         * Move to change password screen。
         */
        page.login.changePassword = function () {
            var element = $(".container");
            App.ui.page.notifyAlert.clear();

            var cd_kaisha = element.findP("cd_kaisha_validate").text(),
                user_id = element.findP("userId").val();

            App.ui.page.notifyAlert.remove(element.findP("password"));
            page.setColValidStyle(element.findP("password"));

            page.login.validator.validate({
                targets: element.find("[data-prop='cd_kaisha'], [data-prop='userId']")
            }).then(function () {
               
                $.ajax(App.ajax.webapi.get(page.login.urls.ma_user_togo, { id_user: user_id, cd_kaisha: cd_kaisha })).then(function (result) {
                    if (result.length > 0) {
                        window.location.href = "PasswordChange.aspx?cd_kaisha=" + cd_kaisha + "&user_id=" + user_id;
                    } else {
                        App.ui.page.notifyAlert.message(App.str.format(App.messages.app.AP0032, { name: "ユーザーID", tablename: "ユーザーマスタ" }), element.findP("userId")).show();
                    }
                });
            });
        }
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div class="wrap">
        <!-- ヘッダーのレイアウト -->
        <div class="navbar navbar-default navbar-fixed-top">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".nav-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <div class="navbar-brand app-title" data-app-text="systemName">&nbsp;</div>
                <!-- ナビゲーションメニュー -->
                <div class="navbar-brand page-title"></div>

       </div>
            <div class="collapse navbar-collapse">

                <!-- ユーザー情報のコンテナ -->
                <p class="navbar-text pull-right">
                    <!-- TODO: ユーザー情報の表示は コードビハインドの SetUserInfo メソッドを変更してください -->
                    <button type="button" class="btn btn-link btn-xs" id="logout" runat="server">
                        <i class="icon-user"></i>
                    </button>
                    <span id="userName" class="user-name" runat="server"></span>
                </p>
            </div>
        </div>

        <!-- メニュー表示のコンテナー -->
        <div class="navbar navbar-static-top menu-container" style="display:none">
        </div>
    
        <div class="container">

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title" data-app-text="login"></h3>
                </div>
                <div class="panel-body padding-bottom-0">
                    <div class="form-group">
                        <div class="control-label col-sm-4  height-42">
                            <label data-app-text="cd_kaisha" style="min-width: 0px;"></label><span class="red-color">*</span>
                        </div>
                        <div class="control col-sm-8  height-42">
                            <input type="tel" class="form-control width-65 limit-input-int ime-active margin-top-2 padding-input" data-prop="cd_kaisha" maxlength="4" />
                            <input type="text" runat="server" class="form-control width-65 limit-input-int ime-active" id="cd_kaisha" data-prop="cd_kaisha" maxlength="4" style="display:none" />
                            <label data-prop="cd_kaisha_validate" style="display:none;"></label> 
                            <select class="width-69-percent form-control margin-top-2" data-prop="nm_kaisha"></select>
                        </div>
                    </div>
                </div>
                <div class="panel-body padding-top-0">
                    <div class="form-group">
                        <div class="control-label col-sm-4 height-42">
                            <label data-app-text="userId"></label><span class="red-color">*</span>
                        </div>
                        <div class="control col-sm-8 height-42">
                            <input type="tel" class="form-control width-99-percent limit-input-int ime-active margin-top-2 padding-input" data-prop="userId" maxlength="10"/>
                            <input type="text" runat="server" id="userid" class="form-control width-99-percent limit-input-int ime-active margin-top-2" data-prop="userId" maxlength="10" style="display:none;"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="control-label col-sm-4 height-42">
                            <label data-app-text="password"></label><span class="red-color">*</span>
                        </div>
                        <div class="control col-sm-8 height-42">
                            <input type="password" runat="server" id="password" class="form-control width-99-percent padding-input" data-prop="password" maxlength="50" />
                        </div>
                    </div>
<%--                    <div class="form-group">
                        <div class="col-sm-offset-4 col-sm-8">
                            <input type="checkbox" runat="server" id="persistlogin" />
                            <label for="persistlogin" data-app-text="persistantLogin"></label>
                        </div>
                    </div>--%>
                </div>
                <div class="panel-footer">
                    <button type="button" id="login" class="btn btn-primary btn-block" data-app-text="login"></button>
                    <button type="button" id="changepassword" class="btn btn-primary btn-block" data-app-text="changepassword"></button>
                </div>
            </div>

        </div>
    </div>

    <!-- フッターのレイアウト -->
    <div class="footer">
        <div class="footer-container">
            <!-- 通知／エラーメッセージの表示エリア -->
            <div class="message-area slideup-area">
                <div class="alert-message" data-app-text="title:alertTitle" style="display: none">
                    <ul></ul>
                </div>
                <div class="info-message" data-app-text="title:infoTitle" style="display: none">
                    <ul></ul>
                </div>
                </div>

            <div class="footer-content">
                </div>
            </div>
        </div>
    </form>
    <div id="dialog-container"></div>
</body>
</html>
