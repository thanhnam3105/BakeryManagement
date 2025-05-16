//========================================================================================
// 初期表示処理
// 作成者：M.Jinbo
// 作成日：2009/04/01
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

    //画面設定
    funInitScreen(ConGenryoInfoMstId);

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

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.5
	//初期フォーカスの設定
	document.frm00.txtGenryoName.focus();
//add end --------------------------------------------------------------------------------------

    return true;

}

//========================================================================================
// 会社コンボボックス連動処理
// 作成者：M.Jinbo
// 作成日：2009/04/01
// 引数  ：なし
// 概要  ：会社に紐付く工場コンボボックスを生成する
//========================================================================================
function funChangeKaisha() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP0420";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA180");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA180I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA180O);

    if (frm.ddlKaisha.selectedIndex == -1) {
        //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
        funClearSelect(frm.ddlKojo, 2);
        return true;
    }

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //工場情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0420, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽの作成
    funCreateComboBox(frm.ddlKojo, xmlResAry[2], 2);

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
	frm.rdoShiyo[0].disabled = true;
	frm.rdoShiyo[1].checked = "true";
//add end --------------------------------------------------------------------------------------

    return true;

}

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
//========================================================================================
// 工場コンボボックス連動処理
// 作成者：k-katayama
// 作成日：2010/07/08
// 引数  ：なし
//========================================================================================
function funChangeKojo() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //選択工場の取得
	var selectKojo = frm.ddlKojo.options[frm.ddlKojo.selectedIndex].value;
	var selectKojoNm = frm.ddlKojo.options[frm.ddlKojo.selectedIndex].innerText;

	// 工場未選択の場合、使用実績を使用不可にする
	if ( selectKojo == "" || selectKojoNm == "新規登録原料") {
		frm.rdoShiyo[0].disabled = true;
		frm.rdoShiyo[1].checked = "true";

	} else {
		frm.rdoShiyo[0].disabled = false;
		frm.rdoShiyo[0].checked = "true";

	}

}
//add end --------------------------------------------------------------------------------------

//========================================================================================
// 検索ボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/04/01
// 引数  ：なし
// 概要  ：原料データの検索を行う
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
// 作成日：2009/04/01
// 引数  ：なし
// 概要  ：原料データの検索を行う
//========================================================================================
function funSearchData() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP0430";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA390");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA390I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA390O);
    var PageCnt;
    var RecCnt;
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

    //検索条件に一致する原料ﾃﾞｰﾀを取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0430, xmlReqAry, xmlResAry, 1) == false) {
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
    if (funGetLength(xmlResAry[2]) > 0 && funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
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
// 作成日：2009/04/01
// 引数  ：なし
// 概要  ：画面を初期化する
//========================================================================================
function funClear() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //画面の初期化
    frm.reset();

    //一覧のｸﾘｱ
    funClearList();
    xmlSA390O.src = "";

    //ﾃﾞﾌｫﾙﾄのｺﾝﾎﾞﾎﾞｯｸｽ設定値を読み込む
    xmlSA140O.load(xmlSA140);
    xmlSA180O.load(xmlSA180);

    //ｺﾝﾎﾞﾎﾞｯｸｽの再設定
    funCreateComboBox(frm.ddlKaisha, xmlSA140O, 1);
    funDefaultIndex(frm.ddlKaisha, 1);
    funCreateComboBox(frm.ddlKojo, xmlSA180O, 2);
    funDefaultIndex(frm.ddlKojo, 2);

    //ﾌｫｰｶｽ設定
    if (frm.btnSearch.disabled) {
        frm.txtGenryoCd.focus();
    } else {
        frm.btnSearch.focus();
    }

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
	//工場選択時処理
	funChangeKojo();
//add end --------------------------------------------------------------------------------------

    return true;

}

