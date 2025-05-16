var textValue;
//========================================================================================
// 初期表示処理
// 作成者：H.Shima
// 作成日：2014/09/4
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

    var frm = document.frm00; // ﾌｫｰﾑへの参照

    //画面設定
    funInitScreen(ConRuiziDataId);

    // 画面情報を取得・設定
    if (funGetInfo(1) == false) {
        return false;
    }

    document.getElementById("lblTitle").innerHTML = "類似データ呼出画面";
    // 手配済みにチェック
    frm.chkTehaizumi.checked = true;

    // 手配区分チェックボックスを非活性
    frm.chkTehaizumi.disabled = true;
    frm.chkMitehai.disabled = true;
    frm.chkMinyuryoku.disabled = true;
    frm.chkEdit.disabled = true;

    // ボタン非活性
    frm.btnUpLoad.disabled = true;
    frm.btnOutput.disabled = true;
    frm.btnCompletion.disabled = true;

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
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3200","FGEN3300","FGEN3310","SA290");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3200I, xmlFGEN3300I, xmlFGEN3310I,xmlSA290I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3200O, xmlFGEN3300O, xmlFGEN3310O,xmlSA290O);

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

    // 発注者
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
            	case 4:    //SA290
    				funXmlWrite(reqAry[i], "id_user", "", 0);
    				funXmlWrite(reqAry[i], "id_gamen", ConGmnIdRuiziData, 0);
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
        } // 選択完了
        else if (XmlId.toString() == "RGEN3350"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0)
                    break;
                case 1:    //FGEN3350
                    // XMLより試作コード取得
                    var obj_cd_shain   = document.getElementById("cd_shain_" + funGetCurrentRow());
                    var cd_shain       = obj_cd_shain.value;
                    var obj_nen        = document.getElementById("nen_" + funGetCurrentRow());
                    var nen            = obj_nen.value;
                    var obj_no_oi      = document.getElementById("no_oi_" + funGetCurrentRow());
                    var no_oi          = obj_no_oi.value;
                    var obj_seq_shizai = document.getElementById("seq_shizai_" + funGetCurrentRow());
                    var seq_shizai     = obj_seq_shizai.value;
                    var obj_no_eda     = document.getElementById("no_eda_" + funGetCurrentRow());
                    var no_eda         = obj_no_eda.value;

                    funXmlWrite(reqAry[i], "cd_shain", cd_shain, 0);
                    funXmlWrite(reqAry[i], "nen", nen, 0);
                    funXmlWrite(reqAry[i], "no_oi", no_oi, 0);
                    funXmlWrite(reqAry[i], "seq_shizai", seq_shizai, 0);
                    funXmlWrite(reqAry[i], "no_eda", no_eda, 0);

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
                // 未支払
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

        //レスポンスデータ取得-------------------------------------------------------------------------------
        var row_no        = funXmlRead_3(xmlResAry[2], tableNm, "row_no", 0, i);									// 行No
       	var nm_tanto      = funXmlRead_3(xmlResAry[2], tableNm, "nm_tanto", 0, i);									// 担当者
        var naiyo         = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "naiyo", 0, i));							// 内容
        var cd_shohin     = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "cd_shohin", 0, i));						// 製品（商品）コード
        var nm_shohin     = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_shohin", 0, i));						// 製品（商品）名
        var nisugata      = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nisugata", 0, i));						// 荷姿／入数（漢字）
        var target_shizai = funXmlRead_3(xmlResAry[2], tableNm, "nm_taisyo_shizai", 0, i);							// 対象資材（リテラル名）
        var nm_hattyusaki = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_hattyusaki", 0, i));					// 発注先
        var cd_hattyusaki = funXmlRead_3(xmlResAry[2], tableNm, "cd_hattyusaki", 0, i);								// 発注先コード
        var nm_nounyusaki = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nounyusaki", 0, i));					// 納入先（製造工場）
        var cd_shizai_old = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "cd_shizai_old", 0, i));					// 旧資材コード
        var cd_shizai     = funXmlRead_3(xmlResAry[2], tableNm, "cd_shizai", 0, i);									// 資材コード
        var nm_shizai     = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_shizai", 0, i));						// 資材名
        var sekkei1       = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "sekkei1", 0, i));						// 設計①
        var sekkei2       = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "sekkei2", 0, i));						// 設計②
        var sekkei3       = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "sekkei3", 0, i));						// 設計③
        var zaishitsu     = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "zaishitsu", 0, i));						// 材質
        var biko_tehai    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "biko_tehai", 0, i));					// 備考
        var printcolor    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "printcolor", 0, i));					// 印刷色
        var no_color      = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "no_color", 0, i));						// 色番号
        var henkounaiyoushosai   = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "henkounaiyoushosai", 0, i));		// 変更内容詳細
        var nouki         = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nouki", 0, i));							// 納期
        var suryo         = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "suryo", 0, i));							// 数量
        var nounyu_day    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nounyu_day", 0, i));					// 納入日
        var han_pay       = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "han_pay", 0, i));						// 版代
        var dt_han_payday   = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "dt_han_payday", 0, i));				// 版代支払日
        // nullの場合'1900/01/01'に更新されるので'1900/01/01'の場合""に更新
        if (dt_han_payday == '1900/01/01') {
        	dt_han_payday = "";
        }
        var han_upload    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "file_path_aoyaki", 0, i));				// 青焼アップロード
        var nm_file       = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_file_aoyaki", 0, i));				// 保存ファイル名

        var cd_shain      = funXmlRead_3(xmlResAry[2], tableNm, "cd_shain", 0, i);									// 社員コード
        var nen           = funXmlRead_3(xmlResAry[2], tableNm, "nen", 0, i);										// 年
        var no_oi         = funXmlRead_3(xmlResAry[2], tableNm, "no_oi", 0, i);										// 追番
        var seq_shizai    = funXmlRead_3(xmlResAry[2], tableNm, "seq_shizai", 0, i);								// 試作SEQ
        var no_eda        = funXmlRead_3(xmlResAry[2], tableNm, "no_eda", 0, i);									// 枝番
        var flg_status    = funXmlRead_3(xmlResAry[2], tableNm, "flg_status", 0, i);								// 手配ステータス
        var toroku_disp   = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "dt_koshin_disp", 0, i));				// 登録日

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
            output_html += "<tr class=\"disprow\" bgcolor=\"" + deactiveSelectedColor + "\" onDblClick=\"funCompletion();\">";
        } else if("2" === flg_status) {
            // 未手配
            output_html += "<tr class=\"disprow\" bgcolor=\"" + notArrangeColor + "\" onDblClick=\"funCompletion();\">";
        } else {
            // 未入力
            output_html += "<tr class=\"disprow\" bgcolor=\"" + notInputColor + "\" onDblClick=\"funCompletion();\">";
        }

        //行No
        output_html += "    <td class=\"column\" width=\"28\"  align=\"right\">";
        output_html += "        <input type=\"text\" id=\"no_row_" + i + "\" name=\"no_row_" + i + "\" style=\"background-color:transparent;width:26px;border-width:0px;text-align:left\" readOnly value=\"" + row_no + "\" >";
        output_html += "        <input type=\"hidden\" id=\"cd_shain_" + i + "\" name=\"cd_shain_" + i + "\" readOnly value=\"" + cd_shain + "\" >";
        output_html += "        <input type=\"hidden\" id=\"nen_" + i + "\" name=\"nen_" + i + "\" readOnly value=\"" + nen + "\" >";
        output_html += "        <input type=\"hidden\" id=\"no_oi_" + i + "\" name=\"no_oi_" + i + "\" readOnly value=\"" + no_oi + "\" >";
        output_html += "        <input type=\"hidden\" id=\"seq_shizai_" + i + "\" name=\"seq_shizai_" + i + "\" readOnly value=\"" + seq_shizai + "\" >";
        output_html += "        <input type=\"hidden\" id=\"no_eda_" + i + "\" name=\"no_eda_" + i + "\" readOnly value=\"" + no_eda + "\" >";
        output_html += "    </td>";

        //選択ボタン
        output_html += "    <td class=\"column\" width=\"32\" align=\"center\">";
        output_html += "        <input type=\"radio\" id=\"chk\" name=\"chk\" onclick=\"clickItiran(" + i + ");\" style=\"width:28px;\" value=\"" + i + "\" tabindex=\"-1\">";
