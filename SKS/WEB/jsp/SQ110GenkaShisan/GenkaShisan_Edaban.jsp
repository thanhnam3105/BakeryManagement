<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �yQP@00342�z�V�T�N�C�b�N�@�X�e�[�^�X�N���A���                                                -->
<!-- �쐬�ҁFTT.Nishigawa                                                                -->
<!-- �쐬���F2011/01/26                                                              -->
<!-- �T�v  �F���݃X�e�[�^�X�̕\���ƁA�X�e�[�^�X�N���A���s���B                          -->
<!------------------------------------------------------------------------------------->
<html>
    <head>
        <title>�������Z�V�X�e�� �}�ԍ쐬���</title>
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

    <body class="" onLoad="funLoad_Edaban();">
    	
        <!-- XML Document��` -->
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        
        <xml id="xmlRESULT"></xml>
        
        <xml id="xmlRGEN2190"></xml>
        <xml id="xmlFGEN2170I" src="../../model/FGEN2170I.xml"></xml>
        <xml id="xmlFGEN2170O"></xml>
        
        <form name="frm00" id="frm00" method="post">
            
            <table id="dataTable" name="dataTable" border="0" cellspacing="0" width="630" align="left">
                <tr>
                	<td>
                		���}�Ԏ�ނ�I�����Ă��������B
                		<br/>
                		<select name="ddlEda" id="ddlEda" style="width:200px;height:16px;" tabindex="1" style="background-color:#ffff88;"/>
                        </select>
                	</td>
                	<td align="right" valign="top">
                        <input type="button" class="normalbutton" id="btnTyusi" name="btnTyusi" value="�o�^" onClick="edaban_toroku();" tabindex="1">
                    	<input type="button" class="normalbutton" id="btnEdaban" name="btnEdaban" value="�I��" onClick="edaban_close();" tabindex="1">
                	</td>
               	</tr>
                <tr>
                	<td colspan="2">
                		�I���ӁI�I���W�i������R�[�h����쐬����܂��B
               			<br/><br/>
						�� ���얼�̌��ɒǋL����e�L�X�g����͂��Ă��������B
               			<br/>
                	</td>
                	<td></td>
                </tr>
                <tr>
                	<td colspan="2">
                	<input type="text" class="act_text" id="txtShisakuNm" name="txtShisakuNm" readonly  datafld="" maxlength="" value="" style="width:350px;" tabindex="-1">
                	�{
                	<input type="text" class="act_text" id="txtEdaShisakuNm" name="txtEdaShisakuNm" datafld="" maxlength="" value="" style="width:250px;" tabindex="-1" style="background-color:#ffff88;">
                	</td>
                	<td></td>
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
