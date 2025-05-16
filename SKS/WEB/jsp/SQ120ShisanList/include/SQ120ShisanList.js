
//========================================================================================
// 共通変数
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
//========================================================================================
//機能ID、データ参照IDを保持
var hidKinoId = "";
var hidDataId = "";
// 【QP@10713】2011.10.27 ADD Start hisahori
var hidUserId = "";
// 【QP@10713】2011.10.27 ADD End
var flg_kenkyu = "";
var flg_seikan = "";
var flg_gentyo = "";
var flg_kojo = "";
var flg_eigyo = "";
var flg_tab="0";
//***** ADD【H24年度対応】20120416 hagiwara S **********
var search_cnt = 0;
//***** ADD【H24年度対応】20120416 hagiwara E **********


//========================================================================================
// 【QP@00342】初期表示処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/31
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //画面設定
    funInitScreen(ConGenkaListId);

    //ﾍﾟｰｼﾞの設定
    funSetCurrentPage(1);

    //タブ設定
    document.getElementById('todo').style.backgroundColor='#8380F5';
	document.getElementById('todo').style.color='#ffffff';
	//frm.chkKanryo.disabled = true;

    //画面情報を取得・設定
    if (funGetInfo(1) == false) {
        return false;
    }

    //画面の初期化
    //funClear();

    return true;

}

//========================================================================================
// 【QP@00342】タブ選択
// 作成者：Y.Nishigawa
// 作成日：2011/01/31
//========================================================================================
function todo_click(){

	var frm = document.frm00;    //ﾌｫｰﾑへの参照

	//タブ設定
	document.getElementById('todo').style.backgroundColor='#8380F5';
	document.getElementById('itiran').style.backgroundColor='#ffffff';
	document.getElementById('todo').style.color='#ffffff';
	document.getElementById('itiran').style.color='#777777';

	//ﾍﾟｰｼﾞの設定
    funSetCurrentPage(1);

	//検索条件設定
	flg_tab="0";
	//frm.chkKanryo.checked = false;
	//frm.chkKanryo.disabled = true;

}
function itiran_click(){

	var frm = document.frm00;    //ﾌｫｰﾑへの参照

	//タブ設定
	document.getElementById('itiran').style.backgroundColor='#8380F5';
	document.getElementById('todo').style.backgroundColor='#ffffff';
	document.getElementById('itiran').style.color='#ffffff';
	document.getElementById('todo').style.color='#777777';

	//ﾍﾟｰｼﾞの設定
    funSetCurrentPage(1);

	//検索条件設定
	flg_tab="1";
	if(frm.chkMinyuryoku.checked == false){
			frm.chkKanryo.disabled = false;
	}


}

//========================================================================================
// 【QP@00342】未入力チェックボックス選択
// 作成者：Y.Nishigawa
// 作成日：2011/01/31
//========================================================================================
function minyuryoku_click(){

	var frm = document.frm00;    //ﾌｫｰﾑへの参照

	//未入力選択時
	if(frm.chkMinyuryoku.checked == true){
		frm.chkKanryo.checked = false;
		frm.chkKanryo.disabled = true;

		frm.txtKizitu_From.value="";
		frm.txtKizitu_From.disabled=true;
		frm.txtKizitu_From.style.backgroundColor="#cccccc";

		frm.txtKizitu_To.value="";
		frm.txtKizitu_To.disabled=true;
		frm.txtKizitu_To.style.backgroundColor="#cccccc";
	}
	//未入力未選択時
	else{
		frm.chkKanryo.disabled = false;

		frm.txtKizitu_From.disabled=false;
		frm.txtKizitu_From.style.backgroundColor="#ffffff";

		frm.txtKizitu_To.disabled=false;
		frm.txtKizitu_To.style.backgroundColor="#ffffff";

	}
}

//========================================================================================
// 【QP@00342】画面情報取得処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/31
// 引数  ：①mode  ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：ユーザのセッション情報、コンボボックスの情報を取得する
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN2120";
    //【QP@20505】No.31 2012/09/14 TT H.Shima MOD Start
//    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA030","SA050","SA080","SA140","SA290","FGEN2140","FGEN2130");
//    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA030I,xmlSA050I,xmlSA080I,xmlSA140I,xmlSA290I,xmlFGEN2140I,xmlFGEN2130I);
//    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA030O,xmlSA050O,xmlSA080O,xmlSA140O,xmlSA290O,xmlFGEN2140O,xmlFGEN2130O);
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA030","SA050","SA080","SA140","SA290","FGEN2140","FGEN2130","SA250");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA030I,xmlSA050I,xmlSA080I,xmlSA140I,xmlSA290I,xmlFGEN2140I,xmlFGEN2130I,xmlSA250I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA030O,xmlSA050O,xmlSA080O,xmlSA140O,xmlSA290O,xmlFGEN2140O,xmlFGEN2130O,xmlSA250O);
    //【QP@20505】No.31 2012/09/14 TT H.Shima MOD End
    //*******
    //2015/03/30 TT.Kitazawa【QP@40812】No.24  xmlResAry[9]： 未使用（担当者の取得）
    // cd_kaisha="" で検索の為、０件取得
    //*******

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2120, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //ﾕｰｻﾞ情報表示
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

// 【QP@10713】2011.10.27 ADD Start hisahori
        var tmpUserID;
        var i;
        tmpUserID = funXmlRead(xmlResAry[1], "id_user", 0);
	    for (i = tmpUserID.length; i < 10; i++) {
	        tmpUserID = "0" + tmpUserID;
	    }
        hidUserId = tmpUserID;
// 【QP@10713】2011.10.27 ADD End

    //権限関連の処理を行う
    funSaveKengenInfo();

    //ｺﾝﾎﾞﾎﾞｯｸｽの作成--------------------------------------------------------------------------
    //ユーザコンボボックス
    funCreateComboBox(frm.ddlUser, xmlResAry[2], 4, 2);

    // DEL 2013/10/22 QP@30154 okano start
    //グループ・チームコンボボックス
    //権限が「同一グループ且つ（本人＋チームリーダー以上）」の場合
//	    if(hidDataId == "1"){
//		    funCreateComboBox(frm.ddlGroup, xmlResAry[3], 1, 1);
//	    	funCreateComboBox(frm.ddlTeam, xmlResAry[4], 2, 2);
//	    	//【QP@20505】No.31 2012/09/14 TT H.Shima ADD Start
//	    	funCreateComboBox(frm.ddlTanto, xmlResAry[9], 3 ,2);
//	    	//【QP@20505】No.31 2012/09/14 TT H.Shima ADD End
//
//	    	//ｺﾝﾎﾞﾎﾞｯｸｽの再設定
//		    funDefaultIndex(frm.ddlGroup, 1);
//		    funDefaultIndex(frm.ddlTeam, 2);
//		    //【QP@20505】No.31 2012/09/14 TT H.Shima ADD Start
//		    funDefaultIndex(frm.ddlTanto, 3);
//		    //【QP@20505】No.31 2012/09/14 TT H.Shima ADD End
//	// 【QP@10713】2011.10.27 ADD Start hisahori
//	        frm.txtShisakuNo.value = hidUserId;
//	// 【QP@10713】2011.10.27 ADD End
//
//	    }
//	    //権限が「同一グループ且つ（本人＋チームリーダー以上）」以外の場合
//	    else{
    // DEL 2013/10/22 QP@30154 okano end

    	// ADD 2015/03/30 TT.Kitazawa【QP@40812】No.24 start
        // 研担当者検索用：選択グループの会社CDを渡す為、ｸﾞﾙｰﾌﾟ情報を退避
    	xmlSA050.load(xmlResAry[3]);
    	// ADD 2015/03/30 TT.Kitazawa【QP@40812】No.24 end
    	funCreateComboBox(frm.ddlGroup, xmlResAry[3], 1, 2);
	    funClearSelect(frm.ddlTeam, 2);
	    //【QP@20505】No.31 2012/09/14 TT H.Shima ADD Start
	    funClearSelect(frm.ddlTanto, 2);
	    //【QP@20505】No.31 2012/09/14 TT H.Shima ADD End
	// DEL 2013/10/22 QP@30154 okano start
