
// ADD start 20120620 hisahori
// グローバル変数
var stKojo;  // 工場ステータス
var tblSampleRowCount;  // 試算日クリアサンプル選択テーブルrow数
// ADD end 20120620 hisahori
//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.29 start
var stEigyo;  // 営業ステータス
//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.29 end

//========================================================================================
// 初期表示処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/26
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

	var cd_shain = window.dialogArguments[0];
	var nen = window.dialogArguments[1];
	var no_oi = window.dialogArguments[2];
	var no_eda = window.dialogArguments[3];
// ADD start 20120615 hisahori
	var seqshisaku = window.dialogArguments[4];
	var sampleno = window.dialogArguments[5];
	var shisanbi = window.dialogArguments[6];
	var shisanchushi = window.dialogArguments[7];
// ADD end 20120615 hisahori
	// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.29 start
	var chkKoumoku = window.dialogArguments[8];
	// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.29 end

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
	frm.hidShisakuNo_shaincd.value = cd_shain;
	frm.hidShisakuNo_nen.value = nen;
	frm.hidShisakuNo_oi.value = no_oi;
	frm.hidShisakuNo_eda.value = no_eda;
// ADD start 20120615 hisahori
	frm.hidSeqShisaku.value = seqshisaku;
	frm.hidSampleNo.value = sampleno;
	frm.hidShisanHi.value = shisanbi;
	frm.hidShisanChushi.value = shisanchushi;
// ADD end 20120615 hisahori
	// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.29 start
	frm.hidChkKoumoku.value = chkKoumoku;
	// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.29 end

    //画面設定
    funInitScreen(ConStatusClearId);

    //画面情報を取得・設定
    if (funGetInfo(1) == false) {
        return false;
    }

//ADD start 20120614 hisahori
  //20160607  KPX@1502111_No9 非表示に変更
	//試算日クリア選択表を表示
	funDtShisanClearDisplay("divDtShisanClearSelect","");
//ADD end 20120614 hisahori

    return true;

}

//========================================================================================
// 【QP@00342】画面情報取得処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/24
// 引数  ：①mode  ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：ユーザのセッション情報、コンボボックスの情報を取得する
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN2020";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2010","FGEN2020");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2010I,xmlFGEN2020I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2010O,xmlFGEN2020O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //ｾｯｼｮﾝ情報、ステータス履歴の情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2020, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //設定値取得
    var nm_clear = funXmlRead_3(xmlResAry[2], "kihon", "nm_clear", 0, 0);
    var st_kenkyu = funXmlRead_3(xmlResAry[3], "kihon", "st_kenkyu", 0, 0);

    //現在ステータス設定
    var divStatusKenkyu_now = document.getElementById("divStatusKenkyu_now");
    var divStatusSeikan_now = document.getElementById("divStatusSeikan_now");
    var divStatusGentyo_now = document.getElementById("divStatusGentyo_now");
    var divStatusKojo_now = document.getElementById("divStatusKojo_now");
    var divStatusEigyo_now = document.getElementById("divStatusEigyo_now");
    divStatusKenkyu_now.innerHTML = funStatusSetting("1",funXmlRead_3(xmlResAry[3], "kihon", "st_kenkyu", 0, 0));
    divStatusSeikan_now.innerHTML = funStatusSetting("2",funXmlRead_3(xmlResAry[3], "kihon", "st_seisan", 0, 0));
    divStatusGentyo_now.innerHTML = funStatusSetting("3",funXmlRead_3(xmlResAry[3], "kihon", "st_gensizai", 0, 0));
    divStatusKojo_now.innerHTML = funStatusSetting("4",funXmlRead_3(xmlResAry[3], "kihon", "st_kojo", 0, 0));
    divStatusEigyo_now.innerHTML = funStatusSetting("5",funXmlRead_3(xmlResAry[3], "kihon", "st_eigyo", 0, 0));

    //ステータスクリアテーブル生成
    //現在ステータス取得
    var st_kenkyu = funXmlRead_3(xmlResAry[3], "kihon", "st_kenkyu", 0, 0);
    var st_seisan = funXmlRead_3(xmlResAry[3], "kihon", "st_seisan", 0, 0);
    var st_gensizai = funXmlRead_3(xmlResAry[3], "kihon", "st_gensizai", 0, 0);
    var st_kojo = funXmlRead_3(xmlResAry[3], "kihon", "st_kojo", 0, 0);
