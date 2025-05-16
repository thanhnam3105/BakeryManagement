//========================================================================================
// 共通変数
// 作成者：E.Kitazawa
// 作成日：2014/09/01
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
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

    //画面設定
    funInitScreen(ConDesignSpaceAddId);

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //一覧のｸﾘｱ
    funClearList();

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
// 画面情報取得処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：①mode  ：処理モード
//          1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：ユーザのセッション情報、コンボボックスの情報（部署、種類）を取得する
// セッション情報より前回Keyのデザインスペース一覧を取得（アップロード後の再ロード対応）
//========================================================================================
function funGetInfo(mode) {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	var XmlId = "RGEN3250";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"SA290","FGEN3250","FGEN3260");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA290I,xmlFGEN3250I,xmlFGEN3260I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA290O,xmlFGEN3250O,xmlFGEN3260O);

	//画面ロード時の検索キー
	var start_kojo ="";
	var start_syokuba = "";
	var start_line = "";

	// 引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
		return false;
	}

	// ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3250, xmlReqAry, xmlResAry, mode) == false) {
		return false;
	}

	// ﾕｰｻﾞ情報表示
	funInformationDisplay(xmlResAry[1], 2, "divUserInfo");
	// ﾕｰｻﾞの会社コード取得
	cd_kaisha = funXmlRead(xmlResAry[1], "cd_kaisha", 0);

	// ﾃﾞﾌｫﾙﾄのｺﾝﾎﾞﾎﾞｯｸｽ設定値を退避
	xmlSA290.load(xmlResAry[2]);

	// 種類
	xmlFGEN3250.load(xmlResAry[3]);

	// 製造工場（ログインユーザの会社の部署一覧）
	funCreateComboBox(frm.ddlSeizoKojo, xmlResAry[2], 2, 1);

	// デザインスペース情報
	if (funXmlRead(xmlResAry[4], "flg_return", 0) == "true") {
		// デザインスペース情報
		xmlFGEN3260.load(xmlResAry[4]);
		// アップロード後の再ロード
		start_kojo = funXmlRead(xmlResAry[4], "cd_seizokojo", 0);
		start_syokuba = funXmlRead(xmlResAry[4], "cd_shokuba", 0);
		start_line = funXmlRead(xmlResAry[4], "cd_line", 0);

		// 取得データの製造工場にIndexをセット（空白なし）
		funSetIndex(frm.ddlSeizoKojo, xmlSA290, "cd_busho", start_kojo, 0);
	}

	// 職場ｺﾝﾎﾞﾎﾞｯｸｽを設定
    funChangeKojo();

    if (start_syokuba) {
    	// 取得データの職場にIndexをセット（空白あり⇒空白なしに変更：2014/11/26）
//		funSetIndex(frm.ddlShokuba, xmlFGEN3230O, "cd_shokuba", start_syokuba, 1);
		funSetIndex(frm.ddlShokuba, xmlFGEN3230O, "cd_shokuba", start_syokuba, 0);
		// ラインｺﾝﾎﾞﾎﾞｯｸｽを設定
		funChangeShokuba();

    	// 取得データのラインにIndexをセット（空白あり⇒空白なしに変更：2014/11/26）
//		funSetIndex(frm.ddlLine, xmlFGEN3240O, "cd_line", start_line, 1);
		funSetIndex(frm.ddlLine, xmlFGEN3240O, "cd_line", start_line, 0);

		// 一覧表示：ボタンの活性化
		if (funGetLength(xmlFGEN3260) > 0) {
	        //表示
	        tblList.style.display = "block";
	        // デザインスペース情報一覧の作成
	        funCreateDesignSpaceList(xmlFGEN3260);

	    	// 実行可能ボタンを活性化
	    	frm.btnLineAdd.disabled = false;
	    	frm.btnLineDel.disabled = false;
	    	frm.btnInsert.disabled = false;
	    	frm.btnDelete.disabled = false;

		}
	}

	return true;
}

//========================================================================================
// 製造工場コンボボックス連動処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：なし
// 概要  ：製造工場に紐付く職場コンボボックスを生成する
//========================================================================================
function funChangeKojo() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	var XmlId = "RGEN3230";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3230");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3230I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3230O);

//	// デザイン情報一覧の初期化
//	funClearList();
	// ボタンを非活性
	funDisBtn();

	//引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}


	//情報を取得
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3230, xmlReqAry, xmlResAry, 1) == false) {
		return false;
	}

	//職場ｺﾝﾎﾞﾎﾞｯｸｽの作成：空白なしに変更（2014/11/26）
//	funCreateComboBox(frm.ddlShokuba, xmlResAry[2], 3 , 2);
	funCreateComboBox(frm.ddlShokuba, xmlResAry[2], 3 , 1);
	//ﾗｲﾝｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
//	funClearSelect(frm.ddlLine, 2);
	// ラインｺﾝﾎﾞﾎﾞｯｸｽを設定
	funChangeShokuba();

	return true;

}

//========================================================================================
// 職場コンボボックス連動処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：なし
// 概要  ：職場に紐付く製造ラインコンボボックスを生成する
//========================================================================================
function funChangeShokuba() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	var XmlId = "RGEN3240";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3240");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3240I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3240O);


//	// デザイン情報一覧の初期化
//	funClearList();
	// ボタンを非活性
	funDisBtn();

	// 空白なしの為、修正（2014/11/26）
