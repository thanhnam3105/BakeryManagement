<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- シサクイック　権限機能追加画面                                                  -->
<!-- 作成者：TT.Jinbo                                                                -->
<!-- 作成日：2009/04/09                                                              -->
<!-- 概要  ：権限の画面、機能、データの組み合わせの選択を行う。                      -->
<!------------------------------------------------------------------------------------->

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
        <script type="text/javascript" src="include/SQ091KengenAdd.js"></script>
        <!-- スタイルシート -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">

        <script for=tblList event=onreadystatechange>
            if (tblList.readyState == 'complete') {
                //処理中ﾒｯｾｰｼﾞ非表示
                funClearRunMessage();
            }
        </script>

        <!--  テーブル明細行クリック -->
        <script for="tblList" event="onclick" language="JavaScript">
            //選択行の背景色を変更
            funChangeSelectRowColor();
        </script>
    </head>

    <body class="pfcancel" onLoad="funLoad();" topmargin="20" leftmargin="8" marginheight="20" marginwidth="5">
        <!-- XML Document定義 -->
        <xml id="xmlJSP9030"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlAuthority" src="../common/xml/AuthorityInfo.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlRESULT"></xml>

        <form name="frm00" id="frm00" method="post">
            <!-- タイトル・ボタン -->
            <table width="99%">
                <tr>
                    <td width="32%" class="title">権限機能追加</td>
                    <td width="68%" align="right">
                        <input type="button" class="normalbutton" id="btnSelect" name="btnSelect" value="採用" onClick="funSelect();">
                        <input type="button" class="normalbutton" id="btnClear" name="btnClear" value="クリア" onClick="funClear();">
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="終了" onClick="funClose();">
                    </td>
                </tr>
            </table>

            <!-- ユーザー情報 -->
            <br>
            <div id="divUserInfo"></div>
            <br>

            <!-- 入力・選択 -->
            <table width="610" align="center">
                <tr>
                    <td width="120">機能名<font color="red">（必須）</font></td>
                    <td>
                        <input type="text" class="act_text" id="txtKino" name="txtKino" maxlength="60" value="" style="width:300px;">
                    </td>
                </tr>
                <tr>
                    <td height="10"></td>
                </tr>
                <tr>
                    <td>画面<font color="red">（必須）</font></td>
                    <td>
                        <select id="ddlGamen" name="ddlGamen" style="width:300px;" onChange="funChangeCmb(2);">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td height="10"></td>
                </tr>
                <tr>
                    <td>機能<font color="red">（必須）</font></td>
                    <td>
                        <select id="ddlKino" name="ddlKino" style="width:300px;" onChange="funChangeCmb(3);">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td height="10"></td>
                </tr>
                <tr>
                    <td>参照可能データ<font color="red">（必須）</font></td>
                    <td>
                        <select id="ddlData" name="ddlData" style="width:300px;">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td height="10"></td>
                </tr>
                <tr>
                    <td>備考</td>
                    <td>
                        <textarea class="act_area" id="txtBiko" name="txtBiko" cols="80" rows="6"></textarea>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