//    	}
    // DEL 2013/10/22 QP@30154 okano end

    //会社・部署コンボボックス
    //権限が「自分工場のみ」の場合
    if(hidDataId == "2"){
    	funCreateComboBox(frm.ddlKaisha, xmlResAry[5], 5, 1);
    	funCreateComboBox(frm.ddlBusho, xmlResAry[6], 6, 1);
    }
    //権限が「自分工場のみ」以外の場合
    else{
    	funCreateComboBox(frm.ddlKaisha, xmlResAry[5], 5, 2);
    	funClearSelect(frm.ddlBusho, 2);
    }

    //枝番種類コンボボックス
    funCreateComboBox(frm.ddlShurui, xmlResAry[7], 4, 2);


    //所属部署フラグ設定------------------------------------------------------------------------
    var flg_kenkyu = funXmlRead_3(xmlResAry[8], "table", "flg_kenkyu", 0, 0);
	var flg_seikan = funXmlRead_3(xmlResAry[8], "table", "flg_seikan", 0, 0);
	var flg_gentyo = funXmlRead_3(xmlResAry[8], "table", "flg_gentyo", 0, 0);
	var flg_kojo = funXmlRead_3(xmlResAry[8], "table", "flg_kojo", 0, 0);
	var flg_eigyo = funXmlRead_3(xmlResAry[8], "table", "flg_eigyo", 0, 0);


	//状況部署コンボボックス生成-----------------------------------------------------------------
	funClearSelect(frm.ddlZyokyo, 2);
	var obj = frm.ddlZyokyo;
	// DEL 2013/8/7 okano【QP@30151】No.12 start
//	//研究所
//	if( flg_kenkyu == "1" ){
//		var objNewOption = document.createElement("option");
//	    obj.options.add(objNewOption);
//    	objNewOption.innerText = "研究所";
//    	objNewOption.value = "1";
//
//	}
//	//生産管理部
//	else if( flg_seikan == "1" ){
	// DEL 2013/8/7 okano【QP@30151】No.12 end
		var objNewOption1 = document.createElement("option");
	    obj.options.add(objNewOption1);
    	objNewOption1.innerText = "研究所";
    	objNewOption1.value = "1";

    	var objNewOption2 = document.createElement("option");
    	obj.options.add(objNewOption2);
    	objNewOption2.innerText = "生産管理部";
    	objNewOption2.value = "2";

    	var objNewOption3 = document.createElement("option");
    	obj.options.add(objNewOption3);
    	objNewOption3.innerText = "原資材調達部";
    	objNewOption3.value = "3";

    	var objNewOption4 = document.createElement("option");
    	obj.options.add(objNewOption4);
    	objNewOption4.innerText = "工場";
    	objNewOption4.value = "4";

    	var objNewOption5 = document.createElement("option");
    	obj.options.add(objNewOption5);
    	objNewOption5.innerText = "営業";
    	objNewOption5.value = "5";

	// DEL 2013/8/7 okano【QP@30151】No.12 start
//	}
//	//原資材調達部
//	else if( flg_gentyo == "1" ){
//		var objNewOption = document.createElement("option");
//	    obj.options.add(objNewOption);
//    	objNewOption.innerText = "原資材調達部";
//    	objNewOption.value = "3";
//
//	}
//    //工場
//	else if( flg_kojo == "1" ){
//		var objNewOption = document.createElement("option");
//	    obj.options.add(objNewOption);
//    	objNewOption.innerText = "工場";
//    	objNewOption.value = "4";
//
//	}
//	//営業
//	else if( flg_eigyo == "1" ){
//		var objNewOption = document.createElement("option");
//	    obj.options.add(objNewOption);
//    	objNewOption.innerText = "営業";
//    	objNewOption.value = "5";
//
//	}
	// DEL 2013/8/7 okano【QP@30151】No.12 end

	//ステータスコンボボックス
	funClearSelect(frm.ddlStatus, 2);

    return true;

}

//========================================================================================
// 所属チームコンボボックス連動処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/31
// 引数  ：なし
// 概要  ：所属グループに紐付く所属チームコンボボックスを生成する
//========================================================================================
function funChangeGroup() {

	// DEL 2013/10/22 QP@30154 okano start
//		//権限が「同一グループ且つ（本人＋チームリーダー以上）」の場合
//	    if(hidDataId == "1"){
//	    	//処理しない
//	    	//return true;
//	    }
	// DEL 2013/10/22 QP@30154 okano end

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN2130";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA080");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA080I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA080O);

    if (frm.ddlGroup.selectedIndex == 0) {
        //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
        funClearSelect(frm.ddlTeam, 2);
        //【QP@20505】No.31 2012/09/20 TT H.Shima ADD Start
        funClearSelect(frm.ddlTanto, 2);
        //【QP@20505】No.31 2012/09/20 TT H.Shima ADD End
        return true;
    }

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }


    //ﾁｰﾑ情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2130, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽの作成
    funCreateComboBox(frm.ddlTeam, xmlResAry[2], 2 , 2);
    //【QP@20505】No.31 2012/09/14 TT H.Shima ADD Start
    funClearSelect(frm.ddlTanto, 2);
    //【QP@20505】No.31 2012/09/14 TT H.Shima ADD End

    return true;

}

//========================================================================================
// 製造会社コンボボックス連動処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/31
// 引数  ：なし
// 概要  ：製造会社に紐付く製造工場コンボボックスを生成する
//========================================================================================
function funChangeKaisha() {

	//権限が「自工場分のみ」の場合
    if(hidDataId == "2"){
    	//処理しない
    	return true;
    }

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN2140";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA290");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA290I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA290O);

    if (frm.ddlKaisha.selectedIndex == 0) {
        //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
        funClearSelect(frm.ddlBusho, 2);
        return true;
    }

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }


    //ﾁｰﾑ情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2140, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽの作成
    funCreateComboBox(frm.ddlBusho, xmlResAry[2], 6 , 2);

    return true;

}

//========================================================================================
//担当者コンボボックス連動処理
//作成者：H.Shima
//作成日：2012/09/13
//引数  ：なし
//概要  ：所属グループ、所属チームに紐付く担当者コンボボックスを生成する
//========================================================================================
function funChangeTeam() {

  var frm = document.frm00;    //ﾌｫｰﾑへの参照
  var XmlId = "JSP0320";
  var FuncIdAry = new Array(ConResult,ConUserInfo,"SA250");
  var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA250I);
  var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA250O);

  if (frm.ddlTeam.selectedIndex == 0) {
      //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
      funClearSelect(frm.ddlTanto, 2);
      return true;
  }

  //引数をXMLﾌｧｲﾙに設定
  if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
      return false;
  }

  //担当者情報を取得
  if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0320, xmlReqAry, xmlResAry, 1) == false) {
      return false;
  }

  //ｺﾝﾎﾞﾎﾞｯｸｽの作成
  funCreateComboBox(frm.ddlTanto, xmlResAry[2], 3, 2);

  return true;

}

//========================================================================================
// 状況部署コンボボックス連動処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/31
// 引数  ：なし
// 概要  ：状況部署に紐付くステータスコンボボックスを生成する
//========================================================================================
function funChangeZyokyo() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

	//ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
    funClearSelect(frm.ddlStatus, 2);

    //空白選択時
    if (frm.ddlZyokyo.selectedIndex == 0) {
        return true;
    }

    //選択情報取得
    var zyokyo = frm.ddlZyokyo.options[frm.ddlZyokyo.selectedIndex].value;

	//コンボボックス生成
	var obj = frm.ddlStatus;
	if(zyokyo == "1"){
		//研究所が選択されている場合
		var objNewOption1 = document.createElement("option");
	    obj.options.add(objNewOption1);
    	objNewOption1.innerText = "試算依頼";
    	objNewOption1.value = "2";

	}
	else if(zyokyo == "2"){
		//生産管理部が選択されている場合
		var objNewOption1 = document.createElement("option");
	    obj.options.add(objNewOption1);
    	objNewOption1.innerText = "なし";
    	objNewOption1.value = "1";

    	var objNewOption2 = document.createElement("option");
    	obj.options.add(objNewOption2);
    	objNewOption2.innerText = "試算依頼";
    	objNewOption2.value = "2";

    	var objNewOption3 = document.createElement("option");
    	obj.options.add(objNewOption3);
    	objNewOption3.innerText = "確認完了";
    	objNewOption3.value = "3";

	}
	else if(zyokyo == "3"){
		//原資材調達部が選択されている場合
		var objNewOption1 = document.createElement("option");
	    obj.options.add(objNewOption1);
    	objNewOption1.innerText = "なし";
    	objNewOption1.value = "1";

    	var objNewOption2 = document.createElement("option");
    	obj.options.add(objNewOption2);
    	objNewOption2.innerText = "確認完了";
    	objNewOption2.value = "2";

	}
	else if(zyokyo == "4"){
		//工場が選択されている場合
		var objNewOption1 = document.createElement("option");
	    obj.options.add(objNewOption1);
    	objNewOption1.innerText = "なし";
    	objNewOption1.value = "1";

    	var objNewOption2 = document.createElement("option");
    	obj.options.add(objNewOption2);
    	objNewOption2.innerText = "確認完了";
    	objNewOption2.value = "2";

	}
	else if(zyokyo == "5"){
		//営業が選択されている場合
		var objNewOption1 = document.createElement("option");
	    obj.options.add(objNewOption1);
    	objNewOption1.innerText = "なし";
    	objNewOption1.value = "1";

    	var objNewOption2 = document.createElement("option");
    	obj.options.add(objNewOption2);
    	objNewOption2.innerText = "試算依頼";
    	objNewOption2.value = "2";

    	var objNewOption3 = document.createElement("option");
    	obj.options.add(objNewOption3);
    	objNewOption3.innerText = "確認完了";
    	objNewOption3.value = "3";

    	var objNewOption4 = document.createElement("option");
    	obj.options.add(objNewOption4);
    	objNewOption4.innerText = "採用有無完了";
    	objNewOption4.value = "4";

	}

    return true;

}



