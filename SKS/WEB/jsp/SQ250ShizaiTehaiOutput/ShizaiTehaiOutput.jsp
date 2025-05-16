<%@ page language="java" contentType="text/html;charset=Windows-31J" %>

<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@���ގ�z�˗����o�͉��                                            -->
<!-- �쐬�ҁFTT.Shima                                                                -->
<!-- �쐬���F2014/09/05                                                              -->
<!-- �T�v  �F���ގ�z�˗���������͂���                                            -->
<!------------------------------------------------------------------------------------->
<html>
<head>
        <title>���ގ�z�˗����o�͉��</title>
        <!-- ���� -->
        <script type="text/javascript" src="../common/js/Const.js"></script>
        <script type="text/javascript" src="../common/js/MessageControl.js"></script>
        <script type="text/javascript" src="../common/js/ConnectionControl.js"></script>
        <script type="text/javascript" src="../common/js/XmlControl.js"></script>
        <script type="text/javascript" src="../common/js/AjaxControl.js"></script>
        <script type="text/javascript" src="../common/js/CheckControl.js"></script>
        <script type="text/javascript" src="../common/js/DisplayControl.js"></script>
        <script type="text/javascript" src="../common/js/InputControl.js"></script>
<!--         <script type="text/javascript" src="../common/js/SQ250ShizaiTehaiOutput.js"></script> -->
        <script type="text/javascript" src="../common/js/ZipFileDownload.js"></script>


        <!-- �� -->
        <script type="text/javascript" src="include/SQ250ShizaiTehaiOutput.js"></script>


        <!-- �X�^�C���V�[�g -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">

        <!-- XML Document��` -->
        <xml id="xmlRGEN3200"></xml>
        <xml id="xmlRGEN3290"></xml>
        <xml id="xmlRGEN3680"></xml>
        <xml id="xmlRGEN3700"></xml>
        <xml id="xmlRGEN3730"></xml>

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
    </head>

    <script type="text/javascript">
    <!--//
    //===================================================================================
    // �t�H�[�����[�h����
    // �쐬�ҁFH.Shima
    // �쐬���F2014/09/09
    // ����  �F�Ȃ�
    // �߂�l�F�Ȃ�
    // �T�v  �F�t�H�[�����[�h���̏���������
    //===================================================================================
        function funLoad() {

            var width, height;

            width  = window.screen.width;                     //��ʕ�
            height = window.screen.height;                    //��ʍ���

            //��ʃT�C�Y�E�ʒu�̐ݒ�
            resizeTo(width,height);
            moveTo(0,0);

            //��ʐݒ�
            funInitScreen(ConShizaiTehaiOutputId);

            // ���ގ�z�˗��o�͉�ʂɃp�����^��ݒ肷��
            param = GetQueryString();

            var kbn = parent.detail.document.frm00.flg_hatyuu_status.value;
            var cd_shain = parent.detail.document.frm00.cd_shain.value;
            var nen = parent.detail.document.frm00.nen.value;
            var seq_shizai = parent.detail.document.frm00.seq_shizai.value;
            var no_oi = parent.detail.document.frm00.no_oi.value;
            var no_eda = parent.detail.document.frm00.no_eda.value;
            var cd_shohin =parent.detail.document.frm00.shohinCd.value;

           var data =  kbn + ":::" + cd_shain + ":::" + nen + ":::" + seq_shizai  + ":::" + no_oi + ":::" + no_eda + ":::" + cd_shohin + ":::";

            // ���ގ�z�e�[�u���̃f�[�^�������\���ɐݒ�
            funShizaiTmp(data);

        window.onunload = function() {
            parent.detail.location.href="about:blank";
            parent.header.location.href="about:blank";
        }
        return null;
    }

	// �ݒ肵���p�����^�����ގ�z�˗��o�͉�ʂɃp�����^��ݒ肷�� 09/28
    function GetQueryString() {
    	var str = location.search;

            if (1 < str.length) {

                // �ŏ���1���� (?�L��) ����������������擾����
                var query = str.substring(1);

                // �N�G���̋�؂�L�� (&) �ŕ������z��ɕ�������
                var parameters = query.split('&');

                var result = new Object();
                for (var i = 0; i < parameters.length; i++) {
                    // �p�����[�^���ƃp�����[�^�l�ɕ�������
                    var element = parameters[i].split('=');

                    var paramName = decodeURIComponent(element[0]);

                    var paramValue = decodeURIComponent(element[1]);

                	 // �V���ރR�[�h
                    if(paramName == 'cd_shizai_new '){

                   		parent.detail.document.frm00.txtNewShizai.value = paramValue;
                   		// hidden
                   		parent.detail.document.frm00.newShizai.value = paramValue;
                   	}
                  	//�����ރR�[�h
                    if (paramName == 'cd_shizai') {

                   		parent.detail.document.frm00.txtOldShizai.value = paramValue;
                   		// hidden
                   		parent.detail.document.frm00.olsShizai.value = paramValue;
                   	}
                  	//�[����ː����H��
                    if (paramName == 'seizoKojoNm') {
                   		parent.detail.document.frm00.txtDelivery.value = paramValue;
                   		// hidden
                   		parent.detail.document.frm00.delivery.value = paramValue;

                   	}
                  	//������R�[�h
                  	if (paramName == 'cd_hattyusaki') {
//	                	parent.detail.document.frm00.cmbOrderCom1.value = ( '000' + paramValue ).slice( -3 );
	                	parent.detail.document.frm00.cmbOrderCom1.value = paramValue;
	                   	parent.detail.document.frm00.cmbOrderCom1.onchange();
                   	}
                    // �Ώێ���
                  	if(paramName == 'cd_taishoshizai') {
                   		//�Ώ�����
                   	  	parent.detail.document.frm00.cmbTargetSizai.value = paramValue;

                   	}
                 	// �Ј��R�[�h
                    if (paramName == 'cd_shain') {
                    	parent.detail.document.frm00.cd_shain.value = paramValue;

                   	}
                	 // �N�R�[�h�ߋ�
                 	if (paramName == 'nen') {
                   		parent.detail.document.frm00.nen.value = paramValue;

                   	}
                 	// seq����
                	if (paramName == 'seq_shizai') {
                		parent.detail.document.frm00.seq_shizai.value = paramValue;
                	}

                	// �ǔ�
                	if (paramName == 'no_oi') {
                   		parent.detail.document.frm00.no_oi.value = paramValue;
                   	}
                	// �}��
                	if (paramName == 'no_eda') {
                   		parent.detail.document.frm00.no_eda.value = paramValue;
                   	}
                	// ���i�R�[�h
                	if (paramName == 'seihinCd') {
                		parent.detail.document.frm00.txtSyohin.value = paramValue;
                		// hidden
                		parent.detail.document.frm00.shohinCd.value = paramValue;

                	}
                	// ���i��
                	if (paramName == 'seihinNm') {
                		//parent.detail.document.frm00.txtHinmei.value = paramValue;
                		parent.detail.document.frm00.txtHinmei.value = paramValue;
                		// hidden
                		parent.detail.document.frm00.hinmei.value = paramValue;
                	}

                	// �׎p
                	if (paramName == 'nisugata') {
                		parent.detail.document.frm00.txtNisugata.value = paramValue;
                		// hidden
                		parent.detail.document.frm00.nisugata.value = paramValue;

                	}

                	// �t���O
                	if (paramName == 'flg_hatyuu_status') {
                		parent.detail.document.frm00.flg_hatyuu_status.value = paramValue;

                	}

                }
                    // �p�����[�^�����L�[�Ƃ��ĘA�z�z��ɒǉ�����
                    result[paramName] = decodeURIComponent(paramValue);
                }

            return result;
     }

    -->
    </script>

    <FRAMESET rows="10%,89%" frameborder="0" border="0" framespacing="0" onLoad="funLoad();">
		<FRAME SRC="./ShizaiTehaiOutput_head.jsp" NORESIZE NAME="header"  scrolling="no" tabindex="1">
		<FRAME SRC="" NORESIZE NAME="detail"  scrolling="auto" tabindex="2">
	</FRAMESET>

</html>
