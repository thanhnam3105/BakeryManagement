//========================================================================================
// 初期表示処理
// 作成者：M.Jinbo
// 作成日：2009/03/25
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

    //画面設定
    funInitScreen(ConShisakuListId);

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
// 製法Noの入力制御処理
// 作成者：M.Jinbo
// 作成日：2009/03/25
// 引数  ：なし
// 概要  ：製法Noの入力制御を行う
//========================================================================================
function funUseSeihoNo(obj) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    if (obj.checked) {
        //製法No入力可能
        funItemDisabled(frm.txtSeihouNo, false);
    } else {
        //製法No入力不可
        funItemDisabled(frm.txtSeihouNo, true);
    }

}

//========================================================================================
// 所属チームコンボボックス連動処理
// 作成者：M.Jinbo
// 作成日：2009/03/25
// 引数  ：なし
// 概要  ：所属グループに紐付く所属チームコンボボックスを生成する
//========================================================================================
function funChangeGroup() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP9020";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA080");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA080I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA080O);

    if (frm.ddlGroup.selectedIndex == 0) {
        //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
        funClearSelect(frm.ddlTeam, 2);
        funClearSelect(frm.ddlTanto, 2);
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
    funCreateComboBox(frm.ddlTeam, xmlResAry[2], 2);
    funClearSelect(frm.ddlTanto, 2);

    return true;

}

//========================================================================================
// 担当者コンボボックス連動処理
// 作成者：M.Jinbo
// 作成日：2009/03/25
// 引数  ：なし
// 概要  ：所属グループ、所属チームに紐付く担当者コンボボックスを生成する
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
    funCreateComboBox(frm.ddlTanto, xmlResAry[2], 3);

    return true;

}

//========================================================================================
// 検索ボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/03/25
// 引数  ：なし
// 概要  ：試作データの検索を行う
//========================================================================================
function funSearch() {

    //ﾍﾟｰｼﾞの設定
    funSetCurrentPage(1);

    //ﾃﾞｰﾀ取得
    funDataSearch();

}

//========================================================================================
// 検索処理
// 作成者：M.Jinbo
// 作成日：2009/03/25
// 引数  ：なし
// 概要  ：試作データの検索を行う
//========================================================================================
function funDataSearch() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP0330";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA200");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA200I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA200O);
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
        funClearList();
        funClearRunMessage();
        return false;
    }

    //検索条件に一致する試作ﾃﾞｰﾀを取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0330, xmlReqAry, xmlResAry, 1) == false) {
        //一覧のｸﾘｱ
        funClearList();
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

    return true;

}

//========================================================================================
// クリアボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/03/25
// 引数  ：なし
// 概要  ：画面を初期化する
//========================================================================================
function funClear() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //画面の初期化
    frm.reset();
    frm.hidJwsMode.value = "";
    funItemDisabled(frm.txtSeihouNo, true);
    frm.ddlUser.selectedIndex = 0;
    frm.ddlGenre.selectedIndex = 0;
    frm.ddlHyoujiName.selectedIndex = 0;
//    frm.ddlYouto.selectedIndex = 0;
//    frm.ddlGenryo.selectedIndex = 0;
    frm.txtYouto.value = "";
    frm.txtGenryo.value = "";

    //一覧のｸﾘｱ
    funClearList();

    //ﾃﾞﾌｫﾙﾄのｺﾝﾎﾞﾎﾞｯｸｽ設定値を読み込む
    xmlSA050O.load(xmlSA050);
    xmlSA080O.load(xmlSA080);
    xmlSA250O.load(xmlSA250);

    //ｺﾝﾎﾞﾎﾞｯｸｽの再設定
    funCreateComboBox(frm.ddlGroup, xmlSA050O, 1);
    funDefaultIndex(frm.ddlGroup, 1);
    funCreateComboBox(frm.ddlTeam, xmlSA080O, 2);
    funDefaultIndex(frm.ddlTeam, 2);
    funCreateComboBox(frm.ddlTanto, xmlSA250O, 3);
    funDefaultIndex(frm.ddlTanto, 3);

    //ﾌｫｰｶｽ設定
    frm.chkTaisho[0].focus();

    funSaveKengenInfo();

    return true;

}

