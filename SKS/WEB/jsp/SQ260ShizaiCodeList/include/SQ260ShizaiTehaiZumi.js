//========================================================================================
// 共通変数
// 作成者：t2nakamura
// 作成日：2016/10/03
//========================================================================================
var loop_cnt = 0;
//DBに登録できる最大ファイル名の長さ
var MAXLEN_FILENM = 250;


//========================================================================================
// 初期表示処理
// 作成者：H.Shima
// 作成日：2014/09/4
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

    var frm = document.frm00; // ﾌｫｰﾑへの参照

    //画面設定
    funInitScreen(ConShizaiTehaiZumiListId);

    // 画面情報を取得・設定
    if (funGetInfo(1) == false) {
        return false;
    }

    document.getElementById("lblTitle").innerHTML = "資材手配済一覧";

    // 選択完了を非活性
    document.getElementById("btnCompletion").disabled = true;
    document.getElementById("btnUpLoad").disabled = true;
    document.getElementById("btnOutput").disabled = true;

    // 手配済みにチェック
    frm.chkTehaizumi.checked = true;

    // 手配区分チェックボックスを非活性
    document.getElementById("chkTehaizumi").disabled = true;
    document.getElementById("chkMitehai").disabled = true;
    document.getElementById("chkMinyuryoku").disabled = true;

    document.getElementById("chkEdit").disabled = true;		// 編集チェックボックス

    // アップロードフラグを0に戻す
    uploadFlg = 0;

    return true;
}

//========================================================================================
// 画面情報取得処理
// 作成者：H.Shima
// 作成日：2014/09/09
// 引数１：mode ：処理モード
//         1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 概要  ：ユーザのセッション情報、コンボボックスの情報を取得する
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00; // ﾌｫｰﾑへの参照
    var XmlId = "RGEN3200";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3200","FGEN3300","FGEN3310", "FGEN3330","SA290");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3200I, xmlFGEN3300I, xmlFGEN3310I,xmlFGEN3330I,xmlSA290I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3200O, xmlFGEN3300O, xmlFGEN3310O, xmlFGEN3330O,xmlSA290O);

    // 引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        //funClearRunMessage();
        return false;
    }

    // ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3200, xmlReqAry, xmlResAry,
            mode) == false) {
        return false;
    }

    // ﾕｰｻﾞ情報表示
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    // 対象資材
    funCreateComboBox(frm.ddlShizai, xmlResAry[2], 2, 2);

    // 担当者
    funCreateComboBox(frm.ddlTanto, xmlResAry[3], 3, 2);

    // 発注先
    funCreateComboBox(frm.ddlHattyusaki, xmlResAry[4], 1, 2);

	// アップロード情報
    if (funXmlRead(xmlResAry[5], "flg_return", 0) == "true") {
    	//xmlFGEN3330.load(xmlResAry[5]);
    	if (funXmlRead(xmlResAry[5], "chkTehaizumi", 0) == "1") {
    		frm.chkTehaizumi.checked = true;

    	}
    	// 資材コード
    	frm.txtShizaiCd.value = funXmlRead(xmlResAry[5], "txtShizaiCd", 0);
    	// 資材名
    	frm.txtShizaiNm.value = funXmlRead(xmlResAry[5], "txtShizaiNm", 0);
    	// 旧資材コード
    	frm.txtOldShizaiCd.value = funXmlRead(xmlResAry[5], "txtOldShizaiCd", 0);
    	// 製品コード
    	frm.txtSyohinCd.value = funXmlRead(xmlResAry[5], "txtSyohinCd", 0);
    	// 製品名
    	frm.txtSyohinNm.value = funXmlRead(xmlResAry[5], "txtSyohinNm", 0);
    	// 納入先
    	frm.txtSeizoukojo.value = funXmlRead(xmlResAry[5], "txtSeizoukojo", 0);
    	// 対象資材
    	frm.ddlShizai.value = funXmlRead(xmlResAry[5], "ddlShizai", 0);

    	// 発注先
    	frm.ddlHattyusaki.value = funXmlRead(xmlResAry[5], "ddlHattyusaki", 0);
    	// 発注者
    	frm.ddlTanto.value = funXmlRead(xmlResAry[5], "ddlTanto", 0);
    	// 発注日from
    	frm.txtHattyubiFrom.value = funXmlRead(xmlResAry[5], "txtHattyubiFrom", 0);
    	// 発注日to
    	frm.txtHattyubiTo.value = funXmlRead(xmlResAry[5], "txtHattyubiTo", 0);
    	// 納入日from
    	frm.txtNounyudayFrom.value = funXmlRead(xmlResAry[5], "txtNounyudayFrom", 0);
    	// 納入日to
    	frm.txtNounyudayTo.value = funXmlRead(xmlResAry[5], "txtNounyudayTo", 0);
    	// 版代支払日from
    	frm.txtHanPaydayFrom.value = funXmlRead(xmlResAry[5], "txtHanPaydayFrom", 0);
    	// 版代支払日to
    	frm.txtHanPaydayTo.value = funXmlRead(xmlResAry[5], "txtHanPaydayTo", 0);
    	// 未支払
    	var chkMshiharai = funXmlRead(xmlResAry[5], "chkMshiharai", 0)
    	if (chkMshiharai == 1) {
    		frm.chkMshiharai.checked = true;
    	} else {
    		frm.chkMshiharai.checked = false;
    	}


		// アップロード後の再ロード
        //funSearchConditionSet(xmlResAry[5]);
        funSearch();
	}
}

//========================================================================================
//アップロード押下処理
//作成者：E.Kitazawa
//作成日：2014/09/24
//引数  ：なし
//概要  ：資材テーブル更新、資材手配テーブル登録
//========================================================================================
function funToroku() {

	var frm = document.frm00;          //ﾌｫｰﾑへの参照
	var nm_tanto = frm.nm_tanto;           // 担当者
	var chkKanryo = frm.chkKanryo;     // 完了チェックボックス
	var retBool = false;
	var retUpload = false;

	// 登録確認
	if (funConfMsgBox(I000002) != ConBtnYes) {
		return;
	}

	// 明細行（読み込み分）
	var detail = document.getElementById("tblList");

	// 明細データ
	if (!detail.firstChild){
		// 資材情報がない時
		retBool = funTorokuChk(-1);
	// レコードが１件の時
	} else if (loop_cnt == 1) {
		// チェックボックスを渡し、登録（資材テーブル更新、手配テーブル登録or削除）
		retBool = funTorokuChk(0);
	} else {
		//
		for(var i = 0; i < loop_cnt; i++){
			// チェックボックスを渡し、登録（資材テーブル更新、手配テーブル登録or削除）
			retBool = funTorokuChk(i);
			if (!retBool) break;
		}
	}
	//ファイルアップロードを先に実施
	retUpload = funfileUpload();

	// 完了メッセージ
 if (retBool || retUpload ) {
 	// 登録しました。
 	funInfoMsgBox("最新のデータを検索します");
     //@@@@@
 	// 再検索実行
 	funSearch();
 }

	return true;

}

