//========================================================================================
// 初期表示処理
// 作成者：hisahori
// 作成日：2014/10/07
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

    funInitScreen(ConGentyoLiteralMstId);

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
// カテゴリコンボボックス連動処理
// 作成者：hisahori
// 作成日：2014/10/10
// 引数  ：なし
// 概要  ：カテゴリに紐付くリテラルコンボボックスを生成する
//========================================================================================
function funChangeCategory() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP0621";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA111");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA111I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA111O);

    //ｶﾃｺﾞﾘ以下をｸﾘｱ
    funInit();
    funInit2nd(1);

    if (frm.ddlCategory.selectedIndex == 0) {
        //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
        funClearSelect(frm.ddlLiteral, 2);
        funClearSelect(frm.ddl2ndLiteral, 2);
        return true;
    }

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //ﾘﾃﾗﾙ情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0621, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽの作成
    funCreateComboBox(frm.ddlLiteral, xmlResAry[2], 2);
    funClearSelect(frm.ddl2ndLiteral, 2);

    return true;

}

//========================================================================================
// リテラル情報取得処理
// 作成者：hisahori
// 作成日：2014/10/10
// 引数  ：なし
// 概要  ：リテラルデータの検索を行う
//========================================================================================
function funSearch() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP0631";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA101");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA101I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA101O);
    var PageCnt;
    var RecCnt;
    var ListMaxRow;

    funInit();
    funInit2nd(1);

    // 第二リテラル以下をクリア
    if (frm.ddlLiteral.selectedIndex == 0) {
        funClearSelect(frm.ddl2ndLiteral, 2);
        return true;
    }

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        //画面初期化
        funInit();
        funClearRunMessage();
        return false;
    }

    //検索条件に一致するﾘﾃﾗﾙﾃﾞｰﾀを取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0631, xmlReqAry, xmlResAry, 1) == false) {
        //画面初期化
        funClear();
        return false;
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽの作成 add
    funCreateComboBox(frm.ddl2ndLiteral, xmlResAry[2], 5); //mode=5  第二リテラル

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    //画面の初期化
    funSetData(xmlResAry[2]);

    return true;

}

//========================================================================================
//第二リテラル情報取得処理
//作成者：hisahori
//作成日：2014/10/10
//引数  ：なし
//概要  ：第二リテラルデータの検索を行う
//========================================================================================
function funSearch2nd() {

  var frm = document.frm00;    //ﾌｫｰﾑへの参照
  var XmlId = "JSP0632";
  var FuncIdAry = new Array(ConResult,ConUserInfo,"SA102");
  var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA102I);
  var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA102O);
  var PageCnt;
  var RecCnt;
  var ListMaxRow;

  if (frm.ddl2ndLiteral.value == 0) {
	  funInit2nd(2);
      return true;
  }

  //処理中ﾒｯｾｰｼﾞ表示
  funShowRunMessage();

  //引数をXMLﾌｧｲﾙに設定
  if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
      //画面初期化
	  funInit2nd(2);
      funClearRunMessage();
      return false;
  }

  //検索条件に一致するﾘﾃﾗﾙﾃﾞｰﾀを取得
  if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0632, xmlReqAry, xmlResAry, 1) == false) {
      //画面初期化
      funClear();
      return false;
  }

  //処理中ﾒｯｾｰｼﾞ非表示
  funClearRunMessage();

  //画面の初期化
  funSetData_2nd(xmlResAry[2]);

  return true;

}

