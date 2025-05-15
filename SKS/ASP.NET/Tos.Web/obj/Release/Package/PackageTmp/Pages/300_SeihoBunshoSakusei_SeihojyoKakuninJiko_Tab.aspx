<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.aspx.cs" Inherits="Tos.Web.Pages._300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>

<style type="text/css">
    #_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab .part-content .col-xs-12 {
    }

    #_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab .row-table {
        height: 128px;
        background-color: #f6f6f6;
    }

    #_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab .row-title, .control-title {
        height: 22px !important;
    }

    #_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab .row-area-parent {
        height: 246px !important;
    }

    #_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab .row-area {
        height: 196px !important;
    }

    #_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab textarea {
        /*width: 292px!important;*/
        resize: none;
        border: 1px solid #cccccc !important;
        /*font-size: 11px!important;*/
    }

    #_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab select {
        border: 1px solid #cccccc !important;
    }

    #_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab .text-content {
        height: 187px !important;
    }

    #_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab .col-content {
        height: 100%!important;
    }

        #_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab .col-content textarea {

        }
        #_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab .col-content textarea.fit-25 {
           
        }

    #_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab .col-content2 {
        height: calc(100% - 129px)!important;
    }

        /*#_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab .col-content2 textarea {
            font-size: 11px!important;
        }*/
        #_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab .col-content2 textarea.fit-25 {
            
        }

    #_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab .part.roof {
        height: 100%;
        margin-right: 0px;
        margin-bottom: 0px;
        overflow: auto;
    }
</style>

