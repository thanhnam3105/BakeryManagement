var modeBasePriceAdd = 1;
var backBasePriceAdd = 1;

//========================================================================================
// 初期表示処理
// 作成者：BRC Koizumi
// 作成日：2016/09/06
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {
    //画面設定
    funInitScreen(ConBasePriceAddId);

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //処理中ウィンドウ表示の為、setTimeoutで処理予約
    setTimeout(function(){ funGetInfo(1) }, 0);

    return true;

}

//========================================================================================
// ヘッダ作成処理
// 作成者：BRC Koizumi
// 作成日：2016/09/06
// 引数  ：なし
// 概要  ：ヘッダを作成する
//========================================================================================
function funCreateCostTbl(xmlData) {

    var key;
    var id;
    var mode;

    // 処理モードを取得する
    mode = parseInt(funXmlRead(xmlData, "mode", 0));

    for (i = 0; i < 31; i++) {

        if(i == 0) {

            for (j = 1; j < 31; j++) {

                key = "no_value" + funZeroPudding(j);
                id  = "txtHeader_" + j;

                // カンマ編集
                document.getElementById(id).innerText = funAddComma(funNumFormatChange(funXmlRead(xmlData, key, i),0));
                // 承認
                if (mode == 4) {
                    document.getElementById(id).readOnly = true;
                }
            }

        } else {

            key = "nm_title";
            id  = "nm_title_" + i;

            document.getElementById(id).value = funXmlRead(xmlData, key, i);
            // 承認
            if (mode == 4) {
                document.getElementById(id).readOnly = true;
            }

            for (j = 1; j < 31; j++) {
                key = "no_value" + funZeroPudding(j);
                id  = "no_value_" + i + "_" + j;

                // カンマ編集
                // 少数点以下2位まで表示（3位以下切り捨て）
                document.getElementById(id).value = funAddComma(funNumFormatChange(funXmlRead(xmlData, key, i), 2));
                // 承認
                if (mode == 4) {
                    document.getElementById(id).readOnly = true;
                }
            }
        }
    }

    // 承認:有効開始日、備考制御
    if (mode == 4) {
        document.getElementById("txtYuko").readOnly = true;
        document.getElementById("txtBiko").readOnly = true;
    }
}

//========================================================================================
// 個装使用量計算処理
// 作成者：BRC Koizumi
// 作成日：2016/09/06
// 引数  ：なし
// 概要  ：個装使用量を計算する
//========================================================================================
function funCalc() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var key;
    var headerId;
    var calcRsltId;
    var val;

    //  個装使用量 < 1 を許可する
    if(frm.txtShiyoRyo.value == "" || parseFloat(frm.txtShiyoRyo.value) == 0) {
        return;
    }

    for (i = 0; i < 30; i++) {
        headerId  = "txtHeader_" + (i + 1);
        val = document.getElementById(headerId).value.replace(/,/g,"");
        if(val != ""){
            calcRsltId  = "calcRslt" + (i + 1);
            // 小数点以下切り捨て
            document.getElementById(calcRsltId).value = funAddComma(Math.floor(val / frm.txtShiyoRyo.value.replace(/,/g,"")));
        }
    }
}


//========================================================================================
// アップ率計算処理
// 作成者：BRC Koizumi
// 作成日：2016/09/06
// 引数  ：なし
// 概要  ：アップ率より単価を計算する
//========================================================================================
function funUpRituCalc() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var key;
    var valueId;
    var val;
    var total;
    var row;
    var col;

    if(frm.txtUpRitu.value == "" || parseInt(frm.txtUpRitu.value) == 0) {
        return;
    }

    for (row = 0; row < 30; row++) {
        for (col = 0; col < 30; col++) {
            valueId  = "no_value_" + (row + 1) + "_"  + (col + 1);
            val = document.getElementById(valueId).value.replace(/,/g,"");;
            if(val != ""){
                total = val * (frm.txtUpRitu.value / 100);

                // 少数点2位まで表示（3位以下切り捨て）
                document.getElementById(valueId).value =
                	funAddComma(
                			funNumFormatChange(
                					funRound(parseFloat(val) + parseFloat(total), 3).toString()
                					,2
                			)
                	);
                funCheckChange();
            }
        }
    }
}