//========================================================================================
// 登録、更新、削除ボタン押下処理
// 作成者：hisahori
// 作成日：2014/10/10
// 引数  ：①mode ：処理区分
//           1：登録、2：更新、3：削除
// 概要  ：表示データの登録、更新、削除処理を行う
//========================================================================================
function funDataEdit(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP0641";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA331");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA331I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA331O);
    var dspMsg;

    if (mode == 1) {
    	if (frm.txt2ndLiteralName.value == ""){
    		dspMsg = "第１リテラル を新規登録します。よろしいですか？";
        }else{
    		dspMsg = "第２リテラル を新規登録します。よろしいですか？";
        }
    } else if (mode == 2) {
    	if (frm.txt2ndLiteralName.value == ""){
    		dspMsg = "第１リテラル を更新します。よろしいですか？";
        }else{
    		dspMsg = "第２リテラル を更新します。よろしいですか？";
        }
    } else {
    	if (frm.txt2ndLiteralName.value == ""){
    		dspMsg = "第１リテラル を削除します。よろしいですか？";
        }else{
    		dspMsg = "第２リテラル を削除します。よろしいですか？";
        }
    }

    //確認ﾒｯｾｰｼﾞの表示
    if (funConfMsgBox(dspMsg) != ConBtnYes) {
        return false;
    }

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //処理区分の退避
    frm.hidEditMode.value = mode;

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }
    //登録、更新、削除処理を実行する
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0641, xmlReqAry, xmlResAry, 1) == false) {
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
        funInit();
        funInit2nd(1);
    }

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    return true;

}

//========================================================================================
// クリアボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/04/04
// 引数  ：なし
// 概要  ：画面を初期化する
//========================================================================================
function funClear() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //画面初期化
    funInit();
    funInit2nd(1);
    funCreateComboBox(frm.ddlCategory, xmlSA041O, 1);
    funClearSelect(frm.ddlLiteral, 2);
    funClearSelect(frm.ddl2ndLiteral, 2);

    //ﾌｫｰｶｽ設定
    frm.ddlCategory.focus();

    return true;

}

//========================================================================================
// Excel出力ボタン処理
// 作成者：M.Jinbo
// 作成日：2009/04/04
// 引数  ：なし
// 概要  ：CSVファイルの出力を行う
//========================================================================================
function funOutput() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP0650";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA320");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA320I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA320O);
    var dspMsg;

    //確認ﾒｯｾｰｼﾞの表示
    if (funConfMsgBox(I000008) != ConBtnYes) {
        return false;
    }

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //CSVﾌｧｲﾙを作成する
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0650, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //結果判定
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "false") {
        //ｴﾗｰ発生時はﾒｯｾｰｼﾞを表示
        dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
        funInfoMsgBox(dspMsg);
        return false;
    }

    //ﾌｧｲﾙﾊﾟｽの退避
    frm.strFilePath.value = funXmlRead(xmlSA320O, "URLValue", 0);

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    //ﾀﾞｳﾝﾛｰﾄﾞ画面の起動
    funFileDownloadUrlConnect(ConConectGet, document.frm00);

    return true;

}
//========================================================================================
// 終了処理
// 作成者：May Thu
// 作成日：2016/09/16
// 概要  ：終了処理
//========================================================================================
function funEnd(){
	var wUrl;
	// 親ウィンドウの存在をチェック
	if(window.opener ){
		// 親ウインドウが存在する
		window.close();
	} else {
		// メニューからの遷移
		wUrl = "../SQ170GenchoMenu/SQ170GenchoMenu.jsp";
		// メニューに戻る
		funUrlConnect(wUrl, ConConectPost, document.frm00);

	}
	return true;
}

//========================================================================================
// 次画面遷移処理
// 作成者：M.Jinbo
// 作成日：2009/04/04
// 概要  ：次画面に遷移する
//========================================================================================
function funNext(mode) {
	return funEnd();
//    var wUrl;
//
//    //遷移先判定
//    switch (mode) {
//        case 0:    //ﾏｽﾀﾒﾆｭｰ
//            wUrl = "../SQ170GenchoMenu/SQ170GenchoMenu.jsp";
//            break;
//    }
//
//    //遷移
//    funUrlConnect(wUrl, ConConectPost, document.frm00);
//
//    return true;
}

//========================================================================================
// 画面情報取得処理
// 作成者：hisahori
// 作成日：2014/10/10
// 引数  ：①mode  ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：ユーザのセッション情報、コンボボックスの情報を取得する
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP0611";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA041","SA300");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA041I,xmlSA300I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA041O,xmlSA300O);
    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0611, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //ﾕｰｻﾞ情報表示
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //権限関連の処理を行う
    funSaveKengenInfo();

    //ｺﾝﾎﾞﾎﾞｯｸｽの作成
    funCreateComboBox(frm.ddlCategory, xmlResAry[2], 1);
    funClearSelect(frm.ddlLiteral, 2);
    funClearSelect(frm.ddl2ndLiteral, 2);
    funCreateComboBox(frm.ddlUseEdit, xmlResAry[3], 3);

    return true;

}