<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.dialogDataTakeCount   // TODO:取得するデータ数を指定します。
        },
        urls: {
            search: "../api/_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab",
            searchCopy: "../api/_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab/getCopyData"
        },
        param: {
            mode: "edit",
            no_seiho: "0001-A01-19-0068",
        },
        name: "【製法上の確認事項】",
        defaultValue: {
            nm_free_kanren_tokkyo: "出願番号　   ：（未公開のもの）\n特開番号　   ：（公開中のもの）\n特許番号　   ：（特許成立したもの）\n出願人　　   ：\n発明の名称   ：\n請求項１　   ：",
            nm_free_tasha_kanren_tokkyo: "（なしの場合）\n他社関連特許無し。　　\n（ありの場合）\n特許番号　　　 ：\n特許権者　　　 ：\n請求項１　　　 ：",
            nm_shokuhin_tenkabutsu: "（なしの場合）\n該当無し。\n（ありの場合）\n物質名（原料名）　　：\n使用基準　対象食品　：\n使用量等の最大限度   ：\n使用制限　　            ：\n当該製品　食品分類　：\n配合量　　　　　　   ：\n用途　　　　　　　   ：",
            nm_free_genryo_shitei: "（なしの場合）\n該当無し。\n（ありの場合）\n原料指定の例：国産、北海道産、○○社、こしひかり　など",
            nm_free_genryo_seigen: "（なしの場合）\n該当無し。\n（ありの場合）\n原料制限の例：中国産不使用　など"
        }
    };

    /**
     * 検索ダイアログの初期化処理を行います。
     */
    _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.initialize = function () {
        var element = $("#_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab");

        element.on("shown.bs.modal", _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.shown);
        element.on("change", ":input", _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.change);

        _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.element = element;

        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body",
                show: function () {
                    element.find(".alert-message").show();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                }
            });

        _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.validator = element.validation(App.validation(_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.options.validations, {
            success: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.setColValidStyle(item.element);

                    App.ui.page.notifyAlert.remove(item.element);
                }
            },
            fail: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.setColInvalidStyle(item.element);
                    if (state && state.suppressMessage) {
                        continue;
                    }

                    App.ui.page.notifyAlert.message(_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.name + item.message, item.element).show();
                }
            },
            always: function (results) {
                //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
            }
        }));

        _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.search();
        _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.activeDisplay();
        page.finishLoading("TAB_Initilize", "TAB7", 5);
    };
    
    /**
     * Set up display status
     */
    _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.activeDisplay = function () {
        var modes = page.options.mode;
        switch (_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.param.mode) {
            case modes.new:
            case modes.new_copy:
            case modes.edit:
            case modes.edit_copy:
                break;
            case modes.view:
                _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.element.find(":input").prop("disabled", true);
                break;
        }
    }

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.setColInvalidStyle = function (target) {
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
        if ($target.hasClass("control")) {
            $target.addClass("control-required");
        } else {
            $target.addClass("control-required-label");
        }
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
    _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.setColValidStyle = function (target) {
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
        $target.removeClass("control-required control-required-label");
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
    _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.hidden = function (e) {
        var element = _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.element,
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
            _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.setColValidStyle(item);
        }

        _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.notifyInfo.clear();
        _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.notifyAlert.clear();
    };

    /**
     * 検索ダイアログ表示時処理を実行します。
     */
    _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.shown = function (e) {

        _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.element.find(":input:not(button):first").focus();
    };

    /**
     * 検索ダイアログ　バリデーションルールを定義します。
     */
    _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.options.validations = {
        nm_free_kanren_tokkyo: {
            rules: {
                required: true,
                maxbytelength: 6000
            },
            options: {
                name: "関連特許",
                byte: 3000
            },
            messages: {
                required: App.messages.base.required,
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_free_tasha_kanren_tokkyo: {
            rules: {
                required: true,
                maxbytelength: 3000
            },
            options: {
                name: "他社関連特許",
                byte: 1500
            },
            messages: {
                required: App.messages.base.required,
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_shokuhin_tenkabutsu: {
            rules: {
                required: true,
                maxbytelength: 1500
            },
            options: {
                name: "使用基準のある食品添加物の使用",
                byte: 750
            },
            messages: {
                required: App.messages.base.required,
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_eigyo_kyoka_gyoshu01: {
            rules: {
                required: true,
                maxbytelength: 40
            },
            options: {
                name: "営業許可業種１",
                byte: 20
            },
            messages: {
                required: App.messages.base.required,
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_eigyo_kyoka_gyoshu02: {
            rules: {
                maxbytelength: 40
            },
            options: {
                name: "営業許可業種２",
                byte: 20
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_eigyo_kyoka_gyoshu03: {
            rules: {
                maxbytelength: 40
            },
            options: {
                name: "営業許可業種３",
                byte: 20
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_eigyo_kyoka_gyoshu04: {
            rules: {
                maxbytelength: 40
            },
            options: {
                name: "営業許可業種４",
                byte: 20
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_eigyo_kyoka_gyoshu05: {
            rules: {
                maxbytelength: 40
            },
            options: {
                name: "営業許可業種５",
                byte: 20
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_free_genryo_shitei: {
            rules: {
                required: true,
                maxbytelength: 1000
            },
            options: {
                name: "原料の指定",
                byte: 500
            },
            messages: {
                required: App.messages.base.required,
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_free_genryo_seigen: {
            rules: {
                required: true,
                maxbytelength: 1000
            },
            options: {
                name: "原料の制限",
                byte: 500
            },
            messages: {
                required: App.messages.base.required,
                maxbytelength: App.messages.base.maxbytelength
            }
        },
        nm_free_sonota: {
            rules: {
                maxbytelength: 400
            },
            options: {
                name: "フリー入力",
                byte: 200
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        }
    };

    /**
     * On change function 
     */
    _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.change = function (e) {
        var target = $(e.target),
            property = target.attr("data-prop"),
            element = _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.element,
            entity = _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.data.entry(element.attr("data-key"));

        _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.validator.validate({
            targets: target,
            filter: page.options.changeValidationFilter
        }).then(function () {
            page.setIsChangeValue();
            entity[property] = target.val() ? target.val() : null;
        });
    }

    /**
     * 検索ダイアログの検索処理を実行します。
     */
    _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.search = function () {
        var element = _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.element,
            url = _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.urls.search;

        _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.options.filter = _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.createFilter();

        $.ajax(App.ajax.webapi.get(url, _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.options.filter))
        .done(function (result) {
            var data = _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.prepairBindingData(result);
            _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.bind(data);
        }).always(function () {

        });
    };

    /**
     * Reload data in copy mode
     */
    _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.searchCopy = function () {
        var element = _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.element,
            url = _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.urls.searchCopy;

        _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.options.filter = _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.createFilter();

        $.ajax(App.ajax.webapi.get(url, _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.options.filter))
        .done(function (result) {
            var data = _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.mergeSearchData(result);
            _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.bind(data);
        }).always(function () {

        });
    }

    /**
     * Merge current data with copy data
     */
    _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.mergeSearchData = function (copyData) {
        var currentData = _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.currentData;
        copyData = copyData || {}
        if (currentData) {
            currentData.nm_free_kanren_tokkyo = copyData.nm_free_kanren_tokkyo || "";
            currentData.nm_free_tasha_kanren_tokkyo = copyData.nm_free_tasha_kanren_tokkyo || "";
            currentData.nm_shokuhin_tenkabutsu = copyData.nm_shokuhin_tenkabutsu || "";
            currentData.nm_free_genryo_shitei = copyData.nm_free_genryo_shitei || "";
            currentData.nm_free_genryo_seigen = copyData.nm_free_genryo_seigen || "";
            currentData.nm_eigyo_kyoka_gyoshu01 = copyData.nm_eigyo_kyoka_gyoshu01 || "";
            currentData.nm_eigyo_kyoka_gyoshu02 = copyData.nm_eigyo_kyoka_gyoshu02 || "";
            currentData.nm_eigyo_kyoka_gyoshu03 = copyData.nm_eigyo_kyoka_gyoshu03 || "";
            currentData.nm_eigyo_kyoka_gyoshu04 = copyData.nm_eigyo_kyoka_gyoshu04 || "";
            currentData.nm_eigyo_kyoka_gyoshu05 = copyData.nm_eigyo_kyoka_gyoshu05 || "";
            currentData.nm_free_sonota = copyData.nm_free_sonota || "";
        }
        return currentData;
    }

    /**
     * Prepair search data before binding: create changeset, create variables, ...
     */
    _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.prepairBindingData = function (searchData) {
        searchData = searchData || {};
        var param = _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.param,
            currentData = searchData.currentData,
            modes = page.options.mode;
        _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.data = App.ui.page.dataSet();
        _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.currentData = currentData;
        switch (param.mode) {
            case modes.new:
                // Add new data
                $.extend(currentData, _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.defaultValue);
                _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.data.add(currentData);
                break;
            case modes.new_copy:
                // Add new data
                _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.mergeSearchData(searchData.copyData);
                _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.data.add(currentData);
                break;
            case modes.edit_copy:
                // Merge copy data in copy mode
                _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.mergeSearchData(searchData.copyData);
            case modes.edit:
                // Edit data
                _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.data.attach(currentData);
                _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.data.update(currentData);
                break;
            case modes.view:
                _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.data.attach(currentData);
                break;
        }
        return currentData;
    }
    /**
     * 検索ダイアログの検索条件を組み立てます
     */
    _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.createFilter = function () {
        var param = _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.param;
        return {
            no_seiho: param.no_seiho,
            mode: param.mode,
            no_seiho_copy: param.no_seiho_copy
        }
    };

    /**
     * 検索ダイアログの一覧にデータをバインドします。
     */
    _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.bind = function (data) {
        var element = _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.element,
            data = data || {};

        element.form().bind(data);
        page.finishLoading("TAB_Bind", "TAB7", 5);
    };

    // Get save data
    _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.getSaveData = function () {
        return _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.data.getChangeSet();
    }

    // Check all validate in the tab
    _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.validateAll = function (filter) {
        var targets = _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.element.find(":input");

        return _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab.validator.validate({
            targets: targets,
            filter: filter,
            state: {
                suppressMessage: false
            }
        })
    }

</script>


<div class="tab-pane" tabindex="-1" id="_300_SeihoBunshoSakusei_SeihojyoKakuninJiko_Tab">
    <div class="sub-tab-content smaller">
        <div class="part roof">
            <div class="part-content full-height">
                <div class="row full-height">
                    <div class="col-xs-2 col-zr full-height" style="width: 40%;">
                        <div class="col-xs-6 control col-content single-target">
                            <div class="col-xs-12 col-zr">
                                <label>関連特許</label>
                            </div>
                            <textarea class="textarea-height fit-25" data-prop="nm_free_kanren_tokkyo"></textarea>
                        </div>
                        <div class="col-xs-6 control col-content single-target">
                            <div class="col-xs-12 col-zr">
                                <label>他社関連特許</label>
                            </div>
                            <textarea class="textarea-height fit-25" data-prop="nm_free_tasha_kanren_tokkyo"></textarea>
                        </div>
                    </div>
                    <div class="col-xs-2 col-zr full-height" style="width: 60%;">
                        <div class="col-xs-4 control col-content2  single-target">
                            <div class="col-xs-12 col-zr">
                                <label>使用基準のある食品添加物の使用</label>
                            </div>
                            <textarea class="textarea-height fit-25" data-prop="nm_shokuhin_tenkabutsu"></textarea>
                        </div>
                        <div class="col-xs-4 control col-content2  single-target">
                            <div class="col-xs-12 col-zr">
                                <label>原料の指定</label>
                            </div>
                            <textarea class="textarea-height fit-25" data-prop="nm_free_genryo_shitei"></textarea>
                        </div>
                        <div class="col-xs-4 control col-content2  single-target">
                            <div class="col-xs-12 col-zr">
                                <label>原料の制限</label>
                            </div>
                            <textarea class="textarea-height fit-25" data-prop="nm_free_genryo_seigen"></textarea>
                        </div>
                        <div class="col-xs-8 col-zr">
                            <div class="row">
                                <div class="control-label col-xs-10 fix-label-lg">
                                    <label class="lb">営業許可業種1</label>
                                    <input type="text" class="prop" data-prop="nm_eigyo_kyoka_gyoshu01">
                                </div>
                                <div class="control col-xs-2"></div>
                            </div>
                            <div class="row">
                                <div class="control-label col-xs-10 fix-label-lg">
                                    <label class="lb">営業許可業種2</label>
                                    <input type="text" class="prop" data-prop="nm_eigyo_kyoka_gyoshu02">
                                </div>
                                <div class="control col-xs-2"></div>
                            </div>
                            <div class="row">
                                <div class="control-label col-xs-10 fix-label-lg">
                                    <label class="lb">営業許可業種3</label>
                                    <input type="text" class="prop" data-prop="nm_eigyo_kyoka_gyoshu03">
                                </div>
                                <div class="control col-xs-2"></div>
                            </div>
                            <div class="row">
                                <div class="control-label col-xs-10 fix-label-lg">
                                    <label class="lb">営業許可業種4</label>
                                    <input type="text" class="prop" data-prop="nm_eigyo_kyoka_gyoshu04">
                                </div>
                                <div class="control col-xs-2"></div>
                            </div>
                            <div class="row">
                                <div class="control-label col-xs-10 fix-label-lg">
                                    <label class="lb">営業許可業種5</label>
                                    <input type="text" class="prop" data-prop="nm_eigyo_kyoka_gyoshu05">
                                </div>
                                <div class="control col-xs-2"></div>
                            </div>
                        </div>
                         <div class="col-xs-4 control single-target" style="height: 122px;">
                            <div class="col-xs-12 col-zr">
                                <label>フリー入力</label>
                            </div>
                            <textarea class="textarea-height fit-25" data-prop="nm_free_sonota"></textarea>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>