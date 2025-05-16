//========================================================================================
// 初期表示処理
// 作成者：M.Jinbo
// 作成日：2009/03/19
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //画面設定
    funInitScreen(ConLoginId);

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //シングルサインオンの場合
    if (frm.hidMode.value == "1") {
        //ﾕｰｻﾞ認証を行う
        funChkLogin();
    }

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    //ﾌｫｰｶｽ設定
    frm.txtUserId.focus();

    return true;
}

//========================================================================================
// ログイン情報チェック処理
// 作成者：M.Jinbo
// 作成日：2009/03/19
// 概要  ：ユーザの認証を行う
//========================================================================================
function funChkLogin() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP0010";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA010");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA010I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA010O);

    // ADD 2013/9/25 okano【QP@30151】No.28 start
    var XmlId2 = "JSP0020";
    var FuncIdAry2 = new Array(ConResult,ConUserInfo,"SA020");
    var xmlReqAry2 = new Array(xmlUSERINFO_I,xmlSA020I);
    var xmlResAry2 = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA020O);
    // ADD 2013/9/25 okano【QP@30151】No.28 end

    //引数をXMLﾌｧｲﾙに設定
    // MOD 2013/9/25 okano【QP@30151】No.28 start
//	    if (funReadyOutput(xmlReqAry) == false) {
//	        //ﾛｸﾞｲﾝ画面からの起動に切替
//	        frm.hidMode.value = "2";
//	        funClearRunMessage();
//	        return false;
//	    }
//
//	    //ﾕｰｻﾞ認証を行う
//	    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0010, xmlReqAry, xmlResAry, 2) == false) {
//	        //ﾛｸﾞｲﾝ画面からの起動に切替
//	        frm.hidMode.value = "2";
//	        return false;
//	    } else {
//	        //ﾒﾆｭｰに遷移
//	        funNext();
//	    }
//
//	    return true;

    if (funReadyOutput(xmlReqAry)) {
        if (funReadyOutput(xmlReqAry2)) {
        	//ﾕｰｻﾞ認証を行う
            if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0010, xmlReqAry, xmlResAry, 2) == false) {
                //ﾛｸﾞｲﾝ画面からの起動に切替
                frm.hidMode.value = "2";
                return false;
            } else {
            	if(funXmlRead(xmlResAry[2],"flg_sign",0) == "true"){
//DEL 2014/06/06 hisahori 【QP@30154】課題No.41 シングルサインオン時、キャッシュレスからの新ユーザマスタ値を参照しない
//            		funSignonLogin();
//DEL 2014/06/06
            	} else {
	            	//ﾊﾟｽﾜｰﾄﾞﾁｪｯｸ
	            	if(funAjaxConnection(XmlId2, FuncIdAry2, xmlJSP0020, xmlReqAry2, xmlResAry2, 2) == false){
	                    //ﾒﾆｭｰに遷移
	            		var wUrl;
	                	if(funXmlRead(xmlResAry[2],"flg_eigyo",0) == "true"){
	                		wUrl = "../SQ150EigyoTantoMst/SQ150EigyoTantoMst.jsp";
	                	} else {
	                		wUrl = "../SQ080TantoMst/SQ080TantoMst.jsp";
	                	}
	                	frm.hidUserId.value = funXmlRead(xmlResAry[1],"id_user",0);
	                	funPassLogin(wUrl);
	            	} else {
	            		funNext();
	            	}
            	}
                return true;
            }
        }
    }

    //ﾛｸﾞｲﾝ画面からの起動に切替
    frm.hidMode.value = "2";
    funClearRunMessage();
    return false;
    // MOD 2013/9/25 okano【QP@30151】No.28 end

}

//========================================================================================
// XMLファイルに書き込み
// 作成者：M.Jinbo
// 作成日：2009/03/19
// 引数  ：①reqAry :機能ID別送信XML(配列)
// 戻り値：なし
// 概要  ：機能IDごとのXMLファイルに引数を設定する
//========================================================================================
function funReadyOutput(reqAry) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var i;

    for (i = 0; i < reqAry.length; i++) {
        switch (i) {
            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", 2, 0);
                if (frm.hidMode.value == "1") {
                    funXmlWrite(reqAry[i], "id_user", frm.hidUserId.value, 0);
                } else {
                    funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
                }
                break;

            case 1:    //SA010
                if (frm.hidMode.value == "1") {
                    funXmlWrite(reqAry[i], "id_user", frm.hidUserId.value, 0);
                } else {
                    funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
                }
                funXmlWrite(reqAry[i], "password", frm.txtPass.value, 0);
                funXmlWrite(reqAry[i], "kbn_login", frm.hidMode.value, 0);
                break;
        }
    }

    return true;

}

