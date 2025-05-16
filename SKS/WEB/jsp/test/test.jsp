<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!-----------------------------------------------------------------------------------
 ' デリア物流費DB　CSVファイルダウンロード処理                                       
 ' 作成者：TT.Jinbo                                                                  
 ' 作成日：2008/09/10                                                                
 ' 概要  ：                                                                          
 '-----------------------------------------------------------------------------------
 ' 変更者：                                                                          
 ' 変更日：                                                                          
 ' 概要  ：                                                                          
 '----------------------------------------------------------------------------------->

<html>
    <head>
        <title>サンプルページ</title>
   <!--===================================================================
        INCLUDE SCRIPT & CSS
     ===================================================================-->
        <script type="text/javascript" src="../common/js/Const.js"></script>
        <script type="text/javascript" src="../common/js/MessageControl.js"></script>
        <script type="text/javascript" src="../common/js/ConnectionControl.js"></script>
        <script type="text/javascript" src="../common/js/XmlControl.js"></script>
        <script type="text/javascript" src="../common/js/AjaxControl.js"></script>
        <script type="text/javascript" src="../common/js/CheckControl.js"></script>
        <script type="text/javascript" src="../common/js/DisplayControl.js"></script>

    <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css" />

<!-- 
    <xml id="xmlJSP0010"></xml>
    <xml id="xmlSA020I" src="SA020I.xml"></xml>
    <xml id="xmlSA020O"></xml> -->
    
    <!-- テスト　：　インプットチェック
    <xml id="xmlJW_TEST" src="./JW/JW020.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW030.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW040.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW050.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW060.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW110.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW210.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW610.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW620.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW630.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW710.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW720.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW730.xml"></xml>
    -->
    <xml id="xmlJW_TEST" src="JW820.xml"></xml>
    
    <!-- 帳票用
    <xml id="xmlJW_TEST" src="./JW_E/JW130.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW_E/JW740.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW_E/JW750.xml"></xml>
    -->
    
    <!-- テスト　：　検索ロジック
    <xml id="xmlSA590I" src="./SA/SA590I.xml"></xml>
    <xml id="xmlSA590O"></xml>
    <xml id="xmlSA570I" src="./SA/SA570I.xml"></xml>
    <xml id="xmlSA570O"></xml>
    <xml id="xmlSA510I" src="./SA/SA510I.xml"></xml>
    <xml id="xmlSA510O"></xml>
    -->
    
    <!-- テスト　：　登録・更新ロジック
    <xml id="xmlSA420I" src="./SA/SA420I.xml"></xml>
    <xml id="xmlSA420O"></xml>
    <xml id="xmlSA410I" src="./SA/SA410I.xml"></xml>
    <xml id="xmlSA410O"></xml>
    <xml id="xmlSA370I" src="./SA/SA370I.xml"></xml>
    <xml id="xmlSA370O"></xml>
    <xml id="xmlSA380I" src="./SA/SA380I.xml"></xml>
    <xml id="xmlSA380O"></xml>
    <xml id="xmlSA490I" src="./SA/SA490I.xml"></xml>
    <xml id="xmlSA490O"></xml>
    <xml id="xmlSA380I" src="./SA/SA380I.xml"></xml>
    <xml id="xmlSA380O"></xml>
    -->

        <script language="JavaScript">
        <!--

            function funFile() {
var ret;
var RequestList = new Array();
var ResponseList = new Array();
var FunctionIdList = new Array();

funXmlConnect(xmlJW_TEST);

/*
//SA590
RequestList[0] = xmlSA590I;
ResponseList[0] = xmlSA590O;
FunctionIdList[0] = "SA590O";
//SA570
RequestList[0] = xmlSA570I;
ResponseList[0] = xmlSA570O;
FunctionIdList[0] = "SA570O";
//SA510
RequestList[0] = xmlSA510I;
ResponseList[0] = xmlSA510O;
FunctionIdList[0] = "SA510O";
//SA420
RequestList[0] = xmlSA420I;
ResponseList[0] = xmlSA420O;
FunctionIdList[0] = "SA420O";
//SA410
RequestList[0] = xmlSA410I;
ResponseList[0] = xmlSA410O;
FunctionIdList[0] = "SA410O";
//SA370
RequestList[0] = xmlSA370I;
ResponseList[0] = xmlSA370O;
FunctionIdList[0] = "SA370O";
//SA380
RequestList[0] = xmlSA380I;
ResponseList[0] = xmlSA380O;
FunctionIdList[0] = "SA380O";
//SA490
RequestList[0] = xmlSA490I;
ResponseList[0] = xmlSA490O;
FunctionIdList[0] = "SA490O";
//SA380
RequestList[0] = xmlSA380I;
ResponseList[0] = xmlSA380O;
FunctionIdList[0] = "SA380O";
//Ajax通信
if (funAjaxConnection("JSP0010", FunctionIdList, xmlJSP0010, RequestList, ResponseList, 1) == false ) {
    alert("error");
    return false;
}
*/

/*
//ユーザ情報表示
funInformationDisplay(xmlSA020O, 1, "divInfo");

//ページリンク表示
funCreatePageLink(1, 17, "divPage", "tblList");

//ボタン表示
funCreateMenuButton(xmlSA020O, ConMainMenuId, "divBtnMain");
funCreateMenuButton(xmlSA020O, ConMstMenuId, "divBtnMst");

//画面遷移
//funUrlConnect("post.asp?val=成功！", ConConectPost, document.frm00);

/*
//XML統合
funXmlMerge(xmlJSP0010,xmlSA020I,"JSP0010");
funXmlMerge(xmlJSP0010,xmlSA010I,"JSP0010");
alert("JSP0010：" + xmlJSP0010.xml);

//XML分割
funXmlDivision(xmlJSP0010O,xmlSA020O,"SA020O");
funXmlDivision(xmlJSP0010O,xmlSA010O,"SA010O");
alert("SA020O：" + xmlSA020O.xml);
alert("SA010O：" + xmlSA010O.xml);

//XML読み込み
ret = funXmlRead(xmlSA020O,"id_user",0);
alert(ret);

//XML書き込み
if (funXmlWrite(xmlSA020O,"id_user","159",0) == false) {

alert("error");
}


ret = funXmlRead(xmlSA020O,"id_user",0);
alert(ret);

//戻り値チェック
if (funResultCheck(xmlSA020O) == false) {
}
*/

//  JSL.MSGBOX.Info('情報メッセージ...'); //検索中...
//  JSL.MSGBOX.Warn('ワーニングメッセージ...'); //検索中...
//  JSL.MSGBOX.YesNo('はい、いいえメッセージ...'); //検索中...
//  JSL.MSGBOX.YesNoCancel('はい、いいえ、キャンセルメッセージ...'); //検索中...
            }

            function funItem(mode) {
                funItemControl(document.frm00.txtTest, mode);
                funItemControl(document.frm00.ddlTest, mode);
                funItemControl(document.frm00.chkTest, mode);
                funItemControl(document.frm00.btnTest, mode);
            }

        //-->
        </script>

        <!--  LOGINクリック -->
        <script for="tblList" event="onclick" language="JavaScript">
            funChangeSelectRowColor();
        </script>
    </head>

    <body onLoad="funFile();">
        <form name="frm00">
            <center>
                <br>
                <!-- ヘッダー部 -->
                <table>
                    <tr>
                        <td align="center" class="g_title" colspan="3"><b>【シサクイック共通関数テスト】</b></td>
                    </tr>
                    <tr>
                        <td align="center" colspan="3">　</td>
                    </tr>
                    <tr>
                        <td align="center" colspan="3"><a href="javascript:window.open('../SQ010Login/SQ010Login.jsp?id=1001','shisakuikku','menubar=no,resizable=yes');">シサクイック シングルサインオン</td>
                    </tr>
                    <tr>
                        <td align="center" colspan="3"><a href="javascript:window.open('../SQ010Login/SQ010Login.jsp','shisakuikku','menubar=no,resizable=yes');">シサクイック ログイン</td>
                    </tr>
                    <tr>
                        <td align="center" colspan="3"><a href="javascript:window.open('../SQ020MainMenu/SQ020MainMenu.jsp','shisakuikku','menubar=no,resizable=yes');">メインメニュー</td>
                    </tr>
                    <tr>
                        <td align="center" colspan="3"><a href="javascript:window.open('../SQ030MstMenu/SQ030MstMenu.jsp','shisakuikku','menubar=no,resizable=yes');">マスタメニュー</td>
                    </tr>
                    <tr>
                        <td align="center" class="g_title">
                            <input type="text" name="txtTest" id="txtTest" value="" onBlur="funBuryZero(this, 10);">
                        </td>
                        <td align="center" class="g_title">
                            <select name="ddlTest" id="ddlTest" style="width:100px">
                                <options value="0">てすと</options>
                            </select>
                        </td>
                        <td align="center" class="g_title">
                            <input type="checkbox" name="chkTest" id="chkTest" value="">
                        </td>
                        <td align="center" class="g_title">
                            <input type="button" class="normalbutton" name="btnTest" id="btnTest" value="test">
                        </td>
                    </tr>
                </table>
                <br>
            </center>
            <br>