//        output_html += "        <input type=\"radio\" id=\"chk\" name=\"chk\" onclick=\"clickItiran(" + i + ");\" onDblClick=\"funCompletion();\" style=\"width:28px;\" value=\"" + i + "\" tabindex=\"-1\">";
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
        output_html += "        <input type=\"text\" id=\"cd_shohin_" + i + "\" name=\"cd_shohin_" + i + "\" style=\"background-color:transparent;width:48;border-width:0px;text-align:left\" readOnly value=\"" + fillsZero(cd_shohin,6) + "\" >";
        output_html += "    </td>";

        //製品（商品）名
        output_html += "    <td class=\"column\" width=\"146\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nm_shohin_" + i + "\" name=\"nm_shohin_" + i + "\" style=\"background-color:transparent;width:144px;border-width:0px;text-align:left\" readOnly value=\"" + nm_shohin + "\" >";
        output_html += "    </td>";

        //荷姿
        output_html += "    <td class=\"column\" width=\"147\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nisugata_" + i + "\" name=\"nisugata_" + i + "\" style=\"background-color:transparent;width:145px;border-width:0px;text-align:left\" readOnly value=\"" + nisugata + "\" >";
        output_html += "    </td>";

        //対象資材
        output_html += "    <td class=\"column\" width=\"117\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"target_shizaia_" + i + "\" name=\"target_shizai_" + i + "\" style=\"background-color:transparent;width:115px;border-width:0px;text-align:left\" readOnly value=\"" + target_shizai + "\" >";
        output_html += "    </td>";

        //発注先
        output_html += "    <td class=\"column\" width=\"146\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nm_hattyusaki_" + i + "\" name=\"nm_hattyusaki_" + i + "\" style=\"background-color:transparent;width:144px;border-width:0px;text-align:left\" readOnly value=\"" + nm_hattyusaki + "\" >";
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
        output_html += "    <td class=\"column\" width=\"148\" align=\"right\" >";
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
// 引数  ：なし
// 概要  ：条件より検索を行い、結果を一覧に表示
//========================================================================================
function funSearch(){
    var frm = document.frm00; // ﾌｫｰﾑへの参照

    // 手配区分選択チェック（類似データは、手配済みのみ）
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
        return false;
    }

    // 検索結果行数の設定
    var loop_cnt = funXmlRead(xmlResAry[2], "loop_cnt", 0);
    frm.hidListRow.value = loop_cnt;

    //ﾃﾞｰﾀ取得成功時
    if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
        //表示中に検索系アクションを操作不可
        document.getElementById("btnOutput").disabled = true;
        document.getElementById("btnSearch").disabled = true;
        document.getElementById("btnClear").disabled = true;
        document.getElementById("btnCompletion").disabled = true;
        document.getElementById("btnEnd").disabled = true;

        var output_html = "";
        output_html = output_html + "<table cellpadding=\"0\" id=\"tblList\" cellspacing=\"0\" border=\"1\">";

        setTimeout(function(){ DataSet(xmlResAry, output_html ,0 ,loop_cnt); }, 0);
    }

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    return true;
}

