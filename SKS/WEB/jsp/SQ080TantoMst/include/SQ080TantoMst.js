//========================================================================================
// 初期表示処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

    //画面設定
    funInitScreen(ConTantoMstId);

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //画面情報を取得・設定
    if (funGetInfo(1) == false) {
        return false;
    }

    //画面の初期化
    // DEL 2013/9/25 okano【QP@30151】No.28 start
//    funClear();
    // DEL 2013/9/25 okano【QP@30151】No.28 end

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    return true;

}

//========================================================================================
// ユーザIDロストフォーカス処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 引数  ：なし
// 概要  ：ユーザIDに紐付くデータを取得し表示する
//========================================================================================
function funSearch() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP0820";
	// MOD 2013/11/7 QP@30154 okano start
//	    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA080","SA210","SA260","SA290");
//	    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA080I,xmlSA210I,xmlSA260I,xmlSA290I);
//	    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA080O,xmlSA210O,xmlSA260O,xmlSA290O);
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA080","SA210","SA260","SA290","SA050");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA080I,xmlSA210I,xmlSA260I,xmlSA290I,xmlSA050I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA080O,xmlSA210O,xmlSA260O,xmlSA290O,xmlSA050O);
	// MOD 2013/11/7 QP@30154 okano end

    //ﾕｰｻﾞIDが未入力の場合
    if (frm.txtUserId.value == "") {
        funClear();
        return true;
    }

    // DEL 2013/9/25 okano【QP@30151】No.28 start
//    //処理中ﾒｯｾｰｼﾞ表示
//    funShowRunMessage();
    // DEL 2013/9/25 okano【QP@30151】No.28 end
    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //ﾕｰｻﾞ情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0820, xmlReqAry, xmlResAry, 1) == false) {
//【QP@10713】2011/10/28 TT H.SHIMA -ADD Start
        //ユーザID初期化
        frm.txtUserId.value = "";

        frm.txtUserId.focus();
//【QP@10713】2011/10/28 TT H.SHIMA -ADD End
        return false;
    }
    // ADD 2013/11/20 QP@30154 okano start
    frm.hidEditMode2.value = funXmlRead(xmlResAry[4], "md_edit", 0);
    // ADD 2013/11/20 QP@30154 okano end

    //ｺﾝﾎﾞﾎﾞｯｸｽの作成
    funCreateComboBox(frm.ddlBusho, xmlResAry[5], 3);
	// ADD 2013/11/7 QP@30154 okano start
    funCreateComboBox(frm.ddlGroup, xmlResAry[6], 4);
	// ADD 2013/11/7 QP@30154 okano end
    funCreateComboBox(frm.ddlTeam, xmlResAry[2], 5);

    //値の設定
    funSetData();

    // ADD 2014/06/11 hisahori【QP@30151】課題No.41 start
    funChangeKaisha2();
    // ADD 2014/06/11 hisahori【QP@30151】課題No.41 end

    //新規ﾕｰｻﾞの場合
    if (funXmlRead(xmlResAry[4], "password", 0) == "") {
        xmlResAry[3].src = "";
    }

    //ﾃﾞｰﾀ件数のﾁｪｯｸ
    if (funGetLength(xmlResAry[3]) > 0 && funXmlRead(xmlResAry[3], "flg_return", 0) == "true" && funXmlRead(xmlResAry[3], "cd_kaisha", 0) != "") {
        //表示
        tblList.style.display = "block";
    } else {
        //非表示
        tblList.style.display = "none";

        // DEL 2013/9/25 okano【QP@30151】No.28 start
//        //処理中ﾒｯｾｰｼﾞ非表示
//        funClearRunMessage();
        // DEL 2013/9/25 okano【QP@30151】No.28 end
    }

    return true;

}

//========================================================================================
// ユーザID変更時
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 引数  ：なし
// 概要  ：画面を初期化する
//========================================================================================
function funChangeUserId() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //画面の初期化
    xmlSA210O.src = "";
    tblList.style.display = "none";

    //ｺﾝﾎﾞﾎﾞｯｸｽの再設定
    funClearSelect(frm.ddlBusho, 2);
    funClearSelect(frm.ddlTeam, 2);

    //値の設定
    frm.txtPass.value = "";
    frm.ddlKengen.selectedIndex = 0;
    frm.txtUserName.value = "";
    frm.ddlKaisha.selectedIndex = 0;
    frm.ddlBusho.selectedIndex = 0;
    frm.ddlGroup.selectedIndex = 0;
    frm.ddlTeam.selectedIndex = 0;
    frm.ddlYakushoku.selectedIndex = 0;

    return true;

}

