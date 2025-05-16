
//========================================================================================
// 共通変数
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
//========================================================================================
//仮登録ユーザモードを保持
var hidKariMode = "";
var hidKengenIppan = "";
var hidKengenHonbu = "";
var hidKengenSystem = "";

// 【QP@10713】2011/10/28 TT H.SHIMA -ADD Start
var hidEigyoKengen = "";
// 【QP@10713】2011/10/28 TT H.SHIMA -ADD End


//========================================================================================
// 【QP@00342】初期表示処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

    //画面設定
    funInitScreen(ConEigyoTantoMstId);

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //画面情報を取得・設定
    if (funGetInfo(1) == false) {
        return false;
    }

    //ドロップダウンリストをReadOnlyに設定
    funDdlReadOnly();

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    return true;

}

//========================================================================================
// 画面情報取得処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①mode  ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：ユーザのセッション情報、コンボボックスの情報を取得する
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN2040";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2040","FGEN2050","FGEN2120");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2040I,xmlFGEN2050I,xmlFGEN2120I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2040O,xmlFGEN2050O,xmlFGEN2120O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2040, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }


    //【USERINFO】ﾕｰｻﾞ情報表示
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");


    //【FGEN2040】権限コンボボックス設定
    createKengenCombo(xmlResAry[2]);

    //【FGEN2050】所属会社コンボボックス設定
    //ループカウント取得
    var roop_cnt = funXmlRead_3(xmlResAry[3], "table", "roop_cnt", 0, 0);
    //コンボボックス生成
    for(var i=0 ; i<roop_cnt; i++){
        //権限コード、権限名取得
        var cd_kaisha = funXmlRead_3(xmlResAry[3], "table", "cd_kaisha", 0, i);
        var nm_kaisha = funXmlRead_3(xmlResAry[3], "table", "nm_kaisha", 0, i);

        //空白行設定
        if(i==0){
            objNewOption = document.createElement("option");
		    frm.ddlKaisha.options.add(objNewOption);
		    objNewOption.innerText = "";
		    objNewOption.value = "";
        }

        //コンボボックス生成
        objNewOption = document.createElement("option");
        frm.ddlKaisha.options.add(objNewOption);
        objNewOption.innerText = nm_kaisha;
        objNewOption.value = cd_kaisha;
    }

    //部署コンボボックス　空白行設定
    objNewOption = document.createElement("option");
	frm.ddlBusho.options.add(objNewOption);
	objNewOption.innerText = "";
	objNewOption.value = "";


    //【FGEN2120】ユーザID設定
    //ユーザID設定
    frm.txtUserId.value=funXmlRead_3(xmlResAry[4], "table", "id_user", 0, 0);
    hidKariMode=funXmlRead_3(xmlResAry[4], "table", "kbn_kari", 0, 0);

    //権限関連の処理を行う
    funSaveKengenInfo();

    // ADD 2013/9/25 okano【QP@30151】No.28 start
    if(hidKariMode == "1"){
        funSearch();
    }
    // ADD 2013/9/25 okano【QP@30151】No.28 end

    return true;

}

//========================================================================================
// 権限コンボボックス生成
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：XMLデータ
//========================================================================================
function createKengenCombo(xmlData){

	var frm = document.frm00;    //ﾌｫｰﾑへの参照

	//ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
    funClearSelect(frm.ddlKengen, 2);

    //ループカウント取得
    var roop_cnt = funXmlRead_3(xmlData, "table", "roop_cnt", 0, 0);

    //コンボボックス生成
    for(var i=0 ; i<roop_cnt; i++){
        //権限コード、権限名取得
        var cd_kengen = funXmlRead_3(xmlData, "table", "cd_kengen", 0, i);
        var nm_kengen = funXmlRead_3(xmlData, "table", "nm_kengen", 0, i);

        //コンボボックス生成
    	objNewOption = document.createElement("option");
        frm.ddlKengen.options.add(objNewOption);
        objNewOption.innerText = nm_kengen;
        objNewOption.value = cd_kengen;
    }
    hidKengenIppan = funXmlRead_3(xmlData, "table", "cd_kengen_ippan", 0, 0);
	hidKengenHonbu = funXmlRead_3(xmlData, "table", "cd_kengen_honbu", 0, 0);
	hidKengenSystem = funXmlRead_3(xmlData, "table", "cd_kengen_system", 0, 0);
}

