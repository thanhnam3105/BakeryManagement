//========================================================================================
// グローバル変数の宣言
// 作成者：Y.Nishigawa
// 作成日：2009/11/05
//========================================================================================

//製品ﾃｰﾌﾞﾙのﾍﾟｰｼﾞ指定
var CurrentPage_Seihin;
//資材ﾃｰﾌﾞﾙのﾍﾟｰｼﾞ指定
var CurrentPage_Shizai;
//指定桁数の指定（製品コード）
var keta_seihin;
//指定桁数の指定（資材コード）
var keta_shizai;


//========================================================================================
// 初期処理
// 作成者：Y.Nishigawa
// 作成日：2009/11/05
// 概要  ：画面の初期処理を行う
//========================================================================================
function funLoad() {
    
    //初期表示
    funShokiHyoji(1);
    
    //製品テーブルヘッダ表示
    funSeihinTableHyoji("", "seihinTable");
    
    //資材テーブルヘッダ表示
    funShizaiTableHyoji("", "shizaiTable");
    
    return true;

}


//========================================================================================
// 初期表示
// 作成者：Y.Nishigawa
// 作成日：2009/11/05
// 概要  ：Servletと通信を行い、初期表示に必要な情報を取得する
//========================================================================================
function funShokiHyoji(mode){
    
    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var frmDa = window.dialogArguments.frm00; //親ﾌｫｰﾑへの参照（明細フレーム）
    var XmlId = "RGEN1010";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN1010","FGEN1020");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN1010I, xmlFGEN1020I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN1010O , xmlFGEN1020I );
    
    //------------------------------------------------------------------------------------
    //                                    資材検索
    //------------------------------------------------------------------------------------
    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }
    
    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN1010, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }
    
    //------------------------------------------------------------------------------------
    //                                  製品検索初期表示
    //------------------------------------------------------------------------------------
    //製品検索-会社コンボボックス生成
    funCreateComboBox(frm.selectSeihinKaisha, xmlResAry[2], 1, 0);
    
    //製品検索-会社コンボボックス選択
    funDefaultIndex(frm.selectSeihinKaisha, 1, frmDa.ddlSeizoKaisya.options[frmDa.ddlSeizoKaisya.selectedIndex].value);
    
    //製品検索-工場コンボボックス生成
    funCreateComboBox(frm.selectSeihinKojo, xmlResAry[3], 2, 1);
    
    //製品検索-工場コンボボックス選択
    funDefaultIndex(frm.selectSeihinKojo, 1, frmDa.ddlSeizoKojo.options[frmDa.ddlSeizoKojo.selectedIndex].value);
    
    
    //------------------------------------------------------------------------------------
    //                                  資材検索初期表示
    //------------------------------------------------------------------------------------
    //資材検索-会社コンボボックス生成
    funCreateComboBox(frm.selectShizaiKaisha, xmlResAry[2], 1, 0);
    
    //資材検索-会社コンボボックス選択
    funDefaultIndex(frm.selectShizaiKaisha, 1, frmDa.ddlSeizoKaisya.options[frmDa.ddlSeizoKaisya.selectedIndex].value);
    
    //資材検索-工場コンボボックス生成
    funCreateComboBox(frm.selectShizaiKojo, xmlResAry[3], 2, 1);
    
    //資材検索-工場コンボボックス選択
    funDefaultIndex(frm.selectShizaiKojo, 1, frmDa.ddlSeizoKojo.options[frmDa.ddlSeizoKojo.selectedIndex].value);
    
    
    //------------------------------------------------------------------------------------
    //                                   桁数の初期設定
    //------------------------------------------------------------------------------------
    //指定桁数の指定（製品コード）
    keta_seihin = frmDa.hdnCdketasu.value;
    //指定桁数の指定（資材コード）
    keta_shizai = frmDa.hdnCdketasu.value;
    
    return true;
}

//========================================================================================
// 工場一覧取得
// 作成者：Y.Nishigawa
// 作成日：2009/11/05
// 引数  ：①getObj ：会社コンボ
//       ：②setObj ：工場コンボ
//       ：③mode   ：1:製品,2:資材
// 概要  ：画面の工場一覧取得処理を行う
//========================================================================================
function funKojoChange(getObj, setObj, mode_keta) {
    
    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN1020";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN1020");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN1020I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN1020O );
    var mode = 1;
    
    //------------------------------------------------------------------------------------
    //                                  工場情報取得
    //------------------------------------------------------------------------------------
    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode, getObj.options[getObj.selectedIndex].value) == false) {
        funClearRunMessage();
        return false;
    }
    
    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN1020, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }
    
    //------------------------------------------------------------------------------------
    //                                  工場情報表示
    //------------------------------------------------------------------------------------
    //工場コンボボックス生成
    funCreateComboBox(setObj , xmlResAry[2] , 2, 1);
    
    
    //------------------------------------------------------------------------------------
    //                                  工場別 桁数設定
    //------------------------------------------------------------------------------------
    //工場コンボループ
    /*for ( var i = 0; i < setObj.length; i++) {
        
        //FGEN1020（工場コンボ取得）の件数取得
        var reccnt = funGetLength_3(xmlResAry[2], "kojyo", 0); //件数取得
        
        //FGEN1020レコードループ
        for( var j = 0; j < reccnt; j++ ){
        
            //工場コード取得
            var cd_kojo = funXmlRead(xmlResAry[2], "cd_kojyo", j);
            
            //リストのVALUE値と工場コードが等しい場合
            if( cd_kojo == setObj.options[i].value ){
            
                //工場別桁数取得
                var cd_keta = funXmlRead(xmlResAry[2], "cd_keta", j);
                
                //VALUE値に「工場CD:::桁数」設定
                setObj.options[i].value = cd_kojo + ":::" + cd_keta;
            }
        }
    }*/
    
    //製品、会社桁数設定
    if( mode_keta == 1 ){
        
        keta_seihin = funXmlRead(xmlResAry[2], "cd_keta", 0);
        
    }
    //資材、会社桁数設定
    else if( mode_keta == 2 ){
        
        keta_shizai = funXmlRead(xmlResAry[2], "cd_keta", 0);
        
    }
    
    return true;
}


//========================================================================================
// 製品検索ボタン押下
// 作成者：Y.Nishigawa
// 作成日：2009/11/05
// 概要  ：製品検索ボタン押下
//========================================================================================
function funSeihinClick() {

    //次ﾍﾟｰｼﾞの設定
    funSetCurrentPage_Seihin(1);

    //指定ﾍﾟｰｼﾞのﾃﾞｰﾀ取得
    funSeihinSearch();
    
    return true;
}


//========================================================================================
// 製品より資材転送ボタン押下
// 作成者：Y.Nishigawa
// 作成日：2009/11/05
// 概要  ：製品より資材転送ボタン押下
//========================================================================================
function funSeihin_ShizaiClick() {

    //次ﾍﾟｰｼﾞの設定
    funSetCurrentPage_Shizai(1);
    
    //検索状態の設定
    divKensakuZyotai.innerText = I000011;

    //指定ﾍﾟｰｼﾞのﾃﾞｰﾀ取得
    var ret = funSeihin_ShizaiSearch();


	//データ取得成功時
	if(ret == true){
		// 2010.10.01 Add Arai START 【QP@00412_シサクイック改良　案件№36】
		location.href='#lnkShizaiKensaku';
	    // 2010.10.01 Add Arai END
	}
	
    return true;
}


//========================================================================================
// 資材検索ボタン押下
// 作成者：Y.Nishigawa
// 作成日：2009/11/06
// 概要  ：資材検索ボタン押下
//========================================================================================
function funShizaiClick() {

    //次ﾍﾟｰｼﾞの設定
    funSetCurrentPage_Shizai(1);
    
    //検索状態の設定
    divKensakuZyotai.innerText = I000012;

    //指定ﾍﾟｰｼﾞのﾃﾞｰﾀ取得
    funShizaiSearch();
    
    return true;
}


//========================================================================================
// 資材選択ボタン押下
// 作成者：Y.Nishigawa
// 作成日：2009/11/06
// 概要  ：資材選択ボタン押下
//========================================================================================
function funShizaiSentakuClick() {
    
    //資材一時保管用の表へ追記
    var ret = funSelectShizai();
    
    if(ret == true){
    	// 2010.10.01 Add Arai START 【QP@00412_シサクイック改良　案件№36】
		location.href='#lnkShizaiSentaku';
	    // 2010.10.01 Add Arai END
    }
    
    return true;
}


