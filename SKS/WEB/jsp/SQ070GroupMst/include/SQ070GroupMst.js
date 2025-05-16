//========================================================================================
// 初期表示処理
// 作成者：M.Jinbo
// 作成日：2009/04/04
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

    //画面設定
    funInitScreen(ConGroupMstId);

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //画面情報を取得・設定
    if (funGetInfo(1) == false) {
        return false;
    }

    //画面の初期化
    funClear();

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    return true;

}

//========================================================================================
// 処理データ切り替え処理
// 作成者：M.Jinbo
// 作成日：2009/04/04
// 引数  ：なし
// 概要  ：画面の制御を行う
//========================================================================================
function funChangeMode() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //画面制御
    if (frm.rdoMode[0].checked) {
        //ｸﾞﾙｰﾌﾟ
        funItemReadOnly(frm.txtGroup, false);
        funItemReadOnly(frm.ddlTeam, true);
        funItemReadOnly(frm.txtTeam, true);
        // ADD 2013/10/24 QP@30154 okano start
        funItemReadOnly(frm.ddlKaisha, false);
        // ADD 2013/10/24 QP@30154 okano end

        //画面情報の設定
        if (frm.ddlGroup.selectedIndex == 0) {
            frm.txtGroup.value = "";
        } else {
            frm.txtGroup.value = frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value.split(ConDelimiter)[1];
        }
        funClearSelect(frm.ddlTeam, 2);
        frm.txtTeam.value = "";
        spnHisuG.innerHTML = "<font color=\"red\">（必須）</font>";
        spnHisuT.innerHTML = "";
        // ADD 2013/10/24 QP@30154 okano start
        spnHisuK.innerHTML = "<font color=\"red\">（必須）</font>";
        // ADD 2013/10/24 QP@30154 okano end

    } else {
        //ﾁｰﾑ
        funItemReadOnly(frm.txtGroup, true);
        funItemReadOnly(frm.ddlTeam, false);
        funItemReadOnly(frm.txtTeam, false);
        // ADD 2013/10/24 QP@30154 okano start
        funItemReadOnly(frm.ddlKaisha, true);
        // ADD 2013/10/24 QP@30154 okano end

        //画面情報の設定
        frm.txtGroup.value = "";
        spnHisuG.innerHTML = "";
        spnHisuT.innerHTML = "<font color=\"red\">（必須）</font>";
        // ADD 2013/10/24 QP@30154 okano start
        spnHisuK.innerHTML = "";
        // ADD 2013/10/24 QP@30154 okano end

        //ﾁｰﾑｺﾝﾎﾞ設定
        funChangeGroup();
    }

    return true;

}

//========================================================================================
// グループコンボボックス連動処理
// 作成者：M.Jinbo
// 作成日：2009/04/04
// 引数  ：なし
// 概要  ：グループに紐付くチームコンボボックスを生成する
//       ：選択されたグループコンボボックスの名称を設定する
//========================================================================================
function funChangeGroup() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP9020";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA080");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA080I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA080O);

    if (frm.ddlGroup.selectedIndex == 0) {
        //ｸﾞﾙｰﾌﾟ名のｸﾘｱ
        frm.txtGroup.value = "";
        //ﾁｰﾑのｸﾘｱ
        funClearSelect(frm.ddlTeam, 2);
        frm.txtTeam.value = "";
        // ADD 2013/10/24 QP@30154 okano start
        //会社ｺﾝﾎﾞﾎﾞｯｸｽの選択を初期化
        frm.ddlKaisha.selectedIndex = 0;
        // ADD 2013/10/24 QP@30154 okano end
        return true;
    }

    //名称の設定
    if (frm.rdoMode[0].checked) {
        //ｸﾞﾙｰﾌﾟ名を設定
        frm.txtGroup.value = frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value.split(ConDelimiter)[1];
    } else {
        //ﾁｰﾑ名のｸﾘｱ
        frm.txtTeam.value = "";
    }

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //ﾁｰﾑ情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP9020, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽの作成
    funCreateComboBox(frm.ddlTeam, xmlResAry[2], 2);
    // ADD 2013/10/24 QP@30154 okano start
    //会社ｺﾝﾎﾞﾎﾞｯｸｽを設定
    for (i = 0; i < frm.ddlKaisha.options.length; i++) {
    	if(frm.ddlKaisha.options[i].value.split(ConDelimiter)[0] == frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value.split(ConDelimiter)[2]) {
    		frm.ddlKaisha.selectedIndex = i;
    	}
    }
    // ADD 2013/10/24 QP@30154 okano end

    return true;

}

