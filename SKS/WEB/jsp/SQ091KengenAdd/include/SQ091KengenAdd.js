
//画面設定(BODYのﾛｰﾄﾞ後にﾀｲﾄﾙの設定が行えないため、BODYのﾛｰﾄﾞ前に行う)
funInitScreen(ConKengenAddId);

//========================================================================================
// 初期表示処理
// 作成者：M.Jinbo
// 作成日：2009/04/09
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var i;

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //画面情報を取得・設定
    if (funGetInfo(1) == false) {
        return false;
    }

    //画面の初期化
    funClear();

    //戻り値の初期化
    window.returnValue = "";

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    return true;

}

//========================================================================================
// コンボボックス連動処理
// 作成者：M.Jinbo
// 作成日：2009/04/09
// 引数  ：①mode     ：モード
//           1：画面、2：機能、3：参照可能データ
// 概要  ：下位のコンボボックスを生成する
//========================================================================================
function funChangeCmb(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    switch (mode) {
        case 2:    //機能
            //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
            funClearSelect(frm.ddlKino, 2);
            funClearSelect(frm.ddlData, 2);

            //ｺﾝﾎﾞﾎﾞｯｸｽ作成
            funCreateComboBox(frm.ddlKino, mode);
            break;
        case 3:    //参照可能ﾃﾞｰﾀ
            //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
            funClearSelect(frm.ddlData, 2);

            //ｺﾝﾎﾞﾎﾞｯｸｽ作成
            funCreateComboBox(frm.ddlData, mode);
            break;
    }

    return true;

}

//========================================================================================
// クリアボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/04/09
// 引数  ：なし
// 概要  ：画面を初期化する
//========================================================================================
function funClear() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //画面の初期化
    frm.reset();

    //ｺﾝﾎﾞﾎﾞｯｸｽの再設定
    funClearSelect(frm.ddlKino, 2);
    funClearSelect(frm.ddlData, 2);

    //ﾌｫｰｶｽ設定
    frm.txtKino.focus();

    return true;

}

//========================================================================================
// 採用ボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/04/09
// 概要  ：選択データを呼び出し元画面に返す
//========================================================================================
function funSelect() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var retVal;

    //必須項目が入力されていない場合
    if (frm.txtKino.value == "") {
        funErrorMsgBox("機能名" + E000003);
        return false;
    }
    if (frm.ddlGamen.selectedIndex == 0) {
        funErrorMsgBox("画面" + E000003);
        return false;
    }
    if (frm.ddlKino.selectedIndex == 0) {
        funErrorMsgBox("機能" + E000003);
        return false;
    }
    if (frm.ddlData.selectedIndex == 0) {
        funErrorMsgBox("参照可能データ" + E000003);
        return false;
    }

    //ｼﾝｸﾞﾙｸｫｰﾃｰｼｮﾝが入力されている場合
    if (frm.txtKino.value.indexOf("'") != -1) {
        funErrorMsgBox("機能名" + E000004);
        return false;
    }
    if (frm.txtBiko.value.indexOf("'") != -1) {
        funErrorMsgBox("備考" + E000004);
        return false;
    }

    //戻り値の設定
    retVal = frm.txtKino.value + ConDelimiter;
    retVal += frm.ddlGamen.options[frm.ddlGamen.selectedIndex].value + ConDelimiter;
    retVal += frm.ddlKino.options[frm.ddlKino.selectedIndex].value + ConDelimiter;
    retVal += frm.ddlData.options[frm.ddlData.selectedIndex].value + ConDelimiter;
    retVal += frm.txtBiko.value;
    window.returnValue = retVal;

    //画面を閉じる
    close(self);

}

//========================================================================================
// 終了ボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/04/09
// 概要  ：画面を閉じる
//========================================================================================
function funClose() {

    window.returnValue = "";

    //画面を閉じる
    close(self);

}

//========================================================================================
// 画面情報取得処理
// 作成者：M.Jinbo
// 作成日：2009/04/09
// 引数  ：①mode  ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：ユーザのセッション情報、コンボボックスの情報を取得する
//========================================================================================
function funGetInfo(mode) {

    var opener = window.dialogArguments;      //引数の取得
    var opener_form = opener.document.forms(0);
    var frm = document.frm00;                 //ﾌｫｰﾑへの参照
    var XmlId = "JSP9030";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP9030, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //ﾕｰｻﾞ情報表示
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //ｺﾝﾎﾞﾎﾞｯｸｽの作成
    funCreateComboBox(frm.ddlGamen, 1);

    return true;

}

//========================================================================================
// XMLファイルに書き込み
// 作成者：M.Jinbo
// 作成日：2009/04/09
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
        if (XmlId.toString() == "JSP9030") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
            }
        }
    }

    return true;

}

