//========================================================================================
// 初期表示処理
// 作成者：M.Jinbo
// 作成日：2009/04/09
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

    //画面設定
    funInitScreen(ConKengenMstId);

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //画面情報を取得・設定
    if (funGetInfo(1) == false) {
        return false;
    }

    //画面の初期化
    funClear();

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    return true;

}

//========================================================================================
// 権限コンボボックス選択処理
// 作成者：M.Jinbo
// 作成日：2009/04/09
// 引数  ：なし
// 概要  ：権限データの検索を行う
//========================================================================================
function funSearch() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP1120";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA160");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA160I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA160O);
    var PageCnt;
    var RecCnt;
    var ListMaxRow;

    if (frm.ddlKengen.selectedIndex == 0) {
        //画面の初期化
        funClear();
        return true;
    }

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //検索条件に一致する原料ﾃﾞｰﾀを取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP1120, xmlReqAry, xmlResAry, 1) == false) {
        tblList.style.display = "none";
        return false;
    }

    //権限名の設定
    frm.txtKengenName.value = funXmlRead(xmlResAry[2], "nm_kengen", 0);

    //ﾃﾞｰﾀ件数のﾁｪｯｸ
    if (funGetLength(xmlResAry[2]) > 0 && funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
        //画面、機能、参照可能ﾃﾞｰﾀ名の設定
        funSetName();

        //表示
        tblList.style.display = "block";
    } else {
        //非表示
        tblList.style.display = "none";

        //処理中ﾒｯｾｰｼﾞ非表示
        funClearRunMessage();
    }
    
    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    //ﾎﾞﾀﾝの制御
    frm.btnCheckUser.disabled = false;

    return true;

}

//========================================================================================
// 登録、更新、削除ボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/04/09
// 引数  ：①mode ：処理区分
//           1：登録、2：更新、3：削除
// 概要  ：表示データの登録、更新、削除処理を行う
//========================================================================================
function funDataEdit(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP1140";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA340");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA340I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA340O);
    var dspMsg;

    if (mode == 1) {
        dspMsg = I000002;
    } else if (mode == 2) {
        dspMsg = I000003;
    } else {
        dspMsg = I000004;
    }

    //確認ﾒｯｾｰｼﾞの表示
    if (funConfMsgBox(dspMsg) != ConBtnYes) {
        return false;
    }

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    frm.hidEditMode.value = mode;

    //XMLの初期化
    setTimeout("xmlSA340I.src = '../../model/SA340I.xml';", ConTimer);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //登録、更新、削除処理を実行する
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP1140, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //完了ﾒｯｾｰｼﾞの表示
    dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {
        //正常
        funInfoMsgBox(dspMsg);

        //画面情報を取得・設定
        if (funGetInfo(1) == false) {
            return false;
        }

        //画面の初期化
        funClear();
    }

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

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
    frm.btnCheckUser.disabled = true;

    //一覧のｸﾘｱ
    xmlSA160O.src = "";
    tblList.style.display = "none";
    funClearCurrentRow(tblList);

    //ｺﾝﾎﾞﾎﾞｯｸｽの再設定
    funCreateComboBox(frm.ddlKengen, xmlSA170O, 1);

    //ﾌｫｰｶｽ設定
    frm.ddlKengen.focus();

    return true;

}