//========================================================================================
// 権限コンボボックス変更
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：なし
//========================================================================================
function funChangeKengen() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    // MOD 2013/9/25 okano【QP@30151】No.28 start
//    //選択権限取得
//    var cd_kengen_sentaku = frm.ddlKengen.options[frm.ddlKengen.selectedIndex].value;
//
//// 【QP@10713】2011/10/28 TT H.SHIMA -DEL Start
//    //使用可否設定
////    if( cd_kengen_sentaku == hidKengenIppan ){
////    	//営業（一般）権限の場合は使用可能
////    	frm.btnJosiSearch.disabled=false;
////    	frm.btnJosiDel.disabled=false;
////    }
////    else{
////    	//営業（一般）権限以外の場合は使用不可
////    	frm.btnJosiSearch.disabled=true;
////    	frm.btnJosiDel.disabled=true;
////    	funDelJosi();
////    }
//// 【QP@10713】2011/10/28 TT H.SHIMA -DEL End
//
//// 【QP@10713】2011/10/28 TT H.SHIMA -ADD Start
//    switch(cd_kengen_sentaku){
//    case hidKengenIppan :
//    	frm.btnJosiSearch.disabled=false;
//    	frm.btnJosiDel.disabled=false;
//    	hidEigyoKengen = "1";
//    	break;
//    case hidKengenHonbu :
//    case hidKengenSystem :
//    	hidEigyoKengen = "2";
//    default :
//    	frm.btnJosiSearch.disabled=true;
//    	frm.btnJosiDel.disabled=true;
//    	funDelJosi();
//    }
    if(hidMode.value == ConFuncIdEigyoTantoEditPass.toString() || hidMode.value == ConFuncIdEigyoTantoEditCash.toString()){
    //  2015/03/03 MOD start TT.Kitazawa【QP@40812】No.19
//    	frm.btnJosiSearch.disabled=true;
//    	frm.btnJosiDel.disabled=true;

    	frm.btnAddList.disabled=true;
        frm.btnDelList.disabled=true;
    //  2015/03/03 MOD end TT.Kitazawa【QP@40812】No.19
   } else {
	    //選択権限取得
	    var cd_kengen_sentaku = frm.ddlKengen.options[frm.ddlKengen.selectedIndex].value;

	    switch(cd_kengen_sentaku){
	    case hidKengenIppan :
	    //  2015/03/03 MOD start TT.Kitazawa【QP@40812】No.19
//	    	frm.btnJosiSearch.disabled=false;
//	    	frm.btnJosiDel.disabled=false;

	        frm.btnAddList.disabled=false;
	        frm.btnDelList.disabled=false;
	    //  2015/03/03 MOD end TT.Kitazawa【QP@40812】No.19

	        hidEigyoKengen = "1";
	    	break;
	    case hidKengenHonbu :
	    case hidKengenSystem :
	    	hidEigyoKengen = "2";
	    default :
	    //  2015/03/03 MOD start TT.Kitazawa【QP@40812】No.19
//	    	frm.btnJosiSearch.disabled=true;
//	    	frm.btnJosiDel.disabled=true;
//	    	funDelJosi();

	        frm.btnAddList.disabled=true;
	        frm.btnDelList.disabled=true;
	    //  2015/03/03 MOD end TT.Kitazawa【QP@40812】No.19
	    }
    }
    // MOD 2013/9/25 okano【QP@30151】No.28 end

    funChangeKaisha();

// 【QP@10713】2011/10/28 TT H.SHIMA -ADD End


    return true;

}

//========================================================================================
// クリアボタン押下処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：なし
// 概要  ：画面を初期化する
//========================================================================================
function funClear() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //初期表示
    var XmlId = "RGEN2040";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2040");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2040I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2040O);
    var mode = 1;

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2040, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //【FGEN2040】権限コンボボックス設定
    createKengenCombo(xmlResAry[2]);

    //2015/03/03 MOD start TT.Kitazawa【QP@40812】No.19
	// 共有メンバー：担当者データのクリア
	xmlFGEN2060O.src = "";
    //2015/03/03 MOD end TT.Kitazawa【QP@40812】No.19

    //画面の初期化
    frm.reset();

    //2015/03/03 MOD start TT.Kitazawa【QP@40812】No.19
//    frm.btnJosiSearch.disabled=false;
//    frm.btnJosiDel.disabled=false;

    frm.btnAddList.disabled=false;
    frm.btnDelList.disabled=false;
    tblList.style.display = "none";
    //2015/03/03 MOD end TT.Kitazawa【QP@40812】No.19

    //2012/03/02 TT H.SHIMA Java6対応 add Start
    funDdlReadOnly();
    //2012/03/02 TT H.SHIMA Java6対応 add End

    //ﾌｫｰｶｽ設定
    frm.txtUserId.focus();

    return true;

}


//========================================================================================
// ユーザIDロストフォーカス処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：なし
// 概要  ：ユーザIDに紐付くデータを取得し表示する
//========================================================================================
function funSearch() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    var XmlId = "RGEN2050";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2060","FGEN2040");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2060I,xmlFGEN2040I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2060O,xmlFGEN2040O);

    //ﾕｰｻﾞIDが未入力の場合
    if (frm.txtUserId.value == "") {
        funClear();
        return true;
    }

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //ﾕｰｻﾞ情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2050, xmlReqAry, xmlResAry, 1) == false) {
        //ユーザID初期化
        frm.txtUserId.value = "";

        //2012/03/02 TT H.SHIMA Java6対応 add Start
        funDdlReadOnly();
        //2012/03/02 TT H.SHIMA Java6対応 add End

        //共有メンバー非表示
        tblList.style.display = "none";
        return false;
    }

    //【FGEN2040】権限コンボボックス設定
    createKengenCombo(xmlResAry[3]);

    //値の設定
    funSetData();