// ADD start 20120620 hisahori
	stKojo = st_kojo;
// ADD end 20120620 hisahori
    var st_eigyo = funXmlRead_3(xmlResAry[3], "kihon", "st_eigyo", 0, 0);
 // ADD 2015/03/03 TT.Kitazawa【QP@40812】No.29 start
    stEigyo = st_eigyo;
 // ADD 2015/03/03 TT.Kitazawa【QP@40812】No.29 end

    //生成開始
    var divStatusTable = document.getElementById("divStatusTable");
    var outputHtml = "";
    var i = 0;
    var roop_cnt = funXmlRead_3(xmlResAry[2], "kihon", "roop_cnt", 0, 0);
    var flg_now_down = false;
    var bgColor="";
    var disabled = "";
    var checked = "";

// MOD start 20120704 hisahori
	var countRadioNo = 0;
// MOD end 20120704 hisahori

    //生成ループ
    for(i = roop_cnt-1; i >= 0; i--){
        var cd_clear = funXmlRead_3(xmlResAry[2], "kihon", "cd_clear", 0, i);
        var nm_clear = funXmlRead_3(xmlResAry[2], "kihon", "nm_clear", 0, i);
        var r_st_kenkyu = funXmlRead_3(xmlResAry[2], "kihon", "st_kenkyu", 0, i);
        var r_st_seisan = funXmlRead_3(xmlResAry[2], "kihon", "st_seisan", 0, i);
        var r_st_gensizai = funXmlRead_3(xmlResAry[2], "kihon", "st_gensizai", 0, i);
        var r_st_kojo = funXmlRead_3(xmlResAry[2], "kihon", "st_kojo", 0, i);
        var r_st_eigyo = funXmlRead_3(xmlResAry[2], "kihon", "st_eigyo", 0, i);

        //属性の初期値設定
        if( flg_now_down ){
            bgColor="#FFFFFF";
	    	disabled = "";
	    	checked = "";
        }
        else{
            bgColor="#A9A9A9";
	    	disabled = "disabled";
	    	checked = "";
        }

        //現在ステータスが「生3未満」「営4」の場合
        if( st_seisan < 3 && st_eigyo == 4 ){
        	if(cd_clear == 2){
        		flg_now_down = true;
        	}
        }

        //ステータス判定（営業の採用有無完了）
	    else if( st_eigyo == 4 && cd_clear == 11){
	    	bgColor="#FFFF88";
	    	disabled = "disabled";
	    	checked = "";
	    	flg_now_down = true;
	    }
	    //ステータス判定（営業の確認完了）
	    else if( st_eigyo == 3  && cd_clear == 10){
	    	bgColor="#FFFF88";
	    	disabled = "disabled";
	    	checked = "";
	    	flg_now_down = true;
	    }
	    //ステータス判定（生産管理部の確認完了）
	    else if( st_eigyo == 2  && st_seisan == 3  && cd_clear == 9){
	    	bgColor="#FFFF88";
	    	disabled = "disabled";
	    	checked = "";
	    	flg_now_down = true;
	    }
	    //ステータス判定（生産管理部の工場依頼（原資材調達部・工場　承認済み））
	    else if( st_seisan == 2 && st_kojo == 2 && st_gensizai == 2 && cd_clear == 8){
	    	bgColor="#FFFF88";
	    	disabled = "disabled";
	    	checked = "";
	    	flg_now_down = true;
	    }
	    //ステータス判定（生産管理部の工場依頼（工場　承認済み））
	    else if( st_seisan == 2 && st_gensizai == 1 && st_kojo == 2  && cd_clear == 7){
	    	bgColor="#FFFF88";
	    	disabled = "disabled";
	    	checked = "";
	    	flg_now_down = true;
	    }
	    //ステータス判定（生産管理部の工場依頼（原資材調達部　承認済み））
	    else if( st_seisan == 2 && st_gensizai == 2 && st_kojo == 1 && cd_clear == 6){
	    	bgColor="#FFFF88";
	    	disabled = "disabled";
	    	checked = "";
	    	flg_now_down = true;
	    }
	    //ステータス判定（生産管理部の工場依頼）
	    else if( st_seisan == 2 && st_gensizai == 1 && st_kojo == 1  && cd_clear == 5){
	    	bgColor="#FFFF88";
	    	disabled = "disabled";
	    	checked = "";
	    	flg_now_down = true;
	    }
	    //ステータス判定（営業の条件入力（原資材調達部　承認済み））
	    else if( st_seisan == 1 && st_gensizai == 2 && st_eigyo == 2  && cd_clear == 4){
	    	bgColor="#FFFF88";
	    	disabled = "disabled";
	    	checked = "";
	    	flg_now_down = true;
	    }
	    //ステータス判定（営業の条件入力）
	    else if( st_seisan == 1 && st_gensizai == 1 && st_eigyo == 2  && cd_clear == 3){
	    	bgColor="#FFFF88";
	    	disabled = "disabled";
	    	checked = "";
	    	flg_now_down = true;
	    }
	    //ステータス判定（研究所の試算依頼（原資材調達部　承認済み））
	    else if( st_gensizai == 2 && st_eigyo == 1  && cd_clear == 2){
	    	bgColor="#FFFF88";
	    	disabled = "disabled";
	    	checked = "";
	    	flg_now_down = true;
	    }
	    //ステータス判定（研究所の試算依頼）
	    else if( st_gensizai == 1 && st_eigyo == 1  && cd_clear == 1){
	    	bgColor="#FFFF88";
	    	disabled = "disabled";
	    	checked = "";
	    	flg_now_down = true;
	    }

	    //ステータス判定（原資材調達部）
	    if( st_seisan <= 2 && st_gensizai == 1 && cd_clear == 2){
	        bgColor="#A9A9A9";
	    	disabled = "disabled";
	    	checked = "";
	    }
	    if( st_seisan <= 2 && st_gensizai == 1 && cd_clear == 4){
	        bgColor="#A9A9A9";
	    	disabled = "disabled";
	    	checked = "";
	    }
	    if( st_seisan <= 2 && st_gensizai == 1 && cd_clear == 6){
	        bgColor="#A9A9A9";
	    	disabled = "disabled";
	    	checked = "";
	    }

	    //テーブル生成
        var cleateHtml = "";
	    cleateHtml = cleateHtml + "<tr class=\"disprow\">";
	    if( cd_clear == 11 ){
	        cleateHtml = cleateHtml + "<td class=\"column\" width=\"32\"		bgcolor=\"" + bgColor + "\"	align=\"center\">&nbsp;</td>";
	    }
	    else{
// MOD start 20120704 hisahori
//	        cleateHtml = cleateHtml + "<td class=\"column\" width=\"32\"		bgcolor=\"" + bgColor + "\"	align=\"center\"><input type=\"radio\" name=\"chk\" value=\"" + cd_clear + "\" " + disabled + " " + checked + "></td>";
//			countRadioNo++;
	        cleateHtml = cleateHtml + "<td class=\"column\" width=\"32\" bgcolor=\"" + bgColor + "\" align=\"center\"><input type=\"radio\" name=\"chk\" value=\"" + cd_clear + "\" " + disabled + " " + checked + " onClick=\"radioStatusChange()\"></td>";
// MOD end 20120704 hisahori
	    }
		cleateHtml = cleateHtml + "<td class=\"column\" width=\"351\" 	bgcolor=\"" + bgColor + "\"	align=\"left\"  >" + nm_clear + "</td>";
		cleateHtml = cleateHtml + "<td class=\"column\" width=\"50\"  	bgcolor=\"" + bgColor + "\"	align=\"center\"  >" + funStatusSetting("1",r_st_kenkyu) + "</td>";
		cleateHtml = cleateHtml + "<td class=\"column\" width=\"50\"  	bgcolor=\"" + bgColor + "\"	align=\"center\"  >" + funStatusSetting("2",r_st_seisan) + "</td>";
		cleateHtml = cleateHtml + "<td class=\"column\" width=\"50\"  	bgcolor=\"" + bgColor + "\"	align=\"center\"  >" + funStatusSetting("3",r_st_gensizai) + "</td>";
		cleateHtml = cleateHtml + "<td class=\"column\" width=\"50\"  	bgcolor=\"" + bgColor + "\"	align=\"center\"  >" + funStatusSetting("4",r_st_kojo) + "</td>";
		cleateHtml = cleateHtml + "<td class=\"column\" width=\"50\"  	bgcolor=\"" + bgColor + "\"	align=\"center\"  >" + funStatusSetting("5",r_st_eigyo) + "</td>";
		cleateHtml = cleateHtml + "</tr>";
		outputHtml = cleateHtml + outputHtml;
    }

    outputHtml = "<table class=\"detail\" id=\"tblList\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" style=\"width:630;\">" + outputHtml;
    outputHtml = outputHtml + "</table>";
    divStatusTable.innerHTML = outputHtml;

    return true;
}

