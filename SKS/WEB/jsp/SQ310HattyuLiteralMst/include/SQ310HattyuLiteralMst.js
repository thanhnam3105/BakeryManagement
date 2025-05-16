

//========================================================================================
// 共通変数
// 作成者：t2nakamura
// 作成日：2016/09/15
//========================================================================================
//データ保持
var rowcnt = 0;			// 明細行数
var cd_kaisha = 0;		// 会社コード

// DBに登録できる最大ファイル名の長さ
var MAXLEN_FILENM = 100;

// DB検索時のMAX更新日時
var MAX_DtKoshin = "";

//========================================================================================
// 初期表示処理
//作成者：t2nakamura
//作成日：20160912
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

    //画面設定
    funInitScreen(ConHattyuLiteralMstId);

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //一覧のｸﾘｱ
    //funClearList();

    //画面情報を取得・設定
    if (funGetInfo(1) == false) {
        //処理中ﾒｯｾｰｼﾞ非表示
        funClearRunMessage();
        return false;
    }

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    // ボタン非活性
    funDisBtn();

    return true;
}

//========================================================================================
//画面情報取得処理
//作成者：hisahori
//作成日：2014/10/10
//引数  ：①mode  ：処理モード
//        1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
//戻り値：正常終了:true／異常終了:false
//概要  ：ユーザのセッション情報、コンボボックスの情報を取得する
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGENAAA";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //
    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGENAAA, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //ﾕｰｻﾞ情報表示
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //権限関連の処理を行う
    //funSaveKengenInfo();

    return true;

}
//========================================================================================
//検索ボタン押下処理
//作成者：E.Kitazawa
//作成日：2014/09/01
//引数  ：なし
//概要  ：デザインスペースの検索を行う
//========================================================================================
function funSearch() {

 var frm = document.frm00;    //ﾌｫｰﾑへの参照

var cdHattyu = document.getElementById("cdhattyu").value;
// 数値以外エラー

 // 半角変換
  var halfVal = cdHattyu.replace(/[！-～]/g,
    function( tmpStr ) {
      // 文字コードをシフト
      return String.fromCharCode( tmpStr.charCodeAt(0) - 0xFEE0 );
    }
  );

for (var i = 0; i < halfVal.length; i++) {
	 if (halfVal.charAt(i) == "0" ||  parseInt(halfVal.charAt(i))){

	 } else {
		 funErrorMsgBox("数値を入力してください。");
		 return false;
	 }
}

var nmHattyu = document.getElementById("nmhattyu").value;
var formatStr = nmHattyu.replace(/^\s+|\s+$/g, "");
nmHattyu = formatStr;

 //処理中ﾒｯｾｰｼﾞ表示
 funShowRunMessage();

 //検索処理
 //処理中ウィンドウ表示の為、setTimeoutで処理予約
 setTimeout(function(){ funDataSearch() }, 0);

 // スクロール一番上に設定する
 document.getElementById("sclList").scrollTop = 0;

 return true;

}

//========================================================================================
//リテラル情報取得処理
//作成者：t2nakamura
//作成日：2016/9/12
//引数  ：なし
//概要  ：リテラルデータの検索を行う
//========================================================================================
function funDataSearch() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN3580";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3580");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3580I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3580O);
    var PageCnt;
    var RecCnt;
    var ListMaxRow;

    //選択行の初期化
    funSetCurrentRow("");

    //一覧のｸﾘｱ
    funClearList();

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        //画面初期化
        //funInit();
        funClearRunMessage();
        return false;
    }
    //検索条件に一致するﾘﾃﾗﾙﾃﾞｰﾀを取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3580, xmlReqAry, xmlResAry, 1) == false) {
        //一覧のｸﾘｱ
        funClearList();
        funClearRunMessage();
        return false;
    }

    //ﾃﾞｰﾀ件数のﾁｪｯｸ
    if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
        //表示
        tblList.style.display = "block";

        // 発注先カテゴリマスタ情報一覧の作成
        funAddHattyuusakiListt(xmlResAry[2]);

        //処理中ﾒｯｾｰｼﾞ非表示
        funClearRunMessage();

    } else {

    	// 対象データなし
    	funErrorMsgBox(E000030);

       //非表示：対象データなし
        tblList.style.display = "none";

        // ボタン非活性
        funDisBtn();

        //処理中ﾒｯｾｰｼﾞ非表示
        funClearRunMessage();

        return false;
    }

	// 実行可能ボタンを活性化
	frm.btnInsert.disabled = false;
	frm.btnExcel.disabled = false;
	frm.btnLineAdd.disabled = false;



    return true;
}

//========================================================================================
//発注先コード連動処理
//作成者：t2nakamura
//作成日：2016/1006
//引数  ：なし
//概要  ：カテゴリに紐付くリテラルコンボボックスを生成する
//========================================================================================
function funChangeCategory(row) {

 var frm = document.frm00;    //ﾌｫｰﾑへの参照
 var XmlId = "RGEN3650";
 var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3650");
 var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3650I);
 var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3650O);


 //指定された発注先名をクリアする。
 var nm_literal = document.getElementById("nm_literal-" + row);


 // 引数をXMLﾌｧｲﾙに設定
 if (funReadyOutput(XmlId, xmlReqAry, 1, rowcnt ,row) == false) {
     return false;
 }

 // ﾘﾃﾗﾙ情報を取得
 if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3650, xmlReqAry, xmlResAry, 1) == false) {

     return false;
 }

 // エラーメッセージが空ではない場合エラー
 var errorMsg = funXmlRead(xmlResAry[2], "msg_error", 0);

 // if (errorMsg != "") {