//  2015/03/03 ADD start TT.Kitazawa【QP@40812】No.19
    //共有メンバー件数のﾁｪｯｸ
    if (funGetLength(xmlResAry[2]) > 0 && funXmlRead(xmlResAry[2], "flg_return", 0) == "true" && funXmlRead(xmlResAry[2], "id_member", 0) != "") {
        //表示
        tblList.style.display = "block";
    } else {
        //非表示
        tblList.style.display = "none";
    }
//  2015/03/03 ADD end TT.Kitazawa【QP@40812】No.19

    //【QP@10713】シサクイック改良 No.25
    //部署コード設定
    //funChangeKaisha();
    funChangeKengen();
    funDefaultIndex(frm.ddlBusho, 3);
 //2014/05/05 add start 課題No.30 システム管理者は、会社 部署コンボ選択不可
//    if (hidMode.value.toString() == ConFuncIdEigyoTantoEditHonbu.toString()) {
//    }
//    else
    if (hidMode.value.toString() == ConFuncIdEigyoTantoEditSystem.toString()) {
    // 2015/06/01 ADD start TT.Kitazawa【QP@40812】
    // システム管理者、及び仮登録ユーザはコンボ選択可に
    } else if (ConFuncIdEigyoTantoEditKari == hidMode.value) {
    	// 仮登録ユーザ：既存IDを入力時はコンボ選択不可、共有「追加」「削除」
    	if (funXmlRead(xmlResAry[2], "nm_user", 0) != "") {
    		funDdlReadOnly();
    	    funItemReadOnly(document.frm00.btnAddList, true);
    	    funItemReadOnly(document.frm00.btnDelList, true);
    	}
    // 2015/06/01 ADD end TT.Kitazawa【QP@40812】
    }
    else {
        funDdlReadOnly();
    }
 //2014/05/05 add end 課題No.30

    return true;

}

//========================================================================================
// 取得データ設定処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：なし
// 戻り値：なし
// 概要  ：取得した担当者情報を画面に設定する
//========================================================================================
function funSetData() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //2012/03/02 TT H.SHIMA Java6対応 add Start
    funDdlReadOnly();
    //2012/03/02 TT H.SHIMA Java6対応 add End

    //値の設定
    frm.txtPass.value = funXmlRead(xmlFGEN2060O, "password", 0);
    funDefaultIndex(frm.ddlKengen, 1);
    frm.txtUserName.value = funXmlRead(xmlFGEN2060O, "nm_user", 0);
    funDefaultIndex(frm.ddlKaisha, 2);
//  2015/03/03 DEL start TT.Kitazawa【QP@40812】No.19
//    frm.txtUserNameJosi.value = funXmlRead(xmlFGEN2060O, "nm_josi", 0);
//    frm.hdnUserNameJosi.value = funXmlRead(xmlFGEN2060O, "id_josi", 0);
//  2015/03/03 DEL end TT.Kitazawa【QP@40812】No.19

    //【QP@10713】シサクイック改良 No.25
    //var kengen_ippan = funXmlRead(xmlFGEN2060O, "kengen_ippan", 0);
    var eigyo_kengen = funXmlRead(xmlFGEN2060O, "eigyo_kengen", 0);
    //if(kengen_ippan == "0"){
    // MOD 2013/9/25 okano【QP@30151】No.28 start
