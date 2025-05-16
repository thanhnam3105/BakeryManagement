//========================================================================================
// 共通変数
// 作成者：E.Kitazawa
// 作成日：2014/11/19
//========================================================================================
//データ保持
var sv_seihinCd = "";			// 製品コード退避
var seihin_errmsg = "";			// 製品コード入力エラーメッセージ（フォーカス移動対応）
//May thu START 2016.10.02
var rowcnt = 0;			// 明細行数
var cd_kaisha = 0;		// 会社コード
// DBに登録できる最大ファイル名の長さ
var MAXLEN_FILENM = 255;
// DB検索時のMAX更新日時
var MAX_DtKoshin = "";
var E000067 = "登録ボタンが押されていないため、「資材手配依頼出力画面」に遷移できません。\\n登録ボタンを押して下さい。";
//May thu START 2016.10.02
//========================================================================================
// 初期表示処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {
    //画面設定
    funInitScreen(ConShizaiTehaiInputId);

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //画面情報を取得・設定
    if (funGetInfo(1) == false) {
        //処理中ﾒｯｾｰｼﾞ非表示
        funClearRunMessage();
        return false;
    }
    //画面の初期化
    //funClear();

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();
    return true;
}

//========================================================================================
// 画面情報取得処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：①mode  ：処理モード
//         1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：ユーザのセッション情報、部署情報、対象資材、発注先情報を取得する
//========================================================================================
function funGetInfo(mode) {
	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	var XmlId = "RGEN3200";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"SA290","FGEN3200","FGEN3310","FGEN2130","FGEN3450");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA290I,xmlFGEN3200I,xmlFGEN3310I,xmlFGEN2130I,xmlFGEN3450I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA290O,xmlFGEN3200O,xmlFGEN3310O,xmlFGEN2130O,xmlFGEN3450O);

	// 引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
		return false;
	}

	// ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3200, xmlReqAry, xmlResAry, mode) == false) {
		return false;
	}
	// ﾕｰｻﾞ情報表示
	funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

	// アップロード情報
	if (funXmlRead(xmlResAry[6], "flg_return", 0) == "true") {
		xmlFGEN3450.load(xmlResAry[6]);
		// アップロード後の再ロード
        funSearchConditionSet(xmlResAry[6]);
        funSearch();
	}

	// 権限
	if (funXmlRead(xmlResAry[5], "flg_gentyo", 0)) {
		// 原調ユーザーは登録ボタンを活性
		frm.btnInsert.disabled = false;
	} else {
		// 工場ユーザーは登録ボタンを非活性
		frm.btnInsert.disabled = true;
	}
	// 製品コード
	frm.inputSeihinCd.focus();
	frm.inputSeihinCd.click();

	return true;
}

//========================================================================================
// 初期処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：なし
// 概要  ：画面を初期化する
//========================================================================================
function funClear() {

  var frm = document.frm00;    //ﾌｫｰﾑへの参照

  //画面の初期化
  frm.reset();

  //一覧のｸﾘｱ
  funClearList();

  return true;

}


//========================================================================================
// 一覧クリア処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：なし
// 概要  ：一覧の情報を初期化する
//========================================================================================
function funClearList() {

//	var frm = document.frm00;    //ﾌｫｰﾑへの参照

	// 資材情報
	// 一覧のｸﾘｱ
	xmlFGEN3450O.src = "";

	tblList.style.display = "none";
	// 明細行（読み込み分）
	var detail = document.getElementById("detail");
	// 明細データ削除
	while(detail.firstChild){
		detail.removeChild(detail.firstChild);
	}

	// 原料情報
	xmlFGEN0012O.src = "";
	var Genryo_Left = document.getElementById("divGenryo_Left");
	var Genryo_Right = document.getElementById("divGenryo_Right");
	Genryo_Left.style.display = "none";
	Genryo_Right.style.display = "none";


}

//========================================================================================
// 製品コード検索処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：keyCode ： ENTER 又は TAB
// 概要  ：製品コードより試作No一覧、製品名、荷姿の検索を行う
//========================================================================================
function funSeihinSearch(keyCode) {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	// 製品コード
	var inputSeihin = document.getElementById("inputSeihinCd");

	// 製品コードエラーメッセージをクリア
	seihin_errmsg = "";
	// 製品コードが変更されていない時、検索を実行しない
	if (inputSeihin.value == sv_seihinCd) {
		// 空白でENTERキー押下時はエラーを表示
		if (inputSeihin.value == "" && keyCode == 13) {
			seihin_errmsg = "製品コードは必須項目です。\\n 入力して下さい。";
			funErrorMsgBox(seihin_errmsg);
			// 製品コードにフォーカスを戻す（次に移ってしまう）
			inputSeihin.focus();
			return false;
		} else {
			// 空白でもタブ移動時は次へ
			return;
		}
	}

	var XmlId = "RGEN3420";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3420","FGEN3430");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3420I,xmlFGEN3430I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3420O,xmlFGEN3430O);

	// 製品名、荷姿をクリア
	frm.seihinNm.value = "";
	frm.nisugata.value = "";
	// 製造工場
	frm.seizoKojoCd.value = "";
	frm.seizoKojoNm.value = "";
	//ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
	funClearSelect(frm.ddlShisakuNo, 2);
	funClearSelect(frm.ddlShokuba, 2);
	funClearSelect(frm.ddlLine, 2);

	// 資材情報一覧の初期化
	funClearList();

	//処理中ﾒｯｾｰｼﾞ表示
	funShowRunMessage();

	// 製品コードを退避
	sv_seihinCd = inputSeihin.value;

	// 製品コードが入力チェックは RGEN3420内
	// 引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		funClearRunMessage();
		return false;
	}


	// ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3420, xmlReqAry, xmlResAry, 1) == false) {
		funClearRunMessage();
		// 製品コードにフォーカスをセット（次に移ってしまう）
		inputSeihin.focus();
		return false;
	}

	//処理中ﾒｯｾｰｼﾞ非表示
	funClearRunMessage();

	//試作No一覧件数のﾁｪｯｸ
	if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
		// 試作Noのコンボボックスを作成
		funCreateComboBox(frm.ddlShisakuNo, xmlResAry[2], 5, 2);
	}else {
		// 試作Noが取得できない
		seihin_errmsg = "試作No";
	}

	//製品名、荷姿
	if (funXmlRead(xmlResAry[3], "flg_return", 0) == "true") {
		frm.seihinNm.value = funXmlRead(xmlResAry[3], "name_hinmei", 0);
		frm.nisugata.value = funXmlRead(xmlResAry[3], "name_nisugata", 0);
	} else {
		// 製品名が取得できない
		if (seihin_errmsg != "") seihin_errmsg += "・";
		seihin_errmsg += "製品名";
	}

	if (seihin_errmsg != ""){
		seihin_errmsg += "が取得できません。製品コードを再入力して下さい。";
		funErrorMsgBox(seihin_errmsg);
		// 製品コードにフォーカスを戻す（次に移ってしまう）
		inputSeihin.focus();
		return false;
	}

	// 製造工場（部署）：試作No.選択時にセット

	return true;

}

//========================================================================================
// 製造工場コンボボックス連動処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：なし
// 概要  ：製造工場に紐付く職場コンボボックスを生成する
//========================================================================================
function funChangeKojo() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	var XmlId = "RGEN3230";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3230");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3230I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3230O);

	// 資材情報一覧の初期化
	funClearList();

	//引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}

	//情報を取得
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3230, xmlReqAry, xmlResAry, 1) == false) {
		return false;
	}

	//職場ｺﾝﾎﾞﾎﾞｯｸｽの作成
	funCreateComboBox(frm.ddlShokuba, xmlResAry[2], 3 , 2);
	//ﾗｲﾝｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
	funClearSelect(frm.ddlLine, 2);

	//職場コンボボックスの退避
	xmlFGEN3230.load(xmlResAry[2]);

	return true;

}

//========================================================================================
// 職場コンボボックス連動処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：なし
// 概要  ：職場に紐付く製造ラインコンボボックスを生成する
//========================================================================================
function funChangeShokuba() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	var XmlId = "RGEN3240";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3240");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3240I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3240O);

	if (frm.ddlShokuba.selectedIndex == 0) {
		//ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
		funClearSelect(frm.ddlLine, 2);
		return true;
	}

	//引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}


	//情報を取得
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3240, xmlReqAry, xmlResAry, 1) == false) {
		return false;
	}

	//ｺﾝﾎﾞﾎﾞｯｸｽの作成
	funCreateComboBox(frm.ddlLine, xmlResAry[2], 4 , 2);

	return true;

}

//========================================================================================
// 試作Noコンボボックスフォーカス時の処理
// 作成者：E.Kitazawa
// 作成日：2014/11/25
// 引数  ：なし
// 概要  ：製品コード入力エラー時のフォーカス移動の対応
//         通常ＴＡＢ移動の時はそのままフォーカスを残す
//========================================================================================
function funFocusShisakuNo() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照

	// 2以上の時、値がセットされている（検索データなしの時、1つ目空白）
	if (frm.ddlShisakuNo.length < 2) {
		// 製品コード入力エラーの時、製品コードにフォーカスを移す
		if (seihin_errmsg != "") {
			// メッセージをクリアし、製品コードにフォーカス移動
			seihin_errmsg = "";
			frm.inputSeihinCd.focus();
		}
	}
}

//========================================================================================
// 試作Noコンボボックス
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：なし
// 概要  ：一覧初期化処理
//========================================================================================
function funChangeShisakuNo() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照

	// 資材情報一覧の初期化
	funClearList();

	if (frm.ddlShisakuNo.selectedIndex == 0) {
		// 製造工場（部署）のクリア
		frm.seizoKojoCd.value = "";
		frm.seizoKojoNm.value = "";
		// 職場、製造ラインクリア
		funClearSelect(frm.ddlShokuba, 2);
		funClearSelect(frm.ddlLine, 2);
		return true;
	}

	// 製造工場（部署）：試作No一覧の選択indexより
	frm.seizoKojoCd.value = funXmlRead(xmlFGEN3420O, "cd_busho", frm.ddlShisakuNo.selectedIndex - 1);
	// 部署マスタより対象部署名をセット
	frm.seizoKojoNm.value = funGetXmldata(xmlSA290O, "cd_busho", frm.seizoKojoCd.value, "nm_busho");

	// 職場コンボボックスを生成
	funChangeKojo();

}


//========================================================================================
// 検索ボタン押下処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：なし
// 概要  ：資材データの検索を行う
//========================================================================================
function funSearch() {
	var frm = document.frm00;    //ﾌｫｰﾑへの参照

	// 製品コードが変更されている場合、エラーメッセージ表示
	if (frm.inputSeihinCd.value != sv_seihinCd) {
		funErrorMsgBox(E000066);
//		funErrorMsgBox("製品コードが変更されています。\\n試作Noを検索して下さい。");
		frm.inputSeihinCd.focus();
		return false;
	}
	//処理中ﾒｯｾｰｼﾞ表示
	funShowRunMessage();
    //検索処理
    //処理中ウィンドウ表示の為、setTimeoutで処理予約
    setTimeout(function(){ funSearch0() }, 0);
}

//========================================================================================
//検索ボタン押下処理
//作成者：E.Kitazawa
//作成日：2014/09/24
//引数  ：なし
//概要  ：資材データの検索を行う
//========================================================================================
function funSearch0() {
	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	//ﾃﾞｰﾀ取得
    if (funDataSearch()) {
    	// 原料情報取得・表示
    	funGetGenryoInfo(1)
    } else {
		// 製品コードにフォーカスをセット
		frm.inputSeihinCd.focus();
    }

}

//========================================================================================
// 検索処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：なし
// 概要  ：資材データの検索を行う
//========================================================================================
function funDataSearch() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	var XmlId = "RGEN3450";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3450");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3450I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3450O);


	//選択行の初期化
	funSetCurrentRow("");

	//一覧のｸﾘｱ
	funClearList();

	//引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		funClearRunMessage();
		return false;
	}

	//検索条件に一致する試作ﾃﾞｰﾀを取得
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3450, xmlReqAry, xmlResAry, 1) == false) {
		//処理中ﾒｯｾｰｼﾞ非表示
		funClearRunMessage();
		return false;
	}

	//ﾃﾞｰﾀ件数のﾁｪｯｸ
	if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
		//表示
		tblList.style.display = "block";

		// 職場（1件目の値）
		var cd_shokuba = funXmlRead(xmlResAry[2], "cd_shokuba", 0);
		// 製造ライン（1件目の値）
		var cd_line = funXmlRead(xmlResAry[2], "cd_line", 0);

		// 設定されている時、職場コンボボックスのindexを設定
		if (cd_shokuba != "") {
			funSetIndex(frm.ddlShokuba, xmlFGEN3230O, "cd_shokuba", cd_shokuba);
			// 製造ラインコンボボックスを設定
			funChangeShokuba();

			// 設定されている時、職場コンボボックスのindexを設定
			if (cd_line != "") {
				funSetIndex(frm.ddlLine, xmlFGEN3240O, "cd_line", cd_line);
			}
		} else {
			// 職場コンボボックス再設定：空白選択
			funCreateComboBox(frm.ddlShokuba, xmlFGEN3230, 3 , 2);
			// 製造ラインクリア
			funClearSelect(frm.ddlLine, 2);
		}

		// 資材情報一覧の作成
		funCreateSizaiTableList(xmlResAry[2]);

	} else {
		//非表示
		tblList.style.display = "none";

	}

	//処理中ﾒｯｾｰｼﾞ非表示
	funClearRunMessage();

	return true;

}


