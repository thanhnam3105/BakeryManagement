//データ保持
var cd_kaisha = 0;		// 会社コード

//========================================================================================
// 初期表示処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

    //画面設定
    funInitScreen(ConDesignSpaceDLId);

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //一覧のｸﾘｱ
    funClearList();

    //画面情報を取得・設定
    if (funGetInfo(1) == false) {
        funClearRunMessage();
    	return false;
    }

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    return true;
}

//========================================================================================
// 画面情報取得処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：①mode  ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：ユーザのセッション情報、コンボボックスの情報を取得する
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

	var XmlId = "RGEN3250";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"SA290","FGEN3250");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA290I,xmlFGEN3250I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA290O,xmlFGEN3250O);


    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    // ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3250, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

	// ﾕｰｻﾞ情報表示
	funInformationDisplay(xmlResAry[1], 2, "divUserInfo");
	// ﾕｰｻﾞの会社コード取得
	cd_kaisha = funXmlRead(xmlResAry[1], "cd_kaisha", 0);

	// 製造工場（ログインユーザの会社の部署一覧：空白あり）
	funCreateComboBox(frm.ddlSeizoKojo, xmlResAry[2], 2, 2);

	// 種類（空白あり）
	funCreateComboBox(frm.ddlSyurui, xmlResAry[3], 1, 2);

	// モーダルウィンドウ：資材手配入力からの遷移の場合
    if (!!window.dialogArguments) {
    	var win = window.dialogArguments[0];
    	var argKojo = window.dialogArguments[1];	// 製造工場
    	var argShokuba = window.dialogArguments[2];	// 職場
    	var argLine = window.dialogArguments[3];	// 製造ライン

    	// 条件が選択されている時、selectedIndexを設定する
    	if (argKojo != "") {
    		// 製造工場の選択
    		funSetIndex(frm.ddlSeizoKojo, xmlSA290O, "cd_busho", argKojo);
    		// 職場コンボボックスの作成
    		funChangeKojo();

    		if (argShokuba != "") {
    			// 職場の選択
    			funSetIndex(frm.ddlShokuba, xmlFGEN3230O, "cd_shokuba", argShokuba);
    			// ラインコンボボックスの作成
    			funChangeShokuba();

        		if (argLine != "") {
        			// ラインの選択
        			funSetIndex(frm.ddlLine, xmlFGEN3240O, "cd_line", argLine);
        		}
    		}

    		// 引き継いだ条件で検索
    		funSearch()
    	}
    }

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

	// デザイン情報一覧の初期化
//	funClearList();

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


	// デザイン情報一覧の初期化
//	funClearList();

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
// 製造ラインコンボボックス
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：なし
// 概要  ：一覧初期化処理
//========================================================================================
function funChangeLine() {

	// デザイン情報一覧の初期化
//	funClearList();
}

//========================================================================================
// 種類コンボボックス
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：なし
// 概要  ：一覧初期化処理
//========================================================================================
function funChangeSyurui() {

	// デザイン情報一覧の初期化
//	funClearList();
}


//========================================================================================
// 検索条件のチェック処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：なし
// 戻り値：検索条件を何も選択していない時：false
//         どれかの条件を選択時：true
// 概要  ：検索条件をチェック
//========================================================================================
function funSelChk() {

	var frm = document.frm00;   //ﾌｫｰﾑへの参照

	// 製造工場
	if (frm.ddlSeizoKojo.selectedIndex > 0) {
		return true;
	}

	// 職場
	if (frm.ddlShokuba.selectedIndex > 0) {
		return true;
	}

	// 製造ライン
	if (frm.ddlLine.selectedIndex > 0) {
		return true;
	}
	// 種類
	if (frm.ddlSyurui.selectedIndex> 0) {
		return true;
	}

	return false;
}

//========================================================================================
// 一覧クリア処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：なし
// 概要  ：一覧の情報を初期化する
//========================================================================================
function funClearList() {

	//一覧のｸﾘｱ
	xmlFGEN3260O.src = "";
	// 非表示
	tblList.style.display = "none";

}


//========================================================================================
// 検索ボタン押下処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：なし
// 概要  ：デザインスペースデータの検索を行う
//========================================================================================
function funSearch() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照

    // 条件が選択されているか？
	if (funSelChk() == "") {
		funErrorMsgBox(E000031);
		return false;
	}

    // データ取得
    funDataSearch();

}