//	 funErrorMsgBox(errorMsg);
//     return false;
// }

 // hidden発注先名
 nm_literal.value = funXmlRead(xmlResAry[2], "nm_literal", 0);

 // 発注先名
 var table = document.getElementById("tblList");

 table.rows.item(row).cells(1).innerHTML = "<input type=\"hidden\" name=\"nm_literal\" id=\"nm_literal-" + row + "\" value=\"" + funXmlRead(xmlResAry[2], "nm_literal", 0) + "\" style=\"width:217px\">";
	 if(funXmlRead(xmlResAry[2], "nm_literal", 0) == ""){
		 table.rows.item(row).cells(1).innerHTML += "&nbsp;"
     } else {
 table.rows.item(row).cells(1).innerHTML += funXmlRead(xmlResAry[2], "nm_literal", 0);
     }


 if (errorMsg != "") {
	 funErrorMsgBox(errorMsg);

     return false;
 }
 return true;

}


//========================================================================================
//行追加ボタン押下処理
//作成者：t2nakamura
//作成日：2016/09/15
//引数  ：なし
//概要  ：発注先マスター情報に行追加する
//========================================================================================
function funLineAdd() {

		// 条件が選択していない時（検索後しか来ない）
	//	if (funSelChk() == "") {
	////		funErrorMsgBox(E000031);
	//		funErrorMsgBox(funSelChk.Msg);
	//		return false;
		//}

	// 発注先マスタ-情報一覧表示
	tblList.style.display = "block";

	// 明細行を追加
	funAddHattyuusakiMst(rowcnt, "");


	insertFlg(rowcnt);

	document.getElementById("nm_2nd_literal-" + rowcnt).value = "  様";

	rowcnt++;
	// 追加した行までスクロール
	goBottom();

}

//========================================================================================
//発注先マスター情報　テーブル一覧作成処理
//作成者：E.Kitazawa
//作成日：2014/09/01
//引数  ：xmlData    ：XMLデータ
//概要  ：デザインスペース情報一覧を作成する
//========================================================================================
function funAddHattyuusakiListt(xmlData) {

	var tableNm = "table";

    //件数取得し、行数を保存
	rowcnt = funGetLength(xmlData);

	// キーを保持
	var hdn_cd_literal = "";
	var hdn_cd_2nd_literal = "";

	for(var i = 0; i < rowcnt; i++){

    	// 行追加
		funAddHattyuusakiMst(i, xmlData);

	}
}

//========================================================================================
//行追加した時に一番下にスクロースする
//作成者：t2nakamura
//作成日：2016/09/15
//========================================================================================
function goBottom() {
    var obj = document.getElementById("sclList");
    if(!obj) return;
    obj.scrollTop = obj.scrollHeight;
}