//========================================================================================
// 一覧クリア処理
// 作成者：M.Jinbo
// 作成日：2009/03/25
// 引数  ：なし
// 概要  ：一覧の情報を初期化する
//========================================================================================
function funClearList() {

    //一覧のｸﾘｱ
    xmlSA200O.src = "";
    tblList.style.display = "none";
    spnRecCnt.innerText = "0";
    funSetCurrentRow("");
    funCreatePageLink(1, 1, "divPage", "tblList");
    funClearCurrentRow(tblList);
    funSetCurrentPage(1);
    spnRecInfo.style.display = "none";

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
// 検索条件クリアボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/03/25
// 引数  ：なし
// 概要  ：検索条件を初期化する
//========================================================================================
function funJokenClear() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //画面の初期化(試作No～ｷｰﾜｰﾄﾞ検索までをｸﾘｱ)
    frm.txtShisakuNo.value = "";
    frm.txtSeihouNo.value = "";
    frm.txtShisakuName.value = "";
    frm.ddlGroup.selectedIndex = 0;
    frm.ddlTeam.selectedIndex = 0;
    frm.ddlTanto.selectedIndex = 0;
    frm.ddlUser.selectedIndex = 0;
    frm.ddlGenre.selectedIndex = 0;
    frm.ddlHyoujiName.selectedIndex = 0;
//    frm.ddlYouto.selectedIndex = 0;
//    frm.ddlGenryo.selectedIndex = 0;
    frm.txtYouto.value = "";
    frm.txtGenryo.value = "";
    frm.txtKeyword.value = "";

    //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
    funClearSelect(frm.ddlTeam, 2);
    funClearSelect(frm.ddlTanto, 2);

    //一覧のｸﾘｱ
    xmlSA200O.src = "";
    tblList.style.display = "none";
    spnRecCnt.innerText = "0";
    funCreatePageLink(1, 1, "divPage", "tblList");
    funClearCurrentRow(tblList);
    funSetCurrentPage(1);
    spnRecInfo.style.display = "none";

    return true;

}

//========================================================================================
// 試作データ画面起動処理
// 作成者：M.Jinbo
// 作成日：2009/03/25
// 引数  ：①StartMode ：起動モード
//           1:新規、2:詳細、3:製法支援コピー
// 概要  ：試作データ画面を起動する
//========================================================================================
function funJavaWebStart(StartMode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP0340";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA550");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA550I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA550O);

    //新規以外で行が選択されていない場合
    if (StartMode != 2 && funGetCurrentRow().toString() == "") {
        funErrorMsgBox(E000002);
        return false;
    }

    //VMのﾊﾞｰｼﾞｮﾝﾁｪｯｸを行う
    funGetJWSInstall();
    if (funCheckVersion() == false) {
        frm.hidJwsMode.value = "";
        return false;
    }

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //起動ﾓｰﾄﾞの設定
    switch (StartMode) {
        case 1:    //試作ﾃﾞｰﾀ画面(詳細)
            frm.hidJwsMode.value = ConGmnIdShisakuDataEdit;
            break;
        case 2:    //試作ﾃﾞｰﾀ画面(新規)
            frm.hidJwsMode.value = ConGmnIdShisakuDataNew;
            break;
        case 3:    //試作ﾃﾞｰﾀ画面(製法支援ｺﾋﾟｰ)
            frm.hidJwsMode.value = ConGmnIdSeihoCopy;
            break;
    }

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        frm.hidJwsMode.value = "";
        return false;
    }

    //JNLPﾌｧｲﾙを作成する
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0340, xmlReqAry, xmlResAry, 1) == false) {
        //【QP@20505】No.39 2012/09/25 TT H.Shima ADD Start
        funAccessLogSave(StartMode,1);
        //【QP@20505】No.39 2012/09/25 TT H.Shima ADD End
        frm.hidJwsMode.value = "";
        return false;
    }

//【QP@20505】No.39 2012/09/20 TT H.Shima ADD Start
    funAccessLogSave(StartMode,0);