////========================================================================================
//// クリアボタン押下処理
//// 作成者：BRC Koizumi
//// 作成日：2016/09/06
//// 引数  ：なし
//// 概要  ：画面を初期化する
////========================================================================================
//function funClear() {
//
//    var frm = document.frm00;    //ﾌｫｰﾑへの参照
//
//    //画面の初期化
//    frm.reset();
//    frm.ddlMakerName.selectedIndex = 0;
//    frm.ddlHouzai.selectedIndex = 0;
//
//    //一覧のｸﾘｱ
//    funClearList();
//
//    //ﾃﾞﾌｫﾙﾄのｺﾝﾎﾞﾎﾞｯｸｽ設定値を読み込む
//    xmlFGEN3000O.load(xmlFGEN3000);
//    xmlFGEN3010O.load(xmlFGEN3010);
//
//    //ｺﾝﾎﾞﾎﾞｯｸｽの再設定
//    funCreateComboBox(frm.ddlMakerName, xmlFGEN3000O, 1);
//    funCreateComboBox(frm.ddlHouzai, xmlFGEN3010O, 2);
//
//    return true;
//
//}
//
////========================================================================================
//// 一覧クリア処理
//// 作成者：BRC Koizumi
//// 作成日：2016/09/06
//// 引数  ：なし
//// 概要  ：一覧の情報を初期化する
////========================================================================================
//function funClearList() {
//
//    //一覧のｸﾘｱ
//    xmlFGEN3520O.src = "";
//    tblList.style.display = "none";
//
//}

//========================================================================================
// 画面情報取得処理
// 作成者：BRC Koizumi
// 作成日：2016/09/06
// 引数  ：①mode  ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：ユーザのセッション情報、コンボボックスの情報を取得する
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN3520";

    // 包材情報を取得しない
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3000","FGEN3520");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3000I,xmlFGEN3520I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3000O,xmlFGEN3520O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3520, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //ﾕｰｻﾞ情報表示
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //ｺﾝﾎﾞﾎﾞｯｸｽの作成
    funCreateComboBox(frm.ddlMakerName, xmlResAry[2], 1);

    //ﾃﾞｰﾀ件数のﾁｪｯｸ
    if (funXmlRead(xmlResAry[3], "flg_return", 0) == "true") {
    	// 一覧作成
    	funCreateCostTbl(xmlResAry[3]);

        funDefaultIndex(frm.ddlMakerName, xmlResAry[3], 1);
        // 包材コンボボックスの取得・設定
        funChangeMaker();
        funDefaultIndex(frm.ddlHouzai, xmlResAry[3], 2);

        // 処理モードを取得する
        modeBasePriceAdd = parseInt(funXmlRead(xmlResAry[3], "mode", 0));

        frm.txtHouzai.value = funXmlRead(xmlResAry[3], "name_hansu", 0);

        if(modeBasePriceAdd == 3) {
        	// コピー時は、版数をカウントアップ
        	frm.txtHansu.value = (parseInt(funXmlRead(xmlResAry[3], "no_hansu", 0)) + 1);
        } else {
        frm.txtHansu.value = funXmlRead(xmlResAry[3], "no_hansu", 0);
        }

        frm.txtYuko.value  = funXmlRead(xmlResAry[3], "dt_yuko", 0);
        frm.txtBiko.value  = funXmlRead(xmlResAry[3], "biko", 0);

//        frm.txtBiko_kojo.value  = funXmlRead(xmlResAry[3], "biko_kojo", 0);

        // 確認チェックボックスと確認者を設定する
        if((funXmlRead(xmlResAry[3], "id_kakunin", 0) != "") && modeBasePriceAdd != 3) {
            frm.chkKakunin.checked = true;
            document.getElementById("lblKakunin").innerHTML = funXmlRead(xmlResAry[3], "nm_kakunin", 0);
        } else {
            frm.chkKakunin.checked = false;
            document.getElementById("lblKakunin").innerHTML = "－";
        }

        // 承認チェックボックスと承認者を設定する
        if((funXmlRead(xmlResAry[3], "id_shonin", 0) != "") && modeBasePriceAdd != 3) {
            frm.chkShonin.checked = true;
            document.getElementById("lblShonin").innerHTML = funXmlRead(xmlResAry[3], "nm_shonin", 0);
        } else {
            frm.chkShonin.checked = false;
            document.getElementById("lblShonin").innerHTML = "－";
        }

        // 未使用チェックを設定する
        if(parseInt(funXmlRead(xmlResAry[3], "flg_mishiyo", 0)) == 1) {
            frm.chkMishiyo.checked = true;
        } else {
            frm.chkMishiyo.checked = false;
        }
//
//        // 処理モードを取得する
//        modeBasePriceAdd = parseInt(funXmlRead(xmlResAry[3], "mode", 0));

        // 新規登録
        // メニューよりの新規登録・一覧よりのコピー
        if(modeBasePriceAdd == 1 || modeBasePriceAdd == 3) {
            frm.chkShonin.disabled = true;
            // 削除ボタン非表示
            frm.btnDataDelete.style.display = "none";
            frm.chkMishiyo.disabled = true;		// 未使用チェック

        // 更新
        } else if(modeBasePriceAdd == 2) {
            frm.ddlMakerName.disabled = true;
            frm.ddlHouzai.disabled = true;
            frm.txtHansu.readOnly = true;
            frm.chkShonin.disabled = true;
            frm.txtHouzai.readOnly = true;			// 	包材名
            frm.chkMishiyo.disabled = false;
        } else if(modeBasePriceAdd == 4) {
            frm.btnDataEdit.value = "承認";
            frm.chkKakunin.disabled = true;
            frm.ddlMakerName.disabled = true;
            frm.ddlHouzai.disabled = true;
            frm.btnCalc.disabled = true;
            frm.btnUpRituCalc.disabled = true;
            frm.txtShiyoRyo.readOnly = true;
            frm.txtUpRitu.readOnly = true
            frm.txtHansu.readOnly = true;
            frm.txtYuko.readOnly  = true;
            frm.txtBiko.readOnly  = true;
            frm.txtHouzai.readOnly = true;			// 	包材名
            frm.chkMishiyo.disabled = true;		// 未使用チェック
        }

        // 戻り先を取得する
        backBasePriceAdd = parseInt(funXmlRead(xmlResAry[3], "back", 0));

        //表示
        tblHeader.style.display = "block";
        tblList.style.display = "block";

    } else {
        //非表示
        tblHeader.style.display = "none";
        tblList.style.display = "none";

        //処理中ﾒｯｾｰｼﾞ非表示
        funClearRunMessage();
    }

    //ﾃﾞﾌｫﾙﾄのｺﾝﾎﾞﾎﾞｯｸｽ設定値を退避
    xmlFGEN3000.load(xmlResAry[2]);

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    return true;

}