//========================================================================================
//アップロード処理
//作成者：t2nakamura
//作成日：2016/10/20
//引数  ：なし
//概要  ：青焼きアップロード
//========================================================================================
function funfileUpload() {
 var frm = document.frm00;   //ﾌｫｰﾑへの参照
 var upFildNm = "";          //アップロードするフィールド名
 var delFileNm = "";         //削除するファイル名
 var lstSyurui = {};         //登録する種類の配列（重複チェック用）
 var strMsg = ""				//確認メッセージ付加文字

 var subFolder = "";

// // 保存ファイルのサブフォルダー取得（工場）
// var subFolder = funSelChk();
// // 条件が選択されていない場合
//	if (subFolder == "") {
//		funErrorMsgBox(funSelChk.Msg);
//		return false;
//	}
//
	// 選択した種類コードを_で繋ぐ為、ファイル毎に設定
	var subFlst = "";
//	// 表示行が無い場合、エラー
//	var displayln = false;

	// 一覧データがない
	if (loop_cnt == 0) {
 	funErrorMsgBox(E000030);
     return false;
	}

	// 表示されている一覧をチェック
	// 参照ファイル、表示用inputが空白でないこと
 for(var i = 0; i < loop_cnt; i++){
     // 行オブジェクト
 	//var tr = document.getElementById("tr");

 	// アップロードするファイル名
 	var filename = document.getElementById("filename_" + i);

 	// 表示ファイル名
 	var inputName = document.getElementById("inputName_" + i);

 	// 保存されているファイル名（非表示）
 	var nm_file = document.getElementById("nm_file_" + i);

 	// 社員コード
 	var cd_shain = document.getElementById("cd_shain_" + i);
 	// 年
 	var nen = document.getElementById("nen_" + i);
 	// 追番
 	var no_oi = document.getElementById("no_oi_" + i);
 	// 試作SEQ
 	var seq_shizai = document.getElementById("seq_shizai_" + i);
 	// 枝番
 	var no_eda = document.getElementById("no_eda_" + i);
 	// 製品番号
 	var cd_shohin = document.getElementById("cd_shohin_" + i);

 	//var cd_shizai = document.getElementById("cd_shizai-" + i);

 	// 表示行のみ処理する
 	//if (tr.style.display != "none") {

 	//if (loop_cnt >= 1) {
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
				funErrorMsgBox(E000064 + inputName.value);
// 			funErrorMsgBox("ファイル名に不正文字が含まれています。：\\n" + inputName.value);
 			return false;
			}
 		// クリアボタンが押下されていないこと
 		if ((filename.value != "") && (inputName.value != "") && (inputName.style.color == "red")) {
 			// アップロード処理：サーバー渡し（":::"で区切る）
 			// フィールド名（アップロードチェック用）
 			upFildNm += "filename_" + i + ":::";

 			// サブフォルダーに種類コードを追加
 			//subFlst += subFolder  + cd_shizai.value + ":::";
 			subFolder = cd_shain.value + "_" + nen.value + "_" + no_oi.value + "_" + seq_shizai.value + "_" + no_eda.value + "_" + cd_shohin.value;
 			subFlst += subFolder + ":::";
 		}
 		// 変更前ファイルを削除
 		// ＤＢに保存されている 且つ 変更された赤字ファイルの時、
 		if ( (nm_file.value != "") && (inputName.style.color == "red")) {
 			// サブフォルダー名を付加する。（":::"で区切る）
 			// nm_file_henshita には保存データの種類コードが付加済（"\\"で区切られている）
 			delFileNm += subFolder  + "\\" + nm_file.value + ":::";
 		}

 	//}
 }
 // 表示行がない
	if (!displayln) {
 	funErrorMsgBox(E000030);
     return false;
	}
 if (upFildNm == "") {
     return false;
 }
//	// 排他処理：検索時の更新日時から変更があったら処理中止
//	if (!funKoshinChk()) {
//		return false;
//	}
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
 	frm.action="/" + ConUrlPath + "/FileUpLoadExec"; // ConUrlPath → Shisaquick
 	frm.submit();
 }
 return true;
}


//========================================================================================
//行の登録チェック
//作成者：t2nakamura
//作成日：2016/10/14
//引数  ：なし
//概要  ：指定行の行データをxmlに保存する
//========================================================================================
function funTorokuChk(row) {
	var frm = document.frm00;          //ﾌｫｰﾑへの参照

	// 指定行の行データをセットする為にxml を設定
	//xmlFGEN3450.load(xmlFGEN3450O);   // 空行の時、設定されていない
	funAddRecNode(xmlFGEN3690, "FGEN3690");

	if (row < 0) {
		// 一覧がない
		funXmlWrite(xmlFGEN3690, "cd_shain", "", 0);
		// 試算資材テーブル更新処理（エラー）
		if (!funShizaiUpdate()) {
			return false;
		}

	} else {
		// 社員コード
		var cd_shain = document.getElementById("cd_shain_" + row).value;
		// 年
		var nen = document.getElementById("nen_" + row).value
		// 追番
		var no_oi = document.getElementById("no_oi_" + row).value;
		// 枝番
		var no_eda = document.getElementById("no_eda_" + row).value;
		// 試作SEQ
		var seq_shizai = document.getElementById("seq_shizai_" + row).value;
	 	// 製品番号
	 	var cd_shohin = document.getElementById("cd_shohin_" + row).value;

		// 対象行データを先頭行にセットする
		// 社員コード
		funXmlWrite(xmlFGEN3690, "cd_shain", cd_shain, 0);
		// 年
		funXmlWrite(xmlFGEN3690, "nen", nen, 0);
		// 追番
		funXmlWrite(xmlFGEN3690, "no_oi", no_oi, 0);
		// 枝番
		funXmlWrite(xmlFGEN3690, "no_eda", no_eda, 0);
		// 試作SEQ
		funXmlWrite(xmlFGEN3690, "seq_shizai", seq_shizai, 0);
		// 製品番号
		funXmlWrite(xmlFGEN3690, "cd_shohin", cd_shohin, 0);
		// 納入日
		funXmlWrite(xmlFGEN3690, "nounyu_day", document.getElementById("nounyu_day_" + row).value, 0);
		// 版代
		funXmlWrite(xmlFGEN3690, "han_pay", document.getElementById("han_pay_" + row).value, 0);
		// 版代支払日
		funXmlWrite(xmlFGEN3690, "han_payday", document.getElementById("han_payday_" + row).value, 0);
		// 青焼ファイル名
		var inputName = document.getElementById("inputName_" + row).value == "?" ? document.getElementById("inputName_" + row).value = "" : document.getElementById("inputName_" + row).value;
		funXmlWrite(xmlFGEN3690, "inputName", inputName, 0);
		// 青焼ファイルパス
		// 作成
		// 社員番号
		var subFolder = cd_shain + "_" + nen + "_" + no_oi + "_" + seq_shizai + "_" + no_eda + "_" + cd_shohin;
		funXmlWrite(xmlFGEN3690, "filename", subFolder, 0);

		// 更新処理
		if (!funShizaiUpdate()) {
			 funClearRunMessage();
			return false;
		}

	}
	return true;

}

