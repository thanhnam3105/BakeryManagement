<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@�ގ��i�������                                                -->
<!-- �쐬�ҁFTT.Nishigawa                                                                -->
<!-- �쐬���F2009/10/20                                                              -->
<!-- �T�v  �F�ގ��i�������                          -->
<!------------------------------------------------------------------------------------->

<HTML>
	<HEAD>
		<title>�������Z�V�X�e�� �ގ��i�������</title>
		<!-- ���� -->
        <script type="text/javascript" src="../common/js/Const.js"></script>
        <script type="text/javascript" src="../common/js/MessageControl.js"></script>
        <script type="text/javascript" src="../common/js/ConnectionControl.js"></script>
        <script type="text/javascript" src="../common/js/XmlControl.js"></script>
        <script type="text/javascript" src="../common/js/AjaxControl.js"></script>
        <script type="text/javascript" src="../common/js/CheckControl.js"></script>
        <script type="text/javascript" src="../common/js/DisplayControl.js"></script>
        <script type="text/javascript" src="../common/js/InputControl.js"></script>
        <!-- �� -->
        <script type="text/javascript" src="include/SQ111RuiziSearch.js"></script>
        <!-- �X�^�C���V�[�g -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">
        
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=shift_jis">
		<META HTTP-EQUIV="Content-Language" content="ja">
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	</HEAD>
	
	<body class="pfcancel" onLoad="funLoad()">
	<!-- XML Document��` -->
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
	
		<!----------------------------------------- �w�b�_�[�� ----------------------------------------->
		<table width="99%">
			<tr>
				<td width="20%" class="title">�ގ��i����</td>
				<td width="10%" class="title2">���x���P</td>
				<td width="65%" align="right">
					<input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="�I��" onClick="funEndClick()" tabindex="23">
				</td>
			</tr>
		</table>
		
		<br><br>
		
		<!------------------------------------- ���i�����^�C�g�� --------------------------------------->
		<a name="lnkSeihinKensaku" />
		<table width="99%">
			<tr>
                <!--���i�������C��-->
				<td colspan="3">
					<hr>
				</td>
			</tr>
			<tr>
				<!--���i��������-->
				<td width="70%">
					���i����
				</td>
				<!--���i���������N-->
				<td width="30%" align="right">
					<a href="RuiziSearch.jsp#lnkShizaiKensaku" tabindex="1">���ތ���</a>&nbsp;
					<a href="RuiziSearch.jsp#lnkShizaiSentaku" tabindex="1">�I������</a>
				</td>
			</tr>
			






		</table>
		
		<!----------------------------------------- ���i������ ----------------------------------------->
		<table width="99%">
			<tr>
				<!--��Ўw��-->
				<td width="10%">
					���
				</td>
				<td width="40%">
					<select name="selectSeihinKaisha" id="selectSeihinKaisha" onChange="funKojoChange(this, document.frm00.selectSeihinKojo,1)" style="width:150px;" tabindex="1">
					</select>
				</td>
				<!--�����{�^��-->
				<td width="40%" align="right">
					<input type="button" class="normalbutton" id="btnSeihinSearch" name="btnSeihinSearch" value="����" onClick="funSeihinClick()" tabindex="5">
				</td>
			</tr>
			
			<tr>
				<!--�H��w��-->
				<td>
					�H��
				</td>
				<td>
					<select name="selectSeihinKojo" id="selectSeihinKojo" style="width:150px;" tabindex="2">
					</select>
				</td>
				<td>
				</td>
			</tr>
			
			<tr>
				<!--���i�R�[�h�w��-->
				<td>
					���i�R�[�h
				</td>
				<td>
					<input type="text" name="inputSeihinCd" id="inputSeihinCd" style="width:150px;" maxlength="11" onblur="funInsertCdZero(this, 1)" class="disb_text" value="" tabindex="3" />
				</td>
				<td>
				</td>
			</tr>
			
			<tr>
				<!--���i���w��-->
				<td>
					���i��
				</td>
				<td>
					<input type="text" name="inputSeihinNm" id="inputSeihinNm" style="width:400px;ime-mode:active;" class="disb_text" value="" tabindex="4" />
				</td>
				<td>
				</td>
			</tr>
		</table>
		
		<!---------------------------------------- ���i�������� ---------------------------------------->
		<!--2010/02/15 Nakamura UPDATE START-->
		<!--<div class="scroll_genka" id="sclList1" style="height:45%;width:99%;" rowSelect="true">-->
		<div class="scroll_genka" id="sclList1" style="height:50%;width:99%;" rowSelect="true">
		<!--2010/02/15 Nakamura UPDATE END---->
		    <div id="seihinTable"></div>
		</div>
		
		<!------------------------------------- ���i�������ʃt�b�^�[ ----------------------------------->
		<table width="99%">
			<tr>
				<!--�ꊇ�I��-->
				<td width="10%" align="center">
					<input type="checkbox" name="chkSeihinIkkatu" onClick="funSeihinCheck()" tabindex="7" />�ꊇ�I��
				</td>
				<!--�������ʏ��-->
				<td width="70%" align="center">
					<span id="spnRecInfo">�f�[�^���@�F�@<span id="spnRecCnt"></span> ���ł�(<span id="spnRowMax"></span>�����ɕ\�����Ă��܂�)�@<span id="spnCurPage"></span></span>
				</td>
				
				<td width="20%" align="center">
				</td>
			</tr>
			
			<tr>
				<td width="10%" align="center">
				</td>
				
				<!--�y�[�W�I��-->
				<td width="70%" align="center">
					<div id="divPage" style="height:50px;font-size:12pt;" ></div>
				</td>
				
				<td width="20%" align="center">
				</td>
				
			</tr>
			
			<tr>
				<!--���ތ����{�^��-->
				<td width="10%" align="center">
					<input type="button" class="normalbutton" id="btnSeihinShizai" name="btnSeihinShizai" value="���ތ���" onClick="funSeihin_ShizaiClick()" tabindex="9">
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
		
		<!-------------------------------------- ���ތ����^�C�g�� -------------------------------------->
		<a name="lnkShizaiKensaku" />
		<table width="99%">
			<tr>
                <!--���ތ������C��-->
				<td colspan="3">
					<hr>
				</td>
			</tr>
			<tr>
				<!--���ތ�������-->
				<td width="70%">
					���ތ���
				</td>
				<!--���ތ��������N-->
				<td width="30%" align="right">
					<a href="RuiziSearch.jsp#lnkSeihinKensaku" tabindex="10">���i����</a>&nbsp;
					<a href="RuiziSearch.jsp#lnkShizaiSentaku" tabindex="10">�I������</a>
				</td>
			</tr>
			
			
			
			
			
			
			
		</table>
		
		<!----------------------------------------- ���ތ����� ----------------------------------------->
		<table width="99%">
			<tr>
				<!--��Ўw��-->
				<td width="10%">
					���
				</td>
				<td width="40%">
					<select name="selectShizaiKaisha" id="selectShizaiKaisha" onChange="funKojoChange(this, document.frm00.selectShizaiKojo,2)" style="width:150px;" tabindex="10">
					</select>
				</td>
				<!--�����{�^��-->
				<td width="40%" align="right">
					<input type="button" class="normalbutton" id="btnShizaiSearch" name="btnShizaiSearch" value="����" onClick="funShizaiClick()" tabindex="14">
				</td>
			</tr>
			
			<tr>
				<!--�H��w��-->
				<td>
					�H��
				</td>
				<td>
					<select name="selectShizaiKojo" id="selectShizaiKojo" style="width:150px;" tabindex="11">
					</select>
				</td>
				<td>
				</td>
			</tr>
			
			<tr>
				<!--���ރR�[�h�w��-->
				<td>
					���ރR�[�h
				</td>
				<td>
					<input type="text" name="inputShizaiCd" id="inputShizaiCd" style="width:150px;" maxlength="11" onblur="funInsertCdZero(this, 2)" class="disb_text" value="" tabindex="12" />
				</td>
				<td>
				</td>
			</tr>
			
			<tr>
				<!--���ޖ��w��-->
				<td>
					���ޖ�
				</td>
				<td>
					<input type="text" name="inputShizaiNm" id="inputShizaiNm" style="width:400px;ime-mode:active;" class="disb_text" value="" tabindex="13" />
				</td>
				<td>
				</td>
			</tr>
			
			<tr>
				<!--�������-->
				<td>
					���݂̌������
				</td>
				<td>
					<div id="divKensakuZyotai" ></div>
				</td>
				<td>
				</td>
			</tr>
			
		</table>
		
		<!---------------------------------------- ���ތ������� ---------------------------------------->
		<!--2010/02/15 Nakamura UPDATE START-->
		<!--div class="scroll_genka" id="sclList1" style="height:45%;width:99%;" rowSelect="true">-->
		<div class="scroll_genka" id="sclList1" style="height:55%;width:99%;" rowSelect="true">
		<!--2010/02/15 Nakamura UPDATE END---->
		    <div id="shizaiTable"></div>
		</div>
		
		<!------------------------------------- ���ތ������ʃt�b�^�[ ----------------------------------->
		<table width="99%">
			<tr>
				<!--�ꊇ�I��-->
				<td width="10%" align="center">
					<input type="checkbox" name="chkShizaiIkkatu" onClick="funShizaiCheck()" tabindex="16" />�ꊇ�I��
				</td>
				<!--�������ʏ��-->
				<td width="70%" align="center">
					<span id="spnRecInfo2">�f�[�^���@�F�@<span id="spnRecCnt2"></span> ���ł�(<span id="spnRowMax2"></span>�����ɕ\�����Ă��܂�)�@<span id="spnCurPage2"></span></span>
				</td>
				
				<td width="20%" align="center">
				</td>
			</tr>
			
			<tr>
				<td width="10%" align="center">
				</td>
				
				<!--�y�[�W�I��-->
				<td width="70%" align="center">
					<div id="divPage2" style="height:50px;font-size:12pt;"></div>
				</td>
				
				<td width="20%" align="center">
				</td>
				
			</tr>
			
			<tr>
				<!--���ޑI���{�^��-->
				<td width="10%" align="center">
					<input type="button" class="normalbutton" id="btnShizaiSelect" name="btnShizaiSelect" value="���ޑI��" onClick="funShizaiSentakuClick()" tabindex="18">
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
		
		<!-------------------------------------- ���ޑI���^�C�g�� -------------------------------------->
		<a name="lnkShizaiSentaku" />
		<table width="99%">
			<tr>
				<!--���ޑI�����C��-->
				<td colspan="3">
					<hr>
				</td>
			</tr>
			<tr>
				<!--���ޑI�𕶎�-->
				<td width="70%">
					�I������
				</td>
				<!--���ޑI�������N-->
				<td width="30%" align="right">
					<a href="RuiziSearch.jsp#lnkSeihinKensaku" tabindex="18">���i����</a>&nbsp;
					<a href="RuiziSearch.jsp#lnkShizaiKensaku" tabindex="18">���ތ���</a>
				</td>
			</tr>
			
			
			
			
			
			
			
		</table>
		
		<!---------------------------------------- ���ތ��ꗗ ---------------------------------------->
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
						<th class="columntitle">�I��</th>
						<th class="columntitle">�H��</th>
						<th class="columntitle">���ރR�[�h</th>
						<th class="columntitle">���ޖ�</th>
						<th class="columntitle">�P��</th>
						<th class="columntitle">����(��)</th>
						<th class="columntitle">�g�p��/�P�[�X</th>
						<th class="columntitle">���i�R�[�h</th>
					</tr>
				</thead>
				<tbody>
					<table class="detail" id="tblListKoho" cellpadding="0" cellspacing="0" border="1" datasrc="" datafld="rec" style="width:999px;">
					</table>
				</table>
			</table>
		</div>
		
		<!------------------------------------- ���ތ��ꗗ�t�b�^�[ ----------------------------------->
		<table width="99%" style="height:25%;">
			<tr style="vertical-align:top;height:35%;">
				<!--�ꊇ�I��-->
				<td width="10%" align="center">
					<input type="checkbox" name="chkKohoIkkatu" onClick="funKohoCheck()" tabindex="20" />�ꊇ�I��
				</td>
				<!--�������ʏ��-->
				<td width="70%" align="center">
				</td>
				
				<td width="20%" align="center">
				</td>
			</tr>
			<tr style="vertical-align:top;height:65%;">
			
				<td width="100%" colspan="3" align="left">
					<input type="button" class="normalbutton" id="btnShizaiSakuzyo" name="btnShizaiSakuzyo" value="�폜" onClick="funDeleteKoho()" tabindex="21">
					<input type="button" class="normalbutton" id="btnShizaiKakutei" name="btnShizaiKakutei" value="�m��" onClick="funKakuteiKoho()" tabindex="22">
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