//========================================================================================
// 共通変数
// 作成者：E.Kitazawa
// 作成日：2014/09/01
//========================================================================================
//ダウンロード、アップロードファイル名を保持
// カテゴリ選択で取得したファイル名を保存
var strDL_FileNm = "";
// アップロードファイル名
var strUL_FileNm = "";

//DBに登録できる最大ファイル名の長さ
var MAXLEN_FILENM = 250;

//========================================================================================
// 初期表示処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

    //画面設定
    funInitScreen(ConShiryoRefId);

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //画面情報を取得・設定
    if (funGetInfo(1) == false) {
        return false;
    }

    //画面の初期化
    funClear();

    //カテゴリ選択の初期値設定
    funSetCatgorySel();

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
	var XmlId = "RGEN3400";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3400");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3400I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3400O);

//	引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
		funClearRunMessage();
		return false;
	}

//	ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3400, xmlReqAry, xmlResAry, mode) == false) {
		return false;
	}

//	ﾕｰｻﾞ情報表示
	funInformationDisplay(xmlResAry[1], 2, "divUserInfo");


//	ｺﾝﾎﾞﾎﾞｯｸｽの作成
	funCreateComboBox(frm.ddlCategoryName, xmlResAry[2], 1);

//	ﾃﾞﾌｫﾙﾄのｺﾝﾎﾞﾎﾞｯｸｽ設定値を退避
	xmlFGEN3400.load(xmlResAry[2]);

	return true;
}

//========================================================================================
// カテゴリ選択の初期値設定
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：なし
// 概要  ：選択されていたカテゴリをセットする（画面再表示に対応）
//========================================================================================
function funSetCatgorySel() {

	var selectCategory;                // 親ウィンドウに保存したカテゴリ
	var frm = document.frm00;          // ﾌｫｰﾑへの参照


	// 親ウィンドウから選択カテゴリを取得（再表示用）
	if(!window.opener || window.opener.closed){ // 親ウィンドウの存在をチェック
		// 存在しない
	}
	else{
		// 親ウィンドウの保存カテゴリの存在確認
		if (window.opener.frm00.sq220Category) {
			selectCategory = parseInt(window.opener.frm00.sq220Category.value);
			// 設定されている時
			if (selectCategory > 0) {
				frm.ddlCategoryName.selectedIndex = selectCategory;
				funFileSearch();
			}

		}
	}

}

//========================================================================================
// ダウンロードボタン押下処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：なし
// 概要  ：ファイルダウンロード
//========================================================================================
function funDownLoad() {

	var frm = document.frm00;          // ﾌｫｰﾑへの参照
    var shiryoNm = frm.shiryoName;     // 表示用Input
    var selCategory = frm.ddlCategoryName; 	// カテゴリ選択

    //カテゴリが選択されていない場合は処理中止
    if(selCategory.options[selCategory.selectedIndex].value == ""){
		//メッセージ表示
    	funErrorMsgBox(E000026);
		return;
    }

    // ファイル名のチェック
	if (shiryoNm.value == "") {
		//メッセージ表示（ファイルが指定されていない）
		funErrorMsgBox(E000027);
		return;
	}

	// 参照ボタンで置き換わった場合  ------------ 不要！
	if (shiryoNm.value != strDL_FileNm) {
		//メッセージ表示（ファイルが指定されていない）
		funErrorMsgBox(E000029);
		return;
	}

    //** ﾌｧｲﾙﾊﾟｽの退避 **/
	// ダウンロードファイル名
    frm.strFilePath.value = shiryoNm.value;
    // ダウンロードパス（const定義名）
    frm.strServerConst.value = "UPLOAD_SANKOSHIRYO_FOLDER";
    // 選択カテゴリ名（サブフォルダー名）
    frm.strSubFolder.value = selCategory.options[selCategory.selectedIndex].innerText;

    // ﾀﾞｳﾝﾛｰﾄﾞ画面の起動
    funFileDownloadUrlConnect(ConConectGet, frm);
}