//	if (frm.ddlShokuba.selectedIndex == 0) {
	if (frm.ddlShokuba.selectedIndex < 0) {
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

	//ｺﾝﾎﾞﾎﾞｯｸｽの作成：空白なしに変更（2014/11/26）
//	funCreateComboBox(frm.ddlLine, xmlResAry[2], 4 , 2);
	funCreateComboBox(frm.ddlLine, xmlResAry[2], 4 , 1);

	return true;

}


//========================================================================================
// 製造ラインコンボボックス
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：なし
// 概要  ：一覧初期化処理
//========================================================================================
function funChangeLine() {

//	// デザイン情報一覧の初期化
//	funClearList();
	// ボタンを非活性
	funDisBtn();
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
	if (frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value == "") {
		funSelChk.Msg = "製造工場が選択されていません。";
		return "";
	}
	strRet += frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value + "_";

	// 職場
	if (frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value == "") {
		funSelChk.Msg = "職場が選択されていません。";
		return "";
	}
	strRet += frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value + "_";

	// 製造ライン
	if (frm.ddlLine.options[frm.ddlLine.selectedIndex].value == "") {
		funSelChk.Msg = "製造ラインが選択されていません。";
		return "";
	}
	strRet += frm.ddlLine.options[frm.ddlLine.selectedIndex].value + "_";

	return strRet;
}

//========================================================================================
// 検索ボタン押下処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：なし
// 概要  ：デザインスペースの検索を行う
//========================================================================================
function funSearch() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    // 条件が選択されているか？
    // （資材手配入力での検索では必須条件でないのでSQLのInputCheckにいれない）
	if (funSelChk() == "") {
		funErrorMsgBox(funSelChk.Msg);
		return false;
	}

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //検索処理
    //処理中ウィンドウ表示の為、setTimeoutで処理予約
    setTimeout(function(){ funDataSearch() }, 0);

}

//========================================================================================
// 検索処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
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
        //一覧のｸﾘｱ
        funClearList();
        funClearRunMessage();
        return false;
    }

    //検索条件に一致するデザインスペースデータを取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3260, xmlReqAry, xmlResAry, 1) == false) {
        //一覧のｸﾘｱ
        funClearList();
        funClearRunMessage();
        return false;
    }

    //ﾃﾞｰﾀ件数のﾁｪｯｸ
    if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
        //表示
        tblList.style.display = "block";

        // デザインスペース情報一覧の作成
        funCreateDesignSpaceList(xmlResAry[2]);

    } else {
    	// 対象データなし
    	funErrorMsgBox(E000030);

        //非表示：対象データなし
        tblList.style.display = "none";
        // 検索時の更新日時
        MAX_DtKoshin = "";
    }

	// 実行可能ボタンを活性化
	frm.btnLineAdd.disabled = false;
	frm.btnLineDel.disabled = false;
	frm.btnInsert.disabled = false;
	frm.btnDelete.disabled = false;

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();
	return true;

}

//========================================================================================
// 行追加ボタン押下処理
// 作成者：E.Kitazawa
// 作成日：2014/09/11
// 引数  ：なし
// 概要  ：デザインスペース情報に行追加する
//========================================================================================
function funLineAdd() {

	// 条件が選択していない時（検索後しか来ない）
	if (funSelChk() == "") {
//		funErrorMsgBox(E000031);
		funErrorMsgBox(funSelChk.Msg);
		return false;
	}

    //デザインスペース情報一覧表示
    tblList.style.display = "block";

    // 明細行を追加
    funAddDesignSpace(++rowcnt, "");

    // 種類
    var ddlSyurui = document.getElementById("ddlSyurui-" + rowcnt);

	// 種類のセレクト値設定（空白あり）
    funCreateComboBox(ddlSyurui, xmlFGEN3250O, 1, 2);
}

//========================================================================================
// 行削除ボタン押下処理
// 作成者：E.Kitazawa
// 作成日：2014/09/11
// 引数  ：なし
// 概要  ：デザインスペース情報一覧の指定行を非表示にする
//         保存データがある時：ＤＢ削除、データ削除
//========================================================================================
function funLineDel() {

	var frm = document.frm00;   //ﾌｫｰﾑへの参照
	var retBool = false;		//削除ファイルがある時：削除結果

	// 行選択されていない場合
    if(funGetCurrentRow().toString() == ""){
    	funErrorMsgBox(E000002);
        return false;
    }

    // 行番号
    var row_no = funGetCurrentRow() + 1;
	// 保存ファイルのサブフォルダー名
	var subFolder = "";

	// 保存ファイル名を取得（非表示、種類コードを付加）
	var fileNm = document.getElementById("nm_file-" + row_no);

	// 選択行にファイルが保存されている場合、データ削除
	if (fileNm.value != "") {
		// 削除確認
		if (funConfMsgBox(I000004) != ConBtnYes) {
			return;
		}

		// 保存ファイルのサブフォルダー名を取得（選択番号を_で繋ぐ）
		subFolder = funSelChk();
		// 条件が選択されていない場合
		if (subFolder == "") {
			funErrorMsgBox(funSelChk.Msg);
			return false;
		}

		//** ﾌｧｲﾙﾊﾟｽの退避 **/
		// アップロードパス（const定義名）
		frm.strServerConst.value = "UPLOAD_DESIGNSPACE_FOLDER";

		// 保存ファイル名から種類コードを分割
		//  [1]：種類コード（カテゴリ１_カテゴリ２）、 [2]：ファイル名
		var strTmp = fileNm.value.split("\\");

		// 削除ファイルパス（サブフォルダー名：条件コード含む）
		//  フォルダー毎削除するので、ファイル名は渡さない
		frm.strFilePath.value = subFolder + strTmp[0];


		// 排他処理：検索時の更新日時から変更があったら処理中止
		if (!funKoshinChk()) {
			return false;
		}

		// ファイル削除（サーバー）
		funFileDeleteUrlConnect(ConConectGet, frm);

		// ファイル削除（ＤＢ）
		retBool = funDesignSpaceDelLine();

		fileNm.value = "";			// 保存ファイル名を消す
	}

	// reNumber が必要となる為、行番号は非表示
	var tr = document.getElementById("tr" + row_no);
	// 選択行を隠す
	tr.style.display = "none";

    //選択行の初期化
    funSetCurrentRow("");

	if (retBool) {
		//正常：削除完了
		funInfoMsgBox(I000007);

		// 再検索：対象行以外のデータの更新者・日時をUPDATEする為
		funSearch();
	}
}