//========================================================================================
//試算資材テーブル更新処理
//作成者：t2nakamura
//作成日：2016/10/14
//引数  ：なし
//概要  ：資材テーブル更新
//========================================================================================
function funShizaiUpdate() {

	var XmlId = "RGEN3690";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3690");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3690I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3690O);


	//引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}

	// 処理中ﾒｯｾｰｼﾞ表示
	funShowRunMessage();

	// ｾｯｼｮﾝ情報、共通情報を取得
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3690, xmlReqAry, xmlResAry, 1) == false) {
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
// XMLファイルに書き込み
// 作成者：H.Shima
// 作成日：2009/04/01
// 引数１：XmlId  ：XMLID
// 引数２：reqAry ：機能ID別送信XML(配列)
// 引数３：Mode   ：処理モード
//         1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：なし
// 概要  ：機能IDごとのXMLファイルに引数を設定する
//========================================================================================
function funReadyOutput(XmlId, reqAry, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var i;

    for (i = 0; i < reqAry.length; i++) {
        //画面初期表示
        if (XmlId.toString() == "RGEN3200") {
            switch (i) {
                case 0:    // USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
            	case 5:    //SA290
    				funXmlWrite(reqAry[i], "id_user", "", 0);
    				funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShizaiTehaiList, 0);
    				break;
            }
        }
        // 検索
        else if (XmlId.toString() == "RGEN3330") {
            switch (i) {
            case 0:    // USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);


                // 手配済み(checkbox)
                var chkTehaizumi = frm.chkTehaizumi.checked == true ? 1 : 0;
                // 未支払
                var chkMishiharai = frm.chkMshiharai.checked == true ? 1 : 0;
                // 資材コード＋資材名＋旧資材コード＋製品コード＋製品名 + 納入先 + 対象資材 + 発注先 + 発注者 + 発注日 + 納入日 + 版代支払日
                var put_code = chkTehaizumi + ":::"
                				+ frm.txtShizaiCd.value + ":::" + frm.txtShizaiNm.value + ":::" + frm.txtOldShizaiCd.value + ":::"
                				+ frm.txtSyohinCd.value + ":::" + frm.txtSyohinNm.value + ":::" + frm.txtSeizoukojo.value + ":::"
                				+ frm.ddlShizai.value + ":::" + frm.ddlHattyusaki.value + ":::" + frm.ddlTanto.value + ":::"
                				+ frm.txtHattyubiFrom.value + ":::" + frm.txtHattyubiTo.value + ":::" + frm.txtNounyudayFrom.value + ":::" + frm.txtNounyudayTo.value + ":::"
                				+ frm.txtHanPaydayFrom.value + ":::" + frm.txtHanPaydayTo.value + ":::" + chkMishiharai + ":::";
                // アップロード後の再ロード用
                funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", put_code, 0);

                break;
            case 1:    // FGEN3330
                // チェックボックス設定
                var tehaizumi = 0;
                if(frm.chkTehaizumi.checked == true) {
                    tehaizumi = 1;
                }
                var mitehai = 0;
                var minyuryoku = 0;

                // XML書き込み
                // 手配区分
                // 手配済み
                funXmlWrite(reqAry[i], "kbn_tehaizumi", tehaizumi, 0);
                // 未手配
                funXmlWrite(reqAry[i], "kbn_mitehai", mitehai, 0);
                // 未入力
                funXmlWrite(reqAry[i], "kbn_minyuryoku", minyuryoku, 0);

                /* 検索条件 １行目 */
                // 資材コード
                funXmlWrite(reqAry[i], "cd_shizai", frm.txtShizaiCd.value, 0);
                // 資材名
                funXmlWrite(reqAry[i], "nm_shizai", frm.txtShizaiNm.value, 0);
                // 旧資材コード
                funXmlWrite(reqAry[i], "cd_shizai_old", frm.txtOldShizaiCd.value, 0);

                /* 検索条件 ２行目 */
                // 製品コード
                funXmlWrite(reqAry[i], "cd_shohin", frm.txtSyohinCd.value, 0);
                // 製品名
                funXmlWrite(reqAry[i], "nm_shohin", frm.txtSyohinNm.value, 0);
                // 納入先（製造工場）
                funXmlWrite(reqAry[i], "cd_seizoukojo", frm.txtSeizoukojo.value, 0);
                // 納入先（製造工場名）
                funXmlWrite(reqAry[i], "nm_seizoukojo", frm.txtSeizoukojo.value, 0);

                /* 検索条件 ３行目 */
                // 対象資材
                funXmlWrite(reqAry[i], "taisyo_shizai", frm.ddlShizai.options[frm.ddlShizai.selectedIndex].value, 0);
                // 発注先
                funXmlWrite(reqAry[i], "cd_hattyusaki", frm.ddlHattyusaki.options[frm.ddlHattyusaki.selectedIndex].value, 0);
                // 発注者
                funXmlWrite(reqAry[i], "cd_hattyusya", frm.ddlTanto.options[frm.ddlTanto.selectedIndex].value, 0);

                /* 検索条件 ４行目 */
                // 発注日From
                funXmlWrite(reqAry[i], "dt_hattyu_from", frm.txtHattyubiFrom.value, 0);
                // 発注日To
                funXmlWrite(reqAry[i], "dt_hattyu_to", frm.txtHattyubiTo.value, 0);
                // 納入日From
                funXmlWrite(reqAry[i], "dt_nonyu_from", frm.txtNounyudayFrom.value, 0);
                // 納入日To
                funXmlWrite(reqAry[i], "dt_nonyu_to", frm.txtNounyudayTo.value, 0);
                // 版代支払日
                funXmlWrite(reqAry[i], "dt_han_payday_from", frm.txtHanPaydayFrom.value, 0);
                // 版代支払日
                funXmlWrite(reqAry[i], "dt_han_payday_to", frm.txtHanPaydayTo.value, 0);

                // チェックボックス設定
                var mshiharai = 0;
                if(frm.chkMshiharai.checked == true) {
                	mshiharai = 1;
                }
                // 未支払
                funXmlWrite(reqAry[i], "kbn_mshiharai", mshiharai, 0);

                break;
            }
        }
        // 選択完了
        else if (XmlId.toString() == "RGEN3340"){

            switch (i) {
            case 0:    //USERINFO
                 funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                 funXmlWrite(reqAry[i], "id_user", "", 0)
                 break;
            case 1:    //FGEN3340

                // 行数取得
                var max_row = frm.hidListRow.value;
                var row = 0;

                var cd_shain       = "";
                var nen            = "";
                var no_oi          = "";
                var seq_shizai     = "";
                var no_eda         = "";

                for(var j = 0; j < max_row; j++){

                    if(max_row <= 1){

                        if(frm.chkShizai.checked){
                            // XMLより試作コード取得
                            cd_shain       = document.getElementById("cd_shain_" + j).value;
                            nen            = document.getElementById("nen_" + j).value;
                            no_oi          = document.getElementById("no_oi_" + j).value;
                            seq_shizai     = document.getElementById("seq_shizai_" + j).value;
                            no_eda         = document.getElementById("no_eda_" + j).value;

                            funXmlWrite_Tbl(reqAry[i],"table" , "cd_shain", cd_shain, row);
                            funXmlWrite_Tbl(reqAry[i],"table" , "nen", nen, row);
                            funXmlWrite_Tbl(reqAry[i],"table" , "no_oi", no_oi, row);
                            funXmlWrite_Tbl(reqAry[i],"table" , "seq_shizai", seq_shizai, row);
                            funXmlWrite_Tbl(reqAry[i],"table" , "no_eda", no_eda, row);
                        }
                    } else {

                        if(frm.chkShizai[j].checked){

                            if (row != 0) {
                                funAddRecNode_Tbl(reqAry[i], "FGEN3340", "table");
                            }
                            // XMLより試作コード取得
                            cd_shain       = document.getElementById("cd_shain_" + j).value;
                            nen            = document.getElementById("nen_" + j).value;
                            no_oi          = document.getElementById("no_oi_" + j).value;
                            seq_shizai     = document.getElementById("seq_shizai_" + j).value;
                            no_eda         = document.getElementById("no_eda_" + j).value;

                            funXmlWrite_Tbl(reqAry[i],"table" , "cd_shain", cd_shain, row);
                            funXmlWrite_Tbl(reqAry[i],"table" , "nen", nen, row);
                            funXmlWrite_Tbl(reqAry[i],"table" , "no_oi", no_oi, row);
                            funXmlWrite_Tbl(reqAry[i],"table" , "seq_shizai", seq_shizai, row);
                            funXmlWrite_Tbl(reqAry[i],"table" , "no_eda", no_eda, row);

                            row++; // インクリメント
                        }
                    }
                }

                // 選択行が無い場合エラー
                if(cd_shain == "") {
                    funErrorMsgBox(E000002);
                    return false;
                }
                break;
            }
        }  // Excel出力検索
        else if (XmlId.toString() == "RGEN3620") {
            switch (i) {
            case 0:    // USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;
            case 1:    // FGEN3620
                // チェックボックス設定
                var tehaizumi = 0;
                if(frm.chkTehaizumi.checked == true) {
                    tehaizumi = 1;
                }
                var mitehai = 0;
                var minyuryoku = 0;

                // XML書き込み
                // 手配区分
                // 手配済み
                funXmlWrite(reqAry[i], "kbn_tehaizumi", tehaizumi, 0);
                // 未手配
                funXmlWrite(reqAry[i], "kbn_mitehai", mitehai, 0);
                // 未入力
                funXmlWrite(reqAry[i], "kbn_minyuryoku", minyuryoku, 0);

                /* 検索条件 １行目 */
                // 資材コード
                funXmlWrite(reqAry[i], "cd_shizai", frm.txtShizaiCd.value, 0);
                // 資材名
                funXmlWrite(reqAry[i], "nm_shizai", frm.txtShizaiNm.value, 0);
                // 旧資材コード
                funXmlWrite(reqAry[i], "cd_shizai_old", frm.txtOldShizaiCd.value, 0);

                /* 検索条件 ２行目 */
                // 製品（商品）コード
                funXmlWrite(reqAry[i], "cd_shohin", frm.txtSyohinCd.value, 0);
                // 製品（商品）名
                funXmlWrite(reqAry[i], "nm_shohin", frm.txtSyohinNm.value, 0);
                // 納入先（製造工場）
                funXmlWrite(reqAry[i], "cd_seizoukojo", frm.txtSeizoukojo.value, 0);
                // 納入先（製造工場）名
                funXmlWrite(reqAry[i], "nm_seizoukojo", frm.txtSeizoukojo.value, 0);

                /* 検索条件 ３行目 */
                // 対象資材
                funXmlWrite(reqAry[i], "taisyo_shizai", frm.ddlShizai.options[frm.ddlShizai.selectedIndex].value, 0);
                // 発注先
                funXmlWrite(reqAry[i], "cd_hattyusaki", frm.ddlHattyusaki.options[frm.ddlHattyusaki.selectedIndex].value, 0);
                // 発注者
                funXmlWrite(reqAry[i], "cd_hattyusya", frm.ddlTanto.options[frm.ddlTanto.selectedIndex].value, 0);

                /* 検索条件 ４行目 */
                // 発注日From
                funXmlWrite(reqAry[i], "dt_hattyu_from", frm.txtHattyubiFrom.value, 0);
                // 発注日To
                funXmlWrite(reqAry[i], "dt_hattyu_to", frm.txtHattyubiTo.value, 0);
                // 納入日From
                funXmlWrite(reqAry[i], "dt_nonyu_from", frm.txtNounyudayFrom.value, 0);
                // 納入日To
                funXmlWrite(reqAry[i], "dt_nonyu_to", frm.txtNounyudayTo.value, 0);
                // 版代支払日
                funXmlWrite(reqAry[i], "dt_han_payday_from", frm.txtHanPaydayFrom.value, 0);
                // 版代支払日
                funXmlWrite(reqAry[i], "dt_han_payday_to", frm.txtHanPaydayTo.value, 0);

                // チェックボックス設定
                var mshiharai = 0;
                if(frm.chkMshiharai.checked == true) {
                	mshiharai = 1;
                }
                // 未支払
                funXmlWrite(reqAry[i], "kbn_mshiharai", mshiharai, 0);

                break;
            }
          // アップロードボタン押下：資材手配テーブル更新
        } else if  (XmlId.toString() == "RGEN3690") {
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:

				// 社員コード
				funXmlWrite(reqAry[i], "cd_shain", funXmlRead(xmlFGEN3690, "cd_shain", 0), 0);
				// 年
				funXmlWrite(reqAry[i], "nen", funXmlRead(xmlFGEN3690, "nen", 0), 0);
				// 追番
				funXmlWrite(reqAry[i], "no_oi", funXmlRead(xmlFGEN3690, "no_oi", 0), 0);
				// 枝番
				funXmlWrite(reqAry[i], "no_eda", funXmlRead(xmlFGEN3690, "no_eda", 0), 0);
				// 試作SEQ
				funXmlWrite(reqAry[i], "seq_shizai", funXmlRead(xmlFGEN3690, "seq_shizai", 0), 0);
				// 製品番号
				funXmlWrite(reqAry[i], "cd_shohin", funXmlRead(xmlFGEN3690, "cd_shohin", 0), 0);
				// 納入日
				funXmlWrite(reqAry[i], "nounyu_day", funXmlRead(xmlFGEN3690, "nounyu_day", 0), 0);
				// 版代
				funXmlWrite(reqAry[i], "han_pay", funXmlRead(xmlFGEN3690, "han_pay", 0), 0);
				// 版代支払日
				funXmlWrite(reqAry[i], "han_payday", funXmlRead(xmlFGEN3690, "han_payday", 0), 0);
				// 表示用ファイル名
				funXmlWrite(reqAry[i], "inputName", funXmlRead(xmlFGEN3690, "inputName", 0), 0);
				// ファイルパス
				funXmlWrite(reqAry[i], "filename", funXmlRead(xmlFGEN3690, "filename", 0), 0);

				break;
			}
        } else if (XmlId.toString() == "RGEN3700"){
			//検索ボタン押下
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
                funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", mode, 0);
				break;
			case 1:    //FGEN3700
				break;
			}
		}
    }
    return true;

}

