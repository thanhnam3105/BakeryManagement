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
        <script type="text/javascript" src="include/SQ110GenkaShisan.js"></script>
        <!-- �X�^�C���V�[�g -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">
    </head>

    <body class="" onLoad="funLoad_Status()">
    	
        <!-- XML Document��` -->
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlRESULT"></xml>
        
        <form name="frm00" id="frm00" method="post">
            <table width="99%">
                <tr>
                    <td width="40%" class="title">�X�e�[�^�X�ݒ�</td>
                    <td width="5%">&nbsp;</td>
                    <td width="55%" align="right">
                        <input type="button" class="normalbutton" id="btnToroku" name="btnToroku" value="�o�^" onClick="fun_Toroku_status();" tabindex="1">
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
            
            <table width="99%" cellspacing="0" cellpadding="0" border="0">
                <tr>
                    <td>���L����X�e�[�^�X��I�����A�u�o�^�v�{�^���������ĉ������B<hr size="1"></td>
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
                        <input type="button" style="visibility:hidden" class="normalbutton" id="btnClear" name="btnClear" value="�X�e�[�^�X�N���A" onClick="fun_status_clear();" tabindex="8">
                    </td>
                </tr>
            </table>
            
        </form>
    </body>
</html>
