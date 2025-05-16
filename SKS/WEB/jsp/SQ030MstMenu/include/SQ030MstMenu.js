//========================================================================================
// 初期表示処理
// 作成者：M.Jinbo
// 作成日：2009/03/19
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var obj;

    //画面設定
    funInitScreen(ConMstMenuId);

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //ﾕｰｻﾞｾｯｼｮﾝ情報を取得
    funGetUserInfo(1);

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    obj = document.getElementById("btnMenu");
    if (obj && obj.type == "button") {
        //ﾌｫｰｶｽ設定
        frm.btnMenu[0].focus();
    }

    return true;
}

//========================================================================================
// セッション情報取得処理
// 作成者：M.Jinbo
// 作成日：2009/03/19
// 引数  ：①Mode  ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 概要  ：ユーザのセッション情報を取得する
//========================================================================================
function funGetUserInfo(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP9030";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2130");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2130I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2130O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //ｾｯｼｮﾝ情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP9030, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //ﾕｰｻﾞ情報表示
    funInformationDisplay(xmlResAry[1], 1, "divUserInfo");

    //ﾎﾞﾀﾝ表示
    funCreateMenuButton(xmlResAry[1], ConMstMenuId, "divBtn");

    //【QP@00342】タイトル表示
    var flg_busho = funXmlRead_3(xmlResAry[2], "table", "flg_kenkyu", 0, 0);
    var obj = document.getElementById("divTitle");
    if( flg_busho == "1" ){
    	obj.innerHTML = "<font style=\"color:#FFFDC8;font-size:32px;font-style:bold;\">◆シサクイック◆</font>";
    }
    else{
    	ConSystemId = ConSystemId_genka;
    	obj.innerHTML = "<font style=\"color:#FFFDC8;font-size:32px;font-style:bold;\">◆原価試算システム◆</font>";
    	//画面設定
	    funInitScreen(ConMainMenuId);
    }

    //ADD 2015/07/27 TT.Kitazawa【QP@40812】No.14 start
    // ヘルプファイルパス取得
    frm.strHelpPath.value = funXmlRead_3(xmlResAry[2], "table", "help_file", 0, 0);
    //ADD 2015/07/27 TT.Kitazawa【QP@40812】No.14 end

    return true;

}

//========================================================================================
// XMLファイルに書き込み
// 作成者：M.Jinbo
// 作成日：2009/03/19
// 引数  ：①reqAry :機能ID別送信XML(配列)
//       ：②Mode           ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：なし
// 概要  ：機能IDごとのXMLファイルに引数を設定する
//========================================================================================
function funReadyOutput(reqAry, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var i;

    for (i = 0; i < reqAry.length; i++) {
        switch (i) {
            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;
            case 1:    //【QP@00342】FGEN2130
                break;
        }
    }

    return true;

}

//========================================================================================
// 次画面遷移処理
// 作成者：M.Jinbo
// 作成日：2009/03/19
// 概要  ：次画面に遷移する
//========================================================================================
function funNext(mode) {

    var wUrl;

    //遷移先判定
    switch (mode) {
        case 0:    //メインメニュー
            wUrl = "../SQ020MainMenu/SQ020MainMenu.jsp";
            break;
        case 1:    //原料分析情報マスタ
            wUrl = "../SQ050GenryoInfoMst/SQ050GenryoInfoMst.jsp";
            break;
        case 2:    //全工場単価歩留
            wUrl = "../SQ100TankaBudomari/SQ100TankaBudomari.jsp";
            break;
        case 3:    //リテラルマスタ
            wUrl = "../SQ060LiteralMst/SQ060LiteralMst.jsp";
            break;
        case 4:    //グループマスタ
            wUrl = "../SQ070GroupMst/SQ070GroupMst.jsp";
            break;
        case 5:    //権限マスタ
            wUrl = "../SQ090KengenMst/SQ090KengenMst.jsp";
            break;
        case 6:    //担当者マスタ
            wUrl = "../SQ080TantoMst/SQ080TantoMst.jsp";
            break;
        case 7:    //【QP@00342】担当者マスタ（営業）
            wUrl = "../SQ150EigyoTantoMst/SQ150EigyoTantoMst.jsp";
            break;
        case 9:    //ログイン
            wUrl = "../SQ010Login/SQ010Login.jsp";
            break;
    }

    //遷移
    funUrlConnect(wUrl, ConConectPost, document.frm00);

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
    if (funConfMsgBox("マスタメニュー" + I000001) == ConBtnYes) {
        //画面を閉じる
        close();
    }

    return true;
}

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

	// ヘルプファイルパスはフォームに保存済
	// ヘルプファイル呼出し
	funHelpCall()
	return true;

}
//ADD 2015/07/27 TT.Kitazawa【QP@40812】No.14 end