//【QP@20505】No.39 2012/09/20 TT H.Shima ADD End

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    var width = screen.width / 2 - 200;
    var height = screen.height / 2 + 50;

    //試作ﾃﾞｰﾀ画面を起動する
    window.open("../../jws/" + funXmlRead(xmlResAry[2], "jnlp_path", 0), "", "top=" + width + ",left=" + height + ",width=100,height=100");

    return true;

}

//========================================================================================
// 画面情報取得処理
// 作成者：M.Jinbo
// 作成日：2009/03/25
// 引数  ：①mode  ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：ユーザのセッション情報、コンボボックスの情報を取得する
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP0310";
//    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA030","SA050","SA070","SA080","SA120","SA230","SA250","SA280");
//    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA030I,xmlSA050I,xmlSA070I,xmlSA080I,xmlSA120I,xmlSA230I,xmlSA250I,xmlSA280I);
//    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA030O,xmlSA050O,xmlSA070O,xmlSA080O,xmlSA120O,xmlSA230O,xmlSA250O,xmlSA280O);
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA030","SA050","SA070","SA080","SA120","SA250");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA030I,xmlSA050I,xmlSA070I,xmlSA080I,xmlSA120I,xmlSA250I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA030O,xmlSA050O,xmlSA070O,xmlSA080O,xmlSA120O,xmlSA250O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0310, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //ﾕｰｻﾞ情報表示
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //権限関連の処理を行う
    funSaveKengenInfo();

    //ｺﾝﾎﾞﾎﾞｯｸｽの作成
    funCreateComboBox(frm.ddlUser, xmlResAry[2], 4);
    funCreateComboBox(frm.ddlGenre, xmlResAry[4], 4);
    funCreateComboBox(frm.ddlHyoujiName, xmlResAry[6], 4);
//    funCreateComboBox(frm.ddlYouto, xmlResAry[7], 4);
//    funCreateComboBox(frm.ddlGenryo, xmlResAry[9], 4);

    //ﾃﾞﾌｫﾙﾄのｺﾝﾎﾞﾎﾞｯｸｽ設定値を退避
    xmlSA050.load(xmlResAry[3]);
    xmlSA080.load(xmlResAry[5]);
//    xmlSA250.load(xmlResAry[8]);
    xmlSA250.load(xmlResAry[7]);

    return true;

}

//========================================================================================
// 権限関連処理
// 作成者：M.Jinbo
// 作成日：2009/03/25
// 概要  ：権限情報の退避、画面制御を行う
//========================================================================================
function funSaveKengenInfo() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var obj = xmlUSERINFO_O;
    var GamenId;
    var KinoId;
    var reccnt;
    var i;

    //詳細、新規、製法支援ｺﾋﾟｰﾎﾞﾀﾝの制御
    frm.btnEdit.disabled = true;
    frm.btnNew.disabled = true;
    frm.btnCopy.disabled = true;

    //原価試算ﾎﾞﾀﾝの制御
    frm.btnGenka.disabled = true;

    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        GamenId = funXmlRead(obj, "id_gamen", i);
        KinoId = funXmlRead(obj, "id_kino", i);

        //試作ﾃﾞｰﾀ一覧
        if (GamenId.toString() == ConGmnIdShisakuList.toString()) {
            hidShisakuListKengen.value = funXmlRead(obj, "id_data", i);
            //原価試算
            if (KinoId.toString() == ConFuncIdGenkaShisan.toString()) {
                frm.chkTaisho[3].checked = "true";

            }

        //試作ﾃﾞｰﾀ画面(詳細)
        } else if (GamenId.toString() == ConGmnIdShisakuDataEdit.toString()) {
            //閲覧
            if (KinoId.toString() == ConFuncIdRead.toString()) {
                frm.btnEdit.disabled = false;

            //編集
            } else if (KinoId.toString() == ConFuncIdEdit.toString()) {
                frm.btnEdit.disabled = false;
            }

        //試作ﾃﾞｰﾀ画面(新規)
        } else if (GamenId.toString() == ConGmnIdShisakuDataNew.toString()) {
            //編集
            if (KinoId.toString() == ConFuncIdNew.toString()) {
                frm.btnNew.disabled = false;
            }

        //試作ﾃﾞｰﾀ画面(製法支援ｺﾋﾟｰ)
        } else if (GamenId.toString() == ConGmnIdSeihoCopy.toString()) {
            //編集
            if (KinoId.toString() == ConFuncIdEdit.toString()) {
                frm.btnCopy.disabled = false;
            }
        }

        //原価試算画面
        if (GamenId.toString() == ConGmnIdGenkaShisan.toString()) {
            frm.btnGenka.disabled = false;
        }
    }
    return true;

}

