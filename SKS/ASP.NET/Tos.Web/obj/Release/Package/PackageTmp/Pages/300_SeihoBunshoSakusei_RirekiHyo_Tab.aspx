<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="300_SeihoBunshoSakusei_RirekiHyo_Tab.aspx.cs" Inherits="Tos.Web.Pages._300_SeihoBunshoSakusei_RirekiHyo_Tab" %>

<%@ MasterType VirtualPath="~/Site.Master" %>
<%--created from 【SearchDialog(Ver2.1)】 Template--%>

<style>
    #_300_SeihoBunshoSakusei_RirekiHyo_Tab textarea {
        width: 100%;
        resize: none;
        height: 100%;
        float: left;
        margin: 0px;
        border: 0px;
        padding: 3px;
        min-height: 100px;
    }
    #_300_SeihoBunshoSakusei_RirekiHyo_Tab td {
        vertical-align: top;
        min-height: 100px;
    }
        #_300_SeihoBunshoSakusei_RirekiHyo_Tab td label {
            padding-left: 5px;
            padding-right: 5px;
        }
        #_300_SeihoBunshoSakusei_RirekiHyo_Tab td.text-area {
            padding: 0px;
        }
    #_300_SeihoBunshoSakusei_RirekiHyo_Tab .part.roof {
        margin-right: 0px;
        height: 100%;
        margin-bottom: 0px;
    }

    #_300_SeihoBunshoSakusei_RirekiHyo_Tab .dt-vscroll .datatable {
        margin-top: -1px!important;
    }

    #_300_SeihoBunshoSakusei_RirekiHyo_Tab table.datatable {
        /*width: auto;*/
    }
