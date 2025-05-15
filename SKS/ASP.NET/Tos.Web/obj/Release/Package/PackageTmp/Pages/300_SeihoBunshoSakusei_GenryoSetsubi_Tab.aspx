<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="300_SeihoBunshoSakusei_GenryoSetsubi_Tab.aspx.cs" Inherits="Tos.Web.Pages._300_SeihoBunshoSakusei_GenryoSetsubi_Tab" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>
<style type="text/css">
    #_300_SeihoBunshoSakusei_GenryoSetsubi_Tab .part-content .col-xs-12 {
        padding-left: 0px;
        padding-right: 0px;
    }

    #_300_SeihoBunshoSakusei_GenryoSetsubi_Tab .row-area-parent {
        height: calc(100%)!important;
    }

    #_300_SeihoBunshoSakusei_GenryoSetsubi_Tab .row-area {
        height: calc(100% - 30px);
    }

    #_300_SeihoBunshoSakusei_GenryoSetsubi_Tab .textarea-height {
        /*height: calc(100% - 32px);*/
    }

    #_300_SeihoBunshoSakusei_GenryoSetsubi_Tab textarea {
        width: calc(100% - 2px);
        resize: none;
        border: 1px solid #cccccc !important;
    }
    #_300_SeihoBunshoSakusei_GenryoSetsubi_Tab .part.roof {
        float:left;
        height: 100%;
        width: 100%;
        margin-bottom: 0px;
    }
    #_300_SeihoBunshoSakusei_GenryoSetsubi_Tab .col-content {
        height: calc(100% - 2px)!important;
    }
        #_300_SeihoBunshoSakusei_GenryoSetsubi_Tab .col-content textarea {
            margin-left: 0.5%;
        }

    /*#_300_SeihoBunshoSakusei_GenryoSetsubi_Tab .control-required {
        border-top: 5px solid #d9534f;
    }*/
