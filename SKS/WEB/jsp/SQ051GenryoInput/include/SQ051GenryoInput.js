
//画面設定(BODYのﾛｰﾄﾞ後にﾀｲﾄﾙの設定が行えないため、BODYのﾛｰﾄﾞ前に行う)
funInitScreen(ConGenryoInputId);

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.13
	var blnCheckKakunin;
//add end --------------------------------------------------------------------------------------
// 20160513  KPX@1600766 ADD start
// 廃止操作許可
	var blnHaishiKyoka = false;
// 20160513  KPX@1600766 ADD end

//========================================================================================
// 初期表示処理
// 作成者：M.Jinbo
// 作成日：2009/04/03
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var i;

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //画面情報を取得・設定
    if (funGetInfo(1) == false) {
        return false;
    }

    //戻り値の初期化
    window.returnValue = "";

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.13
	//分析情報退避
	funBunsekiDataTaihi();
//add end --------------------------------------------------------------------------------------

    return true;

}

//========================================================================================
// 確認クリック処理
// 作成者：M.Jinbo
// 作成日：2009/04/03
// 概要  ：確認者情報を表示する
//========================================================================================
function funChkKakunin(obj) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var oDate;
    var oName;

    //確認ﾁｪｯｸﾎﾞｯｸｽがﾁｪｯｸ状態の場合
    if (obj.checked) {
        oDate = funGetSystemDate();
        oName = funXmlRead(xmlUSERINFO_O, "nm_user", 0);
    } else {
        oDate = "";
        oName = "";
    }

    //確認者情報の設定
    frm.txtCheckDt.value = oDate;
    frm.txtCheckName.value = oName;

    return true;
}

//========================================================================================
// 登録ボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/04/03
// 引数  ：なし
// 概要  ：画面内容の登録、更新を行う。
//========================================================================================
function funEdit() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP0520";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA380");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA380I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA380O);
    var confMsg;
    var resMsg;
    var NewGenryoCd;

    if (frm.hidMode.value == 2) {
        confMsg = I000002;
    } else {
        confMsg = I000003;
    }

    //確認ﾒｯｾｰｼﾞの表示
    if (funConfMsgBox(confMsg) != ConBtnYes) {
        return false;
    }

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //管理台帳No300 TT西川 2009/09/30 -------------------------START
    //新規登録で原料ｺｰﾄﾞが採番されていない場合
    /*if  (frm.hidMode.value == 2 && frm.txtGenryoCd.value == "") {
        //採番に必要な変数の宣言
        var XmlId_Saiban = "J010";
        var FuncIdAry_Saiban = new Array(ConResult,ConUserInfo,"SA410");
        var xmlReqAry_Saiban = new Array(xmlUSERINFO_I,xmlSA410I);
        var xmlResAry_Saiban = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA410O);

        //引数をXMLﾌｧｲﾙに設定
        if (funReadyOutput(XmlId_Saiban, xmlReqAry_Saiban, 1) == false) {
            funClearRunMessage();
            return false;
        }

        //採番処理を実行する
        if (funAjaxConnection(XmlId_Saiban, FuncIdAry_Saiban, xmlJ010, xmlReqAry_Saiban, xmlResAry_Saiban, 1) == false) {
            return false;
        }

        //原料ｺｰﾄﾞの編集
        NewGenryoCd = funXmlRead(xmlResAry_Saiban[2], "new_code", 0);
        NewGenryoCd = ConGenryoCdPrefix + ("00000" + NewGenryoCd).slice(-5);
        funXmlWrite(xmlResAry_Saiban[2], "new_code", NewGenryoCd, 0);

    } else {
        NewGenryoCd = "";
    }*/
	//---------------------------------------------------------END

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //登録処理を実行する
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0520, xmlReqAry, xmlResAry, 1) == false) {
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.13
		//退避値戻し処理
		funBunsekiDataBack();
//add end --------------------------------------------------------------------------------------
        return false;
    }

    //完了ﾒｯｾｰｼﾞの表示
    resMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {
        //正常
        funInfoMsgBox(resMsg);


        //管理台帳No300 TT西川 2009/09/30 -------------------------START

        /*if (NewGenryoCd != "") {
            //原料ｺｰﾄﾞの設定
            frm.txtGenryoCd.value = NewGenryoCd;
        }*/

        //新規原料登録時
        if  (frm.hidMode.value == 2 && frm.txtGenryoCd.value == ""){

            //採番した原料コードを取得
            NewGenryoCd = funXmlRead(xmlResAry[2], "cd_genryo", 0);
            frm.txtGenryoCd.value = NewGenryoCd;

        }

        //---------------------------------------------------------END

        var opener = window.dialogArguments;      //引数の取得
        var opener_form = opener.document.forms(0);

        opener_form.hidMode.value = 1;
        opener_form.hidKaishaCd.value = frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value;    //会社ｺｰﾄﾞ
        if (opener_form.hidKojoCd.value == "") {
            opener_form.hidKojoCd.value = "0";    //工場ｺｰﾄﾞ
        }
        opener_form.hidGenryoCd.value = frm.txtGenryoCd.value;    //原料ｺｰﾄﾞ

        //画面情報を取得・設定
        if (funGetInfo(1) == false) {
            return false;
        }

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.13
		//分析情報退避
		funBunsekiDataTaihi();
//add end --------------------------------------------------------------------------------------

        //戻り値の設定
        window.returnValue = "1";

    } else {
        //異常
        funErrorMsgBox(resMsg);

        //戻り値の設定
        window.returnValue = "";
    }

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    return true;

}