//========================================================================================
// 製品検索
// 作成者：Y.Nishigawa
// 作成日：2009/11/05
// 概要  ：製品検索情報の取得、更新を行う
//========================================================================================
function funSeihinSearch() {
    
    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN1030";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN1030");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN1030I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN1030O );
    var mode = 1;
    var PageCnt;
    var RecCnt;
    var ListMaxRow;
    
    //------------------------------------------------------------------------------------
    //                                    製品検索
    //------------------------------------------------------------------------------------
    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }
    
    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN1030, xmlReqAry, xmlResAry, mode) == false) {
    	funSeihinClear();
        return false;
    }
    
    //------------------------------------------------------------------------------------
    //                                   製品情報表示
    //------------------------------------------------------------------------------------
    //結果判定
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "false") {
    	alert(funXmlRead(xmlResAry[0], "flg_return", 0));
        //ｴﾗｰ発生時はﾒｯｾｰｼﾞを表示
        dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
        funInfoMsgBox(dspMsg);
        return false;
    	
    }
	
    //製品ﾃｰﾌﾞﾙ表示
    funSeihinTableHyoji(xmlResAry[2], "seihinTable");
	
    //ﾃﾞｰﾀ件数、ﾍﾟｰｼﾞﾘﾝｸの設定
    spnRecInfo.style.display = "block";
    RecCnt = funXmlRead(xmlResAry[2], "max_cnt", 0);
    spnRecCnt.innerText = RecCnt;
    ListMaxRow = funXmlRead(xmlResAry[2], "disp_cnt", 0);
    spnRowMax.innerText = ListMaxRow;
    PageCnt = Math.ceil(RecCnt / ListMaxRow);
    funCreatePageLink_Seihin(funGetCurrentPage_Seihin(), PageCnt, "divPage", "");
    spnCurPage.innerText = funGetCurrentPage_Seihin() + "／" + PageCnt + "ページ";
    
    return true;
}


//========================================================================================
// 製品より資材転送
// 作成者：Y.Nishigawa
// 作成日：2009/11/05
// 概要  ：製品より資材転送
//========================================================================================
function funSeihin_ShizaiSearch() {

    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN1040";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN1035");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN1035I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN1035O );
    var mode = 1;
    var PageCnt;
    var RecCnt;
    var ListMaxRow;
    
    //------------------------------------------------------------------------------------
    //                                 製品より資材転送
    //------------------------------------------------------------------------------------
    //XMLの初期化
    setTimeout("xmlFGEN1035I.src = '../../model/FGEN1035I.xml';", ConTimer);
    
    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }
    
    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN1040, xmlReqAry, xmlResAry, mode) == false) {
    	funShizaiClear();
        return false;
    }
    
    //------------------------------------------------------------------------------------
    //                              製品より資材転送情報表示
    //------------------------------------------------------------------------------------
    //製品ﾃｰﾌﾞﾙ表示
    funShizaiTableHyoji(xmlResAry[2], "shizaiTable");
    //ﾃﾞｰﾀ件数、ﾍﾟｰｼﾞﾘﾝｸの設定
    spnRecInfo2.style.display = "block";
    RecCnt = funXmlRead(xmlResAry[2], "max_cnt", 0);
    spnRecCnt2.innerText = RecCnt;
    ListMaxRow = funXmlRead(xmlResAry[2], "disp_cnt", 0);
    spnRowMax2.innerText = ListMaxRow;
    PageCnt = Math.ceil(RecCnt / ListMaxRow);
    funCreatePageLink_Shizai(funGetCurrentPage_Shizai(), PageCnt, "divPage2", "");
    spnCurPage2.innerText = funGetCurrentPage_Shizai() + "／" + PageCnt + "ページ";
    
    return true;
}


//========================================================================================
// 資材検索
// 作成者：Y.Nishigawa
// 作成日：2009/11/06
// 概要  ：資材検索
//========================================================================================
function funShizaiSearch() {

    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN1050";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN1040");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN1040I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN1040O );
    var mode = 1;
    var PageCnt;
    var RecCnt;
    var ListMaxRow;
    
    //------------------------------------------------------------------------------------
    //                                 製品より資材転送
    //------------------------------------------------------------------------------------
    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }
    
    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN1050, xmlReqAry, xmlResAry, mode) == false) {
    	funShizaiClear();
        return false;
    }
    
    //------------------------------------------------------------------------------------
    //                              製品より資材転送情報表示
    //------------------------------------------------------------------------------------
    //製品ﾃｰﾌﾞﾙ表示
    funShizaiTableHyoji(xmlResAry[2], "shizaiTable");
    
    //ﾃﾞｰﾀ件数、ﾍﾟｰｼﾞﾘﾝｸの設定
    spnRecInfo2.style.display = "block";
    RecCnt = funXmlRead(xmlResAry[2], "max_cnt", 0);
    spnRecCnt2.innerText = RecCnt;
    ListMaxRow = funXmlRead(xmlResAry[2], "disp_cnt", 0);
    spnRowMax2.innerText = ListMaxRow;
    PageCnt = Math.ceil(RecCnt / ListMaxRow);
    funCreatePageLink_Shizai(funGetCurrentPage_Shizai(), PageCnt, "divPage2", "");
    spnCurPage2.innerText = funGetCurrentPage_Shizai() + "／" + PageCnt + "ページ";
    
    return true;
}


//========================================================================================
// 資材選択
// 作成者：Y.Nishigawa
// 作成日：2009/11/06
// 概要  ：資材選択
//========================================================================================
function funSelectShizai() {

    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN1060";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN1045");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN1045I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN1045O );
    var mode = 1;
    
    //------------------------------------------------------------------------------------
    //                                    資材転送
    //------------------------------------------------------------------------------------
    //XMLの初期化
    setTimeout("xmlFGEN1045I.src = '../../model/FGEN1045I.xml';", ConTimer);
    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }
    
    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN1060, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }
    
    //------------------------------------------------------------------------------------
    //                                  資材表へ追記
    //------------------------------------------------------------------------------------
    //FGEN1045（資材候補）の件数取得
    var reccnt = funGetLength_3(xmlResAry[2], "shizai", 0); //件数取得
    //候補表へ追記
    for( var i = 0; i < reccnt; i++ ){

        funAddShizaiKoho(xmlResAry[2],i);

    }
    
    return true;
}


