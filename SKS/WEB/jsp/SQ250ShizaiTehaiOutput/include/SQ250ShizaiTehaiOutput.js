var selectHattyusaki = "";
// 未チェック色
var checkColor       = "#ffffff";
// 既チェック色
var uncheckColor     = "#99ffff";
//========================================================================================
// ヘッダ初期表示処理
// 作成者：H.Shima
// 作成日：2014/09/4
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad_head() {

    var frm = document.frm00; // ﾌｫｰﾑへの参照

    // 画面情報を取得・設定
    if (funGetUserInfo(1) == false) {
        return false;
    }

    //明細ﾌﾚｰﾑの描画
    parent.detail.location.href="ShizaiTehaiOutput_dtl.jsp";

    return true;
}

//========================================================================================
// 詳細初期表示処理
// 作成者：H.Shima
// 作成日：2014/09/14
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad_dtl() {

    var frm = document.frm00; // ﾌｫｰﾑへの参照

    funGetDetailInfo(1);

    // 旧資材コードの非表示
    frm.txtOldShizai.style.visibility                     = 'hidden';
    // 仕様変更ラジオボタンの非表示
    document.getElementById('siyohenko').style.visibility = 'hidden';
    // 旧資材在庫の非表示
    frm.txtOldShizaiZaiko.style.visibility                = 'hidden';
    //    frm.btnOldShizaiZaikoCheck.style.visibility           = 'hidden';

    // 落版の非表示
    frm.txtRakuhan.style.visibility                       = 'hidden';
//    frm.btnRakuhanCheck.style.visibility                  = 'hidden';


    return true;
}
//========================================================================================
// 権限関連処理
// 作成者：cho
// 作成日：2016/10/04
// 概要  ：権限情報の退避、画面制御を行う
//========================================================================================
function funSaveKengenInfo() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var obj = xmlUSERINFO_O;
    var DataId;
    var reccnt;
    var i;
    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        //GamenId = funXmlRead(obj, "id_gamen", i);
        //KinoId = funXmlRead(obj, "id_kino", i);
        DataId = funXmlRead(obj, "id_data", i);
                if (DataId == "2") {
                    frm.btnYobidasi.disabled = true;
                }
			}
		}


//=========================================================================================
// ========================================================================================
// 画面情報取得処理
// 作成者：H.Shima
// 作成日：2014/09/09
// 引数１：mode ：処理モード
//         1：セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：ユーザのセッション情報、コンボボックスの情報を取得する
// ========================================================================================
function funGetUserInfo(mode) {

    var frm = document.frm00; // ﾌｫｰﾑへの参照
    var XmlId = "RGEN3200";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN2130");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN2130I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN2130O);

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


    // 権限
    if (funXmlRead(xmlResAry[2], "flg_gentyo", 0)) {

        // 原調ユーザーはボタンを活性
       // frm.btnToroku.disabled = false;
       // frm.btnOutput.disabled = false;
        frm.btnClear.disabled  = false;

    } else {
        // 工場ユーザーはボタンを非活性

        //frm.btnYobidasi.disabled = true;
       // frm.btnOutput.disabled = true;
        frm.btnClear.disabled  = true;
    }

	funSaveKengenInfo();



    return true;
}
//========================================================================================
//画面情報取得処理
//作成者：H.Shima
//作成日：2014/09/09
//引数１：mode ：処理モード
//      1：セッション有り、2:セッションなし、3:ユーザ情報取得のみ
//戻り値：正常終了:true／異常終了:false
//概要  ：ユーザのセッション情報、コンボボックスの情報を取得する
//========================================================================================
function funGetDetailInfoTMPLoad(mode, data) {
	 var detail = parent.detail.document.frm00;
	 var frm = document.frm00; // ﾌｫｰﾑへの参照
	 var XmlId = "RGEN3200";
	 var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3310", "FGEN3200");
	 var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3310I, xmlFGEN3200I);
	 var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3310O , xmlFGEN3200O);

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


	 funTempSearch(data);

	 // 確認項目背景色設定
	 funCheckSetting();
    return true;
}
//========================================================================================
// 画面情報取得処理
// 作成者：H.Shima
// 作成日：2014/09/09
// 引数１：mode ：処理モード
//         1：セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：ユーザのセッション情報、コンボボックスの情報を取得する
// ========================================================================================
function funGetDetailInfo(mode) {

	var detail = parent.detail.document.frm00;
    var frm = document.frm00; // ﾌｫｰﾑへの参照
    var XmlId = "RGEN3200";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3310", "FGEN3200");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3310I, xmlFGEN3200I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3310O , xmlFGEN3200O);

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

    // 発注先１
    funCreateComboBox(frm.cmbOrderCom1, xmlResAry[2], 1, 2);



    // 発注先２
    funCreateComboBox(frm.cmbOrderCom2, xmlResAry[2], 1, 2);

     //担当者
    //frm.txtTanto.value = funXmlRead(xmlResAry[1], "nm_user", 0);


    // 対象資材
    funCreateComboBox(frm.cmbTargetSizai, xmlResAry[3], 2, 2);

    // 発注先
    //funCreateComboBox(frm.cmbOrder, xmlResAry[2], 1, 2);

    // 確認項目背景色設定
    funCheckSetting();

    // 資材手配添付データ取得




    return true;
}

//========================================================================================
// 発注先担当者設定処理
// 作成者：H.Shima
// 作成日：2014/10/09
// 引数１：mode ：処理モード
// 戻り値：正常終了:true／異常終了:false
// 概要  ：発注先担当者情報を取得する
//========================================================================================
function funChangeHattyusaki(mode){

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN3290";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3290");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3290I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3290O);

    switch(mode){
    case 1:
        if(frm.cmbOrderCom1.selectedIndex == 0) {
            frm.txtOrderUser1.value = "";
            return true;

        }
        break;
    case 2:
        if(frm.cmbOrderCom2.selectedIndex == 0) {
            frm.txtOrderUser2.value = "";
            return true;
        }
        break;
    }
    selectHattyusaki = mode;
    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //ﾁｰﾑ情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3290, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }
//0926cho_修正
    switch(mode){
    case 1:
        // 発注先担当者１
        funCreateComboBox(frm.txtOrderUser1, xmlResAry[2], 3, 1);
        break;
    case 2:
        // 発注先担当者２
        funCreateComboBox(frm.txtOrderUser2, xmlResAry[2], 3, 1);
        break;
    }
    return true;

}

//========================================================================================
//資材手配添付テーブルデータ検索
//作成者：t2nakamura
//作成日：2016/11/01
//引数１：mode ：処理モード
//戻り値：正常終了:true／異常終了:false
//概要  ：
//========================================================================================
function funShizaiTmp(data) {

	var dataArray = data.split(":::");
	if (dataArray[0] == 1) {

		funGetDetailInfoTMPLoad(1, dataArray);
	}
}

//========================================================================================
// 資材手配テーブルデータ検索
// 作成者：t2nakamura
// 作成日：2016/10/31
// 引数１：mode ：処理モード
// 戻り値：正常終了:true／異常終了:false
// 概要  ：
//========================================================================================
function funTempSearch(data){

	var detail = parent.detail.document.frm00；

    var frm = document.frm00; // ﾌｫｰﾑへの参照
    var XmlId = "RGEN3730";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3730");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3730I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3730O);
    //XMLの初期化
     setTimeout("xmlFGEN3730I.src = '../../model/FGEN3730I.xml';", ConTimer);

    if (data[0] == "1") {
        funXmlWrite(xmlReqAry[1], "kbn", data[0], 0);
        funXmlWrite(xmlReqAry[1], "cd_shain", data[1], 0);
        funXmlWrite(xmlReqAry[1], "nen", data[2], 0);
        funXmlWrite(xmlReqAry[1], "seq_shizai", data[3], 0);
        funXmlWrite(xmlReqAry[1], "no_oi", data[4], 0);
        funXmlWrite(xmlReqAry[1], "no_eda", data[5], 0);
        funXmlWrite(xmlReqAry[1], "cd_shohin", data[6], 0);
    }

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //ﾁｰﾑ情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3730, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }
	// 仮保存が取得できたら実施
	if (funXmlRead(xmlResAry[2], "flg_return_success", 0) == "true") {
	    funShizaiTehaiTmpData();
	}
    return true;

}


