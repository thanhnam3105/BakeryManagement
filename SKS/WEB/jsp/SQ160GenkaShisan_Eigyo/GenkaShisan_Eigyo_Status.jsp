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
        <script type="text/javascript" src="include/SQ160GenkaShisan.js"></script>
        <!-- スタイルシート -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">
    </head>

    <body class="" onLoad="funLoad_Status()">
    	
        <!-- XML Document定義 -->
        <xml id="xmlRGEN2170"></xml>
        <xml id="xmlRGEN2180"></xml>
        
        <xml id="xmlFGEN0010I" src="../../model/FGEN0010I.xml"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN2160I" src="../../model/FGEN2160I.xml"></xml>
        
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlFGEN0010O"></xml>
        <xml id="xmlFGEN2160O"></xml>
        <xml id="xmlRESULT"></xml>
        
        <form name="frm00" id="frm00" method="post">
            <table width="99%">
                <tr>
                    <td width="40%" class="title">ステータス設定</td>
                    <td width="5%">&nbsp;</td>
                    <td width="55%" align="right">
                        <input type="button" class="normalbutton" id="btnClear" name="btnClear" value="登録" onClick="fun_Toroku();" tabindex="1">
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
            
            <table width="99%" cellspacing="0" cellpadding="0">
                <tr>
                    <td>下記からステータスを選択し、「登録」ボタンを押して下さい。<hr size="1"></td>
                </tr>
            </table>
            
            <table id="dataTable" name="dataTable" border="0" cellspacing="0" width="300" align="left">
                <tr>
                	<td align="center"><input type="radio" name="chk" onclick="chkSaiyou();" value="0" tabindex="3" checked></td>
                	<td>保存（ステータス変更無し）</td>
               	</tr>
               	<tr>
                	<td align="center"><input type="radio" name="chk" onclick="chkSaiyou();" value="1" tabindex="4"></td>
                	<td>試算依頼</td>
               	</tr>
               	<tr>
                	<td align="center"><input type="radio" name="chk" onclick="chkSaiyou();" value="2" tabindex="5"></td>
                	<td>確認完了</td>
               	</tr>
               	<tr>
                	<td align="center"><input type="radio" name="chk" onclick="chkSaiyou();" value="3" tabindex="6"></td>
                	<td>
                		採用有無確定（採用有り）
                	</td>
               	</tr>
               	<tr>
                	<td align="center"></td>
                	<td>
                		採用サンプルＮＯ<br />
                		<select name="ddlSaiyoSample" id="ddlSaiyoSample" onChange="chkSaiyou();" style="background-color:#ffffff;width:150px;height:16px;" disabled tabindex="7" >
                        </select>
                	</td>
               	<tr>
                	<td align="center"><input type="radio" name="chk" onclick="chkSaiyou();" value="4" tabindex="8"></td>
                	<td>採用有無確定（採用無し）</td>
               	</tr>
               	<tr>
                	<td align="center"><input type="radio" name="chk" onclick="chkSaiyou();" value="5" tabindex="9"></td>
                	<td>試算途中での不採用決定</td>
               	</tr>
            </table>
            
            <!-- ステータス -->
            <input type="hidden" id="st_kenkyu" name="st_kenkyu" value="">
            <input type="hidden" id="st_seikan" name="st_seikan" value="">
            <input type="hidden" id="st_gentyo" name="st_gentyo" value="">
            <input type="hidden" id="st_kojo" name="st_kojo" value="">
            <input type="hidden" id="st_eigyo" name="st_eigyo" value="">
            
            <!-- 試作No -->
            <input type="hidden" id="cd_shain" name="cd_shain" value="">
            <input type="hidden" id="nen" name="nen" value="">
            <input type="hidden" id="oi" name="oi" value="">
            <input type="hidden" id="eda" name="eda" value="">
            
        </form>
    </body>
</html>
