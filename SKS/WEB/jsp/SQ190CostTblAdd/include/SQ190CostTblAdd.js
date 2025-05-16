var modeCostTblAdd = 1;
var backCostTblAdd = 1;
var costHansu = 0;

//========================================================================================
// 初期表示処理
// 作成者：M.Sakamoto
// 作成日：2014/02/25
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

    //画面設定
    funInitScreen(ConCostTblAddId);

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //処理中ウィンドウ表示の為、setTimeoutで処理予約
    setTimeout(function(){ funGetInfo(1) }, 0);

    return true;

}

//========================================================================================
// ヘッダ作成処理
// 作成者：M.Sakamoto
// 作成日：2014/02/25
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

                // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start
                // カンマ編集
                document.getElementById(id).innerText = funAddComma(funNumFormatChange(funXmlRead(xmlData, key, i),0));
                // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end

                if (mode == 4) {
                    document.getElementById(id).readOnly = true;
                }
            }

        } else {

            key = "nm_title";
            id  = "nm_title_" + i;

            document.getElementById(id).value = funXmlRead(xmlData, key, i);

            if (mode == 4) {
                document.getElementById(id).readOnly = true;
            }

            for (j = 1; j < 31; j++) {
                key = "no_value" + funZeroPudding(j);
                id  = "no_value_" + i + "_" + j;

                // カンマ編集
                // 少数点以下2位まで表示（3位以下切り捨て）
                document.getElementById(id).value = funAddComma(funNumFormatChange(funXmlRead(xmlData, key, i), 2));

                if (mode == 4) {
                    document.getElementById(id).readOnly = true;
                }
            }
        }
    }

    // 有効開始日、備考制御
    if (mode == 4) {
        document.getElementById("txtYuko").readOnly = true;
        document.getElementById("txtBiko").readOnly = true;
    }
}

//========================================================================================
// 個装使用量計算処理
// 作成者：M.Sakamoto
// 作成日：2014/02/25
// 引数  ：なし
// 概要  ：個装使用量を計算する
//========================================================================================
function funCalc() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var key;
    var headerId;
    var calcRsltId;
    var val;

    // 【QP@30297】No.16 2014/08/20  E.kitazawa 課題対応 --------------------- mod start
    //  個装使用量 < 1 を許可する
    if(frm.txtShiyoRyo.value == "" || parseFloat(frm.txtShiyoRyo.value) == 0) {
        return;
    }
    // 【QP@30297】Mo.16 2014/08/20  E.kitazawa 課題対応 --------------------- mod end

    for (i = 0; i < 30; i++) {
        headerId  = "txtHeader_" + (i + 1);
        // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start
        val = document.getElementById(headerId).value.replace(/,/g,"");
        if(val != ""){
            calcRsltId  = "calcRslt" + (i + 1);
            // 【QP@30297】No.20 2014/08/20  E.kitazawa 課題対応 --------------------- mod start
            // 小数点以下切り捨て
            document.getElementById(calcRsltId).value = funAddComma(Math.floor(val / frm.txtShiyoRyo.value.replace(/,/g,"")));
            // 【QP@30297】No.20 2014/08/20  E.kitazawa 課題対応 --------------------- mod end
        }
        // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end
    }
}


//========================================================================================
// アップ率計算処理
// 作成者：M.Sakamoto
// 作成日：2014/02/25
// 引数  ：なし
// 概要  ：アップ率より単価を計算する
//========================================================================================
function funUpRituCalc() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
	if(frm.txtUpRitu.value == "" || parseInt(frm.txtUpRitu.value) == 0) {
        return;
    }

    var key;
    var valueId;
    var val;
    var total;
    var row;
    var col;
    var baseKey;
    var baseVal;

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

//========================================================================================
// 一覧クリア処理
// 作成者：M.Sakamoto
// 作成日：2014/02/25
// 引数  ：なし
// 概要  ：一覧の情報を初期化する
//========================================================================================
function funClearList() {

    //一覧のｸﾘｱ
    xmlFGEN3030O.src = "";
    tblList.style.display = "none";

}