//========================================================================================
// 検索処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：なし
// 概要  ：デザインスペースデータの検索を行う
//========================================================================================
function funDataSearch() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	var XmlId = "RGEN3260";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3260");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3260I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3260O);

	//選択行の初期化
	funSetCurrentRow("");

	//一覧のｸﾘｱ
	funClearList();

	//引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}

	//処理中ﾒｯｾｰｼﾞ表示
	funShowRunMessage();

	//検索条件に一致するデザインスペースデータを取得
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3260, xmlReqAry, xmlResAry, 1) == false) {
		funClearRunMessage();
		return false;
	}

	// デザインスペースデータ件数のチェック
	if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
		//xmlFGEN3260O より明細表示
		tblList.style.display = "block";

	} else {
		//非表示：対象データなし
		tblList.style.display = "none";
		//対象ファイルがありません。
		funErrorMsgBox(E000030);
	}


	//処理中ﾒｯｾｰｼﾞ非表示
	funClearRunMessage();
	return true;

}

//========================================================================================
// ダウンロードボタン押下処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：なし
// 概要  ：選択ファイルダウンロード
//========================================================================================
function funDownLoad() {

	var frm = document.frm00;          // ﾌｫｰﾑへの参照
	var chkbox = frm.chk;              // チェックボックスオブジェクト

	var subFolder = "";					// サブフォルダー名（条件コードを"_"で繋ぐ）
    var fileNm = "";					// ダウンロードするファイル名（":::"で区切る）

    // 一覧表が表示されているか
    if(tblList.style.display == "none") {
		//メッセージ表示
    	funErrorMsgBox(E000030);
		return;
    }

    // チェックされているファイル名を取得
	if (!chkbox.length) {
		// 1件の時 undefined になり、Index は付かない
		if(chkbox.checked) {
			if (funGetRowdata(0)) {
				// サブフォルダー名：条件コードに種類コードを追加
				subFolder = funGetRowdata.subFolder;
				// ダウンロードファイル名
				fileNm = funGetRowdata.fileNm;
			}
		}

	} else {
		for(var i = 0; i < chkbox.length; i++){
			if(chkbox[i].checked) {
				if (funGetRowdata(i)) {
					// サブフォルダー名：会社コード + 条件コード（製造工場_職場_製造ライン_）に種類コードを追加
					subFolder += funGetRowdata.subFolder + ":::";
					// ダウンロードファイル名
					fileNm += funGetRowdata.fileNm + ":::";
				}
			}
		}
	}

	// ファイルが選択されていない場合は処理中止
	if(fileNm == ""){
		//メッセージ表示
		funErrorMsgBox("一覧表から１件以上選択してください。");
		return;
	}

	//** ﾌｧｲﾙﾊﾟｽの退避 **/
	// ダウンロードパス（const定義名）
	frm.strServerConst.value = "UPLOAD_DESIGNSPACE_FOLDER";
	// サブフォルダー名
	frm.strSubFolder.value = subFolder;
	// ダウンロードファイル名（":::"で区切る）
	frm.strFilePath.value = fileNm;

	// ﾀﾞｳﾝﾛｰﾄﾞ画面の起動
	funFileDownloadUrlConnect(ConConectGet, frm);

}

//========================================================================================
// ダウンロードボタン押下処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 引数  ：row       ：行番号
// 戻り値：subFolder ：サブフォルダー名
//         fileNm    ：ダウンロードファイル名
// 概要  ：選択行のデータを取得し、サブフォルダー名、ファイル名を返す
//========================================================================================
function funGetRowdata(row) {

	// 検索条件コード（製造工場_職場_製造ライン_）
	var kensaku_code = funXmlRead(xmlFGEN3260O, "cd_seizokojo", row) + "_" + funXmlRead(xmlFGEN3260O, "cd_shokuba", row) + "_" + funXmlRead(xmlFGEN3260O, "cd_line", row) + "_";

	// サブフォルダー：会社コードを先頭に追加
//	funGetRowdata.subFolder = kensaku_code + funGetXmldata(xmlFGEN3250O, "nm_2nd_literal", funXmlRead(xmlFGEN3260O, "nm_syurui", row), "cd_2nd_literal") ;
	funGetRowdata.subFolder = cd_kaisha + "_" + kensaku_code + funXmlRead(xmlFGEN3260O, "cd_literal", row) + "_" + funXmlRead(xmlFGEN3260O, "cd_2nd_literal", row);

	// ダウンロードファイル名
	funGetRowdata.fileNm = funXmlRead(xmlFGEN3260O, "nm_file", row);

	return true;
}