//========================================================================================
// アップロードボタン押下処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：なし
// 概要  ：参照ファイルのアップロードを行う
//========================================================================================
function funUpLoad() {

	var frm = document.frm00;          // ﾌｫｰﾑへの参照
	var shiryoNm = frm.shiryoName      // ＤＢ登録されているファイル名
    var inputName = frm.inputName;     // 表示用Input
    var sansyoInput = frm.filename;    // 参照Input
    var delFname = frm.strFilePath;    // サーバーファイル削除用（ＤＢ登録されているファイル名）
    var selCategory = frm.ddlCategoryName; 	// カテゴリ選択

	var value = 0;

    //カテゴリが選択されていない場合は処理中止
    if(selCategory.options[selCategory.selectedIndex].value == ""){
		//メッセージ表示
    	funErrorMsgBox(E000026);
    	return;
    }

    // アップロードファイルのチェック
	if (inputName.value == "") {
		//メッセージ表示（アップロードファイルが指定されていない）
		funErrorMsgBox(E000027);
		return;
	}
    // アップロードファイルのチェック
	if (sansyoInput.value == "") {
		//メッセージ表示（アップロードファイルが指定されていない）
		funErrorMsgBox(E000027);
		return;
	}

	// 表示用inputが変更されている
	if (inputName.value != sansyoInput.value) {
		//メッセージ表示（アップロードファイルが指定されていない）
		funErrorMsgBox(E000028);
		return;
	}

	// 参照ファイルフルパスよりファイル名を取得（拡張子のチェック不要）
	// ＤＢ登録用に保存（アップロードするファイル名）
	strUL_FileNm = funGetFileNm(sansyoInput.value);

	// ファイル名の長さチェック（ＤＢ制限）
	if (strUL_FileNm.length > MAXLEN_FILENM) {
		funErrorMsgBox("ファイル名が長すぎます。（２５０文字まで）：\\n" + strUL_FileNm);
		return false;
	}
	// ファイル名の不正文字チェック
	if (!funChkFileNm(strUL_FileNm)) {
		funErrorMsgBox("ファイル名に不正文字が含まれています。：\\n" + strUL_FileNm);
		return false;
	}

	// アップロードの確認
	if (strDL_FileNm == "") {
		if (funConfMsgBox(I000016) != ConBtnYes) {
			return;
		}

	// 指定したカテゴリでＤＢに資料が保存されている場合、上書き確認
	} else {
		// 保存した資料のファイル名が変更されている
		if (strDL_FileNm != strUL_FileNm) {
			// ファイル名が違う：上書き確認
			if (funConfMsgBox(I000014) != ConBtnYes) {
				return;
			}

		} else {
			// ファイル名が同じ：更新確認
			if (funConfMsgBox(I000003) != ConBtnYes) {
				return;
			}
		}
	}

    //** ﾌｧｲﾙﾊﾟｽの退避 **//
    // アップロードパス（const定義名）
    frm.strServerConst.value = "UPLOAD_SANKOSHIRYO_FOLDER";
    // 選択カテゴリ名（サブフォルダー名）
    frm.strSubFolder.value = selCategory.options[selCategory.selectedIndex].innerText;
	// すでに登録されているファイル名（選択カテゴリ名を追加：サーバーより削除する）
	if (shiryoNm.value != "") {
		delFname.value = frm.strSubFolder.value + "\\" + shiryoNm.value;
	}

	// ファイルアップロードサーブレットの実行（ＤＢ登録より先に実行したい）
	funFileUpload(frm);


	//**ＤＢ登録とアップロード処理は同時に走る **//
	// アップロードするファイル名を参考資料ＤＢに登録
	// 参照ボタンで選択したファイル名を渡す
	funShiryoTblIns(selCategory.options[selCategory.selectedIndex].value);

}

//========================================================================================
// アップロードボタン押下処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：str ファイルパス
// 戻り値：ファイル名
// 概要  ：ファイルパスを引数として渡して分解する
//========================================================================================
function funGetFileNm(str){
	var FileName = "";				// 戻り値

	var strTmp = str.split("\\");	// フォルダー、ファイル名を分解

	// ファイル名を取得
	FileName = strTmp[strTmp.length - 1];
	return FileName

/*	var pattern = /(\w+):(?:\\([^\\:\*?\"<>\|]+))*(?:\\(([^:\\\*?\"<>\|]+)\.+([^:\\\*?\"<>\|]+)$))/;

	FilePath.result = str.match(pattern);
	if(FilePath.result){
		FilePath.FullPath = FilePath.result[0];			//フルパス
		FilePath.Drive = FilePath.result[1];			//ドライブ
		FilePath.ParentDir = FilePath.result[2];		//親のディレクトリ
		FilePath.FileName = FilePath.result[3];			//ファイル名
		FilePath.FileNameShort = FilePath.result[4];	//拡張子を除いたファイル名
		FilePath.FileExt = FilePath.result[5].toLowerCase();	//拡張子（小文字変換）


		// ファイル名を除いたパス
		FilePath.Path = FilePath.FullPath.slice(0, FilePath.FileName.length * -1);

		return true;
	}
	return false;
*/
}