//========================================================================================
// 画面情報取得処理
// 作成者：M.Sakamoto
// 作成日：2014/02/25
// 引数  ：①mode  ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：ユーザのセッション情報、コンボボックスの情報を取得する
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN3030";

    // 包材情報を取得しない
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3000","FGEN3030");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3000I,xmlFGEN3030I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3000O,xmlFGEN3030O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3030, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //ﾕｰｻﾞ情報表示
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //ｺﾝﾎﾞﾎﾞｯｸｽの作成
    funCreateComboBox(frm.ddlMakerName, xmlResAry[2], 1);

    //ﾃﾞｰﾀ件数のﾁｪｯｸ
    if (funXmlRead(xmlResAry[3], "flg_return", 0) == "true") {
        // 処理モードを取得する
        modeCostTblAdd = parseInt(funXmlRead(xmlResAry[3], "mode", 0));

    	// 一覧作成
    	funCreateCostTbl(xmlResAry[3]);

        funDefaultIndex(frm.ddlMakerName, xmlResAry[3], 1);
        // 包材コンボボックスの取得・設定
        funChangeMaker();
        funDefaultIndex(frm.ddlHouzai, xmlResAry[3], 2);

//【KPX@1602367】add start
        // 版数コンボボックスの取得・設定
        funChangeBaseHouzai();
        funDefaultIndex(frm.ddlBaseHansu, xmlResAry[3], 3);

        frm.costHouzai.value = funXmlRead(xmlResAry[3], "name_hansu", 0);	// コスト包材名
//【KPX@1602367】add end

        frm.txtHansu.value = funXmlRead(xmlResAry[3], "no_hansu", 0);
        frm.txtYuko.value  = funXmlRead(xmlResAry[3], "dt_yuko", 0);
        frm.txtBiko.value  = funXmlRead(xmlResAry[3], "biko", 0);

        frm.txtBiko_kojo.value  = funXmlRead(xmlResAry[3], "biko_kojo", 0);

        // 登録（確認）チェックボックスと確認者を設定する
        if((funXmlRead(xmlResAry[3], "id_kakunin", 0) != "") && modeCostTblAdd != 1) {
            frm.chkKakunin.checked = true;
            document.getElementById("lblKakunin").innerHTML = funXmlRead(xmlResAry[3], "nm_kakunin", 0);
        } else {
            frm.chkKakunin.checked = false;
            document.getElementById("lblKakunin").innerHTML = "－";
        }

        // 承認チェックボックスと承認者を設定する
        if(funXmlRead(xmlResAry[3], "id_shonin", 0) != "") {
            frm.chkShonin.checked = true;
            document.getElementById("lblShonin").innerHTML = funXmlRead(xmlResAry[3], "nm_shonin", 0);
        } else {
            frm.chkShonin.checked = false;
            document.getElementById("lblShonin").innerHTML = "－";
        }
        // 未使用
        if(parseInt(funXmlRead(xmlResAry[3], "flg_mishiyo", 0)) == 1) {
            frm.chkMishiyo.checked = true;
        } else {
            frm.chkMishiyo.checked = false;
        }

        // 新規登録
        //【KPX@1602367】mod
        // メニューよりの新規登録・一覧よりのコピー
        if(modeCostTblAdd == 1) {
//        if(modeCostTblAdd == 1 || modeCostTblAdd == 3) {
            frm.chkShonin.disabled = true;
            // 削除ボタン非表示
            frm.btnDataDelete.style.display = "none";
            frm.chkMishiyo.disabled = true;		// 未使用チェック
        // 更新
        } else if(modeCostTblAdd == 2) {
            frm.ddlMakerName.disabled = true;
            frm.ddlHouzai.disabled = true;
            frm.txtHansu.readOnly = true;
            frm.chkShonin.disabled = true;
            frm.ddlBaseHansu.disabled = true;	// 版プルダウン 【KPX@1602367】mod
            frm.chkMishiyo.disabled = false;
        } else if(modeCostTblAdd == 4) {
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
            frm.ddlBaseHansu.disabled = true;	// 版プルダウン 【KPX@1602367】mod
            frm.chkMishiyo.disabled = true;		// 未使用チェック
        }

        // 戻り先を取得する
        backCostTblAdd = parseInt(funXmlRead(xmlResAry[3], "back", 0));

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
// 登録ボタン押下処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①mode ：処理区分
//           1：登録、2：承認
// 概要  ：表示データの登録、更新、削除処理を行う
//========================================================================================
function funDataEdit(mode) {

    if (modeCostTblAdd == 4) {

        // 承認ボタン押下処理
        funShonin(mode);

    } else {

        // 登録ボタン押下処理
        funEdit(mode);
    }

    return true;

}

//========================================================================================
// 登録ボタン押下処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①mode ：処理区分
//           1：登録、2：承認
// 概要  ：表示データの登録、更新、削除処理を行う
//========================================================================================
function funEdit(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN3050";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3050");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3050I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3050O);
    var dspMsg;
    // 新規登録時、版数が"max+1"出なければエラー
	if(!isNaN(costHansu) && frm.txtHansu.value != ""){
    if(modeCostTblAdd == 1 &&
    		frm.txtHansu.value != costHansu) {
    	//コストテーブル版数の値が不正です。$1を入力してください。
    	funErrorMsgBox(E000058 + costHansu + E000059);
    	return false;
    }
	}

