//========================================================================================
// チェック処理 [CheckControl.js]
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 概要  ：サーバ処理結果のチェックを行う。
//========================================================================================

var DebugLevel;    //ﾃﾞﾊﾞｯｸﾞﾚﾍﾞﾙ

//========================================================================================
// 結果判定処理(総合)
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①xmlResult   ：処理結果XML名
// 戻り値：正常終了:true／異常終了:false
// 概要  ：サーバで処理された結果の判定を行う。
//========================================================================================
function funXmlResultCheck(xmlResult) {

    var ErrMsg;         //メッセージ
    var ClsName;        //処理名称
    var ErrCd;          //エラーコード
    var SysMsg;         //システムメッセージ
    var MsgNo;          //メッセージ番号
    var OutputMsg;      //出力メッセージ
    var i;
    var resultflg;
    var CurrentFolder;

    resultflg = false;

    for (i = 0; i < xmlResult.length; i++) {
        if (xmlResult[i].xml == "") {
            continue;
        }

        //ResultXMLの場合
        if (xmlResult[i].documentElement.nodeName == ConResult) {
            //ﾃﾞﾊﾞｯｸﾞﾚﾍﾞﾙの退避
            DebugLevel = funXmlRead(xmlResult[i], "debuglevel", 0);
            resultflg = true;

            //処理結果の判定
            if (funXmlRead(xmlResult[i], "flg_return", 0) == "false") {
                //異常時はｴﾗｰﾒｯｾｰｼﾞを表示
                ErrMsg = funXmlRead(xmlResult[i], "msg_error", 0);
                ClsName = funXmlRead(xmlResult[i], "nm_class", 0);
                MsgNo = funXmlRead(xmlResult[i], "no_errmsg", 0);
                ErrCd = funXmlRead(xmlResult[i], "cd_error", 0);
                SysMsg = funXmlRead(xmlResult[i], "msg_system", 0);

                //出力ﾒｯｾｰｼﾞの生成
                if (DebugLevel == 0) {
                    OutputMsg = ErrMsg;
                } else {
                    OutputMsg = ErrMsg;
                    OutputMsg += "<br><br>ﾒｯｾｰｼﾞ番号：" + MsgNo;
                    OutputMsg += "<br>発生箇所：" + ClsName;
                    OutputMsg += "<br>ｼｽﾃﾑｴﾗｰｺｰﾄﾞ：" + ErrCd;
                    OutputMsg += "<br>ｼｽﾃﾑｴﾗｰﾒｯｾｰｼﾞ：" + SysMsg;
                }

                //原価試算画面セッションチェック
                //ｾｯｼｮﾝ切れの場合
                if (MsgNo == ConSessionErrCd) {
                    var CurrentFolder_genka = location.pathname.split("/")[3];
                    //原価試算画面
                    if( CurrentFolder_genka == "SQ110GenkaShisan" ){
                        //画面を終了
                        funErrorMsgBox(E000011);
                        parent.close();
                        return false;
                    }
                    //類似品検索画面
                    else if( CurrentFolder_genka == "RuiziSearch.jsp" ){
                        //画面を終了
                        funErrorMsgBox(E000011);
                        window.close();
                        window.dialogArguments.parent.close();
                        return false;
                    // ADD 2015/03/03 TT.Kitazawa【QP@40812】start
                    //  "SQ160GenkaShisan_Eigyo" 他 OutputMsgが設定されていない時
                    } else if( OutputMsg == "" ) {
                        //画面を終了
                        funErrorMsgBox(E000011);
                        parent.close();
                        return false;
                    // ADD 2015/03/03 TT.Kitazawa【QP@40812】end
                    }
                }

                //ｴﾗｰﾒｯｾｰｼﾞ
                funErrorMsgBox(OutputMsg);
                funClearRunMessage();

                //ｾｯｼｮﾝ切れの場合
                if (MsgNo == ConSessionErrCd) {
                    CurrentFolder = location.pathname.split("/")[2];

                    if (CurrentFolder != "SQ051GenryoInput" && CurrentFolder != "SQ081TantoSearch" &&
                        CurrentFolder != "SQ082KasihaAdd" && CurrentFolder != "SQ091KengenAdd") {
                        //ﾛｸﾞｲﾝ画面へ遷移
                        funUrlConnect("../SQ010Login/SQ010Login.jsp", ConConectGet, "");
                    }
                }

                //結果判定(個別)
                if (funResultCheck(xmlResult) == false) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    //ResultXMLが存在しない場合
    if (resultflg == false) {
        //結果判定を異常で返す
        funErrorMsgBox(E000001);
        return false;
    }

    return true;

}

//========================================================================================
// 結果判定処理(個別)
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①xmlResult   ：処理結果XML名
// 戻り値：正常終了:true／異常終了:false
// 概要  ：サーバで処理された結果の判定を行う。
//========================================================================================
function funResultCheck(xmlResult) {

    var ErrMsg;         //メッセージ
    var ClsName;        //処理名称
    var ErrCd;          //エラーコード
    var SysMsg;         //システムメッセージ
    var MsgNo;          //メッセージ番号
    var OutputMsg;      //出力メッセージ
    var i;

    for (i = 0; i < xmlResult.length; i++) {
        if (xmlResult[i].xml == "") {
            return false;
        }

        //UserInfoXML且つResultXML以外の場合
        if (xmlResult[i].documentElement.nodeName != ConResult && xmlResult[i].documentElement.nodeName != ConUserInfo) {
            //処理結果の判定
            if (funXmlRead(xmlResult[i], "flg_return", 0) == "false") {
/*                //異常時はｴﾗｰﾒｯｾｰｼﾞを表示
                ErrMsg = funXmlRead(xmlResult, "msg_error", 0);
                ClsName = funXmlRead(xmlResult, "nm_class", 0);
                MsgNo = funXmlRead(xmlResult, "no_errmsg", 0);
                ErrCd = funXmlRead(xmlResult, "cd_error", 0);
                SysMsg = funXmlRead(xmlResult, "msg_system", 0);

                //出力ﾒｯｾｰｼﾞの生成
                if (DebugLevel == 0) {
                    OutputMsg = ErrMsg;
                } else {
                    OutputMsg = ErrMsg;
                    OutputMsg += "<br><br>ﾒｯｾｰｼﾞ番号：" + MsgNo;
                    OutputMsg += "<br>発生箇所：" + ClsName;
                    OutputMsg += "<br>ｼｽﾃﾑｴﾗｰｺｰﾄﾞ：" + ErrCd;
                    OutputMsg += "<br>ｼｽﾃﾑｴﾗｰﾒｯｾｰｼﾞ：" + SysMsg;
                }

                //ｴﾗｰﾒｯｾｰｼﾞ
                funErrorMsgBox(OutputMsg);
                funClearRunMessage();*/

                return false;
            }
        }
    }

    return true;

}

//========================================================================================
// セッションタイムアウト確認処理
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①xmlUser      ：ユーザ情報格納XML名
// 戻り値：正常終了:true／異常終了:false
// 概要  ：サーバで保持されているセッション情報のタイムアウト確認を行う。
//========================================================================================
function funSessionTimeoutCheck(xmlUser) {

    var ErrMsg;         //メッセージ
    var ClsName;        //処理名称
    var ErrCd;          //エラーコード
    var SysMsg;         //システムメッセージ
    var MsgNo;          //メッセージ番号
    var OutputMsg;      //出力メッセージ

    if (xmlUser.xml == "") {
        return true;
    }

    if (xmlUser.documentElement.nodeName == ConUserInfo) {
        //処理結果の判定
        if (funXmlRead(xmlUser, "flg_return", 0) == "false") {
            //異常時はｴﾗｰﾒｯｾｰｼﾞを表示
            ErrMsg = funXmlRead(xmlUser, "msg_error", 0);
            ClsName = funXmlRead(xmlUser, "nm_class", 0);
            MsgNo = funXmlRead(xmlUser, "no_errmsg", 0);
            ErrCd = funXmlRead(xmlUser, "cd_error", 0);
            SysMsg = funXmlRead(xmlUser, "msg_system", 0);

            //異常時はｴﾗｰ画面を表示
//            location.href = "../common/SessionTimeout.html";
            //出力ﾒｯｾｰｼﾞの生成
            if (DebugLevel == 0) {
                OutputMsg = ErrMsg;
            } else {
                OutputMsg = ErrMsg;
                OutputMsg += "<br><br>ﾒｯｾｰｼﾞ番号：" + MsgNo;
                OutputMsg += "<br>発生箇所：" + ClsName;
                OutputMsg += "<br>ｼｽﾃﾑｴﾗｰｺｰﾄﾞ：" + ErrCd;
                OutputMsg += "<br>ｼｽﾃﾑｴﾗｰﾒｯｾｰｼﾞ：" + SysMsg;
            }

            //異常時はｴﾗｰﾒｯｾｰｼﾞを表示
            funErrorMsgBox(OutputMsg);
            funClearRunMessage();

            return false;
        }
    }

    return true;

}
