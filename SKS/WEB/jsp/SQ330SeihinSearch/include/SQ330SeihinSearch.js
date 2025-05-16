//========================================================================================
//【KPX@1602367】
// 共通変数
// 作成者：May Thu
// 作成日：2016/09/13
//========================================================================================

//========================================================================================
// 初期表示処理
// 作成者：May Thu
// 作成日：2016/09/13
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {
    //画面設定
    funInitScreen(ConSeihinSearchId);

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();
	//終了ボタン押下時製品コードに空にする
	window.returnValue = "";
    return true;
}

//========================================================================================
// 画面情報取得処理
// 作成者：May Thu
// 作成日：2016/09/13
// 引数  ：①mode  ：処理モード
//         1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：ユーザのセッション情報、部署情報、対象資材、発注先情報を取得する
//========================================================================================
function funGetInfo(mode) {
	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	var XmlId = "RGEN3590";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3590");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3590I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3590O);

	// 引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
		return false;
	}
	// ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3590, xmlReqAry, xmlResAry, mode) == false) {
		return false;
	}
    //scrflag = other ⇒検索
     funCreateSizaiTableList(xmlResAry[2]);
     funCountInfoDisplay(funGetLength(xmlResAry[2]),"divCountInfo");

	return true;
}

//========================================================================================
// XMLファイルに書き込み
// 作成者：May Thu
// 作成日：2016/09/15
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
        if (XmlId.toString() == "RGEN3590"){
              //FGEN3590
				funXmlWrite(reqAry[i], "name_seihin", frm.inputNmHimei.value, 0);
		}
    }
    return true;

}

//========================================================================================
// 資材情報　テーブル一覧作成処理
// 作成者：May Thu
// 作成日：2014/09/24
// 引数  ：xmlData    ：XMLデータ
// 概要  ：資材情報一覧を作成する
//========================================================================================
function funCreateSizaiTableList(xmlData) {
	//件数取得し、行数を保存
	rowcnt = funGetLength(xmlData);
	for(var i = 0; i < rowcnt; i++){
		// 行追加
		funAddSizaiTable(i, xmlData);
	}

}

//========================================================================================
// 資材情報 明細行作成処理
// 作成者：May Thu
// 作成日：2016/09/15
// 引数  ：①row     ：行番号 （0 ～ ）
//       ：②xmlData ：XMLデータ
// 概要  ：情報一覧の行追加処理
//========================================================================================
function funAddSizaiTable(row, xmlData) {
	var detail = document.getElementById("detail");
	var html;

	// 行設定
	var tr = document.createElement("tr");
	tr.setAttribute("class", "disprow");
	tr.setAttribute("id", "tr" + row);

	var td1 = document.createElement("td");
	td1.setAttribute("class", "column");
	td1.innerHTML = funXmlRead(xmlData, "cd_hin", row);
	if (td1.innerHTML == "") {
		td1.innerHTML = "&nbsp;";
	}
	td1.style.textAlign = "left";
	td1.style.width =85;
	tr.appendChild(td1);	

	// 
	var td2 = document.createElement("td");
	td2.setAttribute("class", "column");
	td2.innerHTML = funXmlRead(xmlData, "nm_seihin", row);
	if (td2.innerHTML == "") {
		td2.innerHTML = "&nbsp;";
	}
	td2.style.textAlign = "left";
	td2.style.width = 200;
	tr.appendChild(td2);

	// 
	var td3 = document.createElement("td");
	td3.setAttribute("class", "column");
	td3.innerHTML = funXmlRead(xmlData, "nisugata_hyoji", row);
	if (td3.innerHTML == "") {
		td3.innerHTML = "&nbsp;";
	}
	td3.style.textAlign = "left";
	td3.style.width = 200;
	tr.appendChild(td3);

	detail.appendChild(tr);
	
}

//========================================================================================
// 終了ボタン押下
// 作成者：May Thu
// 作成日：2016/09/16
// 概要  ：終了処理
//========================================================================================
function funEndClick(){
	//終了処理
	funEnd();
	
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
	if(!window.opener || window.opener.closed){
		// モーダルで開いた時
		// 画面を閉じる
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
// 検索ボタン押下処理
// 作成者：May Thu
// 作成日：2016/09/16
// 引数  ：なし
// 概要  ：データの検索を行う
//========================================================================================
function funSeihinNameSearch() {
	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	var detail = document.getElementById("detail");
	// 明細データ削除 検索すると、データが重なっているから、まずデータがあると、削除する
	while(detail.firstChild){
		detail.removeChild(detail.firstChild);
	}
    //検索処理
    //処理中ウィンドウ表示の為、setTimeoutで処理予約
   setTimeout(function(){ funSearch0() }, 0);
}

//========================================================================================
//検索ボタン押下処理
//作成者：May Thu
//作成日：2016/09/16
//引数  ：なし
//概要  ：資材データの検索を行う
//========================================================================================
function funSearch0() {
	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	//処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //画面情報を取得・設定
    if (funGetInfo(1) == false) {
        //処理中ﾒｯｾｰｼﾞ非表示
        funClearRunMessage();
        return false;
    }

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();
    return true;

}

//========================================================================================
//検索ボタン押下処理
//作成者：May Thu
//作成日：2016/09/16
//引数  ：なし
//概要  ：資材データ検索結果を返す。
//========================================================================================
function funChoiceSeihin() {
	    //行が選択されていない場合
    if (funGetCurrentRow().toString() == "") {
        funErrorMsgBox(E000002);
        return false;
    }
    //戻り値の設定
    window.returnValue = funXmlRead(xmlFGEN3590O, "cd_hin", funGetCurrentRow());

    //画面を閉じる
    close(self);
}