//========================================================================================
// 登録（承認）ボタン押下処理
// 作成者：BRC Koizumi
// 作成日：2016/09/06
// 引数  ：①mode ：処理区分
//           1：登録、4：承認、5：包材登録
// 概要  ：表示データの登録、更新、削除処理を行う
//========================================================================================
function funDataEdit(mode) {
    if (modeBasePriceAdd == 4) {
        // 承認ボタン押下処理
        funShonin(mode);
    } else if (modeBasePriceAdd == 1 || modeBasePriceAdd == 3) {
    	var frm = document.frm00;    //ﾌｫｰﾑへの参照
    	// 新規 or コピーボタン押下処理
    	// 版の包材名未選択 かつ 包材名に入力有の場合包材名の登録処理を行う
    	var hanHouzai = frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value;
    	var baseHouzai = frm.txtHouzai.value;

    	if (hanHouzai == "" && baseHouzai != "") {
    		// 包材名の登録チェックを行う
    		if(!fucHouzaiToroku()){
    			return false;
    		}
    	}
    	funEdit(modeBasePriceAdd);
    } else {
        // 登録ボタン押下処理
        funEdit(mode);
    }

    return true;

}

//========================================================================================
// 包材名の登録チェック
// 作成者：BRC Koizumi
// 作成日：2016/09/06
// 概要  ：包材名の登録チェックを行う
//========================================================================================
function fucHouzaiToroku() {
	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	var houzai =  funGetXmldata(xmlFGEN3010O, "nm_2nd_literal", frm.txtHouzai.value, "cd_2nd_literal");

	if (houzai != "") {
		funErrorMsgBox(E000053);
		return false;
	}

  return true;

}

//========================================================================================
// 登録ボタン押下処理
// 作成者：BRC Koizumi
// 作成日：2016/09/06
// 引数  ：①mode ：処理区分
//           1：登録、2：更新、3：コピー
// 概要  ：表示データの登録、更新処理を行う
//========================================================================================
function funEdit(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN3530";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3530");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3530I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3530O);
    var dspMsg;

	    //確認ﾒｯｾｰｼﾞの表示
	    if (funConfMsgBox(I000002) != ConBtnYes) {
	        return false;
	    }

    //XMLの初期化
    setTimeout("xmlFGEN3530I.src = '../../model/FGEN3530I.xml';", ConTimer);


    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //登録、更新、削除処理を実行する
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3530, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    //完了ﾒｯｾｰｼﾞの表示
    dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);

    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {

        //正常
        funInfoMsgBox(dspMsg);

        // ベース単価一覧を更新する
        // 一覧画面が立ち上がっていない場合にエラーとなる為、try~catchで回避
        try{
        	window.opener.document.frm00.btnSearch.click();
        }catch (e) {

        }

        switch(modeBasePriceAdd){
        case 1: // 新規なら更新に
            modeBasePriceAdd = 2;
            backBasePriceAdd = 1; // メニュー画面に戻る
            break;
        case 3: // コピーなら更新
            modeBasePriceAdd = 2;
            backBasePriceAdd = 2; // ベース単価一覧画面に戻る
            break;
        }

        // ベース単価登録・承認画面起動情報通知
        funBasePriceAddTuti(1);

        //画面情報を取得・設定
        if (funGetInfo(1) == false) {
            return false;
        }
    }

    return true;

}