//========================================================================================
// 製品テーブル情報表示
// 作成者：Y.Nishigawa
// 作成日：2009/11/05
// 引数  ：①xmlUser ：更新情報格納XML名
//       ：②ObjectId：設定オブジェクトID
// 戻り値：なし
// 概要  ：製品テーブル情報表示
//========================================================================================
function funSeihinTableHyoji(xmlData, ObjectId) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var obj;              //設定先オブジェクト
    var tablekihonNm;     //読み込みテーブル名
    var tableSeihinNm;    //読み込みテーブル名
    var OutputHtml;       //出力HTML
    var max_cnt;          //総件数
    var no_page;          //ﾍﾟｰｼﾞ番号
    var max_page;         //総ﾍﾟｰｼﾞ数
    var disp_cnt;         //表示件数
    var cd_kaisya;        //会社CD
    var cd_kojyyo;        //工場CD
    var kigo_kojyo;       //工場記号
    var cd_seihin;        //製品CD
    var nm_seihin;        //製品名
    var i;                //ループカウント
    
    //HTML出力オブジェクト設定
    obj = document.getElementById(ObjectId);
    OutputHtml = "";
    
    //テーブル名設定
    tablekihonNm = "kihon";
    tableSeihinNm = "seihin";
    
    //基本情報取得
    max_cnt = funXmlRead_3(xmlData, tablekihonNm, "max_cnt", 0, 0);
    no_page = funXmlRead_3(xmlData, tablekihonNm, "no_page", 0, 0);
    max_page = funXmlRead_3(xmlData, tablekihonNm, "max_page", 0, 0);
    disp_cnt = funXmlRead_3(xmlData, tablekihonNm, "disp_cnt", 0, 0);
    
    //テーブル表示
    OutputHtml += "<table id=\"dataTable\" name=\"dataTable\" cellspacing=\"0\" width=\"1000px\">";
    OutputHtml += "<colgroup>";
    OutputHtml += "<col style=\"width:50px;\" />";
    OutputHtml += "<col style=\"width:50px;\"/>";
    OutputHtml += "<col style=\"width:100px;\"/>";
    OutputHtml += "<col style=\"width:800px;\"/>";
    OutputHtml += "</colgroup>";
    OutputHtml += "<thead class=\"rowtitle\">";
    OutputHtml += "<tr style=\"top:expression(offsetParent.scrollTop);position:relative;\">";
    OutputHtml += "<th class=\"columntitle\">選択</th>";
    OutputHtml += "<th class=\"columntitle\">工場</th>";
    OutputHtml += "<th class=\"columntitle\">製品コード</th>";
    OutputHtml += "<th class=\"columntitle\">製品名</th>";
    OutputHtml += "</tr>";
    OutputHtml += "</thead>";
    OutputHtml += "<tbody>";
    OutputHtml += "<table class=\"detail\" id=\"tblListSeihin\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" datasrc=\"\" datafld=\"\" style=\"width:1000px;\">";
    
    //テーブル明細
    for( i = 0; i < disp_cnt; i++ ){
        
        //XMLデータ取得
        cd_kaisya = funXmlRead_3(xmlData, tableSeihinNm, "cd_kaisya", 0, i);
        cd_kojyyo = funXmlRead_3(xmlData, tableSeihinNm, "cd_kojyyo", 0, i);
        kigo_kojyo = funKuhakuChg(funXmlRead_3(xmlData, tableSeihinNm, "kigo_kojyo", 0, i));
        cd_seihin = funKuhakuChg(funXmlRead_3(xmlData, tableSeihinNm, "cd_seihin", 0, i));
        nm_seihin = funKuhakuChg(funXmlRead_3(xmlData, tableSeihinNm, "nm_seihin", 0, i));
        
        //ループアウト
        if(cd_kaisya == ""){
            
            i += (disp_cnt + 1);
            
        }else{
            
            //明細生成
            OutputHtml += "    <tr class=\"disprow\">";
            OutputHtml += "        <td class=\"column\" width=\"50px\" align=\"center\">";
            OutputHtml += "            <input type=\"checkbox\" name=\"chkSeihin\" tabindex=\"6\" />";
            OutputHtml += "        </td>";
            OutputHtml += "        <td class=\"column\" width=\"50px\">";
            OutputHtml += "            <input type=\"text\" name=\"txtSeihin_Kigou\" id=\"txtSeihin_Kigou\" style=\"text-align:center\" class=\"table_text_view\" readonly value=\"" + kigo_kojyo + "\" tabindex=\"-1\" />";
            OutputHtml += "            <input type=\"hidden\" name=\"hdnSeihin_kasihaCd\" id=\"hdnSeihin_kasihaCd\" value=\"" + cd_kaisya + "\" />";
            OutputHtml += "            <input type=\"hidden\" name=\"hdnSeihin_kojoCd\" id=\"hdnSeihin_kojoCd\" value=\"" + cd_kojyyo + "\" />";
            OutputHtml += "        </td>";
            OutputHtml += "        <td class=\"column\" width=\"100px\">";
            OutputHtml += "            <input type=\"text\" name=\"txtSeihin_seihinCd\" style=\"text-align:center\" id=\"txtSeihin_seihinCd\" class=\"table_text_view\" readonly value=\"" + cd_seihin + "\" tabindex=\"-1\" />";
            OutputHtml += "        </td>";
            OutputHtml += "        <td class=\"column\" width=\"784px\">";
            OutputHtml += "            <input type=\"text\" name=\"txtSeihin_seihinNm\" id=\"txtSeihin_seihinNm\" class=\"table_text_view\" readonly value=\"" + nm_seihin + "\" tabindex=\"-1\" />";
            OutputHtml += "        </td>";
            OutputHtml += "    </tr>";
            
        }
        
    }
    
    OutputHtml += "</table>";
    OutputHtml += "</tbody>";
    OutputHtml += "</table>";
    
    //HTMLを出力
    obj.innerHTML = OutputHtml;
    
    return true;
}


//========================================================================================
// 資材テーブル情報表示
// 作成者：Y.Nishigawa
// 作成日：2009/11/05
// 引数  ：①xmlUser ：更新情報格納XML名
//       ：②ObjectId：設定オブジェクトID
// 戻り値：なし
// 概要  ：資材テーブル情報表示
//========================================================================================
function funShizaiTableHyoji(xmlData, ObjectId) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var obj;              //設定先オブジェクト
    var tablekihonNm;     //読み込みテーブル名
    var tableShizaiNm;    //読み込みテーブル名
    var OutputHtml;       //出力HTML
    var title;            //検索タイトル
    var max_cnt;          //総件数
    var no_page;          //ﾍﾟｰｼﾞ番号
    var max_page;         //総ﾍﾟｰｼﾞ数
    var disp_cnt;         //表示件数
    var cd_kaisya;        //会社CD
    var cd_kojyyo;        //工場CD
    var kigo_kojyo;       //工場記号
    var cd_shizai;        //資材CD
    var nm_shizai;        //資材名
    var tanka;            //単価
    var budomari;         //歩留（％）
    var cd_seihin;        //製品CD
    var ryo;              //使用量
    var i;                //ループカウント
    
    //HTML出力オブジェクト設定
    obj = document.getElementById(ObjectId);
    OutputHtml = "";
    
    //テーブル名設定
    tablekihonNm = "kihon";
    tableShizaiNm = "shizai";
    
    //基本情報取得
    title = funXmlRead_3(xmlData, tablekihonNm, "title", 0, 0);
    max_cnt = funXmlRead_3(xmlData, tablekihonNm, "max_cnt", 0, 0);
    no_page = funXmlRead_3(xmlData, tablekihonNm, "no_page", 0, 0);
    max_page = funXmlRead_3(xmlData, tablekihonNm, "max_page", 0, 0);
    disp_cnt = funXmlRead_3(xmlData, tablekihonNm, "disp_cnt", 0, 0);
    
    //テーブル表示
    OutputHtml += "<table id=\"dataTable\" name=\"dataTable\" cellspacing=\"0\" width=\"1000px\">";
    OutputHtml += "<colgroup>";
    OutputHtml += "<col style=\"width:50px;\"/>";
    OutputHtml += "<col style=\"width:50px;\"/>";
    OutputHtml += "<col style=\"width:100px;\"/>";
    OutputHtml += "<col style=\"width:400px;\"/>";
    OutputHtml += "<col style=\"width:100px;\"/>";
    OutputHtml += "<col style=\"width:100px;\"/>";
    OutputHtml += "<col style=\"width:100px;\"/>";
    OutputHtml += "<col style=\"width:100px;\"/>";
    OutputHtml += "</colgroup>";
    OutputHtml += "<thead class=\"rowtitle\">";
    OutputHtml += "<tr style=\"top:expression(offsetParent.scrollTop);position:relative;\">";
    OutputHtml += "<th class=\"columntitle\">選択</th>";
    OutputHtml += "<th class=\"columntitle\">工場</th>";
    OutputHtml += "<th class=\"columntitle\">資材コード</th>";
    OutputHtml += "<th class=\"columntitle\">資材名</th>";
    OutputHtml += "<th class=\"columntitle\">単価</th>";
    OutputHtml += "<th class=\"columntitle\">歩留(％)</th>";
    OutputHtml += "<th class=\"columntitle\">使用量/ケース</th>";
    OutputHtml += "<th class=\"columntitle\">製品コード</th>";
    OutputHtml += "</tr>";
    OutputHtml += "</thead>";
    OutputHtml += "<tbody>";
    OutputHtml += "<table class=\"detail\" id=\"tblListShizai\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" datasrc=\"\" datafld=\"\" style=\"width:999px;\">";
    
    //テーブル明細
    for( i = 0; i < disp_cnt; i++ ){
        
        //XMLデータ取得
        cd_kaisya = funXmlRead_3(xmlData, tableShizaiNm, "cd_kaisya", 0, i);
        cd_kojyyo = funXmlRead_3(xmlData, tableShizaiNm, "cd_kojyyo", 0, i);
        kigo_kojyo = funXmlRead_3(xmlData, tableShizaiNm, "kigo_kojyo", 0, i);
        cd_shizai = funXmlRead_3(xmlData, tableShizaiNm, "cd_shizai", 0, i);
        nm_shizai = funXmlRead_3(xmlData, tableShizaiNm, "nm_shizai", 0, i);
        tanka = funXmlRead_3(xmlData, tableShizaiNm, "tanka", 0, i);
        budomari = funXmlRead_3(xmlData, tableShizaiNm, "budomari", 0, i);
        cd_seihin = funXmlRead_3(xmlData, tableShizaiNm, "cd_seihin", 0, i);
        ryo = funXmlRead_3(xmlData, tableShizaiNm, "ryo", 0, i);
        
        //ループアウト
        if(cd_kaisya == ""){
            
            i += (disp_cnt + 1);
            
        }else{
            
            //明細生成
            OutputHtml += "    <tr class=\"disprow\">";
            OutputHtml += "        <td class=\"column\" width=\"50px\" align=\"center\">";
            OutputHtml += "            <input type=\"checkbox\" name=\"chkShizai\" tabindex=\"15\" />";
            OutputHtml += "        </td>";
            OutputHtml += "        <td class=\"column\" width=\"51px\">";
            OutputHtml += "            <input type=\"text\" name=\"txtShizai_Kigou\" id=\"txtShizai_Kigou\" style=\"text-align:center\"  class=\"table_text_view\" readonly value=\"" + kigo_kojyo + "\" tabindex=\"-1\" />";
            OutputHtml += "            <input type=\"hidden\" name=\"hdnShizai_kasihaCd\" id=\"hdnShizai_kasihaCd\" value=\"" + cd_kaisya + "\" />";
            OutputHtml += "            <input type=\"hidden\" name=\"hdnShizai_kojoCd\" id=\"hdnShizai_kojoCd\" value=\"" + cd_kojyyo + "\" />";
            OutputHtml += "        </td>";
            OutputHtml += "        <td class=\"column\" width=\"100px\">";
            OutputHtml += "            <input type=\"text\" name=\"txtShizai_shizaiCd\" id=\"txtShizai_shizaiCd\" style=\"text-align:center\" class=\"table_text_view\" readonly value=\"" + cd_shizai + "\" tabindex=\"-1\" />";
            OutputHtml += "        </td>";
            OutputHtml += "        <td class=\"column\" width=\"400px\">";
            OutputHtml += "            <input type=\"text\" name=\"txtShizai_shizaiNm\" id=\"txtShizai_shizaiNm\" class=\"table_text_view\" readonly value=\"" + nm_shizai + "\" tabindex=\"-1\" />";
            OutputHtml += "        </td>";
            OutputHtml += "        <td class=\"column\" width=\"100px\">";
            OutputHtml += "            <input type=\"text\" name=\"txtShizai_tanka\" id=\"txtShizai_tanka\" style=\"text-align:right\" class=\"table_text_view\" readonly value=\"" + tanka + "\" tabindex=\"-1\" />";
            OutputHtml += "        </td>";
            OutputHtml += "        <td class=\"column\" width=\"100px\">";
            OutputHtml += "            <input type=\"text\" name=\"txtShizai_budomari\" id=\"txtShizai_budomari\" style=\"text-align:right\" class=\"table_text_view\" readonly value=\"" + budomari + "\" tabindex=\"-1\" />";
            OutputHtml += "        </td>";
            OutputHtml += "        <td class=\"column\" width=\"100px\">";
            OutputHtml += "            <input type=\"text\" name=\"txtShizai_ryo\" id=\"txtShizai_ryo\" class=\"table_text_view\" style=\"text-align:right\"  readonly value=\"" + ryo + "\" tabindex=\"-1\" />";
            OutputHtml += "        </td>";
            OutputHtml += "        <td class=\"column\" width=\"100px\">";
            OutputHtml += "            <input type=\"text\" name=\"txtShizai_seihinCd\" id=\"txtShizai_seihinCd\" style=\"text-align:center\" class=\"table_text_view\" readonly value=\"" + cd_seihin + "\" tabindex=\"-1\" />";
            OutputHtml += "        </td>";
            OutputHtml += "    </tr>";
            
        }
        
    }
    
    OutputHtml += "</table>";
    OutputHtml += "</tbody>";
    OutputHtml += "</table>";
    
    //HTMLを出力
    obj.innerHTML = OutputHtml;
    
    return true;
}


