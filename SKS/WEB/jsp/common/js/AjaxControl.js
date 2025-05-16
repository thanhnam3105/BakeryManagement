//========================================================================================
// XML通信統合処理 [AjaxControl.js]
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 概要  ：XML通信の総合的な制御を行う。
//========================================================================================

//========================================================================================
// XML通信統合操作処理
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①XmlId     ：XMLID
//       ：②FuncIdList：機能ID(配列)
//       ：③xmlSend   ：送信XML
//       ：④xmlReqList：機能ID別送信XML(配列)
//       ：⑤xmlResList：機能ID別受信XML(配列)
//       ：⑥Mode      ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：XML通信の総合的な制御を行う。
//========================================================================================
function funAjaxConnection(XmlId, FuncIdList, xmlSend, xmlReqList, xmlResList, Mode) {

    var i;

    //XMLﾌｧｲﾙの統合
    for (i = 0; i < xmlReqList.length; i++) {
        funXmlMerge(xmlSend, xmlReqList[i], XmlId);
    }

    //XML通信処理
    if (funXmlConnect(xmlSend) == false) {
        xmlSend.src = "";
        //処理中ﾒｯｾｰｼﾞ非表示
        funClearRunMessage();
        return false;
    }

    //XMLﾌｧｲﾙの分割
    for (i = 0; i < xmlResList.length; i++) {
        funXmlDivision(xmlSend, xmlResList[i], FuncIdList[i]);
    }

    //結果判定
    if (funXmlResultCheck(xmlResList) == false) {
        xmlSend.src = "";
        return false;
    }

    //ｾｯｼｮﾝ有ﾓｰﾄﾞの場合
    if (Mode == "1") {
        //ｾｯｼｮﾝﾀｲﾑｱｳﾄ確認
        for (i = 0; i < xmlResList.length; i++) {
            if (funSessionTimeoutCheck(xmlResList[i]) == false) {
                xmlSend.src = "";
                return false;
            }
        }
    }

    return true;

}
