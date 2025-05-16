<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- 【QP@00342】シサクイック　ステータスクリア画面                                                -->
<!-- 作成者：TT.Nishigawa                                                                -->
<!-- 作成日：2011/01/26                                                              -->
<!-- 概要  ：現在ステータスの表示と、ステータスクリアを行う。                          -->
<!------------------------------------------------------------------------------------->
<html>
    <head>
        <title>原価試算システム ステータス設定画面</title>
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
        <script type="text/javascript" src="include/SQ110GenkaShisan.js"></script>
        <!-- スタイルシート -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">
    </head>

    <body class="" onLoad="funLoad_Status()">
    	
        <!-- XML Document定義 -->
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlRESULT"></xml>
        
        <form name="frm00" id="frm00" method="post">
            <table width="99%">
                <tr>
                    <td width="40%" class="title">ステータス設定</td>
                    <td width="5%">&nbsp;</td>
                    <td width="55%" align="right">
                        <input type="button" class="normalbutton" id="btnToroku" name="btnToroku" value="登録" onClick="fun_Toroku_status();" tabindex="1">
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="終了" onClick="fun_Close_st();" tabindex="2">
                    </td>
                </tr>
            </table>
            
            <br/>

			<table width="300" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>現在の依頼・承認状況</td>
                </tr>
            </table>
			<table width="300" border="1" cellspacing="0" cellpadding="0">
                <tr>
                    <th class="columntitle" width="50">研究所</th>
                    <th class="columntitle" width="50">生管</th>
                    <th class="columntitle" width="50">原調</th>
                    <th class="columntitle" width="50">工場</th>
                    <th class="columntitle" width="50">営業</th>
                </tr>
                <tr>
                    <td align="center"><div id="divStatusKenkyu_now" /></td>
                    <td align="center"><div id="divStatusSeikan_now" /></td>
                    <td align="center"><div id="divStatusGentyo_now" /></td>
                    <td align="center"><div id="divStatusKojo_now" /></td>
                    <td align="center"><div id="divStatusEigyo_now" /></td>
                </tr>
            </table>
            
            <br><br>
            
            <table width="99%" cellspacing="0" cellpadding="0" border="0">
                <tr>
                    <td>下記からステータスを選択し、「登録」ボタンを押して下さい。<hr size="1"></td>
                </tr>
            </table>
            
             <table width="99%" cellspacing="0" cellpadding="0" border="0">
                <tr>
                    <td><div id="status_table" name="status_table" /></td>
                </tr>
            </table>
            
            <table width="99%"  align="left" border="0">
                <tr>
                    <td width="40%">&nbsp;</td>
                    <td width="5%">&nbsp;</td>
                    <td width="55%" align="right">
                        <input type="button" style="visibility:hidden" class="normalbutton" id="btnClear" name="btnClear" value="ステータスクリア" onClick="fun_status_clear();" tabindex="8">
                    </td>
                </tr>
            </table>
            
        </form>
    </body>
</html>
