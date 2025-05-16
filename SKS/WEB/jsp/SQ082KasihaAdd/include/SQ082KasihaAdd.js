
//画面設定(BODYのﾛｰﾄﾞ後にﾀｲﾄﾙの設定が行えないため、BODYのﾛｰﾄﾞ前に行う)
funInitScreen(ConKasihaAddId);

//========================================================================================
// 初期表示処理
// 作成者：M.Jinbo
// 作成日：2009/04/08
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

    //画面初期化
    funClear();

    //戻り値の初期化
    window.returnValue = "";

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    return true;

}

//========================================================================================
// 検索ボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/04/08
// 引数  ：なし
// 概要  ：製造担当会社データの検索を行う
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
// 作成日：2009/04/08
// 引数  ：なし
// 概要  ：製造担当会社データの検索を行う
//========================================================================================
function funSearchData() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP1010";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA220");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA220I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA220O);
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

    //検索処理を実行する
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP1010, xmlReqAry, xmlResAry, 1) == false) {
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

    //処理中ﾒｯｾｰｼﾞ非表示
    funClearRunMessage();

    return true;

}

//========================================================================================
// クリアボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/04/08
// 引数  ：なし
// 概要  ：画面を初期化する
//========================================================================================
function funClear() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照

    //画面の初期化
    frm.reset();

    //一覧のｸﾘｱ
    funClearList();

    //ﾌｫｰｶｽ設定
    frm.txtKaishaName.focus();

    return true;

}

//========================================================================================
// 一覧クリア処理
// 作成者：M.Jinbo
// 作成日：2009/04/08
// 概要  ：一覧の情報を初期化する
//========================================================================================
function funClearList() {

    //一覧のｸﾘｱ
    xmlSA220O.src = "";
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
// 作成日：2009/04/08
// 概要  ：選択データを呼び出し元画面に返す
//========================================================================================
function funSelect() {

    var retVal = "";

    //行が選択されていない場合
    if (funGetCurrentRow().toString() == "") {
        funErrorMsgBox(E000002);
        return false;
    }

    //戻り値の設定
    retVal = funXmlRead(xmlSA220O, "cd_kaisha", funGetCurrentRow());
    retVal += ConDelimiter + funXmlRead(xmlSA220O, "nm_kaisha", funGetCurrentRow());
    window.returnValue = retVal;

    //画面を閉じる
    close(self);

}

//========================================================================================
// 終了ボタン押下処理
// 作成者：M.Jinbo
// 作成日：2009/04/08
// 概要  ：画面を閉じる
//========================================================================================
function funClose() {

    //戻り値の設定
    window.returnValue = "";

    //画面を閉じる
    close(self);

}

//========================================================================================
// ページ遷移
// 作成者：M.Jinbo
// 作成日：2009/04/08
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

//========================================================================================
// 画面情報取得処理
// 作成者：M.Jinbo
// 作成日：2009/04/08
// 引数  ：①mode  ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：ユーザのセッション情報、コンボボックスの情報を取得する
//========================================================================================
function funGetInfo(mode) {

    var opener = window.dialogArguments;      //引数の取得
    var opener_form = opener.document.forms(0);
    var frm = document.frm00;                 //ﾌｫｰﾑへの参照
    var XmlId = "JSP9030";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //ｾｯｼｮﾝ情報、ｺﾝﾎﾞﾎﾞｯｸｽの情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP9030, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //ﾕｰｻﾞ情報表示
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    return true;

}

//========================================================================================
// XMLファイルに書き込み
// 作成者：M.Jinbo
// 作成日：2009/04/08
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
        //検索ﾎﾞﾀﾝ押下
        if (XmlId.toString() == "JSP1010"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA220
                    funXmlWrite(reqAry[i], "no_page", funGetCurrentPage(), 0);
                    break;
            }

        //画面初期表示
        } else if (XmlId.toString() == "JSP9030") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
            }
        }
    }

    return true;

}