//========================================================================================
// 終了ボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/04/03
// 概要  ：画面を閉じる
//========================================================================================
function funClose() {

    //画面を閉じる
    close(self);

    return true;
}

//========================================================================================
// 画面情報取得処理
// 作成者：M.Jinbo
// 作成日：2009/04/03
// 引数  ：①mode  ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：ユーザのセッション情報、コンボボックスの情報を取得する
//========================================================================================
function funGetInfo(mode) {

    var opener = window.dialogArguments;      //引数の取得
    var opener_form = opener.document.forms(0);
    var frm = document.frm00;                 //ﾌｫｰﾑへの参照
    var XmlId = "JSP0510";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA140","SA400");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA140I,xmlSA400I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA140O,xmlSA400O);

    //引数の退避
    frm.hidMode.value = opener_form.hidMode.value;            //起動ﾓｰﾄﾞ（1：詳細、2：新規）
    if (frm.hidMode.value == 1) {
        frm.hidKaishaCd.value = opener_form.hidKaishaCd.value;    //会社ｺｰﾄﾞ
        frm.hidKojoCd.value = opener_form.hidKojoCd.value;    //原料ｺｰﾄﾞ
        frm.hidGenryoCd.value = opener_form.hidGenryoCd.value;    //原料ｺｰﾄﾞ
    }

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0510, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //ﾕｰｻﾞ情報表示
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //権限関連の処理を行う
    funSaveKengenInfo();

    //ｺﾝﾎﾞﾎﾞｯｸｽの作成
    funCreateComboBox(frm.ddlKaisha, xmlResAry[2], 1);

    //画面の初期化
// 20160513  KPX@1600766 MOD start
//    funInit(xmlResAry[3]);
    if (funInit(xmlResAry[3]) == false) {
        funClearRunMessage();
        funClose();
        return false;
    }
// 20160513  KPX@1600766 MOD end

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.13
    if(frm.chkKakunin.checked == true){
    	blnCheckKakunin=true;
    }
    else{
	    blnCheckKakunin=false;
    }
//add end --------------------------------------------------------------------------------------

    return true;

}

//========================================================================================
// 権限関連処理
// 作成者：M.Jinbo
// 作成日：2009/04/03
// 概要  ：権限情報の退避、画面制御を行う
//========================================================================================
function funSaveKengenInfo() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var obj = xmlUSERINFO_O;
    var GamenId;
    var KinoId;
    var reccnt;
    var i;

    //登録ﾎﾞﾀﾝの制御
    frm.btnEdit.disabled = true;

    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        GamenId = funXmlRead(obj, "id_gamen", i);
        KinoId = funXmlRead(obj, "id_kino", i);

        //原料ｺｰﾄﾞが未入力の場合
        if (funXmlRead(xmlSA400O, "cd_genryo", 0) == "") {
            //分析値入力(新規原料)
            if (GamenId.toString() == ConGmnIdGenryoInputNew.toString()) {
                //新規
                if (KinoId.toString() == ConFuncIdNew.toString()) {
                    frm.btnEdit.disabled = false;
                }
            }

        } else {
            //分析値入力(既存原料)
            if (GamenId.toString() == ConGmnIdGenryoInputUpd.toString()) {
                //編集(新規原料のみ)
                if (KinoId.toString() == ConFuncIdEdit.toString()) {
                    //新規原料の場合
                    if (isNaN(funXmlRead(xmlSA400O, "cd_genryo", 0).charAt(0))) {
                        frm.btnEdit.disabled = false;
                    }

                //編集(全て)
                } else if (KinoId.toString() == ConFuncIdAll.toString()) {
                    frm.btnEdit.disabled = false;

// 20160513  KPX@1600766 ADD start
                    //編集(システム管理者) 廃止操作許可
                } else if (KinoId.toString() == ConFuncIdSysMgr.toString()) {
                    frm.btnEdit.disabled = false;
                    //廃止操作許可
                    blnHaishiKyoka = true;
// 20160513  KPX@1600766 ADD end
                }
            }
        }
    }

    return true;

}