//========================================================================================
// 資産手配テーブルデータ取得
//作成者：t2nakamura
//作成日：2016/10/02
//
//戻り値：なし
//概要  ：資材手配入力、資産資材手配依頼出力からの遷移時資産手配テーブルからデータを取得
//========================================================================================
function funShizaiTehaiTmpData() {

    // 版
    var han = funXmlRead(xmlFGEN3730O, "han", 0);
    if (han == 1) {
    	parent.detail.document.getElementById("revision_new").checked = true;
    	parent.detail.document.getElementById("revision_rev").checked = false;
    	parent.detail.document.getElementById("revision_new").onclick();

    } else {
    	parent.detail.document.getElementById("revision_new").checked = false;
    	parent.detail.document.getElementById("revision_rev").checked = true;
    	parent.detail.document.getElementById("revision_rev").onclick();
    }

    // 発注先コード１
    var cd_literal_1 = funXmlRead(xmlFGEN3730O, "cd_literal_1", 0);
    // 発注先名１
   	parent.detail.document.frm00.cmbOrderCom1.value = cd_literal_1;
   	parent.detail.document.frm00.cmbOrderCom1.onchange();
    // 担当者コード
    var cd_2nd_literal_1 = funXmlRead(xmlFGEN3730O, "cd_2nd_literal_1", 0);
    parent.detail.document.frm00.txtOrderUser1.value = cd_2nd_literal_1;

    // 発注先コード２
    var cd_literal_2 =  funXmlRead(xmlFGEN3730O, "cd_literal_2", 0);
    if (cd_literal_2 != "") {
    	parent.detail.document.frm00.cmbOrderCom2.value = cd_literal_2;
    	parent.detail.document.frm00.cmbOrderCom2.onchange();
    }
    // 担当者コード２
    var cd_2nd_literal_2 = funXmlRead(xmlFGEN3730O, "cd_2nd_literal_2", 0);
    parent.detail.document.frm00.txtOrderUser2.value = cd_2nd_literal_2;

    // 内容
    var hyoji_naiyo = funXmlRead(xmlFGEN3730O, "hyoji_naiyo", 0);
    parent.detail.document.frm00.txtNaiyo.value = hyoji_naiyo;

    // 荷姿
    var hyoji_nisugata = funXmlRead(xmlFGEN3730O, "hyoji_nisugata", 0);
    parent.detail.document.frm00.txtNisugata.value = hyoji_nisugata;
    // 対象資材
    var cd_taisyoshizai = funXmlRead(xmlFGEN3730O, "cd_taisyoshizai", 0);
    parent.detail.document.frm00.cmbTargetSizai.value = cd_taisyoshizai;
    //parent.detail.document.frm00.cmbTargetSizai.onchange();

    // 納入先
    var hyoji_nounyusaki = funXmlRead(xmlFGEN3730O, "hyoji_nounyusaki", 0);
    parent.detail.document.frm00.txtDelivery.value = hyoji_nounyusaki;
    // 旧資材コードhidden
    var hyoji_cd_shizai = funXmlRead(xmlFGEN3730O, "hyoji_cd_shizai", 0);
    parent.detail.document.frm00.txtOldShizai.value = hyoji_cd_shizai;
    // 新資材コードhidden
    var hyoji_cd_shizai_new = funXmlRead(xmlFGEN3730O, "hyoji_cd_shizai_new", 0);
    parent.detail.document.frm00.txtNewShizai.value = hyoji_cd_shizai_new;
    // 仕様変更
    var shiyohenko = funXmlRead(xmlFGEN3730O, "shiyohenko", 0);
    if (shiyohenko == 1) {
    	parent.detail.document.frm00.specificationChange.checked = true;
    	parent.detail.document.frm00.specificationChange_nasi.checked = false;
    } else if (shiyohenko == 2) {
    	parent.detail.document.frm00.specificationChange.checked = false;
    	parent.detail.document.frm00.specificationChange_nasi.checked = true;
    } else {

    	parent.detail.document.frm00.specificationChange.checked = true;
    	parent.detail.document.frm00.specificationChange_nasi.checked = false;
    }
    // 設計１
    var sekkei1 = funXmlRead(xmlFGEN3730O, "sekkei1", 0);
    parent.detail.document.frm00.txtDesign1.value = sekkei1;
    // 設計2
    var sekkei2 = funXmlRead(xmlFGEN3730O, "sekkei2", 0);
    parent.detail.document.frm00.txtDesign2.value = sekkei2;
    // 設計3
    var sekkei3 = funXmlRead(xmlFGEN3730O, "sekkei3", 0);
    parent.detail.document.frm00.txtDesign3.value = sekkei3;
    // 材質
    var zaishitsu = funXmlRead(xmlFGEN3730O, "zaishitsu", 0);
    parent.detail.document.frm00.txtZaishitsu.value = zaishitsu;
    // 備考
    var biko_tehai = funXmlRead(xmlFGEN3730O, "biko_tehai", 0);
    parent.detail.document.frm00.txtBiko.value = biko_tehai;
    // 印刷色
    var printcolor = funXmlRead(xmlFGEN3730O, "printcolor", 0);
    parent.detail.document.frm00.txtPrintColor.value = printcolor;
    // 色番号
    var no_color = funXmlRead(xmlFGEN3730O, "no_color", 0);
    parent.detail.document.frm00.txtColorNo.value = no_color;
    // 変更内容詳細
    var henkounaiyoushosai = funXmlRead(xmlFGEN3730O, "henkounaiyoushosai", 0);
    parent.detail.document.frm00.txtChangesDetail.value = henkounaiyoushosai;
    // 納期
    var nouki = funXmlRead(xmlFGEN3730O, "nouki", 0);
    parent.detail.document.frm00.txtDeliveryTime.value = nouki;
    // 数量
    var suryo = funXmlRead(xmlFGEN3730O, "suryo", 0);
    parent.detail.document.frm00.txtQuantity.value = suryo;
    // 旧資材在庫
    var old_sizaizaiko = funXmlRead(xmlFGEN3730O, "old_sizaizaiko", 0);
    parent.detail.document.frm00.txtOldShizaiZaiko.value = old_sizaizaiko;
    // 落版
    var rakuhan = funXmlRead(xmlFGEN3730O, "rakuhan", 0);
    parent.detail.document.frm00.txtRakuhan.value = rakuhan;



}