//========================================================================================
//発注先マスタ情報 明細行作成処理
//作成者：t2nakamura
//作成日：2016/09/15
//引数  ：①row     ：行番号
//    ：②xmlData ：XMLデータ（「行追加」時は、""）
//概要  ：デザインスペース情報一覧の行追加処理
//========================================================================================
function funAddHattyuusakiMst(row, xmlData) {

		var detail = document.getElementById("detail");

	 var html;

	 // 行設定
	 var tr = document.createElement("tr");
	 tr.setAttribute("class", "disprow");
	 tr.setAttribute("id", "tr" + row);

//	 // 削除チェックボックス
//	 var td1 = document.createElement("td");
//	 td1.setAttribute("class", "column");
//	 html = "<input type=\"checkbox\" id=\"check-" + row + "\" name=\"check\" value=\"" + row + "\" >";
//	 // 更新フラグ
//	 html += "<input type=\"hidden\" id=\"update_flgl-" + row + "\" name=\"update_flgl\" value=\" \">";
//	 td1.innerHTML = html;
//	 td1.style.textAlign = "center";
//	 td1.style.width = 0;
//	 tr.appendChild(td1);


	 // 発注先コード
	 var td2 = document.createElement("td");
	 td2.setAttribute("class", "column");
	 html = "<input type=\"text\" name=\"cd_literal\" id=\"cd_literal-" + row + "\" value=\"" + fillsZero(funXmlRead(xmlData, "cd_literal", row), 6) + "\" style=\"width:98px; ime-mode:disabled;\"";
	 html += "onchange=\"funHattyuuUpdate(" + row + ");\" >";
	 // 更新フラグ
	 html += "<input type=\"hidden\" id=\"update_flgl-" + row + "\" name=\"update_flgl\" value=\" \">";
	 html += "<input type=\"hidden\" id=\"hiddenCd_literal-" + row + "\" name=\"hiddenCd_literal\" value=\"" + funXmlRead(xmlData, "cd_literal", row) + "\">";
	 td2.innerHTML = html;
	 td2.style.textAlign="left";
	 td2.style.width = 99;
	 tr.appendChild(td2);

	 // 発注先名
	 var td3 = document.createElement("td");
	 td3.setAttribute("class", "column");
	 html = funXmlRead(xmlData, "nm_literal", row);
	 if(html == ""){
         html = "&nbsp;"
     }
	 html += "<input type=\"hidden\" name=\"nm_literal\" id=\"nm_literal-" + row + "\" value=\"" + funXmlRead(xmlData, "nm_literal", row) + "\" style=\"width:217px\">";
	 //html += "onchange=\"funHattyuuUpdate(" + row + ");\" >";
	 td3.innerHTML = html;
	 td3.style.textAlign="left";
	 td3.style.width = 218;
	 tr.appendChild(td3);

	 // 発注先表示順
	 var td4 = document.createElement("td");
	 td4.setAttribute("class", "column");
	 html = "<input type=\"text\" name=\"no_sort\" id=\"no_sort-" + row + "\" value=\"" + funXmlRead(xmlData, "no_sort", row) + "\" style=\"width:59px; ime-mode:disabled;\"";
	 html += "onchange=\"funHattyuuUpdate(" + row + ");\" >";
	 td4.innerHTML = html;
	 td4.style.textAlign="left";
	 td4.style.width = 60;
	 tr.appendChild(td4);

	 // 担当者コード
	 var td5 = document.createElement("td");
	 td5.setAttribute("class", "column");
	 html = funXmlRead(xmlData, "cd_2nd_literal", row);
	 if(html == ""){
         html = "&nbsp;"
     }
	 html += "<input type=\"hidden\" id=\"hiddenCd_2nd_literal-" + row + "\" name=\"hiddenCd_2nd_literal\" value=\"" + funXmlRead(xmlData, "cd_2nd_literal", row) + "\">";
	 td5.innerHTML += html;
	 td5.style.textAlign="left";
	 td5.style.width = 60;
	 tr.appendChild(td5);

	 // 担当者
	 var td6 = document.createElement("td");
	 td6.setAttribute("class", "column");
	 html = "<input type=\"text\" name=\"nm_2nd_literal\" id=\"nm_2nd_literal-" + row + "\" value=\"" + funXmlRead(xmlData, "nm_2nd_literal", row) + "\" style=\"width:375px; ime-mode:active;\"";
	 html += "onchange=\"funHattyuuUpdate(" + row + ");\" >";
	 html += "<input type=\"hidden\" id=\"hiddenNm_2nd_literal-" + row + "\" name=\"hiddenNm_2nd_literal\" value=\"" + funXmlRead(xmlData, "nm_2nd_literal", row) + "\">";
	 td6.innerHTML = html;
	 td6.style.textAlign="left";
	 td6.style.width = 376;
	 tr.appendChild(td6);

	 // 第二リテラル表示順
	 var td7 = document.createElement("td");
	 td6.setAttribute("class", "column");
	 html = "<input type=\"text\" name=\"no_2nd_sort\" id=\"no_2nd_sort-" + row + "\" value=\"" + funXmlRead(xmlData, "no_2nd_sort", row) + "\" style=\"width:62px; ime-mode:disabled;\"";
	 html += "onchange=\"funHattyuuUpdate(" + row + ");\" >";
	 td7.innerHTML = html;
	 td7.style.textAlign="left";
	 td7.style.width = 63;
	 tr.appendChild(td7);

	 // メールアドレス
	 var td8 = document.createElement("td");
	 td8.setAttribute("class", "column");
	 html = "<input type=\"text\" name=\"mail_address\" id=\"mail_address-" + row + "\" value=\"" + funXmlRead(xmlData, "mail_address", row) + "\" style=\"width:268px; ime-mode:disabled;\"";
	 html += "onchange=\"funHattyuuUpdate(" + row + ");\" >";
	 td8.innerHTML = html;
	 td8.style.textAlign="left";
	 td8.style.width = 268;
	 tr.appendChild(td8);

	 // 未使用チェックボックス
	 var td9 = document.createElement("td");
	 td9.setAttribute("class", "column");
	 html = "<input type=\"checkbox\" name=\"flg_mishiyo\" id=\"flg_mishiyo-" + row + "\" style=\"width:57px\"";
	 html += "onchange=\"funHattyuuUpdate(" + row + ");\" >";
	 html += "<input type=\"hidden\" id=\"hiddenFlg_mishiyo-" + row + "\" name=\"hiddenFlg_mishiyo\" value=\" \">";
	 td9.innerHTML = html;
	 td9.style.textAlign = "center";
	 td9.style.width = 58;
	 tr.appendChild(td9);

	 detail.appendChild(tr);

	 // 未使用のチェックボックスchecked
   	 var flg_misiyo = funXmlRead(xmlData, "flg_mishiyo", row);
	 if (flg_misiyo == 2) {
		 document.getElementById("flg_mishiyo-" + row).checked = true;
	 } else {
		 document.getElementById("flg_mishiyo-" + row).checked = false;
	 }
	 // チェックボックス非表示（現時点では使用していない20161006）
	 //document.getElementById("check-" + row).style.display = "none";


}

//========================================================================================
//ゼロ埋め処理
//作成者：H.Shima
//作成日：2014/12/12
//========================================================================================
function fillsZero(obj, keta){
 var ret = obj;

 while(ret.length < keta){
     ret = "0" + ret;
 }
 return ret;
}