//========================================================================================
// リストの生成
// 作成者：H.Shima
// 作成日：2014/09/12
// 引数１：取得XML
// 引数２：HTML
// 引数３：インデックス
// 引数４：最大行数
// 概要  ：リスト情報を生成する。
//========================================================================================
function DataSet(xmlResAry, html , i , cnt){

    if(i < cnt){
        var tableNm = "table";

        //レスポンスデータ取得-------------------------------------------------------------------------------
        var row_no           = funXmlRead_3(xmlResAry[2], tableNm, "row_no", 0, i);							// 行No
       	var nm_tanto      = funXmlRead_3(xmlResAry[2], tableNm, "nm_tanto", 0, i);						// 担当者
        var naiyo         = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "naiyo", 0, i));							// 内容
        var cd_shohin     = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "cd_shohin", 0, i));						// 製品（商品）コード
        var nm_shohin     = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_shohin", 0, i));						// 製品（商品）名
        var nisugata         = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nisugata", 0, i));			// 荷姿／入数（漢字）
        var target_shizai = funXmlRead_3(xmlResAry[2], tableNm, "nm_taisyo_shizai", 0, i);							// 対象資材（リテラル名）
        var nm_hattyusaki    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_hattyusaki", 0, i));		// 発注先
        var cd_hattyusaki    = funXmlRead_3(xmlResAry[2], tableNm, "cd_hattyusaki", 0, i);					// 発注先コード
        var nm_nounyusaki = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nounyusaki", 0, i));					// 納入先（製造工場）
        var cd_shizai_old    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "cd_shizai_old", 0, i));		// 旧資材コード
        var cd_shizai        = funXmlRead_3(xmlResAry[2], tableNm, "cd_shizai", 0, i);						// 資材コード
        var nm_shizai     = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_shizai", 0, i));						// 資材名
        var sekkei1       = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "sekkei1", 0, i));			// 設計①
        var sekkei2       = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "sekkei2", 0, i));			// 設計②
        var sekkei3       = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "sekkei3", 0, i));			// 設計③
        var zaishitsu     = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "zaishitsu", 0, i));			// 材質
        var biko_tehai    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "biko_tehai", 0, i));		// 備考
        var printcolor    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "printcolor", 0, i));		// 印刷色
        var no_color      = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "no_color", 0, i));			// 色番号
        var henkounaiyoushosai   = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "henkounaiyoushosai", 0, i));		// 変更内容詳細
        var nouki         = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nouki", 0, i));							// 納期
        var suryo         = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "suryo", 0, i));							// 数量
        var nounyu_day    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nounyu_day", 0, i));					// 納入日
        var han_pay       = funXmlRead_3(xmlResAry[2], tableNm, "han_pay", 0, i);						// 版代
        var dt_han_payday   = funXmlRead_3(xmlResAry[2], tableNm, "dt_han_payday", 0, i);				// 版代支払日
        // nullの場合'1900/01/01'に更新されるので'1900/01/01'の場合""に更新
        if (dt_han_payday == '1900/01/01') {
        	dt_han_payday = "";
        }
        var han_upload    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "file_path_aoyaki", 0, i));				// 青焼アップロード
        var nm_file       = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_file_aoyaki", 0, i));				// 保存ファイル名

        var cd_shain      = funXmlRead_3(xmlResAry[2], tableNm, "cd_shain", 0, i);						// 社員コード
        var nen           = funXmlRead_3(xmlResAry[2], tableNm, "nen", 0, i);							// 年
        var no_oi         = funXmlRead_3(xmlResAry[2], tableNm, "no_oi", 0, i);							// 追番
        var seq_shizai    = funXmlRead_3(xmlResAry[2], tableNm, "seq_shizai", 0, i);					// 試作SEQ
        var no_eda        = funXmlRead_3(xmlResAry[2], tableNm, "no_eda", 0, i);						// 枝番
        var flg_status    = funXmlRead_3(xmlResAry[2], tableNm, "flg_status", 0, i);					// 手配ステータス
        var toroku_disp   = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "dt_koshin_disp", 0, i));	// 登録日

        var objColor;
        objColor = henshuOkColor;

        // 未入力色
        var notInputColor     = "#99ffff";
        // 未手配色
        var notArrangeColor   = "#ffbbff";

        //HTML出力オブジェクト設定---------------------------------------------------------------------------
        //TR行開始
        var output_html = "";

        //行No
        if("3" === flg_status){
            // 手配済み
            output_html += "<tr class=\"disprow\" bgcolor=\"" + deactiveSelectedColor + "\">";
        } else if("2" === flg_status) {
            // 未手配
            output_html += "<tr class=\"disprow\" bgcolor=\"" + notArrangeColor + "\">";
        } else {
            // 未入力
            output_html += "<tr class=\"disprow\" bgcolor=\"" + notInputColor + "\">";
        }

        //行No
        output_html += "    <td class=\"column\" width=\"30\"  align=\"right\">";
        output_html += "        <input type=\"text\" id=\"no_row_" + i + "\" name=\"no_row_" + i + "\" style=\"background-color:transparent;width:26px;border-width:0px;text-align:left\" readOnly value=\"" + row_no + "\" >";
        output_html += "        <input type=\"hidden\" id=\"cd_shain_" + i + "\" name=\"cd_shain_" + i + "\" readOnly value=\"" + cd_shain + "\" >";
        output_html += "        <input type=\"hidden\" id=\"nen_" + i + "\" name=\"nen_" + i + "\" readOnly value=\"" + nen + "\" >";
        output_html += "        <input type=\"hidden\" id=\"no_oi_" + i + "\" name=\"no_oi_" + i + "\" readOnly value=\"" + no_oi + "\" >";
        output_html += "        <input type=\"hidden\" id=\"seq_shizai_" + i + "\" name=\"seq_shizai_" + i + "\" readOnly value=\"" + seq_shizai + "\" >";
        output_html += "        <input type=\"hidden\" id=\"no_eda_" + i + "\" name=\"no_eda_" + i + "\" readOnly value=\"" + no_eda + "\" >";
        output_html += "    </td>";

        //選択ボタン
        output_html += "    <td class=\"column\" width=\"30\" align=\"center\">";
        output_html += "        <input type=\"radio\" id=\"chk\" name=\"chk\" onclick=\"clickItiran(" + i + ");\" style=\"width:28px;\" value=\"" + i + "\" tabindex=\"-1\">";
        output_html += "    </td>";

        //担当者
        output_html += "    <td class=\"column\" width=\"100\" align=\"left\" id=\"nm_tanto_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"nm_tanto_" + i + "\" name=\"nm_tanto_" + i + "\" style=\"background-color:transparent;width:96px;border-width:0px;text-align:left\" readOnly value=\"" + nm_tanto + "\" >";
        output_html += "    </td>";

        //内容
        output_html += "    <td class=\"column\" width=\"200\" align=\"left\" id=\"naiyo_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"naiyo_" + i + "\" name=\"naiyo_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + naiyo + "\" >";
        output_html += "    </td>";

        //製品（商品）
        output_html += "    <td class=\"column\" width=\"50\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"cd_shohin_" + i + "\" name=\"cd_shohin_" + i + "\" style=\"background-color:transparent;width:47;border-width:0px;text-align:left\" readOnly value=\"" + fillsZero(cd_shohin,6) + "\" >";
        output_html += "    </td>";

        //製品（商品）名
        output_html += "    <td class=\"column\" width=\"150\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nm_shohin_" + i + "\" name=\"nm_shohin_" + i + "\" style=\"background-color:transparent;width:144px;border-width:0px;text-align:left\" readOnly value=\"" + nm_shohin + "\" >";
        output_html += "    </td>";

        //荷姿
        output_html += "    <td class=\"column\" width=\"150\" align=\"left\" id=\"nisugata_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"nisugata_" + i + "\" name=\"nisugata_" + i + "\" style=\"background-color:transparent;width:145px;border-width:0px;text-align:left\" readOnly value=\"" + nisugata + "\" >";
        output_html += "    </td>";

        //対象資材
        output_html += "    <td class=\"column\" width=\"120\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nm_target_shizai_" + i + "\" name=\"nm_target_shizai_" + i + "\" style=\"background-color:transparent;width:115px;border-width:0px;text-align:left\" readOnly value=\"" + target_shizai + "\" >";
        output_html += "    </td>";

        //発注先
        output_html += "    <td class=\"column\" width=\"150\" align=\"left\" id=\"nm_hattyusaki_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"nm_hattyusaki_" + i + "\" name=\"nm_hattyusaki_" + i + "\" style=\"background-color:transparent;width:144px;border-width:0px;text-align:left\" readOnly value=\"" + nm_hattyusaki + "\" >";
        output_html += "    </td>";

        //納入先
        output_html += "    <td class=\"column\" width=\"150\" align=\"left\" id=\"nm_nounyusaki_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"nm_nounyusaki_" + i + "\" name=\"nm_nounyusaki_" + i + "\" style=\"background-color:transparent;width:144px;border-width:0px;text-align:left\" readOnly value=\"" + nm_nounyusaki + "\" >";
        output_html += "    </td>";

        //旧資材コード
        output_html += "    <td class=\"column\" width=\"50\" align=\"left\" id=\"cd_shizai_old-" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"cd_shizai_old_" + i + "\" name=\"cd_shizai_old_" + i + "\" style=\"background-color:transparent;width:48;border-width:0px;text-align:left\" readOnly value=\"" + fillsZero(cd_shizai_old,6) + "\" >";
        output_html += "    </td>";

        //資材コード
        output_html += "    <td class=\"column\" width=\"50\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"cd_shizai_" + i + "\" name=\"cd_shizai_" + i + "\" style=\"background-color:transparent;width:47px;border-width:0px;text-align:left\" readOnly value=\"" + fillsZero(cd_shizai, 6) + "\" >";
        output_html += "    </td>";

        //資材名
        output_html += "    <td class=\"column\" width=\"150\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nm_shizai_" + i + "\" name=\"nm_shizai_" + i + "\" style=\"background-color:transparent;width:144;border-width:0px;text-align:left\" readOnly value=\"" + nm_shizai + "\" >";
        output_html += "    </td>";

        //設計①
        output_html += "    <td class=\"column\" width=\"200\" align=\"left\" id=\"sekkei1_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"sekkei1_" + i + "\" name=\"sekkei1_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + sekkei1 + "\" >";
        output_html += "    </td>";

        //設計②
        output_html += "    <td class=\"column\" width=\"195\" align=\"left\" id=\"sekkei2_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"sekkei2_" + i + "\" name=\"sekkei2_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + sekkei2 + "\" >";
        output_html += "    </td>";

        //設計③
        output_html += "    <td class=\"column\" width=\"195\" align=\"left\" id=\"sekkei3_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"sekkei3_" + i + "\" name=\"sekkei3_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + sekkei3 + "\" >";
        output_html += "    </td>";

        //材質
        output_html += "    <td class=\"column\" width=\"195\" align=\"left\" id=\"zaishitsu_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"zaishitsu_" + i + "\" name=\"zaishitsu_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + zaishitsu + "\" >";
        output_html += "    </td>";

        //備考
        output_html += "    <td class=\"column\" width=\"294\" align=\"left\" id=\"biko_tehai_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"biko_tehai_" + i + "\" name=\"biko_tehai_" + i + "\" style=\"background-color:transparent;width:294;border-width:0px;text-align:left\" readOnly value=\"" + biko_tehai + "\" >";
        output_html += "    </td>";

        //印刷色
        output_html += "    <td class=\"column\" width=\"195\" align=\"left\" id=\"printcolor_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"printcolor_" + i + "\" name=\"printcolor_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + printcolor + "\" >";
        output_html += "    </td>";

        //色番号
        output_html += "    <td class=\"column\" width=\"196\" align=\"left\" id=\"no_color_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"no_color_" + i + "\" name=\"no_color_" + i + "\" style=\"background-color:transparent;width:194;border-width:0px;text-align:left\" readOnly value=\"" + no_color + "\" >";
        output_html += "    </td>";

        //変更内容詳細
        output_html += "    <td class=\"column\" width=\"195\" align=\"left\" id=\"henkounaiyoushosai_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"henkounaiyoushosai_" + i + "\" name=\"henkounaiyoushosai_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + henkounaiyoushosai + "\" >";
        output_html += "    </td>";

        //納期
        output_html += "    <td class=\"column\" width=\"148\" align=\"left\" id=\"nouki_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"nouki_" + i + "\" name=\"nouki_" + i + "\" style=\"background-color:transparent;width:148px;border-width:0px;text-align:left\" readOnly value=\"" + nouki + "\" >";
        output_html += "    </td>";

        //数量
        output_html += "    <td class=\"column\" width=\"148\" align=\"left\" id=\"suryo_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"suryo_" + i + "\" name=\"suryo_" + i + "\" style=\"background-color:transparent;width:148px;border-width:0px;text-align:left\" readOnly value=\"" + suryo + "\" >";
        output_html += "    </td>";

        //登録日
        output_html += "    <td class=\"column\" width=\"148\" align=\"left\" id=\"toroku_disp_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"toroku_disp_" + i + "\" name=\"toroku_disp_" + i + "\" style=\"background-color:transparent;width:148px;border-width:0px;text-align:left\" readOnly value=\"" + toroku_disp + "\" >";
        output_html += "    </td>";

        //納入日
        output_html += "    <td class=\"column\" width=\"98\" align=\"left\" id=\"nounyu_day_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"nounyu_day_" + i + "\" name=\"nounyu_day_" + i + "\" style=\"background-color:transparent;width:96px;border-width:0px;text-align:left\" readOnly value=\"" + nounyu_day + "\" >";
        output_html += "    </td>";

        //版代
        output_html += "    <td class=\"column ninput\" width=\"148\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"han_pay_" + i + "\" name=\"han_pay_" + i + "\" style=\"background-color:transparent;width:148px;border-width:0px;text-align:left\" value=\"" + han_pay + "\" >";
        output_html += "    </td>";

        //版代支払日
        output_html += "    <td class=\"column \" width=\"98\" align=\"left\" >";
        output_html += "        <input class=\"disb_text\" type=\"text\" id=\"han_payday_" + i + "\" name=\"han_payday_" + i + "\" style=\"background-color:transparent;width:96px;border-width:0px;text-align:left\" value=\"" + dt_han_payday + "\" >";
        output_html += "    </td>";

        //版下（青焼）アップロード
        output_html += "    <td class=\"column\" width=\"468\" align=\"left\" >";
        //保存されているファイル名
        output_html += "        <input type=\"hidden\" id=\"nm_file_" + i + "\" name=\"nm_file_" + i + "\" value=\"" + nm_file + "\" tabindex=\"-1\">";
        output_html += "        <div style=\"position: relative;\">";
        // 参照ボタン
        output_html += "            <input type=\"file\" id=\"filename_" + i + "\" name=\"filename_" + i + "\" class=\"normalbutton\" size=\"464\" style=\"width:464px;\"";
        output_html += "onChange=\"funChangeFile(" + i + ")\" onclick=\"funSetInput(" + i + ")\" onkeydown=\"funEnterFile(" + i + ", event.keyCode);\" >";
        // 表示用ファイル名
        output_html += "            <span style=\"position: absolute; top: 0px; left: 0px; z-index:1;\">";
        output_html += "                <input type=\"text\" id=\"inputName_" + i + "\" name=\"inputName_" + i + "\" value=\""+ funXmlRead_3(xmlResAry[2], tableNm, "nm_file_aoyaki", 0, i) + "\" size=\"76\" readonly tabindex=\"-1\" >";
        output_html += "            </sapn>";
        output_html += "        </div>";
        output_html += "    </td>";


        //TR行閉め
        output_html += "</tr>";
        html += output_html;

        //再帰処理（次データのHTML生成）
        setTimeout(function(){ DataSet( xmlResAry , html , ( i + 1 ) , cnt ); }, 0);
    } else {
        //一覧内にHTML設定
        var obj = document.getElementById("divMeisai");
        html = html + "</table>";
        obj.innerHTML = html;

        // 選択行の初期化
        funSetCurrentRow("");

        //表示終了後に検索系アクションを操作可能
        document.getElementById("btnSearch").disabled = false;
        document.getElementById("btnClear").disabled = false;
        document.getElementById("btnEnd").disabled = false;
        document.getElementById("btnUpLoad").disabled = false;
        document.getElementById("btnOutput").disabled = false;

        var limit_over = funXmlRead_3(xmlResAry[2], "table", "limit_over", 0, (i - 1));
        if("0" != limit_over){
        	funErrorMsgBox(E000051 + limit_over + E000052);
        }

        xmlResAry = null;
        html = null;

        // メッセージの非表示
        funClearRunMessage();

        //処理終了
        return true;
    }
}