//========================================================================================
// XMLファイルに書き込み
// 作成者：H.Shima
// 作成日：2009/09/12
// 引数１：XmlId  ：XMLID
// 引数２：reqAry ：機能ID別送信XML(配列)
// 引数３：Mode   ：処理モード
//         1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：なし
// 概要  ：機能IDごとのXMLファイルに引数を設定する
//========================================================================================
function funReadyOutput(XmlId, reqAry, mode) {

    var header = parent.header.document.frm00;
    var detail = parent.detail.document.frm00;
    var i;

    for (i = 0; i < reqAry.length; i++) {
        //画面初期表示
        if (XmlId.toString() == "RGEN3200") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
            }
        }
        else if (XmlId.toString() == "RGEN3290"){
            switch(i){
            case 0://USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;
            case 1:
                // 発注先コード
                var hattyusaki = "";
                switch(selectHattyusaki){
                case 1:
                    hattyusaki = detail.cmbOrderCom1.value;
                    break;
                case 2:
                    hattyusaki = detail.cmbOrderCom2.value;
                    break;
                }

                funXmlWrite_Tbl(reqAry[i], "table", "cd_hattyusaki", hattyusaki, 0);

            }
        }
        // 削除
        else if(XmlId.toString() == "RGEN3320"){
            switch (i) {
            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;
            case 1:    // FGEN3370

            	var element = parent.detail.document.getElementById("sizaid");
            	if (element.hasChildNodes()) {

	                var objShain      = detail.hdnShain.value.split(ConDelimiter);
	                var objNen        = detail.hdnNen.value.split(ConDelimiter);
	                var objNoOi       = detail.hdnNoOi.value.split(ConDelimiter);
	                var objSeqShizai  = detail.hdnSeqShizai.value.split(ConDelimiter);
	                var objNoEda      = detail.hdnNoEda.value.split(ConDelimiter);
	                var objShohinCd   = detail.hdnCdShohin.value.split(ConDelimiter);

	                // 配列の数を取得
	                var max_row = objShain.length;

	                for(var j = 0;j < max_row; j++){

	                    if(j != 0){
	                        funAddRecNode_Tbl(reqAry[i], "FGEN3320", "table");
	                    }

	                    // 社員コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "cd_shain", objShain[j], j);
	                    // 年コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "nen", objNen[j], j);
	                    // 追番
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_oi", objNoOi[j], j);
	                    // seq資材
	                    funXmlWrite_Tbl(reqAry[i], "table", "seq_shizai", objSeqShizai[j], j);
	                    // 枝番
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_eda", objNoEda[j], j);
	                    // 製品コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "cd_shohin", objShohinCd[j], j);
	                    // 区分
	                    funXmlWrite_Tbl(reqAry[i], "table", "kbn", "2", j);
	                }

            	} else {

            		sizaiNyuryokuKey(reqAry[i]);

            	}
            }
        }
        // 登録
        else if (XmlId.toString() == "RGEN3360"){
            switch (i) {
            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;

            case 1:

            	var element = parent.detail.document.getElementById("sizaid");
            	// 複数選択
            	if (element.hasChildNodes()) {

	            	var len = parent.detail.document.frm00.loopCnt.value;
	            	var num = Number(len);
	            	for (var index = 0; index < num; index++) {

		              	var data = parent.detail.document.getElementById("sizaiInser_" + index).value;
		              	var result = data.split(":::");

	                    if(index != 0){
	                    	funAddRecNode_Tbl(reqAry[i], "FGEN3360", "table");
	                    }

	                    // 社員コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "cd_shain", result[0], index);
	                    // 年コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "nen", result[1], index);
	                    // 追番
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_oi", result[2], index);
	                    // 枝番
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_eda", result[4], index);
	                    // seq資材
	                    funXmlWrite_Tbl(reqAry[i], "table", "seq_no", result[3], index);

	                    // 設計①
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei1", detail.txtDesign1.value, index);
	                    // 設計②
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei2", detail.txtDesign2.value, index);
	                    // 設計③
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei3", detail.txtDesign3.value, index);
	                    // 材質
	                    funXmlWrite_Tbl(reqAry[i], "table", "zaishitsu", detail.txtZaishitsu.value, index);
	                    // 印刷色
	                    funXmlWrite_Tbl(reqAry[i], "table", "printcolor", detail.txtPrintColor.value, index);
	                    // 色番号
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_color", detail.txtColorNo.value, index);

	                    // 追加
	                    // 備考
	                    funXmlWrite_Tbl(reqAry[i], "table", "biko", detail.txtBiko.value, index);
	                    // 変更内容詳細
	                    funXmlWrite_Tbl(reqAry[i], "table", "henkou", detail.txtChangesDetail.value, index);
	                    // 納期
	                    funXmlWrite_Tbl(reqAry[i], "table", "nouki", detail.txtDeliveryTime.value, index);
	                    // 数量
	                    funXmlWrite_Tbl(reqAry[i], "table", "suryo", detail.txtQuantity.value, index);


                        // 追加updateData
                        // 内容
                        funXmlWrite_Tbl(reqAry[i], "table", "naiyo", result[8], index);
                        // 製品コード
                        funXmlWrite_Tbl(reqAry[i], "table", "cd_shohin", result[5], index);
                        // 製品名
                        funXmlWrite_Tbl(reqAry[i], "table", "nm_shohin", result[6], index);
                        // 納入先
                        funXmlWrite_Tbl(reqAry[i], "table", "nounyusaki", result[9], index);
                        // 旧資材コード
                        funXmlWrite_Tbl(reqAry[i], "table", "cd_shizai", result[10], index);
                        // 新資材コード
                        funXmlWrite_Tbl(reqAry[i], "table", "cd_shizai_new", result[11], index);
	                }


                } else {

	                for(var j = 0;j < 1; j++){

	                    if(j != 0){
	                        funAddRecNode_Tbl(reqAry[i], "FGEN3360", "table");
	                    }

	                    // 社員コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "cd_shain", detail.cd_shain.value, j);
	                    // 年コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "nen", detail.nen.value, j);
	                    // 追番
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_oi", detail.no_oi.value, j);
	                    // 枝番
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_eda", detail.no_eda.value, j);
	                    // seq資材
	                    funXmlWrite_Tbl(reqAry[i], "table", "seq_no", detail.seq_shizai.value, j);

	                    // 設計①
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei1", detail.txtDesign1.value, j);
	                    // 設計②
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei2", detail.txtDesign2.value, j);
	                    // 設計③
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei3", detail.txtDesign3.value, j);
	                    // 材質
	                    funXmlWrite_Tbl(reqAry[i], "table", "zaishitsu", detail.txtZaishitsu.value, j);
	                    // 印刷色
	                    funXmlWrite_Tbl(reqAry[i], "table", "printcolor", detail.txtPrintColor.value, j);
	                    // 色番号
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_color", detail.txtColorNo.value, j);

		                // 追加
		                // 備考
		                funXmlWrite_Tbl(reqAry[i], "table", "biko", detail.txtBiko.value, j);
		                // 変更内容詳細
		                funXmlWrite_Tbl(reqAry[i], "table", "henkou", detail.txtChangesDetail.value, j);
		                // 納期
		                funXmlWrite_Tbl(reqAry[i], "table", "nouki", detail.txtDeliveryTime.value, j);
		                // 数量
		                funXmlWrite_Tbl(reqAry[i], "table", "suryo", detail.txtQuantity.value, j);


	                    // 追加updateData
	                    // 内容
	                    funXmlWrite_Tbl(reqAry[i], "table", "naiyo", "", j);
	                    // 製品コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "cd_shohin", detail.shohinCd.value, j);
	                    // 製品名
	                    funXmlWrite_Tbl(reqAry[i], "table", "nm_shohin", detail.hinmei.value, j);
	                    // 納入先
	                    funXmlWrite_Tbl(reqAry[i], "table", "nounyusaki", detail.delivery.value, j);
	                    // 旧資材コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "cd_shizai", detail.txtOldShizai.value, j);
	                    // 新資材コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "cd_shizai_new", detail.txtNewShizai.value, j);


	                }
                }

                break;
            }
        }
        // Excel出力
        else if(XmlId.toString() == "RGEN3370"){
            switch (i) {
            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;
            case 1:    // FGEN3370

            	var element = parent.detail.document.getElementById("sizaid");
            	if (element.hasChildNodes()) {

            		var len = parent.detail.document.frm00.loopCnt.value;
            		var num = Number(len);

            		// データ分ループ
            		for (var exCnt = 0; exCnt < num; exCnt++) {

            	    	var data = parent.detail.document.getElementById("sizaiInser_" + exCnt).value;
            	    	var result = data.split(":::");

            	        if(exCnt != 0){
            	            funAddRecNode_Tbl(reqAry[i], "RGEN3370", "table");
            	        }

                        //-------------------PDF用------------------------

                        // 社員コード
                        funXmlWrite_Tbl(reqAry[i], "table", "cd_shain", result[0], exCnt);
                        // 年コード
                        funXmlWrite_Tbl(reqAry[i], "table", "nen", result[1], exCnt);
                        // 追番
                        funXmlWrite_Tbl(reqAry[i], "table", "no_oi", result[2], exCnt);
                        // seq資材
                        funXmlWrite_Tbl(reqAry[i], "table", "seq_shizai", result[3], exCnt);
                        // 枝番
                        funXmlWrite_Tbl(reqAry[i], "table", "no_eda", result[4], exCnt);
                        // 製品コード
                        funXmlWrite_Tbl(reqAry[i], "table", "cd_shohinPDF", result[5], exCnt);


                        // 新版/改版
                        var valRevision = "";
                        for(var j = 0; j < detail.revision.length; j++){
                            if(detail.revision[j].checked){
                                valRevision = detail.revision[j].value;
                                break;
                            }
                        }

                        funXmlWrite_Tbl(reqAry[i], "table", "revision", valRevision, exCnt);
                        // 発注先１(会社)
                        funXmlWrite_Tbl(reqAry[i], "table", "hattyusaki_com1", detail.cmbOrderCom1.value, exCnt);
                        // 発注先１(ユーザ)
                        funXmlWrite_Tbl(reqAry[i], "table", "hattyusaki_user1", detail.txtOrderUser1.value, exCnt);
                        // 発注先２(会社)
                        funXmlWrite_Tbl(reqAry[i], "table", "hattyusaki_com2", detail.cmbOrderCom2.value, exCnt);
                        // 発注先２(ユーザ)
                        funXmlWrite_Tbl(reqAry[i], "table", "hattyusaki_user2", detail.txtOrderUser2.value, exCnt);
                        // 内容
                        funXmlWrite_Tbl(reqAry[i], "table", "naiyo", detail.txtNaiyo.value, exCnt);
                        // 商品コード
                        funXmlWrite_Tbl(reqAry[i], "table", "cd_shohin", detail.txtSyohin.value, exCnt);
                        // 品名
                        funXmlWrite_Tbl(reqAry[i], "table", "hinmei", detail.txtHinmei.value, exCnt);
                        // 荷姿
                        funXmlWrite_Tbl(reqAry[i], "table", "nisugata", detail.txtNisugata.value, exCnt);
                        // 対象資材
                        funXmlWrite_Tbl(reqAry[i], "table", "taisyosizai", detail.cmbTargetSizai.value, exCnt);
                        // 発注先
                        //funXmlWrite(reqAry[i], "table", "hattyusaki", detail.cmbOrder.value, 0);
                        // 納入先
                        funXmlWrite_Tbl(reqAry[i], "table", "nounyusaki", detail.txtDelivery.value, exCnt);
                        // 旧資材コード
                        funXmlWrite_Tbl(reqAry[i], "table", "old_shizai_cd", detail.txtOldShizai.value, exCnt);
                        // 新資材コード
                        funXmlWrite_Tbl(reqAry[i], "table", "new_shizai_cd", detail.txtNewShizai.value, exCnt);
                        // 仕様変更
                        var valSiyohenko = "";
                        for(var j = 0; j < detail.specificationChange.length; j++){
                            if(detail.specificationChange[j].checked){
                                valSiyohenko = detail.specificationChange[j].value;
                                break;
                            }
                        }
                        funXmlWrite_Tbl(reqAry[i], "table", "siyohenko", valSiyohenko, exCnt);
                        // 設計①
                        funXmlWrite_Tbl(reqAry[i], "table", "sekkei1", detail.txtDesign1.value, exCnt);
                        // 設計②
                        funXmlWrite_Tbl(reqAry[i], "table", "sekkei2", detail.txtDesign2.value, exCnt);
                        // 設計③
                        funXmlWrite_Tbl(reqAry[i], "table", "sekkei3", detail.txtDesign3.value, exCnt);
                        // 材質
                        funXmlWrite_Tbl(reqAry[i], "table", "zaishitsu", detail.txtZaishitsu.value, exCnt);
                        // 備考
                        funXmlWrite_Tbl(reqAry[i], "table", "biko_tehai", detail.txtBiko.value, exCnt);
                        // 印刷色
                        funXmlWrite_Tbl(reqAry[i], "table", "printcolor", detail.txtPrintColor.value, exCnt);
                        // 色番号
                        funXmlWrite_Tbl(reqAry[i], "table", "no_color", detail.txtColorNo.value, exCnt);
                        // 変更内容詳細
                        funXmlWrite_Tbl(reqAry[i], "table", "henkounaiyoushosai", detail.txtChangesDetail.value, exCnt);
                        // 納期
                        funXmlWrite_Tbl(reqAry[i], "table", "nouki", detail.txtDeliveryTime.value, exCnt);
                        // 数量
                        funXmlWrite_Tbl(reqAry[i], "table", "suryo", detail.txtQuantity.value, exCnt);
                        // 旧資材在庫
                        funXmlWrite_Tbl(reqAry[i], "table", "old_sizaizaiko", detail.txtOldShizaiZaiko.value, exCnt);
                         // 落版
                        funXmlWrite_Tbl(reqAry[i], "table", "rakuhan", detail.txtRakuhan.value, exCnt);


            		}
            	// 資材入力
            	} else {

                    // 新版/改版
                    var valRevision = "";
                    for(var j = 0; j < detail.revision.length; j++){
                        if(detail.revision[j].checked){
                            valRevision = detail.revision[j].value;
                            break;
                        }
                    }
                    funXmlWrite(reqAry[i], "revision", valRevision, 0);
                    // 発注先１(会社)
                    funXmlWrite(reqAry[i], "hattyusaki_com1", detail.cmbOrderCom1.value, 0);
                    // 発注先１(ユーザ)
                    funXmlWrite(reqAry[i], "hattyusaki_user1", detail.txtOrderUser1.value, 0);
                    // 発注先２(会社)
                    funXmlWrite(reqAry[i], "hattyusaki_com2", detail.cmbOrderCom2.value, 0);
                    // 発注先２(ユーザ)
                    funXmlWrite(reqAry[i], "hattyusaki_user2", detail.txtOrderUser2.value, 0);
                    // 内容
                    funXmlWrite(reqAry[i], "naiyo", detail.txtNaiyo.value, 0);

                    // 商品コード
                    funXmlWrite(reqAry[i], "cd_shohin", detail.txtSyohin.value, 0);
                    // 品名
                    funXmlWrite(reqAry[i], "hinmei", detail.txtHinmei.value, 0);
                    // 荷姿
                    funXmlWrite(reqAry[i], "nisugata", detail.txtNisugata.value, 0);
                    // 対象資材
                    funXmlWrite(reqAry[i], "taisyosizai", detail.cmbTargetSizai.value, 0);
                    // 発注先
                    //funXmlWrite(reqAry[i], "hattyusaki", detail.cmbOrder.value, 0);
                    // 納入先
                    funXmlWrite(reqAry[i], "nounyusaki", detail.txtDelivery.value, 0);
                    // 旧資材コード
                    funXmlWrite(reqAry[i], "old_shizai_cd", detail.txtOldShizai.value, 0);
                    // 新資材コード
                    funXmlWrite(reqAry[i], "new_shizai_cd", detail.txtNewShizai.value, 0);
                    // 仕様変更
                    var valSiyohenko = "";
                    for(var j = 0; j < detail.specificationChange.length; j++){
                        if(detail.specificationChange[j].checked){
                            valSiyohenko = detail.specificationChange[j].value;
                            break;
                        }
                    }
                    funXmlWrite(reqAry[i], "siyohenko", valSiyohenko, 0);
                    // 設計①
                    funXmlWrite(reqAry[i], "sekkei1", detail.txtDesign1.value, 0);
                    // 設計②
                    funXmlWrite(reqAry[i], "sekkei2", detail.txtDesign2.value, 0);
                    // 設計③
                    funXmlWrite(reqAry[i], "sekkei3", detail.txtDesign3.value, 0);
                    // 材質
                    funXmlWrite(reqAry[i], "zaishitsu", detail.txtZaishitsu.value, 0);
                    // 備考
                    funXmlWrite(reqAry[i], "biko_tehai", detail.txtBiko.value, 0);
                    // 印刷色
                    funXmlWrite(reqAry[i], "printcolor", detail.txtPrintColor.value, 0);
                    // 色番号
                    funXmlWrite(reqAry[i], "no_color", detail.txtColorNo.value, 0);
                    // 変更内容詳細
                    funXmlWrite(reqAry[i], "henkounaiyoushosai", detail.txtChangesDetail.value, 0);
                    // 納期
                    funXmlWrite(reqAry[i], "nouki", detail.txtDeliveryTime.value, 0);
                    // 数量
                    funXmlWrite(reqAry[i], "suryo", detail.txtQuantity.value, 0);
                    // 旧資材在庫
                    funXmlWrite(reqAry[i], "old_sizaizaiko", detail.txtOldShizaiZaiko.value, 0);
                     // 落版
                    funXmlWrite(reqAry[i], "rakuhan", detail.txtRakuhan.value, 0);

                    //-------------------PDF用------------------------

                    // 社員コード
                	funXmlWrite(reqAry[i], "cd_shain", detail.cd_shain.value, 0);
                    // 年コード
                	funXmlWrite(reqAry[i], "nen", detail.nen.value, 0);
                    // 追番
                	funXmlWrite(reqAry[i], "no_oi", detail.no_oi.value, 0);
                    // seq資材
                	funXmlWrite(reqAry[i], "seq_shizai", detail.seq_shizai.value, 0);
                    // 枝番
                	funXmlWrite(reqAry[i], "no_eda", detail.no_eda.value, 0);
            	}

                break;

            case 2:    // FGEN3380

            	var kbn = detail.flg_hatyuu_status.value;

            	var element = parent.detail.document.getElementById("sizaid");
            	// 複数選択
            	if (element.hasChildNodes()) {

            		heddenParamSet("FGEN3380",reqAry[i]);

            		// 資材入力
            	} else {

            		// 区分
            		funXmlWrite(reqAry[i], "kbn", "1", index);
                    // 社員コード
                	funXmlWrite(reqAry[i], "cd_shain", detail.cd_shain.value, 0);
                    // 年コード
                	funXmlWrite(reqAry[i], "nen", detail.nen.value, 0);

                    // 追番
                	funXmlWrite(reqAry[i], "no_oi", detail.no_oi.value, 0);

                    // seq資材
                	funXmlWrite(reqAry[i], "seq_shizai", detail.seq_shizai.value, 0);

                    // 枝番
                	funXmlWrite(reqAry[i], "no_eda", detail.no_eda.value, 0);

                	// 製品コード
                	funXmlWrite(reqAry[i], "cd_shohin", detail.shohinCd.value, 0);
                	// 発注先コード１
                	funXmlWrite(reqAry[i], "cd_hattyuusaki", detail.cmbOrderCom1.value, 0);
                	// 対象資材コード
                	funXmlWrite(reqAry[i], "cd_taisyoshizai", detail.cmbTargetSizai.value, 0);

            	}

                break;
            }
        }
        // 削除処理_手配済み確認
        else if(XmlId.toString() == "RGEN3390"){
            switch (i) {
            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;
            case 1:    // FGEN3390

            	var element = parent.detail.document.getElementById("sizaid");
            	if (element.hasChildNodes()) {


	                var objShain      = detail.hdnShain.value.split(ConDelimiter);
	                var objNen        = detail.hdnNen.value.split(ConDelimiter);
	                var objNoOi       = detail.hdnNoOi.value.split(ConDelimiter);
	                var objSeqShizai  = detail.hdnSeqShizai.value.split(ConDelimiter);
	                var objNoEda      = detail.hdnNoEda.value.split(ConDelimiter);
	                var objShohinCd   = detail.hdnCdShohin.value.split(ConDelimiter);

	                // 配列の数を取得
	                var max_row = objShain.length;

	                for(var j = 0;j < max_row; j++){

	                    if(j != 0){
	                        funAddRecNode_Tbl(reqAry[i], "FGEN3390", "table");
	                    }

	                    // 社員コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "cd_shain", objShain[j], j);
	                    // 年コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "nen", objNen[j], j);
	                    // 追番
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_oi", objNoOi[j], j);
	                    // seq資材
	                    funXmlWrite_Tbl(reqAry[i], "table", "seq_shizai", objSeqShizai[j], j);
	                    // 枝番
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_eda", objNoEda[j], j);
	                    // 製品コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "cd_shohin", objShohinCd[j], j);
	                    // 区分
	                    funXmlWrite_Tbl(reqAry[i], "table", "kbn", "2", j);

	                }

            	} else {
            		sizaiNyuryokuKey(reqAry[i]);
            	}
            }
        }
        // 仮保存処理
        else if(XmlId.toString() == "RGEN3700") {

        	switch (i) {

            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;

            case 1:

                var objShain      = detail.hdnShain.value.split(ConDelimiter);
                // 複数
                if (objShain != "") {

	                // 資材添付テーブルインサート用
                	var loopCnt = detail.loopCnt.value;
                	var loopCntNum = parseInt(loopCnt);

	                for (var sizaiCnt = 0; sizaiCnt < loopCntNum; sizaiCnt++) {

	                	var data = parent.detail.document.getElementById("sizaiInser_" + sizaiCnt).value;
	                	var result = data.split(":::");

	                    if(sizaiCnt != 0){
	                    	funAddRecNode_Tbl(reqAry[i], "FGEN3700", "table");
	                    }
	                	// 社員コード
	                	funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_shain", result[0], sizaiCnt);
	                	// 年
	                	funXmlWrite_Tbl(reqAry[i], "table", "insert_nen", result[1],  sizaiCnt);
	                	// 追番
	                	funXmlWrite_Tbl(reqAry[i], "table", "insert_no_oi", result[2], sizaiCnt);
	                	// seq番号
	                	funXmlWrite_Tbl(reqAry[i], "table", "insert_seq_shizai", result[3], sizaiCnt);
	                	// 枝番
	                	funXmlWrite_Tbl(reqAry[i], "table", "insert_no_eda", result[4], sizaiCnt);
	                	// 区分（複数選択からの場合区分：2）
	                	funXmlWrite_Tbl(reqAry[i], "table", "insert_kbn_shizai", 2, sizaiCnt);
	                    // 新版／改版
	                    var revision_new = parent.detail.document.getElementById("revision_new").checked;
	                    if (revision_new) {
	                    	funXmlWrite_Tbl(reqAry[i], "table", "insert_han", 1, sizaiCnt)
	                    } else {
	                    	funXmlWrite_Tbl(reqAry[i], "table", "insert_han", 0, sizaiCnt)
	                    }

	                	// 発注先１
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_literal1", detail.cmbOrderCom1.value, sizaiCnt);
	                    // 担当者１
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_2nd_literal1", detail.txtOrderUser1.value, sizaiCnt);
	                	// 発注先２
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_liiteral2", detail.cmbOrderCom2.value, sizaiCnt);
	                    // 担当者２
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_2nd_literal2", detail.txtOrderUser2.value, sizaiCnt);
	                    //
	                    // 内容
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_naiyo", result[8], sizaiCnt);
	                    // 製品コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_shohin", result[5], sizaiCnt);
	                    // 製品名
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_nm_shohin", result[6], sizaiCnt);
	                    // 荷姿
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_nisugata", result[7], sizaiCnt);
	                    // 対象資材
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_taisyoshizai", detail.cmbTargetSizai.value, sizaiCnt);
	                    // 納入先
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_nounyusaki", result[9], sizaiCnt);
	                    // 旧資材コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_shizai", result[10], sizaiCnt);
	                    // 新資材コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_shizai_new", result[11], sizaiCnt);
	                    // 仕様変更
	                    if (parent.detail.document.getElementById("specificationChange_ari").checked) {
	                    	funXmlWrite_Tbl(reqAry[i], "table", "insert_shiyohenko", 1, sizaiCnt);
	                    } else if (parent.detail.document.getElementById("specificationChange_nasi").checked) {
	                    	funXmlWrite_Tbl(reqAry[i], "table", "insert_shiyohenko", 2, sizaiCnt);
	                    }
	                    // 設計①
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei1", detail.txtDesign1.value, sizaiCnt);
	                    // 設計②
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei2", detail.txtDesign2.value, sizaiCnt);
	                    // 設計③
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei3", detail.txtDesign3.value, sizaiCnt);
	                    // 材質
	                    funXmlWrite_Tbl(reqAry[i], "table", "zaishitsu", detail.txtZaishitsu.value, sizaiCnt);
	                    // 備考
	                    funXmlWrite_Tbl(reqAry[i], "table", "biko", detail.txtBiko.value, sizaiCnt);// *
	                    // 印刷色
	                    funXmlWrite_Tbl(reqAry[i], "table", "printcolor", detail.txtPrintColor.value, sizaiCnt);
	                    // 色番号
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_color", detail.txtColorNo.value, sizaiCnt);
	                    // 変更内容詳細
	                    funXmlWrite_Tbl(reqAry[i], "table", "henkounaiyoushosai", detail.txtChangesDetail.value, sizaiCnt);
	                    // 納期
	                    funXmlWrite_Tbl(reqAry[i], "table", "nouki", detail.txtDeliveryTime.value, sizaiCnt);
	                    // 数量
	                    funXmlWrite_Tbl(reqAry[i], "table", "suryo", detail.txtQuantity.value, sizaiCnt);
	                    // 旧資材在庫
	                    funXmlWrite_Tbl(reqAry[i], "table", "old_sizaizaiko", detail.txtOldShizaiZaiko.value, sizaiCnt);
	                    // 落版
	                    funXmlWrite_Tbl(reqAry[i], "table", "rakuhan", detail.txtRakuhan.value, sizaiCnt);

	                    // 表示内容
	                    funXmlWrite_Tbl(reqAry[i], "table", "hyoji_naiyo", detail.txtNaiyo.value, sizaiCnt);
	                    // 表示荷姿
	                    funXmlWrite_Tbl(reqAry[i], "table", "hyoji_nisugata", detail.txtNisugata.value, sizaiCnt);
	                    // 表示納入先
	                    funXmlWrite_Tbl(reqAry[i], "table", "hyoji_nounyusaki", detail.txtDelivery.value, sizaiCnt);
	                    // 表示旧資材コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "hyoji_cd_shizai", detail.txtOldShizai.value, sizaiCnt);
	                    // 表示新資材コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "hyoji_cd_shizai_new", detail.txtNewShizai.value, sizaiCnt);
	                    // 追加
	                    // 版代支払日
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_dt_han_payday", result[13], sizaiCnt);
	                    // 版代
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_file_han_pay", result[14], sizaiCnt);
	                    // 青焼ファイル名
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_nm_file_aoyaki", result[15], sizaiCnt);
	                    // 青焼ファイルパス
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_file_path_aoyaki", result[16], sizaiCnt);

	                }
	                // 資材手配入力から遷移の時
                } else {

	                for(var j = 0;j < 1; j++){

	                    if(j != 0){
	                        funAddRecNode_Tbl(reqAry[i], "FGEN3700", "table");
	                    }

	                    // 社員コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_shain", detail.cd_shain.value, j);
	                    // 年コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_nen", detail.nen.value, j);
	                    // 追番
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_no_oi", detail.no_oi.value, j);
	                    // 枝番
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_no_eda", detail.no_eda.value, j);
	                    // seq資材
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_seq_shizai", detail.seq_shizai.value, j);
	                    // 区分資材 	資材手配入力からの場合区分：
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_kbn_shizai", detail.flg_hatyuu_status.value, j);

	                    // 新版／改版
	                    var revision_new = parent.detail.document.getElementById("revision_new").checked;
	                    if (revision_new) {
	                    	funXmlWrite_Tbl(reqAry[i], "table", "insert_han", 1, sizaiCnt)
	                    } else {
	                    	funXmlWrite_Tbl(reqAry[i], "table", "insert_han", 0, sizaiCnt)
	                    }

	                	// 発注先１
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_literal1", detail.cmbOrderCom1.value, sizaiCnt);
	                    // 担当者１
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_2nd_literal1", detail.txtOrderUser1.value, sizaiCnt);
	                	// 発注先２
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_liiteral2", detail.cmbOrderCom2.value, sizaiCnt);
	                    // 担当者２
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_2nd_literal2", detail.txtOrderUser2.value, sizaiCnt);

	                    // 内容
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_naiyo", detail.txtNaiyo.value, sizaiCnt);
	                    // 製品コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_shohin", detail.shohinCd.value, sizaiCnt);

	                    // 製品名
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_nm_shohin", detail.hinmei.value, sizaiCnt);
	                    // 荷姿
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_nisugata", detail.nisugata.value, sizaiCnt);
	                    // 対象資材
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_taisyoshizai", detail.cmbTargetSizai.value, sizaiCnt);
	                    // 納入先
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_nounyusaki", detail.delivery.value, sizaiCnt);
	                    // 旧資材コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_shizai", detail.olsShizai.value, sizaiCnt);
	                    // 新資材コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_shizai_new", detail.newShizai.value, sizaiCnt);
	                    // 仕様変更
	                    if (detail.specificationChange.checked) {
	                    	funXmlWrite_Tbl(reqAry[i], "table", "insert_shiyohenko", 1, sizaiCnt);
	                    } else {
	                    	funXmlWrite_Tbl(reqAry[i], "table", "insert_shiyohenko", 0, sizaiCnt);
	                    }

	                    // 設計①
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei1", detail.txtDesign1.value, j);
	                    // 設計②
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei2", detail.txtDesign2.value, j);
	                    // 設計③
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei3", detail.txtDesign3.value, j);
	                    // 材質
	                    funXmlWrite_Tbl(reqAry[i], "table", "zaishitsu", detail.txtZaishitsu.value, sizaiCnt);
	                    // 備考
	                    funXmlWrite_Tbl(reqAry[i], "table", "biko", detail.txtBiko.value, sizaiCnt);// *
	                    // 印刷色
	                    funXmlWrite_Tbl(reqAry[i], "table", "printcolor", detail.txtPrintColor.value, sizaiCnt);
	                    // 色番号
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_color", detail.txtColorNo.value, sizaiCnt);
	                    // 変更内容詳細
	                    funXmlWrite_Tbl(reqAry[i], "table", "henkounaiyoushosai", detail.txtChangesDetail.value, sizaiCnt);
	                    // 納期
	                    funXmlWrite_Tbl(reqAry[i], "table", "nouki", detail.txtDeliveryTime.value, sizaiCnt);
	                    // 数量
	                    funXmlWrite_Tbl(reqAry[i], "table", "suryo", detail.txtQuantity.value, sizaiCnt);
	                    // 旧資材在庫
	                    funXmlWrite_Tbl(reqAry[i], "table", "old_sizaizaiko", detail.txtOldShizaiZaiko.value, sizaiCnt);
	                    // 落版
	                    funXmlWrite_Tbl(reqAry[i], "table", "rakuhan", detail.txtRakuhan.value, sizaiCnt);

	                    // 表示内容
	                    funXmlWrite_Tbl(reqAry[i], "table", "hyoji_naiyo", detail.txtNaiyo.value, sizaiCnt);
	                    // 表示荷姿
	                    funXmlWrite_Tbl(reqAry[i], "table", "hyoji_nisugata", detail.txtNisugata.value, sizaiCnt);
	                    // 表示納入先
	                    funXmlWrite_Tbl(reqAry[i], "table", "hyoji_nounyusaki", detail.txtDelivery.value, sizaiCnt);
	                    // 表示旧資材コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "hyoji_cd_shizai", detail.txtOldShizai.value, sizaiCnt);
	                    // 表示新資材コード
	                    funXmlWrite_Tbl(reqAry[i], "table", "hyoji_cd_shizai_new", detail.txtNewShizai.value, sizaiCnt);
	                    // 追加
	                    // 版代支払日
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_dt_han_payday", "", sizaiCnt);
	                    // 版代
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_file_han_pay", "", sizaiCnt);
	                    // 青焼ファイル名
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_nm_file_aoyaki", "", sizaiCnt);
	                    // 青焼ファイルパス
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_file_path_aoyaki", "", sizaiCnt);
	                }
                }

                break;
        	}
        }
        // 資材手配入力から遷移時、資材手配テーブルに整合するデータがある場合設定する。
        else if (XmlId.toString() == "RGEN3730") {
        	switch (i) {

            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;

            case 1:
            	var kbn = funXmlRead(reqAry[1], "kbn", 0);

            	if (kbn == 1) {

            		heddenParamSetNyuryoku("FGEN3730", reqAry[i]);

            	}
            	else {
            		heddenParamSet("FGEN3730",reqAry[i])            	}

        	}
        }

    }

    return true;

}
//========================================================================================
//複数選択 仮保存時、登録
//作成者：t2nakamura
//作成日：2016/11/04
//引数  ：
//概要  ：
//========================================================================================
function heddenParamSetNyuryoku(key, xmlNo) {

	for (var index = 0; index < 1; index++) {

        if(index != 0){
            funAddRecNode_Tbl(xmlNo, key, "table");
        }
		// 区分
        funXmlWrite_Tbl(xmlNo, "table", "kbn", funXmlRead(xmlNo, "kbn", 0), index);
        // 社員コード
        funXmlWrite_Tbl(xmlNo, "table", "cd_shain", funXmlRead(xmlNo, "cd_shain", 0), index);
        // 年コード
        funXmlWrite_Tbl(xmlNo, "table", "nen", funXmlRead(xmlNo, "nen", 0), index);
        // 追番
        funXmlWrite_Tbl(xmlNo, "table", "seq_shizai", funXmlRead(xmlNo, "seq_shizai", 0), index);
        // seq資材
        funXmlWrite_Tbl(xmlNo, "table", "no_oi", funXmlRead(xmlNo, "no_oi", 0), index);
        // 枝番
        funXmlWrite_Tbl(xmlNo, "table", "no_eda", funXmlRead(xmlNo, "no_eda", 0), index);
        // 製品コード
        funXmlWrite_Tbl(xmlNo, "table", "cd_shohin",funXmlRead(xmlNo, "cd_shohin", 0), index);
        // 対象資材
        funXmlWrite_Tbl(xmlNo, "table", "cd_taisyoshizai","", index);


	}
}