//========================================================================================
// 資材情報　テーブル一覧作成処理
// 作成者：E.Kitazawa
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

		// 発注先コードの取得
		var cdHattyusaki = funXmlRead(xmlData, "cd_hattyusaki", i);
		// 発注先コンボボックス
		var ddlHattyusaki = document.getElementById("ddlHattyusaki-" + i);
    	// 発注先コンボボックス設定
        funCreateComboBox(ddlHattyusaki, xmlFGEN3310O, 2, 2);

		// 対象資材名の取得
		var cdTaisyoShizai = funXmlRead(xmlData, "cd_taisyoshizai", i);
		// 対象資材コンボボックス
		var ddlShizai = document.getElementById("ddlShizai-" + i);
    	// 対象資材コンボボックス設定
        funCreateComboBox(ddlShizai, xmlFGEN3200O, 1, 2);

        // 新規チェックがオフの時、非活性
        ddlHattyusaki.disabled = true;
        ddlShizai.disabled = true;

		// 発注先選択Indexの設定
		funSetIndex(ddlHattyusaki, xmlFGEN3310O, "cd_hattyusaki", cdHattyusaki);
		// 対象資材選択Indexの設定（リテラルマスタ）
		funSetIndex(ddlShizai, xmlFGEN3200O, "cd_literal", cdTaisyoShizai);
	}
}
//========================================================================================
// 資材情報 明細行作成処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：①row     ：行番号 （0 ～ ）
//       ：②xmlData ：XMLデータ
// 概要  ：デザインスペース情報一覧の行追加処理
//========================================================================================
function funAddSizaiTable(row, xmlData) {

	var detail = document.getElementById("detail");

	var html;
	// 完了チェック
	var flgKanryo = funXmlRead(xmlData, "chk_kanryo", row);
	// 手配ステータス
	var flgTtehaiStatus = funXmlRead(xmlData, "flg_tehai_status", row);
	// 行設定
	var tr = document.createElement("tr");
	tr.setAttribute("class", "disprow");
	tr.setAttribute("id", "tr" + row);

	var td1 = document.createElement("td");
	td1.setAttribute("class", "column");
	// 試作No. seq
	html = "<input type=\"hidden\" name=\"seq_shizai-" + row + "\" id=\"seq_shizai-" + row + "\"  value=\"";
	html += funXmlRead(xmlData, "seq_shizai", row) + "\" >";
	// 入力ステータスの表示 START 2017.02.09 Makoto Takada
	html += "<input type=\"hidden\" name=\"flg_tehai_status_org-" + row + "\" id=\"flg_tehai_status_org-" + row + "\"  value=\"";
	html += flgTtehaiStatus + "\" >";
	// 入力ステータスの表示 END 2017.02.09 Makoto Takada
	// 資材コード（退避用）
	html += "<input type=\"hidden\" name=\"cd_shizai-" + row + "\" id=\"cd_shizai-" + row + "\"  value=\"";
	html += funXmlRead(xmlData, "cd_shizai", row) + "\" >";
	// 空行の対応
	var cd_sizai =funXmlRead(xmlData, "cd_shizai", row);
	if (cd_sizai == "") {
		html += "&nbsp;";
	} else {
		// 資材コード 前ゼロ付加（表示用）
		html += ("000000" + cd_sizai).substr(funXmlRead(xmlData, "cd_shizai", row).length);
	}
	td1.innerHTML = html;
	td1.style.textAlign = "left";
	td1.style.width = 50;//80 -58
	tr.appendChild(td1);

	// 資材名
	var td2 = document.createElement("td");
	td2.setAttribute("class", "column");
	td2.innerHTML = funXmlRead(xmlData, "nm_shizai", row);
	if (td2.innerHTML == "") {
		td2.innerHTML = "&nbsp;";
	}
	td2.style.textAlign = "left";
	td2.style.width = 168;
	tr.appendChild(td2);

	// 単価
	var td3 = document.createElement("td");
	td3.setAttribute("class", "column");
	td3.innerHTML = funXmlRead(xmlData, "tanka", row);
	if (td3.innerHTML == "") {
		td3.innerHTML = "&nbsp;";
	}
	td3.style.textAlign = "right";
	td3.style.width = 55;//70 -50
	tr.appendChild(td3);

	// 新規チェックボックス
	var td4 = document.createElement("td");
	td4.setAttribute("class", "column");
	html = "<input type=\"checkbox\" name=\"chkNew\" value=\"\" onClick=\"funClickChkNew(" + row + ")\" style=\width:45px;\”";
	// 手配ステータス=3（手配済み）の時、非活性
	if (flgTtehaiStatus == "3") {
		html += "checked disabled";
	}
	html += " >";
	td4.innerHTML = html;
	td4.style.textAlign = "center";
	td4.style.width = 45;//43
	tr.appendChild(td4);

	// 新資材コード（readonly → 新規onで許可）
	var td5 = document.createElement("td");
	td5.setAttribute("class", "column");
	html = "<span class=\"ninput\" format=\"6,0\" comma=\"false\">";
	html += "<input type=\"text\"  name=\"inputNewShizaiCd-" + row + "\" id=\"inputNewShizaiCd-" + row + "\"  value=\"";
	//  登録されている時、前ゼロ付加
	var cd_shizai_new = funXmlRead(xmlData, "cd_shizai_new", row);
	if (cd_shizai_new != "") {
		cd_shizai_new = "000000" + cd_shizai_new;
		html += cd_shizai_new.substr(funXmlRead(xmlData, "cd_shizai_new", row).length);
	}
	html += "\" onChange=\"funChangeNewShizaiCd(" + row + ")\"";
	// 空にした時、onChange イベントが発生しない為
//	html += " onkeydown=\"if(event.keyCode == 13 || event.keyCode == 9){funChangeNewShizaiCd(" + row + ")} \"";
//	html += " size=\"17\" style=\"ime-mode:disabled;\" onBlur=\"funChangeNewShizaiCd(" + row + "); funBuryZero(this, 6);\" readonly>";
	html += " size=\"8\" style=\"ime-mode:disabled;\" onBlur=\"funBuryZero(this, 6);\" style=\width:60px;\”readonly>";
	html += "</span>";
	td5.innerHTML = html;
	td5.style.textAlign = "left";
	td5.style.width = 60;//100 - 60
	tr.appendChild(td5);

	// 新資材名
	var td6 = document.createElement("td");
	td6.setAttribute("class", "column");
	html = "<input type=\"text\"  name=\"inputNewShizaiNm-" + row + "\" id=\"inputNewShizaiNm-" + row + "\" onChange=\"funKanryoChk(" + row + ")\"  value=\"";
	html += funXmlRead(xmlData, "nm_shizai_new", row);
	html += "\"  style=\"width:228px;\" readonly>";
//	html += "\"  style=\"width:232px;\" tabindex=\"-1\" readonly>";
	td6.innerHTML = html;
	td6.style.textAlign = "left";
	td6.style.width = 229;
	tr.appendChild(td6);

	// 発注先（セレクトボックス）
	var td7 = document.createElement("td");
	td7.setAttribute("class", "column");
	html = "<select name=\"ddlHattyusaki-" + row + "\" id=\"ddlHattyusaki-" + row + "\" style=\"width:233px;\" onChange=\"funChangeHattyusaki(" + row + ")\" onFocus=\"funSetHattyusaki(" + row + ")\">";
	html += "</select>";
	html += "<input type=\"hidden\" name=\"flgHattyusaki-" + row + "\" id=\"flgHattyusaki-" + row + "\"  value=\"\" >";
	td7.innerHTML = html;
	td7.style.textAlign="left";
	td7.style.width = 236;
	tr.appendChild(td7);

	// 対象資材（セレクトボックス）
	var td8 = document.createElement("td");
	td8.setAttribute("class", "column");
	html = "<select name=\"ddlShizai-" + row + "\" id=\"ddlShizai-" + row + "\" style=\"width:144px;\" onChange=\"funChangeTaisyoShizai(" + row + ")\" >";
	html += "</select>";
	td8.innerHTML = html;
	td8.style.textAlign="left";
	td8.style.width = 144;
	tr.appendChild(td8);

	var td9 = document.createElement("td");
	td9.setAttribute("class", "column");
	// 手配ステータス
	html = "<input type=\"hidden\" name=\"flg_tehai_status-" + row + "\" id=\"flg_tehai_status-" + row + "\"  value=\"";
	html += flgTtehaiStatus + "\" >";
	// 完了チェックボックス
	html += "<input type=\"checkbox\" name=\"chkKanryo\" value=\"\" style=\"width:43px;\" onClick=\"funClickKanryo(" + row + ")\"";
	// 完了チェック
	if (flgKanryo == "1") {
		html += " checked";
	}
	// 手配ステータス=3（手配済み）の時、非活性
	if (flgTtehaiStatus == "3") {
		html += " disabled";
	}
	html += " >";
	td9.innerHTML = html;
	td9.style.textAlign = "center";
	td9.style.width = 45;
	tr.appendChild(td9);

	// 資材手配テーブルの更新日時
	var td12 = document.createElement("td");
	td12.setAttribute("class", "column");
	html = "<input type=\"hidden\" name=\"dt_tehai_koshin-" + row + "\" id=\"dt_tehai_koshin-" + row + "\"  value=\"";
	html += funXmlRead(xmlData, "dt_tehai_koshin", row) + "\" >";
	td12.innerHTML =  html;
	tr.appendChild(td12);

	// May Thu 【KPX@1602367】 add start 2016/09/05
	// ファイル名 版下ｱｯﾌﾟﾛｰﾄﾞ
    var td13 = document.createElement("td");
    td13.setAttribute("class", "column");
    // 保存されているファイル名（非表示）
    html = "<input type=\"hidden\" id=\"nm_file_henshita-" + row + "\" value=\"";
	html += funXmlRead(xmlData, "nm_file_henshita", row);
    html += "\" tabindex=\"-1\">";
    // 参照ボタンのinput を表示用ファイル名で隠す
    html += "<div style=\"position: relative;\">";
   // 参照ボタン（onChangeイベントだけでは表示ファイル名をクリアして同じファイルを選択した時セットされないのでonclickイベントで表示ファイル名にセット）
//    html += "<input type=\"file\" class=\"normalbutton\" value=\"\" style=\"width:380px;\" name=\"fileName-" + row + "\" id=\"fileName-" + row + "\"";
    html += "<input type=\"file\" class=\"normalbutton\" value=\"";
    html += funXmlRead(xmlData, "file_path_henshita", row);
    html += "\" style=\"width:270px;\" name=\"fileName-" + row + "\" id=\"fileName-" + row + "\"";
    html += "onChange=\"funChangeFile(" + row + ")\" onclick=\"funSetInput(" + row + ")\" ";
    // 参照ボタンENTERキーでダイアログを開く
    html += "onkeydown=\"funEnterFile(" + row + ", event.keyCode);\" >";
    // 表示用ファイル名（スクロールしてもタイトルの下に表示）
    html += "<span style=\"position: absolute; top: 0px; left: 0px; z-index:1;\">";
    html += "<input type=\"text\" value=\"";
    html += funXmlRead(xmlData, "nm_file_henshita", row);
    // 表示用ファイル名：タブ移動を無効とする89 → 59 50
    html += "\" name=\"inputName-" + row + "\" id=\"inputName-" + row + "\" size=\"37\" style=\"width:210px;\" readonly tabindex=\"-1\" >";
    html += "</span>";
    html += "</div>";
    td13.innerHTML = html;
    td13.style.textAlign="left";
    td13.style.width = 270;//383 363
//    td13.colspan = 2;
    tr.appendChild(td13);

	// 発注
	var td14 = document.createElement("td");
	td14.setAttribute("class", "column");
	td14.setAttribute("class", "column");
	html = "<input type=\"button\" name=\"btnhattyu\" value=\"発注\"  style=\"width:44px;\" onClick=\"funClickBtnHattyu(" + row + ")\"";
	html += " >";
	td14.innerHTML = html;
	td14.style.textAlign = "center";
	td14.style.width = 45;
	tr.appendChild(td14);

	// 発注者
	var td15 = document.createElement("td");
	td15.setAttribute("class", "column");
	td15.innerHTML = funXmlRead(xmlData, "nm_hattyu", row);
	if (td15.innerHTML == "") {
		td15.innerHTML = "&nbsp;";
	}
	td15.style.textAlign = "center";
	td15.style.width = 90;//157 - 127
	tr.appendChild(td15);

	// 発注日時 →　発注日
	var td16 = document.createElement("td");
	td16.setAttribute("class", "column");
//	var dthattyu = funXmlRead(xmlData, "dt_hattyu", row);
//	var dthattyu = "2007-08-16 10:30:15";
//	td16.innerHTML = funGetDateString(dthattyu);
	td16.innerHTML = funXmlRead(xmlData, "dt_hattyu", row);
	if (td16.innerHTML == "") {
		td16.innerHTML = "&nbsp;";
	}
	td16.style.textAlign = "left";
	td16.style.width = 75;//90 - 80
	tr.appendChild(td16);
    // May Thu 【KPX@1602367】 add end 2016/09/05

	// 更新者
	var td10 = document.createElement("td");
	td10.setAttribute("class", "column");
	td10.innerHTML = funXmlRead(xmlData, "nm_koshin", row);
	if (td10.innerHTML == "") {
		td10.innerHTML = "&nbsp;";
	}
	td10.style.textAlign = "center";
	td10.style.width = 90;//157 127
	tr.appendChild(td10);

	// 更新日時
	var td11 = document.createElement("td");
	td11.setAttribute("class", "column");
//	var dtkoushin = funXmlRead(xmlData, "dt_koshin", row);
//	td11.innerHTML = funGetDateString(dtkoushin);
	td11.innerHTML = funXmlRead(xmlData, "dt_koshin", row);
	if (td11.innerHTML == "") {
		td11.innerHTML = "&nbsp;";
	}
	td11.style.textAlign = "left";
	td11.style.width = 75;//90 - 77
	tr.appendChild(td11);

	detail.appendChild(tr);
}
//========================================================================================
// 日時から日付(10文字）を切り出す。
// 作成者：May Thu
// 作成日：2016/09/05
// 引数  ：文字  ：頭10文字数
// 概要  ：
//========================================================================================
function funGetDateString(str) {
   if(str == null || str == "") {
      return "";
   }
   return str.substr(0,10);
}
//*** 資材情報：明細行内処理 ***//
//========================================================================================
// 発注ボタン押下処理
// 作成者：May Thu
// 作成日：2016/09/05
// 引数  ：row   ：行番号
// 概要  ：資材手配依頼書出力画面へ遷移する
//========================================================================================
function funClickBtnHattyu(row) {

	// 新資材コード、新資材名、発注先、対象資材すべてが設定されていない時、「資材手配依頼書出力画面」へ遷移不可とする
	if (!funNyuryokuChk(row, 1)) {
		funErrorMsgBox(E000061);
		return false;
	}

    var frm = document.frm00;        //ﾌｫｰﾑへの参照
    //START 2017.02.09 Makoto Takada 資材手配依頼出力画面に遷移しても登録できないものは、遷移させない形に変更
    var flg_tehai_status_org = document.getElementById("flg_tehai_status_org-" + row).value;
    if (funTorokuStatusChk(row)) {
        funErrorMsgBox(E000067);
        return false;
    }
    //END 2017.02.09 Makoto Takada 資材手配依頼出力画面に遷移しても登録できないものは、遷移させない形に変更
    //Drop down list の値を出したい場合
    var taishoshizai    = document.getElementById("ddlShizai-" + row); //対象資材
    var hattyusaki    = document.getElementById("ddlHattyusaki-" + row); //発注先
    var shisakuNo      = frm.ddlShisakuNo.value; //試作コード
    var flg_hatyuu_status = frm.flg_hatyuu_status.value; //flag
    var seizoKojoCd       = frm.seizoKojoCd.value;    //製造工場コード 入力すると、出る、ないと、空
    var seizoKojoNm       = frm.seizoKojoNm.value;    //製造工場名　入力すると、出る、ないと、空
    var cd_taishoshizai = taishoshizai.options[taishoshizai.selectedIndex].value;    //対象資材コード
    var nm_taishoshizai = taishoshizai.options[taishoshizai.selectedIndex].text;    //対象資材名
    var cd_hattyusaki = hattyusaki.options[hattyusaki.selectedIndex].value; //発注先コード
    var nm_hattyusaki = hattyusaki.options[hattyusaki.selectedIndex].text; //発注先名
    var seihinCd      = frm.inputSeihinCd.value;    //製品コード
    var cd_shizai     = document.getElementById("cd_shizai-" + row).value;    //資材コード
    var nm_shizai     = funXmlRead(xmlFGEN3450O, "nm_shizai", row);    //資材名
    var cd_shizai_new = document.getElementById("inputNewShizaiCd-" + row).value;    // 新資材コード
    var nm_shizai_new = document.getElementById("inputNewShizaiNm-" + row).value;    // 新資材名
    var cd_shain = shisakuNo.substring(0,10);    //cd_shain
    var nen = shisakuNo.substring(11,13);        //nen
    var no_oi = shisakuNo.substring(14,17);      //no_oi
    var no_eda = shisakuNo.substring(18,21);     //no_eda
    var seq_shizai = document.getElementById("seq_shizai-" + row).value; //
    var seihinNm = frm.seihinNm.value;
    var nisugata = frm.nisugata.value ;
    var wUrl;
    //画面移動とパラメータ
        wUrl = "../SQ250ShizaiTehaiOutput/ShizaiTehaiOutput.jsp"+ "?" + "flg_hatyuu_status=" + flg_hatyuu_status
                                                                + "&" + "seizoKojoCd=" + seizoKojoCd
                                                                + "&" + "seizoKojoNm=" + seizoKojoNm
                                                                + "&" + "cd_taishoshizai=" + cd_taishoshizai
                                                                + "&" + "nm_taishoshizai=" + nm_taishoshizai
                                                                + "&" + "cd_hattyusaki=" + cd_hattyusaki
                                                                + "&" + "nm_hattyusaki=" + nm_hattyusaki
                                                                + "&" + "seihinCd=" + seihinCd
                                                                + "&" + "cd_shizai=" + cd_shizai
                                                                + "&" + "nm_shizai=" + nm_shizai
                                                                + "&" + "cd_shizai_new =" + cd_shizai_new
                                                                + "&" + "nm_shizai_new =" + nm_shizai_new
                                                                + "&" + "cd_shain=" + cd_shain
                                                                + "&" + "nen=" + nen
                                                                + "&" + "no_oi=" + no_oi
                                                                + "&" + "no_eda=" + no_eda
                                                                + "&" + "seihinNm=" + seihinNm
                                                                + "&" + "nisugata=" + nisugata
                                                                + "&" + "seq_shizai=" + seq_shizai;
        //遷移
//        var win = window.open(wUrl,"gensi","menubar=no,resizable=yes");
        var width, height;
      var win = funOpenModalDialog(wUrl,"gensi","dialogHeight:800px;dialogWidth:1600px;menubar=no,resizable=yes");
//      	    , this
//			, "");

      if (win != null && win != "") {
    	 var returnParam = win.split(":::");

	      if(returnParam[0] == 2 || returnParam[0] == 3) {
	    	  // hattyuusakiko-do
	    	  if (cd_hattyusaki != returnParam[1]) {

	    		  document.getElementById("ddlHattyusaki-" + row).value = Number(returnParam[1]);
	    		  hattyusaki.onchange();

	    	  }

	    	  if (taishoshizai != returnParam[2]) {
				  document.getElementById("ddlShizai-" + row).value = ( '000' + returnParam[2] ).slice( -3 );
				  document.getElementById("ddlShizai-" + row).onchange();
	    	  }
	      }
      }
	  // 「仮保存」または、「発信」処理後の場合
	  // 登録処理を実行する
	  if (!funToroku()) {
		  // 登録しなかったら再検索
		  funSearch();
	  }


        // 再表示の為にフォーカスにする
        //win.focus();


    return true;

}