//========================================================================================
// 検索処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/31
// 引数  ：なし
// 概要  ：XMLデータ取得→HTMLオブジェクト生成（再帰処理）→一覧表示
//========================================================================================
function funDataSearch() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN2150";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2150");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2150I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2150O);
    var RecCnt;
    var PageCnt;
    var ListMaxRow;

    //***** ADD【H24年度対応】20120411 hagiwara S **********
    search_cnt = 0;
    //***** ADD【H24年度対応】20120411 hagiwara E **********


    //処理中ﾒｯｾｰｼﾞ表示
	showWin_mati();

	//選択行の初期化
	funSetCurrentRow("");

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        //一覧のｸﾘｱ
        funClearList();
        setTimeout(function(){ closeWin_mati(); },0);
        return false;
    }

    //***** ADD【H24年度対応】20120411 hagiwara S **********
    // 印刷用のxmlFGEN2210Iにも引数を残す
    if (funReadyOutput(XmlId, [xmlUSERINFO_I, xmlFGEN2210I], 1) == false) {
        // 一覧のｸﾘｱ
        funClearList();
        setTimeout(function(){ closeWin_mati(); },0);
        return false;
    }
    //***** ADD【H24年度対応】20120411 hagiwara E **********

    //検索条件に一致する試作ﾃﾞｰﾀを取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2150, xmlReqAry, xmlResAry, 1) == false) {
        //一覧のｸﾘｱ
        funClearList();
        setTimeout(function(){ closeWin_mati(); },0);
        return false;
    }

    //表示中に検索系アクションを操作不可
    document.getElementById("todo").disabled = true;
    document.getElementById("itiran").disabled = true;
    document.getElementById("btnSearch").disabled = true;

    //一覧のｸﾘｱ
    funClearList_search();


    //取得ﾃﾞｰﾀの設定
    //HTML出力オブジェクト設定
    var obj = document.getElementById("divMeisai");
    var roop_cnt = funXmlRead(xmlResAry[2], "roop_cnt", 0);
    //***** ADD【H24年度対応】20120416 hagiwara S **********
    // 印刷処理判定用に検索結果総数を格納
    search_cnt = funXmlRead(xmlFGEN2150O, "roop_cnt", 0);
    //***** ADD【H24年度対応】20120416 hagiwara E **********
    var i;
    var tableNm = "table";
    var output_html = "";
    output_html = output_html + "<table cellpadding=\"0\" id=\"tblList\" cellspacing=\"0\" border=\"1\">";

    //再帰処理（擬似マルチスレッド）
    //処理待ちウィンドウのGIFアニメが止まらないようにする為、再帰処理内にて
    //setTimeoutを用いて、画面に処理を返す
    DataSet( xmlResAry ,output_html , 0 , roop_cnt);

    //ﾃﾞｰﾀ件数、ﾍﾟｰｼﾞﾘﾝｸの設定
    spnRecInfo.style.display = "block";
    RecCnt = funXmlRead(xmlResAry[2], "max_row", 0);
    spnRecCnt.innerText = RecCnt;
    ListMaxRow = funXmlRead(xmlResAry[2], "list_max_row", 0);
    spnRowMax.innerText = ListMaxRow;
    PageCnt = Math.ceil(RecCnt / ListMaxRow);
    funCreatePageLink(funGetCurrentPage(), PageCnt, "divPage", "tblList");
    spnCurPage.innerText = funGetCurrentPage() + "／" + PageCnt + "ページ";

    FuncIdAry = null;
    xmlReqAry = null;

    return true;

}
function DataSet( xmlResAry ,html , i , cnt){
	if(i < cnt){
		var tableNm = "table";

		//レスポンスデータ取得-------------------------------------------------------------------------------
    	var no_row = funXmlRead_3(xmlResAry[2], tableNm, "no_row", 0, i);
    	var hi_kizitu = funSetNbsp_kizitu(funXmlRead_3(xmlResAry[2], tableNm, "hi_kizitu", 0, i));
    	var cnt_irai = funXmlRead_3(xmlResAry[2], tableNm, "cnt_irai", 0, i);
    	var no_shisaku = funXmlRead_3(xmlResAry[2], tableNm, "no_shisaku", 0, i);
    	var no_eda = funXmlRead_3(xmlResAry[2], tableNm, "no_eda", 0, i);
    	var nm_shisaku = funEscape(funXmlRead_3(xmlResAry[2], tableNm, "nm_shisaku", 0, i));
    	var nm_shurui = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_shurui", 0, i));
    	var no_iraisample = funSetNbsp(funEscape(funXmlRead_3(xmlResAry[2], tableNm, "no_iraisample", 0, i)));
    	var st_kenkyu = funXmlRead_3(xmlResAry[2], tableNm, "st_kenkyu", 0, i);
    	var st_seikan = funXmlRead_3(xmlResAry[2], tableNm, "st_seikan", 0, i);
    	var st_gentyo = funXmlRead_3(xmlResAry[2], tableNm, "st_gentyo", 0, i);
    	var st_kojo = funXmlRead_3(xmlResAry[2], tableNm, "st_kojo", 0, i);
    	var st_eigyo = funXmlRead_3(xmlResAry[2], tableNm, "st_eigyo", 0, i);
    	var cd_saiyou = funXmlRead_3(xmlResAry[2], tableNm, "cd_saiyou", 0, i);
    	var no_saiyou = funSetNbsp(funEscape(funXmlRead_3(xmlResAry[2], tableNm, "no_saiyou", 0, i)));
    	var nm_team = funXmlRead_3(xmlResAry[2], tableNm, "nm_team", 0, i);
    	var nm_liuser = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_liuser", 0, i));
    	var nm_kaisha_seizo = funXmlRead_3(xmlResAry[2], tableNm, "nm_kaisha_seizo", 0, i);
    	var nm_kaisha_busho = funXmlRead_3(xmlResAry[2], tableNm, "nm_kaisha_busho", 0, i);
    	var memo_eigyo = funEscape(funXmlRead_3(xmlResAry[2], tableNm, "memo_eigyo", 0, i));
    	// 【QP@10713】2011.10.28 ADD start hisahori
    	var cd_nisugata = funSetNbsp(funEscape(funXmlRead_3(xmlResAry[2], tableNm, "cd_nisugata", 0, i)));
    	var nm_ondo = funEscape(funXmlRead_3(xmlResAry[2], tableNm, "nm_ondo", 0, i));
    	var nm_tantoeigyo = funEscape(funXmlRead_3(xmlResAry[2], tableNm, "nm_tantoeigyo", 0, i));
    	//【QP@20505】No.31 2012/09/14 TT H.Shima MOD Start
//    	var nm_tantosha = funEscape(funXmlRead_3(xmlResAry[2], tableNm, "nm_tantosha", 0, i));
    	var nm_tantosha = funSetNbsp(funEscape(funXmlRead_3(xmlResAry[2], tableNm, "nm_tantosha", 0, i)));
    	//【QP@20505】No.31 2012/09/14 TT H.Shima MOD End
    	// 【QP@10713】2011.10.28 ADD end

		//HTML出力オブジェクト設定---------------------------------------------------------------------------
		//TR行開始
		var output_html = "";
		output_html = output_html + "<tr class=\"disprow\">";

		/*【QP@20505】No.31 2012/09/14 TT H.Shima MOD 担当者列追加によるサイズの調整 Start */
		//行No
//		output_html = output_html + "    <td class=\"column\" width=\"30\"  align=\"right\" >" +
		output_html = output_html + "    <td class=\"column\" width=\"28\"  align=\"right\" >" +
				"<div id=\"no_row_" + i + "\" onDblClick=\"openWin(" + i + ")\">" + no_row + "</div></td>";

		//選択ラジオボタン
//		output_html = output_html + "    <td class=\"column\" width=\"30\"  align=\"center\">" +
		output_html = output_html + "    <td class=\"column\" width=\"31\"  align=\"center\">" +
				"<input type=\"radio\" name=\"chk\" onclick=\"clickItiran(" + i + ");\" value=\"" + i + "\" tabindex=\"-1\"></td>";

		//試算期日
//		output_html = output_html + "    <td class=\"column\" width=\"80\"  align=\"left\"  >" +
		output_html = output_html + "    <td class=\"column\" width=\"79\"  align=\"left\"  >" +
				"<div id=\"hi_kizitu_" + i + "\" onDblClick=\"openWin(" + i + ")\">" + hi_kizitu +"</div></td>";

		//依頼回数
//		output_html = output_html + "    <td class=\"column\" width=\"31\"  align=\"right\" >" +
		output_html = output_html + "    <td class=\"column\" width=\"30\"  align=\"right\" >" +
				"<div id=\"cnt_irai_" + i + "\" onDblClick=\"openWin(" + i + ")\">" + cnt_irai + "</div></td>";

		//試作No
//		output_html = output_html + "    <td class=\"column\" width=\"130\" align=\"left\"  >" +
		output_html = output_html + "    <td class=\"column\" width=\"125\" align=\"left\"  >" +
				"<div id=\"no_shisaku_" + i + "\" onDblClick=\"openWin(" + i + ")\">" + no_shisaku + "</div></td>";

		//枝番号
//		output_html = output_html + "    <td class=\"column\" width=\"31\"  align=\"right\" >" +
		output_html = output_html + "    <td class=\"column\" width=\"32\"  align=\"right\" >" +
				"<div id=\"no_eda_" + i + "\" onDblClick=\"openWin(" + i + ")\">" + no_eda + "</div></td>";

		//試作名
//		output_html = output_html + "    <td class=\"column\" width=\"193\" align=\"left\"  >" +
		output_html = output_html + "    <td class=\"column\" width=\"188\" align=\"left\"  >" +
				"<input type=\"text\" id=\"nm_shisaku_" + i + "\" style=\"background-color:transparent;width:190;border-width:0px;text-align:left;\" readonly value=\"" + nm_shisaku + "\" onDblClick=\"openWin(" + i + ")\" tabindex=\"-1\" tabindex=\"-1\" /></td>";

		//枝番種類
//		output_html = output_html + "    <td class=\"column\" width=\"41\"  align=\"left\"  >" +
		output_html = output_html + "    <td class=\"column\" width=\"42\"  align=\"left\"  >" +
				"<div id=\"nm_shurui_" + i + "\" onDblClick=\"openWin(" + i + ")\">" + nm_shurui + "</div></td>";

		//試算依頼ｻﾝﾌﾟﾙNo
//		output_html = output_html + "    <td class=\"column\" width=\"97\" align=\"left\"  >" +
		output_html = output_html + "    <td class=\"column\" width=\"96\" align=\"left\"  >" +
//				"<input type=\"text\" id=\"no_iraisample_" + i + "\" style=\"background-color:transparent;width:99;border-width:0px;text-align:left;\" readonly value=\"" + no_iraisample + "\" onDblClick=\"openWin(" + i + ")\" tabindex=\"-1\" tabindex=\"-1\" /></td>";
		"<input type=\"text\" id=\"no_iraisample_" + i + "\" style=\"background-color:transparent;width:96;border-width:0px;text-align:left;\" readonly value=\"" + no_iraisample + "\" onDblClick=\"openWin(" + i + ")\" tabindex=\"-1\" tabindex=\"-1\" /></td>";

		//研究所ステータス
		//output_html = output_html + "    <td class=\"column\" width=\"50\"  align=\"center\"  >" +
		//		"<div id=\"st_kenkyu_" + i + "\" onClick=\"openWin(" + i + ")\">" + funStatusSetting("1",st_kenkyu) + "</div></td>";

		output_html = output_html + "    <td class=\"column\" width=\"50\"  align=\"center\"  ><img src=\"../image/" + funStatusSetting_img("1",st_kenkyu) + "\"></td>";

		//生産管理部ステータス
		//output_html = output_html + "    <td class=\"column\" width=\"50\"  align=\"center\"  >" +
		//		"<div id=\"st_seikan_" + i + "\" onClick=\"openWin(" + i + ")\">" + funStatusSetting("2",st_seikan) + "</div></td>";

		output_html = output_html + "    <td class=\"column\" width=\"50\"  align=\"center\"  ><img src=\"../image/" + funStatusSetting_img("2",st_seikan) + "\"></td>";

		//原資材調達部ステータス
		//output_html = output_html + "    <td class=\"column\" width=\"50\"  align=\"center\"  >" +
		//		"<div id=\"st_gentyo_" + i + "\" onClick=\"openWin(" + i + ")\">" + funStatusSetting("3",st_gentyo) + "</div></td>";

		output_html = output_html + "    <td class=\"column\" width=\"50\"  align=\"center\"  ><img src=\"../image/" + funStatusSetting_img("3",st_gentyo) + "\"></td>";

		//工場ステータス
		//output_html = output_html + "    <td class=\"column\" width=\"50\"  align=\"center\"  >" +
		//		"<div id=\"st_kojo_" + i + "\" onClick=\"openWin(" + i + ")\">" + funStatusSetting("4",st_kojo) + "</div></td>";

		output_html = output_html + "    <td class=\"column\" width=\"50\"  align=\"center\"  ><img src=\"../image/" + funStatusSetting_img("4",st_kojo) + "\"></td>";

		//営業ステータス
		if(st_eigyo == 4){
			//不採用
			if(cd_saiyou == "-1"){
				output_html = output_html + "    <td class=\"column\" width=\"50\"  align=\"center\"  ><img src=\"../image/husaiyo.GIF\"></td>";
			}
			else{
				output_html = output_html + "    <td class=\"column\" width=\"50\"  align=\"center\"  ><img src=\"../image/" + funStatusSetting_img("5",st_eigyo) + "\"></td>";
			}
		}
		else{
			output_html = output_html + "    <td class=\"column\" width=\"50\"  align=\"center\"  ><img src=\"../image/" + funStatusSetting_img("5",st_eigyo) + "\"></td>";
		}

		//採用有無
//		output_html = output_html + "    <td class=\"column\" width=\"95\" align=\"left\"  >" +
		output_html = output_html + "    <td class=\"column\" width=\"97\" align=\"left\"  >" +
				"<input type=\"text\" id=\"no_saiyou_" + i + "\" style=\"background-color:transparent;width:95;border-width:0px;text-align:left;\" readonly value=\"" + no_saiyou + "\" onDblClick=\"openWin(" + i + ")\" tabindex=\"-1\" tabindex=\"-1\" /></td>";

		//チーム名
//		output_html = output_html + "    <td class=\"column\" width=\"101\" align=\"left\"  >" +
		output_html = output_html + "    <td class=\"column\" width=\"99\" align=\"left\"  >" +
				"<div id=\"nm_team_" + i + "\" onDblClick=\"openWin(" + i + ")\">" + nm_team + "</div></td>";

		//担当者名
		output_html = output_html + "    <td class=\"column\" width=\"97\" align=\"left\"  >" +
				"<div id=\"nm_liuser_" + i + "\" onDblClick=\"openWin(" + i + ")\">" + nm_tantosha + "</div></td>";

		//ユーザ名
//		output_html = output_html + "    <td class=\"column\" width=\"199\" align=\"left\"  >" +
		output_html = output_html + "    <td class=\"column\" width=\"191\" align=\"left\"  >" +
				"<div id=\"nm_liuser_" + i + "\" onDblClick=\"openWin(" + i + ")\">" + nm_liuser + "</div></td>";

		//製造会社
//		output_html = output_html + "    <td class=\"column\" width=\"100\" align=\"left\"  >" +
		output_html = output_html + "    <td class=\"column\" width=\"97\" align=\"left\"  >" +
				"<div id=\"nm_kaisha_seizo_" + i + "\" onDblClick=\"openWin(" + i + ")\">" + nm_kaisha_seizo + "</div></td>";

		//製造工場
//		output_html = output_html + "    <td class=\"column\" width=\"100\" align=\"left\"  >" +
		output_html = output_html + "    <td class=\"column\" width=\"96\" align=\"left\"  >" +
				"<div id=\"nm_kaisha_busho_" + i + "\" onDblClick=\"openWin(" + i + ")\">" + nm_kaisha_busho + "</div></td>";

		/*【QP@20505】No.31 2012/09/14 TT H.Shima MOD 担当者列追加によるサイズの調整 End   */

		//原価試算メモ（営業連絡用）
		output_html = output_html + "<input type=\"hidden\" id=\"memo_eigyo_" + i + "\" value=\"" + memo_eigyo + "\">";

		// 【QP@10713】2011.10.28 ADD start hisahori
		//荷姿
		output_html = output_html + "<input type=\"hidden\" id=\"cd_nisugata_" + i + "\" value=\"" + cd_nisugata + "\">";
		//取扱温度
		output_html = output_html + "<input type=\"hidden\" id=\"nm_ondo_" + i + "\" value=\"" + nm_ondo + "\">";
		//製造工場
		output_html = output_html + "<input type=\"hidden\" id=\"nm_kaisha_busho2_" + i + "\" value=\"" + nm_kaisha_busho + "\">";
		//担当営業
		output_html = output_html + "<input type=\"hidden\" id=\"nm_tantoeigyo" + i + "\" value=\"" + nm_tantoeigyo + "\">";
		//研究所担当者（試作No先頭10桁IDのユーザ名）
		output_html = output_html + "<input type=\"hidden\" id=\"nm_tantosha" + i + "\" value=\"" + nm_tantosha + "\">";
		// 【QP@10713】2011.10.28 ADD end

		//採用ｻﾝﾌﾟﾙNo（試作SEQ）
		output_html = output_html + "<input type=\"hidden\" id=\"cd_saiyou_" + i + "\" value=\"" + cd_saiyou + "\">";

		//TR行閉め
		output_html = output_html + "</tr>";
		html = html + output_html;

		//再帰処理（次データのHTML生成）
		setTimeout(function(){ DataSet( xmlResAry , html , (i+1) , cnt ); }, 0);
	}
	else{
		//一覧内にHTML設定
		var obj = document.getElementById("divMeisai");
		html = html + "</table>";
		obj.innerHTML = html;

		//処理待ちウィンドウ非表示
		closeWin_mati();

		//表示終了後に検索系アクションを操作可能
	    document.getElementById("todo").disabled = false;
	    document.getElementById("itiran").disabled = false;
	    document.getElementById("btnSearch").disabled = false;

		xmlResAry = null;
		html = null;

		//処理終了
		return true;
	}
}
function funClearList_search() {

    //一覧のｸﾘｱ
    document.getElementById("divMeisai").innerHTML = "";
    funSetCurrentRow("");
    spnRecInfo.style.display = "none";

}