//========================================================================================
// 画面初期化処理
// 作成者：M.Jinbo
// 作成日：2009/04/03
// 引数  ：①xmlData ： 原料情報XML
// 概要  ：画面を初期化する
//========================================================================================
function funInit(xmlData) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var i;

    //画面制御
    if (frm.hidMode.value == 1) {
        //詳細ﾓｰﾄﾞ
        funItemReadOnly(frm.ddlKaisha, true);
        funItemReadOnly(frm.txtGenryoCd, true);

        //新規原料の場合
        if (isNaN(funXmlRead(xmlData, "cd_genryo", 0))) {
        	funItemReadOnly(frm.txtGenryoName, false);
        } else {
            funItemReadOnly(frm.txtGenryoName, true);
        }

        funItemReadOnly(frm.txtHyojian, false);
        funItemReadOnly(frm.txtTenkabutu, false);
        for (i = 0; i < 5; i++) {
            funItemReadOnly(frm.txtEiyoNo[i], false);
            funItemReadOnly(frm.txtWariai[i], false);
        }
        funItemReadOnly(frm.txtInputDt, true);
        funItemReadOnly(frm.txtInputName, true);
        funItemReadOnly(frm.chkKakunin, false);
        funItemReadOnly(frm.txtCheckDt, true);
        funItemReadOnly(frm.txtCheckName, true);
// 20160513  KPX@1600766 MOD start
        //廃止チェックはシステム管理者のみ
//        funItemReadOnly(frm.chkHaishi, false);
        funItemReadOnly(frm.chkHaishi, true);
// 20160513  KPX@1600766 MOD end
        funItemReadOnly(frm.txtKakuteiCd, false);

        //既存原料、又は新規原料で廃止区分＝1の場合
        if (isNaN(funXmlRead(xmlData, "cd_genryo", 0)) == false ||
            (funXmlRead(xmlData, "kbn_haishi", 0) == "1" && isNaN(funXmlRead(xmlData, "cd_genryo", 0)))) {
            funItemReadOnly(frm.chkHaishi, true);
            funItemReadOnly(frm.txtKakuteiCd, true);
        }

        //新規原料で廃止区分＝1の場合
        if (funXmlRead(xmlData, "kbn_haishi", 0) == "1" && isNaN(funXmlRead(xmlData, "cd_genryo", 0))) {
            funItemReadOnly(frm.btnEdit, true);
        }

// 20160513  KPX@1600766 ADD start
        // 画面権限がシステム管理者（廃止操作許可）の時のみチェック可、廃止解除可
        if (blnHaishiKyoka) {
        	//新規原料
        	if (isNaN(funXmlRead(xmlData, "cd_genryo", 0)) == true) {
        		funItemReadOnly(frm.chkHaishi, false);
        	}
        	funItemReadOnly(frm.btnEdit, false);
        }
// 20160513  KPX@1600766 ADD end

        //画面情報の設定
//20160513  KPX@1600766 MOD start
        //表示する会社がコンボボックスに存在しない時、エラーとする。
//        funDefaultIndex(frm.ddlKaisha, 2);
        if (funDefaultIndex(frm.ddlKaisha, 2) == false) {
        	funErrorMsgBox("対象会社の原料は詳細表示が許可されていません。");
        	return false;
        }
//20160513  KPX@1600766 MOD end
// 20160610  KPX@1502111_No.5 ADD start
        //新規原料の場合
        if (isNaN(funXmlRead(xmlData, "cd_genryo", 0))) {
        	//新規原料部署コード退避
        	frm.hidGenryoBushoCd.value = funXmlRead(xmlData, "cd_busho", 0);
        	//新規原料の連携情報取得・表示
        	funRenkeiDsp();
        }
// 20160610  KPX@1502111_No.5 ADD end

        frm.txtGenryoCd.value = funXmlRead(xmlData, "cd_genryo", 0);
        frm.txtGenryoName.value = funXmlRead(xmlData, "nm_genryo", 0);
        frm.txtSakusan.value = funXmlRead(xmlData, "ritu_sakusan", 0);
        frm.txtShokuen.value = funXmlRead(xmlData, "ritu_shokuen", 0);
        frm.txtSosan.value = funXmlRead(xmlData, "ritu_sousan", 0);
        frm.txtGanyu.value = funXmlRead(xmlData, "ritu_abura", 0);
// ADD start 20121005 QP@20505 No.24
        frm.txtMsg.value = funXmlRead(xmlData, "ritu_msg", 0);
// ADD end 20121005 QP@20505 No.24
        frm.txtHyojian.value = funXmlRead(xmlData, "hyojian", 0);
        frm.txtTenkabutu.value = funXmlRead(xmlData, "tenkabutu", 0);
        for (i = 0; i < 5; i++) {
            frm.txtEiyoNo[i].value = funXmlRead(xmlData, "no_eiyo" + (i+1), 0);
            frm.txtWariai[i].value = funXmlRead(xmlData, "wariai" + (i+1), 0);
        }
        frm.txtMemo.value = funXmlRead(xmlData, "memo", 0);
        //frm.txtInputDt.value = funXmlRead(xmlData, "dt_toroku", 0);
        //frm.txtInputName.value = funXmlRead(xmlData, "nm_toroku", 0);
        frm.txtInputDt.value = funXmlRead(xmlData, "dt_koshin", 0);
        frm.txtInputName.value = funXmlRead(xmlData, "nm_koshin", 0);
        if (funXmlRead(xmlData, "dt_kakunin", 0) != "") {
            frm.chkKakunin.checked = true;
            frm.txtCheckDt.value = funXmlRead(xmlData, "dt_kakunin", 0);
            frm.txtCheckName.value = funXmlRead(xmlData, "nm_kakunin", 0);
        } else {
            frm.chkKakunin.checked = false;
            frm.txtCheckDt.value = "";
            frm.txtCheckName.value = "";
        }
        if (funXmlRead(xmlData, "kbn_haishi", 0) == "1") {
            frm.chkHaishi.checked = true;
        } else {
            frm.chkHaishi.checked = false;
        }
        frm.txtKakuteiCd.value = funXmlRead(xmlData, "cd_kakutei", 0);

        //ﾌｫｰｶｽ設定
        if (isNaN(funXmlRead(xmlData, "cd_genryo", 0))) {
            frm.txtGenryoName.focus();
        } else {
            frm.txtSakusan.focus();
        }

    } else {
        //新規原料ﾓｰﾄﾞ
        funItemReadOnly(frm.txtGenryoCd, true);
        funItemReadOnly(frm.txtHyojian, true);
        funItemReadOnly(frm.txtTenkabutu, true);
        for (i = 0; i < 5; i++) {
            funItemReadOnly(frm.txtEiyoNo[i], true);
            funItemReadOnly(frm.txtWariai[i], true);
        }
        funItemReadOnly(frm.txtInputDt, true);
        funItemReadOnly(frm.txtInputName, true);
        funItemReadOnly(frm.chkKakunin, true);
        funItemReadOnly(frm.txtCheckDt, true);
        funItemReadOnly(frm.txtCheckName, true);
        funItemReadOnly(frm.chkHaishi, true);
        funItemReadOnly(frm.txtKakuteiCd, true);
        //画面情報の設定
        funDefaultIndex(frm.ddlKaisha, 1);
        frm.txtInputDt.value = funGetSystemDate();
        frm.txtInputName.value = funXmlRead(xmlUSERINFO_O, "nm_user", 0);

        //ﾌｫｰｶｽ設定
        frm.ddlKaisha.focus();
    }