//*** 資材情報：明細行内処理 ***//
//========================================================================================
// 新規チェック
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：row   ：行番号
// 概要  ：新規チェック時、新資材コードの編集状態を変更する
//========================================================================================
function funClickChkNew(row) {
	var frm = document.frm00;        //ﾌｫｰﾑへの参照
	var chkNew = frm.chkNew;         // 新規チェックボックス
	var chkKanryo = frm.chkKanryo;   // 完了チェックボックス

	// レコードが１件の時
	if (!chkNew.length) {
		// 指定行の新資材コードの編集状態を変更
		funShizaiNewHenko(row, chkNew, chkKanryo);
	// チェックされた行の設定
	} else {
		// 指定行の新資材コードの編集状態を変更
		funShizaiNewHenko(row, chkNew[row], chkKanryo[row]);
	}

}

//========================================================================================
// 新規チェック処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：row      ：行番号
//       ：chkobj   ：新規チェックオブジェクト
//       ：chkKanryo：完了チェックオブジェクト
// 概要  ：新資材コードの編集状態を変更
//========================================================================================
function funShizaiNewHenko(row, chkobj, chkKanryo) {

	// 新資材コード
	var shizaiCd = document.getElementById("inputNewShizaiCd-" + row);
	// 新資材名
	var shizaiNm = document.getElementById("inputNewShizaiNm-" + row);
	// 発注先コンボボックス
	var ddlHattyusaki = document.getElementById("ddlHattyusaki-" + row);
	// 対象資材コンボボックス
	var ddlShizai = document.getElementById("ddlShizai-" + row);

	// 新規チェックの状態
	if(chkobj.checked == true) {
		// 新規チェックがon の時、編集可
		shizaiCd.readOnly = false;
		shizaiCd.style.backgroundColor = "#ffff88";
		shizaiCd.focus();
		shizaiCd.click();

		// 新規チェックがオンの時、活性化
		ddlHattyusaki.disabled = false;
		ddlShizai.disabled = false;

	} else {
		// 新資材コード、発注先、対象資材のすべてが空白の時（確認不要）
		if (!funNyuryokuChk(row, 2)) {
			shizaiCd.readOnly = true;
			shizaiCd.style.backgroundColor = "#ffffff";
			// 新規チェックがオフの時、非活性
			ddlHattyusaki.disabled = true;
			ddlShizai.disabled = true;

		//新資材コード、発注先、対象資材が設定されている時（どれか一つ）
		}else {
			// 編集不可にするか確認
			if (funConfMsgBox("新資材コード、又は発注先・対象資材が設定されています。\nチェックをはずしてクリアしますか？") == ConBtnYes) {
				// 新規チェックがoff の時、編集不可
				shizaiCd.readOnly = true;
				shizaiCd.style.backgroundColor = "#ffffff";
				// 新資材コードをクリア
				shizaiCd.value = "";
				// 新資材名をクリア
				shizaiNm.value = "";

				// 新規チェックがオフの時、非活性（indexもクリアにする）
				ddlHattyusaki.disabled = true;
				ddlShizai.disabled = true;
				// indexもクリア
				ddlHattyusaki.selectedIndex = 0;
				ddlShizai.selectedIndex = 0;
				// 完了チェックを外す
				if(chkKanryo.checked == true) {
					chkKanryo.checked = false;
				}

			} else {
				// 新規チェックをonに変更
				chkobj.checked = true;
			}

		}
	}

}

//========================================================================================
// 新資材コード変更
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：row   ：行番号
// 概要  ：資材テーブルより資材名を取得する
//========================================================================================
function funChangeNewShizaiCd(row) {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	var XmlId = "RGEN3440";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3440");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3440I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3440O);

	// 新資材コード
	var shizaiCd = document.getElementById("inputNewShizaiCd-" + row);

	// 新資材名
	var shizaiNm = document.getElementById("inputNewShizaiNm-" + row);
	// 発注先
	var hattyusaki = document.getElementById("ddlHattyusaki-" + row);

	// リターンキーが押下されても編集不可の状態では何もしない
	if (shizaiCd.readOnly) {
		return false;
	}

	// 新資材名をクリア
	shizaiNm.value = "";
	// 新資材コードが空白の時
	if(shizaiCd.value == ""){
		// 完了チェックを外す
		funKanryoChk(row);
		return false;
	}

	// 引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}

	// 情報を取得
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3440, xmlReqAry, xmlResAry, 1) == false) {
		return false;
	}

	// ﾃﾞｰﾀ件数のﾁｪｯｸ
	if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
		// 新資材名
		shizaiNm.value = funXmlRead(xmlResAry[2], "nm_shizai", 0);
		// 完了チェック
		funKanryoChk(row);
//【KPX@1602367】 add start
		hattyusaki.focus();	// 発注先にフォーカスする
		shizaiNm.readOnly = true; // 入力不可にする
//【KPX@1602367】 add end
	} else {

		// 資材コード検索エラー
		funErrorMsgBox("資材コードが存在しません。資材名を手入力してください。");
//【KPX@1602367】 add start
		// 完了チェックを外す
		funKanryoChk(row);

		shizaiNm.readOnly = false; // 入力可にする
		// 新資材名にフォーカスする
		shizaiNm.focus();
//【KPX@1602367】 add end
	}


}


//========================================================================================
// 完了チェック
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：row   ：行番号
// 概要  ：完了チェック時の処理
//========================================================================================
function funClickKanryo(row) {

	var frm = document.frm00;          //ﾌｫｰﾑへの参照
	var chkKanryo = null;              // 完了チェックボックス

	// Indexの確認
	if (!frm.chkKanryo.length) {
		// レコードが１件の時
		chkKanryo = frm.chkKanryo;
	} else {
		// 指定行のチェックボックス
		chkKanryo = frm.chkKanryo[row];
	}

	if (chkKanryo.checked == true) {
		// 新資材コード、発注先、対象資材すべてが設定されていない時、チェックできない
		if (!funNyuryokuChk(row, 1)) {
			funErrorMsgBox(E000065);
//			funErrorMsgBox("入力項目が設定されていないため、完了チェックをＯＮにできません。");
			chkKanryo.checked = false;
		}
	}
}


