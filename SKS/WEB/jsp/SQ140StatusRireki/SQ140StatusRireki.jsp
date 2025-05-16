<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- 【QP@00342】シサクイック　ステータス履歴画面                                                                             -->
<!-- 作成者：TT.Nishigawa                                                                                             -->
<!-- 作成日：2011/01/24                                                              -->
<!-- 概要  ：対象試作Noのステータス履歴を一覧表示する                          -->
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
        <script type="text/javascript" src="include/SQ140StatusRireki.js"></script>
        <!-- スタイルシート -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">
    </head>

    <body class="pfcancel" onLoad="funLoad()">
    
        <!-- XML Document定義 -->
        <xml id="xmlRGEN2010"></xml>
        
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN2000I" src="../../model/FGEN2000I.xml"></xml>
        
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlFGEN2000O"></xml>
        
        <xml id="xmlRESULT"></xml>

        <form name="frm00" id="frm00" method="post">
        
            <table width="99%">
                <tr>
                    <td width="20%" class="title">ステータス履歴</td>
                    <td width="25%">&nbsp;</td>
                    <td width="55%" align="right">
                                                <input type="button" class="normalbutton" id="btnCopy" name="btnCopy" value="終了" onClick="funClose();" tabindex="1">
                    </td>
                </tr>
            </table>
            <br>
			<table width="99%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>試作No　「<span id="divStatusRirekiCd"></span>」　のステータス履歴を表示しています。</td>
                </tr>
            </table>
            
            <!-- [試作ﾃﾞｰﾀ一覧]リスト -->
            <div class="scroll2" id="sclList" style="height:70%;width:910px;" rowSelect="true">
            <table id="dataTable" name="dataTable" cellspacing="0" width="908px" align="center">
                <colgroup>
                    <col style="width:30px;"/>
                    <col style="width:80px;"/>
                    <col style="width:70px;"/>
                    <col style="width:100px;"/>
                    <col style="width:80px;"/>
                    <col style="width:300px;"/>
                    <col style="width:250px;"/>
                </colgroup>
                <thead class="rowtitle">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;">
                        <th class="columntitle">&nbsp;</th>
                        
                        <th class="columntitle">作業日付</th>
                        <th class="columntitle">作業時間</th>
                        <th class="columntitle">作業者</th>
                        <th class="columntitle">作業種別</th>
                        
                        <th class="columntitle">作業詳細業務</th>
                        <th class="columntitle">
                        	<table width="250" cellspacing="0" cellpadding="0" border="1" frame="void">
                        		<tr class="columntitle" >
                        			<th colspan="5">依頼・承認状況</th>
                        		</tr>
                        		<tr class="columntitle" >
                        			<th width="50">研究所</th>
                        			<th width="50">生管</th>
                        			<th width="50">原調</th>
                        			<th width="50">工場</th>
                        			<th width="50">営業</th>
                        		</tr>
                        	</table>
                        </th>
                    </tr>
                </thead>
                <tbody>
                	<table border="0" cellspacing="0" cellpadding="0">
                		<tr>
                			<td><div id="divStatusRireki"></div></td>
                		</tr>
                	</table>
                </table>
            </table>
            </div>
            
            <table width="99%">
                <!-- データ数 -->
                <tr align="center">
                    <td height="18px">
                        <span id="spnRecInfo">データ数　：　<span id="spnRecCnt"></span> 件です(<span id="spnRowMax"></span>件毎に表示しています)　<span id="spnCurPage"></span></span>
                    </td>
                </tr>
            </table>

            <!-- ページリンク -->
            <div id="divPage" style="height:50px;font-size:12pt;"></div>
            
            <input type="hidden" id="hidShisakuNo_shaincd" name="hidShisakuNo_shaincd" value="">
            <input type="hidden" id="hidShisakuNo_nen" name="hidShisakuNo_nen" value="">
            <input type="hidden" id="hidShisakuNo_oi" name="hidShisakuNo_oi" value="">
            <input type="hidden" id="hidShisakuNo_eda" name="hidShisakuNo_eda" value="">

        </form>
    </body>
</html>