//========================================================================================
// 承認ボタン押下処理
// 作成者：BRC Koizumi
// 作成日：2016/09/06
// 引数  ：①mode ：処理区分
//           1：登録、2：承認
// 概要  ：表示データの登録、更新、削除処理を行う
//========================================================================================
function funShonin(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN3540";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3540");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3540I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3540O);

    // 承認者チェックボックス
    if (!frm.chkShonin.checked) {
    	funErrorMsgBox(E000060);
    	return false;
    }

    //確認ﾒｯｾｰｼﾞの表示
    if (funConfMsgBox(I000019) != ConBtnYes) {
        return false;
    }

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    // 承認処理を実行する
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3540, xmlReqAry, xmlResAry, 1) == false) {
        frm.chkShonin.checked = false;
        funClearRunMessage();
        return false;
    }

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    //完了ﾒｯｾｰｼﾞの表示
    dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);

    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {

        //正常
        funInfoMsgBox(dspMsg);

        // ベース単価一覧を更新する
        // 一覧画面が立ち上がっていない場合にエラーとなる為、try~catchで回避
        try{
        	window.opener.document.frm00.btnSearch.click();
        }catch (e) {

        }

        switch(modeBasePriceAdd){
        case 1: // 新規なら更新に
            modeBasePriceAdd = 2;
            backBasePriceAdd = 1; // メニュー画面に戻る
            break;
        case 3: // コピーなら更新
            modeBasePriceAdd = 2;
            backBasePriceAdd = 2; // ベース単価一覧画面に戻る
            break;
        }

        // ベース単価登録・承認画面起動情報通知
        funBasePriceAddTuti(1);

        //画面情報を取得・設定
        if (funGetInfo(1) == false) {
            return false;
        }
    }

    return true;

}

//========================================================================================
// 削除ボタン押下処理
// 作成者：BRC Koizumi
// 作成日：2016/09/06
// 引数  ：なし
// 概要  ：表示データの削除処理を行う
//========================================================================================
function funDataDelete() {

     var frm = document.frm00;    //ﾌｫｰﾑへの参照
     var XmlId = "RGEN3570";
     var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3570");
     var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3570I);
     var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3570O);

     //削除確認
     if (funConfMsgBox(I000020) != ConBtnYes) {
         return false;
     }

     //処理中ﾒｯｾｰｼﾞ表示
     funShowRunMessage();

     //引数をXMLﾌｧｲﾙに設定
     if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
         funClearRunMessage();
         return false;
     }

     //削除処理を実行する
     if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3570, xmlReqAry, xmlResAry, 1) == false) {
         return false;
     }

     //処理中ﾒｯｾｰｼﾞ非表示
     funClearRunMessage();

     if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {

         //完了ﾒｯｾｰｼﾞの表示
         funInfoMsgBox("ベース単価を" + I000007);

         // ベース単価一覧を更新する
         // 一覧画面が立ち上がっていない場合にエラーとなる為、try~catchで回避
         try{
             window.opener.document.frm00.btnSearch.click();
         }catch (e) {

         }

         // 要修正 → CostTbl BasePrice
         // ベース単価一覧画面に戻るmodeBasePriceAdd=2(登録) のみ
         modeBasePriceAdd = 2;
         backBasePriceAdd = 2; // ベース単価一覧画面に戻る

         // ベース単価一覧画面に戻る
         funNext();

     }

     return true;
}

