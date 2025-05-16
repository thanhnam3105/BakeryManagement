<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@���ގ�z�˗����o�͉��                                            -->
<!-- �쐬�ҁFTT.Shima                                                                -->
<!-- �쐬���F2014/09/05                                                              -->
<!-- �T�v  �F���ގ�z�˗���������͂���                                            -->
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
        <script type="text/javascript" src="../common/js/ZipFileDownload.js"></script>
        <!-- �� -->
        <script type="text/javascript" src="include/SQ250ShizaiTehaiOutput.js"></script>
        <!-- �X�^�C���V�[�g -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">
    </head>

    <body class="pfcancel" onLoad="funLoad_head();">
        <!-- XML Document��` -->
        <xml id="xmlRGEN3200"></xml>
        <xml id="xmlRGEN3320"></xml>
        <xml id="xmlRGEN3360"></xml>
        <xml id="xmlRGEN3370"></xml>
        <xml id="xmlRGEN3380"></xml>
        <xml id="xmlRGEN3390"></xml>
        <xml id="xmlRGEN3680"></xml>
        <xml id="xmlRGEN3700"></xml>
        <xml id="xmlRGEN3730"></xml>
        <xml id="xmlRGEN3290"></xml>
        <xml id="xmlRGEN3310"></xml>

        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN2130I" src="../../model/FGEN2130I.xml"></xml>
        <xml id="xmlFGEN3320I" src="../../model/FGEN3320I.xml"></xml>
        <xml id="xmlFGEN3360I" src="../../model/FGEN3360I.xml"></xml>
        <xml id="xmlFGEN3370I" src="../../model/FGEN3370I.xml"></xml>
        <xml id="xmlFGEN3380I" src="../../model/FGEN3380I.xml"></xml>
        <xml id="xmlFGEN3390I" src="../../model/FGEN3390I.xml"></xml>
        <xml id="xmlFGEN3680I" src="../../model/FGEN3680I.xml"></xml>
        <xml id="xmlFGEN3700I" src="../../model/FGEN3700I.xml"></xml>
        <xml id="xmlFGEN3730I" src="../../model/FGEN3700I.xml"></xml>
<xml id="xmlFGEN3290I" src="../../model/FGEN3290I.xml"></xml>
<xml id="xmlFGEN3310I" src="../../model/FGEN3310I.xml"></xml>

        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlFGEN2130O"></xml>
        <xml id="xmlFGEN3320O"></xml>
        <xml id="xmlFGEN3360O"></xml>
        <xml id="xmlFGEN3370O"></xml>
        <xml id="xmlFGEN3380O"></xml>
        <xml id="xmlFGEN3390O"></xml>
        <xml id="xmlFGEN3680O"></xml>
        <xml id="xmlFGEN3700O"></xml>
        <xml id="xmlFGEN3730O"></xml>
        <xml id="xmlRESULT"></xml>
        <xml id="xmlFGEN3290O"></xml>
        <xml id="xmlFGEN3310O"></xml>

        <form name="frm00" id="frm00" method="post">
            <!-- �^�C�g���� -->
            <!-- �^�C�g���E�{�^�� -->
            <table width="99%">
                <tr>
                    <td width="25%" class="title">���ގ�z�˗����o��</td>
                    <td width="75%" align="right">
                        <input type="button" class="normalbutton" id="aaa" name="btnHaashin" value="���M" style="width:100px" onClick="funNextLogic(1);">
                        <input type="button" class="normalbutton" id="btnYobidasi" name="btnYobidasi" value="���ۑ�" style="width:100px" onClick="funNextLogic(2);">
                        <input type="button" class="normalbutton" id="btnReference" name="btnReference" value="�Q�l����" style="width:100px" onClick="funReference(1);">
                        <input type="button" class="normalbutton" id="btnClear" name="btnClear" value="�폜" style="width:100px" onClick="funNextLogic(3);">
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="�I��" style="width:100px" onClick="funEnd();">

                        <!--  <input type="button" class="normalbutton" id="btntest" name="btntest" value="test" style="width:100px" onClick="funtest();"> -->
                       <!-- <input type="button" class="normalbutton" id="btnYobidasi" name="btnYobidasi" value="�����ꊇ�I��" style="width:100px" onClick="funShizaiSearch(1);">  -->
                    </td>
                </tr>
            </table>

            <!-- ��� -->
            <table width="99%">
                <tr>
                    <td align="right">
                        <div id="divUserInfo"></div>
                    </td>
                </tr>
            </table>

            <!-- �ގ��ďo�I�������`�F�b�N -->
            <input type="hidden" id="hdnRuiziSelect" name="hdnRuiziSelect" value="false" />

            <!-- �\���p������R�[�h -->
            <input type="hidden" id="hdnTaisyosizai_disp" name="hdnTaisyosizai_disp" />
            <!-- �\���p������R�[�h -->
            <input type="hidden" id="hdnHattyusaki_disp" name="hdnHattyusaki_disp" />
            <!-- �\���p���i�R�[�h -->
            <input type="hidden" id="hdnSyohin_disp" name="hdnSyohin_disp" />
            <!-- �\���p�i�� -->
            <input type="hidden" id="hdnHinmei_disp" name="hdnHinmei_disp" />
            <!-- �\���p�׎p -->
            <input type="hidden" id="hdnNisugata_disp" name="hdnNisugata_disp" />
            <!-- �\���p�����ރR�[�h -->
            <input type="hidden" id="hdnOldShizai_disp" name="hdnOldShizai_disp" />
            <!-- �\���p�V���ރR�[�h -->
            <input type="hidden" id="hdnNewShizai_disp" name="hdnNewShizai_disp" />
            <!-- �����p��z�X�e�[�^�X -->
            <input type="hidden" id="hdnStatus" name="hdnStatus" />

            <!-- Excel�t�@�C���p�X -->
            <input type="hidden" id="strFilePath" name="strFilePath" />
            <input type="hidden" id="strServerConst" name="strServerConst" />
            <!-- Excel�t�@�C���� -->
            <input type="hidden" id="strExcelFilePath" name="strExcelFilePath" />
            <!-- PDF�t�@�C���� -->
            <input type="hidden" id="strPdfFilePath" name="strPdfFilePath" />
            <!-- �Q�l���� -->
            <input type="hidden" id="sq220Category" name="sq220Category" />

            <!-- �ҏW�`�F�b�N -->
            <input type="hidden" id="hdnHensyu" name="hdnHensyu" value="false" />

            <!-- ���[���[�̃��[���ɐݒ肷�郁�[���A�h���X�P -->
            <input type="hidden" id="strMaiAddress1" name="strMaiAddress1" />
            <!-- ���[���[�̃��[���ɐݒ肷�郁�[���A�h���X�Q -->
            <input type="hidden" id="strMaiAddress2" name="strMaiAddress2" />
            <input type="hidden" id="hdMeilAddress" name="hdMeilAddress" value="false" />
            <!-- ���M�{�^��������Excel�̃p�X�ݒ�p -->
            <input type="hidden" id="hdExcelPass" name="hdExcelPass" value="false" />

        </form>
    </body>
</html>