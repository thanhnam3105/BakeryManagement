//========================================================================================
// 初期表示処理
// 作成者：M.Jinbo
// 作成日：2009/05/19
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //画面設定
    funInitScreen(ConTankaBudomariId);

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //画面情報を取得・設定
    if (funGetInfo(1) == false) {
        return false;
    }

    //画面の初期化
    funClearList();

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    frm.ddlKaisha.focus();

    return true;

}

//========================================================================================
// 検索ボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/05/19
// 引数  ：なし
// 概要  ：原料データの検索を行う
//========================================================================================
function funSearch() {

    //次ﾍﾟｰｼﾞの設定
    funSetCurrentPage(1);

    //指定ﾍﾟｰｼﾞのﾃﾞｰﾀ取得
    funSearchData();
}

//========================================================================================
// 検索処理
// 作成者：M.Jinbo
// 作成日：2009/05/19
// 引数  ：なし
// 概要  ：原料データの検索を行う
//========================================================================================
function funSearchData() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP1320";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA790");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA790I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA790O);
    var PageCnt;
    var RecCnt;
    var ListMaxRow;

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //選択行の初期化
    funSetCurrentRow("");

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        //一覧のｸﾘｱ
        funClearList();
        funClearRunMessage();
        return false;
    }

    //検索条件に一致する原料ﾃﾞｰﾀを取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP1320, xmlReqAry, xmlResAry, 1) == false) {
        //HTMLの出力
        divTableList.innerHTML = funCreateListHTML(xmlResAry[2]);
        funClearList();
        return false;
    }

    //HTMLの出力
    divTableList.innerHTML = funCreateListHTML(xmlResAry[2]);

    //ﾃﾞｰﾀ件数、ﾍﾟｰｼﾞﾘﾝｸの設定
    spnRecInfo.style.display = "block";
    RecCnt = funXmlRead(xmlResAry[2], "max_row", 0);
    spnRecCnt.innerText = RecCnt;
    ListMaxRow = funXmlRead(xmlResAry[2], "list_max_row", 0);
    spnRowMax.innerText = ListMaxRow;
    PageCnt = Math.ceil(RecCnt / ListMaxRow);
    funCreatePageLink(funGetCurrentPage(), PageCnt, "divPage", "tblList");
    spnCurPage.innerText = funGetCurrentPage() + "／" + PageCnt + "ページ";

    //ﾃﾞｰﾀ件数のﾁｪｯｸ
    if (funGetLength(xmlResAry[2]) > 0 && funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
        //表示
        tblList.style.display = "block";
    } else {
        //非表示
        tblList.style.display = "none";

        //一覧のｸﾘｱ
        funClearList();

        //処理中ﾒｯｾｰｼﾞ非表示
        funClearRunMessage();
    }

    return true;

}

