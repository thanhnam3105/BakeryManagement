<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@�������Z��ʁi�w�b�_�[�j                                                 -->
<!-- �쐬�ҁFTT.nishigawa                                                             -->
<!-- �쐬���F2009/10/20                                                              -->
<!-- �T�v  �F�������Z��ʂ̃w�b�_�[���t���[��                                                                        -->
<!------------------------------------------------------------------------------------->
<html>

	<script type="text/javascript">
    <!--
    	//�I���{�^�������m�F
	    var end_click = false;

//20160628 KPX@502111_No.1 ADD start
    window.onbeforeunload = function() {
        if( event.clientY < 0 || event.altKey ) {
            var mes = "�u�L�����Z���v��I�����āu�I���v�{�^���ŕ��Ă��������B\n\n";
            mes = mes + "�u�~�v�{�^���ŉ�ʂ����ƕs����������A�o�^����Ă�����e��������\��������܂��B" ;
            event.returnValue = mes;
        }
    }
//20160628 KPX@502111_No.1 ADD end

		window.onunload = function() {

			//�I���{�^������
			if(end_click){

			}
			//�E�B���h�E�́~�{�^������
			else{
				//�r������
				funcHaitaKaijo(1);

				//���b�Z�[�W�\��
				alert(E000021);
			}
		}
	// -->
	</script>

    <head>
        <title>�������Z�V�X�e�� �������Z��ʁi�c�Ɓj</title>
        <!-- ���� -->
        <script type="text/javascript" src="../common/js/Const.js"></script>
        <script type="text/javascript" src="../common/js/MessageControl.js"></script>
        <script type="text/javascript" src="../common/js/ConnectionControl.js"></script>
        <script type="text/javascript" src="../common/js/XmlControl.js"></script>
        <script type="text/javascript" src="../common/js/AjaxControl.js"></script>
        <script type="text/javascript" src="../common/js/CheckControl.js"></script>
        <script type="text/javascript" src="../common/js/DisplayControl.js"></script>
        <script type="text/javascript" src="../common/js/InputControl.js"></script>
        <!-- ADD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.6 start -->
        <script type="text/javascript" src="../common/js/MailControl.js"></script>
        <!-- ADD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.6 end -->
        <!-- �� -->
        <script type="text/javascript" src="include/SQ160GenkaShisan.js"></script>
        <!-- �X�^�C���V�[�g -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">
    </head>

    <body class="pfcancel" onLoad="funLoad_head();" tabindex="-1">

        <!-- XML Document��` -->
        <xml id="xmlRGEN0010"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN0010I" src="../../model/FGEN0010I.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlFGEN0010O"></xml>
        <xml id="xmlRESULT"></xml>
        <xml id="xmlFGEN2020I" src="../../model/FGEN2020I.xml"></xml>
        <xml id="xmlFGEN2020O"></xml>

        <xml id="xmlRGEN0041"></xml>
        <xml id="xmlFGEN0040I" src="../../model/FGEN0040I.xml"></xml>
        <xml id="xmlFGEN0040O"></xml>

        <xml id="xmlRGEN0030"></xml>
        <xml id="xmlFGEN0030I" src="../../model/FGEN0030I.xml"></xml>
        <xml id="xmlFGEN0030O"></xml>

<!--  DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.1 start -->
<!--         <xml id="xmlRGEN0050"></xml>
        <xml id="xmlFGEN0020I" src="../../model/FGEN0020I.xml"></xml>
        <xml id="xmlFGEN0020O"></xml>

        <xml id="xmlRGEN0051"></xml>
        <xml id="xmlFGEN0050I" src="../../model/FGEN0050I.xml"></xml>
        <xml id="xmlFGEN0050O"></xml>
 -->
<!--  DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.1 end -->
<!--  ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.1 start -->
        <xml id="xmlRGEN2240"></xml>
        <xml id="xmlFGEN2240I" src="../../model/FGEN2240I.xml"></xml>
        <xml id="xmlFGEN2240O"></xml>
<!--  ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.1 end -->

        <!--2010/02/23 NAKAMURA ADD START------------------------------>
        <xml id="xmlRGEN0090"></xml>
        <xml id="xmlFGEN0090I" src="../../model/FGEN0090I.xml"></xml>
        <xml id="xmlFGEN0090O"></xml>
        <!--2010/02/23 NAKAMURA ADD END-------------------------------->

        <xml id="xmlRGEN2160"></xml>
        <!-- ADD 2013/10/22 QP@30154 okano start -->
        <xml id="xmlFGEN2130I" src="../../model/FGEN2130I.xml"></xml>
        <xml id="xmlFGEN2130O"></xml>
        <!-- ADD 2013/10/22 QP@30154 okano end -->

        <form name="frm00" id="frm00" method="post">
            <table width="99%">
                <tr>
                    <td width="19%" class="title">�������Z��ʁi�c�Ɓj</td>
                    <td width="10%" class="title2">���x���P</td>

                    <!-- 2010/05/12 �V�T�N�C�b�N�i�����j�v�] �y�Č�No9,12�z�r�����,�w���v�̕\���@TT���� START -->
                    <td width="25%" align="left">
                    </td>
                    <!-- 2010/05/12 �V�T�N�C�b�N�i�����j�v�] �y�Č�No9,12�z�r�����,�w���v�̕\���@TT���� END -->

                    <td width="45%" align="right">
                        <input type="button" class="normalbutton" id="btnToroku" name="btnToroku" value="�o�^" onClick="funToroku()" tabindex="1">
                        <!-- ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.1 start -->
                        <input type="button" class="normalbutton" id="btnInsatu" name="btnInsatu" value="���" onClick="funInsatu()" tabindex="2">
                        <!-- ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.1 end -->
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="�I��" onClick="funEnd();end_click=true;" tabindex="3">
                    </td>
                </tr>
            </table>

            <!-- ���[�U�[��� -->
            <div id="divUserInfo"></div>

            <!-- ���́E�I�� -->
            <table width="100%" cellpadding="0" cellspacing="0" datasrc="" datafld="" border="0">
                <!-- 1�s�� -->
                <tr>
                    <td width="100">����R�[�h</td>
                    <td width="400">
                        <input type="text" class="disb_text" id="txtShainCd" name="txtShainCd" readonly datafld="" maxlength="" value="" style="width:100px;" tabindex="-1">
                        -&nbsp;
                        <input type="text" class="disb_text" id="txtNen" name="txtNen" datafld="" readonly maxlength="" value="" style="width:50px;" tabindex="-1">
                        -&nbsp;
                        <input type="text" class="disb_text" id="txtOiNo" name="txtOiNo" datafld="" readonly maxlength="" value="" style="width:50px;" tabindex="-1">
                         -&nbsp;
                        <input type="text" class="disb_text" id="txtEdaNo" name="txtEdaNo" datafld="" readonly maxlength="" value="" style="width:50px;" tabindex="-1">
                    </td>
                    <td rowspan="3" valign="top">
                       �̗p�T���v��No<br/>
                       <input type="text" class="disb_text" id="txtSaiyou" name="txtSaiyou" datafld="" readonly maxlength="" value="" style="width:150px;" tabindex="-1">
                       <!-- �y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 ADD Start -->
                       <input type="hidden" class="disb_text" id="hdnSaiyou_column" name="hdnSaiyou_column" >
                       <!-- �y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 ADD End   -->
                       <br/>���Z����<br/>
                       <!-- �y�V�T�N�C�b�NH24�Ή��zNo41 Start -->
                       <!-- <input type="text" id="txtKizitu" name="txtKizitu"  maxlength="10" style="border-color:#999;border-style:solid;border-width:1px;ime-mode:disabled;background-color:#ffff88;width:150px"  onchange="setFg_Henkou();hidukeSetting(this);" onblur="chkKizitu(this);" value="" tabindex="6" /> -->
                       <input type="text" id="txtKizitu" name="txtKizitu"  maxlength="10" style="border-color:#999;border-style:solid;border-width:1px;ime-mode:disabled;background-color:#ffff88;width:150px"  onchange="setFg_Henkou();hidukeSetting(this);" value="" tabindex="6" />
                       <!-- �y�V�T�N�C�b�NH24�Ή��zNo41 End   -->
                    </td>
                    <td rowspan="3" width="150" valign="top">
                       �˗��ԍ�
                       <br><div id="divIraiNo"></div>
                    </td>

                    <td rowspan="3" align="right">
                    	<input type="button" class="normalbutton" id="btnStatusRireki" name="btnStatusRireki" value="�ð������" onClick="funStatusRireki_btn();" tabindex="4"><br/>
                    	<input type="button" class="normalbutton" id="btnHaita" name="btnHaita" value="�g�p���\��" onClick="funHaitaDisp_btn();" tabindex="5"><br/>
                        <!-- DEL 2015/05/15 TT.Kitazawa�yQP@40812�zNo.14 start -->
                    	<!-- <input type="button" class="normalbutton" id="btnHelp" name="btnHelp" value="�w���v�\��" onClick="funHelpDisp();" tabindex="6"> -->
                        <!-- DEL 2015/05/15 TT.Kitazawa�yQP@40812�zNo.14 end -->
                    </td>
                </tr>

                <!-- 2�s�� -->
                <tr>
                    <td>�i��</td>
                    <td>
                        <input type="text" class="act_text" id="txtHinNm" name="txtHinNm" readonly  datafld="" maxlength="" value="" style="width:350px;" tabindex="-1">
                    </td>
                    <td>&nbsp;</td>
                </tr>

                <!-- 3�s�� -->
                <tr>
                    <td>�}�Ԏ��</td>
                    <td>
                        <input type="text" class="act_text" id="txtShuruiEda" name="txtShuruiEda" readonly  datafld="" maxlength="" value="" style="width:150px;" tabindex="-1">
                    </td>
                    <td>&nbsp;</td>
                </tr>

                <!-- 4�s�� -->
                <tr>
                    <td>&nbsp;</td>
                    <td colspan="2">
                        <input type="text" class="disb_text" bgcolor="yellow" style="width:16px;height:16px;background-color:#ffff88;" readonly  tabindex="-1" />
                        �̍��ڂ͓��͉\�ł�
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        ���̍��ڂ͌v�Z���ڂł�
                    </td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>

            </table>

            <!-- �ύX�t���O -->
            <input type="hidden" value="false" name="FgHenkou" id="FgHenkou">
            <!-- �����ݒ� -->
            <input type="hidden" value="" name="hdnKengen" id="hdnKengen">
            <!-- �_�E�����[�h�t�@�C�� -->
            <input type="hidden" value="" name="strFilePath" id="strFilePath">


            <!-- �yQP@00342�z -->
            <!-- �r������CD -->
            <input type="hidden" value="" name="strHaitaCdShisaku" id="strHaitaCdShisaku">
            <!-- �r�����얼 -->
            <input type="hidden" value="" name="strHaitaNmShisaku" id="strHaitaNmShisaku">
            <!-- ���ݽð�� -->
            <input type="hidden" value="" name="hdnStatus_kenkyu" id="hdnStatus_kenkyu">
            <input type="hidden" value="" name="hdnStatus_seikan" id="hdnStatus_seikan">
            <input type="hidden" value="" name="hdnStatus_gentyo" id="hdnStatus_gentyo">
            <input type="hidden" value="" name="hdnStatus_kojo" id="hdnStatus_kojo">
            <input type="hidden" value="" name="hdnStatus_eigyo" id="hdnStatus_eigyo">
            <!-- ADD 2013/10/22 QP@30154 okano start -->
            <!-- ������� -->
            <input type="hidden" value="" name="hdnBusho_kenkyu" id="hdnBusho_kenkyu">
            <input type="hidden" value="" name="hdnBusho_seikan" id="hdnBusho_seikan">
            <input type="hidden" value="" name="hdnBusho_gentyo" id="hdnBusho_gentyo">
            <input type="hidden" value="" name="hdnBusho_kojo" id="hdnBusho_kojo">
            <input type="hidden" value="" name="hdnBusho_eigyo" id="hdnBusho_eigyo">
            <!-- ADD 2013/10/22 QP@30154 okano end -->


            <!-- 2010/05/12 �V�T�N�C�b�N�i�����j�v�] �y�Č�No9�z�r�����̕\���@TT���� START -->
            <!-- �r����� -->
            <input type="hidden" value="" name="strHaitaKaisha" id="strHaitaKaisha">
            <!-- �r������ -->
            <input type="hidden" value="" name="strHaitaBusho" id="strHaitaBusho">
            <!-- �r�����[�U -->
            <input type="hidden" value="" name="strHaitaUser" id="strHaitaUser">
            <!-- �{���̌��� -->
            <input type="hidden" value="" name="strKengenMoto" id="strKengenMoto">
            <!-- 2010/05/12 �V�T�N�C�b�N�i�����j�v�] �y�Č�No9�z�r�����̕\���@TT���� START -->


            <!-- 2010/05/12 �V�T�N�C�b�N�i�����j�v�] �y�Č�No12�z�w���v�̕\���@TT���� START -->
            <!-- �w���v�t�@�C�� -->
            <input type="hidden" value="" name="strHelpPath" id="strHelpPath">
            <!-- 2010/05/12 �V�T�N�C�b�N�i�����j�v�] �y�Č�No12�z�w���v�̕\���@TT���� START -->

            <!-- 2015/08/26 TT.Kitazawa�yQP@40812�zNo.6 start -->
            <!-- ���[���n���F����˗��T���v��No. -->
            <input type="hidden" value="" name="hdnNo_iraisampleForMail" id="hdnNo_iraisampleForMail">
            <!-- 2015/08/26 TT.Kitazawa�yQP@40812�zNo.6 end -->

        </form>
    </body>
</html>