//========================================================================================
// 担当者検索画面起動処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 引数  ：なし
// 概要  ：担当者検索画面を起動する
//========================================================================================
// 更新者：M.Jinbo
// 更新日：2009/04/24
// 内容  ：リストの表示高さを広げる(課題表№14)
//========================================================================================
function funSearchUser() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var retVal;

    //担当者検索画面を起動する
//    retVal = funOpenModalDialog("../SQ081TantoSearch/SQ081TantoSearch.jsp", this, "dialogHeight:720px;dialogWidth:740px;status:no;scroll:no");
    retVal = funOpenModalDialog("../SQ081TantoSearch/SQ081TantoSearch.jsp", this, "dialogHeight:" + window.screen.height + "px;dialogWidth:" + window.screen.width + "px;status:no;scroll:no");

    if (retVal != "") {
        //ﾕｰｻﾞIDの設定
        frm.txtUserId.value = retVal;

//【QP@10713】2011/10/28 TT H.SHIMA -MOD Start
//funSearchを呼ぶとjsp側のonBlurのfunSearch()も呼ばれ二重処理になる。
        //担当者情報の表示
        //funSearch();

// 2015/03/03 TT.Kitazawa【QP@40812】ADD start（onBlurイベントがうまく発生しない為）
        frm.txtUserId.onblur();
// 2015/03/03 TT.Kitazawa【QP@40812】ADD end

        //ﾌｫｰｶｽ設定
        frm.txtPass.focus();
//【QP@10713】2011/10/28 TT H.SHIMA -MOD End
    }

    return true;

}

//========================================================================================
// 会社コンボボックス連動処理（部署と、グループ）
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 引数  ：なし
// 概要  ：会社に紐付く部署コンボボックスを生成する
//========================================================================================
function funChangeKaisha() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP9010";
	// MOD 2013/11/7 QP@30154 okano start
//	    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA290");
//	    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA290I);
//	    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA290O);
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA290","SA050");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA290I,xmlSA050I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA290O,xmlSA050O);
	// MOD 2013/11/7 QP@30154 okano end

    if (frm.ddlKaisha.selectedIndex == 0) {
        funClearSelect(frm.ddlBusho, 2);
        // ADD 2013/11/7 QP@30154 okano start
        funClearSelect(frm.ddlGroup, 2);
        funClearSelect(frm.ddlTeam, 2);
        // ADD 2013/11/7 QP@30154 okano end
        return true;
    }
    // ADD 2013/11/7 QP@30154 okano start
    funClearSelect(frm.ddlTeam, 2);
    // ADD 2013/11/7 QP@30154 okano end

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //部署情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP9010, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽの作成
    funCreateComboBox(frm.ddlBusho, xmlResAry[2], 3);
	// ADD 2013/11/7 QP@30154 okano start
    funCreateComboBox(frm.ddlGroup, xmlResAry[3], 4);
	// ADD 2013/11/7 QP@30154 okano end

    return true;

}

//========================================================================================
// 会社コンボボックス連動処理（グループのみ）
// 作成者：T.Hisahori
// 作成日：2014/06/11
// 引数  ：なし
// 概要  ：会社に紐付くグループコンボボックスを生成する
//========================================================================================
function funChangeKaisha2() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP9010";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA290","SA050");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA290I,xmlSA050I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA290O,xmlSA050O);

 // 2015/07/30 TT.Kitazawa【QP@40812】ADD start
    // 新規社員番号の場合
    if(frm.hidEditMode2.value == "cash"){
    	return false;
    }
    // 2015/07/30 TT.Kitazawa【QP@40812】ADD end

if (frm.ddlGroup.selectedIndex == 0 || frm.ddlGroup.options[frm.ddlGroup.selectedIndex].text == "") {

        funClearSelect(frm.ddlGroup, 2);
        funClearSelect(frm.ddlTeam, 2);

        //引数をXMLﾌｧｲﾙに設定
        if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
            return false;
        }

        //部署情報を取得
        if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP9010, xmlReqAry, xmlResAry, 1) == false) {
            return false;
        }

        //ｺﾝﾎﾞﾎﾞｯｸｽの作成
        funCreateComboBox(frm.ddlGroup, xmlResAry[3], 4);

    }
    return true;
}

//========================================================================================
// グループコンボボックス連動処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 引数  ：なし
// 概要  ：グループに紐付くチームコンボボックスを生成する
//========================================================================================
function funChangeGroup() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP9020";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA080");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA080I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA080O);

    if (frm.ddlGroup.selectedIndex == 0) {
        funClearSelect(frm.ddlTeam, 2);
        return true;
    }

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //ﾁｰﾑ情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP9020, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽの作成
    funCreateComboBox(frm.ddlTeam, xmlResAry[2], 5);

    return true;

}

