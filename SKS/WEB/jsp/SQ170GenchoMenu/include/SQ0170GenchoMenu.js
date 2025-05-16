//========================================================================================
// 初期表示処理
// 作成者：M.Sakamoto
// 作成日：2014/02/19
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var obj;

    //画面設定
    funInitScreen(ConGenchoMenuId);

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //ﾕｰｻﾞｾｯｼｮﾝ情報を取得
    funGetUserInfo(1);

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    obj = document.getElementById("btnMenu");

    if (obj && obj.type == "button") {
    	if (!frm.btnMenu.length) {
    		// ボタンが1つしかない時
    		frm.btnMenu.focus();

    	} else {
    		//ﾌｫｰｶｽ設定
    		frm.btnMenu[0].focus();
    	}
    }
    //画面設定
    funInitScreen(ConGenchoMenuId);

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
	// ADD 2016/09/01 May Thu 【KPX@1602367】Update Start
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2130","FGEN3490");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2130I,xmlFGEN3490I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2130O,xmlFGEN3490O);
	// ADD 2016/09/01 May Thu 【KPX@1602367】Update Start

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
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
    funCreateMenuButton(xmlResAry[1], ConGenchoMenuId, "divBtn");
	//maythu add start
 	funOrderInfoDisplay(xmlResAry[3], 1, "divOrderInfo");
	//maythu add end
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
function funReadyOutput(XmlId, reqAry, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var i;

    for (i = 0; i < reqAry.length; i++) {

        // 画面初期表示
        if (XmlId.toString() == "JSP9030") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:
                    break;
            }
        // コストテーブル登録・承認ボタン押下
        // 「ベース単価登録・承認」メニューボタン押下
        } else if (XmlId.toString() == "RGEN2160"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", "1::::::::::::1", 0);
                    break;
            }


    } else if (XmlId.toString() == "RGEN3200"){
        switch (i) {
            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", "1", 0);
                break;
        }


      // 資材手配依頼書起動情報通知
    }  else if (XmlId.toString() == "RGEN3390"){
            // XMLより試作コード取得
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
        case 3:    //デザインスペース登録
            wUrl = "../SQ210DesignSpaceAdd/SQ210DesignSpaceAdd.jsp";
            funUrlConnect(wUrl, ConConectPost, document.frm00);
            return true;
            break;
        case 7:    //原資材調達部 カテゴリマスタメンテナンス
            wUrl = "../SQ061GentyoLiteralMst/SQ061GentyoLiteralMst.jsp";
            break;
         // 【QP@40404】2014/09/01 TT E.Kitazawa add end
        case 9:    //ログイン
            wUrl = "../SQ010Login/SQ010Login.jsp";
            break;
//		// 【KPX@1602367】2016/08/31 May Thu add end
	  }
	  if(wUrl != null) {
         //遷移
         funUrlConnect(wUrl, ConConectPost, document.frm00);
         return true;
      } else {
         return funOpen(mode);
	  }
}

