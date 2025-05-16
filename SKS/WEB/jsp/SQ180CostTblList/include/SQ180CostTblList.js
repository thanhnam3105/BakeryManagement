//========================================================================================
// 初期表示処理
// 作成者：M.Sakamoto
// 作成日：2014/02/25
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

    //画面設定
    funInitScreen(ConCostTblListId);

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //画面情報を取得・設定
    if (funGetInfo(1) == false) {
        return false;
    }

    //画面の初期化
    funClear();

    // 初期表示時、有効版のみで検索
    //ﾃﾞｰﾀ取得（確認・承認チェック）
    funDataSearch();

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

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //ﾍﾟｰｼﾞの設定
    funSetCurrentPage(1);

    // 全件チェック対応
    if (frm.chkAll.checked) {
        //全件ﾃﾞｰﾀ取得
        funDataAllSearch();
    } else if (frm.chkMishiyo.checked) {
    	// 未使用チェック対応
    	// 未使用時は、メーカー名、包材名のみ条件として有効とする
    	funDataMishiyoSearch();
    } else {
        //ﾃﾞｰﾀ取得（確認・承認チェック）
        funDataSearch();
    }

}

// 【QP@30297】No.21 2014/08/21  E.kitazawa 課題対応 --------------------- add start
//========================================================================================
// 検索処理
// 作成者：E.Kitazawa
// 作成日：2014/08/21
// 引数  ：なし
// 概要  ：コストテーブルの検索を行う（全件：確認・承認状態をチェックしない）
//========================================================================================
function funDataAllSearch() {

  var frm = document.frm00;    //ﾌｫｰﾑへの参照
  var XmlId = "RGEN3100";
  var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3100");
  var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3100I);
  var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3100O);
  var RecCnt;
  var PageCnt;
  var ListMaxRow;

  //処理中ﾒｯｾｰｼﾞ表示
  funShowRunMessage();

  //選択行の初期化
  funSetCurrentRow("");

  //一覧のｸﾘｱ
  funClearList();

  //引数をXMLﾌｧｲﾙに設定
  if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
      //一覧のｸﾘｱ
      funClearList();
      funClearRunMessage();
      return false;
  }

  //検索条件に一致する試作ﾃﾞｰﾀを取得
  if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3100, xmlReqAry, xmlResAry, 1) == false) {
      //一覧のｸﾘｱ
      funClearList();
      return false;
  }

  //ﾃﾞｰﾀ件数のﾁｪｯｸ
  if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
      //表示
      tblList.style.display = "block";

      // コストテーブル一覧の作成
      funCreateCostTableList(xmlResAry[2]);

      //処理中ﾒｯｾｰｼﾞ非表示
      funClearRunMessage();

  } else {
      //非表示
      tblList.style.display = "none";

      //処理中ﾒｯｾｰｼﾞ非表示
      funClearRunMessage();
  }

  return true;

}
// 【QP@30297】No.21 2014/08/21  E.kitazawa 課題対応 --------------------- add end