//========================================================================================
// 製造担当会社追加画面起動処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 引数  ：なし
// 概要  ：製造担当会社追加画面を起動する
//========================================================================================
function funAddList() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var retVal;
    var xmlBuff = new ActiveXObject("Microsoft.XMLDOM");

    //製造担当会社追加画面を起動する
    retVal = funOpenModalDialog("../SQ082KasihaAdd/SQ082KasihaAdd.jsp", this, "dialogHeight:610px;dialogWidth:785px;status:no;scroll:no");

    if (retVal != "") {
        //製造担当会社の追加
        if (funXmlRead(xmlSA210O, "cd_kaisha", funGetLength(xmlSA210O)-1) != "" || funGetLength(xmlSA210O) == 0) {
            funAddRecNode(xmlSA210O, "SA210");
        }
        funXmlWrite(xmlSA210O, "cd_kaisha", retVal.split(ConDelimiter)[0], funGetLength(xmlSA210O)-1);
        funXmlWrite(xmlSA210O, "nm_kaisha", retVal.split(ConDelimiter)[1], funGetLength(xmlSA210O)-1);

        //値を設定したXMLの再ﾛｰﾄﾞ
        xmlBuff.load(xmlSA210O);
        xmlSA210O.load(xmlBuff);

        if (tblList.style.display == "none") {
           tblList.style.display = "block";
        }
        funClearCurrentRow(tblList);
    }

    return true;

}

//========================================================================================
// 製造担当会社削除処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 引数  ：なし
// 概要  ：選択行の製造担当会社を削除する
//========================================================================================
function funDelList() {

    //行が選択されていない場合
    if (funGetCurrentRow().toString() == "") {
        funErrorMsgBox(E000002);
        return false;
    }

    //指定された担当会社を削除する
    funSelectRowDelete(xmlSA210O);

    return true;

}

//========================================================================================
// 登録、更新、削除ボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 引数  ：①mode ：処理区分
//           1：登録、2：更新、3：削除
// 概要  ：表示データの登録、更新、削除処理を行う
//========================================================================================
function funDataEdit(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP0830";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA270");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA270I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA270O);
    var dspMsg;
    // 2015/07/30 TT.Kitazawa【QP@40812】ADD start
    // 社員番号未入力の場合、データ渡しでエラーになる為
    if (frm.txtUserId.value == "") {
    	funErrorMsgBox("社員番号は必須項目です。\\n入力して下さい。");
    	return false;
    }
    // 2015/07/30 TT.Kitazawa【QP@40812】ADD end

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

    //処理区分の退避
    frm.hidEditMode.value = mode;

    //XMLの初期化
    setTimeout("xmlSA270I.src = '../../model/SA270I.xml';", ConTimer);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //登録、更新、削除処理を実行する
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0830, xmlReqAry, xmlResAry, 1) == false) {
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

    	// ADD 2013/9/25 okano【QP@30151】No.28 start
    	//仮登録ユーザの場合
    	if(hidKariMode == "1"){
    		funChkLogin();
    	}
    	// ADD 2013/9/25 okano【QP@30151】No.28 end

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
// 作成日：2009/04/06
// 引数  ：なし
// 概要  ：画面を初期化する
//========================================================================================
function funClear() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //画面の初期化
    frm.reset();
    xmlSA210O.src = "";
    tblList.style.display = "none";

    //画面の制御
    funInit();

    //ｺﾝﾎﾞﾎﾞｯｸｽの再設定
    funClearSelect(frm.ddlBusho, 2);
    funClearSelect(frm.ddlTeam, 2);

    //ﾌｫｰｶｽ設定
    frm.txtUserId.focus();

    return true;

}

//========================================================================================
// 次画面遷移処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 概要  ：次画面に遷移する
//========================================================================================
function funNext(mode) {

    var wUrl;

    //遷移先判定
    switch (mode) {
        case 0:    //ﾏｽﾀﾒﾆｭｰ
            wUrl = "../SQ030MstMenu/SQ030MstMenu.jsp";
            break;
        // ADD 2013/9/25 okano【QP@30151】No.28 start
        case 1:    //仮登録ﾕｰｻﾞ
            wUrl = "../SQ020MainMenu/SQ020MainMenu.jsp";
            break;
        case 2:    //仮登録ﾕｰｻﾞ終了時
        	wUrl = "../SQ010Login/SQ010Login.jsp";
            break;
        // ADD 2013/9/25 okano【QP@30151】No.28 end
    }

    //遷移
    funUrlConnect(wUrl, ConConectPost, document.frm00);

    return true;
}

