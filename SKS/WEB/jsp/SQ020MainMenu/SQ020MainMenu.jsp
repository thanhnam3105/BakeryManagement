<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@���C�����j���[���                                                -->
<!-- �쐬�ҁFTT.Jinbo                                                                -->
<!-- �쐬���F2009/03/19                                                              -->
<!-- �T�v  �F�g�p�����̂����ʂւ̃{�^���݂̂�\������B                            -->
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
        <script type="text/javascript" src="include/SQ020MainMenu.js"></script>
        <!-- �X�^�C���V�[�g -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">
    </head>

    <body class="pfcancel" onLoad="funLoad();">
        <!-- XML Document��` -->
        <xml id="xmlJSP9030"></xml>

        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN2130I" src="../../model/FGEN2130I.xml"></xml>

        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlFGEN2130O"></xml>
        <xml id="xmlRESULT"></xml>

        <form name="frm00" id="frm00" method="post">
            <!-- �^�C�g���� -->
            <table align="center" width="600" height="36">
                <tr>
                    <th align="center" bgcolor="#8380F5">
                    	<div id="divTitle" name="divTitle" />
                    </th>
                </tr>
            </table>

            <!-- ���͕� -->
            <table width="600">
                <tr>
                    <td>
                        <div id="divBtn"></div>
                    </td>
                    <td>
                        <table align="right" width="300" height="400">
                            <tr style="text-align:right;font-size:12px;">
                                <td align="right">
                                    <div id="divUserInfo"></div>
                                </td>
                            </tr>
                            <tr>
                                <td height="8"></td>
                            </tr>

                            <tr align="right">
                                <td colspan="2">
                                    <!-- ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.14 start -->
                                    <input type="button" name="btnHelp" id="btnHelp" class="normalbutton" value="�w���v�\��" onClick="funHelpDisp();">&nbsp;
                                    <!-- �w���v�t�@�C�� -->
                                    <input type="hidden" value="" name="strHelpPath" id="strHelpPath">
                                    <!-- ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.14 end -->
                                    <input type="button" name="btnOk" class="normalbutton" onClick="funNext(9);" value="���O�C���ύX">&nbsp;
                                </td>
                            </tr>
                            <tr align="right">
                                <td colspan="2" width="240" height="200">
<!--                                    <img src="../image/QP.JPG" alt="QP" width="240" height="200"></img>-->
                                </td>
                            </tr>
                            <tr align="right">
                                <td colspan="2">
                                    <input type="button" name="btnOk" class="normalbutton" onClick="funClose();" value="�I��" style="width:120px;height:30px;">&nbsp;
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>

            <input type="hidden" id="btnMenu" name="btnMenu" value="">
            <!-- ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.14 start -->
            <!-- �w���v�t�@�C�� �p�X -->
            <input type="hidden" value="" name="strHelpPath" id="strHelpPath">
            <!-- ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.14 end -->
        </form>
    </body>
</html>