// 20160610  KPX@1502111_No.5 ADD start
    //連携情報：全てtxtSampleNo
    funItemReadOnly(frm.txtShainCd, true);
    funItemReadOnly(frm.txtNen, true);
    funItemReadOnly(frm.txtOiNo, true);
    funItemReadOnly(frm.txtEdaNo, true);
    funItemReadOnly(frm.txtHinNm, true);
    funItemReadOnly(frm.txtSampleNo, true);
// 20160610  KPX@1502111_No.5 ADD end

    return true;

}

//========================================================================================
// XMLファイルに書き込み
// 作成者：M.Jinbo
// 作成日：2009/04/03
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
        if (XmlId.toString() == "JSP0510") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA140
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    if (frm.hidMode.value == 1) {
                        //詳細
                        funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenryoInputUpd, 0);
                    } else {
                        //新規
                        funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenryoInputNew, 0);
                    }
                    break;
                case 2:    //SA400
                    if (frm.hidMode.value == 1) {
                        //詳細
                        funXmlWrite(reqAry[i], "cd_kaisha", frm.hidKaishaCd.value, 0);
                        funXmlWrite(reqAry[i], "cd_busho", frm.hidKojoCd.value, 0);
                        funXmlWrite(reqAry[i], "cd_genryo", frm.hidGenryoCd.value, 0);
                        funXmlWrite(reqAry[i], "kbn_shori", "2", 0);
                    } else {
                        //新規
                        funXmlWrite(reqAry[i], "cd_kaisha", frm.hidKaishaCd.value, 0);
                        funXmlWrite(reqAry[i], "cd_busho", frm.hidKojoCd.value, 0);
                        funXmlWrite(reqAry[i], "cd_genryo", "", 0);
                        funXmlWrite(reqAry[i], "kbn_shori", "1", 0);
                    }
                    break;
            }

        //登録ﾎﾞﾀﾝ押下
        } else if (XmlId.toString() == "JSP0520"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA380
                    if (frm.ddlKaisha.length == 0) {
                        funXmlWrite(reqAry[i], "cd_kaisha", "", 0);
                    } else {
                        funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    }
                    //入力者情報
                    if (frm.hidMode.value == 1) {
                        //更新
                        //funXmlWrite(reqAry[i], "id_toroku", funXmlRead(xmlSA400O, "id_toroku", 0), 0);
                        //funXmlWrite(reqAry[i], "dt_toroku", funXmlRead(xmlSA400O, "dt_toroku", 0), 0);
                        funXmlWrite(reqAry[i], "id_koshin", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                        funXmlWrite(reqAry[i], "dt_koshin", funGetSystemDate(), 0);
                        funXmlWrite(reqAry[i], "kbn_shori", "1", 0);
                    } else {
                        //登録
                        funXmlWrite(reqAry[i], "cd_genryo", funXmlRead(xmlSA410O, "new_code", 0), 0);
                        funXmlWrite(reqAry[i], "id_toroku", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                        funXmlWrite(reqAry[i], "dt_toroku", funGetSystemDate(), 0);
                        funXmlWrite(reqAry[i], "kbn_shori", "0", 0);
                    }
                    //確認者情報
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.13
                    //更新情報の確認
                    var fg = funKoshinJohoCheck();
                    var fg_henkou = funBunsekiDataHensyuCheck();

                    if(fg_henkou == 1){
	                    funXmlWrite(reqAry[i], "fg_henkou", "true", 0);
                    }
                    else{
                    	funXmlWrite(reqAry[i], "fg_henkou", "false", 0);
                    }

                    if (fg == 1) {
                    	//確認情報にユーザ情報を格納
                        funXmlWrite(reqAry[i], "kbn_kakunin", "1", 0);
                        funXmlWrite(reqAry[i], "id_kakunin", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                        funXmlWrite(reqAry[i], "dt_kakunin", funGetSystemDate(), 0);
                    } else if(fg == 0) {
                    	//確認情報にNULLを格納
                        funXmlWrite(reqAry[i], "kbn_kakunin", "", 0);
                        funXmlWrite(reqAry[i], "id_kakunin", "", 0);
                        funXmlWrite(reqAry[i], "dt_kakunin", "", 0);
                    }else{
                    	return false;
                    }
//mod end --------------------------------------------------------------------------------------

                    if (frm.chkHaishi.checked) {
                        //廃止
                        funXmlWrite(reqAry[i], "kbn_haishi", "1", 0);
                    } else {
                        //使用中
                        funXmlWrite(reqAry[i], "kbn_haishi", "", 0);
                    }
                    break;
            }

        //自動採番
        } else if (XmlId.toString() == "J010"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA410
                    funXmlWrite(reqAry[i], "kbn_shori", "cd_genryo", 0);
                    break;
            }
        //原料連携：配合リンク取得
        } else if (XmlId.toString() == "JW821"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2260
                	//原料コードより検索
                	funXmlWrite(reqAry[i], "syoriMode", "0", 0);
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.hidKaishaCd.value, 0);
                    funXmlWrite(reqAry[i], "cd_genryo", frm.hidGenryoCd.value, 0);
                    funXmlWrite(reqAry[i], "cd_busho", frm.hidGenryoBushoCd.value, 0);
                    break;
            }
        //原料連携：試作名・サンプルNo取得
        } else if (XmlId.toString() == "JW822"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2270
                	//原料コードより検索
                	funXmlWrite(reqAry[i], "syoriMode", "1", 0);
                    funXmlWrite(reqAry[i], "cd_shain", frm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", frm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", frm.txtOiNo.value, 0);
                    funXmlWrite(reqAry[i], "no_eda", frm.txtEdaNo.value, 0);
//                    funXmlWrite(reqAry[i], "seq_shisaku", frm.hidShisakuSeq.value, 0);
                    break;
            }
        }

    }

    return true;

}