//========================================================================================
//登録ボタン押下処理
//作成者：t2nakamura
//作成日：2016/09/28
//引数  ：なし
//概要  ：発注先マスター情報の登録
//========================================================================================
function funUpdate() {

	 var frm = document.frm00;   //ﾌｫｰﾑへの参照
	 var upFildNm = "";          //アップロードするフィールド名
	// var delFileNm = "";         //削除するファイル名
	// var lstSyurui = {};         //登録する種類の配列（重複チェック用）
	// var strMsg = ""				//確認メッセージ付加文字

	 // 保存ファイルのサブフォルダー取得（工場、職場、製造ラインを_で繋ぐ）
	 //var subFolder = funSelChk();
	 // 条件が選択されていない場合
	//	if (subFolder == "") {
	////		funErrorMsgBox(E000031);
	//		funErrorMsgBox(funSelChk.Msg);
	//		return false;
	//	}

	// 選択した種類コードを_で繋ぐ為、ファイル毎に設定
	//var subFlst = "";

	// 一覧データがない
	if (rowcnt == 0) {
		funErrorMsgBox(E000030);
		return false;
	}

	// 表示されている一覧をチェック
	// 参照ファイル、表示用inputが空白でないこと
	// メッセージリスト
	var msgStr = "";

	for(var i = 0; i < rowcnt; i++){
	     // 行オブジェクト
	 	var tr = document.getElementById("tr" + i);

		 //var check = document.getElementById("check-" + i);

	     // 発注先コード
	     var cd_literal = document.getElementById("cd_literal-" + i);

	     // 発注先名
	     //var  nm_literal = document.getElementById("nm_literal-" + i);

	     // 発注先表示順
	     var no_sort = document.getElementById("no_sort-" + i);

	     // 担当者
	     var  nm_2nd_literal = document.getElementById("nm_2nd_literal-" + i);

	     // 担当者表示順
	     var no_2nd_sort = document.getElementById("no_2nd_sort-" + i);

	     // メールアドレス
	     var  mail_address = document.getElementById("mail_address-" + i);

	     // update_flgl
	     var  update_flgl = document.getElementById("update_flgl-" + i);




		// 発注先コードが空白の場合、エラー（ＤＢ登録できない）
		if (cd_literal.value == "") {
			msgStr += ((i + 1) + E000054 + "<br>");

		// 数値チェック
		} else {
			if (isNaN(cd_literal.value)) {
				msgStr += ((i + 1) + E000054 + "<br>");
			}
		}

//		// 発注先名が空白の場合、エラー（ＤＢ登録できない）
//		// 空白ではない場合数値でない場合、エラー（DB登録できない）
//		if (nm_literal.value == "") {
//			msgStr += ((i + 1) + E000055 + "<br>");
//		}

		// 発注先表示順が空白の場合、エラー（ＤＢ登録できない）
		// 空白ではない場合数値チェック
		if (no_sort.value == "") {
			msgStr += ((i + 1) + E000056 + "<br>");
		} else {
			if (isNaN(no_sort.value)) {
				msgStr += ((i + 1) + E000055 + "<br>");
			}
		}

		// 担当者が空白の場合、エラー（ＤＢ登録できない）
		var nm2ndLiteral = nm_2nd_literal.value;
		var samaStr = nm2ndLiteral.substr(nm2ndLiteral.length-1);
		if (nm_2nd_literal.value == "") {
			msgStr += ((i + 1) + E000057 + "<br>");

		} else if (samaStr != "様") {
			msgStr += ((i + 1) + "行目に「様」を入力してください。" + "<br>");
		}

		// 担当者表示順が空白の場合、エラー（DB登録できない）
		// 担当者表示順が数値でない場合、エラー（DB登録できない）
		if (no_2nd_sort.value == "") {
			msgStr += ((i + 1) + E000046 + "<br>");
		} else {
			if (isNaN(no_2nd_sort.value)) {
				msgStr += ((i + 1) + E000047 + "<br>");
			}
		}

		// メールアドレス空白の場合、エラー（DB登録できない）
		if (mail_address.value == "") {
			msgStr += ((i + 1) + E000048 + "<br>");

		// メールアドレスが空でなくメールアドレス形式でない場合、エラー【DB登録できない）
		} else if (mail_address.value != "" && !mail_address.value.match(/^[A-Za-z0-9]+[\w-]+@[\w\.-]+\.\w{2,}$/)) {
			msgStr += ((i + 1) + E000049 + "<br>");
		}



	}
	// エラーメッセージがある場合表示する。
	if (msgStr != "") {
		funErrorMsgBox(msgStr);
		return false;
	}


	// 一覧データがない
	if (rowcnt == 0) {
		funErrorMsgBox(E000030);
		return false;
	}

	 // 登録確認
	if (funConfMsgBox(I000002 ) != ConBtnYes) {
		return;
	}

	funHattyuusakiMstInsert();


//	// 登録、更新、削除処理
//	for (var i = 0; i < rowcnt; i++) {
//		var checkbox = document.getElementById("check-" + i);
//		var updateFlg = document.getElementById("update_flgl-" + i);
//
//		if (checkbox.checked) {
//			// 削除
//			funHattyuusakiMstDelete(i);
//		} else if (checkbox.checked == false && updateFlg.value == 1) {
//			// 登録
//			if (funHattyuusakiDifferenceCheck(i)) {
//
//				funHattyuusakiMstInsert(i);
//			} else {
//				// エラー処理
//				funErrorMsgBox("発注先コードが同じ場合、別の発注先名、発注先表示順は入力できません。");
//				return false;
//			}
//
//		} else if (checkbox.checked == false && updateFlg.value == 2) {
//			// 更新
//			if (funHattyuusakiDifferenceCheck(i)) {
//				funHattyuusakiMstUpdate(i);
//			} else {
//				// エラー処理
//				funErrorMsgBox("発注先コードが同じ場合、別の発注先名、発注先表示順は入力できません。");
//				return false;
//			}
//		}
//	}

	// 登録、更新、削除後の再建策



//	// 発注先マスターＤＢ登録、更新、削除
//	if (funHattyuusakiMstUpdate()) {
//	 //正常登録
//	 funInfoMsgBox(I000005);
//	}

}