//========================================================================================
// 登録ボタン押下処理
// 作成者：E.Kitazawa
// 作成日：2014/09/11
// 引数  ：なし
// 概要  ：デザインスペース情報の登録（洗い替え）を行う
//========================================================================================
function funUpdate() {

    var frm = document.frm00;   //ﾌｫｰﾑへの参照
    var upFildNm = "";          //アップロードするフィールド名
    var delFileNm = "";         //削除するファイル名
    var lstSyurui = {};         //登録する種類の配列（重複チェック用）
    var strMsg = ""				//確認メッセージ付加文字

    // 保存ファイルのサブフォルダー取得（工場、職場、製造ラインを_で繋ぐ）
    var subFolder = funSelChk();
    // 条件が選択されていない場合
	if (subFolder == "") {
//		funErrorMsgBox(E000031);
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
    for(var i = 1; i <= rowcnt; i++){
        // 行オブジェクト
    	var tr = document.getElementById("tr" + i);

    	// アップロードするファイル名
    	var fileName = document.getElementById("fileName-" + i);

    	// 表示ファイル名
    	var inputName = document.getElementById("inputName-" + i);
    	// 保存されているファイル名（非表示）
    	var nm_file = document.getElementById("nm_file-" + i);

        // 種類オブジェクト
        var ddlSyurui = document.getElementById("ddlSyurui-" + i);

    	// 種類コード（セレクト値）
        var selSyurui = ddlSyurui.options[ddlSyurui.selectedIndex].value;

    	// 表示行のみ処理する
    	if (tr.style.display != "none") {
    		// 表示行
    		displayln = true;

    		// 表示用inputファイル名が空白の場合、エラー（ＤＢ登録できない）
    		if (inputName.value == "") {
    			funErrorMsgBox(E000027);
    			return false;
    		}

    		// 種類が選択されていない
    		if (ddlSyurui.selectedIndex < 1) {
    			funErrorMsgBox("すべての種類を選択して下さい。");
    			return false;
    		}
    		// 同じ種類が選択されていない事！
    		if (lstSyurui[selSyurui] == null) {
    			lstSyurui[selSyurui] = ddlSyurui.options[ddlSyurui.selectedIndex].innerHTML;
    		} else {
    			// 種類の重複
    			funErrorMsgBox("選択した種類が重複しています。");
    			return false;
    		}
    		// ファイル名の長さチェック（ＤＢ制限）
			if (inputName.value.length > MAXLEN_FILENM) {
				funErrorMsgBox("ファイル名が長すぎます。（１００文字まで）：\\n" + inputName.value);
				return false;
			}
			// ファイル名の不正文字チェック
			if (!funChkFileNm(inputName.value)) {
    			funErrorMsgBox("ファイル名に不正文字が含まれています。：\\n" + inputName.value);
    			return false;
			}


    		// クリアボタンが押下されていないこと
    		if ((fileName.value != "") && (inputName.value != "")) {
    			// アップロード処理：サーバー渡し（":::"で区切る）
    			// フィールド名（アップロードチェック用）
    			upFildNm += "fileName-" + i + ":::";
    			// サブフォルダーに種類コードを追加
    			subFlst += subFolder + selSyurui + ":::";
    		}

    		// 変更前ファイルを削除
    		// ＤＢに保存されている 且つ 変更された赤字ファイルの時、
    		if ((nm_file.value != "") && (inputName.style.color == "red")) {
    			// サブフォルダー名を付加する。（":::"で区切る）
    			// nm_file には保存データの種類コードが付加済（"\\"で区切られている）
    			delFileNm += subFolder + nm_file.value + ":::";
    		}
    	}
    }

    // 表示行がない
	if (!displayln) {
    	funErrorMsgBox(E000030);
        return false;
	}

    if (upFildNm == "") {
    	strMsg = "（アップロード対象ファイルはありません。）"
    }
    // 登録確認
	if (funConfMsgBox(I000002 + strMsg) != ConBtnYes) {
		return;
	}

	// 排他処理：検索時の更新日時から変更があったら処理中止
	if (!funKoshinChk()) {
		return false;
	}


	//** ﾌｧｲﾙﾊﾟｽの退避 **/
    // アップロードパス（const定義名）
    frm.strServerConst.value = "UPLOAD_DESIGNSPACE_FOLDER";
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

    // デザインスペースファイルＤＢ更新処理
	// 選択条件でデザインスペースファイルを削除後、登録
	if (funDesignSpaceUpdate("RGEN3280")) {
        //正常登録
        funInfoMsgBox(I000005);
	}

}

//========================================================================================
// 削除ボタン押下処理
// 作成者：E.Kitazawa
// 作成日：2014/09/11
// 引数  ：なし
// 概要  ：指定条件のデザインスペース情報を削除する
//========================================================================================
function funDelete() {

    var frm = document.frm00;   //ﾌｫｰﾑへの参照
    var delFileNm = "";         //削除するファイル名

    // 表示行が無い場合、エラー
	var displayln = false;

    // 保存ファイルのサブフォルダー取得（工場、職場、製造ラインを_で繋ぐ）
    var subFolder = funSelChk();
    // 条件が選択されていない場合
	if (subFolder == "") {
//		funErrorMsgBox(E000031);
		funErrorMsgBox(funSelChk.Msg);
		return false;
	}

	// 一覧データがない
	if (rowcnt == 0) {
		funErrorMsgBox(E000030);
		return false;
	}

	// 表示されている一覧をチェック
    for(var i = 1; i <= rowcnt; i++){
        // 行オブジェクト
    	var tr = document.getElementById("tr" + i);

    	// 変更前ファイルを削除
    	var nm_file = document.getElementById("nm_file-" + i);
    	var strTmp = null;

    	// 表示行のみ処理する
    	if (tr.style.display != "none") {
    		// 表示行
    		displayln = true;

    		// 保存されているファイル名がある場合に削除（非表示）
    		if (nm_file.value != "") {
    			// nm_file には保存データの種類コードが付加済（"\\"で区切られている）
    			// 種類コードを分割 [1]：種類コード（カテゴリ１_カテゴリ２）、 [2]：ファイル名
    			strTmp = nm_file.value.split("\\");

    			// 削除ファイルパスにサブフォルダー名を付加する。（":::"で区切る）
    			//  フォルダー毎削除するので、ファイル名は渡さない
    			delFileNm += subFolder + strTmp[0] + ":::";
    		}
    	}
    }

    // 表示行がない
    if (!displayln) {
		funErrorMsgBox(E000030);
		return false;
    }

    // 削除確認
    if (funConfMsgBox(I000004) != ConBtnYes) {
    	return;
    }

    // 排他処理：検索時の更新日時から変更があったら処理中止
	if (!funKoshinChk()) {
		return false;
	}


    if (delFileNm != "") {
    	//** ﾌｧｲﾙﾊﾟｽの退避 **/
    	// アップロードパス（const定義名）
    	frm.strServerConst.value = "UPLOAD_DESIGNSPACE_FOLDER";
    	// 削除ファイルパス（":::"で区切る）
    	frm.strFilePath.value = delFileNm;

    	// サーバーファイルの削除
		funFileDeleteUrlConnect(ConConectGet, frm);

    }

    // デザインスペースファイルＤＢ更新処理
	// 選択条件のデザインスペースファイルを削除
	if (funDesignSpaceUpdate("RGEN3285")) {
        //正常：削除完了
        funInfoMsgBox(I000007);
	}

	// デザイン情報一覧の初期化
	funClearList();

}

//========================================================================================
// デザインスペースファイルDB更新処理
// 作成者：E.Kitazawa
// 作成日：2014/09/17
// 引数  ：XmlId     ：XmlId
// 概要  ：デザインスペースファイルDB更新の実行
//========================================================================================
function funDesignSpaceUpdate(XmlId){

	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3280");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3280I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3280O);

    // 処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

	//引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
	    //処理中ﾒｯｾｰｼﾞ非表示
	    funClearRunMessage();
		return false;
	}

	//デザインスペースファイルDB登録
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3280, xmlReqAry, xmlResAry, 1) == false) {
	    //処理中ﾒｯｾｰｼﾞ非表示
	    funClearRunMessage();
		return false;
	}

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();


    //完了ﾒｯｾｰｼﾞの表示
    dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);

    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {
    	return true;
    } else {
        //error
    	funErrorMsgBox(dspMsg);
        return false;
    }

}

