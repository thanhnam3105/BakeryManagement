//========================================================================================
// 初期表示処理
// 作成者：H.Shima
// 作成日：2014/09/4
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

    var frm = document.frm00; // ﾌｫｰﾑへの参照

    //画面設定
    funInitScreen(ConShizaiCodeListId);

    // 画面情報を取得・設定
    if (funGetInfo(1) == false) {
        return false;
    }

    document.getElementById("lblTitle").innerHTML = "複数一括選択";

    // 未入力にチェック
    frm.chkMinyuryoku.checked = true;
    // 編集非活性
    frm.chkEdit.disabled = true;

    // ボタン非活性（アップロード、Excel）
    frm.btnUpLoad.disabled = true;
    frm.btnOutput.disabled = true;
    frm.btnCompletion.disabled = true;

    // 表示時の検索
    funSearch();

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
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3200","FGEN3300","FGEN3310");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3200I, xmlFGEN3300I, xmlFGEN3310I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3200O, xmlFGEN3300O, xmlFGEN3310O);

    // 引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
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
            }
        }
        // 検索
        else if (XmlId.toString() == "RGEN3330") {
            switch (i) {
            case 0:    // USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;

            case 1:    // FGEN3330
                // チェックボックス設定
                var tehaizumi = 0;
                if(frm.chkTehaizumi.checked == true) {
                    tehaizumi = 1;
                }
                var mitehai = 0;
                if(frm.chkMitehai.checked == true) {
                    mitehai = 1;
                }
                var minyuryoku = 0;
                if(frm.chkMinyuryoku.checked == true) {
                    minyuryoku = 1;
                }
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
        } // Excel出力検索
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
                // 版代支払日From
                funXmlWrite(reqAry[i], "dt_han_payday_from", frm.txtHanPaydayFrom.value, 0);
                // 版代支払日To
                funXmlWrite(reqAry[i], "dt_han_payday_to", frm.txtHanPaydayTo.value, 0);

                // チェックボックス設定
                var mshiharai = 0;
                if(frm.chkMshiharai.checked == true) {
                	mshiharai = 1;
                }
                // 未支払い
                funXmlWrite(reqAry[i], "kbn_mshiharai", mshiharai, 0);

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

        //レスポンスデータ取得--------------------------------------------------------------------------------------------------------------------
        var row_no           = funXmlRead_3(xmlResAry[2], tableNm, "row_no", 0, i);							// 行No
       	var nm_tanto      = funXmlRead_3(xmlResAry[2], tableNm, "nm_tanto", 0, i);						// 担当者
        var naiyo         = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "naiyo", 0, i));							// 内容
        var cd_shohin     = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "cd_shohin", 0, i));						// 製品（商品）コード
        var nm_shohin     = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_shohin", 0, i));						// 製品（商品）名
        var nisugata         = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nisugata", 0, i));			// 荷姿／入数（漢字）
        var cd_target_shizai = funXmlRead_3(xmlResAry[2], tableNm, "cd_taisyo_shizai", 0, i);						// 対象資材コード
        var nm_target_shizai = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_taisyo_shizai", 0, i));	// 対象資材
        var nm_hattyusaki    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_hattyusaki", 0, i));		// 発注先
        var cd_hattyusaki    = funXmlRead_3(xmlResAry[2], tableNm, "cd_hattyusaki", 0, i);					// 発注先コード
        var nm_nounyusaki = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nounyusaki", 0, i));					// 納入先（製造工場）
        var cd_shizai_old    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "cd_shizai_old", 0, i));		// 旧資材コード
        var cd_shizai        = funXmlRead_3(xmlResAry[2], tableNm, "cd_shizai", 0, i);						// 資材コード
        var nm_shizai        = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_shizai", 0, i));			// 資材名称
        var sekkei1       = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "sekkei1", 0, i));			// 設計①
        var sekkei2       = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "sekkei2", 0, i));			// 設計②
        var sekkei3       = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "sekkei3", 0, i));			// 設計③
        var zaishitsu     = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "zaishitsu", 0, i));			// 材質
        var biko_tehai    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "biko_tehai", 0, i));		// 備考
        var printcolor    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "printcolor", 0, i));		// 印刷色
        var no_color      = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "no_color", 0, i));			// 色番号
        var henkounaiyoushosai   = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "henkounaiyoushosai", 0, i));		// 変更内容詳細
        var nouki         = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nouki", 0, i));		// 納期
        var suryo         = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "suryo", 0, i));		// 数量
        var nounyu_day    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nounyu_day", 0, i));					// 納入日
        var han_pay      = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "han_pay", 0, i));				// 版代
        var dt_han_payday   = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "dt_han_payday", 0, i));				// 版代支払日
        // nullの場合'1900/01/01'に更新されるので'1900/01/01'の場合""に更新
        if (dt_han_payday == '1900/01/01') {
        	dt_han_payday = "";
        }
        var han_upload   = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "file_path_aoyaki", 0, i));		// 青焼アップロード
        var nm_file      = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_file_aoyaki", 0, i));			// 保存ファイル名

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
        output_html += "    <td class=\"column\" width=\"29\"  align=\"right\">";
        output_html += "        <input type=\"text\" id=\"no_row_" + i + "\" name=\"no_row_" + i + "\" style=\"background-color:transparent;width:27px;border-width:0px;text-align:left\" readOnly value=\"" + row_no + "\" >";
        output_html += "        <input type=\"hidden\" id=\"cd_shain_" + i + "\" name=\"cd_shain_" + i + "\" readOnly value=\"" + cd_shain + "\" >";
        output_html += "        <input type=\"hidden\" id=\"nen_" + i + "\" name=\"nen_" + i + "\" readOnly value=\"" + nen + "\" >";
        output_html += "        <input type=\"hidden\" id=\"no_oi_" + i + "\" name=\"no_oi_" + i + "\" readOnly value=\"" + no_oi + "\" >";
        output_html += "        <input type=\"hidden\" id=\"seq_shizai_" + i + "\" name=\"seq_shizai_" + i + "\" readOnly value=\"" + seq_shizai + "\" >";
        output_html += "        <input type=\"hidden\" id=\"no_eda_" + i + "\" name=\"no_eda_" + i + "\" readOnly value=\"" + no_eda + "\" >";
        output_html += "    </td>";

        //選択ボタン
        output_html += "    <td class=\"column\" width=\"31\" align=\"center\">";
        output_html += "        <input type=\"checkbox\" name=\"chkShizai\" onclick=\"clickItiran(" + i + ");\" style=\"width:28px;\" tabindex=\"-1\" >";
        output_html += "    </td>";

        //担当者
        output_html += "    <td class=\"column\" width=\"99\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nm_tanto_" + i + "\" name=\"nm_tanto_" + i + "\" style=\"background-color:transparent;width:96px;border-width:0px;text-align:left\" readOnly value=\"" + nm_tanto + "\" >";
        output_html += "    </td>";

        //内容
        output_html += "    <td class=\"column\" width=\"195\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"naiyo_" + i + "\" name=\"naiyo_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + naiyo + "\" >";
        output_html += "    </td>";

        //製品（商品）コード
        output_html += "    <td class=\"column\" width=\"50\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"cd_shohin_" + i + "\" name=\"cd_shohin_" + i + "\" style=\"background-color:transparent;width:47;border-width:0px;text-align:left\" readOnly value=\"" + fillsZero(cd_shohin,6) + "\" >";
        output_html += "    </td>";

        //製品（商品）名
        output_html += "    <td class=\"column\" width=\"146\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nm_shohin_" + i + "\" name=\"nm_shohin_" + i + "\" style=\"background-color:transparent;width:144px;border-width:0px;text-align:left\" readOnly value=\"" + nm_shohin + "\" >";
        output_html += "    </td>";

        //荷姿
        output_html += "    <td class=\"column\" width=\"146\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nisugata_" + i + "\" name=\"nisugata_" + i + "\" style=\"background-color:transparent;width:144px;border-width:0px;text-align:left\" readOnly value=\"" + nisugata + "\" >";
        output_html += "    </td>";

        //対象資材
        output_html += "    <td class=\"column\" width=\"117\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nm_target_shizai_" + i + "\" name=\"nm_target_shizai_" + i + "\" style=\"background-color:transparent;width:115px;border-width:0px;text-align:left\" readOnly value=\"" + nm_target_shizai + "\" >";
        output_html += "        <input type=\"hidden\" id=\"cd_target_shizai_" + i + "\" name=\"cd_target_shizai_" + i + "\" readOnly value=\"" + cd_target_shizai + "\" >";
        output_html += "    </td>";

        //発注先
        output_html += "    <td class=\"column\" width=\"146\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nm_hattyusaki_" + i + "\" name=\"nm_hattyusaki_" + i + "\" style=\"background-color:transparent;width:144px;border-width:0px;text-align:left\" readOnly value=\"" + nm_hattyusaki + "\" >";
        output_html += "        <input type=\"hidden\" id=\"cd_hattyusaki_" + i + "\" name=\"cd_hattyusaki_" + i + "\" readOnly value=\"" + cd_hattyusaki + "\" >";
        output_html += "    </td>";

        //納入先（製造工場）
        output_html += "    <td class=\"column\" width=\"146\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nm_nounyusaki_" + i + "\" name=\"nm_nounyusaki_" + i + "\" style=\"background-color:transparent;width:144px;border-width:0px;text-align:left\" readOnly value=\"" + nm_nounyusaki + "\" >";
        output_html += "    </td>";

        //旧資材コード
        output_html += "    <td class=\"column\" width=\"50\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"cd_shizai_old_" + i + "\" name=\"cd_shizai_old_" + i + "\" style=\"background-color:transparent;width:47;border-width:0px;text-align:left\" readOnly value=\"" + fillsZero(cd_shizai_old,6) + "\" >";
        output_html += "    </td>";

        //資材コード
        output_html += "    <td class=\"column\" width=\"50\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"cd_shizai_" + i + "\" name=\"cd_shizai_" + i + "\" style=\"background-color:transparent;width:47px;border-width:0px;text-align:left\" readOnly value=\"" + fillsZero(cd_shizai, 6) + "\" >";
        output_html += "    </td>";

        //資材名
        output_html += "    <td class=\"column\" width=\"146\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nm_shizai_" + i + "\" name=\"nm_shizai_" + i + "\" style=\"background-color:transparent;width:144;border-width:0px;text-align:left\" readOnly value=\"" + nm_shizai + "\" >";
        output_html += "    </td>";

        //設計①
        output_html += "    <td class=\"column\" width=\"196\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"sekkei1_" + i + "\" name=\"sekkei1_" + i + "\" style=\"background-color:transparent;width:194;border-width:0px;text-align:left\" readOnly value=\"" + sekkei1 + "\" >";
        output_html += "    </td>";

        //設計②
        output_html += "    <td class=\"column\" width=\"195\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"sekkei2_" + i + "\" name=\"sekkei2_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + sekkei2 + "\" >";
        output_html += "    </td>";

        //設計③
        output_html += "    <td class=\"column\" width=\"195\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"sekkei3_" + i + "\" name=\"sekkei3_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + sekkei3 + "\" >";
        output_html += "    </td>";

        //材質
        output_html += "    <td class=\"column\" width=\"195\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"zaishitsu_" + i + "\" name=\"zaishitsu_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + zaishitsu + "\" >";
        output_html += "    </td>";

        //備考
        output_html += "    <td class=\"column\" width=\"294\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"biko_tehai_" + i + "\" name=\"biko_tehai_" + i + "\" style=\"background-color:transparent;width:294;border-width:0px;text-align:left\" readOnly value=\"" + biko_tehai + "\" >";
        output_html += "    </td>";

        //印刷色
        output_html += "    <td class=\"column\" width=\"195\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"printcolor_" + i + "\" name=\"printcolor_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + printcolor + "\" >";
        output_html += "    </td>";

        //色番号
        output_html += "    <td class=\"column\" width=\"196\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"no_color_" + i + "\" name=\"no_color_" + i + "\" style=\"background-color:transparent;width:194;border-width:0px;text-align:left\" readOnly value=\"" + no_color + "\" >";
        output_html += "    </td>";

        //変更内容詳細
        output_html += "    <td class=\"column\" width=\"195\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"henkounaiyoushosai_" + i + "\" name=\"henkounaiyoushosai_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + henkounaiyoushosai + "\" >";
        output_html += "    </td>";

        //納期
        output_html += "    <td class=\"column\" width=\"148\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nouki_" + i + "\" name=\"nouki_" + i + "\" style=\"background-color:transparent;width:148px;border-width:0px;text-align:left\" readOnly value=\"" + nouki + "\" >";
        output_html += "    </td>";

        //数量
        output_html += "    <td class=\"column\" width=\"148\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"suryo_" + i + "\" name=\"suryo_" + i + "\" style=\"background-color:transparent;width:148px;border-width:0px;text-align:left\" readOnly value=\"" + suryo + "\" >";
        output_html += "    </td>";

        //登録日
        output_html += "    <td class=\"column\" width=\"148\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"toroku_disp_" + i + "\" name=\"toroku_disp_" + i + "\" style=\"background-color:transparent;width:148px;border-width:0px;text-align:left\" readOnly value=\"" + toroku_disp + "\" >";
        output_html += "    </td>";

        //納入日
        output_html += "    <td class=\"column\" width=\"98\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nounyu_day_" + i + "\" name=\"nounyu_day_" + i + "\" style=\"background-color:transparent;width:96px;border-width:0px;text-align:left\" readOnly value=\"" + nounyu_day + "\" >";
        output_html += "    </td>";

        //版代
        output_html += "    <td class=\"column\" width=\"148\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"han_pay_" + i + "\" name=\"han_pay_" + i + "\" style=\"background-color:transparent;width:147px;border-width:0px;text-align:right\" readOnly value=\"" + funAddComma(han_pay) + "\" >";
        output_html += "    </td>";

        //版代支払日
        output_html += "    <td class=\"column\" width=\"98\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"han_payday_" + i + "\" name=\"han_payday_" + i + "\" style=\"background-color:transparent;width:96px;border-width:0px;text-align:left\" readOnly value=\"" + dt_han_payday + "\" >";
        output_html += "    </td>";

        //版下（青焼）アップロード
        output_html += "    <td class=\"column\" width=\"468\" align=\"left\" >";
        //保存されているファイル名
        output_html += "        <input type=\"hidden\" id=\"nm_file_" + i + "\" name=\"nm_file_" + i + "\" value=\"" + nm_file + "\" tabindex=\"-1\">";
        output_html += "        <div style=\"position: relative;\">";
        // 参照ボタン
        output_html += "            <input type=\"file\" id=\"filename_" + i + "\" name=\"filename_" + i + "\" class=\"normalbutton\" size=\"464\" style=\"width:464px;\"";
        output_html += "onChange=\"funChangeFile(" + i + ")\" onclick=\"funSetInput(" + i + ")\" onkeydown=\"funEnterFile(" + i + ", event.keyCode);\" disabled=\"disabled\" >";
        // 表示用ファイル名
        output_html += "            <span style=\"position: absolute; top: 0px; left: 0px; z-index:1;\">";
        output_html += "                <input type=\"text\" id=\"inputName_" + i + "\" name=\"inputName_" + i + "\" value=\"\" size=\"76\" readonly tabindex=\"-1\" >";
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
        document.getElementById("btnOutput").disabled = false;
        document.getElementById("btnSearch").disabled = false;
        document.getElementById("btnClear").disabled = false;
        document.getElementById("btnCompletion").disabled = false;
        document.getElementById("btnEnd").disabled = false;

        var limit_over = funXmlRead_3(xmlResAry[2], "table", "limit_over", 0, (i - 1));
        if("0" != limit_over){
            funErrorMsgBox("検索結果が制限件数を超えた為、上位" + limit_over + "件のみを表示します。");
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
    var checkFlg = frm.chkTehaizumi.checked || frm.chkMitehai.checked || frm.chkMinyuryoku.checked;
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
        return false;
    }

    //表示中に検索系アクションを操作不可
    document.getElementById("btnSearch").disabled = true;
    document.getElementById("btnClear").disabled = true;
    document.getElementById("btnCompletion").disabled = true;
    document.getElementById("btnEnd").disabled = true;

    // 検索結果行数の設定
    var loop_cnt = funXmlRead(xmlResAry[2], "loop_cnt", 0);
    frm.hidListRow.value = loop_cnt;

    var output_html = "";
    output_html = output_html + "<table cellpadding=\"0\" id=\"tblList\" cellspacing=\"0\" border=\"1\">";

    setTimeout(function(){ DataSet(xmlResAry, output_html ,0 ,loop_cnt); }, 0);

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
    // 選択完了ボタン非活性
    document.getElementById("btnCompletion").disabled = true;
}