//
//    if (mode == 1) {
//        dspMsg = I000002;
//    } else if (mode == 2) {
//        dspMsg = I000003;
//    } else {
//        dspMsg = I000004;
//    }
//
//    //確認ﾒｯｾｰｼﾞの表示
//    if (funConfMsgBox(dspMsg) != ConBtnYes) {
//        return false;
//    }

    //確認ﾒｯｾｰｼﾞの表示
    if (funConfMsgBox(I000002) != ConBtnYes) {
        return false;
    }

    //XMLの初期化
    setTimeout("xmlFGEN3050I.src = '../../model/FGEN3050I.xml';", ConTimer);

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //登録、更新、削除処理を実行する
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3050, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    //完了ﾒｯｾｰｼﾞの表示
    dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);

    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {

        //正常
        funInfoMsgBox(dspMsg);

        // コストテーブル一覧を更新する
        // 【QP@30297】 TT.nishigawa 課題対応 ---------------------mod start
        // 一覧画面が立ち上がっていない場合にエラーとなる為、try~catchで回避
        try{

        	window.opener.document.frm00.btnSearch.click();
        }catch (e) {

        }

        // 【QP@30297】 TT.nishigawa 課題対応 ---------------------mod end
        switch(modeCostTblAdd){
        case 1: // 新規なら更新に
            modeCostTblAdd = 2;
            backCostTblAdd = 1; // メニュー画面に戻る
            break;
        case 3: // コピーなら更新
            modeCostTblAdd = 2;
            backCostTblAdd = 2; // コストテーブル一覧画面に戻る
            break;
        }
        funCostTblAddTuti(1);

        //画面情報を取得・設定
        if (funGetInfo(1) == false) {
            return false;
        }
    }

    return true;

}

//========================================================================================
// 承認ボタン押下処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①mode ：処理区分
//           1：登録、2：承認
// 概要  ：表示データの登録、更新、削除処理を行う
//========================================================================================
function funShonin(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN3060";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3060");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3060I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3060O);
    var dspMsg;

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
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3060, xmlReqAry, xmlResAry, 1) == false) {
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

        // コストテーブル一覧を更新する
        // 【QP@30297】 TT.nishigawa 課題対応 ---------------------mod start
        // 一覧画面が立ち上がっていない場合にエラーとなる為、try~catchで回避
        try{
        	window.opener.document.frm00.btnSearch.click();
        }catch (e) {

        }
        // 【QP@30297】 TT.nishigawa 課題対応 ---------------------mod end

        switch(modeCostTblAdd){
        case 1: // 新規なら更新に
            modeCostTblAdd = 2;
            backCostTblAdd = 1; // メニュー画面に戻る
            break;
        case 3: // コピーなら更新
            modeCostTblAdd = 2;
            backCostTblAdd = 2; // コストテーブル一覧画面に戻る
            break;
        }

        funCostTblAddTuti(1);

        //画面情報を取得・設定
        if (funGetInfo(1) == false) {
            return false;
        }
    }

    return true;

}