//========================================================================================
// 行削除ボタン押下処理
// 作成者：E.Kitazawa
// 作成日：2014/09/17
// 引数  ：
// 概要  ：デザインスペースファイルDB削除処理の実行
//========================================================================================
function funDesignSpaceDelLine(){

	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	var XmlId = "RGEN3270";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3270");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3270I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3270O);

    // 処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

	//引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}


	//デザインスペースファイル行削除
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3270, xmlReqAry, xmlResAry, 1) == false) {
		return false;
	}

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    //完了ﾒｯｾｰｼﾞの表示

    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {
    	return true;

    } else {

        return false;
    }


}


//========================================================================================
// 一覧クリア処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：なし
// 概要  ：一覧の情報を初期化する
//========================================================================================
function funClearList() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

	// 一覧のｸﾘｱ
	xmlFGEN3260O.src = "";
	tblList.style.display = "none";
	// 明細行（読み込み分）
	var detail = document.getElementById("detail");
	// 明細データ削除
	while(detail.firstChild){
		detail.removeChild(detail.firstChild);
	}

	// 行カウントのクリア
	rowcnt = 0;
	// ボタンを非活性
	funDisBtn();

}

//========================================================================================
// クリア処理
// 作成者：E.Kitazawa
// 作成日：2014/11/27
// 引数  ：なし
// 概要  ：「行追加」～「削除」ボタンを非活性にする
//========================================================================================
function funDisBtn() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

	// ボタンの初期状態設定：「検索」押下まで非活性
	frm.btnLineAdd.disabled = true;
	frm.btnLineDel.disabled = true;
	frm.btnInsert.disabled = true;
	frm.btnDelete.disabled = true;

}