//========================================================================================
// 選択完了ボタン押下処理
// 作成者：H.Shima
// 作成日：2014/09/11
// 引数  ：なし
// 概要  ：選択された資材コードに関連する商品コードを呼び出し元画面に表示する
//========================================================================================
function funCompletion(){

    var frm = document.frm00;                    // ﾌｫｰﾑへの参照
    var XmlId = "RGEN3340";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3340");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3340I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3340O);
    var tableNm = "table";

    //選択されていない場合（１度もチェックしていない場合）
    if(funGetCurrentRow().toString() == ""){
        funErrorMsgBox(E000002);
        return false;
    }
    //選択されている場合
    else{
        /* 入力チェック */
        // 行数取得
        var max_row = frm.hidListRow.value;

        // 検索結果が1件より多い場合
        if(1 < max_row){

            // 比較元発注先
            var store_cd_hattyusaki;
            // 比較元対象資材
            var store_cd_target_shizai;

            // 選択チェック
            for(var j = 0; j < max_row; j++){
                // チェックされている行のみチェック
                if(frm.chkShizai[j].checked){
                    // 発注先コードの取得
                    var obj_cd_hattyusaki    = document.getElementById("cd_hattyusaki_" + j);
                    var cd_hattyusaki        = obj_cd_hattyusaki.value;

                    var obj_cd_target_shizai = document.getElementById("cd_target_shizai_" + j);
                    var cd_target_shizai     = obj_cd_target_shizai.value;

                    // store_cd_hattyusakiとstore_cd_target_shizaiに値が含まれている場合はtrue
                    if(store_cd_hattyusaki && store_cd_target_shizai){
                        // 選択された発注先コードが異なればエラー
                        if(store_cd_hattyusaki != cd_hattyusaki){
                            funErrorMsgBox(E000034);
                            return false;
                        }
                         // 選択された対象資材コードが異なればエラー
                        if(store_cd_target_shizai != cd_target_shizai){
                            funErrorMsgBox(E000035);
                            return false;
                        }

                    }
                    // ループ1回目
                    else {
                        store_cd_hattyusaki = cd_hattyusaki;
                        store_cd_target_shizai = cd_target_shizai;
                    }
                }
            }
        }

        //XMLの初期化
        setTimeout("xmlFGEN3340I.src = '../../model/FGEN3340I.xml';", ConTimer);

        // 引数をXMLﾌｧｲﾙに設定
        if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
            funClearRunMessage();
            return false;
        }

        // ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
        if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3340, xmlReqAry, xmlResAry,
                1) == false) {
            return false;
        }

        // 表示
        funSyohinDisp(xmlResAry[2]);
    }
}

