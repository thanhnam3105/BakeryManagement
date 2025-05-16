//========================================================================================
// 初期表示処理
// 作成者：M.Sakamoto
// 作成日：2014/02/25
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

    //画面設定
    funInitScreen(ConCostTblRefId);

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
// 版数の入力制御処理
// 作成者：M.Sakamoto
// 作成日：2014/02/25
// 引数  ：なし
// 概要  ：製法Noの入力制御を行う
//========================================================================================
function funUseHansuNo(obj) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    if (obj.checked) {
        //版数入力可能
        funItemDisabled(frm.txtHansu, false);
    } else {
        //版数入力不可
        funItemDisabled(frm.txtHansu, true);
    }

}

//========================================================================================
// 検索ボタン押下処理
// 作成者：M.Sakamoto
// 作成日：2014/02/25
// 引数  ：なし
// 概要  ：試作データの検索を行う
//========================================================================================
function funSearch() {

    //ﾃﾞｰﾀ取得
    funDataSearch();

}

//========================================================================================
// 検索処理
// 作成者：M.Sakamoto
// 作成日：2014/02/25
// 引数  ：なし
// 概要  ：試作データの検索を行う
//========================================================================================
function funDataSearch() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN3040";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3040");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3040I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3040O);

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //選択行の初期化
    funSetCurrentRow("");

    frm.txtShiyoRyo.value = "";

    funClearHeader();

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        //一覧のｸﾘｱ
        funClearList();
        funClearRunMessage();
        return false;
    }

    //検索条件に一致する試作ﾃﾞｰﾀを取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3040, xmlReqAry, xmlResAry, 1) == false) {
        //一覧のｸﾘｱ
        funClearList();
        return false;
    }

    //ﾃﾞｰﾀ件数のﾁｪｯｸ
    if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {

        // ヘッダ作成
        funCreateHeader(xmlResAry[2]);
        // 【QP@30297】 E.kitazawa 課題対応 --------------------- del start
//        funRowDelete_Tbl(xmlResAry[2], "xmlFGEN3040", 0);
        // 【QP@30297】 E.kitazawa 課題対応 --------------------- del end

        // 【QP@30297】No.23 2014/08/20  E.kitazawa 課題対応 --------------------- add start
        // 一覧作成（明細部）
        funCreateCostTbl(xmlResAry[2]);

        //処理中ﾒｯｾｰｼﾞ非表示
        funClearRunMessage();
        // 【QP@30297】No.23 2014/08/20  E.kitazawa 課題対応 --------------------- add end

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

    return true;

}

//========================================================================================
// ヘッダ初期化処理
// 作成者：M.Sakamoto
// 作成日：2014/02/25
// 引数  ：なし
// 概要  ：ヘッダを初期化する
//========================================================================================
function funClearHeader() {
    var id;

    for (i = 0; i < 30; i++) {
        id  = "tblHeader" + funZeroPudding(i + 1);
        document.getElementById(id).value = "";

        id  = "calcRslt" + funZeroPudding(i + 1);
        document.getElementById(id).innerText = "";
    }

    // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start
    document.getElementById("txtYuko").innerHTML = "";
    //20140616 del start【QP@30297】課題
    //document.getElementById("txtBiko").innerHTML = "";
    //20140616 del end【QP@30297】課題
    document.getElementById("txtBiko_kojo").innerHTML = "";
    // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end
}

//========================================================================================
// ヘッダ初期化処理
// 作成者：M.Sakamoto
// 作成日：2014/02/25
// 引数  ：なし
// 概要  ：ヘッダを初期化する
//========================================================================================
function funCreateHeader(data) {

    var key;
    var id;

    // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start
    for (i = 0; i < 30; i++) {
        key = "no_value" + funZeroPudding(i + 1);;
        id  = "tblHeader" + funZeroPudding(i + 1);
        document.getElementById(id).value = funAddComma(funXmlRead(data, key, 0));
    }
    document.getElementById("txtYuko").innerHTML = funXmlRead(data, "dt_yuko", 0);
    //20140616 del start【QP@30297】課題
    //document.getElementById("txtBiko").innerHTML = funXmlRead(data, "biko", 0);
    //20140616 del end【QP@30297】課題
    document.getElementById("txtBiko_kojo").innerHTML = funXmlRead(data, "biko_kojo", 0);
    // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end
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

    // 【QP@30297】No.17 2014/08/20  E.kitazawa 課題対応 --------------------- mod start
    //  個装使用量 < 1 を許可する
    // if(frm.txtShiyoRyo.value == "" || parseInt(frm.txtShiyoRyo.value) == 0) {
    if(frm.txtShiyoRyo.value == "" || parseFloat(frm.txtShiyoRyo.value) == 0) {
        return;
    }
    // 【QP@30297】No.17 2014/08/20  E.kitazawa 課題対応 --------------------- mod end

    for (i = 0; i < 30; i++) {
        headerId  = "tblHeader" + funZeroPudding(i + 1);
        // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start
        val = document.getElementById(headerId).value.replace(/,/g,"");
        if(val != ""){
            calcRsltId  = "calcRslt" + funZeroPudding(i + 1);
            // 【QP@30297】No.20 2014/08/20  E.kitazawa 課題対応 --------------------- mod start
            // document.getElementById(calcRsltId).innerText = funAddComma(Math.floor((val / frm.txtShiyoRyo.value.replace(/,/g,"")) * 10) / 10);
            // 小数点以下切り捨て
            document.getElementById(calcRsltId).innerText = funAddComma(Math.floor(val / frm.txtShiyoRyo.value.replace(/,/g,"")));
            // 【QP@30297】No.20 2014/08/20  E.kitazawa 課題対応 --------------------- mod end
        }
        // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end
    }
}