//========================================================================================
// 権限関連処理
// 作成者：M.Jinbo
// 作成日：2009/04/04
// 概要  ：権限情報の退避、画面制御を行う
//========================================================================================
function funSaveKengenInfo() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var obj = xmlUSERINFO_O;
    var GamenId;
    var KinoId;
    var reccnt;
    var i;

    //登録、更新、削除ﾎﾞﾀﾝの制御
    frm.btnInsert.disabled = true;
    frm.btnUpdate.disabled = true;
    frm.btnDelete.disabled = true;
//    frm.btnExcel.disabled = true;

    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        GamenId = funXmlRead(obj, "id_gamen", i);
        KinoId = funXmlRead(obj, "id_kino", i);

        //ﾘﾃﾗﾙﾏｽﾀﾒﾝﾃﾅﾝｽ
        if (GamenId.toString() == ConGmnIdGentyoLiteralMst.toString()) {
            //編集 or ｼｽﾃﾑ管理者
            if (KinoId.toString() == ConFuncIdEdit.toString() || KinoId.toString() == ConFuncIdSysMgr.toString()) {
                frm.btnInsert.disabled = false;
                frm.btnUpdate.disabled = false;
                frm.btnDelete.disabled = false;
            }

            hidGamenId.value = GamenId;
            hidKinoId.value = KinoId;
        }
    }

    return true;

}

//========================================================================================
// リテラルデータ設定処理
// 作成者：hisahori
// 作成日：2014/10/10
// 引数  ：①xmlData ： リテラル情報XML
// 概要  ：取得したリテラルデータを画面に設定する
//========================================================================================
function funSetData(xmlData) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //画面制御
    if (funXmlRead(xmlData, "flg_edit", 0) == "1" || hidKinoId.value == ConFuncIdSysMgr) {
        //編集可 or ｼｽﾃﾑ管理者
        funGamenControl(false);

    } else {
        //編集不可
        funGamenControl(true);
    }

    //画面情報の設定
    // 第二リテラル使用フラグが1なら、チェックボックスをオンに
    if (funXmlRead(xmlData, "flg_2ndedit", 0) == "1"){
    	frm.chk2ndliteral.checked = true;
		frm.ddl2ndLiteral.disabled = false; //第二リテラルコンボボックス活性化
		frm.txt2ndLiteralName.disabled = false; //第二リテラル名 活性化
		frm.txt2ndSortNo.disabled = false; //第二ソート 活性化

		frm.txt2ndliteralHissu.value = "（必須）"; //第二リテラルの必須マーク付与
		frm.txt2ndHyojiHissu.value = "（必須）"; //第二表示順の必須マーク付与
    }else {
    	frm.chk2ndliteral.checked = false;
		frm.ddl2ndLiteral.disabled = true; //第二リテラルコンボボックス非活性化
		frm.txt2ndLiteralName.disabled = true; //第二リテラル名 非活性化
		frm.txt2ndSortNo.disabled = true; //第二ソート 非活性化

		frm.txt2ndliteralHissu.value = ""; //第二リテラルの必須マーク削除
		frm.txt2ndHyojiHissu.value = ""; //第二表示順の必須マーク削除
    }
    frm.txtLiteralName.value = funXmlRead(xmlData, "nm_literal", 0);
//    frm.txtValue1.value = funXmlRead(xmlData, "value1", 0);
//    frm.txtValue2.value = funXmlRead(xmlData, "value2", 0);
    frm.txtSortNo.value = funXmlRead(xmlData, "no_sort", 0);
    frm.txtBikou.value = funXmlRead(xmlData, "biko", 0);
    funDefaultIndex(frm.ddlUseEdit, 2);
//    funDefaultIndex(frm.ddlGroup, 3);

    return true;

}