//========================================================================================
// コンボボックス作成処理
// 作成者：H.Shima
// 作成日：2014/09/19
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②xmlData  ：設定XML
//       ：③mode     ：モード
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

        case 2:    // 対象資材
            atbName = "nm_literal";
            atbCd   = "cd_literal";
            break;

        case 3:    // 発注者
            atbName = "nm_user";
            atbCd   = "id_user";
            break;
    }

    for (i = 0; i < reccnt; i++) {
        if (funXmlRead(xmlData, atbCd, i) != "" && funXmlRead(xmlData, atbName, i) != "") {
            objNewOption = document.createElement("option");
            obj.options.add(objNewOption);
            objNewOption.innerText = funXmlRead(xmlData, atbName, i);
            objNewOption.value     = funXmlRead(xmlData, atbCd, i);
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
    funChangeSelectRowColorStockPreviousColor();
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
// 一覧クリア処理
// 作成者：H.Shima
// 作成日：2014/9/12
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
// 選択完了処理
// 作成者：H.Shima
// 作成日：2014/9/12
// 引数  ：なし
// 概要  ：一覧から選択された情報を依頼画面に送る
//========================================================================================
function funCompletion(){

    var frm = document.frm00;                    // ﾌｫｰﾑへの参照
    var frmDa = window.dialogArguments.frm00;    // 親ﾌｫｰﾑへの参照（明細フレーム）
    var docDa = window.dialogArguments.document; // 親documentへの参照（明細フレーム）
    var XmlId = "RGEN3350";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3350");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3350I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3350O);
    var tableNm = "table";

    //選択されていない場合
    if(funGetCurrentRow().toString() == ""){
        funErrorMsgBox(E000002);
        return false;
    }
    //選択されている場合
    else {

        // 引数をXMLﾌｧｲﾙに設定
        if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
            funClearRunMessage();
            return false;
        }

        // ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
        if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3350, xmlReqAry, xmlResAry, 1) == false) {
            return false;
        }

        /* 資材手配依頼書出力（親フォーム）に値を設定 */
        // 新版、改版 共通項目
        frmDa.txtDesign1.value         = funXmlRead_3(xmlResAry[2], tableNm, "sekkei1", 0, 0);				// 設計①
        frmDa.txtDesign2.value         = funXmlRead_3(xmlResAry[2], tableNm, "sekkei2", 0, 0);				// 設計②
        frmDa.txtDesign3.value         = funXmlRead_3(xmlResAry[2], tableNm, "sekkei3", 0, 0);				// 設計③
        frmDa.txtZaishitsu.value       = funXmlRead_3(xmlResAry[2], tableNm, "zaishitsu", 0, 0);			// 材質
        frmDa.txtBiko.value            = funXmlRead_3(xmlResAry[2], tableNm, "biko_tehai", 0, 0);			// 備考
        frmDa.txtPrintColor.value      = funXmlRead_3(xmlResAry[2], tableNm, "printcolor", 0, 0);			// 印刷色
        frmDa.txtColorNo.value         = funXmlRead_3(xmlResAry[2], tableNm, "no_color", 0, 0);				// 色番号
        frmDa.txtChangesDetail.value   = funXmlRead_3(xmlResAry[2], tableNm, "henkounaiyoushosai", 0, 0);	// 変更内容詳細
        frmDa.txtDeliveryTime.value    = funXmlRead_3(xmlResAry[2], tableNm, "nouki", 0, 0);				// 納期
        frmDa.txtQuantity.value        = funXmlRead_3(xmlResAry[2], tableNm, "suryo", 0, 0);				// 数量

        // 改版のみ
        if (frmDa.revision_rev.checked) {
            frmDa.txtOldShizaiZaiko.value  = funXmlRead_3(xmlResAry[2], tableNm, "old_sizaizaiko", 0, 0);	// 旧資材在庫
            frmDa.txtRakuhan.value         = funXmlRead_3(xmlResAry[2], tableNm, "rakuhan", 0, 0);			// 落版
        }

        // 類似選択完了
        frmDa.hdnRuiziSelect.value    = "true";
        // 終了処理
        funEnd();
    }
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
// 選択行操作
// 作成者：H.Shima
// 作成日：2014/10/14
// 引数  ：なし
// 戻り値：なし
// 概要  ：選択行の背景色を保持し、選択色に変更する。
//========================================================================================
function funChangeSelectRowColorStockPreviousColor() {
	var deactiveColor = deactiveSelectedColor;	// 選択行の非アクティブ色
    var oSrc = event.srcElement, oTBL, oTR, oTD;
    var BeforeRow;

    if ( (oSrc.tagName != "TABLE") && (oSrc.tagName != "DIV") && (oSrc.tagName != "TR") ) {
        for ( oTBL = oSrc.parentElement; oTBL.tagName != "TABLE"; oTBL = oTBL.parentElement ) {}
        for ( oTR = oSrc.parentElement; oTR.tagName != "TR"; oTR = oTR.parentElement ) {}

        if(CurrentRow !== oTR.rowIndex){
            if(CurrentRow !== ""){
                BeforeRow = (CurrentRow == "" ? 0 : CurrentRow / 1);
                if ((BeforeRow != oTR.rowIndex) && (BeforeRow + 1 <= oTBL.rows.length)){
                    //背景色を戻す
                    oTBL.rows(BeforeRow).style.backgroundColor = deactiveColor;
                }
            }
            // 背景色の保持
            deactiveColor = oTR.style.backgroundColor;
        }

        //背景色を変更
        oTBL.rows(oTR.rowIndex).style.backgroundColor = activeSelectedColor;

        //ｶﾚﾝﾄ行の退避
        CurrentRow = oTR.rowIndex;
    }

    return true;
}

//========================================================================================
// ゼロ埋め処理
// 作成者：H.Shima
// 作成日：2014/12/12
//========================================================================================
function fillsZero(obj, keta){
    var ret = obj;

    while(ret.length < keta){
        ret = "0" + ret;
    }
    return ret;
}

//========================================================================================
// 納入先（製造工場）入力時
// 作成者：BRC Koizumi
// 作成日：2016/10/05
// 引数  ：なし
// 概要  ：製造工場コード設定
//========================================================================================
function funChangeKojo() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照

	// 納入先（製造工場）コードを設定
	//frm.seizoKojoCd.value = funGetXmldata(xmlSA290O, "nm_busho", frm.txtSeizoukojo.value, "cd_busho");
	// Excelボタン非活性
	funChangeSearch();

	return;
}

//========================================================================================
// XMLデータより検索行のコード←→名前 変換
// 作成者：BRC Koizumi
// 作成日：2016/10/04
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
// Excel出力処理
// 作成者：BRC Koizumi
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
// 作成者：BRC Koizumi
// 作成日：2016/10/13
// 引数  ：なし
// 概要  ：Excelボタン非活性
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