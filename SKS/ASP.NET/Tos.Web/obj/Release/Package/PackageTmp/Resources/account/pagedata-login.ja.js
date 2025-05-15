/** 最終更新日 : 2016-10-17 **/
(function () {

    var lang = App.ui.pagedata.lang("ja", {
        _pageTitle: { text: "001_ログイン" },
        cd_kaisha: { text: "会社" },
        userId: { text: "ユーザーID" },
        password: { text: "パスワード" },
        persistantLogin: { text: "ログインしたままにする" },
        login: { text: "ログイン" },
        changepassword: { text: "パスワード変更" },
    });

    //App.ui.pagedata.validation("ja", {
    //    cd_kaisha: {
    //        rules: {
    //            required: true,
    //            digits: true,
    //            existsCode: function (value, opts, state, done) {
    //                var element = $(".container");
    //                var cd_kaisha = element.findP("cd_kaisha").val();
    //                var cd_kaisha_validate = element.findP("cd_kaisha_validate").text();

    //                if (cd_kaisha == "") {
    //                    return done(true);
    //                }

                   
    //                return done(cd_kaisha_validate != "");
    //            }
    //        },
    //        options: {
    //            name: "会社コード"
    //        },
    //        messages: {
    //            required: App.messages.base.required,
    //            digits: App.messages.base.digits,
    //            existsCode: App.messages.app.AP0010
    //        }
    //    },
    //    nm_kaisha: {
    //        rules: {
    //            required: true
    //        },
    //        options: {
    //            name: "会社名"
    //        },
    //        messages: {
    //            required: App.messages.base.required
    //        }
    //    },
    //    userId: {
    //        rules: {
    //            required: true,
    //            digits: true,
    //        },
    //        options: {
    //            name: "ユーザーID"
    //        },
    //        messages: {
    //            required: App.messages.base.required,
    //            digits: App.messages.base.digits,
    //        }
    //    },
    //    password: {
    //        rules: {
    //            required: true
    //        },
    //        messages: {
    //            required: "パスワードは必須です。"
    //        }
    //    }
    //});
})();