//========================================================================================
// 一覧選択処理
// 作成者：Y.Nishigawa
// 作成日：2011/2/1
// 引数  ：なし
//========================================================================================
function clickItiran(row){
	funChangeSelectRowColor();
}

//========================================================================================
// 一覧クリア処理
// 作成者：Y.Nishigawa
// 作成日：2011/2/1
// 引数  ：なし
// 概要  ：一覧の情報を初期化する
//========================================================================================
function funClearList() {

    //一覧のｸﾘｱ
    document.getElementById("divMeisai").innerHTML = "";
    funSetCurrentRow("");
    funCreatePageLink(1, 1, "divPage", "tblList");
    funSetCurrentPage(1);
    spnRecInfo.style.display = "none";

}

//========================================================================================
// 処理待ちウィンドウ生成
// 作成者：Y.Nishigawa
// 作成日：2011/02/1
//========================================================================================
var win_mati; //サブウィンドウオブジェクト（原価試算画面内　グローバル変数）
function openWin_mati(){

    //サブウィンドウが生成されていない場合
    if(win_mati == null){

        //サブウィンドウ内HTMLを記述
        outputHtml = "";
        outputHtml = outputHtml + "<table cellpadding=\"0\" width=\"180\" height=\"80\" cellspacing=\"0\" border=\"0\">";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td valign=\"middle\" align=\"center\" style=\"font-size:17pt\"><img src=\"../image/loading.gif\">処理中．．．</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "</table>";

        //サブウィンドウ生成
        win_mati = new Window("win02", {
        			title:"一覧を描画中"
                    ,className: "alphacube"
                    ,top:(window.document.body.clientHeight - 80) / 2
                    ,left:(window.document.body.clientWidth - 180) / 2
                    ,width:180
                    ,height:80
                    ,resizable:false
                    ,minimizable:false
                    ,maximizable:false
                    ,opacity:0.9
                    ,hideEffect:Element.hide
                    ,closable:false
              });

        //サブウィンドウ内にHTMLを設定
        win_mati.setHTMLContent(outputHtml);
    }
}