//========================================================================================
// アップロードボタン押下処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：frm ﾌｫｰﾑへの参照
// 概要  ：ファイルアップロードサーブレットの実行
//========================================================================================
function funFileUpload(frm){


	// ファイルアップロードサーブレットの実行
	frm.action="/" + ConUrlPath + "/FileUpLoadExec";
	frm.submit();

}

//========================================================================================
// 参照ボタン押下処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：sel  0 -- カテゴリ選択時
//              1 -- 参照ボタン押下時
// 概要  ：ファイル選択したファイルパスを表示用Inputにセットする
//         表示用Input がクリアされていた場合に対応するため
//========================================================================================
function funSetInput(sel) {

	var frm = document.frm00;          // ﾌｫｰﾑへの参照
    var shiryoNm = frm.shiryoName;     // ダウンロード用Input
    var inputName = frm.inputName;     // 表示用Input
    var sansyoInput = frm.filename;    // 参照Input

    if (sel == 0) {
    	// カテゴリ選択（selectが変更されていない場合にも対応）
    	// 一旦、値を戻す onChangeでＤＢ値より設定  --- 不要？
    	shiryoNm.value = strDL_FileNm;
    } else {
    	// 参照ボタン押下（表示用にセット）
    	inputName.value = sansyoInput.value;
    }

    return true;

}

//========================================================================================
// 参照ボタンキー押下処理（ENTERキー対応）
// 作成者：E.Kitazawa
// 作成日：2014/11/20
// 引数  ： keyCode：キーコード
// 概要  ：アップロードファイルを指定
//========================================================================================
function funEnterFile(keyCode) {

	var frm = document.frm00;          // ﾌｫｰﾑへの参照
    var sansyoInput = frm.filename;    // 参照Input

    // ENTERキー押下時、参照ボタンクリックの動きを実行
    if (keyCode == 13) {
    	// ENTERキー押下でクリックイベントを発生させる
    	sansyoInput.click();
    } else if (keyCode == 46) {
    	// DELETEキーでなぜかクリアされる
    	return false;
    }
}


//========================================================================================
// 参照ボタン押下でファイル選択時
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：なし
// 概要  ：ファイル選択したファイルパスを表示用Inputにセットする
//========================================================================================
function funChangeFile() {

	funSetInput(1);

    return true;

}