//========================================================================================
// 一覧クリア処理
// 作成者：M.Jinbo
// 作成日：2009/04/01
// 引数  ：なし
// 概要  ：一覧の情報を初期化する
//========================================================================================
function funClearList() {

    //一覧のｸﾘｱ
//    xmlSA390O.src = "";
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
// 作成日：2009/04/01
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
// 分析値入力画面起動処理
// 作成者：M.Jinbo
// 作成日：2009/04/01
// 引数  ：①StartMode ：起動モード
//           1:詳細、2:新規原料
// 概要  ：分析値入力画面を起動する
//========================================================================================
// 更新者：M.Jinbo
// 更新日：2009/06/24
// 内容  ：工場コンボで未選択も可能にする(課題表№16)
//========================================================================================
function funOpenGenryoInput(StartMode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var retVal;

    if (StartMode == 1) {
        //行が選択されていない場合
        if (funGetCurrentRow().toString() == "") {
            funErrorMsgBox(E000002);
            return false;
        }
    }

    //ﾊﾟﾗﾒｰﾀの設定
    frm.hidMode.value = StartMode;
    if (StartMode == 1) {
        frm.hidKaishaCd.value = funXmlRead(xmlSA390O, "cd_kaisha", funGetCurrentRow());
        if (frm.ddlKojo.selectedIndex > 0) {
            //工場コンボ選択時
            frm.hidKojoCd.value = frm.ddlKojo.options[frm.ddlKojo.selectedIndex].value;
        } else {
            //工場コンボ未選択時
            frm.hidKojoCd.value = funXmlRead(xmlSA390O, "cd_busho", funGetCurrentRow());
        }
        frm.hidGenryoCd.value = funXmlRead(xmlSA390O, "cd_genryo", funGetCurrentRow());
    }

    //分析値入力画面を起動する
//20160610  KPX@1502111_No.5 MOD start
//    retVal = funOpenModalDialog("../SQ051GenryoInput/SQ051GenryoInput.jsp", this, "dialogHeight:750px;dialogWidth:800px;status:no;scroll:no");
    retVal = funOpenModalDialog("../SQ051GenryoInput/SQ051GenryoInput.jsp", this, "dialogHeight:800px;dialogWidth:800px;status:no;scroll:no");
//20160610  KPX@1502111_No.5 MOD end

    //検索が行われている、且つ分析値入力で登録処理が行われた場合
    if (xmlSA390O.xml != "" && retVal == "1") {
        //再検索処理
        funSearchData();
        //選択行のｸﾘｱ
        funSetCurrentRow("");
    }

    return true;

}

//========================================================================================
// 削除ボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/04/01
// 引数  ：なし
// 概要  ：選択行の削除処理を行う
//========================================================================================
function funDelete() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP0440";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA370");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA370I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA370O);
    var resMsg;

    //行が選択されていない場合
    if (funGetCurrentRow().toString() == "") {
        funErrorMsgBox(E000002);
        return false;
    }

    //確認ﾒｯｾｰｼﾞの表示
    if (funConfMsgBox(I000004) != ConBtnYes) {
        return false;
    }

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //削除処理を実行する
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0440, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //完了ﾒｯｾｰｼﾞの表示
    resMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {
        //正常
        funInfoMsgBox(resMsg);
    }

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    //再検索処理
    funSearch();
    //選択行のｸﾘｱ
    funSetCurrentRow("");

    return true;

}

//========================================================================================
// Excel出力ボタン処理
// 作成者：M.Jinbo
// 作成日：2009/04/01
// 引数  ：なし
// 概要  ：CSVファイルの出力を行う
//========================================================================================
function funOutput() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP0450";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA360");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA360I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA360O);
    var dspMsg;

    //確認ﾒｯｾｰｼﾞの表示
    if (funConfMsgBox(I000008) != ConBtnYes) {
        return false;
    }

    //処理中ﾒｯｾｰｼﾞ表示
    funShowRunMessage();

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //CSVﾌｧｲﾙを作成する
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0450, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //結果判定
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "false") {
        //ｴﾗｰ発生時はﾒｯｾｰｼﾞを表示
        dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
        funInfoMsgBox(dspMsg);
        return false;
    }

    //ﾌｧｲﾙﾊﾟｽの退避
    frm.strFilePath.value = funXmlRead(xmlSA360O, "URLValue", 0);

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    //ﾀﾞｳﾝﾛｰﾄﾞ画面の起動
    funFileDownloadUrlConnect(ConConectGet, document.frm00);

    return true;

}

//========================================================================================
// 画面情報取得処理
// 作成者：M.Jinbo
// 作成日：2009/04/01
// 引数  ：①mode  ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：ユーザのセッション情報、コンボボックスの情報を取得する
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP0410";
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA140","SA180","SA880");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA140I,xmlSA180I,xmlSA880I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA140O,xmlSA180O,xmlSA880O);
//add end --------------------------------------------------------------------------------------

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0410, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //ﾕｰｻﾞ情報表示
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //権限関連の処理を行う
    funSaveKengenInfo();