//========================================================================================
// 画面情報取得処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 引数  ：①mode  ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：ユーザのセッション情報、コンボボックスの情報を取得する
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP0810";
    // MOD 2013/9/25 okano【QP@30151】No.28 start
//	    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA050","SA140","SA170","SA310");
//	    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA050I,xmlSA140I,xmlSA170I,xmlSA310I);
//	    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA050O,xmlSA140O,xmlSA170O,xmlSA310O);
	// MOD 2013/11/7 QP@30154 okano start
//	    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA050","SA140","SA170","SA310","FGEN2120");
//	    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA050I,xmlSA140I,xmlSA170I,xmlSA310I,xmlFGEN2120I);
//	    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA050O,xmlSA140O,xmlSA170O,xmlSA310O,xmlFGEN2120O);
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA140","SA170","SA310","FGEN2120");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA140I,xmlSA170I,xmlSA310I,xmlFGEN2120I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA140O,xmlSA170O,xmlSA310O,xmlFGEN2120O);
	// MOD 2013/11/7 QP@30154 okano end
    // MOD 2013/9/25 okano【QP@30151】No.28 end

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0810, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //ﾕｰｻﾞ情報表示
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //権限関連の処理を行う
    funSaveKengenInfo();

    //ｺﾝﾎﾞﾎﾞｯｸｽの作成
	// MOD 2013/11/7 QP@30154 okano start
//	    funCreateComboBox(frm.ddlKengen, xmlResAry[4], 1);
//	    funCreateComboBox(frm.ddlKaisha, xmlResAry[3], 2);
//	    funCreateComboBox(frm.ddlGroup, xmlResAry[2], 4);
//	    funCreateComboBox(frm.ddlYakushoku, xmlResAry[5], 6);
    funCreateComboBox(frm.ddlKengen, xmlResAry[3], 1);
    funCreateComboBox(frm.ddlKaisha, xmlResAry[2], 2);
    funCreateComboBox(frm.ddlYakushoku, xmlResAry[4], 6);
	// MOD 2013/11/7 QP@30154 okano end

    // ADD 2013/9/25 okano【QP@30151】No.28 start
    frm.txtUserId.value=funXmlRead_3(xmlResAry[5], "table", "id_user", 0, 0);
    hidKariMode=funXmlRead_3(xmlResAry[5], "table", "kbn_kari", 0, 0);
    if(hidKariMode == "1"){
        funSearch();
    }
    // ADD 2013/9/25 okano【QP@30151】No.28 end

    return true;

}

//========================================================================================
// 権限関連処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 概要  ：権限情報の退避、画面制御を行う
//========================================================================================
function funSaveKengenInfo() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var obj = xmlUSERINFO_O;
    var GamenId;
    var KinoId;
    var DataId;
    var reccnt;
    var i;

    //登録、更新、削除ﾎﾞﾀﾝの制御
    frm.btnInsert.disabled = true;
    frm.btnUpdate.disabled = true;
    frm.btnDelete.disabled = true;

    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        GamenId = funXmlRead(obj, "id_gamen", i);
        KinoId = funXmlRead(obj, "id_kino", i);
        DataId = funXmlRead(obj, "id_data", i);

        //担当者ﾏｽﾀﾒﾝﾃﾅﾝｽ
        if (GamenId.toString() == ConGmnIdTantoMst.toString()) {
            //編集
            if (KinoId.toString() == ConFuncIdEdit.toString()) {
                //対象データが全ての場合
     // MOD 2014/06/11 start 【QP@30154】課題No.41
     //           if (DataId == "9") {
     //               frm.btnInsert.disabled = false;
     //           }
                frm.btnInsert.disabled = false;
     // MOD 2014/06/11 end 【QP@30154】課題No.41
                frm.btnUpdate.disabled = false;
                frm.btnDelete.disabled = false;

            //編集(氏名、ﾊﾟｽﾜｰﾄﾞのみ)
            } else if (KinoId.toString() == ConFuncIdEdit2.toString()) {
                frm.btnUpdate.disabled = false;

            // ADD 2013/9/25 okano【QP@30151】No.28 start
            //編集（仮登録ユーザ）
            } else if (KinoId.toString() == ConFuncIdEditCash.toString()) {
                frm.btnInsert.disabled = false;
                frm.btnUpdate.disabled = true;
                frm.btnDelete.disabled = true;
                frm.btnClear.disabled = true;
                frm.btnSearchUser.disabled = true;
                funItemReadOnly(frm.txtUserId, true);
            } else if (KinoId.toString() == ConFuncIdEditPass.toString()) {
                frm.btnInsert.disabled = true;
                frm.btnUpdate.disabled = false;
                frm.btnDelete.disabled = true;
                frm.btnClear.disabled = true;
                frm.btnSearchUser.disabled = true;
                funItemReadOnly(frm.txtUserId, true);
            // ADD 2013/9/25 okano【QP@30151】No.28 end
            }

            hidMode.value = KinoId;
        }
    }

    return true;

}