//========================================================================================
//発注先マスターDB更新処理
//作成者：t2nakamura
//作成日：2016/0916
//引数  ：XmlId     ：XmlId
//概要  ：発注先マスターDB更新の実行
//========================================================================================
function funHattyuusakiMstInsert(){

	var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN3630";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3630");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3630I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3630O);

    //XMLの初期化
    setTimeout("xmlFGEN3630I.src = '../../model/FGEN3630I.xml';", ConTimer);

	 // 処理中ﾒｯｾｰｼﾞ表示
	 funShowRunMessage();


	//引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, 1, rowcnt) == false) {
	    //処理中ﾒｯｾｰｼﾞ非表示
	    funClearRunMessage();
		return false;
	}

	//発注先マスタDB登録
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3630, xmlReqAry, xmlResAry, 1) == false) {
	    //処理中ﾒｯｾｰｼﾞ非表示
	    funClearRunMessage();
	    //funErrorMsgBox(dspMsg);
		return false;
	}

	//処理中ﾒｯｾｰｼﾞ非表示
	 funClearRunMessage();



	 if (funXmlRead(xmlResAry[2], "updateErrorFlg_return", 0) == "false") {  //updateErrorFlg_return
		 funSearch();
	 	return true;
	 } else {
	     //error
		 //完了ﾒｯｾｰｼﾞの表示
		 var dspMsg = funXmlRead(xmlResAry[2], "meisaiNum", 0);

	 	funErrorMsgBox((parseInt(dspMsg)+1) + "行目の発注先コード、担当者コードが重複のため、更新に失敗しました。");
	     return false;
	 }



	 return true;

}


//========================================================================================
//発注先マスター登録、更新チェック処理
//作成者：t2nakamura
//作成日：2016/0929
//引数  ：XmlId     ：XmlId
//概要  ：発注先マスターCD一意チェック
//========================================================================================
function funHattyuusakiDifferenceCheck(row) {

	var errorFlg = true;

	var updateHattyuusakiJoin = "";
	// 更新対象発注先コード
	var beforeCdLiteralValue = document.getElementById("cd_literal-" + row).value;
	 // 半角変換
	  var cdLiteralValue = beforeCdLiteralValue.replace(/[！-～]/g,
	    function( tmpStr ) {
	      // 文字コードをシフト
	      return String.fromCharCode( tmpStr.charCodeAt(0) - 0xFEE0 );
	    }
	  );

	// 更新対象発注先名
	//var nmLiteralValue = document.getElementById("nm_literal-" + row).value;
	var table = document.getElementById("tblList");
	var nmLiteralValue = table.rows.item(row).cells(1).innerText;
	// 更新対象発注先表示順
	var noSortValue = document.getElementById("no_sort-" + row).value;

	// 更新対象の発注先コードと発注先名と発注先名表示順をジョイント
	updateHattyuusakiJoin = cdLiteralValue + "@" + nmLiteralValue + "@" + noSortValue;

	for (var i = 0; i < rowcnt; i++) {
		// 比較側発注先コード
		var beforeTaisyouCdLiteral = document.getElementById("cd_literal-" + i).value;
		 // 半角変換
		  var taisyouCdLiteral = beforeTaisyouCdLiteral.replace(/[！-～]/g,
		    function( tmpStr ) {
		      // 文字コードをシフト
		      return String.fromCharCode( tmpStr.charCodeAt(0) - 0xFEE0 );
		    }
		  );



		// 比較側発注先名
		var table = document.getElementById("tblList");
		var taisyoNmLiteralValue = table.rows.item(i).cells(1).innerText;
		//var taisyoNmLiteralValue = document.getElementById("nm_literal-" + i).value;
		// 比較側発注先表示順
		var taisyoSortlValue = document.getElementById("no_sort-" + i).value;

		// 比較側の発注先コードと発注先名と発注先名表示順をジョイント
		hikakutaisyouHattyuusakiJoin = taisyouCdLiteral + "@" + taisyoNmLiteralValue + "@" + taisyoSortlValue;
		// 発注先コードが同じ場合
		if (taisyouCdLiteral == cdLiteralValue) {
			if (hikakutaisyouHattyuusakiJoin != updateHattyuusakiJoin) {
				errorFlg = false;
			}
		}
	}
	return errorFlg;
}

//========================================================================================
//EXCEL出力処理
//作成者：t2nakamura
//作成日：2016/09/30
//概要  ：Excel出力を行う
//========================================================================================
function funFileExcel() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照

    var XmlId = "RGEN3670";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3670");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3670I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3670O);

    // Excel出力確認ﾒｯｾｰｼﾞの表示
    if (funConfMsgBox(I000008) != ConBtnYes) {
        return false;
    }

    //XMLの初期化
    setTimeout("xmlFGEN3670I.src = '../../model/FGEN3670I.xml';", ConTimer);
    // 引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    // 出力
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3670, xmlReqAry, xmlResAry,
         1) == false) {
        return false;
    }

    //ﾌｧｲﾙﾊﾟｽの退避
    frm.strFilePath.value = funXmlRead(xmlFGEN3670O, "URLValue", 0);

    //ﾀﾞｳﾝﾛｰﾄﾞ画面の起動
    funFileDownloadUrlConnect(ConConectGet, frm);

    return true;
}

//========================================================================================
//次画面遷移処理
//作成者：t2nakamura
//作成日：2016/10/03
//概要  ：次画面に遷移する
//========================================================================================
function funNext() {
    //画面を閉じる
    close(self);
    return true;
}