//========================================================================================
// 資材候補テーブル行追加処理
// 作成者：Y.Nishigawa
// 作成日：2009/10/28
// 概要  ：資材候補テーブル行追加処理
//========================================================================================
function funAddShizaiKoho(xmlData,index){

    //XMLデータ取得
    var tableShizaiNm = "shizai";
    var cd_kaisya = funXmlRead_3(xmlData, tableShizaiNm, "cd_kaisya", 0, index);
    var cd_kojyyo = funXmlRead_3(xmlData, tableShizaiNm, "cd_kojyyo", 0, index);
    var kigo_kojyo = funXmlRead_3(xmlData, tableShizaiNm, "kigo_kojyo", 0, index);
    var cd_shizai = funXmlRead_3(xmlData, tableShizaiNm, "cd_shizai", 0, index);
    var nm_shizai = funXmlRead_3(xmlData, tableShizaiNm, "nm_shizai", 0, index);
    var tanka = funXmlRead_3(xmlData, tableShizaiNm, "tanka", 0, index);
    var budomari = funXmlRead_3(xmlData, tableShizaiNm, "budomari", 0, index);
    var cd_seihin = funXmlRead_3(xmlData, tableShizaiNm, "cd_seihin", 0, index);
    var ryo = funXmlRead_3(xmlData, tableShizaiNm, "ryo", 0, index);

    //テーブル参照
    var tblShizai = document.getElementById("tblListKoho");

    //行数取得
    var strlength = tblShizai.rows.length;
    
    //TR要素追加
    var row = tblShizai.insertRow(strlength);

    //TD要素追加
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    var cell3 = row.insertCell(2);
    var cell4 = row.insertCell(3);
    var cell5 = row.insertCell(4);
    var cell6 = row.insertCell(5);
    var cell7 = row.insertCell(6);
    var cell8 = row.insertCell(7);

    //選択
    cell1.className = "column";
    cell1.setAttribute("class","column");
    cell1.setAttribute("width","50px");
    cell1.setAttribute("align","center");
    cell1.innerHTML = "<input type=\"checkbox\" name=\"chkShizaiKoho\" tabindex=\"19\" />";

    //工場記号
    cell2.className = "column";
    cell2.setAttribute("class","column");
    cell2.setAttribute("width","51px");
    cell2.setAttribute("align","center");
    cell2.innerHTML = "<input type=\"text\" id=\"txtKoho_KigouKojo\" name=\"txtKoho_KigouKojo\" class=\"table_text_view\" readonly style=\"text-align:center;\" value=\"" + kigo_kojyo + "\" tabindex=\"-1\" />";
    cell2.innerHTML += "<input type=\"hidden\"  id=\"hdnKoho_kaishaCd\" name=\"hdnKoho_kaishaCd\" value=\"" + cd_kaisya + "\" />";
    cell2.innerHTML += "<input type=\"hidden\"  id=\"hdnkoho_kojoCd\"   name=\"hdnkoho_kojoCd\" value=\"" + cd_kojyyo + "\" />";

    //資材コード
    cell3.className = "column";
    cell3.setAttribute("class","column");
    cell3.setAttribute("width","100px");
    cell3.setAttribute("align","center");
    cell3.innerHTML = "<input type=\"text\"id=\"txtKoho_CdShizai\" name=\"txtKoho_CdShizai\" class=\"table_text_view\" readonly style=\"text-align:center\" value=\"" + cd_shizai + "\" tabindex=\"-1\" />";

    //資材名
    cell4.className = "column";
    cell4.setAttribute("class","column");
    cell4.setAttribute("width","400px");
    cell4.setAttribute("align","left");
    cell4.innerHTML = "<input type=\"text\" id=\"txtKoho_NmShizai\" name=\"txtKoho_NmShizai\" class=\"table_text_view\" readonly style=\"ime-mode:active;text-align:left\" value=\"" + nm_shizai + "\" tabindex=\"-1\" />";

    //単価
    cell5.className = "column";
    cell5.setAttribute("class","column");
    cell5.setAttribute("width","100px");
    cell5.setAttribute("align","right");
    cell5.innerHTML = "<input type=\"text\" id=\"txtKoho_TankaShizai\" name=\"txtKoho_TankaShizai\" class=\"table_text_view\" readonly style=\"text-align:right\" value=\"" + tanka + "\" tabindex=\"-1\" />";

    //歩留
    cell6.className = "column";
    cell6.setAttribute("class","column");
    cell6.setAttribute("width","100px");
    cell6.setAttribute("align","right");
    cell6.innerHTML = "<input type=\"text\" id=\"txtKoho_BudomariShizai\" name=\"txtKoho_BudomariShizai\" class=\"table_text_view\" readonly style=\"text-align:right\" value=\"" + budomari + "\" tabindex=\"-1\" />";

    //使用量/ケース
    cell7.className = "column";
    cell7.setAttribute("class","column");
    cell7.setAttribute("width","100px");
    cell7.setAttribute("align","right");
    cell7.innerHTML = "<input type=\"text\" id=\"txtKoho_SiyouShizai\" name=\"txtKoho_SiyouShizai\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\"" + ryo + "\" tabindex=\"-1\" />";
    
    //製品コード
    cell8.className = "column";
    cell8.setAttribute("class","column");
    cell8.setAttribute("width","100px");
    cell8.setAttribute("align","center");
    cell8.innerHTML = "<input type=\"text\" id=\"txtKoho_SeihinCd\" name=\"txtKoho_SeihinCd\" class=\"table_text_view\" readonly style=\"text-align:center;\" value=\"" + cd_seihin + "\" tabindex=\"-1\" />";
    
   return true;
}


//========================================================================================
// 製品テーブルクリア処理
// 作成者：Y.Nishigawa
// 作成日：2009/11/05
// 引数  ：なし
// 戻り値：カレントページ
// 概要  ：選択中のページ番号を返す。
//========================================================================================
function funSeihinClear(){
    
    var frm = document.frm00;
    
    //テーブル参照
    var tblShizai = document.getElementById("tblListSeihin");
    var gyoCount = tblShizai.rows.length;
	
	//テーブルクリア
    for(var i=0; i<gyoCount; i++){
        //行削除
        tblShizai.deleteRow(i);
        i = i - 1;
        gyoCount = gyoCount - 1;
    }
	
	//ﾍﾟｰｼﾞ情報クリア
    spnRecCnt.innerText = "";
    spnRowMax.innerText = "";
    spnCurPage.innerText = "";
	divPage.innerText = "";
    
}


