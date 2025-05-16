
//画面設定(BODYのﾛｰﾄﾞ後にﾀｲﾄﾙの設定が行えないため、BODYのﾛｰﾄﾞ前に行う)
funInitScreen(ConTantoSearchId);

//========================================================================================
// 初期表示処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
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

    //画面の初期化
    funClear();

    //戻り値の初期化
    window.returnValue = "";

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    return true;

}

//========================================================================================
// 会社コンボボックス連動処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 引数  ：なし
// 概要  ：会社に紐付く部署コンボボックスを生成する
//========================================================================================
function funChangeKaisha() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP9010";
    // MOD 2013/11/7 QP@30154 okano start
//	    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA290");
//	    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA290I);
//	    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA290O);
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA290","SA050");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA290I,xmlSA050I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA290O,xmlSA050O);
    // MOD 2013/11/7 QP@30154 okano end

    if (frm.ddlKaisha.selectedIndex == 0) {
        funClearSelect(frm.ddlBusho, 2);
        // ADD 2013/11/7 QP@30154 okano start
        funClearSelect(frm.ddlGroup, 2);
        // ADD 2013/11/7 QP@30154 okano end
        return true;
    }

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //部署情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP9010, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽの作成
    funCreateComboBox(frm.ddlBusho, xmlResAry[2], 2);
    // ADD 2013/11/7 QP@30154 okano start
    funCreateComboBox(frm.ddlGroup, xmlResAry[3], 3);
    // ADD 2013/11/7 QP@30154 okano end

    return true;

}

//========================================================================================
// グループコンボボックス連動処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 引数  ：なし
// 概要  ：グループに紐付くチームコンボボックスを生成する
//========================================================================================
function funChangeGroup() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP9020";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA080");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA080I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA080O);

    if (frm.ddlGroup.selectedIndex == 0) {
        funClearSelect(frm.ddlTeam, 2);
        return true;
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
    funCreateComboBox(frm.ddlTeam, xmlResAry[2], 4);

    return true;

}

//========================================================================================
// 検索ボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 引数  ：なし
// 概要  ：担当者データの検索を行う
//========================================================================================
function funSearch() {

    //次ﾍﾟｰｼﾞの設定
    funSetCurrentPage(1);

    //指定ﾍﾟｰｼﾞのﾃﾞｰﾀ取得
    funSearchData();
}

//========================================================================================
// 検索処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 引数  ：なし
// 概要  ：担当者データの検索を行う
//========================================================================================
function funSearchData() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP0920";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA240");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA240I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA240O);
    var RecCnt;
    var PageCnt;
    var ListMaxRow;

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //選択行の初期化
    funSetCurrentRow("");

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        //一覧のｸﾘｱ
        funClearList()
        funClearRunMessage();
        return false;
    }

    //検索処理を実行する
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0920, xmlReqAry, xmlResAry, 1) == false) {
        //一覧のｸﾘｱ
        funClearList()
        return false;
    }

    //ﾃﾞｰﾀ件数、ﾍﾟｰｼﾞﾘﾝｸの設定
    spnRecInfo.style.display = "block";
    RecCnt = funXmlRead(xmlResAry[2], "max_row", 0);
    spnRecCnt.innerText = RecCnt;
    ListMaxRow = funXmlRead(xmlResAry[2], "list_max_row", 0);
    spnRowMax.innerText = ListMaxRow;
    PageCnt = Math.ceil(RecCnt / ListMaxRow);
    funCreatePageLink(funGetCurrentPage(), PageCnt, "divPage", "tblList");
    spnCurPage.innerText = funGetCurrentPage() + "／" + PageCnt + "ページ";

    //ﾃﾞｰﾀ件数のﾁｪｯｸ
    if (RecCnt > 0 && funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
        //表示
        tblList.style.display = "block";
    } else {
        //非表示
        tblList.style.display = "none";

        //処理中ﾒｯｾｰｼﾞ非表示
        funClearRunMessage();
    }

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    return true;

}

//========================================================================================
// クリアボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 引数  ：なし
// 概要  ：画面を初期化する
//========================================================================================
function funClear() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //画面の初期化
    frm.reset();

    //一覧のｸﾘｱ
    funClearList()

    //ｺﾝﾎﾞﾎﾞｯｸｽの再設定
    frm.ddlKaisha.selectedIndex = 0;
    funClearSelect(frm.ddlBusho, 2);
    // MOD 2013/11/7 QP@30154 okano start
//    	frm.ddlGroup.selectedIndex = 0;
    funClearSelect(frm.ddlGroup, 2);
    // MOD 2013/11/7 QP@30154 okano end
    funClearSelect(frm.ddlTeam, 2);

    //ﾌｫｰｶｽ設定
    frm.txtUserId.focus();

    return true;

}