//========================================================================================
//複数選択 仮保存時、登録
//作成者：t2nakamura
//作成日：2016/11/04
//引数  ：
//概要  ：
//========================================================================================
function heddenParamSet(key, xmlNo) {

	var len = parent.detail.document.frm00.loopCnt.value;
	var num = Number(len);
	for (var index = 0; index < num; index++) {

    	var data = parent.detail.document.getElementById("sizaiInser_" + index).value;
    	var result = data.split(":::");

        if(index != 0){
            funAddRecNode_Tbl(xmlNo, key, "table");
        }

		// 区分
        funXmlWrite_Tbl(xmlNo, "table", "kbn", "2", index);
        // 社員コード
        funXmlWrite_Tbl(xmlNo, "table", "cd_shain", result[0], index);
        // 年コード
        funXmlWrite_Tbl(xmlNo, "table", "nen", result[1], index);
        // 追番
        funXmlWrite_Tbl(xmlNo, "table", "no_oi", result[2], index);
        // seq資材
        funXmlWrite_Tbl(xmlNo, "table", "seq_shizai", result[3], index);
         // 枝番
        funXmlWrite_Tbl(xmlNo, "table", "no_eda", result[4], index);
        // 製品コード
        funXmlWrite_Tbl(xmlNo, "table", "cd_shohin",result[5], index);
    	// 発注先コード１
        funXmlWrite_Tbl(xmlNo, "table", "cd_hattyuusaki", parent.detail.document.frm00.cmbOrderCom1.value, 0);
    	// 対象資材コード
        funXmlWrite_Tbl(xmlNo, "table", "cd_taisyoshizai", parent.detail.document.frm00.cmbTargetSizai.value, 0);

	}
}