//【QP@30297】No.22 2014/08/22  E.kitazawa 課題対応 --------------------- add start
//========================================================================================
//削除ボタン押下処理
//作成者：E.Kitazawa
//作成日：2014/08/22
//引数  ：なし
//概要  ：表示データの削除処理を行う
//========================================================================================
function funDataDelete() {

     var frm = document.frm00;    //ﾌｫｰﾑへの参照
     var XmlId = "RGEN3110";
     var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3110");
     var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3110I);
     var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3110O);

     //削除確認
     if (funConfMsgBox(I000004) != ConBtnYes) {
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
     if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3110, xmlReqAry, xmlResAry, 1) == false) {
         return false;
     }

     //処理中ﾒｯｾｰｼﾞ非表示
     funClearRunMessage();

     if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {

         //完了ﾒｯｾｰｼﾞの表示
         funInfoMsgBox("コストテーブルを" + I000007);

         // コストテーブル一覧を更新する
         // 一覧画面が立ち上がっていない場合にエラーとなる為、try~catchで回避
         try{
             window.opener.document.frm00.btnSearch.click();
         }catch (e) {

         }

         // コストテーブル一覧画面に戻るmodeCostTblAdd=2(登録) のみ
         modeCostTblAdd = 2;
         backCostTblAdd = 2; // コストテーブル一覧画面に戻る

         // コストテーブル一覧画面に戻る
         funNext();

     }

     return true;
}
//【QP@30297】No.22 2014/08/22  E.kitazawa 課題対応 --------------------- add end