//========================================================================================
// XMLファイルに書き込み
// 作成者：BRC Koizumi
// 作成日：2016/09/06
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

        // 画面初期表示
        if (XmlId.toString() == "RGEN3520") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3000
                    break;
                case 2:    //FGEN3520
                    break;
            }

        // 包材ドロップダウン
        } else if (XmlId.toString() == "RGEN3010"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //RGEN3010
                    funXmlWrite(reqAry[i], "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    break;
            }

        // 登録ボタン押下
        } else if (XmlId.toString() == "RGEN3530") {

            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3530

                	// ベース単価リストマスタ、ベース単価マスタ
                    funXmlWrite_Tbl(reqAry[i], "base_price_list", "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "base_price_list", "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "base_price_list", "no_hansu", frm.txtHansu.value, 0);
                    funXmlWrite_Tbl(reqAry[i], "base_price_list", "dt_yuko", frm.txtYuko.value, 0);
                    funXmlWrite_Tbl(reqAry[i], "base_price_list", "biko", frm.txtBiko.value, 0);

//                    funXmlWrite_Tbl(reqAry[i], "base_price_list", "biko_kojo", frm.txtBiko_kojo.value, 0);

                	// 版の包材名未選択 かつ 包材名に入力有の場合包材名の登録処理を行う
                	var hanHouzai = frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].innerText;
                	var baseHouzai = frm.txtHouzai.value;

                	if (hanHouzai == "" && baseHouzai != "") {
                		funXmlWrite_Tbl(reqAry[i], "base_price_list", "name_hansu", baseHouzai, 0);
                		funXmlWrite_Tbl(reqAry[i], "base_price_list", "name_houzai", baseHouzai, 0);
                	} else if (hanHouzai != "" && baseHouzai != ""){
                		funXmlWrite_Tbl(reqAry[i], "base_price_list", "name_hansu", hanHouzai, 0);
                		funXmlWrite_Tbl(reqAry[i], "base_price_list", "name_houzai", baseHouzai, 0);
                	}
                	else {
                		funXmlWrite_Tbl(reqAry[i], "base_price_list", "name_hansu", hanHouzai, 0);
                		funXmlWrite_Tbl(reqAry[i], "base_price_list", "name_houzai", hanHouzai, 0);
                	}
                	// 承認者チェックボックス
                    if (frm.chkShonin.checked) {
                        funXmlWrite_Tbl(reqAry[i], "base_price_list", "chk_shonin", "1", 0);
                    } else {
                        funXmlWrite_Tbl(reqAry[i], "base_price_list", "chk_shonin", "", 0);
                    }
                    // 登録者チェックボックス
                    if (frm.chkKakunin.checked) {
                        funXmlWrite_Tbl(reqAry[i], "base_price_list", "chk_kakunin", "1", 0);
                    } else {
                        funXmlWrite_Tbl(reqAry[i], "base_price_list", "chk_kakunin", "", 0);
                    }
                    // 未使用チェックボックス
                    if (frm.chkMishiyo.checked) {
                        funXmlWrite_Tbl(reqAry[i], "base_price_list", "chk_mishiyo", "1", 0);
                    } else {
                        funXmlWrite_Tbl(reqAry[i], "base_price_list", "chk_mishiyo", "0", 0);
                    }

                    for (j = 0; j < 31; j++) {

                        if (j <= 0) {
                            funXmlWrite_Tbl(reqAry[i], "base_price", "nm_title", "", j);
                            funXmlWrite_Tbl(reqAry[i], "base_price", "no_row", "0", j);
                            for (k = 0; k < 30; k++) {
                                funXmlWrite_Tbl(reqAry[i], "base_price", "no_value" + funZeroPudding(k + 1), document.getElementById("txtHeader_" + (k + 1)).value.replace(/,/g,""), j);
                            }
                        } else {

                            funXmlWrite_Tbl(reqAry[i], "base_price", "nm_title", document.getElementById("nm_title_" + j).value, j);
                            funXmlWrite_Tbl(reqAry[i], "base_price", "no_row", j, j);
                            for (k = 0; k < 30; k++) {
                                funXmlWrite_Tbl(reqAry[i], "base_price", "no_value" + funZeroPudding(k + 1), document.getElementById("no_value_" + j + "_" + (k + 1)).value.replace(/,/g,""), j);
                            }
                        }
                        funAddRecNode_Tbl(reqAry[i], "FGEN3530", "base_price");
                    }

	            	// 包材名登録用
                    funXmlWrite_Tbl(reqAry[i], "ma_literal", "cd_category", "maker_name", 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_literal", "cd_literal", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_literal", "nm_literal", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].innerText, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_literal", "nm_2nd_literal", frm.txtHouzai.value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_literal", "kbn_shori", "1", 0);

                    break;
            }

        // 承認ボタン押下
        } else if (XmlId.toString() == "RGEN3540") {

            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3540
                    funXmlWrite_Tbl(reqAry[i], "base_price_list", "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "base_price_list", "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "base_price_list", "no_hansu", frm.txtHansu.value, 0);

                    if (frm.chkShonin.checked) {
                        funXmlWrite_Tbl(reqAry[i], "base_price_list", "chk_shonin", "1", 0);
                    } else {
                        funXmlWrite_Tbl(reqAry[i], "base_price_list", "chk_shonin", "", 0);
                    }

                    break;
            }

        //包材名プルダウン、版数変更時
        } else if (XmlId.toString() == "RGEN3560"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3560
                    funXmlWrite(reqAry[i], "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "no_hansu", frm.txtHansu.value);
                    funXmlWrite(reqAry[i], "name_hanhouzai", frm.txtHouzai.value);
                    break;
            }

        //削除ボタン押下
            // 承認済みデータもガッツリ削除してＯＫ？
        } else if (XmlId.toString() == "RGEN3570"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3570
                    funXmlWrite(reqAry[i], "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "no_hansu", frm.txtHansu.value);
                    break;
            }

        } else if (XmlId.toString() == "RGEN2160"){
        	// メーカーコード
            var cd_maker  = frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value;
            // 版の包材コード
            var cd_houzai =  frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value;
            // 版数
            var no_hansu  = frm.txtHansu.value;
            // 未使用
            var mishiyo;
            if (frm.chkMishiyo.checked) {
            	mishiyo = 1;
            } else {
            	mishiyo = 2;
            }

            // 修正
            var put_code = modeBasePriceAdd + "-" + cd_maker + "-" + cd_houzai + "-" + no_hansu + "-" + backBasePriceAdd + "-" + mishiyo;

            // 検索コード置換【モード:::メーカーコード:::版の包材コード:::版数:::バックモード】
            var put_code = put_code.replace(/-/g,":::");

            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", put_code, 0);
                    break;
            }
        }
    }

    return true;
}

