<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@�O���[�v�}�X�^�����e�i���X���                                    -->
<!-- �쐬�ҁFTT.Jinbo                                                                -->
<!-- �쐬���F2009/04/04                                                              -->
<!-- �T�v  �F�O���[�v�A�`�[���f�[�^�̓o�^�A�X�V�A�폜���s���B                        -->
<!------------------------------------------------------------------------------------->
<!-- �X�V�ҁFTT.Jinbo                                                                -->
<!-- �X�V���F2009/06/24                                                              -->
<!-- ���e  �F�폜�{�^���̃R�����g��(�ۑ�\��13)                                      -->
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
        <script type="text/javascript" src="include/SQ070GroupMst.js"></script>
        <!-- �X�^�C���V�[�g -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">

        <script for=txtGroup event=onkeydown>
            if (event.keyCode == 13 && document.frm00.rdoMode[0].checked) {
                //�Ō�̺��۰ق�Enter�������̏ꍇ�A��а���݂�̫�����ݒ�
                document.frm00.btnDummy.focus();
            }
        </script>

        <script for=txtTeam event=onkeydown>
            if (event.keyCode == 13 && document.frm00.rdoMode[1].checked) {
                //�Ō�̺��۰ق�Enter�������̏ꍇ�A��а���݂�̫�����ݒ�
                document.frm00.btnDummy.focus();
            }
        </script>
    </head>

    <body class="pfcancel" onLoad="funLoad();">
        <!-- XML Document��` -->
        <xml id="xmlJSP0710"></xml>
        <xml id="xmlJSP0720"></xml>
        <xml id="xmlJSP0730"></xml>
        <xml id="xmlJSP9020"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlSA050I" src="../../model/SA050I.xml"></xml>
        <xml id="xmlSA060I" src="../../model/SA060I.xml"></xml>
        <xml id="xmlSA080I" src="../../model/SA080I.xml"></xml>
        <xml id="xmlSA090I" src="../../model/SA090I.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlSA050O"></xml>
        <xml id="xmlSA060O"></xml>
        <xml id="xmlSA080O"></xml>
        <xml id="xmlSA090O"></xml>
        <xml id="xmlRESULT"></xml>
        <!-- ADD 2013/10/24 QP@30154 okano start -->
        <xml id="xmlSA140I" src="../../model/SA140I.xml"></xml>
        <xml id="xmlSA140O"></xml>
        <!-- ADD 2013/10/24 QP@30154 okano end -->

        <form name="frm00" id="frm00" onSubmit="" method="post">
            <table width="99%">
                <tr>
                    <td width="35%" class="title">�O���[�v�}�X�^�����e�i���X</td>
                    <td width="65%" align="right">
                        <input type="button" id="btnDummy" name="btnDummy" value="" style="width:0px" tabindex=-1> <!-- ̫����ݒ�p��а���� -->
                        <input type="button" class="normalbutton" id="btnInsert" name="btnInsert" value="�V�K" style="width:80px" onClick="funDataEdit(1);">
                        <input type="button" class="normalbutton" id="btnUpdate" named="btnUpdate" value="�X�V" style="width:80px" onClick="funDataEdit(2);">
<!--                        <input type="button" class="normalbutton" id="btnDelete" name="btnDelete" value="�폜" style="width:80px" onClick="funDataEdit(3);">-->
                        <input type="button" class="normalbutton" id="btnClear" name="btnClear" value="�N���A" style="width:80px" onClick="funClear();">
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="�I��" style="width:80px" onClick="funNext(0);">
                    </td>
                </tr>
            </table>

            <!-- ���[�U�[��� -->
            <br>
            <div id="divUserInfo"></div>
            <br>

            <table width="250">
                <tr>
                    <td width="60">�O���[�v</td>
                    <td width="60">
                        <input type="radio" name="rdoMode" value="" onClick="funChangeMode();" CHECKED>
                    </td>
                    <td width="60">�`�[��</td>
                    <td width="60">
                        <input type="radio" name="rdoMode" value="" onClick="funChangeMode();">
                    </td>
                </tr>
            </table>

            <table width="660">
                <!-- Line -->
                <tr>
                    <td colspan="2" align="center">
                        <hr>
                    </td>
                </tr>

                <tr>
                    <td width="110">�O���[�v<font color="red">�i�K�{�j</font></td>
                    <td>
                        <select id="ddlGroup" name="ddlGroup" style="width:400px;" onChange="funChangeGroup();">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td width="110">�O���[�v��<span id="spnHisuG"><font color="red">�i�K�{�j</font></span></td>
                    <td>
                         <input type="text" class="act_text" id="txtGroup" name="txtGroup" maxlength="100" value="" style="width:350px;">
                    </td>
                </tr>
                <!-- ADD 2013/10/24 QP@30154 okano start -->
                <tr>
                    <td width="110">���<span id="spnHisuK"><font color="red">�i�K�{�j</font></span></td>
                    <td>
                        <select id="ddlKaisha" name="ddlKaisha" style="width:400px;">
                        </select>
                    </td>
                </tr>
                <!-- ADD 2013/10/24 QP@30154 okano end -->
                <tr>
                    <td width="110">�`�[��</td>
                    <td>
                        <select id="ddlTeam" name="ddlTeam" style="width:400px;" onChange="funChangeTeam();">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td width="110">�`�[����<span id="spnHisuT"></span></td>
                    <td>
                         <input type="text" class="act_text" id="txtTeam" name="txtTeam" maxlength="100" value="" style="width:350px;">
                    </td>
                </tr>
            </table>

            <input type="hidden" id="hidEditMode" name="hidEditMode" value="">
        </form>
    </body>
</html>