//========================================================================================
//第二リテラルデータ設定処理
//作成者：hisahori
//作成日：2014/10/10
//引数  ：①xmlData ： 第二リテラル情報XML
//概要  ：取得したリテラルデータを画面に設定する
//========================================================================================
function funSetData_2nd(xmlData) {

  var frm = document.frm00;    //ﾌｫｰﾑへの参照

  //画面制御
  if (funXmlRead(xmlData, "flg_edit", 0) == "1" || hidKinoId.value == ConFuncIdSysMgr) {
      //編集可 or ｼｽﾃﾑ管理者
      funGamenControl(false);

  } else {
      //編集不可
      funGamenControl(true);
  }

  //画面情報の設定
  frm.txt2ndLiteralName.value = funXmlRead(xmlData, "nm_2nd_literal", 0); // 第二リテラル名
  if (frm.txt2ndSortNo.value == null){
	  // 値が無ければ空白のみ
  }else{
	  frm.txt2ndSortNo.value = funXmlRead(xmlData, "no_2nd_sort", 0); // 第二リテラルの表示順
  }

  return true;

}

//========================================================================================
// 画面初期化
// 作成者：hisahori
// 作成日：2014/10/10
// 引数  ：なし
// 戻り値：なし
// 概要  ：画面を初期状態に戻す
//========================================================================================
function funInit() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

//    if (hidGamenId.value == ConGmnIdLiteralCsv) {
//        funItemDisabled(frm.ddlLiteral, true);
//        funGamenControl(true);
//    } else {
//        funGamenControl(false);
//    }

    //画面情報の設定
//    frm.txtLiteralCd.value = "";
    frm.txtLiteralName.value = "";
//    frm.txtValue1.value = "";
//    frm.txtValue2.value = "";
    frm.txtSortNo.value = "";
    frm.txtBikou.value = "";
    funCreateComboBox(frm.ddlUseEdit, xmlSA300O, 3);
    funDefaultIndex(frm.ddlUseEdit, 1);
//    funCreateComboBox(frm.ddlGroup, xmlSA050O, 4);
//    frm.ddlGroup.selectedIndex = 0;

}

//========================================================================================
//画面初期化
//作成者：hisahori
//作成日：2014/10/10
//引数  ：mode 1:第２リテラル使用チェックボックスを初期化する  2:
//戻り値：なし
//概要  ：画面を初期状態に戻す
//========================================================================================
function funInit2nd(mode) {

  var frm = document.frm00;    //ﾌｫｰﾑへの参照

//  if (hidGamenId.value == ConGmnIdLiteralCsv) {
//      funItemDisabled(frm.ddlLiteral, true);
//      funGamenControl(true);
//  } else {
//      funGamenControl(false);
//  }

  //画面情報の設定
  if (mode == 1){
	  frm.chk2ndliteral.checked = false;
	  frm.txt2ndLiteralName.disabled = true; //第二リテラル名 非活性化
	  frm.txt2ndSortNo.disabled = true; //第二ソート 非活性化

		frm.txt2ndliteralHissu.value = ""; //第二リテラルの必須マーク削除
		frm.txt2ndHyojiHissu.value = ""; //第二表示順の必須マーク削除
  }
  frm.txt2ndLiteralName.value = ""; // 第二リテラル名
  frm.txt2ndSortNo.value = ""; // 第二リテラルの表示順

}

//========================================================================================
// 画面コントロール制御
// 作成者：M.Jinbo
// 作成日：2009/04/04
// 引数  ：①flg ： 制御値(true:使用不可、false:使用可能)
// 戻り値：なし
// 概要  ：画面コントロールの入力制御を行う
//========================================================================================
function funGamenControl(flg) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

//    funItemReadOnly(frm.txtLiteralCd, true);
    funItemReadOnly(frm.txtLiteralName, flg);
//    funItemReadOnly(frm.txtValue1, flg);
//    funItemReadOnly(frm.txtValue2, flg);
    funItemReadOnly(frm.txtSortNo, flg);
    funItemReadOnly(frm.txtBikou, flg);
    funItemReadOnly(frm.ddlUseEdit, flg);
