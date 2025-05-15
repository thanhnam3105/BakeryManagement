/** 最終更新日 : 2016-10-17 **/
(function () {

    var lang = App.ui.pagedata.lang("en", {
        _pageTitle: { text: "login" },
        cd_kaisha: { text: "company code" },
        userId: { text: "user id" },
        password: { text: "password" },
        persistantLogin: { text: "keep me logged in." },
        login: { text: "Login" },
        changepassword: { text: "Change password" },
    });

    //App.ui.pagedata.validation("en", {
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
    //            required: "please enter your password."
    //        }
    //    }
    //});
})();