//========================================================================================
// コンボボックス作成処理
// 作成者：BRC Koizumi
// 作成日：2016/09/06
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②xmlData  ：設定XML
//       ：③mode     ：モード
// 概要  ：コンボボックスを作成する
//========================================================================================
function funCreateComboBox(obj, xmlData, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var reccnt;
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
        //処理を中断
        return true;
    }

    //属性名の取得
    switch (mode) {
        case 1:    //ﾘﾃﾗﾙﾏｽﾀ
            atbName = "nm_literal";
            atbCd = "cd_literal";
            break;
        case 2:    //ﾘﾃﾗﾙﾏｽﾀ（第2カテゴリ）
            atbName = "nm_2nd_literal";
            atbCd = "cd_2nd_literal";
            break;
    }

    for (i = 0; i < reccnt; i++) {
        if (funXmlRead(xmlData, atbCd, i) != "" && funXmlRead(xmlData, atbName, i) != "") {
        	// 包材名 かつ 新規、コピーの場合は未使用を除く
        	if(atbCd == "cd_2nd_literal"){
        		if(modeBasePriceAdd == 1 || modeBasePriceAdd == 3) {
        			if(parseInt(funXmlRead(xmlData, "flg_mishiyo", i)) != 1){
	        			objNewOption = document.createElement("option");
	                    obj.options.add(objNewOption);
	                    objNewOption.innerText = funXmlRead(xmlData, atbName, i);
	                    objNewOption.value = funXmlRead(xmlData, atbCd, i);
        			}
        		} else {
            objNewOption = document.createElement("option");
            obj.options.add(objNewOption);
            objNewOption.innerText = funXmlRead(xmlData, atbName, i);
            objNewOption.value = funXmlRead(xmlData, atbCd, i);
        }
        	} else {
        		objNewOption = document.createElement("option");
                obj.options.add(objNewOption);
                objNewOption.innerText = funXmlRead(xmlData, atbName, i);
                objNewOption.value = funXmlRead(xmlData, atbCd, i);
        	}
        }
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽのﾃﾞﾌｫﾙﾄ表示
    obj.selectedIndex = 0;

    return true;
}