//    funItemReadOnly(frm.ddlGroup, flg);
    if (hidGamenId.value == ConGmnIdGentyoLiteralMst) {
        funItemDisabled(frm.btnInsert, flg);
        funItemDisabled(frm.btnUpdate, flg);
        funItemDisabled(frm.btnDelete, flg);
    }

}

//========================================================================================
// XMLファイルに書き込み
// 作成者：M.Jinbo
// 作成日：2009/04/04
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
        if (XmlId.toString() == "JSP0610") {
//            switch (i) {
//                case 0:    //USERINFO
//                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
//                    funXmlWrite(reqAry[i], "id_user", "", 0);
//                    break;
//                case 1:    //SA040
//                    funXmlWrite(reqAry[i], "id_user", "", 0);
//                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdLiteralMst, 0);
//                    break;
//                case 2:    //SA050
//                    funXmlWrite(reqAry[i], "id_user", "", 0);
//                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdLiteralMst, 0);
//                    break;
//                case 3:    //SA300
//                    break;
//            }

        //【QP@40404】ADD start 2014/10/10
        } else if (XmlId.toString() == "JSP0611") {
                switch (i) {
                    case 0:    //USERINFO
                        funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                        funXmlWrite(reqAry[i], "id_user", "", 0);
                        break;
                    case 1:    //SA041
                        funXmlWrite(reqAry[i], "id_user", "", 0);
                        funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGentyoLiteralMst, 0);
                        break;
                    case 2:    //SA300
                        break;
              }
        //【QP@40404】ADD start 2014/10/10

        //ｶﾃｺﾞﾘｺﾝﾎﾞ選択
        } else if (XmlId.toString() == "JSP0621"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA111
                    funXmlWrite(reqAry[i], "cd_category", frm.ddlCategory.options[frm.ddlCategory.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 1), 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGentyoLiteralMst, 0);
                    break;
            }

        //ﾘﾃﾗﾙｺﾝﾎﾞ選択
        } else if (XmlId.toString() == "JSP0631"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA101
                    funXmlWrite(reqAry[i], "cd_category", frm.ddlCategory.options[frm.ddlCategory.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_literal", frm.ddlLiteral.options[frm.ddlLiteral.selectedIndex].value, 0);
                    break;
            }

        //第二ﾘﾃﾗﾙｺﾝﾎﾞ選択
        } else if (XmlId.toString() == "JSP0632"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA102
                    funXmlWrite(reqAry[i], "cd_category", frm.ddlCategory.options[frm.ddlCategory.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_literal", frm.ddlLiteral.options[frm.ddlLiteral.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_2nd_literal", frm.ddl2ndLiteral.options[frm.ddl2ndLiteral.selectedIndex].value, 0);
                    break;
            }

        //登録、更新、削除ﾎﾞﾀﾝ押下
        } else if (XmlId.toString() == "JSP0641"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA331
                    funXmlWrite(reqAry[i], "cd_category", frm.ddlCategory.options[frm.ddlCategory.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_literal", frm.ddlLiteral.options[frm.ddlLiteral.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "flg_edit", frm.ddlUseEdit.options[frm.ddlUseEdit.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "kbn_shori", frm.hidEditMode.value, 0);
                    funXmlWrite(reqAry[i], "cd_2nd_literal", frm.ddl2ndLiteral.options[frm.ddl2ndLiteral.selectedIndex].value, 0);
                    if(frm.chk2ndliteral.checked == true){
                        funXmlWrite(reqAry[i], "flg_2ndedit", "1", 0);
                    }else{
                        funXmlWrite(reqAry[i], "flg_2ndedit", "", 0);

                    }
                    break;
            }

        //Excelﾎﾞﾀﾝ押下
        } else if (XmlId.toString() == "JSP0650"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA320
                    funXmlWrite(reqAry[i], "cd_category", frm.ddlCategory.options[frm.ddlCategory.selectedIndex].value, 0);
                    break;
            }
        }
    }

    return true;

}