//========================================================================================
// 一覧HTML生成処理
// 作成者：M.Jinbo
// 作成日：2009/05/19
// 引数  ：①xmlObj ：XMLオブジェクト
// 戻り値：作成したHTML
// 概要  ：一覧のHTMLを作成する
//========================================================================================
// 更新者：M.Jinbo
// 更新日：2009/05/19
// 内容  ：単価の桁数を整数8桁、小数2桁に変更(課題表№17)
//========================================================================================
function funCreateListHTML(xmlObj) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var i;
    var OutputHtml;
    var ListMaxCol;
    var HeadAry = new Array();

    //工場件数の取得
    ListMaxCol = funXmlRead(xmlObj, "cnt_kojo", 0);

    //工場名の退避
    for (i = 0; i <= ListMaxCol; i++) {
        HeadAry[i] = funXmlRead(xmlObj, "disp_val" + (i+1), 0);
    }

    //結果XMLより先頭行(工場名称格納行)の削除
    funSetCurrentRow(0);
    funSelectRowDelete(xmlObj);
    funSetCurrentRow("");

    //一覧のHTMLを作成
    OutputHtml = ""
    OutputHtml += "<table id=\"dataTable\" name=\"dataTable\" cellspacing=\"0\" width=\"" + (310 + (85 * ListMaxCol)) + "px\">";
    OutputHtml += "    <colgroup>";
    OutputHtml += "        <col style=\"width:30px;\"/>";
    OutputHtml += "        <col style=\"width:80px;\"/>";
    OutputHtml += "        <col style=\"width:200px;\"/>";
    for (i = 0; i < ListMaxCol; i++) {
        //工場件数分、列を生成する
        OutputHtml += "        <col style=\"width:85px;\"/>";
    }
    OutputHtml += "    </colgroup>";
    OutputHtml += "    <thead class=\"rowtitle\">";
    OutputHtml += "        <tr style=\"top:expression(offsetParent.scrollTop);position:relative;\">";
    OutputHtml += "            <th class=\"columntitle\" width=\"30\">&nbsp;</th>";
    //原料区分を判定
    if (frm.rdoGenryoKbn[0].checked) {
        //原料の場合
        OutputHtml += "            <th class=\"columntitle\" width=\"80\">原料CD</th>";
        OutputHtml += "            <th class=\"columntitle\" width=\"200\">原料名</th>";
    } else {
        //資材の場合
        OutputHtml += "            <th class=\"columntitle\" width=\"80\">資材CD</th>";
        OutputHtml += "            <th class=\"columntitle\" width=\"200\">資材名</th>";
    }
    for (i = 0; i < ListMaxCol; i++) {
        //工場件数分、列を生成する
        OutputHtml += "            <th class=\"columntitle\" width=\"85\">" + HeadAry[i] + "</th>";
    }
    OutputHtml += "        </tr>";
    OutputHtml += "    </thead>";
    OutputHtml += "    <tbody>";
    OutputHtml += "        <table class=\"detail\" id=\"tblList\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" datasrc=\"#xmlSA790O\" datafld=\"rec\" style=\"width:" + (310 + (85 * ListMaxCol)) + "px;display:none\">";
    OutputHtml += "            <tr class=\"disprow\">";
    OutputHtml += "                <td class=\"column\" width=\"32\" align=\"right\"><span datafld=\"no_row\"></span></td>";
    OutputHtml += "                <td class=\"column\" width=\"83\" align=\"left\"><span datafld=\"cd_genryo\"></span></td>";
    OutputHtml += "                <td class=\"column\" width=\"202\" align=\"left\"><span datafld=\"nm_genryo\"></span></td>";
    for (i = 1; i <= ListMaxCol; i++) {
        //工場件数分、列を生成する
        OutputHtml += "                <td class=\"column\" id=\"lblCol\" width=\"87\" align=\"right\"><span datafld=\"disp_val" + i + "\"></span></td>";
    }
    OutputHtml += "            </tr>";
    OutputHtml += "        </table>";
    OutputHtml += "    </table>";
    OutputHtml += "</table>";

    //作成したHTMLを返す
    return OutputHtml;

}

//========================================================================================
// 一覧クリア処理
// 作成者：M.Jinbo
// 作成日：2009/05/19
// 引数  ：なし
// 概要  ：一覧の情報を初期化する
//========================================================================================
function funClearList() {

    //一覧のｸﾘｱ
    xmlSA790O.src = "";
    tblList.style.display = "none";
    spnRecCnt.innerText = "0";
    funSetCurrentRow("");
    funCreatePageLink(1, 1, "divPage", "tblList");
    funClearCurrentRow(tblList);
    funSetCurrentPage(1);
    spnRecInfo.style.display = "none";

}

//========================================================================================
// 次画面遷移処理
// 作成者：M.Jinbo
// 作成日：2009/05/19
// 概要  ：次画面に遷移する
//========================================================================================
function funNext(mode) {

    var wUrl;

    //遷移先判定
    switch (mode) {
        case 0:    //ﾏｽﾀﾒﾆｭｰ
            wUrl = "../SQ030MstMenu/SQ030MstMenu.jsp";
            break;
    }

    //遷移
    funUrlConnect(wUrl, ConConectPost, document.frm00);

    return true;
}

//========================================================================================
// 画面情報取得処理
// 作成者：M.Jinbo
// 作成日：2009/05/19
// 引数  ：①mode  ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：ユーザのセッション情報、コンボボックスの情報を取得する
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP1310";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA140");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA140I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA140O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP1310, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //ﾕｰｻﾞ情報表示
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //ｺﾝﾎﾞﾎﾞｯｸｽの作成
    funCreateComboBox(frm.ddlKaisha, xmlResAry[2], 1);
    funDefaultIndex(frm.ddlKaisha, 1);

    return true;

}