//	    if(eigyo_kengen == "0"){
//	    	frm.btnJosiSearch.disabled=true;
//	    	frm.btnJosiDel.disabled=true;
//	    }
//	    else {
//			frm.btnJosiSearch.disabled=false;
//	    	frm.btnJosiDel.disabled=false;
//
//	    	//2012/03/02 TT H.SHIMA Java6対応 add Start
//	    	funItemReadOnly(frm.txtPass, false);
//		    funItemReadOnly(frm.ddlKengen, false);
//		    funItemReadOnly(frm.txtUserName, false);
//		    funItemReadOnly(frm.ddlKaisha, false);
//		    funItemReadOnly(frm.ddlBusho, false);
//		    //2012/03/02 TT H.SHIMA Java6対応 add End
//	    }
    if(hidMode.value == ConFuncIdEigyoTantoEditPass.toString()){
    	funItemReadOnly(frm.txtUserId, true);
	    funItemReadOnly(frm.ddlKengen, true);
	    funItemReadOnly(frm.ddlKaisha, true);
	    funItemReadOnly(frm.ddlBusho, true);
//  2015/03/03 DEL start TT.Kitazawa【QP@40812】No.19
//    	frm.btnJosiSearch.disabled=true;
//    	frm.btnJosiDel.disabled=true;
//  2015/03/03 DEL end TT.Kitazawa【QP@40812】No.19
    	frm.btnInsert.disabled=true;
    	frm.btnUpdate.disabled=false;
    	frm.btnDelete.disabled=true;
    	frm.btnClear.disabled=true;
    	frm.btnEnd.disabled=false;
    	frm.btnSearchUser.disabled=true;
    	frm.btnMenu.disabled=true;

//  2015/03/03 ADD start TT.Kitazawa【QP@40812】No.19
        frm.btnAddList.disabled=true;
        frm.btnDelList.disabled=true;
//  2015/03/03 ADD end TT.Kitazawa【QP@40812】No.19

    } else if(hidMode.value == ConFuncIdEigyoTantoEditCash.toString()){
    	funItemReadOnly(frm.txtUserId, true);
	    funItemReadOnly(frm.ddlKengen, true);
	    funItemReadOnly(frm.ddlKaisha, true);
	    funItemReadOnly(frm.ddlBusho, true);
//  2015/03/03 DEL start TT.Kitazawa【QP@40812】No.19
//    	frm.btnJosiSearch.disabled=true;
//    	frm.btnJosiDel.disabled=true;
//  2015/03/03 DEL end TT.Kitazawa【QP@40812】No.19
    	frm.btnInsert.disabled=false;
    	frm.btnUpdate.disabled=true;
    	frm.btnDelete.disabled=true;
    	frm.btnClear.disabled=true;
    	frm.btnEnd.disabled=false;
    	frm.btnSearchUser.disabled=true;
    	frm.btnMenu.disabled=true;

//  2015/03/03 ADD start TT.Kitazawa【QP@40812】No.19
    	frm.btnAddList.disabled=true;
        frm.btnDelList.disabled=true;
//  2015/03/03 ADD end TT.Kitazawa【QP@40812】No.19

    }else{
	    if(eigyo_kengen == "0" || eigyo_kengen == "3" ){
	    //  2015/03/03 MOD start TT.Kitazawa【QP@40812】No.19
//	    	frm.btnJosiSearch.disabled=true;
//	    	frm.btnJosiDel.disabled=true;

	        frm.btnAddList.disabled=true;
	        frm.btnDelList.disabled=true;
	    //  2015/03/03 MOD end TT.Kitazawa【QP@40812】No.19
	    }
	    else {
	    //  2015/03/03 MOD start TT.Kitazawa【QP@40812】No.19
//			frm.btnJosiSearch.disabled=false;
//	    	frm.btnJosiDel.disabled=false;

	        frm.btnAddList.disabled=false;
	        frm.btnDelList.disabled=false;
	        //  2015/03/03 MOD end TT.Kitazawa【QP@40812】No.19

	    	//2012/03/02 TT H.SHIMA Java6対応 add Start
	    	funItemReadOnly(frm.txtPass, false);
		    funItemReadOnly(frm.ddlKengen, false);
		    funItemReadOnly(frm.txtUserName, false);
		    funItemReadOnly(frm.ddlKaisha, false);
		    funItemReadOnly(frm.ddlBusho, false);
		    //2012/03/02 TT H.SHIMA Java6対応 add End
	    }
    }
    // MOD 2013/9/25 okano【QP@30151】No.28 end

    return true;

}

//========================================================================================
// 登録、更新、削除ボタン押下処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①mode ：処理区分
//           1：登録、2：更新、3：削除
// 概要  ：表示データの登録、更新、削除処理を行う
//========================================================================================
function funDataEdit(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN2070";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2080");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2080I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2080O);
    var dspMsg;

    if (mode == 1) {
        dspMsg = I000002;
    } else if (mode == 2) {
        dspMsg = I000003;
    } else {
        dspMsg = I000004;
    }

    //確認ﾒｯｾｰｼﾞの表示
    if (funConfMsgBox(dspMsg) != ConBtnYes) {
        return false;
    }

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //処理区分の退避
    frm.hidEditMode.value = mode;

    //XMLの初期化
    setTimeout("xmlFGEN2080I.src = '../../model/FGEN2080I.xml';", ConTimer);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //登録、更新、削除処理を実行する
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2070, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //完了ﾒｯｾｰｼﾞの表示
    dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {

    	//仮登録ユーザの場合
    	if(hidKariMode == "1"){
    		funChkLogin();
    	}

        //正常
        funInfoMsgBox(dspMsg);

        //画面情報を取得・設定
        //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ(空白あり)
	    funClearSelect(frm.ddlKengen, 1);
	    funClearSelect(frm.ddlKaisha, 1);
	    funClearSelect(frm.ddlBusho, 2);
        if (funGetInfo(1) == false) {
            return false;
        }

        //画面の初期化
        funClear();
    }

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    return true;

}