//========================================================================================
// チーム情報表示処理
// 作成者：M.Jinbo
// 作成日：2009/04/04
// 引数  ：なし
// 概要  ：選択されたチームコンボボックスの名称を設定する
//========================================================================================
function funChangeTeam() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    if (frm.ddlTeam.selectedIndex == 0) {
        //ﾁｰﾑ名のｸﾘｱ
        frm.txtTeam.value = "";
    } else {
        //ﾁｰﾑ名を設定
        frm.txtTeam.value = frm.ddlTeam.options[frm.ddlTeam.selectedIndex].value.split(ConDelimiter)[1];
    }

    return true;

}

//========================================================================================
// 登録、更新、削除ボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/04/04
// 引数  ：①mode ：処理区分
//           1：登録、2：更新、3：削除
// 概要  ：表示データの登録、更新、削除処理を行う
//========================================================================================
function funDataEdit(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId;
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);
    var dspMsg;
    var xmlObj;

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

    //対象ﾃﾞｰﾀの判定
    if (frm.rdoMode[0].checked) {
        //ｸﾞﾙｰﾌﾟの場合
        XmlId = "JSP0720";
        FuncIdAry[2] = "SA060";
        xmlReqAry[1] = xmlSA060I;
        xmlResAry[2] = xmlSA060O;
        xmlObj = xmlJSP0720;
    } else {
        //ﾁｰﾑの場合
        XmlId = "JSP0730";
        FuncIdAry[2] = "SA090";
        xmlReqAry[1] = xmlSA090I;
        xmlResAry[2] = xmlSA090O;
        xmlObj = xmlJSP0730;
    }

    //処理区分の退避
    frm.hidEditMode.value = mode;

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //登録、更新、削除処理を実行する
    if (funAjaxConnection(XmlId, FuncIdAry, xmlObj, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //完了ﾒｯｾｰｼﾞの表示
    dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {
        //正常
        funInfoMsgBox(dspMsg);

        //画面情報を取得・設定
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
// クリアボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/04/04
// 引数  ：なし
// 概要  ：画面を初期化する
//========================================================================================
function funClear() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //画面の初期化
    frm.reset();

    //画面制御
    frm.rdoMode[0].checked = true;
    funChangeMode();

    //ﾌｫｰｶｽ設定
    frm.rdoMode[0].focus();

    return true;

}

//========================================================================================
// 次画面遷移処理
// 作成者：M.Jinbo
// 作成日：2009/04/04
// 概要  ：次画面に遷移する
//========================================================================================
function funNext(mode) {

    var wUrl;

    //遷移先判定
    switch (mode) {
        case 0:    //ﾏｽﾀﾒﾆｭｰ
            wUrl = "../SQ030MstMenu/SQ030MstMenu.jsp";
            break;
    }

    //遷移
    funUrlConnect(wUrl, ConConectPost, document.frm00);

    return true;
}

//========================================================================================
// 画面情報取得処理
// 作成者：M.Jinbo
// 作成日：2009/04/04
// 引数  ：①mode  ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：ユーザのセッション情報、コンボボックスの情報を取得する
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP0710";
    // MOD 2013/10/24 QP@30154 okano start
//	    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA050");
//	    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA050I);
//	    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA050O);
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA050","SA140");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA050I,xmlSA140I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA050O,xmlSA140O);
    // MOD 2013/10/24 QP@30154 okano end

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0710, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //ﾕｰｻﾞ情報表示
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //権限関連の処理を行う
    funSaveKengenInfo();

    //ｺﾝﾎﾞﾎﾞｯｸｽの作成
    funCreateComboBox(frm.ddlGroup, xmlResAry[2], 1);
    // ADD 2013/10/24 QP@30154 okano start
    funCreateComboBox(frm.ddlKaisha, xmlResAry[3], 3);
    // ADD 2013/10/24 QP@30154 okano end

    return true;

}

//========================================================================================
// 権限関連処理
// 作成者：M.Jinbo
// 作成日：2009/04/04
// 概要  ：権限情報の退避、画面制御を行う
//========================================================================================
// 更新者：M.Jinbo
// 更新日：2009/06/24
// 内容  ：削除ボタンのコメント化(課題表№13)
//========================================================================================
function funSaveKengenInfo() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var obj = xmlUSERINFO_O;
    var GamenId;
    var KinoId;
    var reccnt;
    var i;

    //登録、更新、削除ﾎﾞﾀﾝの制御
    frm.btnInsert.disabled = true;
    frm.btnUpdate.disabled = true;
//    frm.btnDelete.disabled = true;

    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        GamenId = funXmlRead(obj, "id_gamen", i);
        KinoId = funXmlRead(obj, "id_kino", i);

        //ｸﾞﾙｰﾌﾟﾒﾝﾃﾅﾝｽ
        if (GamenId.toString() == ConGmnIdGroupMst.toString()) {
            //編集
            if (KinoId.toString() == ConFuncIdEdit.toString()) {
                frm.btnInsert.disabled = false;
                frm.btnUpdate.disabled = false;
//                frm.btnDelete.disabled = false;
            }
        }
    }

    return true;

}