//========================================================================================
// 【QP@00342】XMLファイルに書き込み
// 作成者：Y.Nishigawa
// 作成日：2011/01/24
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
        if (XmlId.toString() == "RGEN2020") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2010

                    break;
                case 2:    //FGEN2020
                	var hidShisakuNo_shaincd = frm.hidShisakuNo_shaincd.value;
                	var hidShisakuNo_nen = frm.hidShisakuNo_nen.value;
                	var hidShisakuNo_oi = frm.hidShisakuNo_oi.value;
                	var hidShisakuNo_eda = frm.hidShisakuNo_eda.value;

                    funXmlWrite(reqAry[i], "cd_shain", hidShisakuNo_shaincd, 0);
                    funXmlWrite(reqAry[i], "nen", hidShisakuNo_nen, 0);
                    funXmlWrite(reqAry[i], "no_oi", hidShisakuNo_oi, 0);
                    funXmlWrite(reqAry[i], "no_eda", hidShisakuNo_eda, 0);
                    break;
            }
        }
        //クリア実行
        else if (XmlId.toString() == "RGEN2030") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2030
                	var hidShisakuNo_shaincd = frm.hidShisakuNo_shaincd.value;
                	var hidShisakuNo_nen = frm.hidShisakuNo_nen.value;
                	var hidShisakuNo_oi = frm.hidShisakuNo_oi.value;
                	var hidShisakuNo_eda = frm.hidShisakuNo_eda.value;
					//クリアコード取得
					var cd_clear = "";
					for(var j = 0; j < frm.chk.length; j++){
				        if(frm.chk[j].checked) {
				            cd_clear = frm.chk[j].value;
				            break;
				        }
					}
					// ADD start 20120622 hisahori
					if (cd_clear == ""){
        				funErrorMsgBox("【ステータスクリア実行】　：　 " + E000025);
        				return false;
					}
					// ADD end 20120622 hisahori

                    funXmlWrite(reqAry[i], "cd_shain", hidShisakuNo_shaincd, 0);
                    funXmlWrite(reqAry[i], "nen", hidShisakuNo_nen, 0);
                    funXmlWrite(reqAry[i], "no_oi", hidShisakuNo_oi, 0);
                    funXmlWrite(reqAry[i], "no_eda", hidShisakuNo_eda, 0);
                    funXmlWrite(reqAry[i], "cd_clear", cd_clear, 0);

                    //ADD start 20120618 hisahori
                    //選択された試算日をクリアするサンプルNO（seq_shisaku）
                    var cnttblrow;
                    if (tblSampleRowCount == 0){
	                    cnttblrow = 0; // 試算中止サンプルしかない場合、rowカウント0
                    } else {
	                    cnttblrow = tblClearShisanHi.rows.length;
                    }
                    var checkNo = "";
					for(var k = 0; k < cnttblrow; k++){
						if (document.getElementById("chkSampleGyo_" + k).checked){
							if (checkNo == ""){
								checkNo = checkNo + document.getElementById("hdnTblSeqShisaku_" + k).value;
							} else {
								checkNo = checkNo + "chk" + document.getElementById("hdnTblSeqShisaku_" + k).value; //xmlの要素内で「,」が使えない
							}
						}
					}
                    funXmlWrite(reqAry[i], "no_clearcheck", checkNo, 0);
                    //ADD end 20120618 hisahori

                    break;
            }
        }
    }

    return true;

}

