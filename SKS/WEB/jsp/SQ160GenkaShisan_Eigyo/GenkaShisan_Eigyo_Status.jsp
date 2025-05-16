<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �yQP@00342�z�V�T�N�C�b�N�@�X�e�[�^�X�N���A���                                                -->
<!-- �쐬�ҁFTT.Nishigawa                                                                -->
<!-- �쐬���F2011/01/26                                                              -->
<!-- �T�v  �F���݃X�e�[�^�X�̕\���ƁA�X�e�[�^�X�N���A���s���B                          -->
<!------------------------------------------------------------------------------------->
<html>
    <head>
        <title>�������Z�V�X�e�� �X�e�[�^�X�ݒ���</title>
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
        <script type="text/javascript" src="include/SQ160GenkaShisan.js"></script>
        <!-- �X�^�C���V�[�g -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">
    </head>

    <body class="" onLoad="funLoad_Status()">
    	
        <!-- XML Document��` -->
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
                    <td width="40%" class="title">�X�e�[�^�X�ݒ�</td>
                    <td width="5%">&nbsp;</td>
                    <td width="55%" align="right">
                        <input type="button" class="normalbutton" id="btnClear" name="btnClear" value="�o�^" onClick="fun_Toroku();" tabindex="1">
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="�I��" onClick="fun_Close_st();" tabindex="2">
                    </td>
                </tr>
            </table>
            
            <br/>

			<table width="300" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>���݂̈˗��E���F��</td>
                </tr>
            </table>
			<table width="300" border="1" cellspacing="0" cellpadding="0">
                <tr>
                    <th class="columntitle" width="50">������</th>
                    <th class="columntitle" width="50">����</th>
                    <th class="columntitle" width="50">����</th>
                    <th class="columntitle" width="50">�H��</th>
                    <th class="columntitle" width="50">�c��</th>
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
                    <td>���L����X�e�[�^�X��I�����A�u�o�^�v�{�^���������ĉ������B<hr size="1"></td>
                </tr>
            </table>
            
            <table id="dataTable" name="dataTable" border="0" cellspacing="0" width="300" align="left">
                <tr>
                	<td align="center"><input type="radio" name="chk" onclick="chkSaiyou();" value="0" tabindex="3" checked></td>
                	<td>�ۑ��i�X�e�[�^�X�ύX�����j</td>
               	</tr>
               	<tr>
                	<td align="center"><input type="radio" name="chk" onclick="chkSaiyou();" value="1" tabindex="4"></td>
                	<td>���Z�˗�</td>
               	</tr>
               	<tr>
                	<td align="center"><input type="radio" name="chk" onclick="chkSaiyou();" value="2" tabindex="5"></td>
                	<td>�m�F����</td>
               	</tr>
               	<tr>
                	<td align="center"><input type="radio" name="chk" onclick="chkSaiyou();" value="3" tabindex="6"></td>
                	<td>
                		�̗p�L���m��i�̗p�L��j
                	</td>
               	</tr>
               	<tr>
                	<td align="center"></td>
                	<td>
                		�̗p�T���v���m�n<br />
                		<select name="ddlSaiyoSample" id="ddlSaiyoSample" onChange="chkSaiyou();" style="background-color:#ffffff;width:150px;height:16px;" disabled tabindex="7" >
                        </select>
                	</td>
               	<tr>
                	<td align="center"><input type="radio" name="chk" onclick="chkSaiyou();" value="4" tabindex="8"></td>
                	<td>�̗p�L���m��i�̗p�����j</td>
               	</tr>
               	<tr>
                	<td align="center"><input type="radio" name="chk" onclick="chkSaiyou();" value="5" tabindex="9"></td>
                	<td>���Z�r���ł̕s�̗p����</td>
               	</tr>
            </table>
            
            <!-- �X�e�[�^�X -->
            <input type="hidden" id="st_kenkyu" name="st_kenkyu" value="">
            <input type="hidden" id="st_seikan" name="st_seikan" value="">
            <input type="hidden" id="st_gentyo" name="st_gentyo" value="">
            <input type="hidden" id="st_kojo" name="st_kojo" value="">
            <input type="hidden" id="st_eigyo" name="st_eigyo" value="">
            
            <!-- ����No -->
            <input type="hidden" id="cd_shain" name="cd_shain" value="">
            <input type="hidden" id="nen" name="nen" value="">
            <input type="hidden" id="oi" name="oi" value="">
            <input type="hidden" id="eda" name="eda" value="">
            
        </form>
    </body>
</html>