//========================================================================================
//未使用全件検索処理
//作成者：BRC Koizumi
//作成日：2016/10/27
//引数  ：なし
//概要  ：ベース単価の検索を行う
//       （未使用全件取得：版数・確認・承認・全件・有効版のみ状態をチェックしない）
//========================================================================================
function funDataMishiyoSearch() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	var XmlId = "RGEN3710";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3710");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3710I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3710O);

	//処理中ﾒｯｾｰｼﾞ表示
	funShowRunMessage();

	//選択行の初期化
	funSetCurrentRow("");

	//一覧のｸﾘｱ
	funClearList();

	//引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		//一覧のｸﾘｱ
		funClearList();
		//処理中ﾒｯｾｰｼﾞ非表示
		funClearRunMessage();
		return false;
	}

	//検索条件に一致するベース単価ﾃﾞｰﾀを取得
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3710, xmlReqAry, xmlResAry, 1) == false) {
		//一覧のｸﾘｱ
		funClearList();
		return false;
	}

	//ﾃﾞｰﾀ件数のﾁｪｯｸ
	if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
		//表示
		tblList.style.display = "block";

		// コストテーブル一覧の作成
	    funCreateCostTableList(xmlResAry[2]);

		//処理中ﾒｯｾｰｼﾞ非表示
		funClearRunMessage();

	} else {
		//明細非表示
		tblList.style.display = "none";

		//処理中ﾒｯｾｰｼﾞ非表示
		funClearRunMessage();
	}

	return true;

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
    var XmlId = "RGEN3020";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3020");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3020I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3020O);
    var RecCnt;
    var PageCnt;
    var ListMaxRow;

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //選択行の初期化
    funSetCurrentRow("");

    //一覧のｸﾘｱ
    funClearList();

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        //一覧のｸﾘｱ
        funClearList();
        funClearRunMessage();
        return false;
    }

    //検索条件に一致する試作ﾃﾞｰﾀを取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3020, xmlReqAry, xmlResAry, 1) == false) {
        //一覧のｸﾘｱ
        funClearList();
        return false;
    }

    //ﾃﾞｰﾀ件数のﾁｪｯｸ
    if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
        //表示
        tblList.style.display = "block";

        // コストテーブル一覧の作成
        funCreateCostTableList(xmlResAry[2]);

        //処理中ﾒｯｾｰｼﾞ非表示
        funClearRunMessage();

    } else {
        //非表示
        tblList.style.display = "none";

        //処理中ﾒｯｾｰｼﾞ非表示
        funClearRunMessage();
    }

    return true;

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
    funItemDisabled(frm.txtHansu, false);
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
    // 空の包材ｺﾝﾎﾞﾎﾞｯｸｽを設定
    funCreateComboBox(frm.ddlHouzai, xmlFGEN3010O, 2);
    funDefaultIndex(frm.ddlHouzai, 1);

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
    xmlFGEN3020O.src = "";
    tblList.style.display = "none";
    var detail = document.getElementById("detail");
    while(detail.firstChild){
        detail.removeChild(detail.firstChild);
    }
}

//========================================================================================
// 次画面遷移処理
// 作成者：M.Sakamoto
// 作成日：2014/02/25
// 概要  ：次画面に遷移する
//========================================================================================
function funNext(mode) {
    //画面を閉じる
    close(self);
    return true;


//    var wUrl;
//
//    //遷移先判定
//    switch (mode) {
//        case 0:    //メインメニュー
//            wUrl = "../SQ170GenchoMenu/SQ170GenchoMenu.jsp";
//            break;
//    }
//
//    //遷移
//    funUrlConnect(wUrl, ConConectPost, document.frm00);

    return true;
}

//========================================================================================
// 原価試算画面へ遷移
// 作成者：M.Sakamoto
// 作成日：2014/02/25
// 引数  ：mode     ：モード  1,コピー 2,登録,3承認
// 概要  ：参照ユーザの閲覧権限にて処理を判定する
//========================================================================================
function funOpenConstTblAdd(mode){

    var frm = document.frm00;    // フォームへの参照

    // 選択されていない場合
    if(funGetCurrentRow().toString() == ""){
        funErrorMsgBox(E000002);
        return false;
    }
    // 選択されている場合
    else{

        // 処理モードを保存する
        frm.mode.value = mode;

        // コストテーブル登録・承認画面起動情報通知に成功
        if(funCostTblAddTuti(1)){

            // 承認ボタン押下ならば確認済みであるかをチェックする
            if(mode == 4){
                if(!funCheckKakuninData(1)){
                    return;
                }
            }

            // コストテーブル登録・承認画面を表示する
            window.open("../SQ190CostTblAdd/SQ190CostTblAdd.jsp","shisaquick_genka","menubar=no,resizable=yes");

        }
    }
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

    // メーカー名のみ取得
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3000");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3000I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3000O);

    // 引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    // ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3000, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    // ﾕｰｻﾞ情報表示
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");


    // 画面権限制御
    var reccnt = funGetLength(xmlResAry[1]);
    for (i = 0; i < reccnt; i++) {
        var GamenId = funXmlRead(xmlResAry[1], "id_gamen", i);

        // 画面IDで分岐
        switch (GamenId.toString()) {
            case ConGmnIdCostTblAdd.toString():
                // コストテーブル登録・承認画面の権限有りの場合のみ使用可能
                // frm.btnCopy.disabled = false; 【KPX@1602367 20160926削除】
                frm.btnNew.disabled = false;
                frm.btnShonin.disabled = false;
                break;
        }
    }
    // ｺﾝﾎﾞﾎﾞｯｸｽの作成
    funCreateComboBox(frm.ddlMakerName, xmlResAry[2], 1);

    // ﾃﾞﾌｫﾙﾄのｺﾝﾎﾞﾎﾞｯｸｽ設定値を退避
    xmlFGEN3000.load(xmlResAry[2]);

    return true;
}