//========================================================================================
// 参考資料ボタン押下
// 作成者：E.Kitazawa
// 作成日：2014/09/01
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
// ＤＢ更新：排他制御
// 作成者：E.Kitazawa
// 作成日：2014/12/03
// 戻り値： true  :処理実行可
//          false：処理続行不可（更新されている）
// 概要  ：カテゴリデータの更新日時を取得
//========================================================================================
function funKoshinChk(){

	var XmlId = "RGEN3410";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3410");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3410I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3410O);

	//引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}

	//検索条件に一致するデザインスペースデータを取得
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3410, xmlReqAry, xmlResAry, 1) == false) {
		return false;
	}

	//ﾃﾞｰﾀﾁｪｯｸ：データが存在しなくても max_koshin に""が返る
	if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
		var max_koshin = funXmlRead(xmlResAry[2], "max_koshin", 0);
//		alert("検索時：" + MAX_DtKoshin + "   現在：" + max_koshin);

		// 1.検索時からデータが更新された
		// 2.検索時にデータが無かったのに作成されている
		// 3.検索時にデータがあったのに削除されている
		if (MAX_DtKoshin != max_koshin) {

			// エラーメッセージ表示
			funErrorMsgBox("既に更新されています。\\n再度検索し直してください。");

			return false;

		}

	}
	return true;


}

//========================================================================================
// 終了ボタン押下
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 概要  ：セッション情報をクリアする
//========================================================================================
function funEndClick(){

    var XmlId = "RGEN3265";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //検索条件に一致するデザインスペースデータを取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3260, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

	//終了処理
	funEnd();
}

//========================================================================================
// 終了処理
// 作成者：E.Kitazawa
// 作成日：2014/09/24
// 概要  ：終了処理
//========================================================================================
//function funEnd(){
//
//	var wUrl;
//
//	// 原調メニュー
//	wUrl = "../SQ170GenchoMenu/SQ170GenchoMenu.jsp";
//
//	// 遷移
//	funUrlConnect(wUrl, ConConectPost, document.frm00);
//
//	return true;
//}
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
		// メニューからの遷移
		wUrl = "../SQ170GenchoMenu/SQ170GenchoMenu.jsp";
		// メニューに戻る
		funUrlConnect(wUrl, ConConectPost, document.frm00);

	}
	return true;
}


//========================================================================================
// デザインスペース情報　テーブル一覧作成処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：xmlData    ：XMLデータ
// 概要  ：デザインスペース情報一覧を作成する
//========================================================================================
function funCreateDesignSpaceList(xmlData) {

    //件数取得し、行数を保存
	rowcnt = funGetLength(xmlData);
    // 検索時の更新日時
    MAX_DtKoshin = "";

    for(var i = 1; i <= rowcnt; i++){

    	if (MAX_DtKoshin < funXmlRead(xmlData, "dt_koshin", i-1)) {
    		// 検索時の更新日時のMAX値を保存
    		MAX_DtKoshin = funXmlRead(xmlData, "dt_koshin", i-1);
    	}

    	// 行追加
    	funAddDesignSpace(i, xmlData);

    	// 種類コードの取得（カテゴリを"_"で結合）
    	var cd_syurui = funXmlRead(xmlData, "cd_literal", i-1) + "_" +  funXmlRead(xmlData, "cd_2nd_literal", i-1);

    	// 種類名の取得
    	var syuruiNm = funXmlRead(xmlData, "nm_syurui", i-1);

        // 種類
        var ddlSyurui = document.getElementById("ddlSyurui-" + i);
        // 表示ファイル名
    	var inputName = document.getElementById("inputName-" + i);

    	// 種類のセレクト値設定（空白あり）
        funCreateComboBox(ddlSyurui, xmlFGEN3250O, 1, 2);

        // 種類選択Indexの設定 （第２カテゴリ）
        funSetIndex(ddlSyurui, xmlFGEN3250O, "cd_2nd_literal", cd_syurui, 1);

        // 保存ファイル名を表示（種類選択Indexを変更してからセット）
        inputName.value = funXmlRead(xmlData, "nm_file", i-1);

   }

}

