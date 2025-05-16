<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- 【KPX@1502111_No.10】シサクイック　サンプルコピー選択画面                       -->
<!-- 作成者：TT.Nishigawa                                                            -->
<!-- 作成日：2011/01/26                                                              -->
<!-- 概要  ：サンプルコピー選択指定を行う。                                          -->
<!------------------------------------------------------------------------------------->
<html>
    <head>
        <title>原価試算システム サンプルコピー画面</title>
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
        <script type="text/javascript" src="include/SQ135SampleCopy.js"></script>
        <!-- スタイルシート -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">
    </head>

    <body class="" onLoad="funLoad();">

        <!-- XML Document定義 -->
<!--         <xml id="xmlRGEN2020"></xml>
        <xml id="xmlRGEN2030"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN2010I" src="../../model/FGEN2010I.xml"></xml>
        <xml id="xmlFGEN2020I" src="../../model/FGEN2020I.xml"></xml>
        <xml id="xmlFGEN2030I" src="../../model/FGEN2030I.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlFGEN2010O"></xml>
        <xml id="xmlFGEN2020O"></xml>
        <xml id="xmlFGEN2030O"></xml>
        <xml id="xmlRESULT"></xml>
 -->
        <form name="frm00" id="frm00" method="post">
            <table width="99%">
                <tr>
                    <td width="45%" class="title">コピー選択画面</td>
                    <td width="55%" align="right">
                        <input type="button" class="normalbutton" id="btnCopy" name="btnCopy" value="確定" onClick="funCopy();" tabindex="21">
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="終了" onClick="funClose();" tabindex="22">
                    </td>
                </tr>
            </table>

            <br/>
			<div>
			  <table width="90%" border="0" cellspacing="4" cellpadding="0" align="center">
                <tr>
                    <td>コピー元サンプルNoと<br>コピー先サンプルNoを選択してください。
                    <br/><br/></td>
                </tr>
                <tr>
                    <td>コピー元サンプルNo</td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;
                        <select name="selCopyMoto" id="selCopyMoto" style="width:200px;height:16px;" tabindex="1" >
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><br/>コピー先サンプルNo</td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;
                        <select name="selCopySaki" id="selCopySaki" style="width:200px;height:16px;" tabindex="2" >
                        </select>
                    </td>
                </tr>
              </table>
			</div>
        </form>
    </body>
</html>