//========================================================================================
// XMLファイルに書き込み
// 作成者：M.Jinbo
// 作成日：2009/04/04
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
        if (XmlId.toString() == "JSP0710") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA050
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGroupMst, 0);
                    break;
                // ADD 2013/10/24 QP@30154 okano start
                case 2:    //SA140
                    funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 1), 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGroupMst, 0);
                    break;
                // ADD 2013/10/24 QP@30154 okano end
            }

        //登録、更新、削除ﾎﾞﾀﾝ押下(ｸﾞﾙｰﾌﾟ)
        } else if (XmlId.toString() == "JSP0720"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA060
                    if (frm.hidEditMode.value == 1) {
                        funXmlWrite(reqAry[i], "kbn_shori", "1", 0);
                    } else if (frm.hidEditMode.value == 2) {
                        funXmlWrite(reqAry[i], "kbn_shori", "2", 0);
                    } else {
                        funXmlWrite(reqAry[i], "kbn_shori", "3", 0);
                    }
                    funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value.split(ConDelimiter)[0], 0);
                    funXmlWrite(reqAry[i], "nm_group", frm.txtGroup.value, 0);
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value.split(ConDelimiter)[0], 0);
                    break;
            }

        //登録、更新、削除ﾎﾞﾀﾝ押下(ﾁｰﾑ)
        } else if (XmlId.toString() == "JSP0730"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA090
                    if (frm.hidEditMode.value == 1) {
                        funXmlWrite(reqAry[i], "kbn_shori", "1", 0);
                    } else if (frm.hidEditMode.value == 2) {
                        funXmlWrite(reqAry[i], "kbn_shori", "2", 0);
                    } else {
                        funXmlWrite(reqAry[i], "kbn_shori", "3", 0);
                    }
                    funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value.split(ConDelimiter)[0], 0);
                    funXmlWrite(reqAry[i], "cd_team", frm.ddlTeam.options[frm.ddlTeam.selectedIndex].value.split(ConDelimiter)[0], 0);
                    funXmlWrite(reqAry[i], "nm_team", frm.txtTeam.value, 0);
                    break;
            }

        //ｸﾞﾙｰﾌﾟｺﾝﾎﾞ選択、ﾁｰﾑﾗｼﾞｵﾎﾞﾀﾝ選択
        } else if (XmlId.toString() == "JSP9020"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA080
                    funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value.split(ConDelimiter)[0], 0);
                    funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 1), 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGroupMst, 0);
                    break;
            }
        }
    }

    return true;

}

//========================================================================================
// コンボボックス作成処理
// 作成者：M.Jinbo
// 作成日：2009/04/04
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

    //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
    funClearSelect(obj, 2);

    //件数取得
    reccnt = funGetLength(xmlData);

    //ﾃﾞｰﾀが存在しない場合
    if (reccnt == 0) {
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
        // ADD 2013/10/24 QP@30154 okano start
        case 3:    //会社ﾏｽﾀ
            atbName = "nm_kaisha";
            atbCd = "cd_kaisha";
            break;
        // ADD 2013/10/24 QP@30154 okano end
    }

    for (i = 0; i < reccnt; i++) {
        if (funXmlRead(xmlData, atbCd, i) != "" && funXmlRead(xmlData, atbName, i) != "") {
            objNewOption = document.createElement("option");
            obj.options.add(objNewOption);
            objNewOption.innerText = ("000" + funXmlRead(xmlData, atbCd, i)).slice(-3) + "：" + funXmlRead(xmlData, atbName, i);
            // MOD 2013/10/24 QP@30154 okano start
//            objNewOption.value = funXmlRead(xmlData, atbCd, i) + ConDelimiter + funXmlRead(xmlData, atbName, i);
            if(mode == 1){
            	objNewOption.value = funXmlRead(xmlData, atbCd, i) + ConDelimiter + funXmlRead(xmlData, atbName, i) + ConDelimiter + funXmlRead(xmlData, "cd_kaisha", i);
            } else {
            	objNewOption.value = funXmlRead(xmlData, atbCd, i) + ConDelimiter + funXmlRead(xmlData, atbName, i);
            }
            // MOD 2013/10/24 QP@30154 okano end
        }
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽのﾃﾞﾌｫﾙﾄ表示
    obj.selectedIndex = 0;

    return true;
}