//========================================================================================
// デザインスペース情報 明細行作成処理
// 作成者：E.Kitazawa
// 作成日：2014/09/11
// 引数  ：①row     ：行番号
//       ：②xmlData ：XMLデータ（「行追加」時は、""）
// 概要  ：デザインスペース情報一覧の行追加処理
//========================================================================================
function funAddDesignSpace(row, xmlData) {

	var detail = document.getElementById("detail");

    var html;

    // 行設定
    var tr = document.createElement("tr");
    tr.setAttribute("class", "disprow");
    tr.setAttribute("id", "tr" + row);

    // №（非表示）
    var td1 = document.createElement("td");
    td1.setAttribute("class", "column");
    td1.innerHTML = row;
    td1.style.textAlign = "center";
    td1.style.width = 29;
    td1.style.display = "none";
    tr.appendChild(td1);

    // 選択ラジオボタン
    var td2 = document.createElement("td");
    td2.setAttribute("class", "column");
    html = "<input type=\"radio\" name=\"chk\" value=\"" + row + "\" ";
    html += "onkeydown=\"if(event.keyCode == 13){funEnterRadio(" + row + ");}\" >";
    td2.innerHTML = html;
    td2.style.textAlign = "center";
    td2.style.width = 30;
    tr.appendChild(td2);


    // 種類（セレクトボックス）
    var td3 = document.createElement("td");
    td3.setAttribute("class", "column");
    html = "<select name=\"ddlSyurui-" + row + "\" id=\"ddlSyurui-" + row + "\" style=\"width:270px;\" onChange=\"funChangeSyurui(" + row + ")\" >";
    html += "</select>";
    td3.innerHTML = html;
    td3.style.textAlign="left";
    td3.style.width = 280;
    tr.appendChild(td3);

    // ファイル名
    var td4 = document.createElement("td");
    td4.setAttribute("class", "column");
    // 保存されているファイル名（非表示）
    html = "<input type=\"hidden\" id=\"nm_file-" + row + "\" value=\"";
    // 検索ボタン押下（データが存在する）の場合
    if (xmlData != "") {
    	// 種類コードの取得（カテゴリを"_"で結合）
    	var cd_syurui = funXmlRead(xmlData, "cd_literal", row-1) + "_" +  funXmlRead(xmlData, "cd_2nd_literal", row-1);
    	// 既存ファイル名に種類コードを付加する（サブフォルダーの一部）
    	html += cd_syurui + "\\";
    	html += funXmlRead(xmlData, "nm_file", row-1);
    }
    html += "\" tabindex=\"-1\">";

    // 参照ボタンのinput を表示用ファイル名で隠す
    html += "<div style=\"position: relative;\">";
    // 参照ボタン（onChangeイベントだけでは表示ファイル名をクリアして同じファイルを選択した時セットされないのでonclickイベントで表示ファイル名にセット）
    html += "<input type=\"file\" class=\"normalbutton\" value=\"\" style=\"width:528px;\" name=\"fileName-" + row + "\" id=\"fileName-" + row + "\"";
    html += "onChange=\"funChangeFile(" + row + ")\" onclick=\"funSetInput(" + row + ")\" ";
    // 参照ボタンENTERキーでダイアログを開く
    html += "onkeydown=\"funEnterFile(" + row + ", event.keyCode);\" >";
    // 表示用ファイル名（スクロールしてもタイトルの下に表示）
    html += "<span style=\"position: absolute; top: 0px; left: 0px; z-index:1;\">";
    html += "<input type=\"text\" value=\"";
    // 表示用ファイル名：タブ移動を無効とする
    html += "\" name=\"inputName-" + row + "\" id=\"inputName-" + row + "\" size=\"91\" readonly tabindex=\"-1\" >";
    html += "</span>";
    html += "</div>";
    td4.innerHTML = html;
    td4.style.textAlign="left";
    td4.style.width = 530;
    tr.appendChild(td4);

    // クリアボタン：削除（2014-11-20）
/*    var td5 = document.createElement("td");
    td5.setAttribute("class", "column");
    html = "<input type=\"button\" class=\"normalbutton\" name=\"btnClear-" + row + "\" id=\"btnClear-" + row + "\" ";
    html += "value=\"クリア\" onClick=\"funClearInput(" + row + ")\" >";
    td5.innerHTML = html;
    td5.style.textAlign="center";
    td5.style.width = 100;
    tr.appendChild(td5);
*/
    // 更新者
    var td6 = document.createElement("td");
    td6.setAttribute("class", "column");
    td6.setAttribute("name", "nm_koshin-" + row);
    td6.setAttribute("id", "nm_koshin-" + row);
    if (xmlData != "") {
    	td6.innerHTML = funXmlRead(xmlData, "nm_koshin", row-1);
    } else {
    	td6.innerHTML = "&nbsp;"
    }
    td6.style.textAlign = "center";
    td6.style.width = 160;
    tr.appendChild(td6);

	// 更新日時
    var td7 = document.createElement("td");
    td7.setAttribute("class", "column");
    td7.setAttribute("name", "dt_koshin-" + row);
    td7.setAttribute("id", "dt_koshin-" + row);
    if (xmlData != "") {
    	td7.innerHTML = funXmlRead(xmlData, "dt_koshin", row-1);
    } else {
    	td7.innerHTML = "&nbsp;"
    }
    td7.style.textAlign = "left";
    td7.style.width = 150;
    tr.appendChild(td7);

    detail.appendChild(tr);

}

//*** デザインスペース情報：明細行内処理 ***//
//========================================================================================
// 種類選択処理
// 作成者：E.Kitazawa
// 作成日：2014/09/11
// 引数  ：row   ：行番号
// 概要  ：ファイル名をクリア
//========================================================================================
function funChangeSyurui(row) {
	var frm = document.frm00;    //ﾌｫｰﾑへの参照

	var inputName = document.getElementById("inputName-" + row);

	// 「参照」ボタンで設定したファイル名か？
	if (inputName.style.color != "red") {
		// 保存ファイル名の時、クリアする
		// アップロードファイルを指定すること！
		inputName.value = "";
	}

	// ENTERキーで行選択する対応
	funEnterRadio(row)
}