/*    //ｺﾝﾎﾞﾎﾞｯｸｽの作成
    funCreateComboBox(frm.ddlKaisha, xmlResAry[2], 1);
    funCreateComboBox(frm.ddlKojo, xmlResAry[3], 2);*/

    //ﾃﾞﾌｫﾙﾄのｺﾝﾎﾞﾎﾞｯｸｽ設定値を退避
    xmlSA140.load(xmlResAry[2]);
    xmlSA180.load(xmlResAry[3]);

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
    //使用実績のラベル名取得・設定
    var shiyoNm = funXmlRead(xmlResAry[4], "nm_shiyo", 0);
    var shiyoNmLst = shiyoNm.substring(0,1) + "<br>" + shiyoNm.substring(1,2);
    var objShiyoNm = document.getElementById("divShiyoNm");
    var objShiyoNmLst = document.getElementById("divShiyoNmLst");
    objShiyoNm.innerHTML = shiyoNm;
    objShiyoNmLst.innerHTML = shiyoNmLst;

    //使用実績フラグの初期設定
	frm.rdoShiyo[1].checked = "true";
	frm.rdoShiyo[0].disabled = "true";		//使用実績 未使用
//add end --------------------------------------------------------------------------------------

    return true;

}

//========================================================================================
// 権限関連処理
// 作成者：M.Jinbo
// 作成日：2009/04/01
// 概要  ：権限情報の退避、画面制御を行う
//========================================================================================
function funSaveKengenInfo() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var obj = xmlUSERINFO_O;
    var GamenId;
    var KinoId;
    var reccnt;
    var i;

    //詳細、新規原料、削除ﾎﾞﾀﾝの制御
    frm.btnSearch.disabled = true;
    frm.btnEdit.disabled = true;
    frm.btnNew.disabled = true;
    frm.btnDel.disabled = true;
    frm.btnExcel.disabled = true;

    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        GamenId = funXmlRead(obj, "id_gamen", i);
        KinoId = funXmlRead(obj, "id_kino", i);

        //原料分析情報ﾏｽﾀﾒﾝﾃﾅﾝｽ
        if (GamenId.toString() == ConGmnIdGenryoInfoMst.toString()) {
            //閲覧
            if (KinoId.toString() == ConFuncIdRead.toString()) {
                frm.btnSearch.disabled = false;

            //編集
            } else if (KinoId.toString() == ConFuncIdEdit.toString()) {
                frm.btnSearch.disabled = false;
                frm.btnDel.disabled = false;
            }

        //原料分析情報ﾏｽﾀCSV
        } else if (GamenId.toString() == ConGmnIdGenryoInfoCsv.toString()) {
            //CSV出力
            if (KinoId.toString() == ConFuncIdRead.toString()) {
                frm.btnExcel.disabled = false;
            }

        //分析値入力(既存原料)
        } else if (GamenId.toString() == ConGmnIdGenryoInputUpd.toString()) {
            //閲覧
            if (KinoId.toString() == ConFuncIdRead.toString()) {
                frm.btnEdit.disabled = false;

            //編集(新規原料のみ)
            } else if (KinoId.toString() == ConFuncIdEdit.toString()) {
                frm.btnEdit.disabled = false;

            //編集(全て)
            } else if (KinoId.toString() == ConFuncIdAll.toString()) {
                frm.btnEdit.disabled = false;
// 20160513  KPX@1600766 ADD start
            //編集(システム管理者・廃止操作許可）
            } else if (KinoId.toString() == ConFuncIdSysMgr.toString()) {
                frm.btnEdit.disabled = false;
// 20160513  KPX@1600766 ADD end
            }

        //分析値入力(新規原料)
        } else if (GamenId.toString() == ConGmnIdGenryoInputNew.toString()) {
            //新規
            if (KinoId.toString() == ConFuncIdNew.toString()) {
                frm.btnNew.disabled = false;
            }
        }
    }

    return true;

}

