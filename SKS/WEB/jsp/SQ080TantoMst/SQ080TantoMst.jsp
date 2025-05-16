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
        <script type="text/javascript" src="include/SQ080TantoMst.js"></script>
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
        <xml id="xmlJSP0810"></xml>
        <xml id="xmlJSP0820"></xml>
        <xml id="xmlJSP0830"></xml>
        <xml id="xmlJSP9010"></xml>
        <xml id="xmlJSP9020"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlSA050I" src="../../model/SA050I.xml"></xml>
        <xml id="xmlSA080I" src="../../model/SA080I.xml"></xml>
        <xml id="xmlSA140I" src="../../model/SA140I.xml"></xml>
        <xml id="xmlSA170I" src="../../model/SA170I.xml"></xml>
        <xml id="xmlSA210I" src="../../model/SA210I.xml"></xml>
        <xml id="xmlSA260I" src="../../model/SA260I.xml"></xml>
        <xml id="xmlSA270I" src="../../model/SA270I.xml"></xml>
        <xml id="xmlSA290I" src="../../model/SA290I.xml"></xml>
        <xml id="xmlSA310I" src="../../model/SA310I.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlSA050O"></xml>
        <xml id="xmlSA080O"></xml>
        <xml id="xmlSA140O"></xml>
        <xml id="xmlSA170O"></xml>
        <xml id="xmlSA210O"></xml>
        <xml id="xmlSA260O"></xml>
        <xml id="xmlSA270O"></xml>
        <xml id="xmlSA290O"></xml>
        <xml id="xmlSA310O"></xml>
        <xml id="xmlRESULT"></xml>
        <!-- ADD 2013/9/25 okano【QP@30151】No.28 start -->
        <xml id="xmlJSP0010"></xml>
        <xml id="xmlFGEN2120I" src="../../model/FGEN2120I.xml"></xml>
        <xml id="xmlFGEN2120O"></xml>
        <xml id="xmlSA010I" src="../../model/SA010I.xml"></xml>
        <xml id="xmlSA010O"></xml>
        <!-- ADD 2013/9/25 okano【QP@30151】No.28 end -->

        <form name="frm00" id="frm00" method="post">
            <!-- タイトル・ボタン -->
            <table width="99%">
                <tr>
                    <td width="35%" class="title">担当者マスタメンテナンス</td>
                    <td width="65%" align="right">
                    <!-- MOD 2015/03/03 TT.Kitazawa【QP@40812】No.5 start -->
                        <!-- <input type="button" class="normalbutton" id="btnInsert" name="btnInsert" value="新規" style="width:80px" onClick="funDataEdit(1);"> -->
                        <input type="button" class="normalbutton" id="btnInsert" name="btnInsert" value="新規登録" style="width:80px" onClick="funDataEdit(1);">
                    <!-- MOD 2015/03/03 TT.Kitazawa【QP@40812】No.5 end -->
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
                        <input type="text" class="disb_text" id="txtUserId" name="txtUserId" maxlength="10" value="" style="width:300px;" onBlur="funBuryZero(this, 10);funSearch();" onChange="funChangeUserId();">
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
                        <select id="ddlKengen" id="ddlKengen" name="ddlKengen" style="width:300px;">
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
                <tr>
                    <td width="120">所属グループ<font color="red">（必須）</font></td>
                    <td>
                        <select id="ddlGroup" name="ddlGroup" style="width:300px;" onChange="funChangeGroup();">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="3" height="10">&nbsp;</td>
                </tr>
                <tr>
                    <td width="120">所属チーム<font color="red">（必須）</font></td>
                    <td>
                        <select id="ddlTeam" name="ddlTeam" style="width:300px;">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="3" height="10">&nbsp;</td>
                </tr>
                <tr>
                    <td width="120">役職<font color="red">（必須）</font></td>
                    <td>
                        <select id="ddlYakushoku" name="ddlYakushoku" style="width:300px;">
                        </select>
                    </td>
                </tr>
            </table>

            <br>

            <table width="660" align="center">
                <tr>
                    <td width="120" valign="top">担当製造会社</td>
                    <td width="340" valign="top" align="left">
                        <div id="sclList" style="overflow-y:scroll;height:130px;width:315px;" rowSelect="true">
                        <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" datasrc="#xmlSA210O" datafld="rec" style="width:300px;display:none;">
                            <tr class="disprow">
                                <td class="column" width="300" align="left"><span datafld="nm_kaisha"></span></td>
                            </tr>
                        </table>
                        </div>
                    </td>
                    <td width="187" valign="bottom">
                        <input type="button" class="normalbutton" id="btnAddList" name="btnAddList" value="追加" style="width:80px" onClick="funAddList();">
                        <input type="button" class="normalbutton" id="btnDelList" name="btnDelList" value="削除" style="width:80px" onClick="funDelList();">
                    </td>
                </tr>
            </table>

            <input type="hidden" id="hidEditMode" name="hidEditMode" value="">
            <!-- ADD 2013/11/20 QP@30154 okano start -->
            <input type="hidden" id="hidEditMode2" name="hidEditCash" value="">
            <!-- ADD 2013/11/20 QP@30154 okano end -->
        </form>

        <input type="hidden" id="hidMode" name="hidMode" value="">
    </body>
</html>