//========================================================================================
// 参照ボタン押下処理
// 作成者：E.Kitazawa
// 作成日：2014/09/11
// 引数  ：row   ：行番号
// 概要  ：アップロードファイルを指定
//========================================================================================
function funSetInput(row) {

	var inputName = document.getElementById("inputName-" + row);		// 表示用ファイル名
	var fileName = document.getElementById("fileName-" + row);			// 参照ファイル名
	var nm_koshin = document.getElementById("nm_koshin-" + row);		// 更新者
	var dt_koshin = document.getElementById("dt_koshin-" + row);		// 更新日

	// ファイルダイアログ
	if (fileName.value == "") {
		// クリアボタンが削除されたのでここでの置き換えは不要となった。
/*		if (inputName.style.color == "red") {
			//inputName.value = fileName.value;
		}
*/
/*
		// 参照ファイル名を表示用にセット（アップロードファイル名と一致させる）
		inputName.value = fileName.value;
		// アップロードファイル名は文字色を変更
		inputName.style.color = "red";
		// 更新者・更新日をクリア
		nm_koshin.innerHTML = "&nbsp;";
		dt_koshin.innerHTML = "&nbsp;";
*/

		} else {
		// 参照ファイルフルパスよりファイル名を取得し表示用にセット
		//（拡張子のチェック不要）
		inputName.value = funGetFileNm(fileName.value);
		// アップロードファイル名は文字色を変更
		inputName.style.color = "red";
		// 更新者・更新日をクリア
		nm_koshin.innerHTML = "&nbsp;";
		dt_koshin.innerHTML = "&nbsp;";
	}
    return true;

}


//========================================================================================
// 参照ボタンキー押下処理（ENTERキー対応）
// 作成者：E.Kitazawa
// 作成日：2014/11/20
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
// ラジオボタン押下処理（ENTERキー）
// 作成者：E.Kitazawa
// 作成日：2014/11/20
// 引数  ：row   ：行番号（１～）
// 概要  ：ENTERキーを行選択処理を実行する為
//========================================================================================
function funEnterRadio(row) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    if (!!frm.chk[row-1]) {
		frm.chk[row-1].click();
	} else {
		// Index が付いていない
		frm.chk.click();
	}
}

//========================================================================================
// アップロードファイル名が変更された
// 作成者：E.Kitazawa
// 作成日：2014/09/11
// 引数  ：row   ：行番号
// 概要  ：表示用ファイル名にセットする
//========================================================================================
function funChangeFile(row) {

	funSetInput(row);

}

//========================================================================================
// 参照ボタン押下処理
// 作成者：E.Kitazawa
// 作成日：2014/09/11
// 引数  ：str   ファイルパス
// 戻り値：ファイル名
// 概要  ：ファイルパスを引数として渡して分解する
//========================================================================================
function funGetFileNm(str){

	var FileName = "";				// 戻り値

	var strTmp = str.split("\\");	// フォルダー、ファイル名を分解

	// ファイル名を取得
	FileName = strTmp[strTmp.length - 1];
	return FileName

}