function sizaiNyuryokuKey(xmlNo) {

    // 社員コード
	funXmlWrite(xmlNo, "cd_shain", parent.detail.document.frm00.cd_shain.value, 0);
    // 年コード
	funXmlWrite(xmlNo, "nen", parent.detail.document.frm00.nen.value, 0);
    // 追番
	funXmlWrite(xmlNo, "no_oi", parent.detail.document.frm00.no_oi.value, 0);
    // seq資材
	funXmlWrite(xmlNo, "seq_shizai", parent.detail.document.frm00.seq_shizai.value, 0);
    // 枝番
	funXmlWrite(xmlNo, "no_eda", parent.detail.document.frm00.no_eda.value, 0);
	// 製品コード
	funXmlWrite(xmlNo, "cd_shohin", parent.detail.document.frm00.shohinCd.value, 0);
	// 区分
	funXmlWrite(xmlNo, "kbn", parent.detail.document.frm00.flg_hatyuu_status.value, 0);


}

//========================================================================================
//複数選択 発進時、登録
//作成者：t2nakamura
//作成日：2016/11/10
//引数  ：
//概要  ：
//========================================================================================
function heddenParamSetToroku(key, xmlNo) {

	var len = parent.detail.document.frm00.loopCnt.value;
	var num = Number(len);
	for (var index = 0; index < num; index++) {

  	var data = parent.detail.document.getElementById("sizaiInser_" + index).value;
  	var result = data.split(":::");

      if(index != 0){
          funAddRecNode_Tbl(xmlNo, key, "table");
      }

		// 区分
      funXmlWrite_Tbl(xmlNo, "table", "kbn", "2", index);
      // 社員コード
      funXmlWrite_Tbl(xmlNo, "table", "cd_shain", result[0], index);
      // 年コード
      funXmlWrite_Tbl(xmlNo, "table", "nen", result[1], index);
      // 追番
      funXmlWrite_Tbl(xmlNo, "table", "no_oi", result[2], index);
      // seq資材
      funXmlWrite_Tbl(xmlNo, "table", "seq_shizai", result[3], index);
       // 枝番
      funXmlWrite_Tbl(xmlNo, "table", "no_eda", result[4], index);
      // 製品コード
      funXmlWrite_Tbl(xmlNo, "table", "cd_shohin",result[5], index);
      // 製品名
      funXmlWrite_Tbl(xmlNo, "table", "cd_shohin_nm",result[6], index);
      // 納入先
      funXmlWrite_Tbl(xmlNo, "table", "cd_shohin_nm",result[6], index);
      // 旧資材コード

      // 新資材コード





	}
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

        case 2:    // リテラルマスタ
            atbName = "nm_literal";
            atbCd   = "cd_literal";
            break;
        case 3:    // cho発注先担当者
            atbName = "nm_tantosha";
            atbCd   = "cd_tantosha";
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
// 改版ラジオボタン切り替え処理
// 作成者：H.Shima
// 作成日：2014/09/09
// 概要  ：項目の表示状態を切り替える
//========================================================================================
function funKaihan(mode){
    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    switch(mode){
    case 1:
        // 旧資材コードの非表示
        frm.txtOldShizai.style.visibility                     = 'hidden';
        // 仕様変更ラジオボタンの非表示
        document.getElementById('siyohenko').style.visibility = 'hidden';
        // 旧資材在庫の非表示
        frm.txtOldShizaiZaiko.style.visibility                = 'hidden';
//        frm.btnOldShizaiZaikoCheck.style.visibility           = 'hidden';
        // 落版の非表示
        frm.txtRakuhan.style.visibility                       = 'hidden';
//        frm.btnRakuhanCheck.style.visibility                  = 'hidden';
        break;

    case 2:
        // 旧資材コードの表示
        frm.txtOldShizai.style.visibility                     = 'visible';
        // 仕様変更ラジオボタンの表示
        document.getElementById('siyohenko').style.visibility = 'visible';
        // 旧資材在庫の表示
        frm.txtOldShizaiZaiko.style.visibility                = 'visible';
//        frm.btnOldShizaiZaikoCheck.style.visibility           = 'visible';
        // 落版の表示
        frm.txtRakuhan.style.visibility                       = 'visible';
 //       frm.btnRakuhanCheck.style.visibility                  = 'visible';
        break;
    }
}
//========================================================
//test
//==========================================================
function funtest()
{



	  var Syohin = parent.detail.document.frm00.hdnSyohin_disp.value

	  var Hinmei = parent.detail.document.frm00.hdnHinmei_disp.value;

	  var Nisugata = parent.detail.document.frm00.hdnNisugata_disp.value;

	  var OldShizai = parent.detail.document.frm00.hdnOldShizai_disp.value;

	  var hdnNewShizai = parent.detail.document.frm00.hdnNewShizai_disp.value;

	  var txtDesign1 = parent.detail.document.frm00.txtDesign1.value

	  var txtDesign2 = parent.detail.document.frm00.txtDesign2.value

	  var txtDesign3 = parent.detail.document.frm00.txtDesign3.value

	  var txtPrintColor = parent.detail.document.frm00.txtPrintColor.value

	  var txtColorNo = parent.detail.document.frm00.txtColorNo.value



}

//========================================================================================
// 資材検索画面表示処理
// 作成者：H.Shima
// 作成日：2014/09/12
// 概要  ：資材検索画面を表示する
//========================================================================================
function funShizaiSearch(mode){

    var header = parent.header.document.frm00;
    var detail = parent.detail.document.frm00;

    var wUrl;

    switch(mode){
    case 1:
    	// 資材手配入力からのデータ保持
    	// 対象先コード
    	var cmbTargetSizai =detail.cmbTargetSizai.value;
    	// 商品コード
    	var txtSyohin = detail.txtSyohin.value;
    	// 品名
    	var txtHinmei = detail.txtHinmei.value;
    	// 荷姿
    	var txtNisugata =  detail.txtNisugata.value;
    	// 旧資材コード
    	var txtOldShizai =  detail.txtOldShizai.value;
    	// 新資材コード
    	var txtNewShizai = detail.txtNewShizai.value;
    	// 内容
    	var txtNaiyo = detail.txtNaiyo.value;
    	// 納入先
    	var txtDelivery = detail.txtDelivery.value;

    	var put_code = cmbTargetSizai + ":::" + txtSyohin + ":::" + txtHinmei + ":::"
    					+ txtNisugata + ":::" + txtOldShizai + ":::" + txtNewShizai + ":::"
    					+ txtNaiyo + ":::" + txtDelivery + ":::";
    	funXmlWrite(xmlUSERINFO_I, "MOVEMENT_CONDITION", put_code, 0);
    	// 読み出し funXmlRead(xmlUSERINFO_I, "MOVEMENT_CONDITION", 0)

        // 資材コード検索画面
        var logic = "shizai"
        wUrl = "../SQ260ShizaiCodeList/SQ260ShizaiCodeList.jsp" + "?" + logic;

        funModalCall(wUrl);

        if (detail.hdnShain.value != "") {


	        // 返却情報を詳細画面に表示
	        detail.cmbTargetSizai.value = detail.hdnTaisyosizai_disp.value; // 対象資材

	        detail.txtSyohin.value      = detail.hdnSyohin_disp.value;      // 商品コード
	        detail.txtHinmei.value      = detail.hdnHinmei_disp.value;      // 品名
	        detail.txtNisugata.value    = detail.hdnNisugata_disp.value;    // 荷姿
	        detail.txtOldShizai.value   = detail.hdnOldShizai_disp.value;   // 旧資材コード
	        detail.txtNewShizai.value   = detail.hdnNewShizai_disp.value;   // 新資材コード

	        detail.txtNaiyo.value       = detail.hdnNaiyo_disp.value;		// 内容
	        detail.txtDelivery.value    = detail.hdnNounyusaki_disp.value;	// 納入先

	        // 資材添付テーブルから再取得
	        funTempSearch(new Array(1));
	        // 編集フラグをtrueに
	        setFg_hensyu();

	     // 選択しないで終了ボタンを押下した場合資材手配入力から取得したデータを設定しなおす。
    	} else if (detail.hdnShain.value == "" && txtNewShizai != ""){
    		var sizaitehaiData = funXmlRead(xmlUSERINFO_I, "MOVEMENT_CONDITION", 0)

    		var shizaitehaiList = sizaitehaiData.split(":::");

	        detail.cmbTargetSizai.value = shizaitehaiList[0]; // 対象資材

	        detail.txtSyohin.value      = shizaitehaiList[1];	// 商品コード
	        detail.txtHinmei.value      = shizaitehaiList[2];   // 品名
	        detail.txtNisugata.value    = shizaitehaiList[3];   // 荷姿
	        detail.txtOldShizai.value   = shizaitehaiList[4];   // 旧資材コード
	        detail.txtNewShizai.value   = shizaitehaiList[5];   // 新資材コード

	        detail.txtNaiyo.value       = shizaitehaiList[6];	// 内容
	        detail.txtDelivery.value    = shizaitehaiList[7];	// 納1入先
   	}

        break;

    case 2:
        // 選択完了チェックの初期化
        detail.hdnRuiziSelect.value = "false";

        // 類似品検索画面
        var logic = "ruizi"
        wUrl = "../SQ260ShizaiCodeList/SQ260ShizaiCodeList.jsp" + "?" + logic;
        funModalCall(wUrl);

        // 選択完了で画面が終了された場合
        if(detail.hdnRuiziSelect.value === "true"){

            funCheckSetting();

            // 編集フラグをtrueに
            setFg_hensyu();
        }

        break;

    }

    // セッション削除
    funXmlWrite(xmlUSERINFO_I, "MOVEMENT_CONDITION", "", 0);
}

//========================================================================================
// モーダル起動処理処理
// 作成者：H.Shima
// 作成日：2014/09/12
// 概要  ：対象の画面をモーダルダイアログで表示する
//========================================================================================
function funModalCall(wUrl){

    funOpenModalDialog(wUrl , this, "dialogHeight:" + window.screen.height + "px;dialogWidth:" + window.screen.width + "px;status:no;scroll:no");

    return true;
}

//========================================================================================
// 機能遷移処理
// 作成者：H.Shima
// 作成日：2014/10/20
// 概要  ：機能遷移共通関数
//========================================================================================
function funNextLogic(mode){

    var detail = parent.detail.document.frm00;

    var shohin = detail.txtSyohin.value;

    // 入力チェック　商品コード
    if(shohin.length < 1) {
        funErrorMsgBox(E000036);
        return false;
    }

    switch(mode){
    case 1:
    	funUpDInsert(2);
    	funExcelOut(1);
        //funTorokuCheck(1);
        break;

    case 2:
        funUpDInsert(1);
        break;

    case 3:
        funDelete(1);
        break;
    }

    return true;
}

//========================================================================================
// 登録チェック
// 作成者：H.Shima
// 作成日：2014/10/15
// 概要  ：各項目の確認チェックを行う
//========================================================================================
function funTorokuCheck(mode){

    // 全ての項目がチェックされたか
    if(!funEditCheck()){
        // 警告表示
        funErrorMsgBox(E000037);
        return false;
    }

    // 確認ﾒｯｾｰｼﾞの表示
    if (funConfMsgBox(I000002) != ConBtnYes) {
        return false;
    }

    funToroku(mode);

}

//========================================================================================
// 登録処理
// 作成者：H.Shima
// 作成日：2014/10/03
// 概要  ：登録を行う
//========================================================================================
function funToroku(mode) {
	var messagemode = 1;
    if(mode == 2) {
    	mode = 1;
    	messagemode = 2;
    }


	var header = parent.header.document.frm00; // ﾌｫｰﾑへの参照
    var detail = parent.detail.document.frm00; // ﾌｫｰﾑへの参照
    var XmlId = "RGEN3360";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3360");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3360I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3360O);

    //XMLの初期化
    setTimeout("xmlFGEN3360I.src = '../../model/FGEN3360I.xml';", ConTimer);

    // 引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }


    // 更新処理
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3360, xmlReqAry, xmlResAry,
         mode) == false) {
        return false;
    }

    //再計算フラグをOFFにする
     header.hdnHensyu.value = "false";
     if(messagemode == 1 ) {
    	 // 登録完了メッセージ
    	 funInfoMsgBox(I000005);
     }

    return true;
}