//========================================================================================
// ログイン情報チェック処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 概要  ：ユーザの認証を行う
//========================================================================================
function funChkLogin() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP0010";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA010");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA010I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA010O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //ﾕｰｻﾞ認証を行う
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0010, xmlReqAry, xmlResAry, 2) == false) {
        return false;
    } else {
    	// MOD 2013/9/25 okano【QP@30151】No.28 start
        //ﾒﾆｭｰに遷移
//        	funNextMenu();
    	funNext(1);
    	// MOD 2013/9/25 okano【QP@30151】No.28 end
    }

    return true;

}

// DEL 2013/9/25 okano【QP@30151】No.28 start
//========================================================================================
// メインメニュー画面遷移処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 概要  ：メニュー画面に遷移する
//========================================================================================
//function funNextMenu() {
//
//    var wUrl;
//
//    wUrl = "../SQ020MainMenu/SQ020MainMenu.jsp";
//
//    //ﾒｲﾝﾒﾆｭｰを表示
//    funUrlConnect(wUrl, ConConectPost, document.frm00);
//
//    return true;
//}
// DEL 2013/9/25 okano【QP@30151】No.28 end

//========================================================================================
// XMLファイルに書き込み
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
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
    var j;

    for (i = 0; i < reqAry.length; i++) {
        //画面初期表示
        if (XmlId.toString() == "RGEN2040") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2040
                	funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 2:    //FGEN2050
                    break;
            }

        //ﾕｰｻﾞIDﾛｽﾄﾌｫｰｶｽ
        } else if (XmlId.toString() == "RGEN2050"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2060
                	//前ゼロ削除
// MOD start 20120705 hisahori
//                    var id_user = frm.txtUserId.value.replace(/^0+/,"");
                    var id_user = frm.txtUserId.value;
                    if (id_user != 0){
                    	id_user = id_user.replace(/^0+/,"");
                    }
// MOD start 20120705 hisahori
                    funXmlWrite(reqAry[i], "id_user", id_user, 0);
                    break;
                case 2:    //FGEN2040
                	funXmlWrite(reqAry[i], "id_user", id_user, 0);
                    break;
            }

        //登録、更新、削除ﾎﾞﾀﾝ押下
        } else if (XmlId.toString() == "RGEN2070"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2080
                	funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
                	funXmlWrite(reqAry[i], "password", frm.txtPass.value, 0);
                	funXmlWrite(reqAry[i], "cd_kengen", frm.ddlKengen.options[frm.ddlKengen.selectedIndex].value, 0);
                	funXmlWrite(reqAry[i], "nm_user", frm.txtUserName.value, 0);
                	funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                	funXmlWrite(reqAry[i], "cd_busho", frm.ddlBusho.options[frm.ddlBusho.selectedIndex].value, 0);
                    //2015/03/03 DEL start TT.Kitazawa【QP@40812】No.19
//                	funXmlWrite(reqAry[i], "id_josi", frm.hdnUserNameJosi.value, 0);
                    //2015/03/03 DEL end TT.Kitazawa【QP@40812】No.19
                	funXmlWrite(reqAry[i], "kbn_shori", frm.hidEditMode.value, 0);

                    //2015/03/03 ADD start TT.Kitazawa【QP@40812】No.19
                    funXmlWrite(reqAry[i], "id_member", funXmlRead(xmlFGEN2060O, "id_member", 0), 0);
                    // 共有メンバー2件目以降
                    for( j = 1; j < funGetLength(xmlFGEN2060O); j++ ){
                    	funAddRecNode_Tbl(reqAry[i], "RGEN2070", "table");
                    	funXmlWrite(reqAry[i], "id_member", funXmlRead(xmlFGEN2060O, "id_member", j), j);
                    }
                    //2015/03/03 ADD end TT.Kitazawa【QP@40812】No.19
                	break;
            }

        //会社ｺﾝﾎﾞ選択
        } else if (XmlId.toString() == "RGEN2060"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2070
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
// 【QP@10713】2011/10/28 TT H.SHIMA -ADD Start
//xmlに設定する値、権限の追加
                    funXmlWrite(reqAry[i], "eigyo_kengen", hidEigyoKengen , 0);
// 【QP@10713】2011/10/28 TT H.SHIMA -ADD End
                    break;
            }

        //シングルサインオン
        } else if (XmlId.toString() == "JSP0010"){
            switch (i) {
                case 0:    //USERINFO
	                funXmlWrite(reqAry[i], "kbn_shori", 2, 0);
	                funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
	                break;
            	case 1:    //SA010
                    funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
	                funXmlWrite(reqAry[i], "password", frm.txtPass.value, 0);
	                funXmlWrite(reqAry[i], "kbn_login", "1", 0);
	                break;
            }
        }
    }

    return true;

}

//========================================================================================
// 権限関連処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 概要  ：権限情報の退避、画面制御を行う
//========================================================================================
function funSaveKengenInfo() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var obj = xmlUSERINFO_O;
    var GamenId;
    var KinoId;
    var DataId;
    var reccnt;
    var i;