//========================================================================================
// 取得データ設定処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 引数  ：なし
// 戻り値：なし
// 概要  ：取得した担当者情報を画面に設定する
//========================================================================================
function funSetData() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    //画面の制御
    funInit();

    //ﾎﾞﾀﾝ制御
    // ADD 2013/11/20 QP@30154 okano start
    if(frm.hidEditMode2.value == "cash"){
    	funItemReadOnly(frm.txtPass, false);
	    funItemReadOnly(frm.ddlKengen, true);
    	funItemReadOnly(frm.txtUserName, false);
	    funItemReadOnly(frm.ddlKaisha, true);
	    funItemReadOnly(frm.ddlBusho, true);
	    // 2015/07/30 TT.Kitazawa【QP@40812】ADD start
    	// 新規社員番号入力時：編集権限の人は権限コンボボックス等活性化
    	if (hidMode.value == ConFuncIdEdit) {
    	    funItemReadOnly(frm.ddlKengen, false);
    	    funItemReadOnly(frm.ddlKaisha, false);
    	    funItemReadOnly(frm.ddlBusho, false);
    	    // 新規登録ボタンは活性化
    	    frm.btnInsert.disabled = false;
    	}
    	// 2015/07/30 TT.Kitazawa【QP@40812】ADD end

    	funItemReadOnly(frm.ddlGroup, false);
	    funItemReadOnly(frm.ddlTeam, false);
	    funItemReadOnly(frm.ddlYakushoku, false);
	    // ADD 2014/06/11 QP@30151 課題Mo.41 start
        frm.btnAddList.disabled = false;
        frm.btnDelList.disabled = false;
        frm.btnUpdate.disabled = true;  //新ユーザマスタにしか存在しないユーザIDの場合、更新ボタン非活性
	    // ADD 2014/06/11 QP@30151 課題Mo.41 end

    } else {
    // ADD 2013/11/20 QP@30154 okano end
    if (hidMode.value == ConFuncIdEdit) {
        // ADD 2013/11/20 QP@30154 okano start
    	if(frm.hidEditMode2.value == "system"){
        	// 2015/07/30 TT.Kitazawa【QP@40812】MOD start
        	// 入力した社員番号が存在時、新規登録ボタンは非活性
    		frm.btnInsert.disabled = true;
           	// 2015/07/30 TT.Kitazawa【QP@40812】MOD end
            frm.btnAddList.disabled = false;
            frm.btnDelList.disabled = false;
    	    funItemReadOnly(frm.txtPass, false);
    	    funItemReadOnly(frm.ddlKengen, false);
    	    funItemReadOnly(frm.txtUserName, false);
    	    funItemReadOnly(frm.ddlKaisha, false);
    	    funItemReadOnly(frm.ddlBusho, false);
    	    funItemReadOnly(frm.ddlGroup, false);
    	    funItemReadOnly(frm.ddlTeam, false);
    	    funItemReadOnly(frm.ddlYakushoku, false);
    	} else {
        // ADD 2013/11/20 QP@30154 okano end

    	// 2015/07/30 TT.Kitazawa【QP@40812】MOD start
    	// 入力した社員番号が存在時、新規登録ボタンは非活性
        frm.btnInsert.disabled = true;
       	// 2015/07/30 TT.Kitazawa【QP@40812】MOD end
        frm.btnAddList.disabled = false;
        frm.btnDelList.disabled = false;
	    // ADD 2014/06/11 QP@30151 課題Mo.41 start
        frm.btnUpdate.disabled = false;  //新ユーザマスタにしか存在しないユーザIDの場合、更新ボタン非活性したため、再検索時に活性化
	    // ADD 2014/06/11 QP@30151 課題Mo.41 end

        //フォーム制御
	    //2012/03/01 TT H.SHIMA Java6対応 add Start
        // MOD 2013/11/20 QP@30154 okano start
//		    funItemReadOnly(frm.txtPass, false);
//		    funItemReadOnly(frm.ddlKengen, false);
//		    funItemReadOnly(frm.txtUserName, false);
//		    funItemReadOnly(frm.ddlKaisha, false);
//		    funItemReadOnly(frm.ddlBusho, false);
//		    funItemReadOnly(frm.ddlGroup, false);
//		    funItemReadOnly(frm.ddlTeam, false);
//		    funItemReadOnly(frm.ddlYakushoku, false);
	    funItemReadOnly(frm.txtPass, false);
	    funItemReadOnly(frm.ddlKengen, true);
	    funItemReadOnly(frm.txtUserName, false);
	    funItemReadOnly(frm.ddlKaisha, true);
	    funItemReadOnly(frm.ddlBusho, true);
	    funItemReadOnly(frm.ddlGroup, false);
	    funItemReadOnly(frm.ddlTeam, false);
	    funItemReadOnly(frm.ddlYakushoku, false);
        // MOD 2013/11/20 QP@30154 okano end
	    //2012/03/01 TT H.SHIMA Java6対応 add End
	// DEL 2013/11/20 QP@30154 okano start

	// ADD 2013/9/25 okano【QP@30151】No.28 start
//		} else if (hidMode.value == ConFuncIdEditCash) {
//		    funItemReadOnly(frm.txtPass, false);
//		    funItemReadOnly(frm.ddlKengen, true);
//		    funItemReadOnly(frm.txtUserName, false);
//		    funItemReadOnly(frm.ddlKaisha, true);
//		    funItemReadOnly(frm.ddlBusho, true);
//		    funItemReadOnly(frm.ddlGroup, false);
//		    funItemReadOnly(frm.ddlTeam, false);
//		    funItemReadOnly(frm.ddlYakushoku, false);
	// ADD 2013/9/25 okano【QP@30151】No.28 end
	// DEL 2013/11/20 QP@30154 okano end
	    // ADD 2013/11/20 QP@30154 okano start
    	}

	    // ADD 2014/06/11 QP@30151 課題Mo.41 start
        frm.btnUpdate.disabled = false;  //新ユーザマスタにしか存在しないユーザIDの場合、更新ボタン非活性したため、再検索時に活性化
	    // ADD 2014/06/11 QP@30151 課題Mo.41 end

        }
        // ADD 2013/11/20 QP@30154 okano end

    }

    //値の設定
    frm.txtPass.value = funXmlRead(xmlSA260O, "password", 0);
    funDefaultIndex(frm.ddlKengen, 1);
    frm.txtUserName.value = funXmlRead(xmlSA260O, "nm_user", 0);
    funDefaultIndex(frm.ddlKaisha, 2);
    funDefaultIndex(frm.ddlBusho, 3);
    // MOD 2013/9/25 okano【QP@30151】No.28 start