//========================================================================================
// 資材テーブルクリア処理
// 作成者：Y.Nishigawa
// 作成日：2009/11/05
// 引数  ：なし
// 戻り値：カレントページ
// 概要  ：選択中のページ番号を返す。
//========================================================================================
function funShizaiClear(){
    
    var frm = document.frm00;
    
    //テーブル参照
    var tblShizai = document.getElementById("tblListShizai");
    var gyoCount = tblShizai.rows.length;
	
	//テーブルクリア
    for(var i=0; i<gyoCount; i++){
        //行削除
        tblShizai.deleteRow(i);
        i = i - 1;
        gyoCount = gyoCount - 1;
    }
	
	//ﾍﾟｰｼﾞ情報クリア
    spnRecCnt2.innerText = "";
    spnRowMax2.innerText = "";
    spnCurPage2.innerText = "";
	divPage2.innerText = "";
    
}


//========================================================================================
// 候補行削除処理
// 作成者：Y.Nishigawa
// 作成日：2009/11/06
// 概要  ：選択されている候補行を削除する
//========================================================================================
function funDeleteKoho(){
    
    //テーブル参照
    var frm = document.frm00; 
    var tblShizai = document.getElementById("tblListKoho");
    var gyoCount = tblShizai.rows.length;

	if(gyoCount <= 1){
        if(frm.chkShizaiKoho == null){
        	//リストに行が存在しない場合
        	return false;
        }else{
        	if(frm.chkShizaiKoho.checked){
            	//選択されている行の場合行削除
            	tblShizai.deleteRow(0);
            	}else{
            	//リストに行が存在するがチェックがされていない場合
            	return false;
        	}
        }
    }else{
    //資材行の削除
	    for( var i=0; i < gyoCount; i++ ){
	    	//選択されている行の場合
	        if(frm.chkShizaiKoho[i].checked){
	            //行削除
	            tblShizai.deleteRow(i);
	            i = i - 1;
	            gyoCount = gyoCount - 1;
	        }
	    }
    }
    return true;
}


//========================================================================================
// 候補確定処理
// 作成者：Y.Nishigawa
// 作成日：2009/11/06
// 概要  ：候補確定処理
//========================================================================================
function funKakuteiKoho(){
    
    var frmDa = window.dialogArguments.frm00;    //親ﾌｫｰﾑへの参照（明細フレーム）
    var docDa = window.dialogArguments.document; //親documentへの参照（明細フレーム）
    var frm = document.frm00;
    
    //原価試算 テーブル参照
    var tblShizai = docDa.getElementById("tblList4");
    var gyoCount = tblShizai.rows.length;
    var lastIndex = -1;
    
    //類似品検索 テーブル参照
    var tblShizaiR = document.getElementById("tblListKoho");
    var gyoCountR = tblShizaiR.rows.length;
    
    //入力されている最終行の検索
    for(var i=0; i<gyoCount; i++){
        
        //何も入力されていない場合
        if( frmDa.txtCdShizai[i].value == ""
            && frmDa.txtNmShizai[i].value == ""
            && frmDa.txtTankaShizai[i].value == ""
            && frmDa.txtBudomariShizai[i].value == ""
            && frmDa.txtSiyouShizai[i].value == "" ){
            
            //何もしない
            
            
        //何か入力されている場合
        }else{
           
           lastIndex = i;
           
        }
    }
    
    //追加開始行の設定
    lastIndex++;
    
    //列追加
    for( var i = 0; i < gyoCountR; i++ ){

        //原価試算 資材行追加処理
        funAddShizai(lastIndex);
        
        if(gyoCountR <= 1){
            
            //工場記号
            frmDa.txtKigouKojo[lastIndex].value = frm.txtKoho_KigouKojo.value;

            //会社コード
            frmDa.hdnKaisha_Shizai[lastIndex].value = frm.hdnKoho_kaishaCd.value;

            //工場コード
            frmDa.hdnKojo_Shizai[lastIndex].value = frm.hdnkoho_kojoCd.value;

            //資材コード
            frmDa.txtCdShizai[lastIndex].value = frm.txtKoho_CdShizai.value;

            //資材名
            frmDa.txtNmShizai[lastIndex].value = frm.txtKoho_NmShizai.value;

            //単価
            frmDa.txtTankaShizai[lastIndex].value = frm.txtKoho_TankaShizai.value;

            //歩留
            frmDa.txtBudomariShizai[lastIndex].value = frm.txtKoho_BudomariShizai.value;

            //使用量/ケース
            frmDa.txtSiyouShizai[lastIndex].value = frm.txtKoho_SiyouShizai.value;
            
        }else{
            
            //工場記号
            frmDa.txtKigouKojo[lastIndex].value = frm.txtKoho_KigouKojo[i].value;

            //会社コード
            frmDa.hdnKaisha_Shizai[lastIndex].value = frm.hdnKoho_kaishaCd[i].value;

            //工場コード
            frmDa.hdnKojo_Shizai[lastIndex].value = frm.hdnkoho_kojoCd[i].value;

            //資材コード
            frmDa.txtCdShizai[lastIndex].value = frm.txtKoho_CdShizai[i].value;

            //資材名
            frmDa.txtNmShizai[lastIndex].value = frm.txtKoho_NmShizai[i].value;

            //単価
            frmDa.txtTankaShizai[lastIndex].value = frm.txtKoho_TankaShizai[i].value;

            //歩留
            frmDa.txtBudomariShizai[lastIndex].value = frm.txtKoho_BudomariShizai[i].value;

            //使用量/ケース
            frmDa.txtSiyouShizai[lastIndex].value = frm.txtKoho_SiyouShizai[i].value;
        }
        

        //原価試算 挿入行カウント
        lastIndex++;

    }
    
    //画面終了
    funEnd();
    
    return true;
}


//========================================================================================
// 資材行追加処理
// 作成者：Y.Nishigawa
// 作成日：2009/10/28
// 概要  ：最終行を追加する
//========================================================================================
function funAddShizai(lastIndex){

    //明細ﾌﾚｰﾑのDocument参照
    var detailDoc = window.dialogArguments.document;
    var frm = window.dialogArguments.frm00;
    
    // 工場　担当会社
    var cd_kaisha = frm.ddlSeizoKaisya.options[frm.ddlSeizoKaisya.selectedIndex].value;
    // 工場　担当工場
    var cd_kojo = frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value;

    //テーブル参照
    var tblShizai = detailDoc.getElementById("tblList4");

    //行数取得
    var strlength = lastIndex;
    
    //TR要素追加
    var row = tblShizai.insertRow(strlength);

    //TD要素追加
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    var cell3 = row.insertCell(2);
    var cell4 = row.insertCell(3);
    var cell5 = row.insertCell(4);
    var cell6 = row.insertCell(5);
    var cell7 = row.insertCell(6);
    var cell8 = row.insertCell(7);

    //選択
    cell1.style.backgroundColor = "#ffff88";
    cell1.className = "column";
    cell1.setAttribute("class","column");
    cell1.setAttribute("width","48px");
    cell1.setAttribute("align","center");
    cell1.innerHTML = "<input type=\"checkbox\" name=\"chkShizaiGyo\" tabindex=\"27\" />&nbsp;";

    //工場記号
    cell2.className = "column";
    cell2.setAttribute("class","column");
    cell2.setAttribute("width","50px");
    cell2.setAttribute("align","center");
    cell2.innerHTML = "<input type=\"text\" id=\"txtKigouKojo\" name=\"txtKigouKojo\" class=\"table_text_view\" readonly style=\"text-align:center;\" value=\"\" tabindex=\"-1\" />";
    cell2.innerHTML += "<input type=\"hidden\"  id=\"hdnKaisha_Shizai\" name=\"hdnKaisha_Shizai\" value=\"" + cd_kaisha + "\" />";
    cell2.innerHTML += "<input type=\"hidden\"  id=\"hdnKojo_Shizai\"   name=\"hdnKojo_Shizai\" value=\"" + cd_kojo + "\" />";
    cell2.innerHTML += "<input type=\"hidden\"  id=\"hdnId_toroku\"   name=\"hdnId_toroku\" value=\"\" />";
    cell2.innerHTML += "<input type=\"hidden\"  id=\"hdnDt_toroku\"   name=\"hdnDt_toroku\" value=\"\" />";

    //資材コード
    cell3.style.backgroundColor = "#ffff88";
    cell3.className = "column";
    cell3.setAttribute("class","column");
    cell3.setAttribute("width","100px");
    cell3.setAttribute("align","center");
    //【QP@00342】資材テーブル修正
    //cell3.innerHTML = "<input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funInsertCdZero(this);funShizaiSearch()\" id=\"txtCdShizai\" name=\"txtCdShizai\" class=\"table_text_disb\" style=\"text-align:center\" value=\"\" tabindex=\"27\" />";
    cell3.innerHTML = "<input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funInsertCdZero(this);funChangeSelectRowColor3(this);funShizaiSearch();\" id=\"txtCdShizai\" name=\"txtCdShizai\" class=\"table_text_disb\" style=\"text-align:center\" value=\"\" tabindex=\"27\" />";
    
    //資材名
    cell4.style.backgroundColor = "#ffff88";
    cell4.className = "column";
    cell4.setAttribute("class","column");
    cell4.setAttribute("width","353px");
    cell4.setAttribute("align","left");
    cell4.innerHTML = "<input type=\"text\" id=\"txtNmShizai\" name=\"txtNmShizai\" class=\"table_text_disb\"  style=\"ime-mode:active;text-align:left\" value=\"\" tabindex=\"27\" />";

    //単価
    cell5.style.backgroundColor = "#ffff88";
    cell5.className = "column";
    cell5.setAttribute("class","column");
    cell5.setAttribute("width","100px");
    cell5.setAttribute("align","right");
    cell5.innerHTML = "<input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtTankaShizai\" name=\"txtTankaShizai\" class=\"table_text_disb\" style=\"text-align:right\" value=\"\" tabindex=\"27\" />";

    //歩留
    cell6.style.backgroundColor = "#ffff88";
    cell6.className = "column";
    cell6.setAttribute("class","column");
    cell6.setAttribute("width","80px");
    cell6.setAttribute("align","right");
    cell6.innerHTML = "<input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtBudomariShizai\" name=\"txtBudomariShizai\" class=\"table_text_disb\" style=\"text-align:right\" value=\"\" tabindex=\"27\" />";

    //使用量/ケース
    cell7.style.backgroundColor = "#ffff88";
    cell7.className = "column";
    cell7.setAttribute("class","column");
    cell7.setAttribute("width","120px");
    cell7.setAttribute("align","right");
    cell7.innerHTML = "<input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funShosuUp(this,6);setKanma(this)\" id=\"txtSiyouShizai\" name=\"txtSiyouShizai\" class=\"table_text_disb\" style=\"text-align:right;\" value=\"\" tabindex=\"27\" />";

    //金額
    cell8.className = "column";
    cell8.setAttribute("class","column");
    cell8.setAttribute("width","100px");
    cell8.setAttribute("align","right");
    cell8.innerHTML = "&nbsp;";
    
    return true;
}