// 【QP@10713】2011/10/28 TT H.SHIMA -ADD Start
	//権限初期値設定
	hidEigyoKengen = "1";
// 【QP@10713】2011/10/28 TT H.SHIMA -ADD End

    //登録、更新、削除ﾎﾞﾀﾝの制御
    frm.btnInsert.disabled = true;
    frm.btnUpdate.disabled = true;
    frm.btnDelete.disabled = true;

    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        GamenId = funXmlRead(obj, "id_gamen", i);
        KinoId = funXmlRead(obj, "id_kino", i);
        DataId = funXmlRead(obj, "id_data", i);

        //担当者ﾏｽﾀﾒﾝﾃﾅﾝｽ（営業）
        if (GamenId.toString() == ConGmnIdEigyoTantoMst.toString()) {
            //編集（一般）
            if (KinoId.toString() == ConFuncIdEigyoTantoEditIppan.toString()) {
                frm.btnInsert.disabled = true;
                frm.btnUpdate.disabled = false;
                frm.btnDelete.disabled = true;
            }
            //編集（仮登録ユーザ）
            else if (KinoId.toString() == ConFuncIdEigyoTantoEditKari.toString()) {
                frm.btnInsert.disabled = false;
                frm.btnUpdate.disabled = true;
                frm.btnDelete.disabled = true;
            }
            //編集（本部権限）
            else if (KinoId.toString() == ConFuncIdEigyoTantoEditHonbu.toString()) {
                frm.btnInsert.disabled = false;
                frm.btnUpdate.disabled = false;
                frm.btnDelete.disabled = false;
            }
            //編集（システム管理者）
            else if (KinoId.toString() == ConFuncIdEigyoTantoEditSystem.toString()) {
                frm.btnInsert.disabled = false;
                frm.btnUpdate.disabled = false;
                frm.btnDelete.disabled = false;
            }

            hidMode.value = KinoId;
        }
    }

    return true;

}

//========================================================================================
// ユーザID変更時
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：なし
// 概要  ：画面を初期化する
//========================================================================================
function funChangeUserId() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //画面の初期化

    //ｺﾝﾎﾞﾎﾞｯｸｽの再設定
    funClearSelect(frm.ddlBusho, 2);

    //値の設定
    frm.txtPass.value = "";
    frm.ddlKengen.selectedIndex = 0;
    frm.txtUserName.value = "";
    frm.ddlKaisha.selectedIndex = 0;
//2015/03/03 DEL start TT.Kitazawa【QP@40812】No.19
//    frm.txtUserNameJosi.value = "";
//    frm.hdnUserNameJosi.value = "";
//
//    //編集可否の設定
//    frm.btnJosiSearch.disabled=false;
//    frm.btnJosiDel.disabled=false;
//2015/03/03 DEL end TT.Kitazawa【QP@40812】No.19

    //2015/03/03 ADD start TT.Kitazawa【QP@40812】No.19
    frm.btnAddList.disabled=false;
    frm.btnDelList.disabled=false;
    //2015/03/03 ADD end TT.Kitazawa【QP@40812】No.19
    return true;

}

//========================================================================================
// 担当者検索画面起動処理（担当者検索モード）
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：なし
// 概要  ：担当者検索画面を起動する
//========================================================================================
function funSearchUser() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var retVal;

    //担当者検索モード設定
    frm.hidOpnerSearch.value = "1";

    //担当者検索画面を起動する
    //2015/05/15 MOD start TT.Kitazawa【QP@40812】No.19
//    retVal = funOpenModalDialog("../SQ151EigyoTantoSearch/SQ151EigyoTantoSearch.jsp", this, "dialogHeight:800px;dialogWidth:1000px;status:no;scroll:no");
    retVal = funOpenModalDialog("../SQ151EigyoTantoSearch/SQ151EigyoTantoSearch.jsp", this, "dialogHeight:800px;dialogWidth:1100px;status:no;scroll:no");
    //2015/05/15 MOD end TT.Kitazawa【QP@40812】No.19
    //retVal = funOpenModalDialog("../SQ151EigyoTantoSearch/SQ151EigyoTantoSearch.jsp", this, "dialogHeight:" + window.screen.height + "px;dialogWidth:" + window.screen.width + "px;status:no;scroll:no");

    if (retVal != "") {
        //情報の分割
        var id_user = retVal.split(":")[0];

        //ﾕｰｻﾞIDの設定
        frm.txtUserId.value = id_user;

        //担当者情報の表示
        funSearch();

//【QP@10713】2011/11/07 TT H.SHIMA -ADD Start 担当者検索後の部署コンボ設定
        funChangeKengen();
        funDefaultIndex(frm.ddlBusho, 3);
//【QP@10713】2011/11/07 TT H.SHIMA -ADD End

        //ﾌｫｰｶｽ設定
        //frm.txtPass.focus();
    }


    return true;

}