</style>
<script type="text/javascript">

    /**
     * 検索ダイアログのレイアウト構造に対応するオブジェクトを定義します。
     */
    var _300_SeihoBunshoSakusei_RirekiHyo_Tab = {
        options: {
            skip: 0,                                // TODO:先頭からスキップするデータ数を指定します。
            top: App.settings.base.dialogDataTakeCount   // TODO:取得するデータ数を指定します。
        },
        urls: {
            search: "../api/_300_SeihoBunshoSakusei_RirekiHyo_Tab"
        },
        param: {
            no_seiho: "",
            mode: ""
        },
        name: "【商品開発履歴表】"
    };

    /**
     * 検索ダイアログの初期化処理を行います。
     */
    _300_SeihoBunshoSakusei_RirekiHyo_Tab.initialize = function () {
        var element = $("#_300_SeihoBunshoSakusei_RirekiHyo_Tab");

        //element.on("change", ":input", _300_SeihoBunshoSakusei_RirekiHyo_Tab.change);
        _300_SeihoBunshoSakusei_RirekiHyo_Tab.element = element;

        element.find(".alert-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).alertTitle.text);
        element.find(".info-message").attr("title", App.ui.pagedata.lang(App.ui.page.lang).infoTitle.text);

        _300_SeihoBunshoSakusei_RirekiHyo_Tab.notifyInfo =
             App.ui.notify.info(element, {
                 container: "#_300_SeihoBunshoSakusei_RirekiHyo_Tab .dialog-slideup-area .info-message",
                 bodyContainer: ".modal-body",
                 show: function () {
                     element.find(".info-message").show();
                 },
                 clear: function () {
                     element.find(".info-message").hide();
                 }
             });
        _300_SeihoBunshoSakusei_RirekiHyo_Tab.notifyAlert =
            App.ui.notify.alert(element, {
                container: "#_300_SeihoBunshoSakusei_RirekiHyo_Tab .dialog-slideup-area .alert-message",
                bodyContainer: ".modal-body",
                show: function () {
                    element.find(".alert-message").show();
                },
                clear: function () {
                    element.find(".alert-message").hide();
                }
            });

        _300_SeihoBunshoSakusei_RirekiHyo_Tab.validator = element.validation(App.validation(_300_SeihoBunshoSakusei_RirekiHyo_Tab.options.validations, {
            success: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    $(item.element).removeClass("has-error");
                    //_300_SeihoBunshoSakusei_RirekiHyo_Tab.setColValidStyle(item.element);

                    App.ui.page.notifyAlert.remove(item.element);
                }
            },
            fail: function (results, state) {
                var i = 0, l = results.length,
                    item, $target;

                for (; i < l; i++) {
                    item = results[i];
                    //_300_SeihoBunshoSakusei_RirekiHyo_Tab.setColInvalidStyle(item.element);
                    $(item.element).addClass("has-error");
                    if (state && state.suppressMessage) {
                        continue;
                    }

                    App.ui.page.notifyAlert.message(_300_SeihoBunshoSakusei_RirekiHyo_Tab.name + item.message, item.element).show();
                }
            },
            always: function (results) {
                //TODO: バリデーションの成功、失敗に関わらない処理が必要な場合はここに記述します。
            }
        }));

        var table = element.find(".datatable"),
            datatable = table.dataTable({
            height: 100,
            resize: true,
            onchange: _300_SeihoBunshoSakusei_RirekiHyo_Tab.change,
            onselect: _300_SeihoBunshoSakusei_RirekiHyo_Tab.select
        });

        _300_SeihoBunshoSakusei_RirekiHyo_Tab.dataTable = datatable;
        _300_SeihoBunshoSakusei_RirekiHyo_Tab.search();
        page.finishLoading("TAB_Initilize", "TAB9", 5);
    };


    _300_SeihoBunshoSakusei_RirekiHyo_Tab.change = function (e, row) {
        var target = $(e.target),
            property = target.attr("data-prop"),
            id = row.element.attr("data-key"),
            entity = _300_SeihoBunshoSakusei_RirekiHyo_Tab.data.entry(id);

        _300_SeihoBunshoSakusei_RirekiHyo_Tab.validator.validate({
            targets: target,
            state: {
                tbody: row,
                isGridValidation: true
            }
        }).then(function () {
            page.setIsChangeValue();
            entity[property] = $.trim(target.val());
            _300_SeihoBunshoSakusei_RirekiHyo_Tab.data.update(entity);
        });
    }

    /**
    * Get data set when save
    */
    _300_SeihoBunshoSakusei_RirekiHyo_Tab.getSaveData = function () {
        var changeset = _300_SeihoBunshoSakusei_RirekiHyo_Tab.data.getChangeSet();
        $.each(changeset.created, function (ind, item) {
            item.no_seq_seiho_rireki = ind + 1;
        });
        return changeset;
    }

    // Check all validate in the tab
    _300_SeihoBunshoSakusei_RirekiHyo_Tab.validateAll = function (filter) {
        var targets = _300_SeihoBunshoSakusei_RirekiHyo_Tab.element.find(":input");
        var validateList = [];
        targets.each(function (ind, item) {
            validateList.push(
                _300_SeihoBunshoSakusei_RirekiHyo_Tab.validator.validate({
                    targets: $(item),
                    filter: filter,
                    state: {
                        suppressMessage: false
                    }
                })
            )
        });
        return App.async.all(validateList);
        
    }

    /**
     * 単項目要素をエラーのスタイルに設定します。
     * @param target 設定する要素
     */
    _300_SeihoBunshoSakusei_RirekiHyo_Tab.setColInvalidStyle = function (target) {
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
    _300_SeihoBunshoSakusei_RirekiHyo_Tab.setColValidStyle = function (target) {
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
     * 検索ダイアログ　バリデーションルールを定義します。
     */
    _300_SeihoBunshoSakusei_RirekiHyo_Tab.options.validations = {
        no_seiho_rireki_naiyo: {
            rules: {
                maxbytelength: 1000
            },
            options: {
                name: "内容",
                byte: 500
            },
            messages: {
                maxbytelength: App.messages.base.maxbytelength
            }
        }
    };

    /**
     * 検索ダイアログの検索処理を実行します。
     */
    _300_SeihoBunshoSakusei_RirekiHyo_Tab.search = function () {
        var element = _300_SeihoBunshoSakusei_RirekiHyo_Tab.element,
            url = _300_SeihoBunshoSakusei_RirekiHyo_Tab.urls.search;

        _300_SeihoBunshoSakusei_RirekiHyo_Tab.options.filter = _300_SeihoBunshoSakusei_RirekiHyo_Tab.createFilter();
        
        $.ajax(App.ajax.webapi.get(url, _300_SeihoBunshoSakusei_RirekiHyo_Tab.options.filter))
        .done(function (result) {

            _300_SeihoBunshoSakusei_RirekiHyo_Tab.bind(result);
        }).always(function () {

        });
    };

    /**
     * 検索ダイアログの検索条件を組み立てます
     */
    _300_SeihoBunshoSakusei_RirekiHyo_Tab.createFilter = function () {
        var param = _300_SeihoBunshoSakusei_RirekiHyo_Tab.param;
        return {
            no_seiho: param.no_seiho,
            kbn_haigo: App.settings.app.kbnHin.haigo,
            mode: param.mode == "new" ? 1 : 2
        }
    };

    /**
     * 検索ダイアログの一覧にデータをバインドします。
     */
    _300_SeihoBunshoSakusei_RirekiHyo_Tab.bind = function (data) {
        var data = data ? data.Items ? data.Items : data : [],
            subRow = [],
            no_seiho,
            isfirstRow = true;
        _300_SeihoBunshoSakusei_RirekiHyo_Tab.data = App.ui.page.dataSet();
        for (var i = 0 ; i < data.length; i++) {
            var item = data[i];
            if (no_seiho != item.no_seiho && subRow.length) {
                _300_SeihoBunshoSakusei_RirekiHyo_Tab.bindToRow(subRow, isfirstRow);
                isfirstRow = false;
                subRow = [];
            }
            subRow.push(item);
            no_seiho = item.no_seiho;
        }
        if (subRow.length) {
            _300_SeihoBunshoSakusei_RirekiHyo_Tab.bindToRow(subRow, isfirstRow);
            isfirstRow = false;
        }
        page.finishLoading("TAB_Bind", "TAB9", 5);
    };

    _300_SeihoBunshoSakusei_RirekiHyo_Tab.expandSeihin = function (nm_seihin, su_code_standard) {
        if (!nm_seihin) {
            return "";
        }
        var breakLine = "@@BREAK-LINES@@";
        nm_seihin = nm_seihin.replace(breakLine, "");
        var lstNmSeihin = nm_seihin.split(breakLine);
        $.each(lstNmSeihin, function (ind, seihin) {
            lstNmSeihin[ind] = App.common.fillString(Number(seihin), su_code_standard);
        })
        return lstNmSeihin.join("<br/>");
    }

    _300_SeihoBunshoSakusei_RirekiHyo_Tab.expandBusho = function (nm_busho) {
        if (!nm_busho) {
            return "";
        }
        nm_busho = nm_busho.replace("@@BREAK-LINES@@", "");
        return nm_busho.replace(/@@BREAK-LINES@@/g, "<br/>");
    }

    _300_SeihoBunshoSakusei_RirekiHyo_Tab.bindToRow = function (data, isFocus) {
        var element = _300_SeihoBunshoSakusei_RirekiHyo_Tab.element,
            mode = _300_SeihoBunshoSakusei_RirekiHyo_Tab.param.mode,
            dataSet = _300_SeihoBunshoSakusei_RirekiHyo_Tab.data;

        if (data.length) {
            _300_SeihoBunshoSakusei_RirekiHyo_Tab.dataTable.dataTable("addRow", function (tbody) {
                for (var i = 0; i < data.length; i++) {
                    var item = data[i];
                    if (i === 0) {
                        if (!item.ts) {
                            dataSet.add(item);
                        } else {
                            dataSet.attach(item);
                            dataSet.update(item);
                        }
                        tbody.form().bind(item);
                        tbody.findP("nm_busho").html(_300_SeihoBunshoSakusei_RirekiHyo_Tab.expandBusho(item.nm_busho));
                        tbody.findP("nm_seihin").html(_300_SeihoBunshoSakusei_RirekiHyo_Tab.expandSeihin(item.nm_seihin, item.su_code_standard));
                    } else {
                        kaishaEXT = tbody.find(".kaisha-ext.item-tmpl").clone().removeClass("item-tmpl").show();
                        kaishaEXT.findP("nm_busho").html(_300_SeihoBunshoSakusei_RirekiHyo_Tab.expandBusho(item.nm_busho));
                        kaishaEXT.findP("nm_kaisha").text(item.nm_kaisha);
                        kaishaEXT.appendTo(tbody);
                    }
                }
                tbody.find(".multi-row").attr("rowspan", data.length);
                if (_300_SeihoBunshoSakusei_RirekiHyo_Tab.param.sub_mode == page.options.mode.view) {
                    tbody.findP("no_seiho_rireki_naiyo").prop("disabled", true);
                }
                return tbody;
            }, isFocus);
        }
    };

    _300_SeihoBunshoSakusei_RirekiHyo_Tab.autoHeightTextArea = function () {
        var target = _300_SeihoBunshoSakusei_RirekiHyo_Tab.element.find("textarea");
        $.each(target, function (ind, item) {
            item = $(item);
            item.css("height", item.closest("td").height());
        })
    }

    /**
     * On row selected
     */
    _300_SeihoBunshoSakusei_RirekiHyo_Tab.select = function (e, row) {
        if (row && row.element && !row.element.hasClass("selected-row")) {
            _300_SeihoBunshoSakusei_RirekiHyo_Tab.element.find(".selected-row").removeClass("selected-row");
            row.element.find("tr").addClass("selected-row");
        }
    };

    
</script>

<div class="tab-pane" id="_300_SeihoBunshoSakusei_RirekiHyo_Tab">
    <div class="sub-tab-content">
        <div class="part roof full-height">
            <div class="part-content full-height">
                <table class="datatable">
                    <thead>
                        <tr>
                            <th rowspan="2" style="width: 200px;">製法番号</th>
                            <th colspan="2" style="width: 500px;">伝送先／配布先</th>
                            <th rowspan="2" style="width: 150px;">製品コード</th>
                            <th rowspan="2" style="">内容（改版の場合は改良点を記載）</th>
                        </tr>
                        <tr>
                            <th style="width: 250px;">会社</th>
                            <th style="width: 250px;">工場</th>
                        </tr>
                    </thead>
                    <tbody class="item-tmpl" style="display: none">
                        <tr>
                            <td class="multi-row">
                                <label data-prop="no_seiho" class="full-width"></label>
                            </td>
                            <td>
                                <label data-prop="nm_kaisha"></label>
                            </td>
                            <td>
                                <label data-prop="nm_busho"></label>
                            </td>
                            <td class="multi-row">
                                <label data-prop="nm_seihin" class="full-width center"></label>
                            </td>
                            <td class="multi-row text-area">
                                <textarea data-prop="no_seiho_rireki_naiyo" class="fit-30"></textarea></td>
                        </tr>
                        <tr class="kaisha-ext item-tmpl" style="display: none">
                            <td>
                                <label data-prop="nm_kaisha"></label>
                            </td>
                            <td>
                                <label data-prop="nm_busho"></label>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