//========================================================================================
// XMLファイルに書き込み
// 作成者：Y.Nishigawa
// 作成日：2009/11/05
// 引数  ：①XmlId  ：XMLID
//       ：②reqAry ：機能ID別送信XML(配列)
//       ：③Mode   ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
//       ：④val    ：設定値
// 戻り値：なし
// 概要  ：機能IDごとのXMLファイルに引数を設定する
//========================================================================================
function funReadyOutput(XmlId, reqAry, mode, val) {
    
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var frmDa = window.dialogArguments.frm00; //親ﾌｫｰﾑへの参照（明細フレーム）
    var i;
    
    for (i = 0; i < reqAry.length; i++) {
        
        //画面初期表示（共通情報）
        if (XmlId.toString() == "RGEN1010") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN1010
                    funXmlWrite(reqAry[i], "cd_kaisya", frmDa.ddlSeizoKaisya.options[frmDa.ddlSeizoKaisya.selectedIndex].value, 0);
                    break;
                case 2:    //FGEN1020
                    funXmlWrite(reqAry[i], "cd_kaisya", frmDa.ddlSeizoKaisya.options[frmDa.ddlSeizoKaisya.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_kojyo", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", RuizihinKensakuId, 0);
                    break;
            }
        }
        //工場検索（会社変更時）
        else if (XmlId.toString() == "RGEN1020"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN1020
                    funXmlWrite(reqAry[i], "cd_kaisya", val, 0);
                    funXmlWrite(reqAry[i], "cd_kojyo", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", RuizihinKensakuId, 0);
                    break;
            }
        }
        //製品検索
        else if (XmlId.toString() == "RGEN1030"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN1030
                    funXmlWrite(reqAry[i], "cd_kaisya", frm.selectSeihinKaisha.options[frm.selectSeihinKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_kojyo", frm.selectSeihinKojo.options[frm.selectSeihinKojo.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_seihin", frm.inputSeihinCd.value, 0);
                    funXmlWrite(reqAry[i], "nm_seihin", frm.inputSeihinNm.value, 0);
                    funXmlWrite(reqAry[i], "no_page", funGetCurrentPage_Seihin(), 0);
                    break;
            }
        }
        //製品より資材転送
        else if (XmlId.toString() == "RGEN1040"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN1035
                    // 製品テーブルオブジェクト取得
                    var tblSeihin = document.getElementById("tblListSeihin");
                    // 資材表の行数取得
                    recCnt = tblSeihin.rows.length;
                    // XMLレコード挿入カウント
                    var recInsert = 0;
                    // XMLへ書き込み
                    for(var j=0; j<recCnt; j++){
                        // 選択されている行の場合
                        if(recCnt <= 1){
                            
                            if(frm.chkSeihin.checked){
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_kaisya", frm.hdnSeihin_kasihaCd.value, recInsert); 
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_kojyo", frm.hdnSeihin_kojoCd.value, recInsert); 
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_seihin", frm.txtSeihin_seihinCd.value, recInsert); 
                                funXmlWrite_Tbl(reqAry[i], "table", "no_page", funGetCurrentPage_Shizai(), recInsert); 
                            }
                        
                        }else{
                            
                            if(frm.chkSeihin[j].checked){
                                if (recInsert != 0) {
                                    funAddRecNode_Tbl(reqAry[i], "FGEN1035", "table");
                                }
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_kaisya", frm.hdnSeihin_kasihaCd[j].value, recInsert); 
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_kojyo", frm.hdnSeihin_kojoCd[j].value, recInsert); 
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_seihin", frm.txtSeihin_seihinCd[j].value, recInsert); 
                                funXmlWrite_Tbl(reqAry[i], "table", "no_page", funGetCurrentPage_Shizai(), recInsert); 
                            	// XMLレコード挿入カウント+1
                                recInsert++;
                            }
                        }
                        
                    }
                    break;
            }
        }
        //製品検索
        else if (XmlId.toString() == "RGEN1050"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN1040
                    funXmlWrite(reqAry[i], "cd_kaisya", frm.selectShizaiKaisha.options[frm.selectShizaiKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_kojyo", frm.selectShizaiKojo.options[frm.selectShizaiKojo.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_shizai", frm.inputShizaiCd.value, 0);
                    funXmlWrite(reqAry[i], "nm_shizai", frm.inputShizaiNm.value, 0);
                    funXmlWrite(reqAry[i], "no_page", funGetCurrentPage_Shizai(), 0);
                    break;
            }
        }
        //資材候補転送
        else if (XmlId.toString() == "RGEN1060"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN1045
                    var tblShizai;
                    var recCnt = 0;
                    
                    //資材テーブルオブジェクトが存在する場合
                    if(document.getElementById("tblListShizai")){
                        
                        // 資材テーブルオブジェクト取得
                        tblShizai = document.getElementById("tblListShizai");
                        // 資材表の行数取得
                        recCnt = tblShizai.rows.length;
                        
                    }
                    // XMLレコード挿入カウント
                    var recInsert = 0;
                    // XMLへ書き込み
                    for(var j=0; j<recCnt; j++){
                        // 選択されている行の場合
                        if(recCnt <= 1){
                            if(frm.chkShizai.checked){
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_kaisya", frm.hdnShizai_kasihaCd.value, recInsert);
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_kojyo", frm.hdnShizai_kojoCd.value, recInsert);
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_shizai", frm.txtShizai_shizaiCd.value, recInsert);
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_seihin", frm.txtShizai_seihinCd.value, recInsert);
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_tanto_kaisya", frmDa.ddlSeizoKaisya.options[frmDa.ddlSeizoKaisya.selectedIndex].value, recInsert);
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_tanto_kojyo", frmDa.ddlSeizoKojo.options[frmDa.ddlSeizoKojo.selectedIndex].value, recInsert);
                            }
                        }else{
                            if(frm.chkShizai[j].checked){
                                if (recInsert != 0) {
                                    funAddRecNode_Tbl(reqAry[i], "FGEN1045", "table");
                                }
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_kaisya", frm.hdnShizai_kasihaCd[j].value, recInsert);
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_kojyo", frm.hdnShizai_kojoCd[j].value, recInsert);
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_shizai", frm.txtShizai_shizaiCd[j].value, recInsert);
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_seihin", frm.txtShizai_seihinCd[j].value, recInsert);
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_tanto_kaisya", frmDa.ddlSeizoKaisya.options[frmDa.ddlSeizoKaisya.selectedIndex].value, recInsert);
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_tanto_kojyo", frmDa.ddlSeizoKojo.options[frmDa.ddlSeizoKojo.selectedIndex].value, recInsert);
                            	// XMLレコード挿入カウント+1
                                recInsert++;
                            }
                        }
                        
                    }
                    break;
            }
        }
    }
    
    return true;

}