//========================================================================================
// 処理待ちウィンドウ表示
// 作成者：Y.Nishigawa
// 作成日：2011/02/1
//========================================================================================
function showWin_mati(){
	try{
    	//サブウィンドウを表示
        win_mati.show();
    }
    catch (e) {
    }
}

//========================================================================================
// 処理待ちウィンドウ非表示
// 作成者：Y.Nishigawa
// 作成日：2011/02/1
//========================================================================================
function closeWin_mati(){
	try{
    	//表示されているサブウィンドウを破棄（catch対象）
        win_mati.destroy();
        //サブウィンドウを初期化
        win_mati = null;
        //初期化
        openWin_mati();
    }
    catch (e) {
        //サブウィンドウを初期化
        win_mati = null;
        //初期化
        openWin_mati();
    }
}

//========================================================================================
// 原料詳細情報表示 動作確認
// 作成者：Y.Nishigawa
// 作成日：2010/02/12
// 引数  ：gyoNo(Wクリックされた行番号)
// 戻り値：なし
// 概要  ：原料詳細情報表示
//========================================================================================
var win; //サブウィンドウオブジェクト（原価試算画面内　グローバル変数）
var subwinShowBln;		//サブウィンドウ表示フラグ（原価試算画面内　グローバル変数）
function openWin(wclickgyoNo){

    //※検討事項
    //　サブウィンドウを閉じるのと同時に、素早く再度表示を行った場合にエラーが発生する
    //　エラー発生後はサブウィンドウが表示されなくなる
    //　対策として、原料行の連打防止　etc

    //サブウィンドウが生成されていない場合
    if(win == null){

        //サブウィンドウ内HTML
        var outputHtml;

        var detailDoc = document;		//明細ﾌﾚｰﾑのDocument参照

        var no_shisaku;		//試作No
        var no_eda;			//枝番
        var nm_shisaku;	//試作名
        var no_iraisample;	//依頼ｻﾝﾌﾟﾙNo
        var no_saiyou;		//採用ｻﾝﾌﾟﾙNo
        // 【QP@10713】2011.10.28 ADD start hisahori
        var cd_nisugata;
        cd_nisugata = detailDoc.getElementById("cd_nisugata_" + wclickgyoNo);
        // 【QP@10713】2011.10.28 ADD end
        var memo_eigyo;	//原価試算メモ（営業連絡用）
        var subwinOnCloseEv;	//サブウィンドウクローズ時イベント
        no_shisaku = detailDoc.getElementById("no_shisaku_" + wclickgyoNo);
        nm_shisaku = detailDoc.getElementById("nm_shisaku_" + wclickgyoNo);
        no_eda = detailDoc.getElementById("no_eda_" + wclickgyoNo);
        no_saiyou = detailDoc.getElementById("no_saiyou_" + wclickgyoNo);
        no_iraisample = detailDoc.getElementById("no_iraisample_" + wclickgyoNo);
        memo_eigyo = detailDoc.getElementById("memo_eigyo_" + wclickgyoNo);

        //サブウィンドウ内HTMLを記述
        outputHtml = "";

        outputHtml = outputHtml + "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"100%\">";
        //  【QP@10713】20111101 hagiwara mod start
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">◆試作No（枝番） ：" + no_shisaku.innerHTML + "( " + no_eda.innerHTML + " )</td>";
        outputHtml = outputHtml + "   </tr>";
        /*
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;width:200;\">◆試作No（枝番） ：</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"word-break: break-all;text-align:left;\">" + no_shisaku.innerHTML + "( " + no_eda.innerHTML + " )</td>";
        outputHtml = outputHtml + "   </tr>";
        */
        //  【QP@10713】20111101 hagiwara mod end
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td>&nbsp;</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">◆試作名 ：</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"word-break: break-all;text-align:left;\">" + nm_shisaku.value +"</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td>&nbsp;</td>";
        outputHtml = outputHtml + "   </tr>";
        //  【QP@10713】20111101 hagiwara mod start
        /*
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">◆依頼ｻﾝﾌﾟﾙNo ：</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
		outputHtml = outputHtml + "      <td style=\"word-break: break-all;text-align:left;\">" + no_iraisample.value +"</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td>&nbsp;</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">◆採用ｻﾝﾌﾟﾙNo ：</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"word-break: break-all;text-align:left;\">" + no_saiyou.value +"</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "      <td>&nbsp;</td>";
        outputHtml = outputHtml + "   </tr>";
        */

        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">◆荷姿 ：" + document.getElementById('cd_nisugata_' + wclickgyoNo).value + "</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td>&nbsp;</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">◆取扱い温度 ：" + document.getElementById('nm_ondo_' + wclickgyoNo).value + "</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td>&nbsp;</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">◆製造工場 ：" + document.getElementById('nm_kaisha_busho2_' + wclickgyoNo).value + "</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td>&nbsp;</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">◆担当営業 ：" + document.getElementById('nm_tantoeigyo' + wclickgyoNo).value + "</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td>&nbsp;</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">◆研究所担当者 ：" + document.getElementById('nm_tantosha' + wclickgyoNo).value + "</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td>&nbsp;</td>";
        outputHtml = outputHtml + "   </tr>";
        //  【QP@10713】20111101 hagiwara mod end

        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">◆原価試算メモ（営業連絡用） ：</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"word-break: break-all;text-align:left;\">" + memo_eigyo.value +"</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "</table>";

        //サブウィンドウ生成
        win = new Window("win01", {
                    title: "原価試算情報"
                    ,className: "alphacube"
                    //,top:300+event.y //マウスポインタのY座標（+600調整）
                    //,left:event.x //マウスポインタのX座標（+30調整）
                    ,top:0
                    ,left:0
                    ,width:350
                    ,height:300
                    ,resizable:false
                    ,minimizable:false
                    ,maximizable:false
                    ,opacity:0.9
                    ,hideEffect:Element.hide
              });

        //サブウィンドウ内に「閉じる」ボタンを追加
        win.setDestroyOnClose();

        //サブウィンドウ内にHTMLを設定
        win.setHTMLContent(outputHtml);

        //サブウィンドウクローズ時イベント
		subwinOnCloseEv = {
			onClose: function(eventName, winObj) {
				subwinShowBln = false;
			}
		}

		//サブウィンドウにイベントを設定
		Windows.addObserver(subwinOnCloseEv);

		//サブウィンドウ表示フラグオン
		subwinShowBln = true;

        //サブウィンドウを表示
        win.show();

    }
    //サブウィンドウが生成されている場合
    else{

        try{
            //表示されているサブウィンドウを破棄（catch対象）
            win.destroy();

            //サブウィンドウを初期化
            win = null;

            //自身を呼び出し、サブウィンドウの再表示
            openWin(wclickgyoNo);

        }
        //win=null処理だけでは画面上のサブウィンドウは初期化されずに、
        //win.destroy()実行後にエラーとなる。
        //上記理由により、サブウィンドウが閉じられている場合は、
        //win.destroy()の例外を捕捉し、サブウィンドウの初期化と再表示を行う
        catch (e) {

            //サブウィンドウを初期化
            win = null;

            //自身を呼び出し、サブウィンドウの再表示
            openWin(wclickgyoNo);

        }
    }
}