//    funDefaultIndex(frm.ddlGroup, 4);
//    funDefaultIndex(frm.ddlTeam, 5);
//    funDefaultIndex(frm.ddlYakushoku, 6);
    if(hidMode.value == ConFuncIdEditCash){
    	frm.ddlGroup.selectedIndex = 0;
    	frm.ddlTeam.selectedIndex = 0;
    	frm.ddlYakushoku.selectedIndex = 0;
    } else {
	    funDefaultIndex(frm.ddlGroup, 4);
	    funDefaultIndex(frm.ddlTeam, 5);
	    funDefaultIndex(frm.ddlYakushoku, 6);
    }
    // MOD 2013/9/25 okano【QP@30151】No.28 start

    return true;

}

//========================================================================================
// 画面初期化
// 作成者：M.Jinbo
// 作成日：2009/04/04
// 引数  ：なし
// 戻り値：なし
// 概要  ：画面を初期状態に戻す
//========================================================================================
function funInit() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

  //2012/03/01 TT H.SHIMA Java6対応 mod Start
    //画面の制御
//    if (hidMode.value == ConFuncIdEdit) {
//        //編集
//        funItemReadOnly(frm.txtPass, false);
//        funItemReadOnly(frm.ddlKengen, false);
//        funItemReadOnly(frm.txtUserName, false);
//        funItemReadOnly(frm.ddlKaisha, false);
//        funItemReadOnly(frm.ddlBusho, false);
//        funItemReadOnly(frm.ddlGroup, false);
//        funItemReadOnly(frm.ddlTeam, false);
//        funItemReadOnly(frm.ddlYakushoku, false);
//    } else {
        //編集(氏名、ﾊﾟｽﾜｰﾄﾞのみ)
        funItemReadOnly(frm.txtPass, false);
        funItemReadOnly(frm.ddlKengen, true);
        funItemReadOnly(frm.txtUserName, false);
        funItemReadOnly(frm.ddlKaisha, true);
        funItemReadOnly(frm.ddlBusho, true);
        funItemReadOnly(frm.ddlGroup, true);
        funItemReadOnly(frm.ddlTeam, true);
        funItemReadOnly(frm.ddlYakushoku, true);