//========================================================================================
// 完了チェック
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：row   ：行番号
// 概要  ：新規チェックOn、新資材コード、発注先、対象資材が設定されている時、
//         完了チェックをOnにする
//========================================================================================
function funKanryoChk(row) {
	var frm = document.frm00;     //ﾌｫｰﾑへの参照

	var chkNew = null;            // 新規チェックボックス
	var chkKanryo = null;         // 完了チェックボックス

	// Indexの確認
	if (!frm.chkNew.length) {
		// レコードが１件の時
		chkNew = frm.chkNew;
		chkKanryo = frm.chkKanryo;
	} else {
		// 指定行のチェックボックス
		chkNew = frm.chkNew[row];
		chkKanryo = frm.chkKanryo[row];
	}

	// 新資材コード、発注先、対象資材が設定されているか？
	if (!funNyuryokuChk(row, 1)) {
		// 未設定の項目があれば完了チェックをはずす
		if(chkKanryo.checked == true && chkKanryo.disabled == false) {
			// 完了チェックをOffに変更
			chkKanryo.checked = false;
		}
		return;
	}

	// 新規チェックがonの時
	if(chkNew.checked == true) {
		// 完了チェックをOnに変更
		chkKanryo.checked = true;
	}
	return;
}

//========================================================================================
// 入力チェック
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：row   ：行番号
//       ：mode  ：1 or 2
// 戻り値：true  ：mode==1:すべて設定されている時
//                 mode==2：一つでも設定されている時
//         false ：未入力あり
// 概要  ：新資材コード、発注先、対象資材が設定されているか？
//========================================================================================
function funNyuryokuChk(row, mode) {

	// 新資材コード
	var shizaiCd = document.getElementById("inputNewShizaiCd-" + row);
	// 新資材名
	var shizaiNm = document.getElementById("inputNewShizaiNm-" + row);
	// 発注先コンボボックス
	var selHattyusaki = document.getElementById("ddlHattyusaki-" + row);
	// 対象資材コンボボックス
	var selShzai = document.getElementById("ddlShizai-" + row);

	// すべて設定されている時、true
	if (mode == 1) {
		// 新資材コード、新資材名、発注先、対象資材のどれかが未設定
		if ((shizaiCd.value == "") || (shizaiNm.value == "") ||  (selHattyusaki.selectedIndex < 1) || (selShzai.selectedIndex < 1)) {
			return false;
		}
	} else {
		// 新資材コードが空白、発注先未選択、対象資材未選択
		if ((shizaiCd.value == "") &&  (selHattyusaki.selectedIndex < 1) && (selShzai.selectedIndex < 1)) {
			return false;
		}
	}

	return true;
}



//========================================================================================
// 発注先コンボボックス変更
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：row   ：行番号
// 概要  ：発注先コンボボックス変更時の処理
//========================================================================================
function funChangeHattyusaki(row) {

	// 完了チェック
	funKanryoChk(row);

}


//========================================================================================
// 対象資材コンボボックス変更
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：row   ：行番号
// 概要  ：対象資材コンボボックス変更時の処理
//========================================================================================
function funChangeTaisyoShizai(row) {

	// 完了チェック
	funKanryoChk(row);

}
//*** 明細行内処理 終わり ***//


//========================================================================================
// 登録押下処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：なし
// 概要  ：資材テーブル更新、資材手配テーブル登録
//========================================================================================
function funToroku() {

	var frm = document.frm00;          //ﾌｫｰﾑへの参照
	var chkNew = frm.chkNew;           // 新規チェックボックス
	var chkKanryo = frm.chkKanryo;     // 完了チェックボックス
	var retBool = false;
	var retUpload = false;


	// 製品コードが変更されている場合、エラーメッセージ表示
	if (frm.inputSeihinCd.value != sv_seihinCd) {
		funErrorMsgBox("製品コードが変更されているので、登録できません。");
		frm.inputSeihinCd.focus();
		return false;
	}
	if(funTorokuInputCheck()) {
		return false;
	}


	// 登録確認
	if (funConfMsgBox(I000002) != ConBtnYes) {
		return false;
	}

	// 明細行（読み込み分）
	var detail = document.getElementById("detail");

	// 明細データ
	if (!detail.firstChild){
		// 資材情報がない時
		retBool = funTorokuChk(-1, null, null);

	// レコードが１件の時
	} else if (!chkNew.length) {
		// チェックボックスを渡し、登録（資材テーブル更新、手配テーブル登録or削除）
		retBool = funTorokuChk(0, chkNew, chkKanryo);

	} else {
		// チェックされた行の設定
		for(var i = 0; i < chkNew.length; i++){
			// チェックボックスを渡し、登録（資材テーブル更新、手配テーブル登録or削除）
			retBool = funTorokuChk(i, chkNew[i], chkKanryo[i]);
			if (!retBool) break;
		}
	}
	//ファイルアップロードを先に実施
	retUpload = funfileUpload();

	// 完了メッセージ
    if (retBool || retUpload ) {
// ユーザーレビューでの指摘（発信処理をすばやく行うため不要なメッセージを削除） Maythu
//    	// 登録しました。
//    	funInfoMsgBox("最新のデータを検索します");
    	// 再検索実行
    	funSearch();
    }

	return true;

}
//========================================================================================
// 検索条件の再設定処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：なし
// 戻り値：全条件を選択していない時：""
//         選択時：選択条件をつなげたコード文字列
// 概要  ：検索条件をチェックし、条件コードをつなげた文字列を返す
//========================================================================================
function funSearchConditionSet(resultAray) {

	var frm = document.frm00;		//ﾌｫｰﾑへの参照
	var sisakuNo            = funXmlRead(resultAray, "sisakuNo" , 0);
	frm.inputSeihinCd.value = funXmlRead(resultAray, "cd_seihin", 0);
	funSeihinSearch();
	frm.ddlShisakuNo.value =  sisakuNo;
	funChangeShisakuNo();
	return true;
}

//========================================================================================
// 検索条件のチェック処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：なし
// 戻り値：全条件を選択していない時：""
//         選択時：選択条件をつなげたコード文字列
// 概要  ：検索条件をチェックし、条件コードをつなげた文字列を返す
//========================================================================================
function funSelChk() {

	var frm = document.frm00;		//ﾌｫｰﾑへの参照
	var strRet = cd_kaisha + "_"	//戻り値（会社コードをセット）

	// 製造工場
	if (frm.seizoKojoCd.value == "") {
		funSelChk.Msg = "製造工場が選択されていません。";
		return "";
	}
	strRet += frm.seizoKojoCd.value + "_";

	// 試作NO
	if (frm.ddlShisakuNo.options[frm.ddlShisakuNo.selectedIndex].value == "") {
		funSelChk.Msg = "試作NOが選択されていません。";
		return "";
	}
	strRet += frm.ddlShisakuNo.options[frm.ddlShisakuNo.selectedIndex].value + "_";

	return strRet;
}

//========================================================================================
// アップロード処理
// 作成者：May Thu
// 作成日：2016/09/30
// 引数  ：なし
// 概要  ：版下アップロード
//========================================================================================
function funfileUpload() {
    var frm = document.frm00;   //ﾌｫｰﾑへの参照
    var upFildNm = "";          //アップロードするフィールド名
    var delFileNm = "";         //削除するファイル名
    var lstSyurui = {};         //登録する種類の配列（重複チェック用）
    var strMsg = ""				//確認メッセージ付加文字

    // 保存ファイルのサブフォルダー取得（工場）
    var subFolder = funSelChk();
    // 条件が選択されていない場合
	if (subFolder == "") {
		funErrorMsgBox(funSelChk.Msg);
		return false;
	}

	// 選択した種類コードを_で繋ぐ為、ファイル毎に設定
	var subFlst = "";
	// 表示行が無い場合、エラー
	var displayln = false;

	// 一覧データがない
	if (rowcnt == 0) {
    	funErrorMsgBox(E000030);
        return false;
	}

	// 表示されている一覧をチェック
	// 参照ファイル、表示用inputが空白でないこと
    for(var i = 0; i < rowcnt; i++){
        // 行オブジェクト
    	var tr = document.getElementById("tr" + i);

    	// アップロードするファイル名
    	var fileName = document.getElementById("fileName-" + i);

    	// 表示ファイル名
    	var inputName = document.getElementById("inputName-" + i);
    	// 保存されているファイル名（非表示）
    	var nm_file_henshita = document.getElementById("nm_file_henshita-" + i);

    	var cd_shizai = document.getElementById("cd_shizai-" + i);

    	// 表示行のみ処理する
    	if (tr.style.display != "none") {
    		// 表示行
    		displayln = true;

    		// 表示用inputファイル名が空白の場合、処理を飛ばすエラー（ＤＢ登録できない）
    		if (inputName.value == "") {
    		    continue;
    		}
    		// ファイル名の長さチェック（ＤＢ制限）
			if (inputName.value.length > MAXLEN_FILENM) {
				funErrorMsgBox(E000062 + MAXLEN_FILENM + E000063);
//				funErrorMsgBox("ファイル名が長すぎます。（" + MAXLEN_FILENM + "）文字：\\n" + inputName.value);
				return false;
			}
			// ファイル名の不正文字チェック
			if (!funChkFileNm(inputName.value)) {
				funErrorMsgBox(E000066 + inputName.value);
//    			funErrorMsgBox("ファイル名に不正文字が含まれています。：\\n" + inputName.value);
    			return false;
			}
    		// クリアボタンが押下されていないこと
    		if ((fileName.value != "") && (inputName.value != "") && (inputName.style.color == "red")) {
    			// アップロード処理：サーバー渡し（":::"で区切る）
    			// フィールド名（アップロードチェック用）
    			upFildNm += "fileName-" + i + ":::";
    			// サブフォルダーに種類コードを追加
    			subFlst += subFolder  + cd_shizai.value + ":::";
    		}
    		// 変更前ファイルを削除
    		// ＤＢに保存されている 且つ 変更された赤字ファイルの時、
    		if ( (nm_file_henshita.value != "") && (inputName.style.color == "red")) {
    			// サブフォルダー名を付加する。（":::"で区切る）
    			// nm_file_henshita には保存データの種類コードが付加済（"\\"で区切られている）
    			delFileNm += subFolder + cd_shizai.value + "\\" + nm_file_henshita.value + ":::";
    		}
    	}
    }
    // 表示行がない
	if (!displayln) {
    	funErrorMsgBox(E000030);
        return false;
	}
    if (upFildNm == "") {
        return false;
    }
	// 排他処理：検索時の更新日時から変更があったら処理中止
	if (!funKoshinChk()) {
		return false;
	}
	//** ﾌｧｲﾙﾊﾟｽの退避 **/
    // アップロードパス（const定義名）
    frm.strServerConst.value = "UPLOAD_HANSITA_FOLDER";
    // アップロードファイルのフィールド名（ ":::"で区切る）
    frm.strFieldNm.value = upFildNm;
    // アップロードファイルのサブフォルダー（ファイル単位に ":::"で区切る）
    frm.strSubFolder.value = subFlst;
    // 削除ファイルパス（":::"で区切る）
    frm.strFilePath.value = delFileNm;
    // ファイルアップロードサーブレットの実行
    if (upFildNm != "") {
    	// ファイルアップロードサーブレットの実行
    	frm.action="/" + ConUrlPath + "/FileUpLoadExec";
    	frm.submit();
    }
    return true;
}
//========================================================================================
// 行の登録チェック
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：なし
// 概要  ：指定行の行データをxmlに保存する
//========================================================================================
function funTorokuChk(row, chkNewObj, chkKnryoObj) {
	var frm = document.frm00;          //ﾌｫｰﾑへの参照

	// 指定行の行データをセットする為にxml を設定
//xmlFGEN3450.load(xmlFGEN3450O);   // 空行の時、設定されていない
	funAddRecNode(xmlFGEN3450, "FGEN3460");

	// 対象行データを先頭行にセットする
	// 製品コード（前ゼロ含む ⇒ 前ゼロを削除）
//	funXmlWrite(xmlFGEN3450, "cd_seihin", frm.inputSeihinCd.value, 0);
	funXmlWrite(xmlFGEN3450, "cd_seihin", frm.inputSeihinCd.value.replace(/^0+/,""), 0);
	// 製品名
	funXmlWrite(xmlFGEN3450, "nm_shohin", frm.seihinNm.value, 0);

	// 試作No（選択データ）
	var no_sisaku = "";
	if (frm.ddlShisakuNo.selectedIndex < 1) {
		funXmlWrite(xmlFGEN3450, "sisakuNo", "", 0);
	} else {
		funXmlWrite(xmlFGEN3450, "sisakuNo", frm.ddlShisakuNo.options[frm.ddlShisakuNo.selectedIndex].value, 0);

		no_sisaku = frm.ddlShisakuNo.options[frm.ddlShisakuNo.selectedIndex].value.split("-");
		funXmlWrite(xmlFGEN3450, "cd_shain", no_sisaku[0], 0);
		funXmlWrite(xmlFGEN3450, "nen", no_sisaku[1], 0);
		funXmlWrite(xmlFGEN3450, "no_oi", no_sisaku[2], 0);
		funXmlWrite(xmlFGEN3450, "no_eda", no_sisaku[3], 0);
	}

	// 製造工場情報
	funXmlWrite(xmlFGEN3450, "cd_seizokojo", frm.seizoKojoCd.value, 0);

	if (frm.ddlShokuba.selectedIndex < 1) {
		funXmlWrite(xmlFGEN3450, "cd_shokuba", "", 0);
	} else {
		funXmlWrite(xmlFGEN3450, "cd_shokuba", frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value, 0);
	}
	if (frm.ddlLine.selectedIndex < 1) {
		funXmlWrite(xmlFGEN3450, "cd_line", "", 0);
	} else {
		funXmlWrite(xmlFGEN3450, "cd_line", frm.ddlLine.options[frm.ddlLine.selectedIndex].value, 0);
	}

	if (row < 0) {
		// 一覧がない
		funXmlWrite(xmlFGEN3450, "seq_shizai", "", 0);
		// 試算資材テーブル更新処理（エラー）
		if (!funShizaiUpdate()) {
			return false;
		}

	} else {
		// 発注先コンボボックス
		var ddlHattyusaki = document.getElementById("ddlHattyusaki-" + row);
		// 対象資材コンボボックス
		var ddlShizai = document.getElementById("ddlShizai-" + row);
		// 資材手配ステータス
		var tehai_status = document.getElementById("flg_tehai_status-" + row).value;

		//May Thu 2016/09/28 【KPX@1602367】 add start
		// 参照ファイル名
        var inputName = document.getElementById("inputName-" + row).value;
        // 参照ファイルパス
//		var filePath = document.getElementById("fileName-" + row).value;
//		filePath = funSetfilePath(filePath);   //CUT する
		//May Thu 2016/09/28 【KPX@1602367】 add end

		// 行情報を取得
		// 試作No.シーケンス
		funXmlWrite(xmlFGEN3450, "seq_shizai", document.getElementById("seq_shizai-" + row).value, 0);
		// 旧資材コード
		var cd_shizai = document.getElementById("cd_shizai-" + row).value;
		funXmlWrite(xmlFGEN3450, "cd_shizai", cd_shizai, 0);
//		funXmlWrite(xmlFGEN3450, "cd_shizai", document.getElementById("cd_shizai-" + row).value, 0);

		// 新資材名
		var nm_shizai_new = document.getElementById("inputNewShizaiNm-" + row).value;
		funXmlWrite(xmlFGEN3450, "nm_shizai_new", nm_shizai_new, 0);

		// 新資材コードエラーの時、新資材名が空白（コードもクリア済）
		// 新資材コード（前ゼロ削除）
		var cd_shizai_new = document.getElementById("inputNewShizaiCd-" + row).value;
		funXmlWrite(xmlFGEN3450, "cd_shizai_new", cd_shizai_new.replace(/^0+/,""), 0);

		// 対象資材コンボボックス
		funXmlWrite(xmlFGEN3450, "cd_taisyoshizai", ddlShizai.options[ddlShizai.selectedIndex].value, 0);

		// 発注先コンボボックス
		funXmlWrite(xmlFGEN3450, "cd_hattyusaki", ddlHattyusaki.options[ddlHattyusaki.selectedIndex].value, 0);
		// 資材手配テーブルの検索時の更新日時
		funXmlWrite(xmlFGEN3450, "dt_tehai_koshin", document.getElementById("dt_tehai_koshin-" + row).value, 0);

		// 保存ファイルのサブフォルダー
	    var subFolder = funSelChk();
	    subFolder = subFolder + cd_shizai;

	    // ファイル名有の場合 版下ファイルパス（サブフォルダ）を設定する
	    if(inputName != "") {
	    	funXmlWrite(xmlFGEN3450, "file_path_henshita", subFolder, 0);	// 版下ファイルパス
	    } else {
	    	funXmlWrite(xmlFGEN3450, "file_path_henshita", "", 0);	// 版下ファイルパス
	    }

		//May Thu 【KPX@1602367】 2016/09/28 add start
		// ファイル名　版下アップロード
		funXmlWrite(xmlFGEN3450, "nm_file_henshita", inputName, 0);
//
//		funXmlWrite(xmlFGEN3450, "file_path_henshita", subFolder, 0);	// 版下ファイルパス
//		funXmlWrite(xmlFGEN3450, "file_path_henshita", filePath, 0);
		//May Thu 【KPX@1602367】 2016/09/28 add end

		// 完了チェック
		if (chkKnryoObj.checked == true) {
			funXmlWrite(xmlFGEN3450, "chk_kanryo", 1, 0);

			// 資材手配ステータスが 3（手配済） 以外の時、資材手配テーブル登録・更新
			if (tehai_status != 3) {
				// 資材手配テーブル登録・更新を先に実行
				// （資材手配テーブル更新チェックで検索時点より更新された時、エラーが返る）
					if (funTehaiInsert()) {
						// 更新が成功した時、試算資材テーブルを更新
						if (!funShizaiUpdate()) {
							return false;
						}
					}
			} else {
				// 資材手配ステータスが 3（手配済） の時、更新しない
				// 試算資材テーブル更新処理
				if (!funShizaiUpdate()) {
					return false;
				}
			}

		} else {
			funXmlWrite(xmlFGEN3450, "chk_kanryo", 0, 0);

			// 資材手配ステータスが 1（未入力） or 2（未手配） の時、資材手配テーブル削除
			if (tehai_status == 1 || tehai_status == 2) {
				// 資材手配テーブル削除を先に実行
				// （資材手配テーブル削除チェックで検索時点より更新された時、エラーが返る）
					if (funTehaiDelete()) {
						// 削除が成功した時、試算資材テーブルを更新
						if (!funShizaiUpdate()) {
							return false;
						}
					}
			} else {
				// 資材手配ステータスが 3（手配済） の時、削除しない
				// 試算資材テーブル更新処理
				if (!funShizaiUpdate()) {
					return false;
				}
			}
		}
	}
	return true;

}