//========================================================================================
// 検索処理
// 作成者：H.Shima
// 作成日：2014/09/09
// 概要  ：条件より検索を行い、結果を一覧に表示
//========================================================================================
function funSearch(){
    var frm = document.frm00; // ﾌｫｰﾑへの参照

    // 手配区分選択チェック
    var checkFlg = frm.chkTehaizumi.checked;

    if(!checkFlg){
        funErrorMsgBox(E000038);
        return false;
    }
    funClearList();
    funShowRunMessage();

    setTimeout(function(){ funDataSearch() }, 0);
}

//========================================================================================
// データ検索処理
// 作成者：H.Shima
// 作成日：2014/09/09
// 概要  ：条件より検索を行い、結果を一覧に表示
//========================================================================================
function funDataSearch(){

    var frm = document.frm00; // ﾌｫｰﾑへの参照
    var XmlId = "RGEN3330";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3330");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3330I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3330O);

    // 引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }
    // ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3330, xmlReqAry, xmlResAry,
            1) == false) {
        //処理中ﾒｯｾｰｼﾞ非表示
        funClearRunMessage();

        // 編集モード戻し
        funChangeEditResearch();
        // 編集チェックボックスチェック外す
        frm.chkEdit.checked = false;
        // 編集チェックボックス非活性
        frm.chkEdit.disabled = true;

        frm.btnUpLoad.disabled = true;
        frm.btnOutput.disabled = true;
        return false;
    }


    // 編集チェック有で検索された場合
    if (frm.chkEdit.checked) {
    	funChangeEditResearch();
    	// 編集チェックボックスチェック外す
        frm.chkEdit.checked = false;
    }

    //表示中に検索系アクションを操作不可
    frm.btnUpLoad.disabled = true;
    frm.btnOutput.disabled = true;
    frm.btnSearch.disabled = true;
    frm.btnClear.disabled = true;
    frm.btnCompletion.disabled = true;
    frm.btnEnd.disabled = true;

    // 検索結果行数の設定
    loop_cnt = funXmlRead(xmlResAry[2], "loop_cnt", 0);
    frm.hidListRow.value = loop_cnt;


    var output_html = "";
    output_html = output_html + "<table cellpadding=\"0\" id=\"tblList\" cellspacing=\"0\" border=\"1\">";

    setTimeout(function(){ DataSet(xmlResAry, output_html ,0 ,loop_cnt); }, 0);

    frm.chkEdit.disabled = false;

    return true;

}