//========================================================================================
// XMLファイルに書き込み
// 作成者：M.Jinbo
// 作成日：2009/03/25
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
        if (XmlId.toString() == "JSP0310") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA030
                    funXmlWrite(reqAry[i], "cd_category", "K_yuza", 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShisakuList, 0);
                    break;
                case 2:    //SA050
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShisakuList, 0);
                    break;
                case 3:    //SA070
                    funXmlWrite(reqAry[i], "cd_category", "K_jyanru", 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShisakuList, 0);
                    break;
                case 4:    //SA080
                    funXmlWrite(reqAry[i], "kbn_shori", "2", 0);
                    funXmlWrite(reqAry[i], "cd_group", "", 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShisakuList, 0);
                    break;
                case 5:    //SA120
                    funXmlWrite(reqAry[i], "cd_category", "K_ikatuhyouzi", 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShisakuList, 0);
                    break;
//                case 6:    //SA230
//                    funXmlWrite(reqAry[i], "cd_category", "K_yoto", 0);
//                    funXmlWrite(reqAry[i], "id_user", "", 0);
//                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShisakuList, 0);
//                    break;
//                case 7:    //SA250
                case 6:    //SA250
                    funXmlWrite(reqAry[i], "kbn_shori", "2", 0);
                    funXmlWrite(reqAry[i], "cd_group", "", 0);
                    funXmlWrite(reqAry[i], "cd_team", "", 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShisakuList, 0);
                    break;