//========================================================================================
// XMLファイルに書き込み
// 作成者：M.Jinbo
// 作成日：2009/05/19
// 引数  ：①XmlId  ：XMLID
//       ：②reqAry ：機能ID別送信XML(配列)
//       ：③Mode   ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：なし
// 概要  ：機能IDごとのXMLファイルに引数を設定する
//========================================================================================
function funReadyOutput(XmlId, reqAry, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var i;

    for (i = 0; i < reqAry.length; i++) {
        //画面初期表示
        if (XmlId.toString() == "JSP1310") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA140
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTankaBudomari, 0);
                    break;
            }

        //検索ﾎﾞﾀﾝ押下
        } else if (XmlId.toString() == "JSP1320"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA790
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_genryo", frm.txtGenryoCd.value, 0);
                    funXmlWrite(reqAry[i], "nm_genryo", frm.txtGenryoName.value, 0);
                    if (frm.rdoGenryoKbn[0].checked) {
                        funXmlWrite(reqAry[i], "kbn_data", "0", 0);
                    } else if (frm.rdoGenryoKbn[1].checked) {
                        funXmlWrite(reqAry[i], "kbn_data", "1", 0);
                    } else {
                        funXmlWrite(reqAry[i], "kbn_data", "", 0);
                    }
                    if (frm.rdoTaisho[0].checked) {
                        funXmlWrite(reqAry[i], "item_output", "0", 0);
                    } else if (frm.rdoTaisho[1].checked) {
                        funXmlWrite(reqAry[i], "item_output", "1", 0);
                    } else {
                        funXmlWrite(reqAry[i], "item_output", "", 0);
                    }
                    funXmlWrite(reqAry[i], "no_page", funGetCurrentPage(), 0);
                    break;
            }
        }
    }

    return true;

}