//========================================================================================
// XMLファイルに書き込み
// 作成者：M.Jinbo
// 作成日：2009/04/01
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
        if (XmlId.toString() == "JSP0410") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA140
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenryoInfoMst, 0);
                    break;
                case 2:    //SA180
                    funXmlWrite(reqAry[i], "cd_kaisha", "", 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenryoInfoMst, 0);
                    break;
            }

        //会社ｺﾝﾎﾞ選択
        } else if (XmlId.toString() == "JSP0420"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA180
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenryoInfoMst, 0);
                    break;
            }

        //検索ﾎﾞﾀﾝ押下
        } else if (XmlId.toString() == "JSP0430"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA390
                    if (frm.chkGenryo[0].checked) {
                        funXmlWrite(reqAry[i], "kbn_joken1", "1", 0);
                    } else {
                        funXmlWrite(reqAry[i], "kbn_joken1", "", 0);
                    }
                    if (frm.chkGenryo[1].checked) {
                        funXmlWrite(reqAry[i], "kbn_joken2", "1", 0);
                    } else {
                        funXmlWrite(reqAry[i], "kbn_joken2", "", 0);
                    }
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_kojo", frm.ddlKojo.options[frm.ddlKojo.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "no_page", funGetCurrentPage(), 0);
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
                    //使用実績
                    if (frm.rdoShiyo[0].checked
                    	&& frm.ddlKojo.options[frm.ddlKojo.selectedIndex].value != "" ) {
                    	funXmlWrite(reqAry[i], "flg_shiyo", "1", 0);
                    } else {
                    	funXmlWrite(reqAry[i], "flg_shiyo", "", 0);
                    }
//add end --------------------------------------------------------------------------------------

                    break;
            }

        //削除ﾎﾞﾀﾝ押下
        } else if (XmlId.toString() == "JSP0440"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA370
                    funXmlWrite(reqAry[i], "cd_kaisha", funXmlRead(xmlSA390O, "cd_kaisha", funGetCurrentRow()), 0);
                    funXmlWrite(reqAry[i], "cd_genryo", funXmlRead(xmlSA390O, "cd_genryo", funGetCurrentRow()), 0);
                    funXmlWrite(reqAry[i], "flg_haishi", funXmlRead(xmlSA390O, "kbn_haishi", funGetCurrentRow()), 0);
                    break;
            }

        //Excelﾎﾞﾀﾝ押下
        } else if (XmlId.toString() == "JSP0450"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA360
                    if (frm.chkGenryo[0].checked) {
                        funXmlWrite(reqAry[i], "kbn_joken1", "1", 0);
                    } else {
                        funXmlWrite(reqAry[i], "kbn_joken1", "", 0);
                    }
                    if (frm.chkGenryo[1].checked) {
                        funXmlWrite(reqAry[i], "kbn_joken2", "1", 0);
                    } else {
                        funXmlWrite(reqAry[i], "kbn_joken2", "", 0);
                    }
                    funXmlWrite(reqAry[i], "cd_genryo", frm.txtGenryoCd.value, 0);
                    funXmlWrite(reqAry[i], "nm_genryo", frm.txtGenryoName.value, 0);
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_kojo", frm.ddlKojo.options[frm.ddlKojo.selectedIndex].value, 0);
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
                    //使用実績
                    if (frm.rdoShiyo[0].checked
                    	&& frm.ddlKojo.options[frm.ddlKojo.selectedIndex].value != "" ) {
                    	funXmlWrite(reqAry[i], "flg_shiyo", "1", 0);
                    } else {
                    	funXmlWrite(reqAry[i], "flg_shiyo", "", 0);
                    }
//add end --------------------------------------------------------------------------------------
                    break;
            }
        }
    }

    return true;

}