//                case 8:    //SA280
//                    funXmlWrite(reqAry[i], "cd_category", "K_tokucyogenryo", 0);
//                    funXmlWrite(reqAry[i], "id_user", "", 0);
//                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShisakuList, 0);
//                    break;
            }

        //ﾁｰﾑｺﾝﾎﾞ選択
        } else if (XmlId.toString() == "JSP0320"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA250
                    funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_team", frm.ddlTeam.options[frm.ddlTeam.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "kbn_shori", "1", 0);
                    funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShisakuList, 0);
                    // ADD 2015/03/30 TT.Kitazawa【QP@40812】No.24 start
                    // 担当者コンボボックス設定：選択したグループの会社CD（空行除く）
                    funXmlWrite(reqAry[i], "cd_kaisha", funXmlRead(xmlSA050, "cd_kaisha",frm.ddlGroup.selectedIndex-1), 0);
                    // ADD 2015/03/30 TT.Kitazawa【QP@40812】No.24 end
                    break;
            }

        //検索ﾎﾞﾀﾝ押下
        } else if (XmlId.toString() == "JSP0330"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA200
                    if (frm.chkTaisho[0].checked) {
                        funXmlWrite(reqAry[i], "kbn_joken1", "1", 0);
                    } else {
                        funXmlWrite(reqAry[i], "kbn_joken1", "", 0);
                    }
                    if (frm.chkTaisho[1].checked) {
                        funXmlWrite(reqAry[i], "kbn_joken2", "1", 0);
                    } else {
                        funXmlWrite(reqAry[i], "kbn_joken2", "", 0);
                    }
                    if (frm.chkTaisho[2].checked) {
                        funXmlWrite(reqAry[i], "kbn_joken3", "1", 0);
                    } else {
                        funXmlWrite(reqAry[i], "kbn_joken3", "", 0);
                    }

               //2009/10/27 TT.Y.NISHIGAWA ADD START   [原価試算：原価依頼を検索対象]
                    if (frm.chkTaisho[3].checked) {
                        funXmlWrite(reqAry[i], "kbn_joken4", "1", 0);
                    } else {
                        funXmlWrite(reqAry[i], "kbn_joken4", "", 0);
                    }
               //2009/10/27 TT.A.ISONO ADD END   [原価試算：原価依頼を検索対象]

                    funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_team", frm.ddlTeam.options[frm.ddlTeam.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_tanto", frm.ddlTanto.options[frm.ddlTanto.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_user", frm.ddlUser.options[frm.ddlUser.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_genre", frm.ddlGenre.options[frm.ddlGenre.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_ikatu", frm.ddlHyoujiName.options[frm.ddlHyoujiName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "no_page", funGetCurrentPage(), 0);
                    break;
            }

        //JNLP作成
        } else if (XmlId.toString() == "JSP0340"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA550
                    funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 1), 0);
                    if (frm.hidJwsMode.value == ConGmnIdShisakuDataNew) {
                        funXmlWrite(reqAry[i], "no_shisaku", "0-0-0", 0);
                    } else {
                        funXmlWrite(reqAry[i], "no_shisaku", funXmlRead(xmlSA200O, "no_shisaku", funGetCurrentRow()), 0);
                    }
                    funXmlWrite(reqAry[i], "mode", frm.hidJwsMode.value, 0);
                    break;
            }

        //ｸﾞﾙｰﾌﾟｺﾝﾎﾞ選択
        } else if (XmlId.toString() == "JSP9020"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA080
                    funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "kbn_shori", "1", 0);
                    funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShisakuList, 0);
                    break;
            }
        }

        //原価試算画面起動情報通知
        else if (XmlId.toString() == "RGEN1090"){

            var get_shisaku;   // 試作コード取得用
            var put_shisaku;   // 試作コード送信用

            // XMLより試作コード取得
            no_shisaku = funXmlRead(xmlSA200O, "no_shisaku", funGetCurrentRow());

            // 試作コード置換【社員CD::年:::追番】
            put_shisaku = no_shisaku.replace(/-/g,":::");

            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", put_shisaku, 0);
                    break;
            }
        }
        //【QP@20505】No.39 2012/09/20 TT H.Shima ADD Start
        //試作アクセスログ出力
        else if (XmlId.toString() == "JSP0350"){
        	switch (i) {
            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;
            case 1:    //SA530
            	var no_shisaku = funXmlRead(xmlSA200O, "no_shisaku", funGetCurrentRow());
            	var shisaku = no_shisaku.split("-");

	            funXmlWrite(reqAry[i], "cd_shain", shisaku[0], 0);
	            funXmlWrite(reqAry[i], "nen", shisaku[1], 0);
	            funXmlWrite(reqAry[i], "no_oi", shisaku[2], 0);

                funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                funXmlWrite(reqAry[i], "id_gamen", frm.hidJwsMode.value, 0);
                funXmlWrite(reqAry[i], "not_right", "NULL", 0);
                break;
        	}
        }
        else if (XmlId.toString() == "JSP0351"){
        	switch (i) {
            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;
            case 1:    //SA530
            	var no_shisaku = funXmlRead(xmlSA200O, "no_shisaku", funGetCurrentRow());
            	var shisaku = no_shisaku.split("-");

	            funXmlWrite(reqAry[i], "cd_shain", shisaku[0], 0);
	            funXmlWrite(reqAry[i], "nen", shisaku[1], 0);
	            funXmlWrite(reqAry[i], "no_oi", shisaku[2], 0);

                funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                funXmlWrite(reqAry[i], "id_gamen", frm.hidJwsMode.value, 0);
                funXmlWrite(reqAry[i], "not_right", 1, 0);
                break;
        	}
        }
      //【QP@20505】No.39 2012/09/20 TT H.Shima ADD End
    }

    return true;

}