//========================================================================================
// 一覧クリア処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 引数  ：なし
// 概要  ：一覧の情報を初期化する
//========================================================================================
function funClearList() {

    //一覧のｸﾘｱ
    xmlSA240O.src = "";
    tblList.style.display = "none";
    spnRecCnt.innerText = "0";
    funSetCurrentRow("");
    funCreatePageLink(1, 1, "divPage", "tblList");
    funClearCurrentRow(tblList);
    funSetCurrentPage(1);
    spnRecInfo.style.display = "none";

}

//========================================================================================
// 選択ボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 概要  ：選択データを呼び出し元画面に返す
//========================================================================================
function funSelect() {

    //行が選択されていない場合
    if (funGetCurrentRow().toString() == "") {
        funErrorMsgBox(E000002);
        return false;
    }

    //戻り値の設定
    window.returnValue = funXmlRead(xmlSA240O, "id_user", funGetCurrentRow());

    //画面を閉じる
    close(self);

}

//========================================================================================
// 終了ボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 概要  ：画面を閉じる
//========================================================================================
function funClose() {

    window.returnValue = "";

    //画面を閉じる
    close(self);

}

//========================================================================================
// 画面情報取得処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 引数  ：①mode  ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：ユーザのセッション情報、コンボボックスの情報を取得する
//========================================================================================
function funGetInfo(mode) {

    var opener = window.dialogArguments;      //引数の取得
    var opener_form = opener.document.forms(0);
    var frm = document.frm00;                 //ﾌｫｰﾑへの参照
    var XmlId = "JSP0910";
    // MOD 2013/11/7 QP@30154 okano start
//	    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA050","SA140");
//	    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA050I,xmlSA140I);
//	    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA050O,xmlSA140O);
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA140");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA140I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA140O);
    // MOD 2013/11/7 QP@30154 okano end

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0910, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //ﾕｰｻﾞ情報表示
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //ｺﾝﾎﾞﾎﾞｯｸｽの作成
    // MOD 2013/11/7 QP@30154 okano start
//	    funCreateComboBox(frm.ddlKaisha, xmlResAry[3], 1);
//	    funCreateComboBox(frm.ddlGroup, xmlResAry[2], 3);
    funCreateComboBox(frm.ddlKaisha, xmlResAry[2], 1);
    // MOD 2013/11/7 QP@30154 okano end

    return true;

}

//========================================================================================
// XMLファイルに書き込み
// 作成者：M.Jinbo
// 作成日：2009/04/06
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
        if (XmlId.toString() == "JSP0910") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                // MOD 2013/11/7 QP@30154 okano start
//	                case 1:    //SA050
//	                    funXmlWrite(reqAry[i], "id_user", "", 0);
//	                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
//	                    break;
//	                case 2:    //SA140
//	                    funXmlWrite(reqAry[i], "id_user", "", 0);
//	                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
//	                    break;
                case 1:    //SA140
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                    break;
                // MOD 2013/11/7 QP@30154 okano end
            }

        //検索ﾎﾞﾀﾝ押下
        } else if (XmlId.toString() == "JSP0920"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA240
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_busho", frm.ddlBusho.options[frm.ddlBusho.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_team", frm.ddlTeam.options[frm.ddlTeam.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "no_page", funGetCurrentPage(), 0);
                    break;
            }

        //会社ｺﾝﾎﾞ選択
        } else if (XmlId.toString() == "JSP9010") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA290
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                    break;
                // ADD 2013/11/7 QP@30154 okano start
                case 2:    //SA050
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                    break;
                // ADD 2013/11/7 QP@30154 okano end
            }

        //ｸﾞﾙｰﾌﾟｺﾝﾎﾞ選択
        } else if (XmlId.toString() == "JSP9020") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA080
                    funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                    break;
            }
        }
    }

    return true;

}

//========================================================================================
// コンボボックス作成処理
// 作成者：M.Jinbo
// 作成日：2009/04/06
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
        case 1:    //部署ﾏｽﾀ(会社)
            atbName = "nm_kaisha";
            atbCd = "cd_kaisha";
            break;
        case 2:    //部署ﾏｽﾀ(部署)
            atbName = "nm_busho";
            atbCd = "cd_busho";
            break;
        case 3:    //ｸﾞﾙｰﾌﾟﾏｽﾀ
            atbName = "nm_group";
            atbCd = "cd_group";
            break;
        case 4:    //ﾁｰﾑﾏｽﾀ
            atbName = "nm_team";
            atbCd = "cd_team";
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
// ページ遷移
// 作成者：M.Jinbo
// 作成日：2009/04/06
// 引数  ：①NextPage   ：次のページ番号
// 戻り値：なし
// 概要  ：指定ページの情報を表示する。
//========================================================================================
function funPageMove(NextPage) {

    //次ﾍﾟｰｼﾞの設定
    funSetCurrentPage(NextPage);

    //指定ﾍﾟｰｼﾞのﾃﾞｰﾀ取得
    funSearchData();
}