//========================================================================================
// デフォルト値選択処理
// 作成者：BRC Koizumi
// 作成日：2016/09/06
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②mode     ：モード
// 概要  ：コンボボックスのデフォルト値を選択する
//========================================================================================
function funDefaultIndex(obj, xmlData, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var selIndex;
    var i;

    //
    for (i = 0; i < obj.length; i++) {
        switch (mode) {
            case 1:    // メーカー名
                if (obj.options[i].value == funXmlRead(xmlData, "cd_maker", 0)) {
                    selIndex = i;
                }
                break;
            case 2:    // 包材
                if (obj.options[i].value == funXmlRead(xmlData, "cd_houzai", 0)) {
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
// 次画面遷移処理
// 作成者：BRC Koizumi
// 作成日：2016/09/06
// 概要  ：次画面に遷移する
//========================================================================================
function funNext() {

    var wUrl;

    //遷移先判定

    if (backBasePriceAdd == 1) {
        //画面を閉じる
        close(self);
        return true;

//        // 原資材調達部メニュー
//        wUrl = "../SQ170GenchoMenu/SQ170GenchoMenu.jsp";
//
//        //処理中ﾒｯｾｰｼﾞ表示
//        funShowRunMessage();
//
//        //遷移
//        //処理中ウィンドウ表示の為、setTimeoutで処理予約
//        setTimeout(function(){ funUrlConnect(wUrl, ConConectPost, document.frm00) }, 0);

    } else {

        window.close();


    }

    return true;
}

//========================================================================================
// ベース単価登録・承認画面起動情報通知
// 作成者：BRC Koizumi
// 作成日：2016/09/06
// 引数  ：なし
// 概要  ：選択したベース単価をセッションへ保存する
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

//========================================================================================
// ロット数数値チェック
// 作成者：BRC Koizumi
// 作成日：2016/09/06
// 引数  ：なし
// 概要  ：ロット数に値が入力された場合、入力値によって
//        確認者・承認者チェックボックスをクリアする
//========================================================================================
function funNumOnly() {

	// Ctrl + A or C or V or X かつ 更新の場合
	// 確認者・承認者チェックボックスをクリア
    if (event.ctrlKey) {
        switch(event.keyCode) {
            case 65:  //Keyboard.A
            case 67:  //Keyboard.C
            case 86:  //Keyboard.V
            case 88:  //Keyboard.X
            funCheckChange();
            return true;
        }
    }

	// ←、→ テンキー数字 かつ 更新の場合
	// 確認者・承認者チェックボックスをクリア
    switch(event.keyCode) {
        case 37:   // <--
        case 39:   // -->
        case 96:   // 0
        case 97:   // 1
        case 98:   // 2
        case 99:   // 3
        case 100:  // 4
        case 101:  // 5
        case 102:  // 6
        case 103:  // 7
        case 104:  // 8
        case 105:  // 9
        funCheckChange();
        return true;
    }

    m = String.fromCharCode(event.keyCode);

    // 数値以外の場合、処理しない
    if("0123456789\b\t".indexOf(m, 0) < 0) return false;

    // 更新の場合、確認者・承認者チェックボックスをクリア
    funCheckChange();

    return true;
}


//========================================================================================
// 四捨五入
// 作成者：BRC Koizumi
// 作成日：2016/09/06
// 引数  ：なし
// 概要  ：アップ率計算 少数点2位まで表示（3位以下切り捨て）
//========================================================================================
function funRound(val, precision)
{
     //小数点を移動させる為の数を10のべき乗で求める
     //例) 小数点以下2桁の場合は 100 をかける必要がある
     digit = Math.pow(10, precision);

     // 四捨五入したい数字に digit を掛けて小数点を移動
     val = val * digit;

     // roundを使って四捨五入
     val = Math.round(val);

     // 移動させた小数点を digit で割ることでもとに戻す
     val = val / digit;

     return val;
}

//========================================================================================
// 確認者・承認者チェックボックスをクリア
// 作成者：BRC Koizumi
// 作成日：2016/09/06
// 引数  ：なし
// 概要  ：各項目更新時、チェックボックスをクリアする
//========================================================================================
function funCheckChange() {
    // 更新の場合、確認者・承認者チェックボックスをクリア
    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    if(modeBasePriceAdd == 2){
    	// 承認チェックボックス
        if (frm.chkShonin.checked) {
            frm.chkShonin.checked = false;
            document.getElementById("lblShonin").innerHTML = "－";
        }
        // 確認チェックボックス
        if (frm.chkKakunin.checked) {
            frm.chkKakunin.checked = false;
            document.getElementById("lblKakunin").innerHTML = "－";
        }
    }

    return false;
}

//========================================================================================
// 表示情報取得
// 作成者：BRC Koizumi
// 作成日：2016/09/06
// 引数  ：なし
// 概要  ：包材、版数変更時、表示情報を再取得する
//========================================================================================
function funExistData() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    var cd_maker  = frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value;
    var cd_houzai =  frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value;
    var no_hansu  = frm.txtHansu.value;
    if(modeBasePriceAdd == 1 && cd_maker != "" && cd_houzai != "" && no_hansu != ""){

        var XmlId = "RGEN3560";
        var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3560");
        var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3560I);
        var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3560O);


        //引数をXMLﾌｧｲﾙに設定
        if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
            funClearRunMessage();
            return false;
        }

        //ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
        if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3560, xmlReqAry, xmlResAry, mode) == false) {
            return false;
        }
    }

    return;
}

//========================================================================================
// 入力チェック
// 作成者：BRC Koizumi
// 作成日：2016/09/06
// 引数  ：なし
// 概要  ：確認チェック　チェック時に入力値をチェックする
//========================================================================================
function funCheckData() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var col;
    var row;
    var lotValue;
    var titleValue;
    var costValue;
    var lotCount;
    var costCount;

    var rowCount = 0;
    var colCount = 0;

    lotCount = 0;
    headerCount = 0;

    if(frm.chkKakunin.checked == false) {
        return;
    }

    for (col = 0; col < 30; col++) {

        lotValue = document.getElementById("txtHeader_" + (col + 1)).value;

        if (lotValue != "") {
            colCount++;
        }
    }

    // ロット数に値があるかチェックする
    if (colCount <= 0) {
        frm.chkKakunin.checked = false;
        funErrorMsgBox("ロット数" + E000050);
        return;
    }

    for (col = 0; col < 30; col++) {

        lotValue = document.getElementById("txtHeader_" + (col + 1)).value;

        if (lotValue != "") {
            lotCount++;
        } else {
            break;
        }
    }

    // 連続性をチェックする
    if (colCount != lotCount) {
        frm.chkKakunin.checked = false;
        funErrorMsgBox("ロット数" + E000050);
        return;
    }

    // 色数を追加
    colCount++;

    costCount = 0;

    for (row = 0; row < 30; row++) {

        costCount = 0;

        // 色数
        titleValue = document.getElementById("nm_title_" + (row + 1)).value;

        if (titleValue != "") {
            costCount++;
        }

        for (col = 0; col < 30; col++) {

            costValue = document.getElementById("no_value_" + (row + 1) + "_"  + (col + 1)).value;

            if (costValue != "") {
                costCount++;
            }
        }

        if (costCount > 0) {
            rowCount++;
        }
    }

    if (colCount <= 0 || rowCount <= 0) {
        frm.chkKakunin.checked = false;
        funErrorMsgBox("色数またはコスト" + E000050);
        return;
    }

    for (row = 0; row < rowCount; row++) {

        costCount = 0;

        // 色数
        titleValue = document.getElementById("nm_title_" + (row + 1)).value;

        if (titleValue != "") {
            costCount++;
        }

        for (col = 0; col < colCount; col++) {

        	try{
        		costValue = document.getElementById("no_value_" + (row + 1) + "_"  + (col + 1)).value;

                if (costValue != "") {
                    costCount++;
                }
        	}catch(e){

        	}
        }

        if (costCount != colCount) {
            frm.chkKakunin.checked = false;
            funErrorMsgBox("色数またはコスト" + E000050);
            return;
        }
    }
}

