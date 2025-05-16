//========================================================================================
// 【QP@00342】初期表示処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/24
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

	//初期ページ設定
	funSetCurrentPage(1);

    //画面設定
    funInitScreen(ConStatusRirekiId);

    //画面情報を取得・設定
    if (funGetInfo(1) == false) {
        return false;
    }

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
    var XmlId = "RGEN2010";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2000");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2000I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2000O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //ｾｯｼｮﾝ情報、ステータス履歴の情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2010, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //HTML出力オブジェクト設定
    var obj = document.getElementById("divStatusRireki");
    OutputHtml = "";

    OutputHtml = OutputHtml + "<table class=\"detail\" id=\"tblList\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" style=\"width:910px;\">";
    var i; //ループカウント
    var cnt_roop = funXmlRead_3(xmlResAry[2], "kihon", "roop_cnt", 0, 0);

    //試作コード表示
	frm.hidShisakuNo_shaincd.value = funXmlRead_3(xmlResAry[2], "kihon", "cd_shain", 0, 0);
	frm.hidShisakuNo_nen.value = funXmlRead_3(xmlResAry[2], "kihon", "nen", 0, 0);
	frm.hidShisakuNo_oi.value = funXmlRead_3(xmlResAry[2], "kihon", "no_oi", 0, 0);
	frm.hidShisakuNo_eda.value = funXmlRead_3(xmlResAry[2], "kihon", "no_eda", 0, 0);
    var obj1 = document.getElementById("divStatusRirekiCd");
    var hidShisakuNo_shaincd = frm.hidShisakuNo_shaincd.value;
    var hidShisakuNo_nen = frm.hidShisakuNo_nen.value;
    var hidShisakuNo_oi = frm.hidShisakuNo_oi.value;
    var hidShisakuNo_eda = frm.hidShisakuNo_eda.value;
    obj1.innerHTML = hidShisakuNo_shaincd + " - " + hidShisakuNo_nen + " - " +  hidShisakuNo_oi + " - " + hidShisakuNo_eda;

    for(i = 0; i < cnt_roop; i++){
    	//設定値取得
    	var no_row = funXmlRead_3(xmlResAry[2], "kihon", "no_row", 0, i);
    	var dt_henkou_ymd = funXmlRead_3(xmlResAry[2], "kihon", "dt_henkou_ymd", 0, i);
    	var dt_henkou_hms = funXmlRead_3(xmlResAry[2], "kihon", "dt_henkou_hms", 0, i);
    	var nm_henkou = funXmlRead_3(xmlResAry[2], "kihon", "nm_henkou", 0, i);
    	var nm_zikko_ca = funXmlRead_3(xmlResAry[2], "kihon", "nm_zikko_ca", 0, i);
    	var nm_zikko_li = funXmlRead_3(xmlResAry[2], "kihon", "nm_zikko_li", 0, i);
    	var st_kenkyu = funXmlRead_3(xmlResAry[2], "kihon", "st_kenkyu", 0, i);
    	var st_seisan = funXmlRead_3(xmlResAry[2], "kihon", "st_seisan", 0, i);
    	var st_gensizai = funXmlRead_3(xmlResAry[2], "kihon", "st_gensizai", 0, i);
    	var st_kojo = funXmlRead_3(xmlResAry[2], "kihon", "st_kojo", 0, i);
    	var st_eigyo = funXmlRead_3(xmlResAry[2], "kihon", "st_eigyo", 0, i);
    	var cd_zikko_li = funXmlRead_3(xmlResAry[2], "kihon", "cd_zikko_li", 0, i); //ADD 【H24年度対応】 2012/04/16 青木 保存内容識別要素

    	//テーブル設定
    	//ADD 【H24年度対応】 2012/04/16 青木 S
    	if(cd_zikko_li == "0"){	// 保存内容識別要素が"0"（仮保存）ならば背景は白
        	OutputHtml = OutputHtml + "<tr class=\"disprow\" >";

        //ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
    	} else if(cd_zikko_li == "9"){
    		// 保存内容識別要素が"9"（工場変更）ならば背景は白
           	OutputHtml = OutputHtml + "<tr class=\"disprow\" >";
        //ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 end
    	}
    	else{					// 仮保存以外は薄いブルー(参考色:Const.js→アクアマリン)
        	OutputHtml = OutputHtml + "<tr class=\"disprow\" bgcolor=\"#7fffd4\">";
    	}
    	//ADD 【H24年度対応】 2012/04/16 青木 E
    	OutputHtml = OutputHtml + "<td class=\"column\" width=\"30\"  align=\"right\" >" + no_row + "</td>";
    	OutputHtml = OutputHtml + "<td class=\"column\" width=\"80\"  align=\"left\" >" + dt_henkou_ymd + "</td>";
    	OutputHtml = OutputHtml + "<td class=\"column\" width=\"70\"  align=\"left\" >" + dt_henkou_hms + "</td>";
    	OutputHtml = OutputHtml + "<td class=\"column\" width=\"100\" align=\"left\" >" + nm_henkou + "</td>";
    	OutputHtml = OutputHtml + "<td class=\"column\" width=\"80\"  align=\"left\" >" + nm_zikko_ca + "</td>";
    	OutputHtml = OutputHtml + "<td class=\"column\" width=\"295\" align=\"left\" >" + nm_zikko_li + "</td>";
    	OutputHtml = OutputHtml + "<td class=\"column\" width=\"50\"  align=\"center\" >" + funStatusSetting("1",st_kenkyu) + "</td>";
    	OutputHtml = OutputHtml + "<td class=\"column\" width=\"50\"  align=\"center\" >" + funStatusSetting("2",st_seisan) + "</td>";
    	OutputHtml = OutputHtml + "<td class=\"column\" width=\"50\"  align=\"center\" >" + funStatusSetting("3",st_gensizai) + "</td>";
    	OutputHtml = OutputHtml + "<td class=\"column\" width=\"50\"  align=\"center\" >" + funStatusSetting("4",st_kojo) + "</td>";
    	OutputHtml = OutputHtml + "<td class=\"column\" width=\"50\"  align=\"center\" >" + funStatusSetting("5",st_eigyo) + "</td>";
    	OutputHtml = OutputHtml + "</tr>";

    }
    OutputHtml = OutputHtml + "</table>";

    //HTMLを出力
    obj.innerHTML = OutputHtml;

    //ﾃﾞｰﾀ件数、ﾍﾟｰｼﾞﾘﾝｸの設定
    spnRecInfo.style.display = "block";
    RecCnt = funXmlRead(xmlResAry[2], "max_row", 0);
    spnRecCnt.innerText = RecCnt;
    ListMaxRow = funXmlRead(xmlResAry[2], "list_max_row", 0);
    spnRowMax.innerText = ListMaxRow;
    PageCnt = Math.ceil(RecCnt / ListMaxRow);
    funCreatePageLink(funGetCurrentPage(), PageCnt, "divPage", "tblList");
    spnCurPage.innerText = funGetCurrentPage() + "／" + PageCnt + "ページ";

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
        if (XmlId.toString() == "RGEN2010") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2000
                	var hidShisakuNo_shaincd = frm.hidShisakuNo_shaincd.value;
                	var hidShisakuNo_nen = frm.hidShisakuNo_nen.value;
                	var hidShisakuNo_oi = frm.hidShisakuNo_oi.value;
                	var hidShisakuNo_eda = frm.hidShisakuNo_eda.value;
                	var page = funGetCurrentPage();

                    funXmlWrite(reqAry[i], "cd_shain", hidShisakuNo_shaincd, 0);
                    funXmlWrite(reqAry[i], "nen", hidShisakuNo_nen, 0);
                    funXmlWrite(reqAry[i], "no_oi", hidShisakuNo_oi, 0);
                    funXmlWrite(reqAry[i], "no_eda", hidShisakuNo_eda, 0);
                    funXmlWrite(reqAry[i], "no_page", page, 0);
                    break;
            }
        }
    }

    return true;

}

//========================================================================================
// 【QP@00342】ページ遷移
// 作成者：Y.Nishigawa
// 作成日：2011/01/24
// 引数  ：①NextPage   ：次のページ番号
// 戻り値：なし
// 概要  ：指定ページの情報を表示する。
//========================================================================================
function funPageMove(NextPage) {

    //次ﾍﾟｰｼﾞの設定
    funSetCurrentPage(NextPage);

    //指定ﾍﾟｰｼﾞのﾃﾞｰﾀ取得
    funGetInfo(1);
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
    window.close();
}