//========================================================================================
// XMLファイルに書き込み
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：①XmlId  ：XMLID
//       ：②reqAry ：機能ID別送信XML(配列)
//       ：③Mode   ：処理モード
//         1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：なし
// 概要  ：機能IDごとのXMLファイルに引数を設定する
//========================================================================================
function funReadyOutput(XmlId, reqAry, mode) {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	var i;

	for (i = 0; i < reqAry.length; i++) {
		//画面初期表示
		if (XmlId.toString() == "RGEN3250"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //SA290
				funXmlWrite(reqAry[i], "id_user", "", 0);
				funXmlWrite(reqAry[i], "id_gamen", ConGmnIdDesignSpaceDL, 0);
				break;
			case 2:    //FGEN3250（種類を取得）
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
				funXmlWrite(reqAry[i], "cd_seizokojo", frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value, 0);
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
				funXmlWrite(reqAry[i], "cd_seizokojo", frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value, 0);
				funXmlWrite(reqAry[i], "cd_shokuba", frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value, 0);
				break;
			}

			//検索ボタン押下
		} else if (XmlId.toString() == "RGEN3260"){
			var cd_seizokojo = "";
			var cd_shokuba = "";
			var cd_line = "";
			var cd_syurui = "";

			// 選択されている条件を設定
			if (frm.ddlSeizoKojo.selectedIndex > 0) {
				cd_seizokojo = frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value;
			}
			if (frm.ddlShokuba.selectedIndex > 0) {
				cd_shokuba = frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value;
			}
			if (frm.ddlLine.selectedIndex > 0) {
				cd_line = frm.ddlLine.options[frm.ddlLine.selectedIndex].value;
			}
			if (frm.ddlSyurui.selectedIndex > 0) {
				// カテゴリを "_"で結合
				cd_syurui = frm.ddlSyurui.options[frm.ddlSyurui.selectedIndex].value;
			}

			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //FGEN3260
				funXmlWrite(reqAry[i], "cd_seizokojo", cd_seizokojo, 0);
				funXmlWrite(reqAry[i], "cd_shokuba", cd_shokuba, 0);
				funXmlWrite(reqAry[i], "cd_line", cd_line, 0);
				funXmlWrite(reqAry[i], "cd_syurui", cd_syurui, 0);
				break;
			}
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
//       ：⑤idx      ：選択状態のIndex
//概要  ：コンボボックスを作成する
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
	case 1:    //ﾘﾃﾗﾙﾏｽﾀ
		atbName = "nm_2nd_literal";
		atbCd = "cd_2nd_literal";
		break;
	case 2:    //部署マスタ
		atbName = "nm_busho";
		atbCd = "cd_busho";
		break;
	case 3:    //職場マスタ
		atbName = "nm_shokuba";
		atbCd = "cd_shokuba";
		break;
	case 4:    //ラインマスタ
		atbName = "nm_line";
		atbCd = "cd_line";
		break;
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
// XMLデータより検索行のコード←→名前 変換
// 作成者：E.Kitazawa
// 作成日：2014/09/17
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

	for (var i = 0; i < reccnt; i++) {
		// 検索項目値が等しい場合、Index設定
		if (funXmlRead(xmlData, komoku, i) == text) {
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
// 終了ボタン押下
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 概要  ：終了処理
//========================================================================================
function funEndClick(){

	//終了処理
	funEnd();
}

//========================================================================================
// 終了処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 概要  ：終了処理
//========================================================================================
function funEnd(){
    //画面を閉じる
    close(self);

//	var wUrl;
//	// 親ウィンドウの存在をチェック
//	if(window.opener && !window.opener.closed == true){
//		// モーダルで開いた時
//	    //画面を閉じる
//	    close(self);
//	} else {
//		// メニューからの遷移
//		wUrl = "../SQ170GenchoMenu/SQ170GenchoMenu.jsp";
//		// メニューに戻る
//		funUrlConnect(wUrl, ConConectPost, document.frm00);
//
//	}
	return true;
}