//========================================================================================
// XMLファイルに書き込み
// 作成者：Y.Nishigawa
// 作成日：2011/01/31
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
        if (XmlId.toString() == "RGEN2120") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA030
                    funXmlWrite(reqAry[i], "cd_category", "K_yuza", 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisanItiran, 0);
                    break;
                case 2:    //SA050
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisanItiran, 0);
                    break;
                case 3:    //SA080
                    funXmlWrite(reqAry[i], "kbn_shori", "2", 0);
                    funXmlWrite(reqAry[i], "cd_group", "", 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisanItiran, 0);
                    break;
                case 4:    //SA140
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisanItiran, 0);
                    break;
                case 5:    //SA290
                    funXmlWrite(reqAry[i], "cd_kaisha", "", 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisanItiran, 0);
                    break;
                case 6:    //FGEN2140
                    break;
                case 7:    //FGEN2130
                    break;
                //【QP@20505】No.31 2012/09/14 TT H.Shima ADD Start
                    // ******** 不要！
                case 8:    //SA250
    	        	funXmlWrite(reqAry[i], "cd_group", "", 0);
                    funXmlWrite(reqAry[i], "cd_team", "", 0);
                    funXmlWrite(reqAry[i], "kbn_shori", "2", 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisanItiran, 0);
                    break;
                //【QP@20505】No.31 2012/09/14 TT H.Shima ADD End
            }
        }
        //ｸﾞﾙｰﾌﾟｺﾝﾎﾞ選択
        else if (XmlId.toString() == "RGEN2130"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA080
                    funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "kbn_shori", "1", 0);
                    funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisanItiran, 0);
                    break;
            }
        }
        //製造会社選択
        else if (XmlId.toString() == "RGEN2140"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA290
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisanItiran, 0);
                    break;
            }
        }
        //検索
        else if (XmlId.toString() == "RGEN2150"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2150
                	//チェックボックス設定
                	var minyuryoku = 0;
                	var kakunin = 0;
                	if(frm.chkMinyuryoku.checked == true){
                		minyuryoku = 1;
                	}
                	if(frm.chkKanryo.checked == true){
                		kakunin = 1;
                	}

                	//XML書き込み
                	funXmlWrite(reqAry[i], "kbn_joken1", flg_tab, 0);
                	funXmlWrite(reqAry[i], "kbn_joken2", minyuryoku, 0);
                	funXmlWrite(reqAry[i], "kbn_joken3", kakunin, 0);
                	funXmlWrite(reqAry[i], "no_shisaku", frm.txtShisakuNo.value, 0);
                	funXmlWrite(reqAry[i], "nm_shisaku", frm.txtShisakuNm.value, 0);
                	funXmlWrite(reqAry[i], "hi_kizitu_from", frm.txtKizitu_From.value, 0);
                	funXmlWrite(reqAry[i], "hi_kizitu_to", frm.txtKizitu_To.value, 0);
                	funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value, 0);
                	funXmlWrite(reqAry[i], "cd_team", frm.ddlTeam.options[frm.ddlTeam.selectedIndex].value, 0);
                	funXmlWrite(reqAry[i], "cd_user", frm.ddlUser.options[frm.ddlUser.selectedIndex].value, 0);
                	funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                	funXmlWrite(reqAry[i], "cd_busho", frm.ddlBusho.options[frm.ddlBusho.selectedIndex].value, 0);
                	funXmlWrite(reqAry[i], "busho_zyokyou", frm.ddlZyokyo.options[frm.ddlZyokyo.selectedIndex].value, 0);
                	funXmlWrite(reqAry[i], "status", frm.ddlStatus.options[frm.ddlStatus.selectedIndex].value, 0);
                	funXmlWrite(reqAry[i], "eda_shurui", frm.ddlShurui.options[frm.ddlShurui.selectedIndex].value, 0);
                	funXmlWrite(reqAry[i], "no_page", funGetCurrentPage(), 0);
                	//【QP@20505】No.31 2012/09/14 TT H.Shima ADD Start
                	funXmlWrite(reqAry[i], "cd_tanto", frm.ddlTanto.options[frm.ddlTanto.selectedIndex].value,0);
                	//【QP@20505】No.31 2012/09/14 TT H.Shima ADD End
                    break;
            }
        }

        //原価試算画面起動情報通知
        else if (XmlId.toString() == "RGEN2160"){
            // XMLより試作コード取得
            var obj_no_shisaku = document.getElementById("no_shisaku_" + funGetCurrentRow());
            var no_shisaku = obj_no_shisaku.innerHTML;
            var obj_no_eda = document.getElementById("no_eda_" + funGetCurrentRow());
            var no_eda = obj_no_eda.innerHTML;

            var put_shisaku = no_shisaku + "-" + no_eda;

            // 試作コード置換【社員CD::年:::追番:::枝番】
            var put_shisaku = put_shisaku.replace(/-/g,":::");

            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", put_shisaku, 0);
                    break;
            }
        }

        //【QP@20505】No.31 2012/09/14 TT H.Shima ADD Start
        else if (XmlId.toString() == "JSP0320"){
            switch (i) {
	        case 0:    //USERINFO
	            funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
	            funXmlWrite(reqAry[i], "id_user", "", 0);
	            break;
	        case 1:
	        	funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value, 0);
                funXmlWrite(reqAry[i], "cd_team", frm.ddlTeam.options[frm.ddlTeam.selectedIndex].value, 0);
                funXmlWrite(reqAry[i], "kbn_shori", "1", 0);
                funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisanItiran, 0);
             // ADD 2015/03/30 TT.Kitazawa【QP@40812】No.24 start
                // 研担当者コンボボックス設定：選択したグループの会社CD（空行除く）
                funXmlWrite(reqAry[i], "cd_kaisha", funXmlRead(xmlSA050, "cd_kaisha",frm.ddlGroup.selectedIndex-1), 0);
             // ADD 2015/03/30 TT.Kitazawa【QP@40812】No.24 end
                break;
            }
        }
        //【QP@20505】No.31 2012/09/14 TT H.Shima ADD End

		//【QP@20505】No.39 2012/09/20 TT H.Shima ADD Start
        else if (XmlId.toString() == "RGEN2220"){
        	switch (i) {
	        case 0:    //USERINFO
	            funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
	            funXmlWrite(reqAry[i], "id_user", "", 0);
	            break;
	        case 1:
	            var obj_no_shisaku = document.getElementById("no_shisaku_" + funGetCurrentRow());
	            var no_shisaku = obj_no_shisaku.innerHTML;
	            var obj_no_eda = document.getElementById("no_eda_" + funGetCurrentRow());
	            var no_eda = obj_no_eda.innerHTML;

	            var put_shisaku = no_shisaku + "-" + no_eda;

	            var shisaku = put_shisaku.split("-");

	            funXmlWrite(reqAry[i], "cd_shain", shisaku[0], 0);
	            funXmlWrite(reqAry[i], "nen", shisaku[1], 0);
	            funXmlWrite(reqAry[i], "no_oi", shisaku[2], 0);
	            funXmlWrite(reqAry[i], "no_eda", shisaku[3], 0);

	            funXmlWrite(reqAry[i], "cd_shisaku", put_shisaku, 0);
                funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisan, 0);
                funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
	        	break;
        	}
        }

        else if (XmlId.toString() == "RGEN2221"){
        	switch (i) {
	        case 0:    //USERINFO
	            funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
	            funXmlWrite(reqAry[i], "id_user", "", 0);
	            break;
	        case 1:
	            var obj_no_shisaku = document.getElementById("no_shisaku_" + funGetCurrentRow());
	            var no_shisaku = obj_no_shisaku.innerHTML;
	            var obj_no_eda = document.getElementById("no_eda_" + funGetCurrentRow());
	            var no_eda = obj_no_eda.innerHTML;

	            var put_shisaku = no_shisaku + "-" + no_eda;

	            var shisaku = put_shisaku.split("-");

	            funXmlWrite(reqAry[i], "cd_shain", shisaku[0], 0);
	            funXmlWrite(reqAry[i], "nen", shisaku[1], 0);
	            funXmlWrite(reqAry[i], "no_oi", shisaku[2], 0);
	            funXmlWrite(reqAry[i], "no_eda", shisaku[3], 0);

                funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisanEigyo, 0);
                funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
	        	break;
        	}
        }
        // ADD 2013/10/22 QP@30154 okano start
        else if (XmlId.toString() == "RGEN2230"){
        	switch (i) {
	        case 0:    //USERINFO
	            funXmlWrite(reqAry[i], "kbn_shori", 1, 0);
	            funXmlWrite(reqAry[i], "id_user", "", 0);
	            break;
	        case 1:
	            var obj_no_shisaku = document.getElementById("no_shisaku_" + funGetCurrentRow());
	            var no_shisaku = obj_no_shisaku.innerHTML;
	            var obj_no_eda = document.getElementById("no_eda_" + funGetCurrentRow());
	            var no_eda = obj_no_eda.innerHTML;

	            var put_shisaku = no_shisaku + "-" + no_eda;

	            var shisaku = put_shisaku.split("-");

	            funXmlWrite(reqAry[i], "no_shisaku", put_shisaku, 0);
	            funXmlWrite(reqAry[i], "cd_shain", shisaku[0], 0);
	            funXmlWrite(reqAry[i], "nen", shisaku[1], 0);
	            funXmlWrite(reqAry[i], "no_oi", shisaku[2], 0);
	            funXmlWrite(reqAry[i], "no_eda", shisaku[3], 0);

                funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisanEigyo, 0);
                funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                funXmlWrite(reqAry[i], "mode", mode, 0);
	        	break;
        	}
        }
        // ADD 2013/10/22 QP@30154 okano end
      //【QP@20505】No.39 2012/09/20 TT H.Shima ADD Start
    }
    return true;
}

