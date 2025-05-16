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
        <script type="text/javascript" src="../common/js/ZipFileDownload.js"></script>

        <!-- �� -->
        <script type="text/javascript" src="include/SQ250ShizaiTehaiOutput.js"></script>

        <!-- �X�^�C���V�[�g -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">

    </head>

    <body class="pfcancel" onLoad="funLoad_dtl();">
        <!-- XML Document��` -->
        <xml id="xmlRGEN3200"></xml>
        <xml id="xmlRGEN3290"></xml>
        <xml id="xmlRGEN3680"></xml>
        <xml id="xmlRGEN3700"></xml>
        <xml id="xmlRGEN3730"></xml>

        <xml id="xmlRGEN3310"></xml>

        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN2130I" src="../../model/FGEN2130I.xml"></xml>
        <xml id="xmlFGEN3200I" src="../../model/FGEN3200I.xml"></xml>
        <xml id="xmlFGEN3290I" src="../../model/FGEN3290I.xml"></xml>
        <xml id="xmlFGEN3310I" src="../../model/FGEN3310I.xml"></xml>
        <xml id="xmlFGEN3680I" src="../../model/FGEN3680I.xml"></xml>
        <xml id="xmlFGEN3700I" src="../../model/FGEN3700I.xml"></xml>
        <xml id="xmlFGEN3730I" src="../../model/FGEN3730I.xml"></xml>

        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlFGEN2130O"></xml>
        <xml id="xmlFGEN3200O"></xml>
        <xml id="xmlFGEN3290O"></xml>
        <xml id="xmlFGEN3310O"></xml>
        <xml id="xmlFGEN3680O"></xml>
        <xml id="xmlFGEN3700O"></xml>
        <xml id="xmlFGEN3730O"></xml>
        <xml id="xmlRESULT"></xml>

        <form name="frm00" id="frm00" method="post" width="99%">
            <!-- ���́E�I�� -->
            <table>
                <tr>
                    <td width="120">�V�Ł^����</td>
                    <td width="655">
                        <input type="radio" id="revision_new" name="revision" value="1" checked="checked" onclick="funKaihan(1)">
                        <label for="revision_new">�V��</label>&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="radio" id="revision_rev" name="revision" value="2" onclick="funKaihan(2)">
                        <label for="revision_rev">����</label>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td width="120">������P</td>
                    <td>
                        <select name="cmbOrderCom1" id="cmbOrderCom1" style="width:245px" onChange="funChangeHattyusaki(1);" >
                        </select>
                        <select id="txtOrderUser1" name="txtOrderUser1" style="width:400px;"></select>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td width="120">������Q</td>
                    <td>
                        <select name="cmbOrderCom2" id="cmbOrderCom2" style="width:245px"onChange="funChangeHattyusaki(2);" >
                        </select>
                        <select class="act_text" id="txtOrderUser2" name="txtOrderUser2"  style="width:400px;"></select>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td width="120">���@�e</td>
                    <td>
                        <input type="text" class="act_text" id="txtNaiyo" name="txtNaiyo" value="" style="width:650px;">
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td width="120">���i�R�[�h</td>
                    <td>
                        <input type="text" class="disb_text" id="txtSyohin" name="txtSyohin" value="" readOnly style="width:650px;">
                    </td>
                    <td>
                    	 <input type="button" class="normalbutton" id="btnYobidasi" name="btnYobidasi" value="�����ꊇ�I��" style="width:100px" onClick="funShizaiSearch(1);">
                    </td>
                </tr>
                <tr>
                    <td width="120">���i��</td>
                    <td>
                        <textarea class="act_text" name="txtHinmei" id="txtHinmei" cols="50" rows="4" style="width:650px;"></textarea>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td width="120">�ׁ@�p</td>
                    <td>
                        <input type="text" class="act_text" id="txtNisugata" name="txtNisugata" value="" style="width:650px;">
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td width="120">�Ώێ���</td>
                    <td>
                        <select name="cmbTargetSizai" id="cmbTargetSizai" style="width:200px">
                        </select>
                    </td>
                    <td></td>
                </tr>
                <!--
                <tr>
                    <td width="120">������</td>
                    <td>
                        <select name="cmbOrder" id="cmbOrder" style="width:200px">
                        </select>
                    </td>
                    <td></td>
                </tr>
                 -->
                <tr>
                    <td width="120">�[����</td>
                    <td>
                        <input type="text" class="act_text" id="txtDelivery" name="txtDelivery" value="" style="width:650px;">
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td width="120">�����ރR�[�h</td>
                    <td>
                        <input type="text" class="disb_text" id="txtOldShizai" name="txtOldShizai" value="" style="width:650px;">
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td width="120">�V���ރR�[�h</td>
                    <td>
                        <input type="text" class="disb_text" id="txtNewShizai" name="txtNewShizai"  value="" style="width:650px;">
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td width="120">�d�l�ύX</td>
                    <td width="340">
                        <p id="siyohenko">
                            <input type="radio" id="specificationChange_ari" name="specificationChange" value="1" checked="checked">
                            <label for="specificationChange_ari">����</label>&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="radio" id="specificationChange_nasi" name="specificationChange" value="2">
                            <label for="specificationChange_nasi">����</label>
                        </p>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td width="120">�݌v�@</td>
                    <td>
                        <input type="text" class="act_text" id="txtDesign1" name="txtDesign1" value="" maxlength="250" style="width:650px;backgroundColor:#ffffff" onChange="funCheckComplete(1);setFg_hensyu();" >
                    </td>
                    <td width="100">
                        <input type="button" class="normalbutton" id="btnRuizi" name="btnRuizi" value="�ގ��f�[�^�ďo" style="width:100px" onClick="funShizaiSearch(2);">
                    </td>
                </tr>
                <tr>
                    <td width="120">�݌v�A</td>
                    <td>
                        <input type="text" class="act_text" id="txtDesign2" name="txtDesign2" value="" maxlength="250" style="width:650px;" onChange="funCheckComplete(2);setFg_hensyu();">
                    </td>
                    <td width="50">
                       �@
                    </td>
                </tr>
                <tr>
                    <td width="120">�݌v�B</td>
                    <td>
                        <input type="text" class="act_text" id="txtDesign3" name="txtDesign3" value="" maxlength="250" style="width:650px;" onChange="funCheckComplete(3);setFg_hensyu();">
                    </td>
                    <td width="50">
                       �@
                    </td>
                </tr>
                <tr>
                    <td width="120">�ގ�</td>
                    <td>
                        <input type="text" class="act_text" id="txtZaishitsu" name="txtZaishitsu" value="" maxlength="250" style="width:650px;" onChange="funCheckComplete(4);setFg_hensyu();">
                    </td>
                    <td width="50">
                       �@
                    </td>
                </tr>
                <tr>
                    <td width="120">���l</td>
                    <td>
                        <textarea class="act_area" name="txtBiko" id="txtBiko" maxlength="250" cols="50" rows="2" style="width:650px;" onChange="funCheckComplete(5);setFg_hensyu();"></textarea>
                    </td>
                    <td width="50">
                       �@
                    </td>
                </tr>
                <tr>
                    <td width="120">����F</td>
                    <td>
                        <input type="text" class="act_text" id="txtPrintColor" name="txtPrintColor" value="" maxlength="250" style="width:350px;" onChange="funCheckComplete(6);setFg_hensyu();">
                    </td>
                    <td width="50">
                       �@
                    </td>
                </tr>
                <tr>
                    <td width="120">�F�ԍ�</td>
                    <td>
                        <input type="text" class="act_text" id="txtColorNo" name="txtColorNo" value="" maxlength="250" style="width:650px;" onChange="funCheckComplete(7);setFg_hensyu();">
                    </td>
                    <td width="100">
                       �@
                    </td>
                </tr>
                <tr>
                    <td width="120">�ύX���e�ڍ�</td>
                    <td>
                        <textarea class="act_area" name="txtChangesDetail" id="txtChangesDetail" cols="50" rows="2" style="width:650px;" onChange="funCheckComplete(8);setFg_hensyu();"></textarea>
                    </td>
                    <td width="50">
                       �@
                    </td>
                </tr>
                <tr>
                    <td width="120">�[��</td>
                    <td>
                        <textarea class="act_area" name="txtDeliveryTime" id="txtDeliveryTime" cols="50" rows="2" style="width:650px;" onChange="funCheckComplete(9);setFg_hensyu();"></textarea>
                    </td>
                    <td width="50">
                       �@
                    </td>
                </tr>
                <tr>
                    <td width="120">����</td>
                    <td>
                        <input type="text" class="act_text" id="txtQuantity" name="txtQuantity" value="" style="width:650px;" onChange="funCheckComplete(10);setFg_hensyu();">
                    </td>
                    <td>
                        <input type="button" class="normalbutton" value="�m�F" style="width:50px" onClick="funCheckCompleteALL();">
                    </td>
                </tr>
                <tr>
                    <td width="120">�����ލ݌�</td>
                    <td>
                        <input type="text" class="act_text" id="txtOldShizaiZaiko" name="txtOldShizaiZaiko" value="�����ލ݌ɂ��A�[����H��ւ��A���������B" style="width:650px;" onChange="funCheckComplete(11);setFg_hensyu();">
                    </td>
                    <td width="50">
                       �@
                    </td>
                </tr>
                <tr>
                    <td width="120">����</td>
                    <td>
                        <input type="text" class="act_text" id="txtRakuhan" name="txtRakuhan" value="�H��Ƃ��A���̏�A�m���ɗ��ł����肢���܂�" style="width:650px;" onChange="funCheckComplete(12);setFg_hensyu();">
                    </td>
                    <td width="50">
                       �@
                    </td>
                </tr>
            </table>
            <!-- cho 2016/09/27 -->
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
            <!-- �ǉ� -->
            <!-- ���e -->
            <input type="hidden" id="hdnNaiyo_disp" name="hdnNaiyo_disp" />
            <!-- �[���� -->
            <input type="hidden" id="hdnNounyusaki_disp" name="hdnNounyusaki_disp" />

            <!-- �����p�Ј��R�[�h -->
            <input type="hidden" id="hdnShain" name="hdnCdShain" />
            <!-- �����p�N�R�[�h -->
            <input type="hidden" id="hdnNen" name="hdnNen" />
            <!-- �����p�ǔ� -->
            <input type="hidden" id="hdnNoOi" name="hdnNoOi" />
            <!-- �����p���ޏ��� -->
            <input type="hidden" id="hdnSeqShizai" name="hdnSeqShizai" />
            <!-- �����p�}�� -->
            <input type="hidden" id="hdnNoEda" name="hdnNoEda" />
            <!-- �����p���i�R�[�h -->
            <input type="hidden" id="hdnCdShohin" name="hdnCdShohin" />

            <!-- ���ގ�z���͂���̃p�����[�^ -->
            <!--  �Ј��R�[�h -->
            <input type="hidden" id="cd_shain" name="cd_shain" />
            <!-- �N�R�[�h -->
            <input type="hidden" id="nen" name="nen" />
            <!-- �ǔ� -->
            <input type="hidden" id="no_oi" name="no_oi" />
            <!-- seq���� -->
            <input type="hidden" id="seq_shizai" name="seq_shizai" />
            <!-- �}�� -->
            <input type="hidden" id="no_eda" name="no_eda" />
            <!-- �t���O -->
            <input type="hidden" id="flg_hatyuu_status" name="flg_hatyuu_status" />
            <!-- �����R�[�hhidden -->
			<input type="hidden" id="hidden_Hattyuusaki_cd" name="hidden_Hattyuusaki_cd" />
			<!--�Ώێ���hidden  -->
			<input type="hidden" id="hidden_target_sizai" name="hidden_target_sizai" />
            <!-- ���i�R�[�h -->
			<input type="hidden" id="shohinCd" name="shohinCd" />
			<!-- ���i�� -->
			<input type="hidden" id="hinmei" name="hinmei" />
			<!-- �׎p -->
			<input type="hidden" id="nisugata" name="nisugata" />
			<!-- �[���� -->
			<input type="hidden" id="delivery" name="delivery" />
			<!-- �����ރR�[�h -->
			<input type="hidden" id="olsShizai" name="olsShizai" />
			<!-- �V���ރR�[�h -->
			<input type="hidden" id="newShizai" name="newShizai" />


            <div id="sizaid">
			</div>


			<!-- ���ގ�z���͉�ʑJ�ڌ�A���ގ�z�e�[�u���擾�̃f�[�^ -->
<!-- 			<input type="hidden" id="tantosyamei1" name ="tantosyamei1" /> -->

			<!-- ���[�v�J�E���g -->
			<input type="hidden" name="loopCnt" name="loopCnt" />

        </form>

        <br><br><br><br>
        <br><br><br><br>
        <br><br><br><br>
        <br>

    </body>
</html>