//    }
      //2012/03/01 TT H.SHIMA Java6対応 mod End

    frm.btnAddList.disabled = true;
    frm.btnDelList.disabled = true;

}

//========================================================================================
// XMLファイルに書き込み
// 作成者：M.Jinbo
// 作成日：2009/04/06
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
    var j;

    for (i = 0; i < reqAry.length; i++) {
        //画面初期表示
        if (XmlId.toString() == "JSP0810") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                // MOD 2013/10/24 QP@30154 okano start
//	                case 1:    //SA050
//	                    funXmlWrite(reqAry[i], "id_user", "", 0);
//	                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
//	                    break;
//	                case 2:    //SA140
//	                    funXmlWrite(reqAry[i], "id_user", "", 0);
//	                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
//	                    break;
//	                case 3:    //SA170
//	                    break;
//	                case 4:    //SA310
//	                    funXmlWrite(reqAry[i], "cd_category", "K_yakusyoku", 0);
//	                    funXmlWrite(reqAry[i], "id_user", "", 0);
//	                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
//	                    break;
                case 1:    //SA140
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                    break;
                case 2:    //SA170
                    break;
                case 3:    //SA310
                    funXmlWrite(reqAry[i], "cd_category", "K_yakusyoku", 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                    break;
                // MOD 2013/10/24 QP@30154 okano end
            }

        //ﾕｰｻﾞIDﾛｽﾄﾌｫｰｶｽ
        } else if (XmlId.toString() == "JSP0820"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA080
                    funXmlWrite(reqAry[i], "cd_group", "", 0);
                    funXmlWrite(reqAry[i], "kbn_shori", "2", 0);
                    funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                    break;
                case 2:    //SA210
                    funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
                    funXmlWrite(reqAry[i], "kbn_shori", "1", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                    break;
                case 3:    //SA260
                    funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
                    break;
                case 4:    //SA290
                    funXmlWrite(reqAry[i], "cd_kaisha", "", 0);
                    funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                    break;
               // ADD 2013/10/24 QP@30154 okano start
                case 5:    //SA050
                    funXmlWrite(reqAry[i], "cd_kaisha", "", 0);
                    funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                break;
               // ADD 2013/10/24 QP@30154 okano end
            }

        //登録、更新、削除ﾎﾞﾀﾝ押下
        } else if (XmlId.toString() == "JSP0830"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA270
                    funXmlWrite_Tbl(reqAry[i], "ma_user", "id_user", frm.txtUserId.value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_user", "password", frm.txtPass.value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_user", "cd_kengen", frm.ddlKengen.options[frm.ddlKengen.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_user", "nm_user", frm.txtUserName.value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_user", "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_user", "cd_busho", frm.ddlBusho.options[frm.ddlBusho.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_user", "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_user", "cd_team", frm.ddlTeam.options[frm.ddlTeam.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_user", "cd_yakushoku", frm.ddlYakushoku.options[frm.ddlYakushoku.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_user", "kbn_shori", frm.hidEditMode.value, 0);
                    //製造担当会社の設定
                    for (j = 0; j < funGetLength(xmlSA210O); j++) {
                        if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "SA270", "ma_tantokaisya");
                        }
                        funXmlWrite_Tbl(reqAry[i], "ma_tantokaisya", "id_user", frm.txtUserId.value, j);
                        funXmlWrite_Tbl(reqAry[i], "ma_tantokaisya", "cd_tantokaisha", funXmlRead(xmlSA210O, "cd_kaisha", j), j);
                        funXmlWrite_Tbl(reqAry[i], "ma_tantokaisya", "kbn_shori", frm.hidEditMode.value, j);
                    }
                    if (funGetLength(xmlSA210O) == 0) {
                        if (reqAry[i].xml == "") {
                            funAddRecNode_Tbl(reqAry[i], "SA270", "ma_tantokaisya");
                        }
                        funXmlWrite_Tbl(reqAry[i], "ma_tantokaisya", "id_user", frm.txtUserId.value, j);
                        funXmlWrite_Tbl(reqAry[i], "ma_tantokaisya", "cd_tantokaisha", "", 0);
                        funXmlWrite_Tbl(reqAry[i], "ma_tantokaisya", "kbn_shori", frm.hidEditMode.value, j);
                    }
                    break;
            }

        //会社ｺﾝﾎﾞ選択
        } else if (XmlId.toString() == "JSP9010"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA290
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                    break;
               // ADD 2013/10/24 QP@30154 okano start
                case 2:    //SA050
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                break;
               // ADD 2013/10/24 QP@30154 okano end
            }

        //ｸﾞﾙｰﾌﾟｺﾝﾎﾞ選択
        } else if (XmlId.toString() == "JSP9020"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA080
                    funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "kbn_shori", "1", 0);
                    funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                    break;
            }
        }
        // ADD 2013/9/25 okano【QP@30151】No.28 start
        //シングルサインオン
        else if (XmlId.toString() == "JSP0010"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", 2, 0);
                    funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
                    break;
                case 1:    //SA010
                    funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
                    funXmlWrite(reqAry[i], "password", frm.txtPass.value, 0);
                    funXmlWrite(reqAry[i], "kbn_login", "1", 0);
                    break;
            }
        }
        // ADD 2013/9/25 okano【QP@30151】No.28 end
    }

    return true;

}