//========================================================================================
// コストテーブル一覧作成処理
// 作成者：M.Sakamoto
// 作成日：2014/02/25
// 概要  ：コストテーブル一覧を作成する
//========================================================================================
function funCreateCostTableList(xmlData) {

    var html;
    var reccnt;

    //件数取得
    reccnt = funGetLength(xmlData);

    var detail = document.getElementById("detail");

    for(var i = 0; i < reccnt; i++){

        // 行設定
        var tr = document.createElement("tr");
        tr.setAttribute("class", "disprow");

        // №
        var td1 = document.createElement("td");
        td1.setAttribute("class", "column");

        html = funXmlRead(xmlData, "no_row", i);
        html += "<input type=\"hidden\" id=\"cd_maker_" + i + "\" value=\"" + funXmlRead(xmlData, "cd_maker", i) + "\">";
        html += "<input type=\"hidden\" id=\"cd_houzai_" + i + "\" value=\"" + funXmlRead(xmlData, "cd_houzai", i) + "\">";
        html += "<input type=\"hidden\" id=\"no_hansu_" + i + "\" value=\"" + funXmlRead(xmlData, "no_hansu", i) + "\">";
        td1.innerHTML = html;
        td1.style.textAlign = "right";
        td1.style.width = 30;
        tr.appendChild(td1);

        // メーカー名
        var td2 = document.createElement("td");
        td2.setAttribute("class", "column");
        td2.innerHTML = funXmlRead(xmlData, "nm_maker", i);
        td2.style.textAlign="left";
        td2.style.width = 157;
        tr.appendChild(td2);

        // 版数
        var td3 = document.createElement("td");
        td3.setAttribute("class", "column");
        td3.innerHTML = funXmlRead(xmlData, "no_hansu", i);
        td3.style.textAlign="right";
        td3.style.width = 30;
        tr.appendChild(td3);

        // 版の包材
        var td4 = document.createElement("td");
        td4.setAttribute("class", "column");
        html = funXmlRead(xmlData, "nm_houzai", i);
        if(html == ""){
            html = "&nbsp;"
        }
        td4.innerHTML = html;
        td4.style.textAlign="left";
        td4.style.width = 392;
        tr.appendChild(td4);

        // 登録者
        var td5 = document.createElement("td");
        td5.setAttribute("class", "column");
        html = funXmlRead(xmlData, "nm_kakunin", i);
        if(html == ""){
            html = "&nbsp;"
        }
        td5.innerHTML = html;
        td5.style.textAlign="left";
        td5.style.width = 118;
        tr.appendChild(td5);

        // 承認者
        var td6 = document.createElement("td");
        td6.setAttribute("class", "column");
        html = funXmlRead(xmlData, "nm_shonin", i);
        if(html == ""){
            html = "&nbsp;"
        }
        td6.innerHTML = html;
        td6.style.textAlign="left";
        td6.style.width = 118;
        tr.appendChild(td6);

        // 有効開始日
        var td7 = document.createElement("td");
        td7.setAttribute("class", "column");
        html = funXmlRead(xmlData, "dt_yuko", i);
        if(html == ""){
            html = "&nbsp;"
        }
        td7.innerHTML = html;
        td7.style.textAlign="left";
        td7.style.width = 88;
        tr.appendChild(td7);

        // 変更日
        var td8 = document.createElement("td");
        td8.setAttribute("class", "column");
        html = funXmlRead(xmlData, "dt_koshin", i);
        if(html == ""){
            html = "&nbsp;"
        }
        td8.innerHTML = html;
        td8.style.textAlign="left";
        td8.style.width = 78;
        tr.appendChild(td8);

        // ベース包材名
        var td9 = document.createElement("td");
        td9.setAttribute("class", "column");
        html = funXmlRead(xmlData, "nm_base_houzai", i);
        if(html == ""){
            html = "&nbsp;"
        }
        td9.innerHTML = html;
        td9.style.textAlign="left";
        td9.style.width = 391;
        tr.appendChild(td9);

        detail.appendChild(tr);
    }
}