//========================================================================================
//キー項目のXMLファイルに書き込み、行情報取得
//作成者：E.Kitazawa
//作成日：2014/09/24
//引数  ：①XmlId  ：XMLID
//      ：②reqAry ：機能ID別送信XML(配列)
//戻り値：なし
//概要  ：ＤＢ処理の為、行情報を取得し、XMLファイルに引数を設定する
//========================================================================================
function funRowDataOutput(XmlId, reqAry) {
  // 選択行番号

}




//========================================================================================
// 試算資材テーブル更新処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：なし
// 概要  ：資材テーブル更新
//========================================================================================
function funShizaiUpdate() {

	var XmlId = "RGEN3460";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3460");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3460I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3460O);


	//引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}

	// 処理中ﾒｯｾｰｼﾞ表示
	funShowRunMessage();

	// ｾｯｼｮﾝ情報、共通情報を取得
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3460, xmlReqAry, xmlResAry, 1) == false) {
	    //処理中ﾒｯｾｰｼﾞ非表示
	    funClearRunMessage();
		return false;
	}

	// 処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    // 処理結果
    if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
    	return true;
    } else {
        //error
        return false;
    }

}

//========================================================================================
// 資材手配テーブル登録処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：なし
// 概要  ：資材手配テーブル登録
//========================================================================================
function funTehaiInsert() {

	var XmlId = "RGEN3470";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3470");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3470I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3470O);


	//引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}

	// 処理中ﾒｯｾｰｼﾞ表示
	funShowRunMessage();

	//ｾｯｼｮﾝ情報、共通情報を取得
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3470, xmlReqAry, xmlResAry, 1) == false) {
		// 処理中ﾒｯｾｰｼﾞ非表示
	    funClearRunMessage();
		return false;
	}

	// 処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    // 処理結果
    if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
    	return true;
    } else {
        //error（ここには来ない）
        return false;
    }


}

//========================================================================================
//資材手配テーブル削除処理
//作成者：E.Kitazawa
//作成日：2014/09/24
//引数  ：なし
//概要  ：資材手配テーブル削除
//========================================================================================
function funTehaiDelete() {

	var XmlId = "RGEN3480";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3480");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3480I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3480O);


	// 処理中ﾒｯｾｰｼﾞ表示
	funShowRunMessage();

	//引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}

	//ｾｯｼｮﾝ情報、共通情報を取得
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3480, xmlReqAry, xmlResAry, 1) == false) {
		// 処理中ﾒｯｾｰｼﾞ非表示
		funClearRunMessage();
		return false;
	}

	// 処理中ﾒｯｾｰｼﾞ非表示
	funClearRunMessage();

	// 処理結果
	if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
		return true;
	} else {
		//error
		return false;
	}


}

//========================================================================================
//デザインスペースボタン押下処理
//作成者：E.Kitazawa
//作成日：2014/09/24
//引数  ：なし
//概要  ：デザインスペースダウンロード画面を表示する
//========================================================================================
function funDesignSpace() {

	var frm = document.frm00;          //ﾌｫｰﾑへの参照

	var args = new Array("","","","");		// ダイアログに渡すパラメータ
	var retVal;

	args[0] = window;			//"SQ230ShizaiTehaiInput.jsp";
	// 選択された製造工場情報を渡す
	args[1] = frm.seizoKojoCd.value;
	if (frm.ddlShokuba.selectedIndex > 0) {
		args[2] = frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value;
	}
	if (frm.ddlLine.selectedIndex > 0) {
		args[3] = frm.ddlLine.options[frm.ddlLine.selectedIndex].value;
	}

	// デザインスペースダウンロード画面をモーダルで開く
    retVal = funOpenModalDialog("../SQ240DesignSpaceDL/SQ240DesignSpaceDL.jsp"
    		, args
    		, "dialogHeight:900px;dialogWidth:1360px;minimize=yes;maximize=yes;status:no;scroll:no");

	return true;

}

//========================================================================================
// 参考資料ボタン押下
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：なし
// 概要  ：参考資料画面を表示する
//========================================================================================
function funSiryo() {

    // 参考資料ウィンドウオープン
	var win = window.open("../SQ220ShiryoRef/SQ220ShiryoRef.jsp","sansyo","menubar=no,resizable=yes");
    // 再表示の為にフォーカスにする
    win.focus();

    return true;

}


//========================================================================================
// 原価試算、原料情報取得＆表示処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：①mode  ：処理モード
//         1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：原価試算、原料情報取得
//========================================================================================
function funGetGenryoInfo(mode) {

	//------------------------------------------------------------------------------------
	//                                    変数宣言
	//------------------------------------------------------------------------------------
	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	var XmlId = "RGEN0012";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0012");
	var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0012I );
	var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0012O );


	//------------------------------------------------------------------------------------
	//                                  基本情報取得
	//------------------------------------------------------------------------------------
	//引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
		funClearRunMessage2();
		return false;
	}

	//処理中ﾒｯｾｰｼﾞ表示
	funShowRunMessage();


	//ｾｯｼｮﾝ情報、共通情報を取得
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0012, xmlReqAry, xmlResAry, mode) == false) {
		//処理中ﾒｯｾｰｼﾞ非表示
		funClearRunMessage();
		return false;
	}


	//------------------------------------------------------------------------------------
	//                                  原料情報表示
	//------------------------------------------------------------------------------------
	var Genryo_Left = document.getElementById("divGenryo_Left");
	var Genryo_Right = document.getElementById("divGenryo_Right");
	// 表示行数
	var dsp_cnt = 0;

	//原料テーブル左側
	dsp_cnt = funGenryo_LeftDisplay(xmlResAry[2], Genryo_Left);

	//原料テーブル右側
	funGenryo_RightDisplay(xmlResAry[2], Genryo_Right, dsp_cnt);

	// 表示
	Genryo_Left.style.display = "block";
	Genryo_Right.style.display = "block";

	//処理中ﾒｯｾｰｼﾞ非表示
	funClearRunMessage();

	//処理終了
	return true;

}


//========================================================================================
// 原料テーブル（左側）情報表示
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：①xmlUser ：更新情報格納XML名
//       ：②ObjectId：設定オブジェクト
// 戻り値：表示する行数（）
// 概要  ：原料テーブル（左側）表示用のHTML文を生成、出力する。
//========================================================================================
function funGenryo_LeftDisplay(xmlData, obj) {

	var tablekihonNm;     //読み込みテーブル名
	var tableGenryoNm;    //読み込みテーブル名
	var OutputHtml;       //出力HTML
	var cnt_genryo;       //行数
	var sort_kotei;       //工程
	var cd_kotei;         //工程CD
	var seq_kotei;        //工程SEQ
	var cd_genryo;        //原料CD
	var nm_genryo;        //原料名
	var henko_renraku;    //変更
	var tanka;            //単価
	var budomari;         //歩留
	var genryo_fg;        //原料行フラグ
	var i;                //ループカウント
	var hdnKojoNmTanka;		//工場名 単価
	var hdnKojoNmBudomari;	//工場名 歩留

	//HTML出力オブジェクト設定
	OutputHtml = "";

	//テーブル名設定
	tablekihonNm = "kihon";
	tableGenryoNm = "genryo";

	//行数取得
	cnt_genryo = funXmlRead_3(xmlData, tablekihonNm, "cnt_genryo", 0, 0);

	// 原料情報の原料コードより下の部分を表示しない為、表示行数取得
	var dsp_cnt = 0;
	for(i = 0; i < cnt_genryo; i++){
		// 行程
		sort_kotei = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "sort_kotei", 0, i));
		// 行程が設定されている行までを表示する
		if (sort_kotei != "") {
			dsp_cnt = i+1;
		}
	}

	//出力HTML設定
	OutputHtml += "<input type=\"hidden\" id=\"cnt_genryo\" name=\"cnt_genryo\" value=\"" + cnt_genryo + "\">";

	//テーブル表示
	OutputHtml += "<table id=\"tblList1\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" width=\"605px\" style=\"word-break:break-all;word-wrap:break-word;\">";