//========================================================================================
// コンボボックス作成処理
// 作成者：H.Shima
// 作成日：2014/09/19
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②xmlData  ：設定XML
//       ：③mode     ：モード
//       ：④kara     ：空白行許容モード
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
        case 1:    // 発注先ﾏｽﾀ
            atbName = "nm_hattyusaki";
            atbCd   = "cd_hattyusaki";
            break;

        case 2:    // リテラルマスタ
            atbName = "nm_literal";
            atbCd   = "cd_literal";
            break;

        case 3:    // 担当者
            atbName = "nm_user";
            atbCd   = "id_user";
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
// 一覧選択処理
// 作成者：H.Shima
// 作成日：2014/9/12
// 引数  ：インデックス
// 概要  ：選択行をハイライト
//========================================================================================
function clickItiran(row){
    funSetCurrentRow(row);
}

//========================================================================================
// クリア処理
// 作成者：H.Shima
// 作成日：2014/09/09
// 引数  ：なし
// 概要  ：検索条件を初期化する。
//========================================================================================
function funClear(){

    funClearJoken();
    funClearList();
}

//========================================================================================
// 検索条件クリアボタン押下処理
// 作成者：H.Shima
// 作成日：2014/09/11
// 引数  ：なし
// 概要  ：検索条件を初期化する
//========================================================================================
function funClearJoken(){
    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    frm.reset();
    // 手配済みにチェック
    frm.chkTehaizumi.checked = true;
    // Excelボタン非活性
    frm.btnOutput.disabled = true;

}

//========================================================================================
// リストクリア処理
// 作成者：H.Shima
// 作成日：2014/09/11
// 引数  ：なし
// 概要  ：一覧の情報を初期化する
//========================================================================================
function funClearList(){

    document.getElementById("divMeisai").innerHTML = "";
    funSetCurrentRow("");
}

//========================================================================================
// 重複排除
// 作成者：H.Shima
// 作成日：2014/10/02
// 引数  ：配列
// 概要  ：一意のリストを返却
//========================================================================================
function convertUniqueList(arrayObject) {

    for(var i = 0; i < (arrayObject.length - 1); i++){
        for(var j = (arrayObject.length - 1);i < j; j--){
            if(arrayObject[i] === arrayObject[j]){
                // 削除して配列を詰める
                arrayObject.splice(j, 1);
            }
        }
    }

    return arrayObject;
}

//========================================================================================
// 数値並び替え重複排除
// 作成者：H.Shima
// 作成日：2014/10/02
// 引数  ：配列
// 概要  ：ソートされた一意のリストを返却
//========================================================================================
function convertUniqueSortList(arrayObject) {
    // ソート
    arrayObject = arrayObject.sort(function(a, b) { return a - b;});

    // 重複削除
    var i = 0;
    var j = 1;

    while(j <= arrayObject.length){
        if(arrayObject[i] === arrayObject[j]){
            // 削除して配列を詰める
            arrayObject.splice(j, 1);
        } else {
            i++;
            j++;
        }
    }
    return arrayObject;
}

//========================================================================================
// 表示文字列の作成
// 作成者：H.Shima
// 作成日：2014/10/02
// 引数  ：配列
// 概要  ：画面表示用文字列を作成
//========================================================================================
function createDispString(arrayObject){
    var str = "";
    for(var i = 0;i < arrayObject.length; i++){
        var dispNo = (i + 1);
        if(i != 0){
            // 区切り
            str += " ";
        }
        str += "(" + dispNo + ")" + arrayObject[i];
    }
    return str;
}