//========================================================================================
// 資材手配依頼書出力画面に表示
// 作成者：H.Shima
// 作成日：2014/10/02
// 引数  ：xmlData
// 概要  ：取得値を資材手配依頼書出力画面に表示
//========================================================================================
function funSyohinDisp(xmlData){

    var frmDa = window.dialogArguments.frm00;    //親ﾌｫｰﾑへの参照（明細フレーム）
    var docDa = window.dialogArguments.document; //親documentへの参照（明細フレーム）

    var tableNm = "table";

    var cd_taisyosizai = "";
    var cd_hattyusaki = "";

    // キー値を保持
    var hdn_cd_shain         = "";
    var hdn_nen              = "";
    var hdn_no_oi            = "";
    var hdn_seq_shizai       = "";
    var hdn_no_eda           = "";
    var hdn_shohin_cd        = "";

    var arrShohinObj   = new Array();
    var arrOldShizaiCd = new Array();
    var arrNewShizaiCd = new Array();

    // 資材手配出力インサート用
    var arrShohinObj2 = new Array();

    var loop_cnt = funXmlRead_3(xmlData, tableNm, "loop_cnt", 0, 0);
    var htmlstr = "";
    for(var i = 0; i < loop_cnt; i++){
        if(i != 0){
            // 区切り文字の設定
            hdn_cd_shain         += ConDelimiter;
            hdn_nen              += ConDelimiter;
            hdn_no_oi            += ConDelimiter;
            hdn_seq_shizai       += ConDelimiter;
            hdn_no_eda           += ConDelimiter;
            // 製品コード追加
            hdn_shohin_cd        += ConDelimiter;
        }
//        else {
//            cd_taisyosizai = funXmlRead_3(xmlData, tableNm, "cd_taisyosizai", 0, i);
//            //cd_hattyusaki  = funXmlRead_3(xmlData, tableNm, "cd_hattyusaki", 0, i);
//        }

        // 対象資材20161031追加
        cd_taisyosizai = funXmlRead_3(xmlData, tableNm, "cd_taisyosizai", 0, i);
        /* 表示用 */
        // [製品コード]:::[製品名]:::[荷姿]:::[内容]:::[納入先]
        var shohinCd = funXmlRead_3(xmlData, tableNm, "cd_shohin", 0, i);
        var shohinNm = funXmlRead_3(xmlData, tableNm, "nm_shohin", 0, i);
        var nisugata = funXmlRead_3(xmlData, tableNm, "name_nisugata", 0, i);
        var naiyo = funXmlRead_3(xmlData, tableNm, "naiyo", 0, i);				// 内容【KPX@1602367 】add
        var nounyusaki = funXmlRead_3(xmlData, tableNm, "nounyusaki", 0, i);	// 納入先【KPX@1602367 】add

        arrShohinObj.push(shohinCd + ConDelimiter + shohinNm + ConDelimiter + nisugata + ConDelimiter + naiyo + ConDelimiter + nounyusaki);
//        arrShohinObj.push(shohinCd + ConDelimiter + shohinNm + ConDelimiter + nisugata);
        // 旧資材コード
        arrOldShizaiCd.push(funXmlRead_3(xmlData, tableNm, "cd_shizai", 0, i));
        // 新資材コード
        arrNewShizaiCd.push(funXmlRead_3(xmlData, tableNm, "cd_shizai_new", 0, i));

        /* 保持用 */
        hdn_cd_shain         += funXmlRead_3(xmlData, tableNm, "cd_shain", 0, i);
        hdn_nen              += funXmlRead_3(xmlData, tableNm, "nen", 0, i);
        hdn_no_oi            += funXmlRead_3(xmlData, tableNm, "no_oi", 0, i);
        hdn_seq_shizai       += funXmlRead_3(xmlData, tableNm, "seq_shizai", 0, i);
        hdn_no_eda           += funXmlRead_3(xmlData, tableNm, "no_eda", 0, i);
        // 製品コード追加
        hdn_shohin_cd        += funXmlRead_3(xmlData, tableNm, "cd_shohin", 0, i);
        // 資材手配出力インサート用
        /*
         * 社員コード[0]
         * 年[1]
         * 追番[2]
         * seq番号[3]
         * 枝番[4]
         * 製品コード[5]
         * 製品名[6]
         * 荷姿[7]
         * 内容[8]
         * 納入先[9]
         * 旧資材コード[10]
         * 新資材コード[11]
         * 対象資材[12]
         * 版代支払日[13]
         * 版代[14]
         * 青焼ファイル名[15]
         * 青焼ファイルパス[16]
         */
        var insertData = 	  funXmlRead_3(xmlData, tableNm, "cd_shain", 0, i) + ConDelimiter
        					+ funXmlRead_3(xmlData, tableNm, "nen", 0, i) + ConDelimiter
        					+ funXmlRead_3(xmlData, tableNm, "no_oi", 0, i) + ConDelimiter
        					+ funXmlRead_3(xmlData, tableNm, "seq_shizai", 0, i) + ConDelimiter
        					+ funXmlRead_3(xmlData, tableNm, "no_eda", 0, i) + ConDelimiter
        					+ funXmlRead_3(xmlData, tableNm, "cd_shohin", 0, i) + ConDelimiter
        					+ funXmlRead_3(xmlData, tableNm, "nm_shohin", 0, i) + ConDelimiter
        					+ funXmlRead_3(xmlData, tableNm, "name_nisugata", 0, i) + ConDelimiter
        					+ funXmlRead_3(xmlData, tableNm, "naiyo", 0, i) + ConDelimiter
        					+ funXmlRead_3(xmlData, tableNm, "nounyusaki", 0, i) + ConDelimiter
        					+ funXmlRead_3(xmlData, tableNm, "cd_shizai", 0, i) + ConDelimiter
        					+ funXmlRead_3(xmlData, tableNm, "cd_shizai_new", 0, i) + ConDelimiter
        					+ funXmlRead_3(xmlData, tableNm, "cd_taisyosizai", 0, i) + ConDelimiter
        					+ funXmlRead_3(xmlData, tableNm, "dt_han_payday", 0, i) + ConDelimiter
        					+ funXmlRead_3(xmlData, tableNm, "han_pay", 0, i) + ConDelimiter
        					+ funXmlRead_3(xmlData, tableNm, "nm_file_aoyaki", 0, i) + ConDelimiter
        					+ funXmlRead_3(xmlData, tableNm, "file_path_aoyaki", 0, i) + ConDelimiter;

        var parent_object = window.dialogArguments.document.getElementById("sizaid");

        htmlstr += "<input type=\"hidden\" id=\"sizaiInser_" + i + "\" name=\"sizaiInser\" value=\"" + insertData + "\">"

    }
    parent_object.innerHTML = htmlstr;

    frmDa.loopCnt.value = loop_cnt;


    // ソート重複排除
    arrShohinObj    = convertUniqueList(arrShohinObj);
    var arrShohin   = new Array();
    var arrHinmei   = new Array();
    var arrNisugata = new Array();
    var arrNaiyo       = new Array();		// 内容【KPX@1602367 】add
    var arrNounyusaki  = new Array();		// 納入先【KPX@1602367 】add
    for(var i = 0;i < arrShohinObj.length; i++){
        var splShohinObj = arrShohinObj[i].split(ConDelimiter);
        arrShohin.push(splShohinObj[0]);
        arrHinmei.push(splShohinObj[1]);
        arrNisugata.push(splShohinObj[2]);
        arrNaiyo.push(splShohinObj[3]);			// 内容
        arrNounyusaki.push(splShohinObj[4]);	// 納入先
    }
    arrShohin      = convertUniqueList(arrShohin);
    arrOldShizaiCd = convertUniqueSortList(arrOldShizaiCd);
    arrNewShizaiCd = convertUniqueSortList(arrNewShizaiCd);

    // cho資材手配依頼書出力画面に値を設定
    frmDa.hdnTaisyosizai_disp.value = cd_taisyosizai; // 対象資材
    frmDa.hdnHattyusaki_disp.value  = cd_hattyusaki; // 発注先
    frmDa.hdnSyohin_disp.value      = createDispString(arrShohin);	// 商品
    frmDa.hdnHinmei_disp.value      = createDispString(arrHinmei);	// 品名
    frmDa.hdnNisugata_disp.value    = createDispString(arrNisugata);	// 荷姿
    frmDa.hdnOldShizai_disp.value   = createDispString(arrOldShizaiCd);	// 旧資材コード
    frmDa.hdnNewShizai_disp.value   = createDispString(arrNewShizaiCd);	// 新資材コード

    frmDa.hdnNaiyo_disp.value       = createDispString(arrNaiyo);			// 内容
    frmDa.hdnNounyusaki_disp.value  = createDispString(arrNounyusaki);		// 納入先

    frmDa.hdnCdShain.value          = hdn_cd_shain;
    frmDa.hdnNen.value              = hdn_nen;
    frmDa.hdnNoOi.value             = hdn_no_oi;
    frmDa.hdnSeqShizai.value        = hdn_seq_shizai;
    frmDa.hdnNoEda.value            = hdn_no_eda;
    frmDa.hdnCdShohin.value         = hdn_shohin_cd;

    // 終了処理
    funEnd();
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

    parent.close();

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
// Excel出力処理
//作成者：BRC Koizumi
// 作成日：2016/10/04
// 概要  ：Excel出力を実行する
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
// 検索条件変更
//作成者：BRC Koizumi
// 作成日：2016/10/13
// 引数  ：なし
// 概要  ：Excelボタン非活性
//========================================================================================
function funChangeSearch() {

	// ボタン非活性
	document.getElementById("btnOutput").disabled = true;

	return;
}
//START Makoto Takada 2017.03.15
//========================================================================================
// 作成日：2016/10/05
// 引数  ：なし
// 概要  ：製造工場コード設定
//========================================================================================
function funChangeKojo() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照

	// 納入先（製造工場）コードを設定
//	frm.seizoKojoCd.value = frm.txtSeizoukojo.value
	// Excelボタン非活性
	funChangeSearch();

	return;
}
//END Makoto Takada  2017.03.15
