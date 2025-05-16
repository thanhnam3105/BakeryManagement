<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- シサクイック　ログイン画面                                                      -->
<!-- 作成者：TT.Jinbo                                                                -->
<!-- 作成日：2009/03/19                                                              -->
<!-- 概要  ：ログインユーザの認証処理を行う。                                        -->
<!------------------------------------------------------------------------------------->
<%
    Integer iMode;

    //パラメータの取得
    String strUserId = request.getParameter("id");

    //モードの設定
    if (strUserId != null && strUserId != "") {
        iMode = 1;    //シングルサインオン
    } else {
        iMode = 2;    //ログイン画面から起動
        strUserId = "";
    }
%>
<html>
    <head>
        <title></title>
        <!-- 共通 -->
        <script type="text/javascript" src="../common/js/Const.js"></script>
        <script type="text/javascript" src="../common/js/MessageControl.js"></script>
        <script type="text/javascript" src="../common/js/ConnectionControl.js"></script>
        <script type="text/javascript" src="../common/js/XmlControl.js"></script>
        <script type="text/javascript" src="../common/js/AjaxControl.js"></script>
        <script type="text/javascript" src="../common/js/CheckControl.js"></script>
        <script type="text/javascript" src="../common/js/DisplayControl.js"></script>
        <script type="text/javascript" src="../common/js/InputControl.js"></script>
        <!-- 個別 -->
        <script type="text/javascript" src="include/SQ010Login.js"></script>
        <!-- スタイルシート -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">
    </head>

    <body class="pfcancel" onLoad="funLoad()">
        <!-- XML Document定義 -->
        <xml id="xmlJSP0010"></xml>
        <xml id="xmlRGEN2110"></xml>

        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlSA010I" src="../../model/SA010I.xml"></xml>

        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlSA010O"></xml>
        <xml id="xmlRESULT"></xml>

        <!-- ADD 2013/9/25 okano【QP@30151】No.28 start -->
        <xml id="xmlJSP0020"></xml>
        <xml id="xmlSA020I" src="../../model/SA020I.xml"></xml>
        <xml id="xmlSA020O"></xml>
        <!-- ADD 2013/9/25 okano【QP@30151】No.28 end -->
        <!-- ADD 2015/07/27 TT.Kitazawa【QP@40812】No.14 start -->
        <xml id="xmlJSP9030"></xml>
        <xml id="xmlFGEN2130I" src="../../model/FGEN2130I.xml"></xml>
        <xml id="xmlFGEN2130O"></xml>
        <!-- ADD 2015/07/27 TT.Kitazawa【QP@40812】No.14 end -->

        <form name="frm00" id="frm00" method="post">
            <!-- タイトル部 -->
            <table align="center" width="350">
                <tr>
                    <th colspan="2" align="center" bgcolor="#FEC0C1">
                        <font color="#E54F5B">パスワードを入力してください</font>
                    </th>
                </tr>
            </table>

            <!-- 入力部 -->
            <table align="center" width="350">
                <tr>
                    <td height="8px"></td>
                </tr>
                <tr>
                    <td width="100" style="text-align:center;">社員番号</td>
                    <td>
                        <span class="ninput" format="10,0" comma="false" required="true" defaultfocus="false" id="em_UserId">
                        <input type="text" class="disb_text" name="txtUserId" id="txtUserId" maxlength="10" style="width:200px;text-align:left;" onBlur="funBuryZero(this, 10);">
                        </span>
                    </td>
                </tr>
                <tr>
                    <td style="text-align:center;">パスワード</td>
                    <td>
                        <input type="password" class="disb_text" name="txtPass" id="txtPass" maxlength="30" style="width:200px;text-align:left;">
                    </td>
                </tr>
            </table>

            <br>

            <!-- ボタン部 -->
            <table align="center">
                <tr>
                    <td width="160" align="center">
                        <input type="button" name="btnOk" id="btnOk" class="normalbutton" value="ＯＫ" onClick="funChkLogin();">
                    </td>
                    <td width="160" align="center">
                        <input type="button" name="btnCancel" id="btnCancel" class="normalbutton" value="キャンセル" onClick="funClose();">
                    </td>
                </tr>
            </table>

            <!-- 【QP@00342】 -->
            <table align="center">
                <tr>
                    <td width="160" align="center">
                        <!-- MOD 2014/8/7 shima【QP@30154】No.42 start -->
                        <!-- <input type="button" name="btnEigyo" id="btnEigyo" class="normalbutton" value="営業登録へ" onClick="funEigyoLogin();"> -->
                        <input type="button" name="btnEigyo" id="btnEigyo" class="normalbutton" value="新規登録" onClick="funEigyoLogin();">
                        <!-- MOD 2014/8/7 shima【QP@30154】No.42 end -->
                    </td>
                    <td width="160" align="center">
                        <!-- ADD 2015/03/03 TT.Kitazawa【QP@40812】No.14 start -->
                        <input type="button" name="btnHelp" id="btnHelp" class="normalbutton" value="ヘルプ表示" onClick="funHelpDisp();">
                        <!-- ヘルプファイル -->
                        <input type="hidden" value="" name="strHelpPath" id="strHelpPath">
                        <!-- ADD 2015/03/03 TT.Kitazawa【QP@40812】No.14 end -->
                    </td>
                </tr>
            </table>

            <input type="hidden" name="hidMode" id="hidMode" value="<%= iMode %>">
            <input type="hidden" name="hidUserId" id="hidUserId" value="<%= strUserId %>">

        </form>
    </body>
</html>
