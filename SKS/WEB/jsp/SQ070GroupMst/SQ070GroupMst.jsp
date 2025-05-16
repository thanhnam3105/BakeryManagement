<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- シサクイック　グループマスタメンテナンス画面                                    -->
<!-- 作成者：TT.Jinbo                                                                -->
<!-- 作成日：2009/04/04                                                              -->
<!-- 概要  ：グループ、チームデータの登録、更新、削除を行う。                        -->
<!------------------------------------------------------------------------------------->
<!-- 更新者：TT.Jinbo                                                                -->
<!-- 更新日：2009/06/24                                                              -->
<!-- 内容  ：削除ボタンのコメント化(課題表№13)                                      -->
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
        <script type="text/javascript" src="include/SQ070GroupMst.js"></script>
        <!-- スタイルシート -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">

        <script for=txtGroup event=onkeydown>
            if (event.keyCode == 13 && document.frm00.rdoMode[0].checked) {
                //最後のｺﾝﾄﾛｰﾙでEnterｷｰ押下の場合、ﾀﾞﾐｰﾎﾞﾀﾝにﾌｫｰｶｽを設定
                document.frm00.btnDummy.focus();
            }
        </script>

        <script for=txtTeam event=onkeydown>
            if (event.keyCode == 13 && document.frm00.rdoMode[1].checked) {
                //最後のｺﾝﾄﾛｰﾙでEnterｷｰ押下の場合、ﾀﾞﾐｰﾎﾞﾀﾝにﾌｫｰｶｽを設定
                document.frm00.btnDummy.focus();
            }
        </script>
    </head>

    <body class="pfcancel" onLoad="funLoad();">
        <!-- XML Document定義 -->
        <xml id="xmlJSP0710"></xml>
        <xml id="xmlJSP0720"></xml>
        <xml id="xmlJSP0730"></xml>
        <xml id="xmlJSP9020"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlSA050I" src="../../model/SA050I.xml"></xml>
        <xml id="xmlSA060I" src="../../model/SA060I.xml"></xml>
        <xml id="xmlSA080I" src="../../model/SA080I.xml"></xml>
        <xml id="xmlSA090I" src="../../model/SA090I.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlSA050O"></xml>
        <xml id="xmlSA060O"></xml>
        <xml id="xmlSA080O"></xml>
        <xml id="xmlSA090O"></xml>
        <xml id="xmlRESULT"></xml>
        <!-- ADD 2013/10/24 QP@30154 okano start -->
        <xml id="xmlSA140I" src="../../model/SA140I.xml"></xml>
        <xml id="xmlSA140O"></xml>
        <!-- ADD 2013/10/24 QP@30154 okano end -->

        <form name="frm00" id="frm00" onSubmit="" method="post">
            <table width="99%">
                <tr>
                    <td width="35%" class="title">グループマスタメンテナンス</td>
                    <td width="65%" align="right">
                        <input type="button" id="btnDummy" name="btnDummy" value="" style="width:0px" tabindex=-1> <!-- ﾌｫｰｶｽ設定用ﾀﾞﾐｰﾎﾞﾀﾝ -->
                        <input type="button" class="normalbutton" id="btnInsert" name="btnInsert" value="新規" style="width:80px" onClick="funDataEdit(1);">
                        <input type="button" class="normalbutton" id="btnUpdate" named="btnUpdate" value="更新" style="width:80px" onClick="funDataEdit(2);">
<!--                        <input type="button" class="normalbutton" id="btnDelete" name="btnDelete" value="削除" style="width:80px" onClick="funDataEdit(3);">-->
                        <input type="button" class="normalbutton" id="btnClear" name="btnClear" value="クリア" style="width:80px" onClick="funClear();">
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="終了" style="width:80px" onClick="funNext(0);">
                    </td>
                </tr>
            </table>

            <!-- ユーザー情報 -->
            <br>
            <div id="divUserInfo"></div>
            <br>

            <table width="250">
                <tr>
                    <td width="60">グループ</td>
                    <td width="60">
                        <input type="radio" name="rdoMode" value="" onClick="funChangeMode();" CHECKED>
                    </td>
                    <td width="60">チーム</td>
                    <td width="60">
                        <input type="radio" name="rdoMode" value="" onClick="funChangeMode();">
                    </td>
                </tr>
            </table>

            <table width="660">
                <!-- Line -->
                <tr>
                    <td colspan="2" align="center">
                        <hr>
                    </td>
                </tr>

                <tr>
                    <td width="110">グループ<font color="red">（必須）</font></td>
                    <td>
                        <select id="ddlGroup" name="ddlGroup" style="width:400px;" onChange="funChangeGroup();">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td width="110">グループ名<span id="spnHisuG"><font color="red">（必須）</font></span></td>
                    <td>
                         <input type="text" class="act_text" id="txtGroup" name="txtGroup" maxlength="100" value="" style="width:350px;">
                    </td>
                </tr>
                <!-- ADD 2013/10/24 QP@30154 okano start -->
                <tr>
                    <td width="110">会社<span id="spnHisuK"><font color="red">（必須）</font></span></td>
                    <td>
                        <select id="ddlKaisha" name="ddlKaisha" style="width:400px;">
                        </select>
                    </td>
                </tr>
                <!-- ADD 2013/10/24 QP@30154 okano end -->
                <tr>
                    <td width="110">チーム</td>
                    <td>
                        <select id="ddlTeam" name="ddlTeam" style="width:400px;" onChange="funChangeTeam();">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td width="110">チーム名<span id="spnHisuT"></span></td>
                    <td>
                         <input type="text" class="act_text" id="txtTeam" name="txtTeam" maxlength="100" value="" style="width:350px;">
                    </td>
                </tr>
            </table>

            <input type="hidden" id="hidEditMode" name="hidEditMode" value="">
        </form>
    </body>
</html>
