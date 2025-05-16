<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- シサクイック　担当者マスタメンテナンス画面                                      -->
<!-- 作成者：TT.Jinbo                                                                -->
<!-- 作成日：2009/04/06                                                              -->
<!-- 概要  ：担当者データの登録、更新、削除を行う。                                  -->
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
        <script type="text/javascript" src="include/SQ150EigyoTantoMst.js"></script>
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

    <body class="pfcancel" onLoad="funLoad();">
        <!-- XML Document定義 -->
        <xml id="xmlRGEN2040"></xml>
        <xml id="xmlRGEN2050"></xml>
        <xml id="xmlRGEN2060"></xml>
        <xml id="xmlRGEN2070"></xml>
        <xml id="xmlJSP0010"></xml>

        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN2040I" src="../../model/FGEN2040I.xml"></xml>
        <xml id="xmlFGEN2050I" src="../../model/FGEN2050I.xml"></xml>
        <xml id="xmlFGEN2120I" src="../../model/FGEN2120I.xml"></xml>
        <xml id="xmlFGEN2060I" src="../../model/FGEN2060I.xml"></xml>
        <xml id="xmlFGEN2070I" src="../../model/FGEN2070I.xml"></xml>
        <xml id="xmlFGEN2080I" src="../../model/FGEN2080I.xml"></xml>
        <xml id="xmlSA010I" src="../../model/SA010I.xml"></xml>

        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlFGEN2040O"></xml>
        <xml id="xmlFGEN2050O"></xml>
        <xml id="xmlFGEN2120O"></xml>
        <xml id="xmlFGEN2060O"></xml>
        <xml id="xmlFGEN2070O"></xml>
        <xml id="xmlFGEN2080O"></xml>
        <xml id="xmlSA010O"></xml>

        <xml id="xmlRESULT"></xml>

        <form name="frm00" id="frm00" method="post">
            <!-- タイトル・ボタン -->
            <table width="99%">
                <tr>
                    <td width="35%" class="title">担当者マスタメンテナンス（営業）</td>
                    <td width="65%" align="right">
                        <!-- MOD 2013/9/25 okano【QP@30151】No.28 start -->
<!--                         <input type="button" class="normalbutton" id="btnMenu" name="btnMenu" value="メインメニューへ" style="width:120px" onClick="funNextMenu();"> -->
                        <input type="button" class="normalbutton" id="btnMenu" name="btnMenu" value="メインメニューへ" style="width:120px" onClick="funNext(1);">
                        <!-- MOD 2013/9/25 okano【QP@30151】No.28 end -->
                        <!-- MOD start 2015/03/03 TT.Kitazawa【QP@40812】No.5 -->
                        <!-- <input type="button" class="normalbutton" id="btnInsert" name="btnInsert" value="新規" style="width:80px" onClick="funDataEdit(1);"> -->
                        <input type="button" class="normalbutton" id="btnInsert" name="btnInsert" value="新規登録" style="width:80px" onClick="funDataEdit(1);">
                        <!-- MOD end 2015/03/03 TT.Kitazawa【QP@40812】No.5 -->
                        <input type="button" class="normalbutton" id="btnUpdate" named="btnUpdate" value="更新" style="width:80px" onClick="funDataEdit(2);">
                        <input type="button" class="normalbutton" id="btnDelete" name="btnDelete" value="削除" style="width:80px" onClick="funDataEdit(3);">
                        <input type="button" class="normalbutton" id="btnClear" name="btnClear" value="クリア" style="width:80px" onClick="funClear();">
                        <!-- MOD 2013/9/25 okano【QP@30151】No.28 start -->