<input type="button" class="normalbutton" onclick="funItem(true)" value="ｺﾝﾄﾛｰﾙﾛｯｸ">
<input type="button" class="normalbutton" onclick="funItem(false)" value="ｺﾝﾄﾛｰﾙﾛｯｸ解除">
            <br>
            <div id="divInfo"></div>
            <br>
<button onclick="funInfoMsgBox('情報メッセージ...');">メッセージ[Info]</button>
<button onclick="funErrorMsgBox('警告メッセージ...');">メッセージ[Warn]</button>
<button onclick="funWarnMsgBox('はい、いいえ、キャンセルメッセージ...');">メッセージ[Yes/no/Cancel]</button>
<button onclick="funConfMsgBox('はい、いいえメッセージ...');">メッセージ[Yes/no]</button>
            <br>
            <br>
<button onclick="funShowRunMessage()">処理中</button>
<button onclick="funClearRunMessage()">処理完了</button>
            <br>
<table style="width:100%; text-align:center;">
    <tr>
        <td style="text-align:center;">
            <div id="divBtnMain"></div>
        </td>
    </tr>
</table>
            <br><br>
<table style="width:100%; text-align:center;">
    <tr>
        <td style="text-align:center;">
            <div id="divBtnMst"></div>
        </td>
    </tr>