//========================================================================================
// クリアボタン押下処理：ボタン削除（2014-11-20）
// 作成者：E.Kitazawa
// 作成日：2014/09/11
// 引数  ：row   ：行番号
// 概要  ：ファイル名をクリア
//========================================================================================
function funClearInput(row) {
	var frm = document.frm00;    //ﾌｫｰﾑへの参照

    // 表示用ファイル名
	var inputName = document.getElementById("inputName-" + row);
	// 更新者
	var nm_koshin = document.getElementById("nm_koshin-" + row);
	// 更新日
	var dt_koshin = document.getElementById("dt_koshin-" + row);

    // 表示用にセット
    inputName.value = "";
    // 更新者・更新日をクリア
    nm_koshin.innerHTML = "&nbsp;";
    dt_koshin.innerHTML = "&nbsp;";

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
		if (XmlId.toString() == "RGEN3250"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //SA290
				funXmlWrite(reqAry[i], "id_user", "", 0);
				funXmlWrite(reqAry[i], "id_gamen", ConGmnIdDesignSpaceAdd, 0);
				break;
			case 2:    //FGEN3250（種類を取得）
				break;
			case 3:    //FGEN3260（デザインスペース一覧）
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
			var cd_seizokojo = frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value;
			var cd_shokuba = frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value;
			var cd_line = frm.ddlLine.options[frm.ddlLine.selectedIndex].value;

            var put_code = mode + "-" + cd_seizokojo + "-" + cd_shokuba + "-" + cd_line;

            // 検索コード置換【製造工場:::職場:::製造ライン】
            put_code = put_code.replace(/-/g,":::");

			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				// アップロード後の再ロード用
                funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", put_code, 0);
				break;
			case 1:    //FGEN3260
				funXmlWrite(reqAry[i], "cd_seizokojo", cd_seizokojo, 0);
				funXmlWrite(reqAry[i], "cd_shokuba", cd_shokuba, 0);
				funXmlWrite(reqAry[i], "cd_line", cd_line, 0);
				break;
			}

			//終了ボタン押下（検索コードをクリア）
		} else if (XmlId.toString() == "RGEN3265"){

			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
                funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", mode, 0);
				break;
			}

			// 行削除ボタン押下
		} else if (XmlId.toString() == "RGEN3270"){

		    // 選択行番号
		    var row_no = funGetCurrentRow() + 1;
			// 選択行の保存ファイル名より種類コードとファイル名に分割
			var str_fnm = document.getElementById("nm_file-" + row_no).value;
			var ary_fnm = str_fnm.split("\\");
			// 種類コード
			var cd_syurui =ary_fnm[0];
			// 種類コードより種類名を取得
			var nm_syurui = funGetXmldata(xmlFGEN3250O, "cd_2nd_literal", cd_syurui, "nm_2nd_literal");

			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //
				funXmlWrite(reqAry[i], "cd_seizokojo", frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value, 0);
				funXmlWrite(reqAry[i], "cd_shokuba", frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value, 0);
				funXmlWrite(reqAry[i], "cd_line", frm.ddlLine.options[frm.ddlLine.selectedIndex].value, 0);

				funXmlWrite(reqAry[i], "cd_syurui", cd_syurui, 0);
				funXmlWrite(reqAry[i], "nm_syurui", nm_syurui, 0);
				break;
			}

			// 登録ボタン押下
		} else if (XmlId.toString() == "RGEN3280"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //登録
				funXmlWrite(reqAry[i], "cd_seizokojo", frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value, 0);
				funXmlWrite(reqAry[i], "cd_shokuba", frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value, 0);
				funXmlWrite(reqAry[i], "cd_line", frm.ddlLine.options[frm.ddlLine.selectedIndex].value, 0);
				funXmlWrite(reqAry[i], "syoriMode", "ADD", 0);

				var str_fnm = "";
				var cd_syurui = "";
				var nm_syurui = "";

				// 表示行の種類、ファイル名を保存（":::"で繋げる）
				// 表示されている一覧をチェック
			    for(var ln = 1; ln <= rowcnt; ln++){
			        // 行オブジェクト
			    	var tr = document.getElementById("tr" + ln);
			    	// 表示行のみチェック
			    	if (tr.style.display != "none") {
			    		// 表示ファイル名
			    		var inputName = document.getElementById("inputName-" + ln);
			    		// 種類オブジェクト
			    		var ddlSyurui = document.getElementById("ddlSyurui-" + ln);

			    		// 種類名（セレクト値）
			    		cd_syurui += ddlSyurui.options[ddlSyurui.selectedIndex].value + ":::";
			    		nm_syurui += ddlSyurui.options[ddlSyurui.selectedIndex].innerText + ":::";
			    		// 登録ファイル名
			    		str_fnm += inputName.value + ":::";
			    	}
			    }

			    funXmlWrite(reqAry[i], "nm_syurui", nm_syurui, 0);
			    funXmlWrite(reqAry[i], "cd_syurui", cd_syurui, 0);
			    funXmlWrite(reqAry[i], "nm_file", str_fnm, 0);
				break;
			}

			// 削除ボタン押下
		} else if (XmlId.toString() == "RGEN3285"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //削除
				funXmlWrite(reqAry[i], "cd_seizokojo", frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value, 0);
				funXmlWrite(reqAry[i], "cd_shokuba", frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value, 0);
				funXmlWrite(reqAry[i], "cd_line", frm.ddlLine.options[frm.ddlLine.selectedIndex].value, 0);
				funXmlWrite(reqAry[i], "syoriMode", "DEL", 0);
				break;
			}

			// 更新日時チェック
		} else if (XmlId.toString() == "RGEN3410"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //削除
				funXmlWrite(reqAry[i], "cd_seizokojo", frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value, 0);
				funXmlWrite(reqAry[i], "cd_shokuba", frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value, 0);
				funXmlWrite(reqAry[i], "cd_line", frm.ddlLine.options[frm.ddlLine.selectedIndex].value, 0);
				break;
			}
		}

	}

	return true;

}

//========================================================================================
// コンボボックス作成処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②xmlData  ：設定XML
//       ：③mode     ：モード
//       ：④kara     ：1:先頭空白行なし、2:先頭空白行あり
//       ：⑤idx      ：選択状態のIndex
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
/*            if (i == idx) {
            	objNewOption.setAttribute("selected", "selected");
            }
*/
        }
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽのﾃﾞﾌｫﾙﾄ表示
    obj.selectedIndex = 0;
//    obj.selectedIndex = idx;

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
// 作成日：2014/09/17
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②xmlData  ：XMLデータ
//       ：③komoku   ：検索項目名
//       ：④text     ：検索値
//       ：⑤addIdx   ：0：空白なし、1：空白あり （indexに+1）
// 戻り値：一致した行番号を返す
// 概要  ：コンボボックスのIndex を設定する
//========================================================================================
function funSetIndex(obj, xmlData, komoku, text, addIdx) {

	if (text == "") {
		return 0;
	}

	//件数取得
	var reccnt = funGetLength(xmlData);

	for (var i = 0; i < reccnt; i++) {
		// 検索項目値が等しい場合、Index設定
		if (funXmlRead(xmlData, komoku, i) == text) {
			//ｺﾝﾎﾞﾎﾞｯｸｽのIndex設定（空白ありの時、+1）
			obj.selectedIndex = i + addIdx;
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
// 登録ボタン押下時チェック処理
// 作成者：E.Kitazawa
// 作成日：2014/11/20
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