//========================================================================================
// XMLファイルに書き込み
// 作成者：M.Jinbo
// 作成日：2009/03/25
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
        if (XmlId.toString() == "RGEN3030") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3000
                    break;
                case 2:    //FGEN3030
                    break;
            }

        // 【QP@30297】No.19 E.kitazawa 課題対応 --------------------- add start
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
        // 【QP@30297】No.19 E.kitazawa 課題対応 --------------------- add end

            // 登録ボタン押下
        } else if (XmlId.toString() == "RGEN3050") {

            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3050

                    funXmlWrite_Tbl(reqAry[i], "cost_list", "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "cost_list", "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
//                    funXmlWrite_Tbl(reqAry[i], "cost_list", "no_hansu", frm.txtHansu.value, 0);
                    funXmlWrite_Tbl(reqAry[i], "cost_list", "dt_yuko", frm.txtYuko.value, 0);
                    funXmlWrite_Tbl(reqAry[i], "cost_list", "biko", frm.txtBiko.value, 0);
                    var no_basehansu  = frm.ddlBaseHansu.options[frm.ddlBaseHansu.selectedIndex].value;
                    no_basehansu = no_basehansu.split("版・")[0];

                    funXmlWrite_Tbl(reqAry[i], "cost_list", "no_hansu", frm.txtHansu.value, 0);
                    funXmlWrite_Tbl(reqAry[i], "cost_list", "no_basehansu", no_basehansu, 0);
                    funXmlWrite_Tbl(reqAry[i], "cost_list", "name_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].innerText, 0);
                    funXmlWrite_Tbl(reqAry[i], "cost_list", "name_hansu", frm.costHouzai.value, 0);

                    // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start
                    funXmlWrite_Tbl(reqAry[i], "cost_list", "biko_kojo", frm.txtBiko_kojo.value, 0);
                    // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end

                    if (frm.chkShonin.checked) {
                        funXmlWrite_Tbl(reqAry[i], "cost_list", "chk_shonin", "1", 0);
                    } else {
                        funXmlWrite_Tbl(reqAry[i], "cost_list", "chk_shonin", "", 0);
                    }

                    if (frm.chkKakunin.checked) {
                        funXmlWrite_Tbl(reqAry[i], "cost_list", "chk_kakunin", "1", 0);
                    } else {
                        funXmlWrite_Tbl(reqAry[i], "cost_list", "chk_kakunin", "", 0);
                    }
                    // 未使用チェックボックス 【KPX@1602367】add start
                    if (frm.chkMishiyo.checked) {
                        funXmlWrite_Tbl(reqAry[i], "cost_list", "chk_mishiyo", "1", 0);
                    } else {
                        funXmlWrite_Tbl(reqAry[i], "cost_list", "chk_mishiyo", "0", 0);
                    }
                    funXmlWrite_Tbl(reqAry[i], "cost_list", "cd_category", "maker_name", 0);
                    //【KPX@1602367】add end

                    for (j = 0; j < 31; j++) {

                        if (j <= 0) {
                            funXmlWrite_Tbl(reqAry[i], "cost", "nm_title", "", j);
                            funXmlWrite_Tbl(reqAry[i], "cost", "no_row", "0", j);
                            for (k = 0; k < 30; k++) {
                            	// 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start
                                funXmlWrite_Tbl(reqAry[i], "cost", "no_value" + funZeroPudding(k + 1), document.getElementById("txtHeader_" + (k + 1)).value.replace(/,/g,""), j);
                                // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end
                            }
                        } else {

                            funXmlWrite_Tbl(reqAry[i], "cost", "nm_title", document.getElementById("nm_title_" + j).value, j);
                            funXmlWrite_Tbl(reqAry[i], "cost", "no_row", j, j);
                            for (k = 0; k < 30; k++) {
                            	// 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start
                                funXmlWrite_Tbl(reqAry[i], "cost", "no_value" + funZeroPudding(k + 1), document.getElementById("no_value_" + j + "_" + (k + 1)).value.replace(/,/g,""), j);
                                // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end
                            }
                        }
                        funAddRecNode_Tbl(reqAry[i], "FGEN3050", "cost");
                    }
                    break;
            }

        // 承認ボタン押下
        } else if (XmlId.toString() == "RGEN3060") {

            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3060
                    funXmlWrite_Tbl(reqAry[i], "cost_list", "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "cost_list", "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "cost_list", "no_hansu", frm.txtHansu.value, 0);

                    if (frm.chkShonin.checked) {
                        funXmlWrite_Tbl(reqAry[i], "cost_list", "chk_shonin", "1", 0);
                    } else {
                        funXmlWrite_Tbl(reqAry[i], "cost_list", "chk_shonin", "", 0);
                    }

                    break;
            }

        //検索ボタン押下
        } else if (XmlId.toString() == "RGEN3080"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //RGEN3080
                    funXmlWrite(reqAry[i], "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "no_hansu", frm.txtHansu.value);
                    break;
            }

        //【QP@30297】No.22 2014/08/22  E.kitazawa 課題対応 --------------------- add start
        //削除ボタン押下
            // 承認済みデータもガッツリ削除してＯＫ？
        } else if (XmlId.toString() == "RGEN3110"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //RGEN3110
                    funXmlWrite(reqAry[i], "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "no_hansu", frm.txtHansu.value);
                    break;
            }
        //【QP@30297】No.22 2014/08/22  E.kitazawa 課題対応 --------------------- add end

        } else if (XmlId.toString() == "RGEN2160"){

            var cd_maker  = frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value;
            var cd_houzai =  frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value;
            var no_hansu  = frm.txtHansu.value;
//【KPX@1602367】add start
            // 未使用
            var mishiyo;
            if (frm.chkMishiyo.checked) {
            	mishiyo = 1;
            } else {
            	mishiyo = 2;
            }

//            var put_code = modeCostTblAdd + "-" + cd_maker + "-" + cd_houzai + "-" + no_hansu + "-" + backCostTblAdd;
            var put_code = modeCostTblAdd + "-" + cd_maker + "-" + cd_houzai + "-" + no_hansu + "-" + backCostTblAdd + "-" + mishiyo;
//【KPX@1602367】add end

            // 検索コード置換【メーカーコード:::包材コード:::版数】
            var put_code = put_code.replace(/-/g,":::");

            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", put_code, 0);
                    break;
            }