<!--                         <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="終了" style="width:80px" onClick="funNext(0);"> -->
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="終了" style="width:80px" onClick="funClose();">
                        <!-- MOD 2013/9/25 okano【QP@30151】No.28 end -->
                    </td>
                </tr>
            </table>

            <!-- ユーザー情報 -->
            <br>
            <div id="divUserInfo"></div>
            <br>

            <!-- 入力・選択 -->
            <table width="660" align="center">
                <tr>
                    <!-- MOD 2015/03/03 TT.Kitazawa【QP@40812】No.23 start -->
                    <!-- <td width="120">ユーザID<font color="red">（必須）</font></td> -->
                    <td width="120">社員番号<font color="red">（必須）</font></td>
                    <!-- MOD 2015/03/03 TT.Kitazawa【QP@40812】No.23 end -->
                    <td width="340">
                        <span class="ninput" format="10,0" comma="false" required="true" defaultfocus="false" id="em_UserId">
                        <!-- 【QP@10713】シサクイック改良 No.25 -->
                        <!-- <input type="text" class="disb_text" id="txtUserId" name="txtUserId" maxlength="10" value="" style="width:300px;" onBlur="funBuryZero(this, 10);funSearch();" onChange="funChangeUserId();"> -->
                        <input type="text" class="disb_text" id="txtUserId" name="txtUserId" maxlength="10" value="" style="width:300px;" onBlur="funBuryZero(this, 10);funSearch();" onChange="funChangeUserId();funChangeKengen();">
                        </span>
                    </td>
                    <td align="left">
                        <input type="button" class="normalbutton" id="btnSearchUser" name="btnSearchUser" value="検索" style="width:80px" onClick="funSearchUser();">
                    </td>
                </tr>

                <!-- Line -->
                <tr>
                    <td colspan="3" align="center"><hr></td>
                </tr>

                <tr>
                    <td width="120">パスワード<font color="red">（必須）</font></td>
                    <td width="310">
                        <input type="password" class="disb_text" id="txtPass" name="txtPass" maxlength="30" value="" style="width:300px;">
                    </td>
                </tr>
                <tr>
                    <td colspan="3" height="10">&nbsp;</td>
                </tr>
                <tr>
                    <td width="120">権限<font color="red">（必須）</font></td>
                    <td>
                        <select id="ddlKengen" id="ddlKengen" name="ddlKengen" style="width:300px;" onChange="funChangeKengen();">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="3" height="10">&nbsp;</td>
                </tr>
                <tr>
                    <td width="120">氏名<font color="red">（必須）</font></td>
                    <td>
                       <input type="text" class="act_text" id="txtUserName" name="txtUserName" maxlength="60" value="" style="width:300px;">
                    </td>
                </tr>
                <tr>
                    <td colspan="3" height="10">&nbsp;</td>
                </tr>
                <tr>
                    <td width="120">所属会社<font color="red">（必須）</font></td>
                    <td>
                        <select id="ddlKaisha" name="ddlKaisha" style="width:300px;" onChange="funChangeKaisha();">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="3" height="10">&nbsp;</td>
                </tr>
                <tr>
                    <td width="120">所属部署<font color="red">（必須）</font></td>
                    <td>
                        <select id="ddlBusho" name="ddlBusho" style="width:300px;">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="3" height="10">&nbsp;</td>
                </tr>
            </table>

            <br>

            <table width="660" align="center">
                <tr>
                    <!-- MOD start 2015/03/03 TT.Kitazawa【QP@40812】No.19 -->
                    <!-- <td width="120" valign="top">担当上司</td> -->
                    <td width="120" valign="top">共有メンバー</td>
                    <td width="340" valign="top" align="left">
                        <!-- <input type="text" class="act_text" id="txtUserNameJosi" name="txtUserNameJosi" maxlength="60" value="" style="width:300px;" disabled>
                        <input type="hidden" id="hdnUserNameJosi" name="hdnUserNameJosi" maxlength="60" value=""> -->

                        <div id="sclList" style="overflow-y:scroll;height:118px;width:315px;" rowSelect="true">
                        <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" datasrc="#xmlFGEN2060O" datafld="rec" style="width:300px;display:none;">
                            <tr class="disprow">
                                <td class="column" width="300" align="left" disabled><span datafld="nm_member"></span></td>
                            </tr>
                        </table>
                        </div>
                    </td>
                    <td width="187" valign="bottom">
                        <!-- <input type="button" class="normalbutton" id="btnJosiSearch" name="btnJosiSearch" value="検索" style="width:80px" onClick="funSearchUserJosi();">
                        <input type="button" class="normalbutton" id="btnJosiDel" name="btnJosiDel" value="上司クリア" style="width:80px" onClick="funDelJosi();"> -->

                        <input type="button" class="normalbutton" id="btnAddList" name="btnAddList" value="追加" style="width:80px" onClick="funAddList();">
                        <input type="button" class="normalbutton" id="btnDelList" name="btnDelList" value="削除" style="width:80px" onClick="funDelList();">
                    </td>
                    <!-- MOD end 2015/03/03 TT.Kitazawa【QP@40812】No.19 -->
                </tr>
            </table>

            <input type="hidden" id="hidEditMode" name="hidEditMode" value="">
            <input type="hidden" id="hidOpnerSearch" name="hidOpnerSearch" value="">
        </form>

        <input type="hidden" id="hidMode" name="hidMode" value="">
    </body>
</html>