//========================================================================================
// コンボボックス作成処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/31
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
        case 1:    //ｸﾞﾙｰﾌﾟﾏｽﾀ
            atbName = "nm_group";
            atbCd = "cd_group";
            break;
        case 2:    //ﾁｰﾑﾏｽﾀ
            atbName = "nm_team";
            atbCd = "cd_team";
            break;
        case 3:    //ﾕｰｻﾞｰﾏｽﾀ
            atbName = "nm_user";
            atbCd = "id_user";
            break;
        case 4:    //ﾘﾃﾗﾙﾏｽﾀ
            atbName = "nm_literal";
            atbCd = "cd_literal";
            break;
        case 5:    //会社マスタ
            atbName = "nm_kaisha";
            atbCd = "cd_kaisha";
            break;
        case 6:    //部署マスタ
            atbName = "nm_busho";
            atbCd = "cd_busho";
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
// 権限情報退避
// 作成者：Y.Nishigawa
// 作成日：2011/01/31
// 概要  ：権限情報の退避を行う
//========================================================================================
function funSaveKengenInfo() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var obj = xmlUSERINFO_O;
    var GamenId;
    var KinoId;
    var DataId;
    var reccnt;
    var i;

    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        GamenId = funXmlRead(obj, "id_gamen", i);
        KinoId = funXmlRead(obj, "id_kino", i);
        DataId = funXmlRead(obj, "id_data", i);

        //原価試算一覧画面
        if (GamenId.toString() == ConGmnIdGenkaShisanItiran.toString()) {
            hidKinoId = KinoId;
			hidDataId = DataId;
        }
        //原価試算画面
        if (GamenId.toString() == ConGmnIdGenkaShisan.toString()) {
        	document.getElementById("btnGenka").style.visibility = "visible";
        }
        //原価試算（営業）画面
        if (GamenId.toString() == ConGmnIdGenkaShisanEigyo.toString()) {
	        document.getElementById("btnGenka_Eigyo").style.visibility = "visible";
        }

    }
    return true;

}

//========================================================================================
// ページ遷移
// 作成者：Y.Nishigawa
// 作成日：2011/01/31
// 引数  ：①NextPage   ：次のページ番号
// 戻り値：なし
// 概要  ：指定ページの情報を表示する。
//========================================================================================
function funPageMove(NextPage) {

    //次ﾍﾟｰｼﾞの設定
    funSetCurrentPage(NextPage);

    //指定ﾍﾟｰｼﾞのﾃﾞｰﾀ取得
    funDataSearch();
}

//========================================================================================
// クリアボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/03/25
// 引数  ：なし
// 概要  ：画面を初期化する
//========================================================================================
function funClear() {

	//検索条件クリア
    funClear_zyoken();

    //一覧のｸﾘｱ
    funClearList();

    //***** ADD【H24年度対応】20120416 hagiwara S **********
    // 印刷処理を不可能にするため検索数を初期化
    search_cnt = 0;
    //***** ADD【H24年度対応】20120416 hagiwara E **********

    return true;

}

//========================================================================================
// 検索条件クリアボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/03/25
// 引数  ：なし
// 概要  ：画面を初期化する
//========================================================================================
function funClear_zyoken() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //画面の初期化
    frm.reset();

// ADD start 20120705 hisahori
	// 確認完了チェックボックス、試算期日テキストボックスの活性化
		frm.txtKizitu_From.disabled=false;
		frm.txtKizitu_From.style.backgroundColor="#ffffff";
		frm.txtKizitu_To.disabled=false;
		frm.txtKizitu_To.style.backgroundColor="#ffffff";;
		frm.chkKanryo.disabled = false;
// ADD end 20120705 hisahori

    //チームコンボボックス
	//DEL 2013/10/22 QP@30154 okano start
//	    //権限が「同一グループ且つ（本人＋チームリーダー以上）」の場合
//
//	    if(hidDataId == "1"){
//	    	//ｺﾝﾎﾞﾎﾞｯｸｽの再設定
//		    funDefaultIndex(frm.ddlGroup, 1);
//		    funDefaultIndex(frm.ddlTeam, 2);
//	//【QP@20505】No.31 2012/09/18 TT H.Shima ADD Start
//		    funChangeTeam();
//		    funDefaultIndex(frm.ddlTanto, 3);
//	//【QP@20505】No.31 2012/09/18 TT H.Shima ADD End
//	//  【QP@10713】2011.10.27 ADD Start hisahori
//		    frm.txtShisakuNo.value = hidUserId;
//	//  【QP@10713】2011.10.27 ADD End
//	    }
//	    //権限が「同一グループ且つ（本人＋チームリーダー以上）」以外の場合
//	    else{
    //DEL 2013/10/22 QP@30154 okano end
    	funClearSelect(frm.ddlTeam, 2);