// 【KPX@1602367】 add start
        // ベース単価のデータを取得
	    } else if (XmlId.toString() == "RGEN3600"){
	    	var cd_maker  = frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value;
            var cd_houzai =  frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value;
            var no_hansu  = frm.ddlBaseHansu.options[frm.ddlBaseHansu.selectedIndex].value;
            no_hansu = no_hansu.split("版・")[0];

            var put_code = modeCostTblAdd + "-" + cd_maker + "-" + cd_houzai + "-" + no_hansu + "-" + backCostTblAdd;

            // 検索コード置換【メーカーコード:::包材コード:::版数】
            var put_code = put_code.replace(/-/g,":::");
	        switch (i) {
	            case 0:    //USERINFO
	                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
	                funXmlWrite(reqAry[i], "id_user", "", 0);
	                funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", put_code, 0);
	                break;
	            case 1:    //FGEN3600
	            	funXmlWrite(reqAry[i], "dt_yuko", frm.txtYuko.value, 0);
                    break;
	        }
	    // 版数リスト取得
        } else if (XmlId.toString() == "RGEN3610"){
        	var cd_maker  = frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value;
            var cd_houzai = frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value;

            var put_code = modeCostTblAdd + "-" + cd_maker + "-" + cd_houzai;

            // 検索コード置換【モード:::メーカーコード:::包材コード】
            var put_code = put_code.replace(/-/g,":::");
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", put_code, 0);
                    break;
                case 1:    //RGEN3610
                    funXmlWrite(reqAry[i], "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    break;
	            case 2:    //FGEN3660
                    break;
            }
// 【KPX@1602367】 add end
        }
    }

    return true;
}

//========================================================================================
// コンボボックス作成処理
// 作成者：M.Jinbo
// 作成日：2009/03/25
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
        case 1:    //ﾘﾃﾗﾙﾏｽﾀ メーカー名
            atbName = "nm_literal";
            atbCd = "cd_literal";
            break;
            //【QP@30297】No.19 E.kitazawa 課題対応 --------------------- add start
        case 2:    //ﾘﾃﾗﾙﾏｽﾀ（第2カテゴリ） 包材名
            atbName = "nm_2nd_literal";
            atbCd = "cd_2nd_literal";
            break;
          //【QP@30297】No.19 E.kitazawa 課題対応 --------------------- add end
//【KPX@1602367】add start
        case 3:    // ベース単価リストマスタ 版数
            atbName = "no_hansu";
            atbCd = "no_hansu";
            break;