//========================================================================================
// コンボボックス作成処理
// 作成者：M.Jinbo
// 作成日：2009/05/19
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②xmlData  ：設定XML
//       ：③mode     ：モード
// 概要  ：コンボボックスを作成する
//========================================================================================
function funCreateComboBox(obj, xmlData, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var reccnt;                  //設定XMLの件数
    var objNewOption;
    var atbName;
    var atbCd;
    var i;

    //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
    funClearSelect(obj, 1);

    //件数取得
    reccnt = funGetLength(xmlData);

    //ﾃﾞｰﾀが存在しない場合
    if (reccnt == 0) {
        //空白行の追加
        funClearSelect(obj, 2);
        return true;
    }

    //属性名の取得
    switch (mode) {
        case 1:    //会社ﾏｽﾀ
            atbName = "nm_kaisha";
            atbCd = "cd_kaisha";
            break;
    }

    for (i = 0; i < reccnt; i++) {
        if (funXmlRead(xmlData, atbCd, i) != "" && funXmlRead(xmlData, atbName, i) != "") {
            objNewOption = document.createElement("option");
            obj.options.add(objNewOption);
            objNewOption.innerText = funXmlRead(xmlData, atbName, i);
            objNewOption.value = funXmlRead(xmlData, atbCd, i);
        }
    }

    //製造担当会社が未登録の場合
    if (obj.length == 0) {
        //空白行の追加
        funClearSelect(obj, 2);
        return true;
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽのﾃﾞﾌｫﾙﾄ表示
    obj.selectedIndex = 0;

    return true;
}

//========================================================================================
// デフォルト値選択処理
// 作成者：M.Jinbo
// 作成日：2009/05/19
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②mode     ：モード
// 概要  ：コンボボックスのデフォルト値を選択する
//========================================================================================
function funDefaultIndex(obj, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var selIndex;
    var i;

    for (i = 0; i < obj.length; i++) {
        switch (mode) {
            case 1:    //会社ｺﾝﾎﾞ
                if (obj.options[i].value == funXmlRead(xmlUSERINFO_O, "cd_kaisha", 0)) {
                    selIndex = i;
                }
                break;
        }
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽのｲﾝﾃﾞｯｸｽ指定
    obj.selectedIndex = selIndex;

    return true;
}

//========================================================================================
// 未登録工場の背景色変更処理
// 作成者：M.Jinbo
// 作成日：2009/05/19
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②mode     ：モード
// 概要  ：未登録工場の背景色を変更する
//========================================================================================
function funChangeHaishiColor(obj, mode) {

    var i;
    var j;
    var Idx;
    var reccnt = funGetLength(xmlSA790O);
    var ListMaxCol = funXmlRead(xmlSA790O, "cnt_kojo", 0);
    var val;

    //背景色の変更
    for (i = 0; i < reccnt; i++) {
        Idx = 1;
        for (j = ListMaxCol * i; j < ListMaxCol * (i+1); j++) {
            //ﾃﾞｰﾀの取得
            val = funXmlRead(xmlSA790O, "disp_val" + Idx, i);

            if (val == "") {
                //未登録の場合
                if (ListMaxCol == 1 && reccnt == 1) {
                    lblCol.style.backgroundColor = haishiRowColor;
                } else {
                    lblCol[j].style.backgroundColor = haishiRowColor;
                }
            } else {
                //登録されている場合
                if (ListMaxCol == 1 && reccnt == 1) {
                    lblCol.style.backgroundColor = deactiveSelectedColor;
                } else {
                    lblCol[j].style.backgroundColor = deactiveSelectedColor;
                }

                //ｶﾝﾏ編集を行う
                funXmlWrite(xmlSA790O, "disp_val" + Idx, funAddComma(val), i);
            }

            Idx += 1;
        }
    }

    return true;
}

//========================================================================================
// 選択行操作(ローカル版)
// 作成者：M.Jinbo
// 作成日：2009/05/19
// 引数  ：なし
// 戻り値：なし
// 概要  ：選択行の背景色を変更する。
//========================================================================================
function funChangeSelectRowColorLocal() {

    var i;
    var oSrc = event.srcElement, oTBL, oTR, oTD;
    var BeforeRow;
    var reccnt = funGetLength(xmlSA790O);
    var ListMaxCol = funXmlRead(xmlSA790O, "cnt_kojo", 0);

    if ( (oSrc.tagName != "TABLE") && (oSrc.tagName != "DIV") && (oSrc.tagName != "TR") ) {
        for ( oTBL = oSrc.parentElement; oTBL.tagName != "TABLE"; oTBL = oTBL.parentElement ) {}
        for ( oTR = oSrc.parentElement; oTR.tagName != "TR"; oTR = oTR.parentElement ) {}

        BeforeRow = (funGetCurrentRow() == "" ? 0 : funGetCurrentRow() / 1);

        //背景色を変更
        oTBL.rows(oTR.rowIndex).style.backgroundColor = activeSelectedColor;
        for (i = ListMaxCol * oTR.rowIndex; i < ListMaxCol * (oTR.rowIndex+1); i++) {
            if (ListMaxCol == 1 && reccnt == 1) {
                lblCol.style.backgroundColor = activeSelectedColor;
            } else {
                lblCol[i].style.backgroundColor = activeSelectedColor;
            }
        }

        if ((BeforeRow != oTR.rowIndex) && (BeforeRow + 1 <= oTBL.rows.length)){
            //背景色を戻す
            oTBL.rows(BeforeRow).style.backgroundColor = deactiveSelectedColor;

            //前回選択行の背景色が変更されている場合
            if (funGetCurrentRow().toString() != "") {
                Idx = 1;
                //前回選択行の背景色を元に戻す
                for (i = ListMaxCol * funGetCurrentRow(); i < ListMaxCol * (funGetCurrentRow()+1); i++) {
                    if (funXmlRead(xmlSA790O, "disp_val" + Idx, funGetCurrentRow()) == "") {
                        //未登録の場合
                        if (ListMaxCol == 1 && reccnt == 1) {
                            lblCol.style.backgroundColor = haishiRowColor;
                        } else {
                            lblCol[i].style.backgroundColor = haishiRowColor;
                        }
                    } else {
                        //登録されている場合
                        if (ListMaxCol == 1 && reccnt == 1) {
                            lblCol.style.backgroundColor = deactiveSelectedColor;
                        } else {
                            lblCol[i].style.backgroundColor = deactiveSelectedColor;
                        }
                    }

                    Idx += 1;
                }
            }
        }

        //ｶﾚﾝﾄ行の退避
        funSetCurrentRow(oTR.rowIndex);
    }

    return true;

}

//========================================================================================
// ページ遷移
// 作成者：M.Jinbo
// 作成日：2009/05/19
// 引数  ：①NextPage   ：次のページ番号
// 戻り値：なし
// 概要  ：指定ページの情報を表示する。
//========================================================================================
function funPageMove(NextPage) {

    //次ﾍﾟｰｼﾞの設定
    funSetCurrentPage(NextPage);

    //指定ﾍﾟｰｼﾞのﾃﾞｰﾀ取得
    funSearchData();
}