//========================================================================================
//発注先マスター情報を変更した場合更新フラグ2をたてる
//作成者：t2nakamura
//作成日：2016/09/15
//引数  ：なし
//概要  ：DB登録、更新フラグをたてる
//========================================================================================
function funHattyuuUpdate(row) {

	var frm = document.frm00;   //ﾌｫｰﾑへの参照

	// 発注先コード設定により発注先名を設定する。
	if ("cd_literal" == event.srcElement.name) {
		var cd_literal = document.getElementById("cd_literal-" + row).value;
		if (cd_literal == "") {
			funErrorMsgBox("発注先コードを入力してください");
			return false;
		} else {
			if (isNaN(cd_literal)) {
				 funErrorMsgBox("数値を入力してください。");
				 return false;
			}
		}
		document.getElementById("cd_literal-" + row).value = fillsZero(cd_literal, 6)
		// 発注先名取得
		funChangeCategory(row);
	}

	// 発注先表示順数値以外エラー
	if ("no_sort" == event.srcElement.name) {
		var no_sort = document.getElementById("no_sort-" + row).value;

		if (no_sort != "") {

			for (var i = 0; i < no_sort.length; i++) {
				 if (parseInt(no_sort.charAt(i))){

				 } else {
					 funErrorMsgBox("数値を入力してください。");
					 return false;
				 }
			}
		} else {
			funErrorMsgBox("発注先表示順を入力してください。");
		}
	}

	// 担当者表示順数値以外エラー
	if ("no_2nd_sort" == event.srcElement.name) {
		var no_2nd_sort = document.getElementById("no_2nd_sort-" + row).value;

		if (no_2nd_sort != "") {

			for (var i = 0; i < no_2nd_sort.length; i++) {
				 if (parseInt(no_2nd_sort.charAt(i))){

				 } else {
					 funErrorMsgBox("数値を入力してください。");
					 return false;
				 }
			}
		}  else {
			funErrorMsgBox("担当者表示順を入力してください。");
		}
	}

	var obj = document.getElementById("update_flgl-" + row);
	// 新しく行を追加したものをインサートフラグに設定
	if (obj.value == 1) {
		obj.value = 1;
	} else {
		obj.value=2;
	}


    // 担当者
    var  nm_2nd_literal = document.getElementById("nm_2nd_literal-" + row);
	if (nm_2nd_literal.name == "nm_2nd_literal") {
	    var nm_2nd_literalValue = document.getElementById("nm_2nd_literal-" + row).value;
	    var formatNm_2nd_literalValue =nm_2nd_literalValue.replace(/^\s+|\s+$/g, "");
	    document.getElementById("nm_2nd_literal-" + row).value = formatNm_2nd_literalValue;
	}


//	// 発注先コード半角に変換
//	var cd_literal = document.getElementById("cd_literal-" + row);
//
//	if ( !cd_literal.value.match(/^(\w| |'|,|&)+$/) ){
//
//		if (cd_literal.name == "cd_literal") {
//			 var cd_literalValue = document.getElementById("cd_literal-" + row).value;
//			 // 半角変換
//			  var halfVal = cd_literalValue.replace(/[！-～]/g,
//			    function( tmpStr ) {
//			      // 文字コードをシフト
//			      return String.fromCharCode( tmpStr.charCodeAt(0) - 0xFEE0 );
//			    }
//			  );
//			  document.getElementById("cd_literal-" + row).value = halfVal;
//
//		}
//	}
//	// 発注先表示順半角に変換
//	var no_sort = document.getElementById("no_sort-" + row);
//
//	if ( !no_sort.value.match(/^(\w| |'|,|&)+$/) ){
//
//		if (no_sort.name == "no_sort") {
//			 var no_sortValue = document.getElementById("no_sort-" + row).value;
//			 // 半角変換
//			  var halfVal = no_sortValue.replace(/[！-～]/g,
//			    function( tmpStr ) {
//			      // 文字コードをシフト
//			      return String.fromCharCode( tmpStr.charCodeAt(0) - 0xFEE0 );
//			    }
//			  );
//			  document.getElementById("no_sort-" + row).value = halfVal;
//
//		}
//	}
//	// 担当者表示順半角に変換
//	var no_2nd_sort = document.getElementById("no_2nd_sort-" + row);
//
//	if ( !no_2nd_sort.value.match(/^(\w| |'|,|&)+$/) ){
//
//		if (no_2nd_sort.name == "no_2nd_sort") {
//			var no_2nd_sortValue = document.getElementById("no_2nd_sort-" + row).value;
//			 // 半角変換
//			  var halfVal = no_2nd_sortValue.replace(/[！-～]/g,
//			    function( tmpStr ) {
//			      // 文字コードをシフト
//			      return String.fromCharCode( tmpStr.charCodeAt(0) - 0xFEE0 );
//			    }
//			  );
//			  document.getElementById("no_sort-" + row).value = halfVal;
//
//		}
//	}
	return true;

}
//========================================================================================
//発注先マスター情報を変更した場合更新フラグ1をたてる
//作成者：t2nakamura
//作成日：2016/09/15
//引数  ：なし
//概要  ：DB登録、登録フラグをたてる
//========================================================================================
function insertFlg(row) {

	var obj = document.getElementById("update_flgl-" + row);
	obj.value=1;


}

//========================================================================================
//検索用の発注先コード、発注先名を変更した場合、Excelボタン非活性処理
//作成者：t2nakamura
//作成日：2016/10/30
//引数  ：なし
//概要  ：一覧の情報を初期化する
//========================================================================================
function hattyuusakiFocusOut() {

	if (rowcnt != 0) {
		// 	「Excel」ボタン非活性
		document.getElementById("btnExcel").disabled = true;
	}

}

//========================================================================================
//一覧クリア処理
//作成者：t2nakamura
//作成日：2016/09/13
//引数  ：なし
//概要  ：一覧の情報を初期化する
//========================================================================================
function funClearList() {

 //一覧のｸﾘｱ
 xmlFGEN3580O.src = "";
 tblList.style.display = "none";
 var detail = document.getElementById("detail");
 while(detail.firstChild){
     detail.removeChild(detail.firstChild);
 }
}