//========================================================================================
// EXCEL出力処理
// 作成者：H.Shima
// 作成日：2014/10/07
// 概要  ：Excel出力を行う
//========================================================================================
function funExcelOut(mode) {
    var header = parent.header.document.frm00; // ﾌｫｰﾑへの参照
    var detail = parent.detail.document.frm00; // ﾌｫｰﾑへの参照
    var XmlId = "RGEN3370";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3370", "FGEN3380");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3370I,xmlFGEN3380I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3370O, xmlFGEN3380O);

    // 全ての項目がチェックされたか
    if(!funEditCheck()){
        // 警告表示
        funErrorMsgBox(E000037);
        return false;
    }

//    // 編集されているか
//    var flgHensyu = header.hdnHensyu.value;
//
//    if(flgHensyu === "true"){
//        // 編集
//        if(funConfMsgBox(I000015) != ConBtnYes) {
//            // 出力を中止
//            return false;
//        } else {
//            // 登録処理
//            if(funToroku(1) == false){
//                return false;
//            }
//        }
//    }else{
//        // Excel出力確認ﾒｯｾｰｼﾞの表示
//        if (funConfMsgBox(I000008) != ConBtnYes) {
//            return false;
//        }
//    }

    // 登録処理
    if (funToroku(2) == true) {

//        // Excel出力確認ﾒｯｾｰｼﾞの表示
//        if (funConfMsgBox(I000008) != ConBtnYes) {
//            return false;
//       }
    } else {
    	return false;
    }

    //XMLの初期化
    setTimeout("xmlFGEN3370I.src = '../../model/FGEN3370I.xml';", ConTimer);
    setTimeout("xmlFGEN3380I.src = '../../model/FGEN3380I.xml';", ConTimer);

    // 引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    // 出力
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3370, xmlReqAry, xmlResAry,
         mode) == false) {
        return false;
    }

    header.strServerConst.value = "DOWNLOAD_FOLDER_TEMP";

    //ﾌｧｲﾙﾊﾟｽの退避
    header.strFilePath.value = funXmlRead(xmlResAry[2], "URLValue", 0);

    // excelFileName
    header.strExcelFilePath.value = funXmlRead(xmlResAry[2], "excelFileName", 0);
    // pdfFileName
    header.strPdfFilePath.value = funXmlRead(xmlResAry[2], "pdfFileName", 0);

    //ﾀﾞｳﾝﾛｰﾄﾞ画面の起動
    funFileDownloadUrlConnect2(ConConectGet, header);

    // メールアドレス１
    header.strMaiAddress1.value = funXmlRead(xmlResAry[2], "mail_address1", 0);
    // メールアドレス２
    header.strMaiAddress2.value = funXmlRead(xmlResAry[2], "mail_address2", 0);

    // メーラー起動
    setTimeout(funMailTo, ConTimer);

  //戻り値の設定
    window.returnValue = "3" + ":::" + detail.cmbOrderCom1.value + ":::" + detail.cmbTargetSizai.value;

    return true;
}