//========================================================================================
// 画面初期処理　（クリアボタン押下処理）
// 作成者：M.Sakamoto
// 作成日：2014/02/25
// 引数  ：なし
// 概要  ：画面を初期化する
//========================================================================================
function funClear() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //画面の初期化
    frm.reset();
    frm.ddlMakerName.selectedIndex = 0;
    frm.ddlHouzai.selectedIndex = 0;

    //一覧のｸﾘｱ
    funClearList();

    //ﾃﾞﾌｫﾙﾄのｺﾝﾎﾞﾎﾞｯｸｽ設定値を読み込む
    xmlFGEN3000O.load(xmlFGEN3000);
    xmlFGEN3010O.load(xmlFGEN3010);

    //ｺﾝﾎﾞﾎﾞｯｸｽの再設定
    funCreateComboBox(frm.ddlMakerName, xmlFGEN3000O, 1);
    funDefaultIndex(frm.ddlMakerName, 1);
    // 【QP@30297】No.19 E.kitazawa 課題対応 --------------------- mod start
    // 空の包材ｺﾝﾎﾞﾎﾞｯｸｽを設定
//    funCreateComboBox(frm.ddlHouzai, xmlFGEN3010O, 1);
    funCreateComboBox(frm.ddlHouzai, xmlFGEN3010O, 2);
    funDefaultIndex(frm.ddlHouzai, 1);
    // 【QP@30297】No.19 E.kitazawa 課題対応 --------------------- mod end

    return true;

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
    xmlFGEN3040O.src = "";
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
    var XmlId = "RGEN3000";
 // 【QP@30297】No.19 E.kitazawa 課題対応 --------------------- mod start
/*    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3000","FGEN3010");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3000I,xmlFGEN3010I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3000O,xmlFGEN3010O);
*/
    // メーカー名のみ取得
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3000");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3000I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3000O);
 // 【QP@30297】No.19 E.kitazawa 課題対応 --------------------- mod end

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3000, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    // 【QP@30297】No.19 2014/10/28 E.kitazawa 課題対応 --------------------- mod start
    //ｺﾝﾎﾞﾎﾞｯｸｽの作成
    funCreateComboBox(frm.ddlMakerName, xmlResAry[2], 1);
//    funCreateComboBox(frm.ddlHouzai, xmlResAry[3], 1);

    //ﾃﾞﾌｫﾙﾄのｺﾝﾎﾞﾎﾞｯｸｽ設定値を退避
    xmlFGEN3000.load(xmlResAry[2]);
