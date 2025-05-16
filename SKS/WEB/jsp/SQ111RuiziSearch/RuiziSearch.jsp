<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- シサクイック　類似品検索画面                                                -->
<!-- 作成者：TT.Nishigawa                                                                -->
<!-- 作成日：2009/10/20                                                              -->
<!-- 概要  ：類似品検索画面                          -->
<!------------------------------------------------------------------------------------->

<HTML>
	<HEAD>
		<title>原価試算システム 類似品検索画面</title>
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
        <script type="text/javascript" src="include/SQ111RuiziSearch.js"></script>
        <!-- スタイルシート -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">
        
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=shift_jis">
		<META HTTP-EQUIV="Content-Language" content="ja">
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	</HEAD>
	
	<body class="pfcancel" onLoad="funLoad()">
	<!-- XML Document定義 -->
	<xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
    <xml id="xmlUSERINFO_O"></xml>
        
    <xml id="xmlRESULT"></xml>
	 
	<xml id="xmlRGEN1010"></xml>
    <xml id="xmlFGEN1010I" src="../../model/FGEN1010I.xml"></xml>
    <xml id="xmlFGEN1010O"></xml>
    <xml id="xmlFGEN1020I" src="../../model/FGEN1020I.xml"></xml>
    <xml id="xmlFGEN1020O"></xml>
    
    <xml id="xmlRGEN1020"></xml>
	
	<xml id="xmlRGEN1030"></xml>
    <xml id="xmlFGEN1030I" src="../../model/FGEN1030I.xml"></xml>
    <xml id="xmlFGEN1030O"></xml>
    
    <xml id="xmlRGEN1040"></xml>
    <xml id="xmlFGEN1035I" src="../../model/FGEN1035I.xml"></xml>
    <xml id="xmlFGEN1035O"></xml>
    
    <xml id="xmlRGEN1050"></xml>
    <xml id="xmlFGEN1040I" src="../../model/FGEN1040I.xml"></xml>
    <xml id="xmlFGEN1040O"></xml>
    
    <xml id="xmlRGEN1060"></xml>
    <xml id="xmlFGEN1045I" src="../../model/FGEN1045I.xml"></xml>
    <xml id="xmlFGEN1045O"></xml>
	
	<form name="frm00" id="frm00" method="post">
	
		<!----------------------------------------- ヘッダー部 ----------------------------------------->
		<table width="99%">
			<tr>
				<td width="20%" class="title">類似品検索</td>
				<td width="10%" class="title2">レベル１</td>
				<td width="65%" align="right">
					<input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="終了" onClick="funEndClick()" tabindex="23">
				</td>
			</tr>
		</table>
		
		<br><br>
		
		<!------------------------------------- 製品検索タイトル --------------------------------------->
		<a name="lnkSeihinKensaku" />
		<table width="99%">
			<tr>
                <!--製品検索ライン-->
				<td colspan="3">
					<hr>
				</td>
			</tr>
			<tr>
				<!--製品検索文字-->
				<td width="70%">
					製品検索
				</td>
				<!--製品検索リンク-->
				<td width="30%" align="right">
					<a href="RuiziSearch.jsp#lnkShizaiKensaku" tabindex="1">資材検索</a>&nbsp;
					<a href="RuiziSearch.jsp#lnkShizaiSentaku" tabindex="1">選択資材</a>
				</td>
			</tr>
			






		</table>
		
		<!----------------------------------------- 製品検索部 ----------------------------------------->
		<table width="99%">
			<tr>
				<!--会社指定-->
				<td width="10%">
					会社
				</td>
				<td width="40%">
					<select name="selectSeihinKaisha" id="selectSeihinKaisha" onChange="funKojoChange(this, document.frm00.selectSeihinKojo,1)" style="width:150px;" tabindex="1">
					</select>
				</td>
				<!--検索ボタン-->
				<td width="40%" align="right">
					<input type="button" class="normalbutton" id="btnSeihinSearch" name="btnSeihinSearch" value="検索" onClick="funSeihinClick()" tabindex="5">
				</td>
			</tr>
			
			<tr>
				<!--工場指定-->
				<td>
					工場
				</td>
				<td>
					<select name="selectSeihinKojo" id="selectSeihinKojo" style="width:150px;" tabindex="2">
					</select>
				</td>
				<td>
				</td>
			</tr>
			
			<tr>
				<!--製品コード指定-->
				<td>
					製品コード
				</td>
				<td>
					<input type="text" name="inputSeihinCd" id="inputSeihinCd" style="width:150px;" maxlength="11" onblur="funInsertCdZero(this, 1)" class="disb_text" value="" tabindex="3" />
				</td>
				<td>
				</td>
			</tr>
			
			<tr>
				<!--製品名指定-->
				<td>
					製品名
				</td>
				<td>
					<input type="text" name="inputSeihinNm" id="inputSeihinNm" style="width:400px;ime-mode:active;" class="disb_text" value="" tabindex="4" />
				</td>
				<td>
				</td>
			</tr>
		</table>
		
		<!---------------------------------------- 製品検索結果 ---------------------------------------->
		<!--2010/02/15 Nakamura UPDATE START-->
		<!--<div class="scroll_genka" id="sclList1" style="height:45%;width:99%;" rowSelect="true">-->
		<div class="scroll_genka" id="sclList1" style="height:50%;width:99%;" rowSelect="true">
		<!--2010/02/15 Nakamura UPDATE END---->
		    <div id="seihinTable"></div>
		</div>
		
		<!------------------------------------- 製品検索結果フッター ----------------------------------->
		<table width="99%">
			<tr>
				<!--一括選択-->
				<td width="10%" align="center">
					<input type="checkbox" name="chkSeihinIkkatu" onClick="funSeihinCheck()" tabindex="7" />一括選択
				</td>
				<!--検索結果情報-->
				<td width="70%" align="center">
					<span id="spnRecInfo">データ数　：　<span id="spnRecCnt"></span> 件です(<span id="spnRowMax"></span>件毎に表示しています)　<span id="spnCurPage"></span></span>
				</td>
				
				<td width="20%" align="center">
				</td>
			</tr>
			
			<tr>
				<td width="10%" align="center">
				</td>
				
				<!--ページ選択-->
				<td width="70%" align="center">
					<div id="divPage" style="height:50px;font-size:12pt;" ></div>
				</td>
				
				<td width="20%" align="center">
				</td>
				
			</tr>
			
			<tr>
				<!--資材検索ボタン-->
				<td width="10%" align="center">
					<input type="button" class="normalbutton" id="btnSeihinShizai" name="btnSeihinShizai" value="資材検索" onClick="funSeihin_ShizaiClick()" tabindex="9">
				</td>
				
				<td width="70%" align="center">
				</td>
				<td width="20%" align="center">
				</td>
			</tr>
		</table>
		
		<br><br><br>
		<br><br><br>
		<br><br><br>
		<br>
		
		<!-------------------------------------- 資材検索タイトル -------------------------------------->
		<a name="lnkShizaiKensaku" />
		<table width="99%">
			<tr>
                <!--資材検索ライン-->
				<td colspan="3">
					<hr>
				</td>
			</tr>
			<tr>
				<!--資材検索文字-->
				<td width="70%">
					資材検索
				</td>
				<!--資材検索リンク-->
				<td width="30%" align="right">
					<a href="RuiziSearch.jsp#lnkSeihinKensaku" tabindex="10">製品検索</a>&nbsp;
					<a href="RuiziSearch.jsp#lnkShizaiSentaku" tabindex="10">選択資材</a>
				</td>
			</tr>
			
			
			
			
			
			
			
		</table>
		
		<!----------------------------------------- 資材検索部 ----------------------------------------->
		<table width="99%">
			<tr>
				<!--会社指定-->
				<td width="10%">
					会社
				</td>
				<td width="40%">
					<select name="selectShizaiKaisha" id="selectShizaiKaisha" onChange="funKojoChange(this, document.frm00.selectShizaiKojo,2)" style="width:150px;" tabindex="10">
					</select>
				</td>
				<!--検索ボタン-->
				<td width="40%" align="right">
					<input type="button" class="normalbutton" id="btnShizaiSearch" name="btnShizaiSearch" value="検索" onClick="funShizaiClick()" tabindex="14">
				</td>
			</tr>
			
			<tr>
				<!--工場指定-->
				<td>
					工場
				</td>
				<td>
					<select name="selectShizaiKojo" id="selectShizaiKojo" style="width:150px;" tabindex="11">
					</select>
				</td>
				<td>
				</td>
			</tr>
			
			<tr>
				<!--資材コード指定-->
				<td>
					資材コード
				</td>
				<td>
					<input type="text" name="inputShizaiCd" id="inputShizaiCd" style="width:150px;" maxlength="11" onblur="funInsertCdZero(this, 2)" class="disb_text" value="" tabindex="12" />
				</td>
				<td>
				</td>
			</tr>
			
			<tr>
				<!--資材名指定-->
				<td>
					資材名
				</td>
				<td>
					<input type="text" name="inputShizaiNm" id="inputShizaiNm" style="width:400px;ime-mode:active;" class="disb_text" value="" tabindex="13" />
				</td>
				<td>
				</td>
			</tr>
			
			<tr>
				<!--検索状態-->
				<td>
					現在の検索状態
				</td>
				<td>
					<div id="divKensakuZyotai" ></div>
				</td>
				<td>
				</td>
			</tr>
			
		</table>
		
		<!---------------------------------------- 資材検索結果 ---------------------------------------->
		<!--2010/02/15 Nakamura UPDATE START-->
		<!--div class="scroll_genka" id="sclList1" style="height:45%;width:99%;" rowSelect="true">-->
		<div class="scroll_genka" id="sclList1" style="height:55%;width:99%;" rowSelect="true">
		<!--2010/02/15 Nakamura UPDATE END---->
		    <div id="shizaiTable"></div>
		</div>
		
		<!------------------------------------- 資材検索結果フッター ----------------------------------->
		<table width="99%">
			<tr>
				<!--一括選択-->
				<td width="10%" align="center">
					<input type="checkbox" name="chkShizaiIkkatu" onClick="funShizaiCheck()" tabindex="16" />一括選択
				</td>
				<!--検索結果情報-->
				<td width="70%" align="center">
					<span id="spnRecInfo2">データ数　：　<span id="spnRecCnt2"></span> 件です(<span id="spnRowMax2"></span>件毎に表示しています)　<span id="spnCurPage2"></span></span>
				</td>
				
				<td width="20%" align="center">
				</td>
			</tr>
			
			<tr>
				<td width="10%" align="center">
				</td>
				
				<!--ページ選択-->
				<td width="70%" align="center">
					<div id="divPage2" style="height:50px;font-size:12pt;"></div>
				</td>
				
				<td width="20%" align="center">
				</td>
				
			</tr>
			
			<tr>
				<!--資材選択ボタン-->
				<td width="10%" align="center">
					<input type="button" class="normalbutton" id="btnShizaiSelect" name="btnShizaiSelect" value="資材選択" onClick="funShizaiSentakuClick()" tabindex="18">
				</td>
				
				<td width="70%" align="center">
				</td>
				<td width="20%" align="center">
				</td>
			</tr>
		</table>
		
		<br><br><br>
		<br><br><br>
		<br><br><br>
		<br>
		
		<!-------------------------------------- 資材選択タイトル -------------------------------------->
		<a name="lnkShizaiSentaku" />
		<table width="99%">
			<tr>
				<!--資材選択ライン-->
				<td colspan="3">
					<hr>
				</td>
			</tr>
			<tr>
				<!--資材選択文字-->
				<td width="70%">
					選択資材
				</td>
				<!--資材選択リンク-->
				<td width="30%" align="right">
					<a href="RuiziSearch.jsp#lnkSeihinKensaku" tabindex="18">製品検索</a>&nbsp;
					<a href="RuiziSearch.jsp#lnkShizaiKensaku" tabindex="18">資材検索</a>
				</td>
			</tr>
			
			
			
			
			
			
			
		</table>
		
		<!---------------------------------------- 資材候補一覧 ---------------------------------------->
		<!--2010/02/15 Nakamura UPDATE START-->
		<!--<div class="scroll_genka" id="sclList" style="height:45%;width:99%;" rowSelect="true">-->
		<div class="scroll_genka" id="sclList" style="height:55%;width:99%;" rowSelect="true">
		<!--2010/02/15 Nakamura UPDATE END---->
			<table id="dataTable" name="dataTable" cellspacing="0" width="1000px">
				<colgroup>
					<col style="width:50px;" />
					<col style="width:50px;"/>
					<col style="width:100px;"/>
					<col style="width:400px;"/>
					<col style="width:100px;"/>
					<col style="width:100px;"/>
					<col style="width:100px;"/>
					<col style="width:100px;"/>
				</colgroup>
				<thead class="rowtitle">
					<tr style="top:expression(offsetParent.scrollTop);position:relative;">
						<th class="columntitle">選択</th>
						<th class="columntitle">工場</th>
						<th class="columntitle">資材コード</th>
						<th class="columntitle">資材名</th>
						<th class="columntitle">単価</th>
						<th class="columntitle">歩留(％)</th>
						<th class="columntitle">使用量/ケース</th>
						<th class="columntitle">製品コード</th>
					</tr>
				</thead>
				<tbody>
					<table class="detail" id="tblListKoho" cellpadding="0" cellspacing="0" border="1" datasrc="" datafld="rec" style="width:999px;">
					</table>
				</table>
			</table>
		</div>
		
		<!------------------------------------- 資材候補一覧フッター ----------------------------------->
		<table width="99%" style="height:25%;">
			<tr style="vertical-align:top;height:35%;">
				<!--一括選択-->
				<td width="10%" align="center">
					<input type="checkbox" name="chkKohoIkkatu" onClick="funKohoCheck()" tabindex="20" />一括選択
				</td>
				<!--検索結果情報-->
				<td width="70%" align="center">
				</td>
				
				<td width="20%" align="center">
				</td>
			</tr>
			<tr style="vertical-align:top;height:65%;">
			
				<td width="100%" colspan="3" align="left">
					<input type="button" class="normalbutton" id="btnShizaiSakuzyo" name="btnShizaiSakuzyo" value="削除" onClick="funDeleteKoho()" tabindex="21">
					<input type="button" class="normalbutton" id="btnShizaiKakutei" name="btnShizaiKakutei" value="確定" onClick="funKakuteiKoho()" tabindex="22">
				</td>
				
			</tr>
		</table>
		
		<br><br><br>
		<br><br><br>
		<br><br><br>
		<br>
		
	</form>
	
	</body>
</HTML>