//========================================================================================
// 終了処理
// 作成者：H.Shima
// 作成日：2014/09/09
// 引数  ：なし
// 概要  ：メニュー画面に戻る。
//========================================================================================
function funEnd(){

    var XmlId = "RGEN3700";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //検索条件に一致するデザインスペースデータを取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3700, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }
    //画面を閉じる
    close(self);

    return true;
}

//========================================================================================
// 空白時の設定
// 作成者：H.Shima
// 作成日：2014/09/19
//========================================================================================
function funSetNbsp(val) {

    if( val == "" || val == "NULL" ){
        val = "&nbsp;";
    }

    return val;
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
//ファイル変更
//作成者：BRC Koizumi
//作成日：2016/09/14
//引数  ：row   ：行番号
//概要  ：表示用ファイル名にセットする
//========================================================================================
function funChangeFile(row) {

	funSetInput(row);

	return true;
}

//========================================================================================
//参照ボタン押下処理
//作成者：BRC Koizumi
//作成日：2016/09/14
//引数  ：row   ：行番号
//概要  ：アップロードファイルを指定
//========================================================================================
function funSetInput(row) {

	var inputName = document.getElementById("inputName_" + row);		// 表示用ファイル名（ファイル名のみ）
	var fileName = document.getElementById("filename_" + row);			// 参照ファイル名（フルパス）

	// ファイルダイアログ
	if (fileName.value != "") {
		// 参照ファイルフルパスよりファイル名を取得し表示用にセット
		//（拡張子のチェック不要）
		inputName.value = funGetFileNm(fileName.value);
		// アップロードファイル名は文字色を変更
		inputName.style.color = "red";
	}
return true;

}

//========================================================================================
//ファイルパス編集処理
//作成者：BRC Koizumi
//作成日：2016/09/14
//引数  ：str   ファイルパス
//戻り値：ファイル名
//概要  ：ファイルパスを引数として渡して分解する
//========================================================================================
function funGetFileNm(str){

	var fileName = "";			// ファイル名
	var tmp = str.split("\\");	// ファイルパス（フォルダー、ファイル名）を分解

	// ファイル名を取得
	fileName = tmp[tmp.length - 1];

	return fileName;
}

//========================================================================================
//参照ボタンキー押下処理（ENTERキー対応）
//作成者：BRC Koizumi
//作成日：2016/09/14
//引数  ：row    ：行番号
//   keyCode：キーコード
//概要  ：アップロードファイルを指定
//========================================================================================
function funEnterFile(row, keyCode) {

	// ENTERキー押下時、参照ボタンクリックの動きを実行
	if (keyCode == 13) {
		var fileName = document.getElementById("inputName_" + row);			// 参照ボタン
		// ENTERキー押下でクリックイベントを発生させる
		fileName.click();
	} else {
		return false;
	}
}

//========================================================================================
// EXCEL出力処理
// 作成者：t2nakamura
// 作成日：2016/10/06
// 概要  ：Excel出力を行う
//========================================================================================
function funExcelOut() {
	// フォーム参照
	var frm = document.frm00;

	var XmlId = "RGEN3620";
	var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3620");
	var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3620I);
	var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3620O);

	// Excel出力確認ﾒｯｾｰｼﾞの表示
    if (funConfMsgBox(I000008) != ConBtnYes) {
        return false;
    }

    // XMLの初期化
    setTimeout("xmlFGEN3620I.src = '../../model/FGEN3620I.xml';", ConTimer);

    // 引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    // 出力実行
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3620, xmlReqAry, xmlResAry,
            1) == false) {
        return false;
    }

    // ファイルパスの退避
    frm.strFilePath.value = funXmlRead(xmlFGEN3620O, "URLValue", 0);

    //ﾀﾞｳﾝﾛｰﾄﾞ画面の起動
    funFileDownloadUrlConnect(ConConectGet, frm);

	return true;
}

//========================================================================================
// 編集モード切替
// 作成者：BRC Koizumi
// 作成日：2016/09/28
// 引数  ：
// 概要  ：編集モードの切り替えを行う
//========================================================================================
function funChangeEdit() {

	var edit = document.getElementById("chkEdit");		// 編集チェックボックス

	if(edit.checked == true){

		// ヘッダ(colgroup)
		document.getElementById("colHeadTanto").style.display = 'none';	// 担当者
		document.getElementById("colHeadNaiyo").style.display = 'none';	// 内容
		document.getElementById("colHeadNisugata").style.display = 'none';	// 荷姿
		document.getElementById("colHeadNmHattyusaki").style.display = 'none';	// 発注先
		document.getElementById("colHeadNmNounyusaki_").style.display = 'none';	// 納入先
		document.getElementById("colHeadCdShizaiOld").style.display = 'none';	// 旧資材コード
		document.getElementById("colHeadSekkei1").style.display = 'none';	// 設計①
		document.getElementById("colHeadSekkei2").style.display = 'none';	// 設計②
		document.getElementById("colHeadSekkei3").style.display = 'none';	// 設計③
		document.getElementById("colHeadZaishitsu").style.display = 'none';	// 材質
		document.getElementById("colHeadBikoTehai").style.display = 'none';	// 備考
		document.getElementById("colHeadPrintcolor").style.display = 'none';	// 印刷色
		document.getElementById("colHeadNo_color").style.display = 'none';	// 色番号
		document.getElementById("colHeadHenkounaiyoushosai").style.display = 'none';	// 変更内容詳細
		document.getElementById("colHeadNouki").style.display = 'none';	// 納期
		document.getElementById("colHeadSuryo").style.display = 'none';	// 数量
		document.getElementById("colHeadTorokuDisp").style.display = 'none';	// 登録日

		for (var i = 0; i < loop_cnt; i++){
			// 一覧
			document.getElementById("nm_tanto_" + i).style.display = 'none';		// 担当者
			document.getElementById("naiyo_" + i).style.display = 'none';			// 内容
			document.getElementById("nisugata_" + i).style.display = 'none';	// 荷姿
			document.getElementById("nm_hattyusaki_" + i).style.display = 'none';	// 発注先
			document.getElementById("nm_nounyusaki_" + i).style.display = 'none';	// 納入先
			document.getElementById("cd_shizai_old-" + i).style.display = 'none';	// 旧資材コード
			document.getElementById("sekkei1_" + i).style.display = 'none';	// 設計①
			document.getElementById("sekkei2_" + i).style.display = 'none';	// 設計②
			document.getElementById("sekkei3_" + i).style.display = 'none';	// 設計③
			document.getElementById("zaishitsu_" + i).style.display = 'none';	// 材質
			document.getElementById("biko_tehai_" + i).style.display = 'none';	// 備考
			document.getElementById("printcolor_" + i).style.display = 'none';	// 印刷色
			document.getElementById("no_color_" + i).style.display = 'none';	// 色番号
			document.getElementById("henkounaiyoushosai_" + i).style.display = 'none';	// 変更内容詳細
			document.getElementById("nouki_" + i).style.display = 'none';	// 納期
			document.getElementById("suryo_" + i).style.display = 'none';	// 数量
			document.getElementById("toroku_disp_" + i).style.display = 'none';	// 登録日
		}

		document.getElementById("dataTable").style.width="1420px";
		document.getElementById("sclList").style.width="100%";

		document.getElementById("tblList").style.width="1360px";

	} else if (edit.checked == false){

		// ヘッダ
		document.getElementById("colHeadTanto").style.display = '';	// 担当者
		document.getElementById("colHeadNaiyo").style.display = '';	// 内容
		document.getElementById("colHeadNisugata").style.display = '';	// 荷姿
		document.getElementById("colHeadNmHattyusaki").style.display = '';	// 発注先
		document.getElementById("colHeadNmNounyusaki_").style.display = '';	// 納入先
		document.getElementById("colHeadCdShizaiOld").style.display = '';	// 旧資材コード
		document.getElementById("colHeadSekkei1").style.display = '';	// 設計①
		document.getElementById("colHeadSekkei2").style.display = '';	// 設計②
		document.getElementById("colHeadSekkei3").style.display = '';	// 設計③
		document.getElementById("colHeadZaishitsu").style.display = '';	// 材質
		document.getElementById("colHeadBikoTehai").style.display = '';	// 備考
		document.getElementById("colHeadPrintcolor").style.display = '';	// 印刷色
		document.getElementById("colHeadNo_color").style.display = '';	// 色番号
		document.getElementById("colHeadHenkounaiyoushosai").style.display = '';	// 変更内容詳細
		document.getElementById("colHeadNouki").style.display = '';	// 納期
		document.getElementById("colHeadSuryo").style.display = '';	// 数量
		document.getElementById("colHeadTorokuDisp").style.display = '';	// 登録日

		for (var i = 0; i < loop_cnt; i++){
			// 一覧
			document.getElementById("nm_tanto_" + i).style.display = '';		// 担当者
			document.getElementById("naiyo_" + i).style.display = '';			// 内容
			document.getElementById("nisugata_" + i).style.display = '';	// 荷姿
			document.getElementById("nm_hattyusaki_" + i).style.display = '';	// 発注先
			document.getElementById("nm_nounyusaki_" + i).style.display = '';	// 納入先
			document.getElementById("cd_shizai_old-" + i).style.display = '';	// 旧資材コード
			document.getElementById("sekkei1_" + i).style.display = '';	// 設計①
			document.getElementById("sekkei2_" + i).style.display = '';	// 設計②
			document.getElementById("sekkei3_" + i).style.display = '';	// 設計③
			document.getElementById("zaishitsu_" + i).style.display = '';	// 材質
			document.getElementById("biko_tehai_" + i).style.display = '';	// 備考
			document.getElementById("printcolor_" + i).style.display = '';	// 印刷色
			document.getElementById("no_color_" + i).style.display = '';	// 色番号
			document.getElementById("henkounaiyoushosai_" + i).style.display = '';	// 変更内容詳細
			document.getElementById("nouki_" + i).style.display = '';	// 納期
			document.getElementById("suryo_" + i).style.display = '';	// 数量
			document.getElementById("toroku_disp_" + i).style.display = '';	// 登録日
			document.getElementById("nounyu_day_" + i).style.display = '';	// 納入日
		}
		document.getElementById("dataTable").style.width="4365px";
		document.getElementById("sclList").style.width="100%";

		document.getElementById("tblList").style.width="4313px";
	}
	return true;
}