//========================================================================================
// 次画面遷移処理
// 作成者：M.Jinbo
// 作成日：2009/04/09
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
// 権限機能追加画面起動処理
// 作成者：M.Jinbo
// 作成日：2009/04/09
// 引数  ：なし
// 概要  ：権限機能追加画面を起動する
//========================================================================================
function funOpenKengenAdd() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var retVal;
    var xmlBuff = new ActiveXObject("Microsoft.XMLDOM");

    //権限機能追加画面を起動する
    retVal = funOpenModalDialog("../SQ091KengenAdd/SQ091KengenAdd.jsp", this, "dialogHeight:430px;dialogWidth:650px;status:no;scroll:no");

    if (retVal != "") {
        //機能の追加
        funAddRecNode(xmlSA160O, "SA160");
        funXmlWrite(xmlSA160O, "nm_kino", retVal.split(ConDelimiter)[0], funGetLength(xmlSA160O)-1);
        funXmlWrite(xmlSA160O, "id_gamen", retVal.split(ConDelimiter)[1], funGetLength(xmlSA160O)-1);
        funXmlWrite(xmlSA160O, "gamen", retVal.split(ConDelimiter)[2], funGetLength(xmlSA160O)-1);
        funXmlWrite(xmlSA160O, "id_kino", retVal.split(ConDelimiter)[3], funGetLength(xmlSA160O)-1);
        funXmlWrite(xmlSA160O, "kino", retVal.split(ConDelimiter)[4], funGetLength(xmlSA160O)-1);
        funXmlWrite(xmlSA160O, "id_data", retVal.split(ConDelimiter)[5], funGetLength(xmlSA160O)-1);
        funXmlWrite(xmlSA160O, "data", retVal.split(ConDelimiter)[6], funGetLength(xmlSA160O)-1);
        funXmlWrite(xmlSA160O, "biko", retVal.split(ConDelimiter)[7], funGetLength(xmlSA160O)-1);

        //値を設定したXMLの再ﾛｰﾄﾞ
        xmlBuff.load(xmlSA160O);
        xmlSA160O.load(xmlBuff);

        if (tblList.style.display == "none") {
           tblList.style.display = "block";
        }
        funClearCurrentRow(tblList);
    }

    return true;

}

//========================================================================================
// 機能削除処理
// 作成者：M.Jinbo
// 作成日：2009/04/09
// 引数  ：なし
// 概要  ：選択行の機能を削除する
//========================================================================================
function funDelList() {

    //行が選択されていない場合
    if (funGetCurrentRow().toString() == "") {
        funErrorMsgBox(E000002);
        return false;
    }

    //指定された機能を削除する
    funSelectRowDelete(xmlSA160O);

    return true;

}

//========================================================================================
// 権限使用ユーザ確認ボタン処理
// 作成者：M.Jinbo
// 作成日：2009/04/09
// 引数  ：なし
// 概要  ：選択されている権限を使用しているユーザ数の表示を行う
//========================================================================================
function funCheckUser() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP1130";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA350");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA350I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA350O);

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //権限使用ﾕｰｻﾞ数を取得する
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP1130, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //ﾒｯｾｰｼﾞの表示
    funInfoMsgBox(funXmlRead(xmlSA350O, "su_user", 0) + I000010);

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    return true;

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

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP1110";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA170");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA170I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA170O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP1110, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //ﾕｰｻﾞ情報表示
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //権限関連の処理を行う
    funSaveKengenInfo();

    //ｺﾝﾎﾞﾎﾞｯｸｽの作成
    funCreateComboBox(frm.ddlKengen, xmlResAry[2], 1);

    return true;

}

//========================================================================================
// 権限関連処理
// 作成者：M.Jinbo
// 作成日：2009/04/09
// 概要  ：権限情報の退避、画面制御を行う
//========================================================================================
function funSaveKengenInfo() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var obj = xmlUSERINFO_O;
    var GamenId;
    var KinoId;
    var reccnt;
    var i;

    //詳細、新規原料、削除ﾎﾞﾀﾝの制御
    frm.btnInsert.disabled = true;
    frm.btnUpdate.disabled = true;
    frm.btnDelete.disabled = true;

    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        GamenId = funXmlRead(obj, "id_gamen", i);
        KinoId = funXmlRead(obj, "id_kino", i);

        //権限ﾏｽﾀﾒﾝﾃﾅﾝｽ
        if (GamenId.toString() == ConGmnIdKengenMst.toString()) {
            //編集
            if (KinoId.toString() == ConFuncIdEdit.toString()) {
                frm.btnInsert.disabled = false;
                frm.btnUpdate.disabled = false;
                frm.btnDelete.disabled = false;
            }
        }
    }

    return true;

}