//========================================================================================
// 仮保存ボタン押下
// 作成者：t2nakamura
// 作成日：2016/10/28
// 概要  ：資材手配テーブル未手配に更新、資材手配添付テーブル登録
//========================================================================================
function funUpDInsert(mode) {
 var messagemode = 1;
 if(mode == 2) {
 	mode = 1;
 	messagemode = 2;
 }

 var header = parent.header.document.frm00; // ﾌｫｰﾑへの参照
 var detail = parent.detail.document.frm00; // ﾌｫｰﾑへの参照

 var XmlId = "RGEN3700";
 var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3700");
 var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3700I);
 var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3700O);

 //XMLの初期化
 setTimeout("xmlFGEN3700I.src = '../../model/FGEN3700I.xml';", ConTimer);

 // 引数をXMLﾌｧｲﾙに設定
 if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
     funClearRunMessage();
     return false;
 }


 // 更新処理
 if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3700, xmlReqAry, xmlResAry,
      mode) == false) {
     return false;
 }

 //再計算フラグをOFFにする
  header.hdnHensyu.value = "false";
 if(messagemode == 1 ) {
   // 登録完了メッセージ
   funInfoMsgBox(I000005);
 }
 window.returnValue = "2" + ":::" + detail.cmbOrderCom1.value + ":::" + detail.cmbTargetSizai.value;

 return true;
}

//========================================================================================
// 参考資料出力処理
// 作成者：H.Shima
// 作成日：2014/10/03
// 概要  ：参考資料を表示する
//========================================================================================
function funReference() {

    // 参考資料ウィンドウオープン
    var win = window.open("../SQ220ShiryoRef/SQ220ShiryoRef.jsp","sansyo","menubar=no,resizable=yes");
    // 再表示の為にフォーカスにする
    win.focus();

    return true;
}

//========================================================================================
// 削除処理
// 作成者：H.Shima
// 作成日：2014/10/03
// 概要  ：削除を行う
//========================================================================================
function funDelete(mode) {

    var header = parent.header.document.frm00; // ﾌｫｰﾑへの参照
    var detailDoc = parent.detail.document;
    var detail = detailDoc.frm00;
    var XmlId = "RGEN3320";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3320");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3320I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3320O);

    // 削除確認
    if(!funDeleteCheck(mode)){
        return false;
    }

    // XMLの初期化
    setTimeout("xmlFGEN3320I.src = '../../model/FGEN3320I.xml';", ConTimer);

    // 引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    // 出力
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3320, xmlReqAry, xmlResAry,
         mode) == false) {
        return false;
    }

    // 削除完了メッセージ
    funInfoMsgBox(I000007);

    // フォームの初期化
    detail.reset();
    detail.txtOldShizai.style.visibility                   = 'hidden';
    detailDoc.getElementById('siyohenko').style.visibility = 'hidden';
    detail.txtOldShizaiZaiko.style.visibility              = 'hidden';
//    detail.btnOldShizaiZaikoCheck.style.visibility         = 'hidden';
    detail.txtRakuhan.style.visibility                     = 'hidden';
//    detail.btnRakuhanCheck.style.visibility                = 'hidden';
    detail.cmbOrderCom1.selectedIndex = 0;
    detail.cmbOrderCom1.onchange();
    detail.cmbOrderCom2.selectedIndex = 0;
    detail.cmbOrderCom2.onchange();

    return true;
}


//========================================================================================
// 削除確認
// 作成者：H.Shima
// 作成日：2014/12/4
// 概要  ：手配済みデータが含まれる場合は確認メッセージを表示する
//========================================================================================
function funDeleteCheck(mode){

    var header = parent.header.document.frm00; // ﾌｫｰﾑへの参照
    var detailDoc = parent.detail.document;
    var detail = detailDoc.frm00;
    var XmlId = "RGEN3390";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3390");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3390I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3390O);

    // XMLの初期化
    setTimeout("xmlFGEN3390I.src = '../../model/FGEN3390I.xml';", ConTimer);

    // 引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    // 出力
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3390, xmlReqAry, xmlResAry,
         mode) == false) {
        return false;
    }

    // 手配済み件数取得
    var cnt_zumi = funXmlRead_3(xmlResAry[2], "table", "count_tehaizumi", 0, 0);
    if(0 < cnt_zumi){
        // 手配済みデータの削除確認メッセージの表示
        if (funConfMsgBox(I000017) != ConBtnYes) {
            return false;
        }
    }
    // 確認ﾒｯｾｰｼﾞの表示
    else if (funConfMsgBox(I000004) != ConBtnYes) {
        return false;
    }

    return true;
}