//========================================================================================
// 権限関連処理
// 作成者：M.Sakamoto
// 作成日：2014/02/25
// 概要  ：権限情報の退避、画面制御を行う
//========================================================================================
function funSaveKengenInfo() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //コピー、登録、承認ボタンの制御
    frm.btnNew.disabled = false;
    frm.btnCopy.disabled = false;
    frm.btnApproval.disabled = false;

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
    	// メーカー名
        if (XmlId.toString() == "RGEN3000") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3000
                    break;
            }
        // 包材名
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
        //検索ボタン押下
        } else if (XmlId.toString() == "RGEN3020"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //RGEN3020
                    funXmlWrite(reqAry[i], "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);

                    // 承認チェック
                    if (frm.chkShonin.checked) {
                    	funXmlWrite(reqAry[i], "chk_shonin", "1", 0);
                    } else {
                    	funXmlWrite(reqAry[i], "chk_shonin", "", 0);
                    }
                    // 確認チェック
                    if (frm.chkKakunin.checked) {
                    	funXmlWrite(reqAry[i], "chk_kakunin", "1", 0);
                    } else if (frm.chkShonin.checked){
                    	funXmlWrite(reqAry[i], "chk_kakunin", "1", 0);
                    	frm.chkKakunin.checked = false;
                    } else {
                    	funXmlWrite(reqAry[i], "chk_kakunin", "", 0);
                    }
                    // 版のみチェック
                    if (frm.chkHanOnly.checked) {
                    	funXmlWrite(reqAry[i], "chk_hanonly", "1", 0);
                    } else {
                    	funXmlWrite(reqAry[i], "chk_hanonly", "", 0);
                    }
                    break;
            }
        //検索ボタン押下（全件チェック）
        } else if (XmlId.toString() == "RGEN3100"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //RGEN3100
                    funXmlWrite(reqAry[i], "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "no_hansu", frm.txtHansu.value, 0);    // RGEN3020 にno_hansuがないのはなぜ？
                    break;
            }

        // 承認ボタン押下
        } else if (XmlId.toString() == "RGEN3070"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
            }
        // 登録ボタン押下
        } else if (XmlId.toString() == "RGEN2160"){

            var cd_maker = document.getElementById("cd_maker_" + funGetCurrentRow()).value;
            var cd_houzai = document.getElementById("cd_houzai_" + funGetCurrentRow()).value;
            var no_hansu = document.getElementById("no_hansu_" + funGetCurrentRow()).value;
            var mishiyo;
            if (frm.chkMishiyo.checked) {
            	mishiyo = 1;
            } else {
            	mishiyo = 2;
            }
            var put_code = document.getElementById("mode").value + "-" + cd_maker + "-" + cd_houzai + "-" + no_hansu + "-2" + "-" + mishiyo;

            // 検索コード置換【メーカーコード:::包材コード:::版数】
            var put_code = put_code.replace(/-/g,":::");

            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", put_code, 0);
                    break;
            }
        //検索ボタン押下（未使用チェック）
        } else if (XmlId.toString() == "RGEN3710"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //RGEN3710
                    funXmlWrite(reqAry[i], "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    var mishiyo;
                    if (frm.chkMishiyo.checked) {
                    	mishiyo = 1;
                    } else {
                    	mishiyo = 2;
                    }
                    funXmlWrite(reqAry[i], "chk_mishiyo", mishiyo, 0);
                    break;
            }
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
    var selIndex = 0;

    // ｺﾝﾎﾞﾎﾞｯｸｽのｲﾝﾃﾞｯｸｽ指定
    obj.selectedIndex = selIndex;

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
// コストテーブル登録・承認画面起動情報通知
// 作成者：Y.Nishigawa
// 作成日：2009/10/27
// 引数  ：なし
// 概要  ：選択した試作コードをセッションへ保存する
//========================================================================================
function funCheckKakuninData(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN3070";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3070");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3070I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3070O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlFGEN3070, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //ﾃﾞｰﾀ件数のﾁｪｯｸ
    if (funXmlRead(xmlResAry[2], "flg_return", 0) != "true") {
        return false;
    }

    return true;
}

// 【QP@30297】No.19 E.kitazawa 課題対応 --------------------- add start
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