//========================================================================================
// メインメニュー画面遷移処理
// 作成者：M.Jinbo
// 作成日：2009/03/19
// 概要  ：メニュー画面に遷移する
//========================================================================================
function funNext() {

    var wUrl;

    wUrl = "../SQ020MainMenu/SQ020MainMenu.jsp";

    //ﾒｲﾝﾒﾆｭｰを表示
    funUrlConnect(wUrl, ConConectPost, document.frm00);
//    window.open(wUrl, "shisaquick", "menubar=no,resizable=yes");

    return true;
}

//========================================================================================
// 終了ボタン押下
// 作成者：M.Jinbo
// 作成日：2009/03/19
// 概要  ：画面を終了する
//========================================================================================
function funClose() {

    //確認ﾒｯｾｰｼﾞ表示
    if (funConfMsgBox("ログイン" + I000001) == ConBtnYes) {
        //画面を閉じる
        close();
    }

    return true;
}

//========================================================================================
// 【QP@00342】営業登録へボタン押下
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 概要  ：営業登録用の仮ユーザでログインし、担当者マスタメンテ（営業）へ遷移
//========================================================================================
function funEigyoLogin() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN2110";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput_Eigyo(xmlReqAry) == false) {
        funClearRunMessage();
        return false;
    }

    //ﾕｰｻﾞ認証を行う
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2110, xmlReqAry, xmlResAry, 2) == false) {
        return false;
    } else {
        //担当者マスタメンテ（営業）に遷移
        funNext_Eigyo();
    }

    return true;

}

//========================================================================================
// 【QP@00342】XMLファイルに書き込み（営業登録へ）
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①reqAry :機能ID別送信XML(配列)
// 戻り値：なし
// 概要  ：機能IDごとのXMLファイルに引数を設定する
//========================================================================================
function funReadyOutput_Eigyo(reqAry) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var i;

    for (i = 0; i < reqAry.length; i++) {
        switch (i) {
            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", 2, 0);
                funXmlWrite(reqAry[i], "id_user", "9090909090", 0);

                if(frm.hidUserId.value == ""){

                }
                else{
                	funBuryZero(frm.hidUserId, 10);
                	funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", frm.hidUserId.value + ":::", 0);
                }

                break;
        }
    }

    return true;

}


//========================================================================================
// 【QP@00342】担当者マスタメンテ（営業）画面遷移処理
// 作成者：M.Jinbo
// 作成日：2009/03/19
// 概要  ：メニュー画面に遷移する
//========================================================================================
function funNext_Eigyo() {

    var wUrl;

    wUrl = "../SQ150EigyoTantoMst/SQ150EigyoTantoMst.jsp";

    //担当者マスタメンテ（営業）を表示
    funUrlConnect(wUrl, ConConectPost, document.frm00);

    return true;
}

// ADD 2013/9/25 okano【QP@30151】No.28 start
//========================================================================================
//【QP@00342】ｼﾝｸﾞﾙｻｲﾝｵﾝ-ｷｬｯｼｭﾚｽｱｸｾｽ時
//作成者：Okano
//作成日：2013/09/20
//概要  ：登録用の仮ユーザでログインし、各部署に対応した担当者マスタメンテへ遷移
//========================================================================================
function funSignonLogin() {

 var frm = document.frm00;    //ﾌｫｰﾑへの参照
 var XmlId = "RGEN2110";
 var FuncIdAry = new Array(ConResult,ConUserInfo);
 var xmlReqAry = new Array(xmlUSERINFO_I);
 var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

 //引数をXMLﾌｧｲﾙに設定
 if (funReadyOutput_Signon(xmlReqAry) == false) {
     funClearRunMessage();
     return false;
 }

 //ﾕｰｻﾞ認証を行う
 if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2110, xmlReqAry, xmlResAry, 2) == false) {
     return false;
 } else {
     //担当者マスタメンテに遷移
     funNext_Signon();
 }

 return true;

}