//========================================================================================
// 編集モード戻し
// 作成者：BRC Koizumi
// 作成日：2016/09/28
// 引数  ：
// 概要  ：編集モードで検索された場合、戻す
//========================================================================================
function funChangeEditResearch() {

	var edit = document.getElementById("chkEdit");		// 編集チェックボックス

	if(edit.checked == true){
		// ヘッダ
		document.getElementById("colHeadTanto").style.display = '';	// 担当者
		document.getElementById("colHeadNaiyo").style.display = '';	// 内容
		document.getElementById("colHeadNisugata").style.display = '';	// 荷姿
		document.getElementById("colHeadNmHattyusaki").style.display = '';	// 発注先
		document.getElementById("colHeadNmNounyusaki_").style.display = '';	// 納入先
		document.getElementById("colHeadCdShizaiOld").style.display = '';	// 旧資材コード
		document.getElementById("colHeadSekkei1").style.display = '';	// 設計①
		document.getElementById("colHeadSekkei2").style.display = '';	// 設計②
		document.getElementById("colHeadSekkei3").style.display = '';	// 設計③
		document.getElementById("colHeadZaishitsu").style.display = '';	// 材質
		document.getElementById("colHeadBikoTehai").style.display = '';	// 備考
		document.getElementById("colHeadPrintcolor").style.display = '';	// 印刷色
		document.getElementById("colHeadNo_color").style.display = '';	// 色番号
		document.getElementById("colHeadHenkounaiyoushosai").style.display = '';	// 変更内容詳細
		document.getElementById("colHeadNouki").style.display = '';	// 納期
		document.getElementById("colHeadSuryo").style.display = '';	// 数量
		document.getElementById("colHeadTorokuDisp").style.display = '';	// 登録日

		document.getElementById("dataTable").style.width="4365px";
		document.getElementById("sclList").style.width="100%";

		document.getElementById("tblList").style.width="4315px";
	}
	return true;
}

//========================================================================================
//登録ボタン押下時チェック処理
//作成者：May Thu
//作成日：2016/09/30
//引数  ：ファイル名
//戻り値：Boolean    true：登録可   false：不正文字あり
//概要  ：ファイル名の中に不正文字が含まれているかチェックする
//========================================================================================
function funChkFileNm(str){

	// 不正文字が含まれている場合、falseを返す
	var ret = str.match(/[;/?:@&=+$,%# \s]/);// 半角空白校
	if (ret) {
		return false;
	}
	return true;

}
//========================================================================================
//検索条件の再設定処理
//作成者：E.Kitazawa
//作成日：2014/09/01
//引数  ：なし
//戻り値：全条件を選択していない時：""
//      選択時：選択条件をつなげたコード文字列
//概要  ：検索条件をチェックし、条件コードをつなげた文字列を返す
//========================================================================================
function funSearchConditionSet(resultAray) {

	var frm = document.frm00;		//ﾌｫｰﾑへの参照
	// 手配済チェック
	var chkTehaizumi = funXmlRead(resultAray, "chkTehaizumi" , 0);

	if (chkTehaizumi == 1) {
		frm.chkTehaizumi.checked = true;
	} else {
		frm.chkTehaizumi.checked = false;
	}

	// 未入力
	frm.inputSeihinCd.value = funXmlRead(resultAray, "txtShizaiCd", 0);
	// 資材コード
	frm.txtShizaiCd.value = funXmlRead(resultAray, "txtShizaiCd", 0);
	// 資材名
	frm.txtShizaiNm.value = funXmlRead(resultAray, "txtShizaiNm", 0);
	// 旧資材コード
	frm.txtOldShizaiCd.value = funXmlRead(resultAray, "txtOldShizaiCd", 0);
	// 製品コード
	frm.txtSyohinCd.value = funXmlRead(resultAray, "txtSyohinCd", 0);
	// 製品名
	frm.txtSyohinNm.value = funXmlRead(resultAray, "txtSyohinNm", 0);
	// 納入先
	frm.txtSeizoukojo.value = funXmlRead(resultAray, "txtSeizoukojo", 0);
	// 対象資材
	frm.ddlShizai.value = funXmlRead(resultAray, "ddlShizai", 0);

	// 発注先
	frm.ddlHattyusaki.value = funXmlRead(resultAray, "ddlHattyusaki", 0);
	// 発注者
	frm.ddlTanto.value = funXmlRead(resultAray, "ddlTanto", 0);
	// 発注日from
	frm.txtHattyubiFrom.value = funXmlRead(resultAray, "txtHattyubiFrom", 0);
	// 発注日to
	frm.txtHattyubiTo.value = funXmlRead(resultAray, "txtHattyubiTo", 0);
	// 納入日from
	frm.txtNounyudayFrom.value = funXmlRead(resultAray, "txtNounyudayFrom", 0);
	// 納入日to
	frm.txtNounyudayTo.value = funXmlRead(resultAray, "txtNounyudayTo", 0);
	// 版代支払日from
	frm.txtHanPaydayFrom.value = funXmlRead(resultAray, "txtHanPaydayFrom", 0);
	// 版代支払日to
	frm.txtHanPaydayTo.value = funXmlRead(resultAray, "txtHanPaydayTo", 0);
	// 未支払
	var chkMshiharai = funXmlRead(resultAray, "chkMshiharai", 0)
	if (chkMshiharai == 1) {
		frm.chkMshiharai.checked = true;
	} else {
		frm.chkMshiharai.checked = false;
	}


	return true;
}

//========================================================================================
//納入先（製造工場）入力時
//作成者：BRC Koizumi
//作成日：2016/10/05
//引数  ：なし
//概要  ：製造工場コード設定
//========================================================================================
function funChangeKojo() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照

	// 納入先（製造工場）コードを設定
//	frm.seizoKojoCd.value = funGetXmldata(xmlSA290O, "nm_busho", frm.txtSeizoukojo.value, "cd_busho");
	// Excelボタン非活性
	funChangeSearch();

	return;
}

//========================================================================================
//検索条件変更
//作成者：BRC Koizumi
//作成日：2016/10/13
//引数  ：なし
//概要  ：Excelボタン非活性
//========================================================================================
function funChangeSearch() {

	// ボタン非活性
	document.getElementById("btnOutput").disabled = true;

	return;
}


//========================================================================================
// 検索条件変更
// 作成者：BRC Koizumi
// 作成日：2016/10/13
// 引数  ：なし
// 概要  ：Excelボタン非活性
//========================================================================================
function funTextChange(value){
	// 検索条件変更時、ﾃｷｽﾄが変更された場合のみボタンを非活性にする
	if (textValue == value) {
		return;
	}

	funChangeSearch();
	return;
}