//========================================================================================
// コンボボックス作成処理
// 作成者：M.Jinbo
// 作成日：2009/04/09
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②mode     ：モード
//           1：画面、2：機能、3：参照可能データ
// 概要  ：コンボボックスを作成する
//========================================================================================
function funCreateComboBox(obj, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var atbName;
    var atbCd;
    var reccnt;
    var i;

    //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
    funClearSelect(obj, 2);

    //件数の取得
    reccnt = funGetLengthLocal(xmlAuthority, mode);

    if (reccnt == 0) {
        return true;
    }

    switch (mode) {
        case 1:    //画面
            atbName = "nm_gamen";
            atbCd = "id_gamen";
            break;
        case 2:    //機能
            atbName = "nm_kino";
            atbCd = "id_kino";
            break;
        case 3:    //参照可能データ
            atbName = "nm_data";
            atbCd = "id_data";
            break;
    }

    for (i = 0; i < reccnt; i++) {
        if (funXmlReadLocal(xmlAuthority, atbCd, i, mode) != "") {
            objNewOption = document.createElement("option");
            obj.options.add(objNewOption);
            objNewOption.innerText = funXmlReadLocal(xmlAuthority, atbName, i, mode);
            objNewOption.value = funXmlReadLocal(xmlAuthority, atbCd, i, mode) + ConDelimiter + funXmlReadLocal(xmlAuthority, atbName, i, mode);
        }
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽのﾃﾞﾌｫﾙﾄ表示
    obj.selectedIndex = 0;

    return true;
}

//========================================================================================
// XML読み出し処理
// 作成者：M.Jinbo
// 作成日：2009/04/09
// 引数  ：①xmlRead      ：読み出し元XML
//       ：②AttributeName：項目名
//       ：③RowNo        ：行番号
//       ：④mode         ：モード
//           1：画面、2：機能、3：参照可能データ
// 戻り値：取得値
// 概要  ：XMLファイルから指定項目の値を取得する。
//========================================================================================
function funXmlReadLocal(xmlRead, AttributeName, RowNo, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var objNode;
    var GIndex;
    var KIndex;
    var retVal;

    //ﾓｰﾄﾞ≠1で、画面ｺﾝﾎﾞﾎﾞｯｸｽが選択されていない場合
    if (mode != 1 && frm.ddlGamen.selectIndex == 0) {
        return "";
    }

    try {
        if (xmlRead.xml == "") {
            return "";
        }

        switch (mode) {
            case 1:    //画面
                objNode = xmlRead.documentElement.childNodes;
                break;
            case 2:    //機能
                GIndex = frm.ddlGamen.selectedIndex - 1;
                objNode = xmlRead.documentElement.childNodes.item(GIndex).childNodes;
                break;
            case 3:    //参照可能データ
                GIndex = frm.ddlGamen.selectedIndex - 1;
                KIndex = frm.ddlKino.selectedIndex - 1;
                objNode = xmlRead.documentElement.childNodes.item(GIndex).childNodes.item(KIndex).childNodes;
                break;
        }

        //子ﾉｰﾄﾞの数をﾁｪｯｸする
        if (objNode.length == 0) {
            return "";
        }
        //属性値を取得
        return objNode.item(RowNo).getAttributeNode(AttributeName).value;

    } catch (e) {
        return "";
    }

}

//========================================================================================
// XMLレコード件数取得処理
// 作成者：M.Jinbo
// 作成日：2009/04/09
// 引数  ：①xmlData ：件数取得XML
//       ：②mode    ：モード
//           1：画面、2：機能、3：参照可能データ
// 戻り値：レコード件数
// 概要  ：XMLファイルレコード件数を取得する
//========================================================================================
function funGetLengthLocal(xmlData, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var reccnt;
    var obj;
    var GIndex;
    var KIndex;

    reccnt = 0;

    //ﾓｰﾄﾞ≠1で、画面ｺﾝﾎﾞﾎﾞｯｸｽが選択されていない場合
    if (mode != 1 && frm.ddlGamen.selectIndex == 0) {
        return reccnt;
    }

    if (xmlData.xml == "") {
        return reccnt;

    } else {
        switch (mode) {
            case 1:    //画面
                obj = xmlData.documentElement;
                break;
            case 2:    //機能
                GIndex = frm.ddlGamen.selectedIndex - 1;
                obj = xmlData.documentElement.childNodes.item(GIndex);
                break;
            case 3:    //参照可能データ
                GIndex = frm.ddlGamen.selectedIndex - 1;
                KIndex = frm.ddlKino.selectedIndex - 1;
                obj = xmlData.documentElement.childNodes.item(GIndex).childNodes.item(KIndex);
                break;
        }

        try {
            //子ﾉｰﾄﾞの存在ﾁｪｯｸ
            if (obj.hasChildNodes()) {
                //存在する場合
                reccnt = obj.childNodes.length;
            } else {
                //存在しない場合
                reccnt = 0;
            }

        } catch (e) {

        }
    }

    return reccnt;

}