//========================================================================================
// コンボボックス作成処理
// 作成者：M.Jinbo
// 作成日：2009/04/04
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
        case 1:    //ｶﾃｺﾞﾘﾏｽﾀ
            //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
            funClearSelect(obj, 2);

            atbName = "nm_category";
            atbCd = "cd_category";
            break;
        case 2:    //ﾘﾃﾗﾙﾏｽﾀ
            //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
            funClearSelect(obj, 2);

            atbName = "nm_literal";
            atbCd = "cd_literal";
            break;
        case 3:    //編集可否
            //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
            funClearSelect(obj, 1);

            atbName = "nm_editflg";
            atbCd = "cd_editflg";
            break;
        case 4:    //ｸﾞﾙｰﾌﾟﾏｽﾀ
            //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
            funClearSelect(obj, 2);

            atbName = "nm_group";
            atbCd = "cd_group";
            break;
        case 5:    //第二リテラル
            //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
            funClearSelect(obj, 2);

            atbName = "nm_2nd_literal";
            atbCd = "cd_2nd_literal";
            break;
    }

    for (i = 0; i < reccnt; i++) {
        if (funXmlRead(xmlData, atbCd, i) != "" && funXmlRead(xmlData, atbName, i) != "") {
            objNewOption = document.createElement("option");
            obj.options.add(objNewOption);
            if (mode == 2) {
                //ﾘﾃﾗﾙｺﾝﾎﾞ(ｺｰﾄﾞ＋名称)
                objNewOption.innerText = ("000" + funXmlRead(xmlData, atbCd, i)).slice(-3) + "：" + funXmlRead(xmlData, atbName, i);
            } else {
                //ﾘﾃﾗﾙｺﾝﾎﾞ以外(名称のみ)
                objNewOption.innerText = funXmlRead(xmlData, atbName, i);
            }
            objNewOption.value = funXmlRead(xmlData, atbCd, i);
        }
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽのﾃﾞﾌｫﾙﾄ表示
    obj.selectedIndex = 0;

    return true;
}

//========================================================================================
// デフォルト値選択処理
// 作成者：hisahori
// 作成日：2014/10/10
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
            case 1:    //編集可否ｺﾝﾎﾞ
                if (obj.options[i].value == 1) {
                    selIndex = i;
                }
                break;
            case 2:    //編集可否ｺﾝﾎﾞ
                if (obj.options[i].value == funXmlRead(xmlSA101O, "flg_edit", 0)) {
                    selIndex = i;
                }
                break;
//            case 3:    //ｸﾞﾙｰﾌﾟｺﾝﾎﾞ
//                if (obj.options[i].value == funXmlRead(xmlSA101O, "cd_group", 0)) {
//                    selIndex = i;
//                }
//                break;
        }
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽのｲﾝﾃﾞｯｸｽ指定
    obj.selectedIndex = selIndex;

    return true;
}

//========================================================================================
//第二リテラル使用チェックボックス  オン／オフ
//作成者：hisahori
//作成日：2014/10/10
//引数  ：
//概要  ：第二リテラルコンボボックスの活性化／非活性化
//========================================================================================
function click_2ndliteral(){

	var frm = document.frm00;    //ﾌｫｰﾑへの参照

	//オン時
	if(frm.chk2ndliteral.checked == true){
		frm.ddl2ndLiteral.disabled = false;
		frm.txt2ndLiteralName.disabled = false; //第二リテラル名 活性化
		frm.txt2ndSortNo.disabled = false; //第二ソート 活性化

		frm.txt2ndliteralHissu.value = "（必須）"; //第二リテラルの必須マーク付与
		frm.txt2ndHyojiHissu.value = "（必須）"; //第二表示順の必須マーク付与
	}
	//オフ時
	else{
		frm.ddl2ndLiteral.disabled = true;
		frm.txt2ndLiteralName.value = ''; //第二リテラル名 空白
		frm.txt2ndSortNo.value = ''; //第二ソート 空白
		frm.txt2ndLiteralName.disabled = true; //第二リテラル名 非活性化
		frm.txt2ndSortNo.disabled = true; //第二ソート 非活性化

		frm.txt2ndliteralHissu.value = ""; //第二リテラルの必須マーク削除
		frm.txt2ndHyojiHissu.value = ""; //第二表示順の必須マーク削除
	}
}