//【KPX@1602367】add end
    }

    for (i = 0; i < reccnt; i++) {
        if (funXmlRead(xmlData, atbCd, i) != "" && funXmlRead(xmlData, atbName, i) != "") {
        	// 包材名 かつ 新規の場合は未使用を除く
        	if(atbCd == "cd_2nd_literal"){
        		if(modeCostTblAdd == 1) {
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
// 作成者：M.Jinbo
// 作成日：2009/03/25
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
//【KPX@1602367】add start
            case 3:    // 版数
            	obj.options[i].value = obj.options[i].value.split("版・")[0];
                if (obj.options[i].value == funXmlRead(xmlData, "no_basehansu", 0)) {
                    selIndex = i;
                }
                break;
//【KPX@1602367】add end
        }
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽのｲﾝﾃﾞｯｸｽ指定
    obj.selectedIndex = selIndex;

    return true;
}

//========================================================================================
// 次画面遷移処理
// 作成者：M.Sakamoto
// 作成日：2014/02/25
// 概要  ：次画面に遷移する
//========================================================================================
function funNext() {

    var wUrl;

    //遷移先判定

    if (backCostTblAdd == 1) {
        funEnd();
    } else {

        window.close();


    }

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
	    //画面を閉じる
	    close(self);
	    return true;

//        // 原資材調達部メニュー
//        wUrl = "../SQ170GenchoMenu/SQ170GenchoMenu.jsp";
//
//        //【QP@40404】課題-No.112  E.kitazawa --------- mod start
//        //処理中ﾒｯｾｰｼﾞ表示
//        funShowRunMessage();
//
//        //遷移
//        //処理中ウィンドウ表示の為、setTimeoutで処理予約
//        setTimeout(function(){ funUrlConnect(wUrl, ConConectPost, document.frm00) }, 0);
        //【QP@40404】課題-No.112  E.kitazawa --------- mod end
	}
	return true;
}

//========================================================================================
// コストテーブル登録・承認画面起動情報通知
// 作成者：M.Sakamoto
// 作成日：2014/02/25
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
// ロット数数値チェック
// 作成者：
// 作成日：
// 引数  ：なし
// 概要  ：ロット数に値が入力された場合、入力値によって
//     確認者・承認者チェックボックスをクリアする
//========================================================================================
function funNumOnly() {

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

    if("0123456789\b\t".indexOf(m, 0) < 0) return false;

    funCheckChange();

    return true;
}

//========================================================================================
// 四捨五入
// 作成者：
// 作成日：
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
// 作成者：
// 作成日：
// 引数  ：なし
// 概要  ：各項目更新時、チェックボックスをクリアする
//========================================================================================
function funCheckChange() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    if(modeCostTblAdd == 2){

        if (frm.chkShonin.checked) {
            frm.chkShonin.checked = false;
            // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start
            document.getElementById("lblShonin").innerHTML = "－";
            // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end
        }

        if (frm.chkKakunin.checked) {
            frm.chkKakunin.checked = false;
            // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start
            document.getElementById("lblKakunin").innerHTML = "－";
            // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end
        }
    }

    return false;
}

//========================================================================================
// 表示情報取得
// 作成者：
// 作成日：
// 引数  ：なし
// 概要  ：包材、版数変更時、表示情報を再取得する
//========================================================================================
function funExistData() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    var cd_maker  = frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value;
    var cd_houzai =  frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value;
    var no_hansu  = frm.txtHansu.value;
    if(modeCostTblAdd == 1 && cd_maker != "" && cd_houzai != "" && no_hansu != ""){

        var XmlId = "RGEN3080";
        var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3080");
        var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3080I);
        var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3080O);

        // 【QP@40404】 E.kitazawa 2014.12.26 ----------- del start
        // mode値が設定されていない ⇒ ﾕｰｻﾞ情報の処理区分未設定
        //  ⇒ ﾕｰｻﾞ情報が取得できない ⇒ userInfoData未設定の為、NullPointerException
//            var mode;
        // 【QP@40404】 E.kitazawa  --------------------- del end

        //引数をXMLﾌｧｲﾙに設定
        if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {		//【KPX@1602367】mod
            funClearRunMessage();
            return false;
        }

        //ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
        if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3080, xmlReqAry, xmlResAry, 1) == false) {		//【KPX@1602367】mod
            return false;
        }
    }

    return;
}

//========================================================================================
// 入力チェック
// 作成者：
// 作成日：
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
//        funErrorMsgBox("ロット数が未入力です。");
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

        	// 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start
        	try{
        		costValue = document.getElementById("no_value_" + (row + 1) + "_"  + (col + 1)).value;

                if (costValue != "") {
                    costCount++;
                }
        	}catch(e){

        	}
        	// 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end
        }

        if (costCount != colCount) {
            frm.chkKakunin.checked = false;
            funErrorMsgBox("色数またはコスト" + E000050);
            return;
        }
    }
}

//【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start
//========================================================================================
// 数値Format変換
// 作成者：TT.nishigawa
// 作成日：
// 引数  ：val:変換値
//       keta:小数桁（切り捨て桁）
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
//【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end

//【QP@30297】 No.25 E.kitazawa 課題対応 --------------------- mod start
//========================================================================================
// カーソル移動処理：↓↑キー押下
// 作成者：E.Kitazawa
// 作成日：2014/10/21
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
//【QP@30297】 No.25 E.kitazawa 課題対応 --------------------- mod end

