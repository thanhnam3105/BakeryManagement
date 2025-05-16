<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �yQP@00342�z�V�T�N�C�b�N�@�X�e�[�^�X�������                                                                             -->
<!-- �쐬�ҁFTT.Nishigawa                                                                                             -->
<!-- �쐬���F2011/01/24                                                              -->
<!-- �T�v  �F�Ώێ���No�̃X�e�[�^�X�������ꗗ�\������                          -->
<!------------------------------------------------------------------------------------->
<html>
    <head>
        <title></title>
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
        <script type="text/javascript" src="include/SQ140StatusRireki.js"></script>
        <!-- �X�^�C���V�[�g -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">
    </head>

    <body class="pfcancel" onLoad="funLoad()">
    
        <!-- XML Document��` -->
        <xml id="xmlRGEN2010"></xml>
        
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN2000I" src="../../model/FGEN2000I.xml"></xml>
        
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlFGEN2000O"></xml>
        
        <xml id="xmlRESULT"></xml>

        <form name="frm00" id="frm00" method="post">
        
            <table width="99%">
                <tr>
                    <td width="20%" class="title">�X�e�[�^�X����</td>
                    <td width="25%">&nbsp;</td>
                    <td width="55%" align="right">
                                                <input type="button" class="normalbutton" id="btnCopy" name="btnCopy" value="�I��" onClick="funClose();" tabindex="1">
                    </td>
                </tr>
            </table>
            <br>
			<table width="99%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>����No�@�u<span id="divStatusRirekiCd"></span>�v�@�̃X�e�[�^�X������\�����Ă��܂��B</td>
                </tr>
            </table>
            
            <!-- [�����ް��ꗗ]���X�g -->
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
                        
                        <th class="columntitle">��Ɠ��t</th>
                        <th class="columntitle">��Ǝ���</th>
                        <th class="columntitle">��Ǝ�</th>
                        <th class="columntitle">��Ǝ��</th>
                        
                        <th class="columntitle">��Əڍ׋Ɩ�</th>
                        <th class="columntitle">
                        	<table width="250" cellspacing="0" cellpadding="0" border="1" frame="void">
                        		<tr class="columntitle" >
                        			<th colspan="5">�˗��E���F��</th>
                        		</tr>
                        		<tr class="columntitle" >
                        			<th width="50">������</th>
                        			<th width="50">����</th>
                        			<th width="50">����</th>
                        			<th width="50">�H��</th>
                        			<th width="50">�c��</th>
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
                <!-- �f�[�^�� -->
                <tr align="center">
                    <td height="18px">
                        <span id="spnRecInfo">�f�[�^���@�F�@<span id="spnRecCnt"></span> ���ł�(<span id="spnRowMax"></span>�����ɕ\�����Ă��܂�)�@<span id="spnCurPage"></span></span>
                    </td>
                </tr>
            </table>

            <!-- �y�[�W�����N -->
            <div id="divPage" style="height:50px;font-size:12pt;"></div>
            
            <input type="hidden" id="hidShisakuNo_shaincd" name="hidShisakuNo_shaincd" value="">
            <input type="hidden" id="hidShisakuNo_nen" name="hidShisakuNo_nen" value="">
            <input type="hidden" id="hidShisakuNo_oi" name="hidShisakuNo_oi" value="">
            <input type="hidden" id="hidShisakuNo_eda" name="hidShisakuNo_eda" value="">

        </form>
    </body>
</html>