//========================================================================================
// コンボボックス作成処理
// 作成者：Y.Nishigawa
// 作成日：2009/10/21
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②xmlData  ：設定XML
//       ：③mode     ：モード
//       ：④karaFg   ：空白選択の設定（0：空白無し、1：空白有り）
// 概要  ：コンボボックスを作成する
//========================================================================================
function funCreateComboBox(obj, xmlData, mode, karaFg) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var reccnt;
    var objNewOption;
    var atbName;
    var atbCd;
    var i;
    var tableNm;

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
        
        //会社コンボボックス
        case 1:
            atbName = "nm_kaisya"; //会社名
            atbCd = "cd_kaisya";   //会社CD
            tableNm = "kaisya";    //テーブル行
            reccnt = funGetLength_3(xmlData, tableNm, 0); //件数取得
            break;
        
        //工場コンボボックス
        case 2:
            atbName = "nm_kojyo"; //工場名
            atbCd = "cd_kojyo";  //工場CD
            tableNm = "kojyo";   //テーブル行
            reccnt = funGetLength_3(xmlData, tableNm, 0); //件数取得
            break;
        
    }
    
    for (i = 0; i < reccnt; i++) {
        if (funXmlRead_3(xmlData, tableNm, atbCd, 0, i) != "" && funXmlRead_3(xmlData, tableNm, atbName, 0, i) != "") {
            objNewOption = document.createElement("option");
            obj.options.add(objNewOption);
            objNewOption.innerText = funXmlRead_3(xmlData, tableNm, atbName, 0, i);
            objNewOption.value = funXmlRead_3(xmlData, tableNm, atbCd, 0, i);
        }
    }
    
    //先頭行の削除
    if(karaFg == 0){
        obj.options[0] = null;
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽのﾃﾞﾌｫﾙﾄ表示
    obj.selectedIndex = 0;

    return true;
}

//========================================================================================
// デフォルト値選択処理
// 作成者：Y.nishigawa
// 作成日：2009/10/22
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②mode     ：モード
//       ：③val      ：設定値
// 概要  ：コンボボックスのデフォルト値を選択する
//========================================================================================
function funDefaultIndex(obj, mode, val) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var selIndex;
    var i;

    //XMLとコンボボックスVALUE値を判定
    for (i = 0; i < obj.length; i++) {
        switch (mode) {
            case 1:    //会社、工場
                if (obj.options[i].value == val) {
                    selIndex = i;
                }
                break;
        }
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽのｲﾝﾃﾞｯｸｽ指定
    obj.selectedIndex = selIndex;

    return true;
}

//========================================================================================
// 空白置換関数（"" → "&nbsp;"）
// 作成者：Y.Nishigawa
// 作成日：2009/10/23
// 引数  ：なし
// 概要  ："" → "&nbsp;" へ置換する
//========================================================================================
function funKuhakuChg(strChk) {
    
    //空白の場合
    if(strChk == ""){
        return "&nbsp;";
    }
    //空白でない場合
    else{
        return strChk;
    }

}

//========================================================================================
// 製品一括選択
// 作成者：Y.Nishigawa
// 作成日：2009/11/06
// 引数  ：なし
// 概要  ：製品一括選択
//========================================================================================
function funSeihinCheck() {
    
    //明細ﾌﾚｰﾑのﾌｫｰﾑ参照
    var frm = document.frm00;
    
    //テーブルが生成済みの場合
    if(document.getElementById("tblListSeihin")){
        //テーブル参照
        var tblShizai = document.getElementById("tblListSeihin");
        var gyoCount = tblShizai.rows.length;
        //一括チェック
        if(frm.chkSeihinIkkatu.checked){
            
            for(var i=0; i<gyoCount; i++){
            	if(gyoCount <= 1){
            		frm.chkSeihin.checked = true;
            	}else{
            		frm.chkSeihin[i].checked = true;
            	}
            }
        
        }else{
        
            for(var i=0; i<gyoCount; i++){
            	if(gyoCount <= 1){
            		frm.chkSeihin.checked = false;
            	}else{
            		frm.chkSeihin[i].checked = false;
            	}
            }
            
        }
    }
    
    return true;
}


//========================================================================================
// 資材一括選択
// 作成者：Y.Nishigawa
// 作成日：2009/11/06
// 引数  ：なし
// 概要  ：資材一括選択
//========================================================================================
function funShizaiCheck() {
    
    //明細ﾌﾚｰﾑのﾌｫｰﾑ参照
    var frm = document.frm00;
    
    //テーブルが生成済みの場合
    if(document.getElementById("tblListShizai")){
        //テーブル参照
        var tblShizai = document.getElementById("tblListShizai");
        var gyoCount = tblShizai.rows.length;
        //一括チェック
        if(frm.chkShizaiIkkatu.checked){
            
            for(var i=0; i<gyoCount; i++){
            	if(gyoCount <= 1){
            		frm.chkShizai.checked = true;
            	}else{
            		frm.chkShizai[i].checked = true;
            	}
            }
        
        }else{
        
            for(var i=0; i<gyoCount; i++){
            	
            	if(gyoCount <= 1){
            		frm.chkShizai.checked = false;
            	}else{
            		frm.chkShizai[i].checked = false;
            	}
            }
            
        }
    }
    
    return true;
}

//========================================================================================
// 候補一括選択
// 作成者：Y.Nishigawa
// 作成日：2009/11/06
// 引数  ：なし
// 概要  ：候補一括選択
//========================================================================================
function funKohoCheck() {
    
    //明細ﾌﾚｰﾑのﾌｫｰﾑ参照
    var frm = document.frm00;
    
    //テーブルが生成済みの場合
    if(document.getElementById("tblListKoho")){
        //テーブル参照
        var tblShizai = document.getElementById("tblListKoho");
        var gyoCount = tblShizai.rows.length;
        //一括チェック
        if(frm.chkKohoIkkatu.checked){
            
            for(var i=0; i<gyoCount; i++){
            	if(gyoCount <= 1){
            		frm.chkShizaiKoho.checked = true;
            	}else{
            		frm.chkShizaiKoho[i].checked = true;
            	}
            }
        
        }else{
        
            for(var i=0; i<gyoCount; i++){
                if(gyoCount <= 1){
            		frm.chkShizaiKoho.checked = false;
            	}else{
            		frm.chkShizaiKoho[i].checked = false;
            	}
            }
            
        }
    }
    
    return true;
}


//========================================================================================
// コード0埋め処理
// 作成者：Y.Nishigawa
// 作成日：2009/10/20
// 引数  ：①obj ：オブジェクト
//       ：②mode：1:製品,2:資材
// 概要  ：コード0埋め処理
//========================================================================================
function funInsertCdZero(obj,mode){
    
    var frm = document.frm00;
    var keta;
    
    //製品桁数指定
    if( mode == 1 ){
        keta = keta_seihin;
    }
    //資材桁数指定
    else if( mode == 2 ){
        keta = keta_shizai;
    }
    
    //0埋め実行
    funBuryZero(obj, keta);
    
    return true;
}




////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//
//                                 製品テーブル ﾍﾟｰｼﾞNo設定
//
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//========================================================================================
// 製品テーブルページ遷移
// 作成者：Y.Nishigawa
// 作成日：2009/04/06
// 引数  ：①NextPage   ：次のページ番号
// 戻り値：なし
// 概要  ：指定ページの情報を表示する。
//========================================================================================
function funPageMove_Seihin(NextPage) {

    //次ﾍﾟｰｼﾞの設定
    funSetCurrentPage_Seihin(NextPage);

    //指定ﾍﾟｰｼﾞのﾃﾞｰﾀ取得
    funSeihinSearch();
}

//========================================================================================
// 製品テーブルカレントページ設定処理
// 作成者：Y.Nishigawa
// 作成日：2009/11/05
// 引数  ：①pageNo ：カレントページ
// 戻り値：なし
// 概要  ：選択中のページ番号を設定する。
//========================================================================================
function funSetCurrentPage_Seihin(PageNo) {
    
    CurrentPage_Seihin = PageNo;

    return true;

}