//========================================================================================
// 【QP@00342】ステータスクリア実行
// 作成者：Y.Nishigawa
// 作成日：2011/01/26
// 引数  ：なし
// 戻り値：なし
// 概要  ：ステータスクリア実行
//========================================================================================
function funClear(){

	var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN2030";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2030");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2030I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2030O);
    var mode = 1;

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage(); //処理中メッセージを終了する
        return false;
    }

    //ｾｯｼｮﾝ情報、ステータス履歴の情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2030, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //完了ﾒｯｾｰｼﾞの表示
    dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {
        //正常
        funInfoMsgBox(dspMsg);
    }

    //戻り値の設定
	window.returnValue = "Clear";

	//画面を閉じる
    close(self);
}

//========================================================================================
// 【QP@00342】画面終了
// 作成者：Y.Nishigawa
// 作成日：2011/01/24
// 引数  ：なし
// 戻り値：なし
// 概要  ：画面を閉じる。
//========================================================================================
function funClose() {

	//戻り値の設定
	window.returnValue = "false";

    //画面を閉じる
    close(self);
}

//20160607  KPX@1502111_No9 以下表示されない！
// 試算日クリア処理削除
//========================================================================================
// 試算日クリアサンプル選択表
// 作成者：T.Hisahori
// 作成日：2012/06/14
// 引数  ：ObjectId：設定オブジェクトID
// 戻り値：なし
// 概要  ：試算日クリア選択表のHTML文を生成、出力する。
//========================================================================================
function funDtShisanClearDisplay(ObjectId) {
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var divDtShisanSelect = document.getElementById(ObjectId);
    var OutputHtml;       //出力HTML
    var chkDisabled;      //チェックボックス入力設定 -- 未使用
    var objColor;         //オブジェクト背景色

// MOD start 20120704
//	var chkEnable = "enable";
//    if (stKojo <= 1){
//    	chkEnable = "disabled";
//    }
	var chkEnable = "disabled";
// MOD end 20120704

    //サンプルNO、試算日配列作成
    var getSeqShisaku = frm.hidSeqShisaku.value;
    var getSampleNo = frm.hidSampleNo.value;
    var getShisanHi = frm.hidShisanHi.value;
    var getShisanChushi = frm.hidShisanChushi.value;
    var arrSeqShisaku = getSeqShisaku.split(",");
    var arrSampleNo = getSampleNo.split(",");
    var arrShisanHi = getShisanHi.split(",");
    var arrShisanChushi = getShisanChushi.split(",");
	// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.29 start
    var getChkKoumoku = frm.hidChkKoumoku.value;
    var arrChkKoumoku = getChkKoumoku.split(",");
	// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.29 end


    chkDisabled = "disabled";		//未使用
    txtClass = "table_text_view";

	var cnt_sampleNo = arrShisanHi.length;
    //中止フラグの数
    var cntChushi = 0;
    for(var i = 0; i < cnt_sampleNo; i++ ){
    	if (arrShisanChushi[i] == 1){
    		cntChushi++;
    	}
    }
    if (cnt_sampleNo == cntChushi){
	    tblSampleRowCount = 0; // 試算中止サンプルしかない場合、rowカウント0
    }

    //HTML出力オブジェクト設定
    OutputHtml = "";

    //テーブル名設定
    OutputHtml += "<input type=\"hidden\" id=\"cnt_sampleNo\" name=\"cnt_sampleNo\" value=\"" + cnt_sampleNo + "\">";

    //出力HTML設定
    OutputHtml += "      <div class=\"scroll_genka\" id=\"divDtShisanClearSelect\" style=\"width:346px;height:85px;overflow-x:hidden;\" rowSelect=\"true\" />";
    OutputHtml += "      <table class=\"detail\" id=\"tblClearShisanHi\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" style=\"width:330px;　>";

	// サンプルが試算中止ではないサンプルのみ表示
	var cntNumbering = 0;
    for(var j = 0; j < cnt_sampleNo; j++ ){
    	if (arrShisanChushi[j] != "1"){
	        OutputHtml += "    <tr　class=\"disprow\">";
	        //選択
	        OutputHtml += "        <td bgcolor=\"#ffffff\" class=\"column\" width=\"32px\" align=\"center\">";
	        OutputHtml += "            <input name=\"chkSampleGyo_" + cntNumbering + "\" type=\"checkbox\" " + chkEnable + " />";
	        OutputHtml += "        </td>";
	        //サンプルNO
	        OutputHtml += "        <td class=\"column\" width=\"151\"  	bgcolor=\"" + "#FFFFFF" + "\"	align=\"left\"  >" + arrSampleNo[j] + "</td>";
	        //試算日
	        OutputHtml += "        <td class=\"column\" width=\"150\"  	bgcolor=\"" + "#FFFFFF" + "\"	align=\"left\"  >" + arrShisanHi[j] + "</td>";
	        //サンプルNOキー（seq_shisaku）
	        OutputHtml += "        <td class=\"hidden\" >";
	        OutputHtml += "            <input type=\"hidden\" value=\"" + arrSeqShisaku[j] + "\" name=\"hdnTblSeqShisaku_" + cntNumbering + "\" id=\"hdnTblSeqShisaku_" + cntNumbering + "\" >";
	        OutputHtml += "        </td>";
	    	// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.29 start
	        // 項目固定チェック
	        OutputHtml += "        <td class=\"hidden\" >";
	        OutputHtml += "            <input type=\"hidden\" value=\"" + arrChkKoumoku[j] + "\" name=\"hdnTblChkKoumoku_" + cntNumbering + "\" id=\"hdnTblChkKoumoku_" + cntNumbering + "\" >";
	        OutputHtml += "        </td>";
	    	// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.29 end
	        OutputHtml += "    </tr>";

	        cntNumbering ++;
	   }
    }
    OutputHtml += "      </table>";

    //HTMLを出力
	divDtShisanSelect.innerHTML = OutputHtml;

    OutputHtml = null;
    return true;
}