//========================================================================================
// 画面起動処理
// 作成者：H.Shima
// 作成日：2014/09/12
// 概要  ：画面を起動する
//========================================================================================
function funOpen(mode) {

    var wUrl;

    if(funTuti(mode)){
	    //遷移先判定
    	switch (mode) {
        	case 1:    //コストテーブル登録・承認
            	funCostTblAddTuti(1);
            	wUrl = "../SQ190CostTblAdd/SQ190CostTblAdd.jsp";
            	break;
        	case 2:    //コストテーブル一覧
            	wUrl = "../SQ180CostTblList/SQ180CostTblList.jsp";
            	break;
            case 4:    //デザインスペースダウンロード
            	wUrl = "../SQ240DesignSpaceDL/SQ240DesignSpaceDL.jsp";
            	break;
        	case 5:    //資材手配入力
        		funShizaiCodeListAddTuti(1);
            	wUrl = "../SQ230ShizaiTehaiInput/SQ230ShizaiTehaiInput.jsp";
            	break;
			// 【KPX@1602367】2016/08/31 May Thu add start
			case 10:   //ベース単価登録・承認
				funBasePriceAddTuti(1);
		    	wUrl = "../SQ280BasePriceAdd/SQ280BasePriceAdd.jsp";
		    	break;
			case 11:   //ベース単価一覧
		    	wUrl = "../SQ270BasePriceList/SQ270BasePriceList.jsp";
		    	break;
        	case 12:   //資材手配済一覧
        		funShizaiCodeListAddTuti(1);
				var logic = "shizaiTehai"
				wUrl = "../SQ260ShizaiCodeList/SQ260ShizaiCodeList.jsp" + "?" + logic;
		    	break;
        	case 13:   //発注先マスタ画面
            	wUrl = "../SQ310HattyuLiteralMst/SQ310HattyuLiteralMst.jsp";
		    	break;
			// 【KPX@1602367】2016/08/31 May Thu add end
        	case 14:
            	wUrl = "../SQ250ShizaiTehaiOutput/ShizaiTehaiOutput.jsp";
            	break;
        	case 15:
            	wUrl = "../SQ200CostTblRef/SQ200CostTblRef.jsp";
            	break;
            case 16:
            	wUrl = GenchoUrl;
            	break;
        }

        //遷移
        if(wUrl == GenchoUrl){
        	var chromeURL = decodeURIComponent(wUrl);
        	var lngWidth  = self.screen.width - 12;
        	var lngHeight = self.screen.height - 167;
			lngHeight = lngHeight + 109;
			lngWidth = lngWidth   + 2;
			//var strName	  = "NEBICK";
			var strOpt = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=no,width=" + lngWidth + ",height=" + lngHeight + ",left=0,top=0";
			var strBrowser = "legacyBrowser";
			var strOptChrome = "--start-maximized  --new-window ";
			openWindow(strBrowser, chromeURL, "", [strOpt, strOptChrome,""]);
        }else{
          var win = window.open(wUrl, "gensi","menubar=no,resizable=yes");

        	// 再表示の為にフォーカスにする
        	win.focus();
        }
    }
    return true;
}
function openWindow(targetBrowser, url, title, options) {
	var command = "";
	var chromeURL = "";
	var ret = 0;
	// 指定されたブラウザでの起動
	switch (targetBrowser) {
	case "legacyBrowser":
		window.open(url, title, options[0]);
		break;
	default:
		break;
	}
	// 指定なし、または指定のブラウザがインストールされていない
	if (targetBrowser == "" || ret == -2) {
		window.open(url, title, options[0]);
	}

	return true;
}

function funTuti(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN3390";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3390, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

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
    if (funConfMsgBox("原資材調達部メニュー" + I000001) == ConBtnYes) {
        //画面を閉じる
        close();
    }

    return true;
}

//========================================================================================
// コストテーブル登録・承認画面起動情報通知
// 作成者：Y.Nishigawa
// 作成日：2009/10/27
// 引数  ：なし
// 概要  ：選択した試作コードをセッションへ保存する
//========================================================================================
function funCostTblAddTuti(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN2160";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2160, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    return true;

}

//========================================================================================
//資材手配済一覧
//作成者：nakamura
//作成日：2016/11/15
//引数  ：なし
//概要  ：選択した試作コードをセッションへ保存する
//========================================================================================
function funShizaiCodeListAddTuti(mode) {

 var frm = document.frm00;    //ﾌｫｰﾑへの参照
 var XmlId = "RGEN3200";
 var FuncIdAry = new Array(ConResult,ConUserInfo);
 var xmlReqAry = new Array(xmlUSERINFO_I);
 var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

 //引数をXMLﾌｧｲﾙに設定
 if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
     return false;
 }

 //ｾｯｼｮﾝ情報、共通情報を取得
 if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2160, xmlReqAry, xmlResAry, mode) == false) {
     return false;
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

// ADD 2016/09/29 Koizumi 【KPX@1602367】Start
//========================================================================================
// コストテーブル登録・承認画面起動情報通知
// 作成者：BRC Koizumi
// 作成日：2016/09/29
// 引数  ：なし
// 概要  ：選択した試作コードをセッションへ保存する
//========================================================================================
function funBasePriceAddTuti(mode) {

 var frm = document.frm00;    //ﾌｫｰﾑへの参照
 var XmlId = "RGEN2160";
 var FuncIdAry = new Array(ConResult,ConUserInfo);
 var xmlReqAry = new Array(xmlUSERINFO_I);
 var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

 //引数をXMLﾌｧｲﾙに設定
 if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
     return false;
 }

 //ｾｯｼｮﾝ情報、共通情報を取得
 if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2160, xmlReqAry, xmlResAry, mode) == false) {
     return false;
 }

 return true;

}
//ADD 2016/09/29 Koizumi 【KPX@1602367】End