//	for(i = 0; i < cnt_genryo; i++){
	// 行程詳細は表示しない
	for(i = 0; i < dsp_cnt; i++){

		//HTML
		var sentaku_checkbox = ""; //選択チェックボックス
		var tanka_textbox = "";    //単価テキストボックス
		var budomari_textbox = ""; //歩留テキストボックス
		var txtReadonly = "readonly";      //テキストボックス入力設定
		var txtClass = henshuNgClass;      //テキストボックス背景色

		//XMLデータ取得
		sort_kotei = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "sort_kotei", 0, i));
		cd_kotei = funXmlRead_3(xmlData, tableGenryoNm, "cd_kotei", 0, i);
		seq_kotei = funXmlRead_3(xmlData, tableGenryoNm, "seq_kotei", 0, i);
		cd_genryo = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "cd_genryo", 0, i));
		nm_genryo = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "nm_genryo", 0, i));
		henko_renraku = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "henko_renraku", 0, i));
		tanka = funXmlRead_3(xmlData, tableGenryoNm, "tanka", 0, i);
		budomari = funXmlRead_3(xmlData, tableGenryoNm, "budomari", 0, i);
		genryo_fg = funXmlRead_3(xmlData, tableGenryoNm, "genryo_fg", 0, i);
		hdnKojoNmTanka = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "nm_kojo_tanka", 0, i));
		hdnKojoNmBudomari = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "nm_kojo_budomari", 0, i));


		if(genryo_fg == "1"){
			//原料行の場合は選択、単価、歩留、工程CD、工程SEQオブジェクトの生成
			sentaku_checkbox = "&nbsp;";
			tanka_textbox = "<input type=\"text\" class=\"" + txtClass + "\" style=\"width:70px;text-align:right;\" value=\"" + tanka + "\" " + txtReadonly + " tabindex=\"-1\" />";
			budomari_textbox = "<input type=\"text\" class=\"" + txtClass + "\" style=\"width:45px;text-align:right;\" value=\"" + budomari + "\" " + txtReadonly + " tabindex=\"-1\" />";

			//行
			OutputHtml += "  <input type=\"hidden\" id=\"gyo_" + (i+1) + "\" class=\"table_text_view\" readonly value=\"" + (i + 1) + "\" title=\"" + (i + 1) + "\" tabindex=\"-1\" name=\"gyo\" style=\"text-align:center;\" />";
		}else if(genryo_fg == "2"){
			//工程行の場合は選択、単価、歩留、工程CD、工程SEQは空白
			sentaku_checkbox = "&nbsp;";
			tanka_textbox = "&nbsp;";
			budomari_textbox = "&nbsp;";
		}



		//テーブルタグ生成
		OutputHtml += "    <tr class=\"disprow\" id=\"tableRowL_" + i + "\" name=\"tableRowL_" + i + "\">";

		//選択
		OutputHtml += "        <td class=\"column\" style=\"text-align:right;width:20px;\">" + sentaku_checkbox + "</td>";

		//工程
		OutputHtml += "        <td class=\"column\" style=\"text-align:right;width:20px;\"><input type=\"text\" id=\"sort_kote\" name=\"sort_kote\" class=\"table_text_view\" style=\"text-align:center;\" readonly value=\"" + sort_kotei + "\" tabindex=\"-1\" /></td>";

		//原料コード
		if(genryo_fg == "1"){
			//原料行の場合Wクリック時のイベントを設定
			OutputHtml += "        <td class=\"column\" style=\"text-align:center;width:105px;\">";
			OutputHtml += "            <input type=\"text\" id=\"txtCd_genryo_" + (i+1) + "\" name=\"txtCd_genryo_" + (i+1) + "\" class=\"table_text_view\" readonly value=\"" + cd_genryo + "\" tabindex=\"-1\" />";
			OutputHtml += "        </td>";
		}else if(genryo_fg == "2"){
			//工程行の場合Wクリックのイベントは未設定
			OutputHtml += "        <td class=\"column\" style=\"text-align:center;width:105px;\">";
			OutputHtml += "            <input type=\"text\" id=\"txtCd_genryo_" + (i+1) + "\" name=\"txtCd_genryo_" + (i+1) + "\" class=\"table_text_view\" readonly value=\"" + cd_genryo + "\" tabindex=\"-1\" />";
			OutputHtml += "        </td>";
		}

		//原料名
		OutputHtml += "        <td class=\"column\" style=\"width:310px;\">";
		OutputHtml += "            <input type=\"text\" id=\"txtCd_genryoNm_" + (i+1) + "\" class=\"table_text_view\" readonly value=\"" + nm_genryo + "\" title=\"" + nm_genryo + "\" tabindex=\"-1\" />";

		OutputHtml += "        </td>";

		//変更
		OutputHtml += "        <td class=\"column\" style=\"text-align:center;width:20px;\"><input type=\"text\" name=\"txtHenkouRen_"+(i+1)+"\" id=\"txtHenkouRen_"+(i+1)+"\" class=\"table_text_view\" readonly value=\"" + henko_renraku + "\"  tabindex=\"-1\" /></td>";

		//単価
		OutputHtml += "        <td class=\"column\" style=\"width:70px;\">";
		OutputHtml += "            " + tanka_textbox;
		OutputHtml += "        </td>";

		//歩留
		OutputHtml += "        <td class=\"column\" style=\"width:45px;\">";
		OutputHtml += "            " + budomari_textbox;
		OutputHtml += "        </td>";

		OutputHtml += "    </tr>";

	}

	OutputHtml += "</table>";

	//HTMLを出力
	obj.innerHTML = OutputHtml;

	OutputHtml = null;

	// 表示行数を返す
	return dsp_cnt;
//	return true;
}


//========================================================================================
//原料テーブル（右側）情報表示
//作成者：E.Kitazawa
//作成日：2014/09/24
//引数  ：①xmlUser ：更新情報格納XML名
//      ：②ObjectId：設定オブジェクト
//      ：③表示行数
//戻り値：なし
//概要  ：原料テーブル（右側）表示用のHTML文を生成、出力する。
//========================================================================================
function funGenryo_RightDisplay(xmlData, obj, cnt) {

	//------------------------------------------------------------------------------------
	//                                    変数宣言
	//------------------------------------------------------------------------------------
	var tablekihonNm;     //読み込みテーブル名
	var tableHaigoNm;     //読み込みテーブル名
	var OutputHtml;       //出力HTML
	var cnt_genryo;       //行数
	var table_size;       //テーブル幅
	var seq_shisaku;      //試作SEQ
	var shisakuDate;      //試作日付
	var nm_sample;        //サンプルNO（名称）
	var haigo;            //配合
	var kingaku;          //金額
	var i;                //ループカウント
	var j;                //ループカウント
	var color = "#ffffff";

	//------------------------------------------------------------------------------------
	//                                    初期設定
	//------------------------------------------------------------------------------------
	//HTML出力オブジェクト設定
	OutputHtml = "";

	//テーブル名設定
	tablekihonNm = "kihon";
	tableHaigoNm = "shisaku";

	//列数取得
	cnt_sample = funXmlRead_3(xmlData, tablekihonNm, "cnt_sanpuru", 0, 0);

	//行数取得
	cnt_genryo = funXmlRead_3(xmlData, tablekihonNm, "cnt_genryo", 0, 0);

	//テーブル幅取得
	table_size = 175 * cnt_sample;


	//------------------------------------------------------------------------------------
	//                                  テーブル生成
	//------------------------------------------------------------------------------------
	//テーブル生成
	OutputHtml += "<table id=\"dataTable2\" name=\"dataTable2\" cellspacing=\"0\" width=\"" + table_size + "px;\">";

	//右側ヘッダサイズ指定
	OutputHtml += "<colgroup>";
	for(i = 0; i < cnt_sample; i++){
		OutputHtml += "   <col style=\"width:175px;\"/>";
	}
	OutputHtml += "</colgroup>";

	//右側ヘッダテーブル設定
	OutputHtml += "<thead class=\"rowtitle\">";
	OutputHtml += "    <tr style=\"top:expression(offsetParent.scrollTop);position:relative;\">";
	for(i = 0; i < cnt_sample; i++){

		//XMLデータ取得
		seq_shisaku = funXmlRead_3(xmlData, tableHaigoNm+i, "seq_shisaku", 0, 1);
		shisakuDate = funXmlRead_3(xmlData, tableHaigoNm+i, "shisakuDate", 0, 1);
		nm_sample = funXmlRead_3(xmlData, tableHaigoNm+i, "nm_sample", 0, 1);

		OutputHtml += "        <th class=\"columntitle\">";
		OutputHtml += "            <table frame=\"void\" width=\"100%\" height=\"50px\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" bordercolor=\"#bbbbbb\">";
		OutputHtml += "                <tr><td align=\"center\" colspan=\"2\" height=\"20px\">&nbsp;</td></tr>";

		//試作日付
		if(shisakuDate.length < 10){
			shisakuDate = "0000/00/00";
			color = "#0066FF";
		}
		OutputHtml += "                <tr><td align=\"center\" colspan=\"2\"><font color=\""+ color +"\">" + shisakuDate + "</font></td></tr>";

		//サンプルNo
		OutputHtml += "                <tr>";
		OutputHtml += "                   <td style=\"width:175px;\" align=\"center\" colspan=\"2\">";
		OutputHtml += "                      <input type=\"text\" style=\"border-width:0px;background-color:#0066FF;color:#FFFFFF;text-align:center;\" readonly value=\"" + nm_sample + "\" tabindex=\"-1\" />";

		OutputHtml += "                   </td>";
		OutputHtml += "                </tr>";

		OutputHtml += "                <tr><td align=\"center\"><font color=\"#ffffff\" style=\"\">配合(kg)※</font></td><td align=\"center\" style=\"width:45%;\"><font color=\"#ffffff\">金額(円)</font></td></tr>";
		OutputHtml += "            </table>";
		OutputHtml += "        </th>";
	}
	OutputHtml += "    </tr>";
	OutputHtml += "</thead>";

	//右側明細テーブル設定
	OutputHtml += "<tbody>";
	OutputHtml += "    <table class=\"detail\" align=\"left\" id=\"tblList2\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" style=\"width:" + table_size + "px;display:list-item\">";

	//行ループ
//	for(i = 0; i < cnt_genryo; i++){
	// 左表示で取得した行数まで表示する
	for(i = 0; i < cnt; i++){

		OutputHtml += "        <tr class=\"disprow\" id=\"tableRowR_" + i + "\" name=\"tableRowR_" + i + "\" >";

		//列ループ
		for(j = 0; j < cnt_sample; j++){

			//XMLデータ取得
			haigo  = funXmlRead_3(xmlData, tableHaigoNm+j, "haigo", 0, i+2);
			kingaku = funXmlRead_3(xmlData, tableHaigoNm+j, "kingaku", 0, i+2);

			OutputHtml += "            <td class=\"column\" style=\"width:175px;\" align=\"left\">";
			OutputHtml += "                <table frame=\"void\" width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\">";
			OutputHtml += "                    <tr>";
			OutputHtml += "                        <td class=\"dot_r\" style=\"width:55%;text-align:right;\">" + "<input type=\"text\" style=\"width:82px;border-width:0px;background-color:#FFFFFF;text-align:right;\" readonly value=\"" + haigo + "\" tabindex=\"-1\" />" + "</td>";
			OutputHtml += "                        <td class=\"dot_l\" style=\"width:45%;text-align:right;\">" + "<input type=\"text\" style=\"width:67px;border-width:0px;background-color:#FFFFFF;text-align:right;\" readonly value=\"" + kingaku + "\" tabindex=\"-1\" />" + "</td>";
			OutputHtml += "                    </tr>";
			OutputHtml += "                </table>";
			OutputHtml += "            </td>";
		}
		OutputHtml += "        </tr>";
	}

	OutputHtml += "    </table>";
	OutputHtml += "</tbody>";

	OutputHtml += "</table>";


	//------------------------------------------------------------------------------------
	//                                  HTML出力
	//------------------------------------------------------------------------------------
	//HTMLを出力
	obj.innerHTML = OutputHtml;

	OutputHtml = null;

	return true;
}

//========================================================================================
//次画面遷移処理
//作成者：E.Kitazawa
//作成日：2014/09/01
//概要  ：次画面に遷移する
//========================================================================================
function funNext(mode) {
    var XmlId = "RGEN3455";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //検索条件に一致するデザインスペースデータを取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3455, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }
    //画面を閉じる
    close(self);
    return true;
}