</table>
            <br><br>
<button onclick="funSelectRowDelete(xmlUser)">行削除</button>
<span class="scrtable">
<div class="scroll" id="sclList" style="height:150px;width:100%;" rowSelect="true">
<table id="tbl1" name="dataTable" cellspacing="0" style="width:983;">
    <colgroup>
        <col style="width:150px;"/>
        <col style="width:150px;"/>
        <col style="width:150px;"/>
        <col style="width:150px;"/>
        <col style="width:150px;"/>
        <col style="width:150px;"/>
        <col style="width:150px;"/>
        <col style="width:150px;"/>
    </colgroup>
    <thead class="rowtitle">
        <tr style="top:expression(offsetParent.scrollTop);position:relative;">
            <td class="columntitle" style="text-align:center">担当者名</td>
            <td class="columntitle" style="text-align:center">会社名</td>
            <td class="columntitle" style="text-align:center">部署名</td>
            <td class="columntitle" style="text-align:center">グループ名</td>
            <td class="columntitle" style="text-align:center">チーム名</td>
            <td class="columntitle" style="text-align:center">役職名</td>
            <td class="columntitle" style="text-align:center">システムバージョン</td>
            <td class="columntitle" style="text-align:center">権限ID</td>
        </tr>
    </thead>
    <tbody class="disprow">
        <table id="tblList" cellpadding="0" cellspacing="0" border="1" datasrc="#xmlSA020O" datafld="rec" style="width:983;word-break:break-all;word-wrap:break-word;font-size:9pt;" datapagesize=5>
            <tr class="disprow" >
                <td class="column" style="width:150px;"><span datafld="nm_user"></span></td>
                <td class="column" style="width:150px;"><span datafld="nm_kaisha"></span></td>
                <td class="column" style="width:150px;"><span datafld="nm_busho"></span></td>
                <td class="column" style="width:150px;"><span datafld="nm_group"></span></td>
                <td class="column" style="width:150px;"><span datafld="nm_team"></span></td>
                <td class="column" style="width:150px;"><span datafld="nm_yakushoku"></span></td>
                <td class="column" style="width:150px;"><span datafld="ver_system"></span></td>
                <td class="column" style="width:150px;"><span datafld="id_gamen"></span></td>
            </tr>
        </table>
    </tbody>
</table>
</div>
</span>
            <br><br>
<table style="width:100%; text-align:center;">
    <tr>
        <td style="text-align:center;">
            <span id="divPage"></span>
        </td>
    </tr>
</table>
            <br><br>
<input type="button" class="normalbutton" onclick="tblList.previousPage()" value="前ページ">
<input type="button" class="normalbutton" onclick="tblList.nextPage()" value="次ページ">

            <input type="hidden" name="hidVal" value="成功！">
        </form>
    </body>
</html>
