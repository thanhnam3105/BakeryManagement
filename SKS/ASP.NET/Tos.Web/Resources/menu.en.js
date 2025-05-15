(function () {
    // visible が未設定の場合は表示
    // visible が "*" の場合は権限確認しない
    // visible が "*" 以外の場合は、一致する role だけに表示
    // visible が配列の場合は、一致する role が含まれている場合だけ表示
    // visible が関数の場合は、戻り値として true が返ってきた場合だけ表示
    App.ui.ddlmenu.settings("en", "menu", [
        {
            display: "hierarchical menu",
            items: [
                {
                    display: "simple menu",
                    items: [
                        { display: "SearchList", url: "/Pages/SearchListWithDialog.aspx" },
                        { display: "SearchListWithMultiSelect", url: "/Pages/SearchListWithMultiSelect.aspx" }
                    ]
                },
                {
                    display: "property specification",
                    items: [
                        {
                            display: "load property", url: "#OptionSample.aspx",
                            load: function (target) {
                                page.menu.loadData(target);
                            }
                        },
                        {
                            display: "click property",
                            url: "/Pages/HeaderDetailScrollable.aspx",
                            click: function (e) {
                                document.location = "OptionSample.aspx?userid=" + App.ui.page.user.EmployeeCD;
                                return false;

                                //ドロップダウンメニューでは無効とする場合
                                //var $target = $(e.target);
                                //if ($target.closest(".menu-container").length == 0) {  
                                //    document.location = "OptionSample.aspx?userid=" + App.ui.page.user.EmployeeCD;
                                //    return false;
                                //}
                            }
                        }
                    ]
                },
                {
                    display: "visible property",
                    items: [
                        { display: "portal type", url: "/Pages/PortalMenu.aspx" },
                        { display: "tree type", url: "/Pages/TreeMenu.aspx" }
                    ],
                    visible: function (roles) {
                        for (var role in roles) {
                            if (!!/^sys.*$/i.test(role.AuthorityCode)) {
                                return true;
                            }
                        }
                        return false;
                    }
                }
            ]
        },
        {
            display: "direct button",
            url: "/Pages/SearchList.aspx"
        },
        {
            display: "option pattern",
            items: [
                {
                    display: "others",
                    items: [
                        {
                            display: "new window open",
                            url: "#Chart",
                            click: function (e) {
                                App.ui.transfer("../../Pages/Chart.aspx");
                                return false;
                            }
                        }
                    ]
                }
            ]
        }
    ]);

})(App);