//========================================================================================
// XMLファイルに書き込み
// 作成者：E.Kitazawa
// 作成日：2014/09/01
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
		if (XmlId.toString() == "RGEN3200"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //SA290
				funXmlWrite(reqAry[i], "id_user", "", 0);
				funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShizaiTehaiInput, 0);
				break;
			case 2:    //FGEN3200（対象資材を取得）
				break;
			case 3:    //FGEN3310（種類を取得）
				break;
			case 4:    //FGEN2130（所属部署フラグ取得）
				break;
			case 5:    //FGEN2130（所属部署フラグ取得）
				break;
			}

			//製造工場選択変更
		} else if (XmlId.toString() == "RGEN3230") {
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //FGEN3230:ログインユーザの会社
				funXmlWrite(reqAry[i], "cd_seizokojo", frm.seizoKojoCd.value, 0);
				break;
			}

			//職場選択変更
		} else if (XmlId.toString() == "RGEN3240") {
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //FGEN3240:ログインユーザの会社
				funXmlWrite(reqAry[i], "cd_seizokojo", frm.seizoKojoCd.value, 0);
				funXmlWrite(reqAry[i], "cd_shokuba", frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value, 0);
				break;
			}

			//製品コード検索
		} else if (XmlId.toString() == "RGEN3420"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //FGEN3420（試作No）
				// 製品コード（数値）
				funXmlWrite(reqAry[i], "cd_seihin", frm.inputSeihinCd.value, 0);
				break;
			case 2:    //FGEN3430（品名マスタ検索）
				funXmlWrite(reqAry[i], "cd_seihin", frm.inputSeihinCd.value, 0);
				break;
			}

			//新資材コード検索
		} else if (XmlId.toString() == "RGEN3440"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //FGEN3440
			    // 選択行番号
			    var row_no = funGetCurrentRow();
			    // 新資材コード　cd_shizai
			    var shizaiCd = document.getElementById("inputNewShizaiCd-" + row_no).value;
				// 部署
				funXmlWrite(reqAry[i], "cd_busho", frm.seizoKojoCd.value, 0);
				funXmlWrite(reqAry[i], "cd_shizai", shizaiCd, 0);
				break;
			}

			//検索ボタン押下
		} else if (XmlId.toString() == "RGEN3450"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				// 試作No
				var no_sisakutmp = "";
				var no_sisakuwork = "";
				var put_code      =  frm.inputSeihinCd.value  + ":::" ;
				if (frm.ddlShisakuNo.selectedIndex < 1) {
					put_code = put_code + ":::" + ":::" + ":::" + ":::";
				} else {
					no_sisakuwork =frm.ddlShisakuNo.options[frm.ddlShisakuNo.selectedIndex].value;
					no_sisakutmp = frm.ddlShisakuNo.options[frm.ddlShisakuNo.selectedIndex].value.split("-");
					put_code     = put_code + no_sisakuwork + ":::" + no_sisakutmp[0] + ":::"
					put_code     = put_code + no_sisakutmp[1] + ":::" +  no_sisakutmp[2] + ":::" +  no_sisakutmp[3];
				}
				// アップロード後の再ロード用
                funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", put_code, 0);
				break;
			case 1:    //FGEN3450
				// 製品コード
				funXmlWrite(reqAry[i], "cd_seihin", frm.inputSeihinCd.value, 0);
				// 試作No
				var no_sisaku = "";
				if (frm.ddlShisakuNo.selectedIndex < 1) {
					funXmlWrite(reqAry[i], "sisakuNo", "", 0);
				} else {
					funXmlWrite(reqAry[i], "sisakuNo", frm.ddlShisakuNo.options[frm.ddlShisakuNo.selectedIndex].value, 0);
					no_sisaku = frm.ddlShisakuNo.options[frm.ddlShisakuNo.selectedIndex].value.split("-");
					funXmlWrite(reqAry[i], "cd_shain", no_sisaku[0], 0);
					funXmlWrite(reqAry[i], "nen", no_sisaku[1], 0);
					funXmlWrite(reqAry[i], "no_oi", no_sisaku[2], 0);
					funXmlWrite(reqAry[i], "no_eda", no_sisaku[3], 0);
				}

				break;
			}

			//登録ボタン押下：資材テーブル更新
		} else if (XmlId.toString() == "RGEN3460"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //FGEN3460

				// 製品コード
				funXmlWrite(reqAry[i], "cd_seihin", funXmlRead(xmlFGEN3450, "cd_seihin", 0), 0);
				// 試作No.、製造工場情報、行情報を Xml にセット
				funXmlWrite(reqAry[i], "sisakuNo", funXmlRead(xmlFGEN3450, "sisakuNo", 0), 0);

				funXmlWrite(reqAry[i], "cd_shain", funXmlRead(xmlFGEN3450, "cd_shain", 0), 0);
				funXmlWrite(reqAry[i], "nen", funXmlRead(xmlFGEN3450, "nen", 0), 0);
				funXmlWrite(reqAry[i], "no_oi", funXmlRead(xmlFGEN3450, "no_oi", 0), 0);
				funXmlWrite(reqAry[i], "no_eda", funXmlRead(xmlFGEN3450, "no_eda", 0), 0);
				//対象行がない時、""をセット
				funXmlWrite(reqAry[i], "seq_shizai", funXmlRead(xmlFGEN3450, "seq_shizai", 0), 0);

				funXmlWrite(reqAry[i], "cd_shizai_new", funXmlRead(xmlFGEN3450, "cd_shizai_new", 0), 0);
				funXmlWrite(reqAry[i], "nm_shizai_new", funXmlRead(xmlFGEN3450, "nm_shizai_new", 0), 0);
				funXmlWrite(reqAry[i], "cd_seizokojo", funXmlRead(xmlFGEN3450, "cd_seizokojo", 0), 0);
				funXmlWrite(reqAry[i], "cd_shokuba", funXmlRead(xmlFGEN3450, "cd_shokuba", 0), 0);
				funXmlWrite(reqAry[i], "cd_line", funXmlRead(xmlFGEN3450, "cd_line", 0), 0);
				funXmlWrite(reqAry[i], "cd_taisyoshizai", funXmlRead(xmlFGEN3450, "cd_taisyoshizai", 0), 0);
				funXmlWrite(reqAry[i], "cd_hattyusaki", funXmlRead(xmlFGEN3450, "cd_hattyusaki", 0), 0);
				funXmlWrite(reqAry[i], "chk_kanryo", funXmlRead(xmlFGEN3450, "chk_kanryo", 0), 0);
				//May Thu 【KPX@1602367】 2016/09/28 add start
				funXmlWrite(reqAry[i], "nm_file_henshita", funXmlRead(xmlFGEN3450, "nm_file_henshita", 0), 0);
				funXmlWrite(reqAry[i], "file_path_henshita", funXmlRead(xmlFGEN3450, "file_path_henshita", 0), 0);
				//May Thu 【KPX@1602367】 2016/09/28 add end
				break;
			}
			//登録ボタン押下：資材手配テーブル登録
		} else if (XmlId.toString() == "RGEN3470"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //FGEN3470
				// 試作No.、製造工場情報、行情報を Xml にセット
				funXmlWrite(reqAry[i], "cd_shain", funXmlRead(xmlFGEN3450, "cd_shain", 0), 0);
				funXmlWrite(reqAry[i], "nen", funXmlRead(xmlFGEN3450, "nen", 0), 0);
				funXmlWrite(reqAry[i], "no_oi", funXmlRead(xmlFGEN3450, "no_oi", 0), 0);
				funXmlWrite(reqAry[i], "no_eda", funXmlRead(xmlFGEN3450, "no_eda", 0), 0);
				funXmlWrite(reqAry[i], "seq_shizai", funXmlRead(xmlFGEN3450, "seq_shizai", 0), 0);

				funXmlWrite(reqAry[i], "cd_seihin", funXmlRead(xmlFGEN3450, "cd_seihin", 0), 0);
				funXmlWrite(reqAry[i], "nm_shohin", funXmlRead(xmlFGEN3450, "nm_shohin", 0), 0);
				funXmlWrite(reqAry[i], "cd_shizai", funXmlRead(xmlFGEN3450, "cd_shizai", 0), 0);
				funXmlWrite(reqAry[i], "cd_shizai_new", funXmlRead(xmlFGEN3450, "cd_shizai_new", 0), 0);

				// 検索時の資材手配テーブルの更新日時
				funXmlWrite(reqAry[i], "dt_tehai_koshin", funXmlRead(xmlFGEN3450, "dt_tehai_koshin", 0), 0);
				break;
			}
			//登録ボタン押下：資材手配テーブル削除
		} else if (XmlId.toString() == "RGEN3480"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //FGEN3480
				// 試作No.
				funXmlWrite(reqAry[i], "cd_shain", funXmlRead(xmlFGEN3450, "cd_shain", 0), 0);
				funXmlWrite(reqAry[i], "nen", funXmlRead(xmlFGEN3450, "nen", 0), 0);
				funXmlWrite(reqAry[i], "no_oi", funXmlRead(xmlFGEN3450, "no_oi", 0), 0);
				funXmlWrite(reqAry[i], "no_eda", funXmlRead(xmlFGEN3450, "no_eda", 0), 0);
				funXmlWrite(reqAry[i], "seq_shizai", funXmlRead(xmlFGEN3450, "seq_shizai", 0), 0);

				// 検索時の資材手配テーブルの更新日時
				funXmlWrite(reqAry[i], "dt_tehai_koshin", funXmlRead(xmlFGEN3450, "dt_tehai_koshin", 0), 0);

				break;
			}

			//検索（原料情報）
		}  else if (XmlId.toString() == "RGEN0012"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //FGEN0012
				// 試作No
				var no_sisaku = frm.ddlShisakuNo.options[frm.ddlShisakuNo.selectedIndex].value.split("-");
				funXmlWrite(reqAry[i], "cd_shain", no_sisaku[0], 0);
				funXmlWrite(reqAry[i], "nen", no_sisaku[1], 0);
				funXmlWrite(reqAry[i], "no_oi", no_sisaku[2], 0);
				funXmlWrite(reqAry[i], "no_eda", no_sisaku[3], 0);
				break;
			}
		} else if (XmlId.toString() == "RGEN3455"){
			//検索ボタン押下
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
                funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", mode, 0);
				break;
			case 1:    //FGEN3450
				break;
			}
			// 発注先選択（新規）
		} else if (XmlId.toString() == "RGEN3310"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //FGEN3310
				// 未使用フラグ
				funXmlWrite(reqAry[i], "flg_mishiyo", 1, 0);
				break;
			}
			//登録ボタン押下：資材手配テーブル登録
		}
    }
    return true;

}


//========================================================================================
// コンボボックス作成処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②xmlData  ：設定XML
//       ：③mode     ：モード
//       ：④kara     ：1:先頭空白行なし、2:先頭空白行あり
// 概要  ：コンボボックスを作成する
//========================================================================================
function funCreateComboBox(obj, xmlData, mode, kara) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var reccnt;
    var objNewOption;
    var atbName;
    var atbCd;
    var i;

    //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
    funClearSelect(obj, kara);

    //件数取得
    reccnt = funGetLength(xmlData);

    //ﾃﾞｰﾀが存在しない場合
    if (reccnt == 0) {
        //処理を中断
        return true;
    }

    //属性名の取得
    switch (mode) {
        case 1:    //ﾘﾃﾗﾙﾏｽﾀ（対象資材）
            atbName = "nm_literal";
            atbCd = "cd_literal";
            break;
        case 2:    //発注先
            atbName = "nm_hattyusaki";
            atbCd = "cd_hattyusaki";
            break;
        case 3:    //職場マスタ
            atbName = "nm_shokuba";
            atbCd = "cd_shokuba";

            break;
        case 4:    //ラインマスタ
            atbName = "nm_line";
            atbCd = "cd_line";
            break;
        case 5:    //試作No（資材テーブル）
            atbName = "no_shisaku";
            atbCd = "no_shisaku";
            break;
    }

    for (i = 0; i < reccnt; i++) {
        if (funXmlRead(xmlData, atbCd, i) != "" && funXmlRead(xmlData, atbName, i) != "") {
            objNewOption = document.createElement("option");
            obj.options.add(objNewOption);
            objNewOption.innerText = funXmlRead(xmlData, atbName, i);
//【KPX@1602367 20161019】 add start
            if(mode == 2) {
            	// 発注先コードの場合のみint型に変換する
            	objNewOption.value = parseInt(funXmlRead(xmlData, atbCd, i),10);
            } else {
            objNewOption.value = funXmlRead(xmlData, atbCd, i);
        }
//【KPX@1602367 20161019】 add end
        }
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽのﾃﾞﾌｫﾙﾄ表示
    obj.selectedIndex = 0;
    return true;
}

//========================================================================================
// デフォルト値選択処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②mode     ：モード
// 概要  ：コンボボックスのデフォルト値を選択する
//========================================================================================
function funDefaultIndex(obj, mode) {

/*    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var selIndex;
    var i;

    // ｺﾝﾎﾞﾎﾞｯｸｽのｲﾝﾃﾞｯｸｽ指定
    obj.selectedIndex = selIndex;
*/
    return true;
}