//========================================================================================
//チェックボックス処理
//作成者：t2nakamura
//作成日：2016/09/13
//引数  ：なし
//概要  ：チェックボックスON・OFF
//========================================================================================
function funEnterChk(row){

	if (frm.che[row].checked) {
		frm.che[row].checked==false;
	} else {
		frm.che[row].checked==true;
	}
}



//========================================================================================
//XMLファイルに書き込み
//作成者：t2nakamura
//作成日：2016/09/12
//引数  ：①XmlId  ：XMLID
//    ：②reqAry ：機能ID別送信XML(配列)
//    ：③Mode   ：処理モード
//        1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
//戻り値：なし
//概要  ：機能IDごとのXMLファイルに引数を設定する
//========================================================================================
function funReadyOutput(XmlId, reqAry, mode, row, no) {

 var frm = document.frm00;    //ﾌｫｰﾑへの参照
 var i;

 for (i = 0; i < reqAry.length; i++) {
     //画面初期表示
     if (XmlId.toString() == "RGENAAA") {
    	//USERINFO
         funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
         funXmlWrite(reqAry[i], "id_user", "", 0);

     // 検索ボタン押下
     } else if (XmlId.toString() == "RGEN3580"){
         switch (i) {
             case 0:    //USERINFO
                 funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                 funXmlWrite(reqAry[i], "id_user", "", 0);
                 break;
             case 1:    //SA890
            	// カテゴリに発注先を設定
                 funXmlWrite(reqAry[i], "cd_category", 'C_hattyuusaki', 0);
                 funXmlWrite(reqAry[i], "cd_literal", frm.cdhattyu.value, 0);
                 funXmlWrite(reqAry[i], "nm_literal", frm.nmhattyu.value, 0);
                 break;
         }

     //登録
     }  else if (XmlId.toString() == "RGEN3630"){

    	 ;
         switch (i) {
             case 0:    //USERINFO
                 funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                 funXmlWrite(reqAry[i], "id_user", "", 0);
                 break;
             case 1:    //RGEN3630
            	 var gyouCnt = 0;
            	 for (var y = 0; y < row; y++) {
            			//var checkbox = document.getElementById("check-" + y);
            			var updateFlg = document.getElementById("update_flgl-" + y);

//            		 if (checkbox.checked) {
//     		            if(y != 0){
//    		            	funAddRecNode_Tbl(reqAry[i], "FGEN3630", "table");
//    		            }
//
//    	        		 // 処理区分：3
//            			 funXmlWrite_Tbl(reqAry[i], "table", "shoriKbn", 3, gyouCnt);
//    	        		 // 発注先コード
//            			 funXmlWrite_Tbl(reqAry[i], "table", "cd_literal", document.getElementById("cd_literal-" + y).value, gyouCnt);
//    	        		 // 発注先名
//            			 funXmlWrite_Tbl(reqAry[i], "table", "nm_literal", document.getElementById("nm_literal-" + y).value, gyouCnt);
//    	                 // 発注先名表示順
//            			 funXmlWrite_Tbl(reqAry[i], "table", "no_sort", document.getElementById("no_sort-" + y).value, gyouCnt);
//    	        		 // 担当者
//            			 funXmlWrite_Tbl(reqAry[i], "table", "cd_2nd_literal", document.getElementById("hiddenCd_2nd_literal-" + y).value, gyouCnt);
//    	                 // 担当者順
//            			 funXmlWrite_Tbl(reqAry[i], "table", "no_2nd_sort", document.getElementById("no_2nd_sort-" + y).value, gyouCnt);
//    	        		 // メールアドレス
//            			 funXmlWrite_Tbl(reqAry[i], "table", "mail_address", document.getElementById("mail_address-" + y).value, gyouCnt);
//    	        		 // 未使用
//    	                 var flg_mishiyo = document.getElementById("flg_mishiyo-" + y);
//    	                 if (flg_mishiyo.checked) {
//    	                	 funXmlWrite_Tbl(reqAry[i], "table", "flg_mishiyo", 1, gyouCnt);
//    	                 } else {
//    	                	 funXmlWrite_Tbl(reqAry[i], "table", "flg_mishiyo", 0, gyouCnt);
//    	                 }
//
//    	                 gyouCnt++;

            		// }

            		 if (updateFlg.value == 1) {
            				// 登録
            				if (funHattyuusakiDifferenceCheck(y)) {
            		            if(gyouCnt != 0){
            		            	funAddRecNode_Tbl(reqAry[i], "FGEN3630", "table");
            		            }

	           	        		 // 処理区分：1
            					funXmlWrite_Tbl(reqAry[i], "table", "shoriKbn", 1, gyouCnt);
            					// 明細番号
            					funXmlWrite_Tbl(reqAry[i], "table", "meisaiNum", y, gyouCnt);
	           	        		 // 発注先コード
            					funXmlWrite_Tbl(reqAry[i], "table", "cd_literal", document.getElementById("cd_literal-" + y).value, gyouCnt);
            					var table = document.getElementById("tblList");
            					var nmLiteralValue = table.rows.item(y).cells(1).innerText;
	           	        		 // 発注先名
            					funXmlWrite_Tbl(reqAry[i], "table", "nm_literal",nmLiteralValue, gyouCnt);
	           	                 // 発注先名表示順
            					funXmlWrite_Tbl(reqAry[i], "table", "no_sort", document.getElementById("no_sort-" + y).value, gyouCnt);
	           	        		 // 担当者
            					funXmlWrite_Tbl(reqAry[i], "table", "nm_2nd_literal", document.getElementById("nm_2nd_literal-" + y).value, gyouCnt);
	           	                 // 担当者順
            					funXmlWrite_Tbl(reqAry[i], "table", "no_2nd_sort", document.getElementById("no_2nd_sort-" + y).value, gyouCnt);
	           	        		 // メールアドレス
            					funXmlWrite_Tbl(reqAry[i], "table", "mail_address", document.getElementById("mail_address-" + y).value, gyouCnt);
	           	        		 // 未使用
	           	                 var flg_mishiyo = document.getElementById("flg_mishiyo-" + y);
	           	                 if (flg_mishiyo.checked) {
	           	                	funXmlWrite_Tbl(reqAry[i], "table", "flg_mishiyo", 2,  gyouCnt);
	           	                 } else {
	           	                	funXmlWrite_Tbl(reqAry[i], "table", "flg_mishiyo", 1,  gyouCnt);
	           	                 }
            				} else {
            					// エラー処理
            					funErrorMsgBox("発注先コードが同じ場合、別の発注先名、発注先表示順は入力できません。");
            					return false;

            				}

            				 gyouCnt++;

            		 } else if (updateFlg.value == 2) {

            				// 更新
            				if (funHattyuusakiDifferenceCheck(y)) {
            		            if(gyouCnt != 0){
            		            	funAddRecNode_Tbl(reqAry[i], "FGEN3630", "table");
            		            }
	           	        		 // 処理区分：2
            					funXmlWrite_Tbl(reqAry[i], "table", "shoriKbn", 2, gyouCnt);
            					// 明細番号
               					funXmlWrite_Tbl(reqAry[i], "table", "meisaiNum", y, gyouCnt);
	           	        		 // 発注先コード
            					funXmlWrite_Tbl(reqAry[i], "table", "hiddenCd_literal", document.getElementById("hiddenCd_literal-" + y).value, gyouCnt);
	           	        		 // 発注先コード
            					funXmlWrite_Tbl(reqAry[i], "table", "cd_literal",document.getElementById("cd_literal-" + y).value, gyouCnt);

            					var table = document.getElementById("tblList");
            					var nmLiteralValue = table.rows.item(y).cells(1).innerText;
	           	        		 // 発注先名
            					funXmlWrite_Tbl(reqAry[i], "table", "nm_literal",nmLiteralValue, gyouCnt);
	           	                 // 発注先名表示順
            					funXmlWrite_Tbl(reqAry[i], "table", "no_sort", document.getElementById("no_sort-" + y).value, gyouCnt);
	           	        		 // 担当者コード
            					funXmlWrite_Tbl(reqAry[i], "table", "cd_2nd_literal", document.getElementById("hiddenCd_2nd_literal-" + y).value, gyouCnt);
	           	        		 // 担当者
            					funXmlWrite_Tbl(reqAry[i], "table", "nm_2nd_literal", document.getElementById("nm_2nd_literal-" + y).value, gyouCnt);
	           	                 // 担当者順
            					funXmlWrite_Tbl(reqAry[i], "table", "no_2nd_sort", document.getElementById("no_2nd_sort-" + y).value, gyouCnt);
	           	        		 // メールアドレス
            					funXmlWrite_Tbl(reqAry[i], "table", "mail_address", document.getElementById("mail_address-" + y).value, gyouCnt);
	           	        		 // 未使用
	           	                 var flg_mishiyo = document.getElementById("flg_mishiyo-" + y);
	           	                 if (flg_mishiyo.checked) {
	           	                	funXmlWrite_Tbl(reqAry[i], "table", "flg_mishiyo", 2, gyouCnt);
	           	                 } else {
	           	                	funXmlWrite_Tbl(reqAry[i], "table", "flg_mishiyo", 1, gyouCnt);
	           	                 }
            				} else {
            					// エラー処理
            					funErrorMsgBox("発注先コードが同じ場合、別の発注先名、発注先表示順は入力できません。");
            					return false;
            				}

            				 gyouCnt++;
            		 }


            	 }
            	 // 登録、更新データがない場合メッセージ表示
            	 if (gyouCnt == 0) {
            		 funClearRunMessage();
            		 funErrorMsgBox("明細データは変更されていません。");
            		 return false;
            	 }
            	 break;
         }


     } else if (XmlId.toString() == "RGEN3670"){

         switch (i) {
         case 0:    //USERINFO
             funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
             funXmlWrite(reqAry[i], "id_user", "", 0);
             break;
         case 1:
        	//RGEN3670
             funXmlWrite(reqAry[i], "cdhattyu", document.getElementById("cdhattyu").value, 0);
             funXmlWrite(reqAry[i], "nmhattyu", document.getElementById("nmhattyu").value, 0);

             break;
         }
     }
     // 発注先コード設定
     else if (XmlId.toString() == "RGEN3650"){

         switch (i) {
         case 0:    //USERINFO
             funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
             funXmlWrite(reqAry[i], "id_user", "", 0);
             break;
         case 1:
        	//RGEN3650
       		 // 発注先コード
        	 funXmlWrite(reqAry[i], "cd_literal",document.getElementById("cd_literal-" + no).value, 0);

             break;
         }
     }
 }

 return true;

}

//========================================================================================
//クリア処理
//作成者：t2nakamura
//作成日：2016/09/15
//引数  ：なし
//概要  ：「行追加」～「削除」ボタンを非活性にする
//========================================================================================
function funDisBtn() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照

	frm.btnInsert.disabled = true;
	frm.btnExcel.disabled = true;
	frm.btnExcel.disabled = true;
	frm.btnLineAdd.disabled = true;
}

//========================================================================================
//一覧選択処理
//作成者：H.Shima
//作成日：2014/9/12
//引数  ：インデックス
//概要  ：選択行をハイライト
//========================================================================================
function clickItiran(row){
 funSetCurrentRow(row);
}