//========================================================================================
// 編集確認色設定処理
// 作成者：H.Shima
// 作成日：2014/10/10
// 概要  ：項目の背景色を水色にする。
//========================================================================================
function funCheckSetting(){
    var detail = parent.detail.document.frm00;

    // 背景色を変更
    detail.txtDesign1.style.backgroundColor        = uncheckColor;
    detail.txtDesign2.style.backgroundColor        = uncheckColor;
    detail.txtDesign3.style.backgroundColor        = uncheckColor;
    detail.txtZaishitsu.style.backgroundColor      = uncheckColor;
    detail.txtBiko.style.backgroundColor           = uncheckColor;
    detail.txtPrintColor.style.backgroundColor     = uncheckColor;
    detail.txtColorNo.style.backgroundColor        = uncheckColor;
    detail.txtChangesDetail.style.backgroundColor  = uncheckColor;
    detail.txtDeliveryTime.style.backgroundColor   = uncheckColor;
    detail.txtQuantity.style.backgroundColor       = uncheckColor;
    detail.txtOldShizaiZaiko.style.backgroundColor = uncheckColor;
    detail.txtRakuhan.style.backgroundColor        = uncheckColor;
}

//========================================================================================
// 編集完了確認処理
// 作成者：H.Shima
// 作成日：2014/10/10
// 概要  ：対象項目の背景色を白色にする。
//========================================================================================
function funCheckComplete(mode){
    var detail = parent.detail.document.frm00;
    var object;

    switch(mode){
    case 1:  // 設計①
        object = detail.txtDesign1;
        break;
    case 2:  // 設計②
        object = detail.txtDesign2;
        break;
    case 3:  // 設計③
        object = detail.txtDesign3;
        break;
    case 4:  // 材質
        object = detail.txtZaishitsu;
        break;
    case 5:  // 備考
        object = detail.txtBiko;
        break;
    case 6:  // 印刷色
        object = detail.txtPrintColor;
        break;
    case 7:  // 色番号
        object = detail.txtColorNo;
        break;
    case 8:  // 変更内容詳細
        object = detail.txtChangesDetail;
        break;
    case 9:  // 納期
        object = detail.txtDeliveryTime;
        break;
    case 10: // 数量
        object = detail.txtQuantity;
        break;
    case 11: // 旧資材在庫
        object = detail.txtOldShizaiZaiko;
        break;
    case 12: // 落版
        object = detail.txtRakuhan;
        break;
    }

    object.style.backgroundColor = checkColor;

}
//========================================================================================
// 全編集完了確認処理
// 作成者：H.Shima
// 作成日：2014/10/10
// 概要  ：対象項目の背景色を白色にする。
//========================================================================================
function funCheckCompleteALL(){
	for(var i = 1 ; i < 13; i++) {
		funCheckComplete(i);
	}
}

//========================================================================================
// チェック済み確認処理
// 作成者：H.Shima
// 作成日：2014/09/09
// 概要  ：チェック済みを判断する
//========================================================================================
function funEditCheck(){
    var detailDoc = parent.detail.document;
    var detailFrm = parent.detail.document.frm00;

    var common     = 0; // 共通
    var newEdition = 1; // 新版
    var revision   = 2; // 改版

    var No = 0;
    var arrObj = new Array();
    arrObj[No++] = new Array("txtDesign1", common);          // 設計①
    arrObj[No++] = new Array("txtDesign2", common);          // 設計②
    arrObj[No++] = new Array("txtDesign3", common);          // 設計③
    arrObj[No++] = new Array("txtZaishitsu", common);        // 材質
    arrObj[No++] = new Array("txtBiko", common);             // 備考
    arrObj[No++] = new Array("txtPrintColor", common);       // 印刷色
    arrObj[No++] = new Array("txtColorNo", common);          // 色番号
    arrObj[No++] = new Array("txtChangesDetail", common);    // 変更内容詳細
    arrObj[No++] = new Array("txtDeliveryTime", common);     // 納期
    arrObj[No++] = new Array("txtQuantity", common);         // 数量
    arrObj[No++] = new Array("txtOldShizaiZaiko", revision); // 旧資材在庫
    arrObj[No++] = new Array("txtRakuhan", revision);        // 落版

    for(var i = 0; i < arrObj.length; i++){
        // 背景色取得
        var bgColor = detailDoc.getElementById(arrObj[i][0]).style.backgroundColor;

        // 未チェック有り
        if(bgColor === uncheckColor){

            // 改版チェック取得
            var valRev = "";
            for(var j = 0; j < detailFrm.revision.length; j++){
                if(detailFrm.revision[j].checked){
                    valRev = detailFrm.revision[j].value;
                    break;
                }
            }

            // 改版にチェックが入っていない かつ 改版時に表示される項目の場合
            if(valRev != revision && revision === arrObj[i][1]){
                // 何もしない
            } else {
                return false;
            }
        }
    }

    return true;
}

//========================================================================================
// 編集フラグ変更
// 作成者：H.Shima
// 作成日：2014/10/10
// 概要  ：編集フラグをonにする
//========================================================================================
function setFg_hensyu(){

    //ﾍｯﾀﾞﾌﾚｰﾑのDocument参照
    var headerFrm = parent.header.document.frm00;

    //再計算フラグをonにする
    headerFrm.hdnHensyu.value = "true";

    return true;

}

//========================================================================================
// 画面終了処理
// 作成者：H.Shima
// 作成日：2014/09/09
// 概要  ：画面を終了する
//========================================================================================
function funEnd() {

    parent.close();

    return true;
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

/**
 * メール送信フォーマットとメールを送信する。
 * @returns
 */
function funMailTo() {
    var detail = parent.detail.document.frm00;
	var header = parent.header.document.frm00; // ﾌｫｰﾑへの参照
	// メール
	var mailAddress = header.strMaiAddress1.value;
	var mail2 = funXmlRead(xmlFGEN3370O, "mail_address2", 0);
	    //START 対象資材取得 Makoto Takada 
    // 対象資材
    var hyouji_taisyosizai_disp = funXmlRead(xmlFGEN3370O, "nm_Taisyosizai", 0);
    //END   対象資材取得 Makoto Takada  


	if (mail2 != "") {
		mailAddress += "," + mail2;
	}

	var subject     = "【資材手配依頼書】";
	//var revision_new = parent.detail.document.getElementById("revision_new").value;
	var revision_new = parent.detail.document.getElementById("revision_new").checked;

	if(revision_new) {
		subject += "新版:";
	} else {
		subject += "改版:";
	}
	var hyoji_hinmei = detail.txtHinmei.value;
	subject +=hyoji_hinmei;
	var hyoji_nisugata = detail.txtNisugata.value;
	subject +=hyoji_nisugata;
	//START 対象資材取得 Makoto Takada 
	subject +=hyouji_taisyosizai_disp;
    //END   対象資材取得 Makoto Takada  
	var hyoji_txtDelivery = detail.txtDelivery.value
	subject += "(" + hyoji_txtDelivery + ")";

	var body        = funXmlRead(xmlFGEN3370O, "nm_hattyusaki1", 0) + "　" + funXmlRead(xmlFGEN3370O, "nm_2nd_hattyusaki1", 0) + "%0d%0a";
		if(funXmlRead(xmlFGEN3370O, "nm_hattyusaki2", 0) != "") {
		    body        +=funXmlRead(xmlFGEN3370O, "nm_hattyusaki2", 0) + "　" + funXmlRead(xmlFGEN3370O, "nm_2nd_hattyusaki2", 0) + "%0d%0a";
		}
		//20170322 tamura delete start
	    //body        += "%0d%0a%0d%0aいつも大変お世話になり誠に有難とうございます。%0d%0a";
	    //20170322 tamura delete end
	    //20170322 tamura add start
	    body          += "%0d%0aいつも大変お世話になり誠に有難うございます。%0d%0a";
	    //20170322 tamura add end  
		body        += "下記のとおり、資材の手配をお願い致します。%0d%0a%0d%0a";
　　　　//20170314 tamura delete start
		//body        += "版下データ：別メールにて添付いたします。%0d%0a";
		//20170314 tamura delete end
		//20170314 tamura add start
		body        += "版下データ：%0d%0a";
		//20170314 tamura add end
		body        += "【内容】：" + detail.txtNaiyo.value + "%0d%0a";
		body        += "【納期】：" + detail.txtDeliveryTime.value  + "%0d%0a%0d%0a";
		body        += hyoji_txtDelivery + "　業務課様%0d%0a%0d%0a";
		body        += "上記のとおり依頼をかけております。%0d%0a";
		body        += "詳細の発注は、工場から直接ご連絡をお願い致します。%0d%0a%0d%0a";
		body        += "※資材コード：%0d%0a%0d%0a";
		body        += "以上、何卒宜しくお願い申し上げます。%0d%0a";
		//20170329 tamura delete start
		//body        += "＋＋＋＋＋＋＋＋＋＋＋＋＋＋＋＋＋＋%0d%0a";
		//body        += "生産本部　原資材調達部　資材課%0d%0a";
		//20170329 tamura delete end
		//20170329 tamura add start
		body        += "＋＋＋＋＋＋＋＋＋＋＋＋＋＋＋＋＋＋%0d%0a";
		body        += "キユーピー株式会社%0d%0a";
        body        += "生産本部　原資材調達部　資材課%0d%0a";
		body        += "＋＋＋＋＋＋＋＋＋＋＋＋＋＋＋＋＋＋%0d%0a";
        //20170329 tamura add end
    var url = "mailto:" + mailAddress + "?subject="+ subject + "&amp;body=" + body  ;
    var windowName = "childWindow";
    var winWidth = "400";
    var winHeight = "300";
    var x = (screen.width - winWidth) / 2;
    var y = (screen.height - winHeight) / 2;
    var options = "width=" + winWidth + ", \
                   height=" + winHeight + ", \
                   left="+ x + ", \
                   top=" + y;
    open(url, windowName, options);
}