//========================================================================================
// クリアボタン押下処理：削除
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：なし
// 概要  ：表示用Inputのクリアを行う（input type="file" の値は変更できないため）
//========================================================================================
//function funClearInput() {
//
//	var frm = document.frm00;          // ﾌｫｰﾑへの参照
//    var inputName = frm.inputName;     // 表示用Input
//
//    // 表示用にセット
//    inputName.value = "";
//
//    return true;
//
//}

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
        // 画面初期表示（カテゴリ一覧取得）
        if (XmlId.toString() == "RGEN3400") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3400
                    break;
            }
        // カテゴリ選択
        } else if (XmlId.toString() == "RGEN3210"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3210
                    funXmlWrite(reqAry[i], "cd_literal", frm.ddlCategoryName.options[frm.ddlCategoryName.selectedIndex].value, 0);
                    break;
            }
        // アップロードボタン押下（参考資料ＤＢ登録）
        } else if (XmlId.toString() == "RGEN3220"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3220
                    funXmlWrite(reqAry[i], "cd_literal", frm.ddlCategoryName.options[frm.ddlCategoryName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "nm_literal", frm.ddlCategoryName.options[frm.ddlCategoryName.selectedIndex].innerText, 0);
                    funXmlWrite(reqAry[i], "nm_file", strUL_FileNm, 0);
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
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②mode     ：モード
// 概要  ：コンボボックスのデフォルト値を選択する
//========================================================================================
function funDefaultIndex(obj, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var selIndex;
    var i;

    // ｺﾝﾎﾞﾎﾞｯｸｽのｲﾝﾃﾞｯｸｽ指定
    obj.selectedIndex = selIndex;

    return true;
}

//========================================================================================
// 終了ボタン押下
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 概要  ：終了処理
//========================================================================================
function funEndClick(){

	//終了処理
	funEnd();
}

//========================================================================================
// 終了処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 概要  ：終了処理
//========================================================================================
function funEnd(){

	// 親ウィンドウから選択カテゴリを取得（再表示用）
	if(!window.opener || window.opener.closed){ // 親ウィンドウの存在をチェック
		// 存在しない
	} else {
		// 親ウィンドウの保存カテゴリの存在確認
		if (window.opener.frm00.sq220Category) {
			// 親ウィンドウに保存しているカテゴリ情報を削除
			window.opener.frm00.sq220Category.value = "";
		}
		// 親ウィンドウをフォーカスにする
		window.opener.focus();
	}

	// 画面を閉じる
	window.close();

}

//========================================================================================
// 画面の初期化処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：なし
// 概要  ：画面を初期化する
//========================================================================================
function funClear() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照

	//画面の初期化
	frm.reset();
	frm.ddlCategoryName.selectedIndex = 0;

	//ﾃﾞﾌｫﾙﾄのｺﾝﾎﾞﾎﾞｯｸｽ設定値を読み込む
	xmlFGEN3400O.load(xmlFGEN3400);

	//ｺﾝﾎﾞﾎﾞｯｸｽの再設定
	funCreateComboBox(frm.ddlCategoryName, xmlFGEN3400O, 1);
	funDefaultIndex(frm.ddlCategoryName, 1);

 return true;

}


//========================================================================================
// カテゴリ選択処理
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：なし
// 概要  ：選択したカテゴリより資料ファイルパスを設定する
//========================================================================================
function funFileSearch() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    var XmlId = "RGEN3210";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3210");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3210I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3210O);



	if(!window.opener || window.opener.closed){ // 親ウィンドウの存在をチェック
		// 存在しない
	}
	else{
		// 親ウィンドウの保存カテゴリの存在確認
		if (window.opener.frm00.sq220Category) {
			// 再表示選択のために親ウィンドウに保存
			window.opener.frm00.sq220Category.value = frm.ddlCategoryName.selectedIndex;
		}
	}

	// 表示用 input をクリア
	frm.shiryoName.value = "";
	// 選択したカテゴリで保存されているファイル名をクリア
	strDL_FileNm = "";

    // カテゴリが選択されていない場合、ファイルパスをクリアして処理中止
    if(frm.ddlCategoryName.options[frm.ddlCategoryName.selectedIndex].value == ""){
    	return;
    }

    // 処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    // 引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    // 検索条件に一致するカテゴリデータを取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3210, xmlReqAry, xmlResAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    // 件数取得
    reccnt = funGetLength(xmlResAry[2]);

    // 複数件あった場合（１：１のハズ！）
    if(reccnt > 1){
   	 funInfoMsgBox("複数見つかりました。");
    }

    //ダウンロードファイル名の設定
    var val = "";
    for(i=0; i<reccnt; i++){
    	if(i >= 1){
    		val = val + ",";
    	}
    	val = val + funXmlRead(xmlResAry[2], "nm_file", i);
    }

    // ダウンロード用 input に設定
    frm.shiryoName.value = val;
    // 参照ボタン押下で置き換わるので退避
    strDL_FileNm = val;

    // 処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    return true;

}


//========================================================================================
// 参考資料管理テーブル登録
// 作成者：E.Kitazawa
// 作成日：2014/09/01
// 引数  ：literal  カテゴリ番号
//         strUL_FileNm アップロードする参考資料ファイル名
// 概要  ：参考資料管理テーブルにカテゴリ番号とファイル名を登録する
//========================================================================================
function funShiryoTblIns(literal) {
	   var frm = document.frm00;    //ﾌｫｰﾑへの参照

	    var XmlId = "RGEN3220";
	    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3220");
	    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3220I);
	    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3220O);


	    // カテゴリが選択されていない場合、参考資料ファイル名が設定されていない処理中止
	    if(literal == "" || strUL_FileNm == ""){
	    	return;
	    }

	    // 処理中ﾒｯｾｰｼﾞ表示
	    funShowRunMessage();

	    // 引数をXMLﾌｧｲﾙに設定
	    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
	        funClearRunMessage();
	        return false;
	    }

	    // 参考資料管理テーブル登録
	    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3220, xmlReqAry, xmlResAry, 1) == false) {
	        return false;
	    }

	    //処理中ﾒｯｾｰｼﾞ非表示
	    funClearRunMessage();

	    //完了ﾒｯｾｰｼﾞの表示
	    dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);

	    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {

	        //正常
	        // ダウンロード用 input に設定  ---------  不要！
	        frm.shiryoName.value = strDL_FileNm;

	    } else {

	        //error
	    	funErrorMsgBox(dspMsg);
	    }
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