//========================================================================================
// ステータス戻し先ラジオボタンチェンジイベント
// 作成者：T.Hisahori
// 作成日：2012/07/04
// 引数  ：
// 戻り値：なし
// 概要  ：工場１になるステータス戻し先が選択された場合、試算日クリアサンプルNOチェックボックスを活性化する
//========================================================================================
function radioStatusChange(){
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var flgChkboxSelect = 0;
// ADD start 20120711
    if (stKojo <= 1){
    } else {
// ADD end 20120711

		// 試算日クリアサンプルNO　チェックボックスの数を取得
	    var cnttblrow;
	    if (tblSampleRowCount == 0){
	        cnttblrow = 0; // 試算中止サンプルしかない場合、rowカウント0
	    } else {
	        cnttblrow = tblClearShisanHi.rows.length;
	    }
	    if (cnttblrow == 0){
	    	return;
	    }

		if (frm.chk[0].checked == true){
			flgChkboxSelect = 1;
		} else if (frm.chk[1].checked == true){
			flgChkboxSelect = 1;
		} else if (frm.chk[2].checked == true){
			flgChkboxSelect = 1;
		} else if (frm.chk[3].checked == true){
			flgChkboxSelect = 1;
		} else if (frm.chk[4].checked == true){
			flgChkboxSelect = 1;
		} else if (frm.chk[5].checked == true){
			flgChkboxSelect = 1;
		} else{
			//営業：確認後、試算日クリアできない
			flgChkboxSelect = 0;
		}
//20160607  KPX@1502111_No9 DEL start  試算日をクリアしない
//		//試算日クリアサンプルNoチェックボックスの活性/非活性を切り替え
//		if (flgChkboxSelect == 1){
//			for(var k = 0; k < cnttblrow; k++){
//				document.getElementById("chkSampleGyo_" + k).disabled = false;
//				document.getElementById("chkSampleGyo_" + k).checked = true;
//				//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.29 start
//				//営業確認完了後ステータスクリアする場合、項目固定チェックONの列：「試算日」のチェック不可
//				if ((stEigyo > 2) && (document.getElementById("hdnTblChkKoumoku_" + k).value == 1)) {
//					document.getElementById("chkSampleGyo_" + k).disabled = true;
//					document.getElementById("chkSampleGyo_" + k).checked = false;
//				}
//				//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.29 end
//			}
//		} else {
//			for(var k = 0; k < cnttblrow; k++){
//				document.getElementById("chkSampleGyo_" + k).disabled = true;
//				document.getElementById("chkSampleGyo_" + k).checked = false;
//			}
//		}
//20160607  KPX@1502111_No9 DEL end
// ADD start 20120711
    }
// ADD end 20120711

}