//2015/03/03 DEL start TT.Kitazawa【QP@40812】No.19
/*
//========================================================================================
// 担当者検索画面起動処理（上司検索モード）
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：なし
// 概要  ：担当者検索画面を起動する
//========================================================================================
function funSearchUserJosi() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var retVal;

    var cd_kaisha = frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value;
    var cd_busho = frm.ddlBusho.options[frm.ddlBusho.selectedIndex].value;
    if(cd_kaisha == "" || cd_busho == "" ){
        funInfoMsgBox(E000014);
    	return false;
    }

    //担当者検索モード設定
    frm.hidOpnerSearch.value = "2";

    //担当者検索画面を起動する
    retVal = funOpenModalDialog("../SQ151EigyoTantoSearch/SQ151EigyoTantoSearch.jsp", this, "dialogHeight:800px;dialogWidth:1000px;status:no;scroll:no");
    //retVal = funOpenModalDialog("../SQ151EigyoTantoSearch/SQ151EigyoTantoSearch.jsp", this, "dialogHeight:" + window.screen.height + "px;dialogWidth:" + window.screen.width + "px;status:no;scroll:no");

    if (retVal != "") {
        //情報の分割
        var id_user = retVal.split(":")[0];
        var nm_user = retVal.split(":")[1];

        //ﾕｰｻﾞIDの設定
        frm.hdnUserNameJosi.value = id_user;
        frm.txtUserNameJosi.value = nm_user;
    }

    return true;

}*/
//2015/03/03 DEL end TT.Kitazawa【QP@40812】No.19

//========================================================================================
// 会社コンボボックス連動処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：なし
// 概要  ：会社に紐付く部署コンボボックスを生成する
//========================================================================================
function funChangeKaisha() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN2060";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2070");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2070I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2070O);

    if (frm.ddlKaisha.selectedIndex == 0) {
        funClearSelect(frm.ddlBusho, 2);
        return true;
    }

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //部署情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2060, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽの作成
    funCreateComboBox(frm.ddlBusho, xmlResAry[2], 3);

    return true;

}

//========================================================================================
// 次画面遷移処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 概要  ：次画面に遷移する
//========================================================================================
function funNext(mode) {

    var wUrl;

    //遷移先判定
    switch (mode) {
        case 0:    //ﾏｽﾀﾒﾆｭｰ
            wUrl = "../SQ030MstMenu/SQ030MstMenu.jsp";
            break;
        // ADD 2013/9/25 okano【QP@30151】No.28 start
        case 1:    //仮登録ﾕｰｻﾞ
        	wUrl = "../SQ020MainMenu/SQ020MainMenu.jsp";
            break;
        case 2:    //仮登録ﾕｰｻﾞ終了時
        	wUrl = "../SQ010Login/SQ010Login.jsp";
            break;
        // ADD 2013/9/25 okano【QP@30151】No.28 end
    }

    //遷移
    funUrlConnect(wUrl, ConConectPost, document.frm00);

    return true;
}

//========================================================================================
// コンボボックス作成処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②xmlData  ：設定XML
//       ：③mode     ：モード
// 概要  ：コンボボックスを作成する
//========================================================================================
function funCreateComboBox(obj, xmlData, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var reccnt;                  //設定XMLの件数
    var objNewOption;
    var atbName;
    var atbCd;
    var i;

    //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ(空白あり)
    funClearSelect(obj, 2);

    //件数取得
    reccnt = funGetLength(xmlData);

    //ﾃﾞｰﾀが存在しない場合
    if (reccnt == 0) {
        return true;
    }

    //属性名の取得
    switch (mode) {
        case 1:    //権限ﾏｽﾀ
            atbName = "nm_kengen";
            atbCd = "cd_kengen";
            break;
        case 2:    //部署ﾏｽﾀ(会社)
            atbName = "nm_kaisha";
            atbCd = "cd_kaisha";
            break;
        case 3:    //部署ﾏｽﾀ(部署)
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
// デフォルト値選択処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
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
            case 1:    //権限ｺﾝﾎﾞ
                if (obj.options[i].value == funXmlRead(xmlFGEN2060O, "cd_kengen", 0)) {
                    selIndex = i;
                }
                break;
            case 2:    //会社ｺﾝﾎﾞ
                if (obj.options[i].value == funXmlRead(xmlFGEN2060O, "cd_kaisha", 0)) {
                    selIndex = i;
                }
                break;
            case 3:    //部署ｺﾝﾎﾞ
                if (obj.options[i].value == funXmlRead(xmlFGEN2060O, "cd_busho", 0)) {
                    selIndex = i;
                }
                break;
        }
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽのｲﾝﾃﾞｯｸｽ指定
    obj.selectedIndex = selIndex;

    return true;
}