//========================================================================================
// コンボボックス選択処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②xmlData  ：XMLデータ
//       ：③komoku   ：検索項目名
//       ：④text     ：検索値
// 戻り値：一致した行番号を返す
// 概要  ：コンボボックスのIndex を設定する
//========================================================================================
function funSetIndex(obj, xmlData, komoku, text) {

	if (text == "") {
		return 0;
	}

	//件数取得
	var reccnt = funGetLength(xmlData);
//【KPX@1602367】20161019 mod start
	var xmlValue ;
	for (var i = 0; i < reccnt; i++) {
		if(komoku == "cd_hattyusaki") {
			xmlValue = parseInt(funXmlRead(xmlData, komoku, i),10);
		} else {
			xmlValue = funXmlRead(xmlData, komoku, i)
		}
		// 検索項目値が等しい場合、Index設定
		if (xmlValue == text) {
//【KPX@1602367】20161019 mod end
			//ｺﾝﾎﾞﾎﾞｯｸｽのIndex設定（空白ありの場合、+1）
			obj.selectedIndex = i + 1;
			return i;
		}
	}

	//ｺﾝﾎﾞﾎﾞｯｸｽのﾃﾞﾌｫﾙﾄ表示
	obj.selectedIndex = 0;

	return 0;

}

//========================================================================================
// XMLデータより検索行のコード←→名前 変換
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：①xmlData  ：XMLデータ
//       ：②komoku   ：検索項目名
//       ：③text     ：検索値
//       ：④ret      ：取得項目名
// return：コード値又は名前
// 概要  ：一致するコード又は名前を取得
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

//========================================================================================
// 空白置換関数（"" → "&nbsp;"）
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：なし
// 概要  ："" → "&nbsp;" へ置換する
//========================================================================================
function funKuhakuChg(strChk) {

  //空白の場合
  if(strChk == ""){
      return "";
  }
  //空白でない場合
  else{
      return strChk;
  }

}

//【KPX@1602367】
//========================================================================================
// 製品コードが変更された
// 作成者：May Thu
// 作成日：2016/09/15
// 引数  ：なし
// 概要  ：製品コードを検索設定する。
//========================================================================================
function funSeihinSearchBox(){
	var frm = document.frm00;          //ﾌｫｰﾑへの参照
	var args = new Array("");		// ダイアログに渡すパラメータ
    args[0] = window;			//"SQ230ShizaiTehaiInput.jsp";

	// 製品コード検索画面をモーダルで開く
    var retVal = funOpenModalDialog("../SQ330SeihinSearch/SQ330SeihinSearch.jsp"
    		, args
    		, "dialogHeight:500px;dialogWidth:600px;minimize=yes;maximize=yes;status:no;scroll:no");

    if(retVal != "") {
    	//製品コードをコピー
	    frm.inputSeihinCd.value = retVal;
	    //2017.02.03 指摘事項画面の起動処理に違和感があるとのことで製品検索処理後、荷姿、製品名の再検索実施処理を追加 START Makoto Takada
	    funSeihinSearch();
	    //2017.02.03 指摘事項画面の起動処理に違和感があるとのことで製品検索処理後、荷姿、製品名の再検索実施処理を追加 END Makoto Takada
	}
}

//========================================================================================
// アップロードファイル名が変更された
// 作成者：May Thu
// 作成日：2016/09/15
// 引数  ：row   ：行番号
// 概要  ：表示用ファイル名にセットする
//========================================================================================
function funChangeFile(row) {
	funSetInput(row);

}

//========================================================================================
// 参照ボタン押下処理
// 作成者：May Thu
// 作成日：2016/09/15
// 引数  ：row   ：行番号
// 概要  ：アップロードファイルを指定
//========================================================================================
function funSetInput(row) {
	var inputName = document.getElementById("inputName-" + row);		// 表示用ファイル名
	var fileName = document.getElementById("fileName-" + row);			// 参照ファイル名
	// ファイルダイアログ
	if (fileName.value == "") {


		} else {
		// 参照ファイルフルパスよりファイル名を取得し表示用にセット
		//（拡張子のチェック不要）
		inputName.value = funGetFileNm(fileName.value);
		// アップロードファイル名は文字色を変更
		inputName.style.color = "red";

	}
    return true;

}

//========================================================================================
// 参照ボタンキー押下処理（ENTERキー対応）
// 作成者：May Thu
// 作成日：2016/09/15
// 引数  ：row    ：行番号
//         keyCode：キーコード
// 概要  ：アップロードファイルを指定
//========================================================================================
function funEnterFile(row, keyCode) {
	// ENTERキー押下時、参照ボタンクリックの動きを実行
	if (keyCode == 13) {
		var fileName = document.getElementById("fileName-" + row);			// 参照ボタン
		// ENTERキー押下でクリックイベントを発生させる
		fileName.click();
	} else {
		return false;
	}
}

//========================================================================================
// 参照ボタン押下処理
// 作成者：May Thu
// 作成日：2016/09/15
// 引数  ：str   ファイルパス
// 戻り値：ファイル名
// 概要  ：ファイルパスを引数として渡して分解する
//========================================================================================
function funGetFileNm(str){

	var FileName = "";				// 戻り値

	var strTmp = str.split("\\");	// フォルダー、ファイル名を分解

	// ファイル名を取得
	FileName = strTmp[strTmp.length - 1];
	return FileName;

}

//========================================================================================
// 参照ボタン押下処理
// 作成者：May Thu
// 作成日：2016/09/28
// 引数  ：str   ファイル名
// 戻り値：ファイルパス
// 概要  ：ファイル名を引数として渡して分解する
//========================================================================================
function funSetfilePath(str){
	var FilePath = "";
	var result = str.lastIndexOf("\\");
    FilePath = str.substring(0,result);
	return FilePath;
}

//========================================================================================
// 登録ボタン押下時チェック処理
// 作成者：May Thu
// 作成日：2016/09/30
// 引数  ：ファイル名
// 戻り値：Boolean    true：登録可   false：不正文字あり
// 概要  ：ファイル名の中に不正文字が含まれているかチェックする
//========================================================================================
function funChkFileNm(str){

	// 不正文字が含まれている場合、falseを返す
	var ret = str.match(/[;/?:@&=+$,%#]/);
	if (ret) {
		return false;
	}
	return true;

}
//========================================================================================
// ＤＢ更新：排他制御
// 作成者：May thu
// 作成日：2014/12/03
// 戻り値： true  :処理実行可
//          false：処理続行不可（更新されている）
// 概要  ：前回更新と同様のデータを取得して、処理続行不可能とする。
//========================================================================================
function funKoshinChk(){
//現在は更新排他は行わないとしているので、いったんは空メソッドで実施
	return true;
}
//========================================================================================
// 製品コード検索処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：keyCode ： ENTER 又は TAB
// 概要  ：製品コードより試作No一覧、製品名、荷姿の検索を行う
//========================================================================================
function funSeihinSearch() {
	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	// 製品コード
	var inputSeihin = document.getElementById("inputSeihinCd");
	var XmlId = "RGEN3420";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3420","FGEN3430");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3420I,xmlFGEN3430I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3420O,xmlFGEN3430O);

	// 製品名、荷姿をクリア
	frm.seihinNm.value = "";
	frm.nisugata.value = "";
	// 製造工場
	frm.seizoKojoCd.value = "";
	frm.seizoKojoNm.value = "";
	//ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
	funClearSelect(frm.ddlShisakuNo, 2);
	funClearSelect(frm.ddlShokuba, 2);
	funClearSelect(frm.ddlLine, 2);

	// 資材情報一覧の初期化
	funClearList();

	//処理中ﾒｯｾｰｼﾞ表示
	funShowRunMessage();

	// 製品コードを退避
	sv_seihinCd = inputSeihin.value;

	// 製品コードが入力チェックは RGEN3420内
	// 引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		funClearRunMessage();
		return false;
	}

	// ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3420, xmlReqAry, xmlResAry, 1) == false) {
		funClearRunMessage();
		// 製品コードにフォーカスをセット（次に移ってしまう）
		inputSeihin.focus();
		return false;
	}

	//処理中ﾒｯｾｰｼﾞ非表示
	funClearRunMessage();

	//試作No一覧件数のﾁｪｯｸ
	if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
		// 試作Noのコンボボックスを作成
		funCreateComboBox(frm.ddlShisakuNo, xmlResAry[2], 5, 2);
	}else {
		// 試作Noが取得できない
		seihin_errmsg = "試作No";
	}

	//製品名、荷姿
	if (funXmlRead(xmlResAry[3], "flg_return", 0) == "true") {
		frm.seihinNm.value = funXmlRead(xmlResAry[3], "name_hinmei", 0);
		frm.nisugata.value = funXmlRead(xmlResAry[3], "name_nisugata", 0);
	} else {
		// 製品名が取得できない
		if (seihin_errmsg != "") seihin_errmsg += "・";
		seihin_errmsg += "製品名";
	}

	if (seihin_errmsg != ""){
		seihin_errmsg += "が取得できません。製品コードを再入力して下さい。";
		funErrorMsgBox(seihin_errmsg);
		// 製品コードにフォーカスを戻す（次に移ってしまう）
		inputSeihin.focus();
		return false;
	}
	// 製造工場（部署）：試作No.選択時にセット
	return true;
}
//========================================================================================
// 検索ボタン押下処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：なし
// 概要  ：資材データの検索を行う
//========================================================================================
function refunSearch() {
	var frm = document.frm00;    //ﾌｫｰﾑへの参照

	// 製品コードが変更されている場合、エラーメッセージ表示
	if (frm.inputSeihinCd.value != sv_seihinCd) {
		funErrorMsgBox(E000066);
//		funErrorMsgBox("製品コードが変更されています。\\n試作Noを検索して下さい。");
		frm.inputSeihinCd.focus();
		return false;
	}
    //処理中ウィンドウ表示の為、setTimeoutで処理予約
    funSearch0();
}

//========================================================================================
// 発注先コンボボックス変更（新規選択時）
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：row   ：行番号
// 概要  ：発注先コンボボックス変更時の処理
//========================================================================================
function funSetHattyusaki(row) {

	var flghattyusaki = document.getElementById("flgHattyusaki-" + row); //発注先

	if(flghattyusaki.value != ""){
		// 使用可能な発注先（未使用を除く）に設定済み
		return;
	}

	var hattyusaki    = document.getElementById("ddlHattyusaki-" + row); //発注先
	var cd_hattyusaki = hattyusaki.options[hattyusaki.selectedIndex].value; //発注先コード
	var nm_hattyusaki = hattyusaki.options[hattyusaki.selectedIndex].text; //発注先名

    var frm = document.frm00; // ﾌｫｰﾑへの参照
    var XmlId = "RGEN3310";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3310");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3310I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3310O);

    // 引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    // ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3200, xmlReqAry, xmlResAry,
            1) == false) {
        return false;
    }

    // 発注先コンボボックス
    var ddlHattyusaki = document.getElementById("ddlHattyusaki-" + row);

    // 発注先コンボボックス設定
    funCreateComboBox(ddlHattyusaki, xmlFGEN3310O, 2, 2);

    // 発注先選択Indexの設定
    funSetIndex(ddlHattyusaki, xmlFGEN3310O, "cd_hattyusaki", cd_hattyusaki);

    flghattyusaki.value = "1";
    return;
}
//START【KPX@1602367】Makoto Takada 指定行の登録対象が登録必要かをチェックします。
//========================================================================================
// 行の登録チェック
// 作成者：Makoto Takada
// 作成日：2017/02/09
// 引数  ：なし
// 概要  ：指定行の登録対象が登録必要かをチェックする。
//========================================================================================
function funTorokuStatusChk(row) {
	var frm = document.frm00;          //ﾌｫｰﾑへの参照@@@
	// 表示ファイル名
	var inputName = document.getElementById("inputName-" + row);
	if(inputName.style.color == "red") {
		return true;
	}
    var flg_tehai_status_org = document.getElementById("flg_tehai_status_org-" + row).value; 
	if (flg_tehai_status_org == "") {
		return true;
	}
	return false;
}
//END 【KPX@1602367】指定行の登録対象が登録必要かをチェックします。 Makoto Takada
//START【KPX@1602367】Makoto Takada 職場、製造ラインのチェック
//========================================================================================
// 行の登録チェック(排他をする前にチェックする必要があるのでJAVASCRIPTでチェックする。)
// 作成者：Makoto Takada
// 作成日：2017/03/13
// 引数  ：なし
// 概要 ：
//========================================================================================
function funTorokuInputCheck() {
	var frm = document.frm00;          //ﾌｫｰﾑへの参照@@@
	// 職場が変更されている場合、エラーメッセージ表示
	if (frm.ddlShokuba.selectedIndex < 1) {
		funErrorMsgBox("職場は必須項目です。\\n選択してください。");
		return true;
	}
	// 製造ラインのチェックが変更されている場合、エラーメッセージ表示
	if (frm.ddlLine.selectedIndex < 1) {
		funErrorMsgBox("製造ラインは必須項目です。\\n選択して下さい。");
		return true;
	}
	return false;
}
//【KPX@1602367 20170313】 add END