//【QP@30297】No.19  E.kitazawa 課題対応 --------------------- add start
//========================================================================================
//メーカー名コンボボックス連動処理
//作成者：E.Kitazawa
//作成日：2014/09/01
//引数  ：なし
//概要  ：メーカーに紐付く包材コンボボックスを生成する
//========================================================================================
function funChangeMaker() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	var XmlId = "RGEN3010";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3010");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3010I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3010O);

    //包材ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
    funClearSelect(frm.ddlHouzai, 2);
    funClearSelect(frm.ddlBaseHansu, 2);	//版数ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ 【KPX@1602367】add
	frm.txtHansu.value = "";				//版数クリア【KPX@1602367】add

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
//【QP@30297】No.19 E.kitazawa 課題対応 --------------------- add end

//【KPX@1602367】add start
//========================================================================================
// ベース包材名コンボボックス連動処理
// 作成者：BRC Koizumi
// 作成日：2016/10/14
// 引数  ：なし
// 概要  ：メーカー、包材名に紐付く版数コンボボックスを生成する
//========================================================================================
function funChangeBaseHouzai() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	var XmlId = "RGEN3610";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3610","FGEN3660");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3610I,xmlFGEN3660I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3610O,xmlFGEN3660O);

	funClearSelect(frm.ddlBaseHansu, 2);	//版数ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
	frm.txtHansu.value = "";				//版数クリア

	//引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}

	//情報を取得
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3010, xmlReqAry, xmlResAry, 1) == false) {
		return false;
	}

	//版数ｺﾝﾎﾞﾎﾞｯｸｽの作成
	funCreateComboBox(frm.ddlBaseHansu, xmlResAry[2], 3);
	xmlFGEN3610.load(xmlResAry[2]);
    var no_hansu = funXmlRead(xmlResAry[3], "no_hansu", 0);
    if (no_hansu == "") {
    	costHansu = 1;

    } else {
    	costHansu = (parseInt(funXmlRead(xmlResAry[3], "no_hansu", 0)) + 1);

    }

//	var costHansuM = (parseInt(funXmlRead(xmlResAry[3], "no_hansu", 0)) + 1);
//	if(costHansuM == NaN) {
//		costHansu = 1;
//	} else {
//		costHansu = costHansuM;
//	}

	return true;

}

//========================================================================================
// ベース単価情報検索処理
// 作成者：BRC Koizumi
// 作成日：2014/02/25
// 引数  ：なし
// 概要  ：版数変更時、該当のベース単価情報を取得・設定する
//========================================================================================
function funBaseSearch() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照

	var XmlId = "RGEN3600";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3600");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3600I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3600O);

	//処理中ﾒｯｾｰｼﾞ表示
	funShowRunMessage();

	//一覧のｸﾘｱ
	funClearList();
	xmlFGEN3600O.src = "";
	frm.txtHansu.value = "";

	//引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		funClearRunMessage();
		return false;
	}

	//検索条件に一致するﾃﾞｰﾀを取得
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3600, xmlReqAry, xmlResAry, 1) == false) {
		funClearRunMessage();
		return false;
	}

	//ﾃﾞｰﾀ件数のﾁｪｯｸ
	if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
		//表示
		tblList.style.display = "block";
		// 一覧作成
		funCreateCostTbl(xmlResAry[2]);
		// 有効開始日、備考（原調）
		frm.txtYuko.value  = funXmlRead(xmlResAry[2], "dt_yuko", 0);
		frm.txtBiko.value  = funXmlRead(xmlResAry[2], "biko", 0);
		// コスト版数
		frm.txtHansu.value = costHansu;
		// コスト包材
//		frm.costHouzai.value = funXmlRead(xmlResAry[2], "name_houzai", 0);

		//処理中ﾒｯｾｰｼﾞ非表示
		funClearRunMessage();

	} else {
		//処理中ﾒｯｾｰｼﾞ非表示
		funClearRunMessage();
	}

	return true;
}
//【KPX@1602367】add end