//2015/03/03 DEL start TT.Kitazawa【QP@40812】No.19
/*
//========================================================================================
// 上司クリア
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 概要  ：コンボボックスのデフォルト値を選択する
//========================================================================================
function funDelJosi() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //上司IDクリア
    frm.hdnUserNameJosi.value="";
    //上司名クリア
    frm.txtUserNameJosi.value="";

    return true;
}*/
//2015/03/03 DEL end TT.Kitazawa【QP@40812】No.19

//========================================================================================
//ドロップダウンリストのReadOnly設定処理
//作成者：H.Shima
//作成日：2012/03/02
//概要  ：ドロップダウンリストの設定を行う
//========================================================================================
function funDdlReadOnly(){
    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    funItemReadOnly(frm.ddlKengen, true);
    funItemReadOnly(frm.ddlKaisha, true);
    funItemReadOnly(frm.ddlBusho, true);

    return true;
}

// ADD 2013/9/25 okano【QP@30151】No.28 start
function funClose(){

    if (hidMode.value == ConFuncIdEigyoTantoEditKari.toString() || hidMode.value == ConFuncIdEigyoTantoEditPass.toString() || hidMode.value == ConFuncIdEigyoTantoEditCash.toString()) {
        funNext(2);
    } else {
        funNext(0);
    }

    return true;
}
// ADD 2013/9/25 okano【QP@30151】No.28 end

//2015/03/03 ADD start TT.Kitazawa【QP@40812】No.19
//========================================================================================
// 担当者検索画面起動処理（上司検索モード）
// 作成者：TT.Kitazawa
// 作成日：2015/03/03
// 引数  ：なし
// 概要  ：担当者検索画面を起動する
//========================================================================================
function funAddList() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var retVal;
    var xmlBuff = new ActiveXObject("Microsoft.XMLDOM");

    var cd_kaisha = frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value;
    var cd_busho = frm.ddlBusho.options[frm.ddlBusho.selectedIndex].value;
    if(cd_kaisha == "" || cd_busho == "" ){
        funInfoMsgBox(E000014);
        return false;
    }

    //担当者検索モード設定
    frm.hidOpnerSearch.value = "2";

    //担当者検索画面を起動する
    retVal = funOpenModalDialog("../SQ151EigyoTantoSearch/SQ151EigyoTantoSearch.jsp", this, "dialogHeight:800px;dialogWidth:1100px;status:no;scroll:no");

    if (retVal != "") {
        //情報の分割
        var id_user = retVal.split(":")[0];
        var nm_user = retVal.split(":")[1];

        //前ゼロ削除（空白の時は来ない）
        id_user = id_user.replace(/^0+/,"");

        // 自分自身は登録できない（空白の時は来ない）
        if (id_user == frm.txtUserId.value.replace(/^0+/,"")) {
            funInfoMsgBox(E000040);
            return false;
        }
        // リストに存在する場合
        for(var i = 0; i < funGetLength(xmlFGEN2060O); i++ ){
            if (id_user == funXmlRead(xmlFGEN2060O, "id_member", i)) {
                funInfoMsgBox(E000041);
                return false;
            }
        }

        //共有メンバーの追加：
        if (funXmlRead(xmlFGEN2060O, "id_member", funGetLength(xmlFGEN2060O)-1) != "" ) {
            // id_memberが設定済の場合、nodeを追加
            funAddRecNode(xmlFGEN2060O, "FGEN2060");
        }
        // 選択したユーザID、ユーザ名をXMLに保存
        funXmlWrite(xmlFGEN2060O, "id_member", id_user, funGetLength(xmlFGEN2060O)-1);
        funXmlWrite(xmlFGEN2060O, "nm_member", nm_user, funGetLength(xmlFGEN2060O)-1);

        //値を設定したXMLの再ﾛｰﾄﾞ
        xmlBuff.load(xmlFGEN2060O);
        xmlFGEN2060O.load(xmlBuff);

        // 共有メンバーリストを表示
        if (tblList.style.display == "none") {
            tblList.style.display = "block";
        }
        funClearCurrentRow(tblList);

    }

    return true;

}

//========================================================================================
// 共有メンバー削除処理
// 作成者：TT.Kitazawa
// 作成日：2015/03/03
// 引数  ：なし
// 概要  ：選択行の共有メンバーを削除する
//========================================================================================
function funDelList() {

    //行が選択されていない場合
    if (funGetCurrentRow().toString() == "") {
        funErrorMsgBox(E000002);
        return false;
    }

    //指定された共有メンバーを削除する
    if (funGetLength(xmlFGEN2060O) > 1) {
    	//指定行を削除
        funSelectRowDelete(xmlFGEN2060O);
    } else {
    	//データが1件の時、共有メンバーをクリア
        funXmlWrite(xmlFGEN2060O, "id_member", "", 0);
        funXmlWrite(xmlFGEN2060O, "nm_member", "", 0);
        // 選択行をクリア
        funClearCurrentRow(tblList);
        // 共有メンバーリストを非表示
        tblList.style.display = "none"
    }

    return true;

}
//2015/03/03 ADD end TT.Kitazawa【QP@40812】No.19
