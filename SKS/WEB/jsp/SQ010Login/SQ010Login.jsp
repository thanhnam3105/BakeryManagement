<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@���O�C�����                                                      -->
<!-- �쐬�ҁFTT.Jinbo                                                                -->
<!-- �쐬���F2009/03/19                                                              -->
<!-- �T�v  �F���O�C�����[�U�̔F�؏������s���B                                        -->
<!------------------------------------------------------------------------------------->
<%
    Integer iMode;

    //�p�����[�^�̎擾
    String strUserId = request.getParameter("id");

    //���[�h�̐ݒ�
    if (strUserId != null && strUserId != "") {
        iMode = 1;    //�V���O���T�C���I��
    } else {
        iMode = 2;    //���O�C����ʂ���N��
        strUserId = "";
    }
%>
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
        <script type="text/javascript" src="include/SQ010Login.js"></script>
        <!-- �X�^�C���V�[�g -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">
    </head>

    <body class="pfcancel" onLoad="funLoad()">
        <!-- XML Document��` -->
        <xml id="xmlJSP0010"></xml>
        <xml id="xmlRGEN2110"></xml>

        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlSA010I" src="../../model/SA010I.xml"></xml>

        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlSA010O"></xml>
        <xml id="xmlRESULT"></xml>

        <!-- ADD 2013/9/25 okano�yQP@30151�zNo.28 start -->
        <xml id="xmlJSP0020"></xml>
        <xml id="xmlSA020I" src="../../model/SA020I.xml"></xml>
        <xml id="xmlSA020O"></xml>
        <!-- ADD 2013/9/25 okano�yQP@30151�zNo.28 end -->
        <!-- ADD 2015/07/27 TT.Kitazawa�yQP@40812�zNo.14 start -->
        <xml id="xmlJSP9030"></xml>
        <xml id="xmlFGEN2130I" src="../../model/FGEN2130I.xml"></xml>
        <xml id="xmlFGEN2130O"></xml>
        <!-- ADD 2015/07/27 TT.Kitazawa�yQP@40812�zNo.14 end -->

        <form name="frm00" id="frm00" method="post">
            <!-- �^�C�g���� -->
            <table align="center" width="350">
                <tr>
                    <th colspan="2" align="center" bgcolor="#FEC0C1">
                        <font color="#E54F5B">�p�X���[�h����͂��Ă�������</font>
                    </th>
                </tr>
            </table>

            <!-- ���͕� -->
            <table align="center" width="350">
                <tr>
                    <td height="8px"></td>
                </tr>
                <tr>
                    <td width="100" style="text-align:center;">�Ј��ԍ�</td>
                    <td>
                        <span class="ninput" format="10,0" comma="false" required="true" defaultfocus="false" id="em_UserId">
                        <input type="text" class="disb_text" name="txtUserId" id="txtUserId" maxlength="10" style="width:200px;text-align:left;" onBlur="funBuryZero(this, 10);">
                        </span>
                    </td>
                </tr>
                <tr>
                    <td style="text-align:center;">�p�X���[�h</td>
                    <td>
                        <input type="password" class="disb_text" name="txtPass" id="txtPass" maxlength="30" style="width:200px;text-align:left;">
                    </td>
                </tr>
            </table>

            <br>

            <!-- �{�^���� -->
            <table align="center">
                <tr>
                    <td width="160" align="center">
                        <input type="button" name="btnOk" id="btnOk" class="normalbutton" value="�n�j" onClick="funChkLogin();">
                    </td>
                    <td width="160" align="center">
                        <input type="button" name="btnCancel" id="btnCancel" class="normalbutton" value="�L�����Z��" onClick="funClose();">
                    </td>
                </tr>
            </table>

            <!-- �yQP@00342�z -->
            <table align="center">
                <tr>
                    <td width="160" align="center">
                        <!-- MOD 2014/8/7 shima�yQP@30154�zNo.42 start -->
                        <!-- <input type="button" name="btnEigyo" id="btnEigyo" class="normalbutton" value="�c�Ɠo�^��" onClick="funEigyoLogin();"> -->
                        <input type="button" name="btnEigyo" id="btnEigyo" class="normalbutton" value="�V�K�o�^" onClick="funEigyoLogin();">
                        <!-- MOD 2014/8/7 shima�yQP@30154�zNo.42 end -->
                    </td>
                    <td width="160" align="center">
                        <!-- ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.14 start -->
                        <input type="button" name="btnHelp" id="btnHelp" class="normalbutton" value="�w���v�\��" onClick="funHelpDisp();">
                        <!-- �w���v�t�@�C�� -->
                        <input type="hidden" value="" name="strHelpPath" id="strHelpPath">
                        <!-- ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.14 end -->
                    </td>
                </tr>
            </table>

            <input type="hidden" name="hidMode" id="hidMode" value="<%= iMode %>">
            <input type="hidden" name="hidUserId" id="hidUserId" value="<%= strUserId %>">

        </form>
    </body>
</html>