</style>
<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var _300_SeihoBunshoSakusei_GenryoSetsubi_Tab = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.dialogDataTakeCount   // TODO:取得するデータ数を指定します。
        },
        param: {
            no_seiho: "",
            mode: ""
        },
        urls: {
            search: "../api/_300_SeihoBunshoSakusei_GenryoSetsubi_Tab/Get",
            searchCopy: "../api/_300_SeihoBunshoSakusei_GenryoSetsubi_Tab/getCopyData"
        },
        name: "【原料・機械設備・製造方法・表示事項】"
    };

    /**
     * 検索ダイアログの初期化処理を行います。
     */
    _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.initialize = function () {
        var element = $("#_300_SeihoBunshoSakusei_GenryoSetsubi_Tab");

        element.on("hidden.bs.modal", _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.hidden);
        element.on("shown.bs.modal", _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.shown);
        element.on("click", ".search", _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.search);
        element.on("change", ":input", _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.change);
        //TODO: 単一セレクトの場合は、下の１行を使用します
        element.on("click", ".search-list tbody ", _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.select);
        //TODO: 複数セレクトの場合は、上の１行を削除し、下の３行をコメント解除します
        //element.on("click", ".select", _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.select);
        //element.on("click", ".search-list tbody", _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.selectOne);
        //element.find("[name='select_cd_all']").on("click", _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.selectAll);
        _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.element = element;

        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#_300_SeihoBunshoSakusei_GenryoSetsubi_Tab .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#_300_SeihoBunshoSakusei_GenryoSetsubi_Tab .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body",
                show: function () {
                    element.find(".alert-message").show();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                }
            });

        _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.validator = element.validation(App.validation(_300_SeihoBunshoSakusei_GenryoSetsubi_Tab.options.validations, {
            success: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.setColValidStyle(item.element);

                    App.ui.page.notifyAlert.remove(item.element);
                }
            },
            fail: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.setColInvalidStyle(item.element);
                    if (state && state.suppressMessage) {
                        continue;
                    }

                    App.ui.page.notifyAlert.message(_300_SeihoBunshoSakusei_GenryoSetsubi_Tab.name + item.message, item.element).show();
                }
            },
            always: function (results) {
                //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
            }
        }));

        _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.search();
        page.finishLoading("TAB_Initilize", "TAB3", 5);
    };

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.setColInvalidStyle = function (target) {
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

        $target = $(target).closest(".control");
        $target.addClass("control-required");
        $target.removeClass("control-success");
        if ($target.hasClass("single-target")) {
            return;
        }

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
    _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.setColValidStyle = function (target) {
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
        $target = $(target).closest(".control");
        $target.removeClass("control-required");
        $target.addClass("control-success");
        if ($target.hasClass("single-target")) {
            return;
        }
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
    _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.hidden = function (e) {
        var element = _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.element,
            table = element.find(".search-list");

        //TODO: ダイアログ非表示時に、項目をクリアする処理をここに記述します。
        element.find(":input").val("");
        //TODO: 複数セレクトの場合は、下の１行をコメント解除します。
        //element.find(":checked").prop("checked", false);
        table.find("tbody").not(".item-tmpl").remove();

        element.findP("data_count").text("");
        element.findP("data_count_total").text("");

        var items = element.find(".search-criteria :input:not(button)");
        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.setColValidStyle(item);
        }

        _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.notifyInfo.clear();
        _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.notifyAlert.clear();
    };

    /**
     * 検索ダイアログ表示時処理を実行します。
     */
    _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.shown = function (e) {

        _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.element.find(":input:not(button):first").focus();
    };

    /**
     * 検索ダイアログ　バリデーションルールを定義します。
     */
    _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.options.validations = {
        nm_genryo_free_komoku: {
            rules: {
                required: true,
                maxbytelength: 4000
            },
            options: {
                name: "原料",
                byte: 2000
            },
            messages: {
                required: App.messages.base.required,
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_kikai_setsubi_free_komoku: {
            rules: {
                required: true,
                maxbytelength: 500
            },
            options: {
                name: "機械設備",
                byte: 250
            },
            messages: {
                required: App.messages.base.required,
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_seizo_hoho_free_komoku: {
            rules: {
                required: true,
                maxbytelength: 4000
            },
            options: {
                name: "製造方法",
                byte: 2000
            },
            messages: {
                required: App.messages.base.required,
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_hyoji_jiko_free_komoku: {
            rules: {
                required: true,
                maxbytelength: 500
            },
            options: {
                name: "表示事項",
                byte: 250
            },
            messages: {
                required: App.messages.base.required,
                maxbytelength: App.messages.base.maxbytelength
            }
        }
    };

    /**
     * 検索ダイアログの検索条件を組み立てます
     */
    _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.createFilter = function () {
        var param = _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.param;
        return {
            no_seiho: param.no_seiho,
            no_seiho_copy: param.no_seiho_copy,
            mode: param.mode
        }
    }

    /**
     * 検索ダイアログの検索処理を実行します。
     */
    _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.search = function () {
        var element = _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.element,
            url = _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.urls.search;

        _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.options.filter = _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.createFilter();

        $.ajax(App.ajax.webapi.get(url, _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.options.filter))
        .done(function (result) {
            var data = _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.prepairBindingData(result);
            _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.bind(data);
        }).always(function () {

        });
    };

    /**
     * Reload data in copy mode
     */
    _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.searchCopy = function () {
        var element = _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.element,
            url = _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.urls.searchCopy;

        _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.options.filter = _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.createFilter();

        $.ajax(App.ajax.webapi.get(url, _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.options.filter))
        .done(function (result) {
            var data = _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.mergeSearchData(result);
            _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.bind(data);
        }).always(function () {

        });
    }

    /**
     * Merge current data with copy data
     */
    _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.mergeSearchData = function (copyData) {
        var currentData = _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.currentData;
        copyData = copyData || {}
        if (currentData) {
            currentData.nm_genryo_free_komoku = copyData.nm_genryo_free_komoku || "";
            currentData.nm_kikai_setsubi_free_komoku = copyData.nm_kikai_setsubi_free_komoku || "";
            currentData.nm_seizo_hoho_free_komoku = copyData.nm_seizo_hoho_free_komoku || "";
            currentData.nm_hyoji_jiko_free_komoku = copyData.nm_hyoji_jiko_free_komoku || "";
        }
        return currentData;
    }

    /**
     * Prepair search data before binding: create changeset, create variables, ...
     */
    _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.prepairBindingData = function (searchData) {
        searchData = searchData || {};
        var param = _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.param,
            currentData = searchData.currentData,
            modes = page.options.mode;
        _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.data = App.ui.page.dataSet();
        _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.currentData = currentData;
        switch (param.mode) {
            case modes.new:
            case modes.new_copy:
                // Add new data
                _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.mergeSearchData(searchData.copyData);
                _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.data.add(currentData);
                break;
            case modes.edit_copy:
                // Merge copy data in copy mode
                _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.mergeSearchData(searchData.copyData);
            case modes.edit:
                // Edit data
                _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.data.attach(currentData);
                _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.data.update(currentData);
                break;
            case modes.view:
                _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.data.attach(currentData);
                break;
        }
        return currentData;
    }

    /**
     * 検索ダイアログの一覧にデータをバインドします。
     */
    _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.bind = function (data) {
        var element = _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.element,
            item = data || {};
        
        _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.element.form().bind(item);
        _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.activeModeControl();
        page.finishLoading("TAB_Bind", "TAB3", 5);
    };

    /**
     * mode control page's reaction for modes: new, edit, view, copy
     */
    _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.activeModeControl = function () {
        var mode = _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.param.mode,
            element = _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.element,
            modes = page.options.mode;
        switch (mode) {
            case modes.new:
                break;
            case modes.new_copy:
                break;
            case modes.edit:
                break;
            case modes.edit_copy:
                break;
            case modes.view:
                element.find("textarea").prop("disabled", true);
                break;
        }
    }

    /**
     * 一覧から行を選択された際の処理を実行します。（単一セレクト用）
     */
    _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.select = function (e) {
        var element = _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.element,
            target = $(e.target),
            id = target.closest("tbody").attr("data-key"),
            data;

        if (App.isUndef(id)) {
            return;
        }

        data = _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.data.entry(id);

        if (App.isFunc(_300_SeihoBunshoSakusei_GenryoSetsubi_Tab.dataSelected)) {
            if (!_300_SeihoBunshoSakusei_GenryoSetsubi_Tab.dataSelected(data)) {
                element.modal("hide");
            }
        } else {
            element.modal("hide");
        }
    };

    /**
    * On change function
    */
    _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.change = function (e) {
        var target = $(e.target),
            id = _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.element.attr("data-key"),
            property = target.attr("data-prop"),
            entity = _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.data.entry(id);

        _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.validator.validate({
            targets: target,
            filter: page.options.changeValidationFilter
        }).then(function () {
            page.setIsChangeValue();
            entity[property] = $.trim(target.val());
            _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.data.update(entity);
        });
    }

    /**
    * Get save data
    */
    _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.getSaveData = function () {
        return _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.data.getChangeSet();
    }

    // Check all validate in the tab
    _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.validateAll = function (filter) {
        var targets = _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.element.find(":input");
        return _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.validator.validate({
            targets: targets,
            filter: filter,
            state: {
                suppressMessage: false
            }
        })
    }

    //TODO: 複数セレクトを使用する場合は、上の単一セレクト用select関数を削除し、下の複数セレクト用の関数をコメント解除します
    //TODO: 単一セレクトの場合は、不要なコメント部分は削除してください
    <%--
        /**
         * 一覧から行を選択された際の処理を実行します。（複数セレクト用）
         */
        _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.select = function (e) {
            var element = _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.element,
                items;

            //TODO: 選択された行から起動元画面に返却したい値を抽出します
            items = element.find(".search-list").find("input:checked[name='select_cd']").map(function (index, item) {
                var tbody = $(item).closest("tbody"),
                    id = tbody.attr("data-key"),
                    data = _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.data.entry(id);
                return data.cd_torihiki;
            }).toArray();

            if (items.length == 0) {
                _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.notifyAlert.message(App.messages.base.MS0020).show();
                return;
            }

            if (App.isFunc(_300_SeihoBunshoSakusei_GenryoSetsubi_Tab.dataSelected)) {
                if (!_300_SeihoBunshoSakusei_GenryoSetsubi_Tab.dataSelected(items)) {
                    element.modal("hide");
                }
            } else {
                element.modal("hide");
            }
        };

        /**
         * 一覧の行をクリックした際の処理を実行します。（複数セレクト用）
         */
        _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.selectOne = function (e) {
            var target = $(e.target),
                tr = target.closest("tr");

            if (target.is("[name='select_cd']")) {
                return;
            }

            var check = tr.find("[name='select_cd']");
            check.prop("checked", !check.is(":checked"));
        };

        /**
         * 一覧のヘッダ部のckeckbox(ALLチェック用）をクリックした際の処理を実行します。（複数セレクト用）
         */
        _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.selectAll = function (e) {
            var target = $(e.target);
            _300_SeihoBunshoSakusei_GenryoSetsubi_Tab.element.find("[name='select_cd']:visible").prop("checked", target.is(":checked"));
        };
--%>
</script>
<div class="tab-pane" tabindex="-1" id="_300_SeihoBunshoSakusei_GenryoSetsubi_Tab">
    <div class="sub-tab-content smaller">
        <div class="part roof">
            <div class="part-content full-height">
                <div class="row full-height">
                    <div class="control col-xs-3 col-content single-target">
                        <div class="col-zr col-xs-12">
                            <label>原料</label>
                        </div>
                        <textarea class="textarea-height fit-25" data-prop="nm_genryo_free_komoku"></textarea>
                    </div>
                    <div class="control col-xs-3 col-content single-target">
                        <div class="col-zr col-xs-12">
                            <label>機械設備</label>
                        </div>
                        <textarea class="textarea-height fit-25" data-prop="nm_kikai_setsubi_free_komoku"></textarea>
                    </div>
                    <div class="control col-xs-3 col-content single-target">
                        <div class="col-zr col-xs-12">
                            <label>製造方法</label>
                        </div>
                        <textarea class="textarea-height fit-25" data-prop="nm_seizo_hoho_free_komoku"></textarea>
                    </div>
                    <div class="control col-xs-3 col-content single-target">
                        <div class="col-zr col-xs-12">
                            <label>表示事項</label>
                        </div>
                        <textarea class="textarea-height fit-25" data-prop="nm_hyoji_jiko_free_komoku"></textarea>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