//    xmlFGEN3010.load(xmlResAry[3]);
    // 【QP@30297】No.19 2014/10/28 E.kitazawa 課題対応 --------------------- mod end

    return true;

}

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
        //画面初期表示
        if (XmlId.toString() == "RGEN3000") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3000
                    break;
                case 2:    //FGEN3010 --- 廃止【QP@30297】No.19
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

        //検索ﾎﾞﾀﾝ押下
        } else if (XmlId.toString() == "RGEN3040"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    // FGEN3040
                    funXmlWrite(reqAry[i], "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "no_hansu", frm.txtHansu.value);
                    break;
            }
        }
        //【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start
        //承認版検索
        else if (XmlId.toString() == "RGEN3090"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    // FGEN3090
                    funXmlWrite(reqAry[i], "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    break;
            }
        }
        //【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end
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
        case 1:    //ﾘﾃﾗﾙﾏｽﾀ
            atbName = "nm_literal";
            atbCd = "cd_literal";
            break;

          //【QP@30297】No.19 E.kitazawa 課題対応 --------------------- add start
        case 2:    //ﾘﾃﾗﾙﾏｽﾀ（第2カテゴリ）
            atbName = "nm_2nd_literal";
            atbCd = "cd_2nd_literal";
            break;
          //【QP@30297】No.19 E.kitazawa 課題対応 --------------------- add end
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
// 作成日：2009/03/25
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②mode     ：モード
// 概要  ：コンボボックスのデフォルト値を選択する
//========================================================================================
function funDefaultIndex(obj, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    //【QP@30297】No.19 E.kitazawa 課題対応 --------------------- mod start
//    var selIndex;
//    var i;
    var selIndex = 0;
    //【QP@30297】No.19 E.kitazawa 課題対応 --------------------- mod end

    //ｺﾝﾎﾞﾎﾞｯｸｽのｲﾝﾃﾞｯｸｽ指定
    obj.selectedIndex = selIndex;

    return true;
}

//========================================================================================
// 終了ボタン押下
// 作成者：Y.Nishigawa
// 作成日：2009/11/07
// 概要  ：終了処理
//========================================================================================
function funEndClick(){
    //終了処理
    funEnd();
}

//========================================================================================
// 終了処理
// 作成者：Y.Nishigawa
// 作成日：2009/11/05
// 概要  ：終了処理
//========================================================================================
function funEnd(){

    //画面を閉じる
    window.close();

}

//【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start
// メーカ名、包材選択時に承認版取得
function funHanSearch() {

 var frm = document.frm00;    //ﾌｫｰﾑへの参照
 var XmlId = "RGEN3090";
 var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3090");
 var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3090I);
 var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3090O);


 //メーカ名、包材が選択されていない場合は処理中止
 if(frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value == ""){
	 //版数のｸﾘｱ
	 frm.txtHansu.value = "";
	 return;
 }
 if(frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value == ""){
	 //版数のｸﾘｱ
	 frm.txtHansu.value = "";
	 return;
 }

 //処理中ﾒｯｾｰｼﾞ表示
 funShowRunMessage();
 //引数をXMLﾌｧｲﾙに設定
 if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
     funClearRunMessage();
     return false;
 }

 //検索条件に一致する試作ﾃﾞｰﾀを取得
 if (funAjaxConnection(XmlId, FuncIdAry, xmlFGEN3090, xmlReqAry, xmlResAry, 1) == false) {
     return false;
 }

 //件数取得
 reccnt = funGetLength(xmlResAry[2]);

 //複数件あった場合
 if(reccnt > 1){
	 funInfoMsgBox("承認されている版数が複数見つかりました。\\nカンマ区切りで表示します。");
 }

 //版数の設定
 var val = "";
 for(i=0; i<reccnt; i++){
	 if(i >= 1){
		 val = val + ",";
	 }
	 val = val + funXmlRead(xmlResAry[2], "no_hansu", i);
 }
 frm.txtHansu.value = val;

//処理中ﾒｯｾｰｼﾞ非表示
 funClearRunMessage();

 return true;

}
//【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end


//【QP@30297】No.23 2014/08/20  E.kitazawa 課題対応 --------------------- add start
//========================================================================================
// コストテーブル表示処理
// 作成者：E.kitazawa
// 作成日：2014/08/20
// 引数  ：①xmlData  ：XMLデータ(配列）
// 概要  ：コストテーブルを作成する
//========================================================================================
function funCreateCostTbl(xmlData) {

  var key;
  var id;

  for (i = 1; i < 31; i++) {

      key = "nm_title";
      id  = "nm_title_" + i;

      document.getElementById(id).value = funXmlRead(xmlData, key, i);

      for (j = 1; j < 31; j++) {
          key = "no_value" + funZeroPudding(j);
          id  = "no_value_" + i + "_" + j;

          // 少数点以下2位まで表示（3位以下切り捨て）
          document.getElementById(id).value = funAddComma(funNumFormatChange(funXmlRead(xmlData, key, i), 2));
      }
  }

}


//数値Format変換
//引数　　val:変換値
//　　　keta:小数桁（切り捨て桁）
//戻り値　変換値（※数値でない場合は空文字返却）
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
//【QP@30297】No.23 2014/08/20  E.kitazawa 課題対応 --------------------- add end

//【QP@30297】No.19 E.kitazawa 課題対応 --------------------- add start
//========================================================================================
// メーカー名コンボボックス連動処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
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
  //版数のｸﾘｱ
  frm.txtHansu.value = "";

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