//========================================================================================
// 画面、機能、参照可能データ名設定処理
// 作成者：M.Jinbo
// 作成日：2009/04/09
// 引数  ：なし
// 概要  ：権限に関係する項目の名称をXMLへ設定する
//========================================================================================
function funSetName() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var reccnt;
    var greccnt;
    var kreccnt;
    var dreccnt;
    var GamenId;
    var KinoId;
    var DataId;
    var i;
    var j;
    var k;
    var l;
    var xmlBuff = new ActiveXObject("Microsoft.XMLDOM");

    //ﾚｺｰﾄﾞ件数の取得
    reccnt = funGetLength(xmlSA160O);
    greccnt = funGetLengthLocal(xmlAuthority, 0, 0, 1);

    for (i = 0; i < reccnt; i++) {
        //IDの退避
        GamenId = funXmlRead(xmlSA160O, "id_gamen", i);
        KinoId = funXmlRead(xmlSA160O, "id_kino", i);
        DataId = funXmlRead(xmlSA160O, "id_data", i);

        for (j = 0; j < greccnt; j++) {
            //画面IDが同じ場合
            if (GamenId == funXmlReadLocal(xmlAuthority, "id_gamen", j, 0, 0, 1)) {
                //画面名称を設定
                funXmlWrite(xmlSA160O, "gamen", funXmlReadLocal(xmlAuthority, "nm_gamen", j, 0, 0, 1), i);

                //ﾚｺｰﾄﾞ件数の取得
                kreccnt = funGetLengthLocal(xmlAuthority, j, 0, 2);

                for (k = 0; k < greccnt; k++) {
                    //機能IDが同じ場合
                    if (KinoId == funXmlReadLocal(xmlAuthority, "id_kino", j, k, 0, 2)) {
                        //機能名称を設定
                        funXmlWrite(xmlSA160O, "kino", funXmlReadLocal(xmlAuthority, "nm_kino", j, k, 0, 2), i);

                        //ﾚｺｰﾄﾞ件数の取得
                        dreccnt = funGetLengthLocal(xmlAuthority, j, k, 3);

                        for (l = 0; l < dreccnt; l++) {
                            //機能IDが同じ場合
                            if (DataId == funXmlReadLocal(xmlAuthority, "id_data", j, k, l, 3)) {
                                //ﾃﾞｰﾀ名称を設定
                                funXmlWrite(xmlSA160O, "data", funXmlReadLocal(xmlAuthority, "nm_data", j, k, l, 3), i);
                            }
                        }
                    }
                }
            }
        }
    }

    //値を設定したXMLの再ﾛｰﾄﾞ
    xmlBuff.load(xmlSA160O);
    xmlSA160O.load(xmlBuff);

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
        if (XmlId.toString() == "JSP1110") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA170
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenryoInfoMst, 0);
                    break;
            }

        //権限ｺﾝﾎﾞ選択
        } else if (XmlId.toString() == "JSP1120"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA160
                    funXmlWrite(reqAry[i], "cd_kengen", frm.ddlKengen.options[frm.ddlKengen.selectedIndex].value, 0);
                    break;
            }

        //権限使用ﾕｰｻﾞ確認ﾎﾞﾀﾝ押下
        } else if (XmlId.toString() == "JSP1130"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA350
                    funXmlWrite(reqAry[i], "cd_kengen", frm.ddlKengen.options[frm.ddlKengen.selectedIndex].value, 0);
                    break;
            }

        //登録、更新、削除ﾎﾞﾀﾝ押下
        } else if (XmlId.toString() == "JSP1140"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA340
                    funXmlWrite_Tbl(reqAry[i], "ma_kengen", "cd_kengen", frm.ddlKengen.options[frm.ddlKengen.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_kengen", "nm_kengen", frm.txtKengenName.value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_kengen", "kbn_shori", frm.hidEditMode.value, 0);
                    //機能の設定
                    for (j = 0; j < funGetLength(xmlSA160O); j++) {
                        if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "SA340", "ma_kinou");
                        }
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "nm_kino", funXmlRead(xmlSA160O, "nm_kino", j), j);
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "id_gamen", funXmlRead(xmlSA160O, "id_gamen", j), j);
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "id_kino", funXmlRead(xmlSA160O, "id_kino", j), j);
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "id_data", funXmlRead(xmlSA160O, "id_data", j), j);
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "biko", funXmlRead(xmlSA160O, "biko", j), j);
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "kbn_shori", frm.hidEditMode.value, j);
                    }
                    if (funGetLength(xmlSA160O) == 0) {
                        if (reqAry[i].xml == "") {
                            funAddRecNode_Tbl(reqAry[i], "SA340", "ma_kinou");
                        }
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "nm_kino", "", 0);
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "id_gamen", "", 0);
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "id_kino", "", 0);
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "id_data", "", 0);
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "biko", "", 0);
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "kbn_shori", frm.hidEditMode.value, 0);
                    }
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
    funClearSelect(obj, 2);

    //件数取得
    reccnt = funGetLength(xmlData);

    //ﾃﾞｰﾀが存在しない場合
    if (reccnt == 0) {
        return true;
    }

    //属性名の取得
    switch (mode) {
        case 1:    //権限ﾏｽﾀ
            atbName = "nm_kengen";
            atbCd = "cd_kengen";
            break;
    }

    for (i = 0; i < reccnt; i++) {
        if (funXmlRead(xmlData, atbCd, i) != "" && funXmlRead(xmlData, atbName, i) != "") {
            objNewOption = document.createElement("option");
            obj.options.add(objNewOption);
            objNewOption.innerText = ("000" + funXmlRead(xmlData, atbCd, i)).slice(-3) + "：" + funXmlRead(xmlData, atbName, i);
            objNewOption.value = funXmlRead(xmlData, atbCd, i);
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
//       ：③GamenIdx     ：画面INDEX
//       ：④KinoIdx      ：機能INDEX
//       ：⑤DataIdx      ：データINDEX
//       ：⑥mode         ：モード
//           1：画面、2：機能、3：参照可能データ
// 戻り値：取得値
// 概要  ：XMLファイルから指定項目の値を取得する。
//========================================================================================
function funXmlReadLocal(xmlRead, AttributeName, GamenIdx, KinoIdx, DataIdx, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var objNode;
    var RowNo;

    try {
        if (xmlRead.xml == "") {
            return "";
        }

        switch (mode) {
            case 1:    //画面
                objNode = xmlRead.documentElement.childNodes;
                RowNo = GamenIdx;
                break;
            case 2:    //機能
                objNode = xmlRead.documentElement.childNodes.item(GamenIdx).childNodes;
                RowNo = KinoIdx;
                break;
            case 3:    //参照可能データ
                objNode = xmlRead.documentElement.childNodes.item(GamenIdx).childNodes.item(KinoIdx).childNodes;
                RowNo = DataIdx;
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
//       ：②GamenIdx     ：画面INDEX
//       ：③KinoIdx      ：機能INDEX
//       ：④mode    ：モード
//           1：画面、2：機能、3：参照可能データ
// 戻り値：レコード件数
// 概要  ：XMLファイルレコード件数を取得する
//========================================================================================
function funGetLengthLocal(xmlData, GamenIdx, KinoIdx, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var reccnt;
    var obj;

    reccnt = 0;

    if (xmlData.xml == "") {
        return reccnt;

    } else {
        switch (mode) {
            case 1:    //画面
                obj = xmlData.documentElement;
                break;
            case 2:    //機能
                obj = xmlData.documentElement.childNodes.item(GamenIdx);
                break;
            case 3:    //参照可能データ
                obj = xmlData.documentElement.childNodes.item(GamenIdx).childNodes.item(KinoIdx);
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