//【QP@20505】No.31 2012/09/18 TT H.Shima ADD Start
    	funClearSelect(frm.ddlTanto, 2);
//【QP@20505】No.31 2012/09/18 TT H.Shima ADD End
    //DEL 2013/10/22 QP@30154 okano start
//    }
	//DEL 2013/10/22 QP@30154 okano end

    //部署コンボボックス
    //権限が「自分工場のみ」の場合
    if(hidDataId == "2"){

    }
    //権限が「自分工場のみ」以外の場合
    else{
    	funClearSelect(frm.ddlBusho, 2);
    }

    //ステータスコンボボックス
    funClearSelect(frm.ddlStatus, 2);

}

//========================================================================================
// 次画面遷移処理
// 作成者：M.Jinbo
// 作成日：2009/03/25
// 概要  ：次画面に遷移する
//========================================================================================
function funNext(mode) {

    var wUrl;

    //遷移先判定
    switch (mode) {
        case 0:    //メインメニュー
            wUrl = "../SQ020MainMenu/SQ020MainMenu.jsp";
            break;
    }

    //遷移
    funUrlConnect(wUrl, ConConectPost, document.frm00);

    return true;
}

//========================================================================================
// 原価試算画面へ遷移
// 作成者：Y.Nishigawa
// 作成日：2009/10/20
// 引数  ：なし
// 概要  ：原価試算画面へ遷移する
//========================================================================================
function funNextPage(mode) {

    //選択されていない場合
    if(funGetCurrentRow().toString() == ""){
    	funErrorMsgBox(E000002);
        return false;
    }
    //選択されている場合
    else{
    	//原価試算画面起動情報通知に成功
        if(funGenkaTuti(1)){

        	//【QP@20505】No.39 2012/09/20 TT H.Shima ADD Start
	    	funAccessLogSave(mode);
	    	//【QP@20505】No.39 2012/09/20 TT H.Shima ADD End

        	//原価試算画面
	    	if(mode == 1){
	    		//原価試算画面へ遷移
	    		window.open("../SQ110GenkaShisan/GenkaShisan.jsp","shisaquick_genka","menubar=no,resizable=yes");
	            //window.open("../SQ110GenkaShisan/GenkaShisan.jsp","shisaquick_genka","menubar=yes,resizable=yes");
	    	}
	    	//原価試算画面（営業）
	    	else if(mode == 2){
	    		//原価試算画面（営業）へ遷移
	            window.open("../SQ160GenkaShisan_Eigyo/GenkaShisan_Eigyo.jsp","_blank","menubar=no,resizable=yes");
	            //window.open("../SQ160GenkaShisan_Eigyo/GenkaShisan_Eigyo.jsp","_blank","menubar=yes,resizable=yes");
	    	}
	    	//ステータス履歴
	    	else if(mode == 3){
	    		//ステータス履歴へ遷移
	            window.open("../SQ140StatusRireki/SQ140StatusRireki.jsp","_blank","menubar=no,resizable=yes");
	    	}
        }
        //原価試算画面起動情報通知に失敗
        else{

        }
    }

    return true;

}

//========================================================================================
// 原価試算画面起動情報通知
// 作成者：Y.Nishigawa
// 作成日：2009/10/27
// 引数  ：なし
// 概要  ：選択した試作コードをセッションへ保存する
//========================================================================================
function funGenkaTuti(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN2160";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2160, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    return true;

}

//========================================================================================
// 空白時の設定
// 作成者：Y.Nishigawa
// 作成日：2011/01/31
//========================================================================================
function funSetNbsp(val) {

    if( val == "" || val == "NULL" ){
    	val = "&nbsp;";
    }

    return val;
}
function funSetNbsp_kizitu(val) {

    if( val == "" || val == "未定" ){
    	val = "&nbsp;";
    }

    return val;
}

//========================================================================================
// HTML文字エスケープ
// 作成者：Y.Nishigawa
// 作成日：2011/01/31
//========================================================================================
function funEscape(val) {

    return val.replace(/<|>|&|'|"|\s/g, function(s){
    	var map = {"<":"&lt;", ">":"&gt;", "&":"&amp;", "'":"&#39;", "\"":"&quot;", " ":"&nbsp;"};
    	return map[s];
	});
}


//========================================================================================
// デフォルト値選択処理
// 作成者：M.Jinbo
// 作成日：2009/03/25
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②mode     ：モード
// 概要  ：コンボボックスのデフォルト値を選択する
//========================================================================================
function funDefaultIndex(obj, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var selIndex;
    var i;

    //
    for (i = 0; i < obj.length; i++) {
        switch (mode) {
            case 1:    //ｸﾞﾙｰﾌﾟｺﾝﾎﾞ
                if (obj.options[i].value == funXmlRead(xmlUSERINFO_O, "cd_group", 0)) {
                    selIndex = i;
                }
                break;
            case 2:    //ﾁｰﾑｺﾝﾎﾞ
                if (obj.options[i].value == funXmlRead(xmlUSERINFO_O, "cd_team", 0)) {
                    selIndex = i;
                }
                break;
            //【QP@20505】No.31 2012/09/14 TT H.Shima ADD Start
            case 3:
            	if (obj.options[i].value == funXmlRead(xmlUSERINFO_O, "id_user", 0)) {
                    selIndex = i;
                }
                break;
            //【QP@20505】No.31 2012/09/14 TT H.Shima ADD End
        }
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽのｲﾝﾃﾞｯｸｽ指定
    obj.selectedIndex = selIndex;

    return true;
}

//========================================================================================
//「印刷」ボタンクリックイベント
// 作成者：Ryo.Hagiwara
// 作成日：2012/04/02
// 引数  ：なし
// 概要  ：現在一覧に出力されているデータをもとにExcelファイルを出力する。
//========================================================================================
function funOutput() {

	// 選択されていない場合
    if(search_cnt <= 0){
    	funErrorMsgBox(E000023);
        return false;
    }

    //--------------------------------------------------
    var frm = document.frm00;    // ﾌｫｰﾑへの参照
    var XmlId = "RGEN2210";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2210");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2210I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2210O);

    // 検索条件に一致する試作ﾃﾞｰﾀを取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2210, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    // ﾌｧｲﾙﾊﾟｽの退避
    frm.strFilePath.value = funXmlRead(xmlFGEN2210O, "URLValue", 0);

    // ﾀﾞｳﾝﾛｰﾄﾞ画面の起動
    funFileDownloadUrlConnect(ConConectGet, frm);

    FuncIdAry = null;
    xmlReqAry = null;

	return true;

}

//========================================================================================
// 画面参照ログの追加
// 作成者：H.Shima
// 作成日：2012/09/20
// 引数  ：mode     ：モード  1,原価試算 2,原価試算(営業) 3,ステータス履歴
// 概要  ：参照画面と参照ユーザのログを保存する
//========================================================================================
function funAccessLogSave(mode) {

	var _XmlId = new Array("RGEN2220", "RGEN2221","");
	var XmlId = _XmlId[mode - 1];
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2220");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2220I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2220O);

	//引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}

	switch(mode){
	case 1:
	case 2:
		if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2220, xmlReqAry, xmlResAry, 1) == false) {
			return false;
		}
	case 3:
		break;

	}
}

// ADD 2013/10/22 QP@30154 okano  start
//========================================================================================
//原価試算画面へ遷移
//作成者：R.Okano
//作成日：2013/10/24
//引数  ：mode     ：モード  1,原価試算 2,原価試算(営業)
//概要  ：参照ユーザの閲覧権限にて処理を判定する
//========================================================================================
function funGenkaOpen(mode){
	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	var XmlId = "RGEN2230";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2230");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2230I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2230O);

	// ADD H.Shima Start
	//選択されていない場合
    if(funGetCurrentRow().toString() == ""){
    	funErrorMsgBox(E000002);
        return false;
    }
    // ADD H.Shima End

	//引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
	    return false;
	}

	//ユーザ情報を取得
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2230, xmlReqAry, xmlResAry, 1) == false) {
	    return false;
	}

	//原価試算画面
	funNextPage(mode);
}
// ADD 2013/10/22 QP@30154 okano end