//========================================================================================
// コンボボックス作成処理
// 作成者：M.Jinbo
// 作成日：2009/04/01
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②xmlData  ：設定XML
//       ：③mode     ：モード
// 概要  ：コンボボックスを作成する
//========================================================================================
// 更新者：M.Jinbo
// 更新日：2009/06/24
// 内容  ：工場コンボで未選択も可能にする(課題表№16)
//========================================================================================
function funCreateComboBox(obj, xmlData, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var reccnt;                  //設定XMLの件数
    var objNewOption;
    var atbName;
    var atbCd;
    var i;

    //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
    funClearSelect(obj, 1);

    //件数取得
    reccnt = funGetLength(xmlData);

    //ﾃﾞｰﾀが存在しない場合
    if (reccnt == 0) {
        //空白行の追加
        funClearSelect(obj, 2);
        return true;
    }

    //属性名の取得
    switch (mode) {
        case 1:    //会社ﾏｽﾀ
            atbName = "nm_kaisha";
            atbCd = "cd_kaisha";
            break;
        case 2:    //工場ﾏｽﾀ
            //空白行の追加
            funClearSelect(obj, 2);
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.6
            obj.options[0].innerText = "全工場_縦";
//add end --------------------------------------------------------------------------------------

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

    //製造担当会社が未登録の場合
    if (obj.length == 0) {
        //空白行の追加
        funClearSelect(obj, 2);
        return true;
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽのﾃﾞﾌｫﾙﾄ表示
    obj.selectedIndex = 0;

    return true;
}

//========================================================================================
// デフォルト値選択処理
// 作成者：M.Jinbo
// 作成日：2009/04/01
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②mode     ：モード
// 概要  ：コンボボックスのデフォルト値を選択する
//========================================================================================
// 更新者：M.Jinbo
// 更新日：2009/06/24
// 概要  ：工場コンボで未選択も可能にする(課題表№16)
//========================================================================================
function funDefaultIndex(obj, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var selIndex;
    var i;

    for (i = 0; i < obj.length; i++) {
        switch (mode) {
            case 1:    //会社ｺﾝﾎﾞ
                if (obj.options[i].value == funXmlRead(xmlUSERINFO_O, "cd_kaisha", 0)) {
                    selIndex = i;
                }
                break;
            case 2:    //工場ｺﾝﾎﾞ
/*                if (obj.options[i].value == funXmlRead(xmlUSERINFO_O, "cd_busho", 0)) {
                    selIndex = i;
                }*/
                selIndex = 0;
                break;
        }
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽのｲﾝﾃﾞｯｸｽ指定
    obj.selectedIndex = selIndex;

    return true;
}

//========================================================================================
// 廃止原料背景色変更処理
// 作成者：M.Jinbo
// 作成日：2009/04/01
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②mode     ：モード
// 概要  ：廃止原料の背景色を変更する
//========================================================================================
function funChangeHaishiColor(obj, mode) {

    var i;
    var reccnt = funGetLength(xmlSA390O);

    //背景色の変更
    for (i = 0; i < reccnt; i++) {
        if (funXmlRead(xmlSA390O, "kbn_haishi", i) == "1") {
            //廃止の場合
            tblList.rows(i).style.backgroundColor = haishiRowColor;
        } else {
            //使用可能の場合
            tblList.rows(i).style.backgroundColor = deactiveSelectedColor;
        }
    }

    return true;
}

//========================================================================================
// 選択行操作(ローカル版)
// 作成者：M.Jinbo
// 作成日：2009/04/01
// 引数  ：なし
// 戻り値：なし
// 概要  ：選択行の背景色を変更する。
//========================================================================================
function funChangeSelectRowColorLocal() {

    var oSrc = event.srcElement, oTBL, oTR, oTD;
    var BeforeRow;

    if ( (oSrc.tagName != "TABLE") && (oSrc.tagName != "DIV") && (oSrc.tagName != "TR") ) {
        for ( oTBL = oSrc.parentElement; oTBL.tagName != "TABLE"; oTBL = oTBL.parentElement ) {}
        for ( oTR = oSrc.parentElement; oTR.tagName != "TR"; oTR = oTR.parentElement ) {}

        BeforeRow = (funGetCurrentRow() == "" ? 0 : funGetCurrentRow() / 1);

        //背景色を変更
        oTBL.rows(oTR.rowIndex).style.backgroundColor = activeSelectedColor;

        if ((BeforeRow != oTR.rowIndex) && (BeforeRow + 1 <= oTBL.rows.length)){
            //背景色を戻す
            if (funXmlRead(xmlSA390O, "kbn_haishi", BeforeRow) == "0") {
                //使用可能
                oTBL.rows(BeforeRow).style.backgroundColor = deactiveSelectedColor;
            } else {
                //廃止
                oTBL.rows(BeforeRow).style.backgroundColor = haishiRowColor;
            }
        }

        //ｶﾚﾝﾄ行の退避
        funSetCurrentRow(oTR.rowIndex);
    }

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

