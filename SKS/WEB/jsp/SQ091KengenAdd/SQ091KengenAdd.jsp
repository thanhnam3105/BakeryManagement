<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@�����@�\�ǉ����                                                  -->
<!-- �쐬�ҁFTT.Jinbo                                                                -->
<!-- �쐬���F2009/04/09                                                              -->
<!-- �T�v  �F�����̉�ʁA�@�\�A�f�[�^�̑g�ݍ��킹�̑I�����s���B                      -->
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
        <script type="text/javascript" src="include/SQ091KengenAdd.js"></script>
        <!-- �X�^�C���V�[�g -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">

        <script for=tblList event=onreadystatechange>
            if (tblList.readyState == 'complete') {
                //������ү���ޔ�\��
                funClearRunMessage();
            }
        </script>

        <!--  �e�[�u�����׍s�N���b�N -->
        <script for="tblList" event="onclick" language="JavaScript">
            //�I���s�̔w�i�F��ύX
            funChangeSelectRowColor();
        </script>
    </head>

    <body class="pfcancel" onLoad="funLoad();" topmargin="20" leftmargin="8" marginheight="20" marginwidth="5">
        <!-- XML Document��` -->
        <xml id="xmlJSP9030"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlAuthority" src="../common/xml/AuthorityInfo.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlRESULT"></xml>

        <form name="frm00" id="frm00" method="post">
            <!-- �^�C�g���E�{�^�� -->
            <table width="99%">
                <tr>
                    <td width="32%" class="title">�����@�\�ǉ�</td>
                    <td width="68%" align="right">
                        <input type="button" class="normalbutton" id="btnSelect" name="btnSelect" value="�̗p" onClick="funSelect();">
                        <input type="button" class="normalbutton" id="btnClear" name="btnClear" value="�N���A" onClick="funClear();">
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="�I��" onClick="funClose();">
                    </td>
                </tr>
            </table>

            <!-- ���[�U�[��� -->
            <br>
            <div id="divUserInfo"></div>
            <br>

            <!-- ���́E�I�� -->
            <table width="610" align="center">
                <tr>
                    <td width="120">�@�\��<font color="red">�i�K�{�j</font></td>
                    <td>
                        <input type="text" class="act_text" id="txtKino" name="txtKino" maxlength="60" value="" style="width:300px;">
                    </td>
                </tr>
                <tr>
                    <td height="10"></td>
                </tr>
                <tr>
                    <td>���<font color="red">�i�K�{�j</font></td>
                    <td>
                        <select id="ddlGamen" name="ddlGamen" style="width:300px;" onChange="funChangeCmb(2);">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td height="10"></td>
                </tr>
                <tr>
                    <td>�@�\<font color="red">�i�K�{�j</font></td>
                    <td>
                        <select id="ddlKino" name="ddlKino" style="width:300px;" onChange="funChangeCmb(3);">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td height="10"></td>
                </tr>
                <tr>
                    <td>�Q�Ɖ\�f�[�^<font color="red">�i�K�{�j</font></td>
                    <td>
                        <select id="ddlData" name="ddlData" style="width:300px;">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td height="10"></td>
                </tr>
                <tr>
                    <td>���l</td>
                    <td>
                        <textarea class="act_area" id="txtBiko" name="txtBiko" cols="80" rows="6"></textarea>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