//========================================================================================
//【QP@00342】XMLファイルに書き込み
//作成者：Okano
//作成日：2013/09/20
//引数  ：①reqAry :機能ID別送信XML(配列)
//戻り値：なし
//概要  ：機能IDごとのXMLファイルに引数を設定する
//========================================================================================
function funReadyOutput_Signon(reqAry) {

 var frm = document.frm00;    //ﾌｫｰﾑへの参照
 var i;

 for (i = 0; i < reqAry.length; i++) {
     switch (i) {
         case 0:    //USERINFO
             funXmlWrite(reqAry[i], "kbn_shori", 2, 0);
             funXmlWrite(reqAry[i], "id_user", "9090909092", 0);

             if(frm.hidUserId.value == ""){

             }
             else{
             	funBuryZero(frm.hidUserId, 10);
             	funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", frm.hidUserId.value + ":::", 0);
             }

             break;
     }
 }

 return true;

}

//========================================================================================
//【QP@00342】担当者マスタメンテ画面遷移処理
//作成者：M.Jinbo
//作成日：2009/03/19
//概要  ：メニュー画面に遷移する
//========================================================================================
function funNext_Signon() {

    var wUrl;

    if(funXmlRead(xmlSA010O,"flg_eigyo",0) == "true"){
        wUrl = "../SQ150EigyoTantoMst/SQ150EigyoTantoMst.jsp";
    } else {
        wUrl = "../SQ080TantoMst/SQ080TantoMst.jsp";
    }

    //担当者マスタメンテを表示
    funUrlConnect(wUrl, ConConectPost, document.frm00);

    return true;
}

//========================================================================================
//【QP@00342】パスワードチェック失敗時
//作成者：Y.Nishigawa
//作成日：2011/01/28
//概要  ：パスワード変更用の仮ユーザでログインし、ユーザに対応した担当者マスタメンテへ遷移
//========================================================================================
function funPassLogin(wUrl) {

var frm = document.frm00;    //ﾌｫｰﾑへの参照
var XmlId = "RGEN2110";
var FuncIdAry = new Array(ConResult,ConUserInfo);
var xmlReqAry = new Array(xmlUSERINFO_I);
var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

//引数をXMLﾌｧｲﾙに設定
if (funReadyOutput_Pass(xmlReqAry) == false) {
   funClearRunMessage();
   return false;
}

//ﾕｰｻﾞ認証を行う
if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2110, xmlReqAry, xmlResAry, 2) == false) {
   return false;
} else {
   //担当者マスタメンテに遷移
   funUrlConnect(wUrl, ConConectPost, document.frm00);
}

return true;

}

//========================================================================================
//【QP@00342】XMLファイルに書き込み（営業登録へ）
//作成者：Y.Nishigawa
//作成日：2011/01/28
//引数  ：①reqAry :機能ID別送信XML(配列)
//戻り値：なし
//概要  ：機能IDごとのXMLファイルに引数を設定する
//========================================================================================
function funReadyOutput_Pass(reqAry) {

var frm = document.frm00;    //ﾌｫｰﾑへの参照
var i;

for (i = 0; i < reqAry.length; i++) {
   switch (i) {
       case 0:    //USERINFO
           funXmlWrite(reqAry[i], "kbn_shori", 2, 0);
           funXmlWrite(reqAry[i], "id_user", "9090909091", 0);

           if(frm.hidUserId.value == ""){

           }
           else{
           	funBuryZero(frm.hidUserId, 10);
           	funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", frm.hidUserId.value + ":::", 0);
           }

           break;
   }
}

return true;

}
// ADD 2013/9/25 okano【QP@30151】No.28 end

//ADD 2015/07/27 TT.Kitazawa【QP@40812】No.14 start
//========================================================================================
//【QP@40812】ヘルプ画面を表示
//作成者：E.Kitazawa
//作成日：2015/07/27
//引数  ：なし
//戻り値：なし
//概要  ：ヘルプ画面を表示する
//========================================================================================
function funHelpDisp() {
 var frm = document.frm00;    //ﾌｫｰﾑへの参照

 var XmlId = "JSP9030";
 var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2130");
 var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2130I);
 var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2130O);

 //引数をXMLﾌｧｲﾙに設定
 if (funReadyOutput_Eigyo(xmlReqAry) == false) {
     funClearRunMessage();
     return false;
 }

 //ユーザ情報取得のみ
 if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP9030, xmlReqAry, xmlResAry, 3) == false) {
     return false;
 }

 // ヘルプファイルパス取得：フォームに保存する
 frm.strHelpPath.value = funXmlRead_3(xmlResAry[2], "table", "help_file", 0, 0);

 // ヘルプファイル呼出し
 funHelpCall();
 return true;

}
//ADD 2015/07/27 TT.Kitazawa【QP@40812】No.14 end