//========================================================================================
// 数値Format変換
// 作成者：BRC Koizumi
// 作成日：2016/09/06
// 引数  ：val:変換値
//         keta:小数桁（切り捨て桁）
// 戻り値　変換値（※数値でない場合は空文字返却）
// 概要  ：
//========================================================================================
function funNumFormatChange(val , keta) {

	// カンマ削除
	val = val.replace(/,/g,"");

	// 数値チェック
    if ( val == parseFloat(val)){

    	if(keta == 0){
    		return Math.floor(val);
    	}

    	// 数値の場合に、指定小数切り捨て
    	return funShosuKirisute(val,keta);
    } else {
    	// 数値でない
    	return "";
    }
}

//========================================================================================
// カーソル移動処理：↓↑キー押下
// 作成者：BRC Koizumi
// 作成日：2016/09/06
// 概要  ：下行（上行）にフォーカスを移す
//========================================================================================
function funCellIdou(code) {

	// ↓↑キーの時のみカーソル移動処理
	if ((code != 38) && (code != 40)) {
		return;
	}

	// nextエレメントID
	var nextId = "";
	// アクティブエレメントID
	var currentId = document.activeElement.id;

	// 行と列名を分解
	var tmpId = currentId.split("_");

	var row = "";		// 行番号
	var iti = 0;		// 行番号位置

	if (tmpId[0] == "no") {
		// 単価セルの場合：行番号
		iti = tmpId.length - 2;
		row = tmpId[iti];
	} else {
		// 名前セルの場合：行番号
		iti = tmpId.length - 1;
		row = tmpId[iti];
	}


	for(i=0; i<tmpId.length; i++) {
		// 行番号＋１
		if (i == iti) {
			// ↓キー
			if (code == 40) {
				if (row < 30) row++;

			// ↑キー
			} else {
				if (row > 1) row--;
			}
			nextId += row;
			if (i == (tmpId.length - 2)) {
				// 単価セルの場合、col番号を追加
				nextId +=  "_" + tmpId[tmpId.length - 1];
			}
			break;

		} else {
			nextId += tmpId[i] + "_";
		}
	}
	// 次行にフォーカスを移す
	document.getElementById(nextId).focus();
	//クリックして選択行の背景色を変更
	document.getElementById(nextId).click();
}

//========================================================================================
// メーカー名コンボボックス連動処理
// 作成者：BRC Koizumi
// 作成日：2016/09/06
// 引数  ：なし
// 概要  ：メーカーに紐付く包材コンボボックスを生成する
//========================================================================================
function funChangeMaker() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	var XmlId = "RGEN3010";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3010");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3010I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3010O);

    //包材ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
    funClearSelect(frm.ddlHouzai, 2);

	//引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}


	//情報を取得
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3010, xmlReqAry, xmlResAry, 1) == false) {
		return false;
	}

	//包材ｺﾝﾎﾞﾎﾞｯｸｽの作成
	funCreateComboBox(frm.ddlHouzai, xmlResAry[2], 2);
	xmlFGEN3010.load(xmlResAry[2]);

	return true;

}


//========================================================================================
//XMLデータより検索行のコード←→名前 変換
//作成者：BRC Koizumi
//作成日：2016/10/04
//引数  ：①xmlData  ：XMLデータ
//     ：②komoku   ：検索項目名
//     ：③text     ：検索値
//     ：④ret      ：取得項目名
//return：コード値又は名前
//概要  ：一致するコード又は名前を取得
//========================================================================================
function funGetXmldata(xmlData, komoku, text, ret) {

	//件数取得
	var reccnt = funGetLength(xmlData);
	// 戻り値
	var retStr = "";
	for (var i = 0; i < reccnt; i++) {
		// 検索項目値が等しい場合、Index設定
		if (funXmlRead(xmlData, komoku, i) == text) {
			//指定項目名の値を返す
			retStr = funXmlRead(xmlData, ret, i);
			break;
		}
	}

return retStr;
}
