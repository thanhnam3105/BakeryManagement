<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@�}�X�^���j���[���                                                -->
<!-- �쐬�ҁFTT.Sakamoto                                                             -->
<!-- �쐬���F2014/02/19                                                              -->
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
        <script type="text/javascript" src="include/SQ0170GenchoMenu.js"></script>
        <!-- �X�^�C���V�[�g -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">
    </head>

    <body class="pfcancel" onLoad="funLoad();">
        <!-- XML Document��` -->
        <xml id="xmlJSP9030"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN2130I" src="../../model/FGEN2130I.xml"></xml>
        <xml id="xmlRGEN2160"></xml>
        <xml id="xmlRGEN3390"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlFGEN2130O"></xml>
        <xml id="xmlRGEN3200"></xml>
        <xml id="xmlRESULT"></xml>
		<!-- 2016/09/05 May Thu �yKPX@1602367�zAdd Start -->
        <xml id="xmlFGEN3490I" src="../../model/FGEN3490I.xml"></xml>
		<xml id="xmlFGEN3490O"></xml>
		<!-- 2016/09/05 May Thu �yKPX@1602367�zAdd End -->

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
                                    <input type="button" name="btnHelp" id="btnHelp" class="normalbutton" value="�w���v�\��" onClick="funHelpDisp();">
                                    <!-- �w���v�t�@�C�� -->
                                    <input type="hidden" value="" name="strHelpPath" id="strHelpPath">
                                    <!-- ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.14 end -->
                                    <input type="button" name="btnOk" class="normalbutton" onClick="funNext(9);" value="���O�C���ύX">&nbsp;
                                </td>
                            </tr>
							<!-- ADD 2016/09/02 May Thu �yKPX@1602367�zAdd Start-->
							<tr style="text-align:right;font-size:12px;">
                                <td align="right">

                                    <div id="divOrderInfo"></div>
                                </td>
                            </tr>
							<!-- ADD 2016/09/02 May Thu �yKPX@1602367�zAdd END-->

                            <tr align="right">
                                <td colspan="2" width="240" height="200">
<!--                                    <img src="../image/QP.JPG" alt="QP" width="240" height="200"></img>-->
                                </td>
                            </tr>
                            <tr align="right">
                                <td colspan="2">
                                    <input type="button" name="btnBackMenu" class="normalbutton" onClick="funNext(0);" value="���C�����j���[�֖߂�" style="width:120px;height:30px;">&nbsp;
                                    <input type="button" name="btnOk" class="normalbutton" onClick="funClose();" value="�I��" style="width:120px;height:30px;">&nbsp;
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