//========================================================================================
// コンボボックス作成処理
// 作成者：M.Jinbo
// 作成日：2009/03/25
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②xmlData  ：設定XML
//       ：③mode     ：モード
// 概要  ：コンボボックスを作成する
//========================================================================================
function funCreateComboBox(obj, xmlData, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var reccnt;
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
            case 3:    //担当者ｺﾝﾎﾞ
                if (obj.options[i].value == funXmlRead(xmlUSERINFO_O, "id_user", 0)) {
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
// ＶＭバージョンチェック
// 作成者：M.Jinbo
// 作成日：2009/03/25
// 引数  ：なし
// 概要  ：サーバとクライアントのＶＭのバージョンを比較する
//========================================================================================
function funCheckVersion() {

//alert(myVersion);

	if (javawsInstalled == 1) {
        //同じﾊﾞｰｼﾞｮﾝがｲﾝｽﾄｰﾙされている場合
        return true;

    } else {
        //同じﾊﾞｰｼﾞｮﾝがｲﾝｽﾄｰﾙされていない場合


        if(myVersion == 6){
        	//if (funConfMsgBox(I000009) == ConBtnYes) {
	            //ｲﾝｽﾄｰﾗｰ起動
	            location.href = ConJreInstallUrl;
	        //}
        }
        else if(myVersion >= 7){
        	//if (funConfMsgBox(I000013) == ConBtnYes) {
	            //ｲﾝｽﾄｰﾗｰ起動
	            //location.href = ConJreInstallUrl_5;
	            location.href = ConJreInstallUrl_6;
	        //}
        }
        else{
        }

        //JREｲﾝｽﾄｰﾙ確認ﾒｯｾｰｼﾞ
        /*if (funConfMsgBox(I000009) == ConBtnYes) {
            //ｲﾝｽﾄｰﾗｰ起動
            location.href = ConJreInstallUrl;
        }*/

        return false;
    }

}

//========================================================================================
// ページ遷移
// 作成者：M.Jinbo
// 作成日：2009/03/13
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
// 原価試算画面へ遷移
// 作成者：Y.Nishigawa
// 作成日：2009/10/20
// 引数  ：なし
// 概要  ：原価試算画面へ遷移する
//========================================================================================
function funGenkaShisan() {

    //「$」記号格納
    var dara;

    //選択行の「$」記号取得
    dara = funXmlRead(xmlSA200O, "dara", funGetCurrentRow());

    //原価依頼行が選択されている場合
    if(dara == GenkaMark){

        //原価試算画面起動情報通知に成功
        if(funGenkaTuti(1)){
            //原価試算画面へ遷移
            //funUrlConnect("../SQ110GenkaShisan/GenkaShisan.jsp", "POST", document.frm00);

            window.open("../SQ110GenkaShisan/GenkaShisan.jsp","shisaquick_genka","menubar=no,resizable=yes");

        }
        //原価試算画面起動情報通知に失敗
        else{

        }

    }
    //原価依頼行が選択されていない場合
    else{
        //エラーメッセージを表示
        funErrorMsgBox(E000005);
        return false;
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
    var XmlId = "RGEN1090";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN1090, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    return true;

}

//========================================================================================
// 画面参照ログの追加
// 作成者：H.Shima
// 作成日：2012/9/20
// 引数  ：mode     ：モード  1,詳細 2,新規 3,製法支援コピー
//       ：kengen   ：権限 0,権限有 1,権限無
// 概要  ：参照画面と参照ユーザのログを保存する
//========================================================================================
function funAccessLogSave(mode, kengen) {
    var mId = new Array(["JSP0350"]		//権限有
    					  ,["JSP0351"]);//権限無
    var XmlId = mId[kengen];
    var mFuncIdAry = new Array(ConResult,ConUserInfo,"SA530");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA530I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA530O);

    //引数をXMLﾌｧｲﾙに設定
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}

	switch(mode){
	case 1:	//詳細
		if (funAjaxConnection(XmlId, mFuncIdAry, xmlSA530, xmlReqAry, xmlResAry, 1) == false) {
			return false;
		}
		break;
	case 2:	//新規
		break;
	case 3:	//製法支援コピー
		if (funAjaxConnection(XmlId, mFuncIdAry, xmlSA530, xmlReqAry, xmlResAry, 1) == false) {
			return false;
		}
		break;
	}

}