//========================================================================================
// コンボボックス作成処理
// 作成者：M.Jinbo
// 作成日：2009/04/03
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②xmlData  ：設定XML
//       ：③mode     ：モード
// 概要  ：コンボボックスを作成する
//========================================================================================
function funCreateComboBox(obj, xmlData, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var reccnt;                  //設定XMLの件数
    var atbName;
    var atbCd;
    var i;

    //件数取得
    reccnt = funGetLength(xmlData);

    //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
    funClearSelect(obj, 1);

    //ﾃﾞｰﾀが存在しない場合
    if (reccnt == 0) {
        //処理を中断
        return true;
    }

    //属性名の取得
    switch (mode) {
        case 1:    //会社ﾏｽﾀ
            atbName = "nm_kaisha";
            atbCd = "cd_kaisha";
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
// 作成者：M.Jinbo
// 作成日：2009/04/03
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②mode     ：モード
// 概要  ：コンボボックスのデフォルト値を選択する
//========================================================================================
function funDefaultIndex(obj, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
//20160513  KPX@1600766 ADD start
//    var selIndex;
    var selIndex = -1;
//20160513  KPX@1600766 ADD end
    var i;

    for (i = 0; i < obj.length; i++) {
        switch (mode) {
            case 1:    //会社ｺﾝﾎﾞ(ﾕｰｻﾞ情報の会社)
                if (obj.options[i].value == funXmlRead(xmlUSERINFO_O, "cd_kaisha", 0)) {
                    selIndex = i;
                }
                break;
            case 2:    //会社ｺﾝﾎﾞ(登録済の会社)
                if (obj.options[i].value == funXmlRead(xmlSA400O, "cd_kaisha", 0)) {
                    selIndex = i;
                }
                break;
        }
    }
//20160513  KPX@1600766 ADD start
    //コンボボックスに表示する会社が存在しない時
    if (selIndex < 0) return false;
//20160513  KPX@1600766 ADD end

    //ｺﾝﾎﾞﾎﾞｯｸｽのｲﾝﾃﾞｯｸｽ指定
    obj.selectedIndex = selIndex;

    return true;
}

//========================================================================================
// システム日付取得処理
// 作成者：M.Jinbo
// 作成日：2009/04/03
// 引数  ：なし
// 戻り値：システム日付(YYYY/MM/DD形式)
// 概要  ：システム日付を取得する
//========================================================================================
function funGetSystemDate() {

    var wDate;
    var wDay;
    var oDate;

    //ｼｽﾃﾑ日付の取得
    wDate = new Date();
    oDate = wDate.getYear();
    wDay = wDate.getMonth() + 1;
    if (wDay.toString().length == 1) {
        oDate += "/0" + wDay;
    } else {
        oDate += "/" + wDay;
    }
    wDay = wDate.getDate();
    if (wDay.toString().length == 1) {
        oDate += "/0" + wDay;
    } else {
        oDate += "/" + wDay;
    }

    return oDate;

}

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.13
//========================================================================================
// 分析情報退避処理
// 作成者：TT.k-katayama
// 作成日：2010/10/13
// 概要  ：分析情報を退避させる
//========================================================================================
function funBunsekiDataTaihi() {
    var frm = document.frm00;                 //ﾌｫｰﾑへの参照

    frm.hidGenryoName.value = frm.txtGenryoName.value;
	frm.hidSakusan.value = frm.txtSakusan.value;
	frm.hidShokuen.value = frm.txtShokuen.value;
	frm.hidSosan.value = frm.txtSosan.value;
	frm.hidGanyu.value = frm.txtGanyu.value;
// ADD start 20121005 QP@20505 No.24
	frm.hidMsg.value = frm.txtMsg.value;
// ADD end 20121005 QP@20505 No.24
	frm.hidHyojian.value = frm.txtHyojian.value;
	frm.hidTenkabutu.value = frm.txtTenkabutu.value;
	frm.hidEiyoNo1.value = frm.txtEiyoNo[0].value;
	frm.hidEiyoNo2.value = frm.txtEiyoNo[1].value;
	frm.hidEiyoNo3.value = frm.txtEiyoNo[2].value;
	frm.hidEiyoNo4.value = frm.txtEiyoNo[3].value;
	frm.hidEiyoNo5.value = frm.txtEiyoNo[4].value;
	frm.hidWariai1.value = frm.txtWariai[0].value;
	frm.hidWariai2.value = frm.txtWariai[1].value;
	frm.hidWariai3.value = frm.txtWariai[2].value;
	frm.hidWariai4.value = frm.txtWariai[3].value;
	frm.hidWariai5.value = frm.txtWariai[4].value;
	frm.hidMemo.value = frm.txtMemo.value;
	frm.hidKakuteiCd.value = frm.txtKakuteiCd.value;
	//確認情報の退避
//20160817  KPX@1502111 DEL start
	//funGetInfo にて blnCheckKakunin に退避
//	frm.hidKakunin.value = frm.chkKakunin.checked;
//20160817  KPX@1502111 DEL end
	frm.hidCheckDt.value = frm.txtCheckDt.value;
	frm.hidCheckName.value = frm.txtCheckName.value;

}

//========================================================================================
// 分析情報編集チェック処理
// 作成者：TT.k-katayama
// 作成日：2010/10/13
// 概要  ：分析情報を退避させる
//========================================================================================
function funBunsekiDataHensyuCheck() {
    var frm = document.frm00;                 //ﾌｫｰﾑへの参照

    //入力項目が編集されている場合、1を返却する
	if ( frm.hidGenryoName.value != frm.txtGenryoName.value ) {
		return 1;
	}
	if ( frm.hidSakusan.value != frm.txtSakusan.value ) {
		return 1;
	}
	if ( frm.hidShokuen.value != frm.txtShokuen.value ) {
		return 1;
	}
	if ( frm.hidSosan.value != frm.txtSosan.value ) {
		return 1;
	}
	if ( frm.hidGanyu.value != frm.txtGanyu.value ) {
		return 1;
	}
// ADD start 20121005 QP@20505 No.24
	if ( frm.hidMsg.value != frm.txtMsg.value ) {
		return 1;
	}
// ADD end 20121005 QP@20505 No.24
	if ( frm.hidHyojian.value != frm.txtHyojian.value ) {
		return 1;
	}
	if ( frm.hidTenkabutu.value != frm.txtTenkabutu.value ) {
		return 1;
	}
	if ( frm.hidEiyoNo1.value != frm.txtEiyoNo[0].value ) {
		return 1;
	}
	if ( frm.hidEiyoNo2.value != frm.txtEiyoNo[1].value ) {
		return 1;
	}
	if ( frm.hidEiyoNo3.value != frm.txtEiyoNo[2].value ) {
		return 1;
	}
	if ( frm.hidEiyoNo4.value != frm.txtEiyoNo[3].value ) {
		return 1;
	}
	if ( frm.hidEiyoNo5.value != frm.txtEiyoNo[4].value ) {
		return 1;
	}
	if ( frm.hidWariai1.value != frm.txtWariai[0].value ) {
		return 1;
	}
	if ( frm.hidWariai2.value != frm.txtWariai[1].value ) {
		return 1;
	}
	if ( frm.hidWariai3.value != frm.txtWariai[2].value ) {
		return 1;
	}
	if ( frm.hidWariai4.value != frm.txtWariai[3].value ) {
		return 1;
	}
	if ( frm.hidWariai5.value != frm.txtWariai[4].value ) {
		return 1;
	}
	if ( frm.hidMemo.value != frm.txtMemo.value ) {
		return 1;
	}
	if ( frm.hidKakuteiCd.value != frm.txtKakuteiCd.value ) {
		return 1;
	}

	return 0;

}

//========================================================================================
// 更新情報確認処理
// 作成者：TT.k-katayama
// 作成日：2010/10/13
// 概要  ：更新情報を確認する
//========================================================================================
function funKoshinJohoCheck() {
    var frm = document.frm00;                 //ﾌｫｰﾑへの参照
    var retFlg = 0;
    var confMsg = "確認済みデータのため確認者をクリアして保存しますがよろしいでしょうか？";

	//確認チェック

	if(blnCheckKakunin){
		if (frm.chkKakunin.checked) {
			//分析データ編集チェック
			if (funBunsekiDataHensyuCheck() == 1) {
				//分析データが編集されている場合

				//YES・NO判定
			    if (funConfMsgBox(confMsg) == ConBtnYes){
			    	//「はい」押下
			    	retFlg = 0;
			    } else {
			    	//「いいえ」押下
			    	retFlg = 2;
			    }

			} else {
				//分析データが未編集の場合
		    	retFlg = 1;

			}
		} else {
	    	retFlg = 0;
		}
	}
	else{
		if(frm.chkKakunin.checked){
			retFlg = 1;
		}
		else{
			retFlg = 0;
		}
	}

	return retFlg;

}

//========================================================================================
// 退避値戻し処理
// 作成者：TT.k-katayama
// 作成日：2010/10/13
// 概要  ：退避していた分析情報を戻す
//========================================================================================
function funBunsekiDataBack() {
    var frm = document.frm00;                 //ﾌｫｰﾑへの参照

//20160817  KPX@1502111 MOD start
//    frm.chkKakunin.checked = frm.hidKakunin.value;
    frm.chkKakunin.checked = blnCheckKakunin;
//20160817  KPX@1502111 MOD end
    frm.txtCheckDt.value = frm.hidCheckDt.value;
	frm.txtCheckName.value = frm.hidCheckName.value;

}
//add end --------------------------------------------------------------------------------------

//20160610  KPX@1502111_No.5 ADD start
//========================================================================================
//新規原料の連携情報取得・表示処理
//作成者：TT.kitazawa
//作成日：2016/06/10
//概要  ：新規原料表示で自家原料の連携情報を取得し、表示する
//========================================================================================
function funRenkeiDsp() {

    var frm = document.frm00;                 //ﾌｫｰﾑへの参照
    var XmlId = "JW821";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2260");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2260I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2260O);

    var FuncIdAry2 = new Array(ConResult,ConUserInfo,"FGEN2270");
    var xmlReqAry2 = new Array(xmlUSERINFO_I,xmlFGEN2270I);
    var xmlResAry2 = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2270O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJW821, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //データが取得できた時
    if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true" ) {
    	frm.txtShainCd.value = funXmlRead(xmlResAry[2], "cd_shain", 0);
    	frm.txtNen.value = funXmlRead(xmlResAry[2], "nen", 0);
    	frm.txtOiNo.value = funXmlRead(xmlResAry[2], "no_oi", 0);
    	frm.txtEdaNo.value = funXmlRead(xmlResAry[2], "no_eda", 0);
    	frm.hidShisakuSeq.value = funXmlRead(xmlResAry[2], "seq_shisaku", 0);

        //引数をXMLﾌｧｲﾙに設定
        if (funReadyOutput("JW822", xmlReqAry2, 1) == false) {
            funClearRunMessage();
            return false;
        }

        //ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
        if (funAjaxConnection(XmlId, FuncIdAry2, xmlJW821, xmlReqAry2, xmlResAry2, 1) == false) {
            return false;
        }

    	//試作名
        frm.txtHinNm.value = funXmlRead(xmlResAry2[2], "nm_hin", 0);
        //サンプルNo
        frm.txtSampleNo.value = funGetSampleNo(frm.hidShisakuSeq.value, xmlResAry2[2]);

    }
    return true;
}

//========================================================================================
//新規原料の連携情報取得・表示処理
//作成者：TT.kitazawa
//作成日：2016/06/10
//引数  ：①shisakuSeq  ：試作SEQ
//      ：②xmlData     ：XML
//戻り値：サンプルNo
//概要  ：試作SEQに該当するサンプルNoを取得する
//========================================================================================
function funGetSampleNo(shisakuSeq, xmlData) {

	var reccnt;                  //設定XMLの件数
    var i;
    var retSampleNo = "";        //戻り値

    //件数取得
    reccnt = funGetLength(xmlData);

    //ﾃﾞｰﾀが存在しない場合
    if (reccnt == 0) {
        //処理を中断
        return retSampleNo;
    }
    //サンプルNoを取得していない場合
    if (funXmlRead(xmlData, "flg_return", 0) == "" ) {
    	return retSampleNo;
    }

    for (i = 0; i < reccnt; i++) {
        if (funXmlRead(xmlData, "seq_shisaku", i) == shisakuSeq) {
        	retSampleNo = funXmlRead(xmlData, "nm_sample", i);
        	break;
        }
    }

    return retSampleNo;
}

//20160610  KPX@1502111_No.5 ADD end
