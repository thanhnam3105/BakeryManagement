<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- シサクイック　権限マスタメンテナンス画面                                        -->
<!-- 作成者：TT.Jinbo                                                                -->
<!-- 作成日：2009/04/09                                                              -->
<!-- 概要  ：権限データの登録、更新、削除を行う。                                    -->
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
        <script type="text/javascript" src="include/SQ090KengenMst.js"></script>
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
        <xml id="xmlJSP1110"></xml>
        <xml id="xmlJSP1120"></xml>
        <xml id="xmlJSP1130"></xml>
        <xml id="xmlJSP1140"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlAuthority" src="../common/xml/AuthorityInfo.xml"></xml>
        <xml id="xmlSA160I" src="../../model/SA160I.xml"></xml>
        <xml id="xmlSA170I" src="../../model/SA170I.xml"></xml>
        <xml id="xmlSA340I" src="../../model/SA340I.xml"></xml>
        <xml id="xmlSA350I" src="../../model/SA350I.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlSA160O"></xml>
        <xml id="xmlSA170O"></xml>
        <xml id="xmlSA340O"></xml>
        <xml id="xmlSA350O"></xml>
        <xml id="xmlRESULT"></xml>

        <form name="frm00" id="frm00" method="post">
            <!-- タイトル・ボタン -->
            <table width="99%">
                <tr>
                    <td width="32%" class="title">権限マスタメンテナンス</td>
                    <td width="68%" align="right">
                        <input type="button" class="normalbutton" id="btnInsert" name="btnInsert" value="新規" onClick="funDataEdit(1);">
                        <input type="button" class="normalbutton" id="btnUpdate" name="btnUpdate" value="更新" onClick="funDataEdit(2);">
                        <input type="button" class="normalbutton" id="btnDelete" name="btnDelete" value="削除" onClick="funDataEdit(3);">
                        <input type="button" class="normalbutton" id="btnClear" name="btnClear" value="クリア" onClick="funClear();">
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="終了" onClick="funNext(0);">
                    </td>
                </tr>
            </table>

            <!-- ユーザー情報 -->
            <br>
            <div id="divUserInfo"></div>
            <br>

            <!-- 入力・選択 -->
            <table width="980">
                <tr>
                    <td height="5"></td>
                </tr>

                <tr>
                    <td width="100">権限</td>
                    <td width="330">
                        <select id="ddlKengen" name="ddlKengen" style="width:300px;" onChange="funSearch();">
                        </select>
                    </td>
                    <td width="550" align="left">
                        <input type="button" class="normalbutton" id="btnCheckUser" name="btnCheckUser" value="権限使用ユーザ確認" style="width:140px;" onClick="funCheckUser();">
                    </td>
                </tr>
                <tr>
                    <td height="10"></td>
                </tr>
                <tr>
                    <td width="100">権限名<font color="red">（必須）</font></td>
                    <td width="330">
                        <input type="text" class="act_text" id="txtKengenName" name="txtKengenName" maxlength="60" value="" style="width:300px;">
                    </td>
                    <td width="550" align="right">
                        <input type="button" class="normalbutton" id="btnAddList" name="btnAddList" value="追加" onClick="funOpenKengenAdd();">
                        <input type="button" class="normalbutton" id="btnDelList" name="btnDelList" value="削除" onClick="funDelList();">
                    </td>
                </tr>
            </table>

            <!-- [機能一覧]リスト -->
            <div class="scroll" id="sclList" style="height:68%;width:99%;" rowSelect="true">
            <table id="dataTable" name="dataTable" cellspacing="0" width="1250px" align="center">
                <colgroup>
                    <col style="width:200px;"/>
                    <col style="width:300px;"/>
                    <col style="width:100px;"/>
                    <col style="width:350px;"/>
                    <col style="width:300px;"/>
                </colgroup>
                <thead class="rowtitle">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;">
                    <th class="columntitle" width="200">機能名</th>
                    <th class="columntitle" width="300">画面</th>
                    <th class="columntitle" width="100">機能</th>
                    <th class="columntitle" width="350">参照可能データ</th>
                    <th class="columntitle" width="300">備考</th>
                    </tr>
                </thead>
                <tbody>
                    <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" datasrc="#xmlSA160O" datafld="rec" style="width:1250px;display:none">
                        <tr class="disprow">
                            <td class="column" width="200" align="left"><span datafld="nm_kino"></span></td>
                            <td class="column" width="300" align="left"><span datafld="gamen"></span></td>
                            <td class="column" width="100" align="left"><span datafld="kino"></span></td>
                            <td class="column" width="350" align="left"><span datafld="data"></span></td>
                            <td class="column" width="300" align="left"><span datafld="biko"></span></td>
                        </tr>
                    </table>
                </table>
            </table>
            </div>

            <input type="hidden" id="hidEditMode" name="hidEditMode" value="">
        </form>
    </body>
</html>