//========================================================================================
// 製品テーブルページリンク作成
// 作成者：Y.Nishigawa
// 作成日：2009/11/05
// 引数  ：①PageNo   ：現在のページ番号
//       ：②MaxPageNo：最終ページ番号
//       ：③LinkId   ：ページリンク設定オブジェクトID
//       ：④TblId    ：明細部テーブルオブジェクトID
// 戻り値：なし
// 概要  ：ページ遷移用のHTML文を生成、出力する。
//========================================================================================
function funCreatePageLink_Seihin(PageNo, MaxPageNo, LinkId, TblId) {

    var obj;              //設定先オブジェクト
    var OutputHtml;       //出力HTML
    var i;
    var startidx;
    var endidx;

    obj = document.getElementById(LinkId);

    if (MaxPageNo != 0) {
        OutputHtml = "<table width=\"99%\" height=\"50\">";
        OutputHtml += "    <tr>";
        OutputHtml += "        <td align=\"center\">";

        if (PageNo == "1") {
            OutputHtml += "最初";
        } else {
            OutputHtml += "<span onClick=\"funPageMove_Seihin(1);\" tabindex=\"8\" style=\"cursor:pointer;color:blue;\"><u>最初</u></span>";
        }

        OutputHtml += "&nbsp;&nbsp;&nbsp;　";

        //開始ﾍﾟｰｼﾞ、終了ﾍﾟｰｼﾞ位置の取得
        if (MaxPageNo < 11) {
            startidx = 1;
            endidx = MaxPageNo;
        } else {
            if (PageNo < 5) {
                startidx = 1;
                endidx = 10;
            } else {
                startidx = PageNo - 4;
                if (MaxPageNo < PageNo+5) {
                    startidx = MaxPageNo - 9;
                    endidx = MaxPageNo;
                } else {
                    endidx = PageNo + 5;
                }
            }
        }

        for (i = startidx; i <= endidx; i++) {
            OutputHtml += "　";
            if (i == PageNo) {
                OutputHtml += "<span style=\"font-size:20px\">";
                OutputHtml += i;
                OutputHtml += "</span>&nbsp;";
            } else {
                OutputHtml += "<span onClick=\"funPageMove_Seihin(" + i + ");\" tabindex=\"8\" style=\"cursor:pointer;color:blue;\"><u>" + i + "</u></span>&nbsp;";
            }
        }

        OutputHtml += "&nbsp;&nbsp;　";

        if (MaxPageNo == "1" || PageNo == MaxPageNo) {
            OutputHtml += "最後";
        } else {
            OutputHtml += "<span onClick=\"funPageMove_Seihin(" + MaxPageNo + ");\" tabindex=\"8\" style=\"cursor:pointer;color:blue;\"><u>最後</u></span>";
        }

        OutputHtml += "        </td>";
        OutputHtml += "    </tr>";
        OutputHtml += "</table>";

    } else {
        //ﾃﾞｰﾀが存在しない場合はﾘﾝｸを表示しない
        OutputHtml = "";
    }

    //HTMLを出力
    obj.innerHTML = OutputHtml;
    CurrentPage_Seihin = PageNo;

    return true;

}

//========================================================================================
// 製品テーブルカレントページ取得処理
// 作成者：Y.Nishigawa
// 作成日：2009/11/05
// 引数  ：なし
// 戻り値：カレントページ
// 概要  ：選択中のページ番号を返す。
//========================================================================================
function funGetCurrentPage_Seihin() {

    return CurrentPage_Seihin;

}



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//
//                                 資材テーブル ﾍﾟｰｼﾞNo設定
//
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//========================================================================================
// 資材テーブルページ遷移
// 作成者：Y.Nishigawa
// 作成日：2009/04/06
// 引数  ：①NextPage   ：次のページ番号
// 戻り値：なし
// 概要  ：指定ページの情報を表示する。
//========================================================================================
function funPageMove_Shizai(NextPage) {

    //次ﾍﾟｰｼﾞの設定
    funSetCurrentPage_Shizai(NextPage);
    
    //指定ﾍﾟｰｼﾞのﾃﾞｰﾀ取得（製品-資材検索の場合）
    if(divKensakuZyotai.innerText == I000011){
        
        //製品より資材転送
        funSeihin_ShizaiSearch();
        
    //上記以外の場合（資材単品検索）
    }else{
        
        //資材検索
        funShizaiSearch();
    }
    
    return true;
}

//========================================================================================
// 資材テーブルカレントページ設定処理
// 作成者：Y.Nishigawa
// 作成日：2009/11/05
// 引数  ：①pageNo ：カレントページ
// 戻り値：なし
// 概要  ：選択中のページ番号を設定する。
//========================================================================================
function funSetCurrentPage_Shizai(PageNo) {
    
    CurrentPage_Shizai = PageNo;

    return true;

}

//========================================================================================
// 資材テーブルページリンク作成
// 作成者：Y.Nishigawa
// 作成日：2009/11/05
// 引数  ：①PageNo   ：現在のページ番号
//       ：②MaxPageNo：最終ページ番号
//       ：③LinkId   ：ページリンク設定オブジェクトID
//       ：④TblId    ：明細部テーブルオブジェクトID
// 戻り値：なし
// 概要  ：ページ遷移用のHTML文を生成、出力する。
//========================================================================================
function funCreatePageLink_Shizai(PageNo, MaxPageNo, LinkId, TblId) {

    var obj;              //設定先オブジェクト
    var OutputHtml;       //出力HTML
    var i;
    var startidx;
    var endidx;

    obj = document.getElementById(LinkId);

    if (MaxPageNo != 0) {
        OutputHtml = "<table width=\"99%\" height=\"50\">";
        OutputHtml += "    <tr>";
        OutputHtml += "        <td align=\"center\">";

        if (PageNo == "1") {
            OutputHtml += "最初";
        } else {
            OutputHtml += "<span onClick=\"funPageMove_Shizai(1);\" tabindex=\"17\" style=\"cursor:pointer;color:blue;\"><u>最初</u></span>";
        }

        OutputHtml += "&nbsp;&nbsp;&nbsp;　";

        //開始ﾍﾟｰｼﾞ、終了ﾍﾟｰｼﾞ位置の取得
        if (MaxPageNo < 11) {
            startidx = 1;
            endidx = MaxPageNo;
        } else {
            if (PageNo < 5) {
                startidx = 1;
                endidx = 10;
            } else {
                startidx = PageNo - 4;
                if (MaxPageNo < PageNo+5) {
                    startidx = MaxPageNo - 9;
                    endidx = MaxPageNo;
                } else {
                    endidx = PageNo + 5;
                }
            }
        }

        for (i = startidx; i <= endidx; i++) {
            OutputHtml += "　";
            if (i == PageNo) {
                OutputHtml += "<span style=\"font-size:20px\">";
                OutputHtml += i;
                OutputHtml += "</span>&nbsp;";
            } else {
                OutputHtml += "<span onClick=\"funPageMove_Shizai(" + i + ");\" tabindex=\"17\" style=\"cursor:pointer;color:blue;\"><u>" + i + "</u></span>&nbsp;";
            }
        }

        OutputHtml += "&nbsp;&nbsp;　";

        if (MaxPageNo == "1" || PageNo == MaxPageNo) {
            OutputHtml += "最後";
        } else {
            OutputHtml += "<span onClick=\"funPageMove_Shizai(" + MaxPageNo + ");\" tabindex=\"17\" style=\"cursor:pointer;color:blue;\"><u>最後</u></span>";
        }

        OutputHtml += "        </td>";
        OutputHtml += "    </tr>";
        OutputHtml += "</table>";

    } else {
        //ﾃﾞｰﾀが存在しない場合はﾘﾝｸを表示しない
        OutputHtml = "";
    }

    //HTMLを出力
    obj.innerHTML = OutputHtml;
    CurrentPage_Shizai = PageNo;

    return true;

}

//========================================================================================
// 資材テーブルカレントページ取得処理
// 作成者：Y.Nishigawa
// 作成日：2009/11/05
// 引数  ：なし
// 戻り値：カレントページ
// 概要  ：選択中のページ番号を返す。
//========================================================================================
function funGetCurrentPage_Shizai() {

    return CurrentPage_Shizai;

}


//========================================================================================
// 終了ボタン押下
// 作成者：Y.Nishigawa
// 作成日：2009/11/07
// 概要  ：終了処理
//========================================================================================
function funEndClick(){
    
    //類似品検索 候補テーブル参照
    var tblShizaiR = document.getElementById("tblListKoho");
    var gyoCountR = tblShizaiR.rows.length;
    
    //候補が転送されていない場合に、確認メッセージ
    if( gyoCountR >= 1 ){
        
        //確認メッセージを表示
        if(window.confirm(E000010)){
            //終了処理
            funEnd();
        }else{
            //何もしない
            return true;
        }
        
    }else{
        //終了処理
        funEnd();
    }

}

//========================================================================================
// 終了処理
// 作成者：Y.Nishigawa
// 作成日：2009/11/05
// 概要  ：終了処理
//========================================================================================
function funEnd(){
    
    //画面を閉じる
    window.close();
    
}