//========================================================================================
// コンボボックス作成処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
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

    //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ(空白あり)
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
        case 2:    //部署ﾏｽﾀ(会社)
            atbName = "nm_kaisha";
            atbCd = "cd_kaisha";
            break;
        case 3:    //部署ﾏｽﾀ(部署)
            atbName = "nm_busho";
            atbCd = "cd_busho";
            break;
        case 4:    //ｸﾞﾙｰﾌﾟﾏｽﾀ
            atbName = "nm_group";
            atbCd = "cd_group";
            break;
        case 5:    //ﾁｰﾑﾏｽﾀ
            atbName = "nm_team";
            atbCd = "cd_team";
            break;
        case 6:    //ﾘﾃﾗﾙﾏｽﾀ
            atbName = "nm_literal";
            atbCd = "cd_literal";
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

    //ｺﾝﾎﾞﾎﾞｯｸｽのﾃﾞﾌｫﾙﾄ表示
    obj.selectedIndex = 0;

    return true;
}

//========================================================================================
// デフォルト値選択処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②mode     ：モード
// 概要  ：コンボボックスのデフォルト値を選択する
//========================================================================================
function funDefaultIndex(obj, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var selIndex;
    var i;

    //
    for (i = 0; i < obj.length; i++) {
        switch (mode) {
            case 1:    //権限ｺﾝﾎﾞ
                if (obj.options[i].value == funXmlRead(xmlSA260O, "cd_kengen", 0)) {
                    selIndex = i;
                }
                break;
            case 2:    //会社ｺﾝﾎﾞ
                if (obj.options[i].value == funXmlRead(xmlSA260O, "cd_kaisha", 0)) {
                    selIndex = i;
                }
                break;
            case 3:    //部署ｺﾝﾎﾞ
                if (obj.options[i].value == funXmlRead(xmlSA260O, "cd_busho", 0)) {
                    selIndex = i;
                }
                break;
            case 4:    //ｸﾞﾙｰﾌﾟｺﾝﾎﾞ
                if (obj.options[i].value == funXmlRead(xmlSA260O, "cd_group", 0)) {
                    selIndex = i;
                }
                break;
            case 5:    //ﾁｰﾑｺﾝﾎﾞ
                if (obj.options[i].value == funXmlRead(xmlSA260O, "cd_team", 0)) {
                    selIndex = i;
                }
                break;
            case 6:    //役職ｺﾝﾎﾞ
                if (obj.options[i].value == funXmlRead(xmlSA260O, "cd_yakushoku", 0)) {
                    selIndex = i;
                }
                break;
        }
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽのｲﾝﾃﾞｯｸｽ指定
    obj.selectedIndex = selIndex;

    return true;
}

// ADD 2013/9/25 okano【QP@30151】No.28 start
function funClose(){

    if (hidMode.value == ConFuncIdEditKari.toString() || hidMode.value == ConFuncIdEditPass.toString() || hidMode.value == ConFuncIdEditCash.toString()) {
        funNext(2);
    }
    else {
        funNext(0);
     }

    return true;
}

//========================================================================================
//ログイン情報チェック処理
//作成者：Y.Nishigawa
//作成日：2011/01/28
//概要  ：ユーザの認証を行う
//========================================================================================
function funChkLogin() {

var frm = document.frm00;    //ﾌｫｰﾑへの参照
var XmlId = "JSP0010";
var FuncIdAry = new Array(ConResult,ConUserInfo,"SA010");
var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA010I);
var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA010O);

//引数をXMLﾌｧｲﾙに設定
if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
   funClearRunMessage();
   return false;
}

//ﾕｰｻﾞ認証を行う
if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0010, xmlReqAry, xmlResAry, 2) == false) {
   return false;
} else {
   //ﾒﾆｭｰに遷移
   funNext(1);
}

return true;

}
// ADD 2013/9/25 okano【QP@30151】No.28 end

